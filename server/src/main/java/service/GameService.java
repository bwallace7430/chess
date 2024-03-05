package service;

import chess.ChessGame;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import exception.ResponseException;
import model.GameData;

import java.util.Collection;

public class GameService {
    private final DataAccess dataAccessObject;

    public GameService(DataAccess data) {
        dataAccessObject = data;
    }

    public GameData createGame(String authToken, String gameName) throws ResponseException {
        try {
            if (gameName == null || gameName.isEmpty()) {
                throw new ResponseException(400, "Error: bad request");
            } else if (dataAccessObject.getAuthDataByAuthToken(authToken) != null) {
                return dataAccessObject.createGame(gameName);
            } else {
                throw new ResponseException(401, "Error: user unauthorized");
            }
        }
        catch (DataAccessException e){
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }

    public Collection<GameData> listGames(String authToken) throws ResponseException {
        try {
            if (dataAccessObject.getAuthDataByAuthToken(authToken) == null) {
                throw new ResponseException(401, "Error: unauthorized");
            }
            return dataAccessObject.getAllGames();
        }
        catch (DataAccessException e){
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }

   //FIX THIS ONE
    public GameData joinGame(String authToken, int gameID, ChessGame.TeamColor playerColor) throws ResponseException{
        try {
            if (dataAccessObject.getAuthDataByAuthToken(authToken) == null) {
                throw new ResponseException(401, "Error: unauthorized");
            }
            var game = dataAccessObject.getGameByID(gameID);
            if (game == null) {
                throw new ResponseException(400, "Error: bad request");
            }
            var username = dataAccessObject.getAuthDataByAuthToken(authToken).username();
                game = dataAccessObject.addPlayerToGame(game, username, playerColor);
            return game;
        }
        catch (DataAccessException e){
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }

    public GameData joinGame(String authToken, int gameID) throws ResponseException{
        try {
            if (dataAccessObject.getAuthDataByAuthToken(authToken) == null) {
                throw new ResponseException(401, "Error: unauthorized");
            }
            var game = dataAccessObject.getGameByID(gameID);
            if (game == null) {
                throw new ResponseException(400, "Error: bad request");
            }
            var username = dataAccessObject.getAuthDataByAuthToken(authToken).username();
            return dataAccessObject.addObserverToGame(game, username);
        }
        catch (DataAccessException e){
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }
}