/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import comprog.project1.Greeter;
import comprog.project1.MessageFormatter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jroga
 */
public class GreeterTest {
    @Test
    public void testGreet() {
        MessageFormatter formatter=new MessageFormatter();
        Greeter greeter=new Greeter(formatter);
        assertEquals("hello !", greeter.greet(null));
        assertEquals("hello !", greeter.greet(""));
        assertEquals("hello student!", greeter.greet("student"));
        //assertSame("hello student!", greeter.greet("student")); - failed (different memory locations)

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}