/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package stec;

/**
 * Backtracking algorithm to solve sudoku.
 * @author jroga
 */
public class BacktrackingSudokuSolver implements SudokuSolver {
    @Override
    public void solve(SudokuBoard board, int row, int col) {
         if (row == board.getSize()) {
            return;
        }
        int newRow = (col == board.getSize() - 1) ? row + 1 : row;
        int newCol = (col == board.getSize() - 1) ? 0 : col + 1;

        int[] numbers = board.generateRandomNumbers();

        for (int number : numbers) {
            if (board.isValid(row, col, number)) {
                board.setNum(row,col,number);
                solve(board,newRow, newCol);
                boolean solved = true;
                for (int i = 0;i < board.getSize();i++) {
                    for (int j = 0;j < board.getSize();j++) {
                        if (board.getNum(i, j) == 0) {
                            solved = false;
                        }
                    }
                }
                if (solved == true) {
                    return;
                }
                board.setNum(row,col,0);
            }
        }      
    }
}   

