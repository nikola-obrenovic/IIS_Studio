package ui;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;

public class JDBCDialog extends JDialog  implements ItemListener
{
  private JLabel jLabel3 = new JLabel();
  private String drivers = "sun.jdbc.odbc.JdbcOdbcDriver";
  private String url =new String();
  private String username =new String();
  private String password =new String();
  private IISFrameMain owner;
  private Connection connection = null;
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel1 = new JLabel();
  private JPasswordField txtPassword = new JPasswordField();
  private JTextField txtUsername = new JTextField();
  private JButton btnOK = new JButton();
  private JButton btnCancel = new JButton();
  private ODBCList o=new ODBCList();
  private JComboBox cmbDSN;
  public JDBCDialog(IISFrameMain parent, String title, boolean modal)
  { super((Frame) parent, title, modal);
    try
    {owner=parent;
     jbInit() ;
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception
  {  this.setResizable(false);
    cmbDSN = new JComboBox(o.odbc.toArray());     
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.getContentPane().setBackground(new Color(212,208,200));
    this.getContentPane().setLayout(null);
    this.setBounds(0,0,286, 172);
    this.setFont(new Font("SansSerif", 0, 11));
    this.setResizable(false);
    this.setSize(new Dimension(286, 164));
    this.setModal(true);
    jLabel3.setText("Password:");
    jLabel3.setBounds(new Rectangle(10, 35, 60, 20));
    jLabel3.setFont(new Font("SansSerif", 0, 11));
    jLabel2.setText("Username:");
    jLabel2.setBounds(new Rectangle(10, 10, 70, 20));
    jLabel2.setFont(new Font("SansSerif", 0, 11));
    jLabel1.setText("DSN:");
    jLabel1.setBounds(new Rectangle(10, 60, 60, 20));
    jLabel1.setFont(new Font("SansSerif", 0, 11));
    txtPassword.setBounds(new Rectangle(90, 35, 180, 20));
    txtPassword.setFont(new Font("SansSerif", 0, 11));
    txtPassword.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          txtPassword_actionPerformed(e);
        }
      });
    cmbDSN.setBounds(new Rectangle(90, 60, 180, 20));
    cmbDSN.setFont(new Font("SansSerif", 0, 11));
    cmbDSN.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          cmbDSN_actionPerformed(e);
        }
      });
     cmbDSN.addItemListener(this);
    if(cmbDSN.getSelectedItem()==null)
    cmbDSN.setEditable(true);
    txtUsername.setBounds(new Rectangle(90, 10, 180, 20));
    txtUsername.setFont(new Font("SansSerif", 0, 11));
    txtUsername.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          txtUsername_actionPerformed(e);
        }
      });
    btnOK.setText("OK");
    btnOK.setBounds(new Rectangle(65, 95, 70, 25));
    btnOK.setBackground(new Color(212,208,200));
    btnOK.setFont(new Font("SansSerif", 0, 11));
    btnOK.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnOK_actionPerformed(e);
        }
      });
    btnCancel.setText("Cancel");
    btnCancel.setBounds(new Rectangle(175, 95, 80, 25));
    btnCancel.setFont(new Font("SansSerif", 0, 11));
    btnCancel.setBackground(new Color(212,208,200));
    btnCancel.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCancel_actionPerformed(e);
        }
      });
    this.getContentPane().add(btnCancel, null);
    this.getContentPane().add(btnOK, null);
    this.getContentPane().add(txtUsername, null);
    this.getContentPane().add(cmbDSN, null);
    this.getContentPane().add(txtPassword, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel3, null);
  Settings.Center(this);
  this.setVisible(true);
  }
  
  private void btnCancel_actionPerformed(ActionEvent e)
  {
 this.dispose();
  }
  void activateConnectionDialog() 
  {
    
      url = "jdbc:odbc:"+cmbDSN.getSelectedItem().toString();
      System.out.println(url);
      username = txtUsername.getText();
      password = new String(txtPassword.getPassword());

  try 
    {
      Class.forName(drivers);
    }
    catch (ClassNotFoundException ef) 
    {
      JOptionPane.showMessageDialog(null, "ClassNotFoundException:    " + ef.getMessage() , "Connection error", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }
  
   
   try
      {url ="jdbc:odbc:"+ cmbDSN.getSelectedItem().toString();
       username = txtUsername.getText();
       password = new String(txtPassword.getPassword());
      // System.out.println(url + " : " + username + " > " + password);
       connection = (Connection)DriverManager.getConnection(url, username, password);
      }
      catch(SQLException ex)
      {
      if(this.isVisible())
      {
      JOptionPane.showMessageDialog(null, "<html><center>This is not valid repository!", "Connection Error", JOptionPane.ERROR_MESSAGE);
      }
      }
      
  }

public void itemStateChanged(ItemEvent event )

{
 
  }
 
  private void cmbDSN_actionPerformed(ActionEvent e)
  {
  
  }

  private void txtPassword_actionPerformed(ActionEvent e)
  {   }

  private void txtUsername_actionPerformed(ActionEvent e)
  { 
  }

  private void btnOK_actionPerformed(ActionEvent e)
  {
  activateConnectionDialog();
  if(connection!=null)
  { boolean can=true;
    try {    
      JInternalFrame[] frames;
      frames=owner.desktop.getAllFrames();
      for(int j=0;j<frames.length;j++)
      {   if(((PTree)frames[j]).con!=null && ((PTree)frames[j]).con.getCatalog().toString().equals(connection.getCatalog().toString()))
          {JOptionPane.showMessageDialog(null, "<html><center>Connection already opened!","IIS*UIModeler", JOptionPane.INFORMATION_MESSAGE);     
          can=false;
          }
      }
    }
      catch (Exception ex) {
          ex.printStackTrace();
      } 
  if(can){  
   PTree tree=new PTree(connection,(IISFrameMain)getParent());
   tree.setVisible(true);
   owner.desktop.add(tree, BorderLayout.CENTER);
    try {
        tree.setSelected(true);
    } 
    catch (java.beans.PropertyVetoException ed)
    {
    ed.printStackTrace();
    } 
  }
  dispose();
  }
  else
  JOptionPane.showMessageDialog(null, "<html><center>You have to choose Repository!", "Error", JOptionPane.ERROR_MESSAGE);
  }

  private void cmbProject_actionPerformed(ActionEvent e)
  {
  }
  
}