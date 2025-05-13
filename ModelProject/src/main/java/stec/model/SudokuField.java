package stec.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SudokuField implements Serializable, Comparable<SudokuField>, Cloneable {
  private static final Logger logger = LoggerFactory.getLogger(SudokuField.class.getName());
  private transient PropertyChangeSupport support;
  private int value;

  public SudokuField(int value) {
    this.value = value;
    this.support = new PropertyChangeSupport(this);
  }

  public int getFieldValue() {
    return value;
  }

  public void setFieldValue(int value) {
    int oldVal = this.getFieldValue();
    int newVal = value;
    logger.debug("Changing field value from {} to {}", oldVal, newVal);
    this.value = newVal;
    support.firePropertyChange("fieldValue", oldVal, newVal);
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  // Might be needed in the future
  // public void removePropertyChangeListener(PropertyChangeListener listener) {
  //      support.removePropertyChangeListener(listener);
  // }
  
  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    in.defaultReadObject();
    support = new PropertyChangeSupport(this);
}
  
  @Override
  public int compareTo(SudokuField o) {
    if (o == null) {
      throw new NullPointerException();
    }
    return this.getFieldValue() - o.getFieldValue();
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    Object clone = (SudokuField) super.clone();
    ((SudokuField) clone).support = new PropertyChangeSupport(clone);
    return clone;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
