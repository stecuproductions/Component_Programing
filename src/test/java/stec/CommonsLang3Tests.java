package stec;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CommonsLang3Tests {
        private SudokuSolver solver = new BacktrackingSudokuSolver();
        private SudokuBoard board = new SudokuBoard(solver);
        private SudokuField[][] privateBoardField = SudokuTestsUtils.getPrivateBoardField(board);

        @Test 
        public void equalsTest(){
            SudokuBoard testBoard = new SudokuBoard(solver);
            assertFalse(board.equals(testBoard));
            assertFalse(board.equals(null));
            testBoard=board;
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
            assertFalse(board.equals(testBoard));
            assertNotEquals(board.hashCode(), testBoard.hashCode());
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    int val = board.get(row, col);
                    testBoard.set(row, col, val);
                }
            }
            //assertTrue(board.equals(testBoard));
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
}