import java.util.*;
import java.sql.*;
import java.io.*;

public class ConditionObj{
  List<String> c_id = new ArrayList<String>();
  List<String> description = new ArrayList<String>();


  //setters
 public void addCid(String input){
   c_id.add(input);
 }

 public void addDescription(String input){
   description.add(input);
 }

  //getters
  public List<String> getCid(){
    return c_id;
  }

  public List<String> getDescription(){
    return description;
  }
}
