package iisc.lang;

import iisc.AttEventDef;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PlSqlTranslator 
{
    Connection conn; 
    int PR_id;
    IISCaseLangParser parser = null;
    Hashtable domains = null;
    Hashtable userDefDomains = null;
    Hashtable predefinedFunc = null;
    Hashtable userDefFunc = null;
    Hashtable generatedFunctions = new Hashtable();
    String newLineStr = "";
    Hashtable simple_replace_func = new Hashtable();
    Hashtable simple_rename_func = new Hashtable();
    
    public PlSqlTranslator(Connection conn, int PR_id) 
    {
        this.conn = conn;
        this.PR_id = PR_id;
        this.newLineStr = System.getProperty("line.separator");
        
        parser = new IISCaseLangParser(conn, PR_id, null);
        this.domains = parser.getDomains();
        this.userDefDomains = parser.getUserDefDomains();
        this.predefinedFunc = parser.getPredefinedFunctions();
        this.userDefFunc = parser.getFunctions();
        
        InitSimpleFunc();
    }
    
    private void InitSimpleFunc()
    {
        simple_replace_func.put("currentdate", "sysdate");
        simple_replace_func.put("currenttime", "to_timestamp(sysdate)");
        simple_replace_func.put("showyesnomessage", "0");
        simple_replace_func.put("showinputmessage", "''");
        
        simple_rename_func.put("to_string", "to_char");
    }
    
    /*public static void main(String[] args)
    {
        PlSqlTranslator translator = new PlSqlTranslator(Connect(), 3);
        String result = translator.GenerateTobEvents("DEPARTMENT");
        
        //String result = translator.ConvertToPlSql(1);
         
        try 
        {

            BufferedWriter out = new BufferedWriter(new FileWriter("plsql.txt"));

            String text = result;
            out.write(text);
            out.close();

        }
        catch (IOException e)
        {
            System.out.println("Exception ");
        }
    }*/
    
    public String GenerateTobEvents(String tob_name)
    {
        try
        {
            int tob_id = this.GetTobId(this.conn, tob_name);
            
            if (tob_id == -1)
            {
                return "";
            }
            
            Statement stmt = this.conn.createStatement();
            
            String sql = "select IISC_ATT_TOB_EVENT.Event_id as Event_id,IISC_EVENT.Event_mnem as Event_mnem, IISC_ATT_TOB_EVENT.Fun_id as Fun_id," +
            "IISC_FUNCTION.Fun_name as Fun_mnem,IISC_ATT_TOB_EVENT.Event_level as Event_level,IISC_FUNCTION.Dom_id as Fun_dom_id,IISC_ATT_TOB_EVENT.Att_id as Att_id from IISC_ATT_TOB_EVENT, IISC_EVENT, IISC_FUNCTION where IISC_ATT_TOB_EVENT.Event_id=IISC_EVENT.Event_id and ";
            sql += "IISC_ATT_TOB_EVENT.Fun_id=IISC_FUNCTION.Fun_id and (IISC_ATT_TOB_EVENT.Event_level=2 or IISC_ATT_TOB_EVENT.Event_level=1) and IISC_ATT_TOB_EVENT.Event_type=1";             
            sql += " and IISC_ATT_TOB_EVENT.Tob_id=" + tob_id;
            
            //System.out.println(sql);
            
            ResultSet rs = stmt.executeQuery(sql);
            
            StringBuilder result = new StringBuilder();
            
            while(rs.next())
            {
                StringBuilder builder = new StringBuilder();
                
                String event_id = rs.getString("Event_id");
                String event_name = rs.getString("Event_mnem");
                int func_id = rs.getInt("Fun_id");
                String func_name = rs.getString("Fun_mnem");
                int func_dom_id  = rs.getInt("Fun_dom_id");
                String event_lev = rs.getString("Event_level");
                String att_id = rs.getString("Att_id");
                String e_att_name = "";
                
                if (att_id != null && !att_id.equals("0") && !att_id.equals("-1"))
                {
                    e_att_name = this.GetAttName(conn, att_id);
                }
                
                result.append(this.ConvertToPlSql(func_id));
                
                String when = event_name.toUpperCase(Locale.US);
                
                boolean isRecordLevel = false; 
                boolean isUpdateEvent = false; 
                boolean isDeleteEvent = false; 
                boolean isInsertEvent = false; 
                
                if (when.endsWith("RECORD"))
                {
                    when = when.substring(0, when.length() - 6);
                    isRecordLevel = true;
                }
                
                if (when.indexOf("UPDATE") != -1)
                {
                    isUpdateEvent = true;
                }
                if (when.indexOf("DELETE") != -1)
                {
                    isDeleteEvent = true;
                }
                if (when.indexOf("INSERT") != -1)
                {
                    isInsertEvent = true;
                }
                
                builder.append("CREATE OR REPLACE TRIGGER " + this.GetEventName(event_name, tob_name, e_att_name, event_lev, att_id, "" + tob_id, isUpdateEvent, isDeleteEvent, isInsertEvent, isRecordLevel) + newLineStr);
                
                builder.append(when);
                
                if (event_lev.equals("1"))
                {
                    builder.append(" OF " + e_att_name);
                }
                
                builder.append(" ON " + tob_name);
                
                if (isRecordLevel)
                {
                    builder.append(newLineStr + "FOR EACH ROW");
                }
                
                int i = 0;
                String dom_name = this.GetDomName(this.conn, func_dom_id);
                
                builder.append(newLineStr + "DECLARE");
                
                builder.append(newLineStr + "\tVAR_" + i + " " + this.GetDomPlSqlName(dom_name, true) + ";");
                i++;
                
                Statement stmt1 = conn.createStatement();
                ResultSet rs1 = stmt1.executeQuery("select * from IISC_FUN_PARAM where Fun_id=" + func_id + " order by Param_seq");
                
                ArrayList paramIds = new ArrayList();
                ArrayList paramDomNames = new ArrayList();
                ArrayList paramPlSqlDomNames = new ArrayList();
                
                while(rs1.next())
                {   
                    String paramId = rs1.getString("Param_id");
                    paramIds.add(paramId);
                    int dom_id = rs1.getInt("Dom_id");
                    
                    dom_name = this.GetDomName(this.conn, dom_id);        
                    paramDomNames.add(dom_name);
                    String dom_temp = this.GetDomPlSqlName(dom_name, true);
                    paramPlSqlDomNames.add(dom_temp);
                    builder.append(newLineStr + "\tVAR_" + i + " " + dom_temp + ";");
                    i++;
                }
                
                builder.append(newLineStr + "BEGIN" + newLineStr);
                
                for(int j = 0; j < paramIds.size(); j++)
                {
                    stmt1 = conn.createStatement();
                    rs1 = stmt1.executeQuery("select * from IISC_FUN_PARAM_VALUE where Call_type=" + event_lev + " and Fun_id=" + func_id + " and Param_id=" + paramIds.get(j).toString() + " and Tob_id=" + tob_id + " and Event_id=" + event_id + " and Event_type=1");
                    
                    if (rs1.next())
                    {
                        String value_type = rs1.getString("Value_type");
                        String var_val = null;  
                        
                        if (value_type.equals("2"))
                        {
                            String value_att_id = rs1.getString("Value_att_id");
                            String att_name = this.GetAttName(this.conn, value_att_id);
                            
                            if (isInsertEvent)
                            {
                                var_val = ":new." + att_name;
                            }
                            else
                            {
                                if (isDeleteEvent)
                                {
                                    var_val = ":old." + att_name;
                                }
                                else
                                {
                                    if (isUpdateEvent)
                                    {
                                        if (when.startsWith("AFTER"))
                                        {
                                            var_val = ":old." + att_name;
                                        }
                                        else
                                        {
                                            var_val = ":new." + att_name;
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            if (value_type.equals("1"))
                            {
                                var_val = rs1.getString("Value_const");
                                Element valueNode = this.parser.ParseVariableInit(var_val, (String)paramDomNames.get(j));
                                var_val = this.GetVarDefValue(valueNode.getFirstChild(), (String)paramDomNames.get(j), (String)paramPlSqlDomNames.get(j), new Hashtable());
                            }
                        }
                        if (var_val != null)
                        {
                            builder.append("\tVAR_" + (j+1) + " := " + var_val + ";" + newLineStr);
                        }
                    }
                }
                
                builder.append("\tVAR_0 := " + func_name + "_pac_" + this.PR_id + "." + func_name + "(");
                
                for(int j = 0; j < paramIds.size(); j++)
                {
                    if (j>0)
                    {
                        builder.append(",");
                    }
                    
                    builder.append("VAR_" + (j+1));
                }
                
                builder.append(");" + newLineStr);
                
                result.append(builder.toString());
                result.append("END;" + newLineStr + "/" + newLineStr);
            }
            
            return result.toString();
        }
        catch(Exception e)
        {
            return "";
        }
    }
    
    private String GetEventName(String event_name, String tob_name, String att_name, String event_lev, String att_id, String tob_id, boolean isUpdateEvent, boolean isDeleteEvent, boolean isInsertEvent, boolean isRecordLevel)
    {
        String name = "";
        
        if (event_name.startsWith("Before"))
        {
            name += "B";
        }
        else
        {
            name += "A";
        }
        
        if (isInsertEvent)
        {
            name += "I";
        }
        else
        {
            if (isUpdateEvent)
            {
                name += "U";
            }
            else
            {
                name += "D";
            }
        }
        
        if (isRecordLevel)
        {
            name += "R";
        }
        
        if (event_lev.equals("1"))
        {
            if (att_name.length() > 4)
            {
                name += "A_" + att_name.substring(0,3)  + att_id;
            }
            else
            {
                name += "A_" + att_name  + att_id;
            }
        }
        else
        {
            if (tob_name.length() > 4)
            {
                name += "C_" + tob_name.substring(0,3)  + tob_id;
            }
            else
            {
                name += "C_" + tob_name  + tob_id;
            }
        }
        name += "_" + PR_id;
        return name;
    }
    
    public int GetTobId(Connection con, String tob_name)
    {
        try 
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tob_mnem='" + tob_name + "'");
            
            if (rs.next())
            {
                return rs.getInt("Tob_id");
            }
        }
        catch(Exception ex)
        {}
        
        return -1;
    }
    
    public String GetDomName(Connection con, int dom_id)
    {
        try 
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from IISC_DOMAIN where Dom_id=" + dom_id + " and PR_id=" + this.PR_id);
            
            if (rs.next())
            {
                return rs.getString("Dom_mnem");
            }
        }
        catch(Exception ex)
        {}
        
        return "-1";
    }
    
    public String GetAttName(Connection con, String att_id)
    {
        try 
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from IISC_ATTRIBUTE where Att_id=" + att_id + " and PR_id=" + this.PR_id);
            
            if (rs.next())
            {
                return rs.getString("Att_mnem");
            }
        }
        catch(Exception ex)
        {}
        
        return "-1";
    }
    
    public String ConvertToPlSql(int funcId)
    {
        String fId = Integer.toString(funcId);
        String funcName = JSourceCodeEditor.LoadFuncNameFromRepository(this.PR_id, fId, conn);
        
        if (this.generatedFunctions.containsKey(funcName.toLowerCase(Locale.US)))
        {
            return "";
        }
        
        this.generatedFunctions.put(funcName.toLowerCase(Locale.US), "");
        
        String functext = JSourceCodeEditor.LoadFuncSourceFromRepository(this.PR_id, fId, conn);
        
        IISCaseLangParser parser = new IISCaseLangParser(conn, PR_id, null);
        parser.ParseProject(functext, "");
        
        StringBuilder packageSpec = new StringBuilder();
        StringBuilder builder = new StringBuilder();            
        
        this.ConvertToPlSql(parser.getAstXml(), packageSpec, builder);
        
        return packageSpec.toString() + builder.toString();
    }
    
    public void ConvertToPlSql(Document doc, StringBuilder packageSpec, StringBuilder builder)
    {
        try 
        {
            ArrayList funcToBeCreated = new ArrayList();
            
            Element root = (Element)doc.getDocumentElement().getFirstChild();
            String funcName = root.getNodeName();
            String pacName = funcName + "_pac_" + this.PR_id;
            
            packageSpec.append("CREATE OR REPLACE PACKAGE " +  pacName + " IS" + newLineStr);
            packageSpec.append("\ttype T_REF_CUR is ref cursor;" + newLineStr);
            
            StringBuilder header = new StringBuilder();
            
            header.append("FUNCTION ");
            header.append(funcName);
            
            Node argsNode = root.getChildNodes().item(0);
            NodeList list = argsNode.getChildNodes();
            
            if (list.getLength() > 0)
            {
                header.append("(");
                
                for(int i = 0; i < list.getLength();i++)
                {
                    if (i > 0)
                    {
                        header.append(", ");
                    }
                    
                    Element argNode = (Element)list.item(i);
                    this.CreatePlSqlArgs(header, argNode, true);
                }
                
                header.append(") RETURN");
            }
            else
            {
                header.append(" RETURN");
            }
            
            Node returnsNode = root.getChildNodes().item(1);
            String retType = returnsNode.getFirstChild().getFirstChild().getNodeName();
            header.append(" " + this.GetDomPlSqlName(retType, false));
            
            builder.append("CREATE OR REPLACE PACKAGE BODY " +  pacName + " IS" + newLineStr);
            builder.append(header.toString());            
            packageSpec.append("\t" + header.toString() + ";" + newLineStr);
            builder.append(newLineStr + "IS" + newLineStr);
            
            Node varsNode = root.getChildNodes().item(2);
            list = varsNode.getChildNodes();
            
            Hashtable types = new Hashtable();
            
            for(int i = 0; i < list.getLength();i++)
            {
                Element argNode = (Element)list.item(i);
                this.CreatePlSqlVars(builder, argNode, false, types);
            }
            
            builder.append(newLineStr + "BEGIN" + newLineStr);
            
            Node bodyNode = root.getChildNodes().item(3);
            this.ConvertStatements((Element)bodyNode.getFirstChild(), builder, "\t", funcToBeCreated);
            builder.append("END " + funcName + ";" + newLineStr);
            
            for(int i = 0; i < funcToBeCreated.size(); i++)
            {
                if (this.predefinedFunc.containsKey(((String)funcToBeCreated.get(i)).toLowerCase(Locale.US)))
                {
                    this.CreatePlsSqlFunc(packageSpec, (String)funcToBeCreated.get(i), true);
                    this.CreatePlsSqlFunc(builder, (String)funcToBeCreated.get(i), false);
                }
            }
            
            packageSpec.append("END " + pacName + ";" + newLineStr + "/" + newLineStr);
            builder.append("END " + pacName + ";" + newLineStr + "/" + newLineStr);
            
            for(int i = 0; i < funcToBeCreated.size(); i++)
            {
                String fname = ((String)funcToBeCreated.get(i)).toLowerCase(Locale.US);
                
                if (this.userDefFunc.containsKey(fname))
                {
                    this.ConvertToPlSql(fname, packageSpec, builder);
                }
            } 
        }
        catch(Exception e)
        {
        }
    }
    
    private void ConvertToPlSql(String fName, StringBuilder packageSpec, StringBuilder builder)
    {
        //String fId = Integer.toString(funcId);
        String funcName = fName.toLowerCase(Locale.US);
        String fId = JSourceCodeEditor.LoadFuncIdFromRepository(this.PR_id, funcName, conn);
        
        if (this.generatedFunctions.containsKey(funcName.toLowerCase(Locale.US)))
        {
            return;
        }
        
        this.generatedFunctions.put(funcName.toLowerCase(Locale.US), "");
        
        String functext = JSourceCodeEditor.LoadFuncSourceFromRepository(this.PR_id, fId, conn);
        
        IISCaseLangParser parser = new IISCaseLangParser(conn, PR_id, null);
        parser.ParseProject(functext, "");
        
        this.ConvertToPlSql(parser.getAstXml(), packageSpec, builder);
    }
    
    private void CreatePlsSqlFunc(StringBuilder builder, String funcName, boolean justHeader)
    {
    
        if (funcName.equals("hasnext"))
        {
            if (justHeader)
            {
                builder.append("\t");
            }
            
            builder.append("FUNCTION hasnext(curr in T_REF_CUR) RETURN BOOLEAN");
            
            if (justHeader)
            {
                builder.append(";" + newLineStr);
            }
            else
            {
                builder.append(newLineStr + 
                "IS " + newLineStr + 
                "BEGIN " + newLineStr + 
                "       return not curr%notfound;" + newLineStr +
                "END hasnext;" + newLineStr);
            }
        }
        
        if (funcName.equals("execute_query"))
        {
            if (justHeader)
            {
                builder.append("\t");
            }
            
            builder.append("FUNCTION execute_query(stmt in VARCHAR) RETURN T_REF_CUR");
            
            if (justHeader)
            {
                builder.append(";" + newLineStr);
            }
            else
            {
                builder.append(newLineStr + 
                "IS " + newLineStr + 
                "       curr T_REF_CUR;" + newLineStr + 
                "BEGIN" + newLineStr + 
                "       open curr for stmt;" + newLineStr + 
                "       return curr;" + newLineStr + 
                "END execute_query;" + newLineStr);
            }
        }
        
        if (funcName.equals("execute_nonquery"))
        {
            if (justHeader)
            {
                builder.append("\t");
            }
            
            builder.append("FUNCTION execute_nonquery(stmt in VARCHAR) RETURN INTEGER");
            
            if (justHeader)
            {
                builder.append(";" + newLineStr);
            }
            else
            {
               builder.append(newLineStr + 
               "IS " + newLineStr + 
               "BEGIN" + newLineStr + 
               "        execute immediate stmt;" + newLineStr + 
               "        return 0;" + newLineStr + 
               "END execute_nonquery;" + newLineStr);
            }
        }
        
        if (funcName.equals("showinfomessage"))
        {
            if (justHeader)
            {
                builder.append("\t");
            }
            
            builder.append("FUNCTION ShowInfoMessage(msg in varchar) RETURN INTEGER");
            
            if (justHeader)
            {
                builder.append(";" + newLineStr);
            }
            else
            {
               builder.append(newLineStr + 
               "IS " + newLineStr + 
               "BEGIN " + newLineStr + 
               "	DBMS_OUTPUT.PUT_LINE('Info ' || msg);" + newLineStr +
               "	return 0;" + newLineStr + 
               "END ShowInfoMessage;" + newLineStr);
            }
        }
        
        if (funcName.equals("showwarningmessage"))
        {
            if (justHeader)
            {
                builder.append("\t");
            }
            
            builder.append("FUNCTION ShowWarningMessage(msg in varchar) RETURN INTEGER");
            
            if (justHeader)
            {
                builder.append(";" + newLineStr);
            }
            else
            {
               builder.append(newLineStr + 
               "IS " + newLineStr + 
               "BEGIN " + newLineStr + 
               "	DBMS_OUTPUT.PUT_LINE('Warning ' || msg);" + newLineStr + 
               "	return 0;" + newLineStr + 
               "END ShowWarningMessage;" + newLineStr);
            }
        }
        
        if (funcName.equals("showerrormessage"))
        {
            if (justHeader)
            {
                builder.append("\t");
            }
            
            builder.append("FUNCTION ShowErrorMessage(msg in varchar) RETURN INTEGER");
            
            if (justHeader)
            {
                builder.append(";" + newLineStr);
            }
            else
            {
               builder.append(newLineStr + 
               "IS " + newLineStr + 
               "BEGIN " + newLineStr + 
               "        DBMS_OUTPUT.PUT_LINE('Error ' || msg);" + newLineStr + 
               "        return 0;" + newLineStr + 
               "END ShowErrorMessage;" + newLineStr);
            }
        }
    }
    
    private void InitPlSqlFunc(Hashtable functions)
    {
        functions.put("hasnext", "hasnext");
        functions.put("execute_query", "execute_query");
        functions.put("execute_nonquery", "execute_nonquery");
    }
    
    private void AddToList(ArrayList list, String newName)
    {
        boolean exists = false;
        
        for(int i = 0; i < list.size(); i++)
        {
            if (newName.equals((String)list.get(i)))
            {
                exists = true;
                break;
            }
        }
        
        if (!exists)
        {
            list.add(newName);
        }
    }
    
    public void ConvertStatements(Node statNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        for(int i = 0; i < statNode.getChildNodes().getLength(); i++)
        {
            Element statementNode = (Element)statNode.getChildNodes().item(i);
            
            if (statementNode.getNodeName().equals("assign"))
            {
                ConvertAssStatements(statementNode, builder, tabTxt, funcToBeCreated);
            }
            else
            {
                if (statementNode.getNodeName().equals("while"))
                {
                    ConvertWhileStatements(statementNode, builder, tabTxt, funcToBeCreated);
                }
                else
                {
                    if (statementNode.getNodeName().equals("debug"))
                    {
                        ConvertDebugStatements(statementNode, builder, tabTxt, funcToBeCreated);
                    }
                    else
                    {
                        if (statementNode.getNodeName().equals("fetch"))
                        {
                            ConvertFetchStatements(statementNode, builder, tabTxt, funcToBeCreated);
                        }
                        else
                        {
                            if (statementNode.getNodeName().equals("return"))
                            {
                                ConvertReturnStatements(statementNode, builder, tabTxt, funcToBeCreated);
                            }
                            else
                            {
                                if (statementNode.getNodeName().equals("select"))
                                {
                                    ConvertSelStatements(statementNode, builder, tabTxt, funcToBeCreated);
                                }
                                else
                                {
                                    if (statementNode.getNodeName().equals("if"))
                                    {
                                        ConvertIfStatements(statementNode, builder, tabTxt, funcToBeCreated);
                                    }
                                    else
                                    {
                                        if (statementNode.getNodeName().equals("signal"))
                                        {
                                            ConvertSignalStatements(statementNode, builder, tabTxt, funcToBeCreated);
                                        }
                                        else
                                        {
                                            if (statementNode.getNodeName().equals("for"))
                                            {
                                                ConvertForStatements(statementNode, builder, tabTxt, funcToBeCreated);
                                            }
                                            else
                                            {
                                                if (statementNode.getNodeName().equals("repeat"))
                                                {
                                                    ConvertRepeatStatements(statementNode, builder, tabTxt, funcToBeCreated);
                                                }
                                                else
                                                {
                                                    if (statementNode.getNodeName().equals("break"))
                                                    {
                                                        ConvertBreakStatements(statementNode, builder, tabTxt, funcToBeCreated);
                                                    }
                                                    else
                                                    {
                                                        if (statementNode.getNodeName().equals("switch"))
                                                        {
                                                            ConvertSwitchStatements(statementNode, builder, tabTxt, funcToBeCreated);
                                                        }
                                                        else
                                                        {
                                                            if (statementNode.getNodeName().equals("update"))
                                                            {
                                                                ConvertUpdateStatements(statementNode, builder, tabTxt, funcToBeCreated);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void ConvertReturnStatements(Node retNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        try 
        {
            Element varNode = (Element)retNode.getChildNodes().item(0);
            Element expNode = (Element)varNode.getFirstChild();
            
            builder.append(tabTxt + "return ");
            builder.append(ConvertExpression(expNode, funcToBeCreated));
            builder.append(";" + newLineStr);
        }
        catch(Exception e)
        {}
    }
    
    public void ConvertFetchStatements(Node fNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        try 
        {
            
            Element fetchNode = (Element)fNode;            
            builder.append(tabTxt + "FETCH " + fetchNode.getAttribute("Name") + " INTO ");
            for(int i = 0 ; i < fetchNode.getChildNodes().getLength(); i++)
            {
                if (i >0)
                {
                    builder.append(",");
                }
                builder.append(ConverToPlSqlVar((Element)fetchNode.getChildNodes().item(i),funcToBeCreated));
            }
            builder.append(";" + newLineStr);
        }
        catch(Exception e)
        {}
    }
    
    public void ConvertWhileStatements(Node whileNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        try 
        {
            Element condNode = (Element)whileNode.getChildNodes().item(0);
            Element statNode = (Element)whileNode.getChildNodes().item(1);
            
            builder.append(newLineStr + tabTxt + "WHILE ");
            builder.append(ConvertExpression((Element)condNode.getFirstChild(), funcToBeCreated));
            builder.append(newLineStr + tabTxt + "LOOP" + newLineStr);
            this.ConvertStatements(statNode, builder, tabTxt + "\t", funcToBeCreated);
            builder.append(tabTxt + "END LOOP;" + newLineStr+ newLineStr);
        }
        catch(Exception e)
        {}
    }
    
    public void ConvertRepeatStatements(Node whileNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        try 
        {
            Element condNode = (Element)whileNode.getChildNodes().item(1);
            Element statNode = (Element)whileNode.getChildNodes().item(0);
            
            builder.append(newLineStr + tabTxt + "LOOP " + newLineStr);
            this.ConvertStatements(statNode, builder, tabTxt + "\t", funcToBeCreated);
            builder.append(tabTxt + "EXIT WHEN ");
            builder.append(ConvertExpression((Element)condNode.getFirstChild(), funcToBeCreated));
            builder.append(";" + newLineStr);
            builder.append(tabTxt + "END LOOP;" + newLineStr + newLineStr);
        }
        catch(Exception e)
        {}
    }
    
    public void ConvertForStatements(Node forNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        try 
        {
            Element varNode = (Element)forNode.getChildNodes().item(0);
            Element fromNode = (Element)forNode.getChildNodes().item(1).getFirstChild();
            Element toNode = (Element)forNode.getChildNodes().item(2).getFirstChild();
            Element statNode = (Element)forNode.getChildNodes().item(3);
            
            builder.append(newLineStr + tabTxt + "FOR ");
            builder.append(ConverToPlSqlVar(varNode,funcToBeCreated));
            builder.append(" IN ");
            builder.append(ConvertExpression(fromNode, funcToBeCreated));
            builder.append(" .. ");
            builder.append(ConvertExpression(toNode, funcToBeCreated));
            builder.append(newLineStr + tabTxt + "LOOP" + newLineStr);
            this.ConvertStatements(statNode, builder, tabTxt + "\t", funcToBeCreated);
            builder.append(tabTxt + "END LOOP;" + newLineStr + newLineStr);
        }
        catch(Exception e)
        {}
    }
    
    public void ConvertIfStatements(Node ifNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        try 
        {
            Element condNode = (Element)ifNode.getChildNodes().item(0);
            Element statNode = (Element)ifNode.getChildNodes().item(1);
            
            builder.append(newLineStr + tabTxt + "IF ");
            builder.append(ConvertExpression((Element)condNode.getFirstChild(), funcToBeCreated));
            builder.append(newLineStr + tabTxt + "THEN" + newLineStr);
            this.ConvertStatements(statNode, builder, tabTxt + "\t", funcToBeCreated);
            
            if (ifNode.getChildNodes().getLength() > 2)
            {
                Element elseNode = (Element)ifNode.getChildNodes().item(2);
                builder.append(tabTxt + "ELSE" + newLineStr);                
                this.ConvertStatements(elseNode.getFirstChild(), builder, tabTxt + "\t", funcToBeCreated);
            }
            builder.append(tabTxt + "END IF;" + newLineStr + newLineStr);
        }
        catch(Exception e)
        {}
    }
    
    public void ConvertSwitchStatements(Node switchNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        try 
        {
            Element valNode = (Element)switchNode.getChildNodes().item(0);
            String varstr = ConvertExpression((Element)valNode.getFirstChild(), funcToBeCreated);
            
            for(int i = 1; i < switchNode.getChildNodes().getLength(); i++)
            {
                Element caseNode = (Element)switchNode.getChildNodes().item(i);
                boolean isDefault = false;
                
                if (caseNode.getNodeName().equals("default"))
                {
                    isDefault = true;
                }
                
                if (isDefault)
                {
                    builder.append(tabTxt + "ELSE" + newLineStr);
                    Element statNode = (Element)caseNode.getChildNodes().item(0);
                    this.ConvertStatements(statNode, builder, tabTxt + "\t", funcToBeCreated);
                }
                else
                {
                    if (i == 1)
                    {
                        builder.append(newLineStr + tabTxt + "IF ");
                    }
                    else
                    {
                        builder.append(tabTxt + "ELSIF ");
                    }
                    valNode = (Element)caseNode.getChildNodes().item(0).getFirstChild();
                    
                    builder.append("(" + varstr + ") = (" + ConvertExpression((Element)valNode, funcToBeCreated) + ")");
                    
                    builder.append(" THEN" + newLineStr);
                    Element statNode = (Element)caseNode.getChildNodes().item(1);
                    this.ConvertStatements(statNode, builder, tabTxt + "\t", funcToBeCreated);    
                }
            }
            builder.append(tabTxt + "END IF;" + newLineStr + newLineStr);
        }
        catch(Exception e)
        {}
    }
    
    public void ConvertDebugStatements(Node printNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        try 
        {
            Element valNode = (Element)printNode.getChildNodes().item(0);
            
            builder.append(tabTxt + "DBMS_OUTPUT.PUT_LINE(");
            builder.append(ConvertExpression((Element)valNode.getFirstChild(), funcToBeCreated));
            builder.append(");" + newLineStr);
        }
        catch(Exception e)
        {}
    }
    
    public void ConvertBreakStatements(Node breakNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        try 
        {
            builder.append(tabTxt + "EXIT;" + newLineStr);
        }
        catch(Exception e)
        {}
    }
    
    public void ConvertUpdateStatements(Node breakNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        try 
        {
            builder.append(tabTxt + "NULL;" + newLineStr);
        }
        catch(Exception e)
        {}
    }
    
    public void ConvertAssStatements(Node assNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        try 
        {
            Element varNode = (Element)assNode.getChildNodes().item(0);
            Element valNode = (Element)assNode.getChildNodes().item(1);
            Element expNode = (Element)valNode.getFirstChild();
            
            builder.append(tabTxt);
            builder.append(ConverToPlSqlVar(varNode,funcToBeCreated));
            builder.append(" := ");
            builder.append(ConvertExpression(expNode, funcToBeCreated));
            builder.append(";" + newLineStr);
        }
        catch(Exception e)
        {}
    }
    
    public void ConvertSignalStatements(Node rNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        try 
        {
            Element raiseNode = (Element)rNode;
            //Element varNode = (Element)assNode.getChildNodes().item(1);
            String funcName = ((Element)raiseNode).getAttribute("Name");
            
            if (funcName.equalsIgnoreCase("abort_trigger"))
            {
                builder.append(tabTxt);
                builder.append("RAISE_APPLICATION_ERROR(");
                
                if (raiseNode.getChildNodes().getLength() > 0)
                {
                    builder.append(ConvertExpression((Element)raiseNode.getChildNodes().item(0).getFirstChild(), funcToBeCreated));
                }
                else
                {
                    builder.append("-20500");
                }
                
                builder.append(",");
                
                if (raiseNode.getChildNodes().getLength() > 1)
                {
                    builder.append(ConvertExpression((Element)raiseNode.getChildNodes().item(1).getFirstChild(), funcToBeCreated));
                }
                else
                {
                    builder.append("''");
                }
                builder.append(");" + newLineStr);
            }
        }
        catch(Exception e)
        {}
    }
    
    public void ConvertSelStatements(Node assNode, StringBuilder builder, String tabTxt, ArrayList funcToBeCreated)
    {
        try 
        {
            Element fromNode = (Element)assNode.getChildNodes().item(2);
            Element varNode = (Element)assNode.getChildNodes().item(1);
            String funcName = ((Element)assNode).getAttribute("FuncName");
            
            builder.append(tabTxt);
            builder.append("select " + funcName + "(COLUMN_VALUE) INTO ");
            builder.append(ConverToPlSqlVar(varNode,funcToBeCreated));
            builder.append(" FROM TABLE(");
            builder.append(ConvertExpression((Element)fromNode.getFirstChild(), funcToBeCreated));
            builder.append(");" + newLineStr);
        }
        catch(Exception e)
        {}
    }
    
    public String ConvertExpression(Element expNode, ArrayList funcToBeCreated)
    {
        try 
        {
            String res = "";
            
            if (expNode.getFirstChild().getNodeName().equals("const"))
            {
                Element constNode = (Element)expNode.getFirstChild();
                res = ConvertConst(constNode);                
            }
            else
            {
                if (expNode.getFirstChild().getNodeName().equals("var"))
                {
                    Element varNode = (Element)expNode.getFirstChild();
                    
                    res = ConverToPlSqlVar(varNode, funcToBeCreated);
                }
                else
                {
                    String opTxt = expNode.getAttribute("Op");
                    boolean isUnionOp = false;
                    
                    if (expNode.getAttribute("Op") != null && !opTxt.equals(""))
                    {
                        if(opTxt.equals("=="))
                        {
                            opTxt = "=";
                        }
                        else
                        {
                            if (opTxt.toLowerCase(Locale.US).equals("union"))
                            {
                                opTxt = "MULTISET UNION";
                                isUnionOp = true;
                            }
                            else
                            {
                                if (opTxt.toLowerCase(Locale.US).equals("intersect"))
                                {
                                    opTxt = "MULTISET INTERSECT";
                                }
                                else
                                {
                                    if(opTxt.equals("%"))
                                    {
                                        opTxt = "mod";
                                    }
                                    else
                                    {
                                        if(opTxt.equals("unminus"))
                                        {
                                            opTxt = "-";
                                        }
                                    }
                                }
                            }
                        }
                        
                        if (opTxt.toLowerCase(Locale.US).equals("in"))
                        {
                            String isOverSetAtt = expNode.getAttribute("IsOverSet");
                            
                            if (isOverSetAtt != null && !isOverSetAtt.equals(""))
                            {
                                res = ConvertExpression((Element)expNode.getChildNodes().item(0), funcToBeCreated) + " member of (" + ConvertExpression((Element)expNode.getChildNodes().item(1), funcToBeCreated) + ")";
                            }
                            else
                            {
                                res = ConvertExpression((Element)expNode.getChildNodes().item(0), funcToBeCreated) + " " + opTxt + " (";
                                
                                for(int j = 1; j < expNode.getChildNodes().getLength(); j++)
                                {
                                    if (j > 1)
                                    {
                                        res += ", ";
                                    }
                                    
                                    res += ConvertExpression((Element)expNode.getChildNodes().item(j), funcToBeCreated);
                                }
                                
                                res += ")";
                            }
                        }
                        else
                        {
                            if (opTxt.toLowerCase(Locale.US).equals("xor"))
                            {
                                String expText1 = ConvertExpression((Element)expNode.getChildNodes().item(0), funcToBeCreated);
                                String expText2 = ConvertExpression((Element)expNode.getChildNodes().item(1), funcToBeCreated);
                                res = "(" + "(" + expText1 + ") != (" + expText2 + ")" + ")";
                            }
                            else
                            {
                                if (opTxt.toLowerCase(Locale.US).equals("=>"))
                                {
                                    String expText1 = ConvertExpression((Element)expNode.getChildNodes().item(0), funcToBeCreated);
                                    String expText2 = ConvertExpression((Element)expNode.getChildNodes().item(1), funcToBeCreated);
                                    res = "(" + "not (" + expText1 + ") or (" + expText2 + ")" + ")";
                                }
                                else
                                {
                                    if (opTxt.toLowerCase(Locale.US).equals("like"))
                                    {
                                        String expText1 = ConvertExpression((Element)expNode.getChildNodes().item(0), funcToBeCreated);
                                        res = expText1 + " like " + expNode.getAttribute("Pattern");
                                    }
                                    else
                                    {
                                        if (expNode.getChildNodes().getLength() == 1)
                                        {
                                            res = opTxt + " " +ConvertExpression((Element)expNode.getChildNodes().item(0), funcToBeCreated);
                                        }
                                        else
                                        {
                                            if (expNode.getChildNodes().getLength() == 2)
                                            {
                                                if (isUnionOp)
                                                {
                                                    String typeName1 = ((Element)expNode.getChildNodes().item(0)).getAttribute("TypeName");
                                                    String typeName2 = ((Element)expNode.getChildNodes().item(1)).getAttribute("TypeName");
                                                    DomainDesc d1 = this.GetBaseDomain(typeName1);
                                                    DomainDesc d2 = this.GetBaseDomain(typeName2);
                                                    String expText1 = ConvertExpression((Element)expNode.getChildNodes().item(0), funcToBeCreated);
                                                    String expText2 = ConvertExpression((Element)expNode.getChildNodes().item(1), funcToBeCreated);
                                                    
                                                    res = expText1 + " " + opTxt + " " + expText2;
                                                    
                                                    if (d2 == null || d2.getType() != DomainDesc.SET)
                                                    {
                                                        if (d1 != null && d1.getType() == DomainDesc.SET)
                                                        {
                                                            res = expText1 + " " + opTxt + " " + this.GetDomPlSqlName(d1.getName(),false) + "(" + expText2 + ")";
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (d1 == null || d1.getType() != DomainDesc.SET)
                                                        {
                                                            if (d2 != null && d2.getType() == DomainDesc.SET)
                                                            {
                                                                res = this.GetDomPlSqlName(d2.getName(),false) + "(" + expText1 + ")" + " " + opTxt + " " + expText2;
                                                            }
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    res = ConvertExpression((Element)expNode.getChildNodes().item(0), funcToBeCreated) + " " + opTxt + " " + ConvertExpression((Element)expNode.getChildNodes().item(1), funcToBeCreated);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        if (expNode.getFirstChild().getNodeName().equals("func_call"))
                        {
                            Element funcNode = (Element)expNode.getFirstChild();                            
                            res = funcNode.getAttribute("Name");
                            String fName = res.toLowerCase(Locale.US);
                            
                            if (fName.equals("get_size"))
                            {
                                Element paramExpNode = (Element)funcNode.getFirstChild().getFirstChild();                                
                                res = ConvertExpression(paramExpNode, funcToBeCreated) + ".count";
                            }
                            else
                            {
                                if (this.simple_replace_func.containsKey(fName))
                                {
                                    res = (String)this.simple_replace_func.get(fName);
                                }
                                else
                                {
                                    if (this.simple_rename_func.containsKey(fName))
                                    {
                                        res = (String)this.simple_rename_func.get(fName);
                                    }
                                    else
                                    {
                                        this.AddToList(funcToBeCreated, res);
                                    }
                                    
                                    if (this.userDefFunc.containsKey(res))
                                    {
                                        res = res + "_pac_" + this.PR_id + "." + res;
                                    }
                                    
                                    res += "(";
                                    
                                    for(int i = 0; i < funcNode.getChildNodes().getLength(); i++)
                                    {
                                        if (i > 0)
                                        {
                                            res += ", ";
                                        }
                                        Element paramExpNode = (Element)funcNode.getChildNodes().item(i).getFirstChild();
                                        
                                        res += ConvertExpression(paramExpNode, funcToBeCreated);
                                    }
                                    
                                    res += ")";
                                }
                            }
                        }
                    }
                }
            }
            
            if(expNode.getAttribute("HasPar") != null & !expNode.getAttribute("HasPar").equals(""))
            {
                res = "(" + res + ")";
            }
            return res;
        }
        catch(Exception e)
        {
            return "";
        }
    }
    
    public String ConvertConst(Element constNode)
    {
        String res = "";
        
        try 
        {
            if (constNode.getAttribute("val") != null)
            {
                String val = constNode.getAttribute("val");
                String type = "INT";
                
                if (constNode.getAttribute("type") != null)
                {
                    type = constNode.getAttribute("type");
                }
                
                if (type.equals("STRING"))
                {
                    res = ConvertToPlSqlString(val);
                }
                else
                {
                    if (type.equals("TIME"))
                    {
                        res = "TO_TIMESTAMP('" + val + "','HH24:MI:SS')";
                    }
                    else
                    {
                        if (type.equals("DATE"))
                        {
                            res = "TO_DATE('" + val + "','DD-mm-YYYY')";
                        }
                        else
                        {
                            res = val;
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {}
        
        return res;
    }
    
    public String ConvertConst(String type)
    {
        String res = "";
        
        try 
        {
            if (type.equals("STRING"))
            {
                res = "''";
            }
            else
            {
                if (type.equals("TIME"))
                {
                    res = "TO_TIMESTAMP('00:00:00','HH24:MI:SS')";
                }
                else
                {
                    if (type.equals("DATE"))
                    {
                        res = "TO_DATE('01-01-2000','DD-mm-YYYY')";
                    }
                    else
                    {
                        if (type.startsWith("BOOL"))
                        {
                            res = "TRUE";
                        }
                        else
                        {
                            if (type.startsWith("INT"))
                            {
                                res = "0";
                            }
                            else
                            {
                                if (type.equals("REAL"))
                                {
                                    res = "0.0";
                                }
                                else
                                {
                                    res = "";
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {}
        
        return res;
    }
    
    public String ConverToPlSqlVar(Element varNode, ArrayList functobecreated)
    {
        try 
        {
            if (varNode.getAttribute("Name") != null)
            {
                String res = varNode.getAttribute("Name");
                
                if (varNode.getChildNodes().getLength() == 0)
                {
                    return res;
                }
                
                for(int i = 0; i < varNode.getChildNodes().getLength(); i++)
                {
                    Element elemNode = (Element)varNode.getChildNodes().item(i);
                    
                    if (elemNode.getNodeName().equals("subscript"))
                    {
                        res += "(" + this.ConvertExpression((Element)elemNode.getFirstChild(), functobecreated) + " + 1)";
                    }
                    else
                    {
                        res += "." + elemNode.getAttribute("FieldName");
                    }
                }
                return res;
            }
        }
        catch(Exception e)
        {}
        return "";
    }
    
    public static String ConvertToPlSqlString(String value)
    {
        try 
        {
            int len = value.length();
            int c = 0;
            
            for(int k = 0; k < len; k++)
            {
                if (value.charAt(k) == '"')
                {
                    c = c +1;
                }
            }
            
            char cVal[] = new char[len+c];
            int i = 0;
            int j = 0;
            
            while(i<len) 
            {
                if (value.charAt(i) == '\\')    
                {
                    i = i + 1;
                    
                    if (value.charAt(i) == '\'') 
                    {
                        cVal[ j ] = '\'';
                        j = j + 1;
                        cVal[ j ] = '\'';
                    }
                    else
                    {
                        cVal[ j ] = value.charAt( i );
                    }
                    j = j + 1;
                    i = i + 1;
                }
                else
                {
                    if (value.charAt(i) == '"')
                    {
                        cVal[ j ] = '"';
                        j = j + 1;
                        cVal[ j ] = '"';
                        j = j + 1;
                        i = i + 1;
                    }
                    else
                    {
                        cVal[ j ] = value.charAt( i );
                        j = j + 1;
                        i = i + 1;
                    }
                }                
            }
            return new String(cVal, 0, j);
        }
        catch(Exception e)
        {}
        return "";
    }
    
    private void CreatePlSqlArgs(StringBuilder builder, Element varNode, boolean isArg)
    {
        try 
        {
            Node nameNode = varNode.getChildNodes().item(0);
            Node typeNode = varNode.getChildNodes().item(1);
            
            if (!isArg)
            {
                builder.append("\t");
            }
            builder.append(nameNode.getFirstChild().getNodeName());
            
            for(int i = 1; i < nameNode.getChildNodes().getLength(); i++)
            {
                builder.append("," + nameNode.getChildNodes().item(i).getNodeName());
            }
            
            if (isArg && varNode.getAttribute("Type") != null)
            {
                builder.append(" " + varNode.getAttribute("Type"));
            }
            String type = typeNode.getFirstChild().getNodeName();
            
            builder.append(" " + this.GetDomPlSqlName(type, !isArg));
        }
        catch(Exception e)
        {
        }
    }
    
    private void CreatePlSqlVars(StringBuilder builder, Element varNode, boolean isArg, Hashtable types)
    {
        try 
        {
            Node nameNode = varNode.getChildNodes().item(0);
            Node typeNode = varNode.getChildNodes().item(1);
            Node valueNode = varNode.getChildNodes().item(2);
            
            String type = typeNode.getFirstChild().getNodeName();            
            String typeSpec = this.GetDomPlSqlName(type, true);
            String valueSpec = "";
            
            if (type.equals("ARRAY"))
            {
                typeSpec = GenerateArrTypes(typeNode.getFirstChild(), builder, types);
            }
            
            if (valueNode != null)
            {
                valueSpec = this.GetVarDefValue(valueNode.getFirstChild(), type, typeSpec, types);
            }
            
            for(int i = 0; i < nameNode.getChildNodes().getLength(); i++)
            {
                if (valueSpec.equals(""))
                {
                    builder.append("\t" + nameNode.getChildNodes().item(i).getNodeName() + " " + typeSpec + ";" + newLineStr);
                }
                else
                {
                    builder.append("\t" + nameNode.getChildNodes().item(i).getNodeName() + " " + typeSpec + " := " + valueSpec + ";" + newLineStr);
                }
            }
        }
        catch(Exception e)
        {
        }
    }
    
    private String GetVarDefValue(Node valueNode, String type, String typeSpec, Hashtable types)
    {
        String res = "";
        
        try 
        {
            if (valueNode != null && valueNode.getNodeName().equals("const"))
            {
                res = ConvertConst((Element)valueNode); 
            }
            else
            {
                String baseType = "";
                String newTypeSpec = "";
                
                boolean isTupple = false;
                DomainDesc dDesc = null;
                
                if (types.containsKey(typeSpec))
                {
                    baseType = (String)types.get(typeSpec);
                    
                    if(this.userDefDomains.containsKey(baseType.toLowerCase(Locale.US)))
                    {
                        newTypeSpec = this.GetDomPlSqlName(baseType, true);
                    }
                    else
                    {
                        newTypeSpec = baseType;
                    }
                }
                else
                {
                    dDesc = this.GetBaseDomain(type);
                    
                    if (dDesc.getType() == DomainDesc.TUPPLE || dDesc.getType() == DomainDesc.CHOICE)
                    {
                        isTupple = true;
                    }
                    else
                    {
                        baseType = dDesc.getParentName();
                        newTypeSpec = this.GetDomPlSqlName(baseType, true);
                        
                        if (dDesc.getType() != DomainDesc.SET && valueNode == null)
                        {
                            return this.ConvertConst(SemAnalyzer.ptNames[dDesc.getPrimitiveDomainType()]);
                        }
                    }
                }
                
                res = typeSpec + "(";
                int len = 0;
                
                if (valueNode != null)
                {
                    len = valueNode.getChildNodes().getLength();
                }
                
                for(int i = 0; i < len; i++)
                {
                    if (isTupple && dDesc != null)
                    {
                        AttributeDesc ades = (AttributeDesc)dDesc.members.get(i);
                        baseType = ades.getDomName();
                        newTypeSpec = this.GetDomPlSqlName(baseType, true);
                    }
                    
                    Node valnode = null;
                    
                    if (valueNode != null)
                    {
                        valnode = valueNode.getChildNodes().item(i);
                    }
                    
                    if (i > 0)
                    {
                        res += ",";
                    }
                    res += this.GetVarDefValue(valnode, baseType, newTypeSpec, types);                    
                }
                
                if (isTupple && dDesc != null)
                {
                    for(int i = len; i < dDesc.getMembers().size(); i++)
                    {
                        AttributeDesc ades = (AttributeDesc)dDesc.members.get(i);
                        baseType = ades.getDomName();
                        newTypeSpec = this.GetDomPlSqlName(baseType, true);
                        
                        Node valnode = null;
                        
                        if (i > 0)
                        {
                            res += ",";
                        }
                        res += this.GetVarDefValue(valnode, baseType, newTypeSpec, types);                    
                    }
                }
                
                res += ")";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return res;
    }
    
    private String GenerateArrTypes(Node arrNode, StringBuilder builder, Hashtable types)
    {
        try 
        {
            int count = types.size();
            
            Node subsNode = arrNode.getFirstChild();
            Node typeNode = arrNode.getChildNodes().item(1);
            
            String type = typeNode.getFirstChild().getNodeName();            
            String typeSpec = this.GetDomPlSqlName(type, true);
            
            for(int i = subsNode.getChildNodes().getLength() -1; i >= 0; i--) 
            {
                Node subNode = subsNode.getChildNodes().item(i);
                
                int b = Integer.parseInt(SemAnalyzer.getTextContent(subNode.getChildNodes().item(0)));
                int e = Integer.parseInt(SemAnalyzer.getTextContent(subNode.getChildNodes().item(1)));
                
                String typeName = "GEN_ARR_TYPE_" + (types.size() + 1);
                
                if (i == subsNode.getChildNodes().getLength() -1)
                {
                    types.put(typeName, type);
                }
                else
                {
                    types.put(typeName, typeSpec);
                }
                
                
                builder.append("\tTYPE " + typeName + " IS VARRAY(" + (e - b + 1) + ") OF " + typeSpec + ";" + newLineStr);
                typeSpec = typeName;                
            }            
            return typeSpec;
        }
        catch(Exception e)
        {
        }
        return "";
    }
    
    public String GetDomPlSqlName(String domName, boolean addLen)
    {
        DomainDesc current = null;
        
        if (this.domains.containsKey(domName.toLowerCase(Locale.US)))
        {
            current = (DomainDesc)this.domains.get(domName.toLowerCase(Locale.US));
        }
        
        if (this.userDefDomains.containsKey(domName.toLowerCase(Locale.US)))
        {
            current = (DomainDesc)this.userDefDomains.get(domName.toLowerCase(Locale.US));
        }
        
        if (current == null)
        {
            return "";
        }
        
        int i = 1;
        
        while(i < 100)
        {
            if (current.getType() == DomainDesc.PRIMITIVE)
            {
                if (current.getName().toLowerCase(Locale.US).equals("iterator"))
                {
                    return "T_REF_CUR";
                }
                else
                {
                    return current.getName();
                }
            }
            else
            {
                if (current.getType() == DomainDesc.INHERITED_PRIMITIVE)
                {
                    String lenStr = "";
                    
                    if (current.getLength() != null && !current.getLength().equals("") && addLen)
                    {
                        lenStr = current.getLength();
                    }
                    
                    if (this.domains.containsKey(current.getPrimitiveTypeName().toLowerCase(Locale.US).toString()))
                    {
                        current = (DomainDesc)this.domains.get(current.getPrimitiveTypeName().toLowerCase(Locale.US).toString());  
                        
                        if (lenStr.equals(""))
                        {
                            return current.getName();
                        }
                        else
                        {
                            return current.getName() + "(" + lenStr + ")";
                        }
                    }
                }
                else
                {
                    if (current.getType() == DomainDesc.INHERITED_USER_DEF)
                    {
                        if (this.userDefDomains.containsKey(current.getParentName().toLowerCase(Locale.US)))
                        {
                            current = (DomainDesc)this.userDefDomains.get(current.getParentName().toLowerCase(Locale.US));  
                        }
                    }
                    else
                    {
                        if (current.getType() == DomainDesc.SET)
                        {
                            return "T_" + current.getName();
                        }
                        
                        if (current.getType() == DomainDesc.CHOICE || current.getType() == DomainDesc.TUPPLE)
                        {
                            return "O_" + current.getName();
                        }    
                    }
                }
            }
            
            i = i + 1;
        }
        return "";
    }
    
    public static void lookFeel()
    {
             try
             {
                 UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
             }
             catch ( Exception e ) 
             {
                 e.printStackTrace();
             }
         }
             
     private static Connection Connect()
     {
         String url = "jdbc:odbc:isscase_new";
         
         try
         {
             Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
             return (Connection)DriverManager.getConnection(url, "", "");
         }
         catch (ClassNotFoundException ef) 
         {
             JOptionPane.showMessageDialog(null, "ClassNotFoundException:    " + ef.getMessage() , "Connection error", JOptionPane.ERROR_MESSAGE);
         }
         catch(SQLException ex)
         {
             JOptionPane.showMessageDialog(null, "<html><center>This is not valid repository!", "Connection Error", JOptionPane.ERROR_MESSAGE);
         }
         return null;
     }
     
    public DomainDesc GetBaseDomain(String domName)
    {
        
        DomainDesc current = null;
        
        if (this.domains.containsKey(domName.toLowerCase(Locale.US)))
        {
            current = (DomainDesc)this.domains.get(domName.toLowerCase(Locale.US));
        }
        
        if (this.userDefDomains.containsKey(domName.toLowerCase(Locale.US)))
        {
            current = (DomainDesc)this.userDefDomains.get(domName.toLowerCase(Locale.US));
        }
        
        if (current == null)
        {
            return current;
        }
        
        int i = 1;
        
        while(i < 100)
        {
            if (current.getType() == DomainDesc.PRIMITIVE)
            {
                break;
            }
            else
            {
                if (current.getType() == DomainDesc.INHERITED_PRIMITIVE)
                {
                    if (this.domains.containsKey(current.getPrimitiveTypeName().toLowerCase(Locale.US).toString()))
                    {
                        current = (DomainDesc)this.domains.get(current.getPrimitiveTypeName().toLowerCase(Locale.US).toString());  
                    }
                }
                else
                {
                    if (current.getType() == DomainDesc.INHERITED_USER_DEF)
                    {
                        if (this.userDefDomains.containsKey(current.getParentName().toLowerCase(Locale.US)))
                        {
                            current = (DomainDesc)this.userDefDomains.get(current.getParentName().toLowerCase(Locale.US));  
                        }
                    }
                    else
                    {
                        break;
                    }
                }
            }
            
            i = i + 1;
        }
        
        return current;
    }
}
