/**Run the project on command line
 * should contain a main method**/
import java.time.format.SignStyle;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class Main {
    public static void main (String[] args){
        try {
            Scanner option = new Scanner(System.in);
            System.out.println("WELCOME TO RIDEDASH\nTo create an account press 1\n" +
                    "To log in to an existing account,press 2\n to exit at any point press ctrl+z");
            int choice = option.nextInt();
            //conditional statements to handle different choices
            if (choice == 1) {
                System.out.println("Type 1 to create a Driver account\nType 2 to create a Passenger account\nTo create an account as an admin press 3");
                int roleChoice = option.nextInt();
                String role = null;
                if (roleChoice == 1) {
                    System.out.println("Creating an account as a driver");
                    role = "Driver";
                }
                if (roleChoice == 2) {
                    System.out.println("Creating an account as a Passenger");
                    role = "Passenger";
                }
                if (roleChoice == 3) {
                    System.out.println("Creating an account as a Admin");
                    role = "Admin";
                }
                else {
                    System.out.println("Invalid choice,try again");
                    return;
                }
                //System.out.println("Role: "+role);
                System.out.println("Enter your name ");
                String name = option.nextLine();
                if (User.userPasswords.containsKey(name)){
                    System.out.println("an account with that name exists,try again");
                    return;
                }

        }
        }catch (NoSuchElementException e) {
            System.out.println("\nProgram exited. Goodbye!");
            System.exit(0);
        }
    }
}