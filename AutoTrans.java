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
  System.out.print("You will need the id (the SIN number) of the seller and the buyer,\n" +
                   "the vehicle serial number, and the price.\n");

  // ask for all input
  // check if vehicle is in system or not
  // if not, complete VehicleReg
  // else, update vehicle transaction info
  // tables required: owner, auto_sale

  // owner( owner_id, vehicle_id, is_primary_owner )
  // - char(15), char(15), char(1) (y/n)
  // auto_sale( transaction_id, seller_id, buyer_id, vehicle_id, s_date, price )
  // - int, char(15), char(15), char(15), date, numberic(9,2)

  // ask for input:
  System.out.print("Please fill out the following details in the alotted amount of space:\n");

  String prev_owner; // seller_id
  String new_owner;  // buyer_id
  String auto_sold;  // vehicle_id
  Integer cost;     // price

  System.out.print("Vehicle Id: ");
  auto_sold = scanner.nextLine();

  System.out.print("Seller Id: ");
  prev_owner = scanner.nextLine();
  
  System.out.print("Buyer Id: ");
  new_owner = scanner.nextLine();

  System.out.print("Price of sale: ");
  cost = Integer.parseInt(scanner.nextLine());


  System.out.print("Transaction Complete\n");
}
}







