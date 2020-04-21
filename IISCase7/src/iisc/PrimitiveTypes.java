package iisc;
import java.awt.Dimension;
import java.util.Iterator;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import java.awt.event.*;
import javax.swing.JLabel;
import java.sql.*;
import javax.swing.JRadioButton;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JToolBar;
import javax.swing.JComboBox;

public class PrimitiveTypes extends JDialog implements ActionListener,ItemListener
{
  public JButton btnClose = new JButton();
  private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
  private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
  private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
  private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif")); private JButton btnSave = new JButton();
  private JButton btnNew = new JButton();
  private JButton btnErase = new JButton();
  private JTextArea txtDescription = new JTextArea();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel11 = new JLabel();
  private JRadioButton rdYes = new JRadioButton();
  private JRadioButton rdNo = new JRadioButton();
  private JRadioButton rdOptional = new JRadioButton();
  private JTextField txtLength = new JTextField();
  private JComboBox cmbName;
  private JTextField txtDecimal = new JTextField();
  private JLabel jLabel12 = new JLabel();
  private JTextField txtName = new JTextField();
  private JLabel jLabel13 = new JLabel();
  private Connection con;
  private ButtonGroup bgrp=new ButtonGroup();
  private int ID;
  private JLabel jLabel14 = new JLabel();
  private JTextField txtDefault = new JTextField();
  public String dialog=new String();
  private JButton btnHelp = new JButton();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  private JButton btnApply = new JButton();
  public  SearchTable dlg; 
  public  String Mnem; 
   public  PTree tree;
  private boolean dispose=false;
  private JToolBar jToolBar1 = new JToolBar();
  private JButton btnFirst = new JButton();
  private JButton btnPrev = new JButton();
  private JButton btnNext = new JButton();
  private JButton btnLast = new JButton();
    private JComboBox ptBaseCmb = new JComboBox();
    private JLabel jLabel1 = new JLabel();

    public PrimitiveTypes()
  {
    this(null, "",-1, false,null,null);
  }

