package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//handles the communication with the user database
public class DatabaseUserRepository implements UserRepository{
    //TODO

    private final String FIND_BY_USERNAME = "SELECT username FROM users WHERE username = ?";
    private final String FIND_BY_USERNAME_AND_PASSWORD = "SELECT * FROM users WHERE username = ? AND password = ?";
    private final String SAVE = "INSERT INTO users(id, username, password, points, coins, deck_id) VALUES(?, ?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE users SET bio = ?, image = ?, name = ? WHERE username = ?";
    private final String GET_POINTS_SQL = "SELECT points FROM users WHERE username = ?";
    private final String GET_ALL_POINTS_SQL = "SELECT points FROM users";
    private final String TOKEN_SQL = "SELECT username FROM users WHERE username = ?";
    private final Database database = new Database();


    @Override
    public boolean isValid(String username){
        String foundUsername = null;

        try(
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(TOKEN_SQL);
                ){
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    foundUsername = rs.getString("username");
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return foundUsername != null;
    }



    @Override
    public User findByUsername(String username) {
        User user = null;

        try(
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_BY_USERNAME);
        ){
          pstmt.setString(1, username);
          try (ResultSet rs = pstmt.executeQuery()){
              if(rs.next()){
                  user = new User(
                        rs.getString("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("points"),
                        rs.getInt("coins"),
                        rs.getString("deck_id"),
                        rs.getString("bio"),
                        rs.getString("image"),
                        rs.getString("name"));
                  }
              }
              }catch (SQLException e){
                System.err.println("SQL Exception! Message: " + e.getMessage());
          }
        return user;
    }

    public String findUserString(String username) {
        String user = null;

        try(
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_BY_USERNAME);
        ){
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    user = rs.getString("username");
                }
            }
        }catch (SQLException e){
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return user;
    }

    @Override
    public Optional<User> login(String username, String password){
        User user = null;

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_BY_USERNAME_AND_PASSWORD);
        ){
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    user = new User(
                            rs.getString("user_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("points"),
                            rs.getInt("coins"),
                            rs.getString("deck_id"),
                            rs.getString("bio"),
                            rs.getString("image"),
                            rs.getString("name"));
                }
            }
        }catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User save(User user) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SAVE)
        ) {
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setInt(4, 100);
            pstmt.setInt(5, 20);
            pstmt.setString(6, user.getDeckID());


            pstmt.execute();
        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }

        return user;
    }
    @Override
    public User update(User user, String username) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(UPDATE)
        ) {
            pstmt.setString(1, user.getBio());
            pstmt.setString(2, user.getImage());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, username);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }

        return user;
    }
    @Override
    public int findStats(String username) {

        int stats = 0;
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_POINTS_SQL)
        ) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    stats = rs.getInt("points");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        }
        return stats;
    }

    public List<Integer> sortedPointsList() {
        List<Integer> list = new ArrayList<>();
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(GET_ALL_POINTS_SQL);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                int elo = rs.getInt("points");
                list.add(elo);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        }
        return list;
    }

}
