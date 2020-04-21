package iisc;

import java.awt.*;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JToolBar;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class AppType extends JDialog implements ActionListener
{
  private JToolBar jToolBar1 = new JToolBar();
  private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
  private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
  private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
  private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  private JButton btnFirst= new JButton();
  private JButton btnPrev = new JButton();
  private JButton btnNext = new JButton();
  private JButton  btnLast = new JButton();
  private JButton btnNew = new JButton();
  private JButton btnClose = new JButton();
  private PTree tree;
  private JLabel jLabel3 = new JLabel();
  private JTextField txtName = new JTextField();
  private JLabel jLabel10 = new JLabel();
  private Connection connection;
  private String Mnem;
  private int id;
  private JButton btnSave = new JButton();
  private JTextArea txtDescription = new JTextArea();
  private JButton btnErase = new JButton();
  private JButton btnHelp = new JButton();
  private JButton btnApply = new JButton();
  private JComboBox cmbAppType = new JComboBox();
    private boolean dispose=false;
  public AppType()
  {
    this(null, "", false);
  }

  public AppType(IISFrameMain parent, String title, boolean modal)
  {
    super((Frame) parent, title, modal);
    try
    {

      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
  public AppType(IISFrameMain parent, String title, boolean modal,Connection con,int m , PTree tr )
  {
    super((Frame) parent, title, modal);
    try
    {   
      tree=tr;
      connection=con;
    if(m>=0){
      id=m;
      JDBCQuery query=new JDBCQuery(connection);
      ResultSet rs1;
      rs1=query.select("select * from IISC_App_System_type where  AS_type_id="+id);
      rs1.next();
      Mnem=rs1.getString("AS_type");
      query.Close();
    }
    else
      Mnem="";
       Iterator it=tr.WindowsManager.iterator();
      while(it.hasNext())
      {
        Object obj=(Object)it.next();
        Class cls=obj.getClass();
        if(cls==this.getClass())
        {  ((AppType)obj).dispose();
         
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
  {

     JDBCQuery query=new JDBCQuery(connection);
     ResultSet rs;
    this.setSize(new Dimension(470, 287));
    this.getContentPane().setLayout(null);
    this.setResizable(false);
    this.setTitle("Application System Types");
    this.setFont(new Font("SansSerif", 0, 11));
    jToolBar1.setFont(new Font("Verdana", 0, 11));

    jToolBar1.setLayout(null);
    jToolBar1.setPreferredSize(new Dimension(249, 60));
    jToolBar1.setFloatable(false);
    jToolBar1.setBounds(new Rectangle(60, 5, 400, 30));
    btnFirst.setMaximumSize(new Dimension(60, 60));
    btnFirst.setPreferredSize(new Dimension(25, 20));
    btnFirst.setIcon(iconPrevv);
    btnFirst.setBounds(new Rectangle(25, 5, 25, 20));
    btnFirst.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          prevv_ActionPerformed(ae);
        }
      });
    btnPrev.setMaximumSize(new Dimension(60, 60));
    btnPrev.setIcon(iconPrev);
    btnPrev.setPreferredSize(new Dimension(25, 20));
    btnPrev.setBounds(new Rectangle(55, 5, 25, 20));
    btnPrev.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
        prev_ActionPerformed(ae);
        }
      });

    btnNext.setMaximumSize(new Dimension(60, 60));
    btnNext.setIcon(iconNext);
    btnNext.setPreferredSize(new Dimension(25, 20));
    btnNext.setBounds(new Rectangle(335, 5, 25, 20));
    btnNext.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          next_ActionPerformed(ae);
        }
      });

    btnLast.setMaximumSize(new Dimension(60, 60));
    btnLast.setIcon(iconNextt);

     btnLast.setPreferredSize(new Dimension(25, 20));
    btnLast.setBounds(new Rectangle(365, 5, 25, 20));
     btnLast.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          nextt_ActionPerformed(ae);
        }
      });

    btnNew.setMaximumSize(new Dimension(50, 30));
    btnNew.setPreferredSize(new Dimension(50, 30));
    btnNew.setText("New");
    btnNew.setMinimumSize(new Dimension(50, 30));
    btnNew.setFont(new Font("SansSerif", 0, 11));
    btnNew.setBounds(new Rectangle(85, 215, 80, 30));
    btnNew.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          new_ActionPerformed(ae);
        }
      });

    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Cancel");
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.setFont(new Font("SansSerif", 0, 11));
    btnClose.setBounds(new Rectangle(330, 215, 80, 30));

    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          close_ActionPerformed(ae);
    
        }
      });
    jLabel3.setText("Description:");
    jLabel3.setFont(new Font("SansSerif", 0, 11));
    jLabel3.setBounds(new Rectangle(10, 70, 85, 20));
    txtName.setFont(new Font("SansSerif", 0, 11));
    txtName.setBounds(new Rectangle(85, 45, 365, 20));
    txtName.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtName_keyTyped(e);
        }
      });
    jLabel10.setText("Name:");
    jLabel10.setFont(new Font("SansSerif", 0, 11));
    jLabel10.setBounds(new Rectangle(10, 45, 65, 20));
    btnSave.setMaximumSize(new Dimension(50, 30));
    btnSave.setPreferredSize(new Dimension(50, 30));
    btnSave.setText("OK");
    btnSave.setMinimumSize(new Dimension(50, 30));
    btnSave.setFont(new Font("SansSerif", 0, 11));
    btnSave.setBounds(new Rectangle(250, 215, 75, 30));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save_ActionPerformed(ae);
        
        }
      });

    txtDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    txtDescription.setFont(new Font("SansSerif", 0, 11));
    txtDescription.setBounds(new Rectangle(85, 75, 365, 130));
    txtDescription.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtDescription_keyTyped(e);
        }
      });
    btnErase.setMaximumSize(new Dimension(50, 30));
    btnErase.setPreferredSize(new Dimension(50, 30));
    btnErase.setText("Delete");
    btnErase.setMinimumSize(new Dimension(50, 30));
    btnErase.setFont(new Font("SansSerif", 0, 11));
    btnErase.setBounds(new Rectangle(170, 215, 75, 30));
    btnErase.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          erase_ActionPerformed(ae);
        }
      });
    btnHelp.setIcon(imageHelp);
    btnHelp.setFont(new Font("SansSerif", 0, 11));
    btnHelp.setBounds(new Rectangle(415, 215, 35, 30));
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
    btnApply.setMinimumSize(new Dimension(50, 30));
    btnApply.setFont(new Font("SansSerif", 0, 11));
    btnApply.setBounds(new Rectangle(5, 215, 75, 30));
    btnApply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          apply_ActionPerformed(ae);
        }
      });
    cmbAppType.setFont(new Font("SansSerif", 0, 11));
    cmbAppType.setBounds(new Rectangle(85, 5, 245, 20));
    cmbAppType.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          cmbAppType_itemStateChanged(e);
        }
      });
    cmbAppType.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jComboBox1_actionPerformed(e);
        }
      });
    jToolBar1.add(btnFirst, null);
    jToolBar1.add(btnPrev, null);
    jToolBar1.add(cmbAppType, null);
    jToolBar1.add(btnNext, null);
    jToolBar1.add(btnLast, null);
    this.getContentPane().add(btnApply, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(txtDescription, null);
    this.getContentPane().add(jLabel10, null);
    this.getContentPane().add(txtName, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jToolBar1, null);
    this.getContentPane().add(btnNew, null);
    this.getContentPane().add(btnClose, null);
    this.getContentPane().add(btnErase, null);
    this.getContentPane().add(btnSave, null);
  
    setapp(Mnem);
 }
 public void actionPerformed(ActionEvent ae)
        {
       
        }
  private void prevv_ActionPerformed(ActionEvent e)
  {
  String s="";
 JDBCQuery query=new JDBCQuery(connection);
ResultSet rs1;
try
{ if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
      
 rs1=query.select("select * from IISC_App_System_type  order by  AS_type asc");
 if(rs1.next())
{s=rs1.getString(2);
}
query.Close();
 
setapp(s);
Mnem=s;
  tree.select_node(Mnem,"Application Types");
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}


  }
