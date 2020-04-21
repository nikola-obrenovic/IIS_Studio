package iisc;

import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class CheckConstraintExpressionOpen extends JDialog {
    private JComboBox cbGroup = new JComboBox();
    private JList listOfItems = new JList();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTextPane txtExpression = new JTextPane();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JButton btnOK = new JButton();
    private JButton btnCancel = new JButton();
    
    //private String ID = "";
    //private String ID2 = "";
    //private String treeID = "";
    //private Connection con = null;
    private int typeOfEditor = 0;
    //private String nodeName = "";
    public DefaultListModel model2 = new DefaultListModel();
    //private Hashtable numbers = new Hashtable();
    private CheckConstraintExpressionEditor cc =null;

    public CheckConstraintExpressionOpen() {
        this(null, "", false);
    }

    public CheckConstraintExpressionOpen(Frame parent, String title, 
                                         boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public CheckConstraintExpressionOpen(Frame parent, String title, boolean modal,
            CheckConstraintExpressionEditor ccee) {
        super(parent, title, modal);
        try {
            cc =ccee;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    private void jbInit() throws Exception {
        listOfItems.setModel(model2);
        this.setSize(new Dimension(474, 318));
        this.getContentPane().setLayout( null );
        this.setTitle("Use Existing Expression");
        cbGroup.setBounds(new Rectangle(125, 25, 335, 20));
        cbGroup.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cbGroup_actionPerformed(e);
                    }
                });
        listOfItems.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        listOfItems_mouseClicked(e);
                    }
                });
        listOfItems.addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        listOfItems_valueChanged(e);
                    }
                });
        jScrollPane1.setBounds(new Rectangle(125, 65, 335, 85));
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setBounds(new Rectangle(125, 160, 335, 95));
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        txtExpression.setEditable(false);
        jLabel1.setText("Select Group:");
        jLabel1.setBounds(new Rectangle(10, 25, 95, 20));
        jLabel2.setText("Use Expression from:");
        jLabel2.setBounds(new Rectangle(10, 60, 145, 20));
        jLabel3.setText("Expression:");
        jLabel3.setBounds(new Rectangle(10, 160, 100, 20));
        btnOK.setText("OK");
        btnOK.setBounds(new Rectangle(310, 260, 73, 22));
        btnOK.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnOK_actionPerformed(e);
                    }
                });
        btnCancel.setText("Cancel");
        btnCancel.setBounds(new Rectangle(390, 260, 73, 22));
        btnCancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnCancel_actionPerformed(e);
                    }
                });
        this.getContentPane().add(btnCancel, null);
        this.getContentPane().add(btnOK, null);
        this.getContentPane().add(jLabel3, null);
        this.getContentPane().add(jLabel2, null);
        this.getContentPane().add(jLabel1, null);
        jScrollPane2.getViewport().add(txtExpression, null);
        this.getContentPane().add(jScrollPane2, null);
        jScrollPane1.getViewport().add(listOfItems, null);
        this.getContentPane().add(jScrollPane1, null);
        this.getContentPane().add(cbGroup, null);
        
        cbGroup.addItem("Domain");
        cbGroup.addItem("Attribute");
        cbGroup.addItem("Form Type");
        
        setData();
        
    }
    
    
    private void setData(){
        try{   
            JDBCQuery query=new JDBCQuery(cc.con);
            //JDBCQuery query2=new JDBCQuery(con);
            ResultSet rs1;
            //String atts = "";
            //String idp = String.valueOf(TOB);
            
            model2.removeAllElements();
                
            if(typeOfEditor == 0){
                //cbGroup.addItem("value");
                //cbGroup.addItem("this");

                    //JDBCQuery query=new JDBCQuery(con);
                    //ResultSet rs1,rs2,; 
                     rs1=query.select("     SELECT * " +
                                        "   FROM IISC_Domain " +
                                        "   WHERE PR_id="+ cc.treeID +" AND TRIM(Dom_reg_exp_str) <> '' " +
                                        "   ORDER BY Dom_mnem ");
                     
                     
                     while(rs1.next()){   
                        String tmps = rs1.getString("Dom_mnem");
                        String tmpID = rs1.getString("Dom_ID");
                        model2.addElement(tmps);
                        //numbers.put(tmpID,tmps);
                     }
                    rs1.close();
/*
                    String dom_ids = "-1";
                    String dom_type = "";               
                
                    //System.out.println("select * from IISC_Domain where PR_id="+ tree.ID +" and Dom_id= "+ nodeIDSyntax);
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
                        " from IISC_Dom_Att,IISC_ATTRIBUTE,IISC_DOMAIN " +
                        " where IISC_Dom_Att.PR_id = IISC_ATTRIBUTE.PR_id and " +
                        " IISC_Dom_Att.Att_id = IISC_ATTRIBUTE.Att_id and " +
                        " IISC_ATTRIBUTE.Dom_id = IISC_DOMAIN.Dom_id and IISC_DOMAIN.PR_id = " + treeID + " and " + 
                        " IISC_Dom_Att.PR_id="+ treeID +" and IISC_Dom_Att.Dom_id = "+ id_comp);
                        while(rs3.next()){
                            String tmps = rs3.getString("Att_mnem").trim() + " : " + rs3.getString("Dom_mnem");
                            //dom_atts += tmps + ":::";
                             cbGroup.addItem(tmps);
                        }
                        rs3.close();
                    }
                    rs1.close();   
                    */
            }
            else if(typeOfEditor == 2){
                rs1=query.select("  select * " +
                                "   from IISC_COMPONENT_TYPE_OBJECT_TYPE " +
                                "   where PR_id = " + cc.treeID +
                                "   AND TRIM(Tob_check) <> '' " +
                                "   ORDER BY TOB_mnem" /*+" and  TF_ID="+ ID2 +" and TOB_id = " + ID*/);
                
                
                while(rs1.next()){   
                   String tmps = rs1.getString("Tob_mnem");
                   String tmpID = rs1.getString("Tob_id");
                   model2.addElement(tmps);
                   //numbers.put(tmpID,tmps);
                }
                rs1.close();             
            /*
                String idp = String.valueOf(id_comp);
                while(true){
                    rs = query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE " +
                                        " where PR_id="+ treeID +" and  TF_ID="+ ID2 +" and TOB_id = " + idp);
                                        
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
                                    " IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_ID = "+ ID2 +" and " +
                                    " IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id = " + idp );
        
                    while( rs.next() ){
                        String tmps = rs.getString("Att_mnem") + " : " + rs.getString("Tob_mnem") + " ";
                        cbGroup.addItem(tmps);
                    }
                    
                    if(nextIDP == null)
                        break;

                    idp = nextIDP;                    
                }*/
            }
            else if(typeOfEditor == 1){
                rs1=query.select("  SELECT * " +
                                "   FROM IISC_Attribute " +
                                "   WHERE PR_id="+ cc.treeID +" AND TRIM(Att_expr) <> '' " +
                                "   ORDER BY Att_mnem ");
                
                
                while(rs1.next()){   
                   String tmps = rs1.getString("Att_mnem");
                   String tmpID = rs1.getString("Att_id");
                   model2.addElement(tmps);
                   //numbers.put(tmpID,tmps);
                }
                rs1.close();            
            /*
                rs = query.select("select * " +
                                " FROM IISC_ATTRIBUTE " +
                                " where IISC_ATTRIBUTE.PR_id = "+ treeID +" and " +
                                " IISC_ATTRIBUTE.Att_id = " + id_comp );
                                //+ " and " + 
                                //" Att_mnem like '" + tmpEx + "%' ");
                
                if( rs.next() ){  
                    String tmpS = rs.getString("Att_mnem");
                    cbGroup.addItem(tmpS);
                }
                */
            }
            
        }
        catch(SQLException ex){
            System.out.println("exDDLIST:" + ex);
        }         
    }

    private void cbGroup_actionPerformed(ActionEvent e) {
        typeOfEditor = cbGroup.getSelectedIndex();
        setData();
    }

    private void clicked(){
        if(listOfItems.getSelectedIndex() != -1){
            try{
                JDBCQuery query=new JDBCQuery(cc.con);
                //JDBCQuery query2=new JDBCQuery(con);
                ResultSet rs1;
                if(typeOfEditor == 0){
                    rs1=query.select("     SELECT * " +
                                       "   FROM IISC_Domain " +
                                       "   WHERE PR_id="+ cc.treeID +" AND TRIM(Dom_mnem) = TRIM('" + listOfItems.getSelectedValue().toString() + "') ");
                    
                    
                    if(rs1.next()){   
                       String tmps = rs1.getString("Dom_reg_exp_str");
                       txtExpression.setText(tmps);
                    }
                    rs1.close();                    
                }
                else if(typeOfEditor == 1){
                    rs1=query.select("  SELECT * " +
                                "   FROM IISC_Attribute " +
                                "   WHERE PR_id="+ cc.treeID +" AND TRIM(Att_mnem) = TRIM('" + listOfItems.getSelectedValue().toString() + "')  ");
                    
                    
                    if(rs1.next()){   
                       String tmps = rs1.getString("Att_expr");
                       txtExpression.setText(tmps);
                    }
                    rs1.close();                         
                }
                else if(typeOfEditor == 2){
                    rs1=query.select("  select * " +
                                "   from IISC_COMPONENT_TYPE_OBJECT_TYPE " +
                                "   where PR_id = " + cc.treeID +" and TRIM(Tob_mnem) = TRIM('" + listOfItems.getSelectedValue().toString() + "') ");
                    
                    
                    if(rs1.next()){   
                       String tmps = rs1.getString("Tob_check");
                       txtExpression.setText(tmps);
                    }
                    rs1.close();                 
                }
                Attribute.checkSyntax2(txtExpression,false,cc.con,cc.typeOfEditor,cc.treeID,cc.ID2,String.valueOf(cc.id_comp),"",null);
            }
            catch(SQLException ex){
                System.out.println("CCEopen ex:" + ex);
            }
        }        
    }

    private void listOfItems_mouseClicked(MouseEvent e) {
        clicked();
    }

    private void btnCancel_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    private void btnOK_actionPerformed(ActionEvent e) {
        cc.txtExpression.setText(txtExpression.getText());
        CCEValue tmpCCVE = Attribute.checkSyntax2(cc.txtExpression,true,cc.con,cc.typeOfEditor,cc.treeID,cc.ID2,String.valueOf(cc.id_comp),cc.nodeNameSyntax,cc.model2);
        if(tmpCCVE.check){
            cc.drawSyntax(tmpCCVE);
        }
        
        this.dispose();
    }

    private void listOfItems_valueChanged(ListSelectionEvent e) {
        //System.out.println("listOfItems_valueChanged");
         clicked();
    }

}
