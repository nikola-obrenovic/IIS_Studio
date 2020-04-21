package iisc.callgraph;

import iisc.*;
import java.awt.*;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JToolBar;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.*;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ItemEvent;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;

public class Form extends JDialog implements ActionListener, ListSelectionListener
{
  private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
  private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
  private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
  private ImageIcon iconUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
  private ImageIcon iconDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
  private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  public PTree tree;
  private Connection connection;
  public String Mnem;
  public int id;
  private int as;
  private int PR_id;
  private ButtonGroup bgrp=new ButtonGroup();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel JPanel1 = new JPanel();
  private JToolBar jToolBar1 = new JToolBar();
  private JButton btnFirst = new JButton();
  private JButton btnPrev = new JButton();
  private JButton btnNext = new JButton();
  private JButton btnLast = new JButton();
  private JButton btnClose = new JButton();
  public JButton btnSave = new JButton();
  private JButton btnNew = new JButton();
  private JButton btnErase = new JButton();
  private JTextArea txtTitle = new JTextArea();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel10 = new JLabel();
  private JTextField txtName = new JTextField();
  private JPanel jPanel2 = new JPanel();
  private JLabel jLabel18 = new JLabel();
  public String appsys=new String();
  private JLabel lbCreateDate = new JLabel();
  private JLabel lbModDate = new JLabel();
  private JLabel jLabel19 = new JLabel();
  private JLabel jLabel110 = new JLabel();
  private JLabel jLabel111 = new JLabel();
  private JComboBox cmbUnitF = new JComboBox(new String[] {"sec.","min.","hour","day","week","month","year"});
  private JComboBox cmbUnitR = new JComboBox(new String[] {"sec.","min.","hour","day","week","month","year"});
  private JTextField txtFreq = new JTextField();
  private JTextField txtResp = new JTextField();
  private JLabel jLabel112 = new JLabel();
  private JLabel jLabel113 = new JLabel();
  private JComboBox cmbUse = new JComboBox(new String[] {"considered in db design","not considered in db design"});
  private JLabel jLabel115 = new JLabel();
  public JTable table = new JTable();
  public JTable tableatt = new JTable();
  public QueryTableModel qtm,qtmatt;
  private ListSelectionModel rowSM,rowSMatt;
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JScrollPane jScrollPane3 = new JScrollPane();
  private JTree objectTree = new JTree();
  private ImageIcon imageExpand = new ImageIcon(IISFrameMain.class.getResource("icons/expand.gif"));
  private ImageIcon imageCollapse = new ImageIcon(IISFrameMain.class.getResource("icons/collapse.gif"));
  private JButton btnAddO = new JButton();
  private JButton btnEditO = new JButton();
  private JButton btnDelO = new JButton();
  private JButton btnAddA = new JButton();
  private JButton btnEditA = new JButton();
  private JButton btnDelA = new JButton();
  private JButton btnAddK = new JButton();
  private JButton btnRemK = new JButton();
  private JButton btnHelp = new JButton();
  public JButton btnApply = new JButton();
  private JPanel jPanel1 = new JPanel();
  private JTextArea txtComment = new JTextArea();
  private JLabel jLabel4 = new JLabel();
  private JComboBox cmbForm = new JComboBox();
  private JRadioButton rdMenu = new JRadioButton();
  private JRadioButton rdProgram = new JRadioButton();
  private JButton btnUp = new JButton();
  private JButton btnDown = new JButton();
  private JButton btnKey = new JButton();
  private boolean dispose=false;
  private JButton btnUnique = new JButton();
  private JButton btnCheck = new JButton();
  private Parameter par;
  private CalledForms cf;
  private int BA_id;
  CallingGraph gr;
  
  public Form(IISFrameMain parent, String title, boolean modal,Connection con,int Tf_id, String _Mnem, int _PR_id, int as_id, String app_sys, PTree tr, int _BA_id, CallingGraph _gr)
  {
    super( parent, title, modal);
    
    try
    { 
        connection=con; 
        id=Tf_id; 
        Mnem = _Mnem;
        as=as_id; 
        PR_id = _PR_id;
        tree=tr;
        appsys = app_sys;
        gr = _gr;
        BA_id = _BA_id;
        
        jbInit();
        cmbForm.setEnabled(false);
        btnPrev.setEnabled(false);
        btnFirst.setEnabled(false);
        btnLast.setEnabled(false);
        btnNext.setEnabled(false);
      
        
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
  
  private void jbInit() throws Exception
  {      
    JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
    setResizable(false);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(new Dimension(763, 359));
    getContentPane().setLayout(null);
    setTitle("Owned Form Types");
    jTabbedPane1.setBounds(new Rectangle(5, 0, 745, 285));
    JPanel1.setLayout(null);
    jToolBar1.setFont(new Font("Verdana", 0, 11));
    jToolBar1.setLayout(null);
    jToolBar1.setPreferredSize(new Dimension(249, 60));
    jToolBar1.setFloatable(false);
    jToolBar1.setBounds(new Rectangle(-10, 290, 315, 35));
    btnFirst.setMaximumSize(new Dimension(60, 60));
    btnFirst.setPreferredSize(new Dimension(25, 20));
    btnFirst.setIcon(iconPrevv);
    
    btnFirst.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          prevv_ActionPerformed(ae);
        }
      });
    btnPrev.setMaximumSize(new Dimension(60, 60));
    btnPrev.setIcon(iconPrev);
    btnPrev.setPreferredSize(new Dimension(25, 20));
    btnPrev.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          prev_ActionPerformed(ae);
        }
      });
    btnNext.setMaximumSize(new Dimension(60, 60));
    btnNext.setIcon(iconNext);
    btnNext.setPreferredSize(new Dimension(25, 20));
    btnNext.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          next_ActionPerformed(ae);
        }
      });
    btnLast.setMaximumSize(new Dimension(60, 60));
    btnLast.setIcon(iconNextt);
    btnLast.setPreferredSize(new Dimension(25, 20));
    btnLast.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          nextt_ActionPerformed(ae);
        }
      });
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Close");
    btnClose.setBounds(new Rectangle(630, 290, 80, 30));
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          close_ActionPerformed(ae);
        }
      });
    btnSave.setMaximumSize(new Dimension(50, 30));
    btnSave.setPreferredSize(new Dimension(50, 30));
    btnSave.setText("OK");
    btnSave.setBounds(new Rectangle(550, 290, 75, 30));
    btnSave.setMinimumSize(new Dimension(50, 30));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save_ActionPerformed(ae);
        }
      });
    btnNew.setMaximumSize(new Dimension(50, 30));
    btnNew.setPreferredSize(new Dimension(50, 30));
    btnNew.setText("New");
    btnNew.setBounds(new Rectangle(465, 290, 80, 30));
    btnNew.setMinimumSize(new Dimension(50, 30));
    btnNew.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          new_ActionPerformed(ae);
        }
      });
    btnErase.setMaximumSize(new Dimension(50, 30));
    btnErase.setPreferredSize(new Dimension(50, 30));
    btnErase.setText("Delete");
    btnErase.setBounds(new Rectangle(385, 290, 75, 30));
    btnErase.setMinimumSize(new Dimension(50, 30));
    btnErase.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          erase_ActionPerformed(ae);
        }
      });
    txtTitle.setBounds(new Rectangle(90, 60, 295, 55));
    txtTitle.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    jLabel3.setText("Title:");
    jLabel3.setBounds(new Rectangle(15, 55, 85, 20));
    jLabel3.setFont(new Font("SansSerif", 0, 11));
    jLabel10.setText("Name:");
    jLabel10.setBounds(new Rectangle(15, 25, 65, 20));
    jLabel10.setFont(new Font("SansSerif", 0, 11));
    txtName.setBounds(new Rectangle(90, 25, 295, 20));
    txtName.setFont(new Font("SansSerif", 0, 11));
    jPanel2.setLayout(null);

   

    jLabel18.setText("Created:");
    jLabel18.setFont(new Font("SansSerif", 0, 11));
    jLabel18.setBounds(new Rectangle(15, 140, 60, 15));
    lbCreateDate.setFont(new Font("Verdana", 0, 11));
    lbCreateDate.setBounds(new Rectangle(90, 140, 300, 15));
    lbModDate.setFont(new Font("Verdana", 0, 11));
    lbModDate.setBounds(new Rectangle(90, 160, 300, 15));
    jLabel19.setText("Last modified:");
    jLabel19.setFont(new Font("SansSerif", 0, 11));
    jLabel19.setBounds(new Rectangle(15, 160, 85, 15));
    jLabel110.setText("Frequency:");
    jLabel110.setFont(new Font("SansSerif", 0, 11));
    jLabel110.setBounds(new Rectangle(15, 200, 75, 15));
    jLabel111.setText("Response:");
    jLabel111.setFont(new Font("SansSerif", 0, 11));
    jLabel111.setBounds(new Rectangle(15, 225, 75, 15));
    cmbUnitF.setBounds(new Rectangle(160, 200, 90, 20));
    cmbUnitR.setBounds(new Rectangle(160, 225, 90, 20));
    txtFreq.setBounds(new Rectangle(90, 200, 40, 20));
    txtFreq.setFont(new Font("SansSerif", 0, 11));
    txtResp.setBounds(new Rectangle(90, 225, 40, 20));
    txtResp.setFont(new Font("SansSerif", 0, 11));
    jLabel112.setText("per");
    jLabel112.setFont(new Font("SansSerif", 0, 11));
    jLabel112.setBounds(new Rectangle(135, 200, 25, 15));
    jLabel113.setText("per");
    jLabel113.setFont(new Font("SansSerif", 0, 11));
    jLabel113.setBounds(new Rectangle(135, 225, 25, 15));
    cmbUse.setBounds(new Rectangle(475, 75, 255, 20));
    jLabel115.setText("Usage:");
    jLabel115.setFont(new Font("SansSerif", 0, 11));
    jLabel115.setBounds(new Rectangle(420, 25, 50, 15));
    qtm=new QueryTableModel(connection,id);
    table=new JTable(qtm);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  //table.setCellSelectionEnabled(false);
    table.setRowSelectionAllowed(true);
    table.setGridColor(new Color(0,0,0));
    table.setBackground(Color.white);
    table.setAutoResizeMode(0);
    table.setAutoscrolls(true);
    table.getTableHeader().setReorderingAllowed(false);
    rowSM = table.getSelectionModel();
    table.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.addMouseListener(new MouseAdapter() {

public void mouseClicked (MouseEvent me) 
{

doMouseClicked();

}

});

