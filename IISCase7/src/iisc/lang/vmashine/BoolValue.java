package iisc.lang.vmashine;

public class BoolValue implements ValElem
{
    boolean val; 
    
    public BoolValue()     
    {
        val = false;
    }
    
    public BoolValue(boolean val)     
    {
        this.val = val;
    }
    
    public ValElem Clone()
    {
        return new BoolValue(this.val);
    }
    
    public String ToString()
    {
        if (this.val)
        {
            return "True";
        }
        else
        {
            return "False";
        }
    }
    
    public void Copy(ValElem elem, int typeCode)
    {
        if (typeCode == VarValue.BOOL)
        {
            this.val = ((BoolValue)elem).val;
        }
    }
    
    public int Compare(ValElem elem, int typeCode) 
    {
        BoolValue temp = ((BoolValue)elem);
        
        if (this.val == temp.val)
        {
            return 0;
        }
        return 1;
    }
}
