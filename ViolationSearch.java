import java.util.*;
import java.sql.*;
import java.io.*;

// ViolationSearch:
//
// Specification:
//      -Lists: Ticket, Violator, Vehicle Id, Officer If, Violation Type,
//              Location, Comments
//      -Query by: licence_no, sin
public class ViolationSearch{
    Scanner scanner = IO.getScanner();
    Helpers h = new Helpers();
    final String STRING_TYPE = "String";
    
    // violationSearchMenu: provides user navigation, prompts user input,
    //                      validates input, calls violationSearch
    //               input: none
    //             returns: true
    //                        -if a successful search completed
    //                        -if user selects "Return to search menu"
    //                      false
    //                        -if user selects "Return to main menu"
    public boolean violationSearchMenu(){
        // loop to prompt user
        while(true){
            System.out.println("+-------------------Violation Search------------------+\n" +
                               "   Select an option:\n" +
                               "     1- Search by licence number\n" +
                               "     2- Search by sin\n" +
                               "     3- Return to search engine menu\n" +
                               "     4- Return to main menu");

            String input = scanner.nextLine();
            boolean succ = false;        

            // loop to prompt/get user input
            while(true){
                if(input.equals("1")){
                    // in case 1, sin is not used, set to empty string
                    String sin = "";
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
                    succ = violationSearch(sin, i);
                    // if success, return to searchEngineMenu
                    if (succ) return true;
                    // if !success, return to violationSearchMenu
                    break;
                    
                }else if(input.equals("2")){
                    // in case 2, licence_no is not used, set to empty string
                    String licence_no = ""; 
                    String i = "";
                
                    // Check validity of user search criteria, l is valid length
                    int l=15;
                    String prompt = "Sin(15): ";        

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
                    succ = violationSearch(i, licence_no);
                    // if success, return to searchEngineMenu
                    if(succ) return true;
                    // if !success, return to violationSearchMenu
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

    // violationSearch: generates and executes query, calls parseViolationSearch
    //                  then call printViolationSearch
    //           input: Strings name, licenceNo. These are strings to used to
    //                  generate the queries' WHERE clause; only one of these
    //                  should be passed per call.
    //         returns: true
    //                    -if search sucessful (results found)    
    //                  false    
    //                    -otherwise
    public boolean violationSearch(String sin, String licenceNo){
        String query;
        ResultSet rs;
        
        // Create SQL query statement
        query =
            "Select *\n" +
            "FROM ticket t, people p, drive_licence dl\n" +
            "WHERE p.sin = t.violator_no AND t.violator_no = dl.sin AND\n" +
            "(LOWER(dl.licence_no) = ";

        query += "\'" + licenceNo.toLowerCase() + "\'" + " OR LOWER(violator_no) = ";
        query += "\'" + sin.toLowerCase() + "\')";            

        // Try to execute query
        try{
            rs = Login.stmt.executeQuery(query);
            // Check if any results
            if (rs.isBeforeFirst()) {
                Map<String,ViolationObj> m = new HashMap<>();                
                m = parseViolationSearch(rs);
                printViolationResults(m);
                return true;
            }else{
                // Extra checks to differentiate 1)no records 
                // 2) invalid licence number 3) invalid sin
                if(h.checkLicenceExists(licenceNo) ||
                   h.checkSinExists(sin)){
                    System.out.println("No violations found.");
                    System.out.println();
                }else{
                    if(licenceNo.length() > 0){
                       System.out.println("The provided licence number does not exist.");
                    }else if(sin.length() > 0){
                        System.out.println("The provided sin number does not exist.");
                    }
                    System.out.println();
                }
                return false;
            }
        }catch(SQLException ex){
            System.err.println("SQLException: " +
                               ex.getMessage());
            return false;
        }

    }

    // parseDriverSearch: takes a ResultSet rs and stores the info in
    //                    >=1 ViolationObj instance(s),
    //                    which are then stored in a map
    //             input: ResultSet rs
    //           returns: Map containing ViolationObj instance(s)
    public Map<String,ViolationObj> parseViolationSearch(ResultSet rs){
        // Use a map to hold violations, keys are ticket_no
        Map<String,ViolationObj> m = new HashMap<>();
        String s = new String();
        java.sql.Date date;
        
        try{
            // While records to process
            while(rs.next()){

                // Assumption: no reason to have multiple
                //             records with same ticket_no
                ViolationObj d = new ViolationObj();                

                // Populate the object
                s = rs.getString("ticket_no");
                d.setTicketNo(Integer.parseInt(s));

                // Add object to map
                m.put(s,d);                
                    
                s = rs.getString("violator_no");
                d.setViolatorNo(s);

                s = rs.getString("vehicle_id");
                d.setVehicleID(s);

                s = rs.getString("office_no");
                d.setOfficeNo(s);

                s = rs.getString("vtype");
                d.setVtype(s);

                date = rs.getDate("vdate");
                d.setVDate(date);                    
                    
                s = rs.getString("place");
                d.setPlace(s);

                s = rs.getString("descriptions");
                d.setDescriptions(s);
            }

        }catch(SQLException e){
            System.err.println("SQLException: " +
                               e.getMessage());                
        }        

        // Return map to callee
        return m;
    }

    // printViolationResults: given a map of ViolationObj(s), prints the records
    //                 input: map containing DriverObj(s)
    //               returns: none
    public void printViolationResults(Map<String,ViolationObj> m){
        ViolationObj d;
        for(String k: m.keySet()){
            d = m.get(k);
            d.printRecord();            
        }
        System.out.println();
    }    
}
