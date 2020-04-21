package iisc;
import java.sql.*;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;
 
public class TriggerMSSQL 
{
  private int Sadrzavanje=0; 
  private int SadrzavanjeIRI=0;  
  private int SetNullRP=0;  
  private int SetNullRFD=0;  
  private int SetDefaultP=0;
  private int SetDefaultFD=0;
  private int SetDefaultFDR=0;
  private int KaskadnoFD=0;
  private int KaskadnoP=0;
  private int KaskadnoUpFD=0;
  private int KaskadnoUpP=0;
  private int KaskadnoIRI=0;  
  private int IzvrsiOkidac=0;
  private int IzvrsenjeOkidaca=0;
  private int Postoji=0;
  private ClassFunction Pom = new ClassFunction();
  public TriggerMSSQL()
  {
  }  
  
  public void SadrzavanjePRI(PrintWriter out, String ImeTabeleL, String ImeTabeleR,String[] AtributNameL, String[] AtributNameR, String[] ParamFunkcije, boolean alter, Connection conn)
  {
    String str = "";    
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.SadrzavanjePRI_"+ ImeTabeleL+"]') and OBJECTPROPERTY(id, N'IsScalarFunction') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
     str = "ALTER FUNCTION dbo.SadrzavanjePRI_"+ ImeTabeleL.replaceAll(" ","_") + "(";
   }else
   {
     str = "CREATE FUNCTION dbo.SadrzavanjePRI_"+ ImeTabeleL.replaceAll(" ","_") + "(";
   }
    
    int m = AtributNameL.length; 
    for (int i=0;i<m;i++)
    {
      str = str + "@" + ParamFunkcije[i].replaceAll(" ","_");
      if (i<m-1)
      {
           str = str + (",");  
      } 
    }
    out.println(str+")"); 
    out.println("RETURNS int");
    out.println("AS");    
    out.println("BEGIN");
    out.println("   DECLARE @Count int,");
    out.println("   @Ret  int");
    out.println("SELECT @Count = COUNT(*) FROM "+ImeTabeleR.replaceAll(" ","_")+" v");
    str = "WHERE ";
    out.println(str);
    for (int i=0;i<m;i++)
    {
      str = "(@" + AtributNameL[i].replaceAll(" ","_") + " IS NULL OR " + "v." + AtributNameR[i].replaceAll(" ","_") + " = " + "@" + AtributNameL[i].replaceAll(" ","_") + ")";
      out.print(str);
      if (i<m-1)
        {
           out.println(" AND ");  
        }
      //out.println(str); 
    }
    out.println();
    out.println("IF @Count != 0 ");
    out.println("SET @ret=1");
    out.println("ELSE");
    out.println("SET @ret=0");
    out.println("RETURN @ret");
    out.println("END");    
    out.println("GO");
    out.println();
  }
  void SadrzavanjeFRI(PrintWriter out, String ImeTabeleL, String ImeTabeleR, String[] AtributNameL, String[] AtributNameR, String[] ParamFunkcije, boolean alter, Connection conn)
  {
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.SadrzavanjeFRI_"+ ImeTabeleL+"]') and OBJECTPROPERTY(id, N'IsScalarFunction') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
    str = "ALTER FUNCTION dbo.SadrzavanjeFRI_"+ ImeTabeleL.replaceAll(" ","_") + "(";
   }else
   {
     str = "CREATE FUNCTION dbo.SadrzavanjeFRI_"+ ImeTabeleL.replaceAll(" ","_") + "(";
   }
    int m = AtributNameL.length; 
    for (int i=0;i<m;i++)
    {
      str = str + "@" + ParamFunkcije[i].replaceAll(" ","_");
      if (i<m-1)
      {
           str = str + (",");  
      } 
    }
    out.println(str+")"); 
    out.println("RETURNS int");
    out.println("AS");    
    out.println("BEGIN");
    out.println("   DECLARE @Count int,");
    out.println("   @ret  int");
    out.println("SET @ret=0");
    out.print("IF ");
    for (int i=0;i<m;i++)
    {
      str = "(@" + AtributNameL[i].replaceAll(" ","_") + " IS NULL)";
      out.print(str);
      if (i<m-1)
        {
           out.println(" AND ");  
        }
    }
    out.println();
    out.println("   SET @ret=1");
    out.println("ELSE");  
    out.println("BEGIN");
    out.println("SELECT @Count = COUNT(*) FROM "+ImeTabeleR.replaceAll(" ","_")+" v");
    str = "WHERE ";
    out.print(str);
    for (int i=0;i<m;i++)
    {
      str = "(v." + AtributNameR[i].replaceAll(" ","_") + " = " + "@" + AtributNameL[i].replaceAll(" ","_") + ")";
      out.print(str);
      if (i<m-1)
        {
           out.println(" AND ");  
        }
      //out.println(str); 
    }
    out.println();
    out.println("IF @Count != 0 ");
    out.println("SET @ret=1");
    out.println("END");
    out.println("RETURN @ret");
    out.println("END");    
    out.println("GO");
    out.println();
  }
  void SadrzavanjeDRI(PrintWriter out, String ImeTabeleL, String ImeTabeleR, String[] AtributNameL, String[] AtributNameR, String[] ParamFunkcije, boolean alter, Connection conn)
  {
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.SadrzavanjeDRI_"+ ImeTabeleL+"]') and OBJECTPROPERTY(id, N'IsScalarFunction') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER FUNCTION dbo.SadrzavanjeDRI_"+ ImeTabeleL.replaceAll(" ","_") + "(";
   }else
   {
     str = "CREATE FUNCTION dbo.SadrzavanjeDRI_"+ ImeTabeleL.replaceAll(" ","_") + "(";
   }
    int m = AtributNameL.length; 
    for (int i=0;i<m;i++)
    {
      str = str + "@" + ParamFunkcije[i].replaceAll(" ","_");
      if (i<m-1)
      {
           str = str + (",");  
      } 
    }
    out.println(str+")"); 
    out.println("RETURNS int");
    out.println("AS");    
    out.println("BEGIN");
    out.println("   DECLARE @Count int,");
    out.println("   @ret  int");
    out.println("SET @ret=0");
    out.print("IF ");
    for (int i=0;i<m;i++)
    {
      str = "(@" + AtributNameL[i].replaceAll(" ","_") + " IS NULL)";
      out.print(str);
      if (i<m-1)
        {
           out.println(" OR ");  
        }
    }
    out.println();
    out.println("   SET @ret=1");
    out.println("ELSE");  
    out.println("BEGIN");
    out.println("SELECT @Count = COUNT(*) FROM "+ImeTabeleR.replaceAll(" ","_")+" v");
    str = "WHERE ";
    out.print(str);
    for (int i=0;i<m;i++)
    {
      str = "(v." + AtributNameR[i].replaceAll(" ","_") + " = " + "@" + AtributNameL[i].replaceAll(" ","_") + ")";
      out.print(str);
      if (i<m-1)
        {
           out.println(" AND ");  
        }
      //out.println(str); 
    }
    out.println();
    out.println("IF @Count != 0 ");
    out.println("SET @ret=1");
    out.println("END");
    out.println("RETURN @ret");
    out.println("END");    
    out.println("GO");
    out.println();
  }
  public void UpisOsnovniRIL(PrintWriter out, PrintWriter outWorning, int[] PKljucL, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaL, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int InsActionL, ClassFunction pom,int IdASistema, int IdSemaRel, Connection con, boolean alter, Connection conn)
  {   //triger za upis leve strane za osnovni ref. int. parcijalni, full i default
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);
    String[] ParamFunk = pom.ParametriFunkcijeProcedure(IdAtributaL, con, IdProjekta, Rek, 1);
    String[] ParamProcDefNull = pom.ParametriFunkcijeProcedure(PKljucL, con, IdProjekta, Rek,1); 
    //String[] ParamDeklaracijeL = pom.ParametriFunkcijeProcedure(IdAtributaL, query, IdProjekta, Rek);
    String[] ImeKljucL = pom.FAtributName(PKljucL, con, IdProjekta);
    if (RSCRelIntType == 1 && Sadrzavanje == 0)
    {     
      SadrzavanjePRI(out, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, ParamFunk, alter, conn); 
      Sadrzavanje = 1;
    }else if (RSCRelIntType == 0 && Sadrzavanje == 0)
    {
      SadrzavanjeDRI(out, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, ParamFunk, alter, conn); 
      Sadrzavanje = 1;
    } else
    {
      if (Sadrzavanje == 0)
      {
        SadrzavanjeFRI(out, ImeTabeleL, ImeTabeleR,ImeAL, ImeAR, ParamFunk, alter, conn);
        Sadrzavanje = 1;
      } 
    }
    String str="";
    ResultSet rs;
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[TRG_"+ ImeTabeleL+"_"+ImeOgranicenja+"_INS"+"]') and OBJECTPROPERTY(id, N'IsTrigger') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      out.println("ALTER TRIGGER TRG_"+ ImeTabeleL.replaceAll(" ","_")+"_"+ImeOgranicenja+"_INS");
    }else
    {
      out.println("CREATE TRIGGER TRG_"+ ImeTabeleL.replaceAll(" ","_")+"_"+ImeOgranicenja+"_INS");    
    }  
    out.println("   ON "+ ImeTabeleL.replaceAll(" ","_"));    
    out.println("   FOR INSERT");
    out.println("AS");
    
    int m = IdAtributaL.length;  
    String[] AtributName = new String[m];
    try {
    out.print("   DECLARE ");
    for (int i=0;i<m;i++)
    {
      String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaL[i];
      rs=query.select(upit);
      if (rs.next())
      {
        AtributName[i] = rs.getString("Att_mnem");
        out.print("   @" + AtributName[i].replaceAll(" ","_")); 
        int IdDomena = rs.getInt("Dom_id"); 
        query.Close();        
        String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta,1); 
        int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);        
        if (Duzina!=0)            
        {
              TipPodatka = TipPodatka + "(" + Duzina + ")";               
        }
        out.print(" " + TipPodatka);          
        if (InsActionL != -1)
        {
          out.println(",");
        }else
        {
          if (i<m-1)
          {
             out.println(",");  
          }else{
            out.println();
          }   
        } 
      } 
    }
    if (InsActionL != -1)
    {
    for (int i=0;i<ParamProcDefNull.length;i++)
    {
      str = str + "   @" + ParamProcDefNull[i];
      if (i<ParamProcDefNull.length-1)
      {
           str = str + (",");  
      }
      out.println(str);
    }
    }
    out.print("SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = "@" + AtributName[i].replaceAll(" ","_") + "= " + AtributName[i].replaceAll(" ","_");      
      if (InsActionL == -1)
    {
      if (i<m-1)
      {
        str = str + ", ";  
      }
    }else
    {
      str = str + ", ";
    }
      out.println(str);
    } 
    if (InsActionL != -1)
    {
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "   @" + ImeKljucL[i].replaceAll(" ","_") + " = " + ImeKljucL[i].replaceAll(" ","_");
      if (i<ImeKljucL.length-1)
      {
           str = str + (",");  
      } 
      out.print(str);
    }
    out.println();
    }    
    out.println("FROM Inserted");
    String Argumenti="";    
    for (int k=0;k<m;k++)
    {      
      Argumenti = Argumenti + "@" + AtributName[k].replaceAll(" ","_");
      
      if (k<m-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    }
    if (RSCRelIntType == 1)
    {
      out.println("IF dbo.SadrzavanjePRI_"+ImeTabeleL.replaceAll(" ","_")+"("+Argumenti+")=0");
    } else if (RSCRelIntType == 2)
    {
      out.println("IF dbo.SadrzavanjeFRI_"+ImeTabeleL.replaceAll(" ","_")+"("+Argumenti+")=0");
    }else
    {
      out.println("IF dbo.SadrzavanjeDRI_"+ImeTabeleL.replaceAll(" ","_")+"("+Argumenti+")=0");
    }
    out.println("BEGIN");
    if (InsActionL == -1)
    {  
      out.println("RAISERROR('Torka u ne moze da se upise u relaciju "+ImeTabeleL.replaceAll(" ","_")+"',16,1)");
      out.println("ROLLBACK TRAN");
    }else
    if (InsActionL == -3)
    {
      out.println("UPDATE u");
      out.println("SET");
      for (int i=0;i<m;i++)
      {
        str = "u." + ImeAL[i].replaceAll(" ","_") + " = default";        
        if (i<m-1)
        {
          str = str + ", ";  
        }
        out.println(str);
      } 
      str = "FROM " + ImeTabeleL.replaceAll(" ","_") + " u";
      out.println(str);
      out.println("WHERE ");
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "(u." + ImeKljucL[i].replaceAll(" ","_") + " = " + "@" + ImeKljucL[i].replaceAll(" ","_") + ")";       
      out.print(str);
      if (i<ImeKljucL.length-1)
        {
           out.println(" AND ");  
        }      
    }
    out.println();
    out.print("SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = "@" + AtributName[i].replaceAll(" ","_") + "= " + "u." + AtributName[i].replaceAll(" ","_");      
      if (i<m-1)
      {
        str = str + ", ";  
      }
      out.println(str);
    } 
    out.println("FROM " + ImeTabeleL.replaceAll(" ","_") + " u");
    out.println("WHERE ");
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "(u." + ImeKljucL[i].replaceAll(" ","_") + " = " + "@" + ImeKljucL[i].replaceAll(" ","_") + ")";       
      out.print(str);
      if (i<ImeKljucL.length-1)
        {
           out.println(" AND ");  
        }      
    }
    out.println();  
    Argumenti = "";
    for (int k=0;k<m;k++)
    {      
      Argumenti = Argumenti + "@" + AtributName[k].replaceAll(" ","_");      
      if (k<m-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    } 
    if (RSCRelIntType == 1)
    {
        out.println("IF dbo.SadrzavanjePRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");
    }else if (RSCRelIntType == 2)
    {
        out.println("IF dbo.SadrzavanjeFRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");
    }else
    {
      out.println("IF dbo.SadrzavanjeDRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");
    }
    out.println("BEGIN");
    out.println("RAISERROR('Torka u ne moze da se upise u relaciju "+ImeTabeleL.replaceAll(" ","_")+"',16,1)");
    out.println("ROLLBACK TRAN");
    out.println("END");
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
          worningAtr = worningAtr + NotNullAtr[k].replaceAll(" ","_"); 
          if (k<NotNullAtr.length-1)
          {
           worningAtr = worningAtr + ",";
          }          
        }
        worning = worning + worningAtr + " su not null.";
        outWorning.println(worning);
    }
      out.println("UPDATE u");
      out.println("SET");
      for (int i=0;i<m;i++)
      {
        str = "u." + ImeAL[i].replaceAll(" ","_") + " = null";        
        if (i<m-1)
        {
          str = str + ", ";  
        }
        out.println(str);
      } 
      str = "FROM " + ImeTabeleL.replaceAll(" ","_") + " u";
      out.println(str);
      out.println("WHERE ");
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "(u." + ImeKljucL[i].replaceAll(" ","_") + " = " + "@" + ImeKljucL[i].replaceAll(" ","_") + ")";       
      out.print(str);
      if (i<ImeKljucL.length-1)
        {
           out.println(" AND ");  
        }      
    }
    out.println();  
    }
    
    out.println("END");
    out.println("GO");
    out.println();
  }
       
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }
    
  }  
  void KaskadnoUpdateDFRIL(PrintWriter out, String ImeTabeleL,String[] AtributNameL, String[] AtributNameR,String[] ParamProcedure, String ImeTabeleR, int RSCRelIntType, boolean alter, Connection conn)
  {
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.KaskadnoUpdDFRI_" + ImeTabeleR+"]') and OBJECTPROPERTY(id, N'IsProcedure') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
    str = "ALTER PROCEDURE dbo.KaskadnoUpdDFRI_" + ImeTabeleR.replaceAll(" ","_") + "(";
   }else
   {
     str = "CREATE PROCEDURE dbo.KaskadnoUpdDFRI_" + ImeTabeleR.replaceAll(" ","_") + "(";
   }
   
    out.print(str);
    //str = "CREATE PROCEDURE dbo.KaskadnoUpdPRI_"+ ImeTabeleR + "(";
    int m = AtributNameL.length;     
    for (int i=0;i<ParamProcedure.length;i++)
    {
      str = "@" + ParamProcedure[i];        
      if(i<ParamProcedure.length-1)
      {
        str = str+",";
      }
      out.print(str);
    }    
    
    out.println(")");
    out.println("AS");    
   
    out.println("UPDATE u"); 
    out.println("SET ");
    for (int k=0;k<m;k++)
    {
         str = "u." + AtributNameL[k].replaceAll(" ","_") + " = @" + AtributNameR[k].replaceAll(" ","_")+"_New";
         if (k<m-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }
    str = "FROM " + ImeTabeleL.replaceAll(" ","_") + " u";
    out.println(str);
    out.println("WHERE ");    
    for (int k=0;k<m;k++)
    {
         str = "u." + AtributNameL[k].replaceAll(" ","_") + " = @" + AtributNameR[k].replaceAll(" ","_")+"_Old";
         out.print(str);
         if (k<m-1)
          {
           out.println(" AND ");  
          }  
    }
    
    if (RSCRelIntType == 1)
    {          
        out.println(" AND (");    
        for (int i=0;i<m;i++)
        {
          str = "u." + AtributNameL[i].replaceAll(" ","_") + " IS NOT NULL";
          if (i<m-1)
          {
           str = str + (" AND ");  
          } 
          out.println(str);
        }
        out.println(")");           
    } 
    
    out.println();    
    out.println("GO");
    out.println();
  }
  void KaskadnoUpdatePRIL(PrintWriter out, String ImeTabeleL, String[] ImeKljucL,String[] AtributNameL, String[] AtributNameR,String[] ParamProcedure, String[] ParamProcPK,String ImeTabeleR, boolean alter, Connection conn)
  {
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
    if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.KaskadnoUpdPRI_" + ImeTabeleR+"]') and OBJECTPROPERTY(id, N'IsProcedure') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER PROCEDURE dbo.KaskadnoUpdPRI_" + ImeTabeleR.replaceAll(" ","_") + "(";
   }else
   {
     str = "CREATE PROCEDURE dbo.KaskadnoUpdPRI_" + ImeTabeleR.replaceAll(" ","_") + "(";
   }
    
    int m = AtributNameL.length;     
       
    for (int i=0;i<ParamProcedure.length;i++)
    {
      str = str + "@" + ParamProcedure[i]+",";
      
    }
    for (int i=0;i<ParamProcPK.length;i++)
    {
      str = str + " @" +ParamProcPK[i];
      if (i<ParamProcPK.length-1)
      {
           str = str + (",");  
      }
    }
    out.println(str+")");
    out.println("AS");    
   
    out.println("UPDATE u"); 
    out.println("SET ");
    for (int k=0;k<m;k++)
    {
         str = "u." + AtributNameL[k].replaceAll(" ","_") + " = @" + AtributNameR[k].replaceAll(" ","_");
         if (k<m-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }
    str = "FROM " + ImeTabeleL.replaceAll(" ","_") + " u";
    out.println(str);
    out.println("WHERE ");    
    for (int k=0;k<ImeKljucL.length;k++)
    {
         str = "u." + ImeKljucL[k].replaceAll(" ","_") + " = @" +ImeKljucL[k].replaceAll(" ","_");
         out.print(str);
         if (k<ImeKljucL.length-1)
          {
           out.println(" AND ");  
          }  
    }
    /*
    if (RSCRelIntType == 1)
    {          
        out.println(" AND (");    
        for (int i=0;i<m;i++)
        {
          str = "u." + AtributNameL[i] + " IS NOT NULL";
          if (i<m-1)
          {
           str = str + (" AND ");  
          } 
          out.println(str);
        }
        out.println(")");           
    } 
    */
    out.println();    
    out.println("GO");
    out.println();
  }
  void KaskadnoPRI(PrintWriter out, String ImeTabeleL, String[] ImeKljucL, String[] ParamProcedure, String ImeTabeleR, int RSCRelIntType, boolean alter, Connection conn)
  {
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
    if (alter)
   {
    if (RSCRelIntType == 1)
    {
      Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.KaskadnoDelPRI_"+ ImeTabeleR+"]') and OBJECTPROPERTY(id, N'IsProcedure') = 1";
    }else
    {
      Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.KaskadnoDelDFRI_"+ ImeTabeleR+"]') and OBJECTPROPERTY(id, N'IsProcedure') = 1";
    }
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER ";
   }else
   {
     str = "CREATE ";
   }
     if (RSCRelIntType == 1)
    {
      str =   str + "PROCEDURE dbo.KaskadnoDelPRI_"+ ImeTabeleR.replaceAll(" ","_") + "(";
    }else
    {
      str = str + "PROCEDURE dbo.KaskadnoDelDFRI_"+ ImeTabeleR.replaceAll(" ","_") + "(";
    }
    //str = "CREATE PROCEDURE dbo.KaskadnoDelPRI_"+ ImeTabeleR + "(";
    
    int n = ParamProcedure.length;
    for (int i=0;i<n;i++)
    {
      str = str + "@" + ParamProcedure[i];
      if (i<n-1)
      {
           str = str + (",");  
      } 
    }
    out.println(str+")");
    out.println("AS");    
    
    out.println("DELETE FROM "+ImeTabeleL.replaceAll(" ","_"));    
    out.println("WHERE ");
    for (int i=0;i<ImeKljucL.length;i++)
    {
          str = "(" + ImeKljucL[i].replaceAll(" ","_") + " = " + "@" + ImeKljucL[i].replaceAll(" ","_") + ")";        
          out.print(str);
          if (i<ImeKljucL.length-1)
          {
           out.println(" AND ");  
          }      
    }
    /*
    if (RSCRelIntType == 1)
    {          
        out.println(" AND (");    
        for (int i=0;i<m;i++)
        {
          str =  AtributNameL[i] + " IS NOT NULL";
          if (i<m-1)
          {
           str = str + (" AND ");  
          } 
          out.println(str);
        }
        out.println(")");           
    }    */ 
    out.println();    
    out.println("GO");
    out.println();
  }
  void KaskadnoDF(PrintWriter out, String ImeTabeleL, String[] AtributNameL,String[] AtributNameR, String[] ParamProcedure, String ImeTabeleR, boolean alter, Connection conn)
  {
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
    if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.KaskadnoDelDFRI_"+ ImeTabeleR+"]') and OBJECTPROPERTY(id, N'IsProcedure') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER PROCEDURE dbo.KaskadnoDelDFRI_"+ ImeTabeleR.replaceAll(" ","_") + "(";
   }else
   {
     str = "CREATE PROCEDURE dbo.KaskadnoDelDFRI_"+ ImeTabeleR.replaceAll(" ","_") + "(";
   }
      
    int n = ParamProcedure.length;
    for (int i=0;i<n;i++)
    {
      str = str + "@" + ParamProcedure[i];
      if (i<n-1)
      {
           str = str + (",");  
      } 
    }
    out.println(str+")");
    out.println("AS");    
    
    out.println("DELETE FROM "+ImeTabeleL.replaceAll(" ","_"));    
    out.println("WHERE ");
    for (int i=0;i<AtributNameL.length;i++)
    {
          str = "(" + AtributNameL[i].replaceAll(" ","_") + " = " + "@" + AtributNameR[i].replaceAll(" ","_") + ")";        
          out.print(str);
          if (i<AtributNameL.length-1)
          {
           out.println(" AND ");  
          }      
    }    
    out.println();    
    out.println("GO");
    out.println();
  }
  void DefaultPRI(PrintWriter out, String ImeTabeleL, String[] ImeKljucL,String[] AtributNameL, String[] ParamProcedure,String[] ParamDeklaracije, String ImeTabeleR, boolean alter, Connection conn)
  {
    String str = "";
    int m = AtributNameL.length;
    int n = ParamProcedure.length;
    int f = ParamDeklaracije.length;
    int s = ImeKljucL.length;   
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
    if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.SetDefaultPRI_"+ ImeTabeleR+"]') and OBJECTPROPERTY(id, N'IsProcedure') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER PROCEDURE dbo.SetDefaultPRI_"+ ImeTabeleR.replaceAll(" ","_") + "(";
   }else
   {
     str = "CREATE PROCEDURE dbo.SetDefaultPRI_"+ ImeTabeleR.replaceAll(" ","_") + "(";
   }
    for (int i=0;i<n;i++)
    {
      str = str + "@" + ParamProcedure[i];
      if (i<n-1)
      {
           str = str + (",");  
      } 
    }
    out.println(str+")");
    out.println("AS");    
    out.println("DECLARE");
    str = "";
    for (int i=0;i<f;i++)
    {
       str = "@" + ParamDeklaracije[i] + ",";
       
      out.println(str);
    } 
    out.println("@ObelezjaZaUpd NCHAR(255)");
    out.print("SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = "@" + AtributNameL[i].replaceAll(" ","_") + "= " + "u." + AtributNameL[i].replaceAll(" ","_");      
      if (i<m-1)
      {
        str = str + ", ";  
      }
      out.println(str);
    } 
    out.println("FROM " + ImeTabeleL.replaceAll(" ","_") + " u");
    out.println("WHERE ");
    for (int i=0;i<s;i++)
    {
      str = "(u." + ImeKljucL[i].replaceAll(" ","_") + " = " + "@" + ImeKljucL[i].replaceAll(" ","_") + ")";       
      out.print(str);
      if (i<s-1)
        {
           out.println(" AND ");  
        }      
    }
    out.println();    
    out.print("IF (");
    for (int i=0;i<m;i++)
    {
      str = "@" + AtributNameL[i].replaceAll(" ","_") + " IS NOT NULL";
         if (i<m-1)
         {
           str = str + (" OR ");  
         } 
         out.println(str);
    }
    out.println(")");
    out.println("BEGIN");
    
    for (int i=0;i<m;i++)
    {
      out.print("IF (");
      str = "@" + AtributNameL[i].replaceAll(" ","_") + " IS NOT NULL)";
      out.println(str);       
      out.println("BEGIN"); 
      out.println("IF @ObelezjaZaUpd != '' ");
      str = "SET @ObelezjaZaUpd = @ObelezjaZaUpd + '," + "u." + AtributNameL[i].replaceAll(" ","_") + " = default'" ;
      out.println(str);
      str = "ELSE SET @ObelezjaZaUpd = ' " + "u." + AtributNameL[i].replaceAll(" ","_") + " = default'";
      out.println(str);
      out.println("END");
    }
    str = "EXEC ('UPDATE u SET ' + @ObelezjaZaUpd + 'FROM " + ImeTabeleL.replaceAll(" ","_") + " u WHERE ";
    out.println(str);
    for (int g=0;g<ImeKljucL.length;g++)
    {
        str = "u." + ImeKljucL[g].replaceAll(" ","_") + " = ' + " + "@" + ImeKljucL[g].replaceAll(" ","_");        
        out.print(str);
        if (g<ImeKljucL.length-1)
        {
           out.println(" AND ");  
        }      
    }
    out.println(")");              
    out.print("SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = "@" + AtributNameL[i].replaceAll(" ","_") + "= " + "u." + AtributNameL[i].replaceAll(" ","_");      
      if (i<m-1)
      {
        str = str + ", ";  
      }
      out.println(str);
    } 
    out.println("FROM " + ImeTabeleL.replaceAll(" ","_") + " u");
    out.println("WHERE ");
    for (int i=0;i<s;i++)
    {
      str = "(u." + ImeKljucL[i].replaceAll(" ","_") + " = " + "@" + ImeKljucL[i].replaceAll(" ","_") + ")";       
      out.print(str);
      if (i<s-1)
        {
           out.println(" AND ");  
        }      
    }
    out.println();  
    String Argumenti="";    
    for (int k=0;k<m;k++)
    {      
      Argumenti = Argumenti + "@" + AtributNameL[k].replaceAll(" ","_");      
      if (k<m-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    } 
    out.println("IF dbo.SadrzavanjePRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");
    out.println("BEGIN");
    out.println("RAISERROR('Torka u ne moze da se brise iz relacije "+ImeTabeleR.replaceAll(" ","_")+"',16,1)");
    out.println("ROLLBACK TRAN");
    out.println("END");
    out.println("END");
    out.println("GO");
    out.println();
  }
  void SetNullPRI(PrintWriter out, String ImeTabeleL,String[] ImeKljucL, String[] AtributNameL, String[] ParamProcedure, String ImeTabeleR, boolean alter, Connection conn)
  {
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
    if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.SetNull_"+ ImeTabeleR+"]') and OBJECTPROPERTY(id, N'IsProcedure') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER PROCEDURE dbo.SetNull_"+ ImeTabeleR.replaceAll(" ","_") + "(";
   }else
   {
     str = "CREATE PROCEDURE dbo.SetNull_"+ ImeTabeleR.replaceAll(" ","_") + "(";
   }
    int m = AtributNameL.length;
    int n = ParamProcedure.length;
    int s= ImeKljucL.length;
    for (int i=0;i<n;i++)
    {
      str = str + "@" + ParamProcedure[i];
      if (i<n-1)
      {
           str = str + (",");  
      } 
    }
    out.println(str+")");
    out.println("AS");      
    out.println("UPDATE u"); 
    out.println("SET ");
    
    for (int k=0;k<m;k++)
    {
         str = "u." + AtributNameL[k].replaceAll(" ","_") + " = NULL";
         if (k<m-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }
    str = "FROM " + ImeTabeleL.replaceAll(" ","_") + " u";
    out.println(str);
    out.println("WHERE ");
    for (int i=0;i<s;i++)
    {
          str = "(u." + ImeKljucL[i].replaceAll(" ","_") + " = " + "@" + ImeKljucL[i].replaceAll(" ","_") + ")";        
          out.print(str);
          if (i<s-1)
          {
           out.println(" AND ");  
          }      
    }    
    /*
     * if (RSCRelIntType == 1)
    {          
        out.println(" AND (");    
        for (int i=0;i<m;i++)
        {
          str = "u." + AtributNameL[i] + " IS NOT NULL";
          if (i<m-1)
          {
           str = str + (" AND ");  
          } 
          out.println(str);
        }
        out.println(")");           
    }  */  
    out.println();    
    out.println("GO");
    out.println();
  }
  
  void SetNullDFRI(PrintWriter out, String ImeTabeleL,String[] AtributNameR, String[] AtributNameL, String[] ParamProcedure, String ImeTabeleR, boolean alter, Connection conn)
  {
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
    if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.SetNull_"+ ImeTabeleR+"]') and OBJECTPROPERTY(id, N'IsProcedure') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER PROCEDURE dbo.SetNull_"+ ImeTabeleR.replaceAll(" ","_") + "(";
   }else
   {
     str = "CREATE PROCEDURE dbo.SetNull_"+ ImeTabeleR.replaceAll(" ","_") + "(";
   }
    int m = AtributNameL.length;
    int n = ParamProcedure.length;
    int s= AtributNameL.length;
    for (int i=0;i<n;i++)
    {
      str = str + "@" + ParamProcedure[i];
      if (i<n-1)
      {
           str = str + (",");  
      } 
    }
    out.println(str+")");
    out.println("AS");      
    out.println("UPDATE u"); 
    out.println("SET ");
    
    for (int k=0;k<m;k++)
    {
         str = "u." + AtributNameL[k].replaceAll(" ","_") + " = NULL";
         if (k<m-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }
    str = "FROM " + ImeTabeleL.replaceAll(" ","_") + " u";
    out.println(str);
    out.println("WHERE ");
    for (int i=0;i<s;i++)
    {
          str = "(u." + AtributNameL[i].replaceAll(" ","_") + " = " + "@" + AtributNameR[i].replaceAll(" ","_") + ")";        
          out.print(str);
          if (i<s-1)
          {
           out.println(" AND ");  
          }      
    }
    
    out.println();    
    out.println("GO");
    out.println();
  }
  
  void SetDefaultDFRIRight(PrintWriter out, String ImeTabeleL,String[] AtributNameR, String[] AtributNameL, String[] ParamProcedure, String ImeTabeleR, boolean alter, Connection conn)
  {
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
    if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.SetDefault_R_"+ ImeTabeleR+"]') and OBJECTPROPERTY(id, N'IsProcedure') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER PROCEDURE dbo.SetDefault_R_"+ ImeTabeleR.replaceAll(" ","_") + "(";
   }else
   {
     str = "CREATE PROCEDURE dbo.SetDefault_R_"+ ImeTabeleR.replaceAll(" ","_") + "(";
   }
    int m = AtributNameL.length;
    int n = ParamProcedure.length;
    int s= AtributNameL.length;
    for (int i=0;i<n;i++)
    {
      str = str + "@" + ParamProcedure[i];
      if (i<n-1)
      {
           str = str + (",");  
      } 
    }
    out.println(str+")");
    out.println("AS");      
    out.println("UPDATE u"); 
    out.println("SET ");
    
    for (int k=0;k<m;k++)
    {
         str = "u." + AtributNameL[k].replaceAll(" ","_") + " = default";
         if (k<m-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }
    str = "FROM " + ImeTabeleL.replaceAll(" ","_") + " u";
    out.println(str);
    out.println("WHERE ");
    for (int i=0;i<s;i++)
    {
          str = "(u." + AtributNameL[i].replaceAll(" ","_") + " = " + "@" + AtributNameR[i].replaceAll(" ","_") + ")";        
          out.print(str);
          if (i<s-1)
          {
           out.println(" AND ");  
          }      
    }
    
    out.println();    
    out.println("GO");
    out.println();
  }
  
  void Default(PrintWriter out, String ImeTabeleL,String[] ImeKljucL, String[] AtributNameL, String[] ParamProcedure, String[] ParamDeklaracije,String ImeTabeleR, int RSCRelIntType, boolean alter, Connection conn)
  {
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
    if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.SetDefault_"+ ImeTabeleL+"]') and OBJECTPROPERTY(id, N'IsProcedure') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER PROCEDURE dbo.SetDefault_"+ ImeTabeleL.replaceAll(" ","_") + "(";
   }else
   {
     str = "CREATE PROCEDURE dbo.SetDefault_"+ ImeTabeleL.replaceAll(" ","_") + "(";
   }
    int m = AtributNameL.length;
    int n = ParamProcedure.length;
    int s= ImeKljucL.length;
    for (int i=0;i<n;i++)
    {
      str = str + "@" + ParamProcedure[i];
      if (i<n-1)
      {
           str = str + (",");  
      } 
    }
    out.println(str+")");
    out.println("AS");    
    out.println("DECLARE");
    str = "";
    for (int i=0;i<ParamDeklaracije.length ;i++)
    {
       str = "@" + ParamDeklaracije[i];
       if (i<ParamDeklaracije.length-1)
      {
           str = str + (",");  
      } 
      out.println(str);
    } 
    out.println("UPDATE u"); 
    out.println("SET ");
    for (int k=0;k<m;k++)
    {
         str = "u." + AtributNameL[k].replaceAll(" ","_") + " = default";
         if (k<m-1)
         {
           str = str + (",");  
         } 
         out.println(str);
    }
    str = "FROM " + ImeTabeleL.replaceAll(" ","_") + " u";
    out.println(str);
    out.println("WHERE ");
    for (int i=0;i<s;i++)
    {
          str = "(u." + ImeKljucL[i].replaceAll(" ","_") + " = " + "@" + ImeKljucL[i].replaceAll(" ","_") + ")";        
          out.print(str);
          if (i<s-1)
          {
           out.println(" AND ");  
          }      
    }
    out.println();
    out.print("SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = "@" + AtributNameL[i].replaceAll(" ","_") + "= " + "u." + AtributNameL[i].replaceAll(" ","_");      
      if (i<m-1)
      {
        str = str + ", ";  
      }
      out.println(str);
    } 
    out.println("FROM " + ImeTabeleL.replaceAll(" ","_") + " u");
    out.println("WHERE ");
    for (int i=0;i<s;i++)
    {
      str = "(u." + ImeKljucL[i].replaceAll(" ","_") + " = " + "@" + ImeKljucL[i].replaceAll(" ","_") + ")";       
      out.print(str);
      if (i<s-1)
        {
           out.println(" AND ");  
        }      
    }
    out.println();  
    String Argumenti="";    
    for (int k=0;k<m;k++)
    {      
      Argumenti = Argumenti + "@" + AtributNameL[k].replaceAll(" ","_");      
      if (k<m-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    } 
    if (RSCRelIntType == 2)
    {
        out.println("IF dbo.SadrzavanjeFRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");
    }else
    {
        out.println("IF dbo.SadrzavanjeDRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");
    }
    out.println("BEGIN");
    out.println("RAISERROR('Torka u ne moze da se brise iz relacije "+ImeTabeleR.replaceAll(" ","_")+"',16,1)");
    out.println("ROLLBACK TRAN");
    out.println("END");            
    out.println("GO");
    out.println();
  }
  public void BrisanjeOsnovniPRIR( PrintWriter out, PrintWriter outWorning, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int DelActionR, int[] IdAtributaL,int[] PKljucL, ClassFunction pom, int IdASistema, int IdSemaRel, Connection con, boolean alter, Connection conn)
  { //triger za brisanje desne strane za osnovni ref. int. PARCIJALNI
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeKljucL = pom.FAtributName(PKljucL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);        
    String[] ParamDeklaracijeL = pom.ParametriFunkcijeProcedure(IdAtributaL, con, IdProjekta, Rek,1);
    String[] ParamProcDefNull = pom.ParametriFunkcijeProcedure(PKljucL, con, IdProjekta, Rek,1);         
        
    if (Sadrzavanje == 0)
    {
      String[] ParamFunk = pom.ParametriFunkcijeProcedure(IdAtributaL, con, IdProjekta, Rek,1);
      SadrzavanjePRI(out, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, ParamFunk, alter,conn);
      Sadrzavanje = 1;
    }
    /*if (DelActionR == -1 && BezAkcije == 0)
    {
      SprecPRI(out, ImeTabeleL, ImeAL, ImeAR, ParamProc, ImeTabeleR, RSCRelIntType);
      BezAkcije = 1;
    } else*/
    if (DelActionR == -2)
    {
      if (SetNullRP == 0 && RSCRelIntType == 1)
      {
        SetNullPRI(out, ImeTabeleL,ImeKljucL, ImeAL, ParamProcDefNull, ImeTabeleR, alter, conn);
        SetNullRP = 1;
      }else if (SetNullRFD ==0 && RSCRelIntType != 1)
      {
        SetNullPRI(out, ImeTabeleL,ImeKljucL, ImeAL, ParamProcDefNull, ImeTabeleR, alter, conn);
        SetNullRFD = 1;
      }
    } else
    if (DelActionR == -3)
    {
      if (SetDefaultP == 0 && RSCRelIntType == 1)
      {
        DefaultPRI(out, ImeTabeleL,ImeKljucL, ImeAL, ParamProcDefNull,ParamDeklaracijeL, ImeTabeleR, alter, conn); //vazi samo za parcijalni
        SetDefaultP = 1;
      }else if (SetDefaultFD == 0 && RSCRelIntType != 1)
      {
        Default(out, ImeTabeleL,ImeKljucL, ImeAL, ParamProcDefNull,ParamDeklaracijeL, ImeTabeleR, RSCRelIntType, alter, conn); //vazi samo za parcijalni
        SetDefaultFD = 1;
      }
    } else
    if (DelActionR == -4)
    {    
      if (KaskadnoP == 0 && RSCRelIntType == 1)
      {
        KaskadnoPRI(out, ImeTabeleL, ImeKljucL, ParamProcDefNull, ImeTabeleR, RSCRelIntType, alter, conn);
        KaskadnoP = 1; 
      }else if (KaskadnoFD == 0 && RSCRelIntType != 1)
      {
        KaskadnoPRI(out, ImeTabeleL, ImeKljucL, ParamProcDefNull, ImeTabeleR, RSCRelIntType, alter, conn);
        KaskadnoFD = 1;
      }
    }   
    
    String str="";
    ResultSet rs;
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   { 
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[TRG_"+ ImeTabeleR+"_"+ImeOgranicenja+"_DEL"+"]') and OBJECTPROPERTY(id, N'IsTrigger') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      out.println("ALTER TRIGGER TRG_"+ ImeTabeleR.replaceAll(" ","_")+"_"+ImeOgranicenja+"_DEL");
    }else
    {
      out.println("CREATE TRIGGER TRG_"+ ImeTabeleR.replaceAll(" ","_")+"_"+ImeOgranicenja+"_DEL");
    }
        
    out.println("   ON "+ ImeTabeleR.replaceAll(" ","_"));    
    out.println("   FOR DELETE");
    out.println("AS");
    int m = IdAtributaR.length;  
    String[] AtributNameR = new String[m];
    String[] AtributNameL = new String[m];
    try {
    out.print("   DECLARE ");    
    for (int i=0;i<m;i++)
    {
      String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaR[i];
      rs=query.select(upit);
      if (rs.next())
      {
        AtributNameR[i] = rs.getString("Att_mnem");
        out.print("   @" + AtributNameR[i].replaceAll(" ","_") + "R");        
        int IdDomena = rs.getInt("Dom_id"); 
        query.Close();        
        String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta,1); 
        int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);        
        if (Duzina!=0)            
        {
              TipPodatka = TipPodatka + "(" + Duzina + ")";               
        }
        out.println(" " + TipPodatka + ",");        
                 
      } 
    }
    for (int i=0;i<m;i++)
    {
      String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaL[i];
      rs=query.select(upit);
      if (rs.next())
      {
        AtributNameL[i] = rs.getString("Att_mnem");
        out.print("   @" + AtributNameL[i].replaceAll(" ","_") + "L");        
        int IdDomena = rs.getInt("Dom_id"); 
        query.Close();        
        String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta,1); 
        int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);        
        if (Duzina!=0)            
        {
              TipPodatka = TipPodatka + "(" + Duzina + ")";               
        }
        str = " " + TipPodatka;  
        if (DelActionR == -1)
        {
          if (i<m-1)
          {
            str = str + ", ";  
          }
        }else 
        {
          str = str + ",";
        }
        out.println(str);         
      } 
    }     
    
    if (DelActionR != -1)
    {
    for (int i=0;i<ParamProcDefNull.length;i++)
    {
      str = "   @" + ParamProcDefNull[i];
      if (i<ParamProcDefNull.length-1)
      {
           str = str + (",");  
      }
      out.println(str);
    }
    }
    str = "   DECLARE Cursor_" + ImeTabeleR.replaceAll(" ","_") + " CURSOR";
    out.println(str);
    out.println("   FOR");
    out.print("   SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = AtributNameR[i].replaceAll(" ","_");      
      if (i<m-1)
      {
        str = str + ", ";  
      }
      out.print(str);
    } 
    
    out.println();
    out.println("   FROM Deleted");   
    
    out.println("   OPEN " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleR.replaceAll(" ","_") + " INTO "; 
    out.println(str);
    out.print("   ");
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = "@" + AtributNameR[i].replaceAll(" ","_") + "R";
      if (i<AtributNameR.length-1)
      {
        str = str + ", ";  
      }
      out.print(str);      
    }
    out.println();
    out.println("   WHILE @@FETCH_STATUS=0");
    out.println("   BEGIN"); 
    str = "   DECLARE Cursor_" + ImeTabeleL.replaceAll(" ","_") + " CURSOR";
    out.println(str);
    out.println("   FOR");
    out.print("   SELECT ");
    for (int i=0;i<AtributNameL.length;i++)
    {
      if (DelActionR != -1)
      {
        str = AtributNameL[i].replaceAll(" ","_") + ",";  
        out.print(str);
      } else
      {
        str = AtributNameL[i].replaceAll(" ","_");
        if (i<AtributNameL.length-1)
        {
            str = str + ", ";
        }
        out.print(str);
      }      
    }
        
    if (DelActionR != -1)
    {
    out.println();
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "   " + ImeKljucL[i].replaceAll(" ","_");
      if (i<ImeKljucL.length-1)
      {
           str = str + (",");  
      } 
      out.print(str);
    }
    }
    out.println();
    str = "   FROM " + ImeTabeleL.replaceAll(" ","_") + " WHERE "; 
    out.println(str);
    if (RSCRelIntType == 1)
    {
    for (int i=0;i<AtributNameL.length;i++)
    {
      str = "   (" + AtributNameL[i].replaceAll(" ","_") + " = @" + AtributNameR[i].replaceAll(" ","_") + "R" + " OR " + AtributNameL[i].replaceAll(" ","_") + " IS NULL)";      
      if (i<AtributNameL.length-1)
      {
           str = str + (" AND ");  
      } 
      out.println(str);
    }
    }else
    {
    for (int i=0;i<AtributNameL.length;i++)
    {
      str = "   " + AtributNameL[i].replaceAll(" ","_") + " = @" + AtributNameR[i].replaceAll(" ","_") + "R";      
      if (i<AtributNameL.length-1)
      {
           str = str + (" AND ");  
      } 
      out.println(str);
    }
    }
    out.println("   OPEN " + "Cursor_" + ImeTabeleL.replaceAll(" ","_"));
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleL.replaceAll(" ","_") + " INTO "; 
    out.println(str);    
    for (int i=0;i<AtributNameL.length;i++)
    {
      if (DelActionR != -1)
      {
        str = "   @" + AtributNameL[i].replaceAll(" ","_") + "L,";  
        out.print(str);
      }else
      {
        str = "   @" + AtributNameL[i].replaceAll(" ","_") + "L";
        if (i<AtributNameL.length-1)
        {
           str = str + (",");  
        } 
        out.print(str);
      }            
    }
         
    if (DelActionR != -1)
    {
    out.println();
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "   @" + ImeKljucL[i].replaceAll(" ","_");
      if (i<ImeKljucL.length-1)
      {
           str = str + (",");  
      } 
      out.print(str);
    }
    }
    out.println();
    out.println("   WHILE @@FETCH_STATUS=0");
    out.println("   BEGIN");    
    String ArgumentiF="";    
    for (int k=0;k<AtributNameL.length;k++)
    {      
      ArgumentiF = ArgumentiF + "@" + AtributNameL[k].replaceAll(" ","_") + "L";
      
      if (k<AtributNameL.length-1)
      {
        ArgumentiF = ArgumentiF + ", ";  
      }
    }    
    if (RSCRelIntType == 1)
    {
      out.print("   IF NOT (");
      for (int k=0;k<AtributNameL.length;k++)
      {
          str = "   @" + AtributNameL[k].replaceAll(" ","_") + "L"+ " IS NULL ";          
          if (k<AtributNameL.length-1)
          {
              str = str + " AND ";
          }
          out.print(str);
      }
      out.println(")");
      out.println("   BEGIN");
      out.println("   IF dbo.SadrzavanjePRI_" + ImeTabeleL.replaceAll(" ","_")+"(" + ArgumentiF +")=0");         
    }    
    String Argumenti1="";     
    for (int k=0;k<ImeKljucL.length;k++)
    {      
      Argumenti1 = Argumenti1 + "@" + ImeKljucL[k].replaceAll(" ","_");
      
      if (k<ImeKljucL.length-1)
      {
        Argumenti1 = Argumenti1 + ", ";  
      }
    } 
    if (DelActionR == -1)
    {
      out.println("BEGIN");
      out.println("RAISERROR('Torka u ne moze da se brise iz relacije "+ImeTabeleR.replaceAll(" ","_")+"',16,1)");
      out.println("ROLLBACK TRAN");
      out.println("END");
      //out.println("   exec dbo.SprecPRI_"+ImeTabeleR + " ");
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
      out.println("   exec dbo.SetNull_"+ImeTabeleR.replaceAll(" ","_") + " " + Argumenti1);
    } else
    if (DelActionR == -3)
    {
      if (RSCRelIntType == 1)    
      {
        out.println("   exec dbo.SetDefaultPRI_"+ImeTabeleR .replaceAll(" ","_")+ " " + Argumenti1);
      }else
      {
        out.println("   exec dbo.SetDefault_"+ImeTabeleR.replaceAll(" ","_") + " " + Argumenti1);
      }
    } else
    {
      out.println("   exec dbo.KaskadnoDelPRI_" + ImeTabeleR.replaceAll(" ","_") + " " + Argumenti1);
    }   
    out.println("   END");
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleL.replaceAll(" ","_") + " INTO "; 
    out.println(str);
    
    for (int i=0;i<AtributNameL.length;i++)
    {
      if (DelActionR != -1)
      {
        str = "   @" + AtributNameL[i].replaceAll(" ","_") + "L,";   
        out.print(str); 
      }else
      {
        str = "   @" + AtributNameL[i].replaceAll(" ","_") + "L";
        if (i<AtributNameL.length-1)
        {
           str = str + (",");  
        }
        out.print(str); 
      }           
    }
         
    if (DelActionR != -1)
    {
    out.println();
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "   @" + ImeKljucL[i].replaceAll(" ","_");
      if (i<ImeKljucL.length-1)
      {
           str = str + (",");  
      } 
      out.print(str);
    }
    }
    out.println();
    out.println("   END");
    out.println("   CLOSE " + "Cursor_" + ImeTabeleL.replaceAll(" ","_"));
    out.println("   DEALLOCATE " + "Cursor_" + ImeTabeleL.replaceAll(" ","_"));
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleR.replaceAll(" ","_") + " INTO "; 
    out.println(str);
    
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = "   @" + AtributNameR[i].replaceAll(" ","_") + "R";
      if (i<AtributNameR.length-1)
      {
        str = str + ", ";  
      }
      out.print(str);      
    }
    out.println();
    out.println("   END");
    out.println("   CLOSE " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    out.println("   DEALLOCATE " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    out.println("GO");
    out.println();
  }
       
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }
  }
  void PostojiFunkcija(PrintWriter out, String ImeTabeleL, String[] AtributNameL, String[] AtributNameR, String[] ParamProcedure, int RSCRelIntType, boolean alter, Connection conn)
  {
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
    if (alter)
   {
    if (RSCRelIntType == 0 || RSCRelIntType == 2)
    {
      Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.PostojiFDRI_"+ ImeTabeleL+"]') and OBJECTPROPERTY(id, N'IsScalarFunction') = 1";
    }else
    {
      Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.PostojiPRI_"+ ImeTabeleL+"]') and OBJECTPROPERTY(id, N'IsScalarFunction') = 1";
    }
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER ";
   }else
   {
     str = "CREATE ";
   }
   
    if (RSCRelIntType == 0 || RSCRelIntType == 2)
    {
          str = str + "FUNCTION dbo.PostojiFDRI_"+ ImeTabeleL.replaceAll(" ","_") + "(";
    }else
    {
          str = str + "FUNCTION dbo.PostojiPRI_"+ ImeTabeleL.replaceAll(" ","_") + "(";
    }
    
    int m = AtributNameL.length; 
    int n = ParamProcedure.length;
    for (int i=0;i<n;i++)
    {
      str = str + "@" + ParamProcedure[i];
      if (i<n-1)
      {
           str = str + (",");  
      } 
    }    
    out.println(str+")");
    out.println("RETURNS int");
    out.println("AS"); 
    out.println("BEGIN");
    out.println("   DECLARE @Count int,");
    out.println("   @Ret  int");
    out.println("SELECT @Count = COUNT(*) FROM "+ImeTabeleL.replaceAll(" ","_")+" u");    
    out.println("WHERE ");
    for (int i=0;i<m;i++)
    {        
        if (RSCRelIntType == 0 || RSCRelIntType == 2)
        {
          str = "(u." + AtributNameL[i].replaceAll(" ","_") + " = " + "@" + AtributNameR[i].replaceAll(" ","_") + ")";
        }else
        {
         str = "(u." + AtributNameL[i].replaceAll(" ","_") + " IS NULL OR " + "u." + AtributNameL[i].replaceAll(" ","_") + " = " + "@" + AtributNameR[i].replaceAll(" ","_") + ")";
        }      
        out.print(str);
        if (i<m-1)
        {
           out.println(" AND ");  
        }     
    }
    if (RSCRelIntType == 1)
    {
      out.print(" AND (");
      for (int i=0;i<m;i++)
      {
        str = "u." + AtributNameL[i].replaceAll(" ","_") + " IS NOT NULL ";
        out.print(str);
        if (i<m-1)
        {
           out.println(" AND ");  
        }          
      }
      out.print(")");
    }
    out.println();
    out.println("IF @Count != 0 ");
    out.println("SET @ret=1");
    out.println("ELSE");
    out.println("SET @ret=0");
    out.println("RETURN @ret");
    out.println("END");    
    out.println("GO");
    out.println();
  }
  
  public void BrisanjeOsnovniDFRIR(PrintWriter out, PrintWriter outWorning, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int DelActionR, int[] IdAtributaL,int[] PKljucL, ClassFunction pom, int IdASistema, int IdSemaRel, Connection con, boolean alter, Connection conn)
  { //triger za brisanje desne strane za osnovni ref. int. full i default
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeKljucL = pom.FAtributName(PKljucL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);        
    String[] ParamDeklaracijeR = pom.ParametriFunkcijeProcedure(IdAtributaR, con, IdProjekta, Rek,1);
            
    String[] ParamProc = pom.ParametriFunkcijeProcedure(IdAtributaR, con, IdProjekta, Rek,1);    
    if (Postoji==0)
    {
      PostojiFunkcija(out, ImeTabeleL, ImeAL, ImeAR, ParamProc, RSCRelIntType, alter, conn);  
      Postoji =1;
    }    
    
    /*if (DelActionR == -1 && BezAkcije == 0)
    {
      SprecPRI(out, ImeTabeleL, ImeAL, ImeAR, ParamProc, ImeTabeleR, RSCRelIntType);
      BezAkcije = 1;
    } else*/
    if (DelActionR == -2)
    {
      if (SetNullRFD ==0 && RSCRelIntType != 1)
      {
        SetNullDFRI(out, ImeTabeleL,ImeAR, ImeAL, ParamDeklaracijeR, ImeTabeleR, alter, conn);
        SetNullRFD = 1;
      }
    } else
    if (DelActionR == -3)
    {
      if (SetDefaultFDR == 0 && RSCRelIntType != 1)
      {
        SetDefaultDFRIRight(out, ImeTabeleL,ImeAR, ImeAL, ParamDeklaracijeR, ImeTabeleR, alter, conn);
        SetDefaultFDR = 1;
      }
    } else
    if (DelActionR == -4)
    {    
      if (KaskadnoFD == 0 && RSCRelIntType != 1)
      {
        KaskadnoDF(out, ImeTabeleL, ImeAL, ImeAR, ParamProc, ImeTabeleR, alter, conn);
        KaskadnoFD = 1;
      }
    }   
    
    String str="";
    ResultSet rs;
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
    if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[TRG_"+ ImeTabeleR+"_"+ImeOgranicenja+"_DEL"+"]') and OBJECTPROPERTY(id, N'IsTrigger') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      out.println("ALTER TRIGGER TRG_"+ ImeTabeleR.replaceAll(" ","_")+"_"+ImeOgranicenja+"_DEL");  
    }else
    {
      out.println("CREATE TRIGGER TRG_"+ ImeTabeleR.replaceAll(" ","_")+"_"+ImeOgranicenja+"_DEL");  
    }      
    out.println("   ON "+ ImeTabeleR.replaceAll(" ","_"));    
    out.println("   FOR DELETE");
    out.println("AS");
    int m = IdAtributaR.length;  
    String[] AtributNameR = new String[m];
    
    try {
    out.print("   DECLARE ");    
    for (int i=0;i<m;i++)
    {
      String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaR[i];
      rs=query.select(upit);
      if (rs.next())
      {
        AtributNameR[i] = rs.getString("Att_mnem");
        out.print("   @" + AtributNameR[i].replaceAll(" ","_") + "R");        
        int IdDomena = rs.getInt("Dom_id"); 
        query.Close();        
        String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta,1); 
        int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);        
        if (Duzina!=0)            
        {
              TipPodatka = TipPodatka + "(" + Duzina + ")";               
        }
        out.print(" " + TipPodatka); 
        if (i<m-1)
        {
          out.println(",");        
        }        
      } 
    } 
    /*
    if (DelActionR != -1)
    {
    for (int i=0;i<ParamProcDefNull.length;i++)
    {
      str = "   @" + ParamProcDefNull[i];
      if (i<ParamProcDefNull.length-1)
      {
           str = str + (",");  
      }
      out.println(str);
    }
    }*/
    out.println();
    out.println("   BEGIN"); 
    str = "   DECLARE Cursor_" + ImeTabeleR.replaceAll(" ","_") + " CURSOR";
    out.println(str);
    out.println("   FOR");
    out.print("   SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = AtributNameR[i].replaceAll(" ","_");      
      if (i<m-1)
      {
        str = str + ", ";  
      }
      out.print(str);
    }     
    out.println();
    out.println("   FROM Deleted");    
      
    String ArgumentiF="";    
    for (int k=0;k<AtributNameR.length;k++)
    {      
      ArgumentiF = ArgumentiF + "@" + AtributNameR[k].replaceAll(" ","_") + "R";
      
      if (k<m-1)
      {
        ArgumentiF = ArgumentiF + ", ";  
      }
    }    
    String Argumenti="";    
    for (int k=0;k<AtributNameR.length;k++)
    {      
      Argumenti = Argumenti + "@" + AtributNameR[k].replaceAll(" ","_") + "R";
      
      if (k<m-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    }  
    out.println("   OPEN " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleR.replaceAll(" ","_") + " INTO "; 
    out.println(str);    
    out.println(Argumenti);
    out.println("   WHILE @@FETCH_STATUS=0");
    out.println("   BEGIN");
    if (DelActionR == -3 || DelActionR == -1)
    {
      out.println("   BEGIN TRANSACTION");  
    }
    out.println("   IF dbo.PostojiFDRI_"+ ImeTabeleL.replaceAll(" ","_") +"(" + ArgumentiF +")=1");  
    
    String Argumenti1="";     
    for (int k=0;k<ImeKljucL.length;k++)
    {      
      Argumenti1 = Argumenti1 + "@" + ImeKljucL[k].replaceAll(" ","_");
      
      if (k<ImeKljucL.length-1)
      {
        Argumenti1 = Argumenti1 + ", ";  
      }
    } 
    if (DelActionR == -1)
    {
      out.println("BEGIN");
      out.println("RAISERROR('Torka u ne moze da se brise iz relacije "+ImeTabeleR.replaceAll(" ","_")+"',16,1)");
      out.println("ROLLBACK TRAN");
      out.println("END");
      //out.println("   exec dbo.SprecPRI_"+ImeTabeleR + " ");
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
      out.println("   exec dbo.SetNull_"+ImeTabeleR.replaceAll(" ","_") + " " + ArgumentiF);
    } else
    if (DelActionR == -3)
    {     
        out.println("   exec dbo.SetDefault_R_"+ImeTabeleR.replaceAll(" ","_") + " " + ArgumentiF);
      
    } else if (DelActionR == -4)
    {
      out.println("   exec dbo.KaskadnoDelDFRI_" + ImeTabeleR.replaceAll(" ","_") + " " + ArgumentiF);
    }    
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleR.replaceAll(" ","_") + " INTO "; 
    out.println(str);
    out.println(Argumenti);
    out.println("END");
    out.println("   CLOSE " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    out.println("   DEALLOCATE " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    out.println("   END");
    out.println("GO");
    out.println();
  }
       
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }
  }
  
 /*
  public void UpdateOsnovniRefIFulIDefR(JDBCQuery query, PrintWriter out, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, rekurzije Rek, int RSCRelIntType, int ModActionR, int[] IdAtributaL)
  {  
    String[] ImeAL = FAtributName(IdAtributaL, query, IdProjekta, Rek);
    String[] ImeAR = FAtributName(IdAtributaR, query, IdProjekta, Rek);
    String[] ADefault = FDefault(IdAtributaL, query, IdProjekta, Rek);
    String[] ParamProc = ParametriFunkcijeProcedure(IdAtributaR, query, IdProjekta, Rek);    
    if (Postoji==0)
    {
      PostojiFunkcija(out, ImeTabeleL, ImeAL, ImeAR, ParamProc, ImeTabeleR, RSCRelIntType);  
      Postoji =1;
    }  
    String str;
    ResultSet rs;
    out.println("CREATE TRIGGER TRG_"+ ImeTabeleR+"_"+ImeOgranicenja+"_UPD");    
    out.println("   ON "+ ImeTabeleR);    
    out.println("   FOR UPDATE");
    out.println("AS");
    int m = IdAtributaR.length;  
    String[] AtributName = new String[m];
    try {
    out.print("   DECLARE ");        
    for (int i=0;i<m;i++)
    {
      String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaR[i];
      rs=query.select(upit);
      if (rs.next())
      {
        AtributName[i] = rs.getString("Att_mnem");
        //out.print("   @" + AtributName[i] + "_old");       
        int IdDomena = rs.getInt("Dom_id"); 
        query.Close();        
        String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta); 
        int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);        
        if (Duzina!=0)            
        {
              TipPodatka = TipPodatka + "(" + Duzina + ")";               
        }
        //out.print(" " + TipPodatka + ",");  
        out.print("   @" + AtributName[i] + "_new");
        out.print(" " + TipPodatka);
        if (i<m-1)
        {
           out.println(",");  
        }else{
          out.println();
        }        
      } 
    }
    out.print("SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = "@" + AtributName[i] + "_new" + " = " + AtributName[i];      
      if (i<m-1)
      {
        str = str + ", ";  
      }
      out.println(str);
    } 
    out.println("FROM Inserted");    
    String Argumenti="";    
    for (int k=0;k<m;k++)
    {      
      Argumenti = Argumenti + "@" + AtributName[k] + "_new";
      
      if (k<m-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    }        
    str = "";
    out.print("IF ");
    for (int i=0;i<m;i++)
    {
        str = "UPDATE(" + AtributName[i] + ")";
        out.print(str);
        if (i<m-1)
        {
           out.println(" OR ");  
        }
    }
    out.println();
    out.println("BEGIN");
    
    out.println("IF dbo.PostojiFDRI_"+ImeTabeleL+"(" + Argumenti + ")" + " = 1 ");    
    
    out.println("BEGIN");
    int n = IdAtributaL.length;
    if (ModActionR == -1)
    {       
      out.println("RAISERROR('Torka u ne moze da se modifikuje u semi "+ImeTabeleR+"',16,1)");
      out.println("ROLLBACK TRAN");                
    } else
    if (ModActionR == -4)
    {
      out.println("UPDATE u");
      out.println("SET");
      for (int i=0;i<n;i++)
      {
        str = "u." + ImeAL[i] + " = " + AtributName[i] + "_new";        
        if (i<n-1)
        {
          str = str + ", ";  
        }
        out.println(str);
      }
      str = "FROM " + ImeTabeleL + " u";
      out.println(str);
    } else
    if (ModActionR == -3)
    {
      out.println("UPDATE u");
      out.println("SET");
      for (int i=0;i<n;i++)
      {
        str = "u." + ImeAL[i] + " = default" ;       
        if (i<n-1)
        {
          str = str + ", ";  
        }
        out.println(str);
      }
      str = "FROM " + ImeTabeleL + " u";
      out.println(str);      
    } else
    {      
      out.println("UPDATE u");
      out.println("SET");
      for (int i=0;i<n;i++)
      {
        str = "u." + ImeAL[i] + " = null";        
        if (i<n-1)
        {
          str = str + ", ";  
        }
        out.println(str);
      }
      str = "FROM " + ImeTabeleL + " u";
      out.println(str);     
    }    
    out.println("WHERE ");
    for (int i=0;i<n;i++) //i ovo nije ako se updejtuju samo oni koji se skroz slazu????
    {
        
        str = "(u." + ImeAL[i] + " = " + "@" + AtributName[i] + "_new" + ")";        
        out.print(str);
        if (i<m-1)
        {
           out.println(" AND ");  
        }      
    }
    out.println();  
    if (ModActionR == -3)
    {  
      if (RSCRelIntType == 2)
      {
        out.println("IF dbo.SadrzavanjeFRI_"+ImeTabeleL+"(" + Argumenti + ")" + " = 0 ");
      }else
      {
      out.println("IF dbo.SadrzavanjeDRI_"+ImeTabeleL+"(" + Argumenti + ")" + " = 0 ");
      }
      out.println("BEGIN");
      out.println("RAISERROR('Torka u ne moze da se brise iz seme "+ImeTabeleR+"',16,1)");
      out.println("ROLLBACK TRAN");
      out.println("END");
    }  
    out.println("END");
    out.println("END");
    out.println("GO");
    out.println();
  }
       
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }
  }
  public void DeleteOsnovniFDRI(JDBCQuery query, PrintWriter out, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, rekurzije Rek, int RSCRelIntType, int ModActionR, int[] IdAtributaL)
  {  
    String[] ImeAL = Pom.FAtributName(IdAtributaL, query, IdProjekta, Rek);
    String[] ImeAR = Pom.FAtributName(IdAtributaR, query, IdProjekta, Rek);
    String[] ADefault = Pom.FDefault(IdAtributaL, query, IdProjekta, Rek);
    String[] ParamProc = Pom.ParametriFunkcijeProcedure(IdAtributaR, query, IdProjekta, Rek);    
    if (Postoji==0)
    {
      PostojiFunkcija(out, ImeTabeleL, ImeAL, ImeAR, ParamProc, ImeTabeleR, RSCRelIntType);  
      Postoji =1;
    }  
    String str;
    ResultSet rs;
    out.println("CREATE TRIGGER TRG_"+ ImeTabeleR+"_"+ImeOgranicenja+"_DEL");    
    out.println("   ON "+ ImeTabeleR);    
    out.println("   FOR DELETE");
    out.println("AS");
    int m = IdAtributaR.length;  
    String[] AtributName = new String[m];
    try {
    out.print("   DECLARE ");        
    for (int i=0;i<m;i++)
    {
      String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaR[i];
      rs=query.select(upit);
      if (rs.next())
      {
        AtributName[i] = rs.getString("Att_mnem");
        //out.print("   @" + AtributName[i] + "_old");       
        int IdDomena = rs.getInt("Dom_id"); 
        query.Close();        
        String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta); 
        int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);        
        if (Duzina!=0)            
        {
              TipPodatka = TipPodatka + "(" + Duzina + ")";               
        }
        //out.print(" " + TipPodatka + ",");  
        out.print("   @" + AtributName[i] + "_new");
        out.print(" " + TipPodatka);
        if (i<m-1)
        {
           out.println(",");  
        }else{
          out.println();
        }        
      } 
    }
    out.print("SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = "@" + AtributName[i] + "_new" + " = " + AtributName[i];      
      if (i<m-1)
      {
        str = str + ", ";  
      }
      out.println(str);
    } 
    out.println("FROM Inserted");    
    String Argumenti="";    
    for (int k=0;k<m;k++)
    {      
      Argumenti = Argumenti + "@" + AtributName[k] + "_new";
      
      if (k<m-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    }        
    str = "";
    
    
    out.println("IF dbo.PostojiFDRI_"+ImeTabeleL+"(" + Argumenti + ")" + " = 1 ");    
    
    out.println("BEGIN");
    int n = IdAtributaL.length;
    if (ModActionR == -1)
    {       
      out.println("RAISERROR('Torka u ne moze da se modifikuje u semi "+ImeTabeleR+"',16,1)");
      out.println("ROLLBACK TRAN");                
    } else
    if (ModActionR == -4)
    {
      out.println("UPDATE u");
      out.println("SET");
      for (int i=0;i<n;i++)
      {
        str = "u." + ImeAL[i] + " = " + AtributName[i] + "_new";        
        if (i<n-1)
        {
          str = str + ", ";  
        }
        out.println(str);
      }
      str = "FROM " + ImeTabeleL + " u";
      out.println(str);
    } else
    if (ModActionR == -3)
    {
      out.println("UPDATE u");
      out.println("SET");
      for (int i=0;i<n;i++)
      {
        str = "u." + ImeAL[i] + " = default" ;       
        if (i<n-1)
        {
          str = str + ", ";  
        }
        out.println(str);
      }
      str = "FROM " + ImeTabeleL + " u";
      out.println(str);      
    } else
    {      
      out.println("UPDATE u");
      out.println("SET");
      for (int i=0;i<n;i++)
      {
        str = "u." + ImeAL[i] + " = null";        
        if (i<n-1)
        {
          str = str + ", ";  
        }
        out.println(str);
      }
      str = "FROM " + ImeTabeleL + " u";
      out.println(str);     
    }    
    out.println("WHERE ");
    for (int i=0;i<n;i++) 
    {
        
        str = "(u." + ImeAL[i] + " = " + "@" + AtributName[i] + "_new" + ")";        
        out.print(str);
        if (i<m-1)
        {
           out.println(" AND ");  
        }      
    }
    out.println();  
    if (ModActionR == -3)
    {  
      if (RSCRelIntType == 2)
      {
        out.println("IF dbo.SadrzavanjeFRI_"+ImeTabeleL+"(" + Argumenti + ")" + " = 0 ");
      }else
      {
      out.println("IF dbo.SadrzavanjeDRI_"+ImeTabeleL+"(" + Argumenti + ")" + " = 0 ");
      }
      out.println("BEGIN");
      out.println("RAISERROR('Torka u ne moze da se brise iz seme "+ImeTabeleR+"',16,1)");
      out.println("ROLLBACK TRAN");
      out.println("END");
    }  
    out.println("END");
    
    out.println("GO");
    out.println();
  }
       
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }
  }*/
  public void UpdateOsnovniPRIR(PrintWriter out, PrintWriter outWorning, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int ModActionR, int[] IdAtributaL, int[] PKljucL, ClassFunction pom,int IdASistema, int IdSemaRel, Connection con, boolean alter, Connection conn)
  { // triger za update desne strane parcijalni
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeKljucL = pom.FAtributName(PKljucL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);    
    String[] ParamDeklaracijeL = pom.ParametriFunkcijeProcedure(IdAtributaL, con, IdProjekta, Rek,1);
    String[] ParamProcDefNull = pom.ParametriFunkcijeProcedure(PKljucL, con, IdProjekta, Rek,1);
    String[] ParamProcKaskadno = pom.ParametriFunkcijeProcedure(IdAtributaR, con, IdProjekta, Rek,1);
    if (Sadrzavanje == 0)
    {
      String[] ParamFunk = pom.ParametriFunkcijeProcedure(IdAtributaL, con, IdProjekta, Rek,1);
      SadrzavanjePRI(out, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, ParamFunk, alter, conn);
      Sadrzavanje = 1;
    }
        
    /*if (ModActionR == -1 && BezAkcije == 0)
    {
      SprecPRI(out, ImeTabeleL, ImeAL, ImeAR, ParamProc, ImeTabeleR, RSCRelIntType);
      BezAkcije = 1;
    } else*/
    if (ModActionR == -2 && SetNullRP == 0)
    {
      if (SetNullRP == 0 && RSCRelIntType == 1)
      {  
        SetNullPRI(out, ImeTabeleL,ImeKljucL, ImeAL, ParamProcDefNull, ImeTabeleR, alter, conn);
        SetNullRP = 1;
      }else if (SetNullRFD ==0 && RSCRelIntType != 1)
      {
        SetNullPRI(out, ImeTabeleL,ImeKljucL, ImeAL, ParamProcDefNull, ImeTabeleR, alter, conn);
        SetNullRFD = 1;
      }      
    } else
    if (ModActionR == -3)
    {
      if (SetDefaultP == 0 && RSCRelIntType == 1)
      {
        DefaultPRI(out, ImeTabeleL,ImeKljucL, ImeAL, ParamProcDefNull,ParamDeklaracijeL, ImeTabeleR, alter, conn); //vazi samo za parcijalni
        SetDefaultP = 1;
      }else if (SetDefaultFD == 0 && RSCRelIntType != 1)
      {
        Default(out, ImeTabeleL,ImeKljucL, ImeAL, ParamProcDefNull,ParamDeklaracijeL, ImeTabeleR, RSCRelIntType, alter, conn); //vazi samo za parcijalni
        SetDefaultFD = 1;
      }
      
    } else
    if (ModActionR == -4)
    {       
      if (KaskadnoUpP == 0 && RSCRelIntType == 1)
      {        
        KaskadnoUpdatePRIL(out, ImeTabeleL, ImeKljucL, ImeAL, ImeAR, ParamProcKaskadno, ParamProcDefNull, ImeTabeleR, alter, conn);
        KaskadnoUpP = 1; 
      }   
    } 
    String str="";
    ResultSet rs;
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   { 
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[TRG_"+ ImeTabeleR+"_"+ImeOgranicenja+"_UPD"+"]') and OBJECTPROPERTY(id, N'IsTrigger') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      out.println("ALTER TRIGGER TRG_"+ ImeTabeleR.replaceAll(" ","_")+"_"+ImeOgranicenja+"_UPD");
    }else
    {
      out.println("CREATE TRIGGER TRG_"+ ImeTabeleR.replaceAll(" ","_")+"_"+ImeOgranicenja+"_UPD");
    }
        
    out.println("   ON "+ ImeTabeleR.replaceAll(" ","_"));    
    out.println("   FOR UPDATE");
    out.println("AS");
    int m = IdAtributaR.length;  
    String[] AtributNameR = new String[m];
    String[] AtributNameL = new String[m];
    try {
    out.print("   DECLARE ");    
    for (int i=0;i<AtributNameR.length;i++)
    {
      String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaR[i];
      rs=query.select(upit);
      if (rs.next())
      {
        AtributNameR[i] = rs.getString("Att_mnem");
        out.print("   @" + AtributNameR[i].replaceAll(" ","_") + "R");        
        int IdDomena = rs.getInt("Dom_id"); 
        query.Close();        
        String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta,1); 
        int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);        
        if (Duzina!=0)            
        {
              TipPodatka = TipPodatka + "(" + Duzina + ")";               
        }
        out.println(" " + TipPodatka + ",");        
        if (ModActionR == -4)
        {
          out.println("   @" + AtributNameR[i].replaceAll(" ","_") + "_New "+TipPodatka + ",");
        } 
      } 
    }
    
    for (int i=0;i<AtributNameL.length;i++)
    {
      String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaL[i];
      rs=query.select(upit);
      if (rs.next())
      {
        AtributNameL[i] = rs.getString("Att_mnem");
        out.print("   @" + AtributNameL[i].replaceAll(" ","_") + "L");        
        int IdDomena = rs.getInt("Dom_id"); 
        query.Close();        
        String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta,1); 
        int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);        
        if (Duzina!=0)            
        {
              TipPodatka = TipPodatka + "(" + Duzina + ")";               
        }                                
        str = " " + TipPodatka;  
        if (ModActionR == -1)
        {
          if (i<AtributNameL.length-1)
          {
            str = str + ", ";  
          }
        }else 
        {
          str = str + ",";
        }
        out.println(str);         
      } 
    }     
    
    if (ModActionR != -1)
    {    
    for (int i=0;i<ParamProcDefNull.length;i++)
    {
      str = "   @" + ParamProcDefNull[i];
      if (i<ParamProcDefNull.length-1)
      {
           str = str + (",");  
      }
      out.println(str);
    }
    }
    str = "";
    out.print("   IF ");
    for (int i=0;i<AtributNameR.length;i++)
    {
        str = "   UPDATE(" + AtributNameR[i].replaceAll(" ","_") + ")";
        out.print(str);
        if (i<AtributNameR.length-1)
        {
           out.println(" OR ");  
        }
    }
    out.println();
    out.println("   BEGIN");
    str = "   DECLARE Cursor_" + ImeTabeleR.replaceAll(" ","_") + " CURSOR";
    out.println(str);
    out.println("   FOR");
    out.print("   SELECT ");    
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = AtributNameR[i].replaceAll(" ","_");      
      if (i<AtributNameR.length-1)
      {
        str = str + ", ";  
      }
      out.print(str);
    } 
    out.println();
    out.println("   FROM Deleted"); 
    if (ModActionR == -4)
    {
       out.print("   SELECT ");    
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = "@" + AtributNameR[i].replaceAll(" ","_") + "_New = " + AtributNameR[i].replaceAll(" ","_");      
      if (i<AtributNameR.length-1)
      {
        str = str + ", ";  
      }
      out.print(str);
    } 
    
    out.println();
    out.println("   FROM Inserted");  
    }
    out.println("   OPEN " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleR.replaceAll(" ","_") + " INTO "; 
    out.println(str);
    out.print("   ");
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = "@" + AtributNameR[i].replaceAll(" ","_") + "R";
      if (i<AtributNameR.length-1)
      {
        str = str + ", ";  
      }
      out.print(str);      
    }
    out.println();
    out.println("   WHILE @@FETCH_STATUS=0");
    out.println("   BEGIN"); 
    str = "   DECLARE Cursor_" + ImeTabeleL.replaceAll(" ","_") + " CURSOR";
    out.println(str);
    out.println("   FOR");
    out.print("   SELECT ");
    for (int i=0;i<AtributNameL.length;i++)
    {
      if (ModActionR != -1)
      {
        str = AtributNameL[i].replaceAll(" ","_") + ",";  
        out.print(str);
      } else
      {
        str = AtributNameL[i].replaceAll(" ","_");
        if (i<AtributNameL.length-1)
        {
            str = str + ", ";
        }
        out.print(str);
      }      
    }
        
    if (ModActionR != -1)
    {
    out.println();
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "   " + ImeKljucL[i].replaceAll(" ","_");
      if (i<ImeKljucL.length-1)
      {
           str = str + (",");  
      } 
      out.print(str);
    }
    }
    out.println();
    str = "   FROM " + ImeTabeleL.replaceAll(" ","_") + " WHERE "; 
    out.println(str);
    if (RSCRelIntType == 1)
    {
    for (int i=0;i<AtributNameL.length;i++)
    {
      str = "   (" + AtributNameL[i].replaceAll(" ","_") + " = @" + AtributNameR[i].replaceAll(" ","_") + "R" + " OR " + AtributNameL[i].replaceAll(" ","_") + " IS NULL)";      
      if (i<AtributNameL.length-1)
      {
           str = str + (" AND ");  
      } 
      out.println(str);
    }
    }else 
    {
      for (int i=0;i<AtributNameL.length;i++)
      {
        str = "   " + AtributNameL[i].replaceAll(" ","_") + " = @" + AtributNameR[i].replaceAll(" ","_") + "R";      
        if (i<AtributNameL.length-1)
        {
           str = str + (" AND ");  
        } 
        out.println(str);
      }
    }
    out.println("   OPEN " + "Cursor_" + ImeTabeleL.replaceAll(" ","_"));
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleL.replaceAll(" ","_") + " INTO "; 
    out.println(str);          
    for (int i=0;i<AtributNameL.length;i++)
    {
      if (ModActionR != -1)
      {
        str = "   @" + AtributNameL[i].replaceAll(" ","_") + "L,";  
        out.print(str);
      }else
      {
        str = "   @" + AtributNameL[i].replaceAll(" ","_") + "L";
        if (i<AtributNameL.length-1)
        {
           str = str + (",");  
        } 
        out.print(str);
      }            
    }
         
    if (ModActionR != -1)
    {
    out.println();
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "   @" + ImeKljucL[i].replaceAll(" ","_");
      if (i<ImeKljucL.length-1)
      {
           str = str + (",");  
      } 
      out.print(str);
    }
    }
    out.println();
    out.println("   WHILE @@FETCH_STATUS=0");
    out.println("   BEGIN");    
    String Argumenti="";    
    for (int k=0;k<AtributNameR.length;k++)
    {      
      Argumenti = Argumenti + "@" + AtributNameR[k].replaceAll(" ","_") + "R";
      
      if (k<AtributNameR.length-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    } 
    String Argumenti2="";    
    for (int k=0;k<AtributNameR.length;k++)
    {      
      Argumenti2 = Argumenti2 + "@" + AtributNameR[k].replaceAll(" ","_") + "_New";
      
      if (k<AtributNameR.length-1)
      {
        Argumenti2 = Argumenti2 + ", ";  
      }
    }  
    String ArgumentiF="";    
    for (int k=0;k<AtributNameL.length;k++)
    {      
      ArgumentiF = ArgumentiF + "@" + AtributNameL[k].replaceAll(" ","_") + "L";
      
      if (k<AtributNameL.length-1)
      {
        ArgumentiF = ArgumentiF + ", ";  
      }
    }    
    if (RSCRelIntType == 1)
    {
      out.println("   IF dbo.SadrzavanjePRI_" + ImeTabeleL.replaceAll(" ","_")+"(" + ArgumentiF +")=0");
    } 
    String Argumenti1="";     
    for (int k=0;k<ImeKljucL.length;k++)
    {      
      Argumenti1 = Argumenti1 + "@" + ImeKljucL[k].replaceAll(" ","_");
      
      if (k<ImeKljucL.length-1)
      {
        Argumenti1 = Argumenti1 + ", ";  
      }
    } 
    String Argumenti3="";
    Argumenti3 = Argumenti2+", "+Argumenti1;
    if (ModActionR == -1)
    {
      out.println("BEGIN");
      out.println("RAISERROR('Torka u ne moze da se menja iz relacije "+ImeTabeleR.replaceAll(" ","_")+"',16,1)");
      out.println("ROLLBACK TRAN");
      out.println("END");
      //out.println("   exec dbo.SprecPRI_"+ImeTabeleR + " ");
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
      out.println("   exec dbo.SetNull_"+ImeTabeleR.replaceAll(" ","_") + " " + Argumenti1);      
    } else
    if (ModActionR == -3)
    {
      if (RSCRelIntType == 1)
      {
        out.println("   exec dbo.SetDefaultPRI_"+ImeTabeleR.replaceAll(" ","_") + " " + Argumenti1);
      }else
      {
        out.println("   exec dbo.SetDefault_"+ImeTabeleR.replaceAll(" ","_") + " " + Argumenti1);
      }
    } else
    {
      out.println("   exec dbo.KaskadnoUpdPRI_" + ImeTabeleR.replaceAll(" ","_") + " " + Argumenti3);
    }   
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleL.replaceAll(" ","_") + " INTO "; 
    out.println(str);
    
    for (int i=0;i<AtributNameL.length;i++)
    {
      if (ModActionR != -1)
      {
        str = "   @" + AtributNameL[i].replaceAll(" ","_") + "L,";   
        out.print(str); 
      }else
      {
        str = "   @" + AtributNameL[i].replaceAll(" ","_") + "L";
        if (i<AtributNameL.length-1)
        {
           str = str + (",");  
        }
        out.print(str); 
      }           
    }
         
    if (ModActionR != -1)
    {
    out.println();
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "   @" + ImeKljucL[i].replaceAll(" ","_");
      if (i<ImeKljucL.length-1)
      {
           str = str + (",");  
      } 
      out.print(str);
    }
    }
    out.println();
    out.println("   END");
    out.println("   CLOSE " + "Cursor_" + ImeTabeleL.replaceAll(" ","_"));
    out.println("   DEALLOCATE " + "Cursor_" + ImeTabeleL.replaceAll(" ","_"));
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleR.replaceAll(" ","_") + " INTO "; 
    out.println(str);
    
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = "   @" + AtributNameR[i].replaceAll(" ","_") + "R";
      if (i<AtributNameR.length-1)
      {
        str = str + ", ";  
      }
      out.print(str);      
    }
    out.println();
    out.println("   END");
    out.println("   CLOSE " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    out.println("   DEALLOCATE " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    out.println("   END");
    out.println("GO");
    out.println();
  }
       
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }
  }   
  public void UpdateOsnovniDFRIR(PrintWriter out, PrintWriter outWorning, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int ModActionR, int[] IdAtributaL, int[] PKljucL, ClassFunction pom,int IdASistema, int IdSemaRel, Connection con, boolean alter, Connection conn)
  { //triger za update desne strane za osnovni ref. int. default full
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeKljucL = pom.FAtributName(PKljucL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);    
    String[] ParamDeklaracijeR = pom.ParametriFunkcijeProcedure(IdAtributaR, con, IdProjekta, Rek,1);
    //String[] ParamProcDefNull = pom.ParametriFunkcijeProcedure(PKljucL, con, IdProjekta, Rek);
    String[] ParamProcKaskadno = pom.ParametriFunkcijeProcedureUpd(IdAtributaR, con, IdProjekta, Rek,1);
    String[] ParamProc = pom.ParametriFunkcijeProcedure(IdAtributaR, con, IdProjekta, Rek,1);    
    if (Postoji==0)
    {
      PostojiFunkcija(out, ImeTabeleL, ImeAL, ImeAR, ParamProc, RSCRelIntType, alter, conn);  
      Postoji =1;
    }    
        
    /*if (ModActionR == -1 && BezAkcije == 0)
    {
      SprecPRI(out, ImeTabeleL, ImeAL, ImeAR, ParamProc, ImeTabeleR, RSCRelIntType);
      BezAkcije = 1;
    } else*/
    if (ModActionR == -2 && SetNullRP == 0)
    {
      if (SetNullRFD ==0 && RSCRelIntType != 1)
      {
        SetNullDFRI(out, ImeTabeleL,ImeAR, ImeAL, ParamDeklaracijeR, ImeTabeleR, alter, conn);
        SetNullRFD = 1;
      }      
    } else
    if (ModActionR == -3)
    {
      if (SetDefaultFDR == 0 && RSCRelIntType != 1)
      {
        SetDefaultDFRIRight(out, ImeTabeleL,ImeAR, ImeAL, ParamDeklaracijeR, ImeTabeleR, alter, conn); 
        SetDefaultFDR = 1;
      }
      
    } else
    if (ModActionR == -4)
    {       
      if (KaskadnoUpFD == 0 && RSCRelIntType != 1)
      {
        KaskadnoUpdateDFRIL(out, ImeTabeleL, ImeAL, ImeAR, ParamProcKaskadno, ImeTabeleR, RSCRelIntType, alter, conn);
        KaskadnoUpFD = 1;
      }        
    } 
    String str="";
    ResultSet rs;
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   { 
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[TRG_"+ ImeTabeleR+"_"+ImeOgranicenja+"_UPD"+"]') and OBJECTPROPERTY(id, N'IsTrigger') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      out.println("ALTER TRIGGER TRG_"+ ImeTabeleR.replaceAll(" ","_")+"_"+ImeOgranicenja+"_UPD");
    }else
    {
      out.println("CREATE TRIGGER TRG_"+ ImeTabeleR.replaceAll(" ","_")+"_"+ImeOgranicenja+"_UPD");
    }
        
    out.println("   ON "+ ImeTabeleR.replaceAll(" ","_"));    
    out.println("   FOR UPDATE");
    out.println("AS");
    int m = IdAtributaR.length;  
    String[] AtributNameR = new String[m];
    //String[] AtributNameL = new String[m];
    try {
    out.print("   DECLARE ");    
    for (int i=0;i<m;i++)
    {
      String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaR[i];
      rs=query.select(upit);
      if (rs.next())
      {
        AtributNameR[i] = rs.getString("Att_mnem");
        out.print("   @" + AtributNameR[i].replaceAll(" ","_") + "_Old");        
        int IdDomena = rs.getInt("Dom_id"); 
        query.Close();        
        String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta,1); 
        int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);        
        if (Duzina!=0)            
        {
              TipPodatka = TipPodatka + "(" + Duzina + ")";               
        }
        out.print(" " + TipPodatka);  
        if (ModActionR == -4)
        {
          out.println(",");
          out.print("   @" + AtributNameR[i].replaceAll(" ","_") + "_New"); 
          out.print(" " + TipPodatka);
        }        
        if (i<m-1)
        {
          out.println(",");
        } 
      } 
    }    
    out.println();
    str = "";
    out.print("   IF ");
    for (int i=0;i<AtributNameR.length;i++)
    {
        str = "   UPDATE(" + AtributNameR[i].replaceAll(" ","_") + ")";
        out.print(str);
        if (i<m-1)
        {
           out.println(" OR ");  
        }
    }
    out.println();
    out.println("   BEGIN");  
    str = "   DECLARE Cursor_" + ImeTabeleR.replaceAll(" ","_") + " CURSOR";
    out.println(str);
    out.println("   FOR");
    out.print("   SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = AtributNameR[i].replaceAll(" ","_");      
      if (i<m-1)
      {
        str = str + ", ";  
      }
      out.print(str);
    } 
    out.println();
    out.println("   FROM Deleted");
    
  if(ModActionR == -4)
  {
    out.print("   SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = "@"+AtributNameR[i].replaceAll(" ","_")+"_New = " + AtributNameR[i].replaceAll(" ","_");      
      if (i<m-1)
      {
        str = str + ", ";  
      }
      out.print(str);
    } 
    out.println();
    out.println("   FROM Inserted");    
  }
    out.print("   ");   
     
    String Argumenti="";    
    for (int k=0;k<AtributNameR.length;k++)
    {      
      Argumenti = Argumenti + "@" + AtributNameR[k].replaceAll(" ","_") + "_Old";
      
      if (k<m-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    }  
    String ArgumentiF="";    
    for (int k=0;k<AtributNameR.length;k++)
    {      
      ArgumentiF = ArgumentiF + "@" + AtributNameR[k].replaceAll(" ","_") + "_Old,";
      ArgumentiF = ArgumentiF + "@" + AtributNameR[k].replaceAll(" ","_") + "_New";
      if (k<m-1)
      {
        ArgumentiF = ArgumentiF + ", ";  
      }
    }    
    out.println("   OPEN " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleR.replaceAll(" ","_") + " INTO "; 
    out.println(str);    
    out.println(Argumenti);
    out.println("   WHILE @@FETCH_STATUS=0");
    out.println("   BEGIN"); 
    if (ModActionR == -3 || ModActionR == -1)
    {
      out.println("   BEGIN TRANSACTION");  
    }
    out.println("   IF dbo.PostojiFDRI_"+ ImeTabeleL.replaceAll(" ","_") +"(" + Argumenti +")=1");
     
    String Argumenti1="";     
    for (int k=0;k<AtributNameR.length;k++)
    {      
      Argumenti1 = Argumenti1 + "@" + AtributNameR[k].replaceAll(" ","_") + "_Old";
      
      if (k<AtributNameR.length-1)
      {
        Argumenti1 = Argumenti1 + ", ";  
      }
    } 
    String Argumenti2="";     
    for (int k=0;k<ImeKljucL.length;k++)
    {      
      Argumenti2 = Argumenti2 + "@" + ImeKljucL[k].replaceAll(" ","_");      
      if (k<ImeKljucL.length-1)
      {
        Argumenti2 = Argumenti2 + ", ";  
      }
    } 
    if (ModActionR == -1)
    {
      out.println("BEGIN");
      out.println("RAISERROR('Torka u ne moze da se menja iz relacije "+ImeTabeleR.replaceAll(" ","_")+"',16,1)");
      out.println("ROLLBACK TRAN");
      out.println("END");
      //out.println("   exec dbo.SprecPRI_"+ImeTabeleR + " ");
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
      out.println("   exec dbo.SetNull_"+ImeTabeleR.replaceAll(" ","_") + " " + Argumenti1);      
    } else
    if (ModActionR == -3)
    {      
        out.println("   exec dbo.SetDefault_R_"+ImeTabeleR.replaceAll(" ","_") + " " + Argumenti1);
      
    } else
    {
      out.println("   exec dbo.KaskadnoUpdDFRI_" + ImeTabeleR.replaceAll(" ","_") + " " + ArgumentiF);
    }   
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleR.replaceAll(" ","_") + " INTO "; 
    out.println(str);
    out.println(Argumenti);
    out.println("END");
    out.println("   CLOSE " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    out.println("   DEALLOCATE " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    out.println("   END");
    out.println("GO");
    out.println();
  }
       
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }
  } 
  public void UpdateOsnovniRIL(PrintWriter out, PrintWriter outWorning, int[] PKljucL, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaR, int IdProjekta, Rekurzije Rek, int RSCRelIntType, int ModActionL, int[] IdAtributaL, ClassFunction pom,int IdASistema, int IdSemaRel, Connection con, boolean alter, Connection conn)
  { //triger za update leve strane za osnovni ref. int. parcijalni, default i full
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);
    
    String[] ParamFunk = pom.ParametriFunkcijeProcedure(IdAtributaR, con, IdProjekta, Rek,1);  
    String[] ParamProcDefNull = pom.ParametriFunkcijeProcedure(PKljucL, con, IdProjekta, Rek,1); 
    //String[] ParamDeklaracijeL = pom.ParametriFunkcijeProcedure(IdAtributaL, con, IdProjekta, Rek);
    String[] ImeKljucL = pom.FAtributName(PKljucL, con, IdProjekta);
    if (RSCRelIntType == 1 && Sadrzavanje ==0)
    {
      SadrzavanjePRI(out, ImeTabeleL,ImeTabeleR, ImeAL, ImeAR, ParamFunk, alter, conn);
      Sadrzavanje = 1;
    } else if (RSCRelIntType == 2 && Sadrzavanje ==0)
    {
      SadrzavanjeFRI(out, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, ParamFunk, alter, conn);
      Sadrzavanje = 1;
    } else 
    {
      if (Sadrzavanje == 0) 
      {
        SadrzavanjeDRI(out, ImeTabeleL, ImeTabeleR, ImeAL, ImeAR, ParamFunk, alter, conn);
        Sadrzavanje = 1;
      }
    } 
    /*
    if (ModActionL == -3)
    {
      if (SetDefaultFD == 0 && RSCRelIntType != 1)
      {
        Default(out, ImeTabeleL,ImeKljucL, ImeAL, ParamProcDefNull,ParamDeklaracijeL, ImeTabeleR, RSCRelIntType); //vazi samo za parcijalni
        SetDefaultFD = 1;
      }      
    }  
    */
    String str="";
    ResultSet rs;
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   { 
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[TRG_"+ ImeTabeleL+"_"+ImeOgranicenja+"_UPD"+"]') and OBJECTPROPERTY(id, N'IsTrigger') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      out.println("ALTER TRIGGER TRG_"+ ImeTabeleL.replaceAll(" ","_")+"_"+ImeOgranicenja+"_UPD"); 
    }else
    {
      out.println("CREATE TRIGGER TRG_"+ ImeTabeleL.replaceAll(" ","_")+"_"+ImeOgranicenja+"_UPD");
    }        
    out.println("   ON "+ ImeTabeleL.replaceAll(" ","_"));    
    out.println("   FOR UPDATE");
    out.println("AS");
    int m = IdAtributaR.length;  
    String[] AtributName = new String[m];
    try {
    out.print("   DECLARE ");        
    for (int i=0;i<m;i++)
    {
      String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaL[i];
      rs=query.select(upit);
      if (rs.next())
      {
        AtributName[i] = rs.getString("Att_mnem");
        //out.print("   @" + AtributName[i] + "_old");       
        int IdDomena = rs.getInt("Dom_id"); 
        query.Close();        
        String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta,1); 
        int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);        
        if (Duzina!=0)            
        {
              TipPodatka = TipPodatka + "(" + Duzina + ")";               
        }
          
        out.print("   @" + AtributName[i].replaceAll(" ","_"));
        out.print(" " + TipPodatka);
        out.println(",");        
      } 
    }
    
    for (int i=0;i<ParamProcDefNull.length;i++)
    {
      str = str + "   @" + ParamProcDefNull[i];
      if (i<ParamProcDefNull.length-1)
      {
           str = str + (",");  
      }
      out.println(str);
    }
      
    String Argumenti="";    
    for (int k=0;k<m;k++)
    {      
      Argumenti = Argumenti + "@" + AtributName[k].replaceAll(" ","_");
      
      if (k<m-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    }        
    str = "";
    out.print("IF ");
    for (int i=0;i<m;i++)
    {
        str = "UPDATE(" + AtributName[i].replaceAll(" ","_") + ")";
        out.print(str);
        if (i<m-1)
        {
           out.println(" OR ");  
        }
    } 
    out.println();
    out.println("BEGIN");
    
    str = "   DECLARE Cursor_" + ImeTabeleL.replaceAll(" ","_")+"_L" + " CURSOR";
    out.println(str);
    out.println("   FOR");
    out.print("   SELECT ");
    for (int i=0;i<AtributName.length;i++)
    {
      str = AtributName[i].replaceAll(" ","_") + ",";       
      out.print(str);
    }
    out.println();    
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "   " + ImeKljucL[i].replaceAll(" ","_");
      if (i<ImeKljucL.length-1)
      {
           str = str + (",");  
      } 
      out.print(str);
    }
    out.println();
    str = "   FROM Inserted"; 
    out.println(str);
    out.println("   OPEN " + "Cursor_" + ImeTabeleL.replaceAll(" ","_")+"_L");
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleL.replaceAll(" ","_") +"_L"+ " INTO "; 
    out.println(str);    
    for (int i=0;i<AtributName.length;i++)
    {
      str = "   @" + AtributName[i].replaceAll(" ","_") + ",";      
      out.print(str);      
    }
    out.println();     
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "   @" + ImeKljucL[i].replaceAll(" ","_");
      if (i<ImeKljucL.length-1)
      {
           str = str + (",");  
      } 
      out.print(str);
    }
    out.println();
    out.println("   WHILE @@FETCH_STATUS=0");
    out.println("   BEGIN");        
    if (ModActionL == -3 || ModActionL == -1)
    {
      out.println("   BEGIN TRANSACTION");  
    }
    if (RSCRelIntType == 1)
    {
        out.println("IF dbo.SadrzavanjePRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");
    }else if (RSCRelIntType == 2)
    {
        out.println("IF dbo.SadrzavanjeFRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");
    }else    
    {
        out.println("IF dbo.SadrzavanjeDRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");
    }    
    out.println("BEGIN");
    int n = IdAtributaL.length;
    String Argumenti1="";     
    for (int k=0;k<ImeKljucL.length;k++)
    {      
      Argumenti1 = Argumenti1 + "@" + ImeKljucL[k].replaceAll(" ","_");
      
      if (k<ImeKljucL.length-1)
      {
        Argumenti1 = Argumenti1 + ", ";  
      }
    } 
    if (ModActionL == -1)
    {       
      out.println("RAISERROR('Torka u ne moze da se modifikuje u relaciji "+ImeTabeleL.replaceAll(" ","_")+"',16,1)");
      out.println("ROLLBACK TRAN");                
    } else    
    if (ModActionL == -3)
    {
      //out.println("   exec dbo.SetDefault_"+ImeTabeleL + " " + Argumenti1);
    
      out.println("UPDATE u");
      out.println("SET");
      for (int i=0;i<n;i++)
      {
        str = "u." + ImeAL[i].replaceAll(" ","_") + " = default";        
        if (i<n-1)
        {
          str = str + ", ";  
        }
        out.println(str);
      }
      str = "FROM " + ImeTabeleL.replaceAll(" ","_") + " u";
      out.println(str);
      out.println("WHERE ");
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "(u." + ImeKljucL[i].replaceAll(" ","_") + " = " + "@" + ImeKljucL[i].replaceAll(" ","_") + ")";       
      out.print(str);
      if (i<ImeKljucL.length-1)
        {
           out.println(" AND ");  
        }      
    }
    out.println();
    out.print("SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = "@" + AtributName[i].replaceAll(" ","_") + "= " + "u." + AtributName[i].replaceAll(" ","_");      
      if (i<m-1)
      {
        str = str + ", ";  
      }
      out.println(str);
    } 
    out.println("FROM " + ImeTabeleL.replaceAll(" ","_") + " u");
    out.println("WHERE ");
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "(u." + ImeKljucL[i].replaceAll(" ","_") + " = " + "@" + ImeKljucL[i].replaceAll(" ","_") + ")";       
      out.print(str);
      if (i<ImeKljucL.length-1)
        {
           out.println(" AND ");  
        }      
    }
    out.println();  
    Argumenti = "";
    for (int k=0;k<m;k++)
    {      
      Argumenti = Argumenti + "@" + AtributName[k].replaceAll(" ","_");      
      if (k<m-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    } 
    if (RSCRelIntType == 1)
    {
        out.println("IF dbo.SadrzavanjePRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");
    }else if (RSCRelIntType == 2)
    {
        out.println("IF dbo.SadrzavanjeFRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");
    }else
    {
      out.println("IF dbo.SadrzavanjeDRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");
    }
    out.println("BEGIN");
    out.println("RAISERROR('Torka u ne moze da se modifikuje u relaciji "+ImeTabeleL.replaceAll(" ","_")+"',16,1)");
    out.println("ROLLBACK TRAN");
    out.println("END");
    //out.println();
    
    } else
    if (ModActionL == -2)
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
      out.println("UPDATE u");
      out.println("SET");
      for (int i=0;i<n;i++)
      {
        str = "u." + ImeAL[i].replaceAll(" ","_") + " = null";        
        if (i<n-1)
        {
          str = str + ", ";  
        }
        out.println(str);
      }
      str = "FROM " + ImeTabeleL .replaceAll(" ","_")+ " u";
      out.println(str);  
      out.println("WHERE ");
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "(u." + ImeKljucL[i].replaceAll(" ","_") + " = " + "@" + ImeKljucL[i].replaceAll(" ","_") + ")";       
      out.print(str);
      if (i<ImeKljucL.length-1)
        {
           out.println(" AND ");  
        }      
    }
     out.println();
    }    
    
    //out.println();
    out.println("END");
    
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleL.replaceAll(" ","_")+"_L" + " INTO "; 
    out.println(str);
    
    for (int i=0;i<AtributName.length;i++)
    {
      str = "   @" + AtributName[i].replaceAll(" ","_") + ",";      
      out.print(str);      
    }
    out.println();     
    for (int i=0;i<ImeKljucL.length;i++)
    {
      str = "   @" + ImeKljucL[i].replaceAll(" ","_");
      if (i<ImeKljucL.length-1)
      {
           str = str + (",");  
      } 
      out.print(str);
    }
    out.println();
    out.println("   END");
    out.println("   CLOSE " + "Cursor_" + ImeTabeleL.replaceAll(" ","_")+"_L");
    out.println("   DEALLOCATE " + "Cursor_" + ImeTabeleL.replaceAll(" ","_")+"_L");
    out.println("END");
    out.println("GO");
    out.println();
  }
       
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }
  }
  
  //za inverzni ref. int.
  public void SadrzavanjeIRI(PrintWriter out, String ImeTabeleL, String ImeTabeleR, String[] AtributNameR, String[] AtributNameL, String[] ParamFunkcije, boolean alter, Connection conn)
  {
    String str = "";    
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.SadrzavanjeIRI_"+ ImeTabeleL+"]') and OBJECTPROPERTY(id, N'IsScalarFunction') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER FUNCTION dbo.SadrzavanjeIRI_"+ ImeTabeleL.replaceAll(" ","_") + "(";    
   }else
   {
     str = "CREATE FUNCTION dbo.SadrzavanjeIRI_"+ ImeTabeleL.replaceAll(" ","_") + "(";
   }
    for (int i=0;i<ParamFunkcije.length;i++)
    {
      str = str + "@" + ParamFunkcije[i];
      if (i<ParamFunkcije.length-1)
      {
           str = str + (",");  
      } 
    }
    out.println(str+")"); 
    out.println("RETURNS int");
    out.println("AS");    
    out.println("BEGIN");
    out.println("   DECLARE @Count int,");
    out.println("   @Ret  int");
    out.println("SELECT @Count = COUNT(*) FROM "+ImeTabeleR.replaceAll(" ","_")+" u");
    str = "WHERE ";
    out.println(str);
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = "(u." + AtributNameR[i].replaceAll(" ","_") + " = " + "@" + AtributNameL[i].replaceAll(" ","_") + ")";
      out.print(str);
      if (i<AtributNameR.length-1)
        {
           out.println(" AND ");  
        }       
    }
    out.println();
    out.println("IF @Count != 0 ");
    out.println("SELECT @ret=1");
    out.println("ELSE");
    out.println("SELECT @ret=0");
    out.println("RETURN @ret");
    out.println("END");    
    out.println("GO");
    out.println();
  }
  public void IzvrsiOkidacIRI(PrintWriter out, boolean alter, Connection conn)
  {
    String str = "";  
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.IzvrsiOkidac]') and OBJECTPROPERTY(id, N'IsScalarFunction') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER FUNCTION dbo.IzvrsiOkidac(@NazivOkidaca varchar(50))";    
   }else
   {
     str = "CREATE FUNCTION dbo.IzvrsiOkidac(@NazivOkidaca varchar(50))";
   }
    out.println(str); 
    out.println("RETURNS int");
    out.println("AS");    
    out.println("BEGIN");
    out.println("   DECLARE @Count int,");
    out.println("   @Idt  int");
    out.println("   @Ret  int");
    out.println("SELECT @Idt = @@SPID");
    out.println("SELECT @Count = COUNT(*) FROM StatusOkidaca");    
    out.println("WHERE (Okidac = @NazivOkidaca) AND (IdTransakcije = @Idt)");      
    out.println("IF @Count != 0 ");
    out.println("SELECT @ret=1");
    out.println("ELSE");
    out.println("SELECT @ret=0");
    out.println("RETURN @ret");
    out.println("END");    
    out.println("GO");
    out.println();
  }
  void IzvrsenjeOkidacaIRI(PrintWriter out, boolean alter, Connection conn)
  {
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.IzvrsenjeOkidaca]') and OBJECTPROPERTY(id, N'IsProcedure') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER PROCEDURE dbo.IzvrsenjeOkidaca";
   }else
   {
     str = "CREATE PROCEDURE dbo.IzvrsenjeOkidaca";
   }
    out.println(str);
    out.println("(@Stat int, @NazivOkidaca varchar(50), @Idt int)");
    out.println("AS");    
    out.println("SELECT * FROM StatusOkidaca");
    out.println("IF @Stat = 1 ");    
    out.println("DELETE FROM StatusOkidaca WHERE");    
    out.println("Okidac = @NazivOkidaca AND IdTransakcije = @Idt");
    out.println("ELSE");
    out.println("INSERT INTO StatusOkidaca (Okidac, IdTransakcije)");    
    out.println("VALUES (@NazivOkidaca, @Idt)");    
    out.println("GO");
    out.println();
  }
  void KaskadnoIRIDel(PrintWriter out, String ImeTabeleL, String[] AtributNameL, String[] AtributNameR, String[] ParamProc,String ImeTabeleR, boolean alter, Connection conn)  
  {
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   {
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.KaskadnoIRI_Del_"+ ImeTabeleR+"]') and OBJECTPROPERTY(id, N'IsProcedure') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      str = "ALTER PROCEDURE dbo.KaskadnoIRI_Del_"+ ImeTabeleR.replaceAll(" ","_") + "(";
   }else
   {
     str = "CREATE PROCEDURE dbo.KaskadnoIRI_Del_"+ ImeTabeleR.replaceAll(" ","_") + "(";
   }
    int m = AtributNameL.length; 
    int n = ParamProc.length;
    for (int i=0;i<n;i++)
    {
      str = str + "@" + ParamProc[i];
      if (i<n-1)
      {
           str = str + (",");  
      } 
    }
    out.println(str+")");
    out.println("AS");      
    out.println("DELETE FROM "+ImeTabeleL.replaceAll(" ","_")+" u");    
    out.println("WHERE ");        
    for (int i=0;i<m;i++)
    {
          str = "u." + AtributNameL[i].replaceAll(" ","_") + " = " + "@" + AtributNameR[i].replaceAll(" ","_");
          if (i<m-1)
          {
           str = str + (" AND ");  
          } 
          out.println(str);
    }
    out.println(")");                    
    out.println();    
    out.println("GO");
    out.println();
  }
  
  void ProceduraZaUpisIRI(int IdProjekta,ClassFunction Pom,String ImeOgranicenja,PrintWriter out, String ImeTabeleL,String ImeTabeleR, Rekurzije Rek, int IdASistema, Connection con, int IdSemaL, int IdSemaR, int[] IdAtributaR,int[] IdAtributaL, ClassFunction pom, boolean alter , Connection conn)  
  {
    int[] IdAtributaLOll;
    int[] IdAtributaROll;
    int k,s;
    JDBCQuery query=new JDBCQuery(con);  
    k=Pom.brojanjeNtorki("IISC_RS_ATT","PR_id="+IdProjekta+" and RS_id="+IdSemaL+" and AS_id="+IdASistema,con); //broji koliko ima atributa odgovarajuca sema relacije
    String upit1 = "select Att_id,Att_null from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSemaL+" and IISC_RS_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" ORDER BY Att_sequence";
    IdAtributaLOll=query.selectArraySAint(upit1,k,1); 
    s=Pom.brojanjeNtorki("IISC_RS_ATT","PR_id="+IdProjekta+" and RS_id="+IdSemaR+" and AS_id="+IdASistema,con); //broji koliko ima atributa odgovarajuca sema relacije
    upit1 = "select Att_id,Att_null from IISC_RS_ATT where IISC_RS_ATT.RS_id="+IdSemaR+" and IISC_RS_ATT.PR_id="+IdProjekta+" and AS_id="+IdASistema+" ORDER BY Att_sequence";
    IdAtributaROll=query.selectArraySAint(upit1,s,1);
    query.Close();
    //String[] ImeAL = pom.FAtributName(IdAtributaLOll, query, IdProjekta, Rek);
    String[] ImeAR = pom.FAtributName(IdAtributaROll, con, IdProjekta); 
    String[] ImeALS = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeARS = pom.FAtributName(IdAtributaR, con, IdProjekta);
    String[] ParamProcL = pom.ParametriFunkcijeProcedure(IdAtributaLOll, con, IdProjekta, Rek,1);
    String[] ParamProcR = pom.ParametriFunkcijeProcedure(IdAtributaROll, con, IdProjekta, Rek,1); 
    String[] ParamProc = pom.ParametriFunkcijeProcedure(IdAtributaL, con, IdProjekta, Rek,1);   
    if (SadrzavanjeIRI == 0)
    {     
      SadrzavanjeIRI(out, ImeTabeleL, ImeTabeleR, ImeARS, ImeALS, ParamProc, alter, conn);       
      SadrzavanjeIRI = 1;
    }
    if (IzvrsenjeOkidaca == 0)
    {     
      IzvrsenjeOkidacaIRI(out, alter, conn); 
      IzvrsenjeOkidaca = 1;
    }  
    String str = "";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   { 
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[dbo.Insert_"+ ImeOgranicenja+"]') and OBJECTPROPERTY(id, N'IsProcedure') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
        str = "ALTER PROCEDURE dbo.Insert_"+ ImeOgranicenja + "(";
    }else
    {
        str = "CREATE PROCEDURE dbo.Insert_"+ ImeOgranicenja + "(";
    }
    out.println(str);
    str="";
    for (int i=0;i<ParamProcL.length;i++)
    {
      str = str + "@" + ParamProcL[i] + ",";
      /*
      if (i<ParamProcL.length-1)
      {
           str = str + (",");  
      } */
    }
    out.println(str);
    str="";
    boolean ind;
    for (int i=0;i<ParamProcR.length;i++)
    {
      ind = false;      
      for (int j=0;j<ParamProcL.length;j++)
      {
          if (ParamProcL[j].compareTo(ParamProcR[i])==0)            
            ind = true;            
      }
      if (ind == false)
      {
        str = str + "@" + ParamProcR[i];
        if (i<ParamProcR.length-1)
        {
           str = str + (",");  
        } 
      }        
    }    
    out.println(str + ")");
    String Argumenti = "";      
    for (int g=0;g<ImeALS.length;g++)
    {      
      Argumenti = Argumenti + "@" + ImeALS[g].replaceAll(" ","_");
      
      if (g<ImeALS.length-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    }   
    String Argumenti1 = "";      
    for (int g=0;g<ImeAR.length;g++)
    {      
      Argumenti1 = Argumenti1 + "@" + ImeAR[g].replaceAll(" ","_");
      
      if (g<ImeAR.length-1)
      {
        Argumenti1 = Argumenti1 + ", ";  
      }
    }     
    
    out.println("AS");     
    out.println("   DECLARE @Idt int");
    out.println("   BEGIN TRANSACTION");
    out.println("   SET @Idt = @@SPID");
    out.println("   exec dbo.IzvrsenjeOkidaca 0, 'UpisIRI_" + ImeTabeleL.replaceAll(" ","_") + "', @Idt");
    out.println("INSERT INTO " + ImeTabeleL.replaceAll(" ","_"));    
    out.print("VALUES ( ");        
    str = Argumenti+")";    
    out.println(str);    
    out.println("INSERT INTO " + ImeTabeleR.replaceAll(" ","_"));     
    out.print("VALUES (");
    str = Argumenti1+")";
    out.println(str);
    out.println("   exec dbo.IzvrsenjeOkidaca 1, 'UpisIRI_" + ImeTabeleL.replaceAll(" ","_") + "', @Idt");
    out.println("IF dbo.SadrzavanjeIRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");    
    out.println("BEGIN");
    out.println("RAISERROR('Narusen inverzni integritet',16,1)");
    out.println("ROLLBACK TRAN");
    out.println("END");
    out.println("   COMMIT TRANSACTION");   
    out.println("GO");
    out.println();
  }
  public void BrisanjeUpdateInverzniRI(Connection con, PrintWriter out, String ImeTabeleR, String ImeTabeleL, String ImeOgranicenja, int[] IdAtributaR, int[] IdAtributaL, int IdProjekta, Rekurzije Rek, int DelActionR, int Triger, ClassFunction pom, boolean alter, Connection conn)
  {   
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);    
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);    
    String[] ParamProc = pom.ParametriFunkcijeProcedure(IdAtributaR, con, IdProjekta, Rek,1);       
    if (DelActionR == -4 && KaskadnoIRI == 0)
    {    
      KaskadnoIRIDel(out, ImeTabeleL, ImeAL, ImeAR, ParamProc, ImeTabeleR, alter, conn);
      KaskadnoIRI = 1;        
    }
    
    String str="";
    ResultSet rs;    
  
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
    if (alter)
  {
    if (Triger == 1)
    {
      Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[TRG_"+ ImeTabeleR+"_"+ImeOgranicenja+"_DEL"+"]') and OBJECTPROPERTY(id, N'IsTrigger') = 1";
    }else
    {
      Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[TRG_"+ ImeTabeleR+"_"+ImeOgranicenja+"_UPD"+"]') and OBJECTPROPERTY(id, N'IsTrigger') = 1";
    }
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
    if (Triger == 1)
    {
      if (alter && postoji)
      {
        out.println("ALTER TRIGGER TRG_"+ ImeTabeleR.replaceAll(" ","_")+"_"+ImeOgranicenja+"_DEL");
      }else
      {
        out.println("CREATE TRIGGER TRG_"+ ImeTabeleR.replaceAll(" ","_")+"_"+ImeOgranicenja+"_DEL");
      }
       
      out.println("   ON "+ ImeTabeleR.replaceAll(" ","_"));          
      out.println("   FOR DELETE");
    }else
    {
      if (alter && postoji)
      {
        out.println("ALTER TRIGGER TRG_"+ ImeTabeleR.replaceAll(" ","_")+"_"+ImeOgranicenja+"_UPD");
      }else
      {
        out.println("CREATE TRIGGER TRG_"+ ImeTabeleR.replaceAll(" ","_")+"_"+ImeOgranicenja+"_UPD");
      }
       
      out.println("   ON "+ ImeTabeleR.replaceAll(" ","_"));    
      out.println("   FOR UPDATE");
    }
    
    out.println("AS");
    int m = IdAtributaR.length;  
    String[] AtributNameR = new String[m];
    
    try {
    out.println("   DECLARE @Count int,");    
    for (int i=0;i<m;i++)
    {
      String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaR[i];
      rs=query.select(upit);
      if (rs.next())
      {
        AtributNameR[i] = rs.getString("Att_mnem");
        out.print("   @" + AtributNameR[i].replaceAll(" ","_"));
        int IdDomena = rs.getInt("Dom_id"); 
        query.Close();        
        String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta,1); 
        int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);        
        if (Duzina!=0)            
        {
              TipPodatka = TipPodatka + "(" + Duzina + ")";               
        }
        str = " " + TipPodatka;
        if (i<m-1)
      {
           str = str + (",");  
      } 
        out.println(str);                   
      } 
    }    
    if (Triger == 2)
    {
      str = "";
      out.print("   IF ");
      for (int i=0;i<AtributNameR.length;i++)
      {
        str = "   UPDATE(" + AtributNameR[i].replaceAll(" ","_") + ")";
        out.print(str);
        if (i<m-1)
        {
           out.println(" OR ");  
        }
      }
      out.println();
      out.println("   BEGIN");
    }
    str = "   DECLARE Cursor_" + ImeTabeleR.replaceAll(" ","_") + " CURSOR";
    out.println(str);
    out.println("   FOR");
    out.print("   SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = AtributNameR[i].replaceAll(" ","_");      
      if (i<m-1)
      {
        str = str + ", ";  
      }
      out.print(str);
    } 
    
    out.println();
    out.println("   FROM Deleted");    
    out.println("   OPEN " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleR.replaceAll(" ","_") + " INTO "; 
    out.println(str);
    out.print("   ");
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = "@" + AtributNameR[i].replaceAll(" ","_");
      if (i<AtributNameR.length-1)
      {
        str = str + ", ";  
      }
      out.print(str);      
    }
    out.println();
    out.println("   WHILE @@FETCH_STATUS=0");
    out.println("   BEGIN"); 
    out.print("   IF (");
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = "@" + AtributNameR[i].replaceAll(" ","_") + " IS NOT NULL";
      out.print(str);
      if (i<AtributNameR.length-1)
        {
           out.println(" AND ");  
        }       
    }   
    out.println(")");
    out.println("     BEGIN");
    out.println("SELECT @Count = COUNT(*) FROM "+ImeTabeleR.replaceAll(" ","_") +" v");
    str = "WHERE ";
    out.println(str);
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = "(v." + AtributNameR[i].replaceAll(" ","_") + " = " + "@" + AtributNameR[i].replaceAll(" ","_") + ")";
      out.print(str);
      if (i<m-1)
        {
           out.println(" AND ");  
        }       
    }
    out.println();
    String Argumenti="";    
    for (int k=0;k<AtributNameR.length;k++)
    {      
      Argumenti = Argumenti + "@" + AtributNameR[k].replaceAll(" ","_");
      
      if (k<m-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    }  
    out.println("IF (@Count = 0)");
    if (Triger == 1)
    {
      if (DelActionR == -4)
      {      
        out.println("   exec dbo.KaskadnoIRI_Del_" + ImeTabeleR.replaceAll(" ","_") + " " + Argumenti);      
      }else 
      {      
        out.println("BEGIN");      
        out.println("RAISERROR('Torka u ne moze da se brise iz relacije "+ImeTabeleR.replaceAll(" ","_")+"',16,1)");           
        out.println("ROLLBACK TRAN");
        out.println("END");    
      } 
    }else if (Triger == 2)
    {     
        out.println("BEGIN");            
        out.println("RAISERROR('Torka u ne moze da se menja iz relacije "+ImeTabeleR.replaceAll(" ","_")+"',16,1)");               
        out.println("ROLLBACK TRAN");
        out.println("END");      
    }    
    out.println("END");
    str = "   FETCH NEXT FROM " + "Cursor_" + ImeTabeleR.replaceAll(" ","_") + " INTO "; 
    out.println(str);
    
    for (int i=0;i<AtributNameR.length;i++)
    {
      str = "   @" + AtributNameR[i].replaceAll(" ","_");
      if (i<AtributNameR.length-1)
      {
        str = str + ", ";  
      }
      out.print(str);      
    }
    out.println();
    out.println("   END");
    out.println("   CLOSE " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    out.println("   DEALLOCATE " + "Cursor_" + ImeTabeleR.replaceAll(" ","_"));
    if (Triger == 2)
    {
      out.println("   END");
    }
    out.println("GO");
    out.println();
  }
       
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }
  }
  public void UpisOsnovniIRIRefIL(Connection con,PrintWriter out, String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaL,int[] IdAtributaR, int IdProjekta, Rekurzije Rek, ClassFunction pom, boolean alter, Connection conn)
  {     
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ParamFunk = pom.ParametriFunkcijeProcedure(IdAtributaL, con, IdProjekta, Rek,1);       
    if (SadrzavanjeIRI == 0)
    {     
      SadrzavanjeIRI(out, ImeTabeleL, ImeTabeleR, ImeAR,ImeAL, ParamFunk, alter, conn);       
      Sadrzavanje = 1;
    }
    if (IzvrsiOkidac == 0)
    {     
      IzvrsiOkidacIRI(out, alter, conn); 
      IzvrsiOkidac = 1;
    }    
    String str="";
    ResultSet rs;
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   { 
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[TRG_"+ ImeTabeleL+"_"+ImeOgranicenja+"_INS"+"]') and OBJECTPROPERTY(id, N'IsTrigger') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      out.println("ALTER TRIGGER TRG_"+ ImeTabeleL.replaceAll(" ","_")+"_"+ImeOgranicenja+"_INS");
    }else
    {
      out.println("CREATE TRIGGER TRG_"+ ImeTabeleL.replaceAll(" ","_")+"_"+ImeOgranicenja+"_INS");
    }
        
    out.println("   ON "+ ImeTabeleL.replaceAll(" ","_"));    
    out.println("   FOR INSERT");
    out.println("AS");
    
    int m = IdAtributaL.length;  
    String[] AtributName = new String[m];
    try {
    out.print("   DECLARE ");
    for (int i=0;i<m;i++)
    {
      String upit = "select Att_mnem, Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+IdAtributaL[i];
      rs=query.select(upit);
      if (rs.next())
      {
        AtributName[i] = rs.getString("Att_mnem");
        out.print("   @" + AtributName[i].replaceAll(" ","_")); 
        int IdDomena = rs.getInt("Dom_id"); 
        query.Close();        
        String TipPodatka=Rek.TraziTip(IdDomena, query, IdProjekta,1); 
        int Duzina = Rek.TraziDuzinu(IdDomena, query, IdProjekta);        
        if (Duzina!=0)            
        {
              TipPodatka = TipPodatka + "(" + Duzina + ")";               
        }
        out.print(" " + TipPodatka);          
        if (i<m-1)
          {
             out.println(",");  
          }else{
            out.println();
          }   
        
      } 
    }
    
    out.print("SELECT ");    
    for (int i=0;i<m;i++)
    {
      str = "@" + AtributName[i].replaceAll(" ","_") + "= " + AtributName[i].replaceAll(" ","_");      
      
      if (i<m-1)
      {
        str = str + ", ";  
      }
    
      out.println(str);
    } 
    
    out.println("FROM Inserted");
    
    String Argumenti="";    
    for (int k=0;k<m;k++)
    {      
      Argumenti = Argumenti + "@" + AtributName[k].replaceAll(" ","_");
      
      if (k<m-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    }
    out.println("IF (dbo.IzvrsiOkidac(TRG_"+ ImeTabeleL.replaceAll(" ","_")+"_"+ImeOgranicenja+"_INS)=0) AND "); 
    out.println("(dbo.SadrzavanjeIRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0)");
    out.println("BEGIN");     
    out.println("RAISERROR('Narusen inverzni ref. integritet',16,1)");
    out.println("ROLLBACK TRAN");     
    out.println("END");
    out.println("GO");
    out.println();
  }
       
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }
    
  } 
  public void UpdatePKey(PrintWriter out, String ImeTabele, String[] ModifableAtr, boolean alter, Connection conn)
  {
    String str="";
    Statement stmt;
    ResultSet rset;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   { 
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[TRG_"+ ImeTabele+"_PKey_UPD]') and OBJECTPROPERTY(id, N'IsTrigger') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
      out.println("ALTER TRIGGER TRG_"+ ImeTabele.replaceAll(" ","_")+"_PKey_UPD");    
    }else
    {
      out.println("CREATE TRIGGER TRG_"+ ImeTabele.replaceAll(" ","_")+"_PKey_UPD");
    }
    out.println("   ON "+ ImeTabele.replaceAll(" ","_"));    
    out.println("   FOR UPDATE");
    out.println("AS");    
    out.print("IF ");
    for (int i=0;i<ModifableAtr.length;i++)
    {
        str = "UPDATE(" + ModifableAtr[i].replaceAll(" ","_") + ")";
        out.print(str);
        if (i<ModifableAtr.length-1)
        {
           out.println(" OR ");  
        }
    } 
    out.println();
    out.println("BEGIN");
    out.println("RAISERROR('Izmena primarnog kljuca nije dozvoljena',16,1)");
    out.println("ROLLBACK TRAN");     
    out.println("END");
    out.println("GO");
    out.println();    
  }
  public void InvezniRIPogled(PrintWriter out,String ImeTabeleL, String ImeTabeleR, String ImeOgranicenja, int[] IdAtributaL,int[] IdAtributaR, int IdProjekta, ClassFunction pom, Connection con, boolean alter, Connection conn, int IdASistema, Rekurzije Rek)
  {     
    JDBCQuery query=new JDBCQuery(con);
    String[] ImeAL = pom.FAtributName(IdAtributaL, con, IdProjekta);
    String[] ImeAR = pom.FAtributName(IdAtributaR, con, IdProjekta);    
    String str;
    Statement stmt;
    ResultSet rset;
    ResultSet rs;
    String Query;
    boolean postoji;
    postoji = false;
   if (alter)
   { 
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[Pogled_"+ImeTabeleL+"_"+ImeTabeleR+"]') and OBJECTPROPERTY(id, N'IsView') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
    Set LIdAtributa = new HashSet(); 
    Set RIdAtributa = new HashSet();    
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
    String[] DeclareVarL = pom.ParametriFunkcijeProcedure(IdAtributa, con, IdProjekta, Rek,1);
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
    String[] DeclareR = new String[RIdAtributa.size()];
    int u=0;
    for (Iterator it=RIdAtributa.iterator(); it.hasNext(); ) 
          {
              Object element = it.next();
              String E = element.toString();
              DeclareR[u] = E;
              u=u+1;
          }
    String[] DeclareVarR = pom.DeklareParametri(DeclareR, con, IdProjekta, Rek,1);    
   
   if (alter && postoji)
   {
      out.println("ALTER VIEW " + "View_"+ImeTabeleL.replaceAll(" ","_")+"_"+ImeTabeleR.replaceAll(" ","_") + " AS");   
   }else
   {
     out.println("CREATE VIEW " + "View_"+ImeTabeleL.replaceAll(" ","_")+"_"+ImeTabeleR.replaceAll(" ","_") + " AS"); 
   }    
    out.print("SELECT ");
    for (int i=0;i<LIdAtributa.toArray().length;i++)
    {
      str= "u."+ LIdAtributa.toArray()[i]+", ";
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
    out.println("GO");
    out.println();   
    if (IzvrsenjeOkidaca == 0)
    {     
      IzvrsenjeOkidacaIRI(out, alter, conn); 
      IzvrsenjeOkidaca = 1;
    }     
    String Argumenti = "";  
    for (int g=0;g<ImenaSvihAtributaL.length;g++)
    {      
      Argumenti = Argumenti + "@" + ImenaSvihAtributaL[g].replaceAll(" ","_");
      
      if (g<ImenaSvihAtributaL.length-1)
      {
        Argumenti = Argumenti + ", ";  
      }
    }     
    String Argumenti1 = "";      
    for (int g=0;g<ImenaSvihAtributaR.length;g++)
    {      
      Argumenti1 = Argumenti1 + "@" + ImenaSvihAtributaR[g].replaceAll(" ","_");
      
      if (g<ImenaSvihAtributaR.length-1)
      {
        Argumenti1 = Argumenti1 + ", ";  
      }
    }    
    postoji = false;
   if (alter)
   { 
    Query = "select * from dbo.sysobjects where id = object_id(N'[dbo].[TRG_"+ ImeOgranicenja+"_INV_Pogled"+"]') and OBJECTPROPERTY(id, N'IsTrigger') = 1";
    try 
    {     
         stmt = conn.createStatement();       
         rset = stmt.executeQuery(Query);
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
   String ArgumentiL = "";      
    for (int g=0;g<ImenaSvihAtributaL.length;g++)
    {      
      ArgumentiL = ArgumentiL  + ImenaSvihAtributaL[g].replaceAll(" ","_");
      
      if (g<ImenaSvihAtributaL.length-1)
      {
        ArgumentiL = ArgumentiL + ", ";  
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
    if (alter && postoji)
    {
      out.println("ALTER TRIGGER "+ "TRG_"+ImeOgranicenja+"_INV_View");         
    }else
    {
      out.println("CREATE TRIGGER "+ "TRG_"+ImeOgranicenja+"_INV_View");   
    }
    out.println("ON View_"+ ImeTabeleL.replaceAll(" ","_")+"_"+ ImeTabeleR.replaceAll(" ","_") + " INSTEAD OF INSERT");        
    out.println("AS");     
    out.println("DECLARE ");
    out.println("   @Idt int,");    
    out.println("   @Count int,");
    for (int g=0;g<DeclareVarL.length;g++)
    {      
      str = "   @" + DeclareVarL[g] + ", ";
      out.println(str);      
    }
    for (int g=0;g<DeclareVarR.length;g++)
    {      
      str = "   @" + DeclareVarR[g];
      if (g < DeclareVarR.length-1)
      {
        str = str + ",";
      }
      out.println(str);      
    }
    out.print("SELECT " );      
    for (int i=0;i<LIdAtributa.toArray().length;i++)
    {
      str= "@"+ LIdAtributa.toArray()[i]+",";
      out.print(str);
    }        
    for (int i=0;i<RIdAtributa.toArray().length;i++)
    {
      str= "@"+ RIdAtributa.toArray()[i];
      if (i<RIdAtributa.toArray().length-1)
      {
        str=str+",";
      }
      out.print(str);
    }    
    str = " FROM Inserted";
    out.println(str);
    out.println("SELECT @Count = COUNT(*) FROM "+ImeTabeleL.replaceAll(" ","_"));    
    out.println("WHERE ");
    
    for (int i=0;i<ImeAL.length;i++)
    {
      str = ImeAL[i].replaceAll(" ","_") + " = @" + ImeAL[i].replaceAll(" ","_");
      if (i<ImeAL.length-1)
      {
        str = str + " AND ";
      }
      out.print(str);
    } 
    out.println("");
    out.println("IF @Count <> 0 ");
    out.println("INSERT INTO " + ImeTabeleR.replaceAll(" ","_"));
    out.println("(" + Argumenti2 +")");
    out.print("VALUES (");
    str = Argumenti1+")";    
    out.println(str);
    out.println("ELSE");
    out.println("   SET @Idt = @@SPID");
    out.println("   exec dbo.IzvrsenjeOkidaca 0, 'UpisIRI_" + ImeTabeleL.replaceAll(" ","_") + "', @Idt");
    out.println("INSERT INTO " + ImeTabeleL.replaceAll(" ","_")); 
    out.println("(" + ArgumentiL +")");
    out.print("VALUES ( ");        
    str = Argumenti+")";    
    out.println(str);    
    out.println("INSERT INTO " + ImeTabeleR.replaceAll(" ","_")); 
    out.println("(" + Argumenti2 +")");
    out.print("VALUES (");
    str = Argumenti1+")";
    out.println(str);
    out.println("   exec dbo.IzvrsenjeOkidaca 1, 'UpisIRI_" + ImeTabeleL.replaceAll(" ","_") + "', @Idt");
    out.println("IF dbo.SadrzavanjeIRI_"+ImeTabeleL.replaceAll(" ","_")+"(" + Argumenti + ")" + " = 0 ");    
    out.println("BEGIN");
    out.println("RAISERROR('Narusen inverzni integritet!',16,1)");
    out.println("ROLLBACK TRAN");
    out.println("END");    
    out.println();
  } 
}