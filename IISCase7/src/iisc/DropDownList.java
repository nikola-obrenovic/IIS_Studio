package iisc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;


class DropDownList extends JScrollPane {
    //private JScrollPane jScrollPane1 = new JScrollPane();
    private JList jList1 = new JList();
    private DefaultListModel listModel = new DefaultListModel();
    private JTextPane myExpression = null;
    private expPanel myExpression2 = null;
    private FunctionPanel myExpression3 = null;
    private String treeID = "";
    private String ID_2 = "";
    private String ID = "";
    private Connection con = null;
    private int typeOfExpression = 0;
    
    public void setTxtArea(expPanel a){
        myExpression3 = null;
        myExpression2 = a;
        myExpression = null;
    }

    public void setPaneArea(JTextPane a){
        myExpression3 = null;
        myExpression2 = null;
        myExpression = a;
    }  
    
    public void setTxtFun(FunctionPanel a){
        myExpression3 = a;
        myExpression2 = null;
        myExpression = null;
    }
    
    public DefaultListModel getModelData(){
        return (DefaultListModel)jList1.getModel();
    }

    public DropDownList(Connection connection,String etreeID,String eID_2,String eID) {
        try {
            con = connection;
            treeID = etreeID;
            ID_2 = eID_2;
            ID = eID;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DropDownList(int type,Connection connection,JTextPane txt,String etreeID,String eID_2,String eID) {
        try {
            typeOfExpression = type;
            con = connection;
            treeID = etreeID;
            ID_2 = eID_2;
            ID = eID;
            myExpression = txt;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public DropDownList(int type,Connection connection,expPanel txt,String etreeID,String eID_2,String eID) {
        try {
            typeOfExpression = type;
            con = connection;
            treeID = etreeID;
            ID_2 = eID_2;
            ID = eID;
            myExpression2 = txt;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    private void jbInit() throws Exception {
        jList1.setModel(listModel); 
        //this.setLayout(borderLayout1);
        this.setSize(new Dimension(293, 126));
        //jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.addFocusListener(new FocusAdapter() {
                    public void focusGained(FocusEvent e) {
                        this_focusGained(e);
                    }
                });
        jList1.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jList1_keyPressed(e);
                    }
                });
        jList1.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        jList1_mouseClicked(e);
                    }
                });
        //jScrollPane1.getViewport().add(jList1, null);
        this.getViewport().add(jList1, null);
    }
     
    private String dropDownString(){
        if(myExpression3 != null ){
            int g = myExpression3.jTextArea1.getCaretPosition() - 1;
            String tmpEx = "";
            if(g < 0)
                return tmpEx;
                
            while( g >= 0 && ( (myExpression3.jTextArea1.getText().charAt(g) >= '0' && myExpression3.jTextArea1.getText().charAt(g) <= '9') || 
                    ( myExpression3.jTextArea1.getText().charAt(g) >= 'A' && myExpression3.jTextArea1.getText().charAt(g) <= 'Z' ) ||
                     (myExpression3.jTextArea1.getText().charAt(g) >= 'a' && myExpression3.jTextArea1.getText().charAt(g) <= 'z') || 
                myExpression3.jTextArea1.getText().charAt(g) == '_' || myExpression3.jTextArea1.getText().charAt(g) == '.' )){
                tmpEx = myExpression3.jTextArea1.getText().charAt(g) + tmpEx;
                g--;
            }
            return tmpEx;  
        }    
        else if(myExpression != null && myExpression2 == null ){
            int g = myExpression.getCaretPosition() - 1;
            String tmpEx = "";
            if(g < 0)
                return tmpEx;
                
            while( g >= 0 && ( (myExpression.getText().charAt(g) >= '0' && myExpression.getText().charAt(g) <= '9') || 
                    ( myExpression.getText().charAt(g) >= 'A' && myExpression.getText().charAt(g) <= 'Z' ) ||
                     (myExpression.getText().charAt(g) >= 'a' && myExpression.getText().charAt(g) <= 'z') || 
                myExpression.getText().charAt(g) == '_' || myExpression.getText().charAt(g) == '.' )){
                tmpEx = myExpression.getText().charAt(g) + tmpEx;
                g--;
            }
            return tmpEx;
        }
        else if(myExpression == null && myExpression2 != null ){
            int g = myExpression2.jTextArea1.getCaretPosition() - 1;
            String tmpEx = "";
            if(g < 0)
                return tmpEx;
                
            while( g >= 0 && ( (myExpression2.jTextArea1.getText().charAt(g) >= '0' && myExpression2.jTextArea1.getText().charAt(g) <= '9') || 
                    ( myExpression2.jTextArea1.getText().charAt(g) >= 'A' && myExpression2.jTextArea1.getText().charAt(g) <= 'Z' ) ||
                     (myExpression2.jTextArea1.getText().charAt(g) >= 'a' && myExpression2.jTextArea1.getText().charAt(g) <= 'z') || 
                myExpression2.jTextArea1.getText().charAt(g) == '_' || myExpression2.jTextArea1.getText().charAt(g) == '.' )){
                tmpEx = myExpression2.jTextArea1.getText().charAt(g) + tmpEx;
                g--;
            }
            return tmpEx;        
        }
        return "";
    }    
    
    private void dropDownAtt(int pos){
        if(myExpression3 != null ){
            //int tmpCaret =  myExpression2.jTextArea1.getCaretPosition();
            int tmpCaret = myExpression3.jTextArea1.getCaretPosition();
            myExpression3.jTextArea1.setText(myExpression3.jTextArea1.getText().replaceAll("\r",""));
            myExpression3.jTextArea1.setCaretPosition(tmpCaret);            
            String tmpEx = dropDownString();
            String tmps = "";
            if(jList1.getSelectedValue().toString().split("\\|")[0].length() >= tmpEx.length()){
                String ggg[] = jList1.getSelectedValue().toString().split("\\|");
                int posS = 0;
                if(ggg.length == 3)
                    posS = 1;                
                tmps = jList1.getSelectedValue().toString().split("\\|")[posS].substring(tmpEx.length()).trim();
            }
            
            myExpression3.jTextArea1.setText( 
                 myExpression3.jTextArea1.getText().substring(0,tmpCaret) + tmps +  myExpression3.jTextArea1.getText().substring(tmpCaret ,  myExpression3.jTextArea1.getText().length() ));
            myExpression3.my.aaa[myExpression3.my.clicked].value = myExpression3.jTextArea1.getText();
            //myExpression3.my.txtExpression.setText(myExpression3.my.print(1).trim());
            //Attribute.checkSyntax2(myExpression2.my.txtExpression,false,con,myExpression2.my.typeOfEditor,String.valueOf(treeID),String.valueOf(ID_2),String.valueOf(ID),myExpression2.my.nodeNameSyntax,myExpression2.my.model2);                             

            this.setVisible(false);
             myExpression3.jTextArea1.requestFocus();
             myExpression3.jTextArea1.setText(myExpression3.jTextArea1.getText().replaceAll("\r",""));               
             myExpression3.jTextArea1.setCaretPosition(tmpCaret + tmps.length());        
        //************************
        /*            int g = myExpression2.jTextArea1.getCaretPosition();
            myExpression2.jTextArea1.setText(myExpression2.jTextArea1.getText() + " " + jList1.getSelectedValue().toString().split("\|")[0]);
            myExpression2.my.aaa[myExpression2.my.clicked].value += " " + jList1.getSelectedValue().toString().split("\|")[0];
            myExpression2.my.txtExpression.setText(myExpression2.my.print(1).trim());
            myExpression2.jTextArea1.setCaretPosition(g);
            Attribute.checkSyntax2(myExpression2.my.txtExpression,false,con,myExpression2.my.typeOfEditor,String.valueOf(treeID),String.valueOf(ID_2),String.valueOf(ID),myExpression2.my.nodeNameSyntax,myExpression2.my.model2);            
            */
        }        
        else if(myExpression != null && myExpression2 == null ){
            int tmpCaret = myExpression.getCaretPosition();
            myExpression.setText(myExpression.getText().replaceAll("\r",""));
            myExpression.setCaretPosition(tmpCaret);
            String tmpEx = dropDownString();
            String tmps = "";
            if(jList1.getSelectedValue().toString().split("\\|")[0].length() >= tmpEx.length()){
                String ggg[] = jList1.getSelectedValue().toString().split("\\|");
                int posS = 0;
                if(ggg.length == 3)
                    posS = 1;
                tmps = jList1.getSelectedValue().toString().split("\\|")[posS].substring(tmpEx.length()).trim();
            }
    
            myExpression.setText( 
                myExpression.getText().substring(0,tmpCaret) + tmps + myExpression.getText().substring(tmpCaret , myExpression.getText().length() )
            );
            this.setVisible(false);
            myExpression.requestFocus();
            myExpression.setText(myExpression.getText().replaceAll("\r",""));               
            myExpression.setCaretPosition(tmpCaret + tmps.length());
        }
        else if(myExpression == null && myExpression2 != null ){
            //int tmpCaret =  myExpression2.jTextArea1.getCaretPosition();
            int tmpCaret = myExpression2.jTextArea1.getCaretPosition();
            myExpression2.jTextArea1.setText(myExpression2.jTextArea1.getText().replaceAll("\r",""));
            myExpression2.jTextArea1.setCaretPosition(tmpCaret);            
            String tmpEx = dropDownString();
            String tmps = "";
            if(jList1.getSelectedValue().toString().split("\\|")[0].length() >= tmpEx.length()){
                String ggg[] = jList1.getSelectedValue().toString().split("\\|");
                int posS = 0;
                if(ggg.length == 3)
                    posS = 1;                
                tmps = jList1.getSelectedValue().toString().split("\\|")[posS].substring(tmpEx.length()).trim();
            }
            
            myExpression2.jTextArea1.setText( 
                 myExpression2.jTextArea1.getText().substring(0,tmpCaret) + tmps +  myExpression2.jTextArea1.getText().substring(tmpCaret ,  myExpression2.jTextArea1.getText().length() ));
            myExpression2.my.aaa[myExpression2.my.clicked].value = myExpression2.jTextArea1.getText();
            myExpression2.my.txtExpression.setText(myExpression2.my.print(1).trim());
            Attribute.checkSyntax2(myExpression2.my.txtExpression,false,con,myExpression2.my.typeOfEditor,String.valueOf(treeID),String.valueOf(ID_2),String.valueOf(ID),myExpression2.my.nodeNameSyntax,myExpression2.my.model2);                             

            this.setVisible(false);
             myExpression2.jTextArea1.requestFocus();
             myExpression2.jTextArea1.setText(myExpression2.jTextArea1.getText().replaceAll("\r",""));               
             myExpression2.jTextArea1.setCaretPosition(tmpCaret + tmps.length());        
        //************************
/*            int g = myExpression2.jTextArea1.getCaretPosition();
            myExpression2.jTextArea1.setText(myExpression2.jTextArea1.getText() + " " + jList1.getSelectedValue().toString().split("\|")[0]);
            myExpression2.my.aaa[myExpression2.my.clicked].value += " " + jList1.getSelectedValue().toString().split("\|")[0];
            myExpression2.my.txtExpression.setText(myExpression2.my.print(1).trim());
            myExpression2.jTextArea1.setCaretPosition(g);
            Attribute.checkSyntax2(myExpression2.my.txtExpression,false,con,myExpression2.my.typeOfEditor,String.valueOf(treeID),String.valueOf(ID_2),String.valueOf(ID),myExpression2.my.nodeNameSyntax,myExpression2.my.model2);            
            */
        }
    }    
    
    public void setData(String cond){
        try{
            JDBCQuery query=new JDBCQuery(con);
            JDBCQuery query2=new JDBCQuery(con);
            ResultSet rs,rs1,rs2,rs3;
            //String atts = "";
            //String idp = String.valueOf(TOB);
            listModel.removeAllElements();
            
            String tmpEx = cond;
            if( cond == null)
                tmpEx = dropDownString();
                
            if(typeOfExpression == 0){
                if("value"/*.toUpperCase()*/.startsWith(tmpEx/*.toUpperCase()*/))
                    listModel.addElement("value");
                if("this"/*.toUpperCase()*/.startsWith(tmpEx/*.toUpperCase()*/))
                    listModel.addElement("this");

                    //JDBCQuery query=new JDBCQuery(con);
                    //ResultSet rs1,rs2,; 

                    String dom_ids = "-1";
                    String dom_type = "";               
                
                    //System.out.println("select * from IISC_Domain where PR_id="+ tree.ID +" and Dom_id= "+ nodeIDSyntax);
                    rs1=query.select("select * from IISC_Domain where PR_id="+ treeID +" and Dom_id= "+ ID );
                    //boolean ok = false;
                    
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
                        " from IISC_Dom_Att,IISC_ATTRIBUTE,IISC_DOMAIN " +
                        " where IISC_Dom_Att.PR_id = IISC_ATTRIBUTE.PR_id and " +
                        " IISC_Dom_Att.Att_id = IISC_ATTRIBUTE.Att_id and " +
                        " IISC_ATTRIBUTE.Dom_id = IISC_DOMAIN.Dom_id and IISC_DOMAIN.PR_id = " + treeID + " and " + 
                        " IISC_Dom_Att.PR_id="+ treeID +" and IISC_Dom_Att.Dom_id = "+ ID);
                        while(rs3.next()){
                            String tmps = "value." + rs3.getString("Att_mnem").trim()/*.toUpperCase()*/ + " | " + rs3.getString("Dom_mnem");
                            //dom_atts += tmps + ":::"; 
                            if((tmps/*.toUpperCase()*/).startsWith(tmpEx/*.toUpperCase()*/))
                                listModel.addElement(tmps);
                        }
                        rs3.close();
                    }
                    rs1.close();                    
                
            }
            else if(typeOfExpression == 2){
                String idp = ID;
                while(true){
                    rs = query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE " +
                                        " where PR_id="+ treeID +" and  TF_ID="+ ID_2 +" and TOB_id = " + idp);
                                        
                    String nextIDP = null;
                    
                    if( rs.next() )
                        nextIDP = rs.getString("TOB_superord");
                    
                    rs = query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_ATT_TOB,IISC_ATTRIBUTE,IISC_DOMAIN " +
                                    " where IISC_COMPONENT_TYPE_OBJECT_TYPE.tob_id = IISC_ATT_TOB.tob_id AND " +
                                    " IISC_COMPONENT_TYPE_OBJECT_TYPE.tf_id = IISC_ATT_TOB.tf_id AND " +
                                    " IISC_COMPONENT_TYPE_OBJECT_TYPE.PR_id = IISC_ATT_TOB.PR_id AND " +
                                    " IISC_ATT_TOB.ATT_id = IISC_ATTRIBUTE.ATT_id AND " +
                                    " IISC_ATT_TOB.PR_id = IISC_ATTRIBUTE.PR_id AND " +
                                    " IISC_COMPONENT_TYPE_OBJECT_TYPE.PR_id = "+ treeID +" and " +
                                    " IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_ID = "+ ID_2 +" and " +
                                    " IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id = " + idp + " and " +
                                    " IISC_DOMAIN.Dom_id = IISC_ATTRIBUTE.Dom_id and" +
                                    " IISC_Domain.Pr_id = " + treeID + " and " + 
                                    " Att_mnem like '" + tmpEx + "%' ");
    
                    while( rs.next() ){
                        String tmps = rs.getString("Tob_mnem") + " | " + rs.getString("Att_mnem") + " | " + rs.getString("Dom_mnem") ;
                        if((tmps/*.toUpperCase()*/).startsWith(tmpEx/*.toUpperCase()*/))
                            listModel.addElement(tmps);
                    }
                    
                    if(nextIDP == null)
                        break;

                    idp = nextIDP;                    
                }
            }
            else if(typeOfExpression == 1){
                rs = query.select("select * " +
                                " FROM IISC_ATTRIBUTE " +
                                " where IISC_ATTRIBUTE.PR_id = "+ treeID +" and " +
                                " IISC_ATTRIBUTE.Att_id = " + ID );
                                //+ " and " + 
                                //" Att_mnem like '" + tmpEx + "%' ");
                
                if( rs.next() ){  
                    String tmpS = rs.getString("Att_mnem");
                    if(tmpS.startsWith(tmpEx))
                        listModel.addElement(tmpS);
                }
            }
            
            
            
            rs = query.select("SELECT * , IISC_FUNCTION.FUN_ID as FID" + 
            "   FROM ((IISC_FUNCTION LEFT JOIN IISC_PACK_FUN ON IISC_FUNCTION.Fun_id = IISC_PACK_FUN.Fun_id) " +
            "   LEFT JOIN IISC_PACKAGE ON IISC_PACK_FUN.Pack_id = IISC_PACKAGE.Pack_id) " +
            "   LEFT JOIN IISC_DOMAIN ON IISC_FUNCTION.Dom_id = IISC_DOMAIN.Dom_id " + 
            "   WHERE IISC_FUNCTION.PR_ID = " + treeID + " AND IISC_DOMAIN.PR_ID = " + treeID +
            "   ORDER BY IISC_PACKAGE.Pack_name,IISC_FUNCTION.Fun_name " );
            
            while( rs.next() ){
                String packName = rs.getString("Pack_name");
                String domName = rs.getString("Dom_mnem");
                String funName = rs.getString("Fun_name");
                String funId = rs.getString("FID");
                if(packName == null) packName = "";
                else packName += ".";
                if(domName == null) domName = "";
                
                rs2 = query2.select("   SELECT * " +
                                    "   FROM IISC_FUN_PARAM,IISC_DOMAIN " +
                                    "   WHERE   IISC_FUN_PARAM.Dom_id = IISC_DOMAIN.Dom_id AND " +
                                    "   IISC_FUN_PARAM.PR_ID = " + treeID + " AND " +
                                    "   IISC_FUN_PARAM.Fun_id = " + funId + " AND " +
                                    "   IISC_DOMAIN.PR_ID = " + treeID);
                String params = " ( ";
                while(rs2.next())
                    params += rs2.getString("Dom_mnem") + " ,";
                params = params.substring(0,params.length() - 1);
                params += ") ";
                
                if((packName + funName).startsWith(tmpEx))
                    listModel.addElement(packName + funName + params + " | " + domName);
                rs2.close();
            }

            rs.close();
            //if(listModel.getSize() == 0)
            //    listModel.addElement(" | No Available Elements ");
            
            jList1.setSelectedIndex(0);
        }
        catch(SQLException ex){
            System.out.println("exDDLIST:" + ex);
        }        
    }

    private void jList1_keyPressed(KeyEvent e) {
        //System.out.println("yyy:" + e.getKeyCode());
        if( e.getKeyCode() == 27){
            this.setVisible(false);
            if(myExpression != null && myExpression2 == null ){
                int tmpCaret = myExpression.getCaretPosition();
                myExpression.requestFocus();
                myExpression.setCaretPosition(tmpCaret);
            }
            else if(myExpression == null && myExpression2 != null ){
                int tmpCaret = myExpression2.jTextArea1.getCaretPosition();
                myExpression2.jTextArea1.requestFocus();
                myExpression2.jTextArea1.setCaretPosition(tmpCaret);            
            }
        }  
        else if( e.getKeyCode() == 10 && jList1.getSelectedIndex() != -1){
            dropDownAtt(jList1.getSelectedIndex());
            this.setVisible(false);
        }    
    }

    private void jList1_mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2 && jList1.getSelectedIndex() != -1){
            dropDownAtt(jList1.getSelectedIndex());
            this.setVisible(false);
        }    
    }


    public void setFunData(String cond){
        try{   
            JDBCQuery query=new JDBCQuery(con);
            JDBCQuery query2=new JDBCQuery(con);
            ResultSet rs,rs1,rs2,rs3;
            //String atts = "";
            //String idp = String.valueOf(TOB);
            listModel.removeAllElements();
            
            String tmpEx = cond;
            if( cond == null)
                tmpEx = dropDownString();
                
            rs = query.select("select * from IISC_Function,IISC_DOMAIN where IISC_Function.Dom_id = IISC_DOMAIN.Dom_id and IISC_DOMAIN.PR_ID = " + treeID + " and Fun_name like '" + tmpEx + "%'  and IISC_FUNCTION.PR_id="+treeID);
            
            //i=-1;
            
            while( rs.next() )
            {
                  String funnamecmb = rs.getString("Fun_name");
                  int Fun_id = rs.getInt("Fun_id");//getString("Fun_id");
                  String domName = rs.getString("Dom_mnem");

                  rs1=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.Fun_id = "+Fun_id+" order by F.Param_seq");
                    
                  funnamecmb = funnamecmb + " ( ";
                  int itemp = 1;
                    
                  while(rs1.next())
                  {
                      String domtemp= rs1.getString("Dom_mnem");
                      funnamecmb = funnamecmb + domtemp + " , ";  
                      itemp = 2;
                  }
                    
                  funnamecmb = funnamecmb.substring(0,funnamecmb.length()-itemp) + " )";
                  
                    listModel.addElement(funnamecmb + " | " + domName );

                  query2.Close();
            }                 
                
/*
            rs = query.select("SELECT * , IISC_FUNCTION.FUN_ID as FID" + 
            "   FROM ((IISC_FUNCTION LEFT JOIN IISC_PACK_FUN ON IISC_FUNCTION.Fun_id = IISC_PACK_FUN.Fun_id) " +
            "   LEFT JOIN IISC_PACKAGE ON IISC_PACK_FUN.Pack_id = IISC_PACKAGE.Pack_id) " +
            "   LEFT JOIN IISC_DOMAIN ON IISC_FUNCTION.Dom_id = IISC_DOMAIN.Dom_id " + 
            "   WHERE IISC_FUNCTION.PR_ID = " + treeID + " AND IISC_DOMAIN.PR_ID = " + treeID +
            "   ORDER BY IISC_PACKAGE.Pack_name,IISC_FUNCTION.Fun_name " );

            while( rs.next() ){
                String packName = rs.getString("Pack_name");
                String domName = rs.getString("Dom_mnem");
                String funName = rs.getString("Fun_name");
                String funId = rs.getString("FID");
                int pack_type = rs.getInt("pack_type");
                if(packName == null) packName = "";
                else packName += ".";
                if(domName == null) domName = "";
                
                rs2 = query2.select("   SELECT * " +
                                    "   FROM IISC_FUN_PARAM,IISC_DOMAIN " +
                                    "   WHERE   IISC_FUN_PARAM.Dom_id = IISC_DOMAIN.Dom_id AND " +
                                    "   IISC_FUN_PARAM.PR_ID = " + treeID + " AND " +
                                    "   IISC_FUN_PARAM.Fun_id = " + funId + " AND " +
                                    "   IISC_DOMAIN.PR_ID = " + treeID);
                String params = " ( ";
                while(rs2.next())
                    params += rs2.getString("Dom_mnem") + " ,";
                params = params.substring(0,params.length() - 1);
                params += ") ";
                
                if((packName + funName).startsWith(tmpEx)){
                    String type = "";
                    if(pack_type == 1)
                        type = "DB Server";
                    else if(pack_type == 2)
                        type = "Application Server";
                    else if(pack_type == 3)
                        type = "Client";
                    listModel.addElement(type + " | " + packName + funName + params + " | " + domName );
                }
                rs2.close();
            }
*/
            rs.close();
            //if(listModel.getSize() == 0)
            //    listModel.addElement(" | No Available Elements ");
            
            jList1.setSelectedIndex(0);
        }
        catch(SQLException ex){
            System.out.println("exDDLIST:" + ex);
        }        
    }

    public void setDataDomain(JComboBox cmb){
        try{
                JDBCQuery query=new JDBCQuery(con);
                ResultSet rs1; 
                
                String tmpEx = dropDownString();
                
                String dom_ids = "-1";
                String dom_type = "";               
                String Dom_mnem = "";
            
                //System.out.println("select * from IISC_Domain where PR_id="+ tree.ID +" and Dom_id= "+ nodeIDSyntax);
                rs1=query.select("select * from IISC_Domain where PR_id="+ treeID +" and Dom_mnem LIKE '" + tmpEx + "%' " );
                //boolean ok = false;
                
                cmb.removeAllItems();
                cmb.addItem("");
                listModel.removeAllElements();
                while(rs1.next()){
                    dom_type = rs1.getString("Dom_type");
                    dom_ids = rs1.getString("Dom_id");     
                    Dom_mnem = rs1.getString("Dom_mnem");     
                    listModel.addElement( Dom_mnem );
                    cmb.addItem(Dom_mnem);
                }
                rs1.close();
        }
        catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    public void setDataFunEditor (node[] a,int poz,JComboBox cmb) {
        listModel.removeAllElements();
        if(cmb != null){
            cmb.removeAllItems();
            cmb.addItem("");
        }
        int level = 0;
        boolean ok = true;
        
        String tmpEx = dropDownString();
        
        int stack[] = new int[a.length];
        int stackno = 0;
        String dec = "";
        int i = 0;
        for(i=0; i< poz; i++){
            if( a[i].type.equals("B") ){
                if(a[i].group != -1){
                    stack[stackno++] = i;
                    /*if(a[i].group == 8){
                        for(int j=0; j< stackno; j++){
                            dec += stack[j] + ":";
                        }
                        dec += i + "\n";                        
                    }*/
                }
                else
                    stackno--;
            }
            else{
                if( a[i].group == -5){
                    for(int j=0; j< stackno; j++){
                        dec += stack[j] + ":";
                    }
                    dec += i + "\n";
                }
                else if( a[i].group == -88){
                    for(int j=0; j< stackno - 1; j++){
                        dec += stack[j] + ":";
                    }
                    dec += i + "\n";
                }
                else if( a[i].group == -8){
                    for(int j=0; j< stackno; j++){
                        dec += stack[j] + ":";
                    }
                    dec += i + "\n";
                }                
            }
            
        }
        String last = "";
        for(int j=0; j< stackno; j++){
            last += stack[j] + ":";
        }
        last += i + "\n";
        //System.out.println(dec);    
        //System.out.println(last);  
        
        for(int j= last.split(":").length - 2; j>=0; j-- ){
            for(int k=0;k < dec.split("\n").length; k++){

                //System.out.println("J:" + j)    ;
                //System.out.println("K:" + k)    ;
                //System.out.println(" :" + dec.split("\n")[k])    ;
                //System.out.println(" :" + dec.split("\n")[k].split(":").length)    ;
                //System.out.println(" :" + (dec.split("\n")[k].split(":").length + 2))    ;

                if(j + 1>= (dec.split("\n")[k].split(":").length ) )
                    continue;

                //System.out.println(a[Integer.parseInt(dec.split("\n")[k].split(":")[j])].group);
                //System.out.println(a[Integer.parseInt(last.split(":")[j])].group);
                //System.out.println(a[Integer.parseInt(dec.split("\n")[k].split(":")[j+1]) ].group);
                if(a[Integer.parseInt(dec.split("\n")[k].split(":")[j])].group == a[Integer.parseInt(last.split(":")[j])].group
                && (a[Integer.parseInt(dec.split("\n")[k].split(":")[j+1]) ].group == 5  || a[Integer.parseInt(dec.split("\n")[k].split(":")[j+1]) ].group == 88))
                {
                    String tmp = "";
                    if(a[Integer.parseInt(dec.split("\n")[k].split(":")[j+2]) ].group == -88 ){
                        tmp += "( ";
                        //System.out.println(Integer.parseInt(dec.split("\n")[k].split(":")[j+2]));
                        //System.out.println((Integer.parseInt(dec.split("\n")[k].split(":")[j+2]) + 2));
                        int t = Integer.parseInt(dec.split("\n")[k].split(":")[j+2]) + 2;
                        //System.out.println("T:" + t);
                        while(a[t].group == -8 ){
                            tmp +=  a[t].value.replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("  "," ").split("=")[0].split(" ")[0] + " ,";
                            t++;
                        }
                        tmp = tmp.substring(0,tmp.length() - 1) + ")";
                    }
                    //System.out.println(a[Integer.parseInt(dec.split("\n")[k].split(":")[j+2]) ].value);
                     String tmpa = "";
                    if( a[Integer.parseInt(dec.split("\n")[k].split(":")[j+2]) ].value.replaceAll("  "," ").split("=",2)[0].split(" ").length == 2)
                        tmpa = a[Integer.parseInt(dec.split("\n")[k].split(":")[j+2]) ].value.replaceAll("  "," ").split("=",2)[0].split(" ")[1] + " " + tmp + " | " + a[Integer.parseInt(dec.split("\n")[k].split(":")[j+2]) ].value.replaceAll("  "," ").split("=",2)[0].split(" ")[0];
                    else
                        continue;
                    if(tmpa.startsWith(tmpEx)){
                        listModel.addElement( tmpa );
                        if(cmb != null)
                            cmb.addItem( tmpa );
                    }
                }
            }
        }
        try{   
            JDBCQuery query=new JDBCQuery(con);
            JDBCQuery query2=new JDBCQuery(con);
            ResultSet rs,rs1,rs2,rs3;
            rs = query.select("SELECT * , IISC_FUNCTION.FUN_ID as FID" + 
            "   FROM ((IISC_FUNCTION LEFT JOIN IISC_PACK_FUN ON IISC_FUNCTION.Fun_id = IISC_PACK_FUN.Fun_id) " +
            "   LEFT JOIN IISC_PACKAGE ON IISC_PACK_FUN.Pack_id = IISC_PACKAGE.Pack_id) " +
            "   LEFT JOIN IISC_DOMAIN ON IISC_FUNCTION.Dom_id = IISC_DOMAIN.Dom_id " + 
            "   WHERE IISC_FUNCTION.PR_ID = " + treeID + " AND IISC_DOMAIN.PR_ID = " + treeID +
            "   ORDER BY IISC_PACKAGE.Pack_name,IISC_FUNCTION.Fun_name " );
            
            while( rs.next() ){
                String packName = rs.getString("Pack_name");
                String domName = rs.getString("Dom_mnem");
                String funName = rs.getString("Fun_name");
                String funId = rs.getString("FID");
                if(packName == null) packName = "";
                else packName += ".";
                if(domName == null) domName = "";
                
                rs2 = query2.select("   SELECT * " +
                                    "   FROM IISC_FUN_PARAM,IISC_DOMAIN " +
                                    "   WHERE   IISC_FUN_PARAM.Dom_id = IISC_DOMAIN.Dom_id AND " +
                                    "   IISC_FUN_PARAM.PR_ID = " + treeID + " AND " +
                                    "   IISC_FUN_PARAM.Fun_id = " + funId + " AND " +
                                    "   IISC_DOMAIN.PR_ID = " + treeID);
                String params = " ( ";
                while(rs2.next())
                    params += rs2.getString("Dom_mnem") + " ,";
                params = params.substring(0,params.length() - 1);
                params += ") ";
                
                if((packName + funName).startsWith(tmpEx)){
                    listModel.addElement(packName + funName + params + " | " + domName);
                    if(cmb != null)
                        cmb.addItem(packName + funName + params + " | " + domName);
                }
                rs2.close();
            }

            rs.close();
            //if(listModel.getSize() == 0)
            //    listModel.addElement(" | No Available Elements ");
        }
        catch(SQLException ex){
            System.out.println("exDDLIST:" + ex);
        }   
        //listModel.addElement("aaa2 | bbb2");
    }

    private void this_focusGained(FocusEvent e) {
        jList1.requestFocus();
    }
}
