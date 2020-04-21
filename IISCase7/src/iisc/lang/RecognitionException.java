 package iisc.lang;
 

public class RecognitionException extends Exception {
	/** What input stream did the error occur in? */
	public transient IntStream input;

	/** What is index of token/char were we looking at when the error occurred? */
	public int index;

	/** The current Token when an error occurred.  Since not all streams
	 *  can retrieve the ith Token, we have to track the Token object.
	 *  For parsers.  Even when it's a tree parser, token might be set.
	 */
	public Token token;

	/** If this is a tree parser exception, node is set to the node with
	 *  the problem.
	 */
	public Object node;

	/** The current char when an error occurred. For lexers. */
	public int c;

	/** Track the line at which the error occurred in case this is
	 *  generated from a lexer.  We need to track this since the
	 *  unexpected char doesn't carry the line info.
	 */
	public int line;

	public int charPositionInLine;

	/** If you are parsing a tree node stream, you will encounter som
	 *  imaginary nodes w/o line/col info.  We now search backwards looking
	 *  for most recent token with line/col info, but notify getErrorHeader()
	 *  that info is approximate.
	 */
	public boolean approximateLineInfo;
        
        public ErrorDescription message;
        
	/** Used for remote debugger deserialization */
	public RecognitionException() {
	}

	public RecognitionException(IntStream input) 
        {
		this.input = input;
		this.index = input.index();
		if ( input instanceof TokenStream ) {
			this.token = ((TokenStream)input).LT(1);
			this.line = token.getLine();
			this.charPositionInLine = token.getCharPositionInLine();
		}
		else if ( input instanceof CharStream ) {
			this.c = input.LA(1);
			this.line = ((CharStream)input).getLine();
			this.charPositionInLine = ((CharStream)input).getCharPositionInLine();
		}
		else {
			this.c = input.LA(1);
		}
	}
	
        public RecognitionException(ErrorDescription message, IntStream input)
        {
            this.input = input;
            this.index = input.index();
            this.message = message;

            if (input instanceof TokenStream) 
            {
                this.token = ((TokenStream)input).LT(1);
                this.line = token.getLine();
                this.charPositionInLine = token.getCharPositionInLine();

            }

            if (input instanceof CharStream) 
            {
                this.c = input.LA(1);
                this.line = ((CharStream)input).getLine();
                this.charPositionInLine = ((CharStream)input).getCharPositionInLine();
            } 
            else  
            {
                this.c = input.LA(1);

            }
        }
	/** Return the token type or char of the unexpected input element */
	public int getUnexpectedType() {
		if ( input instanceof TokenStream ) {
			return token.getType();
		}
		else {
			return c;
		}
	}
        
        public ErrorDescription getDescription()
        {
            return this.message;
        }
}
