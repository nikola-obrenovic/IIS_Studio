package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import java.awt.event.*;
import javax.swing.JLabel;
import java.sql.*;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.Color;
import java.text.Collator;
import java.beans.PropertyChangeEvent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.JToolBar;
import javax.swing.JComboBox;
import java.awt.event.ItemEvent;

public class InclusionDep extends JDialog implements ActionListener,ItemListener
{
  public JButton btnClose = new JButton();


  private JButton btnSave = new JButton();
  private JButton btnNew = new JButton();
  private JButton btnErase = new JButton();
  private JTextField txtName = new JTextField();
  private JLabel jLabel13 = new JLabel();
  private Connection con;
  private ButtonGroup bgrp=new ButtonGroup();
  private int ID;
  public String dialog=new String();
  private JButton btnHelp = new JButton();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  private JButton btnApply = new JButton();
  private JLabel jLabel14 = new JLabel();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private DefaultListModel modellstAttribute=new DefaultListModel();
  private JList lstAttribute = new JList(modellstAttribute);
  private JScrollPane jScrollPane3 = new JScrollPane();
  private JScrollPane jScrollPane4 = new JScrollPane();
  private JLabel jLabel15 = new JLabel();
  private JLabel jLabel16 = new JLabel();
  private DefaultListModel modellstLHS=new DefaultListModel();
  private DefaultListModel modellstRHS=new DefaultListModel();
  private JList lstLHS = new JList(modellstLHS);
  private JList lstRHS = new JList(modellstRHS);
  private JButton btnAdd = new JButton();
  private JButton btnRem = new JButton();
  private JButton btnAdd1 = new JButton();
  private JButton btnRem1 = new JButton();
  private PTree tree;
  private JButton btnDown = new JButton();
  private JButton btnUp = new JButton();
  private JButton btnUp1 = new JButton();
  private JButton btnDown1 = new JButton();
  private ImageIcon iconUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
  private ImageIcon iconDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
  private ImageIcon iconCheck = new ImageIcon(IISFrameMain.class.getResource("icons/check.gif"));
  private ImageIcon iconErase = new ImageIcon(IISFrameMain.class.getResource("icons/erase.gif"));
  private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
  private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
  private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
  private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
  private JScrollPane jScrollPane5 = new JScrollPane();
  private JList lstAttribute1 = new JList(modellstAttribute);
  private JButton btnCheck = new JButton();
  private JTable table;
  private String[] qarr=new String[0];
  private QueryTableModel qtm;
  private JButton btnCheck1 = new JButton();
  private JToolBar jToolBar1 = new JToolBar();
  private JComboBox cmbIncDep = new JComboBox();
  private JButton btnFirst = new JButton();
  private JButton btnPrev = new JButton();
  private JButton btnNext = new JButton();
  private JButton btnLast = new JButton();
  private boolean dispose=false;

 public InclusionDep()
  {
    this(null, "",-1, false,null,null);
  }

