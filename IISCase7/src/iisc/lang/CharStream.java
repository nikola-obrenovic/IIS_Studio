 package iisc.lang;
 
/** A source of characters for an ANTLR lexer */
public interface CharStream extends IntStream {
    public static final int EOF = -1;

	/** For infinite streams, you don't need this; primarily I'm providing
	 *  a useful interface for action code.  Just make sure actions don't
	 *  use this on streams that don't support it.
	 */
	public String substring(int start, int stop);

	/** Get the ith character of lookahead.  This is the same usually as
	 *  LA(i).  This will be used for labels in the generated
	 *  lexer code.  I'd prefer to return a char here type-wise, but it's
	 *  probably better to be 32-bit clean and be consistent with LA.
	 */
	public int LT(int i);

	/** ANTLR tracks the line information automatically */
	int getLine();

	/** Because this stream can rewind, we need to be able to reset the line */
	void setLine(int line);

	void setCharPositionInLine(int pos);

	/** The index of the character relative to the beginning of the line 0..n-1 */
	int getCharPositionInLine();
}