qtmatt=new QueryTableModel(connection,id);
    tableatt=new JTable(qtmatt);
    tableatt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  //table.setCellSelectionEnabled(false);
  tableatt.setRowSelectionAllowed(true);
  tableatt.setGridColor(new Color(0,0,0));
    tableatt.setBackground(Color.white);
tableatt.setAutoResizeMode(0);
tableatt.getTableHeader().setReorderingAllowed(false);
tableatt.setAutoscrolls(true);
	rowSMatt = table.getSelectionModel();
  tableatt.setBorder(BorderFactory.createLineBorder(Color.black, 1));
   tableatt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


    jScrollPane1.setBounds(new Rectangle(5, 5, 370, 105));
    jScrollPane3.setBounds(new Rectangle(35, 115, 535, 140));
    jScrollPane2.setBounds(new Rectangle(380, 5, 190, 105));
    objectTree.setFont(new Font("Verdana", 0, 12));
    DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)objectTree.getCellRenderer();
    jTabbedPane1.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          jTabbedPane1_mouseClicked(e);
        }
      });
    txtResp.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtResp_keyTyped(e);
        }
      });
    txtFreq.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtFreq_keyTyped(e);
        }
      });
    txtName.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtName_keyTyped(e);
        }
      });
    jScrollPane1.setFont(new Font("SansSerif", 0, 11));
    jScrollPane2.setFont(new Font("SansSerif", 0, 11));
    jScrollPane3.setFont(new Font("SansSerif", 0, 11));
    btnErase.setFont(new Font("SansSerif", 0, 11));
    btnNew.setFont(new Font("SansSerif", 0, 11));
    btnSave.setFont(new Font("SansSerif", 0, 11));
    btnSave.setEnabled(false);
    btnClose.setFont(new Font("SansSerif", 0, 11));
    cmbUse.setFont(new Font("SansSerif", 0, 11));
    cmbUse.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          cmbUse_mouseClicked(e);
        }
      });
    cmbUse.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          cmbUse_actionPerformed(e);
        }
      });
    cmbUse.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          cmbUse_itemStateChanged(e);
        }
      });
    cmbUnitF.setFont(new Font("SansSerif", 0, 11));
    cmbUnitF.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          cmbUnitF_mouseClicked(e);
        }
      });
    cmbUnitF.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          cmbUnitF_actionPerformed(e);
        }
      });
    cmbUnitF.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          cmbUnitF_itemStateChanged(e);
        }
      });
    cmbUnitR.setFont(new Font("SansSerif", 0, 11));
    cmbUnitR.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          cmbUnitR_mouseClicked(e);
        }
      });
    cmbUnitR.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          cmbUnitR_itemStateChanged(e);
        }
      });
    JPanel1.setFont(new Font("SansSerif", 0, 11));
    txtTitle.setFont(new Font("SansSerif", 0, 11));
    txtTitle.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtTitle_keyTyped(e);
        }
      });
    this.setFont(new Font("SansSerif", 0, 11));
    jPanel2.setFont(new Font("SansSerif", 0, 11));
    btnFirst.setBounds(new Rectangle(20, 5, 25, 20));
    btnPrev.setBounds(new Rectangle(50, 5, 25, 20));
    btnNext.setBounds(new Rectangle(255, 5, 25, 20));
    btnLast.setBounds(new Rectangle(285, 5, 25, 20));
    jToolBar1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
     renderer.setClosedIcon(null);
     renderer.setOpenIcon(null);
    btnCheck.setText("Comp. Type Check Cons.");
    btnCheck.setBounds(new Rectangle(575, 229, 160, 25));
    btnCheck.setActionCommand("Add Obj. Typ.");
    btnCheck.setFont(new Font("SansSerif", 0, 11));
    btnCheck.setEnabled(false);
    btnCheck.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCheck_actionPerformed(e);
        }
      });
    btnUnique.setText("Component Type Uniques");
    btnUnique.setBounds(new Rectangle(575, 202, 160, 25));
    btnUnique.setActionCommand("Add Obj. Typ.");
    btnUnique.setFont(new Font("SansSerif", 0, 11));
    btnUnique.setEnabled(false);
    btnUnique.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnUniques_actionPerformed(e);
        }
      });
    btnKey.setText("Component Type Keys");
    btnKey.setBounds(new Rectangle(575, 175, 160, 25));
    btnKey.setActionCommand("Add Obj. Typ.");
    btnKey.setFont(new Font("SansSerif", 0, 11));
    btnKey.setEnabled(false);
    btnKey.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnKeys_actionPerformed(e);
        }
      });
    btnDown.setMaximumSize(new Dimension(60, 60));
    btnDown.setIcon(iconDown);
    btnDown.setPreferredSize(new Dimension(25, 20));
    btnDown.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          down_ActionPerformed(ae);
        }
      });
    btnDown.setBounds(new Rectangle(5, 235, 25, 20));
    btnUp.setMaximumSize(new Dimension(60, 60));
    btnUp.setIcon(iconUp);
    btnUp.setPreferredSize(new Dimension(25, 20));
    btnUp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          up_ActionPerformed(ae);
        }
      });
    btnUp.setBounds(new Rectangle(5, 115, 25, 20));
    rdProgram.setText("Program");
    rdProgram.setBounds(new Rectangle(470, 50, 90, 20));
    rdProgram.addChangeListener(new ChangeListener()
      {
        public void stateChanged(ChangeEvent e)
        {
          rdProgram_stateChanged(e);
        }
      });
    rdProgram.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rdProgram_actionPerformed(e);
        }
      });
    rdMenu.setText("Menu");
    rdMenu.setBounds(new Rectangle(470, 25, 90, 20));
    rdMenu.setSelected(true);
    rdMenu.addChangeListener(new ChangeListener()
      {
        public void stateChanged(ChangeEvent e)
        {
          rdMenu_stateChanged(e);
        }
      });
    rdMenu.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          rdMenu_actionPerformed(e);
        }
      });
    cmbForm.setFont(new Font("SansSerif", 0, 11));
    cmbForm.setBounds(new Rectangle(80, 5, 170, 20));
    cmbForm.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          cmbAppType_itemStateChanged(e);
        }
      });
    
    txtComment.setBounds(new Rectangle(5, 30, 730, 225));
    txtComment.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    txtComment.setFont(new Font("SansSerif", 0, 11));
    txtComment.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtComment_keyTyped(e);
        }
      });
    jLabel4.setText("Comment:");
    jLabel4.setBounds(new Rectangle(5, 5, 85, 20));
    jLabel4.setFont(new Font("SansSerif", 0, 11));
    btnApply.setMaximumSize(new Dimension(50, 30));
    btnApply.setPreferredSize(new Dimension(50, 30));
    btnApply.setText("Apply");
    btnApply.setBounds(new Rectangle(305, 290, 75, 30));
    btnApply.setMinimumSize(new Dimension(50, 30));
    btnApply.setFont(new Font("SansSerif", 0, 11));
    btnApply.setEnabled(false);
    btnApply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          apply_ActionPerformed(ae);
        }
      });
    jPanel1.setLayout(null);
    btnHelp.setBounds(new Rectangle(715, 290, 35, 30));
    btnHelp.setIcon(imageHelp);
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
      btnRemK.setVisible(false);
    btnRemK.setText("Remove Key");
    btnRemK.setBounds(new Rectangle(615, 195, 120, 20));
    btnRemK.setActionCommand("Add Obj. Typ.");
    btnRemK.setFont(new Font("SansSerif", 0, 11));
    btnRemK.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnRemK_actionPerformed(e);
        }
      });
    btnAddK.setVisible(false);
    btnAddK.setText("Add Key");
    btnAddK.setBounds(new Rectangle(615, 170, 120, 20));
    btnAddK.setActionCommand("Add Obj. Typ.");
    btnAddK.setFont(new Font("SansSerif", 0, 11));
    btnAddK.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnAddK_actionPerformed(e);
        }
      });
    btnDelA.setText("Delete Attribute");
    btnDelA.setBounds(new Rectangle(575, 144, 160, 25));
    btnDelA.setActionCommand("Add Obj. Typ.");
    btnDelA.setFont(new Font("SansSerif", 0, 11));
    btnDelA.setEnabled(false);
    btnDelA.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDelA_actionPerformed(e);
        }
      });
    btnEditA.setText("Edit Atributte");
    btnEditA.setBounds(new Rectangle(575, 117, 160, 25));
    btnEditA.setActionCommand("Add Obj. Typ.");
    btnEditA.setFont(new Font("SansSerif", 0, 11));
    btnEditA.setEnabled(false);
    btnEditA.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnEditA_actionPerformed(e);
        }
      });
    btnAddA.setText("Add Attribute");
    btnAddA.setBounds(new Rectangle(575, 90, 160, 25));
    btnAddA.setActionCommand("Add Obj. Typ.");
    btnAddA.setFont(new Font("SansSerif", 0, 11));
    btnAddA.setEnabled(false);
    btnAddA.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnAddA_actionPerformed(e);
        }
      });
    btnDelO.setText("Delete Component Type");
    btnDelO.setBounds(new Rectangle(575, 59, 160, 25));
    btnDelO.setActionCommand("Add Obj. Typ.");
    btnDelO.setFont(new Font("SansSerif", 0, 11));
    btnDelO.setEnabled(false);
    btnDelO.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDelO_actionPerformed(e);
        }
      });
    btnEditO.setText("Edit Component Type");
    btnEditO.setBounds(new Rectangle(575, 32, 160, 25));
    btnEditO.setActionCommand("Add Obj. Typ.");
    btnEditO.setFont(new Font("SansSerif", 0, 11));
    btnEditO.setEnabled(false);
    btnEditO.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnEditO_actionPerformed(e);
        }
      });
    btnAddO.setText("Add Component Type");
    btnAddO.setBounds(new Rectangle(575, 5, 160, 25));
    btnAddO.setActionCommand("Add Obj. Typ.");
    btnAddO.setFont(new Font("SansSerif", 0, 11));
    btnAddO.setEnabled(false);
    btnAddO.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnAddO_actionPerformed(e);
        }
      });
    bgrp.add(rdMenu);
    bgrp.add(rdProgram);
    jToolBar1.add(cmbForm, null);
    jToolBar1.add(btnFirst, null);
    jToolBar1.add(btnPrev, null);
    jToolBar1.add(btnNext, null);
    jToolBar1.add(btnLast, null);
    JPanel1.add(rdProgram, null);
    JPanel1.add(rdMenu, null);
    JPanel1.add(jLabel115, null);
    JPanel1.add(cmbUse, null);
    JPanel1.add(jLabel113, null);
    JPanel1.add(jLabel112, null);
    JPanel1.add(txtResp, null);
    JPanel1.add(txtFreq, null);
    JPanel1.add(cmbUnitR, null);
    JPanel1.add(cmbUnitF, null);
    JPanel1.add(jLabel111, null);
    JPanel1.add(jLabel110, null);
    JPanel1.add(jLabel19, null);
    JPanel1.add(lbModDate, null);
    JPanel1.add(lbCreateDate, null);
    JPanel1.add(jLabel18, null);
    JPanel1.add(txtName, null);
    JPanel1.add(jLabel10, null);
    JPanel1.add(jLabel3, null);
    JPanel1.add(txtTitle, null);
    jPanel2.add(btnCheck, null);
    jPanel2.add(btnUnique, null);
    jPanel2.add(btnKey, null);
    jPanel2.add(btnDown, null);
    jPanel2.add(btnUp, null);
    jPanel2.add(btnRemK, null);
    jPanel2.add(btnAddK, null);
    jPanel2.add(btnDelA, null);
    jPanel2.add(btnEditA, null);
    jPanel2.add(btnAddA, null);
    jPanel2.add(btnDelO, null);
    jPanel2.add(btnEditO, null);
    jPanel2.add(btnAddO, null);
    jPanel2.add(jScrollPane2, null);
    jScrollPane1.getViewport().add(table, null);
    jPanel2.add(jScrollPane1, null);
    jScrollPane3.getViewport().add(tableatt, null);
    jPanel2.add(jScrollPane3, null);
    jTabbedPane1.addTab("Form Type", JPanel1);
    jTabbedPane1.addTab("Component Types", jPanel2);
    
    jPanel1.add(jLabel4, null);
    jPanel1.add(txtComment, null);
    this.getContentPane().add(btnApply, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(jTabbedPane1, null);
    this.getContentPane().add(btnClose, null);
    this.getContentPane().add(btnSave, null);
    this.getContentPane().add(btnNew, null);
    this.getContentPane().add(btnErase, null);
    this.getContentPane().add(jToolBar1, null);
  
    setForm(Mnem);
    doMouseClicked ();
    par = new Parameter(jTabbedPane1.getWidth(), jTabbedPane1.getHeight(),(IISFrameMain)getOwner(), this, connection, PR_id);
    cf = new CalledForms(jTabbedPane1.getWidth(), jTabbedPane1.getHeight(),(IISFrameMain)getOwner(), this, connection, PR_id, BA_id, gr);
    btnNew.setEnabled(false);
    btnErase.setEnabled(false);
    jTabbedPane1.addTab("Parameters", par);
    jTabbedPane1.addTab("Called Form Types", cf);
    jTabbedPane1.addTab("Notes", jPanel1);
    
 }
 public void actionPerformed(ActionEvent ae)
        {
       
        }
