package stec.exceptions;

/**
 * Exception thrown when a data read operation fails.
 */
public class SudokuDaoReadException extends SudokuDaoException {
    
    public SudokuDaoReadException(String messageKey) {
        super(messageKey);
    }

    public SudokuDaoReadException(String messageKey, Throwable cause) {
        super(messageKey, cause);
    }
}
