/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package stec;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Backtracking algorithm to solve sudoku.
 * Improved time complexity of the algorithm.
 * 
 * @author jroga
 * @author jstec
 */
public class BacktrackingSudokuSolver implements SudokuSolver {
    private boolean solved = false;
    


    @Override
    public void solve(SudokuBoard board, int row, int col) {
        if (solved) {
            return;
        }

        if (row == 9) {
            solved = true; 
            return;
        }

        int newRow = (col == 8) ? row + 1 : row;
        int newCol = (col == 8) ? 0 : col + 1;


        int[] numbers = generateRandomNumbers();


        for (int number : numbers) {
            board.set(row, col, number);
            boolean isValid = board.getRow(row).verify() && board.getColumn(col).verify() && board.getBox(col, row).verify();

            if (isValid) {
                solve(board, newRow, newCol);

                if (solved) {
                    return;
                }
            }
            board.set(row, col, 0); 
        }
    }





    public int[] generateRandomNumbers() {
            Random random = new Random();
            int size = 9;
            List<Integer> sudokuNumbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
            int[] result = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            for (int iterator = 0; iterator < size; iterator++) {
                int randomIndex = random.nextInt(sudokuNumbers.size());
                result[iterator] = sudokuNumbers.get(randomIndex);
                sudokuNumbers.remove(randomIndex);
            }
            return result;
    }





    
}
