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

public VehicleReg(){
}

public void tester(){
  System.out.println("I'm a thing!");

}

public void vehicleRegMenu(){
  System.out.println("Would you like to register a vehicle? Y/N");

  Scanner scanner = new Scanner(System.in);
  String input = scanner.next();

  if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")){
    switch(input.toLowerCase()){
    case "y":
      System.out.println("YEAH");
      break;
    case "n":
      System.out.println("NOPE");
      break;
    }
    
    }
}
  
}
