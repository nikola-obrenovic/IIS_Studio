package iisc;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
public class JDBCQuery 
{ 
private Connection connection;
ResultSet rs;
Statement stmt; 
public JDBCQuery(Connection c)
  {
  connection=c;
  }
  public void Close()
  {
  try {
    rs.close();
    stmt.close();
    }
                          catch (SQLException e) {
    
                          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                          }
  }
  public int update(String sqlString)  {

              // SQL statement object 
              String query;   // SQL select string 
                 // SQL query results  

              query =sqlString ;
               System.out.println(sqlString);                         
                  try {    
                
                 stmt = connection.createStatement();
                 
                 int i= stmt.executeUpdate(query);
                   stmt.close();
                 return i;
                          }
                          catch (SQLException e) {
                          System.out.println(sqlString);    
                          JOptionPane.showMessageDialog(null, "Entered values are not valid!", "Error", JOptionPane.ERROR_MESSAGE);
                          //JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                           return -1;
                          }

                
  }
  public ResultSet select(String sqlString)  {

              // SQL statement object 
              String query;   // SQL select string 
         // SQL query results  

              query =sqlString ;
                                 
          // System.out.println(sqlString);                 
                 try {    
                 stmt = connection.createStatement();
                 rs = stmt.executeQuery(query);
                   return rs;
                          }
                          catch (SQLException e) {
//                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                           return null;
                          }

                
  }
    public String[] selectArray(String sqlString, int i,int k)  {

            // SQL statement object 
              String query;   // SQL select string 
            // SQL query results  
              i++;
String[] arr=new String[i];
int j=1;
              query =sqlString ;
              //  System.out.println(sqlString);                                 
                   arr[0]="";  
                  try {
                 stmt = connection.createStatement();
                 rs = stmt.executeQuery(query);
                 while(rs.next())
                 {
                 arr[j]=rs.getString(k).trim();
          
                 j++;
                 }
                   return arr;
                          }
                          catch (SQLException e) {
                          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
 // System.out.println(sqlString);
                           return null;
                        
                          }

                
  }
  
     public String[] selectArray1(String sqlString, int i,int k)  {

            // SQL statement object 
              String query;   // SQL select string 
            // SQL query results  
             
String[] arr=new String[i];
int j=0;
              query =sqlString ;
              
                  try {
                 stmt = connection.createStatement();
                 rs = stmt.executeQuery(query);
                 while(rs.next())
                 {
                 arr[j]=rs.getString(k).trim();
          
                 j++;
                 }
                   return arr;
                          }
                          catch (SQLException e) {
                          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
 // System.out.println(sqlString);
                           return null;
                        
                          }

                
  }
  
  //Slavica
  public String[] selectArraySA(String sqlString, int i,int k)  {

            // SQL statement object 
              String query;   // SQL select string 
            // SQL query results  
              //i++;
              String[] arr=new String[i];
              int j=0;
              query =sqlString ;
              //  System.out.println(sqlString);                                 
                  // arr[0]="";  
                  try {
                 stmt = connection.createStatement();
                 rs = stmt.executeQuery(query);
                 while(rs.next())
                 {              
                 arr[j]=rs.getString(k).trim();           
                 j++;
                 }
                   return arr;
                          }
                          catch (SQLException e) {
                          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
 // System.out.println(sqlString);
                           return null;
                        
                          }

                
  }
  public int[] selectArraySAint(String sqlString, int i,int k)  {

            // SQL statement object 
              String query;   // SQL select string 
            // SQL query results  
              //i++;
              int[] arr = new int[i];              
              int j=0;
              query =sqlString ;
              //  System.out.println(sqlString);                                 
                  // arr[0]="";  
                  try {
                 stmt = connection.createStatement();
                 rs = stmt.executeQuery(query);
                 while(rs.next())
                 {              
                 arr[j]=rs.getInt(k);           
                 j++;
                 }
                   return arr;
                          }
                          catch (SQLException e) {
                          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
 // System.out.println(sqlString);
                           return null;
                        
                          }

                
  }
  public String[] selectArraySAizraz(String sqlString, int i,int k)  {

            // SQL statement object 
              String query;   // SQL select string 
            // SQL query results  
              //i++;
              String[] arr=new String[i];
              int j=0;
              query =sqlString ;
              //  System.out.println(sqlString);                                 
                  // arr[0]="";  
                  try {
                 stmt = connection.createStatement();
                 rs = stmt.executeQuery(query);
                 while(rs.next())
                 {              
                  arr[j]=rs.getString(k);
                  j++;
                 }
                   return arr;
                          }
                          catch (SQLException e) {
                          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
 // System.out.println(sqlString);
                           return null;
                        
                          }                
  }
   public String toString(int i) 
  { return i + ""; }
   public Set selectSASet(String sqlString,int k)  {

             
              String query;   // SQL select string             
              Set Skup = new HashSet();              
              query =sqlString ;
              try {
                 stmt = connection.createStatement();
                 rs = stmt.executeQuery(query);
                 while(rs.next())
                 {              
                    String O= toString(rs.getInt(k));
                    Skup.add(O);                  
                 }
                   return Skup;
              }
               catch (SQLException e) {
                          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
 // System.out.println(sqlString);
                           return null;
              }                
  } 
}