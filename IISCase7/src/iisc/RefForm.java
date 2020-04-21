package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class RefForm extends JDialog 
{
  private JButton btnSave = new JButton();
  private int id;
  private PTree tree;
  private Connection con;
  private JComboBox cmbRefForm = new JComboBox();
  private JButton btnCancel = new JButton();
  private JButton btnFunction5 = new JButton();
  private String[] sa;
  private String[] sa1;
  private String[][] sa2;
  public RefForm()
  {
    this(null, "", false,null,null);
  }

  public RefForm(IISFrameMain parent, String title, boolean modal, Connection conn, PTree tr)
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
  { this.setResizable(false);
     try
    {
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    rs=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ tree.tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString() +"' and PR_id="+ tree.ID);
    if(rs.next())id=rs.getInt(1);
    query.Close();
    rs=query.select("select count(*) from IISC_FORM_TYPE,IISC_TF_APPSYS where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.AS_id<>"+ id +" and   IISC_FORM_TYPE.PR_id="+tree.ID);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] said=query.selectArray("select * from IISC_FORM_TYPE,IISC_TF_APPSYS where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.AS_id<>"+ id +" and   IISC_FORM_TYPE.PR_id="+tree.ID,j,1);
    query.Close();
    sa=query.selectArray("select * from IISC_FORM_TYPE,IISC_TF_APPSYS where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.AS_id<>"+ id +" and   IISC_FORM_TYPE.PR_id="+tree.ID,j,3);
    query.Close();
    sa1=query.selectArray("select AS_name from IISC_FORM_TYPE,IISC_TF_APPSYS,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.AS_id<>"+ id +" and   IISC_FORM_TYPE.PR_id="+tree.ID,j,1);
    query.Close();
    cmbRefForm.addItem("");
    int k=1;
    rs=query.select("select count(*) from IISC_APP_SYS_REFERENCE where AS_id="+ id +" and PR_id="+tree.ID);
    if(rs.next() )
        j=j-rs.getInt(1);
    query.Close();
    sa2=new String[j+1][2];
    for (int i=1;i<sa.length;i++)
    {
    rs=query.select("select * from IISC_APP_SYS_REFERENCE where AS_id="+ id +" and  TF_id="+ said[i] +" and PR_id="+tree.ID);
    if(!rs.next() ){
    cmbRefForm.addItem(sa[i]+ " (" + sa1[i] + ")");
    sa2[k][0]=sa[i];
    sa2[k][1]=sa1[i];
    k++;
    }
    query.Close();
    
    }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    this.setSize(new Dimension(292, 102));
    this.getContentPane().setLayout(null);
    this.setTitle("Reference Form Type");
    this.setFont(new Font("Dialog", 0, 11));
    this.setModal(true);
    btnSave.setText("Reference");
    btnSave.setBounds(new Rectangle(115, 40, 85, 25));
    btnSave.setSelected(true);
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnSave_actionPerformed(e);
        }
      });
    cmbRefForm.setBounds(new Rectangle(5, 10, 230, 20));
    btnCancel.setText("Cancel");
    btnCancel.setBounds(new Rectangle(205, 40, 75, 25));
    btnCancel.setSelected(true);
    btnCancel.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCancel_actionPerformed(e);
        }
      });
    btnFunction5.setMaximumSize(new Dimension(50, 30));
    btnFunction5.setPreferredSize(new Dimension(50, 30));
    btnFunction5.setText("...");
    btnFunction5.setBounds(new Rectangle(245, 10, 30, 20));
    btnFunction5.setMinimumSize(new Dimension(50, 30));
    btnFunction5.setFont(new Font("SansSerif", 0, 11));
    btnFunction5.addActionListener(new ActionListener()
      {

        public void actionPerformed(ActionEvent e)
        {
          btnFunction5_actionPerformed(e);
        }
      });
    this.getContentPane().add(btnFunction5, null);
    this.getContentPane().add(btnCancel, null);
    this.getContentPane().add(cmbRefForm, null);
    this.getContentPane().add(btnSave, null);
  }

  private void btnSave_actionPerformed(ActionEvent e)
  { try
    {
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(con);
    if(!cmbRefForm.getSelectedItem().toString().equals(""))
    {int ids=-1;
    rs=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ sa2[cmbRefForm.getSelectedIndex()][1] +"' and PR_id="+ tree.ID);
    if(rs.next())ids=rs.getInt(1);
       query.Close();
    rs=query.select("select * from IISC_FORM_TYPE, IISC_TF_APPSYS where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and TF_mnem='"+ sa2[cmbRefForm.getSelectedIndex()][0] +"' and IISC_FORM_TYPE.PR_id="+ tree.ID + " and AS_id="+ids);
    if(rs.next())ids=rs.getInt(1);
       query.Close();
    query.update("insert into IISC_APP_SYS_REFERENCE(AS_id,Tf_id,PR_id) values("+ id +"," +ids +","+ tree.ID +")");
    tree.insert(sa[cmbRefForm.getSelectedIndex()],"Referenced","Form Types",tree.tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString());
 
    }
    this.dispose();
    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }
  }

  private void btnCancel_actionPerformed(ActionEvent e)
  {
  dispose();
  }

  private void btnFunction5_actionPerformed(ActionEvent e)
  {
  SearchTable ptype=new SearchTable((Frame)getParent(),"Select Form Type",true,con);
      Settings.Center(ptype);
      ptype.type="Form Type";
      ptype.btnNew.setEnabled(false);
      ptype.btnPrimitive.setEnabled(false);
      ptype.item=cmbRefForm;
      ptype.owner=this;
      ptype.setVisible(true);
  }

 

}