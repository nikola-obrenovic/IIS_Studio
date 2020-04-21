package iisc;
import java.awt.Dimension;
import javax.swing.JDialog;
import java.awt.*;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.MouseEvent;
public class Collision extends JDialog implements ActionListener, ListSelectionListener
{
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTextField txtSearch = new JTextField();
  private JButton btnSelect = new JButton();
  public QueryTableModel qtm;
  public String type;
  public Object owner;
  public JComboBox item;
  public String query;
  private Connection connection;
  private ListSelectionModel rowSM;
  public JTable table = new JTable();
  private JButton btnCancel = new JButton();
  private JButton btnHelp = new JButton();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif")); 
  private ImageIcon imageFilter = new ImageIcon(IISFrameMain.class.getResource("icons/filter.gif"));
  private JButton btnFilter = new JButton();
  private JButton btnSelect1 = new JButton();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  public JComboBox cmbReportType = new JComboBox();
  private int as;
  private JButton btnDelete = new JButton();
  private JButton btnDelete1 = new JButton();
  PTree tree;

  public Collision()
  {
    this(null, "", false,null,-1,null);
  }

  public Collision(IISFrameMain parent, String title, boolean modal,Connection con, int ass, PTree pt)
  {
    super(parent, title, modal);
    try
    { as=ass;
      tree=pt;
      connection=con;
         Iterator it=((PTree)(parent.desktop.getSelectedFrame())).WindowsManager.iterator();
      while(it.hasNext())
      {
        Object obj=(Object)it.next();
        Class cls=obj.getClass();
        if(cls==this.getClass())
        {if(((Collision)obj).as==as)
        { ((Collision)obj).dispose();
        }
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
    try{
    JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
    rs=query.select("select count(*) from IISC_LOG_TYPE");
    int j=-1;
    if(rs.next())
    j=rs.getInt(1);
    query.Close();
    String[] sa=query.selectArray("select * from IISC_LOG_TYPE ",j,2);
    sa[0]="";
    query.Close(); 
    for (int k=0; k<sa.length;k++)
    cmbReportType.addItem(sa[k]);
     }
    catch(SQLException ef)
    {
      JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

    }
     this.setResizable(false);
    qtm=new QueryTableModel(connection,-1);
    table=new JTable(qtm);
    this.setSize(new Dimension(477, 360));
    this.getContentPane().setLayout(null);
    this.setFont(new Font("SansSerif", 0, 11));
    jScrollPane1.setBounds(new Rectangle(5, 80, 460, 195));
    jScrollPane1.setFont(new Font("SansSerif", 0, 11));
    txtSearch.setBounds(new Rectangle(90, 15, 320, 20));
    txtSearch.setFont(new Font("SansSerif", 0, 11));
    btnSelect.setMaximumSize(new Dimension(50, 30));
    btnSelect.setPreferredSize(new Dimension(50, 30));
    btnSelect.setText("Reset");
    btnSelect.setBounds(new Rectangle(260, 290, 80, 30));
    btnSelect.setMinimumSize(new Dimension(50, 30));
    btnSelect.setFont(new Font("SansSerif", 0, 11));
    table.setFont(new Font("SansSerif", 0, 11));
    this.getContentPane().add(btnDelete1, null);
    this.getContentPane().add(btnDelete, null);
    this.getContentPane().add(cmbReportType, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(btnSelect1, null);
    this.getContentPane().add(btnFilter, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnCancel, null);
    this.getContentPane().add(btnSelect, null);
    this.getContentPane().add(txtSearch, null);
    jScrollPane1.getViewport().add(table, null);
    this.getContentPane().add(jScrollPane1, null);
    jScrollPane1.getViewport().add(table, null);
    table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
  table.setRowSelectionAllowed(true);
  table.setGridColor(new Color(0,0,0));
  table.setBackground(Color.white);
  table.setAutoResizeMode(1);
  table.setAutoscrolls(true);
  table.getTableHeader().setReorderingAllowed(false);
	rowSM = table.getSelectionModel();
    cmbReportType.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          cmbReportType_actionPerformed(e);
        }
      });
    btnDelete1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDeleteAll_actionPerformed(e);
        }
      });
    btnDelete1.setFont(new Font("SansSerif", 0, 11));
    btnDelete1.setMinimumSize(new Dimension(50, 30));
    btnDelete1.setBounds(new Rectangle(5, 290, 80, 30));
    btnDelete1.setText("Delete All");
    btnDelete1.setPreferredSize(new Dimension(50, 30));
    btnDelete1.setMaximumSize(new Dimension(50, 30));
    btnDelete.setMaximumSize(new Dimension(50, 30));
    btnDelete.setPreferredSize(new Dimension(50, 30));
    btnDelete.setText("Delete");
    btnDelete.setBounds(new Rectangle(90, 290, 80, 30));
    btnDelete.setMinimumSize(new Dimension(50, 30));
    btnDelete.setFont(new Font("SansSerif", 0, 11));
    btnDelete.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDelete_actionPerformed(e);
        }
      });
    this.setTitle("Reports - DB Schema log");
    cmbReportType.setBounds(new Rectangle(90, 45, 320, 20));
    jLabel2.setText("Report Type:");
    jLabel2.setBounds(new Rectangle(5, 45, 85, 20));
    jLabel1.setBounds(new Rectangle(5, 15, 85, 20));
    jLabel1.setText("Containing text:");
    btnSelect1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnSelect_actionPerformed(e);
        }
      });
    btnSelect1.setFont(new Font("SansSerif", 0, 11));
    btnSelect1.setMinimumSize(new Dimension(50, 30));
    btnSelect1.setBounds(new Rectangle(175, 290, 80, 30));
    btnSelect1.setText("View");
    btnSelect1.setPreferredSize(new Dimension(50, 30));
    btnSelect1.setMaximumSize(new Dimension(50, 30));
   
    btnFilter.setBounds(new Rectangle(425, 35, 35, 30));
    btnFilter.setFont(new Font("SansSerif", 0, 11));
    btnFilter.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnSearch_actionPerformed(e);
        }
      });
    btnFilter.setIcon(imageFilter);
    btnHelp.setIcon(imageHelp);
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    btnHelp.setFont(new Font("SansSerif", 0, 11));
    btnHelp.setBounds(new Rectangle(430, 290, 35, 30));
   
    btnCancel.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCancel_actionPerformed(e);
        }
      });
    btnCancel.setFont(new Font("SansSerif", 0, 11));
    btnCancel.setMinimumSize(new Dimension(50, 30));
    btnCancel.setBounds(new Rectangle(345, 290, 80, 30));
    btnCancel.setText("Cancel");
    btnCancel.setPreferredSize(new Dimension(50, 30));
    btnCancel.setMaximumSize(new Dimension(50, 30));
    btnSelect.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnReset_actionPerformed(e);
        }
      });
    txtSearch.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          txtSearch_actionPerformed(e);
        }
      });
    txtSearch.addKeyListener(new KeyAdapter()
      {
        public void keyReleased(KeyEvent e)
        {
          txtSearch_keyReleased(e);
        }
      });
    table.addMouseListener(new MouseAdapter() {

public void mouseReleased (MouseEvent me) {

doMouseClicked(me);

}

});
  }
