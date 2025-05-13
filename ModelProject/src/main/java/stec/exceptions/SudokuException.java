package stec.exceptions;

import java.util.Locale;
import java.util.ResourceBundle;


public class SudokuException extends RuntimeException {
    private static final String BUNDLE_BASE_NAME = "stec.exceptions.ExceptionMessages";
    
    public SudokuException(String messageKey) {
        super(messageKey);
    }

    public SudokuException(String messageKey, Throwable cause) {
        super(messageKey, cause);
    }
    
    @Override
    public String getLocalizedMessage() {
        return getLocalizedMessage(Locale.getDefault());
    }
    

    public String getLocalizedMessage(Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
            return bundle.getString(getMessage());
        } catch (Exception e) {
            return getMessage();
        }
    }
}
