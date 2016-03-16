import java.util.*;
import java.sql.*;
import java.io.*;

// Violation Record
//

//
public class ViolationRecord {
  Scanner scanner;
  Helpers h;
  ViolationObj vio;

  public ViolationRecord() {
    scanner = new Scanner(System.in);
    h = new Helpers();
    vio = new ViolationObj();
  }

  public void violationRecordMenu() {
    System.out.print("Would you like to record a violation? Y/N : ");

    String input = scanner.nextLine();

    if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
      switch(input.toLowerCase()) {
      case "y":
        addRecord();
        break;
      case "n":
        break;
      }
    } else {
      System.out.println("Invalid input!");
    }
  }

  public void addRecord() {
    // Initialize temporary variables:
    String i;
    //Integer n;

    // Begin asking user for input:
    System.out.print("Please fill out the following details within the constrictions: \n");

    // Ask user for OfficerId:
    // If person DNE, try again or create person
    while(true) {
      System.out.print("(15 characters) Officer Id: ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      // TODO: Check if person exists or not
      // if DNE, try again or create person
      try {
        h.checkValidity(i, 15, "String", false);
        break;
      } catch(CantBeNullException e) {
        System.out.println("Please enter a valid Officer Id: ");
      } catch(TooLongException e) {
        System.out.println("Please enter a shorter Officer Id: ");
      } catch(NumberFormatException e) {
        System.out.println("Please enter a string Officer Id: ");
      }
    }
    // Everything checks out, set the Officer Id
    vio.setOfficeNo(i);

    // Ask User for ViolatorId:
    while(true) {
      System.out.print("(15 characters) Violator Id: ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      // TODO: check if person exists
      // if DNE, try again or create person
      try {
        h.checkValidity(i, 15, "String", false);
        break;
      } catch(CantBeNullException e) {
        System.out.println("Please enter a valid Violator Id: ");
      } catch(TooLongException e) {
        System.out.println("Please enter a shorter Violator Id: ");
      } catch(NumberFormatException e) {
        System.out.println("Please enter a string Violator Id: ");
      }
    }
    // Everything checks out, set the ViolatorId:
    vio.setViolatorNo(i);

    // Ask user for VehicleId:
    while(true) {
      System.out.print("(15 Characters) Vehicle Id: ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      // TODO: make sure vehicle exists. If not, try again or go to main menu
      try {
        h.checkValidity(i, 15, "String", false);
        break;
      } catch(CantBeNullException e) {
        System.out.println("Please enter a valid Vehicle Id: ");
      } catch(TooLongException e) {
        System.out.println("Please enter a shorter Vehicle Id: ");
      } catch(NumberFormatException e) {
        System.out.println("Please enter a string Vehicle Id: ");
      }
    }
    // Everything checks out, set the VehicleId:
    vio.setVehicleID(i);

    // Ask user for VType:
    while(true) {
      System.out.print("(10 Characters) Violation Type: ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      // TODO: make sure it is a valid VType.
      // If not, try again.
      try {
        h.checkValidity(i, 10, "String", false);
        break;
      } catch(CantBeNullException e) {
        System.out.println("Please enter a valid Violation Type: ");
      } catch(TooLongException e) {
        System.out.println("Please enter a shorter Violation Type: ");
      } catch(NumberFormatException e) {
        System.out.println("Please enter a string Violation Type: ");
      }
    }
    // Everything checks out, set the VType:
    vio.setVtype(i);

    // Ask user for Location:
    while(true) {
      System.out.print("(20 Characters) Location: ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      try {
        h.checkValidity(i, 20, "String", false);
        break;
      } catch(CantBeNullException e) {
        System.out.println("Please enter a valid Location: ");
      } catch(TooLongException e) {
        System.out.println("Please enter a shorter Location: ");
      } catch(NumberFormatException e) {
        System.out.println("Please enter a string Location: ");
      }
    }
    // Everything checks out, set the Location:
    vio.setPlace(i);

    // Ask user for Comments:
    while(true) {
      System.out.print("(1024 Characters) Comments: ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      try {
        h.checkValidity(i, 1024, "String", true);
        break;
      } catch(CantBeNullException e) {
        System.out.println("Please enter a valid Comment: ");
      } catch(TooLongException e) {
        System.out.println("Please enter a shorter Comment: ");
      } catch(NumberFormatException e) {
        System.out.println("Please enter a string Comment: ");
      }
    }
    // Everything checks out, set the Comments:
    vio.setDescriptions(i);
  

    // Confirm Violation Record
    System.out.print("Are you sure you want to make these changes? Y/N:\n");
    // TODO: Set a record Id
    java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
    vio.setVDate(today);
    vio.printAll();

    String input = scanner.nextLine();

    if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
    switch(input.toLowerCase()){
      case "y":
        vio.finRecord();
        break;
      case "n":
        violationRecordMenu();
        break;
      }
    } else {
      System.out.println("Invalid Input!");
    }

  // TODO: Update database with ingormation given.
  // Tables required: ticket
}
}
