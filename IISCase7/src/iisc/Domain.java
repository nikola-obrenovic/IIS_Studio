package iisc;


import java.awt.*;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;

import java.awt.event.FocusEvent;

import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
import javax.swing.JList;

public class Domain extends JDialog implements ActionListener
{
        private JToolBar jToolBar1 = new JToolBar();
        private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
        private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
        private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
        private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
        private JButton btnFirst= new JButton();
        private JButton btnPrev = new JButton();
        private JButton btnNext = new JButton();
        private JButton  btnLast = new JButton();
        private JButton btnNew = new JButton();
        public JButton btnClose = new JButton();
        private PTree tree;
        private JLabel jLabel3 = new JLabel();
        private JTextField txtName = new JTextField();
        private JLabel jLabel10 = new JLabel();
        private Connection connection;
        public String Mnem;
        public int id;
        private int[] domain;
        private  DefaultListModel model =new DefaultListModel();
        private  DefaultListModel model1 = new DefaultListModel();
        private DropDownList ddList = null;

        public QueryTableModel qtm;
        private ButtonGroup bgrp = new ButtonGroup();
        private ButtonGroup bgrp1 = new ButtonGroup();
        private ButtonGroup bgrp2 = new ButtonGroup();
        private ButtonGroup bgrp3 = new ButtonGroup();
        public JButton btnSave = new JButton();
        private JTextArea txtDescription = new JTextArea();
        private JButton btnErase = new JButton();
        public JTextField txtPrimitive = new JTextField();
        private JButton btnPrimitive = new JButton();
        public JComboBox cmbParent = new JComboBox();
        public int att=-2;
        public Attribute at;
        private JButton btnHelp = new JButton();
        private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
        private ImageIcon iconUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
        private ImageIcon iconDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
        private JTabbedPane jTabbedPane1 = new JTabbedPane();
        private JPanel jPanel1 = new JPanel();
        private JPanel jPanel2 = new JPanel();
        public JButton btnApply = new JButton();
        private JComboBox cmbDom = new JComboBox();
        private JButton btnDomain = new JButton();
        private boolean dispose=false;
        private JLabel jLabel15 = new JLabel();
        private JRadioButton rdInheritance = new JRadioButton();
        private JRadioButton rdComplex = new JRadioButton();
        private JRadioButton rdPrimitive = new JRadioButton();
        private JRadioButton rdUser = new JRadioButton();
        private JRadioButton rdTuple = new JRadioButton();
        private JRadioButton rdChoice = new JRadioButton();
        private JRadioButton rdSet = new JRadioButton();
        public JTextField txtDecimal = new JTextField();
        private JLabel jLabel14 = new JLabel();
        public JTextField txtLength = new JTextField();
        private JLabel jLabel12 = new JLabel();
        private JButton btnAdd = new JButton();
        private JButton btnRem = new JButton();
        private JLabel jLabel17 = new JLabel();
        private JLabel jLabel18 = new JLabel();
        private JScrollPane jScrollPane1 = new JScrollPane();
        private JList lstAtt = new JList(model);
        private JScrollPane jScrollPane2 = new JScrollPane();
        private JList lstAttList = new JList(model1);
        private JTextPane txtExpr = new JTextPane();
        private JLabel jLabel7 = new JLabel();
        public JTextField txtDefault = new JTextField();
        private JLabel jLabel13 = new JLabel();
        private JPanel jPanel3 = new JPanel();
        private JPanel jPanel4 = new JPanel();
        private JButton btnUp = new JButton();
        private JButton btnDown = new JButton();
        private JButton btnDomainPrimitive = new JButton();
        private JLabel jLabel1 = new JLabel();
        private JButton btnDomainUser = new JButton();
        public JComboBox cmbDomain = new JComboBox();
        private JPanel jPanel7 = new JPanel();
        private JPanel jPanel5 = new JPanel();
        private JTextArea txtComment = new JTextArea();
        private JLabel jLabel6 = new JLabel();
        private IISFrameMain p = null;
        private DefaultListModel model2 = new DefaultListModel();
 
        ControlPanel cp =  new ControlPanel();
        private JButton expButton = new JButton();
        private JButton chkButton = new JButton();
        private JList errorList = new JList();
        private JScrollPane jScrollPaneErr = new JScrollPane();
        private JLabel jLabel2 = new JLabel();
        private JScrollPane jScrollPane3 = new JScrollPane();
        private boolean can=true;

        public Domain(){
            this(null, "", false);
        }

