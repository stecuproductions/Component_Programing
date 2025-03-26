/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package stec;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jroga
 */
public class SudokuBoardTest {
        private boolean validRows(SudokuBoard sudokuBoard) {
        Set<Integer> rowNumbers = new HashSet<>();
        int size = sudokuBoard.getSize();
        int[][] board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = sudokuBoard.getNum(i, j);
            }
        }
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
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
        int size = sudokuBoard.getSize();
        int[][] board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = sudokuBoard.getNum(i, j);
            }
        }
        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
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
        int size = sudokuBoard.getSize();
        int[][] board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = sudokuBoard.getNum(i, j);
            }
        }
        for (int rowStart = 0; rowStart < size; rowStart += 3) {
            for (int colStart = 0; colStart < size; colStart += 3) {

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
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        boolean testResult = validCols(sudokuBoard) && validRows(sudokuBoard) && validSquares(sudokuBoard);
        assertTrue(testResult); // Test passed Valid sudoku
    }

    @Test
    public void diffrentBoardsTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board1 = new SudokuBoard(solver);
        SudokuBoard board2 = new SudokuBoard(solver);
        board1.solveGame();
        board2.solveGame();
        boolean different = false;
        for (int i = 0; i < board1.getSize(); i++) {
            for (int j = 0; j < board1.getSize(); j++) {
                if (board1.getNum(i, j) != board2.getNum(i, j)) {
                    different = true;
                    break;
                }
            }
            if (different) break;
        }
        assertTrue(different);
    }
    
    @Test
    void testDrawSudoku() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

        board.setNum(0, 0, 5);  

        String output = board.drawSudoku();

        assertTrue(output.startsWith("5 "));

        long newlineCount = output.chars().filter(c -> c == '\n').count();
        assertEquals(9, newlineCount);
    }
    @Test 
    void mainMethodRunning(){
        App myApp=new App();
        
        App.main(new String[]{});
    }


}