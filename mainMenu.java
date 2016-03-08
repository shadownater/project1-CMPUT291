import java.util.*;
import java.sql.*; // Java package for accessing Oracle
import java.io.*;

                

public class mainMenu{
  

public static void menu(){
  //main menu for navigation

  VehicleReg v = new VehicleReg();
  AutoTrans transaction = new AutoTrans();
  SearchEngine search = new SearchEngine();
  System.out.println("-----------------------------------------------------");
  
  System.out.println("Welcome to the Auto Registration System!\n Please select an option to begin:");

  System.out.println("1- New Vehicle Registration\n2- Auto Transaction\n3- Driver Licence Registration\n4- Violation Record\n5- Search Engine\n6- Exit");

    System.out.println("-----------------------------------------------------");


    //listen for input
    Scanner scanner = new Scanner(System.in);
    String input = scanner.next();

    try{
      int number = Integer.parseInt(input);

      switch(number){
      case 1:
        System.out.println("Welcome to New Vehicle Registration Menu!");
        v.vehicleRegMenu();
        
        //returning from vehicle registration - go back to the main menu
        System.out.println();
        menu();
        break;
        
      case 2:
        System.out.println("Welcome to Auto Transaction Menu!");
        transaction.autoTransMenu();
        
        //returning from Auto Transaction - go back to the main menu
        System.out.println();
        menu();
        break;
        
      case 3:
        System.out.println("Welcome to Driver Licence Registration Menu!");

        //returning from Driver Licence Reg - go back to the main menu
        System.out.println();
        menu();
        break;
        
      case 4:
        System.out.println("Welcome to Violation Record Menu!");

        //returning from Violation Record Menu - go back to the main menu
        System.out.println();
        menu();
        break;
        
      case 5:
        System.out.println("Welcome to the Search Engine!");
        search.searchMenu();
        
        //returning from search - go back to the main menu
        System.out.println();
        menu();
        break;
        
      case 6:
        System.out.println("Bye-bye!");
        System.exit(0);
        break;
        
      default:
        System.out.println("That is not a valid input! Try again.");
        menu(); //iffy but fix later if problems
        break;

      }
    }catch(NumberFormatException e){
      System.out.println("That is not a valid input! Try again.");
      menu(); //this is kinda weird but works for now
    }  

}





public static void main(String args[]){

  menu();
  System.out.println("Bye! :D");
  
 
}//end of main
}
