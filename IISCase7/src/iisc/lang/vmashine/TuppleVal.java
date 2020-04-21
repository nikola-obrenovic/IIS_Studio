package iisc.lang.vmashine;

public class TuppleVal implements ValElem 
{
    int memCount; 
    ValElem[] members;
    
    public TuppleVal() 
    {
    
    }
    
    public ValElem Clone()
    {
        TuppleVal temp = new TuppleVal();
        temp.memCount = this.memCount;
        temp.members = new MemberVal[this.memCount];
        
        for(int i = 0; i < memCount; i++)
        {
            temp.members[ i ] = this.members[ i ].Clone();
        }
        
        return temp;
    }
    
    public String ToString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        
        for(int i = 0; i < memCount; i++)
        {
            if ( i > 0 )
            {
                builder.append(",");
            }
            builder.append(members[i].ToString());
        }
        
        builder.append("}");
        return builder.toString();
    }
    
    public void Copy(ValElem elem, int typeCode)
    {
        TuppleVal temp = (TuppleVal)elem;
        
        for(int i = 0; i < memCount; i++)
        {
            this.members[ i ].Copy(temp.members[ i ], ((MemberVal)temp.members[ i ]).type);
        }
    }
    
    public int Compare(ValElem elem, int typeCode)
    {
        TuppleVal temp = (TuppleVal)elem;
        
        for(int i = 0; i < memCount; i++)
        {
            int rez = this.members[ i ].Compare(temp.members[ i ], ((MemberVal)temp.members[ i ]).type);
            
            if ( rez != 0)
            {
                return rez;
            }
        }
        return 0;
    }
}