  public PrimitiveTypes(IISFrameMain parent, String title,int m, boolean modal, Connection conn, PTree tr )
  {
    super(parent, title, modal);
    try
    { 
      ID=m;
      con=conn;
      tree=tr;
          if(m>=0){
   
      JDBCQuery query=new JDBCQuery(con);
      ResultSet rs1;
      rs1=query.select("select * from IISC_PRIMITIVE_TYPE where  PT_id="+m);
      rs1.next();
      Mnem=rs1.getString("PT_mnemonic");
      query.Close();
    }
    else
      Mnem="";
             Iterator it=((PTree)((IISFrameMain)parent).desktop.getSelectedFrame()).WindowsManager.iterator();
      while(it.hasNext())
      {
        Object obj=(Object)it.next();
        Class cls=obj.getClass();
        if(cls==this.getClass())
        { 
          ((PrimitiveTypes)obj).dispose();
         
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
    this.setSize(new Dimension(463, 379));
    this.getContentPane().setLayout(null);
    this.setTitle("Primitive domains");
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Cancel");
    btnClose.setBounds(new Rectangle(330, 305, 80, 30));
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
    btnSave.setBounds(new Rectangle(250, 305, 75, 30));
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
    btnNew.setBounds(new Rectangle(85, 305, 80, 30));
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
    btnErase.setBounds(new Rectangle(170, 305, 75, 30));
    btnErase.setMinimumSize(new Dimension(50, 30));
    btnErase.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          erase_ActionPerformed(ae);
        }
      });
    txtDescription.setBounds(new Rectangle(100, 80, 345, 80));
    txtDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    jLabel3.setText("Description:");
    jLabel3.setBounds(new Rectangle(15, 75, 85, 20));
    jLabel11.setText("Length required:");
    jLabel11.setBounds(new Rectangle(15, 195, 95, 20));
    rdYes.setText("yes");
    rdYes.setSelected(true);
    rdYes.setBounds(new Rectangle(115, 195, 50, 25));
    rdNo.setText("no");
    rdNo.setBounds(new Rectangle(115, 215, 50, 25));
    rdOptional.setText("optional with default length:");
    rdOptional.setBounds(new Rectangle(115, 235, 170, 25));
    txtLength.setBounds(new Rectangle(285, 235, 50, 20));

        jToolBar1.add(btnFirst, null);
    jToolBar1.add(btnPrev, null);
    jToolBar1.add(btnNext, null);
    jToolBar1.add(btnLast, null);
    bgrp.add(rdYes);
    bgrp.add(rdNo);
    bgrp.add(rdOptional);
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(con);
    rs=query.select("select count(*) from IISC_PRIMITIVE_TYPE");
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] sa=query.selectArray("select * from IISC_PRIMITIVE_TYPE order by PT_mnemonic asc",j,2);
    sa[0]="";
    query.Close();
    cmbName=new JComboBox(sa);
    txtDefault.setBounds(new Rectangle(100, 170, 345, 20));
    jLabel14.setBounds(new Rectangle(15, 170, 85, 20));
    jLabel14.setText("Default value:");
    cmbName.setVisible(true);
    cmbName.setBounds(new Rectangle(75, 5, 220, 20));
    cmbName.addItemListener(this);
    txtDecimal.setBounds(new Rectangle(105, 265, 50, 20));
    jLabel12.setText("Decimal places:");
    jLabel12.setBounds(new Rectangle(15, 265, 95, 20));
    txtName.setBounds(new Rectangle(100, 45, 345, 20));
    jLabel13.setText("Name:");
    jLabel13.setBounds(new Rectangle(15, 45, 65, 20));
    jToolBar1.add(cmbName, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(ptBaseCmb, null);
        this.getContentPane().add(jToolBar1, null);
    this.getContentPane().add(btnApply, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(txtDefault, null);
    this.getContentPane().add(jLabel14, null);
    this.getContentPane().add(jLabel13, null);
    this.getContentPane().add(txtName, null);
    this.getContentPane().add(jLabel12, null);
    this.getContentPane().add(txtDecimal, null);
    this.getContentPane().add(txtLength, null);
    this.getContentPane().add(rdOptional, null);
    this.getContentPane().add(rdNo, null);
    this.getContentPane().add(rdYes, null);
    this.getContentPane().add(jLabel11, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(txtDescription, null);
    this.getContentPane().add(btnErase, null);
    this.getContentPane().add(btnNew, null);
    this.getContentPane().add(btnSave, null);
    this.getContentPane().add(btnClose, null);
    rs=query.select("select * from IISC_Primitive_Type where PT_id="+ ID +"");
    btnApply.setEnabled(false);
    btnSave.setEnabled(false);
    cmbName.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          cmbName_mouseClicked(e);
        }
      });
    cmbName.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          cmbName_actionPerformed(e);
        }
      });
    btnLast.setIcon(iconNextt);
    btnNext.setIcon(iconNext);
    btnPrev.setIcon(iconPrev);
    btnFirst.setIcon(iconPrevv);
    btnLast.setBounds(new Rectangle(335, 5, 25, 20));
    btnLast.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          nextt_ActionPerformed(ae);
        }
      });
    btnLast.setPreferredSize(new Dimension(25, 20));
    btnLast.setMaximumSize(new Dimension(60, 60));
    
        ptBaseCmb.setBounds(new Rectangle(235, 265, 100, 20));
        jLabel1.setText("Base type");
        jLabel1.setBounds(new Rectangle(175, 265, 50, 20));
        
        ptBaseCmb.addItem("Real");
        ptBaseCmb.addItem("Integer");
        ptBaseCmb.addItem("String");
        ptBaseCmb.addItem("Date");
        ptBaseCmb.addItem("Time");
        ptBaseCmb.addItem("Boolean");
        
        
        btnNext.setBounds(new Rectangle(305, 5, 25, 20));
    btnNext.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          next_ActionPerformed(ae);
        }
      });
    btnNext.setPreferredSize(new Dimension(25, 20));
    btnNext.setMaximumSize(new Dimension(60, 60));
    btnPrev.setBounds(new Rectangle(40, 5, 25, 20));
    btnPrev.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          prev_ActionPerformed(ae);
        }
      });
    btnPrev.setPreferredSize(new Dimension(25, 20));
    btnPrev.setMaximumSize(new Dimension(60, 60));
    btnFirst.setBounds(new Rectangle(10, 5, 25, 20));
    btnFirst.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          prevv_ActionPerformed(ae);
        }
      });
    btnFirst.setPreferredSize(new Dimension(25, 20));
    btnFirst.setMaximumSize(new Dimension(60, 60));
    jToolBar1.setFloatable(false);
    jToolBar1.setPreferredSize(new Dimension(249, 60));
    jToolBar1.setLayout(null);
    jToolBar1.setFont(new Font("SansSerif", 0, 11));
    jToolBar1.setBounds(new Rectangle(40, 5, 400, 35));
    btnClose.setFont(new Font("SansSerif", 0, 11));
    btnSave.setFont(new Font("SansSerif", 0, 11));
    btnNew.setFont(new Font("SansSerif", 0, 11));
    btnErase.setFont(new Font("SansSerif", 0, 11));
    txtDescription.setFont(new Font("SansSerif", 0, 11));
    jLabel3.setFont(new Font("SansSerif", 0, 11));
    jLabel11.setFont(new Font("SansSerif", 0, 11));
    rdYes.setFont(new Font("SansSerif", 0, 11));
    rdNo.setFont(new Font("SansSerif", 0, 11));
    rdOptional.setFont(new Font("SansSerif", 0, 11));
    txtLength.setFont(new Font("SansSerif", 0, 11));
    txtDecimal.setFont(new Font("SansSerif", 0, 11));
    jLabel12.setFont(new Font("SansSerif", 0, 11));
    txtName.setFont(new Font("SansSerif", 0, 11));
    jLabel13.setFont(new Font("SansSerif", 0, 11));
    jLabel14.setFont(new Font("SansSerif", 0, 11));
    txtDefault.setFont(new Font("SansSerif", 0, 11));
    btnHelp.setFont(new Font("SansSerif", 0, 11));
    rdYes.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rdYes_actionPerformed(e);
        }
      });
    txtName.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          txtName_actionPerformed(e);
        }
      });
    rdNo.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rdNo_actionPerformed(e);
        }
      });
    rdOptional.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rdOptional_actionPerformed(e);
        }
      });
    rdYes.addMouseListener(new MouseAdapter()
      {
       public void actionPerformed(ActionEvent e)
        {
          rdYes_actionPerformed(e);
        }
      });
    txtLength.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtLength_keyTyped(e);
        }
      });
    txtDecimal.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtDecimal_keyTyped(e);
        }
      });
    txtDefault.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtDefault_keyTyped(e);
        }
      });
    txtDescription.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtDescription_keyTyped(e);
        }
      });
    txtName.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtName_keyTyped(e);
        }
      });
    btnApply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          apply_ActionPerformed(ae);
        }
      });
    btnApply.setFont(new Font("SansSerif", 0, 11));
    btnApply.setMinimumSize(new Dimension(50, 30));
    btnApply.setBounds(new Rectangle(5, 305, 75, 30));
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
    btnHelp.setBounds(new Rectangle(415, 305, 35, 30));
    if(rs.next())
    {
    cmbName.setSelectedItem(rs.getString("PT_mnemonic")); 
    ptBaseCmb.setSelectedIndex(rs.getInt("PT_base_type") - 1);
    }
    query.Close();
    setPrimitive();
    
        ptBaseCmb.addItemListener(new ItemListener()
        {

            public void itemStateChanged(ItemEvent e) 
            {
                btnSave.setEnabled(true);
                   btnApply.setEnabled(true);
            }
        });
    }

  private void close_ActionPerformed(ActionEvent e)
  { 
     if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Primitive domains", JOptionPane.YES_NO_OPTION)==0)
      update();
  dispose();
  }
  public void actionPerformed(ActionEvent e)
  {
  }
  public void Reload ()
{
setPrimitive();
}