        public Domain(IISFrameMain parent, String title, boolean modal){
            super((Frame) parent, title, modal);
            try{
                p = parent;
                jbInit();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
  
  
        public Domain(IISFrameMain parent, String title, boolean modal,Connection con,int m , PTree tr ){
            super((Frame) parent, title, modal);
            try{   
                p = parent;
                tree=tr;
                connection=con;
                id=m;
                if(m>=0){
                    JDBCQuery query=new JDBCQuery(connection);
                    ResultSet rs1;
                    rs1=query.select("select * from IISC_Domain where PR_id="+ tree.ID +" and Dom_id="+id);
                    rs1.next();
                    Mnem=rs1.getString("Dom_mnem");
                    query.Close();
                }
                else
                    Mnem="";
                Iterator it=tree.WindowsManager.iterator();
                while(it.hasNext()){
                    Object obj=(Object)it.next();
                    Class cls=obj.getClass();
                    if(cls==this.getClass()){ 
                        ((Domain)obj).dispose();
                    }
                }
                jbInit();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    private void jbInit() throws Exception{

        ddList = new DropDownList(0,connection,txtExpr,String.valueOf(tree.ID),"",String.valueOf(id));
        jPanel2.add(ddList);    
        ddList.setVisible(false);
        errorList.setModel(model2);  
        errorList.setCellRenderer(new CheckConstraintErrorListRender());
        //jScrollPaneErr.setVisible(false);
        rdUser.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                rdUser_actionPerformed(e);
            }
        });
        this.setResizable(false);
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs;
        this.setSize(new Dimension(460, 593));
        this.getContentPane().setLayout(null);
        
        this.setTitle("User defined domains");
        jToolBar1.setBounds(new Rectangle(40, 5, 400, 35));
        jToolBar1.setFont(new Font("SansSerif", 0, 11));

        jToolBar1.setLayout(null);
        jToolBar1.setPreferredSize(new Dimension(249, 60));
        jToolBar1.setFloatable(false);
        btnFirst.setMaximumSize(new Dimension(60, 60));
        btnFirst.setPreferredSize(new Dimension(25, 20));
        btnFirst.setIcon(iconPrevv);
        btnFirst.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
              prevv_ActionPerformed(ae);
            }
          });
        btnPrev.setMaximumSize(new Dimension(60, 60));
        btnPrev.setIcon(iconPrev);
        btnPrev.setPreferredSize(new Dimension(25, 20));
        btnPrev.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
            prev_ActionPerformed(ae);
            }
          });
        
        btnNext.setMaximumSize(new Dimension(60, 60));
        btnNext.setIcon(iconNext);
        btnNext.setPreferredSize(new Dimension(25, 20));
        btnNext.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
              next_ActionPerformed(ae);
            }
          });
        
        btnLast.setMaximumSize(new Dimension(60, 60));
        btnLast.setIcon(iconNextt);

        btnLast.setPreferredSize(new Dimension(25, 20));
        btnLast.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
              nextt_ActionPerformed(ae);
            }
        });
        
        btnNew.setMaximumSize(new Dimension(50, 30));
        btnNew.setPreferredSize(new Dimension(50, 30));
        btnNew.setText("New");
        btnNew.setBounds(new Rectangle(165, 525, 80, 30));
        btnNew.setMinimumSize(new Dimension(50, 30));
        btnNew.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
              new_ActionPerformed(ae);
            }
        });

        btnClose.setMaximumSize(new Dimension(50, 30));
        btnClose.setPreferredSize(new Dimension(50, 30));
        btnClose.setText("Cancel");
        btnClose.setBounds(new Rectangle(330, 525, 80, 30));
        btnClose.setMinimumSize(new Dimension(50, 30));
        
        btnClose.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
              close1_ActionPerformed(ae);
        
            }
          });
        jLabel3.setText("Description:");
        jLabel3.setBounds(new Rectangle(10, 45, 85, 20));
        jLabel3.setFont(new Font("SansSerif", 0, 11));
        txtName.setBounds(new Rectangle(85, 15, 350, 20));
        txtName.setFont(new Font("SansSerif", 0, 11));
        jLabel10.setText("Name:");
        jLabel10.setBounds(new Rectangle(10, 15, 65, 20));
        jLabel10.setFont(new Font("SansSerif", 0, 11));
        btnSave.setMaximumSize(new Dimension(50, 30));
        btnSave.setPreferredSize(new Dimension(50, 30));
        btnSave.setText("OK");
        btnSave.setBounds(new Rectangle(250, 525, 75, 30));
        btnSave.setMinimumSize(new Dimension(50, 30));
        btnSave.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              save_ActionPerformed(ae);
            
            }
          });
    
        txtDescription.setBounds(new Rectangle(85, 50, 350, 70));
        txtDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        btnErase.setMaximumSize(new Dimension(50, 30));
        btnErase.setPreferredSize(new Dimension(50, 30));
        btnErase.setText("Delete");
        btnErase.setBounds(new Rectangle(85, 525, 75, 30));
        btnErase.setMinimumSize(new Dimension(50, 30));
        btnErase.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent ae)
        {
          erase_ActionPerformed(ae);
        }
        });
        txtPrimitive.setBounds(new Rectangle(155, 5, 155, 20));
        txtPrimitive.setFont(new Font("SansSerif", 0, 11));
        txtPrimitive.setEnabled(false);
        btnPrimitive.setMaximumSize(new Dimension(50, 30));
        btnPrimitive.setPreferredSize(new Dimension(50, 30));
        btnPrimitive.setText("...");
        btnPrimitive.setBounds(new Rectangle(315, 5, 30, 20));
        btnPrimitive.setMinimumSize(new Dimension(50, 30));
        btnPrimitive.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              close_ActionPerformed(ae);
            }
          });
        txtName.addKeyListener(new KeyAdapter()
          {
            public void keyTyped(KeyEvent e)
            {
              txtName_keyTyped(e);
            }
          });
        txtDescription.setFont(new Font("SansSerif", 0, 11));
        txtDescription.addKeyListener(new KeyAdapter()
          {
            public void keyTyped(KeyEvent e)
            {
              txtDescription_keyTyped(e);
            }
          });
        btnPrimitive.setFont(new Font("SansSerif", 0, 11));
        btnErase.setFont(new Font("SansSerif", 0, 11));
        btnNew.setFont(new Font("SansSerif", 0, 11));
        btnSave.setFont(new Font("SansSerif", 0, 11));
        btnClose.setFont(new Font("SansSerif", 0, 11));
        this.setFont(new Font("SansSerif", 0, 11));
        btnFirst.setBounds(new Rectangle(10, 5, 25, 20));
        btnPrev.setBounds(new Rectangle(40, 5, 25, 20));
        btnNext.setBounds(new Rectangle(305, 5, 25, 20));
        btnLast.setBounds(new Rectangle(335, 5, 25, 20));
        cmbParent.setBounds(new Rectangle(155, 60, 155, 20));
        cmbParent.setFont(new Font("SansSerif", 0, 11));
        cmbParent.addKeyListener(new KeyAdapter()
          {
            public void keyPressed(KeyEvent e)
            {
              cmbParent_keyPressed(e);
            }
          });
        cmbParent.addMouseListener(new MouseAdapter()
          {
            public void mouseClicked(MouseEvent e)
            {
              cmbParent_mouseClicked(e);
            }
          });
        btnHelp.setBounds(new Rectangle(415, 525, 35, 30));
        btnHelp.setIcon(imageHelp);
        btnHelp.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnHelp_actionPerformed(e);
            }
          });
        jTabbedPane1.setBounds(new Rectangle(5, 35, 445, 480));
        jPanel1.setLayout(null);
        jPanel2.setLayout(null);
        jPanel2.setFont(new Font("SansSerif", 0, 11));
    
        btnApply.setMaximumSize(new Dimension(50, 30));
        btnApply.setPreferredSize(new Dimension(50, 30));
        btnApply.setText("Apply");
        btnApply.setBounds(new Rectangle(5, 525, 75, 30));
        btnApply.setMinimumSize(new Dimension(50, 30));
        btnApply.setFont(new Font("SansSerif", 0, 11));
        btnApply.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              apply_ActionPerformed(ae);
            }
          });

        cmbDom.setFont(new Font("SansSerif", 0, 11));
        cmbDom.setBounds(new Rectangle(70, 5, 230, 20));
        cmbDom.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
                            jComboBox1_actionPerformed(e);
                        }
          });
        btnDomain.setMaximumSize(new Dimension(50, 30));
        btnDomain.setPreferredSize(new Dimension(50, 30));
        btnDomain.setText("...");
        btnDomain.setBounds(new Rectangle(315, 60, 30, 20));
        btnDomain.setMinimumSize(new Dimension(50, 30));
        btnDomain.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              domain_ActionPerformed(ae);
            }
          });
        btnDomain.setFont(new Font("SansSerif", 0, 11));
        jLabel15.setText("Domain type:");
        jLabel15.setBounds(new Rectangle(10, 130, 65, 20));
        jLabel15.setFont(new Font("SansSerif", 0, 11));
        rdInheritance.setText("Inheritance");
        rdInheritance.setBounds(new Rectangle(85, 130, 80, 20));
        rdInheritance.setFont(new Font("SansSerif", 0, 11));
        rdInheritance.setSelected(true);
        rdInheritance.addActionListener(new ActionListener()
          {
    
            public void actionPerformed(ActionEvent e)
            {
              rdInheritance_actionPerformed(e);
            }
          });
    
        rdComplex.setText("Complex");
        rdComplex.setBounds(new Rectangle(85, 250, 80, 20));
        rdComplex.setFont(new Font("SansSerif", 0, 11));
        rdComplex.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              rdComplex_actionPerformed(e);
            }
          });
        rdPrimitive.setText("From primitive domain");
        rdPrimitive.setBounds(new Rectangle(5, 5, 130, 20));
        rdPrimitive.setFont(new Font("SansSerif", 0, 11));
        rdPrimitive.setSelected(true);
        rdPrimitive.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              rdPrimitive_actionPerformed(e);
            }
    
          });
        rdUser.setText("From user defined domain");
        rdUser.setBounds(new Rectangle(5, 60, 150, 20));
        rdUser.setFont(new Font("SansSerif", 0, 11));
        rdTuple.setText("Tuple domain");
        rdTuple.setBounds(new Rectangle(5, 10, 95, 20));
        rdTuple.setFont(new Font("SansSerif", 0, 11));
        rdTuple.setSelected(true);
        rdTuple.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              rdTuple_actionPerformed(e);
            }
          });
        rdChoice.setText("Choice domain");
        rdChoice.setBounds(new Rectangle(110, 10, 100, 20));
        rdChoice.setFont(new Font("SansSerif", 0, 11));
        rdChoice.setOpaque(false);
        rdChoice.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              rdChoice_actionPerformed(e);
            }
          });
        rdSet.setText("Set domain");
        rdSet.setBounds(new Rectangle(215, 10, 80, 20));
        rdSet.setFont(new Font("SansSerif", 0, 11));
        rdSet.addActionListener(new ActionListener()
          {
            
            public void actionPerformed(ActionEvent e)
            {
              rdSet_actionPerformed(e);
            }
          });
        txtDecimal.setBounds(new Rectangle(295, 30, 50, 20));
        txtDecimal.setFont(new Font("SansSerif", 0, 11));
        txtDecimal.setEnabled(false);
        txtDecimal.addKeyListener(new KeyAdapter()
          {
            public void keyTyped(KeyEvent e)
            {
              txtDecimal_keyTyped(e);
            }
          });
        jLabel14.setText("Decimal places:");
        jLabel14.setBounds(new Rectangle(215, 30, 80, 20));
        jLabel14.setFont(new Font("SansSerif", 0, 11));
        txtLength.setBounds(new Rectangle(155, 30, 50, 20));
        txtLength.setFont(new Font("SansSerif", 0, 11));
        txtLength.setEnabled(false);
        txtLength.addKeyListener(new KeyAdapter()
          {
            public void keyTyped(KeyEvent e)
            {
              txtLength_keyTyped(e);
            }
          });
        jLabel12.setText("Length:");
        jLabel12.setBounds(new Rectangle(110, 30, 65, 20));
        jLabel12.setFont(new Font("SansSerif", 0, 11));
        bgrp.add(rdInheritance);
        bgrp.add(rdComplex);
        bgrp1.add(rdUser);
        bgrp1.add(rdPrimitive);
        bgrp2.add(rdTuple);
        bgrp2.add(rdSet);
        bgrp2.add(rdChoice);
        jToolBar1.add(btnFirst, null);
        jToolBar1.add(btnPrev, null);
        jToolBar1.add(btnNext, null);
        jToolBar1.add(btnLast, null);
        jToolBar1.add(cmbDom, null);
        jPanel3.add(rdPrimitive, null);
        jPanel3.add(rdUser, null);
        jPanel3.add(cmbParent, null);
        jPanel3.add(btnDomain, null);
        jPanel3.add(btnPrimitive, null);
        jPanel3.add(txtPrimitive, null);
        jPanel3.add(txtDecimal, null);
        jPanel3.add(jLabel14, null);
        jPanel3.add(txtLength, null);
        jPanel3.add(jLabel12, null);
        jPanel4.add(cmbDomain, null);
        jPanel4.add(btnDomainUser, null);
        jPanel4.add(jLabel1, null);
        jPanel4.add(btnDomainPrimitive, null);
        jPanel4.add(btnDown, null);
        jPanel4.add(btnUp, null);
        jPanel4.add(rdSet, null);
        jPanel4.add(rdChoice, null);
        jPanel4.add(rdTuple, null);
        jPanel4.add(jLabel17, null);
        jPanel4.add(btnRem, null);
        jPanel4.add(btnAdd, null);
        jPanel4.add(jLabel18, null);
        jScrollPane1.getViewport().add(lstAtt, null);
        jPanel4.add(jScrollPane1, null);
        jScrollPane2.getViewport().add(lstAttList, null);
        jPanel4.add(jScrollPane2, null);
        jPanel1.add(jPanel4, null);
        jPanel1.add(jPanel3, null);
        jPanel1.add(rdComplex, null);
        jPanel1.add(rdInheritance, null);
        jPanel1.add(jLabel15, null);
        jPanel1.add(txtDescription, null);
        jPanel1.add(jLabel10, null);
        jPanel1.add(txtName, null);
        jPanel1.add(jLabel3, null);
        jTabbedPane1.addTab("Domain", jPanel1);
        jScrollPane3.getViewport().add(txtExpr, null);
        jPanel2.add(jScrollPane3, null);
        jPanel2.add(jLabel2, null);
        jScrollPaneErr.getViewport().add(errorList, null);
        jPanel2.add(jScrollPaneErr, null);
        jPanel2.add(chkButton, null);
        jPanel2.add(expButton, null);
        jPanel2.add(jLabel13, null);
        jPanel2.add(txtDefault, null);
        jPanel2.add(jLabel7, null);
        jTabbedPane1.addTab("Expressions", jPanel2);
        jPanel5.add(jLabel6, null);
        jPanel5.add(txtComment, null);
        jTabbedPane1.addTab("Display",jPanel7);
        jTabbedPane1.addTab("Notes", jPanel5);
        this.getContentPane().add(btnApply, null);
        this.getContentPane().add(jTabbedPane1, null);
        this.getContentPane().add(btnHelp, null);
        this.getContentPane().add(jToolBar1, null);
        this.getContentPane().add(btnNew, null);
        this.getContentPane().add(btnClose, null);
        this.getContentPane().add(btnErase, null);
        this.getContentPane().add(btnSave, null);
      
        qtm=new QueryTableModel(connection,id);
        jLabel6.setFont(new Font("SansSerif", 0, 11));
        jLabel6.setBounds(new Rectangle(5, 10, 85, 20));
        jLabel6.setText("Comment:");
        expButton.setText("Expression Editor");
        expButton.setBounds(new Rectangle(105, 180, 165, 30));
        expButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButton1_actionPerformed(e);
                    }
                });
        chkButton.setText("Check Syntax");
        chkButton.setBounds(new Rectangle(275, 180, 160, 30));
        chkButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        chkButton_actionPerformed(e);
                    }
                });
        errorList.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        errorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPaneErr.setBounds(new Rectangle(105, 215, 330, 125));
        jScrollPaneErr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jLabel2.setText("Error log messages:");
        jLabel2.setBounds(new Rectangle(5, 215, 105, 15));
        jScrollPane3.setBounds(new Rectangle(5, 70, 430, 105));
        jScrollPane3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        txtComment.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
              txtComment_keyTyped(e);
            }
        });
        txtComment.setFont(new Font("SansSerif", 0, 11));
        txtComment.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        txtComment.setBounds(new Rectangle(5, 35, 430, 410));
        jPanel5.setLayout(null);
        btnDomainUser.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnDomainUser_actionPerformed(e);
            }
          });
        btnDomainPrimitive.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnDomainPrimitive_actionPerformed(e);
            }
          });
        btnDown.setIcon(iconDown);
        btnUp.setIcon(iconUp);
        cmbDomain.addMouseListener(new MouseAdapter()
          {
            public void mouseClicked(MouseEvent e)
            {
              cmbParent_mouseClicked(e);
            }
          });
        cmbDomain.addKeyListener(new KeyAdapter()
          {
            public void keyPressed(KeyEvent e)
            {
              cmbParent_keyPressed(e);
            }
          });
        cmbDomain.setFont(new Font("SansSerif", 0, 11));
        cmbDomain.setBounds(new Rectangle(110, 140, 160, 20));
        btnDomainUser.setToolTipText("Choose user defined domain");
        btnDomainPrimitive.setToolTipText("Choose primitive domain");
        btnDomainUser.setMaximumSize(new Dimension(50, 30));
        btnDomainUser.setPreferredSize(new Dimension(50, 30));
        btnDomainUser.setText("...");
        btnDomainUser.setBounds(new Rectangle(315, 140, 30, 20));
        btnDomainUser.setMinimumSize(new Dimension(50, 30));
        btnDomainUser.setFont(new Font("SansSerif", 0, 11));
        jLabel1.setBounds(new Rectangle(10, 140, 110, 20));
        jLabel1.setText("Set member domain:");
        btnDomainPrimitive.setFont(new Font("SansSerif", 0, 11));
        btnDomainPrimitive.setMinimumSize(new Dimension(50, 30));
        btnDomainPrimitive.setBounds(new Rectangle(280, 140, 30, 20));
        btnDomainPrimitive.setText("...");
        btnDomainPrimitive.setPreferredSize(new Dimension(50, 30));
        btnDomainPrimitive.setMaximumSize(new Dimension(50, 30));
        btnDown.setBounds(new Rectangle(320, 110, 25, 20));
        btnDown.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              down_ActionPerformed(ae);
            }
          });
        btnDown.setPreferredSize(new Dimension(25, 20));
        btnDown.setMaximumSize(new Dimension(60, 60));
        btnUp.setBounds(new Rectangle(320, 60, 25, 20));
        btnUp.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              up_ActionPerformed(ae);
            }
          });
        btnUp.setPreferredSize(new Dimension(25, 20));
        btnUp.setMaximumSize(new Dimension(60, 60));
        jPanel4.setBorder(BorderFactory.createLineBorder(new Color(124, 124, 124), 1));
        jPanel4.setLayout(null);
        jPanel4.setBounds(new Rectangle(85, 275, 350, 170));
        rdUser.setOpaque(false);
        rdPrimitive.setOpaque(false);
        jPanel3.setBorder(BorderFactory.createLineBorder(new Color(124, 124, 124), 1));
        jPanel3.setLayout(null);
        jPanel3.setBounds(new Rectangle(85, 155, 350, 90));
        jLabel13.setText("Default value:");
        jLabel13.setBounds(new Rectangle(5, 15, 75, 20));
        jLabel13.setFont(new Font("SansSerif", 0, 11));
        txtDefault.setBounds(new Rectangle(105, 15, 150, 20));
        txtDefault.setFont(new Font("SansSerif", 0, 11));
        txtDefault.addKeyListener(new KeyAdapter()
          {
            public void keyTyped(KeyEvent e)
            {
              txtLength_keyTyped(e);
            }
          });
        txtDefault.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              txtDefault_actionPerformed(e);
            }
          });
        jLabel7.setFont(new Font("SansSerif", 0, 11));
        jLabel7.setBounds(new Rectangle(5, 50, 85, 20));
        jLabel7.setText("Check condition:");
        txtExpr.addKeyListener(new KeyAdapter()
          {
            public void keyTyped(KeyEvent e)
            {
                            txtExpr_keyTyped(e);
                        }
    
                        public void keyReleased(KeyEvent e) {
                            txtExpr_keyReleased(e);
                        }
    
                        public void keyPressed(KeyEvent e) {
                            txtExpr_keyPressed(e);
                        }
                    });
        txtExpr.setFont(new Font("SansSerif", 0, 11));
        txtExpr.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            txtExpr.addFocusListener(new FocusAdapter() {
                        public void focusGained(FocusEvent e) {
                            txtExpr_focusGained(e);
                        }
                    });
            txtExpr.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            txtExpr_mouseClicked(e);
                        }
                    });
        lstAttList.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        lstAttList.setFont(new Font("SansSerif", 0, 11));
        jScrollPane2.setBounds(new Rectangle(185, 60, 130, 70));
        lstAtt.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        lstAtt.setFont(new Font("SansSerif", 0, 11));
        jScrollPane1.setBounds(new Rectangle(5, 60, 130, 70));
        jLabel18.setText("Attributes:");
        jLabel18.setBounds(new Rectangle(5, 35, 120, 20));
        jLabel18.setFont(new Font("SansSerif", 0, 11));
        jLabel17.setFont(new Font("SansSerif", 0, 11));
        jLabel17.setBounds(new Rectangle(185, 35, 120, 20));
        jLabel17.setText("Tuple attributes:");
        btnRem.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnRem_actionPerformed(e);
            }
          });
        btnRem.setFont(new Font("SansSerif", 0, 13));
        btnRem.setText("<");
        btnRem.setBounds(new Rectangle(140, 100, 40, 30));
        btnAdd.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnAdd_actionPerformed(e);
            }
          });
        btnAdd.setFont(new Font("SansSerif", 0, 13));
        btnAdd.setText(">");
        btnAdd.setBounds(new Rectangle(140, 60, 40, 30));

        setDomain(Mnem);

        tree.select_node(Mnem,"User defined domains");
    
        jPanel7.setLayout(null);
        
        //jTabbedPane1.addTab("Display",jPanel7);
        cp = new ControlPanel(jTabbedPane1.getWidth() - 50, 350, connection, (IISFrameMain)getParent(), id, this, tree.ID);
        cp.setBounds(20, 15 , jTabbedPane1.getWidth() - 40, 350);
        jPanel7.add(cp);
  }
  
    public void actionPerformed(ActionEvent e){
    }

    private void prevv_ActionPerformed(ActionEvent e)
    {
        String s="";
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        try
        {
            if(btnSave.isEnabled())
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "User defined domains", JOptionPane.YES_NO_OPTION)==0){
                //cp.Update();
                update(1);
            }
            rs1=query.select("select * from IISC_Domain where PR_id="+ tree.ID +" order by  Dom_mnem asc");
            if(rs1.next()){
                s=rs1.getString(3);
            }
            query.Close();
            setDomain(s);
            Mnem=s;
            tree.select_node(Mnem,"User defined domains");
        }
        catch(SQLException ef){
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    private void prev_ActionPerformed(ActionEvent e){
        String s=Mnem.trim() ;
        
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        try{ 
            if(btnSave.isEnabled())
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "User defined domains", JOptionPane.YES_NO_OPTION)==0){
                //cp.Update();
                update(1);
            }
            rs1=query.select("select Dom_mnem from IISC_Domain  where PR_id="+ tree.ID +" and Dom_mnem<'" + s + "' order by Dom_mnem desc" );
            if(rs1.next()){
                s=rs1.getString(1);
            }

            query.Close();
            setDomain(s);
            tree.select_node(Mnem,"User defined domains");
            Mnem=s;
        }
        catch(SQLException ef){
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void next_ActionPerformed(ActionEvent e){
        String s=Mnem.trim() ;
        
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        try{
            if(btnSave.isEnabled())
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "User defined domains", JOptionPane.YES_NO_OPTION)==0){
                //cp.Update();
                update(1);
            }
            rs1=query.select("select Dom_mnem from IISC_Domain  where PR_id="+ tree.ID +" and Dom_mnem>'" + s + "' order by Dom_mnem asc" );
            if(rs1.next()){
                s=rs1.getString(1);
            }

            query.Close();
            setDomain(s);
            Mnem=s;
            tree.select_node(Mnem,"User defined domains");
        }
        catch(SQLException ef){
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }


    private void nextt_ActionPerformed(ActionEvent e){
        String s="";
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        try
        {
            if(btnSave.isEnabled())
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "User defined domains", JOptionPane.YES_NO_OPTION)==0){
                //cp.Update();
                update(1);
            }
            rs1=query.select("select * from IISC_Domain  where PR_id="+ tree.ID +" order by  Dom_mnem desc");
            if(rs1.next()){
                s=rs1.getString(3);
            }
            query.Close();
            
            setDomain(s);
            Mnem=s;
            tree.select_node(Mnem,"User defined domains");
        }
        catch(SQLException ef){
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

        public void Reload (){
        try{
            JDBCQuery query=new JDBCQuery(connection);
            ResultSet rs;
            rs=query.select("select count(*) from IISC_Domain  where PR_id="+ tree.ID +" and Dom_id <> "+id);
            rs.next();
            int j=rs.getInt(1);
            query.Close();
            String[] sa=query.selectArray("select * from IISC_Domain  where PR_id="+ tree.ID +" and Dom_id <> "+id,j,3);
            sa[0]="";
            query.Close();
            cmbParent.removeAllItems();
            for (int k=0; k<sa.length;k++)
                cmbParent.addItem(sa[k]);
            
            cmbDomain.removeAllItems();
            rs=query.select("select count(*) from IISC_PRIMITIVE_TYPE ");
            rs.next();
            j=rs.getInt(1);
            query.Close();
            String[] sa1=query.selectArray1("select * from IISC_PRIMITIVE_TYPE "+ tree.ID,j,2);
            query.Close();
            domain= new int[sa.length+sa1.length];
            int k;
            for (k=0; k<sa.length;k++){
                cmbDomain.addItem(sa[k]);
                domain[k]=1;
            }
            for (j=0; j<sa1.length;j++){
                cmbDomain.addItem(sa1[j]);
                domain[k+j]=0;
            }
        }
        catch(SQLException ef){
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void close_ActionPerformed(ActionEvent e){  
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);  
        SearchTable ptype=new SearchTable((Frame)getParent(),"Select Primitive Type",true,connection);
        Settings.Center(ptype);
        ptype.type="Primitive Type";
        ptype.owner=this;
        ptype.setVisible(true);
    }
    
    private void close1_ActionPerformed(ActionEvent e)
    {
        if(btnSave.isEnabled())
        if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "User defined domains", JOptionPane.YES_NO_OPTION)==0){
            //cp.Update();    
            update(1);
        }
        if(att>-2)
            at.setAttribute(at.Mnem);
        dispose();
    }
    
    
    private void save_ActionPerformed(ActionEvent e){
        dispose=true;
        //cp.Update();
        update(1);
        tree.select_node(Mnem,"User defined domains");
    }
  
  
    private void new_ActionPerformed(ActionEvent e){
        Mnem="";
        id=-1;
        setDomain("");
    }

    public void update (int k)
    {
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;  
        boolean can=true;
        //System.out.println(txtName.getText().trim());
        try{
            if(Mnem.trim().equals("")){
                rs1=query.select("select * from IISC_Domain  where PR_id="+ tree.ID +" and Dom_mnem='"+ txtName.getText().trim() +"'");
                if(rs1.next()){
                    JOptionPane.showMessageDialog(null, "<html><center>Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    can=false;
                }
                query.Close();
            }
            else{
                if(!Mnem.toLowerCase().trim().equals(txtName.getText().toLowerCase().trim())){
                    rs1=query.select("select * from IISC_Domain  where PR_id="+ tree.ID +" and Dom_mnem='"+ txtName.getText().trim() +"'");
                    if(rs1.next()){ 
                        JOptionPane.showMessageDialog(null, "<html><center>Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        can=false;
                    }
                    query.Close();
                }  
            }
    
            if(txtName.getText().trim().equals(""))
                JOptionPane.showMessageDialog(null, "<html><center>Name required!", "User defined domains", JOptionPane.ERROR_MESSAGE);
            else if(txtName.getText().split(" ").length>1)
                JOptionPane.showMessageDialog(null, "<html><center>Name cannot contain blank character!", "User defined domains", JOptionPane.ERROR_MESSAGE);
            else if(txtDescription.getText().trim().equals(""))
                JOptionPane.showMessageDialog(null, "<html><center>Description required!", "User defined domains", JOptionPane.ERROR_MESSAGE);
            else if(txtPrimitive.getText().trim().equals("")&& btnPrimitive.isEnabled())
                JOptionPane.showMessageDialog(null, "<html><center>Primitive domain required!", "User defined domains", JOptionPane.ERROR_MESSAGE);
            else if(cmbParent.getSelectedItem().toString().trim().equals("")&& btnDomain.isEnabled())
                JOptionPane.showMessageDialog(null, "<html><center>User defined domain required!", "User defined domains", JOptionPane.ERROR_MESSAGE);
            else if(cmbDomain.isEnabled()&& cmbDomain.getSelectedItem().toString().trim().equals("") )
                JOptionPane.showMessageDialog(null, "<html><center>Domain required!", "User defined domains", JOptionPane.ERROR_MESSAGE);
            else if(rdComplex.isSelected() && !rdSet.isSelected() && lstAttList.getModel().getSize()==0)
                JOptionPane.showMessageDialog(null, "<html><center>Attribute list is empty!", "User defined domains", JOptionPane.ERROR_MESSAGE);
            else if(!Attribute.checkSyntax2(txtExpr,true,connection,0,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2).check)
                JOptionPane.showMessageDialog(null, "<html><center>Check expression syntax!", "User defined domains", JOptionPane.ERROR_MESSAGE);            
            else if(can){
                String len=new String();
                String decimal=new String();
                String parent=new String();
                String data=new String();
                if(txtLength.getText().equals("") || !txtLength.isEnabled())
                    len="null";
                else 
                    len=txtLength.getText();
                if(txtDecimal.getText().equals("") || !txtDecimal.isEnabled()) 
                    decimal="null";
                else 
                    decimal=txtDecimal.getText();
                
                parent="null";
                if(rdUser.isSelected()&& rdUser.isEnabled()){
                    rs1=query.select("select * from IISC_Domain where Dom_mnem='"+cmbParent.getSelectedItem().toString() +"' and PR_id="+tree.ID);
                    if(rs1.next())parent=rs1.getString("Dom_id");
                        query.Close();
                }
                data="null";
                if(rdPrimitive.isSelected()) {
                    rs1=query.select("select * from IISC_Primitive_type where PT_mnemonic='"+txtPrimitive.getText()+"'");
                    if(rs1.next())data=rs1.getString(1);
                        query.Close();
                }
                if(cmbDomain.isEnabled()){
                    data="null";
                    parent="null";
                    if(domain[cmbDomain.getSelectedIndex()]==0){
                        rs1=query.select("select * from IISC_Primitive_type where PT_mnemonic='"+cmbDomain.getSelectedItem().toString() +"'");
                        if(rs1.next())data=rs1.getString(1);
                            query.Close(); 
                    }
                    else{
                        rs1=query.select("select * from IISC_Domain where Dom_mnem='"+cmbDomain.getSelectedItem().toString() +"' and PR_id="+tree.ID);
                        if(rs1.next())parent=rs1.getString("Dom_id");
                            query.Close();  
                    }
                }
                int type=0;
                
                if(rdUser.isSelected() && rdUser.isEnabled()) type=1;
                else if(rdTuple.isSelected() && rdTuple.isEnabled()) type=2;
                else if(rdChoice.isSelected() && rdChoice.isEnabled()) type=3;
                else if(rdSet.isSelected() && rdSet.isEnabled()) type=4;
                if(Mnem.trim().equals("")){
                    rs1=query.select("select max(Dom_id)+1  from IISC_Domain");
                    if(rs1.next())
                        id=rs1.getInt(1);
                    else
                        id=0;
                
                    query.Close();
                
                    int i=query.update("insert into IISC_Domain(Dom_id,PR_id,Dom_mnem,Dom_name,Dom_type,Dom_data_type,Dom_length,Dom_reg_exp_str,Dom_parent,Dom_decimal,Dom_default,Dom_comment) values ("+ id +","+ tree.ID +", '" + txtName.getText().trim() + "','" + txtDescription.getText().trim() + "'," + type + "," + data + "," + len + ",'" + txtExpr.getText().trim().replaceAll("'","''") + "'," + parent + "," + decimal + ",'" + txtDefault.getText() + "','" + txtComment.getText().trim() + "')");
                    tree.insert(txtName.getText().trim(),"User defined domains");
                    Mnem=txtName.getText().trim();
                }
                else{
                    query.update("delete from IISC_DOM_ATT where Dom_id=" + id + ""); 
                    query.update("update IISC_Domain set Dom_mnem='" + txtName.getText().trim() + "',Dom_name='" + txtDescription.getText().trim() + "',Dom_data_type=" + data + ",Dom_type=" + type + ",Dom_length=" + len + ",Dom_reg_exp_str='" + txtExpr.getText().trim().replaceAll("'","''") + "',Dom_parent=" + parent + ",Dom_decimal=" + decimal + ",Dom_default='" + txtDefault.getText() + "',Dom_comment='" + txtComment.getText().trim() + "' where Dom_id=" + id + ""); 
                    if(type==2 || type==3){
                        for(int i=0;i<lstAttList.getModel().getSize();i++){
                            rs1=query.select("select *  from IISC_ATTRIBUTE where  ATT_mnem='"+ model1.getElementAt(i).toString() +"' and PR_id="+ tree.ID);
                            if(rs1.next()){
                                int a=rs1.getInt(1);
                                query.Close();
                                query.update("insert into IISC_DOM_ATT(Dom_id,Att_id,Pr_id, Att_rbr) values ("+ id +","+ a +","+ tree.ID +","+ (i+1) +")");
                            }
                            else
                                query.Close();
                        }
                    }
                    tree.change(Mnem ,"User defined domains",txtName.getText().trim());
                    Mnem=txtName.getText().trim();
                }
                if(k==1){
                    cp.Dom_id = id;
                    cp.Update();
                    JOptionPane.showMessageDialog(null, "<html><center>Domain saved!", "User defined domains", JOptionPane.INFORMATION_MESSAGE);
                }
                setDomain(Mnem);
                if(dispose)
                    dispose();
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
    
    public void setDomain (String m)
    {    
        try
        { 
            btnApply.setEnabled(false);
            btnSave.setEnabled(false);
            txtName.setText(m);
            txtPrimitive.setText("");
            ResultSet rs,rs1;
            JDBCQuery query=new JDBCQuery(connection);
            rs=query.select("select count(*) from IISC_DOMAIN where PR_ID="+ tree.ID);
            rs.next();
            int f=rs.getInt(1);
            query.Close();
            String[] sad=query.selectArray("select * from IISC_DOMAIN where PR_ID="+ tree.ID,f,3);
            sad[0]="";
            query.Close();
            can=false;
            cmbDom.removeAllItems();
            for(int k=0;k<sad.length; k++)
            cmbDom.addItem(sad[k]);
            cmbDom.setSelectedItem(m);
            can=true;
            Mnem=m;
            int type=-1;
            if(Mnem.equals(""))tree.select_node(Mnem,"User defined domains");
            
            String prim=new String(); 
            String dompar=new String("null");
            rs1=query.select("select * from IISC_Domain where PR_id="+ tree.ID +"  and Dom_mnem='"+ Mnem +"'");
            if(rs1.next())
            {   
                txtName.setText(rs1.getString("Dom_mnem"));
                txtDescription.setText(rs1.getString("Dom_name"));
                id=rs1.getInt("Dom_id");
                String pt=rs1.getString("Dom_data_type");
                type=rs1.getInt("Dom_type");
                txtComment.setText(rs1.getString("Dom_comment"));
                txtDecimal.setText(rs1.getString("Dom_decimal"));
                txtLength.setText(rs1.getString("Dom_length"));
                txtDefault.setText(rs1.getString("Dom_default"));
                if(txtLength.getText().equals("null"))txtLength.setText("");
                if(txtDecimal.getText().equals("null"))txtDecimal.setText("");
                String tmps = rs1.getString("Dom_reg_exp_str");
                if(tmps != null)
                    txtExpr.setText(tmps.trim());
                else
                    txtExpr.setText("");
                //checkSyntax(true);
                Attribute.checkSyntax2(txtExpr,true,connection,0,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2);
                                
                dompar=rs1.getString("Dom_parent"); 
                query.Close();
                rs=query.select("select * from IISC_Domain where  PR_id="+ tree.ID +" and  Dom_id="+ dompar +" ");
                
                if(rs.next())
                    dompar=rs.getString("Dom_mnem");
                else
                    dompar="";
            
                query.Close();
                
                if(type==0)
                {
                    rdInheritance.setSelected(true);
                    rdPrimitive.setSelected(true);
                }
                else if(type==1)
                {
                  rdInheritance.setSelected(true);
                  rdUser.setSelected(true);
                }
                else if(type==2)
                {
                    rdComplex.setSelected(true);
                    rdTuple.setSelected(true);
                }
                else if(type==3)
                {
                  rdComplex.setSelected(true);
                  rdChoice.setSelected(true);
                }
                else if(type==4)
                {
                    rdComplex.setSelected(true);
                    rdSet.setSelected(true);
                }
                rs=query.select("select count(*) from IISC_DOM_ATT,IISC_ATTRIBUTE where  IISC_DOM_ATT.Dom_id="+ id +" and IISC_DOM_ATT.ATT_id=IISC_ATTRIBUTE.ATT_id");
                int i=0;
                if(rs.next())
                  i=rs.getInt(1);
                query.Close();
                model1.removeAllElements();
                
                String[] atta=query.selectArray("select Att_mnem from IISC_DOM_ATT,IISC_ATTRIBUTE where  IISC_DOM_ATT.Dom_id="+ id +" and IISC_DOM_ATT.ATT_id=IISC_ATTRIBUTE.ATT_id  order by Att_rbr",i,1);
              
                for(i=0;i<atta.length;i++)
                    if(!atta[i].equals(""))
            
                model1.addElement(atta[i]);
            
                query.Close();
            
                rs=query.select("select * from IISC_Primitive_type where  PT_id="+ pt +"");
                if(rs.next()){
                    prim=rs.getString("PT_mnemonic");
                    txtPrimitive.setText(prim);
                    txtDecimal.setEnabled(false);
                    String decimal=rs.getString("PT_decimal");
                    if(decimal!=null) 
                        if(!decimal.equals(""))
                            txtDecimal.setEnabled(true);   
                    txtLength.setEnabled(false);
                    String length=rs.getString("PT_length");
                    if(length!=null)
                        if(!length.equals(""))
                        txtLength.setEnabled(true);         
                }
                query.Close();
                if(type==4 ){ 
                for(i=0;i< cmbParent.getItemCount() ;i++)
                    if(domain[i]==0 && cmbParent.getItemAt(i).toString().equals(prim))
                        cmbParent.setSelectedIndex(i);
                }     
            }
            else{
                txtDefault.setText("");
                txtName.setText("");
                txtDescription.setText("");
                txtPrimitive.setText("");
                cmbParent.setSelectedItem("");
                txtComment.setText("");
                txtDecimal.setText("");
                txtLength.setText("");
                txtExpr.setText("");
                txtLength.setEnabled(false);
                txtDecimal.setEnabled(false);
                query.Close();
                id = -1;
            }
            setAtt();
            setChecks();
            Reload();
            if(dompar!=null){
                if(type!=4 ){
                    cmbParent.setSelectedItem(dompar);
                }
                else{ 
                    for(int i=0;i< cmbDomain.getItemCount() ;i++)
                        if((domain[i]==1 && cmbDomain.getItemAt(i).toString().equals(dompar)) ||domain[i]==0 && cmbDomain.getItemAt(i).toString().equals(prim))
                            cmbDomain.setSelectedIndex(i);
                }
            }
            rs1=query.select("select * from IISC_Domain  where PR_id="+ tree.ID +" and Dom_mnem>'" + m.trim() + "'" );
            if(rs1.next()){
                btnNext.setEnabled(true);
                btnLast.setEnabled(true);
            }
            else
            {
                btnNext.setEnabled(false);
                btnLast.setEnabled(false);
            }
            query.Close();
            rs1=query.select("select * from IISC_Domain where PR_id="+ tree.ID +" and  Dom_mnem<'" + m.trim() + "'" );
            if(rs1.next()){
                btnPrev.setEnabled(true);
                btnFirst.setEnabled(true);
            }
            else{
                btnPrev.setEnabled(false);
                btnFirst.setEnabled(false);
            }
            query.Close();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void setAtt(){    
        try{ 
            ResultSet rs,rs1;
            JDBCQuery query=new JDBCQuery(connection);
            rs=query.select("select count(*) from IISC_ATTRIBUTE where  PR_id="+ tree.ID+"");
            int i=0;
            if(rs.next())
                i=rs.getInt(1);
            query.Close();
            //Set s=new HashSet();
            //Set s1=new HashSet();
            
            String att1[]=query.selectArray("select Att_mnem from IISC_ATTRIBUTE where  PR_id="+ tree.ID+"   order by Att_mnem",i,1);
            model.removeAllElements();
            for(i=0;i<att1.length;i++)
                if(!model1.contains(att1[i]))
                    model.addElement(att1[i]);
        }
        catch(SQLException e){   
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void erase_ActionPerformed(ActionEvent e){
        if(delete()){
            tree.removenode(Mnem,"User defined domains");
            Mnem="";
            setDomain(Mnem);
            tree.select_node(Mnem,"User defined domains");
        }
    }


    public boolean delete(){
    
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;  
        boolean can=true;
        if(Mnem.equals(""))
            can=false;
        try{
            rs1=query.select("select * from IISC_Domain  where Dom_parent="+ id +"");
            if(rs1.next()){ 
                JOptionPane.showMessageDialog(null, "<html><center>Domain can not be removed!", "Error", JOptionPane.ERROR_MESSAGE);
                can=false;
            } 
            query.Close();
            rs1=query.select("select * from IISC_Attribute  where Dom_id="+ id +"");
            if(rs1.next()){ 
                JOptionPane.showMessageDialog(null, "<html><center>Domain can not be removed!", "Error", JOptionPane.ERROR_MESSAGE);
                can=false;
            } 
            query.Close();
            if(can){
                int i=query.update("delete from IISC_Domain where Dom_id=" + id + ""); 
                i=query.update("delete from IISC_DOMAIN_DISPLAY where Dom_id=" + id + ""); 
                i=query.update("delete from IISC_DOM_DISPLAY_VALUES where Dom_id=" + id + ""); 
                JOptionPane.showMessageDialog(null, "<html><center>Domain removed!", "User defined domains", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return can;
    }


    private void btnHelp_actionPerformed(ActionEvent e){
        Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
        Settings.Center(hlp);
        hlp.setVisible(true);
    }


    private void apply_ActionPerformed(ActionEvent e){
        dispose=false;
        update(1);
        tree.select_node(Mnem,"User defined domains");
    }

    private void jComboBox1_actionPerformed(ActionEvent e){
        try{
            if(!can)return;
            if(btnSave.isEnabled()){
                if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "User defined domains", JOptionPane.YES_NO_OPTION)==0){
                    //cp.Update();
                    update(1);
                }
            }
            String s="";
            if(cmbDom.getSelectedItem()!=null)
                s=cmbDom.getSelectedItem().toString();
            setDomain(s);
            if (cp != null){
                jPanel7.remove(cp);
                cp = new ControlPanel(jTabbedPane1.getWidth() - 50, 350, connection, (IISFrameMain)getParent(), id, this, tree.ID);
                cp.setBounds(20, 15 , jTabbedPane1.getWidth() - 40, 350);
                jPanel7.add(cp);
                jPanel7.repaint();
            }
            tree.select_node(Mnem,"User defined domains");
            Mnem=s;
            }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }


    private void txtName_keyTyped(KeyEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }

    private void txtDescription_keyTyped(KeyEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }

    private void txtExpr_keyTyped(KeyEvent e)
    {
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }

    private void txtLength_keyTyped(KeyEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void txtDecimal_keyTyped(KeyEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void txtComment_keyTyped(KeyEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void cmbParent_mouseClicked(MouseEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }

    private void cmbParent_keyPressed(KeyEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }


  private void domain_ActionPerformed(ActionEvent e)
  {
      btnApply.setEnabled(true);
      btnSave.setEnabled(true);  
      SearchTable ptype=new SearchTable((Frame)getParent(),"Select User defined domain",true,connection);
      Settings.Center(ptype);
      ptype.type="Parent domain";
      ptype.owner=this;
      ptype.setVisible(true);
  }


    private void btnRemove_actionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }


    private void setChecks(){ 
        if(!rdTuple.isSelected()&& !rdChoice.isSelected()){
            model1.removeAllElements();
            setAtt();
        }
        
        cmbDomain.setEnabled(false);
        btnDomainPrimitive.setEnabled(false);
        btnDomainUser.setEnabled(false);
        cmbDomain.setEnabled(false);
        btnDown.setEnabled(false);
        btnAdd.setEnabled(false);
        btnRem.setEnabled(false);
        lstAtt.setEnabled(false);
        lstAttList.setEnabled(false);
        txtDecimal.setEnabled(false);
        txtLength.setEnabled(false);
        if(rdPrimitive.isSelected()){
            btnPrimitive.setEnabled(true);
            cmbParent.setEnabled(false);
            btnDomain.setEnabled(false);
        }
        else{
            btnPrimitive.setEnabled(false);
            cmbParent.setEnabled(true);
            btnDomain.setEnabled(true);
        }
        if(rdInheritance.isSelected()){
            rdPrimitive.setEnabled(true); 
            rdUser.setEnabled(true); 
            rdTuple.setEnabled(false);
            rdSet.setEnabled(false);
            rdChoice.setEnabled(false);
            if(rdPrimitive.isSelected()){            
                btnPrimitive.setEnabled(true);
                cmbParent.setEnabled(false);
                btnDomain.setEnabled(false);     
                txtDecimal.setEnabled(true);
                txtLength.setEnabled(true);
            }
            else{ 
                btnPrimitive.setEnabled(false);
                cmbParent.setEnabled(true);
                btnDomain.setEnabled(true);
            }
        }
        else{
            btnPrimitive.setEnabled(false);
            cmbParent.setEnabled(false);
            btnDomain.setEnabled(false);
            rdPrimitive.setEnabled(false); 
            rdUser.setEnabled(false); 
            rdTuple.setEnabled(true);
            rdSet.setEnabled(true);
            rdChoice.setEnabled(true);
            if(rdTuple.isSelected() || rdChoice.isSelected()){
                btnUp.setEnabled(true);
                btnDown.setEnabled(true);
                btnAdd.setEnabled(true);
                btnRem.setEnabled(true);
                lstAtt.setEnabled(true);
                lstAttList.setEnabled(true);
                if(rdTuple.isSelected())
                    jLabel17.setText("Tuple attributes");
                else 
                    jLabel17.setText("Choice attributes");
            }
            else{
                btnDomainPrimitive.setEnabled(true);
                btnDomainUser.setEnabled(true);
                cmbDomain.setEnabled(true);
            }
        }
    }

    private void rdInheritance_actionPerformed(ActionEvent e){
        setChecks();
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void rdPrimitive_actionPerformed(ActionEvent e){
        setChecks();
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void rdUser_actionPerformed(ActionEvent e){ 
        setChecks();
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void rdTuple_actionPerformed(ActionEvent e){ 
        setChecks();
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }

    private void rdChoice_actionPerformed(ActionEvent e){ 
        setChecks();
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void rdSet_actionPerformed(ActionEvent e){ 
        setChecks();
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void rdPrimitiveDomain_actionPerformed(ActionEvent e){ 
        setChecks();
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void rdUserDomain_actionPerformed(ActionEvent e){ 
        setChecks();
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void rdComplex_actionPerformed(ActionEvent e){
        setChecks();
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }

    private void btnAdd_actionPerformed(ActionEvent e){
        model1.addElement(lstAtt.getSelectedValue().toString());
        setAtt();
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
   
    private void btnRem_actionPerformed(ActionEvent e){
        model1.removeElement(lstAttList.getSelectedValue());
        setAtt();
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    
    private void txtDefault_actionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }

    private void up_ActionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
        if(!lstAttList.isSelectionEmpty())
            if(lstAttList.getSelectedIndex()>0){
                String pom=lstAttList.getSelectedValue().toString();
                model1.setElementAt(model1.getElementAt(lstAttList.getSelectedIndex()-1),lstAttList.getSelectedIndex());
                model1.setElementAt(pom,lstAttList.getSelectedIndex()-1 );
                lstAttList.setSelectedIndex(lstAttList.getSelectedIndex()-1);
            }
    }

    private void down_ActionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
        if(!lstAttList.isSelectionEmpty())
            if(lstAttList.getSelectedIndex()< lstAttList.getModel().getSize()-1){
                String pom=lstAttList.getSelectedValue().toString();
                model1.setElementAt(model1.getElementAt(lstAttList.getSelectedIndex()+1),lstAttList.getSelectedIndex());
                model1.setElementAt(pom,lstAttList.getSelectedIndex()+1 );
                lstAttList.setSelectedIndex(lstAttList.getSelectedIndex()+1);
            }
    }

    private void btnDomainPrimitive_actionPerformed(ActionEvent e){    
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);  
        SearchTable ptype=new SearchTable((Frame)getParent(),"Select Primitive Type",true,connection);
        Settings.Center(ptype);
        ptype.item=cmbDomain;
        ptype.type="Primitive Type";
        ptype.owner=this;
        ptype.setVisible(true);
    }

    private void btnDomainUser_actionPerformed(ActionEvent e){   
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);  
        SearchTable ptype=new SearchTable((Frame)getParent(),"Select User defined domain",true,connection);
        Settings.Center(ptype);
        ptype.item=cmbDomain;
        ptype.type="Parent domain";
        ptype.owner=this;
        ptype.setVisible(true);
    }


    private void jButton1_actionPerformed(ActionEvent e) {
        if(id == -1){
            JOptionPane.showMessageDialog(null, "<html><center>Save Domain First!", "Error", JOptionPane.ERROR_MESSAGE);    
        }
        else if( Attribute.checkSyntax2(txtExpr,true,connection,0,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2).check ){
            CheckConstraintExpressionEditor a= new CheckConstraintExpressionEditor((Frame) p,"Function",true,connection,/*tree,*/id,0,txtExpr,txtName.getText().toUpperCase(),"",String.valueOf(tree.ID));
            a.setVisible(true);
        }
    }

    private void chkButton_actionPerformed(ActionEvent e) {
        Attribute.checkSyntax2(txtExpr,true,connection,0,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2);
    }
    
/*    public boolean checkSyntax(boolean ch){
        try {
            int caretPosition = txtExpr.getCaretPosition();
            
            if( ch)
                btnApply.setEnabled(true);
            if(txtExpr.getText().trim().compareTo("") == 0)
                return true;
            
            String output = "";
            ByteArrayInputStream byte_in = new ByteArrayInputStream (txtExpr.getText().toUpperCase().getBytes());
            InputStream is = byte_in;                

            ANTLRInputStream input = new ANTLRInputStream(is);
            // Create an ExprLexer that feeds from that stream
            CheckConstraintExpressionLexer lexer = new CheckConstraintExpressionLexer(input);
            // Create a stream of tokens fed by the lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // Create a parser that feeds off the token stream
            //Object aaa2[] = tokens.getTokens().toArray();
            CheckConstraintExpressionParser parser = new CheckConstraintExpressionParser(tokens);
            parser.typeOfParser = 0;
            parser.con = connection;

            parser.rootNodeName = txtName.getText().toUpperCase();
            parser.treeID = String.valueOf(tree.ID);
            parser.ID = String.valueOf(id);
            

            // Begin parsing at rule prog
           
            Object aaa2[] = tokens.getTokens().toArray();

            StyleContext context = new StyleContext();
            StyledDocument document = new DefaultStyledDocument(context);
            Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
            
            if(ch){
                output = parser.project();
                
                //jScrollPaneErr.setVisible(false);
                model2.removeAllElements();
                if(parser.getMyError().length() > 7){
                for(int h=0; h < parser.getMyError().split("#!#E#!#").length && ch; h++){
                    JLabel tmpl = new JLabel(parser.getMyError().split("#!#E#!#")[h]);
                    tmpl.setBorder(BorderFactory.createLineBorder(new Color(243,243,243), 1));                
                    model2.addElement(tmpl);
                }
                }
                else if(ch){
                    JLabel tmpl = new JLabel("Succes");
                    tmpl.setBorder(BorderFactory.createLineBorder(new Color(243,243,243), 1));             
                    model2.addElement(tmpl);
                }

                //System.out.println("ERR:::" + parser.getMyError() );
                if(parser.getMyError().length() > 7 && ch){
                    //jScrollPaneErr.setVisible(true);
                    btnApply.setEnabled(false);
                    return false;
                }                
            }
            
            document.insertString(0, txtExpr.getText(), null);
            String line = txtExpr.getText();
            for(int s=0;s<tokens.getTokens().toArray().length; s++){
                String text = "";
                int f =  Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]);
                int l =  Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1] ) + 1;
                
                text = ("'" + line.substring(f,l) + "'").toUpperCase() ;
                
                //System.out.println(text);

                if( text.compareTo("'AND'") == 0 ||
                    text.compareTo("'OR'") == 0 ||
                    text.compareTo("'XOR'") == 0 ||
                    text.compareTo("'<=>'") == 0 ||
                    text.compareTo("'=>'") == 0){
                    
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setItalic(attributes, true);
                    StyleConstants.setForeground(attributes, Color.red);                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }
                else if( text.compareTo("'TRUE'") == 0 ||
                    text.compareTo("'FALSE'") == 0 ||
                    text.compareTo("'NULL'") == 0 ){
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setBold(attributes, true);
                    StyleConstants.setForeground(attributes, Color.BLUE);                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }
                else if( text.compareTo("'VALUE'") == 0 ||
                    text.compareTo("'THIS'") == 0 ){
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setBold(attributes, true);
                    StyleConstants.setForeground(attributes, new Color(0,214,0));                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }                
                else if( text.compareTo("'IN'") == 0  ){
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setBold(attributes, true);
                    StyleConstants.setItalic(attributes, true);
                    StyleConstants.setForeground(attributes, new Color(255,181,99));                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }  
                else if( text.compareTo("'LIKE'") == 0  ){
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setBold(attributes, true);
                    StyleConstants.setItalic(attributes, true);
                    StyleConstants.setForeground(attributes, new Color(148,0,148));                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }    
                else if(text.length() > 3 &&
                        text.substring(0,2).compareTo("''") == 0 && 
                        text.substring(aaa2[s].toString().split(",")[1].split("=")[1].length() - 2,aaa2[s].toString().split(",")[1].split("=")[1].length()).compareTo("''") == 0 ){

                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setUnderline(attributes, true);
                    StyleConstants.setForeground(attributes, new Color(159,159,159));
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);                    

                }
            }
            txtExpr.setDocument(document);
            //System.out.println("pos:" + caretPosition);
            txtExpr.setCaretPosition(caretPosition);
            
            //System.out.println("XML::::::::\n\n"+output+"\n\nXML::::::::\n");                
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }  
        return true;
    }
    */

    private void txtExpr_keyReleased(KeyEvent e) {

        //System.out.println("aaaaaaaaaaaaaaa::" + e.getKeyCode());
        if( e.getKeyCode() == 16 || e.getKeyCode() == 17 || e.getKeyCode() == 18 || e.getKeyCode() == 37 || 
            e.getKeyCode() == 38 || e.getKeyCode() == 39 || e.getKeyCode() == 40 || e.getKeyCode() == 33 ||
            e.getKeyCode() == 34 || e.getKeyCode() == 35 || e.getKeyCode() == 36 || /*e.getKeyCode() == 127 || */
            e.getKeyCode() == 155 || e.getKeyCode() == 10 )
            return;
        
        Attribute.checkSyntax2(txtExpr,false,connection,0,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2);
    }


    private void txtExpr_keyPressed(KeyEvent e) {
        if( e.getKeyCode() == 113){
        
            //if(ddList != null)
            //    txtExpr.remove(ddList);
            //jScrollPane1.setVisible(true);
            //jList1.requestFocus();
            
/*            int x = 2;
            int y = 15;
            if(txtExpr.getCaret().getMagicCaretPosition() != null){
                x = (int)txtExpr.getCaret().getMagicCaretPosition().getX() + 2;
                y = (int)txtExpr.getCaret().getMagicCaretPosition().getY() + 15;
            }

            if(x + 200 > txtExpr.getWidth())
                x = txtExpr.getWidth() - 200 - 2;
                
            if(y + 50 > txtExpr.getHeight())
                y = y - 80 - 15;
*/
            int x = (int)jScrollPane3.getBounds().getX();
            int y = (int)jScrollPane3.getLocation().getY() + (int)jScrollPane3.getSize().getHeight();
            ddList.setData(null);
            //ddList.setBounds( x, y, 200, 80 );
            ddList.setBounds( x , y + 4, jScrollPane3.getWidth() , 110 );
            ddList.setVisible(true);
            ddList.requestFocus();
        }      
    }

    private void txtExpr_focusGained(FocusEvent e) {
       Attribute.checkSyntax2(txtExpr,false,connection,0,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2);
    }

    private void txtExpr_mouseClicked(MouseEvent e) {
        if(ddList != null)
            ddList.setVisible(false);
    }
}
