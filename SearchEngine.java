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
        /**
           Idea: user picks criteria, enters search term, calls driverSearch
         **/
        System.out.println("Driver Search:\n" +
                           "Select an option:\n" +
                           "1- Search by licence number\n" +
                           "2- Search by name\n" +
                           "3- Return to search engine menu");
        
        String input = scanner.nextLine();
        while(!input.equals("3")){
            if(input.equals("1")){
                String name = ""; 
                String i = "";
                
                // Check validity of user search criteria, l is valid length
                int l=15;
                String prompt = "Licence(15): ";
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

                // Do the search
                driverSearch(name, i);
                break;
            }else if(input.equals("2")){
                String licence_no = ""; 
                String i = "";
                
                // Check validity of user search criteria, l is valid length
                int l=40;
                String prompt = "Name(40): ";        
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
                // Do the search
                driverSearch(i, licence_no);
                break;
            }else if(input.equals("3")){
                searchMenu();
                break;
            }else{
                System.out.println("That is not a valid input! Try again.");
            }
            input = scanner.nextLine();
        }        
    }

    public void driverSearch(String name, String licenceNo){
        /**
             Idea: This is the main driver search, and searches by licence or name.
                   Calls parseDriverSearch.
             
         **/
        
        String query;
        ResultSet rs;
        
        // Create SQL query statement
        query =
            "SELECT name, dl.licence_no, addr, birthday, class, description, expiring_date\n"+
            "FROM people p, drive_licence dl\n" +
            "LEFT JOIN restriction r ON r.licence_no = dl.licence_no\n" +
            "LEFT JOIN driving_condition dc ON dc.c_id = r.r_id\n" +
            "WHERE p.sin = dl.sin AND dl.class <> 'nondriving' AND\n" +
            "(LOWER(dl.licence_no) = ";
        
        query += "\'" + licenceNo.toLowerCase() + "\'" + " OR LOWER(name) = ";
        query += "\'" + name.toLowerCase() + "\')";

        // Debug statement print out **-KG
        // System.out.println(query);

        // try to find the licence
        try{
            rs = Login.stmt.executeQuery(query);
            // Check if any results
            if (rs.isBeforeFirst()) {
                Map<String,DriverObj> m = new HashMap<>();                
                m = parseDriverSearch(rs);
                printDriverResults(m);
            }else{
                System.out.println("Sorry, no matches found.");
            }
        }catch(SQLException ex){
            System.err.println("SQLException: " +
                               ex.getMessage());
        }

    }

    public Map<String,DriverObj> parseDriverSearch(ResultSet rs){
        // Use a map to hold drivers, keys are licence_no

        Map<String,DriverObj> m = new HashMap<>();
        String s = new String();
        
        try{
            // While records to process
            while(rs.next()){
                // Check if we've made this driverObj yet
                s = rs.getString("licence_no");
                DriverObj d = new DriverObj();                
                if (!m.containsKey(s)){
                    d.setLicenceNo(s);
                    m.put(s,d);
                    
                    s = rs.getString("name");
                    d.setName(s);

                    s = rs.getString("addr");
                    d.setAddr(s);

                    s = rs.getString("birthday");
                    d.setBirthday(s);

                    s = rs.getString("class");
                    d.setDrivingClass(s);

                    s = rs.getString("expiring_date");
                    d.setExpiryDate(s);

                }
                // Add driving condition descriptions
                s = rs.getString("description");
                if(s != null){
                    s = rs.getString("licence_no");
                    d = m.get(s);
                    s = rs.getString("description");
                    d.addDrivingCondition(s);
                }
            }
        }catch(SQLException e){
            System.err.println("SQLException: " +
                               e.getMessage());                
        }        

        return m;
    }

    public void printDriverResults(Map<String,DriverObj> m){
        DriverObj d;
        for(String k: m.keySet()){
            d = m.get(k);
            d.printAll();            
        }
    }
}

                           
        
