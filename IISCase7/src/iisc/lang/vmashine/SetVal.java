package iisc.lang.vmashine;
import java.util.ArrayList;

public class SetVal implements ValElem 
{
    ArrayList members = new ArrayList();
    VarValue val = null;
    
    public SetVal() 
    {
    }
    
    public ValElem Clone()
    {
        SetVal temp = new SetVal();
        temp.val = this.val.Clone();
        
        for(int i = 0; i < this.members.size(); i++)
        {
            temp.members.add(((ValElem)this.members.get(i)).Clone());
        }
        
        return temp;
    }
    
    public String ToString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        
        for(int i = 0; i < this.members.size(); i++)
        {
            if ( i > 0 )
            {
                builder.append(",");
            }
            builder.append(((ValElem)members.get(i)).ToString());
        }
        
        builder.append("}");
        return builder.toString();
    }
    
    public void Copy(ValElem elem, int typeCode)
    {
        SetVal temp = (SetVal)elem;
        this.members.clear();
        
        for(int i = 0; i < temp.members.size(); i++)
        {
            this.members.add(((ValElem)temp.members.get(i)).Clone());
        }
    }
    
    public int Compare(ValElem elem, int typeCode) 
    {
        SetVal temp = (SetVal)elem;
        
        for(int i = 0; i < temp.members.size();i++)
        {
            if (!this.Constains(((ValElem)temp.members.get(i)), temp.val.typeCode))
            {
                return 1;
            }
        }
        
        for(int i = 0; i < this.members.size();i++)
        {
            if (!temp.Constains(((ValElem)this.members.get(i)), this.val.typeCode))
            {
                return 1;
            }
        }
        return 0;
    }
    
    public boolean Constains(ValElem elem, int typeCode) 
    {
        for(int i = 0; i < this.members.size();i++)
        {
            if ( ((ValElem)members.get(i)).Compare(elem, typeCode) == 0)
            {
                return true;
            }
        }
        return false;
    }
}