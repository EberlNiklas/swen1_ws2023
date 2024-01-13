package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.repository.DatabaseUserRepository;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserController extends AbstractController {

    private final UserService userService;


    public UserController() {
        this.userService = new UserService();
    }

    @Override
    public boolean supports(String route) {
        return route.equals("/users");
    }

    @Override
    public Response handle(Request request) {
        if (request.getRoute().equals("/users")) {

            if (request.getMethod().equals("POST")) {
                return create(request);
            }

            String[] route = request.getRoute().split("/");
            String user = route[2];

            switch (request.getMethod()) {
                case "GET":
                    return readAll(request, user);
                case "PUT":
                    return update(request, user);
            }
            return notAllowed(HttpStatus.NOT_ALLOWED);
        }
        return notAllowed(HttpStatus.NOT_ALLOWED);

    }


    public Response create(Request request) {

        if (!request.getContentType().equals("application/json")) {
            return badRequest(HttpStatus.BAD_REQUEST);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        User user;
        try {
            user = objectMapper.readValue(request.getBody(), User.class);
        } catch (JsonProcessingException e) {
            return badRequest(HttpStatus.BAD_REQUEST);
        }

        String checkIfExists = userService.findUserString(user.getUsername());
        if (checkIfExists != null) {
            return badRequest(HttpStatus.BAD_REQUEST);
        }
        user = userService.registration(user);

        if (user == null) {
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

    public Response readAll(Request request, String user) {
        String token = request.getHttpHeader();
        String username = extractUsername(token);

        if (!user.equals(username)) {
            return badRequest(HttpStatus.BAD_REQUEST);
        }

        if (!userService.isValid(username)) {
            return notAllowed(HttpStatus.NOT_ALLOWED);
        }

        User userFound = userService.findByUsername(username);

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson;
        try {
            userJson = objectMapper.writeValueAsString(userFound);
        } catch (JsonProcessingException e) {
            return internalServerError(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return internalServerError(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Response update(Request request, String user) {
        if (!request.getContentType().equals("application/json")) {
            return badRequest(HttpStatus.BAD_REQUEST);
        }

        String token = request.getHttpHeader();
        String username = extractUsername(token);

        if (!user.equals(username)) {
            return badRequest(HttpStatus.BAD_REQUEST);
        }

        if (!userService.isValid(username)) {
            return notAllowed(HttpStatus.NOT_ALLOWED);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        User updatedUser;
        try {
            updatedUser = objectMapper.readValue(request.getBody(), User.class);
        } catch (JsonProcessingException e) {
            return badRequest(HttpStatus.BAD_REQUEST);
        }

        User player = userService.update(updatedUser, username);
        String userJson;

        try {
            userJson = objectMapper.writeValueAsString(player);
        } catch (JsonProcessingException e) {
            return internalServerError(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return json(HttpStatus.OK, userJson);
    }
}