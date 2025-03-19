package stec;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        SudokuBoard board = new SudokuBoard();
        board.fillBoard();
        System.out.println(board.drawSudoku());
    }
}
