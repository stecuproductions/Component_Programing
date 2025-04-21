package stec;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import stec.model.*;
import stec.solver.*;

import java.util.List;

public class CommonsLang3Tests {
    private SudokuSolver solver = new BacktrackingSudokuSolver();
    private SudokuBoard board = new SudokuBoard(solver);
    private SudokuField[][] privateBoardField = SudokuTestsUtils.getPrivateBoardField(board);

    @Test
    public void equalsTest(){
        SudokuBoard testBoard = new SudokuBoard(solver);
        board.solveGame();
        assertFalse(board.equals(testBoard));
        assertFalse(board.equals(null));
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int val = board.get(row, col);
                testBoard.set(row, col, val);
            }
        }
        assertTrue(board.equals(testBoard));

        SudokuBoard testBoard2=new SudokuBoard(solver);
        testBoard2.set(0, 0, 1);

        SudokuField[][] testBoard2Fields=SudokuTestsUtils.getPrivateBoardField(testBoard2);
        SudokuField[][] boardFields=SudokuTestsUtils.getPrivateBoardField(board);
        assertFalse(testBoard2Fields[0][0].equals(boardFields) );
    }

    @Test
    public void hashCodeTest() {
        SudokuBoard testBoard = new SudokuBoard(solver);
        board.solveGame();
        assertFalse(board.equals(testBoard));
        assertNotEquals(board.hashCode(), testBoard.hashCode());
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int val = board.get(row, col);
                testBoard.set(row, col, val);
            }
        }
        assertTrue(board.equals(testBoard));
        assertEquals(board.hashCode(), testBoard.hashCode());
    }

    @Test
    public void toStringTest() {
        String output = board.toString();
        assertNotNull(output);
        assertFalse(output.isBlank());
        assertTrue(output.contains("SudokuBoard"));
        assertTrue(output.contains("rows"));
        assertTrue(output.contains("columns"));
        assertTrue(output.contains("boxes"));
    }

    @Test
    void sameBoardNullOrEqualsTest(){
        SudokuBoard testBoard = new SudokuBoard(solver);
        assertTrue(testBoard.equals(testBoard));
        assertFalse(testBoard.equals(null));
        SudokuRow row = new SudokuRow(List.of(new SudokuField(1)));
        assertFalse(testBoard.equals(row));
    }

    @Test
    void sameRowsSameColsSameBoxes(){
        List<SudokuField> fieldList= List.of(
                new SudokuField(1),
                new SudokuField(2)
        );
        SudokuRow row1 = new SudokuRow(fieldList);
        SudokuRow row2 = new SudokuRow(fieldList);
        SudokuColumn column1 = new SudokuColumn(fieldList);
        SudokuColumn column2 = new SudokuColumn(fieldList);
        SudokuBox box1 = new SudokuBox(fieldList);
        SudokuBox box2 = new SudokuBox(fieldList);
        assertTrue(row1.equals(row2));
        assertTrue(column1.equals(column2));
        assertTrue(box1.equals(box2));
    }
}