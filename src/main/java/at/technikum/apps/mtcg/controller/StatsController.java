package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StatsController extends AbstractController {

    private final SessionService sessionService;

    private final UserService userService;

    public StatsController(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @Override
    public boolean supports(String route) {
        return route.equals("/stats");
    }

    @Override
    public Response handle(Request request) {
        if (request.getRoute().startsWith("/stats")) {
            if (request.getMethod().equals("GET")) {
                return getUserStats(request);
            }
        }else {
            return badRequest();
        }
        return badRequest();
    }

    public Response getUserStats(Request request){

            String token = request.getHttpHeader();
            String username = extractUsername(token);
            token = token.split(" ")[1];

            if(sessionService.isLoggedIn(token)){

                int userStats = userService.getStats(username);

                ObjectMapper objectMapper = new ObjectMapper();
                String userJson;
                try {
                    userJson = objectMapper.writeValueAsString(userStats);
                } catch (JsonProcessingException e) {
                    return internalServerError();
                }
                return json(HttpStatus.OK, userJson);
            }else {
                return unauthorized();
            }
    }
}
