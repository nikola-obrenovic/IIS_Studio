package iisc.lang.vmashine;

public class StringValue implements ValElem
{
    String val = "";
    
    public StringValue() {
    }
    
    public StringValue(String val) 
    {
        this.val = val;
    }
    
    public ValElem Clone()
    {
        return new StringValue(this.val);
    }
    
    public String ToString()
    {
        return val;
    }
    
    public void Copy(ValElem elem, int typeCode)
    {
        if (typeCode == VarValue.STRING)
        {
            this.val = ((StringValue)elem).val;
        }
    }
    
    public int Compare(ValElem elem, int typeCode) 
    {
        StringValue temp = ((StringValue)elem);
        
        return this.val.compareTo(temp.val);
    }
}
