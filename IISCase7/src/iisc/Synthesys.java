package iisc;

import iisc.smtsolver.CVC3Checker;
import iisc.smtsolver.MathSAT5Checker;

import java.util.*;
import java.sql.*;
public class Synthesys 
{ public int  pr;
  public int  as;
  public Connection con;
  public Synthesys()
  {
  }
public  String[] splitString(String theString, String theDelimiter)
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

public RelationScheme getRelationScheme(String id, Set SR)
{
    Iterator its=SR.iterator();
    RelationScheme r=new RelationScheme();
    while(its.hasNext())
    {RelationScheme rel=(RelationScheme)its.next();
     if ((""+rel.id).equals(id))
     {
     r=rel;
     break;
     }
    }
    return r;
}
public String getPrintRS(Set RS)
{try{
   JDBCQuery query=new JDBCQuery(con);
   JDBCQuery query1=new JDBCQuery(con);
   String str="";
   Iterator it=RS.iterator();
   while(it.hasNext())
   {    
     RelationScheme rel=(RelationScheme)it.next();
     String[] attr,keys,uniques;
     String a="",k="",u="";
     int i=rel.atributi.size();
     attr=new String[i];
     i=rel.kljuc.size();
     keys=new String[i];
     i=rel.unique.size();
     uniques=new String[i];
     i=0;
     Iterator it1=rel.atributi.iterator();
      while(it1.hasNext())
      {
        a=a+getAttribute((new Integer(""+it1.next())).intValue());
        if(it1.hasNext())a=a+", ";
        i++;
      }
       it1=rel.kljuc.iterator();
       i=0;
      while(it1.hasNext())
      { Iterator it2=((Set)it1.next()).iterator();
         while(it2.hasNext())
         {
         if(keys[i]==null)
         keys[i]=getAttribute((new Integer(""+it2.next())).intValue());
         else
         keys[i]=keys[i]+"+"+getAttribute((new Integer(""+it2.next())).intValue());
         }
         k=k+keys[i];
         if(it1.hasNext())k=k+", ";
         i++;
      }
      i=0;
      it1=rel.unique.iterator();
      while(it1.hasNext())
      { Iterator it2=((Set)it1.next()).iterator();
         while(it2.hasNext())
         {
         if(uniques[i]==null)
         uniques[i]=getAttribute((new Integer(""+it2.next())).intValue());
         else
         uniques[i]=uniques[i]+"+"+getAttribute((new Integer(""+it2.next())).intValue());
         }
         u=u+uniques[i];
          if(it1.hasNext())u=u+", ";
         i++;
      }
      
     str=str+"<b>"+rel.name+"</b>{{"+ a +"},{"+ k +"},{"+ u +"}}<br>";
   }
   return str;
 }
catch(Exception e)
    {
      e.printStackTrace();
    } 
    return null;
}
public String getHomonyms()
{try{
      JDBCQuery query=new JDBCQuery(con),query1=new JDBCQuery(con);
      ResultSet rs,rs1;
      String str="";
      Set keys=new HashSet();
      rs=query.select("select Att_id  from IISC_RSK_ATT,IISC_RS_KEY where RS_primary_key=1 and IISC_RSK_ATT.RS_rbrk=IISC_RS_KEY.RS_rbrk and  IISC_RSK_ATT.RS_id=IISC_RS_KEY.RS_id and  IISC_RSK_ATT.AS_id=IISC_RS_KEY.AS_id and  IISC_RSK_ATT.PR_id=IISC_RS_KEY.PR_id and  IISC_RSK_ATT.PR_id=" + pr + " and  IISC_RSK_ATT.AS_id=" + as+"");
       while(rs.next()) 
       keys.add(rs.getString(1));
       query.Close();
      rs=query.select("select IISC_ATTRIBUTE.Att_id,IISC_ATTRIBUTE.Att_mnem,count(*)  from IISC_RS_ATT,IISC_ATTRIBUTE where IISC_RS_ATT.Att_id=IISC_ATTRIBUTE.Att_id and IISC_RS_ATT.PR_id=" + pr + " and  IISC_RS_ATT.AS_id=" + as+" group by IISC_ATTRIBUTE.Att_id,IISC_ATTRIBUTE.Att_mnem");
      while(rs.next()) 
      {
      String str1="";
      int a=rs.getInt(1);
      if(keys.contains(""+a))continue;
      String am=rs.getString("Att_mnem");
      if(rs.getInt(3)>1)
      {
      rs1=query1.select("select RS_name,IISC_RELATION_SCHEME.RS_id  from IISC_RS_ATT,IISC_RELATION_SCHEME  where IISC_RS_ATT.Att_id="+a+" and IISC_RS_ATT.RS_id=IISC_RELATION_SCHEME.RS_id and IISC_RS_ATT.PR_id=" + pr + " and  IISC_RS_ATT.AS_id=" + as+" ");
      while(rs1.next()) 
      str1=str1+"<a href="+ rs1.getInt(2) +">" + rs1.getString(1) + "</a><br>";
      query1.Close();
      }
       if(!str1.equals(""))
      str=str+"<tr><td>"+am+"</td><td colspan=2>"+str1.substring(0,str1.length()-4)+"</td></tr>";
      }
      query.Close();
      if(!str.equals(""))
      str="<tr><td bgcolor=#CCCCCC colspan=3><div align=center><strong>List of Homonyms</strong></div></td></tr><tr><td><strong>Homonym</strong></td><td colspan=2><strong>Relation schemes</strong></td></tr>"+str;
      return str;
 }
catch(Exception e)
    {
      e.printStackTrace();
    } 
    return null;
}
public Set getSubGraph(int i)
{try{
      Set graph=new HashSet();
      graph.add(""+i);
      JDBCQuery query=new JDBCQuery(con),query1=new JDBCQuery(con);
      ResultSet rs,rs1;
      rs=query.select("select *  from IISC_GRAPH_CLOSURE where PR_id=" + pr + " and  AS_id=" + as+" and GC_edge_type=0 and RS_superior="+i);
       while(rs.next()) 
       {
         int k=rs.getInt("RS_inferior");
         graph.addAll(getSubGraph(k));
       }
       query.Close();
       return graph;
    }
catch(Exception e)
    {
      e.printStackTrace();
    } 
    return null;
}
public Set getInferiors(int i)
{
    try{
          Set graph=new HashSet();
          JDBCQuery query=new JDBCQuery(con),query1=new JDBCQuery(con);
          ResultSet rs,rs1;
          rs=query.select("select *  from IISC_GRAPH_CLOSURE where PR_id=" + pr + " and  AS_id=" + as+" and GC_edge_type=0 and RS_superior="+i);
           while(rs.next()) 
           {
             int k=rs.getInt("RS_inferior");
               graph.add(""+k);
           }
           query.Close();
           return graph;
        }
    catch(Exception e)
        {
          e.printStackTrace();
        } 
        return null;
}

public Set getSuperiors(int i)
{
    try{
          Set graph=new HashSet();
          graph.add(""+i);
          JDBCQuery query=new JDBCQuery(con),query1=new JDBCQuery(con);
          ResultSet rs,rs1;
          rs=query.select("select *  from IISC_GRAPH_CLOSURE where PR_id=" + pr + " and  AS_id=" + as+" and GC_edge_type=0 and RS_inferior="+i);
           while(rs.next()) 
           {
             int k=rs.getInt("RS_superior");
             graph.add(""+k);
           }
           query.Close();
           return graph;
        }
    catch(Exception e)
        {
          e.printStackTrace();
        } 
        return null;
}
public String get_A_and_B_dependecies()
{try{
      JDBCQuery query=new JDBCQuery(con),query1=new JDBCQuery(con);
      ResultSet rs,rs1;
      String str="";
      Set Rel=getRelationSchemes();
      Set Criticals=new HashSet();
      Set Criticalsatt=new HashSet();
      //Set RelS=new HashSet();
      //RelS.addAll(Rel);
      Set F=(Set)((Object[])getFunDepScheme(Rel))[0];
      int k=0;
      while(true)
      {Set T=graf_nivo(k,-1); 
      k=k+1;
      if(T.isEmpty())break;
     // if( Presjek(RelS,T).isEmpty())continue;
      
      Iterator it=T.iterator();
      boolean error=false;
      while(it.hasNext()&& !error)
      {
      int j=(new Integer(""+it.next())).intValue();
      RelationScheme rel=getRelationScheme(j);
      Set graph=getSubGraph(rel.id);
      //RelS.removeAll(graph);
      Set R=new HashSet();
      R.addAll(rel.atributi);
      graph.remove(""+rel.id);
      Iterator it1=graph.iterator();
       while(it1.hasNext() && !error)
      { int idr=(new Integer((String)it1.next())).intValue();
        RelationScheme rel1=getRelationScheme(idr);
        Iterator it2=rel1.kljuc.iterator();
         boolean can=false;
        while(it2.hasNext()&& !error)
        {Set key=(Set)it2.next();
          if(equalSets(key,Presjek(R,rel1.atributi)))
            {R.addAll(rel1.atributi);
             can=true;
            }
         } 
         it2=rel1.kljuc.iterator();
          while(it2.hasNext()&& !can)
        { Set key=(Set)it2.next();
         if( Presjek(R,rel1.atributi).containsAll(key))
          { error=false;
            Set at=Razlika(Presjek(R,rel1.atributi),key);
            if(Criticals.contains(at))continue;
            at=Razlika(at,Criticalsatt);
            Criticalsatt.addAll(at);
            Criticals.add(at);
            Iterator it3=at.iterator();
            String attr="";
            while(it3.hasNext())
            attr=attr+getAttribute((new Integer((String)it3.next())).intValue())+", ";
            it3=Rel.iterator();
            Set critical=new HashSet();
            String strc="";
            critical.add(""+rel1.id);
            strc=strc+"<a href="+ rel1.id +">"+ rel1.name +"</a>";
            while(it3.hasNext())
            {
              RelationScheme rel2=(RelationScheme)it3.next();
              if(!Presjek(rel2.atributi,at).isEmpty() && !zatvaranje(rel1.atributi,F).containsAll(rel2.atributi))
               { critical.add(""+rel2.id);
                strc=strc+"<br><a href="+ rel2.id +">"+ rel2.name +"</a>";
               }
            } 
            
            String strq="<tr><td>"+ strc +"</td><td>["+ attr.substring(0,attr.length()-2) +"]</td><td>"+ getIntegrities(critical,Rel,at) +"</td></tr>";
            if(!(ODBCList.splitString(str,strq).length>1))
            str=str+strq;
         } 
        }
      it1.remove();
      it1=graph.iterator();
      }
     
      }
      }
        if(!str.equals(""))
      str="<tr><td bgcolor=#CCCCCC colspan=3><div align=center><strong>A/B Dependencies</strong></div></td></tr><tr><td><strong>Critical scheme</strong></td><td><strong>Critical attribute set</strong></td><td><strong>Constraints</strong></td></tr>"+str+"";
    
      return str;
 }
catch(Exception e)
    {
      e.printStackTrace();
    } 
    return null;
}
public String getIntegrities(Set critical,Set Rel,Set criticalatt)
{try{
      JDBCQuery query=new JDBCQuery(con);
      ResultSet rs;
      String str="";
      Set minimal=new HashSet();
      Set FunDepS= getFunDep();
      Set critica11=new HashSet();
      int minid=-1;
      Set Integrities=new HashSet();
      Iterator it=Rel.iterator();
      while(it.hasNext())
      {
        int j=((RelationScheme)it.next()).id;
        Set pom=getSubGraph(j);
        if(pom.containsAll(critical) && (minimal.size()>pom.size()|| minimal.isEmpty()))
        {
          minimal.clear();
          minimal.addAll(pom);
          minid=j;
        }
      }  
     it=critical.iterator();
     while(it.hasNext())
    { int i1=(new Integer((String)it.next())).intValue();
      critica11.clear();
      critica11.addAll(critical);
      critica11.remove(""+i1);
      Iterator it1=critica11.iterator();
      while(it1.hasNext())
      {
       int i2=(new Integer((String)it1.next())).intValue();
       if(!getSubGraph(i2).contains(""+i1))
       { RelationScheme r1=getRelationScheme(i1);
       RelationScheme r2=getRelationScheme(i2);
       Set r2set=new HashSet();
       r2set.add(r2);
           Set S=new HashSet();  
    /* Iterator itk=Rel.iterator();
     while(itk.hasNext())
     {
       RelationScheme scheme=(RelationScheme)itk.next();
        if(zatvaranje(r1.atributi,FunDepS).containsAll(scheme.atributi))
        S.add(scheme);
       }*/
       Iterator itk=minimal.iterator();
       while(itk.hasNext())
      {
       RelationScheme scheme=getRelationScheme((new Integer((String)itk.next())).intValue());
        S.add(scheme);
       }
       RelationScheme minrel=getRelationScheme(minid);
       Object[] obj=getFunDepSchemeTrue(S);
        Set FunDepSet=(Set)obj[0]; 
        Set setRel=new HashSet();
           
        setRel=Unija(gen_rscheme_set(minrel,r1,S,FunDepS),gen_rscheme_set(minrel,r2,S,FunDepS));
        setRel.add(r1);
        itk=setRel.iterator();
        Set setRel1=new HashSet();
        boolean can=true;
       while(itk.hasNext()&& can)
       {
         RelationScheme scheme=(RelationScheme)itk.next();
         if(scheme.id==r2.id)
         {
           itk.remove();
           can=false;
         }
         else if(!setRel1.contains((Object)scheme))
         {
         Iterator itk1=setRel1.iterator();
          boolean can1=true;
         while(itk1.hasNext())
         {
           RelationScheme scheme1=(RelationScheme)itk1.next();
            if(scheme.id==scheme1.id)
            can1=false;
         }
         if(can1)
         setRel1.add(scheme);
         }
       }
       int t=8;
       if(setRel1.size()>1)
       t=9;
       Integrity integr=new Integrity(t,setRel1,r2set,Unija(r2.primarni_kljuc,criticalatt),Unija(r2.primarni_kljuc,criticalatt));
        // Integrity integr=getIntegrity(i1,i2,minimal,new HashSet(),criticalatt);
         if(integr!=null)
         Integrities.add(integr);
       }
       
      }
    }
it=Integrities.iterator();
while(it.hasNext())
{
  Integrity integr=(Integrity)it.next();
  if(!integr.exists(pr,as,con) && !integr.isContained(pr,as,con))
  {integr.save(pr,as,con);
  str=str+"<a href=integrity="+ integr.id +">" +  integr.name + "</a><br>";}
}
if(str.length()>4)
return str.substring(0,str.length()-4);
else
return "";
} 
catch(Exception e)
    {
      e.printStackTrace();
    } 
    return null;
}
public Integrity getIntegrity(int l, int r, Set graph, Set path,Set criticalatt)
{try{
      JDBCQuery query=new JDBCQuery(con),query1=new JDBCQuery(con);
      ResultSet rs,rs1;
      String str="";
      Set path1=new HashSet();
      path1.addAll(path);
      rs=query.select("select * from IISC_GRAPH_CLOSURE where GC_edge_type=0 and PR_id=" + pr + " and  AS_id=" + as + " and (RS_superior="+l + " or RS_inferior="+l + ")");
      while(rs.next()) 
      {
      int l1=l;
      int superior=rs.getInt("RS_superior");
      int inferior=rs.getInt("RS_inferior");
        path1.add(""+l);
      if(superior==l)
      l1= inferior;
      if(inferior==l)
      l1= superior; 
      if(!graph.contains(""+l1))continue;
      if(path.contains(""+l1))continue;
      if(l1==r)
      {
        Set att=new HashSet();
        Set RelS=new HashSet();
     
      RelationScheme rel=getRelationScheme(l);
      RelationScheme rel1=getRelationScheme(l1);
      Set X=new HashSet();
      X.addAll(rel.atributi);
      if(!X.containsAll(rel1.primarni_kljuc))
      {   String str1=" and RS_superior="+l1+"  and RS_inferior<>"+l +"";
          if(superior==l)
         str1=" and RS_superior="+l+"  and RS_inferior<>"+l1 +"";
         rs1=query1.select("select * from IISC_GRAPH_CLOSURE where GC_edge_type=0 and PR_id=" + pr + " and  AS_id=" + as +str1  );
         while(rs1.next()) 
         {
            int inf=rs1.getInt("RS_inferior");
            if(inf==r)continue;
            RelationScheme relinf=getRelationScheme(inf);
             boolean can=false;
             can=!Presjek(Razlika(relinf.atributi,X),rel1.primarni_kljuc).isEmpty();
             if(can)
             {
              X.addAll(relinf.atributi);
              path1.add(""+inf);
             }
         }
         query1.Close();
      }
     
        Iterator it=path1.iterator();
        while(it.hasNext())
        {
          RelationScheme scheme=getRelationScheme((new Integer((String)it.next())).intValue());
          Iterator it1=Presjek(getRelationScheme(r).atributi,scheme.atributi).iterator();
          while(it1.hasNext())
          { String N=(String)it1.next();
            boolean can=true;
            Iterator it2=att.iterator();
            while(it2.hasNext())
            {String M=(String)it2.next();
             if(N.equals(M)) 
             can=false;
            }
            if(can)
            att.add(N);
          }
         
          RelS.add(scheme);
        }
        if(Presjek(criticalatt,att).isEmpty())
        return null;
        Set right=new HashSet();
        right.add(getRelationScheme(r));
        int type=0;
        if(RelS.size()>1)type=1;
        Integrity integr= new Integrity(type,RelS,right,att,att);
        return integr;
      }
    
      RelationScheme rel=getRelationScheme(l);
      RelationScheme rel1=getRelationScheme(l1);
      Set X=new HashSet();
      X.addAll(rel.atributi);
      if(!X.containsAll(rel1.primarni_kljuc))
      {   String str1=" and RS_superior="+l1+"  and RS_inferior<>"+l +"";
          if(superior==l)
         str1=" and RS_superior="+l+"  and RS_inferior<>"+l1 +"";
         rs1=query1.select("select * from IISC_GRAPH_CLOSURE where GC_edge_type=0 and PR_id=" + pr + " and  AS_id=" + as +str1  );
         while(rs1.next()) 
         {
            int inf=rs1.getInt("RS_inferior");
            if(inf==r)continue;
            RelationScheme relinf=getRelationScheme(inf);
             boolean can=false;
             can=!Presjek(Razlika(relinf.atributi,X),rel1.primarni_kljuc).isEmpty();
             if(can)
             {
              X.addAll(relinf.atributi);
              path1.add(""+inf);
             }
         }
         query1.Close();
      }
      
      Integrity integr=getIntegrity(l1,r,graph,path1, criticalatt);
      if(integr!=null)
      return integr;
      }
      query.Close();
      return null;
 }
catch(Exception e)
    {
      e.printStackTrace();
    } 
    return null;
}
public String getAttribute(int a)
{try{
      JDBCQuery query=new JDBCQuery(con);
      ResultSet rs;
      String str="";
      rs=query.select("select *  from IISC_ATTRIBUTE where  PR_id=" + pr + " and  Att_id=" + a);
      if(rs.next()) 
      str=rs.getString("Att_mnem");
      query.Close();
      return str;
 }
catch(Exception e)
    {
      e.printStackTrace();
    } 
    return null;
}
public Set getRelationSchemes()
{Set R=new HashSet();
try{
   JDBCQuery query=new JDBCQuery(con);
   JDBCQuery query1=new JDBCQuery(con);
  
  ResultSet rs,rs1;
  rs1=query.select("select *  from IISC_RELATION_SCHEME where  PR_id=" + pr + " and  AS_id=" + as);
while(rs1.next())
{R.add(getRelationScheme(rs1.getInt("RS_id")));
}
query.Close();
}
catch(Exception e)
    {
      e.printStackTrace();
    }
    return R;
}
public Set getRelationSchemesID()
{Set R=new HashSet();
try{
   JDBCQuery query=new JDBCQuery(con);
   JDBCQuery query1=new JDBCQuery(con);
  
  ResultSet rs,rs1;
  rs1=query.select("select *  from IISC_RELATION_SCHEME where  PR_id=" + pr + " and  AS_id=" + as);
while(rs1.next())
{R.add(rs1.getString("RS_id"));
}
query.Close();
}
catch(Exception e)
    {
      e.printStackTrace();
    }
    return R;
}
public boolean getRelationScheme(String s)
{RelationScheme R=new RelationScheme();
try{
   JDBCQuery query=new JDBCQuery(con);
   JDBCQuery query1=new JDBCQuery(con);
  
  ResultSet rs,rs1;
  rs1=query.select("select *  from IISC_RELATION_SCHEME where RS_name='"+ s +"' and PR_id=" + pr + " and  AS_id=" + as);
if(rs1.next())
{
query.Close();
return true;
}
query.Close();

}
catch(Exception e)
    {
      e.printStackTrace();
    }
return false;
}
public RelationScheme getRelationScheme(int id)
{ RelationScheme r=new RelationScheme();
    try{
   JDBCQuery query=new JDBCQuery(con);
   JDBCQuery query1=new JDBCQuery(con);
  
  ResultSet rs,rs1;
rs1=query.select("select *  from IISC_RELATION_SCHEME where RS_id=" + id + "  and PR_id=" + pr );
if(rs1.next())
{
r.name=rs1.getString("RS_name") ;
r.id= rs1.getInt("RS_id") ;
query.Close();
rs=query1.select("select *  from IISC_RS_ATT where RS_id=" + r.id + " and  PR_id=" + pr );
while(rs.next())
{
int atid=rs.getInt("Att_id");
r.atributi.add(""+atid);
if(rs.getInt("Att_modifiable")==1)
r.mod_atributi.add(""+atid);
if(rs.getInt("Att_null")==1)
r.null_atributi.add(""+atid);
}
query1.Close();
rs=query1.select("select *  from IISC_RS_ROLE where RS_id=" + r.id + " and  PR_id=" + pr );
while(rs.next())
{
if(rs.getInt("RSR_read")==1)r.role.add("r");
if(rs.getInt("RSR_insert")==1)r.role.add("i");
if(rs.getInt("RSR_modify")==1)r.role.add("m");
if(rs.getInt("RSR_delete")==1)r.role.add("d");
}
query1.Close();
rs=query1.select("select *  from IISC_RS_KEY where RS_id=" + r.id + " and  PR_id=" + pr + " order by RS_rbrk");
while(rs.next())
{int key=rs.getInt("RS_rbrk");
int local=rs.getInt("RS_local");
rs1=query.select("select *  from IISC_RSK_ATT where RS_rbrk=" + key + "  and RS_id=" +  r.id  + "  and PR_id=" + pr );
Set pom=new HashSet();
while(rs1.next())
{
 pom.add(rs1.getString("Att_id"));
}
r.kljuc.add(pom);
if(local==1)r.lokalni_kljuc.add(pom);
if(rs.getInt("RS_primary_key")==1)
r.primarni_kljuc.addAll(pom);
 
if(rs.getInt("RS_candidate")==1)r.kandidati.add(pom);
query.Close(); 
}
query1.Close(); 
if(r.kandidati.size()==1)r.primarni_kljuc.addAll((Set)r.kandidati.iterator().next());

rs=query1.select("select distinct(RS_rbru) from IISC_RS_UNIQUE where RS_id=" + r.id + " and  PR_id=" + pr + " order by 1");
while(rs.next())
{int key=rs.getInt("RS_rbru");
rs1=query.select("select *  from IISC_RS_UNIQUE where RS_rbru=" + key + "  and RS_id=" +  r.id  + "  and PR_id=" + pr );
Set pom=new HashSet();
while(rs1.next())
{
 pom.add(rs1.getString("Att_id"));
}
r.unique.add(pom);
query.Close(); 
}
query1.Close(); 
}
else
query.Close(); 
   }
catch(Exception e)
    {
      e.printStackTrace();
    }
    return r;
}
public Set Presjek(Set a, Set b)
{
  Set pr=new HashSet();
  Iterator it=a.iterator();
  while(it.hasNext())
  {Object el=it.next();
  if(b.contains(el))pr.add(el);
  }
  return pr;
}
public Set Unija(Set a, Set b)
{
  Set pr=new HashSet();
  pr.addAll(a);
  pr.addAll(b);
  return pr;
}
public Set PartitivniSkup(Set a)
{Set pr=new HashSet();
 Set b=new HashSet();
 b.addAll(a);
Iterator it=b.iterator();
while(it.hasNext())
{
Object el=(Object)it.next();
b.remove(el);
Set pom=new HashSet();
if(b.size()>0)
 pom=PartitivniSkup(b);
Set pom1=new HashSet();
pom1.add(el);
pom.add(pom1);
Iterator itb=pom.iterator();
while(itb.hasNext())
((Set)itb.next()).add((String)el);
pr.addAll(pom);
it=b.iterator();
}
return pr;
}
public Set prostiranje_primarnog_kljuca(RelationScheme r, Set pomFunS,Set SS)
{
 if(r.kandidati.size()==1)
 { r.primarni_kljuc.addAll((Set)r.kandidati.iterator().next());
 return SS;
}
  Set SS1=new HashSet();
  Iterator it=SS.iterator();
//   while(it.hasNext())
//   ((RelationScheme)it.next()).print();
  //  it=SS.iterator();
  Set K=new HashSet();
  Iterator itk=r.kljuc.iterator();
  while(itk.hasNext())
  K.addAll((Set)itk.next());
  while(it.hasNext())
  {RelationScheme rel=(RelationScheme)it.next();
  RelationScheme rel1=new RelationScheme();
  if(!Presjek(rel.atributi,Razlika(K,r.primarni_kljuc)).isEmpty())
  {
    Set v=PartitivniSkup(r.primarni_kljuc);
    itk=v.iterator();
    Set v1=new HashSet();
    while(itk.hasNext() )
    {v1=(Set)itk.next();
    if(equalSets(zatvaranje(Presjek(zatvaranje(rel.atributi,pomFunS),K),pomFunS),zatvaranje(v1,pomFunS)))
    {rel1.atributi=Unija(v1,Razlika(rel.atributi,K));
    
    itk=rel.kljuc.iterator();
    while(itk.hasNext())
    {Set y=(Set)itk.next();
    if(!Presjek(y,Razlika(K,v1)).isEmpty())
    {
     Set w=PartitivniSkup(v1);
     Iterator itw=w.iterator();
     while(itw.hasNext())
     {
     Set w1=(Set)itw.next();
    if(equalSets(zatvaranje(w1,pomFunS),zatvaranje(Presjek(K,Unija(y,zatvaranje(Razlika(y,K),pomFunS))),pomFunS)) )
     {rel1.kljuc.add(Unija(w1,Razlika(y,K)));
      if(rel.primarni_kljuc.containsAll(y))rel1.primarni_kljuc.addAll(Unija(w1,Razlika(y,K)));
      Iterator itkand=rel.kandidati.iterator();
       while(itkand.hasNext())
       {
         Set relkan=(Set)itkand.next();
          if(equalSets(relkan,rel.primarni_kljuc))
             rel1.kandidati.add(rel1.primarni_kljuc);
          else
            rel1.kandidati.add(relkan);
       }
     }
     }
    }
    else
   { rel1.kljuc.add(y);
   if(rel.primarni_kljuc.containsAll(y))rel1.primarni_kljuc.addAll(y);}
    }
    }
    }
  }
  else
  {rel1.atributi.addAll(rel.atributi);
  rel1.kandidati.addAll(rel.kandidati);
  rel1.kljuc.addAll(rel.kljuc);
  rel1.primarni_kljuc.addAll(rel.primarni_kljuc);
  }
  rel1.name=rel.name;
  rel1.mod_atributi.addAll(rel.mod_atributi);
  rel1.null_atributi.addAll(rel.null_atributi);
  rel1.unique.addAll(rel.unique);
  rel1.id=rel.id;
  rel=rel1;
  SS1.add(rel1);}
  return SS1;
}
public int getKeyID(RelationScheme r,Set key)
{
 int id=-1;
try{
  
   JDBCQuery query=new JDBCQuery(con);
   JDBCQuery query1=new JDBCQuery(con);
   
  ResultSet rs,rs1;
Set RS=new HashSet(); 
rs1=query.select("select *  from IISC_RS_key where  RS_id=" + r.id + " and PR_id=" + pr + " and  AS_id=" + as);
while(rs1.next() && id==-1)
 { boolean can=true;
   int k=rs1.getInt("RS_rbrk");
   rs=query1.select("select count(*)  from IISC_RSK_ATT where    RS_rbrk=" + k + " and RS_id=" + r.id + " and PR_id=" + pr + " and  AS_id=" + as);
   rs.next();
   if(rs.getInt(1)!=key.size())can=false;
   query1.Close();
   rs=query1.select("select *  from IISC_RSK_ATT where    RS_rbrk=" + k + " and RS_id=" + r.id + " and PR_id=" + pr + " and  AS_id=" + as);
   while(rs.next() && can)
   if(!key.contains(rs.getString("Att_id")))can=false;
   if(can)id=k;
   query1.Close();
 }
 query.Close();
 }
catch(Exception e)
    {
      e.printStackTrace();
    }
return id;
}
public void Kandidati()
{ try{
   int id;
   JDBCQuery query=new JDBCQuery(con);
   JDBCQuery query1=new JDBCQuery(con);
   
  ResultSet rs,rs1;
Set RS=new HashSet();
rs1=query.select("select *  from IISC_RELATION_SCHEME where PR_id=" + pr + " and  AS_id=" + as);
while(rs1.next())
{
RelationScheme r=getRelationScheme(rs1.getInt("RS_id"));
RS.add(r);
}
query.Close();
Iterator it=RS.iterator();
while(it.hasNext())
{ RelationScheme r=(RelationScheme)it.next();
  Kandidati(r,(Set)((Object[])getFunDepSchemeTrue(RS))[0],RS);
  Iterator itk=r.kandidati.iterator();
while(itk.hasNext())
{
Set c=(Set)itk.next();
int kan=getKeyID(r,c);
query.update("update IISC_RS_KEY set RS_candidate=1  where   RS_rbrk=" + kan + " and    RS_id=" + r.id + " and   PR_id=" + pr + " and  AS_id=" + as);
}
}
  }
catch(Exception e)
    {
      e.printStackTrace();
    }
}
public boolean equalSets(Set s, Set s1)
{
  if(s.containsAll(s1)&& s1.containsAll(s))return true;
  else
  return false;
}
public Set Kandidati(RelationScheme r,Set pomFunS,Set SR)
{

Set kan=new HashSet();
Set S=new HashSet();
r.kandidati.addAll(r.kljuc);
Set K=new HashSet();
Iterator itk=r.kljuc.iterator();
while(itk.hasNext())
K.addAll((Set)itk.next());
Iterator it=SR.iterator();
  while(it.hasNext())
  {RelationScheme rel=(RelationScheme)it.next();
  if(r.id!=rel.id  && zatvaranje(rel.atributi,pomFunS).containsAll(r.atributi) && !Presjek(rel.atributi,K).isEmpty())
  {
    S.add(rel);
  }
  }
 it=S.iterator();
  Set kan1=new HashSet();
  kan1.addAll(r.kandidati);
  it=kan1.iterator();
  while(it.hasNext())  
  {
    Set key=(Set)it.next();
    itk=S.iterator();
    boolean can=true;
   while(itk.hasNext())
   {RelationScheme rl=(RelationScheme)itk.next();

    if(!Presjek(rl.atributi,Razlika(K,key)).isEmpty())
    { 
      Set pom=PartitivniSkup(key);
      Iterator itp=pom.iterator();
      while(itp.hasNext())  
      {Set v=(Set)itp.next();  
     if( equalSets(zatvaranje(v,pomFunS), zatvaranje(Presjek(K,zatvaranje(rl.atributi,pomFunS)),pomFunS)))
      {can=false;
       boolean can1=true;
     Iterator itp1=rl.kljuc.iterator();
      while(itp1.hasNext()&& can1)   
      { Set y=(Set)itp1.next();
        if(!Presjek(y,Razlika(K,key)).isEmpty())
        {  can1=true;
          Set pom2=PartitivniSkup(v);
          Iterator itc=pom2.iterator();
          while(itc.hasNext())  
          if( equalSets(zatvaranje((Set)itc.next(),pomFunS), zatvaranje(Presjek(K,Unija(y,zatvaranje(Razlika(y,K),pomFunS))),pomFunS)))
          {can1=false;
           break;}
           if(can1)
           { 
            r.kandidati.remove(key);
            break;
           }
        }
      }
      } 
      }
   if(can) {
           r.kandidati.remove(key);
           break;
           } 
           }
    }
   }
  r.S.addAll(S);
  return S;
}
public Set Razlika(Set s1,Set s2)
{
  Set razlika=new HashSet();
  razlika.addAll(s1);
  razlika.removeAll(s2);
  return razlika;
}
public void prikaziRSet(Set s1)
{
  Iterator it=(s1).iterator();
  while(it.hasNext())
  ((RelationScheme)it.next()).print();
}

public String  algoritam_prostiranja( Set SR,Set pomFunS ,Set T)
 {String log=new String(),quer=new String();
  try{
  if(SR.size()<2)
  return log;
 JDBCQuery query=new JDBCQuery(con);
 JDBCQuery query1=new JDBCQuery(con);
 ResultSet rs,rs1;
 int k=0;
 Set pomRS=new HashSet(SR);
 while(!graf_nivo(k,-1).isEmpty())
 k=k+1;
 Object[] S=new Object[k];
 for(int i=0;i<k;i++)
 {Iterator it= graf_nivo(i,-1).iterator();
 S[i]=new HashSet();
 while(it.hasNext())
 {Integer d=new Integer((String)it.next());
 int d1=d.intValue();
 RelationScheme rl=getRelationScheme(""+d1,SR);
 pomRS.remove(rl);
 ((Set)S[i]).add(rl);}
  
 }
  for(int i=S.length-1;i>0;i--)
  {Iterator it=((Set)S[i]).iterator();
  while(it.hasNext())
  {
   RelationScheme r=((RelationScheme)it.next());
   Set SS=new HashSet();
   Set K=new HashSet();
  Iterator itklj=r.kljuc.iterator();
  while(itklj.hasNext())
  K.addAll((Set)itklj.next());
  Iterator itsr=SR.iterator();
  while(itsr.hasNext())
  {RelationScheme rel=(RelationScheme)itsr.next();
  if(r.id!=rel.id  && zatvaranje(rel.atributi,pomFunS).containsAll(r.atributi) && !Presjek(rel.atributi,K).isEmpty())
  {
    SS.add(rel);
  }
  }
if(!r.kandidati.isEmpty())
   {
   Set SS1=prostiranje_primarnog_kljuca(r, pomFunS,SS);
   Iterator it1=SS.iterator();
   Set pom=new HashSet();
   while(it1.hasNext())
   pom.add(""+((RelationScheme)it1.next()).id);
   it1=SS1.iterator();
   while(it1.hasNext())
   { RelationScheme rr=((RelationScheme)it1.next());
     if(pom.contains(""+rr.id))
    { 
     RelationScheme re= getRelationScheme(""+rr.id,SR);
     SR.remove(re);
     SR.add(rr);
    }
   }

   
   
   for( k=0;k<i ;k++)
   {
   it1=((Set)S[k]).iterator();
   Set pom1=new HashSet();
   while(it1.hasNext())
   { RelationScheme rr=((RelationScheme)it1.next());
     if(!pom.contains(""+rr.id))
     {pom1.add(rr);
    
     }
   }
   it1=SS1.iterator();
   while(it1.hasNext())
   { RelationScheme rr=((RelationScheme)it1.next());
     if(pom.contains(""+rr.id))
    { pom1.add(rr);
     RelationScheme re= getRelationScheme(""+rr.id,SR);
     SR.remove(re);
     SR.add(rr);
    }
   }
   ((Set)S[k]).clear();
   ((Set)S[k]).addAll(pom1);
   }
   }
   else if(r.primarni_kljuc.isEmpty())
   { log=log+ r.name +" has no primary key\n";
     quer=quer+ " and RS_id<>" + r.id;
   }
  }
  
 Iterator it4=SR.iterator();
  while(it4.hasNext())
  {
    ((RelationScheme)it4.next()).print();
  }
  }
 Iterator it=pomRS.iterator();
  while(it.hasNext())
  { RelationScheme re=(RelationScheme)it.next();
   if(re.primarni_kljuc.isEmpty()) 
  log=log+ re.name +" has no primary key\n";
  quer=quer+ " and RS_id<>" + re.id;
  }
  
  Object[] obj=getFunDepScheme(Razlika(SR,pomRS) );
 clearDBDesign(quer);
 
  SynthSimple((Set)obj[0],(Set)obj[1],Razlika(SR,pomRS));   
  query.update("update IISC_RELATION_SCHEME set RS_propagate=1 where PR_id=" + pr + " and  AS_id=" + as );   
  graf_zatvaranja();
   }
catch(Exception e)
    {
      e.printStackTrace();    }
      return log;
 }
 public Object[] getFunDepScheme(Set RS)
 {
   Set FunSet=new HashSet();
 Iterator it=RS.iterator();
  Set TS =new HashSet();
  while(it.hasNext())
  {
   RelationScheme r=(RelationScheme)it.next();
   Iterator itk=r.kljuc.iterator();
   int t=1;
  while(itk.hasNext())
  {
  Set k1=(Set)itk.next();
  Iterator ita=r.atributi.iterator();
  while(ita.hasNext())
  {FunDep fun=new FunDep();
  String at=(String)ita.next();
    fun.left.addAll(k1);
   fun.right.add(at);
   fun.fun=true;
   fun.type=true;
   FunSet.add(fun);
    
  }
  if(k1.containsAll(r.atributi))
  {FunDep fun=new FunDep();
  fun.left.addAll(k1);
   fun.right.add("T"+t);
   TS.add("T"+t);
   t=t+1;
  fun.fun=false;
  fun.type=true;
  FunSet.add(fun);
  }
  }
   Iterator itu=r.unique.iterator();
   t=1;
  while(itu.hasNext())
  {
  Set k1=(Set)itu.next();
  Iterator ita=r.atributi.iterator();
  while(ita.hasNext())
  {FunDep fun=new FunDep();
  String at=(String)ita.next();
    fun.left.addAll(k1);
   fun.right.add(at);
   fun.fun=true;
   fun.type=false;
   FunSet.add(fun);
    
  }
  if(k1.containsAll(r.atributi))
  {FunDep fun=new FunDep();
  fun.left.addAll(k1);
   fun.right.add("T"+t);
   TS.add("T"+t);
   t=t+1;
  fun.fun=false;
  fun.type=false;
  FunSet.add(fun);
  }
  }
  }
  Object[] obj=new HashSet[2];
  obj[0]=FunSet;
  obj[1]=TS;
  return obj;
 }
  public Object[] getFunDepSchemeTrue(Set RS)
 {
   Set FunSet=new HashSet();
 Iterator it=RS.iterator();
  Set TS =new HashSet();
  while(it.hasNext())
  {
   RelationScheme r=(RelationScheme)it.next();
   Iterator itk=r.kljuc.iterator();
   int t=1;
  while(itk.hasNext())
  {
  Set k1=(Set)itk.next();
  Iterator ita=r.atributi.iterator();
  while(ita.hasNext())
  {FunDep fun=new FunDep();
  String at=(String)ita.next();
    fun.left.addAll(k1);
   fun.right.add(at);
   fun.fun=true;
   fun.type=true;
   FunSet.add(fun);
    
  }
  if(k1.containsAll(r.atributi))
  {FunDep fun=new FunDep();
  fun.left.addAll(k1);
   fun.right.add("T"+t);
   TS.add("T"+t);
   t=t+1;
  fun.fun=false;
  fun.type=true;
  FunSet.add(fun);
  }
  }
  }
  Object[] obj=new HashSet[2];
  obj[0]=FunSet;
  obj[1]=TS;
  return obj;
 }
public void clearDBDesign(String s) 
 {     JDBCQuery query=new JDBCQuery(con);
      
      query.update("delete  from IISC_F_NF_DEP where  PR_id=" + pr + " and  AS_id=" + as); 
      query.update("delete  from IISC_FNF_LHS_RHS where  PR_id=" + pr + " and  AS_id=" + as); 
      query.update("delete  from IISC_RSK_ATT where  PR_id=" + pr + " and  AS_id=" + as + s); 
      query.update("delete  from IISC_RS_KEY where  PR_id=" + pr + " and  AS_id=" + as + s); 
      query.update("delete  from IISC_RS_ATT where  PR_id=" + pr + " and  AS_id=" + as + s);
      query.update("delete   from IISC_RELATION_SCHEME where  PR_id=" + pr + " and  AS_id=" + as + s); 
      query.update("delete   from IISC_RS_ROLE where  PR_id=" + pr + " and  AS_id=" + as + s); 
      query.update("delete  from IISC_GRAPH_CLOSURE where PR_id=" + pr + " and  AS_id=" + as); 
      query.update("delete  from IISC_RSC_ACTION where PR_id=" + pr + " and  AS_id=" + as); 
      query.update("delete  from IISC_RS_CONSTRAINT where PR_id=" + pr + " and  AS_id=" + as); 
      query.update("delete  from IISC_RSC_LHS_RHS where PR_id=" + pr + " and  AS_id=" + as); 
      query.update("delete  from IISC_RSC_RS_SET where PR_id=" + pr + " and  AS_id=" + as); 
      query.update("delete  from IISC_RS_UNIQUE where AS_id=" + as + " and  PR_id=" + pr);
      query.update("delete  from IISC_RS_SR where AS_id=" + as + " and  PR_id=" + pr);
     /*nobrenovic: start*/
     query.update("delete  from IISC_CHKC_BASIC_TERM where AS_id=" + as + " and  PR_id=" + pr);
     query.update("delete  from IISC_CHKC_NF_PART where AS_id=" + as + " and  PR_id=" + pr);
     query.update("delete  from IISC_CHKC_NORMAL_FORM where AS_id=" + as + " and  PR_id=" + pr);
     query.update("delete  from IISC_CHECK_CONSTRAINT where AS_id=" + as + " and  PR_id=" + pr);
     /*nobrenovic: stop*/
}
public int check_subscheme_compatybility(int i)
 {
 try{
     JDBCQuery query=new JDBCQuery(con);
     JDBCQuery query1=new JDBCQuery(con);
     ResultSet rs,rs1;
     Set ASet=new HashSet();
    rs=query.select("select *  from IISC_APP_SYSTEM_CONTAIN where PR_id=" + pr + " and  AS_id=" + as);
    while(rs.next())
{
ASet.add(rs.getString("AS_id_con"));
}
query.Close();  
  if(i>=1)
  if(check_attributes(ASet)==-1)
  return -1;
  if(i>=2)
  if(check_keys(ASet)==-1)
  return -2;
  if(i>=3)
  if(check_uniques(ASet)==-1)
  return -3;
  if(i>=4)
  if(check_null(ASet)==-1)
  return -4;
  if(i>=5)
  if(check_referentials(ASet)==-1)
  return -5;
  if(i>=6)
  if(check_check_constraints(ASet)==-1)
  return -6;
  }
catch(Exception e)
    {
      e.printStackTrace();
      
    }
int k=i;
return k;
 }
 public int check_attributes(Set ASet)
  { Iterator it=ASet.iterator();
  while(it.hasNext())
  {
  int ass=(new Integer(it.next().toString())).intValue();
 try{
     JDBCQuery query=new JDBCQuery(con);
     JDBCQuery query1=new JDBCQuery(con);
     ResultSet rs,rs1;
     Set RelSet=getRelationSchemes();
     Set RSet=new HashSet();
     String str=new String();
     rs=query.select("select * from IISC_APP_SYSTEM where  AS_id=" + as);
  String string=new String();
  if(rs.next())
  string="<a href=as="+rs.getString("AS_id")+">"+rs.getString("AS_name")+"</a>";
  query.Close();
  
    rs=query.select("select *  from IISC_RELATION_SCHEME where PR_id=" + pr + " and  AS_id=" + ass);
    while(rs.next())
{
RSet.add(getRelationScheme( rs.getInt("RS_id")));
}
query.Close();  
  Iterator it2=RSet.iterator();
  while(it2.hasNext())
  {
  RelationScheme rel= (RelationScheme)it2.next();
  Iterator it1=RelSet.iterator();
  RelationScheme pomr=new RelationScheme();
  while(it1.hasNext())
  {
  RelationScheme r= (RelationScheme)it1.next();
  if(r.atributi.containsAll(rel.atributi) && r.atributi.size()>pomr.atributi.size())
  {pomr=r;
  }
  }
  if(pomr.atributi.size()==0)
  {
rs=query.select("select * from IISC_APP_SYSTEM where  AS_id=" + ass);
if(rs.next())
str=str+ "<tr><td nowrap><a href=as="+ rs.getString("AS_id") +">"+rs.getString("AS_name")+ "</a> </td><td nowrap> <a href="+ rel.id +">" + rel.name + "</a> "+ "</td></tr>";
query.Close();
  }
  else
  {RelSet.remove(pomr);
  rs=query.select("select count(*) from IISC_RS_SR where PR_id="+ pr +" and AS_id="+ as +" and RS_id="+ pomr.id +" and SR_as_id=" + ass);
  if(rs.next())
  {
    if(rs.getInt(1)>0)
    {
      query.Close();
      query.update("update IISC_RS_SR set SR_id="+ rel.id +" where PR_id="+ pr +" and AS_id="+ as +" and RS_id="+ pomr.id +" and SR_as_id=" + ass);
    }
    else
    { 
     query.Close();
      query.update("insert into IISC_RS_SR(RS_id,AS_id,PR_id,SR_as_id,SR_id) values ("+ pomr.id +","+ as +","+ pr +"," + ass + ","+ rel.id +")");
    }
  }
   else
 query.Close();
 }
  }
  if(!str.equals(""))
  { 
    rs=query.select("select max(CL_id)+1  from IISC_COLLISION_LOG");
    int idl=0;
    if(rs.next())
    idl=rs.getInt(1);
    query.Close();
    query.update("insert into IISC_COLLISION_LOG(CL_id,PR_id,AS_id,CL_type,CL_date,CL_text,CL_warning) values ("+ idl +","+ pr +","+ as +",0,'" + ODBCList.now() + "','" + str + "',"+ 0 +")");
  return -1;
  }
  }
catch(Exception e)
    {
      e.printStackTrace();
      
    }
  }
return 0;
 }
 
