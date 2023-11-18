package at.technikum.server.util;

import at.technikum.server.http.HttpMethod;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


//Konvertiert einen HTTP-Request-String in ein Request-Objekt
// --THOUGHT: Maybe divide the HttpMatter into two classes (single responsibility)
// THOUGHT: Dont use static methods (non-static is better for testing)
public class HttpMapper {

    public static Request toRequestObject(String httpRequest) {
        Request request = new Request();

        request.setMethod(HttpMethod.getHttpMethod(httpRequest));
        request.setRoute(getRoute(httpRequest));
        request.setHost(getHttpHeader("Host", httpRequest));

        return request;
    }

    private static String getRoute(String httpRequest) {

        return httpRequest.split(" ")[1];
    }

    private static String getHttpHeader(String header, String httpRequest) {
        Pattern regex = Pattern.compile("^" + header + ":\\s(.+)", Pattern.MULTILINE);
        Matcher matcher = regex.matcher(httpRequest);

        if (!matcher.find()) {
            return null;
        }

        return matcher.group(1);
    }
}
