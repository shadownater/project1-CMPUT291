import java.util.*;
import java.sql.*;
import java.io.*;

public class DriverObj{
    String name;
    String licenceNo;
    String addr;
    String birthday;
    String drivingClass;
    List<String> drivingConditions = new ArrayList<String>();
    String expiryDate;

    // Setters
    public void setName(String input){
        name = input;
    }

    public void setLicenceNo(String input){
        licenceNo = input;
    }

    public void setAddr(String input){
        addr = input;
    }

    public void setBirthday(String input){
        birthday = input;
    }

    public void setDrivingClass(String input){
        drivingClass = input;
    }

    public void addDrivingCondition(String input){
        // Note: multivalue attribute
        drivingConditions.add(input);
    }

    public void setExpiryDate(String input){
        expiryDate = input;
    }

    // Getters
    public String getName(){
        return name;
    }

    public String getLicenceNo(){
        return licenceNo;
    }

    public String getAddr(){
        return addr;
    }

    public String getBirthday(){
        return birthday;
    }

    public String getDrivingClass(){
        return drivingClass;
    }

    public String getDrivingConditions(){
        // Note: multivalue attribute
        String s = new String();
        for(String cond: drivingConditions){
            s = s + cond + ", ";
        }
        return s;
    }

    public String getExpiryDate(){
        return expiryDate;
    }

    // Print 
    public void printAll(){
        System.out.println("" + name + " " + licenceNo + " " + addr +
                           " " + birthday + " " + drivingClass + " " +
                           getDrivingConditions() + " " + expiryDate);
    }
}
