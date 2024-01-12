package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.Deck;
import at.technikum.apps.mtcg.entity.Package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDeckRepository implements DeckRepository{

    private final String FIND_ALL = "SELECT card_id FROM deck where deck_id = ?";
    private final String SAVE = "INSERT INTO deck(deck_id, user_id) VALUES(?, ?)";

    private final String GET_DECK_ID = "SELECT deck_id FROM deck WHERE user_id = ?";

    private final String GET_ID_FROM_USER = "SELECT id FROM users WHERE username = ?";

    private final String SAVE_IN_USER = "UPDATE users SET deck_id = ? where id = ?";
    private final Database database = Database.getInstance();
    private final CardRepository cardRepository = new DatabaseCardRepository();

    @Override
    public void save(Deck deck, String user_id) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SAVE)
        ) {

            pstmt.setString(1, deck.getDeck_id());
            pstmt.setString(2, user_id);
            pstmt.execute();

        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
    }

    @Override
    public String getDeck_Id(String user_id){
        String deck_id = null;

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_DECK_ID)
        ) {
            pstmt.setString(1, user_id);
            try (ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    deck_id = rs.getString("deck_id");
                }
            }
        }catch (SQLException e){
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return deck_id;

    }

    @Override
    public String getIdFromUser(String username){
        String id = null;

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_ID_FROM_USER)
        ) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    id = rs.getString("id");
                }
            }
        }catch (SQLException e){
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return id;

    }

    @Override
    public List <String> findAll(String deck_id){
        List<String> cardsInDeck = new ArrayList<>();
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_ALL)
        ) {
            pstmt.setString(1, deck_id);
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    String card = rs.getString("card_id");
                    cardsInDeck.add(card);
                }
            }
        }catch (SQLException e){
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return cardsInDeck;

    }

    @Override
    public void saveDeckInUser(String deck_id, String user_id){
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SAVE_IN_USER)
        ) {
            pstmt.setString(1, deck_id);
            pstmt.setString(2, user_id);
            pstmt.execute();
        }catch (SQLException e){
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }


    }




}
