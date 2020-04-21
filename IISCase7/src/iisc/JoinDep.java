package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
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
import javax.swing.border.BevelBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.JToolBar;
import javax.swing.JComboBox;
import java.awt.event.ItemEvent;

import javax.swing.text.html.HTMLEditorKit;

public class JoinDep extends JDialog implements ActionListener,ItemListener
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
    private int as;
    public String dialog=new String();
    private JButton btnHelp = new JButton();
    private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private JButton btnApply = new JButton();
    private JLabel jLabel14 = new JLabel();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private DefaultListModel modellstRS=new DefaultListModel();
    private JList lstAttribute = new JList(modellstRS);
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JLabel jLabel15 = new JLabel();
    private DefaultListModel modellstLHS=new DefaultListModel();
    private JList lstRS = new JList(modellstLHS);
    private JButton btnAdd = new JButton();
    private JButton btnRem = new JButton();
    private PTree tree;
    private JButton btnDown = new JButton();
    private JButton btnUp = new JButton();
    private ImageIcon iconUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
    private ImageIcon iconDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
    private ImageIcon iconCheck = new ImageIcon(IISFrameMain.class.getResource("icons/check.gif"));
    private ImageIcon iconErase = new ImageIcon(IISFrameMain.class.getResource("icons/erase.gif"));
    private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
    private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
    private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
    private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
    private JList lstAttribute1 = new JList(modellstRS);
    private String[] qarr=new String[0];
    private JToolBar jToolBar1 = new JToolBar();
    private JComboBox cmbIncDep = new JComboBox();
    private JButton btnFirst = new JButton();
    private JButton btnPrev = new JButton();
    private JButton btnNext = new JButton();
    private JButton btnLast = new JButton();
    private boolean dispose=false;
    private String appsys="";
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JEditorPane txtReport = new JEditorPane();

    public JoinDep()
  {
    this(null, "",-1, false,null,null,null);
  }

  public JoinDep(IISFrameMain parent, String title,int m, boolean modal, Connection conn, PTree tr, String s)
  {
    super(parent, title, modal);
    
    try
    { tree=tr;
      ID=m;
      con=conn; 
      appsys=s;
      JDBCQuery query=new JDBCQuery(con);
      ResultSet rs;
      try
      {      
        rs=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ s+"' and PR_id="+tree.ID);
        rs.next();
        as=rs.getInt("AS_id");
        query.Close();
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
      Iterator it=tr.WindowsManager.iterator();
      while(it.hasNext())
      {
        Object obj=(Object)it.next();
        Class cls=obj.getClass();
        if(cls==this.getClass())
        { 
        ((JoinDep)obj).dispose();
         
        }
      }
      jbInit();
     
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
    this.setTitle("Join Dependencies");
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
        lstRS.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
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
        lstAttribute1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    lstAttribute1.setBounds(new Rectangle(115, 105, 0, 0));

        jScrollPane2.getViewport();
    jScrollPane3.getViewport();
    jScrollPane3.getViewport();
        Dimension d=new Dimension(100,15);
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

        jScrollPane1.setBounds(new Rectangle(10, 300, 410, 85));
        txtReport.setEditable(false);
        txtReport.setFont(new Font("SansSerif", 0, 11));
        txtReport.setEditorKit(new HTMLEditorKit());
        txtReport.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          txtReport_mouseClicked(e);
        }
      });
        txtReport.addHyperlinkListener(new HyperlinkListener()
      {
        public void hyperlinkUpdate(HyperlinkEvent e)
        {
                        txtReport_hyperlinkUpdate(e);
                    }
      });
        jToolBar1.add(cmbIncDep, null);
    jToolBar1.add(btnFirst, null);
    jToolBar1.add(btnPrev, null);
    jToolBar1.add(btnNext, null);
    jToolBar1.add(btnLast, null);
        jScrollPane1.getViewport().add(txtReport, null);
        this.getContentPane().add(jScrollPane1, null);
        this.getContentPane().add(jToolBar1, null);
        this.getContentPane().add(lstAttribute1, null);
        this.getContentPane().add(btnUp, null);
        this.getContentPane().add(btnDown, null);
        this.getContentPane().add(btnRem, null);
        this.getContentPane().add(btnAdd, null);
        this.getContentPane().add(jLabel15, null);
        jScrollPane3.getViewport().add(lstRS, null);
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
        jLabel15.setText("Join dependency relation schemes:");
    jLabel15.setBounds(new Rectangle(255, 80, 180, 20));
    jLabel15.setFont(new Font("SansSerif", 0, 11));
        jScrollPane3.setBounds(new Rectangle(255, 105, 165, 185));
  
    jScrollPane2.setBounds(new Rectangle(10, 105, 165, 185));
    jLabel14.setFont(new Font("SansSerif", 0, 11));
    jLabel14.setBounds(new Rectangle(10, 80, 105, 20));
    jLabel14.setText("Relation Schema:");

        jLabel14.setToolTipText("null");
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
        jScrollPane1.getViewport().add(txtReport, null);
    }

  private void close_ActionPerformed(ActionEvent e)
  { 
  if(btnSave.isEnabled())
  if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Join Dependencies", JOptionPane.YES_NO_OPTION)==0)
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
      rs=query.select("select * from IISC_JOIN_DEPENDENCY where JD_name='"+ txtName.getText().toString() + "' and PR_id="+ tree.ID+" and AS_id="+as);
      else
      rs=query.select("select * from IISC_JOIN_DEPENDENCY where JD_name='"+ txtName.getText().toString() + "' and JD_id<>"+ ID +"  and PR_id="+ tree.ID+" and AS_id="+as);
      if(rs.next())
      { 
        JOptionPane.showMessageDialog(null, "<html><center>Inclusion name exists!", "Join Dependencies", JOptionPane.ERROR_MESSAGE);
        query.Close();
      }
      else
      {
      Set l=new HashSet();
      Set r=new HashSet();
      for(int i=0;i< modellstLHS.getSize();i++)
      l.add(modellstLHS.getElementAt(i));
      boolean can=true;
      if(txtName.getText().trim().equals(""))
      JOptionPane.showMessageDialog(null, "<html><center>Name required!", "Join Dependencies", JOptionPane.ERROR_MESSAGE);
      else if(lstRS.getModel().getSize()<=1)
      JOptionPane.showMessageDialog(null, "<html><center>Join dependency is trivial!", "Join Dependencies", JOptionPane.ERROR_MESSAGE);
       else {
      String[] left=new String[lstRS.getModel().getSize()];
      String[] right=new String[lstRS.getModel().getSize()];
      for(int j=0;j<lstRS.getModel().getSize();j++)
      { String Rs= lstRS.getModel().getElementAt(j).toString();
        rs=query.select("select * from IISC_RELATION_SCHEME where RS_name='"+Rs+"'  and PR_id="+ tree.ID+" and AS_id="+as ); 
        rs.next();
        left[j]=""+rs.getInt("RS_id");
        query.Close();
      }
      if(cmbIncDep.getSelectedItem().equals(""))
      {
      rs=query.select("select max(JD_id)+1 from IISC_JOIN_DEPENDENCY"); 
      int j=0;
      if(rs.next())
      j=rs.getInt(1);
      query.Close();
      query.update("insert into IISC_JOIN_DEPENDENCY(JD_id,PR_id, AS_id,JD_name) values("+j+","+tree.ID+","+as+",'"+txtName.getText().toString()+"')");
      ID=j;
      }
      else
      {query.update("update IISC_JOIN_DEPENDENCY set JD_name='"+txtName.getText().toString()+"' where JD_id="+ID  +"  and PR_id="+ tree.ID+" and AS_id="+as);
      query.update("delete from IISC_JOIN_DEP_RS   where JD_id="+ID  +"  and PR_id="+ tree.ID+" and AS_id="+as);
      }
      for(int j=0;j<left.length;j++)
      query.update("insert into IISC_JOIN_DEP_RS(JD_id,RS_id,PR_id,RS_rbr,AS_id) values("+ID+","+ left[j] +","+tree.ID+","+(j+1)+","+as+")");
       for(int j=0;j<right.length;j++)
      query.update("insert into IISC_JOIN_DEP_RS(JD_id,RS_id,PR_id,RS_rbr,AS_id) values("+ID+","+ right[j] +","+tree.ID+","+(j+1)+","+as+")");
      check_ActionPerformed(e);
      JOptionPane.showMessageDialog(null, "<html><center>Join dependency saved!", "Join Dependencies", JOptionPane.INFORMATION_MESSAGE);
      tree.insert(txtName.getText().toString(),"Join Dependencies", appsys); 
      SetDepInclusion(ID);
      if(dispose)
      dispose();
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
    delete(cmbIncDep.getSelectedItem().toString());
  }
  }
  public boolean delete(String s) {
      JDBCQuery query=new JDBCQuery(con);
      ResultSet rs;
      try
      {
      rs=query.select("select * from IISC_JOIN_DEPENDENCY where JD_name='"+ s +"' and PR_id="+ tree.ID+" and AS_id="+as);
      if(rs.next())
      {
         int idi=rs.getInt("JD_id");
         query.Close();   
         query.update("delete from IISC_JOIN_DEPENDENCY where JD_id="+ idi);
         query.update("delete from IISC_JOIN_DEP_RS where JD_id="+ idi);
         tree.removenode(txtName.getText().toString(),"Join Dependencies", appsys); 
         SetDepInclusion(-1);
         if(this.isVisible())
         JOptionPane.showMessageDialog(null, "<html><center>Join dependency removed!", "Join Dependencies", JOptionPane.INFORMATION_MESSAGE);
        return true;
      }
      else
      query.Close();
      }
      catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
      return false;
  }

  public void itemStateChanged(ItemEvent event )
  {
   
  }
  private void SetDep()
  {JDBCQuery query=new JDBCQuery(con);
ResultSet rs;
   try
    {   
        cmbIncDep.removeAllItems();;
        rs=query.select("select count(*) from IISC_JOIN_DEPENDENCY  where PR_id="+ tree.ID+" and AS_id="+as);
        if( rs.next())
        { 
            int j=rs.getInt(1);
            query.Close();
            String[] sa=query.selectArray("select * from IISC_JOIN_DEPENDENCY where PR_id="+ tree.ID+" and AS_id="+as + " order by  JD_name asc ",j,4);
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
    SetDependency();
    if(i>=0)
    {  
        rs=query.select("select * from IISC_JOIN_DEPENDENCY  where JD_id="+ i +" and PR_id="+ tree.ID+" and AS_id="+as);
        String s="";
        if(rs.next())
            s=rs.getString("JD_name");
        txtName.setText(s);
        cmbIncDep.setSelectedItem(txtName.getText());
        query.Close();
        SetLHS_RHS(i);
        ID=i;
        tree.select_node(s,"Join Dependencies", appsys);
   }
   else
   { 
    tree.select_node("","Join Dependencies", appsys);
    ID=-1;
    int k=0;
    boolean go=true;
    while(go)
     {k++;
     rs=query.select("select * from IISC_JOIN_DEPENDENCY  where JD_name='Join Dependency "+ k +"' and PR_id="+ tree.ID+" and AS_id="+as);
     if(!rs.next())
     {query.Close();
     go=false;
     }
     else
     query.Close();
     }
   txtName.setText("Join Dependency "+ k );
   cmbIncDep.setSelectedItem(""); 
   modellstLHS.removeAllElements(); }
  
    rs=query.select("select * from IISC_JOIN_DEPENDENCY  where PR_id="+ tree.ID+" and AS_id="+as +" and JD_name>'" + cmbIncDep.getSelectedItem().toString() + "'" );
    if(rs.next())
    {btnNext.setEnabled(true);
    btnLast.setEnabled(true);}
    else
    {btnNext.setEnabled(false);
    btnLast.setEnabled(false);}
    query.Close();
    rs=query.select("select * from IISC_JOIN_DEPENDENCY where PR_id="+ tree.ID+" and AS_id="+as +" and  JD_name<'" + cmbIncDep.getSelectedItem().toString() + "'" );
    if(rs.next())
    {btnPrev.setEnabled(true);
    btnFirst.setEnabled(true);}
    else
    {btnPrev.setEnabled(false);
    btnFirst.setEnabled(false);}
    query.Close(); 
   }
   catch (SQLException ex) {
    JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
   }
    SetRS(i);
    setDesc();
   
  }
private void setDesc(){
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    String str="";
    try
    { 
        String[] left=new String[lstRS.getModel().getSize()];
        for(int j=0;j<lstRS.getModel().getSize();j++)
        { String Rs= lstRS.getModel().getElementAt(j).toString();
          rs=query.select("select * from IISC_RELATION_SCHEME where RS_name='"+Rs+"'  and PR_id="+ tree.ID+" and AS_id="+as ); 
          rs.next();
          left[j]=""+rs.getInt("RS_id");
          query.Close();
          str=str+"(";
           rs=query.select("select Att_mnem, Att_rbrk from IISC_RS_KEY,IISC_RSK_ATT,IISC_ATTRIBUTE where IISC_RS_KEY.RS_primary_key=1 and IISC_RS_KEY.RS_rbrk=IISC_RSK_ATT.RS_rbrk and IISC_RS_KEY.RS_id=IISC_RSK_ATT.RS_id and IISC_RSK_ATT.Att_id=IISC_ATTRIBUTE.Att_id and IISC_RS_KEY.RS_id="+left[j]+"  and IISC_RS_KEY.PR_id="+ tree.ID+" and IISC_RS_KEY.AS_id="+as+" order by 2" );
           while(rs.next()){
              str=str+ rs.getString(1)+", ";
          }
          query.Close();
          str=str.substring(0,str.length()-2) +"), ";
        }
    }
    catch (SQLException ex) {
     JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
    }
    if(!str.equals(""))
        str="<html><b>|&gt;&lt;|</b> ("+str.substring(0,str.length()-2)+")";
    txtReport.setText(str);
}
 private void SetRS(int i)
  {
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    try
    {rs=query.select("select count(*) from IISC_RELATION_SCHEME  where PR_id="+ tree.ID+" and AS_id="+as);
      rs.next(); 
      int j=rs.getInt(1);
     query.Close();
     String[] sa=query.selectArray1("select * from IISC_RELATION_SCHEME where PR_id="+ tree.ID+" and AS_id="+as,j,4);
     query.Close();
     String[] said=query.selectArray1("select * from IISC_RELATION_SCHEME where PR_id="+ tree.ID+" and AS_id="+as,j,1);
     query.Close();
     Set att=new HashSet();
   for(j=0;j<sa.length;j++)
   { 
    att.add(sa[j]); 
   }  
    Object[] satt=sortArray(Collator.getInstance(),att.toArray());
    modellstRS.removeAllElements();
    for(j=0;j<satt.length;j++)
     if(!modellstLHS.contains(satt[j]))
        modellstRS.addElement(satt[j]);
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
  {
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    try
    {  modellstLHS.removeAllElements();
      rs=query.select("select count(*) from IISC_RELATION_SCHEME,IISC_JOIN_DEP_RS where IISC_JOIN_DEP_RS.RS_id=IISC_RELATION_SCHEME.RS_id and IISC_JOIN_DEP_RS.JD_id="+ i +" and IISC_JOIN_DEP_RS.PR_id="+ tree.ID+" and IISC_JOIN_DEP_RS.AS_id="+as);
      if(rs.next()) 
      {int j=rs.getInt(1);
      query.Close();
      String[] sa=query.selectArray1("select * from IISC_RELATION_SCHEME,IISC_JOIN_DEP_RS where IISC_JOIN_DEP_RS.RS_id=IISC_RELATION_SCHEME.RS_id and IISC_JOIN_DEP_RS.JD_id="+ i +" and IISC_JOIN_DEP_RS.PR_id="+ tree.ID+" and IISC_JOIN_DEP_RS.AS_id="+as+" order by RS_rbr",j,4);
      for(j=0;j<sa.length;j++)
      modellstLHS.addElement(sa[j]); 
      }
      query.Close();
    }
    catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  }
   private void SetDependency()
  {
  SetDep();
  }

  private void btnHelp_actionPerformed(ActionEvent e)
  {Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void save_ActionPerformed(ActionEvent e)
  {
    dispose=true;
    apply_ActionPerformed(e);
  }

  private void txtName_keyPressed(KeyEvent e)
  {
    btnSave.setEnabled(true);
    btnApply.setEnabled(true);
  }

  private void btnAdd_actionPerformed(ActionEvent e)
  { 
  if(!lstRS.isSelectionEmpty())
  if(lstRS.getSelectedValue().toString().length()>0)
  {
  Object[] sel=lstRS.getSelectedValues();
  for(int j=0;j<sel.length;j++)
  {modellstLHS.removeElement(sel[j]);
   for(int k=0;k<modellstRS.size();k++)
   if(modellstRS.getElementAt(k).toString().compareTo(sel[j].toString())>0)
   {
    modellstRS.add(k,sel[j]);
    modellstLHS.removeElement(sel[j]);
    break;
   }
   }
  }
  setDesc();
  btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }

  private void btnRem_actionPerformed(ActionEvent e)
  {
  if(!lstAttribute.isSelectionEmpty())
  if(lstAttribute.getSelectedValue().toString().length()>0)
  {
  Object[] sel=lstAttribute.getSelectedValues();
  for(int j=0;j<sel.length;j++)
  {if(!modellstLHS.contains((Object)sel[j]))
    modellstLHS.addElement(sel[j]);
    modellstRS.removeElement(sel[j]);
   }
  }
  setDesc();
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
   if(!lstRS.isSelectionEmpty() && lstRS.getSelectedIndex()<(lstRS.getModel().getSize()-1))
  {
  Object[] lst = new Object[lstRS.getModel().getSize()];
  for(int i = 0; i < lstRS.getModel().getSize(); i++)  
    lst[i]=lstRS.getModel().getElementAt(i);
  Object pom=modellstLHS.getElementAt(lstRS.getSelectedIndex());
   modellstLHS.setElementAt(modellstLHS.getElementAt((lstRS.getSelectedIndex()+1)),lstRS.getSelectedIndex()) ;
  modellstLHS.setElementAt(pom,(lstRS.getSelectedIndex()+1));
 // lstLHS.setListData(lst);
  lstRS.setSelectedValue(pom,true);
   btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }
  }

  private void up_ActionPerformed(ActionEvent e)
  {
  if(!lstRS.isSelectionEmpty() && lstRS.getSelectedIndex()>0)
  {
  Object[] lst = new Object[lstRS.getModel().getSize()];
  for(int i = 0; i < lstRS.getModel().getSize(); i++)  
    lst[i]=lstRS.getModel().getElementAt(i);
  Object pom=modellstLHS.getElementAt(lstRS.getSelectedIndex());
  modellstLHS.setElementAt(modellstLHS.getElementAt((lstRS.getSelectedIndex()-1)),lstRS.getSelectedIndex()) ;
  modellstLHS.setElementAt(pom,(lstRS.getSelectedIndex()-1));
 // lstLHS.setListData(lst);
  lstRS.setSelectedValue(pom,true);
   btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }
  }

  private void check_ActionPerformed(ActionEvent e)
  {    
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    boolean can=true;
    String war=new String();
    String[] left=new String[lstRS.getModel().getSize()];
    String[] right=new String[lstRS.getModel().getSize()];
    Set l=new HashSet();
    Set r=new HashSet();
    for(int i=0;i< modellstLHS.getSize();i++)
    l.add(modellstLHS.getElementAt(i));
    if(txtName.getText().trim().equals(""))
    JOptionPane.showMessageDialog(null, "<html><center>Name required!", "Join Dependencies", JOptionPane.ERROR_MESSAGE);
    else if(l.toString().equals(r.toString()))
    JOptionPane.showMessageDialog(null, "<html><center>Left and right side contain equal sets!", "Join Dependencies", JOptionPane.ERROR_MESSAGE);
    else if(!can)
    JOptionPane.showMessageDialog(null, "<html><center>Left or right side contain attribute in wrong place!", "Join Dependencies", JOptionPane.ERROR_MESSAGE);
}
public Rectangle getRowBounds(JTable table, int row)
{  
Rectangle result = table.getCellRect(row, -1, true);    
Insets i = table.getInsets();    result.x = i.left;    
result.width = table.getWidth() - i.left - i.right;   
return result;
}


  private void jComboBox1_actionPerformed(ActionEvent e)
  {JDBCQuery query=new JDBCQuery(con);
   ResultSet rs;
  
   try
    { 
    if(cmbIncDep.getItemCount()>0 && !cmbIncDep.getSelectedItem().toString().equals("")) 
     {
     if(btnSave.isEnabled())
     {
        if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Join Dependencies", JOptionPane.YES_NO_OPTION)==0)
        apply_ActionPerformed(e);
     }
    String s=cmbIncDep.getSelectedItem().toString();
    int j=-1;
    rs=query.select("select * from IISC_JOIN_DEPENDENCY where JD_name='"+ s +"' and PR_id="+ tree.ID+" and AS_id="+as);
    if(rs.next()) 
    j=rs.getInt(1);
    query.Close();
    SetDepInclusion(j);
    }
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
if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Join Dependencies", JOptionPane.YES_NO_OPTION)==0)
apply_ActionPerformed(e);

 rs1=query.select("select JD_id,JD_name from IISC_JOIN_DEPENDENCY where PR_id="+ tree.ID+" and AS_id="+as +" order by  JD_name asc");
 if(rs1.next())
{s=rs1.getString(2);
SetDepInclusion(rs1.getInt(1));
}
query.Close();
  tree.select_node(cmbIncDep.getSelectedItem().toString(),"Join Dependencies", appsys);
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
if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Join Dependencies", JOptionPane.YES_NO_OPTION)==0)
apply_ActionPerformed(e);

rs1=query.select("select JD_id,JD_name from IISC_JOIN_DEPENDENCY  where PR_id="+ tree.ID+" and AS_id="+as +" and JD_name<'" + s + "' order by JD_name desc" );
if(rs1.next())
{s=rs1.getString(2);
SetDepInclusion(rs1.getInt(1));
}
query.Close();

   tree.select_node(cmbIncDep.getSelectedItem().toString(),"Join Dependencies", appsys);


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
if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Join Dependencies", JOptionPane.YES_NO_OPTION)==0)
apply_ActionPerformed(e);

 rs1=query.select("select JD_id,JD_name from IISC_JOIN_DEPENDENCY  where PR_id="+ tree.ID+" and AS_id="+as +" and JD_name>'" + s + "' order by JD_name asc" );
if(rs1.next())
{s=rs1.getString(2);
SetDepInclusion(rs1.getInt(1));
}
query.Close();


 
  tree.select_node(cmbIncDep.getSelectedItem().toString(),"Join Dependencies", appsys);
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
if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Join Dependencies", JOptionPane.YES_NO_OPTION)==0)
apply_ActionPerformed(e);

 rs1=query.select("select JD_id,JD_name from IISC_JOIN_DEPENDENCY  where PR_id="+ tree.ID+" and AS_id="+as +" order by  JD_name desc");
if(rs1.next())
{s=rs1.getString(2);
SetDepInclusion(rs1.getInt(1));
}
query.Close();


 
tree.select_node(cmbIncDep.getSelectedItem().toString(),"Join Dependencies", appsys);
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}


  }

    private void txtComment_keyTyped(KeyEvent e) {
    }

    private void txtReport_hyperlinkUpdate(HyperlinkEvent e) {
    }

    private void txtReport_mouseClicked(MouseEvent e) {
    }
}
