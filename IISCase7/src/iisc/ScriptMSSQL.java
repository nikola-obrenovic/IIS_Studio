package iisc;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.io.*;

import java.util.ArrayList;

public class ScriptMSSQL 
{
  protected Connection con;
  protected int IdASistema;
  protected int IdProjekta;
  protected int[] IdSema;
  protected String[] ImenaSemaRel;
  protected Table[] Tabele; 
  protected int[] Constr;  
  /*nobrenovic: start*/
  protected TriggerMSSQL Trigeri = new TriggerMSSQL(); 
  /*nobrenovic: end*/  
  protected ClassFunction Pom = new ClassFunction();
  protected Rekurzije Rek = new Rekurzije();  
  protected boolean alter;  
  //PrintWriter out;
  protected PrintWriter outWarning;
  protected String PrefixNaziva;
  protected boolean OneFileOnly;
  protected boolean Ogranicenja, IndeksiP,IndeksiF, IndeksiU; 
  protected boolean Triger;
  protected String FileIme;
  protected String ImeBaze;
  protected boolean View;
  protected String SQLusername;
  protected String SQLpassword;
  protected String SQLhost;
  protected Date d = new Date();
  
  /*nobrenovic: start*/
  protected CheckConstraint.ImplemetationType chkConImplType;
  /*nobrenovic: stop*/
  
  
  public ScriptMSSQL()
  {
  }
  public ScriptMSSQL(Connection Kon, int PrId, int AsId, int[] SemeRelId, String[] SemeRelName,boolean Promena, int[] Constraint, PrintWriter warningf, String PrefixNazivaDat, boolean OneFile,boolean IndP, boolean IndF, boolean IndU, CheckConstraint.ImplemetationType chkConImplType, boolean Ogr, boolean Trg, String FileName, String NameBase, boolean Pogled, String usernamep, String passwordp, String hostp)
  {
    ImeBaze = NameBase;
    con = Kon;
    IdASistema = AsId;
    IdProjekta = PrId;
    IdSema = SemeRelId;    
    ImenaSemaRel = SemeRelName;
    alter = Promena;
    Constr = Constraint;
    //out = outf;
    outWarning = warningf;
    PrefixNaziva = PrefixNazivaDat;
    OneFileOnly = OneFile;
    IndeksiP = IndP;
    IndeksiF = IndF;
    IndeksiU = IndU;
    Ogranicenja = Ogr;
    Triger = Trg;
    FileIme = FileName;
    View = Pogled;
    SQLusername = usernamep;
    SQLpassword = passwordp;
    SQLhost = hostp;
    this.chkConImplType = chkConImplType;
    
  }
  
  
  protected int PodaciZaTabele(Connection con)
  {            
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(con);    
    int l=0;
       
    int j=IdSema.length; 
    Tabele = new Table[j];    
    for (int i=0; i < Tabele.length; i++)
      if(Tabele[i] == null)
        Tabele[i]= new Table();
      
    try {    
    for(int i=0; i < j; i++) { 
      l=0;
      Tabele[i].setTableId(IdSema[i]);
      Tabele[i].setImeTabele(ImenaSemaRel[i].replaceAll(" ","_")); 
      int k=Pom.brojanjeNtorki("IISC_RS_ATT","PR_id="+IdProjekta+" and RS_id="+IdSema[i]+" and AS_id="+IdASistema,con); //broji koliko ima atributa odgovarajuca sema relacije
      String upit1 = "select Att_id,Att_null from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSema[i]+" and IISC_RS_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" ORDER BY Att_sequence";
      String[] IdAtributa=query.selectArraySA(upit1,k,1); 
      int[] NullVrednosti=query.selectArraySAint(upit1,k,2);
      query.Close(); 
      
      Tabele[i].ImenaAtributa =Tabele[i].KreirajNizAtr(k); 
      Tabele[i].TipPodataka=Tabele[i].KreirajNizTip(k); 
      Tabele[i].Izrazi=Tabele[i].KreirajNizIzrazi(k);  
      Tabele[i].Nulls=Tabele[i].KreirajNizNull(k); 
      Tabele[i].Defaultovi=Tabele[i].KreirajNizDefault(k);
      
      //primarni kljuc
      int IdKljuca;
      String CeoKljuc="";
      
      String upit4 = "select RS_rbrk from IISC_RS_KEY where IISC_RS_KEY.RS_id="+IdSema[i]+" and IISC_RS_KEY.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and RS_primary_key=1";      
      rs=query.select(upit4);
      if(rs.next())
      {
        IdKljuca=rs.getInt(1);
        query.Close();      
        String upit5 = "select Att_id from IISC_RSK_ATT where IISC_RSK_ATT.RS_id="+IdSema[i]+" and IISC_RSK_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and IISC_RSK_ATT.RS_rbrk="+IdKljuca+" ORDER BY Att_rbrk";
        int v=Pom.brojanjeNtorki("IISC_RSK_ATT","IISC_RSK_ATT.RS_id="+IdSema[i]+" and IISC_RSK_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and IISC_RSK_ATT.RS_rbrk="+IdKljuca,con); 
        int[] IdAtr=query.selectArraySAint(upit5,v,1);
        query.Close();
        String[] Kljuc= new String[IdAtr.length];
        Tabele[i].Kljuc=Tabele[i].KreirajNizKljuc(IdAtr.length);
        for (int s=0;s<IdAtr.length;s++)
        {
          String upit6 = "select Att_mnem from IISC_ATTRIBUTE where IISC_ATTRIBUTE.PR_id="+IdProjekta+" and IISC_ATTRIBUTE.Att_id="+IdAtr[s];     
          rs=query.select(upit6);
          rs.next();
          String MnemAtr=rs.getString(1);
          query.Close();
          CeoKljuc=CeoKljuc+"["+MnemAtr.replaceAll(" ","_")+"]";
          Kljuc[s]=MnemAtr.replaceAll(" ","_");
          if (s!=IdAtr.length-1)
          {
            CeoKljuc=CeoKljuc+",";
          }
          Tabele[i].setAtributKljuca(s,Kljuc[s]);
        }
        Tabele[i].setPKljuc(CeoKljuc);  
        
      } else 
      {
        query.Close();
      }
      
      //alternativni kljucevi unique      
      
      int b=Pom.brojanjeNtorki("IISC_RS_UNIQUE","PR_id="+IdProjekta+" and RS_id="+IdSema[i]+" and AS_id="+IdASistema,con); //koliko jos ima uniquova            
      int a=Pom.brojanjeNtorki("IISC_RS_KEY","PR_id="+IdProjekta+" and RS_id="+IdSema[i]+" and AS_id="+IdASistema+" and RS_primary_key=0",con); //broji koliko ima alternativnih kljuceva
      int g;
    if (a+b!=0)
    {  
      Tabele[i].Uniqueovi=Tabele[i].KreirajNizUniq(a+b);
      String upitU = "select RS_rbrk from IISC_RS_KEY where IISC_RS_KEY.RS_id="+IdSema[i]+" and IISC_RS_KEY.PR_id="+IdProjekta+" and IISC_RS_KEY.AS_id="+IdASistema+" and RS_primary_key=0";            
      String[] Uniqueovi=query.selectArraySA(upitU,a,1);      
      query.Close();      
      for (g=0;g<a;g++)
      { 
        CeoKljuc="";
        String upit5 = "select Att_id from IISC_RSK_ATT where IISC_RSK_ATT.RS_id="+IdSema[i]+" and IISC_RSK_ATT.PR_id="+IdProjekta+" and IISC_RSK_ATT.AS_id="+IdASistema+" and IISC_RSK_ATT.RS_rbrk="+Uniqueovi[g]+" ORDER BY Att_rbrk";
        int v=Pom.brojanjeNtorki("IISC_RSK_ATT","IISC_RSK_ATT.RS_id="+IdSema[i]+" and IISC_RSK_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and IISC_RSK_ATT.RS_rbrk="+Uniqueovi[g],con); 
        int[] IdAtr=query.selectArraySAint(upit5,v,1);
        query.Close();
        for (int s=0;s<IdAtr.length;s++)
        {
          String upit6 = "select Att_mnem from IISC_ATTRIBUTE where IISC_ATTRIBUTE.PR_id="+IdProjekta+" and IISC_ATTRIBUTE.Att_id="+IdAtr[s];     
          rs=query.select(upit6);
          rs.next();
          String MnemAtr=rs.getString(1);
          query.Close();
          CeoKljuc=CeoKljuc+"["+MnemAtr.replaceAll(" ","_")+"]";
          if (s!=IdAtr.length-1)
          {
            CeoKljuc=CeoKljuc+",";
          }
        }
        //Tabele[i].setUnique(g,CeoKljuc); 
        Tabele[i].setUnique(g,CeoKljuc);        
      }  
      
      String upitUQ = "select distinct RS_rbru from IISC_RS_UNIQUE where IISC_RS_UNIQUE.RS_id="+IdSema[i]+" and IISC_RS_UNIQUE.PR_id="+IdProjekta+" and IISC_RS_UNIQUE.AS_id="+IdASistema;      
      String[] Uniqueovi2=query.selectArraySA(upitUQ,b,1);
      query.Close();
      
      for (int p=0; p<Uniqueovi2.length;p++)
      {
        String upitQ = "select Att_id from IISC_RS_UNIQUE where IISC_RS_UNIQUE.RS_id="+IdSema[i]+" and IISC_RS_UNIQUE.PR_id="+IdProjekta+" and IISC_RS_UNIQUE.AS_id="+IdASistema+" and RS_rbru="+Uniqueovi2[p]+" ORDER BY Att_rbru"; 
        int c=Pom.brojanjeNtorki("IISC_RS_UNIQUE","PR_id="+IdProjekta+" and RS_id="+IdSema[i]+" and AS_id="+IdASistema+" and RS_rbru="+Uniqueovi2[p],con);             
        String[] AtrUniqua=query.selectArraySA(upitQ,c,1);
        query.Close();
        CeoKljuc="";
        for (int s=0;s<AtrUniqua.length;s++)
        {
          String upit6 = "select Att_mnem from IISC_ATTRIBUTE where IISC_ATTRIBUTE.PR_id="+IdProjekta+" and IISC_ATTRIBUTE.Att_id="+AtrUniqua[s];     
          rs=query.select(upit6);
          rs.next();
          String MnemAtr=rs.getString(1);
          query.Close();
          CeoKljuc=CeoKljuc+"["+MnemAtr.replaceAll(" ","_")+"]";
          if (s!=AtrUniqua.length-1)
          {
            CeoKljuc=CeoKljuc+",";
          }
        }
        Tabele[i].setUnique(g+p,CeoKljuc);        
      }
    } 
     
      for(int m=0; m < k; m++){        
        String upit2 = "select Att_mnem,Dom_id,Att_expr,Att_default from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributa[m]+" and Att_sbp=1"; 
        rs=query.select(upit2);
        if(rs.next())
        {  
          l=l+1;
          String ImeAtr=rs.getString(1);
          int IdDomena = rs.getInt(2);
          String AIzraz=rs.getString(3);
          String ADefault=rs.getString(4);
          query.Close();
          String Izraz;
          
          Tabele[i].setImeAtributa(m,ImeAtr.replaceAll(" ","_")); 
             
          /*nobrenovic: start*/   
          ArrayList<CheckConstraint> chkCons = CheckConGenHelper.findDomAttrChkCon(con,IdProjekta,IdASistema,IdDomena, Integer.parseInt(IdAtributa[m]));
          if(chkCons != null && chkCons.size()>0)
            Tabele[i].getAttrChkCons().put(ImeAtr, chkCons);
          /*nobrenovic: stop*/
                   
          String DomenDefault;          
          String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta,1); 
          int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);
          if (Duzina!=0)            
            {
              TipPodatka="["+TipPodatka+"]" + " (" + Duzina + ")";               
            }else
            {
              TipPodatka="["+TipPodatka+"]";
            }              
          Tabele[i].setTip(m,TipPodatka);
          
          if (NullVrednosti[m]==0)
          {              
              Tabele[i].setNullNotNull(m,1);                             
          }  
          
          if (ADefault!=null)
          {
            ADefault=ADefault.trim();
          }
          if (ADefault!=null && ADefault.length()!=0)
          {
            Tabele[i].setDefault(m,ADefault);
          }else
          {
            DomenDefault=Rek.RekZaDefault(IdDomena, query, IdProjekta);            
            if (DomenDefault!="")
            {
              Tabele[i].setDefault(m,DomenDefault);              
            }         
          }   
        } else 
        {
        query.Close();
        }
      }    //for m  
      Tabele[i].setBrAtributa(l);
      
      /*nobrenovic: start*/
      //find check constraints related to the this scheme
      rs=query.select("select IISC_CHECK_CONSTRAINT.CHKC_id  from IISC_RELATION_SCHEME, IISC_RSC_RS_SET, IISC_CHECK_CONSTRAINT " +
                   " where IISC_RELATION_SCHEME.PR_id = " + IdProjekta + 
                   " and IISC_RELATION_SCHEME.AS_id="  + IdASistema + 
                   " and IISC_RELATION_SCHEME.RS_id=" + IdSema[i] +
                   " and IISC_RELATION_SCHEME.RS_id=IISC_RSC_RS_SET.RS_id" +
                   " and IISC_RELATION_SCHEME.AS_id=IISC_RSC_RS_SET.AS_id" +
                   " and IISC_RELATION_SCHEME.PR_id=IISC_RSC_RS_SET.PR_id" + 
                   " and IISC_CHECK_CONSTRAINT.RS_Set_id=IISC_RSC_RS_SET.RS_Set_id" +
                   " and IISC_CHECK_CONSTRAINT.AS_id=IISC_RSC_RS_SET.AS_id" +
                   " and IISC_CHECK_CONSTRAINT.PR_id=IISC_RSC_RS_SET.PR_id");
      while(rs.next())
      {  
        int chkConID = rs.getInt(1);
        Tabele[i].getCheckCons().add(CheckConstraint.loadCheckConstraint(con, IdProjekta, IdASistema, chkConID));
      }
      /*nobrenovic: stop*/
      
     }  //for i     
     return j;
    } //try
     catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          return 0;
                          }
  }  //Tabele
  
    
  protected void DatotkaTabele(String ImeBaze,PrintWriter out, int BrRelacija, Connection conn)
  {
     String str;
     str="";
     Statement stmt;
     ResultSet rset,rs2;
     String query;
     boolean PostojiObjekat;          
     PostojiObjekat = false;
     Set SetAtributaBaza = new HashSet();
     Set SetAtributaCase = new HashSet();
     Set AtributiADD = new HashSet();
     Set AtributiDROP = new HashSet();
     Set AtributiALTER = new HashSet();
     JDBCQuery query3=new JDBCQuery(con);
     
     if (!ImeBaze.trim().equals(""))
     {
     if (alter == false)
     {
              str = "IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'"+ImeBaze+"')";
              out.println(str);           
              str = "DROP DATABASE ["+ImeBaze.replaceAll(" ","_")+"]";
              out.println(str);
              out.println("GO");
              out.println();       
              str = "CREATE DATABASE ["+ImeBaze.replaceAll(" ","_")+"]"; //  ON (NAME = N'"+ImeBaze+"', FILENAME = N'"+Putanja+"' , SIZE = 2, FILEGROWTH = 10%) LOG ON (NAME = N'"+ImeBaze+"_log', FILENAME = N'"+PutanjaLog+"' , FILEGROWTH = 10%)";
              out.println(str);
              out.println("GO");
     }
     
     out.println();
     str = "USE "+ ImeBaze.replaceAll(" ","_");
     out.println(str);
     out.println();
     }
     if (alter == false)
     { 
      for (int i=0;i<BrRelacija;i++)
      {
        str = "if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].["+Tabele[i].getImeTabele()+"]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)";
        out.println(str);
        str = "DROP TABLE [dbo].["+Tabele[i].getImeTabele()+"]";
        str = str+"\r\n"+"GO";
        out.println(str);
        out.println();             
      }      
     }          
     for (int i=0;i<BrRelacija;i++)
      {               
            if (alter == true)
            {               
              query = "select * from dbo.sysobjects where id = object_id(N'[dbo].["+Tabele[i].getImeTabele()+"]') and OBJECTPROPERTY(id, N'IsUserTable') = 1";
              try 
              {     
                stmt = conn.createStatement();       
                rset = stmt.executeQuery(query);
                if (!rset.next ())
                {
                  PostojiObjekat = false;
                }else
                {
                  PostojiObjekat = true;
                }
               rset.close();
              stmt.close();          
              }   
              catch (Exception ex) 
              {
                ex.printStackTrace();
              }    
              if (!PostojiObjekat)
              {                 
                  String seq="";
                  str = "CREATE TABLE [dbo].["+Tabele[i].getImeTabele()+"] (";
                  out.println(str);              
                  for (int j=0; j<Tabele[i].getBrAtributa();j++)
                  {
                    str = "   ["+Tabele[i].getImeAtributa(j)+"] "+Tabele[i].getTip(j);
                    /*nobrenovic: start*/
                    if(Tabele[i].getAttrChkCons().containsKey(Tabele[i].getImeAtributa(j)))
                    {
                        String chkCon = "";
                        ArrayList<CheckConstraint> chkCons = Tabele[i].getAttrChkCons().get(Tabele[i].getImeAtributa(j));
                        if(chkCons.size() == 1)
                        {
                            if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
                                chkCon = chkCons.get(0).getDnf().toString(false);
                            else
                                chkCon = chkCons.get(0).getCnf().toString(false);
                        }
                        else if(chkCons.size() >= 1)
                        {
                            for(int k=0;k<chkCons.size();k++)
                            {   
                                if(k>0) chkCon += " AND ";
                                if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
                                    chkCon += "(" + chkCons.get(k).getDnf().toString(false) + ")";
                                else
                                    chkCon += "(" + chkCons.get(k).getCnf().toString(false) + ")";
                            }
                        }
                        chkCon = chkCon.replaceAll("VALUE",Tabele[i].getImeAtributa(j));
                        str += " CHECK (" + chkCon + ")";
                    }
                    /*nobrenovic: stop*/
                    out.print(str);   
                    String upitS = "select * from IISC_SEQUENCE where PR_id="+IdProjekta+ " and AS_id="+IdASistema;      
                    try{
                      rs2=query3.select(upitS);
                      if(rs2.next())
                      {                   
                        seq = " IDENTITY (" + rs2.getInt("Seq_start") +","+ rs2.getInt("Seq_increment") +")";
                       }
                       query3.Close();
                    }
                    catch(Exception ex){     
                      ex.printStackTrace();
                    }      
                    if (Tabele[i].getNullNotNull(j)==0)
                    {
                      str = " NULL";
                    }else
                    {
                      str = seq +" NOT NULL";
                    }
                    out.print(str);               
                    if (j<(Tabele[i].getBrAtributa()-1))
                    {
                        out.println(",");
                    }                
                  } //end for j             
                  str = "\r\n"+") ON [PRIMARY]";              
                  out.println(str);
                  str = "GO";
                  out.println(str);
                  out.println(); 
               }else
               {                  
                  
                  query = "select name from dbo.syscolumns where dbo.syscolumns.ID = (select id from dbo.sysobjects where dbo.sysobjects.ID = object_id(N'[dbo].["+ Tabele[i].getImeTabele() +"]') and (OBJECTPROPERTY(id, N'IsUserTable') = 1))";
                  try 
                  {     
                        stmt = conn.createStatement();       
                        rset = stmt.executeQuery(query);
                        while(rset.next ())
                        {
                          String O= rset.getString(1);
                          SetAtributaBaza.add(O);
                        }
                        rset.close();
                        stmt.close();          
                  }   
                  catch (Exception ex) 
                  {
                       ex.printStackTrace();
                  }    
                  for (int j=0; j<Tabele[i].getBrAtributa();j++)
                  {                    
                    SetAtributaCase.add(Tabele[i].getImeAtributa(j));
                  }
                  AtributiALTER.clear();                  
                  AtributiADD.clear();
                  AtributiDROP.clear();
                  AtributiALTER.addAll(SetAtributaCase);
                  AtributiALTER.retainAll(SetAtributaBaza);
                  AtributiADD.addAll(SetAtributaCase);
                  AtributiADD.removeAll(SetAtributaBaza);
                  AtributiDROP.addAll(SetAtributaBaza);
                  AtributiDROP.removeAll(SetAtributaCase);
                  SetAtributaCase.clear();
                  SetAtributaBaza.clear();
                  int j=0;
                 if (!AtributiDROP.isEmpty())
                {  
                  str = "ALTER TABLE [dbo].["+Tabele[i].getImeTabele()+"] DROP";
                  out.println(str);
                  for (Iterator it=AtributiDROP.iterator(); it.hasNext(); )
                  {
                    j=j+1;  
                    int t=0;
                    Object element = it.next();
                    for (int p=0; p<Tabele[i].getBrAtributa();p++)
                    {
                      if (element.equals(Tabele[i].getImeAtributa(p)))
                      {
                        t = p;
                      }
                    }
                    str = "COLUMN "+ "["+element+"]";
                    out.print(str);
                    if (j<(AtributiDROP.size()))
                    {
                      out.println(",");
                    }                    
                    for (int k = 0; k<Tabele[i].Kljuc.length;k++)
                    {
                      if (element.equals(Tabele[i].getAtributKljuca(k)))
                      {
                        str = "Attribute "+ element + " which you want drop is primary key of relation schema " + Tabele[i].getImeTabele() + "!";
                        outWarning.println(str);
                      }                      
                    } 
                  }
                  out.println();
                  out.println("GO");
                  out.println();
                }
                if (!AtributiALTER.isEmpty())
                {                   
                  j=0;
                  for (Iterator it=AtributiALTER.iterator(); it.hasNext(); )
                  {
                    j=j+1;
                    int t=0;
                    Object element = it.next();
                    for (int p=0; p<Tabele[i].getBrAtributa();p++)
                    {
                      if (element.equals(Tabele[i].getImeAtributa(p)))
                      {
                        t = p;
                      }
                    }
                    str = "ALTER TABLE [dbo].["+Tabele[i].getImeTabele()+"] ";
                    out.println(str);
                    str = "ALTER COLUMN "+ "["+element+"] "+Tabele[i].getTip(t);
                    out.println(str);                    
                    out.println("GO");
                    out.println();
                    for (int k = 0; k<Tabele[i].Kljuc.length;k++)
                    {
                      if (element.equals(Tabele[i].getAtributKljuca(k)))
                      {
                        str = "Attribute "+ element + " which you want change is primary key of relation schema " + Tabele[i].getImeTabele() + "!";
                        outWarning.println(str);
                      }                      
                    } 
                  }
                  out.println();                  
                } 
                if (!AtributiADD.isEmpty())
                {  
                  str = "ALTER TABLE [dbo].["+Tabele[i].getImeTabele()+"] ADD";
                  out.println(str);
                  j=0;
                  for (Iterator it=AtributiADD.iterator(); it.hasNext(); )
                  {
                    j=j+1;
                    int t=0;
                    Object element = it.next();
                    for (int p=0; p<Tabele[i].getBrAtributa();p++)
                    {
                      if (element.equals(Tabele[i].getImeAtributa(p)))
                      {
                        t = p;
                      }
                    }
                    str = "["+element+"] "+Tabele[i].getTip(t);
                    out.print(str);   
                    if (j<(AtributiADD.size()))
                    {
                      out.println(",");
                    }
                  } 
                  out.println();
                  str = "GO";
                  out.println(str);
                  out.println();
                } 
               }
                //  j=rset.getInt(1);                          
                   
              
            }else //ako je izmena
            { //ako je nova baza
              str = "CREATE TABLE [dbo].["+Tabele[i].getImeTabele()+"] (";                  
              out.println(str);
              for (int j=0; j<Tabele[i].getBrAtributa();j++)
              {
                String seq="";
                str = "   ["+Tabele[i].getImeAtributa(j)+"] "+Tabele[i].getTip(j);
                out.print(str);
                String upitS = "select * from IISC_SEQUENCE where PR_id="+IdProjekta+ " and AS_id="+IdASistema;      
                    try{
                      rs2=query3.select(upitS);
                      if(rs2.next())
                      {                   
                        seq = " IDENTITY (" + rs2.getInt("Seq_start") +","+ rs2.getInt("Seq_increment") +")";
                      }
                      query3.Close();
                    }
                    catch(Exception ex){     
                      ex.printStackTrace();
                    }      
                if (Tabele[i].getNullNotNull(j)==0)
                {
                  str = " NULL";
                }else
                {
                  str = seq + " NOT NULL";
                }
                out.print(str);               
                
                  /*nobrenovic: start*/
                  if(Tabele[i].getAttrChkCons().containsKey(Tabele[i].getImeAtributa(j)))
                  {
                    String chkCon = "";
                    ArrayList<CheckConstraint> chkCons = Tabele[i].getAttrChkCons().get(Tabele[i].getImeAtributa(j));
                    if(chkCons.size() == 1)
                    {
                        if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
                            chkCon = chkCons.get(0).getDnf().toString(false);
                        else
                            chkCon = chkCons.get(0).getCnf().toString(false);
                    }
                    else if(chkCons.size() >= 1)
                    {
                        for(int k=0;k<chkCons.size();k++)
                        {   
                            if(k>0) chkCon += " AND ";
                            if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
                                chkCon += "(" + chkCons.get(k).getDnf().toString(false) + ")";
                            else
                                chkCon += "(" + chkCons.get(k).getCnf().toString(false) + ")";
                        }
                    }
                    chkCon = chkCon.replaceAll("VALUE",Tabele[i].getImeAtributa(j));
                    out.print(" CHECK (" + chkCon + ")");
                  }
                  /*nobrenovic: stop*/
                  
                  
                if (j<(Tabele[i].getBrAtributa()-1))
                {
                  out.println(",");
                }                
              } //end for j             
              str = "\r\n"+") ON [PRIMARY]";              
              out.println(str);
              str = "GO";
              out.println(str);
              out.println(); 
            }  //ako je nova baza
      } //end for i   
  }
  
  protected void DatotkaIndeksi(PrintWriter out, int BrRelacija, Connection conn)
  {
     String str;
     str="";    
     Statement stmt;
     ResultSet rset;
     String query;
     
     for (int i=0;i<BrRelacija;i++)
      {        
              //indeksi
              if (IndeksiP)
              {
                if (Tabele[i].getPKljuc().length()!=0)
                {
                  if (alter)
                  {
                          query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[PK_"+Tabele[i].getImeTabele()+"]')";
                          try 
                          {     
                            stmt = conn.createStatement();       
                            rset = stmt.executeQuery(query);
                            if (rset.next ())
                            {                              
                              str = "ALTER INDEX PK_"+Tabele[i].getImeTabele();                              
                              outWarning.println("You changed primary key index PK_" + Tabele[i].getImeTabele()+".");
                            }else
                            {
                              str = "CREATE INDEX PK_"+Tabele[i].getImeTabele();
                            }
                            rset.close();
                            stmt.close();          
                          }   
                          catch (Exception ex) 
                          {
                             ex.printStackTrace();
                          }       
                  }else
                  {
                    str = "CREATE INDEX PK_"+Tabele[i].getImeTabele();
                  }
                  out.println(str);                                       
                  str = "   ON "+Tabele[i].getImeTabele();
                  out.println(str);
                  str="   (";
                  out.println(str); 
                  str = "       "+Tabele[i].getPKljuc();
                  out.println(str); 
                  str = "   )";
                  out.println(str);   
                  str = "   WITH DROP_EXISTING";
                  out.println(str);
                }
              }
              if (IndeksiU)
              {
                if (IndeksiP)
                {
                  out.println();
                }                
                if (Tabele[i].Uniqueovi!=null && Tabele[i].Uniqueovi.length!=0)
                {                    
                  for (int x=0;x<Tabele[i].Uniqueovi.length;x++)
                  {
                    if (Tabele[i].getUnique(x).length()!=0)
                    {                      
                      if (alter)
                      {
                          query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[UK_"+Tabele[i].getImeTabele()+"]')";
                          try 
                          {     
                            stmt = conn.createStatement();       
                            rset = stmt.executeQuery(query);
                            if (rset.next ())
                            {
                              str = "ALTER INDEX UK_"+Tabele[i].getImeTabele();
                            }else
                            {
                              str = "CREATE INDEX UK_"+Tabele[i].getImeTabele();
                            }
                            rset.close();
                            stmt.close();          
                          }   
                          catch (Exception ex) 
                          {
                             ex.printStackTrace();
                          }       
                      }else
                      {
                        str = "CREATE INDEX UK_"+Tabele[i].getImeTabele();
                      }  
                      out.println(str);                                       
                      str = "   ON "+Tabele[i].getImeTabele();
                      out.println(str);
                      str="   (";
                      out.println(str);
                      str = "       "+Tabele[i].getUnique(x);
                      out.println(str);
                      str= "    )";
                      out.println(str);                      
                    }                  
                  }                  
                } 
              } //indeksiU     
              out.println();  
      } //end for i   
  }
  
  protected void DatotekaConstraints(PrintWriter out, int BrRelacija, Connection conn)
  {
    String str = "";
    String alt = "";
    Statement stmt;
    ResultSet rset;
    String query;
    for (int i=0;i<BrRelacija;i++)
    {             
                alt = "";        
                str = "";
         if (alter)
         {
                if (Tabele[i].getPKljuc().length()!=0)
                {
                    query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[PK_"+Tabele[i].getImeTabele()+"]') and OBJECTPROPERTY(id, N'IsPrimaryKey') = 1";
                    try 
                    {     
                      stmt = conn.createStatement();       
                      rset = stmt.executeQuery(query);
                      if (rset.next ())
                      {
                        str = "ALTER TABLE " + Tabele[i].getImeTabele();
                        out.println(str);
                        str = "DROP CONSTRAINT [PK_"+Tabele[i].getImeTabele()+"]";
                        out.println(str);
                        out.println("GO");
                        out.println();
                      }  
                        alt = "ALTER TABLE [dbo].["+Tabele[i].getImeTabele()+"] WITH NOCHECK ADD";
                        out.println(alt);                                        
                        str = "   CONSTRAINT [PK_"+Tabele[i].getImeTabele()+"] PRIMARY KEY CLUSTERED";
                        out.println(str);                   
                        str="   (";
                        out.println(str); 
                        str = "       "+Tabele[i].getPKljuc();
                        out.println(str); 
                        str = "   ) ON [PRIMARY]";
                        out.println(str);  
                        out.println("GO");
                        out.println();                                             
                      rset.close();
                      stmt.close();          
                    }   
                    catch (Exception ex) 
                    {
                      ex.printStackTrace();
                    }                  
                    
                }
                if (Tabele[i].Uniqueovi!=null && Tabele[i].Uniqueovi.length!=0)
                {
                    for (int x=0;x<Tabele[i].Uniqueovi.length;x++)
                    {
                      if (Tabele[i].getUnique(x).length()!=0)
                      {
                          query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[AK_"+Tabele[i].getImeTabele()+"_"+x+"]') and OBJECTPROPERTY(id, N'IsUniqueCnst') = 1";
                          try 
                          {     
                            stmt = conn.createStatement();       
                            rset = stmt.executeQuery(query);
                            if (rset.next ())
                            {
                              str = "ALTER TABLE " + Tabele[i].getImeTabele();
                              out.println(str);
                              str = "DROP CONSTRAINT [AK_"+Tabele[i].getImeTabele()+"_"+x+"]";
                              out.println(str);
                              out.println("GO");
                              out.println();                         
                            }
                     alt = "ALTER TABLE [dbo].["+Tabele[i].getImeTabele()+"] WITH NOCHECK ADD";
                     out.println(alt);                                         
                    str="     CONSTRAINT [AK_"+Tabele[i].getImeTabele()+"_"+x+"]"+" UNIQUE";
                    out.println(str);
                    str="   (";
                    out.println(str);
                    str = "       "+Tabele[i].getUnique(x);
                    out.println(str);
                    str= "    )";
                    out.println(str);
                    out.println("GO");
                    out.println();
                    rset.close();
                    stmt.close();          
                  }   
                  catch (Exception ex) 
                  {
                             ex.printStackTrace();
                  }                      
                }
               }
              }
            /*nobrenovic: start*/
            for (int x=0;x<Tabele[i].getCheckCons().size();x++)
            {
                CheckConstraint chkCon = Tabele[i].getCheckCons().get(x);
                if (chkCon.getRefRelSch().size() == 1)
                {
                      query = "select * from dbo.sysobjects where id = object_id(N'[dbo].["+chkCon.getName()+"]') and OBJECTPROPERTY(id, N'IsCheckCnst') = 1";
                      try 
                      {     
                        stmt = conn.createStatement();       
                        rset = stmt.executeQuery(query);
                        if (rset.next ())
                        {
                          str = "ALTER TABLE " + Tabele[i].getImeTabele();
                          out.println(str);
                          str = "DROP CONSTRAINT [" + chkCon.getName() + "]";
                          out.println(str);
                          out.println("GO");
                          out.println();
                        }  
                        //generate new constraint
                        alt = "ALTER TABLE [dbo].["+Tabele[i].getImeTabele()+"] WITH NOCHECK ADD";
                        out.println(alt);       
                        String conStr = "";
                        if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
                           conStr = chkCon.getDnf().toString(false);
                        else 
                          conStr = chkCon.getCnf().toString(false);
                        str = "    CONSTRAINT ["+ chkCon.getName() +"] CHECK ("+ conStr +")";
                        out.println(str);                 
                        
                        out.println("GO");
                        out.println();
                        rset.close();
                        stmt.close();          
                      }   
                      catch (Exception ex) 
                      {
                         ex.printStackTrace();
                      }                       
                }
            }
            
            //multi-table check constraints  
            for (int x=0;x<Tabele[i].getCheckCons().size();x++)
            {
                CheckConstraint chkCon = Tabele[i].getCheckCons().get(x);
                if (chkCon.getRefRelSch().size() > 1)
                {
                    GenerateChkConTrigger(Tabele[i], chkCon, out);                       
                }
            }  
                
            /*nobrenovic: stop*/
               
                if (Tabele[i].Defaultovi!=null && Tabele[i].Defaultovi.length!=0)
                {                 
                  for (int m=0;m<Tabele[i].Defaultovi.length;m++)
                  {
                   if (Tabele[i].getDefault(m).length()!=0)
                   {
                          query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[DF_"+Tabele[i].getImeTabele()+"_"+Tabele[i].getImeAtributa(m)+"]') and OBJECTPROPERTY(id, N'IsConstraint') = 1";
                          try 
                          {     
                            stmt = conn.createStatement();       
                            rset = stmt.executeQuery(query);
                            if (rset.next ())
                            {
                              str = "ALTER TABLE " + Tabele[i].getImeTabele();
                              out.println(str);
                              str = "DROP CONSTRAINT [DF_"+Tabele[i].getImeTabele()+"_"+Tabele[i].getImeAtributa(m)+"]";
                              out.println(str);
                              out.println("GO");
                              out.println();
                            } 
                              for (int u=0;u<Tabele[i].Defaultovi.length;u++)
                              {                  
                                if (Tabele[i].getDefault(u).length()!=0)
                                {                                
                                  alt = "ALTER TABLE [dbo].["+Tabele[i].getImeTabele()+"] WITH NOCHECK ADD";
                                  out.println(alt);                     
                                  str = "   CONSTRAINT [DF_"+Tabele[i].getImeTabele()+"_"+Tabele[i].getImeAtributa(u)+"] DEFAULT ("+Tabele[i].getDefault(u)+") FOR ["+Tabele[i].getImeAtributa(u)+"],";
                                  out.println(str);                     
                                }   
                              }                              
                              out.println("GO");
                              out.println();
                            rset.close();
                            stmt.close();          
                          }   
                          catch (Exception ex) 
                          {
                             ex.printStackTrace();
                          }                    
                   } 
                  } 
                }  
    }else //not alter
    {
                for (int m=0;m<Tabele[i].Defaultovi.length;m++)
                {                  
                  if (Tabele[i].getDefault(m).length()!=0)
                  {
                     if (alt == "")
                     {
                        alt = "ALTER TABLE [dbo].["+Tabele[i].getImeTabele()+"] WITH NOCHECK ADD";
                        out.println(alt);
                     }
                     
                     str = "   CONSTRAINT [DF_"+Tabele[i].getImeTabele()+"_"+Tabele[i].getImeAtributa(m)+"] DEFAULT ("+Tabele[i].getDefault(m)+") FOR ["+Tabele[i].getImeAtributa(m)+"],";
                     out.println(str);                     
                  }   
                }
                if (Tabele[i].getPKljuc().length()!=0)
                {
                  if (alt == "")
                     {
                        alt = "ALTER TABLE [dbo].["+Tabele[i].getImeTabele()+"] WITH NOCHECK ADD";
                        out.println(alt);
                        
                     }                       
                  str = "   CONSTRAINT [PK_"+Tabele[i].getImeTabele()+"] PRIMARY KEY CLUSTERED";
                  out.println(str);                   
                  str="   (";
                  out.println(str); 
                  str = "       "+Tabele[i].getPKljuc();
                  out.println(str); 
                  str = "   ) ON [PRIMARY]";
                  out.print(str);                
                }
                
             if (Tabele[i].Uniqueovi!=null && Tabele[i].Uniqueovi.length!=0)
              {
                if (Tabele[i].getPKljuc().length()!=0)
                {
                    out.println(",");
                }
                for (int x=0;x<Tabele[i].Uniqueovi.length;x++)
                {
                  if (Tabele[i].getUnique(x).length()!=0)
                  {
                    if (alt == "")
                     {
                        alt = "ALTER TABLE [dbo].["+Tabele[i].getImeTabele()+"] WITH NOCHECK ADD";
                        out.println(alt);
                     }                    
                    str="     CONSTRAINT [AK_"+Tabele[i].getImeTabele()+"_"+x+"]"+" UNIQUE";
                    out.println(str);
                    str="   (";
                    out.println(str);
                    str = "       "+Tabele[i].getUnique(x);
                    out.println(str);
                    str= "    )";
                    out.print(str);
                    if (x<(Tabele[i].Uniqueovi.length-1))
                    {
                      str = ",";
                      out.print(str);                     
                    } 
                  }                  
                }                  
              } 
              /*nobrenovic: start*/
              for (int x=0;x<Tabele[i].getCheckCons().size();x++)
              {
                CheckConstraint chkCon = Tabele[i].getCheckCons().get(x);
                if (chkCon.getRefRelSch().size() == 1)
                {
                    //generate new constraint
                    if (alt == ""){
                        alt = "ALTER TABLE [dbo].["+Tabele[i].getImeTabele()+"] WITH NOCHECK ADD";
                        out.println(alt);
                    }else{
                        out.println(",");
                    }
                    
                    String conStr = "";
                    if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
                         conStr = chkCon.getDnf().toString(false);
                    else 
                        conStr = chkCon.getCnf().toString(false);
                    str = "    CONSTRAINT ["+ chkCon.getName() +"] CHECK ("+ conStr +")";
                    out.print(str);                 
                }
              }
              
              str = "\r\n"+"GO";
              out.println(str);
              out.println();
            
            //multi-table check constraints  
            for (int x=0;x<Tabele[i].getCheckCons().size();x++)
            {
              CheckConstraint chkCon = Tabele[i].getCheckCons().get(x);
              if (chkCon.getRefRelSch().size() > 1)
              {
                  GenerateChkConTrigger(Tabele[i], chkCon, out);
              }
            }
            
            /*nobrenovic: stop*/
        }
      } 
  }
  
  protected void DatotekaTrigeri(PrintWriter out, Connection con, int[] IdSemaRelL, int RSCType, int RSCRelIntType, String[] SemaRelNameL, String[] SemaRelNameR, String RSCName, int[] IdAtributaL, int[] IdAtributaR, int InsActionL, int DelActionR, int ModActionR, int ModActionL, int[] IdSemaRelR, boolean NoAction, Connection conn)
  {
    JDBCQuery query=new JDBCQuery(con);
    int[] PKljucL = Pom.PKljucLeveStrane(query, con, IdSemaRelL[0], IdProjekta, IdASistema); 
                                
    if ((RSCType == 0 || RSCType == 2) && RSCRelIntType == 1) //Osnovni i ref integritet kao posledica zavisnosti sadrzavanja PARCIJANI
    {                                           
               Trigeri.UpisOsnovniRIL(out, outWarning, PKljucL, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaL, IdAtributaR, IdProjekta, Rek, RSCRelIntType, InsActionL,Pom,IdASistema, IdSemaRelL[0], con, alter, conn);                                       
               Trigeri.UpdateOsnovniRIL(out, outWarning, PKljucL, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionL, IdAtributaL, Pom, IdASistema, IdSemaRelL[0], con, alter, conn);               
               Trigeri.BrisanjeOsnovniPRIR(out, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, DelActionR, IdAtributaL, PKljucL, Pom, IdASistema, IdSemaRelL[0], con, alter, conn);//Osnovni ref integritet bez default kad je no action - to je deklartivno vec uradjeno
               Trigeri.UpdateOsnovniPRIR(out, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionR, IdAtributaL,PKljucL, Pom, IdASistema, IdSemaRelL[0], con, alter,conn);
               
    } else
    if ((RSCType == 0 || RSCType == 2) && RSCRelIntType == 2) //Osnovni i ref integritet kao posledica zavisnosti sadrzavanja full
    {
               Trigeri.UpisOsnovniRIL(out, outWarning, PKljucL, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaL, IdAtributaR, IdProjekta, Rek, RSCRelIntType, InsActionL, Pom, IdASistema, IdSemaRelL[0], con, alter, conn);                   
               Trigeri.UpdateOsnovniRIL(out, outWarning, PKljucL, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionL, IdAtributaL, Pom, IdASistema, IdSemaRelL[0], con, alter, conn);
               Trigeri.BrisanjeOsnovniDFRIR(out, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, DelActionR, IdAtributaL,PKljucL, Pom, IdASistema, IdSemaRelL[0], con, alter, conn);//Osnovni ref integritet bez default kad je no action - to je deklartivno vec uradjeno
               Trigeri.UpdateOsnovniDFRIR(out, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionR, IdAtributaL,PKljucL, Pom, IdASistema, IdSemaRelL[0], con, alter, conn);
               
     } else
     if ((RSCType == 0 || RSCType == 2) && RSCRelIntType == 0 && NoAction != true) //Osnovni ref integritet default
     {               
               Trigeri.UpisOsnovniRIL(out, outWarning, PKljucL, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaL, IdAtributaR, IdProjekta, Rek, RSCRelIntType, InsActionL, Pom, IdASistema, IdSemaRelL[0], con, alter,conn);                
               Trigeri.UpdateOsnovniRIL(out, outWarning, PKljucL, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionL, IdAtributaL, Pom, IdASistema, IdSemaRelL[0], con, alter, conn);
               Trigeri.BrisanjeOsnovniDFRIR(out, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, DelActionR, IdAtributaL,PKljucL, Pom, IdASistema, IdSemaRelL[0], con, alter, conn);//Osnovni ref integritet bez default kad je no action - to je deklartivno vec uradjeno
               Trigeri.UpdateOsnovniDFRIR(out, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionR, IdAtributaL,PKljucL, Pom, IdASistema, IdSemaRelL[0], con, alter, conn);
                          
      }
      int Akcija;
      if (RSCType == 4)
      {
            if (View == false)
            {
                Trigeri.ProceduraZaUpisIRI(IdProjekta, Pom, RSCName, out, SemaRelNameL[0], SemaRelNameR[0], Rek, IdASistema, con, IdSemaRelL[0],IdSemaRelR[0],IdAtributaR,IdAtributaL, Pom, alter, conn);                    
            }else
            {                
                Trigeri.InvezniRIPogled(out, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaL, IdAtributaR, IdProjekta, Pom, con, alter, conn, IdASistema, Rek);
            }            
            Akcija = 1; //delete                   
            Trigeri.BrisanjeUpdateInverzniRI(con, out, SemaRelNameR[0], SemaRelNameL[0], RSCName, IdAtributaR, IdAtributaL,IdProjekta, Rek, DelActionR, Akcija, Pom, alter, conn);
            Akcija = 2;   //update
            Trigeri.BrisanjeUpdateInverzniRI(con, out, SemaRelNameR[0], SemaRelNameL[0], RSCName, IdAtributaR, IdAtributaL,IdProjekta, Rek, DelActionR, Akcija,Pom, alter, conn);               
            Trigeri.UpisOsnovniIRIRefIL(con, out, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaL, IdAtributaR,IdProjekta, Rek, Pom, alter, conn);                 
      }
  }
  
  protected void ConstraintForeignKey(PrintWriter out, PrintWriter outInd, String SemaRelNameL, String RSCName, String[] AtributNameL,String SemaRelNameR, String[] AtributNameR,int ModActionR, int DelActionR, int m, int n, Connection conn)
  {
    String str = "";
    String alt = "";
    Statement stmt;
    ResultSet rset;
    String query;
    if (alter == true)
    {
              query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[PK_"+SemaRelNameL+"]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1";
              try 
              {     
                stmt = conn.createStatement();       
                rset = stmt.executeQuery(query);
                if (rset.next ())                
                {
                  str = "ALTER TABLE [dbo].["+SemaRelNameL.replaceAll(" ","_")+"]";
                    out.println(str);
                    str = "DROP CONSTRAINT ["+RSCName.replaceAll(" ","_")+"]";
                    out.println(str);
                    out.println("GO");
                    out.println();
                }
               rset.close();
              stmt.close();          
              }   
              catch (Exception ex) 
              {
                ex.printStackTrace();
              }                       
    }
    alt = "ALTER TABLE [dbo].["+SemaRelNameL.replaceAll(" ","_")+"] ADD";
                  out.println(alt);                                 
                  str = "   CONSTRAINT ["+RSCName.replaceAll(" ","_")+"] FOREIGN KEY";
                  out.println(str);
                  str="   (";
                  out.println(str);
                  str = "";
                  for (int u=0;u<m;u++)
                  {
                    str = str + "[" + AtributNameL[u].replaceAll(" ","_") + "]";
                    if (u<m-1)
                    str = str + ",";
                  }
                  out.println("       "+str);  
                  str = "   ) REFERENCES [dbo].["+SemaRelNameR.replaceAll(" ","_")+ "]";                  
                  out.println(str);
                  str="   (";
                  out.println(str);
                  str = "";
                  for (int u=0;u<n;u++)
                  {
                    str = str + "[" + AtributNameR[u].replaceAll(" ","_") + "]";
                    if (u<n-1)
                    str = str + ",";
                  }
                  out.println("       "+str);
                  out.println("   )");
                  if (ModActionR == -4)
                  {
                    str = "[ON UPDATE {CASCADE}]";
                    out.println(str);
                  }
                  if (DelActionR == -4)
                  {
                    str = "[ON DELETE {CASCADE}]";
                    out.println(str);
                  }                                    
                  out.println("GO");
                  out.println();
                  
                  //INDEKSI
                  if (OneFileOnly)
                  {
                  if (IndeksiF)
                  { 
                    str = "CREATE INDEX FK_"+SemaRelNameL.replaceAll(" ","_");
                    out.println(str);                                       
                    str = "   ON "+SemaRelNameL.replaceAll(" ","_");
                    out.println(str);
                    str="   (";
                    out.println(str);                  
                    str = "";
                    for (int u=0;u<m;u++)
                    {
                      str = str + "[" + AtributNameL[u].replaceAll(" ","_") + "]";
                      if (u<m-1)
                      str = str + ",";
                    }
                    out.println("       "+str);  
                    str = "   )";                  
                    out.println(str);
                  }//INDEKSIF
                  }else
                  {
                    if (alter)
                  {
                          query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_"+SemaRelNameL+"]')";
                          try 
                          {     
                            stmt = conn.createStatement();       
                            rset = stmt.executeQuery(query);
                            if (rset.next ())
                            {
                              str = "ALTER INDEX FK_"+SemaRelNameL.replaceAll(" ","_");
                            }else
                            {
                              str = "CREATE INDEX FK_"+SemaRelNameL.replaceAll(" ","_");
                            }
                            rset.close();
                            stmt.close();          
                          }   
                          catch (Exception ex) 
                          {
                             ex.printStackTrace();
                          }       
                  }else
                  {
                    str = "CREATE INDEX FK_"+SemaRelNameL.replaceAll(" ","_");
                  }
                    outInd.println(str);                                       
                    str = "   ON "+SemaRelNameL.replaceAll(" ","_");
                    outInd.println(str);
                    str="   (";
                    outInd.println(str);                  
                    str = "";
                    for (int u=0;u<m;u++)
                    {
                      str = str + "[" + AtributNameL[u].replaceAll(" ","_") + "]";
                      if (u<m-1)
                      str = str + ",";
                    }
                    outInd.println("       "+str);  
                    str = "   )";                  
                    outInd.println(str);
                  }
  }
  
  protected void JosTrigera(PrintWriter out, Connection con, Connection conn)
  {
    
    String[] ModifableAtr=null;            
            for (int a=0;a<IdSema.length;a++)
            {                  
                  ModifableAtr=Pom.ModifiableObelezja(IdProjekta, IdSema[a], IdASistema, con);                  
                  if (ModifableAtr != null && ModifableAtr.length != 0)
                      Trigeri.UpdatePKey(out, ImenaSemaRel[a], ModifableAtr, alter, conn);
            }
  }
  
  protected void DatotekaDDl(PrintWriter out, String Verzija)
  {
    
    String str = "";
    str = Verzija;
    out.println(str);
    DateFormat df = DateFormat.getDateTimeInstance();
    String s = df.format(d);    
    str = "/*Created on:  " + s +" */";
    out.println(str);
    out.println();
    str = FileIme + "_Tab.sql";
    out.println(str);
    if(Ogranicenja)
    {
      str = FileIme + "_Con.sql";
      out.println(str);
    }
    if(IndeksiP || IndeksiU || IndeksiF)
    {
      str = FileIme + "_Ind.sql";
      out.println(str);
    }
    if (Triger)
    {
      str = FileIme + "_Trg.sql";
      out.println(str);
    }
  }

  //void FormirajSkript(String ImeBaze, String Putanja, String PutanjaLog)
  protected void FormirajSkript()
  {
    
    ResultSet rs;    
    JDBCQuery query=new JDBCQuery(con);    
    int BrRelacija=PodaciZaTabele(con);
    PrintWriter Datoteka;        
    PrintWriter out=null;
    PrintWriter outSql=null;
    PrintWriter outTable=null; 
    PrintWriter outInd=null;
    PrintWriter outConstr=null;
    PrintWriter outTrigeri=null;
    String Verzija;
    Verzija = "/* DBMS name:      Microsoft SQL Server 2000 */";
    //konekcija na bazu MSSQL
    Connection conn=null;
    String url = "";
    if (alter == true)
    {    
    url = "jdbc:microsoft:sqlserver://localhost:1433;databasename=" + ImeBaze +";";
    try
    { 
      DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
      conn = DriverManager.getConnection(url,SQLusername,SQLpassword);
    }catch (SQLException ex)
    {
      JOptionPane.showMessageDialog(null, "ClassNotFoundException:  " + ex.getMessage() , "Connection error", JOptionPane.ERROR_MESSAGE);
      
    }    
    //kraj konekcije
    }
    try {
        if (OneFileOnly == true)
           {
              File outputFile = new File(PrefixNaziva + ".sql");
              out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
           }else
           {
              File outputFileSql = new File(PrefixNaziva+"_DDL.sql");
              outSql = new PrintWriter(new BufferedWriter(new FileWriter(outputFileSql)));
              File outputFileTable = new File(PrefixNaziva+"_Tab.sql");
              outTable = new PrintWriter(new BufferedWriter(new FileWriter(outputFileTable)));
              if (IndeksiP==true || IndeksiF==true || IndeksiU==true)
              {
                File outputFileInd = new File(PrefixNaziva+"_Ind.sql");              
                outInd = new PrintWriter(new BufferedWriter(new FileWriter(outputFileInd)));
              }              
              if (Ogranicenja==true)
              {
                File outputFileConstr = new File(PrefixNaziva+"_Con.sql");
                outConstr = new PrintWriter(new BufferedWriter(new FileWriter(outputFileConstr)));
              }
              if (Triger==true)
              {
                File outputFileTrigeri = new File(PrefixNaziva+"_Trg.sql");
                outTrigeri = new PrintWriter(new BufferedWriter(new FileWriter(outputFileTrigeri)));
              
              }
           }        
    }
    catch (IOException ex) {
            System.err.println("Cannot open file.");
            System.err.println(ex);
    }         
                         
    if (OneFileOnly)
    {
        Datoteka = out;
    }else
    {
        Datoteka = outTable;
        DatotekaDDl(outSql,Verzija);
    }
    DatotkaTabele(ImeBaze, Datoteka, BrRelacija, conn);
    if (IndeksiP || IndeksiF || IndeksiU) 
    {
                  if (OneFileOnly == true)
                    {
                        Datoteka = out;
                    }else
                    {
                        Datoteka = outInd;
                    }    
                    DatotkaIndeksi(Datoteka, BrRelacija, conn);
    }
    if (Ogranicenja)
    {
        if (OneFileOnly)
        {
             DatotekaConstraints(out, BrRelacija,conn);
        }else
        {
             DatotekaConstraints(outConstr, BrRelacija,conn);
        }                 
    }                     
    try {        
       if ((Ogranicenja || Triger) && Constr != null)
       {
            String upit;          
            for (int i=0;i<Constr.length;i++)
            {
              int RHSId;
              String RSCName;
              int LHSRSId;
              int RHSRSId;
              int RSCType;
              int LHSId;
              int RSCRelIntType;
              int Initially;
              int Deferrable;
              int RSL;                                         
              int InsActionL=-1;                      
              int ModActionL=-1;
              int RSR;                                         
              int ModActionR=-1; 
              int DelActionR=-1;
              upit = "select RSC_name,LHS_RS_Set_id,RHS_RS_Set_id,RSC_type,LHS_id,RHS_id,RSC_rel_int_type,RSC_deferrable,RSC_initially from IISC_RS_CONSTRAINT where PR_id="+IdProjekta+" and RSC_id="+Constr[i]+" and AS_id="+IdASistema; 
              rs=query.select(upit);              
              if(rs.next())
              {
                 RHSId = rs.getInt("RHS_id");
                 RSCName = rs.getString("RSC_name");
                 LHSRSId = rs.getInt("LHS_RS_Set_id");
                 RHSRSId = rs.getInt("RHS_RS_Set_id");
                 RSCType = rs.getInt("RSC_type");
                 LHSId = rs.getInt("LHS_id");
                 RSCRelIntType = rs.getInt("RSC_rel_int_type");
                 Initially = rs.getInt("RSC_initially");
                 Deferrable = rs.getInt("RSC_deferrable");                                  
                 query.Close();
                 upit = "select RS_id from IISC_RSC_RS_SET where PR_id="+IdProjekta+" and RS_Set_id="+LHSRSId+"and AS_id="+IdASistema;                  
                 int k=Pom.brojanjeNtorki("IISC_RSC_RS_SET","PR_id="+IdProjekta+" and AS_id="+IdASistema+" and RS_Set_id="+LHSRSId,con);
                 int[] IdSemaRelL=query.selectArraySAint(upit,k,1);
                 query.Close();
                 String[] SemaRelNameL = new String[k];
                 for (int u=0;u<k;u++)
                 {
                  upit = "select RS_name from IISC_RELATION_SCHEME where PR_id="+IdProjekta+" and RS_id="+IdSemaRelL[u]+" and AS_id="+IdASistema;                  
                  rs=query.select(upit);                  
                  rs.next();
                  SemaRelNameL[u] = rs.getString("RS_name");
                  query.Close();
                 }
                 
                 upit = "select RS_id from IISC_RSC_RS_SET where PR_id="+IdProjekta+" and RS_Set_id="+RHSRSId+" and AS_id="+IdASistema;                  
                 int l=Pom.brojanjeNtorki("IISC_RSC_RS_SET","PR_id="+IdProjekta+" and AS_id="+IdASistema+" and RS_Set_id="+RHSRSId,con);
                 int[] IdSemaRelR=query.selectArraySAint(upit,l,1);
                 query.Close();
                 String[] SemaRelNameR = new String[l];
                 for (int u=0;u<l;u++)
                 {
                  upit = "select RS_name from IISC_RELATION_SCHEME where PR_id="+IdProjekta+" and RS_id="+IdSemaRelR[u]+" and AS_id="+IdASistema;                  
                  rs=query.select(upit);                  
                  rs.next();
                  SemaRelNameR[u] = rs.getString("RS_name");
                  query.Close();
                 }               
              
                 upit = "select Att_id from IISC_RSC_LHS_RHS where PR_id="+IdProjekta+" and RSCLR_id="+LHSId+" and AS_id="+IdASistema+" ORDER BY Att_rbr";                  
                 int m=Pom.brojanjeNtorki("IISC_RSC_LHS_RHS","PR_id="+IdProjekta+" and AS_id="+IdASistema+" and RSCLR_id="+LHSId,con);
                 int[] IdAtributaL=query.selectArraySAint(upit,m,1);
                 query.Close();
                 String[] AtributNameL = new String[m];
                 for (int u=0;u<m;u++)
                 {
                    upit = "select Att_mnem from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaL[u];
                    rs=query.select(upit);
                    rs.next();
                    AtributNameL[u] = rs.getString("Att_mnem");
                    query.Close();
                 }
                                                   
                 upit = "select Att_id from IISC_RSC_LHS_RHS where PR_id="+IdProjekta+" and RSCLR_id="+RHSId+" and AS_id="+IdASistema+" ORDER BY Att_rbr";                  
                 int n=Pom.brojanjeNtorki("IISC_RSC_LHS_RHS","PR_id="+IdProjekta+" and AS_id="+IdASistema+" and RSCLR_id="+RHSId,con);
                 int[] IdAtributaR=query.selectArraySAint(upit,n,1);
                 query.Close();
                 String[] AtributNameR = new String[n];
                 for (int u=0;u<n;u++)
                 {
                    upit = "select Att_mnem from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaR[u];
                    rs=query.select(upit);
                    rs.next();
                    AtributNameR[u] = rs.getString("Att_mnem");
                    query.Close();
                 }
                               
                 int  p=Pom.brojanjeNtorki("IISC_RSC_ACTION","PR_id="+IdProjekta+" and RSC_id="+Constr[i]+" and AS_id="+IdASistema,con);
                 boolean NoAction = true;
                 if (p>0)
                 {                  
                    NoAction = false;
                    upit = upit = "select RS_id, RSC_ins_action, RSC_mod_action, RSC_del_action from IISC_RSC_ACTION where PR_id="+IdProjekta+" and RSC_id="+Constr[i]+" and AS_id="+IdASistema+" and RSC_lhs_rhs=0";
                    rs=query.select(upit);
                    rs.next();
                    RSL=rs.getInt("RS_id");                                         
                    InsActionL=rs.getInt("RSC_ins_action");                     
                    ModActionL=rs.getInt("RSC_mod_action");                                       
                    query.Close();        
                    
                    upit = upit = "select RS_id, RSC_ins_action, RSC_mod_action, RSC_del_action from IISC_RSC_ACTION where PR_id="+IdProjekta+" and RSC_id="+Constr[i]+" and AS_id="+IdASistema+" and RSC_lhs_rhs=1";
                    rs=query.select(upit);
                    rs.next();
                    RSR=rs.getInt("RS_id");                                         
                    ModActionR=rs.getInt("RSC_mod_action"); 
                    DelActionR=rs.getInt("RSC_del_action");
                    query.Close();  
                    
                    if (InsActionL == -1 && ModActionL == -1 &&  ModActionR == -1 && DelActionR == -1)
                    {
                      NoAction = true;
                    }                    
                 }  
                 
                 if ((RSCType == 0 || RSCType == 2) && RSCRelIntType == 0 && NoAction == true) // || ModActionR == -4 || ModActionR == -1 || DelActionR == -4 || DelActionR == -1 || ModActionL == -1 || InsActionL == -1)) 
                 {                    
                    if (Ogranicenja==true)
                    {
                      if (OneFileOnly == true)
                      {
                          Datoteka = out;
                      }else
                      {
                          Datoteka = outConstr;
                      }         
                        ConstraintForeignKey(Datoteka,outInd,SemaRelNameL[0],RSCName,AtributNameL,SemaRelNameR[0],AtributNameR,ModActionR,DelActionR,m,n,conn);
                    }                 
                 } 
                 
                 // trigeri
                 if (Triger==true)
                 {
                    if (OneFileOnly == true)
                    {
                        Datoteka = out;
                    }else
                    {
                        Datoteka = outTrigeri;
                    }         
                    DatotekaTrigeri(Datoteka, con, IdSemaRelL, RSCType, RSCRelIntType, SemaRelNameL, SemaRelNameR, RSCName, IdAtributaL, IdAtributaR, InsActionL, DelActionR, ModActionR, ModActionL, IdSemaRelR, NoAction, conn);
                }                 
                
              } else// rs.next                  
              {
                  query.Close();
              }
              
            } //for i constraint            
    } //ako zeli da geenrise ogranicenja
          
          if (Triger==true)
          {
            if (OneFileOnly)
            {
              JosTrigera(out, con, conn);
            }else
            {
              JosTrigera(outTrigeri, con, conn);
            }            
          } 
        
        /*nobrenovic: start*/
          try 
          { 
             if(conn != null && !conn.isClosed())
                conn.close();
          }   
          catch (Exception ex) 
          {
            ex.printStackTrace();
          } 
          /*nobrenovic: stop*/

            if (OneFileOnly)
            {
              out.close();
            }else
            {
              outSql.close();
              outTable.close(); 
              if (IndeksiP || IndeksiU || IndeksiF)
              {
                outInd.close();
              }  
              if (Ogranicenja) 
              {
                outConstr.close();
              }  
              if (Triger)
              {
                outTrigeri.close();
              }  
            }       
        }
         
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }
      
  }

    /*nobrenovic: start*/
    /**
     * Genetares trigger code that implements the given check constraint over 
     * the given table.
     * 
     * @param table Trigger's table.
     * @param chkCon Check constraint.
     * @param out Output stream, opened for writing
     */
    protected void GenerateChkConTrigger(Table table, CheckConstraint chkCon, PrintWriter out) {
    
        final String trgName = "TRG_"+ chkCon.getName() + "_" + table.getImeTabele() +"_INS_UPD";
        
        boolean postoji = false;
        if (alter)
        {
            String que = "select * from dbo.sysobjects where id = object_id(N'[dbo].["+ trgName +"]') and OBJECTPROPERTY(id, N'IsTrigger') = 1";
            try 
            {     
                 Statement stmt = con.createStatement();       
                 ResultSet rset = stmt.executeQuery(que);
                 if (rset.next ())
                 {
                      postoji = true;                              
                 }
                 rset.close();
                 stmt.close();          
            }   
            catch (Exception ex) 
            {
                   ex.printStackTrace();
            }
        }
        if (alter && postoji)
        {
          out.println("ALTER TRIGGER "+ trgName);
        }
        else
        {
          out.println("CREATE TRIGGER "+ trgName);
        }
            
        out.println("   ON "+ table.getImeTabele());    
        out.println("   FOR INSERT, UPDATE");
        out.println("AS");
        
        out.println("   DECLARE @cnt INT");
        out.print("   SELECT @cnt=count(*) FROM ");    
        for (int i=0;i<chkCon.getRefRelSch().size();i++)
        {
            if(!chkCon.getRefRelSch().get(i).equalsIgnoreCase(table.getImeTabele()))
                out.print(chkCon.getRefRelSch().get(i));
            else
                out.print("Inserted");    
          if(i<chkCon.getRefRelSch().size()-1)
              out.print(",");
        } 
        
        out.println();
        ArrayList<Integer> normJoin = chkCon.getRefRelSchIds();
        normJoin.remove((Integer)table.getTableId());
        out.print("   WHERE ");
        String where = CheckConGenHelper.GenerateJoinWhereClause(con,IdProjekta, IdASistema,normJoin);
        if(!where.equals(""))
            where += "\n        AND ";
        where += CheckConGenHelper.GenerateJoinWithInserted(con,IdProjekta, IdASistema,table.getTableId(), "Inserted", normJoin);
        out.println(where);
        
        String conStr = "";
        if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
             conStr = chkCon.getDnf().toString(true);
        else 
            conStr = chkCon.getCnf().toString(true);
            
        conStr = conStr.replaceAll(table.getImeTabele()+".","Inserted.");    
        out.println("       AND NOT("+ conStr + ")");
        
        out.println("   IF (@cnt>0)"); 
        out.println("   BEGIN");     
        out.println("       RAISERROR('Narusen check constraint " + chkCon.getName() + "',16,1)");
        out.println("       ROLLBACK TRAN");     
        out.println("   END");
        out.println("GO");
        out.println();
        
    }
    
    /*nobrenovic: stop*/
}
