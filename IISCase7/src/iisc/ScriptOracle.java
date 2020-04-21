package iisc;

import iisc.lang.PlSqlTranslator;

import java.sql.*;
import java.text.DateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.io.*;

import java.util.ArrayList;
import java.util.Date;

import oracle.jdbc.OracleConnection;

public class ScriptOracle 
{
  private Connection con;
  private int IdASistema;
  private int IdProjekta;
  private int[] IdSema;
  private String[] ImenaSemaRel;
  private Table[] Tabele; 
  private int[] Constr;    
  private ClassFunction Pom = new ClassFunction();
  private Rekurzije Rek = new Rekurzije();  
  private boolean alter;
  //PrintWriter out;
  private PrintWriter outWarning;
  private String PrefixNaziva;
  private boolean OneFileOnly;
  private boolean Ogranicenja, IndeksiP,IndeksiF, IndeksiU; 
  private boolean Triger;
  private String FileIme;
  private String ImeBaze;
  private TriggerOracle Trigeri;
  private boolean View;
  private String PackageNameConst;
  private String HostO;
  private String passwordO;
  private String usernameO;
  private Date d = new Date();
 
  /*nobrenovic: start*/
  private CheckConstraint.ImplemetationType chkConImplType;
   /*nobrenovic: stop*/
  
  public ScriptOracle()
  {
  }

  public ScriptOracle(Connection Kon, int PrId, int AsId, int[] SemeRelId, String[] SemeRelName,boolean Promena, int[] Constraint, PrintWriter warningf, String PrefixNazivaDat, boolean OneFile, boolean IndP, boolean IndF, boolean IndU, CheckConstraint.ImplemetationType chkConImpl, boolean Ogr, boolean Trg, String FileName, String NameBase, boolean Pogled, String usernameDatabase, String passwordDatabase , String HostDatabase)
  {
    
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
    ImeBaze = NameBase;
    View = Pogled;
    HostO = HostDatabase;
    passwordO = passwordDatabase;
    usernameO = usernameDatabase;
    this.chkConImplType = chkConImpl;
    
  }  
  
  int PodaciZaTabele(Connection con,Rekurzije Rek)
  {
    JDBCQuery query=new JDBCQuery(con);        
    ResultSet rs;
    
    int l=0;
    //int j=Pom.brojanjeNtorki("IISC_RELATION_SCHEME","PR_id="+IdProjekta+" and AS_id="+IdASistema,con);    
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
          CeoKljuc=CeoKljuc+MnemAtr.replaceAll(" ","_");
          Kljuc[s]=MnemAtr.replaceAll(" ","_");
          if (s!=IdAtr.length-1)
          {
            CeoKljuc=CeoKljuc+",";
          }
          Tabele[i].setAtributKljuca(s,Kljuc[s]);
        }
        
