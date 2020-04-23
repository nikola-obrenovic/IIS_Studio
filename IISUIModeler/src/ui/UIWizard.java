package ui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import javax.swing.ScrollPaneConstants;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.border.Border;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JToolBar;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.JSplitPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableColumn;

public class UIWizard extends JDialog 
{
  private JButton btnBack = new JButton();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
  private ImageIcon imageNew = new ImageIcon(IISFrameMain.class.getResource("icons/new.gif"));
  private ImageIcon imageOK = new ImageIcon(IISFrameMain.class.getResource("icons/ok.gif"));
  private ImageIcon imageDelete = new ImageIcon(IISFrameMain.class.getResource("icons/erase.gif"));
  private ImageIcon imageApply = new ImageIcon(IISFrameMain.class.getResource("icons/apply.gif"));
  private ImageIcon imageCancel = new ImageIcon(IISFrameMain.class.getResource("icons/close.gif"));
  private ImageIcon imageNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
  private ImageIcon imagePrevios = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
  private ImageIcon imageFirst = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
  private ImageIcon imageLast = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
  private ImageIcon imageDeleteAll = new ImageIcon(IISFrameMain.class.getResource("icons/obsolutereport.gif"));
  private ImageIcon imageDuplicate = new ImageIcon(IISFrameMain.class.getResource("icons/copy.gif"));    
  private ImageIcon iconCombo = new ImageIcon(IISFrameMain.class.getResource("icons/combo.gif"));
  private ImageIcon iconCheck = new ImageIcon(IISFrameMain.class.getResource("icons/checkbox.gif"));
  private ImageIcon iconRadio = new ImageIcon(IISFrameMain.class.getResource("icons/radio.gif"));
  private ImageIcon iconText = new ImageIcon(IISFrameMain.class.getResource("icons/textbox.gif"));
  private ImageIcon iconList = new ImageIcon(IISFrameMain.class.getResource("icons/list.gif"));
  private JButton btnNext = new JButton();
  private JButton btnPrewiev = new JButton();
  private JButton btnSave = new JButton();
  private JButton btnClose = new JButton();
  private JButton btnHelp = new JButton();
  public JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel pnlGlobal = new JPanel();
  private JLabel jLabel4 = new JLabel();
  private JButton btnFontGlobal = new JButton();
  private JLabel jLabel6 = new JLabel();
  public JLabel lbBackground = new JLabel();
  private JButton btnBackground = new JButton();
  private JLabel jLabel8 = new JLabel();
  private JButton btnForgrond = new JButton();
  public JLabel lbForeground = new JLabel();
  private JButton btnIcon = new JButton();
  public JLabel lbIcon = new JLabel();
  private JLabel jLabel13 = new JLabel();
  private SpecialTableModel model = new SpecialTableModel();
  private UIWizard wizard;
  private Connection connection;
  private int id;
  private JScrollPane jScrollPane2 = new JScrollPane();
  public JEditorPane lbFontGlobal = new JEditorPane();
  private ButtonGroup bgrp1=new ButtonGroup();
  private ButtonGroup bgrp2=new ButtonGroup();
  private ButtonGroup bgrp3=new ButtonGroup();
  private ButtonGroup bgrp4=new ButtonGroup();
  private ButtonGroup bgrp5=new ButtonGroup();
  private ButtonGroup bgrp6=new ButtonGroup();
  private ButtonGroup bgrp7=new ButtonGroup();
  private ButtonGroup bgrp8=new ButtonGroup();
  private ButtonGroup bgrp9=new ButtonGroup();
  private ButtonGroup bgrp10=new ButtonGroup();
  private ButtonGroup bgrp11=new ButtonGroup();
  private JLabel jLabel5 = new JLabel();
  private JButton btnInputFontGlobal = new JButton();
  private JScrollPane jScrollPane4 = new JScrollPane();
  public JEditorPane lbInputFontGlobal = new JEditorPane();
  private JLabel jLabel7 = new JLabel();
  private JButton btnDesktop = new JButton();
  public JLabel lbDesktop = new JLabel();
  private JLabel jLabel10 = new JLabel();
  public JTextField txtName = new JTextField();
  private JLabel jLabel17 = new JLabel();
  public JRadioButton rdFixedSize = new JRadioButton();
  public JRadioButton rdFullScreen = new JRadioButton();
  public JTextField txtX = new JTextField();
  public JTextField txtY = new JTextField();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel18 = new JLabel();
  public JRadioButton rdLeftonTop = new JRadioButton();
  public JRadioButton rdCenter = new JRadioButton();
  public JRadioButton rdCustom = new JRadioButton();
  public myScreen lbScreen = new myScreen();
  public int xPos, yPos;
  public JLabel lbX = new JLabel();
  public JLabel lbY = new JLabel();
  private JPanel pnlForm = new JPanel();
  private JLabel lbTitle = new JLabel();
  public JCheckBox chTitle = new JCheckBox();
  private JLabel jLabel11 = new JLabel();
  private JButton btnFontTitle = new JButton();
  private JScrollPane jScrollPane3 = new JScrollPane();
  public JEditorPane lbFontTitle = new JEditorPane();
  private JLabel jLabel12 = new JLabel();
  public JLabel lbForegroundForm = new JLabel();
  private JButton btnForgroundForm = new JButton();
  private JButton btnBackgroundForm = new JButton();
  public JLabel lbBackgroundForm = new JLabel();
  private JLabel jLabel14 = new JLabel();
  private JPanel jPanel1 = new JPanel();
  public JComboBox cmbOverflow = new JComboBox();
  public JCheckBox chOverflow = new JCheckBox();
  private JLabel jLabel1117 = new JLabel();
  private JLabel jLabel1116 = new JLabel();
  public JComboBox cmbColumnWidth = new JComboBox();
  private JLabel jLabel1115 = new JLabel();
  public JComboBox cmbRowHeight = new JComboBox();
  private JLabel jLabel1111 = new JLabel();
  public JLabel lbForegroundCell = new JLabel();
  private JButton btnForgroundCell = new JButton();
  private JButton btnBackgroundCell = new JButton();
  private JLabel jLabel1112 = new JLabel();
  public JLabel lbBackgroundCell = new JLabel();
  public JLabel lbForegroundTablePanel = new JLabel();
  private JButton btnForgroundTablePanel = new JButton();
  private JButton btnBackgroundTablePanel = new JButton();
  public JLabel lbBackgroundTablePanel = new JLabel();
  private JLabel jLabel123 = new JLabel();
  private JLabel jLabel122 = new JLabel();
  private JPanel jPanel2 = new JPanel();
  public JComboBox cmbOrientation = new JComboBox();
  private JLabel jLabel1114 = new JLabel();
  public JRadioButton rdTabbed = new JRadioButton();
  public JRadioButton rdNormal = new JRadioButton();
  private JLabel jLabel1113 = new JLabel();
  public JComboBox cmbItemPanelSpacing = new JComboBox();
  private JLabel jLabel3 = new JLabel();
  public JCheckBox chContex = new JCheckBox();
  private JLabel jLabel121 = new JLabel();
  public JLabel lbBackgroundItemPanel = new JLabel();
  private JButton btnBackgroundItemPanel = new JButton();
  private JButton btnForgroundItemPanel = new JButton();
  public JLabel lbForegroundItemPanel = new JLabel();
  private JLabel jLabel120 = new JLabel();
  private JLabel jLabel1110 = new JLabel();
  private JLabel jLabel119 = new JLabel();
  public JLabel lbForegroundInput = new JLabel();
  public JLabel lbBackgroundInput = new JLabel();
  private JButton btnBackgroundInput = new JButton();
  private JButton btnForgroundInput = new JButton();
  private JPanel jPanel4 = new JPanel();
  private JLabel jLabel115 = new JLabel();
  public JComboBox cmbTitleOffset = new JComboBox();
  private JLabel jLabel2 = new JLabel();
  public JCheckBox chItalic = new JCheckBox();
  public JCheckBox chBold = new JCheckBox();
  private JLabel jLabel114 = new JLabel();
  public JComboBox cmbTitlePosition = new JComboBox();
  private JLabel jLabel112 = new JLabel();
  public JComboBox cmbTitleAlign = new JComboBox();
  private JPanel jPanel3 = new JPanel();
  private JButton btnBackgroundButton = new JButton();
  private JButton btnForgroundButton = new JButton();
  public JLabel lbForegroundButton = new JLabel();
  public JLabel lbBackgroundButton = new JLabel();
  private JLabel jLabel110 = new JLabel();
  private JLabel jLabel19 = new JLabel();
    private JLabel jLabel16 = new JLabel();
    public JRadioButton rdGraphical = new JRadioButton();
  public JRadioButton rdTextual = new JRadioButton();
  private JLabel jLabel15 = new JLabel();
  public JComboBox cmbContex = new JComboBox();
  public Template template;
  public int ID;
  public int group;
  public PTree tree;
  public JCheckBox rdMenuCall = new JCheckBox();
  public JCheckBox rdButtonCall = new JCheckBox();
  public JCheckBox rdKeyCall = new JCheckBox();
  public JComboBox cmbVisibleRows = new JComboBox();
  private JLabel jLabel1118 = new JLabel();
  public JComboBox cmbItemSpacing = new JComboBox();
  private JLabel jLabel118 = new JLabel();
  public JCheckBox rdResizable = new JCheckBox();
  private ImageIcon imageScreen = new ImageIcon(IISFrameMain.class.getResource("icons/screen.gif"));
  private ImageIcon imageScreenForm = new ImageIcon(IISFrameMain.class.getResource("icons/screen_form.gif"));
  private ImageIcon imageFormPrewiev = new ImageIcon(IISFrameMain.class.getResource("icons/form_prewiev.gif"));
  private ImageIcon imageFormContent = new ImageIcon(IISFrameMain.class.getResource("icons/form_content.gif"));
  private JLabel jLabel9 = new JLabel();
  private JLabel jLabel125 = new JLabel();
  public JLabel lbIcon1 = new JLabel();
  public JLabel lbForm = new JLabel();
  public JComboBox cmbFormTitle = new JComboBox();
  private JLabel lbContent = new JLabel();
  private JScrollPane tableScrollPane = new JScrollPane();
  private CustomTableCellRenderer tableCellRenderer= new CustomTableCellRenderer(0);
  private CustomTableCellRenderer tableCellRenderer1= new CustomTableCellRenderer(1);
  private JScrollPane pnOverflow = new JScrollPane();
  public JTextArea lbOverflow = new JTextArea();
  private JScrollPane scTablePrewiev = new JScrollPane();
  private JSplitPane pnTablePrewiev = new JSplitPane();
  private JPanel scButtonPrewiev = new JPanel();
  private JPanel pnEditingButton = new JPanel();
  private JButton btnPrewApply = new JButton();
  private FlowLayout flowLayout1 = new FlowLayout();
  private JButton btnPrewOK = new JButton();
  private JButton btnPrewNew = new JButton();
  private JButton btnPrewDelete = new JButton();
  private JButton btnPrewDeleteAll = new JButton();
  private JButton btnPrewCancel = new JButton();
  private JButton btnPrewDuplicate = new JButton();
  private JPanel pnNavigationButton = new JPanel();
  private FlowLayout flowLayout2 = new FlowLayout();
  private JButton btnPrewFirst = new JButton();
  private JButton btnPrewPrevios = new JButton();
  private JButton btnPrewNext = new JButton();
  private JButton btnPrewLast = new JButton();
  private JLabel lbNavigation = new JLabel();
  public int itemID=0;
  private JPanel jPanel7 = new JPanel();
  private JLabel txtText = new JLabel();
  private JLabel txtRadio = new JLabel();
  private JLabel txtCheck = new JLabel();
  private JLabel txtCombo = new JLabel();
  private JLabel txtList = new JLabel();
  private JCheckBox jCheckBox1 = new JCheckBox();
  public int[][] item=new int[5][8];
    private JPanel pnItemPrewiev = new JPanel();
    private JLabel itemLabel = new JLabel();
    private  DefaultListModel listModel =new DefaultListModel();
    private JComboBox itemCombo = new JComboBox();
    private JTextField itemText = new JTextField();
    private JCheckBox itemCheck = new JCheckBox();
    private JScrollPane scItemPrewiev = new JScrollPane();
    private JPanel pnItemPanelPrewiev = new JPanel();
    private JTabbedPane tbpItemPanel = new JTabbedPane();
    private JPanel pnItemPanel1 = new JPanel();
    private JPanel pnItemPanel2 = new JPanel();
    private JScrollPane scpContex = new JScrollPane();
    private JTable tblContex = new JTable();
    private JPanel nestedPanel1 = new JPanel();
    private JPanel nestedPanel2 = new JPanel();
    private JComboBox itemPanelitem4 = new JComboBox();
    private JScrollPane itemPanelitem5 = new JScrollPane();
    private JLabel itemLabel3 = new JLabel();
    private JLabel itemPanellabel4 = new JLabel();
    private JLabel itemPanellabel5 = new JLabel();
    private JLabel itemPanellabel1 = new JLabel();
    private JTextField itemPanelitem1 = new JTextField();
    private JTextField itemPanelitem2 = new JTextField();
    private JLabel itemPanellabel2 = new JLabel();
    private JTextField itemPanelitem3 = new JTextField();
    private JLabel itemPanellabel3 = new JLabel();
    private JLabel itemPanellabel7 = new JLabel();
    private JTextField itemPanelitem7 = new JTextField();
    private JTextField itemPanelitem6 = new JTextField();
    private JLabel itemPanellabel6 = new JLabel();
    private JTable table = new JTable();
    private JScrollPane itemScroll = new JScrollPane();
    private JList itemList = new JList();
    private JPanel itemPanel = new JPanel();
    private JRadioButton itemRadio1 = new JRadioButton();
    private JRadioButton itemRadio2 = new JRadioButton();
    private JScrollPane scItemPanelPrew = new JScrollPane();
    private JPanel pnItemPanel4 = new JPanel();
    private JPanel pnItemPanel5 = new JPanel();
    private JPanel pnItemPanel3 = new JPanel();
    private JTextField itemPanelitem11 = new JTextField();
    private JTextField itemPanelitem12 = new JTextField();
    private JLabel itemPanellabel11 = new JLabel();
    private JLabel itemPanellabel12 = new JLabel();
    private JTextField itemPanelitem13 = new JTextField();
    private JLabel itemPanellabel14 = new JLabel();
    private JComboBox itemPanelitem14 = new JComboBox();
    private JLabel itemPanellabel15 = new JLabel();
    private JScrollPane itemPanelitem15 = new JScrollPane();
    private JList itemPanelitem16 = new JList(listModel);
    private JPanel nestedPanel3 = new JPanel();
    private JRadioButton itemPanelitem17 = new JRadioButton();
    private JPanel itemPanel2 = new JPanel();
    private JRadioButton itemPanelitem18 = new JRadioButton();
    private JLabel itemPanellabel17 = new JLabel();
    private JTextField itemPanelitem19 = new JTextField();
    private JLabel itemPanellabel19 = new JLabel();
    private JLabel itemPanellabel13 = new JLabel();
    private JList itemPanelitem10 = new JList(listModel);
    private JLabel itemPanellabel8 = new JLabel();
    private JRadioButton itemPanelitem9 = new JRadioButton();
    private JRadioButton itemPanelitem8 = new JRadioButton();
    private JButton btnApply = new JButton();
    private JButton btnButtonBorder = new JButton();
    public JLabel lbButtonBorder = new JLabel();
    private JLabel jLabel111 = new JLabel();
    private JButton btnItemTypeBorder = new JButton();
    public JLabel lbItemTypeBorder = new JLabel();
    private JRadioButton itemRadio3 = new JRadioButton();
    private JPanel jPanel5 = new JPanel();
    private JPanel jPanel6 = new JPanel();
    private JPanel jPanel8 = new JPanel();
    private JPanel jPanel9 = new JPanel();
    private JPanel jPanel10 = new JPanel();
    private JPanel jPanel11 = new JPanel();
    private JPanel jPanel12 = new JPanel();
    private JPanel jPanel13 = new JPanel();
    private JPanel jPanel14 = new JPanel();
    private JPanel itemCheckPanel = new JPanel();
    private JPanel jPanel15 = new JPanel();
    private JPanel jPanel16 = new JPanel();
    private JPanel jPanel17 = new JPanel();
    private JButton btnItemPanelBorder = new JButton();
    public JLabel lbItemPanelBorder = new JLabel();
    private JLabel jLabel124 = new JLabel();
    private JButton btnNestedPanelBorder = new JButton();
    public JLabel lbNestedPanelBorder = new JLabel();
    private JLabel jLabel126 = new JLabel();
    private JPanel jPanel18 = new JPanel();
    private JButton btnTableBorder = new JButton();
    public JLabel lbTableBorder = new JLabel();
    private JPanel jPanel19 = new JPanel();
    private JPanel jPanel20 = new JPanel();
    public JComboBox cmbOverflowSize = new JComboBox();
    private JLabel jLabel1119 = new JLabel();
    private JPanel jPanel21 = new JPanel();
    private JPanel jPanel22 = new JPanel();
    public JComboBox cmbContexHeight = new JComboBox();
    private JLabel jLabel11110 = new JLabel();
    public JRadioButton rdContexDefault = new JRadioButton();
    public JRadioButton rdContexHeight = new JRadioButton();
    private Border tableBorder;
    private Border itemPanelBorder;
    private Border nestedPanelBorder;
    private JPanel jPanel23 = new JPanel();
    private JLabel jLabel20 = new JLabel();
    private JLabel jLabel21 = new JLabel();
    public JComboBox cmbPLAlignment = new JComboBox();
    public JComboBox cmbPIAlignment = new JComboBox();
    private JPanel jPanel24 = new JPanel();
    public JComboBox cmbNPIAlignment = new JComboBox();
    public JComboBox cmbNPLAlignment = new JComboBox();
    private JLabel jLabel22 = new JLabel();
    private JLabel jLabel23 = new JLabel();
    private boolean canTable=true;
    private boolean canTableResized=true;
    private Generator gen;
    private int tableHeight;

    public UIWizard()
  {
    this(null, "", false,null,0,0,null);
  }

