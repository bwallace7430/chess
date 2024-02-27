package chess.movements;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class BishopMoves extends Movements {
    public BishopMoves(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> getPieceMoves() {return fullDiagonal();}
}
