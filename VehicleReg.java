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
  Owner owner;

  //data string man 
  String vehicleString, ownerString;
  final String STRING_TYPE = "String";
  final String NUM_TYPE = "Integer";
  
  
                                                         
public VehicleReg(){
  scanner = new Scanner(System.in);
  h = new Helpers();
  vehicle = new VehicleObj();
  owner = new Owner();
}


//displays the menu for vehicle registration
public void vehicleRegMenu(){
  System.out.println("Would you like to register a vehicle? Y/N\nOr would you like to add an owner? O");

  
  String input = scanner.nextLine();

  if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N") || input.equalsIgnoreCase("O")){
      switch(input.toLowerCase()){
    case "y":
      addVehicle();
      break;
      
    case "n":
      break;

    case "o":
      addOwnerMenu();
    }
    
  }else{
    System.out.println("Invalid input!");
    vehicleRegMenu();
  }
  
} //end of vehicleRegMenu

//does the orderly stuff for the vehicle registration **same title as the one for owner, owner has its own
public void confirmEntries(VehicleObj v){
  String isOk = scanner.nextLine();
  
  if(isOk.equalsIgnoreCase("Y") || isOk.equalsIgnoreCase("N") || isOk.equalsIgnoreCase("Q")){
    switch(isOk.toLowerCase()){
    case "y":
      addVehicle();
      break;
      
    case "n":
      //**upload data to database here!
      
      if(!checkVehicle(v)){
        commitVehicle(v);
        addOwnerMenu();
      }
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

//does the orderly stuff for the owner registration **same title as the one for vehicle, vehicle has its own
public void confirmEntries(Owner o){
  String isOk = scanner.nextLine();
  
  if(isOk.equalsIgnoreCase("Y") || isOk.equalsIgnoreCase("N") || isOk.equalsIgnoreCase("Q")){
    switch(isOk.toLowerCase()){
    case "y":
      addOwner();
      break;
      
    case "n":
      //**upload data to database here!
      
      if(!h.checkTwoPK("owner_id", "vehicle_id", o.getOwnerId(), o.getVehicleId(), "owner", true)){
        commitOwner(o);
        vehicleRegMenu();
      }
         else System.out.println("Owner already exists in the database. Please try again."); 
      break;  
    case "q":
      break; 
    }
    
  }else{
    System.out.println("Invalid input! Please try again.");
    confirmEntries(o);
  }
  
}//end of confirmEntries

public void addVehicle(){
  System.out.println("Please enter the vehicle information, ONE LINE AT A TIME, as such: \n" +
                     "Serial number(15), maker(20), model(20), year(4), colour(10), type ID(38).\n" +
                     "Please note: the serial number cannot be blank.\n");

  String i;

  while(true){
  System.out.print("Serial number(15): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;

  try{
    h.checkValidity(i, 15, STRING_TYPE, false);
    //check if that primary key is available or not
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
  
  vehicle.setYear(i); 

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
  boolean bah=false;
  
  while(true){
  System.out.print("Type ID(38): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;
  try{
  h.checkValidity(i, 38, NUM_TYPE, true);
  h.checkFK(i, "vehicle_type", "type_id", true, true);
  break;
  }catch(CantBeNullException e){
    System.out.println("Entry cannot be null!");
  }catch(TooLongException e){
    System.out.println("Entry too long!");
  }catch(NumberFormatException e){
    System.out.println("Entry in the wrong format!");
  }catch(FKException e){
    System.out.println("Entry does not exist in vehicle_type table. Add to type table? Y/N");

    String response = scanner.nextLine();
  
  if(response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("N") || response.equalsIgnoreCase("Q")){
    switch(response.toLowerCase()){
    case "y":
      commitType(i);
      bah=true;
      break;
    }//end of switch 
  }//end of if statement
  }//end of catch
  if(bah)break;
  }//end of while
  
  vehicle.setType_Id(i);

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
      " where UPPER(serial_no) ='" + vData.getSerialNo().toUpperCase() + "'";
    
    try{
      
      ResultSet rs = Login.stmt.executeQuery(query);

      //check if returned anything or not

      duh = rs.next();


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

    
    try{
       Login.stmt.executeUpdate(statement);
      System.out.println("Vehicle successfully added to the database!");
    }catch(SQLException ex) {
      System.err.println("SQLException: " +
                        ex.getMessage());
    }
    
    
  }

  public void commitType(String type_id){
    System.out.println("Adding to database...");

    //create SQL insert statement

    String statement = "insert into vehicle_type values (" + type_id + ", NULL)"; 

    
    try{
       Login.stmt.executeUpdate(statement);
      System.out.println("Type_ID successfully added to the database!");
    }catch(SQLException ex) {
      System.err.println("SQLException: " +
                        ex.getMessage());
    }

  }

  
//adds an owner to the database - called x times when a vehicle is successfully created
  public void addOwnerMenu(){

    System.out.println("Would you like to add an owner? Y/N");

    String response = scanner.nextLine();
    
    if(response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("N")){
    switch(response.toLowerCase()){
    case "y":
      addOwner();
      break;
      
    case "n":
      break;  
    }
    }
  }

public void addOwner(){

  System.out.println("Please enter the owner information, ONE LINE AT A TIME, as such: \n" +
                     "Owner ID(15), Vehicle ID(20), primary owner?(y/n)\n" +
                     "Please note: NONE of the values can be blank.\n");
  
  String i;
  boolean c1 =false, c2 = false;
  
  while(true){
    System.out.print("Owner ID(15): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkValidity(i, 15, STRING_TYPE, false);
      h.checkFK(i, "people", "sin", false, false);
      break;
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }catch(FKException e){
    System.out.println("Entry does not exist in people table. Add to people table? Y/N");

    String response = scanner.nextLine();
  
  if(response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("N")){
    switch(response.toLowerCase()){
    case "y":
    boolean result=false;
    result = commitPeople(i);
      c1=true;
      if(c1 && result)
        break;
    }//end of switch 
  }//end of if statement
  }//end of catch
    if(c1)break;
  }
  
  owner.setOwnerId(i); 

  while(true){
  System.out.print("Vehicle ID(15): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;
  try{
    h.checkValidity(i, 15, STRING_TYPE, false);
    h.checkFK(i, "vehicle", "serial_no", false, false);
    break;
  }catch(CantBeNullException e){
    System.out.println("Entry cannot be null!");
  }catch(TooLongException e){
    System.out.println("Entry too long!");
  }catch(NumberFormatException e){
    System.out.println("Entry in the wrong format!");
  }catch(FKException e){
    System.out.println("Entry does not exist in vehicle table. Make a vehicle instead? Y/N");

    String response = scanner.nextLine();
  
  if(response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("N")){
    switch(response.toLowerCase()){
    case "y":
      addVehicle();
      break;
    }//end of switch 
  }//end of if statement
  }//end of catch
  
  }

  owner.setVehicleId(i);
  
  while(true){
  System.out.print("Primary Owner?(y/n): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;
  try{
    h.checkValidity(i, 1, STRING_TYPE, false);
    if(i.equalsIgnoreCase("Y") || i.equalsIgnoreCase("N")){
      if(i.equalsIgnoreCase("Y")) checkPrimaryOwner(owner.getVehicleId());
      if(i.equalsIgnoreCase("N")) checkAnyPrimary(owner.getVehicleId());
      break;
    }
    else System.out.println("Entry must be 'y' or 'n'");
  }catch(CantBeNullException e){
    System.out.println("Entry cannot be null!");
  }catch(TooLongException e){
    System.out.println("Entry too long!");
  }catch(NumberFormatException e){
    System.out.println("Entry in the wrong format!");
  }catch(AlreadyPrimaryException e){
    System.out.println("There's already a primary owner for this vehicle. Please try again.");
  }catch(NoPrimaryException e){
    System.out.println("No primary owner for this vehicle. Owner must be primary owner.");
  }
  }//end of while
  
  owner.setIsPO(i);

  System.out.println("Data entered: ");
  owner.printAll();
  System.out.println("\nWas there a mistake? Y/N or Q to quit (will not upload to database.)");

  confirmEntries(owner);
}//end of addOwner

//adds the owner to the database
public void commitOwner(Owner own){

  String query = own.createInsertStatement();
  System.out.println(query);
  
  try{
    Login.stmt.executeUpdate(query);
    System.out.println("Owner successfully added to the database!");
  }catch(SQLException ex) {
    System.err.println("SQLException: " +
                       ex.getMessage());
  }
  
}

//adds a person to the database (already know they're not in it)
public boolean commitPeople(String sin){
  //make sure the sin is valid before going ahead
  try{
  h.checkValidity(sin, 15, STRING_TYPE, false);
  }catch(CantBeNullException e){return false;
  }catch(TooLongException e){return false;
  }catch(NumberFormatException e){return false;
  }

  //get here if it's ok!
  
  String query = "insert into people values ('" + sin + "', null, null, null, null, null, null, null, null)";

  try{
    Login.stmt.executeUpdate(query);
    System.out.println("Person successfully added to the database!");
  }catch(SQLException ex) {
    System.err.println("SQLException: " +
                       ex.getMessage());
  }
  
  return true;
}

//Checks if the vehicle already has a primary owner - throws an exception if it does
  public void checkPrimaryOwner(String vehicleId) throws AlreadyPrimaryException{
    
    String query = "Select is_primary_owner from owner where UPPER(vehicle_id)= '" + vehicleId.toUpperCase() + "'";

    try{

      ResultSet rs = Login.stmt.executeQuery(query);

      //check if returned any with 'y' in it

      boolean foundY=false;
      
      while(rs.next()){
        if(rs.getString("is_primary_owner").equalsIgnoreCase("y")) foundY=true;
      }
      
      if(foundY) throw new AlreadyPrimaryException();


    }catch(SQLException ex) {
      System.err.println("SQLException: " +
                         ex.getMessage());
    }
  }

  public void checkAnyPrimary(String vehicleId) throws NoPrimaryException{
    String query = "Select is_primary_owner from owner where UPPER(vehicle_id)= '" + vehicleId.toUpperCase() + "'";

    try{

      ResultSet rs = Login.stmt.executeQuery(query);

      //check if returned any with 'y' in it

      boolean foundY=false;

      while(rs.next()){
        if(rs.getString("is_primary_owner").equalsIgnoreCase("y")) foundY=true;
      }

      if(!foundY) throw new NoPrimaryException();


    }catch(SQLException ex) {
      System.err.println("SQLException: " +
                         ex.getMessage());
    }
    
  }
}
