import java.util.*;
import java.sql.*;
import java.io.*;


//Driver Licence Registration

//This component is used to record the information needed to issuing a drive licence,
//including the personal information and a picture for the driver.
//You may assume that all the image files are stored in a local disk system.


public class DriverReg{
  Scanner scanner;
  Helpers h;
  DriverObj dL;
  PeopleObj person;
  Restrictions res;
  ConditionObj cond;
  
  final String STRING_TYPE = "String";
  final String NUM_TYPE = "Integer";
  final String DATE_TYPE = "Date";

public DriverReg(){
  scanner = new Scanner(System.in);
  h = new Helpers();
  dL = new DriverObj();
  person = new PeopleObj();
  res = new Restrictions();
  cond = new ConditionObj();
}

//displays the menu for driver licence registration
public void driverRegMenu(){
  System.out.println("Would you like to register a new licence? Y/N\nOr would you like to register a person? P\nOR add a driving condition to a licence? C");


  String input = scanner.nextLine();

  if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N") || input.equalsIgnoreCase("P") || input.equalsIgnoreCase("C")){
    switch(input.toLowerCase()){
    case "y":
      addLicence();
      break;

    case "n":
      break;

    case "p":
      addPeople();
      break;

    case "c":
      drivingConditionsMenu();
    }

  }else{
    System.out.println("Invalid input!");
    driverRegMenu();
  }
}
  
