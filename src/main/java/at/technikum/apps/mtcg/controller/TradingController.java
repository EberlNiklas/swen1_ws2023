package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.Trade;
import at.technikum.apps.mtcg.entity.User;
import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.apps.mtcg.service.TradingService;
import at.technikum.apps.mtcg.service.UserService;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class TradingController extends AbstractController{

    private final SessionService sessionService;

    private final TradingService tradingService;

    private final UserService userService;

    public TradingController(){
        this.sessionService = new SessionService();
        this.tradingService = new TradingService();
        this.userService = new UserService();
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/tradings");
    }

    @Override
    public Response handle(Request request) {
        if (supports(request.getRoute())) {
            String[] routeParts = request.getRoute().split("/");
            if (routeParts.length > 2) {
                String trade_id = routeParts[2];
                switch (request.getMethod()) {
                    case "POST":
                        return trade(request, trade_id);
                    case "DELETE":
                        return delete(request, trade_id);
                }
            } else {
                switch (request.getMethod()) {
                    case "GET":
                        return getDeals(request);
                    case "POST":
                        return createDeal(request);
                }

            }
            return notAllowed();
        }
        return notAllowed();
    }

    public Response trade(Request request, String trade_id) {
        if (request.getContentType().equals("application/json")) {
            String token = request.getHttpHeader();
            String username = extractUsername(token);
            token = token.split(" ")[1];
            String user_id = userService.getIdFromUser(username);
            String checkIfSelf = tradingService.getDealerUserIdFromTrade(trade_id);
            Trade tradeToBeUpdated;

            if (sessionService.isLoggedIn(token)) {

                if (checkIfSelf.equals(user_id)) {
                    return json(HttpStatus.NOT_ALLOWED, "Can't trade with yourself!");
                }

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                String cardId;
                try {
                    cardId = objectMapper.readValue(request.getBody(), String.class);
                } catch (JsonProcessingException e) {
                    return badRequest();
                }

                Trade trade = tradingService.getSpecificTrade(trade_id);

                if (tradingService.checkIfCardIsInDeck(cardId)) {
                    return json(HttpStatus.NOT_ALLOWED, "Card can't be in Deck!");
                }

                int damage = tradingService.getDamageFromCard(cardId);
                String type = tradingService.getTypeFromCard(cardId);

                if (damage < trade.getMinimumDamage() || (!type.equalsIgnoreCase(trade.getType()))) {
                    return json(HttpStatus.NOT_ALLOWED, "Card is not matching the minimum requirements for this trade!");
                }

                tradeToBeUpdated = tradingService.updateTrade(trade, trade_id, user_id, cardId);
                tradingService.tradeCards(tradeToBeUpdated.getCustomerUserId(), tradeToBeUpdated.getDealerCardId());
                tradingService.tradeCards(tradeToBeUpdated.getDealerUserId(), tradeToBeUpdated.getCustomerCardId());

                String userJson;
                try {
                    userJson = objectMapper.writeValueAsString(tradeToBeUpdated);
                } catch (JsonProcessingException e) {
                    return internalServerError();
                }

                return json(HttpStatus.OK, userJson);

            } else {
                return unauthorized();
            }
        }
        return badRequest();
    }

    public Response delete(Request request, String trade_id){
        String token = request.getHttpHeader();
        String username = extractUsername(token);
        token = token.split(" ")[1];


        if (sessionService.isLoggedIn(token)) {
            try{
                tradingService.deleteTrade(trade_id);
            } catch(Exception e){
                return internalServerError();
            }
            return json(HttpStatus.OK, "Trade deleted!");
        }else{
            return notAllowed();
        }
    }

    public Response getDeals(Request request) {
        String token = request.getHttpHeader();
        String username = extractUsername(token);
        token = token.split(" ")[1];

        if (sessionService.isLoggedIn(token)) {
            List<Trade> openTrades = tradingService.findAllOpenTrades();

            ObjectMapper objectMapper = new ObjectMapper();

            String tradeJson;
            try {
                tradeJson = objectMapper.writeValueAsString(openTrades);
            } catch (JsonProcessingException e) {
                return internalServerError();
            }
            return json(HttpStatus.OK, tradeJson);
        }else{
            return unauthorized();
        }
    }


    public Response createDeal(Request request){
        if (request.getContentType().equals("application/json")) {
            String token = request.getHttpHeader();
            String username = extractUsername(token);
            token = token.split(" ")[1];
            String user_id = userService.getIdFromUser(username);

            if (sessionService.isLoggedIn(token)) {

                ObjectMapper objectMapper = new ObjectMapper();
                Trade trade;
                try {
                    trade = objectMapper.readValue(request.getBody(), Trade.class);
                } catch (JsonProcessingException e) {
                    return badRequest();
                }

                if(tradingService.checkIfCardIsInDeck(trade.getDealerCardId())){
                    return json(HttpStatus.NOT_ALLOWED, "Card is in Deck!");
                }

                trade = tradingService.saveTrade(trade, user_id);

                if(trade == null){
                    return notFound();
                }

                String tradeJson;
                try {
                    tradeJson = objectMapper.writeValueAsString(trade);
                } catch (JsonProcessingException e) {
                    return internalServerError();
                }

                return json(HttpStatus.CREATED, tradeJson);
            }

        }else{
            return badRequest();
        }
        return badRequest();
    }


}
