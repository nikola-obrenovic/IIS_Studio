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
import javax.swing.JRadioButton;
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
public class Keys extends JDialog implements ActionListener
{
  public JButton btnClose = new JButton();

  private JButton btnRemoveKey = new JButton();
  private Connection con;
  private Form form;
  private ButtonGroup bgrp=new ButtonGroup();
  private int ID,FID,TOB;
  public String dialog=new String();
  private JButton btnHelp = new JButton();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private ImageIcon iconUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
  private ImageIcon iconDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
  private JButton btnAddKey = new JButton();
  private JTable table = new JTable();
   private JList lstAtt = new JList();
  private JList lstAttKey = new JList();
  private JButton btnRemm = new JButton();
  private JButton btnRem = new JButton();
  private JButton btnAdd = new JButton();
  private JButton btmAddd = new JButton();
  private JList lstKey = new JList();
  private JButton btnDown = new JButton();
  private JButton btnUp = new JButton();
  private JLabel jLabel2 = new JLabel();
  private JList lstKey1 = new JList();
  private JList lstAttKey1 = new JList();
  private JList lstAtt1 = new JList();
  private JTable table1 = new JTable();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel13 = new JLabel();
  private JLabel lbObject = new JLabel();
  private JRadioButton rdGlobal = new JRadioButton();
  private JRadioButton rdLocal = new JRadioButton();
  private JLabel jLabel1 = new JLabel();
  
  public Keys()
  {
    this(null, "",-1, false,null,null);
  }

