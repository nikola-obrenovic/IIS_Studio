package iisc;
import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javax.swing.JOptionPane;
import java.sql.SQLException;
public class ClassFunction 
{
  
  public ClassFunction()
  {
  
  }
  
  
int[] PKljucLeveStrane(JDBCQuery query, Connection con, int IdSema, int IdProjekta,int IdASistema)
  {
    ResultSet rs;
    int IdKljuca;    
    int[] IdAtr=null;
    String upit4 = "select RS_rbrk from IISC_RS_KEY where IISC_RS_KEY.RS_id="+IdSema+" and IISC_RS_KEY.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and RS_primary_key=1";      
    try {  
      rs=query.select(upit4);
      if(rs.next())
      {
        IdKljuca=rs.getInt(1);
        query.Close();      
        String upit5 = "select Att_id from IISC_RSK_ATT where IISC_RSK_ATT.RS_id="+IdSema+" and IISC_RSK_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and IISC_RSK_ATT.RS_rbrk="+IdKljuca+" ORDER BY Att_rbrk";
        int v=brojanjeNtorki("IISC_RSK_ATT","IISC_RSK_ATT.RS_id="+IdSema+" and IISC_RSK_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and IISC_RSK_ATT.RS_rbrk="+IdKljuca,con); 
        IdAtr=query.selectArraySAint(upit5,v,1);
        query.Close(); 
        
      } else {
      query.Close();
      }  
      return IdAtr;
    }       
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          return IdAtr;
                      }  
  }
 public int brojanjeNtorki(String SemaRel,String Uslov,Connection kon)
  {
    Connection con = kon;
    ResultSet rs;
    int j;
    
    String upit = "select count(*) from " + SemaRel + " where " + Uslov;
    try {
      JDBCQuery query=new JDBCQuery(con);    
      rs=query.select(upit);
      rs.next();      
      j=rs.getInt(1);     
      query.Close();
      return j;
    }
       catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          return 0;
                          }
  
  }
  String[] NotNullObelezja (JDBCQuery query, int IdProjekta, int IdSema, int IdASistema, Connection con, int[] IdAtr, String[] ImenaAtr)
  {
    int s =0;
    ResultSet rs;    
    String[] NotNullAtributi = null; 
    int NullVrednost;
    String upit1;
    try { 
      for (int i=0;i<IdAtr.length;i++)
      {
        upit1 = "select Att_null from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSema +" and IISC_RS_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and Att_id="+IdAtr[i];
        rs=query.select(upit1);
        if(rs.next())
        {
          NullVrednost=rs.getInt(1);
          query.Close(); 
          if (NullVrednost==0)
          {         
            s=s+1;
          }                    
        }else
        {
          query.Close(); 
        }
      } 
      NotNullAtributi = new String[s];
      s=0;
      for (int i=0;i<IdAtr.length;i++)
      {
        upit1 = "select Att_null from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSema +" and IISC_RS_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and Att_id="+IdAtr[i];
        rs=query.select(upit1);
        if(rs.next())
        {
          NullVrednost=rs.getInt(1);
          query.Close(); 
          if (NullVrednost==0)
          {            
            NotNullAtributi[s] = ImenaAtr[i];  
            s=s+1;
          }                    
        }else
        {
          query.Close(); 
        }
      } 
      return NotNullAtributi;
    }
      catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          return null;
                          }          
    
  }   
  
  String[] ModifiableObelezja (int IdProjekta, int IdSema, int IdASistema, Connection con)
  {
    int s =0;
    ResultSet rs;
    int IdKljuca;
    int[] IdAtr=null;
    int ModifiableVrednost;
    String MnemAtr;    
    JDBCQuery query=new JDBCQuery(con);
    String[] NotModifiableVrednosti = null;
    String upit4 = "select RS_rbrk from IISC_RS_KEY where IISC_RS_KEY.RS_id="+IdSema+" and IISC_RS_KEY.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and RS_primary_key=1";      
    try {  
      rs=query.select(upit4);
      if(rs.next())
      {
        IdKljuca=rs.getInt(1);
        query.Close();      
        String upit5 = "select Att_id from IISC_RSK_ATT where IISC_RSK_ATT.RS_id="+IdSema+" and IISC_RSK_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and IISC_RSK_ATT.RS_rbrk="+IdKljuca+" ORDER BY Att_rbrk";
        int v=brojanjeNtorki("IISC_RSK_ATT","IISC_RSK_ATT.RS_id="+IdSema+" and IISC_RSK_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and IISC_RSK_ATT.RS_rbrk="+IdKljuca,con); 
        IdAtr=query.selectArraySAint(upit5,v,1);
        query.Close();
                
        for (int i=0;i<IdAtr.length;i++)
        {          
          String upit1 = "select Att_modifiable from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSema +" and IISC_RS_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and Att_id="+IdAtr[i];
          rs=query.select(upit1);
          if(rs.next())
          {
            ModifiableVrednost=rs.getInt(1);            
            query.Close(); 
            if (ModifiableVrednost == 0) //ne moze se menjati
            {
              String upit6 = "select Att_mnem from IISC_ATTRIBUTE where IISC_ATTRIBUTE.PR_id="+IdProjekta+" and IISC_ATTRIBUTE.Att_id="+IdAtr[i];     
              rs=query.select(upit6);
              rs.next();
              MnemAtr=rs.getString(1);                          
              s = s+1;
            }
            } else
            {
              query.Close(); 
            }              
        }        
      } else 
      {
        query.Close();
      }
      NotModifiableVrednosti = new String[s];
      s=0;
      for (int i=0;i<IdAtr.length;i++)
        {          
          String upit1 = "select Att_modifiable from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSema +" and IISC_RS_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and Att_id="+IdAtr[i];
          rs=query.select(upit1);
          if(rs.next())
          {
            ModifiableVrednost=rs.getInt(1);            
            query.Close(); 
            if (ModifiableVrednost == 0) //ne moze se menjati
            {
              String upit6 = "select Att_mnem from IISC_ATTRIBUTE where IISC_ATTRIBUTE.PR_id="+IdProjekta+" and IISC_ATTRIBUTE.Att_id="+IdAtr[i];     
              rs=query.select(upit6);
              rs.next();
              MnemAtr=rs.getString(1);              
              NotModifiableVrednosti[s] = MnemAtr;     
              s=s+1;
            }
            } else
            {
              query.Close(); 
            }              
        }
      return NotModifiableVrednosti;
    }
     catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          return null;
                          }                         
    
  }   
  String[] FAtributName(int[] IdAtributa, Connection con, int IdProjekta)
  { 
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    int m = IdAtributa.length;  
    String[] AtributName = new String[m];     
    try {
      for (int i=0;i<m;i++)
      {
        String upit = "select Att_mnem from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributa[i];
        rs=query.select(upit);
        if (rs.next())
        {
          AtributName[i] = rs.getString("Att_mnem");                           
        }
        query.Close();
      } 
      return AtributName;          
    }
     catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          for (int i=0;i<AtributName.length;i++)
                         {
                            AtributName[i]="";
                          }
                          return AtributName;
                   }
  }  
  String[] FDefault(int[] IdAtributa, JDBCQuery query, int IdProjekta, Rekurzije Rek)
  {
    ResultSet rs;
    int m = IdAtributa.length; 
    String ADefault=null;
    int IdDomena=0;
    String[] ADefaults = new String[m];     
    try {
      for (int i=0;i<m;i++)
      {
        String upit = "select Dom_id,Att_default from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributa[i];
        rs=query.select(upit);
        if (rs.next())
        {
          IdDomena = rs.getInt("Dom_id");
          ADefault=rs.getString("Att_default");
        }
        query.Close();
        if (ADefault!=null)
          {
            ADefault=ADefault.trim();
          }
          if (ADefault!=null && ADefault.length()!=0)
          {
            ADefaults[i] = ADefault;
          }else
          {
            ADefaults[i]=Rek.RekZaDefault(IdDomena, query, IdProjekta);                   
          }
      } 
      return ADefaults;          
    }
     catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          for (int i=0;i<ADefaults.length;i++)
                         {
                            ADefaults[i]="";
                          }
                          return ADefaults;
                   }
  }  
  
  String[] ParametriFunkcijeProcedure(int[] IdAtributa, Connection con, int IdProjekta, Rekurzije Rek, int SUBP)
  {
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    int m = IdAtributa.length;  
    String[] AtributName = new String[m];
     
    try {
      for (int i=0;i<m;i++)
      {
        String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributa[i];
        rs=query.select(upit);
        if (rs.next())
        {
          AtributName[i] = rs.getString("Att_mnem");
          int IdDomena = rs.getInt("Dom_id");
          query.Close();
          String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta, SUBP); 
          int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);
          if (Duzina!=0)            
          {
              AtributName[i] = AtributName[i].replaceAll(" ","_") + " " + TipPodatka + "(" + Duzina + ")";               
          }else
          {
              AtributName[i] = AtributName[i].replaceAll(" ","_") + " " + TipPodatka;
          }
        }
        
      } 
      return AtributName;          
    }
     catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          for (int i=0;i<AtributName.length;i++)
                         {
                            AtributName[i]="";
                          }
                          return AtributName;
                   }
  }    
  String[] DeklareParametri(String[] AtributName, Connection con, int IdProjekta, Rekurzije Rek, int SUBP)
  {
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    int m = AtributName.length;    
     
    try {
      for (int i=0;i<m;i++)
      {
        String upit = "select Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_mnem='"+AtributName[i]+"'";
        rs=query.select(upit);
        if (rs.next())
        {          
          int IdDomena = rs.getInt("Dom_id");
          query.Close();
          String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta, SUBP); 
          int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);
          if (Duzina!=0)            
          {
              AtributName[i] = AtributName[i].replaceAll(" ","_") + " " + TipPodatka + "(" + Duzina + ")";               
          }else
          {
              AtributName[i] = AtributName[i].replaceAll(" ","_") + " " + TipPodatka;
          }
        }
        
      } 
      return AtributName;          
    }
     catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          for (int i=0;i<AtributName.length;i++)
                         {
                            AtributName[i]="";
                          }
                          return AtributName;
                   }
  }  
  String[] ParametriFunkcijeProcedureUpd(int[] IdAtributa, Connection con, int IdProjekta, Rekurzije Rek, int SUBP)
  {
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    int m = IdAtributa.length;  
    String[] AtributNameNiz = new String[2*m];
     
    try {
      int j=0;
      for (int i=0;i<m;i++)      
      {
        String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributa[i];
        rs=query.select(upit);
        if (rs.next())
        {
          String AtributName = rs.getString("Att_mnem");
          int IdDomena = rs.getInt("Dom_id");
          query.Close();
          String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta, SUBP); 
          int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);
          if (Duzina!=0)            
          {
              AtributNameNiz[j] = AtributName+"_Old " + TipPodatka + "(" + Duzina + ")"; 
              AtributNameNiz[j+1] = AtributName+"_New " + TipPodatka + "(" + Duzina + ")";
          }else
          {
              AtributNameNiz[j] = AtributName + "_Old " + TipPodatka;
              AtributNameNiz[j+1] = AtributName + "_New " + TipPodatka;
          }
        }
        j=j+2;
      } 
      return AtributNameNiz;          
    }
     catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          for (int i=0;i<AtributNameNiz.length;i++)
                         {
                            AtributNameNiz[i]="";
                          }
                          return AtributNameNiz;
                   }
  }    
  public String toString(int i) 
  { return i + ""; }
  
  public Set OutSemeRel(Connection con,int IdProjekta, int IdASistema, Set IdSema, Set AllIdSema)
  {
    JDBCQuery query=new JDBCQuery(con);        
    Set AllOutSeme = new HashSet();     
    String upit;    
    Set RSInferior = new HashSet();
    AllIdSema.removeAll(IdSema);         
    Set AllOutSemeJos = new HashSet();    
    try
    {
      if(!AllIdSema.isEmpty())
      {
      for (Iterator it=IdSema.iterator(); it.hasNext(); ) {
        Object element = it.next();
        upit = "select RS_inferior from IISC_GRAPH_CLOSURE where PR_id="+IdProjekta+" and AS_id="+IdASistema+ "and RS_superior="+ element;
        RSInferior = query.selectSASet(upit,1);
        query.Close();
        if (!RSInferior.isEmpty())
        {
          RSInferior.removeAll(IdSema);        
          AllOutSeme.addAll(RSInferior);
        }  
      }
      Set pom = new HashSet(); 
      pom.addAll(AllOutSeme);
      pom.removeAll(AllIdSema);
      AllOutSeme.removeAll(pom);      
      if (!AllOutSeme.isEmpty())
      {
        AllOutSemeJos.addAll(OutSemeRel(con,IdProjekta, IdASistema, AllOutSeme, AllIdSema));
        if (!AllOutSemeJos.isEmpty())
        {
          AllOutSeme.addAll(AllOutSemeJos);
        }        
      }
      } //ako je prazan AllIdSema
      return AllOutSeme;
    }
    catch (Exception ev)
    {
      JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          return null;
    }
  }   
  
  public Set AllConstraint(Connection con, int IdProjekta, int IdASistema, Set IdSema)
  {
    JDBCQuery query=new JDBCQuery(con);        
    Set AllConstraints = new HashSet();
    Set ConstraintsSema = new HashSet();
    String upit;
    
    ResultSet rs;
    Set IdSemaRelL = new HashSet();
    Set IdSemaRelR = new HashSet();
    try
    {
        for (Iterator it=IdSema.iterator(); it.hasNext(); ) 
        {
          Object element = it.next();
          upit = "select RSC_id from IISC_GRAPH_CLOSURE where PR_id="+IdProjekta+" and AS_id="+IdASistema+ "and (RS_superior="+ element + " or RS_inferior=" + element + ")";
          Set Constr = new HashSet();
          Constr = query.selectSASet(upit,1);
          query.Close();
          ConstraintsSema.addAll(Constr);          
        }
        for (Iterator it=ConstraintsSema.iterator(); it.hasNext(); ) 
        {
          Object element = it.next();
          upit = "select LHS_RS_Set_id,RHS_RS_Set_id from IISC_RS_CONSTRAINT where PR_id="+IdProjekta+" and RSC_id="+ element +" and AS_id="+IdASistema; 
          rs=query.select(upit);              
              if(rs.next())
              {
                int LHSRSId = rs.getInt("LHS_RS_Set_id");
                int RHSRSId = rs.getInt("RHS_RS_Set_id");
                query.Close();
                upit = "select RS_id from IISC_RSC_RS_SET where PR_id="+IdProjekta+" and RS_Set_id="+LHSRSId+"and AS_id="+IdASistema;  
                IdSemaRelL=query.selectSASet(upit,1);
                query.Close();
                upit = "select RS_id from IISC_RSC_RS_SET where PR_id="+IdProjekta+" and RS_Set_id="+RHSRSId+" and AS_id="+IdASistema;                  
                IdSemaRelR=query.selectSASet(upit,1);
                query.Close();
                IdSemaRelL.addAll(IdSemaRelR);
                if (IdSema.containsAll(IdSemaRelL))
                {
                  AllConstraints.add(element);
                }
              } else
              {
                query.Close();
              }
        }
        return AllConstraints;
    }
    catch (Exception ev)
    {
      JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          return null;
    }
  }
  
  

}