void doMouseClicked (MouseEvent me) {

if(me.getClickCount()==2 && table.getSelectedRowCount()>0)
      {   JDBCQuery query=new JDBCQuery(connection);
          ResultSet rs1;
          int type=-1;
          try
          {
           rs1=query.select("select * from IISC_COLLISION_LOG where CL_id="+ table.getValueAt(table.getSelectedRow(),0));
           if(rs1.next())
          {type=rs1.getInt("CL_type");
          }
          query.Close();
          }
          catch(SQLException ef)
          {
           JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          
          }
          if(type<20)
          {Report rep=new Report((IISFrameMain)getParent(),"",true,connection,(new Integer(table.getValueAt(table.getSelectedRow(),0).toString())).intValue());
          Settings.Center(rep);
          rep.setVisible(true);}
          else
          {RepReport rep=new RepReport((IISFrameMain)getParent(),"",true,connection,(new Integer(table.getValueAt(table.getSelectedRow(),0).toString())).intValue(),tree);
          Settings.Center(rep);
          rep.setVisible(true);}
      }
}
  public void actionPerformed(ActionEvent e)
  {
  }

  private void btnSearch_actionPerformed(ActionEvent e)
  {
  Filter filt =new  Filter((IISFrameMain)getParent(),getTitle(), true, connection,this );
 Settings.Center(filt);
 filt.setVisible(true);
  }
  public void filter()
  {
  filter("");
  }
  public void filter(String q)
  {
   qtm.setCollisionQuery("select * from IISC_COLLISION_LOG,IISC_LOG_TYPE where IISC_COLLISION_LOG.CL_type=IISC_LOG_TYPE.CLT_id and CLT_name like '%"+ cmbReportType.getSelectedItem() +"%'  and CL_text like '%"+ txtSearch.getText() +"%' and AS_id="+as+ q + " order by CL_date desc, CLT_name asc"); 
   table.getColumnModel().getColumn(0).setPreferredWidth(0);
   table.getColumnModel().getColumn(0).setWidth(0);
   table.getColumnModel().getColumn(0).setMinWidth(0);
   table.getColumnModel().getColumn(0).setMaxWidth(0);
   table.getColumnModel().getColumn(3).setPreferredWidth(0);
   table.getColumnModel().getColumn(3).setWidth(0);
   table.getColumnModel().getColumn(3).setMinWidth(0);
   table.getColumnModel().getColumn(3).setMaxWidth(0);
  }
