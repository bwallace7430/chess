package clientTests;

import client.ServerFacade;
import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;
    String username = "abe_the_babe";
    String password = "lincolnR0ck$";
    String email = "alincoln@usa.com";
    String gameName = "Abe and the Baberahams";
    static int port = 0;
    static String url = "";


    @BeforeAll
    public static void init() {
        server = new Server();
        port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        url = "http://localhost:"+port;
    }

    @BeforeEach
    public void doThis(){
        try{
            ServerFacade.deleteDatabase("http://localhost:"+port);
        }
        catch (Exception e){
            System.out.print("Big oof.");
        }
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    public void validCreateUser() throws Exception {
        String[] args = {username, password, email};
        var responseBody = ServerFacade.createUser(url, args);
        var authToken = responseBody.get("authToken");
        Assertions.assertNotNull(authToken);
    }
    @Test
    public void invalidCreateUser() throws Exception {
        String[] args = {username, password};
        Assertions.assertThrows(Exception.class, ()->ServerFacade.createUser(url, args));
    }
    @Test
    public void validCreateSession() throws Exception {
        ServerFacade.createUser(url, new String[]{username, password, email});
        String[] args = {username, password};
        var responseBody = ServerFacade.createSession(url, args);
        var authToken = responseBody.get("authToken");
        Assertions.assertNotNull(authToken);
    }
    @Test
    public void invalidCreateSession() {
        Assertions.assertThrows(Exception.class, ()->ServerFacade.createSession(url, new String[]{username, password}));
    }
    @Test
    public void validDeleteSession() throws Exception {
        ServerFacade.createUser(url, new String[]{username, password, email});
        var responseBody = ServerFacade.createSession(url, new String[]{username, password});
        var authToken = responseBody.get("authToken");
        Assertions.assertDoesNotThrow(()->ServerFacade.deleteSession(url, (String) authToken));
    }
    @Test
    public void invalidDeleteSession() throws Exception {
        ServerFacade.createUser(url, new String[]{username, password, email});
        var responseBody = ServerFacade.createSession(url, new String[]{username, password});
        var authToken = responseBody.get("authToken");
        ServerFacade.deleteSession(url, (String) authToken);
        Assertions.assertThrows(Exception.class, ()->ServerFacade.deleteSession(url, (String) authToken));
    }
    @Test
    public void validListGame() throws Exception {
        ServerFacade.createUser(url, new String[]{username, password, email});
        var responseBody = ServerFacade.createSession(url, new String[]{username, password});
        var authToken = responseBody.get("authToken");
        var responseBody2 = ServerFacade.listGame(url, (String) authToken);
        var games = responseBody2.get("games");
        Assertions.assertTrue(games.isEmpty());
    }
    @Test
    public void invalidListGame() throws Exception {
        ServerFacade.createUser(url, new String[]{username, password, email});
        Assertions.assertThrows(Exception.class, ()-> ServerFacade.listGame(url, "fakeAuthToken"));
    }
    @Test
    public void validCreateGame() throws Exception {
        ServerFacade.createUser(url, new String[]{username, password, email});
        var authResponseBody = ServerFacade.createSession(url, new String[]{username, password});
        var authToken = authResponseBody.get("authToken");

        var createResponseBody = ServerFacade.createGame(url, gameName, (String) authToken);
        var gameID = createResponseBody.get("gameID");
        Assertions.assertNotNull(gameID);

        var listResponseBody = ServerFacade.listGame(url, (String) authToken);
        var games = listResponseBody.get("games");
        Assertions.assertFalse(games.isEmpty());
    }
    @Test
    public void invalidCreateGame() throws Exception {
        ServerFacade.createUser(url, new String[]{username, password, email});
        Assertions.assertThrows(Exception.class, ()-> ServerFacade.createGame(url, gameName, "fakeAuthToken"));
    }
    @Test
    public void validJoinGame() throws Exception {
        ServerFacade.createUser(url, new String[]{username, password, email});
        var authResponseBody = ServerFacade.createSession(url, new String[]{username, password});
        var authToken = authResponseBody.get("authToken");

        var createResponseBody = ServerFacade.createGame(url, gameName, (String) authToken);
        var gameID = createResponseBody.get("gameID");

        Assertions.assertDoesNotThrow(()->ServerFacade.joinGame(url, "WHITE", String.valueOf(gameID), (String) authToken));
        Assertions.assertDoesNotThrow(()->ServerFacade.joinGame(url, "BLACK", String.valueOf(gameID), (String) authToken));
        Assertions.assertDoesNotThrow(()->ServerFacade.joinGame(url, String.valueOf(gameID), (String) authToken));
    }
    @Test
    public void invalidJoinGame() throws Exception {
        ServerFacade.createUser(url, new String[]{username, password, email});
        var authResponseBody = ServerFacade.createSession(url, new String[]{username, password});
        var authToken = authResponseBody.get("authToken");

        var createResponseBody = ServerFacade.createGame(url, gameName, (String) authToken);
        var gameID = createResponseBody.get("gameID");

        Assertions.assertDoesNotThrow(()->ServerFacade.joinGame(url, "WHITE", String.valueOf(gameID), (String) authToken));
        Assertions.assertThrows(Exception.class, ()->ServerFacade.joinGame(url, "WHITE", String.valueOf(gameID), (String) authToken));
        Assertions.assertThrows(Exception.class, ()->ServerFacade.joinGame(url, "BLACK", String.valueOf(0), (String) authToken));
    }
}
