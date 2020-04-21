// $ANTLR 3.0.1 D:\\antlwork\\IISCaseLang.g 2012-02-22 22:18:38

package iisc.lang;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class IISCaseLangLexer extends Lexer {
    public static final int T75=75;
    public static final int MOD_OPERATOR=53;
    public static final int FUNCTION=5;
    public static final int T76=76;
    public static final int T73=73;
    public static final int T74=74;
    public static final int T79=79;
    public static final int END_REPEAT=35;
    public static final int T77=77;
    public static final int T78=78;
    public static final int WHILE=31;
    public static final int CONST=14;
    public static final int END_FOR=43;
    public static final int CASE=39;
    public static final int SUB_OPERATOR=51;
    public static final int INPUT=62;
    public static final int UPDATE=21;
    public static final int FOR=41;
    public static final int NOT=57;
    public static final int AND=47;
    public static final int CONCAT_OPERATOR=54;
    public static final int EOF=-1;
    public static final int BREAK=23;
    public static final int T72=72;
    public static final int T71=71;
    public static final int T70=70;
    public static final int IF=27;
    public static final int INOUT=13;
    public static final int TIME=61;
    public static final int IN=11;
    public static final int THEN=28;
    public static final int ITERATOR=15;
    public static final int IDENTIFIER=4;
    public static final int MUL_OPERATOR=52;
    public static final int BEGIN=9;
    public static final int RETURN=24;
    public static final int SIGNAL=25;
    public static final int SQ_STRING_LIT=59;
    public static final int VAR=7;
    public static final int COMMENT=68;
    public static final int SELECT=17;
    public static final int END_WHILE=32;
    public static final int INTO=18;
    public static final int RETURNS=6;
    public static final int LIKE=58;
    public static final int IMPLY_OP=44;
    public static final int INTEGER=16;
    public static final int END_VAR=8;
    public static final int END_SWITCH=37;
    public static final int XOR=45;
    public static final int UNION_OPERATOR=55;
    public static final int COMPARASION_OPERATOR=49;
    public static final int TO=42;
    public static final int END_CASE=40;
    public static final int EQU_OP=48;
    public static final int SWITCH=36;
    public static final int NULL=66;
    public static final int DEFAULT=38;
    public static final int ELSE=29;
    public static final int SET=22;
    public static final int ADD_OPERATOR=50;
    public static final int Tokens=84;
    public static final int TRUE=64;
    public static final int PRINT=26;
    public static final int WS=67;
    public static final int FETCH=20;
    public static final int OUT=12;
    public static final int UNTIL=34;
    public static final int OR=46;
    public static final int END_IF=30;
    public static final int COMMENT_MULTYLINE=69;
    public static final int REPEAT=33;
    public static final int T81=81;
    public static final int END=10;
    public static final int FROM=19;
    public static final int DATE=60;
    public static final int T80=80;
    public static final int FALSE=65;
    public static final int T83=83;
    public static final int T82=82;
    public static final int INTERSECT_OPERATOR=56;
    public static final int OUTPUT=63;
    public IISCaseLangLexer() {;} 
    public IISCaseLangLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "D:\\antlwork\\IISCaseLang.g"; }

    // $ANTLR start T70
    public final void mT70() throws RecognitionException {
        try {
            int _type = T70;
            // D:\\antlwork\\IISCaseLang.g:10:5: ( ',' )
            // D:\\antlwork\\IISCaseLang.g:10:7: ','
            {
            match(','); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T70

    // $ANTLR start T71
    public final void mT71() throws RecognitionException {
        try {
            int _type = T71;
            // D:\\antlwork\\IISCaseLang.g:11:5: ( '(' )
            // D:\\antlwork\\IISCaseLang.g:11:7: '('
            {
            match('('); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T71

    // $ANTLR start T72
    public final void mT72() throws RecognitionException {
        try {
            int _type = T72;
            // D:\\antlwork\\IISCaseLang.g:12:5: ( ')' )
            // D:\\antlwork\\IISCaseLang.g:12:7: ')'
            {
            match(')'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T72

    // $ANTLR start T73
    public final void mT73() throws RecognitionException {
        try {
            int _type = T73;
            // D:\\antlwork\\IISCaseLang.g:13:5: ( ':=' )
            // D:\\antlwork\\IISCaseLang.g:13:7: ':='
            {
            match(":="); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T73

    // $ANTLR start T74
    public final void mT74() throws RecognitionException {
        try {
            int _type = T74;
            // D:\\antlwork\\IISCaseLang.g:14:5: ( ';' )
            // D:\\antlwork\\IISCaseLang.g:14:7: ';'
            {
            match(';'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T74

    // $ANTLR start T75
    public final void mT75() throws RecognitionException {
        try {
            int _type = T75;
            // D:\\antlwork\\IISCaseLang.g:15:5: ( 'ARRAY' )
            // D:\\antlwork\\IISCaseLang.g:15:7: 'ARRAY'
            {
            match("ARRAY"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T75

    // $ANTLR start T76
    public final void mT76() throws RecognitionException {
        try {
            int _type = T76;
            // D:\\antlwork\\IISCaseLang.g:16:5: ( '[' )
            // D:\\antlwork\\IISCaseLang.g:16:7: '['
            {
            match('['); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T76

    // $ANTLR start T77
    public final void mT77() throws RecognitionException {
        try {
            int _type = T77;
            // D:\\antlwork\\IISCaseLang.g:17:5: ( ']' )
            // D:\\antlwork\\IISCaseLang.g:17:7: ']'
            {
            match(']'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T77

    // $ANTLR start T78
    public final void mT78() throws RecognitionException {
        try {
            int _type = T78;
            // D:\\antlwork\\IISCaseLang.g:18:5: ( 'OF' )
            // D:\\antlwork\\IISCaseLang.g:18:7: 'OF'
            {
            match("OF"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T78

    // $ANTLR start T79
    public final void mT79() throws RecognitionException {
        try {
            int _type = T79;
            // D:\\antlwork\\IISCaseLang.g:19:5: ( '{' )
            // D:\\antlwork\\IISCaseLang.g:19:7: '{'
            {
            match('{'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T79

    // $ANTLR start T80
    public final void mT80() throws RecognitionException {
        try {
            int _type = T80;
            // D:\\antlwork\\IISCaseLang.g:20:5: ( '}' )
            // D:\\antlwork\\IISCaseLang.g:20:7: '}'
            {
            match('}'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T80

    // $ANTLR start T81
    public final void mT81() throws RecognitionException {
        try {
            int _type = T81;
            // D:\\antlwork\\IISCaseLang.g:21:5: ( '.' )
            // D:\\antlwork\\IISCaseLang.g:21:7: '.'
            {
            match('.'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T81

    // $ANTLR start T82
    public final void mT82() throws RecognitionException {
        try {
            int _type = T82;
            // D:\\antlwork\\IISCaseLang.g:22:5: ( '=' )
            // D:\\antlwork\\IISCaseLang.g:22:7: '='
            {
            match('='); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T82

    // $ANTLR start T83
    public final void mT83() throws RecognitionException {
        try {
            int _type = T83;
            // D:\\antlwork\\IISCaseLang.g:23:5: ( ':' )
            // D:\\antlwork\\IISCaseLang.g:23:7: ':'
            {
            match(':'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end T83

    // $ANTLR start INTERSECT_OPERATOR
    public final void mINTERSECT_OPERATOR() throws RecognitionException {
        try {
            int _type = INTERSECT_OPERATOR;
            // D:\\antlwork\\IISCaseLang.g:1305:2: ( 'INTERSECT' )
            // D:\\antlwork\\IISCaseLang.g:1306:2: 'INTERSECT'
            {
            match("INTERSECT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end INTERSECT_OPERATOR

    // $ANTLR start UNION_OPERATOR
    public final void mUNION_OPERATOR() throws RecognitionException {
        try {
            int _type = UNION_OPERATOR;
            // D:\\antlwork\\IISCaseLang.g:1309:2: ( 'UNION' )
            // D:\\antlwork\\IISCaseLang.g:1310:2: 'UNION'
            {
            match("UNION"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end UNION_OPERATOR

    // $ANTLR start CASE
    public final void mCASE() throws RecognitionException {
        try {
            int _type = CASE;
            // D:\\antlwork\\IISCaseLang.g:1313:2: ( 'CASE' )
            // D:\\antlwork\\IISCaseLang.g:1314:2: 'CASE'
            {
            match("CASE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end CASE

    // $ANTLR start END_CASE
    public final void mEND_CASE() throws RecognitionException {
        try {
            int _type = END_CASE;
            // D:\\antlwork\\IISCaseLang.g:1317:2: ( 'END_CASE' )
            // D:\\antlwork\\IISCaseLang.g:1318:2: 'END_CASE'
            {
            match("END_CASE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end END_CASE

    // $ANTLR start DEFAULT
    public final void mDEFAULT() throws RecognitionException {
        try {
            int _type = DEFAULT;
            // D:\\antlwork\\IISCaseLang.g:1321:2: ( 'DEFAULT' )
            // D:\\antlwork\\IISCaseLang.g:1322:2: 'DEFAULT'
            {
            match("DEFAULT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end DEFAULT

    // $ANTLR start SWITCH
    public final void mSWITCH() throws RecognitionException {
        try {
            int _type = SWITCH;
            // D:\\antlwork\\IISCaseLang.g:1324:2: ( 'SWITCH' )
            // D:\\antlwork\\IISCaseLang.g:1325:2: 'SWITCH'
            {
            match("SWITCH"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end SWITCH

    // $ANTLR start END_SWITCH
    public final void mEND_SWITCH() throws RecognitionException {
        try {
            int _type = END_SWITCH;
            // D:\\antlwork\\IISCaseLang.g:1328:2: ( 'END_SWITCH' )
            // D:\\antlwork\\IISCaseLang.g:1329:2: 'END_SWITCH'
            {
            match("END_SWITCH"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end END_SWITCH

    // $ANTLR start BREAK
    public final void mBREAK() throws RecognitionException {
        try {
            int _type = BREAK;
            // D:\\antlwork\\IISCaseLang.g:1332:2: ( 'BREAK' )
            // D:\\antlwork\\IISCaseLang.g:1333:2: 'BREAK'
            {
            match("BREAK"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end BREAK

    // $ANTLR start SELECT
    public final void mSELECT() throws RecognitionException {
        try {
            int _type = SELECT;
            // D:\\antlwork\\IISCaseLang.g:1336:2: ( 'SELECT' )
            // D:\\antlwork\\IISCaseLang.g:1337:2: 'SELECT'
            {
            match("SELECT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end SELECT

    // $ANTLR start INTO
    public final void mINTO() throws RecognitionException {
        try {
            int _type = INTO;
            // D:\\antlwork\\IISCaseLang.g:1339:2: ( 'INTO' )
            // D:\\antlwork\\IISCaseLang.g:1340:2: 'INTO'
            {
            match("INTO"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end INTO

    // $ANTLR start FROM
    public final void mFROM() throws RecognitionException {
        try {
            int _type = FROM;
            // D:\\antlwork\\IISCaseLang.g:1343:2: ( 'FROM' )
            // D:\\antlwork\\IISCaseLang.g:1344:2: 'FROM'
            {
            match("FROM"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end FROM

    // $ANTLR start RETURN
    public final void mRETURN() throws RecognitionException {
        try {
            int _type = RETURN;
            // D:\\antlwork\\IISCaseLang.g:1346:2: ( 'RETURN' )
            // D:\\antlwork\\IISCaseLang.g:1347:2: 'RETURN'
            {
            match("RETURN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RETURN

    // $ANTLR start FETCH
    public final void mFETCH() throws RecognitionException {
        try {
            int _type = FETCH;
            // D:\\antlwork\\IISCaseLang.g:1350:2: ( 'FETCH' )
            // D:\\antlwork\\IISCaseLang.g:1351:2: 'FETCH'
            {
            match("FETCH"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end FETCH

    // $ANTLR start UPDATE
    public final void mUPDATE() throws RecognitionException {
        try {
            int _type = UPDATE;
            // D:\\antlwork\\IISCaseLang.g:1354:2: ( 'UPDATE' )
            // D:\\antlwork\\IISCaseLang.g:1355:2: 'UPDATE'
            {
            match("UPDATE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end UPDATE

    // $ANTLR start SET
    public final void mSET() throws RecognitionException {
        try {
            int _type = SET;
            // D:\\antlwork\\IISCaseLang.g:1358:2: ( 'SET' )
            // D:\\antlwork\\IISCaseLang.g:1359:2: 'SET'
            {
            match("SET"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end SET

    // $ANTLR start SIGNAL
    public final void mSIGNAL() throws RecognitionException {
        try {
            int _type = SIGNAL;
            // D:\\antlwork\\IISCaseLang.g:1362:2: ( 'SIGNAL' )
            // D:\\antlwork\\IISCaseLang.g:1363:2: 'SIGNAL'
            {
            match("SIGNAL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end SIGNAL

    // $ANTLR start RETURNS
    public final void mRETURNS() throws RecognitionException {
        try {
            int _type = RETURNS;
            // D:\\antlwork\\IISCaseLang.g:1366:2: ( 'RETURNS' )
            // D:\\antlwork\\IISCaseLang.g:1367:2: 'RETURNS'
            {
            match("RETURNS"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RETURNS

    // $ANTLR start END
    public final void mEND() throws RecognitionException {
        try {
            int _type = END;
            // D:\\antlwork\\IISCaseLang.g:1369:5: ( 'END' )
            // D:\\antlwork\\IISCaseLang.g:1370:2: 'END'
            {
            match("END"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end END

    // $ANTLR start END_IF
    public final void mEND_IF() throws RecognitionException {
        try {
            int _type = END_IF;
            // D:\\antlwork\\IISCaseLang.g:1372:8: ( 'END_IF' )
            // D:\\antlwork\\IISCaseLang.g:1373:2: 'END_IF'
            {
            match("END_IF"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end END_IF

    // $ANTLR start BEGIN
    public final void mBEGIN() throws RecognitionException {
        try {
            int _type = BEGIN;
            // D:\\antlwork\\IISCaseLang.g:1375:7: ( 'BEGIN' )
            // D:\\antlwork\\IISCaseLang.g:1376:2: 'BEGIN'
            {
            match("BEGIN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end BEGIN

    // $ANTLR start VAR
    public final void mVAR() throws RecognitionException {
        try {
            int _type = VAR;
            // D:\\antlwork\\IISCaseLang.g:1378:5: ( 'VAR' )
            // D:\\antlwork\\IISCaseLang.g:1379:2: 'VAR'
            {
            match("VAR"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end VAR

    // $ANTLR start END_VAR
    public final void mEND_VAR() throws RecognitionException {
        try {
            int _type = END_VAR;
            // D:\\antlwork\\IISCaseLang.g:1381:9: ( 'END_VAR' )
            // D:\\antlwork\\IISCaseLang.g:1382:2: 'END_VAR'
            {
            match("END_VAR"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end END_VAR

    // $ANTLR start INPUT
    public final void mINPUT() throws RecognitionException {
        try {
            int _type = INPUT;
            // D:\\antlwork\\IISCaseLang.g:1384:7: ( 'INPUT' )
            // D:\\antlwork\\IISCaseLang.g:1385:2: 'INPUT'
            {
            match("INPUT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end INPUT

    // $ANTLR start OUTPUT
    public final void mOUTPUT() throws RecognitionException {
        try {
            int _type = OUTPUT;
            // D:\\antlwork\\IISCaseLang.g:1388:2: ( 'OUTPUT' )
            // D:\\antlwork\\IISCaseLang.g:1389:2: 'OUTPUT'
            {
            match("OUTPUT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end OUTPUT

    // $ANTLR start CONST
    public final void mCONST() throws RecognitionException {
        try {
            int _type = CONST;
            // D:\\antlwork\\IISCaseLang.g:1392:2: ( 'CONST' )
            // D:\\antlwork\\IISCaseLang.g:1393:2: 'CONST'
            {
            match("CONST"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end CONST

    // $ANTLR start IF
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            // D:\\antlwork\\IISCaseLang.g:1395:4: ( 'IF' )
            // D:\\antlwork\\IISCaseLang.g:1396:2: 'IF'
            {
            match("IF"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end IF

    // $ANTLR start THEN
    public final void mTHEN() throws RecognitionException {
        try {
            int _type = THEN;
            // D:\\antlwork\\IISCaseLang.g:1398:6: ( 'THEN' )
            // D:\\antlwork\\IISCaseLang.g:1399:2: 'THEN'
            {
            match("THEN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end THEN

    // $ANTLR start ELSE
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            // D:\\antlwork\\IISCaseLang.g:1401:6: ( 'ELSE' )
            // D:\\antlwork\\IISCaseLang.g:1402:2: 'ELSE'
            {
            match("ELSE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end ELSE

    // $ANTLR start WHILE
    public final void mWHILE() throws RecognitionException {
        try {
            int _type = WHILE;
            // D:\\antlwork\\IISCaseLang.g:1404:7: ( 'WHILE' )
            // D:\\antlwork\\IISCaseLang.g:1405:2: 'WHILE'
            {
            match("WHILE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end WHILE

    // $ANTLR start END_WHILE
    public final void mEND_WHILE() throws RecognitionException {
        try {
            int _type = END_WHILE;
            // D:\\antlwork\\IISCaseLang.g:1407:11: ( 'END_WHILE' )
            // D:\\antlwork\\IISCaseLang.g:1408:2: 'END_WHILE'
            {
            match("END_WHILE"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end END_WHILE

    // $ANTLR start REPEAT
    public final void mREPEAT() throws RecognitionException {
        try {
            int _type = REPEAT;
            // D:\\antlwork\\IISCaseLang.g:1410:9: ( 'REPEAT' )
            // D:\\antlwork\\IISCaseLang.g:1411:2: 'REPEAT'
            {
            match("REPEAT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end REPEAT

    // $ANTLR start END_REPEAT
    public final void mEND_REPEAT() throws RecognitionException {
        try {
            int _type = END_REPEAT;
            // D:\\antlwork\\IISCaseLang.g:1413:13: ( 'END_REPEAT' )
            // D:\\antlwork\\IISCaseLang.g:1414:2: 'END_REPEAT'
            {
            match("END_REPEAT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end END_REPEAT

    // $ANTLR start UNTIL
    public final void mUNTIL() throws RecognitionException {
        try {
            int _type = UNTIL;
            // D:\\antlwork\\IISCaseLang.g:1416:7: ( 'UNTIL' )
            // D:\\antlwork\\IISCaseLang.g:1417:2: 'UNTIL'
            {
            match("UNTIL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end UNTIL

    // $ANTLR start FOR
    public final void mFOR() throws RecognitionException {
        try {
            int _type = FOR;
            // D:\\antlwork\\IISCaseLang.g:1420:2: ( 'FOR' )
            // D:\\antlwork\\IISCaseLang.g:1421:2: 'FOR'
            {
            match("FOR"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end FOR

    // $ANTLR start END_FOR
    public final void mEND_FOR() throws RecognitionException {
        try {
            int _type = END_FOR;
            // D:\\antlwork\\IISCaseLang.g:1424:2: ( 'END_FOR' )
            // D:\\antlwork\\IISCaseLang.g:1425:2: 'END_FOR'
            {
            match("END_FOR"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end END_FOR

    // $ANTLR start TO
    public final void mTO() throws RecognitionException {
        try {
            int _type = TO;
            // D:\\antlwork\\IISCaseLang.g:1427:4: ( 'TO' )
            // D:\\antlwork\\IISCaseLang.g:1428:2: 'TO'
            {
            match("TO"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end TO

    // $ANTLR start ITERATOR
    public final void mITERATOR() throws RecognitionException {
        try {
            int _type = ITERATOR;
            // D:\\antlwork\\IISCaseLang.g:1482:2: ( 'ITERATOR' )
            // D:\\antlwork\\IISCaseLang.g:1483:2: 'ITERATOR'
            {
            match("ITERATOR"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end ITERATOR

    // $ANTLR start FUNCTION
    public final void mFUNCTION() throws RecognitionException {
        try {
            int _type = FUNCTION;
            // D:\\antlwork\\IISCaseLang.g:1486:2: ( 'FUNCTION' )
            // D:\\antlwork\\IISCaseLang.g:1487:2: 'FUNCTION'
            {
            match("FUNCTION"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end FUNCTION

    // $ANTLR start CONCAT_OPERATOR
    public final void mCONCAT_OPERATOR() throws RecognitionException {
        try {
            int _type = CONCAT_OPERATOR;
            // D:\\antlwork\\IISCaseLang.g:1489:17: ( '||' )
            // D:\\antlwork\\IISCaseLang.g:1489:19: '||'
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
            // D:\\antlwork\\IISCaseLang.g:1491:14: ( '+' )
            // D:\\antlwork\\IISCaseLang.g:1491:16: '+'
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
            // D:\\antlwork\\IISCaseLang.g:1493:14: ( '-' )
            // D:\\antlwork\\IISCaseLang.g:1493:16: '-'
            {
            match('-'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end SUB_OPERATOR

    // $ANTLR start MUL_OPERATOR
    public final void mMUL_OPERATOR() throws RecognitionException {
        try {
            int _type = MUL_OPERATOR;
            // D:\\antlwork\\IISCaseLang.g:1495:14: ( '*' | '/' )
            // D:\\antlwork\\IISCaseLang.g:
            {
            if ( input.LA(1)=='*'||input.LA(1)=='/' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end MUL_OPERATOR

    // $ANTLR start MOD_OPERATOR
    public final void mMOD_OPERATOR() throws RecognitionException {
        try {
            int _type = MOD_OPERATOR;
            // D:\\antlwork\\IISCaseLang.g:1497:14: ( '%' )
            // D:\\antlwork\\IISCaseLang.g:1497:16: '%'
            {
            match('%'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end MOD_OPERATOR

    // $ANTLR start EQU_OP
    public final void mEQU_OP() throws RecognitionException {
        try {
            int _type = EQU_OP;
            // D:\\antlwork\\IISCaseLang.g:1499:8: ( '!=' | '==' )
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
                    new NoViableAltException("1499:1: EQU_OP : ( '!=' | '==' );", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // D:\\antlwork\\IISCaseLang.g:1499:10: '!='
                    {
                    match("!="); 


                    }
                    break;
                case 2 :
                    // D:\\antlwork\\IISCaseLang.g:1499:17: '=='
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
    // $ANTLR end EQU_OP

    // $ANTLR start IN
    public final void mIN() throws RecognitionException {
        try {
            int _type = IN;
            // D:\\antlwork\\IISCaseLang.g:1501:4: ( 'IN' )
            // D:\\antlwork\\IISCaseLang.g:1501:6: 'IN'
            {
            match("IN"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end IN

    // $ANTLR start OUT
    public final void mOUT() throws RecognitionException {
        try {
            int _type = OUT;
            // D:\\antlwork\\IISCaseLang.g:1503:5: ( 'OUT' )
            // D:\\antlwork\\IISCaseLang.g:1503:7: 'OUT'
            {
            match("OUT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end OUT

    // $ANTLR start INOUT
    public final void mINOUT() throws RecognitionException {
        try {
            int _type = INOUT;
            // D:\\antlwork\\IISCaseLang.g:1505:7: ( 'INOUT' )
            // D:\\antlwork\\IISCaseLang.g:1505:9: 'INOUT'
            {
            match("INOUT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end INOUT

    // $ANTLR start LIKE
    public final void mLIKE() throws RecognitionException {
        try {
            int _type = LIKE;
            // D:\\antlwork\\IISCaseLang.g:1507:7: ( 'LIKE' )
            // D:\\antlwork\\IISCaseLang.g:1507:9: 'LIKE'
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
            // D:\\antlwork\\IISCaseLang.g:1509:15: ( '\\'' ( (~ ( '\\'' | '\\\\' ) ) | '\\\\' '\\'' | '\\\\' '\\\\' | '\\\\' 'N' | '\\\\' 'R' | '\\\\' 'T' | '\\\\' 'n' | '\\\\' 'r' | '\\\\' 't' )* '\\'' )
            // D:\\antlwork\\IISCaseLang.g:1509:17: '\\'' ( (~ ( '\\'' | '\\\\' ) ) | '\\\\' '\\'' | '\\\\' '\\\\' | '\\\\' 'N' | '\\\\' 'R' | '\\\\' 'T' | '\\\\' 'n' | '\\\\' 'r' | '\\\\' 't' )* '\\''
            {
            match('\''); 
            // D:\\antlwork\\IISCaseLang.g:1509:22: ( (~ ( '\\'' | '\\\\' ) ) | '\\\\' '\\'' | '\\\\' '\\\\' | '\\\\' 'N' | '\\\\' 'R' | '\\\\' 'T' | '\\\\' 'n' | '\\\\' 'r' | '\\\\' 't' )*
            loop2:
            do {
                int alt2=10;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='\u0000' && LA2_0<='&')||(LA2_0>='(' && LA2_0<='[')||(LA2_0>=']' && LA2_0<='\uFFFE')) ) {
                    alt2=1;
                }
                else if ( (LA2_0=='\\') ) {
                    switch ( input.LA(2) ) {
                    case 'T':
                        {
                        alt2=6;
                        }
                        break;
                    case 'N':
                        {
                        alt2=4;
                        }
                        break;
                    case 'n':
                        {
                        alt2=7;
                        }
                        break;
                    case '\\':
                        {
                        alt2=3;
                        }
                        break;
                    case 'r':
                        {
                        alt2=8;
                        }
                        break;
                    case '\'':
                        {
                        alt2=2;
                        }
                        break;
                    case 'R':
                        {
                        alt2=5;
                        }
                        break;
                    case 't':
                        {
                        alt2=9;
                        }
                        break;

                    }

                }


                switch (alt2) {
            	case 1 :
            	    // D:\\antlwork\\IISCaseLang.g:1509:24: (~ ( '\\'' | '\\\\' ) )
            	    {
            	    // D:\\antlwork\\IISCaseLang.g:1509:24: (~ ( '\\'' | '\\\\' ) )
            	    // D:\\antlwork\\IISCaseLang.g:1509:25: ~ ( '\\'' | '\\\\' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
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
            	case 2 :
            	    // D:\\antlwork\\IISCaseLang.g:1509:43: '\\\\' '\\''
            	    {
            	    match('\\'); 
            	    match('\''); 

            	    }
            	    break;
            	case 3 :
            	    // D:\\antlwork\\IISCaseLang.g:1509:55: '\\\\' '\\\\'
            	    {
            	    match('\\'); 
            	    match('\\'); 

            	    }
            	    break;
            	case 4 :
            	    // D:\\antlwork\\IISCaseLang.g:1509:67: '\\\\' 'N'
            	    {
            	    match('\\'); 
            	    match('N'); 

            	    }
            	    break;
            	case 5 :
            	    // D:\\antlwork\\IISCaseLang.g:1509:78: '\\\\' 'R'
            	    {
            	    match('\\'); 
            	    match('R'); 

            	    }
            	    break;
            	case 6 :
            	    // D:\\antlwork\\IISCaseLang.g:1509:89: '\\\\' 'T'
            	    {
            	    match('\\'); 
            	    match('T'); 

            	    }
            	    break;
            	case 7 :
            	    // D:\\antlwork\\IISCaseLang.g:1510:6: '\\\\' 'n'
            	    {
            	    match('\\'); 
            	    match('n'); 

            	    }
            	    break;
            	case 8 :
            	    // D:\\antlwork\\IISCaseLang.g:1510:17: '\\\\' 'r'
            	    {
            	    match('\\'); 
            	    match('r'); 

            	    }
            	    break;
            	case 9 :
            	    // D:\\antlwork\\IISCaseLang.g:1510:28: '\\\\' 't'
            	    {
            	    match('\\'); 
            	    match('t'); 

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
            // D:\\antlwork\\IISCaseLang.g:1512:6: ( 'TRUE' )
            // D:\\antlwork\\IISCaseLang.g:1512:8: 'TRUE'
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
            // D:\\antlwork\\IISCaseLang.g:1514:7: ( 'FALSE' )
            // D:\\antlwork\\IISCaseLang.g:1514:9: 'FALSE'
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
            // D:\\antlwork\\IISCaseLang.g:1516:6: ( 'NULL' )
            // D:\\antlwork\\IISCaseLang.g:1516:8: 'NULL'
            {
            match("NULL"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end NULL

    // $ANTLR start COMPARASION_OPERATOR
    public final void mCOMPARASION_OPERATOR() throws RecognitionException {
        try {
            int _type = COMPARASION_OPERATOR;
            // D:\\antlwork\\IISCaseLang.g:1518:22: ( '<' | '>' | '<=' | '>=' )
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
                    new NoViableAltException("1518:1: COMPARASION_OPERATOR : ( '<' | '>' | '<=' | '>=' );", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // D:\\antlwork\\IISCaseLang.g:1518:24: '<'
                    {
                    match('<'); 

                    }
                    break;
                case 2 :
                    // D:\\antlwork\\IISCaseLang.g:1518:30: '>'
                    {
                    match('>'); 

                    }
                    break;
                case 3 :
                    // D:\\antlwork\\IISCaseLang.g:1518:36: '<='
                    {
                    match("<="); 


                    }
                    break;
                case 4 :
                    // D:\\antlwork\\IISCaseLang.g:1518:43: '>='
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
    // $ANTLR end COMPARASION_OPERATOR

    // $ANTLR start IMPLY_OP
    public final void mIMPLY_OP() throws RecognitionException {
        try {
            int _type = IMPLY_OP;
            // D:\\antlwork\\IISCaseLang.g:1520:10: ( '=>' )
            // D:\\antlwork\\IISCaseLang.g:1520:12: '=>'
            {
            match("=>"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end IMPLY_OP

    // $ANTLR start OR
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            // D:\\antlwork\\IISCaseLang.g:1522:5: ( 'OR' )
            // D:\\antlwork\\IISCaseLang.g:1522:7: 'OR'
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
            // D:\\antlwork\\IISCaseLang.g:1524:5: ( 'AND' )
            // D:\\antlwork\\IISCaseLang.g:1524:7: 'AND'
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
            // D:\\antlwork\\IISCaseLang.g:1526:6: ( 'XOR' )
            // D:\\antlwork\\IISCaseLang.g:1526:8: 'XOR'
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
            // D:\\antlwork\\IISCaseLang.g:1528:5: ( 'NOT' )
            // D:\\antlwork\\IISCaseLang.g:1528:7: 'NOT'
            {
            match("NOT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end NOT

    // $ANTLR start PRINT
    public final void mPRINT() throws RecognitionException {
        try {
            int _type = PRINT;
            // D:\\antlwork\\IISCaseLang.g:1530:7: ( 'PRINT' )
            // D:\\antlwork\\IISCaseLang.g:1530:9: 'PRINT'
            {
            match("PRINT"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end PRINT

    // $ANTLR start IDENTIFIER
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            // D:\\antlwork\\IISCaseLang.g:1533:12: ( ( ( 'A' .. 'Z' | 'a' .. 'z' ) | ( '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) ) ) ( ( '_' )? ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) )* )
            // D:\\antlwork\\IISCaseLang.g:1533:14: ( ( 'A' .. 'Z' | 'a' .. 'z' ) | ( '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) ) ) ( ( '_' )? ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) )*
            {
            // D:\\antlwork\\IISCaseLang.g:1533:14: ( ( 'A' .. 'Z' | 'a' .. 'z' ) | ( '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) ) )
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
                    new NoViableAltException("1533:14: ( ( 'A' .. 'Z' | 'a' .. 'z' ) | ( '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) ) )", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // D:\\antlwork\\IISCaseLang.g:1533:15: ( 'A' .. 'Z' | 'a' .. 'z' )
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
                    // D:\\antlwork\\IISCaseLang.g:1533:39: ( '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) )
                    {
                    // D:\\antlwork\\IISCaseLang.g:1533:39: ( '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) )
                    // D:\\antlwork\\IISCaseLang.g:1533:40: '_' ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' )
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

            // D:\\antlwork\\IISCaseLang.g:1533:81: ( ( '_' )? ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')||(LA6_0>='A' && LA6_0<='Z')||LA6_0=='_'||(LA6_0>='a' && LA6_0<='z')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // D:\\antlwork\\IISCaseLang.g:1533:82: ( '_' )? ( ( 'A' .. 'Z' | 'a' .. 'z' ) | '0' .. '9' )
            	    {
            	    // D:\\antlwork\\IISCaseLang.g:1533:82: ( '_' )?
            	    int alt5=2;
            	    int LA5_0 = input.LA(1);

            	    if ( (LA5_0=='_') ) {
            	        alt5=1;
            	    }
            	    switch (alt5) {
            	        case 1 :
            	            // D:\\antlwork\\IISCaseLang.g:1533:83: '_'
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

    // $ANTLR start TIME
    public final void mTIME() throws RecognitionException {
        try {
            int _type = TIME;
            // D:\\antlwork\\IISCaseLang.g:1535:6: ( ( '0' .. '9' ) ( '0' .. '9' ) ':' ( '0' .. '9' ) ( '0' .. '9' ) ':' ( '0' .. '9' ) ( '0' .. '9' ) )
            // D:\\antlwork\\IISCaseLang.g:1535:8: ( '0' .. '9' ) ( '0' .. '9' ) ':' ( '0' .. '9' ) ( '0' .. '9' ) ':' ( '0' .. '9' ) ( '0' .. '9' )
            {
            // D:\\antlwork\\IISCaseLang.g:1535:8: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1535:9: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            // D:\\antlwork\\IISCaseLang.g:1535:19: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1535:20: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            match(':'); 
            // D:\\antlwork\\IISCaseLang.g:1535:34: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1535:35: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            // D:\\antlwork\\IISCaseLang.g:1535:45: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1535:46: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            match(':'); 
            // D:\\antlwork\\IISCaseLang.g:1535:60: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1535:61: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            // D:\\antlwork\\IISCaseLang.g:1535:71: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1535:72: '0' .. '9'
            {
            matchRange('0','9'); 

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end TIME

    // $ANTLR start DATE
    public final void mDATE() throws RecognitionException {
        try {
            int _type = DATE;
            // D:\\antlwork\\IISCaseLang.g:1537:6: ( ( '0' .. '9' ) ( '0' .. '9' ) '-' ( '0' .. '9' ) ( '0' .. '9' ) '-' ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' ) )
            // D:\\antlwork\\IISCaseLang.g:1537:8: ( '0' .. '9' ) ( '0' .. '9' ) '-' ( '0' .. '9' ) ( '0' .. '9' ) '-' ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' )
            {
            // D:\\antlwork\\IISCaseLang.g:1537:8: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1537:9: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            // D:\\antlwork\\IISCaseLang.g:1537:19: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1537:20: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            match('-'); 
            // D:\\antlwork\\IISCaseLang.g:1537:34: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1537:35: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            // D:\\antlwork\\IISCaseLang.g:1537:45: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1537:46: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            match('-'); 
            // D:\\antlwork\\IISCaseLang.g:1537:60: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1537:61: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            // D:\\antlwork\\IISCaseLang.g:1537:71: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1537:72: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            // D:\\antlwork\\IISCaseLang.g:1537:81: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1537:82: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            // D:\\antlwork\\IISCaseLang.g:1537:92: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1537:93: '0' .. '9'
            {
            matchRange('0','9'); 

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end DATE

    // $ANTLR start INTEGER
    public final void mINTEGER() throws RecognitionException {
        try {
            int _type = INTEGER;
            // D:\\antlwork\\IISCaseLang.g:1539:9: ( ( '0' .. '9' ) ( '0' .. '9' )* )
            // D:\\antlwork\\IISCaseLang.g:1539:11: ( '0' .. '9' ) ( '0' .. '9' )*
            {
            // D:\\antlwork\\IISCaseLang.g:1539:11: ( '0' .. '9' )
            // D:\\antlwork\\IISCaseLang.g:1539:12: '0' .. '9'
            {
            matchRange('0','9'); 

            }

            // D:\\antlwork\\IISCaseLang.g:1539:22: ( '0' .. '9' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // D:\\antlwork\\IISCaseLang.g:1539:23: '0' .. '9'
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
            // D:\\antlwork\\IISCaseLang.g:1541:4: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // D:\\antlwork\\IISCaseLang.g:1541:6: ( ' ' | '\\t' | '\\r' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }

            skip();

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
            // D:\\antlwork\\IISCaseLang.g:1543:9: ( ( ( '//' ( . )* '\\n' ) | ( 'REM ' ( . )* '\\n' ) ) )
            // D:\\antlwork\\IISCaseLang.g:1543:11: ( ( '//' ( . )* '\\n' ) | ( 'REM ' ( . )* '\\n' ) )
            {
            // D:\\antlwork\\IISCaseLang.g:1543:11: ( ( '//' ( . )* '\\n' ) | ( 'REM ' ( . )* '\\n' ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='/') ) {
                alt10=1;
            }
            else if ( (LA10_0=='R') ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("1543:11: ( ( '//' ( . )* '\\n' ) | ( 'REM ' ( . )* '\\n' ) )", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // D:\\antlwork\\IISCaseLang.g:1543:12: ( '//' ( . )* '\\n' )
                    {
                    // D:\\antlwork\\IISCaseLang.g:1543:12: ( '//' ( . )* '\\n' )
                    // D:\\antlwork\\IISCaseLang.g:1543:13: '//' ( . )* '\\n'
                    {
                    match("//"); 

                    // D:\\antlwork\\IISCaseLang.g:1543:18: ( . )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0=='\n') ) {
                            alt8=2;
                        }
                        else if ( ((LA8_0>='\u0000' && LA8_0<='\t')||(LA8_0>='\u000B' && LA8_0<='\uFFFE')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // D:\\antlwork\\IISCaseLang.g:1543:19: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);

                    match('\n'); 

                    }


                    }
                    break;
                case 2 :
                    // D:\\antlwork\\IISCaseLang.g:1543:31: ( 'REM ' ( . )* '\\n' )
                    {
                    // D:\\antlwork\\IISCaseLang.g:1543:31: ( 'REM ' ( . )* '\\n' )
                    // D:\\antlwork\\IISCaseLang.g:1543:32: 'REM ' ( . )* '\\n'
                    {
                    match("REM "); 

                    // D:\\antlwork\\IISCaseLang.g:1543:39: ( . )*
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
                    	    // D:\\antlwork\\IISCaseLang.g:1543:40: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);

                    match('\n'); 

                    }


                    }
                    break;

            }

            skip();

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end COMMENT

    // $ANTLR start COMMENT_MULTYLINE
    public final void mCOMMENT_MULTYLINE() throws RecognitionException {
        try {
            int _type = COMMENT_MULTYLINE;
            // D:\\antlwork\\IISCaseLang.g:1545:19: ( ( '/*' ( . )* '*/' ) )
            // D:\\antlwork\\IISCaseLang.g:1545:21: ( '/*' ( . )* '*/' )
            {
            // D:\\antlwork\\IISCaseLang.g:1545:21: ( '/*' ( . )* '*/' )
            // D:\\antlwork\\IISCaseLang.g:1545:22: '/*' ( . )* '*/'
            {
            match("/*"); 

            // D:\\antlwork\\IISCaseLang.g:1545:27: ( . )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0=='*') ) {
                    int LA11_1 = input.LA(2);

                    if ( (LA11_1=='/') ) {
                        alt11=2;
                    }
                    else if ( ((LA11_1>='\u0000' && LA11_1<='.')||(LA11_1>='0' && LA11_1<='\uFFFE')) ) {
                        alt11=1;
                    }


                }
                else if ( ((LA11_0>='\u0000' && LA11_0<=')')||(LA11_0>='+' && LA11_0<='\uFFFE')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // D:\\antlwork\\IISCaseLang.g:1545:28: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            match("*/"); 


            }

            skip();

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end COMMENT_MULTYLINE

    public void mTokens() throws RecognitionException {
        // D:\\antlwork\\IISCaseLang.g:1:8: ( T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | T81 | T82 | T83 | INTERSECT_OPERATOR | UNION_OPERATOR | CASE | END_CASE | DEFAULT | SWITCH | END_SWITCH | BREAK | SELECT | INTO | FROM | RETURN | FETCH | UPDATE | SET | SIGNAL | RETURNS | END | END_IF | BEGIN | VAR | END_VAR | INPUT | OUTPUT | CONST | IF | THEN | ELSE | WHILE | END_WHILE | REPEAT | END_REPEAT | UNTIL | FOR | END_FOR | TO | ITERATOR | FUNCTION | CONCAT_OPERATOR | ADD_OPERATOR | SUB_OPERATOR | MUL_OPERATOR | MOD_OPERATOR | EQU_OP | IN | OUT | INOUT | LIKE | SQ_STRING_LIT | TRUE | FALSE | NULL | COMPARASION_OPERATOR | IMPLY_OP | OR | AND | XOR | NOT | PRINT | IDENTIFIER | TIME | DATE | INTEGER | WS | COMMENT | COMMENT_MULTYLINE )
        int alt12=80;
        switch ( input.LA(1) ) {
        case ',':
            {
            alt12=1;
            }
            break;
        case '(':
            {
            alt12=2;
            }
            break;
        case ')':
            {
            alt12=3;
            }
            break;
        case ':':
            {
            int LA12_4 = input.LA(2);

            if ( (LA12_4=='=') ) {
                alt12=4;
            }
            else {
                alt12=14;}
            }
            break;
        case ';':
            {
            alt12=5;
            }
            break;
        case 'A':
            {
            switch ( input.LA(2) ) {
            case 'N':
                {
                int LA12_44 = input.LA(3);

                if ( (LA12_44=='D') ) {
                    int LA12_86 = input.LA(4);

                    if ( ((LA12_86>='0' && LA12_86<='9')||(LA12_86>='A' && LA12_86<='Z')||LA12_86=='_'||(LA12_86>='a' && LA12_86<='z')) ) {
                        alt12=74;
                    }
                    else {
                        alt12=70;}
                }
                else {
                    alt12=74;}
                }
                break;
            case 'R':
                {
                int LA12_45 = input.LA(3);

                if ( (LA12_45=='R') ) {
                    int LA12_87 = input.LA(4);

                    if ( (LA12_87=='A') ) {
                        int LA12_132 = input.LA(5);

                        if ( (LA12_132=='Y') ) {
                            int LA12_171 = input.LA(6);

                            if ( ((LA12_171>='0' && LA12_171<='9')||(LA12_171>='A' && LA12_171<='Z')||LA12_171=='_'||(LA12_171>='a' && LA12_171<='z')) ) {
                                alt12=74;
                            }
                            else {
                                alt12=6;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            default:
                alt12=74;}

            }
            break;
        case '[':
            {
            alt12=7;
            }
            break;
        case ']':
            {
            alt12=8;
            }
            break;
        case 'O':
            {
            switch ( input.LA(2) ) {
            case 'R':
                {
                int LA12_46 = input.LA(3);

                if ( ((LA12_46>='0' && LA12_46<='9')||(LA12_46>='A' && LA12_46<='Z')||LA12_46=='_'||(LA12_46>='a' && LA12_46<='z')) ) {
                    alt12=74;
                }
                else {
                    alt12=69;}
                }
                break;
            case 'U':
                {
                int LA12_47 = input.LA(3);

                if ( (LA12_47=='T') ) {
                    switch ( input.LA(4) ) {
                    case 'P':
                        {
                        int LA12_133 = input.LA(5);

                        if ( (LA12_133=='U') ) {
                            int LA12_172 = input.LA(6);

                            if ( (LA12_172=='T') ) {
                                int LA12_210 = input.LA(7);

                                if ( ((LA12_210>='0' && LA12_210<='9')||(LA12_210>='A' && LA12_210<='Z')||LA12_210=='_'||(LA12_210>='a' && LA12_210<='z')) ) {
                                    alt12=74;
                                }
                                else {
                                    alt12=38;}
                            }
                            else {
                                alt12=74;}
                        }
                        else {
                            alt12=74;}
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
                    case 'A':
                    case 'B':
                    case 'C':
                    case 'D':
                    case 'E':
                    case 'F':
                    case 'G':
                    case 'H':
                    case 'I':
                    case 'J':
                    case 'K':
                    case 'L':
                    case 'M':
                    case 'N':
                    case 'O':
                    case 'Q':
                    case 'R':
                    case 'S':
                    case 'T':
                    case 'U':
                    case 'V':
                    case 'W':
                    case 'X':
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
                        alt12=74;
                        }
                        break;
                    default:
                        alt12=60;}

                }
                else {
                    alt12=74;}
                }
                break;
            case 'F':
                {
                int LA12_48 = input.LA(3);

                if ( ((LA12_48>='0' && LA12_48<='9')||(LA12_48>='A' && LA12_48<='Z')||LA12_48=='_'||(LA12_48>='a' && LA12_48<='z')) ) {
                    alt12=74;
                }
                else {
                    alt12=9;}
                }
                break;
            default:
                alt12=74;}

            }
            break;
        case '{':
            {
            alt12=10;
            }
            break;
        case '}':
            {
            alt12=11;
            }
            break;
        case '.':
            {
            alt12=12;
            }
            break;
        case '=':
            {
            switch ( input.LA(2) ) {
            case '>':
                {
                alt12=68;
                }
                break;
            case '=':
                {
                alt12=58;
                }
                break;
            default:
                alt12=13;}

            }
            break;
        case 'I':
            {
            switch ( input.LA(2) ) {
            case 'N':
                {
                switch ( input.LA(3) ) {
                case 'T':
                    {
                    switch ( input.LA(4) ) {
                    case 'O':
                        {
                        int LA12_135 = input.LA(5);

                        if ( ((LA12_135>='0' && LA12_135<='9')||(LA12_135>='A' && LA12_135<='Z')||LA12_135=='_'||(LA12_135>='a' && LA12_135<='z')) ) {
                            alt12=74;
                        }
                        else {
                            alt12=24;}
                        }
                        break;
                    case 'E':
                        {
                        int LA12_136 = input.LA(5);

                        if ( (LA12_136=='R') ) {
                            int LA12_174 = input.LA(6);

                            if ( (LA12_174=='S') ) {
                                int LA12_211 = input.LA(7);

                                if ( (LA12_211=='E') ) {
                                    int LA12_240 = input.LA(8);

                                    if ( (LA12_240=='C') ) {
                                        int LA12_258 = input.LA(9);

                                        if ( (LA12_258=='T') ) {
                                            int LA12_269 = input.LA(10);

                                            if ( ((LA12_269>='0' && LA12_269<='9')||(LA12_269>='A' && LA12_269<='Z')||LA12_269=='_'||(LA12_269>='a' && LA12_269<='z')) ) {
                                                alt12=74;
                                            }
                                            else {
                                                alt12=15;}
                                        }
                                        else {
                                            alt12=74;}
                                    }
                                    else {
                                        alt12=74;}
                                }
                                else {
                                    alt12=74;}
                            }
                            else {
                                alt12=74;}
                        }
                        else {
                            alt12=74;}
                        }
                        break;
                    default:
                        alt12=74;}

                    }
                    break;
                case 'P':
                    {
                    int LA12_92 = input.LA(4);

                    if ( (LA12_92=='U') ) {
                        int LA12_137 = input.LA(5);

                        if ( (LA12_137=='T') ) {
                            int LA12_175 = input.LA(6);

                            if ( ((LA12_175>='0' && LA12_175<='9')||(LA12_175>='A' && LA12_175<='Z')||LA12_175=='_'||(LA12_175>='a' && LA12_175<='z')) ) {
                                alt12=74;
                            }
                            else {
                                alt12=37;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                    }
                    break;
                case 'O':
                    {
                    int LA12_93 = input.LA(4);

                    if ( (LA12_93=='U') ) {
                        int LA12_138 = input.LA(5);

                        if ( (LA12_138=='T') ) {
                            int LA12_176 = input.LA(6);

                            if ( ((LA12_176>='0' && LA12_176<='9')||(LA12_176>='A' && LA12_176<='Z')||LA12_176=='_'||(LA12_176>='a' && LA12_176<='z')) ) {
                                alt12=74;
                            }
                            else {
                                alt12=61;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
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
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'Q':
                case 'R':
                case 'S':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
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
                    alt12=74;
                    }
                    break;
                default:
                    alt12=59;}

                }
                break;
            case 'F':
                {
                int LA12_52 = input.LA(3);

                if ( ((LA12_52>='0' && LA12_52<='9')||(LA12_52>='A' && LA12_52<='Z')||LA12_52=='_'||(LA12_52>='a' && LA12_52<='z')) ) {
                    alt12=74;
                }
                else {
                    alt12=40;}
                }
                break;
            case 'T':
                {
                int LA12_53 = input.LA(3);

                if ( (LA12_53=='E') ) {
                    int LA12_96 = input.LA(4);

                    if ( (LA12_96=='R') ) {
                        int LA12_139 = input.LA(5);

                        if ( (LA12_139=='A') ) {
                            int LA12_177 = input.LA(6);

                            if ( (LA12_177=='T') ) {
                                int LA12_214 = input.LA(7);

                                if ( (LA12_214=='O') ) {
                                    int LA12_241 = input.LA(8);

                                    if ( (LA12_241=='R') ) {
                                        int LA12_259 = input.LA(9);

                                        if ( ((LA12_259>='0' && LA12_259<='9')||(LA12_259>='A' && LA12_259<='Z')||LA12_259=='_'||(LA12_259>='a' && LA12_259<='z')) ) {
                                            alt12=74;
                                        }
                                        else {
                                            alt12=51;}
                                    }
                                    else {
                                        alt12=74;}
                                }
                                else {
                                    alt12=74;}
                            }
                            else {
                                alt12=74;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            default:
                alt12=74;}

            }
            break;
        case 'U':
            {
            switch ( input.LA(2) ) {
            case 'N':
                {
                switch ( input.LA(3) ) {
                case 'I':
                    {
                    int LA12_97 = input.LA(4);

                    if ( (LA12_97=='O') ) {
                        int LA12_140 = input.LA(5);

                        if ( (LA12_140=='N') ) {
                            int LA12_178 = input.LA(6);

                            if ( ((LA12_178>='0' && LA12_178<='9')||(LA12_178>='A' && LA12_178<='Z')||LA12_178=='_'||(LA12_178>='a' && LA12_178<='z')) ) {
                                alt12=74;
                            }
                            else {
                                alt12=16;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                    }
                    break;
                case 'T':
                    {
                    int LA12_98 = input.LA(4);

                    if ( (LA12_98=='I') ) {
                        int LA12_141 = input.LA(5);

                        if ( (LA12_141=='L') ) {
                            int LA12_179 = input.LA(6);

                            if ( ((LA12_179>='0' && LA12_179<='9')||(LA12_179>='A' && LA12_179<='Z')||LA12_179=='_'||(LA12_179>='a' && LA12_179<='z')) ) {
                                alt12=74;
                            }
                            else {
                                alt12=47;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                    }
                    break;
                default:
                    alt12=74;}

                }
                break;
            case 'P':
                {
                int LA12_55 = input.LA(3);

                if ( (LA12_55=='D') ) {
                    int LA12_99 = input.LA(4);

                    if ( (LA12_99=='A') ) {
                        int LA12_142 = input.LA(5);

                        if ( (LA12_142=='T') ) {
                            int LA12_180 = input.LA(6);

                            if ( (LA12_180=='E') ) {
                                int LA12_217 = input.LA(7);

                                if ( ((LA12_217>='0' && LA12_217<='9')||(LA12_217>='A' && LA12_217<='Z')||LA12_217=='_'||(LA12_217>='a' && LA12_217<='z')) ) {
                                    alt12=74;
                                }
                                else {
                                    alt12=28;}
                            }
                            else {
                                alt12=74;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            default:
                alt12=74;}

            }
            break;
        case 'C':
            {
            switch ( input.LA(2) ) {
            case 'A':
                {
                int LA12_56 = input.LA(3);

                if ( (LA12_56=='S') ) {
                    int LA12_100 = input.LA(4);

                    if ( (LA12_100=='E') ) {
                        int LA12_143 = input.LA(5);

                        if ( ((LA12_143>='0' && LA12_143<='9')||(LA12_143>='A' && LA12_143<='Z')||LA12_143=='_'||(LA12_143>='a' && LA12_143<='z')) ) {
                            alt12=74;
                        }
                        else {
                            alt12=17;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            case 'O':
                {
                int LA12_57 = input.LA(3);

                if ( (LA12_57=='N') ) {
                    int LA12_101 = input.LA(4);

                    if ( (LA12_101=='S') ) {
                        int LA12_144 = input.LA(5);

                        if ( (LA12_144=='T') ) {
                            int LA12_182 = input.LA(6);

                            if ( ((LA12_182>='0' && LA12_182<='9')||(LA12_182>='A' && LA12_182<='Z')||LA12_182=='_'||(LA12_182>='a' && LA12_182<='z')) ) {
                                alt12=74;
                            }
                            else {
                                alt12=39;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            default:
                alt12=74;}

            }
            break;
        case 'E':
            {
            switch ( input.LA(2) ) {
            case 'N':
                {
                int LA12_58 = input.LA(3);

                if ( (LA12_58=='D') ) {
                    switch ( input.LA(4) ) {
                    case '_':
                        {
                        switch ( input.LA(5) ) {
                        case 'I':
                            {
                            int LA12_183 = input.LA(6);

                            if ( (LA12_183=='F') ) {
                                int LA12_219 = input.LA(7);

                                if ( ((LA12_219>='0' && LA12_219<='9')||(LA12_219>='A' && LA12_219<='Z')||LA12_219=='_'||(LA12_219>='a' && LA12_219<='z')) ) {
                                    alt12=74;
                                }
                                else {
                                    alt12=33;}
                            }
                            else {
                                alt12=74;}
                            }
                            break;
                        case 'S':
                            {
                            int LA12_184 = input.LA(6);

                            if ( (LA12_184=='W') ) {
                                int LA12_220 = input.LA(7);

                                if ( (LA12_220=='I') ) {
                                    int LA12_244 = input.LA(8);

                                    if ( (LA12_244=='T') ) {
                                        int LA12_260 = input.LA(9);

                                        if ( (LA12_260=='C') ) {
                                            int LA12_271 = input.LA(10);

                                            if ( (LA12_271=='H') ) {
                                                int LA12_277 = input.LA(11);

                                                if ( ((LA12_277>='0' && LA12_277<='9')||(LA12_277>='A' && LA12_277<='Z')||LA12_277=='_'||(LA12_277>='a' && LA12_277<='z')) ) {
                                                    alt12=74;
                                                }
                                                else {
                                                    alt12=21;}
                                            }
                                            else {
                                                alt12=74;}
                                        }
                                        else {
                                            alt12=74;}
                                    }
                                    else {
                                        alt12=74;}
                                }
                                else {
                                    alt12=74;}
                            }
                            else {
                                alt12=74;}
                            }
                            break;
                        case 'C':
                            {
                            int LA12_185 = input.LA(6);

                            if ( (LA12_185=='A') ) {
                                int LA12_221 = input.LA(7);

                                if ( (LA12_221=='S') ) {
                                    int LA12_245 = input.LA(8);

                                    if ( (LA12_245=='E') ) {
                                        int LA12_261 = input.LA(9);

                                        if ( ((LA12_261>='0' && LA12_261<='9')||(LA12_261>='A' && LA12_261<='Z')||LA12_261=='_'||(LA12_261>='a' && LA12_261<='z')) ) {
                                            alt12=74;
                                        }
                                        else {
                                            alt12=18;}
                                    }
                                    else {
                                        alt12=74;}
                                }
                                else {
                                    alt12=74;}
                            }
                            else {
                                alt12=74;}
                            }
                            break;
                        case 'W':
                            {
                            int LA12_186 = input.LA(6);

                            if ( (LA12_186=='H') ) {
                                int LA12_222 = input.LA(7);

                                if ( (LA12_222=='I') ) {
                                    int LA12_246 = input.LA(8);

                                    if ( (LA12_246=='L') ) {
                                        int LA12_262 = input.LA(9);

                                        if ( (LA12_262=='E') ) {
                                            int LA12_273 = input.LA(10);

                                            if ( ((LA12_273>='0' && LA12_273<='9')||(LA12_273>='A' && LA12_273<='Z')||LA12_273=='_'||(LA12_273>='a' && LA12_273<='z')) ) {
                                                alt12=74;
                                            }
                                            else {
                                                alt12=44;}
                                        }
                                        else {
                                            alt12=74;}
                                    }
                                    else {
                                        alt12=74;}
                                }
                                else {
                                    alt12=74;}
                            }
                            else {
                                alt12=74;}
                            }
                            break;
                        case 'V':
                            {
                            int LA12_187 = input.LA(6);

                            if ( (LA12_187=='A') ) {
                                int LA12_223 = input.LA(7);

                                if ( (LA12_223=='R') ) {
                                    int LA12_247 = input.LA(8);

                                    if ( ((LA12_247>='0' && LA12_247<='9')||(LA12_247>='A' && LA12_247<='Z')||LA12_247=='_'||(LA12_247>='a' && LA12_247<='z')) ) {
                                        alt12=74;
                                    }
                                    else {
                                        alt12=36;}
                                }
                                else {
                                    alt12=74;}
                            }
                            else {
                                alt12=74;}
                            }
                            break;
                        case 'F':
                            {
                            int LA12_188 = input.LA(6);

                            if ( (LA12_188=='O') ) {
                                int LA12_224 = input.LA(7);

                                if ( (LA12_224=='R') ) {
                                    int LA12_248 = input.LA(8);

                                    if ( ((LA12_248>='0' && LA12_248<='9')||(LA12_248>='A' && LA12_248<='Z')||LA12_248=='_'||(LA12_248>='a' && LA12_248<='z')) ) {
                                        alt12=74;
                                    }
                                    else {
                                        alt12=49;}
                                }
                                else {
                                    alt12=74;}
                            }
                            else {
                                alt12=74;}
                            }
                            break;
                        case 'R':
                            {
                            int LA12_189 = input.LA(6);

                            if ( (LA12_189=='E') ) {
                                int LA12_225 = input.LA(7);

                                if ( (LA12_225=='P') ) {
                                    int LA12_249 = input.LA(8);

                                    if ( (LA12_249=='E') ) {
                                        int LA12_265 = input.LA(9);

                                        if ( (LA12_265=='A') ) {
                                            int LA12_274 = input.LA(10);

                                            if ( (LA12_274=='T') ) {
                                                int LA12_279 = input.LA(11);

                                                if ( ((LA12_279>='0' && LA12_279<='9')||(LA12_279>='A' && LA12_279<='Z')||LA12_279=='_'||(LA12_279>='a' && LA12_279<='z')) ) {
                                                    alt12=74;
                                                }
                                                else {
                                                    alt12=46;}
                                            }
                                            else {
                                                alt12=74;}
                                        }
                                        else {
                                            alt12=74;}
                                    }
                                    else {
                                        alt12=74;}
                                }
                                else {
                                    alt12=74;}
                            }
                            else {
                                alt12=74;}
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
                        case 'A':
                        case 'B':
                        case 'D':
                        case 'E':
                        case 'G':
                        case 'H':
                        case 'J':
                        case 'K':
                        case 'L':
                        case 'M':
                        case 'N':
                        case 'O':
                        case 'P':
                        case 'Q':
                        case 'T':
                        case 'U':
                        case 'X':
                        case 'Y':
                        case 'Z':
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
                            alt12=74;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("1:1: Tokens : ( T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | T81 | T82 | T83 | INTERSECT_OPERATOR | UNION_OPERATOR | CASE | END_CASE | DEFAULT | SWITCH | END_SWITCH | BREAK | SELECT | INTO | FROM | RETURN | FETCH | UPDATE | SET | SIGNAL | RETURNS | END | END_IF | BEGIN | VAR | END_VAR | INPUT | OUTPUT | CONST | IF | THEN | ELSE | WHILE | END_WHILE | REPEAT | END_REPEAT | UNTIL | FOR | END_FOR | TO | ITERATOR | FUNCTION | CONCAT_OPERATOR | ADD_OPERATOR | SUB_OPERATOR | MUL_OPERATOR | MOD_OPERATOR | EQU_OP | IN | OUT | INOUT | LIKE | SQ_STRING_LIT | TRUE | FALSE | NULL | COMPARASION_OPERATOR | IMPLY_OP | OR | AND | XOR | NOT | PRINT | IDENTIFIER | TIME | DATE | INTEGER | WS | COMMENT | COMMENT_MULTYLINE );", 12, 145, input);

                            throw nvae;
                        }

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
                    case 'A':
                    case 'B':
                    case 'C':
                    case 'D':
                    case 'E':
                    case 'F':
                    case 'G':
                    case 'H':
                    case 'I':
                    case 'J':
                    case 'K':
                    case 'L':
                    case 'M':
                    case 'N':
                    case 'O':
                    case 'P':
                    case 'Q':
                    case 'R':
                    case 'S':
                    case 'T':
                    case 'U':
                    case 'V':
                    case 'W':
                    case 'X':
                    case 'Y':
                    case 'Z':
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
                        alt12=74;
                        }
                        break;
                    default:
                        alt12=32;}

                }
                else {
                    alt12=74;}
                }
                break;
            case 'L':
                {
                int LA12_59 = input.LA(3);

                if ( (LA12_59=='S') ) {
                    int LA12_103 = input.LA(4);

                    if ( (LA12_103=='E') ) {
                        int LA12_147 = input.LA(5);

                        if ( ((LA12_147>='0' && LA12_147<='9')||(LA12_147>='A' && LA12_147<='Z')||LA12_147=='_'||(LA12_147>='a' && LA12_147<='z')) ) {
                            alt12=74;
                        }
                        else {
                            alt12=42;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            default:
                alt12=74;}

            }
            break;
        case 'D':
            {
            int LA12_18 = input.LA(2);

            if ( (LA12_18=='E') ) {
                int LA12_60 = input.LA(3);

                if ( (LA12_60=='F') ) {
                    int LA12_104 = input.LA(4);

                    if ( (LA12_104=='A') ) {
                        int LA12_148 = input.LA(5);

                        if ( (LA12_148=='U') ) {
                            int LA12_191 = input.LA(6);

                            if ( (LA12_191=='L') ) {
                                int LA12_226 = input.LA(7);

                                if ( (LA12_226=='T') ) {
                                    int LA12_250 = input.LA(8);

                                    if ( ((LA12_250>='0' && LA12_250<='9')||(LA12_250>='A' && LA12_250<='Z')||LA12_250=='_'||(LA12_250>='a' && LA12_250<='z')) ) {
                                        alt12=74;
                                    }
                                    else {
                                        alt12=19;}
                                }
                                else {
                                    alt12=74;}
                            }
                            else {
                                alt12=74;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
            }
            else {
                alt12=74;}
            }
            break;
        case 'S':
            {
            switch ( input.LA(2) ) {
            case 'W':
                {
                int LA12_61 = input.LA(3);

                if ( (LA12_61=='I') ) {
                    int LA12_105 = input.LA(4);

                    if ( (LA12_105=='T') ) {
                        int LA12_149 = input.LA(5);

                        if ( (LA12_149=='C') ) {
                            int LA12_192 = input.LA(6);

                            if ( (LA12_192=='H') ) {
                                int LA12_227 = input.LA(7);

                                if ( ((LA12_227>='0' && LA12_227<='9')||(LA12_227>='A' && LA12_227<='Z')||LA12_227=='_'||(LA12_227>='a' && LA12_227<='z')) ) {
                                    alt12=74;
                                }
                                else {
                                    alt12=20;}
                            }
                            else {
                                alt12=74;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            case 'E':
                {
                switch ( input.LA(3) ) {
                case 'L':
                    {
                    int LA12_106 = input.LA(4);

                    if ( (LA12_106=='E') ) {
                        int LA12_150 = input.LA(5);

                        if ( (LA12_150=='C') ) {
                            int LA12_193 = input.LA(6);

                            if ( (LA12_193=='T') ) {
                                int LA12_228 = input.LA(7);

                                if ( ((LA12_228>='0' && LA12_228<='9')||(LA12_228>='A' && LA12_228<='Z')||LA12_228=='_'||(LA12_228>='a' && LA12_228<='z')) ) {
                                    alt12=74;
                                }
                                else {
                                    alt12=23;}
                            }
                            else {
                                alt12=74;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                    }
                    break;
                case 'T':
                    {
                    int LA12_107 = input.LA(4);

                    if ( ((LA12_107>='0' && LA12_107<='9')||(LA12_107>='A' && LA12_107<='Z')||LA12_107=='_'||(LA12_107>='a' && LA12_107<='z')) ) {
                        alt12=74;
                    }
                    else {
                        alt12=29;}
                    }
                    break;
                default:
                    alt12=74;}

                }
                break;
            case 'I':
                {
                int LA12_63 = input.LA(3);

                if ( (LA12_63=='G') ) {
                    int LA12_108 = input.LA(4);

                    if ( (LA12_108=='N') ) {
                        int LA12_152 = input.LA(5);

                        if ( (LA12_152=='A') ) {
                            int LA12_194 = input.LA(6);

                            if ( (LA12_194=='L') ) {
                                int LA12_229 = input.LA(7);

                                if ( ((LA12_229>='0' && LA12_229<='9')||(LA12_229>='A' && LA12_229<='Z')||LA12_229=='_'||(LA12_229>='a' && LA12_229<='z')) ) {
                                    alt12=74;
                                }
                                else {
                                    alt12=30;}
                            }
                            else {
                                alt12=74;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            default:
                alt12=74;}

            }
            break;
        case 'B':
            {
            switch ( input.LA(2) ) {
            case 'R':
                {
                int LA12_64 = input.LA(3);

                if ( (LA12_64=='E') ) {
                    int LA12_109 = input.LA(4);

                    if ( (LA12_109=='A') ) {
                        int LA12_153 = input.LA(5);

                        if ( (LA12_153=='K') ) {
                            int LA12_195 = input.LA(6);

                            if ( ((LA12_195>='0' && LA12_195<='9')||(LA12_195>='A' && LA12_195<='Z')||LA12_195=='_'||(LA12_195>='a' && LA12_195<='z')) ) {
                                alt12=74;
                            }
                            else {
                                alt12=22;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            case 'E':
                {
                int LA12_65 = input.LA(3);

                if ( (LA12_65=='G') ) {
                    int LA12_110 = input.LA(4);

                    if ( (LA12_110=='I') ) {
                        int LA12_154 = input.LA(5);

                        if ( (LA12_154=='N') ) {
                            int LA12_196 = input.LA(6);

                            if ( ((LA12_196>='0' && LA12_196<='9')||(LA12_196>='A' && LA12_196<='Z')||LA12_196=='_'||(LA12_196>='a' && LA12_196<='z')) ) {
                                alt12=74;
                            }
                            else {
                                alt12=34;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            default:
                alt12=74;}

            }
            break;
        case 'F':
            {
            switch ( input.LA(2) ) {
            case 'A':
                {
                int LA12_66 = input.LA(3);

                if ( (LA12_66=='L') ) {
                    int LA12_111 = input.LA(4);

                    if ( (LA12_111=='S') ) {
                        int LA12_155 = input.LA(5);

                        if ( (LA12_155=='E') ) {
                            int LA12_197 = input.LA(6);

                            if ( ((LA12_197>='0' && LA12_197<='9')||(LA12_197>='A' && LA12_197<='Z')||LA12_197=='_'||(LA12_197>='a' && LA12_197<='z')) ) {
                                alt12=74;
                            }
                            else {
                                alt12=65;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            case 'R':
                {
                int LA12_67 = input.LA(3);

                if ( (LA12_67=='O') ) {
                    int LA12_112 = input.LA(4);

                    if ( (LA12_112=='M') ) {
                        int LA12_156 = input.LA(5);

                        if ( ((LA12_156>='0' && LA12_156<='9')||(LA12_156>='A' && LA12_156<='Z')||LA12_156=='_'||(LA12_156>='a' && LA12_156<='z')) ) {
                            alt12=74;
                        }
                        else {
                            alt12=25;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            case 'E':
                {
                int LA12_68 = input.LA(3);

                if ( (LA12_68=='T') ) {
                    int LA12_113 = input.LA(4);

                    if ( (LA12_113=='C') ) {
                        int LA12_157 = input.LA(5);

                        if ( (LA12_157=='H') ) {
                            int LA12_199 = input.LA(6);

                            if ( ((LA12_199>='0' && LA12_199<='9')||(LA12_199>='A' && LA12_199<='Z')||LA12_199=='_'||(LA12_199>='a' && LA12_199<='z')) ) {
                                alt12=74;
                            }
                            else {
                                alt12=27;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            case 'O':
                {
                int LA12_69 = input.LA(3);

                if ( (LA12_69=='R') ) {
                    int LA12_114 = input.LA(4);

                    if ( ((LA12_114>='0' && LA12_114<='9')||(LA12_114>='A' && LA12_114<='Z')||LA12_114=='_'||(LA12_114>='a' && LA12_114<='z')) ) {
                        alt12=74;
                    }
                    else {
                        alt12=48;}
                }
                else {
                    alt12=74;}
                }
                break;
            case 'U':
                {
                int LA12_70 = input.LA(3);

                if ( (LA12_70=='N') ) {
                    int LA12_115 = input.LA(4);

                    if ( (LA12_115=='C') ) {
                        int LA12_159 = input.LA(5);

                        if ( (LA12_159=='T') ) {
                            int LA12_200 = input.LA(6);

                            if ( (LA12_200=='I') ) {
                                int LA12_234 = input.LA(7);

                                if ( (LA12_234=='O') ) {
                                    int LA12_254 = input.LA(8);

                                    if ( (LA12_254=='N') ) {
                                        int LA12_267 = input.LA(9);

                                        if ( ((LA12_267>='0' && LA12_267<='9')||(LA12_267>='A' && LA12_267<='Z')||LA12_267=='_'||(LA12_267>='a' && LA12_267<='z')) ) {
                                            alt12=74;
                                        }
                                        else {
                                            alt12=52;}
                                    }
                                    else {
                                        alt12=74;}
                                }
                                else {
                                    alt12=74;}
                            }
                            else {
                                alt12=74;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            default:
                alt12=74;}

            }
            break;
        case 'R':
            {
            int LA12_22 = input.LA(2);

            if ( (LA12_22=='E') ) {
                switch ( input.LA(3) ) {
                case 'P':
                    {
                    int LA12_116 = input.LA(4);

                    if ( (LA12_116=='E') ) {
                        int LA12_160 = input.LA(5);

                        if ( (LA12_160=='A') ) {
                            int LA12_201 = input.LA(6);

                            if ( (LA12_201=='T') ) {
                                int LA12_235 = input.LA(7);

                                if ( ((LA12_235>='0' && LA12_235<='9')||(LA12_235>='A' && LA12_235<='Z')||LA12_235=='_'||(LA12_235>='a' && LA12_235<='z')) ) {
                                    alt12=74;
                                }
                                else {
                                    alt12=45;}
                            }
                            else {
                                alt12=74;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                    }
                    break;
                case 'T':
                    {
                    int LA12_117 = input.LA(4);

                    if ( (LA12_117=='U') ) {
                        int LA12_161 = input.LA(5);

                        if ( (LA12_161=='R') ) {
                            int LA12_202 = input.LA(6);

                            if ( (LA12_202=='N') ) {
                                switch ( input.LA(7) ) {
                                case 'S':
                                    {
                                    int LA12_256 = input.LA(8);

                                    if ( ((LA12_256>='0' && LA12_256<='9')||(LA12_256>='A' && LA12_256<='Z')||LA12_256=='_'||(LA12_256>='a' && LA12_256<='z')) ) {
                                        alt12=74;
                                    }
                                    else {
                                        alt12=31;}
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
                                case 'A':
                                case 'B':
                                case 'C':
                                case 'D':
                                case 'E':
                                case 'F':
                                case 'G':
                                case 'H':
                                case 'I':
                                case 'J':
                                case 'K':
                                case 'L':
                                case 'M':
                                case 'N':
                                case 'O':
                                case 'P':
                                case 'Q':
                                case 'R':
                                case 'T':
                                case 'U':
                                case 'V':
                                case 'W':
                                case 'X':
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
                                    alt12=74;
                                    }
                                    break;
                                default:
                                    alt12=26;}

                            }
                            else {
                                alt12=74;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                    }
                    break;
                case 'M':
                    {
                    int LA12_118 = input.LA(4);

                    if ( (LA12_118==' ') ) {
                        alt12=79;
                    }
                    else {
                        alt12=74;}
                    }
                    break;
                default:
                    alt12=74;}

            }
            else {
                alt12=74;}
            }
            break;
        case 'V':
            {
            int LA12_23 = input.LA(2);

            if ( (LA12_23=='A') ) {
                int LA12_72 = input.LA(3);

                if ( (LA12_72=='R') ) {
                    int LA12_119 = input.LA(4);

                    if ( ((LA12_119>='0' && LA12_119<='9')||(LA12_119>='A' && LA12_119<='Z')||LA12_119=='_'||(LA12_119>='a' && LA12_119<='z')) ) {
                        alt12=74;
                    }
                    else {
                        alt12=35;}
                }
                else {
                    alt12=74;}
            }
            else {
                alt12=74;}
            }
            break;
        case 'T':
            {
            switch ( input.LA(2) ) {
            case 'R':
                {
                int LA12_73 = input.LA(3);

                if ( (LA12_73=='U') ) {
                    int LA12_120 = input.LA(4);

                    if ( (LA12_120=='E') ) {
                        int LA12_163 = input.LA(5);

                        if ( ((LA12_163>='0' && LA12_163<='9')||(LA12_163>='A' && LA12_163<='Z')||LA12_163=='_'||(LA12_163>='a' && LA12_163<='z')) ) {
                            alt12=74;
                        }
                        else {
                            alt12=64;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            case 'H':
                {
                int LA12_74 = input.LA(3);

                if ( (LA12_74=='E') ) {
                    int LA12_121 = input.LA(4);

                    if ( (LA12_121=='N') ) {
                        int LA12_164 = input.LA(5);

                        if ( ((LA12_164>='0' && LA12_164<='9')||(LA12_164>='A' && LA12_164<='Z')||LA12_164=='_'||(LA12_164>='a' && LA12_164<='z')) ) {
                            alt12=74;
                        }
                        else {
                            alt12=41;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            case 'O':
                {
                int LA12_75 = input.LA(3);

                if ( ((LA12_75>='0' && LA12_75<='9')||(LA12_75>='A' && LA12_75<='Z')||LA12_75=='_'||(LA12_75>='a' && LA12_75<='z')) ) {
                    alt12=74;
                }
                else {
                    alt12=50;}
                }
                break;
            default:
                alt12=74;}

            }
            break;
        case 'W':
            {
            int LA12_25 = input.LA(2);

            if ( (LA12_25=='H') ) {
                int LA12_76 = input.LA(3);

                if ( (LA12_76=='I') ) {
                    int LA12_123 = input.LA(4);

                    if ( (LA12_123=='L') ) {
                        int LA12_165 = input.LA(5);

                        if ( (LA12_165=='E') ) {
                            int LA12_205 = input.LA(6);

                            if ( ((LA12_205>='0' && LA12_205<='9')||(LA12_205>='A' && LA12_205<='Z')||LA12_205=='_'||(LA12_205>='a' && LA12_205<='z')) ) {
                                alt12=74;
                            }
                            else {
                                alt12=43;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
            }
            else {
                alt12=74;}
            }
            break;
        case '|':
            {
            alt12=53;
            }
            break;
        case '+':
            {
            alt12=54;
            }
            break;
        case '-':
            {
            alt12=55;
            }
            break;
        case '/':
            {
            switch ( input.LA(2) ) {
            case '*':
                {
                alt12=80;
                }
                break;
            case '/':
                {
                alt12=79;
                }
                break;
            default:
                alt12=56;}

            }
            break;
        case '%':
            {
            alt12=57;
            }
            break;
        case '!':
            {
            alt12=58;
            }
            break;
        case 'L':
            {
            int LA12_32 = input.LA(2);

            if ( (LA12_32=='I') ) {
                int LA12_79 = input.LA(3);

                if ( (LA12_79=='K') ) {
                    int LA12_124 = input.LA(4);

                    if ( (LA12_124=='E') ) {
                        int LA12_166 = input.LA(5);

                        if ( ((LA12_166>='0' && LA12_166<='9')||(LA12_166>='A' && LA12_166<='Z')||LA12_166=='_'||(LA12_166>='a' && LA12_166<='z')) ) {
                            alt12=74;
                        }
                        else {
                            alt12=62;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
            }
            else {
                alt12=74;}
            }
            break;
        case '\'':
            {
            alt12=63;
            }
            break;
        case 'N':
            {
            switch ( input.LA(2) ) {
            case 'U':
                {
                int LA12_80 = input.LA(3);

                if ( (LA12_80=='L') ) {
                    int LA12_125 = input.LA(4);

                    if ( (LA12_125=='L') ) {
                        int LA12_167 = input.LA(5);

                        if ( ((LA12_167>='0' && LA12_167<='9')||(LA12_167>='A' && LA12_167<='Z')||LA12_167=='_'||(LA12_167>='a' && LA12_167<='z')) ) {
                            alt12=74;
                        }
                        else {
                            alt12=66;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
                }
                break;
            case 'O':
                {
                int LA12_81 = input.LA(3);

                if ( (LA12_81=='T') ) {
                    int LA12_126 = input.LA(4);

                    if ( ((LA12_126>='0' && LA12_126<='9')||(LA12_126>='A' && LA12_126<='Z')||LA12_126=='_'||(LA12_126>='a' && LA12_126<='z')) ) {
                        alt12=74;
                    }
                    else {
                        alt12=72;}
                }
                else {
                    alt12=74;}
                }
                break;
            default:
                alt12=74;}

            }
            break;
        case '<':
        case '>':
            {
            alt12=67;
            }
            break;
        case 'X':
            {
            int LA12_36 = input.LA(2);

            if ( (LA12_36=='O') ) {
                int LA12_82 = input.LA(3);

                if ( (LA12_82=='R') ) {
                    int LA12_127 = input.LA(4);

                    if ( ((LA12_127>='0' && LA12_127<='9')||(LA12_127>='A' && LA12_127<='Z')||LA12_127=='_'||(LA12_127>='a' && LA12_127<='z')) ) {
                        alt12=74;
                    }
                    else {
                        alt12=71;}
                }
                else {
                    alt12=74;}
            }
            else {
                alt12=74;}
            }
            break;
        case 'P':
            {
            int LA12_37 = input.LA(2);

            if ( (LA12_37=='R') ) {
                int LA12_83 = input.LA(3);

                if ( (LA12_83=='I') ) {
                    int LA12_128 = input.LA(4);

                    if ( (LA12_128=='N') ) {
                        int LA12_170 = input.LA(5);

                        if ( (LA12_170=='T') ) {
                            int LA12_208 = input.LA(6);

                            if ( ((LA12_208>='0' && LA12_208<='9')||(LA12_208>='A' && LA12_208<='Z')||LA12_208=='_'||(LA12_208>='a' && LA12_208<='z')) ) {
                                alt12=74;
                            }
                            else {
                                alt12=73;}
                        }
                        else {
                            alt12=74;}
                    }
                    else {
                        alt12=74;}
                }
                else {
                    alt12=74;}
            }
            else {
                alt12=74;}
            }
            break;
        case 'G':
        case 'H':
        case 'J':
        case 'K':
        case 'M':
        case 'Q':
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
            alt12=74;
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
            int LA12_39 = input.LA(2);

            if ( ((LA12_39>='0' && LA12_39<='9')) ) {
                switch ( input.LA(3) ) {
                case '-':
                    {
                    alt12=76;
                    }
                    break;
                case ':':
                    {
                    alt12=75;
                    }
                    break;
                default:
                    alt12=77;}

            }
            else {
                alt12=77;}
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt12=78;
            }
            break;
        case '*':
            {
            alt12=56;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( T70 | T71 | T72 | T73 | T74 | T75 | T76 | T77 | T78 | T79 | T80 | T81 | T82 | T83 | INTERSECT_OPERATOR | UNION_OPERATOR | CASE | END_CASE | DEFAULT | SWITCH | END_SWITCH | BREAK | SELECT | INTO | FROM | RETURN | FETCH | UPDATE | SET | SIGNAL | RETURNS | END | END_IF | BEGIN | VAR | END_VAR | INPUT | OUTPUT | CONST | IF | THEN | ELSE | WHILE | END_WHILE | REPEAT | END_REPEAT | UNTIL | FOR | END_FOR | TO | ITERATOR | FUNCTION | CONCAT_OPERATOR | ADD_OPERATOR | SUB_OPERATOR | MUL_OPERATOR | MOD_OPERATOR | EQU_OP | IN | OUT | INOUT | LIKE | SQ_STRING_LIT | TRUE | FALSE | NULL | COMPARASION_OPERATOR | IMPLY_OP | OR | AND | XOR | NOT | PRINT | IDENTIFIER | TIME | DATE | INTEGER | WS | COMMENT | COMMENT_MULTYLINE );", 12, 0, input);

            throw nvae;
        }

        switch (alt12) {
            case 1 :
                // D:\\antlwork\\IISCaseLang.g:1:10: T70
                {
                mT70(); 

                }
                break;
            case 2 :
                // D:\\antlwork\\IISCaseLang.g:1:14: T71
                {
                mT71(); 

                }
                break;
            case 3 :
                // D:\\antlwork\\IISCaseLang.g:1:18: T72
                {
                mT72(); 

                }
                break;
            case 4 :
                // D:\\antlwork\\IISCaseLang.g:1:22: T73
                {
                mT73(); 

                }
                break;
            case 5 :
                // D:\\antlwork\\IISCaseLang.g:1:26: T74
                {
                mT74(); 

                }
                break;
            case 6 :
                // D:\\antlwork\\IISCaseLang.g:1:30: T75
                {
                mT75(); 

                }
                break;
            case 7 :
                // D:\\antlwork\\IISCaseLang.g:1:34: T76
                {
                mT76(); 

                }
                break;
            case 8 :
                // D:\\antlwork\\IISCaseLang.g:1:38: T77
                {
                mT77(); 

                }
                break;
            case 9 :
                // D:\\antlwork\\IISCaseLang.g:1:42: T78
                {
                mT78(); 

                }
                break;
            case 10 :
                // D:\\antlwork\\IISCaseLang.g:1:46: T79
                {
                mT79(); 

                }
                break;
            case 11 :
                // D:\\antlwork\\IISCaseLang.g:1:50: T80
                {
                mT80(); 

                }
                break;
            case 12 :
                // D:\\antlwork\\IISCaseLang.g:1:54: T81
                {
                mT81(); 

                }
                break;
            case 13 :
                // D:\\antlwork\\IISCaseLang.g:1:58: T82
                {
                mT82(); 

                }
                break;
            case 14 :
                // D:\\antlwork\\IISCaseLang.g:1:62: T83
                {
                mT83(); 

                }
                break;
            case 15 :
                // D:\\antlwork\\IISCaseLang.g:1:66: INTERSECT_OPERATOR
                {
                mINTERSECT_OPERATOR(); 

                }
                break;
            case 16 :
                // D:\\antlwork\\IISCaseLang.g:1:85: UNION_OPERATOR
                {
                mUNION_OPERATOR(); 

                }
                break;
            case 17 :
                // D:\\antlwork\\IISCaseLang.g:1:100: CASE
                {
                mCASE(); 

                }
                break;
            case 18 :
                // D:\\antlwork\\IISCaseLang.g:1:105: END_CASE
                {
                mEND_CASE(); 

                }
                break;
            case 19 :
                // D:\\antlwork\\IISCaseLang.g:1:114: DEFAULT
                {
                mDEFAULT(); 

                }
                break;
            case 20 :
                // D:\\antlwork\\IISCaseLang.g:1:122: SWITCH
                {
                mSWITCH(); 

                }
                break;
            case 21 :
                // D:\\antlwork\\IISCaseLang.g:1:129: END_SWITCH
                {
                mEND_SWITCH(); 

                }
                break;
            case 22 :
                // D:\\antlwork\\IISCaseLang.g:1:140: BREAK
                {
                mBREAK(); 

                }
                break;
            case 23 :
                // D:\\antlwork\\IISCaseLang.g:1:146: SELECT
                {
                mSELECT(); 

                }
                break;
            case 24 :
                // D:\\antlwork\\IISCaseLang.g:1:153: INTO
                {
                mINTO(); 

                }
                break;
            case 25 :
                // D:\\antlwork\\IISCaseLang.g:1:158: FROM
                {
                mFROM(); 

                }
                break;
            case 26 :
                // D:\\antlwork\\IISCaseLang.g:1:163: RETURN
                {
                mRETURN(); 

                }
                break;
            case 27 :
                // D:\\antlwork\\IISCaseLang.g:1:170: FETCH
                {
                mFETCH(); 

                }
                break;
            case 28 :
                // D:\\antlwork\\IISCaseLang.g:1:176: UPDATE
                {
                mUPDATE(); 

                }
                break;
            case 29 :
                // D:\\antlwork\\IISCaseLang.g:1:183: SET
                {
                mSET(); 

                }
                break;
            case 30 :
                // D:\\antlwork\\IISCaseLang.g:1:187: SIGNAL
                {
                mSIGNAL(); 

                }
                break;
            case 31 :
                // D:\\antlwork\\IISCaseLang.g:1:194: RETURNS
                {
                mRETURNS(); 

                }
                break;
            case 32 :
                // D:\\antlwork\\IISCaseLang.g:1:202: END
                {
                mEND(); 

                }
                break;
            case 33 :
                // D:\\antlwork\\IISCaseLang.g:1:206: END_IF
                {
                mEND_IF(); 

                }
                break;
            case 34 :
                // D:\\antlwork\\IISCaseLang.g:1:213: BEGIN
                {
                mBEGIN(); 

                }
                break;
            case 35 :
                // D:\\antlwork\\IISCaseLang.g:1:219: VAR
                {
                mVAR(); 

                }
                break;
            case 36 :
                // D:\\antlwork\\IISCaseLang.g:1:223: END_VAR
                {
                mEND_VAR(); 

                }
                break;
            case 37 :
                // D:\\antlwork\\IISCaseLang.g:1:231: INPUT
                {
                mINPUT(); 

                }
                break;
            case 38 :
                // D:\\antlwork\\IISCaseLang.g:1:237: OUTPUT
                {
                mOUTPUT(); 

                }
                break;
            case 39 :
                // D:\\antlwork\\IISCaseLang.g:1:244: CONST
                {
                mCONST(); 

                }
                break;
            case 40 :
                // D:\\antlwork\\IISCaseLang.g:1:250: IF
                {
                mIF(); 

                }
                break;
            case 41 :
                // D:\\antlwork\\IISCaseLang.g:1:253: THEN
                {
                mTHEN(); 

                }
                break;
            case 42 :
                // D:\\antlwork\\IISCaseLang.g:1:258: ELSE
                {
                mELSE(); 

                }
                break;
            case 43 :
                // D:\\antlwork\\IISCaseLang.g:1:263: WHILE
                {
                mWHILE(); 

                }
                break;
            case 44 :
                // D:\\antlwork\\IISCaseLang.g:1:269: END_WHILE
                {
                mEND_WHILE(); 

                }
                break;
            case 45 :
                // D:\\antlwork\\IISCaseLang.g:1:279: REPEAT
                {
                mREPEAT(); 

                }
                break;
            case 46 :
                // D:\\antlwork\\IISCaseLang.g:1:286: END_REPEAT
                {
                mEND_REPEAT(); 

                }
                break;
            case 47 :
                // D:\\antlwork\\IISCaseLang.g:1:297: UNTIL
                {
                mUNTIL(); 

                }
                break;
            case 48 :
                // D:\\antlwork\\IISCaseLang.g:1:303: FOR
                {
                mFOR(); 

                }
                break;
            case 49 :
                // D:\\antlwork\\IISCaseLang.g:1:307: END_FOR
                {
                mEND_FOR(); 

                }
                break;
            case 50 :
                // D:\\antlwork\\IISCaseLang.g:1:315: TO
                {
                mTO(); 

                }
                break;
            case 51 :
                // D:\\antlwork\\IISCaseLang.g:1:318: ITERATOR
                {
                mITERATOR(); 

                }
                break;
            case 52 :
                // D:\\antlwork\\IISCaseLang.g:1:327: FUNCTION
                {
                mFUNCTION(); 

                }
                break;
            case 53 :
                // D:\\antlwork\\IISCaseLang.g:1:336: CONCAT_OPERATOR
                {
                mCONCAT_OPERATOR(); 

                }
                break;
            case 54 :
                // D:\\antlwork\\IISCaseLang.g:1:352: ADD_OPERATOR
                {
                mADD_OPERATOR(); 

                }
                break;
            case 55 :
                // D:\\antlwork\\IISCaseLang.g:1:365: SUB_OPERATOR
                {
                mSUB_OPERATOR(); 

                }
                break;
            case 56 :
                // D:\\antlwork\\IISCaseLang.g:1:378: MUL_OPERATOR
                {
                mMUL_OPERATOR(); 

                }
                break;
            case 57 :
                // D:\\antlwork\\IISCaseLang.g:1:391: MOD_OPERATOR
                {
                mMOD_OPERATOR(); 

                }
                break;
            case 58 :
                // D:\\antlwork\\IISCaseLang.g:1:404: EQU_OP
                {
                mEQU_OP(); 

                }
                break;
            case 59 :
                // D:\\antlwork\\IISCaseLang.g:1:411: IN
                {
                mIN(); 

                }
                break;
            case 60 :
                // D:\\antlwork\\IISCaseLang.g:1:414: OUT
                {
                mOUT(); 

                }
                break;
            case 61 :
                // D:\\antlwork\\IISCaseLang.g:1:418: INOUT
                {
                mINOUT(); 

                }
                break;
            case 62 :
                // D:\\antlwork\\IISCaseLang.g:1:424: LIKE
                {
                mLIKE(); 

                }
                break;
            case 63 :
                // D:\\antlwork\\IISCaseLang.g:1:429: SQ_STRING_LIT
                {
                mSQ_STRING_LIT(); 

                }
                break;
            case 64 :
                // D:\\antlwork\\IISCaseLang.g:1:443: TRUE
                {
                mTRUE(); 

                }
                break;
            case 65 :
                // D:\\antlwork\\IISCaseLang.g:1:448: FALSE
                {
                mFALSE(); 

                }
                break;
            case 66 :
                // D:\\antlwork\\IISCaseLang.g:1:454: NULL
                {
                mNULL(); 

                }
                break;
            case 67 :
                // D:\\antlwork\\IISCaseLang.g:1:459: COMPARASION_OPERATOR
                {
                mCOMPARASION_OPERATOR(); 

                }
                break;
            case 68 :
                // D:\\antlwork\\IISCaseLang.g:1:480: IMPLY_OP
                {
                mIMPLY_OP(); 

                }
                break;
            case 69 :
                // D:\\antlwork\\IISCaseLang.g:1:489: OR
                {
                mOR(); 

                }
                break;
            case 70 :
                // D:\\antlwork\\IISCaseLang.g:1:492: AND
                {
                mAND(); 

                }
                break;
            case 71 :
                // D:\\antlwork\\IISCaseLang.g:1:496: XOR
                {
                mXOR(); 

                }
                break;
            case 72 :
                // D:\\antlwork\\IISCaseLang.g:1:500: NOT
                {
                mNOT(); 

                }
                break;
            case 73 :
                // D:\\antlwork\\IISCaseLang.g:1:504: PRINT
                {
                mPRINT(); 

                }
                break;
            case 74 :
                // D:\\antlwork\\IISCaseLang.g:1:510: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 75 :
                // D:\\antlwork\\IISCaseLang.g:1:521: TIME
                {
                mTIME(); 

                }
                break;
            case 76 :
                // D:\\antlwork\\IISCaseLang.g:1:526: DATE
                {
                mDATE(); 

                }
                break;
            case 77 :
                // D:\\antlwork\\IISCaseLang.g:1:531: INTEGER
                {
                mINTEGER(); 

                }
                break;
            case 78 :
                // D:\\antlwork\\IISCaseLang.g:1:539: WS
                {
                mWS(); 

                }
                break;
            case 79 :
                // D:\\antlwork\\IISCaseLang.g:1:542: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 80 :
                // D:\\antlwork\\IISCaseLang.g:1:550: COMMENT_MULTYLINE
                {
                mCOMMENT_MULTYLINE(); 

                }
                break;

        }

    }


 

}