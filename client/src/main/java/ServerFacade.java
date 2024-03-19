import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.*;
public class ServerFacade {
    private static void writeRequestBody(Map body, HttpURLConnection http) throws IOException {
        if (!body.isEmpty()) {
            http.setDoOutput(true);
            try (var outputStream = http.getOutputStream()) {
                var jsonBody = new Gson().toJson(body);
                outputStream.write(jsonBody.getBytes());
            }
        }
    }

    private static void writeRequestHeader(String header, HttpURLConnection http) {
        if (!header.isEmpty()) {
            http.setDoOutput(true);
            http.addRequestProperty("Authorization", header);
        }
        http.addRequestProperty("Content-Type", "application/json");
    }
    private static void sendRequest(String url, String method, String[] args, String header) throws Exception {
        URI uri = new URI(url);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        var body = Map.of("username", args[0], "password", args[1], "email", args[2]);

        http.setReadTimeout(5000);
        http.setRequestMethod(method);
        writeRequestHeader(header, http);
        writeRequestBody(body, http);

        http.connect();
    }

    public static void createUser(String[] body, String header) throws Exception {
        sendRequest("http://localhost:8080/user", "POST", body, header);
    }

    public static void createSession(String[] body, String header) throws Exception {
        sendRequest("http://localhost:8080/session", "POST", body, header);
    }

    public static void deleteSession(String[] body, String header) throws Exception {
        sendRequest("http://localhost:8080/session", "DELETE", body, header);
    }

    public static void listGame(String[] body, String header) throws Exception {
        sendRequest("http://localhost:8080/game", "GET", body, header);
    }

    public static void createGame(String[] body, String header) throws Exception {
        sendRequest("http://localhost:8080/post", "POST", body, header);
    }

    public static void joinGames(String[] body, String header) throws Exception {
        sendRequest("http://localhost:8080/put", "PUT", body, header);
    }
    }