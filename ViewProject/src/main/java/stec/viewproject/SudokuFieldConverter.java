/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stec.viewproject;

import javafx.util.StringConverter;

/**
 *
 * @author jroga
 */
public class SudokuFieldConverter extends StringConverter<Integer> {

    @Override
    public String toString(Integer t) {
        return t == null || t == 0 ? "" : t.toString();
    }

    @Override
    public Integer fromString(String string) {
        if(string == null || string.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }
    
}
