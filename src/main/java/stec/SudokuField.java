
package stec;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SudokuField {
    private final transient PropertyChangeSupport support;
    private  int value;
    
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
        this.value = newVal;
        support.firePropertyChange("value", oldVal, newVal);   
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // Might be needed in the future
    // public void removePropertyChangeListener(PropertyChangeListener listener) {
    //      support.removePropertyChangeListener(listener);
    // }
    
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