  public UIWizard(IISFrameMain parent, String title, boolean modal,Connection con,int m, int g, PTree tr)
  {
    super((Frame)parent, title, modal);
    try
    { 
      connection=con; 
      ID=m;
      group=g;
      tree=tr;
      gen=new Generator(connection);
      jbInit();     
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  { 
    if (ID == -1){
        template = new Template(connection, 1);
        template.name="";
    }
    else {
        template = new Template(connection, ID);
    }
    for(int i=0;i<5;i++) {
       if(i==1 || i==2) {
        item[i][5]=0;
        item[i][6]=0;
       }
       else {
        item[i][5]=2;
        item[i][6]=0;
       } 
    }
    //this.setIconImage(Toolkit.getDefaultToolkit().getImage(IISFrameMain.class.getResource("icons/IISCase.gif")));
    this.setSize(new Dimension(641, 534));
    this.getContentPane().setLayout(null);
    this.setResizable(false);
    this.setTitle("UI Model Template Wizard");
    this.setFont(new Font("SansSerif", 0, 11));
    this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
    btnBack.setMaximumSize(new Dimension(50, 30));
    btnBack.setPreferredSize(new Dimension(50, 30));
    btnBack.setText("<< Back");
    btnBack.setBounds(new Rectangle(95, 460, 75, 30));
    btnBack.setMinimumSize(new Dimension(50, 30));
    btnBack.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          apply_ActionPerformed(ae);
        }
      });
    btnBack.setFont(new Font("SansSerif", 0, 11));
    btnNext.setMaximumSize(new Dimension(50, 30));
    btnNext.setPreferredSize(new Dimension(50, 30));
    btnNext.setText("Next >>");
    btnNext.setBounds(new Rectangle(175, 460, 75, 30));
    btnNext.setMinimumSize(new Dimension(50, 30));
    btnNext.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          erase_ActionPerformed(ae);
        }
      });
    btnNext.setFont(new Font("SansSerif", 0, 11));
    btnPrewiev.setMaximumSize(new Dimension(50, 30));
    btnPrewiev.setPreferredSize(new Dimension(50, 30));
    btnPrewiev.setText("Prewiev");
    btnPrewiev.setBounds(new Rectangle(255, 460, 80, 30));
    btnPrewiev.setMinimumSize(new Dimension(50, 30));
    btnPrewiev.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          new_ActionPerformed(ae);
        }
      });
    btnPrewiev.setFont(new Font("SansSerif", 0, 11));
    btnSave.setMaximumSize(new Dimension(50, 30));
    btnSave.setPreferredSize(new Dimension(50, 30));
    btnSave.setText("Save");
    btnSave.setBounds(new Rectangle(420, 460, 75, 30));
    btnSave.setMinimumSize(new Dimension(50, 30));
    btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          save_ActionPerformed(ae);
        }
      });
    btnSave.setFont(new Font("SansSerif", 0, 11));
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Cancel");
    btnClose.setBounds(new Rectangle(500, 460, 80, 30));
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          close_ActionPerformed(ae);
        }
      });
    btnClose.setFont(new Font("SansSerif", 0, 11));
    btnHelp.setBounds(new Rectangle(585, 460, 35, 30));
    btnHelp.setFont(new Font("SansSerif", 0, 11));
    btnHelp.setIcon(imageHelp);
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    jTabbedPane1.setBounds(new Rectangle(10, 10, 610, 440));
    jTabbedPane1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jTabbedPane1_mouseClicked(e);
                }
            });
    pnlGlobal.setLayout(null);
    pnlGlobal.setVisible(false);
    pnlGlobal.setFocusable(false);
    jLabel4.setText("Label font:");
    jLabel4.setBounds(new Rectangle(15, 65, 85, 20));
    jLabel4.setFont(new Font("SansSerif", 0, 11));
 
    btnFontGlobal.setMaximumSize(new Dimension(50, 30));
    btnFontGlobal.setPreferredSize(new Dimension(50, 30));
    btnFontGlobal.setText("...");
    btnFontGlobal.setBounds(new Rectangle(235, 65, 30, 20));
    btnFontGlobal.setMinimumSize(new Dimension(50, 30));
    btnFontGlobal.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e) {
            setTextFont(lbFontGlobal);
          }
      });
    btnFontGlobal.setFont(new Font("SansSerif", 0, 11));
    jLabel6.setText("Background:");
    jLabel6.setBounds(new Rectangle(15, 85, 85, 20));
    jLabel6.setFont(new Font("SansSerif", 0, 11));
    lbBackground.setBounds(new Rectangle(105, 85, 30, 20));
    lbBackground.setFont(new Font("SansSerif", 0, 11));
    lbBackground.setBackground(SystemColor.control);
    lbBackground.setOpaque(true);
    lbBackground.setHorizontalAlignment(SwingConstants.CENTER);
    lbBackground.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    btnBackground.setMaximumSize(new Dimension(50, 30));
    btnBackground.setPreferredSize(new Dimension(50, 30));
    btnBackground.setText("...");
    btnBackground.setBounds(new Rectangle(145, 85, 30, 20));
    btnBackground.setMinimumSize(new Dimension(50, 30));

    btnBackground.setFont(new Font("SansSerif", 0, 11));
    btnBackground.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e) {
            setColor(lbBackground,e);
      }
      });
    jLabel8.setText("Foreground:");
    jLabel8.setBounds(new Rectangle(15, 120, 85, 20));
    jLabel8.setFont(new Font("SansSerif", 0, 11));
    btnForgrond.setMaximumSize(new Dimension(50, 30));
    btnForgrond.setPreferredSize(new Dimension(50, 30));
    btnForgrond.setText("...");
    btnForgrond.setBounds(new Rectangle(145, 120, 30, 20));
    btnForgrond.setMinimumSize(new Dimension(50, 30));

    btnForgrond.setFont(new Font("SansSerif", 0, 11));
    btnForgrond.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e) {
          setColor(lbForeground,e);
      }
      });
    lbForeground.setBounds(new Rectangle(105, 120, 30, 20));
    lbForeground.setFont(new Font("SansSerif", 0, 11));
    lbForeground.setBackground(SystemColor.controlText);
    lbForeground.setOpaque(true);
    lbForeground.setHorizontalAlignment(SwingConstants.CENTER);
    lbForeground.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    btnIcon.setMaximumSize(new Dimension(50, 30));
    btnIcon.setPreferredSize(new Dimension(50, 30));
    btnIcon.setText("...");
    btnIcon.setBounds(new Rectangle(145, 30, 30, 20));
    btnIcon.setMinimumSize(new Dimension(50, 30));
    btnIcon.setFont(new Font("SansSerif", 0, 11));
    btnIcon.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        btnIcon_actionPerformed(e);
                    }
      });
    lbIcon.setBounds(new Rectangle(105, 30, 25, 20));
    lbIcon.setFont(new Font("SansSerif", 0, 11));
    lbIcon.setBackground(SystemColor.controlText);
    lbIcon.setHorizontalAlignment(SwingConstants.CENTER);
    File dir1 = new File (".");
    lbIcon.setToolTipText(dir1.getCanonicalPath()+"/src/ui/icons/IIST.gif");
    lbIcon.setIcon(new ImageIcon(dir1.getCanonicalPath()+"/src/ui/icons/IIST.gif"));
    jLabel13.setText("Application icon:");
    jLabel13.setBounds(new Rectangle(15, 30, 85, 20));
    jLabel13.setFont(new Font("SansSerif", 0, 11));
    jScrollPane2.setBounds(new Rectangle(105, 65, 125, 20));
    jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    lbFontGlobal.setFont(new Font("SansSerif.plain", 0, 11));  
    lbFontGlobal.setBackground(Color.white);
    lbFontGlobal.setOpaque(true);
    lbFontGlobal.setEditorKit(new HTMLEditorKit());
    lbFontGlobal.setEditable(false);
    cmbContex.addItem("left");
    cmbContex.addItem("top");
    cmbFormTitle.addItem("left");
    cmbFormTitle.addItem("centered");
    cmbFormTitle.addItem("right");

        pnNavigationButton.add(btnPrewFirst, null);
        pnNavigationButton.add(btnPrewPrevios, null);
        pnNavigationButton.add(lbNavigation, null);
        pnNavigationButton.add(btnPrewNext, null);
        pnNavigationButton.add(btnPrewLast, null);
        jPanel7.add(txtText, null);
        jPanel7.add(txtRadio, null);
        jPanel7.add(txtCheck, null);
        jPanel7.add(txtCombo, null);
        jPanel7.add(txtList, null);
        itemScroll.getViewport().add(itemList, null);
        itemPanel.add(itemRadio3, null);
        itemPanel.add(itemRadio2, null);
        itemPanel.add(itemRadio1, null);
        itemCheckPanel.add(itemCheck, null);
        pnItemPrewiev.add(itemCheckPanel, null);
        pnItemPrewiev.add(itemPanel, null);
        pnItemPrewiev.add(itemScroll, null);
        pnItemPrewiev.add(itemText, null);
        pnItemPrewiev.add(itemCombo, null);
        pnItemPrewiev.add(itemLabel, null);
        scItemPrewiev.getViewport().add(pnItemPrewiev, null);
        jPanel14.add(cmbTitleAlign, null);
        jPanel14.add(cmbTitlePosition, null);
        jPanel14.add(cmbTitleOffset, null);
        jPanel14.add(chBold, null);
        jPanel14.add(chItalic, null);
        jPanel14.add(jLabel2, null);
        jPanel14.add(jLabel115, null);
        jPanel14.add(jLabel114, null);
        jPanel14.add(jLabel112, null);
        jPanel14.add(btnItemTypeBorder, null);
        jPanel14.add(lbItemTypeBorder, null);
        jPanel14.add(jLabel111, null);
        jPanel4.add(jPanel14, null);
        jPanel4.add(scItemPrewiev, null);
        jPanel4.add(jPanel7, null);
        cmbTitlePosition.addItem("left");
        cmbTitlePosition.addItem("top");
        cmbTitlePosition.addItem("right");
        cmbTitlePosition.addItem("bottom");
        cmbTitleAlign.addItem("left");
        cmbTitleAlign.addItem("center");
        cmbTitleAlign.addItem("right");
        String[] columnNames = 
        { "Column 1", "Column 2", "Column 3", "Column 4", "Column 5" };
        String[] columnNames1 = { "Contex ID" };
        Object[][] data = 
        { { "Value 11", "Value 12", "Value 13", "Value 14", "Value 15" }, 
          { "Value 21", "Value 22", "Value 23", "Value 24", "Value 25" }, 
          { "Value 31", "Value 32", "Value 33", "Value 34", "Value 35" }, 
          { "Value 41", "Value 42", "Value 43", "Value 44", "Value 45" }, 
          { "Value 51", "Value 52", "Value 53", "Value 54", "Value 55" }, 
          { "Value 61", "Value 62", "Value 63", "Value 64", "Value 65" }, 
          { "Value 71", "Value 72", "Value 73", "Value 74", "Value 75" }, 
          { "Value 81", "Value 82", "Value 83", "Value 84", "Value 85" }, 
          { "Value 91", "Value 92", "Value 93", "Value 94", "Value 95" }, 
          { "Value 101", "Value 102", "Value 103", "Value 104", "Value 105" }, 
          { "Value 111", "Value 112", "Value 113", "Value 114", "Value 115" }, 
          { "Value 121", "Value 122", "Value 123", "Value 124", "Value 125" }, 
          { "Value 131", "Value 132", "Value 133", "Value 134", "Value 135" }, 
          { "Value 141", "Value 142", "Value 143", "Value 144", "Value 145" }, 
          { "Value 151", "Value 152", "Value 153", "Value 154", "Value 155" }, 
          { "Value 161", "Value 162", "Value 163", "Value 164", "Value 165" }, 
          { "Value 171", "Value 172", "Value 173", "Value 174", "Value 175" }, 
          { "Value 181", "Value 182", "Value 183", "Value 184", "Value 185" }, 
          { "Value 191", "Value 192", "Value 193", "Value 194", "Value 195" }, 
          { "Value 201", "Value 202", "Value 203", "Value 204", 
            "Value 205" } };
        Object[][] data1 = 
        { { "1001" }, { "1002" }, { "1003" }, { "1004" }, { "1005" }, 
          { "1006" }, { "1007" }, { "1008" }, { "1009" }, { "1010" }, 
          { "1011" }, { "1012" }, { "1013" }, { "1014" }, { "1015" }, 
          { "1016" }, { "1017" }, { "1018" }, { "1019" }, { "1020" } };
        table.setModel(new SpecialTableModel());
        tblContex.setModel(new SpecialTableModel());
        table = new JTable(data, columnNames);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
            table.getColumnModel().getColumn(i).setHeaderRenderer(new MyHeaderRenderer(0)); 
            table.getColumnModel().getColumn(i).setCellEditor(new MyTableCellEditor());
        }
        tblContex.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblContex.setRowSelectionAllowed(true);
        tblContex.setBackground(Color.white);
        tblContex.getTableHeader().setReorderingAllowed(false);
        tblContex.setAutoscrolls(true);
        tblContex.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblContex = new JTable(data1, columnNames1);
        tblContex.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblContex.setRowSelectionAllowed(true);
        tblContex.setBackground(Color.white);
        tblContex.getTableHeader().setReorderingAllowed(false);
        tblContex.setAutoscrolls(true);
        tblContex.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblContex.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer1);
        tblContex.getColumnModel().getColumn(0).setHeaderRenderer(new MyHeaderRenderer(1));
        table.getColumnModel().getColumn(0).setCellEditor(new MyTableCellEditor());
        pnItemPanel1.add(itemPanellabel3, null);
        pnItemPanel1.add(itemPanelitem3, null);
        pnItemPanel1.add(itemPanellabel2, null);
        pnItemPanel1.add(itemPanelitem2, null);
        pnItemPanel1.add(itemPanelitem1, null);
        pnItemPanel1.add(itemPanellabel1, null);
        pnItemPanel1.add(itemPanellabel5, null);
        pnItemPanel1.add(itemPanellabel4, null);
        pnItemPanel1.add(itemLabel3, null);
        itemPanelitem5.getViewport().add(itemPanelitem10, null);
        pnItemPanel1.add(itemPanelitem5, null);
        pnItemPanel1.add(itemPanelitem4, null);
        nestedPanel2.add(itemPanelitem8, null);
        nestedPanel2.add(itemPanelitem9, null);
        nestedPanel2.add(itemPanellabel8, null);
        nestedPanel1.add(itemPanellabel6, null);
        nestedPanel1.add(itemPanelitem6, null);
        nestedPanel1.add(itemPanelitem7, null);
        nestedPanel1.add(itemPanellabel7, null);
        pnItemPanel2.add(nestedPanel2, null);
        pnItemPanel2.add(nestedPanel1, null);
        tbpItemPanel.addTab("Item Panel 1", pnItemPanel1);
        tbpItemPanel.addTab("Item Panel 2", pnItemPanel2);
        pnItemPanel3.add(itemPanellabel13, null);
        pnItemPanel3.add(itemPanelitem13, null);
        pnItemPanel3.add(itemPanellabel12, null);
        pnItemPanel3.add(itemPanellabel11, null);
        pnItemPanel3.add(itemPanelitem12, null);
        pnItemPanel3.add(itemPanelitem11, null);
        itemPanelitem15.getViewport().add(itemPanelitem16, null);
        pnItemPanel4.add(itemPanelitem15, null);
        pnItemPanel4.add(itemPanellabel15, null);
        pnItemPanel4.add(itemPanelitem14, null);
        pnItemPanel4.add(itemPanellabel14, null);
        pnItemPanelPrewiev.add(pnItemPanel4, null);
        scpContex.getViewport().add(tblContex, null);
        pnItemPanelPrewiev.add(scpContex, null);
        pnItemPanelPrewiev.add(tbpItemPanel, null);
        pnItemPanelPrewiev.add(pnItemPanel3, null);
        pnItemPanelPrewiev.add(pnItemPanel5, null);
        scItemPanelPrew.getViewport().add(pnItemPanelPrewiev, null);
        jPanel15.add(lbBackgroundInput, null);
        jPanel15.add(jLabel119, null);
        jPanel15.add(jLabel1110, null);
        jPanel15.add(jLabel120, null);
        jPanel15.add(jLabel121, null);
        jPanel15.add(lbBackgroundItemPanel, null);
        jPanel15.add(lbForegroundItemPanel, null);
        jPanel15.add(lbForegroundInput, null);
        jPanel15.add(btnForgroundInput, null);
        jPanel15.add(btnBackgroundInput, null);
        jPanel15.add(btnForgroundItemPanel, null);
        jPanel15.add(btnBackgroundItemPanel, null);
        jPanel16.add(jLabel1114, null);
        jPanel16.add(cmbOrientation, null);
        jPanel16.add(rdNormal, null);
        jPanel16.add(rdTabbed, null);
        jPanel16.add(cmbItemSpacing, null);
        jPanel16.add(jLabel118, null);
        jPanel16.add(cmbItemPanelSpacing, null);
        jPanel16.add(jLabel1113, null);
        jPanel17.add(jLabel126, null);
        jPanel17.add(lbNestedPanelBorder, null);
        jPanel17.add(btnNestedPanelBorder, null);
        jPanel17.add(jLabel124, null);
        jPanel17.add(lbItemPanelBorder, null);
        jPanel17.add(btnItemPanelBorder, null);
        jPanel22.add(rdContexHeight, null);
        jPanel22.add(rdContexDefault, null);
        jPanel22.add(jLabel11110, null);
        jPanel22.add(cmbContexHeight, null);
        jPanel22.add(cmbContex, null);
        jPanel22.add(chContex, null);
        jPanel22.add(jLabel3, null);
        jPanel23.add(cmbPIAlignment, null);
        jPanel23.add(cmbPLAlignment, null);
        jPanel23.add(jLabel21, null);
        jPanel23.add(jLabel20, null);
        jPanel24.add(jLabel23, null);
        jPanel24.add(jLabel22, null);
        jPanel24.add(cmbNPLAlignment, null);
        jPanel24.add(cmbNPIAlignment, null);
        jPanel2.add(jPanel24, null);
        jPanel2.add(jPanel23, null);
        jPanel2.add(jPanel22, null);
        jPanel2.add(jPanel17, null);
        jPanel2.add(jPanel16, null);
        jPanel2.add(jPanel15, null);
        jPanel2.add(scItemPanelPrew, null);
        pnItemPanel5.add(itemPanellabel19, null);
        pnItemPanel5.add(itemPanelitem19, null);
        pnItemPanel5.add(itemPanel2, null);
        pnItemPanel5.add(nestedPanel3, null);
        nestedPanel3.add(itemPanellabel17, null);
        nestedPanel3.add(itemPanelitem18, null);
        nestedPanel3.add(itemPanelitem17, null);
        cmbOrientation.addItem("1 in line");
        cmbOrientation.addItem("2 in line");
        cmbOrientation.addItem("3 in line");
        tableScrollPane.getViewport().add(table, null);
        pnOverflow.getViewport().add(lbOverflow, null);
        pnTablePrewiev=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,tableScrollPane,pnOverflow);
        scTablePrewiev.getViewport().add(pnTablePrewiev, null);
        jPanel18.add(lbBackgroundCell, null);
        jPanel18.add(jLabel1111, null);
        jPanel18.add(jLabel1112, null);
        jPanel18.add(lbForegroundCell, null);
        jPanel18.add(btnForgroundCell, null);
        jPanel18.add(btnBackgroundCell, null);
        jPanel18.add(btnForgroundTablePanel, null);
        jPanel18.add(btnBackgroundTablePanel, null);
        jPanel18.add(lbBackgroundTablePanel, null);
        jPanel18.add(lbForegroundTablePanel, null);
        jPanel18.add(jLabel122, null);
        jPanel18.add(jLabel123, null);
        jPanel19.add(cmbRowHeight, null);
        jPanel19.add(cmbColumnWidth, null);
        jPanel19.add(cmbVisibleRows, null);
        jPanel19.add(jLabel1118, null);
        jPanel19.add(jLabel1116, null);
        jPanel19.add(jLabel1115, null);
        jPanel20.add(jLabel1119, null);
        jPanel20.add(cmbOverflowSize, null);
        jPanel20.add(cmbOverflow, null);
        jPanel20.add(chOverflow, null);
        jPanel20.add(jLabel1117, null);
        jPanel21.add(lbTableBorder, null);
        jPanel21.add(btnTableBorder, null);
        jPanel1.add(jPanel21, null);
        jPanel1.add(jPanel20, null);
        jPanel1.add(jPanel19, null);
        jPanel1.add(jPanel18, null);
        jPanel1.add(scTablePrewiev, null);
        cmbOverflow.addItem("right");
        cmbOverflow.addItem("bottom");
        bgrp1.add(rdFixedSize);
        bgrp1.add(rdFullScreen);
        bgrp2.add(rdLeftonTop);
        bgrp2.add(rdCenter);
        bgrp2.add(rdCustom);
        bgrp5.add(rdTextual);
        bgrp5.add(rdGraphical);
        bgrp6.add(rdTabbed);
        bgrp6.add(rdNormal);
        bgrp7.add(itemRadio1);
        bgrp7.add(itemRadio2);
        bgrp7.add(itemRadio3);
        bgrp8.add(rdTabbed);
        bgrp8.add(rdNormal);
        bgrp9.add(rdTabbed);
        bgrp9.add(rdNormal);
        bgrp10.add(rdContexDefault);
        bgrp10.add(rdContexHeight);
        bgrp11.add(itemPanelitem8);
        bgrp11.add(itemPanelitem9);
        bgrp11.add(itemPanelitem17);
        bgrp11.add(itemPanelitem18);
        jPanel5.add(btnIcon, null);
        jPanel5.add(btnDesktop, null);
        jPanel5.add(btnForgrond, null);
        jPanel5.add(lbForeground, null);
        jPanel5.add(lbDesktop, null);
        jPanel5.add(jLabel10, null);
        jPanel5.add(jLabel8, null);
        jPanel5.add(jLabel6, null);
        jPanel5.add(lbBackground, null);
        jPanel5.add(btnBackground, null);
        jPanel5.add(lbIcon, null);
        jPanel5.add(jLabel13, null);
        jPanel6.add(btnInputFontGlobal, null);
        jPanel6.add(jLabel5, null);
        jScrollPane4.getViewport().add(lbInputFontGlobal, null);
        jPanel6.add(jScrollPane4, null);
        jPanel6.add(btnFontGlobal, null);
        jPanel6.add(jLabel4, null);
        jScrollPane2.getViewport().add(lbFontGlobal, null);
        jPanel6.add(jScrollPane2, null);
        jPanel8.add(rdFixedSize, null);
        jPanel8.add(rdResizable, null);
        jPanel8.add(jLabel17, null);
        jPanel8.add(jLabel125, null);
        jPanel8.add(rdFullScreen, null);
        jPanel8.add(txtY, null);
        jPanel8.add(jLabel1, null);
        jPanel8.add(txtX, null);
        jPanel8.add(jLabel18, null);
        jPanel8.add(rdCenter, null);
        jPanel8.add(rdLeftonTop, null);
        jPanel8.add(lbY, null);
        jPanel8.add(lbX, null);
        jPanel8.add(rdCustom, null);
        jPanel8.add(lbScreen, null);
        jPanel8.add(jLabel9, null);
        pnlGlobal.add(jPanel8, null);
        pnlGlobal.add(jPanel6, null);
        pnlGlobal.add(jPanel5, null);
        pnlGlobal.add(txtName, null);
        pnlGlobal.add(jLabel7, null);
        jTabbedPane1.addTab("Global Options", pnlGlobal);
        for (int i = 1; i <= 100; i++)
            cmbItemSpacing.addItem("" + i);
        for (int i = 1; i <= 100; i++)
            cmbTitleOffset.addItem("" + i);
        for (int i = 0; i <= 100; i++)
            cmbItemPanelSpacing.addItem("" + i * 5);
        for (int i = 1; i <= 300; i++)
            cmbOverflowSize.addItem("" + i);
        for (int i = 1; i <= 100; i++)
            cmbRowHeight.addItem("" + i);
        for (int i = 1; i <= 1000; i++)
            cmbColumnWidth.addItem("" + i);
        for (int i = 1; i <= 20; i++)
            cmbVisibleRows.addItem("" + i);
        for (int i = 1; i <= 1000; i++)
            cmbContexHeight.addItem("" + i);
        cmbPIAlignment.addItem("start");
        cmbPIAlignment.addItem("end");
        cmbPIAlignment.addItem("none");      
        cmbPLAlignment.addItem("start");
        cmbPLAlignment.addItem("end");
        cmbPLAlignment.addItem("none");       
        cmbNPIAlignment.addItem("start");
        cmbNPIAlignment.addItem("end");
        cmbNPIAlignment.addItem("none");      
        cmbNPLAlignment.addItem("start");
        cmbNPLAlignment.addItem("end");
        cmbNPLAlignment.addItem("none");
        cmbVisibleRows.setSelectedItem("" + 9);
        cmbRowHeight.setSelectedItem("" + 15);
        cmbColumnWidth.setSelectedItem("" + 106);
        cmbItemSpacing.setSelectedItem("" + 5);
        cmbContexHeight.setSelectedItem("" + 100);
        jPanel9.add(rdMenuCall, null);
        jPanel9.add(rdButtonCall, null);
        jPanel9.add(rdKeyCall, null);
        jPanel10.add(lbBackgroundForm, null);
        jPanel10.add(jLabel14, null);
        jPanel10.add(btnBackgroundForm, null);
        jPanel10.add(btnForgroundForm, null);
        jPanel10.add(lbForegroundForm, null);
        jPanel10.add(jLabel12, null);
        jPanel11.add(btnFontTitle, null);
        jPanel11.add(jLabel11, null);
        jScrollPane3.getViewport().add(lbFontTitle, null);
        jPanel11.add(jScrollPane3, null);
        jPanel11.add(cmbFormTitle, null);
        jPanel11.add(chTitle, null);
        jPanel11.add(lbTitle, null);
        pnlForm.add(jPanel11, null);
        pnlForm.add(jPanel10, null);
        pnlForm.add(jPanel9, null);
        pnlForm.add(lbContent, null);
        pnlForm.add(lbForm, null);
        pnlForm.add(lbIcon1, null);
        jTabbedPane1.addTab("Screen Form", pnlForm);
        jTabbedPane1.addTab("Table Panel", jPanel1);
        jTabbedPane1.addTab("Item Panel", jPanel2);

        pnEditingButton.add(btnPrewNew, null);
        pnEditingButton.add(btnPrewDuplicate, null);
        pnEditingButton.add(btnPrewDelete, null);
        pnEditingButton.add(btnPrewDeleteAll, null);
        pnEditingButton.add(btnPrewApply, null);
        pnEditingButton.add(btnPrewOK, null);
        pnEditingButton.add(btnPrewCancel, null);
        scButtonPrewiev.add(pnNavigationButton, null);
        scButtonPrewiev.add(pnEditingButton, null);
        jPanel12.add(lbBackgroundButton, null);
        jPanel12.add(jLabel19, null);
        jPanel12.add(jLabel110, null);
        jPanel12.add(btnBackgroundButton, null);
        jPanel12.add(btnForgroundButton, null);
        jPanel12.add(lbForegroundButton, null);
        jPanel13.add(rdGraphical, null);
        jPanel13.add(rdTextual, null);
        jPanel13.add(jLabel15, null);
        jPanel13.add(btnButtonBorder, null);
        jPanel13.add(lbButtonBorder, null);
        jPanel13.add(jLabel16, null);
        jPanel3.add(jPanel13, null);
        jPanel3.add(jPanel12, null);
        jPanel3.add(scButtonPrewiev, null);
        jTabbedPane1.addTab("Item Type", jPanel4);
        jTabbedPane1.addTab("Button Panel", jPanel3);
        JTextField txt = new JTextField();
        jLabel125.setFont(new Font("SansSerif", 0, 11));
        jLabel125.setBounds(new Rectangle(15, 60, 80, 20));
        jLabel125.setText("Screen size:");
        jLabel9.setIcon(imageScreen);
        jLabel9.setBounds(new Rectangle(80, 190, 200, 140));
        rdResizable.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change(e);
                    }
                });
        rdResizable.setBounds(new Rectangle(87, 35, 90, 15));
        rdResizable.setText("resizable");
        jLabel118.setFont(new Font("SansSerif", 0, 11));
        jLabel118.setBounds(new Rectangle(10, 95, 75, 20));
        jLabel118.setText("Item spacing:");
        cmbItemSpacing.setBounds(new Rectangle(90, 95, 50, 20));
        cmbItemSpacing.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_item_panel(e);
                    }
                });
        jLabel1118.setText("No. of visible rows:");
        jLabel1118.setBounds(new Rectangle(10, 90, 115, 20));
        jLabel1118.setFont(new Font("SansSerif", 0, 11));
        cmbVisibleRows.setBounds(new Rectangle(130, 90, 50, 20));
        cmbVisibleRows.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_table(e);
                    }
                });
        rdKeyCall.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        subFormCall(e);
                    }
                });
        rdKeyCall.setBounds(new Rectangle(20, 85, 90, 15));
        rdKeyCall.setText("press key");
        rdButtonCall.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        subFormCall(e);
                    }
                });
        rdButtonCall.setBounds(new Rectangle(20, 60, 90, 15));
        rdButtonCall.setText("button click");
        rdMenuCall.setText("from menu");
        rdMenuCall.setBounds(new Rectangle(20, 35, 90, 15));
        rdMenuCall.setSelected(true);
        rdMenuCall.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        subFormCall(e);
                    }
                });
        lbScreen.addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseDragged(MouseEvent e) {
                        lbForeground2_mouseDragged(e);
                    }

                    public void mouseMoved(MouseEvent e) {
                        lbScreen_mouseMoved(e);
                    }
                });
        lbScreen.setBounds(new Rectangle(80, 140, 167, 104));
        lbScreen.setFont(new Font("SansSerif", 1, 11));
        lbScreen.setBackground(SystemColor.desktop);
        lbScreen.setOpaque(true);
        lbScreen.setHorizontalAlignment(SwingConstants.CENTER);
        lbScreen.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lbScreen.setForeground(Color.white);
        lbScreen.setBounds(new Rectangle(95, 205, 167, 104));
        rdCustom.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        rdNo_propertyChange(e);
                    }
                });
        rdCustom.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rdNo_actionPerformed(e);
                    }
                });
        rdCustom.setBounds(new Rectangle(85, 165, 60, 20));
        rdCustom.setText("custom");
        rdCenter.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        rdNo_propertyChange(e);
                    }
                });
        rdCenter.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rdNo_actionPerformed(e);
                    }
                });
        rdCenter.setSelected(true);
        rdCenter.setBounds(new Rectangle(85, 115, 60, 20));
        rdCenter.setText("center");
        rdLeftonTop.setBounds(new Rectangle(85, 140, 85, 20));
        rdLeftonTop.setText("left on top");
        jLabel18.setFont(new Font("SansSerif", 0, 11));
        jLabel18.setBounds(new Rectangle(15, 110, 80, 20));
        jLabel18.setText("Position:");
        jLabel1.setBounds(new Rectangle(205, 85, 15, 20));
        jLabel1.setText("X");
        txtY.setBounds(new Rectangle(215, 85, 40, 20));
        txtX.setBounds(new Rectangle(160, 85, 40, 20));
        rdFullScreen.setText("full screen");
        rdFullScreen.setBounds(new Rectangle(85, 60, 85, 20));
        rdFullScreen.setSelected(true);
        rdFixedSize.setText("fixed size:");
        rdFixedSize.setBounds(new Rectangle(85, 85, 75, 20));
        jLabel17.setText("Screen:");
        jLabel17.setBounds(new Rectangle(15, 30, 80, 20));
        jLabel17.setFont(new Font("SansSerif", 0, 11));
        txtName.setFont(new Font("SansSerif", 0, 11));
        txtName.setBounds(new Rectangle(95, 20, 205, 20));
        lbDesktop.setForeground(SystemColor.desktop);
        jLabel10.setFont(new Font("SansSerif", 0, 11));
        jLabel10.setBounds(new Rectangle(15, 155, 85, 20));
        jLabel10.setText("Desktop:");
        lbDesktop.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lbDesktop.setHorizontalAlignment(SwingConstants.CENTER);
        lbDesktop.setOpaque(true);
        lbDesktop.setBackground(SystemColor.desktop);
        lbDesktop.setFont(new Font("SansSerif", 0, 11));
        lbDesktop.setBounds(new Rectangle(105, 155, 30, 20));
        btnDesktop.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setColor(lbDesktop,e);
                    }
                });
        btnDesktop.setFont(new Font("SansSerif", 0, 11));
        btnDesktop.setMinimumSize(new Dimension(50, 30));
        btnDesktop.setBounds(new Rectangle(145, 155, 30, 20));
        btnDesktop.setText("...");
        btnDesktop.setPreferredSize(new Dimension(50, 30));
        btnDesktop.setMaximumSize(new Dimension(50, 30));
        jLabel7.setFont(new Font("SansSerif", 0, 11));
        jLabel7.setBounds(new Rectangle(10, 20, 85, 20));
        jLabel7.setText("Template name:");
        lbInputFontGlobal.setOpaque(true);
        lbInputFontGlobal.setBackground(Color.white);
        lbInputFontGlobal.setText("SansSerif, 11");
        lbInputFontGlobal.setFont(new Font("SansSerif.plain", 0, 11));
        lbInputFontGlobal.setEditorKit(new HTMLEditorKit());
        lbInputFontGlobal.setEditable(false);
        jScrollPane4.setBounds(new Rectangle(105, 30, 125, 20));
        jScrollPane4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        btnInputFontGlobal.setFont(new Font("SansSerif", 0, 11));
        btnInputFontGlobal.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setTextFont(lbInputFontGlobal);
                    }
                });
        btnInputFontGlobal.setMinimumSize(new Dimension(50, 30));
        btnInputFontGlobal.setBounds(new Rectangle(235, 30, 30, 20));
        btnInputFontGlobal.setText("...");
        btnInputFontGlobal.setPreferredSize(new Dimension(50, 30));
        btnInputFontGlobal.setMaximumSize(new Dimension(50, 30));
        jLabel5.setFont(new Font("SansSerif", 0, 11));
        jLabel5.setBounds(new Rectangle(15, 30, 85, 20));
        jLabel5.setText("Item font:");
        txt.setBackground(Color.white);
        jPanel24.setBounds(new Rectangle(430, 140, 160, 70));
        jPanel24.setBorder(BorderFactory.createTitledBorder("Nested Panel"));
        jPanel24.setLayout(null);
        cmbNPIAlignment.setBounds(new Rectangle(80, 40, 70, 20));
        cmbNPIAlignment.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_item_panel(e);
                    }
                });
        cmbNPLAlignment.setBounds(new Rectangle(80, 15, 70, 20));
        cmbNPLAlignment.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_item_panel(e);
                    }
                });
        jLabel22.setText("Input field:");
        jLabel22.setBounds(new Rectangle(10, 40, 60, 20));
        jLabel23.setText("Label:");
        jLabel23.setBounds(new Rectangle(10, 15, 35, 20));
        cmbPIAlignment.setBounds(new Rectangle(100, 50, 70, 20));
        cmbPIAlignment.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_item_panel(e);
                    }
                });
        jPanel23.setBounds(new Rectangle(235, 130, 360, 85));
        jPanel23.setBorder(BorderFactory.createTitledBorder("Panel Alignment"));
        jPanel23.setLayout(null);
        jLabel20.setText("Label:");
        jLabel20.setBounds(new Rectangle(10, 25, 35, 20));
        jLabel21.setText("Input field:");
        jLabel21.setBounds(new Rectangle(10, 50, 60, 20));
        cmbPLAlignment.setBounds(new Rectangle(100, 25, 70, 20));
        cmbPLAlignment.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_item_panel(e);
                    }
                });
        rdContexHeight.setBounds(new Rectangle(145, 50, 25, 20));
        rdContexHeight.setOpaque(false);
        cmbContexHeight.setSelectedItem("" + 50);
        cmbContexHeight.setBounds(new Rectangle(165, 50, 50, 20));
        cmbContexHeight.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_item_panel(e);
                    }
                });
        jLabel11110.setFont(new Font("SansSerif", 0, 11));
        jLabel11110.setBounds(new Rectangle(10, 50, 35, 20));
        jLabel11110.setText("Height:");
        rdContexDefault.setBounds(new Rectangle(90, 50, 60, 20));
        rdContexDefault.setText("default");
        rdContexDefault.setSelected(true);
        rdContexDefault.setOpaque(false);
        rdContexDefault.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if (rdNormal.isSelected())
                        {
                            cmbOrientation.setEnabled(true);
                            chContex.setEnabled(false);
                            cmbContex.setEnabled(false);
                        }
                        else
                        {
                            cmbOrientation.setEnabled(false);
                            chContex.setEnabled(true);
                            cmbContex.setEnabled(true);
                        }
                        change_item_panel(e);
                    }
                });
        rdContexDefault.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rdNo_actionPerformed(e);
                    }
                });
        jPanel22.setBounds(new Rectangle(5, 130, 225, 85));
        jPanel22.setBorder(BorderFactory.createTitledBorder("Contex Table"));
        jPanel22.setLayout(null);
        jPanel21.setBounds(new Rectangle(215, 90, 180, 70));
        jPanel21.setBorder(BorderFactory.createTitledBorder("Borders"));
        jPanel21.setLayout(null);
        cmbOverflowSize.setSelectedItem("" + 50);
        cmbOverflowSize.setBounds(new Rectangle(70, 55, 50, 20));
        cmbOverflowSize.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_table(e);
                    }
                });
        jLabel1119.setFont(new Font("SansSerif", 0, 11));
        jLabel1119.setBounds(new Rectangle(10, 55, 45, 20));
        jLabel1119.setText("Size:");
        jPanel19.setBounds(new Rectangle(15, 5, 195, 155));
        jPanel19.setBorder(BorderFactory.createTitledBorder("Table"));
        jPanel19.setLayout(null);
        jPanel20.setBounds(new Rectangle(215, 5, 180, 85));
        jPanel20.setBorder(BorderFactory.createTitledBorder("Overflow"));
        jPanel20.setLayout(null);
        btnTableBorder.setFont(new Font("SansSerif", 0, 11));
        btnTableBorder.setMinimumSize(new Dimension(50, 30));
        btnTableBorder.setBounds(new Rectangle(110, 20, 30, 20));
        btnTableBorder.setText("...");
        btnTableBorder.setPreferredSize(new Dimension(50, 30));
        btnTableBorder.setMaximumSize(new Dimension(50, 30));
        btnTableBorder.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                setBorderStyle(lbTableBorder, false);
             }
        });
        lbTableBorder.setBorder(BorderFactory.createEmptyBorder());
        lbTableBorder.setHorizontalAlignment(SwingConstants.CENTER);
        lbTableBorder.setBackground(SystemColor.control);
        lbTableBorder.setFont(new Font("SansSerif", 0, 11));
        lbTableBorder.setBounds(new Rectangle(10, 20, 95, 40));
        lbTableBorder.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_table(e);
                    }
                });
        jPanel18.setBounds(new Rectangle(400, 5, 200, 155));
        jPanel18.setBorder(BorderFactory.createTitledBorder("Colors"));
        jPanel18.setOpaque(false);
        jPanel18.setLayout(null);
        btnItemPanelBorder.setFont(new Font("SansSerif", 0, 11));
        btnItemPanelBorder.setMinimumSize(new Dimension(50, 30));
        btnItemPanelBorder.setBounds(new Rectangle(110, 30, 30, 20));
        btnItemPanelBorder.setText("...");
        btnItemPanelBorder.setPreferredSize(new Dimension(50, 30));
        btnItemPanelBorder.setMaximumSize(new Dimension(50, 30));
        btnItemPanelBorder.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                setBorderStyle(lbItemPanelBorder,true);
             }
        });
        lbItemPanelBorder.setBorder(BorderFactory.createEtchedBorder());
        lbItemPanelBorder.setHorizontalAlignment(SwingConstants.CENTER);
        lbItemPanelBorder.setBackground(SystemColor.control);
        lbItemPanelBorder.setFont(new Font("SansSerif", 0, 11));
        lbItemPanelBorder.setBounds(new Rectangle(10, 30, 95, 35));
        lbItemPanelBorder.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_item_panel(e);
                    }
                });
        jLabel124.setText("Item panel:");
        jLabel124.setBounds(new Rectangle(10, 10, 120, 20));
        jLabel124.setFont(new Font("SansSerif", 0, 11));
        btnNestedPanelBorder.setFont(new Font("SansSerif", 0, 11));
        btnNestedPanelBorder.setMinimumSize(new Dimension(50, 30));
        btnNestedPanelBorder.setBounds(new Rectangle(110, 85, 30, 20));
        btnNestedPanelBorder.setText("...");
        btnNestedPanelBorder.setPreferredSize(new Dimension(50, 30));
        btnNestedPanelBorder.setMaximumSize(new Dimension(50, 30));
        btnNestedPanelBorder.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                setBorderStyle(lbNestedPanelBorder,true);
             }
        });
        lbNestedPanelBorder.setBorder(BorderFactory.createTitledBorder("Title"));
        lbNestedPanelBorder.setHorizontalAlignment(SwingConstants.CENTER);
        lbNestedPanelBorder.setBackground(SystemColor.control);
        lbNestedPanelBorder.setFont(new Font("SansSerif", 0, 11));
        lbNestedPanelBorder.setBounds(new Rectangle(10, 85, 95, 35));
        lbNestedPanelBorder.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_item_panel(e);
                    }
                });
        jLabel126.setText("Nested panel:");
        jLabel126.setBounds(new Rectangle(10, 65, 120, 20));
        jLabel126.setFont(new Font("SansSerif", 0, 11));
        jPanel17.setBounds(new Rectangle(430, 0, 165, 130));
        jPanel17.setBorder(BorderFactory.createTitledBorder("Borders"));
        jPanel17.setOpaque(false);
        jPanel17.setLayout(null);
        jPanel15.setBounds(new Rectangle(235, 0, 190, 130));
        jPanel15.setBorder(BorderFactory.createTitledBorder("Colors"));
        jPanel15.setOpaque(false);
        jPanel15.setLayout(null);
        jPanel16.setBounds(new Rectangle(5, 0, 225, 130));
        jPanel16.setBorder(BorderFactory.createTitledBorder("Item Panel"));
        jPanel16.setOpaque(false);
        jPanel16.setLayout(null);
        itemCheckPanel.setBounds(new Rectangle(45, 180, 125, 20));
        itemCheckPanel.setLayout(null);
        itemCheckPanel.setSize(new Dimension(125, 21));
        itemCheckPanel.setOpaque(false);
        jPanel14.setBounds(new Rectangle(15, 185, 275, 210));
        jPanel14.setOpaque(false);
        jPanel14.setBorder(BorderFactory.createTitledBorder("Item type style"));
        jPanel14.setLayout(null);
        jPanel13.setBounds(new Rectangle(10, 15, 285, 145));
        jPanel13.setBorder(BorderFactory.createTitledBorder("Button style"));
        jPanel13.setOpaque(false);
        jPanel13.setLayout(null);
        jPanel12.setBounds(new Rectangle(305, 15, 285, 145));
        jPanel12.setOpaque(false);
        jPanel12.setBorder(BorderFactory.createTitledBorder("Colors"));
        jPanel12.setLayout(null);
        jPanel10.setBounds(new Rectangle(10, 160, 265, 105));
        jPanel10.setOpaque(false);
        jPanel10.setBorder(BorderFactory.createTitledBorder("Colors"));
        jPanel10.setLayout(null);
        jPanel11.setBounds(new Rectangle(10, 25, 265, 130));
        jPanel11.setOpaque(false);
        jPanel11.setBorder(BorderFactory.createTitledBorder("Form title"));
        jPanel11.setLayout(null);
        jPanel9.setBounds(new Rectangle(10, 275, 265, 120));
        jPanel9.setOpaque(false);
        jPanel9.setBorder(BorderFactory.createTitledBorder("Subform call mode"));
        jPanel9.setLayout(null);
        jPanel5.setBounds(new Rectangle(310, 185, 280, 215));
        jPanel5.setBorder(BorderFactory.createTitledBorder("Colors & Icon"));
        jPanel5.setLayout(null);
        jPanel6.setBounds(new Rectangle(310, 55, 280, 125));
        jPanel6.setBorder(BorderFactory.createTitledBorder("Fonts"));
        jPanel6.setLayout(null);
        jPanel8.setBounds(new Rectangle(10, 55, 295, 345));
        jPanel8.setLayout(null);
        jPanel8.setBorder(BorderFactory.createTitledBorder("Application Window"));
        itemRadio3.setText("Item Value 3");
        itemRadio3.setBounds(new Rectangle(0, 50, 115, 25));
        itemRadio3.setOpaque(false);
        jLabel111.setFont(new Font("SansSerif", 0, 11));
        jLabel111.setBounds(new Rectangle(15, 150, 70, 20));
        jLabel111.setText("Border style:");
        btnItemTypeBorder.setFont(new Font("SansSerif", 0, 11));
        btnItemTypeBorder.setMinimumSize(new Dimension(50, 30));
        btnItemTypeBorder.setBounds(new Rectangle(215, 150, 30, 20));
        btnItemTypeBorder.setText("...");
        btnItemTypeBorder.setPreferredSize(new Dimension(50, 30));
        btnItemTypeBorder.setMaximumSize(new Dimension(50, 30));
        btnItemTypeBorder.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                setBorderStyle(lbItemTypeBorder,false);
             }
        });
        lbItemTypeBorder.setBorder(BorderFactory.createLineBorder(Color.black,1));
        lbItemTypeBorder.setHorizontalAlignment(SwingConstants.CENTER);
        lbItemTypeBorder.setBackground(SystemColor.control);
        lbItemTypeBorder.setFont(new Font("SansSerif", 0, 11));
        lbItemTypeBorder.setBounds(new Rectangle(115, 150, 95, 40));
        lbItemTypeBorder.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_itemType(e);
                    }
                });
        btnButtonBorder.setFont(new Font("SansSerif", 0, 11));
        btnButtonBorder.setMinimumSize(new Dimension(50, 30));
        btnButtonBorder.setBounds(new Rectangle(215, 85, 30, 20));
        btnButtonBorder.setText("...");
        btnButtonBorder.setPreferredSize(new Dimension(50, 30));
        btnButtonBorder.setMaximumSize(new Dimension(50, 30));
        btnButtonBorder.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                setBorderStyle(lbButtonBorder,false);
             }    
        });

        lbButtonBorder.setBorder(BorderFactory.createLineBorder(Color.black,
                                                                    1));
        lbButtonBorder.setHorizontalAlignment(SwingConstants.CENTER);
        lbButtonBorder.setBackground(SystemColor.control);
        lbButtonBorder.setFont(new Font("SansSerif", 0, 11));
        lbButtonBorder.setBounds(new Rectangle(115, 85, 95, 40));
        lbButtonBorder.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_button(e);
                    }
                });
        btnApply.setMaximumSize(new Dimension(50, 30));
        btnApply.setPreferredSize(new Dimension(50, 30));
        btnApply.setText("Apply");
        btnApply.setBounds(new Rectangle(340, 460, 75, 30));
        btnApply.setMinimumSize(new Dimension(50, 30));
        btnApply.setFont(new Font("SansSerif", 0, 11));
        btnApply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
                        appl_ActionPerformed(ae);
                    }
      });
        itemPanellabel8.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel8.setText("Item title:");
        itemPanellabel8.setBounds(new Rectangle(10, 35, 55, 20));
        itemPanelitem9.setText("Item Value 1");
        itemPanelitem9.setOpaque(false);
        itemPanelitem9.setBounds(new Rectangle(65, 55, 110, 25));
        itemPanelitem8.setText("Item Value 2");
        itemPanelitem8.setOpaque(false);
        itemPanelitem8.setBounds(new Rectangle(65, 30, 110, 25));
        itemPanelitem8.setSelected(true);
        itemPanellabel13.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel13.setText("Item title:");
        itemPanellabel13.setBounds(new Rectangle(10, 90, 55, 20));
        itemPanelitem19.setBounds(new Rectangle(180, 40, 120, 20));
        itemPanelitem19.setText("Item Value");
        itemPanelitem19.setBounds(new Rectangle(75, 20, 75, 20));
        itemPanelitem19.setText("Item value");
        itemPanellabel19.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel19.setText("Item title:");
        itemPanellabel19.setBounds(new Rectangle(10, 20, 55, 20));
        nestedPanel3.setBounds(new Rectangle(5, 45, 160, 85));
        nestedPanel3.setBorder(BorderFactory.createTitledBorder("Nested Panel"));
        nestedPanel3.setLayout(null);
        nestedPanel3.setOpaque(false);
        itemPanelitem17.setText("Item Value 2");
        itemPanelitem17.setOpaque(false);
        itemPanelitem17.setBounds(new Rectangle(70, 45, 110, 25));
        itemPanelitem17.setSelected(true);
        itemPanel2.setLayout(null);
        itemPanel2.setBounds(new Rectangle(0, 0, 1, 1));
        itemPanelitem18.setText("Item Value 1");
        itemPanelitem18.setOpaque(false);
        itemPanelitem18.setBounds(new Rectangle(70, 20, 110, 25));
        itemPanellabel17.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel17.setText("Item title:");
        itemPanellabel17.setBounds(new Rectangle(15, 25, 55, 15));
        itemPanellabel14.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel14.setText("Item title:");
        itemPanellabel14.setBounds(new Rectangle(15, 25, 55, 20));
        itemPanelitem14.setBounds(new Rectangle(60, 25, 85, 20));
        itemPanellabel15.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel15.setText("Item title:");
        itemPanellabel15.setBounds(new Rectangle(15, 50, 55, 20));
        itemPanelitem15.setBounds(new Rectangle(60, 50, 100, 55));
        itemPanelitem11.setBounds(new Rectangle(180, 40, 120, 20));
        itemPanelitem11.setText("Item Value");
        itemPanelitem11.setBounds(new Rectangle(70, 30, 75, 20));
        itemPanelitem11.setText("Item value");
        itemPanelitem12.setBounds(new Rectangle(180, 40, 120, 20));
        itemPanelitem12.setText("Item Value");
        itemPanelitem12.setBounds(new Rectangle(70, 60, 75, 20));
        itemPanelitem12.setText("Item value");
        itemPanellabel11.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel11.setText("Item title long:");
        itemPanellabel11.setBounds(new Rectangle(10, 30, 80, 20));
        itemPanellabel12.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel12.setText("Item title:");
        itemPanellabel12.setBounds(new Rectangle(10, 60, 55, 20));
        itemPanelitem13.setBounds(new Rectangle(180, 40, 120, 20));
        itemPanelitem13.setText("Item Value");
        itemPanelitem13.setBounds(new Rectangle(70, 90, 75, 20));
        itemPanelitem13.setText("Item value");
        pnItemPanel3.setBounds(new Rectangle(365, 5, 170, 145));
        pnItemPanel3.setBorder(BorderFactory.createTitledBorder("Item Panel 1"));
        pnItemPanel3.setLayout(null);
        pnItemPanel3.setVisible(false);
        pnItemPanel5.setBounds(new Rectangle(160, 10, 170, 145));
        pnItemPanel5.setBorder(BorderFactory.createTitledBorder("Item Panel 3"));
        pnItemPanel5.setLayout(null);
        pnItemPanel5.setVisible(false);
        pnItemPanel4.setBounds(new Rectangle(0, 10, 170, 145));
        pnItemPanel4.setBorder(BorderFactory.createTitledBorder("Item Panel 2"));
        pnItemPanel4.setLayout(null);
        pnItemPanel4.setVisible(false);
        scItemPanelPrew.setBounds(new Rectangle(5, 215, 590, 190));
        scItemPanelPrew.setBorder(BorderFactory.createTitledBorder("Item Panel Prewiev"));
        scItemPanelPrew.setAutoscrolls(true);
        itemPanel.setBounds(new Rectangle(105, 65, 125, 75));
        itemPanel.setLayout(null);
        itemPanel.setOpaque(false);
        itemRadio1.setText("Item Value 1");
        itemRadio1.setBounds(new Rectangle(0, 0, 110, 25));
        itemRadio1.setOpaque(false);
        itemRadio1.setSelected(true);
        itemRadio2.setText("Item Value 2");
        itemRadio2.setBounds(new Rectangle(0, 25, 115, 25));
        itemRadio2.setOpaque(false);
        itemScroll.setBounds(new Rectangle(355, 20, 125, 85));
        table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        table_mouseClicked(e);
                    }
                });
        itemPanellabel7.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel7.setText("Item title:");
        itemPanellabel7.setBounds(new Rectangle(10, 65, 55, 20));
        itemPanelitem7.setBounds(new Rectangle(180, 40, 120, 20));
        itemPanelitem7.setText("Item Value");
        itemPanelitem7.setBounds(new Rectangle(60, 65, 65, 20));
        itemPanelitem7.setText("Item value");
        itemPanelitem6.setBounds(new Rectangle(60, 30, 85, 20));
        itemPanelitem6.setText("Item value long");
        itemPanellabel6.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel6.setText("Item title:");
        itemPanellabel6.setBounds(new Rectangle(10, 30, 55, 20));
        itemPanellabel5.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel5.setText("Item title:");
        itemPanellabel5.setBounds(new Rectangle(210, 50, 55, 20));
        itemPanellabel1.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel1.setText("Item title long:");
        itemPanellabel1.setBounds(new Rectangle(5, 10, 80, 20));
        itemPanelitem1.setBounds(new Rectangle(180, 40, 120, 20));
        itemPanelitem1.setText("Item Value");
        itemPanelitem1.setBounds(new Rectangle(55, 80, 85, 20));
        itemPanelitem1.setText("Item value");
        itemPanelitem2.setBounds(new Rectangle(180, 40, 120, 20));
        itemPanelitem2.setText("Item Value");
        itemPanelitem2.setBounds(new Rectangle(55, 80, 85, 20));
        itemPanelitem2.setText("Item value");
        itemPanellabel2.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel2.setText("Item title:");
        itemPanellabel2.setBounds(new Rectangle(5, 45, 55, 20));
        itemPanelitem3.setBounds(new Rectangle(180, 40, 120, 20));
        itemPanelitem3.setText("Item Value");
        itemPanelitem3.setBounds(new Rectangle(55, 80, 85, 20));
        itemPanelitem3.setText("Item value");
        itemPanellabel3.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel3.setText("Item title:");
        itemPanellabel3.setBounds(new Rectangle(5, 80, 55, 20));
        itemPanellabel4.setFont(new Font("SansSerif", 0, 11));
        itemPanellabel4.setText("Item title:");
        itemPanellabel4.setBounds(new Rectangle(210, 10, 55, 20));
        itemPanelitem4.setBounds(new Rectangle(255, 10, 85, 20));
        itemPanelitem5.setBounds(new Rectangle(255, 50, 85, 55));
        itemLabel3.setFont(new Font("SansSerif", 0, 11));
        itemLabel3.setText("Item title:");
        itemLabel3.setBounds(new Rectangle(-195, 50, 45, 20));
        nestedPanel1.setBounds(new Rectangle(10, 5, 195, 140));
        nestedPanel1.setBorder(BorderFactory.createTitledBorder("Nested Panel 1"));
        nestedPanel1.setOpaque(false);
        nestedPanel1.setLayout(null);
        nestedPanel2.setBounds(new Rectangle(205, 5, 180, 140));
        nestedPanel2.setBorder(BorderFactory.createTitledBorder("Nested Panel 2"));
        nestedPanel2.setLayout(null);
        nestedPanel2.setOpaque(false);
        scpContex.setBounds(new Rectangle(5, 25, 85, 180));
        scpContex.setAutoscrolls(true);
        tblContex.setRowSelectionAllowed(true);
        tblContex.setBackground(Color.white);
        tblContex.setAutoscrolls(true);
        tblContex.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblContex.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbpItemPanel.setBounds(new Rectangle(95, 0, 395, 185));
        pnItemPanel1.setLayout(null);
        pnItemPanel2.setLayout(null);
        pnItemPanelPrewiev.setLayout(null);
        pnItemPanelPrewiev.setAutoscrolls(true);
        scItemPrewiev.setBounds(new Rectangle(305, 10, 285, 385));
        scItemPrewiev.setBorder(BorderFactory.createTitledBorder("Item prewiev"));
        scItemPrewiev.setAutoscrolls(true);
        itemCheck.setText("Item Value");
        itemCheck.setBounds(new Rectangle(2, 3, 120, 15));
        itemCheck.setOpaque(false);
        itemText.setBounds(new Rectangle(180, 40, 120, 20));
        itemText.setText("Item Value");
        itemText.setBounds(new Rectangle(195, 15, 125, 20));
        itemText.setText("Item value");

        itemCombo.setBounds(new Rectangle(75, 40, 125, 20));
        itemCombo.addItem("Item Value 1");
        itemCombo.addItem("Item Value 2");
        itemCombo.addItem("Item Value 3");
        itemCombo.addItem("Item Value 4");
        itemCombo.addItem("Item Value 5");
        itemCombo.addItem("Item Value 6");
        itemCombo.setRenderer(new MyCellRenderer());
        listModel.addElement("Item Value 1");
        listModel.addElement("Item Value 2");
        listModel.addElement("Item Value 3");
        listModel.addElement("Item Value 4");
        listModel.addElement("Item Value 5");
        listModel.addElement("Item Value 6");
        itemLabel.setFont(new Font("SansSerif", 0, 11));
        itemLabel.setText("Item title:");
        itemLabel.setBounds(new Rectangle(15, 40, 60, 15));
        pnItemPrewiev.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pnItemPrewiev.setAutoscrolls(true);
        pnItemPrewiev.setLayout(null);
        this.getContentPane().add(btnApply, null);
        this.getContentPane().add(jCheckBox1, null);
        this.getContentPane().add(jTabbedPane1, null);
        this.getContentPane().add(btnHelp, null);
        this.getContentPane().add(btnClose, null);
        this.getContentPane().add(btnSave, null);
        this.getContentPane().add(btnPrewiev, null);
        this.getContentPane().add(btnNext, null);
        this.getContentPane().add(btnBack, null);
        txtY.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        change(e);
                    }
                });
        txtX.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        change(e);
                    }
                });
        txtName.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        change(e);
                    }
                });
        rdFixedSize.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rdFixedSize_actionPerformed(e);
                    }
                });
        lbFontGlobal.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_table(e);
                    }
                });
        lbInputFontGlobal.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_table(e);
                    }
                });
        lbDesktop.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_desktop(e);
                    }
                });
        lbForeground.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change(e);
                    }
                });
        lbBackground.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change(e);
                    }
                });
        rdCustom.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_screeen(e);
                    }
                });
        rdLeftonTop.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_screeen(e);
                    }
                });
        rdCenter.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_screeen(e);
                    }
                });
        lbIcon.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change(e);
                    }
                });
        txtName.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change(e);
                    }
                });
        cmbColumnWidth.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_table(e);
                    }
                });
        cmbRowHeight.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_table(e);
                    }
                });
        cmbOverflow.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_table(e);
                    }
                });
        chOverflow.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if (chOverflow.isSelected())
                        {
                            cmbOverflow.setEnabled(true);
                            cmbOverflowSize.setEnabled(true);}
                        else
                         {
                            cmbOverflow.setEnabled(false);
                            cmbOverflowSize.setEnabled(false);}
                        change_table(e);
                    }
                });
        lbForegroundCell.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_table(e);
                    }
                });
        lbBackgroundCell.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_table(e);
                    }
                });
        lbForegroundTablePanel.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_table(e);
                    }
                });
        lbBackgroundTablePanel.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_table(e);
                    }
                });
        lbForegroundForm.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_form(e);
                    }
                });
        lbBackgroundForm.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_form(e);
                    }
                });
        lbFontTitle.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_form(e);
                    }
                });
        chTitle.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        showTitle(e);
                    }

                });
        cmbContex.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_item_panel(e);
                    }
                });
        chContex.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if (chContex.isSelected())
                            {
                                cmbContex.setEnabled(true);
                                rdContexDefault.setEnabled(true);
                                rdContexHeight.setEnabled(true);
                                if(rdContexHeight.isSelected())
                                    cmbContexHeight.setEnabled(true);
                            }
                        else
                            {
                                cmbContex.setEnabled(false);
                                rdContexDefault.setEnabled(false);
                                rdContexHeight.setEnabled(false);
                                cmbContexHeight.setEnabled(false);
                            }
                        change_item_panel(e);
                    }
                });
        rdContexDefault.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if (rdContexHeight.isSelected())
                            {
                                cmbContexHeight.setEnabled(true);
                            }
                        else
                            {
                                cmbContexHeight.setEnabled(false);
                            }
                        change_item_panel(e);
                    }
                });
        rdContexHeight.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if (rdContexHeight.isSelected())
                            {
                                cmbContexHeight.setEnabled(true);
                            }
                        else
                            {
                                cmbContexHeight.setEnabled(false);
                            }
                        change_item_panel(e);
                    }
                });
        rdContexHeight.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rdContexHeight_actionPerformed(e);
                    }
                });
        cmbItemPanelSpacing.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_item_panel(e);
                    }
                });
        cmbOrientation.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_item_panel(e);
                    }
                });
        rdNormal.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if (rdNormal.isSelected())
                        {
                            cmbOrientation.setEnabled(true);
                            chContex.setEnabled(false);
                            cmbContex.setEnabled(false);
                            cmbContexHeight.setEnabled(false);
                            rdContexDefault.setEnabled(false);
                            rdContexDefault.setEnabled(false);
                        }
                        else
                        {
                            cmbOrientation.setEnabled(false);
                            chContex.setEnabled(true);
                            cmbContex.setEnabled(true);
                            cmbContexHeight.setEnabled(false);
                            rdContexDefault.setEnabled(false);
                            rdContexHeight.setEnabled(false);
                            if(chContex.isSelected())
                            {
                            if(rdContexHeight.isSelected())
                                cmbContexHeight.setEnabled(true);
                            rdContexHeight.setEnabled(true);
                            rdContexDefault.setEnabled(true);
                            }
                        }
                        change_item_panel(e);
                    }
                });
        rdTabbed.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_item_panel(e);
                    }
                });
        lbForegroundItemPanel.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_item_panel(e);
                    }
                });
        lbBackgroundItemPanel.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_item_panel(e);
                    }
                });
        lbForegroundInput.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_item_panel(e);
                    }
                });
        lbBackgroundInput.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_item_panel(e);
                    }
                });
        lbForegroundButton.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_button(e);
                    }
                });
        lbBackgroundButton.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_button(e);
                    }
                });
        rdTextual.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_button(e);
                    }
                });
        rdGraphical.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_button(e);
                    }
                });
        cmbTitleAlign.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_itemType(e);
                    }
                });
        cmbTitlePosition.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_itemType(e);
                    }
                });
        cmbTitleOffset.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_itemType(e);
                    }
                });
        chItalic.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_itemType(e);
                    }
                });
        chBold.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_itemType(e);
                    }
                });
        cmbContex.setBounds(new Rectangle(115, 20, 65, 20));
        jLabel15.setText("Button view:");
        jLabel15.setBounds(new Rectangle(15, 25, 75, 20));
        jLabel15.setFont(new Font("SansSerif", 0, 11));
        rdTextual.setText("textual");
        rdTextual.setBounds(new Rectangle(115, 25, 60, 20));
        rdTextual.setSelected(true);
        rdTextual.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rdNo_actionPerformed(e);
                    }
                });
        rdGraphical.setText("iconic");
        rdGraphical.setBounds(new Rectangle(115, 50, 70, 20));
        rdGraphical.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rdNo_actionPerformed(e);
                    }
                });
        jLabel16.setFont(new Font("SansSerif", 0, 11));
        jLabel16.setBounds(new Rectangle(15, 80, 70, 20));
        jLabel16.setText("Border style:");
        jLabel19.setFont(new Font("SansSerif", 0, 11));
        jLabel19.setBounds(new Rectangle(15, 55, 110, 20));
        jLabel19.setText("Button foreground:");
        jLabel110.setFont(new Font("SansSerif", 0, 11));
        jLabel110.setBounds(new Rectangle(15, 25, 105, 20));
        jLabel110.setText("Button background:");
        lbBackgroundButton.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                    1));
        lbBackgroundButton.setHorizontalAlignment(SwingConstants.CENTER);
        lbBackgroundButton.setOpaque(true);
        lbBackgroundButton.setBackground(SystemColor.control);
        lbBackgroundButton.setFont(new Font("SansSerif", 0, 11));
        lbBackgroundButton.setBounds(new Rectangle(145, 25, 30, 20));
        lbForegroundButton.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                    1));
        lbForegroundButton.setHorizontalAlignment(SwingConstants.CENTER);
        lbForegroundButton.setOpaque(true);
        lbForegroundButton.setBackground(SystemColor.controlText);
        lbForegroundButton.setFont(new Font("SansSerif", 0, 11));
        lbForegroundButton.setBounds(new Rectangle(145, 55, 30, 20));
        btnForgroundButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setColor(lbForegroundButton,e);
                    }
                });
        btnForgroundButton.setFont(new Font("SansSerif", 0, 11));
        btnForgroundButton.setMinimumSize(new Dimension(50, 30));
        btnForgroundButton.setBounds(new Rectangle(185, 55, 30, 20));
        btnForgroundButton.setText("...");
        btnForgroundButton.setPreferredSize(new Dimension(50, 30));
        btnForgroundButton.setMaximumSize(new Dimension(50, 30));
        btnBackgroundButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setColor(lbBackgroundButton,e);
                    }
                });
        btnBackgroundButton.setFont(new Font("SansSerif", 0, 11));
        btnBackgroundButton.setMinimumSize(new Dimension(50, 30));
        btnBackgroundButton.setBounds(new Rectangle(185, 25, 30, 20));
        btnBackgroundButton.setText("...");
        btnBackgroundButton.setPreferredSize(new Dimension(50, 30));
        btnBackgroundButton.setMaximumSize(new Dimension(50, 30));
        jPanel3.setLayout(null);
        jPanel3.setVisible(false);
        cmbTitleAlign.setBounds(new Rectangle(115, 30, 95, 20));
        jLabel112.setFont(new Font("SansSerif", 0, 11));
        jLabel112.setBounds(new Rectangle(15, 30, 75, 20));
        jLabel112.setText("Title align:");
        cmbTitlePosition.setBounds(new Rectangle(115, 60, 95, 20));
        jLabel114.setFont(new Font("SansSerif", 0, 11));
        jLabel114.setBounds(new Rectangle(15, 60, 75, 20));
        jLabel114.setText("Title position:");
        chBold.setText("bold");
        chBold.setBounds(new Rectangle(170, 120, 50, 20));
        chItalic.setText("italic");
        chItalic.setBounds(new Rectangle(115, 120, 50, 20));
        jLabel2.setText("Title style:");
        jLabel2.setBounds(new Rectangle(15, 120, 70, 20));
        cmbTitleOffset.setBounds(new Rectangle(115, 90, 95, 20));
        jLabel115.setText("Title offset:");
        jLabel115.setBounds(new Rectangle(15, 90, 75, 20));
        jLabel115.setFont(new Font("SansSerif", 0, 11));
        jPanel4.setLayout(null);
        btnForgroundInput.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setColor(lbForegroundInput,e);
                    }
                });
        btnForgroundInput.setFont(new Font("SansSerif", 0, 11));
        btnForgroundInput.setMinimumSize(new Dimension(50, 30));
        btnForgroundInput.setBounds(new Rectangle(145, 95, 30, 20));
        btnForgroundInput.setText("...");
        btnForgroundInput.setPreferredSize(new Dimension(50, 30));
        btnForgroundInput.setMaximumSize(new Dimension(50, 30));
        btnBackgroundInput.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setColor(lbBackgroundInput,e);
                    }
                });
        btnBackgroundInput.setFont(new Font("SansSerif", 0, 11));
        btnBackgroundInput.setMinimumSize(new Dimension(50, 30));
        btnBackgroundInput.setBounds(new Rectangle(145, 70, 30, 20));
        btnBackgroundInput.setText("...");
        btnBackgroundInput.setPreferredSize(new Dimension(50, 30));
        btnBackgroundInput.setMaximumSize(new Dimension(50, 30));
        lbBackgroundInput.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                   1));
        lbBackgroundInput.setHorizontalAlignment(SwingConstants.CENTER);
        lbBackgroundInput.setOpaque(true);
        lbBackgroundInput.setBackground(Color.white);
        lbBackgroundInput.setFont(new Font("SansSerif", 0, 11));
        lbBackgroundInput.setBounds(new Rectangle(105, 70, 30, 20));
        lbForegroundInput.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                   1));
        lbForegroundInput.setHorizontalAlignment(SwingConstants.CENTER);
        lbForegroundInput.setOpaque(true);
        lbForegroundInput.setBackground(SystemColor.controlText);
        lbForegroundInput.setFont(new Font("SansSerif", 0, 11));
        lbForegroundInput.setBounds(new Rectangle(105, 95, 30, 20));
        jLabel119.setFont(new Font("SansSerif", 0, 11));
        jLabel119.setBounds(new Rectangle(10, 95, 90, 20));
        jLabel119.setText("Input foreground:");
        jLabel1110.setFont(new Font("SansSerif", 0, 11));
        jLabel1110.setBounds(new Rectangle(10, 70, 90, 20));
        jLabel1110.setText("Input background:");
        jLabel120.setText("Foreground:");
        jLabel120.setBounds(new Rectangle(10, 45, 70, 20));
        jLabel120.setFont(new Font("SansSerif", 0, 11));
        lbForegroundItemPanel.setBounds(new Rectangle(105, 45, 30, 20));
        lbForegroundItemPanel.setFont(new Font("SansSerif", 0, 11));
        lbForegroundItemPanel.setBackground(SystemColor.controlText);
        lbForegroundItemPanel.setOpaque(true);
        lbForegroundItemPanel.setHorizontalAlignment(SwingConstants.CENTER);
        lbForegroundItemPanel.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                       1));
        btnForgroundItemPanel.setMaximumSize(new Dimension(50, 30));
        btnForgroundItemPanel.setPreferredSize(new Dimension(50, 30));
        btnForgroundItemPanel.setText("...");
        btnForgroundItemPanel.setBounds(new Rectangle(145, 45, 30, 20));
        btnForgroundItemPanel.setMinimumSize(new Dimension(50, 30));
        btnForgroundItemPanel.setFont(new Font("SansSerif", 0, 11));
        btnForgroundItemPanel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setColor(lbForegroundItemPanel,e);
                    }
                });
        btnBackgroundItemPanel.setMaximumSize(new Dimension(50, 30));
        btnBackgroundItemPanel.setPreferredSize(new Dimension(50, 30));
        btnBackgroundItemPanel.setText("...");
        btnBackgroundItemPanel.setBounds(new Rectangle(145, 20, 30, 20));
        btnBackgroundItemPanel.setMinimumSize(new Dimension(50, 30));
        btnBackgroundItemPanel.setFont(new Font("SansSerif", 0, 11));
        btnBackgroundItemPanel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setColor(lbBackgroundItemPanel,e);
                    }
                });
        lbBackgroundItemPanel.setBounds(new Rectangle(105, 20, 30, 20));
        lbBackgroundItemPanel.setFont(new Font("SansSerif", 0, 11));
        lbBackgroundItemPanel.setBackground(SystemColor.control);
        lbBackgroundItemPanel.setOpaque(true);
        lbBackgroundItemPanel.setHorizontalAlignment(SwingConstants.CENTER);
        lbBackgroundItemPanel.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                       1));
        jLabel121.setText("Background:");
        jLabel121.setBounds(new Rectangle(10, 20, 80, 20));
        jLabel121.setFont(new Font("SansSerif", 0, 11));
        chContex.setBounds(new Rectangle(90, 20, 20, 20));
        jLabel3.setBounds(new Rectangle(10, 25, 100, 20));
        jLabel3.setText("Show:");
        cmbItemPanelSpacing.setBounds(new Rectangle(90, 70, 50, 20));
        jLabel1113.setFont(new Font("SansSerif", 0, 11));
        jLabel1113.setBounds(new Rectangle(10, 70, 105, 20));
        jLabel1113.setText("Panel spacing:");
        rdNormal.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rdNo_actionPerformed(e);
                    }
                });
        rdNormal.setBounds(new Rectangle(85, 20, 60, 20));
        rdNormal.setText("normal");
        rdNormal.setSelected(true);
        rdTabbed.setActionCommand("3DItem");
        rdTabbed.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rdNo_actionPerformed(e);
                    }
                });
        rdTabbed.setBounds(new Rectangle(85, 40, 60, 20));
        rdTabbed.setText("tabbed");
        jLabel1114.setFont(new Font("SansSerif", 0, 11));
        jLabel1114.setBounds(new Rectangle(10, 20, 85, 20));
        jLabel1114.setText("Style:");
        cmbOrientation.setBounds(new Rectangle(145, 20, 60, 20));
        jPanel2.setLayout(null);
        jLabel122.setFont(new Font("SansSerif", 0, 11));
        jLabel122.setBounds(new Rectangle(10, 45, 130, 20));
        jLabel122.setText("Table foreground:");
        jLabel123.setFont(new Font("SansSerif", 0, 11));
        jLabel123.setBounds(new Rectangle(10, 20, 150, 20));
        jLabel123.setText("Table background:");
        lbBackgroundTablePanel.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                        1));
        lbBackgroundTablePanel.setHorizontalAlignment(SwingConstants.CENTER);
        lbBackgroundTablePanel.setOpaque(true);
        lbBackgroundTablePanel.setBackground(SystemColor.control);
        lbBackgroundTablePanel.setFont(new Font("SansSerif", 0, 11));
        lbBackgroundTablePanel.setBounds(new Rectangle(105, 20, 30, 20));
        btnBackgroundTablePanel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setColor(lbBackgroundTablePanel,e);
                    }
                });
        btnBackgroundTablePanel.setFont(new Font("SansSerif", 0, 11));
        btnBackgroundTablePanel.setMinimumSize(new Dimension(50, 30));
        btnBackgroundTablePanel.setBounds(new Rectangle(145, 20, 30, 20));
        btnBackgroundTablePanel.setText("...");
        btnBackgroundTablePanel.setPreferredSize(new Dimension(50, 30));
        btnBackgroundTablePanel.setMaximumSize(new Dimension(50, 30));
        btnForgroundTablePanel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setColor(lbForegroundTablePanel,e);
                    }
                });
        btnForgroundTablePanel.setFont(new Font("SansSerif", 0, 11));
        btnForgroundTablePanel.setMinimumSize(new Dimension(50, 30));
        btnForgroundTablePanel.setBounds(new Rectangle(145, 45, 30, 20));
        btnForgroundTablePanel.setText("...");
        btnForgroundTablePanel.setPreferredSize(new Dimension(50, 30));
        btnForgroundTablePanel.setMaximumSize(new Dimension(50, 30));
        lbForegroundTablePanel.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                        1));
        lbForegroundTablePanel.setHorizontalAlignment(SwingConstants.CENTER);
        lbForegroundTablePanel.setOpaque(true);
        lbForegroundTablePanel.setBackground(SystemColor.controlText);
        lbForegroundTablePanel.setFont(new Font("SansSerif", 0, 11));
        lbForegroundTablePanel.setBounds(new Rectangle(105, 45, 30, 20));
        lbBackgroundCell.setBounds(new Rectangle(105, 90, 30, 20));
        lbBackgroundCell.setFont(new Font("SansSerif", 0, 11));
        lbBackgroundCell.setBackground(Color.white);
        lbBackgroundCell.setOpaque(true);
        lbBackgroundCell.setHorizontalAlignment(SwingConstants.CENTER);
        lbBackgroundCell.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                  1));
        jLabel1112.setText("Cell background:");
        jLabel1112.setBounds(new Rectangle(10, 90, 95, 20));
        jLabel1112.setFont(new Font("SansSerif", 0, 11));
        btnBackgroundCell.setMaximumSize(new Dimension(50, 30));
        btnBackgroundCell.setPreferredSize(new Dimension(50, 30));
        btnBackgroundCell.setText("...");
        btnBackgroundCell.setBounds(new Rectangle(145, 90, 30, 20));
        btnBackgroundCell.setMinimumSize(new Dimension(50, 30));
        btnBackgroundCell.setFont(new Font("SansSerif", 0, 11));
        btnBackgroundCell.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setColor(lbBackgroundCell,e);
                    }
                });
        btnForgroundCell.setMaximumSize(new Dimension(50, 30));
        btnForgroundCell.setPreferredSize(new Dimension(50, 30));
        btnForgroundCell.setText("...");
        btnForgroundCell.setBounds(new Rectangle(145, 115, 30, 20));
        btnForgroundCell.setMinimumSize(new Dimension(50, 30));
        btnForgroundCell.setFont(new Font("SansSerif", 0, 11));
        btnForgroundCell.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setColor(lbForegroundCell,e);
                    }
                });
        lbForegroundCell.setBounds(new Rectangle(105, 115, 30, 20));
        lbForegroundCell.setFont(new Font("SansSerif", 0, 11));
        lbForegroundCell.setBackground(SystemColor.controlText);
        lbForegroundCell.setOpaque(true);
        lbForegroundCell.setHorizontalAlignment(SwingConstants.CENTER);
        lbForegroundCell.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                  1));
        jLabel1111.setText("Cell foreground:");
        jLabel1111.setBounds(new Rectangle(10, 115, 95, 20));
        jLabel1111.setFont(new Font("SansSerif", 0, 11));
        cmbRowHeight.setBounds(new Rectangle(130, 20, 50, 20));
        jLabel1115.setFont(new Font("SansSerif", 0, 11));
        jLabel1115.setBounds(new Rectangle(10, 20, 75, 20));
        jLabel1115.setText("Row height:");
        cmbColumnWidth.setBounds(new Rectangle(130, 55, 50, 20));
        jLabel1116.setText("Default column width:");
        jLabel1116.setBounds(new Rectangle(10, 55, 110, 20));
        jLabel1116.setFont(new Font("SansSerif", 0, 11));
        jLabel1117.setFont(new Font("SansSerif", 0, 11));
        jLabel1117.setBounds(new Rectangle(10, 25, 50, 20));
        jLabel1117.setText("Show");
        chOverflow.setBounds(new Rectangle(70, 25, 45, 20));
        chOverflow.setOpaque(false);
        cmbOverflow.setBounds(new Rectangle(90, 25, 75, 20));
        jPanel1.setLayout(null);

        jPanel1.addComponentListener(new ComponentAdapter() {
                    public void componentShown(ComponentEvent e) {
                        jPanel1_componentShown(e);
                    }
                });
        jLabel14.setFont(new Font("SansSerif", 0, 11));
        jLabel14.setBounds(new Rectangle(20, 30, 105, 20));
        jLabel14.setText("Form background:");
        lbBackgroundForm.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                  1));
        lbBackgroundForm.setHorizontalAlignment(SwingConstants.CENTER);
        lbBackgroundForm.setOpaque(true);
        lbBackgroundForm.setBackground(SystemColor.control);
        lbBackgroundForm.setFont(new Font("SansSerif", 0, 11));
        lbBackgroundForm.setBounds(new Rectangle(180, 30, 30, 20));
        btnBackgroundForm.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setColor(lbBackgroundForm,e);
                    }
                });
        btnBackgroundForm.setFont(new Font("SansSerif", 0, 11));
        btnBackgroundForm.setMinimumSize(new Dimension(50, 30));
        btnBackgroundForm.setBounds(new Rectangle(220, 30, 30, 20));
        btnBackgroundForm.setText("...");
        btnBackgroundForm.setPreferredSize(new Dimension(50, 30));
        btnBackgroundForm.setMaximumSize(new Dimension(50, 30));
        btnForgroundForm.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setColor(lbForegroundForm,e);
                    }
                });
        btnForgroundForm.setFont(new Font("SansSerif", 0, 11));
        btnForgroundForm.setMinimumSize(new Dimension(50, 30));
        btnForgroundForm.setBounds(new Rectangle(220, 60, 30, 20));
        btnForgroundForm.setText("...");
        btnForgroundForm.setPreferredSize(new Dimension(50, 30));
        btnForgroundForm.setMaximumSize(new Dimension(50, 30));
        lbForegroundForm.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                  1));
        lbForegroundForm.setHorizontalAlignment(SwingConstants.CENTER);
        lbForegroundForm.setOpaque(true);
        lbForegroundForm.setBackground(SystemColor.controlText);
        lbForegroundForm.setFont(new Font("SansSerif", 0, 11));
        lbForegroundForm.setBounds(new Rectangle(180, 60, 30, 20));
        jLabel12.setFont(new Font("SansSerif", 0, 11));
        jLabel12.setBounds(new Rectangle(20, 60, 85, 20));
        jLabel12.setText("Form foreground:");
        jScrollPane3.setFont(new Font("SansSerif", 0, 11));
        lbTitle.setFont(new Font("SansSerif", 0, 11));
        lbFontTitle.setOpaque(true);
        lbFontTitle.setBackground(Color.white);
        lbFontTitle.setText("SansSerif.plain, 11");
        lbFontGlobal.setText(lbFontGlobal.getFont().getName().toLowerCase() +", "+lbFontGlobal.getFont().getSize());   
        lbInputFontGlobal.setText(lbInputFontGlobal.getFont().getName().toLowerCase() +", "+lbInputFontGlobal.getFont().getSize());   
        lbFontTitle.setText(lbFontTitle.getFont().getName().toLowerCase() +", "+lbFontTitle.getFont().getSize()); 
        lbFontTitle.setFont(new Font("SansSerif", 1, 14));
        lbFontTitle.setEditorKit(new HTMLEditorKit());
        lbFontTitle.setEditable(false);
        jScrollPane3.setBounds(new Rectangle(90, 65, 125, 20));
        jScrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        btnFontTitle.setFont(new Font("SansSerif", 0, 11));
        btnFontTitle.setMinimumSize(new Dimension(50, 30));
        btnFontTitle.setBounds(new Rectangle(220, 65, 30, 20));
        btnFontTitle.setText("...");
        btnFontTitle.setPreferredSize(new Dimension(50, 30));
        btnFontTitle.setMaximumSize(new Dimension(50, 30));
        btnFontTitle.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       setTextFont(lbFontTitle);
                    }
                });
        jLabel11.setFont(new Font("SansSerif", 0, 11));
        jLabel11.setBounds(new Rectangle(20, 65, 30, 20));
        jLabel11.setText("Font:");
        chTitle.setBounds(new Rectangle(90, 35, 20, 15));
        chTitle.setText("jCheckBox1");
        lbTitle.setBounds(new Rectangle(20, 30, 55, 20));
        lbTitle.setText("Show title");
        pnlForm.setLayout(null);
        rdFixedSize.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        screen(e);
                    }
                });
        txtY.setText("600");
        txtX.setText("800");
        rdFullScreen.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        screen(e);
                    }
                });
        lbY.setBounds(new Rectangle(185, 165, 50, 20));
        lbX.setBounds(new Rectangle(145, 165, 50, 20));
        if(ID!=-1)
        template.LoadTemplate(this);
        if (chOverflow.isSelected())
        {
            cmbOverflow.setEnabled(true);
            cmbOverflowSize.setEnabled(true);
        }
        else
        {
            cmbOverflow.setEnabled(false);
            cmbOverflowSize.setEnabled(false);
        }
        if (rdNormal.isSelected())
        {
            cmbOrientation.setEnabled(true);
            chContex.setEnabled(false);
            cmbContex.setEnabled(false);
        }
        else
        {
            cmbOrientation.setEnabled(false);
            chContex.setEnabled(true);
            cmbContex.setEnabled(true);
        }
        if (chContex.isSelected())
            cmbContex.setEnabled(true);
        else
            cmbContex.setEnabled(false);
        if (rdFullScreen.isSelected()) {
            txtX.setEnabled(false);
            txtY.setEnabled(false);
        } else {
            txtX.setEnabled(true);
            txtY.setEnabled(true);
        }
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        txtRadio.setBorder(BorderFactory.createLineBorder(SystemColor.control, 
                                                          2));
        txtCheck.setBorder(BorderFactory.createLineBorder(SystemColor.control, 
                                                          2));
        txtCombo.setBorder(BorderFactory.createLineBorder(SystemColor.control, 
                                                          2));
        txtList.setBorder(BorderFactory.createLineBorder(SystemColor.control, 
                                                         2));
        txtText.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        txtList.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        change_border_raise(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        change_border_none(e);
                    }

                    public void mouseClicked(MouseEvent e) {
                        change_border_lower(e);
                    }
                });
        txtText.setBounds(new Rectangle(1, 1, 200, 30));
        txtRadio.setBounds(new Rectangle(1, 30, 200, 30));
        txtCheck.setBounds(new Rectangle(1, 60, 200, 30));
        txtCombo.setBounds(new Rectangle(1, 90, 200, 30));
        txtList.setBounds(new Rectangle(1, 120, 200, 30));
        txtCombo.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        change_border_raise(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        change_border_none(e);
                    }

                    public void mouseClicked(MouseEvent e) {
                        change_border_lower(e);
                    }
                });
        txtCheck.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        change_border_raise(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        change_border_none(e);
                    }

                    public void mouseClicked(MouseEvent e) {
                        change_border_lower(e);
                    }
                });
        txtRadio.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        change_border_raise(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        change_border_none(e);
                    }

                    public void mouseClicked(MouseEvent e) {
                        change_border_lower(e);
                    }
                });
        txtText.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        change_border_raise(e);
                    }

                    public void mouseExited(MouseEvent e) {
                        change_border_none(e);
                    }

                    public void mouseClicked(MouseEvent e) {
                        change_border_lower(e);
                    }
                });
        jCheckBox1.setBounds(new Rectangle(980, 380, 79, 23));
        jCheckBox1.setText("jCheckBox1");
        txtList.setText("  List");
        txtList.setMinimumSize(new Dimension(40, 120));
        txtList.setMaximumSize(new Dimension(40, 120));
        txtList.setSize(new Dimension(270, 30));
        txtList.setPreferredSize(new Dimension(270, 30));
        txtList.setIcon(iconList);
        txtCombo.setText("  Combo Box");
        txtCombo.setMinimumSize(new Dimension(40, 120));
        txtCombo.setMaximumSize(new Dimension(40, 120));
        txtCombo.setSize(new Dimension(270, 30));
        txtCombo.setPreferredSize(new Dimension(270, 30));
        txtCombo.setIcon(iconCombo);
        txtCheck.setText("  Check Box");
        txtCheck.setMinimumSize(new Dimension(40, 120));
        txtCheck.setMaximumSize(new Dimension(40, 120));
        txtCheck.setSize(new Dimension(270, 30));
        txtCheck.setPreferredSize(new Dimension(270, 30));
        txtCheck.setIcon(iconCheck);
        txtRadio.setText("  Radio Button");
        txtRadio.setMinimumSize(new Dimension(40, 120));
        txtRadio.setMaximumSize(new Dimension(40, 120));
        txtRadio.setSize(new Dimension(270, 30));
        txtRadio.setPreferredSize(new Dimension(270, 30));
        txtRadio.setIcon(iconRadio);
        txtText.setIcon(iconText);
        jPanel7.setPreferredSize(new Dimension(201, 28));
        txtText.setPreferredSize(new Dimension(270, 30));
        txtText.setSize(new Dimension(270, 30));
        txtText.setMaximumSize(new Dimension(40, 120));
        txtText.setMinimumSize(new Dimension(40, 120));
        txtText.setText("  Text Box");
        jPanel7.setLayout(null);
        jPanel7.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        jPanel7.setBounds(new Rectangle(15, 15, 272, 152));
        jPanel7.setSize(new Dimension(272, 151));
        btnPrewFirst.setContentAreaFilled(false);
        btnPrewLast.setContentAreaFilled(false);
        btnPrewNew.setContentAreaFilled(false);
        btnPrewNext.setContentAreaFilled(false);
        btnPrewPrevios.setContentAreaFilled(false);
        btnPrewOK.setContentAreaFilled(false);
        btnPrewDelete.setContentAreaFilled(false);
        btnPrewDeleteAll.setContentAreaFilled(false);
        btnPrewDuplicate.setContentAreaFilled(false);
        btnPrewCancel.setContentAreaFilled(false);
        btnPrewApply.setContentAreaFilled(false);
        btnPrewFirst.setOpaque(true);
        btnPrewFirst.setBackground(SystemColor.control);
        btnPrewApply.setOpaque(true);
        btnPrewDeleteAll.setOpaque(true);
        btnPrewDuplicate.setOpaque(true);
        btnPrewDelete.setOpaque(true);
        btnPrewNew.setOpaque(true);
        btnPrewOK.setOpaque(true);
        btnPrewCancel.setOpaque(true);
        btnPrewLast.setOpaque(true);
        btnPrewNext.setOpaque(true);
        btnPrewPrevios.setOpaque(true);
        btnPrewApply.setActionCommand("First");
        btnPrewDelete.setActionCommand("First");
        btnPrewDeleteAll.setActionCommand("First");
        btnPrewDuplicate.setActionCommand("First");
        btnPrewNew.setActionCommand("First");
        btnPrewOK.setActionCommand("First");
        btnPrewCancel.setActionCommand("First");
        btnPrewLast.setActionCommand("First");
        btnPrewNext.setActionCommand("First");
        btnPrewPrevios.setActionCommand("First");
        btnPrewNew.setContentAreaFilled(false);
        btnPrewOK.setContentAreaFilled(false);
        btnPrewCancel.setContentAreaFilled(false);
        btnPrewApply.setContentAreaFilled(false);
        btnPrewPrevios.setContentAreaFilled(false);
        btnPrewNext.setContentAreaFilled(false);
        btnPrewLast.setContentAreaFilled(false);
        btnPrewDelete.setContentAreaFilled(false);
        lbNavigation.setText("Record: 1/100");
        lbNavigation.setPreferredSize(new Dimension(75, 30));
        btnPrewLast.setSize(new Dimension(75, 30));
        btnPrewLast.setFont(new Font("SansSerif", 0, 11));
        btnPrewLast.setMinimumSize(new Dimension(75, 30));
        btnPrewLast.setText("Last");
        btnPrewLast.setPreferredSize(new Dimension(75, 30));
        btnPrewLast.setMaximumSize(new Dimension(75, 30));
        btnPrewNext.setSize(new Dimension(75, 30));
        btnPrewNext.setFont(new Font("SansSerif", 0, 11));
        btnPrewNext.setMinimumSize(new Dimension(75, 30));
        btnPrewNext.setText("Next");
        btnPrewNext.setPreferredSize(new Dimension(75, 30));
        btnPrewNext.setMaximumSize(new Dimension(75, 30));
        btnPrewPrevios.setSize(new Dimension(75, 30));
        btnPrewPrevios.setFont(new Font("SansSerif", 0, 11));
        btnPrewPrevios.setMinimumSize(new Dimension(75, 30));
        btnPrewPrevios.setText("Previos");
        btnPrewPrevios.setPreferredSize(new Dimension(75, 30));
        btnPrewPrevios.setMaximumSize(new Dimension(75, 30));
        btnPrewFirst.setMaximumSize(new Dimension(75, 30));
        btnPrewFirst.setPreferredSize(new Dimension(75, 30));
        btnPrewFirst.setText("First");
        btnPrewFirst.setMinimumSize(new Dimension(75, 30));
        btnPrewFirst.setFont(new Font("SansSerif", 0, 11));
        btnPrewFirst.setSize(new Dimension(75, 30));
        pnNavigationButton.setBounds(new Rectangle(55, 60, 475, 40));
        pnNavigationButton.setLayout(flowLayout2);
        pnNavigationButton.setOpaque(false);
        btnPrewCancel.setMaximumSize(new Dimension(75, 30));
        btnPrewCancel.setPreferredSize(new Dimension(75, 30));
        btnPrewCancel.setText("Cancel");
        btnPrewCancel.setMinimumSize(new Dimension(75, 30));
        btnPrewCancel.setFont(new Font("SansSerif", 0, 11));
        btnPrewCancel.setSize(new Dimension(75, 30));
        btnPrewDelete.setMaximumSize(new Dimension(75, 30));
        btnPrewDelete.setPreferredSize(new Dimension(75, 30));
        btnPrewDelete.setText("Delete");
        btnPrewDeleteAll.setMaximumSize(new Dimension(75, 30));
        btnPrewDeleteAll.setPreferredSize(new Dimension(75, 30));
        btnPrewDeleteAll.setText("Delete all");
        btnPrewDuplicate.setMaximumSize(new Dimension(75, 30));
        btnPrewDuplicate.setPreferredSize(new Dimension(75, 30));
        btnPrewDuplicate.setText("Duplicate");
        btnPrewDelete.setMinimumSize(new Dimension(75, 30));
        btnPrewDelete.setFont(new Font("SansSerif", 0, 11));
        btnPrewDelete.setSize(new Dimension(75, 30));
        btnPrewNew.setMaximumSize(new Dimension(75, 30));
        btnPrewNew.setPreferredSize(new Dimension(75, 30));
        btnPrewNew.setText("New");
        btnPrewNew.setMinimumSize(new Dimension(75, 30));
        btnPrewNew.setFont(new Font("SansSerif", 0, 11));
        btnPrewNew.setSize(new Dimension(75, 30));
        btnPrewOK.setMaximumSize(new Dimension(75, 30));
        btnPrewOK.setPreferredSize(new Dimension(75, 30));
        btnPrewOK.setText("OK");
        btnPrewOK.setMinimumSize(new Dimension(75, 30));
        btnPrewOK.setFont(new Font("SansSerif", 0, 11));
        btnPrewOK.setSize(new Dimension(75, 30));
        btnPrewApply.setSize(new Dimension(75, 30));
        pnEditingButton.setLayout(flowLayout1);
        btnPrewApply.setFont(new Font("SansSerif", 0, 11));
        btnPrewApply.setMinimumSize(new Dimension(75, 30));
        btnPrewApply.setText("Apply");
        btnPrewApply.setPreferredSize(new Dimension(75, 30));
        btnPrewApply.setMaximumSize(new Dimension(75, 30));
        pnEditingButton.setBounds(new Rectangle(0, 130, 580, 40));
        pnEditingButton.setOpaque(false);
        scButtonPrewiev.setLayout(null);
        lbOverflow.setWrapStyleWord(true);
        scButtonPrewiev.setBorder(BorderFactory.createTitledBorder("Button prewiev"));
        scButtonPrewiev.setBounds(new Rectangle(10, 170, 580, 230));
        scTablePrewiev.setBorder(BorderFactory.createTitledBorder("Table prewiev"));
        scTablePrewiev.setAutoscrolls(true);
        pnTablePrewiev.setAutoscrolls(true);
        pnTablePrewiev.setBounds(new Rectangle(0, 0, 550, 425));
        pnTablePrewiev.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        pnTablePrewiev_propertyChange(e);
                    }
                });
        pnTablePrewiev.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        pnTablePrewiev_mouseReleased(e);
                    }

                    public void mouseClicked(MouseEvent e) {
                        pnTablePrewiev_mouseClicked(e);
                    }
                });
        scTablePrewiev.getViewport().setLayout(null);
        scTablePrewiev.setBounds(new Rectangle(10, 165, 590, 240));
        lbOverflow.setFont(new Font("SansSerif", 1, 11));
        lbOverflow.setBackground(Color.white);
        lbOverflow.setOpaque(true);
        lbOverflow.setLineWrap(true);
        lbOverflow.setEditable(false);
        lbOverflow.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        lbOverflow.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change_form(e);
                    }
                });
        pnOverflow.setFont(new Font("SansSerif", 0, 11));
        tableScrollPane.setBounds(new Rectangle(0, 0, 430, 175));
        tableScrollPane.setAutoscrolls(true);
        tableScrollPane.setOpaque(false);
        chTitle.setSelected(true);
        lbContent.setHorizontalAlignment(SwingConstants.CENTER);
        lbContent.setIcon(imageFormContent);
        lbContent.setBounds(new Rectangle(300, 100, 275, 165));
        lbForm.setOpaque(true);
        cmbFormTitle.setBounds(new Rectangle(115, 30, 100, 20));
        cmbFormTitle.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        change_form(e);
                    }
                });
        lbForm.setBounds(new Rectangle(300, 60, 270, 229));
        lbIcon1.setBounds(new Rectangle(285, 15, 300, 290));
        lbIcon1.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        change(e);
                    }
                });
        lbIcon1.setIcon(imageFormPrewiev);
        lbIcon1.setToolTipText(dir1.getCanonicalPath() + 
                               "/src/ui/icons/IIST.gif");
        lbIcon1.setHorizontalAlignment(SwingConstants.CENTER);
        lbIcon1.setBackground(SystemColor.controlText);
        lbIcon1.setFont(new Font("SansSerif", 0, 11));
        xPos = template.x_position;
        yPos = template.y_position;
        scpContex.setVisible(false);
        change_screeen();
        change_table();
        change_button();
        itemID=0;
        change_itemType();
        change_item_panel();
        screen();
        lbScreen.repaint();
        tblContex.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemPanelitem4.addItem("Item Value 1");
        itemPanelitem4.addItem("Item Value 2");
        itemPanelitem4.addItem("Item Value 3");
        itemPanelitem4.addItem("Item Value 4");
        itemPanelitem4.addItem("Item Value 5");
        itemPanelitem4.addItem("Item Value 6");
        itemList.setModel(listModel);
        itemPanelitem14.addItem("Item Value 1");
        itemPanelitem14.addItem("Item Value 2");
        itemPanelitem14.addItem("Item Value 3");
        itemPanelitem14.addItem("Item Value 4");
        itemPanelitem14.addItem("Item Value 5");
        itemPanelitem14.addItem("Item Value 6");
        itemPanelitem14.setRenderer(new MyCellRenderer());
        itemPanelitem4.setRenderer(new MyCellRenderer());
        btnSave.setEnabled(false);
        btnApply.setEnabled(false);
        template.group=group;
        template.ID=ID;
    }

  private void apply_ActionPerformed(ActionEvent e)
  {
  jTabbedPane1.setSelectedIndex(jTabbedPane1.getSelectedIndex()-1);
  setButtons();
  }

  private void erase_ActionPerformed(ActionEvent e)
  {
  jTabbedPane1.setSelectedIndex(jTabbedPane1.getSelectedIndex()+1);
  setButtons();
  }
   
  private void createTemplate()
  {
    }
  private void new_ActionPerformed(ActionEvent e)
  {
  Runtime.getRuntime().gc();
  template=new Template(this, template.ID, template.group);
  setItem(getItemLabel(itemID));
  template.LoadItemType(this);
  try {
    Application app =new Application();
    app.applicationExample();
    gen.createUIMLSpecification(template, app);
    gen.renderUIMLSpecification();
  }
  catch (SQLException ex) {
       	System.out.println("Exception: " + ex);
       	ex.printStackTrace ();
  }
  catch (IOException ex) {
       	System.out.println("Exception: " + ex);
       	ex.printStackTrace ();
  }
  
  }

  private int save(){
      JDBCQuery query=new JDBCQuery(connection);
      ResultSet rs;
          
      if(rdFixedSize.isSelected())
      {
        try {
        Integer i = new Integer(txtX.getText().toString());
        Integer j = new Integer(txtY.getText().toString());
        if(i.intValue() <=0 || j.intValue()<=0){
          JOptionPane.showMessageDialog(null, "Bad screen size dimensions!", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        } 
        } 
        catch (Exception cce) {
          JOptionPane.showMessageDialog(null, "Bad screen size dimensions!", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        } 
      }
   
      template = new Template(this, template.ID, template.group);
      try
      { 
        rs=query.select("select * from IIST_TEMPLATE  where T_name='%"+template.name +"%' and T_id="+template.ID);
        if(rs.next())
        {
          JOptionPane.showMessageDialog(null, "Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
          return -1;
        }
        query.Close();
      }
      catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }

      setItem(getItemLabel(itemID));
      template.LoadItemType(this);
      if(template.name.equals(""))
      {
          JOptionPane.showMessageDialog(null, "Name is empty!", "Error", JOptionPane.ERROR_MESSAGE);
          return -1;
      }  
      else
      {
      template.Save(connection);
      btnSave.setEnabled(false);
      btnApply.setEnabled(false);
      tree.pravi_drvo();

      String par="";
      try
      {
        rs=query.select("select TG_name from IIST_TEMPLATE left join IIST_TEMPLATE_GROUP on (IIST_TEMPLATE.TG_id=IIST_TEMPLATE_GROUP.TG_id) where T_id="+template.ID);
        if(rs.next())
          par=rs.getString("TG_name");
        query.Close();
      }
      catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      tree.select_node(template.name,par,"Templates");
      }
      return 0;
  }
  private void save_ActionPerformed(ActionEvent e)
  {
    if((save())==0)
    dispose();
  }

  private void close_ActionPerformed(ActionEvent e)
  {
  dispose();
  }

  private void btnHelp_actionPerformed(ActionEvent e)
  {Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
  Settings.Center(hlp);
  hlp.setVisible(true);
  }

  

  private void rdYes_actionPerformed(ActionEvent e)
  {
  }

  private void rdYes_propertyChange(PropertyChangeEvent e)
  {
  }


  private void btnIcon_actionPerformed(ActionEvent e)
  {
    JFileChooser fc = new JFileChooser( );
    fc.addChoosableFileFilter(new MyFilter("gif"));
    fc.showOpenDialog(this);
    File openFile = fc.getSelectedFile();
    if(openFile!=null)
    if(openFile.isFile())
    {
      ImageIcon image = new ImageIcon(openFile.getPath());
      lbIcon.setIcon(image);
      lbIcon.setToolTipText(openFile.getPath());
    }
  }

    private void change_itemType(ItemEvent e) {
    change_itemType();
    change(e);
    }

    private void jPanel4_mouseClicked(MouseEvent e) {
        change_itemType();
    }

    private void change_item_panel(ItemEvent e) {
    change_item_panel();
    change(e);
    }

    public void change_item_panel(){
    itemPanelBorder=lbItemPanelBorder.getBorder();
    if(lbItemPanelBorder.getText().startsWith("Line")) {
         String[] m=lbItemPanelBorder.getText().split(",");
         int width=(new Integer(m[1].trim())).intValue();
         itemPanelBorder=BorderFactory.createLineBorder(lbForegroundItemPanel.getBackground(),width);
    }    
    nestedPanelBorder=lbNestedPanelBorder.getBorder();
    if(lbNestedPanelBorder.getText().startsWith("Line")) {
         String[] m=lbNestedPanelBorder.getText().split(",");
         int width=(new Integer(m[1].trim())).intValue();
         nestedPanelBorder=BorderFactory.createLineBorder(lbForegroundItemPanel.getBackground(),width);
    }    
    itemPanelitem1.setFont(lbInputFontGlobal.getFont());
    itemPanelitem2.setFont(lbInputFontGlobal.getFont());
    itemPanelitem3.setFont(lbInputFontGlobal.getFont());
    itemPanelitem4.setFont(lbInputFontGlobal.getFont());
    itemPanelitem5.setFont(lbInputFontGlobal.getFont());
    itemPanelitem6.setFont(lbInputFontGlobal.getFont());
    itemPanelitem7.setFont(lbInputFontGlobal.getFont());
    itemPanelitem8.setFont(lbInputFontGlobal.getFont());
    itemPanelitem9.setFont(lbInputFontGlobal.getFont());
    itemPanelitem10.setFont(lbInputFontGlobal.getFont());
    itemPanelitem11.setFont(lbInputFontGlobal.getFont());
    itemPanelitem12.setFont(lbInputFontGlobal.getFont());
    itemPanelitem13.setFont(lbInputFontGlobal.getFont());
    itemPanelitem14.setFont(lbInputFontGlobal.getFont());
    itemPanelitem15.setFont(lbInputFontGlobal.getFont());
    itemPanelitem16.setFont(lbInputFontGlobal.getFont());
    itemPanelitem17.setFont(lbInputFontGlobal.getFont());
    itemPanelitem18.setFont(lbInputFontGlobal.getFont());
    itemPanelitem19.setFont(lbInputFontGlobal.getFont());
    itemPanellabel1.setFont(lbFontGlobal.getFont()); 
    itemPanellabel2.setFont(lbFontGlobal.getFont()); 
    itemPanellabel3.setFont(lbFontGlobal.getFont()); 
    itemPanellabel4.setFont(lbFontGlobal.getFont()); 
    itemPanellabel5.setFont(lbFontGlobal.getFont()); 
    itemPanellabel6.setFont(lbFontGlobal.getFont()); 
    itemPanellabel7.setFont(lbFontGlobal.getFont()); 
    itemPanellabel8.setFont(lbFontGlobal.getFont()); 
    itemPanellabel11.setFont(lbFontGlobal.getFont());
    itemPanellabel12.setFont(lbFontGlobal.getFont());
    itemPanellabel13.setFont(lbFontGlobal.getFont());
    itemPanellabel14.setFont(lbFontGlobal.getFont());
    itemPanellabel15.setFont(lbFontGlobal.getFont());
    itemPanellabel17.setFont(lbFontGlobal.getFont());
    itemPanellabel19.setFont(lbFontGlobal.getFont());
    nestedPanel1.setFont(lbFontGlobal.getFont()); 
    nestedPanel2.setFont(lbFontGlobal.getFont());   
    nestedPanel3.setFont(lbFontGlobal.getFont());
    nestedPanel1.setBorder(nestedPanelBorder); 
    nestedPanel2.setBorder(nestedPanelBorder);   
    nestedPanel3.setBorder(nestedPanelBorder); 
    pnItemPanel1.setBorder(itemPanelBorder);
    pnItemPanel2.setBorder(itemPanelBorder);
    pnItemPanel3.setBorder(itemPanelBorder);
    pnItemPanel4.setBorder(itemPanelBorder);
    pnItemPanel5.setBorder(itemPanelBorder);
    pnItemPanel1.setFont(lbFontGlobal.getFont());
    pnItemPanel2.setFont(lbFontGlobal.getFont());
    pnItemPanel3.setFont(lbFontGlobal.getFont());
    pnItemPanel4.setFont(lbFontGlobal.getFont());
    pnItemPanel5.setFont(lbFontGlobal.getFont());
    tbpItemPanel.setFont(lbFontGlobal.getFont());
    tblContex.setFont(lbInputFontGlobal.getFont());
    scItemPanelPrew.setBackground(lbBackgroundForm.getBackground());
    pnItemPanelPrewiev.setBackground(lbBackgroundForm.getBackground());
    int height=cmbItemSpacing.getSelectedIndex();
    int heightPanel=(new Integer(cmbItemPanelSpacing.getSelectedItem().toString())).intValue();
    if(rdTabbed.isSelected()){
        pnItemPanel3.setVisible(false);
        pnItemPanel4.setVisible(false);
        pnItemPanel5.setVisible(false);
        tbpItemPanel.setVisible(true);
        int i= tbpItemPanel.getWidth()/2;
        int j= tbpItemPanel.getHeight();
        itemPanellabel1.setBounds(5+ height,5+ height, itemPanellabel1.getWidth(), itemPanellabel1.getHeight());
        itemPanelitem1.setBounds(itemPanellabel1.getX()+itemPanellabel1.getWidth(),5+ height, itemPanelitem1.getWidth(), itemPanelitem1.getHeight());
        itemPanellabel2.setBounds(((cmbPLAlignment.getSelectedIndex()==2)?(itemPanelitem1.getWidth()+itemPanelitem1.getX()+5):i) +5+ height ,5+ height, itemPanellabel2.getWidth(), itemPanellabel2.getHeight());
        itemPanelitem2.setBounds(itemPanellabel2.getX()+itemPanellabel2.getWidth(),5+ height, itemPanelitem2.getWidth(), itemPanelitem2.getHeight());  
        itemPanellabel3.setBounds(5+ height +((cmbPLAlignment.getSelectedIndex()==1)?(itemPanellabel1.getWidth()-itemPanellabel3.getWidth()):0),itemPanelitem1.getHeight() + itemPanelitem1.getY()+ height, itemPanellabel3.getWidth(), itemPanellabel3.getHeight());            
        itemPanelitem3.setBounds(((cmbPIAlignment.getSelectedIndex()!=2)?(itemPanellabel1.getWidth()+itemPanellabel1.getX()):(itemPanellabel3.getX()+itemPanellabel3.getWidth())), itemPanelitem1.getHeight() + itemPanelitem1.getY()+ height, itemPanelitem3.getWidth(), itemPanelitem3.getHeight());
        itemPanellabel4.setBounds(((cmbPLAlignment.getSelectedIndex()==2)?(itemPanelitem3.getWidth()+itemPanelitem3.getX()+5):i) +5+height,itemPanelitem1.getHeight() + itemPanelitem1.getY()+ height , itemPanellabel4.getWidth(), itemPanellabel4.getHeight());        
        itemPanelitem4.setBounds(itemPanellabel4.getX()+itemPanellabel4.getWidth(), itemPanelitem1.getHeight() + itemPanelitem1.getY()+ height, itemPanelitem4.getWidth(), itemPanelitem4.getHeight());
        itemPanellabel5.setBounds(5+ height+((cmbPLAlignment.getSelectedIndex()==1)?(itemPanellabel1.getWidth()-itemPanellabel5.getWidth()):0),itemPanelitem4.getHeight() + itemPanelitem4.getY()+ height, itemPanellabel5.getWidth(), itemPanellabel5.getHeight());
        itemPanelitem5.setBounds(((cmbPIAlignment.getSelectedIndex()!=2)?(itemPanellabel1.getWidth()+itemPanellabel1.getX()):(itemPanellabel5.getX()+itemPanellabel5.getWidth())), itemPanelitem4.getHeight() + itemPanelitem4.getY()+ height, itemPanelitem5.getWidth(), itemPanelitem5.getHeight());
        itemPanellabel6.setBounds(10+ height,20+ height, itemPanellabel6.getWidth(), itemPanellabel6.getHeight());      
        if(cmbNPIAlignment.getSelectedIndex()==1)
        {
            itemPanelitem6.setBounds(10+itemPanellabel6.getWidth()+ height,20+ height, itemPanelitem6.getWidth(), itemPanelitem6.getHeight());
            itemPanelitem7.setBounds(10+itemPanellabel7.getWidth()+itemPanelitem6.getWidth()-itemPanelitem7.getWidth()+ height,itemPanelitem6.getHeight() + itemPanelitem6.getY()+ height, itemPanelitem7.getWidth(), itemPanelitem7.getHeight());
        } 
        else
        {
            itemPanelitem6.setBounds(10+itemPanellabel6.getWidth()+ height,20+ height, itemPanelitem6.getWidth(), itemPanelitem6.getHeight());
            itemPanelitem7.setBounds(10+itemPanellabel7.getWidth()+ height,itemPanelitem6.getHeight() + itemPanelitem6.getY()+ height, itemPanelitem7.getWidth(), itemPanelitem7.getHeight());
        }
        itemPanellabel7.setBounds(10+ height,itemPanelitem6.getHeight() + itemPanelitem6.getY()+ height, itemPanellabel7.getWidth(), itemPanellabel7.getHeight()); 
        itemPanellabel8.setBounds(10+ height,20+ height, itemPanellabel8.getWidth(), itemPanellabel8.getHeight());
        itemPanelitem8.setBounds(5+ height+itemPanellabel8.getWidth(),20+ height, itemPanelitem8.getWidth(), itemPanelitem8.getHeight());
        itemPanelitem9.setBounds(5+ height+itemPanellabel8.getWidth(),itemPanelitem8.getHeight() + itemPanelitem8.getY(), itemPanelitem9.getWidth(), itemPanelitem9.getHeight());
        tblContex.setBackground(lbBackgroundInput.getBackground());
        tblContex.setForeground(lbForegroundInput.getBackground()); 
        tblContex.getColumnModel().getColumn(0).setHeaderRenderer(new MyHeaderRenderer(1)); 
        tbpItemPanel.setBackground(lbBackgroundItemPanel.getBackground());
        tbpItemPanel.setForeground(lbForegroundItemPanel.getBackground());
        pnItemPanel1.setBackground(lbBackgroundItemPanel.getBackground());
        pnItemPanel1.setForeground(lbForegroundItemPanel.getBackground());
        pnItemPanel2.setBackground(lbBackgroundItemPanel.getBackground());
        pnItemPanel2.setForeground(lbForegroundItemPanel.getBackground());
        if(chContex.isSelected()) {
            scpContex.setVisible(true);
            if(cmbContex.getSelectedIndex()==0){
            int h=rdContexHeight.isSelected()?((new Integer(cmbContexHeight.getSelectedItem().toString())).intValue()):tbpItemPanel.getHeight();
            scpContex.setBounds(5,5, scpContex.getWidth(), h);
            tblContex.setBounds(0,0, tblContex.getWidth(), h);
            tbpItemPanel.setBounds(5+height +scpContex.getWidth(),5,400+cmbItemPanelSpacing.getSelectedIndex()*4,160+cmbItemPanelSpacing.getSelectedIndex()*2);
            pnItemPanelPrewiev.setPreferredSize(new Dimension(tbpItemPanel.getWidth() + scpContex.getWidth()+5+template.itemPanel.spacing, 5 + Math.max(scpContex.getHeight(),tbpItemPanel.getHeight())));
            pnItemPanelPrewiev.setSize(new Dimension(tbpItemPanel.getWidth() + scpContex.getWidth()+5+height, 5 + Math.max(scpContex.getHeight(),tbpItemPanel.getHeight())));
            }
            else{
            int h=rdContexHeight.isSelected()?((new Integer(cmbContexHeight.getSelectedItem().toString())).intValue()):75;
            scpContex.setBounds(5,5, scpContex.getWidth(), h);
            tblContex.setBounds(0,0, tblContex.getWidth(),h);
            tbpItemPanel.setBounds(5,height +h+5,400+cmbItemPanelSpacing.getSelectedIndex()*4,160+cmbItemPanelSpacing.getSelectedIndex()*2);
            pnItemPanelPrewiev.setPreferredSize(new Dimension(tbpItemPanel.getWidth()+5, 10 + scpContex.getHeight() + tbpItemPanel.getHeight()));
            pnItemPanelPrewiev.setSize(new Dimension(tbpItemPanel.getWidth()+5, 10 + scpContex.getHeight() + tbpItemPanel.getHeight()));
            }
        }
        else {
            scpContex.setVisible(false);
            tbpItemPanel.setBounds(5,5,400+cmbItemPanelSpacing.getSelectedIndex()*4,160+cmbItemPanelSpacing.getSelectedIndex()*2);
            pnItemPanelPrewiev.setPreferredSize(new Dimension(tbpItemPanel.getWidth()+10, 5 + tbpItemPanel.getHeight()));
            pnItemPanelPrewiev.setSize(new Dimension(tbpItemPanel.getWidth()+10, 5 + tbpItemPanel.getHeight()));
        }
        itemPanellabel1.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel2.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel3.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel4.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel5.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel6.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel7.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel8.setForeground(lbForegroundItemPanel.getBackground());
        itemPanelitem1.setForeground(lbForegroundInput.getBackground());
        itemPanelitem2.setForeground(lbForegroundInput.getBackground());
        itemPanelitem3.setForeground(lbForegroundInput.getBackground());
        itemPanelitem4.setForeground(lbForegroundInput.getBackground());
        itemPanelitem5.setForeground(lbForegroundInput.getBackground());
        itemPanelitem6.setForeground(lbForegroundInput.getBackground());
        itemPanelitem7.setForeground(lbForegroundInput.getBackground());
        itemPanelitem8.setForeground(lbForegroundInput.getBackground());
        itemPanelitem9.setForeground(lbForegroundInput.getBackground());
        itemPanelitem10.setForeground(lbForegroundInput.getBackground());
        itemPanelitem1.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem2.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem3.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem4.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem5.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem6.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem7.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem8.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem9.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem10.setBackground(lbBackgroundInput.getBackground());
        nestedPanel1.setBorder(nestedPanelBorder);
        nestedPanel2.setBorder(nestedPanelBorder);
        nestedPanel1.setBounds(5+height, 5+height,170+cmbItemSpacing.getSelectedIndex()*2, Math.max(itemPanelitem9.getHeight() + itemPanelitem9.getY(),itemPanelitem7.getHeight() + itemPanelitem7.getY()) + height);
        nestedPanel2.setBounds(nestedPanel1.getX()+nestedPanel1.getWidth()+height, 5+height,nestedPanel1.getWidth(),  Math.max(itemPanelitem9.getHeight() + itemPanelitem9.getY(),itemPanelitem7.getHeight() + itemPanelitem7.getY())+ height);
        tbpItemPanel.setBounds(tbpItemPanel.getX(), tbpItemPanel.getY(),nestedPanel1.getWidth()+nestedPanel2.getWidth()+height*3+20,itemPanelitem5.getHeight() + itemPanelitem5.getY()+height+30+template.labelFont.font.getSize());
        pnItemPanelPrewiev.setPreferredSize(new Dimension(tbpItemPanel.getX()+tbpItemPanel.getWidth()+10,tbpItemPanel.getY() + tbpItemPanel.getHeight()+10));
        pnItemPanelPrewiev.setSize(new Dimension(tbpItemPanel.getX()+tbpItemPanel.getWidth()+10,tbpItemPanel.getY() + tbpItemPanel.getHeight()+10));
    }
    else {
        pnItemPanel3.setVisible(true);
        pnItemPanel4.setVisible(true);
        pnItemPanel5.setVisible(true);
        tbpItemPanel.setVisible(false);
        scpContex.setVisible(false);
        pnItemPanel3.setBackground(lbBackgroundItemPanel.getBackground());
        pnItemPanel3.setForeground(lbForegroundItemPanel.getBackground());
        pnItemPanel4.setBackground(lbBackgroundItemPanel.getBackground());
        pnItemPanel4.setForeground(lbForegroundItemPanel.getBackground());
        pnItemPanel5.setBackground(lbBackgroundItemPanel.getBackground());
        pnItemPanel5.setForeground(lbForegroundItemPanel.getBackground());
        nestedPanel3.setBackground(lbBackgroundItemPanel.getBackground());
        nestedPanel3.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel11.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel12.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel13.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel14.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel15.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel17.setForeground(lbForegroundItemPanel.getBackground());
        itemPanellabel19.setForeground(lbForegroundItemPanel.getBackground());
        itemPanelitem11.setForeground(lbForegroundInput.getBackground());
        itemPanelitem12.setForeground(lbForegroundInput.getBackground());
        itemPanelitem13.setForeground(lbForegroundInput.getBackground());
        itemPanelitem14.setForeground(lbForegroundInput.getBackground());
        itemPanelitem15.setForeground(lbForegroundInput.getBackground());
        itemPanelitem16.setForeground(lbForegroundInput.getBackground());
        itemPanelitem17.setForeground(lbForegroundInput.getBackground());
        itemPanelitem18.setForeground(lbForegroundInput.getBackground());
        itemPanelitem19.setForeground(lbForegroundInput.getBackground());
        itemPanelitem11.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem12.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem13.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem14.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem15.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem16.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem17.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem18.setBackground(lbBackgroundInput.getBackground());
        itemPanelitem19.setBackground(lbBackgroundInput.getBackground());
        if(cmbPLAlignment.getSelectedIndex()==1) {
            itemPanellabel11.setBounds(5+height,5+height, itemPanellabel11.getWidth(), itemPanellabel11.getHeight());
            itemPanellabel12.setBounds(5+height +itemPanellabel11.getWidth()-itemPanellabel12.getWidth(),itemPanelitem11.getHeight() + itemPanelitem11.getY()+ height, itemPanellabel12.getWidth(), itemPanellabel12.getHeight());
            itemPanellabel13.setBounds(5+height+itemPanellabel11.getWidth()-itemPanellabel13.getWidth(),itemPanelitem12.getHeight() + itemPanelitem12.getY()+ height, itemPanellabel13.getWidth(), itemPanellabel13.getHeight());                      
        }
        else {
            itemPanellabel11.setBounds(5+height,5+height, itemPanellabel11.getWidth(), itemPanellabel11.getHeight());
            itemPanellabel12.setBounds(5+height,itemPanelitem11.getHeight() + itemPanelitem11.getY()+ height, itemPanellabel12.getWidth(), itemPanellabel12.getHeight());
            itemPanellabel13.setBounds(5+height,itemPanelitem12.getHeight() + itemPanelitem12.getY()+ height, itemPanellabel13.getWidth(), itemPanellabel13.getHeight());          
        }
        if(cmbPIAlignment.getSelectedIndex()==0 || cmbPIAlignment.getSelectedIndex()==1)
        {
        itemPanelitem11.setBounds(itemPanellabel11.getX() + itemPanellabel11.getWidth(),5+height, itemPanelitem11.getWidth(), itemPanelitem11.getHeight());
        itemPanelitem12.setBounds(itemPanellabel11.getX() + itemPanellabel11.getWidth(),itemPanelitem11.getHeight() + itemPanelitem11.getY()+ height, itemPanelitem12.getWidth(), itemPanelitem12.getHeight());
        itemPanelitem13.setBounds(itemPanellabel11.getX() + itemPanellabel11.getWidth(),itemPanelitem12.getHeight() + itemPanelitem12.getY()+ height, itemPanelitem13.getWidth(), itemPanelitem13.getHeight());
        }
        else
        {
        itemPanelitem11.setBounds(itemPanellabel11.getX() + itemPanellabel11.getWidth(),5+height, itemPanelitem11.getWidth(), itemPanelitem11.getHeight());
        itemPanelitem12.setBounds(itemPanellabel12.getX() + itemPanellabel12.getWidth(),itemPanelitem11.getHeight() + itemPanelitem11.getY()+ height, itemPanelitem12.getWidth(), itemPanelitem12.getHeight());
        itemPanelitem13.setBounds(itemPanellabel13.getX() + itemPanellabel13.getWidth(),itemPanelitem12.getHeight() + itemPanelitem12.getY()+ height, itemPanelitem13.getWidth(), itemPanelitem13.getHeight());
        } 
        if(cmbPIAlignment.getSelectedIndex()==1)
        {
            itemPanelitem14.setBounds(5+height+itemPanellabel4.getWidth()+itemPanelitem15.getWidth()-itemPanelitem14.getWidth(),5+height, itemPanelitem14.getWidth(), itemPanelitem14.getHeight());
            itemPanelitem15.setBounds(5+height+itemPanellabel5.getWidth(),itemPanelitem14.getHeight() + itemPanelitem14.getY()+ height, itemPanelitem15.getWidth(), itemPanelitem15.getHeight());            
        }
        else
        {
            itemPanelitem14.setBounds(5+height+itemPanellabel4.getWidth(),5+height, itemPanelitem14.getWidth(), itemPanelitem14.getHeight());
            itemPanelitem15.setBounds(5+height+itemPanellabel5.getWidth(),itemPanelitem14.getHeight() + itemPanelitem14.getY()+ height, itemPanelitem15.getWidth(), itemPanelitem15.getHeight());
        }
        itemPanellabel14.setBounds(5+height,5+height, itemPanellabel14.getWidth(), itemPanellabel14.getHeight());
        itemPanellabel15.setBounds(5+height,itemPanelitem14.getHeight() + itemPanelitem14.getY()+ height, itemPanellabel15.getWidth(), itemPanellabel15.getHeight());
        itemPanelitem19.setBounds(5+height+itemPanellabel19.getWidth(),5+height, itemPanelitem19.getWidth(), itemPanelitem19.getHeight());
        itemPanellabel19.setBounds(5+height,5+height, itemPanellabel19.getWidth(), itemPanellabel19.getHeight());
        itemPanellabel14.setBounds(5+height,5+height, itemPanellabel19.getWidth(), itemPanellabel19.getHeight());
        nestedPanel3.setBounds(5+height,itemPanelitem19.getHeight() + itemPanelitem19.getY()+ height, 155+2*height, nestedPanel3.getHeight());
        int permWidth=Math.max(400+(cmbOrientation.getSelectedIndex()==2?50:0) +8*height+40,3*150+2*heightPanel);
        int permHeight[]={0,0,0};
        permHeight[0]=itemPanelitem13.getHeight() + itemPanelitem13.getY()+ 10+height;
        permHeight[1]=itemPanelitem15.getHeight() + itemPanelitem15.getY()+ 10+height;
        permHeight[2]=nestedPanel3.getHeight() + nestedPanel3.getY()+ 10+height;
    if(cmbOrientation.getSelectedIndex()==0) {
       pnItemPanel3.setBounds(10,10,permWidth,permHeight[0]); 
       pnItemPanel4.setBounds(10,10+heightPanel+pnItemPanel3.getHeight(),permWidth,permHeight[1]);
       pnItemPanel5.setBounds(10,10+heightPanel + heightPanel +pnItemPanel3.getHeight()+pnItemPanel4.getHeight(),permWidth,permHeight[2]);
       pnItemPanelPrewiev.setPreferredSize(new Dimension(pnItemPanel5.getWidth()+20,pnItemPanel5.getY() + pnItemPanel5.getHeight()+10));
       pnItemPanelPrewiev.setSize(new Dimension(pnItemPanel5.getWidth()+20,pnItemPanel5.getY() + pnItemPanel5.getHeight()+10));
    }
    else if(cmbOrientation.getSelectedIndex()==1) {
       pnItemPanel3.setBounds(10,10,(permWidth- heightPanel)/2,Math.max(permHeight[0],permHeight[1])); 
       pnItemPanel4.setBounds(10+pnItemPanel3.getWidth()+heightPanel,10,(permWidth- heightPanel)/2,Math.max(permHeight[0],permHeight[1]));
       pnItemPanel5.setBounds(10,10+heightPanel+pnItemPanel3.getHeight(),permWidth,permHeight[2]);
       pnItemPanelPrewiev.setPreferredSize(new Dimension(pnItemPanel5.getWidth()+20,pnItemPanel5.getY() + pnItemPanel5.getHeight()+10));
       pnItemPanelPrewiev.setSize(new Dimension(pnItemPanel5.getWidth()+20,pnItemPanel5.getY() + pnItemPanel5.getHeight()+10));
    }
    else if(cmbOrientation.getSelectedIndex()==2) {
        pnItemPanel3.setBounds(10,10,(permWidth- heightPanel)/3+20,Math.max(Math.max(permHeight[0],permHeight[1]),permHeight[2])); 
        pnItemPanel4.setBounds(10+pnItemPanel3.getWidth()+heightPanel,10,(permWidth- heightPanel)/3+20,Math.max(Math.max(permHeight[0],permHeight[1]),permHeight[2]));
        pnItemPanel5.setBounds(10+pnItemPanel3.getWidth()+pnItemPanel4.getWidth()+2*heightPanel,10,(permWidth- heightPanel)/3+20,Math.max(Math.max(permHeight[0],permHeight[1]),permHeight[2]));
        pnItemPanelPrewiev.setPreferredSize(new Dimension(3*pnItemPanel3.getWidth()+2*heightPanel+20,pnItemPanel5.getY() + pnItemPanel5.getHeight()+10));
        pnItemPanelPrewiev.setSize(new Dimension(3*pnItemPanel3.getWidth()+2*heightPanel+20,pnItemPanel5.getY() + pnItemPanel5.getHeight()+10));
    }    
    }
    }

    private void change_item_panel(PropertyChangeEvent e) {
        change_item_panel();
        change(e);
    }


    private void lbScreen_mouseMoved(MouseEvent e) {
    if(Math.abs(e.getX()-xPos*lbScreen.getWidth()/100)<10 && Math.abs(e.getY()-yPos*lbScreen.getHeight()/100)<10 && e.getX()>=0 && e.getY()>=0 && e.getX()<lbScreen.getWidth() && e.getY()<lbScreen.getHeight())
        lbScreen.setCursor(new Cursor(Cursor.MOVE_CURSOR));
    else
        lbScreen.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void jTabbedPane1_mouseClicked(MouseEvent e) {
        boolean isEnabled=btnApply.isEnabled();
        int index=jTabbedPane1.getSelectedIndex();
        if(index==2)
            change_table();
        else if(index==3)
            change_item_panel();
        else if(index==4)
            change_button();
        else if(index==5)
            change_itemType();
        btnSave.setEnabled(isEnabled);
        btnApply.setEnabled(isEnabled);
    }

    private void appl_ActionPerformed(ActionEvent e) {
        save();
    }

    private void setBorderStyle(ActionEvent e) {
    }

    private void change_itemType(PropertyChangeEvent e) {
        change_itemType();
        change(e);
    }

    private void rdNo_propertyChange(PropertyChangeEvent e) {
    }

    private void rdNo_actionPerformed(ActionEvent e) {
    }

    private void subFormCall(ItemEvent e) {
    if(!rdMenuCall.isSelected() && !rdButtonCall.isSelected() && !rdKeyCall.isSelected())
        rdMenuCall.setSelected(true);
    if(rdKeyCall.isSelected() && !rdMenuCall.isSelected())rdMenuCall.setSelected(true);
    change(e);
    }

    private void showTitle(ItemEvent e) {
        if(chTitle.isSelected()) {
            cmbFormTitle.setEnabled(true);
            lbFontTitle.setEnabled(true);
            btnFontTitle.setEnabled(true);
        }
        else{
            cmbFormTitle.setEnabled(false);
            lbFontTitle.setEnabled(false);
            btnFontTitle.setEnabled(false);
        }
        
        change_form(e);
    }

    private void pnTablePrewiev_propertyChange(PropertyChangeEvent e) {

        if(e.getPropertyName().equals(JSplitPane.LAST_DIVIDER_LOCATION_PROPERTY) ||e.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY))
        {
            int cur=pnTablePrewiev.getDividerLocation();
            table_resized(cur);          
            
        }
    }

    private void pnTablePrewiev_mouseReleased(MouseEvent e) {
    }

  private void table_resized(int current){
     if(current>table.getTableHeader().getHeight() && canTable && canTableResized){  
         canTable=false;
         canTableResized=false;
          int s=0;
          int sc=0;
          int row=0;
         if(cmbColumnWidth.getSelectedIndex()>105)sc=20;
          if(cmbOverflow.getSelectedIndex()==0)
              s=(int)(552-current);
          else
          {
              s=(int)(cmbOverflowSize.getSelectedIndex()+1);
              row=(current-table.getTableHeader().getHeight()-sc )/((new Integer(cmbRowHeight.getSelectedItem().toString())).intValue());
              if(row>20)row=20;
              cmbVisibleRows.setSelectedItem(""+row);
          }        
          if(s>300)s=300;
          cmbOverflowSize.setSelectedItem(""+s);
          if(chOverflow.isSelected())
          {
            int div;
              if(cmbOverflow.getSelectedIndex()==0)
                div=552-s;
              else
                div=(new Integer(cmbRowHeight.getSelectedItem().toString())).intValue()*((new Integer(cmbVisibleRows.getSelectedItem().toString())).intValue())+sc+table.getTableHeader().getHeight();
              pnTablePrewiev.setDividerLocation(div);
          }
          canTable=true;
         canTableResized=true;
     }
  }

    private void pnTablePrewiev_mouseClicked(MouseEvent e) {
    }

    private void change_table1() {
        
        if(chOverflow.isSelected())
        {
            if(cmbOverflow.getSelectedIndex()==0)
            {  
                pnTablePrewiev.setDividerLocation(552-(new Integer(cmbOverflowSize.getSelectedItem().toString())));
            }  
            else
            {
                change_table();
            }
        }
        else
            change_table();
    }

    private void change_table1(ItemEvent e) {
        change(e);
        change_table1();
    }


    private void this_windowOpened(WindowEvent e) {
        btnSave.setEnabled(false);
        btnApply.setEnabled(false);
    }

    private void jPanel1_componentShown(ComponentEvent e) {
        boolean isEnabled=btnApply.isEnabled();
        change_table();
        btnSave.setEnabled(isEnabled);
        btnApply.setEnabled(isEnabled);
    }

    private void rdContexHeight_actionPerformed(ActionEvent e) {
    }

    class MyFilter extends FileFilter {
       private String type;
        public boolean accept(File file) {
            String filename = file.getName();
            return filename.endsWith("."+type);
        }
        MyFilter(String t)
        {
          type=t;
        }
        public String getDescription() {
            return "*."+type;
        }
    }

  private void setButtons()
  {
  if(jTabbedPane1.getSelectedIndex()==0)
  {
    btnBack.setEnabled(false);
    btnNext.setEnabled(true);
  }
  else if(jTabbedPane1.getSelectedIndex()==1 || jTabbedPane1.getSelectedIndex()==2 || jTabbedPane1.getSelectedIndex()==3 || jTabbedPane1.getSelectedIndex()==4)
  {
    btnBack.setEnabled(true);
    btnNext.setEnabled(true);
  }
  else if(jTabbedPane1.getSelectedIndex()==5)
  {
    btnBack.setEnabled(true);
    btnNext.setEnabled(false);
  }
  }
  
  private void Center(ActionEvent e)
  {
  }

  private void setScreen()
  {
    lbScreen.repaint();
  }

  private void lbForeground2_mouseDragged(MouseEvent e)
  {
      if(rdCustom.isSelected() && rdFixedSize.isSelected())
      {
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      if(Math.abs(e.getX()-xPos*lbScreen.getWidth()/100)<10 && Math.abs(e.getY()-yPos*lbScreen.getHeight()/100)<10 && e.getX()>=0 && e.getY()>=0 && e.getX()<lbScreen.getWidth() && e.getY()<lbScreen.getHeight())
      {
        xPos=e.getX()*100/lbScreen.getWidth();
        yPos=e.getY()*100/lbScreen.getHeight();
        lbScreen.repaint();
      }
      if(template.group>0)
      {
        btnSave.setEnabled(true);
        btnApply.setEnabled(true);
      }
      }
  }
  
 class SpecialTableModel extends DefaultTableModel
{
public boolean isCellEditable(int row, int column) {
    return false;
 } 

}


  private void screen(ItemEvent e)
  {
  screen();  
  change(e);  
  } 
  private void screen()
  {
      if(rdFullScreen.isSelected())
        {
          txtX.setEnabled(false);
          txtY.setEnabled(false);
          rdCustom.setEnabled(false);
          rdLeftonTop.setEnabled(false);
          rdCenter.setEnabled(false);
          lbScreen.setEnabled(false);
          lbX.setEnabled(false);
          lbY.setEnabled(false);
        }
      else
        {
          txtX.setEnabled(true);
          txtY.setEnabled(true);
          rdCustom.setEnabled(true);
          rdLeftonTop.setEnabled(true);
          rdCenter.setEnabled(true);
          lbScreen.setEnabled(true);
          lbX.setEnabled(true);
          lbY.setEnabled(true);
        }    
  }
 private void change(ItemEvent e)
  {
      if(template.group>0)
      { 
          btnSave.setEnabled(true);
          btnApply.setEnabled(true);
      }
  }

  private void change(PropertyChangeEvent e)
  {
        if(template.group>0)
        {
            btnSave.setEnabled(true);
            btnApply.setEnabled(true);
        }
  }

  private void rdFixedSize_actionPerformed(ActionEvent e)
  {
  }

  private void change(KeyEvent e)
  {
      if(template.group>0)
      {
          btnSave.setEnabled(true);
          btnApply.setEnabled(true);
      }
  }

  private void change_screeen(ItemEvent e)
  {
    change_screeen();
    change(e);  
  }
  private void change_screeen()
  {
    if(rdCustom.isSelected())
      {    
        lbX.setVisible(true);
        lbY.setVisible(true);
      }
    else
      {
        lbX.setVisible(false);
        lbY.setVisible(false);
      } 
    if(rdLeftonTop.isSelected())
      {
        xPos=6;
        yPos=10;
      }
    else if(rdCenter.isSelected())
      {
        xPos=50;
        yPos=50;
      }
    lbScreen.repaint(); 
  }

  private void change_desktop(PropertyChangeEvent e)
  {
    lbScreen.repaint();
    change(e);
  }

  private void change_form(ItemEvent e)
  {
  change_form();
  change(e);
  }

  private void change_form(PropertyChangeEvent e)
  {
  change_form();
  change(e);
  }
  
  private void change_form()
  {
    lbContent.setVisible(false);
    lbForm.setBackground(lbBackgroundForm.getBackground());
    lbForm.setForeground(lbForegroundForm.getBackground());
    lbForm.setFont(lbFontTitle.getFont());
    if(chTitle.isSelected())
    {
      lbForm.setText(" Form title ");
      int align=JLabel.LEFT;
      if(cmbFormTitle.getSelectedIndex()==1)
        align=JLabel.CENTER;
      else if(cmbFormTitle.getSelectedIndex()==2)
        align=JLabel.RIGHT;
      lbForm.setHorizontalAlignment(align);
      lbForm.setVerticalAlignment(JLabel.TOP);
    }
    else
      lbForm.setText("");
      lbContent.setVisible(true);
  }
 
  class myScreen extends JLabel
{
 public void paint(Graphics g) {
 
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    g.setColor(lbDesktop.getBackground());
    g.fillRect(0, 0, lbScreen.getWidth(),lbScreen.getHeight());
    g.setColor(SystemColor.white);
    int x=xPos*lbScreen.getWidth()/100;
    int y=yPos*lbScreen.getHeight()/100;
    g.drawImage(imageScreenForm.getImage(), x-10, y-10,this);
    lbX.setText("X: "+ (int)(xPos*d.getWidth()/100));
    lbY.setText("Y: "+ (int)(yPos*d.getWidth()/100));
 } 
}

  private void change_table(ItemEvent e)
  {    
    change(e);
    if(e.getStateChange()==1)
        change_table();
  }

  private void change_table(PropertyChangeEvent e)
  { 
    change(e);
    change_table();
  }

  private void change_table()
  {
  if(!canTable)return;
    canTable=false;
    scTablePrewiev.setVisible(false);
    table.addMouseListener(new MouseAdapter() {
              public void mouseClicked(MouseEvent e) {
                  table_mouseClicked(e);
              }
          });
    tableBorder=lbTableBorder.getBorder();
    if(lbTableBorder.getText().startsWith("Line")) {
            String[] m=lbTableBorder.getText().split(",");
            int width=(new Integer(m[1].trim())).intValue();
            tableBorder=BorderFactory.createLineBorder(lbForegroundTablePanel.getBackground(),width);
    }
    table.setIntercellSpacing(new Dimension(0,0));
    for(int i=0; i<table.getColumnCount(); i++){
    TableColumn column =table.getColumnModel().getColumn(i);
    column.setCellRenderer(new DefaultTableCellRenderer() {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
          Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
          TableColumn tc = table.getColumnModel().getColumn(column);
          setBorder(tableBorder);
          if(isSelected) {
              renderer.setBackground(table.getSelectionBackground());
              renderer.setForeground(table.getSelectionForeground());   
          }
          else
          {
            renderer.setForeground(lbForegroundCell.getBackground());
            renderer.setBackground(lbBackgroundCell.getBackground());}
          return renderer;
        }
      });
    }
      tblContex.setIntercellSpacing(new Dimension(0,0));
      for(int i=0; i<tblContex.getColumnCount(); i++){
      TableColumn column =tblContex.getColumnModel().getColumn(i);
      column.setCellRenderer(new DefaultTableCellRenderer() {
          public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            TableColumn tc = table.getColumnModel().getColumn(column);
            setBorder(tableBorder);
              if(isSelected) {
                  renderer.setBackground(table.getSelectionBackground());
                  renderer.setForeground(table.getSelectionForeground());   
              }
              else
              {
                renderer.setForeground(lbForegroundCell.getBackground());
                renderer.setBackground(lbBackgroundCell.getBackground());}
            return renderer;
          }
        });
      }
    table.setBorder(tableBorder);
    tableScrollPane.setBorder(tableBorder);
    lbOverflow.setBorder(tableBorder);
    scTablePrewiev.setBackground(lbBackgroundForm.getBackground());
    scTablePrewiev.getViewport().setBackground(lbBackgroundForm.getBackground());
    pnTablePrewiev.setBackground(lbBackgroundForm.getBackground());
    table.setBackground(lbBackgroundTablePanel.getBackground());
    table.setForeground(lbForegroundTablePanel.getBackground());
    lbOverflow.setForeground(lbForegroundCell.getBackground());
    lbOverflow.setBackground(lbBackgroundCell.getBackground());
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    for (int i=0; i<table.getColumnCount(); i++) {
      table.getColumnModel().getColumn(i).setPreferredWidth((new Integer(cmbColumnWidth.getSelectedItem().toString())).intValue());
      table.getColumnModel().getColumn(i).setMaxWidth((new Integer(cmbColumnWidth.getSelectedItem().toString())).intValue());
      table.getColumnModel().getColumn(i).setMinWidth((new Integer(cmbColumnWidth.getSelectedItem().toString())).intValue());
      table.getColumnModel().getColumn(i).setWidth((new Integer(cmbColumnWidth.getSelectedItem().toString())).intValue());
      table.getColumnModel().getColumn(i).setHeaderRenderer(new MyHeaderRenderer(0)); 
    }
    table.setRowHeight((new Integer(cmbRowHeight.getSelectedItem().toString())).intValue());
    table.getTableHeader().setBackground(lbBackgroundTablePanel.getBackground());
    table.getTableHeader().setForeground(lbForegroundTablePanel.getBackground());
    table.getTableHeader().setBorder(tableBorder);
    table.setBorder(tableBorder);
    tblContex.getTableHeader().setBackground(lbBackgroundTablePanel.getBackground());
    tblContex.getTableHeader().setForeground(lbForegroundTablePanel.getBackground());
    tblContex.getTableHeader().setBorder(tableBorder);
    tblContex.setBorder(tableBorder);
    tblContex.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblContex.getSelectionModel().setSelectionInterval(0,0);
    tblContex.setRowHeight((new Integer(cmbRowHeight.getSelectedItem().toString())).intValue());
    int width=552;
    int size=(new Integer(cmbOverflowSize.getSelectedItem().toString())).intValue();
    int sc=0;
    if(cmbColumnWidth.getSelectedIndex()>105)sc=20;
    Dimension dm=new Dimension(width-(chOverflow.isSelected()&&cmbOverflow.getSelectedIndex()==0?size:0), (new Integer(cmbRowHeight.getSelectedItem().toString())).intValue()*((new Integer(cmbVisibleRows.getSelectedItem().toString())).intValue())+sc+table.getTableHeader().getHeight());
    tableScrollPane.setPreferredSize(dm);
    tableScrollPane.setSize(dm);
    tableScrollPane.setMinimumSize(new Dimension(1,table.getTableHeader().getHeight() +sc+(new Integer(cmbRowHeight.getSelectedItem().toString())).intValue()));
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.getSelectionModel().setSelectionInterval(0,0);      
    Dimension dim1=new Dimension(width, (int)dm.getHeight() +((chOverflow.isSelected()&&cmbOverflow.getSelectedIndex()==0)?0:(size+2)));
    pnTablePrewiev.setPreferredSize(dim1);
    pnTablePrewiev.setSize(dim1);
    pnTablePrewiev.setBorder(BorderFactory.createEmptyBorder());
    if(chOverflow.isSelected())
    {
        pnOverflow.setFont(new Font("SansSerif", 0, 11));
        pnTablePrewiev.setOneTouchExpandable(false);
        pnTablePrewiev.setDividerSize(2);
        lbOverflow.setText("This is overflow area");
        table.setFont(lbInputFontGlobal.getFont());
        lbOverflow.setFont(lbInputFontGlobal.getFont());     
        pnOverflow.setMinimumSize(new Dimension(1,1));
        pnOverflow.setPreferredSize(new Dimension(size, (int)dm.getHeight()));
        pnOverflow.setSize(new Dimension(size, (int)dm.getHeight()));
        pnTablePrewiev.remove(tableScrollPane);
        pnTablePrewiev.remove(pnOverflow);
        pnTablePrewiev.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        pnTablePrewiev.setLeftComponent(tableScrollPane);
        pnTablePrewiev.setRightComponent(pnOverflow);  
        if(cmbOverflow.getSelectedIndex()==0)
            pnTablePrewiev.setDividerLocation((int)dm.getWidth());
     if(cmbOverflow.getSelectedIndex()==1)
     {
        pnOverflow.setMinimumSize(new Dimension(1,1));
        pnOverflow.setPreferredSize(new Dimension((int)dm.getWidth(),(new Integer(cmbOverflowSize.getSelectedItem().toString())).intValue()));
        pnOverflow.setSize(new Dimension((int)dm.getWidth(),(new Integer(cmbOverflowSize.getSelectedItem().toString())).intValue()));
        pnTablePrewiev.remove(tableScrollPane);
        pnTablePrewiev.remove(pnOverflow);
        pnTablePrewiev.setOrientation(JSplitPane.VERTICAL_SPLIT);
        pnTablePrewiev.setLeftComponent(tableScrollPane);
        pnTablePrewiev.setRightComponent(pnOverflow);
        pnTablePrewiev.resetToPreferredSizes();
        pnTablePrewiev.setDividerLocation((int)dm.getHeight());
      }
        
    }
    else
    {
        pnOverflow.setMinimumSize(new Dimension(0,0));
        pnOverflow.setPreferredSize(new Dimension(0,0));
        pnOverflow.setSize(new Dimension(0,0));
        pnTablePrewiev.setTopComponent(tableScrollPane);
        pnTablePrewiev.setBottomComponent(pnOverflow);   
        pnTablePrewiev.setPreferredSize(new Dimension(width, (int)dm.getHeight()));
        pnTablePrewiev.setSize(new Dimension(width,(int)dm.getHeight()));    
        tableScrollPane.setPreferredSize(new Dimension(width, (int)dm.getHeight()));
        tableScrollPane.setSize(new Dimension(width,(int)dm.getHeight()));
        pnTablePrewiev.setDividerSize(0);
    }
     scTablePrewiev.setVisible(true);
     canTable=true;
  }

 class MyHeaderRenderer extends JLabel implements 
             TableCellRenderer, Serializable 
{ 
     public  MyHeaderRenderer(int i) 
     { 
            this.setBorder(lbTableBorder.getBorder()); 
            this.setForeground(lbForegroundTablePanel.getBackground()); 
            this.setBackground(lbBackgroundTablePanel.getBackground()); 
            this.setFont(lbFontGlobal.getFont());
            
     } 
     public Component getTableCellRendererComponent(JTable table, 
             Object value, boolean isSelected, boolean hasFocus, int row, 
             int column) 
     { 
             this.setText(value.toString()); 
             return this; 
     } 

}
  public class CustomTableCellRenderer extends DefaultTableCellRenderer 
{
    int br;
    public CustomTableCellRenderer(int i) {
       super();
       br=i;
    }
    public Component getTableCellRendererComponent
       (JTable table, Object value, boolean isSelected,
       boolean hasFocus, int row, int column) 
    {
        Component cell = super.getTableCellRendererComponent
           (table, value, isSelected, hasFocus, row, column);        
        if(br==0){
            cell.setBackground(lbBackgroundCell.getBackground());
            cell.setForeground(lbForegroundCell.getBackground());
            if(table.getSelectedRow()==row)
                {
                    cell.setBackground(table.getSelectionBackground());
                    cell.setForeground(table.getSelectionForeground());
                }
        }
        else{
            cell.setBackground(lbBackgroundInput.getBackground());
            cell.setForeground(lbForegroundInput.getBackground());
            if(table.getSelectedRow()==row)
                {
                    cell.setBackground(table.getSelectionBackground());
                    cell.setForeground(table.getSelectionForeground());
                }
        }
        return cell;
 
    }
}
  private void table_mouseClicked(MouseEvent e)
  {
  lbOverflow.setText("This is overflow value area");
  }
  private void change_button(ItemEvent e)
  {
    change_button();
    change(e);
  }
  private void change_button_att(JButton button, String sort)
  {
    button.setOpaque(true);
    button.setFont(lbFontGlobal.getFont());
    button.setContentAreaFilled(false);
    if(rdTextual.isSelected())
    {
      button.setSize(75,30);
      button.setPreferredSize(new Dimension(75,30));
      button.setMaximumSize(new Dimension(75,30));
      button.setMinimumSize(new Dimension(75,30));
      if(sort.equals("New"))
        button.setText("New");
      else if(sort.equals("Delete"))
        button.setText("Delete");
      else if(sort.equals("Delete all"))
        button.setText("Delete all");
      else if(sort.equals("Duplicate"))
        button.setText("Duplicate");
      else if(sort.equals("OK"))
        button.setText("OK");
      else if(sort.equals("Cancel"))
        button.setText("Cancel");
      else if(sort.equals("Apply"))
        button.setText("Apply");
      else if(sort.equals("Previos"))
        button.setText("Previos");
      else if(sort.equals("Next"))
        button.setText("Next");
      else if(sort.equals("First"))
        button.setText("First");
      else if(sort.equals("Last"))
        button.setText("Last");
      button.setIcon(null);
    }
    else
      {
      button.setSize(30,30);
      button.setPreferredSize(new Dimension(30,30));
      button.setMaximumSize(new Dimension(30,30));
      button.setMinimumSize(new Dimension(30,30));
      button.setText("");
      if(sort.equals("New"))
        button.setIcon(imageNew);
      else if(sort.equals("Delete"))
        button.setIcon(imageDelete);
      else if(sort.equals("Delete all"))
        button.setIcon(imageDeleteAll);
      else if(sort.equals("Duplicate"))
        button.setIcon(imageDuplicate);
      else if(sort.equals("OK"))
        button.setIcon(imageOK);
      else if(sort.equals("Cancel"))
        button.setIcon(imageCancel);
      else if(sort.equals("Apply"))
        button.setIcon(imageApply);
      else if(sort.equals("Previos"))
        button.setIcon(imagePrevios);
      else if(sort.equals("Next"))
        button.setIcon(imageNext);
      else if(sort.equals("First"))
        button.setIcon(imageFirst);
      else if(sort.equals("Last"))
        button.setIcon(imageLast);
    }

    button.setBackground(lbBackgroundButton.getBackground());
    button.setForeground(lbForegroundButton.getBackground());
    lbNavigation.setBackground(lbBackgroundButton.getBackground());
    lbNavigation.setForeground(lbForegroundForm.getBackground());
    if(lbButtonBorder.getText().startsWith("Line")) {
          String[] m=lbButtonBorder.getText().split(",");
          int width=(new Integer(m[1].trim())).intValue();
          button.setBorder(BorderFactory.createLineBorder(lbForegroundButton.getBackground(),width));
      }
    else
          button.setBorder(lbButtonBorder.getBorder());  
  }

  private void change_button(PropertyChangeEvent e)
  {
    change_button();
    change(e);
  }
  private void change_button()
  {
    scButtonPrewiev.setBackground(lbBackgroundForm.getBackground());
    lbNavigation.setFont(lbFontGlobal.getFont());
    change_button_att(btnPrewApply, "Apply");
    change_button_att(btnPrewOK, "OK");
    change_button_att(btnPrewNew, "New");
    change_button_att(btnPrewDelete, "Delete");
    change_button_att(btnPrewDeleteAll, "Delete all");
    change_button_att(btnPrewDuplicate, "Duplicate");
    change_button_att(btnPrewCancel, "Cancel");
    change_button_att(btnPrewPrevios, "Previos");
    change_button_att(btnPrewNext, "Next");
    change_button_att(btnPrewFirst, "First");
    change_button_att(btnPrewLast, "Last");
  }

  private void change_border_raise(MouseEvent e)
  {
    if(getItem(((JLabel)e.getComponent()))!=itemID) 
    ((JLabel)e.getComponent()).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
  }
  
  private void change_border_none(MouseEvent e)
  {
    if(getItem(((JLabel)e.getComponent()))!=itemID) 
    ((JLabel)e.getComponent()).setBorder(BorderFactory.createLineBorder(SystemColor.control, 2));
  }
  
  public void setBorder()
  {
  
  }

  private void change_border_lower(MouseEvent e)
  {
    txtCheck.setBorder(BorderFactory.createLineBorder(SystemColor.control, 2));
    txtCombo.setBorder(BorderFactory.createLineBorder(SystemColor.control, 2));
    txtRadio.setBorder(BorderFactory.createLineBorder(SystemColor.control, 2));
    txtText.setBorder(BorderFactory.createLineBorder(SystemColor.control, 2));
    txtList.setBorder(BorderFactory.createLineBorder(SystemColor.control, 2));
    setItem((JLabel)e.getComponent());
    ((JLabel)e.getComponent()).setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    if(itemID==2 || itemID==1){
      btnItemTypeBorder.setEnabled(false);
      lbItemTypeBorder.setEnabled(false);
    }  
    else {
      btnItemTypeBorder.setEnabled(true);
      lbItemTypeBorder.setEnabled(true);
    }
    change_itemType();
  }

