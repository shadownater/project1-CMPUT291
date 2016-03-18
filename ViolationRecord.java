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

    // Ask user for ticket#
    // If ticket# exists, try again or exit to main menu
    while(true) {
      System.out.print("(Integer)         | Ticket#: ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      try {
        // make sure input is valid
        h.checkValidity(i, 100, "Integer", true);
        m = Integer.parseInt(i);
        // Make sure ticket# does not exist
        query = "Select ticket_no from ticket";
        rs = Login.stmt.executeQuery(query);
        int id;
        while(rs.next()) {
          id = rs.getInt("ticket_no");
          // If a match is found, throw exception
          if(id == m) {
            throw new DNEException();
          }
        }
        // If we make it here, we can set it.
        vio.setTicketNo(m);
        break;
      } catch(CantBeNullException e) {
        System.out.println("Please enter a valid Ticket#: ");
      } catch(TooLongException e) {
        System.out.println("Please enter a shorter Ticket#: ");
      } catch (NumberFormatException e) {
        System.out.println("Please enter a number: ");
      } catch (SQLException e) {
        System.out.println("SQL Fail. Try again.");
      } catch(DNEException e) {
        System.out.println("Ticket# already exists. Try again(T) or return to Main Menu(other)?");
        // If the transaction_id already exists, try again or quit to mainMenu
        String input = scanner.nextLine();
        if(input.equalsIgnoreCase("T")) {
          switch(input.toLowerCase()) {
          case "t":
            break;
          }
        } else {
          System.out.println("Goodbye!");
          return;
        }
      }
    }

    // Ask user for OfficerId:
    // If person DNE, try again or create person
    while(true) {
      System.out.print("(15 characters)   | Officer Id: ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      // TODO: Check if person exists or not
      // if DNE, try again or create person
      try {
        // Check that input is valid
        h.checkValidity(i, 15, "String", false);
        // Make sure person exists
        query = "Select sin from people";
        rs = Login.stmt.executeQuery(query);
        String id = new String();
        while(rs.next()) {
          id = rs.getString("sin").trim().toLowerCase();
          // If a match is found, set officer id and break out of loop
          if(id.equals(i.toLowerCase())) {
            vio.setOfficeNo(id);
            break;
          }
        }
        if(!id.equals(i.toLowerCase())) {
          throw new DNEException();
        }
        break;
      } catch(CantBeNullException e) {
        System.out.println("Please enter a valid Officer Id: ");
      } catch(TooLongException e) {
        System.out.println("Please enter a shorter Officer Id: ");
      } catch(NumberFormatException e) {
        System.out.println("Please enter a string Officer Id: ");
      } catch(SQLException e) {
        System.out.println("SQL Fail. Try again.");
      } catch(DNEException e) {
        System.out.println("Person does not exist. Try again(T), Register person(R), or return to Main Menu(other)");
        // If officer DNE then try again, register, or return to main menu.
        String input = scanner.nextLine();
        if(input.equalsIgnoreCase("T") || input.equalsIgnoreCase("R")) {
          switch(input.toLowerCase()) {
          case "t":
            break;
          case "r":
            noob.addPeople();
            break;
          }
        } else {
          System.out.println("Goodbye!");
          return;
        }
      }
    }

    // Ask User for VehicleId:
    while(true) {
      System.out.print("(15 characters)   | Vehicle Id: ");
      i = scanner.nextLine().trim();
      if(i.isEmpty()) i=null;
      try {
        // Make sure input is valid input
        h.checkValidity(i, 15, "String", false);
        // Make sure vehicle exists
        query = "Select serial_no from vehicle";
        rs = Login.stmt.executeQuery(query);
        String id = new String();
        while(rs.next()) {
          id = rs.getString("serial_no").trim().toLowerCase();
          // If a match is found, set vehicle ID and break out of loop
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
      } catch (SQLException e) {
        System.out.println("SQL Fail. Try again.");
      } catch (DNEException e) {
        System.out.println("Vehicle does not exist. Try again(T) or \nreturn to Main Menu to complete an Auto Registration(other)?: ");
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
    }

    boolean flag = true;
    // Ask User for ViolatorId:
    while(flag) {
      System.out.println("Would you like to issue the ticket to the driver(D) or primary owner(O) of the vehicle?");
      String select = scanner.nextLine();
      if(select.equalsIgnoreCase("D") || select.equalsIgnoreCase("O")) {
        switch(select.toLowerCase()) {
        case "d":
          System.out.print("(15 characters)   | Violator Id: ");
          i = scanner.nextLine();
          if(i.isEmpty()) i=null;
          // check if person exists. If DNE, try again or create person
          try {
            // Check that input is valid
            h.checkValidity(i, 15, "String", false);
            // Make sure person exists
            query = "Select sin from people";
            rs = Login.stmt.executeQuery(query);
            String id = new String();
            while(rs.next()) {
              id = rs.getString("sin").trim().toLowerCase();
              // If a match is found, set buyer id and break out of loop
              if(id.equals(i.toLowerCase())) {
                vio.setViolatorNo(id);
                flag = false;
                break;
              }
            }
            if(!id.equals(i.toLowerCase())) {
              throw new DNEException();
            }
            break;
          } catch(CantBeNullException e) {
            System.out.println("Please enter a valid Violator Id: ");
          } catch(TooLongException e) {
            System.out.println("Please enter a shorter Violator Id: ");
          } catch(NumberFormatException e) {
            System.out.println("Please enter a string Violator Id: ");
          } catch(SQLException e) {
            System.out.println("SQL Fail. Try again.");
          } catch(DNEException e) {
            System.out.println("Person does not exist. Try again(T), Register a person(R), or return to Main Menu(other)?");
            // If the buyer does not exist, then either try again
            // or register a new person or quite to mainMenu.
            String again = scanner.nextLine();
            if(again.equalsIgnoreCase("T") || again.equalsIgnoreCase("R")) {
              switch(again.toLowerCase()) {
              case "t":
                break;
              case "r":
                noob.addPeople();
                break;
              }
            } else {
              System.out.println("Goodbye!");
              return;
            }
          }
          break;
        case "o":
          query = "select owner_id from owner where is_primary_owner = 'y'\n" +
            "and LOWER(vehicle_id) = '" + vio.getVehicleID().toLowerCase() + "'";
          try {
            rs = Login.stmt.executeQuery(query);
            while(rs.next()) {
              String s = rs.getString("owner_id");
              vio.setViolatorNo(s);
              flag = false;
            }
            System.out.println("primary owner blamed\n");
          } catch(SQLException e) {
            System.err.println("SQL Fail: " + e.getMessage());
          }
        }
      } else {
        System.out.println("Goodbye!");
        return;
      }
    }
    
    // Ask user for VType:
    while(true) {
      System.out.print("(10 Characters)   | Violation Type: ");
      i = scanner.nextLine().replaceAll("\\s+","");
      if(i.isEmpty()) i=null;
      // TODO: make sure it is a valid VType.
      // If not, try again.
      try {
        // Make sure input is valid
        h.checkValidity(i, 10, "String", false);
        // Make sure input is a type in violation table
        query = "select vtype from ticket_type";
        rs = Login.stmt.executeQuery(query);
        String id = new String();
        while(rs.next()) {
          id = rs.getString("vtype").toLowerCase().trim();
          // if match is found, throw exception
          if(id.equals(i.toLowerCase())) {
            vio.setVtype(id);
            break;
          }
        }
        if(!id.equals(i.toLowerCase())) {
          throw new DNEException();
        }           
        break;
      } catch(CantBeNullException e) {
        System.out.println("Please enter a valid Violation Type: ");
      } catch(TooLongException e) {
        System.out.println("Please enter a shorter Violation Type: ");
      } catch(NumberFormatException e) {
        System.out.println("Please enter a string Violation Type: ");
      } catch(SQLException e) {
        System.out.println("SQL Fail. Try again");
      } catch(DNEException e) {
        System.out.println("Not a Type. Try again(T) or return to Main Menu(other)?");
        String input = scanner.nextLine();
        if(input.equalsIgnoreCase("T")) {
          switch(input.toLowerCase()) {
          case "t":
            break;
          }
        } else {
          System.out.println("Goodbye!");
          return;
        }
      }
    }

    // Ask user for Location:
    while(true) {
      System.out.print("(20 Characters)   | Location: ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      try {
        h.checkValidity(i, 20, "String", true);
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
      System.out.print("(1024 Characters) | Comments: ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      try {
        h.checkValidity(i, 1024, "String", true);
        vio.setDescriptions(i);
        break;
      } catch(CantBeNullException e) {
        System.out.println("Please enter a valid Comment: ");
      } catch(TooLongException e) {
        System.out.println("Please enter a shorter Comment: ");
      } catch(NumberFormatException e) {
        System.out.println("Please enter a string Comment: ");
      }
    }

    // Confirm Violation Record
    System.out.print("Make changes(Y/N) or return to Main Menu(other)?:\n");
    // Set date as today's date
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
      System.out.println("Goodbye!");
    }

    // TODO: Update database with ingormation given.
    // Tables required: ticket
  }


public void finRecord(ViolationObj vio) {
  System.out.println("adding...");

  //Create SQL insert statement to record ViolationRecord
  String createString = "insert into ticket values (" + vio.getTicketNo() +
    ", '" + vio.getViolatorNo() + "'" +
    ", '" + vio.getVehicleID() + "'" +
    ", '" + vio.getOfficeNo() + "'" +
    ", '" + vio.getVtype() + "'" +
    ", TO_DATE('" + vio.getVDate() + "', 'yyyy/mm/dd')" +
    ", '" + vio.getPlace() + "'" +
    ", '" + vio.getDescriptions() + "')";

  try {
    // Create a ticket
    Login.stmt.executeUpdate(createString);
    System.out.println("Record Complete!");
  } catch(SQLException e) {
    System.err.println("SQL Fail: " + e.getMessage());
  }
}
}


