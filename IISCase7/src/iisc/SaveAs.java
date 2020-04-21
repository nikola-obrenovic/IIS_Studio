package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

public class SaveAs extends JDialog 
{
  private JTextField txtName = new JTextField();
  private JButton btnSave = new JButton();
  private String title1;
  private int id;
  private Connection con;
  public SaveAs()
  {
    this(null, "", false,null);
  }

  public SaveAs(Frame parent, String title, boolean modal, Connection conn)
  {
   
    super(parent, title, modal);
    title1=title;
    con=conn;
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
  {  this.setResizable(false);
     try
    {
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(con);
    rs=query.select("select * from IISC_PROJECT where PR_name='"+ title1 +"'");
    rs.next();
    id=rs.getInt(1);
    query.Close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    this.setSize(new Dimension(333, 73));
    this.getContentPane().setLayout(null);
    this.setTitle("Rename " + title1 + " ");
    this.setFont(new Font("SansSerif", 0, 11));
    this.setModal(true);
    txtName.setBounds(new Rectangle(5, 10, 225, 25));
    txtName.setFont(new Font("SansSerif", 0, 11));
    txtName.setText(title1);
    txtName.selectAll();
    btnSave.setText("Rename");
    btnSave.setBounds(new Rectangle(240, 10, 80, 25));
    btnSave.setSelected(true);
    btnSave.setFont(new Font("SansSerif", 0, 11));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnSave_actionPerformed();
        }
      });
    this.getContentPane().add(btnSave, null);
    this.getContentPane().add(txtName, null);
  }

  public void btnSave_actionPerformed()
  { try
    {
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(con);
    boolean can=true;
    JTree tree=((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).tree;
    String name=((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).name;
    int ID=((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID;
    tree.setSelectionInterval(0,0);
    if(txtName.getText().trim().equals(""))
     {JOptionPane.showMessageDialog(null, "Bad name!", "Error", JOptionPane.ERROR_MESSAGE);
    can=false;
     }
    else
    {
     rs1=query.select("Select * from IISC_PROJECT where PR_name='"+  txtName.getText().trim() +"' and PR_id<>" + ID);
    if(!rs1.next())
    {
    query.Close();
    query.update("update IISC_PROJECT set PR_name='"+  txtName.getText().trim() +"' where PR_id=" + ID);
    }
    else
    {
    JOptionPane.showMessageDialog(null, "Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
    query.Close();  
    can=false;
    }
    }
    if(can)
    {
    tree.setVisible(false);
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
    node.setUserObject(txtName.getText().trim());
    tree.setVisible(true);
    ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).setTitle(  txtName.getText().trim() + " (" + con.getCatalog() + ")");
    dispose();
    }
    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }
  }
}