package server;

import chess.ChessGame;

public record JoinGameRequest(String playerColor, int gameID) {
    public ChessGame.TeamColor convertColorToEnum(){
        switch (playerColor){
            case "WHITE", "white", "White" ->{return ChessGame.TeamColor.WHITE;}
            case "BLACK", "black", "Black" -> {return ChessGame.TeamColor.BLACK;}
            case null, default -> {return null;}
        }
    }
}
