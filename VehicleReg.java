import java.util.*;
import java.sql.*;
import java.io.*;


//New Vehicle Registration

//This component is used to register a new vehicle by an auto registration officer.
//By a new vehicle, we mean a vehicle that has not been registered in the database.
//The component shall allow an officer to enter the detailed information about the
//vehicle and personal information about its new owners, if it is not in the database.
//You may assume that all the information about vehicle types has been loaded in the initial database.

public class VehicleReg{
  Scanner scanner;
  Helpers h;
VehicleObj vehicle;

  //data string man 
  String vehicleString, ownerString;
  final String STRING_TYPE = "String";
  final String NUM_TYPE = "Integer";
  
  // The URL we are connecting to
  final String m_url = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
  
  // The driver to use for connection
  final String m_driverName = "oracle.jdbc.driver.OracleDriver";
                                                         
public VehicleReg(){
  scanner = new Scanner(System.in);
  h = new Helpers();
  vehicle = new VehicleObj();
}


//displays the menu for vehicle registration
public void vehicleRegMenu(){
  System.out.println("Would you like to register a vehicle? Y/N");

  
  String input = scanner.nextLine();

  if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")){
    switch(input.toLowerCase()){
    case "y":
      addVehicle();
      break;
      
    case "n":
      break;
    }
    
  }else{
    System.out.println("Invalid input!");
    vehicleRegMenu();
  }
  
} //end of vehicleRegMenu

public void confirmEntries(VehicleObj v){
  String isOk = scanner.nextLine();
  
  if(isOk.equalsIgnoreCase("Y") || isOk.equalsIgnoreCase("N") || isOk.equalsIgnoreCase("Q")){
    switch(isOk.toLowerCase()){
    case "y":
      addVehicle();
      break;
      
    case "n":
      //**upload data to database here!
      
      if(checkVehicle(v)) System.out.println("True!"); //want this to return uhh
         else System.out.println("False!"); //if-check, then: commitVehicle();
      break;  
    case "q":
      break; 
    }
    
  }else{
    System.out.println("Invalid input! Please try again.");
    confirmEntries(v);
  }
  
}//end of confirmEntries

public void addVehicle(){
  System.out.println("Please enter the vehicle information, ONE LINE AT A TIME, as such: \n" +
                     "Serial number(15), maker(20), model(20), year(4), colour(10), type ID(38).\n" +
                     "Please note: the serial number cannot be blank.\n");

  String i;
  boolean r;

  while(true){
  System.out.print("Serial number(15): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;

  try{
    h.checkValidity(i, 15, STRING_TYPE, false);
    break;
  }catch(CantBeNullException e){
    System.out.println("Entry cannot be null!");
  }catch(TooLongException e){
    System.out.println("Entry too long!");
  }catch(NumberFormatException e){
    System.out.println("Entry in the wrong format!");
  }
    
  }
  
  vehicle.setSerialNo(i);
  //vehicleString = i + " ";

  while(true){
  System.out.print("Maker(20): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;
  try{
    h.checkValidity(i, 20, STRING_TYPE, true);
    break;
  }catch(CantBeNullException e){
    System.out.println("Entry cannot be null!");
  }catch(TooLongException e){
    System.out.println("Entry too long!");
  }catch(NumberFormatException e){
    System.out.println("Entry in the wrong format!");
  }

  }
  vehicle.setMaker(i);
  //vehicleString += i + " ";

  while(true){
  System.out.print("Model(20): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;
  try{
    h.checkValidity(i, 20, STRING_TYPE, true);
    break;
  }catch(CantBeNullException e){
    System.out.println("Entry cannot be null!");
  }catch(TooLongException e){
    System.out.println("Entry too long!");
  }catch(NumberFormatException e){
    System.out.println("Entry in the wrong format!");
  }
    
  }
  vehicle.setModel(i);
  //vehicleString += i + " ";

  while(true){
  System.out.print("Year(4): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;
  try{
    h.checkValidity(i, 4, NUM_TYPE, true);
    break;
  }catch(CantBeNullException e){
    System.out.println("Entry cannot be null!");
  }catch(TooLongException e){
    System.out.println("Entry too long!");
  }catch(NumberFormatException e){
    System.out.println("Entry in the wrong format!");
  }
    
  }
  
  vehicle.setYear(i); //might complain here
  //vehicleString += i + " ";

  while(true){
  System.out.print("Colour(10): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;
  try{
    h.checkValidity(i, 10, STRING_TYPE, true);
    break;
  }catch(CantBeNullException e){
    System.out.println("Entry cannot be null!");
  }catch(TooLongException e){
    System.out.println("Entry too long!");
  }catch(NumberFormatException e){
    System.out.println("Entry in the wrong format!");
  }
  
  }

  vehicle.setColour(i);
  //vehicleString += i+ " ";

  while(true){
  System.out.print("Type ID(38): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;
  try{
  h.checkValidity(i, 38, NUM_TYPE, true);
  break;
  }catch(CantBeNullException e){
    System.out.println("Entry cannot be null!");
  }catch(TooLongException e){
    System.out.println("Entry too long!");
  }catch(NumberFormatException e){
    System.out.println("Entry in the wrong format!");
  }
    
  }

  vehicle.setType_Id(i);
  //vehicleString += i + " ";

  System.out.println("Data entered: ");
  vehicle.printAll();
  System.out.println("\nWas there a mistake? Y/N or Q to quit (will not upload to database.)");

  confirmEntries(vehicle);
  
  
}

//checks if the vehicle exists in the database already or not
//checking here is done by the primary key - ie, the serial number!
//true = the serial_no already exists
//false = it doesn't exist, therefore go ahead and add your vehicle!
  public boolean checkVehicle(VehicleObj vData){

    // get username
    System.out.print("Username: ");
    Console co = System.console();
    String m_userName = co.readLine();
    
    // obtain password
    char[] passwordArray = co.readPassword("Password: ");
    String m_password = new String(passwordArray);

    Connection m_con;
    String query;
    boolean duh = true;
    
    // SQL statement to execute
    query = "select serial_no from Vehicle" +
      " where serial_no ='" + vData.getSerialNo() + "'";
    
    try{
      Class drvClass = Class.forName(m_driverName);    
    }catch(Exception e){
      System.err.print("ClassNotFoundException: ");
      System.err.println(e.getMessage());
    }
    
    try{
      // Establish a connection
      m_con = DriverManager.getConnection(m_url, m_userName, m_password);
      
      // Create a statement object.
      // Changed to reflect changes made in the result set and to make these changes permanent to the database too
      Statement stmt = m_con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = stmt.executeQuery(query);

      //check if returned anything or not

      duh = rs.next();
      System.out.println(duh);

    }catch(SQLException ex) {
      System.err.println("SQLException: " +
                         ex.getMessage());
    }

    if(duh)return true;
      else return false;
    
  }

//adds an owner to the database - called x times when a vehicle is successfully created
  public void addOwner(){
  }
}