  public InclusionDep(IISFrameMain parent, String title,int m, boolean modal, Connection conn, PTree tr)
  {
    super(parent, title, modal);
    try
    { tree=tr;
      ID=m;
      con=conn; 
             Iterator it=tr.WindowsManager.iterator();
      while(it.hasNext())
      {
        Object obj=(Object)it.next();
        Class cls=obj.getClass();
        if(cls==this.getClass())
        { 
        ((InclusionDep)obj).dispose();
         
        }
      }
      qtm=new QueryTableModel(con, m);
      jbInit();
    table.getColumnModel().getColumn(0).setPreferredWidth(20);
    table.setShowGrid(false);
    table.getColumnModel().getColumn(0).setMaxWidth(20);
    table.getColumnModel().getColumn(0).setMinWidth(20);  
    table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    table.getColumnModel().getColumn(0).setCellRenderer(new MyRenderer()); 
    table.getColumnModel().getColumn(1).setCellRenderer(new MyRenderer()); 
     
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {  this.setResizable(false);
    this.setSize(new Dimension(463, 477));
    this.getContentPane().setLayout(null);
    this.setTitle("Inclusion Dependencies");
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Cancel");
    btnClose.setBounds(new Rectangle(330, 400, 80, 30));
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
    btnSave.setBounds(new Rectangle(250, 400, 75, 30));
    btnSave.setMinimumSize(new Dimension(50, 30));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save_ActionPerformed(ae);
        }
      });
    btnNew.setMaximumSize(new Dimension(50, 30));
    btnNew.setPreferredSize(new Dimension(50, 30));
    btnNew.setText("New");
    btnNew.setBounds(new Rectangle(85, 400, 80, 30));
    btnNew.setMinimumSize(new Dimension(50, 30));
    btnNew.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          new_ActionPerformed(ae);
        }
      });
    btnErase.setMaximumSize(new Dimension(50, 30));
    btnErase.setPreferredSize(new Dimension(50, 30));
    btnErase.setText("Delete");
    btnErase.setBounds(new Rectangle(170, 400, 75, 30));
    btnErase.setMinimumSize(new Dimension(50, 30));
    btnErase.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          erase_ActionPerformed(ae);
        }
      });
    
    txtName.setBounds(new Rectangle(50, 50, 395, 20));
    txtName.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          txtName_actionPerformed(e);
        }
      });
    jLabel13.setText("Name:");
    jLabel13.setBounds(new Rectangle(10, 50, 65, 20));
    jScrollPane2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lstAttribute.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    jScrollPane3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jScrollPane4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lstLHS.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    lstRHS.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    btnDown.setMaximumSize(new Dimension(60, 60));
    btnDown.setPreferredSize(new Dimension(25, 20));
    btnDown.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          down_ActionPerformed(ae);
        }
      });
    btnDown.setBounds(new Rectangle(425, 135, 25, 20));
    btnDown.setFont(new Font("SansSerif", 0, 11));
    btnDown.setIcon(iconDown);
    btnUp.setMaximumSize(new Dimension(60, 60));
    btnUp.setPreferredSize(new Dimension(25, 20));
    btnUp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          up_ActionPerformed(ae);
        }
      });
    btnUp.setBounds(new Rectangle(425, 105, 25, 20));
    btnUp.setFont(new Font("SansSerif", 0, 11));
    btnUp.setIcon(iconUp);
    btnUp1.setMaximumSize(new Dimension(60, 60));
    btnUp1.setPreferredSize(new Dimension(25, 20));
    btnUp1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          up1_ActionPerformed(ae);
        }
      });
    btnUp1.setBounds(new Rectangle(425, 210, 25, 20));
    btnUp1.setFont(new Font("SansSerif", 0, 11));
    btnUp1.setIcon(iconUp);
    btnDown1.setMaximumSize(new Dimension(60, 60));
    btnDown1.setPreferredSize(new Dimension(25, 20));
    btnDown1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          down1_ActionPerformed(ae);
        }
      });
    btnDown1.setBounds(new Rectangle(425, 240, 25, 20));
    btnDown1.setFont(new Font("SansSerif", 0, 11));
    btnDown1.setIcon(iconDown);
    jScrollPane5.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    jScrollPane5.setBounds(new Rectangle(10, 305, 410, 85));
    lstAttribute1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    lstAttribute1.setBounds(new Rectangle(115, 105, 0, 0));
    btnCheck.setMaximumSize(new Dimension(60, 60));
    btnCheck.setPreferredSize(new Dimension(25, 20));
    btnCheck.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          check_ActionPerformed(ae);
        }
      });
    btnCheck.setBounds(new Rectangle(425, 305, 25, 20));
    btnCheck.setFont(new Font("SansSerif", 0, 11));
    btnCheck.setIcon(iconCheck);
    btnCheck.setToolTipText("Check domain compatibility");
 
    jScrollPane2.getViewport();
    jScrollPane3.getViewport();
    jScrollPane3.getViewport();
    jScrollPane4.getViewport();
    jScrollPane4.getViewport();
    jScrollPane5.getViewport();
    jScrollPane5.getViewport();
    
     table=new JTable(qtm);
     qtm.image=2;
    qtm.setQueryLog(qarr);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setRowSelectionAllowed(true);
    table.setGridColor(new Color(0,0,0));
    table.setBackground(Color.white);
    table.setAutoscrolls(true);
    table.getTableHeader().setReorderingAllowed(false);
    table.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    Dimension d=new Dimension(100,15);
    btnCheck1.setMaximumSize(new Dimension(60, 60));
    btnCheck1.setPreferredSize(new Dimension(25, 20));
    btnCheck1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          eraselog_ActionPerformed(ae);
        }
      });
    btnCheck1.setBounds(new Rectangle(425, 330, 25, 20));
    btnCheck1.setFont(new Font("SansSerif", 0, 11));
    btnCheck1.setIcon(iconErase);
    btnCheck1.setToolTipText("Clear Log");
    jToolBar1.setFont(new Font("Verdana", 0, 11));
    jToolBar1.setLayout(null);
    jToolBar1.setPreferredSize(new Dimension(249, 60));
    jToolBar1.setFloatable(false);
    jToolBar1.setBounds(new Rectangle(15, 5, 440, 35));
    cmbIncDep.setFont(new Font("SansSerif", 0, 11));
    cmbIncDep.setBounds(new Rectangle(100, 5, 230, 20));

    cmbIncDep.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jComboBox1_actionPerformed(e);
        }
      });
    btnFirst.setMaximumSize(new Dimension(60, 60));
    btnFirst.setPreferredSize(new Dimension(25, 20));
    btnFirst.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          prevv_ActionPerformed(ae);
        }
      });
    btnFirst.setBounds(new Rectangle(40, 5, 25, 20));
    btnFirst.setIcon(iconPrevv);
    btnPrev.setMaximumSize(new Dimension(60, 60));
    btnPrev.setPreferredSize(new Dimension(25, 20));
    btnPrev.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          prev_ActionPerformed(ae);
        }
      });
    btnPrev.setBounds(new Rectangle(70, 5, 25, 20));
    btnPrev.setIcon(iconPrev);
    btnNext.setMaximumSize(new Dimension(60, 60));
    btnNext.setPreferredSize(new Dimension(25, 20));
    btnNext.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          next_ActionPerformed(ae);
        }
      });
    btnNext.setBounds(new Rectangle(335, 5, 25, 20));
    btnNext.setIcon(iconNext);
    btnLast.setMaximumSize(new Dimension(60, 60));
    btnLast.setPreferredSize(new Dimension(25, 20));
    btnLast.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          nextt_ActionPerformed(ae);
        }
      });
    btnLast.setBounds(new Rectangle(365, 5, 25, 20));
    btnLast.setIcon(iconNextt);
    table.getTableHeader().setPreferredSize(d);
 
    jToolBar1.add(cmbIncDep, null);
    jToolBar1.add(btnFirst, null);
    jToolBar1.add(btnPrev, null);
    jToolBar1.add(btnNext, null);
    jToolBar1.add(btnLast, null);
    this.getContentPane().add(jToolBar1, null);
    this.getContentPane().add(btnCheck1, null);
    this.getContentPane().add(btnCheck, null);
    this.getContentPane().add(lstAttribute1, null);
    jScrollPane5.getViewport().add(table, null);
    this.getContentPane().add(jScrollPane5, null);
    this.getContentPane().add(btnDown1, null);
    this.getContentPane().add(btnUp1, null);
    this.getContentPane().add(btnUp, null);
    this.getContentPane().add(btnDown, null);
    this.getContentPane().add(btnRem1, null);
    this.getContentPane().add(btnAdd1, null);
    this.getContentPane().add(btnRem, null);
    this.getContentPane().add(btnAdd, null);
    this.getContentPane().add(jLabel16, null);
    this.getContentPane().add(jLabel15, null);
    jScrollPane4.getViewport().add(lstRHS, null);
    this.getContentPane().add(jScrollPane4, null);
    jScrollPane3.getViewport().add(lstLHS, null);
    this.getContentPane().add(jScrollPane3, null);
    jScrollPane2.getViewport().add(lstAttribute, null);
    this.getContentPane().add(jScrollPane2, null);
    this.getContentPane().add(jLabel14, null);
    this.getContentPane().add(btnApply, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(jLabel13, null);
     
    this.getContentPane().add(txtName, null);
    this.getContentPane().add(btnErase, null);
    this.getContentPane().add(btnNew, null);
    this.getContentPane().add(btnSave, null);
    this.getContentPane().add(btnClose, null);
    
    btnRem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRem1_actionPerformed(e);
        }
      });
    btnRem1.setFont(new Font("SansSerif", 0, 14));
    btnRem1.setText(">");
    btnRem1.setBounds(new Rectangle(190, 220, 50, 30));
    btnAdd1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnAdd1_actionPerformed(e);
        }
      });
    btnAdd1.setFont(new Font("SansSerif", 0, 14));
    btnAdd1.setText("<");
    btnAdd1.setBounds(new Rectangle(190, 255, 50, 30));
    btnRem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRem_actionPerformed(e);
        }
      });
    btnRem.setFont(new Font("SansSerif", 0, 14));
    btnRem.setText(">");
    btnRem.setBounds(new Rectangle(190, 110, 50, 30));
    btnAdd.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnAdd_actionPerformed(e);
        }
      });
    btnAdd.setFont(new Font("SansSerif", 0, 14));
    btnAdd.setText("<");
    btnAdd.setBounds(new Rectangle(190, 145, 50, 30));
    jLabel16.setFont(new Font("SansSerif", 0, 11));
    jLabel16.setBounds(new Rectangle(255, 190, 105, 20));
    jLabel16.setText("Right side attributes:");
    jLabel15.setText("Left side attributes:");
    jLabel15.setBounds(new Rectangle(255, 80, 115, 20));
    jLabel15.setFont(new Font("SansSerif", 0, 11));
    jScrollPane4.setBounds(new Rectangle(255, 210, 165, 80));
     jScrollPane3.setBounds(new Rectangle(255, 105, 165, 80));
  
    jScrollPane2.setBounds(new Rectangle(10, 105, 165, 185));
    jLabel14.setFont(new Font("SansSerif", 0, 11));
    jLabel14.setBounds(new Rectangle(10, 80, 65, 20));
    jLabel14.setText("Attributes:");
 
    btnClose.setFont(new Font("SansSerif", 0, 11));
    btnSave.setFont(new Font("SansSerif", 0, 11));
    btnNew.setFont(new Font("SansSerif", 0, 11));
    btnErase.setFont(new Font("SansSerif", 0, 11));
    txtName.setFont(new Font("SansSerif", 0, 11));
    jLabel13.setFont(new Font("SansSerif", 0, 11));
    btnHelp.setFont(new Font("SansSerif", 0, 11));
 
    txtName.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          txtName_keyPressed(e);
        }
      });
    btnApply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        { dispose=false;
          apply_ActionPerformed(ae);
        }
      });
    btnApply.setFont(new Font("SansSerif", 0, 11));
    btnApply.setMinimumSize(new Dimension(50, 30));
    btnApply.setBounds(new Rectangle(5, 400, 75, 30));
    btnApply.setText("Apply");
    btnApply.setPreferredSize(new Dimension(50, 30));
    btnApply.setMaximumSize(new Dimension(50, 30));
    btnHelp.setIcon(imageHelp);
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    btnHelp.setBounds(new Rectangle(415, 400, 35, 30));
   
    SetDepInclusion(ID);
    }

  private void close_ActionPerformed(ActionEvent e)
  { 
  if(btnSave.isEnabled())
  if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
  apply_ActionPerformed(e);
  this.dispose();
  }
  public void actionPerformed(ActionEvent e)
  {
  }
  public void Reload ()
{
SetDependency();
}
  private void apply_ActionPerformed(ActionEvent e)
  {
     JDBCQuery query=new JDBCQuery(con);
     ResultSet rs;
     try
    {  
      if(cmbIncDep.getSelectedItem().equals(""))
      rs=query.select("select * from IISC_INCLUSION_DEPENDENCY where ID_name='"+ txtName.getText().toString() + "' and PR_id="+ tree.ID);
      else
      rs=query.select("select * from IISC_INCLUSION_DEPENDENCY where ID_name='"+ txtName.getText().toString() + "' and ID_id<>"+ ID +"  and PR_id="+ tree.ID);
      if(rs.next())
      { JOptionPane.showMessageDialog(null, "<html><center>Inclusion name exists!", "Inclusion Dependencies", JOptionPane.ERROR_MESSAGE);
       query.Close();
      }
      else
      {
      Set l=new HashSet();
      Set r=new HashSet();
      for(int i=0;i< modellstLHS.getSize();i++)
      l.add(modellstLHS.getElementAt(i));
      for(int i=0;i< modellstRHS.getSize();i++)
      r.add(modellstRHS.getElementAt(i));
      boolean can=true;
      for(int i=0;i< modellstLHS.getSize()-1;i++)
       for(int j=i+1;j< modellstRHS.getSize();j++)
       {
         if(modellstLHS.getElementAt(i).toString().equals(modellstRHS.getElementAt(j).toString()))can=false;
       }
      if(txtName.getText().trim().equals(""))
      JOptionPane.showMessageDialog(null, "<html><center>Name required!", "Inclusion Dependencies", JOptionPane.ERROR_MESSAGE);
      else if(l.toString().equals(r.toString()))
      JOptionPane.showMessageDialog(null, "<html><center>Left and right side contain equal sets!", "Inclusion Dependencies", JOptionPane.ERROR_MESSAGE);
      else if(!can)
      JOptionPane.showMessageDialog(null, "<html><center>Left or right side contain attribute in wrong place!", "Inclusion Dependencies", JOptionPane.ERROR_MESSAGE);
      else {
      if(lstLHS.getModel().getSize()!=lstRHS.getModel().getSize()  || lstLHS.getModel().getSize()==0)
      JOptionPane.showMessageDialog(null, "<html><center>Wrong number of attributes on left and right side of inclusion dependency!", "Inclusion Dependencies", JOptionPane.ERROR_MESSAGE);
      else
      {
     String[] left=new String[lstLHS.getModel().getSize()];
      String[] right=new String[lstLHS.getModel().getSize()];
       for(int j=0;j<lstLHS.getModel().getSize();j++)
      { String attl= lstLHS.getModel().getElementAt(j).toString();
        String attr= lstRHS.getModel().getElementAt(j).toString();
        rs=query.select("select * from IISC_Attribute where Att_mnem='"+attl+"'  and PR_id="+ tree.ID ); 
        rs.next();
        int al=rs.getInt("Dom_id");
        left[j]=""+rs.getInt("Att_id");
        query.Close();
        rs=query.select("select * from IISC_Attribute where Att_mnem='"+attr+"'  and PR_id="+ tree.ID ); 
        rs.next();
        int ar=rs.getInt("Dom_id");
        right[j]=""+rs.getInt("Att_id");
        query.Close();}
      if(cmbIncDep.getSelectedItem().equals(""))
      {
      rs=query.select("select max(ID_id)+1 from IISC_INCLUSION_DEPENDENCY"); 
      int j=0;
      if(rs.next())
      j=rs.getInt(1);
      query.Close();
      query.update("insert into IISC_INCLUSION_DEPENDENCY(ID_id,PR_id,ID_name) values("+j+","+tree.ID+",'"+txtName.getText().toString()+"')");
      ID=j;
     
     
      }
      else
      {query.update("update IISC_INCLUSION_DEPENDENCY set ID_name='"+txtName.getText().toString()+"' where ID_id="+ID  +"  and PR_id="+ tree.ID );
      query.update("delete from IISC_INC_DEP_LHS_RHS   where ID_id="+ID  +"  and PR_id="+ tree.ID );
      }
      for(int j=0;j<left.length;j++)
      query.update("insert into IISC_INC_DEP_LHS_RHS(ID_id,Att_id,PR_id,ID_lhs_rhs,Att_rbr) values("+ID+","+ left[j] +","+tree.ID+",0,"+(j+1)+")");
       for(int j=0;j<right.length;j++)
      query.update("insert into IISC_INC_DEP_LHS_RHS(ID_id,Att_id,PR_id,ID_lhs_rhs,Att_rbr) values("+ID+","+ right[j] +","+tree.ID+",1,"+(j+1)+")");
      if(left.length==right.length && left.length==1)
      query.update("update IISC_ATTRIBUTE set   Att_elem=" + right[0] + "  where Att_id="+ left[0] ); 
     check_ActionPerformed(e);
     JOptionPane.showMessageDialog(null, "<html><center>Inclusion dependency saved!", "Inclusion Dependencies", JOptionPane.INFORMATION_MESSAGE);
     tree.insert(txtName.getText().toString(),"Inclusion Dependencies"); 
     SetDepInclusion(ID);
      if(dispose)
      dispose();
      }
      }
       
      }


    }
     catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  }
  

  private void new_ActionPerformed(ActionEvent e)
  {
  SetDepInclusion(-1);
  }

  private void erase_ActionPerformed(ActionEvent e)
  {
  if(!cmbIncDep.getSelectedItem().equals(""))
  {
     JDBCQuery query=new JDBCQuery(con);
     ResultSet rs;
     try
    { 
   rs=query.select("select * from IISC_INCLUSION_DEPENDENCY where ID_name='"+ cmbIncDep.getSelectedItem() +"' and PR_id="+ tree.ID);
   if(rs.next())
    {int idi=rs.getInt("ID_id");
    query.Close();   
    rs=query.select("select a.Att_id,b.Att_id, count(*) from IISC_INC_DEP_LHS_RHS as a,IISC_INC_DEP_LHS_RHS as b  where a.ID_id="+ idi +" and a.ID_lhs_rhs=0 and b.ID_lhs_rhs=1 and a.PR_id="+ tree.ID + " and b.ID_id="+ idi +" and b.PR_id="+ tree.ID + " group by a.Att_id,b.Att_id");
   if(rs.next())
   {
    int idc=rs.getInt(3);
    int at=rs.getInt(1);
    query.Close();  
    if(idc==1)
    {
    query.update("update IISC_ATTRIBUTE set Att_elem=-1  where Att_id="+ at);   
    }
   }
   else
   query.Close();
     query.update("delete from IISC_INCLUSION_DEPENDENCY where ID_id="+ idi);
      query.update("delete from IISC_INC_DEP_LHS_RHS where ID_id="+ idi);
       tree.removenode(txtName.getText().toString(),"Inclusion Dependencies"); 
       SetDepInclusion(-1);
      JOptionPane.showMessageDialog(null, "<html><center>Inclusion dependency removed!", "Inclusion Dependencies", JOptionPane.INFORMATION_MESSAGE);
  
    } 
    else
    query.Close();
    }
     catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  }
  }
  public void itemStateChanged(ItemEvent event )
  {
   
  }
  private void SetDep()
  {JDBCQuery query=new JDBCQuery(con);
ResultSet rs;
   try
    { cmbIncDep.removeAllItems();;
    rs=query.select("select count(*) from IISC_INCLUSION_DEPENDENCY  where PR_id="+ tree.ID + "  ");
     if( rs.next())
     { int j=rs.getInt(1);
     query.Close();
     String[] sa=query.selectArray("select * from IISC_INCLUSION_DEPENDENCY where PR_id="+ tree.ID + " order by  ID_name asc ",j,3);
    for(j=0;j<sa.length;j++)
     cmbIncDep.addItem(sa[j]);
     }     
       query.Close();
    
    }
      catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  }
   private void SetDepInclusion(int i)
  {JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
   try
    { 
    btnSave.setEnabled(false);
     btnApply.setEnabled(false);
    table.getColumnModel().getColumn(0).setPreferredWidth(20);
    table.getColumnModel().getColumn(0).setMaxWidth(20);
    table.getColumnModel().getColumn(0).setMinWidth(20);
    SetDependency();
    if(i>=0)
   {  rs=query.select("select * from IISC_INCLUSION_DEPENDENCY  where ID_id="+ i +" and PR_id="+ tree.ID);
     if(rs.next())
     txtName.setText(rs.getString("ID_name"));
    cmbIncDep.setSelectedItem( txtName.getText() );
     query.Close();
    SetLHS_RHS(i);
    ID=i;
    tree.select_node(txtName.getText(),"Inclusion Dependencies");
   }
   else
   { tree.select_node("","Inclusion Dependencies");
   ID=-1;
   int k=0;
  
    boolean go=true;
    while(go)
     {k++;
     rs=query.select("select * from IISC_INCLUSION_DEPENDENCY  where ID_name='Inclusion Dependency "+ k +"' and PR_id="+ tree.ID);
     if(!rs.next())
     {query.Close();
     go=false;
     }
     else
     query.Close();
     }
   
   
   txtName.setText("Inclusion Dependency "+ k );
   cmbIncDep.setSelectedItem(""); 
   modellstLHS.removeAllElements();
    modellstRHS.removeAllElements(); }
  
  rs=query.select("select * from IISC_INCLUSION_DEPENDENCY  where PR_id="+ tree.ID +" and ID_name>'" + cmbIncDep.getSelectedItem().toString() + "'" );
if(rs.next())
{btnNext.setEnabled(true);
btnLast.setEnabled(true);}
else
{btnNext.setEnabled(false);
btnLast.setEnabled(false);}
query.Close();
rs=query.select("select * from IISC_INCLUSION_DEPENDENCY where PR_id="+ tree.ID +" and  ID_name<'" + cmbIncDep.getSelectedItem().toString() + "'" );
if(rs.next())
{btnPrev.setEnabled(true);
btnFirst.setEnabled(true);}
else
{btnPrev.setEnabled(false);
btnFirst.setEnabled(false);}
query.Close(); 
   }
   catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
    SetAtt(i);
  
   
  }
 private void SetAtt(int i)
  {JDBCQuery query=new JDBCQuery(con);
ResultSet rs;
   try
    {rs=query.select("select count(*) from IISC_Attribute  where PR_id="+ tree.ID);
      rs.next(); 
      int j=rs.getInt(1);
     query.Close();
     String[] sa=query.selectArray1("select * from IISC_Attribute where PR_id="+ tree.ID,j,3);
     query.Close();
     String[] said=query.selectArray1("select * from IISC_Attribute where PR_id="+ tree.ID,j,1);
     query.Close();
     Set att=new HashSet();
   for(j=0;j<sa.length;j++)
   { 
   // rs=query.select("select * from IISC_INC_DEP_LHS_RHS where   IISC_INC_DEP_LHS_RHS.ID_id="+ i +" and Att_id="+said[j]+" and PR_id="+ tree.ID);
   // if(!rs.next()) 
    att.add(sa[j]); 
   // query.Close();
   }
   
 Object[] satt=sortArray(Collator.getInstance(),att.toArray());
 modellstAttribute.removeAllElements();
   for(j=0;j<satt.length;j++)
     modellstAttribute.addElement(satt[j]);
    }
      catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  } 
 private Object[] sortArray(Collator collator, Object[] strArray) {
   String tmp;
   if (strArray.length == 1) return strArray;
   for (int i = 0; i < strArray.length; i++) {
    for (int j = i + 1; j < strArray.length; j++) {
      if( collator.compare(strArray[i], strArray[j] ) > 0 ) {
        tmp = strArray[i].toString();
        strArray[i] = strArray[j];
        strArray[j] = tmp;
        }
      }
    } 
    return strArray;
   }

    private void SetLHS_RHS(int i)
  {JDBCQuery query=new JDBCQuery(con);
ResultSet rs;
   try
    {  modellstLHS.removeAllElements();
      modellstRHS.removeAllElements();
      rs=query.select("select count(*) from IISC_Attribute,IISC_INC_DEP_LHS_RHS where IISC_INC_DEP_LHS_RHS.Att_id=IISC_Attribute.Att_id and IISC_INC_DEP_LHS_RHS.ID_id="+ i +" and ID_lhs_rhs=0 and IISC_INC_DEP_LHS_RHS.PR_id="+ tree.ID);
      if(rs.next()) 
      {int j=rs.getInt(1);
      query.Close();
      String[] sa=query.selectArray1("select * from IISC_Attribute,IISC_INC_DEP_LHS_RHS where IISC_INC_DEP_LHS_RHS.Att_id=IISC_Attribute.Att_id and IISC_INC_DEP_LHS_RHS.ID_id="+ i +" and ID_lhs_rhs=0 and IISC_INC_DEP_LHS_RHS.PR_id="+ tree.ID+" order by Att_rbr",j,3);
      for(j=0;j<sa.length;j++)
      modellstLHS.addElement(sa[j]); 
      }
      query.Close();
      rs=query.select("select count(*) from IISC_Attribute,IISC_INC_DEP_LHS_RHS where IISC_INC_DEP_LHS_RHS.Att_id=IISC_Attribute.Att_id and IISC_INC_DEP_LHS_RHS.ID_id="+ i +" and ID_lhs_rhs=1 and IISC_INC_DEP_LHS_RHS.PR_id="+ tree.ID);
       if(rs.next()) 
      {int j=rs.getInt(1);
      query.Close();
      String[] sa1=query.selectArray1("select * from IISC_Attribute,IISC_INC_DEP_LHS_RHS where IISC_INC_DEP_LHS_RHS.Att_id=IISC_Attribute.Att_id and IISC_INC_DEP_LHS_RHS.ID_id="+ i +" and ID_lhs_rhs=1 and IISC_INC_DEP_LHS_RHS.PR_id="+ tree.ID+" order by Att_rbr",j,3);
      for(j=0;j<sa1.length;j++)
      modellstRHS.addElement(sa1[j]); 
      }
      query.Close();

    }
      catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  }
   private void SetDependency()
  {
  SetDep();
  JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
   
  }

  private void btnHelp_actionPerformed(ActionEvent e)
  {Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void save_ActionPerformed(ActionEvent e)
  {dispose=true;
  apply_ActionPerformed(e);
  
  
  }

  private void txtName_keyPressed(KeyEvent e)
  {btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }

  private void btnAdd_actionPerformed(ActionEvent e)
  { if(lstLHS.getSelectedValue().toString().length()>0)
  {
  Object[] sel=lstLHS.getSelectedValues();
  for(int j=0;j<sel.length;j++)
  {modellstLHS.removeElement(sel[j]);
   for(int k=0;k<modellstAttribute.size();k++)
   if(modellstAttribute.getElementAt(k).toString().compareTo(sel[j].toString())>0)
   {//modellstAttribute.add(k,sel[j]);
   break;
   }
   }
  }
  btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }

  private void btnRem_actionPerformed(ActionEvent e)
  {
  if(lstAttribute.getSelectedValue().toString().length()>0)
  {
  Object[] sel=lstAttribute.getSelectedValues();
  for(int j=0;j<sel.length;j++)
  {if(!modellstLHS.contains((Object)sel[j]))
    modellstLHS.addElement(sel[j]);
   //modellstAttribute.removeElement(sel[j]);
   }
  }
  btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }

 
  private void btnRem1_actionPerformed(ActionEvent e)
  { if(lstAttribute.getSelectedValue().toString().length()>0)
  {
  Object[] sel=lstAttribute.getSelectedValues();
  for(int j=0;j<sel.length;j++)
  {if(!modellstRHS.contains((Object)sel[j]))
  modellstRHS.addElement(sel[j]);
  // modellstAttribute.removeElement(sel[j]);
  }
  }
  btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }

  private void btnAdd1_actionPerformed(ActionEvent e)
  { if(lstRHS.getSelectedValue().toString().length()>0)
  {
  Object[] sel=lstRHS.getSelectedValues();
  for(int j=0;j<sel.length;j++)
  {modellstRHS.removeElement(sel[j]);
 for(int k=0;k<modellstAttribute.size();k++)
   if(modellstAttribute.getElementAt(k).toString().compareTo(sel[j].toString())>0)
   {//modellstAttribute.add(k,sel[j]);
   break;
   }
   
   }
  }
  btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }

  private void txtName_actionPerformed(ActionEvent e)
  {
  }

  private void lstDependency_propertyChange(PropertyChangeEvent e)
  {   }

  private void down_ActionPerformed(ActionEvent e)
  {
   if(!lstLHS.isSelectionEmpty() && lstLHS.getSelectedIndex()<(lstLHS.getModel().getSize()-1))
  {
  Object[] lst = new Object[lstLHS.getModel().getSize()];
  for(int i = 0; i < lstLHS.getModel().getSize(); i++)  
    lst[i]=lstLHS.getModel().getElementAt(i);
  Object pom=modellstLHS.getElementAt(lstLHS.getSelectedIndex());
   modellstLHS.setElementAt(modellstLHS.getElementAt((lstLHS.getSelectedIndex()+1)),lstLHS.getSelectedIndex()) ;
  modellstLHS.setElementAt(pom,(lstLHS.getSelectedIndex()+1));
 // lstLHS.setListData(lst);
  lstLHS.setSelectedValue(pom,true);
   btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }
  }

  private void up_ActionPerformed(ActionEvent e)
  {
  if(!lstLHS.isSelectionEmpty() && lstLHS.getSelectedIndex()>0)
  {
  Object[] lst = new Object[lstLHS.getModel().getSize()];
  for(int i = 0; i < lstLHS.getModel().getSize(); i++)  
    lst[i]=lstLHS.getModel().getElementAt(i);
  Object pom=modellstLHS.getElementAt(lstLHS.getSelectedIndex());
  modellstLHS.setElementAt(modellstLHS.getElementAt((lstLHS.getSelectedIndex()-1)),lstLHS.getSelectedIndex()) ;
  modellstLHS.setElementAt(pom,(lstLHS.getSelectedIndex()-1));
 // lstLHS.setListData(lst);
  lstLHS.setSelectedValue(pom,true);
   btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }
  }
    private void down1_ActionPerformed(ActionEvent e)
  {
   if(!lstRHS.isSelectionEmpty() && lstRHS.getSelectedIndex()<(lstRHS.getModel().getSize()-1))
  {
  Object[] lst = new Object[lstRHS.getModel().getSize()];
  for(int i = 0; i < lstRHS.getModel().getSize(); i++)  
    lst[i]=lstRHS.getModel().getElementAt(i);
  Object pom=modellstRHS.getElementAt(lstRHS.getSelectedIndex());
   modellstRHS.setElementAt(modellstRHS.getElementAt((lstRHS.getSelectedIndex()+1)),lstRHS.getSelectedIndex()) ;
  modellstRHS.setElementAt(pom,(lstRHS.getSelectedIndex()+1));
  lstRHS.setListData(lst);
  lstRHS.setSelectedValue(pom,true);
   btnSave.setEnabled(true);
   btnApply.setEnabled(true);}
  }

  private void up1_ActionPerformed(ActionEvent e)
  {
  if(!lstRHS.isSelectionEmpty() && lstRHS.getSelectedIndex()>0)
  {
  Object[] lst = new Object[lstRHS.getModel().getSize()];
  for(int i = 0; i < lstRHS.getModel().getSize(); i++)  
    lst[i]=lstRHS.getModel().getElementAt(i);
  Object pom=modellstRHS.getElementAt(lstRHS.getSelectedIndex());
  modellstRHS.setElementAt(modellstRHS.getElementAt((lstRHS.getSelectedIndex()-1)),lstRHS.getSelectedIndex()) ;
  modellstRHS.setElementAt(pom,(lstRHS.getSelectedIndex()-1));
 // lstRHS.setListData(lst);
  lstRHS.setSelectedValue(pom,true);
  btnSave.setEnabled(true);
   btnApply.setEnabled(true); }
  }

  private void check_ActionPerformed(ActionEvent e)
  {    JDBCQuery query=new JDBCQuery(con);
     ResultSet rs;
     try
    { 
      boolean can=true;
      String war=new String();
      String[] left=new String[lstLHS.getModel().getSize()];
      String[] right=new String[lstLHS.getModel().getSize()];
      Set l=new HashSet();
      Set r=new HashSet();
      for(int i=0;i< modellstLHS.getSize();i++)
      l.add(modellstLHS.getElementAt(i));
      for(int i=0;i< modellstRHS.getSize();i++)
      r.add(modellstRHS.getElementAt(i));
      for(int i=0;i< modellstLHS.getSize()-1;i++)
       for(int j=i+1;j< modellstRHS.getSize();j++)
       {
         if(modellstLHS.getElementAt(i).toString().equals(modellstRHS.getElementAt(j).toString()))can=false;
       }
      if(txtName.getText().trim().equals(""))
      JOptionPane.showMessageDialog(null, "<html><center>Name required!", "Inclusion Dependencies", JOptionPane.ERROR_MESSAGE);
      else if(l.toString().equals(r.toString()))
      JOptionPane.showMessageDialog(null, "<html><center>Left and right side contain equal sets!", "Inclusion Dependencies", JOptionPane.ERROR_MESSAGE);
      else if(!can)
      JOptionPane.showMessageDialog(null, "<html><center>Left or right side contain attribute in wrong place!", "Inclusion Dependencies", JOptionPane.ERROR_MESSAGE);
      else  if(lstLHS.getModel().getSize()!=lstRHS.getModel().getSize()  || lstLHS.getModel().getSize()==0)
      JOptionPane.showMessageDialog(null, "<html><center>Wrong number of attributes on left and right side of inclusion dependency!", "Inclusion Dependencies", JOptionPane.ERROR_MESSAGE);
      else
     { for(int j=0;j<lstLHS.getModel().getSize();j++)
      {
       String attl= lstLHS.getModel().getElementAt(j).toString();
       String attr= lstRHS.getModel().getElementAt(j).toString();
        rs=query.select("select * from IISC_Attribute where Att_mnem='"+attl+"'  and PR_id="+ tree.ID ); 
        rs.next();
        int al=rs.getInt("Dom_id");
        left[j]=""+rs.getInt("Att_id");
        query.Close();
        rs=query.select("select * from IISC_Attribute where Att_mnem='"+attr+"'  and PR_id="+ tree.ID ); 
        rs.next();
        int ar=rs.getInt("Dom_id");
        right[j]=""+rs.getInt("Att_id");
        query.Close();
       if(ar!=al)
       {can=false;
       }
      if(!can)
       war=  "Attributes " + attl + " and  " + attr + " are not domain compatible!"; 
      else
       war="";
      if(!war.equals("")) 
      {war= " Warning: " + war; 
      String[] qa=new String[qarr.length+1];
      int k;
      for( k=0;k<qarr.length;k++)
      qa[k]=qarr[k];
      qa[k]=war;
      qarr=qa;
     
      }
      }
      if(can)
      {
      String[] qa=new String[qarr.length+1];
      int k;
      for( k=0;k<qarr.length;k++)
      qa[k]=qarr[k];
      qa[k]="Success!";
      qarr=qa;  
      }
       qtm.setQueryLog(qarr);
    table.getColumnModel().getColumn(0).setPreferredWidth(20);
    table.getColumnModel().getColumn(0).setMaxWidth(20);
    table.getColumnModel().getColumn(0).setMinWidth(20);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    table.getColumnModel().getColumn(0).setCellRenderer(new MyRenderer()); 
    table.getColumnModel().getColumn(1).setCellRenderer(new MyRenderer()); 
    table.scrollRectToVisible(getRowBounds(table,table.getRowCount()-1));
    }
  }
   catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
}
public Rectangle getRowBounds(JTable table, int row)
{  
Rectangle result = table.getCellRect(row, -1, true);    
Insets i = table.getInsets();    result.x = i.left;    
result.width = table.getWidth() - i.left - i.right;   
return result;
}

  private void eraselog_ActionPerformed(ActionEvent e)
  {
    qarr=new String[0];
    qtm.setQueryLog(qarr);
    table.getColumnModel().getColumn(0).setPreferredWidth(20);
    table.getColumnModel().getColumn(0).setMaxWidth(20);
    table.getColumnModel().getColumn(0).setMinWidth(20);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
  }

  private void jComboBox1_actionPerformed(ActionEvent e)
  {JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
  
   try
    { 
      if(cmbIncDep.getItemCount()>0 && !cmbIncDep.getSelectedItem().equals("")) 
     {
     if(btnSave.isEnabled())
     {
      if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
     apply_ActionPerformed(e);}
     rs=query.select("select * from IISC_INCLUSION_DEPENDENCY where ID_name='"+ cmbIncDep.getSelectedItem().toString() +"' and PR_id="+ tree.ID);
      rs.next(); 
      int j=rs.getInt(1);
      query.Close();
      SetDepInclusion(j);}
      else
      SetDepInclusion(-1);
      
    }
    catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}

  }

  private void prevv_ActionPerformed(ActionEvent e)
  {
  String s="";
 JDBCQuery query=new JDBCQuery(con);
ResultSet rs1;
try
{
if(btnSave.isEnabled())
if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
apply_ActionPerformed(e);

 rs1=query.select("select ID_id,ID_name from IISC_INCLUSION_DEPENDENCY where PR_id="+ tree.ID +" order by  ID_name asc");
 if(rs1.next())
{s=rs1.getString(2);
SetDepInclusion(rs1.getInt(1));
}
query.Close();
  tree.select_node(cmbIncDep.getSelectedItem().toString(),"Inclusion Dependencies");
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}


  }
