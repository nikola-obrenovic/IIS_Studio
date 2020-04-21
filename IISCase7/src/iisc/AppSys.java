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
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.text.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class AppSys extends JDialog implements ActionListener
{
  private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
  private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
  private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
  private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
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
  private JLabel lbCreateDate = new JLabel();
  private JLabel jLabel18 = new JLabel();
  private JComboBox cmbType = new JComboBox();
  private JLabel jLabel115 = new JLabel();
  private JButton btnApply = new JButton();
  private JButton btnHelp = new JButton();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private JTextArea txtComment = new JTextArea();
  private JLabel jLabel4 = new JLabel();
  private JToolBar jToolBar1 = new JToolBar();
  private JButton btnFirst = new JButton();
  private JButton btnPrev = new JButton();
  private JComboBox cmbAppSys = new JComboBox();
  private JButton btnNext = new JButton();
  private JButton btnLast = new JButton();
  private JButton btnFunction3 = new JButton();
  private boolean dispose=false;
  public AppSys()
  {
    this(null, "", false);
  }

  public AppSys(IISFrameMain parent, String title, boolean modal)
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
  public AppSys(IISFrameMain parent, String title, boolean modal,Connection con,int m , PTree tr )
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
      rs1=query.select("select * from IISC_APP_SYSTEM where PR_id="+ tree.ID +" and AS_id="+id);
      rs1.next();
      Mnem=rs1.getString("AS_name");
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
        { 
         ((AppSys)obj).dispose();
        
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
    rs=query.select("select count(*) from IISC_APP_SYSTEM_TYPE");
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] sa=query.selectArray("select * from IISC_APP_SYSTEM_TYPE" ,j,2);
    sa[0]="";
    query.Close();
    cmbType=new JComboBox(sa);
    cmbType.setFont(new Font("SansSerif", 0, 11));
    this.setSize(new Dimension(472, 403));
    this.getContentPane().setLayout(null);

    this.setTitle("Application Systems");
    this.setFont(new Font("SansSerif", 0, 11));
    this.setResizable(false);




    btnNew.setMaximumSize(new Dimension(50, 30));
    btnNew.setPreferredSize(new Dimension(50, 30));
    btnNew.setText("New");
    btnNew.setBounds(new Rectangle(170, 330, 80, 30));
    btnNew.setMinimumSize(new Dimension(50, 30));
    btnNew.setFont(new Font("SansSerif", 0, 11));
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
    btnClose.setBounds(new Rectangle(335, 330, 80, 30));
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.setFont(new Font("SansSerif", 0, 11));

    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          close_ActionPerformed(ae);
    
        }
      });
    jLabel3.setText("Description:");
    jLabel3.setBounds(new Rectangle(5, 45, 85, 20));
    jLabel3.setFont(new Font("SansSerif", 0, 11));
    txtName.setBounds(new Rectangle(80, 15, 350, 20));
    txtName.setFont(new Font("SansSerif", 0, 11));
    txtName.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtName_keyTyped(e);
        }
      });
    jLabel10.setText("Name:");
    jLabel10.setBounds(new Rectangle(5, 15, 65, 20));
    jLabel10.setFont(new Font("SansSerif", 0, 11));
    btnSave.setMaximumSize(new Dimension(50, 30));
    btnSave.setPreferredSize(new Dimension(50, 30));
    btnSave.setText("OK");
    btnSave.setBounds(new Rectangle(255, 330, 75, 30));
    btnSave.setMinimumSize(new Dimension(50, 30));
    btnSave.setFont(new Font("SansSerif", 0, 11));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save_ActionPerformed(ae);
        
        }
      });

    txtDescription.setBounds(new Rectangle(80, 50, 350, 130));
    txtDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    txtDescription.setFont(new Font("SansSerif", 0, 11));
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
    btnErase.setBounds(new Rectangle(90, 330, 75, 30));
    btnErase.setMinimumSize(new Dimension(50, 30));
    btnErase.setFont(new Font("SansSerif", 0, 11));
    btnErase.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          erase_ActionPerformed(ae);
        }
      });
    lbCreateDate.setFont(new Font("SansSerif", 0, 11));
    lbCreateDate.setBounds(new Rectangle(80, 190, 300, 15));
    jLabel18.setText("Created:");
    jLabel18.setFont(new Font("SansSerif", 0, 11));
    jLabel18.setBounds(new Rectangle(5, 190, 60, 15));
    cmbType.setBounds(new Rectangle(80, 215, 310, 20));
   cmbType.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          cmbType_mouseClicked(e);
        }
      });
    jLabel115.setText("Type:");
    jLabel115.setFont(new Font("SansSerif", 0, 11));
    jLabel115.setBounds(new Rectangle(5, 215, 50, 15));
    btnApply.setMaximumSize(new Dimension(50, 30));
    btnApply.setPreferredSize(new Dimension(50, 30));
    btnApply.setText("Apply");
    btnApply.setBounds(new Rectangle(10, 330, 75, 30));
    btnApply.setMinimumSize(new Dimension(50, 30));
    btnApply.setFont(new Font("SansSerif", 0, 11));
    btnApply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          apply_ActionPerformed(ae);
        }
      });
    btnHelp.setBounds(new Rectangle(420, 330, 35, 30));
    btnHelp.setFont(new Font("SansSerif", 0, 11));
    btnHelp.setIcon(imageHelp);
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    jTabbedPane1.setBounds(new Rectangle(10, 45, 445, 275));
    jTabbedPane1.setFont(new Font("SansSerif", 0, 11));
    jPanel1.setFont(new Font("SansSerif", 0, 11));
    jPanel1.setLayout(null);
    jPanel2.setFont(new Font("SansSerif", 0, 11));
    jPanel2.setLayout(null);
    txtComment.setBounds(new Rectangle(5, 30, 430, 210));
    txtComment.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    txtComment.setFont(new Font("SansSerif", 0, 11));
    txtComment.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtComment_keyTyped(e);
        }
      });
    jLabel4.setText("Comment:");
    jLabel4.setBounds(new Rectangle(5, 5, 85, 20));
    jLabel4.setFont(new Font("SansSerif", 0, 11));
    jToolBar1.setFont(new Font("Verdana", 0, 11));
    jToolBar1.setLayout(null);
    jToolBar1.setPreferredSize(new Dimension(249, 60));
    jToolBar1.setFloatable(false);
    jToolBar1.setBounds(new Rectangle(35, 10, 400, 30));
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
    cmbAppSys.setFont(new Font("SansSerif", 0, 11));
    cmbAppSys.setBounds(new Rectangle(85, 5, 245, 20));
    cmbAppSys.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          cmbAppSys_mouseClicked(e);
        }
      });
    cmbAppSys.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          cmbAppType_itemStateChanged(e);
        }
      });
    cmbAppSys.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jComboBox1_actionPerformed(e);
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
    btnFunction3.setMaximumSize(new Dimension(50, 30));
    btnFunction3.setPreferredSize(new Dimension(50, 30));
    btnFunction3.setText("...");
    btnFunction3.setBounds(new Rectangle(400, 215, 30, 20));
    btnFunction3.setMinimumSize(new Dimension(50, 30));
    btnFunction3.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          type_ActionPerformed(ae);
        }
      });
    btnFunction3.setFont(new Font("SansSerif", 0, 11));
    jToolBar1.add(btnFirst, null);
    jToolBar1.add(btnPrev, null);
    jToolBar1.add(cmbAppSys, null);
    jToolBar1.add(btnNext, null);
    jToolBar1.add(btnLast, null);
    jPanel1.add(btnFunction3, null);
    jPanel1.add(txtDescription, null);
    jPanel1.add(txtName, null);
    jPanel1.add(cmbType, null);
    jPanel1.add(jLabel3, null);
    jPanel1.add(jLabel10, null);
    jPanel1.add(lbCreateDate, null);
    jPanel1.add(jLabel18, null);
    jPanel1.add(jLabel115, null);
    jPanel2.add(jLabel4, null);
    jPanel2.add(txtComment, null);
    jTabbedPane1.addTab("Application System", jPanel1);
    jTabbedPane1.addTab("Notes", jPanel2);
    this.getContentPane().add(jToolBar1, null);
    this.getContentPane().add(jTabbedPane1, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnApply, null);
    this.getContentPane().add(btnNew, null);
    this.getContentPane().add(btnClose, null);
    this.getContentPane().add(btnErase, null);
    this.getContentPane().add(btnSave, null);
  
    setAppSysem(Mnem);
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
{
 if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
 rs1=query.select("select * from IISC_APP_SYSTEM where PR_id="+ tree.ID +" order by  AS_name asc");
 if(rs1.next())
{s=rs1.getString(3);
}
 query.Close();
 
setAppSysem(s);
Mnem=s;
  tree.select_node(Mnem,"Application Systems");
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
rs1=query.select("select AS_name from IISC_APP_SYSTEM  where PR_id="+ tree.ID +" and AS_name<'" + s + "' order by AS_name desc" );
if(rs1.next())
{

s=rs1.getString(1);
}
 query.Close();
 
setAppSysem(s);
  tree.select_node(Mnem,"Application Systems");

Mnem=s;
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
{ if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
 rs1=query.select("select AS_name from IISC_APP_SYSTEM  where PR_id="+ tree.ID +" and AS_name>'" + s + "' order by AS_name asc" );
if(rs1.next())
{
s=rs1.getString(1);
}
 query.Close();
 
setAppSysem(s);
Mnem=s;
  tree.select_node(Mnem,"Application Systems");
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
{ if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
 rs1=query.select("select * from IISC_APP_SYSTEM  where PR_id="+ tree.ID +" order by  AS_name desc");
if(rs1.next())
{s=rs1.getString(3);
}
 query.Close();
 
setAppSysem(s);
Mnem=s;
tree.select_node(Mnem,"Application Systems");
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

 private void save_ActionPerformed(ActionEvent e)
  {
  update(2);
  tree.select_node(Mnem,"Application Systems");
  }
  private void apply_ActionPerformed(ActionEvent e)
  {
  update(1);
  tree.select_node(Mnem,"Application Systems");
  }
  private void new_ActionPerformed(ActionEvent e)
  {
  Mnem="";
  id=-1;
  setAppSysem("");
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
 rs1=query.select("select * from IISC_APP_SYSTEM  where PR_id="+ tree.ID +" and AS_name='"+ txtName.getText().trim() +"'");
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
rs1=query.select("select * from IISC_APP_SYSTEM  where PR_id="+ tree.ID +" and AS_name='"+ txtName.getText().trim() +"'");
if(rs1.next())
{ JOptionPane.showMessageDialog(null, "<html><center>Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
can=false;
}
 query.Close();
}  
}
if(txtName.getText().trim().equals(""))
JOptionPane.showMessageDialog(null, "<html><center>Name required!", "Application Systems", JOptionPane.ERROR_MESSAGE);
else if(txtDescription.getText().trim().equals(""))
JOptionPane.showMessageDialog(null, "<html><center>Description required!", "Application Systems", JOptionPane.ERROR_MESSAGE);
else if(cmbType.getSelectedItem().toString().trim().equals(""))
JOptionPane.showMessageDialog(null, "<html><center>Type required!", "Application Systems", JOptionPane.ERROR_MESSAGE);
else if(can)
{
int type;
rs1=query.select("select *  from IISC_APP_SYSTEM_TYPE where AS_type='"+ cmbType.getSelectedItem().toString().trim() +"'");
rs1.next();
type=rs1.getInt(1);
query.Close();
if(Mnem.trim().equals(""))
{
rs1=query.select("select max(AS_id)+1  from IISC_APP_SYSTEM");
if(rs1.next())
id=rs1.getInt(1);
else
id=0;
query.Close();
int i=query.update("insert into IISC_APP_SYSTEM(AS_id,PR_id,AS_name,AS_desc,AS_date,AS_type_id,AS_comment,AS_collision_detection) values ("+ id +","+ tree.ID +", '" + txtName.getText().trim() + "','" + txtDescription.getText().trim() + "','"+ ODBCList.now() +"',"+ type +",'" + txtComment.getText().trim() + "',0)");
 tree.insert(txtName.getText().trim(),"Application Systems");

}
else
{

 int i=query.update("update IISC_APP_SYSTEM set AS_name='" + txtName.getText().trim() + "',AS_desc='" + txtDescription.getText().trim() + "',AS_type_id="+ type +", AS_comment='" + txtComment.getText().trim() + "'  where AS_id=" + id + ""); 
tree.change(Mnem ,"Application Systems",txtName.getText().trim());
}
Mnem=txtName.getText().trim();
setAppSysem(Mnem);
if(k>=1)
JOptionPane.showMessageDialog(null, "<html><center>Application System saved!", "Application Systems", JOptionPane.INFORMATION_MESSAGE);
if(k==2)dispose();
}
}
catch(SQLException e)
{
 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}

}
public void setAppSysem (String m)
  
 {try
    {
     btnApply.setEnabled(false);
  btnSave.setEnabled(false);
  ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select count(*) from IISC_APP_SYSTEM where PR_id=" + tree.ID);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] sa=query.selectArray("select * from IISC_APP_SYSTEM where PR_id=" + tree.ID,j,3);
    sa[0]="";
    query.Close();
    cmbAppSys.removeAllItems();
    for(int k=0;k<sa.length; k++)
    cmbAppSys.addItem(sa[k]);
    cmbAppSys.setSelectedItem(m);
  txtName.setText(m);
  Mnem=m;
  if(Mnem.equals(""))tree.select_node(Mnem,"Application Systems");
    
     rs1=query.select("select * from IISC_APP_SYSTEM where PR_id="+ tree.ID +"  and AS_name='"+ Mnem +"'");
     if(rs1.next())
    {   
        txtName.setText(rs1.getString("AS_name"));
        txtDescription.setText(rs1.getString("AS_desc"));
        txtComment.setText(rs1.getString("AS_comment"));
        jLabel18.setVisible(true);
        DateFormat df = DateFormat.getDateInstance();
        DateFormat dft = DateFormat.getTimeInstance();
        Timestamp tm=rs1.getTimestamp("AS_date");
        lbCreateDate.setText(df.format(tm) + " " + dft.format(tm));
        id=rs1.getInt("AS_id");
        String type=rs1.getString("AS_type_id");
         query.Close();
        rs=query.select("select *  from IISC_APP_SYSTEM_TYPE where AS_type_id="+ type +"");
        rs.next();
        type=rs.getString(2);
        cmbType.setSelectedItem(type);
    }
  else
  {     jLabel18.setVisible(false);
        txtName.setText("");
        txtDescription.setText("");
        txtComment.setText("");
        lbCreateDate.setText("");
        cmbType.setSelectedItem("");
  }
  
 
  
   query.Close();
rs1=query.select("select * from IISC_APP_SYSTEM  where PR_id="+ tree.ID +" and AS_name>'" + m.trim() + "'" );
if(rs1.next())
{btnNext.setEnabled(true);
btnLast.setEnabled(true);}
else
{btnNext.setEnabled(false);
btnLast.setEnabled(false);}
 query.Close();
rs1=query.select("select * from IISC_APP_SYSTEM where PR_id="+ tree.ID +" and  AS_name<'" + m.trim() + "'" );
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
public void Reload ()
{
setAppSysem(Mnem);
}
 private void erase_ActionPerformed(ActionEvent e)
  {
  if(delete())
  {tree.removenode(Mnem,"Application Systems");
  Mnem="";
  setAppSysem(Mnem);
  tree.select_node(Mnem,"Application Systems");
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
rs1=query.select("select * from IISC_TF_APPSYS  where AS_id="+ id +"");
if(rs1.next())
{ JOptionPane.showMessageDialog(null, "<html><center>Application System can not be removed!", "Error", JOptionPane.ERROR_MESSAGE);
can=false;
} 
 query.Close();
if(can)
{
 int i=query.update("delete from IISC_APP_SYSTEM where AS_id=" + id + ""); 
JOptionPane.showMessageDialog(null, "<html><center>Application System removed!", "Application Systems", JOptionPane.INFORMATION_MESSAGE);
}

}
catch(SQLException e)
{
 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}
return can;
}

  
  private void btnHelp_actionPerformed(ActionEvent e)
  {Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void txtName_propertyChange(PropertyChangeEvent e)
  {
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
  try 
  { if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
  String s=cmbAppSys.getSelectedItem().toString();
  setAppSysem(s);
  tree.select_node(Mnem,"Application Systems");
  Mnem=s;
  }
   catch(Exception ex)
    {
      ex.printStackTrace();
    }

  }

  private void cmbAppType_itemStateChanged(ItemEvent e)
  {
  }

  private void type_ActionPerformed(ActionEvent e)
  {btnApply.setEnabled(true);
      btnSave.setEnabled(true);  
      SearchTable ptype=new SearchTable((Frame)getParent(),"Select Application Type",true,connection);
      Settings.Center(ptype);
      ptype.type="Application Type";
      ptype.item=cmbType;
      ptype.owner=this;
      ptype.setVisible(true);
  }

  private void cmbAppSys_mouseClicked(MouseEvent e)
  {
  }
    private void cmbType_mouseClicked(MouseEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }
 

  



 
 }