void doMouseClicked () {


try
{JDBCQuery query=new JDBCQuery(connection);
 ResultSet rs;
  if(table.getRowCount()>0)
{ if(table.getSelectedRowCount()>0)
{ rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE  where  PR_id="+ PR_id +" and TOB_mnem='"+ table.getValueAt(table.getSelectedRow(),0) +"' and TF_id=" + id);
 if(rs.next())
 qtmatt.setQueryA("select * from IISC_ATT_TOB where  PR_id="+ PR_id +" and  TF_id="+ id +" and TOB_id="+rs.getInt("TOB_id") );
 else
 qtmatt.setQueryA("select * from IISC_ATT_TOB where TOB_id=-1" );
query.Close();
 }
 for (int i = 0; i < objectTree.getRowCount(); i++) 
  {objectTree.expandRow(i);
  }
  for (int k = 0; k < objectTree.getRowCount(); k++) {
  TreePath pt= objectTree.getPathForRow(k);
  if(pt.getLastPathComponent().toString().equals(table.getValueAt(table.getSelectedRow(),0).toString() ))
 { objectTree.setSelectionRow(k);
   break;
 }
  }
}
}
 catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}
}
 void doMouseClicked1 () {
 for(int i=0;i<table.getRowCount();i++)
 if(table.getValueAt(i,0).equals(objectTree.getSelectionPath().getLastPathComponent().toString()))
 {table.setRowSelectionInterval(i,i);  
 doMouseClicked();}
 }
  private void prevv_ActionPerformed(ActionEvent e)
  {
  String s="";
 JDBCQuery query=new JDBCQuery(connection);
ResultSet rs1;
try
{
 if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
 rs1=query.select("select * from IISC_Form_TYPE,IISC_TF_APPSYS  where IISC_TF_APPSYS.AS_id="+ as +" and IISC_Form_TYPE.TF_id=IISC_TF_APPSYS.TF_id and  PR_id="+ PR_id +"  order by  TF_mnem asc");
 if(rs1.next())
{s=rs1.getString(3);
}
query.Close();
 
setForm(s);
Mnem=s;
  //tree.select_node(Mnem,"Owned","Form Types",appsys);
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}


  }
private void prev_ActionPerformed(ActionEvent e)
  {
  String s=Mnem.trim() ;

 JDBCQuery query=new JDBCQuery(connection);
ResultSet rs1;
try
{
 if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
rs1=query.select("select TF_mnem from IISC_Form_TYPE,IISC_TF_APPSYS  where IISC_TF_APPSYS.AS_id="+ as +" and IISC_Form_TYPE.TF_id=IISC_TF_APPSYS.TF_id and  IISC_Form_TYPE.PR_id="+ PR_id +"  and TF_mnem<'" + s + "' order by TF_mnem desc" );
if(rs1.next())
{

s=rs1.getString(1);
}
query.Close();
 
setForm(s);
Mnem=s;
  //tree.select_node(Mnem,"Owned","Form Types",appsys);


}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}


  }


