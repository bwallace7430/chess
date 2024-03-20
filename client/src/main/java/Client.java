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
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    private String inGameHandler(String cmd, String[] params) {
        return "";
    }

    private String loggedInHandler(String cmd, String[] params) {
        return switch (cmd) {
            case "help" -> signIn(params);
            case "list" -> rescuePet(params);
            case "create" -> create();
            case "join" -> listPets();
            case "observe" -> signOut();
            case "logout" -> signOut();
            default -> help();
        };
    }

    private String loggedOutHandler(String cmd, String[] params) {
        return switch (cmd) {
            case "help" -> signIn(params);
            case "quit" -> rescuePet(params);
            case "login" -> listPets();
            case "register" -> signOut();
            default -> help();
        };
    }
}
