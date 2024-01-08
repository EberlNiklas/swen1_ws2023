package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.Package;
import at.technikum.apps.mtcg.entity.Stack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseStackRepository implements StackRepository{

    private final String FIND_ALL = "SELECT * FROM stack";

    private final String INSERT = "INSERT INTO stack(card_id, user_id) VALUES(?, ?)";
    private final Database database = Database.getInstance();

    @Override
    public List<Stack> findAll() {
        List<Stack> all = new ArrayList<>();
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_ALL)
        ) {
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    Stack stack = new Stack(
                            rs.getString("user_id"),
                            rs.getString("card_id"));
                    all.add(stack);
                }
            }
        }catch (SQLException e){
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }
        return all;

    }

    @Override
    public void saveCardsIntoStack(String user_id, String card_id) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(INSERT)
        ) {
            pstmt.setString(1, card_id);
            pstmt.setString(2, user_id);
            pstmt.execute();
        }catch (SQLException e){
            System.err.println("SQL Exception! Message: " + e.getMessage());
        }

    }

}
