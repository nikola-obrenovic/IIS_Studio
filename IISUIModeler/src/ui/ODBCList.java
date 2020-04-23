package ui;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class ODBCList 
{ public Set odbc;
  public ODBCList()
  {odbc=new HashSet();
  try 
        { String name;
               BufferedReader infile = new BufferedReader (new InputStreamReader (new FileInputStream ("C:\\WINNT\\odbc.ini"))); 
            name = infile.readLine ();
               while ( (name = infile.readLine ())!=null && name.indexOf("[")<0) 
               { 
         
                  String[] in=splitString(name,"=");
                   odbc.add(in[0]);
               } 
               infile.close (); 
          }
          catch (IOException e) { 
          
            try 
        { String name;
               BufferedReader infile = new BufferedReader (new InputStreamReader (new FileInputStream ("C:\\Windows\\odbc.ini"))); 
            name = infile.readLine ();
               while ( (name = infile.readLine ())!=null && name.indexOf("[")<0) 
               { 
         
                String[] in=splitString(name,"=");
                   odbc.add(in[0]);
               } 
               infile.close (); 
          }catch (IOException ex) { 
           getDsn(JOptionPane.showInputDialog(null,"<html><center>File C:\\Windows\\odbc.ini or C:\\WINNT\\odbc.ini does not exist. Type DSN name.", "DSN name", JOptionPane.QUESTION_MESSAGE));
          } 
    
          } 
    
  }
  public void getDsn(String s)
  {
     try 
        { String name;
               BufferedReader infile = new BufferedReader (new InputStreamReader (new FileInputStream (s))); 
            name = infile.readLine ();
               while ( (name = infile.readLine ())!=null && name.indexOf("[")<0) 
               { 
         
                String[] in=splitString(name,"=");
                   odbc.add(in[0]);
               } 
               infile.close (); 
          }catch (IOException ex) { 
          String s1=(String)JOptionPane.showInputDialog(null,"<html><center>File "+ s +" does not exist. Type new DSN name.", "DSN name", JOptionPane.QUESTION_MESSAGE);
           getDsn(s1);
          } 
  }
  public static String[] splitString(String theString, String theDelimiter)
{
int delimiterLength;
int stringLength = theString.length();
if (theDelimiter == null || (delimiterLength = theDelimiter.length()) == 0)
{
return new String[] {theString};
}
int count,start,end;
count = 0;
start = 0;
while((end = theString.indexOf(theDelimiter, start)) != -1)
{
count++;
start = end + delimiterLength;
}
count++;

String[] result = new String[count];

count = 0;
start = 0;
while((end = theString.indexOf(theDelimiter, start)) != -1)
{
result[count] = (theString.substring(start, end));
count++;
start = end + delimiterLength;
}
end = stringLength;
result[count] = theString.substring(start, end);

return (result);
}

public static String now()
{
    /* 
    ** on some JDK, the default TimeZone is wrong
    ** we must set the TimeZone manually!!!
    **   Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EST"));
    */
    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
    
    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    java.text.SimpleDateFormat sdf = 
          new java.text.SimpleDateFormat(DATE_FORMAT);
    /*
    ** on some JDK, the default TimeZone is wrong
    ** we must set the TimeZone manually!!!
    **     sdf.setTimeZone(TimeZone.getTimeZone("EST"));
    */
    sdf.setTimeZone(TimeZone.getDefault());          
          
    return sdf.format(cal.getTime());
    }



}