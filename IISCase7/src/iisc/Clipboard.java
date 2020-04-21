package iisc;
import java.sql.*;

import javax.swing.tree.TreePath;

public class Clipboard 
{public int id;
 public int pid;
 public String table;
 public Connection con;
 public String type;
 public TreePath elem; //jovo+ 
 public Clipboard()
  {
  id=-1;
  pid=-1;
  table=null;
  con=null;
  type=null;
  }
    //jovo+  
      public Clipboard(int p,int i, String t, Connection c, String tp,TreePath element)
      {
          pid=p;
          id=i;
          table=t;
          con=c;
          type=tp;
          elem = element;
          //System.out.print("Eve2");
      }  
    //jovo+ end   
  public Clipboard(int p,int i, String t, Connection c, String tp)
  {
  pid=p;
  id=i;
  table=t;
  con=c;
  type=tp;
  }
  public boolean  isEmpty()
  {
    if(pid==-1)
    return true;
    else
    return false;
  }
   public void clear()
  {
   pid=-1;
  id=-1;
  table=null;
  con=null;
  type=null;
  }
  
}