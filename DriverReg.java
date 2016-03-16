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
  PeopleObj person;
  
  final String STRING_TYPE = "String";
  final String NUM_TYPE = "Integer";
  final String DATE_TYPE = "Date";

public DriverReg(){
  scanner = new Scanner(System.in);
  h = new Helpers();
  dL = new DriverObj();
  person = new PeopleObj();
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
      addPeople();
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
        h.checkValidity(i, 15, STRING_TYPE, true);
        h.checkFK(i, "people", "sin", false, true);
        break;
      }catch(CantBeNullException e){
        System.out.println("Entry cannot be null!");
      }catch(TooLongException e){
        System.out.println("Entry too long!");
      }catch(NumberFormatException e){
        System.out.println("Entry in the wrong format!");
      }catch(FKException e){
        System.out.println("Person is not in the table. Add a person instead? Y/N\n" +
                           "This will begin the add person program instead.");

        String response = scanner.nextLine();
  
        if(response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("N")){
          switch(response.toLowerCase()){
          case "y":
            addPeople();
            return; //TEST ME
            //break;
          }
        }

        
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
      
        boolean isValidPhoto= h.checkPhoto(i); 
        if(isValidPhoto)
          break;
        else System.out.println("Photo is not valid. Please try again.");
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
    
    confirmEntries(dL);
    
}//end of addLicence

//does the orderly stuff for the driver's licence registration **same title as the one for person, person has its own
public void confirmEntries(DriverObj drive){

    String isOk = scanner.nextLine();
  
  if(isOk.equalsIgnoreCase("Y") || isOk.equalsIgnoreCase("N") || isOk.equalsIgnoreCase("Q")){
    switch(isOk.toLowerCase()){
    case "y":
      addLicence();
      break;
      
    case "n":
      //**upload data to database here!
      
      if(!checkDriver(drive)){
        //commitDriver(drive); **write after
      }
         else System.out.println("Driver's licence already exists in the database. Please try again."); 
      break;  
    case "q":
      break; 
    }
    
  }else{
    System.out.println("Invalid input! Please try again.");
    confirmEntries(drive);
  }
  }


//does the orderly stuff for the driver's licence registration **same title as 
//the one for drive_licence, it has its own
public void confirmEntries(PeopleObj peep){

    String isOk = scanner.nextLine();

    if(isOk.equalsIgnoreCase("Y") || isOk.equalsIgnoreCase("N") || isOk.equalsIgnoreCase("Q")){
      switch(isOk.toLowerCase()){
      case "y":
        addPeople();
        break;

      case "n":
        //**upload data to database here!

        if(!checkPerson(peep)){
          //commitPerson(peep); **write after
        }
        else System.out.println("Driver's licence already exists in the database. Please try again.");
        break;
      case "q":
        break;
      }

    }else{
      System.out.println("Invalid input! Please try again.");
      confirmEntries(peep);
    }
  }
  
  
  //checks if the primary and unique keys are already in the database for the driver's licence
public boolean checkDriver(DriverObj d){
  //need to check primary key and unique key together
  //it's NOT ok if there are multiple sins (but can be null)
  //it's NOT ok if there are multiple licence_no's 

  boolean duh = true;
  String query;
  
  if(d.getSin() !=null){
    
    query = "select licence_no, sin from Drive_licence" +
      " where UPPER(licence_no) ='" + d.getLicenceNo().toUpperCase() +
      "' and UPPER(sin)='" + d.getSin().toUpperCase() + "'";
  }else{
    //get in here if sin is listed as null
    query = "select licence_no, sin from Drive_licence" +
      " where UPPER(licence_no) ='" + d.getLicenceNo().toUpperCase() +
      "' and sin is null";
  }
  
  
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
  
  
}//end of checkDriver

//function used to ask the user for info to add people to the people table
public void addPeople(){
  System.out.println("Please enter the person's information, ONE LINE AT A TIME, as such: \n" + "Sin number(15), name(40), height(5,2), weight(5,2), eyecolor(10),\n" + "hair color(10), address(50), gender(m/f), birthday(yyyy-mm-dd).\n" + "Please note: the sin number cannot be blank.\n");

  String i;

  while(true){
    System.out.print("Sin number(15): ");
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

  person.setSinNo(i);
  
  while(true){
    System.out.print("Name(40): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkValidity(i, 40, STRING_TYPE, true);
      break;
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }

  }

  person.setName(i);
  
  while(true){
    System.out.print("Height(5,2): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkDecimal(i, 5, 2, true); 
      break;
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }

  }
  person.setHeight(i);
  
  while(true){
    System.out.print("Weight(5,2): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkDecimal(i, 5, 2, true); 
      break;
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }

  }

  person.setWeight(i);

  while(true){
    System.out.print("Eyecolor(10): ");
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
  
  person.setEyecolor(i);

  while(true){
    System.out.print("Haircolor(10): ");
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
  
  person.setHaircolor(i);

  while(true){
    System.out.print("Address(50): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkValidity(i, 50, STRING_TYPE, true);
      break;
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }

  }

  person.setAddress(i);

  while(true){
    System.out.print("Gender(m/f): "); 
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkValidity(i, 1, STRING_TYPE, true);
      if(i == null) break;
        if(i.equalsIgnoreCase("M") || i.equalsIgnoreCase("F")){
          break;
      }else System.out.println("Entry must be m or f.");
      
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }

  }

  person.setGender(i);
  java.sql.Date myDate=null;
  boolean dateFlag = false;
  
  while(true){
    System.out.print("Birthday(yyyy-mm-dd): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkDate(i, 10, DATE_TYPE); 
      break;
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }catch(DateFormatException e){
      System.out.println("No letters in the date please!");
    }catch(DateIsNullException e){
      dateFlag = true;
      break;
    }

  }

  if(dateFlag) person.setBirthday(myDate);
  else person.setBirthday(java.sql.Date.valueOf(i));


  System.out.println("Data entered: ");
  person.printAll();
  System.out.println("\nWas there a mistake? Y/N or Q to quit (will not upload to database.)");

  confirmEntries(person);
  

  
}//end of addPeople

public boolean checkPerson(PeopleObj p){
  return true;
  
}
  
}
