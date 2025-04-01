/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package stec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jroga
 */
public class SudokuBoardTest {       

    @Test 
    public void testSudokuBoardCheckValidSudoku(){
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();
        assertTrue(SudokuTestsUtils.getPrivateCheckBoardMethod(board));
    }

    @Test
    public void testSudokuCheckBoard() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        //invalid row
        board.set(0, 0, 1);
        board.set(0, 1, 1);
        assertTrue(!SudokuTestsUtils.getPrivateCheckBoardMethod(board));
        board.set(0, 1, 0); //reset to 0
        board.set(0, 1, 0); //reset to 0
        //invalid column
        board.set(0, 0, 1);
        board.set(1, 0, 1);
        assertTrue(!SudokuTestsUtils.getPrivateCheckBoardMethod(board));
        board.set(1, 0, 0); //reset to 0
        board.set(0, 0, 0); //reset to 0
        //invalid box
        board.set(0, 0, 1);
        board.set(1, 1, 1);
        assertTrue(!SudokuTestsUtils.getPrivateCheckBoardMethod(board));
        board.set(1, 1, 0); //reset to 0
        board.set(0, 0, 0); //reset to 0
        //valid board
        board.solveGame();
        assertTrue(SudokuTestsUtils.getPrivateCheckBoardMethod(board));
        
    }



    @Test
    public void diffrentBoardsTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board1 = new SudokuBoard(solver);
        SudokuBoard board2 = new SudokuBoard(solver);
        board1.solveGame();
        board2.solveGame();
        boolean different = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board1.get(i, j) != board2.get(i, j)) {
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

        board.set(0, 0, 5);  

        String output = board.drawSudoku();

        assertTrue(output.startsWith("5 "));

        long newlineCount = output.chars().filter(c -> c == '\n').count();
        assertEquals(9, newlineCount);
    }

    @Test 
    void mainMethodRunning(){
        App myApp=new App();
        myApp.main(null);

        
    }
}