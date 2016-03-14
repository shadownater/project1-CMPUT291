import java.util.*;
import java.sql.*;
import java.io.*;

public class DriverObj{
    String name;
    String licenceNo;
    String addr;
    java.sql.Date birthday;
    String drivingClass;
    List<String> drivingConditions = new ArrayList<String>();
    java.sql.Date expiryDate;

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

    public void setBirthday(java.sql.Date input){
        birthday = input;
    }

    public void setDrivingClass(String input){
        drivingClass = input;
    }

    public void addDrivingCondition(String input){
        // Note: multivalue attribute
        drivingConditions.add(input);
    }

    public void setExpiryDate(java.sql.Date input){
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

    public java.sql.Date getBirthday(){
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

    public java.sql.Date getExpiryDate(){
        return expiryDate;
    }

    // Print 
    public void printAll(){
        System.out.println("" + name + " " + licenceNo + " " + addr +
                           " " + birthday + " " + drivingClass + " " +
                           getDrivingConditions() + " " + expiryDate);
    }
    // Print fancier: try to emulate a real licence to a degree
    public void printRecord(){
        System.out.println("+-----------------------------------------------------+\n" +
                           "|  No: " + licenceNo + "\n" +
                           "|  Class: " + drivingClass + "\n" +
                           "|  Cond: " + getDrivingConditions() + "\n" +
                           "|  Expires: " + expiryDate + "\n" +
                           "|\n" +
                           "|  " + name + "\n" +
                           "|  " + addr + "\n" +
                           "|  DOB: " + birthday + "\n" +
                           "+-----------------------------------------------------+");                           
    }
}
