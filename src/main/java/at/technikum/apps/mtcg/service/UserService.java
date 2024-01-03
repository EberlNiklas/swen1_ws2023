package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.DatabaseUserRepository;

import java.util.UUID;

//service that lets you create and edit users
public class UserService {
    //TODO

    private final DatabaseUserRepository userRepository;

    public UserService(DatabaseUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }



    public User save(User user) {
        user.setId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }


}
