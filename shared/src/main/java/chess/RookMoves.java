package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoves extends Movements {
    public RookMoves(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> getMovesForPiece() {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        possibleMoves.addAll(moveHorizontal());
        possibleMoves.addAll(moveVertical());
        return possibleMoves;
    }
}