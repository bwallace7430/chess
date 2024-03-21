package ui;
import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ui.EscapeSequences.*;
public class BoardUI {
    private static final Map<ChessPiece.PieceType, String> whitePieceSymbols = new HashMap<>();
    static {
        whitePieceSymbols.put(ChessPiece.PieceType.KING, WHITE_KING);
        whitePieceSymbols.put(ChessPiece.PieceType.QUEEN, WHITE_QUEEN);
        whitePieceSymbols.put(ChessPiece.PieceType.KNIGHT, WHITE_KNIGHT);
        whitePieceSymbols.put(ChessPiece.PieceType.BISHOP, WHITE_BISHOP);
        whitePieceSymbols.put(ChessPiece.PieceType.ROOK, WHITE_ROOK);
        whitePieceSymbols.put(ChessPiece.PieceType.PAWN, WHITE_PAWN);
    }
    private static final Map<ChessPiece.PieceType, String> blackPieceSymbols = new HashMap<>();
    static {
        blackPieceSymbols.put(ChessPiece.PieceType.KING, BLACK_KING);
        blackPieceSymbols.put(ChessPiece.PieceType.QUEEN, BLACK_QUEEN);
        blackPieceSymbols.put(ChessPiece.PieceType.KNIGHT, BLACK_KNIGHT);
        blackPieceSymbols.put(ChessPiece.PieceType.BISHOP, BLACK_BISHOP);
        blackPieceSymbols.put(ChessPiece.PieceType.ROOK, BLACK_ROOK);
        blackPieceSymbols.put(ChessPiece.PieceType.PAWN, BLACK_PAWN);
    }
    private static final String[] header = {"a", "b", "c", "d", "e", "f", "g", "h"};
    private static final String[] backwardHeader = {"h", "g", "f", "e", "d", "c", "b", "a"};

    public static void renderBoard(ChessBoard board) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print("\n\n");

        printHeaders(out, "top");
        for(int i = 0; i < 8; i++){
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_WHITE);
            out.print(" " + (i + 1) + " ");
            for(int j = 0; j < 8; j++){
                drawSquare(out, i+1, j+1, board.getPiece(new ChessPosition(i+1, j+1)));
            }
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_WHITE);
            out.print(" " + (i + 1) + " ");
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print("\n");
        }
        printHeaders(out, "top");

        out.print("\n\n");

        printHeaders(out, "bottom");
        for(int i = 7; i > -1; i--){
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_WHITE);
            out.print(" " + (i + 1) + " ");
            for(int j = 7; j > -1; j--){
                drawSquare(out, i+1, j+1, board.getPiece(new ChessPosition(i+1, j+1)));
            }
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_WHITE);
            out.print(" " + (i + 1) + " ");
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print("\n");
        }
        printHeaders(out, "bottom");
        out.print("\n\n");

        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
    }
    private static void printHeaders(PrintStream out, String boardPosition){
        out.print(SET_TEXT_COLOR_WHITE);
        out.print("  ");
        if(boardPosition.equals("top")){
            for(var letter : backwardHeader) {
                out.print(" " + letter + "  ");
            }
        }
        else{
            for(var letter : header) {
                out.print(" " + letter + "  ");
            }
        }
        out.print("\n");
    }
    private static void drawSquare(PrintStream out, int rowNum, int colNum, ChessPiece piece){
        boolean isRowEven = rowNum % 2 == 0;
        boolean isColEven = colNum % 2 == 0;
        boolean rowColMatch = (isRowEven && isColEven) || (!isRowEven && !isColEven);
        if(rowColMatch){
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print(SET_TEXT_COLOR_DARK_GREY);
        }
        else {
            out.print(SET_BG_COLOR_WHITE);
            out.print(SET_TEXT_COLOR_WHITE);
        }
        if(piece == null){
            out.print(whitePieceSymbols.get(ChessPiece.PieceType.PAWN));
        }
        else if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
            out.print(SET_TEXT_COLOR_BLUE);
            out.print(whitePieceSymbols.get(piece.getPieceType()));
        } else{
            out.print(SET_TEXT_COLOR_BLUE);
            out.print(blackPieceSymbols.get(piece.getPieceType()));
        }
    }
}