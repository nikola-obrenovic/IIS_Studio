package iisc;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.MouseAdapter;

import java.io.*;

import java.sql.*;
import java.sql.SQLException;


import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.*;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.JRadioButton;

import javax.swing.JSeparator;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import java.lang.Process;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;



public class GenerateSQL extends JDialog 
{
  private Date d = new Date();
  public int id; 
  private boolean DDL=false;
  private DefaultListModel modellst1=new DefaultListModel();
  private DefaultListModel modellst2=new DefaultListModel();
  private DefaultListModel modellst3=new DefaultListModel();
  private JToolBar jToolBar1 = new JToolBar();
  private JButton btnPrev = new JButton();
  String[] DBMS = { "ANSI SQL-2003", "MS SQL Server 2000", "Oracle Server 9i/10g", "MS SQL Server 2008"};
  private JComboBox cmbDBMS = new JComboBox(DBMS);
  private JButton btnNext = new JButton();
  private JButton btnLast = new JButton();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JTextField FolderIme = new JTextField();
  private JTextField FileIme = new JTextField();
  private JButton ChooseF = new JButton();
  private JButton ChoosFile = new JButton();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private JPanel jPOptions = new JPanel();
  private JButton btnOK = new JButton();
  private JButton btnClose = new JButton();
  private JButton btnHelp = new JButton();
  private JLabel jLabel3 = new JLabel();
  private Connection con;
  public PTree tree; 
  public int PrId;
  private int AsId;
  private boolean Pogled = false;
  private JButton btnFirst = new JButton();
  private JButton btnApply = new JButton();
  private int[] SemeRelId; 
  private String[] SemeRelName;
  private int[] Constraint;
  private JRadioButton rbIzmena = new JRadioButton();
  private JRadioButton rbKreiranje = new JRadioButton();
  private JButton RemAll1 = new JButton();
  private JButton Rem1 = new JButton();
  private JButton Add1 = new JButton();
  private JButton AddAll1 = new JButton();
  private JList List1 = new JList();
  private JList List2 = new JList();
  private JButton btnDown1 = new JButton();
  private JButton btnUp1 = new JButton();
  private JScrollPane jScrollPane3 = new JScrollPane();
  private JScrollPane jScrollPane4 = new JScrollPane();
  private JRadioButton rbScript = new JRadioButton();
  private JRadioButton rbDatabase = new JRadioButton();
  private JComboBox cmbDSN = new JComboBox(DBMS);
  private JLabel LSource = new JLabel();
  private JSeparator jSeparator1 = new JSeparator();
  private ButtonGroup bgrp1=new ButtonGroup();
  private ButtonGroup bgrp2=new ButtonGroup();
  private ButtonGroup bgrp3=new ButtonGroup();
  private ButtonGroup bgrp4=new ButtonGroup();
  private ButtonGroup bgrCheckCon=new ButtonGroup();
  private JLabel jLabel5 = new JLabel();
  private JLabel jLabel6 = new JLabel();
  private JCheckBox cbOneFile = new JCheckBox();
  private JCheckBox chIndeksi = new JCheckBox();
  private JCheckBox chConstraints = new JCheckBox();
  private JCheckBox chTrigeri = new JCheckBox();
  private JCheckBox ChPK = new JCheckBox();
  private JCheckBox ChUK = new JCheckBox();
  private JCheckBox ChFK = new JCheckBox();
  private String ImeBaze;
  private JTextField tfImeBaze = new JTextField();
  private JLabel jLabel7 = new JLabel();
  private JCheckBox ChComent = new JCheckBox();
  private JSeparator jSeparator2 = new JSeparator();
  private JRadioButton rbView = new JRadioButton();
  private JRadioButton rbProcedure = new JRadioButton();
  private JLabel LInv = new JLabel();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  private ImageIcon iconUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
  private ImageIcon iconDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
  
  private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
  private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
  private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
  private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
  private JPanel Modify = new JPanel();
  private JRadioButton rbCreate = new JRadioButton();
  private JRadioButton rbAlter = new JRadioButton();
  private JLabel jLabel10 = new JLabel();
  private JLabel jLabel9 = new JLabel();
  private JLabel jLabel11 = new JLabel();
  private JTextField UserNameAlter = new JTextField();
  private JTextField UserNameDatabase = new JTextField();
  private JPasswordField PasswordDatabase = new JPasswordField();
  private JTextField HostAlter = new JTextField();
  private JTextField HostDatabase = new JTextField();
  private JLabel LUserName = new JLabel();
  private JLabel LPassword = new JLabel();
  private JLabel LHost = new JLabel();
  private JRadioButton rbODBC = new JRadioButton();
  private JLabel HostL = new JLabel();
  private JLabel PasswordL = new JLabel();
  private JLabel UserNameL = new JLabel();
  
  private String driverJdbcOdbc = "sun.jdbc.odbc.JdbcOdbcDriver";
  private String url =new String();
  private String username =new String();
  private String password =new String();  
  private Connection connection = null;
  private ODBCList o=new ODBCList();
    private JTextField ImeBazeAlter = new JTextField();
  private JLabel DBSchemaName = new JLabel();
  private JPasswordField PasswordAlter = new JPasswordField();
  private JPanel Sequence = new JPanel();
  public SequenceTableModel stm;
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JList List3 = new JList();
  private JTable Tabela = new JTable();
  private JLabel jLabel8 = new JLabel();
  private JLabel jLabel4 = new JLabel();
     
  private boolean ImaSekvencer = false;
  private int IdSemeSeq;
    private JRadioButton rbDNFImpl = new JRadioButton();
    private JRadioButton rbCNFImpl = new JRadioButton();
    private JLabel jLabel12 = new JLabel();


    public GenerateSQL()
  {
    this(null, "", false);
  }

