package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.User;

import java.util.List;
import java.util.Optional;

//handles the communication with the user database
public class DatabaseUserRepository implements UserRepository{
    //TODO

    public DatabaseUserRepository() {
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> find(int id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User delete(User user) {
        return null;
    }
}
