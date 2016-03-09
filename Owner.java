import java.util.*;
import java.sql.*;
import java.io.*;

public class Owner{
  String owner_id;
  String vehicle_id;
  String is_primary_owner;

public void setOwnerId(String oId){
  owner_id = oId;
}

public void setVehicleId(String vId){
  vehicle_id = vId;
}

public void setIsPO(String p){
  is_primary_owner = p;
}


public String getOwnerId(){
    return owner_id;
}

public String getVehicleId(){
  return vehicle_id;
}

public String getIsPO(){
  return is_primary_owner;
}

public void printAll(){
  System.out.println("" + owner_id + " " + vehicle_id + " " + is_primary_owner);
}

//creates the insert statement, while minding null values
//literally none of this one can be null so nothing to worry about!
public String createInsertStatement(){
  String statement = "insert into owner values ('"+ owner_id + "', '" + vehicle_id +
    "', '" + is_primary_owner + "')";
  
  return statement;
}
  
}
