import java.util.*;
import java.sql.*;
import java.io.*;

// Provides search functionality to the application
// 
public class SearchEngine{
    
    Scanner scanner = IO.getScanner();
    Helpers h = new Helpers();;
    final String STRING_TYPE = "String";
    
    DriverSearch d;
    ViolationSearch v;
    VehicleSearch a;

    // searchMenu: provides user navigation and creates the various
    //             search object instances
    //      input: none
    //    returns: none
    public void searchMenu(){
        boolean searching = true;

        while(searching){           
            // A menu to select which search to execute
            System.out.println("+------------Welcome to the Search Engine-------------+");
            System.out.println("   Select an option:\n" +
                               "     1- Driver Search\n" +
                               "     2- Violation Search\n" +
                               "     3- Vehicle Search\n" +
                               "     4- Return to Main Menu");
            String input = scanner.nextLine();
            
            while(true){
                if(input.equals("1")){
                    d = new DriverSearch();
                    searching = d.driverSearchMenu();
                    break;
                    
                }else if(input.equals("2")){
                    v = new ViolationSearch();
                    searching = v.violationSearchMenu();
                    break;
                    
                }else if(input.equals("3")){
                    a = new VehicleSearch();
                    searching = a.vehicleSearchMenu();
                    break;
                    
                }else if(input.equals("4")){
                    searching = false;
                    break;

                }else{
                    System.out.println("That is not a valid input! Try again.");
                }

                input = scanner.nextLine();

            }
        }
    }
}

                           
        
