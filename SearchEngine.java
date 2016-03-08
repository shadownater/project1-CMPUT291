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
         -Lists: name, licence_no, address, birthday, 
                 driving class, driving_condition, expiry_date
         -Query by: licence_no or name
    2) Violation Search:
         -Lists: "violation records" **clarify-KG
         -Query by: licence_no, name
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
    String m_userName;
    String m_password;
    Statement stmt;

    // 
    final String STRING_TYPE = "String";

    // The URL we are connecting to
    final String m_url = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
  
    // The driver to use for connection
    final String m_driverName = "oracle.jdbc.driver.OracleDriver";

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
                driverSearchMenu();
                break;
            }else if(input.equals("2")){
                System.out.println("That is 2");
                break;
            }else if(input.equals("3")){
                System.out.println("That is 3");
                break;
            }else{
                System.out.println("That is not a valid input! Try again.");
            }
            input = scanner.nextLine();
        }
    }

    public void driverSearchMenu(){
        // A menu to select search option
        System.out.println("Driver Search:\n" +
                           "Select an option:\n" +
                           "1- Search by licence number\n" +
                           "2- Search by name\n" +
                           "3- Return to search engine menu");
        
        String input = scanner.nextLine();
        while(!input.equals("3")){
            if(input.equals("1")){
                driverSearch(Integer.parseInt(input));
                break;
            }else if(input.equals("2")){
                driverSearch(Integer.parseInt(input));
                break;
            }else if(input.equals("3")){
                System.out.println("That is 3");
                break;
            }else{
                System.out.println("That is not a valid input! Try again.");
            }
            input = scanner.nextLine();
        }        
    }

    public void driverSearch(int searchBy){
        // Valid inputs: 1 (search by licence), 2 (search by name)
        String i;
        String query;

        // Set searchBy variables
        // l for valid length
        int l=0;
        String prompt = "";
        // Valid length determined by searchBy
        if(searchBy == 1) {
            l = 15;
            prompt = "Licence(15): ";
        }else if(searchBy == 2) {
            l = 40;
            prompt = "Name(40): ";
        }
        

        // Sync this with ie. vehiclereg.java **-KG
        while(true){
            System.out.print(prompt);
            i = scanner.nextLine();
            if(i.isEmpty()) i=null;

            try{
                h.checkValidity(i, l, STRING_TYPE, false);
                break;
            }catch(CantBeNullException e){
                System.out.println("Entry cannot be null!");
            }catch(TooLongException e){
                System.out.println("Entry too long!");
            }catch(NumberFormatException e){
                System.out.println("Entry in the wrong format!");
            }
        }
        // Have to decide how to handle multivalue (ie restrictions)
        //  two queries? **-KG
        query =
            "SELECT name, licence_no, addr, birthday, class, description,expiring_date\n" +
            "FROM people p, drive_livenve dl, driving_condition dc, restriction r\n" +
            "WHERE p.sin = dl.sin AND dl.licence_no = r.licence_no AND\n" +
            "r.r_id = dc.c_id AND";
        
        if(searchBy == 1) {
            query += " LOWER(dl.licence_no) = ";
        }else if(searchBy == 2) {
            query += " LOWER(p.name) = ";
        }
        query += "\"" + i.toLowerCase() + "\"";

        System.out.println("\nWas there a mistake? Y/N or Q to quit (will not upload to database.)");        

        String isOk = scanner.nextLine();
        while(!isOk.equalsIgnoreCase("Q")){
            if(isOk.equalsIgnoreCase("Y")) driverSearch(searchBy);
            else if(isOk.equalsIgnoreCase("N")) break; // query here
            scanner.nextLine();
        }

    }
}

                           
        
