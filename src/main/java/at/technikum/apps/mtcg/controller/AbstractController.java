package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public abstract class AbstractController {

    public abstract boolean supports(String route);

    public abstract Response handle(Request request);

    private final SessionService sessionService = new SessionService();

    protected Response status(HttpStatus httpStatus) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"error\": \""+ httpStatus.getMessage() + "\"}");

        return response;
    }

    protected Response created(HttpStatus httpStatus) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Created\": \""+ HttpStatus.CREATED + "\"}");

        return response;
    }

    protected Response ok() {
        Response response = new Response();
        response.setStatus(HttpStatus.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"ok\": \""+ HttpStatus.OK + "\"}");

        return response;
    }

    protected Response notAllowed() {
        Response response = new Response();
        response.setStatus(HttpStatus.NOT_ALLOWED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Not Allowed\": \""+ HttpStatus.NOT_ALLOWED + "\"}");

        return response;
    }

    protected Response badRequest() {
        Response response = new Response();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Bad Request\": \""+ HttpStatus.BAD_REQUEST + "\"}");

        return response;
    }

    protected Response accepted() {
        Response response = new Response();
        response.setStatus(HttpStatus.ACCEPTED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Accepted\": \""+ HttpStatus.ACCEPTED + "\"}");

        return response;
    }

    protected Response notFound() {
        Response response = new Response();
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Not Found\": \""+ HttpStatus.NOT_FOUND + "\"}");

        return response;
    }

    protected Response unauthorized() {
        Response response = new Response();
        response.setStatus(HttpStatus.UNAUTHORIZED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Unauthorized\": \""+ HttpStatus.UNAUTHORIZED + "\"}");

        return response;
    }

    protected Response json(HttpStatus httpStatus, String jsonResponse) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody(jsonResponse);

        return response;
    }

    protected Response internalServerError() {
        Response response = new Response();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Bad Request\": \""+ HttpStatus.INTERNAL_SERVER_ERROR + "\"}");

        return response;
    }

    protected String extractUsername(String header) {
        String[] parts = header.split(" ");
        if (parts.length == 2 && parts[0].equals("Bearer")) {
            String[] subParts = parts[1].split("-");
            if (subParts.length == 2) {
                return subParts[0];
            }
        }
        return null;
    }

    protected String extractToken(String header){
        if (header == null){
            return null;
        }
        String[] sections = header.split(" ");
        if (sections.length == 2 && sections[0].equals("Bearer")){
            return sections[1];
        }
        return null;
    }

    protected boolean isLoggedIn(Request request){
        return sessionService.isLoggedIn(extractToken(request.getHttpHeader()));
    }
    protected boolean isLoggedInAsAdmin(Request request){
        return sessionService.isLoggedInAsAdmin(extractToken(request.getHttpHeader()));
    }
}
