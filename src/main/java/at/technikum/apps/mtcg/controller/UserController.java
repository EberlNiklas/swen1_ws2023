package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.service.CardService;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.incubator.vector.VectorOperators;

import java.util.Optional;

public class UserController extends AbstractController {

    private final UserService userService;

    private final ObjectMapper objectMapper;

    public UserController(UserService userService) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public boolean supports(String route) {
        return route.equals("/users");
    }

    @Override
    public Response handle(Request request) {
        if (request.getRoute().equals("/users")) {

            if(request.getMethod().equals("POST")){
                return create(request);
            }
            switch (request.getMethod()) {
                case "GET":
                    return createWhenGET(request);
                case "POST":
                    return createWhenPOST(request.getBody());
            }

        }
            // THOUGHT: better 405
            return status(HttpStatus.BAD_REQUEST);

    }


    public Response create(Request request) {

        if (!request.getContentType().equals("application/json")) {
            return badRequest(HttpStatus.BAD_REQUEST);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        User user;
        try {
            user = objectMapper.readValue(request.getBody(), User.class);
        } catch (JsonProcessingException e) {
            return badRequest(HttpStatus.BAD_REQUEST);
        }

        user = userService.register(user);

        if(user == null) {
            return notFound(HttpStatus.NOT_FOUND);
        }

        String userJson;
        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            return internalServerError(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return json(HttpStatus.CREATED, userJson);
    }

}