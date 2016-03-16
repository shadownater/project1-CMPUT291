import java.util.*;
import java.sql.*;
import java.io.*;

public class ViolationObj{
    Integer ticketNo;
    String violatorNo;
    String vehicleID;
    String officeNo;
    String vtype;
    String place;
    String descriptions;
    java.sql.Date vDate;

    public void setTicketNo(Integer input){
        ticketNo = input;
    }

    public void setViolatorNo(String input){
        violatorNo = input;
    }
    
    public void setVehicleID(String input){
        vehicleID = input;
    }
    
    public void setOfficeNo(String input){
        officeNo = input;
    }
    
    public void setVtype(String input){
        vtype = input;
    }
    
    public void setPlace(String input){
        place = input;
    }
    
    public void setDescriptions(String input){
        descriptions = input;
    }

    public void setVDate(java.sql.Date date){
	vDate = date;
    }

    public Integer getTicketNo(){
        return ticketNo;
    }

    public String getViolatorNo(){
        return violatorNo;
    }

    public String getVehicleID(){
        return vehicleID;
    }

    public String getOfficeNo(){
        return officeNo;
    }

    public String getVtype(){
        return vtype;
    }

    public String getPlace(){
        return place;
    }

    public String getDescriptions(){
        return descriptions;
    }

    public java.sql.Date getVDate() {
	return vDate;
    }

    public void printAll(){
        System.out.println("Ticket#: " + ticketNo + "\nViolator: " + violatorNo +
                           "\nVehicle Id: " + vehicleID + "\nOfficer Id:  " + officeNo +
                           "\nViolation: " + vtype + "\nLocation: " + place +
                           "\nComments: " + descriptions);
    }

    public void finRecord() {
	System.out.println("Record Complete!");
    }

    public void printRecord(){
        System.out.println("+-----------------------------------------------------+\n" +
                           "|  Ticket No. " + ticketNo + "\n" +
                           "|  Date Issued: " + vDate + "\n" +
                           "|  Vehicle Id. " + vehicleID + "\n" +
                           "|  Officer Id. " + officeNo + "\n" +
                           "|  Violation: " + vtype + "\n" +
                           "|  Location: " + place + "\n" +
                           "|  Comments: " + descriptions + "\n" +
                           "+-----------------------------------------------------+");
    }
}
