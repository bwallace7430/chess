package server;

import chess.ChessGame;

public record JoinGameResponse(ChessGame.TeamColor playerColor, int gameID) {
}
