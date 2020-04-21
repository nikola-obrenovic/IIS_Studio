package iisc.callgraph;

import iisc.*;
import iisc.tflayoutmanager.TFLayoutManager;

import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
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
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;

public class ObjectType extends JDialog implements ActionListener
{
  public JButton btnClose = new JButton();

  private ButtonGroup bgrp=new ButtonGroup();
  public JButton btnSave = new JButton();
  private JLabel jLabel10 = new JLabel();
  private JComboBox cmbParent;
  private Connection con;
  private Form form;
  private int ID,FID;
  private JLabel jLabel14 = new JLabel();
  private JTextField txtTitle = new JTextField();
  public String dialog=new String();
  private JLabel jLabel11 = new JLabel();
  private JLabel jLabel12 = new JLabel();
  private JLabel lbForm = new JLabel();
  private JTextField txtObject = new JTextField();
  private JButton btnHelp = new JButton();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  private JLabel jLabel13 = new JLabel();
  private JRadioButton rd1N = new JRadioButton();
  private JRadioButton rd0N = new JRadioButton();
  public JButton btnApply = new JButton();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel2 = new JPanel(null);
  private TobDisplForm tdp;
  private JPanel jPanel3 = new JPanel(null);
  private ItemGroup ig;
  private JLabel jLabel1110 = new JLabel();
  private JLabel jLabel118 = new JLabel();
  private JLabel jLabel116 = new JLabel();
  private JLabel jLabel119 = new JLabel();
  private JLabel jLabel15 = new JLabel();
  private boolean dispose=false;
  private JCheckBox chUpdAllowed = new JCheckBox();
  private JCheckBox chInsAllowed = new JCheckBox();
  private JCheckBox chQueAllowed = new JCheckBox();
  private JCheckBox chDelAllowed = new JCheckBox();
  private JButton jButton1 = new JButton();
  public IISFrameMain _parent;
  
  public ObjectType()
  {
      this(null, "",-1, false,null,null);
  }

