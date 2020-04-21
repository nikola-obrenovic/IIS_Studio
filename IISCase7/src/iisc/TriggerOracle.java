package iisc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import javax.swing.JOptionPane;

public class TriggerOracle 
{
  private int SadrzavanjePL=0;
  private int SadrzavanjeFL=0;
  private int SadrzavanjeDL=0; 
  private int SadrzavanjePR=0;
  private int SadrzavanjeFDR=0;
  private int Sadrzavanje=0;
  private int SadrzavanjeIRI=0;
  private int UpdateTabelaFR=0;
  private boolean DefaultProcedura=false;
  private ClassFunction Pom = new ClassFunction();
  private int KaskadnoP=0;
  private int KaskadnoFD=0;
  private int KaskadnoUpFD=0;
  private int KaskadnoUpP=0;
  private int KaskadnoUpF=0;
  private int SetNullRP=0;  
  private int SetNullRFD=0;  
  private int SetDefaultP=0;
  private int SetDefaultFD=0;
  private int BezAkcijeD=0;  
  private int BezAkcijeU=0;
  private int IzvrsenjeOkidaca=0;
  private int KaskadnoIRI=0;  
  private int IzvrsiOkidac=0;
  private String ImeBaze;
  private boolean Paket = false;
  private String PackageName = "Global_PCK.";
  //private String PackageNameConst;
  
  public TriggerOracle(String NameBase)
  {
    ImeBaze = NameBase;
    
  }
  
