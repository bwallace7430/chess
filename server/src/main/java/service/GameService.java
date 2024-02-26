package service;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import model.AuthData;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;

public class GameService {
    private final MemoryDataAccess dataAccessObject;

    public GameService(MemoryDataAccess data) {
        dataAccessObject = data;
    }

    public GameData createGame(String authToken, String gameName) throws DataAccessException {
        if (gameName == null || gameName.isEmpty()) {
            throw new DataAccessException("Can not create game. Valid game name was not given");
        } else if (dataAccessObject.getAuthDataByAuthToken(authToken) != null) {
            return dataAccessObject.createGame(gameName);
        }
        else {
            throw new DataAccessException("Can not create game. User is not authorized.");
        }
    }

    public Collection<GameData> listGames(String authToken) throws DataAccessException {
        if (dataAccessObject.getAuthDataByAuthToken(authToken) == null) {
            throw new DataAccessException("Can not list games. User is not authorized.");

        }
        return dataAccessObject.getAllGames();
    }

    public GameData joinGame(String authToken, int gameID, ChessGame.TeamColor playerColor) throws DataAccessException{
        if (dataAccessObject.getAuthDataByAuthToken(authToken) == null) {
            throw new DataAccessException("Can not join game. User is not authorized.");
        }
        var game = dataAccessObject.getGameByID(gameID);
        if (game == null){
            throw new DataAccessException("Can not join game. Game does not exist.");
        }
        var username = dataAccessObject.getAuthDataByAuthToken(authToken).username();
        return dataAccessObject.addPlayerToGame(game, username, playerColor);
    }

    public GameData joinGame(String authToken, int gameID) throws DataAccessException{
        if (dataAccessObject.getAuthDataByAuthToken(authToken) == null) {
            throw new DataAccessException("Can not join game. User is not authorized.");
        }
        var game = dataAccessObject.getGameByID(gameID);
        if (game == null){
            throw new DataAccessException("Can not join game. Game does not exist.");
        }
        var username = dataAccessObject.getAuthDataByAuthToken(authToken).username();
        return dataAccessObject.addObserverToGame(game, username);
    }
}