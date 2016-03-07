import java.util.*;
import java.sql.*;
import java.io.*;

public class VehicleObj{
  String serial_no;
  String maker;
  String model;
  String year; //storing as string for now
  String colour;
  String type_id; //storing as string for now

  public void setSerialNo(String input){
    serial_no = input;
  }

  public void setMaker(String input){
    maker = input;
  }

  public void setModel(String input){
    model = input;
  }

  public void setYear(String input){
    year = input;
  }

  public void setColour(String input){
    colour = input;
  }

  public void setType_Id(String input){
    type_id = input;
  }



public String getSerialNo(){
    return serial_no;
  }

  public String getMaker(){
    return maker;
  }

  public String getModel(){
    return model;
  }

  public String getYear(){
    return year;
  }

  public String getColour(){
    return colour;
  }

  public String getType_Id(){
    return type_id;
  }



  public void printAll(){
    System.out.println("" + serial_no + " " + maker + " " + model +
                       " " + year + " " + colour + " " + type_id);
  }
}
