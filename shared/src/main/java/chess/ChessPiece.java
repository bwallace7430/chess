package chess;

import org.junit.jupiter.api.Assertions;

import java.util.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType pieceType;
    public ChessPiece(ChessGame.TeamColor color, ChessPiece.PieceType type) {
        this.pieceColor = color;
        this.pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (this.pieceType == PieceType.BISHOP){
            return new BishopMovements(this);
            var x = new HashSet<ChessMove>(List.of(new ChessMove(new ChessPosition(1, 1), new ChessPosition(8, 8), null)));
            var y = new HashSet<ChessMove>(List.of(new ChessMove(new ChessPosition(1, 1), new ChessPosition(8, 8), null)));

            Assertions.assertEquals(x, y, "something bad is going on");

            return ChessMove.fullDiagonal(myPosition);
        }
        if (this.pieceType == PieceType.ROOK){
            var allMoves = ChessMove.fullVertical(myPosition);
            allMoves.addAll(ChessMove.fullHorizontal((myPosition)));
            return allMoves;
        }
        return null;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, pieceType);
    }
}