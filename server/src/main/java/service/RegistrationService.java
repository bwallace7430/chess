package service;

import dataaccess.MemoryDataAccess;
public class RegistrationService {
    private MemoryDataAccess dataAccessObject;
    public RegistrationService(MemoryDataAccess data){
        dataAccessObject = data;
    }

    public String register(String username, String password, String email) throws Exception{
        var authToken = "";
        if(dataAccessObject.getUser(username) == null){
            dataAccessObject.createUser(username, password, email);
            dataAccessObject.generateAuthToken(username);
            authToken = dataAccessObject.getAuthToken(username);
        }
        else{
            throw new Exception("A user with that username already exists.");
        }
        return authToken;
    };
}
