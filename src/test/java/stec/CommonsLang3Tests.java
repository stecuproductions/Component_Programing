package stec;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
}