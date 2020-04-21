package iisc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import org.antlr.runtime.CommonTokenStream;

 /**
  * 
  * This class contains methods for transformation of check constraints from the 
  * model of form types into the model of platform-independent relation schemes.
  * 
  * @author      Nikola Obrenovic (nikob at sbb.rs)
  * @version     0.1 27/05/10
  */
public class CheckConsSynthesys {
    
    private Synthesys synt;
    
    protected int  projectID;
    protected int  appSysID;
    protected Connection con;
    
    public CheckConsSynthesys(int projID, int appSysID, Connection con) {
        this.projectID = projID;
        this.appSysID = appSysID;
        this.con = con;
        
        synt = new Synthesys();
        synt.as = appSysID;
        synt.pr =  projID;
        synt.con = con;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setAppSysID(int appSysID) {
        this.appSysID = appSysID;
    }

    public int getAppSysID() {
        return appSysID;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }
    
    /**
     * This method is ment to be called for all the transformation to occur. It 
     * collects check constraint defined in form types from the the IISCase 
     * repository, transforms them in the space of the platform-independent 
     * relation schemes and stores the transformed ones in the repository.
     */
    public void transformCheckCons()
    {
        JDBCQuery query = new JDBCQuery(con);
        
        //create domain constraints
        ResultSet rs = query.select("select * from IISC_DOMAIN " +
                                      " where DOM_REG_EXP_STR <> '' and DOM_REG_EXP_STR is not null" +
                                      " and PR_id=" + projectID);

        try {
            while(rs.next())
            {
                transformDomainCheckCon(rs.getInt("Dom_id"),rs.getString("DOM_REG_EXP_STR"));
            }
        } catch (SQLException e) {
            // TODO
        } finally {
            query.Close();
        }
        
        //create attribute constraints
        rs = query.select("select * from IISC_ATTRIBUTE " +
                                      " where ATT_EXPR <> '' and ATT_EXPR is not null" +
                                      " and PR_id=" + projectID);

        try {
            while(rs.next())
            {
                transformAttributeCheckCon(rs.getInt("Att_id"),rs.getString("Att_expr"));
            }
        } catch (SQLException e) {
            // TODO
        } finally {
            query.Close();
        }
        
        //create component type constraints
        rs = query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE, IISC_TF_APPSYS " +
                                      " where IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_ID=IISC_TF_APPSYS.TF_ID " +
                                      " and IISC_COMPONENT_TYPE_OBJECT_TYPE.PR_ID=IISC_TF_APPSYS.PR_ID " +
                                      " and TOB_CHECK <> '' and TOB_CHECK is not null" +
                                      " and IISC_TF_APPSYS.PR_id=" + projectID+" and  IISC_TF_APPSYS.AS_ID="+ appSysID);

        try {
            while(rs.next())
            {
                transformObjectTypeCheckCon(rs.getInt("TF_ID"), rs.getInt("TOB_ID"),rs.getString("TOB_CHECK"));
            }
        } catch (SQLException e) {
            // TODO
        } finally {
            query.Close();
        }
    }
    
