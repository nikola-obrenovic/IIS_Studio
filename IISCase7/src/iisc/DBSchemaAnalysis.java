package iisc;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import java.util.Iterator;
import javax.swing.JDialog;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import java.awt.Rectangle;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.MouseEvent;
import java.awt.Color;
public class DBSchemaAnalysis extends JDialog 
{
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  private ImageIcon imageOK = new ImageIcon(IISFrameMain.class.getResource("icons/ok.gif"));
  private ImageIcon imageWarning = new ImageIcon(IISFrameMain.class.getResource("icons/warning.gif"));
  private ImageIcon imageError = new ImageIcon(IISFrameMain.class.getResource("icons/erase.gif"));
  private ImageIcon imageEmpty = new ImageIcon(IISFrameMain.class.getResource("icons/empty.gif"));
  private JLabel jLabel1 = new JLabel();
  private JLabel txtApp = new JLabel();
  private JLabel lb1 = new JLabel();
  private JLabel lb2 = new JLabel();
  private JLabel lb3 = new JLabel();
  private JLabel lb4 = new JLabel();
  private JLabel lb5 = new JLabel();
 
  private JButton btnHelp1 = new JButton();
  private JButton btnSave2 = new JButton();
  private JButton btnClose1 = new JButton();
  private JCheckBox chAttributes = new JCheckBox();
  private JCheckBox chKeys = new JCheckBox();
  private JCheckBox chUniques = new JCheckBox();
  private JCheckBox chNULLs = new JCheckBox();
  private JCheckBox chReferentials = new JCheckBox();
  private Connection con;
  private JLabel lbAttributes = new JLabel();
  private JLabel lbKeys = new JLabel();
  private JLabel lbUniques = new JLabel();
  private JLabel lbNULLS = new JLabel();
   private JLabel lbReferentials = new JLabel();
  private int id;
   private PTree tree;
    private JCheckBox cbChkConsCollisions = new JCheckBox();
    private JLabel lb6 = new JLabel();
    private JLabel lbChkConsColls = new JLabel();

    public DBSchemaAnalysis()
  {
    this(null, "", false,null,null);
  }

