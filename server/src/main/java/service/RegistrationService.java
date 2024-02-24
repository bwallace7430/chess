package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
public class RegistrationService {
    private final MemoryDataAccess dataAccessObject;
    public RegistrationService(MemoryDataAccess data){
        dataAccessObject = data;
    }

    public model.AuthData register(String username, String password, String email) throws DataAccessException{
        model.AuthData authToken;
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
