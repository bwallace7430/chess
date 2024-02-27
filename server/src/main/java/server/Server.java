package server;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import service.AdminService;
import service.GameService;
import service.RegistrationService;
import service.SessionService;
import spark.*;

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
        return new Gson().toJson(authData);
    }

    private Object logout(Request request, Response response) throws ResponseException{
        var authToken = new Gson().fromJson(request.body(), String.class);
        sessionService.endSession(authToken);
        response.status(200);
        return "";
    }

    private Object listGames(Request request, Response response) throws ResponseException{
        var authToken = new Gson().fromJson(request.headers("authorization"), String.class);
        var all_games = gameService.listGames(authToken);
        response.status(200);
        return all_games;
    }

    private Object createGame(Request request, Response response) throws ResponseException{
        var authToken = new Gson().fromJson(request.headers("authorization"), String.class);
        var gameName = new Gson().fromJson(request.body(), String.class);
        response.status(200);
        return gameService.createGame(authToken, gameName);
    }

    private Object joinGame(Request request, Response response) throws ResponseException{
        var authToken = new Gson().fromJson(request.headers("authorization"), String.class);
        var game = new Gson().fromJson(request.body(), JoinGameRequest.class);
        return gameService.joinGame(authToken, game.gameID(), game.convertColorToEnum());
    }
}
