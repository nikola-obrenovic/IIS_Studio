package iisc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.sql.Connection;
import java.sql.ResultSet;

import java.util.Iterator;

/**
 * 
 * Class is an object representation of check constraint.
 * 
 * @author      Nikola Obrenovic (nikob at sbb.rs)
 * @version     0.1 02/08/10
 */
public class CheckConstraint {
    
    protected int id = -1;
    protected String name = "";
    protected String origCon = "";
    protected String description = "";
    protected NormalForm cnf = null;
    protected NormalForm dnf = null;
    protected ArrayList<String> refRelSch = new ArrayList<String>();
    protected ArrayList<Integer> refRelSchIds = new ArrayList<Integer>();
    protected int domId = -1;
    protected int attrId = -1;
    
    
    public CheckConstraint() {
    }
    
    public CheckConstraint getDeepCopyOf(){
        
        CheckConstraint ret = new CheckConstraint();
        ret.id = this.id;
        ret.name = this.name;
        ret.origCon = this.origCon;
        ret.description = this.description;
        if(this.dnf != null)
            ret.dnf = this.dnf.getDeepCopyOf();
        if(this.cnf != null)
            ret.cnf = this.cnf.getDeepCopyOf();
        ret.domId= this.domId;
        ret.attrId = this.attrId;
        
        ret.refRelSch = new ArrayList<String>();
        for(int i=0;i<this.refRelSch.size();i++){
            ret.refRelSch.add(this.refRelSch.get(i));
        }
        
        ret.refRelSchIds = new ArrayList<Integer>();
        for(int i=0;i<this.refRelSchIds.size();i++){
            ret.refRelSchIds.add(new Integer(this.refRelSchIds.get(i)));
        }
        return ret;
    }
    
    public ArrayList<LogicalTerm> getAllBasicTermsCNF(){
    
        ArrayList<LogicalTerm> ret = new ArrayList<LogicalTerm>();
        for(int i=0;i<cnf.getParts().size();i++){
            NormalFormPart nfp = cnf.getParts().get(i);
            for(int j=0;j<nfp.getBasicTerms().size();j++)
                ret.add(nfp.getBasicTerms().get(j));
        }
        
        return ret;
    }
    
    public ArrayList<LogicalTerm> getAllBasicTermsDNF(){
    
        ArrayList<LogicalTerm> ret = new ArrayList<LogicalTerm>();
        for(int i=0;i<dnf.getParts().size();i++){
            NormalFormPart nfp = dnf.getParts().get(i);
            for(int j=0;j<nfp.getBasicTerms().size();j++)
                ret.add(nfp.getBasicTerms().get(j));
        }
        
        return ret;
    }

    /**
     * Returns the set of attribute names referenced by the check constraint.
     * @return Attribute names referenced by the check constraint.
     */
    public Set<String> getAllAttrNames()
    {
        if(cnf != null)
            return cnf.getAllAttrNames();
        else if(dnf != null)
            return dnf.getAllAttrNames();
        else
            return null;
    }
    
     /**
      * Returns the set of attribute IDs referenced by the check constraint.
      * @return Attribute IDs referenced by the check constraint.
      */
    public Set<String> getAllAttrIDs()
    {
        if(cnf != null)
            return cnf.getAllAttrIDs();
        else if(dnf != null)
            return dnf.getAllAttrIDs();
        else
            return null;
    }
    
