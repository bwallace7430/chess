package service;

import dataAccess.DataAccess;
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
