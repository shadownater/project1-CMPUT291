import java.util.*;
import java.sql.*;
import java.io.*;

// VehicleSearch:
//
// Specification:
//      -Lists: Number of times a vehicle has been sold, the average
//              sale price of the vehicle, and the number of violations
//              involving the vehicle
//      -Query by: vehicle_id (vin)
public class VehicleSearch{
    Scanner scanner = IO.getScanner();
    Helpers h = new Helpers();
    final String STRING_TYPE = "String";

    // vehicleSearchMenu: provides user navigation, prompts user input.
    //                    validates input calls vehicleSearch
    //             input: none
    //           returns: true
    //                      -if a successful search completed
    //                      -if user selects "Return to search menu"
    //                    false
    //                      -if user selects "Return to main menu"
    public boolean vehicleSearchMenu(){
        // loop to prompt user
        while(true){
            System.out.println("+--------------------Vehicle Search-------------------+\n" +
                               "   Select an option:\n" +
                               "     1- Search by vehicle serial number\n" +
                               "     2- Return to search engine menu\n" +
                               "     3- Return to main  menu");

            String input = scanner.nextLine();
            boolean succ = false;

            // loop to prompt/get user input
            while(true){
                if(input.equals("1")){
                    String i = "";
                
                    // Check validity of user search criteria, l is valid length
                    int l=15;
                    String prompt = "vin(15): ";

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
                    succ = vehicleSearch(i);
                    // if success, return to SearchEngineMenu
                    if (succ) return true;
                    // if !success, return to VehicleSearchMenu
                    break;
                }else if(input.equals("2")){
                    // still using search, so search = true
                    return true;
                    
                }else if(input.equals("3")){
                    // finished with search, so searching = false
                    return false;
                    
                }else{
                    System.out.println("That is not a valid input! Try again.");
                }
                input = scanner.nextLine();
            } 
        }
    }

    // vehicleSearch: generates and executes query, calls parseVehicleSearch
    //                then call printVehicleSearch
    //         input: String vin, used to generate queries WHERE clause
    //       returns: true
    //                  -if search sucessful (results found)
    //                false
    //                  -otherwise
    public boolean vehicleSearch(String vin){
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

        // Try to execute query
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

    // parseVehicleSearch: takes a ResultSet rs and stores the info in
    //                     >=1 VehicleHistoryObj instance(s),
    //                     which are then stored in a map    
    //              input: ResultSet rs
    //            returns: Map containing VehicleHistoryObj instance(s)
    public Map<String,VehicleHistoryObj> parseVehicleSearch(ResultSet rs){
        // Use a map to hold vehicles, keys are licence_no

        Map<String,VehicleHistoryObj> m = new HashMap<>();
        String s = new String();
        float i = 0;
        try{
            // While records to process
            while(rs.next()){
                VehicleHistoryObj d = new VehicleHistoryObj();                

                // Since we outer joined, vin could be in either column
                s = rs.getString("v1");
                if(s==null) s = rs.getString("v2");
                
                d.setVin(s);

                // Add object to map
                m.put(s,d);                
                    
                s = rs.getString("sales");
                d.setSales(s);

                i = rs.getFloat("avg_sale_price");
                d.setAvgSalePrice(i);

                s = rs.getString("violations");
                d.setViolations(s);
            }
        }catch(SQLException e){
            System.err.println("SQLException: " +
                               e.getMessage());                
        }        

        // Return map to callee
        return m;
    }

    // printVehicleResults: given a map of VehicleHistoryObj(s),
    //                      prints the records
    //               input: map containing VehicleHistoryObj
    //             returns: none
    public void printVehicleResults(Map<String,VehicleHistoryObj> m){
        VehicleHistoryObj d;
        for(String k: m.keySet()){
            d = m.get(k);
            d.printRecord();            
        }
        System.out.println();
    }
}
