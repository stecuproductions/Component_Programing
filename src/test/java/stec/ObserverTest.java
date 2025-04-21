package stec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import stec.model.*;
import stec.solver.BacktrackingSudokuSolver;
import stec.solver.SudokuSolver;

public class ObserverTest {
  @Test
  public void testObserverWorking() {
    SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
    SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
    SudokuField[][] sudokuBoardFields = SudokuTestsUtils.getPrivateBoardField(sudokuBoard);
    SudokuRow listenerRow = sudokuBoard.getRow(0);
    SudokuColumn listenerColumn = sudokuBoard.getColumn(0);
    SudokuBox listenerBox = sudokuBoard.getBox(0, 0);
    sudokuBoardFields[0][0].setFieldValue(1);
    assertEquals(1, sudokuBoard.get(0, 0));
    assertTrue(listenerBox.notified);
    assertTrue(listenerRow.notified);
    assertTrue(listenerColumn.notified);
  }
}
