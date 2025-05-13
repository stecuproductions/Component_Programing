package stec.exceptions;


public class SudokuDaoException extends SudokuException {
  

  public SudokuDaoException(String messageKey) {
    super(messageKey);
  }

  public SudokuDaoException(String messageKey, Throwable cause) {
    super(messageKey, cause);
  }
}
