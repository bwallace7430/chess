package model;

import chess.ChessGame;
import com.google.gson.*;

import java.util.Collection;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game, Collection<String> observers) {
    public String toString(){
        return new Gson().toJson(this);
    }
    public void addObserver(String username){
        if(!observers.contains(username)) {
            observers.add(username);
        }
    }
}