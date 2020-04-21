package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.event.MouseMotionListener;
import java.beans.VetoableChangeListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class funAlg extends JDialog 
{

  //private int copypressed = -1;
  //private int copyreleased = -1;
  private String toDo = "";
  public int lastAdd = -1;
  public /*static*/ int clicked = -1;  
  public /*static*/ int pressed = -2;
  public /*static*/ int released = -2;
  public /*static*/ int pozklik = 1;
  public int copyNode = -1;
  public int cutNode = -1;
  public int pasteNode = -1;
  //public /*static*/ int replacex = -1;
  //public /*static*/ int replacey = -1;
  public int inBlockWidth = 60; //sirina u ocevom bliku za sina
  public int blockWidth = 280;
  public funcElement[] fun = null;
  private JPanel jPanel2 = new JPanel();
  private JPanel jPanel1 = new JPanel();
  private JScrollPane jScrollPane1 = new JScrollPane();
 // public/* static*/ JLabel resizeLisener = new JLabel();
  //public /*static*/ JLabel kliklbl = new JLabel();
  //public /*static*/ JLabel replacedlbl = new JLabel();
   public JButton jButton1 = new JButton();
   public JButton jButton2 = new JButton();  
  public JButton jButton3 = new JButton();
  public JButton jButton4 = new JButton();
  public JButton jButton5 = new JButton();
  public JButton jButton6 = new JButton();
  public JButton jButton7 = new JButton();
  public JButton jButton8 = new JButton();
  public JButton jButton9 = new JButton();
    public JButton jButton10 = new JButton(); 
  public JButton jButton11 = new JButton();
  public JButton jButton12 = new JButton();
    public JButton jButton13 = new JButton();   
    public JButton jButton14 = new JButton();
    public JButton jButton15 = new JButton();
    public JButton jButton16 = new JButton();
    public JButton jButton17 = new JButton();  
  private Connection connections ;
  private int id = -1;
  
  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu jMenu1 = new JMenu();
  private JMenuItem jMenuItem1 = new JMenuItem();
  private JMenu jMenu2 = new JMenu();
  private JMenu jMenu3 = new JMenu();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();

  private ImageIcon comments = new ImageIcon(IISFrameMain.class.getResource("icons/comments.gif")); 
  private ImageIcon notes = new ImageIcon(IISFrameMain.class.getResource("icons/notes.gif")); 
  private ImageIcon logo = new ImageIcon(IISFrameMain.class.getResource("icons/bl.gif"));  
  private ImageIcon logo2 = new ImageIcon(IISFrameMain.class.getResource("icons/bl2.gif"));  
  private ImageIcon question = new ImageIcon(IISFrameMain.class.getResource("icons/question.gif"));
  private ImageIcon truee = new ImageIcon(IISFrameMain.class.getResource("icons/true.gif"));  
  private ImageIcon falsee = new ImageIcon(IISFrameMain.class.getResource("icons/false.gif"));  
  private ImageIcon forr = new ImageIcon(IISFrameMain.class.getResource("icons/for2.gif"));  
  private ImageIcon whilee = new ImageIcon(IISFrameMain.class.getResource("icons/while.gif"));  
  private ImageIcon dowhile = new ImageIcon(IISFrameMain.class.getResource("icons/dowhile.gif")); 
  private ImageIcon exception = new ImageIcon(IISFrameMain.class.getResource("icons/exception.gif")); 
  private ImageIcon help = new ImageIcon(IISFrameMain.class.getResource("icons/if2.gif")); 
  private ImageIcon function = new ImageIcon(IISFrameMain.class.getResource("icons/function.gif"));   
  private ImageIcon label = new ImageIcon(IISFrameMain.class.getResource("icons/ll.gif"));  
  private ImageIcon erase = new ImageIcon(IISFrameMain.class.getResource("icons/erase.gif"));  
  private ImageIcon laa = new ImageIcon(IISFrameMain.class.getResource("icons/laa.gif"));  
  private ImageIcon lai = new ImageIcon(IISFrameMain.class.getResource("icons/lai.gif"));  
  private ImageIcon lab = new ImageIcon(IISFrameMain.class.getResource("icons/lab.gif"));  
  private ImageIcon baa = new ImageIcon(IISFrameMain.class.getResource("icons/baa.gif"));  
  private ImageIcon bai = new ImageIcon(IISFrameMain.class.getResource("icons/bai.gif"));  
  private ImageIcon bab = new ImageIcon(IISFrameMain.class.getResource("icons/bab.gif"));  
  private ImageIcon declaration = new ImageIcon(IISFrameMain.class.getResource("icons/declaration.gif"));  
  private ImageIcon declaration2 = new ImageIcon(IISFrameMain.class.getResource("icons/declaration2.gif")); 
  private ImageIcon function2 = new ImageIcon(IISFrameMain.class.getResource("icons/fung.gif"));   
  
  private JPanel jPanel3 = new JPanel();
  private JPanel jPanel4 = new JPanel();
  private JPanel jPanel5 = new JPanel();
  private JPanel jPanel6 = new JPanel();
  private JLabel jLabel4 = new JLabel();
  private JMenuItem jMenuItem2 = new JMenuItem();
  private JMenuItem jMenuItem3 = new JMenuItem();
  private JMenuItem jMenuItem4 = new JMenuItem();
  private JMenu jMenu4 = new JMenu();
  private JMenuItem jMenuItem5 = new JMenuItem();
  private JMenuItem jMenuItem6 = new JMenuItem();
  private JMenuItem jMenuItem7 = new JMenuItem();
  private JMenuItem jMenuItem8 = new JMenuItem();
  private JMenuItem jMenuItem9 = new JMenuItem();
  private JPanel jPanel7 = new JPanel();
  private JLabel jLabel5 = new JLabel();
  
  public funAlg()
  {
    this(null, "", false);
  }

  /**
   * 
   * @param modal
   * @param title
   * @param parent
   */
  public funAlg(JDialog parent, String title, boolean modal,Connection con,int i)
  {
    super((JDialog)parent, title, modal);
    try
    {
      connections = con;
      id = i;
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }      
  }
  public funAlg(JDialog parent, String title, boolean modal)
  {
    super((JDialog)parent, title, modal);
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
  {
    jPanel1.removeAll();
    if( fun != null )
      fun = null;
      
    ImageIcon icon = new ImageIcon(IISFrameMain.class.getResource("icons/cursor.gif")); 
    Image a = icon.getImage();          
    this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(a,new Point(1,1),"test") );       

    pressed = -2;
    released = -2;
    pozklik = 1;
    //replacex = -1;
    //replacey = -1;  
    //resizeLisener.setText("0,0");
    //kliklbl.setText("-1");
    //replacedlbl.setText("12");
    
    
    this.setSize(new Dimension(753, 649));
    this.getContentPane().setLayout(null);
    this.setTitle("Function Specification");
    this.setBackground(new Color(47, 87, 155));
    this.setJMenuBar(jMenuBar1);
    this.addWindowListener(new WindowAdapter()
      {
        public void windowClosing(WindowEvent e)
        {
          this_windowClosing(e);
        }
      });
    this.addComponentListener(new ComponentAdapter()
      {
        public void componentResized(ComponentEvent e)
        {
          this_componentResized(e);
        }
      });
    jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    jPanel1.setBackground(new Color(47, 87, 155));
    jPanel1.setLayout(null);
    jPanel1.setBounds(new Rectangle(10, 0, 600, 600));
    jPanel1.setBounds(0,0,600,600);
    jPanel1.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          jPanel1_mouseClicked(e);
        }
      });
    jButton1.setBounds(new Rectangle(60, 5, 25, 20));
    jButton1.setMargin(new Insets(0, 0, 0, 0));
    jButton1.setIcon(bai);
    jButton1.setHorizontalAlignment(SwingConstants.LEADING);
    jButton1.setSize(new Dimension(25, 20));
    jButton1.setToolTipText("Add block in selected item");
    jButton1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton1_actionPerformed(e);
        }
      });
    jButton2.setBounds(new Rectangle(65, 5, 25, 20));
    jButton2.setMargin(new Insets(0, 0, 0, 0));
    jButton2.setIcon(lai);
    jButton2.setSize(new Dimension(25, 20));
    jButton2.setToolTipText("Add action in selected item");
    jButton2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton2_actionPerformed(e);
        }
      });
    jScrollPane1.getViewport().setLayout(null);
    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    jScrollPane1.setPreferredSize(new Dimension(600, 600));
    jPanel1.setSize(new Dimension(600, 600));
    jPanel1.setPreferredSize(new Dimension(600, 600));
    jScrollPane1.setBackground(new Color(47, 87, 155));
    jScrollPane1.setBounds(new Rectangle(0, 40, 745, 555));
