 package iisc.lang;
 
public class Parser extends BaseRecognizer {
    protected TokenStream input;

    public Parser()
    {
    }
    
	public Parser(TokenStream input) {
        setTokenStream(input);
    }

	public void reset() {
		super.reset(); // reset all recognizer state variables
		if ( input!=null ) {
			input.seek(0); // rewind the input
		}
	}

	/** Set the token stream and reset the parser */
	public void setTokenStream(TokenStream input) {
		this.input = null;
		reset();
		this.input = input;
	}

        public TokenStream getTokenStream() {
		return input;
	}

	public void traceIn(String ruleName, int ruleIndex)  {
		super.traceIn(ruleName, ruleIndex, input.LT(1));
	}

	public void traceOut(String ruleName, int ruleIndex)  {
		super.traceOut(ruleName, ruleIndex, input.LT(1));
	}
        
        public void AddError(String message)
        {}
        
        public void LoadErrorDescription(String[] args, String code, int linePos, int charPos)
        {}
}
