package iisc;

import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.sql.*;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import java.awt.Color;

public class AttType extends JDialog implements ActionListener
{
  public JButton btnClose = new JButton();


  public JButton btnSave = new JButton();
  private Connection con;
  private Form form;
  private ButtonGroup bgrp=new ButtonGroup();
  private int ID,FID,TOB;
  public String dialog=new String();
  private JButton btnHelp = new JButton();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  public JButton btnApply = new JButton();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel jPanel1 = new JPanel();
  //private JButton btnFunction6 = new JButton();
  private JButton btnFunction5 = new JButton();
  private JLabel jLabel1110 = new JLabel();
  private JLabel jLabel119 = new JLabel();
  private JLabel jLabel118 = new JLabel();
  //private JComboBox cmbFunction = new JComboBox();
  //private JLabel jLabel15 = new JLabel();
  private JTextField txtOrder = new JTextField();
  private JLabel jLabel116 = new JLabel();
  private JComboBox cmbBehav = new JComboBox(new String[] { "modifiable", "query only","display only"});
  private JLabel jLabel115 = new JLabel();
  private JComboBox cmbMandatory = new JComboBox(new String[] {"No", "Yes"});
  private JLabel jLabel114 = new JLabel();
  private JLabel jLabel13 = new JLabel();
  private JLabel lbObject = new JLabel();
  private JLabel lbForm = new JLabel();
  private JLabel jLabel12 = new JLabel();
  private JLabel jLabel11 = new JLabel();
  private JTextField txtTittle = new JTextField();
  private JLabel jLabel14 = new JLabel();
  private JComboBox cmbAttribute = new JComboBox();
    private boolean dispose=false;
  public JTextField txtDefault = new JTextField();
  private JLabel jLabel17 = new JLabel();
  private JCheckBox chQueAllowed = new JCheckBox();
  private JCheckBox chNullAllowed = new JCheckBox();
  private JCheckBox chInsAllowed = new JCheckBox();
  private JCheckBox chUpdAllowed = new JCheckBox();
  private JPanel jPanel2 = new JPanel();
  private CtaDisplayProperties cdp;
  private LOVPanel lp;
  private AttFunDef att_fun_def;
  private AttEventDef att_event_def;
  private JPanel jPanel3 = new JPanel();
  private JRadioButton jRadioButton1 = new JRadioButton();
  private JRadioButton jRadioButton2 = new JRadioButton();
  private int inherit = 0;
  
  public AttType()
  {
    this(null, "",-1,-1, false,null,null);
  }

