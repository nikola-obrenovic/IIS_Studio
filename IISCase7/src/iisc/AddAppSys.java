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
import javax.swing.JComboBox;

public class AddAppSys extends JDialog 
{
  private JButton btnSave = new JButton();
  private int id;
  private PTree tree;
  private Connection con;
  private JComboBox cmbAppSys = new JComboBox();
  public AddAppSys()
  {
    this(null, "", false,null,null);
  }

  public AddAppSys(IISFrameMain parent, String title, boolean modal, Connection conn, PTree tr)
  {
   
    super(parent, title, modal);
    con=conn;
    tree=tr;
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
  {
     try
    {
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    rs=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ tree.tree.getSelectionPath().getParentPath().getLastPathComponent().toString() +"' and PR_id="+ tree.ID);
    if(rs.next())id=rs.getInt(1);
    query.Close();
    rs=query.select("select count(*) from IISC_APP_SYSTEM  where AS_id<>"+ id +" and   PR_id="+ tree.ID +"");
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] said=query.selectArray("select * from IISC_APP_SYSTEM where AS_id<>"+ id +" and   PR_id="+tree.ID,j,1);
    query.Close();
    String[] sa=query.selectArray("select * from IISC_APP_SYSTEM where AS_id<>"+ id +" and   PR_id="+tree.ID,j,3);
    query.Close();
    cmbAppSys.addItem("");
    for (int i=1;i<sa.length;i++)
    {
    rs=query.select("select * from IISC_APP_SYSTEM_CONTAIN where AS_id="+ id +" and  AS_id_con="+ said[i] +" and PR_id="+tree.ID);
    rs1=query1.select("select * from IISC_APP_SYSTEM_CONTAIN where AS_id="+said[i]+" and  AS_id_con="+  id  +" and PR_id="+tree.ID);
    if(!rs.next() && !rs1.next() && !isCircle(id,(new Integer(said[i])).intValue()) && !isCircle((new Integer(said[i])).intValue(), id))
    cmbAppSys.addItem(sa[i]);
    query.Close();
    query1.Close();
    }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    this.setSize(new Dimension(337, 71));
    this.getContentPane().setLayout(null);
    this.setTitle("Child Application System");
    this.setFont(new Font("SansSerif", 0, 11));
    this.setModal(true);
    btnSave.setText("Reference");
    btnSave.setBounds(new Rectangle(240, 10, 85, 25));
    btnSave.setSelected(true);
    btnSave.setFont(new Font("SansSerif", 0, 11));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnSave_actionPerformed(e);
        }
      });
    cmbAppSys.setBounds(new Rectangle(5, 10, 230, 20));
    cmbAppSys.setFont(new Font("SansSerif", 0, 11));
    this.getContentPane().add(cmbAppSys, null);
    this.getContentPane().add(btnSave, null);
     this.setResizable(false);
  }

  private void btnSave_actionPerformed(ActionEvent e)
  { try
    {
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(con);
    if(!cmbAppSys.getSelectedItem().toString().equals(""))
    {int ids=-1;
    rs=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ cmbAppSys.getSelectedItem().toString() +"' and PR_id="+ tree.ID);
    if(rs.next())ids=rs.getInt(1);
       query.Close();
    query.update("insert into IISC_APP_SYSTEM_CONTAIN(AS_id,AS_id_con,PR_id) values("+ id +"," +ids +","+ tree.ID +")");
    tree.insert(cmbAppSys.getSelectedItem().toString(),"Child Application Systems",tree.tree.getSelectionPath().getParentPath().getLastPathComponent().toString());
 
    }
    this.dispose();
    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }
  }

    private boolean isCircle(int a, int b)
    { try
      {
      ResultSet rs,rs1;
      JDBCQuery query=new JDBCQuery(con);
      int ids;
      rs=query.select("select AS_id_con from IISC_APP_SYSTEM_CONTAIN where AS_id="+ a +" and PR_id="+ tree.ID);
      boolean is=false;
      while(rs.next())
      {
          ids=rs.getInt(1);
          if(ids==b)return true;
          else is=is || isCircle(ids, b);
      }
      query.Close();
      return is;
      }
      catch(SQLException ex)
      {
        ex.printStackTrace();
      }
      return false;
    }
}