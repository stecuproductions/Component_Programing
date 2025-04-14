package  stec;

import java.util.ArrayList;
import java.util.List;

public class SudokuBoard extends BaseModel {
    private SudokuField[][] board; 
    private List<SudokuRow> rows;
    private List<SudokuColumn> columns;
    private List<SudokuBox> boxes;
    private final SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver solver) {
        this.board = new SudokuField[9][9];
        this.rows = new ArrayList<>(9);
        this.columns = new ArrayList<>(9);
        this.boxes = new ArrayList<>(9);
        this.sudokuSolver = solver;
        //Fields initialization
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        } 
        
        //inicjalizacja sudoku components

        for (int i = 0; i < 9; i++) {
            List<SudokuField> rowFields = new ArrayList<>(9);
            List<SudokuField> colFields = new ArrayList<>(9);
            for (int j = 0; j < 9; j++) {
                rowFields.add(board[i][j]);
                colFields.add(board[j][i]);
            }
            rows.add(new SudokuRow(rowFields));
            columns.add(new SudokuColumn(colFields));
        }
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                List<SudokuField> boxFields = new ArrayList<>(9);
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        boxFields.add(board[row * 3 + i][col * 3 + j]);
                    }
                }
                boxes.add(new SudokuBox(boxFields));
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
        return rows.get(y);
    }

    public SudokuColumn getColumn(int x) {
        return columns.get(x);
    }

    public SudokuBox getBox(int x, int y) {
        int boxIndex = (y / 3) * 3 + (x / 3);
        return boxes.get(boxIndex);
    }

    public void set(int row, int col, int value) {
        board[row][col].setFieldValue(value);
    }

    public int get(int row, int col) {
        return board[row][col].getFieldValue();
    }
 
    private boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!rows.get(i).verify() || !columns.get(i).verify() || !boxes.get(i).verify()) {
                return false;
            }

        }
        return true;
    }
    
}
