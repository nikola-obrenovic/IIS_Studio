package iisc;
import java.sql.*;
import java.util.*;    
import javax.swing.*;
import javax.swing.table.*;

public class QueryTableModel extends AbstractTableModel{
 
 Vector cache;
 int colCount;
 String[] headers;
 Connection db;
 Statement statement;
 String currentURL;
 String kolon;
 String type="";
 int PID;
 int id;
 public int image=-1;
 public QueryTableModel(Connection con,int i)
 {
  cache=new Vector();
  db=con;
  id=i;
 }//konstr. sonu
 
 /*****abstracttablemodel methodlarý******/
 public String getColumnName(int i) {return headers[i];}

 public int getColumnCount() {return colCount;}

 public int getRowCount() {return cache.size();}
 public Class getColumnClass(int i)
 {
 try
 {
 if(i>0 && image==1) 
 return  ImageIcon.class;
 else if(i==0 && image==2) 
 return  ImageIcon.class;
 else
 return  String.class;
 }
 catch(Exception e)
 {
   e.printStackTrace();
   return null;
 }
 }
 public Object getValueAt(int row,int col){
        if(row>=0)
            return ((Object[])cache.elementAt(row))[col];
        return null;
  }
 
 public boolean isCellEditable(int row, int col){ 
 if(type.equals("Editable")&& col==1)
 return true; 
 else
 return false;
 }

 public void setValueAt(Object value, int row, int col) {//are you editing?
 	/*prepare the query*/
 	/*you have to change the query to adapt it to your table*/
    ((String[])cache.elementAt(row))[col] = (String)value;
	fireTableCellUpdated(row, col);//also update the table
 }
 /*****end of abstracttablemodel methodlarý******/

