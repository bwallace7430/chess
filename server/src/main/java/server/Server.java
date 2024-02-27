package server;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.MemoryDataAccess;
import exception.ResponseException;
import model.GameData;
import model.UserData;
import service.AdminService;
import service.GameService;
import service.RegistrationService;
import service.SessionService;
import spark.*;

import java.util.ArrayList;
import java.util.Collection;

public class Server {
    private final RegistrationService registrationService;
    private final SessionService sessionService;
    private final GameService gameService;
    private final AdminService adminService;

    public Server(DataAccess dataAccess) {
        registrationService = new RegistrationService(dataAccess);
        sessionService = new SessionService(dataAccess);
        gameService = new GameService(dataAccess);
        adminService = new AdminService(dataAccess);
    }

    public Server() {
        var dataAccess = new MemoryDataAccess();
        registrationService = new RegistrationService(dataAccess);
        sessionService = new SessionService(dataAccess);
        gameService = new GameService(dataAccess);
        adminService = new AdminService(dataAccess);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clearDatabase);
        Spark.post("/user", this::registerUser);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);
        Spark.exception(ResponseException.class, this::exceptionHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private void exceptionHandler(ResponseException ex, Request request, Response response) {
        response.status(ex.ErrorCode());
        var error = new ErrorResponse(ex.getMessage());
        response.body(new Gson().toJson(error));
    }

    private Object clearDatabase(Request request, Response response) {
        adminService.clearDatabase();
        response.status(200);
        return "";
    }

    private Object registerUser(Request request, Response response) throws ResponseException{
        var user = new Gson().fromJson(request.body(), UserData.class);
        var authData = registrationService.register(user.username(), user.password(), user.email());
        response.status(200);
        return new Gson().toJson(authData);
    }

    private Object login(Request request, Response response) throws ResponseException{
        var user = new Gson().fromJson(request.body(), LoginRequest.class);
        var authData = sessionService.createSession(user.username(), user.password());
        response.status(200);
        return new Gson().toJson(authData);
    }

    private Object logout(Request request, Response response) throws ResponseException{
        var authToken = request.headers("authorization");
        sessionService.endSession(authToken);
        response.status(200);
        return "";
    }

    private Object listGames(Request request, Response response) throws ResponseException{
        var authToken = request.headers("authorization");
        var allGames = gameService.listGames(authToken);
        ArrayList<GameListEntry> list = new ArrayList<GameListEntry>();
        for(GameData game : allGames){
            GameListEntry entry = new GameListEntry(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName());
            list.add(entry);
        }
        ListGameResponse responseList = new ListGameResponse(list);
        response.status(200);
        return new Gson().toJson(responseList);
    }

    private Object createGame(Request request, Response response) throws ResponseException{
        var authToken = request.headers("authorization");
        var game = new Gson().fromJson(request.body(), CreateGameRequest.class);
        var gameName = game.gameName();
        var responseObject = new CreateGameResponse(gameService.createGame(authToken, gameName).gameID());
        response.status(200);
        return new Gson().toJson(responseObject);
    }

    private Object joinGame(Request request, Response response) throws ResponseException{
        var authToken = request.headers("authorization");
        var game = new Gson().fromJson(request.body(), JoinGameRequest.class);
        if(game.convertColorToEnum() == null){
            gameService.joinGame(authToken, game.gameID());
        }
        else {
            gameService.joinGame(authToken, game.gameID(), game.convertColorToEnum());
        }
        response.status(200);
        return "";
    }
}
