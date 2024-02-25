package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import model.AuthData;
import model.UserData;
public class AdminService {
    private final MemoryDataAccess dataAccessObject;
    public AdminService(MemoryDataAccess data){
        dataAccessObject = data;
    }

    public void clearDatabase() {
       dataAccessObject.deleteGames();
       dataAccessObject.deleteUsers();
       dataAccessObject.deleteAuths();
    }
}
