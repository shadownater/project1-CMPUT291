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
    File photo;
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

    public void setPhoto(String input){
      if(input==null) photo=null;
      else photo = new File(input);
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

    public File getPhoto(){
      return photo;
    }

    public java.sql.Date getIssueDate(){
      return issueDate;
    }

    // Simple print function
    public void printAll(){
        System.out.println("" + name + " " + licenceNo + " " + addr +
                           " " + birthday + " " + drivingClass + " " +
                           getDrivingConditions() + " " + expiryDate);
    }

    // Prints all for the driver's licence registration part
    public void printAllReg(){
      if(photo!=null){
      System.out.println("" + licenceNo + " " + sin + " " + drivingClass + " " +
                         "photo file: '" + photo + "' " + issueDate + " " +
                         expiryDate);
      }else
        System.out.println("" + licenceNo + " " + sin + " " + drivingClass + " " +
                         "photo file: " + photo + " " + issueDate + " " +
                         expiryDate);
      }
    // Print function: try to emulate a real licence to a degree
    // 
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

  public String createInsertStatement(){
    String statement = "insert into drive_licence values ('"+ licenceNo + "'";

    //don't be like me and write nulls as strings :D
    if(sin !=null)statement += ", '" + sin + "'";
    else statement += ", NULL";

    if(drivingClass !=null)statement += ", '" + drivingClass + "'";
    else statement += ", NULL";

    //NOT LIKE THIS
    if(photo !=null)statement += ", ?";
    else statement += ", NULL";

    if(issueDate!=null)statement += ", TO_DATE('"  + issueDate + "', 'yyyy/mm/dd')";
    else statement += ", NULL";

    if(expiryDate !=null)statement += ", TO_DATE('" + expiryDate + "', 'yyyy/mm/dd')";
    else statement += ", NULL";

    statement += ")";

    return statement;
    
  }

  
}
