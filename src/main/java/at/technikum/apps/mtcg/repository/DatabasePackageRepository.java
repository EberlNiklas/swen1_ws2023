package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.Package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DatabasePackageRepository implements PackageRepository{

    private final String FIND_ALL_SQL = "SELECT * FROM packages";
    private final String SAVE = "INSERT INTO packages(id, user_id) VALUES(?, ?)";

    private final String GET_COINS_FROM_USER = "SELECT coins FROM users WHERE username = ?";

    private final String GET_ID_FROM_USER = "SELECT id FROM users WHERE username = ?";
    private final String GET_ID_FROM_PACKAGE = "SELECT * FROM packages LIMIT 1";

    private final String GET_CARDS = "SELECT id FROM card WHERE package_id = ?";

    private final String UPDATE_USER_COINS = "UPDATE users SET coins = coins - ? WHERE username = ?";

    private final String DELETE = "DELETE from packages WHERE id = ?";
    private final Database database = Database.getInstance();
    private final CardRepository cardRepository = new DatabaseCardRepository();
    @Override
    public Package save(Package packageToBeCreated) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SAVE)
        ) {

            pstmt.setString(1, packageToBeCreated.getId());
            pstmt.setString(2, packageToBeCreated.getUserId());
            pstmt.execute();

            for (Card card:packageToBeCreated.getCards()) {
                card.setPackageId(packageToBeCreated.getId());
                cardRepository.save(card);
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return packageToBeCreated;
    }

    @Override
    public int getCoinsFromUser(String username){
        int user_coins = 0;

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_COINS_FROM_USER)
        ) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    user_coins = rs.getInt("coins");
                }
            }
        }catch (SQLException e){
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return user_coins;

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
    public String getIdFromPackage(){
        String id = null;

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_ID_FROM_PACKAGE)
        ) {
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
    public List<String> getCardsInPackage(String package_id){
        List<String> cards = new ArrayList<>();

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_CARDS)
        ) {
            pstmt.setString(1, package_id);
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    cards.add(rs.getString("id"));
                }
            }
        }catch (SQLException e){
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return cards;

    }

    @Override
    public void updateCoins(String username, int costs) {

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(UPDATE_USER_COINS)
        ) {
            pstmt.setInt(1, costs);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
    }

    @Override
    public void delete(String package_id) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(DELETE)
        ) {
            pstmt.setString(1, package_id);
            pstmt.execute();
        }catch (SQLException e){
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
    }
}
