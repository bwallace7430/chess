package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import model.AuthData;
import model.UserData;
public class SessionService {
    private final MemoryDataAccess dataAccessObject;
    public SessionService(MemoryDataAccess data){
        dataAccessObject = data;
    }

    public AuthData createSession(String username, String password) throws DataAccessException{
        var user = dataAccessObject.getUser(username);
        if(user == null){
            throw new DataAccessException("Can not log in. This user does not exist.");
        }
        else{
            if(user.password() != password){
                throw new DataAccessException("Can not log in. Incorrect password given.");
            }
            else{
                dataAccessObject.generateAuthToken(username);
                return dataAccessObject.getAuthToken(username);
            }
        }
    }
}
