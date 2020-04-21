package iisc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableColumn;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

public class FuncSelection extends JDialog {
    private JButton buttonOK = new JButton();
    private JButton buttonCancel = new JButton();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JList jList1 = new JList();
    private DefaultListModel model = new DefaultListModel();
    private DefaultListModel modelDef = new DefaultListModel();
    private Object tmpMy;
    private boolean newFunc = true;
    Connection con;
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTable tableParam = new JTable();
    private DefaultTableModel tableModel = new DefaultTableModel();
    String ids = "";
    private JLabel funcLabel = new JLabel();
    private JLabel argLabel = new JLabel();
    private JTextField jTextField1 = new JTextField();
    private JLabel filterLabel = new JLabel();
    private JScrollPane ScrollPaneErr = new JScrollPane();
    private JList errorList = new JList();
    private DefaultListModel model2 = new DefaultListModel();
    private JLabel jLabel1 = new JLabel();
    private JButton jButton1 = new JButton();
    private String treeId = "";
    private String ID_2 = "";
    private String ID = "";
    private int typeOfCheck = -1;
    private String nodeName = "";
    private JTextPane txtExpression = new JTextPane();

    public FuncSelection(CheckConstraintExpressionEditor a) {
        this(null, "", false);
        tmpMy = a;
        
    }

