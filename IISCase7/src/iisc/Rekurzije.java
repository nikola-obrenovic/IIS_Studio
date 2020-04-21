package iisc;
import java.sql.*;

import java.util.ArrayList;

import javax.swing.JOptionPane;
public class Rekurzije 
{
  public Rekurzije()
  {
  }
  String RekZaDefault(int IdDomena,JDBCQuery query,int IdProjekta)
  {
        ResultSet rs;
        String upit3 = "select Dom_default,Dom_parent from IISC_DOMAIN where PR_id="+IdProjekta+" and Dom_id="+IdDomena;
        try {
          rs=query.select(upit3);
          rs.next();           
          String DomenDefault = rs.getString(1);          
          int DomenParent=rs.getInt(2);
          query.Close(); 
          if (DomenDefault!=null)
          {
            DomenDefault=DomenDefault.trim();
          }
          if (DomenDefault==null || DomenDefault.length()==0)
          {
             if (DomenParent!=0) 
             {             
                DomenDefault=RekZaDefault(DomenParent,query,IdProjekta);                
             }else
             {
                DomenDefault="";              
             }
          }   
          return DomenDefault;
        }
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          return "";
                          }
  }
  
  String RekZaIzraz(int IdDomena,JDBCQuery query,int IdProjekta)
  {
        ResultSet rs;
        String upit3 = "select Dom_reg_exp_str,Dom_parent from IISC_DOMAIN where PR_id="+IdProjekta+" and Dom_id="+IdDomena;
        try {
          rs=query.select(upit3);
          rs.next();           
          String DomenIzraz = rs.getString(1);          
          int DomenParent=rs.getInt(2);
          query.Close();           
          if (DomenIzraz==null || DomenIzraz.length()==0)
          {
             if (DomenParent!=0) 
             {             
                DomenIzraz=RekZaIzraz(DomenParent,query,IdProjekta);                
             }else
             {
                DomenIzraz="";              
             }
          }   
          return DomenIzraz;
        }
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          return "";
                          }
  }
  String AtributiDomena(int AtrId, JDBCQuery query,Rekurzije Rek, int IdProjekta)
  {
    ResultSet rs;     
    String Domen="";
    String upit2 = "select Att_mnem,Dom_id from IISC_ATTRIBUTE where PR_id="+IdProjekta+" and Att_id="+AtrId+" and Att_sbp=1";
    try {
    rs=query.select(upit2);
    if(rs.next())
        {           
          String ImeAtr=rs.getString(1);
          int IdDomena = rs.getInt(2);        
          query.Close();
          
          ImeAtr = ImeAtr.replaceAll(" ","_");             
         
          String upit3 = "select Dom_mnem from IISC_DOMAIN where PR_id="+IdProjekta+" and Dom_id="+IdDomena;
          rs=query.select(upit3);
          rs.next();
          Domen = rs.getString(1);           
          query.Close(); 
          Domen=Domen.replaceAll(" ","_");                
       } else 
       {
        query.Close();
       }
       return Domen;
    }catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          return "";
          } 
  }
  String TraziTip(int IdDomena,JDBCQuery query, int IdProjekta, int SUBP)
  {
        ResultSet rs;
        String Tip="";
        String upit3 = "select Dom_parent,Dom_data_type,Dom_type,Dom_mnem from IISC_DOMAIN where PR_id="+IdProjekta+" and Dom_id="+IdDomena;
        try {
          rs=query.select(upit3);
          rs.next();                    
          int DomenParent=rs.getInt(1);
          int DomenTip=rs.getInt(2);
          int DomenType = rs.getInt(3);
          String NazivDomena = rs.getString(4);
          query.Close();      
        if (SUBP==2)
        {
          if (DomenType==0 || DomenType==1)
          {
          if (DomenParent!=0) 
          {             
              Tip=TraziTip(DomenParent,query,IdProjekta, SUBP);                
          }else
          {
              String upit4 = "select PT_mnemonic from IISC_PRIMITIVE_TYPE where IISC_PRIMITIVE_TYPE.PT_id="+DomenTip;
              rs=query.select(upit4);
              rs.next();              
              Tip=rs.getString(1);        
              query.Close();
          }  
         }else if (DomenType==2 && SUBP == 2)
         {
           Tip = "O_" + NazivDomena;
         }else if (DomenType==4 && SUBP == 2)
         {
           Tip = "T_" + NazivDomena;
         }
      }else
      {
          if (DomenParent!=0) 
          {             
              Tip=TraziTip(DomenParent,query,IdProjekta, SUBP);                
          }else
          {
              String upit4 = "select PT_mnemonic from IISC_PRIMITIVE_TYPE where IISC_PRIMITIVE_TYPE.PT_id="+DomenTip;
              rs=query.select(upit4);
              rs.next();              
              Tip=rs.getString(1);        
              query.Close();
          }  
      }
          return Tip;
        }
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          return "";
                          }
  }
  
  int TraziDuzinu(int IdDomena, JDBCQuery query, int IdProjekta)
  {
    ResultSet rs;
    String upit = "select Dom_length,Dom_parent from IISC_DOMAIN where PR_id="+IdProjekta+" and Dom_id="+IdDomena;
    try {
      rs=query.select(upit);
      rs.next();              
      int Duzina=rs.getInt(1);  
      int DomenParent=rs.getInt(2);
      query.Close(); 
      if (DomenParent!=0) 
      {             
              Duzina=TraziDuzinu(DomenParent,query,IdProjekta);                
      }
      return Duzina;
    }
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          return 0;
                          }
  }

    /*nobrenovic: start*/
    public ArrayList<CheckConstraint> findDomChkCon(Connection con, int projID, int apSysID, int domID) {
        
        JDBCQuery query = new JDBCQuery(con);
        ArrayList<CheckConstraint> ret = new ArrayList<CheckConstraint>();
        try {
            ResultSet rs = query.select("select CHKC_ID from IISC_CHECK_CONSTRAINT where PR_ID=" + projID 
                                    + " and DOM_ID=" + domID);
        
            if(rs != null && rs.next())
            {
                int chkID = rs.getInt(1);
                ret.add(CheckConstraint.loadCheckConstraint(con, projID, apSysID, chkID));
                query.Close();
            }
            
            rs = query.select("select Dom_parent from IISC_DOMAIN where PR_id="+projID+" and Dom_id="+domID);
            if(rs != null && rs.next())
            {
                int parentDom = rs.getInt(1);
                ret.addAll(findDomChkCon(con, projID, apSysID, parentDom));
                query.Close();
            }
        }
        catch (SQLException ev) {
            ev.printStackTrace();
        }
        return ret;
    }
    /*nobrenovic: stop*/
}
