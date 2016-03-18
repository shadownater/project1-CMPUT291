import java.util.*;
import java.sql.*;
import java.io.*;

public class ConditionObj{
  ArrayList<String> c_id = new ArrayList<String>();
  ArrayList<String> description = new ArrayList<String>();


  //setters
 public void addCid(String input){
   c_id.add(input);
 }

 public void addDescription(String input){
   description.add(input);
 }

  //getters
  public ArrayList<String> getCid(){
    return c_id;
  }

  public ArrayList<String> getDescription(){
    return description;
  }
}
