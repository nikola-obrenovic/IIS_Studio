 // $ANTLR 3.0.1 C:\\CheckConstraintExpression.g 2008-06-29 19:55:49

 package iisc;
 import iisc.JDBCQuery;
 import java.sql.ResultSet;
 import java.sql.Connection;
 import java.util.Hashtable;


 import org.antlr.runtime.*;
 import java.util.Stack;
 import java.util.List;
 import java.util.ArrayList;

 public class CheckConstraintExpressionParser extends Parser {
     public static final String[] tokenNames = new String[] {
         "<invalid>", "<EOR>", "<DOWN>", "<UP>", "OR", "AND", "XOR", "COMP_OP", "COMPARISON_OPERATOR", "ADD_OPERATOR", "SUB_OPERATOR", "MULTIPLAY_OPERATOR", "DIVISION_OPERATOR", "CONCAT_OPERATOR", "NOT", "LIKE", "SQ_STRING_LIT", "IN", "IDENTIFIER", "INTEGER", "TRUE", "FALSE", "NULL", "WS", "COMMENT", "'<=>'", "'=>'", "'('", "')'", "','", "'.'", "':'", "'..'", "'{'", "'}'"
     };
     public static final int INTEGER=19;
     public static final int COMP_OP=7;
     public static final int XOR=6;
     public static final int COMPARISON_OPERATOR=8;
     public static final int MULTIPLAY_OPERATOR=11;
     public static final int NULL=22;
     public static final int SUB_OPERATOR=10;
     public static final int ADD_OPERATOR=9;
     public static final int NOT=14;
     public static final int AND=5;
     public static final int CONCAT_OPERATOR=13;
     public static final int EOF=-1;
     public static final int TRUE=20;
     public static final int WS=23;
     public static final int IN=17;
     public static final int IDENTIFIER=18;
     public static final int OR=4;
     public static final int SQ_STRING_LIT=16;
     public static final int COMMENT=24;
     public static final int FALSE=21;
     public static final int LIKE=15;
     public static final int DIVISION_OPERATOR=12;

         public CheckConstraintExpressionParser(TokenStream input) {
             super(input);
         }
         

     public String[] getTokenNames() { return tokenNames; }
     public String getGrammarFileName() { return "C:\\CheckConstraintExpression.g"; }

     
         public int typeOfParser = 0;
     //  0 - domain
     //  1 - atribut
     //  2 - tip komponenti
         private String errorText = "";
     
         public String rootNodeName = "";        
         public String treeID = "-1";
         public String ID_2 = "-1";
         public String ID = "-1";
         public Connection con;
         private Hashtable numbers = new Hashtable();
         
         public String getErrorMessage(RecognitionException e, String[] tokenNames){
                 String msg = super.getErrorMessage(e, tokenNames);
                 errorText += msg + "#!#E#!#";
                 //System.out.println("JOVO::: " + msg);
                 return msg;
         }
         
         public String getTokenErrorDisplay(Token t) {
                 return t.toString();
         }   
         
         public String getMyError(){
                 return errorText;
         }
     //************************************************************************************
         private void checkCompTypeAtts(String val){
                 if( typeOfParser == 2 )
                 try{   
                     JDBCQuery query=new JDBCQuery(con);
                     ResultSet rs;
                     //String atts = "";
                     String idp = String.valueOf(ID);
                     boolean ok = false;
                     while(true)
                     {
                         rs = query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE " +
                                             " where PR_id="+ treeID +" and  TF_ID="+ ID_2 +" and TOB_id = " + idp);
                                             
                         String nextIDP = null;
                         
                         if( rs.next() )
                             nextIDP = rs.getString("TOB_superord");
                         
                         rs = query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_ATT_TOB,IISC_ATTRIBUTE " +
                                         " where IISC_COMPONENT_TYPE_OBJECT_TYPE.tob_id = IISC_ATT_TOB.tob_id AND " +
                                         " IISC_COMPONENT_TYPE_OBJECT_TYPE.tf_id = IISC_ATT_TOB.tf_id AND " +
                                         " IISC_COMPONENT_TYPE_OBJECT_TYPE.PR_id = IISC_ATT_TOB.PR_id AND " +
                                         " IISC_ATT_TOB.ATT_id = IISC_ATTRIBUTE.ATT_id AND " +
                                         " IISC_ATT_TOB.PR_id = IISC_ATTRIBUTE.PR_id AND " +
                                         " IISC_COMPONENT_TYPE_OBJECT_TYPE.PR_id = "+ treeID +" and " +
                                         " IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_ID = "+ ID_2 +" and " +
                                         " IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id = " + idp);
     
                         while( rs.next() ){
                             //listModel.addElement(rs.getString("Att_mnem") + " : " + rs.getString("Tob_mnem") + " ");
                             if( val/*.toUpperCase()*/.compareTo( rs.getString("Att_mnem")/*.toUpperCase()*/ ) == 0 ){
                                 ok = true;
                                 break;
                             }
                         }
                         
                         if(nextIDP == null)
                             break;
                             
                         idp = nextIDP;                    
                     }
                     if(!ok)
                         errorText += "Attribut " + val + " dose not have subelements#!#E#!#"; // dodaj error
                         
                     rs.close();
                 }
                 catch(Exception ex){
                     System.out.println("ex:" + ex);
                 }       
                /*
                 try {
                     //String aaa="clicked.a.b.c.d.att";
                     String bbb[] = val.split("\\.");
                     int b = 0;
                     JDBCQuery query=new JDBCQuery(con);
                     ResultSet rs;
                     String atts = "";
                     String idp = ID;//String.valueOf(TOB);
                     
                     for(b = 0; b < bbb.length - 1 && idp != null ; b++){
     
                         rs = query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE " +
                                             " where PR_id="+ treeID +" and  TF_ID="+ ID_2 +" and TOB_mnem = '" + bbb[b] + "'");
                         //idp = null;
                         
                         if( rs.next() ){
                             String tmps = rs.getString("Tob_superord");
                             if( tmps != null && b + 2 < bbb.length)
                                 idp = tmps;    
                             else{
                                 b++;
                                 break;
                             }
                         }
                         
                     }
                     
                     if( b < bbb.length - 1 ){
         //                String error = "Componemt xxx are not in selectnio";
                         errorText += "Componemt " + bbb[b] + " are not in tree#!#E#!#";
                         return;
                     }
         
                 rs = query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_ATT_TOB,IISC_ATTRIBUTE " +
                                                     " where IISC_COMPONENT_TYPE_OBJECT_TYPE.tob_id = IISC_ATT_TOB.tob_id AND " +
                                                     " IISC_COMPONENT_TYPE_OBJECT_TYPE.tf_id = IISC_ATT_TOB.tf_id AND " +
                                                     " IISC_COMPONENT_TYPE_OBJECT_TYPE.PR_id = IISC_ATT_TOB.PR_id AND " +
                                                     " IISC_ATT_TOB.ATT_id = IISC_ATTRIBUTE.ATT_id AND " +
                                                     " IISC_ATT_TOB.PR_id = IISC_ATTRIBUTE.PR_id AND " +
                                                          " IISC_COMPONENT_TYPE_OBJECT_TYPE.PR_id = "+ treeID +" and " +
                                                          " IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_ID = "+ ID_2 +" and " +
                                                     " IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id = " + idp);
                     boolean ok = false;
                     while(rs.next()){
                         String tmps = rs.getString("Att_mnem");
                         if(tmps != null && bbb[bbb.length - 1].compareTo(tmps) == 0){
                             ok = true;
                             break;
                         }
                         //atts += rs.getString("Att_mnem") + "(" +  rs.getString("Tob_mnem") + "):";
                         //idp = rs.getString("Tob_superord");
                     }
                     
                     if(!ok){
         //                String error = "Wrong Attribute";
                         errorText += "Wrong Attribute " + bbb[bbb.length - 1] + "#!#E#!#"; // dodaj error       
                         return;
                     }
                     
             
                 }        
                 catch (Exception ex){
                     //JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
                     System.out.println("ER:" + ex.toString());
                 } 
                 */
         }
     //****************************************************************
         private void checkFunctions(){
                 try{   
                     JDBCQuery query=new JDBCQuery(con);
                     ResultSet rs;
     
                     rs = query.select("SELECT * " + 
                     "   FROM ((IISC_FUNCTION LEFT JOIN IISC_PACK_FUN ON IISC_FUNCTION.Fun_id = IISC_PACK_FUN.Fun_id) " +
                     "   LEFT JOIN IISC_PACKAGE ON IISC_PACK_FUN.Pack_id = IISC_PACKAGE.Pack_id) " +
                     "   LEFT JOIN IISC_DOMAIN ON IISC_FUNCTION.Dom_id = IISC_DOMAIN.Dom_id " + 
                     "   WHERE IISC_FUNCTION.PR_ID = " + treeID + " AND IISC_DOMAIN.PR_ID = " + treeID +
                     "   ORDER BY IISC_PACKAGE.Pack_name,IISC_FUNCTION.Fun_name ");
                     
                     while( rs.next() ){
                         String packName = rs.getString("Pack_name");
                         String domName = rs.getString("Dom_mnem");
                         String funName = rs.getString("Fun_name");
                         if(packName == null) packName = "";
                         else packName += ".";
                         if(domName == null) domName = "";
                         //System.out.println(packName + funName);
                         numbers.put( (packName + funName)/*.toUpperCase()*/ , (packName + funName)/*.toUpperCase()*/ );
                     }
     
                     rs.close();
                 }
                 catch(Exception ex){
                     System.out.println("ex:" + ex);
             }
         }
     //****************************************************************  
         private void checkDomAtts(){
     
         if( typeOfParser == 0 )
                 try {
                     JDBCQuery query=new JDBCQuery(con);
                     ResultSet rs1,rs2,rs3; 
     
                     String dom_ids = "-1";
                     String dom_type = "";               
            
                         //System.out.println("select * from IISC_Domain where PR_id="+ tree.ID +" and Dom_id= "+ nodeIDSintax);
                         rs1=query.select("select * from IISC_Domain where PR_id="+ treeID +" and Dom_id= "+ ID );
                         boolean ok = false;
                         
                         if(rs1.next()){
                             dom_type = rs1.getString("Dom_type");
                             dom_ids = rs1.getString("Dom_id");
                             if(dom_type != null){
                                 String tmpd = rs1.getString("Dom_parent");
                                 if(tmpd != null) dom_ids = tmpd;
                                 if(tmpd != null && ( dom_type.compareTo("1") == 0 || dom_type.compareTo("4") == 0 )){
                                     while(true){
                                         rs2=query.select("select * from IISC_Domain where PR_id="+ treeID +" and Dom_id = "+ ID);
                                         if(rs2.next()){
                                             dom_type = rs2.getString("Dom_type");
                                             if( dom_type != null && ( dom_type.compareTo("1") == 0 || dom_type.compareTo("4") == 0 ) )
                                                 dom_ids = rs2.getString("Dom_parent");
                                             else
                                                 break;
                                         }
                                         else 
                                             break;
                                     }
                                     
                                     if(rs2 != null)
                                         rs2.close();
                                 }
                             }
                         }
                         if ( dom_type != null && (dom_type.compareTo("2") == 0 || dom_type.compareTo("3") == 0)){
                             rs3=query.select("select * " +
                             " from IISC_Dom_Att,IISC_ATTRIBUTE " +
                             " where IISC_Dom_Att.PR_id = IISC_ATTRIBUTE.PR_id and " +
                             " IISC_Dom_Att.Att_id = IISC_ATTRIBUTE.Att_id and " +
                             " IISC_Dom_Att.PR_id="+ treeID +" and IISC_Dom_Att.Dom_id = "+ ID);
                             while(rs3.next()){
                                 String tmps = rs3.getString("Att_mnem").trim()/*.toUpperCase()*/;
                                 //dom_atts += tmps + ":::";
                                 numbers.put( tmps , tmps );
                             }
                             rs3.close();
                         }
                         rs1.close();                    
                         }
                         catch(Exception ex){
                                 System.out.println(ex);
                         }       
         }
         



     // $ANTLR start project
     // C:\\CheckConstraintExpression.g:267:1: project returns [String val] : tmp= expression EOF ;
     public final String project() throws RecognitionException {
         String val = null;

         String tmp = null;


         
                 tmp = "";

         try {
             // C:\\CheckConstraintExpression.g:272:1: (tmp= expression EOF )
             // C:\\CheckConstraintExpression.g:273:1: tmp= expression EOF
             {
              checkDomAtts(); checkFunctions(); 
             pushFollow(FOLLOW_expression_in_project58);
             tmp=expression();
             _fsp--;

             
                 val = "<block name=\"Expression\" group=\"-1\">" + tmp + "</block>";
                 val = val.replaceAll("><",">\n<") + "\n\n";
                 //System.out.println(val);      

             match(input,EOF,FOLLOW_EOF_in_project63); 

             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end project


     // $ANTLR start expression
     // C:\\CheckConstraintExpression.g:281:1: expression returns [String val] : tmp1= imply_expression ( '<=>' tmp2= imply_expression )* ;
     public final String expression() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 String tmp3 = "";
                 String sign = "";

         try {
             // C:\\CheckConstraintExpression.g:289:1: (tmp1= imply_expression ( '<=>' tmp2= imply_expression )* )
             // C:\\CheckConstraintExpression.g:290:1: tmp1= imply_expression ( '<=>' tmp2= imply_expression )*
             {
             pushFollow(FOLLOW_imply_expression_in_expression85);
             tmp1=imply_expression();
             _fsp--;

             val = tmp1;
             // C:\\CheckConstraintExpression.g:290:39: ( '<=>' tmp2= imply_expression )*
             loop1:
             do {
                 int alt1=2;
                 int LA1_0 = input.LA(1);

                 if ( (LA1_0==25) ) {
                     alt1=1;
                 }


                 switch (alt1) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:290:41: '<=>' tmp2= imply_expression
                     {
                      sign = input.LT(1).getText();
                     match(input,25,FOLLOW_25_in_expression93); 
                     pushFollow(FOLLOW_imply_expression_in_expression100);
                     tmp2=imply_expression();
                     _fsp--;

                      tmp3 += tmp2 ; 

                     }
                     break;

                 default :
                     break loop1;
                 }
             } while (true);

             
                 if(sign.compareTo("<=>") == 0)
                         val = "<block name=\"EQUIVALENCE\" group=\"2\">" + val + tmp3 + "</block>";


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end expression


     // $ANTLR start imply_expression
     // C:\\CheckConstraintExpression.g:298:1: imply_expression returns [String val] : tmp1= or_expression ( '=>' tmp2= or_expression )* ;
     public final String imply_expression() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 String tmp3 = "";
                 String sign = "";       

         try {
             // C:\\CheckConstraintExpression.g:306:1: (tmp1= or_expression ( '=>' tmp2= or_expression )* )
             // C:\\CheckConstraintExpression.g:307:1: tmp1= or_expression ( '=>' tmp2= or_expression )*
             {
             pushFollow(FOLLOW_or_expression_in_imply_expression130);
             tmp1=or_expression();
             _fsp--;

             val = tmp1;
             // C:\\CheckConstraintExpression.g:307:36: ( '=>' tmp2= or_expression )*
             loop2:
             do {
                 int alt2=2;
                 int LA2_0 = input.LA(1);

                 if ( (LA2_0==26) ) {
                     alt2=1;
                 }


                 switch (alt2) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:307:38: '=>' tmp2= or_expression
                     {
                      sign = input.LT(1).getText();
                     match(input,26,FOLLOW_26_in_imply_expression138); 
                     pushFollow(FOLLOW_or_expression_in_imply_expression144);
                     tmp2=or_expression();
                     _fsp--;

                      tmp3 += tmp2; 

                     }
                     break;

                 default :
                     break loop2;
                 }
             } while (true);

              
                 if(sign.compareTo("=>") == 0)
                         val = "<block name=\"IMPLICATION\"  group=\"2\">" + val + tmp3 + "</block>"; 


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end imply_expression


     // $ANTLR start or_expression
     // C:\\CheckConstraintExpression.g:315:1: or_expression returns [String val] : tmp1= and_expression ( OR tmp2= and_expression )* ;
     public final String or_expression() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 String tmp3 = "";
                 String sign = "";

         try {
             // C:\\CheckConstraintExpression.g:323:1: (tmp1= and_expression ( OR tmp2= and_expression )* )
             // C:\\CheckConstraintExpression.g:324:1: tmp1= and_expression ( OR tmp2= and_expression )*
             {
             pushFollow(FOLLOW_and_expression_in_or_expression173);
             tmp1=and_expression();
             _fsp--;

              val = tmp1;
             // C:\\CheckConstraintExpression.g:324:38: ( OR tmp2= and_expression )*
             loop3:
             do {
                 int alt3=2;
                 int LA3_0 = input.LA(1);

                 if ( (LA3_0==OR) ) {
                     alt3=1;
                 }


                 switch (alt3) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:324:40: OR tmp2= and_expression
                     {
                      sign = input.LT(1).getText(); /*System.out.println(sign);*/ 
                     match(input,OR,FOLLOW_OR_in_or_expression181); 
                     pushFollow(FOLLOW_and_expression_in_or_expression187);
                     tmp2=and_expression();
                     _fsp--;

                     
                         tmp3 += tmp2;


                     }
                     break;

                 default :
                     break loop3;
                 }
             } while (true);

             
                 if(sign.toUpperCase().compareTo("OR") == 0)
                         val = "<block name=\"OR\" group=\"1\">" + val + tmp3 + "</block>" ;


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end or_expression


     // $ANTLR start and_expression
     // C:\\CheckConstraintExpression.g:333:1: and_expression returns [String val] : tmp1= xor_expression ( AND tmp2= xor_expression )* ;
     public final String and_expression() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 String tmp3 = "";
                 String sign = "";

         try {
             // C:\\CheckConstraintExpression.g:341:1: (tmp1= xor_expression ( AND tmp2= xor_expression )* )
             // C:\\CheckConstraintExpression.g:342:1: tmp1= xor_expression ( AND tmp2= xor_expression )*
             {
             pushFollow(FOLLOW_xor_expression_in_and_expression215);
             tmp1=xor_expression();
             _fsp--;

              val = tmp1; 
             // C:\\CheckConstraintExpression.g:342:39: ( AND tmp2= xor_expression )*
             loop4:
             do {
                 int alt4=2;
                 int LA4_0 = input.LA(1);

                 if ( (LA4_0==AND) ) {
                     alt4=1;
                 }


                 switch (alt4) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:342:40: AND tmp2= xor_expression
                     {
                      sign = input.LT(1).getText(); 
                     match(input,AND,FOLLOW_AND_in_and_expression222); 
                     pushFollow(FOLLOW_xor_expression_in_and_expression228);
                     tmp2=xor_expression();
                     _fsp--;

                      tmp3 += tmp2; 

                     }
                     break;

                 default :
                     break loop4;
                 }
             } while (true);

             
                 if(sign.toUpperCase().compareTo("AND") == 0)
                         val = "<block name=\"AND\" group=\"1\">" + val + tmp3 + "</block>" ;


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end and_expression


     // $ANTLR start xor_expression
     // C:\\CheckConstraintExpression.g:349:1: xor_expression returns [String val] : tmp1= comparison ( XOR tmp2= comparison )* ;
     public final String xor_expression() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 String tmp3 = "";
                 String sign = "";       

         try {
             // C:\\CheckConstraintExpression.g:357:1: (tmp1= comparison ( XOR tmp2= comparison )* )
             // C:\\CheckConstraintExpression.g:358:1: tmp1= comparison ( XOR tmp2= comparison )*
             {
             pushFollow(FOLLOW_comparison_in_xor_expression256);
             tmp1=comparison();
             _fsp--;

             val = tmp1; 
             // C:\\CheckConstraintExpression.g:358:34: ( XOR tmp2= comparison )*
             loop5:
             do {
                 int alt5=2;
                 int LA5_0 = input.LA(1);

                 if ( (LA5_0==XOR) ) {
                     alt5=1;
                 }


                 switch (alt5) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:358:35: XOR tmp2= comparison
                     {
                      sign = input.LT(1).getText(); 
                     match(input,XOR,FOLLOW_XOR_in_xor_expression263); 
                     pushFollow(FOLLOW_comparison_in_xor_expression269);
                     tmp2=comparison();
                     _fsp--;

                      tmp3 += tmp2; 

                     }
                     break;

                 default :
                     break loop5;
                 }
             } while (true);

             
                 if(sign.toUpperCase().compareTo("XOR") == 0)
                         val = "<block name=\"XOR\"  group=\"1\">" + val + tmp3 + "</block>" ;


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end xor_expression


     // $ANTLR start comparison
     // C:\\CheckConstraintExpression.g:365:1: comparison returns [String val] : tmp1= equ_expression ( COMP_OP tmp2= equ_expression )* ;
     public final String comparison() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 String tmp3 = "";

         try {
             // C:\\CheckConstraintExpression.g:372:1: (tmp1= equ_expression ( COMP_OP tmp2= equ_expression )* )
             // C:\\CheckConstraintExpression.g:373:1: tmp1= equ_expression ( COMP_OP tmp2= equ_expression )*
             {
             pushFollow(FOLLOW_equ_expression_in_comparison297);
             tmp1=equ_expression();
             _fsp--;

             val = tmp1;
             // C:\\CheckConstraintExpression.g:373:37: ( COMP_OP tmp2= equ_expression )*
             loop6:
             do {
                 int alt6=2;
                 int LA6_0 = input.LA(1);

                 if ( (LA6_0==COMP_OP) ) {
                     alt6=1;
                 }


                 switch (alt6) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:373:38: COMP_OP tmp2= equ_expression
                     {
                     tmp3 = input.LT(1).getText();
                     match(input,COMP_OP,FOLLOW_COMP_OP_in_comparison304); 
                     pushFollow(FOLLOW_equ_expression_in_comparison310);
                     tmp2=equ_expression();
                     _fsp--;

                      
                         String tmpv = "<block name=\""; 
                         if(tmp3.compareTo("==") == 0 )  tmpv += "IS EQUAL"; 
                         else    tmpv += "IS DIFFERENT"; 
                     
                         val = tmpv + "\" group=\"4\">" + val + tmp2 + "</block>";


                     }
                     break;

                 default :
                     break loop6;
                 }
             } while (true);


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end comparison


     // $ANTLR start equ_expression
     // C:\\CheckConstraintExpression.g:382:1: equ_expression returns [String val] : tmp1= add_expression ( COMPARISON_OPERATOR tmp2= add_expression )* ;
     public final String equ_expression() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 String tmp3 = "";

         try {
             // C:\\CheckConstraintExpression.g:389:1: (tmp1= add_expression ( COMPARISON_OPERATOR tmp2= add_expression )* )
             // C:\\CheckConstraintExpression.g:390:1: tmp1= add_expression ( COMPARISON_OPERATOR tmp2= add_expression )*
             {
             pushFollow(FOLLOW_add_expression_in_equ_expression337);
             tmp1=add_expression();
             _fsp--;

             val = tmp1; 
             // C:\\CheckConstraintExpression.g:390:38: ( COMPARISON_OPERATOR tmp2= add_expression )*
             loop7:
             do {
                 int alt7=2;
                 int LA7_0 = input.LA(1);

                 if ( (LA7_0==COMPARISON_OPERATOR) ) {
                     alt7=1;
                 }


                 switch (alt7) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:390:40: COMPARISON_OPERATOR tmp2= add_expression
                     {
                     tmp3 = input.LT(1).getText();
                     match(input,COMPARISON_OPERATOR,FOLLOW_COMPARISON_OPERATOR_in_equ_expression345); 
                     pushFollow(FOLLOW_add_expression_in_equ_expression351);
                     tmp2=add_expression();
                     _fsp--;

                     
                         String tmpv = "<block name=\""; 
                         if(tmp3.compareTo("<") == 0 )           tmpv += "LESS THAN"; 
                         else if(tmp3.compareTo("<=") == 0 )     tmpv += "LESS THAN OR EQUAL TO"; 
                         else if(tmp3.compareTo(">") == 0 )      tmpv += "GREATER THAN"; 
                         else if(tmp3.compareTo(">=") == 0 )     tmpv += "GREATER THAN OR EQUAL TO ";                            
                         val = tmpv + "\" group=\"4\">" + val + tmp2 + "</block>";       


                     }
                     break;

                 default :
                     break loop7;
                 }
             } while (true);


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end equ_expression


     // $ANTLR start add_expression
     // C:\\CheckConstraintExpression.g:400:1: add_expression returns [String val] : tmp1= sub_expression ( ADD_OPERATOR tmp2= sub_expression )* ;
     public final String add_expression() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 String tmp3 = "";
                 String sign = "";

         try {
             // C:\\CheckConstraintExpression.g:408:1: (tmp1= sub_expression ( ADD_OPERATOR tmp2= sub_expression )* )
             // C:\\CheckConstraintExpression.g:409:1: tmp1= sub_expression ( ADD_OPERATOR tmp2= sub_expression )*
             {
             pushFollow(FOLLOW_sub_expression_in_add_expression377);
             tmp1=sub_expression();
             _fsp--;

              val = tmp1; 
             // C:\\CheckConstraintExpression.g:409:39: ( ADD_OPERATOR tmp2= sub_expression )*
             loop8:
             do {
                 int alt8=2;
                 int LA8_0 = input.LA(1);

                 if ( (LA8_0==ADD_OPERATOR) ) {
                     alt8=1;
                 }


                 switch (alt8) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:409:41: ADD_OPERATOR tmp2= sub_expression
                     {
                     sign = input.LT(1).getText();
                     match(input,ADD_OPERATOR,FOLLOW_ADD_OPERATOR_in_add_expression385); 
                     pushFollow(FOLLOW_sub_expression_in_add_expression391);
                     tmp2=sub_expression();
                     _fsp--;

                     
                         tmp3 += tmp2;           


                     }
                     break;

                 default :
                     break loop8;
                 }
             } while (true);

             
                 /*String tmpv = "<block name=\""; 
                 if(tmp3.compareTo("+") == 0 )           tmpv += "SUM"; 
                 else if(tmp3.compareTo("-") == 0 )      tmpv += "SUBTRACTION"; 
                 val = tmpv + "\" group=\"5\">" + val + tmp2 + "</block>";
                 */
                 if(sign.compareTo("+") == 0)
                         val = "<block name=\"ADDITION\" group=\"5\">" + val + tmp3 + "</block>" ;       


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end add_expression


     // $ANTLR start sub_expression
     // C:\\CheckConstraintExpression.g:423:1: sub_expression returns [String val] : tmp1= multiplay_expression ( SUB_OPERATOR tmp2= multiplay_expression )* ;
     public final String sub_expression() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 String tmp3 = "";
                 String sign = "";

         try {
             // C:\\CheckConstraintExpression.g:431:1: (tmp1= multiplay_expression ( SUB_OPERATOR tmp2= multiplay_expression )* )
             // C:\\CheckConstraintExpression.g:432:1: tmp1= multiplay_expression ( SUB_OPERATOR tmp2= multiplay_expression )*
             {
             pushFollow(FOLLOW_multiplay_expression_in_sub_expression419);
             tmp1=multiplay_expression();
             _fsp--;

              val = tmp1; 
             // C:\\CheckConstraintExpression.g:432:45: ( SUB_OPERATOR tmp2= multiplay_expression )*
             loop9:
             do {
                 int alt9=2;
                 int LA9_0 = input.LA(1);

                 if ( (LA9_0==SUB_OPERATOR) ) {
                     alt9=1;
                 }


                 switch (alt9) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:432:47: SUB_OPERATOR tmp2= multiplay_expression
                     {
                     sign = input.LT(1).getText();
                     match(input,SUB_OPERATOR,FOLLOW_SUB_OPERATOR_in_sub_expression427); 
                     pushFollow(FOLLOW_multiplay_expression_in_sub_expression433);
                     tmp2=multiplay_expression();
                     _fsp--;

                     
                         tmp3 += tmp2;
                         /*
                         String tmpv = "<block name=\""; 
                         if(tmp3.compareTo("+") == 0 )           tmpv += "SUM"; 
                         else if(tmp3.compareTo("-") == 0 )      tmpv += "SUBTRACTION"; 
                         val = tmpv + "\" group=\"5\">" + val + tmp2 + "</block>";               
                         */
                         


                     }
                     break;

                 default :
                     break loop9;
                 }
             } while (true);

             
                 if(sign.compareTo("-") == 0)
                         val = "<block name=\"SUBTRACTION\" group=\"5\">" + val + tmp3 + "</block>" ;


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end sub_expression


     // $ANTLR start multiplay_expression
     // C:\\CheckConstraintExpression.g:449:1: multiplay_expression returns [String val] : tmp1= term ( MULTIPLAY_OPERATOR tmp2= term )* ;
     public final String multiplay_expression() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 String tmp3 = "";
                 String sign = "";

         try {
             // C:\\CheckConstraintExpression.g:457:1: (tmp1= term ( MULTIPLAY_OPERATOR tmp2= term )* )
             // C:\\CheckConstraintExpression.g:458:1: tmp1= term ( MULTIPLAY_OPERATOR tmp2= term )*
             {
             pushFollow(FOLLOW_term_in_multiplay_expression462);
             tmp1=term();
             _fsp--;

              val = tmp1; 
             // C:\\CheckConstraintExpression.g:458:29: ( MULTIPLAY_OPERATOR tmp2= term )*
             loop10:
             do {
                 int alt10=2;
                 int LA10_0 = input.LA(1);

                 if ( (LA10_0==MULTIPLAY_OPERATOR) ) {
                     alt10=1;
                 }


                 switch (alt10) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:458:30: MULTIPLAY_OPERATOR tmp2= term
                     {
                     sign = input.LT(1).getText();
                     match(input,MULTIPLAY_OPERATOR,FOLLOW_MULTIPLAY_OPERATOR_in_multiplay_expression469); 
                     pushFollow(FOLLOW_term_in_multiplay_expression475);
                     tmp2=term();
                     _fsp--;

                     
                         tmp3 += tmp2;
                         /*String tmpv = "<block name=\""; 
                         if(tmp3.compareTo("*") == 0 )           tmpv += "MULTIPLICATION"; 
                         else if(tmp3.compareTo("/") == 0 )      tmpv += "DIVISION"; 
                         val = tmpv + "\" group=\"5\">" + val + tmp2 + "</block>";               
                         */


                     }
                     break;

                 default :
                     break loop10;
                 }
             } while (true);

             
                 if(sign.compareTo("*") == 0)
                         val = "<block name=\"MULTIPLICATION\" group=\"5\">" + val + tmp3 + "</block>" ;


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end multiplay_expression


     // $ANTLR start term
     // C:\\CheckConstraintExpression.g:475:1: term returns [String val] : tmp1= concat_expression ( DIVISION_OPERATOR tmp2= concat_expression )* ;
     public final String term() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 String tmp3 = "";
                 String sign = "";

         try {
             // C:\\CheckConstraintExpression.g:483:1: (tmp1= concat_expression ( DIVISION_OPERATOR tmp2= concat_expression )* )
             // C:\\CheckConstraintExpression.g:484:1: tmp1= concat_expression ( DIVISION_OPERATOR tmp2= concat_expression )*
             {
             pushFollow(FOLLOW_concat_expression_in_term506);
             tmp1=concat_expression();
             _fsp--;

              val = tmp1; 
             // C:\\CheckConstraintExpression.g:484:42: ( DIVISION_OPERATOR tmp2= concat_expression )*
             loop11:
             do {
                 int alt11=2;
                 int LA11_0 = input.LA(1);

                 if ( (LA11_0==DIVISION_OPERATOR) ) {
                     alt11=1;
                 }


                 switch (alt11) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:484:43: DIVISION_OPERATOR tmp2= concat_expression
                     {
                     sign = input.LT(1).getText();
                     match(input,DIVISION_OPERATOR,FOLLOW_DIVISION_OPERATOR_in_term513); 
                     pushFollow(FOLLOW_concat_expression_in_term519);
                     tmp2=concat_expression();
                     _fsp--;

                     
                         /*String tmpv = "<block name=\""; 
                         if(tmp3.compareTo("*") == 0 )           tmpv += "MULTIPLICATION"; 
                         else if(tmp3.compareTo("/") == 0 )      tmpv += "DIVISION"; 
                         val = tmpv + "\" group=\"5\">" + val + tmp2 + "</block>";
                         */
                         tmp3 += tmp2;


                     }
                     break;

                 default :
                     break loop11;
                 }
             } while (true);

             
                 if(sign.compareTo("/") == 0)
                         val = "<block name=\"DIVISION\" group=\"5\">" + val + tmp3 + "</block>" ;


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end term


     // $ANTLR start concat_expression
     // C:\\CheckConstraintExpression.g:500:1: concat_expression returns [String val] : tmp1= unary_expression ( CONCAT_OPERATOR tmp2= unary_expression )* ;
     public final String concat_expression() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 String tmp3 = "";
                 String sign = "";

         try {
             // C:\\CheckConstraintExpression.g:508:1: (tmp1= unary_expression ( CONCAT_OPERATOR tmp2= unary_expression )* )
             // C:\\CheckConstraintExpression.g:509:1: tmp1= unary_expression ( CONCAT_OPERATOR tmp2= unary_expression )*
             {
             pushFollow(FOLLOW_unary_expression_in_concat_expression549);
             tmp1=unary_expression();
             _fsp--;

              val = tmp1; 
             // C:\\CheckConstraintExpression.g:509:41: ( CONCAT_OPERATOR tmp2= unary_expression )*
             loop12:
             do {
                 int alt12=2;
                 int LA12_0 = input.LA(1);

                 if ( (LA12_0==CONCAT_OPERATOR) ) {
                     alt12=1;
                 }


                 switch (alt12) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:509:42: CONCAT_OPERATOR tmp2= unary_expression
                     {
                     sign = input.LT(1).getText();
                     match(input,CONCAT_OPERATOR,FOLLOW_CONCAT_OPERATOR_in_concat_expression556); 
                     pushFollow(FOLLOW_unary_expression_in_concat_expression562);
                     tmp2=unary_expression();
                     _fsp--;

                     
                         tmp3 += tmp2;
                         /*String tmpv = "<block name=\""; 
                         if(tmp3.compareTo("*") == 0 )           tmpv += "MULTIPLICATION"; 
                         else if(tmp3.compareTo("/") == 0 )      tmpv += "DIVISION"; 
                         val = tmpv + "\" group=\"5\">" + val + tmp2 + "</block>";               
                         */


                     }
                     break;

                 default :
                     break loop12;
                 }
             } while (true);

             
                 if(sign.compareTo("||") == 0)
                         val = "<block name=\"CONCAT\" group=\"10\">" + val + tmp3 + "</block>" ;


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end concat_expression


     // $ANTLR start unary_expression
     // C:\\CheckConstraintExpression.g:526:1: unary_expression returns [String val] : ( NOT )? tmp2= like_expression ;
     public final String unary_expression() throws RecognitionException {
         String val = null;

         String tmp2 = null;


         
                 String tmp1 = "";
                 tmp2 = "";

         try {
             // C:\\CheckConstraintExpression.g:532:1: ( ( NOT )? tmp2= like_expression )
             // C:\\CheckConstraintExpression.g:533:2: ( NOT )? tmp2= like_expression
             {
             // C:\\CheckConstraintExpression.g:533:2: ( NOT )?
             int alt13=2;
             int LA13_0 = input.LA(1);

             if ( (LA13_0==NOT) ) {
                 alt13=1;
             }
             switch (alt13) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:533:4: NOT
                     {
                      tmp1 = input.LT(1).getText(); 
                     match(input,NOT,FOLLOW_NOT_in_unary_expression594); 

                     }
                     break;

             }

             pushFollow(FOLLOW_like_expression_in_unary_expression602);
             tmp2=like_expression();
             _fsp--;

             
                 if( tmp1.compareTo("-") != 0 && tmp1.toUpperCase().compareTo("NOT") != 0 )      val = tmp2;
                 else    val = "<block name=\"" + tmp1 + "\" group=\"0\">" + tmp2 + "</block>";


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end unary_expression


     // $ANTLR start like_expression
     // C:\\CheckConstraintExpression.g:539:1: like_expression returns [String val] : tmp1= in_expression ( LIKE SQ_STRING_LIT )? ;
     public final String like_expression() throws RecognitionException {
         String val = null;

         String tmp1 = null;


         
                 tmp1 = "";
                 String tmp2 = "";

         try {
             // C:\\CheckConstraintExpression.g:545:1: (tmp1= in_expression ( LIKE SQ_STRING_LIT )? )
             // C:\\CheckConstraintExpression.g:546:1: tmp1= in_expression ( LIKE SQ_STRING_LIT )?
             {
             pushFollow(FOLLOW_in_expression_in_like_expression626);
             tmp1=in_expression();
             _fsp--;

              val = tmp1; 
             // C:\\CheckConstraintExpression.g:546:38: ( LIKE SQ_STRING_LIT )?
             int alt14=2;
             int LA14_0 = input.LA(1);

             if ( (LA14_0==LIKE) ) {
                 alt14=1;
             }
             switch (alt14) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:546:39: LIKE SQ_STRING_LIT
                     {
                     match(input,LIKE,FOLLOW_LIKE_in_like_expression631); 
                     tmp2 = input.LT(1).getText();
                     match(input,SQ_STRING_LIT,FOLLOW_SQ_STRING_LIT_in_like_expression635); 
                     
                         val = "<block name=\"LIKE\" group=\"8\">" + 
                                 "<block name=\"LIKE Expression\" group=\"88\">" + tmp1 + "</block>" +
                                 "<element name=\"LIKE Pattern\" group=\"888\" value=\"" + tmp2 + "\">" + tmp2 + "</element>" + 
                                 "</block>";


                     }
                     break;

             }


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end like_expression


     // $ANTLR start in_expression
     // C:\\CheckConstraintExpression.g:555:1: in_expression returns [String val] : tmp1= primary_expression ( IN tmp2= in_list )? ;
     public final String in_expression() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";

         try {
             // C:\\CheckConstraintExpression.g:561:1: (tmp1= primary_expression ( IN tmp2= in_list )? )
             // C:\\CheckConstraintExpression.g:562:1: tmp1= primary_expression ( IN tmp2= in_list )?
             {
             pushFollow(FOLLOW_primary_expression_in_in_expression661);
             tmp1=primary_expression();
             _fsp--;

              val = tmp1; 
             // C:\\CheckConstraintExpression.g:562:43: ( IN tmp2= in_list )?
             int alt15=2;
             int LA15_0 = input.LA(1);

             if ( (LA15_0==IN) ) {
                 alt15=1;
             }
             switch (alt15) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:562:45: IN tmp2= in_list
                     {
                     match(input,IN,FOLLOW_IN_in_in_expression667); 
                     pushFollow(FOLLOW_in_list_in_in_expression673);
                     tmp2=in_list();
                     _fsp--;

                     
                         val = "<block name=\"IN\" group=\"9\">" + 
                                 "<block name=\"IN_Expression\" group=\"99\">" + tmp1 + "</block>" +
                                 "<block name=\"SET\" group=\"999\">"+ tmp2 + "</block>" +
                                 "</block>";


                     }
                     break;

             }


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end in_expression


     // $ANTLR start primary_expression
     // C:\\CheckConstraintExpression.g:575:1: primary_expression returns [String val] : (tmp1= unsigned_constant | tmp2= variable | (tmp3= func_name '(' tmp4= param_list ')' ) | '(' tmp5= expression ( ',' tmp6= expression )* ')' );
     public final String primary_expression() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;

         String tmp3 = null;

         String tmp4 = null;

         String tmp5 = null;

         String tmp6 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 tmp3 = "";
                 tmp4 = "";
                 tmp5 = "";
                 tmp6 = "";

         try {
             // C:\\CheckConstraintExpression.g:585:1: (tmp1= unsigned_constant | tmp2= variable | (tmp3= func_name '(' tmp4= param_list ')' ) | '(' tmp5= expression ( ',' tmp6= expression )* ')' )
             int alt17=4;
             alt17 = dfa17.predict(input);
             switch (alt17) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:586:1: tmp1= unsigned_constant
                     {
                     pushFollow(FOLLOW_unsigned_constant_in_primary_expression704);
                     tmp1=unsigned_constant();
                     _fsp--;

                      val = tmp1; 

                     }
                     break;
                 case 2 :
                     // C:\\CheckConstraintExpression.g:587:1: tmp2= variable
                     {
                     pushFollow(FOLLOW_variable_in_primary_expression715);
                     tmp2=variable();
                     _fsp--;

                      val = tmp2; 

                     }
                     break;
                 case 3 :
                     // C:\\CheckConstraintExpression.g:588:1: (tmp3= func_name '(' tmp4= param_list ')' )
                     {
                     // C:\\CheckConstraintExpression.g:588:1: (tmp3= func_name '(' tmp4= param_list ')' )
                     // C:\\CheckConstraintExpression.g:588:3: tmp3= func_name '(' tmp4= param_list ')'
                     {
                     pushFollow(FOLLOW_func_name_in_primary_expression728);
                     tmp3=func_name();
                     _fsp--;

                     match(input,27,FOLLOW_27_in_primary_expression730); 
                     pushFollow(FOLLOW_param_list_in_primary_expression736);
                     tmp4=param_list();
                     _fsp--;

                     match(input,28,FOLLOW_28_in_primary_expression738); 

                     }

                      val = "<block name=\"FUNCTION - " + tmp3 + "\" group=\"7\">" + tmp4 + "</block>"; 

                     }
                     break;
                 case 4 :
                     // C:\\CheckConstraintExpression.g:590:1: '(' tmp5= expression ( ',' tmp6= expression )* ')'
                     {
                     match(input,27,FOLLOW_27_in_primary_expression747); 
                     pushFollow(FOLLOW_expression_in_primary_expression753);
                     tmp5=expression();
                     _fsp--;

                      val = tmp5; 
                     // C:\\CheckConstraintExpression.g:591:2: ( ',' tmp6= expression )*
                     loop16:
                     do {
                         int alt16=2;
                         int LA16_0 = input.LA(1);

                         if ( (LA16_0==29) ) {
                             alt16=1;
                         }


                         switch (alt16) {
                         case 1 :
                             // C:\\CheckConstraintExpression.g:591:3: ',' tmp6= expression
                             {
                             match(input,29,FOLLOW_29_in_primary_expression761); 
                             pushFollow(FOLLOW_expression_in_primary_expression767);
                             tmp6=expression();
                             _fsp--;

                              val += tmp6;

                             }
                             break;

                         default :
                             break loop16;
                         }
                     } while (true);

                     
                                         if( !tmp6.equals("") ){
                                                 val = "<block name=\"TUPLE\" group=\"13\">" + val + "</block>";
                                         }
                                         else{
                                                 val = "<block name=\"BLOCK\" group=\"78\">" + tmp5 + "</block>";
                                         }
                                 
                     match(input,28,FOLLOW_28_in_primary_expression778); 

                     }
                     break;

             }
         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end primary_expression


     // $ANTLR start func_name
     // C:\\CheckConstraintExpression.g:603:1: func_name returns [String val] : IDENTIFIER ( '.' IDENTIFIER )* ;
     public final String func_name() throws RecognitionException {
         String val = null;

         
                 String tmp1 = "";
                 String tmp2 = "";
                 String tmp3 = "";
                 String tmp4 = "";

         try {
             // C:\\CheckConstraintExpression.g:611:1: ( IDENTIFIER ( '.' IDENTIFIER )* )
             // C:\\CheckConstraintExpression.g:613:1: IDENTIFIER ( '.' IDENTIFIER )*
             {
             tmp1 = input.LT(1).getText();
             match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_func_name801); 
             // C:\\CheckConstraintExpression.g:613:44: ( '.' IDENTIFIER )*
             loop18:
             do {
                 int alt18=2;
                 int LA18_0 = input.LA(1);

                 if ( (LA18_0==30) ) {
                     alt18=1;
                 }


                 switch (alt18) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:613:46: '.' IDENTIFIER
                     {
                      tmp3 = input.LT(1).getText(); 
                     match(input,30,FOLLOW_30_in_func_name807); 
                      tmp2 = input.LT(1).getText(); 
                     match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_func_name811); 
                      tmp4 += tmp3 + tmp2;

                     }
                     break;

                 default :
                     break loop18;
                 }
             } while (true);

             
                 String tmp = tmp1 + tmp4;
                 //System.out.println("za funkcije");
                 if(numbers.get(tmp/*.toUpperCase()*/) == null)
                         errorText += "Function " + tmp + " dose not consist#!#E#!#"; // dodaj error     
                 val = tmp;      


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end func_name


     // $ANTLR start param_list2
     // C:\\CheckConstraintExpression.g:625:1: param_list2 returns [String val] : ( (tmp1= expression ( ( ':' | '..' ) tmp2= expression )? ( ',' tmp3= expression ( ( ':' | '..' ) tmp4= expression )? )* ) | );
     public final String param_list2() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;

         String tmp3 = null;

         String tmp4 = null;


         
                 String sign = "";

         try {
             // C:\\CheckConstraintExpression.g:630:1: ( (tmp1= expression ( ( ':' | '..' ) tmp2= expression )? ( ',' tmp3= expression ( ( ':' | '..' ) tmp4= expression )? )* ) | )
             int alt22=2;
             int LA22_0 = input.LA(1);

             if ( (LA22_0==NOT||LA22_0==SQ_STRING_LIT||(LA22_0>=IDENTIFIER && LA22_0<=NULL)||LA22_0==27) ) {
                 alt22=1;
             }
             else if ( (LA22_0==34) ) {
                 alt22=2;
             }
             else {
                 NoViableAltException nvae =
                     new NoViableAltException("625:1: param_list2 returns [String val] : ( (tmp1= expression ( ( ':' | '..' ) tmp2= expression )? ( ',' tmp3= expression ( ( ':' | '..' ) tmp4= expression )? )* ) | );", 22, 0, input);

                 throw nvae;
             }
             switch (alt22) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:633:1: (tmp1= expression ( ( ':' | '..' ) tmp2= expression )? ( ',' tmp3= expression ( ( ':' | '..' ) tmp4= expression )? )* )
                     {
                     // C:\\CheckConstraintExpression.g:633:1: (tmp1= expression ( ( ':' | '..' ) tmp2= expression )? ( ',' tmp3= expression ( ( ':' | '..' ) tmp4= expression )? )* )
                     // C:\\CheckConstraintExpression.g:633:3: tmp1= expression ( ( ':' | '..' ) tmp2= expression )? ( ',' tmp3= expression ( ( ':' | '..' ) tmp4= expression )? )*
                     {
                     pushFollow(FOLLOW_expression_in_param_list2846);
                     tmp1=expression();
                     _fsp--;

                      val = tmp1;
                     // C:\\CheckConstraintExpression.g:633:35: ( ( ':' | '..' ) tmp2= expression )?
                     int alt19=2;
                     int LA19_0 = input.LA(1);

                     if ( ((LA19_0>=31 && LA19_0<=32)) ) {
                         alt19=1;
                     }
                     switch (alt19) {
                         case 1 :
                             // C:\\CheckConstraintExpression.g:633:37: ( ':' | '..' ) tmp2= expression
                             {
                             if ( (input.LA(1)>=31 && input.LA(1)<=32) ) {
                                 input.consume();
                                 errorRecovery=false;
                             }
                             else {
                                 MismatchedSetException mse =
                                     new MismatchedSetException(null,input);
                                 recoverFromMismatchedSet(input,mse,FOLLOW_set_in_param_list2851);    throw mse;
                             }

                             pushFollow(FOLLOW_expression_in_param_list2863);
                             tmp2=expression();
                             _fsp--;

                                 val = "<block name=\"INTERVAL\" group=\"9999\">" + val + tmp2 + "</block>" ;

                             }
                             break;

                     }

                     // C:\\CheckConstraintExpression.g:634:2: ( ',' tmp3= expression ( ( ':' | '..' ) tmp4= expression )? )*
                     loop21:
                     do {
                         int alt21=2;
                         int LA21_0 = input.LA(1);

                         if ( (LA21_0==29) ) {
                             alt21=1;
                         }


                         switch (alt21) {
                         case 1 :
                             // C:\\CheckConstraintExpression.g:634:3: ',' tmp3= expression ( ( ':' | '..' ) tmp4= expression )?
                             {
                             match(input,29,FOLLOW_29_in_param_list2872); 
                             pushFollow(FOLLOW_expression_in_param_list2878);
                             tmp3=expression();
                             _fsp--;

                             // C:\\CheckConstraintExpression.g:634:25: ( ( ':' | '..' ) tmp4= expression )?
                             int alt20=2;
                             int LA20_0 = input.LA(1);

                             if ( ((LA20_0>=31 && LA20_0<=32)) ) {
                                 alt20=1;
                             }
                             switch (alt20) {
                                 case 1 :
                                     // C:\\CheckConstraintExpression.g:634:27: ( ':' | '..' ) tmp4= expression
                                     {
                                     sign = input.LT(1).getText(); 
                                     if ( (input.LA(1)>=31 && input.LA(1)<=32) ) {
                                         input.consume();
                                         errorRecovery=false;
                                     }
                                     else {
                                         MismatchedSetException mse =
                                             new MismatchedSetException(null,input);
                                         recoverFromMismatchedSet(input,mse,FOLLOW_set_in_param_list2883);    throw mse;
                                     }

                                     pushFollow(FOLLOW_expression_in_param_list2895);
                                     tmp4=expression();
                                     _fsp--;


                                     }
                                     break;

                             }

                                 
                                 if(     sign.equals(":") || sign.equals("..")   ){
                                         val += "<block name=\"INTERVAL\" group=\"9999\">" + tmp3 + tmp4 + "</block>" ;
                                 }
                                 else
                                         val += tmp3;


                             }
                             break;

                         default :
                             break loop21;
                         }
                     } while (true);


                     }


                     }
                     break;
                 case 2 :
                     // C:\\CheckConstraintExpression.g:642:7: 
                     {
                     }
                     break;

             }
         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end param_list2


     // $ANTLR start param_list
     // C:\\CheckConstraintExpression.g:649:1: param_list returns [String val] : ( (tmp1= expression ( ',' tmp2= expression )* ) | );
     public final String param_list() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         try {
             // C:\\CheckConstraintExpression.g:650:1: ( (tmp1= expression ( ',' tmp2= expression )* ) | )
             int alt24=2;
             int LA24_0 = input.LA(1);

             if ( (LA24_0==NOT||LA24_0==SQ_STRING_LIT||(LA24_0>=IDENTIFIER && LA24_0<=NULL)||LA24_0==27) ) {
                 alt24=1;
             }
             else if ( (LA24_0==28) ) {
                 alt24=2;
             }
             else {
                 NoViableAltException nvae =
                     new NoViableAltException("649:1: param_list returns [String val] : ( (tmp1= expression ( ',' tmp2= expression )* ) | );", 24, 0, input);

                 throw nvae;
             }
             switch (alt24) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:651:1: (tmp1= expression ( ',' tmp2= expression )* )
                     {
                     // C:\\CheckConstraintExpression.g:651:1: (tmp1= expression ( ',' tmp2= expression )* )
                     // C:\\CheckConstraintExpression.g:651:2: tmp1= expression ( ',' tmp2= expression )*
                     {
                     pushFollow(FOLLOW_expression_in_param_list929);
                     tmp1=expression();
                     _fsp--;

                      val = "<block name=\"ARG \" group=\"77\">" + tmp1 + "</block>"; 
                     // C:\\CheckConstraintExpression.g:652:2: ( ',' tmp2= expression )*
                     loop23:
                     do {
                         int alt23=2;
                         int LA23_0 = input.LA(1);

                         if ( (LA23_0==29) ) {
                             alt23=1;
                         }


                         switch (alt23) {
                         case 1 :
                             // C:\\CheckConstraintExpression.g:652:3: ',' tmp2= expression
                             {
                             match(input,29,FOLLOW_29_in_param_list936); 
                             pushFollow(FOLLOW_expression_in_param_list942);
                             tmp2=expression();
                             _fsp--;

                              val += "<block name=\"ARG \" group=\"77\">" + tmp2 + "</block>"; 

                             }
                             break;

                         default :
                             break loop23;
                         }
                     } while (true);


                     }


                     }
                     break;
                 case 2 :
                     // C:\\CheckConstraintExpression.g:652:100: 
                     {
                     }
                     break;

             }
         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end param_list


     // $ANTLR start in_list
     // C:\\CheckConstraintExpression.g:660:1: in_list returns [String val] : ( '{' tmp1= param_list2 '}' | tmp2= variable | (tmp3= func_name '(' tmp4= param_list ')' ) );
     public final String in_list() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;

         String tmp3 = null;

         String tmp4 = null;


         try {
             // C:\\CheckConstraintExpression.g:661:1: ( '{' tmp1= param_list2 '}' | tmp2= variable | (tmp3= func_name '(' tmp4= param_list ')' ) )
             int alt25=3;
             alt25 = dfa25.predict(input);
             switch (alt25) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:662:1: '{' tmp1= param_list2 '}'
                     {
                     match(input,33,FOLLOW_33_in_in_list970); 
                     pushFollow(FOLLOW_param_list2_in_in_list976);
                     tmp1=param_list2();
                     _fsp--;

                     match(input,34,FOLLOW_34_in_in_list978); 
                      val = tmp1; 

                     }
                     break;
                 case 2 :
                     // C:\\CheckConstraintExpression.g:663:1: tmp2= variable
                     {
                     pushFollow(FOLLOW_variable_in_in_list989);
                     tmp2=variable();
                     _fsp--;

                      val = tmp2; 

                     }
                     break;
                 case 3 :
                     // C:\\CheckConstraintExpression.g:664:1: (tmp3= func_name '(' tmp4= param_list ')' )
                     {
                     // C:\\CheckConstraintExpression.g:664:1: (tmp3= func_name '(' tmp4= param_list ')' )
                     // C:\\CheckConstraintExpression.g:664:3: tmp3= func_name '(' tmp4= param_list ')'
                     {
                     pushFollow(FOLLOW_func_name_in_in_list1002);
                     tmp3=func_name();
                     _fsp--;

                     match(input,27,FOLLOW_27_in_in_list1004); 
                     pushFollow(FOLLOW_param_list_in_in_list1010);
                     tmp4=param_list();
                     _fsp--;

                     match(input,28,FOLLOW_28_in_in_list1012); 

                     }

                      val = "<block name=\"Function - " + tmp3 + "\">" + tmp4 + "</block>"; 

                     }
                     break;

             }
         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end in_list


     // $ANTLR start constant
     // C:\\CheckConstraintExpression.g:675:1: constant returns [String val] : (tmp1= bool_lit | tmp2= number | SQ_STRING_LIT );
     public final String constant() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp2 = null;


         
                 tmp1 = "";
                 tmp2 = "";
                 String tmp3 = "";       

         try {
             // C:\\CheckConstraintExpression.g:682:1: (tmp1= bool_lit | tmp2= number | SQ_STRING_LIT )
             int alt26=3;
             switch ( input.LA(1) ) {
             case TRUE:
             case FALSE:
             case NULL:
                 {
                 alt26=1;
                 }
                 break;
             case SUB_OPERATOR:
             case INTEGER:
                 {
                 alt26=2;
                 }
                 break;
             case SQ_STRING_LIT:
                 {
                 alt26=3;
                 }
                 break;
             default:
                 NoViableAltException nvae =
                     new NoViableAltException("675:1: constant returns [String val] : (tmp1= bool_lit | tmp2= number | SQ_STRING_LIT );", 26, 0, input);

                 throw nvae;
             }

             switch (alt26) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:683:1: tmp1= bool_lit
                     {
                     pushFollow(FOLLOW_bool_lit_in_constant1047);
                     tmp1=bool_lit();
                     _fsp--;

                      val = tmp1; 

                     }
                     break;
                 case 2 :
                     // C:\\CheckConstraintExpression.g:683:35: tmp2= number
                     {
                     pushFollow(FOLLOW_number_in_constant1057);
                     tmp2=number();
                     _fsp--;

                      val = tmp2; 

                     }
                     break;
                 case 3 :
                     // C:\\CheckConstraintExpression.g:683:67: SQ_STRING_LIT
                     {
                      tmp3 = input.LT(1).getText(); 
                     match(input,SQ_STRING_LIT,FOLLOW_SQ_STRING_LIT_in_constant1065); 
                      val = tmp3; 

                     }
                     break;

             }
         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end constant


     // $ANTLR start unsigned_constant
     // C:\\CheckConstraintExpression.g:689:1: unsigned_constant returns [String val] : (tmp1= bool_lit | INTEGER | tmp3= real_lit | SQ_STRING_LIT );
     public final String unsigned_constant() throws RecognitionException {
         String val = null;

         String tmp1 = null;

         String tmp3 = null;


         
                 tmp1 = "";
                 String tmp2 = "";
                 tmp3 = "";
                 String tmp4 = "";                       
                 

         try {
             // C:\\CheckConstraintExpression.g:698:1: (tmp1= bool_lit | INTEGER | tmp3= real_lit | SQ_STRING_LIT )
             int alt27=4;
             switch ( input.LA(1) ) {
             case TRUE:
             case FALSE:
             case NULL:
                 {
                 alt27=1;
                 }
                 break;
             case INTEGER:
                 {
                 int LA27_2 = input.LA(2);

                 if ( (LA27_2==30) ) {
                     alt27=3;
                 }
                 else if ( (LA27_2==EOF||(LA27_2>=OR && LA27_2<=CONCAT_OPERATOR)||LA27_2==LIKE||LA27_2==IN||(LA27_2>=25 && LA27_2<=26)||(LA27_2>=28 && LA27_2<=29)||(LA27_2>=31 && LA27_2<=32)||LA27_2==34) ) {
                     alt27=2;
                 }
                 else {
                     NoViableAltException nvae =
                         new NoViableAltException("689:1: unsigned_constant returns [String val] : (tmp1= bool_lit | INTEGER | tmp3= real_lit | SQ_STRING_LIT );", 27, 2, input);

                     throw nvae;
                 }
                 }
                 break;
             case SQ_STRING_LIT:
                 {
                 alt27=4;
                 }
                 break;
             default:
                 NoViableAltException nvae =
                     new NoViableAltException("689:1: unsigned_constant returns [String val] : (tmp1= bool_lit | INTEGER | tmp3= real_lit | SQ_STRING_LIT );", 27, 0, input);

                 throw nvae;
             }

             switch (alt27) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:699:1: tmp1= bool_lit
                     {
                     pushFollow(FOLLOW_bool_lit_in_unsigned_constant1092);
                     tmp1=bool_lit();
                     _fsp--;

                      val = tmp1; 

                     }
                     break;
                 case 2 :
                     // C:\\CheckConstraintExpression.g:701:1: INTEGER
                     {
                      tmp2 = input.LT(1).getText(); 
                     match(input,INTEGER,FOLLOW_INTEGER_in_unsigned_constant1102); 
                      val = "<element name=\"Simple Expression\" group=\"0\" value=\"" + tmp2 + "\">" + tmp2 + "</element>"; 

                     }
                     break;
                 case 3 :
                     // C:\\CheckConstraintExpression.g:702:1: tmp3= real_lit
                     {
                     pushFollow(FOLLOW_real_lit_in_unsigned_constant1113);
                     tmp3=real_lit();
                     _fsp--;

                      val = tmp3; 

                     }
                     break;
                 case 4 :
                     // C:\\CheckConstraintExpression.g:703:1: SQ_STRING_LIT
                     {
                      tmp4 = input.LT(1).getText(); 
                     match(input,SQ_STRING_LIT,FOLLOW_SQ_STRING_LIT_in_unsigned_constant1122); 
                      val = "<element name=\"Simple Expression\" group=\"0\" value=\"" + tmp4 + "\">" + tmp4 + "</element>"; 

                     }
                     break;

             }
         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end unsigned_constant


     // $ANTLR start bool_lit
     // C:\\CheckConstraintExpression.g:709:1: bool_lit returns [String val] : ( TRUE | FALSE | NULL ) ;
     public final String bool_lit() throws RecognitionException {
         String val = null;

         
                 String tmp1 = "";

         try {
             // C:\\CheckConstraintExpression.g:714:1: ( ( TRUE | FALSE | NULL ) )
             // C:\\CheckConstraintExpression.g:715:1: ( TRUE | FALSE | NULL )
             {
              tmp1 = input.LT(1).getText(); 
             if ( (input.LA(1)>=TRUE && input.LA(1)<=NULL) ) {
                 input.consume();
                 errorRecovery=false;
             }
             else {
                 MismatchedSetException mse =
                     new MismatchedSetException(null,input);
                 recoverFromMismatchedSet(input,mse,FOLLOW_set_in_bool_lit1149);    throw mse;
             }

                 
                 val = "<element name=\"Simple Expression\" group=\"0\" value=\"" + tmp1 + "\">" + tmp1 + "</element>";  


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end bool_lit


     // $ANTLR start real_lit
     // C:\\CheckConstraintExpression.g:724:1: real_lit returns [String val] : INTEGER '.' INTEGER ;
     public final String real_lit() throws RecognitionException {
         String val = null;

         
                 String tmp1 = "";
                 String tmp2 = "";

         try {
             // C:\\CheckConstraintExpression.g:730:1: ( INTEGER '.' INTEGER )
             // C:\\CheckConstraintExpression.g:731:1: INTEGER '.' INTEGER
             {
              tmp1 = input.LT(1).getText(); 
             match(input,INTEGER,FOLLOW_INTEGER_in_real_lit1188); 
             match(input,30,FOLLOW_30_in_real_lit1190); 
              tmp2 = input.LT(1).getText(); 
             match(input,INTEGER,FOLLOW_INTEGER_in_real_lit1194); 
                 val = "<element name=\"Simple Expression\" group=\"0\" value=\""+tmp1+"."+tmp2+"\">"+tmp1+"."+tmp2+"</element>";        

             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end real_lit


     // $ANTLR start variable
     // C:\\CheckConstraintExpression.g:740:1: variable returns [String val] : IDENTIFIER ( '.' IDENTIFIER )* ;
     public final String variable() throws RecognitionException {
         String val = null;

         
                 String tmp1 = "";
                 String tmp2 = "";
                 String tmp3 = "";
                 String tmp4 = "";

         try {
             // C:\\CheckConstraintExpression.g:748:1: ( IDENTIFIER ( '.' IDENTIFIER )* )
             // C:\\CheckConstraintExpression.g:749:1: IDENTIFIER ( '.' IDENTIFIER )*
             {
             tmp1 = input.LT(1).getText();
             match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_variable1221); 
             // C:\\CheckConstraintExpression.g:749:44: ( '.' IDENTIFIER )*
             loop28:
             do {
                 int alt28=2;
                 int LA28_0 = input.LA(1);

                 if ( (LA28_0==30) ) {
                     alt28=1;
                 }


                 switch (alt28) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:749:46: '.' IDENTIFIER
                     {
                      tmp3 = input.LT(1).getText(); 
                     match(input,30,FOLLOW_30_in_variable1227); 
                      tmp2 = input.LT(1).getText(); 
                     match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_variable1231); 
                      tmp4 += tmp3 + tmp2;

                     }
                     break;

                 default :
                     break loop28;
                 }
             } while (true);

             
             /*
                 public String rootNodeName = "";        
                 public String treeID = "-1";
                 public String ID_2 = "-1";
                 public String ID = "-1";
             */
                 if(     typeOfParser == 0       ){
                         if(     tmp1.toUpperCase().compareTo("VALUE") != 0 && tmp1.toUpperCase().compareTo("THIS") != 0 ){
                                 errorText += "Mismatched input " + tmp1 + " where expecting VALUE or THIS#!#E#!#"; // dodaj error
                         }
                         else if(        tmp4.compareTo("") != 0         ){
                                 String subElements[] = tmp4.split("\\.");
                                 if(numbers.get(subElements[1]/*.toUpperCase()*/) == null)
                                         errorText += "Domain dose not consist " + subElements[1] + " attribute#!#E#!#"; // dodaj error          
                         }       
                 }
                 else if(        typeOfParser == 1       ){
                         if(     tmp1.compareTo(rootNodeName) != 0       ){
                                 errorText += "Mismatched input " + tmp1 + " where expecting Attribute Mnemonic#!#E#!#"; // dodaj error                  
                         }
                         else if(        tmp4.compareTo("") != 0 ){
                                 errorText += "Attribut " + tmp1 + " dose not have subelements#!#E#!#"; // dodaj error                                   
                         }
                 }
                 else if(        typeOfParser == 2       ){
                         if(tmp4.compareTo("") != 0)
                                 errorText += "Attribut " + tmp1 + " dose not have subelements#!#E#!#"; // dodaj error
                         else
                                 checkCompTypeAtts(tmp1);
                 }
                 
                 val = "<element name=\"Simple Expression\" group=\"0\" value=\""+tmp1+tmp3+tmp2+"\">"+tmp1+tmp3+tmp2+"</element>";


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end variable


     // $ANTLR start number
     // C:\\CheckConstraintExpression.g:789:1: number returns [String val] : ( SUB_OPERATOR )? INTEGER ( '.' INTEGER )? ;
     public final String number() throws RecognitionException {
         String val = null;

         
                 String tmp1 = "";
                 String tmp2 = "";
                 String tmp3 = "";

         try {
             // C:\\CheckConstraintExpression.g:796:1: ( ( SUB_OPERATOR )? INTEGER ( '.' INTEGER )? )
             // C:\\CheckConstraintExpression.g:797:1: ( SUB_OPERATOR )? INTEGER ( '.' INTEGER )?
             {
             // C:\\CheckConstraintExpression.g:797:1: ( SUB_OPERATOR )?
             int alt29=2;
             int LA29_0 = input.LA(1);

             if ( (LA29_0==SUB_OPERATOR) ) {
                 alt29=1;
             }
             switch (alt29) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:797:3: SUB_OPERATOR
                     {
                      tmp1 = input.LT(1).getText(); 
                     match(input,SUB_OPERATOR,FOLLOW_SUB_OPERATOR_in_number1263); 

                     }
                     break;

             }

              tmp2 = input.LT(1).getText(); 
             match(input,INTEGER,FOLLOW_INTEGER_in_number1269); 
             // C:\\CheckConstraintExpression.g:797:94: ( '.' INTEGER )?
             int alt30=2;
             int LA30_0 = input.LA(1);

             if ( (LA30_0==30) ) {
                 alt30=1;
             }
             switch (alt30) {
                 case 1 :
                     // C:\\CheckConstraintExpression.g:797:95: '.' INTEGER
                     {
                     match(input,30,FOLLOW_30_in_number1272); 
                      tmp3 = input.LT(1).getText(); 
                     match(input,INTEGER,FOLLOW_INTEGER_in_number1276); 

                     }
                     break;

             }

             
                 val = "<element name'\"Simple Expression\" group=\"0\" value=\"" + tmp1 + tmp2 + "." + tmp3 + "\">" + tmp1 + tmp2 + "." + tmp3 + "</element>";


             }

         }
         catch (RecognitionException re) {
             reportError(re);
             recover(input,re);
         }
         finally {
         }
         return val;
     }
     // $ANTLR end number


     protected DFA17 dfa17 = new DFA17(this);
     protected DFA25 dfa25 = new DFA25(this);
     static final String DFA17_eotS =
         "\10\uffff";
     static final String DFA17_eofS =
         "\2\uffff\1\6\4\uffff\1\6";
     static final String DFA17_minS =
         "\1\20\1\uffff\1\4\1\uffff\1\22\2\uffff\1\4";
     static final String DFA17_maxS =
         "\1\33\1\uffff\1\42\1\uffff\1\22\2\uffff\1\42";
     static final String DFA17_acceptS =
         "\1\uffff\1\1\1\uffff\1\4\1\uffff\1\3\1\2\1\uffff";
     static final String DFA17_specialS =
         "\10\uffff}>";
     static final String[] DFA17_transitionS = {
             "\1\1\1\uffff\1\2\4\1\4\uffff\1\3",
             "",
             "\12\6\1\uffff\1\6\1\uffff\1\6\7\uffff\2\6\1\5\2\6\1\4\2\6\1"+
             "\uffff\1\6",
             "",
             "\1\7",
             "",
             "",
             "\12\6\1\uffff\1\6\1\uffff\1\6\7\uffff\2\6\1\5\2\6\1\4\2\6\1"+
             "\uffff\1\6"
     };

     static final short[] DFA17_eot = DFA.unpackEncodedString(DFA17_eotS);
     static final short[] DFA17_eof = DFA.unpackEncodedString(DFA17_eofS);
     static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars(DFA17_minS);
     static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars(DFA17_maxS);
     static final short[] DFA17_accept = DFA.unpackEncodedString(DFA17_acceptS);
     static final short[] DFA17_special = DFA.unpackEncodedString(DFA17_specialS);
     static final short[][] DFA17_transition;

     static {
         int numStates = DFA17_transitionS.length;
         DFA17_transition = new short[numStates][];
         for (int i=0; i<numStates; i++) {
             DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
         }
     }

     class DFA17 extends DFA {

         public DFA17(BaseRecognizer recognizer) {
             this.recognizer = recognizer;
             this.decisionNumber = 17;
             this.eot = DFA17_eot;
             this.eof = DFA17_eof;
             this.min = DFA17_min;
             this.max = DFA17_max;
             this.accept = DFA17_accept;
             this.special = DFA17_special;
             this.transition = DFA17_transition;
         }
         public String getDescription() {
             return "575:1: primary_expression returns [String val] : (tmp1= unsigned_constant | tmp2= variable | (tmp3= func_name '(' tmp4= param_list ')' ) | '(' tmp5= expression ( ',' tmp6= expression )* ')' );";
         }
     }
     static final String DFA25_eotS =
         "\7\uffff";
     static final String DFA25_eofS =
         "\2\uffff\1\5\3\uffff\1\5";
     static final String DFA25_minS =
         "\1\22\1\uffff\1\4\1\22\2\uffff\1\4";
     static final String DFA25_maxS =
         "\1\41\1\uffff\1\42\1\22\2\uffff\1\42";
     static final String DFA25_acceptS =
         "\1\uffff\1\1\2\uffff\1\3\1\2\1\uffff";
     static final String DFA25_specialS =
         "\7\uffff}>";
     static final String[] DFA25_transitionS = {
             "\1\2\16\uffff\1\1",
             "",
             "\12\5\1\uffff\1\5\11\uffff\2\5\1\4\2\5\1\3\2\5\1\uffff\1\5",
             "\1\6",
             "",
             "",
             "\12\5\1\uffff\1\5\11\uffff\2\5\1\4\2\5\1\3\2\5\1\uffff\1\5"
     };

     static final short[] DFA25_eot = DFA.unpackEncodedString(DFA25_eotS);
     static final short[] DFA25_eof = DFA.unpackEncodedString(DFA25_eofS);
     static final char[] DFA25_min = DFA.unpackEncodedStringToUnsignedChars(DFA25_minS);
     static final char[] DFA25_max = DFA.unpackEncodedStringToUnsignedChars(DFA25_maxS);
     static final short[] DFA25_accept = DFA.unpackEncodedString(DFA25_acceptS);
     static final short[] DFA25_special = DFA.unpackEncodedString(DFA25_specialS);
     static final short[][] DFA25_transition;

     static {
         int numStates = DFA25_transitionS.length;
         DFA25_transition = new short[numStates][];
         for (int i=0; i<numStates; i++) {
             DFA25_transition[i] = DFA.unpackEncodedString(DFA25_transitionS[i]);
         }
     }

     class DFA25 extends DFA {

         public DFA25(BaseRecognizer recognizer) {
             this.recognizer = recognizer;
             this.decisionNumber = 25;
             this.eot = DFA25_eot;
             this.eof = DFA25_eof;
             this.min = DFA25_min;
             this.max = DFA25_max;
             this.accept = DFA25_accept;
             this.special = DFA25_special;
             this.transition = DFA25_transition;
         }
         public String getDescription() {
             return "660:1: in_list returns [String val] : ( '{' tmp1= param_list2 '}' | tmp2= variable | (tmp3= func_name '(' tmp4= param_list ')' ) );";
         }
     }
  

     public static final BitSet FOLLOW_expression_in_project58 = new BitSet(new long[]{0x0000000000000000L});
     public static final BitSet FOLLOW_EOF_in_project63 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_imply_expression_in_expression85 = new BitSet(new long[]{0x0000000002000002L});
     public static final BitSet FOLLOW_25_in_expression93 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_imply_expression_in_expression100 = new BitSet(new long[]{0x0000000002000002L});
     public static final BitSet FOLLOW_or_expression_in_imply_expression130 = new BitSet(new long[]{0x0000000004000002L});
     public static final BitSet FOLLOW_26_in_imply_expression138 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_or_expression_in_imply_expression144 = new BitSet(new long[]{0x0000000004000002L});
     public static final BitSet FOLLOW_and_expression_in_or_expression173 = new BitSet(new long[]{0x0000000000000012L});
     public static final BitSet FOLLOW_OR_in_or_expression181 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_and_expression_in_or_expression187 = new BitSet(new long[]{0x0000000000000012L});
     public static final BitSet FOLLOW_xor_expression_in_and_expression215 = new BitSet(new long[]{0x0000000000000022L});
     public static final BitSet FOLLOW_AND_in_and_expression222 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_xor_expression_in_and_expression228 = new BitSet(new long[]{0x0000000000000022L});
     public static final BitSet FOLLOW_comparison_in_xor_expression256 = new BitSet(new long[]{0x0000000000000042L});
     public static final BitSet FOLLOW_XOR_in_xor_expression263 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_comparison_in_xor_expression269 = new BitSet(new long[]{0x0000000000000042L});
     public static final BitSet FOLLOW_equ_expression_in_comparison297 = new BitSet(new long[]{0x0000000000000082L});
     public static final BitSet FOLLOW_COMP_OP_in_comparison304 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_equ_expression_in_comparison310 = new BitSet(new long[]{0x0000000000000082L});
     public static final BitSet FOLLOW_add_expression_in_equ_expression337 = new BitSet(new long[]{0x0000000000000102L});
     public static final BitSet FOLLOW_COMPARISON_OPERATOR_in_equ_expression345 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_add_expression_in_equ_expression351 = new BitSet(new long[]{0x0000000000000102L});
     public static final BitSet FOLLOW_sub_expression_in_add_expression377 = new BitSet(new long[]{0x0000000000000202L});
     public static final BitSet FOLLOW_ADD_OPERATOR_in_add_expression385 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_sub_expression_in_add_expression391 = new BitSet(new long[]{0x0000000000000202L});
     public static final BitSet FOLLOW_multiplay_expression_in_sub_expression419 = new BitSet(new long[]{0x0000000000000402L});
     public static final BitSet FOLLOW_SUB_OPERATOR_in_sub_expression427 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_multiplay_expression_in_sub_expression433 = new BitSet(new long[]{0x0000000000000402L});
     public static final BitSet FOLLOW_term_in_multiplay_expression462 = new BitSet(new long[]{0x0000000000000802L});
     public static final BitSet FOLLOW_MULTIPLAY_OPERATOR_in_multiplay_expression469 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_term_in_multiplay_expression475 = new BitSet(new long[]{0x0000000000000802L});
     public static final BitSet FOLLOW_concat_expression_in_term506 = new BitSet(new long[]{0x0000000000001002L});
     public static final BitSet FOLLOW_DIVISION_OPERATOR_in_term513 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_concat_expression_in_term519 = new BitSet(new long[]{0x0000000000001002L});
     public static final BitSet FOLLOW_unary_expression_in_concat_expression549 = new BitSet(new long[]{0x0000000000002002L});
     public static final BitSet FOLLOW_CONCAT_OPERATOR_in_concat_expression556 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_unary_expression_in_concat_expression562 = new BitSet(new long[]{0x0000000000002002L});
     public static final BitSet FOLLOW_NOT_in_unary_expression594 = new BitSet(new long[]{0x00000000087D0000L});
     public static final BitSet FOLLOW_like_expression_in_unary_expression602 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_in_expression_in_like_expression626 = new BitSet(new long[]{0x0000000000008002L});
     public static final BitSet FOLLOW_LIKE_in_like_expression631 = new BitSet(new long[]{0x0000000000010000L});
     public static final BitSet FOLLOW_SQ_STRING_LIT_in_like_expression635 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_primary_expression_in_in_expression661 = new BitSet(new long[]{0x0000000000020002L});
     public static final BitSet FOLLOW_IN_in_in_expression667 = new BitSet(new long[]{0x0000000200040000L});
     public static final BitSet FOLLOW_in_list_in_in_expression673 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_unsigned_constant_in_primary_expression704 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_variable_in_primary_expression715 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_func_name_in_primary_expression728 = new BitSet(new long[]{0x0000000008000000L});
     public static final BitSet FOLLOW_27_in_primary_expression730 = new BitSet(new long[]{0x00000000187D4000L});
     public static final BitSet FOLLOW_param_list_in_primary_expression736 = new BitSet(new long[]{0x0000000010000000L});
     public static final BitSet FOLLOW_28_in_primary_expression738 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_27_in_primary_expression747 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_expression_in_primary_expression753 = new BitSet(new long[]{0x0000000030000000L});
     public static final BitSet FOLLOW_29_in_primary_expression761 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_expression_in_primary_expression767 = new BitSet(new long[]{0x0000000030000000L});
     public static final BitSet FOLLOW_28_in_primary_expression778 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_IDENTIFIER_in_func_name801 = new BitSet(new long[]{0x0000000040000002L});
     public static final BitSet FOLLOW_30_in_func_name807 = new BitSet(new long[]{0x0000000000040000L});
     public static final BitSet FOLLOW_IDENTIFIER_in_func_name811 = new BitSet(new long[]{0x0000000040000002L});
     public static final BitSet FOLLOW_expression_in_param_list2846 = new BitSet(new long[]{0x00000001A0000002L});
     public static final BitSet FOLLOW_set_in_param_list2851 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_expression_in_param_list2863 = new BitSet(new long[]{0x0000000020000002L});
     public static final BitSet FOLLOW_29_in_param_list2872 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_expression_in_param_list2878 = new BitSet(new long[]{0x00000001A0000002L});
     public static final BitSet FOLLOW_set_in_param_list2883 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_expression_in_param_list2895 = new BitSet(new long[]{0x0000000020000002L});
     public static final BitSet FOLLOW_expression_in_param_list929 = new BitSet(new long[]{0x0000000020000002L});
     public static final BitSet FOLLOW_29_in_param_list936 = new BitSet(new long[]{0x00000000087D4000L});
     public static final BitSet FOLLOW_expression_in_param_list942 = new BitSet(new long[]{0x0000000020000002L});
     public static final BitSet FOLLOW_33_in_in_list970 = new BitSet(new long[]{0x00000004087D4000L});
     public static final BitSet FOLLOW_param_list2_in_in_list976 = new BitSet(new long[]{0x0000000400000000L});
     public static final BitSet FOLLOW_34_in_in_list978 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_variable_in_in_list989 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_func_name_in_in_list1002 = new BitSet(new long[]{0x0000000008000000L});
     public static final BitSet FOLLOW_27_in_in_list1004 = new BitSet(new long[]{0x00000000187D4000L});
     public static final BitSet FOLLOW_param_list_in_in_list1010 = new BitSet(new long[]{0x0000000010000000L});
     public static final BitSet FOLLOW_28_in_in_list1012 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_bool_lit_in_constant1047 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_number_in_constant1057 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_SQ_STRING_LIT_in_constant1065 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_bool_lit_in_unsigned_constant1092 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_INTEGER_in_unsigned_constant1102 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_real_lit_in_unsigned_constant1113 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_SQ_STRING_LIT_in_unsigned_constant1122 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_set_in_bool_lit1149 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_INTEGER_in_real_lit1188 = new BitSet(new long[]{0x0000000040000000L});
     public static final BitSet FOLLOW_30_in_real_lit1190 = new BitSet(new long[]{0x0000000000080000L});
     public static final BitSet FOLLOW_INTEGER_in_real_lit1194 = new BitSet(new long[]{0x0000000000000002L});
     public static final BitSet FOLLOW_IDENTIFIER_in_variable1221 = new BitSet(new long[]{0x0000000040000002L});
     public static final BitSet FOLLOW_30_in_variable1227 = new BitSet(new long[]{0x0000000000040000L});
     public static final BitSet FOLLOW_IDENTIFIER_in_variable1231 = new BitSet(new long[]{0x0000000040000002L});
     public static final BitSet FOLLOW_SUB_OPERATOR_in_number1263 = new BitSet(new long[]{0x0000000000080000L});
     public static final BitSet FOLLOW_INTEGER_in_number1269 = new BitSet(new long[]{0x0000000040000002L});
     public static final BitSet FOLLOW_30_in_number1272 = new BitSet(new long[]{0x0000000000080000L});
     public static final BitSet FOLLOW_INTEGER_in_number1276 = new BitSet(new long[]{0x0000000000000002L});

 }