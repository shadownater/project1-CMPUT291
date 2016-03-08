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
  
  
                                                         
public VehicleReg(){
  scanner = new Scanner(System.in);
  h = new Helpers();
  vehicle = new VehicleObj();
}


//displays the menu for vehicle registration
public void vehicleRegMenu(){
  System.out.println("Would you like to register a vehicle? Y/N\nOr would you like to add an owner? O");

  
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
      
      if(!checkVehicle(v)) commitVehicle(v);
         else System.out.println("Vehicle already exists in the database. Please try again."); 
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

    boolean duh = true;
    
    // SQL statement to execute
    String query = "select serial_no from Vehicle" +
      " where serial_no ='" + vData.getSerialNo() + "'";
    
    try{
      
      ResultSet rs = Login.stmt.executeQuery(query);

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

//commits the vehicle to the database
  public void commitVehicle(VehicleObj veh){
    System.out.println("Adding to database...");

    //create SQL insert statement
    String statement = veh.createInsertStatement();
    System.out.println(statement);

    try{
      Login.stmt.executeUpdate(statement);
      System.out.println("Vehicle successfully added to the database!");
    }catch(SQLException ex) {
      System.err.println("SQLException: " +
                        ex.getMessage());
    }
    
  }


  
//adds an owner to the database - called x times when a vehicle is successfully created
  public void addOwner(){
  }
}
