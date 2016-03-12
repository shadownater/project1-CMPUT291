import java.util.*;
import java.sql.*;
import java.io.*;

public class DriverSearch{
    Scanner scanner = new Scanner(System.in);
    Helpers h = new Helpers();
    Statement s;
    final String STRING_TYPE = "String";
    
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
                //searchMenu();
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
