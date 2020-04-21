package ui;
import iisc.FunDep;
import iisc.Help;
import iisc.IISFrameMain;
import iisc.JDBCQuery;
import iisc.ODBCList;
import iisc.PTree;
import iisc.Synthesys;
import iisc.RelationScheme;
import iisc.PTree;

import iisc.Report;
import iisc.Settings;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.IOException;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ViewApplication extends JDialog 
{
    private int id;
    private Connection conn;
    private JButton btnClose = new JButton();
    private JButton btnSave1 = new JButton();
    private JCheckBox chReport = new JCheckBox();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private JCheckBox chDBSchema = new JCheckBox();
    private JLabel lbApp = new JLabel();
    private JLabel lbDBSchema = new JLabel();
    private JCheckBox chApp = new JCheckBox();
    private JLabel txtApp = new JLabel();
    private JLabel lbReport = new JLabel();
    private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private ImageIcon imageOK = new ImageIcon(IISFrameMain.class.getResource("icons/ok.gif"));
    private JButton btnHelp = new JButton();
    private JCheckBox chRemoveDBSchema = new JCheckBox();
    private Set Pu=new HashSet();
    private Set Pa=new HashSet();
    private int ind;
    private int template;
    private int bussines_application;
    private String url;
    private String username;
    private String password;
    private int pr;
    private SubSchemaGen[] schemas;
    private PTree tree;
    
    public ViewApplication()
  {
    this(null, "", false,null,0,0,0,0,null,null,null,null);
  }

  public ViewApplication(IISFrameMain parent, String title, boolean modal, Connection _conn,int _pr, int _id, int _template, int _bussines_application, String _url,String _username,String _password,PTree _tree)
  {
   
    super(parent, title, modal);
    tree=_tree;
    conn=_conn;
    template=_template;
    bussines_application=_bussines_application;
    url=_url;
    pr=_pr;
    id=_id;
    username=_username;
    password=_password;
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
  {this.setTitle("Application Generation");
  
    this.setSize(new Dimension(321, 206));
    this.getContentPane().setLayout(null);
    
    this.setFont(new Font("SansSerif", 0, 11));
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Close");
    btnClose.setBounds(new Rectangle(185, 135, 80, 30));
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          close_ActionPerformed(ae);
        }
      });
    btnClose.setFont(new Font("SansSerif", 0, 11));
    btnSave1.setMaximumSize(new Dimension(50, 30));
    btnSave1.setPreferredSize(new Dimension(50, 30));
    btnSave1.setText("OK");
    btnSave1.setBounds(new Rectangle(100, 135, 80, 30));
    btnSave1.setMinimumSize(new Dimension(50, 30));
    btnSave1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save_ActionPerformed(ae);
        }
      });
    btnSave1.setFont(new Font("SansSerif", 0, 11));


        chReport.setBounds(new Rectangle(245, 35, 20, 20));
        chReport.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        chReport_mouseClicked(e);
                    }
                });
        jLabel4.setText("Report");
        jLabel4.setBounds(new Rectangle(235, 10, 110, 15));
        jLabel4.setFont(new Font("Tahoma", 1, 11));
        jLabel1.setText("Complete");
        jLabel1.setBounds(new Rectangle(20, 10, 85, 15));
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        chDBSchema.setText("Generating Form Type SubSchema");
        chDBSchema.setBounds(new Rectangle(20, 35, 195, 20));
        chDBSchema.setFont(new Font("SansSerif", 0, 11));
        chDBSchema.setSelected(true);
        chDBSchema.setActionCommand("Generating DB Schema");
        chDBSchema.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        chDBSchema_mouseClicked(e);
                    }
                });
        chDBSchema.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chDBSchema_actionPerformed(e);
        }
      });
        lbApp.setText("Generating Closure Graph");
        lbApp.setBounds(new Rectangle(20, 60, 165, 15));
        lbApp.setFont(new Font("SansSerif", 0, 11));
        lbDBSchema.setText("Generating Form Type SubSchema");
        lbDBSchema.setBounds(new Rectangle(20, 38, 190, 15));
        lbDBSchema.setFont(new Font("SansSerif", 0, 11));
        lbDBSchema.setIcon(imageOK);
        chApp.setText("Generating Application");
        chApp.setBounds(new Rectangle(20, 55, 170, 25));
        chApp.setFont(new Font("SansSerif", 0, 11));
        chApp.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        chApp_mouseClicked(e);
                    }
                });
        chApp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chApp_actionPerformed(e);
        }
      });
        txtApp.setFont(new Font("SansSerif", 1, 11));
        txtApp.setBounds(new Rectangle(45, 5, 90, 25));
        lbReport.setBounds(new Rectangle(225, 35, 90, 20));
        lbReport.setForeground(Color.blue);
        lbReport.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        lbReport_mouseClicked(e);
                    }
                });
        lbApp.setText("Generating Application");
        lbApp.setBounds(new Rectangle(20, 60, 165, 15));
        lbApp.setFont(new Font("SansSerif", 0, 11));
        lbApp.setIcon(imageOK);
        btnHelp.setBounds(new Rectangle(270, 135, 35, 30));
        btnHelp.setFont(new Font("SansSerif", 0, 11));
        btnHelp.setIcon(imageHelp);
        btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
        chRemoveDBSchema.setText("Remove all");
        chRemoveDBSchema.setBounds(new Rectangle(20, 95, 155, 20));
        chRemoveDBSchema.setFont(new Font("SansSerif", 0, 11));
        chRemoveDBSchema.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chRelationDBSchema_actionPerformed(e);
        }
      });
        this.getContentPane().add(chRemoveDBSchema, null);
        this.getContentPane().add(btnHelp, null);
        this.getContentPane().add(lbApp, null);
        this.getContentPane().add(lbReport, null);
        this.getContentPane().add(txtApp, null);
        this.getContentPane().add(chApp, null);
        this.getContentPane().add(lbDBSchema, null);
        this.getContentPane().add(lbApp, null);
        this.getContentPane().add(chDBSchema, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(jLabel4, null);
        this.getContentPane().add(chReport, null);
        this.getContentPane().add(btnSave1, null);
        this.getContentPane().add(btnClose, null);
        setCheckboxes();
    }



  private void close_ActionPerformed(ActionEvent e)
  {dispose();
  }
    private void lb1_mouseClicked(MouseEvent e)
    {

    }
    private void openReport()
    {
    try
    { 
    ResultSet rs ;
      JDBCQuery query=new JDBCQuery(conn);
    
    int i=-1;
    rs=query.select("select * from IISC_COLLISION_LOG  where   AS_id=" + id + "  and PR_id="+pr+ " and CL_type=50 order by CL_id desc");
    if(rs.next())
    i=rs.getInt(1);
    Object report=getParent();
    Report rep=new Report((IISFrameMain)getParent(),"Report - Application Generation",true,conn,i);
    Settings.Center(rep);
    rep.setVisible(true);
    query.Close(); 
     }
    catch(SQLException ae)
      {
        ae.printStackTrace();
      }   
    }
  private void save_ActionPerformed(ActionEvent e)
  {
      JDBCQuery query=new JDBCQuery(conn);
      ResultSet rs;
      String s="";
      try {
          Connection connection = (Connection)DriverManager.getConnection(url, username, password);
          Template temp=new Template(conn,template);
          Application app =new Application(conn,id, bussines_application, connection);
          if(ind==0 && chDBSchema.isSelected())
          {
            schemas= app.generateSubSchemes(pr);
            ind=1;
            if(chReport.isSelected()){
                rs=query.select("select max(CL_id)+1  from IISC_COLLISION_LOG");
                int idl=0;
                if(rs.next())
                idl=rs.getInt(1);
                query.Close();
                String text="", desc="";
                for(int i=0;i<schemas.length;i++) {
                    desc="";
                    Set rss=new HashSet();
                    Set rssPu=new HashSet();
                    Set rssPa=new HashSet();
                    String applicable,pa="",pu="";
                    Iterator it=schemas[i].Pa.iterator();
                    while(it.hasNext()){
                        RelationScheme R=(RelationScheme)it.next();
                        rssPa.add(""+R.id);
                        /*pa=pa+""+R.name+"<br>";
                        if(!rss.contains(""+R.name))
                        {
                            desc=desc+getRelDesc(R)+"<br>";
                            rss.add(""+R.name);
                        }*/
                    }
                    it=schemas[i].Pu.iterator();
                    while(it.hasNext()){
                        RelationScheme R=(RelationScheme)it.next();
                        rssPu.add(""+R.id);
                   //     pu=pu+""+R.name+"<br>";
                        if(!rss.contains(""+R.name))
                        {
                            desc=desc+""+getRelDesc(R, rssPu, rssPa);
                            rss.add(""+R.name);
                        }
                    }
                    if(schemas[i].apply_id==0)
                        applicable="inapplicable";
                    else if(schemas[i].apply_id==3)
                        applicable="updatable";
                    else
                        applicable="read only"; 
                    String descComp="";
                    for(int k=0;k<schemas[i].tf.compType.length;k++){
                        String operations="";
                        Application.CompType c=schemas[i].tf.compType[k];
                        if(c.que_allowed==1)operations=operations+"query<br>";
                        if(c.ins_allowed==1)operations=operations+"insert<br>";
                        if(c.upd_allowed==1)operations=operations+"update<br>";
                        if(c.del_allowed==1)operations=operations+"delete<br>";
                        String atts="";
                        for(int l=0;l<c.itemGroup.length;l++){
                            for(int m=0;m<c.itemGroup[l].items.length;m++){
                                Application.Item itt=c.itemGroup[l].items[m];
                                if(itt.elem.getClass().toString().equals("class ui.Application$AttType"))
                                    atts=atts+((Application.AttType)itt.elem).att.mnem+", "; 
                                else{
                                    Application.ItemGroup igi=(Application.ItemGroup)itt.elem;
                                    for(int n=0;n<igi.items.length; n++)
                                        atts=atts+((Application.AttType)igi.items[n].elem).att.mnem+", ";
                                }
                            }
                        }
                        String keys="", uniques="";
                        for(int j=0;j<c.keys.length;j++){
                            keys=keys+"[";
                            Iterator itt=c.keys[j].iterator();
                            while(itt.hasNext()){
                                Application.AttType key=(Application.AttType)itt.next();
                                keys=keys+key.att.mnem+", ";
                            }
                            keys=keys.substring(0,keys.length()-2)+"], ";
                        }
                        for(int j=0;j<c.uniques.length;j++){
                            uniques=uniques+"[";
                            Iterator itt=c.uniques[j].iterator();
                            while(itt.hasNext()){
                                Application.AttType key=(Application.AttType)itt.next();
                                uniques=uniques+"<u>"+key.att.mnem+"</u>, ";
                            }
                            uniques=uniques.substring(0,uniques.length()-2)+"], ";
                        }
                        if(!keys.equals(""))keys=", {"+keys.substring(0,keys.length()-2)+"}";
                        if(!uniques.equals(""))uniques=", {"+uniques.substring(0,uniques.length()-2)+"}";
                        descComp=descComp+"<tr><td><b>"+schemas[i].tf.compType[k].mnem+"</b><br>{["+atts.substring(0,atts.length()-2)+"]"+keys+""+uniques+"}</td><td>"+operations.substring(0,operations.length()-1)+"</td></tr>";
                    }
                    text=text+"<tr><td bgcolor=\"#CCCCCC\" width=\"50%\"><a href=tf="+schemas[i].tf.id +"><b>"+ schemas[i].tf.name +"</b></a></td><td width=\"25%\" bgcolor=\"#CCCCCC\"><b>"+applicable+" CODE: Z0"+schemas[i].apply_id+"</b></td></tr><tr><td valign=\"top\"><table width=\"100%\"><tr><td colspan=\"2\" bgcolor=\"#CCCCCC\"><b>Component Types</b></td></tr>"+ descComp +"</table></td><td valign=\"top\"><table width=\"100%\"><tr><td colspan=\"2\" bgcolor=\"#CCCCCC\"><b>Subschema</b></td></tr>"+ desc +"</table></td></tr>";
                    //text=text+"<tr><td  rowspan=\""+(2*rss.size()+1)+"\"><a href=tf="+schemas[i].tf.id +">"+ schemas[i].tf.name +"</a></td><td rowspan=\""+(2*rss.size()+1)+"\">"+ applicable +" (Z0"+schemas[i].apply_id+")</td>"+desc+"</tr>";
                    //text=text+"<tr><td><a href=tf="+schemas[i].tf.id +">"+ schemas[i].tf.name +"</a></td><td>"+ applicable +" (Z0"+schemas[i].apply_id+")</td><td>"+pu+"</td><td>"+pa+"</td></tr>";
                    //text=text+"<tr><td colspan=4>"+desc+"</td></tr>";
                }
                query.update("update IISC_COLLISION_LOG set CL_obsolete=1 where CL_type=50 and AS_id="+id);
                query.update("insert into IISC_COLLISION_LOG(CL_id,PR_id,AS_id,CL_type,CL_date,CL_text,CL_warning,CL_obsolete) values ("+ idl +","+ pr  +","+ id +",50,'" + ODBCList.now() + "','" + text + "',0,0)");
            }
          }
          if(ind==1 && chApp.isSelected())
          {
            Runtime.getRuntime().gc();
            Generator gen=new Generator(conn,schemas,url,username,password);
            gen.createUIMLSpecification(temp, app);
            gen.renderUIMLSpecification();
            ind=2;
          }
          if(ind>0 && chRemoveDBSchema.isSelected())
          {
            ind=0;
            query.update("update IISC_COLLISION_LOG set CL_obsolete=1 where CL_type=50 and AS_id="+id);
            chApp.setSelected(false);
            chDBSchema.setSelected(false);
            chReport.setSelected(false);
            chRemoveDBSchema.setSelected(false);
          }
      }
      catch (SQLException ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace ();
      }
      catch (IOException ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace ();
      }
    setCheckboxes();
    tree.setVisible(false);
    tree.pravi_drvo();
    tree.setVisible(true);
  }
  public String getRelDesc(RelationScheme scheme, Set Pu, Set Pa){
      String text="", applicable="";
      //text="<b>"+scheme.name+"</b> {"+getAttsDesc(scheme.atributi)+", "+getKeysDesc(scheme.kljuc)+", "+getKey(scheme.primarni_kljuc)+"}";
      if(Pu.contains(""+scheme.id))applicable="read only";
      if(Pa.contains(""+scheme.id))applicable="updatable";
      text="<tr><td><b>"+scheme.name+"</b></td><td>"+applicable+"</td></tr><tr><td colspan=2>{"+getAttsDesc(scheme.atributi)+", "+getKeysDesc(scheme.kljuc)+", "+getKey(scheme.primarni_kljuc)+"}</td></tr>";
      return text;
  }
    public String getAttsDesc(Set attributes){
       Iterator it= attributes.iterator();
       String ret="";
       while(it.hasNext()){
           int att=(new Integer(it.next().toString())).intValue();
           ret=ret+getAttDesc(att)+", ";
       }
       return ("["+ret.substring(0,ret.length()-2)+"]");
    }
    public String getAttDesc(int attribute){
        try
        {
            ResultSet rs;
            JDBCQuery query=new JDBCQuery(conn);
            rs=query.select("select att_mnem from IISC_ATTRIBUTE  where ATT_id="+ attribute +" and PR_id="+pr);
            if(rs.next())
                return rs.getString(1);
        }
        catch(SQLException e)
        {
          e.printStackTrace();
        }
        return "";
    }
    public String getKeysDesc(Set attributes){
        Iterator it= attributes.iterator();
        String ret="";
        while(it.hasNext()){
            Set att=(HashSet)it.next();
            ret=ret+getKey(att)+", ";
        }
        return ("{"+ret.substring(0,ret.length()-2)+"}");       
    }
    public String getKey(Set attributes){
        Iterator it= attributes.iterator();
        String ret="";
        while(it.hasNext()){
            int att=(new Integer(it.next().toString())).intValue();
            ret=ret+getAttDesc(att)+"+";
        }
        if(ret.length()>0)
            return ("["+ret.substring(0,ret.length()-1)+"]");   
        else
            return "";
    }   
    public void setCheckboxes(){
        /*try
        {
            ResultSet rs;
            JDBCQuery query=new JDBCQuery(conn);
            rs=query.select("select * from IISC_COLLISION_LOG where CL_obsolete=0 and AS_id="+ id +" and PR_id="+ pr + " and CL_type=50 order by CL_date desc");
            if(rs.next())
            {
                ind=1;
                chReport.setSelected(true);
            }
        }
        catch(SQLException e)
        {
          e.printStackTrace();
        }*/
        if(ind==0){
            chRemoveDBSchema.setVisible(false);
            chApp.setVisible(true);
            chDBSchema.setVisible(true);
            chReport.setVisible(true);
            lbReport.setVisible(false);
            lbApp.setVisible(false);
            lbDBSchema.setVisible(false);
        }
        else if(ind==1){
            if(chReport.isSelected())
            {
                lbReport.setVisible(true);
                lbReport.setCursor(new Cursor(Cursor.HAND_CURSOR));
                lbReport.setText("<html><u>view report</u>");
            }
            chRemoveDBSchema.setVisible(true);
            chApp.setVisible(true);
            chDBSchema.setVisible(false);
            chReport.setVisible(false);
            lbApp.setVisible(false);
            lbDBSchema.setVisible(true);
        }
        else if(ind==2){
            chRemoveDBSchema.setVisible(true);
            chApp.setVisible(false);
            chDBSchema.setVisible(false);
            chReport.setVisible(false);
            lbApp.setVisible(true);
            lbDBSchema.setVisible(true);
            if(chReport.isSelected())
            {
                lbReport.setVisible(true);
                lbReport.setCursor(new Cursor(Cursor.HAND_CURSOR));
                lbReport.setText("<html><u>view report</u>");
            }
        }
    }

    private void chRemCandidate_actionPerformed(ActionEvent e) {
    }

    private void chRemCandidate_mouseClicked(MouseEvent e) {
    }

    private void chRemGraph_actionPerformed(ActionEvent e) {
    }

    private void chRemGraph_mouseClicked(MouseEvent e) {
    }

    private void chRemSynthesis_actionPerformed(ActionEvent e) {
    }

    private void chRemSynthesis_mouseClicked(MouseEvent e) {
    }

    private void chDBSchema_actionPerformed(ActionEvent e) {
    }

    private void chDBSchema_mouseClicked(MouseEvent e) {
    }

    private void chCandidate_actionPerformed(ActionEvent e) {
    }

    private void chCandidate_mouseClicked(MouseEvent e) {
    }

    private void chApp_actionPerformed(ActionEvent e) {
    }


    private void btnHelp_actionPerformed(ActionEvent e) {
        Help hlp =new  Help((IISFrameMain) getParent().getParent(),getTitle(), true, conn );
        Settings.Center(hlp);
        hlp.setVisible(true);
    }

    private void chRelationDBSchema_actionPerformed(ActionEvent e) {
    }

    private void lbReport_mouseClicked(MouseEvent e) {
        if (lbReport.getText().equals("<html><u>view report</u>"))
            openReport();
    }

    private void chApp_mouseClicked(MouseEvent e) {
        if(chApp.isSelected())
            chDBSchema.setSelected(true);
    }

    private void chReport_mouseClicked(MouseEvent e) {
        if(chReport.isSelected())
            chDBSchema.setSelected(true);
    }
}