  public Keys(IISFrameMain parent, String title,int m1, boolean modal, Connection conn, Form frm )
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
  {  this.setResizable(false);
    this.setSize(new Dimension(521, 442));
    this.getContentPane().setLayout(null);
    this.setTitle("Component Type Keys");
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
    btnRemoveKey.setMaximumSize(new Dimension(50, 30));
    btnRemoveKey.setPreferredSize(new Dimension(50, 30));
    btnRemoveKey.setText("Remove Key");
    btnRemoveKey.setBounds(new Rectangle(280, 100, 100, 30));
    btnRemoveKey.setMinimumSize(new Dimension(50, 30));
    btnRemoveKey.setFont(new Font("SansSerif", 0, 11));
    btnRemoveKey.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRemoveKey_actionPerformed(e);
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
    btnAddKey.setMaximumSize(new Dimension(50, 30));
    btnAddKey.setPreferredSize(new Dimension(50, 30));
    btnAddKey.setText("Add Key");
    btnAddKey.setBounds(new Rectangle(280, 60, 100, 30));
    btnAddKey.setMinimumSize(new Dimension(50, 30));
    btnAddKey.setFont(new Font("SansSerif", 0, 11));
    btnAddKey.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnAddKey_actionPerformed(e);
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
    lstAttKey.setBounds(new Rectangle(280, 215, 190, 145));
    lstAttKey.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lstAttKey.setFont(new Font("SansSerif", 0, 11));
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
    lstKey.setBounds(new Rectangle(65, 55, 205, 85));
    lstKey.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lstKey.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    lstKey.setFont(new Font("SansSerif", 0, 11));
    lstKey.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          lstKey_mouseClicked(e);
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
    btnDown.setBounds(new Rectangle(480, 340, 25, 20));
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
    btnUp.setBounds(new Rectangle(480, 215, 25, 20));
    btnUp.setIcon(iconUp);
    btnUp.setFont(new Font("SansSerif", 0, 11));
    jLabel2.setText("Attributes in the key");
    jLabel2.setBounds(new Rectangle(0, 0, 0, 0));
    jLabel2.setFont(new Font("SansSerif", 0, 11));
    lstKey1.setBounds(new Rectangle(0, 0, 0, 0));
    lstKey1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lstKey1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    lstKey1.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          lstKey_mouseClicked(e);
        }
      });
    lstAttKey1.setBounds(new Rectangle(0, 0, 0, 0));
    lstAttKey1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
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
    jLabel3.setText("Attributes in the key");
    jLabel3.setBounds(new Rectangle(280, 190, 165, 20));
    jLabel3.setFont(new Font("SansSerif", 0, 11));
    jLabel4.setText("Key list");
    jLabel4.setBounds(new Rectangle(15, 55, 165, 20));
    jLabel4.setFont(new Font("SansSerif", 0, 11));
    jLabel13.setText("Component Type:");
    jLabel13.setBounds(new Rectangle(15, 20, 85, 20));
    jLabel13.setFont(new Font("SansSerif", 0, 11));
    lbObject.setBounds(new Rectangle(105, 20, 295, 20));
    lbObject.setFont(new Font("SansSerif", 1, 11));
    rdGlobal.setText("global");
    rdGlobal.setBounds(new Rectangle(15, 160, 60, 15));
    rdGlobal.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rdGlobal_actionPerformed(e);
        }
      });
    rdLocal.setText("local");
    rdLocal.setBounds(new Rectangle(80, 160, 80, 15));
    rdLocal.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rdGlobal_actionPerformed(e);
        }
      });
    jLabel1.setText("<html>Attributes not in the Key <br> (checked as the existing in a database schema)");
    jLabel1.setBounds(new Rectangle(15, 180, 250, 35));
    jLabel1.setFont(new Font("SansSerif", 0, 11));
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    bgrp.add(rdLocal);
    bgrp.add(rdGlobal);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(rdLocal, null);
    this.getContentPane().add(rdGlobal, null);
    this.getContentPane().add(lbObject, null);
    this.getContentPane().add(jLabel13, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(table1, null);
    this.getContentPane().add(lstAtt1, null);
    this.getContentPane().add(lstAttKey1, null);
    this.getContentPane().add(lstKey1, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(btnUp, null);
    this.getContentPane().add(btnDown, null);
    this.getContentPane().add(lstKey, null);
    this.getContentPane().add(btmAddd, null);
    this.getContentPane().add(btnAdd, null);
    this.getContentPane().add(btnRem, null);
    this.getContentPane().add(btnRemm, null);
    this.getContentPane().add(lstAttKey, null);
    this.getContentPane().add(lstAtt, null);
    this.getContentPane().add(table, null);
    this.getContentPane().add(btnAddKey, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnRemoveKey, null);
    this.getContentPane().add(btnClose, null);
    setKeys();
    lstKey.setSelectedIndex(0);
     setKey(lstKey.getSelectedIndex()+1);
   // lstAtt.setListData(setAtt("")); 
    }

  private void close_ActionPerformed(ActionEvent e)
  { 
  this.dispose();
  }
  public void actionPerformed(ActionEvent e)
  {
  }
  
   private void setKey(int s)
  {
  Object[] attkey=setAtt("" + s);  
  Object[] att=setAtt("");  
  Set at=new HashSet();
  for(int i=0; i<att.length ; i++)
   {boolean can=true;
   for(int j=0; j<attkey.length ; j++)
   if(att[i].equals(attkey[j]))can=false;
   if(can)
   at.add(att[i]);
  }
  att=at.toArray();
  lstAtt.setListData(att);
  lstAttKey.setListData(attkey);
    try {
  
   JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
  ResultSet rs1;
  rs=query.select("select * from IISC_KEY_TOB where IISC_KEY_TOB.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +"");
  if(rs.next())
  { 
  if(rs.getInt("Tob_local")==0)
  rdGlobal.setSelected(true);
  else
  rdLocal.setSelected(true);
  }
  query.Close();
  }
 catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);} 
  }


  private void btnHelp_actionPerformed(ActionEvent e)
  { Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con);
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void setKeys()
  {
  try {
  
   JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
  ResultSet rs1;
  rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where PR_id="+ form.tree.ID +" and  TF_ID="+ form.id +" and TOB_id="+TOB);
  if(rs.next())
  {lbObject.setText(rs.getString("Tob_mnem"));
  query.Close();
    rs=query.select("select count(*) from IISC_KEY_TOB where PR_ID="+ form.tree.ID +" and  TF_ID="+ form.id +" and  TOB_ID="+ TOB );
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] sa=query.selectArray("select * from IISC_KEY_TOB where PR_ID="+ form.tree.ID +" and  TF_ID="+ form.id +" and  TOB_ID="+ TOB +" order by TOB_rbrk" ,j,3);
    sa[0]="";
    String[]  sak=new String[sa.length-1];
    for (int k=1;k<sa.length;k++)sak[k-1]="Key no. " + k;
    query.Close();
    lstKey.setListData(sak);
    lstAtt.setListData(new String[0]);
    lstAttKey.setListData(new String[0]);
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
   rs=query.select("select count(*) from IISC_ATTRIBUTE,IISC_ATT_KTO where  Att_sbp=1 and IISC_ATTRIBUTE.Att_id=IISC_ATT_KTO.Att_id and  IISC_ATT_KTO.PR_ID="+ form.tree.ID +" and  IISC_ATT_KTO.TF_ID="+ form.id +" and  IISC_ATT_KTO.TOB_ID="+ TOB + " and TOB_rbrk="+ s );
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] sa,sa1;
    if(s.equals(""))
    sa=query.selectArray("select * from IISC_ATTRIBUTE,IISC_ATT_TOB where  Att_sbp=1 and IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and  IISC_ATT_TOB.PR_ID="+ form.tree.ID +" and  IISC_ATT_TOB.TF_ID="+ form.id +" and  IISC_ATT_TOB.TOB_ID="+ TOB +" order by Att_mnem" ,j,3);
    else
    {sa=query.selectArray("select  Att_mnem from IISC_ATTRIBUTE,IISC_ATT_KTO where  Att_sbp=1 and IISC_ATTRIBUTE.Att_id=IISC_ATT_KTO.Att_id and  IISC_ATT_KTO.PR_ID="+ form.tree.ID +" and  IISC_ATT_KTO.TF_ID="+ form.id +" and  IISC_ATT_KTO.TOB_ID="+ TOB +" and TOB_rbrk="+ s +"  order by Att_rbrk" ,j,1);
    sa[0]="";
    }
    query.Close();
    String[]  sak=new String[sa.length-1];
    for (int k=1;k<sa.length;k++)sak[k-1]=sa[k];
    return sa;
  }
 catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);} 