    /**
     * Transforms check constraint defined on a domain.
     * 
     * @param domID Domain ID.
     * @param checkCon Check constraint as String.
     */
    public void transformDomainCheckCon(int domID, String checkCon)
    {
        try
        {
            //String output = outputt;
            ByteArrayInputStream byte_in = new ByteArrayInputStream (checkCon.getBytes());
            InputStream is = byte_in;                
             
            ANTLRInputStreamCaseIn input;
             // Create an ExprLexer that feeds from that stream
                input = new ANTLRInputStreamCaseIn(is);
            
            CheckConstraintExpressionLexer lexer = new CheckConstraintExpressionLexer(input);
             // Create a stream of tokens fed by the lexer
             CommonTokenStream tokens = new CommonTokenStream(lexer);
             // Create a parser that feeds off the token stream
             CheckConstraintExpressionParser parser = new CheckConstraintExpressionParser(tokens);
             
             parser.rootNodeName = "";
             parser.con = con;
             parser.typeOfParser = 0; //for domain
             parser.treeID = Integer.toString(projectID);
             parser.ID = Integer.toString(domID);
    
             String xmlCon = parser.project();
             
             LogFuncTree funcTree = LogFuncTreeBuilder.BuildFunctionTree(xmlCon);
             
             NormalForm dnf = NFConverter.convertToDNF(funcTree);
            NormalForm cnf = NFConverter.convertToCNF(funcTree);
             
             saveDomainCheckCon(domID,checkCon,dnf,cnf);
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
    /**
     * Stores the check constraint and its conjunctive and disjuntive normal
     * forms into the repository.
     * 
     * @param domID Domain ID.
     * @param origCon Used-defined check constraint as String.
     * @param dnf Disjunctive normal form.
     * @param cnf Conjunctive normal form.
     */
     public void saveDomainCheckCon(int domID, String origCon, NormalForm dnf, NormalForm cnf)
     {
        try{
            JDBCQuery query=new JDBCQuery(con);
            ResultSet rs;
              
            rs = query.select("select max(CHKC_id) from IISC_CHECK_CONSTRAINT " +
                                        " where PR_id="+ projectID +" and  AS_ID="+ appSysID);
            int nextConID = 1;
            if( rs.next() )
                nextConID = rs.getInt(1)+1;
            rs.close();
              
            //find domain's name
            rs = query.select("select DOM_MNEM from IISC_DOMAIN " +
                                      " where PR_id="+ projectID +" and  DOM_ID="+ domID);
            String conName = "CHKC_";
            if( rs.next() )
                conName += rs.getString(1);
            rs.close();
         
            origCon = origCon.replaceAll("'","");
            query.update("insert into IISC_CHECK_CONSTRAINT (PR_id, AS_id, CHKC_id, CHKC_name, CHKC_desc,Dom_id, Attr_id, RS_Set_id,ORIG_CON) " +
                           "values (" + projectID + ", " + appSysID + "," + nextConID + ", '" + conName + "', 'Domain check constraint'," + domID + ", NULL, NULL, '" + origCon + "')");
              
            saveNormalForm(nextConID,dnf);
            saveNormalForm(nextConID,cnf);
              
          }
          catch(Exception ex){
              ex.printStackTrace();
          }
      
     }

    /**
     * Transforms check constraint defined on an attribute.
     * 
     * @param attrID Attribute ID.
     * @param checkCon Check constraint as String.
     */
     public void transformAttributeCheckCon(int attrID, String checkCon)
     {
     
          try
          {
              //String output = outputt;
              ByteArrayInputStream byte_in = new ByteArrayInputStream (checkCon.getBytes());
              InputStream is = byte_in;                
               
              ANTLRInputStreamCaseIn input;
               // Create an ExprLexer that feeds from that stream
                  input = new ANTLRInputStreamCaseIn(is);
              
              CheckConstraintExpressionLexer lexer = new CheckConstraintExpressionLexer(input);
               // Create a stream of tokens fed by the lexer
               CommonTokenStream tokens = new CommonTokenStream(lexer);
               // Create a parser that feeds off the token stream
               CheckConstraintExpressionParser parser = new CheckConstraintExpressionParser(tokens);
               
               parser.rootNodeName = "";
               parser.con = con;
               parser.typeOfParser = 1; //for attribute
               parser.treeID = Integer.toString(projectID);
               parser.ID = Integer.toString(attrID);
          
               String xmlCon = parser.project();
               
               LogFuncTree funcTree = LogFuncTreeBuilder.BuildFunctionTree(xmlCon);
               
               NormalForm dnf = NFConverter.convertToDNF(funcTree);
               
               NormalForm cnf = NFConverter.convertToCNF(funcTree);
               
               saveAttributeCheckCon(attrID,checkCon,dnf, cnf);
          
          } catch (Exception e) {
              // TODO
          }
         
     }
     
    /**
     * Stores the check constraint and its conjunctive and disjuntive normal
     * forms into the repository.
     * 
     * @param attributeID Attribute ID.
     * @param origCon Used-defined check constraint as String.
     * @param dnf Disjunctive normal form.
     * @param cnf Conjunctive normal form.
     */
    public void saveAttributeCheckCon(int attributeID, String origCon, NormalForm dnf, NormalForm cnf)
    {
        try{
            JDBCQuery query=new JDBCQuery(con);
            ResultSet rs;
            
            rs = query.select("select max(CHKC_id) from IISC_CHECK_CONSTRAINT " +
                                      " where PR_id="+ projectID +" and  AS_ID="+ appSysID);
            int nextConID = 1;
            if( rs.next() )
                nextConID = rs.getInt(1)+1;
            rs.close();
            
            //find attribute's name
            rs = query.select("select ATT_MNEM from IISC_ATTRIBUTE " +
                                       " where PR_id="+ projectID +" and  ATT_ID="+ attributeID);
            String attrName = "";
            if( rs.next() )
                 attrName = rs.getString(1);
            rs.close();
             
            String conName = "CHKC_" + attrName;
            
            origCon = origCon.replaceAll("'","");
            query.update("insert into IISC_CHECK_CONSTRAINT (PR_id, AS_id, CHKC_id, CHKC_name, CHKC_desc,Attr_id, DOM_id, RS_Set_id,ORIG_CON) " +
                         "values (" + projectID + ", " + appSysID + "," + nextConID + ", '" + conName + "', 'Attribute check constraint'," + attributeID + ", NULL, NULL, '"+ origCon + "')");
            
            saveNormalForm(nextConID,dnf);
            
            saveNormalForm(nextConID,cnf);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
     
    }
         
    /**
     * Transforms check constraint defined on a component type (object type).
     * 
     * @param formTypeID Form type ID.
     * @param objTypeID Compoment type ID.
     * @param checkCon User-defined check constraint as String.
     */
    public void transformObjectTypeCheckCon(int formTypeID, int objTypeID, String checkCon)
    {
         try
         {
             //String output = outputt;
             ByteArrayInputStream byte_in = new ByteArrayInputStream (checkCon.getBytes());
             InputStream is = byte_in;                
              
             ANTLRInputStreamCaseIn input;
              // Create an ExprLexer that feeds from that stream
                 input = new ANTLRInputStreamCaseIn(is);
             
             CheckConstraintExpressionLexer lexer = new CheckConstraintExpressionLexer(input);
              // Create a stream of tokens fed by the lexer
              CommonTokenStream tokens = new CommonTokenStream(lexer);
              // Create a parser that feeds off the token stream
              CheckConstraintExpressionParser parser = new CheckConstraintExpressionParser(tokens);
              
              parser.rootNodeName = "";
              parser.con = con;
              parser.typeOfParser = 2; //for component type
              parser.treeID = Integer.toString(projectID);
              parser.ID = Integer.toString(objTypeID);
              parser.ID_2 = Integer.toString(formTypeID);
         
              String xmlCon = parser.project();
              
              LogFuncTree funcTree = LogFuncTreeBuilder.BuildFunctionTree(xmlCon);
              
              NormalForm dnf = NFConverter.convertToDNF(funcTree);
             NormalForm cnf = NFConverter.convertToCNF(funcTree);
              
             dnf.findAttributeIDs(con,projectID);
             cnf.findAttributeIDs(con,projectID);
             
             CheckConSubSchema subschema = new CheckConSubSchema(con, projectID, appSysID, formTypeID, dnf);
             
             Set<RelationScheme> setRS = subschema.findRelSchemes();
             
             if(setRS.size() != 0)
             {
                 findTablesOfAttributes(setRS, dnf);
                 findTablesOfAttributes(setRS, cnf);
                 saveObjectTypeCheckCon(setRS, checkCon, dnf, cnf);
             }
         
         } catch (Exception e) {
             e.printStackTrace();
             return;
         }
        
        
        //persist check constraint
    
    }
    
    /**
     * Stores the check constraint and its conjunctive and disjuntive normal
     * forms into the repository.
     * 
     * @param setRS Set of RelationSchemes referenced by the check constraint.
     * @param origCon Used-defined check constraint as String.
     * @param dnf Disjunctive normal form.
     * @param cnf Conjunctive normal form.
     */
     public void saveObjectTypeCheckCon(Set<RelationScheme> setRS, String origCon, NormalForm dnf, NormalForm cnf)
     {
        try{
            JDBCQuery query=new JDBCQuery(con);
            ResultSet rs;
            
            rs = query.select("select max(CHKC_id) from IISC_CHECK_CONSTRAINT " +
                                      " where PR_id="+ projectID +" and  AS_ID="+ appSysID);
                                      
            int nextConID = 1;
            if( rs.next() )
                nextConID = rs.getInt(1)+1;
                
            rs.close();

            rs = query.select("select max(RS_Set_id) from IISC_RSC_RS_SET " +
                                      " where PR_id="+ projectID +" and  AS_ID="+ appSysID);
                                      
            int nextRSSetID = 1;
            if( rs.next() )
                nextRSSetID = rs.getInt(1)+1;
                
            rs.close();         
            
            Iterator<RelationScheme> iter = setRS.iterator();
            int ord = 1;
            
            String conName = "CHKC_";
            while(iter.hasNext())
            {
                RelationScheme relsch = iter.next();
                query.update("insert into IISC_RSC_RS_SET (PR_id, AS_id, RS_Set_id, RS_id, RS_order) " +
                             "values (" + projectID + ", " + appSysID + "," + nextRSSetID + "," + relsch.id + "," + (ord++) + ")");
                if(relsch.name.length() <= 3)
                    conName += relsch.name;
                else
                    conName += relsch.name.substring(0,3);
            }
            
            rs = query.select("select CHKC_name from IISC_CHECK_CONSTRAINT where CHKC_name like '" 
                                + conName + "\\__' and PR_id="+ projectID +" and  AS_ID="+ appSysID);
            int ccOrdNum = 0;           
            if(rs!=null){
                while( rs.next() )
                {
                    String ccName = rs.getString(1);
                    String[] np = ccName.split("_");
                    if(np.length == 3){ //domain and attribute check constraints does not have 3 parts
                        int num = Integer.parseInt(np[2]);
                        if(num > ccOrdNum) ccOrdNum = num;
                    }
                }
                rs.close();
            }
            ccOrdNum++;
              
            
            conName += "_" + ccOrdNum;
            
            origCon = origCon.replaceAll("'","");
            query.update("insert into IISC_CHECK_CONSTRAINT (PR_id, AS_id, CHKC_id, CHKC_name, CHKC_desc,RS_Set_id, Dom_id, Attr_id,ORIG_CON) " +
                         "values (" + projectID + ", " + appSysID + "," + nextConID + ", '" + conName + "', 'Component type check constraint'," + nextRSSetID + ", NULL, NULL, '" + origCon + "')");
            
            saveNormalForm(nextConID,dnf);
            saveNormalForm(nextConID,cnf);
            
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
     }
    
    /**
     * Stores the normal form into the repository.
     * 
     * @param chkConID Check constraint of the normal form.
     * @param nf Normal form.
     */
    public void saveNormalForm(int chkConID, NormalForm nf)
    {
     try{
         JDBCQuery query=new JDBCQuery(con);
         ResultSet rs;
         
         rs = query.select("select max(NF_ID) from IISC_CHKC_NORMAL_FORM " +
                                   " where PR_id="+ projectID +" and  AS_ID="+ appSysID + " and  CHKC_ID="+ chkConID);
         int nextNFID = 1;
         if( rs.next() )
             nextNFID = rs.getInt(1)+1;
         rs.close();
         
         query.update("insert into IISC_CHKC_NORMAL_FORM (PR_id, AS_id, CHKC_id, NF_ID, OPERATOR) " +
                      "values (" + projectID + ", " + appSysID + "," + chkConID + "," + nextNFID + ", '" + nf.getOperator() + "')");
         
         for(int i=0;i<nf.getParts().size();i++)
         {
             NormalFormPart part = nf.getParts().get(i);
             
             query.update("insert into IISC_CHKC_NF_PART (PR_id, AS_id, CHKC_id, NF_ID, NF_PART_ID, OPERATOR) " +
                          "values (" + projectID + ", " + appSysID + "," + chkConID + "," + nextNFID + "," + (i+1) + ",'" + part.getOperator() + "')");
             
             for(int j=0;j<part.getBasicTerms().size();j++)
             {
                 LogicalTerm term = part.getBasicTerms().get(j);
                 
                 //if operands are string literals, they already have apostrophe
                 String leftOper = term.getLeftOperand();
                 if(!leftOper.startsWith("'"))
                     leftOper = "'"+leftOper+"'";
                     
                 String rightOper = term.getRightOperand();
                 if(!rightOper.startsWith("'"))
                     rightOper = "'"+rightOper+"'";
                                 
                 query.update("insert into IISC_CHKC_BASIC_TERM (PR_id, AS_id, CHKC_id, NF_ID, NF_PART_ID, BT_ID, " +
                              "BT_NEGATED, BT_LEFT_RS_ID, BT_LEFT_OPERAND, BT_OPERATOR, BT_RIGHT_RS_ID, BT_RIGHT_OPERAND) " +
                              "values (" + projectID + ", " + appSysID + "," + chkConID + "," + nextNFID + "," + (i+1) + "," + (j+1) + ","
                              + (term.isNegated()?1:0) + "," + term.getLeftRSId() + "," + leftOper + ",'" + term.getOperator() + "'," + term.getRightRSId() + "," + rightOper + ")");
             }
         }
         
         
     }
     catch(Exception ex){
         ex.printStackTrace();
     }
     
    }
    
    /**
     * For all attributes referenced in the check constraint, this method finds 
     * relation schemes they belog to. Only relation schemes that match the 
     * check constraint should be searched. Found relation schemes are set in 
     * LogicalTerm.leftRSId, LogicalTerm.leftRSName, LogicalTerm.rightRSId and 
     * LogicalTerm.rightRSName attributes if the respective operands are 
     * attributes.
     * 
     * @param setRS Set of RelationSchemes.
     * @param nf Normal form of the check constraint.
     */
    private void findTablesOfAttributes(Set<RelationScheme> setRS, 
                                        NormalForm nf) {
                         
        Iterator<RelationScheme> it = setRS.iterator();
        while(it.hasNext())
        {
            RelationScheme rel = it.next();
            for(int i=0;i<nf.getParts().size();i++)
            {
                for(int j=0;j<nf.getParts().get(i).getBasicTerms().size();j++)
                {
                    LogicalTerm term = nf.getParts().get(i).getBasicTerms().get(j);
                    
                    if(term.getLeftAttrId() != -1 && rel.atributi.contains(""+term.getLeftAttrId()))
                    {
                        term.setLeftRSName(rel.name);
                        term.setLeftRSId(rel.id);
                    }
                    
                    if(term.getRightAttrId() != -1 && rel.atributi.contains(""+term.getRightAttrId()))
                    {
                        term.setRightRSName(rel.name);
                        term.setRightRSId(rel.id);
                    }
                }
            }
        }
                
    }
}
