package dataAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import chess.ChessGame;
import model.UserData;
import model.AuthData;
import model.GameData;

public class MemoryDataAccess implements DataAccess{
    private Collection<UserData>allUsers = new ArrayList<>();
    private Collection<AuthData>allAuthTokens = new ArrayList<>();
    private Collection<GameData>allGames = new ArrayList<>();
    private int gameID = 0;

    public UserData getUser(String username){
        for (UserData user : allUsers){
            if(user.username() == username){
                return user;
            }
        }
        return null;
    }

    public void createUser(String username, String password, String email) throws DataAccessException{
        if(username == null || username == "" || password == null || password == "" || email == null || email == ""){
            throw new DataAccessException("Correct info not found.");
        }
        var newUser = new model.UserData(username, password, email);
        allUsers.add(newUser);
    }

    public void generateAuthToken(String username){
        var authToken = UUID.randomUUID().toString();
        var newAuthTokenObject = new AuthData(username, authToken);
        allAuthTokens.add(newAuthTokenObject);
    }

    public AuthData getAuthDataByUsername(String username){
        for (AuthData authData : allAuthTokens){
            if(authData.username() == username){
                return authData;
            }
        }
        return null;
    }

    public AuthData getAuthDataByAuthToken(String authToken){
        for (AuthData authData : allAuthTokens){
            if(authData.authToken() == authToken){
                return authData;
            }
        }
        return null;
    }

    public void removeAuthToken(String authToken){
        allAuthTokens.removeIf(authData -> authData.authToken() == authToken);
    }

    public GameData createGame(String gameName){
        gameID += 1;
        var observers = new ArrayList<String>();
        var newGame = new GameData(gameID, null, null, gameName, new ChessGame(), observers);
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

    public GameData addPlayerToGame(GameData game, String username, ChessGame.TeamColor playerColor) throws DataAccessException{
        switch (playerColor){
            case WHITE -> {
                if (game.whiteUsername() != null){
                    throw new DataAccessException("Can not join as white. Spot already taken.");
                }
                else{
                    var editedGame = new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game(), game.observers());
                    allGames.remove(game);
                    allGames.add(editedGame);
                    return editedGame;
                }
            }
            case BLACK -> {
                if (game.blackUsername() != null){
                throw new DataAccessException("Can not join as black. Spot already taken.");
                }
                else{
                    var editedGame = new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game(), game.observers());
                    allGames.remove(game);
                    allGames.add(editedGame);
                    return editedGame;
                }
            }
        }
        return null;
    }

    public GameData addObserverToGame(GameData game, String username){
        game.addObserver(username);
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
        allAuthTokens.clear();
    }
}
