package iisc;
import java.awt.*;
import java.awt.Dimension;
import javax.swing.JDialog;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.event.*;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class Filter extends JDialog
{ private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable table = new JTable();
  private JButton btnSave = new JButton();
  public JButton btnClose = new JButton();
  private JButton btnHelp = new JButton();
  public QueryTableModel qtm;
  private Connection connection;
  private SearchTable owner;
   private Collision owner1;

  public Filter()
  {
    this(null, "", false);
  }

  public Filter(IISFrameMain parent, String title, boolean modal)
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
  public Filter(IISFrameMain parent, String title, boolean modal, Connection con, SearchTable own)
  { 
    super((Frame)parent, title, modal);
    try
    {connection=con;
    owner=own;
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
 public Filter(IISFrameMain parent, String title, boolean modal, Connection con, Collision own)
  { 
    super((Frame)parent, title, modal);
    try
    {connection=con;
    owner1=own;
      jbInit1();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
    private void jbInit1() throws Exception
  {  this.setResizable(false);
    this.setSize(new Dimension(398, 306));
    this.getContentPane().setLayout(null);
    this.setFont(new Font("SansSerif", 0, 11));
    this.setTitle("Filter");
    jScrollPane1.setBounds(new Rectangle(5, 10, 380, 210));
    jScrollPane1.setFont(new Font("SansSerif", 0, 11));
    btnSave.setMaximumSize(new Dimension(50, 30));
    btnSave.setPreferredSize(new Dimension(50, 30));
    btnSave.setText("Filter");
    btnSave.setBounds(new Rectangle(185, 230, 75, 30));
    btnSave.setMinimumSize(new Dimension(50, 30));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save1_ActionPerformed(ae);
        }
      });
    btnSave.setFont(new Font("SansSerif", 0, 11));
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Cancel");
    btnClose.setBounds(new Rectangle(265, 230, 80, 30));
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          close1_ActionPerformed(ae);
        }
      });
    btnClose.setFont(new Font("SansSerif", 0, 11));
    btnHelp.setBounds(new Rectangle(350, 230, 35, 30));
    btnHelp.setIcon(imageHelp);
    btnHelp.setFont(new Font("SansSerif", 0, 11));
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnClose, null);
    this.getContentPane().add(btnSave, null);
    int k=owner1.table.getColumnCount();
    String[] col=new String[k];
    for (int i=0; i< k;i++)
    {
     col[i]=owner1.table.getColumnName(i);
    }
    qtm=new QueryTableModel(connection,-1);
    table=new JTable(qtm);
    table.addMouseListener(new MouseAdapter()
      {
        public void mouseReleased(MouseEvent e)
        {
          jScrollPane1_mouseReleased(e);
        }
      });
    qtm.setQueryFilter(col);
   
jScrollPane1.getViewport().add(table, null);
    this.getContentPane().add(jScrollPane1, null);

      }
  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(398, 306));
    this.getContentPane().setLayout(null);
    this.setFont(new Font("SansSerif", 0, 11));
    this.setTitle("Filter");
    jScrollPane1.setBounds(new Rectangle(5, 10, 380, 210));
    jScrollPane1.setFont(new Font("SansSerif", 0, 11));
    btnSave.setMaximumSize(new Dimension(50, 30));
    btnSave.setPreferredSize(new Dimension(50, 30));
    btnSave.setText("Filter");
    btnSave.setBounds(new Rectangle(185, 230, 75, 30));
    btnSave.setMinimumSize(new Dimension(50, 30));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save_ActionPerformed(ae);
        }
      });
    btnSave.setFont(new Font("SansSerif", 0, 11));
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Cancel");
    btnClose.setBounds(new Rectangle(265, 230, 80, 30));
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          close1_ActionPerformed(ae);
        }
      });
    btnClose.setFont(new Font("SansSerif", 0, 11));
    btnHelp.setBounds(new Rectangle(350, 230, 35, 30));
    btnHelp.setIcon(imageHelp);
    btnHelp.setFont(new Font("SansSerif", 0, 11));
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnClose, null);
    this.getContentPane().add(btnSave, null);
    int k=owner.table.getColumnCount();
    String[] col=new String[k];
    for (int i=0; i< k;i++)
    {
     col[i]=owner.table.getColumnName(i);
    }
    qtm=new QueryTableModel(connection,-1);
    table=new JTable(qtm);
    table.addMouseListener(new MouseAdapter()
      {
        public void mouseReleased(MouseEvent e)
        {
          jScrollPane1_mouseReleased(e);
        }
      });
    qtm.setQueryFilter(col);
     if(owner.type.equals("Component Type Attribute") || owner.type.equals("Form Type"))
  table.setRowHeight(0, 1);
jScrollPane1.getViewport().add(table, null);
    this.getContentPane().add(jScrollPane1, null);

      }
     
  public void valueChanged(ListSelectionEvent e){}
  private void save_ActionPerformed(ActionEvent e)
  {  
  
  for (int j=owner.table.getRowCount()-1; j>-1 ;j--)
 {

  for (int i=0; i< table.getRowCount();i++)
  {try {
  if( owner.table.getValueAt(j,i) == null)
    continue;

  String s1=owner.table.getValueAt(j,i).toString();
  String s2=table.getValueAt(i,1).toString();
 String[] niz=ODBCList.splitString(s1.toLowerCase(),s2.toLowerCase());
if(!s2.equals("") && niz.length < 2)
  {owner.qtm.removeRow(j);
  break;
  }
  }
     catch(Exception ae)
    {
      ae.printStackTrace();
    }
 }
  }
 
  dispose();
  }

  private void save1_ActionPerformed(ActionEvent e)
  {  
  
  for (int j=owner1.table.getRowCount()-1; j>-1 ;j--)
 {
  for (int i=0; i< table.getRowCount();i++)
  {try {
  String s1=owner1.table.getValueAt(j,i).toString();
  String s2=table.getValueAt(i,1).toString();
 String[] niz=ODBCList.splitString(s1,s2);
if(!s2.equals("") && niz.length < 2)
  {owner1.qtm.removeRow(j);
  break;
  }
  }
     catch(Exception ae)
    {
      ae.printStackTrace();
    }
 }
  }
 
  dispose();
  } 

  private void close1_ActionPerformed(ActionEvent e)
  {
  dispose();
  }

  private void btnHelp_actionPerformed(ActionEvent e)
  {Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void jScrollPane1_mouseReleased(MouseEvent e)
  {

  }
}