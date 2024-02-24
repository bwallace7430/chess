package dataObjects;

import com.google.gson.Gson;

public record AuthToken(String username, String authToken) {
    public String toString(){
        return new Gson().toJson(this);
    }
}
}