 public void deleteRow(int row) {//are you editing?
   	try{
 JDBCQuery query=new JDBCQuery(db); 
 ResultSet rs1;
 rs1=query.select("select * from IISC_DERIVED_ATTRIBUTE,IISC_ATTRIBUTE where IISC_ATTRIBUTE.Att_id=IISC_DERIVED_ATTRIBUTE.Att_id_derived and IISC_ATTRIBUTE.Att_mnem='"+getValueAt(row,0)+"' and IISC_ATTRIBUTE.PR_id="+PID);
 rs1.next();
 int ida=rs1.getInt("DA_id");
 query.Close(); 
 String  s="delete from IISC_DER_ATT_FUN where DA_id="+ ida;
 query.update(s);
 s="delete from IISC_DERIVED_ATTRIBUTE where DA_id="+ ida;
 query.update(s);
}catch(Exception e){
	//System.out.println("Could not update");
	}
cache.remove(row);
	fireTableRowsDeleted(row, row);//also update the table
 }
  public void removeRow(int row) {//are you editing?
cache.remove(row);
	fireTableRowsDeleted(row, row);//also update the table
 }
 public void setQuery(String q){
 
cache= new Vector();
 try{
    JDBCQuery query=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=4;
   //
    headers=new String[colCount];
    headers[0]="Attribute";
    headers[1]="On insert";
    headers[2]="On delete";
    headers[3]="On update";
    //cache.addElement(headers);
    while(rs.next())
      {int i=rs.getInt(1);
      String[] record=new String[colCount];
      ResultSet rs1=query.select("select * from IISC_ATTRIBUTE where Att_id="+rs.getInt(4));
      rs1.next();
      record[0]=rs1.getString("Att_mnem");
      PID=rs1.getInt("PR_ID");
       query.Close();
      rs1=query.select("select * from IISC_DER_ATT_FUN,IISC_FUNCTION where IISC_FUNCTION.Fun_id=IISC_DER_ATT_FUN.Fun_id and DAF_mode='insert' and DA_id="+i);
      if(rs1.next())
      record[1]=rs1.getString("Fun_name");
       query.Close();
      rs1=query.select("select * from IISC_DER_ATT_FUN,IISC_FUNCTION where IISC_FUNCTION.Fun_id=IISC_DER_ATT_FUN.Fun_id and DAF_mode='update' and DA_id="+i);
      if(rs1.next())
      record[2]=rs1.getString("Fun_name");
       query.Close();
      rs1=query.select("select * from IISC_DER_ATT_FUN,IISC_FUNCTION where IISC_FUNCTION.Fun_id=IISC_DER_ATT_FUN.Fun_id and DAF_mode='delete' and DA_id="+i);
      if(rs1.next())
      record[3]=rs1.getString("Fun_name");
       query.Close();
      cache.addElement(record);
      } //while sonu

    fireTableChanged(null);
    } //try sonu
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 } //setQuery sonu

public void setQueryO(String q){
 
cache= new Vector();
 try{
    JDBCQuery query=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=8;
  
    headers=new String[colCount];
    headers[0]="Component Type";
    headers[1]="Title";
    headers[2]="Parent";
    headers[3]="Query allowed";
    headers[4]="Insert allowed";
    headers[5]="Update allowed";
    headers[6]="Delete allowed";
    headers[7]="Check constraint";
   // cache.addElement(headers);
    while(rs.next())
      {int i=rs.getInt(1);
      String[] record=new String[colCount];
      ResultSet rs1=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TOB_id="+i);
      rs1.next();
      record[0]=rs1.getString("TOB_mnem");
      record[1]=rs1.getString("Tob_name");
      record[3]=retValue(rs1.getString("Tob_queallow"));
      record[4]=retValue(rs1.getString("Tob_insallow"));
      record[5]=retValue(rs1.getString("Tob_updallow"));
      record[6]=retValue(rs1.getString("Tob_deleteallow"));
      record[7]=retValue("N");
      String s=rs1.getString("Tob_check");
      if(s!=null && !s.trim().equals(""))
      record[7]="Y";
      PID=rs1.getInt("PR_ID");
      String d=rs1.getString("Tob_superord");
      query.Close();
      rs1=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TOB_id="+d);
      if(rs1.next())
      record[2]=rs1.getString("Tob_mnem");
      query.Close();
      cache.addElement(record);
      } //while sonu

    fireTableChanged(null);
    } //try sonu
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 } //setQuery sonu

public void setQueryA(String q){
 
cache= new Vector();
 try{
    JDBCQuery query=new JDBCQuery(db); 
    JDBCQuery query1=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=10;
  //  
    headers=new String[colCount];
    headers[1]="Attribute";
    headers[2]="Key no.";
     headers[3]="Unique no.";
    headers[0]="Sequence";
    headers[4]="Title";
    headers[5]="Mandatory";
    headers[6]="Update allowed";
    headers[7]="Behaviour";
    headers[8]="Function";
    headers[9]="Function";
    headers[9]="Default value";
    //cache.addElement(headers);
    if(rs.next())
      {int it=rs.getInt("TOB_id");
      int ifd=rs.getInt("Tf_id");
     int k=0;
      query.Close();
      ResultSet rs1,rs2;
      rs1=query.select("select * from IISC_ATT_TOB,IISC_ATTRIBUTE,IISC_COMPONENT_TYPE_OBJECT_TYPE  where IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id=IISC_ATT_TOB.TOB_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_id="+ ifd +"  and IISC_ATT_TOB.TF_id="+ ifd +" and IISC_ATT_TOB.TOB_id="+it+ " order by W_order,IISC_ATT_TOB.Att_id");
      while (rs1.next())
      {k++; 
      int att=rs1.getInt("Att_id") ;
      int tf=rs1.getInt("TF_id") ;
      String[] record=new String[colCount];
      record[1]=rs1.getString("Att_mnem");
      record[0]=""+k;
      record[4]=rs1.getString("W_tittle");
      record[5]=retValue(rs1.getString("W_mand"));
      record[6]=retValue(rs1.getString("W_updallow"));
      record[7]=rs1.getString("W_behav");
      record[9]=rs1.getString("W_default");
      if(record[7].equals("1"))record[7]="query only";
      if(record[7].equals("2"))record[7]="display only"; 
      if(record[7].equals("0"))record[7]="modifiable";
      record[2]="";
       record[3]="";
      rs2=query1.select("select * from IISC_ATT_KTO,IISC_KEY_TOB  where  IISC_ATT_KTO.Tob_rbrk=IISC_KEY_TOB.Tob_rbrk and  IISC_ATT_KTO.Tob_id=IISC_KEY_TOB.Tob_id and  IISC_ATT_KTO.Att_id="+ att +" and  IISC_ATT_KTO.TF_id="+ tf  +" and IISC_ATT_KTO.TOB_id="+it);
      while (rs2.next()) 
      {String local="";
      if(rs2.getInt("Tob_local")==1)
      local=" local";
      else
      local="";
      record[2]=record[2]+" " +rs2.getString("Tob_rbrk")+local;
      }
      query1.Close(); 
      rs2=query1.select("select * from IISC_ATT_UTO  where Att_id="+ att  +" and  TF_id="+ tf +" and TOB_id="+it);
      while (rs2.next()) record[3]=record[3]+" " +rs2.getString("Tob_rbrk");
      query1.Close(); 
      int fid;
      if((fid=rs1.getInt("Fun_id"))>-1)
      {rs2=query1.select("select * from IISC_FUNCTION  where IISC_FUNCTION.Fun_id="+fid);
      if(rs2.next())
      record[8]=rs2.getString("Fun_name");
      query1.Close();
      }
      else record[8]="";
  //    System.out.println(record[0]);
     cache.addElement(record);
      } //while sonu
 query.Close();
      }
    fireTableChanged(null);
    } //try sonu
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 } //setQuery sonu
 
 
  public void setQueryDom(String q){
 
cache= new Vector();
 try{
    JDBCQuery query=new JDBCQuery(db); 
    JDBCQuery query1=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=5;
  
    headers=new String[colCount];
    headers[0]="Name";
    headers[1]="Description";
    headers[2]="Data Type";
    headers[3]="Length";
    headers[4]="Dec.places";
      while (rs.next())
      { String[] record=new String[colCount];
      record[0]=rs.getString("Dom_mnem");
      record[1]=rs.getString("Dom_name");
      record[2]=rs.getString("PT_mnemonic");
      record[3]=rs.getString("Dom_length");
      record[4]=rs.getString("Dom_decimal");
     cache.addElement(record);
      } //while sonu
 query.Close();
    fireTableChanged(null);
    } //try sonu
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 } //setQuery sonu
 
