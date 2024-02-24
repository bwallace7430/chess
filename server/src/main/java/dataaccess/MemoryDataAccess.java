package dataAccess;

import java.util.Collection;
import java.util.UUID;

public class MemoryDataAccess implements DataAccess{
    private Collection<model.UserData>allUsers;
    private Collection<model.AuthData>allAuthTokens;

    public model.UserData getUser(String username){
        for (model.UserData user : allUsers){
            if(user.username() == username){
                return user;
            }
        }
        return null;
    }

    public void createUser(String username, String password, String email){
        var newUser = new model.UserData(username, password, email);
        allUsers.add(newUser);
    }

    public void generateAuthToken(String username){
        var authToken = UUID.randomUUID().toString();
        var newAuthTokenObject = new model.AuthData(username, authToken);
    }

    public model.AuthData getAuthToken(String username){
        for (model.AuthData authData : allAuthTokens){
            if(authData.username() == username){
                return authData;
            }
        }
        return null;
    }
}
