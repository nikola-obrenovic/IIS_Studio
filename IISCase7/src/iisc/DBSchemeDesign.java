package iisc;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

import java.awt.Color;
import java.awt.event.MouseEvent;
public class DBSchemeDesign extends JDialog 
{
  private int id;
  private PTree tree;
  private Connection con;
  private JButton btnClose = new JButton();
  private JButton btnSave1 = new JButton();
  private JButton btnHelp = new JButton();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  private ImageIcon imageOK = new ImageIcon(IISFrameMain.class.getResource("icons/ok.gif"));
  private JLabel txtApp = new JLabel();
  private JCheckBox chGraph = new JCheckBox();
  private JCheckBox chCandidate = new JCheckBox();
  private JLabel lbDBSchema = new JLabel();
  private JLabel lbGraph = new JLabel();
  private JLabel lbCandidate = new JLabel();
  private JCheckBox chDBSchema = new JCheckBox();
  private JCheckBox chRemoveDBSchema = new JCheckBox();
  private JLabel lbPropagation = new JLabel();
  private JCheckBox chPropagation = new JCheckBox();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JCheckBox chRemSynthesis = new JCheckBox();
  private JCheckBox chRemGraph = new JCheckBox();
  private JCheckBox chRemCandidate = new JCheckBox();
  private JCheckBox chRemPropagation = new JCheckBox();
  private JCheckBox chConstraint = new JCheckBox();
  private JLabel lbConstraint = new JLabel();
  private JCheckBox chRemConstraint = new JCheckBox();
  private JLabel jLabel4 = new JLabel();
  private JCheckBox chFundep = new JCheckBox();
  private JLabel lbFundep = new JLabel();
  private JLabel lbDependences = new JLabel();
  private JCheckBox chDependencies = new JCheckBox();
  public DBSchemeDesign()
  {
    this(null, "", false,null,null);
  }

  public DBSchemeDesign(IISFrameMain parent, String title, boolean modal, Connection conn, PTree tr)
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