private void change_itemType() {
    scItemPrewiev.setBackground(lbBackgroundItemPanel.getBackground());
    itemCombo.setVisible(false);
    itemScroll.setVisible(false);
    itemText.setVisible(false);
    itemPanel.setVisible(false);
    itemCheckPanel.setVisible(false);
    Component comp=getItemComponent(itemID);
    int align=cmbTitleAlign.getSelectedIndex()==0?JLabel.LEFT:(cmbTitleAlign.getSelectedIndex()==1?JLabel.CENTER:JLabel.RIGHT);
    itemLabel.setHorizontalAlignment(align);
    itemLabel.setFont(new Font(lbFontGlobal.getFont().getFontName(), ((chBold.isSelected() || lbFontGlobal.getFont().isBold()) ? Font.BOLD : 0) + ((chItalic.isSelected() || lbFontGlobal.getFont().isItalic()) ? Font.ITALIC : 0), itemLabel.getFont().getSize()));
    pnItemPrewiev.setBackground(lbBackgroundItemPanel.getBackground());
    itemLabel.setForeground(lbForegroundItemPanel.getBackground());
    itemLabel.setBackground(lbBackgroundItemPanel.getBackground());
    Border border=lbItemTypeBorder.getBorder();
    if(lbItemTypeBorder.getText().startsWith("Line")) {
            String[] m=lbItemTypeBorder.getText().split(",");
            int width=(new Integer(m[1].trim())).intValue();
            border=BorderFactory.createLineBorder(lbForegroundInput.getBackground(),width);
    }

    if(itemID==0) 
    {
        ((JTextField)comp).setBorder(border);
        comp.setFont(lbInputFontGlobal.getFont());
        comp.setForeground(lbForegroundInput.getBackground());
        comp.setBackground(lbBackgroundInput.getBackground());
    }
    if(itemID==1) 
    {
        itemRadio1.setFont(lbInputFontGlobal.getFont());
        itemRadio1.setForeground(lbForegroundInput.getBackground());
        itemRadio1.setBackground(lbBackgroundInput.getBackground());
        itemRadio2.setFont(lbInputFontGlobal.getFont());
        itemRadio2.setForeground(lbForegroundInput.getBackground());
        itemRadio2.setBackground(lbBackgroundInput.getBackground());
        itemRadio3.setFont(lbInputFontGlobal.getFont());
        itemRadio3.setForeground(lbForegroundInput.getBackground());
        itemRadio3.setBackground(lbBackgroundInput.getBackground());
    }
    if(itemID==2) 
    {
        comp.setFont(lbInputFontGlobal.getFont());
        itemCheck.setForeground(lbForegroundInput.getBackground());
        itemCheck.setBackground(lbBackgroundInput.getBackground());
    }   
    if(itemID==3) 
    {
        comp.setFont(lbInputFontGlobal.getFont());
        comp.setForeground(lbForegroundInput.getBackground());
        comp.setBackground(lbBackgroundInput.getBackground());
        ((JComboBox)comp).getEditor().getEditorComponent().setForeground(lbForegroundInput.getBackground());
        ((JComboBox)comp).getEditor().getEditorComponent().setBackground(lbBackgroundInput.getBackground());
        ((JComboBox)comp).setBorder(border);
        
    }
    if(itemID==4) 
    {((JScrollPane)comp).setBorder(border);
        itemList.setFont(lbInputFontGlobal.getFont());
        itemList.setForeground(lbForegroundInput.getBackground());
        itemList.setBackground(lbBackgroundInput.getBackground());
    }
    int yLabel=0;
    int xLabel=0;
    int yComp=0;
    int xComp=0;
    int widthLabel = 75;
    if(cmbTitlePosition.getSelectedIndex()==1 || cmbTitlePosition.getSelectedIndex()==3)
        widthLabel = 125;
    int offset=(new Integer(cmbTitleOffset.getSelectedItem().toString())).intValue();
    if(cmbTitlePosition.getSelectedIndex()==0)
    {
        widthLabel = 11*(lbFontGlobal.getFont().isBold()?template.labelFont.font.getSize()/2:(lbFontGlobal.getFont().getSize()*2/3));
        xLabel=20;
        yLabel=20;
        yComp=20;
        xComp=xLabel+widthLabel+offset;
    }
    else if(cmbTitlePosition.getSelectedIndex()==1)
    {
        xLabel=20;
        yLabel=20;
        yComp=20 + itemLabel.getHeight() +offset;
        xComp=20;
    }
    else if(cmbTitlePosition.getSelectedIndex()==2)
    {
        widthLabel = 11*(template.labelFont.font.isBold()?template.labelFont.font.getSize()/2:(template.labelFont.font.getSize()*2/3));
        xLabel=comp.getWidth()+20+offset;
        yLabel=20;
        yComp=20;
        xComp=20;
    }
    else if(cmbTitlePosition.getSelectedIndex()==3)
    {
        xLabel=20;
        yLabel=comp.getHeight()+20+offset;
        yComp=20;
        xComp=20;
    }
    itemLabel.setBounds(xLabel,yLabel,widthLabel, itemLabel.getHeight());
    comp.setBounds(xComp,yComp,comp.getWidth(), comp.getHeight());
    comp.setVisible(true);
    pnItemPrewiev.setPreferredSize(new Dimension(Math.max(comp.getX()+comp.getWidth(),itemLabel.getX()+itemLabel.getWidth())+20, Math.max(comp.getY()+comp.getHeight(),itemLabel.getY()+itemLabel.getHeight())+20));
    pnItemPrewiev.setSize(new Dimension(Math.max(comp.getX()+comp.getWidth(),itemLabel.getX()+itemLabel.getWidth())+20, Math.max(comp.getY()+comp.getHeight(),itemLabel.getY()+itemLabel.getHeight())+20));}
  
