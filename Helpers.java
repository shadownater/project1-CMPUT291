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

  

}
