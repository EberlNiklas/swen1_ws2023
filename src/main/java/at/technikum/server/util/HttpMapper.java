package at.technikum.server.util;

import at.technikum.server.http.Request;
import at.technikum.server.http.Response;

public class HttpMapper {

    public static Request toRequestObject(String request){
     return new Request();
    }

    public static String toResponseString(Response response){
        return null;
    }

}
