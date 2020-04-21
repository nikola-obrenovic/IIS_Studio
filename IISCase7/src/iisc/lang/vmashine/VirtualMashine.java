package iisc.lang.vmashine;

import iisc.lang.ErrorDescription;
import iisc.lang.JSourceCodeEditor;
import iisc.lang.SemAnalyzer;
import iisc.lang.VarListener;
import iisc.lang.VarMemRef;
import iisc.lang.VarRef;

import java.io.File;

import java.io.InputStream;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Locale;

import java.util.Stack;

import javax.swing.JOptionPane;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class VirtualMashine 
{
    public static int EXECUTING = 0;
    public static int EXEC_BY_COMM = 1;
    public static int FINAL = 2;
    
    public static String USER_DEF_FUNCTION = "0";
    public static String PREDEFINED_FUNCTION = "1";
    
    FuncSpec[] functions = null;
    StringBuilder outputBuilder = new StringBuilder();
    RegisterPool regPool = new RegisterPool();
    private int state = 0;
    private int counter = 0;
    private int currLine = 0;
    private boolean debugMode = false;
    FuncSpec currentFunc = null;
    private String lastChangedVar = "";
    private int lastChangedVarIndex = -1;
    public ArrayList varListeners = new ArrayList();
    public ArrayList errors = new ArrayList();
    public Connection conn = null;
    Document errDoc = null;
    Element errRoot = null;
    VMUtils utils;
    private String drivers = "sun.jdbc.odbc.JdbcOdbcDriver";
    private boolean isAborted = false;
    private boolean trigger_status = true;
    private Variable[] current_vars;
    
    public VirtualMashine() 
    {
        InitErrorsXML();
        utils = new VMUtils(this);
    }
    
    public VirtualMashine(Connection conn) 
    {
        InitErrorsXML();
        utils = new VMUtils(this);
        this.conn = conn;
    }
    
    private void InitErrorsXML()
    {
        try 
        {
            InputStream in =  JSourceCodeEditor.class.getResourceAsStream("xml/IISCasePaserErrors.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            errDoc = builder.parse(in);
            
            errRoot = errDoc.getDocumentElement();
        }
        catch(Exception e)
        {
        }
    } 
    
    public void SetConnection(String url, String user, String pass)
    {
        try
        {
            Class.forName(drivers);
            this.conn = (Connection)DriverManager.getConnection(url, user, pass);
        }
        catch (ClassNotFoundException ef) 
        {
            //JOptionPane.showMessageDialog(null, "ClassNotFoundException:    " + ef.getMessage() , "Connection error", JOptionPane.ERROR_MESSAGE);
        }
        catch(SQLException ex)
        {
            //JOptionPane.showMessageDialog(null, "<html><center>This is not valid repository!", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    } 
    
    public String getOutput()
    {
        return this.outputBuilder.toString();
    }
    
    public int getState()
    {
        return this.state;
    }
    
    public boolean getIsAborted()
    {
        return this.isAborted;
    }
    
    public void setState(int state)
    {
        this.state = state;
    }
    
    public void setTriggerStatus(boolean status)
    {
        this.trigger_status = status;
    }
    
    public boolean getTriggerStatus()
    {
        return this.trigger_status;
    }
    
    public boolean getDebugMode()
    {
        return this.debugMode;
    }
    
    public void setDebugMode(boolean debugMode)
    {
        this.debugMode = debugMode;
    }
    
    public int getCurrentLine()
    {
        return this.currLine;
    }
    
    public void AddVarListener(VarListener listtner)
    {
        this.varListeners.add(listtner);
    }
    
    private void NotifyListeners()
    {
        for(int i = 0; i < this.varListeners.size(); i++)
        {
            VarListener listtner = (VarListener)this.varListeners.get(i);
            if (listtner != null)
            {
                listtner.VarChanged(this.lastChangedVar, this.lastChangedVarIndex);
            }
        }
    }
    
    public void InitAssemblerCode(String assCodeFileName)
    {
        try 
        {
            File fXmlFile = new File(assCodeFileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
        }
        catch(Exception e)
        {
            e.printStackTrace();;
        }
    }
    
    public void InitAssemblerCode(Document doc)
    {
        try 
        {
            ArrayList funcs = new ArrayList();
            
            for(int i = 0; i < doc.getDocumentElement().getChildNodes().getLength(); i++)
            {
                if (doc.getDocumentElement().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
                {
                    Element funcNode = (Element)doc.getDocumentElement().getChildNodes().item(i);
                    FuncSpec fSpec = new FuncSpec();
                    
                    fSpec.name = funcNode.getAttribute("Name");
                    
                    Element dataNode = null;
                    Element codeNode = null;
                    
                    NodeList list = funcNode.getElementsByTagName("VARIABLES");
                    
                    if(list.getLength() > 0)
                    {
                        dataNode = (Element)list.item(0);
                    }
                    
                    list = funcNode.getElementsByTagName("COMMANDS");
                                        
                    if(list.getLength() > 0)
                    {
                        codeNode = (Element)list.item(0);
                    }
                                 
                    if ( dataNode != null && codeNode != null)       
                    {
                        InitVarsSection(dataNode, fSpec);
                        InitCommandsSection(codeNode, fSpec);
                        funcs.add(fSpec);
                    }
                }
            }
            
            this.functions = new FuncSpec[funcs.size()];
            
            for(int i = 0; i < funcs.size();i++)
            {
                this.functions[ i ] = (FuncSpec)funcs.get(i);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();;
        }
    }
    
    public ErrorDescription LoadErrorDescription(String[] args, String code)
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
                    String message = SemAnalyzer.getTextContent(textNodes.item(0));
                    result.message = message;
                    
                    for(int i = 0; i < args.length; i++)
                    {
                        result.message = result.message.replaceAll("%s" + (i+1), args[i]);
                    }
                    
                    this.errors.add(result);
                    
                    this.outputBuilder.append(result.message + "\r");
                }
            }
            
        }
        catch(Exception e)
        {
        }
        
        return result;
    }
    
    public void InitVarsSection(Element dataNode, FuncSpec function)
    {
        try 
        {
            function.dataInsCont = dataNode.getChildNodes().getLength();
            function.dataIns = new Instruction[function.dataInsCont];
            
            String val = dataNode.getAttribute("VarCount");
            
            if (val != null && val != "")
            {
                function.varCount = Integer.parseInt(val);
                function.vars = new Variable[function.varCount];
            }
            
            for(int i = 0; i < dataNode.getChildNodes().getLength(); i++)
            {
                try 
                {
                    Element insNode = (Element)dataNode.getChildNodes().item(i);
                    function.dataIns[ i ] = new Instruction();
                    
                    val = insNode.getAttribute("InsID");
                    
                    if (val != null && val != "")
                    {
                        function.dataIns[ i ].insID = Integer.parseInt(val);
                    }
                    
                    function.dataIns[ i ].val = insNode.getAttribute("Val");
                    function.dataIns[ i ].type = insNode.getAttribute("Type");
                    function.dataIns[ i ].name = insNode.getAttribute("Name");
                    
                    String tempStr = insNode.getAttribute("OrdNum");
                    
                    if (tempStr != null && tempStr != "")
                    {
                        function.dataIns[ i ].index = Integer.parseInt(tempStr);
                    }
                    
                    tempStr = insNode.getAttribute("Beg");
                                        
                    if (tempStr != null && tempStr != "")
                    {
                        function.dataIns[ i ].index = Integer.parseInt(tempStr);
                    }
                    
                    tempStr = insNode.getAttribute("End");
                                        
                    if (tempStr != null && tempStr != "")
                    {
                        function.dataIns[ i ].toIndex = Integer.parseInt(tempStr);
                    }
                    
                    tempStr = insNode.getAttribute("MemCount");
                                        
                    if (tempStr != null && tempStr != "")
                    {
                        function.dataIns[ i ].index = Integer.parseInt(tempStr);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();;
        }
    }
    
    public void InitCommandsSection(Element codeNode, FuncSpec function)
    {
        try 
        {
            function.codeInsCont = codeNode.getChildNodes().getLength();
            function.codeIns = new Instruction[function.codeInsCont];
            
            String val = "";
            
            for(int i = 0; i < codeNode.getChildNodes().getLength(); i++)
            {
                try 
                {
                    Element insNode = (Element)codeNode.getChildNodes().item(i);
                    function.codeIns[ i ] = new Instruction();
                    
                    val = insNode.getAttribute("InsID");
                    
                    if (val != null && val != "")
                    {
                        function.codeIns[ i ].insID = Integer.parseInt(val);
                    }
                    
                    function.codeIns[ i ].val = insNode.getAttribute("Val");
                    function.codeIns[ i ].type = insNode.getAttribute("Type");                    
                    function.codeIns[ i ].name = insNode.getAttribute("Pattern");
                    
                    String tempStr = insNode.getAttribute("Name");
                    
                    if (tempStr != null && tempStr != "")
                    {
                        function.codeIns[ i ].name = insNode.getAttribute("Name");
                    }                    
                    
                    tempStr = insNode.getAttribute("OrdNum");
                    
                    if (tempStr != null && tempStr != "")
                    {
                        function.codeIns[ i ].index = Integer.parseInt(insNode.getAttribute("OrdNum"));
                    }
                    
                    tempStr = insNode.getAttribute("Count");
                                        
                    if (tempStr != null && tempStr != "")
                    {
                        if (function.codeIns[ i ].insID != Instruction.FETCH_OC && function.codeIns[ i ].insID != Instruction.FUNC_OC)
                        {
                            function.codeIns[ i ].index = Integer.parseInt(insNode.getAttribute("Count"));
                        }
                        else
                        {
                            function.codeIns[ i ].toIndex = Integer.parseInt(insNode.getAttribute("Count"));
                        }
                    }
                                        
                    tempStr = insNode.getAttribute("ToPos");
                                        
                    if (tempStr != null && tempStr != "")
                    {
                        function.codeIns[ i ].index = Integer.parseInt(insNode.getAttribute("ToPos"));
                    }
                    
                    tempStr = insNode.getAttribute("Pos");
                                        
                    if (tempStr != null && tempStr != "")
                    {
                        int commaPos = tempStr.indexOf(",");
                        tempStr = tempStr.substring(0, commaPos);
                        function.codeIns[ i ].index = Integer.parseInt(tempStr);
                    }
                    
                    tempStr = insNode.getAttribute("FuncName");
                                        
                    if (tempStr != null && tempStr != "")
                    {
                        function.codeIns[ i ].name = tempStr;
                    }
                    
                    tempStr = insNode.getAttribute("EnvVarName");
                                        
                    if (tempStr != null && tempStr != "")
                    {
                        function.codeIns[ i ].name = tempStr;
                    }
                    
                    tempStr = insNode.getAttribute("TobId");
                                        
                    if (tempStr != null && tempStr != "")
                    {
                        function.codeIns[ i ].toIndex = Integer.parseInt(tempStr);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String ExecuteFunc(IVmContextProvider contextProvider)
    {
        this.isAborted = false;
        FuncSpec func = this.functions[ this.functions.length - 1 ];
        this.CreateVars(func, func.vars);
        this.counter = 0;
        this.state = VirtualMashine.EXECUTING;
        this.debugMode = false;
        this.ExecuteCommands(func, contextProvider, func.vars);
        
        if (!this.regPool.isEmpty())
        {
            RegVal reg = this.regPool.Pop();
            
            return VMUtils.ConvRegToString(reg);
        }
        return "";
    }
    
    public String BeginDebuggingFunc(String funcName, IVmContextProvider contextProvider)
    {
        int i = 0;
        boolean found = false;
        this.isAborted = false;
        
        for( i = 0; i < this.functions.length; i++)
        {
            FuncSpec func = this.functions[ i ];
            
            if (func.name.toLowerCase(Locale.US).equals(funcName.toLowerCase(Locale.US)))
            {
                found = true;
                break;
            }
        }
        
        if (found)
        {
            FuncSpec func = this.functions[ i ];
            this.currentFunc = func;
            this.CreateVars(func, func.vars);
            this.counter = 0;
            this.state = VirtualMashine.EXEC_BY_COMM;
            this.debugMode = true;
            this.NextStep(contextProvider);
        }
        return "";
    }
    
    public String NextStep(IVmContextProvider contextProvider)
    {
        this.ExecuteCommands(this.currentFunc, contextProvider, this.currentFunc.vars);
        return "";
    }
    
    public String ExecuteFunc(String funcName, IVmContextProvider contextProvider)
    {
        try 
        {
            this.isAborted = false;
            int i = 0;
            boolean found = false;
            
            for( i = 0; i < this.functions.length; i++)
            {
                FuncSpec func = this.functions[ i ];
                
                if (func.name.toLowerCase(Locale.US).equals(funcName.toLowerCase(Locale.US)))
                {
                    found = true;
                    break;
                }
            }
            
            if (found)
            {
                this.CreateVars(this.functions[ i ], this.functions[ i ].vars);
                this.ExecuteCommands(this.functions[ i ], contextProvider, this.functions[ i ].vars);
                
                if (!this.regPool.isEmpty())
                {
                    RegVal reg = this.regPool.Pop();
                    
                    return VMUtils.ConvRegToString(reg);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return "";
    }
    
    public String ExecuteFunc(String funcName, ArrayList varValues, IVmContextProvider contextProvider)
    {
        try 
        {
            this.isAborted = false;
            int i = 0;
            boolean found = false;
            FuncSpec func = null;
            
            for( i = 0; i < this.functions.length; i++)
            {
                func = this.functions[ i ];
                
                if (func.name.toLowerCase(Locale.US).equals(funcName.toLowerCase(Locale.US)))
                {
                    found = true;
                    break;
                }
            }
            
            if (found)
            {
                this.CreateVars(this.functions[ i ], this.functions[ i ].vars);
                
                for(int j = 0;  j < func.varCount && j < varValues.size(); j++)
                {
                    Variable var = func.vars[ j ];
                    
                    if (!var.type.equals("out"))
                    {
                        if (varValues.get(j) != null)
                        {
                            if (varValues.get(j) instanceof String)
                            {
                                VMUtils.InitValue(var.val, (String)varValues.get(j));
                            }
                            else
                            {
                                VMUtils.InitSetValue(var.val, (ArrayList)varValues.get(j));
                            }
                        }
                    }
                }
                
                this.ExecuteCommands(this.functions[ i ], contextProvider, this.functions[ i ].vars);
                
                for(int j = 0;  j < func.varCount && j < varValues.size(); j++)
                {
                    Variable var = func.vars[ j ];
                    
                    if (!var.type.equals("in"))
                    {
                        if (varValues.get(j) != null)
                        {
                            varValues.set(j, VMUtils.ConvToResultString(var.val.value, var.val.typeCode));
                        }
                    }
                }
                
                if (!this.regPool.isEmpty())
                {
                    RegVal reg = this.regPool.Pop();
                    
                    return VMUtils.ConvRegToString(reg);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return "";
    }
    
    private void CreateVars(FuncSpec func, Variable[] vars)
    {
        try 
        {
            int i = 0;
            Stack stack = new Stack();
            int currentVar = 0;
            
            while ( i < func.dataInsCont)
            {
                Instruction currIns = func.dataIns[ i ];
                
                if ( currIns.insID == Instruction.PUSH_OC )
                {
                    //push 
                    stack.push(new VarValue());
                    i = i + 1;
                    continue;
                }
                
                if ( currIns.insID == Instruction.VAL_OC )
                {
                    //val 
                    VMUtils.CreateNewValue((VarValue)stack.peek(), currIns.type);
                    i = i + 1;
                    continue;
                }
                
                if ( currIns.insID == Instruction.VAR_OC )
                {
                    //var
                    Variable var = new Variable();
                    var.name = currIns.name;
                    var.type = currIns.type.toLowerCase(Locale.US);
                    var.val = ((VarValue)stack.peek()).Clone();                
                    vars[currentVar++] = var;
                    i = i + 1;
                    continue;
                }
                
                if ( currIns.insID == Instruction.LAST_VAR_OC )
                {
                    //last var
                    Variable var = new Variable();
                    var.name = currIns.name;
                    var.type = currIns.type.toLowerCase(Locale.US);
                    var.val = ((VarValue)stack.peek());                
                    vars[currentVar++] = var;
                    stack.pop();
                    i = i + 1;
                    continue;
                }
                
                if ( currIns.insID == Instruction.MEMBER_OC )
                {
                    //last var
                    MemberVal memVal = new MemberVal();
                    VarValue val = (VarValue)stack.pop();
                    memVal.type = val.typeCode;
                    memVal.val = val.value;
                    memVal.name = currIns.name;
                    stack.push(memVal);
                    i = i + 1;
                    continue;
                }
                
                
                if ( currIns.insID == Instruction.TUPPLE_OC )
                {
                    //tupple
                    TuppleVal tVal = new TuppleVal();
                    tVal.memCount = currIns.index;
                    tVal.members = new MemberVal[tVal.memCount];
                    
                    for(int j = 0; j < tVal.memCount;j++)
                    {
                        MemberVal val = (MemberVal)stack.pop();
                        tVal.members[ tVal.memCount - j - 1 ] = val;
                    }
                    
                    VarValue newVal = new VarValue();
                    newVal.value = tVal;
                    newVal.typeCode = VarValue.TUPPLE;
                    stack.push(newVal);
                    i = i + 1;
                    continue;
                }
                
                if ( currIns.insID == Instruction.ARR_OC )
                {
                    //Array
                    ArrayVal aVal = new ArrayVal();
                    aVal.lBound = currIns.index;
                    aVal.uBound = currIns.toIndex;
                    aVal.count = aVal.uBound - aVal.lBound + 1;
                    aVal.data = new ValElem[aVal.count];
                    
                    VarValue val = (VarValue)stack.pop();
                    aVal.type = val.typeCode;
                    
                    for(int j = 0; j < aVal.count;j++)
                    {
                        aVal.data[ j ] = val.value.Clone();
                    }
                    
                    VarValue newVal = new VarValue();
                    newVal.value = aVal;
                    newVal.typeCode = VarValue.ARRAY;                
                    stack.push(newVal);
                    i = i + 1;
                    continue;
                }
                
                if ( currIns.insID == Instruction.INIT_OC )
                {
                    //tupple
                    VarValue val = (VarValue)stack.peek();
                    VMUtils.InitValue(val, currIns.val);
                    i = i + 1;
                    continue;
                }
                
                if ( currIns.insID == Instruction.LOADM_OC )
                {
                    //tupple
                    VarValue val = (VarValue)stack.peek();
                    TuppleVal tVal = (TuppleVal)val.value;
                    MemberVal mVal = (MemberVal)tVal.members[currIns.index];
                    VarValue tmp = new VarValue();
                    tmp.typeCode = mVal.type;
                    tmp.value = mVal.val;
                    stack.push(tmp);
                    i = i + 1;
                    continue;
                }
                
                if ( currIns.insID == Instruction.LOADARR_OC )
                {
                    //tupple
                    VarValue val = (VarValue)stack.peek();
                    ArrayVal aVal = (ArrayVal)val.value;
                    ValElem mVal = aVal.data[currIns.index];
                    VarValue tmp = new VarValue();
                    tmp.typeCode = aVal.type;
                    tmp.value = mVal;
                    stack.push(tmp);
                    i = i + 1;
                    continue;
                }
                
                if ( currIns.insID == Instruction.POP_OC )
                {
                    //pop
                    stack.pop();
                    i = i + 1;
                    continue;
                }
                
                if ( currIns.insID == Instruction.SET_OC )
                {
                    //Array
                    SetVal sVal = new SetVal();
                    
                    VarValue val = (VarValue)stack.pop();
                    sVal.val = val;
                    
                    VarValue newVal = new VarValue();
                    newVal.value = sVal;
                    newVal.typeCode = VarValue.SET;                
                    stack.push(newVal);
                    i = i + 1;
                    continue;
                }
                
                if ( currIns.insID == Instruction.LOADSET_OC )
                {
                    //tupple
                    VarValue val = (VarValue)stack.peek();
                    SetVal sVal= (SetVal)val.value;
                    ValElem mVal = sVal.val.value.Clone();
                    VarValue tmp = new VarValue();
                    sVal.members.add(mVal);
                    tmp.typeCode = sVal.val.typeCode;
                    tmp.value = mVal;
                    stack.push(tmp);
                    i = i + 1;
                    continue;
                }
                
                i = i + 1;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void ExecuteCommands(FuncSpec func, IVmContextProvider contextProvider, Variable[] vars)
    {
        int i = counter;
        
        while ( i < func.codeInsCont )
        {
            Instruction currIns = func.codeIns[ i ];
            int opCode = currIns.insID;
            
            if (opCode < Instruction.IMPL_OC)
            {
                if (opCode > Instruction.FUNC_OC)
                {
                    if (opCode == Instruction.JMP_OC)
                    {
                        i = currIns.index;
                        continue;
                    }
                    else
                    {
                        if (opCode == Instruction.JMPC_OC)
                        {
                            RegVal reg = this.regPool.Pop();
                            
                            if (reg.booVal == true)
                            {
                                i = currIns.index;
                                continue;
                            }
                        }
                        else
                        {
                            if (opCode == Instruction.JMPCN_OC)
                            {
                                RegVal reg = this.regPool.Pop();
                                
                                if (reg.booVal == false)
                                {
                                    i = currIns.index;
                                    continue;
                                }
                            }
                        }
                    }
                }
                else
                {
                    if (opCode == Instruction.LDC_OC)
                    {
                        RegVal reg = this.regPool.Push();
                        VMUtils.MoveToReg(reg, currIns.val, currIns.type);
                    }
                    else
                    {
                        if (opCode == Instruction.LDV_OC)
                        {
                            RegVal reg = this.regPool.Push();
                            
                            if (currIns.index < 0 ) 
                            {
                                VMUtils.MoveEnvVarToReg(reg, currIns.name, this, contextProvider, func);
                            }
                            else
                            {
                                Variable var = vars[ currIns.index ];
                                VMUtils.MoveToReg(reg, var.val.value, var.val.typeCode);
                            }
                        }
                        else
                        {
                            if (opCode == Instruction.LDVA_OC)
                            {
                                RegVal reg = this.regPool.Push();
                                Variable var = vars[ currIns.index ];
                                reg.typeCode = var.val.typeCode;
                                reg.value = var.val.value;
                                
                                if (this.debugMode)
                                {
                                    if (currIns.name != null && currIns.name != "")
                                    {
                                        this.lastChangedVarIndex = currIns.index;
                                    }
                                }
                            }
                            else
                            {
                                if (opCode == Instruction.ST_OC)
                                {
                                    RegVal reg = this.regPool.Pop();
                                    Variable var = vars[ currIns.index ];
                                    VMUtils.StoreFromReg(reg, var.val.value, var.val.typeCode);
                                    
                                    if (this.debugMode)
                                    {
                                        this.lastChangedVar = var.name;
                                        this.lastChangedVarIndex = currIns.index;
                                        this.NotifyListeners();
                                    }
                                }
                                else
                                {
                                    if (opCode == Instruction.STA_OC)
                                    {
                                        RegVal regvar = this.regPool.Pop();
                                        RegVal reg = this.regPool.Pop();
                                        VMUtils.StoreFromReg(reg, regvar.value, regvar.typeCode);
                                        
                                        if (this.debugMode)
                                        {
                                            this.NotifyListeners();
                                        }
                                    }    
                                    else
                                    {
                                        if (opCode == Instruction.FUNC_OC)
                                        {
                                            if (currIns.type.equals(PREDEFINED_FUNCTION))
                                            {
                                                this.utils.ExecuteBuiltInFunc(currIns.index, this.regPool);
                                            }
                                            else
                                            {
                                                if (currIns.type.equals(USER_DEF_FUNCTION))
                                                {
                                                    //korisnicki definisana funkcija 
                                                     int p = 0;
                                                     boolean found = false;
                                                     
                                                     for( p = 0; p < this.functions.length; p++)
                                                     {
                                                         FuncSpec newfunc = this.functions[ p ];
                                                         
                                                         if (newfunc.name.toLowerCase(Locale.US).equals(currIns.name.toLowerCase(Locale.US)))
                                                         {
                                                             found = true;
                                                             break;
                                                         }
                                                     }
                                                     
                                                     if (found)
                                                     {
                                                         int currState = this.state;
                                                         int currCounter = counter;
                                                         RegisterPool currPool = this.regPool;
                                                         this.regPool = new RegisterPool();
                                                         this.state = VirtualMashine.EXECUTING;
                                                         counter = 0;
                                                         
                                                         Variable[] newVars = new Variable[this.functions[ p ].varCount];                                                         
                                                         this.CreateVars(this.functions[ p ], newVars);                                                         
                                                         
                                                         int g = 0;
                                                         
                                                         for(int q = currIns.toIndex - 1; q >= 0; q--)
                                                         {
                                                            RegVal reg = currPool.Peek(q);
                                                            Variable var = newVars[ g++ ];
                                                            
                                                            if (var.type.equals("in"))
                                                            {
                                                                VMUtils.StoreFromReg(reg, var.val.value, var.val.typeCode);
                                                            }
                                                            else
                                                            {
                                                                if (var.type.equals("inout"))
                                                                {
                                                                    var.val.value.Copy(reg.value, reg.typeCode);                                                                    
                                                                }
                                                            }
                                                         }                                                         
                                                         //this.state = 
                                                         this.ExecuteCommands(this.functions[ p ], contextProvider, newVars);
                                                         
                                                         g = 0;
                                                         
                                                         for(int q = currIns.toIndex - 1; q >= 0; q--)
                                                         {
                                                            RegVal reg = currPool.Peek(q);
                                                            Variable var = newVars[ g++ ];
                                                            
                                                            if (var.type.equals("inout") || var.type.equals("out"))
                                                            {
                                                                reg.value.Copy(var.val.value, var.val.typeCode);                                                                    
                                                            }
                                                         }
                                                         
                                                         currPool.Pop(currIns.toIndex);
                                                         currPool.Push(this.regPool.Pop()); 
                                                         this.state = currState;
                                                         this.regPool = currPool;  
                                                         counter = currCounter;
                                                     }
                                                }
                                            }
                                        }
                                        else
                                        {
                                            if (opCode == Instruction.LDI_OC)
                                            {
                                                RegVal regIndex = this.regPool.Pop();
                                                RegVal regvalue = this.regPool.Pop();
                                                
                                                if (regvalue.typeCode == VarValue.ARRAY)
                                                {
                                                    ArrayVal aVal = (ArrayVal)regvalue.value;
                                                    
                                                    if ((int)regIndex.intVal >= aVal.lBound && (int)regIndex.intVal <= aVal.uBound )
                                                    {
                                                        ValElem val = aVal.data[ (int)regIndex.intVal - aVal.lBound ];
                                                        RegVal newVal = this.regPool.Push();
                                                        VMUtils.MoveToReg(newVal, val, aVal.type);
                                                    }
                                                    else
                                                    {
                                                        String[] errs = new String[3];  
                                                        errs[0] = Integer.toString(this.currLine);
                                                        errs[1] = func.name;
                                                        errs[2] = Integer.toString((int)regIndex.intVal);
                                                        
                                                        this.LoadErrorDescription(errs, "ARRAY_INDEX_OUT_OF_RANGE");
                                                        
                                                        ValElem val = aVal.data[ 0 ];
                                                        RegVal newVal = this.regPool.Push();
                                                        VMUtils.MoveToReg(newVal, val, aVal.type);
                                                    }
                                                }
                                                else
                                                {
                                                    SetVal sVal = (SetVal)regvalue.value;
                                                    
                                                    if ((int)regIndex.intVal >= 0 && (int)regIndex.intVal < sVal.members.size() )
                                                    {
                                                        ValElem val = (ValElem)sVal.members.get((int)regIndex.intVal);
                                                        RegVal newVal = this.regPool.Push();
                                                        VMUtils.MoveToReg(newVal, val, sVal.val.typeCode);
                                                    }
                                                    else
                                                    {                                                     
                                                        String[] errs = new String[3];  
                                                        errs[0] = Integer.toString(this.currLine);
                                                        errs[1] = func.name;
                                                        errs[2] = Integer.toString((int)regIndex.intVal);
                                                        
                                                        this.LoadErrorDescription(errs, "ARRAY_INDEX_OUT_OF_RANGE");
                                                        
                                                        ValElem val = sVal.val.value;
                                                        RegVal newVal = this.regPool.Push();
                                                        VMUtils.MoveToReg(newVal, val, sVal.val.typeCode);
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (opCode == Instruction.LDIA_OC)
                                                {
                                                    RegVal regIndex = this.regPool.Pop();
                                                    RegVal regvalue = this.regPool.Pop();
                                                    
                                                    if (regvalue.typeCode == VarValue.ARRAY)
                                                    {
                                                        ArrayVal aVal = (ArrayVal)regvalue.value;
                                                        
                                                        if ((int)regIndex.intVal >= aVal.lBound && (int)regIndex.intVal <= aVal.uBound )
                                                        {
                                                            ValElem val = aVal.data[ (int)regIndex.intVal - aVal.lBound ];
                                                            RegVal newVal = this.regPool.Push();
                                                            newVal.typeCode = aVal.type;
                                                            newVal.value = val;
                                                        }
                                                        else
                                                        {
                                                            String[] errs = new String[3];  
                                                            errs[0] = Integer.toString(this.currLine);
                                                            errs[1] = func.name;
                                                            errs[2] = Integer.toString((int)regIndex.intVal);
                                                            
                                                            this.LoadErrorDescription(errs, "ARRAY_INDEX_OUT_OF_RANGE");
                                                            
                                                            ValElem val = aVal.data[ 0 ];
                                                            RegVal newVal = this.regPool.Push();
                                                            newVal.typeCode = aVal.type;
                                                            newVal.value = val;
                                                        }
                                                    }
                                                    else
                                                    {
                                                        SetVal sVal = (SetVal)regvalue.value;
                                                        
                                                        if ((int)regIndex.intVal >= 0 && (int)regIndex.intVal < sVal.members.size() )
                                                        {
                                                            ValElem val = (ValElem)sVal.members.get((int)regIndex.intVal);
                                                            RegVal newVal = this.regPool.Push();
                                                            newVal.typeCode = sVal.val.typeCode;
                                                            newVal.value = val;
                                                        }
                                                        else
                                                        {                                                     
                                                            String[] errs = new String[3];  
                                                            errs[0] = Integer.toString(this.currLine);
                                                            errs[1] = func.name;
                                                            errs[2] = Integer.toString((int)regIndex.intVal);
                                                            
                                                            this.LoadErrorDescription(errs, "ARRAY_INDEX_OUT_OF_RANGE");
                                                            
                                                            ValElem val = sVal.val.value;
                                                            RegVal newVal = this.regPool.Push();
                                                            newVal.typeCode = sVal.val.typeCode;
                                                            newVal.value = val;
                                                        }
                                                    }
                                                }  
                                                else
                                                {
                                                    if (opCode == Instruction.LDM_OC )
                                                    {
                                                        RegVal regvalue = this.regPool.Pop();
                                                        TuppleVal tVal = (TuppleVal)regvalue.value;
                                                        MemberVal val = (MemberVal)tVal.members[ currIns.index ];
                                                        RegVal newVal = this.regPool.Push();
                                                        VMUtils.MoveToReg(newVal, val.val, val.type);
                                                    }
                                                    else
                                                    {
                                                        if (opCode == Instruction.LDMA_OC)
                                                        {
                                                            RegVal regvalue = this.regPool.Pop();
                                                            TuppleVal tVal = (TuppleVal)regvalue.value;
                                                            MemberVal val = (MemberVal)tVal.members[ currIns.index ];
                                                            RegVal newVal = this.regPool.Push();                                            
                                                            newVal.typeCode = val.type;
                                                            newVal.value = val.val;
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
            else
            {
                if (opCode > Instruction.IN_OC)
                {
                    if (opCode == Instruction.BREAKPOINT_OC)
                    {
                        if (this.state == VirtualMashine.EXEC_BY_COMM)
                        {
                            this.counter = i + 1;
                            this.currLine = currIns.index;
                            return;
                        }
                    }
                    else
                    {
                        if (opCode == Instruction.PRINT_OC)
                        {
                            RegVal reg = this.regPool.Pop();
                            this.outputBuilder.append(VMUtils.ConvRegToString(reg));
                            this.outputBuilder.append("\r\n");
                        }
                        else
                        {
                            if (opCode == Instruction.RETURN_OC)
                            {
                                i = func.codeInsCont - 1;
                                continue;
                            }
                            else
                            {
                                if (opCode == Instruction.SELECT_OC)
                                {
                                    RegVal reg = this.regPool.Pop();
                                    Variable var = vars[ currIns.index ];
                                    VMUtils.ExecSelectStatement(reg, currIns.name, var.val.value, var.val.typeCode);
                                    
                                    if (this.debugMode)
                                    {
                                        this.lastChangedVar = var.name;
                                        this.lastChangedVarIndex = currIns.index;
                                        this.NotifyListeners();
                                    }
                                }
                                else
                                {
                                    if (opCode == Instruction.SELECTA_OC)
                                    {
                                        RegVal regvar = this.regPool.Pop();
                                        RegVal reg = this.regPool.Pop();
                                        VMUtils.ExecSelectStatement(reg, currIns.name, regvar.value, regvar.typeCode);
                                        
                                        if (this.debugMode)
                                        {
                                            this.NotifyListeners();
                                        }
                                    }
                                    else
                                    {
                                        if (opCode == Instruction.SIGNAL_OC)
                                        {
                                            ArrayList params = new ArrayList();
                                            params.add(currIns.name);
                                            
                                            for(int j = 0; j < currIns.index; j++)
                                            {
                                                RegVal reg = this.regPool.Pop();
                                                params.add(VMUtils.ConvRegToString(reg));
                                            }
                                            
                                            if (contextProvider != null)
                                            {
                                                contextProvider.DispatchSignal(params);
                                            }
                                        }
                                        else
                                        {
                                            if (currIns.insID == Instruction.UPDATE_OC)
                                            {
                                                ArrayList params = new ArrayList();
                                                params.add(Integer.toString(currIns.toIndex));
                                                params.add(currIns.name);
                                                
                                                if (currIns.index > 0)
                                                {
                                                    for(int j = 0; j < currIns.index; j++)
                                                    {
                                                        RegVal reg = this.regPool.Pop();
                                                        params.add(2, VMUtils.ConvRegToString(reg));
                                                    }
                                                }
                                                
                                                if (contextProvider != null)
                                                {
                                                    contextProvider.ExecuteFunction("update", params);
                                                }
                                            }
                                            else
                                            {
                                                if (currIns.insID == Instruction.FETCH_OC)
                                                {
                                                    Variable var = vars[ currIns.index ];
                                                    
                                                    if (var.type != null && var.val.typeCode == VarValue.ITERATOR)
                                                    {
                                                        IteratorVal iter = (IteratorVal)var.val.value;
                                                        
                                                        try 
                                                        {
                                                            
                                                            ArrayList regs = new ArrayList();
                                                        
                                                            for(int j = 0; j < currIns.toIndex; j++)
                                                            {
                                                                RegVal reg = this.regPool.Pop();   
                                                                regs.add(0,reg);
                                                            }
                                                            
                                                            iter.hasNext = iter.rs.next();
                                                            
                                                            if (iter.hasNext)
                                                            {
                                                                for(int j = 0; j < regs.size(); j++)                                                        
                                                                {
                                                                    RegVal reg = (RegVal)regs.get(j);
                                                                    VMUtils.FetchValue(iter, reg, j+1, this);
                                                                }                                                                
                                                            }
                                                        }
                                                        catch(Exception e)
                                                        {
                                                            e.printStackTrace();
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
                else
                {
                    if (opCode <= Instruction.CONCAT_OC)
                    {
                        //BINARNI OPERATOR
                        RegVal reg1  = this.regPool.Pop();
                        RegVal reg2  = this.regPool.Peek();
                        
                        if (opCode <= Instruction.AND_OC)
                        {
                            //LOGICKI OPERATOR
                            VMUtils.ExecLogOp(opCode, reg2, reg1);
                        }
                        else
                        {
                            if (opCode <= Instruction.GE_OC)
                            {
                                //OPERATOR POREDJENJA I JEDNAKOSTI
                                VMUtils.ExecCompOp(opCode, reg2, reg1);
                            }
                            else
                            {
                                if (opCode <= Instruction.MOD_OC)
                                {
                                    //ARITMETICKI OPERATOR
                                     VMUtils.ExecArithmeticOp(opCode, reg2, reg1);
                                }
                                else
                                {
                                    if (opCode <= Instruction.UNION_OC)
                                    {
                                        VMUtils.ExecSetOp(opCode, reg2, reg1);
                                    }
                                    else
                                    {
                                        //CONCAT
                                        VMUtils.ExecConcatOp(reg2, reg1);
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        if (opCode == Instruction.NOT_OC)
                        {
                            RegVal reg2  = this.regPool.Peek();
                            reg2.booVal = !reg2.booVal;
                        }
                        else
                        {
                            if (opCode == Instruction.UNMINUS_OC)
                            {
                                RegVal reg2  = this.regPool.Peek();
                                
                                if (reg2.typeCode == VarValue.INT)
                                {
                                    reg2.intVal = (-1) * reg2.intVal;
                                }
                                else
                                {
                                    reg2.realVal = (-1) * reg2.realVal;
                                }
                            }
                            else
                            {
                                if (opCode == Instruction.LIKE_OC)
                                {
                                    RegVal compVal = this.regPool.Pop();
                                    String pattern = currIns.name;
                                    boolean isMatch = false;
                                    
                                    if (compVal.typeCode == VarValue.STRING)
                                    {
                                        isMatch = VMUtils.like(((StringValue)compVal.value).val, pattern.substring(1, pattern.length() - 1));
                                    }
                                    
                                    RegVal resVal = this.regPool.Push();
                                    resVal.typeCode = VarValue.BOOL;
                                    resVal.booVal = isMatch;
                                }
                                else
                                {
                                    if (opCode == Instruction.IN_OC)
                                    {
                                        if (currIns.index > 0)
                                        {
                                            ArrayList params = new ArrayList();
                                            for(int j = 0; j < currIns.index; j++)
                                            {
                                                params.add(this.regPool.Pop());
                                            }
                                            
                                            RegVal inVal = (RegVal)params.get(params.size() - 1);
                                            boolean isIn = false;
                                            
                                            for(int j = 0; j < currIns.index-1; j++)
                                            {
                                                RegVal compVal = (RegVal)params.get(j);    
                                                
                                                VMUtils.ExecEQOp(compVal, inVal);
                                                
                                                if (compVal.booVal)
                                                {
                                                    isIn = true;
                                                    break;
                                                }
                                            }
                                            
                                            RegVal newVal = this.regPool.Push();
                                            newVal.booVal = isIn;
                                            newVal.typeCode = VarValue.BOOL;                                        
                                        }
                                        else
                                        {
                                            RegVal setRegVal = this.regPool.Pop();
                                            RegVal inVal = this.regPool.Pop();
                                            
                                            SetVal setVal = (SetVal)setRegVal.value;
                                            
                                            boolean inSet = false;
                                            RegVal resVal = new RegVal();
                                            
                                            for(int j= 0; j < setVal.members.size(); j++)
                                            {
                                                VMUtils.MoveToReg(resVal, (ValElem)(setVal.members.get(j)), setVal.val.typeCode);
                                                VMUtils.ExecEQOp(resVal,inVal);
                                                
                                                if (resVal.booVal)
                                                {
                                                    inSet = true;
                                                    break;
                                                }
                                            }
                                            RegVal solVal = this.regPool.Push();
                                            solVal.typeCode = VarValue.BOOL;
                                            solVal.booVal = inSet;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            i = i + 1;
        }
        
        this.state = VirtualMashine.FINAL;
    }
    
    public int GetInitialVarValue(VarRef vRef)
    {
        if (this.currentFunc == null)
        {
            return 0;
        }
        
        int pos = -1;
        
        for(int i = 0; i < this.currentFunc.vars.length; i++)
        {
            Variable var = this.currentFunc.vars[ i ];
            
            if (var.name.toLowerCase(Locale.US).equals(vRef.varName.toLowerCase(Locale.US)))
            {
                pos = i;
                break;
            }
        }
        
        if (pos == -1)
        {
            vRef.varIndex = -1;
            vRef.value = null;
            vRef.varValue = "Unregognized  variable";
            return 1; //unknown var
        }
        
        vRef.varIndex = pos;
        Variable var = this.currentFunc.vars[ pos ];
        
        if (vRef.mems.size() == 0)
        {
            vRef.value = var.val.value;
            vRef.varValue = var.val.value.ToString();
        }
        else
        {
            int typeCode = var.val.typeCode;
            ValElem val = var.val.value;
            
            for(int i = 0; i < vRef.mems.size(); i++)
            {
                VarMemRef mem = (VarMemRef)vRef.mems.get(i);
                
                if (mem.arrIndex != -1)
                {
                    //arr index
                    if (typeCode != VarValue.ARRAY)
                    {
                        vRef.varIndex = -1;
                        vRef.varValue = "Array variable expected";
                        vRef.value = null;
                        return 2; //not an array var
                    }
                    ArrayVal aVal = (ArrayVal)val;
                    
                    if (mem.arrIndex < aVal.lBound || mem.arrIndex > aVal.lBound )
                    {
                        vRef.varIndex = -1;
                        vRef.varValue = "Array index is out of range";
                        vRef.value = null;
                        return 3; //index out of range
                    }
                    
                    val = aVal.data[ mem.arrIndex - aVal.lBound ];
                    typeCode = aVal.type;
                }
                else
                {
                    if (typeCode != VarValue.TUPPLE)
                    {
                        vRef.varValue = "Tupple variable expected";
                        vRef.value = null;
                        return 4; //not an array var
                    }
                    TuppleVal tVal = (TuppleVal)val;
                    
                    int mPos = -1;
                    
                    for(int j = 0; j < tVal.members.length; j++)
                    {
                        MemberVal memval = (MemberVal )tVal.members[ i ];
                        
                        if (memval.name.toLowerCase(Locale.US).equals(mem.memName.toLowerCase(Locale.US)))
                        {
                            mPos = j;
                            break;
                        }
                    }
                    
                    if (mPos == -1)
                    {
                        vRef.varValue = "Tupple member is not recognized";
                        vRef.value = null;
                        return 5; //not an array var
                    }
                    
                    MemberVal memval = (MemberVal )tVal.members[ mPos ];
                    val = memval.val;
                    typeCode = memval.type;
                }
            }
            vRef.value = val;
        }
        vRef.varValue = vRef.value.ToString();
        return 0;
    }
    
    public int GetVarValue(VarRef vRef)
    {
        try 
        {
            if (this.currentFunc == null)
            {
                return 0;
            }
            
            if (vRef.varIndex == -1)
            {
                return -1; //unknown var
            }
            
            Variable var = this.currentFunc.vars[ vRef.varIndex ];
            
            if (vRef.mems.size() == 0)
            {
                vRef.varValue = var.val.value.ToString();
            }
            return 0;
        }
        catch(Exception e)
        {
        }
        return 0;
    }
}

