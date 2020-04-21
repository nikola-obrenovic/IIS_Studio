package iisc;
import java.util.*;
import java.sql.*;
public class FunDep 
{ public Set left,right;
 public boolean fun,type; 
 public Connection conn;
 
  public FunDep(Set a, Set b,boolean c,Connection con,boolean t)
  {
  left=a;
  right=b;
  fun=c;
  conn=con;
  type=t;
  }
 public FunDep()
  {
  left=new HashSet();
  right=new HashSet();
  fun=false;
  type=false;
  }
   public boolean print()
  { System.out.print(this.left + " --> " + this.right + " "+ type);
 
  return true;
  }
  public String getPrint()
  {
   JDBCQuery query=new JDBCQuery(conn);
  JDBCQuery query1=new JDBCQuery(conn);
  ResultSet rs1,rs;
  String str="",str1="";
   try
    {
   
    Iterator it=this.left.iterator();
    while(it.hasNext())
    {
      rs=query.select("select * from IISC_ATTRIBUTE where Att_id="+ it.next());
      if(rs.next())
     {
      str=str+rs.getString("Att_mnem")+"+";
     }
    query.Close();}
    str="["+ str.substring(0,str.length()-1) +"]";
     it=this.right.iterator();
     if(!fun)
    {str1= ""+it.next();
    str1=str1.replaceFirst("T","&#952;");
    }
   else
  {  while(it.hasNext())
    {
      rs=query.select("select * from IISC_ATTRIBUTE where Att_id="+ it.next());
      if(rs.next())
     {
      str1=str1+rs.getString("Att_mnem")+"+";
     }
     query.Close();
    }
    str1="["+ str1.substring(0,str1.length()-1) +"]";
  }
  if(type)
  return str+" -&#8250; "+str1;
  return str+" <u>-&#8250;</u> "+str1;
  
 }
    catch(SQLException e)
    {
      e.printStackTrace();
    } 
 return null;
  }
}
