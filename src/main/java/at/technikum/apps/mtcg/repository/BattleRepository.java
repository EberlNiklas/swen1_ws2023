package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.Battle;
import at.technikum.apps.mtcg.entity.BattleResult;

import java.util.List;

public interface BattleRepository {

    String findOpenBattles();

    Battle openBattle(Battle battle, String playerA_Id);

    String waitingForBattle(String battleId);

    String getPlayerAId(String battleId);

    void joinBattle(String playerBId, String battleId);

    void updateBattleStatus(String battleId);

    void updatePoints(String user_id, int change);

    int getBattleRound(String battleId);

    Battle updateBattle(String winner, String battleId, Battle battle, int rounds);

    String battleResult(String battleId);

    List<BattleResult> getBattleResults(String battleId);

    int getPoints(String user_id);

    String getDeckId(String user_id);

    String battle(String player1Id, String player2Id, String battleId);
}