//    resizeLisener.setText("0,0");
//    resizeLisener.setBounds(new Rectangle(415, 25, 34, 14));
/*    resizeLisener.addPropertyChangeListener(new PropertyChangeListener()
      {
        public void propertyChange(PropertyChangeEvent e)
        {
          resizeLisener_propertyChange(e);
        }
      });
*/      
    //kliklbl.setText("-1");
    //kliklbl.setBounds(new Rectangle(415, 0, 30, 10));
    /*kliklbl.addPropertyChangeListener(new PropertyChangeListener()
      {
        public void propertyChange(PropertyChangeEvent e)
        {
          kliklbl_propertyChange(e);
        }
      });*/
    //replacedlbl.setText("jLabel1");
    //replacedlbl.setBounds(new Rectangle(415, 10, 34, 14));
    /*replacedlbl.addPropertyChangeListener(new PropertyChangeListener()
      {
        public void propertyChange(PropertyChangeEvent e)
        {
          replacedlbl_propertyChange(e);
        }
      });*/
    jButton3.setBounds(new Rectangle(680, 0, 25, 20));
    jButton3.setMargin(new Insets(0, 0, 0, 0));
    jButton3.setIcon(erase);
    jButton3.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton3_actionPerformed(e);
        }
      });
    jButton4.setBounds(new Rectangle(35, 5, 25, 20));
    jButton4.setMargin(new Insets(1, 1, 0, 0));
    jButton4.setHorizontalAlignment(SwingConstants.LEADING);
    jButton4.setSize(new Dimension(25, 20));
    jButton4.setToolTipText("Add block above selected item");
    jButton4.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton4_actionPerformed(e);
        }
      });
    jButton5.setBounds(new Rectangle(85, 5, 25, 20));
    jButton5.setMargin(new Insets(1, 1, 0, 0));
    jButton5.setIcon(baa);
    jButton5.setHorizontalAlignment(SwingConstants.LEADING);
    jButton5.setSize(new Dimension(25, 20));
    jButton5.setToolTipText("Add block below selected item");
    jButton5.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton5_actionPerformed(e);
        }
      });
    jButton6.setBounds(new Rectangle(40, 5, 25, 20));
    jButton6.setMargin(new Insets(1, 1, 0, 0));
    jButton6.setIcon(lab);
    jButton6.setSize(new Dimension(25, 20));
    jButton6.setToolTipText("Add action above selected item");
    jButton6.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton6_actionPerformed(e);
        }
      });
    jButton7.setBounds(new Rectangle(90, 5, 25, 20));
    jButton7.setMargin(new Insets(1, 1, 0, 0));
    jButton7.setIcon(laa);
    jButton7.setSize(new Dimension(25, 20));
    jButton7.setToolTipText("Add action below selected item");
    jButton7.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton7_actionPerformed(e);
        }
      });
    jButton8.setBounds(new Rectangle(55, 5, 25, 20));
    jButton8.setMargin(new Insets(1, 1, 0, 0));
    jButton8.setIcon(forr);
    jButton8.setToolTipText("Add for loop in selected item");
    jButton8.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton8_actionPerformed(e);
        }
      });
    jButton9.setBounds(new Rectangle(80, 5, 25, 20));
    jButton9.setToolTipText("Add while loop in selected item");
    jButton9.setMargin(new Insets(1, 1, 0, 0));
    jButton9.setIcon(whilee);
    jButton9.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton9_actionPerformed(e);
        }
      });
    jButton11.setBounds(new Rectangle(105, 5, 25, 20));
    jButton11.setMargin(new Insets(1, 1, 0, 0));
    jButton11.setIcon(help);
    jButton11.setHorizontalTextPosition(SwingConstants.CENTER);
    jButton11.setToolTipText("Add if condition in selected item");
    jButton11.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton11_actionPerformed(e);
        }
      });
    jButton12.setBounds(new Rectangle(680, 20, 25, 20));
    jButton12.setMargin(new Insets(0, 0, 0, 0));
    jButton12.setIcon(function);
    jButton12.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton12_actionPerformed(e);
        }
      });
    jButton13.setBounds(new Rectangle(130, 5, 25, 20));
    jButton13.setMargin(new Insets(0, 0, 0, 0));
    jButton13.setIcon(exception);
    jButton13.setToolTipText("Add exception in selected item");
    jButton13.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton13_actionPerformed(e);
        }
      });
    jMenu1.setText("Function");
    jMenuItem1.setText("Load");
    jMenuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem1_actionPerformed(e);
        }
      });
    jMenu2.setText("Edit");
    jMenu3.setText("Help");
    jLabel1.setText("Block ");
    jLabel1.setBounds(new Rectangle(0, 5, 35, 20));
    jLabel1.setToolTipText("null");
    jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setForeground(Color.white);
    jLabel2.setText("Action");
    jLabel2.setBounds(new Rectangle(0, 5, 40, 20));
    jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel2.setForeground(Color.white);
    jLabel3.setText("Structure");
    jLabel3.setBounds(new Rectangle(0, 5, 55, 20));
    jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel3.setToolTipText("null");
    jLabel3.setForeground(Color.white);
    jPanel3.setBounds(new Rectangle(5, 5, 115, 30));
    jPanel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    jPanel3.setLayout(null);
    jPanel3.setBackground(new Color(35, 70, 155));
    jPanel4.setBounds(new Rectangle(365, 5, 120, 30));
    jPanel4.setLayout(null);
    jPanel4.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    jPanel4.setBackground(new Color(35, 70, 155));
    jPanel5.setBounds(new Rectangle(125, 5, 235, 30));
    jPanel5.setLayout(null);
    jPanel5.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    jPanel5.setBackground(new Color(35, 70, 155));
    jPanel6.setBounds(new Rectangle(490, 5, 90, 30));
    jPanel6.setLayout(null);
    jPanel6.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    jPanel6.setBackground(new Color(35, 70, 155));
    jLabel4.setText("Declaration");
    jLabel4.setBounds(new Rectangle(0, 5, 60, 20));
    jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel4.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel4.setForeground(Color.white);
    jButton14.setBounds(new Rectangle(60, 5, 25, 20));
    jButton14.setMargin(new Insets(0, 0, 0, 0));
    jButton14.setToolTipText("Add variables, constants...");
    jButton14.setIcon(declaration2);
    jButton14.setHorizontalTextPosition(SwingConstants.CENTER);
    jButton14.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton14_actionPerformed(e);
        }
      });
    jButton16.setBounds(new Rectangle(155, 5, 25, 20));
    jButton16.setMargin(new Insets(0, 0, 0, 0));
    jButton16.setIcon(declaration);
    jButton16.setToolTipText("Add declaration block in selected item");
    jButton16.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton16_actionPerformed(e);
        }
      });
    jButton17.setBounds(new Rectangle(180, 5, 25, 20));
    jButton17.setIcon(function2);
    jButton17.setToolTipText("Add local function in selected item");
    jButton17.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton17_actionPerformed(e);
        }
      });
    jMenuItem2.setText("New");
    jMenuItem3.setText("Save");
    jMenuItem3.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem3_actionPerformed(e);
        }
      });
    jMenuItem4.setText("Close");
    jMenu4.setText("Run");
    jMenuItem5.setText("Generate XML");
    jMenuItem6.setText("Cut");
    jMenuItem7.setText("Copy");
    jMenuItem8.setText("Paste");
    jMenuItem9.setText("Delete");
    jButton10.setBounds(new Rectangle(205, 5, 25, 20));
    jButton10.setIcon(comments);
    jButton10.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton10_actionPerformed(e);
        }
      });
    jPanel7.setBounds(new Rectangle(585, 5, 90, 30));
    jPanel7.setBackground(new Color(35, 70, 155));
    jPanel7.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    jPanel7.setLayout(null);
    jLabel5.setText("Comments");
    jLabel5.setBounds(new Rectangle(5, 5, 55, 20));
    jLabel5.setToolTipText("null");
    jLabel5.setForeground(Color.white);
    jButton15.setBounds(new Rectangle(60, 5, 25, 20));
    jButton15.setIcon(notes);
    jButton15.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton15_actionPerformed(e);
        }
      });
    //resizeLisener.setVisible(false);
    jPanel2.setLayout(null);
    jPanel2.setMinimumSize(new Dimension(1, 1));
    jPanel2.setBounds(new Rectangle(0, 0, 670, 40));
    jPanel2.setBorder(BorderFactory.createLineBorder(Color.white, 1));
    jPanel2.setBackground(SystemColor.control);
    jPanel2.setSize(new Dimension(730, 40));
    jPanel3.add(jButton5, null);
    jPanel3.add(jButton1, null);
    jPanel3.add(jButton4, null);
    jPanel3.add(jLabel1, null);
    jPanel4.add(jButton6, null);
    jPanel4.add(jButton2, null);
    jPanel4.add(jButton7, null);
    jPanel4.add(jLabel2, null);
    jPanel5.add(jButton10, null);
    jPanel5.add(jButton17, null);
    jPanel5.add(jButton16, null);
    jPanel5.add(jButton13, null);
    jPanel5.add(jButton11, null);
    jPanel5.add(jButton9, null);
    jPanel5.add(jButton8, null);
    jPanel5.add(jLabel3, null);
    jPanel6.add(jButton14, null);
    jPanel6.add(jLabel4, null);
    jPanel7.add(jButton15, null);
    jPanel7.add(jLabel5, null);
    jPanel2.add(jPanel7, null);
    jPanel2.add(jPanel6, null);
    jPanel2.add(jPanel5, null);
    jPanel2.add(jPanel4, null);
    jPanel2.add(jPanel3, null);
    jPanel2.add(jButton12, null);
    jPanel2.add(jButton3, null);
    //jPanel2.add(replacedlbl, null);
    //jPanel2.add(kliklbl, null);
    //jPanel2.add(resizeLisener, null);
    this.getContentPane().add(jPanel2, null);
    jScrollPane1.getViewport().add(jPanel1);
    this.getContentPane().add(jScrollPane1, null);
    
    jButton4.setIcon(bab);
    
    this.add("class iisc.funcPanel");
    jMenu1.add(jMenuItem2);
    jMenu1.add(jMenuItem1);
    jMenu1.add(jMenuItem3);
    jMenu1.addSeparator();
    jMenu1.add(jMenuItem4);
    jMenuBar1.add(jMenu1);
    jMenu2.add(jMenuItem6);
    jMenu2.add(jMenuItem7);
    jMenu2.add(jMenuItem8);
    jMenu2.addSeparator();
    jMenu2.add(jMenuItem9);
    jMenuBar1.add(jMenu2);
    jMenu4.add(jMenuItem5);
    jMenuBar1.add(jMenu4);
    jMenuBar1.add(jMenu3);

    
  }

  private void jButton1_actionPerformed(ActionEvent e)
  {
        addBlockIn();
  }
  
  public void addDeclaration()
  {

      int i=0;
      for(i=0;i<fun.length;i++)
        if( fun[i].getClass().toString().equals("class iisc.funcPanel") && fun[i].father == this.clicked && ((funcPanel)fun[i]).name.getText().equals("DECLARATION"))
          return;

      for(i=0;i<fun.length;i++)
        if( fun[i].getClass().toString().equals("class iisc.funcPanel") && fun[i].father == this.clicked)
        {
          this.clicked = i;
          break;
        }

      this.pozklik = -1;
        
      if (i == fun.length)
        this.pozklik = 0;
        
      add("class iisc.funcPanel");  
      ((funcPanel)fun[lastAdd]).name.setText("DECLARATION");    

      fun[0].fillPanel();

  }
  
  public void addBlockIn()
  {
    this.pozklik = 0;
    add("class iisc.funcPanel");
    
    if(  ((funcPanel)fun[this.clicked]).name.getText().equals("IF CONDITIONAL") || ((funcPanel)fun[this.clicked]).name.getText().equals("ELSE IF CONDITIONAL") )
    {
      ((funcPanel)fun[lastAdd]).name.setText("ELSE");
      jButton1.setEnabled(false);
    } 
    fun[0].fillPanel();
  }

  public void add(String type)
  {
      int klik = this.clicked;
      
      if ( fun == null)
      {
            if (id>=0)
            {
                try
                {
                  
                  JDBCQuery query=new JDBCQuery(connections);
                  JDBCQuery query2=new JDBCQuery(connections);
                  ResultSet rs1,rs2;
                  rs2=query2.select("select * from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.PR_id = D.PR_id AND F.Fun_id = "+id+" order by F.Param_seq");    
                  int duz=0;
                  while(rs2.next())
                     duz++;

                  query2.Close();
    
                  rs1=query.select("select * from IISC_Function where Fun_id="+id);
                  while(rs1.next())
                  {
                      String funname = rs1.getString("Fun_name");
                      
                      fun = new funcElement[duz + 2];
                      fun[0] = new funcPanel(0,-1,0,this);
                      ((funcPanel)fun[0]).name.setText("FUNCTION "+funname+"-");
                      fun[1] = new funcPanel(1,-1,0,this);
                      ((funcPanel)fun[1]).name.setText("BODY");
                      jPanel1.add(((funcPanel)fun[1]));            
                  
                      rs2=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.PR_id = D.PR_id AND F.Fun_id = "+id+" order by F.Param_seq");    
                      int i=2;
                      while(rs2.next())
                      {
                          String domtemp= rs2.getString("Dom_mnem");
                          String param= rs2.getString("Param_name");
                          fun[i] = new funcLabel(i,0,1,this);
                          ((funcPanel)fun[0]).add((funcLabel)fun[i],null); 
                          ((funcLabel)fun[i]).setBounds(inBlockWidth,15*(i-2+1),blockWidth,15);
                          ((funcLabel)fun[i]).label.removeAllItems();
                          ((funcLabel)fun[i]).label.addItem(new JLabel("Param"));
                          //((funcLabel)fun[i]).label.setText("Param");
                          ((funcLabel)fun[i]).action.setText("("+domtemp+") "+param);
                          i++;
                      }
                      query2.Close();
                  }
                  ((funcPanel)fun[0]).setBounds(0,0,inBlockWidth+blockWidth,30+15*duz); 
                  query.Close();              
                }
                catch(Exception ex)
                {
                    System.out.println("Greska i funAlg.add:"+ex.toString());
                }
                ((funcPanel)fun[1]).setBounds(0,((funcPanel)fun[0]).getHeight(),((funcPanel)fun[0]).getWidth(),30); 
                jPanel1.add(((funcPanel)fun[0]));
                jPanel1.revalidate();   
            } 
      }
      else
      {
          if(klik == -1)
            return;      
            
          int pamtiKlik = klik;
          int pamtiPozklik = pozklik;
          
          int mjesto = 0;
          int otac = -1;
          int nivo = -1;
          int x = 0;
          int y = 0;
          int wid = 0;
          
          if ( fun[klik].getClass().toString().equals("class iisc.funcLabel") && pozklik == 0)
            return;                
          else if ( fun[klik].getClass().toString().equals("class iisc.funcPanel") && pozklik == 0)
          {
              mjesto = fun.length; 
              x = inBlockWidth;
              y = 15;
              wid = blockWidth;
              
              for (int i=0;i<fun.length;i++)
                if ((fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).father == klik) || (fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).father == klik))
                {
                  if ( fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).name.getText().equals("EXCEPTION"))
                  {
                    klik = i;
                    this.clicked = i;
                    //kliklbl.setText(""+i);
                    pozklik=-1;
                    break;
                  }

                  mjesto = i + 1;
                  if( fun[i].getClass().toString().equals("class iisc.funcPanel"))
                  { 
                      x = ((funcPanel)fun[i]).getX();
                      y = ((funcPanel)fun[i]).getY() + ((funcPanel)fun[i]).getHeight();
                      wid = ((funcPanel)fun[i]).getWidth();
                  }
                  else if( fun[i].getClass().toString().equals("class iisc.funcLabel"))
                  { 
                      x = ((funcLabel)fun[i]).getX();
                      y = ((funcLabel)fun[i]).getY() + ((funcLabel)fun[i]).getHeight();
                      wid = ((funcLabel)fun[i]).getWidth();                  
                  }
                }
               
              otac = klik;
              nivo = ((funcPanel)fun[klik]).level + 1;
          }
          
          if(pozklik == 1)
          {
              mjesto = klik + 1;
              if ( fun[klik].getClass().toString().equals("class iisc.funcLabel"))
              { 
                  nivo = ((funcLabel)fun[klik]).level;
                  otac = ((funcLabel)fun[klik]).father;
                  x = ((funcLabel)fun[klik]).getX();
                  y = ((funcLabel)fun[klik]).getY() + ((funcLabel)fun[klik]).getHeight();
                  wid = ((funcLabel)fun[klik]).getWidth();
              }
              else if( fun[klik].getClass().toString().equals("class iisc.funcPanel"))
              { 
                  nivo = ((funcPanel)fun[klik]).level;
                  otac = ((funcPanel)fun[klik]).father;
                  x = ((funcPanel)fun[klik]).getX();
                  y = ((funcPanel)fun[klik]).getY() + ((funcPanel)fun[klik]).getHeight();       
                  wid = ((funcPanel)fun[klik]).getWidth();
              }
          }
          
          if(pozklik == -1)
          {
              mjesto = klik;
              if ( fun[klik].getClass().toString().equals("class iisc.funcLabel"))
              { 
                  nivo = ((funcLabel)fun[klik]).level;
                  otac = ((funcLabel)fun[klik]).father;
                  x = ((funcLabel)fun[klik]).getX();
                  y = ((funcLabel)fun[klik]).getY();
                  wid = ((funcLabel)fun[klik]).getWidth();
              }
              else if( fun[klik].getClass().toString().equals("class iisc.funcPanel"))
              { 
                  nivo = ((funcPanel)fun[klik]).level;
                  otac = ((funcPanel)fun[klik]).father;
                  x = ((funcPanel)fun[klik]).getX();
                  y = ((funcPanel)fun[klik]).getY();
                  wid = ((funcPanel)fun[klik]).getWidth();                  
              }              
          }
          
          klik = pamtiKlik;
          this.clicked = klik;
          //kliklbl.setText(""+klik);
          pozklik = pamtiPozklik;
          

          funcElement tmp[] = new funcElement[fun.length];
          
          for(int i = 0; i < tmp.length ; i++)
            tmp[i] = fun[i];
          
          fun = new funcElement[fun.length + 1];
          jPanel1.removeAll();     
          
          int i = 0; //brojac glavnog niza
          int j = 0; //brojac tmp niza
          
          lastAdd = mjesto;
          
          while( j < mjesto )
                fun[i++] = tmp[j++];

          
          if (type.equals("class iisc.funcPanel"))
          {
              fun[mjesto] = new funcPanel(mjesto,otac,nivo,this);
              ((funcPanel)fun[mjesto]).name.setText("BLOCK");
          }
          else if (type.equals("class iisc.funcLabel"))
          {
              JLabel tmpp = null;
              fun[mjesto] = new funcLabel(mjesto,otac,nivo,this);
              ((funcLabel)fun[mjesto]).itemsForAction();
              for(int yu=0;yu<((funcLabel)fun[mjesto]).label.getItemCount();yu++)
                  if( ((JLabel)((funcLabel)fun[mjesto]).label.getItemAt(yu)).getText().equals("Assignment")  )
                    {
                          tmpp = ((JLabel)((funcLabel)fun[mjesto]).label.getItemAt(yu));
                          break;
                    }
                    
              ((funcLabel)fun[mjesto]).label.setSelectedItem(tmpp);
              //((funcLabel)fun[mjesto]).label.setText("Action");
              ((funcLabel)fun[mjesto]).action.setText("");
          }

          i = mjesto + 1;
          
          while( j < tmp.length )
              fun[i++] = tmp[j++];

          for (i = 0; i < fun.length; i++)
          {
            if (fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).father >= mjesto)
                ((funcPanel)fun[i]).father += 1;
            else if (fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).father >= mjesto)
                ((funcLabel)fun[i]).father += 1;
          }
          if ( this.clicked >= mjesto )
              this.clicked++;
            //kliklbl.setText(""+(Integer.parseInt(kliklbl.getText())+1));

          for (i = 0; i < fun.length; i++)
          {
                if (fun[i].getClass().toString().equals("class iisc.funcPanel"))
                {
                  ((funcPanel)fun[i]).pozition = i;
                  if (((funcPanel)fun[i]).father == -1)
                    jPanel1.add((funcPanel)fun[i],null);
                  else
                    ((funcPanel)fun[((funcPanel)fun[i]).father]).add((funcPanel)fun[i],null);
                }
                else if (fun[i].getClass().toString().equals("class iisc.funcLabel"))
                {
                  ((funcLabel)fun[i]).pozition = i;
                  if (((funcLabel)fun[i]).father == -1)
                    jPanel1.add((funcLabel)fun[i],null);
                  else
                    ((funcPanel)fun[((funcLabel)fun[i]).father]).add((funcLabel)fun[i],null);
                }            
          }
          
          jPanel1.revalidate();
          
          if ( type.equals("class iisc.funcPanel"))
          {
              ((funcPanel)fun[mjesto]).setBounds(x,y,wid,30);
              if ( ((funcPanel)fun[mjesto]).father != -1)
                ((funcPanel)fun[((funcPanel)fun[mjesto]).father]).setSize(wid+inBlockWidth,((funcPanel)fun[((funcPanel)fun[mjesto]).father]).getHeight()+15);          
          }
          else if ( type.equals("class iisc.funcLabel") )
          {
              ((funcLabel)fun[mjesto]).setBounds(x,y,wid,15);
              if ( ((funcLabel)fun[mjesto]).father != -1)
                ((funcPanel)fun[((funcLabel)fun[mjesto]).father]).setSize(wid+inBlockWidth,((funcPanel)fun[((funcLabel)fun[mjesto]).father]).getHeight()+15);                      
          }
          
          //setLevel();
          this.clicked = otac;          
          resizeHeight(mjesto,1,"a");
          resizeWidth(/*mjesto*/);
          //resizePanel();
      }
  }