   public void setQueryFun(String q){
 
cache= new Vector();
 try{
    JDBCQuery query=new JDBCQuery(db); 
    JDBCQuery query1=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=2;
    
    headers=new String[colCount];
    headers[0]="Name";
    headers[1]="Description";
      while (rs.next())
      { String[] record=new String[colCount];
      record[0]=rs.getString("Fun_name");
      record[1]=rs.getString("Fun_desc");
     cache.addElement(record);
      } //while sonu
 query.Close();
    fireTableChanged(null);
    } //try sonu
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 } //setQuery sonu
 
 public void setQueryAppType(String q){
 
cache= new Vector();
 try{
    JDBCQuery query=new JDBCQuery(db); 
    JDBCQuery query1=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=2;
    
    headers=new String[colCount];
    headers[0]="Name";
    headers[1]="Description";
      while (rs.next())
      { String[] record=new String[colCount];
      record[0]=rs.getString("AS_type");
      record[1]=rs.getString("AS_type_name");
     cache.addElement(record);
      } //while sonu
 query.Close();
    fireTableChanged(null);
    } //try sonu
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 } //setQuery sonu
  public void setQueryAtt(String q){
 
cache= new Vector();
 try{
    JDBCQuery query=new JDBCQuery(db); 
    JDBCQuery query1=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=6;
    
    headers=new String[colCount];
    headers[0]="Name";
    headers[1]="Description";
    headers[2]="Domain";
    headers[3]="Exists in DB";
    headers[4]="Elementary";
    headers[5]="Derived";
      while (rs.next())
      { String[] record=new String[colCount];
      record[0]=rs.getString("Att_mnem");
      record[1]=rs.getString("Att_name");
      record[2]=rs.getString("Dom_mnem");
      String sbp=rs.getString("Att_sbp");
      if(sbp!=null && sbp.equals("1"))
       record[3]="Yes";
      else
       record[3]="No";
      if(rs.getString("Att_elem").equals("1"))
       record[4]="Yes";
      else
       record[4]="No";
       String dr=rs.getString("Att_der");
      if(dr!= null && !dr.equals("-1"))
       record[5]="Yes";
      else
       record[5]="No";
     cache.addElement(record);
      } //while sonu
 query.Close();
    fireTableChanged(null);
    } //try sonu
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 } //setQuery sonu
 
