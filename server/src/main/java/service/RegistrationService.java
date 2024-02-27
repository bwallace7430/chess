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
        if(dataAccessObject.getUser(username) == null){
            try {
                dataAccessObject.createUser(username, password, email);
            }
            catch (DataAccessException e){
                throw new ResponseException(400, "Error: bad request");
            }
            dataAccessObject.generateAuthToken(username);
            authData = dataAccessObject.getAuthDataByUsername(username);
        }
        else {
            throw new ResponseException(403, "Error: already taken");
        }
        return authData;
    }
}
