package stec.exceptions;

public class SudokuDaoConnectionException extends SudokuException {
    public SudokuDaoConnectionException(String message) {
        super(message);
    }

    public SudokuDaoConnectionException(String message, Throwable cause) {
      super(message, cause);
    }
}
