import java.util.*;
import java.sql.*;
import java.io.*;

public class VehicleHistoryObj{
    String vin;
    String sales;
    float avgSalePrice;
    String violations;

    public void setVin(String input){
        vin = input;
    }

    public void setSales(String input){
        sales = input;
    }
    
    public void setAvgSalePrice(float input){
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

    public float getAvgSalePrice(){
        return avgSalePrice;
    }

    public String getViolations(){
        return violations;
    }

    public void printAll(){
        System.out.println("" + vin + " " + sales +
                           " " + avgSalePrice + " " + violations);
    }

    public void printRecord(){
        System.out.print("+-----------------------------------------------------+\n" +
                         "|  Number of Sales: " + sales + "\n" +
                         "|  Average Sale Price: ");
        System.out.format("%.2f%n",avgSalePrice);
        System.out.println("|  Number of Violations: " + violations + "\n" +
                           "+-----------------------------------------------------+");
    }
}
