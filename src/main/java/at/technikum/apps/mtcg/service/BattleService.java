package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Battle;
import at.technikum.apps.mtcg.entity.BattleResult;
import at.technikum.apps.mtcg.repository.BattleRepository;
import at.technikum.apps.mtcg.repository.DatabaseBattleRepository;

import java.util.List;
import java.util.UUID;

public class BattleService {

    private final BattleRepository battleRepository;

    public BattleService(BattleRepository battleRepository){
        this.battleRepository = battleRepository;
    }

    public String findOpenBattles(){
        return battleRepository.findOpenBattles();
    }

    public Battle openBattle(Battle battle, String playerA_Id){
        battle.setBattle_id(UUID.randomUUID().toString());
        battle.setPlayer_B(null);
        battle.setStatus("Battle open");
        battle.setRounds(0);
        battle.setWinner(null);
        return battleRepository.openBattle(battle, playerA_Id);
    }

    public String waitingForBattle(String battleId){
        return battleRepository.waitingForBattle(battleId);
    }

    public String getPlayerAId(String battleId){
        return battleRepository.getPlayerAId(battleId);
    }

    public void joinBattle(String playerBId, String battleId){
        battleRepository.joinBattle(playerBId, battleId);
    }

    public void updateBattleStatus(String battleId){
        battleRepository.updateBattleStatus(battleId);
    }

    public void updatePoints(String user_id, int change){
        battleRepository.updatePoints(user_id, change);
    }

    public int getBattleRound(String battleId){
        return battleRepository.getBattleRound(battleId);
    }

    public Battle updateBattle(String winner, String battleId, Battle battle, int rounds){
        return battleRepository.updateBattle(winner, battleId, battle, rounds);
    }

    public List<BattleResult> getBattleResults(String battleId){
        return battleRepository.getBattleResults(battleId);
    }

    public String battleResult(String battleId){
        return battleRepository.battleResult(battleId);
    }

    public int getPoints(String user_id){
        return battleRepository.getPoints(user_id);
    }

    public String getDeckId(String user_id){
        return battleRepository.getDeckId(user_id);
    }

    public String battle(String player1Id, String player2Id, String battleId){
        return battleRepository.battle(player1Id, player2Id, battleId);
    }
}
