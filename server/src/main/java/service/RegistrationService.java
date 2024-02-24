package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import model.AuthData;
import model.UserData;
public class RegistrationService {
    private final MemoryDataAccess dataAccessObject;
    public RegistrationService(MemoryDataAccess data){
        dataAccessObject = data;
    }

    public AuthData register(String username, String password, String email) throws DataAccessException{
        AuthData authToken;
        if(dataAccessObject.getUser(username) == null){
            dataAccessObject.createUser(username, password, email);
            dataAccessObject.generateAuthToken(username);
            authToken = dataAccessObject.getAuthToken(username);
        }
        else{
            throw new DataAccessException("A user with that username already exists.");
        }
        return authToken;
    }
}