 //nobrenovic: start
  public int check_check_constraints(Set ASet){ 
    
    MathSAT5Checker ms5Checker = new MathSAT5Checker();
    CVC3Checker cvcChecker = new CVC3Checker();
    
    try{
    
       Set appSysChkCons = new HashSet();
       JDBCQuery query=new JDBCQuery(con);
       ResultSet rs=query.select("select *  from IISC_CHECK_CONSTRAINT where PR_id=" + pr + " and  AS_id=" + as + " and  RS_SET_ID <> NULL");
       while(rs.next())
       {
          appSysChkCons.add(CheckConstraint.loadCheckConstraint(con, pr, as, rs.getInt("CHKC_ID")));
       }
       query.Close();
       
       boolean oneInvalid = false;
       
        String retMsg = "";
        String appSysName = "";
        rs=query.select("select * from IISC_APP_SYSTEM where  AS_id=" + as + " and PR_ID=" + pr);
        
        if(rs.next())
            appSysName="<a href=as="+rs.getString("AS_id")+">"+rs.getString("AS_name")+"</a>";
        query.Close();
        
        Iterator itAppSysChkCons=appSysChkCons.iterator();
        while(itAppSysChkCons.hasNext())
        {
            CheckConstraint appSysCC = (CheckConstraint)itAppSysChkCons.next();
            Iterator it=ASet.iterator();
            while(it.hasNext()){
                
                int subSysId=(new Integer(it.next().toString())).intValue();
                Set<String> subSysAttrs = getAttributesOfAppSys(pr, subSysId);
                
                //if not all check constraint attributes belong to the subsystem, skip check
                if(!Razlika(appSysCC.getAllAttrIDs(),subSysAttrs).isEmpty())
                    continue;
                
                String appSubSysName = "";
                rs=query.select("select * from IISC_APP_SYSTEM where  AS_id=" + subSysId + " and PR_ID=" + pr);
                if(rs.next())
                    appSubSysName=rs.getString("AS_name");
                query.Close();

                Set subSysChkCons = new HashSet();
                rs=query.select("select *  from IISC_CHECK_CONSTRAINT where PR_id=" + pr + " and  AS_id=" + subSysId + " and  RS_SET_ID <> NULL");
                while(rs.next())
                {
                    subSysChkCons.add(CheckConstraint.loadCheckConstraint(con, pr, subSysId, rs.getInt("CHKC_ID")));
                }
                query.Close();
        
                //now, we need to find at least one check constraint in the subsystem that is stronger then check contraint in the system
                Iterator itSubSysChkCons = subSysChkCons.iterator();
                boolean found = false;
                while(itSubSysChkCons.hasNext())
                {
                    CheckConstraint subSysCC = (CheckConstraint)itSubSysChkCons.next();
                    
                    if(subSysCC.equals(appSysCC))
                        found = true;
                    else{ 
                        CheckConstraint fromCC1 = subSysCC.getDeepCopyOf();
                        CheckConstraint toCC1 = appSysCC.getDeepCopyOf();
                        CheckConstraint fromCC2 = subSysCC.getDeepCopyOf();
                        CheckConstraint toCC2 = appSysCC.getDeepCopyOf();
                        
                        boolean ms5Proved = ms5Checker.checkInferrence(fromCC1, toCC1);
                        boolean cvcProved = cvcChecker.checkInferrence(fromCC2, toCC2);
                        
                        System.out.println(subSysCC.getOrigCon() + " => " + appSysCC.getOrigCon());
                        System.out.println("MatSat5 " + (ms5Proved ? " proved!" : " failed to prove!"));
                        System.out.println("CVC " + (cvcProved ? " proved!" : " failed to prove!"));
                        
                        if(ms5Proved || cvcProved)
                        {
                            found = true;
                        }
                        else if(!Presjek(appSysCC.getAllAttrIDs(),subSysCC.getAllAttrIDs()).isEmpty())
                        {
                            String str = "Application subsystem check constraint " + subSysCC.getOrigCon().replaceAll("<", "&lt;").replaceAll(">", "&gt;") + " might be in collision with application system check constraint " + appSysCC.getOrigCon().replaceAll("<", "&lt;").replaceAll(">", "&gt;") + ".";
                            retMsg += "<tr><td nowrap>" + appSubSysName + "</td><td>" + str +  "</td></tr>";
                            oneInvalid = true;
                        }
                    }
                }
                
               if(!found){
                  String str = "Application system check constraint " + appSysCC.getOrigCon().replaceAll("<", "&lt;").replaceAll(">", "&gt;") + " does not have a corresponding check constraint in subsystem " + appSubSysName + ".";
                  retMsg += "<tr><td nowrap>" + appSubSysName + "</td><td>" + str +  "</td></tr>";
                  oneInvalid = true;
               }  
            }
        }
   
        if(oneInvalid){
            //add table header
             retMsg  = "<tr><th nowrap>Application Subsystem</th><th>Error Message</th></tr>" + retMsg;
            
            rs=query.select("select max(CL_id)+1  from IISC_COLLISION_LOG");
            int idLog=0;
            if(rs.next())
                idLog=rs.getInt(1);
            query.Close();
            query.update("insert into IISC_COLLISION_LOG(CL_id,PR_id,AS_id,CL_type,CL_date,CL_text,CL_warning) values ("+ idLog +","+ pr +","+ as +",5,'" + ODBCList.now() + "','" + retMsg + "',"+ 0 +")");
            return -1;
        }else 
            return 0;
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
   
    return 0;
}

    public Set<String> getAttributesOfAppSys(int pr, int as){ 
      
      try{
      
         Set<String> appSysAttrs = new HashSet<String>();
         JDBCQuery query=new JDBCQuery(con);
         ResultSet rs=query.select("select distinct ATT_ID from IISC_ATT_TOB a, IISC_TF_APPSYS b " +
                                    "where a.PR_ID = b.PR_ID and a.TF_ID = b.TF_ID " +
                                          "and b.PR_id=" + pr + " and b.AS_id=" + as);
         while(rs.next())
         {
            appSysAttrs.add(rs.getString("ATT_ID"));
         }
         query.Close();
         
         return appSysAttrs;
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
     
      return null;
    }
//nobrenovic: end
 
public int check_keys(Set ASet)
  { try{
     JDBCQuery query=new JDBCQuery(con);
     JDBCQuery query1=new JDBCQuery(con);
     JDBCQuery query2=new JDBCQuery(con);
     Set Pko=new HashSet();
     Set Pksh=new HashSet();
     ResultSet rs,rs1,rs2;
     String string=new String(),string1=new String();
  Iterator it=ASet.iterator();
  while(it.hasNext())
  {
  int ass=(new Integer(it.next().toString())).intValue();

  rs=query.select("select * from IISC_APP_SYSTEM where  AS_id=" + ass);
  String str=new String();
  if(rs.next())
  str="<a href=as="+rs.getString("AS_id")+"> "+rs.getString("AS_name")+"</a>";
  query.Close();
    
    Set RSet=new HashSet();
    
    rs=query.select("select *  from IISC_RELATION_SCHEME where PR_id=" + pr + " and  AS_id=" + ass);
    while(rs.next())
{
RSet.add(getRelationScheme(rs.getInt("RS_id")));
}
query.Close();  
  Iterator it2=RSet.iterator();
  while(it2.hasNext())
  {
  RelationScheme rel= (RelationScheme)it2.next(),relcoresp;
    rs1=query1.select("select RSR_insert,RSR_modify  from IISC_RS_ROLE where (RSR_insert=1 or RSR_modify=1) and PR_id=" + pr + " and  AS_id=" + ass+ " and RS_id="+ rel.id);
  if(rs1.next())
  {int mod=rs1.getInt(2);
   int ins=rs1.getInt(1);
   if(mod>0 || ins>0)
   {rs=query.select("select *  from IISC_RS_SR where PR_id=" + pr + " and  AS_id=" + as+ " and  SR_as_id=" + ass + " and SR_id="+ rel.id);
   if(rs.next())
   {
   relcoresp=getRelationScheme( rs.getInt("RS_id"));
   Iterator it1=(Razlika(relcoresp.kljuc,Razlika(rel.kljuc,rel.lokalni_kljuc))).iterator();
   while(it1.hasNext())
   {
   Set key=(Set)it1.next();
   if(!Presjek(key,rel.atributi).isEmpty())
   if(ins>0 || (mod>0 && !Presjek(key,relcoresp.mod_atributi).isEmpty()))
   {Pko.add(key);
   Pksh.add(""+rel.id);
   String keyname=new String();
   Iterator it3=key.iterator();
   while(it3.hasNext())
   {
   
   rs2=query2.select("select *  from IISC_ATTRIBUTE where PR_id=" + pr + " and  Att_id=" + it3.next().toString());
   if(rs2.next())
   {
   if(!keyname.equals(""))keyname=keyname+", ";
   keyname=keyname+rs2.getString("Att_mnem");
   }
   query2.Close(); 
   }
  
   string=string + "<tr><td nowrap>"+ str + "</td><td nowrap><a href="+ rel.id +">" + rel.name + "</a></td><td nowrap> ["+ keyname + "]</td><td nowrap><a href="+ relcoresp.id +">" + relcoresp.name + "</a></tr>";
   }
   
   }
   query.Close(); 
   }
    
   }
  }
  query1.Close();  
  }}
 

 it=ASet.iterator();
  while(it.hasNext())
  {
   int ass=(new Integer(it.next().toString())).intValue();

  rs=query.select("select * from IISC_APP_SYSTEM where  AS_id=" + ass);
  String str=new String();
  if(rs.next())
  str="<a href=as="+rs.getString("AS_id")+"> "+rs.getString("AS_name")+"</a>";
  query.Close();
     Set RSet=new HashSet();
    rs=query.select("select *  from IISC_RELATION_SCHEME where PR_id=" + pr + " and  AS_id=" + ass);
    while(rs.next())
{
RSet.add(getRelationScheme(rs.getInt("RS_id")));
}
query.Close();  
  Iterator it2=RSet.iterator();
  while(it2.hasNext())
  {
    RelationScheme rel=(RelationScheme)it2.next(),relcoresp=new RelationScheme();
    rs=query.select("select *  from IISC_RS_SR where PR_id=" + pr + " and  AS_id=" + as+ " and  SR_as_id=" + ass + " and SR_id="+ rel.id);
    
   if(rs.next())
   {
   relcoresp=getRelationScheme( rs.getInt("RS_id"));
   }
  query.Close();
   Iterator it1=relcoresp.kljuc.iterator();
   while(it1.hasNext())
   {
   Set key=(Set)it1.next();
   if(Pko.contains(key))
   {
     if(Razlika(rel.kljuc,rel.lokalni_kljuc).contains(key))
  { 
   Pksh.add(""+rel.id);
   String keyname=new String();
   Iterator it3=key.iterator();
   while(it3.hasNext())
   {
   
   rs2=query2.select("select *  from IISC_ATTRIBUTE where PR_id=" + pr + " and  Att_id=" + it3.next().toString());
   if(rs2.next())
   {
   if(!keyname.equals(""))keyname=keyname+", ";
   keyname=keyname+rs2.getString("Att_mnem");
   }
   query2.Close();
   }
  string.replaceAll("["+ keyname + "]","["+ keyname + "]<br>Key found in <br><table><tr><td   bgcolor=#CCCCCC ><b>Child app.sys.</b></td><td   bgcolor=#CCCCCC ><b>Relation scheme</b></td></tr><tr><td nowrap>"+ str + "</td><td nowrap><a href="+ rel.id +">" + rel.name + "</a></td></tr></table>");
  }
   }
   }
  }
  }
  if(!string.equals(""))
    {
      rs=query.select("select max(CL_id)+1  from IISC_COLLISION_LOG");
      int idl=0;
      if(rs.next())
      idl=rs.getInt(1);
      query.Close();
       query.update("insert into IISC_COLLISION_LOG(CL_id,PR_id,AS_id,CL_type,CL_date,CL_text,CL_warning) values ("+ idl +","+ pr +","+ as +",1,'" + ODBCList.now() + "','" + string + "',"+0 + ")");
      return -1;  
    }
    }
catch(Exception e)
    {
      e.printStackTrace();
      
    }
return 0;
 }
  public int check_uniques(Set ASet)
  { try{
     JDBCQuery query=new JDBCQuery(con);
     JDBCQuery query1=new JDBCQuery(con);
     JDBCQuery query2=new JDBCQuery(con);
     Set Pko=new HashSet();
     Set Pksh=new HashSet();
     ResultSet rs,rs1,rs2;
     String string=new String(),string1=new String();
      rs=query.select("select *  from IISC_RELATION_SCHEME where PR_id=" + pr + " and  AS_id=" + as);
    while(rs.next())
{
RelationScheme rel= getRelationScheme(rs.getInt("RS_id"));
Iterator it2=rel.kljuc.iterator();
  while(it2.hasNext())
  {
  if(((Set)it2.next()).contains(rel.unique))
  Pko.add(rel.unique);
  }
}
query.Close();  
  Iterator it=ASet.iterator();
  while(it.hasNext())
  {
  int ass=(new Integer(it.next().toString())).intValue();

  rs=query.select("select * from IISC_APP_SYSTEM where  AS_id=" + ass);
  String str=new String();
  if(rs.next())
  str="<a href=as="+rs.getString("AS_id")+"> "+rs.getString("AS_name")+"</a>";
  query.Close();
    
    Set RSet=new HashSet();
    
    rs=query.select("select *  from IISC_RELATION_SCHEME where PR_id=" + pr + " and  AS_id=" + ass);
    while(rs.next())
{
RSet.add(getRelationScheme(rs.getInt("RS_id")));
}
query.Close();  
  Iterator it2=RSet.iterator();
  while(it2.hasNext())
  {
  RelationScheme rel= (RelationScheme)it2.next(),relcoresp;
    rs1=query1.select("select RSR_insert,RSR_modify  from IISC_RS_ROLE where (RSR_insert=1 or RSR_modify=1) and PR_id=" + pr + " and  AS_id=" + ass+ " and RS_id="+ rel.id);
  if(rs1.next())
  {int mod=rs1.getInt(2);
   int ins=rs1.getInt(1);
   if(mod>0 || ins>0)
   {rs=query.select("select *  from IISC_RS_SR where PR_id=" + pr + " and  AS_id=" + as+ " and  SR_as_id=" + ass + " and SR_id="+ rel.id);
   if(rs.next())
   {
   relcoresp=getRelationScheme( rs.getInt("RS_id"));
   Iterator it1=(relcoresp.unique).iterator();
   while(it1.hasNext())
   {
   Set unique=(Set)it1.next();
   if(!Presjek(unique,rel.atributi).isEmpty() && !rel.unique.contains(unique) && !rel.kljuc.contains(unique))
   if(ins>0 || (mod>0 && !Presjek(unique,relcoresp.mod_atributi).isEmpty()))
   {Pko.add(unique);
   Pksh.add(""+rel.id);
   String uniquename=new String();
   Iterator it3=unique.iterator();
   while(it3.hasNext())
   {
   
   rs2=query2.select("select *  from IISC_ATTRIBUTE where PR_id=" + pr + " and  Att_id=" + it3.next().toString());
   if(rs2.next())
   {
   if(!uniquename.equals(""))uniquename=uniquename+", ";
   uniquename=uniquename+rs2.getString("Att_mnem");
   }
   query2.Close(); 
   }
   string=string + "<tr><td nowrap>"+ str + "</td><td nowrap><a href="+ rel.id +">" + rel.name + "</a></td><td nowrap> ["+ uniquename + "]</td><td nowrap><a href="+ relcoresp.id +">" + relcoresp.name + "</a></tr>";
   }
   }
   }
   query.Close();  
   }
  }
  query1.Close();  
  }}
 

 it=ASet.iterator();
  while(it.hasNext())
  {
   int ass=(new Integer(it.next().toString())).intValue();

  rs=query.select("select * from IISC_APP_SYSTEM where  AS_id=" + ass);
  String str=new String();
  if(rs.next())
  str="<a href=as="+rs.getString("AS_id")+"> "+rs.getString("AS_name")+"</a>";
  query.Close();
     Set RSet=new HashSet();
    rs=query.select("select *  from IISC_RELATION_SCHEME where PR_id=" + pr + " and  AS_id=" + ass);
    while(rs.next())
{
RSet.add(getRelationScheme(rs.getInt("RS_id")));
}
query.Close();  
  Iterator it2=RSet.iterator();
  while(it2.hasNext())
  {
    RelationScheme rel=(RelationScheme)it2.next(),relcoresp=new RelationScheme();
    rs=query.select("select *  from IISC_RS_SR where PR_id=" + pr + " and  AS_id=" + as+ " and  SR_as_id=" + ass + " and SR_id="+ rel.id);
    
   if(rs.next())
   {
   relcoresp=getRelationScheme( rs.getInt("RS_id"));
   }
  query.Close();
   Iterator it1=relcoresp.unique.iterator();
   while(it1.hasNext())
   {
   Set unique=(Set)it1.next();
   if(Pko.contains(unique))
   {
     if(rel.lokalni_kljuc.contains(unique) || rel.lokalni_unique.contains(unique))
  { 
   Pksh.add(""+rel.id);
   String uniquename=new String();
   Iterator it3=unique.iterator();
   while(it3.hasNext())
   {
   
   rs2=query2.select("select *  from IISC_ATTRIBUTE where PR_id=" + pr + " and  Att_id=" + it3.next().toString());
   if(rs2.next())
   {if(!uniquename.equals(""))uniquename=uniquename+", ";
   uniquename=uniquename+rs2.getString("Att_mnem");
   }
   query2.Close();
   }
  string.replaceAll("["+ uniquename + "]","["+ uniquename + "]<br>Unique found in <br><table><tr><td   bgcolor=#CCCCCC ><b>Child app.sys.</b></td><td   bgcolor=#CCCCCC ><b>Relation scheme</b></td></tr><tr><td nowrap>"+ str + "</td><td nowrap><a href="+ rel.id +">" + rel.name + "</a></td></tr></table>");
     
    }
   }
   }
  }
  }
  if(!string.equals(""))
    {
      rs=query.select("select max(CL_id)+1  from IISC_COLLISION_LOG");
      int idl=0;
      if(rs.next())
      idl=rs.getInt(1);
      query.Close();
       query.update("insert into IISC_COLLISION_LOG(CL_id,PR_id,AS_id,CL_type,CL_date,CL_text,CL_warning) values ("+ idl +","+ pr +","+ as +",2,'" + ODBCList.now() + "','"+ string + "',"+0 + ")");

       return -1;  
    }
    }
catch(Exception e)
    {
      e.printStackTrace();
      
    }
return 0;
 }
  public int check_null(Set ASet)
  { 
  try{
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    JDBCQuery query2=new JDBCQuery(con);
    ResultSet rs,rs1,rs2;
    String str=new String(), string=new String(), string1=new String();
    Set Rset=getRelationSchemes();
    Iterator it2=Rset.iterator();
    if(it2.hasNext()){
    RelationScheme r= (RelationScheme)it2.next();
    Set K=new HashSet();
    Iterator itk=r.kljuc.iterator();
    while(itk.hasNext())
        K.addAll((Set)itk.next());
    itk=r.null_atributi.iterator();
    while(itk.hasNext())
    {
        String k=itk.next().toString(); 
        if(K.contains(k)) 
        {
            rs2=query2.select("select *  from IISC_ATTRIBUTE where PR_id=" + pr + " and  Att_id=" + k);
            if(rs2.next())
                string=string + "<tr><td nowrap><a href="+ r.id +">" + r.name + "</a></td><td valign=top>"+ rs2.getString("Att_mnem") + " </td><td valign=top>NULL</td><td valign=top>NOT NULL</td><td valign=top> </td><td valign=top> </td></tr>";
            query2.Close();
            query2.update("update IISC_RS_ATT set Att_null=0 where PR_id=" + pr + " and RS_id="+ r.id +" Att_id=" + k);
        }
    }
    }
    it2=Rset.iterator();
    while(it2.hasNext())
    {
        RelationScheme rel= (RelationScheme)it2.next(),relcoresp=new RelationScheme();
        Iterator it1=Razlika(rel.atributi, rel.null_atributi).iterator();
        while(it1.hasNext())
        {
            String att=it1.next().toString();
            Iterator it=ASet.iterator();
            while(it.hasNext())
            {
                Set RSet=new HashSet(); 
                int ass=(new Integer(it.next().toString())).intValue();
                rs=query.select("select * from IISC_APP_SYSTEM where  AS_id=" + ass);
                if(rs.next())
                    str="<a href=as="+rs.getString("AS_id")+">"+rs.getString("AS_name")+"</a>";
                query.Close();
                rs=query.select("select *  from IISC_RS_SR where PR_id=" + pr + " and  AS_id=" + as+ " and  SR_as_id=" + ass + " and RS_id="+ rel.id);
                if(rs.next())
                {
                    relcoresp=getRelationScheme( rs.getInt("SR_id"));
                }
                query.Close();  
                rs1=query1.select("select RSR_insert,RSR_modify  from IISC_RS_ROLE where (RSR_insert=1 or RSR_modify=1) and PR_id=" + pr + " and  AS_id=" + ass+ " and RS_id="+ relcoresp.id);
                if(rs1.next())
                {
                    int mod=rs1.getInt(2);
                    int ins=rs1.getInt(1);
                    if((ins>0 || (mod>0 && relcoresp.mod_atributi.contains(att))) && relcoresp.null_atributi.contains(att))
                    {
                        rs2=query2.select("select *  from IISC_ATTRIBUTE where ATT_id="+ att);
                        if(rs2.next())
                            string=string + "<tr><td nowrap><b><font color=#00DD00>i</font></b> info</td><td nowrap><a href="+ rel.id +">" + rel.name + "</a></td><td nowrap>"+ rs2.getString("Att_mnem") + " </td><td nowrap>NOT NULL</td><td nowrap>NULL </td><td nowrap><a href="+ relcoresp.id +">" + relcoresp.name + "</a></td><td nowrap>"+ str + " </td></tr>";
                        query2.Close();  
                        query2.update("update IISC_RS_ATT set Att_null=1 where PR_id=" + pr + " and RS_id="+ rel.id +" and Att_id=" + att);
                    }
                }
                query1.Close();  
            } 
        } 
        Iterator it=ASet.iterator();
        while(it.hasNext())
        {
            int ass=(new Integer(it.next().toString())).intValue();
            rs=query.select("select * from IISC_APP_SYSTEM where  AS_id=" + ass);
            if(rs.next())
                str="<a href=as="+rs.getString("AS_id")+">"+rs.getString("AS_name")+"</a>";
            query.Close();
            rs=query.select("select *  from IISC_RS_SR where PR_id=" + pr + " and  AS_id=" + as+ " and  SR_as_id=" + ass + " and RS_id="+ rel.id);
            if(rs.next())
            {
                relcoresp=getRelationScheme(rs.getInt("SR_id"));
            }
            query.Close();  
            rs1=query1.select("select RSR_insert,RSR_modify  from IISC_RS_ROLE where (RSR_insert=1 or RSR_modify=1) and PR_id=" + pr + " and  AS_id=" + ass+ " and RS_id="+ relcoresp.id);
            if(rs1.next())
            {
                int ins=rs1.getInt(1);
                int mod=rs1.getInt(2);
                Set not_null=new HashSet();
                not_null=Razlika(rel.atributi, rel.null_atributi);
                if(((ins>0 || mod >0) && !(relcoresp.atributi.containsAll(not_null))))
                {
                    String str1="";
                    Iterator it3=(Razlika(not_null, relcoresp.atributi)).iterator();
                    while(it3.hasNext())
                    {
                        int att=(new Integer(it3.next().toString())).intValue();
                        rs2=query2.select("select *  from IISC_ATTRIBUTE where ATT_id="+ att);      
                        if(rs2.next())
                            str1=str1+ rs2.getString("Att_mnem") + "<br>";  
                        query2.Close();
                    }
                        string1=string1 + "<tr><td nowrap><b><font color=#DD0000>X</font></b> collision</td><td nowrap>" + str + "</td><td nowrap><a href="+ relcoresp.id +">" + relcoresp.name + "</a></td><td colspan=2 nowrap>" + str1 + "</td><td colspan=2 nowrap><a href="+ rel.id +">" + rel.name + "</a></td></tr>";         
                }
            }
        }
    }
    if(!string.equals("")||!string1.equals(""))
    {
        if(string.equals(""))string="<tr><td colspan=7></td></tr>";
        if(string1.equals(""))string1="<tr><td colspan=7></td></tr>";
        rs=query.select("select max(CL_id)+1  from IISC_COLLISION_LOG");
        int idl=0;
        if(rs.next())
            idl=rs.getInt(1);
        query.Close();
        query.update("insert into IISC_COLLISION_LOG(CL_id,PR_id,AS_id,CL_type,CL_date,CL_text,CL_warning) values ("+ idl +","+ pr +","+ as +",3,'" + ODBCList.now() + "','"+ string+"###"+string1 + "',"+ ((string1.equals("<tr><td colspan=7></td></tr>"))?1:0) + ")");
        return -1;
    }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
    return 0;
}
public Set get_referentials(int as)
  { 
    Set RefSet=new HashSet();
    try{
     JDBCQuery query=new JDBCQuery(con);
     JDBCQuery query1=new JDBCQuery(con);
     JDBCQuery query2=new JDBCQuery(con);
     ResultSet rs,rs1,rs2;
     String str=new String(),stra=new String(),string=new String(),string1=new String(),string2=new String();
     rs=query.select("select *  from IISC_RS_Constraint where (RSC_type=0 or RSC_type=2) and PR_id=" + pr + " and  AS_id=" + as);
     while(rs.next())
    {int idIntegrity=rs.getInt("RSC_id");
    String name=rs.getString("RSC_name"); 
    int t=rs.getInt("RSC_type");
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
      if(rs1.next())
      {
       l.add(""+rs1.getInt("RS_id")) ;
      }
    query1.Close();
     rs1=query1.select("select  RS_id from IISC_RSC_RS_SET,IISC_RS_Constraint  where IISC_RS_Constraint.RHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id and  IISC_RS_Constraint.PR_ID="+ pr +" and IISC_RS_Constraint.AS_id="+as +" and IISC_RS_Constraint.RSC_id="+idIntegrity);
      if(rs1.next())
      {
       r.add(""+rs1.getInt("RS_id")) ;
      }
    query1.Close();
    RefSet.add( new Integrity(t,l,r,latt,ratt));
    }
  }
  catch(Exception e)
    {
      e.printStackTrace();
        }
        
return RefSet;
}
  public int check_referentials(Set ASet)
  { 
  try{
    JDBCQuery query=new JDBCQuery(con);
     JDBCQuery query1=new JDBCQuery(con);
     JDBCQuery query2=new JDBCQuery(con);
     ResultSet rs,rs1,rs2;
     int warning=1;
   
     String str=new String(),stra=new String(),string=new String(),string1=new String(),string2=new String();
   rs=query.select("select *  from IISC_RS_Constraint where (RSC_type=0 or RSC_type=2) and PR_id=" + pr + " and  AS_id=" + as);
   while(rs.next())
    {int idIntegrity=rs.getInt("RSC_id");
    String name=rs.getString("RSC_name"); 
    int t=rs.getInt("RSC_type");
    Set latt=new HashSet();
    Set ratt=new HashSet();
    int l=-1;
    int r=-1;
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
      if(rs1.next())
      {
       l=rs1.getInt("RS_id") ;
      }
    query1.Close();
     rs1=query1.select("select  RS_id from IISC_RSC_RS_SET,IISC_RS_Constraint  where IISC_RS_Constraint.RHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id and  IISC_RS_Constraint.PR_ID="+ pr +" and IISC_RS_Constraint.AS_id="+as +" and IISC_RS_Constraint.RSC_id="+idIntegrity);
      if(rs1.next())
      {
       r=rs1.getInt("RS_id") ;
      }
    query1.Close();
    
   Set Rset=getRelationSchemes();
    Iterator it2=ASet.iterator();
    RelationScheme relcorespr=new RelationScheme(),relcorespl=new RelationScheme();
   RelationScheme relr=getRelationScheme(r),rell= getRelationScheme(l);
    while(it2.hasNext())
   {
    boolean rhave=false,lhave=false;
    int insl=0,insr=0,modl=0,modr=0,dell=0,delr=0;
    String ass= it2.next().toString();
    Set RF=get_referentials((new Integer(ass)).intValue());
    rs1=query1.select("select * from IISC_APP_SYSTEM where  AS_id=" + ass);
    if(rs1.next())
    str="<a href=as="+rs1.getString("AS_id")+">" +rs1.getString("AS_name")+"</a>";
    query1.Close();
     rs1=query1.select("select *  from IISC_RS_SR where PR_id=" + pr + " and  AS_id=" + as+ " and  SR_as_id=" + ass + " and RS_id="+ l);
   if(rs1.next())
   {
   relcorespl=getRelationScheme( rs1.getInt("SR_id"));
   lhave=true;
       rs2=query2.select("select RSR_insert,RSR_modify,RSR_delete  from IISC_RS_ROLE where  PR_id=" + pr + " and  AS_id=" + ass+ " and RS_id="+ relcorespl.id);
  if(rs2.next())
  { modl=rs2.getInt(2);
    insl=rs2.getInt(1);
    dell=rs2.getInt(3);
   }
  query2.Close();
   }
  query1.Close(); 
   rs1=query1.select("select *  from IISC_RS_SR where PR_id=" + pr + " and  AS_id=" + as+ " and  SR_as_id=" + ass + " and RS_id="+ r);
   if(rs1.next())
   {
   relcorespr=getRelationScheme( rs1.getInt("SR_id"));
   rhave=true;
    rs2=query2.select("select RSR_insert,RSR_modify,RSR_delete  from IISC_RS_ROLE where  PR_id=" + pr + " and  AS_id=" + ass+ " and RS_id="+ relcorespr.id);
  if(rs2.next())
  { modr=rs2.getInt(2);
    insr=rs2.getInt(1);
    delr=rs2.getInt(3);
   }
    query2.Close();
   }
  query1.Close(); 
  Set lset=new HashSet();
  Set rset=new HashSet();
  lset.add(""+l);
  rset.add(""+r);
  Iterator it=RF.iterator();
  boolean can =true;
  while(it.hasNext()&& can )
  {Integrity integr=(Integrity)it.next();
  //System.out.println((new Integer(integr.left.iterator().next().toString())).intValue());
  RelationScheme relleft=getRelationScheme((new Integer(integr.left.iterator().next().toString())).intValue());
  RelationScheme relright=getRelationScheme((new Integer(integr.right.iterator().next().toString())).intValue());
  RelationScheme relle=getRelationScheme(l);
   RelationScheme relre=getRelationScheme(r);
  //if(t==integr.type && equalSets(integr.leftatt,latt) && equalSets(integr.rightatt,ratt))  
  if(t==integr.type && equalSets(integr.leftatt,latt) && equalSets(integr.rightatt,ratt) &&  equalSets(Presjek(relle.atributi,relleft.atributi), relleft.atributi) && equalSets(Presjek(relre.atributi,relright.atributi), relright.atributi))
  can=false;
  }
  if(can)
 { if(lhave && rhave && ((insl==1 && !Presjek(relcorespl.atributi,latt).isEmpty())  ||  (modl==1 && !Presjek(relcorespl.mod_atributi,latt).isEmpty() ||  (modr==1 && !Presjek(relcorespr.mod_atributi,ratt).isEmpty()) || delr==1)) )
 {     
 string=string +  "<tr><td nowrap><b><font color=#DD0000>X</font></b> collision</td><td nowrap><a href=integrity="+ idIntegrity +">" +  name + "</a></td><td nowrap>" +  str + "</td><td>&nbsp;</td></tr>";
  warning=0;
  }
   if(lhave && !rhave && ((insl==1 && !Presjek(relcorespl.atributi,latt).isEmpty())  ||  (modl==1 && !Presjek(relcorespl.mod_atributi,latt).isEmpty() )))
 {     string=string+ "<tr><td nowrap><b><font color=#DD0000>X</font></b> collision</td><td nowrap><a href=integrity="+ idIntegrity +">" +  name + "</a><td nowrap>" +  str + "</td><td nowrap><a href="+ relr.id +">" + relr.name + "</a></td></tr>";
        warning=0;
  }
 if(!lhave && rhave && ((modr==1 && !Presjek(relcorespr.mod_atributi,ratt).isEmpty()) || delr==1))
 {    if(modr==1 && !Presjek(relcorespr.mod_atributi,ratt).isEmpty())
      warning=0;
      if(warning==0)
      string=string +  "<tr><td nowrap><b><font color=#DD0000>X</font></b> collision</td><td nowrap><a href=integrity="+ idIntegrity +">" +  name + "</a><td nowrap>" +  str + "</td><td nowrap><a href="+ rell.id +">" +  rell.name + "</a></td></tr>";     
      else
      string=string +  "<tr><td nowrap><b><font color=#0000DD>!</font></b> warning</td><td nowrap><a href=integrity="+ idIntegrity +">" +  name + "</a><td nowrap>" +  str + "</td><td nowrap><a href="+ rell.id +">" +  rell.name + "</a></td></tr>";     

  }
 }
   }
    }
 query.Close();
    if(!string.equals("") || !string1.equals(""))
    {
     rs=query.select("select max(CL_id)+1  from IISC_COLLISION_LOG");
      int idl=0;
      if(rs.next())
      idl=rs.getInt(1);
      query.Close();
           query.update("insert into IISC_COLLISION_LOG(CL_id,PR_id,AS_id,CL_type,CL_date,CL_text,CL_warning) values ("+ idl +","+ pr +","+ as +",4,'" + ODBCList.now() + "','"+ string + "',"+warning + ")");
     return -1;
    }
    }
catch(Exception e)
    {
      e.printStackTrace();
        }
        
return 0;
  }
public Set graf_nivo(int no , int id)
 {
   try{
     JDBCQuery query=new JDBCQuery(con);
     JDBCQuery query1=new JDBCQuery(con);
     ResultSet rs,rs1;
     Set pom=new HashSet();
if(id==-1) 
rs1=query1.select("select *  from IISC_GRAPH_CLOSURE where PR_id=" + pr + " and GC_edge_type=0 and  AS_id=" + as);
else
rs1=query1.select("select *  from IISC_GRAPH_CLOSURE where RS_superior=" + id + " and GC_edge_type=0 and  PR_id=" + pr + " and  AS_id=" + as);
while(rs1.next())
{ int j=rs1.getInt("RS_superior");
  int k=rs1.getInt("RS_inferior");
if(id==-1) 
{
  rs=query.select("select *  from IISC_GRAPH_CLOSURE where RS_inferior=" + j + " and  PR_id=" + pr + " and GC_edge_type=0 and  AS_id=" + as);
if(!rs.next())
{ if(no==0)
  pom.add(""+j);
  else
  pom.addAll(graf_nivo(no-1,j));
}
query.Close();
}
else
{if(no==0)
  pom.add(""+k);
  else
  pom.addAll(graf_nivo(no-1,k));
}
}
query1.Close();
return pom;
 }
catch(Exception e)
    {
      e.printStackTrace();
      return null;
    }

 }


 public Set getIncDep()
 {Set Iset=new HashSet();
    try{
   ResultSet rs,rs1;
   JDBCQuery query=new JDBCQuery(con);
   JDBCQuery query1=new JDBCQuery(con);
   rs1=query.select("select *  from IISC_INCLUSION_DEPENDENCY where PR_id=" + pr);
while(rs1.next())
{
int id1=rs1.getInt("ID_id");
rs=query1.select("select a.Att_id,b.Att_id  from IISC_INC_DEP_LHS_RHS as a,IISC_INC_DEP_LHS_RHS as b where a.ID_id=" + id1 + " and b.ID_id=" + id1 + " and a.ID_lhs_rhs=" + 0  + " and b.ID_lhs_rhs=" +1+ " and a.PR_id="+pr);
Set[] pom=new HashSet[2];
pom[1]=new HashSet();
pom[0]=new HashSet();
while(rs.next())
{
pom[0].add(""+rs.getInt(1)); 
pom[1].add(""+rs.getInt(2)); 
}
Iset.add(pom);
query1.Close();
}
query.Close();
}
catch(Exception e)
    {
      e.printStackTrace();
    }
return Iset;
 }
  public  Set zatvaranjeIncDep(Set a)
  {Set b=new HashSet();
   b.addAll(a);
   Iterator it=a.iterator();
   Set[] pom=new HashSet[2];
   pom[1]=new HashSet();
   pom[0]=new HashSet();
while(it.hasNext())
{
 pom=(Set[])it.next();
 Iterator it1=b.iterator();
 Set[] pom1=new HashSet[2];
 pom1[1]=new HashSet();
 pom1[0]=new HashSet();
 while(it1.hasNext())
  {
  pom1=(Set[])it1.next();
  if(equalSets(pom[1],pom1[0])  && !equalSets(pom[0],pom1[1]))
  {Set[] pom2=new HashSet[2];
  pom2[1]=new HashSet();
  pom2[0]=new HashSet();
   pom2[0].addAll(pom[0]);
   pom2[1].addAll(pom1[1]);
   if(!a.contains(pom2))
   {b.add(pom2);
    a.add(pom2);
    it=a.iterator(); 
    it1=b.iterator();
    break;
   }
  }
  }
}

return b;

  }
 public Set getFunDep()
 {Set Fset=new HashSet();
    try{
   ResultSet rs,rs1;
   JDBCQuery query=new JDBCQuery(con);
   JDBCQuery query1=new JDBCQuery(con);
   rs1=query.select("select *  from IISC_F_NF_DEP where PR_id=" + pr + " and  AS_id=" + as);
while(rs1.next())
{FunDep pom=new FunDep();
pom.conn=con;
int id1=rs1.getInt("FNF_id");
int lhs=rs1.getInt("FNF_lhs");
int rhs=rs1.getInt("FNF_rhs");
String mark=rs1.getString("NFD_mark_att");
if(mark==null)mark="";
rs=query1.select("select *  from IISC_FNF_LHS_RHS where FNFLR_id=" + lhs + " and PR_id=" + pr + " and  AS_id=" + as);
while(rs.next())pom.left.add(""+rs.getInt("Att_id")); 
query1.Close();
if(mark.equals(""))
{
rs=query1.select("select *  from IISC_FNF_LHS_RHS where FNFLR_id=" + rhs + " and PR_id=" + pr + " and  AS_id=" + as);
while(rs.next())pom.right.add(""+rs.getInt("Att_id")); 
query1.Close();
pom.fun=true;
}
else
pom.right.add(mark);
pom.type=true;
Fset.add(pom);
}
query.Close();
}
catch(Exception e)
    {
      e.printStackTrace();
    }
return Fset;
 }
public void graf_zatvaranja()
 {
   try{
   int id;
   Set Fset=new HashSet();
   Set RS=new HashSet();
   Set Graph=new HashSet();
   JDBCQuery query=new JDBCQuery(con);
   JDBCQuery query1=new JDBCQuery(con);
     ResultSet rs,rs1;
RS=getRelationSchemes();
Fset=(Set)((Object[])getFunDepSchemeTrue(getRelationSchemes()))[0];
Iterator it=RS.iterator();
while(it.hasNext())
{
RelationScheme r=(RelationScheme)it.next();
Iterator it1=RS.iterator();
while(it1.hasNext())
{RelationScheme r1=(RelationScheme)it1.next();
 if(r.id!=r1.id)
 {
   if(zatvaranje(r.atributi,Fset).containsAll(r1.atributi))
   {
    int ind=0;
    Iterator it2=RS.iterator();
    while(it2.hasNext())
    {RelationScheme r2=(RelationScheme)it2.next();
     if(r.id!=r2.id && r1.id!=r2.id )
     {
       if( zatvaranje(r2.atributi,Fset).containsAll(r1.atributi) && zatvaranje(r.atributi,Fset).containsAll(r2.atributi)) ind=1;
     }
   }
   if(ind==0)
   {
         int[] poms=new int[2];
         poms[0]=r.id;
         poms[1]=r1.id;
         Graph.add(poms);
   }
 }
} 
}
}
it=Graph.iterator();
while(it.hasNext())
{ 
int[] pomn=(int[])it.next();
rs1=query.select("select max(GC_id)+1  from IISC_GRAPH_CLOSURE");
int idl;
if(rs1.next())
idl=rs1.getInt(1);
else 
idl=0;
query.Close();
query.update("insert into IISC_GRAPH_CLOSURE(GC_id,PR_id,AS_id,RSC_id,RS_superior,RS_inferior,GC_edge_type) values ("+ idl +","+ pr +","+ as +",null," + pomn[0] + "," + pomn[1] + ",0)"); 
}
 }
catch(Exception e)
    {
      e.printStackTrace();
    }

 }
 public Set zatvaranje(Set a, Set s)
  {Set b=new HashSet();
  b.addAll(a);
  Iterator it=s.iterator();
   FunDep fun=new FunDep();
while(it.hasNext())
{
 fun=(FunDep)it.next();
if (b.containsAll(fun.left) && !b.containsAll(fun.right))
{ b.addAll(fun.right);
it=s.iterator();
}
}

return b;

  }



public  Set atributi(Set a)
  {Set b=new HashSet();
  Iterator it=a.iterator();
   FunDep fun=new FunDep();
while(it.hasNext())
{
 fun=(FunDep)it.next();
 b.addAll(fun.left);
 b.addAll(fun.right); 
}
return b;
  }

 public  Set lhs(Set a)
  {Set b=new HashSet();
  Iterator it=a.iterator();
   FunDep fun=new FunDep();
while(it.hasNext())
{
fun=(FunDep)it.next();
if(!b.contains(fun.left))
 b.add(fun.left);
}
return b;
}


 public  Set rhs(Set a)
  {Set b=new HashSet();
  Iterator it=a.iterator();
   FunDep fun=new FunDep();
while(it.hasNext())
{
fun=(FunDep)it.next();
if(!b.contains(fun.right))
 b.add(fun.right);
}
return b;
}

  
 public  boolean redukuj(Set F)
    { Iterator it=F.iterator();
String pom =new String();
Set pomSet =new HashSet();
 while(it.hasNext() )
 {FunDep b=new FunDep();
b=(FunDep)(it.next());
Iterator ito= b.left.iterator();
pomSet.clear();
 while(ito.hasNext())
 { pom=(String)ito.next();
Set pom1 =new HashSet();
 if(pomSet.contains(pom))continue;
pom1.addAll(b.left);
pom1.remove(pom);
 if(!pom1.isEmpty())
 {
if(zatvaranje(pom1,F).containsAll(b.right))
{b.left.remove(pom);
System.out.println();
System.out.println("redukovano " +pom);
b.print();
 }
ito= b.left.iterator();
pomSet.add(pom);
 }
ito= b.left.iterator();

pomSet.add(pom);
 }
 
 } 
 
  return true;
  }

 public  boolean eliminisi(Set F)
  {Iterator it=F.iterator();
  while(it.hasNext() )
 {
FunDep b=new FunDep();
b=(FunDep)(it.next());
if(!b.fun && b.left.size()==1)
it.remove();
  }
  return true;
  }



 public boolean neredudantno_pokrivanje(Set F, Set T)
  {Iterator it=F.iterator();
Set pom =new HashSet();
  while(it.hasNext() )
 {
FunDep b=new FunDep();
b=(FunDep)(it.next());
if(pom.contains(b))continue;
F.remove(b);
if(!zatvaranje(b.left,F).containsAll(Razlika(b.right,T)) || (!b.fun && !lhs(F).contains(b.left)))
F.add(b);
pom.add(b);
 it=F.iterator();
  }
  return true;
  }

 public  Set particioniranje(Set F)
  {
Set pom =new HashSet();
pom =lhs(F);
Set partSet =new HashSet();
PartSet pSet;
Iterator it=pom.iterator();
 while(it.hasNext())
 {
 pSet=new PartSet();
 Set fun1 =new HashSet();
 fun1=(Set)it.next();
 pSet.X.addAll(fun1);
Iterator itf=F.iterator();
  while(itf.hasNext())
 {FunDep fun =new FunDep();
fun=(FunDep)itf.next();
 if(fun.left.hashCode()== fun1.hashCode())
 {pSet.FunDeps.add(fun);
 }
  }
   
  partSet.add(pSet); 
 }
  return partSet;
  }

 public  Set izdvajanje(Set Ps,Set Fset)
  {
 Set J =new HashSet();
Iterator it=Ps.iterator();
while(it.hasNext())
{
Set p=((PartSet)it.next()).FunDeps;
if(p.isEmpty())continue;
boolean tp=((FunDep)(p.iterator().next())).type;
Set l=(Set)(lhs(p)).iterator().next();
Iterator it1=Ps.iterator();
while(it1.hasNext())
{PartSet prt=(PartSet)it1.next();
Set p1=(prt).FunDeps;
if(p1.isEmpty())continue;
boolean tp1=((FunDep)(p1.iterator().next())).type;
Set l1=(Set)(lhs(p1)).iterator().next();
 if(!equalSets(p,p1))
 {
   if(equalSets(zatvaranje(l,Fset),zatvaranje(l1,Fset)))
   {
   J.add(new FunDep(l,l1,true,con,tp));
    J.add(new FunDep(l1,l,true,con,tp1));
    p.addAll(p1);
    Set pom=new HashSet();
   Set l2= Unija(l,l1);
    Iterator it2=p.iterator();
    
    while(it2.hasNext())
    {
      FunDep f=(FunDep)it2.next();
      if((equalSets(l1,f.left) || equalSets(l,f.left)) && l2.containsAll( f.right))
      it2.remove();
    }
   p1.clear();
   prt.X.clear();
   }
 }
} 
}
it=Ps.iterator();
 while(it.hasNext())
 {
 PartSet p= (PartSet)it.next();
if(p.FunDeps.isEmpty() && p.X.isEmpty())
{it.remove();
it=Ps.iterator();}
 }
  return J;
  }



 public  Set nadji_tranzitivne(Set Pset,Set J)
  {
Set pomSet=new HashSet();
Set L=new HashSet();
Iterator it=Pset.iterator();
while(it.hasNext())
{
  pomSet.addAll(((PartSet)it.next()).FunDeps);
}
it=pomSet.iterator();
Set pom =new HashSet();
pom.addAll(J);
pom.addAll(pomSet);
while(it.hasNext())
{
FunDep f=new FunDep();
f=(FunDep)it.next();
pom.remove(f);
Set r=new HashSet();
r.add(f);
if(zatvaranje(f.left,pom).containsAll(f.right))L.add(f);
}

return L;
  }



 public  boolean rekonstrukcija(Set Pset, Set J, Set L, Set Fset)
  {
Iterator it=Pset.iterator();
while(it.hasNext())
{PartSet pom = new PartSet();
Set pom1=new HashSet();
pom=(PartSet)it.next();
pom1=pom.X;
pom.FunDeps.removeAll(L);
Iterator it1=J.iterator();
while(it1.hasNext())
{ FunDep f=new FunDep();
f=(FunDep)it1.next();
if(equalSets(zatvaranje(pom1,Fset),zatvaranje(f.left,Fset)))
pom.FunDeps.add(f);
}
}

return true;
  }

 public  Set generisi_seme(Set Pset, Set T)
  {
Set S =new HashSet();
Set RSatt =new HashSet();
Iterator it=Pset.iterator();
while(it.hasNext())
{
Set pom = new HashSet();
RelationScheme N = new RelationScheme();
PartSet p= (PartSet)it.next();
if (p.FunDeps.isEmpty())continue;
pom.addAll(p.FunDeps);
Set pom1 = new HashSet();
Set pom2 = new HashSet();
Iterator it1=pom.iterator();
while(it1.hasNext())
{
FunDep fun=(FunDep)it1.next();
  if(fun.type==true)
  pom1.add(fun);
  else
  pom2.add(fun);
}
N.atributi.addAll(atributi(pom));
if(RSatt.contains(N.atributi))continue;
RSatt.add(N.atributi);
N.atributi.removeAll(Presjek(N.atributi,T));
Set pp=lhs(pom1);
Set pp1=new HashSet();
pp1.addAll(pp);
it1=pp.iterator();
while(it1.hasNext())
{Iterator it2=pp1.iterator();
Set pm=(Set)it1.next();
while(it2.hasNext())
{Set pm1=(Set)it2.next();
  if(pm.containsAll(pm1) && !(pm1.containsAll(pm)))
 { pp.remove(pm);
 it1=pp.iterator();
 }
}
}
N.kljuc.addAll(pp);
it1=lhs(pom2).iterator();
while(it1.hasNext())
{Set un=(Set)it1.next();
if(N.atributi.containsAll(un)&& !N.kljuc.contains(un))
N.unique.add(un);
}
S.add(N);
}
return S;
  }
  public  boolean sadrzi(Set Fset, RelationScheme r)
  {
  Iterator it=Fset.iterator();
  while(it.hasNext())
  {
    RelationScheme rs=(RelationScheme)it.next();
    if(equalSets(rs.atributi,r.atributi) && equalSets(rs.kljuc,r.kljuc))
    return true;
  }
  return false;
  }
 public  boolean prikazi(Set Fset)
  {Iterator it=Fset.iterator();
  System.out.print("{");
 while(it.hasNext() )
 { boolean b;
b=((FunDep)(it.next())).print();
if (it.hasNext() )
System.out.print(", ");
 }
  System.out.println("}"); 
 return true;
  } 
   public  void sacuvaj(Set Fset)
 {
   try{
   int id,idl,idr,i;
   JDBCQuery query=new JDBCQuery(con);
     ResultSet rs,rs1;
Iterator it=Fset.iterator();
while(it.hasNext())
{
rs1=query.select("select max(FNF_id)+1  from IISC_F_NF_DEP");
if(rs1.next())
id=rs1.getInt(1);
else 
id=0;
query.Close();
FunDep pom=(FunDep)it.next(); 
Set left=pom.left;
Set right=pom.right;

rs1=query.select("select max(FNFLR_id)+1  from IISC_FNF_LHS_RHS");
if(rs1.next())
idl=rs1.getInt(1);
else 
idl=0;
query.Close();
Iterator it1=left.iterator();
while(it1.hasNext())
{
i=query.update("insert into IISC_FNF_LHS_RHS(FNFLR_id,PR_id,AS_id,Att_id) values ("+ idl +","+ pr +","+ as +"," + it1.next() + ")");
}
it1=right.iterator();
if(pom.fun)
{
rs1=query.select("select max(FNFLR_id)+1  from IISC_FNF_LHS_RHS");
if(rs1.next())
idr=rs1.getInt(1);
else 
idr=0;
query.Close();
while(it1.hasNext())
{
i=query.update("insert into IISC_FNF_LHS_RHS(FNFLR_id,PR_id,AS_id,Att_id) values ("+ idr +","+ pr +","+ as +"," + it1.next() + ")");
}
i=query.update("insert into IISC_F_NF_DEP(FNF_id,PR_id,AS_id,FNF_lhs,FNF_rhs,NFD_mark_att) values ("+ id +","+ pr +","+ as +","+ idl +","+ idr +",'')");
}
else
i=query.update("insert into IISC_F_NF_DEP(FNF_id,PR_id,AS_id,FNF_lhs,FNF_rhs,NFD_mark_att) values ("+ id +","+ pr +","+ as +","+ idl +",NULL,'" + it1.next() + "')");
}
 }
catch(Exception e)
    {
      e.printStackTrace();
    }


 }
  public  void dodatni_kljucevi(RelationScheme RS,Set FS)
 {
 /*
 Set pomKey=new HashSet();
 pomKey.addAll(RS.kljuc);
 Set pomKey1=new HashSet();
 pomKey1.addAll(RS.kljuc);
 Iterator it=pomKey1.iterator();
  while(it.hasNext())
 {
 Set key=(Set)it.next();
 Iterator itf=FS.iterator();
  while(itf.hasNext())
 {
 FunDep fd=(FunDep)itf.next();
 int t=0;
 
 Set pomleft=new HashSet();
 pomleft.addAll(fd.left);
 Set pomright=new HashSet();
 pomright.addAll(fd.right);
 Set pomk =new HashSet();
 pomk.addAll(key);
 pomk.removeAll(pomright);
 pomk.addAll(pomleft);
 
  Iterator itk=pomKey.iterator();
 
  while(itk.hasNext() && t==0)
 {
 
 if(pomk.containsAll((Set)itk.next()))t=1;
 }
 if(t==0)
 {
 if(equalSets(Presjek(red(pomk,FS),RS.atributi),red(pomk,FS)) )
 RS.kljuc.add(red(pomk,FS));
 }
 }
 }
 */
 Set FS1=new HashSet();
 Iterator it=FS.iterator();
 while(it.hasNext())
 {
 FunDep fun=(FunDep)it.next();
 if(fun.type)
 FS1.add(fun);
 }
 Set v=PartitivniSkup(RS.atributi);
 it=v.iterator();
 while(it.hasNext())
 {
    Set pom=(Set)it.next();
    if(zatvaranje(pom,FS1).containsAll(RS.atributi))
    { boolean can=true;
    Set v1=PartitivniSkup(pom);
     Iterator it1=v1.iterator();
     while(it1.hasNext() && can)
    { Set pom1=(Set)it1.next();
    if(!equalSets(pom1,pom) && zatvaranje(pom1,FS1).containsAll(RS.atributi))can=false;
    }
    Iterator itk=RS.kljuc.iterator();
    while(itk.hasNext())
    if(pom.containsAll((Set)itk.next()))can=false;
    if(can)
    if(!RS.kljuc.contains(pom) && !RS.unique.contains(pom))
    RS.kljuc.add(pom); 
    }
 }
 }
  public  Set red(Set pom,Set FS)
 {Set pomk=new HashSet();
 pomk.addAll(pom);
  Iterator itf=FS.iterator();
  while(itf.hasNext())
 {FunDep fun=(FunDep)itf.next();
if(pomk.containsAll(fun.right))
{ pomk.removeAll(fun.right);
 pomk.addAll(fun.left);}
 }
 return pomk;
 }
 
  public Set getCorespTob(RelationScheme RS )
 {  try{
    boolean go=false;
    Set tob=new HashSet();
   String str=new String();
   JDBCQuery query=new JDBCQuery(con),query1=new JDBCQuery(con);
   ResultSet rs,rs1;
    rs1=query1.select("select * from IISC_APP_SYSTEM_CONTAIN   where AS_id="+ as +" " );
    while(rs1.next())
    {int j=rs1.getInt("AS_id_con");
    str=str+" or AS_id="+j ;
    }
    query1.Close();
   rs=query.select("select distinct(TOB_id)  from IISC_TF_APPSYS,IISC_ATT_TOB where   IISC_TF_APPSYS.TF_id=IISC_ATT_TOB.TF_id and IISC_TF_APPSYS.PR_id="+ pr +"   and (IISC_TF_APPSYS.AS_id="+ as + str + " )" );
   while(rs.next())
   {
   int j=rs.getInt(1);  
    Set s=new HashSet();
     rs1=query1.select("select * from IISC_ATT_TOB  where PR_id="+ pr + " and TOB_id="+j );
    while(rs1.next())
    {s.add(""+rs1.getInt("Att_id"));
     }
    query1.Close();
   if(RS.atributi.containsAll(s))
   { 
    tob.add(""+j);
   }

    s=new HashSet();
    int rbr=-1;
     rs1=query1.select("select * from IISC_ATT_KTO  where PR_id="+ pr + " and TOB_id="+j );
    while(rs1.next())
    {int rbr1=rs1.getInt("TOB_rbrk");
    if(rbr1!=rbr)
     { if(RS.kljuc.contains(s)&& ! s.isEmpty())
       { 
       tob.add(""+j);
       }
     s=new HashSet();  
     }
     s.add(""+rs1.getInt("Att_id"));
     }
     query1.Close();
     if(RS.kljuc.contains(s)&& ! s.isEmpty())
       { 
       tob.add(""+j);
       }
   }
   query.Close();
  rs=query.select("select distinct(TOB_id)  from IISC_APP_SYS_REFERENCE,IISC_ATT_TOB where IISC_APP_SYS_REFERENCE.TF_id=IISC_ATT_TOB.TF_id and IISC_APP_SYS_REFERENCE.AS_id="+ as +  " " );
   while(rs.next())
   {
   int j=rs.getInt(1);  
    Set s=new HashSet();
     rs1=query1.select("select * from IISC_ATT_TOB  where PR_id="+ pr + " and TOB_id="+j );
    while(rs1.next())
    {s.add(""+rs1.getInt("Att_id"));
     }
    query1.Close();
   if(RS.atributi.containsAll(s))
   { 
    tob.add(""+j);
   }
   
   s=new HashSet();
    int rbr=-1;
     rs1=query1.select("select * from IISC_ATT_KTO  where PR_id="+ pr + " and TOB_id="+j );
    while(rs1.next())
    {int rbr1=rs1.getInt("TOB_rbrk");
    if(rbr1!=rbr)
     { if(RS.kljuc.contains(s)&& ! s.isEmpty())
       { 
       tob.add(""+j);
       }
     s=new HashSet();  
     }
     s.add(""+rs1.getInt("Att_id"));
     }
     query1.Close();
     if(RS.kljuc.contains(s)&& ! s.isEmpty())
       { 
       tob.add(""+j);
       }
   }
   query.Close();
      return tob;
       }
      
catch(Exception e)
    {
      e.printStackTrace();
    } 
return null;
 }
   public Set getCorespFormType(RelationScheme RS )
 {  try{
    boolean go=false;
    Set tob=new HashSet();
   String str=new String();
   JDBCQuery query=new JDBCQuery(con),query1=new JDBCQuery(con);
   ResultSet rs,rs1;
    rs1=query1.select("select * from IISC_APP_SYSTEM_CONTAIN   where AS_id="+ as +" " );
    while(rs1.next())
    {int j=rs1.getInt("AS_id_con");
    str=str+" or AS_id="+j ;
    }
    query1.Close();
   rs=query.select("select distinct(TOB_id),IISC_ATT_TOB.TF_id  from IISC_TF_APPSYS,IISC_ATT_TOB where   IISC_TF_APPSYS.TF_id=IISC_ATT_TOB.TF_id and IISC_TF_APPSYS.PR_id="+ pr +"   and (IISC_TF_APPSYS.AS_id="+ as + str + " )" );
   while(rs.next())
   {
   int j=rs.getInt(1);  
    int k=rs.getInt(2);  
    Set s=new HashSet();
     rs1=query1.select("select * from IISC_ATT_TOB  where PR_id="+ pr + " and TOB_id="+j );
    while(rs1.next())
    {s.add(""+rs1.getInt("Att_id"));
     }
    query1.Close();
   if(!(Presjek(RS.atributi,s)).isEmpty() && !tob.contains(""+k))
   { 
    tob.add(""+k);
   }
   }
   query.Close();
     rs=query.select("select distinct(TOB_id),IISC_APP_SYS_REFERENCE.TF_id  from IISC_APP_SYS_REFERENCE,IISC_ATT_TOB where IISC_APP_SYS_REFERENCE.TF_id=IISC_ATT_TOB.TF_id and IISC_APP_SYS_REFERENCE.PR_id="+ pr +" and IISC_APP_SYS_REFERENCE.AS_id="+ as + "" );
   while(rs.next())
   {
   int j=rs.getInt(1);  
    int k=rs.getInt(2);  
    Set s=new HashSet();
     rs1=query1.select("select * from IISC_ATT_TOB  where PR_id="+ pr + " and TOB_id="+j );
    while(rs1.next())
    {s.add(""+rs1.getInt("Att_id"));
     }
    query1.Close();
   if(RS.atributi.containsAll(s)&& !tob.contains(""+k))
   { 
    tob.add(""+k);
   }
   }
   query.Close();
      return tob;
       }
      
catch(Exception e)
    {
      e.printStackTrace();
    } 
return null;
 }
 public  String getCorespTobAtt(RelationScheme RS, String att )
 {  try{
  String str=new String();
   JDBCQuery query=new JDBCQuery(con),query1=new JDBCQuery(con);
   ResultSet rs,rs1;
    boolean go=false;
 Set TOB=getCorespTob(RS);
 Iterator it=TOB.iterator();
 while(it.hasNext())
   {
   String j=it.next().toString();  
    Set s=new HashSet();
     rs1=query1.select("select * from IISC_ATT_TOB  where Att_id="+ att + " and PR_id="+ pr + " and TOB_id="+j );
   if(rs1.next())
    {
    query1.Close();
    return j;
     }
    query1.Close();
   }
 }     
catch(Exception e)
    {
      e.printStackTrace();
    } 
return null;
 }
    public  void sacuvaj_semu(RelationScheme RS)
 {
   try{
   int id,i;
   JDBCQuery query=new JDBCQuery(con), query1=new JDBCQuery(con), query2=new JDBCQuery(con), query3=new JDBCQuery(con);
     ResultSet rs,rs1,rs2,rs3;
rs1=query.select("select *  from IISC_APP_SYSTEM where  PR_id="+ pr +"  and AS_id="+ as );
String mnem=new String();
if(rs1.next())
mnem=rs1.getString("AS_name");
query.Close();
rs1=query.select("select max(RS_id)+1  from IISC_RELATION_SCHEME");
if(rs1.next())
id=rs1.getInt(1);
else 
id=0;
query.Close();

Iterator it=RS.kljuc.iterator();
while(it.hasNext())
{Set ke=(Set)it.next();
Iterator it1=ke.iterator();
String quer=new String("IISC_RS_NAME");
Set keys=new HashSet();
while(it1.hasNext())
{String kl=(String)it1.next();
keys.add(kl);
quer="(" +quer+ ") INNER JOIN IISC_RS_NAME_KEY as rs" + kl +" on (IISC_RS_NAME.RS_id_key=rs" + kl +".RS_id_key and rs" + kl +".Att_id="+ kl +")";
}
quer="select * from " + quer+" order by IISC_RS_NAME.RS_id_key ";

rs1=query.select(quer);

while(rs1.next() && RS.name.equals(""))
{
rs2=query1.select("select count(*) from IISC_RS_NAME_KEY where RS_id_key="+rs1.getString("RS_id_key"));
if(rs2.next())
{if(rs2.getInt(1)==keys.size())
  RS.name=rs1.getString("RS_name");
}
//query1.Close();
}
query.Close();
keys.clear();
}
if(RS.name.length()==0)
{
  Set forms=getCorespTob(RS);
  Iterator it1=forms.iterator();
  while(it1.hasNext())
 {
 rs1=query.select("select *  from IISC_COMPONENT_TYPE_OBJECT_TYPE  where PR_id="+pr+" and TOB_id="+ it1.next());
if(rs1.next())
{
if(RS.name.equals(""))
RS.name=rs1.getString("TOB_mnem");
else
RS.name=RS.name+"_"+rs1.getString("TOB_mnem");
}
query.Close();
}

}
if(!RS.name.equals(""))
{int k=0;
  if(getRelationScheme(RS.name))k=k+1;
  while (getRelationScheme(RS.name+""+k))
  k++;
  if(k>0)
  RS.name=RS.name+""+k;
}
if(RS.name.length()==0)RS.name="RS_" +mnem+ "_"+ id;
i=query.update("insert into IISC_RELATION_SCHEME(RS_id,PR_id,AS_id,RS_name,RS_desc,RS_propagate) values ("+ id +","+ pr +","+ as +",'"+ RS.name +"','',0)");
int ins=0,m=0,d=0,r=0;
Set TOB=getCorespTob(RS);
Set TOB1=new HashSet();
TOB1.addAll(TOB);
it=TOB.iterator();
while(it.hasNext())
{
String tob=it.next().toString();
rs1=query.select("select *  from IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_TF_APPSYS  where  IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_id=IISC_TF_APPSYS.TF_id and  IISC_TF_APPSYS.PR_id="+pr+" and TOB_id="+ tob);
if(rs1.next())
{
int ass =rs1.getInt("AS_id");
String qstr=new String();
if(ass!=as)qstr=" Tob_local=0 and ";
if(rs1.getString("TOB_insallow").equals("Y"))ins=1;
if(rs1.getString("TOB_updallow").equals("Y"))m=1;
if(rs1.getString("TOB_deleteallow").equals("Y"))d=1;
if(rs1.getString("TOB_queallow").equals("Y"))r=1;
/*rs=query1.select("select *  from IISC_UNIQUE_TOB  where  " + qstr + " PR_id="+pr+" and TOB_id="+ tob);
while(rs.next())
{
int local=0;
int rbru =rs.getInt("TOB_rbrk");
rs2=query2.select("select *  from IISC_ATT_UTO  where  Tob_rbrk="+ rbru +" and  PR_id="+pr+" and TOB_id="+ tob);
while(rs2.next())
{
int att =rs2.getInt("ATT_id");
int arbru =rs2.getInt("ATT_rbrk");
query3.update("insert into IISC_RS_UNIQUE values ("+ id +","+ att +","+ pr +","+ as +","+ rbru +","+ arbru +","+ local +")");
Iterator it2=TOB1.iterator();
while(it2.hasNext() && local==0)
{rs3=query3.select("select  TOB_local  from IISC_ATT_UTO,IISC_UNIQUE_TOB where IISC_ATT_UTO.TOB_id=IISC_UNIQUE_TOB.TOB_id and  IISC_ATT_UTO.TOB_rbrk=IISC_UNIQUE_TOB.TOB_rbrk and ATT_id="+ att +" and IISC_UNIQUE_TOB.PR_id="+pr+" and  IISC_UNIQUE_TOB.TOB_id="+it2.next());
if(rs3.next())
{
int loc=rs3.getInt(1);
if(loc==1)
local=1;
else if(loc==0)
local=0;}
query3.Close();
}
if(local==1)
query3.update("update IISC_RS_UNIQUE  set RS_local=1 where RS_rbru="+ rbru +"  and RS_id="+ id +"  and PR_id="+ pr +"  and AS_id="+ as);

}
query2.Close();
}
query1.Close();*/
}
query.Close();

}
query.update("insert into IISC_RS_ROLE(RS_id,PR_id,AS_id,RSR_read,RSR_insert,RSR_modify,RSR_delete) values("+id+","+ pr +","+as +","+ r +","+ ins +","+ m +","+ d +")");  
RS.id=id;
 it=RS.atributi.iterator();
int g=0;
while(it.hasNext())
{
int modify=0;
int nlv=0;
String sat= it.next().toString();
int gg=-1;
rs1=query.select("select  W_order,W_updallow,W_nullallow,W_mand  from IISC_ATT_TOB where ATT_id="+ sat +" and PR_id="+pr+" and TOB_id="+getCorespTobAtt(RS,sat));
while(rs1.next())
{
int gg1=rs1.getInt(1);
if(gg<0 || gg<gg1)gg=gg1;
if(rs1.getString(2).equals("Y"))
modify=1;
if(rs1.getString(3).equals("Y") && rs1.getString(4).equals("N"))
nlv=1;
}

query.Close();
if(RS.mod_atributi.contains(sat))modify=1;

Iterator it1=RS.kljuc.iterator();
while(it1.hasNext()&& nlv==1)
{
if(((Set)it1.next()).contains(sat))nlv=0;
}

if(RS.null_atributi.contains(sat)&& nlv==0)nlv=1;
i=query.update("insert into IISC_RS_ATT(RS_id,PR_id,AS_id,Att_id,Att_sequence,Att_null,Att_modifiable) values ("+ id +","+ pr +","+ as +"," + sat + ","+ gg +","+ nlv +","+ modify +")");
g++;}
g=0;
rs1=query.select("select  Att_id  from  IISC_RS_ATT where RS_id="+ id +" and IISC_RS_ATT.PR_id="+pr+" order by Att_sequence");
if(rs1.next())
{
query1.update("update IISC_RS_ATT set Att_sequence="+g+ " where  RS_id="+ id +" and PR_id="+pr+" and Att_id="+rs1.getInt(1));
g++;}
query.Close();
it=RS.kljuc.iterator();
int j=1;
while(it.hasNext())
{
int pk=0;
int can=0;
int local=0;

Set pom=(Set)it.next();
if (RS.kandidati.contains(pom))can=1;
if (RS.primarni_kljuc.containsAll(pom))pk=1;
i=query.update("insert into IISC_RS_KEY(RS_id,RS_rbrk,PR_id,AS_id,RS_candidate,RS_primary_key,RS_local) values ("+ id +","+ j +","+ pr +","+ as +","+ can +","+ pk +","+ local +")");
Iterator it1=pom.iterator();
int k=1;
while(it1.hasNext())
{
String ida=it1.next().toString();
Iterator it2=TOB.iterator();
while(it2.hasNext())
{String tb=it2.next().toString();
rs1=query.select("select min(Tob_local) from IISC_ATT_KTO,IISC_KEY_TOB where  IISC_ATT_KTO.TOB_id=IISC_KEY_TOB.TOB_id and  IISC_ATT_KTO.TOB_rbrk=IISC_KEY_TOB.TOB_rbrk and ATT_id="+ ida +" and IISC_KEY_TOB.PR_id="+pr+" and  IISC_KEY_TOB.TOB_id="+tb);
if(rs1.next())
{
int loc=rs1.getInt(1);
if(loc==1)
local=1;
else if(loc==0)
local=0;}
if(local==0) break;
query.Close();
}

it2=TOB.iterator();
while(it2.hasNext() && k==1)
{String tb=it2.next().toString();
rs1=query.select("select count(*) from IISC_ATT_TOB where W_nullallow='N' and  ATT_id="+ ida +" and PR_id="+pr+" and  TOB_id="+tb);
if(rs1.next())
{ 
if(rs1.getInt(1)>0)
k=0;
}
query.Close();
}

i=query.update("insert into IISC_RSK_ATT(RS_id,Att_id,PR_id,AS_id,RS_rbrk,Att_rbrk) values ("+ id +","+ ida +","+ pr +","+ as +","+ j +","+ k +")");
if(pk==1)
query.update("update IISC_RS_ATT  set Att_null=0 where Att_id="+ ida +"  and RS_id="+ id +"  and PR_id="+ pr +"  and AS_id="+ as);
k++;
}
if(local==1)
query.update("update IISC_RS_KEY  set RS_local=1 where RS_rbrk="+ j +"  and RS_id="+ id +"  and PR_id="+ pr +"  and AS_id="+ as);

j++;
 }
it=RS.unique.iterator();
 j=1;
while(it.hasNext())
{
int local=0;
Set pom=(Set)it.next();
Iterator it1=pom.iterator();
int k=1;
while(it1.hasNext())
{
String ida=it1.next().toString();
Iterator it2=TOB.iterator();
i=query.update("insert into IISC_RS_UNIQUE(RS_id,Att_id,PR_id,AS_id,RS_rbru,Att_rbru) values ("+ id +","+ ida +","+ pr +","+ as +","+ j +","+ k +")");
k++;
}
j++;
 }
   }
catch(Exception e)
    {
      e.printStackTrace();
    }


 }
 
     public Set getSystems(int i)
   { JDBCQuery query=new JDBCQuery(con);
  JDBCQuery query1=new JDBCQuery(con);
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
 
         public Set getRefFormSystems(int i)
   { JDBCQuery query=new JDBCQuery(con);
  JDBCQuery query1=new JDBCQuery(con);
  ResultSet rs1,rs;
   try
    {Set app=new HashSet();
    rs1=query1.select("select IISC_TF_APPSYS.AS_id from IISC_APP_SYS_REFERENCE, IISC_FORM_TYPE, IISC_TF_APPSYS  where IISC_APP_SYS_REFERENCE.AS_id="+i +" and IISC_APP_SYS_REFERENCE.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id" );
    while(rs1.next())
    {int j=rs1.getInt(1);
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
 public String getLog(Set FunSet)
 {
   String str="";
   Iterator it=FunSet.iterator();
   while (it.hasNext())
   str=str+((FunDep)it.next()).getPrint()+"<br>";
   return str;
 }
  public  void Synth(Set FunSet,Set T) 
  {
  JDBCQuery query=new JDBCQuery(con);
  JDBCQuery query1=new JDBCQuery(con);
  ResultSet rs1,rs;
  String log="";
   try
    {
  boolean bo ; 
  Set pFunSet= new HashSet();
  Set LSet=new HashSet();
  Set jSet= new HashSet();
  Set SR= new HashSet();
  Object[] S;
  Iterator it=FunSet.iterator();
while(it.hasNext())
{((FunDep)it.next()).print();
System.out.println();
}
    rs=query.select("select *  from IISC_COLLISION_LOG where CL_obsolete=0  and CL_type=10 and PR_id=" + pr + " and  AS_id=" + as);
    if(rs.next())
    log=rs.getString("CL_text");
    query.Close();  
Set pomFunSet= new HashSet();
pomFunSet.addAll(FunSet);
System.out.print("Pocetni skup:");
prikazi(FunSet);
//eliminisi(FunSet);
//System.out.print("Eliminacija nekorektnih nefun. odnosa:");
//prikazi(FunSet);
redukuj(FunSet);
log=log+"<tr><td bgcolor=#CCCCCC><div align=center><strong>List of FDs and NFDs after reduction</strong></div></td></tr><tr><td>"+getLog(FunSet)+"</td></tr>";
System.out.print("Redukcija:");
prikazi(FunSet);
neredudantno_pokrivanje(FunSet,T);
System.out.print("Neredudantni skup:");
prikazi(FunSet);
log=log+"<tr><td bgcolor=#CCCCCC><div align=center><strong>List of FDs and NFDs after removing redundant dependencies</strong></div></td></tr><tr><td>"+getLog(FunSet)+"</td></tr>";
pFunSet=particioniranje(FunSet);
System.out.print("Particije:");
Iterator its=pFunSet.iterator();
log=log+"<tr><td bgcolor=#CCCCCC><div align=center><strong>Partition of FDs and NFDs</strong></div></td></tr><tr><td>";
  while(its.hasNext())
 {
 Set f=((PartSet)its.next()).FunDeps;
 log=log+getLog(f)+"<br>";
prikazi(f);
 }
  
log=log+ "</td></tr>";
jSet=izdvajanje(pFunSet,FunSet);
its=pFunSet.iterator();
 //System.out.println("Posle izdvajanja exvivalentnih");
  while(its.hasNext())
 {
prikazi(((PartSet)its.next()).FunDeps);
 } 
 System.out.println();
 System.out.print("Ekvivalentne:");
 prikazi(jSet);

 LSet= nadji_tranzitivne(pFunSet,jSet);
System.out.print("Tranzitivne:");
 prikazi(LSet); 
log=log+"<tr><td bgcolor=#CCCCCC><div align=center><strong>List of transitive FDs and NFDs after partitioning</strong></div></td></tr><tr><td>"+getLog(LSet)+"</td></tr>";

rekonstrukcija(pFunSet,jSet, LSet,FunSet);
System.out.print("Rekonstrukcija:");
its=pFunSet.iterator();
log=log+"<tr><td bgcolor=#CCCCCC><div align=center><strong>Reconstructed partition of FDs and NFDs after reduction</strong></div></td></tr><tr><td>";
  while(its.hasNext())
 {
 Set f=((PartSet)its.next()).FunDeps;
 log=log+getLog(f)+"<br>";
prikazi(f);
 }
   Iterator itf=FunSet.iterator();
while(itf.hasNext())
{((FunDep)itf.next()).print();
System.out.println();
}
 System.out.print("Seme relacija:");
 SR=generisi_seme(pFunSet,T);
its=SR.iterator();
log=log+"";
 while(its.hasNext())
 {RelationScheme sema=new RelationScheme();
 sema=(RelationScheme)its.next();
  dodatni_kljucevi(sema,FunSet);
  sacuvaj_semu(sema);} 
  log=log+ "<tr><td bgcolor=#CCCCCC><div align=center><strong>List of generated relation schemes</strong></div></td></tr><tr><td>"+getPrintRS(getRelationSchemes())+"</td></tr>";
 Object[] obj=getFunDepScheme(SR);
 FunSet.clear();
 FunSet.addAll((Set)obj[0]);
 sacuvaj(FunSet);
 rs=query.select("update IISC_COLLISION_LOG set CL_text='"+log+"' where CL_obsolete=0 and CL_type=10 and PR_id=" + pr + " and  AS_id=" + as);
 }
   
    catch(SQLException e)
    {
      e.printStackTrace();
    }
  }
  
public Set funseme(RelationScheme r,Set FunSet)
{Set F=new HashSet();
 Iterator it=FunSet.iterator();
 while(it.hasNext())
 {
   FunDep f=(FunDep)it.next();
   if(r.atributi.containsAll(f.left) && r.atributi.containsAll(f.left))F.add(f);
 }
  return F;
}
 public  void SynthSimple(Set FunSet,Set T,Set RS) 
  {boolean bo ; 
  Set pFunSet= new HashSet();
  Set LSet=new HashSet();
  Set jSet= new HashSet();
  Set SR= new HashSet();
  Object[] S;
 Iterator it=FunSet.iterator();
while(it.hasNext())
{((FunDep)it.next()).print();
System.out.println();
}
it=RS.iterator();
while(it.hasNext())
{((RelationScheme)it.next()).print();
System.out.println();
}
pFunSet=particioniranje(FunSet);
Iterator its=pFunSet.iterator();
jSet=izdvajanje(pFunSet,FunSet);
its=pFunSet.iterator();
 LSet= nadji_tranzitivne(pFunSet,jSet);

rekonstrukcija(pFunSet,jSet, LSet,FunSet);
its=pFunSet.iterator();
 SR=generisi_seme(pFunSet,T);
its=SR.iterator();
 while(its.hasNext())
 {RelationScheme sema=new RelationScheme();
 sema=(RelationScheme)its.next();
  Iterator itsr=RS.iterator();
 while(itsr.hasNext())
 {
  RelationScheme sema1=(RelationScheme)itsr.next();
  if(sema1.kljuc.containsAll(sema.kljuc))
  {
   sema.name=sema1.name;
   Iterator itsrk=sema.kljuc.iterator();
 while(itsrk.hasNext() && sema.primarni_kljuc.isEmpty())
 {
 Set key=(Set)itsrk.next();
 if(!Presjek(sema1.primarni_kljuc,key).isEmpty())  
 sema.primarni_kljuc.addAll(sema1.primarni_kljuc);
 }
Iterator it1=sema1.unique.iterator();
 while(it1.hasNext())
 {
 Set un=(Set)it1.next();
 if(sema.atributi.containsAll(un)&& !sema.kljuc.contains(un))  
 sema.unique.add(un);
 }
  }
 }
  sacuvaj_semu(sema);
 } 
 Object[] obj=getFunDepScheme(SR);
 FunSet.clear();
 FunSet.addAll((Set)obj[0]);
 sacuvaj(FunSet);
 }
public  void integrity_Generation() 
  {Set basicFKIntegrity=new HashSet();
  Set extendedFKIntegrity=new HashSet();
   Set basicRefIntegrity=new HashSet();
    Set basicInvRefIntegrity=new HashSet();
  Set basicIncDepInvRefIntegrity=new HashSet();
  Set FunDepS=(Set)((Object[])getFunDepSchemeTrue(getRelationSchemes()))[0];
  Set graph=new HashSet();
  String strq=" aa.AS_id="+as+ " ";
   Iterator it=getSystems(as).iterator();
   while(it.hasNext())strq=strq+" or  aa.AS_id="+it.next().toString();
   it=getRefFormSystems(as).iterator();
   while(it.hasNext())strq=strq+" or  aa.AS_id="+it.next().toString();
  gen_renaming_inclusion_dependenes();
   try{
     JDBCQuery query=new JDBCQuery(con);
     ResultSet rs;
      JDBCQuery query1=new JDBCQuery(con);
     ResultSet rs1;
      JDBCQuery query2=new JDBCQuery(con);
     ResultSet rs2;
      Set RS=getRelationSchemes();
     Set pom=new HashSet();
    rs=query.select("select *  from IISC_GRAPH_CLOSURE where GC_edge_type=0 and  PR_id=" + pr + " and  AS_id=" + as);
    while(rs.next())
   {String[] str={rs.getString("RS_superior"),rs.getString("RS_inferior")};
   graph.add(str);
   }
 query.Close();
  it=graph.iterator();
   while(it.hasNext())
   {
     String[] str=(String[])it.next();
     RelationScheme r1=getRelationScheme(new Integer(str[0]).intValue()) ;
     RelationScheme r2=getRelationScheme(new Integer(str[1]).intValue()) ;
   Set r1set=new HashSet(),r2set=new HashSet(); 
    r1set.add(r1);
    r2set.add(r2);
    if(r1.atributi.containsAll(r2.primarni_kljuc))
    {
    basicFKIntegrity.add(new Integrity(0,r1set,r2set,r2.primarni_kljuc,r2.primarni_kljuc));}
    else
    {
    Set S=new HashSet();  
   
    Iterator it1=RS.iterator();
    while(it1.hasNext())
    {
      RelationScheme scheme=(RelationScheme)it1.next();
      if(zatvaranje(r1.atributi,FunDepS).containsAll(scheme.atributi))
      S.add(scheme);
    }

      Object[] obj=getFunDepSchemeTrue(S);
   Set FunDepSet=(Set)obj[0];   
    extendedFKIntegrity.add(new Integrity(1,gen_rscheme_set(r1,r2,S,FunDepSet),r2set,r2.primarni_kljuc,r2.primarni_kljuc));
 
    }
   }
   basicRefIntegrity=gen_ref_integrity(RS,(Set)((Object[])getFunDepSchemeTrue(RS))[0]);
    it= basicFKIntegrity.iterator();
   while(it.hasNext())
   {
     Integrity ri=(Integrity)it.next();
     RelationScheme rel1=getRelationScheme((new Integer(""+((RelationScheme)(ri.left.iterator().next())).id )).intValue());
     RelationScheme rel2=getRelationScheme((new Integer(""+((RelationScheme)(ri.right.iterator().next())).id)).intValue());
     Set r1set=new HashSet(),r2set=new HashSet(); 
    r1set.add(rel1);
    r2set.add(rel2);
    
     rs=query.select("select a.Tob_id,b.Tob_id  from IISC_COMPONENT_TYPE_OBJECT_TYPE as a,IISC_COMPONENT_TYPE_OBJECT_TYPE as b, IISC_TF_APPSYS as aa, IISC_TF_APPSYS as bb where (" + strq +") and  aa.PR_id=" + pr+ " and   bb.PR_id=" + pr + " and aa.Tf_id=a.Tf_id  and bb.Tf_id=b.Tf_id and b.Tob_superord=a.Tob_id and b.Tob_mandatory=1" );
 while(rs.next())
{int i=-1,j=-1;
i=rs.getInt(1);
j=rs.getInt(2);
Set iatt=new HashSet();
Set jatt=new HashSet();
Set kiatt=new HashSet();
Set kjatt=new HashSet();
rs1=query1.select("select *  from IISC_ATT_TOB  where   PR_id=" + pr+ " and Tob_id=" +  i );
 while(rs1.next())
  iatt.add(rs1.getString("Att_id"));
query1.Close(); 
rs1=query1.select("select *  from IISC_ATT_TOB  where     PR_id=" + pr+ " and Tob_id=" + j );
 while(rs1.next())
  jatt.add(rs1.getString("Att_id"));
query1.Close(); 
 
rs1=query1.select("select *  from IISC_KEY_TOB  where   PR_id=" + pr+ " and Tob_id=" +  i );
 while(rs1.next())
  {Set pom1=new HashSet();
  rs2=query2.select("select *  from IISC_ATT_KTO  where   PR_id=" + pr+ " and Tob_id=" + i + " and Tob_rbrk=" + rs1.getString("TOB_rbrk"));
  while(rs2.next())
  pom1.add(rs2.getString("Att_id"));
  query2.Close(); 
  kiatt.add(pom1);
  }
query1.Close(); 
rs1=query1.select("select *  from IISC_KEY_TOB  where   PR_id=" + pr+ " and Tob_id=" +  j );
 while(rs1.next())
  {Set pom1=new HashSet();
  rs2=query2.select("select *  from IISC_ATT_KTO  where   PR_id=" + pr+ " and Tob_id=" + j + " and Tob_rbrk=" + rs1.getString("TOB_rbrk"));
  while(rs2.next())
  pom1.add(rs2.getString("Att_id"));
  query2.Close(); 
  kjatt.add(pom1);
  }
query1.Close();  
 if(rel2.atributi.containsAll(iatt) && rel1.atributi.containsAll(jatt))
  { 
  boolean can1=false,can2=false;
  Iterator it2=kiatt.iterator();
  while(it2.hasNext())
  {
  Set poms=((Set)it2.next()); 
  Iterator it1=rel2.kljuc.iterator();
   while(it1.hasNext())
  {
  if(((Set)it1.next()).containsAll(poms))can1=true;
  }
  }
  it2=kjatt.iterator();
  while(it2.hasNext())
  {
  Set poms=((Set)it2.next()); 
  Iterator it1=rel1.kljuc.iterator();
   while(it1.hasNext())
  {
  if(((Set)it1.next()).containsAll(poms))can2=true;
  }
  }
  if(can1 && can2)
  basicInvRefIntegrity.add(new Integrity(4,r2set,r1set,rel2.primarni_kljuc,rel2.primarni_kljuc));
  }
}  
query.Close();
   
   }
   
    it= basicRefIntegrity.iterator();
   while(it.hasNext())
   {
     Integrity ri=(Integrity)it.next();
     if(ri.left.containsAll(ri.right) && ri.right.containsAll(ri.left)) continue;
     Set[] pomn=new HashSet[2];
     Set l=(Set)(ri.leftatt);
    // if(l.size()>1) continue;
     Set r=(Set)(ri.rightatt);
     pomn[1]=new HashSet();
      pomn[0]=new HashSet();
     pomn[1].addAll(l);
     pomn[0].addAll(r);
     RelationScheme rel1=getRelationScheme((new Integer(""+((RelationScheme)(ri.left.iterator().next())).id )).intValue());
     RelationScheme rel2=getRelationScheme((new Integer(""+((RelationScheme)(ri.right.iterator().next())).id)).intValue());
     Set r1set=new HashSet(),r2set=new HashSet(); 
     r1set.add(rel1);
     r2set.add(rel2);
     Set r1setatt=new HashSet(),r2setatt=new HashSet(); 
     r1setatt.addAll(pomn[1]);
     r2setatt.addAll(pomn[0]);
     Set IncDep=zatvaranjeIncDep(getIncDep());
     Iterator it2=IncDep.iterator();
     while(it2.hasNext())
     {Set[] pomr=(Set[])it2.next();
      if(equalSets(pomr[0],pomn[0])  && equalSets(pomr[1],pomn[1]) ) 
       {
      Integrity pomInt=new Integrity(6,r2set,r1set,r1setatt,r2setatt);
      if( !basicIncDepInvRefIntegrity.contains(pomInt))
      basicIncDepInvRefIntegrity.add(pomInt);    
       }
     }
    
   }
   it=basicFKIntegrity.iterator();
   while(it.hasNext())
   ((Integrity)it.next()).save(pr,as,con);
   it=extendedFKIntegrity.iterator();
   while(it.hasNext())
   ((Integrity)it.next()).save(pr,as,con);
   it=basicRefIntegrity.iterator();
   while(it.hasNext())
   {boolean can=true;
   Integrity inter=(Integrity)it.next();
   if(equalSets(inter.left,inter.right))
   {Iterator  it1=basicRefIntegrity.iterator();
   while(it1.hasNext())
   {
    Integrity inter1=(Integrity)it1.next(); 
    if(!equalSets(inter1.right,inter.right) && equalSets(inter1.left,inter.left) && equalSets(inter1.leftatt,inter.leftatt) && equalSets(inter1.rightatt,inter.rightatt))can=false;
   }
   }
   if(can)
   inter.save(pr,as,con);
   }
   it=basicInvRefIntegrity.iterator();
   while(it.hasNext())
   ((Integrity)it.next()).save(pr,as,con);
   it=basicIncDepInvRefIntegrity.iterator();
   while(it.hasNext())
   ((Integrity)it.next()).save(pr,as,con);
   }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
public Set gen_ref_integrity(Set S,Set FunDepSet)
{
try{
   JDBCQuery query=new JDBCQuery(con),query1=new JDBCQuery(con);
  ResultSet rs,rs1;
  Set gen_set=new HashSet();
  rs=query.select("select *  from IISC_INCLUSION_DEPENDENCY where  PR_id=" + pr  );
 while(rs.next())
{Set X=new HashSet();
Set Y=new HashSet();
int idep=rs.getInt("ID_id");
rs1=query1.select("select *  from IISC_INC_DEP_LHS_RHS where  PR_id=" + pr + " and   ID_lhs_rhs=0 and  ID_id=" + idep);
while(rs1.next())X.add(rs1.getString("Att_id"));
query1.Close();
rs1=query1.select("select *  from IISC_INC_DEP_LHS_RHS where  PR_id=" + pr + " and   ID_lhs_rhs=1 and  ID_id=" + idep);
while(rs1.next())Y.add(rs1.getString("Att_id"));
query1.Close();

Set N=new HashSet();
Set NX=new HashSet();
Set SX=new HashSet();
Iterator it=S.iterator();
while(it.hasNext())
{
  RelationScheme scheme=(RelationScheme)it.next();
  if(scheme.kljuc.contains(Y))
  {
   N.add(scheme);
   Iterator it1=S.iterator();
    while(it1.hasNext())
    {
     RelationScheme scheme1=(RelationScheme)it1.next();
      if(scheme1.atributi.containsAll(X))
      {
        if(SX.isEmpty())
        SX.add(scheme1);
        else
        {NX.add(scheme1);
        Iterator it2=SX.iterator();
         while(it2.hasNext()&& !NX.isEmpty())
         { RelationScheme scheme2=(RelationScheme)it2.next();
          if(zatvaranje(scheme2.atributi,FunDepSet).containsAll(scheme1.atributi)) 
            it2.remove();
          else
          {
            if(zatvaranje(scheme1.atributi,FunDepSet).containsAll(scheme2.atributi)) 
              NX.clear();
          }
         }
        SX.addAll(NX);
        }
      }
    }
  }
}
if(!N.isEmpty() && !SX.isEmpty())
{ it=SX.iterator();
  while(it.hasNext())
  {Set N1=new HashSet();
   N1.add((RelationScheme)it.next());
  Integrity integr=new Integrity(2,N1,N,X,Y);
 if(!gen_set.contains(integr))
 gen_set.add(integr);
  
  }
}

it=S.iterator();
while(it.hasNext())
{RelationScheme rel=(RelationScheme)it.next();
 Set R =new HashSet();
 R.add(rel);
 if(rel.atributi.containsAll(X) && rel.atributi.containsAll(Y))
 {Integrity integr=new Integrity(2,R,R,X,Y);
Iterator iti=gen_set.iterator();
boolean can=true;
while(iti.hasNext())
{Integrity integr1=(Integrity)iti.next();
 if(equalSets(integr.left,integr1.left)&& equalSets(integr.leftatt,integr1.leftatt)&&equalSets(integr.right,integr1.right)&&equalSets(integr.rightatt,integr1.rightatt))can=false;
}
if(can)
 gen_set.add(integr);
 }
}

}
query.Close();
return gen_set;
}
catch(Exception e)
    {
      e.printStackTrace();
      return null;
    }
}
public Set gen_rscheme_set(RelationScheme r1,RelationScheme r2,Set S,Set FunDepSet)
{Set genSet=new HashSet();
Set Sig=new HashSet(),SigV=new HashSet(),V=new HashSet();
Sig.addAll(S);
SigV.add(r1);
V.addAll(r1.atributi);
genSet.add(r1);
 int j=0;
 
while( !V.containsAll(r2.primarni_kljuc) )
{

 Set Sigpom=new HashSet(),SigVpom=new HashSet(),Vpom=new HashSet(); 
 Sigpom.addAll(Sig);
SigVpom.addAll(SigV);
Vpom.addAll(V);
Sig=Razlika(Sigpom,SigVpom);
SigV.clear();
Iterator it=Sig.iterator();
while(it.hasNext())
{
  RelationScheme scheme=(RelationScheme)it.next();
  if(V.containsAll(scheme.primarni_kljuc))SigV.add(scheme);
}
it=SigV.iterator();
while(it.hasNext())
{
RelationScheme scheme=(RelationScheme)it.next();
Set FunDepS=new HashSet();
Iterator it1=FunDepSet.iterator();
while(it1.hasNext())
{
FunDep fun=(FunDep)it1.next();
fun.print();
if(scheme.kljuc.contains(fun.left) && Razlika(scheme.atributi,fun.left).contains(fun.right.iterator().next()) )
{FunDepS.add(fun);

 }
}
if(zatvaranje(V,Razlika(FunDepSet,FunDepS)).containsAll(r2.atributi))
{
it.remove();
// SigV.remove(scheme) ;
 Sig.remove(scheme) ;
 FunDepSet.removeAll(FunDepS);
}
}
genSet.addAll(SigV);
it=SigV.iterator();
while(it.hasNext())
{
RelationScheme scheme=(RelationScheme)it.next();
V.addAll(scheme.atributi);
}
}
Iterator it=genSet.iterator();
while(it.hasNext())((RelationScheme)it.next()).print();
  return genSet;
}
public void gen_renaming_inclusion_dependenes()
{
try{
   JDBCQuery query=new JDBCQuery(con);
   JDBCQuery query1=new JDBCQuery(con);
  
  ResultSet rs,rs1;
  rs=query.select("select *  from IISC_ATTRIBUTE where att_elem>0 and PR_id=" + pr  );
while(rs.next())
{int attl= rs.getInt("Att_elem");
 int attr= rs.getInt("Att_id");
 boolean go=true;
 rs1=query1.select("select * from IISC_INC_DEP_LHS_RHS as a, IISC_INC_DEP_LHS_RHS as b  where  a.ID_id=b.ID_id and a.Att_id="+attl+" and b.Att_id="+attr+" and a.PR_id="+ pr);
     if(rs1.next())
     {int dep=rs1.getInt("ID_id");
      query1.Close();
      rs1=query1.select("select count(*) from IISC_INC_DEP_LHS_RHS    where  ID_id="+ dep + " and PR_id="+ pr);
      if(rs1.next())
      if(rs1.getInt(1)==2)
      go=false;
     }
     query1.Close();
    int k=0;
 if(go){
    while(go)
     {k++;
     rs1=query1.select("select * from IISC_Inclusion_Dependency  where ID_name='Inclusion Dependency "+ k +"' and PR_id="+ pr);
     if(!rs1.next())
     {query1.Close();
     go=false;
     }
     else
     query1.Close();
     }
    
     go=true;
     rs1=query1.select("select * from IISC_Attribute where Att_id="+attl+"  and PR_id="+ pr ); 
        rs1.next();
        int al=rs1.getInt("Dom_id");
        query1.Close();
        rs1=query1.select("select * from IISC_Attribute where Att_id="+attr+"  and PR_id="+ pr ); 
        rs1.next();
        int ar=rs1.getInt("Dom_id");
        query1.Close();
       if(ar!=al)
       {go=false;
       }
      rs1=query1.select("select max(ID_id)+1 from IISC_Inclusion_Dependency"); 
      int j=0;
      if(rs1.next())
      j=rs1.getInt(1);
      query1.Close();
      query1.update("insert into IISC_Inclusion_Dependency(ID_id,PR_id,ID_name) values("+j+","+pr+",'Inclusion Dependency "+k+"')");
      // query1.update("insert into IISC_Inclusion_Dependency values("+(j+1)+","+pr+",'Inclusion Dependency "+(k+1)+"')");
       query1.update("insert into IISC_INC_DEP_LHS_RHS(ID_id,Att_id,PR_id,ID_lhs_rhs) values("+j+","+ attl +","+pr+",0)");
       query1.update("insert into IISC_INC_DEP_LHS_RHS(ID_id,Att_id,PR_id,ID_lhs_rhs) values("+j+","+ attr +","+pr+",1)");
      // query1.update("insert into IISC_INC_DEP_LHS_RHS values("+(j+1)+","+ attl +","+pr+",1)");
     //  query1.update("insert into IISC_INC_DEP_LHS_RHS values("+(j+1)+","+ attr +","+pr+",0)");
 }

}
query.Close();
}
catch(SQLException e)
    {
      e.printStackTrace();
    }
}

}