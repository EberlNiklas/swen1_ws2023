package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setId(UUID.randomUUID().toString());
    }

    @Test
    public void testLogin() {
        // Define the behavior of the mock when the login method is called
        when(userRepository.login(anyString(), anyString())).thenReturn(Optional.of(user));

        Optional<User> result = userService.login("testUser", "testPassword");

        assertTrue(result.isPresent());
        assertEquals(user.getUsername(), result.get().getUsername());
    }

    @Test
    public void testLoginFail() {
        when(userRepository.login(anyString(), anyString())).thenReturn(Optional.empty());

        Optional<User> result = userService.login("testUser", "wrongPassword");

        assertTrue(result.isEmpty());
    }

    @Test
    public void testRegister() {
        when(userRepository.findUserString(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registration(user);

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    public void testRegisterFail() {
        when(userRepository.findUserString(anyString())).thenReturn(user);

        User result = userService.registration(user);

        assertNull(result);
    }

    @Test
    public void testUpdate() {
        User updatedPlayer = new User();
        updatedPlayer.setName("updatedName");
        updatedPlayer.setBio("updatedBio");
        updatedPlayer.setImage("updatedImage");
        updatedPlayer.setId(user.getId());

        when(userRepository.update(any(User.class), anyString())).thenReturn(updatedPlayer);

        User result = userService.update(updatedPlayer, "testUser");

        assertNotNull(result);
        assertEquals(updatedPlayer.getName(), result.getName());
        assertEquals(updatedPlayer.getBio(), result.getBio());
        assertEquals(updatedPlayer.getImage(), result.getImage());
    }
}
