package iisc.lang.vmashine;

import java.text.SimpleDateFormat;

import java.util.Date;


public class TimeVal implements ValElem
{
    public Date val;
    public static  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    
    public TimeVal() 
    {
        val = new Date();
    }
    
    public TimeVal(Date val) 
    {
        this.val = new Date(val.getTime());
    }
    
    public ValElem Clone()
    {
        return new TimeVal(this.val);
    }
    
    public String ToString()
    {
        return sdf.format(this.val);
    }
    
    public void Copy(ValElem elem, int typeCode)
    {
        if (typeCode == VarValue.DATE)
        {
            this.val = new Date(((TimeVal)elem).val.getTime());
        }
    }
    
    public int Compare(ValElem elem, int typeCode) 
    {
        TimeVal temp = ((TimeVal)elem);
        return this.val.compareTo(temp.val);
    }
}
