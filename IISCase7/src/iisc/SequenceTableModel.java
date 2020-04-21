package iisc;
import java.util.*;   
import java.sql.*;
import javax.swing.table.*;

public class SequenceTableModel extends AbstractTableModel 
{
  Vector cache;
  int colCount;
  String[] headers;
  Connection db;
  Statement statement;  
    
  public SequenceTableModel(Connection con)
  {
    cache=new Vector();
    db=con;   
  }
  
  
  public String getColumnName(int i) {return headers[i];}

  public int getColumnCount() {return colCount;}

  public int getRowCount() {return cache.size();}

  public Object getValueAt(int row,int col){
   	return ((Object[])cache.elementAt(row))[col];
  }
  
  public Class getColumnClass(int c) {
   try{
    if (c==1 || c==5 || c==7)
        return Boolean.class;
    else
        return getValueAt(0, c).getClass();
   }
   catch(Exception e)
   {
   e.printStackTrace();
   return null;
   }
  }

  public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            boolean ret;
            if (col == 0) {
                ret= false;
            } else if (col==1){ 
                ret= true;
             }else 
            {            
              if (((Object[])cache.elementAt(row))[1].equals(Boolean.valueOf("true")))
                ret =true;
              else
                ret=false;            
            }
            return ret;
        }

   public void setValueAt(Object value, int row, int col) {//are you editing?
 	/*prepare the query*/
 	/*you have to change the query to adapt it to your table*/
  if (col==0)
  {
    ((String[])cache.elementAt(row))[col] = (String)value;
  }
  if (col==1 || col==5 || col==7)
  {
    ((Object[])cache.elementAt(row))[col] = (Boolean)value;
  }
  if (col==2 || col==3 || col==4 || col==6)
  {
    ((Object[])cache.elementAt(row))[col] = (String)value;
  }    
	fireTableCellUpdated(row, col);//also update the table
 }


  public void setQuerySequ(int IdSema, int IdProjekta, int IdASistema, String DBMS)
  { 
    ResultSet rs, rs1;
    if (DBMS.compareTo("MS SQL Server 2000")!=0)
      colCount=8;
     else colCount=4;
    cache= new Vector();
    int Atribut;
    //ImageIcon iconOK = new ImageIcon(IISFrameMain.class.getResource("icons/ok.gif"));
   
    String q = "select Att_id from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSema+" and IISC_RS_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" ORDER BY Att_sequence";
    try{
      headers=new String[colCount];
      headers[0]="Attribute";      
      headers[1]="Sequence";
      headers[2]="Increment by";
      headers[3]="Start with";
      
      if (DBMS.compareTo("MS SQL Server 2000")!=0)
      {
        headers[4]="Maxvalue";
        headers[5]="Cycle";
        headers[6]="Cache";
        headers[7]="Order";      
      }
      JDBCQuery query=new JDBCQuery(db); 
      JDBCQuery query1=new JDBCQuery(db);
      rs=query.select(q);
      while (rs.next())
      {
        Object[] record=new Object[colCount];
        Atribut = rs.getInt("Att_id"); 
        String upit = "select Att_mnem from IISC_ATTRIBUTE where IISC_ATTRIBUTE.PR_id="+IdProjekta+" and IISC_ATTRIBUTE.Att_id="+Atribut;
        rs1=query1.select(upit);
        if (rs1.next())
        {
          record[0]=rs1.getString("Att_mnem");
        }
        query1.Close(); 
        record[1]= Boolean.valueOf(false);
        record[2]= "1"; //new Integer(1);
        record[3]= "1"; //new Integer("1");
        if (DBMS.compareTo("MS SQL Server 2000")!=0)
        {
          record[4]= "0"; //new Integer("0");
          record[5]=Boolean.valueOf(false);
          record[6]="0"; //new Integer("0");
          record[7]= Boolean.valueOf(false); // Integer.valueOf("");
        }
        cache.addElement(record);
      }
      query.Close();
      fireTableChanged(null);
    }
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
    
  }
  
    
  public void setTabeluSequ(int IdSema, int IdProjekta, int IdASistema, String DBMS)
  { 
    ResultSet rs, rs1, rs2;
    if (DBMS.compareTo("MS SQL Server 2000")!=0)
      colCount=8;
     else colCount=4;
    cache= new Vector();
    int Atribut;   

    String q = "select Att_id from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSema+" and IISC_RS_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" ORDER BY Att_sequence";
    try{
      headers=new String[colCount];
      headers[0]="Attribute";      
      headers[1]="Sequence";
      headers[2]="Increment by";
      headers[3]="Start with";
            
      if (DBMS.compareTo("MS SQL Server 2000")!=0)
      {
        headers[4]="Maxvalue";
        headers[5]="Cycle";
        headers[6]="Cache";
        headers[7]="Order";      
      }
      JDBCQuery query=new JDBCQuery(db); 
      JDBCQuery query1=new JDBCQuery(db);
      JDBCQuery query2=new JDBCQuery(db);
      rs=query.select(q);
      while (rs.next())
      {
        Object[] record=new Object[colCount];
        Atribut = rs.getInt("Att_id"); 
        String upit = "select Att_mnem from IISC_ATTRIBUTE where IISC_ATTRIBUTE.PR_id="+IdProjekta+" and IISC_ATTRIBUTE.Att_id="+Atribut;
        rs1=query1.select(upit);
        if (rs1.next())
        {
          record[0]=rs1.getString("Att_mnem");
        }
        query1.Close(); 
        upit = "select * from IISC_SEQUENCE where PR_id="+IdProjekta+ " and AS_id="+IdASistema + " and RS_id="+IdSema+ " and Att_id="+Atribut;
        rs2=query2.select(upit);
        if(rs2.next())
        {
          record[1]= Boolean.valueOf(true);
          record[2]= rs2.getString("Seq_increment"); //Integer.valueOf(rs2.getString("Seq_increment"));
          record[3]= rs2.getString("Seq_start"); //Integer.valueOf(rs2.getString("Seq_start"));
        if (DBMS.compareTo("MS SQL Server 2000")!=0)
        {
          record[4]= rs2.getString("Seq_maxvalue"); //Integer.valueOf(rs2.getString("Seq_maxvalue"));
          if(rs2.getInt("Seq_cycle")==0)
                record[5]= Boolean.valueOf(false);
          else
                record[5]= Boolean.valueOf(true);
          record[6]= rs2.getString("Seq_cache"); //Integer.valueOf(rs2.getString("Seq_cache"));
          if(rs2.getInt("Seq_order")==0)
                record[7]= Boolean.valueOf(false); 
          else
                record[7]= Boolean.valueOf(true);
        }
        }else
        {
          record[1]= Boolean.valueOf(false);
          record[2]= "1"; //new Integer(1);
          record[3]= "1"; //new Integer("1");
          if (DBMS.compareTo("MS SQL Server 2000")!=0)
          {
            record[4]= "0"; //new Integer("0");
            record[5]=Boolean.valueOf(false);
            record[6]="0"; //new Integer("0");
            record[7]= Boolean.valueOf(false); // Integer.valueOf("");
          }
        }
        query2.Close();
        cache.addElement(record);
      }
      query.Close();
      fireTableChanged(null);
    }
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }    
  }
  
  
}