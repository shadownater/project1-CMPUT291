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
  DriverObj dL;
  
  final String STRING_TYPE = "String";
  final String NUM_TYPE = "Integer";
  final String DATE_TYPE = "Date";

public DriverReg(){
  scanner = new Scanner(System.in);
  h = new Helpers();
  dL = new DriverObj();
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
      System.out.print("Licence Number(15): ");
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

    dL.setLicenceNo(i);

    while(true){
      System.out.print("Sin(15): ");
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

    dL.setSin(i);
    
    while(true){
      System.out.print("Class(10): ");
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

    dL.setDrivingClass(i);
    
    while(true){
      System.out.print("Photo: ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      try{
        h.checkValidity(i, 50, STRING_TYPE, true); //how do you check a photo's validity?
        break;
      }catch(CantBeNullException e){
        System.out.println("Entry cannot be null!");
      }catch(TooLongException e){
        System.out.println("Entry too long!");
      }catch(NumberFormatException e){
        System.out.println("Entry in the wrong format!");
      }

    }

    dL.setPhoto(i); 

    java.sql.Date myDate = null;
    boolean dateFlag=false;
    
    while(true){
      System.out.print("Issue Date(yyyy-mm-dd): ");
      i = scanner.nextLine();
      if(i.isEmpty()) i = null; 
      try{
        h.checkDate(i, 10, DATE_TYPE); 
        break;
      }catch(DateFormatException e){
        System.out.println("Entry in the wrong format. Expected yyyy-mm-dd!");
      }catch(TooLongException e){
        System.out.println("Entry too long!");
      }catch(NumberFormatException e){
        System.out.println("No letters in the date please!");
      }catch(DateIsNullException e){
        dateFlag = true;
        break;
      }

    }

    if(dateFlag) dL.setIssueDate(myDate);
    else dL.setIssueDate(java.sql.Date.valueOf(i));

    dateFlag=false;
    
    while(true){
      System.out.print("Expiration Date(yyyy-mm-dd): ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      try{
        h.checkDate(i, 10, DATE_TYPE);
        break;
      }catch(DateFormatException e){
        System.out.println("Entry in the wrong format. Expected yyyy-mm-dd!");
      }catch(TooLongException e){
        System.out.println("Entry too long!");
      }catch(NumberFormatException e){
        System.out.println("No letters in the date please!");
      }catch(DateIsNullException e){
        dateFlag = true;
        break;
      }
      

    }
    
    if(dateFlag) dL.setExpiryDate(myDate);
    else dL.setExpiryDate(java.sql.Date.valueOf(i));

    System.out.println("Data entered: ");
    dL.printAllReg();
    System.out.println("\nWas there a mistake? Y/N or Q to quit (will not upload to database.)");
    
    //confirmEntries(dL);
    
  }


}
