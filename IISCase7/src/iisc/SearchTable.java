package iisc;

import java.awt.Frame;
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
import javax.swing.*;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
public class SearchTable extends JDialog implements ActionListener, ListSelectionListener
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
  public JButton btnPrimitive = new JButton();
  private JButton btnCancel = new JButton();
  private JButton btnHelp = new JButton();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif")); 
  private ImageIcon imageFilter = new ImageIcon(IISFrameMain.class.getResource("icons/filter.gif"));
  public JButton btnNew = new JButton();
  private JButton btnFilter = new JButton();
  private JButton btnSelect1 = new JButton();
  private String treeId = "-1";
  private String ID2 = "-1";
  private String ID = "-1";
  private int typeExpr = 0;
  private Frame p;

  public SearchTable()
  {
    this(null, "", false,null);
  }

  public SearchTable(Frame parent, String title, boolean modal,Connection con)
  {
    super(parent, title, modal);
    try
    { 
            p = parent;
        connection=con;
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
  
    public SearchTable(Frame parent, String title, boolean modal,Connection con,String tID,String id2,String id,int type)
    {
      super(parent, title, modal);
      try
      { 
        typeExpr = type;
        connection=con;
        treeId = tID;
        ID2 = id2;
        ID = id;
        jbInit();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }

    }

  private void jbInit() throws Exception
  {  this.setResizable(false);
    qtm=new QueryTableModel(connection,-1);
    table=new JTable(qtm);
    this.setSize(new Dimension(490, 313));
    this.getContentPane().setLayout(null);
    this.setFont(new Font("SansSerif", 0, 11));
    jScrollPane1.setBounds(new Rectangle(5, 50, 470, 180));
    jScrollPane1.setFont(new Font("SansSerif", 0, 11));
    txtSearch.setBounds(new Rectangle(5, 15, 420, 20));
    txtSearch.setFont(new Font("SansSerif", 0, 11));
    btnSelect.setMaximumSize(new Dimension(50, 30));
    btnSelect.setPreferredSize(new Dimension(50, 30));
    btnSelect.setText("Reset");
    btnSelect.setBounds(new Rectangle(90, 240, 80, 30));
    btnSelect.setMinimumSize(new Dimension(50, 30));
    btnSelect.setFont(new Font("SansSerif", 0, 11));
    table.setFont(new Font("SansSerif", 0, 11));
    jScrollPane1.getViewport().add(table, null);
    this.getContentPane().add(btnSelect1, null);
    this.getContentPane().add(btnFilter, null);
    this.getContentPane().add(btnNew, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnCancel, null);
    this.getContentPane().add(btnPrimitive, null);
    this.getContentPane().add(btnSelect, null);
    this.getContentPane().add(txtSearch, null);
    jScrollPane1.getViewport().add(table, null);
    this.getContentPane().add(jScrollPane1, null);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  table.setRowSelectionAllowed(true);
  table.setGridColor(new Color(0,0,0));
  table.setBackground(Color.white);
  table.setAutoResizeMode(0);
  table.setAutoscrolls(true);
  table.getTableHeader().setReorderingAllowed(false);
	rowSM = table.getSelectionModel();
    this.addWindowListener(new WindowAdapter()
      {
        public void windowActivated(WindowEvent e)
        {
          this_windowActivated(e);
        }
      });
    this.addComponentListener(new ComponentAdapter()
      {
        public void componentShown(ComponentEvent e)
        {
          this_componentShown(e);
        }
      });
    btnSelect1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnSelect_actionPerformed(e);
        }
      });
    btnSelect1.setFont(new Font("SansSerif", 0, 11));
    btnSelect1.setMinimumSize(new Dimension(50, 30));
    btnSelect1.setBounds(new Rectangle(5, 240, 80, 30));
    btnSelect1.setText("Select");
    btnSelect1.setPreferredSize(new Dimension(50, 30));
    btnSelect1.setMaximumSize(new Dimension(50, 30));
    this.addPropertyChangeListener(new PropertyChangeListener()
      {
        public void propertyChange(PropertyChangeEvent e)
        {
          this_propertyChange(e);
        }
      });
    btnFilter.setBounds(new Rectangle(440, 10, 35, 30));
    btnFilter.setFont(new Font("SansSerif", 0, 11));
    btnFilter.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnFilter_actionPerformed(e);
        }
      });
    btnFilter.setIcon(imageFilter);
    btnNew.setMaximumSize(new Dimension(50, 30));
    btnNew.setPreferredSize(new Dimension(50, 30));
    btnNew.setBounds(new Rectangle(175, 240, 85, 30));
    btnNew.setMinimumSize(new Dimension(50, 30));
    btnNew.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          new_ActionPerformed(ae);
        }
      });
    btnNew.setFont(new Font("SansSerif", 0, 11));
    btnNew.setText("New");
    btnPrimitive.setText("Edit");
    btnHelp.setIcon(imageHelp);
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    btnHelp.setFont(new Font("SansSerif", 0, 11));
    btnHelp.setBounds(new Rectangle(440, 240, 35, 30));
    this.addFocusListener(new FocusAdapter()
      {
        public void focusGained(FocusEvent e)
        {
          this_focusGained(e);
        }
      });
    btnCancel.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCancel_actionPerformed(e);
        }
      });
    btnCancel.setFont(new Font("SansSerif", 0, 11));
    btnCancel.setMinimumSize(new Dimension(50, 30));
    btnCancel.setBounds(new Rectangle(355, 240, 80, 30));
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
    btnPrimitive.setFont(new Font("SansSerif", 0, 11));
    btnPrimitive.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          close_ActionPerformed(ae);
        }
      });
    btnPrimitive.setMinimumSize(new Dimension(50, 30));
    btnPrimitive.setBounds(new Rectangle(265, 240, 85, 30));
    btnPrimitive.setPreferredSize(new Dimension(50, 30));
    btnPrimitive.setMaximumSize(new Dimension(50, 30));
    table.addMouseListener(new MouseAdapter() {

public void mouseReleased (MouseEvent me) {

doMouseClicked(me);

}

});
  }
