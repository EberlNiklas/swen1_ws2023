package at.technikum.apps.mtcg.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//service that provides logic for login of a user
public class SessionService {

    private static Map<String,String> valid = new HashMap<>();
    public String generateToken(String username){
        String token = username + "-mtcgToken";
        valid.put(token, username);
        return token;
    }
    public boolean isLoggedInAsAdmin(String token){
        if (isLoggedIn(token)) {
            return valid.get(token).equals("admin");
        }
        return false;
    }
    public boolean isLoggedIn(String token){
        return valid.containsKey(token);
    }
}
