package dataAccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;

public interface DataAccess {
    UserData getUser(String username);
    void createUser(String username, String password, String email) throws DataAccessException;
    AuthData generateAuthToken(String username);
    AuthData getAuthDataByUsername(String username);
    AuthData getAuthDataByAuthToken(String authToken);
    void removeAuthData(String authToken);
    GameData createGame(String gameName);
    GameData getGameByID(int gameID);
    GameData addPlayerToGame(GameData game, String username, ChessGame.TeamColor playerColor) throws DataAccessException;
    GameData addObserverToGame(GameData game, String username);
    Collection<GameData> getAllGames();
    void deleteGames();
    void deleteUsers();
    void deleteAuths();
}