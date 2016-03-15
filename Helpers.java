import java.util.*;
import java.sql.*;
import java.io.*;

public class Helpers{
  final String STRING_TYPE = "String";
  final String NUM_TYPE = "Integer";
  final String DATE_TYPE = "Date";

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

//checks the date's validity. 
public void checkDate(String input, int length, String type) throws TooLongException, NumberFormatException, DateIsNullException, DateFormatException{

  //can the input be null
  if(input == null){
    if(type == DATE_TYPE){
      //this exception will be used just to set a flag
      //catch it in your own code and set a sql.date object as null, if
      //you are using that object type
      throw new DateIsNullException();
    }
  }

  
  if(input!=null){
    //it's a string type of some sort, it's ok if it has numbers mixed in
    if(input.length() > length)
      throw new TooLongException();
    
    if(type == DATE_TYPE){
      //make sure the date can be valid
      //expected form: yyyy-mm-dd
      if(input.contains("-")){
        String []parts = input.split("-");
        
        if(parts.length != 3) throw new DateFormatException();

        //check that we got numbers - throws an exception if not a number
        int count=0;
        while(count < 3){
          Integer.parseInt(parts[count]);
          count++;
        }

        //got here, know we have numbers - but are they valid numbers?
        //just letting people do whatever with the year, go wild
        if(parts[0].length() != 4) throw new DateFormatException();
        if(parts[1].length() !=2 || Integer.parseInt(parts[1]) > 12) throw new DateFormatException();
        if(parts[2].length() !=2 || Integer.parseInt(parts[2]) > 24) throw new DateFormatException();
        
      }else{
        throw new DateFormatException();
      }

    }
  }
}

  //check if the photo exists by opening the file; it's fine if it is null
  public boolean checkPhoto(String f){

    if(f==null)return true;
    
    File file = new File(f);
    if(file.exists())
      return true;
    else return false;
  }




  
  //checks if a user-entered foreign key (assuming primary) is valid
  //checks only one column at a time
  //isNum is for whether or not the value to check is a number (no quotations req'd) or a string(need '') 
  public void checkFK(String enteredValue, String fTable, String col, boolean isNum, boolean canBeNull)throws FKException{

    if(enteredValue==null && canBeNull)
      return;
    
    //performs a small query to check if the foreign key is in the other table
    String query = "select " + col + " from " + fTable + " where UPPER(" + col + ")=";
    boolean anything=true;
    
    if(isNum) query += enteredValue;
    else query += "'" + enteredValue.toUpperCase() + "'"; 
    
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

//checks the primary key for two attributes that are primary keys if they are already a combination
//within the table. Needed for only Owner and Restriction as far as I can tell?
//assumes that you would not send it a null (since they're PKs)
//**isSame: this will be set true for owner but false for restriction because restriction's PKs
  //are not the same type! Not worrying about the other combos of quotation marks bc we don't ever have them
  public boolean checkTwoPK(String col1, String col2, String entry1, String entry2, String table, boolean isSame){

    boolean check=true;
    String query;

    if(isSame){
    query = "select " + col1 + ", " + col2  + " from " + table +
      " where UPPER(" + col1 + ")= '" + entry1.toUpperCase() + "' and UPPER(" + col2 + ")= '" + entry2.toUpperCase() + "'";  

      }else{
      //for restriction
      query = "select " + col1 + ", " + col2  + " from " + table +
        " where UPPER(" + col1 + ")= '" + entry1.toUpperCase() + "' and UPPER(" + col2 + ")=  " + entry2.toUpperCase();
      
    }

    try{

      ResultSet rs = Login.stmt.executeQuery(query);

      //check if returned anything or not

      check = rs.next();

    }catch(SQLException ex) {
      System.err.println("SQLException: " +
                         ex.getMessage());
    }

    if(check)return true;
    else return false;
  }
}

  