  public ObjectType(IISFrameMain parent, String title,int m, boolean modal, Connection conn, Form frm )
  {
    super(parent, title, modal);
    _parent = parent;
    
    try
    { ID=m;
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
    this.getContentPane().setLayout(null);
    this.setTitle("Component Type");
      this.setSize(new Dimension(403, 430));
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Cancel");
    btnClose.setBounds(new Rectangle(260, 360, 80, 30));
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
    btnSave.setBounds(new Rectangle(170, 360, 75, 30));
    btnSave.setMinimumSize(new Dimension(50, 30));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          apply_ActionPerformed(ae);
        }
      });
    jLabel10.setText("Parent:");
    jLabel10.setBounds(new Rectangle(5, 70, 85, 20));
    cmbParent=new JComboBox();
    
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    rs=query.select("select count(*) from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id="+form.id);
    this.setFont(new Font("SansSerif", 0, 11));
    btnSave.setEnabled(false);
    if(rs.next())
    {
    int j=rs.getInt(1);
    query.Close();
    String[] said=query.selectArray("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id="+form.id,j,1);
    query.Close();
    String[] sa=query.selectArray("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id="+form.id,j,4);
    query.Close();
    Set pom=getChildSet(ID);
    for(int k=0;k<sa.length;k++)
    if(!pom.contains(said[k]) && !said[k].equals(""+ID))cmbParent.addItem(sa[k]);
    }
    else
    query.Close(); 
    cmbParent.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          parent_ActionPerformed(ae);
        }
      });
    txtTitle.setBounds(new Rectangle(85, 130, 285, 20));
    txtTitle.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          txtTitle_actionPerformed(e);
        }
      });
    txtTitle.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtTitle_keyTyped(e);
        }
      });
    jLabel11.setText("Form Type:");
    jLabel11.setBounds(new Rectangle(5, 10, 60, 20));
    jLabel12.setText("Name:");
    jLabel12.setBounds(new Rectangle(5, 40, 85, 20));
    lbForm.setBounds(new Rectangle(85, 10, 285, 20));
    lbForm.setFont(new Font("Dialog", 1, 11));
    txtObject.setBounds(new Rectangle(85, 40, 285, 20));
    txtObject.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtObject_keyTyped(e);
        }
      });
    btnHelp.setBounds(new Rectangle(350, 360, 35, 30));
    btnHelp.setIcon(imageHelp);
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    jLabel13.setText("No. of occurrences:");
    jLabel13.setBounds(new Rectangle(85, 100, 110, 20));
    rd1N.setText("1 - N");
    rd1N.setBounds(new Rectangle(255, 95, 55, 30));
    rd1N.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          cmbParent_itemStateChanged(e);
        }
      });
    rd0N.setText("0 - N");
    rd0N.setBounds(new Rectangle(190, 95, 50, 30));
    rd0N.setSelected(true);
    rd0N.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          cmbParent_itemStateChanged(e);
        }
      });
    rd0N.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rd0N_actionPerformed(e);
        }
      });
    btnApply.setMaximumSize(new Dimension(50, 30));
    btnApply.setPreferredSize(new Dimension(50, 30));
    btnApply.setText("Apply");
    btnApply.setBounds(new Rectangle(80, 360, 75, 30));
    btnApply.setMinimumSize(new Dimension(50, 30));
    btnApply.setEnabled(false);
    btnApply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save_ActionPerformed(ae);
        }
      });
    jTabbedPane1.setBounds(new Rectangle(5, 5, 380, 345));
    jPanel1.setLayout(null);
    jLabel1110.setText("update:");
    jLabel1110.setFont(new Font("SansSerif", 0, 11));
    jLabel1110.setBounds(new Rectangle(270, 200, 50, 15));
    jLabel1110.setPreferredSize(new Dimension(20, 15));
    jLabel1110.setMinimumSize(new Dimension(20, 15));
    jLabel1110.setMaximumSize(new Dimension(20, 15));
    jLabel118.setText("insert:");
    jLabel118.setFont(new Font("SansSerif", 0, 11));
    jLabel118.setBounds(new Rectangle(270, 170, 45, 15));
    jLabel118.setMaximumSize(new Dimension(20, 15));
    jLabel118.setMinimumSize(new Dimension(20, 15));
    jLabel118.setPreferredSize(new Dimension(20, 15));
    jLabel116.setText("query:");
    jLabel116.setFont(new Font("SansSerif", 0, 11));
    jLabel116.setBounds(new Rectangle(150, 170, 45, 15));
    jLabel116.setMaximumSize(new Dimension(20, 15));
    jLabel116.setMinimumSize(new Dimension(20, 15));
    jLabel116.setPreferredSize(new Dimension(20, 15));
    jLabel119.setText("delete:");
    jLabel119.setFont(new Font("SansSerif", 0, 11));
    jLabel119.setBounds(new Rectangle(150, 200, 45, 15));
    jLabel119.setMaximumSize(new Dimension(20, 15));
    jLabel119.setMinimumSize(new Dimension(20, 15));
    jLabel119.setPreferredSize(new Dimension(20, 15));
    jLabel15.setBounds(new Rectangle(5, 165, 120, 20));
    jLabel15.setText("Operations allowed");
    jLabel15.setFont(new Font("SansSerif", 0, 11));
    
    chUpdAllowed.setText("jCheckBox1");
    chUpdAllowed.setBounds(new Rectangle(310, 195, 20, 20));
    chUpdAllowed.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          chUpdAllowed_itemStateChanged(e);
        }
      });
    chInsAllowed.setText("jCheckBox1");
    chInsAllowed.setBounds(new Rectangle(310, 165, 20, 20));
    chInsAllowed.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          chInsAllowed_itemStateChanged(e);
        }
      });
    chQueAllowed.setText("jCheckBox1");
    chQueAllowed.setBounds(new Rectangle(190, 165, 20, 20));
    chQueAllowed.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          chQueAllowed_itemStateChanged(e);
        }
      });
    chDelAllowed.setText("jCheckBox1");
    chDelAllowed.setBounds(new Rectangle(190, 195, 20, 20));
    chDelAllowed.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          chDelAllowed_itemStateChanged(e);
        }
      });
    jLabel14.setBounds(new Rectangle(5, 130, 85, 20));
    jLabel14.setText("Title:");
    cmbParent.setVisible(true);
    cmbParent.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          cmbParent_itemStateChanged(e);
        }
      });
    bgrp.add(rd1N);
    bgrp.add(rd0N);
    cmbParent.setBounds(new Rectangle(85, 75, 285, 20));
    jPanel1.add(chDelAllowed, null);
    jPanel1.add(chQueAllowed, null);
    jPanel1.add(chInsAllowed, null);
    jPanel1.add(chUpdAllowed, null);
    jPanel1.add(jLabel15, null);
    jPanel1.add(jLabel119, null);
    jPanel1.add(jLabel116, null);
    jPanel1.add(jLabel118, null);
    jPanel1.add(jLabel1110, null);
    jPanel1.add(txtObject, null);
    jPanel1.add(rd0N, null);
    jPanel1.add(rd1N, null);
    jPanel1.add(jLabel13, null);
    jPanel1.add(lbForm, null);
    jPanel1.add(jLabel12, null);
    jPanel1.add(jLabel11, null);
    jPanel1.add(txtTitle, null);
    jPanel1.add(jLabel14, null);
    jPanel1.add(jLabel10, null);
    jPanel1.add(cmbParent, null);
    jTabbedPane1.addTab("Definition", jPanel1);
    tdp = new TobDisplForm((IISFrameMain)this.getParent(), this, jTabbedPane1.getWidth() - 20, 260, ID, form.id, form.tree.ID, con);
    ig = new ItemGroup(ID, form.id, form.tree.ID,this, con);
    
    jButton1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton1_actionPerformed(e);
        }
      });
    jPanel2.setSize(new Dimension(375, 270));
    jPanel2.setBounds(new Rectangle(2, 22, 375, 275));
    jPanel3.setSize(new Dimension(375, 270));
    jPanel3.setBounds(new Rectangle(2, 22, 375, 275));

    ig.setBounds(new Rectangle(0, 10, 380, 365));
    jButton1.setSize(new Dimension(85, 30));
    jButton1.setPreferredSize(new Dimension(73, 30));
    jButton1.setMinimumSize(new Dimension(73, 30));
    jButton1.setMaximumSize(new Dimension(73, 30));
    jButton1.setBounds(new Rectangle(250, 285, 115, 30));
    jButton1.setText("Layout Manager");
    
    tdp.setBounds(10, 10, jTabbedPane1.getWidth() - 20, 260);
    jPanel2.add(jButton1, null);
    jPanel2.add(tdp, null);
    
    ig.setBounds(0, 10, jTabbedPane1.getWidth(), jTabbedPane1.getHeight() - 20);
    jPanel3.add(ig, null);
        
    jTabbedPane1.addTab("Display", jPanel2);
    jTabbedPane1.addTab("Item groups", jPanel3);
        
    this.getContentPane().add(jTabbedPane1, null);

    this.getContentPane().add(btnApply, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnSave, null);
    this.getContentPane().add(btnClose, null);
    setObject();
    
    tdp.repaint();
    jPanel2.repaint();
  }

  private void close_ActionPerformed(ActionEvent e)
  { 
   if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      save_ActionPerformed(e);
  this.dispose();
  }
  public void actionPerformed(ActionEvent e)
  {
  }
  private void save_ActionPerformed(ActionEvent e)
  {
    
     JDBCQuery query=new JDBCQuery(con);
     ResultSet rs;
     try
     { 
    
        boolean have_root=false;
        
        rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TOB_superord is null and TF_id="+ form.id);
      
        if(rs.next())
            have_root=true;
      
        query.Close();
        
        if(ID==-1)
        rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TOB_mnem='"+ txtObject.getText().toString() + "' and TF_id="+ form.id);
        else
        {
            rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TOB_superord is null and TOB_id="+ ID +" and TF_id="+ form.id);
            if(rs.next())have_root=false;
            query.Close();
            rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TOB_mnem='"+ txtObject.getText().toString() + "' and TOB_id<>"+ ID + " and TF_id="+ form.id);
        }
        
        if(rs.next())
        {  
            JOptionPane.showMessageDialog(null, "<html><center>Component Type exists!", "Component Types", JOptionPane.ERROR_MESSAGE);
            query.Close();
        }
        else if(txtObject.getText().split(" ").length>1){
            JOptionPane.showMessageDialog(null, "<html><center>Name cannot contain blank character!", "Attributes", JOptionPane.ERROR_MESSAGE);
        }
        else 
            if (have_root && cmbParent.getSelectedItem().toString().equals(""))
            {
                JOptionPane.showMessageDialog(null, "<html><center>Parent missing!", "Component Types", JOptionPane.ERROR_MESSAGE);
                query.Close();
            }
            else
            {
                query.Close();
                if(txtObject.getText().trim().equals(""))
                {
                JOptionPane.showMessageDialog(null, "<html><center>Name required", "Component Types", JOptionPane.ERROR_MESSAGE);
                }
                else 
                    if(txtTitle.getText().trim().equals("") )
                        JOptionPane.showMessageDialog(null, "<html><center>Title required", "Component Types", JOptionPane.ERROR_MESSAGE);
                    else 
                    {
                        
                        String rez = ig.CheckValid();
                        
                        if (rez != "")
                        {
                            JOptionPane.showMessageDialog(null, rez, "Component Types", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        String parent;
                        rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where  TOB_mnem='"+ cmbParent.getSelectedItem().toString() + "' and TF_id="+ form.id); 
                        
                        if(rs.next())
                            parent=rs.getString(1);
                        else
                            parent="null";
            
                        query.Close();
                        String mandatory=new String();
                        String mand=new String();
                        
                        if(!cmbParent.getSelectedItem().toString().trim().equals(""))
                        {
                            if(rd0N.isSelected())
                            {
                              mandatory="0";
                              mand=" TOB_mandatory=0";
                            }
                            else
                            {
                               mandatory="1";
                              mand=" TOB_mandatory=1";
                            }
                         }
                         else
                         {
                             mandatory="NULL";
                             mand=" TOB_mandatory=NULL";
                          }
                          String que="N",upd="N",ins="N",del="N";
                          if(chQueAllowed.isSelected())que="Y";
                          if(chInsAllowed.isSelected())ins="Y";
                          if(chUpdAllowed.isSelected())upd="Y";
                          if(chDelAllowed.isSelected())del="Y";
                        
                          if(ID==-1)
                          {
                          rs=query.select("select max(TOB_id)+1 from IISC_COMPONENT_TYPE_OBJECT_TYPE"); 
                          int j=0;
                          if(rs.next())
                          j=rs.getInt(1);
                          ID = j;
                          query.Close();
                          query.update("insert into IISC_COMPONENT_TYPE_OBJECT_TYPE(Tob_id,PR_id,Tf_id,Tob_mnem,Tob_name,Tob_superord,Tob_mandatory,Tob_queallow,Tob_insallow,Tob_updallow,Tob_deleteallow) values("+ j +","+ form.tree.ID +","+ form.id +",'"+ txtObject.getText().trim() + "','"+ txtTitle.getText().trim() + "',"+ parent +","+ mandatory +",'"+ que +"','"+ ins +"','"+ upd +"','"+ del +"')");
                          tdp.Tob_id = ID;
                          tdp.Update();
                          ig.Tob_id = ID;
                          ig.Update();
                          //ig.U
                          }
                          else
                          {
                              tdp.Update();
                              ig.Update();
                              
                          query.update("update IISC_COMPONENT_TYPE_OBJECT_TYPE set TOB_mnem='"+ txtObject.getText().trim() + "',TOB_name='"+ txtTitle.getText().trim() + "',TOB_superord="+ parent + ","+mand +",Tob_queallow='"+ que +"',Tob_insallow='"+ ins +"',Tob_updallow='"+ upd +"',Tob_deleteallow='"+ del +"'  where TOB_id="+ ID +"");
                          if(!chQueAllowed.isSelected())
                           query.update("update IISC_ATT_TOB set  W_queallow='N'  where  PR_id="+ form.tree.ID +" and  TOB_id="+ ID );
                            if(!chUpdAllowed.isSelected())
                           query.update("update IISC_ATT_TOB set  W_updallow='N', W_nullallow='N'  where  PR_id="+ form.tree.ID +" and  TOB_id="+ ID );
                           if(!chInsAllowed.isSelected())
                           query.update("update IISC_ATT_TOB set  W_insallow='N'  where  PR_id="+ form.tree.ID +" and  TOB_id="+ ID );}
                          JOptionPane.showMessageDialog(null, "<html><center>Component Type saved!", "Component Types", JOptionPane.INFORMATION_MESSAGE);
                          
                          form.setForm(form.Mnem);
                          if(form.Mnem!="")
                          {
                           try{
                               form.tree.setVisible(false);
                               form.tree.pravi_drvo();
                                }
                             catch (Exception ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);}
                             catch (Throwable ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);}
                                
                            form.tree.select_node(form.Mnem,"Owned","Form Types",form.appsys);}
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
  

  private Set getChildSet(int i)
  {Set chSet=new HashSet();
  JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
  try {
  rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TOB_superord="+ i +"");
      while(rs.next())
      {int is=rs.getInt(1);
      chSet.addAll(getChildSet(is));
      chSet.add(""+is);
      }
 query.Close();
  return chSet;
  }
     catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
 return null;
 }
   private void setObject()
  {dispose=false; 
  lbForm.setText(form.Mnem);
  if(ID==-1)
  {
  txtTitle.setText("");
  cmbParent.setSelectedItem("");
  txtObject.setText("");
  chQueAllowed.setSelected(true);
  }
  else
  {
  JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
   try
    { rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TOB_id="+ ID +"");
      if(rs.next())
      { ID=rs.getInt(1);
         if(rs.getString("Tob_queallow").equals("Y"))chQueAllowed.setSelected(true);
          else chQueAllowed.setSelected(false);
          if(rs.getString("Tob_deleteallow").equals("Y"))chDelAllowed.setSelected(true);
          else chDelAllowed.setSelected(false);
          if(rs.getString("Tob_insallow").equals("Y"))chInsAllowed.setSelected(true);
          else chInsAllowed.setSelected(false);
          if(rs.getString("Tob_updallow").equals("Y"))chUpdAllowed.setSelected(true);
         txtTitle.setText(rs.getString("TOB_name"));
         txtObject.setText(rs.getString("Tob_mnem"));
         String sid=rs.getString("Tob_superord");
         if(rs.getInt("Tob_mandatory")==1)
         rd1N.setSelected(true);
         else
         rd0N.setSelected(true);
         if(sid==null || sid.equals(""))
         {
           rd1N.setEnabled(false);
           rd0N.setEnabled(false);
         }
         query.Close();
         if(!(sid==null || sid.equals(""))){
         rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TOB_id="+ sid +"");
         if(rs.next())
         cmbParent.setSelectedItem(rs.getString("Tob_mnem").trim());
          query.Close();
         }
         else
         cmbParent.setSelectedItem("");
      }
      else
      query.Close();
      btnApply.setEnabled(false);
      btnSave.setEnabled(false);
      
      if (this.chInsAllowed.isSelected())
      {
         this.tdp.rlirChb.setEnabled(true);
      }
      else
      {
          this.tdp.rlirChb.setEnabled(false);
      }
    }
      catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  }
  }

  private void txtObject_actionPerformed(ActionEvent e)
  {  }
 private void parent_ActionPerformed(ActionEvent e)
  {
  if(cmbParent.getSelectedItem().toString().equals(""))
  {
    rd0N.setEnabled(false);
     rd1N.setEnabled(false);
  }
  else
  {
     rd0N.setEnabled(true);
     rd1N.setEnabled(true);  
  }
  
  tdp.ChangeSuperOrd(cmbParent.getSelectedItem().toString());
  
  
  }

  private void btnHelp_actionPerformed(ActionEvent e)
  {
 Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void apply_ActionPerformed(ActionEvent e)
  {dispose=true;
  save_ActionPerformed(e);
  
  }

  private void txtObject_keyTyped(KeyEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);}

  private void txtTitle_keyTyped(KeyEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void rd1N_mouseClicked(MouseEvent e)
  {if(rd1N.isEnabled()){
  btnApply.setEnabled(true);
  btnSave.setEnabled(true);}
  }
  private void rd0N_mouseClicked(MouseEvent e)
  {
  if(rd0N.isEnabled()){
  btnApply.setEnabled(true);
  btnSave.setEnabled(true);}
  }

  private void rd0N_actionPerformed(ActionEvent e)
  {
  }

  private void txtTitle_actionPerformed(ActionEvent e)
  {
  }


  private void cmbParent_itemStateChanged(ItemEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void chDelAllowed_itemStateChanged(ItemEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void chQueAllowed_itemStateChanged(ItemEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void chInsAllowed_itemStateChanged(ItemEvent e)
  {
    btnApply.setEnabled(true);
    btnSave.setEnabled(true);
    
    if (chInsAllowed.isSelected())
    {
        tdp.rlirChb.setEnabled(true);
    }
    else
    {
        tdp.rlirChb.setEnabled(false);
    }
  }

  private void chUpdAllowed_itemStateChanged(ItemEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void jButton1_actionPerformed(ActionEvent e)
  {
      if ( ID == -1 )
      {
          JOptionPane.showMessageDialog(this,"You have to apply changes first","", JOptionPane.WARNING_MESSAGE);
          return;
      }
      TFLayoutManager tflm = new TFLayoutManager(500, 600, _parent,con, form.id, form.tree.ID);
      Settings.Center(tflm);
      tflm.setVisible(true);
      
      jPanel2.remove(tdp);
      tdp = new TobDisplForm((IISFrameMain)this.getParent(), this, jTabbedPane1.getWidth() - 20, 260, ID, form.id, form.tree.ID, con);
      
      tdp.setBounds(10, 10, jTabbedPane1.getWidth() - 20, 260);
      jPanel2.add(tdp, null);
      tdp.repaint();
      jPanel2.repaint();
    }
}