/*
  private void setLevel()
  {
        int[] stack = new int[1];
        int otaclevel = 0;
        
        stack[0] = 1;
        
        int i = stack.length - 1;
        while(i>-1)
        {
            int otacc = stack[i];
            if( fun[i].getClass().toString().equals("class iisc.funcPanel"))
            {
                //int dsdas = ((funcPanel)fun[i]).level; 
                if (((funcPanel)fun[i]).father == -1)
                  ((funcPanel)fun[i]).level = 0;
                else 
                  ((funcPanel)fun[i]).level = ((funcPanel)fun[((funcPanel)fun[i]).father]).level + 1;
                  
                int djeca = 0;
                for(int j=0;j<fun.length;j++)
                    if( fun[j].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[j]).father == otacc)
                      djeca++;
                    else if( fun[j].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[j]).father == otacc)
                      djeca++;
                
                int[] tmp = new int[stack.length-1];
                for(int j=stack.length-2;j>=0;j--)
                {
                    tmp[j] = stack[j];
                }
                
                stack = new int[stack.length - 1 + djeca];
                for(int j=0;j<tmp.length;j++)
                  stack[j] = tmp[j];
                
                int k=tmp.length;
                for(int j=fun.length-1;j>=0;j--)
                {
                    if( fun[j].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[j]).father == otacc)
                        stack[k++] = j;
                    else if( fun[j].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[j]).father == otacc)
                        stack[k++] = j;
                }                
                
                i=stack.length - 1;    
            }
            else if( fun[i].getClass().toString().equals("class iisc.funcLabel") )
            {
                ((funcLabel)fun[i]).level = ((funcPanel)fun[((funcLabel)fun[i]).father]).level + 1;
                  
                int tmp[] = new int [stack.length-1];
                
                for(int j=0;j<stack.length-1;j++)
                  tmp[j] = stack[j];
                  
                stack = new int[tmp.length];
                
                for(int j=0;j<stack.length;j++)
                  stack[j] = tmp[j];
 
                i=stack.length - 1;   
            }
        }    
  }
  */

  private void jButton2_actionPerformed(ActionEvent e)
  {
        addActionIn();
  }
  
  public void addActionIn()
  {
      this.pozklik = 0;
      add("class iisc.funcLabel");  
      //((funcLabel)fun[lastAdd]).itemsForAction();
      fun[0].fillPanel();
  }

  private void jPanel1_mouseClicked(MouseEvent e)
  {
      //this.kliklbl.setText(""+(-1));
      this.clicked = -1;
  }

/*
  private void jPanel1_mouseMoved(MouseEvent e)
  {
      int klik = Integer.parseInt(kliklbl.getText());
      for(int i = 0;i<fun.length;i++)
        if(i != klik)
        {
              if (fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).level == i ) 
                  ((funcPanel)fun[i]).setBackground(Color.WHITE);
              else if (fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).level == i ) 
                  ((funcLabel)fun[i]).setBackground(Color.WHITE);
        }
  }
*/  
/*
  private void resizeLisener_propertyChange(PropertyChangeEvent e)
  {
    System.out.println("type:  "+e.getPropertyName());
    if (e.getPropertyName().toString().equals("text"))
    {
      String[] tmp =resizeLisener.getText().toString().split(",");
      resizeHeight( Integer.parseInt(tmp[0]) , Integer.parseInt(tmp[1]) ,"l");
      resizeWidth();//Integer.parseInt(tmp[0]));
      //resizePanel();
      jPanel1.revalidate();
      
    }
  }*/

  /*private void resizePanel2()
  {
      /*    int sum = 0;
          for(int i = 0;i < fun.length; i++)
             if (fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).level == 0 ) 
                sum += ((funcPanel)fun[i]).getHeight();
             else if (fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).level == 0 ) 
               sum += ((funcLabel)fun[i]).getHeight();
          
          if (sum > jScrollPane1.getHeight())
          {
              jPanel1.setSize(new Dimension(jPanel1.getWidth(),sum));
              jPanel1.setPreferredSize(new Dimension(jPanel1.getWidth(),sum));
          }
          else
          {
              jPanel1.setSize(new Dimension(jPanel1.getWidth(),jScrollPane1.getHeight()));
              jPanel1.setPreferredSize(new Dimension(jPanel1.getWidth(),jScrollPane1.getHeight()));
          }*/

