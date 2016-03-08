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


public AutoTrans() {
  scanner = new Scanner(System.in);
  h = new Helpers();
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

  // Initialize values
  String prev_owner; // seller_id
  String new_owner;  // buyer_id
  String auto_sold;  // vehicle_id
  Integer cost;     // price

  // Initialize use variables
  int ID_LEN = 15;
  
  // Begin asking user for input:
  System.out.print("Please fill out the following details in the alotted amount of space:\n");

  // Ask user for auto_sold/vehicle_id
  // If vehicle does not exist (check) then tell user to
	// a) try again, enter correctly
	// b) go to VehicleReg and come back once the vehicle is registered
  System.out.print("(15 characters)              | Vehicle Id: ");
  auto_sold = scanner.nextLine().toLowerCase();

  // Ask user for buyer and seller id
  // If buyer and/or user does not exist, go create one and come back
  // Input is converted to lower case and allows no spacing
  System.out.print("(15 characters)              | Seller Id: ");
  prev_owner = scanner.nextLine().toLowerCase();

  System.out.print("(15 characters)              | Buyer Id: ");
  new_owner = scanner.nextLine().toLowerCase();

  // Ask user for the price of the vehicle
  System.out.print("(9 digits, 2 decimal places) | Price of sale: $");
  cost = Integer.parseInt(scanner.nextLine());

  // Confirm Auto Transaction
  System.out.print("Are you sure you want to make these changes? Y/N:\n" +
	" - Vehicle sold: " + auto_sold + "\n - Seller id: " + prev_owner +
	"\n - Buyer id: " + new_owner + "\n - Price of sale: " + cost + "\n");

  String input = scanner.nextLine();

  if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")){
    switch(input.toLowerCase()){
    case "y":
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

  System.out.print("\nTransaction Complete\n");
}
}







