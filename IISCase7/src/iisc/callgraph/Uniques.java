package iisc.callgraph;

import iisc.*;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.util.*;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import java.awt.event.*;
import javax.swing.JLabel;
import java.sql.*;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import java.awt.event.WindowEvent;
public class Uniques extends JDialog implements ActionListener
{
  public JButton btnClose = new JButton();

  private JButton btnRemoveUnique = new JButton();
  private Connection con;
  private Form form;
  private ButtonGroup bgrp=new ButtonGroup();
  private int ID,FID,TOB;
  public String dialog=new String();
  private JButton btnHelp = new JButton();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private ImageIcon iconUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
  private ImageIcon iconDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
  private JButton btnAddUnique = new JButton();
  private JTable table = new JTable();
   private JList lstAtt = new JList();
  private JList lstAttUnique = new JList();
  private JButton btnRemm = new JButton();
  private JButton btnRem = new JButton();
  private JButton btnAdd = new JButton();
  private JButton btmAddd = new JButton();
  private JList lstUnique = new JList();
  private JButton btnDown = new JButton();
  private JButton btnUp = new JButton();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JList lstUnique1 = new JList();
  private JList lstAttUnique1 = new JList();
  private JList lstAtt1 = new JList();
  private JTable table1 = new JTable();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel13 = new JLabel();
  private JLabel lbObject = new JLabel();
  
  public Uniques()
  {
    this(null, "",-1, false,null,null);
  }

