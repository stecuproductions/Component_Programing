/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

package stec.model;

/**
 * Enum representing the different difficulty levels for the sudoku game.
 * @author jroga
 */
public enum Difficulty {
    EASY(30), MEDIUM(35), HARD(40);
    
    private int toRemove;
    
    Difficulty(int toRemove) {
        this.toRemove = toRemove;
    }
    
    public int getToRemove() {
        return toRemove;
    }
}
