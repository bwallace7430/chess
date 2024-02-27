package server;

import chess.ChessGame;

public record JoinGameRequest(String playerColor, int gameID) {
    public ChessGame.TeamColor convertColorToEnum(){
        ChessGame.TeamColor color = null;
        switch (playerColor){
            case "WHITE" -> color = ChessGame.TeamColor.WHITE;
            case "BLACK" -> color = ChessGame.TeamColor.BLACK;
        }
        return color;
    }
}
