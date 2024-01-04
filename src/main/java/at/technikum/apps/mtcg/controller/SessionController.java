package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.DatabaseUserRepository;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class SessionController extends AbstractController{

    private final UserService userService;

    public SessionController() {
        this.userService = new UserService(new DatabaseUserRepository());
    }

    @Override
    public boolean supports(String route) {
        return route.equals("/sessions");
    }

    @Override
    public Response handle(Request request) {

        if(request.getRoute().equals("/sessions")){
            if(request.getMethod().equals("POST")){
                String text = request.getBody();
                String username = text.split("\"Username\":\"")[1].split("\"")[0];
                String password = text.split("\"Password\":\"")[1].split("\"")[0];
                return userLogin(request, username, password);
            }
        }
        return notAllowed(HttpStatus.NOT_ALLOWED);
    }

    public Response userLogin(Request request, String username, String password){
        if(request.getContentType().equals("application/json")){
            Optional<User> user = userService.login(username, password);
            if(user.isEmpty()){
                return notFound(HttpStatus.NOT_FOUND);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String userJson;
            try {
                userJson = objectMapper.writeValueAsString(user.get());
            }catch (JsonProcessingException e){
                return internalServerError(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return json(HttpStatus.ACCEPTED, userJson);
        }
        return badRequest(HttpStatus.BAD_REQUEST);
    }

}
