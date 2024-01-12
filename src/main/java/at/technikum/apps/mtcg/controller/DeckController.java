package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.service.PackageService;
import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.apps.mtcg.service.StackService;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class DeckController extends AbstractController {

    private final SessionService sessionService;

    private final PackageService packageService;

    private final StackService stackService;

    public DeckController() {
        this.sessionService = new SessionService();
        this.packageService = new PackageService();
        this.stackService = new StackService();
    }

    @Override
    public boolean supports(String route) {
        return route.equals("/deck");
    }

    @Override
    public Response handle(Request request) {
        if (supports(request.getRoute())) {
            switch (request.getMethod()) {
                case "GET":
                    return readAll(request, user);
                case "PUT":
                    return configure(request, user);
            }
            return notAllowed(HttpStatus.NOT_ALLOWED);
        }
        return notAllowed(HttpStatus.NOT_ALLOWED);
    }

}
