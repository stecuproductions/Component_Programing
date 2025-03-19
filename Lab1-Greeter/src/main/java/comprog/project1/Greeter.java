/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comprog.project1;

/**
 *
 * @author jroga
 */
public class Greeter {
    private final MessageFormatter messageFormatter;

    public Greeter(MessageFormatter formatter) {
        this.messageFormatter = formatter;
    }

    public String greet(String who) {
        if (who == null)
            who = "";
        return messageFormatter.format(who);
    }
}