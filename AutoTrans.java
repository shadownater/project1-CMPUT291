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
//  mainMenu menu;
                      
public AutoTrans() {
  scanner = new Scanner(System.in);
  h = new Helpers();
  trans = new TransactionObj();
//  menu = new mainMenu();
}

public void autoTransMenu() {
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
    System.out.println("Invalid input!");
  }
}

public void addTransaction() {

  // Initialize use variables
  float n;
  String i;
  String s;
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
        id = rs.getString("serial_no").trim();
	// If a match is found, set vehicle ID and break out of loop
        if(id.equals(i)) {
          trans.setVehicleId(i);
	  break;
        }
      }
      // If the id is not i, then return that it does not exist
      if(!id.equals(i)) {
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
      System.out.println("Vehicle does not exist. Try again(T) or return to Main Menu(other)?: ");
      // If the vehicle does not exist, then either try again
      // Or go back to main menu by not typing y/Y.
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
        id = rs.getString("sin").trim();
	// If a match is found, set seller ID and break out of loop
        if(id.equals(i)) {
          trans.setSellerId(i);
          break;
        }
      }
      if(!id.equals(i)) {
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
	  // register person
	  break;
        }
      } else {
        System.out.println("Goodbye!");
        return;
      }
    }
  }

  //trans.setSellerId(i);

  while(true) {
    System.out.print("(15 characters)              | Buyer Id: ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    // TODO: Check if person exists, offer to make a person or try again
    try {
      h.checkValidity(i, 15, "String", false);
      break;
    } catch(CantBeNullException e) {
      System.out.println("Please enter a valid Vehicle Id: ");
    } catch(TooLongException e) {
      System.out.println("Please enter a shorter Vehicle Id: ");
    } catch(NumberFormatException e) {
      System.out.println("Please enter a valid Vehicle Id: ");
    }
  }
  trans.setBuyerId(i);

  // TODO: Check validity for this
  // Ask user for the price of the vehicle
  System.out.print("() | Price of sale: Dollars: ");
  n = Float.parseFloat(scanner.nextLine());
  //System.out.print("() | Price of sale: Cents: ");
  trans.setPrice(n);

  // Confirm Auto Transaction
  System.out.print("Are you sure you want to make these changes? Y/N:\n");
  // TODO: Set a transaction id
  java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
  trans.setSDate(today);
  trans.printTransaction();

  String input = scanner.nextLine();

  if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")){
    switch(input.toLowerCase()){
    case "y":
      trans.finTrans();
      break;
      
    case "n":
      autoTransMenu();
      break;
    }
    
  } else {
    System.out.println("Invalid input!");
  }

  // Update database with information given
  // Tables required: owner, auto_sale
  // owner( owner_id, vehicle_id, is_primary_owner )
  // - char(15), char(15), char(1) (y/n)
  // auto_sale( transaction_id, seller_id, buyer_id, vehicle_id, s_date, price )
  // - int, char(15), char(15), char(15), date, numberic(9,2)
}
}







