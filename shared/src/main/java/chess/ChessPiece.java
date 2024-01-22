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
        switch (this.pieceType) {
            case ROOK -> {
                return new RookMoves(board, myPosition).getMovesForPiece();
            }
            case BISHOP -> {
                return new BishopMoves(board, myPosition).getMovesForPiece();
            }
            case QUEEN -> {
                return new QueenMoves(board, myPosition).getMovesForPiece();
            }
//            case KING -> {
//                return new KingMoves(board, myPosition).getMovesForPiece();
//            }
//            case PAWN -> {
//                return new PawnMoves(board, myPosition).getMovesForPiece();
//            }
            case KNIGHT -> {
                return new KnightMoves(board, myPosition).getMovesForPiece();
            }
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