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

public class RepFilter extends JDialog
{ private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable table = new JTable();
  private JButton btnSave = new JButton();
  public JButton btnClose = new JButton();
  private JButton btnHelp = new JButton();
  public QueryTableModel qtm;   
  ResultSet rs;
  private int pr_id, as_id, clt_id, rep_id,k;
  private Connection con;
  private PTree tree;
 



 public RepFilter(IISFrameMain parent, String title, boolean modal, Connection conn, int t_id, int p_id, int a_id,PTree tr)
  { 
    super((Frame)parent, title, modal);
    try
    {
    clt_id=t_id;
    con=conn;
    pr_id=p_id;
    as_id=a_id;
    clt_id=t_id;
    tree=tr;
    jbInit1();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
    private void jbInit1() throws Exception
  { JDBCQuery query=new JDBCQuery(con);
    this.setResizable(false);
    this.setSize(new Dimension(398, 306));
    this.getContentPane().setLayout(null);
    this.setFont(new Font("SansSerif", 0, 11));
    this.setTitle("Filter");
    jScrollPane1.setBounds(new Rectangle(5, 10, 380, 210));
    jScrollPane1.setFont(new Font("SansSerif", 0, 11));
    btnSave.setMaximumSize(new Dimension(50, 30));
    btnSave.setPreferredSize(new Dimension(50, 30));
    btnSave.setText("Generate");
    btnSave.setBounds(new Rectangle(175, 230, 85, 30));
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
    
    
    rs=query.select("select count(*) from IISC_LOG_TYPE_PARAMS where CLT_Id= "+clt_id);    
    if(rs.next())
    {
     k=rs.getInt(1);
    }        
    query.Close();
    String[] col=new String[k];
    
    rs=query.select("select CLP_name from IISC_LOG_TYPE_PARAMS where IISC_LOG_TYPE_PARAMS.CLT_id="+ clt_id +" order by CLP_id");
    int i=0;
    while (rs.next())
    { 
     col[i]=rs.getString(1);
     i++;
    }        
    query.Close();
      
    qtm=new QueryTableModel(con,-1);
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


  public void valueChanged(ListSelectionEvent e){}


 private void save1_ActionPerformed(ActionEvent e)
  { 
    
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    JDBCQuery query2=new JDBCQuery(con);
    JDBCQuery query3=new JDBCQuery(con);
    ResultSet rs, rs1, rs2, rs3;  
    String str="",str1="",str2="",str3="",str4="",fus="",fun="",superord="";
    String usl1="",usl2="", usl3="",usl1a="",usl2a="",usl4="";
  
  
      try 
    {   
      
      if (clt_id == 21)
      { 
      if (table.getValueAt(0,1).toString()!="")
      usl1=" and Tf_mnem like '"+ table.getValueAt(0,1).toString()+"' ";
      if (table.getValueAt(1,1).toString()!="")
      usl2=" and Tf_title like '"+ table.getValueAt(1,1).toString()+"' ";
      if (table.getValueAt(2,1).toString().equalsIgnoreCase("Menu"))
      usl3=" and Tf_use=2 ";
      else if (table.getValueAt(2,1).toString().equalsIgnoreCase("Program"))
      usl3 =" and (Tf_use=1 OR Tf_use=0) ";      
      
        rs=query.select("select * from IISC_TF_APPSYS,IISC_FORM_TYPE where IISC_FORM_TYPE.Tf_id=IISC_TF_APPSYS.Tf_id  and  IISC_TF_APPSYS.AS_id="+ as_id +usl1+usl2+usl3+" order by Tf_mnem");
        while(rs.next())
        {
         int use=rs.getInt("TF_use");
         String us="",con="";
         if (use==2) {us="Menu"; con="-";}
         else if (use==0) {us="Program"; con="YES";}
         else if (use==1) {us="Program"; con="NO";};
         str=str+ "<tr><td nowrap><a href=tf=" + rs.getString("Tf_id") + ">" +rs.getString("Tf_mnem") + "</a></td><td>" +rs.getString("Tf_title")+ "</td><td><div align=center>" + us + "</div></td><td><div align=center>" + con + "</div></td></tr>";
        }         
        query.Close();
      }
      else if (clt_id == 22)
      {
        if (table.getValueAt(0,1).toString()!="")
        usl1=" and Tf_mnem like '"+ table.getValueAt(0,1).toString()+"' ";
        if (table.getValueAt(1,1).toString()!="")
        usl2=" and Tf_title like '"+ table.getValueAt(1,1).toString()+"' ";
        if (table.getValueAt(2,1).toString().equalsIgnoreCase("Menu"))
        usl3=" and Tf_use=2 ";
        else if (table.getValueAt(2,1).toString().equalsIgnoreCase("Program"))
        usl3 =" and (Tf_use=1 OR Tf_use=0) ";
        if (table.getValueAt(3,1).toString()!="")
        usl4=" and Tf_crdate like '"+ table.getValueAt(3,1).toString()+"' ";        
        rs=query.select("select * from IISC_TF_APPSYS, IISC_FORM_TYPE where IISC_FORM_TYPE.Tf_id=IISC_TF_APPSYS.Tf_id  and  IISC_TF_APPSYS.AS_id="+ as_id +usl1+usl2+usl3+usl4+" order by Tf_mnem");
        while(rs.next())
        {       
        int tf_id=rs.getInt("Tf_id");        
        int tfus = rs.getInt("Tf_use");
        String cons="";
        if (tfus==0) {fus="Program"; cons="YES";}
        else if (tfus==1) {fus="Program"; cons="NO";}
        else if (tfus==2) {fus="Menu"; cons="-";}
        rs1=query1.select("select count(*) from IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_COMPONENT_TYPE_OBJECT_TYPE.Tf_id="+ tf_id + "");
        int br_kom=0;
        if (rs1.next()) 
          br_kom=rs1.getInt(1);
        query1.Close();
        int rowspn=(br_kom *5) +6;
        
        str = str + "<tr><td rowspan="+rowspn+" width=14%><div align=center><a href=tf=" + tf_id + ">" +rs.getString("Tf_mnem") + "</a></div></td><td  bgcolor=#CCCCCC width=10% colspan=3><div align=center><strong>Title</strong></div></td><td bgcolor=#CCCCCC width=18%><div align=center><strong>Usage</strong></div></td><td  bgcolor=#CCCCCC width=18%><div align=center><strong>Considered in DB Design</strong></div></td></tr>";
        str = str + "<tr><td colspan=3><div align=center>" + rs.getString("Tf_title")  + "</div></td><td width=18%><div align=center>"+ fus+"</div></td><td width=18%><div align=center>"+cons+"</div></td></tr>";
        str = str + "<tr><td bgcolor=#CCCCCC width=15%><div align=center><strong>Frequency</strong></div></td><td bgcolor=#CCCCCC width=21% colspan=2><div align=center><strong>Response</strong></div></td><td bgcolor=#CCCCCC width=18%><div align=center><strong>Date Created</strong></div></td><td bgcolor=#CCCCCC width=18%><div align=center><strong>Last modified</strong></div></td></tr>";
        str = str + "<tr><td width=15%><div align=center>"+rs.getString("Tf_freq")+" per "+rs.getString("Tf_freq_unit")+"</div></td><td colspan=2><div align=center>"+rs.getString("Tf_rest")+ " per " +rs.getString("Tf_rest_unit")+"</div></td><td width=18%><div align=center>"+rs.getString("Tf_crdate")+"</div></td><td width=18%><div align=center>"+ rs.getString("Tf_moddate")+"</div></td></tr>";
        str = str + "<tr><td bgcolor=#CCCCCC><div align=center><strong>Comment:</strong></div></td><td  colspan=4>"+ rs.getString("Tf_comment")+"</td></tr><tr><td colspan=5 bgcolor=#CCCCCC ><div align=center><strong>Component types</strong></div></td></tr>";
      
      
        rs1=query1.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_COMPONENT_TYPE_OBJECT_TYPE.Tf_id="+ tf_id + " order by tob_superord");
        while(rs1.next())
          {
          int sup = rs1.getInt("Tob_superord");
          rs2=query2.select("select tob_mnem from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tob_id=" +sup+ "");
          if (rs2.next()) 
          superord=rs2.getString(1);
          query2.Close();
          
          //"<br>&nbsp&nbsp Mandatory: " +rs1.getString("Tob_mandatory")+
                   
          
          int tob_id=rs1.getInt("Tob_id");
          
          str = str + "<tr><td width=15% rowspan=5><div align=center>" + rs1.getString("Tob_mnem") +"</div></td><td colspan=2 bgcolor=#CCCCCC><div align=center><strong>Title</strong></div></td><td width=18% bgcolor=#CCCCCC><div align=center  ><strong>Parent</strong></div></td><td width=18% bgcolor=#CCCCCC ><div align=center><strong>Check constraint</strong></div></td></tr>";
          str = str + "<tr><td colspan=2><div align=center>"+ rs1.getString("Tob_name")+"</div></td><td width=18%><div align=center>"+ superord+"</div></td><td width=18%><div align=center>"+rs1.getString("Tob_check")+"</div></td></tr>";
          str = str + "<tr><td width=17% bgcolor=#CCCCCC ><div align=center><strong>Query allowed</strong></div></td><td width=17% bgcolor=#CCCCCC ><div align=center><strong>Insert allowed</strong></div></td><td width=17% bgcolor=#CCCCCC ><div align=center><strong>Update allowed</strong></div></td><td width=17% bgcolor=#CCCCCC ><div align=center><strong>Delete allowed</strong></div></td></tr>";
          str = str + "<tr><td><div align=center>"+rs1.getString("Tob_queallow")+"</div></td><td><div align=center>"+rs1.getString("Tob_insallow")+"</div></td><td width=18%><div align=center>"+rs1.getString("Tob_updallow")+"</div></td><td width=18%><div align=center>"+rs1.getString("Tob_deleteallow")+"</div></td></tr>";
          str = str + "<tr><td bgcolor=#CCCCCC><div align=center><strong>Attributes:</strong></div></td><td colspan=3>";
                  
          rs2=query2.select("select * from IISC_ATT_TOB where IISC_ATT_TOB.Tob_id=" +tob_id+ " order by w_order");
          while(rs2.next())  
            {
            str=str+ rs2.getString(6);
            int att_id = rs2.getInt(1);
            rs3=query3.select("select * from IISC_ATT_KTO where IISC_ATT_KTO.Att_id="+ att_id +" and IISC_ATT_KTO.Tf_id="+tf_id +" and IISC_ATT_KTO.Tob_id=" +tob_id+ "");
            while(rs3.next())
            {
            str=str+ " (KEY "+rs3.getString("Tob_rbrk")+")";
            }
            query3.Close();
            rs3=query3.select("select * from IISC_ATT_UTO where IISC_ATT_UTO.Att_id="+ att_id +" and IISC_ATT_UTO.Tf_id="+tf_id +" and IISC_ATT_UTO.Tob_id=" +tob_id+ "");
            while(rs3.next())
            {
            str=str+ " (UNIQUE "+rs3.getString("Tob_rbrk")+")";
            }
            query3.Close();
            str=str+ ", ";            
            }         
          int len = str.length();
          str=str.substring(0,len-2);
          str = str + "</td></tr>";
          query2.Close();
          }
        query1.Close();
        }
      query.Close();
      }
      else if (clt_id == 23)
      { 
      if (table.getValueAt(0,1).toString()!="")
      usl1=" and RS_name like '"+ table.getValueAt(0,1).toString()+"' ";
      if (table.getValueAt(1,1).toString()!="")
      usl2=" and RS_desc like '"+ table.getValueAt(1,1).toString()+"' ";
      //System.out.println(usl3);
      //System.out.println(usl4);
        rs=query.select("select * from IISC_RELATION_SCHEME where IISC_RELATION_SCHEME.AS_id="+ as_id +usl1+usl2+" order by Rs_name");
        while(rs.next()){
          int rs_id=rs.getInt("RS_id");
          str=str+ "<tr><td nowrap><a href="+ rs_id +">"+rs.getString("RS_name")+ "</a></td><td>" +rs.getString("Rs_desc")+ "</td><td><div align=center>";
          rs1=query1.select("select * from  IISC_RS_KEY where IISC_RS_KEY.RS_id=" +rs_id+ "and RS_primary_key=1 ");
          if (rs1.next())
          {
          String rbk = rs1.getString("RS_rbrk");        
          rs2=query2.select("select * from IISC_RSK_ATT, IISC_ATTRIBUTE where IISC_RSK_ATT.Att_id=IISC_ATTRIBUTE.Att_id and IISC_RSK_ATT.RS_id=" +rs_id+ " and IISC_RSK_ATT.RS_rbrk=" +rbk+ " order by Att_rbrk ");          
          while(rs2.next())  
            {            
            str=str + rs2.getString("Att_mnem") + "+";
            }
          int len = str.length();
          str=str.substring(0,len-1);
          str=str+"</div></td></tr>";
          query2.Close();         
          }        
        query1.Close();         
        }
        query.Close();      
      }
      else if (clt_id == 24)
      { 
      if (table.getValueAt(0,1).toString()!="")
      usl1=" and RS_name like '"+ table.getValueAt(0,1).toString()+"' ";
      if (table.getValueAt(1,1).toString()!="")
      usl2=" and RS_desc like '"+ table.getValueAt(1,1).toString()+"' ";
      //System.out.println(usl3);
      //System.out.println(usl4);
      rs=query.select("select * from IISC_RELATION_SCHEME where IISC_RELATION_SCHEME.As_id="+ as_id +usl1+usl2+" order by Rs_name");
      while(rs.next())
        {
        int rs_id=rs.getInt("RS_id");
        str=str+ "<tr><td><div align=center><a href=" + rs_id + ">" +rs.getString("RS_name") + "</a></div><div>";
        str=str + "<div align=center>("+rs.getString("RS_Desc") + ")</div></td><td>";
        rs1=query1.select("select * from  IISC_RS_ATT, IISC_ATTRIBUTE where IISC_RS_ATT.Att_id=IISC_ATTRIBUTE.Att_id and IISC_RS_ATT.RS_id=" +rs_id+ " order by Att_sequence");
        while(rs1.next())
          {
          int atnl = rs1.getInt("Att_null");
          if (atnl==0 ) str1="    NULL not allowed";
          else if (atnl==1) str1="    NULL allowed";
          int atmod = rs1.getInt("Att_modifiable");
          if (atmod==0 ) str2=", not modifiable"; 
          else if (atmod==1) str2=", modifiable";
          str=str + "<em>" + rs1.getString("Att_mnem") +", </em>" + str1 + str2 + "<br>";
          }
        query1.Close();
        str=str + "</td><td>";
        rs1=query1.select("select * from  IISC_RS_KEY where IISC_RS_KEY.RS_id=" +rs_id+ " order by RS_rbrk");
        while(rs1.next())
          {
          int rscan = rs1.getInt("RS_candidate");
          if (rscan==0 ) str1=""; 
          else if (rscan==1) str1=", Candidate";
          int rspk= rs1.getInt("RS_primary_key");
          if (rspk==0 ) str2=""; 
          else if (rspk==1) str2=", Primary Key";          
          String rbk = rs1.getString("RS_rbrk");                   
          rs2=query2.select("select * from IISC_RSK_ATT, IISC_ATTRIBUTE where IISC_RSK_ATT.Att_id=IISC_ATTRIBUTE.Att_id and IISC_RSK_ATT.RS_id=" +rs_id+ " and IISC_RSK_ATT.RS_rbrk=" +rbk+ " order by Att_rbrk");
          str=str + "<br>"+rbk + ". ";
          while(rs2.next())  
            {
            str=str + rs2.getString("Att_mnem") + "+";
            }
          int len = str.length();
          str=str.substring(0,len-1);
          str= str +str1 + str2;
          query2.Close();
          
          }        
        query1.Close();
        str = str + "</td><td>";
        rs1=query1.select("select distinct RS_rbru from IISC_RS_UNIQUE where IISC_RS_UNIQUE.RS_id=" +rs_id+ " order by RS_rbru");
        while(rs1.next())
          {        
          String rs_rbru=rs1.getString(1);
          str=str + rs_rbru+". ";
          rs2=query2.select("select * from IISC_RS_UNIQUE, IISC_ATTRIBUTE where IISC_RS_UNIQUE.Att_id=IISC_ATTRIBUTE.Att_id and IISC_RS_UNIQUE.RS_rbru=" +rs_rbru+ " and IISC_RS_UNIQUE.RS_id=" +rs_id+ " order by Att_rbru");
          while(rs2.next())  
            {            
            str=str +rs2.getString("Att_mnem")+"+";
            }
          query2.Close();    
          int len = str.length();
          str=str.substring(0,len-1);
          }
        str=str + "</td>";
        query1.Close();
        rs1=query1.select("select * from IISC_RS_ROLE where IISC_RS_ROLE.RS_id=" +rs_id);
        while(rs1.next())
          {   
          int rsrr = rs1.getInt("RSR_read");
          int rsri = rs1.getInt("RSR_insert");
          int rsrm = rs1.getInt("RSR_modify");
          int rsrd = rs1.getInt("RSR_delete");
          if (rsrr==0 ) str1="NO"; 
          else if (rsrr==1) str1="YES";
          str=str + "<td><div align=center>"+ str1 +"</div></td>";
          if (rsri==0 ) str1="NO"; 
          else if (rsri==1) str1="YES";
          str=str + "<td><div align=center>"+ str1+"</div></td>";
          if (rsrm==0 ) str1="NO"; 
          else if (rsrm==1) str1="YES";
          str=str + "<td><div align=center>"+ str1+"</div></td>";
          if (rsrd==0 ) str1="NO"; 
          else if (rsrd==1) str1="YES";
          str=str + "<td><div align=center>"+ str1+"</div></td>";
          }
        query1.Close();        
        }
      query.Close();
      }
      
      else if (clt_id == 31)
      { 
        if (table.getValueAt(0,1).toString()!="")
        usl1=" and Att_mnem like '"+ table.getValueAt(0,1).toString()+"' ";
        if (table.getValueAt(1,1).toString()!="")
        usl2=" and Att_name like '"+ table.getValueAt(1,1).toString()+"' ";
        if (table.getValueAt(2,1).toString().equalsIgnoreCase("yes"))
        usl3=" and Att_sbp=1 ";
        else if (table.getValueAt(2,1).toString().equalsIgnoreCase("no"))
        usl3=" and Att_sbp=0 ";
        if (table.getValueAt(3,1).toString().equalsIgnoreCase("yes"))
        usl4=" and Att_der=1 ";
        else if (table.getValueAt(3,1).toString().equalsIgnoreCase("no"))
        usl4=" and Att_der=0 ";        
        String der="", exs="";
        rs=query.select("select * from IISC_ATTRIBUTE,IISC_DOMAIN where IISC_ATTRIBUTE.Dom_id=IISC_DOMAIN.Dom_id and IISC_ATTRIBUTE.PR_id="+ pr_id +usl1+usl2+usl3+usl4+" order by Att_mnem");      
        while(rs.next())
          {
          if (rs.getInt("Att_der")==0) der="NO";
          else der="YES";
          if (rs.getInt("Att_sbp")==0) exs="NO";
          else exs="YES";
          str=str+ "<tr><td nowrap><a href=att="+ rs.getString("Att_id") +">"+rs.getString("Att_mnem")+ "</a></td><td> "+rs.getString("Att_name")+"</div></td><td><div align=center> "+der+"</td><td><div align=center> "+exs+"</div></td><td ><div align=center><a href=dom=" + rs.getString("Dom_id") + ">" +rs.getString("Dom_mnem") + "</a></div></td></tr>";
          }
        query.Close();        
      }
      else if (clt_id == 32)
      {
      if (table.getValueAt(0,1).toString()!="")
      usl1=" and Att_mnem like '"+ table.getValueAt(0,1).toString()+"' ";
      if (table.getValueAt(1,1).toString()!="")
      usl2=" and Att_name like '"+ table.getValueAt(1,1).toString()+"' ";
      if (table.getValueAt(2,1).toString().equalsIgnoreCase("yes"))
      usl3=" and Att_sbp=1 ";
      else if (table.getValueAt(2,1).toString().equalsIgnoreCase("no"))
      usl3=" and Att_sbp=0 ";
      if (table.getValueAt(3,1).toString().equalsIgnoreCase("yes"))
      usl4=" and Att_der=1 ";
      else if (table.getValueAt(3,1).toString().equalsIgnoreCase("no"))
      usl4=" and Att_der=0 ";      
      rs=query.select("select * from IISC_ATTRIBUTE where IISC_ATTRIBUTE.PR_id="+ pr_id +usl1+usl2+usl3+usl4+" order by Att_mnem");      
      while(rs.next())
        {                         
          int atsbp = rs.getInt("Att_sbp");
          if (atsbp==0) str1="NO";
          else if (atsbp==1) str1="YES";
          int att_elem=rs.getInt("Att_elem");
          if (att_elem==-1) str2="YES";
          else
          {
            rs1=query1.select("select Att_name from IISC_ATTRIBUTE where Att_id=" +att_elem+ "");      
            if(rs1.next())                       
              str2="NO, renamed from attribute <b>" +rs1.getString(1)+ "</b>";            
            query1.Close(); 
          }
          int fun_id=rs.getInt("Fun_id");
          rs1=query1.select("select Fun_name from IISC_FUNCTION where Fun_id=" +fun_id + "");      
          if(rs1.next())                       
            fun=rs1.getString(1);            
          query1.Close(); 
          int ader = rs.getInt("Att_der");
          if (ader==0) str3="NO";
          else if (ader==1) str3="YES, <b>Function: </b>" +fun ;  
          int dom_id=rs.getInt("Dom_id");
          rs1=query1.select("select Dom_mnem from IISC_DOMAIN where Dom_id=" +dom_id+ "");      
          if(rs1.next())                       
            str4=rs1.getString(1);
          query1.Close(); 
          str = str + "<tr> <td rowspan=4 width=20%><div align=center><a href=att=" + rs.getString("Att_id") + ">" +rs.getString("Att_mnem") + "</a></div><div align=center>("+ rs.getString("Att_name")+")</div></td>";
          str = str + "<td width=15% bgcolor=#CCCCCC><div align=center><strong>Exists in DB schema</strong></div></td><td width=25% bgcolor=#CCCCCC><div align=center><strong>Elementary attribute</strong></div></td><td width=25% bgcolor=#CCCCCC><div align=center><strong>Derived</strong></div></td><td width=15% bgcolor=#CCCCCC><div align=center><strong>Domain </strong></div></td></tr>";
          str = str + "<tr><td><div align=center>"+ str1 +"</div></td><td><div align=center>"+ str2 + "</div></td><td><div align=center>" + str3 + "</div></td><td><div align=center>"+str4+"</div></td></tr><tr><td bgcolor=#CCCCCC><div align=center><strong>Default value</strong></div></td><td bgcolor=#CCCCCC><div align=center><strong>Expression</strong></div></td><td colspan=2 bgcolor=#CCCCCC><div align=center><strong>Comment</strong></div></td></tr>";
          str = str + "<tr><td><div align=center>"+ rs.getString("Att_default")+"</div></td><td><div align=center>"+ rs.getString("Att_expr")+ "</div></td><td colspan=2><div align=center>"+ rs.getString("Att_comment")+"</div></td></tr>";
         }  
      query.Close();
      }
      else if (clt_id == 33)
      { 
      if (table.getValueAt(0,1).toString()!="")
      usl1=" and PT_mnemonic like '"+ table.getValueAt(0,1).toString()+"' ";
      if (table.getValueAt(0,1).toString()!="")
      usl1a=" and Dom_mnem like '"+ table.getValueAt(0,1).toString()+"' ";
      if (table.getValueAt(1,1).toString()!="")
      usl2=" and PT_name like '"+ table.getValueAt(1,1).toString()+"' ";
      if (table.getValueAt(1,1).toString()!="")
      usl2a=" and Dom_name like '"+ table.getValueAt(1,1).toString()+"' ";
      if (table.getValueAt(2,1).toString().equalsIgnoreCase("primitive"))      
      {
        rs=query.select("select * from IISC_PRIMITIVE_TYPE where 1=1 "+usl1+usl2+" order by Pt_mnemonic");      
        while(rs.next())
          str=str+ "<tr><td nowrap><a href=pt="+ rs.getString("Pt_id") +">"+rs.getString("Pt_mnemonic")+ "</a></td><td> "+rs.getString("PT_name")+" </td><td><div align=center> Primitive </div></td><td ><div align=center> "+rs.getString("PT_def_val")+" </div></td></tr>";
        query.Close();     }
      else if (table.getValueAt(2,1).toString().equalsIgnoreCase("user defined")) {
        rs=query.select("select Dom_id,Dom_mnem,Dom_name,Dom_default from IISC_DOMAIN where IISC_DOMAIN.PR_id="+ pr_id +usl1a+usl2a+" order by Dom_mnem");      
        while(rs.next())
          str=str+ "<tr><td nowrap><a href=dom="+ rs.getString("Dom_id") +">"+rs.getString("Dom_mnem")+ "</a></td><td> "+rs.getString("Dom_name")+" </td><td><div align=center> User defined </div></td><td ><div align=center> "+rs.getString("Dom_default")+" </div></td></tr>";
        query.Close();             }
      else if (table.getValueAt(2,1).toString()=="") {
      rs=query.select("select * from IISC_PRIMITIVE_TYPE where 1=1 "+usl1+usl2+" order by Pt_mnemonic");      
        while(rs.next())
          str=str+ "<tr><td nowrap><a href=pt="+ rs.getString("Pt_id") +">"+rs.getString("Pt_mnemonic")+ "</a></td><td> "+rs.getString("PT_name")+" </td><td><div align=center> Primitive </div></td><td ><div align=center> "+rs.getString("PT_def_val")+" </div></td></tr>";
        query.Close();      
        rs=query.select("select Dom_id,Dom_mnem,Dom_name,Dom_default from IISC_DOMAIN where IISC_DOMAIN.PR_id="+ pr_id +usl1a+usl2a+" order by Dom_mnem");      
        while(rs.next())
          str=str+ "<tr><td nowrap><a href=dom="+ rs.getString("Dom_id") +">"+rs.getString("Dom_mnem")+ "</a></td><td> "+rs.getString("Dom_name")+" </td><td><div align=center> User defined </div></td><td ><div align=center> "+rs.getString("Dom_default")+" </div></td></tr>";
        query.Close();      
      }
      }
      else if (clt_id == 34)
      {            
      if (table.getValueAt(0,1).toString()!="")
      usl1=" and PT_mnemonic like '"+ table.getValueAt(0,1).toString()+"' ";
      if (table.getValueAt(0,1).toString()!="")
      usl1a=" and Dom_mnem like '"+ table.getValueAt(0,1).toString()+"' ";
      if (table.getValueAt(1,1).toString()!="")
      usl2=" and PT_name like '"+ table.getValueAt(1,1).toString()+"' ";
      if (table.getValueAt(1,1).toString()!="")
      usl2a=" and Dom_name like '"+ table.getValueAt(1,1).toString()+"' ";            
      if (table.getValueAt(2,1).toString().equalsIgnoreCase("primitive"))      
      {      
      rs=query.select("select * from IISC_PRIMITIVE_TYPE where 1=1 "+usl1+usl2+" order by Pt_mnemonic");      
      while(rs.next())
      {      
        String dv = rs.getString("Pt_def_val");
        if (dv==null) dv=" /";        
        int lr = rs.getInt("PT_length_required");
        String lenreq="";
        String dl = rs.getString("PT_length");
        if (lr==0) lenreq="YES";
        else if (lr==1) lenreq="NO";
        else if (lr==2) lenreq="OPTIONAL, with default lenght "+dl;               
        String dd =rs.getString("PT_decimal");                
        str = str + "<tr><td rowspan=4 width=20%><div align=center><a href=pt=" + rs.getString("Pt_id") + ">" +rs.getString("Pt_mnemonic") + "</a></div></td><td  bgcolor=#CCCCCC width=20%><div align=center><strong>Primitive / User defined</strong></div></td><td bgcolor=#CCCCCC width=40%><div align=center><strong>Description</strong></div></td><td  bgcolor=#CCCCCC width=20%><div align=center><strong>Default value</strong></div></td></tr>";
        str = str + "<tr><td><div align=center>Primitive</div></td><td><div align=center>"+rs.getString("Pt_name")+"</div></td><td><div align=center>"+dv+"</div></td></tr><tr><td bgcolor=#CCCCCC colspan=2><div align=center><strong>Length required</strong></div></td><td bgcolor=#CCCCCC><div align=center><strong>Decimal places</strong></div></td></tr><tr><td colspan=2><div align=center>"+lenreq+"</div></td><td><div align=center>"+dd+"</div></td></tr>";
      }
      query.Close();}
      else if (table.getValueAt(2,1).toString().equalsIgnoreCase("user defined")) {      
      rs=query.select("select * from IISC_DOMAIN where IISC_DOMAIN.PR_id="+ pr_id+usl1a+usl2a+" order by Dom_mnem");      
      while(rs.next())
        {
        int dt = rs.getInt("Dom_type");
        int dom_id=rs.getInt("Dom_id");
        if (dt==1) 
          {
          int par_id=rs.getInt("Dom_parent");
          rs1=query1.select("select Dom_mnem from IISC_DOMAIN where Dom_id=" +par_id+ "");      
          if(rs1.next())                       
             str2=rs1.getString(1);            
          query1.Close();
          str1="Inherited from user defined domain <b>" +str2+ "</b>";
          } 
        else if (dt==2) 
        {
        str1 = "Complex domain (tuple). Tuple attributes: ";
        rs1=query1.select("select Att_mnem from IISC_ATTRIBUTE,IISC_DOM_ATT where IISC_ATTRIBUTE.Att_id=IISC_DOM_ATT.Att_id and IISC_DOM_ATT.Dom_id=" +dom_id+ "");      
        while (rs1.next()){                       
          str2=rs1.getString(1);            
          str1= str1 + str2+", ";
          }       
        str1=str1.substring(0,str1.length()-2);
        query1.Close();        
        }
        else if (dt==3) 
        {
        str1 = "Complex domain (choice). Choice attributes: ";
        rs1=query1.select("select Att_mnem from IISC_ATTRIBUTE,IISC_DOM_ATT where IISC_ATTRIBUTE.Att_id=IISC_DOM_ATT.Att_id and IISC_DOM_ATT.Dom_id=" +dom_id+ "");      
        while (rs1.next()){                       
          str2=rs1.getString(1);            
          str1= str1 + str2+", ";
          }       
        str1=str1.substring(0,str1.length()-2);
        query1.Close();
        }
        else if (dt==4) 
        {
        String par_id=rs.getString("Dom_parent");
        if (par_id!=null) 
          {
          rs1=query1.select("select Dom_mnem from IISC_DOMAIN where Dom_id=" +par_id+ "");      
          if(rs1.next())                       
             str2=rs1.getString(1);            
          query1.Close();
          str1="Complex domain (set), set member domain:  <b>" +str2+ "</b>";
          }
        else 
          {
          int pt_id=rs.getInt("Dom_data_type");
          rs1=query1.select("select PT_mnemonic from IISC_PRIMITIVE_TYPE where PT_id=" +pt_id+ "");
          if(rs1.next())                       
             str2=rs1.getString(1);            
          query1.Close();
          str1="Complex domain (set), set member domain:  <b>" +str2+ "</b>";
          }
        
        }
        else  
          {
          int pt_id=rs.getInt("Dom_data_type");
          rs1=query1.select("select PT_mnemonic from IISC_PRIMITIVE_TYPE where PT_id=" +pt_id+ "");      
          if(rs1.next())                       
             str2=rs1.getString(1);            
          query1.Close();                 
          str1="Inherited from primitive type <b>" +str2+ "</b>";
          String dl = rs.getString("Dom_length");
          if (dl!=null) str1=str1 +", Length: "+dl;
          String dd =rs.getString("Dom_decimal");   
          if (dd!=null) str1=str1 +", Decimal places: "+dd;
          }
          String dv = rs.getString("Dom_default");
          if (dv==null) dv=" /";              
          str= str + "<tr><td rowspan=4 width=20%><p><div align=center><a href=dom=" + dom_id + ">" +rs.getString("Dom_mnem") + "</a></div><div align=center>" + rs.getString("Dom_name") + "</div></td><td  bgcolor=#CCCCCC width=20%><div align=center><strong>Primitive / User defined</strong></div></td><td bgcolor=#CCCCCC width=40%><div align=center><strong>Expression</strong></div></td><td  bgcolor=#CCCCCC width=20%><div align=center><strong>Default value</strong></div></td></tr>";
          str= str + "<tr><td><div align=center>User defined</div></td><td><div align=center>"+ rs.getString("Dom_reg_exp_str") +"</div></td><td><div align=center>"+ dv +"</div></td></tr><tr><td bgcolor=#CCCCCC colspan=2><div align=center><strong>Domain type</strong></div></td><td bgcolor=#CCCCCC><div align=center><strong>Comment</strong></div></td></tr><tr><td colspan=2><div align=center>"+str1+"</div></td><td><div align=center>"+ rs.getString("Dom_comment")+ "</div></td></tr>";
        }
      query.Close();
      }
      else if (table.getValueAt(2,1).toString()=="") {       
      rs=query.select("select * from IISC_PRIMITIVE_TYPE where 1=1 "+usl1+usl2+" order by Pt_mnemonic");      
      while(rs.next())
      {      
        String dv = rs.getString("Pt_def_val");
        if (dv==null) dv=" /";        
        int lr = rs.getInt("PT_length_required");
        String lenreq="";
        String dl = rs.getString("PT_length");
        if (lr==0) lenreq="YES";
        else if (lr==1) lenreq="NO";
        else if (lr==2) lenreq="OPTIONAL, with default lenght "+dl;               
        String dd =rs.getString("PT_decimal");                
        str = str + "<tr><td rowspan=4 width=20%><div align=center><a href=pt=" + rs.getString("Pt_id") + ">" +rs.getString("Pt_mnemonic") + "</a></div></td><td  bgcolor=#CCCCCC width=20%><div align=center><strong>Primitive / User defined</strong></div></td><td bgcolor=#CCCCCC width=40%><div align=center><strong>Description</strong></div></td><td  bgcolor=#CCCCCC width=20%><div align=center><strong>Default value</strong></div></td></tr>";
        str = str + "<tr><td><div align=center>Primitive</div></td><td><div align=center>"+rs.getString("Pt_name")+"</div></td><td><div align=center>"+dv+"</div></td></tr><tr><td bgcolor=#CCCCCC colspan=2><div align=center><strong>Length required</strong></div></td><td bgcolor=#CCCCCC><div align=center><strong>Decimal places</strong></div></td></tr><tr><td colspan=2><div align=center>"+lenreq+"</div></td><td><div align=center>"+dd+"</div></td></tr>";
      }
      query.Close();      
      rs=query.select("select * from IISC_DOMAIN where IISC_DOMAIN.PR_id="+ pr_id+usl1a+usl2a+" order by Dom_mnem");      
      while(rs.next())
        {
        int dt = rs.getInt("Dom_type");
        int dom_id=rs.getInt("Dom_id");
        if (dt==1) 
          {
          int par_id=rs.getInt("Dom_parent");
          rs1=query1.select("select Dom_mnem from IISC_DOMAIN where Dom_id=" +par_id+ "");      
          if(rs1.next())                       
             str2=rs1.getString(1);            
          query1.Close();
          str1="Inherited from user defined domain <b>" +str2+ "</b>";
          } 
        else if (dt==2) 
        {
        str1 = "Complex domain (tuple). Tuple attributes: ";
        rs1=query1.select("select Att_mnem from IISC_ATTRIBUTE,IISC_DOM_ATT where IISC_ATTRIBUTE.Att_id=IISC_DOM_ATT.Att_id and IISC_DOM_ATT.Dom_id=" +dom_id+ "");      
        while (rs1.next()){                       
          str2=rs1.getString(1);            
          str1= str1 + str2+", ";
          }       
        str1=str1.substring(0,str1.length()-2);
        query1.Close();        
        }
        else if (dt==3) 
        {
        str1 = "Complex domain (choice). Choice attributes: ";
        rs1=query1.select("select Att_mnem from IISC_ATTRIBUTE,IISC_DOM_ATT where IISC_ATTRIBUTE.Att_id=IISC_DOM_ATT.Att_id and IISC_DOM_ATT.Dom_id=" +dom_id+ "");      
        while (rs1.next()){                       
          str2=rs1.getString(1);            
          str1= str1 + str2+", ";
          }       
        str1=str1.substring(0,str1.length()-2);
        query1.Close();
        }
        else if (dt==4) 
        {
        String par_id=rs.getString("Dom_parent");
        if (par_id!=null) 
          {
          rs1=query1.select("select Dom_mnem from IISC_DOMAIN where Dom_id=" +par_id+ "");      
          if(rs1.next())                       
             str2=rs1.getString(1);            
          query1.Close();
          str1="Complex domain (set), set member domain:  <b>" +str2+ "</b>";
          }
        else 
          {
          int pt_id=rs.getInt("Dom_data_type");
          rs1=query1.select("select PT_mnemonic from IISC_PRIMITIVE_TYPE where PT_id=" +pt_id+ "");
          if(rs1.next())                       
             str2=rs1.getString(1);            
          query1.Close();
          str1="Complex domain (set), set member domain:  <b>" +str2+ "</b>";
          }
        
        }
        else  
          {
          int pt_id=rs.getInt("Dom_data_type");
          rs1=query1.select("select PT_mnemonic from IISC_PRIMITIVE_TYPE where PT_id=" +pt_id+ "");      
          if(rs1.next())                       
             str2=rs1.getString(1);            
          query1.Close();                 
          str1="Inherited from primitive type <b>" +str2+ "</b>";
          String dl = rs.getString("Dom_length");
          if (dl!=null) str1=str1 +", Length: "+dl;
          String dd =rs.getString("Dom_decimal");   
          if (dd!=null) str1=str1 +", Decimal places: "+dd;
          }
          String dv = rs.getString("Dom_default");
          if (dv==null) dv=" /";              
          str= str + "<tr><td rowspan=4 width=20%><p><div align=center><a href=dom=" + dom_id + ">" +rs.getString("Dom_mnem") + "</a></div><div align=center>" + rs.getString("Dom_name") + "</div></td><td  bgcolor=#CCCCCC width=20%><div align=center><strong>Primitive / User defined</strong></div></td><td bgcolor=#CCCCCC width=40%><div align=center><strong>Expression</strong></div></td><td  bgcolor=#CCCCCC width=20%><div align=center><strong>Default value</strong></div></td></tr>";
          str= str + "<tr><td><div align=center>User defined</div></td><td><div align=center>"+ rs.getString("Dom_reg_exp_str") +"</div></td><td><div align=center>"+ dv +"</div></td></tr><tr><td bgcolor=#CCCCCC colspan=2><div align=center><strong>Domain type</strong></div></td><td bgcolor=#CCCCCC><div align=center><strong>Comment</strong></div></td></tr><tr><td colspan=2><div align=center>"+str1+"</div></td><td><div align=center>"+ rs.getString("Dom_comment")+ "</div></td></tr>";
        }
      query.Close();    }  
      }
      else if (clt_id == 40)
      {  
      
        if (table.getValueAt(0,1).toString()!="")
        usl1=" and AS_name like '"+ table.getValueAt(0,1).toString()+"'";
        if (table.getValueAt(1,1).toString()!="")
        usl2=" and AS_desc like '"+ table.getValueAt(1,1).toString()+"'";
        if (table.getValueAt(2,1).toString()!="")
        usl3=" and AS_date like '"+ table.getValueAt(2,1).toString()+"'";        
        rs=query.select("select * from IISC_APP_SYSTEM  where  IISC_APP_SYSTEM.PR_id="+ pr_id +usl1+usl2+usl3+" order by AS_name");     
        while(rs.next())
          {
          int as=rs.getInt("AS_id");
          str=str+ "<tr><td nowrap><a href=as="+ as +">"+rs.getString("AS_name")+ "</a></td><td> "+rs.getString("AS_desc")+"</td><td> "+rs.getString("AS_date")+"</td>";
          rs1=query1.select("select IISC_APP_SYSTEM.AS_id,AS_name from IISC_APP_SYSTEM, IISC_APP_SYSTEM_CONTAIN where IISC_APP_SYSTEM.AS_id=IISC_APP_SYSTEM_CONTAIN.AS_id and AS_id_con=" + as );
          if (rs1.next()) 
          str = str + "<td><div align=center><a href=as="+ rs1.getString(1) +">"+rs1.getString(2)+ "</a></div></td></tr>";
          else str = str + "<td><div align=center> - </div></td></tr>";
          query1.Close();
          }
        query.Close();         
      }
            
      rs=query.select("select max(CL_id)+1  from IISC_COLLISION_LOG");        
      if(rs.next())
      rep_id=rs.getInt(1);
      query.Close();        
      query.update("insert into IISC_COLLISION_LOG (CL_id,PR_id,AS_id,CL_type,CL_date,CL_text,CL_warning,CL_obsolete) values ("+ rep_id +","+ pr_id +","+ as_id +","+clt_id+",'" + ODBCList.now() + "','" + str + "',0,0" +")");        
      query.update("update IISC_COLLISION_LOG set CL_obsolete=1 where CL_type="+ clt_id +" AND PR_id="+ pr_id + " AND AS_id="+ as_id + " AND not(CL_id=" + rep_id + ")");
      
      String ldate="";
      rs=query.select("select max(CL_date) from IISC_COLLISION_LOG   where IISC_COLLISION_LOG.CL_type=" + clt_id + " and AS_id=" + as_id + " and PR_id="+pr_id);
      if(rs.next())
      ldate=rs.getString(1);
      query.Close();
      String ime="";
      rs=query.select("select CLT_name from IISC_LOG_TYPE   where CLT_id=" + clt_id);
      if(rs.next())
      ime=rs.getString(1);
      query.Close();      
      if (clt_id>20 && clt_id<31) 
      tree.insert(ldate,ime,"Repository reports","Reports");
      else if (clt_id>=31)
      tree.insert(ldate,ime);
      
      
    
    } //end try...
    catch(SQLException e1)
    {
      JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
           
  dispose();
  } 

  private void close1_ActionPerformed(ActionEvent e)
  {
  dispose();
  }

  private void btnHelp_actionPerformed(ActionEvent e)
  {Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void jScrollPane1_mouseReleased(MouseEvent e)
  {

  }
}