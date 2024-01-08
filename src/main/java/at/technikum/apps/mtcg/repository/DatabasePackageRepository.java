package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.Package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class DatabasePackageRepository implements PackageRepository{

    private final String FIND_ALL_SQL = "SELECT * FROM packages";
    private final String SAVE = "INSERT INTO packages(id, user_id) VALUES(?, ?)";

    private final String GET_COINS_FROM_USER = "SELECT coins FROM users WHERE username = ?";

    private final String GET_ID_FROM_USER = "SELECT id FROM users WHERE username = ?";
    private final String GET_ID_FROM_PACKAGE = "SELECT * FROM packages ORDER BY RANDOM() LIMIT 1";
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
            // THOUGHT: how do i handle exceptions (hint: look at the TaskApp)
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
    public Package update(Package oldPkg, Package newPkg) {
        return null;
    }
}
