package stec;


public class SudokuBoard {
    private static final int SIZE = 9; // Rozmiar planszy
    private int[][] board;
    private SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver solver) {
        board = new int[SIZE][SIZE]; // Inicjalizacja tablicy 9x9
        this.sudokuSolver = solver;
    }
    
    public void solveGame() {
        sudokuSolver.solve(this, 0, 0);
    }

    public String drawSudoku() {
        String result = "";
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                result += board[row][col] + " ";
            }
            result += "\n";
        }
        return result;
    }

    public int getNum(int row, int col) {
        return board[row][col];
    }

    public int getSize() {
        return SIZE;
    }
    
    public void setNum(int row, int col, int val) {
        board[row][col] = val;
    }

    public boolean isValid(int row, int col, int num) {
        return isRowValid(row, num) && isColValid(col, num) && isBoxValid(row, col, num);
    }

    public boolean isRowValid(int row, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }
        return true;
    }

    public boolean isColValid(int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }
        return true;
    }

    public boolean isBoxValid(int row, int col, int num) {
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[boxRow + i][boxCol + j] == num) { 
                    return false; 
                }
            }
        }
        return true;
    }
}
