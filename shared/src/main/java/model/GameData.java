package model;

import chess.ChessGame;
import com.google.gson.*;

import java.util.Collection;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
    public String toString(){
        return new Gson().toJson(this);
    }
}