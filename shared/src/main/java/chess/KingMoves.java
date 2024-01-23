package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoves extends Movements {
    public KingMoves(ChessBoard board, ChessPosition position) {
        super(board, position);
    }
    private ChessMove oneSquareMove(String direction) {

        /*
        if squareisBlocked (piece is there and on our team)
            return null
        else
            return new ChessMove(...)
         */
        if (moveOneSquare(direction, position).isValid()) {
            if (board.getPiece(moveOneSquare(direction, position)) != null) {
                if (!onTeam(this.board.getPiece(moveOneSquare(direction, position)))) {
                    var newMove = new ChessMove(position, moveOneSquare(direction, position));
                    return newMove;
                }
                return null;
            }
            var newMove = new ChessMove(position, moveOneSquare(direction, position));
            return newMove;
        }
        return null;
    }
    @Override
    public Collection<ChessMove> getMovesForPiece() {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        if (!(oneSquareMove("up") == null)){
            possibleMoves.add(oneSquareMove("up"));
        }
        if (!(oneSquareMove("down") == null)){
            possibleMoves.add(oneSquareMove("down"));
        }
        if (!(oneSquareMove("left") == null)){
            possibleMoves.add(oneSquareMove("left"));
        }
        if (!(oneSquareMove("right") == null)){
            possibleMoves.add(oneSquareMove("right"));
        }
        if (!(oneSquareMove("upper left") == null)){
            possibleMoves.add(oneSquareMove("upper left"));
        }
        if (!(oneSquareMove("upper right") == null)){
            possibleMoves.add(oneSquareMove("upper right"));
        }
        if (!(oneSquareMove("lower left") == null)){
            possibleMoves.add(oneSquareMove("lower left"));
        }
        if (!(oneSquareMove("lower right") == null)){
            possibleMoves.add(oneSquareMove("lower right"));
        }
        return possibleMoves;
    }
}
