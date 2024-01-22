package chess;

import java.util.Collection;

public class BishopMoves extends Movements {
    public BishopMoves(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> getMovesForPiece() {
        return moveDiagonal();
    }
}
