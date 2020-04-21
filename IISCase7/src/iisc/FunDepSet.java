package iisc;
import java.util.*;
import javax.swing.JOptionPane;
import java.sql.*;
public class FunDepSet 
{ private Connection conn;
  private int ID,pr=-1;
  private Set unique=new HashSet();
  private Set Nonfunmark=new HashSet();
  public FunDepSet(Connection con, int i)
  {
  conn=con;
  ID=i;
  }
   public Set getNonfunmark()
  {
  return Nonfunmark;
  }
  public Set getSet(int log)
  {JDBCQuery query=new JDBCQuery(conn);
  JDBCQuery query1=new JDBCQuery(conn);
  JDBCQuery query2=new JDBCQuery(conn);
  ResultSet rs1,rs,rs2;
   try
    {String str=new String();
     String strfun=new String();
     Set fnfDepSet=new HashSet();
     rs1=query1.select("select * from IISC_APP_SYSTEM where  AS_id="+ID + " " );
    if(rs1.next())
    {
      pr=rs1.getInt("PR_id");
    }
    query1.Close();
     Iterator it=getSystems(ID).iterator();
     while(it.hasNext())str=str+" or  AS_id="+it.next().toString();
    rs1=query1.select("select * from IISC_TF_APPSYS,IISC_FORM_TYPE    where IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.Tf_use=0 and (AS_id="+ID +  str+")" );
    while(rs1.next())
    {int form=rs1.getInt("TF_id"),as=rs1.getInt("AS_id") ;
    String fname= rs1.getString("TF_mnem");
    strfun=strfun+"<b>"+fname+"</b>:<br>";
    unique=new HashSet();
       rs2=query2.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE   where  TF_id="+form +" " );
     while(rs2.next())
     {
     rs=query.select("select count(*) from IISC_ATT_KTO,IISC_ATTRIBUTE  where IISC_ATT_KTO.Att_id=IISC_ATTRIBUTE.Att_id and  IISC_ATTRIBUTE.Att_sbp=1 and TF_id="+form +"   and Tob_id="+rs2.getInt("Tob_id") );
     if(rs.next())
     {
     if(rs.getInt(1)==0)
        {JOptionPane.showMessageDialog(null,"<html><center>Database Schema Design cannot be performed!<br>Keys are not defined for the component type "+rs2.getString("Tob_mnem")+ " from "+fname+ ". ", "DB Schema", JOptionPane.ERROR_MESSAGE);
        return null;
        }
     }
     query.Close();
     }
      query2.Close();
      rs2=query2.select("select * from IISC_UNIQUE_TOB   where  TF_id="+form +" " );
     while(rs2.next())
     {Set uniqueSet=new HashSet();
    rs=query.select("select * from IISC_ATT_UTO,IISC_ATTRIBUTE   where IISC_ATT_UTO.Att_id=IISC_ATTRIBUTE.Att_id and  IISC_ATTRIBUTE.Att_sbp=1 and  TF_id="+form +" and Tob_rbrk="+rs2.getInt("Tob_rbrk")  +" and Tob_id="+rs2.getInt("Tob_id") );
     while(rs.next())
     {
       uniqueSet.add(""+rs.getInt("Att_id"));
     }
     unique.addAll(uniqueSet);
    query.Close();
    }
    query2.Close();
     rs2=query2.select("select * from IISC_KEY_TOB   where  TF_id="+form +" " );
     while(rs2.next())
     {Set uniqueSetadd=new HashSet();
     Set uniqueSetremove=new HashSet();
     int tblocal=rs2.getInt("Tob_local");
    rs=query.select("select * from IISC_ATT_KTO,IISC_ATTRIBUTE   where IISC_ATT_KTO.Att_id=IISC_ATTRIBUTE.Att_id and  IISC_ATTRIBUTE.Att_sbp=1 and  TF_id="+form +" and Tob_rbrk="+rs2.getInt("Tob_rbrk")  +" and Tob_id="+rs2.getInt("Tob_id") );
     while(rs.next())
     { int att=rs.getInt("Att_id");
      if(ID==as || tblocal==0)
      uniqueSetremove.add(""+att);
      else
      uniqueSetadd.add(""+att);
     }
     unique.removeAll(uniqueSetremove);
     unique.addAll(uniqueSetadd);
    query.Close();
    }
    query2.Close();
    rs=query.select("select * from IISC_ATT_KTO   where  TF_id="+form +" " );
     while(rs.next())
     {
       unique.remove(""+rs.getInt("Att_id"));
     }
    query.Close();
    rs=query.select("select * from IISC_ATT_TOB,IISC_COMPONENT_TYPE_OBJECT_TYPE   where IISC_ATT_TOB.Tob_id=IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_id="+form +" and Tob_superord is null" );
      if(rs.next())
    {  int k=rs.getInt("Tob_id");
      Set fSet=new HashSet();
      Set kSet=new HashSet();
      if(ID==as)
     {fSet=getFun(k,new HashSet(),form,0);
      kSet=getNFun(k,new HashSet(),form,0); } 
      else
     { fSet=getFun(k,new HashSet(),form,1);
      kSet=getNFun(k,new HashSet(),form,1); } 
      it=fSet.iterator();
      if(kSet.size()>1 || fSet.size()>1)
      eliminisi(kSet);
       while(it.hasNext())
     {Set pomm=new HashSet();
      FunDep lpom=(FunDep)it.next();  
      pomm.addAll(lpom.left);
      pomm.removeAll(unique);
      if(pomm.size()<lpom.left.size())
      lpom.type=false;
    }
    // System.out.println("Nefun"  + kSet.toString());
        
     
     it=kSet.iterator();
     k=Nonfunmark.size()+1;
     while(it.hasNext())
     {Set lpom=(Set)it.next(); 
      Set pom=new HashSet();
      pom.add("T"+ k);
      Nonfunmark.add("T"+ k);
      k++;
      Set pomm=new HashSet();
      pomm.addAll(lpom);
      pomm.removeAll(unique);
      if(pomm.size()<lpom.size())
      fSet.add(new FunDep(lpom,pom,false,conn,false));
      else
      fSet.add(new FunDep(lpom,pom,false,conn,true));
     }
    it=fSet.iterator();
    while(it.hasNext())
    strfun=strfun+((FunDep)it.next()).getPrint()+"<br>";
    fnfDepSet.addAll(fSet);
    }
    query.Close();
    strfun=strfun+"<br>";
    }
    query1.Close();
     rs1=query1.select("select * from IISC_APP_SYS_REFERENCE,IISC_FORM_TYPE    where IISC_APP_SYS_REFERENCE.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.Tf_use=0 and AS_id="+ID  );
    while(rs1.next())
    {int form=rs1.getInt("TF_id");
    int fname=rs1.getInt("TF_mnem");
    strfun=strfun+"<b>"+fname+"</b>:<br>";
     Set unique=new HashSet();
      rs2=query2.select("select * from IISC_UNIQUE_TOB   where  TF_id="+form +" " );
     while(rs2.next())
     {Set uniqueSet=new HashSet();
    rs=query.select("select * from IISC_ATT_UTO,IISC_ATTRIBUTE   where IISC_ATT_UTO.Att_id=IISC_ATTRIBUTE.Att_id and  IISC_ATTRIBUTE.Att_sbp=1 and    where  TF_id="+form +" and Tob_rbrk="+rs2.getInt("Tob_rbrk")  +" and Tob_id="+rs2.getInt("Tob_id") );
     while(rs.next())
     {
       uniqueSet.add(""+rs.getInt("Att_id"));
     }
     unique.addAll(uniqueSet);
    query.Close();
    }
    query2.Close();
     rs2=query2.select("select * from IISC_KEY_TOB   where  TF_id="+form +" " );
     while(rs2.next())
     {Set uniqueSetadd=new HashSet();
     Set uniqueSetremove=new HashSet();
     int tblocal=rs2.getInt("Tob_local");
    rs=query.select("select * from IISC_ATT_KTO   where  TF_id="+form +" and Tob_rbrk="+rs2.getInt("Tob_rbrk")  +" and Tob_id="+rs2.getInt("Tob_id") );
     while(rs.next())
     { int att=rs.getInt("Att_id");
      
      uniqueSetremove.add(""+att);
     }
     unique.removeAll(uniqueSetremove);
     unique.addAll(uniqueSetadd);
    query.Close();
    }
    query2.Close();
    rs=query.select("select * from IISC_ATT_TOB,IISC_COMPONENT_TYPE_OBJECT_TYPE   where IISC_ATT_TOB.Tob_id=IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_id="+form +" and Tob_superord is null" );
      if(rs.next())
    {  int k=rs.getInt("Tob_id");
      
      Set fSet=getFun(k,new HashSet(),form,1);
      it=fSet.iterator();
       
     while(it.hasNext())
     {Set pomm=new HashSet();
      FunDep lpom=(FunDep)it.next();  
      pomm.addAll(lpom.left);
      pomm.removeAll(unique);
      if(pomm.size()<lpom.left.size())
      lpom.type=false;
    }
      Set kSet=getNFun(k,new HashSet(),form,1); 
      if(kSet.size()>1 || fSet.size()>1)
      eliminisi(kSet);
    // System.out.println("Nefun"  + kSet.toString());
     it=kSet.iterator();
     k=1;
     while(it.hasNext())
     {Set lpom=(Set)it.next();  
      Set pom=new HashSet();
      pom.add("T"+ k);
      Nonfunmark.add("T"+ k);
      k++;
      fSet.add(new FunDep(lpom,pom,false,conn,true));
    }
    it=fSet.iterator();
    while(it.hasNext())
    strfun=strfun+((FunDep)it.next()).getPrint()+"<br>";
    fnfDepSet.addAll(fSet);
    }
    query.Close();
    strfun=strfun+"<br>";
    }
    query1.Close();
    it=fnfDepSet.iterator();
    while(it.hasNext())
    {
      FunDep f=(FunDep)it.next();
      if(!f.type)
      {  Iterator it1=fnfDepSet.iterator();
          while(it1.hasNext())
          {FunDep fpom=(FunDep)it1.next();
           if(fpom.type && f.left.containsAll(fpom.left) && fpom.left.containsAll(f.left) && f.right.containsAll(fpom.right) && fpom.right.containsAll(f.right))  
           {
             it.remove();
             break;
           }
          }
         
      }
    }
    query.update("update IISC_COLLISION_LOG set CL_obsolete=1 where CL_type>=10 and CL_type<20 and AS_id="+ID);
    if(log==1)
    {
    rs=query.select("select max(CL_id)+1  from IISC_COLLISION_LOG");
    int idl=0;
    if(rs.next())
    idl=rs.getInt(1);
    query.Close();
    query.update("insert into IISC_COLLISION_LOG(CL_id,PR_id,AS_id,CL_type,CL_date,CL_text,CL_warning,CL_obsolete) values ("+ idl +","+ pr  +","+ ID +",10,'" + ODBCList.now() + "','" + strfun + "',0,0)");
    }
    return fnfDepSet;
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
   return null;
  }
  
   public  boolean eliminisi(Set F)
  {Iterator it=F.iterator();
  while(it.hasNext() )
 {
Set b=new HashSet();
b=(Set)(it.next());
if(b.size()==1)
{F.remove(b);
it=F.iterator();}
  }
  return true;
  }
  
   public static Set getUnion(Set s, Set s1)
   {    Set s2=new HashSet();
     if(!s1.isEmpty())   
        {
        Iterator it=s1.iterator();
        while(it.hasNext())
            {
             Iterator it1=s.iterator();
             Set pr=(Set)it.next();
        while(it1.hasNext())
            {
            Set pom=new HashSet();
            pom.addAll(pr); 
            pom.addAll((Set)it1.next()); 
            s2.add(pom);
            }       
            }
        }
        else
        s2.addAll(s);
        return s2;
   }
    public Set getSystems(int i)
   { JDBCQuery query=new JDBCQuery(conn);
  JDBCQuery query1=new JDBCQuery(conn);
  ResultSet rs1,rs;
   try
    {Set app=new HashSet();
    rs1=query1.select("select * from IISC_APP_SYSTEM_CONTAIN   where AS_id="+i +" " );
    while(rs1.next())
    {int j=rs1.getInt("AS_id_con");
    app.add(""+j);
    app.addAll(getSystems(j));
    }
    query1.Close();
     return app;
    }
   
    catch(SQLException e)
    {
      e.printStackTrace();
      return null;
    }
   
   
   }
   public Set getNFun(int i, Set s, int form,int local)
    { Set kSet=new HashSet();
    String s2=new String();
    try
    { //System.out.println("set "+i + "  "  + s);
    //   if(local==1)
    //  s2=" and Tob_local=0 ";
      JDBCQuery query=new JDBCQuery(conn);
      JDBCQuery query1=new JDBCQuery(conn);
      JDBCQuery query2=new JDBCQuery(conn);
      ResultSet rs1,rs,rs2;
      rs1=query.select("select count(*) from IISC_KEY_TOB where TF_id="+form +" and  Tob_id="+ i +" "+ s2 +"" );
      if(rs1.next())
      {Set s1=new HashSet();
      Set KeySet=new HashSet();
       Set KeySet1=new HashSet();
      query.Close();
      rs1=query.select("select  Tob_rbrk  from IISC_KEY_TOB where  TF_id="+form +" and   Tob_id="+ i +""+ s2 +" order by  Tob_rbrk " );
      while(rs1.next())
      { 
      int br=rs1.getInt("Tob_rbrk");
      Set keypom=new HashSet();
      rs=query1.select("select * from IISC_ATT_KTO,IISC_ATTRIBUTE   where IISC_ATT_KTO.Att_id=IISC_ATTRIBUTE.Att_id and  IISC_ATTRIBUTE.Att_sbp=1 and TF_id="+form +" and  Tob_id="+ i +" and Tob_rbrk="+br );
      while(rs.next())
      {String si=rs.getString("Att_id");
      keypom.add(si);
      }
      query1.Close();
      s1.addAll(s);
      if(KeySet.isEmpty())
      KeySet1.add(keypom);
      KeySet.add(keypom);
      
      }
     query.Close();
        rs1=query.select("select count(*) from IISC_COMPONENT_TYPE_OBJECT_TYPE where  TF_id="+form +" and   Tob_superord="+ i +"" );
       rs1.next();
      if(rs1.getInt(1)==0) 
      {kSet.addAll(getUnion(KeySet,s1));
      query.Close(); 
      }
      else
      {query.Close(); 
     
       rs1=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where  TF_id="+form +" and   Tob_superord="+ i +"" );  
      while(rs1.next())
      { 
      //System.out.println("key "+i + "  "  + KeySet.toString());
      kSet.addAll(getNFun(rs1.getInt("Tob_id"),getUnion(KeySet1,s1),form,local)); 
      } 
      query.Close();
      }

      }
    else
    query.Close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
       return kSet;
    }
     return kSet;
  }
  public Set getFun(int i, HashSet s, int form, int local)
    {Set funSet=new HashSet(); 
    try
    { 
      FunDep fun=new FunDep();
      Set attrib=new HashSet();
      Set has=new HashSet();
      Set keySet=new HashSet();
      JDBCQuery query=new JDBCQuery(conn);
      JDBCQuery query1=new JDBCQuery(conn);
      ResultSet rs1,rs;
      String s1=new String();
      rs1=query.select("select * from IISC_ATT_TOB,IISC_ATTRIBUTE   where IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id and  IISC_ATTRIBUTE.Att_sbp=1 and TF_id="+form +" and   Tob_id="+ i +"" );
      while(rs1.next())attrib.add(rs1.getString("Att_id"));
    // System.out.println("Atributi " + attrib.toString());
      query.Close();
     // if(local==1)
     // s1=" and Tob_local=0 ";
      rs1=query.select("select count(*) from IISC_KEY_TOB where  TF_id="+form +" and   Tob_id="+ i +" "+s1 );
      if(rs1.next())
      {
      query.Close();
        Set pr=new HashSet();
        Iterator it=unique.iterator();
         while(it.hasNext())
          {Object el=it.next();
         if(s.contains(el))pr.add(el);
          }
      if(pr.isEmpty())
      {
      rs1=query.select("select * from IISC_KEY_TOB where  TF_id="+form +" and   Tob_id="+ i + s1 +" order by Tob_rbrk ");
      while(rs1.next())
      { 
      int br=rs1.getInt("Tob_rbrk");
      int lc=rs1.getInt("Tob_local");
      Set keypom=new HashSet();
      rs=query1.select("select * from IISC_ATT_KTO,IISC_ATTRIBUTE   where IISC_ATT_KTO.Att_id=IISC_ATTRIBUTE.Att_id and  IISC_ATTRIBUTE.Att_sbp=1 and TF_id="+form +" and   Tob_id="+ i +" and Tob_rbrk="+br );
      while(rs.next())keypom.add(rs.getString("Att_id"));
      if(keySet.isEmpty())
      keySet.add(keypom);
       query1.Close();
        it=attrib.iterator();
        Set pomfunSet=new HashSet();
        Set pom1=new HashSet();
         pom1.addAll(keypom);
        pom1.addAll(s);
        while(it.hasNext())
       {
         Set pom=new HashSet();
         pom.add(it.next());
         
         if(!pom1.isEmpty() &&!(pom1.containsAll(pom))  && (!has.contains(pom)))
         {fun=new FunDep(pom1,pom,true,conn,true);
         if(local==1 && lc==1)fun.type=false;
         fun.print();
         if(fun.type || !funSet.contains(new FunDep(pom1,pom,true,conn,true)));
        { pomfunSet.add(fun);
         has.add(pom);}
         if(fun.type)
        funSet.remove(new FunDep(pom1,pom,true,conn,false));
         }      
       }
       if(pomfunSet.isEmpty())
        {Iterator itk=((Set)keySet.iterator().next()).iterator();
         while(!pom1.isEmpty() && itk.hasNext())
        {Set poms=new HashSet();
        poms.add(itk.next());
        if(!pom1.containsAll(poms))
        {
        fun=new FunDep(pom1,poms,true,conn,true);
         if(local==1 && lc==1)fun.type=false;
        if(fun.type || !funSet.contains(new FunDep(pom1,poms,true,conn,true)))
        funSet.add(fun);
        if(fun.type)
        funSet.remove(new FunDep(pom1,poms,true,conn,false));
        }
        }
        }
         else
         
         funSet.addAll(pomfunSet);
      }
        query.Close();}
            rs1=query.select("select * from IISC_UNIQUE_TOB where  TF_id="+form +" and   Tob_id="+ i + s1 +" order by Tob_rbrk ");
      while(rs1.next())
      { 
      int br=rs1.getInt("Tob_rbrk");
      Set keypom=new HashSet();
      rs=query1.select("select * from IISC_ATT_UTO,IISC_ATTRIBUTE   where IISC_ATT_UTO.Att_id=IISC_ATTRIBUTE.Att_id and  IISC_ATTRIBUTE.Att_sbp=1 and  TF_id="+form +" and   Tob_id="+ i +" and Tob_rbrk="+br );
      while(rs.next())keypom.add(rs.getString("Att_id"));
     //  if(keySet.size()<2)
     // keySet.add(keypom);
       query1.Close();
        it=attrib.iterator();
        Set pomfunSet=new HashSet();
        Set pom1=new HashSet();
         pom1.addAll(keypom);
        pom1.addAll(s);
        while(it.hasNext())
       {
         Set pom=new HashSet();
         pom.add(it.next());
         
         if(!pom1.isEmpty() && !(pom1.containsAll(pom))  && (!has.contains(pom)))
         {fun=new FunDep(pom1,pom,true,conn,false);
         fun.print();
         if(!funSet.contains(new FunDep(pom1,pom,true,conn,true)))
         {pomfunSet.add(fun);
         has.add(pom);}
         }      
       }
       if(pomfunSet.isEmpty())
        {Iterator itk=((Set)keySet.iterator().next()).iterator();
         while(!pom1.isEmpty() && itk.hasNext())
        {Set poms=new HashSet();
        poms.add(itk.next());
        if(!pom1.containsAll(poms))
        {
         if(!funSet.contains(new FunDep(pom1,poms,true,conn,true)))
        funSet.add(new FunDep(pom1,poms,true,conn,false));
        }
        }
        }
         else
         funSet.addAll(pomfunSet);
      }
        query.Close();
        rs1=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where   TF_id="+form +" and  Tob_superord="+ i +"" );
      while(rs1.next())
      {
       it=keySet.iterator();
       int f = rs1.getInt("Tob_id");
       while(it.hasNext())
      { HashSet kk=(HashSet)it.next();
        kk.addAll(s);
      funSet.addAll(getFun(f,kk,form,local)); 
      }
      }
       query.Close();
      }
      else
      query.Close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
       return funSet;
    }
     return funSet;
  }
}