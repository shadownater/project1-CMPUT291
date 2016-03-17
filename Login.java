import java.util.*;
import java.sql.*;
import java.io.*;

//this is acting as a global class!
//use like this: Login.VARIABLE, no need to call setters and getters as its a global variable c:

//stores the user's login information so we don't have to keep fetching it
public class Login{
  public static String m_userName;
  public static String m_password;
  static Statement stmt;
  static PreparedStatement pstmt;
  
  // The URL we are connecting to
  public static String m_url = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
  
  // The driver to use for connection
  public static String m_driverName = "oracle.jdbc.driver.OracleDriver";

  
}

