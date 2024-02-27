package server;

import chess.ChessGame;

public record JoinGameRequest(String playerColor, int gameID) {
    public ChessGame.TeamColor convertColorToEnum(){
        switch (playerColor){
            case "WHITE" ->{return ChessGame.TeamColor.WHITE;}
            case "BLACK" -> {return ChessGame.TeamColor.BLACK;}
            case null, default -> {return null;}
        }
    }
}