  public Uniques(IISFrameMain parent, String title,int m1, boolean modal, Connection conn, Form frm )
  {
    super(parent, title, modal);
    try
    { 
      TOB=m1;
      form=frm;
      con=conn;
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  { this.setResizable(false);
    this.setSize(new Dimension(521, 442));
    this.getContentPane().setLayout(null);
    this.setTitle("Component Type Uniques");
    this.addWindowListener(new WindowAdapter()
      {
        public void windowClosed(WindowEvent e)
        {
          this_windowClosed(e);
        }
      });
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Close");
    btnClose.setBounds(new Rectangle(385, 375, 80, 30));
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.setFont(new Font("SansSerif", 0, 11));
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          close_ActionPerformed(ae);
        }
      });
    btnRemoveUnique.setMaximumSize(new Dimension(50, 30));
    btnRemoveUnique.setPreferredSize(new Dimension(50, 30));
    btnRemoveUnique.setText("Remove Unique");
    btnRemoveUnique.setBounds(new Rectangle(295, 100, 110, 30));
    btnRemoveUnique.setMinimumSize(new Dimension(50, 30));
    btnRemoveUnique.setFont(new Font("SansSerif", 0, 11));
    btnRemoveUnique.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRemoveUnique_actionPerformed(e);
        }
      });
   

    
    btnHelp.setBounds(new Rectangle(470, 375, 35, 30));
    btnHelp.setIcon(imageHelp);
    btnHelp.setFont(new Font("SansSerif", 0, 11));
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    btnAddUnique.setMaximumSize(new Dimension(50, 30));
    btnAddUnique.setPreferredSize(new Dimension(50, 30));
    btnAddUnique.setText("Add Unique");
    btnAddUnique.setBounds(new Rectangle(295, 60, 110, 30));
    btnAddUnique.setMinimumSize(new Dimension(50, 30));
    btnAddUnique.setFont(new Font("SansSerif", 0, 11));
    btnAddUnique.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnAddUnique_actionPerformed(e);
        }
      });
  
    table.setRowSelectionAllowed(true);
    table.setGridColor(new Color(0, 0, 0));
    table.setBackground(Color.white);
    table.setAutoResizeMode(0);
    table.setAutoscrolls(true);
    table.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setBounds(new Rectangle(0, 0, 0, 0));
    lstAtt.setBounds(new Rectangle(15, 215, 195, 150));
    lstAtt.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lstAtt.setFont(new Font("SansSerif", 0, 11));
    lstAttUnique.setBounds(new Rectangle(280, 215, 190, 145));
    lstAttUnique.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lstAttUnique.setFont(new Font("SansSerif", 0, 11));
    btnRemm.setBounds(new Rectangle(220, 220, 50, 30));
    btnRemm.setText("<<");
    btnRemm.setFont(new Font("SansSerif", 0, 14));
    btnRemm.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRemm_actionPerformed(e);
        }
      });
    btnRem.setBounds(new Rectangle(220, 255, 50, 30));
    btnRem.setText("<");
    btnRem.setFont(new Font("SansSerif", 0, 14));
    btnRem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRem_actionPerformed(e);
        }
      });
    btnAdd.setBounds(new Rectangle(220, 290, 50, 30));
    btnAdd.setText(">");
    btnAdd.setFont(new Font("SansSerif", 0, 14));
    btnAdd.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnAdd_actionPerformed(e);
        }
      });
    btmAddd.setBounds(new Rectangle(220, 325, 50, 30));
    btmAddd.setText(">>");
    btmAddd.setFont(new Font("SansSerif", 0, 14));
    btmAddd.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btmAddd_actionPerformed(e);
        }
      });
    lstUnique.setBounds(new Rectangle(80, 55, 205, 85));
    lstUnique.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lstUnique.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    lstUnique.setFont(new Font("SansSerif", 0, 11));
    lstUnique.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          lstUnique_mouseClicked(e);
        }
      });
    btnDown.setMaximumSize(new Dimension(60, 60));
    btnDown.setPreferredSize(new Dimension(25, 20));
    btnDown.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          down_ActionPerformed(ae);
        }
      });
    btnDown.setBounds(new Rectangle(475, 340, 25, 20));
    btnDown.setIcon(iconDown);
    btnDown.setFont(new Font("SansSerif", 0, 11));
    btnUp.setMaximumSize(new Dimension(60, 60));
    btnUp.setPreferredSize(new Dimension(25, 20));
    btnUp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          up_ActionPerformed(ae);
        }
      });
    btnUp.setBounds(new Rectangle(475, 215, 25, 20));
    btnUp.setIcon(iconUp);
    btnUp.setFont(new Font("SansSerif", 0, 11));
    jLabel1.setText("<html>Attributes not in the Unique <br> (checked as the existing in a database schema)");
    jLabel1.setBounds(new Rectangle(15, 180, 250, 35));
    jLabel1.setFont(new Font("SansSerif", 0, 11));
    jLabel2.setText("Attributes in the Unique");
    jLabel2.setBounds(new Rectangle(0, 0, 0, 0));
    jLabel2.setFont(new Font("SansSerif", 0, 11));
    lstUnique1.setBounds(new Rectangle(0, 0, 0, 0));
    lstUnique1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lstUnique1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    lstUnique1.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          lstUnique_mouseClicked(e);
        }
      });
    lstAttUnique1.setBounds(new Rectangle(0, 0, 0, 0));
    lstAttUnique1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lstAtt1.setBounds(new Rectangle(0, 0, 0, 0));
    lstAtt1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    table1.setRowSelectionAllowed(true);
    table1.setGridColor(new Color(0, 0, 0));
    table1.setBackground(Color.white);
    table1.setAutoResizeMode(0);
    table1.setAutoscrolls(true);
    table1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table1.setBounds(new Rectangle(0, 0, 0, 0));
    jLabel3.setText("Attributes in the Unique");
    jLabel3.setBounds(new Rectangle(280, 190, 165, 20));
    jLabel3.setFont(new Font("SansSerif", 0, 11));
    jLabel4.setText("Unique list");
    jLabel4.setBounds(new Rectangle(15, 55, 165, 20));
    jLabel4.setFont(new Font("SansSerif", 0, 11));
    jLabel13.setText("Component Type:");
    jLabel13.setBounds(new Rectangle(15, 20, 85, 20));
    jLabel13.setFont(new Font("SansSerif", 0, 11));
    lbObject.setBounds(new Rectangle(105, 20, 295, 20));
    lbObject.setFont(new Font("SansSerif", 1, 11));
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.getContentPane().add(lbObject, null);
    this.getContentPane().add(jLabel13, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(table1, null);
    this.getContentPane().add(lstAtt1, null);
    this.getContentPane().add(lstAttUnique1, null);
    this.getContentPane().add(lstUnique1, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(btnUp, null);
    this.getContentPane().add(btnDown, null);
    this.getContentPane().add(lstUnique, null);
    this.getContentPane().add(btmAddd, null);
    this.getContentPane().add(btnAdd, null);
    this.getContentPane().add(btnRem, null);
    this.getContentPane().add(btnRemm, null);
    this.getContentPane().add(lstAttUnique, null);
    this.getContentPane().add(lstAtt, null);
    this.getContentPane().add(table, null);
    this.getContentPane().add(btnAddUnique, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnRemoveUnique, null);
    this.getContentPane().add(btnClose, null);
    setUniques();
    lstUnique.setSelectedIndex(0);
     setUnique(lstUnique.getSelectedIndex()+1);
   // lstAtt.setListData(setAtt("")); 
    }

  private void close_ActionPerformed(ActionEvent e)
  { 
  this.dispose();
  }
  public void actionPerformed(ActionEvent e)
  {
  }
  
   private void setUnique(int s)
  {
  Object[] attUnique=setAtt("" + s);  
  Object[] att=setAtt("");  
  Set at=new HashSet();
  for(int i=0; i<att.length ; i++)
   {boolean can=true;
   for(int j=0; j<attUnique.length ; j++)
   if(att[i].equals(attUnique[j]))can=false;
   if(can)
   at.add(att[i]);
  }
  att=at.toArray();
  lstAtt.setListData(att);
  lstAttUnique.setListData(attUnique);
   }


  private void btnHelp_actionPerformed(ActionEvent e)
  { Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con);
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void setUniques()
  {
  try {
  
   JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
  ResultSet rs1;
  rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where PR_id="+ form.tree.ID +" and  TF_ID="+ form.id +" and TOB_id="+TOB);
  if(rs.next())
  {lbObject.setText(rs.getString("Tob_mnem"));
  query.Close();
    rs=query.select("select count(*) from IISC_Unique_TOB where PR_ID="+ form.tree.ID +" and  TF_ID="+ form.id +" and  TOB_ID="+ TOB );
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] sa=query.selectArray("select * from IISC_Unique_TOB where PR_ID="+ form.tree.ID +" and  TF_ID="+ form.id +" and  TOB_ID="+ TOB +" order by TOB_rbrk" ,j,3);
    sa[0]="";
    String[]  sak=new String[sa.length-1];
    for (int k=1;k<sa.length;k++)sak[k-1]="Unique no. " + k;
    query.Close();
    lstUnique.setListData(sak);
    lstAtt.setListData(new String[0]);
    lstAttUnique.setListData(new String[0]);
  }
   else
 query.Close();
  }
 catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);} 
  }
  
   private String[] setAtt(String s)
  {
  try {
   JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
  ResultSet rs1;
   if(s.equals(""))
    rs=query.select("select count(*) from IISC_ATTRIBUTE,IISC_ATT_TOB where Att_sbp=1 and IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and IISC_ATT_TOB.PR_ID="+ form.tree.ID +" and  IISC_ATT_TOB.TF_ID="+ form.id +" and  IISC_ATT_TOB.TOB_ID="+ TOB );
   else
   rs=query.select("select count(*) from IISC_ATTRIBUTE,IISC_ATT_UTO where Att_sbp=1 and  IISC_ATTRIBUTE.Att_id=IISC_ATT_UTO.Att_id and  IISC_ATT_UTO.PR_ID="+ form.tree.ID +" and  IISC_ATT_UTO.TF_ID="+ form.id +" and  IISC_ATT_UTO.TOB_ID="+ TOB + " and TOB_rbrk="+ s );
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] sa,sa1;
    if(s.equals(""))
    sa=query.selectArray("select * from IISC_ATTRIBUTE,IISC_ATT_TOB where  Att_sbp=1 and IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and  IISC_ATT_TOB.PR_ID="+ form.tree.ID +" and  IISC_ATT_TOB.TF_ID="+ form.id +" and  IISC_ATT_TOB.TOB_ID="+ TOB +" order by Att_mnem" ,j,3);
    else
    {sa=query.selectArray("select  Att_mnem from IISC_ATTRIBUTE,IISC_ATT_UTO where Att_sbp=1 and  IISC_ATTRIBUTE.Att_id=IISC_ATT_UTO.Att_id and  IISC_ATT_UTO.PR_ID="+ form.tree.ID +" and  IISC_ATT_UTO.TF_ID="+ form.id +" and  IISC_ATT_UTO.TOB_ID="+ TOB +" and TOB_rbrk="+ s +"  order by Att_rbrk" ,j,1);
    sa[0]="";
    }
    String[]  sak=new String[sa.length-1];
    for (int k=1;k<sa.length;k++)sak[k-1]=sa[k];
    return sa;
  }
 catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);} 
