package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


//handles the communication with the user database
public class DatabaseUserRepository implements UserRepository{
    //TODO

    private final String FIND_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private final String FIND_BY_USERNAME_AND_PASSWORD = "SELECT * FROM users WHERE username = ? AND password = ?";
    private final String SAVE = "INSERT INTO users(id, username, password, points, coins, bio, image, name) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE users SET bio = ?, image = ?, name = ? WHERE username = ?";
    private final String GET_POINTS_SQL = "SELECT points FROM users WHERE username = ?";
    private final String GET_ALL_POINTS_SQL = "SELECT points FROM users";
    private final String TOKEN_SQL = "SELECT username FROM users WHERE username = ?";

    private final String GET_ID_FROM_USER = "SELECT id FROM users WHERE username = ?";
    private final Database database = Database.getInstance();



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
            pstmt.setString(2, securePassword(password));

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
            pstmt.setString(3, securePassword(user.getPassword()));
            pstmt.setInt(4, 100);
            pstmt.setInt(5, 20);
            pstmt.setString(6, user.getBio());
            pstmt.setString(7, user.getImage());
            pstmt.setString(8, user.getName());


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
    public int getStats(String username) {

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

    public List<Integer> getScoreboard() {
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

    public String securePassword(String password){
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
