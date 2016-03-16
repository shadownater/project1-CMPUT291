import java.util.*;
import java.sql.*;
import java.io.*;

//   Driver Search:
//
//   Specification:
//        -Lists: name, licence_no, addr, birthday, 
//                driving class, driving_condition, expiry_date
//        -Query by: licence_no or name
public class DriverSearch{
    Scanner scanner = IO.getScanner();
    Helpers h = new Helpers();
    final String STRING_TYPE = "String";

    // driverSearchMenu: provides user navigation, prompts user input,
    //                   validates input, calls driverSearch
    //            input: none
    //          returns: true
    //                     -if a sucessful search completed
    //                     -if user selects "Return to search menu"
    //                   false
    //                     -if user selects "Return to main menu"
    public boolean driverSearchMenu(){
        // loop to prompt user
        while(true){
            System.out.println("+--------------------Driver Search--------------------+\n" +
                               "   Select an option:\n" +
                               "     1- Search by licence number\n" +
                               "     2- Search by name\n" +
                               "     3- Return to search engine menu\n" +
                               "     4- Return to main menu");       

            String input = scanner.nextLine();
            boolean succ = false;

            // loop to prompt/get user input
            while(true){
                if(input.equals("1")){
                    String name = ""; 
                    String i = "";
                
                    // Check validity of user search criteria, l is valid length
                    int l=15;
                    String prompt = "Licence(15): ";

                    // loop to validate user input
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
                    succ = driverSearch(name, i);
                    // if success, return to SearchEngineMenu
                    if (succ) return true;
                    // if !success, return to DriverSearchMenu
                    break;
                
                }else if(input.equals("2")){
                    String licence_no = ""; 
                    String i = "";
                
                    // Check validity of user search criteria, l is valid length
                    int l=40;
                    String prompt = "Name(40): ";        

                    // loop to validate user input
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
                    succ = driverSearch(i, licence_no);
                    // if success, return to SearchEngineMenu
                    if (succ) return true;
                    // if !success, return to DriverSearchMenu                    
                    break;
                
                }else if(input.equals("3")){
                    // still using search, so searching = true
                    return true;
                    
                }else if(input.equals("4")){
                    // finished with search, so searching = false
                    return false;
                    
                }else{
                    System.out.println("That is not a valid input! Try again.");
                }
                input = scanner.nextLine();
            }        
        }
    }

    // driverSearch: generates and executes query, calls parseDriverSearch
    //               then calls printDriverSearch
    //        input: name, licenceNo. These are strings to used to generate
    //               the queries' WHERE clause; only one of these
    //               should be passed per call.
    //      returns: true
    //                 -if search sucessful(results found)
    //               false
    //                 -otherwise.   
    public boolean driverSearch(String name, String licenceNo){
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

        // Try to execute query
        try{
            rs = Login.stmt.executeQuery(query);
            // Check if any results
            if (rs.isBeforeFirst()) {
                Map<String,DriverObj> m = new HashMap<>();                
                m = parseDriverSearch(rs);
                printDriverResults(m);
                return true;
            }else{
                System.out.println("Sorry, no matches found. Try again.");
                System.out.println();
                return false;
            }
            
        }catch(SQLException ex){
            System.err.println("SQLException: " +
                               ex.getMessage());
            return false;
        }
    }

    // parseDriverSearch: takes a resultSet rs and stores the info in
    //                    >=1 DriverObj instance(s)
    //             input: ResultSet rs
    //           returns: Map containing >= 1 DriverObj instance(s)
    public Map<String,DriverObj> parseDriverSearch(ResultSet rs){
        // Use a map to hold drivers, keys are licence_no
        Map<String,DriverObj> m = new HashMap<>();
        String s = new String();
        java.sql.Date date;

        try{
            // While records to process
            while(rs.next()){

                // Check if we've made this driverObj yet
                s = rs.getString("licence_no");
                DriverObj d = new DriverObj();     
                if (!m.containsKey(s)){
                    // Populate the object                    
                    d.setLicenceNo(s);

                    // Add object to map
                    m.put(s,d);
                    
                    s = rs.getString("name");
                    d.setName(s);

                    s = rs.getString("addr");
                    d.setAddr(s);

                    date = rs.getDate("birthday");
                    d.setBirthday(date);

                    s = rs.getString("class");
                    d.setDrivingClass(s);

                    date = rs.getDate("expiring_date");
                    d.setExpiryDate(date);
                }
                
                // Add driving condition descriptions (multi-value attribute)
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

        // Return the map to callee
        return m;
    }

    // printDriverResults: given a map of DriverObj(s), prints the records
    //              input: map containing DriverObj(s)
    //            returns: none
    public void printDriverResults(Map<String,DriverObj> m){
        DriverObj d;
        for(String k: m.keySet()){
            d = m.get(k);
            d.printRecord();            
        }
        System.out.println();
    }
}
