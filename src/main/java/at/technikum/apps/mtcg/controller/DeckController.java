package at.technikum.apps.mtcg.controller;

import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class DeckController implements Controller{

    @Override
    public boolean supports(String route) {
        return route.equals("/deck"); //show unconfigured deck / configure deck
    }

    @Override
    public Response handle(Request request) {
        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setBody("deck controller");

        return response;
    }
}
