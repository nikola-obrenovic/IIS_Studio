package iisc.lang;

public abstract class Lexer extends BaseRecognizer implements TokenSource {
	
    protected CharStream input;
    protected Token token;
    public Parser parser;

	protected int tokenStartCharIndex = -1;

	/** The line on which the first character of the token resides */
	protected int tokenStartLine;

	/** The character position of first character within the line */
	protected int tokenStartCharPositionInLine;

	/** The channel number for the current token */
	protected int channel;

	/** The token type for the current token */
	protected int type;

	/** You can set the text for the current token to override what is in
	 *  the input char buffer.  Use setText() or can set this instance var.
 	 */
	protected String text;

	public Lexer() {
	}

	public Lexer(CharStream input) {
		this.input = input;
	}

	public void reset() {
		super.reset(); // reset all recognizer state variables
		// wack Lexer state variables
		token = null;
		type = Token.INVALID_TOKEN_TYPE;
		channel = Token.DEFAULT_CHANNEL;
		tokenStartCharIndex = -1;
		tokenStartCharPositionInLine = -1;
		tokenStartLine = -1;
		text = null;
		if ( input!=null ) {
			input.seek(0); // rewind the input
		}
	}

        public void setParser(Parser parser)
        {
            this.parser = parser;
        }
	/** Return a token from this source; i.e., match a token on the char
	 *  stream.
	 */
    public Token nextToken() {
		while (true) {
			token = null;
			channel = Token.DEFAULT_CHANNEL;
			tokenStartCharIndex = input.index();
			tokenStartCharPositionInLine = input.getCharPositionInLine();
			tokenStartLine = input.getLine();
			text = null;
			if ( input.LA(1)==CharStream.EOF ) {
                return Token.EOF_TOKEN;
            }
            try {
                mTokens();
				if ( token==null ) {
					emit();
				}
				else if ( token==Token.SKIP_TOKEN ) {
					continue;
				}
				return token;
			}
            catch (RecognitionException re) {
                reportError(re);
                recover(re);
            }
        }
    }

	public void skip() {
		token = Token.SKIP_TOKEN;
	}

	public abstract void mTokens() throws RecognitionException;

	public void setCharStream(CharStream input) {
		this.input = null;
		reset();
		this.input = input;
	}

	public void emit(Token token) {
		this.token = token;
	}

	public Token emit() {
		Token t = new CommonToken(input, type, channel, tokenStartCharIndex, getCharIndex()-1);
		t.setLine(tokenStartLine);
		t.setText(text);
		t.setCharPositionInLine(tokenStartCharPositionInLine);
		emit(t);
		return t;
	}

	public void match(String s) throws MismatchedTokenException {
        int i = 0;
        while ( i<s.length() ) {
            if ( input.LA(1)!=s.charAt(i) ) {
				if ( backtracking>0 ) {
					failed = true;
					return;
				}
				MismatchedTokenException mte =
					new MismatchedTokenException(s.charAt(i), input);
				recover(mte);
				throw mte;
            }
            i++;
            input.consume();
			failed = false;
        }
    }

    public void matchAny() {
        input.consume();
    }

    public void match(int c) throws MismatchedTokenException {
        if ( input.LA(1)!=c ) {
			if ( backtracking>0 ) {
				failed = true;
				return;
			}
			MismatchedTokenException mte =
				new MismatchedTokenException(c, input);
			recover(mte);
			throw mte;
        }
        input.consume();
		failed = false;
    }

    public void matchRange(int a, int b)
		throws MismatchedRangeException
	{
        if ( input.LA(1)<a || input.LA(1)>b ) {
			if ( backtracking>0 ) {
				failed = true;
				return;
			}
            MismatchedRangeException mre =
				new MismatchedRangeException(a,b,input);
			recover(mre);
			throw mre;
        }
        input.consume();
		failed = false;
    }

    public int getLine() {
        return input.getLine();
    }

    public int getCharPositionInLine() {
        return input.getCharPositionInLine();
    }

	/** What is the index of the current character of lookahead? */
	public int getCharIndex() {
		return input.index();
	}

	/** Return the text matched so far for the current token or any
	 *  text override.
	 */
	public String getText() {
		if ( text!=null ) {
			return text;
		}
		return input.substring(tokenStartCharIndex,getCharIndex()-1);
	}

	/** Set the complete text of this token; it wipes any previous
	 *  changes to the text.
	 */
	public void setText(String text) {
		this.text = text;
	}

	public void reportError(RecognitionException e) {
		/** TODO: not thought about recovery in lexer yet.
		 *
		// if we've already reported an error and have not matched a token
		// yet successfully, don't report any errors.
		if ( errorRecovery ) {
			//System.err.print("[SPURIOUS] ");
			return;
		}
		errorRecovery = true;
		 */

		ReportErrorMessage(e, this.getTokenNames());
	}

