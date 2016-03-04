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

  //data string man 
  String vehicleString, ownerString;
  final String STRING_TYPE = "String";
  final String NUM_TYPE = "Integer";

public VehicleReg(){
  scanner = new Scanner(System.in);
}

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
  }
  
} //end of vehicleRegMenu

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
  if(r = checkValidity(i, 15, STRING_TYPE, false)){
    break;
  }else System.out.println("Entry invalid. Please try again.");
  }

  vehicleString = i + " ";

  while(true){
  System.out.print("Maker(20): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;
  if(r = checkValidity(i, 20, STRING_TYPE, true)){
    break;
  }else System.out.println("Entry invalid. Please try again.");
  }

  vehicleString += i + " ";

  while(true){
  System.out.print("Model(20): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;
  if(r = checkValidity(i, 20, STRING_TYPE, true)){
    break;
  }else System.out.println("Entry invalid. Please try again.");
  }

  vehicleString += i + " ";

  while(true){
  System.out.print("Year(4): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;
  if(r = checkValidity(i, 4, NUM_TYPE, true)){
    break;
  }else System.out.println("Entry invalid. Please try again.");
  }

  vehicleString += i + " ";

  while(true){
  System.out.print("Colour(10): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;
  if(r = checkValidity(i, 10, STRING_TYPE, true)){
    break;
  }else System.out.println("Entry invalid. Please try again.");
  }

  vehicleString += i+ " ";

  while(true){
  System.out.print("Type ID(38): ");
  i = scanner.nextLine();
  if(i.isEmpty()) i=null;
  if(r = checkValidity(i, 38, NUM_TYPE, true)){
    break;
  }else System.out.println("Entry invalid. Please try again.");
  }

  vehicleString += i + " ";

  System.out.println("Data entered: " + vehicleString + "\nWas there a mistake? Y/N or Q to quit (will not upload to database.)");
  
}

//checks if the vehicle exists in the database already or not
  public boolean checkVehicle(){
    return true;
  }

  //AWAITING TA INFO ON IF I CAN HARDCODE THE LENGTHS, TYPES, NOT NULL/NULL
//checks if the given input is the right length, type, and if it can be left null
  public boolean checkValidity(String input, int length, String type, boolean canBeNull){

    if(input == null){
      if(!canBeNull){
        return false;
      }
    }

    //input is not null, yay! checking type - matches that we have a number/string?
    if(type == NUM_TYPE){
      try{
        Integer.parseInt(input);
          }catch(NumberFormatException e){
        return false; //can i even do this? argioghoieh
      } //number is ok, check its length
      if(input.length() <= length){
        return true;
      }else return false;
    }else{
      if(input == null) return true;
//it's a string type of some sort, it's ok if it has numbers mixed in
      if(input.length() <= length){
        return true;
          }else return false;
    }
    
  } //end of checkValidity

//adds an owner to the database - called x times when a vehicle is successfully created
  public void addOwner(){
  }
}
