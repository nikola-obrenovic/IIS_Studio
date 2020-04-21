package iisc;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import javax.swing.JLabel;
import java.sql.*;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.text.html.*;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentEvent;
public class RepReport extends JDialog implements ActionListener
{
  public JButton btnClose = new JButton();
  private Connection con;
  private String tittle;
  private int ID;
  private ButtonGroup bgrp=new ButtonGroup();
  public String dialog=new String();
  private JLabel lbForm = new JLabel();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JEditorPane txtReport = new JEditorPane();
  private String link=new String();
  private ResultSet rs;
  private PTree tree;
  
  public RepReport()
  {
    this(null, "", false,null,-1,null);
  }

  public RepReport(IISFrameMain parent, String title, boolean modal, Connection conn,int id,PTree tr)
  {
    super(parent, title, modal);
    try
    { tittle=title;
      con=conn;
      ID=id;
      tree=tr;
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {   
    this.setSize(new Dimension(800, 600));
    this.getContentPane().setLayout(null);
    this.setTitle("Repository Report");
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Close");
    btnClose.setBounds(new Rectangle(707, 532, 80, 30));
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnClose_actionPerformed(e);
        }
      });
    lbForm.setBounds(new Rectangle(90, 15, 285, 20));
    lbForm.setFont(new Font("Dialog", 1, 11));
    jScrollPane1.setBounds(new Rectangle(5, 5, 782, 522));
    jScrollPane1.getViewport().add(txtReport, null);
    this.getContentPane().add(jScrollPane1);
    this.getContentPane().add(lbForm, null);
    this.getContentPane().add(btnClose);
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    ResultSet rs, rs1;  
    txtReport.setEditable(false);
    HTMLEditorKit kit=new HTMLEditorKit();
    this.addComponentListener(new ComponentAdapter()
      {
        public void componentResized(ComponentEvent e)
        {
          this_componentResized(e);
        }
      });
    txtReport.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          txtReport_mouseClicked(e);
        }
      });
    txtReport.addHyperlinkListener(new HyperlinkListener()
      {
        public void hyperlinkUpdate(HyperlinkEvent e)
        {
          txtReport_hyperlinkUpdate(e);
        }
      });
    btnClose.setFont(new Font("SansSerif", 0, 11));
    txtReport.setFont(new Font("SansSerif", 0, 11));
    this.setFont(new Font("SansSerif", 0, 11));
    txtReport.setEditorKit(kit);

    try
    {  
    rs=query.select("select * from IISC_COLLISION_LOG,IISC_LOG_TYPE where IISC_COLLISION_LOG.CL_type=IISC_LOG_TYPE.CLT_id and CL_id="+ ID +"");
    if(rs.next())
    {
      String str1=rs.getString("CLT_rule");
      int t=rs.getInt("CLT_id");
      //System.out.println("********************************************************" + t);
      
      if (t<30) {
      rs1=query1.select("select * from IISC_COLLISION_LOG, IISC_APP_SYSTEM where IISC_COLLISION_LOG.AS_id=IISC_APP_SYSTEM.AS_id and CL_id="+ ID +"");
      if(rs1.next()) 
        {      
        String asname="<a href=as=" + rs1.getString("AS_id") + ">" +  rs1.getString("AS_name") + "</a>"; 
        String[] stra= ODBCList.splitString(str1,"appxxx");
        stra[0]=stra[0]+ asname;
        stra[0]=stra[0]+ stra[1] ;        
        String[] strb= ODBCList.splitString(stra[0],"tablexxx");
        strb[0]=strb[0]+ rs.getString("CL_text") ;
        strb[0]=strb[0]+ strb[1];
        String text="<html><font style=\"font-family:SansSerif;font-size:12pt;\"><b> Report type:</b> " +t + "</b> <font color=#FFFFFF> ..............................................................</font> <b>" + rs.getString("CLT_name") + "</b><br><b>Report id: </b>" + rs.getString("CL_id") + "<br><b>Report date: </b>" + rs.getString("CL_date") + "<br><b>Application system:</b> "+ asname +"<br><br>"+strb[0] + "  " ;
        txtReport.setText(text);
        txtReport.setCaretPosition(0);
        }
      query1.Close();
      }
      else {      
      rs1=query1.select("select * from IISC_COLLISION_LOG, IISC_PROJECT where IISC_COLLISION_LOG.PR_id=IISC_PROJECT.PR_id and CL_id="+ ID +"");
      if(rs1.next()) 
        {     
        String prname="<b>"+rs1.getString("PR_name")+"</b>";      
        String[] stra= ODBCList.splitString(str1,"prxxx");
        stra[0]=stra[0]+ prname;
        stra[0]=stra[0]+ stra[1];
        String[] strb= ODBCList.splitString(stra[0],"tablexxx");
        strb[0]=strb[0]+ rs.getString("CL_text") ;
        strb[0]=strb[0]+ strb[1];      
        String text="<html><font style=\"font-family:SansSerif;font-size:12pt;\"><b> Report type:</b> " +t + "</b> <font color=#FFFFFF> .................................................................</font> <b>" + rs.getString("CLT_name") + "</b><br><b>Report id: </b>" + rs.getString("CL_id") + "<br><b>Report date: </b>" + rs.getString("CL_date") + "<br><b>Project: </b> "+ prname +"<br><br>"+strb[0] + "  " ;
        txtReport.setText(text);
        txtReport.setCaretPosition(0);        
        }
      query1.Close();
      }      
      
      
    }
    query.Close();
    }
    catch(SQLException e)
    {
    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
 catch(Exception e)
    {
    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    }
     public void actionPerformed(ActionEvent ae)
        {

        }

  private void btnClose_actionPerformed(ActionEvent e)
  {dispose();
  }

  private void txtReport_hyperlinkUpdate(HyperlinkEvent e)
  {
  link= e.getDescription().toString();
  }

  private void txtReport_mouseClicked(MouseEvent e)
  {
  try
  {
  if(!link.equals(""))
  { 
   JDBCQuery query=new JDBCQuery(con);
   ResultSet rs;
   String[] split= ODBCList.splitString(link,"=");
   link="";
   
   if(split.length<=1)
   { 
    String str="";
    rs=query.select("select * from IISC_APP_SYSTEM,IISC_RELATION_SCHEME where  IISC_APP_SYSTEM.AS_id=IISC_RELATION_SCHEME.AS_id and RS_id="+ split[0]);
    if(rs.next())
    {
      str=rs.getString("AS_name");
      int idr=(new Integer(split[0])).intValue();      
      PTree tr=(PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame());
      RScheme rsh=new RScheme((IISFrameMain)getParent(),this.getTitle(),true,con,idr,tr,str);
      Settings.Center(rsh);
      rsh.setVisible(true);
    }
    else
      JOptionPane.showMessageDialog(null,"<html><center>Releation scheme doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
      query.Close();        
   }
   
   else if(split[0].equals("as"))
   {
        PTree tr=(PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame());
        AppSys appsys=new AppSys((IISFrameMain)getParent(),this.getTitle(),true,con,(new Integer(split[1])).intValue(),tr);
        Settings.Center(appsys);
        appsys.setVisible(true);
   }
   else if(split[0].equals("tf"))
   { 
    String str="";
    rs=query.select("select * from IISC_APP_SYSTEM,IISC_TF_APPSYS where IISC_APP_SYSTEM.AS_id=IISC_TF_APPSYS.AS_id and TF_id="+ split[1]);
    if(rs.next())
    {
      str=rs.getString("AS_name");            
      PTree tr=(PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame());
      Form frm = new Form((IISFrameMain)getParent(),this.getTitle(),true,con,(new Integer(split[1])).intValue(),tr,str);
      Settings.Center(frm);
      frm.setVisible(true);      
    }
    else
      JOptionPane.showMessageDialog(null,"<html><center>Form type doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
      query.Close();        
   }
   else if(split[0].equals("att"))
   {
        PTree tr=(PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame());
        Attribute atr=new Attribute((IISFrameMain)getParent(),this.getTitle(),true,con,(new Integer(split[1])).intValue(),tr);
        Settings.Center(atr);
        atr.setVisible(true);
   }
   else if(split[0].equals("dom"))
   {
        PTree tr=(PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame());
        Domain domen = new Domain((IISFrameMain)getParent(),this.getTitle(),true,con,(new Integer(split[1])).intValue(),tr);
        Settings.Center(domen);
        domen.setVisible(true);
   }
   else if(split[0].equals("pt"))
   {
        PTree tr=(PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame());        
        PrimitiveTypes primtype = new PrimitiveTypes((IISFrameMain)getParent(),this.getTitle(),(new Integer(split[1])).intValue(),true,con,tr);
        Settings.Center(primtype);
        primtype.setVisible(true);
   }
  }
  JDBCQuery query=new JDBCQuery(con);
  String ldate="";
  int tip;
  rs=query.select("select CL_date,Cl_type from IISC_COLLISION_LOG where IISC_COLLISION_LOG.CL_id=" + ID);
  if(rs.next())
  ldate=rs.getString(1);
  tip=rs.getInt(2);
  query.Close();
  String ime="";
  rs=query.select("select CLT_name from IISC_LOG_TYPE where CLT_id=" + tip);
  if(rs.next())  
  ime=rs.getString(1);
  query.Close();    
  if (ID>20 && ID<31) 
  tree.select_node(ldate,ime,"Repository reports","Reports");
  else if (ID>31)
  tree.select_node(ldate,ime);        
  }
  catch(Exception ae)
  {
    JOptionPane.showMessageDialog(null, ae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
  }
  }

 
  private void this_componentResized(ComponentEvent e)
  {
  jScrollPane1.setPreferredSize(new Dimension( this.getWidth()-10,this.getHeight()));
  jScrollPane1.setBounds(5,5, this.getWidth()-15,this.getHeight()-95);
  txtReport.setPreferredSize(new Dimension( this.getWidth()-10,this.getHeight()));
 txtReport.setBounds(0,0, this.getWidth()-10,this.getHeight()-90);
 btnClose.setBounds(new Rectangle(this.getWidth()-95, this.getHeight()-70, 80, 30));
  }
}
