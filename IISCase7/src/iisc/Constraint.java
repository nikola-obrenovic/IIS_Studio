package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import java.awt.event.*;
import javax.swing.JLabel;
import java.sql.*;
import javax.swing.JRadioButton;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Color;
import java.awt.SystemColor;
public class Constraint extends JDialog implements ActionListener
{
  public JButton btnClose = new JButton();

  private ButtonGroup bgrp=new ButtonGroup();
  private ButtonGroup bgrp1=new ButtonGroup();
  private ButtonGroup bgrp2=new ButtonGroup();
  private JButton btnSave = new JButton();
  private Connection con;
  private Form form;
  private int ID,FID;
  public String dialog=new String();
  private JButton btnHelp = new JButton();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  private JButton btnApply = new JButton();
  private JLabel lbConName = new JLabel();
  private JLabel jLabel11 = new JLabel();
  private JLabel lbConType = new JLabel();
  private JLabel jLabel13 = new JLabel();
 
  private JLabel jLabel16 = new JLabel();
  private JList lstReferenced1 = new JList();
  private JList lstReferencing1 = new JList();
  private JComboBox cmbType = new JComboBox();
  private JLabel jLabel17 = new JLabel();
  private JLabel jLabel18 = new JLabel();
  private JLabel jLabel19 = new JLabel();
  private int pr;
  private int as;
  private JLabel jLabel112 = new JLabel();
  private JScrollPane jScrollPane5 = new JScrollPane();
  private JTextArea txtDescription = new JTextArea();
  private JScrollPane jScrollPane6 = new JScrollPane();
  private JTable tableReferencing = new JTable();
  private JScrollPane jScrollPane7 = new JScrollPane();
  private JTable tableReferenced = new JTable();
  private JScrollPane jScrollPane8 = new JScrollPane();
  private JTable tableReferencingAtt = new JTable();
  private JScrollPane jScrollPane9 = new JScrollPane();
  private JTable tableReferencedAtt = new JTable();
  public QueryTableModel qtmreferencing,qtmreferenced,qtmreferencedatt,qtmreferencingatt;
  private JLabel jLabel110 = new JLabel();
  private JComboBox cmbInsertReferencing = new JComboBox();
  private JComboBox cmbFunInsertReferencing = new JComboBox();
  private JComboBox cmbFunUpdateReferenced = new JComboBox();
  private JComboBox cmbUpdateReferenced = new JComboBox();
  private JComboBox cmbFunDeleteReferenced = new JComboBox();
  private JComboBox cmbDeleteReferenced = new JComboBox();
  private JComboBox cmbFunUpdateReferencing = new JComboBox();
  private JComboBox cmbUpdateReferencing = new JComboBox();
  private JPanel jPanel1 = new JPanel();
  private JRadioButton rdDeff = new JRadioButton();
  private JRadioButton rdNotDEF = new JRadioButton();
  private JRadioButton rdInitDef = new JRadioButton();
  private JRadioButton rdInitIm = new JRadioButton();
  private JLabel jLabel1 = new JLabel();
 public Constraint()
  {
    this(null, "",-1, false,null,-1,-1);
  }

