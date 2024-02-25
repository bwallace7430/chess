package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import model.AuthData;
import model.GameData;
public class GameService {
    private final MemoryDataAccess dataAccessObject;
    public GameService(MemoryDataAccess data){
        dataAccessObject = data;
    }

    public GameData createGame(String authToken, String gameName) throws DataAccessException{
        if(dataAccessObject.getAuthDataByAuthToken(authToken) != null){
            return dataAccessObject.createGame(gameName);
        }
        else{
            throw new DataAccessException("Can not create game. User is not authorized.");
        }
    }
}