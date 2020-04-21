package iisc.callgraph;

import iisc.*;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CreateAtt extends JDialog 
{
  private ButtonGroup bgrp=new ButtonGroup();
  private int id;
  private Form frm;
  private Connection con;
  private String[] sa;
  private String[] said;
  private String[] faid;
  private String[] oaid;
  private JComboBox cmbAtt = new JComboBox();
  private JRadioButton rdCopy = new JRadioButton();
  private JRadioButton rdNew = new JRadioButton();
  private JButton btnClose = new JButton();
  private JButton btnSave = new JButton();
  private JButton btnFunction5 = new JButton();
  public CreateAtt()
  {
    this(null, "", false,null,null);
  }

  public CreateAtt(IISFrameMain parent, String title, boolean modal, Connection conn, Form tr)
  {
   
    super(parent, title, modal);
    con=conn;
    frm=tr;
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {
     try
    {
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    rs=query.select("select count(*) from  IISC_FORM_TYPE,IISC_ATT_TOB, IISC_ATTRIBUTE where IISC_ATT_TOB.Att_id= IISC_ATTRIBUTE.Att_id and IISC_FORM_TYPE.TF_id=IISC_ATT_TOB.TF_id and IISC_ATT_TOB.PR_id="+ frm.tree.ID );
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    sa=new String[j];
    said=new String[j];
    faid=new String[j];
    oaid=new String[j];
    rs=query.select("select * from  IISC_FORM_TYPE,IISC_ATT_TOB, IISC_ATTRIBUTE where IISC_ATT_TOB.Att_id= IISC_ATTRIBUTE.Att_id and IISC_FORM_TYPE.TF_id=IISC_ATT_TOB.TF_id and IISC_ATT_TOB.PR_id="+ frm.tree.ID );
    int i=0;
    while(rs.next())
    {
    sa[i]=rs.getString("W_tittle")+ " ("+rs.getString("TF_mnem")+ ")";
    said[i]=rs.getString("Att_id");
    faid[i]=rs.getString("TF_id");
    oaid[i]=rs.getString("TOB_id");
    i++;
    }
     query.Close();
    for ( i=0;i<sa.length;i++)
    {
    cmbAtt.addItem(sa[i]);
    }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    this.setSize(new Dimension(310, 164));
    this.getContentPane().setLayout(null);
    this.setTitle("Create Attribute");
    this.setFont(new Font("SansSerif", 0, 11));
    this.setModal(true);
    cmbAtt.setBounds(new Rectangle(30, 60, 230, 20));
    cmbAtt.setFont(new Font("SansSerif", 0, 11));
    rdCopy.setText("Insert by Copying an Existing Attribute");
    rdCopy.setBounds(new Rectangle(10, 35, 285, 20));
    rdCopy.setToolTipText("null");
    rdCopy.addChangeListener(new ChangeListener()
      {
        public void stateChanged(ChangeEvent e)
        {
          rdProgram_stateChanged(e);
        }
      });
    rdCopy.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rdProgram_actionPerformed(e);
        }
      });
    rdNew.setText("Insert a New Attribute");
    rdNew.setBounds(new Rectangle(10, 10, 155, 20));
    rdNew.setSelected(true);
    rdNew.addChangeListener(new ChangeListener()
      {
        public void stateChanged(ChangeEvent e)
        {
          rdMenu_stateChanged(e);
        }
      });
    rdNew.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rdMenu_actionPerformed(e);
        }
      });
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Cancel");
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.setFont(new Font("SansSerif", 0, 11));
    btnClose.setBounds(new Rectangle(215, 95, 80, 30));
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
    btnSave.setMinimumSize(new Dimension(50, 30));
    btnSave.setFont(new Font("SansSerif", 0, 11));
    btnSave.setBounds(new Rectangle(135, 95, 75, 30));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save_ActionPerformed(ae);
        }
      });
    btnFunction5.setMaximumSize(new Dimension(50, 30));
    btnFunction5.setPreferredSize(new Dimension(50, 30));
    btnFunction5.setText("...");
    btnFunction5.setBounds(new Rectangle(265, 60, 30, 20));
    btnFunction5.setMinimumSize(new Dimension(50, 30));
    btnFunction5.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          rename_ActionPerformed(ae);
        }
      });
    btnFunction5.setFont(new Font("SansSerif", 0, 11));
      bgrp.add(rdCopy);
      bgrp.add(rdNew);
    this.getContentPane().add(btnFunction5, null);
    this.getContentPane().add(btnSave, null);
    this.getContentPane().add(btnClose, null);
    this.getContentPane().add(rdNew, null);
    this.getContentPane().add(rdCopy, null);
    this.getContentPane().add(cmbAtt, null);
     this.setResizable(false);
     cmbAtt.setEnabled(false);
    btnFunction5.setEnabled(false);
  }

  
  private void rdProgram_actionPerformed(ActionEvent e)
  {  if(rdNew.isSelected())
  {
    cmbAtt.setEnabled(false);
    btnFunction5.setEnabled(false);
  }
  else
  {
    cmbAtt.setEnabled(true);
    btnFunction5.setEnabled(true);
  }
  }

  private void rdProgram_stateChanged(ChangeEvent e)
  {
  }

  private void rdMenu_actionPerformed(ActionEvent e)
  {
  if(rdNew.isSelected())
  {
    cmbAtt.setEnabled(false);
    btnFunction5.setEnabled(false);
  }
  else
  {
    cmbAtt.setEnabled(true);
    btnFunction5.setEnabled(true);
  }
  }

  private void rdMenu_stateChanged(ChangeEvent e)
  {
  }

  private void btnHelp_actionPerformed(ActionEvent e)
  {
  }

  private void close_ActionPerformed(ActionEvent e)
  {dispose();
  }

  private void save_ActionPerformed(ActionEvent e)
  {
   JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
 try{
rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id="+ frm.id +" and TOB_mnem='"+ frm.table.getValueAt(frm.table.getSelectedRow(),0) +"' and PR_Id="+ frm.tree.ID +"");
  if(rs.next())
 {
 int ld=-1,fd=-1,od=-1;
  if(rdCopy.isSelected())
  for (int i=0;i<sa.length;i++)
    {
    if(cmbAtt.getSelectedItem().equals(sa[i]))
    {ld=(new Integer(said[i])).intValue();
     fd=(new Integer(faid[i])).intValue();
     od=(new Integer(oaid[i])).intValue();
    }
    }
 AttType att =new  AttType((IISFrameMain) getParent(),"Add Component Type Attribute",-1,rs.getInt(1), true, con,frm);
  if(rdCopy.isSelected())
 att.setFields(ld,fd,od); 
 Settings.Center(att);
 att.setVisible(true);
 dispose();
 }
 query.Close();
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
  }

  private void rename_ActionPerformed(ActionEvent e)
  {SearchTable ptype=new SearchTable((Frame)getParent(),"Select Component Type Attribute",true,con);
      Settings.Center(ptype);
      ptype.type="Component Type Attribute";
      ptype.btnNew.setEnabled(false);
      ptype.btnPrimitive.setEnabled(false);
      ptype.item=cmbAtt;
      ptype.owner=this;
      ptype.setVisible(true);
  }
}