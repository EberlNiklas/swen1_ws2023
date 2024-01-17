package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.data.Database;
import at.technikum.apps.mtcg.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DatabaseUserRepositoryTest {

    @Mock
    private Database mockDatabase;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private DatabaseUserRepository databaseUserRepository;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(mockDatabase.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        databaseUserRepository = new DatabaseUserRepository();
    }

    @Test
    public void testLogin() throws SQLException {
        String username = "testUser";
        String password = "testPassword";
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("id")).thenReturn("1");
        when(mockResultSet.getString("username")).thenReturn(username);
        when(mockResultSet.getString("password")).thenReturn(password);
        when(mockResultSet.getInt("points")).thenReturn(100);
        when(mockResultSet.getInt("coins")).thenReturn(20);
        when(mockResultSet.getString("bio")).thenReturn("bio");
        when(mockResultSet.getString("image")).thenReturn("image");
        when(mockResultSet.getString("name")).thenReturn("name");

        Optional<User> result = databaseUserRepository.login(username, databaseUserRepository.securePassword(password));

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        assertEquals(password, result.get().getPassword());
    }

    @Test
    public void testLoginFail() throws SQLException {
        String username = "wrongUser";
        String password = "wrongPassword";
        when(mockResultSet.next()).thenReturn(false);

        Optional<User> result = databaseUserRepository.login(username, databaseUserRepository.securePassword(password));

        assertTrue(result.isEmpty());
    }

    @Test
    public void testSave() throws SQLException {
        User player = new User("1", "testUser", "testPassword", 100, 20, "bio", "image", "name");

        User result = databaseUserRepository.save(player);

        assertEquals(player, result);
    }

   /* @Test
    public void testUpdate() throws SQLException {
        Player player = new Player("1", "testUser", "testPassword", 100, 20, "1", "bio", "image", "name");
        String name = "newName";

        Player result = databasePlayerRepository.update(player, name);

        assertEquals(player, result);
    }

    @Test
    public void testFindStats() throws SQLException {
        String username = "testUser";
        int elo = 100;

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("elo")).thenReturn(elo);

        int result = databasePlayerRepository.findStats(username);

        assertEquals(elo, result);
    }

    @Test
    public void testSortedEloList() throws SQLException {
        List<Integer> expectedList = Arrays.asList(100, 200, 300);


        when(mockResultSet.next()).thenReturn(true, true, true, false);
        when(mockResultSet.getInt("elo")).thenReturn(100, 200, 300);

        List<Integer> result = databasePlayerRepository.sortedEloList();

        assertEquals(expectedList, result);
    }*/
}