//Displays the information for adding a licence and allows the user to input info.
public void addLicence(){

  //some new things in here: blob, varchar, date, and unique constraint!
  System.out.println("Please enter the licence information, ONE LINE AT A TIME, as such: \n" +
                     "Licence number(15), sin(15), class(10), photo, issuing date (yyyy-mm-dd)," +
                     "expiring date(yyyy-mm-dd).\n" +
                     "Please note: the licence number cannot be blank.\n");

    String i;

    while(true){
      System.out.print("Licence Number(15): ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;

      try{
        h.checkValidity(i, 15, STRING_TYPE, false);
        //check if that primary key is available or not
        break;
      }catch(CantBeNullException e){
        System.out.println("Entry cannot be null!");
      }catch(TooLongException e){
        System.out.println("Entry too long!");
      }catch(NumberFormatException e){
        System.out.println("Entry in the wrong format!");
      }

    }

    dL.setLicenceNo(i);
    String found = null;
    
    while(true){
      System.out.print("Sin(15): ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      try{
        h.checkValidity(i, 15, STRING_TYPE, true);
        found = h.checkFK(i, "people", "sin", false, true);
        break;
      }catch(CantBeNullException e){
        System.out.println("Entry cannot be null!");
      }catch(TooLongException e){
        System.out.println("Entry too long!");
      }catch(NumberFormatException e){
        System.out.println("Entry in the wrong format!");
      }catch(FKException e){
        System.out.println("Person is not in the table. Add a person instead? Y/N\n" +
                           "This will begin the add person program instead.");

        String response = scanner.nextLine();
  
        if(response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("N")){
          switch(response.toLowerCase()){
          case "y":
            addPeople();
            return; //TEST ME
            //break;
          }
        }

        
      }
    }

    if(found==null) dL.setSin(i);
    else dL.setSin(found);
    
    while(true){
      System.out.print("Class(10): ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      try{
        h.checkValidity(i, 10, STRING_TYPE, true);
        break;
      }catch(CantBeNullException e){
        System.out.println("Entry cannot be null!");
      }catch(TooLongException e){
        System.out.println("Entry too long!");
      }catch(NumberFormatException e){
        System.out.println("Entry in the wrong format!");
      }
    }

    dL.setDrivingClass(i);
    
    
    while(true){
      System.out.print("Photo: ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      
        boolean isValidPhoto= h.checkPhoto(i); 
        if(isValidPhoto)
          break;
        else System.out.println("Photo is not valid. Please try again.");
    }

    dL.setPhoto(i); 

    java.sql.Date myDate = null;
    boolean dateFlag=false;
    
    while(true){
      System.out.print("Issue Date(yyyy-mm-dd): ");
      i = scanner.nextLine();
      if(i.isEmpty()) i = null; 
      try{
        h.checkDate(i, 10, DATE_TYPE); 
        break;
      }catch(DateFormatException e){
        System.out.println("Entry in the wrong format. Expected yyyy-mm-dd!");
      }catch(TooLongException e){
        System.out.println("Entry too long!");
      }catch(NumberFormatException e){
        System.out.println("Wrong format!");
      }catch(DateIsNullException e){
        dateFlag = true;
        break;
      }

    }

    if(dateFlag) dL.setIssueDate(myDate);
    else dL.setIssueDate(java.sql.Date.valueOf(i));

    dateFlag=false;
    
    while(true){
      System.out.print("Expiration Date(yyyy-mm-dd): ");
      i = scanner.nextLine();
      if(i.isEmpty()) i=null;
      try{
        h.checkDate(i, 10, DATE_TYPE);
        break;
      }catch(DateFormatException e){
        System.out.println("Entry in the wrong format. Expected yyyy-mm-dd!");
      }catch(TooLongException e){
        System.out.println("Entry too long!");
      }catch(NumberFormatException e){
        System.out.println("No letters in the date please!");
      }catch(DateIsNullException e){
        dateFlag = true;
        break;
      }
      

    }
    
    if(dateFlag) dL.setExpiryDate(myDate);
    else dL.setExpiryDate(java.sql.Date.valueOf(i));

    System.out.println("Data entered: ");
    dL.printAllReg();
    System.out.println("\nWas there a mistake? Y/N or Q to quit (will not upload to database.)");
    
    confirmEntries(dL);
    
}//end of addLicence

//does the orderly stuff for the driver's licence registration **same title as the one for person, person has its own
public void confirmEntries(DriverObj drive){

    String isOk = scanner.nextLine();
  
  if(isOk.equalsIgnoreCase("Y") || isOk.equalsIgnoreCase("N") || isOk.equalsIgnoreCase("Q")){
    switch(isOk.toLowerCase()){
    case "y":
      addLicence();
      break;
      
    case "n":
      //**upload data to database here!
      
      if(!checkDriver(drive)){
        commitDriver(drive);
        drivingConditionsMenu();
      }
         else System.out.println("Driver's licence already exists in the database. Please try again."); 
      break;  
    case "q":
      break; 
    }
    
  }else{
    System.out.println("Invalid input! Please try again.");
    confirmEntries(drive);
  }
  }


//does the orderly stuff for the driver's licence registration **same title as 
//the one for drive_licence, it has its own
public void confirmEntries(PeopleObj peep){

    String isOk = scanner.nextLine();

    if(isOk.equalsIgnoreCase("Y") || isOk.equalsIgnoreCase("N") || isOk.equalsIgnoreCase("Q")){
      switch(isOk.toLowerCase()){
      case "y":
        addPeople();
        break;

      case "n":
        //**upload data to database here!

        if(!checkPerson(peep)){
          commitPerson(peep); 
        }
        else System.out.println("Person already exists in the database. Please try again.");
        break;
      case "q":
        break;
      }

    }else{
      System.out.println("Invalid input! Please try again.");
      confirmEntries(peep);
    }
  }
  
  
  //checks if the primary and unique keys are already in the database for the driver's licence
//true = the iput is already in the database  
//false = the input is not already in the databasse
public boolean checkDriver(DriverObj d){
  //need to check primary key and unique key together
  //it's NOT ok if there are multiple sins (but can be null)
  //it's NOT ok if there are multiple licence_no's 

  boolean duh = true;
  String query1, query2;

  query1 = "select licence_no from Drive_licence" +
    " where UPPER(licence_no)='" + d.getLicenceNo().toUpperCase() + "'";

  try{

    ResultSet rs = Login.stmt.executeQuery(query1);

    //check if returned anything or not

    duh = rs.next();


  }catch(SQLException ex) {
    System.err.println("SQLException: " +
                       ex.getMessage());
  }
  
  if(duh) return true;
  
  if(d.getSin() !=null){
    
    query2 = "select sin from Drive_licence" +
      " where UPPER(sin)='" + d.getSin().toUpperCase() + "'";
  }else{
    //get in here if sin is listed as null
    query2 = "select licence_no, sin from Drive_licence" +
      " where UPPER(licence_no) ='" + d.getLicenceNo().toUpperCase() +
      "' and sin is null";
  }
  
  
  try{
    
    ResultSet rs = Login.stmt.executeQuery(query2);
    
    //check if returned anything or not
    
    duh = rs.next();
    
    
  }catch(SQLException ex) {
    System.err.println("SQLException: " +
                       ex.getMessage());
  }
  
  if(duh)return true;
  else return false;
  
  
}//end of checkDriver

//function used to ask the user for info to add people to the people table
public void addPeople(){
  System.out.println("Please enter the person's information, ONE LINE AT A TIME, as such: \n" + "Sin number(15), name(40), height(5,2), weight(5,2), eyecolor(10),\n" + "hair color(10), address(50), gender(m/f), birthday(yyyy-mm-dd).\n" + "Please note: the sin number cannot be blank.\n");

  String i;

  while(true){
    System.out.print("Sin number(15): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;

    try{
      h.checkValidity(i, 15, STRING_TYPE, false);
      //check if that primary key is available or not
      break;
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }

  }

  person.setSinNo(i);
  
  while(true){
    System.out.print("Name(40): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkValidity(i, 40, STRING_TYPE, true);
      break;
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }

  }

  person.setName(i);
  
  while(true){
    System.out.print("Height(5,2): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkDecimal(i, 5, 2, true); 
      break;
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }

  }
  person.setHeight(i);
  
  while(true){
    System.out.print("Weight(5,2): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkDecimal(i, 5, 2, true); 
      break;
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }

  }

  person.setWeight(i);

  while(true){
    System.out.print("Eyecolor(10): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkValidity(i, 10, STRING_TYPE, true);
      break;
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }

  }
  
  person.setEyecolor(i);

  while(true){
    System.out.print("Haircolor(10): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkValidity(i, 10, STRING_TYPE, true);
      break;
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }

  }
  
  person.setHaircolor(i);

  while(true){
    System.out.print("Address(50): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkValidity(i, 50, STRING_TYPE, true);
      break;
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }

  }

  person.setAddress(i);

  while(true){
    System.out.print("Gender(m/f): "); 
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkValidity(i, 1, STRING_TYPE, true);
      if(i == null) break;
        if(i.equalsIgnoreCase("M") || i.equalsIgnoreCase("F")){
          break;
      }else System.out.println("Entry must be m or f.");
      
    }catch(CantBeNullException e){
      System.out.println("Entry cannot be null!");
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }

  }

  person.setGender(i);
  java.sql.Date myDate=null;
  boolean dateFlag = false;
  
  while(true){
    System.out.print("Birthday(yyyy-mm-dd): ");
    i = scanner.nextLine();
    if(i.isEmpty()) i=null;
    try{
      h.checkDate(i, 10, DATE_TYPE); 
      break;
    }catch(TooLongException e){
      System.out.println("Entry too long!");
    }catch(NumberFormatException e){
      System.out.println("Entry in the wrong format!");
    }catch(DateFormatException e){
      System.out.println("No letters in the date please!");
    }catch(DateIsNullException e){
      dateFlag = true;
      break;
    }

  }

  if(dateFlag) person.setBirthday(myDate);
  else person.setBirthday(java.sql.Date.valueOf(i));


  System.out.println("Data entered: ");
  person.printAll();
  System.out.println("\nWas there a mistake? Y/N or Q to quit (will not upload to database.)");

  confirmEntries(person);
  

  
}//end of addPeople

//checks if the person already exists
//if the person already exists in the database, it returns true
//if false, the person may be added to the database
public boolean checkPerson(PeopleObj p){

  boolean duh = true;
  
  // SQL statement to execute
  String query = "select sin from People" +
    " where UPPER(sin) ='" + p.getSinNo().toUpperCase() + "'";
  
      try{

        ResultSet rs = Login.stmt.executeQuery(query);

        //check if returned anything or not

        duh = rs.next();


      }catch(SQLException ex) {
        System.err.println("SQLException: " +
                           ex.getMessage());
      }

      if(duh)return true;
      else return false;
      
  
}

//commits the person to the database
public void commitPerson(PeopleObj per){

  System.out.println("Adding to database...");

  //create SQL insert statement

  String statement = per.createInsertStatement();

  
  try{
    Login.stmt.executeUpdate(statement);
    System.out.println("Person successfully added to the database!");
  }catch(SQLException ex) {
    System.err.println("SQLException: " +
                       ex.getMessage());
  }
  
}
  
//commits the driver to the database
public void commitDriver(DriverObj dl){

  System.out.println("Adding to database...");

  //create SQL insert statement - this statement uses PREPAREDSTATEMENT for the photo part
  //statement is always set up correctly, just need to pick the
  //right pstmt or stmt to enact it (when photo or not)
  String statement = dl.createInsertStatement();

  if(dl.getPhoto()!=null){
  try{
    
  Login.pstmt = Login.stmt.getConnection().prepareStatement(statement);

  Login.pstmt.clearParameters();

  Login.pstmt.setBinaryStream(1, new FileInputStream(dl.getPhoto()), (int)dl.getPhoto().length() );

  
    Login.pstmt.executeUpdate();
    System.out.println("Licence successfully added to the database!");
  }catch(SQLException ex) {
    System.err.println("SQLException: " +
                       ex.getMessage());
  }catch(FileNotFoundException eep){
    System.err.println("Photo not found. Photo input: " +dl.getPhoto());
  }
  }else{

    try{
      Login.stmt.executeUpdate(statement);
      System.out.println("Person successfully added to the database!");
    }catch(SQLException ex) {
      System.err.println("SQLException: " +
                         ex.getMessage());
    }
    
  }
  
}

//a menu asking the user if they would like to add driving conditions
  public void drivingConditionsMenu(){
    System.out.println("Would you like to add driving conditions? Y/N");

    String input = scanner.nextLine();

    if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")){
      switch(input.toLowerCase()){
      case "y":
        addDrivingConditions();
        break;

      case "n":
        break;
      }

    }else{
      System.out.println("Invalid input!");
      drivingConditionsMenu();
    }
    
    
  }//end of drivingConditionsMenu

//part of the program where the user can add driving conditions
  public void addDrivingConditions(){

    System.out.println("Please provide the licence number: ");
    
    String lNo = scanner.nextLine();
    if (lNo.isEmpty())lNo=null;
    
    //check if that is valid, otherwise tell them to go make a licence and exit

    while(true){
      try{
        
        String result = checkLicence(lNo); 
        res.setLicenceNo(result);
        break;
      }catch(CantBeNullException e){
        
        System.out.println("Cannot be null.\n");
        driverRegMenu();
        return;
      }catch(DNEException e){
        System.out.println("Licence does not exist.\n");
        driverRegMenu();
        return;
      }
    }
    
    
    
    //this stuff is for when it is valid!
    System.out.println("\nAdd a driving condition. Here are some below:\n");
    System.out.println("--------------------------------\n");
    //display what already exists in the database and provide
    //the id for each that they can type in

    displayConditions();

    System.out.println("\n--------------------------------");
    
    
    displayInstructions();

    
    
    
    String i;
    while(true){
      try{
      i = scanner.nextLine();
      if(i.isEmpty() || i.equalsIgnoreCase("QUIT")) break;
      else if(i.equalsIgnoreCase("NEW")){

        System.out.println("Enter the description of your new condition:");
        String d = scanner.nextLine();
        if(d.isEmpty()) d=null;
        System.out.println("And the Id number?");
        String id = scanner.nextLine();
        if(id.isEmpty()) id=null;
        addNewCondition(d, id, res, cond);

      }else if(i.equalsIgnoreCase("DONE")){
        commitConditions(res, cond);
        break;
      }else if(i.equalsIgnoreCase("RESTART")){
        resetCondRes(res, cond);
      }else{
      //get here if the user is entering a number in the table
        addC(i, res, cond);

      }
      
      }catch(TakenException e){
        System.out.println("description or id is already taken. Please try again.\n");
      }catch(CantBeNullException e){
        System.out.println("Expected input for new driving condition. Both fields must be filled.\nPlease try again.\n");
      }catch(NumberFormatException e){
        System.out.println("Required a number input. Please try again.");
      }catch(DNEException e){
        System.out.println("Could not add input.\n");
      }
    }
    
  }// end of add DrivingConditions

//displays the possible driving conditions.
  public void displayConditions(){

    String query = "select c_id, description from driving_condition";
    boolean duh=true;
    try{

      ResultSet rs = Login.stmt.executeQuery(query);

      //check if returned anything or not

      duh = rs.next();

      int c_id;
      String desc;

      if(duh){
        do{
          c_id =rs.getInt("C_ID");
          desc = rs.getString("DESCRIPTION");
          System.out.println("Id No: " + c_id + " || " + desc);
        }while(rs.next());
      }else{
        System.out.println("Empty conditions table!");}

      System.out.println();
      
    }catch(SQLException ex) {
      System.err.println("SQLException: " +
                         ex.getMessage());
    }

        
  }
//displays the instructions for adding a dricing condition/restriction.
  public void displayInstructions(){
    System.out.println("Type the number and hit enter to add the condition.\n" +
                       "Please only add the condition once.\n" +
                       "If you would like to add a new condition, type 'new'.\n" +
                       "When you are done, type 'done'.\n" +
                       "If you would like to quit with no changes, type 'quit'\n"
                       +
                       "If you made a mistake, type 'restart' and you can begin again.\n");

    System.out.println("--------------------------------\n");
    
    
  }

//checks adding an entirely NEW driving condition (by description)
//adds this new condition automatically to the licence
  public void addNewCondition(String condition, String idNum, Restrictions r, ConditionObj c)throws TakenException, CantBeNullException{
    //first make sure it is not already in the set
    //both uncommitted and committed

    if(condition ==null || idNum ==null){
      throw new CantBeNullException();
    }

    //check against the database
    //does the number theyre trying to add already in use? same with description
    //so two queries

    //1 - check the number in the database, and with what you have

    //checks if number even exists in driving conditions table

    String query = "select c_id from driving_condition where c_id=" + idNum;

    boolean duh=true;
    try{
      
      ResultSet rs = Login.stmt.executeQuery(query);
      
      //check if returned anything or not
      
      duh = rs.next();
      
    }catch(SQLException ex) {
      System.err.println("SQLException: " +
                         ex.getMessage());
    }
  
    if(duh) throw new TakenException();
    else{
    
    boolean result = checkConditionNo(idNum, r, c, false); 
    if(!result) throw new TakenException();
    else{
    
    //check the description against database and what you have
    //database check

      String query2 = "select description from driving_condition where UPPER(description)='" + condition + "'";

      duh=true;
      try{

        ResultSet rs = Login.stmt.executeQuery(query2);

        //check if returned anything or not

        duh = rs.next();

      }catch(SQLException ex) {
        System.err.println("SQLException: " +
                           ex.getMessage());
      }

      if(duh)throw new TakenException();
      else{
        //against what we have for descriptions...

        ArrayList<String> temp = c.getDescription();
        int count=0;
        
        while(count < temp.size()){
          if(condition.equals(temp.get(count))) throw new TakenException();
          count++;
        }
      
        //then, add it to a conditions table object (to make)
        
        c.addDescription(condition);
        c.addCid(idNum);
        //then, add it to restrictions object
        r.addCondition(idNum);

        System.out.println("Added new condition. It has been added to the licence.");
      }
    }
  }
  }

//commits the user's inputs to the database
//both to restrictions and to driving conditions
  public void commitConditions(Restrictions r, ConditionObj c){
    //anything that has made it into the two objects at this
    //point are valid. not really anything to worry about here

    String query;
    
    //need to commit the driving conditions first
    if(!c.getCid().isEmpty()){
      int count=0;

      while(count < c.getCid().size()){
        query = "insert into driving_condition values(" + c.getCid().get(count) + ", '" + c.getDescription().get(count) + "')";

        //commit the query to the database
        try{
          Login.stmt.executeUpdate(query);
          System.out.println("New condition #" + c.getCid().get(count) + " successfully added to the database!");
        }catch(SQLException ex) {
          System.err.println("SQLException: " +
                             ex.getMessage());
        }
        
        count++;
      }
    }
    
      //now commit the restrictions
    if(!r.getConditions().isEmpty()){
    int count=0;
    
    while(count < r.getConditions().size()){
      query = " insert into restriction values('" + r.getLicenceNo() + "', " + r.getConditions().get(count) + ")";

      //commit the query to the database
      try{
        Login.stmt.executeUpdate(query);
        System.out.println("Restriction #" + r.getConditions().get(count) + " successfully added to the licence!");
      }catch(SQLException ex) {
        System.err.println("SQLException: " +
                           ex.getMessage());
      }
      
      
      count++;
      
    }
    }
    
  }

//checks if the licence is valid
//true if it exists
//false if it doesn't
  public String checkLicence(String input) throws CantBeNullException, DNEException{
    String value=null;
    
    if(input==null) throw new CantBeNullException();
    else{
      String query = "select licence_no from drive_licence where UPPER(licence_no)='" + input.toUpperCase() + "'";
    boolean duh=true;
    try{

      ResultSet rs = Login.stmt.executeQuery(query);

      //check if returned anything or not

      duh = rs.next();


      if(duh){
        value = rs.getString("licence_no");
        
      }else throw new DNEException();
      
      
    }catch(SQLException ex) {
      System.err.println("SQLException: " +
                         ex.getMessage());
    }

    return value;
    }
        
  }

//adds a driving condition that was listed in the list already. Only add
//it to the restriction object, not to the driving condition object
  public void addC(String i, Restrictions r, ConditionObj cond)throws NumberFormatException, DNEException{
    //check: is this a number?
    Integer.parseInt(i);

    //is it a valid number from the list?
    boolean result = checkConditionNo(i, r, cond, true);
    
    if(result){
    r.addCondition(i);
    System.out.println("Added condition " + i + " to the licence.");
    }else throw new DNEException();
  }

//resets the conditions and restrictions objects. Clears them.
  public void resetCondRes(Restrictions r, ConditionObj c){
    r.getConditions().clear();
    

    c.getDescription().clear();
    c.getCid().clear();
    
    System.out.println("Your uncommitted conditions and new conditions have been cleared.");
  }

//checks if the inputted number exists in the driving conditions table
//for that licence, or if you have already added it to the temporary object  
//returns true if it exists and you can add it safely, and false if it does not
  public boolean checkConditionNo(String i, Restrictions r, ConditionObj cond, boolean checkCondTable){
    //check database for that licence
    String query = "Select r_id, licence_no from restriction where r_id=" + i +
      " and UPPER(licence_no)='"+ r.getLicenceNo().toUpperCase() +"'";
    
    boolean duh=true;
    try{

      ResultSet rs = Login.stmt.executeQuery(query);

      //check if returned anything or not
      //its true if the condition is already in the restrictions table for that licence
      duh = rs.next();

    }catch(SQLException ex) {
      System.err.println("SQLException: " +
                         ex.getMessage());
    }

    if(duh)return false;
    else{
      if(!checkCondTable)duh = true;
      else{
      
      //checks if number even exists in driving conditions table

      String query2 = "select c_id from driving_condition where c_id=" + i;

      duh=true;
      try{

        ResultSet rs = Login.stmt.executeQuery(query2);

        //check if returned anything or not

        duh = rs.next();

      }catch(SQLException ex) {
        System.err.println("SQLException: " +
                           ex.getMessage());
      }
      }
      
      if(!duh) return false; 
      else{
      //check local objects
          ArrayList<String> temp = r.getConditions();
          int count=0;
          
          while(count < temp.size()){
            if(i.equals(temp.get(count))) return false;
            count++;
          }

          ArrayList<String> temp2 = cond.getCid();
          count=0;
          
          while(count < temp2.size()){
            if(i.equals(temp2.get(count))) return false;
            count++;
          }

          return true;
          
        }
      
    }
  }
}
