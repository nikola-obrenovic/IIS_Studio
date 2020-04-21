package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RemoveProject extends JDialog  implements ActionListener 
{
  private JLabel jLabel4 = new JLabel();


  private JButton btnCancel = new JButton();
  private JButton btnOK = new JButton();
  private int ID;
  private Connection con;
  public RemoveProject()
  {
    this(null, "", false,-1,null);
  }

  public RemoveProject(Frame parent, String title, boolean modal,int i, Connection conn)
  {
    super(parent, title, modal);
    try
    { ID=i;
      con=conn;
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {  this.setResizable(false);
    this.setSize(new Dimension(227, 116));
    this.getContentPane().setLayout(null);
    this.setTitle("Remove Project");
    jLabel4.setText("Do you really want to remove this project? ");
    jLabel4.setBounds(new Rectangle(10, 10, 335, 35));
    jLabel4.setFont(new Font("SansSerif", 0, 11));
    btnCancel.setText("No");
    btnCancel.setBounds(new Rectangle(115, 50, 80, 25));
    btnCancel.setFont(new Font("SansSerif", 0, 11));
    btnCancel.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCancel_actionPerformed(e);
        }
      });
    btnOK.setText("Yes");
    btnOK.setBounds(new Rectangle(20, 50, 70, 25));
    btnOK.setFont(new Font("SansSerif", 0, 11));
    btnOK.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnOK_actionPerformed(e);
        }
      });
    this.getContentPane().add(btnOK, null);
    this.getContentPane().add(btnCancel, null);
    this.getContentPane().add(jLabel4, null);
  }

  private void btnCancel_actionPerformed(ActionEvent e)
  {this.dispose();
  }

  private void btnOK_actionPerformed(ActionEvent e)
  {
  remove(ID);
  this.dispose();

  
  }
  public void remove(int pr)
  {

  JDBCQuery query=new JDBCQuery(con);
ResultSet rs1;  

JDBCQuery query2=new JDBCQuery(con);
ResultSet rs2; 

query.update("delete from IISC_RSC_ACTION where PR_id=" + pr + "");
query.update("delete from IISC_GRAPH_CLOSURE where PR_id=" + pr + "");
query.update("delete from IISC_RSC_LHS_RHS where PR_id=" + pr + "");
query.update("delete from IISC_RSC_RS_SET where PR_id=" + pr + "");
query.update("delete from IISC_RS_CONSTRAINT where PR_id=" + pr + "");
query.update("delete from IISC_RSK_ATT where PR_id=" + pr + "");
query.update("delete from IISC_RS_KEY where PR_id=" + pr + "");
query.update("delete from IISC_RS_UNIQUE where PR_id=" + pr + "");
query.update("delete from IISC_RS_ATT where PR_id=" + pr + "");
query.update("delete from IISC_RS_ROLE where PR_id=" + pr + ""); 
query.update("delete from IISC_RS_SR where PR_id=" + pr + ""); 
query.update("delete from IISC_RELATION_SCHEME where PR_id=" + pr + "");
query.update("delete from IISC_FNF_LHS_RHS where PR_id=" + pr + ""); 
query.update("delete from IISC_F_NF_DEP where PR_id=" + pr + ""); 
query.update("delete from IISC_ATT_KTO where PR_id=" + pr + "");
query.update("delete from IISC_KEY_TOB where PR_id=" + pr + "");
query.update("delete from IISC_ATT_UTO where PR_id=" + pr + "");
query.update("delete from IISC_UNIQUE_TOB where PR_id=" + pr + "");
query.update("delete from IISC_ATT_TOB where PR_id=" + pr + "");
query.update("delete from IISC_COMPONENT_TYPE_OBJECT_TYPE where PR_id=" + pr + "");
query.update("delete from IISC_TF_APPSYS where PR_id=" + pr + "");
query.update("delete from IISC_FORM_TYPE where PR_id=" + pr + "");
query.update("delete from IISC_APP_SYSTEM_CONTAIN where PR_id=" + pr + "");
query.update("delete from IISC_APP_SYS_REFERENCE where PR_id=" + pr + "");
query.update("delete from IISC_APP_SYSTEM where PR_id=" + pr + "");
query.update("delete from IISC_DER_ATT_FUN where PR_id=" + pr + "");
query.update("delete from IISC_DERIVED_ATTRIBUTE where PR_id=" + pr + "");
query.update("delete from IISC_DOM_ATT where PR_id=" + pr + "");
query.update("delete from IISC_Attribute where PR_id=" + pr + "");
query.update("delete from IISC_DOMAIN where PR_id=" + pr + "");
query.update("delete from IISC_FUNCTION where PR_id=" + pr + "");
query.update("delete from IISC_INC_DEP_LHS_RHS where PR_id=" + pr + ""); 
query.update("delete from IISC_INCLUSION_DEPENDENCY where PR_id=" + pr + "");
query.update("delete from IISC_COLLISION_LOG where PR_id=" + pr + "");
query.update("delete from IISC_BUSINESS_APPLICATION where PR_id=" + pr + ""); 
query.update("delete from IISC_CALLING_STRUCT where PR_id=" + pr + ""); 
query.update("delete from IISC_CALL_GRAPH_NODE where PR_id=" + pr + ""); 
query.update("delete from IISC_CALL_GRAPH_VERTEX where PR_id=" + pr + ""); 
query.update("delete from IISC_COMPTYPE_ATTRIB_DISPLAY where PR_id=" + pr + "");
query.update("delete from IISC_COMPTYPE_DISPLAY where PR_id=" + pr + "");
query.update("delete from IISC_COMPTYPE_DISPLAY where PR_id=" + pr + "");
query.update("delete from IISC_COMP_ATT_DISPLAY_VALUES where PR_id=" + pr + ""); 
query.update("delete from IISC_DOMAIN_DISPLAY where PR_id=" + pr + "");
query.update("delete from IISC_DOM_DISPLAY_VALUES where PR_id=" + pr + "");

rs2 = query2.select("select LV_id from IISC_LIST_OF_VALUES where PR_id=" + pr + "");

try
{
    while(rs2.next())
    {
       query.update("delete from IISC_LV_RETURN where LV_id=" + rs2.getInt(1) + "");
    }
}
catch(SQLException e)
{

}
query.update("delete from IISC_LIST_OF_VALUES where PR_id=" + pr + "");

query.update("delete from IISC_PASSED_VALUE where PR_id=" + pr + "");

query.update("delete from IISC_PARAMETER where PR_id=" + pr + "");

query.update("delete from IISC_PROJECT where PR_id=" + pr + "");

((IISFrameMain)getParent()).desktop.getSelectedFrame().dispose();
JOptionPane.showMessageDialog(null, "<html><center>Project removed!", getTitle(), JOptionPane.INFORMATION_MESSAGE);
dispose();
  }

  private void btnCancel1_actionPerformed(ActionEvent e)
  {dispose();
  }
   public void actionPerformed(ActionEvent e)
  { 
  }
}