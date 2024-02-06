package chess;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard currBoard;

    public ChessGame() {
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    private boolean isYourTurn(TeamColor team) {
        return (team == teamTurn);
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        var piece = getBoard().getPiece(startPosition);
        var possibleMoves = piece.pieceMoves(getBoard(), startPosition);
        var validMoves = new ArrayList<ChessMove>();
        for (var move : possibleMoves){
            if(canMovePiece(move, piece)){
                validMoves.add(move);
            }
        }
        return validMoves;
    }

    private boolean canMovePiece(ChessMove move, ChessPiece piece) {
        var tempBoard = getBoard();
        var currPieceInNewPos = getBoard().getPiece(move.getEndPosition());
        tempBoard.removePiece(move.getStartPosition());
        tempBoard.addPiece(move.getEndPosition(), piece);
        var isInCheck = isInCheck(piece.getTeamColor());

        tempBoard.removePiece(move.getEndPosition());
        tempBoard.addPiece(move.getStartPosition(), piece);
        tempBoard.addPiece(move.getEndPosition(), currPieceInNewPos);
        return !isInCheck;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        var startPos = move.getStartPosition();
        var endPos = move.getEndPosition();
        var piece = getBoard().getPiece(startPos);

        if(piece == null){
            throw new InvalidMoveException("Move is not valid");
        }
        if (!isYourTurn(piece.getTeamColor())) {
            throw new InvalidMoveException("Move is not valid");
        }

        var validMoves = validMoves(startPos);
        if (validMoves.contains(move)) {
            var newBoard = getBoard();
            newBoard.removePiece(startPos);
            newBoard.addPiece(endPos, piece);
            switch (getTeamTurn()) {
                case BLACK -> setTeamTurn(TeamColor.WHITE);
                case WHITE -> setTeamTurn(TeamColor.BLACK);
            }
        } else {
            throw new InvalidMoveException("Move is not valid");
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        for(int i=1; i<9; i++){
            for(int j=1; j<9; j++){
                var position = new ChessPosition(i, j);
                var piece = getBoard().getPiece(position);
                if(piece == null){
                    continue;
                }
                if(piece.getTeamColor()!=teamColor){
                    var possibleMoves = piece.pieceMoves(getBoard(), position);
                    for(var move:possibleMoves){
                        var pieceBeingCaptured = getBoard().getPiece(move.getEndPosition());
                        if(pieceBeingCaptured == null){
                            continue;
                        }
                        if(pieceBeingCaptured.getPieceType() == ChessPiece.PieceType.KING){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        currBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return currBoard;
    }
}


