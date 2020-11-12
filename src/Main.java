
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        //scanner
        Scanner scan = new Scanner(System.in);

        //asks for user input to ether start a game or quit
        while(true) {

            System.out.println("Type:\n1: Multiple Choice Question \n2: Quit");

            int menuInput = scan.nextInt(); //takes in user input of int

            if(menuInput == 1){

                //here it will call the question class
                System.out.println("Here is question");

                new Question();

            }else if(menuInput == 2){

                //quits the while loop and there for the thread will end.
                System.out.println("BYE!");

                break;

            } else {

                // if the user somehow messes up typing 1 or 2 into the console
                System.out.println("You need to input 1 or 2 to answer in this menu! Try again! ");
            }

        }

    }
}
