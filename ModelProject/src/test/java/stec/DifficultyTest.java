/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package stec;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import stec.model.Difficulty;

/**
 *
 * @author jroga
 */
public class DifficultyTest {
    
    public DifficultyTest() {
    }

    @Test
    void LevelsTest() {
    assertEquals(30, Difficulty.EASY.getToRemove());
    assertEquals(35, Difficulty.MEDIUM.getToRemove());
    assertEquals(40, Difficulty.HARD.getToRemove());

}
}
