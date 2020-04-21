package iisc;
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
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class RScheme extends JDialog implements ActionListener
{
  private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
  private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
  private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
  private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  private ImageIcon iconUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
  private ImageIcon iconOK = new ImageIcon(IISFrameMain.class.getResource("icons/ok.gif"));
  private ImageIcon iconPK = new ImageIcon(IISFrameMain.class.getResource("icons/pk.gif"));
  private ImageIcon iconEmpty = new ImageIcon(IISFrameMain.class.getResource("icons/empty.gif"));
  private ImageIcon iconDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
  private JButton btnClose = new JButton();
  private PTree tree;
  private Connection connection;
  public String Mnem;
  private int id;
  private ListSelectionModel rowSM,rowSM1,rowSM2;
  private QueryTableModel qtm,qtm1,qtm2,qtm3,qtm4;
  public String appsys;
  private int as;
  private JButton btnSave = new JButton();
  private JButton btnHelp = new JButton();
  private JToolBar jToolBar1 = new JToolBar();
  private JButton btnFirst = new JButton();
  private JButton btnPrev = new JButton();
  private JComboBox cmbFun = new JComboBox();
  private JButton btnNext = new JButton();
  private JButton btnLast = new JButton();
  private JButton btnApply = new JButton();
  private JLabel jLabel10 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JTextField txtName = new JTextField();
  private JTextArea txtDescription = new JTextArea();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private JPanel jPanel3 = new JPanel();
  private JLabel jLabel5 = new JLabel();
  private JButton btnUp = new JButton();
  private JButton btnDown = new JButton();
  private JButton btnDown1 = new JButton();
  private JButton btnUp1 = new JButton();
  private JButton btnDown2 = new JButton();
  private JButton btnUp2 = new JButton();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JList lstAttKey = new JList();
  private JScrollPane jScrollPane3 = new JScrollPane();
  private JTable table;
  public GraphDraw graph;
  private JScrollPane jScrollPane4 = new JScrollPane();
  private JTable table1;
  private JPanel jPanel4 = new JPanel();
  private JScrollPane jScrollPane5 = new JScrollPane();
  private JTable table2;
  private JButton jButton1 = new JButton();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JTextArea txtConDescription = new JTextArea();
  private JPanel jPanel5 = new JPanel();
  private JLabel jLabel11 = new JLabel();
  private JCheckBox chRead = new JCheckBox();
  private JCheckBox chDelete = new JCheckBox();
  private JCheckBox chModify = new JCheckBox();
  private JCheckBox chInsert = new JCheckBox();
  private boolean dispose=false;
  private JPanel jPanel6 = new JPanel();
  private JLabel jLabel6 = new JLabel();
  private JScrollPane jScrollPane6 = new JScrollPane();
  private JTable table4;
  private JPanel jPanel7 = new JPanel();
  private JScrollPane jScrollPane7 = new JScrollPane();
  private JButton jButton2 = new JButton();
  private JTable table3;
  private JScrollPane jScrollPane8 = new JScrollPane();
  private JList lstAttKey1 = new JList();
  private JList lstAttUnique = new JList();
  
  /*nobrenovic: Start*/
  private QueryTableModel qtmChkCon;
  private JTable tblChkCon;
    private JPanel jPanel8 = new JPanel();
    private JScrollPane jScrollPane9 = new JScrollPane();
    private JButton btnChkConView = new JButton();
    /*nobrenovic: End*/
	
  public RScheme()
  {
    this(null, "", false);
  }

  public RScheme(IISFrameMain parent, String title, boolean modal)
  {
    super((Frame) parent, title, modal);
    try
    {
     
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
  public RScheme(IISFrameMain parent, String title, boolean modal,Connection con,int m , PTree tr, String s )
  { 
    super((Frame) parent, title, modal);
    try
    { tree=tr;
      connection=con;
      JDBCQuery query=new JDBCQuery(connection);
      ResultSet rs1;
    iconOK.setDescription("img");
    appsys=s; 
    rs1=query.select("select * from IISC_APP_SYSTEM where AS_name='"+ appsys+"' and PR_id="+tree.ID);
      rs1.next();
      as=rs1.getInt("AS_id");
       query.Close();
     if(m>=0){
      id=m;
      
      rs1=query.select("select * from IISC_RELATION_SCHEME where PR_id="+ tree.ID +" and RS_id="+id+" and AS_id="+as);
      if(rs1.next())
      Mnem=rs1.getString("RS_name");
       query.Close();
    }
    else
      Mnem="";
           Iterator it=tree.WindowsManager.iterator();
           while(it.hasNext())
      {
        Object obj=(Object)it.next();
        Class cls=obj.getClass();
        if(cls==this.getClass())
        {if(((RScheme)obj).as==as)
        { ((RScheme)obj).dispose();
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
  
    public RScheme(IISFrameMain parent, String title, boolean modal,Connection con,int relSchID , PTree tr, int appSysID)
    { 
      super((Frame) parent, title, modal);
      try
      { tree=tr;
        as = appSysID;
        connection=con;
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
      iconOK.setDescription("img");
      
      rs1=query.select("select AS_name from IISC_APP_SYSTEM where AS_id="+ appSysID +" and PR_id="+tree.ID);
      rs1.next();
      appsys = rs1.getString("AS_name");
      query.Close();
      
        if(relSchID>=0){
            id=relSchID;
            
            rs1=query.select("select * from IISC_RELATION_SCHEME where PR_id="+ tree.ID +" and RS_id="+id+" and AS_id="+as);
            if(rs1.next())
            Mnem=rs1.getString("RS_name");
             query.Close();
        }
        else
            Mnem="";

        jbInit();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }

    }
    
  private void jbInit() throws Exception
  {
    this.setResizable(false);
     JDBCQuery query=new JDBCQuery(connection);
     ResultSet rs;
    this.setSize(new Dimension(508, 391));
    this.getContentPane().setLayout(null);

    this.setTitle("Relation Schemes");






    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Close");
    btnClose.setBounds(new Rectangle(370, 320, 80, 30));
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.setFont(new Font("SansSerif", 0, 11));

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
    btnSave.setBounds(new Rectangle(290, 320, 75, 30));
    btnSave.setMinimumSize(new Dimension(50, 30));
    btnSave.setFont(new Font("SansSerif", 0, 11));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save_ActionPerformed(ae);
        
        }
      });

    btnHelp.setBounds(new Rectangle(455, 320, 35, 30));
    btnHelp.setIcon(imageHelp);
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    jToolBar1.setFont(new Font("SansSerif", 0, 11));
    jToolBar1.setLayout(null);
    jToolBar1.setPreferredSize(new Dimension(249, 60));
    jToolBar1.setFloatable(false);
    jToolBar1.setBounds(new Rectangle(45, 5, 400, 30));
    btnFirst.setMaximumSize(new Dimension(60, 60));
    btnFirst.setPreferredSize(new Dimension(25, 20));
    btnFirst.setIcon(iconPrevv);
    btnFirst.setBounds(new Rectangle(40, 5, 25, 20));
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
    btnPrev.setBounds(new Rectangle(70, 5, 25, 20));
    btnPrev.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          prev_ActionPerformed(ae);
        }
      });
    cmbFun.setFont(new Font("SansSerif", 0, 11));
    cmbFun.setBounds(new Rectangle(100, 5, 230, 20));
    cmbFun.addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          cmbFun_itemStateChanged(e);
        }
      });
    cmbFun.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jComboBox1_actionPerformed(e);
        }
      });
    btnNext.setMaximumSize(new Dimension(60, 60));
    btnNext.setIcon(iconNext);
    btnNext.setPreferredSize(new Dimension(25, 20));
    btnNext.setBounds(new Rectangle(335, 5, 25, 20));
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
    btnLast.setBounds(new Rectangle(365, 5, 25, 20));
    btnLast.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          nextt_ActionPerformed(ae);
        }
      });
    btnApply.setMaximumSize(new Dimension(50, 30));
    btnApply.setPreferredSize(new Dimension(50, 30));
    btnApply.setText("Apply");
    btnApply.setMinimumSize(new Dimension(50, 30));
    btnApply.setFont(new Font("SansSerif", 0, 11));
    btnApply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          apply_ActionPerformed(ae);
        }
      });
    btnApply.setBounds(new Rectangle(210, 320, 75, 30));
    jLabel10.setText("Name:");
    jLabel10.setBounds(new Rectangle(5, 20, 65, 20));
    jLabel10.setFont(new Font("SansSerif", 0, 11));
    jLabel3.setText("Description:");
    jLabel3.setBounds(new Rectangle(5, 50, 85, 20));
    jLabel3.setFont(new Font("SansSerif", 0, 11));
    txtName.setBounds(new Rectangle(70, 20, 385, 20));
    txtName.setFont(new Font("SansSerif", 0, 11));
    txtName.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          txtName_actionPerformed(e);
        }
      });
    txtName.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtName_keyTyped(e);
        }
      });
    txtDescription.setBounds(new Rectangle(70, 55, 385, 155));
    txtDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    txtDescription.setFont(new Font("SansSerif", 0, 11));
    txtDescription.addKeyListener(new KeyAdapter()
      {
        public void keyTyped(KeyEvent e)
        {
          txtDescription_keyTyped(e);
        }
      });
    jTabbedPane1.setBounds(new Rectangle(15, 40, 475, 265));
    jPanel1.setLayout(null);
    jPanel2.setLayout(null);
    jPanel3.setLayout(null);
    jLabel5.setText("Attributes in the key");
    jLabel5.setBounds(new Rectangle(260, 5, 165, 20));
    jLabel5.setFont(new Font("SansSerif", 0, 11));
    btnUp.setMaximumSize(new Dimension(60, 60));
    btnUp.setPreferredSize(new Dimension(25, 20));
    btnUp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          up1_ActionPerformed(ae);
        }
      });
    btnUp.setBounds(new Rectangle(435, 35, 25, 20));
    btnUp.setFont(new Font("SansSerif", 0, 11));
    btnUp.setIcon(iconUp);
    btnDown.setMaximumSize(new Dimension(60, 60));
    btnDown.setPreferredSize(new Dimension(25, 20));
    btnDown.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          down1_ActionPerformed(ae);
        }
      });
    btnDown.setBounds(new Rectangle(435, 65, 25, 20));
    btnDown.setFont(new Font("SansSerif", 0, 11));
    btnDown.setIcon(iconDown);
    btnDown1.setMaximumSize(new Dimension(60, 60));
    btnDown1.setPreferredSize(new Dimension(25, 20));
    btnDown1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          down3_ActionPerformed(ae);
        }
      });
    btnDown1.setBounds(new Rectangle(200, 60, 25, 20));
    btnDown1.setFont(new Font("SansSerif", 0, 11));
    btnDown1.setIcon(iconDown);
    btnUp1.setMaximumSize(new Dimension(60, 60));
    btnUp1.setPreferredSize(new Dimension(25, 20));
    btnUp1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          up3_ActionPerformed(ae);
        }
      });
    btnUp1.setBounds(new Rectangle(200, 30, 25, 20));
    btnUp1.setFont(new Font("SansSerif", 0, 11));
    btnUp1.setIcon(iconUp);
    btnDown2.setMaximumSize(new Dimension(60, 60));
    btnDown2.setPreferredSize(new Dimension(25, 20));
    btnDown2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          down_ActionPerformed(ae);
        }
      });
    btnDown2.setBounds(new Rectangle(225, 60, 25, 20));
    btnDown2.setFont(new Font("SansSerif", 0, 11));
    btnDown2.setIcon(iconDown);
    btnUp2.setMaximumSize(new Dimension(60, 60));
    btnUp2.setPreferredSize(new Dimension(25, 20));
    btnUp2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          up_ActionPerformed(ae);
        }
      });
    btnUp2.setBounds(new Rectangle(225, 30, 25, 20));
    btnUp2.setFont(new Font("SansSerif", 0, 11));
    btnUp2.setIcon(iconUp);
    jScrollPane1.setBounds(new Rectangle(255, 30, 170, 185));
    jScrollPane1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    lstAttKey.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lstAttKey.setFont(new Font("SansSerif", 0, 11));
    jScrollPane3.setBounds(new Rectangle(10, 10, 185, 205));
    jScrollPane3.setFont(new Font("SansSerif", 0, 11));
    qtm=new QueryTableModel(connection,-1);
    qtm.image=1;
    table=new JTable(qtm);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  //table.setCellSelectionEnabled(false);
  table.setRowSelectionAllowed(true);
  table.setGridColor(new Color(0,0,0));
    table.setBackground(Color.white);
