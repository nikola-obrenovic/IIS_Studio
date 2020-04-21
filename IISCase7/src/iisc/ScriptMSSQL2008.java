package iisc;

import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ScriptMSSQL2008 extends ScriptMSSQL {

    public ScriptMSSQL2008(Connection conn, int projId, int appSysId, int[] relSchIds, 
                           String[] relSchNames, boolean doUpgrade, int[] constraints, 
                           PrintWriter warningf, String fileNamePrefix, boolean oneFile, 
                           boolean indP, boolean indF, boolean indU, 
                           CheckConstraint.ImplemetationType chkConImplType, 
                           boolean createCons, boolean createTrgs, String fileName, 
                           String baseName, boolean view, String user, String pass, 
                           String host) {
                           
        super(conn, projId, appSysId, relSchIds, relSchNames, doUpgrade, constraints, 
              warningf, fileNamePrefix, oneFile, indP, indF, indU, chkConImplType, 
              createCons, createTrgs, fileName, baseName, view, user, pass, host);
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
    
}
