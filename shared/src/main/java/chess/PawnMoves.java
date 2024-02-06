package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class PawnMoves extends Movements {
    private final ChessBoard board;
    private final ChessPosition position;
    private final ChessPiece piece;
    private final String direction;

    public PawnMoves(ChessBoard board, ChessPosition position) {
        super(board, position);
        String direction1;
        this.board = board;
        this.position = position;
        piece = board.getPiece(position);
        direction1 = "";
        switch (piece.getTeamColor()) {
            case BLACK -> direction1 = "down";
            case WHITE -> direction1 = "up";
        }
        this.direction = direction1;
    }

    private boolean canBeTakenForward(ChessPosition position) {
        return position != this.position && position.isEmpty(board);
    }

    private boolean canBeTakenCorner(ChessPosition position) {
        if(!position.isValid()){
            return false;
        }
        if (position.isEmpty(this.board)){
            return false;
        }
        return position != this.position && (board.getPiece(position).isOpponent(piece));
    }

    private ChessPosition forwardMove() {
        if (canBeTakenForward(moveOneSquare(direction, position))) {
            return moveOneSquare(direction, position);
        }
        return position;
    }

    private ChessPosition initialMove(){
        ChessPosition oneMove = moveOneSquare(direction, position);
        ChessPosition twoMoves = moveOneSquare(direction, oneMove);
        switch (direction){
            case "up" ->{
                if (position.getRow() == 2 && canBeTakenForward(oneMove) && canBeTakenForward(twoMoves)){
                    return twoMoves;
                }
            }
            case "down" ->{
                if (position.getRow() == 7 && canBeTakenForward(oneMove) && canBeTakenForward(twoMoves)){
                    return twoMoves;
                }
            }
        }
        return position;
    }

    private Collection<ChessMove> captureMoves() {
        var possibleMoves = new ArrayList<ChessMove>();
        var startRow = position.getRow();
        var startCol = position.getColumn();
        ChessPosition leftCorner = null;
        ChessPosition rightCorner = null;
        switch (direction) {
            case "up" -> {
                leftCorner = new ChessPosition(startRow + 1, startCol - 1);
                rightCorner = new ChessPosition(startRow + 1, startCol + 1);
                if (canBeTakenCorner(leftCorner)) {
                    possibleMoves.add(new ChessMove(position, leftCorner));
                }
                if (canBeTakenCorner(rightCorner)) {
                    possibleMoves.add(new ChessMove(position, rightCorner));
                }
            }
            case "down" -> {
                leftCorner = new ChessPosition(startRow - 1, startCol - 1);
                rightCorner = new ChessPosition(startRow - 1, startCol + 1);
                if (canBeTakenCorner(leftCorner)) {
                    possibleMoves.add(new ChessMove(position, leftCorner));
                }
                if (canBeTakenCorner(rightCorner)) {
                    possibleMoves.add(new ChessMove(position, rightCorner));
                }
            }
        }
        return possibleMoves;
    }
    private Collection<ChessMove> basicMoves() {
        var possibleMoves = new ArrayList<ChessMove>();
        if(canBeTakenForward(forwardMove())){
            possibleMoves.add(new ChessMove(position,forwardMove()));
        }
        if(canBeTakenForward(initialMove())){
            possibleMoves.add(new ChessMove(position,initialMove()));
        }
        possibleMoves.addAll(captureMoves());
        return possibleMoves;
    }

    private Collection<ChessMove> movesWithPromos(){
        var allMoves = new HashSet<ChessMove>();
        var possibleMoves = basicMoves();
        ChessPosition endPosition = null;
        ChessPosition startPosition = null;

        for(var move : possibleMoves){
            switch (direction) {
                case "up" -> {
                    endPosition = move.getEndPosition();
                    startPosition = move.getStartPosition();
                    if(endPosition.getRow() == 8){
                        allMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                        allMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                        allMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                        allMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    }
                    else{
                        allMoves.add(move);
                    }
                }
                case "down" -> {
                    endPosition = move.getEndPosition();
                    startPosition = move.getStartPosition();
                    if(endPosition.getRow() == 1){
                        allMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                        allMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                        allMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                        allMoves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    }
                    else{
                        allMoves.add(move);
                    }
                }
            }
        }
        return allMoves;
    }
    @Override
    public Collection<ChessMove> getPieceMoves() {
        return movesWithPromos();
    }
}
