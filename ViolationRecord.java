import java.util.*;
import java.sql.*;
import java.io.*;

// Violation Record
// This is used by police officers to issue traffic tickets
// and record the violation. An officer may choose to issue
// the ticket to the primary owner of the vehicle involved
// or to the driver of the vehicle.

public class ViolationRecord {
  Scanner scanner;
  Helpers h;
  ViolationObj vio;
  DriverReg noob;

  public ViolationRecord() {
    scanner = new Scanner(System.in);
    h = new Helpers();
    vio = new ViolationObj();
    noob = new DriverReg();
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
    float n;
    int m;
    String query;
    ResultSet rs;

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
    // If vehicle does not exist, then tell user to:
    // a) try again
    // b) go to VehicleReg from Main Menu and come back later
    while(true) {
      System.out.print("(15 Characters) Vehicle Id: ");
      i = scanner.nextLine().trim();
      if(i.isEmpty()) i=null;
      // TODO: make sure vehicle exists. If not, try again or go to main menu
      try {
        // Make sure input is valid input
        h.checkValidity(i, 15, "String", false);
        // Make sure vehicle exists
        query = "Select serial_no from vehicle";
        rs = Login.stmt.executeQuery(query);
        String id = new String();
        while(rs.next()) {
          id = rs.getString("serial_no").trim().toLowerCase();
          // If a match is found, set vehicle Id and break out of loop
          if(id.equals(i.toLowerCase())) {
            vio.setVehicleID(id);
            break;
          }
        }
        // If the id is not i, then return that it does not exist
        if(!id.equals(i.toLowerCase())) {
          throw new DNEException();
        }
        break;
      } catch(CantBeNullException e) {
        System.out.println("Please enter a valid Vehicle Id: ");
      } catch(TooLongException e) {
        System.out.println("Please enter a shorter Vehicle Id: ");
      } catch(NumberFormatException e) {
        System.out.println("Please enter a string Vehicle Id: ");
      } catch(SQLException e) {
        System.out.println("SQL Fail. Try again.");
      } catch(DNEException e) {
        System.out.println("Vehicle does not exist. Try again(T) or return to Main Menu(other)?: ");
      // If the vehicle does not exist, then either try again
      // Or go back to main menu.
      String input = scanner.nextLine();
      if(input.equalsIgnoreCase("T")) {
        switch(input.toLowerCase()) {
        case "t":
          break;
        }
      } else {
        System.out.println("See you soon.");
        return;
      }
    }

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
        finRecord(vio);
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

public void finRecord(ViolationObj vio) {
  System.out.println("Record Complete!");
}

}