table.getTableHeader().setReorderingAllowed(false);
table.setAutoscrolls(true);
rowSM = table.getSelectionModel();
  table.setBorder(BorderFactory.createLineBorder(Color.black, 1));
   table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
table.addMouseListener(new MouseAdapter() {

public void mouseClicked (MouseEvent me) {

        doMouseClicked(me);

}

});
qtm.setQueryRSKEY("select * from IISC_RS_KEY where PR_id="+ tree.ID +" and RS_id="+id+" order by RS_rbrk");
    jScrollPane4.setBounds(new Rectangle(10, 10, 205, 205));
    jScrollPane4.setFont(new Font("SansSerif", 0, 11));
    qtm1=new QueryTableModel(connection,-1);
    qtm1.image=1;
    table1=new JTable(qtm1);
    table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table1.getTableHeader().setReorderingAllowed(false);
  table1.setRowSelectionAllowed(true);
  table1.setGridColor(new Color(0,0,0));
    table1.setBackground(Color.white);
table1.setAutoResizeMode(0);

table1.setAutoscrolls(true);
rowSM1 = table1.getSelectionModel();
  table1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
 table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
table1.addMouseListener(new MouseAdapter() {

public void mouseClicked (MouseEvent me) {

    doMouseClicked1(me);

}

});
    jPanel4.setLayout(null);
    jScrollPane5.setBounds(new Rectangle(10, 15, 350, 130));
    jScrollPane5.setFont(new Font("SansSerif", 0, 11));
     qtm2=new QueryTableModel(connection,-1);
     table2=new JTable(qtm2);
    btnApply.setEnabled(false);
    btnSave.setEnabled(false);
    table2.setRowSelectionAllowed(true);
    table2.setGridColor(new Color(0, 0, 0));
    table2.setBackground(Color.white);
    table2.setAutoResizeMode(0);
    table2.setAutoscrolls(true);
    table2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table2.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent me)
        {
          doMouseClicked2(me);
        }
      });
    table2.setBounds(new Rectangle(0, 0, 0, 0));
    table2.addKeyListener(new KeyAdapter()
      {
        public void keyReleased(KeyEvent e)
        {
          table2_keyReleased(e);
        }
      });
    table2.addPropertyChangeListener(new PropertyChangeListener()
      {
        public void propertyChange(PropertyChangeEvent e)
        {
          table2_propertyChange(e);
        }
      });
    

    jButton1.setText("View");
    jButton1.setBounds(new Rectangle(385, 15, 65, 35));
    jButton1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton1_actionPerformed(e);
        }
      });
    jScrollPane2.setBounds(new Rectangle(10, 150, 350, 65));
    txtConDescription.setFont(new Font("SansSerif", 0, 11));
    txtConDescription.setEditable(false);
    jPanel5.setLayout(null);
    jLabel11.setText("Subschema roles (operations in application system):");
    jLabel11.setBounds(new Rectangle(5, 10, 310, 20));
    jLabel11.setFont(new Font("SansSerif", 0, 11));
    chRead.setText("Read data");
    chRead.setBounds(new Rectangle(25, 50, 110, 15));
    chRead.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chRead_mouseClicked(e);
        }
      });
    chDelete.setText("Delete data");
    chDelete.setBounds(new Rectangle(25, 140, 110, 15));
    chDelete.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chDelete_mouseClicked(e);
        }
      });
    chModify.setText("Modify data");
    chModify.setBounds(new Rectangle(25, 110, 110, 15));
    chModify.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chModify_mouseClicked(e);
        }
      });
    chInsert.setText("Insert data");
    chInsert.setBounds(new Rectangle(25, 80, 110, 15));
    chInsert.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          chInsert_mouseClicked(e);
        }
      });
    jPanel6.setLayout(null);
    jLabel6.setText("Attributes in the unique");
    jLabel6.setBounds(new Rectangle(260, 5, 165, 20));
    jLabel6.setFont(new Font("SansSerif", 0, 11));
    jScrollPane6.setBounds(new Rectangle(10, 10, 185, 205));
    jScrollPane6.setFont(new Font("SansSerif", 0, 11));
     qtm3=new QueryTableModel(connection,-1);
     qtm3.image=1;
      qtm4=new QueryTableModel(connection,-1);
      table3=new JTable(qtm4);
    table4=new JTable(qtm3);
    chModify.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          chModify_actionPerformed(e);
        }
      });
    table4.setRowSelectionAllowed(true);
    table4.setGridColor(new Color(0, 0, 0));
    table4.setBackground(Color.white);
    table4.setAutoscrolls(true);
    table4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    table4.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table4.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent me)
        {
          doMouseClicked12(me);
        }
      });
    jPanel7.setLayout(null);
    jScrollPane7.setBounds(new Rectangle(10, 15, 350, 200));
    jScrollPane7.setFont(new Font("SansSerif", 0, 11));
    jButton2.setText("View");
    jButton2.setBounds(new Rectangle(385, 15, 65, 35));
    jButton2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          form_actionPerformed(e);
        }
      });
    table3.setRowSelectionAllowed(true);
    table3.setGridColor(new Color(0, 0, 0));
    table3.setBackground(Color.white);
    table3.setAutoResizeMode(0);
    table3.setAutoscrolls(true);
    table3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    table3.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table3.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent me)
        {
          doMouseClicked3(me);
        }
      });
    table3.addKeyListener(new KeyAdapter()
      {
        public void keyReleased(KeyEvent e)
        {
          table2_keyReleased(e);
        }
      });
    table3.addPropertyChangeListener(new PropertyChangeListener()
      {
        public void propertyChange(PropertyChangeEvent e)
        {
          table2_propertyChange(e);
        }
      });
    jScrollPane8.setBounds(new Rectangle(255, 30, 170, 185));
    jScrollPane8.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    lstAttKey1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lstAttKey1.setFont(new Font("SansSerif", 0, 11));
    lstAttKey1.setBounds(new Rectangle(0, 0, 0, 0));
    lstAttUnique.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    lstAttUnique.setFont(new Font("SansSerif", 0, 11));

    /*nobrenovic: Start*/
    qtmChkCon = new QueryTableModel(connection,-1);
    tblChkCon = new JTable(qtmChkCon);
    /*nobrenovic: End */
    tblChkCon.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblChkCon_mouseClicked(e);
                }
            });
    jPanel8.setLayout(null);
    jScrollPane9.setBounds(new Rectangle(10, 5, 450, 175));
    btnChkConView.setBounds(new Rectangle(385, 185, 75, 30));
    btnChkConView.setText("View");
    btnChkConView.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnChkConView_actionPerformed(e);
                }
            });
    qtm1.setQueryRSATT("select * from IISC_ATTRIBUTE,IISC_RS_ATT where IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id and IISC_RS_ATT.PR_id="+ tree.ID +" and IISC_RS_ATT.RS_id="+id+" order by Att_sequence");
 
    table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jScrollPane5.getViewport();
    table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table2.getTableHeader();
    table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jScrollPane6.getViewport();
    table4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table4.getTableHeader();
    qtm3.setQueryUnique("select  distinct(RS_rbru) from IISC_RS_UNIQUE where PR_id="+ tree.ID +" and RS_id="+id+" order by RS_rbru");
    jScrollPane7.getViewport();
    jScrollPane7.getViewport();
    table3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table3.getTableHeader();
    table3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jScrollPane8.getViewport();
        jToolBar1.add(btnFirst, null);
    jToolBar1.add(btnPrev, null);
    jToolBar1.add(cmbFun, null);
    jToolBar1.add(btnNext, null);
    jToolBar1.add(btnLast, null);
    jPanel1.add(txtDescription, null);
    jPanel1.add(txtName, null);
    jPanel1.add(jLabel3, null);
    jPanel1.add(jLabel10, null);
    
    jScrollPane4.getViewport().add(table1, null);
    jPanel2.add(jScrollPane4, null);
    jPanel2.add(btnUp2, null);
    jPanel2.add(btnDown2, null);
    
    jPanel6.add(lstAttKey1, null);
    jScrollPane8.getViewport().add(lstAttUnique, null);
    jPanel6.add(jScrollPane8, null);
    jScrollPane6.getViewport().add(table4, null);
    jPanel6.add(jScrollPane6, null);
    jPanel6.add(jLabel6, null);
    
    jScrollPane2.getViewport().add(txtConDescription, null);
        jPanel4.add(jScrollPane2, null);
    jPanel4.add(jButton1, null);
    jScrollPane5.getViewport().add(table2, null);
    jPanel4.add(jScrollPane5, null);
    
    jPanel5.add(chInsert, null);
    jPanel5.add(chModify, null);
    jPanel5.add(chDelete, null);
    jPanel5.add(chRead, null);
    jPanel5.add(jLabel11, null);
    
    jScrollPane7.getViewport().add(table3, null);
    jPanel7.add(jButton2, null);
    jPanel7.add(jScrollPane7, null);
    

    jScrollPane9.getViewport().add(tblChkCon, null);
    jPanel8.add(jScrollPane9, null);
    jPanel8.add(btnChkConView, null);
    jScrollPane1.getViewport().add(lstAttKey, null);
    jScrollPane3.getViewport().add(table, null);
    jPanel3.add(jScrollPane3, null);
    jPanel3.add(jScrollPane1, null);
    jPanel3.add(btnUp1, null);
    jPanel3.add(btnDown1, null);
    jPanel3.add(btnDown, null);
    jPanel3.add(btnUp, null);
    jPanel3.add(jLabel5, null);

    jTabbedPane1.addTab("Relation Scheme", jPanel1);
    jTabbedPane1.addTab("Attributes", jPanel2);
    jTabbedPane1.addTab("Keys", jPanel3);
    jTabbedPane1.addTab("Uniques", jPanel6);
    jTabbedPane1.addTab("Check Constraints", jPanel8);
    jTabbedPane1.addTab("Other constraints", jPanel4);
    jTabbedPane1.addTab("Roles", jPanel5);
    jTabbedPane1.addTab("Related form types", jPanel7);
     
    this.getContentPane().add(jTabbedPane1, null);
    this.getContentPane().add(btnApply, null);
    this.getContentPane().add(jToolBar1, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnClose, null);
    this.getContentPane().add(btnSave, null);

    setRScheme(Mnem);
 }
 public void actionPerformed(ActionEvent ae)
        {
       
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
 rs1=query.select("select * from IISC_RELATION_SCHEME where PR_id="+ tree.ID +" and  AS_id="+ as +" order by  RS_name asc");
 if(rs1.next())
{s=rs1.getString(4);
}
 query.Close();
setRScheme(s);
Mnem=s;
  tree.select_node(Mnem,"Relation Schemes",appsys);
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
rs1=query.select("select RS_name from IISC_RELATION_SCHEME  where PR_id="+ tree.ID+" and AS_id="+as +" and RS_name<'" + s + "' order by RS_name desc" );
if(rs1.next())
{

s=rs1.getString(1);
}
 query.Close();
setRScheme(s);
  tree.select_node(Mnem,"Relation Schemes",appsys);

Mnem=s;
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
 rs1=query.select("select RS_name from IISC_RELATION_SCHEME  where PR_id="+ tree.ID+" and AS_id="+as +" and RS_name>'" + s + "' order by RS_name asc" );
if(rs1.next())
{
s=rs1.getString(1);
}
 query.Close();
setRScheme(s);
Mnem=s;
  tree.select_node(Mnem,"Relation Schemes",appsys);
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
{

 if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
      
 rs1=query.select("select * from IISC_RELATION_SCHEME  where PR_id="+ tree.ID+" and AS_id="+as +" order by  RS_name desc");
if(rs1.next())
{s=rs1.getString(4);
}
 query.Close();

setRScheme(s);
Mnem=s;
tree.select_node(Mnem,"Relation Schemes",appsys);
}
catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}


  }

 public void editOnlyOne()
  {
 btnPrev.setEnabled(false);
 btnLast.setEnabled(false);
 btnFirst.setEnabled(false);
 btnNext.setEnabled(false);
 cmbFun.setEnabled(false);

  }
 private void close_ActionPerformed(ActionEvent e)
  {
   if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
  this.dispose();
  
  }

 private void save_ActionPerformed(ActionEvent e)
  {
  dispose=true;
  update(1);
  tree.select_node(Mnem,"Relation Schemes",appsys);
   
  }
  private void new_ActionPerformed(ActionEvent e)
  {
  Mnem="";
  id=-1;
  setRScheme("");
  }

