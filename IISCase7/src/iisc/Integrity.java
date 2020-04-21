package iisc;
import java.util.*;
import java.sql.*;
public class Integrity 
{
public int type;
public int id;
public String name;
public Set left;
public Set right;
public Set leftatt;
public Set rightatt;
//public Set leftcondition=new HashSet();
//public Set rightcondition=new HashSet();
  public Integrity(int t,Set l, Set r,Set latt, Set ratt)
  {
  type=t;
  left=l;
  right=r;
  leftatt=latt;
  rightatt=ratt;
  }
  public boolean exists(int pr, int as, Connection con)
  {
      JDBCQuery query=new JDBCQuery(con);
     JDBCQuery query1=new JDBCQuery(con);
     ResultSet rs,rs1;   
      Set r1=new HashSet();
    Set l1=new HashSet();
    Iterator it=right.iterator();
    while(it.hasNext())
    r1.add(""+((RelationScheme)it.next()).id);
    it=left.iterator();
    while(it.hasNext())
    l1.add(""+((RelationScheme)it.next()).id);
    try {  rs=query.select("select *  from IISC_RS_Constraint where PR_id=" + pr + " and  AS_id=" + as+ " and RSC_type="+type);
   while(rs.next())
    {int idIntegrity=rs.getInt("RSC_id");
    Set latt=new HashSet();

    Set ratt=new HashSet();
    Set l=new HashSet();
    Set  r=new HashSet();
    rs1=query1.select("select * from IISC_RS_Constraint,IISC_RSC_LHS_RHS where IISC_RS_Constraint.LHS_id=IISC_RSC_LHS_RHS.RSCLR_id and  IISC_RS_Constraint.RSC_id="+ idIntegrity +" and IISC_RS_Constraint.PR_id="+ pr +" and IISC_RS_Constraint.AS_id="+as);
      while(rs1.next())
      {
       latt.add(rs1.getString("Att_id")) ;
      }
    query1.Close();
     rs1=query1.select("select * from IISC_RS_Constraint,IISC_RSC_LHS_RHS where IISC_RS_Constraint.RHS_id=IISC_RSC_LHS_RHS.RSCLR_id and  IISC_RS_Constraint.RSC_id="+ idIntegrity +" and IISC_RS_Constraint.PR_id="+ pr +" and IISC_RS_Constraint.AS_id="+as);
      while(rs1.next())
      {
       ratt.add(rs1.getString("Att_id")) ;
      }
    query1.Close();
      rs1=query1.select("select  RS_id from IISC_RSC_RS_SET,IISC_RS_Constraint where IISC_RS_Constraint.LHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id  and  IISC_RS_Constraint.PR_ID="+ pr +" and IISC_RS_Constraint.AS_id="+as +" and IISC_RS_Constraint.RSC_id="+idIntegrity);
      while(rs1.next())
      {
       l.add(""+rs1.getInt("RS_id")) ;
      }
    query1.Close();
     rs1=query1.select("select  RS_id from IISC_RSC_RS_SET,IISC_RS_Constraint  where IISC_RS_Constraint.RHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id and  IISC_RS_Constraint.PR_ID="+ pr +" and IISC_RS_Constraint.AS_id="+as +" and IISC_RS_Constraint.RSC_id="+idIntegrity);
      while(rs1.next())
      {
       r.add(""+rs1.getInt("RS_id")) ;
      }
    query1.Close();
    if(r.containsAll(r1) && r1.containsAll(r) && l.containsAll(l1) && l1.containsAll(l) && ratt.containsAll(rightatt) && rightatt.containsAll(ratt) && latt.containsAll(leftatt) && leftatt.containsAll(latt) )
    return true;
    }
    query.Close();
      }
catch(SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  public boolean isContained(int pr, int as, Connection con)
  {
      JDBCQuery query=new JDBCQuery(con);
     JDBCQuery query1=new JDBCQuery(con);
     ResultSet rs,rs1;   
      Set r1=new HashSet();
    Set l1=new HashSet();
    Iterator it=right.iterator();
    while(it.hasNext())
    r1.add(""+((RelationScheme)it.next()).id);
    it=left.iterator();
    while(it.hasNext())
    l1.add(""+((RelationScheme)it.next()).id);
    try {  rs=query.select("select *  from IISC_RS_Constraint where PR_id=" + pr + " and  AS_id=" + as+ " and RSC_type="+type);
   while(rs.next())
    {int idIntegrity=rs.getInt("RSC_id");
    Set latt=new HashSet();

    Set ratt=new HashSet();
    Set l=new HashSet();
    Set  r=new HashSet();
    rs1=query1.select("select * from IISC_RS_Constraint,IISC_RSC_LHS_RHS where IISC_RS_Constraint.LHS_id=IISC_RSC_LHS_RHS.RSCLR_id and  IISC_RS_Constraint.RSC_id="+ idIntegrity +" and IISC_RS_Constraint.PR_id="+ pr +" and IISC_RS_Constraint.AS_id="+as);
      while(rs1.next())
      {
       latt.add(rs1.getString("Att_id")) ;
      }
    query1.Close();
     rs1=query1.select("select * from IISC_RS_Constraint,IISC_RSC_LHS_RHS where IISC_RS_Constraint.RHS_id=IISC_RSC_LHS_RHS.RSCLR_id and  IISC_RS_Constraint.RSC_id="+ idIntegrity +" and IISC_RS_Constraint.PR_id="+ pr +" and IISC_RS_Constraint.AS_id="+as);
      while(rs1.next())
      {
       ratt.add(rs1.getString("Att_id")) ;
      }
    query1.Close();
      rs1=query1.select("select  RS_id from IISC_RSC_RS_SET,IISC_RS_Constraint where IISC_RS_Constraint.LHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id  and  IISC_RS_Constraint.PR_ID="+ pr +" and IISC_RS_Constraint.AS_id="+as +" and IISC_RS_Constraint.RSC_id="+idIntegrity);
      while(rs1.next())
      {
       l.add(""+rs1.getInt("RS_id")) ;
      }
    query1.Close();
     rs1=query1.select("select  RS_id from IISC_RSC_RS_SET,IISC_RS_Constraint  where IISC_RS_Constraint.RHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id and  IISC_RS_Constraint.PR_ID="+ pr +" and IISC_RS_Constraint.AS_id="+as +" and IISC_RS_Constraint.RSC_id="+idIntegrity);
      while(rs1.next())
      {
       r.add(""+rs1.getInt("RS_id")) ;
      }
    query1.Close();
    if(r.containsAll(r1)  && l.containsAll(l1)  && ratt.containsAll(rightatt) && rightatt.containsAll(ratt) && latt.containsAll(leftatt) && leftatt.containsAll(latt) )
    return true;
    }
    query.Close();
      }
catch(SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public int Contanes(int pr, int as, Connection con)
  {
      JDBCQuery query=new JDBCQuery(con);
     JDBCQuery query1=new JDBCQuery(con);
     ResultSet rs,rs1;   
      Set r1=new HashSet();
    Set l1=new HashSet();
    Iterator it=right.iterator();
    while(it.hasNext())
    r1.add(""+((RelationScheme)it.next()).id);
    it=left.iterator();
    while(it.hasNext())
    l1.add(""+((RelationScheme)it.next()).id);
    try {  rs=query.select("select *  from IISC_RS_Constraint where PR_id=" + pr + " and  AS_id=" + as+ " and RSC_type="+type);
   while(rs.next())
    {int idIntegrity=rs.getInt("RSC_id");
    Set latt=new HashSet();

    Set ratt=new HashSet();
    Set l=new HashSet();
    Set  r=new HashSet();
    rs1=query1.select("select * from IISC_RS_Constraint,IISC_RSC_LHS_RHS where IISC_RS_Constraint.LHS_id=IISC_RSC_LHS_RHS.RSCLR_id and  IISC_RS_Constraint.RSC_id="+ idIntegrity +" and IISC_RS_Constraint.PR_id="+ pr +" and IISC_RS_Constraint.AS_id="+as);
      while(rs1.next())
      {
       latt.add(rs1.getString("Att_id")) ;
      }
    query1.Close();
     rs1=query1.select("select * from IISC_RS_Constraint,IISC_RSC_LHS_RHS where IISC_RS_Constraint.RHS_id=IISC_RSC_LHS_RHS.RSCLR_id and  IISC_RS_Constraint.RSC_id="+ idIntegrity +" and IISC_RS_Constraint.PR_id="+ pr +" and IISC_RS_Constraint.AS_id="+as);
      while(rs1.next())
      {
       ratt.add(rs1.getString("Att_id")) ;
      }
    query1.Close();
      rs1=query1.select("select  RS_id from IISC_RSC_RS_SET,IISC_RS_Constraint where IISC_RS_Constraint.LHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id  and  IISC_RS_Constraint.PR_ID="+ pr +" and IISC_RS_Constraint.AS_id="+as +" and IISC_RS_Constraint.RSC_id="+idIntegrity);
      while(rs1.next())
      {
       l.add(""+rs1.getInt("RS_id")) ;
      }
    query1.Close();
     rs1=query1.select("select  RS_id from IISC_RSC_RS_SET,IISC_RS_Constraint  where IISC_RS_Constraint.RHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id and  IISC_RS_Constraint.PR_ID="+ pr +" and IISC_RS_Constraint.AS_id="+as +" and IISC_RS_Constraint.RSC_id="+idIntegrity);
      while(rs1.next())
      {
       r.add(""+rs1.getInt("RS_id")) ;
      }
    query1.Close();
    if( r1.containsAll(r) &&  l1.containsAll(l) && ratt.containsAll(rightatt) && rightatt.containsAll(ratt) && latt.containsAll(leftatt) && leftatt.containsAll(latt) )
    return idIntegrity;
    }
    query.Close();
      }
catch(SQLException e)
    {
      e.printStackTrace();
    }
    return -1;
  }
  public void save(int pr, int as, Connection con)
  {  
     JDBCQuery query=new JDBCQuery(con);
     JDBCQuery query1=new JDBCQuery(con);
     ResultSet rs,rs1;
      name=new String();
    try {
    int containedId=Contanes(pr,as,con);
    rs=query.select("select *  from IISC_RS_Constraint where PR_id=" + pr + " and  AS_id=" + as+ " and RSC_id="+containedId);
   while(rs.next())
   {
   int lhs=rs.getInt("LHS_id");
   int rhs=rs.getInt("RHS_id");
   int lhsSet=rs.getInt("LHS_RS_Set_id");
   int rhsSet=rs.getInt("RHS_RS_Set_id");
    query.update("delete  from IISC_RSC_RS_SET where RS_Set_id="+lhsSet+" or RS_Set_id="+rhsSet); 
    query.update("delete  from IISC_RSC_LHS_RHS where RSCLR_id="+lhs+" or RSCLR_id="+rhs);
    query.update("delete  from IISC_RS_Constraint where RSC_id="+containedId);
   }
   query.Close();
   
     rs1=query.select("select RSCT_short  from IISC_RSC_TYPE where RSCT_id="+type);
     if(rs1.next())name=rs1.getString(1);
     query.Close();
      rs1=query.select("select max(RS_Set_id)+1  from IISC_RSC_RS_SET");
     int lhssid;
      if(rs1.next())
        lhssid=rs1.getInt(1);
      else 
        lhssid=0;
      query.Close();
      Iterator it=left.iterator();
      int br=0;
      while(it.hasNext())
   {RelationScheme rel=(RelationScheme)it.next();
   name=name +"_"+rel.name;
   query.update("insert into IISC_RSC_RS_SET(RS_Set_id,PR_id,AS_id,RS_id,RS_order) values ("+ lhssid +","+ pr +","+ as +"," + rel.id + "," + br + ")"); 
   br++;
   }
     int rhssid=lhssid+1;
      it=right.iterator();
      br=0;
      while(it.hasNext())
  {RelationScheme rel=(RelationScheme)it.next();
   name=name +"_"+rel.name;
   query.update("insert into IISC_RSC_RS_SET(RS_Set_id,PR_id,AS_id,RS_id,RS_order) values ("+ rhssid +","+ pr +","+ as +"," + rel.id + "," + br + ")"); 
   br++;
  }
  boolean go=true;
  int k=0;
  while(go)
     {if(k==0)
     rs1=query1.select("select * from IISC_RS_CONSTRAINT  where RSC_desc='"+ name+ "' and PR_id="+ pr);
     else
     rs1=query1.select("select * from IISC_RS_CONSTRAINT  where RSC_desc='"+ name+ "_" + k +"' and PR_id="+ pr);
     k++;
     if(!rs1.next())
     {query1.Close();
     go=false;
     }
     else
     query1.Close();
     }
  if(k>1)
  name=name+ "_" + (k-1);
     rs1=query.select("select max(RSCLR_id)+1  from IISC_RSC_LHS_RHS");
     int lhsid;
      if(rs1.next())
        lhsid=rs1.getInt(1);
      else 
        lhsid=0;
      query.Close();
      it=leftatt.iterator();
       int  i=1;
      while(it.hasNext())
    {query.update("insert into IISC_RSC_LHS_RHS(RSCLR_id,Att_id,PR_id,AS_id,Att_rbr) values ("+ lhsid +"," + it.next().toString() + ","+ pr +","+ as +","+ i +")"); 
      i++;
    }
    int rhsid=lhsid+1;
      it=rightatt.iterator();
     i=1;
      while(it.hasNext())
    {query.update("insert into IISC_RSC_LHS_RHS(RSCLR_id,Att_id,PR_id,AS_id,Att_rbr) values ("+ rhsid +"," + it.next().toString() + ","+ pr +","+ as +","+ i +")"); 
     i++;
    }
     rs1=query.select("select max(RSC_id)+1  from IISC_RS_CONSTRAINT");
      if(rs1.next())
        id=rs1.getInt(1);
      else 
        id=0;
      query.Close();
   String itype=new String("NULL");
   if(type==0 || type==1 || type==2 || type==3)itype="0";
    query.update("insert into IISC_RS_CONSTRAINT(RSC_id,PR_id,AS_id,RSC_name,RSC_desc,LHS_id,RHS_id,LHS_RS_Set_id,RHS_RS_Set_id,RSC_type,RSC_rel_int_type,RSC_deferrable,RSC_initially) values ("+ id +","+ pr +","+ as +",'"+name+"',''," + lhsid + "," + rhsid + "," + lhssid + "," + rhssid + "," + type + ","+itype +",0,0)"); 
    
   it=left.iterator();
    while(it.hasNext())
   {RelationScheme rel=(RelationScheme)it.next();
    query.update("insert into IISC_RSC_action(RSC_id,PR_id,AS_id,RS_id,RSC_lhs_rhs,RSC_ins_action,RSC_mod_action,RSC_del_action) values("+ id +","+ pr +","+as + ","+ rel.id +",0,-1,-1,NULL)");
   }
   it=right.iterator();
    while(it.hasNext())
   {RelationScheme rel=(RelationScheme)it.next();
       query.update("insert into IISC_RSC_action(RSC_id,PR_id,AS_id,RS_id,RSC_lhs_rhs,RSC_ins_action,RSC_mod_action,RSC_del_action) values("+ id +","+ pr +","+as + ","+ rel.id +",1,NULL,-1,-1)");
   }
/* it=leftcondition.iterator();
int idk=0;
 while(it.hasNext())
   {
   rs1=query.select("select max(LC_id)+1  from IISC_LOGICAL_CONDITION");
      if(rs1.next())
        idk=rs1.getInt(1);
      else 
        idk=0;
      query.Close();
   query.update("insert into IISC_LOGICAL_CONDITION values ("+ idk +","+ pr +","+ as +"," + lhsid + ",'" + it.next().toString() + "')");  
   }
   it=rightcondition.iterator();
 while(it.hasNext())
   {
   rs1=query.select("select max(LC_id)+1  from IISC_LOGICAL_CONDITION");
      if(rs1.next())
        idk=rs1.getInt(1);
      else 
        idk=0;
      query.Close();
   query.update("insert into IISC_LOGICAL_CONDITION values ("+ idk +","+ pr +","+ as +"," + rhsid + ",'" + it.next().toString() + "')");  
   }
 */
    if(type==0 || type==1 )
 {
 it=left.iterator();
 String qstr= "RS_superior=" + ((RelationScheme)(it.next())).id ;
 while(it.hasNext())
qstr= "RS_superior=" + ((RelationScheme)(it.next())).id + " or "+qstr; 
  query.update("update IISC_GRAPH_CLOSURE set RSC_id="+ id +" where PR_id="+ pr +" and AS_id="+ as +"  and RS_inferior=" +  ((RelationScheme)(right.iterator().next())).id + " and ("+ qstr +") and GC_edge_type=0 "); }
  boolean no=false;
  rs=query.select("select *  from IISC_GRAPH_CLOSURE where RSC_id="+ id);
  if(rs.next())
  no=true;
  query.Close();
  if((type==0 && !no) || type==2 || type==4 || type==6)
 {
 rs=query.select("select max(GC_id)+1  from IISC_GRAPH_CLOSURE");
  int idl,t=2;
  if(type==0)t=0;
  if(type==2)t=1;
  if(rs.next())
  idl=rs.getInt(1);
  else 
  idl=0;
  query.Close();
  query.update("insert into IISC_GRAPH_CLOSURE(GC_id,PR_id,AS_id,RSC_id,RS_superior,RS_inferior,GC_edge_type) values ("+ idl +","+ pr +","+ as +","+ id +"," +  ((RelationScheme)(left.iterator().next())).id + "," + ((RelationScheme)(right.iterator().next())).id + ","+t+")"); }
    }

catch(SQLException e)
    {
      e.printStackTrace();
    }
  }
}