/*
          int widthtmp =((funcPanel)fun[0]).getWidth();
          if (widthtmp > jScrollPane1.getWidth())
          {
              jPanel1.setSize(new Dimension(widthtmp,jPanel1.getHeight()));
              jPanel1.setPreferredSize(new Dimension(widthtmp,jPanel1.getHeight()));
              
          }
          else
          {
              jPanel1.setSize(new Dimension(jScrollPane1.getWidth(),jPanel1.getHeight()));
              jPanel1.setPreferredSize(new Dimension(jScrollPane1.getWidth(),jPanel1.getHeight()));
          }
          
          
          //jPanel1.revalidate();
          //jScrollPane1.revalidate();    
  }*/

  public void resizeWidth(/*int plusmjesto*/)
  {
        int[] stack = new int[2];
        int maxlevel = 0;
        
        stack[0] = 1;
        stack[1] = 0;
        
        int i = stack.length - 1;
        while(i>-1)
        {
            int otacc = stack[i];
            if( fun[stack[i]].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[stack[i]]).jButton1.getText().equals("-") )
            {
                if( maxlevel  <= ((funcPanel)fun[stack[i]]).level)
                  maxlevel = ((funcPanel)fun[stack[i]]).level ;
                  
                int djeca = 0;
                for(int j=0;j<fun.length;j++)
                {
                    if( fun[j].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[j]).jButton1.getText().equals("-") && ((funcPanel)fun[j]).father == otacc)
                      djeca++;
                    else if( fun[j].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[j]).father == otacc)
                      djeca++;
                }
                
                if( maxlevel  < ((funcPanel)fun[stack[i]]).level && djeca > 0)
                  maxlevel++;
                
                int[] tmp = new int[stack.length-1];
                for(int j=stack.length-2;j>=0;j--)
                {
                    tmp[j] = stack[j];
                }
                
                stack = new int[stack.length - 1 + djeca];
                for(int j=0;j<tmp.length;j++)
                  stack[j] = tmp[j];
                
                int k=tmp.length;
                for(int j=fun.length-1;j>=0;j--)
                {
                    if( fun[j].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[j]).jButton1.getText().equals("-") && ((funcPanel)fun[j]).father == otacc)
                        stack[k++] = j;
                    else if( fun[j].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[j]).father == otacc)
                        stack[k++] = j;
                }                
                
                i=stack.length - 1;    
            }
            else if( fun[stack[i]].getClass().toString().equals("class iisc.funcLabel") )
            {
            
                if(maxlevel <= ((funcLabel)fun[stack[i]]).level)
                  maxlevel = ((funcLabel)fun[stack[i]]).level;            
                
                int tmp[] = new int [stack.length-1];
                
                for(int j=0;j<stack.length-1;j++)
                  tmp[j] = stack[j];
                  
                stack = new int[tmp.length];
                
                for(int j=0;j<stack.length;j++)
                  stack[j] = tmp[j];
 
                i=stack.length - 1;   
            }
            else
            {
                int tmp[] = new int [stack.length-1];
                
                for(int j=0;j<stack.length-1;j++)
                  tmp[j] = stack[j];
                  
                stack = new int[tmp.length];
                
                for(int j=0;j<stack.length;j++)
                  stack[j] = tmp[j];
 
                i=stack.length - 1;                 
            }
        }
        
        System.out.println("max level::"+maxlevel);
        for(i=0;i<fun.length;i++)
              if (fun[i].getClass().toString().equals("class iisc.funcPanel"))
              {
                  if(maxlevel - ((funcPanel)fun[i]).level >= 0)
                  {
                        ((funcPanel)fun[i]).setSize(blockWidth + (maxlevel - ((funcPanel)fun[i]).level)*inBlockWidth,((funcPanel)fun[i]).getHeight());
                        ((funcPanel)fun[i]).setPreferredSize(new Dimension(blockWidth + (maxlevel - ((funcPanel)fun[i]).level)*inBlockWidth,((funcPanel)fun[i]).getHeight()));
                  }
              }
              else if (fun[i].getClass().toString().equals("class iisc.funcLabel"))
              {
                  if(maxlevel - ((funcLabel)fun[i]).level >= 0)
                  {
                        ((funcLabel)fun[i]).setSize(blockWidth + (maxlevel - ((funcLabel)fun[i]).level)*inBlockWidth,((funcLabel)fun[i]).getHeight());
                        ((funcLabel)fun[i]).setPreferredSize(new Dimension(blockWidth + (maxlevel - ((funcLabel)fun[i]).level)*inBlockWidth,((funcLabel)fun[i]).getHeight()));
                        
                        ((funcLabel)fun[i]).label.setSize(140,15);
                        ((funcLabel)fun[i]).action.setBounds(140,0,((funcLabel)fun[i]).getWidth()-((funcLabel)fun[i]).label.getWidth(),15);
                  }
              }
              
          if (blockWidth + maxlevel*inBlockWidth > 600)
          {
            jPanel1.setSize(blockWidth + maxlevel*inBlockWidth,jPanel1.getHeight());
            jPanel1.setPreferredSize(new Dimension( blockWidth + maxlevel*inBlockWidth,jPanel1.getHeight()));
          }
          else
          {
            jPanel1.setSize(600,jPanel1.getHeight());
            jPanel1.setPreferredSize(new Dimension( 600,jPanel1.getHeight()));            
          }
/*
          int maxlevel = 0;
          //int maxwid = 0;
          Object[] funf = new Object[fun.length];
          for(int i=0;i<fun.length;i++)
            funf[i] = fun[i];
            
          for (int i = 0; i< funf.length; i++)
          {
              if(funf[i]!= null )
              {
                  if (funf[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)funf[i]).jButton1.getText().equals("+"))
                    funf[i] = null;
                  else if (funf[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)funf[i]).father > -1 && ((funcPanel)funf[((funcPanel)funf[i]).father]) == null  )
                    funf[i] = null;
                  else if (funf[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)funf[i]).father > -1 && ((funcPanel)funf[((funcLabel)funf[i]).father]) == null  )
                    funf[i] = null;
              }
          }
          
          for (int i = 0; i< fun.length; i++)
          {
            if(funf[i] != null)
              if (funf[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)funf[i]).level >= maxlevel ) 
              {
                  maxlevel = ((funcLabel)funf[i]).level;
                  //maxwid = ((funcLabel)fun[i]).getWidth();
              }
              else if (funf[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)funf[i]).level >= maxlevel ) 
              {
                  maxlevel = ((funcPanel)funf[i]).level;
                  //maxwid = ((funcPanel)fun[i]).getWidth();
              }
          }          

          funf[plusmjesto] = fun[plusmjesto] ;
          for(int i=0;i<fun.length;i++)
          {
            if(fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).level == 0)
              funf[i] = fun[i];
          }
          
          for(int i = 0;i <= maxlevel; i++)
            for(int j = 0; j< fun.length;j++)
            {
              if (funf[j]!= null)
                if (funf[j].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)funf[j]).level == i ) 
                    ((funcPanel)fun[j]).setSize(blockWidth + (maxlevel - i)*inBlockWidth,((funcPanel)fun[j]).getHeight());
                else if (funf[j].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)funf[j]).level == i ) 
                    ((funcLabel)fun[j]).setSize(blockWidth + (maxlevel - i)*inBlockWidth,((funcLabel)fun[j]).getHeight());

            }
            */
  }

  public void resizeHeight (int poz,int type,String tipAkcije)
  {
  
          if (fun[poz].getClass().toString().equals("class iisc.funcPanel"))
          {
              if (type == 0)
              {
                ((funcPanel)fun[poz]).setSize(((funcPanel)fun[poz]).getWidth(),15);
              }
              else 
              {
                int sum = 30;
                for(int i=0;i<fun.length;i++)
                {
                  if( i != poz || tipAkcije != "d")
                    if (fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).father == poz )
                      sum += ((funcPanel)fun[i]).getHeight();
                    else if (fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).father == poz)
                      sum += ((funcLabel)fun[i]).getHeight();              
                }
                ((funcPanel)fun[poz]).setSize(((funcPanel)fun[poz]).getWidth(),sum);
              }  
          }
          
          int otacc = -2;
          if ( fun[poz].getClass().toString().equals("class iisc.funcPanel") )
              otacc = ((funcPanel)fun[poz]).father;
          else if( fun[poz].getClass().toString().equals("class iisc.funcLabel") )
              otacc = ((funcLabel)fun[poz]).father;

          while (poz != -1)
          {
              int tmpx = inBlockWidth;
              int tmpy = 15;
              int tmphei = 15;
              
              if(tipAkcije == "d")
              {
                if (poz - 1 >= 0)
                {
                  if ( fun[poz-1].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[poz-1]).father == otacc)
                  {
                      tmpx = ((funcPanel)fun[poz-1]).getX();
                      tmpy = ((funcPanel)fun[poz-1]).getY();
                      tmphei = ((funcPanel)fun[poz-1]).getHeight();            
                      tmpy = tmpy + tmphei;                  
                  }
                  else if ( fun[poz-1].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[poz-1]).father == otacc )
                  {
                      tmpx = ((funcLabel)fun[poz-1]).getX();
                      tmpy = ((funcLabel)fun[poz-1]).getY();
                      tmphei = ((funcLabel)fun[poz-1]).getHeight();            
                      tmpy = tmpy + tmphei;
                  }
                }
              }
              else
              {
                if ( fun[poz].getClass().toString().equals("class iisc.funcPanel") )
                {
                    tmpx = ((funcPanel)fun[poz]).getX();
                    tmpy = ((funcPanel)fun[poz]).getY();
                    tmphei = ((funcPanel)fun[poz]).getHeight();            
                    tmpy = tmpy + tmphei;
                }
                else if ( fun[poz].getClass().toString().equals("class iisc.funcLabel") )
                {
                    tmpx = ((funcLabel)fun[poz]).getX();
                    tmpy = ((funcLabel)fun[poz]).getY();
                    tmphei = ((funcLabel)fun[poz]).getHeight();            
                    tmpy = tmpy + tmphei;
                }
              }

              int sum = 30;

              for (int i = 0; i < poz + 1; i++)
              {
                  if( i != poz || tipAkcije != "d")
                    if (fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).father == otacc )
                      sum += ((funcPanel)fun[i]).getHeight();
                    else if (fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).father == otacc)
                      sum += ((funcLabel)fun[i]).getHeight();
              }
                    
              for (int i = poz + 1; i < fun.length; i++)
              {
                    if (fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).father == otacc )
                    {
                        ((funcPanel)fun[i]).setBounds(tmpx,tmpy,((funcPanel)fun[i]).getWidth(),((funcPanel)fun[i]).getHeight());
                        tmpy = tmpy + ((funcPanel)fun[i]).getHeight();
                        sum += ((funcPanel)fun[i]).getHeight();
                    }
                    else if (fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).father == otacc)
                    {
                        ((funcLabel)fun[i]).setBounds(tmpx,tmpy,((funcLabel)fun[i]).getWidth(),((funcLabel)fun[i]).getHeight());
                        tmpy = tmpy + ((funcLabel)fun[i]).getHeight();
                        sum += ((funcLabel)fun[i]).getHeight();
                    }
              }
              if ( fun[poz].getClass().toString().equals("class iisc.funcPanel") )
                  poz = ((funcPanel)fun[poz]).father;
              else if ( fun[poz].getClass().toString().equals("class iisc.funcLabel") )
                  poz = ((funcLabel)fun[poz]).father;
                  
              if ( poz != -1)
              {
                  ((funcPanel)fun[poz]).setSize(((funcPanel)fun[poz]).getWidth(),sum);
                  otacc = ((funcPanel)fun[poz]).father;
              }
              tipAkcije = "l";
          }
          
          for(int i=0;i<fun.length;i++)
            if (fun[i].getClass().toString().equals("class iisc.funcPanel"))
              ((funcPanel)fun[i]).jButton1.setSize(((funcPanel)fun[i]).jButton1.getWidth(),((funcPanel)fun[i]).getHeight());
              
       
          int sum = 0;
          for(int i = 0;i < fun.length; i++)
             if (fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).level == 0 ) 
                sum += ((funcPanel)fun[i]).getHeight();
             else if (fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).level == 0 ) 
               sum += ((funcLabel)fun[i]).getHeight();
          
          if (sum > jScrollPane1.getHeight())
          {
              jPanel1.setSize(new Dimension(jPanel1.getWidth(),sum));
              jPanel1.setPreferredSize(new Dimension(jPanel1.getWidth(),sum));
          }
          else
          {
              jPanel1.setSize(new Dimension(jPanel1.getWidth(),jScrollPane1.getHeight()));
              jPanel1.setPreferredSize(new Dimension(jPanel1.getWidth(),jScrollPane1.getHeight()));
          }              
   
  }
/*
  private void resizeFun(int poz,int type,String a)
  {
      this.resizeHeight(poz,type,a);   
  }*/

