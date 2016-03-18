import java.util.*;
import java.sql.*;
import java.io.*;

// Auto Transaction

// This component is used to complete an auto transaction.
// It allows an officer to enter all necessary information
// to complete an auto registration including:
// details about seller, buyer, date, and price (at least).
// If the vehicle is already registered, it overwrites the
// old transaction information with the new transaction
// information.

public class AutoTrans{
  Scanner scanner;
  Helpers h;
  TransactionObj trans;
  DriverReg noob;
                      
public AutoTrans() {
  scanner = new Scanner(System.in);
  h = new Helpers();
  trans = new TransactionObj();
  noob = new DriverReg();
}

public void autoTransMenu() {
  // Confirm this is where you want to be
  System.out.print("Would you like to register a transaction? Y/N : ");

  String input = scanner.nextLine();

  if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")){
    switch(input.toLowerCase()){
    case "y":
      addTransaction();
      break;
      
    case "n":
      break;
    }
    
  } else {
    // Return to main menu anyways
    System.out.println("Invalid input!");
  }
}

public void addTransaction() {

  // Initialize use variables
  float n;
  int m;
  String i;
  String query;
  ResultSet rs;
  
  // Begin asking user for input:
  System.out.print("Please fill out the following details in the alotted amount of space:\n");

  // Ask user for auto_sold/vehicle_id
  // If vehicle does not exist (check) then tell user to
	// a) try again, enter correctly
	// b) go to VehicleReg and come back once the vehicle is registered
  while(true) {
    System.out.print("(15 characters)              | Vehicle Id: ");
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
          trans.setVehicleId(id);
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
      System.out.println("Please enter a valid Vehicle Id: ");
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

  // Ask user for buyer and seller id
  // If buyer and/or user does not exist, go create one and come back
  while(true) {
    System.out.print("(15 characters)              | Seller Id: ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    // TODO: Check if seller is owner
    try {
      // Make sure input is valid
      h.checkValidity(i, 15, "String", false);
      // Make sure person exists
      query = "Select sin from people";
      rs = Login.stmt.executeQuery(query);
      String id = new String();
      while(rs.next()) {
        id = rs.getString("sin").trim().toLowerCase();
	// If a match is found, set seller ID and break out of loop
        if(id.equals(i.toLowerCase())) {
          query = "Select owner_id from owner where LOWER(vehicle_id) = '" + trans.getVehicleId().toLowerCase() + "'";
          rs = Login.stmt.executeQuery(query);
          String own = new String();
          while(rs.next()) {
            own = rs.getString("owner_id").toLowerCase().trim();
            // If a match is found, set seller ID and break out of loop.
            if(own.equals(id.toLowerCase())) {
              trans.setSellerId(id);
              break;
            }
          }
          if(!own.equals(i.toLowerCase())) {
            throw new NotOwnerException();
          }
          break;
        }
      }
      if(!id.equals(i.toLowerCase())) {
        throw new DNEException();
      }
      
      
      break;
      // if the id is not i, then return that it does not exist
    } catch(CantBeNullException e) {
      System.out.println("Please enter a valid Vehicle Id: ");
    } catch(TooLongException e) {
      System.out.println("Please enter a shorter Vehicle Id: ");
    } catch(NumberFormatException e) {
      System.out.println("Please enter a valid Vehicle Id: ");
    } catch(SQLException e) {
      System.out.println("SQL Fail. Try again.");
    } catch(DNEException e) {
      System.out.println("Person does not exist. Try again(T), Register a person(R) or return to main Menu(other)?: ");
      // If the seller does not exist, then either try again
      // Or register a new person or Quit to mainMenu.
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
    } catch(NotOwnerException e) {
      System.out.println("Person is not the owner. Try again(T), or return to main Menu(other)?: ");
      // If the seller does not exist, then either try again
      // Or register a new person or Quit to mainMenu.
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

  while(true) {
    System.out.print("(15 characters)              | Buyer Id: ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    // TODO: Check if person exists, offer to make a person or try again
    try {
      // Make sure input is valid
      h.checkValidity(i, 15, "String", false);
      // Make sure person exists
      query = "Select sin from people";
      rs = Login.stmt.executeQuery(query);
      String id = new String();
      while(rs.next()) {
        id = rs.getString("sin").trim().toLowerCase();
        // If a match is found, set buyer id and break out of loopo
        if(id.equals(i.toLowerCase())) {
            /**
               BEGIN KG EDIT
               ORIGINAL--->
               trans.setBuyerId(id);
               break;
               <---
            **/
            // Buyer cannot be the seller
            String j = trans.getSellerId();
            if(!i.toLowerCase().equals(j.toLowerCase())){
                trans.setBuyerId(id);
                break;
            }else{
                throw new BuyerIsSellerException();
            }                     
            /**
               END KG EDIT

               Note: 
            **/
        }
      }
      if(!id.equals(i.toLowerCase())) {
        throw new DNEException();
      }
      break;
      // if the id is not i, then return that it does not exist
    } catch(CantBeNullException e) {
      System.out.println("Please enter a valid Vehicle Id: ");
    } catch(TooLongException e) {
      System.out.println("Please enter a shorter Vehicle Id: ");
    } catch(NumberFormatException e) {
      System.out.println("Please enter a valid Vehicle Id: ");
    } catch(SQLException e) {
      System.out.println("SQL Fail. Try again.");
    } catch(DNEException e) {
      System.out.println("Person does not exist. Try again(T), Register a person(R), or return to Main Menu(other)?");
      // If the buyer does not exist, then either try again
      // or register a new person or quite to mainMenu.
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
      /**
         BEGIN KG EDIT
         ORIGINA---><---
      **/
    } catch(BuyerIsSellerException e) {
        System.out.println("Buyer cannot be seller. Try again(T) or return to Main Menu(other)?");
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
    /** 
        END KG EDIT
    **/    
  }
  while(true) {
    System.out.print("(Integer)                    | Transaction #: ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try {
      // Make sure input is valid
      h.checkValidity(i, 100, "Integer", true);
      m = Integer.parseInt(i);
      // Make sure transaction does not exist
      query = "Select transaction_id from auto_sale";
      rs = Login.stmt.executeQuery(query);
      int id;
      while(rs.next()) {
        id = rs.getInt("transaction_id");
        // If a match is found, throw exception
        if(id == m) {
          throw new DNEException();
        }
      }
      // If we make it here, we can set it.
      trans.setTransId(m);
      break;
    } catch(CantBeNullException e) {
      System.out.println("Please enter a valid Transaction #: ");
    } catch(TooLongException e) {
      System.out.println("Please enter a shorter Transaction #: ");
    } catch (NumberFormatException e) {
      System.out.println("Please enter a number: ");
    } catch (SQLException e) {
      System.out.println("SQL Fail. Try again.");
    } catch(DNEException e) {
      System.out.println("Transaction # already exists. Try again(T) or return to Main Menu(other)?");
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

  while(true) {
    // Ask user for the price of the vehicle
    System.out.print("(Number(9,2))                | Price of sale: ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkDecimal(i, 9, 2, true);
      if (i!=null) {
        n = Float.parseFloat(i);
        trans.setPrice(n);
      } else {
        float zero = Float.parseFloat("0.00");
        trans.setPrice(zero);
      }
      break;
    } catch(CantBeNullException e) {
      System.out.println("Entry cannot be null!");
    } catch(TooLongException e) {
      System.out.println("Entry too long!");
    } catch(NumberFormatException e) {
      System.out.println("Entry in the wrong format!");
    }
  }
  
  // Confirm Auto Transaction
  System.out.print("Make changes(Y/N) or return to Main Menu(other)?:\n");
  // Automatically set transaction date to the current date
  java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
  trans.setSDate(today);
  trans.printTransaction();
  // take user's answer:
  String input = scanner.nextLine();

  if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")){
    switch(input.toLowerCase()){
    case "y":
      finTrans(trans);
      break;
      
    case "n":
      autoTransMenu();
      break;
    }    
  } else {
    System.out.println("Goodbye!");
  }

  // Update database with information given
  // Tables required: owner, auto_sale
  // owner( owner_id, vehicle_id, is_primary_owner )
  // - char(15), char(15), char(1) (y/n)
  // auto_sale( transaction_id, seller_id, buyer_id, vehicle_id, s_date, price )
  // - int, char(15), char(15), char(15), date, numberic(9,2)
}
public void finTrans(TransactionObj trans) {
  System.out.println("adding...");

  // Create SQL insert statement to record auto_sale
  String createString = "insert into auto_sale values (" + trans.getTransId() + "" +
    ", '" + trans.getSellerId() + "'" +
    ", '" + trans.getBuyerId() + "'" +
    ", '" + trans.getVehicleId() + "'" +
    ", TO_DATE('" + trans.getSDate() + "', 'yyyy/mm/dd')" +
    ", " + trans.getPrice() + ")";

  String query = "select owner_id" +
    " from owner" +
    " where LOWER(vehicle_id) = LOWER('" + trans.getVehicleId() + "') " +
    "and LOWER(owner_id) = LOWER('" + trans.getSellerId() + "')";

  try {
    // Create auto_sale
    Login.stmt.executeUpdate(createString);
    // Update owner
    ResultSet rs = Login.stmt.executeQuery(query);
    String u = trans.getBuyerId();
    
    rs.first();
    rs.updateString("owner_id",u);
    rs.updateRow();
    
    System.out.println("Transaction Complete! Goodbye!");
  } catch(SQLException e) {
    System.err.println("SQL Fail: " + e.getMessage());
  }
}
}
