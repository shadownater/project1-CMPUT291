import java.util.*;
import java.sql.*;
import java.io.*;


//Driver Licence Registration

//This component is used to record the information needed to issuing a drive licence,
//including the personal information and a picture for the driver.
//You may assume that all the image files are stored in a local disk system.


public class DriverReg{
  Scanner scanner;
  Helpers h;

  final String STRING_TYPE = "String";
  final String NUM_TYPE = "Integer";


public DriverReg(){
  scanner = new Scanner(System.in);
  h = new Helpers();
}

//displays the menu for driver licence registration
public void driverRegMenu(){
  System.out.println("Would you like to register a new licence? Y/N\nOr would you like to register a person? P");


  String input = scanner.nextLine();

  if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N") || input.equalsIgnoreCase("P")){
    switch(input.toLowerCase()){
    case "y":
      addLicence();
      break;

    case "n":
      break;

    case "p":
      //addPersonMenu();
    }

  }else{
    System.out.println("Invalid input!");
    driverRegMenu();
  }
}

public void addLicence(){

  //some new things in here: blob, varchar, date, and unique constraint!
  System.out.println("Please enter the licence information, ONE LINE AT A TIME, as such: \n" +
                     "Licence number(15), sin(15), class(10), photo, issuing date (yyyy/mm/dd)," +
                     "expiring date(yyyy/mm/dd).\n" +
                     "Please note: the licence number cannot be blank.\n");

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
    
    
  }


}
