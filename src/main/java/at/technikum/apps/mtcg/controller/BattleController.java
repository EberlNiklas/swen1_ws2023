package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.Battle;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.service.BattleService;
import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BattleController extends AbstractController{

    private final BattleService battleService;

    private final UserService userService;

    private final SessionService sessionService;

    public BattleController(BattleService battleService, UserService userService, SessionService sessionService){
        this.battleService = battleService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/battles");
    }

    @Override
    public Response handle(Request request) {
        if (supports(request.getRoute())) {
            if(request.getMethod().equals("POST")){
                return battle(request);
            }
            return notAllowed();
        }
        return notAllowed();
    }

    public Response battle(Request request){
        String token = request.getHttpHeader();
        String username = extractUsername(token);
        token = token.split(" ")[1];
        String user_id = userService.getIdFromUser(username);
        String playerA_id = null;
        String playerB_id = null;
        String battleLog = null;
        String winner = null;
        String looser = null;
        int points1;
        int points2;

        if (sessionService.isLoggedIn(token)) {
            ObjectMapper objectMapper = new ObjectMapper();
            Battle battle = null;

            String battle_id = battleService.findOpenBattles();
            synchronized (this) {
                if(battle_id == null){
                    battle = new Battle();
                    battle = battleService.openBattle(battle, user_id);
                    playerB_id = battleService.waitingForBattle(battle.getBattle_id());

                    if(playerB_id == null){
                        return json(HttpStatus.NOT_FOUND, "No waiting player found!");
                    }
                }else{
                    playerA_id = battleService.getPlayerAId(battle_id);
                    battleService.joinBattle(user_id, battle_id);
                    battleService.updateBattleStatus(battle_id);
                }
            }
            synchronized (this) {
                winner = battleService.battle(user_id, playerA_id, battle_id);
                if(winner != null){
                    looser = calculateElo(winner, user_id, playerB_id);
                    int rounds = battleService.getBattleRound(battle_id);
                    battle = battleService.updateBattle(winner, battle_id, battle, rounds);
                    battleLog = battleService.battleResult(battle_id);
                }else{
                    int rounds = battleService.getBattleRound(battle_id);
                    battle = battleService.updateBattle(winner, battle_id, battle, rounds);
                    battleLog = battleService.battleResult(battle_id);
                }
            }

            String userJson;
            try {
                userJson = objectMapper.writeValueAsString(battle);
                System.out.println(battleLog);
                if (winner == null && looser == null) {
                    points1 = battleService.getPoints(playerA_id);
                    points2 = battleService.getPoints(playerB_id);
                    System.out.println(playerA_id + ": draw, elo -> " + points1);
                    System.out.println(playerB_id + ": draw, elo -> " + points2);
                } else {
                    points1 = battleService.getPoints(winner);
                    points2 = battleService.getPoints(looser);
                    System.out.println(winner + ": wins, elo -> " + points1);
                    System.out.println(looser + ": lost, elo -> " + points2);
                }
            } catch (JsonProcessingException e) {
                return internalServerError();
            }

            return json(HttpStatus.CREATED, userJson);
        }else{
            return unauthorized();
        }

    }

    public String calculateElo(String winner, String playerA_id, String playerB_id){
        String looser = null;
        if (winner.equals(playerA_id)) {
            battleService.updatePoints(playerA_id, 3);
            battleService.updatePoints(playerB_id, -5);
            looser = playerB_id;
        } else if (winner.equals(playerB_id)) {
            battleService.updatePoints(playerB_id, 3);
            battleService.updatePoints(playerA_id, -5);
            looser = playerA_id;
        }
        return looser;
    }


}
