package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoves extends Movements {
    public KnightMoves(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    private Collection<ChessMove> lMoves(){
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        if (upUpRight().isValid()) {
            if (board.getPiece(upUpRight()) != null) {
                if (!onTeam(this.board.getPiece(upUpRight()))) {
                    var newMove = new ChessMove(position, upUpRight());
                    possibleMoves.add(newMove);
                }
            } else{
                var newMove = new ChessMove(position, upUpRight());
                possibleMoves.add(newMove);
            }
        }
        if (upUpLeft().isValid()){
            if (board.getPiece(upUpLeft()) != null) {
                if (!onTeam(this.board.getPiece(upUpLeft()))) {
                    var newMove = new ChessMove(position, upUpLeft());
                    possibleMoves.add(newMove);
                }
            } else{
                var newMove = new ChessMove(position, upUpLeft());
                possibleMoves.add(newMove);
            }
        }
        if (leftLeftDown().isValid()){
            if (board.getPiece(leftLeftDown()) != null) {
                if (!onTeam(this.board.getPiece(leftLeftDown()))) {
                    var newMove = new ChessMove(position, leftLeftDown());
                    possibleMoves.add(newMove);
                }
            } else{
                var newMove = new ChessMove(position, leftLeftDown());
                possibleMoves.add(newMove);
            }
        }
        if (leftLeftUp().isValid()){
            if (board.getPiece(leftLeftUp()) != null) {
                if (!onTeam(this.board.getPiece(leftLeftUp()))) {
                    var newMove = new ChessMove(position, leftLeftUp());
                    possibleMoves.add(newMove);
                }
            } else{
                var newMove = new ChessMove(position, leftLeftUp());
                possibleMoves.add(newMove);
            }
        }
        if (downDownLeft().isValid()){
            if (board.getPiece(downDownLeft()) != null) {
                if (!onTeam(this.board.getPiece(downDownLeft()))) {
                    var newMove = new ChessMove(position, downDownLeft());
                    possibleMoves.add(newMove);
                }
            } else{
                var newMove = new ChessMove(position, downDownLeft());
                possibleMoves.add(newMove);
            }
        }
        if (downDownRight().isValid()){
            if (board.getPiece(downDownRight()) != null) {
                if (!onTeam(this.board.getPiece(downDownRight()))) {
                    var newMove = new ChessMove(position, downDownRight());
                    possibleMoves.add(newMove);
                }
            } else{
                var newMove = new ChessMove(position, downDownRight());
                possibleMoves.add(newMove);
            }
        }
        if (rightRightUp().isValid()){
            if (board.getPiece(rightRightUp()) != null) {
                if (!onTeam(this.board.getPiece(rightRightUp()))) {
                    var newMove = new ChessMove(position, rightRightUp());
                    possibleMoves.add(newMove);
                }
            } else{
                var newMove = new ChessMove(position, rightRightUp());
                possibleMoves.add(newMove);
            }
        }
        if (rightRightDown().isValid()){
            if (board.getPiece(rightRightDown()) != null) {
                if (!onTeam(this.board.getPiece(rightRightDown()))) {
                    var newMove = new ChessMove(position, rightRightDown());
                    possibleMoves.add(newMove);
                }
            } else{
                var newMove = new ChessMove(position, rightRightDown());
                possibleMoves.add(newMove);
            }
        }
        return possibleMoves;
    }
    private ChessPosition upUpRight(){
        return moveOneSquare("right", moveOneSquare("up", moveOneSquare("up", position)));
    }
    private ChessPosition upUpLeft(){
        return moveOneSquare("left", moveOneSquare("up", moveOneSquare("up", position)));
    }
    private ChessPosition leftLeftUp(){
        return moveOneSquare("up", moveOneSquare("left", moveOneSquare("left", position)));
    }
    private ChessPosition leftLeftDown(){
        return moveOneSquare("down", moveOneSquare("left", moveOneSquare("left", position)));
    }
    private ChessPosition downDownLeft(){
        return moveOneSquare("left", moveOneSquare("down", moveOneSquare("down", position)));
    }
    private ChessPosition downDownRight(){
        return moveOneSquare("right", moveOneSquare("down", moveOneSquare("down", position)));
    }
    private ChessPosition rightRightDown(){
        return moveOneSquare("down", moveOneSquare("right", moveOneSquare("right", position)));
    }
    private ChessPosition rightRightUp(){
        return moveOneSquare("up", moveOneSquare("right", moveOneSquare("right", position)));
    }
    @Override
    public Collection<ChessMove> getMovesForPiece() {
        return lMoves();
    }
}