package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import exception.ResponseException;
import model.AuthData;
public class SessionService {
    private final DataAccess dataAccessObject;
    public SessionService(DataAccess data){
        dataAccessObject = data;
    }

    public AuthData createSession(String username, String password) throws ResponseException {
        try {
            var user = dataAccessObject.getUser(username);
            if (user == null) {
                throw new ResponseException(401, "Error: unauthorized");
            } else {
                if (!user.password().equals(password)) {
                    throw new ResponseException(401, "Error: unauthorized");
                } else {
                    return dataAccessObject.generateAuthToken(username);
                }
            }
        }
        catch (DataAccessException e){
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }

    public void endSession(String authToken) throws ResponseException{
        try {
            if (dataAccessObject.getAuthDataByAuthToken(authToken) == null) {
                throw new ResponseException(401, "Error: unauthorized");
            }
            dataAccessObject.removeAuthData(authToken);
        }
        catch (DataAccessException e){
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }
}
