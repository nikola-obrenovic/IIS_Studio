package iisc.lang;

    
public class IISCaseTokenStream implements TokenStream
{

    Lexer lexer;
    CommonToken[] tok = new CommonToken[6];
    int begin = 0;
    IISCaseCharStream chStream;
    protected int p = -1;
    protected int lastMarker;

    int[] charIndex = new int[6];

    public TokenSource getTokenSource()
    {
        return lexer;
    }

    public Lexer getLexer()
    {
        return lexer;
    }

    public IISCaseCharStream getCharStream()
    {
        return chStream;
    }
    
    public void setCharStream(IISCaseCharStream value)
    {
        chStream = value;
    }

    public void rewind()
    {
        this.begin = 0;
        
        tok [0] = (CommonToken)lexer.nextToken();
        tok [1] = (CommonToken)lexer.nextToken();
        tok [2] = (CommonToken)lexer.nextToken();
        tok [3] = (CommonToken)lexer.nextToken();
        tok [4] = (CommonToken)lexer.nextToken();
    }

    public IISCaseTokenStream(TokenSource lexer, IISCaseCharStream chStream)
    {
        this.lexer = (Lexer)lexer;              
        this.chStream = chStream;

        tok [0] = (CommonToken)lexer.nextToken();
        tok [1] = (CommonToken)lexer.nextToken();
        tok [2] = (CommonToken)lexer.nextToken();
        tok [3] = (CommonToken)lexer.nextToken();
        tok [4] = (CommonToken)lexer.nextToken();
    }

    
    public IISCaseTokenStream(TokenSource lexer)
    {
        this.lexer = (Lexer)lexer;      
        tok [0] = (CommonToken)lexer.nextToken();                       
        tok [1] = (CommonToken)lexer.nextToken();                       
        tok [2] = (CommonToken)lexer.nextToken();                       
        tok [3] = (CommonToken)lexer.nextToken();
        tok [4] = (CommonToken)lexer.nextToken();
    }
    
    
    public void consume()
    {
        //base.Consume();
        p = p + 1;

        tok [ (begin + 5) % 6 ] = (CommonToken)lexer.nextToken();
        
        begin = (begin + 1) % 6;
    }
    
    public int getCharIndex()
    {
        return charIndex[ begin ];
    }

    public int LA(int k)
    {       
        return (tok [ (begin + k - 1) % 6]).getType();
    }

    public Token LT(int k)
    {       
        return tok [ (begin + k - 1) % 6];
    }

    public int index()
    {
        return p;
    }

    public int mark()
    {
        lastMarker = index();
        return lastMarker;
    }

    public int size() 
    {
        return 5;
    }

    public void seek(int index) 
    {
        p = index;
    }

    public void release(int marker) 
    {

    }
    
    public void rewind(int i)
    {
    }

    public Token get(int i) 
    {
        return tok [ (begin + i - 1) % 6];
    }
    
    public String toString(int start, int stop)
    {
        return "";
    }

    public String toString(Token start, Token stop) 
    {
        return "";
    }
}