public void update (int k)
{
JDBCQuery query=new JDBCQuery(connection),query1=new JDBCQuery(connection);
     ResultSet rs1,rs2;  
     boolean can=true;
try
{
if(Mnem.trim().equals(""))
{
 rs1=query.select("select * from IISC_RELATION_SCHEME  where PR_id="+ tree.ID+" and AS_id="+as +" and RS_name='"+ txtName.getText().trim() +"'");
if(rs1.next())
 {JOptionPane.showMessageDialog(null, "<html><center>Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
can=false;
 }
  query.Close();
}
else
{
if(!Mnem.toLowerCase().trim().equals(txtName.getText().toLowerCase().trim()))
{
rs1=query.select("select * from IISC_RELATION_SCHEME  where PR_id="+ tree.ID+" and AS_id="+as +" and RS_name='"+ txtName.getText().trim() +"'");
if(rs1.next())
{ JOptionPane.showMessageDialog(null, "<html><center>Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
can=false;
}
 query.Close();
}  
}
if(!Mnem.equals(""))
if(txtName.getText().trim().equals(""))
JOptionPane.showMessageDialog(null, "<html><center>Name required!", "Relation Schemes", JOptionPane.ERROR_MESSAGE);
else if(txtName.getText().split(" ").length>1)
JOptionPane.showMessageDialog(null, "<html><center>Name cannot contain blank character!", "Relation Schemes", JOptionPane.ERROR_MESSAGE);
else if(can)
{
int key=-1;
Synthesys syn=new Synthesys();
syn.as=as;
syn.pr=tree.ID;
syn.con=connection;
RelationScheme rs = syn.getRelationScheme(id);
Iterator it=rs.kljuc.iterator();
while(it.hasNext())
{Set ke=(Set)it.next();
Iterator it1=ke.iterator();
String quer=new String("IISC_RS_NAME");
Set keys=new HashSet();
while(it1.hasNext())
{String kl=(String)it1.next();
keys.add(kl);
quer="(" +quer+ ") INNER JOIN IISC_RS_NAME_KEY as rs" + kl +" on (IISC_RS_NAME.RS_id_key=rs" + kl +".RS_id_key and rs" + kl +".Att_id="+ kl +")";
}
quer="select * from " + quer;
rs1=query.select(quer);
boolean can1=true;
while(rs1.next())
{

  String rsname=rs1.getString("RS_name");
  int rsid=rs1.getInt("RS_id_key");
  rs2=query1.select("select count(*) from IISC_RS_NAME_KEY where RS_id_key="+rsid);
if(rs2.next())
{if(rs2.getInt(1)==keys.size())
  if(!txtName.getText().trim().equals(rsname))
  { query1.Close();
   can1=false;
   query.update("update IISC_RS_NAME set RS_name='"+ txtName.getText().trim() +"' where RS_id_key=" + rsid); 
   break;
  }

}
 else
 query1.Close();
}
 query.Close();
if(can1)
{ 

 /*
rs1=query.select("select * from IISC_RS_NAME  where   RS_name='"+ txtName.getText().trim() +"'");
int idr=-1;
if(rs1.next())
{ idr=rs1.getInt("RS_id_key");
}
 query.Close();
if(idr>=0)
{ 
 query.update("delete from IISC_RS_NAME_KEY  where RS_id_key=" + idr);  
  query.update("delete from IISC_RS_NAME  where RS_id_key=" + idr);  
}
*/
it1=keys.iterator();
int rsid=0;
rs1=query.select("select max(RS_id_key)+1 from IISC_RS_NAME");
if(rs1.next())
{
rsid=rs1.getInt(1);
}
query.Close();
 query.update("insert into IISC_RS_NAME(RS_id_key,RS_name) values("+rsid+",'"+ txtName.getText().trim() +"')"); 
while(it1.hasNext())
 {
   String k1=(String)it1.next();
    query.update("insert into IISC_RS_NAME_KEY(RS_id_key,Att_id) values("+rsid+","+ k1 +")"); 
 }
}
}


query.update("update IISC_RELATION_SCHEME set RS_name='" + txtName.getText().trim() + "',RS_desc='" + txtDescription.getText().trim() + "' where RS_id=" + id + ""); 

rs1=query.select("select count(*) from IISC_RS_ROLE where  PR_id="+ tree.ID+" and AS_id="+as +"  and RS_id="+id);
if(rs1.next())
{
int j=rs1.getInt(1);
query.Close();
int r,i,m,d;
if(chRead.isSelected())r=1;
else r=0;
if(chModify.isSelected())m=1;
else m=0;
if(chInsert.isSelected())i=1;
else i=0;
if(chDelete.isSelected())d=1;
else d=0;
if(j>0)
{
   query.update("update IISC_RS_ROLE set RSR_read="+ r +",RSR_delete="+ d +",RSR_insert="+i +",RSR_modify="+ m +" where  PR_id="+ tree.ID+" and AS_id="+as +"  and RS_id="+id); 
}
else
{
  query.update("insert into IISC_RS_ROLE(RS_id,PR_id,AS_id,RSR_read,RSR_insert,RSR_modify,RSR_delete) values("+id+","+ tree.ID +","+as +","+ r +","+ i +","+ m +","+ d +")");  
}
}

Mnem=txtName.getText().trim();
tree.change(Mnem ,"Relation Schemes",txtName.getText().trim(),appsys);

if(k==1)
JOptionPane.showMessageDialog(null, "<html><center>Relation scheme saved!", "Relation Schemes", JOptionPane.INFORMATION_MESSAGE);
if(dispose)
dispose();
}

if(graph!=null)
   {
   
  
   graph.refreshpaint();
   }
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
public void setRScheme (String m)
  
 {try
    {
     btnApply.setEnabled(false);
  btnSave.setEnabled(false);
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(connection);
     lstAttKey.setListData(new Vector());
    rs=query.select("select count(*) from IISC_RELATION_SCHEME where PR_ID="+ tree.ID+" and AS_id="+as);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[] sa=query.selectArray("select * from IISC_RELATION_SCHEME where PR_ID="+ tree.ID+" and AS_id="+as+"  order by RS_name",j,4);
    sa[0]="";
    query.Close();
    cmbFun.removeAllItems();
    for(int k=0;k<sa.length; k++)
    cmbFun.addItem(sa[k]);
    cmbFun.setSelectedItem(m);
  txtName.setText(m);
  Mnem=m;
     
  if(Mnem.equals(""))tree.select_node(Mnem,"Relation Schemes",appsys);
    
     rs1=query.select("select * from IISC_RELATION_SCHEME where PR_id="+ tree.ID+" and AS_id="+as +"  and RS_name='"+ Mnem +"'");
     if(rs1.next())
    {
        txtName.setText(rs1.getString("RS_name"));
        txtDescription.setText(rs1.getString("RS_desc"));
        id=rs1.getInt("RS_id");
    }
  else
  {
        txtName.setText("");
        txtDescription.setText("");
  }
     query.Close();

 setK();
 
setAtt();
setRoles();

			/*nobrenovic: start*/
            setCheckConstraints();
            /*nobrnovic: end*/

  qtm2.setQueryRSConstraint("select distinct(RSC_id),RSC_name,IISC_RSC_TYPE.RSCT_type,RSCT_id from IISC_RS_Constraint,IISC_RSC_TYPE,IISC_RSC_RS_SET where IISC_RSC_RS_SET.RS_id="+ id +" and (LHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id or RHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id ) and IISC_RS_Constraint.RSC_type=IISC_RSC_TYPE.RSCT_id and IISC_RS_Constraint.PR_id="+ tree.ID +" and IISC_RS_Constraint.AS_id="+as+"  order by RSCT_id");
     qtm3.setQueryUnique("select distinct(RS_rbru) from IISC_RS_UNIQUE where PR_id="+ tree.ID +" and RS_id="+id+" order by RS_rbru");
Synthesys syn=new Synthesys();
syn.as=as;
syn.pr=tree.ID;
syn.con=connection;
String qstr=new String();
Iterator it=(syn.getCorespFormType(syn.getRelationScheme(id))).iterator();
while(it.hasNext())
qstr=qstr + " or IISC_FORM_TYPE.TF_id="+ it.next().toString();
qtm4.setFormQuery("select IISC_FORM_TYPE.TF_id,TF_mnem,AS_name from IISC_FORM_TYPE,IISC_APP_SYSTEM,IISC_TF_APPSYS where IISC_FORM_TYPE.PR_id="+ tree.ID +" and IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_APP_SYSTEM.AS_id=IISC_TF_APPSYS.AS_id and ( 1=0 " + qstr + ") order by AS_name,TF_mnem");
 
  table2.getColumnModel().getColumn(0).setPreferredWidth(0);
  table2.getColumnModel().getColumn(0).setWidth(0);
  table2.getColumnModel().getColumn(0).setMinWidth(0);
  table2.getColumnModel().getColumn(0).setMaxWidth(0);
   table3.getColumnModel().getColumn(0).setPreferredWidth(0);
  table3.getColumnModel().getColumn(0).setWidth(0);
  table3.getColumnModel().getColumn(0).setMinWidth(0);
  table3.getColumnModel().getColumn(0).setMaxWidth(0);
  /*nobrenovic: start*/
  //hide check constraint id column
   tblChkCon.getColumnModel().getColumn(0).setPreferredWidth(0);
   tblChkCon.getColumnModel().getColumn(0).setWidth(0);
   tblChkCon.getColumnModel().getColumn(0).setMinWidth(0);
   tblChkCon.getColumnModel().getColumn(0).setMaxWidth(0);
  /*nobrenovic: stop*/
  
 setU();   
rs1=query.select("select * from IISC_RELATION_SCHEME  where PR_id="+ tree.ID +" and AS_id="+as+" and RS_name>'" + m.trim() + "'" );
if(rs1.next())
{btnNext.setEnabled(true);
btnLast.setEnabled(true);}
else
{btnNext.setEnabled(false);
btnLast.setEnabled(false);}
 query.Close();
rs1=query.select("select * from IISC_RELATION_SCHEME where PR_id="+ tree.ID+" and AS_id="+as +" and  RS_name<'" + m.trim() + "'" );
if(rs1.next())
{btnPrev.setEnabled(true);
btnFirst.setEnabled(true);}
else
{btnPrev.setEnabled(false);
btnFirst.setEnabled(false);}
 query.Close();
}

catch(SQLException e)
{
 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}
}

public void setAtt ()
  
 {qtm1.setQueryRSATT("select * from IISC_ATTRIBUTE,IISC_RS_ATT where IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id and IISC_RS_ATT.PR_id="+ tree.ID  +" and IISC_RS_ATT.RS_id="+id+" order by Att_sequence");

}
public void setRoles ()
  
 {try
    {
   
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select *  from IISC_RS_ROLE where PR_ID="+ tree.ID+" and AS_id="+as +" and RS_id="+id);
    if(rs.next())
    {
    if(rs.getInt("RSR_read")==1)
    chRead.setSelected(true);
    else
    chRead.setSelected(false);
    if(rs.getInt("RSR_modify")==1)
    chModify.setSelected(true);
    else
    chModify.setSelected(false);
    if(rs.getInt("RSR_insert")==1)
    chInsert.setSelected(true);
    else
    chInsert.setSelected(false);
    if(rs.getInt("RSR_delete")==1)
    chDelete.setSelected(true);
    else
    chDelete.setSelected(false);
    }
    else
    {
      chRead.setSelected(false);
      chInsert.setSelected(false);
      chModify.setSelected(false);
      chDelete.setSelected(false);
    }
    query.Close();
    }
catch(SQLException e)
{
 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

} 
}

	/*nobrenovic: start*/
     public void setCheckConstraints() {
         qtmChkCon.setQueryCheckConstraint(
                        "select * from IISC_CHECK_CONSTRAINT, IISC_RSC_RS_SET "
                        + " where IISC_CHECK_CONSTRAINT.RS_Set_id=IISC_RSC_RS_SET.RS_Set_id" +
                        " and IISC_CHECK_CONSTRAINT.AS_id=IISC_RSC_RS_SET.AS_id" +
                        " and IISC_CHECK_CONSTRAINT.PR_id=IISC_RSC_RS_SET.PR_id" + 
                         " and IISC_RSC_RS_SET.PR_id = " + tree.ID + 
                         " and IISC_RSC_RS_SET.AS_id="  + as + 
                         " and IISC_RSC_RS_SET.RS_id=" + id);

     }
    /*nobrenovic: end*/
	
	
  private void btnHelp_actionPerformed(ActionEvent e)
  {Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
 Settings.Center(hlp);
 hlp.setVisible(true);
  }

  private void jComboBox1_actionPerformed(ActionEvent e)
  {
  try
  { if(btnSave.isEnabled())
    if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
      update(1);
  String s=cmbFun.getSelectedItem().toString();
  setRScheme(s);
  Mnem=s;
 tree.select_node(Mnem,"Relation Schemes",appsys);
   }
   catch(Exception ex)
    {
      //ex.printStackTrace();
    }
  }

  private void cmbFun_itemStateChanged(ItemEvent e)
  {
  }

  private void txtDescription_keyTyped(KeyEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void txtName_keyTyped(KeyEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void apply_ActionPerformed(ActionEvent e)
  {dispose=false;
  update(1);
  tree.select_node(Mnem,"Relation Schemes",appsys);
  btnApply.setEnabled(false);
  btnSave.setEnabled(false);
  }

  private void up_ActionPerformed(ActionEvent e)
  { JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
  if(table1.getSelectedRow()>0)
 {try{
 btnApply.setEnabled(true);
  btnSave.setEnabled(true);
rs=query.select("select  IISC_RS_ATT.RS_id,IISC_RS_ATT.Att_id,Att_sequence,ATT_mnem from IISC_RS_ATT,IISC_ATTRIBUTE  where  IISC_RS_ATT.RS_id="+ id +"  and IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id  and  IISC_ATTRIBUTE.PR_Id="+ tree.ID +"  and Att_mnem='"+ table1.getValueAt(table1.getSelectedRow(),0) +"'");
  if(rs.next())
 { 
 int r=rs.getInt(1);
 int at=rs.getInt(2);
 int k1= rs.getInt(3);
 String att=rs.getString(4);
 query.Close();
 rs=query.select("select max(Att_sequence) from IISC_RS_ATT  where Att_sequence<"+ k1 +" and IISC_RS_ATT.RS_id="+ r +"  and  IISC_RS_ATT.PR_Id="+ tree.ID  );
 if(rs.next())
 {int k= rs.getInt(1);
 query.Close();
 query.update("update IISC_RS_ATT set Att_sequence="+(k+1)+" where Att_sequence="+ k +" and IISC_RS_ATT.RS_id="+ r +"  and  IISC_RS_ATT.PR_Id="+ tree.ID  );
query.update("update IISC_RS_ATT set Att_sequence="+ k  +" where  IISC_RS_ATT.RS_id="+ r +"  and  IISC_RS_ATT.Att_Id="+ at + " and  IISC_RS_ATT.PR_Id="+ tree.ID  );
qtm1.setQueryRSATT("select * from IISC_ATTRIBUTE,IISC_RS_ATT where IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id and IISC_RS_ATT.PR_id="+ tree.ID +" and IISC_RS_ATT.RS_id="+id+" order by Att_sequence");
 table1.getSelectionModel().setSelectionInterval(k,k);
 }
 else
  query.Close();
 }
  else
 query.Close();
 btnApply.setEnabled(false);
  btnSave.setEnabled(false);
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
 }
  }

  private void down_ActionPerformed(ActionEvent e)
  { JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
  if(table1.getSelectedRow()<table1.getRowCount()-1)
  {
 try{
 btnApply.setEnabled(true);
  btnSave.setEnabled(true);
rs=query.select("select  IISC_RS_ATT.RS_id,IISC_RS_ATT.Att_id,Att_sequence,ATT_mnem from IISC_RS_ATT,IISC_ATTRIBUTE  where  IISC_RS_ATT.RS_id="+ id +"  and IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id  and  IISC_ATTRIBUTE.PR_Id="+ tree.ID +"  and Att_mnem='"+ table1.getValueAt(table1.getSelectedRow(),0)+"'");
  if(rs.next())
 { 
 int r=rs.getInt(1);
 int at=rs.getInt(2);
 int k1= rs.getInt(3);
 String att=rs.getString(4);
 query.Close();
 rs=query.select("select min(Att_sequence) from IISC_RS_ATT  where Att_sequence>"+ k1 +" and IISC_RS_ATT.RS_id="+ r +"  and  IISC_RS_ATT.PR_Id="+ tree.ID  );
 if(rs.next())
 {int k= rs.getInt(1);
 query.Close();
 query.update("update IISC_RS_ATT set Att_sequence="+(k-1)+" where Att_sequence="+ k +" and IISC_RS_ATT.RS_id="+ r +"  and  IISC_RS_ATT.PR_Id="+ tree.ID  );
query.update("update IISC_RS_ATT set Att_sequence="+ k  +" where  IISC_RS_ATT.RS_id="+ r +"  and  IISC_RS_ATT.Att_Id="+ at + " and  IISC_RS_ATT.PR_Id="+ tree.ID  );
 qtm1.setQueryRSATT("select * from IISC_ATTRIBUTE,IISC_RS_ATT where IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id and IISC_RS_ATT.PR_id="+ tree.ID +" and IISC_RS_ATT.RS_id="+id+" order by Att_sequence");
 table1.getSelectionModel().setSelectionInterval(k,k);
 }
 else
  query.Close();
 }
  else
 query.Close();
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
  }
  }


   private void setK()
  {
  qtm.setQueryRSKEY("select * from IISC_RS_KEY where PR_id="+ tree.ID +" and RS_id="+id+" order by RS_rbrk");
  table.addRowSelectionInterval(0,0);
  setKey(table.getSelectedRow()+1);
  }

  private void setU()
  {
  qtm3.setQueryUnique("select distinct( RS_rbru) from IISC_RS_UNIQUE where PR_id="+ tree.ID +" and RS_id="+id+" order by RS_rbru");
  if(table4.getRowCount()>0)
  {table4.addRowSelectionInterval(0,0);
  setUnique(table4.getSelectedRow()+1);}
  else
  lstAttUnique.setListData(new Object[0]);
  }
   private void setKey(int s)
  {
  JDBCQuery query=new JDBCQuery(connection);
ResultSet rs;
try
{
    rs=query.select("select count(*) from IISC_RSK_ATT where PR_ID="+ tree.ID +" and RS_rbrk =" + s + "  and  RS_ID="+ id);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[]  sa2=query.selectArray("select * from IISC_ATTRIBUTE,IISC_RSK_ATT where IISC_ATTRIBUTE.Att_id=IISC_RSK_ATT.Att_id and IISC_RSK_ATT.PR_ID="+ tree.ID +" and RS_rbrk =" + s + "  and  RS_ID="+ id + " order by Att_rbrk",j,3);
    sa2[0]="";
    String[] sak=new String[sa2.length-1];
    for (int k=1;k<sa2.length;k++)sak[k-1]= sa2[k];
    query.Close();
    lstAttKey.setListData(sak);
 }
 catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}
  }
  
     private void setUnique(int s)
  {
  JDBCQuery query=new JDBCQuery(connection);
ResultSet rs;
try
{
    rs=query.select("select count(*) from IISC_RS_UNIQUE where PR_ID="+ tree.ID +" and RS_rbru =" + s + "  and  RS_ID="+ id);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[]  sa2=query.selectArray("select * from IISC_ATTRIBUTE,IISC_RS_UNIQUE where IISC_ATTRIBUTE.Att_id=IISC_RS_UNIQUE.Att_id and IISC_RS_UNIQUE.PR_ID="+ tree.ID +" and RS_rbru =" + s + "  and  RS_ID="+ id + " order by Att_rbru",j,3);
    sa2[0]="";
    String[] sak=new String[sa2.length-1];
    for (int k=1;k<sa2.length;k++)sak[k-1]= sa2[k];
    query.Close();
    lstAttUnique.setListData(sak);
 }
 catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}
  }
   private void seUnique(int s)
  {
  JDBCQuery query=new JDBCQuery(connection);
ResultSet rs;
try
{
    rs=query.select("select count(*) from IISC_RS_UNIQUE where PR_ID="+ tree.ID +" and RS_rbru =" + s + "  and  RS_ID="+ id);
    rs.next();
    int j=rs.getInt(1);
    query.Close();
    String[]  sa2=query.selectArray("select * from IISC_ATTRIBUTE,IISC_RS_UNIQUE where IISC_ATTRIBUTE.Att_id=IISC_RS_UNIQUE.Att_id and IISC_RS_UNIQUE.PR_ID="+ tree.ID +" and RS_rbru =" + s + "  and  RS_ID="+ id + " order by Att_rbru",j,3);
    sa2[0]="";
    String[] sak=new String[sa2.length-1];
    for (int k=1;k<sa2.length;k++)sak[k-1]= sa2[k];
    query.Close();
    lstAttKey.setListData(sak);
 }
 catch(SQLException ef)
{
 JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}
  }
  private void up1_ActionPerformed(ActionEvent e)
  {JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
  if(lstAttKey.getSelectedIndex()>0)
 {try{
 btnApply.setEnabled(true);
  btnSave.setEnabled(true);
rs=query.select("select  IISC_RSK_ATT.RS_id,IISC_RSK_ATT.Att_id,Att_rbrk from IISC_RSK_ATT,IISC_ATTRIBUTE  where  IISC_RSK_ATT.RS_id="+ id +"  and IISC_ATTRIBUTE.Att_id=IISC_RSK_ATT.Att_id  and  IISC_ATTRIBUTE.PR_Id="+ tree.ID +"  and Att_mnem='"+ lstAttKey.getSelectedValue().toString() +"'  and RS_rbrk="+ table.getValueAt(table.getSelectedRow(),0) +"");
  if(rs.next())
 { 
 int r=rs.getInt(1);
 int at=rs.getInt(2);
 int k1= rs.getInt(3);
 query.Close();
 rs=query.select("select max(Att_rbrk) from IISC_RSK_ATT  where Att_rbrk<"+ k1 +" and IISC_RSK_ATT.RS_id="+ r +"  and  IISC_RSK_ATT.PR_Id="+ tree.ID + " and RS_rbrk="+ table.getValueAt(table.getSelectedRow(),0) +"" );
 if(rs.next())
 {int k= rs.getInt(1);
 query.Close();
 query.update("update IISC_RSK_ATT set Att_rbrk="+(k+1)+" where Att_rbrk="+ k +" and IISC_RSK_ATT.RS_id="+ r +"  and  IISC_RSK_ATT.PR_Id="+ tree.ID + " and RS_rbrk="+ table.getValueAt(table.getSelectedRow(),0) +""  );
query.update("update IISC_RSK_ATT set Att_rbrk="+ k  +" where  IISC_RSK_ATT.RS_id="+ r +"  and  IISC_RSK_ATT.Att_Id="+ at + " and  IISC_RSK_ATT.PR_Id="+ tree.ID + " and RS_rbrk="+ table.getValueAt(table.getSelectedRow(),0) +""  );
 setKey(table.getSelectedRow()+1);
 lstAttKey.setSelectedIndex(k-1);
 }
 else
  query.Close();
 }
  else
 query.Close();
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
 }
  }

  private void down1_ActionPerformed(ActionEvent e)
   {JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
 if(lstAttKey.getSelectedIndex()<lstAttKey.getModel().getSize()-1)
 {try{
 btnApply.setEnabled(true);
  btnSave.setEnabled(true);
rs=query.select("select  IISC_RSK_ATT.RS_id,IISC_RSK_ATT.Att_id,Att_rbrk from IISC_RSK_ATT,IISC_ATTRIBUTE  where  IISC_RSK_ATT.RS_id="+ id +"  and IISC_ATTRIBUTE.Att_id=IISC_RSK_ATT.Att_id  and  IISC_ATTRIBUTE.PR_Id="+ tree.ID +"  and Att_mnem='"+ lstAttKey.getSelectedValue().toString() +"'  and RS_rbrk="+ table.getValueAt(table.getSelectedRow(),0) +"");
  if(rs.next())
 { 
 int r=rs.getInt(1);
 int at=rs.getInt(2);
 int k1= rs.getInt(3);
 query.Close();
 rs=query.select("select min(Att_rbrk) from IISC_RSK_ATT  where Att_rbrk>"+ k1 +" and IISC_RSK_ATT.RS_id="+ r +"  and  IISC_RSK_ATT.PR_Id="+ tree.ID + " and RS_rbrk="+ table.getValueAt(table.getSelectedRow(),0) +"" );
 if(rs.next())
 {int k= rs.getInt(1);
 query.Close();
 query.update("update IISC_RSK_ATT set Att_rbrk="+(k-1)+" where Att_rbrk="+ k +" and IISC_RSK_ATT.RS_id="+ r +"  and  IISC_RSK_ATT.PR_Id="+ tree.ID + " and RS_rbrk="+ table.getValueAt(table.getSelectedRow(),0) +""  );
query.update("update IISC_RSK_ATT set Att_rbrk="+ k  +" where  IISC_RSK_ATT.RS_id="+ r +"  and  IISC_RSK_ATT.Att_Id="+ at + " and  IISC_RSK_ATT.PR_Id="+ tree.ID + " and RS_rbrk="+ table.getValueAt(table.getSelectedRow(),0) +""  );
 setKey(table.getSelectedRow()+1);
 lstAttKey.setSelectedIndex(k-1);
 }
 else
  query.Close();
 }
  else
 query.Close();
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
 }
  }

  private void up3_ActionPerformed(ActionEvent e)
  {
 JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
  if(table.getSelectedRowCount()>0)
 {try{
 btnApply.setEnabled(true);
  btnSave.setEnabled(true);
 rs=query.select("select max(RS_rbrk) from IISC_RSK_ATT  where RS_rbrk<"+ table.getValueAt(table.getSelectedRow(),0) +" and IISC_RSK_ATT.RS_id="+ id +"  and  IISC_RSK_ATT.PR_Id="+ tree.ID);
 if(rs.next())
 {int k= rs.getInt(1);
 query.Close();
 if(k < (new Integer(table.getValueAt(table.getSelectedRow(),0).toString())).intValue() && k>0)
 {query.update("update IISC_RS_KEY set RS_rbrk=-1 where RS_rbrk="+ table.getValueAt(table.getSelectedRow(),0) +" and RS_id="+ id +"  and  PR_Id="+ tree.ID  );
 query.update("update IISC_RS_KEY set RS_rbrk="+ table.getValueAt(table.getSelectedRow(),0)  +" where RS_rbrk="+ k  +" and RS_id="+ id +"  and  PR_Id="+ tree.ID  );
 query.update("update IISC_RS_KEY set RS_rbrk="+ k  +" where RS_rbrk=-1 and RS_id="+ id +"  and  PR_Id="+ tree.ID  );

 query.update("update IISC_RSK_ATT set RS_rbrk=-1 where RS_rbrk="+ k +" and IISC_RSK_ATT.RS_id="+ id +"  and  IISC_RSK_ATT.PR_Id="+ tree.ID  );
query.update("update IISC_RSK_ATT set RS_rbrk="+ k  +" where  IISC_RSK_ATT.RS_id="+ id +"  and  IISC_RSK_ATT.PR_Id="+ tree.ID + " and RS_rbrk="+ table.getValueAt(table.getSelectedRow(),0) +""  );
query.update("update IISC_RSK_ATT set RS_rbrk="+ (k+1)  +" where RS_rbrk=-1 and IISC_RSK_ATT.RS_id="+ id +"  and  IISC_RSK_ATT.PR_Id="+ tree.ID  );
//setK();
 setKey(k);
 // lstKey.setSelectedIndex(k-1);
  qtm.setQueryRSKEY("select * from IISC_RS_KEY where PR_id="+ tree.ID +" and RS_id="+id+" order by RS_rbrk");
  table.getSelectionModel().setSelectionInterval(k-1,k-1);
 }
 }
 else
  query.Close();
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
 }
  }

  private void down3_ActionPerformed(ActionEvent e)
  {JDBCQuery query=new JDBCQuery(connection);
    ResultSet rs;
  if(table.getSelectedRow()<table.getRowCount()-1)
 {try{
 btnApply.setEnabled(true);
  btnSave.setEnabled(true);
 rs=query.select("select min(RS_rbrk) from IISC_RSK_ATT  where RS_rbrk>"+  table.getValueAt(table.getSelectedRow(),0) +" and IISC_RSK_ATT.RS_id="+ id +"  and  IISC_RSK_ATT.PR_Id="+ tree.ID);
 if(rs.next())
 {int k= rs.getInt(1);
 query.Close();
 query.update("update IISC_RS_KEY set RS_rbrk=-1 where RS_rbrk="+  table.getValueAt(table.getSelectedRow(),0) +" and RS_id="+ id +"  and  PR_Id="+ tree.ID  );
 query.update("update IISC_RS_KEY set RS_rbrk="+  table.getValueAt(table.getSelectedRow(),0) +" where RS_rbrk="+ k  +" and RS_id="+ id +"  and  PR_Id="+ tree.ID  );
 query.update("update IISC_RS_KEY set RS_rbrk="+ k  +" where RS_rbrk=-1 and RS_id="+ id +"  and  PR_Id="+ tree.ID  );
 
 query.update("update IISC_RSK_ATT set RS_rbrk=-1 where RS_rbrk="+ k +" and IISC_RSK_ATT.RS_id="+ id +"  and  IISC_RSK_ATT.PR_Id="+ tree.ID  );
query.update("update IISC_RSK_ATT set RS_rbrk="+ k  +" where  IISC_RSK_ATT.RS_id="+ id +"  and  IISC_RSK_ATT.PR_Id="+ tree.ID + " and RS_rbrk="+  table.getValueAt(table.getSelectedRow(),0) +""  );
query.update("update IISC_RSK_ATT set RS_rbrk="+ (k-1)  +" where RS_rbrk=-1 and IISC_RSK_ATT.RS_id="+ id +"  and  IISC_RSK_ATT.PR_Id="+ tree.ID  );
//setK();
 setKey(k);
  //lstKey.setSelectedIndex(k-1);
  qtm.setQueryRSKEY("select * from IISC_RS_KEY where PR_id="+ tree.ID +" and RS_id="+id+" order by RS_rbrk");
   table.getSelectionModel().setSelectionInterval(k-1,k-1);
 }
 else
  query.Close();
 }
catch(SQLException ex)
{
 JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
 }
  }

  private void lstCandidate_mouseClicked(MouseEvent e)
  {
 // lstCandidate.setSelectedIndex(lstKey.getSelectedIndex());
  }


  private void txtName_actionPerformed(ActionEvent e)
  {
  }
private Set getDescenders(int i)
  {
  try{
    ResultSet rs,rs1;
    Set Desc=new HashSet();
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select  * from IISC_GRAPH_CLOSURE where RS_inferior="+ i);
    while(rs.next())
    {
    int rsi=rs.getInt("RS_superior");
    Desc.add(""+rsi);
    Desc.addAll(getDescenders(rsi));
    }
   query.Close();
 return Desc; }

catch(SQLException ae)
{
 JOptionPane.showMessageDialog(null, ae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
return null;
}

  }
   private void doMouseClicked2(MouseEvent e)
  {
if(e.getClickCount()>1 &&table2.getSelectedRowCount()==1)
{
 Constraint cons=new Constraint((IISFrameMain)getParent(),"",(new Integer(table2.getValueAt(table2.getSelectedRow(),0).toString())).intValue(),true,connection,tree.ID,as);
  Settings.Center(cons);
  cons.setVisible(true);
  table2.getSelectionModel().clearSelection();
  txtConDescription.setText("");
  }
  else
  {
  try{
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select RSC_desc from IISC_RS_CONSTRAINT where RSC_id="+ (new Integer(table2.getValueAt(table2.getSelectedRow(),0).toString())).intValue() +"  and AS_ID="+ as +" and  PR_ID="+ tree.ID );
    if(rs.next())
    txtConDescription.setText(rs.getString(1));
 
 query.Close();
  }
  catch(SQLException ae)
  {
    JOptionPane.showMessageDialog(null, ae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
  }
  }
   
  }
  private void doMouseClicked(MouseEvent e)
  {
if(table.getSelectedColumn()==2)
 {try{
    ResultSet rs,rs1;
   // Set  Desc=getDescenders(id);
  //  Iterator it=Desc.iterator();
    String sql=new String("");
    sql=sql+" RS_id="+id;
   /* while(it.hasNext())
    {
     sql=sql+" or RS_id="+(String)it.next(); 
    }*/
    JDBCQuery query=new JDBCQuery(connection);
    int k=table.getSelectedRow();
    if(table.getValueAt(table.getSelectedRow(),1)!=null)
    {query.update("update IISC_RS_KEY set RS_primary_key=0  where PR_ID="+ tree.ID +" and ("+ sql +")  and RS_candidate=1 ");
    query.update("update IISC_RS_KEY  set RS_primary_key=1   where PR_ID="+ tree.ID +" and RS_rbrk="+ table.getValueAt(table.getSelectedRow(),0) +"  and RS_candidate=1    and  RS_ID="+ id);
    rs=query.select("select distinct(IISC_ATTRIBUTE.Att_id) from IISC_ATTRIBUTE,IISC_RSK_ATT,IISC_RS_KEY,IISC_RS_ATT where IISC_ATTRIBUTE.Att_id=IISC_RSK_ATT.Att_id and IISC_RSK_ATT.RS_RBRK=IISC_RS_KEY.RS_RBRK and RS_primary_key=1  and IISC_RS_ATT.PR_ID="+ tree.ID +" and IISC_RS_ATT.RS_ID="+ id +"  and IISC_RSK_ATT.PR_ID="+ tree.ID +" and IISC_RSK_ATT.RS_ID="+ id +"  and IISC_RS_KEY.PR_ID="+ tree.ID +" and IISC_RS_KEY.RS_ID="+ id);
    while(rs.next())
    {
     query.update("update IISC_RS_ATT set ATT_null=0 where PR_ID="+ tree.ID +" and Att_id="+ rs.getInt("Att_id") +"  and  RS_ID="+ id); 
    }
    query.Close();
    qtm.setQueryRSKEY("select * from IISC_RS_KEY where PR_id="+ tree.ID +" and RS_id="+id+" order by RS_rbrk");
    table.getSelectionModel().setSelectionInterval(k,k);
 qtm1.setQueryRSATT("select * from IISC_ATTRIBUTE,IISC_RS_ATT where IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id and IISC_RS_ATT.PR_id="+ tree.ID +" and IISC_RS_ATT.RS_id="+id+" order by Att_sequence");
    }
  }

catch(SQLException ae)
{
 JOptionPane.showMessageDialog(null, ae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

}}
setKey( table.getSelectedRow()+1);
  }
  private void doMouseClicked12(MouseEvent e)
  {
if(table4.getSelectedColumn()==0)
 {
 setUnique(table4.getSelectedRow()+1);
 }
  }
 private void doMouseClicked1(MouseEvent e)
  {
try{
if(table1.getSelectedColumn()==1)
 {
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(connection);
    int k=table1.getSelectedRow();
    rs=query.select("select * from IISC_ATTRIBUTE,IISC_RSK_ATT,IISC_RS_KEY,IISC_RS_ATT where IISC_ATTRIBUTE.Att_id=IISC_RSK_ATT.Att_id and IISC_ATTRIBUTE.Att_mnem='"+ table1.getValueAt(table1.getSelectedRow(),0) +"' and IISC_RSK_ATT.RS_RBRK=IISC_RS_KEY.RS_RBRK  and IISC_RS_ATT.PR_ID="+ tree.ID +" and IISC_RS_ATT.RS_ID="+ id +"  and IISC_RSK_ATT.PR_ID="+ tree.ID +" and IISC_RSK_ATT.RS_ID="+ id +"  and IISC_RS_KEY.PR_ID="+ tree.ID +" and IISC_RS_KEY.RS_ID="+ id);
    if(!rs.next())
    { query.Close();
    rs=query.select("select * from IISC_ATTRIBUTE,IISC_RS_ATT where IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id and IISC_ATTRIBUTE.Att_mnem='"+ table1.getValueAt(table1.getSelectedRow(),0) +"' and IISC_RS_ATT.PR_ID="+ tree.ID +" and IISC_RS_ATT.RS_ID="+ id);
    rs.next();
    int j=rs.getInt("Att_id");
    int i=rs.getInt("Att_null");
    if(i==0)i=1;
    else
    i=0;
    query.Close();
    
    query.update("update IISC_RS_ATT set Att_null="+ i +"  where PR_ID="+ tree.ID +" and Att_id="+ j + " and RS_ID="+ id);
   qtm1.setQueryRSATT("select * from IISC_ATTRIBUTE,IISC_RS_ATT where IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id and IISC_RS_ATT.PR_id="+ tree.ID +" and IISC_RS_ATT.RS_id="+id+" order by Att_sequence");
table1.getSelectionModel().setSelectionInterval(k,k);
 }
 else
 query.Close();
  }
  else
  if(table1.getSelectedColumn()==2)
 {
    ResultSet rs,rs1;
    JDBCQuery query=new JDBCQuery(connection);
    btnApply.setEnabled(true);
    btnSave.setEnabled(true);
    int k=table1.getSelectedRow();
    rs=query.select("select * from IISC_ATTRIBUTE,IISC_RS_ATT where IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id and IISC_ATTRIBUTE.Att_mnem='"+ table1.getValueAt(table1.getSelectedRow(),0) +"' and IISC_RS_ATT.PR_ID="+ tree.ID +" and IISC_RS_ATT.RS_ID="+ id);
    rs.next();
    int j=rs.getInt("Att_id");
    int i=rs.getInt("Att_modifiable");
    if(i==0)i=1;
    else
    i=0;
    query.Close();
    
    query.update("update IISC_RS_ATT set Att_modifiable="+ i +"  where PR_ID="+ tree.ID +" and Att_id="+ j + " and RS_ID="+ id);
   qtm1.setQueryRSATT("select * from IISC_ATTRIBUTE,IISC_RS_ATT where IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id and IISC_RS_ATT.PR_id="+ tree.ID +" and IISC_RS_ATT.RS_id="+id+" order by Att_sequence");
table1.getSelectionModel().setSelectionInterval(k,k);
 }
}
catch(SQLException ae)
{
 JOptionPane.showMessageDialog(null, ae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

} 

  }

  private void jButton1_actionPerformed(ActionEvent e)
  {if(table2.getSelectedRowCount()==1)
  {
  Constraint cons=new Constraint((IISFrameMain)getParent(),"",(new Integer(table2.getValueAt(table2.getSelectedRow(),0).toString())).intValue(),true,connection,tree.ID,as);
  table2.getSelectionModel().clearSelection();
  txtConDescription.setText("");
  Settings.Center(cons);
  cons.setVisible(true);

  }
  }

  private void chRead_mouseClicked(MouseEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void chInsert_mouseClicked(MouseEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void chModify_mouseClicked(MouseEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void chDelete_mouseClicked(MouseEvent e)
  {btnApply.setEnabled(true);
  btnSave.setEnabled(true);
  }

  private void table2_propertyChange(PropertyChangeEvent e)
  {
    }

  private void table2_keyReleased(KeyEvent e)
  {if(table2.getSelectedRowCount()>0){
  try{
    ResultSet rs;
    JDBCQuery query=new JDBCQuery(connection);
    rs=query.select("select RSC_desc from IISC_RS_CONSTRAINT where RSC_id="+ (new Integer(table2.getValueAt(table2.getSelectedRow(),0).toString())).intValue() +"  and AS_ID="+ as +" and  PR_ID="+ tree.ID );
    if(rs.next())
    txtConDescription.setText(rs.getString(1));
    query.Close();
  }
  catch(SQLException ae)
  {
    JOptionPane.showMessageDialog(null, ae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
  }
  }

  }

  private void form_actionPerformed(ActionEvent e)
  {if(table3.getSelectedRowCount()==1)
  {
 
  Form form=new Form((IISFrameMain)getParent(),"",true,connection,(new Integer(table3.getValueAt(table3.getSelectedRow(),0).toString())).intValue(),tree,table3.getValueAt(table3.getSelectedRow(),2).toString());
  table3.getSelectionModel().clearSelection();
  Settings.Center(form);
  form.setVisible(true);

  }
  }

  private void doMouseClicked3(MouseEvent e)
  {  if(e.getClickCount()>1 && table3.getSelectedRowCount()==1)
  {
 
  Form form=new Form((IISFrameMain)getParent(),"",true,connection,(new Integer(table3.getValueAt(table3.getSelectedRow(),0).toString())).intValue(),tree,table3.getValueAt(table3.getSelectedRow(),2).toString());
  table3.getSelectionModel().clearSelection();
  Settings.Center(form);
  form.setVisible(true);

  }
  }

  private void chModify_actionPerformed(ActionEvent e)
  {if(!chModify.isSelected())
    {   
    try{
      ResultSet rs;
      JDBCQuery query=new JDBCQuery(connection);
      rs=query.select("select * from IISC_RS_ATT where Att_modifiable=1 and  RS_id="+ id +"  and AS_ID="+ as +" and  PR_ID="+ tree.ID );
      if(rs.next())
      {chModify.setSelected(true);
      JOptionPane.showMessageDialog(null,"Action cannot be performed. Some attributes in the relation scheme are marked as modifiable!", "Error", JOptionPane.ERROR_MESSAGE);
      }
      query.Close();
    }
     catch(SQLException ae)
    {
      JOptionPane.showMessageDialog(null, ae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
      
    }
  }

    /*nobrenovic: start*/
     private void showChkConDlg() {
         if (tblChkCon.getSelectedRowCount() == 1) {

             int chkConID = (new Integer(tblChkCon.getValueAt(tblChkCon.getSelectedRow(),0).toString())).intValue();
             CheckConsDlg ccDlg = new CheckConsDlg((IISFrameMain)getParent(), "Check Constraint", true, 
                                                     connection, tree.ID, as, chkConID);
             tblChkCon.getSelectionModel().clearSelection();
             Settings.Center(ccDlg);
             ccDlg.setVisible(true);

         }
     }
    /*nobrenovic: stop*/

    private void tblChkCon_mouseClicked(MouseEvent e) {
        if(e.getClickCount() > 1)
            showChkConDlg();
    }

    private void btnChkConView_actionPerformed(ActionEvent e) {
        showChkConDlg();
    }
}