  public Constraint(IISFrameMain parent, String title,int m, boolean modal, Connection conn, int p , int a )
  {
    super(parent, title, modal);
    try
    { ID=m;
      pr=p;
      as=a;
      con=conn;
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {
    this.setResizable(false);
    qtmreferenced=new QueryTableModel(con,ID);
    tableReferenced=new JTable(qtmreferenced);
    tableReferenced.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   tableReferenced.setRowSelectionAllowed(true);
   tableReferenced.setGridColor(new Color(0,0,0));
   tableReferenced.setBackground(Color.white);
   tableReferenced.getTableHeader().setReorderingAllowed(false);
   tableReferenced.setAutoscrolls(true);
   tableReferenced.setBorder(BorderFactory.createLineBorder(Color.black, 1));
   tableReferenced.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    qtmreferencing=new QueryTableModel(con,ID);
    tableReferencing=new JTable(qtmreferencing);
    tableReferencing.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   tableReferencing.setRowSelectionAllowed(true);
   tableReferencing.setGridColor(new Color(0,0,0));
   tableReferencing.setBackground(Color.white);
   tableReferencing.getTableHeader().setReorderingAllowed(false);
   tableReferencing.setAutoscrolls(true);
   tableReferencing.setBorder(BorderFactory.createLineBorder(Color.black, 1));
   tableReferencing.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
   tableReferenced.addMouseListener(new MouseAdapter() {

   public void mouseClicked (MouseEvent me) {

    setConstraint();
 
    }
     
    });
    tableReferencing.addMouseListener(new MouseAdapter() {

   public void mouseClicked (MouseEvent me) {

    setConstraint();;
 
    }
     
    });

    qtmreferencingatt=new QueryTableModel(con,ID);
    tableReferencingAtt=new JTable(qtmreferencingatt);
    tableReferencingAtt.setEnabled(false);
    qtmreferencedatt=new QueryTableModel(con,ID);
    tableReferencedAtt=new JTable(qtmreferencedatt);
    tableReferencedAtt.setEnabled(false);
    
    this.getContentPane().setLayout(null);
 
   this.setSize(new Dimension(405, 674));
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Cancel");
    btnClose.setBounds(new Rectangle(270, 605, 80, 30));
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          close_ActionPerformed(ae);
        }
      });
    btnSave.setMaximumSize(new Dimension(50, 30));
    btnSave.setPreferredSize(new Dimension(50, 30));
    btnSave.setText("OK");
    btnSave.setBounds(new Rectangle(185, 605, 75, 30));
    btnSave.setMinimumSize(new Dimension(50, 30));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          apply_ActionPerformed(ae);
        }
      });
    
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    tableReferenced.addKeyListener(new KeyAdapter()
      {
        public void keyReleased(KeyEvent e)
        {
          setConstraint();
        }
      });
    tableReferencing.addKeyListener(new KeyAdapter()
      {
        public void keyReleased(KeyEvent e)
        {
          setConstraint();
        }
      });
    jLabel110.setText("On update:");
    jLabel110.setBounds(new Rectangle(205, 380, 55, 20));
    jLabel110.setFont(new Font("SansSerif", 0, 11));
    cmbInsertReferencing.setBounds(new Rectangle(65, 380, 130, 20));
    cmbInsertReferencing.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          rsNoaction_actionPerformed(e);
        }
      });
    cmbInsertReferencing.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          cmbInsertReferencing_actionPerformed(e);
        }
      });
    cmbFunInsertReferencing.setBounds(new Rectangle(15, 410, 180, 20));
    cmbFunInsertReferencing.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          rsNoaction_actionPerformed(e);
        }
      });
    cmbFunUpdateReferenced.setBounds(new Rectangle(210, 410, 180, 20));
    cmbFunUpdateReferenced.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          rsNoaction_actionPerformed(e);
        }
      });
    cmbUpdateReferenced.setBounds(new Rectangle(260, 380, 130, 20));
    cmbUpdateReferenced.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          rsNoaction_actionPerformed(e);
        }
      });
    cmbUpdateReferenced.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          cmbUpdateReferenced_actionPerformed(e);
        }
      });
    cmbFunDeleteReferenced.setBounds(new Rectangle(210, 475, 180, 20));
    cmbFunDeleteReferenced.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          rsNoaction_actionPerformed(e);
        }
      });
    cmbDeleteReferenced.setBounds(new Rectangle(260, 445, 130, 20));
    cmbDeleteReferenced.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          rsNoaction_actionPerformed(e);
        }
      });
    cmbDeleteReferenced.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          cmbDeleteReferenced_actionPerformed(e);
        }
      });
    cmbFunUpdateReferencing.setBounds(new Rectangle(15, 475, 180, 20));
    cmbFunUpdateReferencing.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          rsNoaction_actionPerformed(e);
        }
      });
    cmbUpdateReferencing.setBounds(new Rectangle(65, 445, 130, 20));
    cmbUpdateReferencing.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          rsNoaction_actionPerformed(e);
        }
      });
    cmbUpdateReferencing.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          cmbUpdateReferencing_actionPerformed(e);
        }
      });
    jPanel1.setBounds(new Rectangle(10, 510, 380, 85));
    jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(SystemColor.activeCaptionBorder, 1));
    jPanel1.setLayout(null);
    rdDeff.setText("DEFERRABLE");
    rdDeff.setFont(new Font("SansSerif", 0, 11));
    rdDeff.setBounds(new Rectangle(20, 30, 165, 20));
    rdDeff.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          rsNoaction_actionPerformed(e);
        }
      });
    rdNotDEF.setText("NOT DEFERRABLE");
    rdNotDEF.setFont(new Font("SansSerif", 0, 11));
    rdNotDEF.setBounds(new Rectangle(20, 55, 165, 20));
    rdNotDEF.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rdNotDEF_actionPerformed(e);
        }
      });
    rdNotDEF.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          rsNoaction_actionPerformed(e);
        }
      });
    rdInitDef.setText("INITIALLY DEFERRED");
    rdInitDef.setFont(new Font("SansSerif", 0, 11));
    rdInitDef.setBounds(new Rectangle(215, 30, 160, 20));
    rdInitDef.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          rsNoaction_actionPerformed(e);
        }
      });
    rdInitIm.setText("INITIALLY IMMEDIATE ");
    rdInitIm.setFont(new Font("SansSerif", 0, 11));
    rdInitIm.setBounds(new Rectangle(215, 55, 155, 20));
    rdInitIm.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          rsNoaction_actionPerformed(e);
        }
      });
    jLabel1.setText("Constraint Deferrability");
    jLabel1.setBounds(new Rectangle(15, 5, 140, 15));
    jLabel1.setFont(new Font("SansSerif", 1, 11));
    
    this.setFont(new Font("SansSerif", 0, 11));
    btnSave.setEnabled(false);
     btnHelp.setBounds(new Rectangle(360, 605, 35, 30));
    btnHelp.setIcon(imageHelp);
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    btnApply.setMaximumSize(new Dimension(50, 30));
    btnApply.setPreferredSize(new Dimension(50, 30));
    btnApply.setText("Apply");
    btnApply.setBounds(new Rectangle(100, 605, 75, 30));
    btnApply.setMinimumSize(new Dimension(50, 30));
    btnApply.setEnabled(false);
    btnApply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save_ActionPerformed(ae);
        }
      });
    lbConName.setFont(new Font("Dialog", 1, 11));
    lbConName.setBounds(new Rectangle(90, 5, 285, 20));
    jLabel11.setText("Constraint:");
    jLabel11.setBounds(new Rectangle(10, 5, 85, 20));
    jLabel11.setFont(new Font("SansSerif", 0, 11));
    lbConType.setFont(new Font("Dialog", 1, 11));
    lbConType.setBounds(new Rectangle(90, 30, 285, 20));
    jLabel13.setText("Type:");
    jLabel13.setBounds(new Rectangle(10, 30, 85, 20));
    jLabel13.setFont(new Font("SansSerif", 0, 11));
    jLabel16.setText("Referential integrity type:");
    jLabel16.setBounds(new Rectangle(10, 140, 130, 20));
    jLabel16.setFont(new Font("SansSerif", 0, 11));
    cmbType.setBounds(new Rectangle(145, 140, 245, 20));
    cmbType.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          rsNoaction_actionPerformed(e);
        }
      });
    jLabel17.setText("On delete:");
    jLabel17.setBounds(new Rectangle(205, 445, 55, 20));
    jLabel17.setFont(new Font("SansSerif", 0, 11));
    jLabel18.setText("On update:");
    jLabel18.setBounds(new Rectangle(10, 445, 55, 20));
    jLabel18.setFont(new Font("SansSerif", 0, 11));
    jLabel19.setText("On insert:");
    jLabel19.setBounds(new Rectangle(10, 380, 55, 20));
    jLabel19.setFont(new Font("SansSerif", 0, 11));
   
    jLabel112.setText("Description:");
    jLabel112.setBounds(new Rectangle(10, 55, 85, 20));
    jLabel112.setFont(new Font("SansSerif", 0, 11));
    jScrollPane5.setBounds(new Rectangle(10, 80, 380, 55));
    txtDescription.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtDescription_keyPressed(e);
        }
      });
    jScrollPane6.setBounds(new Rectangle(10, 180, 185, 90));
    jScrollPane6.setFont(new Font("SansSerif", 0, 11));
    jScrollPane7.setBounds(new Rectangle(205, 180, 185, 90));
    jScrollPane7.setFont(new Font("SansSerif", 0, 11));
    jScrollPane8.setBounds(new Rectangle(10, 275, 185, 80));
    jScrollPane8.setFont(new Font("SansSerif", 0, 11));
    tableReferencingAtt.setRowSelectionAllowed(true);
    tableReferencingAtt.setGridColor(new Color(0, 0, 0));
    tableReferencingAtt.setBackground(Color.white);
   
    tableReferencingAtt.setAutoscrolls(true);
    tableReferencingAtt.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    tableReferencingAtt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tableReferencedAtt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tableReferencingAtt.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent me)
        {
          doMouseClicked1(me);
        }
      });
    jScrollPane9.setBounds(new Rectangle(205, 275, 185, 80));
    jScrollPane9.setFont(new Font("SansSerif", 0, 11));
    jScrollPane6.getViewport();
    jScrollPane7.getViewport();
    jScrollPane8.getViewport();
    jScrollPane8.getViewport();
    tableReferencingAtt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tableReferencingAtt.getTableHeader();
    tableReferencingAtt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jScrollPane9.getViewport();
    jScrollPane9.getViewport();
    bgrp.add(rdDeff);
    bgrp.add(rdNotDEF);
    bgrp1.add(rdInitDef);
    bgrp1.add(rdInitIm);
    jScrollPane5.getViewport().add(txtDescription, null);
    jScrollPane9.getViewport().add(tableReferencedAtt, null);
    jPanel1.add(jLabel1, null);
    jPanel1.add(rdDeff, null);
    jPanel1.add(rdNotDEF, null);
    jPanel1.add(rdInitDef, null);
    jPanel1.add(rdInitIm, null);
    this.getContentPane().add(jPanel1, null);
    this.getContentPane().add(cmbUpdateReferencing, null);
    this.getContentPane().add(cmbFunUpdateReferencing, null);
    this.getContentPane().add(cmbDeleteReferenced, null);
    this.getContentPane().add(cmbFunDeleteReferenced, null);
    this.getContentPane().add(cmbUpdateReferenced, null);
    this.getContentPane().add(cmbFunUpdateReferenced, null);
    this.getContentPane().add(cmbFunInsertReferencing, null);
    this.getContentPane().add(cmbInsertReferencing, null);
    this.getContentPane().add(jLabel110, null);
    this.getContentPane().add(jScrollPane9, null);
    jScrollPane8.getViewport().add(tableReferencingAtt, null);
    this.getContentPane().add(jScrollPane8, null);
    jScrollPane7.getViewport().add(tableReferenced, null);
    this.getContentPane().add(jScrollPane7, null);
    jScrollPane6.getViewport().add(tableReferencing, null);
    this.getContentPane().add(jScrollPane6, null);
    this.getContentPane().add(jScrollPane5, null);
    this.getContentPane().add(jLabel112, null);
    this.getContentPane().add(jLabel19, null);
    this.getContentPane().add(jLabel18, null);
    this.getContentPane().add(jLabel17, null);
    this.getContentPane().add(cmbType, null);
    this.getContentPane().add(jLabel16, null);
    this.getContentPane().add(jLabel13, null);
    this.getContentPane().add(lbConType, null);
    this.getContentPane().add(jLabel11, null);
    this.getContentPane().add(lbConName, null);
    this.getContentPane().add(btnApply, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnSave, null);
    this.getContentPane().add(btnClose, null);
    setRS();
    setConstraint();
    }

  private void close_ActionPerformed(ActionEvent e)
  { 
   if(btnSave.isEnabled())
  {  if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
   save_ActionPerformed(e);
 }
  
   dispose(); 
  
  }
  public void actionPerformed(ActionEvent e)
  {
  }
  private void save_ActionPerformed(ActionEvent e)
  {
 try
    { 
 
    JDBCQuery query=new JDBCQuery(con);
     ResultSet rs;
     int lhs_ins=-1,rhs_del=-1,rhs_upd=-1,lhs_upd=-1;
       
     if(cmbInsertReferencing.getSelectedItem().equals("NO ACTION"))lhs_ins=-1;
     else  if(cmbInsertReferencing.getSelectedItem().equals("SET NULL"))lhs_ins=-2;
     else  if(cmbInsertReferencing.getSelectedItem().equals("SET DEFAULT"))lhs_ins=-3;
     else  if(cmbInsertReferencing.getSelectedItem().equals("CASCADE"))lhs_ins=-4;
     else  if(cmbInsertReferencing.getSelectedItem().equals("user defined function")&& cmbFunInsertReferencing.getItemCount()>0)
     {  rs=query.select("select  *  from IISC_FUNCTION where PR_ID="+ pr+ " and Fun_name='"+cmbFunInsertReferencing.getSelectedItem()+"'");
        if(rs.next())
        lhs_ins=rs.getInt("Fun_id");
        query.Close();
    }
    
    if(cmbUpdateReferencing.getSelectedItem().equals("NO ACTION"))lhs_upd=-1;
     else  if(cmbUpdateReferencing.getSelectedItem().equals("SET NULL"))lhs_upd=-2;
     else  if(cmbUpdateReferencing.getSelectedItem().equals("SET DEFAULT"))lhs_upd=-3;
     else  if(cmbUpdateReferencing.getSelectedItem().equals("CASCADE"))lhs_upd=-4;
     else  if(cmbUpdateReferencing.getSelectedItem().equals("user defined function") && cmbFunUpdateReferencing.getItemCount()>0)
     {  rs=query.select("select  *  from IISC_FUNCTION where PR_ID="+ pr+ " and Fun_name='"+cmbFunUpdateReferencing.getSelectedItem()+"'");
        if(rs.next())
        lhs_upd=rs.getInt("Fun_id");
        query.Close();
    }
    
       if(cmbUpdateReferenced.getSelectedItem().equals("NO ACTION"))rhs_upd=-1;
     else  if(cmbUpdateReferenced.getSelectedItem().equals("SET NULL"))rhs_upd=-2;
     else  if(cmbUpdateReferenced.getSelectedItem().equals("SET DEFAULT"))rhs_upd=-3;
     else  if(cmbUpdateReferenced.getSelectedItem().equals("CASCADE"))rhs_upd=-4;
     else  if(cmbUpdateReferenced.getSelectedItem().equals("user defined function") && cmbFunUpdateReferenced.getItemCount()>0)
     {  rs=query.select("select  *  from IISC_FUNCTION where PR_ID="+ pr+ " and Fun_name='"+cmbFunUpdateReferenced.getSelectedItem()+"'");
        if(rs.next())
        rhs_upd=rs.getInt("Fun_id");
        query.Close();
    }
    
       if(cmbDeleteReferenced.getSelectedItem().equals("NO ACTION"))rhs_del=-1;
     else  if(cmbDeleteReferenced.getSelectedItem().equals("SET NULL"))rhs_del=-2;
     else  if(cmbDeleteReferenced.getSelectedItem().equals("SET DEFAULT"))rhs_del=-3;
     else  if(cmbDeleteReferenced.getSelectedItem().equals("CASCADE"))rhs_del=-4;
     else  if(cmbDeleteReferenced.getSelectedItem().equals("user defined function") && cmbFunDeleteReferenced.getItemCount()>0)
     {  rs=query.select("select  *  from IISC_FUNCTION where PR_ID="+ pr+ " and Fun_name='"+cmbFunDeleteReferenced.getSelectedItem()+"'");
        if(rs.next())
        rhs_del=rs.getInt("Fun_id");
        query.Close();
    }
    int init=0;
    int def=0;
    if(rdDeff.isSelected())def=1;
    if(rdInitDef.isSelected())init=1;
    query.update("update IISC_RS_Constraint set RSC_desc='"+ txtDescription.getText().trim() +"',RSC_rel_int_type="+ cmbType.getSelectedIndex() +",RSC_deferrable="+def+", RSC_initially="+init +" where  RSC_id="+ ID +" and PR_id="+ pr +" and AS_id="+as);
    query.update("delete from IISC_RSC_action where RSC_id="+ ID +" and PR_id="+ pr +" and AS_id="+as + " and RS_id="+ tableReferencing.getValueAt(tableReferencing.getSelectedRow(),0));
    query.update("delete from IISC_RSC_action where RSC_id="+ ID +" and PR_id="+ pr +" and AS_id="+as + " and RS_id="+ tableReferenced.getValueAt(tableReferenced.getSelectedRow(),0));
    query.update("insert into IISC_RSC_action(RSC_id,PR_id,AS_id,RS_id,RSC_lhs_rhs,RSC_ins_action,RSC_mod_action,RSC_del_action) values("+ ID +","+ pr +","+as + ","+ tableReferenced.getValueAt(tableReferenced.getSelectedRow(),0)+",1,NULL,"+ rhs_upd +","+ rhs_del +")");
    query.update("insert into IISC_RSC_action(RSC_id,PR_id,AS_id,RS_id,RSC_lhs_rhs,RSC_ins_action,RSC_mod_action,RSC_del_action) values("+ ID +","+ pr +","+as + ","+ tableReferencing.getValueAt(tableReferencing.getSelectedRow(),0)+",0,"+ lhs_ins +","+ lhs_upd +",NULL)");
    setConstraint();
  btnApply.setEnabled(false);
  btnSave.setEnabled(false);
  JOptionPane.showMessageDialog(null, "<html><center>Constraint saved!", "Constraints", JOptionPane.INFORMATION_MESSAGE);
    }
    
      catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
}
 private void setRS()
 {
   
    qtmreferencing.setRSCQuery("select RS_name,IISC_RELATION_SCHEME.RS_id from IISC_RSC_RS_SET,IISC_RS_Constraint,IISC_RELATION_SCHEME where IISC_RS_Constraint.LHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id and IISC_RSC_RS_SET.RS_id=IISC_RELATION_SCHEME.RS_id  and  IISC_RS_Constraint.PR_ID="+ pr +" and IISC_RS_Constraint.AS_id="+as +" and IISC_RS_Constraint.RSC_id="+ID+" order by RS_order");
    qtmreferenced.setRSCQuery("select RS_name,IISC_RELATION_SCHEME.RS_id from IISC_RSC_RS_SET,IISC_RS_Constraint,IISC_RELATION_SCHEME where IISC_RS_Constraint.RHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id and IISC_RSC_RS_SET.RS_id=IISC_RELATION_SCHEME.RS_id  and  IISC_RS_Constraint.PR_ID="+ pr +" and IISC_RS_Constraint.AS_id="+as +" and IISC_RS_Constraint.RSC_id="+ID+" order by RS_order");
 
    tableReferenced.getColumnModel().getColumn(0).setPreferredWidth(0);
    tableReferenced.getColumnModel().getColumn(0).setWidth(0);
    tableReferenced.getColumnModel().getColumn(0).setMinWidth(0);
    tableReferenced.getColumnModel().getColumn(0).setMaxWidth(0);
    tableReferencing.getColumnModel().getColumn(0).setPreferredWidth(0);
    tableReferencing.getColumnModel().getColumn(0).setWidth(0);
    tableReferencing.getColumnModel().getColumn(0).setMinWidth(0);
    tableReferencing.getColumnModel().getColumn(0).setMaxWidth(0);
    
    tableReferenced.getColumnModel().getColumn(1).setHeaderValue("Referenced relation schemes"); 
    tableReferencing.getColumnModel().getColumn(1).setHeaderValue("Referencing relation schemes");
    
    tableReferencing.getColumnModel().getColumn(1).setPreferredWidth(185);
    tableReferencing.getColumnModel().getColumn(1).setWidth(185);
    tableReferencing.getColumnModel().getColumn(1).setMinWidth(185);
    tableReferencing.getColumnModel().getColumn(1).setMaxWidth(185);
    
    tableReferenced.getColumnModel().getColumn(1).setPreferredWidth(185);
    tableReferenced.getColumnModel().getColumn(1).setWidth(185);
    tableReferenced.getColumnModel().getColumn(1).setMinWidth(185);
    tableReferenced.getColumnModel().getColumn(1).setMaxWidth(185);
    
    tableReferenced.getSelectionModel().setSelectionInterval(0,0);
    tableReferencing.getSelectionModel().setSelectionInterval(0,0);
    
    qtmreferencingatt.image=1;
    qtmreferencedatt.image=1;
    String  rsquery=" IISC_RS_ATT.RS_id="+ tableReferencing.getValueAt(0,0);
    for(int k=1;k<tableReferencing.getRowCount();k++)
    rsquery=rsquery + " or IISC_RS_ATT.RS_id="+ tableReferencing.getValueAt(k,0);
    qtmreferencingatt.setQueryRSATT("select distinct(IISC_ATTRIBUTE.Att_id),Att_mnem,Att_null,Att_modifiable,Att_rbr from IISC_RSC_LHS_RHS,IISC_RS_Constraint,IISC_ATTRIBUTE,IISC_RS_ATT where IISC_RS_Constraint.LHS_id=IISC_RSC_LHS_RHS.RSCLR_id and IISC_RSC_LHS_RHS.Att_id=IISC_ATTRIBUTE.Att_id  and  IISC_RS_Constraint.PR_ID="+ pr +" and IISC_RS_Constraint.AS_id="+as +" and IISC_RS_Constraint.RSC_id="+ID+ " and IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id and (" + rsquery+ ") order by Att_rbr ");
    rsquery=" IISC_RS_ATT.RS_id="+ tableReferenced.getValueAt(0,0);
    for(int k=1;k<tableReferenced.getRowCount();k++)
    rsquery=rsquery + " or IISC_RS_ATT.RS_id="+ tableReferenced.getValueAt(k,0);
    qtmreferencedatt.setQueryRSATT("select distinct(IISC_ATTRIBUTE.Att_id),Att_mnem,Att_null,Att_modifiable,Att_rbr from IISC_RSC_LHS_RHS,IISC_RS_Constraint,IISC_ATTRIBUTE,IISC_RS_ATT where IISC_RS_Constraint.RHS_id=IISC_RSC_LHS_RHS.RSCLR_id and IISC_RSC_LHS_RHS.Att_id=IISC_ATTRIBUTE.Att_id  and  IISC_RS_Constraint.PR_ID="+ pr +" and IISC_RS_Constraint.AS_id="+as +" and IISC_RS_Constraint.RSC_id="+ID+ " and IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id and (" + rsquery+ ") order by Att_rbr ");
   
 }
   private void setConstraint()
  {
try
    { 
  JDBCQuery query=new JDBCQuery(con),query1=new JDBCQuery(con);
  ResultSet rs,rs1;
  Object[] obj=get_type_and_actions();
  String[] t=(String[])obj[0];
  String[] a=(String[])obj[1];
  
 cmbType.removeAllItems();
  if(t[0].equals("1"))
    cmbType.addItem("default");
  if(t[1].equals("1"))
    cmbType.addItem("partial");
  if(t[2].equals("1"))
    cmbType.addItem("full");
    
    cmbDeleteReferenced.removeAllItems();  
    cmbInsertReferencing.removeAllItems(); 
    cmbUpdateReferenced.removeAllItems(); 
    cmbUpdateReferencing.removeAllItems(); 
    
      /*
       if(a[0].equals("1"))cmbDeleteReferenced.addItem("NO ACTION");
       if(a[1].equals("1"))cmbDeleteReferenced.addItem("CASCADE");
       if(a[2].equals("1"))cmbDeleteReferenced.addItem("SET DEFAULT");
       if(a[3].equals("1"))cmbDeleteReferenced.addItem("SET NULL");
       cmbDeleteReferenced.addItem("user defined function");
       
       cmbInsertReferencing.addItem("NO ACTION");
       cmbInsertReferencing.addItem("CASCADE");
       cmbInsertReferencing.addItem("SET DEFAULT");
       cmbInsertReferencing.addItem("SET NULL");
       cmbInsertReferencing.addItem("user defined function");*/
       
       cmbDeleteReferenced.addItem("NO ACTION");
       cmbDeleteReferenced.addItem("SET NULL");
       cmbDeleteReferenced.addItem("SET DEFAULT");
       cmbDeleteReferenced.addItem("CASCADE");
       cmbDeleteReferenced.addItem("user defined function");
       
       cmbInsertReferencing.addItem("NO ACTION");
       cmbInsertReferencing.addItem("SET NULL");
       cmbInsertReferencing.addItem("SET DEFAULT");
       cmbInsertReferencing.addItem("user defined function");
       
       cmbUpdateReferencing.addItem("NO ACTION");
       cmbUpdateReferencing.addItem("SET NULL");
       cmbUpdateReferencing.addItem("SET DEFAULT");
       cmbUpdateReferencing.addItem("user defined function");
       
       cmbUpdateReferenced.addItem("NO ACTION");
       cmbUpdateReferenced.addItem("SET NULL");
       cmbUpdateReferenced.addItem("SET DEFAULT");
       cmbUpdateReferenced.addItem("CASCADE");
       cmbUpdateReferenced.addItem("user defined function");
       
        rs=query.select("select count(*) from IISC_FUNCTION where PR_ID="+ pr);
        rs.next();
        int j=rs.getInt(1);
        query.Close();
        String[] sa=query.selectArray1("select * from IISC_FUNCTION where PR_ID="+ pr,j,3);
        query.Close();
       
        cmbFunDeleteReferenced.removeAllItems();
        for(int k=0;k<sa.length; k++)
        cmbFunDeleteReferenced.addItem(sa[k]);
        cmbFunDeleteReferenced.setEnabled(false);
        
        cmbFunInsertReferencing.removeAllItems();
        for(int k=0;k<sa.length; k++)
        cmbFunInsertReferencing.addItem(sa[k]);
        cmbFunInsertReferencing.setEnabled(false);
        
        cmbFunUpdateReferenced.removeAllItems();
        for(int k=0;k<sa.length; k++)
        cmbFunUpdateReferenced.addItem(sa[k]);
        cmbFunUpdateReferenced.setEnabled(false);
        
        cmbFunUpdateReferencing.removeAllItems();
        for(int k=0;k<sa.length; k++)
        cmbFunUpdateReferencing.addItem(sa[k]);
        cmbFunUpdateReferencing.setEnabled(false);
    
      
      rs=query.select("select * from IISC_RS_Constraint,IISC_RSC_TYPE where IISC_RS_Constraint.RSC_type=IISC_RSC_TYPE.RSCT_id and RSC_id="+ ID +" and PR_id="+ pr +" and AS_id="+as);
      if(rs.next())
      { ID=rs.getInt(1);
       lbConName.setText(rs.getString("RSC_name"));
       txtDescription.setText(rs.getString("RSC_desc"));
       lbConType.setText(rs.getString("RSCT_type"));
       cmbType.setSelectedIndex(rs.getInt("RSC_rel_int_type"));
       if(rs.getInt("RSC_deferrable")==1)
       rdDeff.setSelected(true);
       else
        rdNotDEF.setSelected(true);
       if(rs.getInt("RSC_initially")==1)
       rdInitDef.setSelected(true);
       else
        rdInitIm.setSelected(true);
       int lhs_ins=-1,lhs_upd=-1,rhs_del=-1,rhs_upd=-1;
       rs1=query1.select("select * from IISC_RSC_ACTION where RS_id="+ tableReferencing.getValueAt(tableReferencing.getSelectedRow(),0) +" and RSC_id="+ ID +" and PR_id="+ pr +" and AS_id="+as+ " and RSC_lhs_rhs=0");
       if(rs1.next()){
        lhs_ins=rs1.getInt("RSC_ins_action");
        lhs_upd=rs1.getInt("RSC_mod_action");
       }
       query1.Close();
       rs1=query1.select("select * from IISC_RSC_ACTION where RS_id="+ tableReferenced.getValueAt(tableReferenced.getSelectedRow(),0) +" and RSC_id="+ ID +" and PR_id="+ pr +" and AS_id="+as+ " and RSC_lhs_rhs=1");
       if(rs1.next()){
        rhs_del=rs1.getInt("RSC_del_action");
        rhs_upd=rs1.getInt("RSC_mod_action");
       }
       query1.Close();
       query.Close();
       
       if(lhs_ins==-1)cmbInsertReferencing.setSelectedItem("NO ACTION");
       else if(lhs_ins==-2)cmbInsertReferencing.setSelectedItem("SET NULL");
       else if(lhs_ins==-3)cmbInsertReferencing.setSelectedItem("SET DEFAULT");
       else if(lhs_ins==-4)cmbInsertReferencing.setSelectedItem("CASCADE");
       else if(lhs_ins>=0)
       {cmbInsertReferencing.setSelectedItem("user defined function");
        rs=query.select("select  *  from IISC_FUNCTION where PR_ID="+ pr+ " and Fun_id="+lhs_ins);
        if(rs.next())
        {cmbFunInsertReferencing.setSelectedItem(rs.getString("Fun_name"));
         cmbFunInsertReferencing.setEnabled(true);
        }
        query.Close();
       }
       
       if(lhs_upd==-1)cmbUpdateReferencing.setSelectedItem("NO ACTION");
       else if(lhs_upd==-2)cmbUpdateReferencing.setSelectedItem("SET NULL");
       else if(lhs_upd==-3)cmbUpdateReferencing.setSelectedItem("SET DEFAULT");
       else if(lhs_upd==-4)cmbUpdateReferencing.setSelectedItem("CASCADE");
       else if(lhs_upd>=0)
       {cmbUpdateReferencing.setSelectedItem("user defined function");
        rs=query.select("select  *  from IISC_FUNCTION where PR_ID="+ pr+ " and Fun_id="+lhs_upd);
        if(rs.next())
        {cmbFunUpdateReferencing.setSelectedItem(rs.getString("Fun_name"));
         cmbFunUpdateReferencing.setEnabled(true);
        }
        query.Close();
       }
       
        if(rhs_upd==-1)cmbUpdateReferenced.setSelectedItem("NO ACTION");
       else if(rhs_upd==-2)cmbUpdateReferenced.setSelectedItem("SET NULL");
       else if(rhs_upd==-3)cmbUpdateReferenced.setSelectedItem("SET DEFAULT");
       else if(rhs_upd==-4)cmbUpdateReferenced.setSelectedItem("CASCADE");
       else if(rhs_upd>=0)
       {cmbUpdateReferenced.setSelectedItem("user defined function");
        rs=query.select("select  *  from IISC_FUNCTION where PR_ID="+ pr+ " and Fun_id="+rhs_upd);
        if(rs.next())
        {cmbFunUpdateReferenced.setSelectedItem(rs.getString("Fun_name"));
         cmbFunUpdateReferenced.setEnabled(true);
        }
        query.Close();
       }
       
       if(rhs_del==-1)cmbDeleteReferenced.setSelectedItem("NO ACTION");
       else if(rhs_del==-2)cmbDeleteReferenced.setSelectedItem("SET NULL");
       else if(rhs_del==-3)cmbDeleteReferenced.setSelectedItem("SET DEFAULT");
       else if(rhs_del==-4)cmbDeleteReferenced.setSelectedItem("CASCADE");
       else if(rhs_del>=0)
       {cmbDeleteReferenced.setSelectedItem("user defined function");
        rs=query.select("select  *  from IISC_FUNCTION where PR_ID="+ pr+ " and Fun_id="+rhs_del);
        if(rs.next())
        {cmbFunDeleteReferenced.setSelectedItem(rs.getString("Fun_name"));
         cmbFunDeleteReferenced.setEnabled(true);
        }
        query.Close();
       }
       
      }
      else
       query.Close();
   btnApply.setEnabled(false);
  btnSave.setEnabled(false);
    }
      catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  }
 private Object[] get_type_and_actions()
  { Object[] obj=new Object[2];
    String[] sort=new String[3];
    String[] action=new String[4];
    
    JDBCQuery query=new JDBCQuery(con),query1=new JDBCQuery(con);
     ResultSet rs,rs1;
     Synthesys Syn=new Synthesys();
     Syn.pr=pr;
     Syn.as=as;
     Syn.con=con;
     Set K=new HashSet();
     Set O=new HashSet();
     Set X=new HashSet();
   try
    {rs=query.select("select * from IISC_RS_Constraint,IISC_RSC_LHS_RHS where IISC_RS_Constraint.LHS_id=IISC_RSC_LHS_RHS.RSCLR_id and  IISC_RS_Constraint.RSC_id="+ ID +" and IISC_RS_Constraint.PR_id="+ pr +" and IISC_RS_Constraint.AS_id="+as);
      while(rs.next())
      {
       X.add(rs.getString("Att_id")) ;
      }
      query.Close();
     rs=query.select("select IISC_RSC_RS_SET.RS_id from IISC_RS_Constraint,IISC_RSC_RS_SET where IISC_RS_Constraint.LHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id and  IISC_RS_Constraint.RSC_id="+ ID +" and IISC_RS_Constraint.PR_id="+ pr +" and IISC_RS_Constraint.AS_id="+as);
      while(rs.next())
      { RelationScheme rel=Syn.getRelationScheme(rs.getInt(1));
        Iterator it=rel.kljuc.iterator();
        while(it.hasNext())
        {
          K.addAll((Set)it.next());
        }
         it=rel.atributi.iterator();
        while(it.hasNext())
        {
        rs1=query1.select("select * from IISC_ATTRIBUTE,IISC_RS_ATT where IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id and IISC_RS_ATT.Att_id="+ it.next() +" and IISC_RS_ATT.PR_id="+ pr +" and IISC_RS_ATT.AS_id="+as+" and IISC_RS_ATT.RS_id="+rel.id);
        if(rs1.next())
        if(rs1.getInt("Att_null")==1)
        O.add(rs1.getString("Att_id"));
        query1.Close();
        }
      }
      query.Close();
     if((X.size()>1 && Syn.Presjek(X,O).isEmpty()) || X.size()==1)
     {sort[0]="1";sort[1]="";sort[2]="";}
     else
     {
      if(O.containsAll(X))
      {
        sort[0]="1";sort[1]="1";sort[2]="1";
      }
      else
      {
        sort[0]="1";sort[1]="1";sort[2]="";
      }
     }
     if(!Syn.Presjek(X,K).isEmpty())
     {
       action[0]="1";action[1]="1";action[2]="";action[3]="";
     }
     else
     {
       if(!Syn.Razlika(X,O).isEmpty())
       {
          action[0]="1"; action[1]="1";action[2]="1";action[3]="";
       }
       else
       {
          action[0]="1";action[1]="1"; action[2]="1";action[3]="1";
       }
     }
    obj[0]=sort;
    obj[1]=action;
   }
      catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  return obj;
  }

  
  private void btnHelp_actionPerformed(ActionEvent e)
  {
 Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void apply_ActionPerformed(ActionEvent e)
  {
  save_ActionPerformed(e);
  dispose();
  }




  private void rsNoaction_actionPerformed(MouseEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void doMouseClicked1(MouseEvent e)
  {
  }

  private void doMouseClicked(MouseEvent e)
  {
  }

  private void cmbInsertReferencing_actionPerformed(ActionEvent e)
  {if(cmbInsertReferencing.getItemCount()>0)
  if(cmbInsertReferencing.getSelectedItem().equals("user defined function"))
  cmbFunInsertReferencing.setEnabled(true);
  }

  private void cmbUpdateReferenced_actionPerformed(ActionEvent e)
  {if(cmbUpdateReferenced.getItemCount()>0 )
  if( cmbUpdateReferenced.getSelectedItem().equals("user defined function"))
  cmbFunUpdateReferenced.setEnabled(true);
  }

  private void cmbDeleteReferenced_actionPerformed(ActionEvent e)
  {
  if(cmbDeleteReferenced.getItemCount()>0)
  if(cmbDeleteReferenced.getSelectedItem().equals("user defined function"))
  cmbFunDeleteReferenced.setEnabled(true);
  }

  private void cmbUpdateReferencing_actionPerformed(ActionEvent e)
  {if(cmbUpdateReferencing.getItemCount()>0)
  if(cmbUpdateReferencing.getSelectedItem().equals("user defined function"))
  cmbFunUpdateReferencing.setEnabled(true);
  }

  private void txtDescription_keyPressed(KeyEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void rsNoaction_actionPerformed(ItemEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  setchecks();
  }
 public void setchecks()
 {
   if(rdDeff.isSelected())
   {
     rdInitDef.setEnabled(true);
   }
   else
    {rdInitIm.setSelected(true);
     rdInitDef.setEnabled(false);
   }
 }
  private void rdNotDEF_actionPerformed(ActionEvent e)
  {
  }

  

 
}