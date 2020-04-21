package iisc.callgraph;

import iisc.*;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
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
public class Check extends JDialog implements ActionListener
{
  public JButton btnClose = new JButton();

  private Connection con;
  private Form form;
  private ButtonGroup bgrp=new ButtonGroup();
  private int ID,FID,TOB;
  public String dialog=new String();
  private JButton btnHelp = new JButton();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private ImageIcon iconUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
  private ImageIcon iconDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
  private JLabel jLabel13 = new JLabel();
  private JLabel lbObject = new JLabel();
  private JLabel jLabel14 = new JLabel();
  private JTextArea txtTittle = new JTextArea();
  private JButton btnSave = new JButton();
  private JButton btnApply = new JButton();
  
  public Check()
  {
    this(null, "",-1, false,null,null);
  }

  public Check(IISFrameMain parent, String title,int m1, boolean modal, Connection conn, Form frm )
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
    this.setSize(new Dimension(357, 326));
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
    btnClose.setBounds(new Rectangle(225, 260, 80, 30));
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.setFont(new Font("SansSerif", 0, 11));
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          close_ActionPerformed(ae);
        }
      });
   

    
    btnHelp.setBounds(new Rectangle(310, 260, 35, 30));
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
    jLabel13.setBounds(new Rectangle(5, 20, 85, 20));
    jLabel13.setFont(new Font("SansSerif", 0, 11));
    lbObject.setBounds(new Rectangle(105, 20, 240, 20));
    lbObject.setFont(new Font("SansSerif", 1, 11));
    jLabel14.setText("Check Constraint:");
    jLabel14.setBounds(new Rectangle(5, 50, 85, 20));
    jLabel14.setFont(new Font("SansSerif", 0, 11));
    txtTittle.setBounds(new Rectangle(5, 70, 340, 180));
    txtTittle.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    txtTittle.setFont(new Font("SansSerif", 0, 11));
    txtTittle.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtTittle_keyTyped(e);
        }
      });
    btnSave.setMaximumSize(new Dimension(50, 30));
    btnSave.setPreferredSize(new Dimension(50, 30));
    btnSave.setText("OK");
    btnSave.setBounds(new Rectangle(145, 260, 75, 30));
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
    btnApply.setBounds(new Rectangle(65, 260, 75, 30));
    btnApply.setMinimumSize(new Dimension(50, 30));
    btnApply.setEnabled(false);
    btnApply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          apply_ActionPerformed(ae);
        }
      });
    this.getContentPane().add(btnApply, null);
    this.getContentPane().add(btnSave, null);
    this.getContentPane().add(txtTittle, null);
    this.getContentPane().add(jLabel14, null);
    this.getContentPane().add(lbObject, null);
    this.getContentPane().add(jLabel13, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnClose, null);
    setCheck();
   // lstAtt.setListData(setAtt("")); 
    }

  private void close_ActionPerformed(ActionEvent e)
  { 
     if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Component Type Check Constraint", JOptionPane.YES_NO_OPTION)==0)
      update(1);
     dispose();
    
  }
  public void actionPerformed(ActionEvent e)
  {
  }
  private void update(int i)
  {
    JDBCQuery query=new JDBCQuery(con);
     ResultSet rs1;  
    query.update("update IISC_COMPONENT_TYPE_OBJECT_TYPE set Tob_check='" + txtTittle.getText()+ "' where Tob_id=" + TOB);
    btnApply.setEnabled(false);
    btnSave.setEnabled(false);
    JOptionPane.showMessageDialog(null, "<html><center>Check Constraint saved!", "Component Type Check Constraint", JOptionPane.INFORMATION_MESSAGE);
    if(i==1)
    dispose();
  }
  private void btnHelp_actionPerformed(ActionEvent e)
  { Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con);
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void setCheck()
  {
  try {
  
   JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
  ResultSet rs1;
  rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where PR_id="+ form.tree.ID +" and  TF_ID="+ form.id +" and TOB_id="+TOB);
  if(rs.next())
  {
  String txt=rs.getString("TOB_check");
  if(txt.equals("null"))txt="";
  txtTittle.setText(txt);
  lbObject.setText(rs.getString("TOB_mnem"));
  }
  query.Close();
  }
 catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);} 
  }


  private void save_ActionPerformed(ActionEvent e)
  {update(1);
  }

  private void apply_ActionPerformed(ActionEvent e)
  {update(0);
  }

  private void txtTittle_keyTyped(KeyEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }


}
  