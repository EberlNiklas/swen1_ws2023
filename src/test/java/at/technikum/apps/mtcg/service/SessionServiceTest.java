package at.technikum.apps.mtcg.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionServiceTest {


    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        sessionService = new SessionService();
    }

    @Test
    void generateToken_validUsername_tokenGenerated() {
        // Arrange
        String username = "testUser";

        // Act
        String token = sessionService.generateToken(username);

        // Assert
        assertNotNull(token);
        assertTrue(token.startsWith(username));
        assertTrue(token.endsWith("-mtcgToken"));
    }

    @Test
    void testIsLoggedIn() {
        // Arrange
        String username = "testUser";

        // Act
        String token = sessionService.generateToken(username);
        boolean result = sessionService.isLoggedIn(token);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsLoggedInAsAdmin() {
        // Arrange
        String username = "admin";

        // Act
        String token = sessionService.generateToken(username);
        boolean result = sessionService.isLoggedInAsAdmin(token);

        // Assert
        assertTrue(result);
    }





}
