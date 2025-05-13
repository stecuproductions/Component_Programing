package stec.exceptions;

public class SudokuDaoNamesException extends SudokuDaoException {
    

    public SudokuDaoNamesException(String messageKey) {
        super(messageKey);
    }

    public SudokuDaoNamesException(String messageKey, Throwable cause) {
        super(messageKey, cause);
    }
}
