// $ANTLR 3.0.1 C:\\CheckConstraintExpression.g 2008-06-29 19:55:49
package iisc;
import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class CheckConstraintExpressionLexer extends Lexer {
    public static final int INTEGER=19;
    public static final int XOR=6;
    public static final int COMP_OP=7;
    public static final int COMPARISON_OPERATOR=8;
    public static final int MULTIPLAY_OPERATOR=11;
    public static final int NULL=22;
    public static final int SUB_OPERATOR=10;
    public static final int T29=29;
    public static final int T28=28;
    public static final int T27=27;
    public static final int ADD_OPERATOR=9;
    public static final int NOT=14;
    public static final int T26=26;
    public static final int T25=25;
    public static final int AND=5;
    public static final int CONCAT_OPERATOR=13;
    public static final int Tokens=35;
    public static final int EOF=-1;
    public static final int TRUE=20;
    public static final int WS=23;
    public static final int IN=17;
    public static final int IDENTIFIER=18;
    public static final int OR=4;
    public static final int SQ_STRING_LIT=16;
    public static final int T34=34;
    public static final int COMMENT=24;
    public static final int T33=33;
    public static final int FALSE=21;
    public static final int T30=30;
    public static final int T32=32;
    public static final int LIKE=15;
    public static final int T31=31;
    public static final int DIVISION_OPERATOR=12;
    public CheckConstraintExpressionLexer() {;} 
    public CheckConstraintExpressionLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "C:\\CheckConstraintExpression.g"; }

    // $ANTLR start T25
    public final void mT25() throws RecognitionException {
        try {
            int _type = T25;
            // C:\\CheckConstraintExpression.g:7:5: ( '<=>' )
            // C:\\CheckConstraintExpression.g:7:7: '<=>'
            {
            match("<=>"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T25

    // $ANTLR start T26
    public final void mT26() throws RecognitionException {
        try {
            int _type = T26;
            // C:\\CheckConstraintExpression.g:8:5: ( '=>' )
            // C:\\CheckConstraintExpression.g:8:7: '=>'
            {
            match("=>"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T26

    // $ANTLR start T27
    public final void mT27() throws RecognitionException {
        try {
            int _type = T27;
            // C:\\CheckConstraintExpression.g:9:5: ( '(' )
            // C:\\CheckConstraintExpression.g:9:7: '('
            {
            match('('); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T27

    // $ANTLR start T28
    public final void mT28() throws RecognitionException {
        try {
            int _type = T28;
            // C:\\CheckConstraintExpression.g:10:5: ( ')' )
            // C:\\CheckConstraintExpression.g:10:7: ')'
            {
            match(')'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T28

    // $ANTLR start T29
    public final void mT29() throws RecognitionException {
        try {
            int _type = T29;
            // C:\\CheckConstraintExpression.g:11:5: ( ',' )
            // C:\\CheckConstraintExpression.g:11:7: ','
            {
            match(','); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T29

    // $ANTLR start T30
    public final void mT30() throws RecognitionException {
        try {
            int _type = T30;
            // C:\\CheckConstraintExpression.g:12:5: ( '.' )
            // C:\\CheckConstraintExpression.g:12:7: '.'
            {
            match('.'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T30

    // $ANTLR start T31
    public final void mT31() throws RecognitionException {
        try {
            int _type = T31;
            // C:\\CheckConstraintExpression.g:13:5: ( ':' )
            // C:\\CheckConstraintExpression.g:13:7: ':'
            {
            match(':'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T31

    // $ANTLR start T32
    public final void mT32() throws RecognitionException {
        try {
            int _type = T32;
            // C:\\CheckConstraintExpression.g:14:5: ( '..' )
            // C:\\CheckConstraintExpression.g:14:7: '..'
            {
            match(".."); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T32

    // $ANTLR start T33
    public final void mT33() throws RecognitionException {
        try {
            int _type = T33;
            // C:\\CheckConstraintExpression.g:15:5: ( '{' )
            // C:\\CheckConstraintExpression.g:15:7: '{'
            {
            match('{'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T33

    // $ANTLR start T34
    public final void mT34() throws RecognitionException {
        try {
            int _type = T34;
            // C:\\CheckConstraintExpression.g:16:5: ( '}' )
            // C:\\CheckConstraintExpression.g:16:7: '}'
            {
            match('}'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T34

    // $ANTLR start CONCAT_OPERATOR
    public final void mCONCAT_OPERATOR() throws RecognitionException {
        try {
            int _type = CONCAT_OPERATOR;
            // C:\\CheckConstraintExpression.g:806:17: ( '||' )
            // C:\\CheckConstraintExpression.g:806:19: '||'
            {
            match("||"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end CONCAT_OPERATOR

    // $ANTLR start ADD_OPERATOR
    public final void mADD_OPERATOR() throws RecognitionException {
        try {
            int _type = ADD_OPERATOR;
            // C:\\CheckConstraintExpression.g:808:14: ( '+' )
            // C:\\CheckConstraintExpression.g:808:16: '+'
            {
            match('+'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end ADD_OPERATOR

    // $ANTLR start SUB_OPERATOR
    public final void mSUB_OPERATOR() throws RecognitionException {
        try {
            int _type = SUB_OPERATOR;
            // C:\\CheckConstraintExpression.g:810:14: ( '-' )
            // C:\\CheckConstraintExpression.g:810:16: '-'
            {
            match('-'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end SUB_OPERATOR

    // $ANTLR start MULTIPLAY_OPERATOR
    public final void mMULTIPLAY_OPERATOR() throws RecognitionException {
        try {
            int _type = MULTIPLAY_OPERATOR;
            // C:\\CheckConstraintExpression.g:812:20: ( '*' )
            // C:\\CheckConstraintExpression.g:812:22: '*'
            {
            match('*'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end MULTIPLAY_OPERATOR

    // $ANTLR start DIVISION_OPERATOR
    public final void mDIVISION_OPERATOR() throws RecognitionException {
        try {
            int _type = DIVISION_OPERATOR;
            // C:\\CheckConstraintExpression.g:814:19: ( '/' )
            // C:\\CheckConstraintExpression.g:814:21: '/'
            {
            match('/'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end DIVISION_OPERATOR

    // $ANTLR start COMP_OP
    public final void mCOMP_OP() throws RecognitionException {
        try {
            int _type = COMP_OP;
            // C:\\CheckConstraintExpression.g:816:9: ( '!=' | '==' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='!') ) {
                alt1=1;
            }
            else if ( (LA1_0=='=') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("816:1: COMP_OP : ( '!=' | '==' );", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // C:\\CheckConstraintExpression.g:816:11: '!='
                    {
                    match("!="); 


                    }
                    break;
                case 2 :
                    // C:\\CheckConstraintExpression.g:816:18: '=='
                    {
                    match("=="); 


                    }
                    break;

            }
            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end COMP_OP

    // $ANTLR start IN
    public final void mIN() throws RecognitionException {
        try {
            int _type = IN;
            // C:\\CheckConstraintExpression.g:818:4: ( 'IN' )
            // C:\\CheckConstraintExpression.g:818:6: 'IN'
            {
            match("IN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end IN

    // $ANTLR start LIKE
    public final void mLIKE() throws RecognitionException {
        try {
            int _type = LIKE;
            // C:\\CheckConstraintExpression.g:820:7: ( 'LIKE' )
            // C:\\CheckConstraintExpression.g:820:9: 'LIKE'
            {
            match("LIKE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end LIKE

    // $ANTLR start SQ_STRING_LIT
    public final void mSQ_STRING_LIT() throws RecognitionException {
        try {
            int _type = SQ_STRING_LIT;
            // C:\\CheckConstraintExpression.g:822:15: ( '\\'' ( . )* '\\'' )
            // C:\\CheckConstraintExpression.g:822:17: '\\'' ( . )* '\\''
            {
            match('\''); 
            // C:\\CheckConstraintExpression.g:822:22: ( . )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\'') ) {
                    alt2=2;
                }
                else if ( ((LA2_0>='\u0000' && LA2_0<='&')||(LA2_0>='(' && LA2_0<='\uFFFE')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\CheckConstraintExpression.g:822:23: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match('\''); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end SQ_STRING_LIT

    // $ANTLR start TRUE
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            // C:\\CheckConstraintExpression.g:824:6: ( 'TRUE' )
            // C:\\CheckConstraintExpression.g:824:8: 'TRUE'
            {
            match("TRUE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end TRUE

    // $ANTLR start FALSE
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            // C:\\CheckConstraintExpression.g:826:7: ( 'FALSE' )
            // C:\\CheckConstraintExpression.g:826:9: 'FALSE'
            {
            match("FALSE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end FALSE

    // $ANTLR start NULL
    public final void mNULL() throws RecognitionException {
        try {
            int _type = NULL;
            // C:\\CheckConstraintExpression.g:830:6: ( 'NULL' )
            // C:\\CheckConstraintExpression.g:830:8: 'NULL'
            {
            match("NULL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end NULL

    // $ANTLR start COMPARISON_OPERATOR
    public final void mCOMPARISON_OPERATOR() throws RecognitionException {
        try {
            int _type = COMPARISON_OPERATOR;
            // C:\\CheckConstraintExpression.g:832:21: ( '<' | '>' | '<=' | '>=' )
            int alt3=4;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='<') ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1=='=') ) {
                    alt3=3;
                }
                else {
                    alt3=1;}
            }
            else if ( (LA3_0=='>') ) {
                int LA3_2 = input.LA(2);

                if ( (LA3_2=='=') ) {
                    alt3=4;
                }
                else {
                    alt3=2;}
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("832:1: COMPARISON_OPERATOR : ( '<' | '>' | '<=' | '>=' );", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // C:\\CheckConstraintExpression.g:832:23: '<'
                    {
                    match('<'); 

                    }
                    break;
                case 2 :
                    // C:\\CheckConstraintExpression.g:832:29: '>'
                    {
                    match('>'); 

                    }
                    break;
                case 3 :
                    // C:\\CheckConstraintExpression.g:832:35: '<='
                    {
                    match("<="); 


                    }
                    break;
                case 4 :
                    // C:\\CheckConstraintExpression.g:832:42: '>='
                    {
                    match(">="); 


                    }
                    break;

            }
            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end COMPARISON_OPERATOR

    // $ANTLR start OR
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            // C:\\CheckConstraintExpression.g:834:5: ( 'OR' )
            // C:\\CheckConstraintExpression.g:834:7: 'OR'
            {
            match("OR"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end OR

    // $ANTLR start AND
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            // C:\\CheckConstraintExpression.g:836:5: ( 'AND' )
            // C:\\CheckConstraintExpression.g:836:7: 'AND'
            {
            match("AND"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end AND

    // $ANTLR start XOR
    public final void mXOR() throws RecognitionException {
        try {
            int _type = XOR;
            // C:\\CheckConstraintExpression.g:838:6: ( 'XOR' )
            // C:\\CheckConstraintExpression.g:838:8: 'XOR'
            {
            match("XOR"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end XOR

    // $ANTLR start NOT
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            // C:\\CheckConstraintExpression.g:840:5: ( 'NOT' )
            // C:\\CheckConstraintExpression.g:840:7: 'NOT'
            {
            match("NOT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end NOT

    // $ANTLR start IDENTIFIER
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            // C:\\CheckConstraintExpression.g:843:12: ( ( ( 'A' .. 'Z' | 'a' .. 'z' ) | ( '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) ) ) ( ( '_' )? ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) )* )
            // C:\\CheckConstraintExpression.g:843:14: ( ( 'A' .. 'Z' | 'a' .. 'z' ) | ( '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) ) ) ( ( '_' )? ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) )*
            {
            // C:\\CheckConstraintExpression.g:843:14: ( ( 'A' .. 'Z' | 'a' .. 'z' ) | ( '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0>='A' && LA4_0<='Z')||(LA4_0>='a' && LA4_0<='z')) ) {
                alt4=1;
            }
            else if ( (LA4_0=='_') ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("843:14: ( ( 'A' .. 'Z' | 'a' .. 'z' ) | ( '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) ) )", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // C:\\CheckConstraintExpression.g:843:15: ( 'A' .. 'Z' | 'a' .. 'z' )
                    {
                    if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }


                    }
                    break;
                case 2 :
                    // C:\\CheckConstraintExpression.g:843:39: ( '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) )
                    {
                    // C:\\CheckConstraintExpression.g:843:39: ( '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) )
                    // C:\\CheckConstraintExpression.g:843:40: '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' )
                    {
                    match('_'); 
                    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse =
                            new MismatchedSetException(null,input);
                        recover(mse);    throw mse;
                    }


                    }


                    }
                    break;

            }

            // C:\\CheckConstraintExpression.g:843:81: ( ( '_' )? ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')||(LA6_0>='A' && LA6_0<='Z')||LA6_0=='_'||(LA6_0>='a' && LA6_0<='z')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // C:\\CheckConstraintExpression.g:843:82: ( '_' )? ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' )
            	    {
            	    // C:\\CheckConstraintExpression.g:843:82: ( '_' )?
            	    int alt5=2;
            	    int LA5_0 = input.LA(1);

            	    if ( (LA5_0=='_') ) {
            	        alt5=1;
            	    }
            	    switch (alt5) {
            	        case 1 :
            	            // C:\\CheckConstraintExpression.g:843:83: '_'
            	            {
            	            match('_'); 

            	            }
            	            break;

            	    }

            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end IDENTIFIER

    // $ANTLR start INTEGER
    public final void mINTEGER() throws RecognitionException {
        try {
            int _type = INTEGER;
            // C:\\CheckConstraintExpression.g:845:9: ( ( '0' .. '9' ) ( '0' .. '9' )* )
            // C:\\CheckConstraintExpression.g:845:11: ( '0' .. '9' ) ( '0' .. '9' )*
            {
            // C:\\CheckConstraintExpression.g:845:11: ( '0' .. '9' )
            // C:\\CheckConstraintExpression.g:845:12: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            // C:\\CheckConstraintExpression.g:845:22: ( '0' .. '9' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // C:\\CheckConstraintExpression.g:845:23: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end INTEGER

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            // C:\\CheckConstraintExpression.g:847:4: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // C:\\CheckConstraintExpression.g:847:6: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // C:\\CheckConstraintExpression.g:847:6: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='\t' && LA8_0<='\n')||LA8_0=='\r'||LA8_0==' ') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // C:\\CheckConstraintExpression.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);

            channel=HIDDEN;

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end WS

    // $ANTLR start COMMENT
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            // C:\\CheckConstraintExpression.g:849:9: ( 'REM ' ( . )* '\\n' )
            // C:\\CheckConstraintExpression.g:849:11: 'REM ' ( . )* '\\n'
            {
            match("REM "); 

            // C:\\CheckConstraintExpression.g:849:18: ( . )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0=='\n') ) {
                    alt9=2;
                }
                else if ( ((LA9_0>='\u0000' && LA9_0<='\t')||(LA9_0>='\u000B' && LA9_0<='\uFFFE')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // C:\\CheckConstraintExpression.g:849:19: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            match('\n'); 
            channel=HIDDEN;

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end COMMENT

    public void mTokens() throws RecognitionException {
        // C:\\CheckConstraintExpression.g:1:8: ( T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | CONCAT_OPERATOR | ADD_OPERATOR | SUB_OPERATOR | MULTIPLAY_OPERATOR | DIVISION_OPERATOR | COMP_OP | IN | LIKE | SQ_STRING_LIT | TRUE | FALSE | NULL | COMPARISON_OPERATOR | OR | AND | XOR | NOT | IDENTIFIER | INTEGER | WS | COMMENT )
        int alt10=31;
        switch ( input.LA(1) ) {
        case '<':
            {
            int LA10_1 = input.LA(2);

            if ( (LA10_1=='=') ) {
                int LA10_30 = input.LA(3);

                if ( (LA10_30=='>') ) {
                    alt10=1;
                }
                else {
                    alt10=23;}
            }
            else {
                alt10=23;}
            }
            break;
        case '=':
            {
            int LA10_2 = input.LA(2);

            if ( (LA10_2=='>') ) {
                alt10=2;
            }
            else if ( (LA10_2=='=') ) {
                alt10=16;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1:1: Tokens : ( T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | CONCAT_OPERATOR | ADD_OPERATOR | SUB_OPERATOR | MULTIPLAY_OPERATOR | DIVISION_OPERATOR | COMP_OP | IN | LIKE | SQ_STRING_LIT | TRUE | FALSE | NULL | COMPARISON_OPERATOR | OR | AND | XOR | NOT | IDENTIFIER | INTEGER | WS | COMMENT );", 10, 2, input);

                throw nvae;
            }
            }
            break;
        case '(':
            {
            alt10=3;
            }
            break;
        case ')':
            {
            alt10=4;
            }
            break;
        case ',':
            {
            alt10=5;
            }
            break;
        case '.':
            {
            int LA10_6 = input.LA(2);

            if ( (LA10_6=='.') ) {
                alt10=8;
            }
            else {
                alt10=6;}
            }
            break;
        case ':':
            {
            alt10=7;
            }
            break;
        case '{':
            {
            alt10=9;
            }
            break;
        case '}':
            {
            alt10=10;
            }
            break;
        case '|':
            {
            alt10=11;
            }
            break;
        case '+':
            {
            alt10=12;
            }
            break;
        case '-':
            {
            alt10=13;
            }
            break;
        case '*':
            {
            alt10=14;
            }
            break;
        case '/':
            {
            alt10=15;
            }
            break;
        case '!':
            {
            alt10=16;
            }
            break;
        case 'I':
            {
            int LA10_16 = input.LA(2);

            if ( (LA10_16=='N') ) {
                int LA10_34 = input.LA(3);

                if ( ((LA10_34>='0' && LA10_34<='9')||(LA10_34>='A' && LA10_34<='Z')||LA10_34=='_'||(LA10_34>='a' && LA10_34<='z')) ) {
                    alt10=28;
                }
                else {
                    alt10=17;}
            }
            else {
                alt10=28;}
            }
            break;
        case 'L':
            {
            int LA10_17 = input.LA(2);

            if ( (LA10_17=='I') ) {
                int LA10_35 = input.LA(3);

                if ( (LA10_35=='K') ) {
                    int LA10_46 = input.LA(4);

                    if ( (LA10_46=='E') ) {
                        int LA10_55 = input.LA(5);

                        if ( ((LA10_55>='0' && LA10_55<='9')||(LA10_55>='A' && LA10_55<='Z')||LA10_55=='_'||(LA10_55>='a' && LA10_55<='z')) ) {
                            alt10=28;
                        }
                        else {
                            alt10=18;}
                    }
                    else {
                        alt10=28;}
                }
                else {
                    alt10=28;}
            }
            else {
                alt10=28;}
            }
            break;
        case '\'':
            {
            alt10=19;
            }
            break;
        case 'T':
            {
            int LA10_19 = input.LA(2);

            if ( (LA10_19=='R') ) {
                int LA10_36 = input.LA(3);

                if ( (LA10_36=='U') ) {
                    int LA10_47 = input.LA(4);

                    if ( (LA10_47=='E') ) {
                        int LA10_56 = input.LA(5);

                        if ( ((LA10_56>='0' && LA10_56<='9')||(LA10_56>='A' && LA10_56<='Z')||LA10_56=='_'||(LA10_56>='a' && LA10_56<='z')) ) {
                            alt10=28;
                        }
                        else {
                            alt10=20;}
                    }
                    else {
                        alt10=28;}
                }
                else {
                    alt10=28;}
            }
            else {
                alt10=28;}
            }
            break;
        case 'F':
            {
            int LA10_20 = input.LA(2);

            if ( (LA10_20=='A') ) {
                int LA10_37 = input.LA(3);

                if ( (LA10_37=='L') ) {
                    int LA10_48 = input.LA(4);

                    if ( (LA10_48=='S') ) {
                        int LA10_57 = input.LA(5);

                        if ( (LA10_57=='E') ) {
                            int LA10_65 = input.LA(6);

                            if ( ((LA10_65>='0' && LA10_65<='9')||(LA10_65>='A' && LA10_65<='Z')||LA10_65=='_'||(LA10_65>='a' && LA10_65<='z')) ) {
                                alt10=28;
                            }
                            else {
                                alt10=21;}
                        }
                        else {
                            alt10=28;}
                    }
                    else {
                        alt10=28;}
                }
                else {
                    alt10=28;}
            }
            else {
                alt10=28;}
            }
            break;
        case 'N':
            {
            switch ( input.LA(2) ) {
            case 'O':
                {
                int LA10_38 = input.LA(3);

                if ( (LA10_38=='T') ) {
                    int LA10_49 = input.LA(4);

                    if ( ((LA10_49>='0' && LA10_49<='9')||(LA10_49>='A' && LA10_49<='Z')||LA10_49=='_'||(LA10_49>='a' && LA10_49<='z')) ) {
                        alt10=28;
                    }
                    else {
                        alt10=27;}
                }
                else {
                    alt10=28;}
                }
                break;
            case 'U':
                {
                int LA10_39 = input.LA(3);

                if ( (LA10_39=='L') ) {
                    int LA10_50 = input.LA(4);

                    if ( (LA10_50=='L') ) {
                        int LA10_59 = input.LA(5);

                        if ( ((LA10_59>='0' && LA10_59<='9')||(LA10_59>='A' && LA10_59<='Z')||LA10_59=='_'||(LA10_59>='a' && LA10_59<='z')) ) {
                            alt10=28;
                        }
                        else {
                            alt10=22;}
                    }
                    else {
                        alt10=28;}
                }
                else {
                    alt10=28;}
                }
                break;
            default:
                alt10=28;}

            }
            break;
        case '>':
            {
            alt10=23;
            }
            break;
        case 'O':
            {
            int LA10_23 = input.LA(2);

            if ( (LA10_23=='R') ) {
                int LA10_40 = input.LA(3);

                if ( ((LA10_40>='0' && LA10_40<='9')||(LA10_40>='A' && LA10_40<='Z')||LA10_40=='_'||(LA10_40>='a' && LA10_40<='z')) ) {
                    alt10=28;
                }
                else {
                    alt10=24;}
            }
            else {
                alt10=28;}
            }
            break;
        case 'A':
            {
            int LA10_24 = input.LA(2);

            if ( (LA10_24=='N') ) {
                int LA10_41 = input.LA(3);

                if ( (LA10_41=='D') ) {
                    int LA10_52 = input.LA(4);

                    if ( ((LA10_52>='0' && LA10_52<='9')||(LA10_52>='A' && LA10_52<='Z')||LA10_52=='_'||(LA10_52>='a' && LA10_52<='z')) ) {
                        alt10=28;
                    }
                    else {
                        alt10=25;}
                }
                else {
                    alt10=28;}
            }
            else {
                alt10=28;}
            }
            break;
        case 'X':
            {
            int LA10_25 = input.LA(2);

            if ( (LA10_25=='O') ) {
                int LA10_42 = input.LA(3);

                if ( (LA10_42=='R') ) {
                    int LA10_53 = input.LA(4);

                    if ( ((LA10_53>='0' && LA10_53<='9')||(LA10_53>='A' && LA10_53<='Z')||LA10_53=='_'||(LA10_53>='a' && LA10_53<='z')) ) {
                        alt10=28;
                    }
                    else {
                        alt10=26;}
                }
                else {
                    alt10=28;}
            }
            else {
                alt10=28;}
            }
            break;
        case 'R':
            {
            int LA10_26 = input.LA(2);

            if ( (LA10_26=='E') ) {
                int LA10_43 = input.LA(3);

                if ( (LA10_43=='M') ) {
                    int LA10_54 = input.LA(4);

                    if ( (LA10_54==' ') ) {
                        alt10=31;
                    }
                    else {
                        alt10=28;}
                }
                else {
                    alt10=28;}
            }
            else {
                alt10=28;}
            }
            break;
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'G':
        case 'H':
        case 'J':
        case 'K':
        case 'M':
        case 'P':
        case 'Q':
        case 'S':
        case 'U':
        case 'V':
        case 'W':
        case 'Y':
        case 'Z':
        case '_':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt10=28;
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt10=29;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt10=30;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( T25 | T26 | T27 | T28 | T29 | T30 | T31 | T32 | T33 | T34 | CONCAT_OPERATOR | ADD_OPERATOR | SUB_OPERATOR | MULTIPLAY_OPERATOR | DIVISION_OPERATOR | COMP_OP | IN | LIKE | SQ_STRING_LIT | TRUE | FALSE | NULL | COMPARISON_OPERATOR | OR | AND | XOR | NOT | IDENTIFIER | INTEGER | WS | COMMENT );", 10, 0, input);

            throw nvae;
        }

        switch (alt10) {
            case 1 :
                // C:\\CheckConstraintExpression.g:1:10: T25
                {
                mT25(); 

                }
                break;
            case 2 :
                // C:\\CheckConstraintExpression.g:1:14: T26
                {
                mT26(); 

                }
                break;
            case 3 :
                // C:\\CheckConstraintExpression.g:1:18: T27
                {
                mT27(); 

                }
                break;
            case 4 :
                // C:\\CheckConstraintExpression.g:1:22: T28
                {
                mT28(); 

                }
                break;
            case 5 :
                // C:\\CheckConstraintExpression.g:1:26: T29
                {
                mT29(); 

                }
                break;
            case 6 :
                // C:\\CheckConstraintExpression.g:1:30: T30
                {
                mT30(); 

                }
                break;
            case 7 :
                // C:\\CheckConstraintExpression.g:1:34: T31
                {
                mT31(); 

                }
                break;
            case 8 :
                // C:\\CheckConstraintExpression.g:1:38: T32
                {
                mT32(); 

                }
                break;
            case 9 :
                // C:\\CheckConstraintExpression.g:1:42: T33
                {
                mT33(); 

                }
                break;
            case 10 :
                // C:\\CheckConstraintExpression.g:1:46: T34
                {
                mT34(); 

                }
                break;
            case 11 :
                // C:\\CheckConstraintExpression.g:1:50: CONCAT_OPERATOR
                {
                mCONCAT_OPERATOR(); 

                }
                break;
            case 12 :
                // C:\\CheckConstraintExpression.g:1:66: ADD_OPERATOR
                {
                mADD_OPERATOR(); 

                }
                break;
            case 13 :
                // C:\\CheckConstraintExpression.g:1:79: SUB_OPERATOR
                {
                mSUB_OPERATOR(); 

                }
                break;
            case 14 :
                // C:\\CheckConstraintExpression.g:1:92: MULTIPLAY_OPERATOR
                {
                mMULTIPLAY_OPERATOR(); 

                }
                break;
            case 15 :
                // C:\\CheckConstraintExpression.g:1:111: DIVISION_OPERATOR
                {
                mDIVISION_OPERATOR(); 

                }
                break;
            case 16 :
                // C:\\CheckConstraintExpression.g:1:129: COMP_OP
                {
                mCOMP_OP(); 

                }
                break;
            case 17 :
                // C:\\CheckConstraintExpression.g:1:137: IN
                {
                mIN(); 

                }
                break;
            case 18 :
                // C:\\CheckConstraintExpression.g:1:140: LIKE
                {
                mLIKE(); 

                }
                break;
            case 19 :
                // C:\\CheckConstraintExpression.g:1:145: SQ_STRING_LIT
                {
                mSQ_STRING_LIT(); 

                }
                break;
            case 20 :
                // C:\\CheckConstraintExpression.g:1:159: TRUE
                {
                mTRUE(); 

                }
                break;
            case 21 :
                // C:\\CheckConstraintExpression.g:1:164: FALSE
                {
                mFALSE(); 

                }
                break;
            case 22 :
                // C:\\CheckConstraintExpression.g:1:170: NULL
                {
                mNULL(); 

                }
                break;
            case 23 :
                // C:\\CheckConstraintExpression.g:1:175: COMPARISON_OPERATOR
                {
                mCOMPARISON_OPERATOR(); 

                }
                break;
            case 24 :
                // C:\\CheckConstraintExpression.g:1:195: OR
                {
                mOR(); 

                }
                break;
            case 25 :
                // C:\\CheckConstraintExpression.g:1:198: AND
                {
                mAND(); 

                }
                break;
            case 26 :
                // C:\\CheckConstraintExpression.g:1:202: XOR
                {
                mXOR(); 

                }
                break;
            case 27 :
                // C:\\CheckConstraintExpression.g:1:206: NOT
                {
                mNOT(); 

                }
                break;
            case 28 :
                // C:\\CheckConstraintExpression.g:1:210: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 29 :
                // C:\\CheckConstraintExpression.g:1:221: INTEGER
                {
                mINTEGER(); 

                }
                break;
            case 30 :
                // C:\\CheckConstraintExpression.g:1:229: WS
                {
                mWS(); 

                }
                break;
            case 31 :
                // C:\\CheckConstraintExpression.g:1:232: COMMENT
                {
                mCOMMENT(); 

                }
                break;

        }

    }


 

}