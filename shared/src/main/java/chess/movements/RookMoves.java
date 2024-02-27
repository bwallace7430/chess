package chess.movements;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoves extends Movements {
    public RookMoves(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> getPieceMoves() {
        var possibleMoves = new ArrayList<ChessMove>();
        possibleMoves.addAll(fullHorizontal());
        possibleMoves.addAll(fullVertical());
        return possibleMoves;
    }
}