void doMouseClicked (MouseEvent me) {

if(me.getClickCount()==2)
choose();
}
  public void actionPerformed(ActionEvent e)
  {
  }

  private void btnSearch_actionPerformed(ActionEvent e)
  {
  }
public void valueChanged(ListSelectionEvent e){}



 private void new_ActionPerformed(ActionEvent e)
  {
  if (type=="Application Type")
  {AppType apptype=new AppType((IISFrameMain)getParent(),this.getTitle(),true,connection,-1,(PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame());
  Settings.Center(apptype);
  apptype.setVisible(true);
  }
  if(type=="Attribute")
  {Attribute att=new Attribute((IISFrameMain)getParent(),this.getTitle(),true,connection,-1,(PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame());
  Settings.Center(att);
  att.setVisible(true);}
  if(type=="Function")
  {
   Function fun=new Function((IISFrameMain)getParent(),"Function",true,connection,-1,(PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame());
      Settings.Center(fun);
      fun.setVisible(true);}
 if(type=="Parent domain" || type=="Domain")
  {
   Domain dom=new Domain((IISFrameMain)getParent(),"Domain",true,connection,-1,(PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame());
      Settings.Center(dom);
      dom.setVisible(true);
      }
  if(type=="Primitive Type")
  {
   PrimitiveTypes  ptypes=new PrimitiveTypes((IISFrameMain)getParent(),"Primitive Types",-1,true,connection,(PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame());
      ptypes.dlg=this;
      ptypes.dialog="Primitive Type";
      Settings.Center(ptypes);
      ptypes.setVisible(true);}
  }
 
 
 
 private void close_ActionPerformed(ActionEvent e)
  {try {
   if(type=="Application Type")
  {
  AppType apt;
   if(table.getSelectedRow()>=0)
     { JDBCQuery query=new JDBCQuery(connection);
      ResultSet rs1;
      int idp;
      rs1=query.select("select *  from IISC_APP_SYSTEM_TYPE  where AS_type='" +table.getValueAt(table.getSelectedRow(),0) + "'");
      if(rs1.next())
      idp=rs1.getInt("AS_type_id");
      else idp=-1;
      query.Close();
      apt=new AppType((IISFrameMain)getParent(),"Application Type",true,connection,idp,(PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame());
      Settings.Center(apt);
      apt.setVisible(true);
     }
  }
   if(type=="Attribute")
  {
  Attribute att;
   if(table.getSelectedRow()>=0)
     { JDBCQuery query=new JDBCQuery(connection);
      ResultSet rs1;
      int idp;
      rs1=query.select("select *  from IISC_Attribute  where Att_mnem='" +table.getValueAt(table.getSelectedRow(),0) + "'");
      if(rs1.next())
      idp=rs1.getInt("Att_id");
      else idp=-1;
      query.Close();
      att=new Attribute((IISFrameMain)getParent(),"Attributes",true,connection,idp,(PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame());
      Settings.Center(att);
      att.setVisible(true);
     }
  }
  if(type=="Function")
  {
  Function fun;
   if(table.getSelectedRow()>=0)
     { JDBCQuery query=new JDBCQuery(connection);
      ResultSet rs1;
      int idp;
      rs1=query.select("select *  from IISC_Function  where Fun_name='" +table.getValueAt(table.getSelectedRow(),0) + "' and  PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID);
      if(rs1.next())
      idp=rs1.getInt("Fun_id");
      else idp=-1;
      query.Close();
      fun=new Function((IISFrameMain)getParent(),"Functions",true,connection,idp,(PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame());
      Settings.Center(fun);
      fun.setVisible(true);
     }
  }
  if(type=="Domain" || type == "Domain2" || type == "Domain3" || type == "Domain4")
  {
  Domain dom;
   if(table.getSelectedRow()>=0)
     { JDBCQuery query=new JDBCQuery(connection);
      ResultSet rs1;
      int idp;
      rs1=query.select("select *  from IISC_Domain  where Dom_mnem='" +table.getValueAt(table.getSelectedRow(),0) + "' and  PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID );
      if(rs1.next())
      idp=rs1.getInt("Dom_id");
      else idp=-1;
      query.Close();
      dom=new Domain((IISFrameMain)getParent(),"Domains",true,connection,idp,(PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame());
      Settings.Center(dom);
      dom.setVisible(true);
     }
  }
  if(type=="Parent domain")
  {
  Domain dom;
   if(table.getSelectedRow()>=0)
     { JDBCQuery query=new JDBCQuery(connection);
      ResultSet rs1;
      int idp;
      rs1=query.select("select *  from IISC_Domain  where Dom_mnem='" +table.getValueAt(table.getSelectedRow(),0) + "' and  PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID);
      if(rs1.next())
      idp=rs1.getInt("Dom_id");
      else idp=-1;
      query.Close();
      dom=new Domain((IISFrameMain)getParent(),"Domains",true,connection,idp,(PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame());
      Settings.Center(dom);
      dom.setVisible(true);
     }
  }
  
   if(type=="Primitive Type")
  {
  PrimitiveTypes ptypes;
   if(table.getSelectedRow()>=0)
     { JDBCQuery query=new JDBCQuery(connection);
      ResultSet rs1;
      int idp;
      rs1=query.select("select *  from IISC_Primitive_type  where PT_mnemonic='" +table.getValueAt(table.getSelectedRow(),0) + "'");
      if(rs1.next())
      idp=rs1.getInt("PT_id");
      else idp=-1;
      query.Close();
      ptypes=new PrimitiveTypes((IISFrameMain)getParent(),"Primitive Types",idp,true,connection,(PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame());
      ptypes.dlg=this;
      ptypes.dialog="Primitive Type";
      Settings.Center(ptypes);
      ptypes.setVisible(true);
     }
  }
     }
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}
   
  }

  private void txtSearch_keyReleased(KeyEvent e)
  {
  if(type=="Form Type")
   {qtm.setFormQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id and TF_mnem like '%"+ txtSearch.getText() +"%'"); 
   table.getColumnModel().getColumn(0).setMinWidth(0);
  table.getColumnModel().getColumn(0).setMaxWidth(0);
  table.getColumnModel().getColumn(0).setPreferredWidth(0);
  table.getColumnModel().getColumn(0).setWidth(0);
   }
   if(type=="Application Type")
   qtm.setQueryAppType("select * from IISC_APP_SYSTEM_TYPE where As_type like '%"+ txtSearch.getText() +"%'"); 
   if(type=="Function")
   qtm.setQueryFun("select * from IISC_Function where  PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID + " and Fun_name like '%"+ txtSearch.getText() +"%'"); 
   if(type=="Attribute")
   qtm.setQueryAtt("select * from IISC_Attribute,IISC_DOMAIN where IISC_Attribute.Dom_id=IISC_DOMAIN.Dom_id and IISC_Attribute.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID + " and Att_mnem like '%"+ txtSearch.getText() +"%'" ); 
   if(type=="Component Type Attribute")
 { qtm.setAttributeType("select * from IISC_ATTRIBUTE,IISC_ATT_TOB,IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_FORM_TYPE,IISC_TF_APPSYS,IISC_APP_SYSTEM  where IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and IISC_ATT_TOB.TOB_id=IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and" + " IISC_APP_SYSTEM.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID + " and (W_tittle like '%"+ txtSearch.getText() +"%' or Att_mnem like '%"+ txtSearch.getText() +"%')"  ); 
table.getColumnModel().getColumn(0).setMinWidth(0);
  table.getColumnModel().getColumn(0).setMaxWidth(0);
  table.getColumnModel().getColumn(0).setPreferredWidth(0);
  table.getColumnModel().getColumn(0).setWidth(0);}
  if(type=="Primitive Type")
   qtm.setQueryPT("select * from IISC_primitive_type where PT_mnemonic like '%"+ txtSearch.getText() +"%'"); 
  if(type=="Parent domain")
   qtm.setQueryDom("select * from IISC_DOMAIN,IISC_primitive_type  where  PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +" and  PT_id=Dom_data_type and  Dom_id<>" + ((Domain)owner).id + " and Dom_mnem like '%"+ txtSearch.getText() +"%'" ); 
   if(type=="Domain")
   qtm.setQueryDom("select * from IISC_DOMAIN,IISC_primitive_type  where  PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +" and  PT_id=Dom_data_type  and Dom_mnem like '%"+ txtSearch.getText() +"%'" ); 
      if(type=="Domain2")
      qtm.setQueryDom("select * from IISC_DOMAIN,IISC_primitive_type  where  PR_id="+ treeId +" and  PT_id=Dom_data_type  and Dom_mnem like '%"+ txtSearch.getText() +"%'" ); 
      if(type=="Domain3" || type=="Domain4")
      qtm.setQueryDom("select * from IISC_DOMAIN,IISC_primitive_type  where  PR_id="+ treeId +" and  PT_id=Dom_data_type  and Dom_mnem like '%"+ txtSearch.getText() +"%'" );       
  }

  private void txtSearch_actionPerformed(ActionEvent e)
  {
  }

  private void choose()
  {
   JDBCQuery query=new JDBCQuery(connection);
     ResultSet rs;
  try{
   if(type=="Function" || type=="Attribute" || type=="Application Type" || type=="Component Type Attribute" || type=="Form Type")
  { if( type=="Application Type")
  ((AppSys)owner).Reload();
  else
  { try{
    ((Attribute)owner).Reload();
  }
  catch(Exception e){}
  try{
   Domain dom= ((Domain)owner);
   dom.Reload();
   int att=-1;
   rs=query.select("select * from IISC_ATTRIBUTE where ATT_mnem='"+ table.getValueAt(table.getSelectedRow(),0).toString() +"' and PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +"");
   if(rs.next())
   att=rs.getInt("Att_id");
   query.Close();
   if(att>-1)
   query.update("insert into IISC_DOM_ATT(Dom_id,Att_id,PR_id) values("+ dom.id +","+ att +","+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +")");
  }
  catch(Exception e){}
  try{
    ((AttType)owner).Reload();
  }
  catch(Exception e){}
  }
  if(type=="Component Type Attribute")
   item.setSelectedItem(table.getValueAt(table.getSelectedRow(),2).toString()+ " ("+ table.getValueAt(table.getSelectedRow(),4).toString()+")");
  else if(type=="Form Type")
   item.setSelectedItem(table.getValueAt(table.getSelectedRow(),1).toString().trim() + " ("+ table.getValueAt(table.getSelectedRow(),2).toString()+")");
  else if(item!=null)
    item.setSelectedItem(table.getValueAt(table.getSelectedRow(),0).toString());
      }
   if(type=="Parent domain")
  {((Domain)owner).Reload();
  if(item!=null)
        {
        item.setSelectedItem(table.getValueAt(table.getSelectedRow(),0).toString());
        }
        else
        {
  ((Domain)owner).cmbParent.setSelectedItem(table.getValueAt(table.getSelectedRow(),0).toString());
        }
      }
   if(type=="Domain")
  {((Attribute)owner).Reload();
  ((Attribute)owner).cmbDomain.setSelectedItem(table.getValueAt(table.getSelectedRow(),0).toString());
      }
  if(type=="Primitive Type")
  {((Domain)owner).Reload();
       if(item!=null)
        {
        item.setSelectedItem(table.getValueAt(table.getSelectedRow(),0).toString());
        }
        else
        {
     ((Domain)owner).txtPrimitive.setText(table.getValueAt(table.getSelectedRow(),0).toString());
  rs=query.select("select * from IISC_Primitive_type where PT_mnemonic='"+ table.getValueAt(table.getSelectedRow(),0) +"'");
      if(rs.next())
      {
        String decimal=rs.getString("PT_decimal");
        if(decimal!=null && !decimal.equals(""))((Domain)owner).txtDecimal.setEnabled(true);
        else ((Domain)owner).txtDecimal.setEnabled(false);
        if(!rs.getString("PT_length").equals(""))((Domain)owner).txtLength.setEnabled(true);
        else ((Domain)owner).txtLength.setEnabled(false);
      }
query.Close();
        }
      }
      if(type == "Domain2"){      
          //((AddDisplayValue)owner).Reload();
            ((AddDisplayValue)owner).domainCombo.setSelectedItem(table.getValueAt(table.getSelectedRow(),0).toString());        
      }
      if(type == "Domain3"){      
          //((AddDisplayValue)owner).Reload();
            ((Function)owner).returnCombo.setSelectedItem(table.getValueAt(table.getSelectedRow(),0).toString());        
      } 
      if(type == "Domain4"){      
          //((AddDisplayValue)owner).Reload();
          item.setSelectedItem(table.getValueAt(table.getSelectedRow(),0).toString());
      }
      if(type == "Regular Expression Editor"){
            String val = "";
          if( !table.getValueAt(table.getSelectedRow(),1).toString().trim().equals("") )
              val += table.getValueAt(table.getSelectedRow(),1).toString().trim() + " | ";                
          if( !table.getValueAt(table.getSelectedRow(),2).toString().trim().equals("") )
              val += table.getValueAt(table.getSelectedRow(),2).toString().trim() + " | ";  
          if( !table.getValueAt(table.getSelectedRow(),3).toString().trim().equals("") )
              val += table.getValueAt(table.getSelectedRow(),3).toString().trim() + " | ";          
              
          val += table.getValueAt(table.getSelectedRow(),2).toString().trim();
          
          //((CheckConstraintExpressionEditor)owner).cmbAddExpr.setSelectedIndex( Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString()) );
        ((CheckConstraintExpressionEditor)owner).cmbAddExpr.removeAllItems();
            
        ((CheckConstraintExpressionEditor)owner).cmbAddExpr.addItem("");
        ((CheckConstraintExpressionEditor)owner).cmbAddExpr.addItem(val);
          ((CheckConstraintExpressionEditor)owner).cmbAddExpr.setSelectedIndex(1);
        //setSelectedIndex( Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString()) );
      }
  }
       catch (SQLException ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);}
      
  this.dispose(); 
  }
  private void btnSelect_actionPerformed(ActionEvent e)
  {
  choose();
  }
   private void btnCancel_actionPerformed(ActionEvent e)
  { //((IISFrameMain)getParent()).dom.setDomain(((IISFrameMain)getParent()).dom.Mnem);
  dispose();
  }

  private void this_focusGained(FocusEvent e)
  {refresh(); 
  }
private void refresh()
{ if(type=="Form Type")
   {qtm.setFormQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id"); 
   table.getColumnModel().getColumn(0).setMinWidth(0);
  table.getColumnModel().getColumn(0).setMaxWidth(0);
  table.getColumnModel().getColumn(0).setPreferredWidth(0);
  table.getColumnModel().getColumn(0).setWidth(0);}
  if(type=="Application Type")
   qtm.setQueryAppType("select * from IISC_APP_SYSTEM_TYPE"); 
  if(type=="Function")
   qtm.setQueryFun("select * from IISC_Function where  PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ); 
   if(type=="Attribute")
   qtm.setQueryAtt("select * from IISC_Attribute,IISC_DOMAIN where IISC_Attribute.Dom_id=IISC_DOMAIN.Dom_id and  IISC_Attribute.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ); 
if(type=="Component Type Attribute")
  { qtm.setAttributeType("select * from IISC_ATTRIBUTE,IISC_ATT_TOB,IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_FORM_TYPE,IISC_TF_APPSYS,IISC_APP_SYSTEM  where IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and IISC_ATT_TOB.TOB_id=IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_APP_SYSTEM.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID + " and W_tittle like '%"+ txtSearch.getText() +"%'"  ); 
  table.getColumnModel().getColumn(0).setMinWidth(0);
  table.getColumnModel().getColumn(0).setMaxWidth(0);
  table.getColumnModel().getColumn(0).setPreferredWidth(0);
  table.getColumnModel().getColumn(0).setWidth(0);
  }
   if(type=="Primitive Type")
   qtm.setQueryPT("select * from IISC_primitive_type"); 
  if(type=="Parent domain")
   qtm.setQueryDom("select * from IISC_DOMAIN ,IISC_primitive_type  where  PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +" and   PT_id=Dom_data_type and   Dom_id<>" + ((Domain)owner).id ); 
  if(type=="Domain")
   qtm.setQueryDom("select * from IISC_DOMAIN ,IISC_primitive_type  where  PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +" and   PT_id=Dom_data_type " ); 

}
  private void btnHelp_actionPerformed(ActionEvent e)
  {
  Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
 Settings.Center(hlp);
 hlp.setVisible(true);
  
  }

  private void this_propertyChange(PropertyChangeEvent e)
  {
          if(type=="Form Type")
          { qtm.setFormQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id"); 
            query="select * from IISC_TF_APPSYS,IISC_FORM_TYPE where from IISC_TF_APPSYS.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id";
          table.getColumnModel().getColumn(0).setMinWidth(0);
          table.getColumnModel().getColumn(0).setMaxWidth(0);
          table.getColumnModel().getColumn(0).setPreferredWidth(0);
          table.getColumnModel().getColumn(0).setWidth(0);
          }
          if(type=="Application Type")
          { qtm.setQueryAppType("select * from IISC_APP_SYSTEM_TYPE"); 
          query="select * from IISC_APP_SYSTEM_TYPE";
          }
          if(type=="Function")
           {qtm.setQueryFun("select * from IISC_Function where  PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ); 
          query="select * from IISC_Function where  PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ;
          } 
          if(type=="Attribute")
           {qtm.setQueryAtt("select * from IISC_Attribute,IISC_DOMAIN where IISC_Attribute.Dom_id=IISC_DOMAIN.Dom_id and IISC_Attribute.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ); 
          query="select * from IISC_Attribute,IISC_DOMAIN where IISC_Attribute.Dom_id=IISC_DOMAIN.Dom_id and IISC_Attribute.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID;
          }
          if(type=="Component Type Attribute")
           {qtm.setAttributeType("select * from IISC_ATTRIBUTE,IISC_ATT_TOB,IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_FORM_TYPE,IISC_TF_APPSYS,IISC_APP_SYSTEM  where IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and IISC_ATT_TOB.TOB_id=IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_APP_SYSTEM.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ); 
          query="select * from IISC_ATTRIBUTE,IISC_ATT_TOB,IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_FORM_TYPE,IISC_TF_APPSYS,IISC_APP_SYSTEM  where IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and IISC_ATT_TOB.TOB_id=IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_APP_SYSTEM.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ;
          table.getColumnModel().getColumn(0).setMinWidth(0);
          table.getColumnModel().getColumn(0).setMaxWidth(0);
          table.getColumnModel().getColumn(0).setPreferredWidth(0);
          table.getColumnModel().getColumn(0).setWidth(0);}
          if(type=="Primitive Type")
           {qtm.setQueryPT("select * from IISC_primitive_type"); 
          query="select * from IISC_primitive_type";
          }
          if(type=="Parent domain")
          { qtm.setQueryDom("select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +" and  PT_id=Dom_data_type and   Dom_id<>" + ((Domain)owner).id ); 
          query="select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +" and  PT_id=Dom_data_type and   Dom_id<>" + ((Domain)owner).id ;
          }
          if(type=="Domain")
          { qtm.setQueryDom("select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +" and  PT_id=Dom_data_type" ); 
         query="select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +" and  PT_id=Dom_data_type";
          }
          if(type == "Regular Expression Editor"){
                qtm.setQueryRegExpr(typeExpr,treeId,ID2,ID);
              table.getColumnModel().getColumn(0).setMinWidth(0);
              table.getColumnModel().getColumn(0).setMaxWidth(0);
              table.getColumnModel().getColumn(0).setPreferredWidth(0);
              table.getColumnModel().getColumn(0).setWidth(0);
              table.getColumnModel().getColumn(1).setPreferredWidth(100);
              table.getColumnModel().getColumn(1).setWidth(100);
              table.getColumnModel().getColumn(2).setPreferredWidth(100);
              table.getColumnModel().getColumn(2).setWidth(100);
              table.getColumnModel().getColumn(3).setPreferredWidth(120);
              table.getColumnModel().getColumn(3).setWidth(120);
              table.getColumnModel().getColumn(4).setPreferredWidth(55);
              table.getColumnModel().getColumn(4).setWidth(55);              
              
              btnNew.setEnabled(false);
              btnPrimitive.setEnabled(false);
                //query="select * from IISC_DOMAIN where PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ;              
          }
         if(type == "Domain2"){
             btnNew.setEnabled(false);
             btnPrimitive.setEnabled(false);
             qtm.setQueryDom("select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ treeId +" and  PT_id=Dom_data_type" ); 
                      query="select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ treeId +" and  PT_id=Dom_data_type";
         }
     if(type == "Domain3" || type == "Domain4"){
         btnNew.setEnabled(false);
         btnPrimitive.setEnabled(false);
         qtm.setQueryDom("select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ treeId +" and  PT_id=Dom_data_type" ); 
                  query="select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ treeId +" and  PT_id=Dom_data_type";
     }         
 }

  private void btnReset_actionPerformed(ActionEvent e)
  {
   if(type=="Form Type")
  { qtm.setFormQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id"); 
    query="select * from IISC_TF_APPSYS,IISC_FORM_TYPE where from IISC_TF_APPSYS.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id";
  table.getColumnModel().getColumn(0).setMinWidth(0);
  table.getColumnModel().getColumn(0).setMaxWidth(0);
  table.getColumnModel().getColumn(0).setPreferredWidth(0);
  table.getColumnModel().getColumn(0).setWidth(0);}
  if(type=="Application Type")
  { qtm.setQueryAppType("select * from IISC_APP_SYSTEM_TYPE"); 
  query="select * from IISC_APP_SYSTEM_TYPE";
  }
  if(type=="Function")
   {qtm.setQueryFun("select * from IISC_Function where  PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ); 
  query="select * from IISC_Function where  PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ;
  } 
  if(type=="Attribute")
   {qtm.setQueryAtt("select * from IISC_Attribute,IISC_DOMAIN where IISC_Attribute.Dom_id=IISC_DOMAIN.Dom_id and IISC_Attribute.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ); 
  query="select * from IISC_Attribute,IISC_DOMAIN where IISC_Attribute.Dom_id=IISC_DOMAIN.Dom_id and IISC_Attribute.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID;
  }
  if(type=="Component Type Attribute")
   {qtm.setAttributeType("select * from IISC_ATTRIBUTE,IISC_ATT_TOB,IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_FORM_TYPE,IISC_TF_APPSYS,IISC_APP_SYSTEM  where IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and IISC_ATT_TOB.TOB_id=IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_APP_SYSTEM.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ); 
  query="select * from IISC_ATTRIBUTE,IISC_ATT_TOB,IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_FORM_TYPE,IISC_TF_APPSYS,IISC_APP_SYSTEM  where IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and IISC_ATT_TOB.TOB_id=IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_APP_SYSTEM.PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ;
  table.getColumnModel().getColumn(0).setMinWidth(0);
  table.getColumnModel().getColumn(0).setMaxWidth(0);
  table.getColumnModel().getColumn(0).setPreferredWidth(0);
  table.getColumnModel().getColumn(0).setWidth(0);
  }
  if(type=="Primitive Type")
   {qtm.setQueryPT("select * from IISC_primitive_type"); 
  query="select * from IISC_primitive_type";
  }
  if(type=="Parent domain")
  { qtm.setQueryDom("select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +" and  PT_id=Dom_data_type and   Dom_id<>" + ((Domain)owner).id ); 
  query="select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +" and  PT_id=Dom_data_type and   Dom_id<>" + ((Domain)owner).id ;
  }
  if(type=="Domain")
  { qtm.setQueryDom("select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +" and  PT_id=Dom_data_type" ); 
 query="select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID +" and  PT_id=Dom_data_type";
  }
      if(type == "Domain2"){
          qtm.setQueryDom("select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ treeId +" and  PT_id=Dom_data_type" ); 
                   query="select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ treeId +" and  PT_id=Dom_data_type";
      }  
      if(type == "Domain3" || type == "Domain4"){
          qtm.setQueryDom("select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ treeId +" and  PT_id=Dom_data_type" ); 
                   query="select * from IISC_DOMAIN,IISC_primitive_type  where PR_id="+ treeId +" and  PT_id=Dom_data_type";
      }        
      if(type == "Regular Expression Editor"){
            qtm.setQueryRegExpr(typeExpr,treeId,ID2,ID);
          table.getColumnModel().getColumn(0).setMinWidth(0);
          table.getColumnModel().getColumn(0).setMaxWidth(0);
          table.getColumnModel().getColumn(0).setPreferredWidth(0);
          table.getColumnModel().getColumn(0).setWidth(0);
            //query="select * from IISC_DOMAIN where PR_id="+ ((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).ID ;              
      }  
  txtSearch.setText("");
  }

  private void this_componentShown(ComponentEvent e)
  {
  }

  private void this_windowActivated(WindowEvent e)
  { 
  }

  private void btnFilter_actionPerformed(ActionEvent e)
  {
  //System.out.println(getParent().getClass().toString());

  Filter filt =new  Filter((IISFrameMain) p,getTitle(), true, connection,this );
 Settings.Center(filt);
 filt.setVisible(true);
  }

}