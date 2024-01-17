package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    private final ChessPosition startPosition;
    private ChessPosition endPosition;
    private ChessPiece.PieceType promotionPiece;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
    public ChessMove(ChessPosition startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return this.startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return this.endPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        throw new RuntimeException("Not implemented");
    }

    public ChessPosition upRightDiagonal(ChessPosition startPosition) {
        var row = startPosition.getRow();
        var col = startPosition.getColumn();
        row += 1;
        col += 1;
        return new ChessPosition(row, col);
    }
    public ChessPosition upLeftDiagonal(ChessPosition startPosition) {
        var row = startPosition.getRow();
        var col = startPosition.getColumn();
        row += 1;
        col -= 1;
        return new ChessPosition(row, col);
    }
    public ChessPosition downLeftDiagonal(ChessPosition startPosition) {
        var row = startPosition.getRow();
        var col = startPosition.getColumn();
        row -= 1;
        col -= 1;
        return new ChessPosition(row, col);
    }
    public ChessPosition downRightDiagonal(ChessPosition startPosition) {
        var row = startPosition.getRow();
        var col = startPosition.getColumn();
        row -= 1;
        col += 1;
        return new ChessPosition(row, col);
    }
    public ChessPosition moveUp(ChessPosition startPosition) {
        var row = startPosition.getRow();
        var col = startPosition.getColumn();
        row += 1;
        return new ChessPosition(row, col);
    }
    public ChessPosition moveDown(ChessPosition startPosition) {
        var row = startPosition.getRow();
        var col = startPosition.getColumn();
        row -= 1;
        return new ChessPosition(row, col);
    }
    public ChessPosition moveLeft(ChessPosition startPosition) {
        var row = startPosition.getRow();
        var col = startPosition.getColumn();
        col -= 1;
        return new ChessPosition(row, col);
    }
    public ChessPosition moveRight(ChessPosition startPosition) {
        var row = startPosition.getRow();
        var col = startPosition.getColumn();
        col += 1;
        return new ChessPosition(row, col);
    }
    public Collection<ChessMove> fullDiagonal(ChessPosition startPosition) {
        var tempPosition = startPosition;
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        while (tempPosition.getRow() > 1 && tempPosition.getColumn() > 1) {
            tempPosition = downLeftDiagonal(tempPosition);
            possibleMoves.add(new ChessMove(startPosition, tempPosition));
        }
        tempPosition = startPosition;
        while (tempPosition.getRow() < 8 && tempPosition.getColumn() < 8) {
            tempPosition = upRightDiagonal(tempPosition);
            possibleMoves.add(new ChessMove(startPosition, tempPosition));
        }
        tempPosition = startPosition;
        while (tempPosition.getRow() < 8 && tempPosition.getColumn() > 1) {
            tempPosition = upLeftDiagonal(tempPosition);
            possibleMoves.add(new ChessMove(startPosition, tempPosition));
        }
        tempPosition = startPosition;
        while (tempPosition.getRow() > 1 && tempPosition.getColumn() < 8) {
            tempPosition = downRightDiagonal(tempPosition);
            possibleMoves.add(new ChessMove(startPosition, tempPosition));
        }
        return possibleMoves;
    }
}