  public void setQueryRSKEY(String q){
 
cache= new Vector();
 try{
      ImageIcon iconOK = new ImageIcon(IISFrameMain.class.getResource("icons/ok.gif"));
      ImageIcon iconPK = new ImageIcon(IISFrameMain.class.getResource("icons/pk.gif"));
    JDBCQuery query=new JDBCQuery(db); 
    JDBCQuery query1=new JDBCQuery(db); 
    JDBCQuery query2=new JDBCQuery(db); 
    ResultSet rs1,rs=query.select(q),rs2;
    colCount=4;
    
    headers=new String[colCount];
    headers[0]="Key no.";
    headers[1]="Candidate";
    headers[2]="PK";
    headers[3]="Local";
      while (rs.next())
      { Object[] record=new Object[colCount];
      record[0]=rs.getString("RS_rbrk");

     if(rs.getInt("RS_primary_key")==1 )record[2]=iconPK;
      if(rs.getInt("RS_local")==1)record[3]=iconOK;
    int idr= rs.getInt("RS_id");
 Synthesys syn=new Synthesys();
 syn.con=db;
 syn.as=rs.getInt("AS_id");
 RelationScheme r= syn.getRelationScheme(idr);
 boolean can=false,can2=true;
  rs1=query1.select("select * from IISC_GRAPH_CLOSURE where RS_superior="+idr);
 if(!rs1.next())can=true;
 else
 { query1.Close();
   rs1=query1.select("select * from IISC_GRAPH_CLOSURE where RS_superior="+idr);
   while(rs1.next()&&!can)
   {can=true;
     RelationScheme re= syn.getRelationScheme(rs1.getInt("RS_inferior"));
     Iterator it=r.kandidati.iterator();
     while(it.hasNext())
     if(syn.Presjek((Set)it.next(),re.primarni_kljuc).isEmpty())can=false;
   }
 }
 query1.Close();
rs1=query1.select("select count(*) from IISC_RS_KEY where RS_id="+idr+" and RS_candidate=1");  
rs1.next();
if(rs1.getInt(1)==1)can=true;
query1.Close();
 if(rs.getInt("RS_candidate")==1)record[1]=iconOK; 
 cache.addElement(record);
      } //while sonu
 query.Close();
    fireTableChanged(null);
    } //try sonu
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 } //setQuery sonu
 
  public void setQueryRSATT(String q){
 
cache= new Vector();
 try{
      ImageIcon iconOK = new ImageIcon(IISFrameMain.class.getResource("icons/ok.gif"));
    JDBCQuery query=new JDBCQuery(db); 
    JDBCQuery query1=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=3;
    
    headers=new String[colCount];
    headers[0]="Attribute";
    headers[1]="NULL";
    headers[2]="Modifiable";
      while (rs.next())
      { Object[] record=new Object[colCount];
      record[0]=rs.getString("Att_mnem");
      if(rs.getInt("Att_null")==1)record[1]=iconOK;
       if(rs.getInt("Att_modifiable")==1)record[2]=iconOK;
     cache.addElement(record);
      } //while sonu
 query.Close();
    fireTableChanged(null);
    } //try sonu
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 } //setQuery sonu
 
 /*nobrenovic: start*/
    public void setQueryCheckConstraint(String q){
  
        cache= new Vector();
        try{
            JDBCQuery query=new JDBCQuery(db); 
            ResultSet rs=query.select(q);
            colCount=3;
            
            headers=new String[colCount];
            headers[0]="Constraint ID";
            headers[1]="Name";
            headers[2]="Description";
            while (rs.next())
            { 
                Object[] record=new Object[colCount];
                record[0]=rs.getString("CHKC_id");
                record[1]=rs.getString("CHKC_name");
                record[2]=rs.getString("CHKC_desc");
                cache.addElement(record);
            }
            query.Close();
            fireTableChanged(null);
        } //try sonu
        catch(Exception e){
            cache=new Vector();
            e.printStackTrace();
        }
    } 
    /*nobrenovic: end*/
    