return null;
  }

  private void lstUnique_mouseClicked(MouseEvent e)
  {
   setUnique(lstUnique.getSelectedIndex()+1);
  }

  private void btnAddUnique_actionPerformed(ActionEvent e)
  { 
  try {
   JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
  int Unique=1;
  rs=query.select("select max(Tob_rbrk)+1 from IISC_Unique_TOB  where   PR_id="+ form.tree.ID +" and TOB_id="+ TOB +" and Tf_id="+ form.id );
  if(rs.next())Unique=rs.getInt(1);
  if(Unique==0)Unique=1;
  query.Close();
  query.update("insert into IISC_Unique_TOB(Tf_id,Tob_id,Tob_rbrk,PR_id) values("+ form.id +","+ TOB +","+ Unique +","+ form.tree.ID +")");
  setUniques();
  setUnique(Unique);
  }
 catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);} 
}

  private void btnRemoveUnique_actionPerformed(ActionEvent e)
  {  
 try{
 JDBCQuery query=new JDBCQuery(con);
 ResultSet rs;
 if(lstUnique.getSelectedIndex()>=0)
{int Unique=lstUnique.getSelectedIndex()+1;
query.update("delete  from IISC_ATT_UTO   where  TF_id="+ form.id +" and TOB_id="+ TOB +"   and PR_id="+ form.tree.ID +" and TOB_rbrk="+ Unique +"");
query.update("delete from IISC_Unique_TOB   where  TF_id="+ form.id +" and TOB_id="+ TOB +"   and PR_id="+ form.tree.ID +" and TOB_rbrk="+ Unique +"");
query.update("update IISC_Unique_TOB set  TOB_rbrk=TOB_rbrk-1 where  TF_id="+ form.id +"  and TOB_id="+ TOB +"  and PR_id="+ form.tree.ID +" and TOB_rbrk>"+ Unique +"");
query.update("update IISC_ATT_UTO set  TOB_rbrk=TOB_rbrk-1 where  TF_id="+ form.id +" and TOB_id="+ TOB +" and PR_id="+ form.tree.ID +" and TOB_rbrk>"+ Unique +"");
 }
 setUniques();
}
catch(Exception ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}

  }

  private void btnRemm_actionPerformed(ActionEvent e)
  { if(!lstUnique.isSelectionEmpty())
  {
  if(lstUnique.getSelectedIndex()>=0)
  {try{
  JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
  lstAtt.setListData(setAtt(""));
  lstAttUnique.setListData(new String[0]);
  query.update("delete  from IISC_ATT_UTO   where  TF_id="+ form.id +" and TOB_id="+ TOB +"  and PR_id="+ form.tree.ID +" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +"");
  }
catch(Exception ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}  
}
  }
  }
private void btmAddd_actionPerformed(ActionEvent e)
  { if(!lstUnique.isSelectionEmpty())
  {
  try{
  int fi=-1;
  JDBCQuery query=new JDBCQuery(con);
 ResultSet rs,rs1;
  lstAtt.setListData(new String[0]);
  lstAttUnique.setListData(setAtt(""));
  for(int m=0;m< lstAttUnique.getModel().getSize() ;m++)
 { rs1=query.select("select * from IISC_ATT_TOB,IISC_ATTRIBUTE  where  IISC_ATT_TOB.PR_id="+ form.tree.ID +" and IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id and  Att_mnem='"+ lstAttUnique.getModel().getElementAt(m) +"'  and Tf_id="+ form.id );
if(rs1.next())fi=rs1.getInt(1);
else fi=0;
query.Close();
rs1=query.select("select * from IISC_ATT_UTO  where  TF_id="+ form.id +" and TOB_id="+ TOB +"  and PR_id="+ form.tree.ID +" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +" and Att_id=" +fi );
if(!rs1.next())
{query.Close();
int rb=1;
rs1=query.select("select max(Att_rbrk)+1 from IISC_ATT_UTO  where  TF_id="+ form.id +" and TOB_id="+ TOB +"  and PR_id="+ form.tree.ID +" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +"");
if(rs1.next())rb=rs1.getInt(1);
if(rb==0)rb=1;
query.Close();
query.update("insert into IISC_ATT_UTO(Att_id,Tf_id,Tob_id,PR_id,Tob_rbrk,Att_rbrk) values("+ fi +","+ form.id +","+ TOB +","+ form.tree.ID +","+ (lstUnique.getSelectedIndex()+1) +","+ rb +")");
}
else
query.Close();
 }
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
  }
  }

  private void btnRem_actionPerformed(ActionEvent e)
  {
  if(!lstUnique.isSelectionEmpty())
  {
  Object[] att=lstAttUnique.getSelectedValues();
  for(int j=0;j<att.length ;j++)
  {
  try{
  JDBCQuery query=new JDBCQuery(con);
  ResultSet rs1;
  rs1=query.select("select IISC_ATTRIBUTE.Att_id, Att_rbrk from IISC_ATT_TOB,IISC_ATTRIBUTE,IISC_ATT_UTO  where IISC_ATT_TOB.Tob_id=IISC_ATT_UTO.Tob_id and IISC_ATT_TOB.PR_id=IISC_ATT_UTO.PR_id and IISC_ATT_TOB.TF_id=IISC_ATT_UTO.TF_id and IISC_ATT_TOB.Att_id=IISC_ATT_UTO.Att_id and IISC_ATT_TOB.PR_id="+ form.tree.ID +" and IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id and  Att_mnem='"+ att[j].toString() +"'  and IISC_ATT_TOB.Tf_id="+ form.id );
if(rs1.next())
{int fi=rs1.getInt(1);
int rb=rs1.getInt(2);
query.Close();
  query.update("delete  from IISC_ATT_UTO   where  TF_id="+ form.id +" and TOB_id="+ TOB +"  and PR_id="+ form.tree.ID +" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +" and Att_id="+fi);
  query.update("update IISC_ATT_UTO set Att_rbrk=Att_rbrk-1   where Att_rbrk> "+ rb +" and  TF_id="+ form.id +" and TOB_id="+ TOB +"  and PR_id="+ form.tree.ID +" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +"");
  
  setUnique(lstUnique.getSelectedIndex()+1);
}
 else
 query.Close();
  }
catch(Exception ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}  
  }
  }
  }

  private void btnAdd_actionPerformed(ActionEvent e)
  { if(!lstUnique.isSelectionEmpty())
  { 
  Object[] att=lstAtt.getSelectedValues();
  for(int j=0;j<att.length ;j++)
  {
  try{
  JDBCQuery query=new JDBCQuery(con);
  ResultSet rs1;
  int fi=-1,rb=-1;
  rs1=query.select("select * from IISC_ATT_TOB,IISC_ATTRIBUTE  where  IISC_ATT_TOB.PR_id="+ form.tree.ID +" and IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id and  Att_mnem='"+ att[j].toString() +"'  and Tf_id="+ form.id );
if(rs1.next())fi=rs1.getInt(1);
query.Close();
rs1=query.select("select max(Att_rbrk)+1 from IISC_ATT_UTO  where  TF_id="+ form.id +" and TOB_id="+ TOB +"  and PR_id="+ form.tree.ID +" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +"");
if(rs1.next())rb=rs1.getInt(1);
if(rb==0)rb=1;
query.Close();
query.update("insert into IISC_ATT_UTO(Att_id,Tf_id,Tob_id,PR_id,Tob_rbrk,Att_rbrk) values("+ fi +","+ form.id +","+ TOB +","+ form.tree.ID +","+ (lstUnique.getSelectedIndex()+1) +","+ rb +")");
  setUnique(lstUnique.getSelectedIndex()+1);
  }
catch(Exception ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}  
  }
  }
  }

  private void down_ActionPerformed(ActionEvent e)
  {
  try{
  JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
  int fi=-1,rb=-1;
   int f=lstAttUnique.getSelectedIndex();
  rs=query.select("select * from IISC_ATT_UTO where  IISC_ATT_UTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +" and Att_rbrk>"+  lstAttUnique.getSelectedIndex()  +" order by Att_rbrk");
  if(rs.next())
 {int k=rs.getInt(1); 
 query.Close();
 rs=query.select("select Att_id from IISC_ATT_UTO where  IISC_ATT_UTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +" and Att_rbrk="+  lstAttUnique.getSelectedIndex()  +" ");
 if(rs.next())
 {
int j=rs.getInt(1); 
 query.Close();
query.update("update IISC_ATT_UTO set Att_rbrk="+ lstAttUnique.getSelectedIndex() +" where Att_id="+ k +" and   IISC_ATT_UTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +"");
query.update("update IISC_ATT_UTO set Att_rbrk="+ (lstAttUnique.getSelectedIndex()+1)  + " where Att_id="+ j +" and   IISC_ATT_UTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +"");
 f=lstAttUnique.getSelectedIndex()+1;
 } 
 else
 query.Close();
 }
  else
 query.Close();
 setUnique(lstUnique.getSelectedIndex()+1);
 lstAttUnique.setSelectedIndex(f);
  }
catch(Exception ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}  
  }

  private void up_ActionPerformed(ActionEvent e)
  {
    try{
  JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
  int fi=-1,rb=-1;
 int f=lstAttUnique.getSelectedIndex();
  rs=query.select("select * from IISC_ATT_UTO where  IISC_ATT_UTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +" and Att_rbrk<"+  lstAttUnique.getSelectedIndex()  +" order by Att_rbrk desc");
  if(rs.next())
 {int k=rs.getInt(1); 
 query.Close();
 rs=query.select("select Att_id from IISC_ATT_UTO where  IISC_ATT_UTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +" and Att_rbrk="+  lstAttUnique.getSelectedIndex()  +" ");
 if(rs.next())
 {
int j=rs.getInt(1); 
 query.Close();
query.update("update IISC_ATT_UTO set Att_rbrk="+ lstAttUnique.getSelectedIndex() +" where Att_id="+ k +" and   IISC_ATT_UTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +"");
query.update("update IISC_ATT_UTO set Att_rbrk="+ (lstAttUnique.getSelectedIndex()-1)  + " where Att_id="+ j +" and   IISC_ATT_UTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstUnique.getSelectedIndex()+1) +"");
 f=lstAttUnique.getSelectedIndex()-1;
 } 
 else
 query.Close();
 }
setUnique(lstUnique.getSelectedIndex()+1);
 lstAttUnique.setSelectedIndex(f);
  }
catch(Exception ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}  
  }

  private void this_windowClosed(WindowEvent e)
  {form.qtmatt.setQueryA("select * from IISC_ATT_TOB where TF_id ="+ form.id +" and TOB_id=" + TOB );
  }

 


}
  