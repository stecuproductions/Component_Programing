package stec;


import stec.model.SudokuBoard;
import stec.solver.BacktrackingSudokuSolver;
import stec.solver.SudokuSolver;

/** Hello world!. */
public class App {
  public static void main(String[] args) {
    SudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard board = new SudokuBoard(solver);
    board.solveGame();
    System.out.println(board.drawSudoku());
    }
}