        public void ReportErrorMessage(RecognitionException e, String[] tokenNames) 
        {
            try
            {
                String[] errs;
                String message;
    
                if (e instanceof MismatchedTokenException) 
                {
                      MismatchedTokenException mte = (MismatchedTokenException)e;   
                      String[] args = new String[4];
                      args[0] = Integer.toString(e.line);
                      args[1] = Integer.toString(e.charPositionInLine);
                      args[2] = GetCharErrorDisplay(e.c);
                      args[3] = GetCharErrorDisplay(mte.expecting);
                      
                      //message = "Line:"+Integer.toString(e.line)+":" + Integer.toString(e.charPositionInLine) + ". Mismatched character '" + GetCharErrorDisplay(e.c) + "', expected '" + GetCharErrorDisplay(mte.expecting) + "'";
                      this.parser.LoadErrorDescription(args, "MISMATCHED_CHAR", e.line, e.charPositionInLine);
                } 
                else  
                {     
                    if ((e instanceof NoViableAltException) || (e instanceof EarlyExitException) || (e instanceof MismatchedSetException) || (e instanceof MismatchedNotSetException))
                    {
                        String[] args = new String[3];
                        args[0] = Integer.toString(e.line);
                        args[1] = Integer.toString(e.charPositionInLine);
                        args[2] = GetCharErrorDisplay(e.c);
                        //message = "Line:"+Integer.toString(e.line)+":" + Integer.toString(e.charPositionInLine) + ". Unexpected character '" + GetCharErrorDisplay(e.c) + "'";
                        this.parser.LoadErrorDescription(args, "UNEXPECED_CHAR", e.line, e.charPositionInLine);
                    }
                    else
                    {
                        if (e instanceof MismatchedRangeException) 
                        {
            
                            MismatchedRangeException mre = (MismatchedRangeException)e;
                            String[] args = new String[5];
                            args[0] = Integer.toString(e.line);
                            args[1] = Integer.toString(e.charPositionInLine);
                            args[2] = GetCharErrorDisplay(e.c);
                            args[3] = GetCharErrorDisplay(mre.a);
                            args[4] = GetCharErrorDisplay(mre.b);
                            
                            this.parser.LoadErrorDescription(args, "MISMATCHED_CHAR_RANGE", e.line, e.charPositionInLine);
                        } 
                        else  
                        {
                            String[] args = new String[5];
                            args[0] = Integer.toString(e.line);
                            args[1] = Integer.toString(e.charPositionInLine);
                            args[2] = e.token.getText();
                            
                            this.parser.LoadErrorDescription(args, "UNEXPECED_TOKEN", e.line, e.charPositionInLine);
                        }
                    }
                }
                //parser.AddError(message);
            }
            catch(Exception ex)
            {
            
            }
        }

                    
        public String GetCharErrorDisplay(int c) 
        {
    
                try 
                {
                        String s = "";// String.valueOf((char)c);
    
                        switch (c) 
                        {
                                case -1: 
                                        s = "<EOF>";
                                        break;        
    
                                case '\n': 
                                        s = "\\\\n";
                                        break;        
    
                                case '\t': 
                                        s = "\\\\t";
                                        break;        
    
                                case '\r': 
                                        s = "\\\\r";
                                        break;
                                default:
                                        s = "" + (char)c;
                                        break;
                        }
    
                        return "\\\'" + s + "\\\'";
                }
                catch(Exception ex)
                {
                        return "" + c;
                }
        }

	public String getErrorMessage(RecognitionException e, String[] tokenNames) {
		String msg = null;
		if ( e instanceof MismatchedTokenException ) {
			MismatchedTokenException mte = (MismatchedTokenException)e;
			msg = "mismatched character "+getCharErrorDisplay(e.c)+" expecting "+getCharErrorDisplay(mte.expecting);
		}
		else if ( e instanceof NoViableAltException ) {
			NoViableAltException nvae = (NoViableAltException)e;
			// for development, can add "decision=<<"+nvae.grammarDecisionDescription+">>"
			// and "(decision="+nvae.decisionNumber+") and
			// "state "+nvae.stateNumber
			msg = "no viable alternative at character "+getCharErrorDisplay(e.c);
		}
		else if ( e instanceof EarlyExitException ) {
			EarlyExitException eee = (EarlyExitException)e;
			// for development, can add "(decision="+eee.decisionNumber+")"
			msg = "required (...)+ loop did not match anything at character "+getCharErrorDisplay(e.c);
		}
		else if ( e instanceof MismatchedNotSetException ) {
			MismatchedNotSetException mse = (MismatchedNotSetException)e;
			msg = "mismatched character "+getCharErrorDisplay(e.c)+" expecting set "+mse.expecting;
		}
		else if ( e instanceof MismatchedSetException ) {
			MismatchedSetException mse = (MismatchedSetException)e;
			msg = "mismatched character "+getCharErrorDisplay(e.c)+" expecting set "+mse.expecting;
		}
		else if ( e instanceof MismatchedRangeException ) {
			MismatchedRangeException mre = (MismatchedRangeException)e;
			msg = "mismatched character "+getCharErrorDisplay(e.c)+" expecting set "+
				getCharErrorDisplay(mre.a)+".."+getCharErrorDisplay(mre.b);
		}
		else {
			msg = super.getErrorMessage(e, tokenNames);
		}
		return msg;
	}

	public String getCharErrorDisplay(int c) {
		String s = String.valueOf((char)c);
		switch ( c ) {
			case Token.EOF :
				s = "<EOF>";
				break;
			case '\n' :
				s = "\\n";
				break;
			case '\t' :
				s = "\\t";
				break;
			case '\r' :
				s = "\\r";
				break;
		}
		return "'"+s+"'";
	}

	/** Lexers can normally match any char in it's vocabulary after matching
	 *  a token, so do the easy thing and just kill a character and hope
	 *  it all works out.  You can instead use the rule invocation stack
	 *  to do sophisticated error recovery if you are in a fragment rule.
	 */
	public void recover(RecognitionException re) {
		//System.out.println("consuming char "+(char)input.LA(1)+" during recovery");
		//re.printStackTrace();
		input.consume();
	}

	public void traceIn(String ruleName, int ruleIndex)  {
		String inputSymbol = ((char)input.LT(1))+" line="+getLine()+":"+getCharPositionInLine();
		super.traceIn(ruleName, ruleIndex, inputSymbol);
	}

	public void traceOut(String ruleName, int ruleIndex)  {
		String inputSymbol = ((char)input.LT(1))+" line="+getLine()+":"+getCharPositionInLine();
		super.traceOut(ruleName, ruleIndex, inputSymbol);
	}
}
