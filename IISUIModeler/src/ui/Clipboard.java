package ui;

import java.sql.*;

public class Clipboard 
{ 
  public int templateID;
  public Connection conn;
  public int cut;
  public Clipboard()
  {
  templateID=-1;
  }
  public Clipboard(int p, Connection c, int cu)
  {
  templateID=p;
  conn=c;
  cut=cu;
  }
  public boolean  isEmpty()
  {
    if(templateID==-1)
    return true;
    else
    return false;
  }
   public void clear()
  {
   templateID=-1;
   cut=0;
  }
  
}