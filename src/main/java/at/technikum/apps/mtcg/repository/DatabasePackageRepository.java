package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.Package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class DatabasePackageRepository implements PackageRepository{

    private final String FIND_ALL_SQL = "SELECT * FROM packages";
    private final String SAVE = "INSERT INTO packages(id, user_id) VALUES(?, ?)";
    private final Database database = Database.getInstance();
    private final CardRepository cardRepository = new DatabaseCardRepository();
    @Override
    public Package save(Package pkg) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SAVE)
        ) {

            pstmt.setString(1, pkg.getId());
            pstmt.setString(2, pkg.getUserId());
            pstmt.execute();

            for (Card card:pkg.getCards()) {
                card.setPackageId(pkg.getId());
                cardRepository.save(card);
            }

        } catch (SQLException e) {
            // THOUGHT: how do i handle exceptions (hint: look at the TaskApp)
        }
        return pkg;
    }


    @Override
    public Package update(Package oldPkg, Package newPkg) {
        return null;
    }
}
