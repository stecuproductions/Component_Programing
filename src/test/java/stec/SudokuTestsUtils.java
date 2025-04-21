package stec;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import stec.model.SudokuField;

// mechanizm 'Refleksji' ktopy pozwala na dostęp do prywatnych pól klasy w testach. W tym przypadku
// uzywamy go do uzyskania dostepu do prywatnego pola board
public class  SudokuTestsUtils {
  public static SudokuField[][] getPrivateBoardField(Object sudokuBoard) {
    try {
      Field privateBoardField = sudokuBoard.getClass().getDeclaredField("board");
      privateBoardField.setAccessible(true);
      return (SudokuField[][]) privateBoardField.get(sudokuBoard);
    } catch (Exception e) {
      throw new RuntimeException("Nie udało się dostać do prywatnej tablicy board", e);
    }
  }

  public static boolean getPrivateCheckBoardMethod(Object sudokuBoard) {
    try {
      Method privateCheckBoardMethod = sudokuBoard.getClass().getDeclaredMethod("checkBoard");
      privateCheckBoardMethod.setAccessible(true);
      return (boolean) privateCheckBoardMethod.invoke(sudokuBoard);
    } catch (Exception e) {
      throw new RuntimeException("Nie udało się dostać do prywatnej metody checkBoard", e);
    }
  }
}
