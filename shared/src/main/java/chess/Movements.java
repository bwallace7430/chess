package chess;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Movements {
    private final ChessPiece piece;

    public Movements(ChessPiece piece) {
        this.piece = piece;
    }

    public abstract Collection<ChessMove> getMovesForPiece();

    private boolean onTeam(ChessPiece piece2) {
        return this.piece.getTeamColor() == piece2.getTeamColor();
    }

    private ChessPosition moveOneSquare(String direction, ChessPosition position) {
        if (direction.equals("up")) {
            var newRow = position.getRow() + 1;
            return new ChessPosition(newRow, position.getColumn());
        } else if (direction.equals("down")) {
            var newRow = position.getRow() - 1;
            return new ChessPosition(newRow, position.getColumn());
        } else if (direction.equals("left")) {
            var newColumn = position.getColumn() - 1;
            return new ChessPosition(position.getRow(), newColumn);
        } else if (direction.equals("right")) {
            var newColumn = position.getColumn() + 1;
            return new ChessPosition(position.getRow(), newColumn);
        } else if (direction.equals("upper right")) {
            var newColumn = position.getColumn() + 1;
            var newRow = position.getRow() + 1;
            return new ChessPosition(newRow, newColumn);
        } else if (direction.equals("upper left")) {
            var newColumn = position.getColumn() - 1;
            var newRow = position.getRow() + 1;
            return new ChessPosition(newRow, newColumn);
        } else if (direction.equals("lower left")) {
            var newColumn = position.getColumn() - 1;
            var newRow = position.getRow() - 1;
            return new ChessPosition(newRow, newColumn);
        } else if (direction.equals("lower right")) {
            var newColumn = position.getColumn() + 1;
            var newRow = position.getRow() - 1;
            return new ChessPosition(newRow, newColumn);
        }
        return position;
    }

    private Collection<ChessMove> moveHorizontal(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        var currPosition = position;
        while (currPosition.getColumn() != 8) {
            var newPosition = moveOneSquare("right", currPosition);
            if (board.getPiece(newPosition) == null) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(board.getPiece(newPosition))) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        currPosition = position;
        while (currPosition.getColumn() != 1) {
            var newPosition = moveOneSquare("left", currPosition);
            if (board.getPiece(newPosition) == null) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(board.getPiece(newPosition))) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        return possibleMoves;
    }

    private Collection<ChessMove> moveVertical(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        var currPosition = position;
        while (currPosition.getRow() != 8) {
            var newPosition = moveOneSquare("up", currPosition);
            if (board.getPiece(newPosition) == null) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(board.getPiece(newPosition))) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        currPosition = position;
        while (currPosition.getRow() != 1) {
            var newPosition = moveOneSquare("down", currPosition);
            if (board.getPiece(newPosition) == null) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(board.getPiece(newPosition))) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        return possibleMoves;
    }

    private Collection<ChessMove> moveDiagonal(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        var currPosition = position;
        while (currPosition.getColumn() != 8 && currPosition.getRow() != 8) {
            var newPosition = moveOneSquare("upper right", currPosition);
            if (board.getPiece(newPosition) == null) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(board.getPiece(newPosition))) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        currPosition = position;
        while (currPosition.getColumn() != 8 && currPosition.getRow() != 1) {
            var newPosition = moveOneSquare("upper left", currPosition);
            if (board.getPiece(newPosition) == null) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(board.getPiece(newPosition))) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        currPosition = position;
        while (currPosition.getColumn() != 1 && currPosition.getRow() != 1) {
            var newPosition = moveOneSquare("lower left", currPosition);
            if (board.getPiece(newPosition) == null) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(board.getPiece(newPosition))) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        currPosition = position;
        while (currPosition.getColumn() != 8 && currPosition.getRow() != 1) {
            var newPosition = moveOneSquare("lower right", currPosition);
            if (board.getPiece(newPosition) == null) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(board.getPiece(newPosition))) {
                var move = new ChessMove(currPosition, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        return possibleMoves;
    }
}