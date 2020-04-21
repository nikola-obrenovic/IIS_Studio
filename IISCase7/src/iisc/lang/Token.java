package iisc.lang;

public interface Token {
	public static final int EOR_TOKEN_TYPE = 1;

	/** imaginary tree navigation type; traverse "get child" link */
	public static final int DOWN = 2;
	/** imaginary tree navigation type; finish with a child list */
	public static final int UP = 3;

	public static final int MIN_TOKEN_TYPE = UP+1;

    public static final int EOF = CharStream.EOF;
	public static final Token EOF_TOKEN = new CommonToken(EOF);
	
	public static final int INVALID_TOKEN_TYPE = 0;
	public static final Token INVALID_TOKEN = new CommonToken(INVALID_TOKEN_TYPE);

	/** In an action, a lexer rule can set token to this SKIP_TOKEN and ANTLR
	 *  will avoid creating a token for this symbol and try to fetch another.
	 */
	public static final Token SKIP_TOKEN = new CommonToken(INVALID_TOKEN_TYPE);

	/** All tokens go to the parser (unless skip() is called in that rule)
	 *  on a particular "channel".  The parser tunes to a particular channel
	 *  so that whitespace etc... can go to the parser on a "hidden" channel.
	 */
	public static final int DEFAULT_CHANNEL = 0;
	
	/** Anything on different channel than DEFAULT_CHANNEL is not parsed
	 *  by parser.
	 */
	public static final int HIDDEN_CHANNEL = 99;

	/** Get the text of the token */
	public abstract String getText();
	public abstract void setText(String text);

	public abstract int getType();
	public abstract void setType(int ttype);
	/**  The line number on which this token was matched; line=1..n */
	public abstract int getLine();
    public abstract void setLine(int line);

	/** The index of the first character relative to the beginning of the line 0..n-1 */
	public abstract int getCharPositionInLine();
	public abstract void setCharPositionInLine(int pos);

	public abstract int getChannel();
	public abstract void setChannel(int channel);

	/** An index from 0..n-1 of the token object in the input stream.
	 *  This must be valid in order to use the ANTLRWorks debugger.
	 */
	public abstract int getTokenIndex();
	public abstract void setTokenIndex(int index);
}
