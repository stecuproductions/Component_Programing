/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

package stec.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum representing the different difficulty levels for the sudoku game.
 * @author jroga
 */
public enum Difficulty {
    EASY(30), MEDIUM(35), HARD(40);
    private  final Logger logger = LoggerFactory.getLogger(Difficulty.class);

    private int toRemove;
    
    Difficulty(int toRemove) {
        logger.info("Difficulty set");
        this.toRemove = toRemove;
    }
    
    public int getToRemove() {
        return toRemove;
    }
}