private String getSqlIntString(String input)
{
    if (input == null || input.equals(""))
    {
        return "null";
    }
    else
    {
        return input.trim();
    }
}
 private void update()
{

       JDBCQuery query=new JDBCQuery(con);
     ResultSet rs;
     try
    { String len=new String();
      int lenreq=0;
      if(rdYes.isSelected())
      {lenreq=0;
       len=txtLength.getText();
      }
      if(rdNo.isSelected())
      {lenreq=1;
       txtDecimal.setText("");
      }
      if(rdOptional.isSelected())
      {lenreq=2;
       len=txtLength.getText();
      }
      if(cmbName.getSelectedItem().toString().equals(""))
      rs=query.select("select * from IISC_PRIMITIVE_TYPE where PT_mnemonic='"+ txtName.getText().toString() + "'");
      else
      rs=query.select("select * from IISC_PRIMITIVE_TYPE where PT_mnemonic='"+ txtName.getText().toString() + "' and PT_id<>"+ ID);
      if(rs.next())
      { JOptionPane.showMessageDialog(null, "<html><center>Name exists!", "Primitive domains", JOptionPane.ERROR_MESSAGE);
       query.Close();
      }
      else
      {query.Close();
      if(txtName.getText().trim().equals(""))
      JOptionPane.showMessageDialog(null, "<html><center>Name required!", "Primitive domains", JOptionPane.ERROR_MESSAGE);
      else if(txtName.getText().split(" ").length>1)
      JOptionPane.showMessageDialog(null, "<html><center>Name cannot contain blank character!", "Primitive defined domains", JOptionPane.ERROR_MESSAGE);
      else if(txtDescription.getText().trim().equals(""))
      JOptionPane.showMessageDialog(null, "<html><center>Description required!", "Primitive domains", JOptionPane.ERROR_MESSAGE);
      else if(txtLength.getText().trim().equals("") && rdOptional.isSelected())
      JOptionPane.showMessageDialog(null, "<html><center>Length required!", "Primitive domains", JOptionPane.ERROR_MESSAGE);
      else {
      btnSave.setEnabled(false);
      btnApply.setEnabled(false);
      if(cmbName.getSelectedItem().toString().equals(""))
      {
      rs=query.select("select max(PT_id)+1 from IISC_PRIMITIVE_TYPE"); 
      int j=0;
      if(rs.next())
      j=rs.getInt(1);
      query.Close();
      query.update("insert into IISC_Primitive_Type(PT_id,PT_mnemonic,PT_name,PT_length_required,PT_length,PT_def_val,PT_decimal,PT_base_type) values("+ j +",'"+ txtName.getText().trim() + "','"+ txtDescription.getText().trim() + "',"+ lenreq + ","+ getSqlIntString(len) +",'"+ txtDefault.getText().trim() +"','"+ txtDecimal.getText().trim() +"'," + (this.ptBaseCmb.getSelectedIndex() + 1)  + ")");
      tree.insert(txtName.getText().trim(),"Primitive domains");
      tree.select_node(txtName.getText().trim(),"Primitive domains");
      }
      else
      {query.update("update IISC_Primitive_Type set PT_mnemonic='"+ txtName.getText().trim() + "',PT_name='"+ txtDescription.getText().trim() + "',PT_length_required="+ lenreq + ",PT_length="+ getSqlIntString(len) +",PT_def_val='"+ txtDefault.getText().trim() +"', PT_decimal='"+ txtDecimal.getText().trim() + "',PT_base_type=" + (this.ptBaseCmb.getSelectedIndex() + 1) + "  where PT_id="+ ID +"");
      tree.change(Mnem ,"Primitive domains",txtName.getText().trim());
      }
      Mnem=txtName.getText().trim();
      rs=query.select("select count(*) from IISC_PRIMITIVE_TYPE");
      rs.next();
      int j=rs.getInt(1);
      String[] sa=query.selectArray("select * from IISC_PRIMITIVE_TYPE order by PT_mnemonic asc",j,2);
      sa[0]="";
      query.Close();
      String s=Mnem;
      cmbName.removeAllItems();
      for( j=0;j< sa.length ;j++)
      cmbName.addItem(sa[j]);
      Mnem=txtName.getText().trim();
      cmbName.setSelectedItem(Mnem);
      Mnem=s;
      setPrimitive();
      JOptionPane.showMessageDialog(null, "<html><center>Primitive domain saved!", "Primitive domains", JOptionPane.INFORMATION_MESSAGE);
      if(dispose)
        dispose();
      }
      }
     
    }
     catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
}
  private void apply_ActionPerformed(ActionEvent e)
  {
    dispose=false;
  update();
  }
  

  private void new_ActionPerformed(ActionEvent e)
  {
  Mnem="";
  setPrimitive();
  tree.select_node("Primitive domains","Domains");
  }

  private void erase_ActionPerformed(ActionEvent e)
  {
  if(!cmbName.getSelectedItem().toString().equals(""))
  {
     JDBCQuery query=new JDBCQuery(con);
     ResultSet rs;
     try
    { rs=query.select("select * from IISC_Domain where Dom_data_type="+ID);
      if(rs.next())
      { JOptionPane.showMessageDialog(null, "<html><center>Primitive domain can not be erased!", "Primitive domains", JOptionPane.ERROR_MESSAGE);
       query.Close();}
      else
      { 
        query.update("delete from IISC_Primitive_Type where PT_id="+ ID +"");
        tree.removenode(Mnem,"Primitive domains");
        cmbName.removeItem(Mnem);
      Mnem="";
      setPrimitive();
      JOptionPane.showMessageDialog(null, "<html><center>Primitive domain erased!", "Primitive domains", JOptionPane.INFORMATION_MESSAGE);
      }
    }
     catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  }
  }
  public void itemStateChanged(ItemEvent event )
  {

  }
   private void setPrimitive()
  {
  btnSave.setEnabled(false);
  btnApply.setEnabled(false);
  JDBCQuery query=new JDBCQuery(con);
  ResultSet rs;
   try
    {

  if(Mnem.equals(""))
  {cmbName.setSelectedItem(Mnem);
  txtDecimal.setText("");
  txtDescription.setText("");
  txtLength.setText("");
  txtName.setText("");
  txtDefault.setText("");
  rdYes.setSelected(true);
  }
  else
  {
   rs=query.select("select * from IISC_Primitive_Type where PT_mnemonic='"+ Mnem +"'");
      if(rs.next())
      { cmbName.setSelectedItem(Mnem);
        ID=rs.getInt(1);
        txtDecimal.setText(rs.getString(7));
        txtDescription.setText(rs.getString(3));
        txtLength.setText(rs.getString(5));
        txtName.setText(rs.getString(2));
        txtDefault.setText(rs.getString(6));
        int k=rs.getInt(4);
        if(k==0)rdYes.setSelected(true);
        else if(k==1)rdNo.setSelected(true); 
        else if(k==2)rdOptional.setSelected(true);
      }
       query.Close();
  } 
  }
      catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
  if(rdNo.isSelected())
   {txtDecimal.setEditable(false);
   txtLength.setEditable(false);}
   else
   { txtDecimal.setEditable(true);
   txtLength.setEditable(true);}
   btnSave.setEnabled(false);
   btnApply.setEnabled(false);
  }

  private void btnHelp_actionPerformed(ActionEvent e)
  {Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void save_ActionPerformed(ActionEvent e)
  {
  dispose=true;
  update();
  dlg.qtm.setQueryPT("select *  from IISC_Primitive_type");
  }

  private void txtName_keyTyped(KeyEvent e)
  {btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }

  private void txtDescription_keyTyped(KeyEvent e)
  {btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }

  private void txtDefault_keyTyped(KeyEvent e)
  {btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }

  private void txtDecimal_keyTyped(KeyEvent e)
  {btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }

  private void txtLength_keyTyped(KeyEvent e)
  {btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  }

  private void rdOptional_actionPerformed(ActionEvent e)
  {btnSave.setEnabled(true);
   btnApply.setEnabled(true);
  txtDecimal.setEditable(true);
   txtLength.setEditable(true); 
  
  }
 private void rdYes_actionPerformed(ActionEvent e)
  {btnSave.setEnabled(true);
   btnApply.setEnabled(true);
    txtDecimal.setEditable(true);
   txtLength.setEditable(true);
  }

  private void rdNo_actionPerformed(ActionEvent e)
  {btnSave.setEnabled(true);
   btnApply.setEnabled(true);
    txtDecimal.setEditable(false);
   txtLength.setEditable(false);
    }

  private void txtName_actionPerformed(ActionEvent e)
  {
  }

  private void prevv_ActionPerformed(ActionEvent e)
  {String s="";
 JDBCQuery query=new JDBCQuery(con);
ResultSet rs1;
try
{
  if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Primitive domains", JOptionPane.YES_NO_OPTION)==0)
      update();
 rs1=query.select("select * from  IISC_PRIMITIVE_TYPE order by  PT_mnemonic asc");
 if(rs1.next())
{s=rs1.getString("PT_mnemonic");
ID=rs1.getInt("PT_id");
}
query.Close();
Mnem=s; 
setPrimitive();
tree.select_node(Mnem,"Primitive domains");
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}


  }

  private void prev_ActionPerformed(ActionEvent e)
  {
    String s=Mnem.trim() ;

 JDBCQuery query=new JDBCQuery(con);
ResultSet rs1;
try
{ if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Primitive domains", JOptionPane.YES_NO_OPTION)==0)
      update();
rs1=query.select("select PT_mnemonic,PT_id from IISC_PRIMITIVE_TYPE  where  PT_mnemonic<'" + s + "' order by PT_mnemonic desc" );
if(rs1.next())
{

s=rs1.getString(1);
ID=rs1.getInt("PT_id");
}
query.Close();
Mnem=s;  
setPrimitive();
tree.select_node(Mnem,"Primitive domains");

}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}
  }

  private void next_ActionPerformed(ActionEvent e)
  {  
  String s=Mnem.trim() ;

 JDBCQuery query=new JDBCQuery(con);
ResultSet rs1;
try
{
  if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Primitive domains", JOptionPane.YES_NO_OPTION)==0)
      update();
 rs1=query.select("select PT_mnemonic,PT_id from IISC_PRIMITIVE_TYPE  where  PT_mnemonic>'" + s + "' order by PT_mnemonic asc" );
if(rs1.next())
{
s=rs1.getString(1);
ID=rs1.getInt("PT_id");
}
query.Close();
 Mnem=s;
setPrimitive();
  tree.select_node(Mnem,"Primitive domains");
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}
  }

  private void nextt_ActionPerformed(ActionEvent e)
  {  String s="";
 JDBCQuery query=new JDBCQuery(con);
ResultSet rs1;
try
{
  if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Primitive domains", JOptionPane.YES_NO_OPTION)==0)
      update();
 rs1=query.select("select * from IISC_PRIMITIVE_TYPE order by  PT_mnemonic desc");
if(rs1.next())
{s=rs1.getString("PT_mnemonic");
ID=rs1.getInt("PT_id");
}
query.Close();
Mnem=s;
setPrimitive();
tree.select_node(Mnem,"Primitive domains");
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}
  }

  private void jComboBox1_actionPerformed(ActionEvent e)
  {
  }

  private void cmbName_actionPerformed(ActionEvent e)
  {  try{
  JDBCQuery query=new JDBCQuery(con);
ResultSet rs1;
String s=cmbName.getSelectedItem().toString(); 
   if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Primitive domains", JOptionPane.YES_NO_OPTION)==0)
      {update();
      }
   rs1=query.select("select * from IISC_PRIMITIVE_TYPE  where  PT_mnemonic='" + s + "'" );
  if(rs1.next())
  {ID=rs1.getInt("PT_id");
  Mnem=rs1.getString("PT_mnemonic");}
  query.Close();
  setPrimitive();
  tree.select_node(Mnem,"Primitive domains");
  }
   catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  private void cmbName_mouseClicked(MouseEvent e)
  { 
  }
  
}