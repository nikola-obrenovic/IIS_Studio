package iisc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * NormalForm is an object representation of DNF or CNF of check constraint.
 * NormalForm consists of NormalFormParts linked with OR or AND, in case of DNF
 * or CNF, respectively.
 * 
 * @author      Nikola Obrenovic (nikob at sbb.rs)
 * @version     0.1 01/06/10
 */
public class NormalForm {
    
    protected ArrayList<NormalFormPart> parts = new ArrayList<NormalFormPart>();
    protected String operator = "";
    
    public NormalForm() {
    }

    public NormalForm getDeepCopyOf(){
        
        NormalForm ret = new NormalForm();
        ret.operator = this.operator;
        ret.parts = new ArrayList<NormalFormPart>();
        for(int i=0;i<parts.size();i++){
            ret.parts.add(parts.get(i).getDeepCopyOf());
        }
        return ret;
    }

    public void setParts(ArrayList<NormalFormPart> disjuncts) {
        this.parts = disjuncts;
    }

    public ArrayList<NormalFormPart> getParts() {
        return parts;
    }
    
    /**
     * Adds given LogicalTerm to the last NormalFormPart in this NormalForm.
     * 
     * @param term
     */
    public void addBasicTerm(LogicalTerm term)
    {
        if(parts.size() != 0)
            parts.get(parts.size()-1).getBasicTerms().add(term);
    }
    
    /**
     * Creates and adds a new NormalFormPart with the given operator (AND/OR)
     * to this NormalForm.
     * 
     * @param oper Operator (AND/OR)
     */
    public void addNFPart(String oper)
    {
        parts.add(new NormalFormPart(oper));
    }
    
    /**
     * Returns names of attributes referenced by the NormalForm.
     * @return Set of attribute names.
     */
    public Set<String> getAllAttrNames()
    {
        Set<String> ret = new HashSet<String>();
        for(int i=0;i<parts.size();i++)
            for(int j=0;j<parts.get(i).getBasicTerms().size();j++)
                ret.addAll(parts.get(i).getBasicTerms().get(j).getRefAttributes());
                
        return ret;
    }
    
    /**
     * Returns IDs of attributes referenced by the NormalForm.
     * @return Set of attribute IDs as Strings.
     */
    public Set<String> getAllAttrIDs()
    {
        Set<String> ret = new HashSet<String>();
        for(int i=0;i<parts.size();i++)
            for(int j=0;j<parts.get(i).getBasicTerms().size();j++)
            {
                LogicalTerm term = parts.get(i).getBasicTerms().get(j);
                if(term.getLeftAttrId() != -1)
                    ret.add(""+term.getLeftAttrId());
                
                if(term.getRightAttrId() != -1)
                    ret.add(""+term.getRightAttrId());
            }
        return ret;
    }

    /**
     * Loads the normal form from the IISCase repository and creates and 
     * returns new NormalForm object.
     *  
     * @param con Repository JDBC connection. 
     * @param projID Project ID.
     * @param appSysID Application system ID.
     * @param chkConID Check constraint ID.
     * @param nfId Normal form ID.
     * @return New NormalForm object filled with data from the repository.
     */
    public static NormalForm loadNormalForm(Connection con, int projID, int appSysID, int chkConID, int nfId) {
        NormalForm nf = new NormalForm();
        
        JDBCQuery q = new JDBCQuery(con);
        
        ResultSet rs;
        
        try
        {
            //load operator
            rs=q.select("select OPERATOR  from IISC_CHKC_NORMAL_FORM where PR_ID=" + projID + " and AS_ID=" + appSysID 
                                                        + " and CHKC_ID=" + chkConID + " and NF_ID=" + nfId);
            while(rs.next())
            { 
                 nf.operator = rs.getString("OPERATOR");
            }
            q.Close();
             
            rs=q.select("select NF_PART_ID  from IISC_CHKC_NF_PART where PR_ID=" + projID + " and AS_ID=" + appSysID 
                        + " and CHKC_ID=" + chkConID + " and NF_ID=" + nfId);
            while(rs.next())
            { 
                NormalFormPart nfp = NormalFormPart.loadNormalFormPart(con, projID, appSysID, chkConID, nfId,rs.getInt("NF_PART_ID"));
                if(nfp != null)
                    nf.parts.add(nfp);
            }
            q.Close();
            
            nf.findAttributeIDs(con, projID);
  
            return nf;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
        
    }
    
    /**
     * Generates String representation of the normal form in SQL syntax.
     * 
     * @return String representation of the normal form in SQL syntax.
     */
    public String toString(boolean inclTable)
    {
        String ret = "";
        
        if(parts.size()==1)
        {
            ret = parts.get(0).toString(inclTable);
        }
        else if(parts.size()>1)
        {
            for(int i=0;i<parts.size();i++)
            {
                if(i!=0) ret += " " + operator + " ";
                ret += "(" + parts.get(i).toString(inclTable) + ")";    
            }
        }
        
        ret = ret.replaceAll("==","=");
        
        return ret;
    }


    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
    
    /**
     * Finds IDs of the attributes referenced by the normal form. Attribute IDs
     * are places in LogicalTerm.leftAttrId and LogicalTerm.rightAttrId 
     * attributes.
     * 
     * @param conn JDBC connection.
     * @param projectID Project ID.
     */
    public void findAttributeIDs(Connection conn, int projectID) {
                         
        try
        {
            //first find attributes data
            JDBCQuery query = new JDBCQuery(conn);
            ResultSet rs;
            for(int i=0;i<parts.size();i++)
            {
                for(int j=0;j<parts.get(i).getBasicTerms().size();j++)
                {
                    LogicalTerm term = parts.get(i).getBasicTerms().get(j);
                    
                    String leftOper = term.getLeftOperand();
                    if(!leftOper.startsWith("'"))
                        leftOper = "'" + leftOper + "'";
                    rs = query.select("select ATT_ID from IISC_ATTRIBUTE where PR_ID=" + projectID 
                                 + " and ATT_MNEM=" + leftOper);
                    
                    if(rs != null && rs.next())
                    {
                        term.setLeftAttrId(rs.getInt("ATT_ID"));
                        rs.close();
                    }
                    
                    String rightOper = term.getRightOperand();
                    if(!rightOper.startsWith("'"))
                        rightOper = "'" + rightOper + "'";
                    rs = query.select("select ATT_ID from IISC_ATTRIBUTE where PR_ID=" + projectID 
                                 + " and ATT_MNEM=" + rightOper);
                    
                    if(rs != null && rs.next())
                    {
                        term.setRightAttrId(rs.getInt("ATT_ID"));
                        rs.close();
                    }
                }
            }
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
                
    }
}
