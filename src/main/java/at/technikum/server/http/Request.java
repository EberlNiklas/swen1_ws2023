package at.technikum.server.http;

public class Request {


    //GET, POST, PUT, DELETE
    private String method;

    // /, /home, /package
    private String route;

    // z.B.: JSON
    private String contentType;

    // 0, 17
    private int contentLength;

    // Informationen die im Body stehen, werden vom Server an die Anwendung weitergegeben
    private String body;


    public void setMethod(HttpMethod httpMethod) {
        this.method = httpMethod.getMethod();
    }

}
