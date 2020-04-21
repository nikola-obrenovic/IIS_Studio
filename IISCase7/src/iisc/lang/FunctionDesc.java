package iisc.lang;

import java.util.ArrayList;

public class FunctionDesc 
{
    String name = "";
    String domName = "";
    ArrayList params = new ArrayList();
    boolean isBuiltIn  = false;
    String code = "";
    
    public FunctionDesc(String name, String domName) 
    {
        this.name= name;
        this.domName = domName;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public String getDescName()
    {
        String result = this.name + "(";
        
        for(int i = 0; i < this.params.size(); i++)
        {
            if ( i > 0 )
            {
                result += ",";
            }
            
            ParamDesc pd = (ParamDesc)this.params.get(i);
            
            if ( i > 0 )
            {
                result += " " + pd.getName();
            }
            else
            {
                result += pd.getName();
            }
            
            if (pd.getType() == ParamDesc.IN)
            {
                result += " " + "IN";
            }
            else
            {
                if (pd.getType() == ParamDesc.OUT)
                {
                    result += " " + "OUT";
                }
                else
                {
                    result += " " + "INOUT";
                }
            }
            
            result += " " + pd.getDomName();
        }
        result += ") RETURNS " + this.domName;
        
        return result;
    }
    
    public void setDomName(String domName)
    {
        this.domName = domName;
    }
    
    public String getDomName()
    {
        return this.domName;
    }
    
    public ArrayList getParams()
    {
        return this.params;
    }
    
    public boolean getIsBuilIn()
    {
        return this.isBuiltIn;
    }
    
    public void setIsBuiltIn(boolean isBuiltIn)
    {
        this.isBuiltIn = isBuiltIn;
    }
    
    public String getCode()
    {
        return this.code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
}