  public DBSchemaAnalysis(IISFrameMain parent, String title, boolean modal, Connection conn, PTree tr)
  {
    super(parent, title, modal);
    try
    { con=tr.con;
      tree=tr;
      jbInit();
      
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  { int stat=0;
    this.setSize(new Dimension(329, 270));
    this.getContentPane().setLayout(null);
   try
    {
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    rs=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ tree.tree.getSelectionPath().getLastPathComponent().toString() +"' and PR_id="+ tree.ID);
    if(rs.next())
    {id=rs.getInt(1);
     
     setTitle("DB Schema Analysis "+  rs.getString("As_name") );
      stat=rs.getInt("AS_collision_detection");
    }
    query.Close();
     Iterator it=tree.WindowsManager.iterator();
      while(it.hasNext())
      {
        Object obj=(Object)it.next();
        Class cls=obj.getClass();
        if(cls==this.getClass())
        {if(((DBSchemaAnalysis)obj).id==id)
        { ((DBSchemaAnalysis)obj).dispose();
        }
        }
      }
    /* rs=query.select("select * from IISC_COLLISION_LOG where AS_id="+ id +" and PR_id="+ tree.ID);
    if(rs.next())
    {
     stat=-rs.getInt("CL_type")-1;
    }
    query.Close();*/
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
     this.setResizable(false);
    jLabel1.setText("Complete");
    jLabel1.setBounds(new Rectangle(20, 10, 85, 15));
    jLabel1.setFont(new Font("SansSarif", 1, 11));
    txtApp.setBounds(new Rectangle(45, 5, 90, 25));
    txtApp.setFont(new Font("SansSerif", 1, 11));
    btnHelp1.setBounds(new Rectangle(280, 200, 35, 30));
    btnHelp1.setFont(new Font("SansSerif", 0, 11));
    btnHelp1.setIcon(imageHelp);
    btnHelp1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    btnSave2.setMaximumSize(new Dimension(50, 30));
    btnSave2.setPreferredSize(new Dimension(50, 30));
    btnSave2.setText("OK");
    btnSave2.setMinimumSize(new Dimension(50, 30));
    btnSave2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save_ActionPerformed(ae);
        }
      });
      btnSave2.setBounds(new Rectangle(110, 200, 80, 30));
    btnSave2.setFont(new Font("SansSerif", 0, 11));
    btnClose1.setMaximumSize(new Dimension(50, 30));
    btnClose1.setPreferredSize(new Dimension(50, 30));
    btnClose1.setText("Close");
    btnClose1.setBounds(new Rectangle(195, 200, 80, 30));
    btnClose1.setMinimumSize(new Dimension(50, 30));
    btnClose1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          close_ActionPerformed(ae);
        }
      });
    btnClose1.setFont(new Font("SansSerif", 0, 11));
    chAttributes.setText("Attribute set collisions");
    chAttributes.setBounds(new Rectangle(20, 45, 195, 20));
    chAttributes.setFont(new Font("SansSerif", 0, 11));
    chAttributes.setOpaque(false);
    chAttributes.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          setChecks(e);
        }
      });
        chReferentials.setText("Referential constraint collisions");
    chReferentials.setBounds(new Rectangle(20, 145, 195, 20));
    chReferentials.setFont(new Font("SansSerif", 0, 11));
    chReferentials.setOpaque(false);
    chReferentials.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          setChecks(e);
        }
      });
      cbChkConsCollisions.setText("Check constraint collisions");
      cbChkConsCollisions.setBounds(new Rectangle(20, 170, 195, 20));
      cbChkConsCollisions.setFont(new Font("SansSerif", 0, 11));
      cbChkConsCollisions.setOpaque(false);
      cbChkConsCollisions.addActionListener(new ActionListener()
      {
      public void actionPerformed(ActionEvent e)
      {
        setChecks(e);
      }
      });
    chKeys.setText("Key collisions");
    chKeys.setBounds(new Rectangle(20, 70, 195, 20));
    chKeys.setFont(new Font("SansSerif", 0, 11));
    chKeys.setOpaque(false);
    chKeys.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          setChecks(e);
        }
      });
    chUniques.setText("Unique constraint collisions");
    chUniques.setBounds(new Rectangle(20, 95, 195, 20));
    chUniques.setFont(new Font("SansSerif", 0, 11));
    chUniques.setOpaque(false);
    chUniques.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          setChecks(e);
        }
      });
    chNULLs.setText("NULL constraint collisions");
    chNULLs.setBounds(new Rectangle(20, 120, 195, 20));
    chNULLs.setFont(new Font("SansSerif", 0, 11));
    chNULLs.setOpaque(false);
    chNULLs.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          setChecks(e);
        }
      });
    lbAttributes.setText("Attribute set collisions");
    lbAttributes.setBounds(new Rectangle(21, 45, 165, 20));
    lbAttributes.setIcon(imageOK);
    lbReferentials.setText("Referential constraint collisions");
    lbReferentials.setBounds(new Rectangle(20, 145, 180, 20));
    lbReferentials.setIcon(imageOK);
        cbChkConsCollisions.setText("Check constraint collisions");
        cbChkConsCollisions.setBounds(new Rectangle(20, 170, 155, 20));
        lbChkConsColls.setText("Check constraints collisions");
        lbChkConsColls.setBounds(new Rectangle(20, 170, 180, 20));
        lbChkConsColls.setIcon(imageOK);
        lbAttributes.setFont(new Font("SansSerif", 0, 11));
    lbKeys.setText("Key collisions");
    lbKeys.setBounds(new Rectangle(21, 70, 110, 20));
    lbKeys.setIcon(imageOK);
    lbKeys.setFont(new Font("SansSerif", 0, 11));
    lbUniques.setText("Unique constraint collisions");
    lbUniques.setBounds(new Rectangle(21, 95, 165, 20));
    lbUniques.setIcon(imageOK);
    lbUniques.setFont(new Font("SansSerif", 0, 11));
    lbNULLS.setText("NULL constraint collisions");
    lbNULLS.setBounds(new Rectangle(21, 120, 165, 20));
    lbNULLS.setIcon(imageOK);
    lbNULLS.setFont(new Font("SansSerif", 0, 11));
    lb1.setText("not performed");
    lb1.setBounds(new Rectangle(200, 45, 165, 20));
    lb1.setFont(new Font("SansSerif", 0, 11));
    
    
    lb1.addMouseListener(new MouseAdapter()
      { 
        public void mouseClicked(MouseEvent e)
        {
          lb1_mouseClicked(e);
        }
      });
    lb2.setText("not performed");
    lb2.setBounds(new Rectangle(200, 70, 165, 20));
    lb2.setFont(new Font("SansSerif", 0, 11));
    lb2.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          lb2_mouseClicked(e);
        }
      });
    lb3.setText("not performed");
    lb3.setBounds(new Rectangle(200, 95, 165, 20));
    lb3.setFont(new Font("SansSerif", 0, 11));
    lb3.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          lb3_mouseClicked(e);
        }
      });
    lb4.setText("not performed");
    lb4.setBounds(new Rectangle(200, 120, 165, 20));
    lb4.setFont(new Font("SansSerif", 0, 11));
    lb4.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          lb4_mouseClicked(e);
        }
      });
    lb5.setText("not performed");
    lb5.setBounds(new Rectangle(200, 145, 165, 20));
    lb5.setFont(new Font("SansSerif", 0, 11));
    lb5.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          lb5_mouseClicked(e);
        }
      });
      lb6.setText("not performed");
      lb6.setBounds(new Rectangle(200, 170, 165, 20));
      lb6.setFont(new Font("SansSerif", 0, 11));
      lb6.addMouseListener(new MouseAdapter()
        {
          public void mouseClicked(MouseEvent e)
          {
            lb6_mouseClicked(e);
          }
        });
        this.getContentPane().add(lbChkConsColls, null);
        this.getContentPane().add(lb6, null);
        this.getContentPane().add(cbChkConsCollisions, null);
        this.getContentPane().add(lbNULLS, null);
    this.getContentPane().add(lbUniques, null);
    this.getContentPane().add(lbKeys, null);
        this.getContentPane().add(lbReferentials, null);
        this.getContentPane().add(lbAttributes, null);
    this.getContentPane().add(chNULLs, null);
    this.getContentPane().add(chUniques, null);
    this.getContentPane().add(chKeys, null);
    this.getContentPane().add(chAttributes, null);
    this.getContentPane().add(chReferentials, null);
        this.getContentPane().add(btnClose1, null);
        this.getContentPane().add(btnSave2, null);
        this.getContentPane().add(btnHelp1, null);
        this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(lb1, null);
    this.getContentPane().add(lb2, null);
    this.getContentPane().add(lb3, null);
    this.getContentPane().add(lb4, null);
    this.getContentPane().add(lb5, null);
    this.getContentPane().add(txtApp, null);

    setCheckboxes(stat);
  }
 private void setCheckboxes(int st)
 {
        try
    {
    if(st==-1)
    lbAttributes.setIcon(imageError);
    if(st==-2)
    lbKeys.setIcon(imageError);
    if(st==-3)
    lbUniques.setIcon(imageError);
    if(st==-4)
    lbNULLS.setIcon(imageError);
    if(st==-5)
    lbReferentials.setIcon(imageError);
    if(st==-6)
    lbChkConsColls.setIcon(imageError);
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
    int status=0;
    int as=-1;
    rs=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ tree.tree.getSelectionPath().getLastPathComponent().toString() +"' and PR_id="+ tree.ID);
    if(rs.next())
    {status=rs.getInt("AS_collision_detection");
    as=rs.getInt("AS_id");
    }
    query.Close();
    lbAttributes.setVisible(false);
    lbKeys.setVisible(false);
    lbNULLS.setVisible(false);
    lbUniques.setVisible(false);
    lbReferentials.setVisible(false);
    lbChkConsColls.setVisible(false);
    chAttributes.setVisible(true);
    chAttributes.setSelected(true);
    chKeys.setVisible(true);
    chKeys.setSelected(false);
    chUniques.setVisible(true);
    chUniques.setSelected(false);
    chNULLs.setVisible(true);
    chNULLs.setSelected(false);
    chReferentials.setVisible(true);
    chReferentials.setSelected(false);
    cbChkConsCollisions.setVisible(true);
    cbChkConsCollisions.setSelected(false);
    lb1.setText("");
   lb2.setText("");
   lb3.setText("");
   lb4.setText("");
   lb5.setText("");
   lb6.setText("");
    if(status==1)
    {
     lbAttributes.setVisible(true); 
     chAttributes.setVisible(false);
     lb1.setText("success");
    }
    else if(status==2)
    {lbAttributes.setVisible(true); 
     chAttributes.setVisible(false);
     lbKeys.setVisible(true); 
     chKeys.setVisible(false);
     lb1.setText("success");
     lb2.setText("success");
    }
    else if(status==3)
    {
     lbAttributes.setVisible(true); 
     chAttributes.setVisible(false);
     lbKeys.setVisible(true); 
     chKeys.setVisible(false);
     lbUniques.setVisible(true); 
     chUniques.setVisible(false);
     lb1.setText("success");
     lb2.setText("success");
     lb3.setText("success");
    }
    else if(status==4)
    {
     lbAttributes.setVisible(true); 
     chAttributes.setVisible(false);
     lbKeys.setVisible(true); 
     chKeys.setVisible(false);
     lbUniques.setVisible(true); 
     chUniques.setVisible(false);
     lbNULLS.setVisible(true); 
     chNULLs.setVisible(false);
     lb1.setText("success");
     lb2.setText("success");
     lb3.setText("success");
     lb4.setText("success");
     
    }
    else if(status==5)
    {
     lbAttributes.setVisible(true); 
     chAttributes.setVisible(false);
     lbKeys.setVisible(true); 
     chKeys.setVisible(false);

     lbUniques.setVisible(true); 
     chUniques.setVisible(false);
 
     lbNULLS.setVisible(true); 
     chNULLs.setVisible(false);

     lbReferentials.setVisible(true); 
     chReferentials.setVisible(false);
     lb1.setText("success");
     lb2.setText("success");
     lb3.setText("success");
     lb4.setText("success");
     lb5.setText("success");
     
     lbReferentials.setIcon(imageOK);
     lbNULLS.setIcon(imageOK);
    }
    else if(status==6)
    {
         lbAttributes.setVisible(true); 
         chAttributes.setVisible(false);
         lbKeys.setVisible(true); 
         chKeys.setVisible(false);
    
         lbUniques.setVisible(true); 
         chUniques.setVisible(false);
        
         lbNULLS.setVisible(true); 
         chNULLs.setVisible(false);
    
         lbReferentials.setVisible(true); 
         chReferentials.setVisible(false);
         
         lbChkConsColls.setVisible(true); 
         cbChkConsCollisions.setVisible(false);
        
         lb1.setText("success");
         lb2.setText("success");
         lb3.setText("success");
         lb4.setText("success");
         lb5.setText("success");
         lb6.setText("success");
         
         lbReferentials.setIcon(imageOK);
         lbNULLS.setIcon(imageOK);
    }
        
    lb4.setForeground(new Color(0,0,0));
   if(st==-1) 
   {lbAttributes.setIcon(imageError); 
    lbAttributes.setVisible(true); 
    chAttributes.setVisible(false);
    lbKeys.setVisible(true); 
    chKeys.setVisible(false);
    lbKeys.setIcon(imageEmpty);
    lbUniques.setVisible(true); 
    chUniques.setVisible(false);
    lbUniques.setIcon(imageEmpty);
    lbNULLS.setVisible(true); 
    chNULLs.setVisible(false);
    lbNULLS.setIcon(imageEmpty);
    lbReferentials.setVisible(true); 
    chReferentials.setVisible(false);
    lbReferentials.setIcon(imageEmpty); 
    lbChkConsColls.setVisible(true); 
    cbChkConsCollisions.setVisible(false);
    lbChkConsColls.setIcon(imageEmpty);
    lb1.setText("<html><u>collisions detected</u>");
    lb1.setCursor(new Cursor(Cursor.HAND_CURSOR));
    lb1.setForeground(new Color(0,0,255));
   lb2.setText("not performed");
   lb3.setText("not performed");
   lb4.setText("not performed");
   lb5.setText("not performed");
   lb6.setText("not performed");
   }
   if(st==-2) 
   {lbAttributes.setVisible(true); 
     chAttributes.setVisible(false);
     lbKeys.setVisible(true); 
     chKeys.setVisible(false);
    lbKeys.setIcon(imageError); 
    lbUniques.setVisible(true); 
    chUniques.setVisible(false);
    lbUniques.setIcon(imageEmpty);
    lbNULLS.setVisible(true); 
    chNULLs.setVisible(false);
    lbNULLS.setIcon(imageEmpty);
    lbReferentials.setVisible(true); 
    chReferentials.setVisible(false);
    lbReferentials.setIcon(imageEmpty);  
    lbChkConsColls.setVisible(true); 
    cbChkConsCollisions.setVisible(false);
    lbChkConsColls.setIcon(imageEmpty);
    lb1.setText("success");
   lb2.setText("<html><u>collisions detected</u>");
   lb2.setCursor(new Cursor(Cursor.HAND_CURSOR));
   lb2.setForeground(new Color(0,0,255));
   lb3.setText("not performed");
   lb4.setText("not performed");
   lb5.setText("not performed");
   lb6.setText("not performed");
   }
   if(st==-3) 
   {lbAttributes.setVisible(true); 
     chAttributes.setVisible(false);
     lbKeys.setVisible(true); 
     chKeys.setVisible(false);
     lbUniques.setVisible(true); 
     chUniques.setVisible(false);
    lbUniques.setIcon(imageError); 
    lbNULLS.setVisible(true); 
    chNULLs.setVisible(false);
    lbNULLS.setIcon(imageEmpty);
    lbReferentials.setVisible(true); 
    chReferentials.setVisible(false);
    lbReferentials.setIcon(imageEmpty); 
    lbChkConsColls.setVisible(true); 
    cbChkConsCollisions.setVisible(false);
    lbChkConsColls.setIcon(imageEmpty);
    lb1.setText("success");
   lb2.setText("success");
   lb3.setText("<html><u>collisions detected</u>");
   lb3.setCursor(new Cursor(Cursor.HAND_CURSOR));
   lb3.setForeground(new Color(0,0,255));
   lb4.setText("not performed");
   lb5.setText("not performed");
   lb6.setText("not performed");
   }
   if(st==-4) 
   {lbAttributes.setVisible(true); 
     chAttributes.setVisible(false);
     lbKeys.setVisible(true); 
     chKeys.setVisible(false);
     lbUniques.setVisible(true); 
     chUniques.setVisible(false);
     lbNULLS.setVisible(true); 
     chNULLs.setVisible(false);
   lbNULLS.setIcon(imageError); 
    lbReferentials.setVisible(false); 
    chReferentials.setVisible(true);
    lbReferentials.setIcon(imageEmpty);  
    lbChkConsColls.setVisible(true); 
    cbChkConsCollisions.setVisible(false);
    lbChkConsColls.setIcon(imageEmpty);
   lb1.setText("success");
   lb2.setText("success");
   lb3.setText("success");
   lbNULLS.setIcon(imageOK);
   rs=query.select("select * from IISC_COLLISION_LOG where AS_id="+ as +" and PR_id="+ tree.ID+ " and CL_type=3 order by CL_date desc");
   if(rs.next())
   {
    if(rs.getInt("CL_warning")==0)
    {
        lb4.setText("<html><u>collisions detected</u>");
        lbNULLS.setIcon(imageError);
    }
    else
    {
        lb4.setText("<html><u>collisions resolved</u>");
        lbNULLS.setIcon(imageWarning);
    }
   }
   query.Close();
   lb4.setCursor(new Cursor(Cursor.HAND_CURSOR));
   lb4.setForeground(new Color(0,0,255));
   lb5.setText("not performed");
   lb6.setText("not performed");

   }
    if(st==-5) 
   {lbAttributes.setVisible(true); 
     chAttributes.setVisible(false);
     lbKeys.setVisible(true); 
     chKeys.setVisible(false);

     lbUniques.setVisible(true); 
     chUniques.setVisible(false);
 
     lbNULLS.setVisible(true); 
     chNULLs.setVisible(false);
       lbNULLS.setIcon(imageEmpty);
     lbReferentials.setVisible(true); 
     chReferentials.setVisible(false);
   lbReferentials.setIcon(imageError); 
   lbChkConsColls.setVisible(true); 
   cbChkConsCollisions.setVisible(false);
   lbChkConsColls.setIcon(imageEmpty);
   lb1.setText("success");
   lb2.setText("success");
   lb3.setText("success");
   lb4.setText("success");
    rs=query.select("select * from IISC_COLLISION_LOG where AS_id="+ as +" and PR_id="+ tree.ID+ " and CL_type=4 order by CL_date desc");
    if(rs.next())
    {if(rs.getInt("CL_warning")==0)
     lb5.setText("<html><u>collisions detected</u>");
     else
      {lb5.setText("<html><u>success with warnings</u>");
       lbReferentials.setIcon(imageWarning);
      }
    }
    query.Close();
   lb5.setCursor(new Cursor(Cursor.HAND_CURSOR));
   lb5.setForeground(new Color(0,0,255));
    lbNULLS.setIcon(imageOK);
   lb6.setText("not performed");
   }
   
        if(st==-6) {
            lbAttributes.setVisible(true);
            chAttributes.setVisible(false);
            lbKeys.setVisible(true); 
            chKeys.setVisible(false);
            lbUniques.setVisible(true); 
            chUniques.setVisible(false);
            
            lbNULLS.setVisible(true); 
            chNULLs.setVisible(false);
            lbNULLS.setIcon(imageOK);
            lbReferentials.setVisible(true); 
            chReferentials.setVisible(false);
            lbReferentials.setIcon(imageOK);
            lbChkConsColls.setVisible(true);
            cbChkConsCollisions.setVisible(false);
            lbChkConsColls.setIcon(imageError);
            lb1.setText("success");
            lb2.setText("success");
            lb3.setText("success");
            lb4.setText("success");
            lb5.setText("success");
            rs=query.select("select * from IISC_COLLISION_LOG where AS_id="+ as +" and PR_id="+ tree.ID+ " and CL_type=5 order by CL_date desc");
            if(rs.next())
            {if(rs.getInt("CL_warning")==0)
             lb6.setText("<html><u>collisions detected</u>");
             else
              {lb6.setText("<html><u>success with warnings</u>");
               lbChkConsColls.setIcon(imageWarning);
              }
            }
            query.Close();
            lb6.setCursor(new Cursor(Cursor.HAND_CURSOR));
            lb6.setForeground(new Color(0,0,255));
        }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
 }

  private void btnHelp_actionPerformed(ActionEvent e)
  {Help hlp =new  Help((IISFrameMain)getParent(),getTitle(), true, con);
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void save_ActionPerformed(ActionEvent e)
  {try
  {String s=new String(); 
  ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(con);
    JDBCQuery query1=new JDBCQuery(con);
     rs1=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ tree.tree.getLastSelectedPathComponent().toString()  +"'" );
      rs1.next();
      int h=rs1.getInt("AS_id");
      int j=rs1.getInt("PR_id");
       s=rs1.getString("AS_name");
      query.Close(); 

    int status=-1,st=0;
    if(chAttributes.isSelected() && chAttributes.isVisible())status=1;
    if(chKeys.isSelected() && chKeys.isVisible())status=2;
    if(chUniques.isSelected() && chUniques.isVisible())status=3;
    if(chNULLs.isSelected() && chNULLs.isVisible())status=4;
    if(chReferentials.isSelected() && chReferentials.isVisible())status=5;
    if(cbChkConsCollisions.isSelected() && cbChkConsCollisions.isVisible())status=6;
    if(status>-1)
    {
    Synthesys Syn=new Synthesys();
     Syn.pr=tree.ID;
     Syn.as=id;
     Syn.con=tree.con;
     st=Syn.check_subscheme_compatybility(status);
     if(st>0)
     status=0+st;
     else
     status=0-st;
    }
     
    if(status>-1)
  {  query.update("update IISC_APP_SYSTEM set AS_collision_detection="+ st +" where AS_id="+ id +" and PR_id="+ tree.ID);
    setCheckboxes(st);
    tree.setVisible(false);
     tree.pravi_drvo();
    tree.setVisible(true);
     tree.select_node(s,"Application Systems"); 
     if(st<0)
     { 
         rs=query.select("select * from IISC_COLLISION_LOG where AS_id="+ id +" and PR_id="+ tree.ID+ " order by CL_date desc");
    if(rs.next())
    {if(rs.getInt("CL_warning")==1)
     JOptionPane.showMessageDialog(null, "<html><center>There were warnings.<br> Please check DB Schema collision reports.", "DB Schema Analysis" , JOptionPane.WARNING_MESSAGE);
     else
      JOptionPane.showMessageDialog(null, "<html><center>Collisions detected!<br> Please check DB Schema collision reports.", "DB Schema Analysis" , JOptionPane.WARNING_MESSAGE);
    }
    query.Close();
     
     
     }
  }
   }
  catch(SQLException ae)
    {
      ae.printStackTrace();
    }    
  }

  private void close_ActionPerformed(ActionEvent e)
  {
  dispose();
  }

  private void setChecks(ActionEvent e)
  {
  if(!((JCheckBox)e.getSource()).getText().equals("") )
  {if(cbChkConsCollisions.isSelected() && chReferentials.isVisible())
   chReferentials.setSelected(true);
   if(chReferentials.isSelected() && chNULLs.isVisible())
   chNULLs.setSelected(true);
   if(chNULLs.isSelected() && chUniques.isVisible())
   chUniques.setSelected(true);
   if(chUniques.isSelected() && chKeys.isVisible())
   chKeys.setSelected(true);
   if(chKeys.isSelected() && chAttributes.isVisible())
   chAttributes.setSelected(true);
  }
  else{ 
    chAttributes.setSelected(false);
    chKeys.setSelected(false);
    chUniques.setSelected(false);
    chNULLs.setSelected(false);
    chReferentials.setSelected(false);
    cbChkConsCollisions.setSelected(false);
  }
   
  }

  private void lb1_mouseClicked(MouseEvent e)
  {
  if (lb1.getText().equals("<html><u>collisions detected</u>")||lb1.getText().equals("<html><u>collisions resolved</u>"))
  openReport();
  }
  private void openReport()
  {
  try
  { 
  ResultSet rs ;
    JDBCQuery query=new JDBCQuery(con);
 
  int i=-1;
  rs=query.select("select * from IISC_COLLISION_LOG  where   AS_id=" + id + "  and PR_id="+tree.ID+ " order by CL_id desc");
  if(rs.next())
  i=rs.getInt(1);
  Report rep=new Report((IISFrameMain)getParent(),"",true,con,i);
  Settings.Center(rep);
  rep.setVisible(true);
  query.Close(); 
   }
  catch(SQLException ae)
    {
      ae.printStackTrace();
    }   
  }

  private void lb2_mouseClicked(MouseEvent e)
  { if (lb2.getText().equals("<html><u>collisions detected</u>")||lb2.getText().equals("<html><u>collisions resolved</u>"))
  openReport();
  }

  private void lb3_mouseClicked(MouseEvent e)
  { if (lb3.getText().equals("<html><u>collisions detected</u>")||lb3.getText().equals("<html><u>collisions resolved</u>"))
  openReport();
  }

  private void lb4_mouseClicked(MouseEvent e)
  { if (lb4.getText().equals("<html><u>collisions detected</u>")||lb4.getText().equals("<html><u>collisions resolved</u>"))
  openReport();
  }

  private void lb5_mouseClicked(MouseEvent e)
  { if (lb5.getText().equals("<html><u>collisions detected</u>")||lb5.getText().equals("<html><u>collisions resolved</u>")|| lb5.getText().equals("<html><u>success with warnings</u>"))
  openReport();
  }
  
    private void lb6_mouseClicked(MouseEvent e)
    { if (lb6.getText().equals("<html><u>collisions detected</u>")||lb6.getText().equals("<html><u>collisions resolved</u>")|| lb6.getText().equals("<html><u>success with warnings</u>"))
    openReport();
    }
}