 private void setCheckboxes()
 {
        try
    {
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    rs=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ tree.tree.getSelectionPath().getLastPathComponent().toString() +"' and PR_id="+ tree.ID);
    if(rs.next())
    {id=rs.getInt(1);
     setTitle("DB Schema Design "+  rs.getString("As_name") );
    }
    query.Close();
    Iterator it=tree.WindowsManager.iterator();
      while(it.hasNext())
      {
        Object obj=(Object)it.next();
        Class cls=obj.getClass();
        if(cls==this.getClass())
        {if(((DBSchemeDesign)obj).id==id)
        { ((DBSchemeDesign)obj).dispose();
        }
        }
      }
       this.setResizable(false);
    lbPropagation.setVisible(false);
    lbConstraint.setVisible(false);
    chPropagation.setVisible(true);
    chConstraint.setVisible(true);
    chRemPropagation.setVisible(false);
    chRemPropagation.setSelected(false);
    chRemConstraint.setVisible(false);
    chRemConstraint.setSelected(false);
    chRemoveDBSchema.setSelected(false);
     rs=query.select("select * from IISC_relation_scheme where AS_id="+ id +" and PR_id="+ tree.ID);
   if(rs.next())
    {
    lbDBSchema.setVisible(true);
    chRemSynthesis.setVisible(true);
    chDBSchema.setVisible(false);
    chRemoveDBSchema.setVisible(true);
    jLabel2.setVisible(true);}
    else
    {
    lbDBSchema.setVisible(false);
    chRemSynthesis.setVisible(false);
    chRemSynthesis.setSelected(false);
    chRemoveDBSchema.setVisible(false);
    chDBSchema.setVisible(true);
    chRemoveDBSchema.setVisible(false);
    jLabel2.setVisible(false);
    
    }
    query.Close();
    rs=query.select("select * from IISC_RELATION_SCHEME where RS_propagate>0 and AS_id="+ id +" and PR_id="+ tree.ID);
    if(rs.next())
    {
    lbGraph.setVisible(true);
    chRemGraph.setVisible(true);
    chGraph.setVisible(false);
    }
    else
    {
    lbGraph.setVisible(false);
    chRemGraph.setVisible(false);
    chRemGraph.setSelected(false);
    chGraph.setVisible(true);
    }
    query.Close();
    rs=query.select("select * from IISC_RS_KEY where RS_candidate=1 and AS_id="+ id +" and PR_id="+ tree.ID);
    if(rs.next())
    {
    lbCandidate.setVisible(true);
    chRemCandidate.setVisible(true);
    chCandidate.setVisible(false);
    
    }
    else
    {
    lbCandidate.setVisible(false);
    chRemCandidate.setVisible(false);
    chRemCandidate.setSelected(false);
    chCandidate.setVisible(true);
    }
    query.Close();
    rs=query.select("select * from IISC_RELATION_SCHEME where RS_propagate=1 and AS_id="+ id +" and PR_id="+ tree.ID);
    if(rs.next())
    {
    lbDBSchema.setVisible(true);
    chDBSchema.setVisible(false);
    lbGraph.setVisible(true);
    chGraph.setVisible(false);
    lbCandidate.setVisible(true);
    chCandidate.setVisible(false);
    lbPropagation.setVisible(true);
    chPropagation.setVisible(false);
    chRemCandidate.setVisible(true);
    chRemPropagation.setVisible(true);
    }
    query.Close();
    rs=query.select("select * from IISC_RELATION_SCHEME where RS_propagate=3 and AS_id="+ id +" and PR_id="+ tree.ID);
    if(rs.next())
    {
    lbDBSchema.setVisible(true);
    chDBSchema.setVisible(false);
    lbGraph.setVisible(true);
    chGraph.setVisible(false);
    lbCandidate.setVisible(true);
    chCandidate.setVisible(false);
    lbPropagation.setVisible(true);
    chPropagation.setVisible(false);
    lbConstraint.setVisible(true);
    chConstraint.setVisible(false);
    chRemCandidate.setVisible(true);
    chRemPropagation.setVisible(true);
    chRemConstraint.setVisible(true);
    }
    query.Close();
    lbFundep.setText("");
    lbFundep.setVisible(false);
    lbFundep.setForeground(new Color(0,0,0));
    lbDependences.setVisible(false);
    lbDependences.setText("");
    lbDependences.setForeground(new Color(0,0,0));
    if(chConstraint.isVisible()) 
    chDependencies.setVisible(true);
    else
    {chDependencies.setVisible(false);
    rs=query.select("select * from IISC_COLLISION_LOG where CL_obsolete=0 and AS_id="+ id +" and PR_id="+ tree.ID+ " and CL_type=11 order by CL_date desc");
    if(rs.next())
    { 
     lbDependences.setVisible(true);
     lbDependences.setText("<html><u><center>A/B Dependencies and Homonyms</u>");
     lbDependences.setCursor(new Cursor(Cursor.HAND_CURSOR));
     lbDependences.setForeground(new Color(0,0,255));
     
    }
    query.Close();
    }
    if(chDBSchema.isVisible())
    chFundep.setVisible(true);
    else 
    {chFundep.setVisible(false);
     rs=query.select("select * from IISC_COLLISION_LOG where CL_obsolete=0 and AS_id="+ id +" and PR_id="+ tree.ID+ " and CL_type=10 order by CL_date desc");
    if(rs.next())
    {lbFundep.setVisible(true);
    lbFundep.setCursor(new Cursor(Cursor.HAND_CURSOR));
     lbFundep.setText("<html><u>Generating Log</u>");
     lbFundep.setForeground(new Color(0,0,255));
    }
    query.Close();
    }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
 }
  private void jbInit() throws Exception
  {this.setTitle("DB Schema Design");
  
    this.setSize(new Dimension(381, 279));
    this.getContentPane().setLayout(null);
    
    this.setFont(new Font("SansSerif", 0, 11));
    this.setModal(true);
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Close");
    btnClose.setBounds(new Rectangle(245, 205, 80, 30));
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
    btnSave1.setBounds(new Rectangle(165, 205, 75, 30));
    btnSave1.setMinimumSize(new Dimension(50, 30));
    btnSave1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save_ActionPerformed(ae);
        }
      });
    btnSave1.setFont(new Font("SansSerif", 0, 11));
    btnHelp.setBounds(new Rectangle(330, 205, 35, 30));
    btnHelp.setFont(new Font("SansSerif", 0, 11));
    btnHelp.setIcon(imageHelp);
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    txtApp.setBounds(new Rectangle(45, 5, 90, 25));
    txtApp.setFont(new Font("SansSerif", 1, 11));
    chGraph.setText("Generating Closure Graph");
    chGraph.setBounds(new Rectangle(20, 55, 170, 25));
    chGraph.setFont(new Font("SansSerif", 0, 11));
    chGraph.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chGraph_mouseClicked(e);
        }
      });
    chGraph.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chGraph_actionPerformed(e);
        }
      });
    chCandidate.setText("Detecting Primary Key Candidates");
    chCandidate.setBounds(new Rectangle(20, 80, 200, 20));
    chCandidate.setFont(new Font("SansSerif", 0, 11));
    chCandidate.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chCandidate_mouseClicked(e);
        }
      });
    chCandidate.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chCandidate_actionPerformed(e);
        }
      });
    lbDBSchema.setText("Generating DB Schema");
    lbDBSchema.setBounds(new Rectangle(20, 40, 190, 15));
    lbDBSchema.setFont(new Font("SansSerif", 0, 11));
    lbDBSchema.setIcon(imageOK);
    lbGraph.setText("Generating Closure Graph");
    lbGraph.setBounds(new Rectangle(20, 60, 165, 15));
    lbGraph.setIcon(imageOK);
    lbGraph.setFont(new Font("SansSerif", 0, 11));
    lbCandidate.setText("Detecting Primary Key Candidates");
    lbCandidate.setBounds(new Rectangle(20, 80, 190, 20));
    lbCandidate.setIcon(imageOK);
    lbCandidate.setFont(new Font("SansSerif", 0, 11));
    chDBSchema.setText("Generating DB Schema");
    chDBSchema.setBounds(new Rectangle(20, 35, 195, 20));
    chDBSchema.setFont(new Font("SansSerif", 0, 11));
    chDBSchema.setSelected(true);
    chDBSchema.setActionCommand("Generating DB Schema");
    chDBSchema.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
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
    chRemoveDBSchema.setText("Remove DB Shema");
    chRemoveDBSchema.setBounds(new Rectangle(40, 170, 155, 20));
    chRemoveDBSchema.setFont(new Font("SansSerif", 0, 11));
    chRemoveDBSchema.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chRelationDBSchema_actionPerformed(e);
        }
      });
    lbPropagation.setText("Primary Key Propagation");
    lbPropagation.setBounds(new Rectangle(20, 105, 190, 20));
    lbPropagation.setIcon(imageOK);
    lbPropagation.setFont(new Font("SansSerif", 0, 11));
    chPropagation.setText("Primary Key Propagation");
    chPropagation.setBounds(new Rectangle(20, 105, 195, 20));
    chPropagation.setFont(new Font("SansSerif", 0, 11));
    chPropagation.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chPropagation_mouseClicked(e);
        }
      });
    chPropagation.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chPropagation_actionPerformed(e);
        }
      });
    jLabel1.setText("Complete");
    jLabel1.setBounds(new Rectangle(20, 10, 85, 15));
    jLabel1.setFont(new Font("Tahoma", 1, 11));
    jLabel2.setText("Remove");
    jLabel2.setBounds(new Rectangle(220, 10, 85, 15));
    jLabel2.setFont(new Font("Tahoma", 1, 11));
    chRemSynthesis.setBounds(new Rectangle(240, 35, 20, 20));
    chRemSynthesis.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chRemSynthesis_mouseClicked(e);
        }
      });
    chRemSynthesis.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chRemSynthesis_actionPerformed(e);
        }
      });
    chRemGraph.setBounds(new Rectangle(240, 57, 20, 20));
    chRemGraph.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chRemGraph_mouseClicked(e);
        }
      });
    chRemGraph.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chRemGraph_actionPerformed(e);
        }
      });
    chRemCandidate.setBounds(new Rectangle(240, 80, 20, 20));
    chRemCandidate.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chRemCandidate_mouseClicked(e);
        }
      });
    chRemCandidate.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chRemCandidate_actionPerformed(e);
        }
      });
    chRemPropagation.setBounds(new Rectangle(240, 105, 20, 20));
    chRemPropagation.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chRemPropagation_mouseClicked(e);
        }
      });
    chRemPropagation.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chRemPropagation_actionPerformed(e);
        }
      });
    chConstraint.setText("Generating other constraints");
    chConstraint.setBounds(new Rectangle(20, 130, 190, 20));
    chConstraint.setFont(new Font("SansSerif", 0, 11));
    chConstraint.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chConstraint_mouseClicked(e);
        }
      });
    chConstraint.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chConstraint_actionPerformed(e);
        }
      });
    lbConstraint.setText("Generating other constraints");
    lbConstraint.setBounds(new Rectangle(20, 130, 165, 20));
    lbConstraint.setIcon(imageOK);
    lbConstraint.setFont(new Font("SansSerif", 0, 11));
    chRemConstraint.setBounds(new Rectangle(240, 130, 20, 20));
    chRemConstraint.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chRemConstraint_mouseClicked(e);
        }
      });
    chRemConstraint.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chRemConstraint_actionPerformed(e);
        }
      });
    jLabel4.setText("Reports");
    jLabel4.setBounds(new Rectangle(285, 10, 110, 15));
    jLabel4.setFont(new Font("Tahoma", 1, 11));
    chFundep.setBounds(new Rectangle(295, 35, 20, 20));
    lbFundep.setBounds(new Rectangle(275, 35, 90, 20));
    lbFundep.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chFundep_mouseClicked(e);
        }
      });
    lbDependences.setBounds(new Rectangle(265, 130, 100, 40));
    lbDependences.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chDependeces_mouseClicked(e);
        }
      });
    chDependencies.setBounds(new Rectangle(295, 130, 20, 20));
    this.getContentPane().add(chDependencies, null);
    this.getContentPane().add(chFundep, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(chRemConstraint, null);
    this.getContentPane().add(lbConstraint, null);
    this.getContentPane().add(chConstraint, null);
    

    this.getContentPane().add(chRemPropagation, null);
    this.getContentPane().add(chRemCandidate, null);
    this.getContentPane().add(chRemGraph, null);
    this.getContentPane().add(chRemSynthesis, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(chPropagation, null);
    this.getContentPane().add(lbPropagation, null);
    this.getContentPane().add(chRemoveDBSchema, null);
    this.getContentPane().add(chDBSchema, null);
    this.getContentPane().add(lbCandidate, null);
    this.getContentPane().add(lbGraph, null);
    this.getContentPane().add(lbDBSchema, null);
    this.getContentPane().add(chCandidate, null);
    this.getContentPane().add(chGraph, null);
    this.getContentPane().add(txtApp, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnSave1, null);
    this.getContentPane().add(btnClose, null);
    this.getContentPane().add(lbFundep, null);
    this.getContentPane().add(lbDependences, null);
     setCheckboxes();
  }



  private void close_ActionPerformed(ActionEvent e)
  {dispose();
  }

  private void save_ActionPerformed(ActionEvent e)
  {
    try
    { if(lbConstraint.isVisible()&& !chRemConstraint.isSelected())return;
    JDBCQuery query=new JDBCQuery(tree.con);
    JDBCQuery query1=new JDBCQuery(tree.con);
    ResultSet rs1,rs;
      rs1=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ tree.tree.getLastSelectedPathComponent().toString()  +"'" );
      rs1.next();
      int h=rs1.getInt("AS_id");
      int j=rs1.getInt("PR_id");
      String s=rs1.getString("AS_name");
      query.Close(); 
     FunDepSet Sinth=new FunDepSet(tree.con ,h);
     Synthesys Syn=new Synthesys();
     Syn.pr=j;
     Syn.as=h;
     Syn.con=tree.con;

 if(chRemoveDBSchema.isSelected() && chRemoveDBSchema.isVisible())
  {   Syn.clearDBDesign("");
  query.update("update IISC_APP_SYSTEM set AS_collision_detection=0 where AS_id="+ h +" and PR_id="+ tree.ID);
 query.update("update IISC_APP_SYSTEM,IISC_APP_SYSTEM_CONTAIN set AS_collision_detection=0 where IISC_APP_SYSTEM_CONTAIN.AS_id=IISC_APP_SYSTEM_CONTAIN.AS_id and IISC_APP_SYSTEM_CONTAIN.AS_id_con="+ h +" and IISC_APP_SYSTEM_CONTAIN.PR_id="+ tree.ID);
 }
 else if(chRemGraph.isSelected() || chRemPropagation.isSelected() || chRemCandidate.isSelected() || chRemConstraint.isSelected()) 
 {if(chRemConstraint.isSelected())
  {   
      query.update("delete  from IISC_RSC_ACTION where PR_id=" + tree.ID + " and  AS_id=" + h); 
      query.update("delete  from IISC_RSC_LHS_RHS where AS_id=" + h + " and  PR_id=" + tree.ID); 
      query.update("delete  from IISC_RSC_RS_SET where AS_id=" + h + " and  PR_id=" + tree.ID);
      query.update("delete  from IISC_RS_CONSTRAINT where AS_id=" + h + " and  PR_id=" + tree.ID);
      /*nobrenovic: start*/
       query.update("delete  from IISC_CHKC_BASIC_TERM where AS_id=" + h + " and  PR_id=" + tree.ID);
       query.update("delete  from IISC_CHKC_NF_PART where AS_id=" + h + " and  PR_id=" + tree.ID);
       query.update("delete  from IISC_CHKC_NORMAL_FORM where AS_id=" + h + " and  PR_id=" + tree.ID);
       query.update("delete  from IISC_CHECK_CONSTRAINT where AS_id=" + h + " and  PR_id=" + tree.ID);
      /*nobrenovic: stop*/
     
      query.update("delete from IISC_GRAPH_CLOSURE  where GC_edge_type>=1 and AS_id="+ h +" and PR_id="+ tree.ID);
      query.update("update IISC_RELATION_SCHEME set RS_propagate=1 where AS_id="+ h +" and PR_id="+ tree.ID);
      query.update("update IISC_GRAPH_CLOSURE set RSC_id=NULL  where AS_id="+ h +" and PR_id="+ tree.ID);
      query.update("update IISC_APP_SYSTEM set AS_collision_detection=0 where AS_id="+ h +" and PR_id="+ tree.ID);
      query.update("update IISC_APP_SYSTEM,IISC_APP_SYSTEM_CONTAIN set AS_collision_detection=0 where IISC_APP_SYSTEM_CONTAIN.AS_id=IISC_APP_SYSTEM_CONTAIN.AS_id and IISC_APP_SYSTEM_CONTAIN.AS_id_con="+ h +" and IISC_APP_SYSTEM_CONTAIN.PR_id="+ tree.ID);
   }
    if(chRemPropagation.isSelected())
  {  
      query.update("update IISC_RELATION_SCHEME set RS_propagate=2 where AS_id="+ h +" and PR_id="+ tree.ID);
      query.update("update IISC_APP_SYSTEM set AS_collision_detection=0 where AS_id="+ h +" and PR_id="+ tree.ID);
      query.update("update IISC_APP_SYSTEM,IISC_APP_SYSTEM_CONTAIN set AS_collision_detection=0 where IISC_APP_SYSTEM_CONTAIN.AS_id=IISC_APP_SYSTEM_CONTAIN.AS_id and IISC_APP_SYSTEM_CONTAIN.AS_id_con="+ h +" and IISC_APP_SYSTEM_CONTAIN.PR_id="+ tree.ID);
  }
 if(chRemCandidate.isSelected())
  {query.update("update IISC_RS_KEY set RS_candidate=0,RS_primary_key=0  where AS_id="+ h +" and PR_id="+ tree.ID);
   query.update("update IISC_APP_SYSTEM set AS_collision_detection=0 where AS_id="+ h +" and PR_id="+ tree.ID);
   query.update("update IISC_APP_SYSTEM,IISC_APP_SYSTEM_CONTAIN set AS_collision_detection=0 where IISC_APP_SYSTEM_CONTAIN.AS_id=IISC_APP_SYSTEM_CONTAIN.AS_id and IISC_APP_SYSTEM_CONTAIN.AS_id_con="+ h +" and IISC_APP_SYSTEM_CONTAIN.PR_id="+ tree.ID);
  }
  if(chRemGraph.isSelected())
  {
  query.update("update IISC_RELATION_SCHEME set RS_propagate=0 where AS_id="+ h +" and PR_id="+ tree.ID);
  query.update("delete from IISC_GRAPH_CLOSURE  where AS_id="+ h +" and PR_id="+ tree.ID);
  }
 } 
else
{
    
 if(chDBSchema.isSelected()  && chDBSchema.isVisible())
  {  
     if(chFundep.isSelected()&& chFundep.isVisible())
    { Syn.Synth(Sinth.getSet(1),Sinth.getNonfunmark());
    }
     else
      Syn.Synth(Sinth.getSet(0),Sinth.getNonfunmark());
      query.update("update IISC_APP_SYSTEM set AS_collision_detection=0 where AS_id="+ h +" and PR_id="+ tree.ID);
     query.update("update IISC_APP_SYSTEM,IISC_APP_SYSTEM_CONTAIN set AS_collision_detection=0 where IISC_APP_SYSTEM_CONTAIN.AS_id=IISC_APP_SYSTEM_CONTAIN.AS_id and IISC_APP_SYSTEM_CONTAIN.AS_id_con="+ h +" and IISC_APP_SYSTEM_CONTAIN.PR_id="+ tree.ID);
 }
   if(chGraph.isSelected()  && chGraph.isVisible())
  {   query.update("update IISC_RELATION_SCHEME set RS_propagate=2 where AS_id="+ h +" and PR_id="+ tree.ID); 
     Syn.graf_zatvaranja();
  }
  if(chCandidate.isSelected()  && chCandidate.isVisible())
  {   
     Syn.Kandidati();
      query.update("update IISC_APP_SYSTEM set AS_collision_detection=0 where AS_id="+ h +" and PR_id="+ tree.ID);
       query.update("update IISC_APP_SYSTEM,IISC_APP_SYSTEM_CONTAIN set AS_collision_detection=0 where IISC_APP_SYSTEM_CONTAIN.AS_id=IISC_APP_SYSTEM_CONTAIN.AS_id and IISC_APP_SYSTEM_CONTAIN.AS_id_con="+ h +" and IISC_APP_SYSTEM_CONTAIN.PR_id="+ tree.ID);
   }
  String str=new String();
  if(chPropagation.isSelected()  && chPropagation.isVisible())
  {//Syn.prikazi( Syn.getFunDep() );
  
   String log=new String();
    rs=query.select("select RS_name,IISC_RS_KEY.RS_id  from IISC_RELATION_SCHEME,IISC_RS_KEY where IISC_RELATION_SCHEME.RS_id=IISC_RS_KEY.RS_id and  RS_candidate=1 and RS_primary_key=0 and  IISC_RS_KEY.AS_id="+ h +" and IISC_RS_KEY.PR_id="+ tree.ID+ " GROUP BY RS_name, IISC_RS_KEY.RS_id");
    
    while(rs.next()  )
   {int rid=rs.getInt(2);
   rs1=query1.select("select count(*) from  IISC_RS_KEY where IISC_RS_KEY.RS_id="+ rid +" and  RS_candidate=1 and RS_primary_key=1 and  IISC_RS_KEY.AS_id="+ h +" and IISC_RS_KEY.PR_id="+ tree.ID); 
    rs1.next();
    if( rs1.getInt(1)<1)
    {query1.Close();
      rs1=query1.select("select count(*) from  IISC_RS_KEY where IISC_RS_KEY.RS_id="+ rid +" and  RS_candidate=1 and RS_primary_key=0 and  IISC_RS_KEY.AS_id="+ h +" and IISC_RS_KEY.PR_id="+ tree.ID); 
    rs1.next();
    if( rs1.getInt(1)>1)
    {str= str + "<br>" + rs.getString(1);
   }
  query1.Close();
    }
    else
    query1.Close();
   }
    query.Close();
    if(!str.equals(""))
    JOptionPane.showMessageDialog(null, "<html><center>You have not marked primary keys for the following relation schemes:</center>"+str,"DB Schema Design" , JOptionPane.ERROR_MESSAGE);
    else 
   {
   log=Syn.algoritam_prostiranja(Syn.getRelationSchemes(),Syn.getFunDep(), new HashSet());
    if(!log.equals(""))
   { Log lg =new  Log((Frame) getParent(),getTitle(), true );
    Settings.Center(lg);
    lg.setLogText("WARNING!\n"+log);
    lg.setVisible(true);
   }
  if(str.equals(""))
 { query.update("update IISC_APP_SYSTEM set AS_collision_detection=0 where AS_id="+ h +" and PR_id="+ tree.ID);
   query.update("update IISC_APP_SYSTEM,IISC_APP_SYSTEM_CONTAIN set AS_collision_detection=0 where IISC_APP_SYSTEM_CONTAIN.AS_id=IISC_APP_SYSTEM_CONTAIN.AS_id and IISC_APP_SYSTEM_CONTAIN.AS_id_con="+ h +" and IISC_APP_SYSTEM_CONTAIN.PR_id="+ tree.ID);

 } }
    
  }
  if(str.equals("") && chConstraint.isSelected()  && chConstraint.isVisible())
 {
  query.update("update IISC_APP_SYSTEM set AS_collision_detection=0 where AS_id="+ h +" and PR_id="+ tree.ID);
  query.update("update IISC_RELATION_SCHEME set RS_propagate=3 where AS_id="+ h +" and PR_id="+ tree.ID);
  query.update("update IISC_APP_SYSTEM,IISC_APP_SYSTEM_CONTAIN set AS_collision_detection=0 where IISC_APP_SYSTEM_CONTAIN.AS_id=IISC_APP_SYSTEM_CONTAIN.AS_id and IISC_APP_SYSTEM_CONTAIN.AS_id_con="+ h +" and IISC_APP_SYSTEM_CONTAIN.PR_id="+ tree.ID);
  Syn.integrity_Generation(); 
  query.update("update IISC_COLLISION_LOG set CL_obsolete=1 where CL_type=11 and AS_id="+ h +" and PR_id="+ tree.ID);
  String hom="";
  if(chDependencies.isSelected())
  {hom=Syn.get_A_and_B_dependecies();
   hom=hom+Syn.getHomonyms();
   if(hom.equals(""))
    hom="<tr><td align=center><b>No homonyms or A/B dependecies detected!</td></tr>";
    rs=query.select("select max(CL_id)+1  from IISC_COLLISION_LOG");
    int idl=0;
    if(rs.next())
    idl=rs.getInt(1);
    query.Close();
    query.update("insert into IISC_COLLISION_LOG(CL_id,PR_id,AS_id,CL_type,CL_date,CL_text,CL_warning,CL_obsolete) values ("+ idl +","+ tree.ID  +","+ h +",11,'" + ODBCList.now() + "','" + hom + "',0,0)");
  }
  
  /*nobrenovic: start*/
  CheckConsSynthesys ccSyn = new CheckConsSynthesys(tree.ID, h, tree.con);
  ccSyn.transformCheckCons();
  /*nobrenovic: end*/
  }
   
} 
 setCheckboxes();
 tree.setVisible(false);
 tree.pravi_drvo();
 tree.setVisible(true);
 tree.select_node(s,"Application Systems"); 
 if(!this.isVisible())
 this.setVisible(true);
}
    catch(Exception ae)
    {
      ae.printStackTrace();
    }
    
      
  }

  private void btnHelp_actionPerformed(ActionEvent e)
  {Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void jCheckBox1_actionPerformed(ActionEvent e)
  {
  }

  private void chDBSchema_actionPerformed(ActionEvent e)
  {

  }

  private void chCandidate_actionPerformed(ActionEvent e)
  {
  
  }

  private void chGraph_actionPerformed(ActionEvent e)
  {
  
  }

  private void chRelationDBSchema_actionPerformed(ActionEvent e)
  {
  if(chRemoveDBSchema.isSelected())
  {chGraph.setSelected(false);
   chCandidate.setSelected(false);
   chPropagation.setSelected(false);
   chConstraint.setSelected(false);
   chRemCandidate.setSelected(true);
   chRemGraph.setSelected(true);
   chRemSynthesis.setSelected(true);
   chRemPropagation.setSelected(true);
    chRemConstraint.setSelected(true);
  }
  else
  {
    chRemSynthesis.setSelected(false);
  }
  }

  private void chPropagation_actionPerformed(ActionEvent e)
  {

  }

  private void chRemSynthesis_actionPerformed(ActionEvent e)
  { 
  }

  private void chRemGraph_actionPerformed(ActionEvent e)
  { 
  }

  private void chRemCandidate_actionPerformed(ActionEvent e)
  { 
  }

  private void chRemPropagation_actionPerformed(ActionEvent e)
  { 
  }

  private void chConstraint_actionPerformed(ActionEvent e)
  {

  }

  private void chRemConstraint_actionPerformed(ActionEvent e)
  {
  
  }

  private void chFundep_mouseClicked(MouseEvent e)
  { 
   try
    {
    JDBCQuery query=new JDBCQuery(tree.con);
    ResultSet rs;
  int i=-1;
  rs=query.select("select * from IISC_COLLISION_LOG  where   AS_id=" + id + "  and PR_id="+tree.ID+ " and CL_type=10 order by CL_id desc");
  if(rs.next())
  i=rs.getInt(1);
  Report rep=new Report((IISFrameMain)getParent(),"",true,con,i);
  Settings.Center(rep);
  rep.setVisible(true);
  query.Close(); 
    }
     catch(SQLException ex)
    {
      ex.printStackTrace();
    }
  }

  private void chDependeces_mouseClicked(MouseEvent e)
  {try
    {
    JDBCQuery query=new JDBCQuery(tree.con);
    ResultSet rs;
  int i=-1;
  rs=query.select("select * from IISC_COLLISION_LOG  where   AS_id=" + id + "  and PR_id="+tree.ID+ " and CL_type=11 order by CL_id desc");
  if(rs.next())
  i=rs.getInt(1);
  Report rep=new Report((IISFrameMain)getParent(),"",true,con,i);
  Settings.Center(rep);
  rep.setVisible(true);
  query.Close(); 
    }
     catch(SQLException ex)
    {
      ex.printStackTrace();
    }
  }

  private void chConstraint_mouseClicked(MouseEvent e)
  { if(chConstraint.isSelected())
  {chPropagation.setSelected(true);
  chGraph.setSelected(true);
  chCandidate.setSelected(true);
  chRemoveDBSchema.setSelected(false);
  }
  }

  private void chPropagation_mouseClicked(MouseEvent e)
  {  if(chPropagation.isSelected())
  {chGraph.setSelected(true);
  chCandidate.setSelected(true);
  chRemoveDBSchema.setSelected(false);
  }
  else
  chConstraint.setSelected(false);
  }

  private void chCandidate_mouseClicked(MouseEvent e)
  {if(chCandidate.isSelected())chGraph.setSelected(true);
 else
  {  
  chConstraint.setSelected(false);
  chPropagation.setSelected(false);
   }
  }

  private void chGraph_mouseClicked(MouseEvent e)
  {if(!chGraph.isSelected())
  {chCandidate.setSelected(false);
   chPropagation.setSelected(false);
  chConstraint.setSelected(false);
   }
  }

  private void chDBSchema_mouseClicked(MouseEvent e)
  {  chDBSchema.setSelected(true);
  }

  private void chRemSynthesis_mouseClicked(MouseEvent e)
  {if(chRemSynthesis.isSelected())
  {chRemoveDBSchema.setSelected(true);
  chRemGraph.setSelected(true);
  chRemCandidate.setSelected(true);
  chRemPropagation.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
  {chRemoveDBSchema.setSelected(false);

    if(chRemGraph.isSelected())
  { 
  chRemCandidate.setSelected(true);
  chRemPropagation.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
  { if(chRemPropagation.isVisible())
   if(chRemPropagation.isSelected())
  { 
  chRemCandidate.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
   chRemCandidate.setSelected(false);
  }
  }
  }

  private void chRemGraph_mouseClicked(MouseEvent e)
  {if(chRemSynthesis.isSelected())
  {chRemoveDBSchema.setSelected(true);
  chRemGraph.setSelected(true);
  chRemCandidate.setSelected(true);
  chRemPropagation.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
  {chRemoveDBSchema.setSelected(false);

    if(chRemGraph.isSelected())
  { 
  chRemCandidate.setSelected(true);
  chRemPropagation.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
  { if(chRemPropagation.isVisible())
   if(chRemPropagation.isSelected())
  { 
  chRemCandidate.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
   chRemCandidate.setSelected(false);
  }
  }
  }

  private void chRemCandidate_mouseClicked(MouseEvent e)
  { if(chRemSynthesis.isSelected())
  {chRemoveDBSchema.setSelected(true);
  chRemGraph.setSelected(true);
  chRemCandidate.setSelected(true);
  chRemPropagation.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
  {chRemoveDBSchema.setSelected(false);

    if(chRemGraph.isSelected())
  { 
  chRemCandidate.setSelected(true);
  chRemPropagation.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
  { if(chRemPropagation.isVisible())
   if(chRemPropagation.isSelected())
  { 
  chRemCandidate.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
   chRemCandidate.setSelected(false);
  }
  }
  }

  private void chRemPropagation_mouseClicked(MouseEvent e)
  {if(chRemSynthesis.isSelected())
  {chRemoveDBSchema.setSelected(true);
  chRemGraph.setSelected(true);
  chRemCandidate.setSelected(true);
  chRemPropagation.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
  {chRemoveDBSchema.setSelected(false);

    if(chRemGraph.isSelected())
  { 
  chRemCandidate.setSelected(true);
  chRemPropagation.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
  { if(chRemPropagation.isVisible())
   if(chRemPropagation.isSelected())
  { 
  chRemCandidate.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
   chRemCandidate.setSelected(false);
  }
  }
  }

  private void chRemConstraint_mouseClicked(MouseEvent e)
  {if(chRemSynthesis.isSelected())
  {chRemoveDBSchema.setSelected(true);
  chRemGraph.setSelected(true);
  chRemCandidate.setSelected(true);
  chRemPropagation.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
  {chRemoveDBSchema.setSelected(false);

    if(chRemGraph.isSelected())
  { 
  chRemCandidate.setSelected(true);
  chRemPropagation.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
  { if(chRemPropagation.isVisible())
   if(chRemPropagation.isSelected())
  { 
  chRemCandidate.setSelected(true);
  chRemConstraint.setSelected(true);
  }
  else
   chRemCandidate.setSelected(false);
  }
  }
  }

 
 
}