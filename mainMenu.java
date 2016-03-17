import java.util.*;
import java.sql.*; // Java package for accessing Oracle
import java.io.*;

                

public class mainMenu{
  
//logs in the user
public static void login(){
  Connection m_con;

  //login part first:
  
  // get username
  System.out.print("Username: ");
  Console co = System.console();
  Login.m_userName = co.readLine();

  // obtain password
  char[] passwordArray = co.readPassword("Password: ");
  Login.m_password =new String(passwordArray);


  try{
    Class drvClass = Class.forName(Login.m_driverName);    
  }catch(Exception e){
    System.err.print("ClassNotFoundException: ");
    System.err.println(e.getMessage());
  }
  
  try{
    // Establish a connection
    m_con = DriverManager.getConnection(Login.m_url, Login.m_userName, Login.m_password);
    // Create a statement object.
    // Changed to reflect changes made in the result set and to make these changes permanent to the database too
    Login.stmt = m_con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    
  }catch(SQLException ex) {
    System.err.println("SQLException: " +
                         ex.getMessage());
    login();
    }
}


public static void menu(){
  //main menu for navigation

  VehicleReg v = new VehicleReg();
  AutoTrans transaction = new AutoTrans();
  SearchEngine search = new SearchEngine();
  DriverReg dLReg = new DriverReg();
  ViolationRecord record = new ViolationRecord();
  System.out.println("-----------------------------------------------------");
  
  System.out.println("Welcome to the Auto Registration System!\n Please select an option to begin:");

  System.out.println("1- New Vehicle Registration\n2- Auto Transaction\n3- Driver Licence Registration\n4- Violation Record\n5- Search Engine\n6- Exit");

    System.out.println("-----------------------------------------------------");


    //listen for input
    Scanner scanner = IO.getScanner();
    String input = scanner.nextLine();

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
        dLReg.driverRegMenu();
        //returning from Driver Licence Reg - go back to the main menu
        System.out.println();
        menu();
        break;
        
      case 4:
        System.out.println("Welcome to Violation Record Menu!");
	record.violationRecordMenu();
        //returning from Violation Record Menu - go back to the main menu
        System.out.println();
        menu();
        break;
        
      case 5:
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

    public static void main(String[] args){
        login();
        
        try{
            // Initialize "global" scanner
            //Scanner scanner = IO.getScanner("test.txt");
            menu();
        }catch(NoSuchElementException e){
          //System.out.println("End of tests, returning to stdin.");

          //Scanner scanner = IO.resetScanner();
            menu();
        }
            
        System.out.println("Bye! :D");
  
 
    }//end of main

}
