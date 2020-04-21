package iisc.lang.vmashine;

public class ArrayVal implements ValElem
{
    int count;
    int type;
    ValElem[] data;
    int lBound;
    int uBound;
    
    public ArrayVal() 
    {   
    }
    
    public ValElem Clone()
    {
        ArrayVal temp = new ArrayVal();
        temp.count = this.count;
        temp.data = new ValElem[this.count];
        temp.type = this.type; 
        temp.lBound = this.lBound;
        temp.uBound = this.uBound;
        
        for(int i = 0; i < count; i++)
        {
            temp.data[ i ] = this.data[ i ].Clone();
        }
        
        return temp;
    }
    
    public String ToString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        
        for(int i = 0; i < count; i++)
        {
            if ( i > 0 )
            {
                builder.append(",");
            }
            builder.append(data[i].ToString());
        }
        
        builder.append("}");
        return builder.toString();
    }
    
    public void Copy(ValElem elem, int typeCode)
    {
        ArrayVal temp = (ArrayVal)elem;
        
        for(int i = 0; i < this.count; i++)
        {
            this.data[ i ].Copy(temp.data[ i ], this.type);
        }
    }

    public int Compare(ValElem elem, int typeCode) 
    {
        ArrayVal temp = (ArrayVal)elem;
        
        for(int i = 0; i < this.count; i++)
        {
            if ( this.data[ i ].Compare(temp.data[ i ], temp.type) != 0)
            {
                return 1;
            }
        }
        return 0;
    }
}
