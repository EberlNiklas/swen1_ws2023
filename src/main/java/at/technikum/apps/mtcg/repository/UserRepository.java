package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {


    Optional<User> login(String username, String password);

    User update(User user, String username);

    int getStats(String username);

    List<Integer> getScoreboard();

    User findByUsername(String username);

    String findUserString(String username);

    User save(User user);

    String getIdFromUser(String username);

}
