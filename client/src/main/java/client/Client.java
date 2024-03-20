package client;
import java.util.Arrays;

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
        authToken = ServerFacade.createUser(serverUrl, params);
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
    private String logIn(String[] params) throws Exception {
        authToken = ServerFacade.createSession(serverUrl, params);
        userState = State.LOGGEDIN;
        return "You are now logged in as " + params[0];
    }

    private String loggedInHandler(String cmd, String[] params) throws Exception {
        return switch (cmd) {
            case "help" -> displayLoggedInHelp();
            case "list" -> listGames();
            case "create" -> createGame(params);
            case "join" -> joinGame(params);
            case "observe" -> observeGame(params);
            case "logout" -> logOut();
            default -> displayLoggedInHelp();
        };
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
    private String listGames() throws Exception {
        ServerFacade.listGame(serverUrl, authToken);
        return"";
    }
    private String createGame(String[] params) throws Exception {
        ServerFacade.createGame(serverUrl, params[0], authToken);
        return "";
    }
    private String joinGame(String[] params) throws Exception {
        ServerFacade.joinGame(serverUrl, params, authToken);
        return "";
    }
    private String observeGame(String[] params) throws Exception {
        ServerFacade.joinGame(serverUrl, params, authToken);
        return "";
    }
    private String logOut() throws Exception {
        ServerFacade.deleteSession(serverUrl);
        userState = State.LOGGEDOUT;
        authToken = null;
        return "You are now logged out.";
    }
}
