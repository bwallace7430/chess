package service;

import dataAccess.DataAccess;
import dataAccess.MemoryDataAccess;
public class AdminService {
    private final DataAccess dataAccessObject;
    public AdminService(DataAccess data){
        dataAccessObject = data;
    }

    public void clearDatabase() {
       dataAccessObject.deleteGames();
       dataAccessObject.deleteUsers();
       dataAccessObject.deleteAuths();
    }
}
