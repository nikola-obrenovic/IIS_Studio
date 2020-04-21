package iisc.lang;

public class ParamDesc 
{
    public static final int IN = 0;
    public static final int OUT = 1;
    public static final int INOUT = 2;
    
    String name = "";
    String domName = "";
    int paramSeqNumber = 0;
    int type = 0;
    
    public ParamDesc(String name, String domName) 
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
    
    public void setDomName(String domName)
    {
        this.domName = domName;
    }
    
    public String getDomName()
    {
        return this.domName;
    }
    
    public void setSeqNumber(int seqNumber)
    {
        this.paramSeqNumber = seqNumber;
    }
    
    public int getSeqNumber()
    {
        return this.paramSeqNumber;
    }
    
    public void setType(int type)
    {
        this.type = type;
    }
    
    public int getType()
    {
        return this.type;
    }
}
