package iisc.lang;

import java.util.ArrayList;


public class IISCaseCharStream implements CharStream
{
    protected char[] data;
    protected int n;
    protected int p = 0;
    protected int line = 1;
    protected int charPositionInLine = 0;
    protected int markDepth = 0;
    protected ArrayList markers;
    protected int lastMarker;
        
    public IISCaseCharStream(String input) 
    {
        this.data = input.toCharArray();
        this.n = input.length();
    }

    public void reset() 
    {
        p = 0;
        line = 1;
        charPositionInLine = 0;
        markDepth = 0;
    }

    public void reset(String input) 
    {
        this.data = input.toCharArray();
        this.n = input.length();
        p = 0;
        line = 1;
        charPositionInLine = 0;
        markDepth = 0;
    }

     public void consume() 
    {
        if (p < n) 
        {
            charPositionInLine++;

                if (data[p] == '\n') 
                {
                        line++;
                        charPositionInLine = 0;
                }
                p++;
        }
    }

    public int LA(int i) 
    {
            if (i == 0) 
            {
                    return 0;
            }

            if (i < 0) 
            {
                    i++;

                    if ((p + i - 1) < 0) 
                    {
                            return EOF;
                    }
            }

            if ((p + i - 1) >= n) 
            {
                    return EOF;
            }

            int temp;

            temp = data[p + i - 1];
            
            if ( (temp >= 'a') && (temp <= 'z'))
            {
                    return temp - ('a' - 'A');
            }
            else
            {
                    return temp;
            }
    }

    public int LT(int i) 
    {
        return LA(i);
    }

    public int index() 
    {
        return p;
    }

    public int size() 
    {
        return n;
    }

    public int mark() 
    {
        if (markers == null) 
        {
                markers = new ArrayList();
                markers.add(null);
        }

        markDepth++;

        CharStreamState state = null;

        if (markDepth >= markers.size()) 
        {
                state = new CharStreamState();
                markers.add(state);
        } 
        else  
        {
                state = (CharStreamState)markers.get(markDepth);
        }

        state.p = p;
        state.line = line;
        state.charPositionInLine = charPositionInLine;
        lastMarker = markDepth;
        return markDepth;

    }

    public void rewind(int m) 
    {
        CharStreamState state = (CharStreamState)markers.get(m);
        seek(state.p);
        line = state.line;
        charPositionInLine = state.charPositionInLine;
        release(m);
    }

    public void rewind() 
    {
        rewind(lastMarker);
    }

    public void release(int marker) 
    {
        markDepth = marker;
        markDepth--;
    }

    public void seek(int index) 
    {

        if (index <= p) 
        {
                p = index;
                return;
        }

        while (p < index) 
        {
                consume();
        }
    }

    public String substring(int start, int stop) 
    {
        return new String(data, start, stop - start + 1);
    }

    public int getCharPositionInLine()
    {
        return this.charPositionInLine;
    }
    
    public void setCharPositionInLine(int value)
    {
        this.charPositionInLine = value;
    }
    
    public int getLine()
    {
        return this.line;
    }
    
    public void setLine(int value)
    {
        this.line = value;
    }
       
    public class CharStreamState 
    {
        public int p;
        public int line;
        public int charPositionInLine;
    }
}
