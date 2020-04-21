package iisc.lang.vmashine;

import java.sql.ResultSet;

public class IteratorVal implements ValElem
{
    int type = 0;
    ResultSet rs = null;
    boolean hasNext = false;
    
    public IteratorVal() 
    {
    }

    public ValElem Clone() 
    {
        IteratorVal iter = new IteratorVal();
        iter.type = this.type;
        iter.rs = this.rs;
        iter.hasNext = this.hasNext;
        return iter;
    }

    public String ToString() 
    {
        return "Iterator";
    }

    public void Copy(ValElem elem, int typeCode) 
    {
        IteratorVal iter = (IteratorVal)elem;
        this.type = iter.type;
        this.rs = iter.rs;
        this.hasNext = iter.hasNext;
    }

    public int Compare(ValElem elem, int typeCode) 
    {
        return 0;
    }
}
