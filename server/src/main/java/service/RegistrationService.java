package service;

import dataaccess.MemoryDataAccess;
public class RegistrationService {
    private MemoryDataAccess dataAccessObject;
    public RegistrationService(MemoryDataAccess data){
        dataAccessObject = data;
    }

    public String register(String username, String password, String email){
        return "";
    };
}
