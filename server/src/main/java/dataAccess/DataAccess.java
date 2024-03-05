package dataAccess;

import chess.ChessGame;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;

public interface DataAccess {
    UserData getUser(String username) throws DataAccessException;
    void createUser(String username, String password, String email) throws ResponseException, DataAccessException;
    AuthData generateAuthToken(String username) throws DataAccessException;
    AuthData getAuthDataByUsername(String username) throws DataAccessException;
    AuthData getAuthDataByAuthToken(String authToken) throws DataAccessException;
    void removeAuthData(String authToken) throws DataAccessException;
    GameData createGame(String gameName) throws DataAccessException;
    GameData getGameByID(int gameID) throws DataAccessException;
    GameData addPlayerToGame(GameData game, String username, ChessGame.TeamColor playerColor) throws DataAccessException, ResponseException;
    GameData addObserverToGame(GameData game, String username);
    Collection<GameData> getAllGames() throws ResponseException;
    void deleteGames() throws DataAccessException;
    void deleteUsers() throws DataAccessException;
    void deleteAuths() throws DataAccessException;
}