    /**
     * Loads the check constraint from the IISCase repository and creates and 
     * returns new CheckConstraint object.
     *  
     * @param con Repository JDBC connection. 
     * @param projID Project ID.
     * @param appSysID Application system ID.
     * @param chkConID Check constraint ID.
     * @return New CheckConstraint object filled with data from the repository.
     */
    public static CheckConstraint loadCheckConstraint(Connection con, int projID, int appSysID, int chkConID) {
        
        JDBCQuery q = new JDBCQuery(con);
        ResultSet rs;
        
        try
        {
            CheckConstraint chkCon = new CheckConstraint();
            
            chkCon.id = chkConID;
            
            //load orig constraint
            rs=q.select("select *  from IISC_CHECK_CONSTRAINT where PR_ID=" + projID + " and AS_ID=" + appSysID + " and CHKC_ID=" + chkConID);
            while(rs.next())
            { 
                chkCon.name = rs.getString("CHKC_Name");
                chkCon.description = rs.getString("CHKC_Desc");
                chkCon.origCon = rs.getString("ORIG_CON");
                chkCon.attrId = rs.getInt("ATTR_ID");
                chkCon.attrId = rs.getInt("DOM_ID");
            }
            q.Close();
             
            rs=q.select("select NF_ID, OPERATOR  from IISC_CHKC_NORMAL_FORM where PR_ID=" + projID + " and AS_ID=" + appSysID + " and CHKC_ID=" + chkConID);
            while(rs.next())
            { 
                if("AND".equalsIgnoreCase(rs.getString("OPERATOR")))
                    chkCon.cnf = NormalForm.loadNormalForm(con, projID, appSysID, chkConID,rs.getInt("NF_ID"));
                else
                    chkCon.dnf = NormalForm.loadNormalForm(con, projID, appSysID, chkConID,rs.getInt("NF_ID"));
            }
            q.Close();
            
            //find referenced relation schemes
             rs=q.select("select IISC_RELATION_SCHEME.RS_ID, RS_name  from IISC_RELATION_SCHEME, IISC_RSC_RS_SET, IISC_CHECK_CONSTRAINT " +
                        " where IISC_RELATION_SCHEME.RS_id=IISC_RSC_RS_SET.RS_id" +
                        " and IISC_RELATION_SCHEME.AS_id=IISC_RSC_RS_SET.AS_id" +
                        " and IISC_RELATION_SCHEME.PR_id=IISC_RSC_RS_SET.PR_id" + 
                        " and IISC_CHECK_CONSTRAINT.RS_Set_id=IISC_RSC_RS_SET.RS_Set_id" +
                        " and IISC_CHECK_CONSTRAINT.AS_id=IISC_RSC_RS_SET.AS_id" +
                        " and IISC_CHECK_CONSTRAINT.PR_id=IISC_RSC_RS_SET.PR_id" + 
                        " and IISC_CHECK_CONSTRAINT.PR_id = " + projID + 
                        " and IISC_CHECK_CONSTRAINT.AS_id="  + appSysID + 
                        " and IISC_CHECK_CONSTRAINT.CHKC_id=" + chkConID);
         
            while(rs.next())
            { 
                chkCon.refRelSchIds.add(rs.getInt("RS_ID"));
                chkCon.refRelSch.add(rs.getString("RS_name"));
            }
            q.Close();
            
            return chkCon;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
        
    }
    
    public void setOrigCon(String origCon) {
        this.origCon = origCon;
    }

    public String getOrigCon() {
        return origCon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setRefRelSch(ArrayList<String> refRelSch) {
        this.refRelSch = refRelSch;
    }

    public ArrayList<String> getRefRelSch() {
        return refRelSch;
    }

    public void setRefRelSchIds(ArrayList<Integer> refRelSchIds) {
        this.refRelSchIds = refRelSchIds;
    }

    public ArrayList<Integer> getRefRelSchIds() {
        return refRelSchIds;
    }

    public void setDomId(int domId) {
        this.domId = domId;
    }

    public int getDomId() {
        return domId;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    public int getAttrId() {
        return attrId;
    }

    public void setCnf(NormalForm cnf) {
        this.cnf = cnf;
    }

    public NormalForm getCnf() {
        return cnf;
    }

    public void setDnf(NormalForm dnf) {
        this.dnf = dnf;
    }

    public NormalForm getDnf() {
        return dnf;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * Enum that marks normal form of the check constraint.
     */
    public enum ImplemetationType {
        DNF, CNF
    }

}
