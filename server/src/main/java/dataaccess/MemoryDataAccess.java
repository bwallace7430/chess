package dataaccess;
import dataObjects.AuthToken;
import dataObjects.User;

import java.util.Collection;
import java.util.HashMap;
public class MemoryDataAccess implements DataAccess{
    private Collection<User>allUsers;
    private Collection<AuthToken>allAuthTokens;

    public User getUser(String username){
        for (User user : allUsers){
            if(user.username() == username){
                return user;
            }
        }
        return null;
    }

    public void createUser(String username, String password, String email){
        var newUser = new User(username, password, email);
        allUsers.add(newUser);
    }

    public void generateAuthToken(String username){

        var newAuthToken = new AuthToken(username, )
    }
}
