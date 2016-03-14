import java.io.*;
import java.util.*;

public class IO{
    private static Scanner scanner = null;
    public static Scanner getScanner(){
        if(scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
    public static Scanner getScanner(String filename){
        if(scanner == null) {
            try{
                File file = new File(filename);
                scanner = new Scanner(new FileReader(file));
            }catch(Exception e){
                System.out.println(e);
            }
        }
        return scanner;
    }
    public static Scanner resetScanner(){
        scanner = new Scanner(System.in);
        return scanner;
    }
    public static Scanner resetScanner(String filename){
        try{
            File file = new File(filename);
            scanner = new Scanner(new FileReader(file));
        }catch(Exception e){
            System.out.println(e);
        }
        return scanner;
    }
}