  public AttType(IISFrameMain parent, String title,int m,int m1, boolean modal, Connection conn, Form frm )
  {
    super(parent, title, modal);
    try
    { ID=m;
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
  {   
      
      this.setResizable(false);
      this.setSize(new Dimension(414, 443));
      this.getContentPane().setLayout(null);
      this.setTitle("Component Type Attribute");
      btnClose.setMaximumSize(new Dimension(50, 30));
      btnClose.setPreferredSize(new Dimension(50, 30));
      btnClose.setText("Cancel");
      btnClose.setBounds(new Rectangle(280, 375, 80, 30));
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
      btnSave.setBounds(new Rectangle(200, 375, 75, 30));
      btnSave.setMinimumSize(new Dimension(50, 30));
      btnSave.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent ae)
          {
            save_ActionPerformed(ae);
          }
        });
      ResultSet rs;
      JDBCQuery query=new JDBCQuery(con);
      rs=query.select("select count(*) +1 from IISC_ATT_TOB where TF_id =" + form.id + " and TOB_id =" + TOB + " and PR_id="+form.tree.ID);
      rs.next();
      int j=rs.getInt(1);
      query.Close();
      txtOrder.setText("" + j);
      rs=query.select("select  *  from IISC_ATT_TOB   where TF_id =" + form.id + "  and Att_id <>" + ID + "  and PR_id="+form.tree.ID);
      String str=new String();
      while(rs.next())
      str=str + " and ATT_id<>" +rs.getInt("Att_id");
      query.Close();
      rs=query.select("select count(*) from IISC_ATTRIBUTE where PR_id="+form.tree.ID+ str);
      rs.next();
      j=rs.getInt(1);
      query.Close();
      String[] sa=query.selectArray("select * from IISC_ATTRIBUTE where PR_id="+form.tree.ID + str,j,3);
      query.Close();
       cmbAttribute =new JComboBox(sa);
      rs=query.select("select count(*) from IISC_FUNCTION where PR_id="+form.tree.ID);
      rs.next();
      j=rs.getInt(1);
      query.Close();
      sa=query.selectArray("select * from IISC_FUNCTION where PR_id="+form.tree.ID,j,3);
      query.Close();
      //cmbFunction =new JComboBox(sa);
      btnSave.setEnabled(false);
      btnHelp.setBounds(new Rectangle(365, 375, 35, 30));
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
      btnApply.setBounds(new Rectangle(120, 375, 75, 30));
      btnApply.setMinimumSize(new Dimension(50, 30));
      btnApply.setEnabled(false);
      btnApply.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent ae)
          {
            apply_ActionPerformed(ae);
          }
        });
      jTabbedPane1.setBounds(new Rectangle(10, 5, 390, 360));
      jPanel1.setFont(new Font("SansSerif", 0, 11));
      jPanel1.setLayout(null);
      /*btnFunction6.setMaximumSize(new Dimension(50, 30));
      btnFunction6.setPreferredSize(new Dimension(50, 30));
      btnFunction6.setText("...");
      btnFunction6.setBounds(new Rectangle(345, 125, 30, 20));
      btnFunction6.setMinimumSize(new Dimension(50, 30));
      btnFunction6.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent ae)
          {
            fun_ActionPerformed(ae);
          }
        });
      btnFunction6.setFont(new Font("SansSerif", 0, 11));*/
      btnFunction5.setMaximumSize(new Dimension(50, 30));
      btnFunction5.setPreferredSize(new Dimension(50, 30));
      btnFunction5.setText("...");
      btnFunction5.setBounds(new Rectangle(345, 65, 30, 20));
      btnFunction5.setMinimumSize(new Dimension(50, 30));
      btnFunction5.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent ae)
          {
            att_ActionPerformed(ae);
          }
        });
      btnFunction5.setFont(new Font("SansSerif", 0, 11));
      jLabel1110.setText("Update allowed:");
      jLabel1110.setFont(new Font("Verdana", 0, 11));
      jLabel1110.setBounds(new Rectangle(210, 225, 95, 15));
      jLabel119.setText("Nullify allowed:");
      jLabel119.setFont(new Font("Verdana", 0, 11));
      jLabel119.setBounds(new Rectangle(40, 225, 95, 15));
      jLabel118.setText("Insert allowed:");
      jLabel118.setFont(new Font("Verdana", 0, 11));
      jLabel118.setBounds(new Rectangle(215, 195, 95, 15));
      /*cmbFunction.addMouseListener(new MouseAdapter()
        {
          public void mouseClicked(MouseEvent e)
          {
            cmbFunction_mouseClicked(e);
          }
        });
      cmbFunction.setBounds(new Rectangle(90, 125, 250, 20));
      cmbFunction.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            cmbInsAllow_mouseClicked(e);
          }
        });*/
      /*jLabel15.setText("Function:");
      jLabel15.setBounds(new Rectangle(10, 125, 85, 20));*/
  
      txtOrder.setBounds(new Rectangle(145, 160, 45, 20));
      txtOrder.setEnabled(false);
      txtOrder.setVisible(false);
      jLabel116.setText("Query allowed:");
      jLabel116.setFont(new Font("Verdana", 0, 11));
      jLabel116.setBounds(new Rectangle(40, 195, 95, 15));
      cmbBehav.setBounds(new Rectangle(160, 155, 200, 20));
      cmbBehav.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            cmbBehav_itemStateChanged(e);
          }
        });
      jLabel115.setText("Attribute behavior:");
      jLabel115.setFont(new Font("Verdana", 0, 11));
      jLabel115.setBounds(new Rectangle(40, 155, 115, 15));
      cmbMandatory.setBounds(new Rectangle(90, 125, 45, 20));
      cmbMandatory.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            cmbMandatory_itemStateChanged(e);
          }
        });
      cmbMandatory.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            cmbMandatory_actionPerformed(e);
          }
        });
      jLabel114.setText("Mandatory:");
      jLabel114.setFont(new Font("Verdana", 0, 11));
      jLabel114.setBounds(new Rectangle(10, 125, 65, 15));
      jLabel13.setText("Component Type:");
      jLabel13.setBounds(new Rectangle(10, 30, 90, 20));
      lbObject.setFont(new Font("Dialog", 1, 11));
      lbObject.setBounds(new Rectangle(100, 5, 285, 20));
      lbForm.setFont(new Font("Dialog", 1, 11));
      lbForm.setBounds(new Rectangle(100, 30, 285, 20));
      jLabel12.setText("Attribute:");
      jLabel12.setBounds(new Rectangle(10, 65, 85, 20));
      jLabel11.setText("Form Type:");
      jLabel11.setBounds(new Rectangle(10, 5, 85, 20));
      txtTittle.setBounds(new Rectangle(90, 95, 285, 20));
      txtTittle.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            txtTittle_actionPerformed(e);
          }
        });
      txtTittle.addKeyListener(new KeyAdapter()
        {
          public void keyTyped(KeyEvent e)
          {
            txtTittle_keyTyped(e);
          }
        });
      jLabel14.setBounds(new Rectangle(10, 95, 85, 20));
      jLabel14.setText("Title:");
      cmbAttribute.addMouseListener(new MouseAdapter()
        {
          public void mouseClicked(MouseEvent e)
          {
            cmbAttribute_mouseClicked(e);
          }
        });
      cmbAttribute.setVisible(true);
      cmbAttribute.setBounds(new Rectangle(90, 65, 250, 20));
      cmbAttribute.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            cmbAttribute_itemStateChanged(e);
          }
        });
      cmbAttribute.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            cmbAttribute_actionPerformed(e);
          }
        });
      txtDefault.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            txtDefault_keyPressed(e);
          }
        });
      txtDefault.setFont(new Font("SansSerif", 0, 11));
      txtDefault.setBounds(new Rectangle(95, 275, 145, 20));
      txtDefault.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            txtDefault_actionPerformed(e);
          }
        });
      jLabel17.setFont(new Font("SansSerif", 0, 11));
      jLabel17.setBounds(new Rectangle(10, 275, 75, 20));
      jLabel17.setText("Default value:");
      chQueAllowed.setText("jCheckBox1");
      chQueAllowed.setBounds(new Rectangle(135, 195, 20, 20));
      chQueAllowed.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            chQueAllowed_itemStateChanged(e);
          }
        });
      chNullAllowed.setText("jCheckBox1");
      chNullAllowed.setBounds(new Rectangle(135, 225, 20, 20));
      chNullAllowed.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            chNullAllowed_itemStateChanged(e);
          }
        });
      chInsAllowed.setText("jCheckBox1");
      chInsAllowed.setBounds(new Rectangle(310, 195, 20, 20));
      chInsAllowed.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            chInsAllowed_itemStateChanged(e);
          }
        });
      chUpdAllowed.setText("jCheckBox1");
      chUpdAllowed.setBounds(new Rectangle(310, 225, 20, 20));
      chUpdAllowed.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            chUpdAllowed_itemStateChanged(e);
          }
        });
      jPanel2.setLayout(null);
    jRadioButton2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jRadioButton2_actionPerformed(e);
        }
      });
      jPanel1.add(chUpdAllowed, null);
      jPanel1.add(chInsAllowed, null);
      jPanel1.add(chNullAllowed, null);
      jPanel1.add(chQueAllowed, null);
      jPanel1.add(jLabel17, null);
      jPanel1.add(txtDefault, null);
      jPanel1.add(cmbAttribute, null);
      jPanel1.add(jLabel14, null);
      jPanel1.add(txtTittle, null);
      jPanel1.add(jLabel11, null);
      jPanel1.add(jLabel12, null);
      jPanel1.add(lbForm, null);
      jPanel1.add(lbObject, null);
      jPanel1.add(jLabel13, null);
      jPanel1.add(jLabel114, null);
      jPanel1.add(cmbMandatory, null);
      jPanel1.add(jLabel115, null);
      jPanel1.add(cmbBehav, null);
      jPanel1.add(jLabel116, null);
      jPanel1.add(txtOrder, null);
      //jPanel1.add(jLabel15, null);
      //jPanel1.add(cmbFunction, null);
      jPanel1.add(jLabel118, null);
      jPanel1.add(jLabel119, null);
      jPanel1.add(jLabel1110, null);
      jPanel1.add(btnFunction5, null);
      //jPanel1.add(btnFunction6, null);
      jTabbedPane1.addTab("Definition", jPanel1);
      jTabbedPane1.addTab("Display", jPanel2);
      this.getContentPane().add(jTabbedPane1, null);
      this.getContentPane().add(btnApply, null);
      this.getContentPane().add(btnHelp, null);
      this.getContentPane().add(btnSave, null);
      this.getContentPane().add(btnClose, null);
      setAtt();
      
      cdp = new CtaDisplayProperties(jTabbedPane1.getWidth() - 25, 300, con, (IISFrameMain)getParent(), ID, this, TOB, form.id, form.tree.ID);
      
      jRadioButton1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jRadioButton1_actionPerformed(e);
        }
      });
    
      jRadioButton2.setBounds(new Rectangle(235, 5, 100, 25));
      jRadioButton2.setText("Override");
      jRadioButton1.setBounds(new Rectangle(65, 5, 105, 25));
      jPanel3.setLayout(null);
      jPanel3.setBorder(BorderFactory.createLineBorder(new Color(124, 124, 124), 1));
      jRadioButton1.setText("Inherit");
      jPanel3.setBounds(new Rectangle(10, 5, 365, 35));
       // cdp.setBounds(new Rectangle(0, 15, jTabbedPane1.getWidth(), 310));
        
        cdp.setBounds(10, 45 , jTabbedPane1.getWidth() - 10 , 350);
        
      lp = new LOVPanel(jTabbedPane1.getWidth() - 20, 300, ID, TOB, form.id,  form.tree.ID, con, (IISFrameMain) getParent(), this);
      jTabbedPane1.addTab("LOV", lp);
    
       att_fun_def = new AttFunDef(jTabbedPane1.getWidth() - 20, 240, ID, TOB, form.id,  form.tree.ID, con, (IISFrameMain) getParent(), this, "0", "", "", "", null, 0, 0);
       jTabbedPane1.addTab("Calculated", att_fun_def);
      
       this.att_event_def = new AttEventDef(jTabbedPane1.getWidth() - 15, 240, ID, TOB, form.id,  form.tree.ID, con, (IISFrameMain) getParent(), "1");
       jTabbedPane1.addTab("Events", att_event_def);
        
      jPanel3.add(jRadioButton2, null);
      jPanel3.add(jRadioButton1, null);
      jPanel2.add(jPanel3, null);
      jPanel2.add(cdp);
      jPanel2.repaint();
    
    }

  private void close_ActionPerformed(ActionEvent e)
  {dispose=true; 
  if(btnSave.isEnabled())
  
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
    {
        
        save();
    }
  
    this.dispose();
    
  }
  public void actionPerformed(ActionEvent e)
  {
  }
  private void save_ActionPerformed(ActionEvent e)
  {   dispose=true;
      save();
       
  }
  private void apply_ActionPerformed(ActionEvent e)
  {
      dispose=false;
      save();
  }
   public void Reload ()
{try
{
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(con);
    rs=query.select("select count(*) +1 from IISC_ATT_TOB where TF_id =" + form.id + " and TOB_id =" + TOB + " and PR_id="+form.tree.ID);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    txtOrder.setText(""+j);
    rs=query.select("select  *  from IISC_ATT_TOB   where TF_id =" + form.id + "  and Att_id <>" + ID + "  and PR_id="+form.tree.ID);
    String str=new String();
    while(rs.next())
    str=str + " and ATT_id<>" +rs.getInt("Att_id");
    query.Close();
    rs=query.select("select count(*) from IISC_ATTRIBUTE where PR_id="+form.tree.ID+ str);
    rs.next();
    j=rs.getInt(1);
    query.Close();
    String[] sa=query.selectArray("select * from IISC_ATTRIBUTE where PR_id="+form.tree.ID + str,j,3);
    query.Close();
    String s=cmbAttribute.getSelectedItem().toString();
    cmbAttribute.removeAllItems();
    for(int i=0;i<sa.length;i++)
    cmbAttribute.addItem(sa[i]);
    cmbAttribute.setSelectedItem(s);
    rs=query.select("select count(*) from IISC_FUNCTION where PR_id="+form.tree.ID);
    rs.next();
    j=rs.getInt(1);
    query.Close();
    sa=query.selectArray("select * from IISC_FUNCTION where PR_id="+form.tree.ID,j,3);
    query.Close();
    /*s=cmbFunction.getSelectedItem().toString();
    cmbFunction.removeAllItems();
    for(int i=0;i<sa.length;i++)
    cmbFunction.addItem(sa[i]);
    cmbFunction.setSelectedItem(s);*/
}
     catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
}
  private void save()
  {
     JDBCQuery query=new JDBCQuery(con);
     ResultSet rs;
     try
    { 
      if (cmbAttribute.getSelectedItem().toString().equals(""))
      JOptionPane.showMessageDialog(null, "<html><center>Attribute required!", "Component Type Attributes", JOptionPane.ERROR_MESSAGE);
      else
      {
      if(txtOrder.getText().trim().equals(""))
      JOptionPane.showMessageDialog(null, "<html><center>Order required!", "Component Type Attributes", JOptionPane.INFORMATION_MESSAGE);
      else if(txtTittle.getText().trim().equals("") )
      JOptionPane.showMessageDialog(null, "<html><center>Tittle required!", "Component Type Attributes", JOptionPane.INFORMATION_MESSAGE);
      else if (!att_fun_def.Check())
      JOptionPane.showMessageDialog(null, "<html><center>Invalid function specification!", "Component Type Attributes", JOptionPane.ERROR_MESSAGE);
      else
      {
      rs=query.select("select * from IISC_Attribute where  PR_id="+ form.tree.ID +" and   Att_mnem='"+ cmbAttribute.getSelectedItem().toString() + "'"); 
      int a=0;
      if(rs.next())
      a=rs.getInt(1);
      query.Close();
      String f = "null";
      /*rs=query.select("select * from IISC_FUNCTION where  PR_id="+ form.tree.ID +" and   Fun_name='"+ cmbFunction.getSelectedItem().toString() + "'");       
      if(rs.next())
      f=rs.getString(1);
      else
      f="null";
      query.Close();*/
      String que="N",upd="N",ins="N",nul="N";
      if(chQueAllowed.isSelected())que="Y";
      if(chInsAllowed.isSelected())ins="Y";
      if(chUpdAllowed.isSelected())upd="Y";
      if(chNullAllowed.isSelected())nul="Y";
      if(ID==-1)
      {
       query.update("insert into IISC_ATT_TOB(Att_id,Tf_id,Tob_id,PR_id,W_order,W_tittle,W_mand,W_queallow,W_insallow,W_updallow,W_nullallow,W_behav,W_default,Fun_id) values("+ a +","+ form.id +"," + TOB + ","+ form.tree.ID +",'"+ txtOrder.getText().trim() + "','"+ txtTittle.getText().trim() + "','"+ valueRet(cmbMandatory.getSelectedItem().toString()) +"','"+ que +"','"+ ins +"','"+ upd +"'," +
       "'"+ nul +"',"+ cmbBehav.getSelectedIndex()+",'"+ txtDefault.getText() +"',null)");
      ID=a;
      this.AddToDefaultIG(a, form.id, TOB, form.tree.ID);
      }
      else
      query.update("update IISC_ATT_TOB set Att_id=" + a + ", W_order="+ txtOrder.getText().trim() + ",W_tittle='"+ txtTittle.getText().trim() + "',W_mand='"+ valueRet(cmbMandatory.getSelectedItem().toString()) +"',W_nullallow='"+ nul +"',W_queallow='"+ que +"',W_updallow='"+ upd +"',W_insallow='"+ ins +"',W_behav="+ cmbBehav.getSelectedIndex() +"," +
      "W_default='"+ txtDefault.getText() +"'  where  PR_id="+ form.tree.ID +" and  TOB_id="+ TOB +" and Tf_id="+ form.id +" and Att_id="+ID);
      query.update("update IISC_ATT_KTO set Att_id=" + a + " where  TF_id ="+ form.id +" and  PR_id="+ form.tree.ID +" and  TOB_id="+ TOB +" and  Att_id="+ID);
      query.update("update IISC_ATT_UTO set Att_id=" + a + " where  TF_id ="+ form.id +" and  PR_id="+ form.tree.ID +" and  TOB_id="+ TOB +" and  Att_id="+ID);
      
      cdp.Att_id = ID;
      cdp.Update();
      lp.Att_id = ID;
      lp.Update();
      
      att_fun_def.Att_id = ID;
      att_fun_def.Tob_id = TOB;
      att_fun_def.Tf_id = form.id;
      att_fun_def.PR_id = form.tree.ID;
      att_fun_def.Update();
          
      att_event_def.Att_id = ID;
      att_event_def.Tob_id = TOB;
      att_event_def.Tf_id = form.id;
      att_event_def.PR_id = form.tree.ID;
          
      JOptionPane.showMessageDialog(null, "<html><center>Component Type Attribute saved!", "Component Type Attributes", JOptionPane.INFORMATION_MESSAGE);
      form.qtmatt.setQueryA("select * from IISC_ATT_TOB where TF_id ="+ form.id +" and TOB_id=" + TOB );
      for(int i=0;i<form.tableatt.getRowCount();i++)
      if(form.tableatt.getValueAt(i,1).toString().equals(cmbAttribute.getSelectedItem().toString()))
      form.tableatt.getSelectionModel().setSelectionInterval(i,i);
      
      if(dispose)
      dispose();
      else
      {
        btnSave.setEnabled(false);
         btnApply.setEnabled(false);
      }
      }
      }
     
    }
     catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  }

  public void AddToDefaultIG(int att_id, int tf_id, int tob_id, int pr_id)
  {
    try 
    {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select IG_id from IISC_ITEM_GROUP where TF_id=" + tf_id + " and TOB_id="+tob_id + " and PR_id=" + pr_id);
        
        if (rs.next())
        {
            String IG_id = rs.getString("IG_id");
            
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("select max(IGI_id) as maxigi from IISC_IG_ITEM");
            
            if (rs2.next())
            {
                
                String IGI_id = Integer.toString(rs2.getInt("maxigi") + 1);
                
                Statement stmt3 = con.createStatement();
                ResultSet rs3 = stmt2.executeQuery("select max(IGI_order) as maxigiorder from IISC_IG_ITEM where IG_id="+ IG_id);
                
                if (rs3.next())
                {
                    String IGI_order = Integer.toString(rs3.getInt("maxigiorder") + 1);
                    
                    Statement stmt4 = con.createStatement();
                    String query = "insert into IISC_IG_ITEM(IGI_id, IG_id, PR_id, TF_id, TOB_id, Att_id, IGI_order, IGI_breakline, IG_nested_group_id) values(";
                    query += IGI_id + ",";
                    query += IG_id + ",";
                    query += pr_id + ",";
                    query += tf_id + ",";
                    query += tob_id + ",";
                    query += att_id + ",";
                    query += IGI_order + ",1,null)";
                    
                    //System.out.println(query);
                    
                    stmt4.execute(query);
                }
            }
        }
        rs.close();
        stmt.close();
    }
    catch(Exception e)
    {
        System.out.println(e.toString());
    }
  }
  
  public void setFields(int att,int frm,int tob)
  {
   try {
  ResultSet rs,rs1;
  JDBCQuery query=new JDBCQuery(con);
 
  rs=query.select("select W_order,W_mand,W_queallow,W_nullallow,W_insallow,W_updallow,Att_mnem,W_behav,IISC_ATT_TOB.Fun_id,W_tittle,W_default from IISC_ATTRIBUTE,IISC_ATT_TOB where IISC_ATT_TOB.PR_id="+ form.tree.ID +" and IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id  and IISC_ATT_TOB.Tf_id="+ frm +" and IISC_ATT_TOB.TOB_id="+ tob +" and IISC_ATT_TOB.Att_id="+att);
      if(rs.next())
      {  
        
          txtDefault.setText(rs.getString("W_default"));
          txtTittle.setText(rs.getString("W_tittle"));
          cmbMandatory.setSelectedItem(retValue(rs.getString("W_mand")));
          if(rs.getString("W_queallow").equals("Y"))chQueAllowed.setSelected(true);
          else chQueAllowed.setSelected(false);
          if(rs.getString("W_nullallow").equals("Y"))chNullAllowed.setSelected(true);
          else chNullAllowed.setSelected(false);
          if(rs.getString("W_insallow").equals("Y"))chInsAllowed.setSelected(true);
          else chInsAllowed.setSelected(false);
          if(rs.getString("W_updallow").equals("Y"))chUpdAllowed.setSelected(true);
          else chUpdAllowed.setSelected(false);
          cmbAttribute.setSelectedItem(rs.getString("Att_mnem"));
          cmbBehav.setSelectedIndex(rs.getInt("W_behav"));
          int f=rs.getInt(9);
          query.Close();
          /*rs1=query.select("select * from IISC_FUNCTION where PR_id="+ form.tree.ID +" and Fun_id="+f);
         if(rs1.next()) cmbFunction.setSelectedItem(rs1.getString("Fun_name"));
         else
         cmbFunction.setSelectedItem("");*/
         query.Close();
         btnApply.setEnabled(true);
          btnSave.setEnabled(true);
      }
      else
      query.Close();
   }
      catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  }
   private void setAtt()
  {
   try {
  ResultSet rs,rs1;
  JDBCQuery query=new JDBCQuery(con);
  rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where PR_id="+ form.tree.ID +" and TOB_id="+TOB);
  rs.next();
  lbForm.setText(rs.getString("Tob_mnem"));
  query.Close();
  lbObject.setText(form.Mnem);
  if(ID==-1)
  {
  txtTittle.setText("");
  txtDefault.setText("");
  cmbAttribute.setSelectedItem("");
  //cmbFunction.setSelectedItem("");
  }
  else
  {
  rs=query.select("select * from IISC_ATTRIBUTE,IISC_ATT_TOB,IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_ATT_TOB.PR_id="+ form.tree.ID +" and IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id=IISC_ATT_TOB.TOB_id and IISC_ATT_TOB.TOB_id="+ TOB +" and IISC_ATT_TOB.Tf_id="+ form.id +" and IISC_ATT_TOB.Att_id="+ID);
      if(rs.next())
      { ID=rs.getInt(1);
         txtTittle.setText(rs.getString("W_tittle"));
          txtOrder.setText(rs.getString("W_order"));
          if(rs.getString("W_queallow").equals("Y"))chQueAllowed.setSelected(true);
          else chQueAllowed.setSelected(false);
          if(rs.getString("W_nullallow").equals("Y"))chNullAllowed.setSelected(true);
          else chNullAllowed.setSelected(false);
          if(rs.getString("W_insallow").equals("Y"))chInsAllowed.setSelected(true);
          else chInsAllowed.setSelected(false);
          if(rs.getString("W_updallow").equals("Y"))chUpdAllowed.setSelected(true);
          txtDefault.setText(rs.getString("W_default"));
          cmbAttribute.setSelectedItem(rs.getString("Att_mnem"));
           cmbBehav.setSelectedIndex(rs.getInt("W_behav"));
            cmbMandatory.setSelectedItem(retValue(rs.getString("W_mand")));
          int f=rs.getInt("Fun_id");
          query.Close();
          rs1=query.select("select * from IISC_FUNCTION where PR_id="+ form.tree.ID +" and Fun_id="+f);
         /*if(rs1.next()) cmbFunction.setSelectedItem(rs1.getString("Fun_name"));
         else
         cmbFunction.setSelectedItem("");*/
         query.Close();
      }
     else
     query.Close();
    }
    btnApply.setEnabled(false);
    btnSave.setEnabled(false);
    check();
   }
      catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  }

  private void txtObject_actionPerformed(ActionEvent e)
  {  }


  private void btnHelp_actionPerformed(ActionEvent e)
  { Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con);
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void fun_ActionPerformed(ActionEvent e)
  {btnApply.setEnabled(true);
      btnSave.setEnabled(true);  
      SearchTable ptype=new SearchTable((Frame)getParent(),"Select Function",true,con);
      Settings.Center(ptype);
      ptype.type="Function";
      //ptype.item=cmbFunction;
      ptype.owner=this;
      ptype.setVisible(true);
  }

  private void att_ActionPerformed(ActionEvent e)
  {
      btnApply.setEnabled(true);
      btnSave.setEnabled(true);  
      SearchTable ptype=new SearchTable((Frame)getParent(),"Select Attribute",true,con);
      Settings.Center(ptype);
      ptype.type="Attribute";
      ptype.item=cmbAttribute;
      ptype.owner=this;
      ptype.setVisible(true);
  }

  private void txtTittle_keyTyped(KeyEvent e)
  {   btnApply.setEnabled(true);
      btnSave.setEnabled(true);  
  }



  private void cmbBehav_mouseClicked(MouseEvent e)
  { 
  }

  private void cmbQueAllow_mouseClicked(MouseEvent e)
  {
  }

  private void cmbDelAllow_mouseClicked(MouseEvent e)
  {
  }


  private void cmbUpdAllow_mouseClicked(MouseEvent e)
  {
  }
 private void cmbFunction_mouseClicked(MouseEvent e)
  { 
  }