/*
  private void kliklbl_propertyChange(PropertyChangeEvent e)
  {
  
        //System.out.println("type:  "+e.getPropertyName());
        if (e.getPropertyName().toString().equals("text"))
        {
          //String[] tmp =resizeLisener.getText().toString().split(",");
          //resizeFun( Integer.parseInt(tmp[0]) , Integer.parseInt(tmp[1]) );
          jButton1.setEnabled(true); 
          jButton2.setEnabled(true); 
          jButton3.setEnabled(true); 
          jButton4.setEnabled(true); 
          jButton5.setEnabled(true); 
          jButton6.setEnabled(true);           
          jButton7.setEnabled(true); 
          jButton8.setEnabled(true); 
          jButton9.setEnabled(true); 
          jButton11.setEnabled(true); 
          jButton13.setEnabled(true); 
          
          if (Integer.parseInt(kliklbl.getText())!= -1)
          {
              if( fun[Integer.parseInt(kliklbl.getText())].getClass().toString().equals("class iisc.funcPanel") )
              {
                  if (Integer.parseInt(kliklbl.getText()) == 0 || ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).father == 0)
                  {
                        jButton1.setEnabled(false); 
                        jButton2.setEnabled(false); 
                        jButton3.setEnabled(false); 
                        jButton4.setEnabled(false); 
                        jButton5.setEnabled(false); 
                        jButton6.setEnabled(false);           
                        jButton7.setEnabled(false); 
                        jButton8.setEnabled(false); 
                        jButton9.setEnabled(false); 
                        jButton11.setEnabled(false); 
                        jButton13.setEnabled(false);                     
                  }
                  else if(Integer.parseInt(kliklbl.getText()) == 1)
                  {
                        jButton3.setEnabled(false); 
                        jButton4.setEnabled(false); 
                        jButton5.setEnabled(false); 
                        jButton6.setEnabled(false);           
                        jButton7.setEnabled(false); 
                  }
                  else if( ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("IF CONDITIONAL") || ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("FOR LOOP") || ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("WHILE LOOP") )
                  {
                        jButton1.setEnabled(false); 
                        jButton2.setEnabled(false); 
                        jButton8.setEnabled(false); 
                        jButton9.setEnabled(false); 
                        jButton11.setEnabled(false); 
                        jButton13.setEnabled(false); 
                        
                        if (((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("IF CONDITIONAL") || ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("ELSE IF CONDITIONAL"))
                        {
                            jButton1.setEnabled(true);
                            for(int i=0;i<fun.length;i++)
                                if(fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).father == Integer.parseInt(kliklbl.getText()) && ((funcPanel)fun[i]).name.getText().equals("ELSE") )
                                {
                                    jButton1.setEnabled(false);                                       
                                    break;
                                }
                        }
                  }
                  else if( ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("EXCEPTION") )
                  {
                        jButton4.setEnabled(false); 
                        jButton5.setEnabled(false); 
                        jButton6.setEnabled(false); 
                        jButton7.setEnabled(false); 
                        jButton13.setEnabled(false); 
                  }
                  else if( ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("THEN") || ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("ELSE")  )
                  {
                        jButton4.setEnabled(false); 
                        jButton5.setEnabled(false); 
                        jButton6.setEnabled(false); 
                        jButton7.setEnabled(false); 
                        
                        if (((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("THEN"))
                              jButton3.setEnabled(false);
                  }
                  else if( ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("BODY") && ( ((funcPanel)fun[((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).father]).name.getText().equals("FOR LOOP") || ((funcPanel)fun[((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).father]).name.getText().equals("WHILE LOOP") ))
                  {
                        jButton3.setEnabled(false);                   
                        jButton4.setEnabled(false); 
                        jButton5.setEnabled(false); 
                        jButton6.setEnabled(false); 
                        jButton7.setEnabled(false); 
                  }
                /*  else if( ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("BODY") )
                  {
                      jButton4.setEnabled(false);
                      jButton5.setEnabled(false);
                      jButton6.setEnabled(false);
                      jButton7.setEnabled(false);
                      jButton3.setEnabled(false); 
                  }
                  else if( ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("IF CONDITIONAL") ||  ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("WHILE LOOP") ||  ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("FOR LOOP") )                  
                  {
                      jButton1.setEnabled(false); 
                      jButton2.setEnabled(false);                   
                      jButton8.setEnabled(false);
                      jButton9.setEnabled(false);  
                      jButton11.setEnabled(false);                  
                  }
                  else if( ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("EXCEPTION") )
                  {
                      jButton4.setEnabled(false);
                      jButton5.setEnabled(false);
                      jButton6.setEnabled(false);
                      jButton7.setEnabled(false);
                      jButton13.setEnabled(false);
                  }
                  else if( ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("BODY WHILE") || ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("-BODY-DO-WHILE-") || ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("-BODY-FOR-") || ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("-THEN-") || ((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("-ELSE-"))
                  {
                      jButton4.setEnabled(false);
                      jButton5.setEnabled(false);
                      jButton6.setEnabled(false);
                      jButton7.setEnabled(false);
                      jButton3.setEnabled(false);
                      if(((funcPanel)fun[Integer.parseInt(kliklbl.getText())]).name.getText().equals("-ELSE-"))
                      {
                          jButton3.setEnabled(true);    
                      }
                  }                  
                  */
                  /*
              }
              else if( fun[Integer.parseInt(kliklbl.getText())].getClass().toString().equals("class iisc.funcLabel") )
              {
                  if ( ((funcLabel)fun[Integer.parseInt(kliklbl.getText())]).father == 0 )
                  {
                      jButton1.setEnabled(false); 
                      jButton2.setEnabled(false); 
                      jButton3.setEnabled(false); 
                      jButton4.setEnabled(false); 
                      jButton5.setEnabled(false); 
                      jButton6.setEnabled(false);           
                      jButton7.setEnabled(false); 
                      jButton8.setEnabled(false); 
                      jButton9.setEnabled(false); 
                      jButton11.setEnabled(false); 
                      jButton13.setEnabled(false);                      
                  }
              }
          }
          
          for(int i=0;i<fun.length;i++)
            if (i != Integer.parseInt(kliklbl.getText()))
            {
                if ( fun[i].getClass().toString().equals("class iisc.funcPanel") )
                    ((funcPanel)fun[i]).setBackground(Color.WHITE);                                      
                else if ( fun[i].getClass().toString().equals("class iisc.funcLabel") )
                    ((funcLabel)fun[i]).setBackground(Color.WHITE);
            }
            else
            {
                if ( fun[i].getClass().toString().equals("class iisc.funcPanel") )
                    ((funcPanel)fun[i]).setBackground(new Color(216,216,216));
                else if ( fun[i].getClass().toString().equals("class iisc.funcLabel") )
                    ((funcLabel)fun[i]).setBackground(new Color(216,216,216));              
            }
          
    /*      if ( e.getOldValue()!= null )
            System.out.println("OLD:  "+e.getOldValue());
          if ( e.getNewValue()!= null )
            System.out.println("NEW:  "+e.getNewValue());  
    
        }  
        
  }
*/
/*  private void replacedlbl_propertyChange(PropertyChangeEvent e)
  {
      if (e.getPropertyName().toString().equals("text"))
      {
          copypressed = this.pressed;
          copyreleased = this.released;
          boolean ok = false;
          if (copypressed == copyreleased)
            System.out.println("isti");
          else
          {
              //int kliknut = Integer.parseInt(funAlg.kliklbl.getText());
              toDo="";
              System.out.println("razliciti");
              System.out.println("Pressed:"+copypressed); 
              System.out.println("released:"+copyreleased);               
              JPopupMenu popupMenu = new JPopupMenu();    
              JMenu copy = new JMenu("Copy");
              JMenu move = new JMenu("Move");

              JMenuItem menuItem1 = new JMenuItem("Copy Above"); 
              JMenuItem menuItem2 = new JMenuItem("Copy in Block"); 
              JMenuItem menuItem3 = new JMenuItem("Copy After"); 
              
              JMenuItem menuItem4 = new JMenuItem("Move Above"); 
              JMenuItem menuItem5 = new JMenuItem("Move in Block"); 
              JMenuItem menuItem6 = new JMenuItem("Move After"); 
              
              if( fun[this.copypressed].getClass().toString().equals("class iisc.funcPanel") && fun[this.copyreleased].getClass().toString().equals("class iisc.funcPanel"))
              {
                    if( ((funcPanel)fun[this.copypressed]).name.getText().equals("THEN") && ((funcPanel)fun[this.copyreleased]).name.getText().equals("ELSE"))
                    {
                        if ( ((funcPanel)fun[this.copypressed]).father == ((funcPanel)fun[this.copyreleased]).father)
                        {
                            menuItem6.setText("Rotate THEN - ELSE");
                            popupMenu.add(menuItem6);
                            toDo = "rotate then->else";
                            ok = true;
                        }
                    }
                    else if( ((funcPanel)fun[this.copypressed]).name.getText().equals("ELSE") && ((funcPanel)fun[this.copyreleased]).name.getText().equals("THEN"))
                    {
                        if ( ((funcPanel)fun[this.copypressed]).father == ((funcPanel)fun[this.copyreleased]).father)
                        {
                            menuItem4.setText("Rotate ELSE - THEN");
                            popupMenu.add(menuItem4);
                            toDo = "rotate else->then";
                            ok = true;
                        }
                    }
                    else if( ((funcPanel)fun[this.copypressed]).name.getText().equals("EXCEPTION") && ((funcPanel)fun[this.copyreleased]).name.getText().equals("EXCEPTION"))
                    {
                            popupMenu.add(menuItem1);
                            popupMenu.add(menuItem3);
                            popupMenu.addSeparator();
                            popupMenu.add(menuItem4);
                            popupMenu.add(menuItem6);                            
                            ok = true;   
                    }                    
                    else if(( ((funcPanel)fun[this.copypressed]).name.getText().equals("-IF-CONDITIONAL-") || ((funcPanel)fun[this.copypressed]).name.getText().equals("-DO-WHILE-LOOP-") || ((funcPanel)fun[this.copypressed]).name.getText().equals("-WHILE-LOOP-") || ((funcPanel)fun[this.copypressed]).name.getText().equals("-FOR-LOOP-") || ((funcPanel)fun[this.copypressed]).name.getText().equals("-EXCEPTION-"))&& ( ((funcPanel)fun[this.released]).name.getText().equals("-THEN-") || 
                    ((funcPanel)fun[this.copyreleased]).name.getText().equals("-ELSE-") || ((funcPanel)fun[this.copyreleased]).name.getText().equals("-BODY-DO-WHILE-") || ((funcPanel)fun[this.copyreleased]).name.getText().equals("-BODY-WHILE-") || ((funcPanel)fun[this.copyreleased]).name.getText().equals("-BODY-FOR-") || ((funcPanel)fun[this.copyreleased]).name.getText().equals("-BODY-FUNCTION-") || ((funcPanel)fun[this.copyreleased]).name.getText().equals("-EXCEPTION-")) )
                    {
                            popupMenu.add(menuItem2);
                            popupMenu.add(menuItem5);
                            ok = true;
                    }
                    else if(( ((funcPanel)fun[this.copypressed]).name.getText().equals("-IF-CONDITIONAL-") || ((funcPanel)fun[this.copypressed]).name.getText().equals("-DO-WHILE-LOOP-") || ((funcPanel)fun[this.copypressed]).name.getText().equals("-WHILE-LOOP-") || ((funcPanel)fun[this.copypressed]).name.getText().equals("-FOR-LOOP-") || ((funcPanel)fun[this.copypressed]).name.getText().equals("-EXCEPTION-"))&& ( ((funcPanel)fun[this.released]).name.getText().equals("-IF-CONDITIONAL-") || 
                    ((funcPanel)fun[this.copyreleased]).name.getText().equals("-DO-WHILE-LOOP-") || ((funcPanel)fun[this.copyreleased]).name.getText().equals("-WHILE-LOOP-") || ((funcPanel)fun[this.copyreleased]).name.getText().equals("-FOR-LOOP-") || ((funcPanel)fun[this.copyreleased]).name.getText().equals("-EXCEPTION-")))
                    {
                            popupMenu.add(menuItem1);
                            popupMenu.add(menuItem3);
                            popupMenu.addSeparator();
                            popupMenu.add(menuItem4);
                            popupMenu.add(menuItem6);                            
                            ok = true;                        
                    }
              }
              else if( fun[this.copypressed].getClass().toString().equals("class iisc.funcLabel") && fun[this.copyreleased].getClass().toString().equals("class iisc.funcLabel")) 
                {}
              else if( fun[this.copypressed].getClass().toString().equals("class iisc.funcPanel") && fun[this.copyreleased].getClass().toString().equals("class iisc.funcLabel")) 
                {}
              else if( fun[this.copypressed].getClass().toString().equals("class iisc.funcLabel") && fun[this.copyreleased].getClass().toString().equals("class iisc.funcPanel")) 
                {
                    if(((JLabel)((funcLabel)fun[this.copypressed]).label.getSelectedItem()).getText().equals("Condition") && ((funcPanel)fun[this.copyreleased]).name.getText().equals("-BODY-WHILE-"))
                    {
                        if ( ((funcLabel)fun[this.copypressed]).father == ((funcPanel)fun[this.copyreleased]).father)
                        {
                            menuItem6.setText("Convert WHILE to DO WHILE LOOP");
                            popupMenu.add(menuItem6);
                            toDo = "convert while->dowhile";
                            ok = true;
                        }
                    }
                    else if( ((JLabel)((funcLabel)fun[this.copypressed]).label.getSelectedItem()).getText().equals("Condition") && ((funcPanel)fun[this.copyreleased]).name.getText().equals("-BODY-DO-WHILE-"))
                    {
                        if ( ((funcLabel)fun[this.copypressed]).father == ((funcPanel)fun[this.copyreleased]).father)
                        {
                            menuItem4.setText("Convert DO WHILE to WHILE LOOP");
                            popupMenu.add(menuItem4);
                            toDo = "convert dowhile->while";
                            ok = true;
                        }
                    }
                    else if( ((JLabel)((funcLabel)fun[this.copypressed]).label.getSelectedItem()).getText().equals("Action") && (((funcPanel)fun[this.copyreleased]).name.getText().equals("-BODY-DO-WHILE-") ||  ((funcPanel)fun[this.copyreleased]).name.getText().equals("-BODY-WHILE-") || ((funcPanel)fun[this.copyreleased]).name.getText().equals("-BODY-FOR-") || ((funcPanel)fun[this.copyreleased]).name.getText().equals("-THEN-") || ((funcPanel)fun[this.copyreleased]).name.getText().equals("-ELSE-") 
                      || ((funcPanel)fun[this.copyreleased]).name.getText().equals("-BODY-FUNCTION-") || ((funcPanel)fun[this.copyreleased]).name.getText().equals("-EXCEPTION-")))
                    {
                        //copy.add(menuItem2);
                        //move.add(menuItem5);
                        popupMenu.add(menuItem2);
                        popupMenu.addSeparator();
                        popupMenu.add(menuItem5);                        
                        ok = true;
                    }
                }
 
              menuItem1.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  popup_actionPerformed1(e);
                }
              });                
              menuItem2.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  popup_actionPerformed2(e);
                }
              });              
              menuItem3.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  popup_actionPerformed3(e);
                }
              });                 
              
              menuItem4.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  popup_actionPerformed4(e);
                }
              });                
              menuItem5.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  popup_actionPerformed5(e);
                }
              });              
              menuItem6.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  popup_actionPerformed6(e);
                }
              });                
              
              if(!ok)
              {
                  menuItem2.setText("Cannot drag&drop this block");
                  menuItem2.setEnabled(false);
                  popupMenu.add(menuItem2);
              }
                    popupMenu.setSelected(popupMenu);
                    if (fun[released].getClass().toString().equals("class iisc.funcPanel"))
                      popupMenu.show((funcPanel)fun[released],replacex,replacey);
                    else if (fun[released].getClass().toString().equals("class iisc.funcLabel"))
                      popupMenu.show((funcLabel)fun[released],replacex,replacey);
                    
                    this.clicked = this.released;
                    //this.kliklbl.setText(""+this.released);
          }
      }
  }
*/


  private void jButton3_actionPerformed(ActionEvent e)
  {
      System.out.println("Brisem:"+this.clicked);
      fun[this.clicked].delFlag = "ok";
      delete();
  }
  
  public void delete()
  {
          int cvor = -1;
          
          for(int i=0;i<fun.length;i++)
              if (fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).delFlag.equals("ok"))
              {
                    cvor = i;
                    break;
              }
              else if (fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).delFlag.equals("ok"))
              {
                    cvor = i;
                    break;                
              }
          
          if (cvor == -1)
            return;
            
          int delno = 1;
          
          resizeHeight(cvor,1,"d");  
          
          fun[cvor]=null;
          for(int i =0; i<fun.length;i++)
          {
            if(fun[i]!=null)
            {
                if ( ( fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).father > -1 && fun[((funcPanel)fun[i]).father] == null) || ( fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).father > -1 && fun[((funcLabel)fun[i]).father] == null))
                {
                    fun[i] = null;
                    delno++;
                }
            }
          }
      
          int nullno = 0;
          int i=0;
          while(true)
          {
                  int j;
                  for( j=i;j<fun.length && fun[j] == null;j++)
                    nullno++;
                  
                  for(i=j;i<fun.length && fun[i] != null;i++)
                  {
                    for(int z=0;z<fun.length;z++)
                      if(fun[z]!=null)
                        if(fun[z].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[z]).father == i)
                            ((funcPanel)fun[z]).father -= nullno;
                        else if(fun[z].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[z]).father == i)
                            ((funcLabel)fun[z]).father -= nullno;
                  }
                  if (i>=fun.length)
                    break;
          }             
      
          funcElement[] tmp = new funcElement[fun.length-delno];
          i = 0;
          int j = 0;
          
          while (i<fun.length)
          {
            if (fun[i]!=null)
              tmp[j++]=fun[i];
              
            i++;
          }
      
          fun = new funcElement[tmp.length];
          for(i=0;i<tmp.length;i++)
          {
            fun[i]=tmp[i];
            if( fun[i].getClass().toString().equals("class iisc.funcPanel"))
              ((funcPanel)fun[i]).pozition = i;
            else if( fun[i].getClass().toString().equals("class iisc.funcLabel"))
              ((funcLabel)fun[i]).pozition = i;      
          }
          
          jPanel1.removeAll(); 
          jPanel1.revalidate();
      
          for (i = 0; i < fun.length; i++)
          {
                if (fun[i].getClass().toString().equals("class iisc.funcPanel"))
                {
                  ((funcPanel)fun[i]).removeAll();
                  ((funcPanel)fun[i]).add(((funcPanel)fun[i]).name);
                  ((funcPanel)fun[i]).add(((funcPanel)fun[i]).jButton1);
                  if (((funcPanel)fun[i]).father == -1)
                    jPanel1.add((funcPanel)fun[i],null);
                  else
                    ((funcPanel)fun[((funcPanel)fun[i]).father]).add((funcPanel)fun[i],null);
                  ((funcPanel)fun[i]).revalidate();
                }
                else if (fun[i].getClass().toString().equals("class iisc.funcLabel"))
                {
                  //((funcLabel)fun[i]).add(((funcLabel)fun[i]).action);
                  //((funcLabel)fun[i]).add(((funcLabel)fun[i]).label);
                  if (((funcLabel)fun[i]).father == -1)
                    jPanel1.add((funcLabel)fun[i],null);
                  else
                    ((funcPanel)fun[((funcLabel)fun[i]).father]).add((funcLabel)fun[i],null);
                }            
          }
          
          //jPanel1.revalidate();  
          this.clicked = 0;
          //kliklbl.setText("0");
          System.out.println("DUZINA: "+fun.length);
          resizeWidth();
          //resizePanel();
  }

  private void jButton4_actionPerformed(ActionEvent e)
  {
      addBlockBefore();
  }
  
  public void addBlockBefore()
  {
        this.pozklik = -1;
        add("class iisc.funcPanel");   
        fun[0].fillPanel();
  }

  private void jButton5_actionPerformed(ActionEvent e)
  {
      addBlockAfter();
  }
  
  public void addBlockAfter()
  {
        this.pozklik = 1;
        add("class iisc.funcPanel"); 
        fun[0].fillPanel();
  }

  private void jButton6_actionPerformed(ActionEvent e)
  {
      addActionBefore();
  }
  
  public void addActionBefore()
  {
        this.pozklik = -1;
        add("class iisc.funcLabel"); 
        fun[0].fillPanel();
  }

  private void jButton7_actionPerformed(ActionEvent e)
  {
      addActionAfter();
  }
  
  public void addActionAfter()
  {
        this.pozklik = 1;
        add("class iisc.funcLabel");  
        fun[0].fillPanel();
  }

  private void jButton8_actionPerformed(ActionEvent e)
  {
      addForLoop();
  }
  
  public void addForLoop()
  {
        //String kliknuo = this.kliklbl.getText();
        int kliknuo = this.clicked;
        if(kliknuo == -1)
          return;
        
        System.out.println("Last Insert:"+this.lastAdd);
        
        this.pozklik = 0;
        add("class iisc.funcPanel");
        ((funcPanel)fun[lastAdd]).name.setText("FOR LOOP");
        
        //kliklbl.setText(""+lastAdd);
        this.clicked = this.lastAdd;
        add("class iisc.funcLabel");
        ((funcLabel)fun[lastAdd]).label.removeAllItems();
        ((funcLabel)fun[lastAdd]).label.addItem(new JLabel("Begin"));
        //((funcLabel)fun[lastAdd]).label.setText("Begin");
        ((funcLabel)fun[lastAdd]).action.setText("i = 0");
        add("class iisc.funcLabel");
        ((funcLabel)fun[lastAdd]).label.removeAllItems();
        ((funcLabel)fun[lastAdd]).label.addItem(new JLabel("Condition"));
        //((funcLabel)fun[lastAdd]).label.setText("Condition");
        ((funcLabel)fun[lastAdd]).action.setText("i < 5");    
        add("class iisc.funcLabel");
        ((funcLabel)fun[lastAdd]).label.removeAllItems();
        ((funcLabel)fun[lastAdd]).label.addItem(new JLabel("Step"));
        //((funcLabel)fun[lastAdd]).label.setText("Step");
        ((funcLabel)fun[lastAdd]).action.setText("i++");    
        add("class iisc.funcPanel");
        ((funcPanel)fun[lastAdd]).name.setText("BODY");    
        
        //this.kliklbl.setText(kliknuo);    
        this.clicked = kliknuo;
        fun[0].fillPanel();
  }

  private void jButton9_actionPerformed(ActionEvent e)
  {
        addWhileLoop();
  }
  
  public void addWhileLoop()
  {
        //String kliknuo = this.kliklbl.getText();
        int kliknuo = this.clicked;
        if(kliknuo == -1)
          return;
          
        System.out.println("Last Insert:"+this.lastAdd);
        
        this.pozklik = 0;
        add("class iisc.funcPanel");
        ((funcPanel)fun[lastAdd]).name.setText("WHILE LOOP");
        
        int blok = lastAdd;
        //kliklbl.setText(""+lastAdd);
        this.clicked = this.lastAdd;
        add("class iisc.funcLabel");
        ((funcLabel)fun[lastAdd]).label.removeAllItems();
        ((funcLabel)fun[lastAdd]).label.addItem(new JLabel("Condition"));
        //((funcLabel)fun[lastAdd]).label.setText("Condition");
        ((funcLabel)fun[lastAdd]).action.setText("i < 5");    
        add("class iisc.funcPanel");
        ((funcPanel)fun[lastAdd]).name.setText("BODY");       
        //kliklbl.setText(""+lastAdd);
        this.clicked = this.lastAdd;
        add("class iisc.funcLabel");
        ((funcLabel)fun[lastAdd]).label.setSelectedItem("Action");
        //((funcLabel)fun[lastAdd]).label.setText("Action");
        ((funcLabel)fun[lastAdd]).action.setText("i++");    
        
        //this.kliklbl.setText(kliknuo);
        this.clicked = kliknuo;
        fun[0].fillPanel();
  }

  private void jButton10_actionPerformed(ActionEvent e)
  {
        addComments();
  }

  private void addComments()
  {
        this.pozklik = 0;
        add("class iisc.funcPanel");
        ((funcPanel)fun[lastAdd]).name.setText("COMMENTS");    
  }  

  private void jButton11_actionPerformed(ActionEvent e)
  {
      addIf();
  }
  
  public void addIf()
  {
        //String kliknuo = this.kliklbl.getText();
        int kliknuo = this.clicked;
        if(kliknuo == -1)
          return;
          
        //System.out.println("Last Insert:"+this.lastAdd);
        if (fun[kliknuo].getClass().toString().equals("class iisc.funcPanel") &&  ((funcPanel)fun[kliknuo]).name.getText().equals("ELSE") && ((funcPanel)fun[kliknuo]).father >=0 && ((funcPanel)fun[((funcPanel)fun[kliknuo]).father]).name.getText().equals("IF CONDITIONAL"))
        {
              //((funcPanel)fun[kliknuo]).name.setText("ELSE IF CONDITIONAL");
              System.out.println("Prvi uslov");
              int durgiKlik = ((funcPanel)fun[kliknuo]).father;
              ((funcPanel)fun[kliknuo]).delFlag = "ok";
              delete();
              this.clicked = durgiKlik;
              this.pozklik = 0;
              add("class iisc.funcPanel");
              ((funcPanel)fun[lastAdd]).name.setText("ELSE IF CONDITIONAL");
              this.clicked = this.lastAdd;
              this.pozklik = 0;
              
              add("class iisc.funcLabel");
              ((funcLabel)fun[lastAdd]).label.removeAllItems();
              ((funcLabel)fun[lastAdd]).label.addItem(new JLabel("Condition"));
              //((funcLabel)fun[lastAdd]).label.setText("Condition");
              ((funcLabel)fun[lastAdd]).action.setText("true");              
              
              add("class iisc.funcPanel");
              ((funcPanel)fun[lastAdd]).name.setText("THEN");
              
              add("class iisc.funcPanel");
              ((funcPanel)fun[lastAdd]).name.setText("ELSE");
              
              //this.kliklbl.setText(kliknuo);              
              this.clicked = kliknuo;
              
              //return;
        }
        else if (fun[kliknuo].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[kliknuo]).name.getText().equals("ELSE") && ((funcPanel)fun[kliknuo]).father >=0 && ((funcPanel)fun[((funcPanel)fun[kliknuo]).father]).name.getText().equals("ELSE IF CONDITIONAL"))
        {
              System.out.println("Drugi uslov");
              int otacc = ((funcPanel)fun[kliknuo]).father;
              ((funcPanel)fun[kliknuo]).delFlag = "ok";
              delete(); 
              //kliklbl.setText(""+otacc);
              this.clicked = otacc;
              this.pozklik = 1;
              
              add("class iisc.funcPanel");
              ((funcPanel)fun[lastAdd]).name.setText("ELSE IF CONDITIONAL");  
              
              //kliklbl.setText(""+this.lastAdd);
              this.clicked = this.lastAdd;
              this.pozklik = 0;

              add("class iisc.funcLabel");
              ((funcLabel)fun[lastAdd]).label.removeAllItems();
              ((funcLabel)fun[lastAdd]).label.addItem(new JLabel("Condition"));
              //((funcLabel)fun[lastAdd]).label.setText("Condition");
              ((funcLabel)fun[lastAdd]).action.setText("true");              
              
              add("class iisc.funcPanel");
              ((funcPanel)fun[lastAdd]).name.setText("THEN");
              
              add("class iisc.funcPanel");
              ((funcPanel)fun[lastAdd]).name.setText("ELSE");
              
              //this.kliklbl.setText(kliknuo);              
              this.clicked = kliknuo;
              
              //return;            
            
        }
        else
        {
              System.out.println("Treci uslov");
              this.pozklik = 0;
              add("class iisc.funcPanel");
              ((funcPanel)fun[lastAdd]).name.setText("IF CONDITIONAL");
              //ImageIcon logo = new ImageIcon(IISFrameMain.class.getResource("icons/question.gif"));
              //((funcPanel)fun[lastAdd]).name.setIcon(logo);
              //int blok = lastAdd;
              //kliklbl.setText(""+lastAdd);
              this.clicked = this.lastAdd;
          
              add("class iisc.funcLabel");
              ((funcLabel)fun[lastAdd]).label.removeAllItems();
              ((funcLabel)fun[lastAdd]).label.addItem(new JLabel("Condition"));
              //((funcLabel)fun[lastAdd]).label.setText("Condition");
              ((funcLabel)fun[lastAdd]).action.setText("true");     
              
              add("class iisc.funcPanel");
              ((funcPanel)fun[lastAdd]).name.setText("THEN");
              
              add("class iisc.funcPanel");
              ((funcPanel)fun[lastAdd]).name.setText("ELSE");
              
              //this.kliklbl.setText(kliknuo);    
              this.clicked = kliknuo;
        }
        fun[0].fillPanel();
  }

  private void jButton12_actionPerformed(ActionEvent e)
  {
        System.out.println("++++++++++++++++++++");
        for(int i=0;i<fun.length;i++)
            if (fun[i].getClass().toString().equals("class iisc.funcPanel"))
                System.out.println("Pozicija :"+i+"  otac :"+((funcPanel)fun[i]).father+"   :"+((funcPanel)fun[i]).name.getText());
            else if (fun[i].getClass().toString().equals("class iisc.funcLabel"))
                System.out.println("Pozicija :"+i+"  otac :"+((funcLabel)fun[i]).father+"   :"+((JLabel)((funcLabel)fun[i]).label.getSelectedItem()).getText()+ "  :  "+((funcLabel)fun[i]).action.getText());
        System.out.println("++++++++++clickde++++++++++"+this.clicked);
  }

 
 public void copy(int copypressed,int copyreleased)
 {
      int[] stack1 = new int[1];
      int[] stack2 = new int[1];

      //stack1[0]= this.copypressed;
      //stack2[0]= this.copyreleased;
      
      stack1[0]= copypressed;
      stack2[0]= copyreleased;
       
      Object[] funtmp = new Object[fun.length];
       
      for(int i=0;i<fun.length;i++)
      {
            if( fun[i].getClass().toString().equals("class iisc.funcPanel") )
            {
                funtmp[i] = new funcPanel(i,((funcPanel)fun[i]).father,((funcPanel)fun[i]).level,this);
                ((funcPanel)funtmp[i]).name.setText(((funcPanel)fun[i]).name.getText());
            }
            else if( fun[i].getClass().toString().equals("class iisc.funcLabel") )
            {
                funtmp[i] = new funcLabel(i,((funcLabel)fun[i]).father,((funcLabel)fun[i]).level,this);
                
                JLabel tmpw = ((JLabel)((funcLabel)fun[i]).label.getSelectedItem());
                //((funcLabel)funtmp[i]).label = ((funcLabel)fun[i]).label;
                ((funcLabel)funtmp[i]).label.removeAllItems();
                for(int yu=0;yu<((funcLabel)fun[i]).label.getItemCount();yu++)
                {
                  ((funcLabel)funtmp[i]).label.addItem(((funcLabel)fun[i]).label.getItemAt(yu));
                }
                ((funcLabel)funtmp[i]).label.setSelectedItem(tmpw);
                //((funcLabel)funtmp[i]).label.setText(((funcLabel)fun[i]).label.getText());
                ((funcLabel)funtmp[i]).action.setText(((funcLabel)fun[i]).action.getText());
            }
      }
        
      int i=stack1.length-1;
      while(i>=0)
      {
          if( funtmp[stack1[i]].getClass().toString().equals("class iisc.funcPanel") )
          {
                //kliklbl.setText(""+stack2[i]);
                this.clicked = stack2[i];
                add("class iisc.funcPanel");
                ((funcPanel)fun[lastAdd]).name.setText(((funcPanel)funtmp[stack1[i]]).name.getText());
                
                if(this.lastAdd <= this.pressed)
                  this.pressed++;
                  
                //kliklbl.setText(""+lastAdd);
                int brojac = 0;                
                for(int k=0;k<funtmp.length;k++)
                      if ((funtmp[k].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)funtmp[k]).father == stack1[i] ) || (funtmp[k].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)funtmp[k]).father == stack1[i] ))
                        brojac++;
                
                int duzina = stack1.length-1+brojac;
                
                int[] tmps1 = new int[stack1.length-1];
                int[] tmps2 = new int[stack1.length-1];
                
                for(int k=stack1.length-2;k>=0;k--)
                {
                    tmps1[k]=stack1[k];
                    tmps2[k]=stack2[k];
                }
                
                int otacs = stack1[i];
                stack1 = new int[duzina];
                stack2 = new int[duzina];
                int korak=0;
                for(korak=0;korak<tmps1.length;korak++)
                {
                    stack1[korak]=tmps1[korak];
                    stack2[korak]=tmps2[korak];
                }
                for(int k=funtmp.length-1;k>=0;k--)
                  if ((funtmp[k].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)funtmp[k]).father == otacs ) || (funtmp[k].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)funtmp[k]).father == otacs ))
                  {
                      stack1[korak]=k;
                      stack2[korak++]=this.lastAdd;
                  }
                
                i=stack1.length;//jer na kraju  je i--;
          }
          else if( funtmp[stack1[i]].getClass().toString().equals("class iisc.funcLabel") )
          {
                //kliklbl.setText(""+stack2[i]);
                this.clicked = stack2[i];
                add("class iisc.funcLabel");
                JLabel tmpl = ((JLabel)((funcLabel)funtmp[stack1[i]]).label.getSelectedItem());
                ((funcLabel)fun[lastAdd]).label.removeAllItems();
                for(int yu=0;yu<((funcLabel)funtmp[stack1[i]]).label.getItemCount();yu++)
                {
                    ((funcLabel)fun[lastAdd]).label.addItem(((funcLabel)funtmp[stack1[i]]).label.getItemAt(yu));
                }
                //((funcLabel)fun[lastAdd]).label = ((funcLabel)funtmp[stack1[i]]).label;
                ((funcLabel)fun[lastAdd]).label.setSelectedItem(tmpl);
                //((funcLabel)fun[lastAdd]).label.setText(((funcLabel)funtmp[stack1[i]]).label.getText());
                ((funcLabel)fun[lastAdd]).action.setText(((funcLabel)funtmp[stack1[i]]).action.getText()); 
           
                if(this.lastAdd <= this.pressed)
                  this.pressed++;
                  
                int[] tmps1 = new int[stack1.length-1];
                int[] tmps2 = new int[stack1.length-1];
                
                for(int k=stack1.length-2;k>=0;k--)
                {
                    tmps1[k]=stack1[k];
                    tmps2[k]=stack2[k];
                }

                stack1 = new int[tmps1.length];
                stack2 = new int[tmps2.length];
                
                int korak=0;
                for(korak=0;korak<tmps1.length;korak++)
                {
                    stack1[korak]=tmps1[korak];
                    stack2[korak]=tmps2[korak];
                }
                
          }
          this.pozklik = 0;
          i--;
      }
        
 }


  private void this_componentResized(ComponentEvent e)
  {
    jPanel2.setSize(jPanel2.getWidth(),jPanel2.getHeight());
    jScrollPane1.setSize(this.getWidth()-10,this.getHeight()-jPanel2.getHeight()-jMenuBar1.getHeight()-35);
    resizeWidth();
    //resizePanel();
  }

  private void jButton13_actionPerformed(ActionEvent e)
  {
      addException();
  }
  
  public void addException()
  {
        //String kliknuo = this.kliklbl.getText();
        int kliknuo = this.clicked;
        if(kliknuo == -1)
          return;
          
        System.out.println("Last Insert:"+this.lastAdd);
        
        this.pozklik = 0;
        add("class iisc.funcPanel");
        ((funcPanel)fun[lastAdd]).name.setText("EXCEPTION");
        //kliklbl.setText(""+lastAdd);
        this.clicked = this.lastAdd;
        //this.kliklbl.setText(kliknuo);     
        this.clicked = kliknuo;
        fun[0].fillPanel();
  }

  private void this_windowClosing(WindowEvent e)
  {
    this.dispose();
  }

  private void jButton16_actionPerformed(ActionEvent e)
  {
      addDeclaration();      
  }

  private void jButton14_actionPerformed(ActionEvent e)
  {
      if( fun[this.clicked].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[this.clicked]).name.getText().equals("DECLARATION"))
      {
              this.pozklik = 0;
              add("class iisc.funcLabel");
              ((funcLabel)fun[lastAdd]).itemsForDeclaration();
              //((funcLabel)fun[lastAdd]).label.addItem(new JLabel("Constant"));
              //((funcLabel)fun[lastAdd]).label.setText("Condition");
              ((funcLabel)fun[lastAdd]).action.setText("");         
      }
  }

  private void jButton17_actionPerformed(ActionEvent e)
  {
      addLocalFunction();
  }
  
  private void addLocalFunction()
  {
        this.pozklik = 0;
        add("class iisc.funcPanel");
        ((funcPanel)fun[lastAdd]).name.setText("LOCAL FUNCTION");    
  }

  private void jMenuItem1_actionPerformed(ActionEvent e)
  {
      load();
  }
  
  private void load()
  {
    try
    {
                  JDBCQuery query=new JDBCQuery(connections);
                  JDBCQuery query2=new JDBCQuery(connections);
                  ResultSet rs1,rs2;
                  rs2=query2.select("select * from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.PR_id = D.PR_id AND F.Fun_id = "+id+" order by F.Param_seq");    
                  int duz=0;
                  while(rs2.next())
                     duz++;

                  query2.Close();
    
                  rs1=query.select("select * from IISC_Function where Fun_id="+id);
                  while(rs1.next())
                  {
                      String funname = rs1.getString("Fun_name");
                      
                      fun = new funcElement[duz + 2];
                      fun[0] = new funcPanel(0,-1,0,this);
                      ((funcPanel)fun[0]).name.setText("FUNCTION "+funname+"-");
                      fun[1] = new funcPanel(1,-1,0,this);
                      ((funcPanel)fun[1]).name.setText("BODY");
                      jPanel1.add(((funcPanel)fun[1]));            
                  
                      rs2=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.PR_id = D.PR_id AND F.Fun_id = "+id+" order by F.Param_seq");    
                      int i=2;
                      while(rs2.next())
                      {
                          String domtemp= rs2.getString("Dom_mnem");
                          String param= rs2.getString("Param_name");
                          fun[i] = new funcLabel(i,0,1,this);
                          ((funcPanel)fun[0]).add((funcLabel)fun[i],null); 
                          ((funcLabel)fun[i]).setBounds(inBlockWidth,15*(i-2+1),blockWidth,15);
                          ((funcLabel)fun[i]).label.removeAllItems();
                          ((funcLabel)fun[i]).label.addItem(new JLabel("Param"));
                          //((funcLabel)fun[i]).label.setText("Param");
                          ((funcLabel)fun[i]).action.setText("("+domtemp+") "+param);
                          i++;
                      }
                      query2.Close();
                  }
                  ((funcPanel)fun[0]).setBounds(0,0,inBlockWidth+blockWidth,30+15*duz); 
                  query.Close();              
                        
    }
    catch(Exception ex)
    {
        System.out.println("GRESKA-LOAD:"+ex.toString());
    }
  }

  private void jMenuItem3_actionPerformed(ActionEvent e)
  {
      save();
  }
  
  private void save()
  {
    try
    {
                  JDBCQuery query=new JDBCQuery(connections);
                  JDBCQuery query2=new JDBCQuery(connections);
                  ResultSet rs1,rs2;
                  
                  int a = query.update("delete from IISC_FUN_BODY where FUN_ID = "+id);    
                  
                  int maximum = 0;
                  for(int i=0;i<fun.length;i++)
                      if(fun[i].level >= maximum)
                        maximum = fun[i].level;
                  
                for(int j=0;j<maximum;j++)
                  for(int i=0;i<fun.length;i++)
                  {
                    if(i==0 || fun[i].father==0 || fun[i].level != j)
                      continue;
                  
                      rs2=query2.select("select max(FB_ID)+1 as maxi from IISC_FUN_BODY");    
                      int max = 0;
                      if(rs2.next())
                        max = rs2.getInt("maxi");
                        
                      query2.Close();                  
                          
                      if(fun[i].getClass().toString().equals("class iisc.funcPanel"))
                      {
                          rs2=query2.select("select FBE_ID from IISC_FUN_BLOCK_ELEMENTS where name = '"+((funcPanel)fun[i]).name.getText()+"'");
                          int block_id = -1;
                          
                          if(rs2.next())
                            block_id = rs2.getInt("FBE_ID");

                          query2.Close();

                          System.out.println("insert into IISC_FUN_BODY(FB_ID,FE_ID,type,[value],father,FUN_ID) values ("+max+","+block_id+",0,'',"+fun[i].father+","+id+") ");                            
                          a = query.update("insert into IISC_FUN_BODY(FB_ID,FE_ID,type,[value],father,FUN_ID) values ("+max+","+block_id+",0,'',"+fun[i].father+","+id+") ");
                      }
                      else if(fun[i].getClass().toString().equals("class iisc.funcLabel"))
                      {
                            System.out.println("select FLE_ID from IISC_FUN_LABEL_ELEMENTS where name = '"+((JLabel)((funcLabel)fun[i]).label.getSelectedItem()).getText()+"'");  
                            rs2=query2.select("select FLE_ID from IISC_FUN_LABEL_ELEMENTS where name = '"+((JLabel)((funcLabel)fun[i]).label.getSelectedItem()).getText()+"'");
                            int block_id = -1;
                            
                            if(rs2.next())
                              block_id = rs2.getInt("FLE_ID");
                              
                            query2.Close();
                            
                            System.out.println("insert into IISC_FUN_BODY(FB_ID,FE_ID,type,[value],father,FUN_ID) values ("+max+","+block_id+",1,'"+((funcLabel)fun[i]).action.getText()+"',"+fun[i].father+","+id+") ");  
                            a = query.update("insert into IISC_FUN_BODY(FB_ID,FE_ID,type,[value],father,FUN_ID) values ("+max+","+block_id+",1,'"+((funcLabel)fun[i]).action.getText()+"',"+fun[i].father+","+id+") ");   
                      }
                  }
                  
                  /*
                  rs2=query2.select("select * from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.PR_id = D.PR_id AND F.Fun_id = "+id+" order by F.Param_seq");    
                  int duz=0;
                  while(rs2.next())
                     duz++;

                  query2.Close();
    
                  rs1=query.select("select * from IISC_Function where Fun_id="+id);
                  while(rs1.next())
                  {
                      String funname = rs1.getString("Fun_name");
                      
                      fun = new funcElement[duz + 2];
                      fun[0] = new funcPanel(0,-1,0,this);
                      ((funcPanel)fun[0]).name.setText("FUNCTION "+funname+"-");
                      fun[1] = new funcPanel(1,-1,0,this);
                      ((funcPanel)fun[1]).name.setText("BODY");
                      jPanel1.add(((funcPanel)fun[1]));            
                  
                      rs2=query2.select("select F.*,D.Dom_mnem from IISC_FUN_PARAM F,IISC_DOMAIN D where F.Dom_id = D.Dom_id AND F.PR_id = D.PR_id AND F.Fun_id = "+id+" order by F.Param_seq");    
                      int i=2;
                      while(rs2.next())
                      {
                          String domtemp= rs2.getString("Dom_mnem");
                          String param= rs2.getString("Param_name");
                          fun[i] = new funcLabel(i,0,1,this);
                          ((funcPanel)fun[0]).add((funcLabel)fun[i],null); 
                          ((funcLabel)fun[i]).setBounds(inBlockWidth,15*(i-2+1),blockWidth,15);
                          ((funcLabel)fun[i]).label.removeAllItems();
                          ((funcLabel)fun[i]).label.addItem(new JLabel("Param"));
                          //((funcLabel)fun[i]).label.setText("Param");
                          ((funcLabel)fun[i]).action.setText("("+domtemp+") "+param);
                          i++;
                      }
                      query2.Close();
                  }
                  ((funcPanel)fun[0]).setBounds(0,0,inBlockWidth+blockWidth,30+15*duz); 
                  query.Close();       
                  */
    }
    catch(Exception ex)
    {
        System.out.println("GRESKA-LOAD:"+ex.toString());
    }    
  }

  private void jButton15_actionPerformed(ActionEvent e)
  {
      addNotes();
  }
  
  public void addNotes()
  {
      if( fun[this.clicked].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[this.clicked]).name.getText().equals("COMMENTS"))
      {
              this.pozklik = 0;
              add("class iisc.funcLabel");
              ((funcLabel)fun[lastAdd]).itemsForNotes();
              //((funcLabel)fun[lastAdd]).label.addItem(new JLabel("Constant"));
              //((funcLabel)fun[lastAdd]).label.setText("Condition");
              ((funcLabel)fun[lastAdd]).action.setText("");         
      }
  }  

}









    /*
          while (mjesto != -1)
          {
              int tmpx = 0;
              int tmpy = 0;
              int tmphei = 0;
              if ( fun[mjesto].getClass().toString().equals("class iisc.funcPanel") )
              {
                  tmpx = ((funcPanel)fun[mjesto]).getX();
                  tmpy = ((funcPanel)fun[mjesto]).getY();
                  tmphei = ((funcPanel)fun[mjesto]).getHeight();            
                  tmpy = tmpy + tmphei;
              }
              else if ( fun[mjesto].getClass().toString().equals("class iisc.funcLabel") )
              {
                  tmpx = ((funcLabel)fun[mjesto]).getX();
                  tmpy = ((funcLabel)fun[mjesto]).getY();
                  tmphei = ((funcLabel)fun[mjesto]).getHeight();            
                  tmpy = tmpy + tmphei;
              }
              int sum = 30;
              for (i = 0; i < mjesto + 1; i++)
              {
                    if (fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).father == otac )
                      sum += ((funcPanel)fun[i]).getHeight();
                    else if (fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).father == otac)
                      sum += ((funcLabel)fun[i]).getHeight();
              }
                    
              for (i = mjesto + 1; i < fun.length; i++)
              {
                    if (fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).father == otac )
                    {
                        ((funcPanel)fun[i]).setBounds(tmpx,tmpy,((funcPanel)fun[i]).getWidth(),((funcPanel)fun[i]).getHeight());
                        tmpy = tmpy + ((funcPanel)fun[i]).getHeight();
                        sum += ((funcPanel)fun[i]).getHeight();
                    }
                    else if (fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).father == otac)
                    {
                        ((funcLabel)fun[i]).setBounds(tmpx,tmpy,((funcLabel)fun[i]).getWidth(),((funcLabel)fun[i]).getHeight());
                        tmpy = tmpy + ((funcLabel)fun[i]).getHeight();
                        sum += ((funcLabel)fun[i]).getHeight();
                    }
              }
              if ( fun[mjesto].getClass().toString().equals("class iisc.funcPanel") )
                  mjesto = ((funcPanel)fun[mjesto]).father;
              else if ( fun[mjesto].getClass().toString().equals("class iisc.funcLabel") )
                  mjesto = ((funcLabel)fun[mjesto]).father;
                  
              if ( mjesto != -1)
              {
                  ((funcPanel)fun[mjesto]).setSize(((funcPanel)fun[mjesto]).getWidth(),sum);
                  otac = ((funcPanel)fun[mjesto]).father;
              }

          }
          */
          
          
          
          
          /*
          int maxlevel = 0;
          int maxwid = 0;
          for ( i = 0; i< fun.length; i++)
          {
              if (fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).level >= maxlevel ) 
              {
                  maxlevel = ((funcLabel)fun[i]).level;
                  maxwid = ((funcLabel)fun[i]).getWidth();
              }
              else if (fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).level >= maxlevel ) 
              {
                  maxlevel = ((funcPanel)fun[i]).level;
                  maxwid = ((funcPanel)fun[i]).getWidth();
              }
          }
          
          for(i = 0;i <= maxlevel; i++)
            for(j = 0; j< fun.length;j++)
            {
                if (fun[j].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[j]).level == i ) 
                    ((funcPanel)fun[j]).setSize(maxwid + (maxlevel - i)*100,((funcPanel)fun[j]).getHeight());
                else if (fun[j].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[j]).level == i ) 
                    ((funcLabel)fun[j]).setSize(maxwid + (maxlevel - i)*100,((funcLabel)fun[j]).getHeight());

            }
            */          
            
         /*   
          int sum = 0;
          for(i = 0;i < fun.length; i++)
             if (fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)fun[i]).level == 0 ) 
                sum += ((funcPanel)fun[i]).getHeight();
             else if (fun[i].getClass().toString().equals("class iisc.funcLabel") && ((funcLabel)fun[i]).level == 0 ) 
               sum += ((funcLabel)fun[i]).getHeight();
          
          if (sum > 600)
          {
              jPanel1.setSize(new Dimension(jPanel1.getWidth(),sum));
              jPanel1.setPreferredSize(new Dimension(jPanel1.getWidth(),sum));
          }
          else
          {
              jPanel1.setSize(new Dimension(jPanel1.getWidth(),600));
              jPanel1.setPreferredSize(new Dimension(jPanel1.getWidth(),600));
          }
          
          if (((funcPanel)fun[0]).getWidth() > 600)
          {
              jPanel1.setSize(new Dimension(((funcPanel)fun[0]).getWidth(),jPanel1.getHeight()));
              jPanel1.setPreferredSize(new Dimension(((funcPanel)fun[0]).getWidth(),jPanel1.getHeight()));
          }
          else
          {
              jPanel1.setSize(new Dimension(600,jPanel1.getHeight()));
              jPanel1.setPreferredSize(new Dimension(600,jPanel1.getHeight()));
          }
          
          jPanel1.revalidate();
          jScrollPane1.revalidate();
          */            
          
          
/*          
          Object [] funtmp = new Object[fun.length];
          
          for(int i = 0; i< fun.length ; i++)
            funtmp[i] = fun[i];
            
          int widthtmp = funAlg.blockWidth;
          
          for(int i = 0;i<funtmp.length; i++)
          {
              if (funtmp[i] != null)
              {
                  if ( funtmp[i].getClass().toString().equals("class iisc.funcPanel") &&  ((funcPanel)funtmp[i]).jButton1.getText().equals("-"))
                  {
                      for(int j=0;j<funtmp.length;j++)
                      if(funtmp[j] != null)
                        if ( funtmp[j].getClass().toString().equals("class iisc.funcPanel") && ( (((funcPanel)funtmp[j]).father > -1 && funtmp[((funcPanel)funtmp[j]).father] == null)    || ((funcPanel)fun[j]).father == i))
                          funtmp[j] = null;
                          
                      widthtmp=((funcPanel)funtmp[i]).level*inBlockWidth + funAlg.blockWidth;
                  }
                  else if ( funtmp[i].getClass().toString().equals("class iisc.funcLabel") )
                  {
                      widthtmp=((funcLabel)funtmp[i]).level*inBlockWidth + funAlg.blockWidth;
                  }
              }
          }
*/          

