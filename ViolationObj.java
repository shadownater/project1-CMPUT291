import java.util.*;
import java.sql.*;
import java.io.*;

public class ViolationObj{
    String ticketNo;
    String violatorNo;
    String vehicleID;
    String officeNo;
    String vtype;
    String place;
    String descriptions;

    public void setTicketNo(String input){
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

    public String getTicketNo(){
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

    public void printAll(){
        System.out.println("" + ticketNo + " " + violatorNo +
                           " " + vehicleID + " " + officeNo +
                           " " + vtype + " " + place +
                           " " + descriptions);
    }
}
