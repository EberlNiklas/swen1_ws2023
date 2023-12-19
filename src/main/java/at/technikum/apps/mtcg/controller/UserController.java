package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.service.CardService;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class UserController implements Controller {

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
            switch (request.getMethod()) {
                case "GET": return readAll(request);
                case "POST": return create(request);
            }

            // THOUGHT: better 405
            return status(HttpStatus.BAD_REQUEST);
        }

        // get id e.g. from /tasks/1
        String[] routeParts = request.getRoute().split("/");
        int cardId = Integer.parseInt(routeParts[2]);

        switch (request.getMethod()) {
            case "GET": return read(cardId, request);
            case "PUT": return update(cardId, request);
            case "DELETE": return delete(cardId, request);
        }

        // THOUGHT: better 405
        return status(HttpStatus.BAD_REQUEST);
    }
}