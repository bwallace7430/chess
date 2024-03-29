package dataAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import chess.ChessGame;
import exception.ResponseException;
import model.UserData;
import model.AuthData;
import model.GameData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MemoryDataAccess implements DataAccess{
    private final Collection<UserData>allUsers = new ArrayList<>();
    private final Collection<AuthData> allAuthData = new ArrayList<>();
    private final Collection<GameData>allGames = new ArrayList<>();
    private int gameID = 0;

    public UserData getUser(String username) {
        for (UserData user : allUsers){
            if(user.username().equals(username)){
                return user;
            }
        }
        return null;
    }

    public void createUser(String username, String password, String email) throws ResponseException {
        if(username == null || username.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()){
            throw new ResponseException(400, "Error: bad request");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        var newUser = new model.UserData(username, hashedPassword, email);
        allUsers.add(newUser);
    }

    public AuthData generateAuthToken(String username) {
        var authToken = UUID.randomUUID().toString();
        var newAuthTokenObject = new AuthData(username, authToken);
        allAuthData.add(newAuthTokenObject);
        return newAuthTokenObject;
    }

    public AuthData getAuthDataByUsername(String username) {
        for (AuthData authData : allAuthData){
            if(authData.username().equals(username)){
                return authData;
            }
        }
        return null;
    }

    public AuthData getAuthDataByAuthToken(String authToken) {
        for (AuthData authData : allAuthData){
            if(authData.authToken().equals(authToken)){
                return authData;
            }
        }
        return null;
    }

    public void removeAuthData(String authToken){
        allAuthData.removeIf(authData -> authData.authToken().equals(authToken));
    }

    public GameData createGame(String gameName){
        gameID += 1;
        var newGame = new GameData(gameID, null, null, gameName, new ChessGame());
        allGames.add(newGame);
        return newGame;
    }

    public GameData getGameByID(int gameID){
        for (GameData gameData : allGames){
            if(gameData.gameID() == gameID){
                return gameData;
            }
        }
        return null;
    }

    public GameData addPlayerToGame(GameData game, String username, ChessGame.TeamColor playerColor) throws ResponseException{
        switch (playerColor){
            case WHITE -> {
                if (game.whiteUsername() != null){
                    throw new ResponseException(403, "Error: already taken");
                }
                else{
                    var editedGame = new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game());
                    allGames.remove(game);
                    allGames.add(editedGame);
                    return editedGame;
                }
            }
            case BLACK -> {
                if (game.blackUsername() != null){
                throw new ResponseException(403, "Error: already taken");
                }
                else{
                    var editedGame = new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game());
                    allGames.remove(game);
                    allGames.add(editedGame);
                    return editedGame;
                }
            }
        }
        return null;
    }

    public GameData addObserverToGame(GameData game, String username){
        return game;
    }

    public Collection<GameData> getAllGames(){
        return allGames;
    }
    public void deleteGames(){
        allGames.clear();
    }
    public void deleteUsers(){
        allUsers.clear();
    }
    public void deleteAuths(){
        allAuthData.clear();
    }
}
