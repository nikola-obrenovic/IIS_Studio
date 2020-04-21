package iisc.lang.vmashine;

public class VarValue 
{
    public int typeCode;
    public ValElem value; 
    
    public static final int REAL = 0;
    public static final int INT = 1;
    public static final int STRING = 2;
    public static final int DATE = 3;
    public static final int TIME = 4;
    public static final int BOOL = 5;
    public static final int TUPPLE = 6;
    public static final int ARRAY = 7;
    public static final int SET = 8;
    public static final int ITERATOR = 9;
    
    public VarValue() 
    {
    }
    
    public VarValue Clone()
    {
        VarValue temp = new VarValue();
        temp.typeCode = this.typeCode;
        temp.value = this.value.Clone();
        return temp;
    }
}
