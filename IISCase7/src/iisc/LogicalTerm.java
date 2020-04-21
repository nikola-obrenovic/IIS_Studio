package iisc;

import java.sql.Connection;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * LogicalTerm is the object representation of the basic part of a normal form.
 * It is a single condition (boolean expression) not containing AND nor OR.
 * 
 * @author      Nikola Obrenovic (nikob at sbb.rs)
 * @version     0.1 18/05/10
 */
public class LogicalTerm {
    
    protected String leftOperand;
    protected String rightOperand;
    protected String operator;
    protected Set<String> refAttributes = new HashSet<String>();
    protected boolean negated;
    protected HashMap<String, ArrayList<DataTypesEnum>> declarations = new HashMap<String, ArrayList<DataTypesEnum>>();
       
    /**
     * leftRSName, leftRSId and leftAttrId are filled only if the left operand
     * is an attribute.
     */
    protected String leftRSName = "";
    protected int leftRSId = -1;    
    protected int leftAttrId = -1;
        
    /**
     * rightRSName, rightRSId and rightAttrId are filled only if the left operand
     * is an attribute.
     */
    protected String rightRSName = "";
    protected int rightRSId = -1;
    protected int rightAttrId = -1;
    
    public LogicalTerm() {
    }

    public boolean equals(Object obj)
    {
        if(obj == null) return false;
        
        if(!(obj instanceof LogicalTerm )) return false;
        
        LogicalTerm lt = (LogicalTerm) obj;
        
        if(this.leftOperand.equalsIgnoreCase(lt.leftOperand)
            && this.rightOperand.equalsIgnoreCase(lt.rightOperand)
            && this.operator.equalsIgnoreCase(lt.operator)
            && this.negated == lt.negated)
        {
            return true;
        }
        else
            return false;
    }
    
    public int hashCode()
    {
        String hcStr = this.leftOperand.toLowerCase()
                       + this.operator.toLowerCase()
                       + this.rightOperand.toLowerCase();
        if(this.negated)
            hcStr = "NOT(" + hcStr + ")";
            
        return hcStr.hashCode();
    }
    
    public String toString()
    {
        String hcStr = this.leftOperand.toLowerCase()
                       + this.operator.toLowerCase()
                       + this.rightOperand.toLowerCase();
            
        return hcStr;
    }

    public void setLeftOperand(String leftOperand) {
        this.leftOperand = leftOperand;
    }

    public String getLeftOperand() {
        return leftOperand;
    }

    public void setRightOperand(String rightOperand) {
        this.rightOperand = rightOperand;
    }

    public String getRightOperand() {
        return rightOperand;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }


    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    public boolean isNegated() {
        return negated;
    }

    /**
     * Creates and returns deep copy this object. It is used for the 
     * transformation of the user-given check constraint into a normal form.
     * 
     * @return Deep copy of the LogicalTerm.
     */
    public LogicalTerm getDeepCopyOf() {
        LogicalTerm newTerm = new LogicalTerm();
        newTerm.leftOperand = leftOperand;
        newTerm.operator = operator;
        newTerm.rightOperand = rightOperand;
        newTerm.negated = negated;
        
        newTerm.declarations = new HashMap<String, ArrayList<DataTypesEnum>>();
        Iterator<String> it = declarations.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            ArrayList<DataTypesEnum> types = new ArrayList<DataTypesEnum>();
            for(int i=0;i<declarations.get(key).size();i++)
                types.add(declarations.get(key).get(i));
            newTerm.declarations.put(key, types);    
        }
        
