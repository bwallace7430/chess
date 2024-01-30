package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private final int rowNum;
    private final int colNum;

    public ChessPosition(int row, int col) {
        rowNum = row;
        colNum = col;
    }
    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return rowNum;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return colNum;
    }

    public boolean isValid(){
        return rowNum <= 8 && colNum <= 8 && rowNum >= 1 && colNum >= 1;
    }

    public boolean isEmpty(ChessBoard board){
        return board.getPiece(this) == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition that = (ChessPosition) o;
        return rowNum == that.rowNum && colNum == that.colNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNum, colNum);
    }
}
