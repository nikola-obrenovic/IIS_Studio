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
public class Report extends JDialog implements ActionListener
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
  private int linkOK=1;

  public Report()
  {
    this(null, "", false,null,-1);
  }

  public Report(IISFrameMain parent, String title, boolean modal, Connection conn,int id )
  {
    super(parent, title, modal);
    try
    { tittle=title;
      con=conn;
      ID=id;
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {   
    this.setSize(new Dimension(588, 483));
    this.getContentPane().setLayout(null);
    this.setTitle("Report - DB Schema collisions" );
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Close");
    btnClose.setBounds(new Rectangle(495, 415, 80, 30));
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
    jScrollPane1.setBounds(new Rectangle(5, 5, 570, 405));
    jScrollPane1.getViewport().add(txtReport, null);
    this.getContentPane().add(jScrollPane1);
    this.getContentPane().add(lbForm, null);
    this.getContentPane().add(btnClose);
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;  
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
    rs=query.select("select * from IISC_COLLISION_LOG,IISC_LOG_TYPE,IISC_APP_SYSTEM where  IISC_COLLISION_LOG.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_COLLISION_LOG.CL_type=IISC_LOG_TYPE.CLT_id and CL_id="+ ID +"");
    if(rs.next())
    { if(rs.getInt("CL_warning")==1)
      this.setTitle("Report - DB Schema collision warnings");
      String asname="<a href=as=" + rs.getString("AS_id") + ">" +  rs.getString("AS_name") + "</a>"; 
      String str1=rs.getString("CLT_rule");
      if(rs.getInt("CL_type")>=10)
      this.setTitle("DB Schema Design Report");
      int t=rs.getInt("CLT_id");
      if(t>=10 && t<50)
      {
       String[] stra= ODBCList.splitString(str1,"fundep");
      stra[0]=stra[0]+ rs.getString("CL_text");
      stra[0]=stra[0]+ stra[1] ;
        String text="<html><font style=\"font-family:SansSerif;font-size:12pt;\"><b> Report type:</b> " + t + "</b> <font color=#FFFFFF> ............</font> <b>" + rs.getString("CLT_name") + "</b><br><b>Report id: </b>" + rs.getString("CL_id") + "<br><b>Report date: </b>" + rs.getString("CL_date") + "<br><b>Application system:</b> "+ asname +"<br><br>"+stra[0] + "  " ;
    txtReport.setText(text);       
      }
        else if (t==5)
        {
            String cltext=rs.getString("CL_text");
            String text="<html><font style=\"font-family:SansSerif;font-size:12pt;\"><b> Report type:</b> " +t 
                        + "</b> <font color=#FFFFFF> ............</font> <b>" + rs.getString("CLT_name") 
                        + "</b><br><b>Report id: </b>" + rs.getString("CL_id") + "<br><b>Report date: </b>" + rs.getString("CL_date") 
                        + "<br><b>Application system:</b> "+ asname +"<br><br><table border=1>"+cltext+ "</table>  " ;
            txtReport.setText(text);
        }
      else
      {
          String cltext=rs.getString("CL_text");
          String cltext1="";
          String[] pr=cltext.split("###");
          if(pr.length>1) {
              cltext=pr[0];
              cltext1=pr[1];
          }
          else {
              cltext=pr[0];
          }
          String[] stra= str1.split("appxxx");
          String strnew=stra[0] + asname + stra[1];
          if(stra.length>2)
              strnew=strnew + asname + stra[2];
          String[] strb= strnew.split("tablexxx");
          strnew=strb[0] + cltext + strb[1];
          String[] strc= strnew.split("tableyyy");
          if(strc.length>1)
            strnew=strc[0] + cltext1 + strc[1];          
          String text="<html><font style=\"font-family:SansSerif;font-size:12pt;\"><b> Report type:</b> " +t + "</b> <font color=#FFFFFF> ............</font> <b>" + rs.getString("CLT_name") + "</b><br><b>Report id: </b>" + rs.getString("CL_id") + "<br><b>Report date: </b>" + rs.getString("CL_date") + "<br><b>Application system:</b> "+ asname +"<br><br>"+strnew+ "  " ;
          txtReport.setText(text);
      }
    }
    query.Close();
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

   if(split.length<=1)
   { 
    String str="";
    rs=query.select("select * from IISC_APP_SYSTEM,IISC_RELATION_SCHEME where  IISC_APP_SYSTEM.AS_id=IISC_RELATION_SCHEME.AS_id and RS_id="+ link);
    if(rs.next())
    {
    str=rs.getString("AS_name");
     int idr=(new Integer(link)).intValue();
    PTree tr=(PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame());
      RScheme rsh=new RScheme((IISFrameMain)getParent(),this.getTitle(),true,con,idr,tr,str);
      Settings.Center(rsh);
      rsh.setVisible(true);
      }
      else
      JOptionPane.showMessageDialog(null,"<html><center>Releation scheme doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
    query.Close();
   
    link="";   
   }   
   else if(split[0].equals("integrity"))
   {
    int ass=-1;
      rs=query.select("select * from IISC_RS_CONSTRAINT where  RSC_id="+ split[1]);
    if(rs.next())
    {
    ass=rs.getInt("AS_id");   
    int idr=(new Integer(split[1])).intValue();
    PTree tr=(PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame());
      Constraint rsc=new Constraint((IISFrameMain)getParent(),"",idr,true,con,tr.ID,ass);
      Settings.Center(rsc);
      rsc.setVisible(true);
    }
     else
      JOptionPane.showMessageDialog(null,"<html><center>Constraint doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
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
      rs=query.select("select * from IISC_APP_SYSTEM, IISC_TF_APPSYS, IISC_FORM_TYPE where  IISC_APP_SYSTEM.AS_id=IISC_TF_APPSYS.AS_id and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id and IISC_FORM_TYPE.TF_id="+ split[1]);
      if(rs.next())
      {
          String str=rs.getString("AS_name");
          int idr=(new Integer(split[1])).intValue();
          PTree tr=(PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame());
          Form form=new Form((IISFrameMain)getParent(),tr.getTitle(),true,con,idr,tr,str);
          Settings.Center(form);
          form.setVisible(true);
      }
      else
      JOptionPane.showMessageDialog(null,"<html><center>Form Type doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
      query.Close();
  }
  }
  }
  catch(Exception ae)
  {
    JOptionPane.showMessageDialog(null, ae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
  }
      link="";
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