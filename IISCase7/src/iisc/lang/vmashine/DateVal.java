package iisc.lang.vmashine;

import java.text.SimpleDateFormat;

import java.util.Date;

public class DateVal implements ValElem
{
    public Date val;
    public static  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public DateVal() 
    {
        val = new Date();
    }
    
    public DateVal(Date val) 
    {
        this.val = new Date(val.getTime());
    }
    
    public ValElem Clone()
    {
        return new DateVal(this.val);
    }
    
    public String ToString()
    {
        return sdf.format(this.val);
    }
    
    public void Copy(ValElem elem, int typeCode)
    {
        if (typeCode == VarValue.DATE)
        {
            this.val = new Date(((DateVal)elem).val.getTime());
        }
    }
    
    public int Compare(ValElem elem, int typeCode) 
    {
        DateVal temp = ((DateVal)elem);
        return this.val.compareTo(temp.val);
    }
}
