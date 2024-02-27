package chess.movements;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Collection;
import java.util.ArrayList;

public abstract class Movements {
    private final ChessPiece piece;
    private final ChessBoard board;
    private final ChessPosition position;

    public Movements(ChessBoard board, ChessPosition position) {
        this.board = board;
        this.position = position;
        this.piece = this.board.getPiece(this.position);
    }

    protected ChessPosition moveOneSquare(String direction, ChessPosition position){
        var startRow = position.getRow();
        var startCol = position.getColumn();
        ChessPosition newPosition = null;
        switch(direction){
            case "up" -> {
                newPosition = new ChessPosition(startRow + 1, startCol);
            }
            case "down" -> {
                newPosition = new ChessPosition(startRow - 1, startCol);
            }
            case "right" -> {
                newPosition = new ChessPosition(startRow, startCol+1);
            }
            case "left" -> {
                newPosition = new ChessPosition(startRow, startCol-1);
            }
            case "upper right" -> {
                newPosition = new ChessPosition(startRow + 1, startCol +1);
            }
            case "upper left" -> {
                newPosition = new ChessPosition(startRow +1, startCol-1);
            }
            case "lower right" -> {
                newPosition = new ChessPosition(startRow - 1, startCol+1);
            }
            case "lower left" -> {
                newPosition = new ChessPosition(startRow - 1, startCol -1);
            }
        }
        if(newPosition.isValid()){
            return newPosition;
        }
        return position;
    }

    protected ChessPosition anyMove(String[] directions){
        var tempPosition = position;
        ChessPosition currPosition = null;
        for (var direction : directions){
            currPosition = tempPosition;
            tempPosition = moveOneSquare(direction, currPosition);
            if(tempPosition == currPosition){
                return position;
            }
        }
        return tempPosition;
    }

    protected Collection<ChessMove> fullLengthMove(String direction){
        var possibleMoves = new ArrayList<ChessMove>();
        var nextPosition = moveOneSquare(direction, position);
        var currPosition = position;
        while(nextPosition != currPosition){
            if(nextPosition.isEmpty(this.board)){
                possibleMoves.add(new ChessMove(position, nextPosition));
                currPosition = nextPosition;
                nextPosition = moveOneSquare(direction, currPosition);
            }
            else if(piece.isOpponent(board.getPiece(nextPosition))){
                possibleMoves.add(new ChessMove(position, nextPosition));
                return possibleMoves;
            }
            else{
                return possibleMoves;
            }
        }
        return possibleMoves;
    }

    protected Collection<ChessMove> fullDiagonal(){
        var possibleMoves = new ArrayList<ChessMove>();
        possibleMoves.addAll(fullLengthMove("upper right"));
        possibleMoves.addAll(fullLengthMove("upper left"));
        possibleMoves.addAll(fullLengthMove("lower left"));
        possibleMoves.addAll(fullLengthMove("lower right"));
        return possibleMoves;
    }

    protected Collection<ChessMove> fullHorizontal(){
        var possibleMoves = new ArrayList<ChessMove>();
        possibleMoves.addAll(fullLengthMove("left"));
        possibleMoves.addAll(fullLengthMove("right"));
        return possibleMoves;
    }

    protected Collection<ChessMove> fullVertical(){
        var possibleMoves = new ArrayList<ChessMove>();
        possibleMoves.addAll(fullLengthMove("up"));
        possibleMoves.addAll(fullLengthMove("down"));
        return possibleMoves;
    }

    public abstract Collection<ChessMove> getPieceMoves();
}