package iisc;
import java.sql.*;
import java.text.DateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.io.*;

import java.util.ArrayList;
import java.util.Date;

public class ScriptANSI 
{
 
  Connection con;
  int IdASistema;
  int IdProjekta;
  int[] IdSema;
  String[] ImenaSemaR;
  Table[] Tabele; 
  int[] Constr;  
  TriggerMSSQL Trigeri = new TriggerMSSQL();
  ClassFunction Pom = new ClassFunction();
  Rekurzije Rek = new Rekurzije();  
  boolean alter;
  //PrintWriter out;
  PrintWriter outWarning;
  String PrefixNaziva;
  boolean OneFileOnly;
  boolean Ogranicenja, IndeksiP,IndeksiF, IndeksiU; 
  boolean Triger;
  String FileIme;
  private Date d = new Date();
  
  /*nobrenovic: start*/
  private ArrayList<CheckConstraint> chkCons = new ArrayList<CheckConstraint>(); 
  private CheckConstraint.ImplemetationType chkConImplType;
  /*nobrenovic: stop*/
  
      
  public ScriptANSI()
  {
  }
  public ScriptANSI(Connection Kon, int PrId, int AsId, int[] SemeRelId, String[] SemeRelName,boolean Promena, int[] Constraint, PrintWriter warningf, String PrefixNazivaDat, boolean OneFile, CheckConstraint.ImplemetationType chkConImplType, boolean Ogr, boolean IndP, boolean IndF, boolean IndU, boolean Trg, String FileName)
  {
    con = Kon;
    IdASistema = AsId;
    IdProjekta = PrId;
    IdSema = SemeRelId;    
    ImenaSemaR = SemeRelName;
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
    this.chkConImplType = chkConImplType;
  }
  private int NadjiIdSemeRelacije(String ImeSemeRel) 
   {
     ResultSet rs2;
    int idsema=0;
    JDBCQuery query2=new JDBCQuery(con);
    String upit = "select RS_id from IISC_RELATION_SCHEME where PR_ID=" + IdProjekta + "and AS_ID=" + IdASistema + " and RS_name='" + ImeSemeRel+ "'";
      try
      {
        rs2=query2.select(upit);
        if (rs2.next())
        {
          idsema=rs2.getInt("RS_id");
        }
        query2.Close();
      } catch(Exception e){    
        e.printStackTrace();        
      } 
      return idsema;        
   }
   
