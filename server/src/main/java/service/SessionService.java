package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import exception.ResponseException;
import model.AuthData;
public class SessionService {
    private final DataAccess dataAccessObject;
    public SessionService(DataAccess data){
        dataAccessObject = data;
    }

    public AuthData createSession(String username, String password) throws ResponseException {
        var user = dataAccessObject.getUser(username);
        if(user == null){
            throw new ResponseException(401, "Error: unauthorized");
        }
        else{
            if(user.password() != password){
                throw new ResponseException(401, "Error: unauthorized");
            }
            else{
                dataAccessObject.generateAuthToken(username);
                return dataAccessObject.getAuthDataByUsername(username);
            }
        }
    }

    public void endSession(String authToken) throws ResponseException{
        if(dataAccessObject.getAuthDataByAuthToken(authToken) == null){
            throw new ResponseException(401, "Error: unauthorized");
        }
        dataAccessObject.removeAuthToken(authToken);
    }
}
