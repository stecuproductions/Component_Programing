package stec;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class SudokuBoard {
    private static final int SIZE = 9; // Rozmiar planszy
    private int[][] board;

    public SudokuBoard() {
        board = new int[SIZE][SIZE]; // Inicjalizacja tablicy 9x9
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

    public boolean fillBoard() {
        return solve(0, 0);
    }

    public int[][] getBoard() {
        return board;
    }

    public int getSize() {
        return SIZE;
    }

    private boolean solve(int row, int col) {
        if (row == SIZE) {
            return true;
        }
        int newRow = (col == SIZE - 1) ? row + 1 : row;
        int newCol = (col == SIZE - 1) ? 0 : col + 1;

        int[] numbers = generateRandomNumbers();

        for (int number : numbers) {
            if (isValid(row, col, number)) {
                board[row][col] = number;
                if (solve(newRow, newCol)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }

        return false;
    }

    private int[] generateRandomNumbers() {
        Random random = new Random();
        List<Integer> sudokuNumbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        int[] result = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int iterator = 0; iterator < SIZE; iterator++) {
            int randomIndex = random.nextInt(sudokuNumbers.size());
            result[iterator] = sudokuNumbers.get(randomIndex);
            sudokuNumbers.remove(randomIndex);
        }
        return result;
    }

    private boolean isValid(int row, int col, int num) {
        return isRowValid(row, num) && isColValid(col, num) && isBoxValid(row, col, num);
    }

    private boolean isRowValid(int row, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num)
                return false;
        }
        return true;
    }

    private boolean isColValid(int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[i][col] == num)
                return false;
        }
        return true;
    }

    private boolean isBoxValid(int row, int col, int num) {
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[boxRow + i][boxCol + j] == num)
                    return false;
            }
        }
        return true;
    }
}
