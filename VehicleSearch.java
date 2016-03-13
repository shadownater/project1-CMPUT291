import java.util.*;
import java.sql.*;
import java.io.*;

public class VehicleSearch{
    Scanner scanner = new Scanner(System.in);
    Helpers h = new Helpers();
    Statement s;
    final String STRING_TYPE = "String";

    public boolean vehicleSearchMenu(){
        /**
           Idea: user picks criteria, enters search term, calls vehicleSearch
         **/
        
        while(true){
            System.out.println("Vehicle Search:\n" +
                               "Select an option:\n" +
                               "1- Search by vehicle serial number\n" +
                               "2- Return to search engine menu\n" +
                               "3- Return to main  menu");

            String input = scanner.nextLine();
            boolean succ = false;
            
            while(true){
                if(input.equals("1")){
                    String i = "";
                
                    // Check validity of user search criteria, l is valid length
                    int l=15;
                    String prompt = "vin(15): ";
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
                    succ = vehicleSearch(i);
                    // if success, return to SearchEngineMenu
                    if (succ) return true;
                    // if !success, return to VehicleSearchMenu
                    break;
                }else if(input.equals("2")){
                    return true;
                }else if(input.equals("3")){
                    return false;
                }else{
                    System.out.println("That is not a valid input! Try again.");
                }
                input = scanner.nextLine();
            } 
        }
    }
    
    public boolean vehicleSearch(String vin){
        /**
             Idea: This is the main vehicle search, searches 
                   by vehicles serial number (vin)
                   Calls parseVehicleSearch, then printVehicleResults.

                   
             Input: sin (case insensitive), 

             Returns: true if successful, false if no matches found
             
         **/
        
        String query;
        ResultSet rs;
        
        // Create SQL query statement
        query = "WITH sale_info AS(\n" +
            "SELECT vehicle_id v1, COUNT(transaction_id) n, SUM(price) p\n" +
            "FROM auto_sale\n" +
            "GROUP BY vehicle_id),\n" +
            "viol_info AS(\n" +
            "SELECT vehicle_id v2, COUNT(ticket_no) m\n" +
            "FROM ticket\n" +
            "GROUP BY vehicle_id)\n" +
            "SELECT v1, v2, n sales, p/n avg_sale_price, m violations\n" +
            "FROM sale_info\n" +
            "FULL JOIN viol_info ON v1 = v2\n" +
            "WHERE (LOWER(v1) = ";

        query += "\'" + vin.toLowerCase() + "\'" + " OR LOWER(v2) = ";
        query += "\'" + vin.toLowerCase() + "\')";            

        // Debug statement print out **-KG
        // System.out.println(query);

        // try to find the licence
        try{
            rs = Login.stmt.executeQuery(query);
            // Check if any results
            if (rs.isBeforeFirst()) {
                Map<String,VehicleHistoryObj> m = new HashMap<>();                
                m = parseVehicleSearch(rs);
                printVehicleResults(m);
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

    public Map<String,VehicleHistoryObj> parseVehicleSearch(ResultSet rs){
        // Use a map to hold vehicles, keys are licence_no

        Map<String,VehicleHistoryObj> m = new HashMap<>();
        String s = new String();
        
        try{
            // While records to process
            while(rs.next()){
                // Check if we've made this VehicleHistoryObj yet

                VehicleHistoryObj d = new VehicleHistoryObj();                

                // Since we outer joined, vin could be in either column
                s = rs.getString("v1");
                if(s==null) s = rs.getString("v2");
                
                d.setVin(s);

                m.put(s,d);                
                    
                s = rs.getString("sales");
                d.setSales(s);

                s = rs.getString("avg_sale_price");
                d.setAvgSalePrice(s);

                s = rs.getString("violations");
                d.setViolations(s);
            }
        }catch(SQLException e){
            System.err.println("SQLException: " +
                               e.getMessage());                
        }        

        return m;
    }

    public void printVehicleResults(Map<String,VehicleHistoryObj> m){
        VehicleHistoryObj d;
        for(String k: m.keySet()){
            d = m.get(k);
            d.printAll();            
        }
        System.out.println();
    }
}