    public FuncSelection(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public FuncSelection(FunctionEditor parent, String title, boolean modal, 
            /*1CheckConstraintExpressionEditor a,*/boolean newe,Connection conne,
                String etIDs,String eID2s,String eIDs,int etype,String name) {
        super((FunctionEditor)parent, title, modal);
        try {
            con = conne;
            tmpMy = parent;
            newFunc = newe;
            treeId = etIDs;
            ID_2 = eID2s;
            ID= eIDs;
            typeOfCheck = etype;
            nodeName = name;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }       
    
    public FuncSelection(CheckConstraintExpressionEditor parent, String title, boolean modal, 
            /*1CheckConstraintExpressionEditor a,*/boolean newe,Connection conne,
                String etIDs,String eID2s,String eIDs,int etype,String name) {
        super(parent, title, modal);
        try {
            con = conne;
            tmpMy = parent;
            newFunc = newe;
            treeId = etIDs;
            ID_2 = eID2s;
            ID= eIDs;
            typeOfCheck = etype;
            nodeName = name;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    private void jbInit() throws Exception {
        jList1 = new JList(model);  
        errorList.setModel(model2);
        errorList.setCellRenderer(new CheckConstraintErrorListRender());
        tableParam.setModel(tableModel);
        funcLabel.setText("Functions:");
        funcLabel.setBounds(new Rectangle(15, 50, 80, 15));
        argLabel.setText("Arguments");
        argLabel.setBounds(new Rectangle(10, 210, 80, 15));
        jTextField1.setBounds(new Rectangle(90, 15, 340, 20));
        jTextField1.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        jTextField1_keyReleased(e);
                    }
                });
        filterLabel.setText("Search:");
        filterLabel.setBounds(new Rectangle(15, 15, 70, 20));
        tableModel.addColumn("Arg No");
        tableModel.addColumn("Name");
        tableModel.addColumn("Domain");
        tableModel.addColumn("Expression");

        tableParam.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableParam.getTableHeader().setReorderingAllowed(false);
        TableColumn col1 = tableParam.getColumnModel().getColumn(0);
        TableColumn col2 = tableParam.getColumnModel().getColumn(1);
        TableColumn col3 = tableParam.getColumnModel().getColumn(2);
        TableColumn col4 = tableParam.getColumnModel().getColumn(3);
        //int width = 100;
        col1.setPreferredWidth(50);        
        col2.setPreferredWidth(50);        
        col3.setPreferredWidth(70);        
        col4.setPreferredWidth(285);
        
        //ScrollPaneErr.setVisible(false);

        txtExpression.setBounds(new Rectangle(475, 5, 5, 5));
        txtExpression.setEnabled(false);
        txtExpression.setEditable(false);
        jButton1.setText("Check");
        jButton1.setBounds(new Rectangle(410, 385, 70, 20));
        jButton1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButton1_actionPerformed(e);
                    }
                });
        jLabel1.setText("Error log messages:");
        jLabel1.setBounds(new Rectangle(10, 395, 135, 15));
        ScrollPaneErr.setBounds(new Rectangle(5, 415, 475, 160));
        ScrollPaneErr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setSize(new Dimension(491, 638));
        this.getContentPane().setLayout( null );
        buttonOK.setText("OK");
        buttonOK.setBounds(new Rectangle(325, 580, 73, 22));
        buttonOK.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        buttonOK_actionPerformed(e);
                    }
                });
        buttonCancel.setText("Cancel");
        buttonCancel.setBounds(new Rectangle(405, 580, 73, 22));
        buttonCancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        buttonCancel_actionPerformed(e);
                    }
                });
        jScrollPane1.setBounds(new Rectangle(90, 50, 340, 155));
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jList1.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        jList1_mouseClicked(e);
                    }
                });
        jScrollPane2.setBounds(new Rectangle(5, 230, 475, 150));
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.getContentPane().add(txtExpression, null);
        this.getContentPane().add(jButton1, null);
        this.getContentPane().add(jLabel1, null);
        ScrollPaneErr.getViewport().add(errorList, null);
        this.getContentPane().add(ScrollPaneErr, null);
        this.getContentPane().add(filterLabel, null);
        this.getContentPane().add(jTextField1, null);
        this.getContentPane().add(argLabel, null);
        this.getContentPane().add(funcLabel, null);
        jScrollPane2.getViewport().add(tableParam, null);
        this.getContentPane().add(jScrollPane2, null);
        jScrollPane1.getViewport().add(jList1, null);
        this.getContentPane().add(jScrollPane1, null);
        this.getContentPane().add(buttonCancel, null);
        this.getContentPane().add(buttonOK, null);
        this.setBounds(250,250,this.getWidth(), this.getHeight());

        /*JDBCQuery query=new JDBCQuery(con);
        ResultSet rs1;

        JDBCQuery query2=new JDBCQuery(con);
        ResultSet rs2;
        
       
        rs1=query.select("select * " +
                        " from IISC_FUNCTION" +
                        " where PR_id = " + treeId + " " +
                        " ORDER BY Fun_name");

        model.removeAllElements();
        String name = "";
        String func = "";
        ids = "";
        while(rs1.next()){
        
            func = rs1.getString("Fun_name") + " ( ";
            String tmpid = rs1.getString("Fun_id");
            ids += tmpid + "-";
            
            String sqlQ = "select * from IISC_FUN_PARAM" +
            ",IISC_DOMAIN " +
            " where " +
            "IISC_FUN_PARAM.PR_id = IISC_DOMAIN.PR_id and" +
            " IISC_FUN_PARAM.Dom_id = IISC_DOMAIN.Dom_id AND " +
            " IISC_FUN_PARAM.PR_id = " + treeId + " AND " +
            " IISC_FUN_PARAM.Fun_id = " + tmpid + "" +
            " ORDER BY IISC_FUN_PARAM.Param_seq ";   
            
            rs2 = query2.select(sqlQ);            
            
            while(rs2.next()){
                func += rs2.getString("Dom_mnem") + " ,";
            }
            func = func.substring(0,func.length() -1) + ")";
            model.addElement(func);
            modelDef.addElement(func);

        }
        
        query2.Close();
        query.Close();
        */
         try{   
             JDBCQuery query=new JDBCQuery(con);
             JDBCQuery query2=new JDBCQuery(con);
             ResultSet rs,rs1,rs2,rs3;
             //String atts = "";
             //String idp = String.valueOf(TOB);
           
             rs = query.select("SELECT * , IISC_FUNCTION.FUN_ID as FID" + 
             "   FROM ((IISC_FUNCTION LEFT JOIN IISC_PACK_FUN ON IISC_FUNCTION.Fun_id = IISC_PACK_FUN.Fun_id) " +
             "   LEFT JOIN IISC_PACKAGE ON IISC_PACK_FUN.Pack_id = IISC_PACKAGE.Pack_id) " +
             "   LEFT JOIN IISC_DOMAIN ON IISC_FUNCTION.Dom_id = IISC_DOMAIN.Dom_id " + 
             "   WHERE IISC_FUNCTION.PR_ID = " + treeId + " AND IISC_DOMAIN.PR_ID = " + treeId +
             "   ORDER BY IISC_PACKAGE.Pack_name,IISC_FUNCTION.Fun_name " );
             
             while( rs.next() ){
                 String packName = rs.getString("Pack_name");
                 String domName = rs.getString("Dom_mnem");
                 String funName = rs.getString("Fun_name");
                 String funId = rs.getString("FID");
                 ids += funId + "-";
                 if(packName == null) packName = "";
                 else packName += ".";
                 if(domName == null) domName = "";
                 
                 rs2 = query2.select("   SELECT * " +
                                     "   FROM IISC_FUN_PARAM,IISC_DOMAIN " +
                                     "   WHERE   IISC_FUN_PARAM.Dom_id = IISC_DOMAIN.Dom_id AND " +
                                     "   IISC_FUN_PARAM.PR_ID = " + treeId + " AND " +
                                     "   IISC_FUN_PARAM.Fun_id = " + funId + " AND " +
                                     "   IISC_DOMAIN.PR_ID = " + treeId);
                 String params = " ( ";
                 while(rs2.next())
                     params += rs2.getString("Dom_mnem") + " ,";
                 params = params.substring(0,params.length() - 1);
                 params += ") ";
                 
                 //model.addItem(packName + funName + params + " : " + domName);
                 model.addElement(packName + funName + params + " : " + domName);
                 modelDef.addElement(packName + funName + params + " : " + domName);                 
                 
                 rs2.close();
             }

             rs.close();
            
         }
         catch(SQLException ex){
             System.out.println("exDDLIST:" + ex);
         }         
    }
    

    private void buttonOK_actionPerformed(ActionEvent e) {
        if(newFunc){
            if(tmpMy.getClass().toString().equals("class iisc.CheckConstraintExpressionEditor")){
                ScrollPaneErr.setVisible(false);
                boolean test = true;
                String msg = "<html><center>Check expressions for:<br>";
                model2.removeAllElements();
                for(int z = 0; z < tableParam.getRowCount(); z++ ){
                    txtExpression.setText(tableModel.getValueAt(z,3).toString());
                    CCEValue a = Attribute.checkSyntax2(txtExpression,true,con,typeOfCheck,treeId,ID_2,ID,nodeName,model2);
                    //System.out.println("check:" + a.check);
                    //System.out.println("xml:" + a.xml);
                    if ( !a.check )// checkSyntax(tableModel.getValueAt(z,3).toString(),z) == -1 )
                        test = false;
                }
                if(!test){
                    ScrollPaneErr.setVisible(true);
                    return;
                }
    
                int tmpclck = ((CheckConstraintExpressionEditor)tmpMy).clicked;
                int tmpc = tmpclck;
                boolean draw = false;
                if( tableParam.getRowCount() == 0)
                    draw = true;
                
                ((CheckConstraintExpressionEditor)tmpMy).clicked = ((CheckConstraintExpressionEditor)tmpMy).addNode("FUNCTION - " + ((String)jList1.getSelectedValue()).split("\\(")[0],"B",0,7,draw,"");
                
                int clickFun = ((CheckConstraintExpressionEditor)tmpMy).clicked;
                /*System.out.println( ((String)jList1.getSelectedValue()).split(" ")[1]);
                return;*/
                //String args[] = ((String)jList1.getSelectedValue()).split(" ")[1].split(",");
                
                for(int z = 0; z < tableParam.getRowCount(); z++ ){
                    ((CheckConstraintExpressionEditor)tmpMy).clicked = clickFun;
                    if(z + 1 == tableParam.getRowCount())
                        draw = true;
                    ((CheckConstraintExpressionEditor)tmpMy).clicked = ((CheckConstraintExpressionEditor)tmpMy).addNode("ARG * - " + tableModel.getValueAt(z,1),"B",0,77,false,"");
                    ((CheckConstraintExpressionEditor)tmpMy).addNode("Simple Expression","S",0,0,draw,tableModel.getValueAt(z,3).toString());
                }
                ((CheckConstraintExpressionEditor)tmpMy).tmp1[tmpc].mClicked(null);
            }
            else if(tmpMy.getClass().toString().equals("class iisc.FunctionEditor")){
                ScrollPaneErr.setVisible(false);
                boolean test = true;
                String msg = "<html><center>Check expressions for:<br>";
                model2.removeAllElements();
                /*for(int z = 0; z < tableParam.getRowCount(); z++ ){
                    txtExpression.setText(tableModel.getValueAt(z,3).toString());
                    CCEValue a = Attribute.checkSyntax2(txtExpression,true,con,typeOfCheck,treeId,ID_2,ID,nodeName,model2);
                    //System.out.println("check:" + a.check);
                    //System.out.println("xml:" + a.xml);
                    if ( !a.check )// checkSyntax(tableModel.getValueAt(z,3).toString(),z) == -1 )
                        test = false;
                }
                if(!test){
                    ScrollPaneErr.setVisible(true);
                    return;
                }*/
                
                int tmpclck = ((FunctionEditor)tmpMy).clicked;
                int tmpc = tmpclck;
                boolean draw = false;
                if( tableParam.getRowCount() == 0)
                    draw = true;
                
                ((FunctionEditor)tmpMy).clicked = ((FunctionEditor)tmpMy).addNode("FUNCTION - " + ((String)jList1.getSelectedValue()).split("\\(")[0],"B",0,7,draw,"");
                
                int clickFun = ((FunctionEditor)tmpMy).clicked;
                /*System.out.println( ((String)jList1.getSelectedValue()).split(" ")[1]);
                return;*/
                //String args[] = ((String)jList1.getSelectedValue()).split(" ")[1].split(",");
                
                for(int z = 0; z < tableParam.getRowCount(); z++ ){
                    ((FunctionEditor)tmpMy).clicked = clickFun;
                    if(z + 1 == tableParam.getRowCount())
                        draw = true;
                    ((FunctionEditor)tmpMy).clicked = ((FunctionEditor)tmpMy).addNode("ARG * - " + tableModel.getValueAt(z,1),"B",0,77,false,"");
                    ((FunctionEditor)tmpMy).addNode("Simple Expression","S",0,0,draw,tableModel.getValueAt(z,3).toString());
                }
                ((FunctionEditor)tmpMy).tmp1[tmpc].mClicked(null);                
            }
        }
        else{//update
            
        }
         this.dispose();        
    }

    private void buttonCancel_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    private void selectFunc(int fun_id){
        try{
            JDBCQuery query=new JDBCQuery(con);
            ResultSet rs1;
            
            while(tableModel.getRowCount() != 0)
                tableModel.removeRow(0);            
            
            String sqlQ = "select * from IISC_FUN_PARAM" +
            ",IISC_DOMAIN " +
            " where " +
            "IISC_FUN_PARAM.PR_id = IISC_DOMAIN.PR_id and" +
            " IISC_FUN_PARAM.Dom_id = IISC_DOMAIN.Dom_id AND " +
            " IISC_FUN_PARAM.PR_id = " + treeId + " AND " +
            " IISC_FUN_PARAM.Fun_id = " + fun_id + "" +
            " ORDER BY IISC_FUN_PARAM.Param_seq ";
            System.out.println(""+sqlQ);
            rs1=query.select(sqlQ);
            
            while(rs1.next()){
                tableModel.addRow(new Object[]{rs1.getString("Param_seq"),rs1.getString("Param_name"),rs1.getString("Dom_mnem"),""});
            }

            query.Close();
        }
        catch(Exception ex){
            System.out.println(ex)            ;
        }
    }

    private void jList1_mouseClicked(MouseEvent e) {
        //System.out.println("selected: " + jList1.getSelectedIndex());
        if(jList1.getSelectedIndex() != -1){
            //System.out.println("id        : " + ids.split("-")[jList1.getSelectedIndex()]);
            //System.out.println("svi id-ovi:" + ids);
            selectFunc(Integer.parseInt(ids.split("-")[jList1.getSelectedIndex()]));
        }
    }

    private void jTextField1_keyReleased(KeyEvent e) {
        model.removeAllElements();
        while(tableModel.getRowCount() !=0)
            tableModel.removeRow(0);
            
        for(int i=0;i<modelDef.size();i++)
            model.addElement(modelDef.getElementAt(i));
            
        for(int i=0;i < model.getSize(); i++){
            System.out.println("+" + model.getElementAt(i).toString());
            System.out.println("=" + model.getElementAt(i).toString().contains(jTextField1.getText()));
            if (!model.getElementAt(i).toString().contains(jTextField1.getText())){
                model.removeElementAt(i);
                i--;
            }
        }
            
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        model2.removeAllElements();
        for(int z = 0; z < tableParam.getRowCount(); z++ ){
            txtExpression.setText(tableModel.getValueAt(z,3).toString());
            DefaultListModel tmpModel = new DefaultListModel();
            Attribute.checkSyntax2(txtExpression,true,con,typeOfCheck,treeId,ID_2,ID,nodeName,tmpModel);
            for(int u=0;u< tmpModel.getSize(); u++)
                model2.addElement(tmpModel.getElementAt(u));
        }
    }
    
    

    
}