private void next_ActionPerformed(ActionEvent e)
 {
  String s=Mnem.trim() ;

 JDBCQuery query=new JDBCQuery(connection);
ResultSet rs1;
try
{
 if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
 rs1=query.select("select TF_mnem from IISC_Form_TYPE,IISC_TF_APPSYS  where IISC_TF_APPSYS.AS_id="+ as +" and IISC_Form_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_Form_TYPE.PR_id="+ PR_id +" and TF_mnem>'" + s + "' order by TF_mnem asc" );
if(rs1.next())
{
s=rs1.getString(1);
}
query.Close();
setForm(s);
Mnem=s;
  //tree.select_node(Mnem,"Owned","Form Types",appsys);
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}

 }


  private void nextt_ActionPerformed(ActionEvent e)
 {
  String s="";
 JDBCQuery query=new JDBCQuery(connection);
ResultSet rs1;
try
{ if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
 rs1=query.select("select * from IISC_Form_TYPE,IISC_TF_APPSYS  where IISC_TF_APPSYS.AS_id="+ as +" and IISC_Form_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_Form_TYPE.PR_id="+ PR_id +" order by  TF_mnem desc");
if(rs1.next())
{s=rs1.getString(3);
}
query.Close();
 
setForm(s);
Mnem=s;
//tree.select_node(Mnem,"Owned","Form Types",appsys);
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}


  }


 private void close_ActionPerformed(ActionEvent e)
  {
   if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      {
          tree.pravi_drvo();
          par.Update();
          cf.Update();
          update(1);
      }
  this.dispose();
  System.runFinalization();

  }
 public void readonly()
  {
  txtComment.setEditable(false);
  txtComment.setEnabled(false);
  txtFreq.setEditable(false);
  txtFreq.setEnabled(false);
  txtName.setEditable(false);
  txtName.setEnabled(false);
  txtResp.setEditable(false);
  txtResp.setEnabled(false);
  txtTitle.setEditable(false);
  txtTitle.setEnabled(false);
  btnAddA.setEnabled(false);
  btnAddK.setEnabled(false);
  btnAddO.setEnabled(false);
  btnApply.setEnabled(false);
  btnDelA.setEnabled(false);
  btnDelO.setEnabled(false);
  btnEditA.setEnabled(false);
  btnEditO.setEnabled(false);
  btnErase.setEnabled(false);
  btnFirst.setEnabled(false);
  btnLast.setEnabled(false);
  btnNew.setEnabled(false);
  btnNext.setEnabled(false);
  btnRemK.setEnabled(false);
  btnPrev.setEnabled(false);
  btnSave.setEnabled(false);
  btnKey.setEnabled(false);
  rdMenu.setEnabled(false);
  rdProgram.setEnabled(false);
  cmbForm.setEnabled(false);
  cmbUnitF.setEnabled(false);
  cmbUnitR.setEnabled(false);
  cmbUse.setEnabled(false);
  btnUnique.setEnabled(false);
  btnCheck.setEnabled(false);
  btnUp.setEnabled(false);
   btnDown.setEnabled(false);
  }
 private void save_ActionPerformed(ActionEvent e)
{
  
  if(update(1)>0)
  {//tree.select_node(Mnem,"Owned","Form Types",appsys);
  tree.pravi_drvo();
  par.Update();
  cf.Update();
  dispose();}
}
 private int tree_root()
  {
 JDBCQuery query=new JDBCQuery(connection);
ResultSet rs;
int i;
try
{rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id="+ id +" and TOB_superord is null and PR_id="+ PR_id +" ");
if(rs.next())
i=rs.getInt(1);
else
i=-1;
query.Close();
return i;}
  catch(SQLException e)
{
 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
return -1;
}

private Object[] tree_objects(String s)
{
    JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs1,rs;
    
    try
    {
        rs1=query.select("select count(*) from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id="+ id +" and TOB_superord=" + s + " and PR_id="+ PR_id +" ");
        
        if(rs1.next())
        {
            int g=rs1.getInt(1)+1;  
            query.Close();

            if (s.equals(""))
            {
                g = g - 1;
            }
            Object[] obj=new Object[g];

            rs1=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id="+ id +" and TOB_id=" + s + " and PR_id="+ PR_id +" ");
            rs1.next();
            obj[0]=rs1.getString("Tob_mnem");
            query.Close();
            rs1=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id="+ id +" and TOB_superord=" + s + " and PR_id="+ PR_id+" order by  Tob_mnem asc");
            int i = 1;
            while(rs1.next())
            {
              int ik=rs1.getInt("Tob_id");
// rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id="+ id +" and TOB_superord=" +ik + " and PR_id="+ PR_id +" order by  Tob_mnem asc");
//if(rs.next())
//{
              obj[i]=tree_objects(""+ik);
//}
//else
//obj[i]=rs1.getString("Tob_mnem");
              i=i+1;
            }  
            query.Close();
            return obj;
        }
        else
        {
            query.Close();
        }
    }

    catch(SQLException e)
    {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return null;
}
  
private void new_ActionPerformed(ActionEvent e)
{
  
  Mnem="";
  id=-1;
  setForm("");
  jTabbedPane1.setSelectedIndex(0);
}
private DefaultMutableTreeNode processHierarchy(Object[] hierarchy) 
{
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy[0]);
    DefaultMutableTreeNode child;
    
    for(int i=1; i<hierarchy.length; i++) 
    {
        Object nodeSpecifier = hierarchy[i];
        if (nodeSpecifier instanceof Object[])  // Ie node with children
        {
          child = processHierarchy((Object[])nodeSpecifier);
        }
        else
        {
            child = new DefaultMutableTreeNode(nodeSpecifier); // Ie Leaf
        }
        node.add(child);
    }
    return(node);
  }

public int update (int k)
{
JDBCQuery query=new JDBCQuery(connection);
ResultSet rs1;  
boolean can=true;
System.out.println(txtName.getText().trim());
try
{
if(Mnem.trim().equals(""))
{
 rs1=query.select("select * from IISC_Form_TYPE  where PR_id="+ PR_id +" and TF_mnem='"+ txtName.getText().trim() +"'");
if(rs1.next())
 {JOptionPane.showMessageDialog(null, "<html><center>Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
can=false;
 }
 query.Close();
}
else
{
rs1=query.select("select * from IISC_Form_TYPE  where PR_id="+ PR_id +" and TF_mnem='"+ Mnem +"'");
if(rs1.next())id=rs1.getInt("TF_id");
query.Close();
if(!Mnem.toLowerCase().trim().equals(txtName.getText().toLowerCase().trim()))
{
rs1=query.select("select * from IISC_Form_TYPE  where PR_id="+ PR_id +" and TF_mnem='"+ txtName.getText().trim() +"'");
if(rs1.next())
{ JOptionPane.showMessageDialog(null, "<html><center>Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
can=false;
}
query.Close();
}  
}
//if(!Mnem.equals(""))
if(txtName.getText().trim().equals(""))
JOptionPane.showMessageDialog(null, "<html><center>Name required!", "Form Types", JOptionPane.ERROR_MESSAGE);
else if(txtTitle.getText().trim().equals(""))
JOptionPane.showMessageDialog(null, "<html><center>Title required!", "Form Types", JOptionPane.ERROR_MESSAGE);
/*else if(txtResp.getText().trim().equals(""))
JOptionPane.showMessageDialog(null, "<html><center>Response required!", "Form Types", JOptionPane.ERROR_MESSAGE);
else if(txtFreq.getText().trim().equals(""))
JOptionPane.showMessageDialog(null, "<html><center>Frequency required!", "Form Types", JOptionPane.ERROR_MESSAGE);*/
else if(can)
{
int use=2;
if(rdProgram.isSelected())
{
  use=cmbUse.getSelectedIndex(); 
}
if(Mnem.trim().equals(""))
{
rs1=query.select("select max(TF_id)+1  from IISC_Form_TYPE");
if(rs1.next())
id=rs1.getInt(1);
else 
id=0;
query.Close();

int i=query.update("insert into IISC_Form_TYPE(Tf_id,PR_ID,Tf_mnem,Tf_title,Tf_crdate,Tf_freq,Tf_freq_unit,Tf_moddate,Tf_rest,Tf_rest_unit,Tf_use,Tf_comment) values ("+ id +","+ PR_id +",'" + txtName.getText().trim() + "','" + txtTitle.getText().trim() + "','" + ODBCList.now() + "','" + txtFreq.getText() + "','" + cmbUnitF.getSelectedItem().toString() + "','" + ODBCList.now() + "','" + txtResp.getText() + "','" + cmbUnitR.getSelectedItem().toString() + "'," + use + ",'" + txtComment.getText() + "')");
i=query.update("insert into IISC_TF_APPSYS(Tf_id,AS_id,PR_id) values ("+ id +","+ as +","+ PR_id +")");
//tree.insert(txtName.getText().trim(),"Owned","Form Types",appsys);
Mnem=txtName.getText().trim();
setForm(Mnem);
}
else
{
 int i=query.update("update IISC_Form_TYPE set TF_mnem='" + txtName.getText().trim() + "',TF_freq_unit='" + cmbUnitF.getSelectedItem().toString() + "',TF_title='" + txtTitle.getText().trim() + "',TF_rest_unit='" + cmbUnitR.getSelectedItem().toString() + "',TF_freq='" + txtFreq.getText() + "',TF_rest='" + txtResp.getText() + "',TF_moddate='" + ODBCList.now() + "',TF_comment='" + txtComment.getText().toString() + "',TF_use=" + use + " where TF_id=" + id + ""); 
Mnem= txtName.getText().trim();
//tree.change(Mnem ,"Owned","Form Types",txtName.getText().trim(),appsys);
}
btnApply.setEnabled(false);
btnSave.setEnabled(false);
if(k==1)
{JOptionPane.showMessageDialog(null, "<html><center>Form Type saved!", "Form Types", JOptionPane.INFORMATION_MESSAGE);
return 1;
}
}

}
catch(SQLException e)
{
 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}

return 0;
}
public void setForm (String m)
  
 { 
    try
    { 
    
    Mnem=m;
    this.btnSave.setEnabled(false);
    this.btnApply.setEnabled(false);
    txtName.setText(m);
    JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
    ResultSet rs1;
      rs=query.select("select count(*) from IISC_FORM_TYPE,IISC_TF_APPSYS  where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and AS_ID="+ as +" and IISC_FORM_TYPE.PR_ID="+ PR_id);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] sa=query.selectArray("select * from IISC_FORM_TYPE,IISC_TF_APPSYS where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and  AS_ID="+ as +" and IISC_FORM_TYPE.PR_ID="+ PR_id ,j,3);
    sa[0]="";
    query.Close();
   cmbForm.removeAllItems();
    for(int k=0;k<sa.length; k++)
    cmbForm.addItem(sa[k]);
    cmbForm.setSelectedItem(m);
 
  if(Mnem.equals(""))
  { //try{tree.select_node(Mnem,"Owned","Form Types",appsys);}catch(Exception e){}
    btnAddA.setEnabled(false);
    btnEditA.setEnabled(false);
    btnDelA.setEnabled(false);
    btnKey.setEnabled(false);
    btnAddO.setEnabled(false);
    btnEditO.setEnabled(false);
    btnDelO.setEnabled(false);
    btnUnique.setEnabled(false);
    btnCheck.setEnabled(false);
  }
  else
  {
    btnAddA.setEnabled(true);
    btnEditA.setEnabled(true);
    btnDelA.setEnabled(true);
    btnKey.setEnabled(true);
    btnUnique.setEnabled(true);
    btnAddO.setEnabled(true);
    btnEditO.setEnabled(true);
    btnDelO.setEnabled(true);
    btnCheck.setEnabled(true);
  }
     rs1=query.select("select * from IISC_Form_TYPE where PR_id="+ PR_id +"  and TF_mnem='"+ Mnem +"'");
     if(rs1.next())
    {   int use=rs1.getInt("TF_use");
        txtName.setText(rs1.getString("TF_mnem"));
        txtTitle.setText(rs1.getString("TF_title"));
        lbCreateDate.setText(rs1.getString("Tf_crdate").toString());
        lbModDate.setText(rs1.getString("Tf_moddate").toString());
        txtResp.setText(rs1.getString("Tf_rest"));
        txtFreq.setText(rs1.getString("Tf_freq"));
        cmbUnitR.setSelectedItem(rs1.getString("Tf_rest_unit"));
        cmbUnitF.setSelectedItem(rs1.getString("Tf_freq_unit"));
        txtComment.setText(rs1.getString("Tf_comment"));
        jLabel18.setVisible(true);
        jLabel19.setVisible(true);
        id=rs1.getInt("TF_id");
        if(use==2)
        {rdMenu.setSelected(true);
         rdProgram.setSelected(false); 
         cmbUse.setEnabled(false);
         jPanel2.setEnabled(false);
        }
        else
        {
         rdProgram.setSelected(true); 
         cmbUse.setSelectedIndex(use);
         rdMenu.setSelected(false);
         jPanel2.setEnabled(true);
        }
    }
  else
  {     
        
        txtName.setText("");
        txtTitle.setText("");
        lbCreateDate.setText("");
        lbModDate.setText("");
        txtResp.setText("");
        txtFreq.setText("");
        jLabel18.setVisible(false);
        jLabel19.setVisible(false);
        id=-1;
        txtComment.setText("");
        rdMenu.setSelected(true);
         rdProgram.setSelected(false); 
         cmbUse.setEnabled(false);
         jPanel2.setEnabled(false);
  }

query.Close();
qtm.setQueryO("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tf_id="+id+ " order by TOB_superord");
qtmatt.setQueryA("select * from IISC_ATT_TOB where Att_id=-1");
int width = 120;
table.getColumnModel().getColumn(0).setPreferredWidth(width);
table.getColumnModel().getColumn(1).setPreferredWidth(width);
table.getColumnModel().getColumn(2).setPreferredWidth(width);
table.getSelectionModel().setSelectionInterval(0,0);
doMouseClicked();
tableatt.getSelectionModel().setSelectionInterval(0,0);
if(tree_root()>=0)
{ 
 DefaultMutableTreeNode root = processHierarchy(tree_objects("" + tree_root()));
 objectTree=new JTree(root);
 objectTree.setShowsRootHandles(true);
 DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)objectTree.getCellRenderer();
 renderer.setClosedIcon(null);
 renderer.setOpenIcon(null);
}
else
{
  DefaultMutableTreeNode root = processHierarchy(new Object[] {"..."});
 objectTree=new JTree(root);
}
objectTree.addMouseListener(new MouseAdapter() {

public void mouseClicked (MouseEvent me) {

doMouseClicked1();

}

});
 jScrollPane2.getViewport().add(objectTree, null);
rs1=query.select("select * from IISC_Form_TYPE,IISC_TF_APPSYS  where IISC_TF_APPSYS.AS_id="+ as +" and IISC_Form_TYPE.TF_id=IISC_TF_APPSYS.TF_id and  IISC_FORM_TYPE.PR_id="+ PR_id +" and TF_mnem>'" + m.trim() + "'" );
if(rs1.next())
{btnNext.setEnabled(true);
btnLast.setEnabled(true);}
else
{btnNext.setEnabled(false);
btnLast.setEnabled(false);}
query.Close();
rs1=query.select("select * from  IISC_Form_TYPE,IISC_TF_APPSYS  where IISC_TF_APPSYS.AS_id="+ as +" and IISC_Form_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_FORM_TYPE.PR_id="+ PR_id +" and  TF_mnem<'" + m.trim() + "'" );
if(rs1.next())
{btnPrev.setEnabled(true);
btnFirst.setEnabled(true);}
else
{btnPrev.setEnabled(false);
btnFirst.setEnabled(false);}
query.Close();
 if(rdMenu.isSelected())
  jTabbedPane1.setEnabledAt(1,false);
  else
  jTabbedPane1.setEnabledAt(1,true);

}

catch(SQLException e)
{
 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}
}

 private void erase_ActionPerformed(ActionEvent e)
  {
  if(delete(id))
  {tree.removenode(Mnem,"Owned");
  Mnem="";
  setForm(Mnem);
  //tree.select_node(Mnem,"Owned","Form Types",appsys);
  }
  }


   public boolean delete(int idd)
   {
   JDBCQuery query=new JDBCQuery(connection);
   JDBCQuery query2=new JDBCQuery(connection);
   ResultSet rs1;  
   ResultSet rs2;

   boolean can=true;
   if(Mnem.equals(""))can=false;
   try
   {
   rs1=query.select("select * from IISC_ATT_TOB  where TF_id="+ idd +"");
   if(rs1.next())
   { 
   if(!(JOptionPane.showConfirmDialog(null, "<html><center>This will remove form type and all its components.<br>Do you want to continue?", "Form Types", JOptionPane.YES_NO_OPTION)==0))
   can=false;
   } 
   query.Close();
   rs1=query.select("select * from IISC_DER_ATT_FUN  where DA_id="+ idd +"");
   if(rs1.next())
   { JOptionPane.showMessageDialog(null, "<html><center>Form can not be removed!", "Error", JOptionPane.ERROR_MESSAGE);
   can=false;
   } 
   query.Close();

   rs1=query.select("select * from IISC_BUSINESS_APPLICATION  where Tf_entry_id="+ idd +"");
   if(rs1.next())
   { 
   JOptionPane.showMessageDialog(null, "<html><center>Form can not be removed!", "Error", JOptionPane.ERROR_MESSAGE);
   can=false;
   } 
   query.Close();

   if(can)
   {
    int LV_id;

   rs2 = query2.select("select LV_id from IISC_ATT_TOB where PR_id="+ tree.ID +" and Tf_id="+ idd +"");
     
   while( rs2.next() )
   {
       LV_id = rs2.getInt(1);
       query.update("delete from IISC_LIST_OF_VALUES where LV_id=" + LV_id);
       query.update("delete from IISC_LV_RETURN where LV_id=" + LV_id);
   }

   query2.Close();

   rs2 = query2.select("select IG_id from IISC_ITEM_GROUP where PR_id="+ tree.ID +" and Tf_id="+ idd +"");
       
   while( rs2.next() )
   {
           int ig_id = rs2.getInt(1);
           query.update("delete from IISC_IG_ITEM where IG_id=" + ig_id);
   }
           
   query2.Close();
   query.update("delete from IISC_ITEM_GROUP  where  PR_id="+ tree.ID +" and Tf_id="+ idd +"");
       
    query.update("delete from IISC_APP_SYS_REFERENCE where TF_id=" + idd + ""); 
    query.update("delete from IISC_ATT_TOB where TF_id=" + idd + ""); 
    query.update("delete from IISC_ATT_KTO where TF_id=" + idd + ""); 
    query.update("delete from IISC_ATT_UTO where TF_id=" + idd + ""); 
    query.update("delete from IISC_UNIQUE_TOB where TF_id=" + idd + ""); 
    query.update("delete from IISC_KEY_TOB where TF_id=" + idd + ""); 
    query.update("delete from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id=" + idd + ""); 
    query.update("delete from IISC_TF_APPSYS where TF_id=" + idd + ""); 
    query.update("delete from IISC_Form_TYPE where TF_id=" + idd + ""); 
    query.update("delete from IISC_CALL_GRAPH_VERTEX where caller_Tf=" + idd + "");
    query.update("delete from IISC_CALL_GRAPH_VERTEX  where called_Tf=" + idd + "");
    query.update("delete from IISC_CALL_GRAPH_NODE where Tf_id=" + idd + "");
    query.update("delete from IISC_COMPTYPE_DISPLAY where Tf_id=" + idd + "");
    query.update("delete from IISC_COMPTYPE_ATTRIB_DISPLAY where Tf_id=" + idd + "");
    query.update("delete from IISC_COMP_ATT_DISPLAY_VALUES where Tf_id=" + idd + "");
    
    query.update("delete from IISC_FUN_PARAM_VALUE where PR_id="+ tree.ID +" and Tf_id="+ idd +"");
    query.update("delete from IISC_FUN_PARAM_VALUE where PR_id="+ tree.ID +" and Value_Tf_id="+ idd +"");
    query.update("delete from IISC_ATT_TOB_EVENT where PR_id="+ tree.ID +" and Tf_id="+ id +"");
    query.update("delete from IISC_TOB_BUTTON where PR_id="+ tree.ID +" and Tf_id="+ id +"");
       
     rs2 = query.select("select CS_id from IISC_CALLING_STRUCT where Caller_Tf_id=" + idd + " or Caller_Tf_id=" + idd);
     
     while(rs2.next())
     {
         query.update("delete from IISC_PASSED_VALUE where CS_id=" + rs2.getString("CS_id"));
     }

   query.update("delete from IISC_CALLING_STRUCT where Caller_Tf_id=" + idd + "");
   query.update("delete from IISC_CALLING_STRUCT where Called_Tf_id=" + idd + "");
   query.update("delete from IISC_PARAMETER where Tf_id=" + idd + "");


           
   JOptionPane.showMessageDialog(null, "<html><center>Form type removed!", "Form Types", JOptionPane.INFORMATION_MESSAGE);
   }

   }
   catch(SQLException e)
   {
    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

   }
   return can;
}


public void valueChanged(ListSelectionEvent e){}

  private void btnAddO_actionPerformed(ActionEvent e)
  {//btnSave.setEnabled(true);
  //btnApply.setEnabled(true);
 ObjectType obj =new  ObjectType((IISFrameMain) getParent(),"Add Component Type",-1, true, connection,this );
 Settings.Center(obj);
 obj.setVisible(true);
  }

  private void btnEditO_actionPerformed(ActionEvent e)
  {//btnSave.setEnabled(true);
  //btnApply.setEnabled(true);
  JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
 try{
rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE  where  PR_id="+ PR_id +" and TOB_mnem='"+ table.getValueAt(table.getSelectedRow(),0) +"' and Tf_id="+id);
 if(rs.next())
 {
  ObjectType obj =new  ObjectType((IISFrameMain) getParent(),"Add Component Type",rs.getInt(1), true, connection,this );
 Settings.Center(obj);
 obj.setVisible(true);
 }
query.Close();
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
}

     private void btnDelO_actionPerformed(ActionEvent e)
     {//btnSave.setEnabled(true);
       //btnApply.setEnabled(true);
      JDBCQuery query=new JDBCQuery(connection);
      JDBCQuery query2=new JDBCQuery(connection);
      ResultSet rs;
      ResultSet rs2;
      int LV_id;
      
      try
      {
         int h;
         rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE  where  PR_id="+ tree.ID +" and TOB_mnem='"+ table.getValueAt(table.getSelectedRow(),0) +"' and Tf_id="+id);
         
         if(rs.next())
         {
             h=rs.getInt(1);
             query.Close();
       
             rs2 = query2.select("select LV_id from IISC_ATT_TOB where PR_id="+ tree.ID +" and TOB_id="+ h +"");
       
             while( rs2.next() )
             {
                 LV_id = rs2.getInt(1);
                 query.update("delete from IISC_LIST_OF_VALUES where LV_id=" + LV_id);
                 query.update("delete from IISC_LV_RETURN where LV_id=" + LV_id);
             }
             
             query2.Close();
             
             rs2 = query2.select("select IG_id from IISC_ITEM_GROUP where PR_id="+ tree.ID +" and TOB_id="+ h +"");
                   
             while( rs2.next() )
             {
                 int ig_id = rs2.getInt(1);
                 query.update("delete from IISC_IG_ITEM where IG_id=" + ig_id);
             }
                       
             query2.Close();
             query.update("delete from IISC_ITEM_GROUP  where  PR_id="+ tree.ID +" and TOB_id="+ h +"");
                   
             query.update("delete from IISC_ATT_UTO  where  PR_id="+ tree.ID +" and TOB_id="+ h +"");
             query.update("delete from IISC_ATT_KTO  where  PR_id="+ tree.ID +" and TOB_id="+ h +"");
             query.update("delete from IISC_ATT_TOB  where  PR_id="+ tree.ID +" and TOB_id="+ h +"");
             query.update("delete from IISC_UNIQUE_TOB  where  TOB_id="+ h +"");
             query.update("delete from IISC_KEY_TOB  where  TOB_id="+ h +"");
             
             query.update("delete from IISC_COMPTYPE_DISPLAY where  TOB_id=" + h);
             query.update("delete from IISC_COMP_ATT_DISPLAY_VALUES  where  PR_id="+ tree.ID +" and TOB_id="+ h +" and Tf_id="+ id +"");
             query.update("delete from IISC_COMPTYPE_ATTRIB_DISPLAY  where  PR_id="+ tree.ID +" and TOB_id="+ h +" and Tf_id="+ id +"");
                   
             query.update("delete from IISC_FUN_PARAM_VALUE where PR_id="+ tree.ID +" and Tob_id="+ h +" and Tf_id="+ id +"");
             query.update("update IISC_FUN_PARAM_VALUE set Value_type=0 where PR_id="+ tree.ID +" and Value_Tob_id="+ h +" and Value_Tf_id="+ id +"");
             query.update("delete from IISC_ATT_TOB_EVENT where PR_id="+ tree.ID +" and Tob_id="+ h +" and Tf_id="+ id +"");
             query.update("delete from IISC_TOB_BUTTON where PR_id="+ tree.ID +" and Tob_id="+ h +" and Tf_id="+ id +"");
                   
             query.update("delete from IISC_COMPONENT_TYPE_OBJECT_TYPE  where  PR_id="+ tree.ID +" and TOB_id="+ h +"");
             
             query.update("update IISC_COMPONENT_TYPE_OBJECT_TYPE set Tob_superord=null where  PR_id="+ tree.ID +" and Tob_superord="+ h +"");
             setForm(Mnem);
      
               try
               {
                   tree.setVisible(false);
                   tree.pravi_drvo();
                   tree.select_node(Mnem,"Owned","Form Types",appsys);
               }
               catch (Exception ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);}
               catch (Throwable ex) {JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);}
           
               JOptionPane.showMessageDialog(null, "<html><center>Component Type deleted!", "Form Types", JOptionPane.INFORMATION_MESSAGE);
               }
               else
                   query.Close();
           }
           catch(SQLException ex)
           {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
           }
       
       }