private Component getItemComponent(int i)  {
    if(i==0)return itemText;
    if(i==1)return itemPanel;
    if(i==2)return itemCheckPanel;
    if(i==3)return itemCombo;
    if(i==4)return itemScroll; 
    return null;
}
    private JLabel getItemLabel(int i)  {
        if(i==0)return txtText;
        if(i==1)return txtRadio;
        if(i==2)return txtCheck;
        if(i==3)return txtCombo;
        if(i==4)return txtList; 
        return null;
    }
 private int getItem(JLabel label)
{
    String s=label.getText().trim();
    if(s.equals("Text Box"))
        return 0;
    else if(s.equals("Radio Button"))
        return 1;  
    else if(s.equals("Check Box"))
        return 2;          
    else if(s.equals("Combo Box"))
        return 3;   
    else if(s.equals("List"))
        return 4;
    return 0;
}

private void setItem(JLabel label)
{
    item[itemID][0]=cmbTitleAlign.getSelectedIndex();
    item[itemID][1]=cmbTitlePosition.getSelectedIndex();
    item[itemID][2]=cmbTitleOffset.getSelectedIndex();
    item[itemID][3]=chBold.isSelected()?1:0;
    item[itemID][4]=chItalic.isSelected()?1:0;
    String[] m=lbItemTypeBorder.getText().split(",");
    item[itemID][5]=getBorderStyle(lbItemTypeBorder.getText());
    if(item[itemID][5]<=1 && m.length>1)
    item[itemID][6]=(new Integer(m[1].trim())).intValue();
    else
    item[itemID][6]=0;
    
       String s=label.getText().trim();
       if(s.equals("Text Box"))
            itemID = 0;
       else if(s.equals("Radio Button"))
            itemID = 1;  
       else if(s.equals("Check Box"))
            itemID = 2;
        else if(s.equals("Combo Box"))
            itemID = 3;
       else if(s.equals("List"))
            itemID = 4;
     LoadItem(itemID);
}
    public int getBorderStyle(String s){
        int k=0;
        if(s.startsWith("None"))k=0;
        else if(s.startsWith("Line"))k=1;
        else if(s.startsWith("Etched"))k=2;
        else if(s.startsWith("Etched Raised"))k=3;
        else if(s.startsWith("Etched Lowered"))k=4;
        else if(s.startsWith("Bevel Raised"))k=5;
        else if(s.startsWith("Bevel Lowered"))k=6;
        else if(s.startsWith("Titled"))k=7;
        return k;
    }
    public String getBorderName(int s){
        String k="";
        if(s==0)k="None";
        else if(s==1)k="Line";
        else if(s==2)k="Etched";
        else if(s==3)k="Etched Raised";
        else if(s==4)k="Etched Lowered";
        else if(s==5)k="Bevel Raised";
        else if(s==6)k="Bevel Lowered";
        else if(s==7)k="Titled";
        return k;
    }
    public void LoadItem(int i)
    {
      setBorder();
      cmbTitleAlign.setSelectedIndex(item[i][0]);
      cmbTitlePosition.setSelectedIndex(item[i][1]);
      cmbTitleOffset.setSelectedIndex(item[i][2]);
      if (item[i][3]==1)
        chBold.setSelected(true);
      else
        chBold.setSelected(false);
      if (item[i][4]==1)
        chItalic.setSelected(true);
      else
        chItalic.setSelected(false); 
      lbItemTypeBorder.setBorder(getBorders(item[i][5],item[i][6]));
      lbItemTypeBorder.setText(getBorderName(item[i][5]) +(item[i][5]<=1?(", "+item[i][6]):"").toString());
      change_itemType();
    }
    public Border getBorders(int border_style, int border) {
        Border b=BorderFactory.createBevelBorder(border);
          if(border_style==0)
              b=BorderFactory.createEmptyBorder(border,border,border,border);
          else if(border_style==1)
              b=BorderFactory.createLineBorder(Color.black, border);
          else if(border_style==2)
              b=BorderFactory.createEtchedBorder();
          else if(border_style==3)
              b=BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
          else if(border_style==4)
              b=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
          else if(border_style==5)
              b=BorderFactory.createBevelBorder(BevelBorder.RAISED);
          else if(border_style==6)
              b=BorderFactory.createBevelBorder(BevelBorder.LOWERED);
          else if(border_style==7)
              b=BorderFactory.createTitledBorder("Title");
          return b;
    }
    public void setTextFont (JEditorPane label) {
        FontChooser chooser = new FontChooser((IISFrameMain)getParent(), label);
          Settings.Center(chooser);
          chooser.setVisible(true);
          if (chooser.getNewFont() != null)
          {
            label.setFont(chooser.getNewFont());
            label.setText("<html>"+(chooser.getNewUnderline()?"<u>":"") +(chooser.getNewFont().isBold()?"<b>":"")+(chooser.getNewFont().isItalic()?"<i>":"")+ "<font style=\"font-family:"+chooser.getNewFont().getFontName() + ";font-size:"+chooser.getNewFont().getSize()+"pt;\">"+ chooser.getNewFont().getName() + ", " + chooser.getNewFont().getSize()+"</font>"+(chooser.getNewFont().isItalic()?"</i>":"")+(chooser.getNewFont().isBold()?"</b>":"")+(chooser.getNewUnderline()?"</u>":"")+"</html>");
            chooser.dispose();
          }       
    }
    public void setBorderStyle(JLabel label, boolean title){
        BorderChooser chooser = new BorderChooser((IISFrameMain)getParent(), label);
        if(!title)
            chooser.borderName.removeItem("Titled");
        Settings.Center(chooser);
        chooser.setVisible(true);
        if (chooser.getNewBorder() != null)
           {
              label.setBorder(chooser.getNewBorder());
              label.setText(chooser.borderName.getSelectedItem().toString() +(chooser.borderSize.isEnabled()?(", "+chooser.borderSize.getSelectedItem()):"").toString());
            }
    }
    public void setColor(JLabel label, ActionEvent e) {
        Color c;
        JColorChooser cchooser=new JColorChooser();
        c = cchooser.showDialog(
                  ((Component)e.getSource()).getParent(),
                  "Color Picker", label.getBackground());
        if (c != null) 
            label.setBackground(c); 
    }
    class MyCellRenderer extends JLabel implements ListCellRenderer {
         public MyCellRenderer() {
             setOpaque(true);
         }
         public Component getListCellRendererComponent(
             JList list, 
             Object value, 
             int index, 
             boolean isSelected, 
             boolean cellHasFocus) 
         {
             setText(value.toString());
             setBackground(isSelected ? list.getSelectionBackground() : lbBackgroundInput.getBackground());
             setForeground(isSelected ? list.getSelectionForeground() : lbForegroundInput.getBackground());
             return this;
         }
     }
}