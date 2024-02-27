package chess.movements;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoves extends Movements {
    private final ChessBoard board;
    private final ChessPosition position;
    private final ChessPiece piece;
    public KingMoves(ChessBoard board, ChessPosition position) {
        super(board, position);
        this.board = board;
        this.position = position;
        piece = board.getPiece(this.position);
    }

    private boolean canBeTaken(String direction, ChessPosition position){
        ChessPosition possiblePosition = null;
        possiblePosition = moveOneSquare(direction, position);
        return possiblePosition != position && (possiblePosition.isEmpty(board) || board.getPiece(possiblePosition).isOpponent(piece));
    }
    @Override
    public Collection<ChessMove> getPieceMoves() {
        var possibleMoves = new ArrayList<ChessMove>();
        if(canBeTaken("up", position)){
            possibleMoves.add(new ChessMove(position, moveOneSquare("up", position)));
        }
        if(canBeTaken("down", position)){
            possibleMoves.add(new ChessMove(position, moveOneSquare("down", position)));
        }
        if(canBeTaken("left", position)){
            possibleMoves.add(new ChessMove(position, moveOneSquare("left", position)));
        }
        if(canBeTaken("right", position)){
            possibleMoves.add(new ChessMove(position, moveOneSquare("right", position)));
        }
        if(canBeTaken("upper right", position)){
            possibleMoves.add(new ChessMove(position, moveOneSquare("upper right", position)));
        }
        if(canBeTaken("upper left", position)){
            possibleMoves.add(new ChessMove(position, moveOneSquare("upper left", position)));
        }
        if(canBeTaken("lower right", position)){
            possibleMoves.add(new ChessMove(position, moveOneSquare("lower right", position)));
        }
        if(canBeTaken("lower left", position)){
            possibleMoves.add(new ChessMove(position, moveOneSquare("lower left", position)));
        }
        return possibleMoves;
    }
}