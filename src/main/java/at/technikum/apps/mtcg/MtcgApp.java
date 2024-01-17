package at.technikum.apps.mtcg;

import at.technikum.apps.mtcg.controller.*;
import at.technikum.apps.mtcg.entity.Stats;
import at.technikum.apps.mtcg.service.Injector;
import at.technikum.server.ServerApplication;
import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

import java.util.ArrayList;
import java.util.List;

public class MtcgApp implements ServerApplication {

    private List<AbstractController> controllers = new ArrayList<>();

    public MtcgApp() {

        Injector injector = new Injector();

        this.controllers = injector.createController();
    }

    @Override
    public Response handle(Request request) {

        for (AbstractController controller: controllers) {
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