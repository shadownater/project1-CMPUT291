import java.util.*;
import java.sql.*;
import java.io.*;

public class ViolationSearch{
    Scanner scanner = new Scanner(System.in);
    Helpers h = new Helpers();
    Statement s;
    final String STRING_TYPE = "String";

    public void violationSearchMenu(){
        /**
           Idea: user picks criteria, enters search term, calls violationSearch
         **/
        System.out.println("Violation Search:\n" +
                           "Select an option:\n" +
                           "1- Search by licence number\n" +
                           "2- Search by sin\n" +
                           "3- Return to search engine menu");
        
        String input = scanner.nextLine();
        while(!input.equals("3")){
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
                violationSearch(sin, i);
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
                violationSearch(i, licence_no);
                break;
            }else if(input.equals("3")){
                //searchMenu();
                break;
            }else{
                System.out.println("That is not a valid input! Try again.");
            }
            input = scanner.nextLine();
        } 
    }

    public void violationSearch(String sin, String licenceNo){
        /**
             Idea: This is the main violation search, and searches by licence or name.
                   Calls parseViolationSearch.
             
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
        System.out.println(query);

        // try to find the licence
        try{
            rs = Login.stmt.executeQuery(query);
            // Check if any results
            if (rs.isBeforeFirst()) {
                Map<String,ViolationObj> m = new HashMap<>();                
                m = parseViolationSearch(rs);
                printViolationResults(m);
            }else{
                System.out.println("Sorry, no matches found.");
            }
        }catch(SQLException ex){
            System.err.println("SQLException: " +
                               ex.getMessage());
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

                
                
                // Add driving condition descriptions
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
    }
}
