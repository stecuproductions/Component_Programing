package stec.exceptions;

/**
 * Exception thrown when a data write operation fails.
 */
public class SudokuDaoWriteException extends SudokuDaoException {

    public SudokuDaoWriteException(String messageKey) {
        super(messageKey);
    }


    public SudokuDaoWriteException(String messageKey, Throwable cause) {
        super(messageKey, cause);
    }
}
