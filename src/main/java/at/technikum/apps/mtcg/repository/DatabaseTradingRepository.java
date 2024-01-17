package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.Trade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTradingRepository implements TradeRepository{


    private final Database database = Database.getInstance();

    private final String FIND_ALL_SQL = "SELECT * FROM trade WHERE status = ?";

    private final String CHECK_IF_CARD_IS_IN_DECK = "SELECT deck_id FROM deckcards where card_id = ?";
    private final String SAVE = "INSERT INTO trade(trade_id, dealerUserId, customerUserId, dealerCardId, customerCardId, status, type, minDamage) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

    private final String DELETE = "DELETE FROM trade WHERE trade_id = ?";

    private final String GET_DAMAGE = "SELECT damage FROM card WHERE id = ?";

    private final String GET_TYPE = "SELECT type FROM card WHERE id = ?";

    private final String UPDATE = "UPDATE trade SET customerUserId = ?, customerCardId = ?, status = ? WHERE trade_id = ?";

    private final String TRADE_CARDS = "UPDATE stack SET user_id = ? WHERE card_id = ?";

    private final String GET_SPECIFIC_TRADE = "SELECT * FROM trade WHERE trade_id = ?";

    private final String GET_DEALER_ID = "SELECT dealerUserId FROM trade WHERE trade_id = ?";

    @Override
    public List<Trade> findAllOpenTrades() {
        List<Trade> allOpenTrades = new ArrayList<>();
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);

        ) {
            pstmt.setString(1, "trade open");
            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    Trade trade = new Trade(
                            rs.getString("trade_id"),
                            rs.getString("dealerUserId"),
                            rs.getString("customerUserId"),
                            rs.getString("dealerCardId"),
                            rs.getString("customerCardId"),
                            rs.getString("status"),
                            rs.getString("type"),
                            rs.getInt("minDamage")
                    );
                    allOpenTrades.add(trade);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return allOpenTrades;
    }

    @Override
    public boolean checkIfCardIsInDeck(String card_id) {
        String cardInDeck = null;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(CHECK_IF_CARD_IS_IN_DECK);

        ) {
            pstmt.setString(1, card_id);
            try(ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    cardInDeck = rs.getString("deck_id");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Trade saveTrade(Trade trade, String user_id) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SAVE);

        ) {
            pstmt.setString(1, trade.getTrade_id());
            pstmt.setString(2, trade.getDealerUserId());
            pstmt.setString(3, trade.getCustomerUserId());
            pstmt.setString(4, trade.getDealerCardId());
            pstmt.setString(5, trade.getCustomerCardId());
            pstmt.setString(6, trade.getStatus());
            pstmt.setString(7, trade.getType());
            pstmt.setInt(8, trade.getMinimumDamage());

            pstmt.execute();

        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return trade;
    }

    @Override
    public void deleteTrade(String trade_id) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(DELETE);

        ) {
            pstmt.setString(1, trade_id);
            pstmt.execute();
        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
    }

    @Override
    public String getDealerUserIdFromTrade(String trade_id) {
        String dealerId = null;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_DEALER_ID);

        ) {
            pstmt.setString(1, trade_id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    dealerId = rs.getString("dealerUserId");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return dealerId;
    }

    @Override
    public Trade getSpecificTrade(String trade_id) {
        Trade trade = null;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_SPECIFIC_TRADE);

        ) {
            pstmt.setString(1, trade_id);
            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    trade = new Trade(
                            rs.getString("trade_id"),
                            rs.getString("dealerUserId"),
                            rs.getString("customerUserId"),
                            rs.getString("dealerCardId"),
                            rs.getString("customerCardId"),
                            rs.getString("status"),
                            rs.getString("type"),
                            rs.getInt("minDamage")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return trade;
    }

    @Override
    public int getDamageFromCard(String card_id) {
        int damage = 0;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_DAMAGE);

        ) {
            pstmt.setString(1, card_id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    damage = rs.getInt("damage");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return damage;
    }

    @Override
    public String getTypeFromCard(String card_id) {
        String type = null;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_TYPE);

        ) {
            pstmt.setString(1, card_id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    type = rs.getString("type");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return type;
    }

    @Override
    public Trade updateTrade(Trade tradeToBeUpdated, String trade_id, String user_id, String card_id) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(UPDATE);

        ) {
            pstmt.setString(1, tradeToBeUpdated.getCustomerUserId());
            pstmt.setString(2, tradeToBeUpdated.getCustomerCardId());
            pstmt.setString(3, tradeToBeUpdated.getStatus());
            pstmt.setString(4, trade_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return tradeToBeUpdated;
    }

    @Override
    public void tradeCards(String user_id, String card_id) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(TRADE_CARDS);

        ) {
            pstmt.setString(1, user_id);
            pstmt.setString(2, card_id);
            pstmt.execute();
        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
    }



}
