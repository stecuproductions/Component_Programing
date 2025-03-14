package stec;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

public class SudokuBoardProperLayout {
    private boolean validRows(SudokuBoard sudokuBoard) {
        Set<Integer> rowNumbers = new HashSet<>();
        int[][] board = sudokuBoard.getBoard();
        int SIZE = sudokuBoard.getSize();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] < 1 || board[row][col] > 9) {
                    return false;
                }
                rowNumbers.add(board[row][col]);
            }
            if (rowNumbers.size() != 9) {
                return false;
            }
            rowNumbers.clear();
        }

        return true;
    }

    private Boolean validCols(SudokuBoard sudokuBoard) {
        Set<Integer> colNumbers = new HashSet<>();
        int[][] board = sudokuBoard.getBoard();
        int SIZE = sudokuBoard.getSize();
        for (int col = 0; col < SIZE; col++) {
            for (int row = 0; row < SIZE; row++) {
                if (board[row][col] < 1 || board[row][col] > 9) {
                    return false;
                }
                colNumbers.add(board[row][col]);
            }
            if (colNumbers.size() != 9) {
                return false;
            }
            colNumbers.clear();
        }

        return true;
    }

    private Boolean validSquares(SudokuBoard sudokuBoard) {
        int[][] board = sudokuBoard.getBoard();
        int SIZE = sudokuBoard.getSize();

        for (int rowStart = 0; rowStart < SIZE; rowStart += 3) {
            for (int colStart = 0; colStart < SIZE; colStart += 3) {

                Set<Integer> squareNumbers = new HashSet<>();

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int num = board[rowStart + i][colStart + j];

                        if (num < 1 || num > 9) {
                            return false; // Niepoprawna liczba
                        }
                        squareNumbers.add(num);
                    }
                }
                if (squareNumbers.size() < 9) {
                    return false;
                }
                squareNumbers.clear();
            }
        }

        return true;
    }

    @Test
    public void validSudokuTest() {
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.fillBoard();

        boolean testResult = validCols(sudokuBoard) && validRows(sudokuBoard) && validSquares(sudokuBoard);
        assertTrue(testResult); // Test passed Valid sudoku
    }

    @Test
    void diffrentBoardsTest() {
        SudokuBoard board1 = new SudokuBoard();
        SudokuBoard board2 = new SudokuBoard();
        board1.fillBoard();
        board2.fillBoard();
        assertTrue(board1.getBoard() != board2.getBoard());
    }

}
