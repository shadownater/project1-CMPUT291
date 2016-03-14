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
    String sin;
    String photo;
    java.sql.Date issueDate;
  
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

    public void setSin(String input){
      sin = input;
    }

   //not sure how to do this yet. Placeholder
    public void setPhoto(String input){
      photo = input;
    }

    public void setIssueDate(java.sql.Date input){
      issueDate = input;
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

    public String getSin(){
      return sin;
    }

    public String getPhoto(){
      return photo;
    }

    public java.sql.Date getIssueDate(){
      return issueDate;
    }

    // Print 
    public void printAll(){
        System.out.println("" + name + " " + licenceNo + " " + addr +
                           " " + birthday + " " + drivingClass + " " +
                           getDrivingConditions() + " " + expiryDate);
    }

    // Prints all for the driver's licence registration part
    public void printAllReg(){
      System.out.println("" + licenceNo + " " + sin + " " + drivingClass + " " +
                         "photo file: " + photo + " " + issueDate + " " +
                         expiryDate);
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
