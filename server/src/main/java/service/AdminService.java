package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import exception.ResponseException;

public class AdminService {
    private final DataAccess dataAccessObject;

    public AdminService(DataAccess data) {
        dataAccessObject = data;
    }

    public void clearDatabase() throws ResponseException {
        try {
            dataAccessObject.deleteGames();
            dataAccessObject.deleteUsers();
            dataAccessObject.deleteAuths();
        } catch (DataAccessException e) {
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
    }
}
