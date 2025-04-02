package  stec;

public class SudokuBoard {
    private SudokuField[][] board; 
    private SudokuRow[] rows;
    private SudokuColumn[] columns;
    private SudokuBox[] boxes;
    private final SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver solver) {
        this.board = new SudokuField[9][9];
        this.rows = new SudokuRow[9];
        this.columns = new SudokuColumn[9];
        this.boxes = new SudokuBox[9];
        this.sudokuSolver = solver;
        //Fields initialization
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        } 
        
        //inicjalizacja sudoku components

        for (int i = 0; i < 9; i++) {
            SudokuField[] rowFields = new SudokuField[9];
            SudokuField[] colFields = new SudokuField[9];
            for (int j = 0; j < 9; j++) {
                rowFields[j] = board[i][j];
                colFields[j] = board[j][i];
            }
            rows[i] = new SudokuRow(rowFields);
            columns[i] = new SudokuColumn(colFields);
        }
        int index = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                SudokuField[] boxFields = new SudokuField[9];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        boxFields[i * 3 + j] = board[row * 3 + i][col * 3 + j];
                    }
                }
                boxes[index++] = new SudokuBox(boxFields);
            }
        }
    }
    
    public void solveGame() {
        sudokuSolver.solve(this, 0, 0);
    }

    public String drawSudoku() {
        String result = "";
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                result += board[row][col].getFieldValue() + " ";
            }
            result += "\n";
        }
        return result;
    }  

    public SudokuRow getRow(int y) {
        return rows[y];
    }

    public SudokuColumn getColumn(int x) {
        return columns[x];
    }

    public SudokuBox getBox(int x, int y) {
        int boxIndex = (y / 3) * 3 + (x / 3);
        return boxes[boxIndex];
    }

    public void set(int row, int col, int value) {
        board[row][col].setFieldValue(value);
    }

    public int get(int row, int col) {
        return board[row][col].getFieldValue();
    }
 
}