private void prev_ActionPerformed(ActionEvent e)
  {
  String s=cmbIncDep.getSelectedItem().toString().trim() ;

 JDBCQuery query=new JDBCQuery(con);
ResultSet rs1;
try
{  
if(btnSave.isEnabled())
if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
apply_ActionPerformed(e);

rs1=query.select("select ID_id,ID_name from IISC_INCLUSION_DEPENDENCY  where PR_id="+ tree.ID +" and ID_name<'" + s + "' order by ID_name desc" );
if(rs1.next())
{s=rs1.getString(2);
SetDepInclusion(rs1.getInt(1));
}
query.Close();

   tree.select_node(cmbIncDep.getSelectedItem().toString(),"Inclusion Dependencies");


}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}


  }


private void next_ActionPerformed(ActionEvent e)
 {
  String s=cmbIncDep.getSelectedItem().toString().trim() ;

 JDBCQuery query=new JDBCQuery(con);
ResultSet rs1;
try
{
if(btnSave.isEnabled())
if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
apply_ActionPerformed(e);

 rs1=query.select("select ID_id,ID_name from IISC_INCLUSION_DEPENDENCY  where PR_id="+ tree.ID +" and ID_name>'" + s + "' order by ID_name asc" );
if(rs1.next())
{s=rs1.getString(2);
SetDepInclusion(rs1.getInt(1));
}
query.Close();


 
  tree.select_node(cmbIncDep.getSelectedItem().toString(),"Inclusion Dependencies");
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}

 }


  private void nextt_ActionPerformed(ActionEvent e)
 {
  String s="";
 JDBCQuery query=new JDBCQuery(con);
ResultSet rs1;
try
{
if(btnSave.isEnabled())
if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
apply_ActionPerformed(e);

 rs1=query.select("select ID_id,ID_name from IISC_INCLUSION_DEPENDENCY  where PR_id="+ tree.ID +" order by  ID_name desc");
if(rs1.next())
{s=rs1.getString(2);
SetDepInclusion(rs1.getInt(1));
}
query.Close();


 
tree.select_node(cmbIncDep.getSelectedItem().toString(),"Inclusion Dependencies");
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}


  }

}
class MyRenderer implements TableCellRenderer{

  JLabel toRender = new JLabel();

  //colours!
  Color[] pretty = new Color[]{Color.white, new Color(226,223,206)};

  public MyRenderer(){
    toRender.setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable table, Object value, boolean hasFocus, boolean isSelected, int row, int col){
    if(col==1)
    {toRender.setText(value.toString());
    toRender.setBackground( pretty[row%2] );
    }
    else
    {toRender.setIcon((ImageIcon)value);
    toRender.setBackground( pretty[row%2] );
     }
    return toRender;
  }
}
