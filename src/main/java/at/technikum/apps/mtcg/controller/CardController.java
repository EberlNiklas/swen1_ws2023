package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.service.CardService;
import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class CardController extends AbstractController {

    private final CardService cardService;

    private final UserService userService;

    private final SessionService sessionService;

    public CardController(CardService cardService, UserService userService, SessionService sessionService) {
        this.cardService = cardService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/cards");
    }

    @Override
    public Response handle(Request request) {
        if (request.getRoute().equals("/cards")) {
            if (!isLoggedIn(request)) {
                return unauthorized();
            }
            if (request.getMethod().equals("GET")) {
                return read(request);
            }

            // THOUGHT: better 405
            return notAllowed();
        }

        // THOUGHT: better 405
        return notAllowed();
    }

    public Response readAll(Request request) {

        List<Card> cards = cardService.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        String cardJson = null;
        try {
            cardJson = objectMapper.writeValueAsString(cards);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json(HttpStatus.CREATED, cardJson);
    }

    public Response read(Request request) {
        List<Card> cards = new ArrayList<>();
        String token = request.getHttpHeader();
        String username = extractUsername(token);
        token = token.split(" ")[1];
        String user_id = userService.getIdFromUser(username);

        if(sessionService.isLoggedIn(token)){
            cards = cardService.findAllCardsFromUser(user_id);
            ObjectMapper objectMapper = new ObjectMapper();
            String cardJson = null;
            try {
                cardJson = objectMapper.writeValueAsString(cards);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return json(HttpStatus.CREATED, cardJson);


        }
        return badRequest();
    }

    public Response update(int id, Request request) {
        return null;
    }

    public Response delete(int id, Request request) {
        return null;
    }
}