private void prev_ActionPerformed(ActionEvent e)
  {
  String s=Mnem.trim() ;

 JDBCQuery query=new JDBCQuery(connection);
ResultSet rs1;
try
{
 if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
rs1=query.select("select AS_type from IISC_App_System_type  where  AS_type<'" + s + "' order by AS_type desc" );
if(rs1.next())
{

s=rs1.getString(1);
}
query.Close();
  
setapp(s);
Mnem=s;
  tree.select_node(Mnem,"Application Types");


}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}


  }


private void next_ActionPerformed(ActionEvent e)
 {
  String s=Mnem.trim() ;

 JDBCQuery query=new JDBCQuery(connection);
ResultSet rs1;
try
{
 if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
 rs1=query.select("select AS_type from IISC_App_System_type where  AS_type>'" + s + "' order by AS_type asc" );
if(rs1.next())
{
s=rs1.getString(1);
}
query.Close();
 
setapp(s);
Mnem=s;
  tree.select_node(Mnem,"Application Types");
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}

 }


  private void nextt_ActionPerformed(ActionEvent e)
 {
  String s="";
 JDBCQuery query=new JDBCQuery(connection);
ResultSet rs1;
try
{
 if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
 rs1=query.select("select * from IISC_App_System_type order by  AS_type desc");
if(rs1.next())
{s=rs1.getString(2);
}
query.Close();
 
setapp(s);
Mnem=s;
tree.select_node(Mnem,"Application Types");
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}


  }


 private void close_ActionPerformed(ActionEvent e)
  {
   if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
      this.dispose();
  }
