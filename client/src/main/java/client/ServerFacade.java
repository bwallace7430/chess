package client;

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

    private static Map sendRequest(String url, String method, Map<String, String> body, String authToken) throws Exception {
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
            return (new Gson().fromJson(inputStreamReader, Map.class));
        }
    }

    private static Map sendRequest(String url, String method, Map<String, String> body) throws Exception{
        return sendRequest(url, method, body, null);
    }

    private static Map sendRequest(String url, String method) throws Exception{
        return sendRequest(url, method, null, null);
    }

    private static Map sendRequest(String url, String method, String authToken) throws Exception{
        return sendRequest(url, method, null, authToken);
    }

    public static String createUser(String url, String[] args) throws Exception {
        var body = Map.of("username", args[0], "password", args[1], "email", args[2]);
        var responseMap = sendRequest(url + "/user", "POST", body);

    }

    public static String createSession(String url, String[] args) throws Exception {
        var body = Map.of("username", args[0], "password", args[1]);
        sendRequest(url + "/session", "POST", body);
    }

    public static void deleteSession(String url) throws Exception {
        sendRequest(url + "/session", "DELETE");
    }

    public static String listGame(String url, String authToken) throws Exception {
        sendRequest(url + "/game", "GET", authToken);
    }

    public static void createGame(String url, String arg, String authToken) throws Exception {
        var body = Map.of("gameName", arg);
        sendRequest(url + "/game", "POST", body, authToken);
    }

    public static void joinGame(String url, String[] args, String authToken) throws Exception {
        var body = Map.of("playerColor", args[0], "gameID", args[1]);
        sendRequest(url + "/game", "PUT", body, authToken);
    }
    }