   public void setQueryUnique(String q){
 
cache= new Vector();
 try{
  ImageIcon iconOK = new ImageIcon(IISFrameMain.class.getResource("icons/ok.gif"));
      JDBCQuery query=new JDBCQuery(db); 
    JDBCQuery query1=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=1;
    
    headers=new String[colCount];
    headers[0]="Unique no.";
      while (rs.next())
      { Object[] record=new Object[colCount];
      record[0]=rs.getString("RS_rbru");
        cache.addElement(record);
      } //while sonu
 query.Close();
    fireTableChanged(null);
    } //try sonu
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 } //setQuery sonu
 
 
 public void setQueryPT(String q){
 
cache= new Vector();
 try{
    JDBCQuery query=new JDBCQuery(db); 
    JDBCQuery query1=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=6;
    
    headers=new String[colCount];
    headers[0]="Name";
    headers[1]="Description";
    headers[2]="Length required";
    headers[3]="Length";
    headers[4]="Def. value";
    headers[5]="Dec.places";
      while (rs.next())
      { String[] record=new String[colCount];
      record[0]=rs.getString("PT_mnemonic");
      record[1]=rs.getString("PT_name");
     if(rs.getString("PT_length_required").equals("1"))
       record[2]="yes";
      else
       record[2]="no";
      record[3]=rs.getString("PT_length");
      record[4]=rs.getString("PT_def_val");
      record[5]=rs.getString("PT_decimal");
     cache.addElement(record);
      } //while sonu
 query.Close();
    fireTableChanged(null);
    } //try sonu
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 } //setQuery sonu
 
 
 public void setQueryFilter(String[] q){
 
cache= new Vector();
 try{colCount=2;
   headers=new String[colCount];
    headers[0]=" ";
    headers[1]="Value";
    type="Editable";
    
      for(int i=0;i< q.length;i++)
      {String[] record=new String[colCount];
      record[0]=q[i];
      record[1]="";

     cache.addElement(record);
   
    } //try sonu
     fireTableChanged(null);
 }
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 } //setQuery sonu
  
  public void setQueryRSConstraint(String q){
 
cache= new Vector();
 try{
   JDBCQuery query=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=3;
   headers=new String[colCount];
    headers[0]=" ";
    headers[1]="Constraint";
    headers[2]="Type";
    
      while (rs.next())
      {String[] record=new String[colCount];
      record[0]=rs.getString("RSC_id");
       record[1]=rs.getString("RSC_name");
      record[2]=rs.getString("RSCT_type");

     cache.addElement(record);
   
    } //try sonu
    query.Close();
     fireTableChanged(null);
 }
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 } //setQuery sonu
 public void setQueryLog(String[] q){
 
cache= new Vector();
 try{
  ImageIcon errorOK = new ImageIcon(IISFrameMain.class.getResource("icons/error.gif"));
   ImageIcon imageOK = new ImageIcon(IISFrameMain.class.getResource("icons/ok.gif"));
    colCount=2;
    headers=new String[colCount];
    headers[0]="";
    headers[1]="Message";
    
    for(int i=0;i<q.length;i++)
      {Object[] record=new Object[colCount];
      if(q[i].toString().trim().startsWith("Warning"))
      record[0]=errorOK;
      else
      record[0]=imageOK;
      record[1]=q[i];

     cache.addElement(record);
   
    } //try sonu

     fireTableChanged(null);
 }
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 }
  public void setRSCQuery(String q){
 
cache= new Vector();
 try{
   JDBCQuery query=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=2;
   headers=new String[colCount];
    headers[0]=" ";
     headers[1]=" ";
    
      while (rs.next())
      {String[] record=new String[colCount];
      record[1]="  " + rs.getString("RS_name");
       record[0]=rs.getString("RS_id");

     cache.addElement(record);
   
    } //try sonu
    query.Close();
     fireTableChanged(null);
 }
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 }
   public void setFormQuery(String q){
 
cache= new Vector();
 try{
   JDBCQuery query=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=3;
   headers=new String[colCount];
    headers[0]=" ";
     headers[1]="Form Type";
     headers[2]="Application system";
      while (rs.next())
      {String[] record=new String[colCount];
      record[1]="  " + rs.getString("TF_mnem");
      record[0]=rs.getString("TF_id");
      record[2]=rs.getString("AS_name");
     cache.addElement(record);
     } //try sonu
    query.Close();
     fireTableChanged(null);
 }
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 }
 public void setCollisionQuery(String q){
 
cache= new Vector();
 try{
   JDBCQuery query=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=4;
   headers=new String[colCount];
    headers[0]="ID";
     headers[1]="Date and time";
     headers[2]="Report type";
     headers[3]="Report text";
    while (rs.next())
      {String[] record=new String[colCount];
      record[0]=rs.getString("CL_id");
      record[1]=rs.getString("CL_date");
      record[2]=rs.getString("CLT_name");
      record[3]=rs.getString("CL_text");
      cache.addElement(record);
     } //try sonu
    query.Close();
     fireTableChanged(null);
 }
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 }
 
