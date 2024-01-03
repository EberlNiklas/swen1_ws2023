package at.technikum.server.http;

// THOUGHT: Add new relevant status (https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
public enum HttpStatus {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),

    CREATED(201, "Created"), //new resource such as user or card is successfully created

    ACCEPTED(202, "Accepted"), //request has been accepted for processing but not yet completed.

    BAD_REQUEST (400, "Bad Request"), // server cannot understand the request

    UNAUTHORIZED (401, "Unauthorized"), // authentication  has failed or has not been provided

    FORBIDDEN (403, "Forbidden"), //The client does not have access rights to the content

    NOT_ALLOWED (405, "Not Allowed"), //The request method is known by the server but is not supported by the target resource

    CONFLICT (409, "Conflict"), //request conflicts with the current state of the server

    INTERNAL_SERVER_ERROR (500, "Internal Server Error"), //The server has encountered a situation it does not know how to handle

    INSUFFICIENT_COINS (402, "Payment Required - Insufficient Coins"), //user does not have enough coins for a purchase

    DECK_INCOMPLETE (422, "Unprocessable Content - Incomplete Deck"); //user tries to battle with an incomplete deck


    private final int code;
    private final String message;

    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
