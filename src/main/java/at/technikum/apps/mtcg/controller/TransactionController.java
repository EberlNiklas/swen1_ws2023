package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.service.PackageService;
import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class TransactionController extends AbstractController{

    private final SessionService sessionService;

    private final PackageService packageService;

    public TransactionController(SessionService sessionService, PackageService packageService) {
        this.sessionService = sessionService;
        this.packageService = packageService;
    }

    @Override
    public boolean supports(String route) {
        return route.equals("/transactions/packages");
    }

    @Override
    public Response handle(Request request) {
        if (supports(request.getRoute())) {
            if (request.getMethod().equals("POST")) {
                return buyPackage(request);
            }
            return notAllowed(HttpStatus.NOT_ALLOWED);
        }
        return notAllowed(HttpStatus.NOT_ALLOWED);
    }

    public Response buyPackage(Request request){

        if(request.getContentType().equals("application/json")){
            String token= request.getHttpHeader();
            String username = extractUsername(token);

            if(sessionService.isLoggedIn(token)){
                int costs = 5;
                int user_coins = packageService.getCoinsFromUser(username);

                if(user_coins >= costs){

                    String user_id = packageService.getIdFromUser(username);

                }
            }
        }
    }
}
