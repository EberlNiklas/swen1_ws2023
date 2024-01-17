package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DatabaseBattleRepository implements BattleRepository{

    private final String FIND_OPEN_BATTLES = "SELECT battle_id FROM battle WHERE status = ? LIMIT 1";

    private final String SAVE = "INSERT INTO battle(battle_id, playerA_id, playerB_id, status, rounds, winner) VALUES(?, ?, ?, ?, ?, ?)";

    private final String GET_BATTLE = "SELECT * FROM battle WHERE battle_id = ?";

    private final String WAIT_FOR_PLAYER = "SELECT playerB_id FROM battle WHERE battle_id = ?";

    private final String GET_PLAYER_A_ID = "SELECT playerA_id FROM battle WHERE battle_id = ?";

    private final String JOIN_BATTLE = "UPDATE battle SET playerB_id = ? WHERE battle_id = ?";

    private final String UPDATE_STATUS = "UPDATE battle SET status = ? WHERE battle_id = ?";

    private final String UPDATE_POINTS = "UPDATE users SET points = ? WHERE id = ?";

    private final String FIND_USER_BY_ID = "SELECT * FROM user WHERE id = ?";

    private final String GET_BATTLE_ROUND = "SELECT MAX(roundnumber) AS roundnumber FROM battleresult WHERE battle_id = ?";

    private final String UPDATE_BATTLE = "UPDATE battle SET status = ?, rounds = ?, winner = ? WHERE battle_id = ?";

    private final String GET_BATTLE_RESULTS = "SELECT * FROM battleresult WHERE battle_id = ?";

    private final String GET_POINTS = "SELECT points FROM users WHERE id = ?";

    private final String GET_DECK_ID = "SELECT deck_id FROM deck WHERE user_id = ?";

    private final String FIND_ALL = "SELECT card_id FROM deckcards WHERE deck_id = ?";

    private final String GET_RANDOM_CARD = "SELECT c.id, c.name, c.damage, c.package_id, c.elementtype, c.type\n" +
            "FROM card c\n" +
            "JOIN deckcards dc ON c.id = dc.card_id\n" +
            "JOIN deck d ON dc.deck_id = d.deck_id\n" +
            "WHERE d.user_id = ? ORDER BY RANDOM() LIMIT 1";

    private final String SAVE_RESULT = "INSERT INTO battleresult(result_id, battle_id, roundnumber, roundwinner, card_a, card_b, damage_a, damage_b, e_damage_a, e_damage_b) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String STEAL_CARD = "UPDATE deckcards SET deck_id = ? WHERE card_id = ?";

    private final Database database = Database.getInstance();

    @Override
    public String findOpenBattles() {
        String foundBattle = null;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_OPEN_BATTLES);

        ) {
            pstmt.setString(1, "battle open");
            try(ResultSet rs = pstmt.executeQuery()){
               if(rs.next()){
                   foundBattle = rs.getString("battle_id");
               }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return foundBattle;
    }

    @Override
    public Battle openBattle(Battle battle, String playerA_Id) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SAVE)
        ) {
            pstmt.setString(1, battle.getBattle_id());
            pstmt.setString(2, playerA_Id);
            pstmt.setString(3, battle.getPlayer_B());
            pstmt.setString(4, battle.getStatus());
            pstmt.setInt(5, battle.getRounds());
            pstmt.setString(6, battle.getWinner());

            pstmt.execute();
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        }

        return getBattle(battle.getBattle_id());
    }

    public Battle getBattle(String battleId) {
        Battle battle = null;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_BATTLE)
        ) {
            pstmt.setString(1, battleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    battle = new Battle(
                            rs.getString("battle_id"),
                            rs.getString("playerA_id"),
                            rs.getString("playerB_id"),
                            rs.getString("status"),
                            rs.getInt("rounds"),
                            rs.getString("winner")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return battle;
    }

    @Override
    public String waitingForBattle(String battleId) {
        String playerId = null;
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;

        while (elapsedTime < 60*1000*3) { // 3 minutes
            try (
                    Connection con = database.getConnection();
                    PreparedStatement pstmt = con.prepareStatement(WAIT_FOR_PLAYER)
            ) {
                pstmt.setString(1, battleId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        playerId = rs.getString("playerB_id");
                        if (playerId != null) {
                            break; // Player has joined, exit the loop
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(5000); // Wait for 5 seconds before checking again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            elapsedTime = (new Date()).getTime() - startTime;
        }

        return playerId;
    }

    @Override
    public String getPlayerAId(String battleId) {
        String playerId = null;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_PLAYER_A_ID)
        ) {
            pstmt.setString(1, battleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    playerId = rs.getString("playerA_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return playerId;
    }

    @Override
    public void joinBattle(String playerBId, String battleId) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(JOIN_BATTLE)
        ) {
            pstmt.setString(1, playerBId);
            pstmt.setString(2, battleId);
            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateBattleStatus(String battleId) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(UPDATE_STATUS)
        ) {
            pstmt.setString(1, "Ongoing");
            pstmt.setString(2, battleId);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePoints(String user_id, int change) {

        User user = getUser(user_id);
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(UPDATE_POINTS)
        ) {
            pstmt.setInt(1, user.getPoints() + change);
            pstmt.setString(2, user_id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        }
    }

    public User getUser(String user_id) {
        User user = null;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_USER_BY_ID)
        ) {
            pstmt.setString(1, user_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    user = new User(
                            rs.getString("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("points"),
                            rs.getInt("coins"),
                            rs.getString("bio"),
                            rs.getString("image"),
                            rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        }
        return user;
    }

    @Override
    public int getBattleRound(String battleId) {
        int round = 0;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_BATTLE_ROUND)
        ) {
            pstmt.setString(1, battleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    round = rs.getInt("roundnumber");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return round;
    }

    @Override
    public Battle updateBattle(String winner, String battleId, Battle battle, int rounds) {

        if (battle == null) {
            battle = new Battle();
        }

        battle.setWinner(winner);
        battle.setRounds(rounds);
        battle.setStatus("Finished");

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(UPDATE_BATTLE)
        ) {
            pstmt.setString(1, battle.getStatus());
            pstmt.setInt(2, battle.getRounds());
            pstmt.setString(3, battle.getWinner());
            pstmt.setString(4, battleId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        }

        Battle newBattle = getBattle(battleId);
        return newBattle;
    }

    @Override
    public String battleResult(String battleId) {
        List<BattleResult> results = getBattleResults(battleId);
        StringBuilder log = new StringBuilder();

        for (BattleResult result : results) {
            log.append("PlayerA: ").append(result.getCard_A()).append(" (").append(result.getDamage_A()).append(" Damage) vs PlayerB:").append(result.getCard_B()).append(" (").append(result.getDamage_A()).append(" Damage) =>")
                    .append(result.getDamage_A()).append(" VS ").append(result.getDamage_B()).append(" -> ").append(result.get_eDamage_A()).append(" VS ").append(result.get_eDamage_B()).append(" => ").append(result.getRoundWinner())
                    .append(" wins\n");
        }
        return log.toString();
    }

    @Override
    public List<BattleResult> getBattleResults(String battleId) {
        List<BattleResult> results = new ArrayList<>();

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_BATTLE_RESULTS)
        ) {
            pstmt.setString(1, battleId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BattleResult result = new BattleResult();
                    result.setCard_A(rs.getString("card_a"));
                    result.setCard_B(rs.getString("card_b"));
                    result.setDamage_A(rs.getInt("damage_a"));
                    result.setDamage_B(rs.getInt("damage_b"));
                    result.set_eDamage_A(rs.getInt("e_damage_a"));
                    result.set_eDamage_B(rs.getInt("e_damage_b"));

                    results.add(result);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return results;
    }

    @Override
    public int getPoints(String user_id) {
        int points = 0;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_POINTS)
        ) {
            pstmt.setString(1, user_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    points = rs.getInt("points");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return points;
    }

    @Override
    public String battle(String player1Id, String player2Id, String battleId) {
        String deckId1 = getDeckId(player1Id);
        String deckId2 = getDeckId(player2Id);
        List<String> cards1 = null;
        List<String> cards2 = null;

        for (int x = 1; x <= 100; x++) {
            cards1 = getDeckContent(deckId1);
            cards2 = getDeckContent(deckId2);

            if (cards1.isEmpty() || cards2.isEmpty()) {
                break;
            }

            Card card1 = getRandomCard(player1Id);
            Card card2 = getRandomCard(player2Id);

            handleBattleRound(card1, card2, deckId1, deckId2, player1Id, player2Id, battleId, x);
        }

        return determineOverallWinner(cards1, cards2, player1Id, player2Id);
    }

    private void handleBattleRound(Card card1, Card card2, String deckId1, String deckId2, String player1Id, String player2Id, String battleId, int round) {
        String roundWinner;
        double extraDamage1;
        double extraDamage2;

        // Monster vs Monster
        if (card1.getType().equals("Monster") && card2.getType().equals("Monster")) {
            extraDamage1 = card1.getDamage();
            extraDamage2 = card2.getDamage();
            if (card1.getName().equals("WaterGoblin") && card2.getName().equals("Goblin") || card1.getName().equals("Wizzard") && card2.getName().equals("Ork") || card1.getName().equals("FireElves") && card2.getName().equals("Dragon")) {
                extraDamage1 = card1.getDamage();
                extraDamage2 = 0;
            }
            if (card2.getName().equals("WaterGoblin") && card1.getName().equals("Goblin") || card2.getName().equals("Wizzard") && card1.getName().equals("Ork") || card2.getName().equals("FireElves") && card1.getName().equals("Dragon")) {
                extraDamage1 = 0;
                extraDamage2 = card2.getDamage();
            }
        }

        // Zauber vs Zauber und gemischte Kämpfe
        if ((card1.getType().equals("Spell") && card2.getType().equals("Spell")) ||
                (card1.getType().equals("Spell") && card2.getType().equals("Monster")) ||
                (card1.getType().equals("Monster") && card2.getType().equals("Spell"))) {

            if (card1.getElementType().equals("water") && card2.getElementType().equals("fire") ||
                    card1.getElementType().equals("fire") && card2.getElementType().equals("normal") ||
                    card1.getElementType().equals("normal") && card2.getElementType().equals("water")) {

                extraDamage1 = card1.getDamage() * 2;
                extraDamage2 = card2.getDamage() / 2;

                if (card1.getName().equals("WaterGoblin") && card2.getName().equals("Dragon") ||
                        card1.getName().equals("Wizzard") && card2.getName().equals("Ork") ||
                        card1.getName().equals("FireElves") && card2.getName().equals("Dragon") ||
                        card1.getName().equals("Knight") && card2.getName().equals("WaterSpell") ||
                        (card1.getName().equals("Kraken") && card2.getName().equals("WaterSpell") || card2.getName().equals("FireSpell") || card2.getName().equals("RegularSpell"))) {

                    extraDamage2 = 0;
                }
                if (card2.getName().equals("WaterGoblin") && card1.getName().equals("Goblin") ||
                        card2.getName().equals("Wizzard") && card1.getName().equals("Ork") ||
                        card2.getName().equals("FireElves") && card1.getName().equals("Dragon") ||
                        card2.getName().equals("Knight") && card1.getName().equals("WaterSpell") ||
                        (card2.getName().equals("Kraken") && card1.getName().equals("WaterSpell") || card1.getName().equals("FireSpell") || card1.getName().equals("RegularSpell"))) {

                    extraDamage1 = 0;
                } else if (card2.getElementType().equals("water") && card1.getElementType().equals("fire") ||
                        card2.getElementType().equals("fire") && card1.getElementType().equals("normal") ||
                        card2.getElementType().equals("normal") && card1.getElementType().equals("water")) {

                    extraDamage1 = card1.getDamage() / 2;
                    extraDamage2 = card2.getDamage() * 2;

                    if (card1.getName().equals("WaterGoblin") && card2.getName().equals("Dragon") ||
                            card1.getName().equals("Wizzard") && card2.getName().equals("Ork") ||
                            card1.getName().equals("FireElves") && card2.getName().equals("Dragon") ||
                            card1.getName().equals("Knight") && card2.getName().equals("WaterSpell") ||
                            (card1.getName().equals("Kraken") && card2.getName().equals("WaterSpell") || card2.getName().equals("FireSpell") || card2.getName().equals("RegularSpell"))) {

                        extraDamage2 = 0;
                    }
                    if (card2.getName().equals("WaterGoblin") && card1.getName().equals("Goblin") ||
                            card2.getName().equals("Wizzard") && card1.getName().equals("Ork") ||
                            card2.getName().equals("FireElves") && card1.getName().equals("Dragon") ||
                            card2.getName().equals("Knight") && card1.getName().equals("WaterSpell") ||
                            (card2.getName().equals("Kraken") && card1.getName().equals("WaterSpell") || card1.getName().equals("FireSpell") || card1.getName().equals("RegularSpell"))) {

                        extraDamage1 = 0;
                    }


                    if (extraDamage1 > extraDamage2) {
                        roundWinner = player1Id;
                        stealCard(deckId1, card2.getId());
                    } else if (extraDamage1 == extraDamage2) {
                        roundWinner = "Draw";
                    } else {
                        roundWinner = player2Id;
                        stealCard(deckId2, card1.getId());
                    }

                    save(card1, card2, roundWinner, extraDamage1, extraDamage2, battleId, round);
                }
            }
        }
    }

    private double calculateExtraDamage(Card attacker, Card defender) {
        double extraDamage = attacker.getDamage();

        // Spezielle Regeln
        if (attacker.getName().equals("Goblin") && defender.getName().equals("Dragon")) {
            extraDamage = 0;
        } else if (attacker.getName().equals("Ork") && defender.getName().equals("Wizzard")) {
            extraDamage = 0;
        } else if (attacker.getName().equals("Knight") && defender.getName().equals("WaterSpell")) {
            extraDamage = 0;
        } else if (attacker.getName().equals("Kraken") && defender.getType().equals("Spell")) {
            extraDamage = 0;
        } else if (attacker.getName().equals("Dragon") && defender.getName().equals("FireElves")) {
            extraDamage = 0;
        }

        return extraDamage;
    }


    private String determineOverallWinner(List<String> cards1, List<String> cards2, String player1Id, String player2Id) {
        if (cards1.size() > cards2.size()) {
            return player1Id;
        } else if (cards1.size() < cards2.size()) {
            return player2Id;
        }
        return null;
    }

    @Override
    public String getDeckId(String playerId) {
        String deckId = null;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_DECK_ID)
        ) {
            pstmt.setString(1, playerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    deckId = rs.getString("deck_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return deckId;
    }

    public List<String> getDeckContent(String deckId) {
        List<String> cards = new ArrayList<>();

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_ALL)
        ) {
            pstmt.setString(1, deckId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String card = rs.getString("card_id");
                cards.add(card);
            }
            return cards;
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        }
        return cards;
    }

    public Card getRandomCard(String playerId) {
        Card card = null;

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_RANDOM_CARD)
        ) {
            pstmt.setString(1, playerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    card = new Card(
                            rs.getString("card_id"),
                            rs.getString("name"),
                            rs.getInt("damage"),
                            rs.getString("elementtype"),
                            rs.getString("cardtype")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return card;
    }

    public void save(Card card1, Card card2, String roundWinner, double extraDamage1, double extraDamage2, String battleId, int round) {
        BattleResult battleResult = null;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SAVE_RESULT)
        ) {
            pstmt.setString(1, UUID.randomUUID().toString());
            pstmt.setString(2, battleId);
            pstmt.setInt(3, round);
            pstmt.setString(4, roundWinner);
            pstmt.setString(5, card1.getName());
            pstmt.setString(6, card2.getName());
            pstmt.setDouble(7, card1.getDamage());
            pstmt.setDouble(8, card2.getDamage());
            pstmt.setDouble(9, extraDamage1);
            pstmt.setDouble(10, extraDamage2);

            pstmt.execute();
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        }
    }

    public void stealCard(String deckId, String cardId) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(STEAL_CARD)
        ) {
            pstmt.setString(1, deckId);
            pstmt.setString(2, cardId);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
