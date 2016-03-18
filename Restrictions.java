import java.util.*;
import java.sql.*;
import java.io.*;
import java.util.ArrayList;
                
public class Restrictions{
  String licence_no;
  ArrayList<String> r_id = new ArrayList<String>();

  //setters
public void setLicenceNo(String input){
  licence_no = input;
}

public void addCondition(String cond){
  r_id.add(cond);
}

  //getters
public String getLicenceNo(){
  return licence_no;
}

public ArrayList<String> getConditions(){
  return r_id;
}
  
}