public void valueChanged(ListSelectionEvent e){}




  private void close_ActionPerformed(ActionEvent e)
  {  
  }



  private void btnSelect_actionPerformed(ActionEvent e)
  {
 if (table.getSelectedRowCount()>0)
      {   
          JDBCQuery query=new JDBCQuery(connection);
          ResultSet rs1;
          int type=-1;
          try
          {
           rs1=query.select("select * from IISC_COLLISION_LOG where CL_id="+ table.getValueAt(table.getSelectedRow(),0));
           if(rs1.next())
          {type=rs1.getInt("CL_type");
          }
          query.Close();
          }
          catch(SQLException ef)
          {
           JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          
          }
          if(type<20)
          {Report rep=new Report((IISFrameMain)getParent(),"",true,connection,(new Integer(table.getValueAt(table.getSelectedRow(),0).toString())).intValue());
          Settings.Center(rep);
          rep.setVisible(true);}
          else
          {RepReport rep=new RepReport((IISFrameMain)getParent(),"",true,connection,(new Integer(table.getValueAt(table.getSelectedRow(),0).toString())).intValue(),tree);
          Settings.Center(rep);
          rep.setVisible(true);}
      }
  }
   private void btnCancel_actionPerformed(ActionEvent e)
  {  
  dispose();
  }

  private void btnHelp_actionPerformed(ActionEvent e)
  {Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

 
  private void this_componentShown(ComponentEvent e)
  {
  }

 
 

  private void btnReset_actionPerformed(ActionEvent e)
  { 
   txtSearch.setText("");
   cmbReportType.setSelectedItem("");
   filter();
  }

  private void btnDelete_actionPerformed(ActionEvent e)
  {
   
    JDBCQuery query=new JDBCQuery(connection);
    int[] sel=table.getSelectedRows();
    for(int i=0; i<sel.length; i++)
    query.update("delete from IISC_COLLISION_LOG where CL_id="+ qtm.getValueAt(i,0));
    filter();
  }

  private void btnDeleteAll_actionPerformed(ActionEvent e)
  {JDBCQuery query=new JDBCQuery(connection);
   for(int i=0; i<table.getRowCount(); i++)
    query.update("delete from IISC_COLLISION_LOG where CL_id="+ qtm.getValueAt(i,0));
     filter();
 
  }

  private void txtSearch_actionPerformed(ActionEvent e)
  { filter();
  }

  private void cmbReportType_actionPerformed(ActionEvent e)
  { filter();
  }

  private void txtSearch_keyReleased(KeyEvent e)
  {filter();
  }
}