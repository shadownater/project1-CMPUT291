import java.util.*;
import java.sql.*; // Java package for accessing Oracle
import java.io.*;

                

public class mainMenu{
  

public static void menu(){
  //main menu for navigation

  VehicleReg v = new VehicleReg();
  
  
  System.out.println("Welcome to the Auto Registration System!\n Please select an option to begin:");

  System.out.println("1- New Vehicle Registration\n2- Auto Transaction\n3- Driver Licence Registration\n4- Violation Record\n5- Search Engine\n6- Exit");

    //listen for input
    Scanner scanner = new Scanner(System.in);
    String input = scanner.next();

    try{
      int number = Integer.parseInt(input);

      switch(number){
      case 1:
        System.out.println("Welcome to New Vehicle Registration Menu!");
        v.tester();
        //call function for vehicleReg here!
        break;
      case 2:
        System.out.println("Welcome to Auto Transaction Menu!");
        break;
      case 3:
        System.out.println("Welcome to Driver Licence Registration Menu!");
        break;
      case 4:
        System.out.println("Welcome to Violation Record Menu!");
        break;
      case 5:
        System.out.println("Welcome to the Search Engine!");
        break;
      case 6:
        System.out.println("Bye-bye!");
        System.exit(0);
        break;
      default:
        System.out.println("That is not a valid input! Try again.\n");
        menu(); //iffy but fix later if problems
        break;

      }
    }catch(NumberFormatException e){
      System.out.println("Invalid input! Please try again.");
      menu(); //this is kinda weird but works for now
    }  

}





public static void main(String args[]){

  menu(); //fix me

  
 
}//end of main
}