  public void UpdatePKey(PrintWriter out, String ImeTabele, String[] ModifableAtr)
  {
    String str="";
    for (int i=0;i<ModifableAtr.length;i++)
    {
        str = str +  ModifableAtr[i];        
        if (i<ModifableAtr.length-1)
        {
           str = str + ", ";  
        }
    } 
    out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ ImeTabele.replaceAll(" ","_")+"_PKey_UPD");    
    out.println("BEFORE UPDATE OF " + str);
    out.println("ON "+ ImeTabele.replaceAll(" ","_"));        
    out.println("FOR EACH ROW");     
    out.println("BEGIN");
    out.println("RAISE_APPLICATION_ERROR(-20999,'Izmena primarnog kljuca nije dozvoljena');");         
    out.println("END;");    
    out.println("/");
    out.println();    
  }
  
   public void SetSequence(PrintWriter out, String ImeTabele, String ImeSeq, String ImeAtr)
  {    
    out.println("CREATE OR REPLACE TRIGGER " + "TRG_"+ImeSeq);    
    out.println("BEFORE INSERT ");
    out.println("ON "+ ImeTabele.replaceAll(" ","_"));
    out.println("FOR EACH ROW");
    out.println("DECLARE");
    out.println("integrity_error  exception;");    
    out.println("BEGIN");
    out.println("SELECT " + ImeSeq+".NEXTVAL INTO :new."+ImeAtr +"FROM DUAL;");
    out.println("exception");
    out.println("when integrity_error then");
    out.println("RAISE_APPLICATION_ERROR(-20997,'Torka u ne moze da se upise u relaciju "+ImeTabele.replaceAll(" ","_")+"');");
    out.println("END;");    
    out.println("/");
    out.println();  
  }
  
  void DefaultVrednost(PrintWriter GlobalOutZ,PrintWriter out, String ImeTabele, String[] Obelezje)
  {
    String str="";
    str = "PROCEDURE "+"DefVrednost_"+ ImeTabele.replaceAll(" ","_") + "(v_default OUT " + ImeTabele.replaceAll(" ","_") + "%ROWTYPE)";    
    GlobalOutZ.println("PROCEDURE "+"DefVrednost_"+ ImeTabele.replaceAll(" ","_") + "(v_default OUT " + ImeTabele.replaceAll(" ","_") + "%ROWTYPE);");
    out.println(str); 
    out.println("IS");    
    out.println("BEGIN");    
    for (int i=0;i<Obelezje.length;i++)
    {
      out.println("SELECT user_tab_columns.data_default INTO v_default."+ Obelezje[i].replaceAll(" ","_")); 
      out.println(" FROM user_tab_columns ");
      str = "WHERE user_tab_columns.table_name = '" + ImeTabele.replaceAll(" ","_").toUpperCase() + "' AND ";
      out.println(str);
      out.println("user_tab_columns.column_name = '" + Obelezje[i].replaceAll(" ","_") + "';");
    }
    out.println("END;");
    //out.println("/");
    out.println();
  }
  public void SadrzavanjePRI(PrintWriter GlobalOutZ,PrintWriter out, String ImeTabeleL, String ImeTabeleR,String[] AtributNameL, String[] AtributNameR)
  {
    String str = "";    
    str = "FUNCTION "+"SadrzavanjePRI_"+ ImeTabeleL.replaceAll(" ","_") + "(u IN " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE)";    
    GlobalOutZ.println("FUNCTION "+"SadrzavanjePRI_"+ ImeTabeleL.replaceAll(" ","_") + "(u IN " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE) RETURN BOOLEAN;"); 
    out.println(str); 
    out.println("RETURN BOOLEAN IS");
    out.println("   I NUMBER;");    
    out.println("BEGIN");    
    out.println("SELECT COUNT(*) INTO I FROM "+ ImeTabeleR.replaceAll(" ","_")+" v");
    str = "WHERE ";
    out.println(str);
    for (int i=0;i<AtributNameL.length;i++)
    {
      str = "(u." + AtributNameL[i].replaceAll(" ","_") + " IS NULL OR " + "v." + AtributNameR[i].replaceAll(" ","_") + " = " + "u." + AtributNameL[i].replaceAll(" ","_") + ")";
      out.print(str);
      if (i<AtributNameL.length-1)
        {
           out.println(" AND ");  
        }       
    }
    out.println(";");
    //out.println();
    out.println("IF I <> 0 THEN");
    out.println("RETURN TRUE;");
    out.println("ELSE");
    out.println("RETURN FALSE;");    
    out.println("END IF;");     
    out.println("END;"); 
    //out.println("/");
    out.println();
  } 
  void SadrzavanjeDRI(PrintWriter GlobalOutZ,PrintWriter out, String ImeTabeleL, String ImeTabeleR, String[] AtributNameL, String[] AtributNameR)
  {
    String str = "";
    str = "FUNCTION "+ "SadrzavanjeDRI_"+ ImeTabeleL.replaceAll(" ","_");
    GlobalOutZ.println("FUNCTION "+ "SadrzavanjeDRI_"+ ImeTabeleL.replaceAll(" ","_"));
    out.println(str); 
    str = "(u IN " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE)"; 
    GlobalOutZ.println(str+" RETURN BOOLEAN;");
    out.println(str); 
    out.println("RETURN BOOLEAN ");
    out.println("IS"); 
    out.println("   I NUMBER;"); 
    out.println("   Ret BOOLEAN;"); 
    out.println("BEGIN");        
    out.println("Ret:=FALSE;");
    out.print("IF ");
    for (int i=0;i<AtributNameL.length;i++)
    {
      str = "(u." + AtributNameL[i].replaceAll(" ","_") + " IS NULL)";
      out.print(str);
      if (i<AtributNameL.length-1)
        {
           out.println(" OR ");  
        }
    }
    out.println(" THEN ");
    out.println("Ret:=TRUE;");
    out.println("ELSE");      
    out.println("SELECT COUNT(*) INTO I FROM "+ ImeTabeleR.replaceAll(" ","_")+" v");
    str = "WHERE ";
    out.print(str);
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = "(v." + AtributNameR[i].replaceAll(" ","_") + " = " + "u." + AtributNameL[i].replaceAll(" ","_") + ")";
      out.print(str);
      if (i<AtributNameR.length-1)
        {
           out.println(" AND ");  
        }
      //out.println(str); 
    }
    out.println(";");
    //out.println();
    out.println("IF I <> 0 THEN ");
    out.println("Ret:=TRUE;");    
    out.println("END IF;");
    out.println("END IF;");
    out.println("RETURN Ret;");
    out.println("END;");    
    //out.println("/");
    out.println();
  }
  void SadrzavanjeFRI(PrintWriter GlobalOutZ,PrintWriter out, String ImeTabeleL, String ImeTabeleR, String[] AtributNameL, String[] AtributNameR)
  {
    String str = "";
    str = "FUNCTION "+ "SadrzavanjeFRI_"+ ImeTabeleL.replaceAll(" ","_") + "(u IN " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE)";    
    GlobalOutZ.println("FUNCTION "+ "SadrzavanjeFRI_"+ ImeTabeleL.replaceAll(" ","_") + "(u IN " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE) RETURN BOOLEAN;");
    out.println(str); 
    out.println("RETURN BOOLEAN IS");    
    out.println("   I NUMBER;");
    out.println("   Ret BOOLEAN;");
    out.println("BEGIN");        
    out.println("Ret:=FALSE;");
    out.print("IF ");        
    for (int i=0;i<AtributNameL.length;i++)
    {
      str = "(u." + AtributNameL[i].replaceAll(" ","_") + " IS NULL)";
      out.print(str);
      if (i<AtributNameL.length-1)
        {
           out.println(" AND ");  
        }
    }
    out.println(" THEN");
    out.println("Ret:=TRUE;");
    out.println("ELSE");      
    out.println("SELECT COUNT(*) INTO I FROM "+ImeTabeleR.replaceAll(" ","_")+" v");
    str = "WHERE ";
    out.print(str);    
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = "(v." + AtributNameR[i].replaceAll(" ","_") + " = " + "u." + AtributNameL[i].replaceAll(" ","_") + ")";
      out.print(str);
      if (i<AtributNameR.length-1)
        {
           out.println(" AND ");  
        }
      //out.println(str); 
    }
    out.println(";");
    //out.println();
    out.println("IF I <> 0 THEN ");
    out.println("Ret:=TRUE;");    
    out.println("END IF;");
    out.println("END IF;");
    out.println("RETURN Ret;");
    out.println("END;");    
    //out.println("/");
    out.println();
  }
  public void RSadrzavanjeRI(PrintWriter GlobalOutZ, PrintWriter out, String ImeTabeleL, String ImeTabeleR,String[] AtributNameL, String[] AtributNameR,int RSCRelIntType)
  {
    String str = "";    
    if (RSCRelIntType == 1)
    {
      str = "FUNCTION "+ "SadrzavanjePRI_"+ ImeTabeleR.replaceAll(" ","_");
      out.println(str);
      GlobalOutZ.println("FUNCTION "+"SadrzavanjePRI_"+ ImeTabeleR.replaceAll(" ","_"));
      str ="(v IN " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE)";  
      GlobalOutZ.println(str+" RETURN BOOLEAN;");
    }else
    {
      str = "FUNCTION "+ "SadrzavanjeFDRI_"+ ImeTabeleR.replaceAll(" ","_");
      out.println(str); 
      GlobalOutZ.println(str);
      str = "(v IN " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE)"; 
      GlobalOutZ.println(str+" RETURN BOOLEAN;");
    }
    out.println(str); 
    out.println("RETURN BOOLEAN ");
    out.println("IS"); 
    out.println("   I NUMBER;");    
    out.println("BEGIN");    
    out.println("SELECT COUNT(*) INTO I FROM "+ ImeTabeleL.replaceAll(" ","_")+" u");
    str = "WHERE ";
    out.println(str);
    if (RSCRelIntType == 1)
    {
      for (int i=0;i<AtributNameL.length;i++)
      {
        str = "(u." + AtributNameL[i].replaceAll(" ","_") + " IS NULL OR " + "u." + AtributNameL[i].replaceAll(" ","_") + " = " + "v." + AtributNameR[i].replaceAll(" ","_") + ")";
        out.print(str);
        if (i<AtributNameL.length-1)
        {
           out.println(" AND ");  
        }       
      }
    }else
    {
      for (int i=0;i<AtributNameL.length;i++)
      {
        str = "(u." + AtributNameL[i].replaceAll(" ","_") + " = " + "v." + AtributNameR[i].replaceAll(" ","_") + ")";
        out.print(str);
        if (i<AtributNameL.length-1)
        {
           out.println(" AND ");  
        }       
      }
    }
    out.println(";");    
    out.println("IF I <> 0 THEN");
    out.println("RETURN TRUE;");
    out.println("ELSE");
    out.println("RETURN FALSE;");    
    out.println("END IF;");    
    out.println("END;");
    //out.println("/");
    out.println();
  }
  public void UpisOsnovniRIL(PrintWriter out,PrintWriter GlobalOutZ,PrintWriter GlobalOutB, PrintWriter outWorning, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaL, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int InsActionL, ClassFunction pom,int IdASistema, int IdSemaRel, Connection con)
  {   
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL;
    ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeAR;
    ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);
    
    if (RSCRelIntType == 1 && SadrzavanjePL == 0)
    {     
      SadrzavanjePRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR); 
      SadrzavanjePL = 1;
    }else if (RSCRelIntType == 0 && SadrzavanjeDL == 0)
    {
      SadrzavanjeDRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR); 
      SadrzavanjeDL = 1;
    } else if (RSCRelIntType == 2 && SadrzavanjeFL ==0)
      {
        SadrzavanjeFRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR,ImeAL, ImeAR);
        SadrzavanjeFL = 1;
      } 
    if (DefaultProcedura==false && InsActionL==-3)
    {
      DefaultVrednost(GlobalOutZ, GlobalOutB, ImeTabeleL, ImeAL);
      DefaultProcedura=true;
    }
    String str="";
    str = ImeBaze + "TRG_"+ ImeOgranicenja+"_L"+"_I";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    out.println("CREATE OR REPLACE TRIGGER "+"TRG_"+ ImeOgranicenja+"LI");    
    out.println("   BEFORE INSERT ON "+  ImeTabeleL.replaceAll(" ","_"));    
    out.println("   FOR EACH ROW");      
    out.println("   DECLARE ");
    if (InsActionL == -1 || InsActionL == -3)
    { 
    out.println("     exc EXCEPTION;");
    }
    out.println("     u " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;");
    out.println("     v_default " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;");
    out.println("BEGIN");
    for (int i=0;i<ImeAL.length;i++)
    {
      out.println("   u." + ImeAL[i].replaceAll(" ","_") + " := :NEW." + ImeAL[i].replaceAll(" ","_") + ";");
    }
     
    if (RSCRelIntType == 1)
    {
      out.println("IF NOT "+ PackageName+"SadrzavanjePRI_"+ImeTabeleL.replaceAll(" ","_")+"(u) THEN");
    } else if (RSCRelIntType == 2)
    {
      out.println("IF NOT "+ PackageName+"SadrzavanjeFRI_"+ImeTabeleL.replaceAll(" ","_")+"(u) THEN");
    }else
    {
      out.println("IF NOT "+  PackageName+"SadrzavanjeDRI_"+ImeTabeleL.replaceAll(" ","_")+"(u) THEN");
    }
    
    if (InsActionL == -1)
    {  
      out.println("   RAISE exc;");
      out.println("END IF;");
      out.println("EXCEPTION");
      out.println(" WHEN  exc THEN");
      out.println("RAISE_APPLICATION_ERROR(-20998,'Torka u ne moze da se upise u relaciju "+ImeTabeleL.replaceAll(" ","_")+"');");            
    }else
    if (InsActionL == -3)
    {      
      
    str = PackageName+"DefVrednost_"+ ImeTabeleL.replaceAll(" ","_") + "(v_default);";
    out.println(str);  
    if (RSCRelIntType == 1)
    {
        out.println("IF "+PackageName+"SadrzavanjePRI_"+ImeTabeleL.replaceAll(" ","_")+"(v_default)");
    }else if (RSCRelIntType == 2)
    {
        out.println("IF "+ PackageName+"SadrzavanjeFRI_"+ImeTabeleL.replaceAll(" ","_")+"(v_default)");
    }else
    {
      out.println("IF "+ PackageName+"SadrzavanjeDRI_"+ImeTabeleL.replaceAll(" ","_")+"(v_default)");
    }    
    out.println("THEN");
    for (int i=0;i<ImeAL.length;i++)
    {
        str = ":NEW." + ImeAL[i].replaceAll(" ","_") + " := v_default."+ ImeAL[i].replaceAll(" ","_") +";";                
        out.println(str);
    } 
    out.println("ELSE");
    out.println("   RAISE exc;");
    out.println("END IF;");
    out.println("END IF;");
    out.println("EXCEPTION");
    out.println(" WHEN  exc THEN");
    out.println("RAISE_APPLICATION_ERROR(-20998,'Torka u ne moze da se upise u relaciju "+ImeTabeleL+"');");
    
    }else
    if (InsActionL == -2)
    {
      String worning="Warning! Atributi ";
      String worningAtr="";
      String[] NotNullAtr = Pom.NotNullObelezja(query, IdProjekta, IdSemaRel, IdASistema, con, IdAtributaL, ImeAL);
      if (NotNullAtr != null && NotNullAtr.length != 0)
      {
        for (int k=0;k<NotNullAtr.length;k++)
        {
          worningAtr = worningAtr + NotNullAtr[k]; 
          if (k<NotNullAtr.length-1)
          {
           worningAtr = worningAtr + ",";
          }          
        }
        worning = worning + worningAtr + " su not null.";
        outWorning.println(worning);
    }
    for (int i=0;i<ImeAL.length;i++)
    {
        str = ":NEW." + ImeAL[i].replaceAll(" ","_") + " := null;";                
        out.println(str);
    } 
     out.println("END IF;"); 
    }    //SET NULL
    //out.println("END IF;");
    out.println("END;");  
    out.println("/");
    out.println();   
  }  
  public void UpdateOsnovniRIL(PrintWriter out, PrintWriter GlobalOutZ,PrintWriter GlobalOutB,PrintWriter outWorning, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaL, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int ModActionL, ClassFunction pom,int IdASistema, int IdSemaRel, Connection con)
  {   
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL;
    ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeAR;
    ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);
            
    if (RSCRelIntType == 1 && SadrzavanjePL == 0)
    {     
      SadrzavanjePRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR); 
      SadrzavanjePL = 1;
    }else if (RSCRelIntType == 0 && SadrzavanjeDL == 0)
    {
      SadrzavanjeDRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR); 
      SadrzavanjeDL = 1;
    } else if (RSCRelIntType == 2 && SadrzavanjeFL == 0)
    {
        SadrzavanjeFRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR,ImeAL, ImeAR);
        SadrzavanjeFL = 1;
    } 
    if (DefaultProcedura==false && ModActionL==-3)
    {
      DefaultVrednost(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeAL);
      DefaultProcedura=true;
    }
    String str="";
    str = ImeBaze + "TRG_"+ ImeOgranicenja+"_L"+"_UPD";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    out.println("CREATE OR REPLACE TRIGGER " +  "TRG_"+ ImeOgranicenja+"L"+"U");    
    out.print("   BEFORE UPDATE OF ");
    for (int j=0;j<ImeAL.length;j++)
    {
        str = ImeAL[j].replaceAll(" ","_");
        if (j<ImeAL.length-1)
        {
          str = str + ", ";
        }
        out.print(str);
    } 
    out.println(" ON "+ ImeTabeleL.replaceAll(" ","_"));    
    out.println("   FOR EACH ROW");  
    out.print("   WHEN (");
    for (int j=0;j<ImeAL.length;j++)
    {
        str = "NEW." + ImeAL[j].replaceAll(" ","_") + " <> OLD." + ImeAL[j].replaceAll(" ","_");
        if (j<ImeAL.length-1)
        {
          str = str + " OR ";
        }
        out.println(str);
    } 
    out.println("   )");
    out.println("   DECLARE ");
    if (ModActionL == -1 || ModActionL == -3)
    { 
    out.println("     exc EXCEPTION;");
    }
    out.println("     u " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;");
    out.println("     v_default " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;");
    out.println("BEGIN");
    for (int i=0;i<ImeAL.length;i++)
    {
      out.println("   u." + ImeAL[i].replaceAll(" ","_") + " := :NEW." + ImeAL[i].replaceAll(" ","_") + ";");
    }
       
    if (RSCRelIntType == 1)
    {            
      out.print("IF NOT "+PackageName+ "SadrzavanjePRI_"+ImeTabeleL.replaceAll(" ","_")+"(u)");
    } else if (RSCRelIntType == 2)
    {
      out.print("IF NOT "+ PackageName+"SadrzavanjeFRI_"+ImeTabeleL.replaceAll(" ","_")+"(u)");
    }else
    {
      out.print("IF NOT "+ PackageName+"SadrzavanjeDRI_"+ImeTabeleL.replaceAll(" ","_")+"(u)");
    }
    out.println(" THEN");
    if (ModActionL == -1)
    {  
      out.println("   RAISE exc;");
      out.println("END IF;");
      out.println("EXCEPTION");
      out.println(" WHEN  exc THEN");
      out.println("RAISE_APPLICATION_ERROR(-20998,'Torka u ne moze da se modifikuje u relaciji "+ImeTabeleL.replaceAll(" ","_")+"');");            
    }else
    if (ModActionL == -3)
    {      
    str = PackageName+"DefVrednost_"+ ImeTabeleL.replaceAll(" ","_") + "(v_default);";
    out.println(str);   
    if (RSCRelIntType == 1)
    {
        out.println("IF "+ PackageName+"SadrzavanjePRI_"+ImeTabeleL.replaceAll(" ","_")+"(v_default)");
    }else if (RSCRelIntType == 2)
    {
        out.println("IF "+ PackageName+"SadrzavanjeFRI_"+ImeTabeleL.replaceAll(" ","_")+"(v_default)");
    }else
    {
      out.println("IF "+PackageName+"SadrzavanjeDRI_"+ImeTabeleL.replaceAll(" ","_")+"(v_default)");
    }
    out.println("THEN");
    for (int i=0;i<ImeAL.length;i++)
    {
        str = ":NEW." + ImeAL[i].replaceAll(" ","_") + " := v_default."+ ImeAL[i].replaceAll(" ","_") +";";                
        out.println(str);
    } 
    out.println("ELSE");
    out.println("   RAISE exc;");
    out.println("END IF;");
    out.println("END IF;");
    out.println("EXCEPTION");
    out.println(" WHEN  exc THEN");
    out.println("RAISE_APPLICATION_ERROR(-20998,'Torka u ne moze da se modifikuje u relaciji "+ImeTabeleL.replaceAll(" ","_")+"');");
    
    }else
    if (ModActionL == -2)
    {
      String worning="Warning! Atributi ";
      String worningAtr="";
      String[] NotNullAtr = Pom.NotNullObelezja(query, IdProjekta, IdSemaRel, IdASistema, con, IdAtributaL, ImeAL);
      if (NotNullAtr != null && NotNullAtr.length != 0)
      {
        for (int k=0;k<NotNullAtr.length;k++)
        {
          worningAtr = worningAtr + NotNullAtr[k].replaceAll(" ","_"); 
          if (k<NotNullAtr.length-1)
          {
           worningAtr = worningAtr + ",";
          }          
        }
        worning = worning + worningAtr + " su not null.";
        outWorning.println(worning);
    }
      for (int i=0;i<ImeAL.length;i++)
      {
        str = ":NEW." + ImeAL[i].replaceAll(" ","_") + " := null;";                
        out.println(str);
      } 
      out.println("END IF;");
    }    
    //out.println("END IF;"); 
    out.println("END;"); 
    out.println("/");
    out.println();   
  }  
  void SprecRI(PrintWriter GlobalOutZ,PrintWriter out, boolean Brisanje)
  {    
    String str;
    str="PROCEDURE "+ "Sprecavanje"; 
    if (Brisanje)
    {
      str = str + "D";
    }else
    {
      str = str + "U";
    }
    out.println(str);
    GlobalOutZ.println(str+";");
    out.println("IS");
    out.println("exc EXCEPTION;");
    out.println("BEGIN");
    out.println("RAISE exc;");
    out.println("EXCEPTION");
    out.println("WHEN exc THEN");
    if (Brisanje)
    {
      out.println("RAISE_APPLICATION_ERROR (-20000,'Torka u ne moze da se brise iz posmatrane relacije!');");
    }else
    {
      out.println("RAISE_APPLICATION_ERROR (-20000,'Torka u ne moze da se modifikuje u posmatranoj relaciji!');");
    }
    out.println("END;");    
    //out.println("/");
    out.println();   
  }
  void SetNullPRI(PrintWriter GlobalOutZ,PrintWriter out, String ImeTabeleL,String[] ImeKljucL, String[] AtributNameL, String ImeTabeleR, int RSCRelIntType)
  {
    String str = "";
    str = "PROCEDURE "+ "SetNull_"+ ImeTabeleR.replaceAll(" ","_");
    out.println(str);
    GlobalOutZ.println("PROCEDURE "+ "SetNull_"+ ImeTabeleR.replaceAll(" ","_"));
    str = "(u IN " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE)";
    GlobalOutZ.println(str+";");
    out.println(str);
    out.println("IS");     
    out.println("BEGIN"); 
    out.println("UPDATE " + ImeTabeleL.replaceAll(" ","_")); 
    out.println("SET ");    
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = AtributNameL[k].replaceAll(" ","_") + " = NULL";
         if (k<AtributNameL.length-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }    
    out.println("WHERE ");
    for (int i=0;i<ImeKljucL.length;i++)
    {
          str = "(" + ImeKljucL[i].replaceAll(" ","_") + " = " + "u." + ImeKljucL[i].replaceAll(" ","_") + ")";        
          out.print(str);
          if (i<ImeKljucL.length-1)
          {
           out.println(" AND ");  
          }      
    }
    
    out.println(";");
    out.println("END;");
    //out.println("/");
    out.println();
    
  }
  void SetNullFDRI(PrintWriter GlobalOutZ, PrintWriter out, String ImeTabeleL,String[] AtributNameR, String[] AtributNameL, String ImeTabeleR)
  {
    String str = "";
    str = "PROCEDURE "+ "SetNull_"+ ImeTabeleR.replaceAll(" ","_");
    out.println(str);
    GlobalOutZ.println("PROCEDURE "+ "SetNull_"+ ImeTabeleR.replaceAll(" ","_"));
    str = "(v IN " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE)";
    out.println(str);
    GlobalOutZ.println(str+";");
    out.println("IS");     
    out.println("BEGIN"); 
    out.println("UPDATE " +  ImeTabeleL.replaceAll(" ","_") + " u"); 
    out.println("SET ");    
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = "u." + AtributNameL[k].replaceAll(" ","_") + " = NULL";
         if (k<AtributNameL.length-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }    
    out.println("WHERE ");
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = "(" + "u." + AtributNameL[k].replaceAll(" ","_") + " = " + "v." +AtributNameR[k].replaceAll(" ","_")+")";
         out.print(str);
         if (k<AtributNameL.length-1)
         {
           out.println(" AND "); 
         }         
    }       
    out.println(";");    
    out.println("END;");
    //out.println("/");
    out.println();
    
  }
  void KaskadnoPRI(PrintWriter GlobalOutZ, PrintWriter out, String ImeTabeleL,String[] PKljucL,String[] AtributNameL, String ImeTabeleR, int RSCRelIntType)
  {
    String str = "";
    
    if (RSCRelIntType == 1)
    {
      str = "PROCEDURE "+  "KaskadnoDelPRI_"+ ImeTabeleR.replaceAll(" ","_");
      
    }else
    {
      str = "PROCEDURE "+ "KaskadnoDelDFRI_"+ ImeTabeleR.replaceAll(" ","_");
      
    }
    out.println(str);
    GlobalOutZ.println(str);
    str = "(u IN " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE)";
    out.println(str);
    GlobalOutZ.println(str+";");
    out.println("IS");   
    out.println("BEGIN");   
    out.println("DELETE FROM "+ ImeTabeleL.replaceAll(" ","_"));    
    out.println("WHERE (");
    for (int k=0;k<PKljucL.length;k++)
    {
         str = PKljucL[k].replaceAll(" ","_") + " = u." + PKljucL[k].replaceAll(" ","_");
         if (k<PKljucL.length-1)
         {
           str = str + (" AND ");  
         } 
         out.println(str);
    }    
    out.println(");");
    out.println("END;");
    //out.println("/");
    out.println();
  }
  void KaskadnoDFRI(PrintWriter GlobalOutZ,PrintWriter out, String ImeTabeleL,String[] AtributNameR,String[] AtributNameL, String ImeTabeleR)
  {
    String str = "";
    str = "PROCEDURE "+  "KaskadnoDelDFRI_"+ ImeTabeleR.replaceAll(" ","_");    
    out.println(str);
    GlobalOutZ.println("PROCEDURE "+  "KaskadnoDelDFRI_"+ ImeTabeleR.replaceAll(" ","_"));
    str = "(u IN " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE)";
    out.println(str);
    GlobalOutZ.println(str+";");
    out.println("IS");   
    out.println("BEGIN");   
    out.println("DELETE FROM "+  ImeTabeleL.replaceAll(" ","_"));    
    out.println("WHERE (");
    for (int k=0;k< AtributNameL.length;k++)
    {
         str =  AtributNameL[k].replaceAll(" ","_") + " = u." +  AtributNameR[k].replaceAll(" ","_");
         if (k<AtributNameL.length-1)
         {
           str = str + (" AND ");  
         } 
         out.println(str);
    }       
    out.println(");");
    out.println("END;");
    //out.println("/");
    out.println();
  }
  
  void DefaultPRI(PrintWriter GlobalOutZ, PrintWriter out, String ImeTabeleL, String[] ImeKljucL,String[] AtributNameL, String ImeTabeleR, boolean Del)
  {
    if (DefaultProcedura==false)
    {
      DefaultVrednost(GlobalOutZ, out, ImeTabeleL, AtributNameL);
      DefaultProcedura=true;
    }
    
    String str = "";   
    str = "PROCEDURE SetDefaultPRI_"+ ImeTabeleR.replaceAll(" ","_");
    out.println(str);
    GlobalOutZ.println("PROCEDURE SetDefaultPRI_"+ ImeTabeleR.replaceAll(" ","_"));
    str = "(v IN " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE)";
    GlobalOutZ.println(str+";");
    out.println(str);
    out.println("IS");  
    out.println("u " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;");
    out.println("e " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;");
    out.println("t_Def " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;");
    out.println("BEGIN");
    out.println("DefVrednost_"+ ImeTabeleL.replaceAll(" ","_") + "(t_Def);");
    out.println("SELECT * INTO u FROM " + ImeTabeleL.replaceAll(" ","_"));
    out.println("WHERE (");
    for (int i=0;i<ImeKljucL.length;i++)
    {
          str = ImeKljucL[i].replaceAll(" ","_") + " = " + "v." + ImeKljucL[i].replaceAll(" ","_");        
          out.print(str);
          if (i<ImeKljucL.length-1)
          {
           out.println(" AND ");  
          }      
    }     
    out.println(");");
    out.print("IF (");
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = "u." + AtributNameL[k].replaceAll(" ","_") + " IS NOT NULL";
         if (k<AtributNameL.length-1)
         {
           str = str + (" AND ");  
         } 
         out.println(str);
    }
    out.println(") THEN");
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = "e."+AtributNameL[k].replaceAll(" ","_") + " := t_Def." + AtributNameL[k].replaceAll(" ","_")+";"; 
         out.println(str);
    } 
    if (AtributNameL.length!=1)
    {
    //out.print("ELSIF ");
    for (int k=0;k<AtributNameL.length;k++)
    {
         if (k<AtributNameL.length-1)
         {
           out.print("ELSIF "); 
           str = "u."+AtributNameL[k].replaceAll(" ","_") + " IS NOT NULL THEN ";
           out.println(str);
         } else
         {
           out.print("ELSE "); 
         }         
         str = "e."+ AtributNameL[k].replaceAll(" ","_") + " := t_Def." + AtributNameL[k].replaceAll(" ","_") + ";";
         out.println(str);        
    } 
    out.println("END IF; "); 
    }
    out.println("UPDATE " + ImeTabeleL.replaceAll(" ","_")); 
    out.println("SET ");    
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = AtributNameL[k].replaceAll(" ","_") + " = e." + AtributNameL[k].replaceAll(" ","_");
         if (k<AtributNameL.length-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }    
    out.println("WHERE (");
    
    //out.println(";");
    
    for (int i=0;i<ImeKljucL.length;i++)
    {
          str =  ImeKljucL[i].replaceAll(" ","_") + " = " + "v." + ImeKljucL[i].replaceAll(" ","_");        
          out.print(str);
          if (i<ImeKljucL.length-1)
          {
           out.println(" AND ");  
          }      
    }     
    out.println(");");
        
    out.println("SELECT * INTO u FROM " + ImeTabeleL.replaceAll(" ","_"));
    out.println("WHERE (");
    for (int i=0;i<ImeKljucL.length;i++)
    {
          str = ImeKljucL[i].replaceAll(" ","_") + " = " + "v." + ImeKljucL[i].replaceAll(" ","_");        
          out.print(str);
          if (i<ImeKljucL.length-1)
          {
           out.println(" AND ");  
          }      
    }  
    out.println(");");
    out.println("IF NOT SadrzavanjePRI_"+ImeTabeleL.replaceAll(" ","_")+"(u) THEN ");
    if (Del)
    {
      out.println("RAISE_APPLICATION_ERROR(-20000,'Torka u ne moze da se brise u relaciji " + ImeTabeleR.replaceAll(" ","_") +"!');");         
    }else
    {
      out.println("RAISE_APPLICATION_ERROR(-20000,'Torka u ne moze da se modifikuje u relaciji " + ImeTabeleR.replaceAll(" ","_") +"!');");         
    }
    out.println("END IF;");
    
    //out.println("END IF;");
    out.println("END;");
    //out.println("/");
    out.println();
    
  }
    
  void Default(PrintWriter GlobalOutZ,PrintWriter out, String ImeTabeleL,String[] AtributNameR, String[] AtributNameL,String ImeTabeleR, int RSCRelIntType)
  {
    if (SadrzavanjeDL == 0 && RSCRelIntType == 0)
    {
      SadrzavanjeDRI(GlobalOutZ,out, ImeTabeleL, ImeTabeleR,AtributNameL,AtributNameR);
      SadrzavanjeDL = 1;
    }
    if (RSCRelIntType == 2 && SadrzavanjeFL == 0)
    {
      SadrzavanjeFRI(GlobalOutZ,out, ImeTabeleL, ImeTabeleR,AtributNameL,AtributNameR);
      SadrzavanjeFL = 1;
    }
    String str = "";
    str = "PROCEDURE "+ "SetDefault_"+ ImeTabeleR.replaceAll(" ","_");
    out.println(str);
    GlobalOutZ.println(str);
    str = "(v IN " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE)";
    out.println(str);
    GlobalOutZ.println(str+";");
    out.println("IS");
    out.println("u " + ImeTabeleL.replaceAll(" ","_")+ "%ROWTYPE;"); 
    out.println("BEGIN");    
    out.println("UPDATE " + ImeTabeleL.replaceAll(" ","_") + " u"); 
    out.println("SET ");
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = "u."+AtributNameL[k].replaceAll(" ","_") + " = default";
         if (k<AtributNameL.length-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }    
    out.println("WHERE ");
    for (int i=0;i<AtributNameL.length;i++)
    {
          str = "(u." + AtributNameL[i].replaceAll(" ","_") + " = " + "v." + AtributNameR[i].replaceAll(" ","_") + ")";        
          out.print(str);
          if (i<AtributNameL.length-1)
          {
           out.println(" AND ");  
          }      
    }
    out.println(";");    
    if (RSCRelIntType == 2)
    {
        out.println("IF "+  "SadrzavanjeFRI_"+ImeTabeleL.replaceAll(" ","_")+"(u) THEN");
    }else
    {
        out.println("IF "+ "SadrzavanjeDRI_"+ImeTabeleL.replaceAll(" ","_")+"(u) THEN");
    }
    out.println("RAISE_APPLICATION_ERROR(-20000,'Torka u ne moze da se modifikuje u relaciji "+ImeTabeleR.replaceAll(" ","_")+"!');");         
    out.println("END IF;");
    out.println("END;");
    //out.println("/");
    out.println();
  }
  void DefaultDFRI(PrintWriter GlobalOutZ, PrintWriter out, String ImeTabeleL,String[] AtributPKljucL,String[] AtributNameR, String[] AtributNameL,String ImeTabeleR, int RSCRelIntType, boolean Del)
  {
    if (SadrzavanjeDL == 0 && RSCRelIntType == 0)
    {
      SadrzavanjeDRI(GlobalOutZ,out, ImeTabeleL, ImeTabeleR,AtributNameL,AtributNameR);
      SadrzavanjeDL = 1;
    }
    if (RSCRelIntType == 2 && SadrzavanjeFL == 0)
    {
      SadrzavanjeFRI(GlobalOutZ,out, ImeTabeleL, ImeTabeleR,AtributNameL,AtributNameR);
      SadrzavanjeFL = 1;
    }
    String str = "";
    str = "PROCEDURE "+  "SetDefaultDFRI_"+ImeTabeleR.replaceAll(" ","_");
    if (Del)
    {
      out.println(str + "_D");
      GlobalOutZ.println(str+ "_D");
    }else
    {
      out.println(str + "_U");
      GlobalOutZ.println(str+ "_U");
    }
    str = "(u IN " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE)";
    out.println(str);
    GlobalOutZ.println(str+ ";");
    out.println("IS");   
    str = "t " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;";
    out.println(str);
    out.println("BEGIN");    
    out.println("UPDATE " + ImeTabeleL.replaceAll(" ","_")); 
    out.println("SET ");
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = AtributNameL[k].replaceAll(" ","_") + " = default";
         if (k<AtributNameL.length-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }    
    out.println("WHERE ");
    for (int i=0;i<AtributPKljucL.length;i++)
    {
          str = "(" + AtributPKljucL[i].replaceAll(" ","_") + " = " + "u." + AtributPKljucL[i].replaceAll(" ","_") + ")";        
          out.print(str);
          if (i<AtributPKljucL.length-1)
          {
           out.println(" AND ");  
          }      
    }
    out.println(";");   
    out.println("SELECT * INTO t");
    out.println("FROM " + ImeTabeleL.replaceAll(" ","_")); 
    out.print("WHERE ");
    for (int i=0;i<AtributPKljucL.length;i++)
    {
          str = "(" + AtributPKljucL[i].replaceAll(" ","_") + " = " + "u." + AtributPKljucL[i].replaceAll(" ","_") + ")";        
          out.print(str);
          if (i<AtributPKljucL.length-1)
          {
           out.println(" AND ");  
          }      
    }
    out.println(";");
    if (RSCRelIntType == 2)
    {
        out.println("IF NOT "+  "SadrzavanjeFRI_"+ImeTabeleL.replaceAll(" ","_")+"(t) THEN");
    }else
    {
        out.println("IF NOT "+  "SadrzavanjeDRI_"+ImeTabeleL.replaceAll(" ","_")+"(t) THEN");
    }
    if (Del)
    {
      out.println("RAISE_APPLICATION_ERROR(-20000,'Torka u ne moze da se brise u relaciji " + ImeTabeleR.replaceAll(" ","_") +"!');");         
    }else
    {
      out.println("RAISE_APPLICATION_ERROR(-20000,'Torka u ne moze da se modifikuje u relaciji " + ImeTabeleR.replaceAll(" ","_") +"!');");         
    }
    out.println("END IF;");
    out.println("END;");
    //out.println("/");
    out.println();
  }
  public void BrisanjeOsnovniPRIR(String PackageNameConst,PrintWriter out,PrintWriter GlobalOutZ,PrintWriter GlobalOutB, PrintWriter outWorning, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int DelActionR, int[] IdAtributaL,int[] PKljucL, ClassFunction pom, int IdASistema, int IdSemaRel, Connection con)
  {
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeKljucL = pom.FAtributName(PKljucL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);   
    boolean Del = true;    
    if (SadrzavanjePL == 0 && RSCRelIntType == 1)
    {      
      SadrzavanjePRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR);
      SadrzavanjePL = 1;
    }
    
    if (SadrzavanjePR == 0)
    {      
      RSadrzavanjeRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, RSCRelIntType);
      SadrzavanjePR = 1;
    }
    if (DelActionR == -1 && BezAkcijeD == 0)
    {
      SprecRI(GlobalOutZ,GlobalOutB, true);
      BezAkcijeD = 1;
    }   
    
    if (DelActionR == -2)
    {
      if (SetNullRP == 0 && RSCRelIntType == 1)
      {
        SetNullPRI(GlobalOutZ,GlobalOutB, ImeTabeleL,ImeKljucL, ImeAL, ImeTabeleR, RSCRelIntType);
        SetNullRP = 1;
      }
    } else
    if (DelActionR == -3)
    {
      if (SetDefaultP == 0 && RSCRelIntType == 1)
      {
        DefaultPRI(GlobalOutZ,GlobalOutB, ImeTabeleL,ImeKljucL, ImeAL, ImeTabeleR, Del); //vazi samo za parcijalni
        SetDefaultP = 1;
      }
    } else
    if (DelActionR == -4)
    {    
      if (KaskadnoP == 0 && RSCRelIntType == 1)
      {
        KaskadnoPRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeKljucL, ImeAL, ImeTabeleR, RSCRelIntType);
        KaskadnoP = 1;        
      } 
    }     
    String str="";
    
    str =  "TRG_"+ ImeOgranicenja+"RD1";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }    
    out.println("CREATE OR REPLACE TRIGGER " + "TRG_"+ImeOgranicenja+"RD1");    
    out.println("BEFORE DELETE ON "+  ImeTabeleR.replaceAll(" ","_"));
    out.println("BEGIN");
    out.println(PackageNameConst + "Del_Count := 0;");
    out.println(PackageNameConst + "For_Del_" +ImeTabeleR.replaceAll(" ","_") +".DELETE;");
    out.println("END;");
    out.println("/");
    out.println();   
    
    out.println("CREATE OR REPLACE TRIGGER " +  "TRG_"+ImeOgranicenja+"RD2");    
    out.println("   BEFORE DELETE ON "+ ImeTabeleR.replaceAll(" ","_"));
    out.println("   FOR EACH ROW");
    out.println("   DECLARE");
    out.println("   v " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    //out.println("   IdTransakcije VARCHAR2(20);");
    out.println("BEGIN");        
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "v."+ ImeAR[i].replaceAll(" ","_") + " := :OLD." + ImeAR[i].replaceAll(" ","_") + ";";      
      out.println(str);      
    }
    if (RSCRelIntType == 1)
    {
      out.println("   IF "+ PackageName+ "SadrzavanjePRI_" + ImeTabeleR.replaceAll(" ","_")+"(v) THEN");  
    }else if (RSCRelIntType == 2)
    {
      out.println("   IF "+  PackageName+"SadrzavanjeFRI_" + ImeTabeleR.replaceAll(" ","_")+"(v) THEN");
    }else 
    {
      out.println("   IF "+ PackageName+"SadrzavanjeDRI_" + ImeTabeleR.replaceAll(" ","_")+"(v) THEN");
    }    
    str = PackageNameConst + "Del_Count := " + PackageNameConst + "Del_Count + 1;";
    out.println(str);
    for (int i=0;i<ImeAR.length;i++)
    {
      str = PackageNameConst + "For_Del_" +ImeTabeleR.replaceAll(" ","_")+"("+ PackageNameConst + "Del_Count)."+ ImeAR[i].replaceAll(" ","_") + " := v." + ImeAR[i].replaceAll(" ","_") + ";";      
      out.println(str);      
    }
    
    out.println("   END IF;");
    out.println("   END;");
    out.println("/");
    out.println();   
    
    str = "";
    str = "TRG_"+ ImeOgranicenja+"_R"+"_D3";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    out.println("CREATE OR REPLACE TRIGGER "+  "TRG_"+ ImeOgranicenja+"R"+"D3");    
    out.println("   AFTER DELETE ON "+  ImeTabeleR.replaceAll(" ","_"));      
    out.println("   DECLARE ");    
    out.println("     t " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    out.println("     u " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;");
    
    str = "   CURSOR Cursor_" + ImeTabeleL.replaceAll(" ","_") + "(";
    out.print(str);    
    for (int i=0;i<ImeAL.length;i++)
    {
      str = "C_" + ImeAL[i].replaceAll(" ","_") + " "+ ImeTabeleR.replaceAll(" ","_") + "." + ImeAR[i].replaceAll(" ","_") + "%TYPE";
      if (i<ImeAL.length-1)
      {
        str = str + ", ";  
      }
      out.println(str);      
    }
    out.print(")");
    out.println("   IS SELECT * FROM " +   ImeTabeleL.replaceAll(" ","_"));    
    str = "   WHERE "; 
    out.println(str);
    if (RSCRelIntType == 1)
    {
    for (int i=0;i<ImeAL.length;i++)
    {
      str = "   ("+ImeTabeleL.replaceAll(" ","_")+"." + ImeAL[i].replaceAll(" ","_") + " = C_" + ImeAL[i].replaceAll(" ","_") + " OR "+ImeTabeleL.replaceAll(" ","_")+"." + ImeAL[i].replaceAll(" ","_") + " IS NULL)";      
      if (i<ImeAL.length-1)
      {
           str = str + (" AND ");  
      } 
      out.println(str);
    }
    }else
    {
    for (int i=0;i<ImeAL.length;i++)
    {
      str = "   "+ImeTabeleL.replaceAll(" ","_")+"." + ImeAL[i].replaceAll(" ","_") + " = C_" + ImeAL[i].replaceAll(" ","_");      
      if (i<ImeAL.length-1)
      {
           str = str + (" AND ");  
      } 
      out.println(str);
    }
    }       
    out.println(";");
    out.println("BEGIN");
    
    out.println("FOR i IN 1.."+ PackageNameConst + "Del_Count LOOP");
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "t." + ImeAR[i].replaceAll(" ","_")+ " := "+ PackageNameConst + "For_Del_" +ImeTabeleR.replaceAll(" ","_") +"("+ PackageNameConst + "Del_Count)."+ ImeAR[i].replaceAll(" ","_")+";";     
      out.println(str);      
    }    
    out.print("   OPEN " + "Cursor_" + ImeTabeleL.replaceAll(" ","_") + "(");     
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "t." + ImeAR[i].replaceAll(" ","_");
      if (i<ImeAR.length-1)
      {
        str = str + ", ";  
      }
      out.print(str);      
    }    
    out.println(");");
    out.println("LOOP");
    str = "   FETCH Cursor_" + ImeTabeleL.replaceAll(" ","_") + " INTO u;"; 
    out.println(str);
    out.println("   EXIT WHEN Cursor_" + ImeTabeleL.replaceAll(" ","_") + "%NOTFOUND;");
        
    if (RSCRelIntType == 1)
    {
      out.println("   IF NOT "+  PackageName+ "SadrzavanjePRI_" + ImeTabeleL.replaceAll(" ","_")+"(u) THEN");  
    }else if (RSCRelIntType == 2)
    {
      out.println("   IF NOT "+ PackageName+"SadrzavanjeFRI_" + ImeTabeleL.replaceAll(" ","_")+"(u) THEN");
    }else 
    {
      out.println("   IF NOT "+ PackageName+ "SadrzavanjeDRI_" + ImeTabeleL.replaceAll(" ","_")+"(u) THEN");
    }
    
    if (DelActionR == -1)
    {
      out.println("   "+ PackageName+ "SprecavanjeD;");
    } else
    if (DelActionR == -2)
    {
      String worning="Warning! Atributi ";
      String worningAtr="";
      String[] NotNullAtr = Pom.NotNullObelezja(query, IdProjekta, IdSemaRel, IdASistema, con, IdAtributaL, ImeAL);
      if ( NotNullAtr != null && NotNullAtr.length != 0)
      {
        for (int k=0;k<NotNullAtr.length;k++)
        {
          worningAtr = worningAtr + NotNullAtr[k]; 
          if (k<NotNullAtr.length-1)
          {
            worningAtr = worningAtr + ",";
          }          
        }
        worning = worning + worningAtr + " su not null.";
        outWorning.println(worning);
      }
      out.println("   "+  PackageName+"SetNull_"+ImeTabeleR.replaceAll(" ","_") + " (u);");
    } else
    if (DelActionR == -3)
    {     
       out.println("   "+ PackageName+ "SetDefaultPRI_"+ImeTabeleR.replaceAll(" ","_") + " (u);");      
    } else
    {
      out.println("   "+ PackageName+ "KaskadnoDelPRI_" + ImeTabeleR.replaceAll(" ","_") + " (u);");
    }        
    
    out.println("   END IF;");
    out.println("   END LOOP;");
    out.println("   CLOSE " + "Cursor_" + ImeTabeleL.replaceAll(" ","_")+";");
    out.println("   END LOOP;");    
    out.println("   END;");
    out.println("/"); 
    out.println();  
  }
  public void BrisanjeOsnovniDFRIR(PrintWriter out, PrintWriter GlobalOutZ,PrintWriter GlobalOutB,PrintWriter outWorning, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int DelActionR, int[] IdAtributaL,int[] PKljucL, ClassFunction pom, int IdASistema, int IdSemaRel, Connection con)
  {
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeKljucL = pom.FAtributName(PKljucL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);   
        
    
    if (SadrzavanjeFDR == 0)
    {
      RSadrzavanjeRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, RSCRelIntType);
      SadrzavanjeFDR = 1;
    }
    
    if (DelActionR == -1 && BezAkcijeD == 0)
    {
      SprecRI(GlobalOutZ,GlobalOutB, true);
      BezAkcijeD = 1;
    }   
    
    if (DelActionR == -2)
    {
      if (SetNullRFD ==0)
      {
        SetNullFDRI(GlobalOutZ,GlobalOutB, ImeTabeleL,ImeAR, ImeAL, ImeTabeleR);
        SetNullRFD = 1;
      }
    } else
    if (DelActionR == -3)
    {
      if (SetDefaultFD == 0)
      {
        Default(GlobalOutZ,GlobalOutB, ImeTabeleL,ImeKljucL, ImeAL, ImeTabeleR, RSCRelIntType); //vazi samo za parcijalni
        SetDefaultFD = 1;
      }
    } else
    if (DelActionR == -4)
    {    
      if (KaskadnoFD == 0)
      {
        KaskadnoDFRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeAR, ImeAL, ImeTabeleR);
        KaskadnoFD = 1;
      }
    }   
    String str=""; 
    str =  "TRG_"+ ImeOgranicenja+"_R"+"_D";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ ImeOgranicenja+"R"+"D");    
    out.println("   BEFORE DELETE ON "+ ImeTabeleR.replaceAll(" ","_"));      
    out.println("   FOR EACH ROW");
    out.println("   DECLARE");        
    out.println("     v " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
        
    out.println("BEGIN");
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "v." + ImeAR[i].replaceAll(" ","_") + " := :OLD." + ImeAR[i].replaceAll(" ","_") + ";";
      out.println(str);
    }           
    
    out.println("   IF "+ PackageName+ "SadrzavanjeFDRI_" + ImeTabeleR.replaceAll(" ","_")+"(v) THEN");    
    
    if (DelActionR == -1)
    {
      out.println("   "+ PackageName+ "SprecavanjeD;");
    } else
    if (DelActionR == -2)
    {
      String worning="Warning! Atributi ";
      String worningAtr="";
      String[] NotNullAtr = Pom.NotNullObelezja(query, IdProjekta, IdSemaRel, IdASistema, con, IdAtributaL, ImeAL);
      if ( NotNullAtr != null && NotNullAtr.length != 0)
      {
        for (int k=0;k<NotNullAtr.length;k++)
        {
          worningAtr = worningAtr + NotNullAtr[k].replaceAll(" ","_"); 
          if (k<NotNullAtr.length-1)
          {
            worningAtr = worningAtr + ",";
          }          
        }
        worning = worning + worningAtr + " su not null.";
        outWorning.println(worning);
      }
      out.println("   "+  PackageName+"SetNull_"+ImeTabeleR.replaceAll(" ","_") + " (v);");
    } else
    /*
    if (DelActionR == -3)
    {      
        out.println("   "+ ImeBaze + "SetDefault_"+ImeTabeleR + " (v);");      
    } else*/
    {
      out.println("   "+ PackageName+"KaskadnoDelDFRI_" + ImeTabeleR.replaceAll(" ","_") + " (v);");
    }       
    out.println("   END IF;");   
    out.println("   END;");
    out.println("/");
    out.println();  
  }
  void KaskadnoUpPRI(PrintWriter GlobalOutZ,PrintWriter out, String ImeTabeleL,String[] ImeKljucL, String[] AtributNameL, String ImeTabeleR, int RSCRelIntType, String[] AtributNameR)
  {
    String str = "";
    
    if (RSCRelIntType == 1)
    {
      str = "PROCEDURE "+ "KaskadnoUpdPRI_" + ImeTabeleR.replaceAll(" ","_");      
    }else
    {
      str = "PROCEDURE "+  "KaskadnoUpdDRI_" + ImeTabeleR.replaceAll(" ","_");      
    }
    out.println(str);
    GlobalOutZ.println(str);
    str = "(t IN " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE" +", v IN " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE)";
    out.println(str);
    GlobalOutZ.println(str+";");
    out.println("IS");   
    out.println("u " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;");
    out.println("e " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;");
    out.println("BEGIN");
    out.println("SELECT * INTO u FROM " + ImeTabeleL.replaceAll(" ","_"));
    out.println("WHERE (");
    for (int i=0;i<ImeKljucL.length;i++)
    {
          str =  ImeKljucL[i].replaceAll(" ","_") + " = " + "v." + ImeKljucL[i].replaceAll(" ","_");        
          out.print(str);
          if (i<ImeKljucL.length-1)
          {
           out.println(" AND ");  
          }      
    }     
    out.println(");");
    out.print("IF (");
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = "u." + AtributNameL[k].replaceAll(" ","_") + " IS NOT NULL";
         if (k<AtributNameL.length-1)
         {
           str = str + (" AND ");  
         } 
         out.println(str);
    }
    out.println(") THEN");
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = "e."+AtributNameL[k].replaceAll(" ","_") + " := t." + AtributNameR[k].replaceAll(" ","_")+";"; 
         out.println(str);
    } 
    if (AtributNameL.length!=1)
    {
    //out.print("ELSIF ");
    for (int k=0;k<AtributNameL.length;k++)
    {
         if (k<AtributNameL.length-1)
         {
           out.print("ELSIF "); 
           str = "u."+AtributNameL[k].replaceAll(" ","_") + " IS NOT NULL THEN ";
           out.println(str);
         } else
         {
           out.print("ELSE "); 
         }         
         str = "e."+ AtributNameL[k].replaceAll(" ","_") + " := t." + AtributNameR[k].replaceAll(" ","_") + ";";
         out.println(str);        
    } 
    out.println("END IF; "); 
    }
    out.println("UPDATE " + ImeTabeleL.replaceAll(" ","_")); 
    out.println("SET ");    
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = AtributNameL[k].replaceAll(" ","_") + " = e." + AtributNameL[k].replaceAll(" ","_");
         if (k<AtributNameL.length-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }    
    out.println("WHERE ");
    
    //out.println(";");
    
    for (int i=0;i<ImeKljucL.length;i++)
    {
          str = "(" + ImeKljucL[i].replaceAll(" ","_") + " = " + "v." + ImeKljucL[i].replaceAll(" ","_");        
          out.print(str);
          if (i<ImeKljucL.length-1)
          {
           out.println(" AND ");  
          }      
    }     
    out.println(");");
    out.println("END;");
    //out.println("/");
    out.println();
    
  }
  void KaskadnoUpDRI(PrintWriter GlobalOutZ,PrintWriter out, String ImeTabeleL,String[] ImeKljucL, String[] AtributNameL, String ImeTabeleR, String[] AtributNameR)
  {
    String str = "";
    String str1="";   
    str = "PROCEDURE "+  "KaskadnoUpdDRI_" + ImeTabeleR.replaceAll(" ","_");
    
    
    out.println(str);
    GlobalOutZ.println(str);
    str = "(t IN " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE" +", v IN " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE)";
    out.println(str);
    GlobalOutZ.println(str+";");
    out.println("IS");     
    out.println("BEGIN");
    out.println("UPDATE " + ImeTabeleL.replaceAll(" ","_")); 
    out.println("SET ");    
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = AtributNameL[k].replaceAll(" ","_") + " = t." + AtributNameR[k].replaceAll(" ","_");
         if (k<AtributNameL.length-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }    
    out.println("WHERE (");
    
    //out.println(";");
    
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = AtributNameL[k].replaceAll(" ","_") + " = v." + AtributNameR[k].replaceAll(" ","_");
         if (k<AtributNameL.length-1)
         {
           str = str + (" AND ");  
         } 
         out.println(str);
    }      
    
    out.println(");");
    out.println("END;");
    //out.println("/");
    out.println();
    
  }
  void KaskadnoUpFRI(PrintWriter GlobalOutZ, PrintWriter out, String ImeTabeleL,String[] ImeKljucL, String[] AtributNameL, String ImeTabeleR, String[] AtributNameR)
  {
    String str = "";
    
    str = "PROCEDURE "+ "KaskadnoUpdFRI_" + ImeTabeleR.replaceAll(" ","_");
    GlobalOutZ.println(str);
    out.println(str);
    str = "(t IN " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE" +", v IN " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE)";
    out.println(str);
    GlobalOutZ.println(str+";");
    out.println("IS");     
    out.println("BEGIN");
    out.println("UPDATE " +  ImeTabeleL.replaceAll(" ","_")); 
    out.println("SET ");    
    for (int k=0;k<AtributNameL.length;k++)
    {
         str = AtributNameL[k].replaceAll(" ","_") + " = t." + AtributNameR[k].replaceAll(" ","_");
         if (k<AtributNameL.length-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }    
    out.println("WHERE ");
       
    for (int i=0;i<ImeKljucL.length;i++)
    {
          str = "(" + ImeKljucL[i].replaceAll(" ","_") + " = " + "v." + ImeKljucL[i].replaceAll(" ","_") + ")";        
          out.print(str);
          if (i<ImeKljucL.length-1)
          {
           out.println(" AND ");  
          }      
    }
    
    out.println(";");   
    out.println("END;");
    //out.println("/");
    out.println();    
  }
   public void UpdateOsnovniPRIR(String PackageNameConst,PrintWriter out,PrintWriter GlobalOutZ,PrintWriter GlobalOutB, PrintWriter outWorning, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int ModActionR, int[] IdAtributaL,int[] PKljucL, ClassFunction pom, int IdASistema, int IdSemaRel, Connection con)
  {
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeKljucL = pom.FAtributName(PKljucL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);        
    boolean Del = false;  
    if (SadrzavanjePL == 0 && RSCRelIntType == 1)
    {      
      SadrzavanjePRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR);
      SadrzavanjePL = 1;
    }
    
    if (SadrzavanjePR == 0)
    {      
      RSadrzavanjeRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, RSCRelIntType);
      SadrzavanjePR = 1;
    }
    
    if (ModActionR == -1 && BezAkcijeU == 0)
    {
      SprecRI(GlobalOutZ,GlobalOutB, false);
      BezAkcijeU = 1;
    }   
    
    if (ModActionR == -2)
    {
      if (SetNullRP == 0 && RSCRelIntType == 1)
      {
        SetNullPRI(GlobalOutZ,GlobalOutB, ImeTabeleL,ImeKljucL, ImeAL, ImeTabeleR, RSCRelIntType);
        SetNullRP = 1;
      }
    } else
    if (ModActionR == -3)
    {
      if (SetDefaultP == 0 && RSCRelIntType == 1)
      {
        DefaultPRI(GlobalOutZ,GlobalOutB, ImeTabeleL,ImeKljucL, ImeAL, ImeTabeleR, Del); //vazi samo za parcijalni
        SetDefaultP = 1;
      }
    } else
    if (ModActionR == -4)
    {    
      if (KaskadnoUpP == 0 && RSCRelIntType == 1)
      {
        KaskadnoUpPRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeKljucL, ImeAL, ImeTabeleR, RSCRelIntType,ImeAL);
        KaskadnoUpP = 1;        
      } 
    }   
    String str="";
    str = "TRG_"+ ImeOgranicenja+"R"+"U1";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    out.println("CREATE OR REPLACE TRIGGER " + "TRG_"+ImeOgranicenja+"R"+"U1");    
    out.println("BEFORE UPDATE ON "+ ImeTabeleR.replaceAll(" ","_"));
    out.println("BEGIN");
    out.println(PackageNameConst + "Upd_Count := 0;");
    out.println(PackageNameConst + "For_Upd_" +ImeTabeleR.replaceAll(" ","_") +".DELETE;");
    out.println("END;");
    out.println("/");
    out.println();   
    
    out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ ImeOgranicenja+"R"+"U2");    
    out.print("   BEFORE UPDATE OF ");
    for (int j=0;j<ImeAR.length;j++)
    {
        str = ImeAR[j];
        if (j<ImeAR.length-1)
        {
          str = str + ", ";
        }
        out.print(str);
    } 
    out.println(" ON "+ ImeTabeleR.replaceAll(" ","_"));    
    out.println("   FOR EACH ROW");    
    out.print("   WHEN (");
    for (int j=0;j<ImeAR.length;j++)
    {
        str = "NEW." + ImeAR[j].replaceAll(" ","_") + " <> OLD." + ImeAR[j].replaceAll(" ","_");
        if (j<ImeAR.length-1)
        {
          str = str + " OR ";
        }
        out.println(str);
    } 
    out.println("   )");
    out.println("   DECLARE");
    out.println("   v " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    out.println("   t " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    //out.println("   IdTransakcije VARCHAR2(20);");
    out.println("BEGIN");
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "v." + ImeAR[i].replaceAll(" ","_") + " := :OLD." + ImeAR[i].replaceAll(" ","_") + ";";      
      out.println(str);      
    }
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "t." + ImeAR[i].replaceAll(" ","_") + " := :NEW." + ImeAR[i].replaceAll(" ","_") + ";";      
      out.println(str);      
    }
    
    out.println("IF "+ "SadrzavanjePRI_" + ImeTabeleR.replaceAll(" ","_")+"(v) THEN");  
    str = PackageNameConst + "Upd_Count := " + PackageNameConst + "Upd_Count + 1;";
    out.println(str);
    for (int i=0;i<ImeAR.length;i++)
    {
      str = PackageNameConst + "For_Upd_" +ImeTabeleR.replaceAll(" ","_")+"("+ PackageNameConst + "Upd_Count)."+ ImeAR[i].replaceAll(" ","_") + "_Old := v." + ImeAR[i].replaceAll(" ","_") + ";";      
      out.println(str);      
    }
    for (int i=0;i<ImeAR.length;i++)
    {
      str = PackageNameConst + "For_Upd_" +ImeTabeleR.replaceAll(" ","_")+"("+ PackageNameConst + "Upd_Count)."+ ImeAR[i].replaceAll(" ","_") + "_New := t." + ImeAR[i].replaceAll(" ","_") + ";";      
      out.println(str);      
    }    
    out.println("   END IF;");
    out.println("   END;");
    out.println("/"); 
    out.println(); 
    str="";
    str = "TRG_"+ ImeOgranicenja+"R"+"U3";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ ImeOgranicenja+"R"+"U3");    
    out.println("   AFTER UPDATE ON "+ ImeTabeleR.replaceAll(" ","_"));    
    out.println("   DECLARE ");    
    out.println("     t " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    if (ModActionR == -4)
    {
      out.println("     v " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    }    
    out.println("     u " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;");
       
    str = "   CURSOR Cursor_" + ImeTabeleL.replaceAll(" ","_") + "(";
    out.print(str);    
    for (int i=0;i<ImeAL.length;i++)
    {
      str = "C_" + ImeAL[i].replaceAll(" ","_") +" "+ ImeTabeleR.replaceAll(" ","_") + "." + ImeAR[i].replaceAll(" ","_") + "%TYPE";
      if (i<ImeAL.length-1)
      {
        str = str + ", ";  
      }
      out.println(str);      
    }
    out.print(")");
    out.println("   IS SELECT * FROM " + ImeTabeleL.replaceAll(" ","_"));    
    str = "   WHERE "; 
    out.println(str);
    if (RSCRelIntType == 1)
    {
    for (int i=0;i<ImeAL.length;i++)
    {
      str = "   ("+ImeTabeleL.replaceAll(" ","_")+"." + ImeAL[i].replaceAll(" ","_") + " = C_" + ImeAL[i].replaceAll(" ","_") + " OR "+ImeTabeleL.replaceAll(" ","_")+"." + ImeAL[i].replaceAll(" ","_") + " IS NULL)";      
      if (i<ImeAL.length-1)
      {
           str = str + (" AND ");  
      } 
      out.println(str);
    }
    }
    out.println(";");
    out.println("BEGIN");
    out.println("FOR i IN 1.."+ PackageNameConst + "Upd_Count LOOP");
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "t." + ImeAR[i].replaceAll(" ","_")+ " := "+ PackageNameConst + "For_Upd_" +ImeTabeleR.replaceAll(" ","_") +"("+ PackageNameConst + "Upd_Count)."+ ImeAR[i].replaceAll(" ","_")+"_Old;";     
      out.println(str);      
      if (ModActionR == -4)
      {
      str = "v." + ImeAR[i].replaceAll(" ","_")+ " := "+ PackageNameConst + "For_Upd_" +ImeTabeleR.replaceAll(" ","_") +"("+ PackageNameConst + "Upd_Count)."+ ImeAR[i].replaceAll(" ","_")+"_New;";     
      out.println(str); 
      }
    }    
        
    out.print("   OPEN " + "Cursor_" + ImeTabeleL.replaceAll(" ","_") + "(");     
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "t." + ImeAR[i].replaceAll(" ","_");
      if (i<ImeAR.length-1)
      {
        str = str + ", ";  
      }
      out.print(str);      
    }    
    out.println(");");
    out.println("LOOP");
    str = "   FETCH Cursor_" + ImeTabeleL.replaceAll(" ","_") + " INTO u;"; 
    out.println(str);
    out.println("   EXIT WHEN Cursor_" + ImeTabeleL.replaceAll(" ","_") + "%NOTFOUND;");
        
    if (RSCRelIntType == 1)
    {
      out.println("   IF NOT "+ PackageName+"SadrzavanjePRI_" + ImeTabeleL.replaceAll(" ","_")+"(u) THEN");  
    }
    
    if (ModActionR == -1)
    {
      out.println("   "+ PackageName+"SprecavanjeU;");
    } else
    if (ModActionR == -2)
    {
      String worning="Warning! Atributi ";
      String worningAtr="";
      String[] NotNullAtr = Pom.NotNullObelezja(query, IdProjekta, IdSemaRel, IdASistema, con, IdAtributaL, ImeAL);
      if ( NotNullAtr != null && NotNullAtr.length != 0)
      {
        for (int k=0;k<NotNullAtr.length;k++)
        {
          worningAtr = worningAtr + NotNullAtr[k]; 
          if (k<NotNullAtr.length-1)
          {
            worningAtr = worningAtr + ",";
          }          
        }
        worning = worning + worningAtr + " su not null.";
        outWorning.println(worning);
      }
      out.println("   "+ PackageName+"SetNull_"+ImeTabeleR.replaceAll(" ","_") + " (u);");
    } else
    if (ModActionR == -3)
    {
      if (RSCRelIntType == 1)    
      {
        out.println("   "+ PackageName+"SetDefaultPRI_"+ImeTabeleR.replaceAll(" ","_") + " (u);");
      }
    } else
    {
      out.println("   "+ PackageName+"KaskadnoUpdPRI_" + ImeTabeleR.replaceAll(" ","_") + " (v,u);");
    }        
    
    out.println("   END IF;");
    out.println("   END LOOP;");
    out.println("   CLOSE " + "Cursor_" + ImeTabeleL.replaceAll(" ","_")+";");
    out.println("   END LOOP;");
    out.println("   END;");
    out.println("/");
    out.println();  
  }
  
   public void UpdateOsnovniDFRICascDef(String PackageNameConst,PrintWriter out, PrintWriter GlobalOutZ,PrintWriter GlobalOutB,PrintWriter outWorning, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int ModActionR, int[] IdAtributaL,int[] PKljucL, ClassFunction pom, Connection con)
  {
    //JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeKljucL = pom.FAtributName(PKljucL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);        
      
    if (SadrzavanjeFDR == 0)
    {
      RSadrzavanjeRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, RSCRelIntType);
      SadrzavanjeFDR = 1;
    }
    /*
    if (SadrzavanjePR == 0)
    {      
      RSadrzavanjeRI(out, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, RSCRelIntType);
      SadrzavanjePR = 1;
    }
    */
    if (ModActionR == -1 && BezAkcijeU == 0)
    {
      SprecRI(GlobalOutZ,GlobalOutB, false);
      BezAkcijeU = 1;
    }       
    boolean Del=false;
    if (ModActionR == -3)
    {      
        DefaultDFRI(GlobalOutZ,GlobalOutB, ImeTabeleL,ImeKljucL, ImeAL, ImeAR, ImeTabeleR, RSCRelIntType, Del); 
              
    } else
    if (ModActionR == -4)
    {    
      if (KaskadnoUpF == 0)
      {
        KaskadnoUpFRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeKljucL, ImeAL, ImeTabeleR,ImeAL);
        KaskadnoUpF = 1;
      }      
    }  
    String str="";        
    str = "TRG_"+ ImeOgranicenja+"RU1";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    out.println("CREATE OR REPLACE TRIGGER " + "TRG_"+ImeOgranicenja+"RU1");    
    out.println("BEFORE UPDATE ON "+ ImeTabeleR.replaceAll(" ","_"));
    out.println("BEGIN");
    out.println(PackageNameConst + "Upd_Count := 0;");
    out.println(PackageNameConst + "For_Upd_" +ImeTabeleR.replaceAll(" ","_") +".DELETE;");
    out.println("END;");
    out.println("/");
    out.println();  
    
    out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ ImeOgranicenja+"RU2");    
    out.print("   BEFORE UPDATE OF ");
    for (int j=0;j<ImeAR.length;j++)
    {
        str = ImeAR[j].replaceAll(" ","_");
        if (j<ImeAR.length-1)
        {
          str = str + ", ";
        }
        out.print(str);
    } 
    out.println(" ON "+ ImeTabeleR.replaceAll(" ","_"));    
    out.println("   FOR EACH ROW");    
    out.print("   WHEN (");
    for (int j=0;j<ImeAR.length;j++)
    {
        str = "NEW." + ImeAR[j].replaceAll(" ","_") + " <> OLD." + ImeAR[j].replaceAll(" ","_");
        if (j<ImeAR.length-1)
        {
          str = str + " OR ";
        }
        out.println(str);
    } 
    out.println("   )");
    out.println("   DECLARE");
    out.println("   v " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    if (ModActionR == -4)
    {
      out.println("   t " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    }
    //out.println("   IdTransakcije VARCHAR2(20);");
    out.println("BEGIN");
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "v." + ImeAR[i].replaceAll(" ","_") + " := :OLD." + ImeAR[i].replaceAll(" ","_") + ";";      
      out.println(str);      
    }
    if (ModActionR == -4)
    {
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "t." + ImeAR[i].replaceAll(" ","_") + " := :NEW." + ImeAR[i].replaceAll(" ","_") + ";";      
      out.println(str);      
    }
    }
    //out.println(); 
    
    out.println("   IF "+ PackageName + "SadrzavanjeFDRI_" + ImeTabeleR.replaceAll(" ","_")+"(v) THEN");  
    str = PackageNameConst + "Upd_Count := " + PackageNameConst + "Upd_Count + 1;";
    out.println(str);
    for (int i=0;i<ImeAR.length;i++)
    {
      str = PackageNameConst + "For_Upd_" +ImeTabeleR.replaceAll(" ","_")+"("+ PackageNameConst + "Upd_Count)."+ ImeAR[i].replaceAll(" ","_") + "_Old := v." + ImeAR[i].replaceAll(" ","_") + ";";      
      out.println(str);      
    }
    if (ModActionR == -4)
    {
    for (int i=0;i<ImeAR.length;i++)
    {
      str = PackageNameConst + "For_Upd_" +ImeTabeleR.replaceAll(" ","_")+"("+ PackageNameConst + "Upd_Count)."+ ImeAR[i].replaceAll(" ","_") + "_New := t." + ImeAR[i].replaceAll(" ","_") + ";";      
      out.println(str);      
    }
    }
    
    out.println("   END IF;");
    out.println("   END;");
    out.println("/");
    out.println();  
    str="";
    str = "TRG_"+ ImeOgranicenja+"R"+"U3";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    out.println("CREATE OR REPLACE TRIGGER "+  "TRG_"+ ImeOgranicenja+"R"+"U3");    
    out.println("   AFTER UPDATE ON "+ ImeTabeleR.replaceAll(" ","_"));    
    out.println("   DECLARE ");    
    out.println("     t " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    if (ModActionR == -4)
    {
      out.println("     v " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    }
    out.println("     u " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;");
    
    str = "   CURSOR Cursor_" + ImeTabeleL.replaceAll(" ","_") + "(";
    out.print(str);    
    for (int i=0;i<ImeAL.length;i++)
    {
      str = "C_" + ImeAL[i].replaceAll(" ","_") +" "+ ImeTabeleR.replaceAll(" ","_") + "." + ImeAR[i].replaceAll(" ","_") + "%TYPE";
      if (i<ImeAL.length-1)
      {
        str = str + ", ";  
      }
      out.println(str);      
    }
    out.println(")");
    out.println("   IS SELECT * FROM " + ImeTabeleL.replaceAll(" ","_"));    
    str = "   WHERE "; 
    out.print(str);    
    for (int i=0;i<ImeAL.length;i++)
    {
      str = ImeAL[i].replaceAll(" ","_") + " = C_" + ImeAL[i].replaceAll(" ","_");      
      if (i<ImeAL.length-1)
      {
           str = str + (" AND ");  
      } 
      out.println(str);
    }     
    out.println(";");
    out.println("BEGIN");
    out.println("FOR i IN 1.."+ PackageNameConst + "Upd_Count LOOP");
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "t." + ImeAR[i].replaceAll(" ","_")+ " := "+ PackageNameConst + "For_Upd_" +ImeTabeleR.replaceAll(" ","_") +"(i)."+ ImeAR[i].replaceAll(" ","_")+"_Old;";     
      out.println(str);      
      if (ModActionR == -4)
      {
      str = "v." + ImeAR[i].replaceAll(" ","_")+ " := "+ PackageNameConst + "For_Upd_" +ImeTabeleR.replaceAll(" ","_") +"(i)."+ ImeAR[i].replaceAll(" ","_")+"_New;";     
      out.println(str); 
      }
    }    
        
    out.print("   OPEN " + "Cursor_" + ImeTabeleL.replaceAll(" ","_") + "(");     
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "t." + ImeAR[i].replaceAll(" ","_");
      if (i<ImeAR.length-1)
      {
        str = str + ", ";  
      }
      out.print(str);      
    }    
    out.println(");");
    out.println("LOOP");
    str = "   FETCH Cursor_" + ImeTabeleL.replaceAll(" ","_") + " INTO u;"; 
    out.println(str);
    out.println("   EXIT WHEN Cursor_" + ImeTabeleL.replaceAll(" ","_") + "%NOTFOUND;");
        
    if (RSCRelIntType == 2)
    {
      out.println("   IF NOT "+ PackageName+ "SadrzavanjeFRI_" + ImeTabeleL.replaceAll(" ","_")+"(u) THEN");  
    }else
    {
      out.println("   IF NOT "+ PackageName+ "SadrzavanjeDRI_" + ImeTabeleL.replaceAll(" ","_")+"(u) THEN");  
    }
            
    if (ModActionR == -3)
    {
      
       out.println("   "+ PackageName+"SetDefaultDFRI_"+ImeTabeleR.replaceAll(" ","_") + "_U (u);");
      
    } else
    {
      out.println("   "+ PackageName+"KaskadnoUpdFRI_" + ImeTabeleR.replaceAll(" ","_") + " (v,u);");
    }            
    out.println("   END IF;");
    out.println("   END LOOP;");    
    out.println("   CLOSE " + "Cursor_" + ImeTabeleL.replaceAll(" ","_")+";");
    out.println("   END LOOP;");   
    out.println("   END;");
    out.println("/");
    out.println();  
  }
   public void BrisanjeOsnovniDFRIDef(String PackageNameConst,PrintWriter out,PrintWriter GlobalOutZ,PrintWriter GlobalOutB, PrintWriter outWorning, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int[] IdAtributaL,int[] PKljucL, ClassFunction pom, Connection con)
  {
    
    //JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeKljucL = pom.FAtributName(PKljucL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);        
    boolean Del = true;  
    if (SadrzavanjeFDR == 0)
    {
      RSadrzavanjeRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, RSCRelIntType);
      SadrzavanjeFDR = 1;
    }           
    DefaultDFRI(GlobalOutZ,GlobalOutB, ImeTabeleL,ImeKljucL, ImeAL, ImeAR, ImeTabeleR, RSCRelIntType, Del); 
        
    String str="";
    /*
    if (UpdateTabelaFR == 0)
    {
      str = "CREATE TABLE "+ImeBaze + "Updated_FRI_" + ImeTabeleR;
      out.println(str);
      str = "(IdTransakcije VARCHAR2(20), ";
      out.print(str);
      for (int i=0;i<ImeAR.length;i++)
      {      
        str = ImeAR[i] + " " + ImeTabeleR + "." + ImeAR[i] + "%TYPE";
        out.print(str);
        if (i<ImeAL.length-1)
        {
          out.println(",");   
        }      
      }
      out.println(");");
    }*/
    out.println();
    str="";
    str =  "TRG_"+ ImeOgranicenja+"RD1";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    out.println("CREATE OR REPLACE TRIGGER " +  "TRG_"+ImeOgranicenja+"RD1");    
    out.println("BEFORE DELETE ON "+  ImeTabeleR.replaceAll(" ","_"));
    out.println("BEGIN");
    out.println(PackageNameConst + "Del_Count := 0;");
    out.println(PackageNameConst + "For_Del_" +ImeTabeleR.replaceAll(" ","_") +".DELETE;");
    out.println("END;");
    out.println("/");
    out.println();   
    
    out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ ImeOgranicenja+"RD2");    
    out.print("   BEFORE DELETE ");    
    out.println("ON "+  ImeTabeleR.replaceAll(" ","_"));    
    out.println("   FOR EACH ROW");      
    out.println("   DECLARE");
    out.println("   v " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    
    //out.println("   IdTransakcije VARCHAR2(20);");
    out.println("BEGIN");
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "v." + ImeAR[i].replaceAll(" ","_") + " := :OLD." + ImeAR[i].replaceAll(" ","_") + ";";      
      out.println(str);      
    }    
    //out.println(); 
    
    out.println("   IF "+ PackageName+"SadrzavanjeFDRI_" + ImeTabeleR.replaceAll(" ","_")+"(v) THEN");  
    str = PackageNameConst + "Del_Count := " + PackageNameConst + "Del_Count + 1;";
    out.println(str);
    for (int i=0;i<ImeAR.length;i++)
    {
      str = PackageNameConst + "For_Del_" +ImeTabeleR.replaceAll(" ","_")+"("+ PackageNameConst + "Del_Count)."+ ImeAR[i].replaceAll(" ","_") + " := v." + ImeAR[i].replaceAll(" ","_") + ";";      
      out.println(str);      
    }
     
    out.println("   END IF;");
    out.println("   END;");
    out.println("/");
    out.println();    
    
    str =  "TRG_"+ ImeOgranicenja+"RD3";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    out.println("CREATE OR REPLACE TRIGGER "+   "TRG_"+ ImeOgranicenja+"RD3");    
    out.println("   AFTER DELETE ON "+   ImeTabeleR.replaceAll(" ","_"));    
    out.println("   DECLARE ");    
    out.println("     t " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");    
    out.println("     u " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;");
    
    str = "   CURSOR Cursor_" + ImeTabeleL.replaceAll(" ","_") + "(";
    out.print(str);    
    for (int i=0;i<ImeAL.length;i++)
    {
      str = "C_" + ImeAL[i].replaceAll(" ","_") +" "+ ImeTabeleR.replaceAll(" ","_") + "." + ImeAR[i].replaceAll(" ","_") + "%TYPE";
      if (i<ImeAL.length-1)
      {
        str = str + ", ";  
      }
      out.println(str);      
    }
    out.println(")");
    out.println("   IS SELECT * FROM " +   ImeTabeleL.replaceAll(" ","_"));    
    str = "   WHERE "; 
    out.print(str);    
    for (int i=0;i<ImeAL.length;i++)
    {
      str = ImeAL[i].replaceAll(" ","_") + " = C_" + ImeAL[i].replaceAll(" ","_");      
      if (i<ImeAL.length-1)
      {
           str = str + (" AND ");  
      } 
      out.println(str);
    }     
    out.println(";");
    out.println("BEGIN");
    
    out.println("FOR i IN 1.."+ PackageNameConst + "Del_Count LOOP");
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "t." + ImeAR[i].replaceAll(" ","_")+ " := "+ PackageNameConst + "For_Del_" +ImeTabeleR.replaceAll(" ","_") +"(i)."+ ImeAR[i].replaceAll(" ","_")+";";     
      out.println(str);      
    } 
    out.print("   OPEN " + "Cursor_" + ImeTabeleL.replaceAll(" ","_") + "(");     
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "t." + ImeAR[i].replaceAll(" ","_");
      if (i<ImeAR.length-1)
      {
        str = str + ", ";  
      }
      out.print(str);      
    }    
    out.println(");");
    out.println("LOOP");
    str = "   FETCH Cursor_" + ImeTabeleL.replaceAll(" ","_") + " INTO u;"; 
    out.println(str);
    out.println("   EXIT WHEN Cursor_" + ImeTabeleL.replaceAll(" ","_") + "%NOTFOUND;");
        
    if (RSCRelIntType == 2)
    {
      out.println("   IF NOT "+  PackageName+ "SadrzavanjeFRI_" + ImeTabeleL.replaceAll(" ","_") +"(u) THEN");  
    }else
    {
      out.println("   IF NOT "+  PackageName+ "SadrzavanjeDRI_" + ImeTabeleL.replaceAll(" ","_")+"(u) THEN");  
    }       
    out.println("   "+ PackageName+"SetDefaultDFRI_"+ImeTabeleR.replaceAll(" ","_") + "_D (u);"); 
    out.println("   END IF;");
    out.println("   END LOOP;");
    out.println("   CLOSE " + "Cursor_" + ImeTabeleL.replaceAll(" ","_")+";");
    out.println("   END LOOP;");
    
    out.println("   END;");
    out.println("/");
    out.println();  
    
  }
  public void UpdateOsnovniDFRIR(PrintWriter out, PrintWriter GlobalOutZ,PrintWriter GlobalOutB,PrintWriter outWorning, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int ModActionR, int[] IdAtributaL,int[] PKljucL, ClassFunction pom, int IdASistema, int IdSemaRel, Connection con)
  {
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeKljucL = pom.FAtributName(PKljucL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);   
    
    
    if (SadrzavanjeFDR == 0)
    {
      RSadrzavanjeRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, RSCRelIntType);
      SadrzavanjeFDR = 1;
    }
    
    if (ModActionR == -1 && BezAkcijeU == 0)
    {
      SprecRI(GlobalOutZ,GlobalOutB, false);
      BezAkcijeU = 1;
    }   
    
    if (ModActionR == -2)
    {
      if (SetNullRFD ==0)
      {
        SetNullFDRI(GlobalOutZ,GlobalOutB, ImeTabeleL,ImeAR, ImeAL, ImeTabeleR);
        SetNullRFD = 1;
      }
    } else
    if (ModActionR == -3)
    {
      if (SetDefaultFD == 0)
      {
        Default(GlobalOutZ,GlobalOutB, ImeTabeleL,ImeAR, ImeAL, ImeTabeleR, RSCRelIntType); //vazi samo za parcijalni
        SetDefaultFD = 1;
      }
    } else
    if (ModActionR == -4)
    {    
      if (KaskadnoUpFD == 0)
      {
        KaskadnoUpDRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeKljucL, ImeAL, ImeTabeleR, ImeAL);
        KaskadnoUpFD = 1;
      }
    }   
    String str="";   
    str = ImeBaze + "TRG_"+ ImeOgranicenja+"RU";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ImeOgranicenja+"RU");    
    out.print("   BEFORE UPDATE OF ");
    for (int j=0;j<ImeAR.length;j++)
    {
        str = ImeAR[j].replaceAll(" ","_");
        if (j<ImeAR.length-1)
        {
          str = str + ", ";
        }
        out.print(str);
    } 
    out.println(" ON " + ImeTabeleR.replaceAll(" ","_"));     
    out.println("   FOR EACH ROW");
    out.print("   WHEN (");
    for (int j=0;j<ImeAR.length;j++)
    {
        str = "NEW." + ImeAR[j].replaceAll(" ","_") + " <> OLD." + ImeAR[j].replaceAll(" ","_");
        if (j<ImeAR.length-1)
        {
          str = str + " OR ";
        }
        out.println(str);
    } 
    out.println("   )");
    out.println("   DECLARE");        
    out.println("     v " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    if (ModActionR == -4)
    {
      out.println("     t " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");    
    }
    out.println("BEGIN");
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "v." + ImeAR[i].replaceAll(" ","_") + " := :OLD." + ImeAR[i].replaceAll(" ","_") + ";";
      out.println(str);
    }           
    if (ModActionR == -4)
    {
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "t." + ImeAR[i].replaceAll(" ","_") + " := :NEW." + ImeAR[i].replaceAll(" ","_") + ";";
      out.println(str);
    } 
    }
    out.println("   IF "+ PackageName+ "SadrzavanjeFDRI_" + ImeTabeleR.replaceAll(" ","_")+"(v) THEN");    
    
    if (ModActionR == -1)
    {
      out.println("   "+ PackageName+ "SprecavanjeU;");
    } else
    if (ModActionR == -2)
    {
      String worning="Warning! Atributi ";
      String worningAtr="";
      String[] NotNullAtr = Pom.NotNullObelezja(query, IdProjekta, IdSemaRel, IdASistema, con, IdAtributaL, ImeAL);
      if ( NotNullAtr != null && NotNullAtr.length != 0)
      {
        for (int k=0;k<NotNullAtr.length;k++)
        {
          worningAtr = worningAtr + NotNullAtr[k].replaceAll(" ","_"); 
          if (k<NotNullAtr.length-1)
          {
            worningAtr = worningAtr + ",";
          }          
        }
        worning = worning + worningAtr + " su not null.";
        outWorning.println(worning);
      }
      out.println("   "+ PackageName+ "SetNull_"+ImeTabeleR.replaceAll(" ","_") + " (v);");
    } else
    if (ModActionR == -3)
    {      
        out.println("   "+ PackageName+"SetDefault_"+ImeTabeleR.replaceAll(" ","_") + " (v);");      
    } else
    {    
        out.println("   "+ PackageName+"KaskadnoUpdDRI_" + ImeTabeleR.replaceAll(" ","_") + " (t,v);");      
    }       
    out.println("   END IF;");   
    out.println("   END;");
    out.println("/");
    out.println();  
  }
  public void DeleteOsnovniDFRIR(PrintWriter out, PrintWriter GlobalOutZ,PrintWriter GlobalOutB,PrintWriter outWorning, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int DelActionR, int[] IdAtributaL,int[] PKljucL, ClassFunction pom, int IdASistema, int IdSemaRel, Connection con)
  {
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeKljucL = pom.FAtributName(PKljucL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);   
    
    
    if (SadrzavanjeFDR == 0)
    {
      RSadrzavanjeRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, RSCRelIntType);
      SadrzavanjeFDR = 1;
    }
    
    if (DelActionR == -1 && BezAkcijeD == 0)
    {
      SprecRI(GlobalOutZ,GlobalOutB, true);
      BezAkcijeD = 1;
    }   
    
    if (DelActionR == -2)
    {
      if (SetNullRFD ==0)
      {
        SetNullFDRI(GlobalOutZ,GlobalOutB, ImeTabeleL,ImeAR, ImeAL, ImeTabeleR);
        SetNullRFD = 1;
      }
    } else
    if (DelActionR == -3)
    {
      if (SetDefaultFD == 0)
      {
        Default(GlobalOutZ,GlobalOutB, ImeTabeleL,ImeAR, ImeAL, ImeTabeleR, RSCRelIntType); //vazi samo za parcijalni
        SetDefaultFD = 1;
      }
    } else
    if (DelActionR == -4)
    {    
      if (KaskadnoFD == 0)
      {
        KaskadnoDFRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeAR, ImeAL, ImeTabeleR);
        //KaskadnoUpDRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeKljucL, ImeAL, ImeTabeleR, ImeAL);
        KaskadnoFD = 1;
      }
    }   
    String str="";   
    str = "TRG_"+ ImeOgranicenja+"RU";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ImeOgranicenja+"RD");    
    out.print("   BEFORE DELETE ");    
    out.println(" ON " + ImeTabeleR.replaceAll(" ","_"));     
    out.println("   FOR EACH ROW");
    
    out.println("   DECLARE");        
    out.println("     v " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    if (DelActionR == -4)
    {
      out.println("     t " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");    
    }
    out.println("BEGIN");
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "v." + ImeAR[i].replaceAll(" ","_") + " := :OLD." + ImeAR[i].replaceAll(" ","_") + ";";
      out.println(str);
    }           
    if (DelActionR == -4)
    {
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "t." + ImeAR[i].replaceAll(" ","_") + " := :NEW." + ImeAR[i].replaceAll(" ","_") + ";";
      out.println(str);
    } 
    }
    out.println("   IF "+ PackageName+ "SadrzavanjeFDRI_" + ImeTabeleR.replaceAll(" ","_")+"(v) THEN");    
    
    if (DelActionR == -1)
    {
      out.println("   "+ PackageName+ "SprecavanjeD;");
    } else
    if (DelActionR == -2)
    {
      String worning="Warning! Atributi ";
      String worningAtr="";
      String[] NotNullAtr = Pom.NotNullObelezja(query, IdProjekta, IdSemaRel, IdASistema, con, IdAtributaL, ImeAL);
      if ( NotNullAtr != null && NotNullAtr.length != 0)
      {
        for (int k=0;k<NotNullAtr.length;k++)
        {
          worningAtr = worningAtr + NotNullAtr[k].replaceAll(" ","_"); 
          if (k<NotNullAtr.length-1)
          {
            worningAtr = worningAtr + ",";
          }          
        }
        worning = worning + worningAtr + " su not null.";
        outWorning.println(worning);
      }
      out.println("   "+ PackageName+ "SetNull_"+ImeTabeleR.replaceAll(" ","_") + " (v);");
    } else
    if (DelActionR == -3)
    {      
        out.println("   "+ PackageName+"SetDefault_"+ImeTabeleR.replaceAll(" ","_") + " (v);");      
    } else
    {    
        out.println("   "+ PackageName+"KaskadnoDelDFRI_" + ImeTabeleR.replaceAll(" ","_") + " (v);");      
    }       
    out.println("   END IF;");   
    out.println("   END;");
    out.println("/");
    out.println();  
  }
  //inverzni ref int.
  public void SadrzavanjeIRI(PrintWriter GlobalOutZ, PrintWriter out, String ImeTabeleL, String ImeTabeleR, String[] AtributNameR, String[] AtributNameL)
  {
    String str = "";    
    str = "FUNCTION "+ "SadrzavanjeIRI_"+ ImeTabeleL.replaceAll(" ","_");        
    out.println(str); 
    GlobalOutZ.println("FUNCTION "+ "SadrzavanjeIRI_"+ ImeTabeleL.replaceAll(" ","_"));    
    str="(v IN " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE)";
    out.println(str);
    GlobalOutZ.println(str +" RETURN BOOLEAN;");
    out.println("RETURN BOOLEAN");
    out.println("IS");
    out.println("   I  NUMBER;");
    out.println("BEGIN");    
    out.println("SELECT  COUNT(*) INTO I FROM " + ImeTabeleR.replaceAll(" ","_")+" u");
    str = "WHERE ";
    out.println(str);
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = "(u." + AtributNameR[i].replaceAll(" ","_") + " = " + "v." + AtributNameL[i].replaceAll(" ","_") + ")";
      out.print(str);
      if (i<AtributNameR.length-1)
        {
           out.println(" AND ");  
        }       
    }
    out.println(";");
    out.println("IF I <> 0 THEN");
    out.println("RETURN TRUE;");
    out.println("ELSE");
    out.println("RETURN FALSE;");    
    out.println("END IF;"); 
    out.println("END;");     
    //out.println("/");
    out.println();
  }
  public void IzvrsiOkidacIRI(PrintWriter GlobalOutZ, PrintWriter out)
  {
    String str = "";    
    str = "FUNCTION "+ "IzvrsiOkidac" + "(NazivOkidaca VARCHAR2)";    
    out.println(str);
    GlobalOutZ.println("FUNCTION "+ "IzvrsiOkidac" + "(NazivOkidaca VARCHAR2) RETURN BOOLEAN;");
    out.println("RETURN BOOLEAN");
    out.println("IS");  
    out.println("   I  NUMBER;");
    out.println("   Idt  VARCHAR2(20);");
    out.println("BEGIN");    
    out.println("SELECT USERENV('SESIONID')");
    out.println("INTO Idt FROM DUAL;");
    out.println("SELECT COUNT(*) INTO I FROM " +  "StatusOkidaca");    
    out.println("WHERE (Okidac = NazivOkidaca) AND (IdTransakcije = Idt);");      
    out.println("IF I <> 0 ");
    out.println("RETURN FALSE;"); 
    out.println("ELSE");
    out.println("RETURN TRUE;");    
    out.println("END IF;");    
    out.println("END;");  
    //out.println("/");
    out.println();
  }
  void IzvrsenjeOkidacaIRI(PrintWriter GlobalOutZ,PrintWriter out)
  {
    String str = "";
    str = "PROCEDURE "+  "IzvrsenjeOkidaca";
    out.println(str);
    GlobalOutZ.println("PROCEDURE "+  "IzvrsenjeOkidaca");    
    str="(Stat IN BOOLEAN, NazivOkidaca IN VARCHAR2, Idt IN VARCHAR2)";
    out.println(str);
    GlobalOutZ.println(str + ";");
    out.println("IS");
    out.println("BEGIN");    
    out.println("IF Stat THEN");    
    out.println("DELETE FROM "+  "StatusOkidaca WHERE");    
    out.println("Okidac = NazivOkidaca AND IdTransakcije = Idt;");
    out.println("ELSE");
    out.println("INSERT INTO "+  "StatusOkidaca (Okidac, IdTransakcije)");    
    out.println("VALUES (NazivOkidaca, Idt);");    
    out.println("END IF;");    
    out.println("END;"); 
    //out.println("/");
    out.println();
  }
  void KaskadnoIRIDel(PrintWriter out, String ImeTabeleL, String[] AtributNameL, String[] AtributNameR, String ImeTabeleR)  
  {
    String str = "";
    str = "PROCEDURE "+ "KaskadnoIRI_Del"+ ImeTabeleR.replaceAll(" ","_");
    out.println(str);
    out.println("(v IN " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE)");
    out.println("IS");  
    out.println("BEGIN");
    out.println("DELETE FROM " +  ImeTabeleL.replaceAll(" ","_")+" u");    
    out.println("WHERE ");        
    for (int i=0;i<AtributNameL.length;i++)
    {
          str = "u." + AtributNameL[i].replaceAll(" ","_") + " = " + "v." + AtributNameR[i].replaceAll(" ","_");
          if (i<AtributNameL.length-1)
          {
           str = str + (" AND ");  
          } 
          out.println(str);
    }
    out.println(";");                    
    out.println("END;");    
    //out.println("/");
    out.println();
  }
  
  void ProceduraZaUpisIRI(String PackageNameConst,PrintWriter GlobalOutZ,PrintWriter GlobalOutB,int IdProjekta,ClassFunction Pom,String ImeOgranicenja,PrintWriter out, String ImeTabeleL,String ImeTabeleR, int IdASistema, Connection con, int IdSemaR, int IdSemaL,int[] IdAtributaR,int[] IdAtributaL, ClassFunction pom)  
  {
    JDBCQuery query=new JDBCQuery(con);
    int[] IdAtributaROll;
    int[] IdAtributaLOll;
    int s;
    String upit1;  
    
    s=Pom.brojanjeNtorki("IISC_RS_ATT","PR_id="+IdProjekta+" and RS_id="+IdSemaR+" and AS_id="+IdASistema,con); //broji koliko ima atributa odgovarajuca sema relacije
    upit1 = "select Att_id from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSemaR+" and IISC_RS_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" ORDER BY Att_sequence";
    IdAtributaROll=query.selectArraySAint(upit1,s,1);
    query.Close();
    s=Pom.brojanjeNtorki("IISC_RS_ATT","PR_id="+IdProjekta+" and RS_id="+IdSemaL+" and AS_id="+IdASistema,con); //broji koliko ima atributa odgovarajuca sema relacije
    upit1 = "select Att_id from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSemaL+" and IISC_RS_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" ORDER BY Att_sequence";
    IdAtributaLOll=query.selectArraySAint(upit1,s,1);
    query.Close();
    String[] ImeAL = pom.FAtributName(IdAtributaLOll, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaROll, con, IdProjekta); 
    String[] ImeALS = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeARS = pom.FAtributName(IdAtributaR, con, IdProjekta);
    if (Paket == false)
    {
      //KreiranjePaketa(out, ImeTabeleL);
    }
    if (SadrzavanjeIRI == 0)
    {     
      SadrzavanjeIRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeARS,ImeALS);       
      SadrzavanjeIRI = 1;
    }
    /*
    if (IzvrsenjeOkidaca == 0)
    {     
      IzvrsenjeOkidacaIRI(GlobalOutZ,GlobalOutB); 
      IzvrsenjeOkidaca = 1;
    }  
    */
    String str = "";
    str = "CREATE OR REPLACE PROCEDURE "+ "Insert_"+ ImeOgranicenja;
    out.println(str);
    str = "(u IN " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE, v IN " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE)";
    out.println(str);
    
    String Argumenti = "";      
    for (int g=0;g<ImeAL.length;g++)
    {      
      Argumenti = Argumenti + "u." + ImeAL[g].replaceAll(" ","_");
      
      if (g<ImeAL.length-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    }  
    String ArgumentiL = "";      
    for (int g=0;g<ImeAL.length;g++)
    {      
      ArgumentiL = ArgumentiL + ImeAL[g].replaceAll(" ","_");
      
      if (g<ImeAL.length-1)
      {
        ArgumentiL = ArgumentiL + ", ";  
      }
    }   
    String Argumenti1 = "";      
    for (int g=0;g<ImeAR.length;g++)
    {      
      Argumenti1 = Argumenti1 + "v." + ImeAR[g].replaceAll(" ","_");
      
      if (g<ImeAR.length-1)
      {
        Argumenti1 = Argumenti1 + ", ";  
      }
    }     
    String ArgumentiR = "";      
    for (int g=0;g<ImeAR.length;g++)
    {      
      ArgumentiR = ArgumentiR + ImeAR[g].replaceAll(" ","_");
      
      if (g<ImeAR.length-1)
      {
        ArgumentiR = ArgumentiR + ", ";  
      }
    }     
    out.println("IS");     
    out.println("Exc EXCEPTION;");
    out.println("BEGIN"); 
    
    out.println("   "+  PackageNameConst +"IzvrsenjeOkidaca := FALSE;");
    out.println("INSERT INTO " + ImeTabeleL.replaceAll(" ","_"));  
    out.println("(" + ArgumentiL +")");
    out.print("VALUES ( ");        
    str = Argumenti+");";    
    out.println(str);      
    out.println("INSERT INTO " + ImeTabeleR.replaceAll(" ","_")); 
    out.println("(" + ArgumentiR +")");
    out.print("VALUES (");
    str = Argumenti1+");";
    out.println(str);
    //out.println("   "+ ImeBaze + "IzvrsenjeOkidaca (TRUE, 'UpisIRI_" + ImeTabeleL + "', Idt);");
    out.println("   "+ PackageNameConst +"IzvrsenjeOkidaca := TRUE;");
    out.println("IF NOT "+ PackageName + "SadrzavanjeIRI_"+ImeTabeleL.replaceAll(" ","_")+"(u) THEN");    
    out.println(" RAISE Exc;");
    out.println("END IF;");
    out.println("EXCEPTION");
    out.println("WHEN Exc THEN");
    out.println("RAISE_APPLICATION_ERROR(-20001,'Narusen inverzni integritet');");    
    out.println("END;");    
    out.println("/");
    out.println();
  }
  public void BrisanjeUpdateInverzniRI(String PackageNameConst,PrintWriter out, PrintWriter outWorning,String ImeTabeleR, String ImeTabeleL, String ImeOgranicenja, int[] IdAtributaR, int[] IdAtributaL, int IdProjekta, int DelActionR, int Triger, ClassFunction pom, Connection con)
  {   
    //JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);    
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);    
         
    if (DelActionR == -4 && KaskadnoIRI == 0)
    {    
      KaskadnoIRIDel(out, ImeTabeleL, ImeAL, ImeAR, ImeTabeleR);
      KaskadnoIRI = 1;        
    }
    
    String str=""; 
    str = ImeBaze + "TRG_"+ ImeOgranicenja+"_DEL1";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    if (Triger == 1)
    {
      out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ ImeOgranicenja+"_DEL1");    
      out.println("   BEFORE DELETE ON " + ImeTabeleR.replaceAll(" ","_"));
    }else
    {
      out.println("CREATE OR REPLACE TRIGGER " + "TRG_"+ ImeOgranicenja+"_UPD1");    
      out.println("   BEFORE UPDATE ON " + ImeTabeleR.replaceAll(" ","_"));
    }
    out.println("BEGIN");
    out.println(PackageNameConst + "Count_IRI := 0;");
    out.println(PackageNameConst + "For_" +ImeTabeleR.replaceAll(" ","_") +".DELETE;");    
    out.println("END;");    
    out.println("/");
    
    if (Triger == 1)
    {
      out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ ImeOgranicenja+"_DEL2");    
      out.println("   BEFORE DELETE ON " + ImeTabeleR.replaceAll(" ","_"));
      out.println("   FOR EACH ROW");
    }else
    {
      out.println("CREATE OR REPLACE TRIGGER " +  "TRG_"+ ImeOgranicenja+"_UPD2");    
      out.println("   BEFORE UPDATE ON " +  ImeTabeleR.replaceAll(" ","_"));
    
    out.println("   FOR EACH ROW");
    out.print("   WHEN (");
    if (Triger == 1)
    {      
    for (int g=0;g<ImeAR.length;g++)
    {      
      str = "v." + ImeAR[g].replaceAll(" ","_") + " IS NOT NULL";
      
      if (g<ImeAR.length-1)
      {
          str = str +" AND ";  
      }
      out.println(str);
    }
    }else
    {
      for (int g=0;g<ImeAR.length;g++)
      {      
        str = "NEW." + ImeAR[g].replaceAll(" ","_") + " <> OLD." + ImeAR[g].replaceAll(" ","_");
      
        if (g<ImeAR.length-1)
        {
          str = str +" OR ";  
        }
        out.println(str);
      }
    }
    out.println("  )");
    }
    out.println("   DECLARE");
    out.println("   v " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");
    //out.println("   Idt VARCHAR2(20);");
    out.println("   BEGIN");
    for (int g=0;g<ImeAR.length;g++)
    {      
      str = "v." + ImeAR[g].replaceAll(" ","_") + " := :OLD." + ImeAR[g].replaceAll(" ","_") + ";";     
      out.println(str);
    }
    str = PackageNameConst + "Count_IRI := " + PackageNameConst + "Count_IRI + 1;";
    out.println(str);
    for (int i=0;i<ImeAR.length;i++)
    {
      str = PackageNameConst + "For_" +ImeTabeleR.replaceAll(" ","_")+"("+ PackageNameConst + "Count_IRI)."+ ImeAR[i].replaceAll(" ","_") + " := v." + ImeAR[i].replaceAll(" ","_") + ";";      
      out.println(str);      
    }
    
    out.println("END;");
    out.println("/");
    out.println();
    str =   "TRG_"+ ImeOgranicenja+"_DEL3";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    if (Triger == 1)
    {
      out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ ImeOgranicenja+"_DEL3");  
    }else
    {
      out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ ImeOgranicenja+"_UPD3"); 
    }    
    if (Triger == 1)
    {
      out.println("   AFTER DELETE ON " +  ImeTabeleR.replaceAll(" ","_"));
    }else
    {
      out.println("   AFTER UPDATE ON " +   ImeTabeleR.replaceAll(" ","_"));
    }      
    out.println("   DECLARE"); 
    out.println("   v " + ImeTabeleR.replaceAll(" ","_") + "%ROWTYPE;");    
    out.println("   I NUMBER;");
    
    out.println("BEGIN");
    out.println("FOR j IN 1.."+ PackageNameConst + "Count_IRI LOOP");
    for (int i=0;i<ImeAR.length;i++)
    {
      str = "v." + ImeAR[i].replaceAll(" ","_")+ " := "+ PackageNameConst + "For_" +ImeTabeleR.replaceAll(" ","_") +"(j)."+ ImeAR[i].replaceAll(" ","_")+";";     
      out.println(str);      
    }        
    out.println("   SELECT COUNT(*) INTO I FROM "+  ImeTabeleL.replaceAll(" ","_"));
    str = "   WHERE ";    
    out.println(str); 
    for (int g=0;g<ImeAR.length;g++)
    {      
      str = ImeAR[g].replaceAll(" ","_") + " = v." + ImeAR[g].replaceAll(" ","_");   
      if (g<ImeAR.length-1)
      {
          str = str + " AND ";  
      }
      out.println(str); 
    } 
    out.println(";");
    out.println("IF I <> 0 THEN");
    if (Triger == 1)
    {
       if (DelActionR == -4)
       {      
          out.println("   "+  PackageName+"KaskadnoIRI_Del_" + ImeTabeleR.replaceAll(" ","_") + "(v);");      
       }else
       {       
          out.println("RAISE_APPLICATION_ERROR (-20003,'Torka u ne moze da se brise iz relacije "+ImeTabeleR.replaceAll(" ","_")+"');");
       }    
    }else if (Triger == 2)
    {
      out.println("RAISE_APPLICATION_ERROR (-20002,'Torka u ne moze da se menja iz relacije "+ImeTabeleR.replaceAll(" ","_")+"');");
    }
    out.println("END IF;"); 
    out.println("END LOOP;");   
    
    out.println("   END;"); 
    out.println("/");
    out.println();
  }
   
  public void UpisOsnovniIRIRefIL(String PackageNameConst, PrintWriter GlobalOutZ,PrintWriter GlobalOutB,PrintWriter out,PrintWriter outWorning,String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaL,int[] IdAtributaR, int IdProjekta, ClassFunction pom, Connection con)
  {     
    //JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);
         
    if (SadrzavanjeIRI == 0)
    {     
      SadrzavanjeIRI(GlobalOutZ,GlobalOutB, ImeTabeleL, ImeTabeleR, ImeAR, ImeAL);       
      Sadrzavanje = 1;
    }
    /*
    if (IzvrsiOkidac == 0)
    {     
      IzvrsiOkidacIRI(GlobalOutZ,GlobalOutB); 
      IzvrsiOkidac = 1;
    }    
    */
    String str="";
    str = "TRG_"+ ImeOgranicenja+"_INS";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ ImeOgranicenja+"_INS");         
    out.println("BEFORE INSERT ON "+ ImeTabeleL.replaceAll(" ","_"));
    out.println("FOR EACH ROW");
    out.println("DECLARE ");
    out.println("   u " + ImeTabeleL.replaceAll(" ","_") + "%ROWTYPE;"); 
    out.println("   Exc EXCEPTION;");
    out.println("BEGIN");
    for (int i=0;i<ImeAL.length;i++)
    {
      str = "u." + ImeAL[i].replaceAll(" ","_") + " := :NEW." + ImeAL[i].replaceAll(" ","_") + ";";
      out.println(str);
    }     
    //out.println("IF "+ ImeBaze + "IzvrsiOkidac('TRG_"+ ImeTabeleL+"_"+ImeOgranicenja+"_INS') AND "); 
    //out.println(" NOT "+ ImeBaze + "SadrzavanjeIRI_"+ImeTabeleL+"(u) THEN");
    out.println("IF "+  PackageNameConst + "IzvrsenjeOkidaca = TRUE AND "); 
    out.println(" NOT "+ PackageName + "SadrzavanjeIRI_"+ImeTabeleL.replaceAll(" ","_")+"(u) THEN");
    out.println("RAISE Exc;");   
    out.println("END IF;");
    out.println("EXCEPTION");
    out.println(" WHEN Exc THEN");
    out.println("RAISE_APPLICATION_ERROR(-20004,'Narusen inverzni ref. integritet!');");    
    out.println("END;");  
    out.println("/");
    out.println();
  }
  
  public void InvezniRIPogled(String PackageNameConst,PrintWriter out,PrintWriter outWorning,String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaL,int[] IdAtributaR, int IdProjekta, ClassFunction pom, Connection con, int IdASistema)
  {     
    JDBCQuery query=new JDBCQuery(con);
    String str=""; 
    ResultSet rs;
    Set LIdAtributa = new HashSet(); 
    Set RIdAtributa = new HashSet();
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);
    int IdSema=0;
    String upit1;
    upit1 = "select RS_id from IISC_RELATION_SCHEME where PR_id="+IdProjekta+" and AS_id="+IdASistema+" and RS_name='"+ImeTabeleL+"'";
    try{
    rs=query.select(upit1);
    rs.next();
    IdSema = rs.getInt(1);
    query.Close();
    }
    catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);                          
    }    
    int k=Pom.brojanjeNtorki("IISC_RS_ATT","PR_id="+IdProjekta+" and RS_id="+IdSema+" and AS_id="+IdASistema,con); //broji koliko ima atributa odgovarajuca sema relacije
    upit1 = "select Att_id,Att_null from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSema+" and IISC_RS_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" ORDER BY Att_sequence";
    int[] IdAtributa=query.selectArraySAint(upit1,k,1);
    String[] ImenaSvihAtributaL = pom.FAtributName(IdAtributa, con, IdProjekta);
    for (int i=0;i<ImenaSvihAtributaL.length;i++)
    {
      LIdAtributa.add(ImenaSvihAtributaL[i].replaceAll(" ","_"));
    }    
    query.Close();
    upit1 = "select rs_id from iisc_relation_scheme where PR_id="+IdProjekta+" and AS_id="+IdASistema+" and rs_name='"+ImeTabeleR+"'";
    try{
    rs=query.select(upit1);
    rs.next();
    IdSema = rs.getInt(1);
    query.Close();
    }
    catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);                          
    }    
    k=Pom.brojanjeNtorki("IISC_RS_ATT","PR_id="+IdProjekta+" and RS_id="+IdSema+" and AS_id="+IdASistema,con); //broji koliko ima atributa odgovarajuca sema relacije
    upit1 = "select Att_id,Att_null from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSema+" and IISC_RS_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" ORDER BY Att_sequence";
    IdAtributa=query.selectArraySAint(upit1,k,1);
    String[] ImenaSvihAtributaR = pom.FAtributName(IdAtributa, con, IdProjekta);
    query.Close();
    for (int i=0;i<ImenaSvihAtributaR.length;i++)
    {
      RIdAtributa.add(ImenaSvihAtributaR[i].replaceAll(" ","_"));
    } 
    RIdAtributa.removeAll(LIdAtributa);
    
    out.println("CREATE OR REPLACE VIEW "+ "View_"+ImeTabeleL.replaceAll(" ","_")+"_"+ImeTabeleR.replaceAll(" ","_")+" AS ");           
    
    out.print("SELECT ");
    for (int i=0;i<LIdAtributa.toArray().length;i++)
    {
      str= "u."+ LIdAtributa.toArray()[i]+",";
      out.print(str);
    }        
    for (int i=0;i<RIdAtributa.toArray().length;i++)
    {
      str= "v."+ RIdAtributa.toArray()[i];
      if (i<RIdAtributa.toArray().length-1)
      {
        str=str+",";
      }
      out.print(str);
    } 
    out.println();
    out.println("FROM " + ImeTabeleL.replaceAll(" ","_") + " u, " + ImeTabeleR.replaceAll(" ","_") + " v");
    out.print("WHERE ");
    for (int i=0;i<ImeAL.length;i++)
    {
      str = "u."+ImeAL[i].replaceAll(" ","_") + " = v." + ImeAR[i].replaceAll(" ","_");  
      if(i<ImeAL.length-1)
      {
        str = str + " AND ";
      }
      out.println(str);
    }   
    out.println("/");
    out.println();
        
    str =  "TRG_"+ImeOgranicenja+"_View";   
    if(str.length()>30)
    {
      outWorning.println(str + " je predugacak naziv trigera! Maksimum je 30 karaktera");
    }
    String Argumenti = "";      
    for (int g=0;g<ImenaSvihAtributaL.length;g++)
    {      
      Argumenti = Argumenti + ":NEW." + ImenaSvihAtributaL[g].replaceAll(" ","_");
      
      if (g<ImenaSvihAtributaL.length-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    } 
    String ArgumentiL = "";      
    for (int g=0;g<ImenaSvihAtributaL.length;g++)
    {      
      ArgumentiL = ArgumentiL  + ImenaSvihAtributaL[g].replaceAll(" ","_");
      
      if (g<ImenaSvihAtributaL.length-1)
      {
        ArgumentiL = ArgumentiL + ", ";  
      }
    }
    String Argumenti1 = "";      
    for (int g=0;g<ImenaSvihAtributaR.length;g++)
    {      
      Argumenti1 = Argumenti1 + ":NEW." + ImenaSvihAtributaR[g].replaceAll(" ","_");
      
      if (g<ImenaSvihAtributaR.length-1)
      {
        Argumenti1 = Argumenti1 + ", ";  
      }
    } 
    String Argumenti2 = "";      
    for (int g=0;g<ImenaSvihAtributaR.length;g++)
    {      
      Argumenti2 = Argumenti2 + ImenaSvihAtributaR[g].replaceAll(" ","_");
      
      if (g<ImenaSvihAtributaR.length-1)
      {
        Argumenti2 = Argumenti2 + ", ";  
      }
    }
    out.println("CREATE OR REPLACE TRIGGER "+ "TRG_"+ImeOgranicenja+"_View");         
    out.println("INSTEAD OF INSERT ON "+  "View_"+ImeTabeleL.replaceAll(" ","_")+"_"+ImeTabeleR.replaceAll(" ","_"));
    out.println("FOR EACH ROW");
    out.println("DECLARE ");
    out.println("   I NUMBER;"); 
    out.println("   Exc EXCEPTION;");
    out.println("   t "+ ImeTabeleL.replaceAll(" ","_")+"%ROWTYPE;"); 
    out.println("BEGIN");        
    out.println("SELECT COUNT(*) INTO I");
    out.println("FROM "+ImeTabeleL.replaceAll(" ","_"));
    out.println("WHERE ");
    for (int i=0;i<ImeAL.length;i++)
    {
      str = ImeAL[i].replaceAll(" ","_") + " = :NEW." + ImeAL[i].replaceAll(" ","_");
      if (i<ImeAL.length-1)
      {
        str = str + " AND ";
      }
      out.print(str);
    } 
    out.println(";");
    out.println("IF I <> 0 THEN ");
    out.println("INSERT INTO " + ImeTabeleR.replaceAll(" ","_")); 
    out.println("(" + Argumenti2 +")");
    out.print("VALUES (");
    str = Argumenti1+");";
    out.println(str);
    out.println("ELSE");    
    out.println("   "+ PackageNameConst +"IzvrsenjeOkidaca := FALSE;");
    out.println("INSERT INTO " + ImeTabeleL.replaceAll(" ","_")); 
    out.println("(" + ArgumentiL +")");
    out.print("VALUES ( ");        
    str = Argumenti+");";    
    out.println(str);      
    out.println("INSERT INTO " + ImeTabeleR.replaceAll(" ","_")); 
    out.println("(" + Argumenti2 +")");
    out.print("VALUES (");
    str = Argumenti1+");";
    out.println(str);
    //out.println("   "+ ImeBaze + "IzvrsenjeOkidaca (TRUE, 'UpisIRI_" + ImeTabeleL + "', Idt);");
    out.println("   "+ PackageNameConst +"IzvrsenjeOkidaca := TRUE;");
    
    out.println("SELECT * INTO t ");
    out.println("FROM "+ImeTabeleL.replaceAll(" ","_"));
    out.println("WHERE ");
    for (int i=0;i<ImeAL.length;i++)
    {
      str = ImeAL[i].replaceAll(" ","_") + " = :NEW." + ImeAL[i].replaceAll(" ","_");
      if (i<ImeAL.length-1)
      {
        str = str + " AND ";
      }
      out.print(str);
    } 
    out.println(";");    
    
    out.println("IF NOT "+ PackageName+ "SadrzavanjeIRI_"+ImeTabeleL.replaceAll(" ","_")+"(t) THEN");        
    out.println("RAISE Exc;");       
    out.println("END IF;");
    out.println("END IF;");
    out.println("EXCEPTION");
    out.println(" WHEN Exc THEN");
    out.println("RAISE_APPLICATION_ERROR(-20005,'Narusen inverzni ref. integritet!');");    
    //out.println("END IF;");
    out.println("END;");
    out.println("/");
    out.println();
  }     
       
    
  
  
}
