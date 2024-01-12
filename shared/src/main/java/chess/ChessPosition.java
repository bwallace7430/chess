package chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private final int rowNum;
    private final int colNum;
    private boolean takenStatus;

    public ChessPosition(int row, int col) {
        this.rowNum = row;
        this.colNum = col;
        this.takenStatus = false;
    }
    public ChessPosition(int row, int col, boolean taken) {
        this.rowNum = row;
        this.colNum = col;
        this.takenStatus = taken;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return this.rowNum;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return this.colNum;
    }

    public boolean getStatus() {
        return this.takenStatus;
    }

    public void updateStatus(boolean taken) {
        this.takenStatus = taken;
    }
}
