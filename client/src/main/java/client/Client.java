package client;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class Client {
    private String authToken;
    private final String serverUrl;
    private enum State{
        LOGGEDIN,
        LOGGEDOUT,
        INGAME
    }
    private State userState;
    private HashMap<Integer, Double> lastViewedGames;
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
        lastViewedGames = new HashMap<>();
        var game_index = 1;
        var responseMap = ServerFacade.listGame(serverUrl, authToken);
        var games = responseMap.get("games");
        for(var game: games) {
            game_index += 1;
            gameListString += ("Game ID: " + String.valueOf(game_index) +
                    "\n    Game Name: " + game.get("gameName") +
                    "\n    Playing white: " + game.get("whiteUsername") +
                    "\n    Playing black: " + game.get("blackUsername") + "\n");
            double gameID = (double) game.get("gameID");
            lastViewedGames.put(game_index, gameID);
        }
        return gameListString;
    }
    private String createGame(String[] params) throws Exception {
        var responseMap = ServerFacade.createGame(serverUrl, params[0], authToken);
        var gameID = responseMap.get("gameID");
        return "Your new game has the id: " + gameID;
    }
    private String joinGame(String[] params) throws Exception {
        var gameIndex = lastViewedGames.get(parseInt(params[1]));
        var stringGameIndex = String.valueOf(gameIndex);
        ServerFacade.joinGame(serverUrl, params[0], stringGameIndex, authToken);
        return "You are playing in game as: " + params[0];
    }
    private String observeGame(String[] params) throws Exception {
        var gameIndex = lastViewedGames.get(parseInt(params[1]));
        var stringGameIndex = String.valueOf(gameIndex);
        ServerFacade.joinGame(serverUrl, params[0], authToken);
        return "You are now viewing the game.";
    }
    private String logOut() throws Exception {
        ServerFacade.deleteSession(serverUrl, authToken);
        userState = State.LOGGEDOUT;
        authToken = null;
        return "You are now logged out.";
    }
    private String displayLoggedInHelp(){
        return """
                list - view all games
                create <NAME> - create a game
                join [WHITE|BLACK] <ID> - join a game
                observe <ID> - watch a game
                logout - log out of your account
                quit - exit the program
                help - view all possible commands""";
    }
}