    private int NadjiIdAtributa(String ImeAtr) 
   {
      ResultSet rs2;
      int idatr=0;
      JDBCQuery query2=new JDBCQuery(con);
      String upit = "select Att_id from IISC_ATTRIBUTE where PR_ID=" + IdProjekta + " and Att_mnem='" + ImeAtr+"'";
      try
      {
        rs2=query2.select(upit);
        if (rs2.next())
        {
          idatr=rs2.getInt("Att_id");
        }
        query2.Close();
      } catch(Exception e){    
        e.printStackTrace();        
      } 
      return idatr;        
   }
  int PodaciZaTabele(Connection con,JDBCQuery query,Rekurzije Rek)
  {
         
    ResultSet rs;
    int j=IdSema.length;
    int l=0;    
    Tabele = new Table[j];    
    for (int i=0; i < Tabele.length; i++)
      if(Tabele[i] == null)
        Tabele[i]= new Table();
     
    try {    
    for(int i=0; i < j; i++) { 
      l=0;
      Tabele[i].setTableId(IdSema[i]);
      Tabele[i].setImeTabele(ImenaSemaR[i].replaceAll(" ","_")); 
      int k=Pom.brojanjeNtorki("IISC_RS_ATT","PR_id="+IdProjekta+" and RS_id="+IdSema[i]+" and AS_id="+IdASistema,con); //broji koliko ima atributa odgovarajuca sema relacije
      String upit1 = "select Att_id,Att_null from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSema[i]+" and IISC_RS_ATT.PR_id="+IdProjekta+" and IISC_RS_ATT.AS_id="+IdASistema+" ORDER BY Att_sequence";
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
      String upit4 = "select RS_rbrk from IISC_RS_KEY where IISC_RS_KEY.RS_id="+IdSema[i]+" and IISC_RS_KEY.PR_id="+IdProjekta+" and IISC_RS_KEY.AS_id="+IdASistema+" and RS_primary_key=1";      
      rs=query.select(upit4);
      if(rs.next())
      {
        IdKljuca=rs.getInt(1);
        query.Close();      
        String upit5 = "select Att_id from IISC_RSK_ATT where IISC_RSK_ATT.RS_id="+IdSema[i]+" and IISC_RSK_ATT.PR_id="+IdProjekta+" and IISC_RSK_ATT.AS_id="+IdASistema+" and IISC_RSK_ATT.RS_rbrk="+IdKljuca+" ORDER BY Att_rbrk";
        int v=Pom.brojanjeNtorki("IISC_RSK_ATT","IISC_RSK_ATT.RS_id="+IdSema[i]+" and IISC_RSK_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" and IISC_RSK_ATT.RS_rbrk="+IdKljuca,con); 
        int[] IdAtr=query.selectArraySAint(upit5,v,1);
        query.Close();
        for (int s=0;s<IdAtr.length;s++)
        {
          String upit6 = "select Att_mnem from IISC_ATTRIBUTE where IISC_ATTRIBUTE.PR_id="+IdProjekta+" and IISC_ATTRIBUTE.Att_id="+IdAtr[s];     
          rs=query.select(upit6);
          rs.next();
          String MnemAtr=rs.getString(1);
          CeoKljuc=CeoKljuc+MnemAtr;
          if (s!=IdAtr.length-1)
          {
            CeoKljuc=CeoKljuc+",";
          }
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
          CeoKljuc=CeoKljuc+MnemAtr;
          if (s!=IdAtr.length-1)
          {
            CeoKljuc=CeoKljuc+",";
          }
        }
        Tabele[i].setUnique(g,CeoKljuc);
        //Tabele[i].setUnique(g,CeoKljuc.replaceAll(" ","_"));         
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
     
      for(int m=0; m < k; m++)
      {        
        String upit2 = "select Att_mnem,Dom_id,Att_expr,Att_default from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributa[m]+" and Att_sbp=1";
        rs=query.select(upit2);
        if(rs.next())
        {  
          l=l+1;   
          String ImeAtr=rs.getString(1);
          int IdDomena = rs.getInt(2);
          String AIzraz=rs.getString(3);
          String ADefault=rs.getString(4);
          String Izraz;
          query.Close();
          
          Tabele[i].setImeAtributa(m,ImeAtr.replaceAll(" ","_")); 
          
           /*nobrenovic: start*/   
           ArrayList<CheckConstraint> chkCons = CheckConGenHelper.findAttrChkCon(con,IdProjekta,IdASistema, Integer.parseInt(IdAtributa[m]));
           if(chkCons != null && chkCons.size()>0)
             Tabele[i].getAttrChkCons().put(ImeAtr, chkCons);
           /*nobrenovic: stop*/
          
          String upit3 = "select Dom_mnem,Dom_default,Dom_parent,Dom_type from IISC_DOMAIN where PR_id="+IdProjekta+" and Dom_id="+IdDomena;
          rs=query.select(upit3);
          rs.next();
          String Domen = rs.getString(1); 
          String DomenDefault;   
          int TipDomena = rs.getInt(4);
          query.Close(); 
          Domen=Domen.replaceAll(" ","_");
         if(TipDomena==0 || TipDomena ==1)
         {
          if (NullVrednosti[m]==0)
          {
            Domen=Domen+" NOT NULL";             
          }
          if (ADefault!=null)
          {
            ADefault=ADefault.trim();
          }
          if (ADefault!=null && ADefault.length()!=0)
          {
            Domen=Domen+" DEFAULT '"+ADefault+"'";
          }else
          {
            DomenDefault=Rek.RekZaDefault(IdDomena, query, IdProjekta);            
            if (DomenDefault!="")
            {
              Domen=Domen+" DEFAULT '"+DomenDefault+"'";
            }            
          }         
         }         
         Tabele[i].setTip(m,Domen);
        if (AIzraz !=null && AIzraz.length()!=0)
          {
            Izraz=AIzraz;
          }else
          {
            Izraz=Rek.RekZaIzraz(IdDomena, query, IdProjekta);
          }
          Tabele[i].setIzrazi(m,Izraz);     
       } else 
       {
        query.Close();
       }
      }    //for m 
      
      Tabele[i].setBrAtributa(l);      
     }  //for i
     
      /*nobrenovic: start*/
      //find check constraints related to all schemes
      rs=query.select("select IISC_CHECK_CONSTRAINT.CHKC_id  from IISC_CHECK_CONSTRAINT " +
                   " where IISC_CHECK_CONSTRAINT.AS_id="  + IdASistema +
                   " and IISC_CHECK_CONSTRAINT.PR_id=" + IdProjekta);
      while(rs.next())
      {  
        int chkConID = rs.getInt(1);
        CheckConstraint chkCon = CheckConstraint.loadCheckConstraint(con, IdProjekta, IdASistema, chkConID);
        if(chkCon.getRefRelSchIds().size()==1)
        {
            int tblId = chkCon.getRefRelSchIds().get(0);
            for(int i=0;i<Tabele.length;i++)
            {
                if(Tabele[i].getTableId() == tblId)
                {
                    Tabele[i].getCheckCons().add(chkCon);
                    break;
                }
            }
        }
        else
            chkCons.add(chkCon);
      }
      /*nobrenovic: stop*/
      
     return j;
    } //try
     catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          return 0;
                          }
  }  //Tabele
  
  void DatotkaTabele(String ImeBaze,PrintWriter out, int BrRelacija)
  {
    String str;
    str="";
    String[] DomMnem;
    int[] DomDataType;
    int[] DomDuzina;
    int[] DomenParent;
    int[] DomenId;
    int[] DomenType;
    int k,Duzina;
    String TipPodatka; 
    TipPodatka = null;
    ResultSet rs;   
    JDBCQuery query=new JDBCQuery(con);
    Rekurzije rek = new Rekurzije();
    k=Pom.brojanjeNtorki("IISC_DOMAIN","PR_id="+IdProjekta,con);    
    String upit = "select Dom_reg_exp_str,Dom_mnem,Dom_data_type,Dom_length,Dom_parent,Dom_id,Dom_type from IISC_DOMAIN where PR_id="+IdProjekta;
    DomMnem=query.selectArraySA(upit,k,2);
    DomDataType=query.selectArraySAint(upit,k,3); 
    DomDuzina=query.selectArraySAint(upit,k,4);
    DomenParent=query.selectArraySAint(upit,k,5);
    DomenId=query.selectArraySAint(upit,k,6);
    DomenType=query.selectArraySAint(upit,k,7);
    query.Close();
    if (alter == false) 
    {
      for (int i=0;i<BrRelacija;i++)
      {             
              str = "DROP TABLE "+Tabele[i].getImeTabele()+" "+"CASCADE";
              out.println(str);
              out.println();             
      }
      
    }       
           for (int i=0; i<k;i++) 
           {
             if (DomenType[i]==0 || DomenType[i]==1)
             {
              str = "CREATE DOMAIN "+ DomMnem[i]+" AS ";               
              if (DomenParent[i]!=0)
              {
                TipPodatka=Rek.TraziTip(DomenId[i], query, IdProjekta, 0);
              }else
              {
                String upit4 = "select PT_mnemonic from IISC_PRIMITIVE_TYPE where IISC_PRIMITIVE_TYPE.PT_id="+DomDataType[i];
                try{
                rs=query.select(upit4);                
                  rs.next();              
                  TipPodatka=rs.getString(1);        
                  query.Close();  
                }
                catch (SQLException ev) {    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
              }  
              str=str+TipPodatka;
              if (DomDuzina[i]!=0)
                str=str+"("+DomDuzina[i]+")";
              else
              {
                if (DomenParent[i]!=0)
                {
                  Duzina=Rek.TraziDuzinu(DomenParent[i], query, IdProjekta);
                  if (Duzina!=0)
                    str=str+"("+Duzina+")";
                }
              }
              
              /*nobrenovic: start*/    
              ArrayList<CheckConstraint> domChkCons = rek.findDomChkCon(con, IdProjekta, IdASistema, DomenId[i]);  
              if (domChkCons != null && domChkCons.size() > 0)
              {
                str=str+" CHECK (";               
              if(domChkCons.size() == 1)
              {
                  if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
                      str += domChkCons.get(0).getDnf().toString(false);
                  else
                      str += domChkCons.get(0).getCnf().toString(false);
              }
              else if(domChkCons.size() >= 1)
              {
                  for(int l=0;l<domChkCons.size();l++)
                  {   
                      if(l>0) str += " AND ";
                      if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
                          str += "(" + domChkCons.get(l).getDnf().toString(false) + ")";
                      else
                          str += "(" + domChkCons.get(l).getCnf().toString(false) + ")";
                  }
              }
                str= str + ")";                
              }
              
              str = str+";";                
              /*nobrenovic: stop*/
              out.println(str);  
              out.println();
             }else if (DomenType[i]==2)
             {
               String ListaAtributa = "";
               int b;
               int[] AtrId=null;
               String AtributName=null;
               b=Pom.brojanjeNtorki("IISC_DOM_ATT","PR_id="+IdProjekta + " and Dom_id="+DomenId[i],con);
               String upitDom = "select Att_id from IISC_DOM_ATT where IISC_DOM_ATT.Dom_id="+DomenId[i] + " and IISC_DOM_ATT.PR_id=" + IdProjekta +" ORDER BY Att_rbr";
               AtrId=query.selectArraySAint(upitDom,b,1);
               
               for (int a=0;a<AtrId.length;a++)
               {
                 String upitAtr = "select Att_mnem from IISC_ATTRIBUTE where IISC_ATTRIBUTE.Att_id="+ AtrId[a]+ " and IISC_ATTRIBUTE.PR_id=" + IdProjekta;
                 try{
                  rs=query.select(upitAtr);                
                  rs.next();              
                  AtributName= rs.getString(1);       
                  query.Close();  
                 }
                 catch (SQLException ev) {    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 }                 
                 ListaAtributa = ListaAtributa + AtributName + " "+ Rek.AtributiDomena(AtrId[a],query, Rek, IdProjekta);
                 if (a<AtrId.length-1)
                 {
                   ListaAtributa = ListaAtributa+", ";
                 }
               }
               str = "CREATE TYPE " + DomMnem[i] + " FINAL";
               out.println(str);
               str = "REF FROM ";
               out.println(str);               
               out.println(ListaAtributa);
               out.println();
             }else if (DomenType[i]==4)
             {
               String DomenName="";
               if (DomenParent[i]!=0)
               {
               String upitDomName = "select Dom_mnem from IISC_DOMAIN where IISC_DOMAIN.PR_id="+IdProjekta + " and IISC_DOMAIN.Dom_id=" + DomenParent[i];               
               try{
                  rs=query.select(upitDomName);                
                  rs.next();              
                  DomenName= rs.getString(1);       
                  query.Close();  
                 }
                 catch (SQLException ev) {    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 }  
                }  else
                {
                  String upitTip = "select PT_mnemonic from IISC_PRIMITIVE_TYPE where IISC_PRIMITIVE_TYPE.PT_id="+DomDataType[i];
                  try{
                  rs=query.select(upitTip);
                  rs.next();              
                  DomenName=rs.getString(1);        
                  query.Close();
                  }
                  catch (SQLException ev) {    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 }  
                }
               str = "CREATE TYPE " + DomMnem[i] + " FINAL";
               out.println(str);
               str = "REF USING " + DomenName;
               out.println(str);
               out.println();
             }             
           }    
           out.println();
           
           for (int i=0;i<BrRelacija;i++)
           {               
              str = "CREATE TABLE "+Tabele[i].getImeTabele()+" (";
              out.println(str);
              for (int j=0; j<Tabele[i].getBrAtributa();j++)
              {
                str = Tabele[i].getImeAtributa(j) +" "+Tabele[i].getTip(j);
                out.print(str);
                JDBCQuery query2=new JDBCQuery(con);
                ResultSet rs2;
                int SemaRelId = NadjiIdSemeRelacije(Tabele[i].getImeTabele().trim());
                int AttId =NadjiIdAtributa(Tabele[i].getImeAtributa(j).trim());
                String upitS = "select * from IISC_SEQUENCE where PR_id="+IdProjekta+ " and AS_id="+IdASistema + " and RS_id="+ SemaRelId+ " and Att_id="+AttId;                
                try{
                 rs2=query2.select(upitS);
                 while(rs2.next())
                 {                      
                  out.println();
                  str="GENERATED ALWAYS AS IDENTITY ";
                  out.println(str);
                  str = "(INCREMENT BY " + rs2.getInt("Seq_increment");
                  out.println(str);
                                   
                  if (rs2.getInt("Seq_maxvalue")!=0)
                  {
                    str = "MAXVALUE " + rs2.getInt("Seq_maxvalue");
                    out.println(str);
                  }else
                  {
                    str = "NOMAXVALUE";
                    out.println(str);
                  }
                  /*
                  if (rs2.getInt("Seq_start")!=0)
                  {
                    str = "MINVALUE " + rs2.getInt("Seq_start");
                    out.println(str);
                  }else
                  {
                    str = "NOMINVALUE";
                    out.println(str);
                  }
                  */
                  if(rs2.getInt("Seq_cycle")==0)
                  {
                    str = "NOCYCLE)";
                    out.print(str);
                  }else
                  {
                    str = "CYCLE)";
                    out.print(str);
                  }                
                                       
              }
              query2.Close();
            }
              catch(Exception ex){     
              ex.printStackTrace();
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
                      for(int l=0;l<chkCons.size();l++)
                      {   
                          if(l>0) chkCon += " AND ";
                          if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
                              chkCon += "(" + chkCons.get(l).getDnf().toString(false) + ")";
                          else
                              chkCon += "(" + chkCons.get(l).getCnf().toString(false) + ")";
                      }
                  }
                  chkCon.replaceAll("VALUE",Tabele[i].getImeAtributa(j));
                  out.print(" CHECK " + chkCon);
              }
              /*nobrenovic: stop*/
              /*  
                if (Tabele[i].getIzrazi(j).length() != 0)
                {                  
                  str = "\r\n"+"CHECK ("+Tabele[i].getIzrazi(j)+")";
                  out.println(str+",");
                }else
                {                  
                  out.println(",");
                }       
               */   
               if (j<(Tabele[i].getBrAtributa()-1))
                {
                  out.println(",");
                } 
              } //end for j
              str = "\r\n"+")";              
              out.println(str);
              out.println();
              /*
              if (IndeksiP)
              {
                if (Tabele[i].getPKljuc().length()!=0)
                {
                  str = "CREATE INDEX PK_"+Tabele[i].getImeTabele();
                  out.println(str);                                       
                  str = "   ON "+Tabele[i].getImeTabele();
                  out.println(str);
                  str="   (";
                  out.println(str); 
                  str = "       "+Tabele[i].getPKljuc();
                  out.println(str); 
                  str = "   )";
                  out.print(str);                
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
                      str = "CREATE INDEX UK_"+Tabele[i].getImeTabele();
                      out.println(str);                                       
                      str = "   ON "+Tabele[i].getImeTabele();
                      out.println(str);
                      str="   (";
                      out.println(str);
                      str = "       "+Tabele[i].getUnique(x);
                      out.println(str);
                      str= "    )";
                      out.print(str);                      
                    }                  
                  }                  
                } 
              } //indeksiU 
              */
           } //end for i  
  }
  void DatotekaConstraints(PrintWriter out, int BrRelacija)
  {
    String str = "";
    String alt = "";
    for (int i=0;i<BrRelacija;i++)
    {
     
        alt = "";        
        str = "";
        for (int m=0;m<Tabele[i].Defaultovi.length;m++)
        {
          if (Tabele[i].getDefault(m).length()!=0)
          {
             if (alt == "")
             {
                alt = "ALTER TABLE "+Tabele[i].getImeTabele()+" ADD";
                out.println(alt);
             }
             
             str = "   CONSTRAINT DF_"+Tabele[i].getImeTabele()+"_"+Tabele[i].getImeAtributa(m)+" DEFAULT ("+Tabele[i].getDefault(m)+") FOR "+Tabele[i].getImeAtributa(m);
             
             if (m<(Tabele[i].Defaultovi.length-1))
              {
                out.println(str+",");                     
              }else
              {
                out.println(str);
              }
          }   
        }
        if (Tabele[i].getPKljuc().length()!=0)
        {
          if (alt == "")
             {
                alt = "ALTER TABLE "+Tabele[i].getImeTabele()+" ADD";
                out.println(alt);
             }                  
          str = "   CONSTRAINT PK_"+Tabele[i].getImeTabele()+" PRIMARY KEY ";
          out.println(str);
          str="   (";
          out.println(str); 
          str = "       "+Tabele[i].getPKljuc();
          out.println(str); 
          str = "   )";
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
                alt = "ALTER TABLE "+Tabele[i].getImeTabele()+" ADD";
                out.println(alt);
             }                    
            str="     CONSTRAINT AK_"+Tabele[i].getImeTabele()+"_"+x+" UNIQUE";
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
           if (alt.equalsIgnoreCase(""))
           {
              alt = "ALTER TABLE [dbo].["+Tabele[i].getImeTabele()+"] ADD";
              out.println(alt);           
           }
           else
           {
               out.println(",");
           }
          String conStr = "";
          if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
            conStr = chkCon.getDnf().toString(false);
          else 
            conStr = chkCon.getCnf().toString(false);
          str = "    CONSTRAINT "+ chkCon.getName() +" CHECK ("+ conStr +")";
          out.println(str);                 
        }
      }
      /*nobrenovic: stop*/
      str = "\r\n";
      out.println(str);
      out.println();
    } 
    
