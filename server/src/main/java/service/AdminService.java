package service;

import dataAccess.MemoryDataAccess;
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
