package at.technikum.apps.mtcg.controller;

import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public abstract class AbstractController {

    public abstract boolean supports(String route);

    public abstract Response handle(Request request);

    protected Response status(HttpStatus httpStatus) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"error\": \""+ httpStatus.getMessage() + "\"}");

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

    protected Response ok(HttpStatus httpStatus) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"ok\": \""+ HttpStatus.OK + "\"}");

        return response;
    }

    protected Response notAllowed(HttpStatus httpStatus) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Not Allowed\": \""+ HttpStatus.NOT_ALLOWED + "\"}");

        return response;
    }

    protected Response created(HttpStatus httpStatus) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Created\": \""+ HttpStatus.CREATED + "\"}");

        return response;
    }

    protected Response badRequest(HttpStatus httpStatus) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Bad Request\": \""+ HttpStatus.BAD_REQUEST + "\"}");

        return response;
    }

    protected Response internalServerError(HttpStatus httpStatus) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Bad Request\": \""+ HttpStatus.INTERNAL_SERVER_ERROR + "\"}");

        return response;
    }

    protected Response accepted(HttpStatus httpStatus) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Accepted\": \""+ HttpStatus.ACCEPTED + "\"}");

        return response;
    }

    protected Response unauthorized(HttpStatus httpStatus) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Accepted\": \""+ HttpStatus.UNAUTHORIZED + "\"}");

        return response;
    }

    protected Response notFound(HttpStatus httpStatus) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody("{ \"Not Found\": \""+ HttpStatus.NOT_FOUND + "\"}");

        return response;
    }

    protected Response json(HttpStatus httpStatus, String jsonResponse) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setBody(jsonResponse);

        return response;
    }

    // THOUGHT: more functionality e.g. ok(), json(), etc

}
