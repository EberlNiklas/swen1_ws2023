package at.technikum.apps.mtcg.service;

import at.technikum.apps.mtcg.repository.DatabaseUserRepository;

//service that lets you create and edit users
public class UserService {
    //TODO

    private final DatabaseUserRepository userRepository;

    public UserService(){
        this.userRepository = new DatabaseUserRepository();
    }
}
