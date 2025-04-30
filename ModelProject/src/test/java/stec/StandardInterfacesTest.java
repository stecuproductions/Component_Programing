package stec;

import org.junit.jupiter.api.Test;
import stec.model.*;
import stec.solver.BacktrackingSudokuSolver;
import stec.solver.SudokuSolver;

import static org.junit.jupiter.api.Assertions.*;


public class StandardInterfacesTest {
    @Test
    public void sudokuFieldComparableTest(){
        SudokuField sudokuField = new SudokuField(3);
        SudokuField sudokuField2 = new SudokuField(4);
        assertEquals(-1, sudokuField.compareTo(sudokuField2));
        assertThrows(NullPointerException.class, () -> {
            sudokuField.compareTo(null);
        });
    }

    @Test
    public void sudokuBoardCloneTest() throws CloneNotSupportedException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard originalBoard = new SudokuBoard(solver);
        SudokuBoard clonedBoard = originalBoard.clone();
        assertNotSame(originalBoard, clonedBoard);
    }
}
