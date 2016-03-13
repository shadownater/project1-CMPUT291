import java.util.*;
import java.sql.*;
import java.io.*;

public class ViolationSearch{
    Scanner scanner = new Scanner(System.in);
    Helpers h = new Helpers();
    Statement s;
    final String STRING_TYPE = "String";

    public boolean violationSearchMenu(){
        /**
           Idea: user picks criteria, enters search term, calls violationSearch
         **/
        
        while(true){
            System.out.println("Violation Search:\n" +
                               "Select an option:\n" +
                               "1- Search by licence number\n" +
                               "2- Search by sin\n" +
                               "3- Return to search engine menu\n" +
                               "4- Return to main menu");

            String input = scanner.nextLine();
            boolean succ = false;        
            
            while(true){
                if(input.equals("1")){
                    String sin = ""; 
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
                    succ = violationSearch(sin, i);
                    // if success, return to searchEngineMenu
                    if (succ) return true;
                    // if !success, return to violationSearchMenu
                    break;
                    
                }else if(input.equals("2")){
                    String licence_no = ""; 
                    String i = "";
                
                    // Check validity of user search criteria, l is valid length
                    int l=15;
                    String prompt = "Sin(15): ";        
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

    public boolean violationSearch(String sin, String licenceNo){
        /**
             Idea: This is the main violation search, and searches by licence or name.
                   Calls parseViolationSearch, then printViolationSearch
                   
             Input: sin or licenceNo (both case insensitive), 
                    pass "" for unknown value

             Returns: true if successful, false if no matches found
         **/
        
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

        // Debug statement print out **-KG
        // System.out.println(query);

        // try to find the licence
        try{
            rs = Login.stmt.executeQuery(query);
            // Check if any results
            if (rs.isBeforeFirst()) {
                Map<String,ViolationObj> m = new HashMap<>();                
                m = parseViolationSearch(rs);
                printViolationResults(m);
                return true;
            }else{
                System.out.println("Sorry, no matches found.");
                System.out.println();
                return false;
            }
        }catch(SQLException ex){
            System.err.println("SQLException: " +
                               ex.getMessage());
            return false;
        }

    }

    public Map<String,ViolationObj> parseViolationSearch(ResultSet rs){
        // Use a map to hold violations, keys are licence_no

        Map<String,ViolationObj> m = new HashMap<>();
        String s = new String();
        
        try{
            // While records to process
            while(rs.next()){
                // Check if we've made this ViolationObj yet

                ViolationObj d = new ViolationObj();                

                s = rs.getString("ticket_no");
                d.setTicketNo(s);

                m.put(s,d);                
                    
                s = rs.getString("violator_no");
                d.setViolatorNo(s);

                s = rs.getString("vehicle_id");
                d.setVehicleID(s);

                s = rs.getString("office_no");
                d.setOfficeNo(s);

                s = rs.getString("vtype");
                d.setVtype(s);

                s = rs.getString("vdate");
                d.setVtype(s);                    
                    
                s = rs.getString("place");
                d.setPlace(s);

                s = rs.getString("descriptions");
                d.setDescriptions(s);
            }

        }catch(SQLException e){
            System.err.println("SQLException: " +
                               e.getMessage());                
        }        

        return m;
    }

    public void printViolationResults(Map<String,ViolationObj> m){
        ViolationObj d;
        for(String k: m.keySet()){
            d = m.get(k);
            d.printAll();            
        }
        System.out.println();
    }
}