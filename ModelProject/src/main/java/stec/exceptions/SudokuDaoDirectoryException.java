package stec.exceptions;

public class SudokuDaoDirectoryException extends SudokuDaoException {
    
    public SudokuDaoDirectoryException(String messageKey) {
        super(messageKey);
    }

    public SudokuDaoDirectoryException(String messageKey, Throwable cause) {
        super(messageKey, cause);
    }
}
