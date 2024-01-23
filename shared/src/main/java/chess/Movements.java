package chess;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Movements {
    protected final ChessPiece piece;
    protected final ChessBoard board;
    protected final ChessPosition position;

    public Movements(ChessBoard board, ChessPosition position) {
        this.board = board;
        this.position = position;
        this.piece = this.board.getPiece(this.position);
    }

    public abstract Collection<ChessMove> getMovesForPiece();

    protected boolean onTeam(ChessPiece piece2) {
        return this.piece.getTeamColor() == piece2.getTeamColor();
    }

    protected ChessPosition moveOneSquare(String direction, ChessPosition position) {
        switch (direction) {
            case "up" -> {
                var newRow = position.getRow() + 1;
                return new ChessPosition(newRow, position.getColumn());
            }
            case "down" -> {
                var newRow = position.getRow() - 1;
                return new ChessPosition(newRow, position.getColumn());
            }
            case "left" -> {
                var newColumn = position.getColumn() - 1;
                return new ChessPosition(position.getRow(), newColumn);
            }
            case "right" -> {
                var newColumn = position.getColumn() + 1;
                return new ChessPosition(position.getRow(), newColumn);
            }
            case "upper right" -> {
                var newColumn = position.getColumn() + 1;
                var newRow = position.getRow() + 1;
                return new ChessPosition(newRow, newColumn);
            }
            case "upper left" -> {
                var newColumn = position.getColumn() - 1;
                var newRow = position.getRow() + 1;
                return new ChessPosition(newRow, newColumn);
            }
            case "lower left" -> {
                var newColumn = position.getColumn() - 1;
                var newRow = position.getRow() - 1;
                return new ChessPosition(newRow, newColumn);
            }
            case "lower right" -> {
                var newColumn = position.getColumn() + 1;
                var newRow = position.getRow() - 1;
                return new ChessPosition(newRow, newColumn);
            }
        }
        throw new RuntimeException("Invalid move direction!");
    }

    protected Collection<ChessMove> moveHorizontal() {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        var currPosition = this.position;
        while (currPosition.getColumn() != 8) {
            var newPosition = moveOneSquare("right", currPosition);
            if (this.board.getPiece(newPosition) == null) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(this.board.getPiece(newPosition))) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        currPosition = this.position;
        while (currPosition.getColumn() != 1) {
            var newPosition = moveOneSquare("left", currPosition);
            if (this.board.getPiece(newPosition) == null) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(this.board.getPiece(newPosition))) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        return possibleMoves;
    }

    protected Collection<ChessMove> moveVertical() {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        var currPosition = this.position;
        while (currPosition.getRow() != 8) {
            var newPosition = moveOneSquare("up", currPosition);
            if (this.board.getPiece(newPosition) == null) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(this.board.getPiece(newPosition))) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        currPosition = this.position;
        while (currPosition.getRow() != 1) {
            var newPosition = moveOneSquare("down", currPosition);
            if (this.board.getPiece(newPosition) == null) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(this.board.getPiece(newPosition))) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        return possibleMoves;
    }

    protected Collection<ChessMove> moveDiagonal() {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        var currPosition = this.position;
        while (currPosition.getColumn() != 8 && currPosition.getRow() != 8) {
            var newPosition = moveOneSquare("upper right", currPosition);
            if (this.board.getPiece(newPosition) == null) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(this.board.getPiece(newPosition))) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        currPosition = this.position;
        while (currPosition.getColumn() > 1 && currPosition.getRow() < 8) {
            var newPosition = moveOneSquare("upper left", currPosition);
            if (this.board.getPiece(newPosition) == null) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(this.board.getPiece(newPosition))) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        currPosition = this.position;
        while (currPosition.getColumn() != 1 && currPosition.getRow() != 1) {
            var newPosition = moveOneSquare("lower left", currPosition);
            if (this.board.getPiece(newPosition) == null) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(this.board.getPiece(newPosition))) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        currPosition = this.position;
        while (currPosition.getColumn() != 8 && currPosition.getRow() != 1) {
            var newPosition = moveOneSquare("lower right", currPosition);
            if (this.board.getPiece(newPosition) == null) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                currPosition = newPosition;
            } else if (!onTeam(this.board.getPiece(newPosition))) {
                var move = new ChessMove(this.position, newPosition);
                possibleMoves.add(move);
                break;
            } else {
                break;
            }
        }
        return possibleMoves;
    }
}