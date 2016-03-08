import java.util.*;
import java.sql.*;
import java.io.*;

public class Helpers{
  final String STRING_TYPE = "String";
  final String NUM_TYPE = "Integer";
  

//checks if the given input is the right length, type, and if it can be left null
public void checkValidity(String input, int length, String type, boolean canBeNull) throws CantBeNullException, TooLongException, NumberFormatException{

    //can the input be null
    if(input == null){
      if(!canBeNull){
        throw new CantBeNullException();
      }
    }

    //input is allowed to be potentially null, yay! checking type - matches that we have a number/string?
    if(input !=null){
    if(type == NUM_TYPE)
      //will throw NumberFormatException if not a number - caller will catch
      Integer.parseInt(input);
    }
    
    if(input!=null){
    //it's a string type of some sort, it's ok if it has numbers mixed in
    if(input.length() > length)
      throw new TooLongException();
    }
    
  } //end of checkValidity


  //checks if a user-entered foreign key (assuming primary) is valid
  //checks only one column at a time
  //isNum is for whether or not the value to check is a number (no quotations req'd) or a string(need '') 
  public void checkFK(String enteredValue, String fTable, String col, boolean isNum, boolean canBeNull)throws FKException{

    if(enteredValue==null && canBeNull)
      return;
    
    //performs a small query to check if the foreign key is in the other table
    String query = "select " + col + " from " + fTable + " where " + col + "=";
    boolean anything=true;
    
    if(isNum) query += enteredValue;
    else query += "'" + enteredValue + "'";
    
    try{
      ResultSet rs = Login.stmt.executeQuery(query);

      //check if returned anything or not

      anything = rs.next();

       }catch(SQLException ex) {
      System.err.println("SQLException: " +
                       ex.getMessage());
      }
    if(!anything) throw new FKException();
    

    
  }
}
