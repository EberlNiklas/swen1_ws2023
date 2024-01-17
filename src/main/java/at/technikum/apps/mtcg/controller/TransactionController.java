package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.service.PackageService;
import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.apps.mtcg.service.StackService;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

import java.util.List;

public class TransactionController extends AbstractController {

    private final SessionService sessionService;

    private final PackageService packageService;

    private final StackService stackService;

    public TransactionController() {
        this.sessionService = new SessionService();
        this.packageService = new PackageService();
        this.stackService = new StackService();
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
            return notAllowed();
        }
        return notAllowed();
    }

    public Response buyPackage(Request request) {

        if (request.getContentType().equals("application/json")) {
            String token = request.getHttpHeader();
            String username = extractUsername(token);
            token = token.split(" ")[1];

            if (sessionService.isLoggedIn(token)) {
                int costs = 5;
                int user_coins = packageService.getCoinsFromUser(username);

                if (user_coins >= costs) {

                    String user_id = packageService.getIdFromUser(username);
                    String package_id = packageService.getIdFromPackage();

                    if (package_id == null) {
                        return json(HttpStatus.NOT_FOUND, "No more packages available for purchase!");
                    }
                    List<String> cardsInPackage = packageService.getCardsFromPackage(package_id);

                    for (String card_id : cardsInPackage) {
                        stackService.saveCardsIntoStack(user_id, card_id);
                    }

                    packageService.updateCoins(username, costs);
                    packageService.delete(package_id);

                    return ok();
                } else {
                    return json(HttpStatus.BAD_REQUEST, "User has no coins left!");
                }
            } else {
                return notAllowed();
            }
        }
        return badRequest();
    }

}
