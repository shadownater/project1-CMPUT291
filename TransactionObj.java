import java.util.*;
import java.sql.*;
import java.io.*;

public class TransactionObj {
    Integer transId;
    String sellerId;
    String buyerId;
    String vehicleId;
    java.sql.Date sDate;
    Float price;


    public void printTransaction () {
	System.out.print("Transaction: " + transId + "\nVehicle: " + vehicleId +
			"\nSeller: " + sellerId + "\nBuyer: " + buyerId + 
			"\nPrice: " + price + "\nDate: " + sDate + "\n");
    }

    public void setTransId (Integer id) {
	transId = id;
    }

    public Integer getTransId () {
	return transId;
    }

    public void setSellerId (String string) {
	sellerId = string;
    }

    public String getSellerId () {
	return sellerId;
    }

    public void setBuyerId (String string) {
	buyerId = string;
    }

    public String getBuyerId () {
	return buyerId;
    }

    public void setVehicleId (String string) {
	vehicleId = string;
    }

    public String getVehicleId () {
	return vehicleId;
    }

    public void setSDate (java.sql.Date date) {
	sDate = date;
    }

    public java.sql.Date getSDate () {
	return sDate;
    }

    public void setPrice (Float cost) {
	price = cost;
    }

    public Float getPrice () {
 	return price;
    }

    public void finTrans () {
	System.out.print("Transaction Complete!\n");
    }
}
