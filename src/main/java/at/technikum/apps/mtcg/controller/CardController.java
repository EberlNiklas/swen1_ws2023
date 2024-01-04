package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.service.CardService;
import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class CardController extends AbstractController {

    private final CardService cardService;

    public CardController() {
        this.cardService = new CardService();
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/cards");
    }

    @Override
    public Response handle(Request request) {

        if (request.getRoute().equals("/cards")) {
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

    public Response create(Request request) {

        ObjectMapper objectMapper = new ObjectMapper();
        Card card = null;
        try {
            card = objectMapper.readValue(request.getBody(), Card.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // task = toObject(request.getBody(), Task.class);

        card = cardService.save(card);

        String cardJson = null;
        try {
            cardJson = objectMapper.writeValueAsString(card);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json(HttpStatus.CREATED, cardJson);
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

    public Response read(int id, Request request) {
        return null;
    }

    public Response update(int id, Request request) {
        return null;
    }

    public Response delete(int id, Request request) {
        return null;
    }
}
