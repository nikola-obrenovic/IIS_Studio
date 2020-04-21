package iisc.lang.vmashine;

public class RealValue implements ValElem
{
    double val;
    
    public RealValue() 
    {
    }
    
    public RealValue(double val) 
    {
        this.val = val;
    }
    
    public ValElem Clone()
    {
        return new RealValue(this.val);
    }
    
    public String ToString()
    {
        String result = Double.toString(this.val);
        
        if(result.endsWith(".0"))
        {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }
    
    public void Copy(ValElem elem, int typeCode)
    {
        if (typeCode == VarValue.REAL)
        {
            this.val = ((RealValue)elem).val;
        }
    }
    
    public int Compare(ValElem elem, int typeCode) 
    {
        RealValue temp = ((RealValue)elem);
        
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
