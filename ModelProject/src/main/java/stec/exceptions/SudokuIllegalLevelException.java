package stec.exceptions;


public class SudokuIllegalLevelException extends SudokuException {

    public SudokuIllegalLevelException(String messageKey) {
        super(messageKey);
    }


    public SudokuIllegalLevelException(String messageKey, Throwable cause) {
        super(messageKey, cause);
    }
}
