import java.util.*;
import java.sql.*;
import java.io.*;

// Search Engine

// This component is used to query and display database records.

// Revision suggestion: use this as menu only, create three search classes
//                      with corresponding objects.

/*
   Specification:
    1) Driver Search:
         -Lists: name, licence_no, addr, birthday, 
                 driving class, driving_condition, expiry_date
         -Query by: licence_no or name
    2) Violation Search:
         -Lists: "violation records" **clarify-KG
         -Query by: licence_no, sin
    3) Vehicle Search:
         -Lists: number of ownership transfers, average sale price, 
                 number of violations

    Major Assumptions:
    -when querying by name, exact string matches are required. (case insensitive)
    -when querying by licence_no, exact string matches are required. (case insensitive)
    -a person may hold <=1 licence
 */

public class SearchEngine{
    
    Scanner scanner;
    Helpers h;
    //String m_userName;
    //String m_password;
    Statement stmt;

    DriverSearch d;
    ViolationSearch v;
    //VehicleSearch a;
    
    final String STRING_TYPE = "String";

    public SearchEngine(){
        scanner = new Scanner(System.in);
        h = new Helpers();
    }
    
    public void searchMenu(){
        // A menu to select which search to execute
        System.out.println(" What would you like to search?\n" +
                           "1- Driver Search\n" +
                           "2- Violation Search\n" +
                           "3- Vehicle Search\n" +
                           "4- Exit");

        // Should we consume whole line in mainMenu? **-KG
        String input = scanner.nextLine();

        while(!input.equals("4")){
            if(input.equals("1")){
                d = new DriverSearch();
                d.driverSearchMenu();
                break;
            }else if(input.equals("2")){
                v = new ViolationSearch();
                v.violationSearchMenu();
                break;
            }else if(input.equals("3")){
                //a = new VehicleSearch();
                //a.vehicleSearchMenu();
                break;
            }else{
                System.out.println("That is not a valid input! Try again.");
            }
            input = scanner.nextLine();
        }
    }
}

                           
        