      /*nobrenovic: start*/
      //generate ASSERT statements for multi-table check constraints
      for (int i=0; i<chkCons.size();i++)
      {
        CheckConstraint chkCon = chkCons.get(i);
        if (chkCon.getRefRelSch().size() > 1)
        {
           final String asrtName = "ASSERT_"+ chkCon.getName();
           
           out.println("CREATE ASSERTION "+ asrtName + " CHECK(");
               
           
           out.print("  (SELECT count(*) FROM ");    
           for (int j=0;j<chkCon.getRefRelSch().size();j++)
           {
               out.print(chkCon.getRefRelSch().get(j));
                if(j<chkCon.getRefRelSch().size()-1)
                    out.print(",");
           } 
           
           out.println();
           out.println("    WHERE "+ CheckConGenHelper.GenerateJoinWhereClause(con,IdProjekta, IdASistema,chkCon.getRefRelSchIds()));
           
            String conStr = "";
            if(chkConImplType == CheckConstraint.ImplemetationType.DNF)
               conStr = chkCon.getDnf().toString(true);
            else 
              conStr = chkCon.getCnf().toString(true);
              
           out.println("    AND NOT("+ conStr + ")) = 0)");
           
           out.println();                 
        }
      }
      /*nobrenovic: stop*/
  }  
  void DatotekaDDl(PrintWriter out, String Verzija)
  {
    String str = "";
    str = Verzija;
    out.println(str);
    DateFormat df = DateFormat.getDateTimeInstance();
    String s = df.format(d); 
    str = "/*Created on:  " + s + " */";
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
  void DatotkaIndeksi(PrintWriter out, int BrRelacija)
  {
     String str;
     str="";         
     for (int i=0;i<BrRelacija;i++)
      {        
              //indeksi
              if (IndeksiP)
              {
                if (Tabele[i].getPKljuc().length()!=0)
                {
                  str = "CREATE INDEX PK_"+Tabele[i].getImeTabele();
                  out.println(str);                                       
                  str = "   ON "+Tabele[i].getImeTabele();
                  out.println(str);
                  str="   (";
                  out.println(str); 
                  str = "       "+Tabele[i].getPKljuc();
                  out.println(str); 
                  str = "   )";
                  out.print(str);                
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
                      str = "CREATE INDEX UK_"+Tabele[i].getImeTabele();
                      out.println(str);                                       
                      str = "   ON "+Tabele[i].getImeTabele();
                      out.println(str);
                      str="   (";
                      out.println(str);
                      str = "       "+Tabele[i].getUnique(x);
                      out.println(str);
                      str= "    )";
                      out.print(str);                      
                    }                  
                  }                  
                } 
              } //indeksiU   
           } //end for i  
  }
  void ConstraintForeignKey(int RSCRelIntType, PrintWriter out, PrintWriter outInd, String SemaRelNameL, String RSCName, String[] AtributNameL,String SemaRelNameR, String[] AtributNameR,int ModActionR, int DelActionR, int m, int n, int Initially, int Deferrable)
  {
    String str = "";
    String alt = "";
    alt = "ALTER TABLE "+SemaRelNameL.replaceAll(" ","_")+" ADD";
                  out.println(alt);                                 
                  str = "   CONSTRAINT "+RSCName.replaceAll(" ","_")+" FOREIGN KEY";
                  out.println(str);
                  str="   (";
                  out.println(str);
                  str = "";
                  for (int u=0;u<m;u++)
                  {
                    str = str +  AtributNameL[u].replaceAll(" ","_");
                    if (u<m-1)
                    str = str + ",";
                  }
                  out.println("       "+str);  
                  str = "   ) REFERENCES "+SemaRelNameR.replaceAll(" ","_");                  
                  out.println(str);
                  str="   (";
                  out.println(str);
                  str = "";
                  for (int u=0;u<n;u++)
                  {
                    str = str +  AtributNameR[u].replaceAll(" ","_");
                    if (u<n-1)
                    str = str + ",";
                  }
                  out.println("       "+str);
                  out.println("   )");
                  if (RSCRelIntType == 1)
                  {
                    str = "MATCH PARTIAL";
                    out.println(str);
                  }
                  if (RSCRelIntType == 2)
                  {
                    str = "MATCH FULL";
                    out.println(str);
                  }
                  if (ModActionR == -4)
                  {
                    str = "ON UPDATE CASCADE";
                    out.println(str);
                  }
                  if (ModActionR == -2)
                  {
                    str = "ON UPDATE SET NULL";
                    out.println(str);
                  }
                  if (ModActionR == -3)
                  {
                    str = "ON UPDATE SET DEFAULT";
                    out.println(str);
                  }
                  if (DelActionR == -2)
                  {
                    str = "ON DELETE SET NULL";
                    out.println(str);
                  }
                  if (DelActionR == -3)
                  {
                    str = "ON DELETE SET DEFAULT";
                    out.println(str);
                  }
                  if (DelActionR == -4)
                  {
                    str = "ON DELETE CASCADE";
                    out.println(str);
                  }    
                  if (Deferrable==1 && Initially==0)
                  {
                    out.println("INITIALLY IMMEDIATE DEFERRABLE");
                  }else if (Deferrable==1 && Initially==1)
                  {
                    out.println("INITIALLY DEFERRED DEFERRABLE");
                  }
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
                    str = "CREATE INDEX FK_"+SemaRelNameL.replaceAll(" ","_");
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
  void FormirajSkript()
  {
    ResultSet rs;          
    JDBCQuery query=new JDBCQuery(con);
    Rekurzije Rek=new Rekurzije();
    int BrRelacija=PodaciZaTabele(con,query,Rek);
    
    PrintWriter Datoteka;
    PrintWriter out=null;
    PrintWriter outSql=null;
    PrintWriter outTable=null; 
    PrintWriter outInd=null;
    PrintWriter outConstr=null;    
    String Verzija;
    Verzija = "/* DBMS name:      ANSI SQL/3 */";
    String ImeBaze;    //bira korisnik
    ImeBaze=null; 
          
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
              /*
              if (Triger==true)
              {
                File outputFileTrigeri = new File(PrefixNaziva+"_Trg.sql");
                outTrigeri = new PrintWriter(new BufferedWriter(new FileWriter(outputFileTrigeri)));
              
              }*/
           }            
    }
    catch (IOException ex) {
            System.err.println("Cannot open file.");
            System.err.println(ex);
    }          
            
          if (OneFileOnly == true)
         {
           Datoteka = out;
         }else
         {
           Datoteka = outTable;
           DatotekaDDl(outSql,Verzija);
         }
         DatotkaTabele(ImeBaze, Datoteka, BrRelacija);
         if (IndeksiP || IndeksiF || IndeksiU) 
    {
                  if (OneFileOnly == true)
                    {
                        Datoteka = out;
                    }else
                    {
                        Datoteka = outInd;
                    }    
                    DatotkaIndeksi(Datoteka, BrRelacija);
    }
         if (Ogranicenja==true)
         {
            if (OneFileOnly == true)
            {
              DatotekaConstraints(out, BrRelacija);
            }else
            {
              DatotekaConstraints(outConstr, BrRelacija);
            }                 
         } 
    try {     
         if (Ogranicenja && Constr != null)
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
                 }
            if (RSCType == 0 || RSCType == 2)  
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
                        ConstraintForeignKey(RSCRelIntType,Datoteka,outInd,SemaRelNameL[0],RSCName,AtributNameL,SemaRelNameR[0],AtributNameR,ModActionR,DelActionR,m,n, Initially,Deferrable);
                    }                 
                 }
            } else// rs.next                  
              {
                  query.Close();
              }
            }
         }
           
              /*
              if (Tabele[i].getPKljuc().length()!=0)
              {
                str="PRIMARY KEY ("+Tabele[i].getPKljuc()+")";
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
                    str="UNIQUE ("+Tabele[i].getUnique(x)+")";
                    if (x<(Tabele[i].Uniqueovi.length-1))
                    {
                      out.println(str+",");                     
                    } else
                    {
                      out.println(str);
                    }               
                  }
                }
              }
              str = ");";
              out.println(str);
              out.println();
            */     
           
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
              /*
              if (Triger)
              {
                outTrigeri.close();
              }  
              */
            }             
                          
    }
         
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }    
  }
}