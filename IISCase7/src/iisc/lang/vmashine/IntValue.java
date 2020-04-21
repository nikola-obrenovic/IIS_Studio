package iisc.lang.vmashine;

public class IntValue implements ValElem
{
    public long val; 
    
    public IntValue(long val) 
    {
        this.val = val;
    }
    
    public IntValue() 
    {
        val = 0;
    }
    
    public ValElem Clone()
    {
        return new IntValue(this.val);
    }
    
    public String ToString()
    {
        return Long.toString(this.val);
    }
    
    public void Copy(ValElem elem, int typeCode)
    {
        if (typeCode == VarValue.INT)
        {
            this.val = ((IntValue)elem).val;
        }
    }
    
    public int Compare(ValElem elem, int typeCode) 
    {
        IntValue temp = ((IntValue)elem);
        
        if (this.val == temp.val)
        {
            return 0;
        }
        
        if (this.val > temp.val)
        {
            return 1;
        }
        return -1;
    }
}
