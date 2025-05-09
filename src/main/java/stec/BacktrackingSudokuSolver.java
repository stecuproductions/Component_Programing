/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package stec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Backtracking algorithm to solve sudoku.
 * Improved time complexity of the algorithm.
 * 
 * @author jroga
 * @author jstec
 */
public class BacktrackingSudokuSolver extends BaseModel implements SudokuSolver  {
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


        List<Integer> numbers = generateRandomNumbers();


        for (int number : numbers) {
            board.set(row, col, number);
            boolean isValid = 
            board.getRow(row).verify() && board.getColumn(col).verify() && board.getBox(col, row).verify();

            if (isValid) {
                solve(board, newRow, newCol);

                if (solved) {
                    return;
                }
            }
            board.set(row, col, 0); 
        }
    }


    public List<Integer> generateRandomNumbers() {
       List<Integer> result = new ArrayList<>(9);
       for (int i = 1; i <= 9; i++) {
           result.add(i);
       }
       Collections.shuffle(result);
       return result;
    }




    
}
