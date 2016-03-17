import java.util.*;
import java.sql.*;
import java.io.*;


public class PeopleObj{
  String sin;
  String name;
  String height;
  String weight;
  String eyecolor;
  String haircolor;
  String address;
  String gender;
  java.sql.Date birthday;


  //setters                      
public void setSinNo(String input){
  sin = input;

}

public void setName(String input){
  name = input;
}

public void setHeight(String input){
  height = input;
}

public void setWeight(String input){
  weight = input;
}

public void setEyecolor(String input){
  eyecolor = input;
}

public void setHaircolor(String input){
  haircolor = input;
}

public void setAddress(String input){
  address = input;
}

public void setGender(String input){
  gender = input;
}

public void setBirthday(java.sql.Date input){
  birthday = input;
}

  //getters
public String getSinNo(){
  return sin;
}

public String getName(){
  return name;
}

public String getHeight(){
  return height;
}

public String getWeight(){
  return weight;
}

public String getEyecolor(){
  return eyecolor;
}

public String getHaircolor(){
    return haircolor;
}

public String getAddress(){
    return address;
}

public String getGender(){
    return gender;
}

public java.sql.Date getBirthday(){
  return birthday;
  }
  
// Print
public void printAll(){
  System.out.println("" + sin + " " + name + " " + height + " " + weight +
                     " " + eyecolor + " " + haircolor + " " + address + " " +
                     gender + " " + birthday);
}
  
//creates the insert statement, while minding null values
  public String createInsertStatement(){
    String statement = "insert into people values ('"+ sin + "'";

    //don't be like me and write nulls as strings :D
    if(name !=null)statement += ", '" + name + "'";
    else statement += ", NULL";
    
    if(height !=null)statement += ", " + height + "";
    else statement += ", NULL";
    
    if(weight !=null)statement += ", "  + weight;
    else statement += ", NULL";
    
    if(eyecolor !=null)statement += ", '"  + eyecolor + "'";
    else statement += ", NULL";
    
    if(haircolor !=null)statement += ", '" + haircolor + "'";
    else statement += ", NULL";

    if(address !=null)statement += ", '" + address + "'";
    else statement += ", NULL";

    if(gender !=null)statement += ", '" + gender + "'";
    else statement += ",NULL";

    if(birthday !=null)statement += ", TO_DATE('" + birthday + "', 'yyyy/mm/dd')";
    else statement += ",NULL";
    
    statement += ")";
    
    return statement;
  }
  
}
