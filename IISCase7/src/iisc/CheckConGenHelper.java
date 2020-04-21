package iisc;

import java.sql.Connection;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * CheckConGenHelper contains helper methods for the generation of check 
 * constraints.
 * 
 * @author      Nikola Obrenovic (nikob at sbb.rs)
 * @version     0.1 25/07/10
 */
public class CheckConGenHelper {
    public CheckConGenHelper() {
    
    }
    
    /**
     * Finds check constraint for only an attribute. 
     * 
     * @param con
     * @param projID
     * @param apSysID
     * @param domID
     * @param attrID
     * @return
     */
    static public ArrayList<CheckConstraint> findAttrChkCon(Connection con, int projID, int apSysID, int attrID) 
    {
        ArrayList<CheckConstraint> ret = new ArrayList<CheckConstraint>();
        try{
            JDBCQuery query = new JDBCQuery(con);
            ResultSet rs = query.select("select CHKC_ID from IISC_CHECK_CONSTRAINT where PR_ID=" + projID
                                        + " and ATTR_ID=" + attrID);
            if(rs != null && rs.next())
            {
                int chkID = rs.getInt(1);
                query.Close();
                ret.add(CheckConstraint.loadCheckConstraint(con,projID,apSysID,chkID));
            }
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return ret;
    }
    
    /**
     * Finds check constraint for an attribute. First, the function searches for
     * the check constraint defined on the attribute itself. If it is not 
     * found, check constraint is looked for at the domain of the attribute or 
     * at any domain which is hierarchically above the attribute's domain.
     * 
     * @param con
     * @param projID
     * @param apSysID
     * @param domID
     * @param attrID
     * @return
     */
    static public ArrayList<CheckConstraint> findDomAttrChkCon(Connection con, int projID, int apSysID, int domID, int attrID) 
    {
        ArrayList<CheckConstraint> ret = new ArrayList<CheckConstraint>();
        try{
            JDBCQuery query = new JDBCQuery(con);
            ResultSet rs = query.select("select CHKC_ID from IISC_CHECK_CONSTRAINT where PR_ID=" + projID
                                        + " and ATTR_ID=" + attrID);
            if(rs != null && rs.next())
            {
                int chkID = rs.getInt(1);
                query.Close();
                ret.add(CheckConstraint.loadCheckConstraint(con,projID,apSysID,chkID));
            }
            
            Rekurzije rek = new Rekurzije();
            ret.addAll(rek.findDomChkCon(con, projID, apSysID, domID));
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return ret;
    }
    
    /**
     * Function generates WHERE clause that joins relation schemes given in relSchIds.
     * 
     * @param conn DB connection
     * @param projId 
     * @param appSysId
     * @param relSchIds IDs of relation schemes that need to be joined
     * @return WHERE clause as a String
     */
    static public String GenerateJoinWhereClause(Connection conn, int projId, int appSysId, ArrayList<Integer> relSchIds)
    {
        Synthesys synt = new Synthesys();
        synt.pr = projId;
        synt.as = appSysId;
        synt.con = conn;
        
        String ret = "";
        
        Set<RelationScheme> rsSet = new HashSet<RelationScheme>();
        ArrayList<RelationScheme> rsList = new ArrayList<RelationScheme>();
        for(int i=0;i<relSchIds.size();i++)
        {
            rsSet.add(synt.getRelationScheme(relSchIds.get(i)));
            rsList.add(synt.getRelationScheme(relSchIds.get(i)));
        }
        
        for(int i=0;i<rsList.size();i++)
        {
            String nq = getQuery(conn,projId,appSysId,rsSet,rsList.get(i));
            if(!ret.equalsIgnoreCase("") && !nq.equalsIgnoreCase(""))
                ret += "\r\n        AND ";
            ret += nq;    
            Iterator<RelationScheme> it = rsSet.iterator();
            while(it.hasNext())
            {
                RelationScheme rsDone = it.next();
                if(rsDone.id == rsList.get(i).id)
                {
                    rsSet.remove(rsDone);
                    break;
                }
            }
        }
        
        return ret;
    }
    
    /**
     * Generates WHERE clause that joins table given as rs with the set of tables
     * given as rs_set.
     * 
     * @param conn
     * @param projId
     * @param rs_set
     * @param rs
     * @return
     */
    static protected String getQuery(Connection conn, int projId, int appSysId, Set rs_set, RelationScheme rs){
        String ret = "";
        if(rs==null)
            return "";
            
        Iterator it=rs_set.iterator();
        while(it.hasNext()){
            RelationScheme rel=(RelationScheme)it.next();
            if(rel.id==rs.id)continue;
            
            //check if relation schemes are directly connected in closure graph
             JDBCQuery query = new JDBCQuery(conn);
             ResultSet res = query.select("select GC_ID from IISC_GRAPH_CLOSURE where PR_ID=" + projId
                                         + " and AS_ID=" + appSysId + " and GC_edge_type=0" 
                                         + " and ((RS_superior="+rel.id+" and RS_inferior=" + rs.id + ") " +
                                                  " or (RS_superior=" + rs.id + " and RS_inferior=" + rel.id + "))");


            try {
                 if(res != null && res.next())
                 {
                    //System.out.println(res.getString(1));
                    if(!ret.equalsIgnoreCase("")) ret += " AND ";
                    ret += getConnection(conn, projId, rs, rel);
                 }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            query.Close();
        }
        return ret;
    }
    
    /**
     * Generates WHERE clause that join two tables given as a and b.
     * 
     * @param conn
     * @param projId
     * @param a
     * @param b
     * @return
     */
    static protected String getConnection(Connection conn, int projId, RelationScheme a, RelationScheme b){
        String ret = "";
        Synthesys synt = new Synthesys();
        
        Set neprimarniA = synt.Razlika(a.atributi,a.primarni_kljuc);
        Set neprimarniB = synt.Razlika(b.atributi,b.primarni_kljuc);
        
        //check connection between PK of B and not-PK attributes of A
        Iterator it=b.primarni_kljuc.iterator();
        while(it.hasNext()){
            int att=(new Integer(it.next().toString())).intValue();
            if(neprimarniA.contains(""+att))
            {
                String att_name=getAttributeName(conn, projId, ""+att);
                if(!ret.equalsIgnoreCase(""))
                    ret += " AND ";
                ret += a.name+"."+ att_name +"="+b.name+"."+ att_name;
            }
        }
        
        //check connection between PK of A and not-PK attributes of B
        it=a.primarni_kljuc.iterator();
        while(it.hasNext()){
            int att=(new Integer(it.next().toString())).intValue();
            if(neprimarniB.contains(""+att))
            {
                String att_name=getAttributeName(conn, projId, ""+att);
                if(!ret.equalsIgnoreCase(""))
                    ret += " AND ";
                ret += a.name+"."+ att_name +"="+b.name+"."+ att_name;
            }
        }
        
        //check connection between two PKs
        it=a.primarni_kljuc.iterator();
        while(it.hasNext()){
            int att=(new Integer(it.next().toString())).intValue();
            if(b.primarni_kljuc.contains(""+att))
            {
                String att_name=getAttributeName(conn, projId, ""+att);
                if(!ret.equalsIgnoreCase(""))
                    ret += " AND ";
                ret += a.name+"."+ att_name +"="+b.name+"."+ att_name;
            }
        }
        return ret;
    }
    
    /**
     * Looks up for the attribute's name in the repository.
     * 
     * @param conn
     * @param projId
     * @param attrId
     * @return
     */
    static protected String getAttributeName(Connection conn, int projId, String attrId){
        try{
             JDBCQuery query=new JDBCQuery(conn);
             ResultSet rs;
             String att="";
             rs=query.select("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + attrId + " and PR_id=" + projId);
             if(rs.next()){
               att=rs.getString(1);
             }
             query.Close();  
             return att;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates WHERE clause that joins a table given as insTblId with normJoin
     * tables, but it renames the first table into insTblName. This function is 
     * used to generate joins with "Inserted" table in MS SQL and ":NEW" 
     * pseudo-table in Oracle.
     * 
     * @param conn
     * @param projId
     * @param appSysId
     * @param insTblId
     * @param insTblName
     * @param normJoin
     * @return
     */
    static public String GenerateJoinWithInserted(Connection conn, int projId, 
                                         int appSysId, int insTblId, String insTblName,
                                         ArrayList<Integer> normJoin) {
    
        Synthesys synt = new Synthesys();
        synt.pr = projId;
        synt.as = appSysId;
        synt.con = conn;
        
        String ret = "";
        
        RelationScheme insTbl = synt.getRelationScheme(insTblId);
        insTbl.name = insTblName;
        
        for(int i=0;i<normJoin.size();i++)
        {
            RelationScheme tbl = synt.getRelationScheme(normJoin.get(i));
            JDBCQuery query = new JDBCQuery(conn);
            ResultSet res = query.select("select GC_ID from IISC_GRAPH_CLOSURE where PR_ID=" + projId
                                        + " and AS_ID=" + appSysId + " and GC_edge_type=0" 
                                        + " and ((RS_superior="+tbl.id+" and RS_inferior=" + insTbl.id + ") " +
                                                 " or (RS_superior=" + insTbl.id + " and RS_inferior=" + tbl.id + "))");
                                                 
            try{                                                 
                if(res!=null && res.next()){
                    String tmp = getConnection(conn, projId, insTbl,tbl);
                    if(!tmp.equals(""))
                    {
                        if(!ret.equals(""))
                            ret += " AND ";
                        ret += tmp;
                    }
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        
        return  ret;
    }
}
