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

    private String loggedOutHandler(String cmd, String[] params) throws Exception {
        return switch (cmd) {
            case "register" -> createUser(params);
            case "login" -> listPets();
            case "help" -> displayLoggedOutHelp(params);
            case "quit" -> rescuePet(params);
            default -> help();
        };
    }


    private String createUser(String[] params) throws Exception {
        authToken = ServerFacade.createUser(params);
        userState = State.LOGGEDIN;
        return "You are now logged in as " + params[0];
    }
    private String loggedInHandler(String cmd, String[] params) {
        return switch (cmd) {
            case "help" -> displayLoggedInHelp(params);
            case "list" -> listGamesrescuePet(params);
            case "create" -> create();
            case "join" -> listPets();
            case "observe" -> signOut();
            case "logout" -> signOut();
            default -> help();
        };
    }

    private String displayLoggedOutHelp(String[] params){
        return "register <USERNAME> <PASSWORD> <EMAIL> - create an account\n" +
                "login <USERNAME> <PASSWORD> - log in to your account\n" +
                "help - view all possible commands\n" +
                "quit - exit the program";
    }
    private String displayLoggedInHelp(String[] params){
        return "list - view all games\n" +
                "create <NAME> - create a game\n" +
                "join <ID> [WHITE|BLACK] - join a game\n" +
                "observe <ID> - watch a game\n" +
                "logout - log out of your account\n" +
                "quit - exit the program" +
                "help - view all possible commands";
    }
}