  /**
   * 
   * @param parent
   * @param title
   * @param modal
   */
  public GenerateSQL(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
  public GenerateSQL(Frame parent, String title, boolean modal,Connection conn)
  {
    super(parent, title, modal);
    try
    {      
      con=conn;       
      jbInit();      
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
  
   public GenerateSQL(IISFrameMain parent, String title, boolean modal,Connection conn, PTree tr )
  {
    super((Frame) parent, title, modal);
    try
    { con=conn;         
      JDBCQuery query=new JDBCQuery(con);
      ResultSet rs;      
      tree=tr; 
      PrId=tree.ID;
      if(((PTree)(parent.desktop.getSelectedFrame())).tree.getSelectionPath().getLastPathComponent().toString().equals("Relation Schemes"))
          rs=query.select("select * from IISC_App_System where AS_name='" + ((PTree)(parent.desktop.getSelectedFrame())).tree.getSelectionPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+((PTree)(parent.desktop.getSelectedFrame())).ID);
      else
          rs=query.select("select * from IISC_App_System where AS_name='" + ((PTree)(parent.desktop.getSelectedFrame())).tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+((PTree)(parent.desktop.getSelectedFrame())).ID);

      if(rs.next())
          AsId=rs.getInt("AS_id");
      query.Close();          
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    
  }
  
  private void jbInit() throws Exception
  {   
    stm= new SequenceTableModel(con);
    Tabela=new JTable(stm);
    
    //Tabela.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);    
    Tabela.setRowSelectionAllowed(true);
    Tabela.setColumnSelectionAllowed(true);
    Tabela.setGridColor(new Color(0,0,0));
    Tabela.setBackground(Color.white);
    Tabela.setAutoResizeMode(0);
    Tabela.setAutoscrolls(true);
    Tabela.getTableHeader().setReorderingAllowed(false);
    Tabela.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    Tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    Tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    
    cmbDSN = new JComboBox(o.odbc.toArray());      
    Tabela.addFocusListener(new FocusAdapter()
      {
        public void focusLost(FocusEvent e)
        {
          Tabela_focusLost(e);
        }

        public void focusGained(FocusEvent e)
        {
          Tabela_focusGained(e);
        }
      });
    jLabel8.setText("Generate");
    jLabel8.setBounds(new Rectangle(25, 20, 70, 15));
    jLabel8.setFont(new Font("SansSerif", 0, 11));
    jLabel4.setText("Set Sequence");
    jLabel4.setBounds(new Rectangle(210, 20, 90, 15));
        rbDNFImpl.setText("Disjunctive Normal Form");
        rbDNFImpl.setBounds(new Rectangle(50, 140, 165, 20));
        rbDNFImpl.setFont(new Font("SansSerif", 0, 11));
        rbDNFImpl.setSelected(true);
        rbCNFImpl.setText("Conjunctive Normal Form");
        rbCNFImpl.setBounds(new Rectangle(50, 160, 165, 20));
        rbCNFImpl.setFont(new Font("SansSerif", 0, 11));
        jLabel12.setText("Generate CHECK constraints as:");
        jLabel12.setBounds(new Rectangle(30, 120, 225, 15));
        jLabel12.setFont(new Font("SansSerif", 0, 11));
        ChoosFile.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          ChoosFile_actionPerformed(e);
        }
      });
    this.setSize(new Dimension(519, 511));
    this.getContentPane().setLayout(null);
    this.setTitle("Generate Server Model Definition");
    jToolBar1.setBounds(new Rectangle(10, 0, 495, 75));
    jToolBar1.setLayout(null);
    jToolBar1.setFloatable(false);
    btnPrev.setBounds(new Rectangle(110, 15, 25, 20));
    btnPrev.setIcon(iconPrev);
    btnPrev.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnPrev_actionPerformed(e);
        }
      });
    cmbDBMS.setBounds(new Rectangle(150, 15, 230, 20));
    cmbDBMS.setFont(new Font("SansSerif", 0, 11));
    cmbDBMS.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          cmbDBMS_actionPerformed(e);
        }
      });
       
    btnNext.setBounds(new Rectangle(395, 15, 25, 20));
    btnNext.setIcon(iconNext);
    btnNext.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnNext_actionPerformed(e);
        }
      });
    btnLast.setBounds(new Rectangle(420, 15, 25, 20));    
    btnLast.setIcon(iconNextt);
    btnLast.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnLast_actionPerformed(e);
        }
      });
    jLabel1.setText("Directory:");
    jLabel1.setBounds(new Rectangle(35, 240, 50, 15));
    jLabel1.setFont(new Font("SansSerif", 0, 11));
    jLabel2.setText("File Prefix:");
    jLabel2.setBounds(new Rectangle(35, 265, 50, 15));
    jLabel2.setFont(new Font("SansSerif", 0, 11));
    FolderIme.setBounds(new Rectangle(130, 235, 275, 20));
    FolderIme.setText("C:\\Scripts");
    FolderIme.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          FolderIme_actionPerformed(e);
        }
      });
    FileIme.setBounds(new Rectangle(130, 260, 275, 20));
    FileIme.setText("script");
    FileIme.setFont(new Font("SansSerif", 0, 11));
    ChooseF.setBounds(new Rectangle(415, 235, 30, 20));
    ChooseF.setText("...");
    ChooseF.setSize(new Dimension(30, 20));
    ChooseF.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          ChooseF_actionPerformed(e);
        }
      });
    ChoosFile.setBounds(new Rectangle(415, 260, 30, 20));    
    ChoosFile.setText("...");
    ChoosFile.setSize(new Dimension(30, 20));
    jTabbedPane1.setBounds(new Rectangle(15, 90, 485, 345));
    jPanel1.setLayout(null);
    jPanel1.setFont(new Font("SansSerif", 0, 11));
    jPanel2.setFont(new Font("SansSerif", 0, 11));
    jPanel2.setLayout(null);
    jPOptions.setFont(new Font("SansSerif", 0, 11));
    jPOptions.setLayout(null);
    btnOK.setText("Generate");
    btnOK.setBounds(new Rectangle(275, 440, 80, 30));
    btnOK.setFont(new Font("SansSerif", 0, 11));
    btnOK.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnOK_actionPerformed(e);
        }
      });
    btnClose.setText("Cancel");
    btnClose.setBounds(new Rectangle(365, 440, 80, 30));    
    btnClose.setFont(new Font("SansSerif", 0, 11));
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnClose_actionPerformed(e);
        }
      });
    btnHelp.setBounds(new Rectangle(455, 440, 35, 30));    
    btnHelp.setIcon(imageHelp);    
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    jLabel3.setText("DBMS:");
    jLabel3.setBounds(new Rectangle(20, 15, 40, 15));
    jLabel3.setFont(new Font("SansSerif", 0, 11));
    btnFirst.setBounds(new Rectangle(85, 15, 25, 20));
    btnFirst.setIcon(iconPrevv);
    btnFirst.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnFirst_actionPerformed(e);
        }
      });
    btnApply.setText("Check ");
    btnApply.setBounds(new Rectangle(185, 440, 80, 30));
    btnApply.setFont(new Font("SansSerif", 0, 11));
    btnApply.setSize(new Dimension(80, 30));
    btnApply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnApply_actionPerformed(e);
        }
      });
    
    rbIzmena.setText("Regenerate Database");
    rbIzmena.setBounds(new Rectangle(260, 45, 150, 20));
    rbIzmena.setFont(new Font("SansSerif", 0, 11));
    rbIzmena.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rbIzmena_actionPerformed(e);
        }        
      });
    rbKreiranje.setText("Generate New Database");
    rbKreiranje.setSelected(true);
    rbKreiranje.setBounds(new Rectangle(80, 45, 150, 20));
    rbKreiranje.setFont(new Font("SansSerif", 0, 11));
    rbKreiranje.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rbKreiranje_actionPerformed(e);
        }
      });
    rbKreiranje.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          DisableEnable_itemStateChanged(e);
        }
      });    
    
    RemAll1.setText("<<");
    RemAll1.setBounds(new Rectangle(200, 230, 50, 30));
    RemAll1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          RemAll_actionPerformed(e);
        }        
      });
    Rem1.setText("<");
    Rem1.setBounds(new Rectangle(200, 175, 50, 30));
    Rem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          Rem_actionPerformed(e);
        }
      });
    Add1.setText(">");
    Add1.setBounds(new Rectangle(200, 140, 50, 30));
    Add1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          Add_actionPerformed(e);
        }
      });
    AddAll1.setText(">>");
    AddAll1.setBounds(new Rectangle(200, 85, 50, 30));
    AddAll1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          AddAll_actionPerformed(e);
        }
      });
    List1.setModel(modellst1);
    List1.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          List1_mouseClicked(e);
        }
      });
    List2.setModel(modellst2);
    List2.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          List2_mouseClicked(e);
        }
      });
    btnDown1.setBounds(new Rectangle(440, 275, 25, 20));
    btnDown1.setIcon(iconDown);
    btnDown1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDown_actionPerformed(e);
        }
      });
    btnUp1.setBounds(new Rectangle(440, 50, 25, 20));
    btnUp1.setIcon(iconUp);
    btnUp1.setMaximumSize(new Dimension(60, 60));
    btnUp1.setMinimumSize(new Dimension(45, 21));
    btnUp1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnUp_actionPerformed(e);
        }
      });
    jScrollPane3.setBounds(new Rectangle(15, 50, 165, 245));
    jScrollPane4.setBounds(new Rectangle(270, 50, 165, 245));
    rbScript.setText("DDL Files Only");
    rbScript.setBounds(new Rectangle(30, 10, 95, 20));
    rbScript.setSelected(true);
    rbScript.setFont(new Font("SansSerif", 0, 11));
    rbScript.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rbScript_actionPerformed(e);
        }
      });
    rbDatabase.setText("Database Source");
    rbDatabase.setBounds(new Rectangle(30, 60, 110, 20));
    rbDatabase.setFont(new Font("SansSerif", 0, 11));
    rbDatabase.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rbDatabase_actionPerformed(e);
        }
      });
    
    cmbDSN.setBounds(new Rectangle(135, 190, 145, 20));
    cmbDSN.setFont(new Font("SansSerif", 0, 11));
    LSource.setText("Source:");
    LSource.setBounds(new Rectangle(60, 195, 40, 15));
    LSource.setFont(new Font("SansSerif", 0, 11));
    jSeparator1.setBounds(new Rectangle(10, 220, 460, 2));
    jLabel5.setText("Don't Generate");
    jLabel5.setBounds(new Rectangle(15, 20, 90, 15));
    jLabel5.setFont(new Font("SansSerif", 0, 11));
    jLabel6.setText("Generate");
    jLabel6.setBounds(new Rectangle(270, 20, 60, 15));
    jLabel6.setFont(new Font("SansSerif", 0, 11));
    cbOneFile.setText("One File Only");
    cbOneFile.setBounds(new Rectangle(60, 35, 100, 20));
    cbOneFile.setSelected(true);
    cbOneFile.setFont(new Font("SansSerif", 0, 11));
    chIndeksi.setText("Generate Indexes For:");
    chIndeksi.setBounds(new Rectangle(30, 25, 135, 20));
    chIndeksi.setSelected(true);
    chIndeksi.setFont(new Font("SansSerif", 0, 11));
    chIndeksi.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chIndeksi_actionPerformed(e);
        }
      });
    chConstraints.setText("Generate SQL CONSTRAINT Clauses");
    chConstraints.setBounds(new Rectangle(30, 85, 205, 20));
    chConstraints.setSelected(true);
    chConstraints.setFont(new Font("SansSerif", 0, 11));
    chTrigeri.setText("Generate Triggers");
    chTrigeri.setBounds(new Rectangle(30, 190, 130, 15));
    chTrigeri.setSelected(true);
    chTrigeri.setFont(new Font("SansSerif", 0, 11));
    chTrigeri.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chTrigeri_actionPerformed(e);
        }
      });
    ChPK.setText("Primary Keys");
    ChPK.setBounds(new Rectangle(55, 50, 100, 20));
    ChPK.setSelected(true);
    ChPK.setFont(new Font("SansSerif", 0, 11));
    ChUK.setText("Alternate Keys");
    ChUK.setBounds(new Rectangle(165, 50, 100, 20));
    ChUK.setFont(new Font("SansSerif", 0, 11));
    ChUK.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          ChUK_actionPerformed(e);
        }
      });
    ChFK.setText("Foreign Keys");
    ChFK.setBounds(new Rectangle(280, 50, 95, 20));
    ChFK.setFont(new Font("SansSerif", 0, 11));
    
    tfImeBaze.setBounds(new Rectangle(130, 285, 275, 20));
    tfImeBaze.addFocusListener(new FocusAdapter()
      {
        public void focusLost(FocusEvent e)
        {
          tfImeBaze_focusLost(e);
        }
      });
    jLabel7.setText("DB Schema Name:");
    jLabel7.setBounds(new Rectangle(35, 290, 90, 15));
    jLabel7.setFont(new Font("SansSerif", 0, 11));
    ChComent.setText("Include Comments");
    ChComent.setBounds(new Rectangle(30, 280, 130, 20));
    ChComent.setFont(new Font("SansSerif", 0, 11));
    jSeparator2.setBounds(new Rectangle(135, 15, 0, 35));
    rbView.setText("Using Views");
    rbView.setSelected(true);
    rbView.setBounds(new Rectangle(70, 230, 105, 20));
    rbView.setFont(new Font("SansSerif", 0, 11));
    rbProcedure.setText("Using Stored Procedures");
    rbProcedure.setBounds(new Rectangle(70, 250, 170, 20));
    rbProcedure.setFont(new Font("SansSerif", 0, 11));
    LInv.setText("Implementation Method for Inverse Referential Integrities:");
    LInv.setBounds(new Rectangle(55, 210, 340, 15));
    LInv.setFont(new Font("SansSerif", 0, 11));
    Modify.setLayout(null);
    rbCreate.setText("Always use create statements");
    rbCreate.setBounds(new Rectangle(25, 50, 175, 20));
    rbCreate.setFont(new Font("SansSerif", 0, 11));
    rbCreate.setSelected(true);
    rbCreate.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rbCreate_actionPerformed(e);
        }
      });
    rbAlter.setText("Use alter statements when possible");
    rbAlter.setBounds(new Rectangle(25, 85, 205, 20));
    rbAlter.setFont(new Font("SansSerif", 0, 11));
    rbAlter.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rbAlter_actionPerformed(e);
        }
      });
    jLabel10.setText("Table");
    jLabel10.setBounds(new Rectangle(20, 25, 40, 14));
    jLabel10.setFont(new Font("SansSerif", 0, 11));
    jLabel9.setText("jLabel9");
    jLabel9.setBounds(new Rectangle(30, 0, 35, 15));
    jLabel11.setText("Method:");
    jLabel11.setBounds(new Rectangle(20, 50, 40, 14));
    UserNameAlter.setBounds(new Rectangle(140, 150, 170, 20));
    UserNameAlter.addFocusListener(new FocusAdapter()
      {
        public void focusLost(FocusEvent e)
        {
          UserNameAlter_focusLost(e);
        }
      });
    UserNameDatabase.setBounds(new Rectangle(135, 110, 145, 20));
    UserNameDatabase.addFocusListener(new FocusAdapter()
      {
        public void focusLost(FocusEvent e)
        {
          UserNameDatabase_focusLost(e);
        }
      });
    PasswordDatabase.setBounds(new Rectangle(135, 135, 145, 20));
    PasswordDatabase.addFocusListener(new FocusAdapter()
      {
        public void focusLost(FocusEvent e)
        {
          PasswordDatabase_focusLost(e);
        }
      });
    HostAlter.setBounds(new Rectangle(140, 120, 170, 20));
    HostAlter.addFocusListener(new FocusAdapter()
      {
        public void focusLost(FocusEvent e)
        {
          HostAlter_focusLost(e);
        }
      });
    HostDatabase.setBounds(new Rectangle(135, 85, 145, 20));
    HostDatabase.addFocusListener(new FocusAdapter()
      {
        public void focusLost(FocusEvent e)
        {
          HostDatabase_focusLost(e);
        }
      });
    LUserName.setText("Username:");
    LUserName.setBounds(new Rectangle(60, 115, 60, 15));
    LPassword.setText("Password:");
    LPassword.setBounds(new Rectangle(60, 140, 65, 15));
    LHost.setText("Host:");
    LHost.setBounds(new Rectangle(60, 90, 40, 14));
    rbODBC.setText("ODBC Source");
    rbODBC.setBounds(new Rectangle(30, 165, 110, 20));
    rbODBC.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rbODBC_actionPerformed(e);
        }
      });
    
    HostL.setText("Host:");
    HostL.setBounds(new Rectangle(45, 125, 40, 15));
    PasswordL.setText("Password:");
    PasswordL.setBounds(new Rectangle(45, 185, 55, 15));
    UserNameL.setText("Username:");
    UserNameL.setBounds(new Rectangle(45, 155, 60, 15));
        ImeBazeAlter.setBounds(new Rectangle(140, 210, 170, 20));
    ImeBazeAlter.addFocusListener(new FocusAdapter()
      {
        public void focusLost(FocusEvent e)
        {
          ImeBazeAlter_focusLost(e);
        }
      });
    DBSchemaName.setText("DB Schema Name:");
    DBSchemaName.setBounds(new Rectangle(45, 215, 90, 15));
    DBSchemaName.setFont(new Font("SansSerif", 0, 11));
    PasswordAlter.setBounds(new Rectangle(140, 180, 170, 20));
    Sequence.setLayout(null);
    Sequence.addFocusListener(new FocusAdapter()
      {
        public void focusGained(FocusEvent e)
        {
          Sequence_focusGained(e);
        }
        
      });
    
    jScrollPane1.setBounds(new Rectangle(20, 50, 163, 243));
    jScrollPane2.setBounds(new Rectangle(210, 50, 255, 243));
    
    List3.setModel(modellst3);
    
    List3.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          List3_mouseClicked(e);
        }
      });


        jToolBar1.add(jLabel11, null);
    jToolBar1.add(btnFirst, null);
    jToolBar1.add(jLabel3, null);
    jToolBar1.add(btnLast, null);
    jToolBar1.add(btnNext, null);
    jToolBar1.add(cmbDBMS, null);
    jToolBar1.add(btnPrev, null);
    jToolBar1.add(rbKreiranje, null);
    jToolBar1.add(rbIzmena, null);
    jPanel1.add(rbODBC, null);
    jPanel1.add(LHost, null);
    jPanel1.add(LPassword, null);
    jPanel1.add(LUserName, null);
    jPanel1.add(HostDatabase, null);
    jPanel1.add(PasswordDatabase, null);
    jPanel1.add(UserNameDatabase, null);
    jPanel1.add(jSeparator2, null);
    jPanel1.add(jLabel7, null);
    jPanel1.add(tfImeBaze, null);
    jPanel1.add(cbOneFile, null);
    jPanel1.add(jSeparator1, null);
    jPanel1.add(LSource, null);
    jPanel1.add(cmbDSN, null);
    jPanel1.add(rbDatabase, null);
    jPanel1.add(rbScript, null);
    jPanel1.add(FolderIme, null);
    jPanel1.add(ChoosFile, null);
    jPanel1.add(ChooseF, null);
    jPanel1.add(FileIme, null);
    jPanel1.add(jLabel2, null);
    jPanel1.add(jLabel1, null);
    jPanel2.add(jLabel6, null);
    jPanel2.add(jLabel5, null);
    jScrollPane4.getViewport().add(List2, null);
    jPanel2.add(jScrollPane4, null);
    jScrollPane3.getViewport().add(List1, null);
    jPanel2.add(jScrollPane3, null);
    jPanel2.add(btnUp1, null);
    jPanel2.add(btnDown1, null);
    jPanel2.add(AddAll1, null);
    jPanel2.add(Add1, null);
    jPanel2.add(Rem1, null);
    jPanel2.add(RemAll1, null);
    jTabbedPane1.addTab("Target", jPanel1);
    jTabbedPane1.addTab("Selection", jPanel2);
    jTabbedPane1.addTab("Options", jPOptions);
    jTabbedPane1.addTab("Modify", Modify);
    jScrollPane2.getViewport().add(Tabela, null);
    Sequence.add(jLabel4, null);
    Sequence.add(jLabel8, null);
    Sequence.add(jScrollPane2, null);
    jScrollPane1.getViewport().add(List3, null);
    Sequence.add(jScrollPane1, null);
    jTabbedPane1.addTab("Sequence", Sequence);
    Modify.add(PasswordAlter, null);
    Modify.add(DBSchemaName, null);
    Modify.add(ImeBazeAlter, null);
    Modify.add(UserNameL, null);
    Modify.add(PasswordL, null);
    Modify.add(HostL, null);
    Modify.add(HostAlter, null);
    Modify.add(UserNameAlter, null);
    Modify.add(jLabel10, null);
    Modify.add(rbCreate, null);
    Modify.add(rbAlter, null);
        jPOptions.add(jLabel12, null);
        jPOptions.add(rbCNFImpl, null);
        jPOptions.add(rbDNFImpl, null);
        jPOptions.add(LInv, null);
        jPOptions.add(rbProcedure, null);
    jPOptions.add(rbView, null);
    jPOptions.add(ChComent, null);
        jPOptions.add(ChFK, null);
        jPOptions.add(ChUK, null);
        jPOptions.add(ChPK, null);
        jPOptions.add(chTrigeri, null);
        jPOptions.add(chConstraints, null);
        jPOptions.add(chIndeksi, null);
    this.getContentPane().add(btnApply, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnClose, null);
    this.getContentPane().add(btnOK, null);
    this.getContentPane().add(jTabbedPane1, null);
    this.getContentPane().add(jToolBar1, null);
    this.getContentPane().add(jLabel9, null);
    bgrp1.add(rbKreiranje);
    bgrp1.add(rbIzmena);
    bgrp2.add(rbScript);
    bgrp2.add(rbDatabase);
    bgrp2.add(rbODBC);
    bgrp3.add(rbView);
    bgrp3.add(rbProcedure);
    bgrp4.add(rbCreate);
    bgrp4.add(rbAlter);    
    bgrCheckCon.add(rbDNFImpl);
    bgrCheckCon.add(rbCNFImpl);
    inicijalizacija();    
    /*
    ChoosFile.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          ChoosFile_actionPerformed(e);
        }
      });
    */
       
    //Tabela.setEditingColumn(0); //getTableHeader();
    //Tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    //InicijalizujKolonu(Tabela.getColumnModel().getColumn(6));
    InicijalizujTabelu(0);  
    
  }
  private String NazivTabele(int IdTabele)
  {
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    String ret="";
    String upit = "select RS_name from IISC_RELATION_SCHEME where PR_id="+PrId+" and AS_id=" + AsId+ " and RS_id="+IdTabele;     
    try{
      rs=query.select(upit);
      if (rs.next())
      {
        ret= rs.getString("RS_name");
      }      
    }
    catch(Exception ex){     
        ex.printStackTrace();
      }    
      return ret;
  }
  
  private String NazivAtributa(int IdAtributa)
  {
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;
    String ret="";
    String upit = "select Att_mnem from IISC_ATTRIBUTE where IISC_ATTRIBUTE.PR_id="+PrId+" and IISC_ATTRIBUTE.Att_id="+IdAtributa;     
    try{
      rs=query.select(upit);
      if (rs.next())
      {
         ret=rs.getString("Att_mnem");
      }      
    }
    catch(Exception ex){     
        ex.printStackTrace();
      }    
      return ret;
  }
   private int NadjiIdSemeRelacije(String ImeSemeRel) 
   {
     ResultSet rs2;
    int idsema=0;
    JDBCQuery query2=new JDBCQuery(con);
    String upit = "select RS_id from IISC_RELATION_SCHEME where PR_ID=" + PrId + "and AS_ID=" + AsId + " and RS_name='" + ImeSemeRel+ "'";
      try
      {
        rs2=query2.select(upit);
        if (rs2.next())
        {
          idsema=rs2.getInt("RS_id");
        }
        query2.Close();
      } catch(Exception e){    
        e.printStackTrace();        
      } 
      return idsema;        
   }
   
    private int NadjiIdAtributa(String ImeAtr) 
   {
      ResultSet rs2;
      int idatr=0;
      JDBCQuery query2=new JDBCQuery(con);
      String upit = "select Att_id from IISC_ATTRIBUTE where PR_ID=" + PrId + " and Att_mnem='" + ImeAtr+"'";
      try
      {
        rs2=query2.select(upit);
        if (rs2.next())
        {
          idatr=rs2.getInt("Att_id");
        }
        query2.Close();
      } catch(Exception e){    
        e.printStackTrace();        
      } 
      return idatr;        
   }
   
   private void InicijalizujTabelu(int RbrEl) {
    ResultSet rs2;
    int idsema=0;
    JDBCQuery query2=new JDBCQuery(con);
    
    
    if(List3.getModel().getSize()>0)
    {
      Object SemeRel=List3.getModel().getElementAt(RbrEl);  
      String upit = "select RS_id from IISC_RELATION_SCHEME where PR_ID=" + PrId + "and AS_ID=" + AsId + " and RS_name='" + SemeRel.toString()+ "'";
      try
      {
        rs2=query2.select(upit);
        if (rs2.next())
        {
          idsema=rs2.getInt("RS_id");
        }
        query2.Close();
      } catch(Exception e){    
        e.printStackTrace();
      }       
      stm.setQuerySequ(idsema,PrId,AsId,cmbDBMS.getSelectedItem().toString().trim());
    }    
     
  }
  
  private void inicijalizacija()
  {
    
    String[] SemeRelNameList = setAtt();
    modellst1.removeAllElements();
    modellst2.removeAllElements();
    for(int j=0;j<SemeRelNameList.length;j++)
    {       
      modellst1.addElement(SemeRelNameList[j]); 
    }  
    
    List1.setSelectedIndex(0); 
    
    Modify.setEnabled(false);    
    
    UserNameAlter.setEnabled(false);
    PasswordAlter.setEnabled(false);
    DBSchemaName.setEnabled(false);
    ImeBazeAlter.setEnabled(false);
    HostAlter.setEnabled(false);
    UserNameL.setEnabled(false);
    PasswordL.setEnabled(false);
    HostL.setEnabled(false);
    //List1.setListData(setAtt());
    //jTabbedPane1.setSelectedIndex(0);
    jTabbedPane1.setEnabledAt(3,false); 
    jTabbedPane1.setEnabledAt(4,false);
    UserNameDatabase.setEnabled(false);
    PasswordDatabase.setEnabled(false);
    HostDatabase.setEnabled(false);
    cmbDSN.setEnabled(false);
    LUserName.setEnabled(false);
    LPassword.setEnabled(false);
    LHost.setEnabled(false);
    LSource.setEnabled(false);
    
    String s=cmbDBMS.getSelectedItem().toString().trim();
    if (s.compareTo("MS SQL Server 2000")==0) 
    {
       ImeBazeAlter.setEnabled(true); 
    }else
    {
      ImeBazeAlter.setEnabled(false);
    }
  }
  
  private String[] setAtt()
  {
  try {    
   JDBCQuery query = new JDBCQuery(con);     
   ResultSet rs;   
   rs=query.select("select count(*) from IISC_RELATION_SCHEME where PR_ID=" + PrId + " and AS_ID=" + AsId);
   rs.next();
   int j=rs.getInt(1);
   query.Close();
       
   String upit = "select RS_name, RS_id from IISC_RELATION_SCHEME where PR_ID=" + PrId + "and AS_ID=" + AsId;
   String[] SemeRelNameL=query.selectArraySA(upit,j,1);   
   query.Close();    
   return SemeRelNameL;
  }
 catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          //System.err.println(ev);
                          return null;
                          }
 
  }
  private void RemAll_actionPerformed(ActionEvent e)
  {
    JDBCQuery query = new JDBCQuery(con);   
    //List2.setListData(new String[0]);  
    String[] SemeRelNameL = setAtt();
    //List1.setListData(SemeRelNameL); 
    
    modellst1.removeAllElements();
    //modellst2.removeAllElements();    
    for(int j=0;j<SemeRelNameL.length;j++)
    {       
      modellst1.addElement(SemeRelNameL[j]);      
    }     
    try{
      query.update("delete from IISC_SEQUENCE where AS_id="+AsId + " and PR_id="+PrId);
    }
    catch(Exception ex){     
        ex.printStackTrace();
    }  
    modellst2.removeAllElements();
    List1.setSelectedIndex(0);    
    jTabbedPane1.setEnabledAt(4,false);
    //ImaSekvencer=false;
    //List2.removeAll();
    //List1.setListData(setAtt());
    //List2.setListData(new String[0]);
  }

  private void AddAll_actionPerformed(ActionEvent e)
  {
    //List1.setListData(new String[0]);
    String[] SemeRelNameL = setAtt();
    //List2.setListData(SemeRelNameL); 
    
    modellst2.removeAllElements();
    for(int j=0;j<SemeRelNameL.length;j++)
    {       
      modellst2.addElement(SemeRelNameL[j]); 
      modellst1.removeElement(SemeRelNameL[j]);   
    }     
    List2.setSelectedIndex(0);  
    /*
    List2.setListData(setAtt());
    List1.setListData(new String[0]);
    */
    //ImaSekvencer=false;
    jTabbedPane1.setEnabledAt(4,true);
  }

  private void Add_actionPerformed(ActionEvent e)
  {
    
    if(List1.getSelectedValue().toString().length()>0)
    {
      Object[] SemeRel=List1.getSelectedValues();
      for(int j=0;j<SemeRel.length;j++)
      {
        modellst2.addElement(SemeRel[j]);
        modellst1.removeElement(SemeRel[j]);
      }
    }
    List2.setSelectedIndex(0);
    List1.setSelectedIndex(0);
    jTabbedPane1.setEnabledAt(4,true);
    //ImaSekvencer=false;
  }

  private void Rem_actionPerformed(ActionEvent e)
  {
    JDBCQuery query = new JDBCQuery(con);
    if(List2.getSelectedValue().toString().length()>0)
    {
      Object[] SemeRel=List2.getSelectedValues();
      for(int j=0;j<SemeRel.length;j++)
      {
        modellst1.addElement(SemeRel[j]);
        modellst2.removeElement(SemeRel[j]);
        int SemaRelId = NadjiIdSemeRelacije(SemeRel[j].toString().trim());
        try{
          query.update("delete from IISC_SEQUENCE where AS_id="+AsId + " and PR_id="+PrId+ " and RS_id="+ SemaRelId);
        }
        catch(Exception ex){     
        ex.printStackTrace();
        }   
      }
    }
    List1.setSelectedIndex(0);
    List2.setSelectedIndex(0);
    if(modellst2.size()==0)
    jTabbedPane1.setEnabledAt(4,false);
    //ImaSekvencer=false;   
    
    
  }

  private void btnClose_actionPerformed(ActionEvent e)
  {
    JDBCQuery query = new JDBCQuery(con);
    try{
      query.update("delete from IISC_SEQUENCE where AS_id="+AsId + " and PR_id="+PrId);
    }
    catch(Exception ex){     
        ex.printStackTrace();
    }  
    this.dispose();
  }

  private void btnFirst_actionPerformed(ActionEvent e)
  {
    cmbDBMS.setSelectedIndex(0);
  }

  private void btnOK_actionPerformed(ActionEvent e)
  {     
    //Check
    JDBCQuery query = new JDBCQuery(con);
    ResultSet rs;
    ClassFunction Pom = new ClassFunction();
    SemeRelName = new String[List2.getModel().getSize()];
    SemeRelId = new int[List2.getModel().getSize()];   
    Set Constr = new HashSet();    
    ImeBaze = tfImeBaze.getText();     
    if (List2.getModel().getSize() == 0)
    {
      JOptionPane.showMessageDialog(null, "You must select relation schemes for generating, first!", "Warning", JOptionPane.WARNING_MESSAGE);
      
      jTabbedPane1.setSelectedIndex(1);
    } else {
    
    for(int i = 0; i < List2.getModel().getSize(); i++)  
        SemeRelName[i]=List2.getModel().getElementAt(i).toString();
     
   try{
    for (int i=0;i<SemeRelName.length;i++)
    {
      
      String upit = "select RS_id from IISC_RELATION_SCHEME where PR_ID=" + PrId + "and AS_ID=" + AsId + " and RS_name='" + SemeRelName[i] + "'";
      rs=query.select(upit);  
      rs.next();        
      SemeRelId[i] = rs.getInt(1);       
      query.Close(); 
    }    
    
  }catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }   
    
    Set setIdSema = new HashSet();
    Set setAllIdSema = new HashSet();
    int i=Pom.brojanjeNtorki("IISC_RELATION_SCHEME","PR_id="+PrId+" and AS_id="+AsId,con);    
    String upit = "select RS_id from IISC_RELATION_SCHEME  where PR_id="+PrId+" and AS_id="+AsId;                
    int[] AllIdSema=query.selectArraySAint(upit,i,1);             
    query.Close();
    for (int j=0;j<AllIdSema.length;j++)
    {
      String s=Pom.toString(AllIdSema[j]);
      setAllIdSema.add(s);
    } 
    for (int j=0;j<SemeRelId.length;j++)
    {
      String s=Pom.toString(SemeRelId[j]);
      setIdSema.add(s);
    } 
    Set PovezaneSemeRel = Pom.OutSemeRel(con,PrId,AsId,setIdSema,setAllIdSema);    
    String str ="";
    int[] SemeRelZaRemoveIzList1 = new int[PovezaneSemeRel.size()];
    String[] SemeRelZaRemoveIzList1Name = new String[PovezaneSemeRel.size()];
    int m=0;
    for (Iterator it=PovezaneSemeRel.iterator(); it.hasNext(); ) 
    {
              Object element = it.next();
              String E = element.toString();
              SemeRelZaRemoveIzList1[m] = Integer.parseInt(E);
              m=m+1;
    }
    for (int k=0;k<SemeRelZaRemoveIzList1.length;k++)
          {
              String upitE = "select RS_name from IISC_RELATION_SCHEME where PR_id="+PrId+" and RS_id="+SemeRelZaRemoveIzList1[k]+" and AS_id="+AsId;                  
              try{
                  rs=query.select(upitE);                  
                  rs.next();
                  SemeRelZaRemoveIzList1Name[k] = rs.getString("RS_name");
                  query.Close();        
              }
                catch (SQLException ev) {    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
              } 
           } 
          
    for (Iterator it=PovezaneSemeRel.iterator(); it.hasNext(); ) {
        Object element = it.next(); 
        String upitE = "select RS_name from IISC_RELATION_SCHEME where PR_id="+PrId+" and RS_id="+element+" and AS_id="+AsId;                  
        try{
        rs=query.select(upitE);                  
        rs.next();
        String SemaRelName = rs.getString("RS_name");
        query.Close();
        str= str + "<br>" + SemaRelName;
        }
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
        }
    }
    str = str + "<br>" + "Include them now?";
    if (!PovezaneSemeRel.isEmpty())
    {       
      int answer = JOptionPane.showConfirmDialog(null,"<html><center>The following related relation schemes are not included:</center>"+str, "Warning",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
      if (answer == JOptionPane.YES_OPTION) 
      {
          setIdSema.addAll(PovezaneSemeRel); 
          i=0;
          SemeRelId = new int[setIdSema.size()];
          SemeRelName = new String[setIdSema.size()];
          for (Iterator it=setIdSema.iterator(); it.hasNext(); ) 
          {
              Object element = it.next();
              String E = element.toString();
              SemeRelId[i] = Integer.parseInt(E);
              i=i+1;
          }
          for (int k=0;k<SemeRelId.length;k++)
          {
           String upitE = "select RS_name from IISC_RELATION_SCHEME where PR_id="+PrId+" and RS_id="+SemeRelId[k]+" and AS_id="+AsId;                  
              try{
                  rs=query.select(upitE);                  
                  rs.next();
                  SemeRelName[k] = rs.getString("RS_name");
                  query.Close();        
              }
                catch (SQLException ev) {    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
              } 
           } 
           modellst2.removeAllElements();
           for(int j=0;j<SemeRelName.length;j++)
           {              
              modellst2.addElement(SemeRelName[j]);              
           }
           for (int l=0;l<SemeRelZaRemoveIzList1Name.length;l++) 
          {            
            modellst1.removeElement(SemeRelZaRemoveIzList1Name[l]);
          }           
      }   else
      {
        
      }
    }  
    Set ConstrZaIzbacivanje = new HashSet();
    if (List1.getModel().getSize() != 0)  
    {
      Constr = Pom.AllConstraint(con, PrId,AsId, setIdSema);
      ConstrZaIzbacivanje = FjaZaIzbacivanjeConstrZaInv(Constr, Pom, con);
      Constr.removeAll(ConstrZaIzbacivanje);      
      Constraint = new int[Constr.size()];
      int s=0;
      for (Iterator it=Constr.iterator(); it.hasNext(); ) 
      {
              Object element = it.next();
              String E = element.toString();
              Constraint[s] = Integer.parseInt(E);
              s=s+1;
      }
    }else
    {            
            String upitConstr = "select RSC_id from IISC_RS_CONSTRAINT where IISC_RS_CONSTRAINT.PR_id="+PrId+" and IISC_RS_CONSTRAINT.AS_id="+AsId;      
            Constr=query.selectSASet(upitConstr,1);
            ConstrZaIzbacivanje = FjaZaIzbacivanjeConstrZaInv(Constr, Pom, con);
            Constr.removeAll(ConstrZaIzbacivanje);
            Constraint = new int[Constr.size()];
            int s=0;
            for (Iterator it=Constr.iterator(); it.hasNext(); ) 
            {
              Object element = it.next();
              String E = element.toString();
              Constraint[s] = Integer.parseInt(E);
              s=s+1;
            }
            query.Close();            
    }  
    
     
   //end check   
    
    String NazivDatoteke,NazivDatSql,NazivDatInd,NazivDatTable,NazivDatConstr;
    boolean Izmena = false; //nova baza ili izmena postojece
    String s=cmbDBMS.getSelectedItem().toString().trim();
    
    ImeBaze = tfImeBaze.getText();
    
    if (rbKreiranje.isSelected())
    {
      Izmena = false;
    }
    if (rbIzmena.isSelected())
    {
      if (rbCreate.isSelected())
      {
        Izmena = false;
      }
      if (rbAlter.isSelected())
      {
        Izmena = true;
      }      
    }
    if (rbView.isSelected())
    {
      Pogled = true;
    }else
    {
      Pogled = false;
    }
    
    String Putanja = FolderIme.getText() + "\\";
    NazivDatoteke=FolderIme.getText() + "\\" + FileIme.getText();
    boolean OneFileOnly;
    File outputFileW;
    outputFileW=null;
    if (cbOneFile.isSelected() || rbDatabase.isSelected())
    {
        OneFileOnly = true;
    }else
    {
        OneFileOnly = false;
    }
    boolean Ogranicenja, Trigeri, IndeksP,IndeksF,IndeksU;
    if (chIndeksi.isSelected())
    {    
    if (ChPK.isSelected())
    {
        IndeksP = true;        
    }else
    {        
        IndeksP = false;        
    }
    if (ChFK.isSelected())
    {
        IndeksF = true;        
    }else
    {        
        IndeksF = false;        
    }
    if (ChUK.isSelected())
    {
        IndeksU = true;        
    }else
    {        
        IndeksU = false;        
    }
    }else
    {
      IndeksP = false;
      IndeksF = false;
      IndeksU = false; 
    }
    if (chConstraints.isSelected())
    {
        Ogranicenja = true;
    }else
    {
        Ogranicenja = false;
    }
    if (chTrigeri.isSelected())
    {
        Trigeri = true;
    }else
    {
        Trigeri = false;
    }
    DDL = false;
    if (rbScript.isSelected())
    {      
      DDL = true;
    } //rbScript je selektovan
    int TargetDB=0;
    if (rbScript.isSelected())
    {      
      TargetDB = 1;
    } else if (rbDatabase.isSelected())
    {
      TargetDB = 2;
    }else if (rbODBC.isSelected())
    {
      TargetDB = 3;
    }   
    
    String t = FolderIme.getText();
    File f = new File(t);
    boolean test = f.exists();
    
    if(FolderIme.getText().trim().equals(""))
    { 
      JOptionPane.showMessageDialog(null, "<html><center>Directory name required!", "Directory", JOptionPane.ERROR_MESSAGE);
      jTabbedPane1.setSelectedIndex(0);
    }  
    else if (!test)
    {
     JOptionPane.showMessageDialog(null, "<html><center>Directory does not exists!", "Directory", JOptionPane.ERROR_MESSAGE);     
     jTabbedPane1.setSelectedIndex(0);     
    }else{
    
    s=cmbDBMS.getSelectedItem().toString().trim();
    if (s.compareTo("MS SQL Server 2000")==0 && rbIzmena.isSelected() && rbAlter.isSelected()) 
    {
      if(tfImeBaze.getText().trim().equals("") && ImeBazeAlter.getText().trim().equals(""))
      {
        JOptionPane.showMessageDialog(null, "<html><center>DB schema name required!", "DB Schema Name", JOptionPane.ERROR_MESSAGE);
        jTabbedPane1.setSelectedIndex(0);
      }
    }
    
    int SUBP=0;
    String urlp = cmbDSN.getSelectedItem().toString();
    String PasswordParam = new String();
    if (PasswordDatabase.getPassword().length != 0)
      PasswordParam = new String(PasswordDatabase.getPassword());
    else if (PasswordAlter.getPassword().length !=0)
      PasswordParam = new String(PasswordAlter.getPassword());  
      
    /*nobrenovic: start*/
    CheckConstraint.ImplemetationType impType; 
    if(rbDNFImpl.isSelected()) impType = CheckConstraint.ImplemetationType.DNF;
    else impType = CheckConstraint.ImplemetationType.CNF;
    
    
            
    try {
           
           outputFileW = new File("WarningDat.txt");
           PrintWriter outWarning = new PrintWriter(new BufferedWriter(new FileWriter(outputFileW)));           
     
           if (s.compareTo("ANSI SQL-2003")==0) 
           {
             SUBP = 0;
             ScriptANSI skript = new ScriptANSI(con, PrId, AsId, SemeRelId, SemeRelName, Izmena, Constraint, outWarning, NazivDatoteke, OneFileOnly, impType,IndeksP,IndeksF,IndeksU, Ogranicenja, Trigeri, FileIme.getText());
             skript.FormirajSkript();
           }           
           else if (s.compareTo("MS SQL Server 2000")==0) 
          {                         
            SUBP = 1;
            ScriptMSSQL skript = new ScriptMSSQL(con, PrId, AsId, SemeRelId, SemeRelName, Izmena, Constraint, outWarning, NazivDatoteke ,OneFileOnly, IndeksP,IndeksF,IndeksU, impType, Ogranicenja, Trigeri, FileIme.getText(),ImeBaze, Pogled, UserNameDatabase.getText(),PasswordParam, HostDatabase.getText());            
            skript.FormirajSkript();
          }  
          
        else if (s.compareTo("MS SQL Server 2008")==0) 
        {
         SUBP = 4;
         ScriptMSSQL2008 skript = new ScriptMSSQL2008(con, PrId, AsId, SemeRelId, SemeRelName, Izmena, Constraint, outWarning, NazivDatoteke ,OneFileOnly, IndeksP,IndeksF,IndeksU, impType, Ogranicenja, Trigeri, FileIme.getText(),ImeBaze, Pogled, UserNameDatabase.getText(),PasswordParam, HostDatabase.getText());            
         skript.FormirajSkript();
        }
          else 
          {
            SUBP = 2;
            if (ImeBaze.trim()!= null && ImeBaze.trim().length() !=0)
            {      
              ImeBaze = ImeBaze.trim()+".";
            }
            ScriptOracle skript = new ScriptOracle(con, PrId, AsId, SemeRelId, SemeRelName, Izmena, Constraint, outWarning, NazivDatoteke, OneFileOnly, IndeksP,IndeksF,IndeksU, impType,Ogranicenja, Trigeri, FileIme.getText(), ImeBaze, Pogled, UserNameDatabase.getText(),PasswordParam, HostDatabase.getText());
            skript.FormirajSkript();
          }       
                  
         outWarning.close();
    }
    catch (IOException ex) {
            System.err.println("Cannot open file.");
            System.err.println(ex);
    }         
    /*nobrenovic: stop*/
        
    MessageWindow MWin = new MessageWindow(this, "Generated Results", true, outputFileW, OneFileOnly, IndeksP,IndeksU,IndeksF, Ogranicenja, Trigeri,FileIme.getText(),Putanja, DDL, SUBP, UserNameDatabase.getText(), PasswordParam, HostDatabase.getText(), NazivDatoteke, con, TargetDB, urlp); 
    Settings.Center(MWin);
    MWin.setVisible(true);   
    }
    } //end else ako nije izabrana nijedna relacija
    
    //List2.setSelectedIndex(0);
    //jTabbedPane1.setSelectedIndex(1);
    
  }

  private void btnPrev_actionPerformed(ActionEvent e)
  {
    int i = cmbDBMS.getSelectedIndex();
    if (i != 0)
    {
      cmbDBMS.setSelectedIndex(i-1);
    }       
  }

  private void btnNext_actionPerformed(ActionEvent e)
  {
    int i = cmbDBMS.getSelectedIndex();
    if (i != cmbDBMS.getItemCount()-1)
    {
      cmbDBMS.setSelectedIndex(i+1);
    } 
  }

  private void btnLast_actionPerformed(ActionEvent e)
  {
    cmbDBMS.setSelectedIndex(cmbDBMS.getItemCount()-1);
  }

  private void btnUp_actionPerformed(ActionEvent e)
  {
    if(!List2.isSelectionEmpty() && List2.getSelectedIndex()>=0)
    {
      Object[] lst = new Object[List2.getModel().getSize()];
      for(int i = 0; i < List2.getModel().getSize(); i++)  
        lst[i]=List2.getModel().getElementAt(i);
      Object pom=lst[List2.getSelectedIndex()];
      lst[List2.getSelectedIndex()]=lst[List2.getSelectedIndex()-1];
      lst[List2.getSelectedIndex()-1]=pom;
      List2.setListData(lst);
      List2.setSelectedValue(pom,true);
    }
  }

  private void btnDown_actionPerformed(ActionEvent e)
  {
    if(!List2.isSelectionEmpty() && List2.getSelectedIndex()>=0)
    {
      Object[] lst = new Object[List2.getModel().getSize()];
      for(int i = 0; i < List2.getModel().getSize(); i++)  
        lst[i]=List2.getModel().getElementAt(i);
      Object pom=lst[List2.getSelectedIndex()];
      lst[List2.getSelectedIndex()]=lst[List2.getSelectedIndex()+1];
      lst[List2.getSelectedIndex()+1]=pom;
      List2.setListData(lst);
      List2.setSelectedValue(pom,true);      
    }
  }

  private void ChooseF_actionPerformed(ActionEvent e)
  {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
    chooser.setDialogTitle("Browse For Folder"); 
    int returnVal = chooser.showDialog(this,"OK");
    if(returnVal == JFileChooser.APPROVE_OPTION) 
    {
        String s = chooser.getSelectedFile().toString();
        FolderIme.setText(s);
    } 
  }

  private void ChoosFile_actionPerformed(ActionEvent e)
  {
        
    JFileChooser chooser = new JFileChooser();    
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setDialogTitle("Browse For File");
    int returnVal = chooser.showDialog(this,"OK");
    if(returnVal == JFileChooser.APPROVE_OPTION) 
    {
        String s = chooser.getSelectedFile().getName().toString();
        FileIme.setText(s);
    }     
  }
  private Set NadjiOsnovniRefInt(int LRhs, ClassFunction Pom, Connection con)
  {
    JDBCQuery query = new JDBCQuery(con);
    Set LIdAtributaInv = new HashSet();
    String upit2 = "select RS_id from IISC_RSC_RS_SET where PR_id="+PrId+" and AS_id="+AsId+" and RS_Set_id="+LRhs;
    int k = Pom.brojanjeNtorki("IISC_RSC_RS_SET","PR_id="+PrId+" and AS_id="+AsId+" and RS_Set_id="+LRhs,con);
    LIdAtributaInv =  query.selectSASet(upit2,k);
    query.Close();  
    return LIdAtributaInv;
  }
  private Set FjaZaIzbacivanjeConstrZaInv(Set Constr, ClassFunction Pom, Connection con)
  {
    JDBCQuery query = new JDBCQuery(con);
    Set ConstrZaIzbacivanje = new HashSet();
    //int z=0;
    int[] ConstrZaIzbacivanjeArr=null;
    Set LIdSeme = new HashSet(); 
    Set RIdSeme = new HashSet();
    Set LIdSemeInv = new HashSet(); 
    Set RIdSemeInv = new HashSet();
    Set Id_RSCInvSet = new HashSet();   
    
    String upit1 = "select RSC_id, LHS_RS_Set_id, RHS_RS_Set_id from IISC_RS_CONSTRAINT where PR_id="+PrId+" and AS_id="+AsId+" and RSC_type=4";
    int v=Pom.brojanjeNtorki("IISC_RS_CONSTRAINT","PR_id="+PrId+" and AS_id="+AsId+" and RSC_type=4",con); 
    int[] Id_RSCInv=query.selectArraySAint(upit1,v,1);
    int[] LhsInv = query.selectArraySAint(upit1,v,2);
    int[] RhsInv = query.selectArraySAint(upit1,v,3);
    query.Close(); 
    
 if (Id_RSCInv != null && Id_RSCInv.length!=0)
 {   
    String O;
    for (int y=0;y<Id_RSCInv.length;y++)
    {      
      O = Pom.toString(Id_RSCInv[y]);
      Id_RSCInvSet.add(O);
    }
    Id_RSCInvSet.retainAll(Constr);
    if (!Id_RSCInvSet.isEmpty())
    {
    Id_RSCInv = new int[Id_RSCInvSet.size()];
    int s=0;
    for (Iterator it=Id_RSCInvSet.iterator(); it.hasNext(); ) 
    {
              Object element = it.next();
              String E = element.toString();
              Id_RSCInv[s] = Integer.parseInt(E);
              s=s+1;
    }
    ResultSet rs;
    int LLhsInv=0;
    int RRhsInv=0;
    for (int u=0;u<Id_RSCInv.length;u++)
    {
        String upit3 = "select LHS_RS_Set_id, RHS_RS_Set_id from IISC_RS_CONSTRAINT where PR_id="+PrId+" and AS_id="+AsId+" and RSC_id=" +Id_RSCInv[u];
        try{
          rs=query.select(upit3);
          //int q=Pom.brojanjeNtorki("IISC_RS_CONSTRAINT","PR_id="+PrId+" and AS_id="+AsId+" and RSC_id=" +Id_RSCInv[u],con); 
          rs.next();
          LLhsInv =  rs.getInt(1);
          RRhsInv = rs.getInt(2);        
          query.Close(); 
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);                   
         }
        
        String upit2 = "select RS_id from IISC_RSC_RS_SET where PR_id="+PrId+" and AS_id="+AsId+" and RS_Set_id="+LLhsInv;
        int k = Pom.brojanjeNtorki("IISC_RSC_RS_SET","PR_id="+PrId+" and AS_id="+AsId+" and RS_Set_id="+LLhsInv,con);
        LIdSemeInv =  query.selectSASet(upit2,k);
        query.Close();
        upit2 = "select RS_id from IISC_RSC_RS_SET where PR_id="+PrId+" and AS_id="+AsId+" and RS_Set_id="+RRhsInv;
        k = Pom.brojanjeNtorki("IISC_RSC_RS_SET","PR_id="+PrId+" and AS_id="+AsId+" and RS_Set_id="+RRhsInv,con);
        RIdSemeInv =  query.selectSASet(upit2,k);
        query.Close();
        
        upit1 = "select RSC_id, LHS_RS_Set_id, RHS_RS_Set_id from IISC_RS_CONSTRAINT where PR_id="+PrId+" and AS_id="+AsId+" and RSC_type=0";
        int l=Pom.brojanjeNtorki("IISC_RS_CONSTRAINT","PR_id="+PrId+" and AS_id="+AsId+" and RSC_type=0",con); 
        int[] Id_RSC=query.selectArraySAint(upit1,l,1);
        int[] Lhs = query.selectArraySAint(upit1,l,2);
        int[] Rhs = query.selectArraySAint(upit1,l,3);
        query.Close();         
        String st;
        for (int f=0;f<Id_RSC.length;f++)
        {
          LIdSeme = NadjiOsnovniRefInt(Lhs[f], Pom,con);
          RIdSeme = NadjiOsnovniRefInt(Rhs[f], Pom, con);
          if (LIdSeme.equals(RIdSemeInv) && RIdSeme.equals(LIdSemeInv))
          {              
              st = Pom.toString(Id_RSC[f]);
              ConstrZaIzbacivanje.add(st);
              //ConstrZaIzbacivanjeArr[z] = Id_RSC[f]; 
          }
        }        
    }
    /*
    if (ConstrZaIzbacivanjeArr != null && ConstrZaIzbacivanjeArr.length !=0)
    {
    for (int y=0;y<ConstrZaIzbacivanjeArr.length;y++)
    {      
      st = Pom.toString(ConstrZaIzbacivanjeArr[y]);
      ConstrZaIzbacivanje.add(st);
    }
    }*/
    
   } //ima inverznih koji pripadaju skupu odabranih ogranicenja  
  }  // ima inverznih u celoj semi 
   return  ConstrZaIzbacivanje;
  }
  
  private void btnApply_actionPerformed(ActionEvent e)
  {    
    JDBCQuery query = new JDBCQuery(con);
    ResultSet rs;
    ClassFunction Pom = new ClassFunction();
    SemeRelName = new String[List2.getModel().getSize()];
    SemeRelId = new int[List2.getModel().getSize()];   
    Set Constr = new HashSet();    
    ImeBaze = tfImeBaze.getText();     
    if (List2.getModel().getSize() == 0)
    {
      JOptionPane.showMessageDialog(null, "You must select relation schemes for generating, first!", "Warning", JOptionPane.WARNING_MESSAGE);
      //btnOK.setEnabled(false);
      jTabbedPane1.setSelectedIndex(1);
    } else {
    
    for(int i = 0; i < List2.getModel().getSize(); i++)  
        SemeRelName[i]=List2.getModel().getElementAt(i).toString();
     
   try{
    for (int i=0;i<SemeRelName.length;i++)
    {
      
      String upit = "select RS_id from IISC_RELATION_SCHEME where PR_ID=" + PrId + "and AS_ID=" + AsId + " and RS_name='" + SemeRelName[i] + "'";
      rs=query.select(upit);  
      rs.next();        
      SemeRelId[i] = rs.getInt(1);       
      query.Close(); 
    }    
    
  }catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
                          }   
    
    Set setIdSema = new HashSet();
    Set setAllIdSema = new HashSet();
    int i=Pom.brojanjeNtorki("IISC_RELATION_SCHEME","PR_id="+PrId+" and AS_id="+AsId,con);    
    String upit = "select RS_id from IISC_RELATION_SCHEME  where PR_id="+PrId+" and AS_id="+AsId;                
    int[] AllIdSema=query.selectArraySAint(upit,i,1);             
    query.Close();
    for (int j=0;j<AllIdSema.length;j++)
    {
      String s=Pom.toString(AllIdSema[j]);
      setAllIdSema.add(s);
    } 
    for (int j=0;j<SemeRelId.length;j++)
    {
      String s=Pom.toString(SemeRelId[j]);
      setIdSema.add(s);
    } 
    Set PovezaneSemeRel = Pom.OutSemeRel(con,PrId,AsId,setIdSema,setAllIdSema);    
    String str ="";
    int[] SemeRelZaRemoveIzList1 = new int[PovezaneSemeRel.size()];
    String[] SemeRelZaRemoveIzList1Name = new String[PovezaneSemeRel.size()];
    int m=0;
    for (Iterator it=PovezaneSemeRel.iterator(); it.hasNext(); ) 
    {
              Object element = it.next();
              String E = element.toString();
              SemeRelZaRemoveIzList1[m] = Integer.parseInt(E);
              m=m+1;
    }
    for (int k=0;k<SemeRelZaRemoveIzList1.length;k++)
          {
              String upitE = "select RS_name from IISC_RELATION_SCHEME where PR_id="+PrId+" and RS_id="+SemeRelZaRemoveIzList1[k]+" and AS_id="+AsId;                  
              try{
                  rs=query.select(upitE);                  
                  rs.next();
                  SemeRelZaRemoveIzList1Name[k] = rs.getString("RS_name");
                  query.Close();        
              }
                catch (SQLException ev) {    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
              } 
           } 
          
    for (Iterator it=PovezaneSemeRel.iterator(); it.hasNext(); ) {
        Object element = it.next(); 
        String upitE = "select RS_name from IISC_RELATION_SCHEME where PR_id="+PrId+" and RS_id="+element+" and AS_id="+AsId;                  
        try{
        rs=query.select(upitE);                  
        rs.next();
        String SemaRelName = rs.getString("RS_name");
        query.Close();
        str= str + "<br>" + SemaRelName;
        }
        catch (SQLException ev) {
    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
        }
    }
    str = str + "<br>" + "Include them now?";
    if (!PovezaneSemeRel.isEmpty())
    {       
      int answer = JOptionPane.showConfirmDialog(null,"<html><center>The following related relation schemes are not included:</center>"+str, "Warning",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
      if (answer == JOptionPane.YES_OPTION) 
      {
          setIdSema.addAll(PovezaneSemeRel); 
          i=0;
          SemeRelId = new int[setIdSema.size()];
          SemeRelName = new String[setIdSema.size()];
          for (Iterator it=setIdSema.iterator(); it.hasNext(); ) 
          {
              Object element = it.next();
              String E = element.toString();
              SemeRelId[i] = Integer.parseInt(E);
              i=i+1;
          }
          for (int k=0;k<SemeRelId.length;k++)
          {
           String upitE = "select RS_name from IISC_RELATION_SCHEME where PR_id="+PrId+" and RS_id="+SemeRelId[k]+" and AS_id="+AsId;                  
              try{
                  rs=query.select(upitE);                  
                  rs.next();
                  SemeRelName[k] = rs.getString("RS_name");
                  query.Close();        
              }
                catch (SQLException ev) {    
                          JOptionPane.showMessageDialog(null, ev.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                          System.err.println(ev);
              } 
           } 
           modellst2.removeAllElements();
           for(int j=0;j<SemeRelName.length;j++)
           {              
              modellst2.addElement(SemeRelName[j]);              
           }
           for (int l=0;l<SemeRelZaRemoveIzList1Name.length;l++) 
          {            
            modellst1.removeElement(SemeRelZaRemoveIzList1Name[l]);
          }  
      } 
    
    }  
    Set ConstrZaIzbacivanje = new HashSet();
    if (List1.getModel().getSize() != 0)  
    {
      Constr = Pom.AllConstraint(con, PrId,AsId, setIdSema);
      ConstrZaIzbacivanje = FjaZaIzbacivanjeConstrZaInv(Constr, Pom, con);
      Constr.removeAll(ConstrZaIzbacivanje);      
      Constraint = new int[Constr.size()];
      int s=0;
      for (Iterator it=Constr.iterator(); it.hasNext(); ) 
      {
              Object element = it.next();
              String E = element.toString();
              Constraint[s] = Integer.parseInt(E);
              s=s+1;
      }
    }else
    {            
            String upitConstr = "select RSC_id from IISC_RS_CONSTRAINT where IISC_RS_CONSTRAINT.PR_id="+PrId+" and IISC_RS_CONSTRAINT.AS_id="+AsId;      
            Constr=query.selectSASet(upitConstr,1);
            ConstrZaIzbacivanje = FjaZaIzbacivanjeConstrZaInv(Constr, Pom, con);
            Constr.removeAll(ConstrZaIzbacivanje);
            Constraint = new int[Constr.size()];
            int s=0;
            for (Iterator it=Constr.iterator(); it.hasNext(); ) 
            {
              Object element = it.next();
              String E = element.toString();
              Constraint[s] = Integer.parseInt(E);
              s=s+1;
            }
            query.Close();            
    }    
    String s=cmbDBMS.getSelectedItem().toString().trim();
    if(FolderIme.getText().trim().equals(""))
      JOptionPane.showMessageDialog(null, "<html><center>Directory name required!", "Directory", JOptionPane.ERROR_MESSAGE);
    else if (s.compareTo("MS SQL Server 2000")==0 && rbIzmena.isSelected()) 
    {
      if(tfImeBaze.getText().trim().equals("") && ImeBazeAlter.getText().trim().equals(""))
        JOptionPane.showMessageDialog(null, "<html><center>DB schema name required!", "DB Schema Name", JOptionPane.ERROR_MESSAGE);
    }else{    
      JOptionPane.showMessageDialog(null, "Completeness check completed successfully.", "Check", JOptionPane.INFORMATION_MESSAGE);
      //btnOK.setEnabled(true);     
    }
   } //end else  
    
  }   
  private void DisableEnable_itemStateChanged(ItemEvent e)
  {
  
  }

  private void rsNoaction_actionPerformed(ItemEvent e)
  {
    
  }

  private void ChUK_actionPerformed(ActionEvent e)
  {
  }

  private void chIndeksi_actionPerformed(ActionEvent e)
  {
    if (chIndeksi.isSelected())
    {
        ChPK.setEnabled(true);
        ChFK.setEnabled(true);
        ChUK.setEnabled(true);
        
    }else
    {
        ChPK.setEnabled(false);
        ChFK.setEnabled(false);
        ChUK.setEnabled(false);
        
    }
  }

  private void rbScript_actionPerformed(ActionEvent e)
  {
    cbOneFile.setEnabled(true);
    UserNameDatabase.setEnabled(false);
    PasswordDatabase.setEnabled(false);
    HostDatabase.setEnabled(false);
    cmbDSN.setEnabled(false);
    LUserName.setEnabled(false);
    LPassword.setEnabled(false);
    LHost.setEnabled(false);
    LSource.setEnabled(false);
  }

  private void rbIzmena_actionPerformed(ActionEvent e)
  {
    char[] PasswordParam;
    PasswordParam = PasswordDatabase.getPassword();
    Modify.setEnabled(true);
    jTabbedPane1.setEnabledAt(3,true);
    if (rbIzmena.isSelected())
    {
      if (rbAlter.isSelected())
      {
        UserNameAlter.setEditable(true);
        PasswordAlter.setEnabled(true);
        HostAlter.setEnabled(true);
        UserNameL.setEnabled(true);
        PasswordL.setEnabled(true);
        HostL.setEnabled(true);
        DBSchemaName.setEnabled(true);
        ImeBazeAlter.setEnabled(true);
      }else
      {
        UserNameAlter.setEditable(false);
        PasswordAlter.setEnabled(false);
        HostAlter.setEnabled(false);
        ImeBazeAlter.setEnabled(false);
      }                  
      
      if (!HostDatabase.getText().trim().equals(""))
      {
        HostAlter.setText(HostDatabase.getText());
        //HostAlter.setEditable(false);
      }
      if (!UserNameDatabase.getText().trim().equals(""))
      {
        UserNameAlter.setText(UserNameDatabase.getText());
        UserNameAlter.setEditable(false);
      }
      
    }else
    {
      jTabbedPane1.setEnabledAt(3,false);
      jTabbedPane1.setSelectedIndex(0);
    }
   
  }

  private void rbKreiranje_actionPerformed(ActionEvent e)
  {
    Modify.setEnabled(false);
    if (rbKreiranje.isSelected())
    {
      jTabbedPane1.setEnabledAt(3,false);
      jTabbedPane1.setSelectedIndex(0);
    }else
    {
      jTabbedPane1.setEnabledAt(3,true);
    }
    
  }

  private void rbAlter_actionPerformed(ActionEvent e)
  {
    if (rbAlter.isSelected())
    {
      PasswordL.setEnabled(true);
      HostL.setEnabled(true);
      String s=cmbDBMS.getSelectedItem().toString().trim();
    if (s.compareTo("MS SQL Server 2000")==0) 
    {
       DBSchemaName.setEnabled(true);
       ImeBazeAlter.setEnabled(true); 
    }else
    {
      DBSchemaName.setEnabled(false);
      ImeBazeAlter.setEnabled(false);
    }      
      UserNameAlter.setEnabled(true);
      PasswordAlter.setEnabled(true);
      HostAlter.setEnabled(true);
      UserNameL.setEnabled(true);      
    }else
    {
      UserNameAlter.setEnabled(false);
      PasswordAlter.setEnabled(false);
      HostAlter.setEnabled(false);
      ImeBazeAlter.setEnabled(false);
    }
    //char[] PasswordParam;
    //PasswordParam = PasswordDatabase.getPassword();
      if (!HostDatabase.getText().trim().equals(""))
      {
        HostAlter.setText(HostDatabase.getText());
        HostAlter.setEditable(false);
      }else
      {
        HostAlter.setEditable(true);
      }
      if (!UserNameDatabase.getText().trim().equals(""))
      {
        UserNameAlter.setText(UserNameDatabase.getText());
        UserNameAlter.setEditable(false);
      }else
      {
        UserNameAlter.setEditable(true);
      }
      
  }

  private void rbCreate_actionPerformed(ActionEvent e)
  {
    if (rbCreate.isSelected())
    {
      UserNameAlter.setEnabled(false);
      PasswordAlter.setEnabled(false);
      HostAlter.setEnabled(false);
      UserNameL.setEnabled(false);
      PasswordL.setEnabled(false);
      HostL.setEnabled(false);
      DBSchemaName.setEnabled(false);
      ImeBazeAlter.setEnabled(false);
    }else 
    {
      UserNameAlter.setEnabled(true);
      PasswordAlter.setEnabled(true);
      HostAlter.setEnabled(true);
      ImeBazeAlter.setEnabled(true);
    }
  }  
  
  void ConnectJdbcOdbc() 
  {
      url = "jdbc:odbc:"+cmbDSN.getSelectedItem().toString();
      //System.out.println(url);
      username = UserNameAlter.getText();
      password = new String(PasswordAlter.getPassword());
      
    try 
    {
      Class.forName(driverJdbcOdbc);
    }
    catch (ClassNotFoundException ef) 
    {
      JOptionPane.showMessageDialog(null, "ClassNotFoundException:    " + ef.getMessage() , "Connection error", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }  
    try
    {  
       url ="jdbc:odbc:"+ cmbDSN.getSelectedItem().toString();
       username = UserNameAlter.getText();
       password = new String(PasswordAlter.getPassword());
       //System.out.println(url + " : " + username + " > " + password);
       connection = (Connection)DriverManager.getConnection(url, username, password);
       connection.close();
    }
    catch(SQLException ex)
    {
        if(this.isVisible())
        {      
          JOptionPane.showMessageDialog(null, "<html><center>This is not valid repository!", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
      
  }
    
  private void rbDatabase_actionPerformed(ActionEvent e)
  {    
    //if (rbDatabase.isSelected())
    //{
      cbOneFile.setEnabled(false);
      UserNameDatabase.setEnabled(true);
      PasswordDatabase.setEnabled(true);
      HostDatabase.setEnabled(true);      
      LUserName.setEnabled(true);
      LPassword.setEnabled(true);
      LHost.setEnabled(true);
      cmbDSN.setEnabled(false);
      LSource.setEnabled(false);
    //}
  }

  private void rbODBC_actionPerformed(ActionEvent e)
  {
    cbOneFile.setEnabled(false);
    UserNameDatabase.setEnabled(false);
    PasswordDatabase.setEnabled(false);
    HostDatabase.setEnabled(false);
    cmbDSN.setEnabled(true);
    LUserName.setEnabled(false);
    LPassword.setEnabled(false);
    LHost.setEnabled(false);
    LSource.setEnabled(true);
  }

  private void chTrigeri_actionPerformed(ActionEvent e)
  {
    if (!chTrigeri.isSelected())
    {
      LInv.setEnabled(false);
      rbView.setEnabled(false);
      rbProcedure.setEnabled(false);
    }else
    {
      LInv.setEnabled(true);
      rbView.setEnabled(true);
      rbProcedure.setEnabled(true);
    }
  }

  private void btnHelp_actionPerformed(ActionEvent e)
  {
    Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, con);
    Settings.Center(hlp);
    hlp.setVisible(true);
  }

  private void HostDatabase_focusLost(FocusEvent e)
  {
    HostAlter.setText(HostDatabase.getText());   
    HostAlter.setEditable(false);
  }

  private void UserNameDatabase_focusLost(FocusEvent e)
  {
    UserNameAlter.setText(UserNameDatabase.getText());
    //UserNameAlter.setEditable(false);
  }

  private void HostAlter_focusLost(FocusEvent e)
  {
    if (rbIzmena.isSelected())
    {
      HostDatabase.setText(HostAlter.getText());   
      //HostDatabase.setEditable(false);
    }
  }

  private void UserNameAlter_focusLost(FocusEvent e)
  {
    if (rbIzmena.isSelected())
    {
      UserNameDatabase.setText(UserNameAlter.getText());
      //UserNameDatabase.setEditable(false);
    }
  }

  private void tfImeBaze_focusLost(FocusEvent e)
  {
    String s=cmbDBMS.getSelectedItem().toString().trim();
    if (s.compareTo("MS SQL Server 2000")==0) 
    {
       ImeBazeAlter.setText(tfImeBaze.getText());
       /*
       if (!tfImeBaze.getText().trim().equals(""))
        ImeBazeAlter.setEditable(false); 
       else 
        ImeBazeAlter.setEditable(true); 
        */
    }    
  }

  private void ImeBazeAlter_focusLost(FocusEvent e)
  {
    if (rbIzmena.isSelected())
    {
      tfImeBaze.setText(ImeBazeAlter.getText());
      /*
      if (!ImeBazeAlter.getText().trim().equals(""))
        tfImeBaze.setEditable(false);
      else
        tfImeBaze.setEditable(true);*/
    }
  }

  private void cmbDBMS_actionPerformed(ActionEvent e)
  {
    JDBCQuery query=new JDBCQuery(con);
    String s=cmbDBMS.getSelectedItem().toString().trim();
    if (s.compareTo("MS SQL Server 2000")==0 && rbAlter.isSelected()) 
    {
       ImeBazeAlter.setEnabled(true); 
    }else
    {
      ImeBazeAlter.setEnabled(false);
    }
    try{
      query.update("delete from IISC_SEQUENCE where AS_id="+AsId + " and PR_id="+PrId);
    }
    catch(Exception ex){     
        ex.printStackTrace();
    }  
    
    InicijalizujTabelu(List3.getSelectedIndex());
  }

  private void PasswordDatabase_focusLost(FocusEvent e)
  {
  }

  private void List1_mouseClicked(MouseEvent e)
  {
    if (e.getClickCount() == 2)
    {
      if (List1.getModel().getSize() != 0)
      {
      int index = List1.locationToIndex(e.getPoint());
      Object SemeRel;      
      SemeRel = List1.getSelectedValue();        
      modellst2.addElement(SemeRel);
      modellst1.remove(index);
      List2.setSelectedIndex(List2.getModel().getSize()-1);
      if(List1.getModel().getSize()>0)
      {
        if (index == 0)
          List1.setSelectedIndex(index);
        else 
          List1.setSelectedIndex(index-1);
      } 
      }
    }
  }

  private void List2_mouseClicked(MouseEvent e)
  {
    if (e.getClickCount() == 2)
    {
      if (List2.getModel().getSize() != 0)
      {
      int index = List2.locationToIndex(e.getPoint());
      Object SemeRel;
      //List1.setSelectedIndex(index);
      SemeRel = List2.getSelectedValue();        
      modellst1.addElement(SemeRel);
      modellst2.remove(index);
      List1.setSelectedIndex(List1.getModel().getSize()-1);
      if(List2.getModel().getSize()>0)
      {
        if (index == 0)
          List2.setSelectedIndex(index);
        else 
          List2.setSelectedIndex(index-1);
      } 
      }
    }
  }

  private void jButton1_actionPerformed(ActionEvent e)
  {
    ResultSet rs2;
      JDBCQuery query2=new JDBCQuery(con);
      
      
      //String upit = "delete from IISC_SEQUENCE where PR_id="+PrId+ " and AS_id="+AsId + " and RS_id="+ IdSeme+ " and Att_id=" + pom;
      try{
        query2.update("delete from IISC_SEQUENCE"); 
             
      }
        catch(Exception ex){     
        ex.printStackTrace();
      }   
  }
 
  private void PuniListuZaSekvencer()
  {
    modellst3.removeAllElements();
    
    if(List2.getModel().getSize()>0)
    {      
      for (int i=0;i<List2.getModel().getSize();i++)
      {
        modellst3.addElement(modellst2.getElementAt(i));
      }  
    }
    List3.setSelectedIndex(0);
  }

  private void Sequence_focusGained(FocusEvent e)
  {
    ResultSet rs2;
    JDBCQuery query2=new JDBCQuery(con);
    
    if (ImaSekvencer==false)
    {
      ImaSekvencer=true;
      PuniListuZaSekvencer();
      InicijalizujTabelu(0);      
    }else
    {
      PuniListuZaSekvencer();
      int pom=NadjiIdSemeRelacije(modellst3.getElementAt(0).toString().trim());
      String upit = "select * from IISC_SEQUENCE where PR_id="+PrId+ " and AS_id="+AsId + " and RS_id="+pom;
      
      try{
        rs2=query2.select(upit);
        if(rs2.next())
        {          
          stm.setTabeluSequ(pom, PrId, AsId,cmbDBMS.getSelectedItem().toString().trim());
        }else
        {          
          stm.setQuerySequ(pom, PrId, AsId,cmbDBMS.getSelectedItem().toString().trim());
        }
        query2.Close();
      }
        catch(Exception ex){     
        ex.printStackTrace();
      }          
    }
    
  }

  private void List3_mouseClicked(MouseEvent e)
  {
      ResultSet rs2;
      JDBCQuery query2=new JDBCQuery(con);
      
      int pom=NadjiIdSemeRelacije(List3.getSelectedValue().toString().trim());
      
      String upit = "select * from IISC_SEQUENCE where PR_id="+PrId+ " and AS_id="+AsId + " and RS_id="+pom;
      try{
        rs2=query2.select(upit);
        if(rs2.next())
        {          
          stm.setTabeluSequ(pom, PrId, AsId,cmbDBMS.getSelectedItem().toString().trim());
        }else
        {          
          stm.setQuerySequ(pom, PrId, AsId,cmbDBMS.getSelectedItem().toString().trim());
        }
        query2.Close();
      }
        catch(Exception ex){     
        ex.printStackTrace();
      }    
  }
  
  private boolean PostojiSekvenca(int IdSeme, String ImeAtr)
  {
      ResultSet rs2;
      JDBCQuery query2=new JDBCQuery(con);
      int pom = NadjiIdAtributa(ImeAtr);
      boolean ret=false;
      String upit = "select * from IISC_SEQUENCE where PR_id="+PrId+ " and AS_id="+AsId + " and RS_id="+ IdSeme+ " and Att_id=" + pom;
      try{
        rs2=query2.select(upit);
        if(rs2.next())
        {          
          ret=true;
        }        
      }
        catch(Exception ex){     
        ex.printStackTrace();
      }   
      return ret;
  }
  
  private void Tabela_focusLost(FocusEvent e)
  {
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(con);
    String s = cmbDBMS.getSelectedItem().toString().trim();
    
    //int IdSeme=NadjiIdSemeRelacije(List3.getSelectedValue().toString().trim());
    try{
      String NazivSekvence;
      for (int i=0;i<Tabela.getRowCount();i++)
      {        
        NazivSekvence = "S_" + NazivTabele(IdSemeSeq)+Tabela.getValueAt(i,0).toString().trim();        
        if (s.compareTo("MS SQL Server 2000")!=0)
      {
        int cycle=0;
        int order=0;
        
        if(stm.getValueAt(i,1).equals(Boolean.valueOf("true")))
        {                             
          if (stm.getValueAt(i,5).equals(Boolean.valueOf("true"))) cycle=1;
          if (stm.getValueAt(i,7).equals(Boolean.valueOf("true"))) order=1;
          if (PostojiSekvenca(IdSemeSeq,Tabela.getValueAt(i,0).toString().trim()))
          {
            query.update("update IISC_SEQUENCE set Seq_increment=" +Tabela.getValueAt(i,2) + ", Seq_name='" + NazivSekvence + "', Seq_start=" + Tabela.getValueAt(i,3) + ", Seq_maxvalue=" + Tabela.getValueAt(i,4) + ", Seq_cycle= " + cycle + ", Seq_cache=" + Tabela.getValueAt(i,6) + ", Seq_order=" + order + " where AS_id="+AsId + " and PR_id="+PrId+ " and RS_id="+ IdSemeSeq + " and Att_id=" + NadjiIdAtributa(Tabela.getValueAt(i,0).toString().trim()));
          }else
          {
            
            query.update("insert into IISC_SEQUENCE(RS_id,Att_id,PR_id,AS_id,Seq_name,Seq_increment,Seq_start,Seq_maxvalue,Seq_cycle,Seq_cache, Seq_order) values("+ IdSemeSeq +","+ NadjiIdAtributa(Tabela.getValueAt(i,0).toString().trim()) +","+PrId+ "," + AsId + ",'"+ NazivSekvence+"',"+ Tabela.getValueAt(i,2)+","+ Tabela.getValueAt(i,3)+","+Tabela.getValueAt(i,4) + "," + cycle + ","+ Tabela.getValueAt(i,6)+","+order + ")");
          }
        }else
        {
          query.update("delete from IISC_SEQUENCE where AS_id="+AsId + " and PR_id="+PrId+ " and RS_id="+ IdSemeSeq + " and Att_id=" + NadjiIdAtributa(Tabela.getValueAt(i,0).toString().trim()));
        }
      }else
      {
       if(stm.getValueAt(i,1).equals(Boolean.valueOf("true")))
        { 
        if (PostojiSekvenca(IdSemeSeq,Tabela.getValueAt(i,0).toString().trim()))
          {
            query.update("update IISC_SEQUENCE set Seq_increment=" +Tabela.getValueAt(i,2) +", Seq_start=" + Tabela.getValueAt(i,3)+ ", Seq_name='" + NazivSekvence + "' where AS_id="+AsId + " and PR_id="+PrId+ " and RS_id="+ IdSemeSeq + " and Att_id=" + NadjiIdAtributa(Tabela.getValueAt(i,0).toString().trim()));
          }else
          {            
            query.update("insert into IISC_SEQUENCE(RS_id,Att_id,PR_id,AS_id, Seq_name,Seq_increment,Seq_start) values("+ IdSemeSeq +","+ NadjiIdAtributa(Tabela.getValueAt(i,0).toString().trim()) +","+PrId+ "," + AsId + ",'"+ NazivSekvence + "', "+Tabela.getValueAt(i,2)+","+ Tabela.getValueAt(i,3)+ ")");
          }
          }else
        {
          query.update("delete from IISC_SEQUENCE where AS_id="+AsId + " and PR_id="+PrId+ " and RS_id="+ IdSemeSeq + " and Att_id=" + NadjiIdAtributa(Tabela.getValueAt(i,0).toString().trim()));
        }
      }
      }
    }
    catch(Exception ex){     
        ex.printStackTrace();
      }    
  }

  private void Tabela_focusGained(FocusEvent e)
  {
    if(ImaSekvencer)
      IdSemeSeq=NadjiIdSemeRelacije(List3.getSelectedValue().toString().trim());
    else
      IdSemeSeq=NadjiIdSemeRelacije(modellst3.getElementAt(0).toString().trim());
  }

  private void FolderIme_actionPerformed(ActionEvent e)
  { 
   
  }

  private void dugme_actionPerformed(ActionEvent e)
  {
    String s = FolderIme.getText();
    File f = new File(s);
    boolean test = f.exists();
    if (!test)
   {
     JOptionPane.showMessageDialog(null, "<html><center>Attribute required!", "Attributes", JOptionPane.ERROR_MESSAGE);
     
   }
  }
  
  /*
  private void PuniVektorSekvenci()
  {
    Object[] record=new Object[9];
    //ZaSveSemeR=new Object[List3.getModel().getSize()];
    for (int j=0;j<List3.getModel().getSize();j++)
    {      
        
        record[0]=modellst3.getElementAt(j);
        record[1]=Tabela.getValueAt(j,1);
        record[2]=Tabela.getValueAt(j,2);
        record[3]=Tabela.getValueAt(j,3);
        record[4]=Tabela.getValueAt(j,4);
        record[5]=Tabela.getValueAt(j,5);
        record[6]=Tabela.getValueAt(j,6);
        record[7]=Tabela.getValueAt(j,7);
        record[8]=Tabela.getValueAt(j,8);
        TabelaSekvenceri.addElement(record);      
    }
    
  }
  */
  

 

 

  
 /* 
   int rowCount = table.getRowCount();
for(int i=0 ;i <rowCount ; i++)
{
boolean bFlag = Boolean.valueOf(table.getValueAt(i,0).toString().trim()).booleanValue();
if(bFlag) {
table.setSelectionBackground(Color.red);
}
else {
table.setSelectionBackground(Color.WHITE);

}
}
 */
/*// in fProcess()...i added this snippet
// basically , it stops the cell editing , fires the tablechange and sets the focus back to table from the table cell...

if (table.isEditing()) {
table.editingStopped(new ChangeEvent(this));
}
model.fireTableDataChanged();
table.requestFocus();
 */
 /*
  * If you want to force the writing of the cell, then you could just do something like...

TableCellEditor cell = myTable.getCellEditor();
if (cell != null)
cell.stopCellEditing();
  */
}