package stec.exceptions;

/**
 * Exception thrown when a cloning operation fails.
 */
public class SudokuCloneException extends SudokuException {
    
    public SudokuCloneException(String messageKey) {
        super(messageKey);
    }
    
    public SudokuCloneException(String messageKey, Throwable cause) {
        super(messageKey, cause);
    }
}