private void apply_ActionPerformed(ActionEvent e)
  {
  update(1);
  tree.select_node(Mnem,"Application Types");
  }
    public void Reload ()
{
setapp(Mnem);
}
 private void save_ActionPerformed(ActionEvent e)
  {
  update(2);
  tree.select_node(Mnem,"Application Types");
  }
  private void new_ActionPerformed(ActionEvent e)
  {
  Mnem="";
  id=-1;
  setapp("");
  }
public void update (int k)
{
JDBCQuery query=new JDBCQuery(connection);
     ResultSet rs1;  
     boolean can=true;
try
{
if(Mnem.trim().equals(""))
{
 rs1=query.select("select * from IISC_App_System_type  where  AS_type='"+ txtName.getText().trim() +"'");
if(rs1.next())
 {JOptionPane.showMessageDialog(null, "<html><center>Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
can=false;
 }
 query.Close();
}
else
{
if(!Mnem.trim().equals(txtName.getText().trim()))
{
rs1=query.select("select * from IISC_App_System_type  where AS_type='"+ txtName.getText().trim() +"'");
if(rs1.next())
{ JOptionPane.showMessageDialog(null, "<html><center>Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
can=false;
}
query.Close();
}  
}
if(txtName.getText().trim().equals(""))
JOptionPane.showMessageDialog(null, "<html><center>Name required!", "Primitive Types", JOptionPane.INFORMATION_MESSAGE);
else if(txtDescription.getText().trim().equals(""))
JOptionPane.showMessageDialog(null, "<html><center>Description required!", "Primitive Types", JOptionPane.INFORMATION_MESSAGE);
else if(can)
{
if(Mnem.trim().equals(""))
{
rs1=query.select("select max(AS_type_id)+1  from IISC_App_System_type");
if(rs1.next())
id=rs1.getInt(1);
else
id=0;
query.Close();
int i=query.update("insert into IISC_App_System_type(AS_type_id,AS_type,AS_type_name) values ("+ id +", '" + txtName.getText().trim() + "','" + txtDescription.getText().trim() + "')");
tree.insert(txtName.getText().trim(),"Application Types");

}
else
{
 int i=query.update("update IISC_App_System_type set AS_type='" + txtName.getText().trim() + "',AS_type_name='" + txtDescription.getText().trim() + "' where AS_type_id=" + id + ""); 
tree.change(Mnem ,"Application Types",txtName.getText().trim());

}
Mnem=txtName.getText().trim();
setapp(Mnem);
if(k>=1)
JOptionPane.showMessageDialog(null, "<html><center>Application Type saved!", "Application Type", JOptionPane.INFORMATION_MESSAGE);
if(k==2)dispose();
}
}
catch(SQLException e)
{
 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}

}
public void setapp (String m)
  
 { try
    {
        btnApply.setEnabled(false);
  btnSave.setEnabled(false);
  ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select count(*) from IISC_APP_SYSTEM_TYPE");
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] sa=query.selectArray("select * from IISC_APP_SYSTEM_TYPE",j,2);
    sa[0]="";
    query.Close();
    cmbAppType.removeAllItems();
    for(int k=0;k<sa.length; k++)
    cmbAppType.addItem(sa[k]);
    cmbAppType.setSelectedItem(m);
    txtName.setText(m);
  Mnem=m;
  if(Mnem.equals(""))tree.select_node(Mnem,"Application Types");
   
     rs1=query.select("select * from IISC_App_System_type where AS_type='"+ Mnem +"'");
     if(rs1.next())
    {
        txtName.setText(rs1.getString("AS_type"));
        txtDescription.setText(rs1.getString("AS_type_name"));
        id=rs1.getInt("AS_type_id");
       
    }
  else
  {
        txtName.setText("");
        txtDescription.setText("");
        
  }
 query.Close();
rs1=query.select("select * from IISC_App_System_type  where  AS_type>'" + m.trim() + "'" );
if(rs1.next())
{btnNext.setEnabled(true);
btnLast.setEnabled(true);}
else
{btnNext.setEnabled(false);
btnLast.setEnabled(false);}
query.Close();
rs1=query.select("select * from IISC_App_System_type where  AS_type<'" + m.trim() + "'" );
if(rs1.next())
{btnPrev.setEnabled(true);
btnFirst.setEnabled(true);}
else
{btnPrev.setEnabled(false);
btnFirst.setEnabled(false);}
query.Close();
}

catch(SQLException e)
{
 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}
}

 private void erase_ActionPerformed(ActionEvent e)
  {
  if(delete())
  {
  tree.removenode(Mnem,"Application Types");
  Mnem="";
  setapp(Mnem);
  tree.select_node(Mnem,"Application Types");
  }
  }


public boolean delete()
{
JDBCQuery query=new JDBCQuery(connection);
ResultSet rs1;  
boolean can=true;
if(Mnem.equals(""))can=false;
try
{
rs1=query.select("select * from IISC_App_System  where AS_type_id="+ id);
if(rs1.next())
{ JOptionPane.showMessageDialog(null, "<html><center>Application Type can not be removed!", "Error", JOptionPane.ERROR_MESSAGE);
can=false;
} 
query.Close();
if(can)
{
 int i=query.update("delete from IISC_App_System_type where AS_type_id=" + id + ""); 
JOptionPane.showMessageDialog(null, "<html><center>Application Type removed!", "Application Types", JOptionPane.INFORMATION_MESSAGE);
}

}
catch(SQLException e)
{
 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}
return can;
}

  private void btnHelp_actionPerformed(ActionEvent e)
  { Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

 private void txtName_keyTyped(KeyEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void txtDescription_keyTyped(KeyEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void txtComment_keyTyped(KeyEvent e)
   {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void jComboBox1_actionPerformed(ActionEvent e)
  {
  if(cmbAppType.getItemCount()>0)
  { if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
  String s=cmbAppType.getSelectedItem().toString();
  setapp(s);
  Mnem=s;
  tree.select_node(Mnem,"Application Types");}
  }

  private void cmbAppType_itemStateChanged(ItemEvent e)
  {
  }


 
 }
