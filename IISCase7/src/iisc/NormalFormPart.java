package iisc;

import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;

/**
 * 
 * NormalFormPart is a part of a normal form that represent subformula contained
 * in one pair of brackets. In case of DNF, NormalFormPart is formula consisted 
 * of LogicalTerms linked with AND, and in case of CNF, LogicalTerms are linked 
 * with OR.
 * 
 * @author      Nikola Obrenovic (nikob at sbb.rs)
 * @version     0.1 20/05/10
 */
public class NormalFormPart {
    
    protected ArrayList<LogicalTerm> basicTerms = new ArrayList<LogicalTerm>();
    protected String operator = "";
    
    public NormalFormPart() {
    }
    
    public NormalFormPart(String oper) {
        operator = oper;
    }

    public void setBasicTerms(ArrayList<LogicalTerm> conjucts) {
        this.basicTerms = conjucts;
    }

    public ArrayList<LogicalTerm> getBasicTerms() {
        return basicTerms;
    }
    
    /**
     * Generates String representation of the normal form part in SQL syntax.
     * 
     * @return String representation of the normal form part in SQL syntax.
     */
    public String toString(boolean inclTable)
    {
        String ret = "";
        
        for(int i=0;i<basicTerms.size();i++){
            if(i!=0) ret += " " + operator + " ";
            ret += basicTerms.get(i).toString(inclTable);    
        }
        return ret;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
    
    /**
     * Loads the normal form part from the IISCase repository and creates and 
     * returns new NormalFormPart object.
     *  
     * @param con Repository JDBC connection. 
     * @param projID Project ID.
     * @param appSysID Application system ID.
     * @param chkConID Check constraint ID.
     * @param nfID Normal form ID.
     * @param nfPartID Normal form part ID.
     * @return New NormalFormPart object filled with data from the repository.
     */
    public static NormalFormPart loadNormalFormPart(Connection con, int projID, int appSysID, int chkConID, int nfID, int nfPartID) {
        
        JDBCQuery q = new JDBCQuery(con);
        
        ResultSet rs;
        
        try
        {
            NormalFormPart part = new NormalFormPart();
            
            //load operator
            rs=q.select("select OPERATOR  from IISC_CHKC_NF_PART where PR_ID=" + projID + " and AS_ID=" + appSysID 
                                        + " and CHKC_ID=" + chkConID + " and NF_ID=" + nfID + " and NF_PART_ID=" + nfPartID);
            while(rs.next())
            { 
                 part.operator = rs.getString("OPERATOR");
            }
            q.Close();
             
            rs=q.select("select BT_ID  from IISC_CHKC_BASIC_TERM where PR_ID=" + projID + " and AS_ID=" + appSysID 
                         + " and CHKC_ID=" + chkConID + " and NF_ID=" + nfID + " and NF_PART_ID=" + nfPartID);
            while(rs.next())
            { 
                int termId = rs.getInt("BT_ID");
                LogicalTerm term = LogicalTerm.loadLogTerm(con, projID, appSysID, chkConID, nfID, nfPartID, termId);
                part.basicTerms.add(term);
            }
            q.Close();
    
            return part;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
        
    }
    
    public NormalFormPart getDeepCopyOf(){
        
        NormalFormPart ret = new NormalFormPart();
        ret.operator = this.operator;
        ret.basicTerms = new ArrayList<LogicalTerm>();
        for(int i=0;i<basicTerms.size();i++){
            ret.basicTerms.add(basicTerms.get(i).getDeepCopyOf());
        }
        return ret;
    }
}