return null;
  }

  private void lstKey_mouseClicked(MouseEvent e)
  {
   setKey(lstKey.getSelectedIndex()+1);
  }

  private void btnAddKey_actionPerformed(ActionEvent e)
  { 
  try {
   JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
  int key=1;
  rs=query.select("select max(Tob_rbrk)+1 from IISC_KEY_TOB  where   PR_id="+ form.tree.ID +" and TOB_id="+ TOB +" and Tf_id="+ form.id );
  if(rs.next())key=rs.getInt(1);
  query.Close();
  if(key==0)key=1;
  query.update("insert into IISC_KEY_TOB(Tf_id,Tob_id,Tob_rbrk,PR_id,Tob_local) values("+ form.id +","+ TOB +","+ key +","+ form.tree.ID +",0)");
  rdGlobal.setSelected(true);
  setKeys();
  setKey(key);
  }
 catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);} 
}

  private void btnRemoveKey_actionPerformed(ActionEvent e)
  {  
 try{
 JDBCQuery query=new JDBCQuery(con);
 ResultSet rs;
 if(lstKey.getSelectedIndex()>=0)
{int key=lstKey.getSelectedIndex()+1;
query.update("delete  from IISC_ATT_KTO   where  TF_id="+ form.id +" and TOB_id="+ TOB +"   and PR_id="+ form.tree.ID +" and TOB_rbrk="+ key +"");
query.update("delete from IISC_KEY_TOB   where  TF_id="+ form.id +" and TOB_id="+ TOB +"   and PR_id="+ form.tree.ID +" and TOB_rbrk="+ key +"");
query.update("update IISC_KEY_TOB set  TOB_rbrk=TOB_rbrk-1 where  TF_id="+ form.id +" and TOB_id="+ TOB +"   and PR_id="+ form.tree.ID +" and TOB_rbrk>"+ key +"");
query.update("update IISC_ATT_KTO set  TOB_rbrk=TOB_rbrk-1 where  TF_id="+ form.id +" and TOB_id="+ TOB +" and PR_id="+ form.tree.ID +" and TOB_rbrk>"+ key +"");
 }
 setKeys();
}
catch(Exception ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}

  }

  private void btnRemm_actionPerformed(ActionEvent e)
  { if(!lstKey.isSelectionEmpty())
  {
  if(lstKey.getSelectedIndex()>=0)
  {try{
  JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
  lstAtt.setListData(setAtt(""));
  lstAttKey.setListData(new String[0]);
  query.update("delete  from IISC_ATT_KTO   where  TF_id="+ form.id +" and TOB_id="+ TOB +"  and PR_id="+ form.tree.ID +" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +"");
  }
catch(Exception ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}  
}
  }
  }
private void btmAddd_actionPerformed(ActionEvent e)
  { if(!lstKey.isSelectionEmpty())
  {
  try{
  int fi=-1;
  JDBCQuery query=new JDBCQuery(con);
 ResultSet rs,rs1;
  lstAtt.setListData(new String[0]);
  lstAttKey.setListData(setAtt(""));
  for(int m=0;m< lstAttKey.getModel().getSize() ;m++)
 { rs1=query.select("select * from IISC_ATT_TOB,IISC_ATTRIBUTE  where  IISC_ATT_TOB.PR_id="+ form.tree.ID +" and IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id and  Att_mnem='"+ lstAttKey.getModel().getElementAt(m) +"'  and Tf_id="+ form.id );
if(rs1.next())fi=rs1.getInt(1);
else fi=0;
query.Close();
rs1=query.select("select * from IISC_ATT_KTO  where  TF_id="+ form.id +" and TOB_id="+ TOB +"  and PR_id="+ form.tree.ID +" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +" and Att_id=" +fi );
if(!rs1.next())
{query.Close();
int rb=1;
rs1=query.select("select max(Att_rbrk)+1 from IISC_ATT_KTO  where  TF_id="+ form.id +" and TOB_id="+ TOB +"  and PR_id="+ form.tree.ID +" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +"");
if(rs1.next())rb=rs1.getInt(1);
if(rb==0)rb=1;
query.Close();
query.update("insert into IISC_ATT_KTO(Att_id,Tf_id,Tob_id,PR_id,Tob_rbrk,Att_rbrk) values("+ fi +","+ form.id +","+ TOB +","+ form.tree.ID +","+ (lstKey.getSelectedIndex()+1) +","+ rb +")");
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
  if(!lstKey.isSelectionEmpty())
  {
  Object[] att=lstAttKey.getSelectedValues();
  for(int j=0;j<att.length ;j++)
  {
  try{
  JDBCQuery query=new JDBCQuery(con);
  ResultSet rs1;
  rs1=query.select("select IISC_ATTRIBUTE.Att_id, Att_rbrk from IISC_ATT_TOB,IISC_ATTRIBUTE,IISC_ATT_KTO  where IISC_ATT_TOB.Tob_id=IISC_ATT_KTO.Tob_id and IISC_ATT_TOB.PR_id=IISC_ATT_KTO.PR_id and IISC_ATT_TOB.TF_id=IISC_ATT_KTO.TF_id and IISC_ATT_TOB.Att_id=IISC_ATT_KTO.Att_id and IISC_ATT_TOB.PR_id="+ form.tree.ID +" and IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id and  Att_mnem='"+ att[j].toString() +"'  and IISC_ATT_TOB.Tf_id="+ form.id );
if(rs1.next())
{int fi=rs1.getInt(1);
int rb=rs1.getInt(2);
query.Close();
  query.update("delete  from IISC_ATT_KTO   where  TF_id="+ form.id +" and TOB_id="+ TOB +"  and PR_id="+ form.tree.ID +" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +" and Att_id="+fi);
  query.update("update IISC_ATT_KTO set Att_rbrk=Att_rbrk-1   where Att_rbrk> "+ rb +" and  TF_id="+ form.id +" and TOB_id="+ TOB +"  and PR_id="+ form.tree.ID +" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +"");
  
  setKey(lstKey.getSelectedIndex()+1);
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
  { if(!lstKey.isSelectionEmpty())
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
rs1=query.select("select max(Att_rbrk)+1 from IISC_ATT_KTO  where  TF_id="+ form.id +" and TOB_id="+ TOB +"  and PR_id="+ form.tree.ID +" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +"");
if(rs1.next())rb=rs1.getInt(1);
if(rb==0)rb=1;
query.Close();
query.update("insert into IISC_ATT_KTO(Att_id,Tf_id,Tob_id,PR_id,Tob_rbrk,Att_rbrk) values("+ fi +","+ form.id +","+ TOB +","+ form.tree.ID +","+ (lstKey.getSelectedIndex()+1) +","+ rb +")");
  setKey(lstKey.getSelectedIndex()+1);
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
   int f=lstAttKey.getSelectedIndex();
  rs=query.select("select * from IISC_ATT_KTO where  IISC_ATT_KTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +" and Att_rbrk>"+  lstAttKey.getSelectedIndex()  +" order by Att_rbrk");
  if(rs.next())
 {int k=rs.getInt(1); 
 query.Close();
 rs=query.select("select Att_id from IISC_ATT_KTO where  IISC_ATT_KTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +" and Att_rbrk="+  lstAttKey.getSelectedIndex()  +" ");
 if(rs.next())
 {
int j=rs.getInt(1); 
 query.Close();
query.update("update IISC_ATT_KTO set Att_rbrk="+ lstAttKey.getSelectedIndex() +" where Att_id="+ k +" and   IISC_ATT_KTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +"");
query.update("update IISC_ATT_KTO set Att_rbrk="+ (lstAttKey.getSelectedIndex()+1)  + " where Att_id="+ j +" and   IISC_ATT_KTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +"");
 f=lstAttKey.getSelectedIndex()+1;
 } 
 else
 query.Close();
 }
 else
 query.Close();
 setKey(lstKey.getSelectedIndex()+1);
 lstAttKey.setSelectedIndex(f);
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
 int f=lstAttKey.getSelectedIndex();
  rs=query.select("select * from IISC_ATT_KTO where  IISC_ATT_KTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +" and Att_rbrk<"+  lstAttKey.getSelectedIndex()  +" order by Att_rbrk desc");
  if(rs.next())
 {int k=rs.getInt(1); 
 query.Close();
 rs=query.select("select Att_id from IISC_ATT_KTO where  IISC_ATT_KTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +" and Att_rbrk="+  lstAttKey.getSelectedIndex()  +" ");
 if(rs.next())
 {
int j=rs.getInt(1); 
 query.Close();
query.update("update IISC_ATT_KTO set Att_rbrk="+ lstAttKey.getSelectedIndex() +" where Att_id="+ k +" and   IISC_ATT_KTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +"");
query.update("update IISC_ATT_KTO set Att_rbrk="+ (lstAttKey.getSelectedIndex()-1)  + " where Att_id="+ j +" and   IISC_ATT_KTO.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +"");
 f=lstAttKey.getSelectedIndex()-1;
 } 
 else
 query.Close();
 }
 else
 query.Close();
setKey(lstKey.getSelectedIndex()+1);
 lstAttKey.setSelectedIndex(f);
  }
catch(Exception ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}  
  }

  private void this_windowClosed(WindowEvent e)
  {form.qtmatt.setQueryA("select * from IISC_ATT_TOB where TF_id ="+ form.id +" and TOB_id=" + TOB );
  }

  private void rdGlobal_actionPerformed(ActionEvent e)
  {
      try{
  JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
 if(rdLocal.isSelected())
 query.update("update IISC_KEY_TOB set TOB_local=1 where   IISC_KEY_TOB.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +"");
 else
  query.update("update IISC_KEY_TOB set TOB_local=0 where   IISC_KEY_TOB.PR_id="+ form.tree.ID +" and TOB_id="+ TOB +"  and Tf_id="+ form.id+" and TOB_rbrk="+ (lstKey.getSelectedIndex()+1) +"");
  }
catch(Exception ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
} 
  }


}
  