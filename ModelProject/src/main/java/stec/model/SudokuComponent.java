package stec.model;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.List;

public abstract class SudokuComponent implements PropertyChangeListener, Serializable, Cloneable {
  protected List<SudokuField> sudokuFields;
  public boolean notified = false;

  public SudokuComponent(List<SudokuField> fields) {
    this.sudokuFields = fields;
    for (SudokuField field : fields) {
      field.addPropertyChangeListener(this);
    }
  }

  public boolean verify() {
    boolean[] seen = new boolean[10];
    int val;
    for (int i = 0; i < 9; i++) {
      val = sudokuFields.get(i).getFieldValue();
      if (val == 0) {
        continue;
      }
      if (seen[val] == true) {
        return false;
      }
      seen[val] = true;
    }
    return true;
  }

  @Override
  public void propertyChange(java.beans.PropertyChangeEvent evt) {
    // int newValue = (int) evt.getNewValue();
    // int oldValue = (int) evt.getOldValue();
    // boolean isValid = verify();
    notified = true;
    // String validationMessage = (isValid
    //   ?  "Does not violate " : "Violates ") + this.getClass().getSimpleName() + " rules";
    // System.out.println("Field value changed! Old value:  " + oldValue
    //   + ". New value: " + newValue + ". Value " + validationMessage);

  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
