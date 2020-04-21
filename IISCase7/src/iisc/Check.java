package iisc;


import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import java.awt.event.*;
import javax.swing.JLabel;
import java.sql.*;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Check extends JDialog implements ActionListener{

    public JButton btnClose = new JButton();
    private Connection con;
    private Form form;
    private ButtonGroup bgrp=new ButtonGroup();
    public int ID,FID,TOB;
    public String dialog=new String();
    private JButton btnHelp = new JButton();
    private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private ImageIcon iconUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
    private ImageIcon iconDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
    private JLabel jLabel13 = new JLabel();
    private JLabel lbObject = new JLabel();
    private JLabel jLabel14 = new JLabel();
    private JTextPane txtExpr = new JTextPane();
    private JButton btnSave = new JButton();
    private JButton btnApply = new JButton();
    private JButton expButton = new JButton();
    private JButton chkButton = new JButton();
    private JScrollPane jScrollPaneErr = new JScrollPane();
    private JList errorList = new JList();
    private DefaultListModel model2 = new DefaultListModel();
    private IISFrameMain pMy = null;
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    //private JScrollPane jScrollPane1 = new JScrollPane();
    //private JList jList1 = new JList();
    private JScrollPane jScrollPane2 = new JScrollPane();
    //private DefaultListModel listModel = new DefaultListModel();
     DropDownList ddList = null;

    public Check(){
        this(null, "",-1, false,null,null);
    }

    public Check(IISFrameMain parent, String title,int m1, boolean modal, Connection conn, Form frm ){
        super(parent, title, modal);
        try{ 
            pMy = parent;
            TOB=m1;
            form=frm;
            con=conn;
            jbInit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
  }

    private void jbInit() throws Exception{  
        
        ddList = new DropDownList(2,con,txtExpr,String.valueOf(form.tree.ID),String.valueOf(form.id),String.valueOf(TOB));
        this.add(ddList);      
        ddList.setVisible(false);
        
        //jList1.setModel(listModel);        
        errorList.setModel(model2);  
        errorList.setCellRenderer(new CheckConstraintErrorListRender());
        //jScrollPaneErr.setVisible(false);
        jLabel1.setText("Error log");
        jLabel1.setBounds(new Rectangle(10, 200, 60, 15));
        jLabel2.setText("messages:");
        jLabel2.setBounds(new Rectangle(10, 215, 75, 15));
        //jScrollPane1.setBounds(new Rectangle(15, 75, 65, 90));
        //jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setResizable(false);
        this.setSize(new Dimension(460, 380));
        this.getContentPane().setLayout(null);
        this.setTitle("Component Type Check Constraint");
        this.addWindowListener(new WindowAdapter()
          {
            public void windowClosed(WindowEvent e)
            {
            }
          });
        btnClose.setMaximumSize(new Dimension(50, 30));
        btnClose.setPreferredSize(new Dimension(50, 30));
        btnClose.setText("Cancel");
        btnClose.setBounds(new Rectangle(325, 315, 80, 30));
        btnClose.setMinimumSize(new Dimension(50, 30));
        btnClose.setFont(new Font("SansSerif", 0, 11));
        btnClose.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              close_ActionPerformed(ae);
            }
          });
       
    
        
        btnHelp.setBounds(new Rectangle(410, 315, 35, 30));
        btnHelp.setIcon(imageHelp);
        btnHelp.setFont(new Font("SansSerif", 0, 11));
        btnHelp.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnHelp_actionPerformed(e);
            }
          });
    
    
        jLabel13.setText("Component Type:");
        jLabel13.setBounds(new Rectangle(10, 10, 85, 20));
        jLabel13.setFont(new Font("SansSerif", 0, 11));
        lbObject.setBounds(new Rectangle(105, 10, 340, 20));
        lbObject.setFont(new Font("SansSerif", 1, 11));
        lbObject.setText("---------");
        jLabel14.setText("Check Constraint:");
        jLabel14.setBounds(new Rectangle(10, 40, 85, 20));
        jLabel14.setFont(new Font("SansSerif", 0, 11));
        txtExpr.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        txtExpr.setFont(new Font("SansSerif", 0, 11));
        txtExpr.addKeyListener(new KeyAdapter()
          {
            public void keyTyped(KeyEvent e)
            {
                        txtTittle_keyTyped(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtExpr_keyReleased(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtExpr_keyPressed(e);
                    }
                });
        txtExpr.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        txtExpr_mouseClicked(e);
                    }
                });
        txtExpr.addFocusListener(new FocusAdapter() {
                    public void focusGained(FocusEvent e) {
                        txtExpr_focusGained(e);
                    }
                });
        btnSave.setMaximumSize(new Dimension(50, 30));
        btnSave.setPreferredSize(new Dimension(50, 30));
        btnSave.setText("OK");
        btnSave.setBounds(new Rectangle(245, 315, 75, 30));
        btnSave.setMinimumSize(new Dimension(50, 30));
        btnSave.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              save_ActionPerformed(ae);
            }
          });
        btnSave.setEnabled(false);
        btnApply.setMaximumSize(new Dimension(50, 30));
        btnApply.setPreferredSize(new Dimension(50, 30));
        btnApply.setText("Apply");
        btnApply.setBounds(new Rectangle(165, 315, 75, 30));
        btnApply.setMinimumSize(new Dimension(50, 30));
        btnApply.setEnabled(false);
        btnApply.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              apply_ActionPerformed(ae);
            }
          });
        expButton.setText("Expression Editor");
        expButton.setBounds(new Rectangle(90, 165, 175, 30));
        expButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        expButton_actionPerformed(e);
                    }
                });
        chkButton.setText("Check Syntax");
        chkButton.setBounds(new Rectangle(270, 165, 175, 30));
        chkButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        chkButton_actionPerformed(e);
                    }
                });
        jScrollPaneErr.setBounds(new Rectangle(90, 200, 355, 110));
        jScrollPaneErr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPaneErr.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        jScrollPaneErr.getViewport().add(errorList, null);
        /*jScrollPane1.getViewport().add(jList1, null);
        txtExpr.add(jScrollPane1, null);
        jScrollPane1.setVisible(false);
        //this.getContentPane().add(jScrollPane1, null);
        jList1.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jList1_keyPressed(e);
                    }
                });
        jList1.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        jList1_mouseClicked(e);
                    }
                });*/
        jScrollPane2.setBounds(new Rectangle(10, 60, 435, 100));
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.getViewport().add(txtExpr, null);
        this.getContentPane().add(jScrollPane2, null);
        this.getContentPane().add(jLabel2, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(jScrollPaneErr, null);
        this.getContentPane().add(chkButton, null);
        this.getContentPane().add(expButton, null);
        this.getContentPane().add(btnApply, null);
        this.getContentPane().add(btnSave, null);
        this.getContentPane().add(jLabel14, null);
        this.getContentPane().add(lbObject, null);
        this.getContentPane().add(jLabel13, null);
        this.getContentPane().add(btnHelp, null);
        this.getContentPane().add(btnClose, null);
        setCheck();
        //lstAtt.setListData(setAtt("")); 
    }

    private void close_ActionPerformed(ActionEvent e){ 
        if(btnSave.isEnabled())
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Component Type Check Constraint", JOptionPane.YES_NO_OPTION)==0)
                update(1);
        dispose();
  }
  
    public void actionPerformed(ActionEvent e){
    }
  
    private void update(int i){
        JDBCQuery query=new JDBCQuery(con);
        ResultSet rs1;  
        query.update("update IISC_COMPONENT_TYPE_OBJECT_TYPE set Tob_check='" + txtExpr.getText().trim().replaceAll("'","''") + "' WHERE PR_id = " + form.tree.ID + " AND Tf_id = " + form.id + " AND Tob_id = " + TOB);
        btnApply.setEnabled(false);
        btnSave.setEnabled(false);
        JOptionPane.showMessageDialog(null, "<html><center>Check Constraint saved!", "Component Type Check Constraint", JOptionPane.INFORMATION_MESSAGE);
        if(i==1)
            dispose();
    }
    private void btnHelp_actionPerformed(ActionEvent e){
        Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con);
        Settings.Center(hlp);
        hlp.setVisible(true);
    }

    private void setCheck(){
        try {

            JDBCQuery query=new JDBCQuery(con);
            ResultSet rs;
            ResultSet rs1;
            //System.out.println("PRID: " + form.tree.ID + "   ID_2: "+ form.id + "   ID:" + TOB);
            rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where PR_id="+ form.tree.ID +" and  TF_ID="+ form.id +" and TOB_id="+TOB);
            if(rs.next()){
                String txt=rs.getString("TOB_check");
                if(txt == null)
                    txt="";
                txtExpr.setText(txt.trim());
                lbObject.setText(rs.getString("TOB_mnem"));
                Attribute.checkSyntax2(txtExpr,true,con,2,String.valueOf(form.tree.ID),String.valueOf(form.id),String.valueOf(TOB),"",model2);
            }
            query.Close();
        }
        catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        } 
    }

    private void save_ActionPerformed(ActionEvent e){
        if(                 Attribute.checkSyntax2(txtExpr,true,con,2,String.valueOf(form.tree.ID),String.valueOf(form.id),String.valueOf(TOB),"",model2).check     )
            update(1);
    }

    private void apply_ActionPerformed(ActionEvent e){
        if(                 Attribute.checkSyntax2(txtExpr,true,con,2,String.valueOf(form.tree.ID),String.valueOf(form.id),String.valueOf(TOB),"",model2).check     )
            update(0);
    }

    private void txtTittle_keyTyped(KeyEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }


    private void chkButton_actionPerformed(ActionEvent e) {
        Attribute.checkSyntax2(txtExpr,true,con,2,String.valueOf(form.tree.ID),String.valueOf(form.id),String.valueOf(TOB),"",model2)     ;
    }

    private void expButton_actionPerformed(ActionEvent e) {
        if(                 Attribute.checkSyntax2(txtExpr,true,con,2,String.valueOf(form.tree.ID),String.valueOf(form.id),String.valueOf(TOB),"",model2).check      ){
            CheckConstraintExpressionEditor a= new CheckConstraintExpressionEditor((Frame) pMy,"Function",true,con,/*form.tree,*/TOB,2,txtExpr,"",String.valueOf(form.id),String.valueOf(form.tree.ID));
            a.setVisible(true);
        }    
    }
               
    private void txtExpr_keyReleased(KeyEvent e) {

        //System.out.println("aaaaaaaaaaaaaaa::" + e.getKeyCode());
        if( e.getKeyCode() == 16 || e.getKeyCode() == 17 || e.getKeyCode() == 18 || e.getKeyCode() == 37 || 
            e.getKeyCode() == 38 || e.getKeyCode() == 39 || e.getKeyCode() == 40 || e.getKeyCode() == 33 ||
            e.getKeyCode() == 34 || e.getKeyCode() == 35 || e.getKeyCode() == 36 || /*e.getKeyCode() == 127 || */
            e.getKeyCode() == 155 || e.getKeyCode() == 10 )
            return;
          
        int tmpCaret = txtExpr.getCaretPosition();  
        txtExpr.setText(txtExpr.getText().replaceAll("\r",""));
        /*int tmpCaret2 = txtExpr.getCaretPosition();
        System.out.println("caret1 :" + tmpCaret);
        System.out.println("caret2 :" + tmpCaret2);*/
        
        txtExpr.setCaretPosition(tmpCaret);
        Attribute.checkSyntax2(txtExpr,false,con,2,String.valueOf(form.tree.ID),String.valueOf(form.id),String.valueOf(TOB),"",model2);

    }
    
    private void txtExpr_keyPressed(KeyEvent e) {
        //System.out.println("xxx:" + e.getKeyCode());
        //jScrollPane1.setVisible(false);
        if( e.getKeyCode() == 113){
        
            int x = (int)jScrollPane2.getBounds().getX();
            int y = (int)jScrollPane2.getLocation().getY() + (int)jScrollPane2.getSize().getHeight();
            ddList.setData(null);
            ddList.setBounds( x , y + 4, jScrollPane2.getWidth() , 110 );
            ddList.setVisible(true);
            ddList.requestFocus();
        }
    }

    private void txtExpr_mouseClicked(MouseEvent e) {
        if(ddList != null)
            ddList.setVisible(false);
    }


    private void txtExpr_focusGained(FocusEvent e) {
        Attribute.checkSyntax2(txtExpr,false,con,2,String.valueOf(form.tree.ID),String.valueOf(form.id),String.valueOf(TOB),"",model2);
    }
}