  public void setAttributeType(String q){
 
cache= new Vector();
 try{
   JDBCQuery query=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=6;
   headers=new String[colCount];
    headers[0]="ID";
     headers[1]="Attribute";
     headers[2]="Attribute Title";
     headers[3]="Component Type";
     headers[4]="Form Type";
     headers[5]="Application system";
    while (rs.next())
      {String[] record=new String[colCount];
      record[0]=rs.getString("Att_id");
      record[1]=rs.getString("Att_mnem");
      record[2]=rs.getString("W_tittle");
      record[3]=rs.getString("Tob_mnem");
      record[4]=rs.getString("TF_mnem");
      record[5]=rs.getString("AS_name");
      cache.addElement(record);
     } //try sonu
    query.Close();
     fireTableChanged(null);
 }
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 }
 
 public void setAttributeDomain(String q, int i){
 
cache= new Vector();
 try{
   JDBCQuery query=new JDBCQuery(db); 
    ResultSet rs=query.select(q);
    colCount=1;
   headers=new String[colCount];
      headers[0]="Attribute";
   if(i==0)
        headers[0]="Tuple attribute";
   if(i==1)
        headers[0]="Choice attribute";
    while (rs.next())
      {String[] record=new String[colCount];
      record[0]=rs.getString("Att_mnem");
      cache.addElement(record);
     } //try sonu
    query.Close();
     fireTableChanged(null);
 }
    catch(Exception e){
    cache=new Vector();
    e.printStackTrace();
    }
 }
    public String retValue(String s){
        if(s.equals("Y"))return "Yes";
        else return "No";
    }
    
    
    public void setQueryRegExpr(int typeOfEditor,String treeID,String ID2,String id_comp){
    
    cache= new Vector();
    try{
      /*JDBCQuery query=new JDBCQuery(db); 
      JDBCQuery query1=new JDBCQuery(db); 
      ResultSet rs=query.select(q);*/
      colCount=6;
    
      headers=new String[colCount];
      headers[0]="ID";
      headers[1]="Component Type";
      headers[2]="Attribute/Domain Value";
      headers[3]="Function";
      headers[4]="Domain";
      headers[5]="Description";
        /*while (rs.next())
        { String[] record=new String[colCount];
        record[0]=rs.getString(1);
        record[1]=rs.getString(2);
        record[2]=rs.getString(3);
        record[3]=rs.getString(4);
        record[4]=rs.getString(5);
        
       cache.addElement(record);
        } //while sonu
        
        */
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
  
             JDBCQuery query=new JDBCQuery(db);
             JDBCQuery query2=new JDBCQuery(db);
             ResultSet rs,rs1,rs2,rs3;
             //String atts = "";
             //String idp = String.valueOf(TOB);
           
            String[] record=new String[colCount];    
            int no = 1;
             if(typeOfEditor == 0){
                
                record[0]=String.valueOf(no++);
                record[1]="";
                record[2]="value";
                record[3]="";
                record[4]="";
                record[5]="";
                cache.addElement(record);  
                //cmbAddExpr.addItem("value");
                
                record=new String[colCount];
                record[0]=String.valueOf(no++);       //ID
                record[1]="";       //component type
                record[2]="this";   //Attribute/Domain Value
                record[3]="";       //Function
                record[4]="";       //Domain
                record[5]="";       //Description
                cache.addElement(record);  
                //cmbAddExpr.addItem("this");

                     //JDBCQuery query=new JDBCQuery(con);
                     //ResultSet rs1,rs2,; 

                     String dom_ids = "-1";
                     String dom_type = "";               
                 
                     //System.out.println("select * from IISC_Domain where PR_id="+ tree.ID +" and Dom_id= "+ nodeIDSyntax);
                     rs1=query.select("select * from IISC_Domain where PR_id="+ treeID +" and Dom_id= "+ id_comp );
                     boolean ok = false;
                     
                     if(rs1.next()){
                         dom_type = rs1.getString("Dom_type");
                         dom_ids = rs1.getString("Dom_id");
                         if(dom_type != null){
                             String tmpd = rs1.getString("Dom_parent");
                             if(tmpd != null) dom_ids = tmpd;
                             if(tmpd != null && ( dom_type.compareTo("1") == 0 || dom_type.compareTo("4") == 0 )){
                                 while(true){
                                     rs2=query.select("select * from IISC_Domain where PR_id="+ treeID +" and Dom_id = "+ id_comp);
                                     if(rs2.next()){
                                         dom_type = rs2.getString("Dom_type");
                                         if( dom_type != null && ( dom_type.compareTo("1") == 0 || dom_type.compareTo("4") == 0 ) )
                                             dom_ids = rs2.getString("Dom_parent");
                                         else
                                             break;
                                     }
                                     else 
                                         break;
                                 }
                                 
                                 if(rs2 != null)
                                     rs2.close();
                             }
                         }
                     }
                     if ( dom_type != null && (dom_type.compareTo("2") == 0 || dom_type.compareTo("3") == 0)){
                         rs3=query.select("select * " +
                         " from IISC_Dom_Att,IISC_ATTRIBUTE,IISC_DOMAIN " +
                         " where IISC_Dom_Att.PR_id = IISC_ATTRIBUTE.PR_id and " +
                         " IISC_Dom_Att.Att_id = IISC_ATTRIBUTE.Att_id and " +
                         " IISC_ATTRIBUTE.Dom_id = IISC_DOMAIN.Dom_id and IISC_DOMAIN.PR_id = " + treeID + " and " + 
                         " IISC_Dom_Att.PR_id="+ treeID +" and IISC_Dom_Att.Dom_id = "+ id_comp);
                         while(rs3.next()){
                             //String tmps = rs3.getString("Att_mnem").trim()/*.toUpperCase()*/ + " : " + rs3.getString("Dom_mnem");
                             //dom_atts += tmps + ":::";
                             //cmbAddExpr.addItem(tmps);
                             record=new String[colCount];
                             record[0]=String.valueOf(no++);       //ID
                             record[1]="";       //component type
                             record[2]="value."+rs3.getString("Att_mnem").trim();   //Attribute/Domain Value
                             record[3]="";       //Function
                             record[4]=rs3.getString("Dom_mnem");       //Domain
                             record[5]=rs3.getString("Att_comment");       //Description
                             cache.addElement(record);                              
                         }
                         rs3.close();
                     }
                     rs1.close();                    
                 
             }
             else if(typeOfEditor == 2){
                 String idp = String.valueOf(id_comp);
                 while(true){
                     rs = query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE " +
                                         " where PR_id="+ treeID +" and  TF_ID="+ ID2 +" and TOB_id = " + idp);
                                         
                     String nextIDP = null;
                     
                     if( rs.next() )
                         nextIDP = rs.getString("TOB_superord");
                     
                     rs = query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_ATT_TOB,IISC_ATTRIBUTE " +
                                     " where IISC_COMPONENT_TYPE_OBJECT_TYPE.tob_id = IISC_ATT_TOB.tob_id AND " +
                                     " IISC_COMPONENT_TYPE_OBJECT_TYPE.tf_id = IISC_ATT_TOB.tf_id AND " +
                                     " IISC_COMPONENT_TYPE_OBJECT_TYPE.PR_id = IISC_ATT_TOB.PR_id AND " +
                                     " IISC_ATT_TOB.ATT_id = IISC_ATTRIBUTE.ATT_id AND " +
                                     " IISC_ATT_TOB.PR_id = IISC_ATTRIBUTE.PR_id AND " +
                                     " IISC_COMPONENT_TYPE_OBJECT_TYPE.PR_id = "+ treeID +" and " +
                                     " IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_ID = "+ ID2 +" and " +
                                     " IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id = " + idp );
         
                     while( rs.next() ){
                         //String tmps = rs.getString("Att_mnem") + " : " + rs.getString("Tob_mnem") + " ";
                         //cmbAddExpr.addItem(tmps);
                          record=new String[colCount];
                          record[0]=String.valueOf(no++);       //ID
                          record[1]=rs.getString("Tob_mnem");       //component type
                          record[2]=rs.getString("Att_mnem");   //Attribute/Domain Value
                          record[3]="";       //Function
                          record[4]="";       //Domain
                          record[5]=rs.getString("Att_comment");       //Description
                          cache.addElement(record);                           
                     }
                     
                     if(nextIDP == null)
                         break;

                     idp = nextIDP;                    
                 }
             }
             else if(typeOfEditor == 1){
                 rs = query.select("select * " +
                                 " FROM IISC_ATTRIBUTE " +
                                 " where IISC_ATTRIBUTE.PR_id = "+ treeID +" and " +
                                 " IISC_ATTRIBUTE.Att_id = " + id_comp );
                                 //+ " and " + 
                                 //" Att_mnem like '" + tmpEx + "%' ");
                 
                 if( rs.next() ){  
                     //String tmpS = rs.getString("Att_mnem");
                     //cmbAddExpr.addItem(tmpS);
                      record=new String[colCount];
                      record[0]=String.valueOf(no++);       //ID
                      record[1]="";       //component type
                      record[2]=rs.getString("Att_mnem");   //Attribute/Domain Value
                      record[3]="";       //Function
                      record[4]="";       //Domain
                      record[5]=rs.getString("Att_comment");       //Description
                      cache.addElement(record);                          
                 }
             }
             
             
             
