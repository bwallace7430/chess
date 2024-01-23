package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class PawnMoves extends Movements {
    private ChessPiece piece;
    private String direction;

    public PawnMoves(ChessBoard board, ChessPosition position) {
        super(board, position);
        this.piece = board.getPiece(position);
        if (this.piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            this.direction = "up";
        } else {
            this.direction = "down";
        }
    }

    private ChessMove basicMove() {
        if (!(moveOneSquare(this.direction, this.position) == null)) {
            return new ChessMove(this.position, moveOneSquare(this.direction, this.position));
        }
        return null;
    }

    private ChessMove initialMove() {
        ChessPosition tempPosition = null;
        if (!(moveOneSquare(this.direction, this.position) == null)) {
            tempPosition = moveOneSquare(this.direction, this.position);
        }
        if (!(tempPosition == null) && !(moveOneSquare(this.direction, tempPosition) == null)) {
            var newPosition = moveOneSquare(this.direction, tempPosition);
            return new ChessMove(this.position, newPosition);
        }
        return null;
    }

    private ArrayList<ChessPosition> captureMove() {
        ChessPosition rightDiagonal = null;
        ChessPosition leftDiagonal = null;
        ArrayList<ChessPosition> diagonalSquares = new ArrayList<>();
        if (Objects.equals(this.direction, "up")) {
            if (moveOneSquare("upper right", this.position).isValid()) {
                rightDiagonal = moveOneSquare("upper right", this.position);
                diagonalSquares.add(rightDiagonal);
            }
            if (moveOneSquare("upper left", this.position).isValid()) {
                leftDiagonal = moveOneSquare("upper left", this.position);
                diagonalSquares.add(leftDiagonal);
            }
        } else {
            if (moveOneSquare("lower right", this.position).isValid()) {
                rightDiagonal = moveOneSquare("lower right", this.position);
                diagonalSquares.add(rightDiagonal);
            }
            if (moveOneSquare("lower left", this.position).isValid()) {
                leftDiagonal = moveOneSquare("lower left", this.position);
                diagonalSquares.add(leftDiagonal);
            }
        }
        return diagonalSquares;
    }

    @Override
    public Collection<ChessMove> getMovesForPiece() {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        // consider breaking down pieces of complex conditions into variables to make it easier to read

        // boolean pieceIsBlack = ...
        // boolean onBlackHomeRow = position == ...
        // boolean pieceIsWhite = this.direction == ...
        // boolean onWhiteHomeRow = position == ...
        // boolean isOnHomeRow = pieceIsBlack && onBlackHomeRow || pieceIsWhite && onWhiteHomeRow

        if ((this.direction.equals("up") && this.position.getRow() == 2) || (this.direction.equals("down") && this.position.getRow() == 7)) { // magic number: replace with e.g. const int WHITE_HOME_RANK = 2
            //check if space is taken or if valid
            var bm = basicMove();
            if (!(bm == null) && (board.getPiece(bm.getEndPosition()) == null)) {
                possibleMoves.add(bm);

                var im = initialMove();
                if (!(im == null) && (board.getPiece(im.getEndPosition()) == null)) {
                    possibleMoves.add(im);
                }
            }
        }
        var bm = basicMove();
        if (!(bm == null) && (board.getPiece(bm.getEndPosition()) == null)) {
            possibleMoves.add(bm);
        }
        for (var square : captureMove()) {
            if (!(board.getPiece(square) == null)) {
                if (!onTeam(board.getPiece(square))) {
                    var newMove = new ChessMove(this.position, square);
                    possibleMoves.add(newMove);
                }

            }
        }
        return possibleMoves;
    }
}

