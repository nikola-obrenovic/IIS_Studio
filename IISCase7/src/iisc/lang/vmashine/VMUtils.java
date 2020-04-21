package iisc.lang.vmashine;

import java.sql.ResultSet;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class VMUtils 
{
    public static int ABS_CODE = 0;
    public static int SUBSTRING_CODE = 1;
    public static int LEN_CODE = 2;
    public static int CURRENTTIME_CODE = 3;
    public static int CURRENTDATE_CODE = 4;
    public static int EXECUTE_QUERY_CODE = 5;
    public static int HAS_NEXT_CODE = 6;
    public static int EXEC_NON_QUERY_CODE = 7;
    public static int GET_STRING_CODE = 8;
    public static int GET_INT_CODE = 9;
    public static int GET_BOOLEAN_CODE = 10;
    public static int GET_NUMBER_CODE = 11;
    public static int GET_TIME_CODE = 12;
    public static int GET_DATE_CODE = 13;
    public static int TO_STRING_CODE = 14;
    public static int GET_SIZE_CODE = 15;
    public static int GET_ITEM_CODE = 16;
    public static int SHOW_INFO_MESSAGE_CODE = 17;
    public static int SHOW_WARNING_MESSAGE_CODE = 18;
    public static int SHOW_ERROR_MESSAGE_CODE = 19;
    public static int SHOW_YESNO_MESSAGE_CODE = 20;
    public static int SHOW_INPUT_MESSAGE_CODE = 21;
    public static int COS_CODE = 22;
    public static int SIN_CODE = 23;
    public static int FLOOR_CODE = 24;
    public static int LOG_CODE = 25;
    public static int MAX_CODE = 26;
    public static int MIN_CODE = 27;
    public static int POW_CODE = 28;
    public static int RANDOM_CODE = 29;
    public static int ROUND_CODE = 30;
    public static int SINH_CODE = 31;
    public static int COSH_CODE = 32;
    public static int SQRT_CODE = 33;
    public static int TAN_CODE = 34;
    public static int TANH_CODE = 35;
    public static int ACOS_CODE = 36;
    public static int ASIN_CODE = 37;
    public static int ATAN_CODE = 38;
    public static int STARTSWITH_CODE = 39;
    public static int ENDSSWITH_CODE = 40;
    public static int INDEXOF_CODE = 41;
    public static int LASTINDEXOF_CODE = 42;
    public static int TOLOWER_CODE = 43;
    public static int TOUPPER_CODE = 44;
    public static int DAY_CODE = 45;
    public static int MONTH_CODE = 46;
    public static int YEAR_CODE = 47;
    public static int HOUR_CODE = 48;
    public static int MINUTE_CODE = 49;
    public static int SECOND_CODE = 50;
    
    public VirtualMashine vMashine = null;
    
    public static void CreateNewValue(VarValue val, String type)
    {
        if (type.equals("REAL"))
        {
             val.value = new RealValue();
             val.typeCode = VarValue.REAL;
             return;
        }
        
        if (type.equals("INT"))
        {
             val.value = new IntValue();
             val.typeCode = VarValue.INT;
             return;
        }
        
        if (type.equals("STRING"))
        {
             val.value = new StringValue();
             val.typeCode = VarValue.STRING;
             return;
        }
        
        if (type.equals("DATE"))
        {
             val.value = new DateVal();
             val.typeCode = VarValue.DATE;
             return;
        }
        
        if (type.equals("TIME"))
        {
             val.value = new TimeVal();
             val.typeCode = VarValue.TIME;
             return;
        }
        
        if (type.equals("BOOL"))
        {
             val.value = new BoolValue();
             val.typeCode = VarValue.BOOL;
             return;
        }
        
        if (type.equals("ITERATOR"))
        {
             val.value = new IteratorVal();
             val.typeCode = VarValue.ITERATOR;
             return;
        }
    }
    
    public static void InitSetValue(VarValue val, ArrayList value)
    {
        if (val.typeCode == VarValue.SET)
        {
            SetVal sVal = (SetVal)val.value;
            sVal.members.clear();
            
            for(int i = 0; i < value.size(); i++)
            {
                VarValue elem = sVal.val.Clone();
                String strVal = (String)value.get(i);
                
                if (strVal != null)
                {
                    InitValue(elem, strVal);
                    sVal.members.add(elem.value);
                }
            }
        }
    }
    
    public static void InitValue(VarValue val, String value)
    {
        try 
        {
            if (value == null || value.equals(""))
            {
                return;
            }
            
            if (val.typeCode == VarValue.REAL)
            {
                RealValue rVal = (RealValue)val.value;
                rVal.val = Double.parseDouble(value);
                return;
            }
            
            if (val.typeCode == VarValue.INT)
            {
                IntValue iVal = (IntValue)val.value;
                
                if (value.indexOf('.') != -1)
                {
                    iVal.val = (int)Double.parseDouble(value);
                }
                else
                {
                    iVal.val = Integer.parseInt(value);
                }
                return;
            }
            
            if (val.typeCode == VarValue.STRING)
            {
                StringValue iVal = (StringValue)val.value;
                if (value.length() < 3)
                {
                    iVal.val = "";
                }
                {
                    iVal.val = VMUtils.ConvertString(value);
                }
                return;
            }
            
            if (val.typeCode == VarValue.DATE)
            {   
                DateVal dVal = (DateVal)val.value;
                try 
                {
                    dVal.val = DateVal.sdf.parse(value);
                }
                catch(Exception e)
                {}
                return;
            }
            
            if (val.typeCode == VarValue.TIME)
            {   
                TimeVal tVal = (TimeVal)val.value;
                try 
                {
                    tVal.val = TimeVal.sdf.parse(value);
                }
                catch(Exception e)
                {}
                return;
            }
            
            if (val.typeCode == VarValue.BOOL)
            {
                BoolValue bVal = (BoolValue)val.value;
                
                if (value.toLowerCase(Locale.US).equals("true"))
                {
                    bVal.val = true;
                }
                else
                {
                    bVal.val = false;
                }
                return;
            }
        }
        catch(Exception e)
        {
            //e.printStackTrace();
        }
    }
    
    public static String ConvertString(String value)
    {
        try 
        {
            int len = value.length() - 1;
            char cVal[] = new char[len];
            int i = 1;
            int j = 0;
            
            while(i<len) 
            {
                if (value.charAt(i) != '\\')    
                {
                    cVal[ j ] = value.charAt( i );
                    j = j + 1;
                }
                else
                {
                    i = i + 1;
                    cVal[ j ] = value.charAt( i );
                    j = j + 1;
                }
                i = i + 1;
            }
            return new String(cVal, 0, j);
        }
        catch(Exception e)
        {}
        return "";
    }
    //move global
    public static void MoveEnvVarToReg(RegVal reg, String name, VirtualMashine vm, IVmContextProvider contextProvider, FuncSpec func)
    {
        String value = "";
        
        if (contextProvider != null)
        {
            value = contextProvider.GetEnvVar(name);
        }
        
        VarValue val = null;
        
        for(int i = func.varCount-1; i>-1; i--)
        {
            if (func.vars[i].name.toUpperCase(Locale.US).equalsIgnoreCase(name.toUpperCase(Locale.US)))
            {
                val = func.vars[i].val;
                break;
            }
        }
        
        if (val != null && value != null && !value.equals(""))
        {
            InitValue(val, value);
        }
        
        if (val != null)
        {
            VMUtils.MoveToReg(reg, val.value, val.typeCode);
        }
    }
    //LDV
    public static void MoveToReg(RegVal reg, ValElem val, int typeCode)
    {
        reg.typeCode = typeCode;
        
        if (typeCode == VarValue.REAL)
        {
            reg.realVal = ((RealValue)val).val;
            return;
        }
        
        if (typeCode == VarValue.INT)
        {
            reg.intVal = ((IntValue)val).val;
            return;
        }
        
        if (typeCode == VarValue.STRING)
        {
            reg.value = new StringValue(((StringValue)val).val);
            return;
        }
        
        if (typeCode == VarValue.DATE)
        {
            reg.value = val;
            return;
        }
        
        if (typeCode == VarValue.TIME)
        {
            reg.value = val;
            return;
        }
        
        if (typeCode == VarValue.BOOL)
        {
            reg.booVal = ((BoolValue)val).val;
            return;
        }
        
        if (typeCode == VarValue.SET)
        {
            SetVal setval = (SetVal)val;
            reg.value = setval.Clone();
            reg.typeCode = VarValue.SET;
            return;
        }
        reg.typeCode = typeCode;
        reg.value = val;
    }
    
    //LDC
    public static void MoveToReg(RegVal reg, String value, String typeCode)
    {
        if (typeCode.equalsIgnoreCase("REAL"))
        {
            reg.realVal = Double.parseDouble(value);
            reg.typeCode = VarValue.REAL;
            return;
        }
        
        if (typeCode.equalsIgnoreCase("INT"))
        {
            reg.intVal = Integer.parseInt(value);
            reg.typeCode = VarValue.INT;
            return;
        }
        
        if (typeCode.equalsIgnoreCase("STRING"))
        {
            reg.typeCode = VarValue.STRING;
            StringValue tempVal = null;
            
            if (value.length() < 2)
            {
                tempVal = new StringValue("");
            }
            else
            {
                tempVal = new StringValue(VMUtils.ConvertString(value));
            }
            reg.value = tempVal;
            return;
        }
        
        if (typeCode.equalsIgnoreCase("DATE"))
        {   
            DateVal dVal = new DateVal();
            
            reg.typeCode = VarValue.DATE;
            reg.value = dVal;
            
            try 
            {
                dVal.val = DateVal.sdf.parse(value);
            }
            catch(Exception e)
            {}
            
            return;
        }
        
        if (typeCode.equalsIgnoreCase("TIME"))
        {   
            TimeVal tVal = new TimeVal();
            
            reg.typeCode = VarValue.TIME;
            reg.value = tVal;
            
            try 
            {
                tVal.val = TimeVal.sdf.parse(value);
            }
            catch(Exception e)
            {}
            
            return;
        }
        
        if (typeCode.equalsIgnoreCase("BOOL"))
        {
            reg.typeCode = VarValue.BOOL;
            
            if (value.toLowerCase(Locale.US).equals("true"))
            {
                reg.booVal = true;
            }
            else
            {
                reg.booVal = false;
            }
            return;
        }
    }
    
    //ST
    public static void StoreFromReg(RegVal reg, ValElem val, int typeCode)
    {
        if (typeCode == VarValue.REAL)
        {
            if (reg.typeCode == VarValue.REAL)
            {
                ((RealValue)val).val = reg.realVal;
            }
            else
            {
                ((RealValue)val).val = reg.intVal;
            }
            return;
        }
        
        if (typeCode == VarValue.INT)
        {
            if (reg.typeCode == VarValue.REAL)
            {
                ((IntValue)val).val = (long)reg.realVal;
            }
            else
            {
                ((IntValue)val).val = reg.intVal;
            }            
            return;
        }
        
        if (typeCode == VarValue.STRING)
        {
            StringValue temp = (StringValue)reg.value;
            ((StringValue)val).val = temp.val;
            return;
        }
        
        if (typeCode == VarValue.DATE)
        {
            ((DateVal)val).val = ((DateVal)reg.value).val;
            return;
        }
        
        if (typeCode == VarValue.TIME)
        {
            ((TimeVal)val).val = ((TimeVal)reg.value).val;
            return;
        }
        
        if (typeCode == VarValue.BOOL)
        {
            ((BoolValue)val).val = reg.booVal;
            return;
        }
        
        val.Copy(reg.value, reg.typeCode);
    }
    
    public static String ConvRegToString(RegVal reg)
    {
        if (reg.typeCode == VarValue.REAL)
        {
            String result = Double.toString(reg.realVal);
            
            if(result.endsWith(".0"))
            {
                result = result.substring(0, result.length() - 2);
            }
            return result;
        }
        
        if (reg.typeCode == VarValue.INT)
        {
            return Long.toString(reg.intVal);            
        }
        
        if (reg.typeCode == VarValue.STRING)
        {
            return reg.value.ToString();
        }
        
        if (reg.typeCode == VarValue.DATE)
        {
            return reg.value.ToString();
        }
        
        if (reg.typeCode == VarValue.TIME)
        {
             return reg.value.ToString();
        }
        
        if (reg.typeCode == VarValue.BOOL)
        {
            if (reg.booVal)
            {
                return "True";
            }
            else
            {
                return "False";
            }
        }
        
        return reg.value.ToString();
    }
    
    public static Object ConvToResultString(ValElem elem, int typeCode)
    {
        if (typeCode != VarValue.SET && typeCode != VarValue.TUPPLE)
        {
            return elem.ToString();
        }
        
        ArrayList result = new ArrayList();
        
        if (typeCode == VarValue.SET)
        {
            SetVal sVal = (SetVal)elem;
            
            for(int i = 0; i < sVal.members.size(); i++)
            {
                result.add(ConvToResultString((ValElem)sVal.members.get(i), sVal.val.typeCode));
            }
        }
        
        if (typeCode == VarValue.TUPPLE)
        {
            TuppleVal tVal = (TuppleVal)elem;
            
            for(int i = 0; i < tVal.memCount; i++)
            {
                result.add(ConvToResultString(((MemberVal)tVal.members[i]).val, ((MemberVal)tVal.members[i]).type));
            }
        }
        
        return result;
    }
    
    public static void ExecLogOp(int opCode, RegVal reg1, RegVal reg2)
    {
        if (opCode == Instruction.AND_OC)
        {
            reg1.typeCode = VarValue.BOOL;
            reg1.booVal = reg1.booVal && reg2.booVal;
        }
        else
        {
            if (opCode == Instruction.OR_OC)
            {
                reg1.typeCode = VarValue.BOOL;
                reg1.booVal = reg1.booVal || reg2.booVal;
            }
            else
            {
                if (opCode == Instruction.IMPL_OC)
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = !reg1.booVal || reg2.booVal;
                }
                else
                {
                    if (opCode == Instruction.XOR_OC)
                    {
                        reg1.typeCode = VarValue.BOOL;
                        reg1.booVal = reg1.booVal ^ reg2.booVal;
                    }
                }
            }
        }
    }
    
    public static void ExecCompOp(int opCode, RegVal reg1, RegVal reg2)
    {
        if (opCode == Instruction.EQ_OC)
        {
            ExecEQOp(reg1, reg2);
        }
        else
        {
            if (opCode == Instruction.NE_OC)
            {
                ExecNEOp(reg1, reg2);
            }
            else
            {
                if (opCode == Instruction.LT_OC)
                {
                    ExecLTOp(reg1, reg2);
                }
                else
                {
                    if (opCode == Instruction.LE_OC)
                    {
                        ExecLEOp(reg1, reg2);
                    }
                    else
                    {
                        if (opCode == Instruction.GT_OC)
                        {
                            ExecGTOp(reg1, reg2);
                        }
                        else
                        {
                            if (opCode == Instruction.GE_OC)
                            {
                                ExecGEOp(reg1, reg2);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static void ExecEQOp(RegVal reg1, RegVal reg2)
    {
        if (reg1.typeCode == VarValue.REAL)
        {
            if (reg2.typeCode == VarValue.REAL)
            {
                reg1.typeCode = VarValue.BOOL;
                reg1.booVal = reg1.realVal == reg2.realVal;
            }
            else
            {
                reg1.typeCode = VarValue.BOOL;
                reg1.booVal = reg1.realVal == reg2.intVal;
            }
        }
        else
        {
            if (reg1.typeCode == VarValue.INT)
            {
                if (reg2.typeCode == VarValue.REAL)
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = reg1.intVal == reg2.realVal;
                }
                else
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = reg1.intVal == reg2.intVal;
                }
            }
            else
            {
                if (reg1.typeCode == VarValue.STRING)
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = ((StringValue)reg1.value).val.compareTo(((StringValue)reg2.value).val) == 0 ;
                }
                else
                {
                    if (reg1.typeCode == VarValue.DATE)
                    {
                        reg1.typeCode = VarValue.BOOL;
                        reg1.booVal =  ((DateVal)reg1.value).val.compareTo(((DateVal)reg2.value).val) == 0;
                    }
                    else
                    {
                        if (reg1.typeCode == VarValue.TIME)
                        {
                            reg1.typeCode = VarValue.BOOL;
                            reg1.booVal =  ((TimeVal)reg1.value).val.compareTo(((TimeVal)reg2.value).val) == 0;
                        }
                        else
                        {
                            if (reg1.typeCode == VarValue.BOOL)
                            {
                                reg1.typeCode = VarValue.BOOL;
                                reg1.booVal = reg1.booVal == reg2.booVal;
                            }
                            else
                            {
                                if (reg1.typeCode == VarValue.ARRAY || reg1.typeCode == VarValue.TUPPLE || reg1.typeCode == VarValue.SET)
                                {
                                    reg1.typeCode = VarValue.BOOL;
                                    reg1.booVal =  reg1.value.Compare(reg2.value, reg2.typeCode) == 0;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static void ExecNEOp(RegVal reg1, RegVal reg2)
    {
        if (reg1.typeCode == VarValue.REAL)
        {
            if (reg2.typeCode == VarValue.REAL)
            {
                reg1.typeCode = VarValue.BOOL;
                reg1.booVal = reg1.realVal != reg2.realVal;
            }
            else
            {
                reg1.typeCode = VarValue.BOOL;
                reg1.booVal = reg1.realVal != reg2.intVal;
            }
        }
        else
        {
            if (reg1.typeCode == VarValue.INT)
            {
                if (reg2.typeCode == VarValue.REAL)
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = reg1.intVal != reg2.realVal;
                }
                else
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = reg1.intVal != reg2.intVal;
                }
            }
            else
            {
                if (reg1.typeCode == VarValue.STRING)
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = ((StringValue)reg1.value).val.compareTo(((StringValue)reg2.value).val) != 0 ;
                }
                else
                {
                    if (reg1.typeCode == VarValue.DATE)
                    {
                        reg1.typeCode = VarValue.BOOL;
                        reg1.booVal =  ((DateVal)reg1.value).val.compareTo(((DateVal)reg2.value).val) != 0;
                    }
                    else
                    {
                        if (reg1.typeCode == VarValue.TIME)
                        {
                            reg1.typeCode = VarValue.BOOL;
                            reg1.booVal =  ((TimeVal)reg1.value).val.compareTo(((TimeVal)reg2.value).val) != 0;
                        }
                        else
                        {
                            if (reg1.typeCode == VarValue.BOOL)
                            {
                                reg1.typeCode = VarValue.BOOL;
                                reg1.booVal = reg1.booVal != reg2.booVal;
                           }
                            else
                            {
                                if (reg1.typeCode == VarValue.ARRAY || reg1.typeCode == VarValue.TUPPLE || reg1.typeCode == VarValue.SET)
                                {
                                    reg1.typeCode = VarValue.BOOL;
                                    reg1.booVal =  reg1.value.Compare(reg2.value, reg2.typeCode) != 0;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static void ExecLTOp(RegVal reg1, RegVal reg2)
    {
        if (reg1.typeCode == VarValue.REAL)
        {
            if (reg2.typeCode == VarValue.REAL)
            {
                reg1.typeCode = VarValue.BOOL;
                reg1.booVal = reg1.realVal < reg2.realVal;
            }
            else
            {
                reg1.typeCode = VarValue.BOOL;
                reg1.booVal = reg1.realVal < reg2.intVal;
            }
        }
        else
        {
            if (reg1.typeCode == VarValue.INT)
            {
                if (reg2.typeCode == VarValue.REAL)
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = reg1.intVal < reg2.realVal;
                }
                else
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = reg1.intVal < reg2.intVal;
                }
            }
            else
            {
                if (reg1.typeCode == VarValue.STRING)
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = ((StringValue)reg1.value).val.compareTo(((StringValue)reg2.value).val) < 0 ;
                }
                else
                {
                    if (reg1.typeCode == VarValue.DATE)
                    {
                        reg1.typeCode = VarValue.BOOL;
                        reg1.booVal = ((DateVal)reg1.value).val.before(((DateVal)reg2.value).val);
                    }
                    else
                    {
                        if (reg1.typeCode == VarValue.TIME)
                        {
                            reg1.typeCode = VarValue.BOOL;
                            reg1.booVal = ((TimeVal)reg1.value).val.before(((TimeVal)reg2.value).val);
                        }
                    }
                }
            }
        }
    }
    
    public static void ExecLEOp(RegVal reg1, RegVal reg2)
    {
        if (reg1.typeCode == VarValue.REAL)
        {
            if (reg2.typeCode == VarValue.REAL)
            {
                reg1.typeCode = VarValue.BOOL;
                reg1.booVal = reg1.realVal <= reg2.realVal;
            }
            else
            {
                reg1.typeCode = VarValue.BOOL;
                reg1.booVal = reg1.realVal <= reg2.intVal;
            }
        }
        else
        {
            if (reg1.typeCode == VarValue.INT)
            {
                if (reg2.typeCode == VarValue.REAL)
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = reg1.intVal <= reg2.realVal;
                }
                else
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = reg1.intVal <= reg2.intVal;
                }
            }
            else
            {
                if (reg1.typeCode == VarValue.STRING)
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = ((StringValue)reg1.value).val.compareTo(((StringValue)reg2.value).val) <= 0 ;
                }
                else
                {
                    if (reg1.typeCode == VarValue.DATE)
                    {
                        reg1.typeCode = VarValue.BOOL;
                        reg1.booVal =  ((DateVal)reg1.value).val.compareTo(((DateVal)reg2.value).val) <= 0;
                    }
                    else
                    {
                        if (reg1.typeCode == VarValue.TIME)
                        {
                            reg1.typeCode = VarValue.BOOL;
                            reg1.booVal =  ((TimeVal)reg1.value).val.compareTo(((TimeVal)reg2.value).val) <= 0;
                        }
                    }
                }
            }
        }
    }
    
    public static void ExecGTOp(RegVal reg1, RegVal reg2)
    {
        if (reg1.typeCode == VarValue.REAL)
        {
            if (reg2.typeCode == VarValue.REAL)
            {
                reg1.typeCode = VarValue.BOOL;
                reg1.booVal = reg1.realVal > reg2.realVal;
            }
            else
            {
                reg1.typeCode = VarValue.BOOL;
                reg1.booVal = reg1.realVal > reg2.intVal;
            }
        }
        else
        {
            if (reg1.typeCode == VarValue.INT)
            {
                if (reg2.typeCode == VarValue.REAL)
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = reg1.intVal > reg2.realVal;
                }
                else
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = reg1.intVal > reg2.intVal;
                }
            }
            else
            {
                if (reg1.typeCode == VarValue.STRING)
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = ((StringValue)reg1.value).val.compareTo(((StringValue)reg2.value).val) > 0 ;
                }
                else
                {
                    if (reg1.typeCode == VarValue.DATE)
                    {
                        reg1.typeCode = VarValue.BOOL;
                        reg1.booVal = ((DateVal)reg1.value).val.after(((DateVal)reg2.value).val);
                    }
                    else
                    {
                        if (reg1.typeCode == VarValue.TIME)
                        {
                            reg1.typeCode = VarValue.BOOL;
                            reg1.booVal = ((TimeVal)reg1.value).val.after(((TimeVal)reg2.value).val);
                        }
                    }
                }
            }
        }
    }
    
    public static void ExecGEOp(RegVal reg1, RegVal reg2)
    {
        if (reg1.typeCode == VarValue.REAL)
        {
            if (reg2.typeCode == VarValue.REAL)
            {
                reg1.typeCode = VarValue.BOOL;
                reg1.booVal = reg1.realVal >= reg2.realVal;
            }
            else
            {
                reg1.typeCode = VarValue.BOOL;
                reg1.booVal = reg1.realVal >= reg2.intVal;
            }
        }
        else
        {
            if (reg1.typeCode == VarValue.INT)
            {
                if (reg2.typeCode == VarValue.REAL)
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = reg1.intVal >= reg2.realVal;
                }
                else
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = reg1.intVal >= reg2.intVal;
                }
            }
            else
            {
                if (reg1.typeCode == VarValue.STRING)
                {
                    reg1.typeCode = VarValue.BOOL;
                    reg1.booVal = ((StringValue)reg1.value).val.compareTo(((StringValue)reg2.value).val) >= 0 ;
                }
                else
                {
                    if (reg1.typeCode == VarValue.DATE)
                    {
                        reg1.typeCode = VarValue.BOOL;
                        reg1.booVal =  ((DateVal)reg1.value).val.compareTo(((DateVal)reg2.value).val) >= 0;
                    }
                    else
                    {
                        if (reg1.typeCode == VarValue.TIME)
                        {
                            reg1.typeCode = VarValue.BOOL;
                            reg1.booVal =  ((TimeVal)reg1.value).val.compareTo(((TimeVal)reg2.value).val) >= 0;
                        }
                    }
                }
            }
        }
    }
    
    public static void ExecArithmeticOp(int opCode, RegVal reg1, RegVal reg2)
    {
        if (opCode == Instruction.ADD_OC)
        {
            ExecAddOp(reg1, reg2);
        }
        else
        {
            if (opCode == Instruction.SUB_OC)
            {
                ExecSubOp(reg1, reg2);
            }
            else
            {
                if (opCode == Instruction.MUL_OC)
                {
                    ExecMulOp(reg1, reg2);
                }
                else
                {
                    if (opCode == Instruction.DIV_OC)
                    {
                        ExecDivOp(reg1, reg2);
                    }
                    else
                    {
                        if (opCode == Instruction.MOD_OC)
                        {
                            ExecModOp(reg1, reg2);
                        }
                        else
                        {
                            if (opCode == Instruction.CONCAT_OC)
                            {
                                ExecConcatOp(reg1, reg2);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static void ExecAddOp(RegVal reg1, RegVal reg2)
    {
        if (reg1.typeCode == VarValue.REAL)
        {
            if (reg2.typeCode == VarValue.REAL)
            {
                reg1.typeCode = VarValue.REAL;
                reg1.realVal = reg1.realVal + reg2.realVal;
            }
            else
            {
                reg1.typeCode = VarValue.REAL;
                reg1.realVal = reg1.realVal + reg2.intVal;
            }
        }
        else
        {
            if (reg1.typeCode == VarValue.INT)
            {
                if (reg2.typeCode == VarValue.REAL)
                {
                    reg1.typeCode = VarValue.REAL;
                    reg1.realVal = reg1.intVal + reg2.realVal;
                }
                else
                {
                    reg1.typeCode = VarValue.INT;
                    reg1.intVal = reg1.intVal + reg2.intVal;
                }
            }
            else
            {
                if (reg1.typeCode == VarValue.STRING)
                {
                    reg1.typeCode = VarValue.STRING;
                    ((StringValue)reg1.value).val += ((StringValue)reg2.value).val;
                }
            }
        }
    }
    
    public static void ExecSubOp(RegVal reg1, RegVal reg2)
    {
        if (reg1.typeCode == VarValue.REAL)
        {
            if (reg2.typeCode == VarValue.REAL)
            {
                reg1.typeCode = VarValue.REAL;
                reg1.realVal = reg1.realVal - reg2.realVal;
            }
            else
            {
                reg1.typeCode = VarValue.REAL;
                reg1.realVal = reg1.realVal - reg2.intVal;
            }
        }
        else
        {
            if (reg1.typeCode == VarValue.INT)
            {
                if (reg2.typeCode == VarValue.REAL)
                {
                    reg1.typeCode = VarValue.REAL;
                    reg1.realVal = reg1.intVal - reg2.realVal;
                }
                else
                {
                    reg1.typeCode = VarValue.INT;
                    reg1.intVal = reg1.intVal - reg2.intVal;
                }
            }
        }
    }
    
    public static void ExecMulOp(RegVal reg1, RegVal reg2)
    {
        if (reg1.typeCode == VarValue.REAL)
        {
            if (reg2.typeCode == VarValue.REAL)
            {
                reg1.typeCode = VarValue.REAL;
                reg1.realVal = reg1.realVal * reg2.realVal;
            }
            else
            {
                reg1.typeCode = VarValue.REAL;
                reg1.realVal = reg1.realVal * reg2.intVal;
            }
        }
        else
        {
            if (reg1.typeCode == VarValue.INT)
            {
                if (reg2.typeCode == VarValue.REAL)
                {
                    reg1.typeCode = VarValue.REAL;
                    reg1.realVal = reg1.intVal * reg2.realVal;
                }
                else
                {
                    reg1.typeCode = VarValue.INT;
                    reg1.intVal = reg1.intVal * reg2.intVal;
                }
            }
        }
    }
    
    public static void ExecDivOp(RegVal reg1, RegVal reg2)
    {
        if (reg1.typeCode == VarValue.REAL)
        {
            if (reg2.typeCode == VarValue.REAL)
            {
                reg1.typeCode = VarValue.REAL;
                reg1.realVal = reg1.realVal / reg2.realVal;
            }
            else
            {
                reg1.typeCode = VarValue.REAL;
                reg1.realVal = reg1.realVal / reg2.intVal;
            }
        }
        else
        {
            if (reg1.typeCode == VarValue.INT)
            {
                if (reg2.typeCode == VarValue.REAL)
                {
                    reg1.typeCode = VarValue.REAL;
                    reg1.realVal = reg1.intVal / reg2.realVal;
                }
                else
                {
                    reg1.typeCode = VarValue.INT;
                    reg1.intVal = reg1.intVal / reg2.intVal;
                }
            }
        }
    }
    
    public static boolean like(final String str, final String expr)
    {
      String regex = quotemeta(expr);
      regex = regex.replace("_", ".").replace("%", ".*");
      Pattern p = Pattern.compile(regex);
      Matcher matcher = p.matcher(str);
      return matcher.find();
    }

    public static String quotemeta(String s)
    {
      if (s == null)
      {
        throw new IllegalArgumentException("String cannot be null");
      }

      int len = s.length();
      if (len == 0)
      {
        return "";
      }

      StringBuilder sb = new StringBuilder(len * 2);
      for (int i = 0; i < len; i++)
      {
        char c = s.charAt(i);
        if ("[](){}.*+?$^|#\\".indexOf(c) != -1)
        {
          sb.append("\\");
        }
        sb.append(c);
      }
      return sb.toString();
    }


    public static void ExecModOp(RegVal reg1, RegVal reg2)
    {
        reg1.typeCode = VarValue.INT;
        reg1.intVal = reg1.intVal % reg2.intVal;
    }
    
    public static void ExecConcatOp(RegVal reg1, RegVal reg2)
    {
        reg1.typeCode = VarValue.STRING;
        ((StringValue)reg1.value).val += ((StringValue)reg2.value).val;
    }
    
    public static void ExecSelectStatement(RegVal reg, String funcName, ValElem val, int typeCode)
    {
        String func_name_low = funcName.toLowerCase(Locale.US);
        SetVal setVal = (SetVal)reg.value;
        
        double result = 0.0;
        
        if (func_name_low.equals("count") )
        {
            result = setVal.members.size();            
        }
        else
        {
            if (func_name_low.equals("count_distinct") )
            {
                result = setVal.members.size(); 
            }
            else
            {
                if (func_name_low.equals("sum") )
                {
                    for(int i = 0; i < setVal.members.size(); i++)
                    {
                        ValElem elem = (ValElem)setVal.members.get(i);
                        
                        if (setVal.val.typeCode == setVal.val.INT)
                        {
                            result += ((IntValue)elem).val;
                        }
                        else
                        {
                            result += ((RealValue)elem).val;
                        }
                    }
                }
                else
                {
                    if (func_name_low.equals("avg") )
                    {
                        for(int i = 0; i < setVal.members.size(); i++)
                        {
                            ValElem elem = (ValElem)setVal.members.get(i);
                            
                            if (setVal.val.typeCode == setVal.val.INT)
                            {
                                result += ((IntValue)elem).val;
                            }
                            else
                            {
                                result += ((RealValue)elem).val;
                            }
                        }
                        result = result / setVal.members.size();
                    }
                    else
                    {
                    }
                }
            }
        }
        
        if (typeCode == VarValue.INT)
        {
            ((IntValue)val).val = (long)result;
        }
        else
        {
            if (typeCode == VarValue.REAL)
            {
                ((RealValue)val).val = result;
            }
        }
    }
    
    public VMUtils(VirtualMashine vMashine) 
    {        
        this.vMashine = vMashine;
    }
    
    public static void ExecSetOp(int opCode, RegVal reg1, RegVal reg2)
    {
        if (opCode == Instruction.UNION_OC)
        {
            ExecUnionOp(reg1, reg2);
        }
        else
        {
            if (opCode == Instruction.INTERSECT_OC)
            {
                ExecInterSectOp(reg1, reg2);
            }
        }
    }
    
    public static void ExecUnionOp(RegVal reg1, RegVal reg2)
    {
        if (reg1.typeCode == VarValue.SET)
        {
            SetVal set1 = (SetVal)reg1.value;
            
            if (reg2.typeCode == VarValue.SET)
            {
                SetVal set2 = (SetVal)reg2.value;
                
                for(int i = 0; i < set2.members.size(); i++)
                {
                    if (!set1.Constains((ValElem)set2.members.get(i), set2.val.typeCode))
                    {
                        set1.members.add(set2.members.get(i));
                    }
                }                
            }
            else
            {
                ValElem elem = set1.val.value.Clone();                
                StoreFromReg(reg2, elem, set1.val.typeCode);
                
                if (!set1.Constains(elem, set1.val.typeCode))
                {
                    set1.members.add(elem);
                }
            }
            reg1.typeCode = VarValue.SET;
            reg1.value = set1;
        }
        else
        {
            SetVal set2 = (SetVal)reg2.value;
            
            ValElem elem = set2.val.value.Clone();                
            StoreFromReg(reg1, elem, set2.val.typeCode);
            
            if (!set2.Constains(elem, set2.val.typeCode))
            {
                set2.members.add(elem);
            }
            reg1.typeCode = VarValue.SET;
            reg1.value = set2;
        }
    }
    
    public static void ExecInterSectOp(RegVal reg1, RegVal reg2)
    {
        if (reg1.typeCode == VarValue.SET)
        {
            SetVal set1 = (SetVal)reg1.value;
            SetVal resSet = new SetVal();
            resSet.val = set1.val.Clone();
            
            if (reg2.typeCode == VarValue.SET)
            {
                SetVal set2 = (SetVal)reg2.value;
                
                for(int i = 0; i < set2.members.size(); i++)
                {
                    if (set1.Constains((ValElem)set2.members.get(i), set2.val.typeCode))
                    {
                        resSet.members.add(set2.members.get(i));
                    }
                }                
            }
            else
            {
                ValElem elem = set1.val.value.Clone();                
                StoreFromReg(reg2, elem, set1.val.typeCode);
                
                if (set1.Constains(elem, set1.val.typeCode))
                {
                    resSet.members.add(elem);
                }
            }
            reg1.typeCode = VarValue.SET;
            reg1.value = resSet;
        }
        else
        {
            SetVal set2 = (SetVal)reg2.value;
            
            ValElem elem = set2.val.value.Clone();                
            StoreFromReg(reg1, elem, set2.val.typeCode);
            
            if (set2.Constains(elem, set2.val.typeCode))
            {
                set2.members.add(elem);
                set2.members.clear();
            }
            else
            {
                set2.members.clear();
            }
            
            reg1.typeCode = VarValue.SET;
            reg1.value = set2;
        }
    }
    
    public void ExecuteAbsBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();
        
        if (reg.typeCode == VarValue.INT)
        {
            if (reg.intVal < 0)
            {
                reg.intVal = reg.intVal * (-1);
            }
        }
        else
        {
            if (reg.realVal < 0)
            {
                reg.realVal = reg.realVal * (-1);
            }
        }
    }
    
    public void ExecuteCurrentDataBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Push();
        reg.typeCode = VarValue.DATE;
        DateVal val = new DateVal();
        val.val = new Date();
        reg.value = val;    
    }
    
    public void ExecuteCurrentTimeBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Push();
        reg.typeCode = VarValue.TIME;
        TimeVal val = new TimeVal();
        val.val = new Date();
        reg.value = val;    
    }
    
    public void ExecuteExecQueryBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        StringValue sqlStr = (StringValue)regArg.value;
        
        boolean error = false;
        
        if (this.vMashine.conn == null)
        {
            error = true;
            String[] errs = new String[1];            
            this.vMashine.LoadErrorDescription(errs, "INVALID_CONNECTION");
        }
        else
        {
            ResultSet rs = null;
            
            try 
            {
                Statement stmt = this.vMashine.conn.createStatement();
                rs = stmt.executeQuery(sqlStr.val);
            }
            catch(Exception e)
            {
                error = true;
                String[] errs = new String[1];            
                errs[ 0 ] = e.getMessage();
                this.vMashine.LoadErrorDescription(errs, "INVALID_QUERY");
            }
            
            if (!error)
            {
                if (rs == null)
                {
                    error = true;
                    String[] errs = new String[1];            
                    errs[ 0 ] = "";
                    this.vMashine.LoadErrorDescription(errs, "INVALID_QUERY");
                }
                
                RegVal reg = regPool.Push();
                reg.typeCode = VarValue.ITERATOR;
                IteratorVal iter = new IteratorVal();
                iter.rs = rs;
                reg.value = iter;
            }
        }
        
        if (error)
        {
            RegVal reg = regPool.Push();
            reg.typeCode = VarValue.ITERATOR;
            IteratorVal iter = new IteratorVal();
            reg.value = iter;
        }
    }
    
    public void ExecuteHasNextBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        IteratorVal iter = (IteratorVal)regArg.value;
        
        RegVal reg = regPool.Push();
        reg.typeCode = VarValue.BOOL;
        reg.booVal = false;
        
        try 
        {
           reg.booVal = iter.hasNext;
        }
        catch(Exception e)
        {
            String[] errs = new String[2];            
            errs[ 0 ] = "NEXT";
            errs[ 1 ] = e.getMessage();
            this.vMashine.LoadErrorDescription(errs, "ERROR_EXECUTING_BUILTIN_FUNC");
        }
    }
    
    public void ExecuteNonQueryBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        StringValue sqlStr = (StringValue)regArg.value;
        
        boolean error = false;
        int rowAffected = 0;
        
        if (this.vMashine.conn == null)
        {
            error = true;
            String[] errs = new String[1];            
            this.vMashine.LoadErrorDescription(errs, "INVALID_CONNECTION");
        }
        else
        {
            ResultSet rs = null;
            
            try 
            {
                Statement stmt = this.vMashine.conn.createStatement();
                rowAffected = stmt.executeUpdate(sqlStr.val);
            }
            catch(Exception e)
            {
                error = true;
                String[] errs = new String[1];            
                errs[ 0 ] = e.getMessage();
                this.vMashine.LoadErrorDescription(errs, "INVALID_QUERY");
            }
            
            if (!error)
            {
                RegVal reg = regPool.Push();
                reg.typeCode = VarValue.INT;                
                reg.intVal = rowAffected;
            }
        }
        
        if (error)
        {
            RegVal reg = regPool.Push();
            reg.typeCode = VarValue.INT;                
            reg.intVal = rowAffected;
        }
    }
    
    public static void FetchValue(IteratorVal iter, RegVal reg, int i, VirtualMashine vMashine)
    {
        try 
        {
            if (iter.rs == null)
            {
                return;
            }
            
            if (reg.typeCode == VarValue.STRING)
            {
                StringValue value = (StringValue)reg.value;
                value.val = iter.rs.getString(i);
                return;
            }
            
            if (reg.typeCode == VarValue.INT)
            {
                IntValue value = (IntValue)reg.value;
                value.val = iter.rs.getInt(i);
                return;
            }
            
            if (reg.typeCode == VarValue.REAL)
            {
                RealValue value = (RealValue)reg.value;
                value.val = iter.rs.getDouble(i);
                return;
            }
            
            if (reg.typeCode == VarValue.DATE)
            {
                DateVal value = (DateVal)reg.value;
                value.val = iter.rs.getDate(i);
                return;
            }
            
            if (reg.typeCode == VarValue.BOOL)
            {
                BoolValue value = (BoolValue)reg.value;
                value.val = iter.rs.getBoolean(i);
                return;
            }
            
            if (reg.typeCode == VarValue.TIME)
            {
                TimeVal value = (TimeVal)reg.value;
                value.val = iter.rs.getTime(i);
                return;
            }
        }
        catch(Exception e)
        {
            String[] errs = new String[2];            
            errs[ 0 ] = "FETCH";
            errs[ 1 ] = e.getMessage();
            vMashine.LoadErrorDescription(errs, "ERROR_EXECUTING_BUILTIN_FUNC");
        }
    }
    
    public void ExecuteGetStringBuiltInFunc(RegisterPool regPool)
    {
        RegVal strArg = regPool.Pop();
        StringValue strVal = (StringValue)strArg.value;
        
        RegVal regArg = regPool.Pop();
        IteratorVal iter = (IteratorVal)regArg.value;
        
        RegVal reg = regPool.Push();
        reg.typeCode = VarValue.STRING;
        StringValue res = new StringValue();
        reg.value = res;
        
        try 
        {
            res.val = iter.rs.getString(strVal.val);
        }
        catch(Exception e)
        {
            String[] errs = new String[2];            
            errs[ 0 ] = "GET_STRING";
            errs[ 1 ] = e.getMessage();
            this.vMashine.LoadErrorDescription(errs, "ERROR_EXECUTING_BUILTIN_FUNC");
        }
    }
    
    public void ExecuteGetIntBuiltInFunc(RegisterPool regPool)
    {
        RegVal strArg = regPool.Pop();
        StringValue strVal = (StringValue)strArg.value;
        
        RegVal regArg = regPool.Pop();
        IteratorVal iter = (IteratorVal)regArg.value;
        
        RegVal reg = regPool.Push();
        reg.typeCode = VarValue.INT;
        
        try 
        {
            reg.intVal = iter.rs.getInt(strVal.val);
        }
        catch(Exception e)
        {
            String[] errs = new String[2];            
            errs[ 0 ] = "GET_INT";
            errs[ 1 ] = e.getMessage();
            this.vMashine.LoadErrorDescription(errs, "ERROR_EXECUTING_BUILTIN_FUNC");
        }
    }
    
    public void ExecuteGetNumberBuiltInFunc(RegisterPool regPool)
    {
        RegVal strArg = regPool.Pop();
        StringValue strVal = (StringValue)strArg.value;
        
        RegVal regArg = regPool.Pop();
        IteratorVal iter = (IteratorVal)regArg.value;
        
        RegVal reg = regPool.Push();
        reg.typeCode = VarValue.REAL;
        
        try 
        {
            reg.realVal = iter.rs.getDouble(strVal.val);
        }
        catch(Exception e)
        {
            String[] errs = new String[2];            
            errs[ 0 ] = "GET_NUMBER";
            errs[ 1 ] = e.getMessage();
            this.vMashine.LoadErrorDescription(errs, "ERROR_EXECUTING_BUILTIN_FUNC");
        }
    }
    
    public void ExecuteGetDateBuiltInFunc(RegisterPool regPool)
    {
        RegVal strArg = regPool.Pop();
        StringValue strVal = (StringValue)strArg.value;
        
        RegVal regArg = regPool.Pop();
        IteratorVal iter = (IteratorVal)regArg.value;
        
        RegVal reg = regPool.Push();
        reg.typeCode = VarValue.DATE;
        
        try 
        {
            DateVal dateVal = new DateVal();
            dateVal.val = iter.rs.getDate(strVal.val);
            reg.value = dateVal;
        }
        catch(Exception e)
        {
            DateVal dateVal = new DateVal();
            reg.value = dateVal;
            
            String[] errs = new String[2];            
            errs[ 0 ] = "GET_DATE";
            errs[ 1 ] = e.getMessage();
            this.vMashine.LoadErrorDescription(errs, "ERROR_EXECUTING_BUILTIN_FUNC");
        }
    }

    public void ExecuteToStringBuiltInFunc(RegisterPool regPool)
    {
        StringValue val = new StringValue();
        
        RegVal arg = regPool.Pop();
        val.val = ConvRegToString(arg);
        
        RegVal reg = regPool.Push();
        reg.typeCode = VarValue.STRING;
        reg.value = val;
    }

    public void ExecuteGetBooleanBuiltInFunc(RegisterPool regPool)
    {
        RegVal strArg = regPool.Pop();
        StringValue strVal = (StringValue)strArg.value;
        
        RegVal regArg = regPool.Pop();
        IteratorVal iter = (IteratorVal)regArg.value;
        
        RegVal reg = regPool.Push();
        reg.typeCode = VarValue.BOOL;
        
        try 
        {
            reg.booVal = iter.rs.getBoolean(strVal.val);
        }
        catch(Exception e)
        {
            String[] errs = new String[2];            
            errs[ 0 ] = "GET_BOOL";
            errs[ 1 ] = e.getMessage();
            this.vMashine.LoadErrorDescription(errs, "ERROR_EXECUTING_BUILTIN_FUNC");
        }
    }
    
    public void ExecuteGetTimeBuiltInFunc(RegisterPool regPool)
    {
        RegVal strArg = regPool.Pop();
        StringValue strVal = (StringValue)strArg.value;
        
        RegVal regArg = regPool.Pop();
        IteratorVal iter = (IteratorVal)regArg.value;
        
        RegVal reg = regPool.Push();
        reg.typeCode = VarValue.TIME;
        
        try 
        {
            TimeVal timeVal = new TimeVal();
            timeVal.val = iter.rs.getTime(strVal.val);
            reg.value = timeVal;
        }
        catch(Exception e)
        {
            TimeVal timeVal = new TimeVal();
            reg.value = timeVal;
            
            String[] errs = new String[2];            
            errs[ 0 ] = "GET_TIME";
            errs[ 1 ] = e.getMessage();
            this.vMashine.LoadErrorDescription(errs, "ERROR_EXECUTING_BUILTIN_FUNC");
        }
    }
    
    public void ExecuteGetSizeBuiltInFunc(RegisterPool regPool)
    {
        RegVal setArg = regPool.Pop();
        SetVal setVal = (SetVal)setArg.value;
        
        RegVal reg = regPool.Push();
        reg.typeCode = VarValue.INT;
        reg.intVal = setVal.members.size();
    }
    
    public void ExecuteGetItemBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        SetVal setVal = (SetVal)regArg.value;
        
        regArg = regPool.Pop();
        
        int index = 0;
                
        if (regArg.typeCode == VarValue.INT)
        {
            index = (int)regArg.intVal;
        }
        else
        {
            index = (int)regArg.realVal;
        }
        
        ValElem elem = (ValElem)setVal.members.get(index);
        
        this.MoveToReg(regPool.Push(),elem, setVal.val.typeCode);
        
    }
    
    public void ExecuteShowInfoMessageBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        StringValue strVal = (StringValue)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.INT;
        newVal.intVal = 0;
        try 
        {
            JOptionPane.showMessageDialog(null, strVal.val, "Info", JOptionPane.INFORMATION_MESSAGE);
            newVal.intVal = 1;
        }
        catch(Exception e)
        {
        }
    }
    
    public void ExecuteShowWarningMessageBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        StringValue strVal = (StringValue)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.INT;
        newVal.intVal = 0;
        try 
        {
            JOptionPane.showMessageDialog(null, strVal.val, "Warning", JOptionPane.WARNING_MESSAGE);
            newVal.intVal = 1;
        }
        catch(Exception e)
        {
        }
    }
    
    public void ExecuteShowErrorMessageBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        StringValue strVal = (StringValue)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.INT;
        newVal.intVal = 0;
        try 
        {
            JOptionPane.showMessageDialog(null, strVal.val, "Error", JOptionPane.ERROR_MESSAGE);
            newVal.intVal = 1;
        }
        catch(Exception e)
        {
        }
    }
    
    public void ExecuteShowYesNoMessageBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        StringValue strVal = (StringValue)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.INT;
        newVal.intVal = 0;
        try 
        {
            newVal.intVal = JOptionPane.showConfirmDialog(null, strVal.val, "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        }
        catch(Exception e)
        {
        }
    }
    
    public void ExecuteShowInputMessageBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        StringValue strVal = (StringValue)regArg.value;
        
        RegVal reg = regPool.Push();
        reg.typeCode = VarValue.STRING;
        StringValue res = new StringValue();
        reg.value = res;
        
        try 
        {
            //res.val = JOptionPane.showInputDialog(null, strVal.val, "Input value", JOptionPane.OK_CANCEL_OPTION);
             res.val = JOptionPane.showInputDialog(strVal.val);
        }
        catch(Exception e)
        {
        }
    }
    
    public void ExecuteLenBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        StringValue strVal = (StringValue)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.INT;
        newVal.intVal = strVal.val.length();
    }
    
    public void ExecuteCosBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();                
        
        if (reg.typeCode == VarValue.INT)
        {
            reg.realVal = Math.cos((double)reg.intVal);
        }
        else
        {
            reg.realVal = Math.cos(reg.realVal);
        }
        
        reg.typeCode = VarValue.REAL;
    }
    
    public void ExecuteSinBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();
        
        if (reg.typeCode == VarValue.INT)
        {
            reg.realVal = Math.sin(reg.intVal);
        }
        else
        {
            reg.realVal = Math.sin(reg.realVal);
        }
        
        reg.typeCode = VarValue.REAL;
    }
    
    public void ExecuteFloorBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();
        
        if (reg.typeCode == VarValue.INT)
        {
            reg.realVal = Math.floor(reg.intVal);
        }
        else
        {
            reg.realVal = Math.floor(reg.realVal);
        }
        
        reg.typeCode = VarValue.REAL;
    }
    
    public void ExecuteRoundBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();
        
        if (reg.typeCode == VarValue.INT)
        {
            reg.realVal = Math.round(reg.intVal);
        }
        else
        {
            reg.realVal = Math.round(reg.realVal);
        }
        
        reg.typeCode = VarValue.REAL;
    }
    
    public void ExecuteSinhBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();
        
        if (reg.typeCode == VarValue.INT)
        {
            reg.realVal = Math.sinh(reg.intVal);
        }
        else
        {
            reg.realVal = Math.sinh(reg.realVal);
        }
        
        reg.typeCode = VarValue.REAL;
    }
    
    public void ExecuteLogBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();
        
        if (reg.typeCode == VarValue.INT)
        {
            reg.realVal = Math.log10(reg.intVal);
        }
        else
        {
            reg.realVal = Math.log10(reg.realVal);
        }
        
        reg.typeCode = VarValue.REAL;
    }
    
    public void ExecuteMaxBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg1 = regPool.Pop();
        RegVal reg2 = regPool.Peek();
        double num1, num2;
        
        if (reg1.typeCode == VarValue.INT && reg2.typeCode == VarValue.INT)
        {
            reg2.intVal = Math.max(reg1.intVal, reg2.intVal);
            return;
        }
        
        if (reg1.typeCode == VarValue.INT)
        {
            num1 = reg1.intVal;
        }
        else
        {
            num1 = reg1.realVal;
        }
        
        if (reg2.typeCode == VarValue.INT)
        {
            num2 = reg2.intVal;
        }
        else
        {
            num2 = reg2.realVal;
        }
        
        reg2.typeCode = VarValue.REAL;
        reg2.realVal = Math.max(num1, num2);
    }
    
    public void ExecuteMinBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg1 = regPool.Pop();
        RegVal reg2 = regPool.Peek();
        double num1, num2;
        
        if (reg1.typeCode == VarValue.INT && reg2.typeCode == VarValue.INT)
        {
            reg2.intVal = Math.min(reg1.intVal, reg2.intVal);
            return;
        }
        
        if (reg1.typeCode == VarValue.INT)
        {
            num1 = reg1.intVal;
        }
        else
        {
            num1 = reg1.realVal;
        }
        
        if (reg2.typeCode == VarValue.INT)
        {
            num2 = reg2.intVal;
        }
        else
        {
            num2 = reg2.realVal;
        }
        
        reg2.typeCode = VarValue.REAL;
        reg2.realVal = Math.min(num1, num2);
    }
    
    public void ExecutePowBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg1 = regPool.Pop();
        RegVal reg2 = regPool.Peek();
        double num1, num2;
        
        if (reg1.typeCode == VarValue.INT)
        {
            num1 = reg1.intVal;
        }
        else
        {
            num1 = reg1.realVal;
        }
        
        if (reg2.typeCode == VarValue.INT)
        {
            num2 = reg2.intVal;
        }
        else
        {
            num2 = reg2.realVal;
        }
        
        reg2.typeCode = VarValue.REAL;
        reg2.realVal = Math.pow(num2, num1);
    }
    
    public void ExecuteRandomBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg1 = regPool.Push();
        
        reg1.typeCode = VarValue.REAL;
        reg1.realVal = Math.random();
    }
    
    public void ExecuteCoshBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();
        
        if (reg.typeCode == VarValue.INT)
        {
            reg.realVal = Math.cosh(reg.intVal);
        }
        else
        {
            reg.realVal = Math.cosh(reg.realVal);
        }
        
        reg.typeCode = VarValue.REAL;
    }
    
    public void ExecuteSqrtBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();
        
        if (reg.typeCode == VarValue.INT)
        {
            reg.realVal = Math.sqrt(reg.intVal);
        }
        else
        {
            reg.realVal = Math.sqrt(reg.realVal);
        }
        
        reg.typeCode = VarValue.REAL;
    }
    
    public void ExecuteTanBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();
        
        if (reg.typeCode == VarValue.INT)
        {
            reg.realVal = Math.tan(reg.intVal);
        }
        else
        {
            reg.realVal = Math.tan(reg.realVal);
        }
        
        reg.typeCode = VarValue.REAL;
    }
    
    public void ExecuteTanhBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();
        
        if (reg.typeCode == VarValue.INT)
        {
            reg.realVal = Math.tanh(reg.intVal);
        }
        else
        {
            reg.realVal = Math.tanh(reg.realVal);
        }
        
        reg.typeCode = VarValue.REAL;
    }
    
    public void ExecuteAcosBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();
        
        if (reg.typeCode == VarValue.INT)
        {
            reg.realVal = Math.acos(reg.intVal);
        }
        else
        {
            reg.realVal = Math.acos(reg.realVal);
        }
        
        reg.typeCode = VarValue.REAL;
    }
    
    public void ExecuteAsinBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();
        
        if (reg.typeCode == VarValue.INT)
        {
            reg.realVal = Math.asin(reg.intVal);
        }
        else
        {
            reg.realVal = Math.asin(reg.realVal);
        }
        
        reg.typeCode = VarValue.REAL;
    }
    
    public void ExecuteAtanBuiltInFunc(RegisterPool regPool)
    {
        RegVal reg = regPool.Peek();
        
        if (reg.typeCode == VarValue.INT)
        {
            reg.realVal = Math.atan(reg.intVal);
        }
        else
        {
            reg.realVal = Math.atan(reg.realVal);
        }
        
        reg.typeCode = VarValue.REAL;
    }
    
    public void ExecuteSubStringBuiltInFunc(RegisterPool regPool)
    {
        int len = 0;
        RegVal regArg = regPool.Pop();
                
        if (regArg.typeCode == VarValue.INT)
        {
            len = (int)regArg.intVal;
        }
        else
        {
            len = (int)regArg.realVal;
        }
        
        int begin = 0;
        regArg = regPool.Pop();
                
        if (regArg.typeCode == VarValue.INT)
        {
            begin = (int)regArg.intVal;
        }
        else
        {
            begin = (int)regArg.realVal;
        }
        
        regArg = regPool.Pop();
        StringValue strVal = (StringValue)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.STRING;
        StringValue newStrVal = new StringValue();
        
        if (begin < 0 || begin >= strVal.val.length())
        {
            newStrVal.val = strVal.val;
        }
        else
        {
            if(begin + len >= strVal.val.length() || len <= 0)
            {
                newStrVal.val = strVal.val.substring(begin);
            }
            else
            {
                newStrVal.val = strVal.val.substring(begin, begin + len);
            }
        }
        newVal.value = newStrVal;
    }
    
    public void ExecuteStartsWithBuiltInFunc(RegisterPool regPool)
    {
        int len = 0;
        RegVal regArg = regPool.Pop();
        StringValue strVal1 = (StringValue)regArg.value;
        
        regArg = regPool.Pop();
        StringValue strVal2 = (StringValue)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.BOOL;
        newVal.booVal = strVal2.val.startsWith(strVal1.val);
    }
    
    public void ExecuteEndsWithBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        StringValue strVal1 = (StringValue)regArg.value;
        
        regArg = regPool.Pop();
        StringValue strVal2 = (StringValue)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.BOOL;
        newVal.booVal = strVal2.val.endsWith(strVal1.val);
    }
    
    public void ExecuteIndexOfBuiltInFunc(RegisterPool regPool)
    {
        int len = 0;
        RegVal regArg = regPool.Pop();
        StringValue strVal1 = (StringValue)regArg.value;
        
        regArg = regPool.Pop();
        StringValue strVal2 = (StringValue)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.INT;
        newVal.intVal = strVal2.val.indexOf(strVal1.val);
    }
    
    public void ExecuteLastIndexOfBuiltInFunc(RegisterPool regPool)
    {
        int len = 0;
        RegVal regArg = regPool.Pop();
        StringValue strVal1 = (StringValue)regArg.value;
        
        regArg = regPool.Pop();
        StringValue strVal2 = (StringValue)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.INT;
        newVal.intVal = strVal2.val.lastIndexOf(strVal1.val);
    }
    
    public void ExecuteToUpperBuiltInFunc(RegisterPool regPool)
    {
        int len = 0;
        RegVal regArg = regPool.Pop();
        StringValue strVal = (StringValue)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.STRING;
        StringValue newStrVal = new StringValue();        
        newStrVal.val = strVal.val.toUpperCase(Locale.US);
        newVal.value = newStrVal;
    }
    
    public void ExecuteToLowerBuiltInFunc(RegisterPool regPool)
    {
        int len = 0;
        RegVal regArg = regPool.Pop();
        StringValue strVal = (StringValue)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.STRING;
        StringValue newStrVal = new StringValue();        
        newStrVal.val = strVal.val.toLowerCase(Locale.US);
        newVal.value = newStrVal;
    }
    
    public void ExecuteDayBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        DateVal datVal = (DateVal)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.INT;       
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String temp = sdf.format(datVal.val);
        newVal.intVal =  Integer.parseInt(temp);
    }
    
    public void ExecuteMonthBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        DateVal datVal = (DateVal)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.INT;       
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String temp = sdf.format(datVal.val);
        newVal.intVal =  Integer.parseInt(temp);
    }
    
    public void ExecuteYearBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        DateVal datVal = (DateVal)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.INT;       
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String temp = sdf.format(datVal.val);
        newVal.intVal =  Integer.parseInt(temp);
    }
    
    public void ExecuteHourBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        TimeVal datVal = (TimeVal)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.INT;       
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String temp = sdf.format(datVal.val);
        newVal.intVal =  Integer.parseInt(temp);
    }
    
    public void ExecuteMinuteBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        TimeVal datVal = (TimeVal)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.INT;       
        SimpleDateFormat sdf = new SimpleDateFormat("mm");
        String temp = sdf.format(datVal.val);
        newVal.intVal =  Integer.parseInt(temp);
    }
    
    public void ExecuteSecondBuiltInFunc(RegisterPool regPool)
    {
        RegVal regArg = regPool.Pop();
        TimeVal datVal = (TimeVal)regArg.value;
        
        RegVal newVal = regPool.Push();
        newVal.typeCode = VarValue.INT;       
        SimpleDateFormat sdf = new SimpleDateFormat("ss");
        String temp = sdf.format(datVal.val);
        newVal.intVal =  Integer.parseInt(temp);
    }
    
    public void ExecuteBuiltInFunc(int code, RegisterPool regPool)
    {
        if (code < 32)
        {
            if (code < 16)
            {
                if (code < 8)
                {
                    if (code < 4) // 0, 1, 2, 3
                    {
                        if (code < 2) //0 ,1
                        {
                            if ( code == ABS_CODE)
                            {
                                ExecuteAbsBuiltInFunc(regPool);
                            }
                            
                            if ( code == SUBSTRING_CODE)
                            {
                                ExecuteSubStringBuiltInFunc(regPool);
                            }
                        }
                        else //2,3
                        {
                            if ( code == LEN_CODE)
                            {
                                ExecuteLenBuiltInFunc(regPool);
                            }
                            
                            if (code == CURRENTTIME_CODE)
                            {
                                ExecuteCurrentTimeBuiltInFunc(regPool);
                            }
                        }
                    }
                    else //4,5,6,7
                    {
                        if (code < 6) //4,5
                        {
                            if (code == CURRENTDATE_CODE)
                            {
                                ExecuteCurrentDataBuiltInFunc(regPool);
                            }
                            
                            if (code == EXECUTE_QUERY_CODE)
                            {
                                ExecuteExecQueryBuiltInFunc(regPool);
                            }
                        }
                        else //6,7
                        {
                            if (code == HAS_NEXT_CODE)
                            {
                                ExecuteHasNextBuiltInFunc(regPool);
                            }
                            
                            if (code == EXEC_NON_QUERY_CODE)
                            {
                                ExecuteNonQueryBuiltInFunc(regPool);
                            }
                        }
                    }
                }
                else //8-16
                {
                    if (code < 12) //8,9,10,11
                    {
                        if(code < 10) //8,9
                        {
                            if (code == GET_STRING_CODE)
                            {
                                ExecuteGetStringBuiltInFunc(regPool);
                            }
                            
                            if (code == GET_INT_CODE)
                            {
                                ExecuteGetIntBuiltInFunc(regPool);
                            }
                        }
                        else //10,11
                        {
                            if (code == GET_BOOLEAN_CODE)
                            {
                                ExecuteGetBooleanBuiltInFunc(regPool);
                            }
                            
                            if (code == GET_NUMBER_CODE)
                            {
                                ExecuteGetNumberBuiltInFunc(regPool);
                            }
                        }
                    }
                    else //12,13,14,15
                    {
                        if (code < 14) //12,13
                        {
                            if (code == GET_TIME_CODE)
                            {
                                ExecuteGetTimeBuiltInFunc(regPool);
                            }
                            
                            if (code == GET_DATE_CODE)
                            {
                                ExecuteGetDateBuiltInFunc(regPool);
                            }
                        }
                        else //14,15
                        {
                            if (code == GET_SIZE_CODE)
                            {
                                ExecuteGetSizeBuiltInFunc(regPool);
                            }
                            
                            if (code == TO_STRING_CODE)
                            {
                                ExecuteToStringBuiltInFunc(regPool);
                            }
                        }
                    }
                }
            }
            else //16..31
            {
                if ( code < 24 )
                {
                    if ( code < 20) //16..20
                    {
                        if ( code < 18) //16,17
                        {
                            if (code == GET_ITEM_CODE)
                            {
                                ExecuteGetItemBuiltInFunc(regPool);
                            }
                            else
                            {
                                if (code == SHOW_INFO_MESSAGE_CODE)
                                {
                                    ExecuteShowInfoMessageBuiltInFunc(regPool);
                                }
                            }
                        }
                        else //18,19, 20
                        {
                            if (code == SHOW_WARNING_MESSAGE_CODE)
                            {
                                ExecuteShowWarningMessageBuiltInFunc(regPool);
                            }
                            else
                            {
                                if (code == SHOW_ERROR_MESSAGE_CODE)
                                {
                                    ExecuteShowErrorMessageBuiltInFunc(regPool);
                                }
                            }
                        }
                    }
                    else //20..23
                    {
                        if ( code < 22) //20,21
                        {
                            if (code == SHOW_YESNO_MESSAGE_CODE)
                            {
                                ExecuteShowYesNoMessageBuiltInFunc(regPool);
                            }
                            
                            if (code == SHOW_INPUT_MESSAGE_CODE)
                            {
                                ExecuteShowInputMessageBuiltInFunc(regPool);
                            }
                        }
                        else //22,23
                        {
                            if (code == COS_CODE)
                            {
                                ExecuteCosBuiltInFunc(regPool);
                            }
                            else
                            {
                                ExecuteSinBuiltInFunc(regPool);
                            }
                        }
                    }
                }
                else //24..31
                {
                    if (code < 29) //24..28
                    {
                        if (code < 26) //24..25
                        {
                            if (code == FLOOR_CODE)
                            {
                                ExecuteFloorBuiltInFunc(regPool);
                            }
                            else
                            {
                                ExecuteLogBuiltInFunc(regPool);
                            }
                        }
                        else//26,27,28
                        {
                            if (code == MAX_CODE)
                            {
                                ExecuteMaxBuiltInFunc(regPool);
                            }
                            else
                            {
                                if (code == MIN_CODE)
                                {
                                    ExecuteMinBuiltInFunc(regPool);
                                }
                                else
                                {
                                    if (code == POW_CODE)
                                    {
                                        ExecutePowBuiltInFunc(regPool);
                                    }
                                }
                            }
                        }
                    }
                    else //29..31
                    {
                        if (code == RANDOM_CODE)
                        {
                            ExecuteRandomBuiltInFunc(regPool);
                        }
                        else
                        {
                            if (code == ROUND_CODE)
                            {
                                ExecuteRoundBuiltInFunc(regPool);
                            }
                            else
                            {
                                if (code == SINH_CODE)
                                {
                                    ExecuteSinhBuiltInFunc(regPool);
                                }
                            }
                        }
                    }
                }
            }
        }
        else//32..61
        {
            if (code < 48) //32..47
            {
                if (code < 40) //32..39
                {
                    if (code < 36) // 32..35
                    {
                        if (code < 34) //32,33
                        {
                            if (code == COSH_CODE)
                            {
                                ExecuteCoshBuiltInFunc(regPool);
                            }
                            else
                            {
                                if (code == SQRT_CODE)
                                {
                                    ExecuteSqrtBuiltInFunc(regPool);
                                }
                            }
                        }
                        else //34,35
                        {
                            if (code == TAN_CODE)
                            {
                                ExecuteTanBuiltInFunc(regPool);
                            }
                            
                            if (code == TANH_CODE)
                            {
                                ExecuteTanhBuiltInFunc(regPool);
                            }
                        }
                    }
                    else //36..39
                    {
                        if (code < 38)//36,37
                        {
                            if (code == ACOS_CODE)
                            {
                                ExecuteAcosBuiltInFunc(regPool);
                            }
                            
                            if (code == ASIN_CODE)
                            {
                                ExecuteAsinBuiltInFunc(regPool);
                            }
                        }
                        else//38,39
                        {
                            if (code == ATAN_CODE)
                            {
                                ExecuteAtanBuiltInFunc(regPool);
                            }
                            
                            if (code == STARTSWITH_CODE)
                            {
                                ExecuteStartsWithBuiltInFunc(regPool);
                            }
                        }
                    }
                }
                else//40..47
                {
                    if (code < 44) //40..43
                    {
                        if (code == ENDSSWITH_CODE)
                        {
                            ExecuteEndsWithBuiltInFunc(regPool);
                        }
                        
                        if (code == INDEXOF_CODE)
                        {
                            ExecuteIndexOfBuiltInFunc(regPool);
                        }
                        
                        if (code == LASTINDEXOF_CODE)
                        {
                            ExecuteLastIndexOfBuiltInFunc(regPool);
                        }
                        
                        if (code == TOLOWER_CODE)
                        {
                            ExecuteToLowerBuiltInFunc(regPool);
                        }
                    }
                    else//44..47
                    {
                        if (code == TOUPPER_CODE)
                        {
                            ExecuteToUpperBuiltInFunc(regPool);
                        }
                        
                        if (code == DAY_CODE)
                        {
                            ExecuteDayBuiltInFunc(regPool);
                        }
                        
                        if (code == MONTH_CODE)
                        {
                            ExecuteMonthBuiltInFunc(regPool);
                        }
                        
                        if (code == YEAR_CODE)
                        {
                            ExecuteYearBuiltInFunc(regPool);
                        }
                    }
                }
            }
            else//48..61
            {
                if (code < 59) //48...58
                {
                    if ( code < 53)
                    {
                        if (code == HOUR_CODE)
                        {
                            ExecuteHourBuiltInFunc(regPool);
                        }
                        
                        if (code == MINUTE_CODE)
                        {
                            ExecuteMinuteBuiltInFunc(regPool);
                        }
                        
                        if (code == SECOND_CODE)
                        {
                            ExecuteSecondBuiltInFunc(regPool);
                        }
                    }
                }
            }
        }
    }
}