             rs = query.select("SELECT * , IISC_FUNCTION.FUN_ID as FID" + 
             "   FROM ((IISC_FUNCTION LEFT JOIN IISC_PACK_FUN ON IISC_FUNCTION.Fun_id = IISC_PACK_FUN.Fun_id) " +
             "   LEFT JOIN IISC_PACKAGE ON IISC_PACK_FUN.Pack_id = IISC_PACKAGE.Pack_id) " +
             "   LEFT JOIN IISC_DOMAIN ON IISC_FUNCTION.Dom_id = IISC_DOMAIN.Dom_id " + 
             "   WHERE IISC_FUNCTION.PR_ID = " + treeID + " AND IISC_DOMAIN.PR_ID = " + treeID +
             "   ORDER BY IISC_PACKAGE.Pack_name,IISC_FUNCTION.Fun_name " );
             
             while( rs.next() ){
                 String packName = rs.getString("Pack_name");
                 String domName = rs.getString("Dom_mnem");
                 String funName = rs.getString("Fun_name");
                 String comm = rs.getString("Fun_comment");
                 String funId = rs.getString("FID");
                 if(packName == null) packName = "";
                 else packName += ".";
                 if(domName == null) domName = "";
                 
                 rs2 = query2.select("   SELECT * " +
                                     "   FROM IISC_FUN_PARAM,IISC_DOMAIN " +
                                     "   WHERE   IISC_FUN_PARAM.Dom_id = IISC_DOMAIN.Dom_id AND " +
                                     "   IISC_FUN_PARAM.PR_ID = " + treeID + " AND " +
                                     "   IISC_FUN_PARAM.Fun_id = " + funId + " AND " +
                                     "   IISC_DOMAIN.PR_ID = " + treeID);
                 String params = " ( ";
                 while(rs2.next())
                     params += rs2.getString("Dom_mnem") + " ,";
                 params = params.substring(0,params.length() - 1);
                 params += ") ";
                 
                 //cmbAddExpr.addItem(packName + funName + params + " : " + domName);
                  record=new String[colCount];
                  record[0]=String.valueOf(no++);       //ID
                  record[1]="";       //component type
                  record[2]="";   //Attribute/Domain Value
                  record[3]=packName + funName + params;       //Function
                  record[4]=domName;       //Domain
                  record[5]=comm;       //Description
                  cache.addElement(record);                  
                 rs2.close();
             }

             rs.close();
 
         
         
         
         
         
         
         
         
         
         
         
        //query.Close();
      fireTableChanged(null);
      } //try sonu
      catch(Exception e){
      cache=new Vector();
      e.printStackTrace();
      }
    } //setQuery sonu    
    
    
}