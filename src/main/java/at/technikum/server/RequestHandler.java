package at.technikum.server;

import at.technikum.server.http.ContentType;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import at.technikum.server.util.HttpMapper;
import at.technikum.server.util.HttpResponseConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//Verwaltet Client Anfrage über Socket
//Ruft Serverlogik auf
//Generiert HTTP Antwort und sendet sie an Client
public class RequestHandler {

    private BufferedReader in;
    private PrintWriter out;

    private final Socket client;

    private final ServerApplication app;

    public RequestHandler(Socket client, ServerApplication app) {
        this.client = client;
        this.app = app;
    }

    public void handle() throws IOException {
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String httpRequest = getHttpStringFromStream(in);

        Request request = HttpMapper.toRequestObject(httpRequest);
        Response response = app.handle(request);

        out = new PrintWriter(client.getOutputStream(), true);

        //neue Klasse verwendet
        out.write(HttpResponseConverter.convert(response));

        out.close();
        in.close();
        client.close();
    }

    private String getHttpStringFromStream(BufferedReader in) throws IOException {
        StringBuilder builder = new StringBuilder();

        String inputLine;
        while ((inputLine = in.readLine()) != null && !inputLine.equals("")) {
            builder
                    .append(inputLine)
                    .append(System.lineSeparator());
        }

        String httpRequest = builder.toString();

        Pattern regex = Pattern.compile("^Content-Length:\\s(.+)", Pattern.MULTILINE);
        Matcher matcher = regex.matcher(httpRequest);

        if (!matcher.find()) {
            return builder.toString();
        }

        builder.append(System.lineSeparator());

        int contentLength = Integer.parseInt(matcher.group(1));
        char[] buffer = new char[contentLength];
        in.read(buffer, 0, contentLength);
        builder.append(buffer);

        return builder.toString();
    }
}