private void cmbAttribute_mouseClicked(MouseEvent e)
  {
  }

  private void cmbAttribute_actionPerformed(ActionEvent e)
  {  }

  private void check()
  {
      if(cmbBehav.getSelectedItem().equals("query only"))
  {
    chUpdAllowed.setSelected(false);
     chUpdAllowed.setEnabled(false);
     chInsAllowed.setSelected(false);
     chInsAllowed.setEnabled(false);
      chNullAllowed.setSelected(false);
     chNullAllowed.setEnabled(false);
     chQueAllowed.setSelected(true);
     chQueAllowed.setEnabled(false);
  }
 else  if(cmbBehav.getSelectedItem().equals("display only"))
  { chUpdAllowed.setSelected(false);
     chUpdAllowed.setEnabled(false);
     chInsAllowed.setSelected(false);
     chInsAllowed.setEnabled(false);
     chNullAllowed.setSelected(false);
     chNullAllowed.setEnabled(false);
     chQueAllowed.setEnabled(true);
  }
  else
  {
     chNullAllowed.setEnabled(true);
     chInsAllowed.setEnabled(true);
     chUpdAllowed.setEnabled(true);
     chQueAllowed.setEnabled(true);
  }
  if(cmbMandatory.getSelectedItem().toString().equals("Y"))
  {
    chNullAllowed.setSelected(false);
    chNullAllowed.setEnabled(false);
  }
  else
   {if(cmbBehav.getSelectedItem().equals("modifiable"))
    chNullAllowed.setEnabled(true);
  }
   try {
  ResultSet rs,rs1;
  JDBCQuery query=new JDBCQuery(con);
  rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where PR_id="+ form.tree.ID +" and TOB_id="+TOB);
  if(rs.next())
  {
    if(rs.getString("TOB_queallow").equals("N"))
    {
       chQueAllowed.setSelected(false);
      chQueAllowed.setEnabled(false);
    }
     if(rs.getString("TOB_updallow").equals("N"))
    {
       chUpdAllowed.setSelected(false);
      chUpdAllowed.setEnabled(false);
       chNullAllowed.setSelected(false);
      chNullAllowed.setEnabled(false);
    }
     if(rs.getString("TOB_insallow").equals("N"))
    {
       chInsAllowed.setSelected(false);
      chInsAllowed.setEnabled(false);
    }
  }
 query.Close();
   }
      catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}

  }

  private void cmbAttribute_itemStateChanged(ItemEvent e)
  { try {
  btnApply.setEnabled(true);
   btnSave.setEnabled(true); 
  ResultSet rs,rs1;
  JDBCQuery query=new JDBCQuery(con);
 if(cmbAttribute.getItemCount()>0)
 { rs=query.select("Select Att_name, Att_id from IISC_ATTRIBUTE where PR_id="+ form.tree.ID +" and Att_mnem='"+cmbAttribute.getSelectedItem().toString()+"'");
      if(rs.next())
      {  
      txtTittle.setText(rs.getString(1));
      int id = rs.getInt(2);
      
      if ( cdp != null)
      {
          cdp.Att_id = id;
      }
      //lp.Att_id = id;
      
      }
  query.Close();
   } }
      catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
 

  }

  private void txtDefault_keyPressed(KeyEvent e)
  {btnApply.setEnabled(true);
   btnSave.setEnabled(true); 
  }

  private void txtDefault_actionPerformed(ActionEvent e)
  { 
  }

  private void txtTittle_actionPerformed(ActionEvent e)
  {
  }

  private void cmbInsAllow_mouseClicked(ItemEvent e)
  {btnApply.setEnabled(true);
   btnSave.setEnabled(true); 
   
  }


  private void cmbInsAllow_actionPerformed(ActionEvent e)
  {
  }


  private void cmbMandatory_actionPerformed(ActionEvent e)
  {
  }

  private void cmbBehav_actionPerformed(ActionEvent e)
  {
  }

  private void cmbBehav_itemStateChanged(ItemEvent e)
  {btnApply.setEnabled(true);
   btnSave.setEnabled(true); 
   check();
  }

  private void cmbMandatory_itemStateChanged(ItemEvent e)
  {btnApply.setEnabled(true);
   btnSave.setEnabled(true); 
   check();
  }

  private void chQueAllowed_itemStateChanged(ItemEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void chNullAllowed_itemStateChanged(ItemEvent e)
  {
      btnApply.setEnabled(true);
      btnSave.setEnabled(true);
  }

  private void chInsAllowed_itemStateChanged(ItemEvent e)
  {
      btnApply.setEnabled(true);
      btnSave.setEnabled(true);
  }

  private void chUpdAllowed_itemStateChanged(ItemEvent e)
  {
      btnApply.setEnabled(true);
      btnSave.setEnabled(true);
  }

  private void jRadioButton1_actionPerformed(ActionEvent e)
  {
      
      
      jRadioButton1.setSelected(true);
      jRadioButton2.setSelected(false);
      
      if ( inherit != 1)
      {
          inherit = 1;
          cdp.Inherit();
          cdp.DisableAll();
          cdp.revalidate();
          cdp.repaint();
      }
  }

  private void jRadioButton2_actionPerformed(ActionEvent e)
  {
  
      jRadioButton2.setSelected(true);
      jRadioButton1.setSelected(false);
      
      if ( inherit != -1)
      {
          inherit = -1;
          cdp.EnableAll();
      }
  }

    public String retValue(String s){
        if(s.equals("Y"))return "Yes";
        else return "No";
    }
    public String valueRet(String s){
        if(s.equals("Yes"))return "Y";
        else return "N";
    }
}