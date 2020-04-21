package iisc.lang;

import iisc.IISFrameMain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.ArrayList;

import java.util.Hashtable;

import java.util.Locale;

import java.util.Stack;

import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.*;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SemAnalyzer 
{
    public static int PT_DECIMAL = 1;
    public static int PT_INT = 2;
    public static int PT_STRING = 3;
    public static int PT_DATE = 4;
    public static int PT_TIME = 5;
    public static int PT_BOOL = 6;
    public static int PT_ITERATOR = 7;
    
    public static String USER_DEF_FUNCTION = "0";
    public static String BUILT_IN_FUNCTION = "1";
    
    public static String[] ptNames = {"", "REAL", "INT", "STRING", "DATE", "TIME", "BOOL", "ITERATOR"};
    
    public static int[][] assignMatr =
    {
     {1, 1, 0, 0, 0, 0, 0},
     {1, 2, 0, 0, 0, 0, 0},
     {0, 0, 3, 0, 0, 0, 0},
     {0, 0, 0, 4, 0, 0, 0},
     {0, 0, 0, 0, 5, 0, 0},
     {0, 0, 0, 0, 0, 6, 0},
     {0, 0, 0, 0, 0, 0, 7}
    };
    
    public static int[][] addMatr =
    {
     {1, 1, 0, 0, 0, 0},
     {1, 2, 0, 0, 0, 0},
     {0, 0, 3, 0, 0, 0},
     {0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0}
    };
    
    public static int[][] minusMatr =
    {
     {1, 1, 0, 0, 0, 0, 0},
     {1, 2, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0}
    };
    
    public static int[][] mulMatr =
    {
     {1, 1, 0, 0, 0, 0, 0},
     {1, 2, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0}
    };
    
    public static int[][] boolMatr =
    {
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 6, 0},
     {0, 0, 0, 0, 0, 0, 0}
    };
    
    public static int[][] compMatr =
    {
     {6, 6, 0, 0, 0, 0, 0},
     {6, 6, 0, 0, 0, 0, 0},
     {0, 0, 6, 0, 0, 0, 0},
     {0, 0, 0, 6, 0, 0, 0},
     {0, 0, 0, 0, 6, 0, 0},
     {0, 0, 0, 0, 0, 6, 0},
     {0, 0, 0, 0, 0, 0, 0}
    };
    
    public static int[][] relMatr =
    {
     {6, 6, 0, 0, 0, 0, 0},
     {6, 6, 0, 0, 0, 0, 0},
     {0, 0, 6, 0, 0, 0, 0},
     {0, 0, 0, 6, 0, 0, 0},
     {0, 0, 0, 0, 6, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0}
    };
    
    public static int[][] modMatr =
    {
     {0, 0, 0, 0, 0, 0, 0},
     {0, 2, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0}
     
    };
    
    public static int[][] concatMatr =
    {
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 3, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0},
     {0, 0, 0, 0, 0, 0, 0}
    };
    
    public static int[] notArr = {0, 0, 0, 0, 0, 6, 0};
    public static int[] unMinusArr = {1, 2, 0, 0, 0, 0, 0};
    public static int[] inMinusArr = {6, 6, 6, 6, 6, 6, 0};
    public static int[] likeMinusArr = {0, 0, 6, 0, 0, 0, 0};
    public static int[] selfuncArr = {1, 2, 0, 0, 0, 0, 0};
    
    public Connection con;
    public int PR_id;
    Element currentAssFNode = null;
    
    public boolean error; 
    ArrayList errors = new ArrayList();
    String contextName = "";
    int i;
    public Document doc = null;
    Document assemblerDoc = null;
    Document errDoc = null;
    Node root = null;
    Node assRoot = null;
    Element errRoot = null;
    Node curretnFuncNode = null;
    Node curretnVarsNode = null;
    Node currentArgsNode = null;
    public Node curretnVarNode = null;
    Node curretnVarNameNode = null;
    Node currentTypeNode = null;
    Hashtable domains = new Hashtable();
    Hashtable userDefDomains = new Hashtable();
    DomainDesc currentDomainDesc = null;
    Hashtable args = new Hashtable();
    Hashtable vars = new Hashtable();
    Hashtable env_vars = new Hashtable();
    Hashtable functions = new Hashtable();
    Hashtable predefinedFunc = new Hashtable();
    Hashtable select_functions = new Hashtable();
    Hashtable used_env_vars = new Hashtable();
    Hashtable tobs = new Hashtable();
    Hashtable tobCommands = new Hashtable();
    Hashtable tobProps = new Hashtable();
    Hashtable tobFieldProps = new Hashtable();
    
    public FunctionDesc currFuncDesc = null;
    public TobDesc currentTobDesc = null;
    Node currentStatementsNode = null;
    public Node currentNode = null;
    public Node currentFuncNode = null;
    public Node currentArgNode = null;
    public Node currentAssFVarsNode = null;
    public Node currentAssFCodeNode = null;
    private Stack exitStack = new Stack();
    public int loopCount = 0;
    DomainDesc iterDomain;
    public Element currentStatNode = null;
    public boolean generateCode = true;
    
    public SemAnalyzer(Connection con, int PR_id, Element currentAssFNode)
    {
        this.error = false;
        this.con = con;
        this.PR_id = PR_id;
        this.currentAssFNode = currentAssFNode;
        
        InitXml();
        InitAssemblerCode();
        InitDomains();
        InitFunctions();
        LoadPredefinedDesc();
        InitErrorsXML();
        InitEnvVars();
        InitTobs();
    }
    
    public Node BeginExpr()
    {
        Node temp = doc.createElement("Tmp");
        this.currentNode.appendChild(temp);
        ((Element)temp).setAttribute("Break", "0,0");
        return temp;
    }
    
    public void InitEnvVars()
    {
        this.env_vars.clear();
        
        try 
        {
            Statement stmt = this.con.createStatement();
            
            String sql = "select IISC_ENV_VAR.Var_id as Var_id,IISC_ENV_VAR.Var_name as Var_Name,IISC_ENV_VAR.Dom_Id as Dom_Id,IISC_DOMAIN.Dom_mnem as Dom_mnem" +
            " from IISC_ENV_VAR, IISC_DOMAIN where IISC_ENV_VAR.Dom_Id=IISC_DOMAIN.Dom_id";
            
            ResultSet rs = stmt.executeQuery(sql);
            
            Vector data = new Vector();
            
            while(rs.next())
            {
                String env_var_name = rs.getString("Var_Name");
                String env_var_dom = rs.getString("Dom_mnem");
                
                if (!this.env_vars.containsKey(env_var_name.toLowerCase(Locale.US)))
                {
                    this.env_vars.put(env_var_name.toLowerCase(Locale.US), env_var_dom.toLowerCase(Locale.US));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void InitDomains()
    {
        try 
        {
            if (this.con == null)
            {
                return;
            }
            iterDomain = new DomainDesc("ITERATOR","0", DomainDesc.PRIMITIVE);
            iterDomain.setPrimitiveDomainType(SemAnalyzer.PT_ITERATOR);
            
            if (!this.domains.containsKey(iterDomain.getName().toLowerCase(Locale.US)))
            {
                this.domains.put(iterDomain.getName().toLowerCase(Locale.US), iterDomain);
            }
            
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from IISC_PRIMITIVE_TYPE");
            
            while(rs.next())
            {
                DomainDesc d = new DomainDesc(rs.getString("PT_mnemonic"),rs.getString("PT_id"), DomainDesc.PRIMITIVE);
                d.setPrimitiveDomainType(rs.getInt("PT_base_type"));
                
                if (!this.domains.containsKey(d.getName().toLowerCase(Locale.US)))
                {
                    this.domains.put(d.getName().toLowerCase(Locale.US), d);
                }
            }
            
            stmt = this.con.createStatement();
            rs = stmt.executeQuery("select * from IISC_DOMAIN where PR_id=" + this.PR_id);
                        
            while(rs.next())
            {
                DomainDesc d = new DomainDesc(rs.getString("Dom_mnem"),rs.getString("Dom_id"), DomainDesc.PRIMITIVE);
                d.dom_len = rs.getString("Dom_length");
                
                int dom_type = rs.getInt("Dom_type");
                
                if (dom_type==0)
                {
                    d.setType(DomainDesc.INHERITED_PRIMITIVE);
                    d.setPrimitiveTypeId(rs.getString("Dom_data_type"));
                    
                    Statement stmt1 = this.con.createStatement();
                    ResultSet rs1 = stmt1.executeQuery("select * from IISC_PRIMITIVE_TYPE where PT_id=" + d.getPrimitiveTypeId());
                    
                    if(rs1.next())
                    {
                        d.setPrimitiveTypeName(rs1.getString("PT_mnemonic"));
                    }
                }
                else
                {
                    if (dom_type==1)
                    {
                        d.setType(DomainDesc.INHERITED_USER_DEF);
                        
                        Statement stmt1 = this.con.createStatement();
                        ResultSet rs1 = stmt1.executeQuery("select * from IISC_DOMAIN where Dom_id=" + rs.getString("Dom_parent"));
                        
                        if(rs1.next())
                        {
                            d.setParentName(rs1.getString("Dom_mnem"));
                        }
                    }
                    else
                    {
                        if (dom_type==2 || dom_type==3)
                        {
                            if (dom_type==2)
                            {
                                d.setType(DomainDesc.TUPPLE);
                            }
                            else
                            {
                                d.setType(DomainDesc.CHOICE);
                            }
                            
                            Statement stmt1 = this.con.createStatement();
                            ResultSet rs1 = stmt1.executeQuery("select * from IISC_DOM_ATT where Dom_id=" + d.getId());
                            
                            while(rs1.next())
                            {
                                String attId = rs1.getString("Att_id");
                                int rbr = rs1.getInt("Att_rbr");
                                
                                Statement stmt2 = this.con.createStatement();
                                ResultSet rs2 = stmt2.executeQuery("select * from IISC_ATTRIBUTE where Att_id=" + attId);
                                
                                if (rs2.next())
                                {
                                    AttributeDesc aDesc = new AttributeDesc(rs2.getString("Att_mnem"), attId, rs2.getString("Dom_id"), rbr);
                                    
                                    Statement stmt3 = this.con.createStatement();
                                    ResultSet rs3 = stmt3.executeQuery("select * from IISC_DOMAIN where Dom_id=" + aDesc.getDomId());
                                    
                                    if(rs3.next())
                                    {
                                        aDesc.setDomName(rs3.getString("Dom_mnem"));
                                        d.getMembers().add(aDesc);
                                    }
                                }
                            }
                        }
                        else
                        {
                            if (dom_type==4)
                            {
                                d.setType(DomainDesc.SET);
                                
                                String domParId = rs.getString("Dom_parent");
                                
                                if ( domParId != null && domParId != "")
                                {
                                    Statement stmt1 = this.con.createStatement();
                                    ResultSet rs1 = stmt1.executeQuery("select * from IISC_DOMAIN where Dom_id=" + domParId);
                                    
                                    if(rs1.next())
                                    {
                                        d.setParentName(rs1.getString("Dom_mnem"));
                                    }
                                }
                                else
                                {
                                    Statement stmt1 = this.con.createStatement();
                                    ResultSet rs1 = stmt1.executeQuery("select * from IISC_PRIMITIVE_TYPE where PT_id=" + rs.getString("Dom_data_type"));
                                    
                                    if(rs1.next())
                                    {
                                        d.setParentName(rs1.getString("PT_mnemonic"));
                                    }
                                }                                
                            }
                        }
                    }
                }
                
                
                if (!this.userDefDomains.containsKey(d.getName().toLowerCase(Locale.US)))
                {
                    this.userDefDomains.put(d.getName().toLowerCase(Locale.US), d);
                }
            }
        }
        catch(Exception e)
        {
        }
    }
    
    public void InitTobs()
    {
        try 
        {
            this.tobCommands = new Hashtable();
            this.tobCommands.put("reload", "");
            this.tobCommands.put("save", "");
            this.tobCommands.put("delete", "");
            this.tobCommands.put("next_rec", "");
            this.tobCommands.put("prev_rec", "");
            this.tobCommands.put("activate", "");
            this.tobCommands.put("deactivate", "");
            this.tobCommands.put("show", "");
            this.tobCommands.put("hide", "");
            
            this.tobFieldProps.put("bgcolor", "");
            this.tobFieldProps.put("visible", "");
            this.tobFieldProps.put("enabled", "");
            this.tobFieldProps.put("value", "");
            this.tobFieldProps.put("focus", "");
            
            if (this.con == null)
            {
                return;
            }
            
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where PR_id=" + this.PR_id);
            
            while(rs.next())
            {
                TobDesc d = new TobDesc();
                d.tobId = rs.getString("Tob_id");
                d.tobMnem = rs.getString("Tob_mnem");
                
                Statement stmt1 = this.con.createStatement();
                ResultSet rs1 = stmt1.executeQuery("select * from IISC_ATT_TOB where Tob_id=" + d.tobId);
                
                while(rs1.next())
                {
                    AttributeDesc aDesc = new AttributeDesc("",rs1.getString("Att_id"), "", 0);
                    
                    Statement stmt3 = this.con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("select * from IISC_ATTRIBUTE where Att_id=" + aDesc.getId());
                    
                    if(rs3.next())
                    {
                        aDesc.setName(rs3.getString("Att_mnem"));
                    }
                    
                    if (!d.atst.containsKey(aDesc.getName().toLowerCase(Locale.US)))
                    {
                        d.atst.put(aDesc.getName().toLowerCase(Locale.US), aDesc);
                    }
                }
                        
                if (!this.tobs.containsKey(d.tobMnem.toLowerCase(Locale.US)))
                {
                    this.tobs.put(d.tobMnem.toLowerCase(Locale.US), d);
                }
            }
        }
        catch(Exception e)
        {
        }
    }
    
    public void InitFunctions()
    {
        try 
        {
            this.select_functions.put("sum", "");
            this.select_functions.put("avg", "");
            this.select_functions.put("count", "");
            this.select_functions.put("count_distinct", "");
            
            if (this.con == null)
            {
                return;
            }
            
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from IISC_FUNCTION where PR_id=" + this.PR_id);
            
            while(rs.next())
            {
                FunctionDesc d = new FunctionDesc("","");
                
                d.setName(rs.getString("Fun_name"));
                Statement stmt1 = this.con.createStatement();
                
                ResultSet rs1 = stmt1.executeQuery("select * from IISC_DOMAIN where Dom_id=" + rs.getString("Dom_id"));
                
                if(rs1.next())
                {
                    d.setDomName(rs1.getString("Dom_mnem"));
                }
                rs1.close();
                stmt1.close();
                
                stmt1 = this.con.createStatement();
                rs1 = stmt1.executeQuery("select * from IISC_FUN_PARAM where Fun_id=" + rs.getString("Fun_id"));
                
                while(rs1.next())
                {
                    ParamDesc p = new ParamDesc("","");
                    
                    p.setName(rs1.getString("Param_name"));
                    p.setSeqNumber(rs1.getInt("Param_seq"));
                    p.setType(rs1.getInt("inout"));
                    
                    Statement stmt3 = this.con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("select * from IISC_DOMAIN where Dom_id=" + rs1.getString("Dom_id"));
                    
                    if(rs3.next())
                    {
                        p.setDomName(rs3.getString("Dom_mnem"));
                    }
                    
                    d.getParams().add(p);
                }
                        
                if (!this.functions.containsKey(d.getName().toLowerCase(Locale.US)))
                {
                    this.functions.put(d.getName().toLowerCase(Locale.US), d);
                }
            }
        }
        catch(Exception e)
        {
        }
    }
    
    public Hashtable getVariables()
    {
        return this.vars;
    }
    
    public Hashtable getIteratorVariables()
    {
        Hashtable result = new Hashtable();
        for(int i = 0; i < curretnVarsNode.getChildNodes().getLength(); i++)
        {
            try 
            {
                Element varNode = (Element)curretnVarsNode.getChildNodes().item(i);            
                Element nameNode = (Element)varNode.getFirstChild();
                Element typeNode = (Element)varNode.getChildNodes().item(1).getFirstChild();
                
                String name = typeNode.getNodeName();
                
                if (name.equalsIgnoreCase("iterator"))
                {
                    for(int j = 0; j < nameNode.getChildNodes().getLength(); j++)
                    {
                        Element varNameNode = (Element)nameNode.getChildNodes().item(j);    
                        result.put(varNameNode.getNodeName(), "");
                    }
                }                
            }
            catch(Exception e)
            {}
            
        }
        return result;
    }
    
    public Hashtable getArgs()
    {
        return this.args;
    }
    
    public Hashtable getFunctions()
    {
        return this.functions;
    }
    
    public Hashtable getPredefinedFunctions()
    {
        return this.predefinedFunc;
    }
    
    public Hashtable getDomains()
    {
        return this.domains;
    }
    
    public Hashtable getUserDefDomains()
    {
        return this.userDefDomains;
    }
    
    public Hashtable getEnvVars()
    {
        return this.env_vars;
    }
    
    public Hashtable getCompTypes()
    {
        return this.tobs;
    }
    
    public Hashtable getCompTypesCommands()
    {
        return this.tobCommands;
    }
    
    private void LoadPredefinedDesc()
    {
        try 
        {
            InputStream in =  JSourceCodeEditor.class.getResourceAsStream("xml/IISCasePredefinedFunctions.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document builtInDoc = builder.parse(in);
            
            Element builInRoot = builtInDoc.getDocumentElement();
            
            for(int i = 0; i < builInRoot.getChildNodes().getLength();i++)
            {
                if (builInRoot.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
                {
                    Element packageNode = (Element)builInRoot.getChildNodes().item(i);
                    
                    for(int j = 0; j < packageNode.getChildNodes().getLength(); j++)
                    {
                        if (packageNode.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element funcNode = (Element)packageNode.getChildNodes().item(j);
                            
                            FunctionDesc fSpec = new FunctionDesc("", "");
                            fSpec.name = funcNode.getNodeName();
                            fSpec.setIsBuiltIn(true);
                            
                            String code = funcNode.getAttribute("Code");
                            
                            if (code != null && code != "")
                            {
                                fSpec.setCode(code);
                            }
                            
                            NodeList returnNL = funcNode.getElementsByTagName("RETURN_TYPE");
                            
                            if (returnNL.getLength() > 0)
                            {
                                Element retTypeNode = (Element)returnNL.item(0);
                                
                                for(int k = 0; k < retTypeNode.getChildNodes().getLength(); k++)
                                {
                                    if (retTypeNode.getChildNodes().item(k).getNodeType() == Node.ELEMENT_NODE)
                                    {
                                        fSpec.domName = retTypeNode.getChildNodes().item(k).getNodeName();
                                    }
                                }
                            }
                            
                            NodeList paramsNL = funcNode.getElementsByTagName("PARAMS");
                            
                            if (paramsNL.getLength() > 0)
                            {
                                Element paramsNode = (Element)paramsNL.item(0);
                                
                                int s = 1;
                                
                                for(int k = 0; k < paramsNode.getChildNodes().getLength(); k++)
                                {
                                    if (paramsNode.getChildNodes().item(k).getNodeType() == Node.ELEMENT_NODE)
                                    {   
                                        Element paramNode = (Element)paramsNode.getChildNodes().item(k);
                                        
                                        ParamDesc p = new ParamDesc("","");
                                        p.setName(paramNode.getNodeName());
                                        p.setSeqNumber(s++);
                                        p.setType(ParamDesc.IN);
                                        fSpec.params.add(p);
                                        
                                        for(int b = 0; b < paramNode.getChildNodes().getLength(); b++)
                                        {
                                            if (paramNode.getChildNodes().item(b).getNodeType() == Node.ELEMENT_NODE)
                                            {  
                                                p.setDomName(paramNode.getChildNodes().item(b).getNodeName());
                                                break;
                                            }
                                        } 
                                            
                                    }
                                }
                                
                            }
                            //Element paramsNode = (Element)funcNode.getChildNodes().item(0);
                            
                            
                            if (!this.predefinedFunc.containsKey(fSpec.name.toLowerCase(Locale.US)))
                            {
                                this.predefinedFunc.put(fSpec.name.toLowerCase(Locale.US), fSpec);
                            }
                        }
                    }

                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void InitErrorsXML()
    {
        try 
        {
            InputStream in =  SemAnalyzer.class.getResourceAsStream("xml/IISCasePaserErrors.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            errDoc = builder.parse(in);
            
            errRoot = errDoc.getDocumentElement();
        }
        catch(Exception e)
        {
        }
    } 
    
    public ErrorDescription LoadErrorDescription(String[] args, String code, int linePos, int charPos)
    {
        ErrorDescription result = new ErrorDescription();
        
        try 
        {
            NodeList errNodes = errRoot.getElementsByTagName(code);
            
            if ( errNodes.getLength() > 0 )
            {
                Node errNode = errNodes.item(0);
                NodeList textNodes = ((Element)errNode).getElementsByTagName("text");
                
                if (textNodes.getLength() > 0)
                {
                    String message = getTextContent(textNodes.item(0));
                    result.charPos = charPos;
                    result.line = linePos;
                    result.message = message;
                    
                    for(int i = 0; i < args.length; i++)
                    {
                        result.message = result.message.replaceAll("%s" + (i+1), args[i]);
                    }
                    
                    this.errors.add(result);
                    //System.out.println("line :" + result.line + " pos " + result.charPos);
                }
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static void setTextContent(Node node, Document doc, String text)
    {
        if (node == null || doc == null || text == null) 
        {
            return;
        }
        
        Node textNode = doc.createTextNode(text);
        node.appendChild(textNode);
    }
    
    public static String getTextContent(Node node)
    {
        if (node == null) 
        {
            return "";
        }
        
        for(int i = 0; i < node.getChildNodes().getLength(); i++)
        {
            Node textNode = node.getChildNodes().item(i);
            
            if (textNode.getNodeType() == Node.TEXT_NODE)
            {
                String text = textNode.getNodeValue();
                return text;
            }
        }

        return "";
    }
    
    public void InitXml()
    {
        try 
        {
            DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = df.newDocumentBuilder();
            doc = db.newDocument();
            
            root = doc.createElement("IISCase");
            doc.appendChild(root);            
        }
        catch(Exception e)
        {
            System.out.println(e.getStackTrace());
        }
    }
    
    public void InitAssemblerCode()
    {
        try 
        {
            if ( this.currentAssFNode == null )
            {
                return;
            }
            
            this.assemblerDoc = this.currentAssFNode.getOwnerDocument();
            
            NodeList list = this.currentAssFNode.getElementsByTagName("VARIABLES");
            
            if(list.getLength() > 0)
            {
                this.currentAssFVarsNode = list.item(0);
                this.currentAssFNode.removeChild(this.currentAssFVarsNode);
            }
            
            list = this.currentAssFNode.getElementsByTagName("COMMANDS");
                        
            if(list.getLength() > 0)
            {
                this.currentAssFCodeNode = list.item(0);
                this.currentAssFNode.removeChild(this.currentAssFCodeNode);
            }
            this.currentAssFVarsNode = this.assemblerDoc.createElement("VARIABLES");
            this.currentAssFCodeNode = this.assemblerDoc.createElement("COMMANDS");
            
            if(this.currentAssFNode.getFirstChild() == null)
            {
                this.currentAssFNode.appendChild(this.currentAssFVarsNode);
                this.currentAssFNode.appendChild(this.currentAssFCodeNode);
            }
            else
            {
                this.currentAssFNode.insertBefore(this.currentAssFCodeNode, this.currentAssFNode.getFirstChild());
                this.currentAssFNode.insertBefore(this.currentAssFVarsNode, this.currentAssFNode.getFirstChild());
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e.getStackTrace());
        }
    }
    
    public Document getAstXml()
    {
        return this.doc;
    }
    
    public Document getAssemberCode()
    {
        return this.assemblerDoc;
    }

    public boolean getError()
    {
        return this.errors.size() > 0;
    }
    
    public ArrayList getErrors()
    {
        return this.errors;
    }
    
    public void reportError(RecognitionException e)
    {
        error = true;
        String[] errs;
        ErrorDescription desc = new ErrorDescription();
        String message;

        if (e instanceof NoViableAltException || e instanceof FailedPredicateException || e instanceof MismatchedSetException )
        {
            if (e.token.getType()  == -1)
            {
                errs = new String[ 3 ];
                errs[ 0 ] = Integer.toString(e.line); 
                errs[ 1 ] = Integer.toString(e.charPositionInLine);
                errs[ 2 ] = this.contextName;
                this.LoadErrorDescription(errs, "UNEXPECED_EOF", e.line, e.charPositionInLine);
            }
            else
            {
                errs = new String[ 4 ];
                errs[ 0 ] = Integer.toString(e.line); 
                errs[ 1 ] = Integer.toString(e.charPositionInLine);
                errs[ 2 ] = this.contextName;
                errs[ 3 ] = e.token.getText();
                this.LoadErrorDescription(errs, "UNEXPECED_TOKEN_CONTEXT", e.line, e.charPositionInLine);
            }
        }
        else
        {
            //String message = "line:" + e.Line + ":" + e.CharPositionInLine + " ";
            if (!(e instanceof RecognitionException))
            {
                errors.add(((RecognitionException)e).getDescription());
            }
        }
    }

    public void match(TokenStream input, int ttype, BitSet follow) throws RecognitionException
    {
        //base.Match (input, ttype, follow);
        i = input.LA(1);
            
        if ( i != ttype )
        {
            String message;
            ErrorDescription desc = new ErrorDescription();
            String[] errs;
            this.error = true;
            Token errToken = input.LT(1);
                    
            if ( ttype == -1 )
            {
                if (errToken.getCharPositionInLine() == -1)
                {
                    errs = new String[ 3 ];
                    errs[ 0 ] = Integer.toString(((IISCaseTokenStream)input).chStream.line); 
                    errs[ 1 ] = Integer.toString(((IISCaseTokenStream)input).chStream.charPositionInLine);                    
                    errs[ 2 ] = this.contextName;
                    desc = this.LoadErrorDescription(errs, "EOF_EXPECED", ((IISCaseTokenStream)input).chStream.line, ((IISCaseTokenStream)input).chStream.charPositionInLine);
                    throw new RecognitionException(desc,input);
                }
                else
                {
                    errs = new String[ 3 ];
                    errs[ 0 ] = Integer.toString(errToken.getLine());
                    errs[ 1 ] = Integer.toString(errToken.getCharPositionInLine());                   
                    errs[ 2 ] = this.contextName;
                    desc = this.LoadErrorDescription(errs, "EOF_EXPECED", errToken.getLine(), errToken.getCharPositionInLine());
                    throw new RecognitionException(desc,input);
                }
            }                               
                        
            if ( i == -1 )
            { 
                if (errToken.getCharPositionInLine() == -1)
                {
                    errs = new String[ 4 ];
                    errs[ 0 ] = Integer.toString(((IISCaseTokenStream)input).chStream.line); 
                    errs[ 1 ] = Integer.toString(((IISCaseTokenStream)input).chStream.charPositionInLine);  
                    errs[ 2 ] = this.contextName;
                    errs[ 3 ] = IISCaseLangParser.tokenNames[ttype];                        
                    desc = this.LoadErrorDescription(errs, "UNEXPECED_EOF_EXPECTED", ((IISCaseTokenStream)input).chStream.line, ((IISCaseTokenStream)input).chStream.charPositionInLine);
                    throw new RecognitionException(desc,input);
                }
                else
                {
                    errs = new String[ 4 ];
                    errs[ 0 ] = Integer.toString(errToken.getLine());
                    errs[ 1 ] = Integer.toString(errToken.getCharPositionInLine());
                    errs[ 2 ] = this.contextName;
                    errs[ 3 ] = IISCaseLangParser.tokenNames[ttype];                        
                    desc = this.LoadErrorDescription(errs, "UNEXPECED_EOF_EXPECTED", errToken.getLine(), errToken.getCharPositionInLine());
                    throw new RecognitionException(desc,input);
                }         
            }
                    
            if (input.LT(1).getText().compareTo("") == 0)
            {
                if (errToken.getCharPositionInLine() == -1)
                {
                    errs = new String[ 4 ];
                    errs[ 0 ] = Integer.toString(((IISCaseTokenStream)input).chStream.line); 
                    errs[ 1 ] = Integer.toString(((IISCaseTokenStream)input).chStream.charPositionInLine);  
                    errs[ 2 ] = this.contextName;
                    errs[ 3 ] = IISCaseLangParser.tokenNames[ttype];                        
                    desc = this.LoadErrorDescription(errs, "EXPECTED_TOKEN_CONTEXT", ((IISCaseTokenStream)input).chStream.line, ((IISCaseTokenStream)input).chStream.charPositionInLine);
                    throw new RecognitionException(desc,input);
                }
                else
                {
                    errs = new String[ 4 ];
                    errs[ 0 ] = Integer.toString(errToken.getLine());
                    errs[ 1 ] = Integer.toString(errToken.getCharPositionInLine());
                    errs[ 2 ] = this.contextName;
                    errs[ 3 ] = IISCaseLangParser.tokenNames[ttype];                        
                    desc = this.LoadErrorDescription(errs, "EXPECTED_TOKEN_CONTEXT", errToken.getLine(), errToken.getCharPositionInLine());
                    throw new RecognitionException(desc,input);
                }
            }
                    
            if (errToken.getCharPositionInLine() == -1)
            {       
                errs = new String[ 5 ];
                errs[ 0 ] = Integer.toString(((IISCaseTokenStream)input).chStream.line); 
                errs[ 1 ] = Integer.toString(((IISCaseTokenStream)input).chStream.charPositionInLine);
                errs[ 2 ] = this.contextName;
                errs[ 3 ] = IISCaseLangParser.tokenNames[ttype];
                errs[ 4 ] = input.LT(1).getText();
                desc = this.LoadErrorDescription(errs, "EXPECTED_TOKEN_FOUND", ((IISCaseTokenStream)input).chStream.line, ((IISCaseTokenStream)input).chStream.charPositionInLine);
                throw new RecognitionException(desc,input);
            }
            else
            {
                errs = new String[ 5 ];
                errs[ 0 ] = Integer.toString(errToken.getLine());
                errs[ 1 ] = Integer.toString(errToken.getCharPositionInLine());
                errs[ 2 ] = this.contextName;
                errs[ 3 ] = IISCaseLangParser.tokenNames[ttype];
                errs[ 4 ] = input.LT(1).getText();
                desc = this.LoadErrorDescription(errs, "EXPECTED_TOKEN_FOUND", errToken.getLine(), errToken.getCharPositionInLine());
                throw new RecognitionException(desc,input);
            }
        }                       
            
        input.consume();        
    }
    
    public void CreateFuncNode(String funcName)
    {
        this.curretnFuncNode = doc.createElement(funcName);
        root.appendChild(this.curretnFuncNode);
        this.contextName = "Function " + funcName;
        this.currentArgsNode = doc.createElement("ARGS");
        this.curretnFuncNode.appendChild(this.currentArgsNode);
        
        /*currentAssFNode = this.assemblerDoc.createElement(funcName);
        this.assRoot.appendChild(currentAssFNode);
        this.currentAssFDataNode = this.assemblerDoc.createElement("DATA");
        currentAssFNode.appendChild(this.currentAssFDataNode);*/
        
    }
    
    public void CreateFuncReturnNode(String domainName, Token tok)
    {
         Node returnsNode = doc.createElement("RETURNS");
         Node temp = doc.createElement("TYPE");
         temp.appendChild(doc.createElement(domainName));
         returnsNode.appendChild(temp);
        
         if (this.curretnFuncNode != null)
         {
            this.curretnFuncNode.appendChild(returnsNode);
         }
         
         if (!this.domains.containsKey(domainName.toLowerCase(Locale.US)) && !this.userDefDomains.containsKey(domainName.toLowerCase(Locale.US)))
         {
             String[] errs = new String[4];
             errs[0] = Integer.toString(tok.getLine());
             errs[1] = Integer.toString(tok.getCharPositionInLine());
             errs[2] = this.contextName;
             errs[3] = tok.getText();
             
             this.LoadErrorDescription(errs, "UNKNOWN_DOMAIN", tok.getLine(), tok.getCharPositionInLine());
         }
        
    }
    
    public void ValidateBreakStatement(Token tok)
    {
         if (this.loopCount == 0)
         {
             String[] errs = new String[3];
             errs[0] = Integer.toString(tok.getLine());
             errs[1] = Integer.toString(tok.getCharPositionInLine());
             errs[2] = this.contextName;
             
             this.LoadErrorDescription(errs, "INVALID_BREAK", tok.getLine(), tok.getCharPositionInLine());
         }
        
    }
    
    public void GenerateArgDefVal(String var_init_val)
    {
        try 
        {
            if (this.currentArgNode != null)
            {
                ((Element)this.currentArgNode).setAttribute("DefVal", var_init_val);
            }
        }
        catch(Exception e)
        {}
    }
    
    public void GenerateVarDefVal(String var_init_val)
    {
        try 
        {
            if (this.curretnVarNode != null)
            {
                ((Element)this.curretnVarNode).setAttribute("DefVal", var_init_val);
            }
        }
        catch(Exception e)
        {}
    }
    
    public void BeginVarNode(String domainName)
    {
        try 
        {
            String domName = domainName.toLowerCase(Locale.US);
            
            this.curretnVarNode = doc.createElement("VAR");
            
            this.currentTypeNode = doc.createElement("TYPE");
            this.currentTypeNode.appendChild(doc.createElement(domainName));
            this.curretnVarNode.appendChild(this.currentTypeNode);
            
            if (this.domains.containsKey(domName))
            {
                this.currentDomainDesc = (DomainDesc)this.domains.get(domName);
            }
            else
            {
                this.currentDomainDesc = (DomainDesc)this.userDefDomains.get(domName);
            }
        }
        catch(Exception e)
        {}
    }
    
    public void GenerateVarValueNode()
    {
        try 
        {
            if (this.curretnVarNode != null)
            {
                Element elem = this.doc.createElement("VALUE");
                this.curretnVarNode.appendChild(elem);
                this.currentNode = elem;
            }
        }
        catch(Exception e)
        {}
    }
    
    public void GenerateVarValueMultiInitNode()
    {
        try 
        {
            if (this.currentNode != null)
            {
                Element elem = this.doc.createElement("MULTI_INIT");
                this.currentNode.appendChild(elem);
                this.currentNode = elem;
            }
        }
        catch(Exception e)
        {}
    }
    
    public void ValidateArgFromRep(String id)
    {
        if (id == null || id.equals(""))
        {
            return;
        }
        try 
        {
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from IISC_FUN_PARAM where Fun_id=" + id + " order by Param_seq asc");
            
            int i = 0; 
            boolean can = true;
            
            while(rs.next())
            {
                try 
                {
                    String dom_id = rs.getString("Dom_id");
                    String inout = rs.getString("inout");
                    
                    if (i >= this.currentArgsNode.getChildNodes().getLength())
                    {
                        can = false;
                        break;
                    }
                    Element argNode = (Element)this.currentArgsNode.getChildNodes().item(i);
                    Element typeNode = (Element)argNode.getChildNodes().item(1);
                    
                    if (typeNode.getNodeName().equalsIgnoreCase("type"))
                    {
                        String domName = typeNode.getFirstChild().getNodeName();
                        
                        if (this.userDefDomains.containsKey(domName.toLowerCase(Locale.US)))
                        {
                            DomainDesc desc = (DomainDesc)this.userDefDomains.get(domName.toLowerCase(Locale.US));
                            
                            if(!desc.id.equalsIgnoreCase(dom_id))
                            {
                                can = false;
                                break;
                            }
                        }
                    }
                    
                    if (argNode.getAttribute("Type") != null)
                    {
                        String argIOType = argNode.getAttribute("Type");
                        
                        if (argIOType.equalsIgnoreCase("in"))
                        {
                            if (!inout.equalsIgnoreCase("0"))
                            {
                                can = false;
                                break;
                            }
                        }
                        else
                        {
                            if (argIOType.equalsIgnoreCase("out"))
                            {
                                if (!inout.equalsIgnoreCase("1"))
                                {
                                    can = false;
                                    break;
                                }
                            }
                            else
                            {
                                if (!inout.equalsIgnoreCase("2"))
                                {
                                    can = false;
                                    break;
                                }
                            }
                        }
                    }
                }
                catch(Exception e)
                {}
                i = i + 1;
            }
            rs.close();
            stmt.close();
            
            if (i != this.currentArgsNode.getChildNodes().getLength())
            {
                can = false;
            }
            
            if (!can)
            {
                String[] errs = new String[1];
                errs[0] = this.contextName;
                
                this.LoadErrorDescription(errs, "ARGS_DOES_NOT_MATCH_REP_DEF", 1, 1);
            }
        }
        catch(Exception e)
        {}
    }
    
    public void CreateArgNode(String argName, String domainName, Token tok, Token inoutTok, Token argTok)
    {
        try 
        {
            Element argNode = doc.createElement("ARG");
            
            if (inoutTok != null)
            {
                argNode.setAttribute("Type", inoutTok.getText().toLowerCase(Locale.US));
            }
            else
            {
                argNode.setAttribute("Type", "in");
            }
            
            this.currentArgNode = argNode;
            
            Node temp = doc.createElement("NAME");
            this.curretnVarNameNode = temp;
            temp.appendChild(doc.createElement(argName));
            argNode.appendChild(temp);
            temp = doc.createElement("TYPE");
            temp.appendChild(doc.createElement(domainName));
            argNode.appendChild(temp);
            this.currentTypeNode = temp;
            ((Element)argNode).setAttribute("OrdNum", Integer.toString(this.currentArgsNode.getChildNodes().getLength()));
            this.currentArgsNode.appendChild(argNode);
            
            if (this.args.containsKey(argName.toLowerCase(Locale.US)))
            {
                String[] errs = new String[4];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = argName;
                
                this.LoadErrorDescription(errs, "DUPLICATE_ARGUMENT_NAME", tok.getLine(), tok.getCharPositionInLine());
            }
            else
            {
                this.args.put(argName.toLowerCase(Locale.US), argNode);
            }
            
            if (!this.userDefDomains.containsKey(domainName.toLowerCase(Locale.US)))
            {
                String[] errs = new String[4];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = tok.getText();
                
                this.LoadErrorDescription(errs, "UNKNOWN_DOMAIN", tok.getLine(), tok.getCharPositionInLine());
            }
            
            this.currentDomainDesc = (DomainDesc)this.userDefDomains.get(domainName.toLowerCase(Locale.US));
            
            this.GenerateDomainAsemblerCode(domainName, this.currentAssFVarsNode);
            /*Element command = assemblerDoc.createElement("VAR");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.VAR_OC);
            command.setAttribute("Name", argName);
            currentAssFDataNode.appendChild(command);*/
        }
        catch(Exception e)
        {}
    }
    
    public void CreateBorderAtt(Node node, Token btok, Token etok)
    {
        try 
        {
            Element argNode = (Element)node;
            CommonToken begToken = (CommonToken)btok;
            CommonToken endToken = (CommonToken)etok;
            String value = Integer.toString(begToken.getStartIndex()) +","+ Integer.toString(endToken.getStopIndex());
            argNode.setAttribute("Borders", value);
        }
        catch(Exception e)
        {}
    }
    
    public void CreateBorderAtt(Node node, Token btok, String attName)
    {
        try 
        {
            Element argNode = (Element)node;
            CommonToken begToken = (CommonToken)btok;
            String value = Integer.toString(begToken.getStartIndex());
            argNode.setAttribute(attName, value);
        }
        catch(Exception e)
        {}
    }
    
    public void CreateEndBorderAtt(Node node, Token btok, String attName)
    {
        try 
        {
            Element argNode = (Element)node;
            CommonToken begToken = (CommonToken)btok;
            String value = Integer.toString(begToken.getStopIndex());
            argNode.setAttribute(attName, value);
        }
        catch(Exception e)
        {}
    }
    
    public void CreateAtt(Node node, Token btok, Token etok, String attName, IISCaseTokenStream input)
    {
        try 
        {
            Element argNode = (Element)node;
            CommonToken begToken = (CommonToken)btok;
            CommonToken endToken = (CommonToken)etok;
            String value = input.chStream.substring(begToken.getStartIndex(), endToken.getStopIndex());
            argNode.setAttribute(attName, value);
        }
        catch(Exception e)
        {}
    }
    
    public void CreateVarsNode()
    {
        this.curretnVarsNode = doc.createElement("VARS");
        this.curretnFuncNode.appendChild(this.curretnVarsNode);
    }
    
    public void CreateVarNameNode(String vName, Token tok)
    {
        this.curretnVarNameNode.appendChild(doc.createElement(vName));
        
        if (this.vars.containsKey(vName.toLowerCase(Locale.US)) || this.args.containsKey(vName.toLowerCase(Locale.US)))
        {
            String[] errs = new String[4];
            errs[0] = Integer.toString(tok.getLine());
            errs[1] = Integer.toString(tok.getCharPositionInLine());
            errs[2] = this.contextName;
            errs[3] = vName;
            
            this.LoadErrorDescription(errs, "DUPLICATE_VAR_NAME", tok.getLine(), tok.getCharPositionInLine());
        }
        else
        {
            ((Element)this.curretnVarNameNode.getLastChild()).setAttribute("OrdNum", Integer.toString(this.args.size() + this.vars.size()));            
            this.vars.put(vName.toLowerCase(Locale.US), this.curretnVarNameNode.getParentNode());
        }
    }
 
    public void CheckDomain(Token tok, boolean isArr)
    {
        String domName = tok.getText().toLowerCase(Locale.US);
        
        if (!this.domains.containsKey(domName) && !this.userDefDomains.containsKey(domName))
        {
            String[] errs = new String[4];
            errs[0] = Integer.toString(tok.getLine());
            errs[1] = Integer.toString(tok.getCharPositionInLine());
            errs[2] = this.contextName;
            errs[3] = tok.getText();
            
            this.LoadErrorDescription(errs, "UNKNOWN_DOMAIN", tok.getLine(), tok.getCharPositionInLine());
            this.currentDomainDesc = null;
        }
        else
        {
            if (!isArr)
            {
                this.currentTypeNode = doc.createElement("TYPE");
                this.currentTypeNode.appendChild(doc.createElement(tok.getText()));
                this.curretnVarNode.appendChild(this.currentTypeNode);
                
                if (this.domains.containsKey(domName))
                {
                    this.currentDomainDesc = (DomainDesc)this.domains.get(domName);
                }
                else
                {
                    this.currentDomainDesc = (DomainDesc)this.userDefDomains.get(domName);
                }
            }
            else
            {
                Node arrType = doc.createElement("TYPE");
                arrType.appendChild(doc.createElement(tok.getText()));
                
                if (this.currentTypeNode!= null && this.currentTypeNode.getFirstChild() != null)
                {
                    this.currentTypeNode.getFirstChild().appendChild(arrType);
                }
            }
            
            this.GenerateDomainAsemblerCode(domName, this.currentAssFVarsNode);
            
            if (isArr)
            {
                NodeList list = ((Element)this.currentTypeNode).getElementsByTagName("SUBRANGES");
                
                if(list.getLength() > 0)
                {
                    Node subRangesNode = list.item(0);
                    
                    for(int i = 0; i < subRangesNode.getChildNodes().getLength(); i++)
                    {
                        Element subNode = (Element)subRangesNode.getChildNodes().item(i);
                        
                        if (subNode.getChildNodes().getLength() == 2)
                        {
                            Element fromNode = (Element)subNode.getChildNodes().item(0);
                            Element toNode = (Element)subNode.getChildNodes().item(1);
                            
                            if (assemblerDoc != null)
                            {
                                Element command = assemblerDoc.createElement("INS");                   
                                command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.ARR_OC);
                                command.setAttribute("Beg", getTextContent(fromNode));
                                command.setAttribute("End", getTextContent(toNode));
                                this.currentAssFVarsNode.appendChild(command);
                            }
                        }
                    }
                    
                }
            }
        }
    }
    
    public void BeginArrNode()
    {
        this.currentTypeNode = doc.createElement("TYPE");
        this.currentTypeNode.appendChild(doc.createElement("ARRAY"));
        Node arrNode = this.currentTypeNode.getFirstChild();
        arrNode.appendChild(doc.createElement("SUBRANGES"));
        this.curretnVarNode.appendChild(this.currentTypeNode);
    }
    
    public void CreateSubrangeSpec(Token beg, Token end)
    {
        try 
        {
            int b = 0;
            int e = Integer.parseInt(end.getText());
            
            if (this.currentTypeNode.getFirstChild() != null && end != null)
            {
                Node arrNode = this.currentTypeNode.getFirstChild();
                Node subrangeNode = doc.createElement("SUBRANGE");
                Node a = doc.createElement("Beg");
                setTextContent(a, doc, "0");
                subrangeNode.appendChild(a);
                
                a = doc.createElement("End");
                setTextContent(a, doc, Integer.toString(e-1));
                subrangeNode.appendChild(a);
                
                if (arrNode.getFirstChild()!= null)
                {
                    arrNode.getFirstChild().appendChild(subrangeNode);
                }
                
                if ( b > e )
                {
                    String[] errs = new String[3];
                    errs[0] = Integer.toString(beg.getLine());
                    errs[1] = Integer.toString(beg.getCharPositionInLine());
                    errs[2] = this.contextName;
                    
                    this.LoadErrorDescription(errs, "INVALID_SUBRANGE", beg.getLine(), beg.getCharPositionInLine());
                }
            }                       
        }
        catch(Exception e)
        {
        } 
    }
    
    private int CalcDataTypeBaseCode(String typeName)
    {
        char c = typeName.toCharArray()[0];
        
        if ( c == 'I' || c == 'i' )
        {
            if (typeName.charAt(1) == 'n' || typeName.charAt(1) == 'N')
            {
                return SemAnalyzer.PT_INT;
            }
            
            return SemAnalyzer.PT_ITERATOR;
        }
        
        if ( c == 'B' || c == 'b' )
        {
            return SemAnalyzer.PT_BOOL;
        }
        
        if ( c == 's' || c == 'S' )
        {
            return SemAnalyzer.PT_STRING;
        }
        
        if ( c == 't' || c == 'T' )
        {
            return SemAnalyzer.PT_TIME;
        }
        
        if ( c == 'D' || c == 'd' )
        {
            return SemAnalyzer.PT_DATE;
        }
        
        if ( c == 'R' || c == 'r' )
        {
            return SemAnalyzer.PT_DECIMAL;
        }
        
        return -1;
    }
    
    public Node CheckTuppleOrArrayType(Node type, ArrayDim dim, Token tok)
    {
        try 
        {
            if (type == null || type.getFirstChild() == null)
            {
                return null;
            }
            
            String domName = type.getFirstChild().getNodeName();
            
            if (domName == "ARRAY")
            {
                Node subragesNode = type.getFirstChild().getChildNodes().item(0);
                Node arrTypeNode = type.getFirstChild().getChildNodes().item(1);                
                
                dim.lower = Integer.parseInt(getTextContent(subragesNode.getFirstChild().getChildNodes().item(0)));
                dim.upper = Integer.parseInt(getTextContent(subragesNode.getFirstChild().getChildNodes().item(1)));
                
                if (subragesNode.getChildNodes().getLength() == 1 || subragesNode.getChildNodes().getLength() == 0)
                {
                    //System.out.println(arrTypeNode.getTextContent());
                    return arrTypeNode;
                }
                else
                {
                    Node newTypeNode = type.cloneNode(true);
                    subragesNode = newTypeNode.getFirstChild().getChildNodes().item(0);
                    subragesNode.removeChild(subragesNode.getFirstChild());
                    
                    return newTypeNode;
                }
            }
            else
            {
                boolean can = true;
                DomainDesc current = null;
                
                if (this.domains.containsKey(domName.toLowerCase(Locale.US)))
                {
                    can = false;
                }
                else
                {
                    if (!this.userDefDomains.containsKey(domName.toLowerCase(Locale.US)))
                    {
                        return null;
                    }
                    
                    int i = 1;
                    
                    current = (DomainDesc)this.userDefDomains.get(domName.toLowerCase(Locale.US));
                    
                    while(i < 100)
                    {
                        if (current.getType() == DomainDesc.PRIMITIVE)
                        {
                            can = false;
                            break;
                        }
                        else
                        {
                            if (current.getType() == DomainDesc.INHERITED_PRIMITIVE)
                            {
                                can = false;
                                break;
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
                                    can = true; //tupple, choice, set
                                    break;
                                }
                            }
                        }
                        
                        i = i + 1;
                    }
                }
                
                if (!can)
                {
                    String[] errs = new String[4];
                    errs[0] = Integer.toString(tok.getLine());
                    errs[1] = Integer.toString(tok.getCharPositionInLine());
                    errs[2] = this.contextName;
                    errs[3] = domName;
                    
                    this.LoadErrorDescription(errs, "CAN_NOT_INIT_NOT_ARRAY", tok.getLine(), tok.getCharPositionInLine());
                    return null;
                }
                else
                {
                    Node resultNode = doc.createElement("TYPE"); 
                    resultNode.appendChild(doc.createElement(current.getName()));
                    return resultNode;
                }
            }
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    public Node GetMemberType(Node type, ArrayDim dim, int ordNum, Token tok)
    {
        try 
        {
            if (type == null || type.getFirstChild() == null)
            {
                return null;
            }
            
            if (dim.lower == 0 && dim.upper == -1)
            {
                String domName = type.getFirstChild().getNodeName();
                
                if (!this.userDefDomains.containsKey(domName.toLowerCase(Locale.US)))
                {
                    return null;
                }
                
                DomainDesc current = (DomainDesc)this.userDefDomains.get(domName.toLowerCase(Locale.US));
                    
                if (( current.getType() != DomainDesc.TUPPLE && current.getType() != DomainDesc.CHOICE && current.getType() != DomainDesc.SET))
                {
                    return null;
                }
                
                if (current.getType() != DomainDesc.SET)
                {
                    AttributeDesc attDesc = null;
                    
                    for(int i = 0; i < current.getMembers().size(); i++)
                    {
                        AttributeDesc temp = (AttributeDesc)current.getMembers().get(i);
                        
                        if (temp.getRbr() == ordNum+1)
                        {
                            attDesc = temp;
                        }
                    }
                    
                    if (attDesc == null)
                    {
                        String[] errs = new String[5];
                        errs[0] = Integer.toString(tok.getLine());
                        errs[1] = Integer.toString(tok.getCharPositionInLine());
                        errs[2] = this.contextName;
                        errs[3] = Integer.toString(ordNum);
                        errs[4] = Integer.toString(current.getMembers().size());
                        
                        this.LoadErrorDescription(errs, "TO_MANY_VALUES", tok.getLine(), tok.getCharPositionInLine());
                        return null;
                    }
                    else
                    {   
                        if (assemblerDoc != null)
                        {
                            Element command = assemblerDoc.createElement("INS");
                            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LOADM_OC);
                            command.setAttribute("OrdNum", Integer.toString(ordNum));
                            currentAssFVarsNode.appendChild(command);
                        }
                        
                        Node resultNode = doc.createElement("TYPE"); 
                        resultNode.appendChild(doc.createElement(attDesc.getDomName()));
                        return resultNode;
                    }
                }
                else
                {
                    if (assemblerDoc != null)
                    {
                        Element command = assemblerDoc.createElement("INS");
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LOADSET_OC);
                        currentAssFVarsNode.appendChild(command);
                    }
                    
                    Node resultNode = doc.createElement("TYPE"); 
                    resultNode.appendChild(doc.createElement(current.getParentName()));
                    return resultNode;
                }
            }
            else
            {
                int count = dim.upper - dim.lower + 1;
                
                if (ordNum >= count)
                {
                    String[] errs = new String[5];
                    errs[0] = Integer.toString(tok.getLine());
                    errs[1] = Integer.toString(tok.getCharPositionInLine());
                    errs[2] = this.contextName;
                    errs[3] = Integer.toString(ordNum);
                    errs[4] = Integer.toString(count);
                    
                    this.LoadErrorDescription(errs, "TO_MANY_VALUES", tok.getLine(), tok.getCharPositionInLine());
                    return null;
                }
                else
                {
                    if (assemblerDoc!=null)
                    {
                        Element command = assemblerDoc.createElement("INS");
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LOADARR_OC);
                        command.setAttribute("OrdNum", Integer.toString(ordNum));
                        currentAssFVarsNode.appendChild(command);
                    }
                    
                    return type;
                }
            }
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    private void CanAssignToPrimitive(int ptType, int constType, Token tok, String constValue)
    {
        if (ptType == -1 || SemAnalyzer.assignMatr[ptType-1][constType -1] == 0)
        {
            String[] errs = new String[5];
            errs[0] = Integer.toString(tok.getLine());
            errs[1] = Integer.toString(tok.getCharPositionInLine());
            errs[2] = this.contextName;
            errs[3] = constValue;
            errs[4] = this.currentDomainDesc.getName();
            
            this.LoadErrorDescription(errs, "CAN_NOT_ASS_CONST_TO_VAR", tok.getLine(), tok.getCharPositionInLine());
        }
    }
    
    public int GetDomainPrimitiveType(Node typeNode)
    {
        String domName = typeNode.getFirstChild().getNodeName();
        
        if (domName == "ARRAY")
        {
            return -1;
        }
        
        if (this.domains.containsKey(domName.toLowerCase(Locale.US)))
        {
            return this.GetDomainPrimitiveType((DomainDesc)this.domains.get(domName.toLowerCase(Locale.US)));
        }
        
        if (this.userDefDomains.containsKey(domName.toLowerCase(Locale.US)))
        {
            return this.GetDomainPrimitiveType((DomainDesc)this.userDefDomains.get(domName.toLowerCase(Locale.US)));
        }
        
        return -1;
    }
    
    public int GetDomainPrimitiveType(DomainDesc desc)
    {
        if (desc == null)
        {
            return -1;
        }
        
        int i = 1;
        
        DomainDesc current = desc;
        
        while(i < 100)
        {
            if (current.getType() == DomainDesc.PRIMITIVE)
            {
                return current.primitiveDomainType;
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
                }
            }
            
            i = i + 1;
        }
        
        return -1;
    }
    
    public void CheckVariableInitialization(Token tok, Node constantNode)
    {
        NodeList list = ((Element)constantNode).getElementsByTagName("TYPE");
        String value = "";
        
        if (list.getLength() > 0)
        {
            Node typeNode = list.item(0);
            
            if (typeNode.getFirstChild() != null)
            {
                String constType = typeNode.getFirstChild().getNodeName();
                //System.out.println(constType);
                int typeCode = this.CalcDataTypeBaseCode(constType);
                
                if (typeCode == -1)
                {
                    return;
                }
                
                if (this.currentDomainDesc != null)
                {
                    
                    list = ((Element)constantNode).getElementsByTagName("VALUE");
                            
                    if (list.getLength() > 0)
                    {
                        Node valueNode = list.item(0);
                        value = getTextContent(valueNode);
                    }
                    
                    int ptBase = this.GetDomainPrimitiveType(this.currentDomainDesc);                        
                    this.CanAssignToPrimitive(ptBase, typeCode, tok, value);
                }
            }
        }
    }
    
    public void CheckConstAssigment(Token tok, Node constantNode, Node type, int ordNum)
    {
         NodeList list = ((Element)constantNode).getElementsByTagName("TYPE");
         
         if (list.getLength() > 0)
         {
             Node typeNode = list.item(0);
             
             if (typeNode.getFirstChild() != null)
             {
                 String constType = typeNode.getFirstChild().getNodeName();
                 int typeCode = this.CalcDataTypeBaseCode(constType);
                 
                 if (typeCode == -1)
                 {
                     return;
                 }
                 
                 if (type != null && type.getFirstChild() != null)
                 {
                     String value = "";
                     list = ((Element)constantNode).getElementsByTagName("VALUE");
                             
                     if (list.getLength() > 0)
                     {
                         Node valueNode = list.item(0);
                         value = getTextContent(valueNode);
                     }
                     
                     String domName = type.getFirstChild().getNodeName();                     
                     
                     if ( domName == "ARRAY")
                     {
                         if (ordNum == -1)
                         {
                             String[] errs = new String[5];
                             errs[0] = Integer.toString(tok.getLine());
                             errs[1] = Integer.toString(tok.getCharPositionInLine());
                             errs[2] = this.contextName;
                             errs[3] = value;
                             errs[4] = "ARRAY";
                                 
                             this.LoadErrorDescription(errs, "CAN_NOT_ASS_CONST_TO_VALUE", tok.getLine(), tok.getCharPositionInLine());
                         }
                         else
                         {
                             String[] errs = new String[6];
                             errs[0] = Integer.toString(tok.getLine());
                             errs[1] = Integer.toString(tok.getCharPositionInLine());
                             errs[2] = this.contextName;
                             errs[3] = Integer.toString(ordNum+1);
                             errs[4] = value;
                             errs[5] = "ARRAY";
                                 
                             this.LoadErrorDescription(errs, "CAN_NOT_ASS_CONST_TO_VALUE_INIT", tok.getLine(), tok.getCharPositionInLine());
                         }
                     }
                     else
                     {                     
                         DomainDesc domDec = null;
                         
                         if (this.domains.containsKey(domName.toLowerCase(Locale.US)))
                         {
                            domDec = (DomainDesc)this.domains.get(domName.toLowerCase(Locale.US));
                         }
                         
                         if (this.userDefDomains.containsKey(domName.toLowerCase(Locale.US)))
                         {
                            domDec = (DomainDesc)this.userDefDomains.get(domName.toLowerCase(Locale.US));
                         }
                         
                         if (domDec != null)
                         {
                             int ptBase = this.GetDomainPrimitiveType(domDec);                        
                             
                             if (ptBase == -1 || SemAnalyzer.assignMatr[ptBase-1][typeCode -1] == 0)
                             {
                                 if (ordNum == -1)
                                 {
                                     String[] errs = new String[5];
                                     errs[0] = Integer.toString(tok.getLine());
                                     errs[1] = Integer.toString(tok.getCharPositionInLine());
                                     errs[2] = this.contextName;
                                     errs[3] = value;
                                     errs[4] = domName;
                                     
                                     this.LoadErrorDescription(errs, "CAN_NOT_ASS_CONST_TO_VALUE", tok.getLine(), tok.getCharPositionInLine());
                                 }
                                 else
                                 {
                                     String[] errs = new String[6];
                                     errs[0] = Integer.toString(tok.getLine());
                                     errs[1] = Integer.toString(tok.getCharPositionInLine());
                                     errs[2] = this.contextName;
                                     errs[3] = Integer.toString(ordNum+1);
                                     errs[4] = value;
                                     errs[5] = domName;
                                         
                                     this.LoadErrorDescription(errs, "CAN_NOT_ASS_CONST_TO_VALUE_INIT", tok.getLine(), tok.getCharPositionInLine());
                                 }
                             }
                         }
                     }
                 }
             }
         }
     }
         
    public void CreateVarNode()
    {
        this.curretnVarNode = doc.createElement("VAR");
        this.curretnVarsNode.appendChild(this.curretnVarNode);
        
        this.curretnVarNameNode = doc.createElement("NAME");
        this.curretnVarNode.appendChild(this.curretnVarNameNode);
    }
    
    public Node CreateNode(String name)
    {
        return doc.createElement(name);
    }
    
    public Node CreateNode(Node node, String name)
    {
        Node temp = doc.createElement(name);
        node.appendChild(temp);
        return temp;
    }
 
    public Node CreateAtt(Node node, String name, String value)
    {
        ((Element)node).setAttribute(name,value);
        return node;
    }
    
    public Node IsVariable(Token tok, boolean lhs)
    {
        String varName = tok.getText().toLowerCase(Locale.US);
        
        if (!this.args.containsKey(varName) && !this.vars.containsKey(varName))
        {
            if (!lhs && this.env_vars.containsKey(varName.toLowerCase(Locale.US)))
            {
                String domName = (String)this.env_vars.get(varName.toLowerCase(Locale.US));
                
                if (!this.used_env_vars.containsKey(varName.toLowerCase(Locale.US)))
                {
                    this.used_env_vars.put(varName.toLowerCase(Locale.US), "");
                    
                    this.GenerateDomainAsemblerCode(domName, this.currentAssFVarsNode);
                    
                    if (assemblerDoc!= null)
                    {
                        Element command = assemblerDoc.createElement("INS");
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.VAR_OC);                    
                        command.setAttribute("Name", varName);
                        currentAssFVarsNode.appendChild(command);
                    }
                }
                
                if (this.currentNode != null)
                {
                    ((Element)this.currentNode).setAttribute("OrdNum", "-100");
                    ((Element)this.currentNode).setAttribute("EnvVarName", varName);
                }
                
                Element resultNode = doc.createElement("TYPE");                
                resultNode.appendChild(doc.createElement(domName));
                return resultNode;
            }
            else
            {
                String[] errs = new String[4];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = tok.getText();
                
                this.LoadErrorDescription(errs, "UNKNOWN_VARIABLE_NAME", tok.getLine(), tok.getCharPositionInLine());
                return null;
            }
        }
        else
        {
            Node varNode = null;
            String indexVal = null;
            
            if (this.args.containsKey(varName))
            {
                varNode = (Node)this.args.get(varName);
                
                if (this.currentNode!=null)
                {
                    indexVal = (String)((Element)varNode).getAttribute("OrdNum");
                    
                    if (indexVal == null || indexVal == "")
                    {
                        indexVal = null;
                    }
                }
            }
            else
            {
                if (this.vars.containsKey(varName))
                {
                    varNode = (Node)this.vars.get(varName);
                    
                    if (varNode != null && varNode.getFirstChild() != null)
                    {
                        NodeList list = varNode.getFirstChild().getChildNodes();
                        
                        for(int p = 0; p < list.getLength(); p++)
                        {
                            if (varName.equalsIgnoreCase(list.item(p).getNodeName()))
                            {
                                indexVal = (String)((Element)list.item(p)).getAttribute("OrdNum");
                                
                                if (indexVal == null || indexVal == "")
                                {
                                    indexVal = null;
                                }
                            }
                        }
                    }
                }
            }
            
            if (indexVal != null && this.currentNode != null)
            {
                ((Element)this.currentNode).setAttribute("OrdNum", indexVal);
            }
            
            if (this.currentNode != null)
            {
                ((Element)this.currentNode).setAttribute("Name", tok.getText());
            }
            
            if (varNode != null)
            {
                NodeList list = ((Element)varNode).getElementsByTagName("TYPE");
                
                if (list.getLength() > 0)
                {
                    Node typeNode = list.item(0);
                    
                    //System.out.println("Sarzaj cvora" + typeNode.getTextContent());
                    return typeNode;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
    }
    
    public Node IsVariable(String vName)
    {
        String varName = vName.toLowerCase(Locale.US);
        
        if (!this.args.containsKey(varName) && !this.vars.containsKey(varName))
        {
            return null;
        }
        else
        {
            Node varNode = null;
            String indexVal = null;
            
            if (this.args.containsKey(varName))
            {
                varNode = (Node)this.args.get(varName);
            }
            else
            {
                if (this.vars.containsKey(varName))
                {
                    varNode = (Node)this.vars.get(varName);
                    
                    if (varNode != null && varNode.getFirstChild() != null)
                    {
                        NodeList list = varNode.getFirstChild().getChildNodes();
                        
                        for(int p = 0; p < list.getLength(); p++)
                        {
                            if (varName.equalsIgnoreCase(list.item(p).getNodeName()))
                            {
                                indexVal = (String)((Element)list.item(p)).getAttribute("OrdNum");
                                
                                if (indexVal == null || indexVal == "")
                                {
                                    indexVal = null;
                                }
                            }
                        }
                    }
                }
            }
            
            if (varNode != null)
            {
                NodeList list = ((Element)varNode).getElementsByTagName("TYPE");
                
                if (list.getLength() > 0)
                {
                    Node typeNode = list.item(0);
                    
                    return typeNode;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
    }
    
    public Node IsStructVariable(Node type, Token tok)
    {
        try 
        {
            if (type == null || type.getFirstChild() == null)
            {
                return null;
            }
            
            String domName = type.getFirstChild().getNodeName();
            boolean can = true;
            DomainDesc current = null;
            
            if (domName == "ARRAY")
            {
                can = false;
            }
            else
            {
                if (this.domains.containsKey(domName.toLowerCase(Locale.US)))
                {
                    can = false;
                }
                else
                {
                    if (!this.userDefDomains.containsKey(domName.toLowerCase(Locale.US)))
                    {
                        return null;
                    }
                    
                    int i = 1;                    
                    current = (DomainDesc)this.userDefDomains.get(domName.toLowerCase(Locale.US));
                    
                    while(i < 100)
                    {
                        if (current.getType() == DomainDesc.PRIMITIVE)
                        {
                            can = false;
                            break;
                        }
                        else
                        {
                            if (current.getType() == DomainDesc.INHERITED_PRIMITIVE)
                            {
                                can = false;
                                break;
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
                                        can = false;
                                        break;
                                    }
                                    else
                                    {
                                        can = true; //tupple, choice
                                        break;
                                    }
                                }
                            }
                        }
                        
                        i = i + 1;
                    }
                }
            }
            
            if (!can)
            {
                String[] errs = new String[4];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = domName;
                
                this.LoadErrorDescription(errs, "CAN_NOT_APPPLY_DOT_OPERATOR", tok.getLine(), tok.getCharPositionInLine());
                return null;
            }
            else
            {
                Node resultNode = doc.createElement("TYPE"); 
                resultNode.appendChild(doc.createElement(current.getName()));
                return resultNode;
            }
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    public Node ContainsField(Node type, String fieldName, Token tok)
    {
        try 
        {
            if (type == null || type.getFirstChild() == null)
            {
                return null;
            }
            
            String domName = type.getFirstChild().getNodeName();
            String fieldNameTemp = fieldName.toLowerCase(Locale.US);
            
            if (!this.userDefDomains.containsKey(domName.toLowerCase(Locale.US)))
            {
                return null;
            }
            
            DomainDesc current = (DomainDesc)this.userDefDomains.get(domName.toLowerCase(Locale.US));
                
            if (( current.getType() != DomainDesc.TUPPLE && current.getType() != DomainDesc.CHOICE))
            {
                return null;
            }
            
            AttributeDesc attDesc = null;
            
            for(int i = 0; i < current.getMembers().size(); i++)
            {
                AttributeDesc temp = (AttributeDesc)current.getMembers().get(i);
                
                if (temp.getName().toLowerCase(Locale.US).equals(fieldNameTemp))
                {
                    attDesc = temp;
                }
            }
            
            if (attDesc == null)
            {
                String[] errs = new String[5];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = domName;
                errs[4] = fieldName;
                
                this.LoadErrorDescription(errs, "DOMAIN_DOES_NOT_CONTAIN", tok.getLine(), tok.getCharPositionInLine());
                return null;
            }
            else
            {
                Node resultNode = doc.createElement("TYPE"); 
                resultNode.appendChild(doc.createElement(attDesc.getDomName()));
                
                if (this.currentNode != null)
                {
                    Node fNode = doc.createElement("field");
                    ((Element)fNode).setAttribute("OrdNum", Integer.toString(attDesc.getRbr() - 1));
                    ((Element)fNode).setAttribute("FieldName", fieldName);
                    this.currentNode.appendChild(fNode);
                }
                return resultNode;
            }
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    public Node IsArrayVariable(Node type, Token tok, int subsCount)
    {
        try 
        {
            if (type == null || type.getFirstChild() == null)
            {
                return null;
            }
            
            String domName = type.getFirstChild().getNodeName();
            
            if (domName == "ARRAY")
            {
                Node subragesNode = type.getFirstChild().getChildNodes().item(0);
                Node arrTypeNode = type.getFirstChild().getChildNodes().item(1);                
                
                if (subragesNode.getChildNodes().getLength() < subsCount )
                {
                    String[] errs = new String[3];
                    errs[0] = Integer.toString(tok.getLine());
                    errs[1] = Integer.toString(tok.getCharPositionInLine());
                    errs[2] = this.contextName;
                        
                    this.LoadErrorDescription(errs, "TO_MANY_SUBSCRIPTS", tok.getLine(), tok.getCharPositionInLine());   
                    return null;
                }
                
                if (subragesNode.getChildNodes().getLength() == subsCount )
                {   
                    return arrTypeNode;
                }
                else
                {
                    Node newTypeNode = type.cloneNode(true);
                    subragesNode = newTypeNode.getFirstChild().getChildNodes().item(0);
                    
                    for(int i = 0; i < subsCount; i++)
                    {
                        subragesNode.removeChild(subragesNode.getChildNodes().item(i));
                    }
                    
                    
                    return newTypeNode;
                }
            }
            else
            {
                DomainDesc domDesc1 = GetBaseDomain(domName);
                
                if (domDesc1 == null)
                {            
                    return null;
                }
                
                if (domDesc1.getType() == DomainDesc.SET)
                {
                    Node resultNode = doc.createElement("TYPE"); 
                    resultNode.appendChild(doc.createElement(domDesc1.getParentName()));
                    return resultNode;
                }
                else
                {
                    String[] errs = new String[4];
                    errs[0] = Integer.toString(tok.getLine());
                    errs[1] = Integer.toString(tok.getCharPositionInLine());
                    errs[2] = this.contextName;
                    errs[3] = domName;
                        
                    this.LoadErrorDescription(errs, "CAN_NOT_APPPLY_INDEX_OPERATOR", tok.getLine(), tok.getCharPositionInLine());
                    return null;
                }
            }
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    public void IsArrayIndexType(Token tok, Node typeNode)
    {
        boolean can = true;
        
        if (typeNode == null || typeNode.getFirstChild() == null)
        {
            return;
        }
        
        int ptBase = -1; 
        
        String isConstValue = ((Element)typeNode).getAttribute("IsConst");
        String domName = typeNode.getFirstChild().getNodeName();
        
        if ( isConstValue == "TRUE")
        {
            String constType = typeNode.getFirstChild().getNodeName();
            ptBase = this.CalcDataTypeBaseCode(constType);
        }
        else
        {
            ptBase = this.GetDomainPrimitiveType(typeNode); 
            
        }
        
        if (ptBase != PT_INT && ptBase != PT_DECIMAL)
        {
            can = false;
        }
        
        if (!can)
        {
            
            String[] errs = new String[4];
            errs[0] = Integer.toString(tok.getLine());
            errs[1] = Integer.toString(tok.getCharPositionInLine());
            errs[2] = this.contextName;
            errs[3] = domName;
                
            this.LoadErrorDescription(errs, "CAN_NOT_BE_USED_AS_INDEX", tok.getLine(), tok.getCharPositionInLine());
        }
    }
    
    public void CheckUpdateTobName(String tob_name, Token tok, Node updateStat)
    {
        try 
        {
            String tob_name_low = tob_name.toLowerCase(Locale.US);
            
            if (!this.tobs.containsKey(tob_name_low))
            {
                String[] errs = new String[4];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = tob_name;
                this.LoadErrorDescription(errs, "UNKOWN_COMPONENT_TYPE", tok.getLine(), tok.getCharPositionInLine());
                return;
            }
            else
            {
                currentTobDesc = (TobDesc)this.tobs.get(tob_name_low);
                ((Element)updateStat).setAttribute("TobId", currentTobDesc.tobId);
            }
        }
        catch(Exception e)
        {
        }
    }
    
    public void CheckUpdateCommName(String com_name, Token tok)
    {
        try 
        {
            String com_name_low = com_name.toLowerCase(Locale.US);
            
            if (!this.tobCommands.containsKey(com_name_low))
            {
                String[] errs = new String[4];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = com_name;
                this.LoadErrorDescription(errs, "UNKOWN_COMPONENT_TYPE_COMMAND", tok.getLine(), tok.getCharPositionInLine());
                return;
            }
        }
        catch(Exception e)
        {
        }
    }
    
    public void CheckUpdateSetPropName(Node uStat, Token tok, String att_name, String prop_name)
    {
        try 
        {
            String addVal = prop_name.toLowerCase(Locale.US);
            
            if (att_name != null)
            {
                if (!this.currentTobDesc.atst.containsKey(att_name.toLowerCase(Locale.US)))
                {
                    String[] errs = new String[4];
                    errs[0] = Integer.toString(tok.getLine());
                    errs[1] = Integer.toString(tok.getCharPositionInLine());
                    errs[2] = this.contextName;
                    errs[3] = att_name;
                    this.LoadErrorDescription(errs, "UNKOWN_COMPONENT_TYPE_ATT", tok.getLine(), tok.getCharPositionInLine());
                    return;
                }
                String att_id = ((AttributeDesc)this.currentTobDesc.atst.get(att_name.toLowerCase(Locale.US))).getId();
                addVal = att_id + "." + prop_name;
                
                if (!this.tobFieldProps.containsKey(prop_name.toLowerCase(Locale.US)))
                {
                    String[] errs = new String[4];
                    errs[0] = Integer.toString(tok.getLine());
                    errs[1] = Integer.toString(tok.getCharPositionInLine());
                    errs[2] = this.contextName;
                    errs[3] = prop_name;
                    this.LoadErrorDescription(errs, "UNKOWN_COMPONENT_TYPE_PROP", tok.getLine(), tok.getCharPositionInLine());
                }
            }
            else
            {
                if (!this.tobProps.containsKey(prop_name.toLowerCase(Locale.US)))
                {
                    String[] errs = new String[4];
                    errs[0] = Integer.toString(tok.getLine());
                    errs[1] = Integer.toString(tok.getCharPositionInLine());
                    errs[2] = this.contextName;
                    errs[3] = prop_name;
                    this.LoadErrorDescription(errs, "UNKOWN_COMPONENT_TYPE_PROP", tok.getLine(), tok.getCharPositionInLine());
                }
            }
            
            Element updateNode = (Element)uStat;
            String setList = updateNode.getAttribute("SetList");
            
            if (setList == null || setList.equals(""))
            {
                updateNode.setAttribute("SetList", addVal);
            }
            else
            {
                updateNode.setAttribute("SetList", setList + "," + addVal);
            }
            
            /*String tob_name_low = tob_name.toLowerCase(Locale.US);
            
            if (!this.tobs.containsKey(tob_name_low))
            {
                String[] errs = new String[4];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = tob_name;
                this.LoadErrorDescription(errs, "UNKOWN_COMPONENT_TYPE", tok.getLine(), tok.getCharPositionInLine());
                return;
            }
            else
            {
                currentTobDesc = (TobDesc)this.tobs.get(tob_name_low);
                ((Element)updateStat).setAttribute("TobId", currentTobDesc.tobId);
            }*/
            
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
    }
    
    public void CheckSelectStatement(String func_name, Token tok, Node tNode1, Node tNode2)
    {
        try 
        {
            String func_name_low = func_name.toLowerCase(Locale.US);
            
            if (!this.select_functions.containsKey(func_name_low))
            {
                String[] errs = new String[4];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = func_name;
                this.LoadErrorDescription(errs, "INVALID_SELECT_FUNCTION", tok.getLine(), tok.getCharPositionInLine());
                return;
            }
            
            if ( tNode1 == null || tNode1.getFirstChild() == null)
            {
                return;
            }
            
            if ( tNode2 == null || tNode2.getFirstChild() == null)
            {
                return;
            }
            
            boolean can = true;
            
            Element typeNode1 = (Element)tNode1;
            Element typeNode2 = (Element)tNode2;
            String isConstValue = typeNode2.getAttribute("IsConst");
            
            String domName = typeNode1.getFirstChild().getNodeName();
            int ptBase = this.GetDomainPrimitiveType(typeNode1); 
            
            if (ptBase == -1 || SemAnalyzer.selfuncArr[ptBase-1] == 0)
            {
                String[] errs = new String[4];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = domName;
                
                this.LoadErrorDescription(errs, "INVALID_SELECT_VAR_TYPE", tok.getLine(), tok.getCharPositionInLine());
                return;
            }
            
            String domName1 = typeNode2.getFirstChild().getNodeName();
            DomainDesc domDesc1 = null;
            
            if ( isConstValue == "TRUE")
            {
                can = false;
            }
            else
            {
               // this.CheckDomainsComp(tok, typeNode1, typeNode2, true);                
                domDesc1 = GetBaseDomain(domName1);
                
                if (domDesc1 == null)
                {            
                    can = false;
                }
                
                if (domDesc1.getType() != DomainDesc.SET)
                {
                    can = false;
                }
            }
            
            if (!can)
            {
                String[] errs = new String[4];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = domName1;
                
                this.LoadErrorDescription(errs, "INVALID_SELECT_SET_EXPECTED", tok.getLine(), tok.getCharPositionInLine());
                return;
            }
            
            if ( func_name_low.equals("avg") || func_name_low.equals("sum"))
            {
                DomainDesc domDesc2 = this.GetBaseDomain(domDesc1.getParentName());                
                ptBase = this.GetDomainPrimitiveType(domDesc2); 
                
                if (ptBase == -1 || SemAnalyzer.selfuncArr[ptBase-1] == 0)
                {
                    String[] errs = new String[4];
                    errs[0] = Integer.toString(tok.getLine());
                    errs[1] = Integer.toString(tok.getCharPositionInLine());
                    errs[2] = this.contextName;
                    errs[3] = func_name;
                    
                    this.LoadErrorDescription(errs, "INVALID_SELECT_SET_OF_NUMERIC_EXPECTED", tok.getLine(), tok.getCharPositionInLine());
                    return;
                }
            }
        }
        catch(Exception e)
        {
            return;
        }
    }
    
    public String CheckSelectStatement(String func_name, Node tNode1, Node tNode2)
    {
        try 
        {
            String func_name_low = func_name.toLowerCase(Locale.US);
            
            if (!this.select_functions.containsKey(func_name_low))
            {
                return "After select '" + func_name + "' uknown function was found.";
            }
            
            if ( tNode1 == null || tNode1.getFirstChild() == null)
            {
                return "";
            }
            
            if ( tNode2 == null || tNode2.getFirstChild() == null)
            {
                return "";
            }
            
            boolean can = true;
            
            Element typeNode1 = (Element)tNode1;
            Element typeNode2 = (Element)tNode2;
            String isConstValue = typeNode2.getAttribute("IsConst");
            
            String domName = typeNode1.getFirstChild().getNodeName();
            int ptBase = this.GetDomainPrimitiveType(typeNode1); 
            
            if (ptBase == -1 || SemAnalyzer.selfuncArr[ptBase-1] == 0)
            {
                return "Result of select function can not be stored in a variable of type " + domName;
            }
            
            String domName1 = typeNode2.getFirstChild().getNodeName();
            DomainDesc domDesc1 = null;
            
            if ( isConstValue == "TRUE")
            {
                can = false;
            }
            else
            {
               // this.CheckDomainsComp(tok, typeNode1, typeNode2, true);                
                domDesc1 = GetBaseDomain(domName1);
                
                if (domDesc1 == null)
                {            
                    can = false;
                }
                
                if (domDesc1.getType() != DomainDesc.SET)
                {
                    can = false;
                }
            }
            
            if (!can)
            {
                return "Set expeced after FROM, but found " + domName1;
            }
            
            if ( func_name_low.equals("avg") || func_name_low.equals("sum"))
            {
                DomainDesc domDesc2 = this.GetBaseDomain(domDesc1.getParentName());                
                ptBase = this.GetDomainPrimitiveType(domDesc2); 
                
                if (ptBase == -1 || SemAnalyzer.selfuncArr[ptBase-1] == 0)
                {
                    return "Function " + func_name + " can only be applied over set of numeric values";
                }
            }
            return "";
        }
        catch(Exception e)
        {
            return "";
        }
    }
    
    public void CheckVarAssigment(Token tok, Node tNode1, Node tNode2)
    {
        try 
        {
            if ( tNode1 == null || tNode1.getFirstChild() == null)
            {
                return;
            }
            
            if ( tNode2 == null || tNode2.getFirstChild() == null)
            {
                return;
            }
            
            Element typeNode1 = (Element)tNode1;
            Element typeNode2 = (Element)tNode2;
            String isConstValue = typeNode2.getAttribute("IsConst");
            
            if ( isConstValue == "TRUE")
            {
                String constType = typeNode2.getFirstChild().getNodeName();
                
                int typeCode = this.CalcDataTypeBaseCode(constType);
                
                if (typeCode == -1)
                {
                    return;
                }
                
                String domName = typeNode1.getFirstChild().getNodeName();
                int ptBase = this.GetDomainPrimitiveType(typeNode1); 
                
                if (ptBase == -1 || SemAnalyzer.assignMatr[ptBase-1][typeCode -1] == 0)
                {
                    String[] errs = new String[5];
                    errs[0] = Integer.toString(tok.getLine());
                    errs[1] = Integer.toString(tok.getCharPositionInLine());
                    errs[2] = this.contextName;
                    errs[3] = constType;
                    errs[4] = domName;
                    
                    this.LoadErrorDescription(errs, "CAN_NOT_ASS_CONST_EXPRESSION_TO_VAR", tok.getLine(), tok.getCharPositionInLine());
                }
            }
            else
            {
                this.CheckDomainsComp(tok, typeNode1, typeNode2, true);
            }
            
            if (tNode1 != null && tNode1.getParentNode() != null)
            {
                Element argNode = (Element)tNode1.getParentNode();
                String typeAtt = argNode.getAttribute("Type");
                
                if (typeAtt != null && !typeAtt.equals(""))
                {
                    if (typeAtt.toLowerCase(Locale.US).equals("in"))
                    {
                        String[] errs = new String[4];
                        errs[0] = Integer.toString(tok.getLine());
                        errs[1] = Integer.toString(tok.getCharPositionInLine());
                        errs[2] = this.contextName;
                        errs[3] = argNode.getFirstChild().getFirstChild().getNodeName();
                        
                        this.LoadErrorDescription(errs, "CAN_NOT_ASSIGN_INPUT_ARG", tok.getLine(), tok.getCharPositionInLine());
                    }
                }
            }
            
        }
        catch(Exception e)
        {
            return;
        }
    }
    
    public boolean CheckVarAssigment(Node tNode1, Node tNode2)
    {
        try 
        {
            if ( tNode1 == null || tNode1.getFirstChild() == null)
            {
                return true;
            }
            
            if ( tNode2 == null || tNode2.getFirstChild() == null)
            {
                return true;
            }
            
            Element typeNode1 = (Element)tNode1;
            Element typeNode2 = (Element)tNode2;
            String isConstValue = typeNode2.getAttribute("IsConst");
            
            if ( isConstValue == "TRUE")
            {
                String constType = typeNode2.getFirstChild().getNodeName();
                
                int typeCode = this.CalcDataTypeBaseCode(constType);
                
                if (typeCode == -1)
                {
                    return true;
                }
                
                String domName = typeNode1.getFirstChild().getNodeName();
                int ptBase = this.GetDomainPrimitiveType(typeNode1); 
                
                if (ptBase == -1 || SemAnalyzer.assignMatr[ptBase-1][typeCode -1] == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            else
            {
                return this.CheckDomainsComp(null, typeNode1, typeNode2, false);
            }
            
        }
        catch(Exception e)
        {
            return true;
        }
    }
    
    public void CheckReturnCommand(Token tok, Node tNode2)
    {
        try 
        {
            
            Node tNode1 = null;
            
            NodeList list = ((Element)this.curretnFuncNode).getElementsByTagName("RETURNS");
            
            if (list.getLength() > 0)
            {
                tNode1 = list.item(0).getFirstChild();
            }
            
            if ( tNode1 == null || tNode1.getFirstChild() == null)
            {
                return;
            }
            
            if ( tNode2 == null || tNode2.getFirstChild() == null)
            {
                return;
            }
            
            Element typeNode1 = (Element)tNode1;
            Element typeNode2 = (Element)tNode2;
            String isConstValue = typeNode2.getAttribute("IsConst");
            
            if ( isConstValue == "TRUE")
            {
                String constType = typeNode2.getFirstChild().getNodeName();
                
                int typeCode = this.CalcDataTypeBaseCode(constType);
                
                if (typeCode == -1)
                {
                    return;
                }
                
                String domName = typeNode1.getFirstChild().getNodeName();
                int ptBase = this.GetDomainPrimitiveType(typeNode1); 
                
                if (ptBase == -1 || SemAnalyzer.assignMatr[ptBase-1][typeCode -1] == 0)
                {
                    String[] errs = new String[5];
                    errs[0] = Integer.toString(tok.getLine());
                    errs[1] = Integer.toString(tok.getCharPositionInLine());
                    errs[2] = this.contextName;
                    errs[3] = constType;
                    errs[4] = domName;
                    
                    this.LoadErrorDescription(errs, "INVALID_RETURN_STATEMENT", tok.getLine(), tok.getCharPositionInLine());
                }
            }
            else
            {
                if (!this.CheckDomainsComp(tok, typeNode1, typeNode2, false))
                {
                    String domName1 = typeNode1.getFirstChild().getNodeName();
                    String domName2 = typeNode2.getFirstChild().getNodeName();
                    
                    String[] errs = new String[5];
                    errs[0] = Integer.toString(tok.getLine());
                    errs[1] = Integer.toString(tok.getCharPositionInLine());
                    errs[2] = this.contextName;
                    errs[3] = domName2;
                    errs[4] = domName1;
                    
                    this.LoadErrorDescription(errs, "INVALID_RETURN_STATEMENT", tok.getLine(), tok.getCharPositionInLine());
                }
            }
            
        }
        catch(Exception e)
        {
            return;
        }
    }
    
    public boolean CheckReturnCommand(Node tNode2)
    {
        try 
        {
            
            Node tNode1 = null;
            
            NodeList list = ((Element)this.curretnFuncNode).getElementsByTagName("RETURNS");
            
            if (list.getLength() > 0)
            {
                tNode1 = list.item(0).getFirstChild();
            }
            
            if ( tNode1 == null || tNode1.getFirstChild() == null)
            {
                return true;
            }
            
            if ( tNode2 == null || tNode2.getFirstChild() == null)
            {
                return true;
            }
            
            Element typeNode1 = (Element)tNode1;
            Element typeNode2 = (Element)tNode2;
            String isConstValue = typeNode2.getAttribute("IsConst");
            
            if ( isConstValue == "TRUE")
            {
                String constType = typeNode2.getFirstChild().getNodeName();
                
                int typeCode = this.CalcDataTypeBaseCode(constType);
                
                if (typeCode == -1)
                {
                    return true;
                }
                
                String domName = typeNode1.getFirstChild().getNodeName();
                int ptBase = this.GetDomainPrimitiveType(typeNode1); 
                
                if (ptBase == -1 || SemAnalyzer.assignMatr[ptBase-1][typeCode -1] == 0)
                {
                    return false;
                }
            }
            else
            {
                return this.CheckDomainsComp(null, typeNode1, typeNode2, false);
            }
            
            return true;
        }
        catch(Exception e)
        {
            return true;
        }
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
    
    public boolean CheckDomainsComp(Token tok, Element typeNode1, Element typeNode2, boolean reportError)
    {
        try 
        {
            String domName1 = typeNode1.getFirstChild().getNodeName();
            String domName2 = typeNode2.getFirstChild().getNodeName();
            
            if (domName1 == "ARRAY")
            {
                if (! (domName2 == "ARRAY") )  
                {
                    if (reportError)
                    {
                        String[] errs = new String[5];
                        errs[0] = Integer.toString(tok.getLine());
                        errs[1] = Integer.toString(tok.getCharPositionInLine());
                        errs[2] = this.contextName;
                        errs[3] = domName2;
                        errs[4] = domName1;
                        
                        this.LoadErrorDescription(errs, "CAN_NOT_ASS_CONST_EXPRESSION_TO_VAR", tok.getLine(), tok.getCharPositionInLine());
                    }
                    return false;
                }
                else
                {
                    if(!this.CheckArraysComp(typeNode1, typeNode2))
                    {
                        if (reportError)
                        {
                            String[] errs = new String[3];
                            errs[0] = Integer.toString(tok.getLine());
                            errs[1] = Integer.toString(tok.getCharPositionInLine());
                            errs[2] = this.contextName;
                            
                            this.LoadErrorDescription(errs, "ARRRAY_NOT_COMPATIBILE", tok.getLine(), tok.getCharPositionInLine());
                        }
                        return false;
                    }
                    return true;
                }
            }
            else
            {
                if (domName2 == "ARRAY")  
                {
                    if (reportError)
                    {
                        String[] errs = new String[5];
                        errs[0] = Integer.toString(tok.getLine());
                        errs[1] = Integer.toString(tok.getCharPositionInLine());
                        errs[2] = this.contextName;
                        errs[3] = domName2;
                        errs[4] = domName1;
                        
                        this.LoadErrorDescription(errs, "CAN_NOT_ASS_CONST_EXPRESSION_TO_VAR", tok.getLine(), tok.getCharPositionInLine());
                    }
                    return false;
                }
                
                DomainDesc domDesc1 = GetBaseDomain(domName1);
                DomainDesc domDesc2 = GetBaseDomain(domName2);
                
                if (domDesc1 == null || domDesc2 == null)
                {
                    return false;
                }
                
                boolean comp = false;
                
                if (domDesc1.getType() == DomainDesc.SET)
                {
                    if (domDesc2.getType() != DomainDesc.SET)
                    {
                        if (reportError)
                        {
                            String[] errs = new String[5];
                            errs[0] = Integer.toString(tok.getLine());
                            errs[1] = Integer.toString(tok.getCharPositionInLine());
                            errs[2] = this.contextName;
                            errs[3] = domName2;
                            errs[4] = domName1;
                            
                            this.LoadErrorDescription(errs, "CAN_NOT_ASS_CONST_EXPRESSION_TO_VAR", tok.getLine(), tok.getCharPositionInLine());
                        }
                        return false;
                    }
                    else
                    {
                        Element tempTypeNode1 = doc.createElement("TYPE");
                        tempTypeNode1.appendChild(doc.createElement(domDesc1.getParentName()));
                        
                        Element tempTypeNode2 = doc.createElement("TYPE");
                        tempTypeNode2.appendChild(doc.createElement(domDesc2.getParentName()));
                        
                        return this.CheckDomainsComp(tok, tempTypeNode1, tempTypeNode2, reportError);
                    }
                }
                else
                {
                    if (domDesc1.getType() == DomainDesc.PRIMITIVE)
                    {
                        if (domDesc2.getType() == DomainDesc.PRIMITIVE)
                        {
                            if (SemAnalyzer.assignMatr[domDesc1.getPrimitiveDomainType()-1][domDesc2.getPrimitiveDomainType() -1] != 0)
                            {
                                comp = true;
                            }
                        }
                    }
                    else
                    {
                        if (domDesc2.getType() != DomainDesc.PRIMITIVE)
                        {
                            if (domDesc1.getId() == domDesc2.getId())
                            {
                                comp = true;
                            }
                        }
                    }
                }
                
                if (!comp)
                {
                    if (reportError)
                    {
                        String[] errs = new String[5];
                        errs[0] = Integer.toString(tok.getLine());
                        errs[1] = Integer.toString(tok.getCharPositionInLine());
                        errs[2] = this.contextName;
                        errs[3] = domName2;
                        errs[4] = domName1;
                        
                        this.LoadErrorDescription(errs, "CAN_NOT_ASS_CONST_EXPRESSION_TO_VAR", tok.getLine(), tok.getCharPositionInLine());
                    }
                    
                    return false;
                }
                
                return true;
            }
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public boolean CheckArraysComp(Element typeNode1, Element typeNode2)
    {
        try 
        {
            Node subragesNode1 = typeNode1.getFirstChild().getChildNodes().item(0);
            Node arrTypeNode1 = typeNode1.getFirstChild().getChildNodes().item(1); 
            
            Node subragesNode2 = typeNode2.getFirstChild().getChildNodes().item(0);
            Node arrTypeNode2 = typeNode2.getFirstChild().getChildNodes().item(1); 
            
            if (subragesNode1.getChildNodes().getLength() != subragesNode2.getChildNodes().getLength())
            {
                return false;
            }
            
            for(int i = 0; i < subragesNode1.getChildNodes().getLength(); i++)
            {
                Node sub1 = subragesNode1.getChildNodes().item(i);
                Node sub2 = subragesNode2.getChildNodes().item(i);
                
                if (sub1.getChildNodes().getLength() != 2 || sub2.getChildNodes().getLength() != 2)
                {
                    return false;
                }
                
                Element beg1 = (Element)sub1.getChildNodes().item(0);
                Element beg2 = (Element)sub2.getChildNodes().item(0);
                
                if (! getTextContent(beg1).equals(getTextContent(beg2)))
                {
                    return false;
                }
                
                Element end1 = (Element)sub1.getChildNodes().item(1);
                Element end2 = (Element)sub2.getChildNodes().item(1);
                
                if (! getTextContent(end1).equals(getTextContent(end2)))
                {
                    return false;
                }
                
                return this.CheckDomainsComp(null, (Element)arrTypeNode1, (Element)arrTypeNode2, false);
            }
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public Node CheckOperator(Node tNode1, Node tNode2, Token tok)
    {
        try 
        {
            if ( tNode1 == null || tNode1.getFirstChild() == null)
            {
                return null;
            }
            
            if ( tNode2 == null || tNode2.getFirstChild() == null)
            {
                return null;
            }
            
            String operator = tok.getText();
            String opLower = operator.toLowerCase(Locale.US);
            
            Element typeNode1 = (Element)tNode1;
            Element typeNode2 = (Element)tNode2;
            String isConstValue1 = typeNode1.getAttribute("IsConst");
            String isConstValue2 = typeNode2.getAttribute("IsConst");
            String domName1 = typeNode1.getFirstChild().getNodeName();
            String domName2 = typeNode2.getFirstChild().getNodeName();
            
            int typeCode1 = -1;
            int typeCode2 = -2;
            
            if ( isConstValue1 == "TRUE")
            {
                String constType = typeNode1.getFirstChild().getNodeName();                
                typeCode1 = this.CalcDataTypeBaseCode(constType);
            }
            else
            {
                typeCode1 = this.GetDomainPrimitiveType(typeNode1); 
            }
            
            if ( isConstValue2 == "TRUE")
            {
                String constType = typeNode2.getFirstChild().getNodeName();                
                typeCode2 = this.CalcDataTypeBaseCode(constType);
            }
            else
            {
                typeCode2 = this.GetDomainPrimitiveType(typeNode2); 
            }
            
            boolean can = true;
            int result = -1;
            
            if (opLower.equals("==") || opLower.equals("!="))
            {
                if ( isConstValue1 == "TRUE" || isConstValue2 == "TRUE")
                {
                    if (typeCode1 == -1 || typeCode2 == -1)
                    {
                        can = false;
                    }
                    else
                    {
                        if (SemAnalyzer.compMatr[ typeCode1 - 1 ][ typeCode2 - 1 ] == 0)
                        {
                            can = false;
                        }
                    }
                }
                else
                {
                    if (this.CheckDomainsComp(tok, typeNode1, typeNode2, false))
                    {
                        can = true;
                        
                        if (domName1.equalsIgnoreCase("ITERATOR"))
                        {
                            can = false;
                        }
                    }
                    else
                    {
                        can = false;
                    }
                }
                
                if (can)
                {
                    result = 6;
                }
            }
            else
            {
                if (typeCode1 == -1 || typeCode2 == -1)
                {
                    can = false;
                }
                else
                {
                    if (operator.equals("+"))
                    {
                        result = SemAnalyzer.addMatr[ typeCode1 - 1 ][ typeCode2 - 1 ];
                    }
                    else
                    {
                        if (operator.equals("-"))
                        {
                            result = SemAnalyzer.minusMatr[ typeCode1 - 1 ][ typeCode2 - 1 ];
                        }
                        else
                        {
                            if (operator.equals("*") || operator.equals("/"))
                            {
                                result = SemAnalyzer.mulMatr[ typeCode1 - 1 ][ typeCode2 - 1 ];
                            }
                            else
                            {
                                if (opLower.equals("=>") || opLower.equals("and") || opLower.equals("or") ||opLower.equals("xor"))
                                {
                                    result = SemAnalyzer.boolMatr[ typeCode1 - 1 ][ typeCode2 - 1 ];
                                }
                                else
                                {
                                    if (opLower.equals("<") ||opLower.equals("<=") || opLower.equals(">") ||opLower.equals(">="))
                                    {
                                        result = SemAnalyzer.compMatr[ typeCode1 - 1 ][ typeCode2 - 1 ];
                                    }
                                    else
                                    {
                                        if (operator.equals("%"))
                                        {
                                            result = SemAnalyzer.modMatr[ typeCode1 - 1 ][ typeCode2 - 1 ];
                                        }
                                        else
                                        {
                                            if (operator.equals("||"))
                                            {
                                                result = SemAnalyzer.concatMatr[ typeCode1 - 1 ][ typeCode2 - 1 ];
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    if (result == 0)
                    {
                        can = false;
                    }
                }
            }
            
            if (!can)
            {
                String[] errs = new String[6];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = operator;
                errs[4] = domName1;
                errs[5] = domName2;
                
                this.LoadErrorDescription(errs, "CAN_NOT_APPLY_OPERATOR", tok.getLine(), tok.getCharPositionInLine());
                return null;
            }
            
            Element resultNode = doc.createElement("TYPE");
            
            if (opLower.equals("==") || opLower.equals("!=") || opLower.equals("<") ||opLower.equals("<=") || opLower.equals(">") ||opLower.equals(">="))
            {
                resultNode.appendChild(doc.createElement(SemAnalyzer.ptNames[result]));
                resultNode.setAttribute("IsConst", "TRUE");
            }
            else
            {
                if (isConstValue1 == "TRUE" && isConstValue2 == "TRUE")
                {
                    resultNode.appendChild(doc.createElement(SemAnalyzer.ptNames[result]));
                    resultNode.setAttribute("IsConst", "TRUE");
                }
                
                if (isConstValue1 != "TRUE")
                {
                    if (isConstValue2 == "TRUE")
                    {
                        resultNode.appendChild(doc.createElement(domName1));
                    }
                    else
                    {
                        if (result == typeCode1)
                        {
                            resultNode.appendChild(doc.createElement(domName1));
                        }
                        else
                        {
                            resultNode.appendChild(doc.createElement(domName2));
                        }
                    }
                }
                else
                {
                    resultNode.appendChild(doc.createElement(domName2));
                }
            }
            
            return resultNode;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    public Node CheckSetOperator(Node tNode1, Node tNode2, Token tok)
    {
        try 
        {
            if ( tNode1 == null || tNode1.getFirstChild() == null)
            {
                return null;
            }
            
            if ( tNode2 == null || tNode2.getFirstChild() == null)
            {
                return null;
            }
            
            String operator = tok.getText();
            String opLower = operator.toLowerCase(Locale.US);
            
            Element typeNode1 = (Element)tNode1;
            Element typeNode2 = (Element)tNode2;
            String isConstValue1 = typeNode1.getAttribute("IsConst");
            String isConstValue2 = typeNode2.getAttribute("IsConst");
            String domName1 = typeNode1.getFirstChild().getNodeName();
            String domName2 = typeNode2.getFirstChild().getNodeName();
            DomainDesc domDesc1 = null;
            DomainDesc domDesc2 = null;
            
            int typeCode1 = -1;
            int typeCode2 = -1;
            
            if ( isConstValue1 == "TRUE")
            {
                domDesc1 = null;
            }
            else
            {
                domDesc1 = this.GetBaseDomain(domName1);
            }
            
            if ( isConstValue2 == "TRUE")
            {
                domDesc2 = null;
            }
            else
            {
                domDesc2 = this.GetBaseDomain(domName2);
            }
            
            boolean can = true;
            int result = -1;
            String resultDomName = "";
            
            if ((domDesc1 == null && domDesc2 == null))
            {
                can = false;
            }
            else
            {
                if (domDesc1 != null && domDesc1.getType() == DomainDesc.SET)
                {  
                    resultDomName = domName1;
                    
                    Element tempTypeNode1 = doc.createElement("TYPE");
                    tempTypeNode1.appendChild(doc.createElement(domDesc1.getParentName()));
                    
                    if ( isConstValue2 == "TRUE")
                    {
                        String constType2 = typeNode2.getFirstChild().getNodeName();                
                        typeCode2 = this.CalcDataTypeBaseCode(constType2);
                        
                        typeCode1 = this.GetDomainPrimitiveType(tempTypeNode1);
                        
                        if (typeCode1 == -1 || typeCode2 == -1)
                        {
                            can = false;
                        }
                        else
                        {
                            if ( SemAnalyzer.compMatr[typeCode1 - 1][typeCode2 - 1] == 0)
                            {
                                can = false;
                            }
                        }
                    }
                    else
                    {
                        if (domDesc2 == null)
                        {
                            can = false;
                        }
                        else
                        {
                            if (domDesc2.getType() == DomainDesc.SET)
                            {
                                Element tempTypeNode2 = doc.createElement("TYPE");
                                tempTypeNode2.appendChild(doc.createElement(domDesc2.getParentName()));
                                
                                can = this.CheckDomainsComp(tok, tempTypeNode1, tempTypeNode2, false);
                            }
                            else
                            {
                                can = this.CheckDomainsComp(tok, tempTypeNode1, typeNode2, false);
                            }
                        }
                            
                    }
                }
                else
                {
                    if (domDesc2 != null && domDesc2.getType() == DomainDesc.SET)
                    {
                        resultDomName = domName2;
                        
                        Element tempTypeNode2 = doc.createElement("TYPE");
                        tempTypeNode2.appendChild(doc.createElement(domDesc2.getParentName()));
                        
                        if ( isConstValue1 == "TRUE")
                        {
                            String constType1 = typeNode1.getFirstChild().getNodeName();                
                            typeCode1 = this.CalcDataTypeBaseCode(constType1);
                            
                            typeCode2 = this.GetDomainPrimitiveType(tempTypeNode2);
                            
                            if (typeCode1 == -1 || typeCode2 == -1)
                            {
                                can = false;
                            }
                            else
                            {
                                if ( SemAnalyzer.compMatr[typeCode1 - 1][typeCode2 - 1] == 0)
                                {
                                    can = false;
                                }
                            }
                        }
                        else
                        {
                            if (domDesc1 == null)
                            {
                                can = false;
                            }
                            else
                            {
                                if (domDesc1.getType() == DomainDesc.SET)
                                {
                                    Element tempTypeNode1 = doc.createElement("TYPE");
                                    tempTypeNode1.appendChild(doc.createElement(domDesc1.getParentName()));
                                    
                                    can = this.CheckDomainsComp(tok, tempTypeNode1, tempTypeNode2, false);
                                }
                                else
                                {
                                    can = this.CheckDomainsComp(tok, typeNode1, tempTypeNode2, false);
                                }
                            }
                                
                        }   
                    }
                    else
                    {
                        can = false;
                    }
                }
            }
            
            if (!can)
            {
                String[] errs = new String[6];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = operator;
                errs[4] = domName1;
                errs[5] = domName2;
                
                this.LoadErrorDescription(errs, "CAN_NOT_APPLY_OPERATOR", tok.getLine(), tok.getCharPositionInLine());
                return null;
            }
            
            Element resultNode = doc.createElement("TYPE");
            resultNode.appendChild(doc.createElement(resultDomName));
            
            return resultNode;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    public void CheckInSet(Node tNode1, Node tNode2, Token tok, Node eNode)
    {
        try 
        {
            if ( tNode1 == null || tNode1.getFirstChild() == null)
            {
                return;
            }
            
            if ( tNode2 == null || tNode2.getFirstChild() == null)
            {
                return;
            }
            
            if( eNode != null )
            {
                ((Element)eNode).setAttribute("IsOverSet", "True");
            }
            String operator = tok.getText();
            String opLower = operator.toLowerCase(Locale.US);
            
            Element typeNode1 = (Element)tNode1;
            Element typeNode2 = (Element)tNode2;
            String isConstValue1 = typeNode1.getAttribute("IsConst");
            String isConstValue2 = typeNode2.getAttribute("IsConst");
            String domName1 = typeNode1.getFirstChild().getNodeName();
            String domName2 = typeNode2.getFirstChild().getNodeName();
            DomainDesc domDesc1 = null;
            DomainDesc domDesc2 = null;
            
            int typeCode1 = -1;
            int typeCode2 = -1;
            
            if ( isConstValue1 == "TRUE")
            {
                domDesc1 = null;
            }
            else
            {
                domDesc1 = this.GetBaseDomain(domName1);
            }
            
            if ( isConstValue2 == "TRUE")
            {
                domDesc2 = null;
            }
            else
            {
                domDesc2 = this.GetBaseDomain(domName2);
            }
            
            boolean can = true;
            int result = -1;
            String resultDomName = "";
            
            if (domDesc2 == null || domDesc2.getType() != DomainDesc.SET)
            {
                can = false;
            }
            else
            {
                resultDomName = domName2;
                    
                Element tempTypeNode2 = doc.createElement("TYPE");
                tempTypeNode2.appendChild(doc.createElement(domDesc2.getParentName()));
                
                if ( isConstValue1 == "TRUE")
                {
                    String constType1 = typeNode1.getFirstChild().getNodeName();                
                    typeCode1 = this.CalcDataTypeBaseCode(constType1);
                    
                    typeCode2 = this.GetDomainPrimitiveType(tempTypeNode2);
                    
                    if (typeCode1 == -1 || typeCode2 == -1)
                    {
                        can = false;
                    }
                    else
                    {
                        if ( SemAnalyzer.compMatr[typeCode1 - 1][typeCode2 - 1] == 0)
                        {
                            can = false;
                        }
                    }
                }
                else
                {
                    if (domDesc1 == null)
                    {
                        can = false;
                    }
                    else
                    {
                        can = this.CheckDomainsComp(tok, typeNode1, tempTypeNode2, false);
                    }
                }
            }
            
            if (!can)
            {
                String[] errs = new String[6];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = operator;
                errs[4] = domName1;
                errs[5] = domName2;
                
                this.LoadErrorDescription(errs, "CAN_NOT_APPLY_OPERATOR", tok.getLine(), tok.getCharPositionInLine());
                return;
            }
            
            Element resultNode = doc.createElement("TYPE");
            resultNode.appendChild(doc.createElement(resultDomName));
            
            return;
        }
        catch(Exception e)
        {
            return;
        }
    }
    
    public Node CheckUnaryOperator(Node tNode1, Token tok)
    {
        try 
        {
            if ( tNode1 == null || tNode1.getFirstChild() == null)
            {
                return null;
            }
            
            if ( tok == null)
            {
                return tNode1;    
            }
            
            String operator = tok.getText();
            String opLower = operator.toLowerCase(Locale.US);
            
            Element typeNode1 = (Element)tNode1;
            String isConstValue1 = typeNode1.getAttribute("IsConst");
            String domName1 = typeNode1.getFirstChild().getNodeName();
            
            int typeCode1 = -1;
            
            if ( isConstValue1 == "TRUE")
            {
                String constType = typeNode1.getFirstChild().getNodeName();                
                typeCode1 = this.CalcDataTypeBaseCode(constType);
            }
            else
            {
                typeCode1 = this.GetDomainPrimitiveType(typeNode1); 
            }
            
            
            boolean can = true;
            int result = -1;
            
            if (typeCode1 == -1 )
            {
                can = false;
            }
            else
            {
                if (opLower.equals("not"))
                {
                    result = SemAnalyzer.notArr[ typeCode1 - 1 ];
                }
                else
                {
                    if (opLower.equals("like"))
                    {
                        result = SemAnalyzer.likeMinusArr[ typeCode1 - 1 ];
                    }
                    else
                    {
                        if (opLower.equals("-"))
                        {
                            result = SemAnalyzer.unMinusArr[ typeCode1 - 1 ];
                        }
                        else
                        {
                            if (opLower.equals("in"))
                            {
                                result = SemAnalyzer.PT_BOOL;
                            }    
                        }
                    }
                }
                
                if (result == 0)
                {
                    can = false;
                }
            }
            
            if (!can)
            {
                String[] errs = new String[5];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = operator;
                errs[4] = domName1;
                
                this.LoadErrorDescription(errs, "CAN_NOT_APPLY_UNARY_OPERATOR", tok.getLine(), tok.getCharPositionInLine());
                return null;
            }
            
            Element resultNode = doc.createElement("TYPE");
            
            if (opLower.equals("like") || opLower.equals("in"))
            {
                resultNode.appendChild(doc.createElement(SemAnalyzer.ptNames[result]));
                resultNode.setAttribute("IsConst", "TRUE");
            }
            else
            {
                if (isConstValue1 == "TRUE")
                {
                    resultNode.appendChild(doc.createElement(SemAnalyzer.ptNames[result]));
                    resultNode.setAttribute("IsConst", "TRUE");
                }
                else
                {
                    resultNode.appendChild(doc.createElement(domName1));
                }
            }
            return resultNode;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    public void CheckInOperator(Node tNode1, Node tNode2, Token tok)
    {
        boolean can = this.CheckExprComp(tNode1, tNode2);
        
        if (!can)
        {
            //report error
             String[] errs = new String[5];
             errs[0] = Integer.toString(tok.getLine());
             errs[1] = Integer.toString(tok.getCharPositionInLine());
             errs[2] = this.contextName;
             errs[3] = tNode1.getFirstChild().getNodeName();
             errs[4] = tNode2.getFirstChild().getNodeName();
             
            this.LoadErrorDescription(errs, "ERROR_IN_OPERATOR", tok.getLine(), tok.getCharPositionInLine());
            return;
        }
    }
    
    public boolean CheckExprComp(Node tNode1, Node tNode2)
    {
        try 
        {
            if ( tNode1 == null || tNode1.getFirstChild() == null)
            {
                return true;
            }
            
            if ( tNode2 == null || tNode2.getFirstChild() == null)
            {
                return true;
            }
            
            Element typeNode1 = (Element)tNode1;
            Element typeNode2 = (Element)tNode2;
            
            boolean can = true;
            
            String isConstValue1 = typeNode1.getAttribute("IsConst");
            String isConstValue2 = typeNode2.getAttribute("IsConst");
            
            if ( isConstValue1 == "TRUE")
            {
                //const sa lijeve 
                String constType1 = typeNode1.getFirstChild().getNodeName();
                int typeCode1 = this.CalcDataTypeBaseCode(constType1);
                
                if (typeCode1 == -1)
                {
                    return false;
                }
                
                if ( isConstValue2 == "TRUE")
                {
                    //const i sa desne
                    String constType2 = typeNode2.getFirstChild().getNodeName();                    
                    int typeCode2 = this.CalcDataTypeBaseCode(constType2);
                    
                    if (typeCode2 == -1)
                    {
                        return false;
                    }
                    
                    if (SemAnalyzer.assignMatr[typeCode1 -1][ typeCode2 -1 ] == 0)
                    {
                        can = false;  
                    }
                }
                else
                {
                    //domen sa desne 
                    int ptBase = this.GetDomainPrimitiveType(typeNode2); 
                    
                    if (ptBase == -1 || SemAnalyzer.assignMatr[typeCode1 -1][ptBase-1] == 0)
                    {
                        can = false;
                    }
                }
            }
            else
            {
                if ( isConstValue2 == "TRUE")
                {
                    //domen sa lijeve konstanta sa desne 
                    String constType2 = typeNode2.getFirstChild().getNodeName();                    
                    int typeCode2 = this.CalcDataTypeBaseCode(constType2);
                    
                    if (typeCode2 == -1)
                    {
                        return false;
                    }
                    
                    int ptBase = this.GetDomainPrimitiveType(typeNode1); 
                    
                    if (ptBase == -1 || SemAnalyzer.assignMatr[ptBase-1][typeCode2 -1] == 0)
                    {
                        can = false;
                    }
                }
                else
                {
                    //domen sa lijeve i sa desne 
                    can = this.CheckDomainsComp(null, typeNode1, typeNode2, false);    
                }
            }
            
            return can;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public Node CalcReturnType(String fName, ArrayList paramNodes, Node currResult)
    {
        if (fName == null)
        {
            return currResult;
        }
        
        if (!fName.equalsIgnoreCase("get_item"))
        {
            return currResult;
        }
        
        if (paramNodes == null || paramNodes.size() < 2)
        {
            return currResult;
        }
        
        String domName1 = ((Element)paramNodes.get(1)).getFirstChild().getNodeName();
        DomainDesc domDesc1 = GetBaseDomain(domName1);
        
        if (domDesc1 == null)
        {            
            return currResult;
        }
        
        if (domDesc1.getType() != DomainDesc.SET)
        {
            Element type = doc.createElement("TYPE");
            type.appendChild(doc.createElement(domDesc1.getParentName()));
            return type;
        }
        
        return currResult;
    }
    
    public Node CheckFunctionName(Token tok, String fName)
    {
        String funcName = fName.toLowerCase(Locale.US);
        Node type = null;
        String funcType = "";
        
        if (!this.functions.containsKey(funcName))
        {
            if (!this.predefinedFunc.containsKey(funcName))
            {
                String[] errs = new String[4];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = fName;
                
                this.LoadErrorDescription(errs, "UNKNOWN_FUNC", tok.getLine(), tok.getCharPositionInLine());
                this.currFuncDesc = null;
            }
            else
            {
                funcType = BUILT_IN_FUNCTION;
                currFuncDesc = (FunctionDesc)predefinedFunc.get(funcName);
                
                type = doc.createElement("TYPE");
                type.appendChild(doc.createElement(currFuncDesc.getDomName()));
                ((Element)type).setAttribute("IsConst", "TRUE");
            }
        }
        else
        {
            currFuncDesc = (FunctionDesc)functions.get(funcName);
            funcType = USER_DEF_FUNCTION;
            
            type = doc.createElement("TYPE");
            type.appendChild(doc.createElement(currFuncDesc.getDomName()));
        }
        
        if (this.currentNode != null)
        {
            this.currentFuncNode = doc.createElement("func_call");
            //this.currentFuncNode.appendChild(doc.createElement("Params"));            
            this.currentNode.appendChild(this.currentFuncNode);
            ((Element)this.currentFuncNode).setAttribute("Name", funcName);
            ((Element)this.currentFuncNode).setAttribute("Type", funcType);
            
            if (funcType == BUILT_IN_FUNCTION)
            {
                ((Element)this.currentFuncNode).setAttribute("OrdNum", currFuncDesc.getCode());
            }
        }
        return type;
    }
    
    public void CheckFuncParam(Node typeNode, Token tok, FunctionDesc fd, int ordNum, Node funcNode)
    {
        if (typeNode == null || typeNode.getFirstChild() == null)
        {
            return;
        }
        
        if (fd == null)
        {
            return;
        }
        
        ParamDesc paramDesc = null;
        
        for(int i = 0; i < fd.getParams().size(); i++)
        {
            ParamDesc pd = (ParamDesc)fd.getParams().get(i);
            
            if (pd.getSeqNumber() == ordNum)
            {
                paramDesc = pd;
                break;
            }
        }
        
        if (paramDesc == null)
        {
            return;
        }
        
        if(funcNode != null)
        {
            Element paramNode = (Element)funcNode.getLastChild();
            
            if (paramDesc.getType() == ParamDesc.INOUT)
            {
                paramNode.setAttribute("iotype", "inout");
            }
            else
            {
                if (paramDesc.getType() == ParamDesc.OUT)
                {
                    paramNode.setAttribute("iotype", "out");
                }
                else
                {
                    paramNode.setAttribute("iotype", "in");
                }
            }            
        }
        
        if (paramDesc.getDomName() == null || paramDesc.getDomName() == "" || paramDesc.getDomName().equalsIgnoreCase("generic"))
        {
            return;
        }
        
        if (paramDesc.getDomName().equalsIgnoreCase("generic_set"))
        {
            String domName1 = typeNode.getFirstChild().getNodeName();
            DomainDesc domDesc1 = GetBaseDomain(domName1);
            
            boolean error = false;
            
            if (domDesc1 == null)
            {            
                error = true;

            }
            
            if (domDesc1.getType() != DomainDesc.SET)
            {
                error = true;
            }
            
            if (error)
            {
                String[] errs = new String[6];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = Integer.toString(ordNum);
                errs[4] = paramDesc.getDomName();
                errs[5] = typeNode.getFirstChild().getNodeName();
                
                this.LoadErrorDescription(errs, "FUNC_PARAM_DT_MISMATCH", tok.getLine(), tok.getCharPositionInLine());
            }
            return;
        }
        
        Node tempTypeNode = doc.createElement("TYPE");
        tempTypeNode.appendChild(doc.createElement(paramDesc.getDomName()));
        
        if(fd.getIsBuilIn())
        {
            ((Element)tempTypeNode).setAttribute("IsConst", "TRUE");
        }
        
        boolean can = this.CheckExprComp(tempTypeNode, typeNode);
        
        if(!can)
        {
            String[] errs = new String[6];
            errs[0] = Integer.toString(tok.getLine());
            errs[1] = Integer.toString(tok.getCharPositionInLine());
            errs[2] = this.contextName;
            errs[3] = Integer.toString(ordNum);
            errs[4] = paramDesc.getDomName();
            errs[5] = typeNode.getFirstChild().getNodeName();
            
            this.LoadErrorDescription(errs, "FUNC_PARAM_DT_MISMATCH", tok.getLine(), tok.getCharPositionInLine());
            return;
        }
        
        if (paramDesc.getType() == ParamDesc.INOUT || paramDesc.getType() == ParamDesc.OUT)
        {
            if (((Element)typeNode).getAttribute("IsVar") != "TRUE")
            {
                String[] errs = new String[4];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = Integer.toString(ordNum);
                
                this.LoadErrorDescription(errs, "FUNC_BAD_PARAM_OUT_TYPE", tok.getLine(), tok.getCharPositionInLine());
                return;
            }
        }
    }
    
    public void CheckFuncParamCount(Token tok, FunctionDesc fd, int ordNum)
    {
        if (fd == null)
        {
            return;
        }
        
        if(ordNum -1 != fd.getParams().size())
        {
            String[] errs = new String[6];
            errs[0] = Integer.toString(tok.getLine());
            errs[1] = Integer.toString(tok.getCharPositionInLine());
            errs[2] = this.contextName;
            errs[3] = fd.getName();
            errs[4] = Integer.toString(fd.getParams().size());
            errs[5] = Integer.toString(ordNum-1);
            
            this.LoadErrorDescription(errs, "FUNC_BAD_PARAM_NUM", tok.getLine(), tok.getCharPositionInLine());
        }
    }
    
    public void CheckCond(Node typeNode, Token tok, int option)
    {
        if (typeNode == null || typeNode.getFirstChild() == null)
        {
            return;
        }
        
        int ptBase = -1; 
        
        String isConstValue = ((Element)typeNode).getAttribute("IsConst");
        
        if ( isConstValue == "TRUE")
        {
            String constType = typeNode.getFirstChild().getNodeName();
            ptBase = this.CalcDataTypeBaseCode(constType);
        }
        else
        {
            ptBase = this.GetDomainPrimitiveType(typeNode); 
        }
        
        if (ptBase != PT_BOOL)
        {
            String[] errs = new String[4];
            errs[0] = Integer.toString(tok.getLine());
            errs[1] = Integer.toString(tok.getCharPositionInLine());
            errs[2] = this.contextName;
            errs[3] = typeNode.getFirstChild().getNodeName();
            
            if (option==0)
            {
                this.LoadErrorDescription(errs, "INVALID_IF_COND", tok.getLine(), tok.getCharPositionInLine());
            }
            else
            {
                if (option==1)
                {
                    this.LoadErrorDescription(errs, "INVALID_WHILE_COND", tok.getLine(), tok.getCharPositionInLine());
                }
                else
                {
                    this.LoadErrorDescription(errs, "INVALID_REPEAT_COND", tok.getLine(), tok.getCharPositionInLine());
                }
            }
        }
    }
    
    public boolean CheckCond(Node typeNode)
    {
        if (typeNode == null || typeNode.getFirstChild() == null)
        {
            return true;
        }
        
        int ptBase = -1; 
        
        String isConstValue = ((Element)typeNode).getAttribute("IsConst");
        
        if ( isConstValue == "TRUE")
        {
            String constType = typeNode.getFirstChild().getNodeName();
            ptBase = this.CalcDataTypeBaseCode(constType);
        }
        else
        {
            ptBase = this.GetDomainPrimitiveType(typeNode); 
        }
        
        if (ptBase != PT_BOOL)
        {
            return false;
        }
        
        return true;
    }
    
    public void CheckForVar(String varName, Token tok)
    {
        Node temp = doc.createElement("var");
        this.currentNode.appendChild(temp);
        Node oldCurrNode = this.currentNode;
        this.currentNode = temp;
        
        Node typeNode = this.IsVariable(tok, true);
        int ptBase = -1; 
        this.currentNode = oldCurrNode;
        
        if (typeNode != null)
        {
            ptBase = this.GetDomainPrimitiveType(typeNode); 
            
            if (ptBase != PT_INT)
            {
                String[] errs = new String[5];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = varName;
                errs[4] = typeNode.getFirstChild().getNodeName();
                
                this.LoadErrorDescription(errs, "INVALID_FOR_VAR", tok.getLine(), tok.getCharPositionInLine());
            }
        }
    }
    
    public void CheckIsIteratorName(String varName, Token tok, Node fNode)
    {
        /*Node temp = doc.createElement("var");
        this.currentNode.appendChild(temp);*/
        Node oldCurrNode = this.currentNode;
        this.currentNode = fNode;
        
        Node typeNode = this.IsVariable(tok, true);
        int ptBase = -1; 
        if (typeNode != null)
        {
            ptBase = this.GetDomainPrimitiveType(typeNode); 
            
            if (ptBase != PT_ITERATOR)
            {
                String[] errs = new String[5];
                errs[0] = Integer.toString(tok.getLine());
                errs[1] = Integer.toString(tok.getCharPositionInLine());
                errs[2] = this.contextName;
                errs[3] = varName;
                errs[4] = typeNode.getFirstChild().getNodeName();
                
                this.LoadErrorDescription(errs, "ITERATOR_EXPECTED", tok.getLine(), tok.getCharPositionInLine());
            }
        }
        this.currentNode = oldCurrNode;
    }
    
    public String CheckIsIteratorName(String varName)
    {
        /*Node temp = doc.createElement("var");
        this.currentNode.appendChild(temp);*/
        
        Node typeNode = this.IsVariable(varName);
        int ptBase = -1; 
        if (typeNode != null)
        {
            ptBase = this.GetDomainPrimitiveType(typeNode); 
            
            if (ptBase != PT_ITERATOR)
            {
                return "Invalid variable. Variable '" +varName+ "' can not be user as iterator. Expected ITERATOR variable, but found " + typeNode.getFirstChild().getNodeName();
            }
            else
            {
                return "";
            }
        }
        else
        {
            return "Unknown variable";
        }
    }
    
    public String CheckForVar(String varName)
    {
        Node typeNode = this.IsVariable(varName);
        int ptBase = -1; 
        
        if (typeNode != null)
        {
            ptBase = this.GetDomainPrimitiveType(typeNode); 
            
            if (ptBase != PT_INT)
            {
                return "Invalid for variable. Variable '" +varName+ "' can not be user as counter. Expected integer variable, but found " + typeNode.getFirstChild().getNodeName();                
            }
            else
            {
                return "";
            }
        }
        else
        {
            return "Unknown variable";
        }
    }
    
    public void CheckForBoundry(Node typeNode, Token tok)
    {
        if (typeNode == null || typeNode.getFirstChild() == null)
        {
            return;
        }
        
        int ptBase = -1; 
        
        String isConstValue = ((Element)typeNode).getAttribute("IsConst");
        
        if ( isConstValue == "TRUE")
        {
            String constType = typeNode.getFirstChild().getNodeName();
            ptBase = this.CalcDataTypeBaseCode(constType);
        }
        else
        {
            ptBase = this.GetDomainPrimitiveType(typeNode); 
        }
        
        if (ptBase != PT_INT)
        {
            String[] errs = new String[4];
            errs[0] = Integer.toString(tok.getLine());
            errs[1] = Integer.toString(tok.getCharPositionInLine());
            errs[2] = this.contextName;
            errs[3] = typeNode.getFirstChild().getNodeName();
            
            this.LoadErrorDescription(errs, "INVALID_FOR_BOUNDARY", tok.getLine(), tok.getCharPositionInLine());
        }
    }
    
    public String CheckForBoundry(Node typeNode)
    {
        if (typeNode == null || typeNode.getFirstChild() == null)
        {
            return "";
        }
        
        int ptBase = -1; 
        
        String isConstValue = ((Element)typeNode).getAttribute("IsConst");
        
        if ( isConstValue == "TRUE")
        {
            String constType = typeNode.getFirstChild().getNodeName();
            ptBase = this.CalcDataTypeBaseCode(constType);
        }
        else
        {
            ptBase = this.GetDomainPrimitiveType(typeNode); 
        }
        
        if (ptBase != PT_INT)
        {
            return "Invalid for boundry. Expeced integer, but found " + typeNode.getFirstChild().getNodeName();
        }
        
        return "";
    }
    
    public void CheckCase(Node tNode1, Node tNode2, Token tok)
    {
        boolean can = this.CheckExprComp(tNode1, tNode2);
        
        if (!can)
        {
            //report error
             String[] errs = new String[5];
             errs[0] = Integer.toString(tok.getLine());
             errs[1] = Integer.toString(tok.getCharPositionInLine());
             errs[2] = this.contextName;
             errs[3] = tNode1.getFirstChild().getNodeName();
             errs[4] = tNode2.getFirstChild().getNodeName();
             
            this.LoadErrorDescription(errs, "ERROR_CASE_DATA_TYPE", tok.getLine(), tok.getCharPositionInLine());
            return;
        }
    }
    
    public String CheckCase(Node tNode1, Node tNode2)
    {
        boolean can = this.CheckExprComp(tNode1, tNode2);
        
        if (!can)
        {
            return "Data type mismatch in case expression. Expected " + tNode1.getFirstChild().getNodeName() + ", but found " + tNode2.getFirstChild().getNodeName();            
        }
        return "";
    }
    
    public void BeginFuncBody()
    {
        if (this.curretnFuncNode != null)
        {
            Node temp = doc.createElement("BODY");
            this.curretnFuncNode.appendChild(temp);
            this.currentNode = temp;
        }
    }
    
    public Node BeginStatements()
    {
        if (this.currentNode != null)
        {
            Node temp = doc.createElement("statements");
            this.currentNode.appendChild(temp);
            this.currentStatementsNode = temp;
            this.currentNode = temp;
            return temp;
        }
        return null;
    }
    
    public void RewindStatements(Node temp)
    {
        this.currentStatementsNode = temp;
        this.currentNode = temp;
    }
    
    public void setETypeAtt(Node expNode, Node typeNode)
    {
        try 
        {
            ((Element)expNode).setAttribute("TypeName", typeNode.getFirstChild().getNodeName());
        }
        catch(Exception e)
        {
        }
    }
    
    public Node CreateStatement(String statName, Token tok)
    {
        if (this.currentStatementsNode != null)
        {
            Element temp = doc.createElement(statName);
            this.currentStatementsNode.appendChild(temp);
            this.currentNode = temp;
            temp.setAttribute("Break", Integer.toString(tok.getLine()) + "," + Integer.toString(tok.getCharPositionInLine()));
            return temp;
        }
        return null;
    }
    
    public Node CreateCurrNode(String nodeName)
    {
        if (this.currentNode != null)
        {
            Node temp = doc.createElement(nodeName);
            this.currentNode.appendChild(temp);
            this.currentNode = temp;
            return temp;
        }
        return null;
    }
    
    public Node CreateCurrNode(String nodeName, Token tok)
    {
        if (this.currentNode != null)
        {
            Node temp = doc.createElement(nodeName);
            this.currentNode.appendChild(temp);
            this.currentNode = temp;
            ((Element)temp).setAttribute("Break", Integer.toString(tok.getLine()) + "," + Integer.toString(tok.getCharPositionInLine()));
            return temp;
        }
        return null;
    }
    
    public void BeginFuncParam(Node funcNode)
    {
        if (this.currentNode != null)
        {
            Node temp = doc.createElement("param");
            funcNode.appendChild(temp);
            this.currentNode = temp;
        }
    }
    
    public Node CreateConstNode(Node tempNode)
    {
        if (tempNode != null)
        {
            ((Element)tempNode).setAttribute("IsConst", "TRUE");
        }
        
        if (this.currentNode != null)
        {
            Node temp = doc.createElement("const");
            
            String val = ((Element)tempNode).getAttribute("VALUE");
            
            if (val != null && val != "")
            {
                ((Element)temp).setAttribute("val", val);
            }
            
            if (tempNode.getFirstChild() != null)
            {
                ((Element)temp).setAttribute("type", tempNode.getFirstChild().getNodeName());
            }
            this.currentNode.appendChild(temp);
            this.currentNode = temp;
        }
        
        return tempNode;
    }
    
    public Node CreateConstVarInitNode(Node tempNode)
    {
        if (this.currentNode != null)
        {
            Node temp = doc.createElement("const");
            
            if (tempNode.getFirstChild() != null && tempNode.getFirstChild().getFirstChild() != null)
            {
                ((Element)temp).setAttribute("type", tempNode.getFirstChild().getFirstChild().getNodeName());
            }
            
            if (tempNode.getChildNodes().item(1) != null)
            {
                String val = getTextContent(tempNode.getChildNodes().item(1));
                
                if (val != null && val != "")
                {
                    ((Element)temp).setAttribute("val", val);
                }    
            }
            
            this.currentNode.appendChild(temp);
        }
        
        return tempNode;
    }
    
    public void CreateBinOperator(Node tempNode, Token tok)
    {
        try 
        {
            Element oldNode = (Element)tempNode.cloneNode(true);            
            
            while( tempNode.getChildNodes().getLength() > 0)
            {
                tempNode.removeChild(tempNode.getChildNodes().item(0));
            }
            
            ((Element)tempNode).setAttribute("Op", tok.getText());
            tempNode.appendChild(oldNode);
            
            this.currentNode = doc.createElement("expr");
            tempNode.appendChild(this.currentNode);
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    
    public void CreateUnOperator(Token tok)
    {
        try 
        {
            if (this.currentNode!= null)
            {
                ((Element)this.currentNode).setAttribute("Op", tok.getText());
                
                if (tok.getText() != null && tok.getText().equals("-"))
                {
                    ((Element)this.currentNode).setAttribute("Op", "unminus");
                }
                
                Node temp = doc.createElement("expr");
                this.currentNode.appendChild(temp);
                this.currentNode = temp;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    
    public void CreateLikeOperator(Node tempNode, Token tok, Token pattTok)
    {
        try 
        {
            Element oldNode = (Element)tempNode.cloneNode(true);            
            
            while( tempNode.getChildNodes().getLength() > 0)
            {
                tempNode.removeChild(tempNode.getChildNodes().item(0));
            }
            
            ((Element)tempNode).setAttribute("Op", tok.getText());
            ((Element)tempNode).setAttribute("Pattern", pattTok.getText());
            tempNode.appendChild(oldNode);
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    
    public void CreateInOperator(Node tempNode, Token tok)
    {
        try 
        {
            Element oldNode = (Element)tempNode.cloneNode(true);            
            
            while( tempNode.getChildNodes().getLength() > 0)
            {
                tempNode.removeChild(tempNode.getChildNodes().item(0));
            }
            
            ((Element)tempNode).setAttribute("Op", tok.getText());
            tempNode.appendChild(oldNode);
            
            this.currentNode = tempNode;
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    
    public void GenerateAssemberCode(Token endTok)
    {
        if (assemblerDoc == null)
        {
            return;
        }
        
        ((Element)this.currentAssFVarsNode).setAttribute("VarCount", Integer.toString(this.args.size() + this.vars.size() + used_env_vars.size()));
        
        NodeList list = ((Element)this.root.getFirstChild()).getElementsByTagName("BODY");
        
        if (list.getLength() > 0)
        {
            Element bodyNode = (Element)list.item(0);
            
            if (bodyNode.getFirstChild() != null)
            {
                this.GenerateStatementsCode(this.currentAssFCodeNode, bodyNode.getFirstChild());
                
                Element command = assemblerDoc.createElement("INS");
                command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.BREAKPOINT_OC);
                command.setAttribute("Pos", Integer.toString(endTok.getLine()) + "," + Integer.toString(endTok.getCharPositionInLine()));
                this.currentAssFCodeNode.appendChild(command);
            }
        }
        
    }
    
    public void GenerateExprCode(Element currentNode, Element exprNode)
    {
        try 
        {
            String operatorStr = exprNode.getAttribute("Op");
            
            if (operatorStr != null && operatorStr != "")
            {
                for(int i = 0; i < exprNode.getChildNodes().getLength(); i++) 
                {
                    Element node = (Element)exprNode.getChildNodes().item(i);
                    this.GenerateExprCode(currentNode, node);
                }
                
                this.GenerateOperatorNode(currentNode, exprNode, operatorStr.toLowerCase(Locale.US));
            }
            else
            {
                Element primNode = (Element)exprNode.getFirstChild();
                
                if (primNode.getNodeName() == "const")
                {
                    Element command = assemblerDoc.createElement("INS");
                    command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDC_OC);
                    
                    String attVal = primNode.getAttribute("type");
                    
                    if (attVal != null && attVal != "")
                    {
                        command.setAttribute("Type", attVal);
                    }
                    
                    attVal = primNode.getAttribute("val");
                    
                    if (attVal != null && attVal != "")
                    {
                        command.setAttribute("Val", attVal);
                    }
                    currentNode.appendChild(command);
                }
                else
                {
                    if (primNode.getNodeName() == "var")
                    {
                        this.GenerateVarExpr(currentNode, exprNode);
                    }
                    else
                    {
                        if (primNode.getNodeName() == "func_call")
                        {
                            this.GenerateFuncExpr(currentNode, exprNode);
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {}
    }
    
    public void GenerateOperatorNode(Element currentNode, Element exprNode, String oper)
    {
        if (oper.equals("=>")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.IMPL_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("xor")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.XOR_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("or")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.OR_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("and")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.AND_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("==")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.EQ_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("!=")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.NE_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("<")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LT_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("<=")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LE_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals(">")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.GT_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals(">=")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.GE_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("+")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.ADD_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("-")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.SUB_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("*")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.MUL_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("/")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.DIV_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("%")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.MOD_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("||")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.CONCAT_OC);
            currentNode.appendChild(command);
            return;
        }

        if (oper.equals("union")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.UNION_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("intersect")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.INTERSECT_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("not")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.NOT_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("unminus")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.UNMINUS_OC);
            currentNode.appendChild(command);
            return;
        }
        
        if (oper.equals("in")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.IN_OC);
            currentNode.appendChild(command);
            
            String isOverSetAtt = exprNode.getAttribute("IsOverSet");
            
            if (isOverSetAtt != null && !isOverSetAtt.equals(""))
            {
                command.setAttribute("Count", "-2");
            }
            else
            {
                command.setAttribute("Count", Integer.toString(exprNode.getChildNodes().getLength()));
            }
            
            return;
        }
        
        if (oper.equals("like")) 
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LIKE_OC);
            currentNode.appendChild(command);
            
            String patt = exprNode.getAttribute("Pattern");
            
            if (patt != null && patt != "")
            {
                command.setAttribute("Pattern", patt);    
            }
        }
    }
    
    public void GenerateVarExpr(Element currentNode, Element exprNode)
    {
        Element varNode = (Element)exprNode.getFirstChild();
        String indexStr = varNode.getAttribute("OrdNum");
        String envVarStr = varNode.getAttribute("EnvVarName");
        
        if (indexStr != null && indexStr != "")
        {
            if (varNode.getChildNodes().getLength() == 0)
            {
                Element command = assemblerDoc.createElement("INS");
                command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDV_OC);
                command.setAttribute("OrdNum", indexStr);
                
                if (envVarStr != null)
                {
                    command.setAttribute("EnvVarName", envVarStr);
                }
                
                currentNode.appendChild(command);
            }
            else
            {
                Element command = assemblerDoc.createElement("INS");
                command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDVA_OC);
                command.setAttribute("OrdNum", indexStr);
                currentNode.appendChild(command);
                
                for(int i = 0; i < varNode.getChildNodes().getLength(); i++)
                {
                    Element childNode = (Element)varNode.getChildNodes().item(i);
                    
                    if (childNode.getNodeName() == "subscript")
                    {
                        this.GenerateExprCode(currentNode, (Element)childNode.getFirstChild());
                        
                        if ( i == varNode.getChildNodes().getLength() -1)
                        {
                            Element tempNode = assemblerDoc.createElement("INS");
                            tempNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDI_OC);
                            currentNode.appendChild(tempNode);
                        }
                        else
                        {
                            Element tempNode = assemblerDoc.createElement("INS");
                            tempNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDIA_OC);
                            currentNode.appendChild(tempNode);
                        }
                    }
                    else
                    {
                        String mIndex = childNode.getAttribute("OrdNum");
                        
                        if (mIndex != null && mIndex != "")
                        {
                            if ( i == varNode.getChildNodes().getLength() -1)
                            {
                                Element tempNode = assemblerDoc.createElement("INS");
                                tempNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDM_OC);
                                tempNode.setAttribute("OrdNum", mIndex);
                                currentNode.appendChild(tempNode);
                            }
                            else
                            {
                                Element tempNode = assemblerDoc.createElement("INS");
                                tempNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDMA_OC);
                                tempNode.setAttribute("OrdNum", mIndex);
                                currentNode.appendChild(tempNode);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void GenerateFuncExpr(Element currentNode, Element exprNode)
    {
        Element funcNode = (Element)exprNode.getFirstChild();
        String funcNameStr = funcNode.getAttribute("Name");
        
        if (funcNameStr != null && funcNameStr != "")
        {
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.FUNC_OC);
            command.setAttribute("Name", funcNameStr);
            
            String att = funcNode.getAttribute("OrdNum");
            
            if (att != null && att != "")
            {
                command.setAttribute("OrdNum", att);
            }
            
            att = funcNode.getAttribute("Type");
                        
            if (att != null && att != "")
            {
                command.setAttribute("Type", att);
            }
                        
            for(int i = 0; i < funcNode.getChildNodes().getLength(); i++)
            {
                Element childNode = (Element)funcNode.getChildNodes().item(i);                
                this.GenerateExprCode(currentNode, (Element)childNode.getFirstChild());
                
                String iotype = childNode.getAttribute("iotype");
                
                if (iotype != null && (iotype.equals("out") || iotype.equals("inout")))
                {
                    Element lastNode = (Element)currentNode.getLastChild();
                    String opcode = lastNode.getAttribute(OpCodes.OP_ATT_NAME);
                    
                    if (opcode != null)
                    {
                        if (opcode.equals(OpCodes.LDV_OC))
                        {
                            lastNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDVA_OC);
                        }
                        else
                        {
                            if (opcode.equals(OpCodes.LDI_OC))
                            {
                                lastNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDIA_OC);
                            }
                            else
                            {
                                if (opcode.equals(OpCodes.LDM_OC))
                                {
                                    lastNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDMA_OC);
                                }
                            }
                        }
                    }                    
                }
            }
            
            command.setAttribute("Count", Integer.toString(funcNode.getChildNodes().getLength()));
            
            currentNode.appendChild(command);
        }
    }
    
    public void GenerateStatementsCode(Node currentNode, Node statementsNode)
    {
        for(int i = 0; i < statementsNode.getChildNodes().getLength(); i++)
        {
            Node statementNode = statementsNode.getChildNodes().item(i);
            
            String statementName = statementNode.getNodeName();
            
            if (statementName == "assign")
            {
                this.GenerateAssignStatementsCode(currentNode, statementNode);
            }
            else
            {
                if (statementName == "if")
                {
                    this.GenerateIfStatementsCode(currentNode, statementNode);
                }
                else
                {
                    if (statementName == "while")
                    {
                        this.GenerateWhileStatementsCode(currentNode, statementNode);
                    }
                    else
                    {
                        if (statementName == "repeat")
                        {
                            this.GenerateRepeatStatementsCode(currentNode, statementNode);
                        }
                        else
                        {
                            if (statementName == "for")
                            {
                                this.GenerateForStatementsCode(currentNode, statementNode);
                            }
                            else
                            {
                                if (statementName == "switch")
                                {
                                    this.GenerateSwitchCode(currentNode, statementNode);
                                }
                                else
                                {
                                    if (statementName == "debug")
                                    {
                                        this.GenerateDebugStatementsCode(currentNode, statementNode);
                                    }
                                    else
                                    {
                                        if (statementName == "return")
                                        {
                                            this.GenerateReturnStatementsCode(currentNode, statementNode);
                                        }
                                        else
                                        {
                                            if (statementName == "break")
                                            {
                                                this.GenerateBreakStatementsCode(currentNode, statementNode);
                                            }
                                            else
                                            {
                                                if (statementName == "select")
                                                {
                                                    this.GenerateSelectStatementsCode(currentNode, statementNode);
                                                }
                                                else
                                                {
                                                    if (statementName == "signal")
                                                    {
                                                        this.GenerateSignalStatementsCode(currentNode, statementNode);
                                                    }
                                                    else
                                                    {
                                                        if (statementName == "update")
                                                        {
                                                            this.GenerateUpdateStatementsCode(currentNode, statementNode);
                                                        }
                                                        else
                                                        {
                                                            if (statementName == "fetch")
                                                            {
                                                                this.GenerateFetchStatementsCode(currentNode, statementNode);
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
    
    public void GenerateSwitchCode(Node currentNode, Node switchNode)
    {
        try 
        {
            if (switchNode.getChildNodes().getLength() > 0)
            {
                this.GenerateLineCommand(currentNode, switchNode);
                
                Element valNode = (Element)switchNode.getChildNodes().item(0);
                
                if (valNode != null)
                {
                    ArrayList jmpComm = new ArrayList();
                    Element prevCaseNode = null;
                    Element command = null;
                    
                    for(int i = 1; i < switchNode.getChildNodes().getLength(); i++) 
                    {
                        Element caseNode = (Element)switchNode.getChildNodes().item(i);
                        
                        if (caseNode.getNodeName() == "case")
                        {
                            if (caseNode.getChildNodes().getLength() > 1)
                            {
                                Element condNode = (Element)caseNode.getChildNodes().item(0);
                                Element statementsNode = (Element)caseNode.getChildNodes().item(1);
                                
                                if (condNode != null && condNode.getFirstChild() != null)
                                {
                                    this.GenerateExprCode((Element)currentNode, (Element)valNode.getFirstChild());
                                    this.GenerateExprCode((Element)currentNode, (Element)condNode.getFirstChild());
                                    
                                    command = assemblerDoc.createElement("INS");                    
                                    command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.EQ_OC);
                                    currentNode.appendChild(command);
                                    
                                    prevCaseNode = assemblerDoc.createElement("INS");
                                    prevCaseNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.JMPCN_OC);
                                    currentNode.appendChild(prevCaseNode);
                                }
                                
                                if (statementsNode != null)
                                {
                                    this.GenerateStatementsCode(currentNode, statementsNode);
                                }
                                
                                Element jmpCommand = assemblerDoc.createElement("INS");                    
                                jmpCommand.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.JMP_OC);
                                currentNode.appendChild(jmpCommand);
                                
                                jmpComm.add(jmpCommand);
                                
                                prevCaseNode.setAttribute("ToPos", Integer.toString(currentNode.getChildNodes().getLength()));
                                
                            }
                        }
                        else
                        {
                            //default 
                             this.GenerateStatementsCode(currentNode, caseNode.getFirstChild());
                        }
                    }
                    
                     int commCount = currentNode.getChildNodes().getLength();
                     
                     for(int i = 0; i < jmpComm.size(); i++)
                     {
                         ((Element)jmpComm.get(i)).setAttribute("ToPos", Integer.toString(commCount));
                     }
                }
            }       
        }
        catch(Exception e)
        {
        }
    }
    
    public void GenerateRepeatStatementsCode(Node currentNode, Node repeatNode)
    {
        try 
        {
            if (repeatNode.getChildNodes().getLength() > 1)
            {
                this.GenerateLineCommand(currentNode, repeatNode);
                
                Element condNode = (Element)repeatNode.getChildNodes().item(1);
                Element statementsNode = (Element)repeatNode.getChildNodes().item(0);
                
                if (statementsNode != null)
                {
                    int commCount = currentNode.getChildNodes().getLength();
                    int exitStackBeforeCount = this.exitStack.size();
                    
                    this.GenerateStatementsCode(currentNode, statementsNode);
                    
                    if (condNode != null && condNode.getFirstChild() != null)
                    {
                        this.GenerateExprCode((Element)currentNode, (Element)condNode.getFirstChild());
                        
                        this.GenerateLineCommand(currentNode, condNode);
                        
                        Element command = assemblerDoc.createElement("INS");                    
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.JMPCN_OC);
                        currentNode.appendChild(command);
                        command.setAttribute("ToPos", Integer.toString(commCount));
                        
                        int exitStackAfterCount = this.exitStack.size();
                        commCount = currentNode.getChildNodes().getLength();
                        
                        for(int k = 0; k < exitStackAfterCount - exitStackBeforeCount; k++)
                        {
                            command = (Element)this.exitStack.pop();
                            command.setAttribute("ToPos", Integer.toString(commCount));
                        }
                    }
                }
            }       
        }
        catch(Exception e)
        {
        }
    }
        
    public void GenerateForStatementsCode(Node currentNode, Node forNode)
    {
        try 
        {
            if (forNode.getChildNodes().getLength() > 3)
            {
                Element varNode = (Element)forNode.getChildNodes().item(0);
                Element fromNode = (Element)forNode.getChildNodes().item(1);
                Element toNode = (Element)forNode.getChildNodes().item(2);
                Element statementsNode = (Element)forNode.getChildNodes().item(3);
                
                int exitStackBeforeCount = this.exitStack.size();
                
                if (statementsNode != null && varNode != null && fromNode != null && toNode != null)
                {
                    String index = varNode.getAttribute("OrdNum");
                    
                    if (index != "" && index != null)
                    {
                        this.GenerateExprCode((Element)currentNode, (Element)fromNode.getFirstChild());
                        
                        Element command = assemblerDoc.createElement("INS");                    
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.ST_OC);
                        command.setAttribute("OrdNum", index);
                        currentNode.appendChild(command);
                        
                        int begCommCount = currentNode.getChildNodes().getLength();
                        this.GenerateLineCommand(currentNode, forNode);
                        
                        command = assemblerDoc.createElement("INS");                    
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDV_OC);
                        command.setAttribute("OrdNum", index);
                        currentNode.appendChild(command);
                        
                        this.GenerateExprCode((Element)currentNode, (Element)toNode.getFirstChild());
                        
                        command = assemblerDoc.createElement("INS");                    
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LE_OC);
                        currentNode.appendChild(command);
                        
                        Element jmpCommand = assemblerDoc.createElement("INS");                    
                        jmpCommand.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.JMPCN_OC);
                        currentNode.appendChild(jmpCommand);
                        
                        this.GenerateStatementsCode(currentNode, statementsNode);
                        
                        command = assemblerDoc.createElement("INS");                    
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDC_OC);
                        command.setAttribute("Val", "1");
                        command.setAttribute("Type", "INT");
                        currentNode.appendChild(command);
                        
                        command = assemblerDoc.createElement("INS");                    
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDV_OC);
                        command.setAttribute("OrdNum", index);
                        currentNode.appendChild(command);
                        
                        command = assemblerDoc.createElement("INS");                    
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.ADD_OC);
                        currentNode.appendChild(command);
                        
                        command = assemblerDoc.createElement("INS");                    
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.ST_OC);
                        command.setAttribute("OrdNum", index);
                        currentNode.appendChild(command);
                        
                        command = assemblerDoc.createElement("INS");                    
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.JMP_OC);
                        currentNode.appendChild(command);
                        command.setAttribute("ToPos", Integer.toString(begCommCount));
                        
                        int commCount = currentNode.getChildNodes().getLength();
                        jmpCommand.setAttribute("ToPos", Integer.toString(commCount));
                        
                        int exitStackAfterCount = this.exitStack.size();
                        
                        for(int k = 0; k < exitStackAfterCount - exitStackBeforeCount; k++)
                        {
                            command = (Element)this.exitStack.pop();
                            command.setAttribute("ToPos", Integer.toString(commCount));
                        }
                    }
                }
            }       
        }
        catch(Exception e)
        {
        }
    }
    
    public void GenerateWhileStatementsCode(Node currentNode, Node whileNode)
    {
        try 
        {
            if (whileNode.getChildNodes().getLength() > 1)
            {
                int exitStackBeforeCount = this.exitStack.size();
                
                int begComm = currentNode.getChildNodes().getLength();
                
                this.GenerateLineCommand(currentNode, whileNode);
                
                Element condNode = (Element)whileNode.getChildNodes().item(0);
                Element statementsNode = (Element)whileNode.getChildNodes().item(1);
                
                if (condNode != null && condNode.getFirstChild() != null)
                {   
                    this.GenerateExprCode((Element)currentNode, (Element)condNode.getFirstChild());
                    
                    Element command = assemblerDoc.createElement("INS");                    
                    command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.JMPCN_OC);
                    currentNode.appendChild(command);
                    
                    if (statementsNode != null)
                    {
                        this.GenerateStatementsCode(currentNode, statementsNode);
                    }
                    
                    int commCount = currentNode.getChildNodes().getLength();
                    command.setAttribute("ToPos", Integer.toString(commCount + 1));
                    
                    command = assemblerDoc.createElement("INS");                    
                    command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.JMP_OC);
                    command.setAttribute("ToPos", Integer.toString(begComm));
                    currentNode.appendChild(command);
                    
                    int exitStackAfterCount = this.exitStack.size();
                    
                    for(int k = 0; k < exitStackAfterCount - exitStackBeforeCount; k++)
                    {
                        command = (Element)this.exitStack.pop();
                        command.setAttribute("ToPos", Integer.toString(commCount + 1));
                    }
                }
            }       
        }
        catch(Exception e)
        {
        }
    }
    
    public void GenerateIfStatementsCode(Node currentNode, Node ifNode)
    {
        try 
        {
            if (ifNode.getChildNodes().getLength() > 1)
            {
                this.GenerateLineCommand(currentNode, ifNode);
                
                Element condNode = (Element)ifNode.getChildNodes().item(0);
                Element statementsNode = (Element)ifNode.getChildNodes().item(1);
                
                if (condNode != null && condNode.getFirstChild() != null)
                {
                    this.GenerateExprCode((Element)currentNode, (Element)condNode.getFirstChild());
                    
                    Element command = assemblerDoc.createElement("INS");                    
                    command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.JMPCN_OC);
                    currentNode.appendChild(command);
                    
                    if (statementsNode != null)
                    {
                        this.GenerateStatementsCode(currentNode, statementsNode);
                    }
                    
                    Element jmpcommand = assemblerDoc.createElement("INS");                    
                    jmpcommand.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.JMP_OC);
                    currentNode.appendChild(jmpcommand);
                                        
                    int commCount = currentNode.getChildNodes().getLength();
                    
                    command.setAttribute("ToPos", Integer.toString(commCount));
                    
                    if (ifNode.getChildNodes().getLength() == 3)
                    {
                        this.GenerateStatementsCode(currentNode, (Element)ifNode.getChildNodes().item(2).getFirstChild());
                    }
                    
                    commCount = currentNode.getChildNodes().getLength();
                    jmpcommand.setAttribute("ToPos", Integer.toString(commCount));
                }
            }       
        }
        catch(Exception e)
        {
        }
    }
    
    public void GenerateDomainAsemblerCode(String domainName, Node currentNode)
    {
        try 
        {
            if (assemblerDoc==null)
            {
                return;
            }
            
            int i = 1;
            
            DomainDesc current = null;
            
            if (this.userDefDomains.containsKey(domainName.toLowerCase(Locale.US).toString()))
            {
                current = (DomainDesc)this.userDefDomains.get(domainName.toLowerCase(Locale.US).toString());
            }
            else
            {
                if (this.domains.containsKey(domainName.toLowerCase(Locale.US).toString()))
                {
                    current = (DomainDesc)this.domains.get(domainName.toLowerCase(Locale.US).toString());
                }    
            }
            
            while(i < 100)
            {
                if (current.getType() == DomainDesc.PRIMITIVE)
                {
                    //return current.primitiveDomainType;
                     Element command = assemblerDoc.createElement("INS");                    
                     command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.PUSH_OC);
                     currentNode.appendChild(command);
                     
                     command = assemblerDoc.createElement("INS");                    
                     command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.VAL_OC);
                     command.setAttribute("Type", SemAnalyzer.ptNames[ current.primitiveDomainType ]);
                     currentNode.appendChild(command);                                         
                     return;
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
                            if (current.getType() == DomainDesc.TUPPLE || current.getType() == DomainDesc.CHOICE)
                            {
                                for(int j = 0; j < current.getMembers().size(); j++)
                                {
                                    AttributeDesc attDesc = (AttributeDesc)current.getMembers().get(j);
                                    
                                    this.GenerateDomainAsemblerCode(attDesc.getDomName(), currentNode);
                                    
                                    Element command = assemblerDoc.createElement("INS");                    
                                    command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.MEMBER_OC);
                                    command.setAttribute("Name", attDesc.getName());
                                    currentNode.appendChild(command);
                                }
                                
                                Element command = assemblerDoc.createElement("INS");
                                command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.TUPPLE_OC);
                                command.setAttribute("MemCount", Integer.toString(current.getMembers().size()));
                                currentNode.appendChild(command);
                                
                                return;
                            }
                            else
                            {
                                if (current.getType() == DomainDesc.SET)
                                {
                                    this.GenerateDomainAsemblerCode(current.getParentName(), currentNode);
                                    
                                    Element command = assemblerDoc.createElement("INS");
                                    command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.SET_OC);
                                    currentNode.appendChild(command);   
                                    return;
                                }
                                else
                                {
                                    return;
                                }
                            }
                        }
                    }
                }
                
                i = i + 1;
            }               
                            
        }
        catch(Exception e)
        {
        }
    }
    
    public void GenerateDebugStatementsCode(Node currentNode, Node debugNode)
    {
        try 
        {
            if (debugNode.getChildNodes().getLength() > 0)
            {   
                this.GenerateLineCommand(currentNode, debugNode);
                
                Element valNode = (Element)debugNode.getChildNodes().item(0);
                
                if (valNode != null && valNode.getFirstChild() != null)
                {
                    this.GenerateExprCode((Element)currentNode, (Element)valNode.getFirstChild());
                }
                
                Element command = assemblerDoc.createElement("INS");                        
                command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.PRINT_OC);
                currentNode.appendChild(command);
            }       
        }
        catch(Exception e)
        {
        }
    }
    
    public void GenerateReturnStatementsCode(Node currentNode, Node returnNode)
    {
        try 
        {
            if (returnNode.getChildNodes().getLength() > 0)
            {   
                this.GenerateLineCommand(currentNode, returnNode);
                
                Element valNode = (Element)returnNode.getChildNodes().item(0);
                
                if (valNode != null && valNode.getFirstChild() != null)
                {
                    this.GenerateExprCode((Element)currentNode, (Element)valNode.getFirstChild());
                }
                
                Element command = assemblerDoc.createElement("INS");                        
                command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.RETURN_OC);
                currentNode.appendChild(command);
            }       
        }
        catch(Exception e)
        {
        }
    }
    
    public void GenerateBreakStatementsCode(Node currentNode, Node breakNode)
    {
        try 
        {
            this.GenerateLineCommand(currentNode, breakNode);
                
            Element command = assemblerDoc.createElement("INS");                        
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.JMP_OC);
            currentNode.appendChild(command);
            
            this.exitStack.push(command);
        }
        catch(Exception e)
        {
        }
    }
    
    public void GenerateSignalStatementsCode(Node currentNode, Node signalNode)
    {
        try 
        {
            this.GenerateLineCommand(currentNode, signalNode);
            
            NodeList list = signalNode.getChildNodes();
            
            for(int i = 0; i < list.getLength(); i++)
            {
                this.GenerateExprCode((Element)currentNode, (Element)list.item(i).getFirstChild());
            }
            
            Element command = assemblerDoc.createElement("INS");                        
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.SIGNAL_OC);
            command.setAttribute("Count", Integer.toString(list.getLength()));
            
            String sigName = ((Element)signalNode).getAttribute("Name");
            
            if (sigName != null)
            {
                command.setAttribute("Name", sigName);
            }
            
            currentNode.appendChild(command);
        }
        catch(Exception e)
        {
        }
    }
    
    public void GenerateUpdateStatementsCode(Node currentNode, Node signalNode)
    {
        try 
        {
            this.GenerateLineCommand(currentNode, signalNode);
            
            NodeList list = signalNode.getChildNodes();
            
            for(int i = 0; i < list.getLength(); i++)
            {
                this.GenerateExprCode((Element)currentNode, (Element)list.item(i).getFirstChild());
            }
            
            Element command = assemblerDoc.createElement("INS");                        
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.UPDATE_OC);
            command.setAttribute("Count", Integer.toString(list.getLength()));
            
            String tobId = ((Element)signalNode).getAttribute("TobId");
            
            if (tobId != null && !tobId.equals(""))
            {
                command.setAttribute("TobId", tobId);
            }
            
            String commName = ((Element)signalNode).getAttribute("ComName");
            
            if (commName != null && !commName.equals(""))
            {
                command.setAttribute("Name", commName);
            }
            else
            {
                String setList = ((Element)signalNode).getAttribute("SetList");                
                
                if (setList != null && !setList.equals(""))
                {
                    command.setAttribute("Name", setList);
                }
            }
            currentNode.appendChild(command);
        }
        catch(Exception e)
        {
        }
    }
    
    public void GenerateLineCommand(Node currentNode, Node statNode)
    {
        Element command = assemblerDoc.createElement("INS");
        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.BREAKPOINT_OC);
        
        String pos = ((Element)statNode).getAttribute("Break");
        
        if (pos != null && pos != "")
        {
            command.setAttribute("Pos", pos);
        }
        currentNode.appendChild(command);
    }
    
    public void GenerateAssignStatementsCode(Node currentNode, Node assNode)
    {
        try 
        {
            if (assNode.getChildNodes().getLength() > 1)
            {
                this.GenerateLineCommand(currentNode, assNode);
                
                Element varNode = (Element)assNode.getChildNodes().item(0);
                Element valNode = (Element)assNode.getChildNodes().item(1);
                
                if (valNode != null && valNode.getFirstChild() != null)
                {
                    this.GenerateExprCode((Element)currentNode, (Element)valNode.getFirstChild());
                }
                
                if (varNode != null)
                {
                    if (varNode.getChildNodes().getLength() == 0)
                    {
                        //generate STORE command
                        String index = varNode.getAttribute("OrdNum");
                        Element command = assemblerDoc.createElement("INS");
                        
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.ST_OC);
                        
                        if (index != "" && index != null)
                        {
                            command.setAttribute("OrdNum", index);
                        }
                        
                        currentNode.appendChild(command);
                    }
                    else
                    {
                        String index = varNode.getAttribute("OrdNum");
                        
                        if (index != "" && index != null)
                        {
                            Element command = assemblerDoc.createElement("INS");
                            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDVA_OC);
                            command.setAttribute("OrdNum", index);
                            command.setAttribute("Name", "Ass");
                            currentNode.appendChild(command);
                            
                            for(int i = 0; i < varNode.getChildNodes().getLength(); i++)
                            {
                                Element childNode = (Element)varNode.getChildNodes().item(i);
                                
                                if (childNode.getNodeName() == "subscript")
                                {
                                    this.GenerateExprCode((Element)currentNode, (Element)childNode.getFirstChild());
                                    
                                    Element tempNode = assemblerDoc.createElement("INS");
                                    tempNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDIA_OC);
                                    currentNode.appendChild(tempNode);
                                }
                                else
                                {
                                    String mIndex = childNode.getAttribute("OrdNum");
                                    
                                    if (mIndex != null && mIndex != "")
                                    {
                                        Element tempNode = assemblerDoc.createElement("INS");
                                        tempNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDMA_OC);
                                        tempNode.setAttribute("OrdNum", mIndex);
                                        currentNode.appendChild(tempNode);
                                    }
                                }
                            }
                        }
                        
                        Element command = assemblerDoc.createElement("INS");                        
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.STA_OC);
                        currentNode.appendChild(command);
                    }
                }
            }       
        }
        catch(Exception e)
        {
        }
    }
    
    public void GenerateSelectStatementsCode(Node currentNode, Node selNode)
    {
        try 
        {
            if (selNode.getChildNodes().getLength() > 2)
            {
                this.GenerateLineCommand(currentNode, selNode);
                
                Element funcNode = (Element)selNode.getChildNodes().item(0);
                Element varNode = (Element)selNode.getChildNodes().item(1);
                Element valNode = (Element)selNode.getChildNodes().item(2);
                
                if (valNode != null && valNode.getFirstChild() != null)
                {
                    this.GenerateExprCode((Element)currentNode, (Element)valNode.getFirstChild());
                }
                
                if (varNode != null)
                {
                    if (varNode.getChildNodes().getLength() == 0)
                    {
                        //generate STORE command
                        String index = varNode.getAttribute("OrdNum");
                        Element command = assemblerDoc.createElement("INS");
                        
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.SELECT_OC);
                        
                        if (index != "" && index != null)
                        {
                            command.setAttribute("OrdNum", index);
                        }
                        
                        command.setAttribute("FuncName", funcNode.getFirstChild().getNodeName());
                        currentNode.appendChild(command);
                    }
                    else
                    {
                        String index = varNode.getAttribute("OrdNum");
                        
                        if (index != "" && index != null)
                        {
                            Element command = assemblerDoc.createElement("INS");
                            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDVA_OC);
                            command.setAttribute("OrdNum", index);
                            command.setAttribute("Name", "Ass");
                            currentNode.appendChild(command);
                            
                            for(int i = 0; i < varNode.getChildNodes().getLength(); i++)
                            {
                                Element childNode = (Element)varNode.getChildNodes().item(i);
                                
                                if (childNode.getNodeName() == "subscript")
                                {
                                    this.GenerateExprCode((Element)currentNode, (Element)childNode.getFirstChild());
                                    
                                    Element tempNode = assemblerDoc.createElement("INS");
                                    tempNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDIA_OC);
                                    currentNode.appendChild(tempNode);
                                }
                                else
                                {
                                    String mIndex = childNode.getAttribute("OrdNum");
                                    
                                    if (mIndex != null && mIndex != "")
                                    {
                                        Element tempNode = assemblerDoc.createElement("INS");
                                        tempNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDMA_OC);
                                        tempNode.setAttribute("OrdNum", mIndex);
                                        currentNode.appendChild(tempNode);
                                    }
                                }
                            }
                        }
                        
                        Element command = assemblerDoc.createElement("INS");                        
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.SELECTA_OC);
                        command.setAttribute("FuncName", funcNode.getFirstChild().getNodeName());
                        currentNode.appendChild(command);
                    }
                }
            }       
        }
        catch(Exception e)
        {
        }
    }
    
    public void GenerateFetchStatementsCode(Node currentNode, Node fNode)
    {
        try 
        {
            this.GenerateLineCommand(currentNode, fNode);
                
            Element fetchNode = (Element)fNode;
            Element varNode = null;           
            
            NodeList list = fetchNode.getChildNodes();
            
            for(int j = 0; j < list.getLength(); j++) 
            {
                varNode = (Element)list.item(j);
                
                if (varNode != null)
                {
                    String index = varNode.getAttribute("OrdNum");
                    
                    if (index != "" && index != null)
                    {
                        Element command = assemblerDoc.createElement("INS");
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDVA_OC);
                        command.setAttribute("OrdNum", index);
                        command.setAttribute("Name", "Ass");
                        currentNode.appendChild(command);
                        
                        for(int i = 0; i < varNode.getChildNodes().getLength(); i++)
                        {
                            Element childNode = (Element)varNode.getChildNodes().item(i);
                            
                            if (childNode.getNodeName() == "subscript")
                            {
                                this.GenerateExprCode((Element)currentNode, (Element)childNode.getFirstChild());
                                
                                Element tempNode = assemblerDoc.createElement("INS");
                                tempNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDIA_OC);
                                currentNode.appendChild(tempNode);
                            }
                            else
                            {
                                String mIndex = childNode.getAttribute("OrdNum");
                                
                                if (mIndex != null && mIndex != "")
                                {
                                    Element tempNode = assemblerDoc.createElement("INS");
                                    tempNode.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LDMA_OC);
                                    tempNode.setAttribute("OrdNum", mIndex);
                                    currentNode.appendChild(tempNode);
                                }
                            }
                        }
                    }
                }       
            }
            
            Element command = assemblerDoc.createElement("INS");                        
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.FETCH_OC);
            
            if (fetchNode.getAttribute("OrdNum") != null) 
            {
                command.setAttribute("OrdNum", fetchNode.getAttribute("OrdNum"));
            }            
            command.setAttribute("Count", Integer.toString(list.getLength()));
            
            currentNode.appendChild(command);
        }
        catch(Exception e)
        {
        }
    }
    
    public void GenerateVars()
    {
        try 
        {
            if (assemblerDoc == null)
            {
                return;
            }
            
            if (this.curretnVarNameNode != null)
            {
                Element command = null;
                
                for(int i = 0; i < this.curretnVarNameNode.getChildNodes().getLength(); i++)
                {
                    command = assemblerDoc.createElement("INS");
                    
                    if ( i == this.curretnVarNameNode.getChildNodes().getLength() -1)
                    {
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.LAST_VAR_OC);
                    }
                    else
                    {
                        command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.VAR_OC);                    
                    }
                    command.setAttribute("Name", this.curretnVarNameNode.getChildNodes().item(i).getNodeName());
                    
                    String iType = ((Element)this.curretnVarNameNode.getParentNode()).getAttribute("Type");
                    
                    if(iType != null && !iType.equals(""))
                    {
                        command.setAttribute("Type", iType);
                    }
                    else
                    {
                        command.setAttribute("Type", "IN");
                    }
                    currentAssFVarsNode.appendChild(command);
                }
            }
        }
        catch(Exception e)
        {}
    }
    
    public void CreateInitComm(Node constantNode)
    {
        try 
        {
            if (assemblerDoc == null)
            {
                return;
            }
            
            String value = "";
            
            NodeList list = ((Element)constantNode).getElementsByTagName("VALUE");
                    
            if (list.getLength() > 0)
            {
                Node valueNode = list.item(0);
                value = getTextContent(valueNode);
            }
            
            if (assemblerDoc != null)
            {
                Element command = assemblerDoc.createElement("INS");                                    
                command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.INIT_OC);
                command.setAttribute("Val", value);
                currentAssFVarsNode.appendChild(command);      
            }
            
        }
        catch(Exception e)
        {
        }
    }
    
    public void CreatePopComm(Node constantNode)
    {
        try 
        {
            if (assemblerDoc == null)
            {
                return;
            }
            
            Element command = assemblerDoc.createElement("INS");
            command.setAttribute(OpCodes.OP_ATT_NAME, OpCodes.POP_OC);
            currentAssFVarsNode.appendChild(command);
        }
        catch(Exception e)
        {
        }
    }
    
}
