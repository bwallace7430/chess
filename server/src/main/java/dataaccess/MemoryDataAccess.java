package dataAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import model.UserData;
import model.AuthData;

public class MemoryDataAccess implements DataAccess{
    private Collection<UserData>allUsers = new ArrayList<UserData>();
    private Collection<AuthData>allAuthTokens = new ArrayList<AuthData>();

    public UserData getUser(String username){
        for (UserData user : allUsers){
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
        var newAuthTokenObject = new AuthData(username, authToken);
        allAuthTokens.add(newAuthTokenObject);
    }

    public AuthData getAuthToken(String username){
        for (AuthData authData : allAuthTokens){
            if(authData.username() == username){
                return authData;
            }
        }
        return null;
    }
}
