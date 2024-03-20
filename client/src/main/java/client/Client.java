package client;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Client {
    private String authToken;
    private final String serverUrl;
    private enum State{
        LOGGEDIN,
        LOGGEDOUT,
        INGAME
    }
    private State userState;
    public Client(String serverUrl) {
        this.serverUrl = serverUrl;
        userState = State.LOGGEDOUT;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch(userState){
                case LOGGEDOUT -> loggedOutHandler(cmd, params);
                case LOGGEDIN -> loggedInHandler(cmd, params);
                case INGAME -> inGameHandler(cmd, params);
            };
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String inGameHandler(String cmd, String[] params) {
        return "";
    }

    private String loggedOutHandler(String cmd, String[] params) throws Exception {
        return switch (cmd) {
            case "register" -> createUser(params);
            case "login" -> logIn(params);
            case "help" -> displayLoggedOutHelp();
            case "quit" -> "quit";
            default -> displayLoggedOutHelp();
        };
    }
    private String createUser(String[] params) throws Exception {
        var responseMap = ServerFacade.createUser(serverUrl, params);
        authToken = (String) responseMap.get("authToken");
        userState = State.LOGGEDIN;
        return "You are now logged in as " + params[0];
    }
    private String logIn(String[] params) throws Exception {
        var responseMap = ServerFacade.createSession(serverUrl, params);
        authToken = (String) responseMap.get("authToken");
        userState = State.LOGGEDIN;
        return "You are now logged in as " + params[0];
    }
    private String displayLoggedOutHelp(){
        return """
                register <USERNAME> <PASSWORD> <EMAIL> - create an account
                login <USERNAME> <PASSWORD> - log in to your account
                help - view all possible commands
                quit - exit the program""";
    }

    private String loggedInHandler(String cmd, String[] params) throws Exception {
        return switch (cmd) {
            case "list" -> listGames();
            case "create" -> createGame(params);
            case "join" -> joinGame(params);
            case "observe" -> observeGame(params);
            case "logout" -> logOut();
            case "quit" -> "quit";
            case "help" -> displayLoggedInHelp();
            default -> displayLoggedInHelp();
        };
    }
    private String listGames() throws Exception {
        var gameListString = "";
        var games = ServerFacade.listGame(serverUrl, authToken);
        return "";
    }
    private String createGame(String[] params) throws Exception {
        var responseMap = ServerFacade.createGame(serverUrl, params[0], authToken);
        var gameID = responseMap.get("gameID");
        return "Your new game has the id: " + gameID;
    }
    private String joinGame(String[] params) throws Exception {
        ServerFacade.joinGame(serverUrl, params, authToken);
        return "You are playing in game: " + params[1];
    }
    private String observeGame(String[] params) throws Exception {
        ServerFacade.joinGame(serverUrl, params, authToken);
        return "You are now watching game: " + params[0];
    }
    private String logOut() throws Exception {
        ServerFacade.deleteSession(serverUrl);
        userState = State.LOGGEDOUT;
        authToken = null;
        return "You are now logged out.";
    }
    private String displayLoggedInHelp(){
        return """
                list - view all games
                create <NAME> - create a game
                join <ID> [WHITE|BLACK] - join a game
                observe <ID> - watch a game
                logout - log out of your account
                quit - exit the program
                help - view all possible commands""";
    }
}