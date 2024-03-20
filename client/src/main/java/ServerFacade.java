import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.*;
public class ServerFacade {
    private static void writeRequestBody(Map<String, String> body, HttpURLConnection http) throws IOException {
        if (!(body == null)) {
            http.setDoOutput(true);
            try (var outputStream = http.getOutputStream()) {
                var jsonBody = new Gson().toJson(body);
                outputStream.write(jsonBody.getBytes());
            }
        }
    }

    private static void writeRequestHeader(String authToken, HttpURLConnection http) {
        if (!(authToken == null)) {
            http.setDoOutput(true);
            http.addRequestProperty("Authorization", authToken);
        }
        // check for non-json routes
        http.addRequestProperty("Accept", "application/json");
    }

    private static void sendRequest(String url, String method, Map<String, String> body, String authToken) throws Exception {
        URI uri = new URI(url);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();

        http.setReadTimeout(5000);
        http.setRequestMethod(method);
        writeRequestHeader(authToken, http);
        writeRequestBody(body, http);

        http.connect();

        var statusCode = http.getResponseCode();
        var statusMessage = http.getResponseMessage();

        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            System.out.println(new Gson().fromJson(inputStreamReader, Map.class));
        }
    }

    private static void sendRequest(String url, String method, Map<String, String> body) throws Exception{
        sendRequest(url, method, body, null);
    }

    private static void sendRequest(String url, String method) throws Exception{
        sendRequest(url, method, null, null);
    }

    private static void sendRequest(String url, String method, String authToken) throws Exception{
        sendRequest(url, method, null, authToken);
    }

    public static String createUser(String[] args) throws Exception {
        var body = Map.of("username", args[0], "password", args[1], "email", args[2]);
        sendRequest("http://localhost:8080/user", "POST", body);
    }

    public static void createSession(String[] args) throws Exception {
        var body = Map.of("username", args[0], "password", args[1]);
        sendRequest("http://localhost:8080/session", "POST", body);
    }

    public static void deleteSession() throws Exception {
        sendRequest("http://localhost:8080/session", "DELETE");
    }

    public static void listGame(String authToken) throws Exception {
        sendRequest("http://localhost:8080/game", "GET", authToken);
    }

    public static void createGame(String arg, String authToken) throws Exception {
        var body = Map.of("gameName", arg);
        sendRequest("http://localhost:8080/post", "POST", body, authToken);
    }

    public static void joinGames(String[] args, String authToken) throws Exception {
        var body = Map.of("playerColor", args[0], "gameID", args[1]);
        sendRequest("http://localhost:8080/put", "PUT", body, authToken);
    }
    }