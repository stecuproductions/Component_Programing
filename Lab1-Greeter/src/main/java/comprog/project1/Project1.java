/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package comprog.project1;

/**

 * @author jroga
 */
import java.util.Scanner;

public class Project1 {

    public static void main(final String[] args) {
        MessageFormatter formatter = new MessageFormatter();
        Scanner scanner = new Scanner(System.in);
        Greeter greeter = new Greeter(formatter);
        System.out.print("Enter a name: ");
        String name = scanner.nextLine();
        System.out.println(greeter.greet(name));
    }
}