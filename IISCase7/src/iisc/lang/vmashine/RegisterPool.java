package iisc.lang.vmashine;

public class RegisterPool {
    int capacity; 
    RegVal[] data;
    int top; 
    
    public RegisterPool() 
    {
        this.capacity = 100;
        this.data = new RegVal[ capacity ];
        this.top = -1;
        
        for(int i = 0; i < this.capacity; i++)
        {
            this.data[ i ] = new RegVal();
        }
    }
    
    public boolean isEmpty()
    {
        return this.top == -1;
    }
    
    public RegVal Peek()
    {
        return this.data[top];
    }
    
    public RegVal Peek(int num)
    {
        return this.data[top - num];
    }
    
    public RegVal Pop()
    {
        return this.data[top--];
    }
    
    public void Pop(int num)
    {
        top = top - num;
    }
    
    public void Push(RegVal  reg)
    {
        if (this.top + 1 == this.capacity)
        {
            RegVal[] temp = this.data;
            this.data = new RegVal[ this.capacity * 2 ];
            
            for(int i = 0; i < this.capacity; i++)
            {
                this.data[ i ] = temp[ i ];
            }
            
            for(int i = this.capacity; i < 2* this.capacity; i++)
            {
                this.data[ i ] = new RegVal();
            }
            
            this.capacity = 2* this.capacity;
        }
        
        this.data[++top] = reg;
    }
    
    public RegVal Push()
    {
        if (this.top + 1 == this.capacity)
        {
            RegVal[] temp = this.data;
            this.data = new RegVal[ this.capacity * 2 ];
            
            for(int i = 0; i < this.capacity; i++)
            {
                this.data[ i ] = temp[ i ];
            }
            
            for(int i = this.capacity; i < 2* this.capacity; i++)
            {
                this.data[ i ] = new RegVal();
            }
            
            this.capacity = 2* this.capacity;
        }
        
        return this.data[++top];
    }
}