        return newTerm;
    }

    public Set<String> getRefAttributes() {
        return refAttributes;            
    }

    public void setLeftRSId(int leftRSId) {
        this.leftRSId = leftRSId;
    }

    public int getLeftRSId() {
        return leftRSId;
    }

    public void setRightRSId(int rightRSId) {
        this.rightRSId = rightRSId;
    }

    public int getRightRSId() {
        return rightRSId;
    }

    /**
     * Loads the logical term from the IISCase repository and creates and 
     * returns new LogicalTerm object.
     *  
     * @param con Repository JDBC connection. 
     * @param projID Project ID.
     * @param appSysID Application system ID.
     * @param chkConID Check constraint ID.
     * @param nfID Normal form ID.
     * @param nfPartID Normal form part ID.
     * @param termId Logical term ID.
     * @return New LogicalTerm object filled with data from the repository.
     */
    static LogicalTerm loadLogTerm(Connection con, int projID, int appSysID, 
                                   int chkConID, int nfID, int nfPartID, 
                                   int termId) {
                                   
        JDBCQuery q = new JDBCQuery(con);
        ResultSet rs;
        
        try
        {
            //load operator
            rs = q.select("select *  from IISC_CHKC_BASIC_TERM where PR_ID=" + projID + " and AS_ID=" + appSysID 
                                 + " and CHKC_ID=" + chkConID + " and NF_ID=" + nfID + " and NF_PART_ID=" + nfPartID 
                                 + " and BT_ID=" + termId);
            LogicalTerm term = new LogicalTerm();
            while(rs.next())
            {
                term.setNegated(rs.getBoolean("BT_NEGATED"));
                term.setLeftOperand(rs.getString("BT_LEFT_OPERAND"));
                term.setOperator(rs.getString("BT_OPERATOR"));
                term.setRightOperand(rs.getString("BT_RIGHT_OPERAND"));
                term.setLeftRSId(rs.getInt("BT_LEFT_RS_ID"));
                term.setRightRSId(rs.getInt("BT_RIGHT_RS_ID"));
            }
            q.Close();
            
            term.createDeclarations(con, projID);
            
            if(term.getLeftRSId() != -1){
                rs = q.select("select RS_NAME  from IISC_RELATION_SCHEME where PR_ID=" + projID + " and RS_ID=" + term.getLeftRSId()); 
                while(rs.next())
                {
                    term.setLeftRSName(rs.getString("RS_NAME"));
                }
                q.Close();
            }

            if(term.getRightRSId() != -1){
                rs = q.select("select RS_NAME  from IISC_RELATION_SCHEME where PR_ID=" + projID + " and RS_ID=" + term.getRightRSId()); 
                while(rs.next())
                {
                    term.setRightRSName(rs.getString("RS_NAME"));
                }
                q.Close();
            }

            return term;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
    }

    public void setLeftRSName(String leftRSName) {
        this.leftRSName = leftRSName;
    }

    public String getLeftRSName() {
        return leftRSName;
    }

    public void setRightRSName(String rightRSName) {
        this.rightRSName = rightRSName;
    }

    public String getRightRSName() {
        return rightRSName;
    }

    public void setLeftAttrId(int leftAttrId) {
        this.leftAttrId = leftAttrId;
    }

    public int getLeftAttrId() {
        return leftAttrId;
    }

    public void setRightAttrId(int rightAttrId) {
        this.rightAttrId = rightAttrId;
    }

    public int getRightAttrId() {
        return rightAttrId;
    }
    
    
    
    /**
     * Generates String representation of the logical term in SQL syntax. It is 
     * used for the generation of the string representation of the whole normal
     * form.
     * 
     * @return String representation of the LogicalTerm in SQL syntax.
     */
    public String toString(boolean inclTable)
    {
        String ret = "";
        if(leftAttrId != -1)
        {
            if(inclTable && !leftRSName.equalsIgnoreCase(""))
                ret += leftRSName + ".";
            ret += leftOperand;
        }
        else
        {
            boolean isNumber = true;
            try{Float.parseFloat(leftOperand);}
            catch(Exception e){isNumber = false;}
            
            if(isNumber || leftOperand.equalsIgnoreCase("VALUE")) ret += leftOperand;
            else ret += "'" + leftOperand + "'";
        }
        
        if(operator.trim().equalsIgnoreCase("LIKE") || operator.trim().equalsIgnoreCase("IN"))
            ret += " " + operator + " ";
        else
            ret += operator;
        
        if(rightAttrId != -1)
        {
            if(inclTable && !rightRSName.equalsIgnoreCase(""))
                ret += rightRSName + ".";
            ret += rightOperand;
        }
        else
        {
            boolean isNumber = true;
            try{Float.parseFloat(rightOperand);}
            catch(Exception e){isNumber = false;}
            
            if(isNumber || rightOperand.equalsIgnoreCase("VALUE") || operator.trim().equalsIgnoreCase("IN")) ret += rightOperand;
            else ret += "'" + rightOperand + "'";
        }
            
        if(negated)
            ret = "NOT(" + ret + ")";    
            
        return ret;
    }


    protected void createDeclarations(Connection con, int projID) {
                                           
        String[] operParts = leftOperand.split("[\\W]");
        
        for(int i=0;i<operParts.length;i++)
        {
            boolean success = createFunctionDeclaration(con, projID, operParts[i], declarations);
            if(!success)
                createAttributeDeclaration(con, projID, operParts[i], declarations);
        }
        
        operParts = rightOperand.split("[\\W]");
                
        for(int i=0;i<operParts.length;i++)
        {
            boolean success = createFunctionDeclaration(con, projID, operParts[i], declarations);
            if(!success)
                createAttributeDeclaration(con, projID, operParts[i], declarations);
        }
    }

    private boolean createFunctionDeclaration(Connection con, int projID, String name,
                                              HashMap<String, ArrayList<DataTypesEnum>> declarations) {
                                              
        JDBCQuery q = new JDBCQuery(con);
        ResultSet rs;
        
        try
        {
            //load operator
            rs = q.select("select *  from IISC_FUNCTION where FUN_NAME='" + name + "' and PR_ID=" + projID);
            if(rs==null || !rs.next())
            {
                //q.Close();
                return false;
            }
            
            ArrayList<DataTypesEnum> decl = new ArrayList<DataTypesEnum>();
            int funcID = -1;
            do
            {
                if(rs.getInt("DOM_ID") != -1)
                    decl.add(resolveDataType(con, projID, rs.getInt("DOM_ID")));
                funcID = rs.getInt("FUN_ID");
                
            }while(rs.next());
            
            rs = q.select("select *  from IISC_FUN_PARAM where FUN_ID=" + funcID + " and PR_ID=" + projID);
            if(rs==null || !rs.next())
            {
                decl.add(DataTypesEnum.VOID);
            }
            else
            {
                do
                {
                    if(rs.getInt("DOM_ID") != -1)
                        decl.add(resolveDataType(con, projID, rs.getInt("DOM_ID")));
                }while(rs.next());
            }
            
            declarations.put(name, decl);
            
            q.Close();
            
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private boolean createAttributeDeclaration(Connection con, int projID, String name, 
                                            HashMap<String, ArrayList<DataTypesEnum>> declarations) {
                                            
        JDBCQuery q = new JDBCQuery(con);
        ResultSet rs;
        
        try
        {
            //load operator
            rs = q.select("select *  from IISC_ATTRIBUTE where ATT_MNEM='" + name + "' and PR_ID=" + projID);
            if(rs==null || !rs.next())
            {
                //q.Close();
                return false;
            }
            
            ArrayList<DataTypesEnum> decl = new ArrayList<DataTypesEnum>();
            int funcID = -1;
            do
            {
                int domID = rs.getInt("Dom_id");
                if(domID != -1)
                    decl.add(resolveDataType(con, projID, domID));
            }while(rs.next());
            
            declarations.put(name, decl);
            
            q.Close();
            
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private DataTypesEnum resolveDataType(Connection con, int projID, int domID) {
        
        JDBCQuery q = new JDBCQuery(con);
        ResultSet rs;
        
        try
        {
            //load operator
            rs = q.select("select DOM_DATA_TYPE  from IISC_DOMAIN where DOM_ID=" + domID + " and PR_ID=" + projID);
            DataTypesEnum type = DataTypesEnum.VOID;
            while(rs.next())
            {
                switch(rs.getInt("DOM_DATA_TYPE"))
                {
                    case 0: case 3:
                        type = DataTypesEnum.STRING;
                        break;
                    case 1:
                        type = DataTypesEnum.INTEGER;
                        break;
                    case 2:
                        type = DataTypesEnum.FLOAT;
                        break;    
                    case 4:
                        type = DataTypesEnum.BOOLEAN;
                        break;
                    case 5: case 6:
                        type = DataTypesEnum.DATE;
                        break;
                };
                
            };
            
            q.Close();
            
            return type;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return DataTypesEnum.VOID;
        }
    }

    public void setDeclarations(HashMap<String, ArrayList<DataTypesEnum>> declarations) {
        this.declarations = declarations;
    }

    public HashMap<String, ArrayList<DataTypesEnum>> getDeclarations() {
        return declarations;
    }
}
