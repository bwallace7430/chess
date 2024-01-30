package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoves extends Movements {
    private final ChessBoard board;
    private final ChessPosition position;
    private final ChessPiece piece;
    public KnightMoves(ChessBoard board, ChessPosition position) {
        super(board, position);
        this.board = board;
        this.position = position;
        piece = board.getPiece(this.position);
    }

    private boolean canBeTaken(ChessPosition position){
        return position != this.position && (position.isEmpty(board) || board.getPiece(position).isOpponent(piece));
    }

    @Override
    public Collection<ChessMove> getPieceMoves() {
        var possibleMoves = new ArrayList<ChessMove>();
        String[] movesString = {"up", "up", "right"};
        if(canBeTaken(anyMove(movesString))){
            possibleMoves.add(new ChessMove(position, anyMove(movesString)));
        }
        movesString = new String[]{"up", "up", "left"};
        if(canBeTaken(anyMove(movesString))){
            possibleMoves.add(new ChessMove(position, anyMove(movesString)));
        }
        movesString = new String[]{"left", "left", "up"};
        if(canBeTaken(anyMove(movesString))){
            possibleMoves.add(new ChessMove(position, anyMove(movesString)));
        }
        movesString = new String[]{"left", "left", "down"};
        if(canBeTaken(anyMove(movesString))){
            possibleMoves.add(new ChessMove(position, anyMove(movesString)));
        }
        movesString = new String[]{"down", "down", "left"};
        if(canBeTaken(anyMove(movesString))){
            possibleMoves.add(new ChessMove(position, anyMove(movesString)));
        }
        movesString = new String[]{"down", "down", "right"};
        if(canBeTaken(anyMove(movesString))){
            possibleMoves.add(new ChessMove(position, anyMove(movesString)));
        }
        movesString = new String[]{"right", "right", "down"};
        if(canBeTaken(anyMove(movesString))){
            possibleMoves.add(new ChessMove(position, anyMove(movesString)));
        }
        movesString = new String[]{"right", "right", "up"};
        if(canBeTaken(anyMove(movesString))){
            possibleMoves.add(new ChessMove(position, anyMove(movesString)));
        }
        return possibleMoves;
    }
}