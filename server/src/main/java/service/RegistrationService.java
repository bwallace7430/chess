package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import exception.ResponseException;
import model.AuthData;
public class RegistrationService {
    private final DataAccess dataAccessObject;
    public RegistrationService(DataAccess data){
        dataAccessObject = data;
    }

    public AuthData register(String username, String password, String email) throws ResponseException {
        AuthData authData;
        if(username == null || username.isEmpty()){
            throw new ResponseException(400, "Error: bad request");
        }
        try {
            if (dataAccessObject.getUser(username) == null) {
                dataAccessObject.createUser(username, password, email);
            }
            else {
                throw new ResponseException(403, "Error: already taken");
            }
            dataAccessObject.generateAuthToken(username);
            authData = dataAccessObject.getAuthDataByUsername(username);
            return authData;
        }
        catch (DataAccessException e){
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }
}
