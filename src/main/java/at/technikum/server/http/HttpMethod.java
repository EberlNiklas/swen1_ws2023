package at.technikum.server.http;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String method;
    public static final String GetMethod = "GET";
    public static final String PostMethod = "POST";
    public static final String PutMethod = "PUT";
    public static final String DeleteMethod = "DELETE";

    //Konstruktor für Enum Wert
    HttpMethod(String method) {
        this.method = method;
    }

    //Getter, um Http Methode als String abzurufen
    public String getMethod() {
        return method;
    }


    //Http Request String in HttpMethod-Enum
    //--THOUGHT: Maybe some better place for this logic?
    public static HttpMethod getHttpMethod(String httpRequest) {
        String httpMethod = httpRequest.split(" ")[0];

        //--THOUGHT: Use constants instead of hardcoded strings
        return switch (httpMethod) {
            case GetMethod -> GET;

            case PostMethod -> POST;

            case PutMethod -> PUT;

            case DeleteMethod -> DELETE;

            default -> throw new RuntimeException("No HTTP Method");
        };
    }
}
