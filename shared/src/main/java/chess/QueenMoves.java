package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoves extends Movements {
    public QueenMoves(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> getPieceMoves() {
        var possibleMoves = new ArrayList<ChessMove>();
        possibleMoves.addAll(fullHorizontal());
        possibleMoves.addAll(fullVertical());
        possibleMoves.addAll(fullDiagonal());
        return possibleMoves;
    }
}