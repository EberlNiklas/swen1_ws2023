package at.technikum.apps.mtcg;

import at.technikum.apps.mtcg.controller.*;
import at.technikum.server.ServerApplication;
import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

import java.util.ArrayList;
import java.util.List;

public class MtcgApp implements ServerApplication {

    private List<Controller> controllers = new ArrayList<>();

    public MtcgApp() {

        controllers.add(new UserController());
        controllers.add(new LoginController());
        controllers.add(new ScoreboardController());
        controllers.add(new CardController());
        controllers.add(new DeckController());
        controllers.add(new StatsController());
        controllers.add(new TransactionController());
    }

    @Override
    public Response handle(Request request) {

        for (Controller controller: controllers) {
            if (!controller.supports(request.getRoute())) {
                continue;
            }

            return controller.handle(request);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setBody("Route " + request.getRoute() + " not found in app!");

        return response;
    }
}