import java.util.*;
import java.sql.*;
import java.io.*;

public class VehicleHistoryObj{
    String vin;
    String sales;
    String avgSalePrice;
    String violations;

    public void setVin(String input){
        vin = input;
    }

    public void setSales(String input){
        sales = input;
    }
    
    public void setAvgSalePrice(String input){
        avgSalePrice = input;
    }
    
    public void setViolations(String input){
        violations = input;
    }
    

    public String getVin(){
        return vin;
    }

    public String getSales(){
        return sales;
    }

    public String getAvgSalePrice(){
        return avgSalePrice;
    }

    public String getViolations(){
        return violations;
    }

    public void printAll(){
        System.out.println("" + vin + " " + sales +
                           " " + avgSalePrice + " " + violations);
    }
}
