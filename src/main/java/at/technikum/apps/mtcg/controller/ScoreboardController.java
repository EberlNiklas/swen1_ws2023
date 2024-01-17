package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

public class ScoreboardController extends AbstractController{

    private final SessionService sessionService;

    private final UserService userService;

    public ScoreboardController() {
        this.sessionService = new SessionService();
        this.userService = new UserService();
    }

    @Override
    public boolean supports(String route) {
        return route.equals("/scoreboard");
    }

    @Override
    public Response handle(Request request) {
        if (request.getRoute().startsWith("/scoreboard")) {
            if (request.getMethod().equals("GET")) {
                return getScoreboard(request);
            }
        }else {
            return badRequest();
        }
        return badRequest();
    }

    public Response getScoreboard(Request request){

        String token = request.getHttpHeader();
        String username = extractUsername(token);
        token = token.split(" ")[1];

        if(sessionService.isLoggedIn(token)){

            List<Integer> scoreboard = userService.getScoreboard();
            scoreboard.sort(Collections.reverseOrder());

            ObjectMapper objectMapper = new ObjectMapper();
            String userJson;
            try {
                userJson = objectMapper.writeValueAsString(scoreboard);
            } catch (JsonProcessingException e) {
                return internalServerError();
            }
            return json(HttpStatus.OK, userJson);
        }else {
            return unauthorized();
        }
    }
}