        Tabele[i].setPKljuc(CeoKljuc);   
      } else {
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
          CeoKljuc=CeoKljuc+MnemAtr.replaceAll(" ","_");
          if (s!=IdAtr.length-1)
          {
            CeoKljuc=CeoKljuc+",";
          }
        }
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
        CeoKljuc="";
        for (int s=0;s<AtrUniqua.length;s++)
        {
          String upit6 = "select Att_mnem from IISC_ATTRIBUTE where IISC_ATTRIBUTE.PR_id="+IdProjekta+" and IISC_ATTRIBUTE.Att_id="+AtrUniqua[s];     
          rs=query.select(upit6);
          rs.next();
          String MnemAtr=rs.getString(1);
          CeoKljuc=CeoKljuc+MnemAtr.replaceAll(" ","_");
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
          
          String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta, 2); 
          int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);        
          
          if (Duzina!=0)
          {
              TipPodatka=TipPodatka + "(" + Duzina + ")";               
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
  
  void DatotkaTabele(PrintWriter out, int BrRelacija, OracleConnection conn)
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
    JDBCQuery query2=new JDBCQuery(con);
    
    String upit = "select * from IISC_SEQUENCE where PR_id="+IdProjekta+ " and AS_id="+IdASistema;      
    if (alter == false) 
    {
      for (int i=0;i<BrRelacija;i++)
      {             
              str = "DROP TABLE "+Tabele[i].getImeTabele()+" "+"CASCADE"+" CONSTRAINTS";
              out.println(str);
              out.println("/");
              out.println();            
      } 
      
      //String upit = "select * from IISC_SEQUENCE where PR_id="+IdProjekta+ " and AS_id="+IdASistema;      
      try{
        rs2=query2.select(upit);
        while(rs2.next())
        {          
          str = "DROP SEQUENCE "+ rs2.getString("Seq_name");
          out.println(str);
          out.println("/");
          out.println();   
        }
        query2.Close();
      }
        catch(Exception ex){     
        ex.printStackTrace();
      }                
    }
    
    try{
        rs2=query2.select(upit);
        while(rs2.next())
        {          
           str = "CREATE SEQUENCE "+ rs2.getString("Seq_name");
                  out.println(str);
                  str = "increment by " + rs2.getInt("Seq_increment");
                  out.println(str);
                  str = "start with " + rs2.getInt("Seq_start");
                  out.println(str);
                  
                  if (rs2.getInt("Seq_maxvalue")!=0)
                  {
                    str = "maxvalue " + rs2.getInt("Seq_maxvalue");
                    out.println(str);
                  }else
                  {
                    str = "nomaxvalue";
                    out.println(str);
                  }
                  if(rs2.getInt("Seq_cycle")==0)
                  {
                    str = "nocycle";
                    out.println(str);
                  }else
                  {
                    str = "cycle";
                    out.println(str);
                  }
                  if (rs2.getInt("Seq_cache")!=0)
                  {
                    str = "cache " + rs2.getInt("Seq_cache");                    
                    out.println(str);
                  }else
                  {
                    str = "nocache";
                    out.println(str);
                  }
                  if (rs2.getInt("Seq_order")!=0)
                  {
                    str = "order";
                    out.println(str);
                  }else
                  {
                    str = "noorder";
                    out.println(str);
                  }                  
                  out.println("/");   
                  out.println();   
        }
      query2.Close();
      }
        catch(Exception ex){     
        ex.printStackTrace();
      }      
      // za slozene tipove podataka 
    String[] DomMnem=null;    
    int[] DomDataType=null;    
    int[] DomenParent=null;
    int[] DomenId=null;
    int[] DomenType=null;
    int t1=0;  
    int DomId=0;
    String TipPodatka="";
    JDBCQuery querydom=new JDBCQuery(con);
    t1=Pom.brojanjeNtorki("IISC_DOMAIN","PR_id="+IdProjekta,con);    
    String upitdom = "select Dom_id,Dom_type,Dom_mnem, Dom_parent,Dom_parent from IISC_DOMAIN where PR_id="+IdProjekta;
    DomenId=querydom.selectArraySAint(upitdom,t1,1);
    DomenType=querydom.selectArraySAint(upitdom,t1,2);
    DomMnem=querydom.selectArraySA(upitdom,t1,3);
    DomenParent=querydom.selectArraySAint(upitdom,t1,4);
    DomDataType=querydom.selectArraySAint(upit,t1,5);
    querydom.Close();    
    for (int l=0; l<t1;l++)
    {
      if (DomenType[l]==2)
      {
               ResultSet rs; 
               JDBCQuery queryd2=new JDBCQuery(con);
               String ListaAtributa = "";
               int b;
               int[] AtrId=null;
               String AtributName=null;
               b=Pom.brojanjeNtorki("IISC_DOM_ATT","PR_id="+IdProjekta + " and Dom_id="+DomenId[l],con);
               String upitDom = "select Att_id from IISC_DOM_ATT where IISC_DOM_ATT.Dom_id="+DomenId[l] + " and IISC_DOM_ATT.PR_id=" + IdProjekta +" ORDER BY Att_rbr";
               AtrId=queryd2.selectArraySAint(upitDom,b,1);
               
               for (int a=0;a<AtrId.length;a++)
               {
                 String upitAtr = "select Att_mnem,Dom_id from IISC_ATTRIBUTE where IISC_ATTRIBUTE.Att_id="+ AtrId[a]+ " and IISC_ATTRIBUTE.PR_id=" + IdProjekta;
                 try{
                  rs=queryd2.select(upitAtr);                
                  rs.next();              
                  AtributName= rs.getString(1); 
                  DomId = rs.getInt(2);
                  queryd2.Close();  
                 }
                 catch (SQLException ev) {    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 }   
                 
                 JDBCQuery queryrek=new JDBCQuery(con); 
                  TipPodatka=Rek.TraziTip(DomId, queryrek, IdProjekta, 1); 
                  int Duzina = Rek.TraziDuzinu(DomId, queryrek, IdProjekta);              
                  if (Duzina!=0)
                  {
                    TipPodatka=TipPodatka + "(" + Duzina + ")";               
                  }               
             
                 ListaAtributa = ListaAtributa + AtributName + " "+ TipPodatka;//Rek.AtributiDomena(AtrId[a],queryd2, Rek, IdProjekta);
                 if (a<AtrId.length-1)
                 {
                   ListaAtributa = ListaAtributa+", ";
                 }
               }
               str = "CREATE [OR REPLACE] TYPE O_" + DomMnem[l];
               out.println(str);
               out.println("IS OBJECT");                            
               out.println(ListaAtributa+";");
               out.println();
        
      }else if (DomenType[l]==4)
      {
               ResultSet rs; 
               JDBCQuery queryd3=new JDBCQuery(con);
                                             
              if (DomenParent[l]!=0)
              {
                TipPodatka=Rek.TraziTip(DomenId[l], queryd3, IdProjekta, 0);
              }else
              {
                String upit4 = "select PT_mnemonic from IISC_PRIMITIVE_TYPE where IISC_PRIMITIVE_TYPE.PT_id="+DomDataType[l];
                try{
                rs=queryd3.select(upit4);                
                  rs.next();              
                  TipPodatka=rs.getString(1);        
                  queryd3.Close();  
                }
                catch (SQLException ev) {    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
              }  
                         
              
               str = "CREATE [OR REPLACE] TYPE T_" + DomMnem[l];
               out.println(str);
               out.print("IS ");
               str = "TABLE OF " + TipPodatka;
               out.println(str);
               out.println();
      }
    }
    
    //za slozene tipove podataka           
    
    for (int i=0;i<BrRelacija;i++)
    {               
           if (alter)
           {
              query = "select table_name from user_tables where table_name = '"+Tabele[i].getImeTabele().toUpperCase()+"'";
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
                str = "CREATE TABLE "+ Tabele[i].getImeTabele()+" (";
                out.println(str);
                for (int j=0; j<Tabele[i].getBrAtributa();j++)
                {
                  str = Tabele[i].getImeAtributa(j)+" "+Tabele[i].getTip(j);
                  out.print(str);
                  if (Tabele[i].getDefault(j).length()!=0)
                  {                     
                     str = " DEFAULT "+Tabele[i].getDefault(j);
                     out.print(str);                      
                  }   
                  if (Tabele[i].getNullNotNull(j)!=0)                
                  {
                    str = " NOT NULL";
                    out.print(str);
                  }         
                  
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
                str = "\r\n"+")";              
                out.println(str);
                out.println("/");
                out.println(); 
              }else
              {                                    
                  query = "select column_name from user_tab_columns where table_name = '"+ Tabele[i].getImeTabele().toUpperCase() +"'";
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
                    SetAtributaCase.add(Tabele[i].getImeAtributa(j).toUpperCase());
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
                    str = "ALTER TABLE "+ImeBaze + Tabele[i].getImeTabele();
                    out.println(str);
                    str = "DROP (";
                    out.print(str);                     
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
                    str = element + "";
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
                  out.println(")");
                  out.println("/"); 
                  out.println();
                  AtributiDROP.clear();
                }                
                if (!AtributiALTER.isEmpty())
                {                    
                  j=0;
                  str = "ALTER TABLE "+ImeBaze + Tabele[i].getImeTabele();
                  out.println(str);
                  str = "MODIFY (";
                  out.print(str);                  
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
                    str = "COLUMN "+ element + " "+Tabele[i].getTip(t);
                    out.print(str);                    
                    if (j<(AtributiALTER.size()))
                    {
                      out.println(",");
                    }  
                    
                    for (int k = 0; k<Tabele[i].Kljuc.length;k++)
                    {
                      if (element.equals(Tabele[i].getAtributKljuca(k)))
                      {
                        str = "Attribute "+ element + " which you want change is primary key of relation schema " + Tabele[i].getImeTabele() + "!";
                        outWarning.println(str);
                      }                      
                    } 
                  }                  
                  out.println(")");
                  out.println("/"); 
                  out.println();
                  AtributiALTER.clear();             
                } 
                if (!AtributiADD.isEmpty())
                {
                  j=0;
                  str = "ALTER TABLE "+ImeBaze + Tabele[i].getImeTabele();
                  out.println(str);
                  str = "ADD (";
                  out.print(str);                  
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
                    str = element + " " + Tabele[i].getTip(t);
                    out.print(str);                       
                    if (j<(AtributiADD.size()))
                    {
                      out.println(",");
                    }
                  }
                  out.println(")");
                  out.println("/"); 
                  out.println();
                  AtributiADD.clear();
                }   
              } 
           }else
           {             
              str = "CREATE TABLE "+ Tabele[i].getImeTabele()+" (";
              out.println(str);
              for (int j=0; j<Tabele[i].getBrAtributa();j++)
              {
                str = Tabele[i].getImeAtributa(j)+" "+Tabele[i].getTip(j);
                out.print(str);
                if (Tabele[i].getDefault(j).length()!=0)
                {                     
                     str = " DEFAULT "+Tabele[i].getDefault(j);
                     out.print(str);                      
                }   
                if (Tabele[i].getNullNotNull(j)!=0)                
                {
                  str = " NOT NULL";
                  out.print(str);
                }     
                
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
              str = "\r\n"+")";              
              out.println(str);
              out.println("/");
              out.println();   
        }   
        
    } //end for i 
  }
  void DatotkaIndeksi(PrintWriter out, int BrRelacija, Connection conn)
  {
    String str;
    str="";
    Statement stmt;
    ResultSet rset;
    String query;      
    for (int i=0;i<BrRelacija;i++)
    {                
              if (IndeksiP)
              {
                if (IndeksiP)
                {
                  out.println();
                }
                if (Tabele[i].getPKljuc().length()!=0)
                {
                  if (alter)  
                  {
                        query = "select * from user_indexes where index_name = 'PK_"+ Tabele[i].getImeTabele().toUpperCase() +"' and table_name = '" + Tabele[i].getImeTabele().toUpperCase() + "'";
                        try 
                        {     
                          stmt = conn.createStatement();       
                          rset = stmt.executeQuery(query);
                          if (rset.next ())
                          {
                              str = "DROP INDEX "+ "PK_"+Tabele[i].getImeTabele().toUpperCase();
                              out.println(str);  
                              out.println("/");                      
                              out.println();
                              outWarning.println("You changed primary key index PK_" + Tabele[i].getImeTabele()+ ".");
                          }
                        } 
                        catch (Exception ex) 
                        {
                          ex.printStackTrace();
                        } 
                  }
                  str = "CREATE INDEX "+ "PK_"+Tabele[i].getImeTabele();
                  out.println(str);                                       
                  str = "   ON "+Tabele[i].getImeTabele();
                  out.println(str);
                  str="   (";
                  out.println(str); 
                  str = "       "+Tabele[i].getPKljuc();
                  out.println(str); 
                  str = "   )";
                  out.println(str);  
                  out.println("/");
                }
              }
              if (IndeksiU)
              {
                if (Tabele[i].Uniqueovi!=null && Tabele[i].Uniqueovi.length!=0)
                {                    
                  for (int x=0;x<Tabele[i].Uniqueovi.length;x++)
                  {
                    if (Tabele[i].getUnique(x).length()!=0)
                    {                        
                      if (alter)  
                      {
                        query = "select * from user_indexes where index_name = 'UK_"+ Tabele[i].getImeTabele().toUpperCase()+"_" + x +"' and table_name = '" + Tabele[i].getImeTabele().toUpperCase() + "'";
                        try 
                        {     
                          stmt = conn.createStatement();       
                          rset = stmt.executeQuery(query);
                          if (rset.next ())
                          {
                              str = "DROP INDEX "+ "UK_"+Tabele[i].getImeTabele().toUpperCase() + "_" + x;
                              out.println(str);  
                              out.println("/");                      
                              out.println();
                          }
                        } 
                        catch (Exception ex) 
                        {
                          ex.printStackTrace();
                        } 
                      }
                      str = "CREATE INDEX "+ "UK_"+Tabele[i].getImeTabele() + "_" + x;
                      out.println(str);                                       
                      str = "   ON "+Tabele[i].getImeTabele();
                      out.println(str);
                      str="   (";
                      out.println(str);
                      str = "       "+Tabele[i].getUnique(x);
                      out.println(str);
                      str= "    )";
                      out.println(str);   
                      out.println("/");
                    }                  
                  }                  
                } 
              } //indeksiU 
              out.println(); 
    } //end for i 
  }
  
  void DatotekaConstraints(PrintWriter out, int BrRelacija, Connection conn)
  {
    String str = "";
    String alt = "";
    Statement stmt;
    ResultSet rset;
    String query;
    PlSqlTranslator trans = new PlSqlTranslator(this.con, IdProjekta);
    
    for (int i=0;i<BrRelacija;i++)
    {             
        //out.println("kod za ogranicenja putem trigera");
        out.println(trans.GenerateTobEvents(Tabele[i].getImeTabele()));
        
                alt = "";        
                str = "";
             if (alter)
             {
                 if (Tabele[i].getPKljuc().length()!=0)
                 { 
                 
                 
                  query = "select * from user_constraints where constraint_name = 'PK_"+Tabele[i].getImeTabele().toUpperCase()+"' and table_name = '" + Tabele[i].getImeTabele().toUpperCase() + "'";
                  try 
                    {     
                      stmt = conn.createStatement();       
                      rset = stmt.executeQuery(query);
                      if (rset.next ())
                      {
                          str = "ALTER TABLE "+ Tabele[i].getImeTabele();
                          out.println(str);
                          str = "   DROP PRIMARY KEY";
                          out.println(str);
                          out.println("/");                      
                          out.println();
                      }
                      str = "ALTER TABLE "+ Tabele[i].getImeTabele();
                      out.println(str);
                      str = "   ADD CONSTRAINT PK_"+Tabele[i].getImeTabele() + " PRIMARY KEY";
                      out.println(str);
                      str="   (";
                      out.println(str); 
                      str = "       "+Tabele[i].getPKljuc();
                      out.println(str); 
                      str = "   )";
                      out.println(str);  
                      out.println("/");                      
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
                        query = "select * from user_constraints where constraint_name = 'AK_"+Tabele[i].getImeTabele().toUpperCase()+"_"+x+"' and table_name = '" + Tabele[i].getImeTabele().toUpperCase() + "'";
                        try 
                        {     
                          stmt = conn.createStatement();       
                          rset = stmt.executeQuery(query);
                          if (rset.next ())
                          {
                            str = "ALTER TABLE "+ Tabele[i].getImeTabele();
                            out.println(str);
                            str = "   DROP UNIQUE ";
                            out.println(str);
                            str="   (";
                            out.println(str);
                            str = "       "+Tabele[i].getUnique(x);
                            out.println(str);
                            str= "    )";
                            out.println(str); 
                            out.println("/");                      
                            out.println();
                          }      
                          rset.close();
                          stmt.close();
                          str = "ALTER TABLE "+ Tabele[i].getImeTabele();
                          out.println(str);
                          str="     ADD CONSTRAINT AK_"+Tabele[i].getImeTabele()+"_"+x+" UNIQUE";
                          out.println(str);
                          str="   (";
                          out.println(str);
                          str = "       "+Tabele[i].getUnique(x);
                          out.println(str);
                          str= "    )";
                          out.println(str); 
                          out.println("/");                      
                          out.println();
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
                             query = "select * from user_constraints where constraint_name = '"+ chkCon.getName() + "' and table_name = '" + Tabele[i].getImeTabele().toUpperCase() + "'";
                             try 
                             {     
                               stmt = conn.createStatement();       
                               rset = stmt.executeQuery(query);
                               
                               out.println();   
                               if (rset.next ())
                               {
                                 str = "ALTER TABLE " + Tabele[i].getImeTabele();
                                 out.println(str);
                                 str = "DROP CONSTRAINT " + chkCon.getName();
                                 out.println(str);
                                 out.println();
                               }  
                               //generate new constraint
                               alt = "ALTER TABLE " + Tabele[i].getImeTabele();
                               out.println(alt);          
                               String conStr = "";
                               if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
                                    conStr = chkCon.getDnf().toString(false);
                               else 
                                   conStr = chkCon.getCnf().toString(false);
                               str = "    CONSTRAINT "+ chkCon.getName() +" CHECK ("+ conStr +")";
                               out.println(str);             
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
                     
                 out.println("/");                      
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
                 out.println();
                 /*nobrenovic: stop*/       
             }
             else
             {             
                if (Tabele[i].getPKljuc().length()!=0)
                {
                  if (alt == "")
                     {
                        alt = "ALTER TABLE "+ Tabele[i].getImeTabele();
                        out.println(alt);
                     }                  
                  str = "   ADD CONSTRAINT PK_"+Tabele[i].getImeTabele() + " PRIMARY KEY";
                  out.println(str);
                  str="   (";
                  out.println(str); 
                  str = "       "+Tabele[i].getPKljuc();
                  out.println(str); 
                  str = "   )";
                  out.println(str);                
                }
             if (Tabele[i].Uniqueovi!=null && Tabele[i].Uniqueovi.length!=0)
              {                
                for (int x=0;x<Tabele[i].Uniqueovi.length;x++)
                {
                  if (Tabele[i].getUnique(x).length()!=0)
                  {
                    if (alt == "")
                    {
                        alt = "ALTER TABLE "+ Tabele[i].getImeTabele();
                        out.println(alt);
                    }                    
                    str="     ADD CONSTRAINT AK_"+Tabele[i].getImeTabele()+"_"+x+" UNIQUE";
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
             
             /*nobrenovic: start*/
             for (int x=0;x<Tabele[i].getCheckCons().size();x++)
             {
               CheckConstraint chkCon = Tabele[i].getCheckCons().get(x);
               if (chkCon.getRefRelSch().size() == 1)
               {
                   //generate new constraint
                   if (alt == "")
                   {
                     alt = "ALTER TABLE "+ Tabele[i].getImeTabele();
                     out.println(alt);
                   }             
                   String conStr = "";
                   if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
                        conStr = chkCon.getDnf().toString(false);
                   else 
                       conStr = chkCon.getCnf().toString(false);
                   str = "    ADD CONSTRAINT "+ chkCon.getName() +" CHECK ("+ conStr +")";
                   out.println(str);                 
                  
               }
             }
             out.println("/");
             //str = "\r\n";
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
  void ConstraintForeignKey(PrintWriter out,PrintWriter outInd,String SemaRelNameL, String RSCName, String[] AtributNameL,String SemaRelNameR, String[] AtributNameR, int DelActionR, int m, int n, Connection conn, int Deferrable, int Initially)
  {
    String str = "";
    String alt = "";
    Statement stmt;
    ResultSet rset;
    String query;
    
              if (alter)
              {                     
                        query = "select * from user_constraints where constraint_name = '"+ RSCName.toUpperCase() +"' and table_name = '" + SemaRelNameL.toUpperCase() + "'";
                        try 
                        {     
                          stmt = conn.createStatement();       
                          rset = stmt.executeQuery(query);
                          if (rset.next ())
                          {
                            str = "ALTER TABLE "+ SemaRelNameL.replaceAll(" ","_");
                            out.println(str);
                            str = "   DROP CONSTRAINT '"+ RSCName.toUpperCase().replaceAll(" ","_") + "'";
                            out.println(str);
                            out.println("/");                      
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
                  alt = "ALTER TABLE "+ SemaRelNameL.replaceAll(" ","_")+" ADD";
                  out.println(alt);                                 
                  str = "   CONSTRAINT "+RSCName.replaceAll(" ","_")+" FOREIGN KEY";
                  out.println(str);
                  str="   (";
                  out.println(str);
                  str = "";
                  for (int u=0;u<m;u++)
                  {
                    str = str + AtributNameL[u].replaceAll(" ","_");
                    if (u<m-1)
                    str = str + ",";
                  }
                  out.println("       "+str);  
                  str = "   ) REFERENCES "+ SemaRelNameR.replaceAll(" ","_");                  
                  out.println(str);
                  str="   (";
                  out.println(str);
                  str = "";
                  for (int u=0;u<n;u++)
                  {
                    str = str + AtributNameR[u].replaceAll(" ","_");
                    if (u<n-1)
                    str = str + ",";
                  }
                  out.println("       "+str);
                  out.print("   )");
                  
                  if (DelActionR == -4)
                  {
                    str = " ON DELETE CASCADE";
                    out.print(str);
                  }                                   
                  if (DelActionR == -2) 
                  {
                    str = " ON DELETE SET NULL";
                    out.print(str);
                  } 
                  out.println();
                  if (Deferrable==1 && Initially==0)
                  {
                    out.println("INITIALLY IMMEDIATE DEFERRABLE");
                  }else if (Deferrable==1 && Initially==1)
                  {
                    out.println("INITIALLY DEFERRED DEFERRABLE");
                  }
                  out.println("/");
                  out.println();
              
                  //INDEKSI
                  if (OneFileOnly)
                  {
                  if (IndeksiF)
                  { 
                    if (alter)  
                    {
                        query = "select * from user_indexes where index_name = 'FK_"+ SemaRelNameL.toUpperCase() +"' and table_name = '" + SemaRelNameL.toUpperCase() + "'";
                        try 
                        {     
                          stmt = conn.createStatement();       
                          rset = stmt.executeQuery(query);
                          if (rset.next ())
                          {
                              str = "DROP INDEX "+ "FK_"+SemaRelNameL.replaceAll(" ","_").toUpperCase().replaceAll(" ","_");
                              out.println(str);  
                              out.println("/");                      
                              out.println();
                          }
                        } 
                        catch (Exception ex) 
                        {
                          ex.printStackTrace();
                        } 
                    }
                    str = "CREATE INDEX "+ "FK_"+SemaRelNameL.replaceAll(" ","_");
                    out.println(str);                                       
                    str = "   ON "+SemaRelNameL.replaceAll(" ","_");
                    out.println(str);
                    str="   (";
                    out.println(str);                  
                    str = "";
                    for (int u=0;u<m;u++)
                    {
                      str = str + AtributNameL[u].replaceAll(" ","_");
                      if (u<m-1)
                      str = str + ",";
                    }
                    out.println("       "+str);  
                    str = "   )";                  
                    out.println(str);
                    out.println("/");
                    out.println();
                  }//INDEKSIF
                  }else
                  {
                    if (alter)  
                    {
                        query = "select * from user_indexes where index_name = 'FK_"+ SemaRelNameL.toUpperCase() +"' and table_name = '" + SemaRelNameL.toUpperCase() + "'";
                        try 
                        {     
                          stmt = conn.createStatement();       
                          rset = stmt.executeQuery(query);
                          if (rset.next ())
                          {
                              str = "DROP INDEX "+ "FK_"+SemaRelNameL.toUpperCase().replaceAll(" ","_");
                              out.println(str);  
                              out.println("/");                      
                              out.println();
                          }
                        } 
                        catch (Exception ex) 
                        {
                          ex.printStackTrace();
                        } 
                    }
                    str = "CREATE INDEX "+ "FK_"+SemaRelNameL.replaceAll(" ","_");
                    outInd.println(str);                                       
                    str = "   ON "+SemaRelNameL.replaceAll(" ","_");
                    outInd.println(str);
                    str="   (";
                    outInd.println(str);                  
                    str = "";
                    for (int u=0;u<m;u++)
                    {
                      str = str + AtributNameL[u].replaceAll(" ","_");
                      if (u<m-1)
                      str = str + ",";
                    }
                    outInd.println("       "+str);  
                    str = "   )";                  
                    outInd.println(str);
                    out.println("/");
                    out.println();
                  }
               
  }
  
  private String NazivTabele(int IdTabele)
  {
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    String ret="";
    String upit = "select RS_name from IISC_RELATION_SCHEME where PR_id="+IdProjekta+" and AS_id=" + IdASistema+ " and RS_id="+IdTabele;     
    try{
      rs=query.select(upit);
      if (rs.next())
      {
         ret=rs.getString("RS_name");
      }      
    }
    catch(Exception ex){     
        ex.printStackTrace();
      }    
      return ret;
  }
  
  private String NazivAtributa(int IdAtributa)
  {
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    String ret="";
    String upit = "select Att_mnem from IISC_ATTRIBUTE where IISC_ATTRIBUTE.PR_id="+IdProjekta+" and IISC_ATTRIBUTE.Att_id="+IdAtributa;     
    try{
      rs=query.select(upit);
      if (rs.next())
      {
         ret=rs.getString("Att_mnem");
      }      
    }
    catch(Exception ex){     
        ex.printStackTrace();
      }    
      return ret;
  }
  void JosTrigera(PrintWriter out, Connection con)
  {    
    JDBCQuery query2=new JDBCQuery(con);
    ResultSet rs2;
    String[] ModifableAtr=null;            
            for (int a=0;a<IdSema.length;a++)
            {                  
                  ModifableAtr=Pom.ModifiableObelezja(IdProjekta, IdSema[a], IdASistema, con);                  
                  if (ModifableAtr != null && ModifableAtr.length != 0)
                      Trigeri.UpdatePKey(out, ImenaSemaRel[a], ModifableAtr);
            }
      
      String upit = "select * from IISC_SEQUENCE where PR_id="+IdProjekta+ " and AS_id="+IdASistema;      
      int IdTabele=0;
      int IdAtributa=0;
      try{
        rs2=query2.select(upit);
        while(rs2.next())
        {          
           IdTabele=rs2.getInt("RS_id");
           IdAtributa=rs2.getInt("Att_id");
           String ImeTabele= NazivTabele(IdTabele);           
           String ImeAtr= NazivAtributa(IdAtributa);
           Trigeri.SetSequence(out, ImeTabele, rs2.getString("Seq_name"), ImeAtr);
        }
      }
        catch(Exception ex){     
        ex.printStackTrace();
      }          
             
  }
  
  void DatotekaDDl(PrintWriter out, String Verzija)
  {
    String str = "";
    str = Verzija;
    out.println(str);
    DateFormat df = DateFormat.getDateTimeInstance();
    String s = df.format(d);     
    str = "-- Created on:  " + s;
    out.println(str);
    out.println("SPOOL " + FileIme + "_DDL.lst");
    str = "@@" + FileIme + "_Tab.sql";
    out.println(str);
    out.println();
    if(Ogranicenja)
    {
      str = "@@" + FileIme + "_Con.sql";
      out.println(str);
    }
    if(IndeksiP || IndeksiU || IndeksiF)
    {
      str = "@@" + FileIme + "_Ind.sql";
      out.println(str);
    }
    if (Triger)
    {
      str = "@@" + FileIme + "_Trg.sql";
      out.println(str);
    }
    out.println("SPOOL OFF");
  }
  
  void DatotekaTrigeri(PrintWriter out,PrintWriter GlobalOutZ,PrintWriter GlobalOutB, Connection con, int[] IdSemaRelL,int[] IdSemaRelR, int RSCType, int RSCRelIntType, String[] SemaRelNameL, String[] SemaRelNameR, String RSCName, int[] IdAtributaL, int[] IdAtributaR, int InsActionL, int DelActionR, int ModActionR, int ModActionL, boolean NoAction, boolean View)
  {
    Trigeri = new TriggerOracle(ImeBaze);    
    JDBCQuery query=new JDBCQuery(con);
    int[] PKljucL = Pom.PKljucLeveStrane(query, con, IdSemaRelL[0], IdProjekta, IdASistema); 
    /*
    File FilePZ=null;
    FilePZ = new File("PackZDat.txt");
    PrintWriter outPackageZ=null; 
    String fPZ = FilePZ.toString();
    File FilePBody;
    FilePBody = new File("PackBodyDat.txt");
    String fPBody = FilePBody.toString();
    PrintWriter outPackageB=null; 
    try
    {
      outPackageZ = new PrintWriter(new BufferedWriter(new FileWriter(FilePZ)));
    }
    catch (IOException e)
    {
      System.err.println("Cannot open file.");
      System.err.println(e);
    }
    try
    {
      outPackageB = new PrintWriter(new BufferedWriter(new FileWriter(FilePBody)));
    }
    catch (IOException ex)
    {
      System.err.println("Cannot open file.");
      System.err.println(ex);
    }   
    */
    PackageNameConst = RSCName.replaceAll(" ","_")+"_PCK.";         
   
    String str;
                                
    if ((RSCType == 0 || RSCType == 2) && RSCRelIntType == 1) //Osnovni ref integritet, parcijalni
    {                               
               out.println("CREATE OR REPLACE PACKAGE "+ RSCName.replaceAll(" ","_")+"_PCK"); 
               out.println("IS"); 
               String[] ImeAR = Pom.FAtributName(IdAtributaR, con, IdProjekta);
               out.println("TYPE TRecDel"+SemaRelNameR[0].replaceAll(" ","_") + " IS RECORD (");
               for (int i=0;i<ImeAR.length;i++)
               {
                  str = ImeAR[i].replaceAll(" ","_")+ " "+ SemaRelNameR[0].replaceAll(" ","_")+"." + ImeAR[i].replaceAll(" ","_") + "%TYPE";      
                  if (i<ImeAR.length-1)
                  {
                    str = str+",";
                  }else
                  {
                    str = str+");";
                  }
                  out.println(str);      
               }               
               out.println("TYPE TTabForDelete IS TABLE OF TRecDel"+SemaRelNameR[0].replaceAll(" ","_"));
               out.println("INDEX BY BINARY_INTEGER;");
               out.println("TYPE TRecUpd"+SemaRelNameR[0].replaceAll(" ","_") + " IS RECORD (");
               for (int i=0;i<ImeAR.length;i++)
               {
                  str = ImeAR[i].replaceAll(" ","_")+ "_Old "+ SemaRelNameR[0].replaceAll(" ","_")+"." + ImeAR[i].replaceAll(" ","_") + "%TYPE,";                   
                  out.println(str);      
               } 
               for (int i=0;i<ImeAR.length;i++)
               {
                  str = ImeAR[i].replaceAll(" ","_")+ "_New "+ SemaRelNameR[0].replaceAll(" ","_")+"." + ImeAR[i].replaceAll(" ","_") + "%TYPE";                   
                  if (i<ImeAR.length-1)
                  {
                    str = str+",";
                  }else
                  {
                    str = str+");";
                  }
                  out.println(str);      
               }                
               out.println("TYPE TTabForUpdate IS TABLE OF TRecUpd"+SemaRelNameR[0].replaceAll(" ","_"));
               out.println("INDEX BY BINARY_INTEGER;");
               out.println("For_Del_" +SemaRelNameR[0].replaceAll(" ","_")+" "+PackageNameConst+"TTabForDelete;");               
               out.println("Del_Count NUMBER(8,0);");
               out.println("For_Upd_" +SemaRelNameR[0].replaceAll(" ","_")+" "+PackageNameConst+"TTabForUpdate;");
               out.println("Upd_Count NUMBER(8,0);");
               out.println("END;");
               out.println("/");
               out.println();
               Trigeri.UpisOsnovniRIL(out,GlobalOutZ,GlobalOutB, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaL, IdAtributaR, IdProjekta, Rek, RSCRelIntType, InsActionL,Pom,IdASistema, IdSemaRelL[0], con);                                                      
               Trigeri.UpdateOsnovniRIL(out,GlobalOutZ,GlobalOutB, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName,IdAtributaL, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionL, Pom, IdASistema, IdSemaRelL[0], con);
               Trigeri.BrisanjeOsnovniPRIR(PackageNameConst, out, GlobalOutZ,GlobalOutB,outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, DelActionR, IdAtributaL, PKljucL, Pom, IdASistema, IdSemaRelL[0], con);//Osnovni ref integritet bez default kad je no action - to je deklartivno vec uradjeno
               Trigeri.UpdateOsnovniPRIR(PackageNameConst,out, GlobalOutZ,GlobalOutB,outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionR, IdAtributaL,PKljucL, Pom, IdASistema, IdSemaRelL[0], con);
                              
    } else
    if ((RSCType == 0 || RSCType == 2) && RSCRelIntType == 2) //Osnovni ref integritet full
    {
               out.println("CREATE OR REPLACE PACKAGE "+ RSCName.replaceAll(" ","_")+"_PCK"); 
               out.println("IS");
               String[] ImeAR = Pom.FAtributName(IdAtributaR, con, IdProjekta);
               if (DelActionR==-3)
               {                 
                out.println("TYPE TRecDel"+SemaRelNameR[0].replaceAll(" ","_") + " IS RECORD (");
                for (int i=0;i<ImeAR.length;i++)
                {
                  str = ImeAR[i].replaceAll(" ","_")+ " "+ SemaRelNameR[0].replaceAll(" ","_")+"." + ImeAR[i].replaceAll(" ","_") + "%TYPE";      
                  if (i<ImeAR.length-1)
                  {
                    str = str+",";
                  }else
                  {
                    str = str+");";
                  }
                  out.println(str);      
                }               
                out.println("TYPE TTabForDelete IS TABLE OF TRecDel"+SemaRelNameR[0].replaceAll(" ","_"));
                out.println("INDEX BY BINARY_INTEGER;");
                out.println("For_Del_" +SemaRelNameR[0].replaceAll(" ","_")+" "+PackageNameConst+"TTabForDelete;");               
                out.println("Del_Count NUMBER(8,0);");               
               }
               if (ModActionR!=-1 || ModActionR!=2)
               {
                 out.println("TYPE TRecUpd"+SemaRelNameR[0].replaceAll(" ","_") + " IS RECORD (");
                 for (int i=0;i<ImeAR.length;i++)
                {
                  str = ImeAR[i].replaceAll(" ","_")+ "_Old "+ SemaRelNameR[0].replaceAll(" ","_")+"." + ImeAR[i].replaceAll(" ","_") + "%TYPE,";                   
                  out.println(str);      
                } 
                for (int i=0;i<ImeAR.length;i++)
                {
                  str = ImeAR[i].replaceAll(" ","_")+ "_New "+ SemaRelNameR[0].replaceAll(" ","_")+"." + ImeAR[i].replaceAll(" ","_") + "%TYPE";                   
                  if (i<ImeAR.length-1)
                  {
                    str = str+",";
                  }else
                  {
                    str = str+");";
                  }
                  out.println(str);      
                }                
                out.println("TYPE TTabForUpdate IS TABLE OF TRecUpd"+SemaRelNameR[0].replaceAll(" ","_"));
                out.println("INDEX BY BINARY_INTEGER;");
                 out.println("For_Upd_" +SemaRelNameR[0].replaceAll(" ","_")+" "+PackageNameConst+"TTabForUpdate;");
                 out.println("Upd_Count NUMBER(8,0);");
               }
               if (ModActionR!=-1 || ModActionR!=2 || DelActionR==-3)
               {
                  out.println("END;");
                  out.println("/");
                  out.println();
               }
               Trigeri.UpisOsnovniRIL(out,GlobalOutZ,GlobalOutB, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaL, IdAtributaR, IdProjekta, Rek, RSCRelIntType, InsActionL, Pom, IdASistema, IdSemaRelL[0], con);                   
               Trigeri.UpdateOsnovniRIL(out, GlobalOutZ,GlobalOutB,outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName,IdAtributaL, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionL, Pom, IdASistema, IdSemaRelL[0], con);
               if (DelActionR!=-3)
               {
                Trigeri.BrisanjeOsnovniDFRIR(out,GlobalOutZ,GlobalOutB, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, DelActionR, IdAtributaL,PKljucL, Pom, IdASistema, IdSemaRelL[0], con);//Osnovni ref integritet bez default kad je no action - to je deklartivno vec uradjeno
               }else
               {
                 Trigeri.BrisanjeOsnovniDFRIDef(PackageNameConst,out,GlobalOutZ,GlobalOutB,outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, IdAtributaL,PKljucL, Pom, con);
               }
               if (ModActionR==-1 || ModActionR==-2)
               {
                  Trigeri.UpdateOsnovniDFRIR(out,GlobalOutZ,GlobalOutB, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionR, IdAtributaL,PKljucL, Pom, IdASistema, IdSemaRelL[0], con);
               }   else
               {
                  Trigeri.UpdateOsnovniDFRICascDef(PackageNameConst,out, GlobalOutZ,GlobalOutB,outWarning,SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionR, IdAtributaL,PKljucL, Pom, con);
               }
     } else
     if ((RSCType == 0 || RSCType == 2) && RSCRelIntType == 0 && NoAction != true) //Osnovni ref integritet default
     {
               if (ModActionR == -3 || (DelActionR == -3))
               {
                  out.println("CREATE OR REPLACE PACKAGE "+ RSCName.replaceAll(" ","_")+"_PCK"); 
                  out.println("IS");
               }   
               String[] ImeAR = Pom.FAtributName(IdAtributaR, con, IdProjekta);
               if (ModActionR == -3)
               {
                  out.println("TYPE TRecUpd"+SemaRelNameR[0].replaceAll(" ","_") + " IS RECORD (");
                 for (int i=0;i<ImeAR.length;i++)
                {
                  str = ImeAR[i].replaceAll(" ","_")+ "_Old "+ SemaRelNameR[0].replaceAll(" ","_")+"." + ImeAR[i].replaceAll(" ","_") + "%TYPE,";                   
                  out.println(str);      
                } 
                for (int i=0;i<ImeAR.length;i++)
                {
                  str = ImeAR[i].replaceAll(" ","_")+ "_New "+ SemaRelNameR[0].replaceAll(" ","_")+"." + ImeAR[i].replaceAll(" ","_") + "%TYPE";                   
                  if (i<ImeAR.length-1)
                  {
                    str = str+",";
                  }else
                  {
                    str = str+");";
                  }
                  out.println(str);      
                }                
                out.println("TYPE TTabForUpdate IS TABLE OF TRecUpd"+SemaRelNameR[0].replaceAll(" ","_"));
                out.println("INDEX BY BINARY_INTEGER;");
                 out.println("For_Upd_" +SemaRelNameR[0].replaceAll(" ","_")+" "+PackageNameConst+"TTabForUpdate;");
                 out.println("Upd_Count NUMBER(8,0);");
               }
               if (DelActionR == -3)
               {
                out.println("TYPE TRecDel"+SemaRelNameR[0].replaceAll(" ","_") + " IS RECORD (");
                for (int i=0;i<ImeAR.length;i++)
                {
                  str = ImeAR[i].replaceAll(" ","_")+ " "+ SemaRelNameR[0].replaceAll(" ","_")+"." + ImeAR[i].replaceAll(" ","_") + "%TYPE";      
                  if (i<ImeAR.length-1)
                  {
                    str = str+",";
                  }else
                  {
                    str = str+");";
                  }
                  out.println(str);      
                }               
                out.println("TYPE TTabForDelete IS TABLE OF TRecDel"+SemaRelNameR[0].replaceAll(" ","_"));
                out.println("INDEX BY BINARY_INTEGER;");
                out.println("For_Del_" +SemaRelNameR[0].replaceAll(" ","_")+" "+PackageNameConst+"TTabForDelete;");               
                out.println("Del_Count NUMBER(8,0);"); 
               }
               if (ModActionR==-3 || DelActionR==-3)
               {
                  out.println("END;");
                  out.println("/");
                  out.println();
               }
               if (InsActionL != -1 || ModActionL != -1 || DelActionR==-3 || ModActionR==-3)
               {
                     Trigeri.UpisOsnovniRIL(out, GlobalOutZ,GlobalOutB,outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaL, IdAtributaR, IdProjekta, Rek, RSCRelIntType, InsActionL, Pom, IdASistema, IdSemaRelL[0], con); 
               }
               if (InsActionL != -1 || ModActionL != -1 || DelActionR==-3 || ModActionR==-3)
               {                      
                      Trigeri.UpdateOsnovniRIL(out, GlobalOutZ,GlobalOutB,outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaL, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionL, Pom, IdASistema, IdSemaRelL[0], con);
               }
               if (ModActionR == -1 || ModActionR == -2 || ModActionR == -4)
               {                     
                     Trigeri.UpdateOsnovniDFRIR(out,GlobalOutZ,GlobalOutB, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionR, IdAtributaL,PKljucL, Pom, IdASistema, IdSemaRelL[0], con);
               }
               if (ModActionR == -3)
               {
                  Trigeri.UpdateOsnovniDFRICascDef(PackageNameConst,out,GlobalOutZ,GlobalOutB, outWarning,SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, ModActionR, IdAtributaL,PKljucL, Pom, con);
               }
               if (ModActionR==-3 || ((InsActionL != -1 || ModActionL != -1) && (DelActionR == -1 || DelActionR == -2 || DelActionR == -4)))
               {
                 Trigeri.DeleteOsnovniDFRIR(out,GlobalOutZ,GlobalOutB, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, DelActionR, IdAtributaL,PKljucL, Pom, IdASistema, IdSemaRelL[0], con);
               }
               if (DelActionR == -3)
               {
                  Trigeri.BrisanjeOsnovniDFRIDef(PackageNameConst,out, GlobalOutZ,GlobalOutB, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaR, IdProjekta, Rek, RSCRelIntType, IdAtributaL,PKljucL, Pom, con);
               }
      }
      int Akcija;
      if (RSCType == 4)
      {                               
                out.println("CREATE OR REPLACE PACKAGE "+ RSCName.replaceAll(" ","_")+"_PCK"); 
                out.println("IS");
                String[] ImeAR = Pom.FAtributName(IdAtributaR, con, IdProjekta);
                out.println("TYPE TRec"+SemaRelNameR[0].replaceAll(" ","_") + " IS RECORD (");
                for (int i=0;i<ImeAR.length;i++)
                {
                  str = ImeAR[i].replaceAll(" ","_")+ " "+ SemaRelNameR[0].replaceAll(" ","_")+"." + ImeAR[i].replaceAll(" ","_") + "%TYPE";      
                  if (i<ImeAR.length-1)
                  {
                    str = str+",";
                  }else
                  {
                    str = str+");";
                  }
                  out.println(str);      
                }               
                out.println("TYPE TTabForDelUpd IS TABLE OF TRec"+SemaRelNameR[0].replaceAll(" ","_"));
                out.println("INDEX BY BINARY_INTEGER;");
                out.println("For_" +SemaRelNameR[0].replaceAll(" ","_")+" "+PackageNameConst+"TTabForDelUpd;");               
                out.println("Count_IRI NUMBER(8,0);"); 
                out.println("IzvrsenjeOkidaca BOOLEAN;");
                out.println("END;");
                out.println("/");
             if (View == false)
             {              
               Trigeri.ProceduraZaUpisIRI(PackageNameConst,GlobalOutZ,GlobalOutB,IdProjekta, Pom, RSCName, out, SemaRelNameL[0], SemaRelNameR[0], IdASistema, con, IdSemaRelR[0],IdSemaRelL[0],IdAtributaR,IdAtributaL, Pom);                
             }else
             {              
               Trigeri.InvezniRIPogled(PackageNameConst,out, outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaL, IdAtributaR, IdProjekta, Pom, con,IdASistema);
             } 
             
             out.println();
             Akcija = 1; //delete                   
             Trigeri.BrisanjeUpdateInverzniRI(PackageNameConst,out, outWarning,SemaRelNameR[0], SemaRelNameL[0], RSCName, IdAtributaR, IdAtributaL,IdProjekta, DelActionR,Akcija, Pom, con);
             Akcija = 2;   //update
             Trigeri.BrisanjeUpdateInverzniRI(PackageNameConst,out,outWarning, SemaRelNameR[0], SemaRelNameL[0], RSCName, IdAtributaR, IdAtributaL,IdProjekta, DelActionR,Akcija,Pom,con);
             Trigeri.UpisOsnovniIRIRefIL(PackageNameConst,GlobalOutZ,GlobalOutB,out,outWarning, SemaRelNameL[0], SemaRelNameR[0], RSCName, IdAtributaL, IdAtributaR,IdProjekta, Pom,con);                 
             
      }
      //outPackageZ.println("END;");
      //outPackageB.println("END;");
      //outPackageZ.close();
      //outPackageB.close();
  }
  void FormirajSkript()
  {
      
    ResultSet rs;
    PrintWriter out=null;
    PrintWriter outCeo=null;
    PrintWriter outSql=null;
    PrintWriter outTable=null; 
    PrintWriter outInd=null;
    PrintWriter outConstr=null;
    PrintWriter outTrigeri=null;
    PrintWriter outPackageGlobalZ=null;
    PrintWriter outPackageGlobalB=null;
    PrintWriter Datoteka;
    String Verzija;
    
    Verzija = "-- DBMS name:      Oracle Server 9i ";
    JDBCQuery query=new JDBCQuery(con);
    Rekurzije Rek=new Rekurzije();
    int BrRelacija=PodaciZaTabele(con,Rek);
    String[] ImenaDatoteka=null;
    if(!OneFileOnly)
    {
      ImenaDatoteka = new String[3];
      ImenaDatoteka[0]="GlobalZ_PCK.sql";
      ImenaDatoteka[1]="GlobalB_PCK.sql";
      ImenaDatoteka[2]="SkriptBezPCK.sql";     
    }else
    {
      ImenaDatoteka = new String[4];
      ImenaDatoteka[0]=FileIme+"_Tab.sql";
      ImenaDatoteka[1]="GlobalZ_PCK.sql";
      ImenaDatoteka[2]="GlobalB_PCK.sql";
      ImenaDatoteka[3]="SkriptBezPCK.sql";  
    }
    ListOfFiles mylist = new ListOfFiles(ImenaDatoteka);
    
    try
    {
      File outputFile;
      if (OneFileOnly)
      {
        outputFile = new File(PrefixNaziva + ".sql");
      }else
      {
        outputFile = new File(PrefixNaziva+"_Trg.sql");
      }
      outCeo = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
    }  
    catch (IOException ex) {
            System.err.println("Cannot open file.");
            System.err.println(ex);
    }  
    
    OracleConnection kon = null;
    //konekcija na Oracle bazu
   if (alter) 
   {
    String drivers = "oracle.jdbc.driver.OracleDriver";
     try {
        Class.forName(drivers);
     }
     catch (Exception e) {
        //System.out.println("Nije ucitan Oracle JDBC Driver!");
         System.err.println(e);
     }

     String url = "jdbc:oracle:thin:@localhost:1521:" + HostO;    
          
     try  {
        kon = (OracleConnection)DriverManager.getConnection(url, usernameO, passwordO);
        //System.out.println("Uspesna konekcija na bazu podataka!");
          }
      catch (Exception ex) {
          ex.printStackTrace();
          //System.err.println("Neuspesna prijava na bazu podataka!");
          System.err.println(ex);
      }
   }
    //kraj konekcije
     
    try {
        if (OneFileOnly == true)
           {
              File outputFile = new File("SkriptBezPCK.sql");
              out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
              File outputFileTable = new File(FileIme+"_Tab.sql");
              outTable = new PrintWriter(new BufferedWriter(new FileWriter(outputFileTable)));
           }else
           {
              File outputFileSql = new File(PrefixNaziva+"_DDL.sql");
              outSql = new PrintWriter(new BufferedWriter(new FileWriter(outputFileSql)));
              File outputFileTable = new File(PrefixNaziva+"_Tab.sql");
              outTable = new PrintWriter(new BufferedWriter(new FileWriter(outputFileTable)));
              if (IndeksiP || IndeksiU || IndeksiF)
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
                File outputFileTrigeri = new File("SkriptBezPCK.sql");                
                outTrigeri = new PrintWriter(new BufferedWriter(new FileWriter(outputFileTrigeri)));
              
              }
           }          
    }
    catch (IOException ex) {
            System.err.println("Cannot open file.");
            System.err.println(ex);
    }   
       
         if (OneFileOnly == true)
         {
           //Datoteka = out;
           Datoteka = outTable;
         }else
         {
           Datoteka = outTable;
           DatotekaDDl(outSql,Verzija);
         }
         DatotkaTabele(Datoteka, BrRelacija, kon);
         if (IndeksiP || IndeksiF || IndeksiU) 
         {
                  if (OneFileOnly == true)
                    {
                        Datoteka = out;
                    }else
                    {
                        Datoteka = outInd;
                    }    
                    DatotkaIndeksi(Datoteka, BrRelacija, kon);
         }
         if (Ogranicenja==true)
         {
            if (OneFileOnly == true)
            {
              DatotekaConstraints(out, BrRelacija, kon);
            }else
            {
              DatotekaConstraints(outConstr, BrRelacija, kon);
            }                 
         }                
            
         try { 
           if ((Ogranicenja || Triger) && Constr != null)
           { 
            // ref. integriteti
            String upit;            
            try{
              File outputFilePackageZ = new File("GlobalZ_PCK.sql");
              outPackageGlobalZ = new PrintWriter(new BufferedWriter(new FileWriter(outputFilePackageZ)));
            }catch (IOException ex) {
              System.err.println("Cannot open file.");
              System.err.println(ex);
            }   
            try{
              File outputFilePackageB = new File("GlobalB_PCK.sql");
              outPackageGlobalB = new PrintWriter(new BufferedWriter(new FileWriter(outputFilePackageB)));
            }catch (IOException ex) {
              System.err.println("Cannot open file.");
              System.err.println(ex);
            }               
            
            String PackageName = "Global_PCK";            
            outPackageGlobalZ.println("CREATE OR REPLACE PACKAGE "+PackageName.replaceAll(" ","_")); 
            outPackageGlobalZ.println("IS");
            outPackageGlobalB.println("CREATE OR REPLACE PACKAGE BODY "+PackageName.replaceAll(" ","_"));
            outPackageGlobalB.println("IS");
            
            
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
                 if ((RSCType == 0 || RSCType == 2) && RSCRelIntType == 0 && ModActionR!=-3 && (NoAction == true || (ModActionL == -1 && InsActionL == -1 && DelActionR == -1) || (ModActionL == -1 && InsActionL == -1 && DelActionR == -4) || (ModActionL == -1 && InsActionL == -1 && DelActionR == -2))) 
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
                        ConstraintForeignKey(Datoteka,outInd,SemaRelNameL[0],RSCName,AtributNameL,SemaRelNameR[0],AtributNameR,DelActionR,m,n, kon, Deferrable, Initially);
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
                    DatotekaTrigeri(Datoteka,outPackageGlobalZ,outPackageGlobalB,con, IdSemaRelL,IdSemaRelR, RSCType, RSCRelIntType, SemaRelNameL, SemaRelNameR, RSCName, IdAtributaL, IdAtributaR, InsActionL, DelActionR, ModActionR, ModActionL, NoAction, View);
                }               
                 
              } // rs.next            
              else// rs.next                  
              {
                  query.Close();
              }
            } //for i constraint 
           } //ako zeli da generise ogranicenja
           
           if (Triger==true)
           {
            if (OneFileOnly)
            {
              JosTrigera(out, con);
            }else
            {
              JosTrigera(outTrigeri, con);
            }            
           } 
           
           outPackageGlobalZ.println("END;");
           outPackageGlobalZ.println("/");
           outPackageGlobalB.println("END;");
           outPackageGlobalB.println("/");
           outPackageGlobalZ.println();
           outPackageGlobalB.println();
           outPackageGlobalZ.close();
           outPackageGlobalB.close();
           if (OneFileOnly)
            {              
              out.close();
              outTable.close();
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
            //spajanje datoteka
            SequenceInputStream s = new SequenceInputStream(mylist);
            int c;
            try
            {
              while ((c = s.read()) != -1)
              outCeo.write(c);              
              s.close();
            }
            catch (IOException e)            
            {
              System.err.println("Cannot open file.");
              System.err.println(e);
            }
            outCeo.close();
        } //try        
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }
       
  }
  
    /*nobrenovic: start*/
    /**
     * This method generates trigger code for the check constraint spanning
     * multiple tables. Generated code is for the Oracle.
     * 
     * @param table Trigger's table
     * @param chkCon Check constraint
     * @param out Output stream, opened for writing
     */
    private void GenerateChkConTrigger(Table table, CheckConstraint chkCon, PrintWriter out) {
    
        final String trgName = "TRG_"+ chkCon.getName() + "_" + table.getImeTabele();
        
        out.println();
        out.println("CREATE OR REPLACE TRIGGER "+ trgName);
        out.println("   BEFORE INSERT OR UPDATE ON "+ table.getImeTabele());    
        out.println("   FOR EACH ROW");
        out.println("   DECLARE cnt INT;");
        out.println("           exc EXCEPTION;");
        out.println("BEGIN");
        
        out.print("    SELECT count(*) INTO cnt FROM ");    
        String tmp = "";
        for (int i=0;i<chkCon.getRefRelSch().size();i++)
        {
            if(!chkCon.getRefRelSch().get(i).equalsIgnoreCase(table.getImeTabele()))
            {
                if(!tmp.equals(""))
                    tmp += ", ";
                tmp+= chkCon.getRefRelSch().get(i);
            }
        } 
        out.println(tmp);
        
        ArrayList<Integer> normJoin = chkCon.getRefRelSchIds();
        normJoin.remove((Integer)table.getTableId());
        out.print("    WHERE ");
        String where = CheckConGenHelper.GenerateJoinWhereClause(con,IdProjekta, IdASistema,normJoin);
        if(!where.equals(""))
            where += "\n        AND ";
        where += CheckConGenHelper.GenerateJoinWithInserted(con,IdProjekta, IdASistema,table.getTableId(), ":NEW", normJoin);
        out.println(where);
        
        String conStr = "";
        if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
             conStr = chkCon.getDnf().toString(true);
        else 
            conStr = chkCon.getCnf().toString(true);
            
        conStr = conStr.replaceAll(table.getImeTabele()+".",":NEW.");
            
        out.println("       AND NOT("+ conStr + ");");
        
        out.println("   IF cnt>0 THEN"); 
        out.println("       RAISE exc;");
        out.println("   END IF;");
        out.println("EXCEPTION");
        out.println("   WHEN exc THEN");
        out.println("       RAISE_APPLICATION_ERROR(-20900,'Naruseno CHECK ogranicenje " + chkCon.getName() + "');");
        out.println("END;");
        out.println("/");
        out.println();
        
    }
    
    /*nobrenovic: stop*/
}