private void btnAddK_actionPerformed(ActionEvent e)
{
   JDBCQuery query=new JDBCQuery(connection);
 ResultSet rs,rs1;
 int f;
 try{
rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE  where  PR_id="+ PR_id +" and TOB_mnem='"+ table.getValueAt(table.getSelectedRow(),0) +"' and Tf_id="+ id );
 if(rs.next())
 {
  f=rs.getInt(1);
   query.Close();
  int key=1;
  rs=query.select("select max(Tob_rbrk)+1 from IISC_ATT_KTO  where   PR_id="+ PR_id +" and TOB_id="+ f +" and Tf_id="+ id );
  if(rs.next())key=rs.getInt(1);
  if(key==0)key=1;
  query.Close();
   for (int k=0;k<tableatt.getRowCount();k++)
{if(tableatt.isRowSelected(k))
{int fi=-1;
rs1=query.select("select * from IISC_ATT_TOB,IISC_ATTRIBUTE  where  IISC_ATT_TOB.PR_id="+ PR_id +" and IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id and  Att_mnem='"+ tableatt.getValueAt(k,0) +"'  and Tf_id="+ id );
if(rs1.next())fi=rs1.getInt(1);
query.Close();
query.update("insert into IISC_ATT_KTO(Att_id,Tf_id,Tob_id,PR_id,Tob_rbrk,Att_rbrk) values("+ fi +","+ id +","+ f +","+ PR_id +","+ key +")");
}
}
query.update("insert into IISC_KEY_TOB(Tf_id,Tob_id,Tob_rbrk,PR_id,Tob_local) values("+ id +","+ f +","+ key +","+ PR_id +")");
 qtmatt.setQueryA("select * from IISC_ATT_TOB where PR_id="+ PR_id +" and TOB_id="+f + " and Tf_id="+ id  );
 }
 else
 query.Close();
}
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
  }

  private void btnRemK_actionPerformed(ActionEvent e)
  {
  JDBCQuery query=new JDBCQuery(connection),query1=new JDBCQuery(connection),query2=new JDBCQuery(connection);
 ResultSet rs,rs1;
 int f;
 try{
rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE  where  PR_id="+ PR_id +" and TOB_mnem='"+ table.getValueAt(table.getSelectedRow(),0) +"'" + " and Tf_id="+ id );
 if(rs.next())
 {
  f=rs.getInt(1);
  query.Close();
   for (int k=0;k<tableatt.getRowCount();k++)
{if(tableatt.isRowSelected(k))
{int fi=-1;
rs1=query.select("select * from IISC_ATT_TOB,IISC_ATTRIBUTE  where  IISC_ATT_TOB.PR_id="+ PR_id +" and IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id and  Att_mnem='"+ tableatt.getValueAt(k,0) +"'");
if(rs1.next())fi=rs1.getInt(1);
query.Close();
rs=query.select("select * from IISC_ATT_KTO where Att_id="+ fi +" and TF_id="+ id +" and TOB_id="+ f +" and PR_id="+ PR_id +"");
while(rs.next())
{int key=rs.getInt("TOB_RBRK");
query.update("delete from IISC_ATT_KTO where Att_id="+ fi +" and TF_id="+ id +" and TOB_id="+ f +" and PR_id="+ PR_id +" and TOB_rbrk="+ key +"");
rs1=query1.select("select * from IISC_ATT_KTO   where  TF_id="+ id +"  and PR_id="+ PR_id +" and TOB_rbrk="+ key +"");
if(!rs1.next())
{query2.update("delete from IISC_KEY_TOB   where  TF_id="+ id +"  and PR_id="+ PR_id +" and TOB_rbrk="+ key +"");
query2.update("update IISC_ATT_KTO set  TOB_rbrk=TOB_rbrk-1 where  TF_id="+ id +" and TOB_id="+ f +" and PR_id="+ PR_id +" and TOB_rbrk>"+ key +"");
query2.update("update IISC_KEY_TOB set  TOB_rbrk=TOB_rbrk-1 where  TF_id="+ id +"   and PR_id="+ PR_id +" and TOB_rbrk>"+ key +"");
}
query1.Close();
}
query.Close();
}
}
 qtmatt.setQueryA("select * from IISC_ATT_TOB where  PR_id="+ PR_id +" and  TOB_id="+f + " and Tf_id="+ id  );
 }
 else
 query.Close();
}
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
  }

  private void btnAddA_actionPerformed(ActionEvent e)
  {//btnSave.setEnabled(true);
  //btnApply.setEnabled(true);
  JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
 try{
rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id="+ id +" and TOB_mnem='"+ table.getValueAt(table.getSelectedRow(),0) +"' and PR_Id="+ PR_id +"");
  if(rs.next())
 {
 //AttType att =new  AttType((IISFrameMain) getParent(),"Add Component Type Attribute",-1,rs.getInt(1), true, connection,this );
 CreateAtt att =new  CreateAtt((IISFrameMain) getParent(),"Add Component Type Attribute", true, connection,this );
 Settings.Center(att);
 att.setVisible(true);
 }
 query.Close();
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
  
 
  }

  private void btnEditA_actionPerformed(ActionEvent e)
  {//btnSave.setEnabled(true);
  //btnApply.setEnabled(true);
  JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
 try{
rs=query.select("select * from IISC_ATT_TOB,IISC_ATTRIBUTE,IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id=IISC_ATT_TOB.TOB_id and TOB_mnem='"+ table.getValueAt(table.getSelectedRow(),0) +"' and  IISC_ATTRIBUTE.PR_Id="+ PR_id +" and IISC_ATT_TOB.Tf_id="+ id +" and Att_mnem='"+ tableatt.getValueAt(tableatt.getSelectedRow(),1) +"'");
  if(rs.next())
 {
  AttType att =new  AttType((IISFrameMain) getParent(),"Edit Component Type Attribute",rs.getInt(1),rs.getInt(3), true, connection,this );
 Settings.Center(att);
 att.setVisible(true);
 }
 query.Close();
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
  }

     private void btnDelA_actionPerformed(ActionEvent e)
     {
      //btnSave.setEnabled(true);
     //btnApply.setEnabled(true);
     JDBCQuery query=new JDBCQuery(connection);
     ResultSet rs;
     try{
     int h,a;
     rs=query.select("select * from IISC_ATT_TOB,IISC_ATTRIBUTE,IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id=IISC_ATT_TOB.TOB_id and TOB_mnem='"+ table.getValueAt(table.getSelectedRow(),0) +"' and  IISC_ATTRIBUTE.PR_Id="+ tree.ID +" and IISC_ATT_TOB.Tf_id="+ id +" and Att_mnem='"+ tableatt.getValueAt(tableatt.getSelectedRow(),1) +"'");
     if(rs.next())
     {a=rs.getInt(1);
     h=rs.getInt(3);
     query.Close();
     
     query.update("delete from IISC_IG_ITEM where  PR_id="+ tree.ID +" and TOB_id="+ h +" and Att_id="+ a +" and Tf_id="+ id +"");
     query.update("delete from IISC_ATT_KTO  where  PR_id="+ tree.ID +" and TOB_id="+ h +" and Att_id="+ a +" and Tf_id="+ id +"");
     query.update("delete from IISC_ATT_TOB  where  PR_id="+ tree.ID +" and TOB_id="+ h +" and Att_id="+ a +" and Tf_id="+ id +"");
     query.update("delete from IISC_COMPTYPE_ATTRIB_DISPLAY  where  PR_id="+ tree.ID +" and TOB_id="+ h +" and Att_id="+ a +" and Tf_id="+ id +"");
     query.update("delete from IISC_COMP_ATT_DISPLAY_VALUES  where  PR_id="+ tree.ID +" and TOB_id="+ h +" and Att_id="+ a +" and Tf_id="+ id +"");
     query.update("delete from IISC_COMP_ATT_DISPLAY_VALUES  where  PR_id="+ tree.ID +" and TOB_id="+ h +" and Tf_id="+ id +"");
     query.update("delete from IISC_COMPTYPE_ATTRIB_DISPLAY  where  PR_id="+ tree.ID +" and TOB_id="+ h +" and Tf_id="+ id +"");
     query.update("delete from IISC_FUN_PARAM_VALUE where PR_id="+ tree.ID +" and Tob_id="+ h +" and Att_id="+ a +" and Tf_id="+ id +"");
     query.update("update IISC_FUN_PARAM_VALUE set Value_type=0 where PR_id="+ tree.ID +" and Value_Tob_id="+ h +" and Value_Att_id="+ a +" and Value_Tf_id="+ id +"");
     query.update("delete from IISC_ATT_TOB_EVENT where PR_id="+ tree.ID +" and Tob_id="+ h +" and Att_id="+ a +" and Tf_id="+ id +"");
        
     String[] str= tableatt.getValueAt(tableatt.getSelectedRow(),2).toString().split("local");
     if(!str[0].equals(""))
     { rs=query.select("select * from IISC_ATT_KTO where  PR_id="+ tree.ID +" and TOB_id="+ h +" and TOB_rbrk="+ str[0] +" and Tf_id="+ id +"");
      if(!rs.next()) 
      {query.Close();
       query.update("delete from IISC_KEY_TOB where  PR_id="+ tree.ID +" and TOB_id="+ h +" and TOB_rbrk="+ str[0] +" and Tf_id="+ id +""); 
       query.update("update IISC_ATT_KTO set  TOB_rbrk=TOB_rbrk-1 where  TF_id="+  id +" and TOB_id="+ h +" and PR_id="+  tree.ID +" and TOB_rbrk>"+str[0] +"");
       query.update("update IISC_KEY_TOB set  TOB_rbrk=TOB_rbrk-1 where  TF_id="+  id +"   and PR_id="+  tree.ID +" and TOB_rbrk>"+ str[0] +"");
     
      }
      else
      query.Close();
     }
       query.update("delete from IISC_ATT_UTO  where  PR_id="+ tree.ID +" and TOB_id="+ h +" and Att_id="+ a +" and Tf_id="+ id +"");
     query.update("delete from IISC_ATT_TOB  where  PR_id="+ tree.ID +" and TOB_id="+ h +" and Att_id="+ a +" and Tf_id="+ id +"");
     if(!tableatt.getValueAt(tableatt.getSelectedRow(),3).equals(""))
     {
     rs=query.select("select * from IISC_ATT_UTO where  PR_id="+ tree.ID +" and TOB_id="+ h +" and TOB_rbrk="+ tableatt.getValueAt(tableatt.getSelectedRow(),3) +" and Tf_id="+ id +"");
      if(!rs.next()) 
      {query.Close();
       query.update("delete from IISC_UNIQUE_TOB where  PR_id="+ tree.ID +" and TOB_id="+ h +" and TOB_rbrk="+ tableatt.getValueAt(tableatt.getSelectedRow(),3) +" and Tf_id="+ id +""); 
      query.update("update IISC_ATT_UTO set  TOB_rbrk=TOB_rbrk-1 where  TF_id="+  id +" and TOB_id="+ h +" and PR_id="+  tree.ID +" and TOB_rbrk>"+ tableatt.getValueAt(tableatt.getSelectedRow(),3) +"");
     query.update("update IISC_UNIQUE_TOB set  TOB_rbrk=TOB_rbrk-1 where  TF_id="+  id +"   and PR_id="+  tree.ID +" and TOB_rbrk>"+ tableatt.getValueAt(tableatt.getSelectedRow(),3) +"");
     
      }
      else
      query.Close();
     }
     qtmatt.setQueryA("select * from IISC_ATT_TOB where  PR_id="+ tree.ID +" and  TOB_id=" +h + " and Tf_id="+ id +"" );
     qtmatt.setQueryA("select * from IISC_ATT_TOB where TF_id ="+ id +" and TOB_id=" + h ); 
     JOptionPane.showMessageDialog(null, "<html><center>Component Type Attribute deleted!", "Form Types", JOptionPane.INFORMATION_MESSAGE);
     }
     else
     query.Close();
     }
     catch(SQLException ex)
     {
     JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
     }
}
     
  private void btnHelp_actionPerformed(ActionEvent e)
  {Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void apply_ActionPerformed(ActionEvent e)
  {
      par.Update();
      cf.Update();
      update(1);
  }


  /*private void jComboBox1_actionPerformed(ActionEvent e)
  {try
  {
   if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
  String s=cmbForm.getSelectedItem().toString();
  setForm(s); 
  Mnem=s;
 
  //tree.select_node(Mnem,"Owned","Form Types",appsys);
 }
   catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
*/



  private void rdMenu_stateChanged(ChangeEvent e)
  {if(rdMenu.isSelected())
  {
    cmbUse.setEnabled(false);
    jPanel2.setEnabled(false);
  }
  else
   {
    cmbUse.setEnabled(true);
    jPanel2.setEnabled(true);
  }
  }

  private void rdProgram_stateChanged(ChangeEvent e)
  {if(rdMenu.isSelected())
  {
    cmbUse.setEnabled(false);
    jPanel2.setEnabled(false);
  }
  else
   {
    cmbUse.setEnabled(true);
    jPanel2.setEnabled(true);
  }
  }

  private void down_ActionPerformed(ActionEvent e)
  {//btnSave.setEnabled(true);
  //btnApply.setEnabled(true);
  JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
 try{
rs=query.select("select IISC_ATT_TOB.TOB_id,IISC_ATT_TOB.Att_id,W_order,ATT_mnem  from IISC_ATT_TOB,IISC_ATTRIBUTE,IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id=IISC_ATT_TOB.TOB_id and TOB_mnem='"+ table.getValueAt(table.getSelectedRow(),0) +"' and  IISC_ATTRIBUTE.PR_Id="+ PR_id +" and IISC_ATT_TOB.Tf_id="+ id +" and Att_mnem='"+ tableatt.getValueAt(tableatt.getSelectedRow(),1) +"'");
  if(rs.next())
 {int tb=rs.getInt(1);
 int at=rs.getInt(2);
  int k1= rs.getInt(3);
  String att=rs.getString(4);
query.Close();
rs=query.select("select  min(W_order) from IISC_ATT_TOB  where W_order>"+ k1 +" and IISC_ATT_TOB.TOB_id="+ tb +" and  IISC_ATT_TOB.PR_Id="+ PR_id + " and IISC_ATT_TOB.Tf_id="+ id);
 if(rs.next())
 {int k= rs.getInt(1); 
 query.Close();
 if(k> k1)
{
  query.update("update IISC_ATT_TOB set W_order="+ k1 +" where W_order="+ k +" and IISC_ATT_TOB.TOB_id="+ tb +" and  IISC_ATT_TOB.PR_Id="+ PR_id + " and IISC_ATT_TOB.Tf_id="+ id);
query.update("update IISC_ATT_TOB set W_order="+ (k1+1)  +" where  IISC_ATT_TOB.TOB_id="+ tb +" and  IISC_ATT_TOB.Att_Id="+ at + " and  IISC_ATT_TOB.PR_Id="+ PR_id + " and IISC_ATT_TOB.Tf_id="+ id);
 qtmatt.setQueryA("select * from IISC_ATT_TOB where PR_id="+ PR_id +" and TOB_id="+tb + " and Tf_id="+ id  );
}
} 
else
   query.Close();
 selectCell(att);
 }
else
   query.Close();
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
  }

private void selectCell(String att)
{
  for (int i=0;i<tableatt.getRowCount();i++)
  if(tableatt.getValueAt(i,1).equals(att))tableatt.getSelectionModel().setSelectionInterval(i,i);
}
  private void up_ActionPerformed(ActionEvent e)
  {//btnSave.setEnabled(true);
  //btnApply.setEnabled(true);
  JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
 try{
rs=query.select("select IISC_ATT_TOB.TOB_id,IISC_ATT_TOB.Att_id,W_order,ATT_mnem from IISC_ATT_TOB,IISC_ATTRIBUTE,IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_ATTRIBUTE.Att_id=IISC_ATT_TOB.Att_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id=IISC_ATT_TOB.TOB_id and TOB_mnem='"+ table.getValueAt(table.getSelectedRow(),0) +"' and  IISC_ATTRIBUTE.PR_Id="+ PR_id +" and IISC_ATT_TOB.Tf_id="+ id +" and Att_mnem='"+ tableatt.getValueAt(tableatt.getSelectedRow(),1) +"'");
  if(rs.next())
 {int tb=rs.getInt(1);
 int at=rs.getInt(2);
  int k1= rs.getInt(3);
  String att=rs.getString(4);
query.Close();
rs=query.select("select max(W_order) from IISC_ATT_TOB  where W_order<"+ k1 +" and IISC_ATT_TOB.TOB_id="+ tb +" and  IISC_ATT_TOB.PR_Id="+ PR_id + " and IISC_ATT_TOB.Tf_id="+ id);
 if(rs.next())
 {int k= rs.getInt(1);
 query.Close();
query.update("update IISC_ATT_TOB set W_order="+(k+1)+" where W_order="+ k +" and IISC_ATT_TOB.TOB_id="+ tb +" and  IISC_ATT_TOB.PR_Id="+ PR_id + " and IISC_ATT_TOB.Tf_id="+ id);
query.update("update IISC_ATT_TOB set W_order="+ k  +" where  IISC_ATT_TOB.TOB_id="+ tb +" and  IISC_ATT_TOB.Att_Id="+ at + " and  IISC_ATT_TOB.PR_Id="+ PR_id + " and IISC_ATT_TOB.Tf_id="+ id);
 qtmatt.setQueryA("select * from IISC_ATT_TOB where PR_id="+ PR_id +" and TOB_id="+tb + " and Tf_id="+ id  );
 }
 else
   query.Close();
 selectCell(att);
 }
 else
   query.Close();
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
  }

  private void btnKeys_actionPerformed(ActionEvent e)
  {//btnSave.setEnabled(true);
  //btnApply.setEnabled(true);
  JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
 try{
rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE  where  PR_id="+ PR_id +" and TF_id="+ id +" and TOB_mnem='"+ table.getValueAt(table.getSelectedRow(),0) +"'");
 if(rs.next())
 {
 Keys key =new  Keys((IISFrameMain) getParent(),"Component Type Keys",rs.getInt(1), true, connection,this );
 Settings.Center(key);
 key.setVisible(true);
 }
   query.Close();
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
  }

  private void txtComment_keyTyped(KeyEvent e)
  {btnSave.setEnabled(true);
  btnApply.setEnabled(true);
  }

  private void txtName_keyTyped(KeyEvent e)
  {btnSave.setEnabled(true);
  btnApply.setEnabled(true);
  }

  private void txtTitle_keyTyped(KeyEvent e)
  {btnSave.setEnabled(true);
  btnApply.setEnabled(true);
  }

  private void rdMenu_actionPerformed(ActionEvent e)
  {btnSave.setEnabled(true);
  btnApply.setEnabled(true);
  if(rdMenu.isSelected())
  jTabbedPane1.setEnabledAt(1,false);
  else
  jTabbedPane1.setEnabledAt(1,true);
  }

  private void rdProgram_actionPerformed(ActionEvent e)
  {btnSave.setEnabled(true);
  btnApply.setEnabled(true);
   if(rdMenu.isSelected())
  jTabbedPane1.setEnabledAt(1,false);
  else
  jTabbedPane1.setEnabledAt(1,true);
  }

  private void txtFreq_keyTyped(KeyEvent e)
  {btnSave.setEnabled(true);
  btnApply.setEnabled(true);
  }

  private void txtResp_keyTyped(KeyEvent e)
  {btnSave.setEnabled(true);
  btnApply.setEnabled(true);
  }

  private void cmbUnitF_itemStateChanged(ItemEvent e)
  {
  }

  private void cmbUnitR_itemStateChanged(ItemEvent e)
  { 
  }

  private void cmbUse_itemStateChanged(ItemEvent e)
  {
  }

  private void cmbUse_actionPerformed(ActionEvent e)
  {
  }

  private void cmbUse_mouseClicked(MouseEvent e)
  {btnSave.setEnabled(true);
  btnApply.setEnabled(true);
  }

  private void cmbUnitF_actionPerformed(ActionEvent e)
  {
  }

  private void cmbUnitF_mouseClicked(MouseEvent e)
  {btnSave.setEnabled(true);
  btnApply.setEnabled(true);
  }

  private void cmbUnitR_mouseClicked(MouseEvent e)
  {btnSave.setEnabled(true);
  btnApply.setEnabled(true);
  }

  private void btnUniques_actionPerformed(ActionEvent e)
  {//btnSave.setEnabled(true);
  //btnApply.setEnabled(true);
    JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
 try{
rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE  where  PR_id="+ PR_id +" and TF_id="+ id +" and TOB_mnem='"+ table.getValueAt(table.getSelectedRow(),0) +"'");
 if(rs.next())
 {
 Uniques key =new  Uniques((IISFrameMain) getParent(),"Component Type Uniques",rs.getInt(1), true, connection,this );
 Settings.Center(key);
 key.setVisible(true);
 }
   query.Close();
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
  }

  private void cmbAppType_itemStateChanged(ItemEvent e)
  {
  }

  private void jTabbedPane1_mouseClicked(MouseEvent e)
  {if(id==-1 && jTabbedPane1.getSelectedIndex()==1)
  {jTabbedPane1.setSelectedIndex(0);
     JOptionPane.showMessageDialog(null, "<html><center>You must apply changes you have made!", "Save Form Type", JOptionPane.OK_OPTION);
   
  }
  }

  private void btnCheck_actionPerformed(ActionEvent e)
  { JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
 try{
 int i=-1;
rs=query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE  where  PR_id="+ PR_id +" and TF_id="+ id +" and TOB_mnem='"+ table.getValueAt(table.getSelectedRow(),0) +"'");
 if(rs.next())
 {
 Check key =new  Check((IISFrameMain) getParent(),"Component Type Check Constarint",rs.getInt(1), true, connection,this );
 Settings.Center(key);
 key.setVisible(true);
 i=table.getSelectedRow();
 }
   query.Close();
   qtm.setQueryO("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tf_id="+id+ " order by TOB_superord");
   table.getSelectionModel().setSelectionInterval(i,i);
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
  }
 
 }
