package iisc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.sql.*;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import java.util.Enumeration;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class CheckConstraintExpressionEditor extends JDialog {

    //public int dragX = -1;
    public int dragY = -1;
    public int dragH = -1;
    //public int dragW = -1;
    //public int dragXe = -1;
    public int dragYe = -1;
    private JLabel lblIcon[] = null;
    

    //public PTree tree;
    public int id_comp = -1;
    public int typeOfEditor = 0;
    public String saveOpt = "";
    
    public int clicked = 0;
    public int entered = 0;
    public int pressed = 0;
    public int released = 0;
    public int tmppressed = -1;
    public int movePosition = 0;
    public String whatMove = "";
    public expPanel tmp1[] = new expPanel[15];
    public node[][] undon = new node[2000][];
    public int undoNo = 0;
    public int undoPoz = 0;
    public int copyPoz = 0;
    public int cutPoz = 0;
    public int pastePoz = 0;
    private int xmlGuiNo = 0;
    //private String startExpr = "";
    private JTextPane myPane = null;
    public String nodeNameSyntax = "";
    public DropDownList ddList = null;
    public DropDownList ddListEditor = null;
    //private int nodeIDSyntax = -1;

    public int clickedTmp = 0;
    public int enteredTmp = 0;
    public int pressedTmp = 0;
    public int releasedTmp = 0;
    public int tmppressedTmp = -1;
    public Connection con;
    private int printStep = 0;
    
    
    private int glWidth = 0;
    private int glWidth2 = 400;
    private int glBlHeight = 40;
    private int glSsHeight = 16;
    private int glX = 40;
    private int glY = 20;
    private int glBottom = 20;
    private int glPlus = 20;  
    public int rightHeight = glX + 10;
    private boolean minimaze = false;
    
    //public boolean mouseDd = true;

    public node[] aaa = new node[1];
    private BorderLayout borderLayout1 = new BorderLayout();
    public JPanel jPanel1 = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileExit = new JMenuItem();
    
    public JTree treeView = new JTree();
    DefaultTreeModel treeModel = (DefaultTreeModel)treeView.getModel();
    //private boolean cmbLoad = true;
    
    private ImageIcon help1 = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private ImageIcon newExpr = new ImageIcon(IISFrameMain.class.getResource("icons/newproj2.gif"));
    private ImageIcon imageOpen = new ImageIcon(IISFrameMain.class.getResource("icons/openfile.gif"));
    private ImageIcon imageOpenExp = new ImageIcon(IISFrameMain.class.getResource("icons/openexp.gif"));
    private ImageIcon leaf = new ImageIcon(IISFrameMain.class.getResource("icons/leaf.gif"));
    private ImageIcon imageClose = new ImageIcon(IISFrameMain.class.getResource("icons/closefile.gif"));
    private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help.gif")); 
    public  ImageIcon imageSave = new ImageIcon(IISFrameMain.class.getResource("icons/save2.gif"));
    private ImageIcon param = new ImageIcon(IISFrameMain.class.getResource("icons/param.gif"));
    private ImageIcon equi = new ImageIcon(IISFrameMain.class.getResource("icons/equi.gif"));
    private ImageIcon concat = new ImageIcon(IISFrameMain.class.getResource("icons/concat.gif"));
    private ImageIcon imply = new ImageIcon(IISFrameMain.class.getResource("icons/imply.gif"));
    private ImageIcon and = new ImageIcon(IISFrameMain.class.getResource("icons/and.gif"));
    private ImageIcon or = new ImageIcon(IISFrameMain.class.getResource("icons/or.gif"));    
    private ImageIcon erase = new ImageIcon(IISFrameMain.class.getResource("icons/erase.gif"));  
    private ImageIcon block = new ImageIcon(IISFrameMain.class.getResource("icons/block.gif"));  
    private ImageIcon not = new ImageIcon(IISFrameMain.class.getResource("icons/not.gif"));  
    private ImageIcon xor = new ImageIcon(IISFrameMain.class.getResource("icons/xor.gif"));  
    private ImageIcon eq = new ImageIcon(IISFrameMain.class.getResource("icons/eq.gif"));  
    private ImageIcon neq = new ImageIcon(IISFrameMain.class.getResource("icons/neq.gif"));  
    private ImageIcon in = new ImageIcon(IISFrameMain.class.getResource("icons/in.gif"));  
    private ImageIcon lk = new ImageIcon(IISFrameMain.class.getResource("icons/lk.gif"));  
    private ImageIcon exp = new ImageIcon(IISFrameMain.class.getResource("icons/exp.gif"));
    private ImageIcon undoi = new ImageIcon(IISFrameMain.class.getResource("icons/undo.gif"));
    private ImageIcon redoi = new ImageIcon(IISFrameMain.class.getResource("icons/redo.gif"));
    private ImageIcon set = new ImageIcon(IISFrameMain.class.getResource("icons/set.gif"));
    private ImageIcon expr = new ImageIcon(IISFrameMain.class.getResource("icons/expr.gif")); 
    private ImageIcon functions2 = new ImageIcon(IISFrameMain.class.getResource("icons/functions2.gif")); 
    private ImageIcon cut = new ImageIcon(IISFrameMain.class.getResource("icons/cut.gif"));  
    private ImageIcon copy = new ImageIcon(IISFrameMain.class.getResource("icons/copy.gif"));  
    private ImageIcon paste = new ImageIcon(IISFrameMain.class.getResource("icons/paste.gif"));  
    private ImageIcon plus = new ImageIcon(IISFrameMain.class.getResource("icons/plus.gif")); 
    private ImageIcon minus = new ImageIcon(IISFrameMain.class.getResource("icons/minus.gif"));     
    private ImageIcon multi = new ImageIcon(IISFrameMain.class.getResource("icons/multi.gif")); 
    private ImageIcon div = new ImageIcon(IISFrameMain.class.getResource("icons/div.gif")); 
    private ImageIcon check = new ImageIcon(IISFrameMain.class.getResource("icons/check3.gif")); 
    private ImageIcon zipexp = new ImageIcon(IISFrameMain.class.getResource("icons/zipexp.gif")); 
    private ImageIcon maximize = new ImageIcon(IISFrameMain.class.getResource("icons/maximize.gif")); 
    private ImageIcon lt = new ImageIcon(IISFrameMain.class.getResource("icons/lt.gif"));    
    private ImageIcon le = new ImageIcon(IISFrameMain.class.getResource("icons/le.gif"));    
    private ImageIcon gt = new ImageIcon(IISFrameMain.class.getResource("icons/gt.gif"));    
    private ImageIcon ge = new ImageIcon(IISFrameMain.class.getResource("icons/ge.gif"));
    private ImageIcon tuple = new ImageIcon(IISFrameMain.class.getResource("icons/tuple.gif"));


    public JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel jPanel3 = new JPanel();
    //private BorderLayout borderLayout2 = new BorderLayout();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JScrollPane jScrollPane2 = new JScrollPane();
    public JTextPane txtExpression = new JTextPane();
    private JPanel lPanel = new JPanel();
    private JMenu jMenu1 = new JMenu();
    private JMenuItem jMenuItem1 = new JMenuItem();
    private JMenuItem jMenuItem2 = new JMenuItem();
    private JMenuItem jMenuItem3 = new JMenuItem();
    private JMenuItem jMenuItem4 = new JMenuItem();
    private JMenuItem jMenuItem5 = new JMenuItem();
    private JMenuItem jMenuItem6 = new JMenuItem();
    private JMenuItem jMenuItem7 = new JMenuItem();
    private JLabel pNot = new JLabel();
    private JLabel pAnd = new JLabel();
    private JLabel pOr = new JLabel();
    private JLabel pXor = new JLabel();
    private JLabel pEqui = new JLabel();
    private JLabel pImply = new JLabel();
    private JLabel pIn = new JLabel();
    private JLabel pLike = new JLabel();
    private JLabel sep1 = new JLabel();
    private JLabel sep2 = new JLabel();
    private JLabel sep3 = new JLabel();
    private JLabel pEq = new JLabel();
    private JLabel pNeq = new JLabel();
    private JLabel pLt = new JLabel();
    private JLabel pLe = new JLabel();
    private JLabel pGe = new JLabel();
    private JLabel pGt = new JLabel();
    private JLabel pAdd = new JLabel();
    private JLabel pSub = new JLabel();
    private JLabel pMulti = new JLabel();
    private JLabel pDiv = new JLabel();
    private JPanel jPanel2 = new JPanel();
    private JLabel pSave = new JLabel();
    private JLabel pDel = new JLabel();
    private JLabel pUndo = new JLabel();
    private JLabel pRedu = new JLabel();
    private JLabel pCheck = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel pExp = new JLabel();
    private JLabel pFun = new JLabel();
    private JLabel pBlock = new JLabel();
    private JMenu jMenu2 = new JMenu();
    private JMenuItem jMenuItem8 = new JMenuItem();
    private JScrollPane ScrollPaneErr = new JScrollPane();
    private JList errorList = new JList();
    public DefaultListModel model2 = new DefaultListModel();
    private JLabel pConcat = new JLabel();
    private JLabel pSepA = new JLabel();
    private JSplitPane jSplitPane2 = new JSplitPane();
    private JSplitPane jSplitPane3 = new JSplitPane();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JLabel sep4 = new JLabel();
    private JLabel sep5 = new JLabel();
    private JLabel sep6 = new JLabel();
    private JLabel pSepA1 = new JLabel();
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JMenu jMenu3 = new JMenu();
    private JMenu jMenu4 = new JMenu();
    private JMenuItem jmiAnd = new JMenuItem();
    private JMenuItem jmiOr = new JMenuItem();
    private JMenuItem jmiXor = new JMenuItem();
    private JMenuItem jmiImply = new JMenuItem();
    private JMenuItem jmiEqui = new JMenuItem();
    private JMenu jMenu5 = new JMenu();
    private JMenuItem jmiEq = new JMenuItem();
    private JMenuItem jmiNeq = new JMenuItem();
    private JMenuItem jmiLt = new JMenuItem();
    private JMenuItem jmiLe = new JMenuItem();
    private JMenuItem jmiGe = new JMenuItem();
    private JMenuItem jmiGt = new JMenuItem();
    private JMenu jMenu6 = new JMenu();
    private JMenuItem jmiAdd = new JMenuItem();
    private JMenuItem jmiSub = new JMenuItem();
    private JMenuItem jmiMulti = new JMenuItem();
    private JMenuItem jmiDiv = new JMenuItem();
    private JMenuItem jmiIn = new JMenuItem();
    private JMenuItem jmiLike = new JMenuItem();
    private JMenuItem jmiNot = new JMenuItem();
    private JMenuItem jmiConcat = new JMenuItem();
    private JMenu jMenu7 = new JMenu();
    private JMenuItem jmiExpr = new JMenuItem();
    private JMenuItem jmiFun = new JMenuItem();
    private JLabel pZipexp = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel pSepA2 = new JLabel();
    public JLabel status = new JLabel();
    public String ID2 = null;
    public String treeID = null;
    public JComboBox cmbAddExpr = new JComboBox();
    private JPanel statusBar = new JPanel();
    private JLabel sep7 = new JLabel();
    private JLabel sep8 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel pHelp = new JLabel();
    public JLabel status2 = new JLabel();
    private Frame pmy = null;
    private JLabel pOpen = new JLabel();
    private JLabel pMaximize = new JLabel();
    private JLabel pSepA3 = new JLabel();
    private JLabel pNewExpr = new JLabel();
    private JMenuItem jmiParenthesis = new JMenuItem();
    private JLabel pTuple = new JLabel();
    private JMenuItem jmiTuple = new JMenuItem();
    private JSlider jsWidth = new JSlider();

    //public int listClicked = -1;

    public CheckConstraintExpressionEditor() {
        this(null, "", false);
    }

    public CheckConstraintExpressionEditor(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            pmy = parent;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public CheckConstraintExpressionEditor(Frame parent, String title, boolean modal, Connection connect, /*PTree tr,*/int idd,int typeE,JTextPane par,String nodeName,String id2,String IDG) {
        super(parent, title, modal);
        try {
            myPane = par;
            txtExpression.setText(myPane.getText().trim());
            saveOpt = txtExpression.getText();
            nodeNameSyntax = nodeName;
            //nodeIDSyntax = nodeId;
            //startExpr = myPane.getText();
            typeOfEditor = typeE;
            id_comp = idd;
            //tree = tr;
            con = connect;
            ID2 = id2;
            treeID = IDG;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    private void jbInit() throws Exception {
        ddList = new DropDownList(typeOfEditor,con,txtExpression,String.valueOf(treeID),ID2,String.valueOf(id_comp));
        this.add(ddList);
        ddList.setVisible(false);
/*        ddList = new DropDownList(typeOfEditor,con,txtExpression,String.valueOf(tree.ID),ID2,String.valueOf(id_comp));
        this.add(ddList);
        ddList.setBounds( 2,960 , 200, 80 );
*/
        //treeView = new JTree();
        this.getContentPane().setLayout(null);
        treeView.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));


        treeView.setRowHeight(15);
        treeView.setShowsRootHandles(true);
        treeView.setVisibleRowCount(500);
        treeView.setFont(new Font("Dialog", 2, 11));
        treeView.setExpandsSelectedPaths(false);
        treeView.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        treeView_mouseClicked(e);
                    }
                });
        this.setBounds(250,250,717, 651);
        this.setJMenuBar( menuBar );
        menuFile.setText( "File" );
        menuFileExit.setText( "Exit" );
        menuFileExit.setHorizontalAlignment(SwingConstants.LEFT);
        menuFileExit.setIcon(imageClose);
        menuFileExit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        menuFileExit_actionPerformed(e);
                    }
                });
        errorList.setModel(model2);
        errorList.setCellRenderer(new CheckConstraintErrorListRender());
        //this.setSize(new Dimension(717, 651));
        //a.setBounds(new Rectangle(100, 100, 350, 175));
        //this.getContentPane().add(a, BorderLayout.CENTER);
        pConcat.setBounds(new Rectangle(490, 5, 22, 22));
        pConcat.setHorizontalAlignment(SwingConstants.CENTER);
        pConcat.setHorizontalTextPosition(SwingConstants.CENTER);
        pConcat.setFont(new Font("Tahoma", 1, 12));
        pConcat.setToolTipText("Concatenation");
        pConcat.setIcon(concat);
        pConcat.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pConcat.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pConcat.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pConcat.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pConcat.setBorder(BorderFactory.createEmptyBorder());
                    }           
        
                    public void mouseClicked(MouseEvent e) {
                        pConcat_mouseClicked(e);
                    }
                });
        pSepA.setBounds(new Rectangle(485, 5, 4, 22));
        pSepA.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jSplitPane2.setDividerLocation(150);
        jSplitPane2.setLastDividerLocation(150);
        jSplitPane2.setOneTouchExpandable(true);
        jSplitPane2.setDividerSize(10);
        jSplitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setLastDividerLocation(500);
        jSplitPane3.setDividerLocation(450);
        jSplitPane3.setOneTouchExpandable(true);
        jSplitPane3.setDividerSize(10);
        jScrollPane3.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        sep4.setBounds(new Rectangle(220, 5, 4, 22));
        sep4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        sep5.setBounds(new Rectangle(275, 5, 4, 20));
        sep5.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        sep6.setBounds(new Rectangle(380, 5, 4, 22));
        sep6.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pSepA1.setBounds(new Rectangle(515, 5, 4, 22));
        pSepA1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jSplitPane1.setDividerLocation(85);
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setDividerSize(10);
        jMenu3.setText("Operators");
        jMenu4.setText("Locigal");
        jmiAnd.setText("And");
        jmiAnd.setHorizontalAlignment(SwingConstants.LEFT);
        jmiAnd.setIcon(and);
        jmiAnd.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem9_actionPerformed(e);
                    }
                });
        jmiOr.setText("Or");
        jmiOr.setHorizontalAlignment(SwingConstants.LEFT);
        jmiOr.setIcon(or);
        jmiOr.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem12_actionPerformed(e);
                    }
                });
        jmiXor.setText("Xor");
        jmiXor.setHorizontalAlignment(SwingConstants.LEFT);
        jmiXor.setIcon(xor);
        jmiXor.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem13_actionPerformed(e);
                    }
                });
        jmiImply.setText("=>");
        jmiImply.setHorizontalAlignment(SwingConstants.LEFT);
        jmiImply.setIcon(imply);
        jmiImply.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem14_actionPerformed(e);
                    }
                });
        jmiEqui.setText("<=>");
        jmiEqui.setHorizontalAlignment(SwingConstants.LEFT);
        jmiEqui.setIcon(equi);
        jmiEqui.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem15_actionPerformed(e);
                    }
                });
        jMenu5.setText("Comparison");
        jmiEq.setText("Is Equal");
        jmiEq.setIcon(eq);
        jmiEq.setHorizontalAlignment(SwingConstants.LEFT);
        jmiEq.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem10_actionPerformed(e);
                    }
                });
        jmiNeq.setText("Is Not Equal");
        jmiNeq.setIcon(neq);
        jmiNeq.setHorizontalAlignment(SwingConstants.LEFT);
        jmiNeq.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem16_actionPerformed(e);
                    }
                });
        jmiLt.setText("Less Than");
        jmiLt.setIcon(lt);
        jmiLt.setHorizontalAlignment(SwingConstants.LEFT);
        jmiLt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem17_actionPerformed(e);
                    }
                });
        jmiLe.setText("Less Than Or Equal To");
        jmiLe.setHorizontalAlignment(SwingConstants.LEFT);
        jmiLe.setIcon(le);
        jmiLe.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem18_actionPerformed(e);
                    }
                });
        jmiGe.setText("Greater Than Or Equal To");
        jmiGe.setHorizontalAlignment(SwingConstants.LEFT);
        jmiGe.setIcon(ge);
        jmiGe.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem19_actionPerformed(e);
                    }
                });
        jmiGt.setText("Greater Than");
        jmiGt.setHorizontalAlignment(SwingConstants.LEFT);
        jmiGt.setIcon(gt);
        jmiGt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem20_actionPerformed(e);
                    }
                });
        jMenu6.setText("Arithmetic & String");
        jmiAdd.setText("Addition");
        jmiAdd.setHorizontalAlignment(SwingConstants.LEFT);
        jmiAdd.setIcon(plus);
        jmiAdd.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem11_actionPerformed(e);
                    }
                });
        jmiSub.setText("Subtraction");
        jmiSub.setHorizontalAlignment(SwingConstants.LEFT);
        jmiSub.setIcon(minus);
        jmiSub.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem21_actionPerformed(e);
                    }
                });
        jmiMulti.setText("Multiplication");
        jmiMulti.setHorizontalAlignment(SwingConstants.LEFT);
        jmiMulti.setIcon(multi);
        jmiMulti.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem22_actionPerformed(e);
                    }
                });
        jmiDiv.setText("Division");
        jmiDiv.setHorizontalAlignment(SwingConstants.LEFT);
        jmiDiv.setIcon(div);
        jmiDiv.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem23_actionPerformed(e);
                    }
                });
        jmiIn.setText("In");
        jmiIn.setHorizontalAlignment(SwingConstants.LEFT);
        jmiIn.setIcon(in);
        jmiIn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem24_actionPerformed(e);
                    }
                });
        jmiLike.setText("Like");
        jmiLike.setHorizontalAlignment(SwingConstants.LEFT);
        jmiLike.setIcon(lk);
        jmiLike.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem25_actionPerformed(e);
                    }
                });
        jmiNot.setText("Not");
        jmiNot.setIcon(not);
        jmiNot.setHorizontalAlignment(SwingConstants.LEFT);
        jmiNot.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem26_actionPerformed(e);
                    }
                });
        jmiConcat.setText("Concatenation");
        jmiConcat.setHorizontalAlignment(SwingConstants.LEFT);
        jmiConcat.setIcon(concat);
        jmiConcat.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem27_actionPerformed(e);
                    }
                });
        jMenu7.setText("Operands");
        jmiExpr.setText("Variables     F2");
        jmiExpr.setHorizontalAlignment(SwingConstants.LEFT);
        jmiExpr.setIcon(exp);
        jmiExpr.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem28_actionPerformed(e);
                    }
                });
        jmiFun.setText("Functions   F2");
        jmiFun.setIcon(functions2);
        jmiFun.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem29_actionPerformed(e);
                    }
                });
        pZipexp.setBounds(new Rectangle(2, 155, 22, 22));
        pZipexp.setHorizontalTextPosition(SwingConstants.CENTER);
        pZipexp.setHorizontalAlignment(SwingConstants.CENTER);
        pZipexp.setIconTextGap(1);
        pZipexp.setIcon(zipexp);
        pZipexp.setToolTipText("Expression optimization");
        pZipexp.setEnabled(false);
        pZipexp.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pZipexp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pZipexp.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pZipexp.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pZipexp.setBorder(BorderFactory.createEmptyBorder());
                    }
                });
        jLabel2.setText("Action:");
        jLabel2.setBounds(new Rectangle(40, 0, 45, 20));
        pSepA2.setBounds(new Rectangle(620, 5, 4, 20));
        pSepA2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        status.setBounds(new Rectangle(80, 0, 360, 20));
        cmbAddExpr.setBounds(new Rectangle(625, 5, 220, 20));
        cmbAddExpr.setToolTipText("Add selected value to the current position (F2)");
        cmbAddExpr.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cmbAddExpr_actionPerformed(e);
                    }
                });
        cmbAddExpr.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        cmbAddExpr_mouseEntered(e);
                    }
                });
        statusBar.setBounds(new Rectangle(780, 10, 50, 50));
        statusBar.setBackground(new Color(0, 181, 0));
        statusBar.setPreferredSize(new Dimension(10, 20));
        statusBar.setLayout(null);
        statusBar.setSize(new Dimension(1042, 25));
        statusBar.setOpaque(false);
        sep7.setBounds(new Rectangle(440, 0, 4, 20));
        sep7.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        sep8.setBounds(new Rectangle(30, 0, 4, 20));
        sep8.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jLabel6.setBounds(new Rectangle(2, 210, 25, 4));
        jLabel6.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pHelp.setBounds(new Rectangle(915, 5, 22, 22));
        pHelp.setHorizontalAlignment(SwingConstants.CENTER);
        pHelp.setHorizontalTextPosition(SwingConstants.CENTER);
        pHelp.setIconTextGap(1);
        pHelp.setIcon(help1);
        pHelp.setToolTipText("Help");
        pHelp.setEnabled(false);
        pHelp.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pHelp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pHelp.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pHelp.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pHelp.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pHelp_mouseClicked(e);
                    }
                });
        status2.setBounds(new Rectangle(450, 0, 425, 20));
        pOpen.setBounds(new Rectangle(2, 5, 22, 22));
        pOpen.setHorizontalAlignment(SwingConstants.CENTER);
        pOpen.setHorizontalTextPosition(SwingConstants.CENTER);
        pOpen.setIconTextGap(1);
        pOpen.setIcon(imageOpenExp);
        pOpen.setToolTipText("Open");
        pOpen.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pOpen.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pOpen.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pOpen.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pOpen.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pOpen_mouseClicked(e);
                    }
                });
        pMaximize.setBounds(new Rectangle(940, 5, 22, 22));
        pMaximize.setHorizontalAlignment(SwingConstants.CENTER);
        pMaximize.setHorizontalTextPosition(SwingConstants.CENTER);
        pMaximize.setIconTextGap(1);
        pMaximize.setIcon(maximize);
        pMaximize.setToolTipText("Maximize/ Restore Window");
        pMaximize.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pMaximize.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pMaximize.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pMaximize.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pMaximize.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pMaximize_mouseClicked(e);
                    }
                });
        pSepA3.setBounds(new Rectangle(850, 5, 4, 20));
        pSepA3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pNewExpr.setBounds(new Rectangle(855, 5, 20, 20));
        pNewExpr.setHorizontalAlignment(SwingConstants.CENTER);
        pNewExpr.setHorizontalTextPosition(SwingConstants.CENTER);
        pNewExpr.setIcon(newExpr);
        pNewExpr.setIconTextGap(1);
        pNewExpr.setToolTipText("Search ...");
        pNewExpr.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pNewExpr.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pNewExpr.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pNewExpr.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pNewExpr.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pNewExpr_mouseClicked(e);
                    }
                });
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jPanel3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jPanel3.setLayout(null);
        jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        txtExpression.setFont(new Font("Dialog", 1, 13));
        txtExpression.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        txtExpression_keyReleased(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtExpression_keyPressed(e);
                    }
                });
        //txtExpression.setWrapStyleWord(true);
        //txtExpression.setLineWrap(true);
        txtExpression.addFocusListener(new FocusAdapter() {
                    public void focusGained(FocusEvent e) {
                        txtExpression_focusGained(e);
                    }
                });
        txtExpression.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        txtExpression_mouseClicked(e);
                    }
                });
        lPanel.setBounds(new Rectangle(0, 0, 1245, 30));
        lPanel.setLayout(null);

        jMenu1.setText("Edit");
        jMenuItem1.setText("Undo");
        jMenuItem1.setIcon(undoi);
        jMenuItem1.setHorizontalAlignment(SwingConstants.LEFT);
        jMenuItem1.setMargin(new Insets(0, 0, 0, 0));
        jMenuItem2.setText("Redo");
        jMenuItem2.setIcon(redoi);
        jMenuItem2.setHorizontalAlignment(SwingConstants.LEFT);
        jMenuItem2.setMargin(new Insets(0, 0, 0, 0));
        jMenuItem3.setText("Cut");
        jMenuItem3.setIcon(cut);
        jMenuItem3.setHorizontalAlignment(SwingConstants.LEFT);
        jMenuItem3.setMargin(new Insets(0, 0, 0, 0));
        jMenuItem4.setText("Copy");
        jMenuItem4.setIcon(copy);
        jMenuItem4.setMargin(new Insets(0, 0, 0, 0));
        jMenuItem5.setText("Paste");
        jMenuItem5.setIcon(paste);
        jMenuItem5.setMargin(new Insets(0, 0, 0, 0));
        jMenuItem6.setText("Delete");
        jMenuItem6.setIcon(erase);
        jMenuItem6.setMargin(new Insets(0, 0, 0, 0));
        jMenuItem7.setText("Save");
        //jMenuItem7.setMnemonic(KeyEvent.VK_S);
        jMenuItem7.setIcon(imageSave);
        jMenuItem7.setHorizontalAlignment(SwingConstants.LEFT);
        jMenuItem7.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem7_actionPerformed(e);
                    }
                });
        pNot.setBounds(new Rectangle(5, 5, 22, 22));
        pNot.setIcon(not);
        pNot.setHorizontalAlignment(SwingConstants.CENTER);
        pNot.setHorizontalTextPosition(SwingConstants.CENTER);
        pNot.setIconTextGap(1);
        pNot.setToolTipText("Not");
        pNot.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pNot.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pNot.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pNot.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pNot.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pNot_mouseClicked(e);
                    }
                });
        pAnd.setBounds(new Rectangle(35, 5, 22, 22));
        pAnd.setIconTextGap(1);
        pAnd.setIcon(and);
        pAnd.setHorizontalAlignment(SwingConstants.CENTER);
        pAnd.setHorizontalTextPosition(SwingConstants.CENTER);
        pAnd.setToolTipText("And");
        pAnd.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pAnd.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pAnd.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pAnd.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pAnd.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pAnd_mouseClicked(e);
                    }
                });
        pOr.setBounds(new Rectangle(60, 5, 22, 22));
        pOr.setHorizontalAlignment(SwingConstants.CENTER);
        pOr.setHorizontalTextPosition(SwingConstants.CENTER);
        pOr.setIcon(or);
        pOr.setIconTextGap(1);
        pOr.setToolTipText("Or");
        pOr.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pOr.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pOr.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pOr.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pOr.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pOr_mouseClicked(e);
                    }
                });
        pXor.setBounds(new Rectangle(85, 5, 22, 22));
        pXor.setHorizontalAlignment(SwingConstants.CENTER);
        pXor.setHorizontalTextPosition(SwingConstants.CENTER);
        pXor.setIcon(xor);
        pXor.setIconTextGap(1);
        pXor.setToolTipText("Xor");
        pXor.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pXor.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pXor.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pXor.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pXor.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pXor_mouseClicked(e);
                    }
                });
        pEqui.setBounds(new Rectangle(115, 5, 22, 22));
        pEqui.setHorizontalAlignment(SwingConstants.CENTER);
        pEqui.setHorizontalTextPosition(SwingConstants.CENTER);
        pEqui.setIcon(equi);
        pEqui.setIconTextGap(1);
        pEqui.setToolTipText("Equivalence");
        pEqui.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pEqui.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pEqui.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pEqui.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pEqui.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pEqui_mouseClicked(e);
                    }
                });
        pImply.setBounds(new Rectangle(140, 5, 22, 22));
        pImply.setHorizontalAlignment(SwingConstants.CENTER);
        pImply.setHorizontalTextPosition(SwingConstants.CENTER);
        pImply.setIconTextGap(1);
        pImply.setIcon(imply);
        pImply.setToolTipText("Implication");
        pImply.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pImply.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pImply.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pImply.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pImply.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pImply_mouseClicked(e);
                    }
                });
        pIn.setBounds(new Rectangle(170, 5, 22, 22));
        pIn.setHorizontalAlignment(SwingConstants.CENTER);
        pIn.setHorizontalTextPosition(SwingConstants.CENTER);
        pIn.setIconTextGap(1);
        pIn.setIcon(in);
        pIn.setToolTipText("In");
        pIn.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pIn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pIn.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pIn.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pIn.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pIn_mouseClicked(e);
                    }
                });
        pLike.setBounds(new Rectangle(195, 5, 22, 22));
        pLike.setHorizontalAlignment(SwingConstants.CENTER);
        pLike.setHorizontalTextPosition(SwingConstants.CENTER);
        pLike.setIconTextGap(1);
        pLike.setIcon(lk);
        pLike.setToolTipText("Like");
        pLike.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pLike.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pLike.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pLike.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pLike.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pLike_mouseClicked(e);
                    }
                });
        sep1.setBounds(new Rectangle(30, 5, 4, 22));
        sep1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        sep2.setBounds(new Rectangle(110, 5, 4, 22));
        sep2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        sep3.setBounds(new Rectangle(165, 5, 4, 22));
        sep3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pEq.setBounds(new Rectangle(225, 5, 22, 22));
        pEq.setHorizontalAlignment(SwingConstants.CENTER);
        pEq.setHorizontalTextPosition(SwingConstants.CENTER);
        pEq.setIcon(eq);
        pEq.setIconTextGap(1);
        pEq.setToolTipText("Is Equal");
        pEq.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pEq.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pEq.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pEq.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pEq.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pEq_mouseClicked(e);
                    }
                });
        pNeq.setBounds(new Rectangle(250, 5, 22, 22));
        pNeq.setHorizontalAlignment(SwingConstants.CENTER);
        pNeq.setHorizontalTextPosition(SwingConstants.CENTER);
        pNeq.setIcon(neq);
        pNeq.setIconTextGap(1);
        pNeq.setToolTipText("Is Not Equal");
        pNeq.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pNeq.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pNeq.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pNeq.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pNeq.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pNeq_mouseClicked(e);
                    }
                });
        pLt.setBounds(new Rectangle(280, 5, 22, 22));
        pLt.setHorizontalAlignment(SwingConstants.CENTER);
        pLt.setHorizontalTextPosition(SwingConstants.CENTER);
        pLt.setIcon(lt);
        pLt.setIconTextGap(1);
        pLt.setToolTipText("Less Than");
        pLt.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pLt.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pLt.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pLt.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pLt.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pLt_mouseClicked(e);
                    }
                });
        pLe.setBounds(new Rectangle(305, 5, 22, 22));
        pLe.setHorizontalAlignment(SwingConstants.CENTER);
        pLe.setHorizontalTextPosition(SwingConstants.CENTER);
        pLe.setIcon(le);
        pLe.setIconTextGap(1);
        pLe.setToolTipText("Less Than or Equal To");
        pLe.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pLe.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pLe.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pLe.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pLe.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pLe_mouseClicked(e);
                    }
                });
        pGe.setBounds(new Rectangle(355, 5, 22, 22));
        pGe.setHorizontalAlignment(SwingConstants.CENTER);
        pGe.setHorizontalTextPosition(SwingConstants.CENTER);
        pGe.setIcon(ge);
        pGe.setIconTextGap(1);
        pGe.setToolTipText("Greater Than or Equal To");
        pGe.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pGe.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pGe.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pGe.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pGe.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pGe_mouseClicked(e);
                    }
                });
        pGt.setBounds(new Rectangle(330, 5, 22, 22));
        pGt.setHorizontalAlignment(SwingConstants.CENTER);
        pGt.setHorizontalTextPosition(SwingConstants.CENTER);
        pGt.setIcon(gt);
        pGt.setIconTextGap(1);
        pGt.setToolTipText("Greater Than");
        pGt.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pGt.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pGt.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pGt.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pGt.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pGt_mouseClicked(e);
                    }
                });
        pAdd.setBounds(new Rectangle(385, 5, 22, 22));
        pAdd.setHorizontalAlignment(SwingConstants.CENTER);
        pAdd.setHorizontalTextPosition(SwingConstants.CENTER);
        pAdd.setIcon(plus);
        pAdd.setIconTextGap(1);
        pAdd.setToolTipText("Addition ");
        pAdd.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pAdd.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pAdd.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pAdd.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pAdd.setBorder(BorderFactory.createEmptyBorder());
                    }        
        
                    public void mouseClicked(MouseEvent e) {
                        pAdd_mouseClicked(e);
                    }
                });
        pSub.setBounds(new Rectangle(410, 5, 22, 22));
        pSub.setHorizontalAlignment(SwingConstants.CENTER);
        pSub.setHorizontalTextPosition(SwingConstants.CENTER);
        pSub.setIcon(minus);
        pSub.setIconTextGap(1);
        pSub.setToolTipText("Subtraction");
        pSub.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pSub.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pSub.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pSub.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pSub.setBorder(BorderFactory.createEmptyBorder());
                    }     
                    
                    public void mouseClicked(MouseEvent e) {
                        pSub_mouseClicked(e);
                    }
                });
        pMulti.setBounds(new Rectangle(435, 5, 22, 22));
        pMulti.setHorizontalAlignment(SwingConstants.CENTER);
        pMulti.setHorizontalTextPosition(SwingConstants.CENTER);
        pMulti.setIcon(multi);
        pMulti.setIconTextGap(1);
        pMulti.setToolTipText("Multiplication");
        pMulti.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pMulti.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pMulti.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pMulti.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pMulti.setBorder(BorderFactory.createEmptyBorder());
                    }     
                            
                    public void mouseClicked(MouseEvent e) {
                        pMulti_mouseClicked(e);
                    }
                });
        pDiv.setBounds(new Rectangle(460, 5, 22, 22));
        pDiv.setHorizontalAlignment(SwingConstants.CENTER);
        pDiv.setHorizontalTextPosition(SwingConstants.CENTER);
        pDiv.setIcon(div);
        pDiv.setIconTextGap(1);
        pDiv.setToolTipText("Division");
        pDiv.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pDiv.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pDiv.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pDiv.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pDiv.setBorder(BorderFactory.createEmptyBorder());
                    }     
                            
                    public void mouseClicked(MouseEvent e) {
                        pDiv_mouseClicked(e);
                    }
                });
        jPanel2.setLayout(null);


        //lPanel.add(statusBar, null);
        lPanel.add(pTuple, null);
        lPanel.add(pNewExpr, null);
        lPanel.add(pSepA3, null);
        lPanel.add(cmbAddExpr, null);
        lPanel.add(pSepA2, null);
        lPanel.add(pSepA1, null);
        lPanel.add(sep6, null);
        lPanel.add(sep5, null);
        lPanel.add(sep4, null);
        lPanel.add(sep3, null);
        lPanel.add(sep2, null);
        lPanel.add(sep1, null);
        lPanel.add(pLike, null);
        lPanel.add(pIn, null);
        lPanel.add(pImply, null);
        lPanel.add(pEqui, null);
        lPanel.add(pXor, null);
        lPanel.add(pOr, null);
        lPanel.add(pAnd, null);
        lPanel.add(pNot, null);
        lPanel.add(pEq, null);
        lPanel.add(pNeq, null);
        lPanel.add(pLe, null);
        lPanel.add(pGe, null);
        lPanel.add(pGt, null);
        lPanel.add(pLt, null);
        lPanel.add(pAdd, null);
        lPanel.add(pConcat, null);
        lPanel.add(pSepA, null);
        lPanel.add(pDiv, null);
        lPanel.add(pMulti, null);
        lPanel.add(pSub, null);
        lPanel.add(pExp, null);
        lPanel.add(pBlock, null);
        lPanel.add(pFun, null);
        lPanel.add(pHelp, null);
        lPanel.add(pMaximize, null);
        jPanel3.setPreferredSize(new Dimension(150, 32));
        jPanel2.setPreferredSize(new Dimension(28, 150));

        pSave.setBounds(new Rectangle(2, 30, 22, 22));
        pSave.setHorizontalAlignment(SwingConstants.CENTER);
        pSave.setHorizontalTextPosition(SwingConstants.CENTER);
        pSave.setIconTextGap(1);
        pSave.setIcon(imageSave);
        pSave.setToolTipText("Save");
        pSave.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pSave.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pSave.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pSave.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pSave.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pSave_mouseClicked(e);
                    }
                });
        pDel.setBounds(new Rectangle(2, 60, 22, 22));
        pDel.setHorizontalAlignment(SwingConstants.CENTER);
        pDel.setHorizontalTextPosition(SwingConstants.CENTER);
        pDel.setIcon(erase);
        pDel.setToolTipText("Delete node");
        pDel.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pDel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pDel.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pDel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pDel.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pDel_mouseClicked(e);
                    }
                });
        pUndo.setBounds(new Rectangle(2, 95, 22, 22));
        pUndo.setHorizontalAlignment(SwingConstants.CENTER);
        pUndo.setHorizontalTextPosition(SwingConstants.CENTER);
        pUndo.setIcon(undoi);
        pUndo.setIconTextGap(1);
        pUndo.setToolTipText("Undo");
        pUndo.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pUndo.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pUndo.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pUndo.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pUndo.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pUndo_mouseClicked(e);
                    }
                });
        pRedu.setBounds(new Rectangle(2, 120, 22, 22));
        pRedu.setHorizontalAlignment(SwingConstants.CENTER);
        pRedu.setHorizontalTextPosition(SwingConstants.CENTER);
        pRedu.setIcon(redoi);
        pRedu.setIconTextGap(1);
        pRedu.setToolTipText("Redo");
        pRedu.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pRedu.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pRedu.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pRedu.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pRedu.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pRedu_mouseClicked(e);
                    }
                });
        pCheck.setBounds(new Rectangle(2, 180, 22, 22));
        pCheck.setHorizontalAlignment(SwingConstants.CENTER);
        pCheck.setHorizontalTextPosition(SwingConstants.CENTER);
        pCheck.setIconTextGap(1);
        pCheck.setIcon(check);
        pCheck.setToolTipText("Check Syntax");
        pCheck.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pCheck.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pCheck.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pCheck.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pCheck.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pCheck_mouseClicked(e);
                    }
                });
        jLabel1.setBounds(new Rectangle(2, 85, 22, 4));
        jLabel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jLabel4.setBounds(new Rectangle(2, 55, 22, 4));
        jLabel4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jLabel5.setBounds(new Rectangle(2, 145, 22, 4));
        jLabel5.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pExp.setBounds(new Rectangle(545, 5, 22, 22));
        pExp.setHorizontalAlignment(SwingConstants.CENTER);
        pExp.setHorizontalTextPosition(SwingConstants.CENTER);
        pExp.setIconTextGap(1);
        pExp.setIcon(exp);
        pExp.setToolTipText("Simple Expression");
        pExp.addMouseListener(new MouseAdapter() {

                    public void mouseEntered(MouseEvent e) {
                        pExp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pExp.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pExp.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pExp.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pExp_mouseClicked(e);
                    }
                });
        pFun.setBounds(new Rectangle(570, 5, 22, 22));
        pFun.setHorizontalAlignment(SwingConstants.CENTER);
        pFun.setHorizontalTextPosition(SwingConstants.CENTER);
        pFun.setIcon(functions2);
        pFun.setToolTipText("Function");
        pFun.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pFun.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pFun.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pFun.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pFun.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pFun_mouseClicked(e);
                    }
                });
        pBlock.setBounds(new Rectangle(595, 5, 22, 22));
        pBlock.setHorizontalAlignment(SwingConstants.CENTER);
        pBlock.setHorizontalTextPosition(SwingConstants.CENTER);
        pBlock.setIcon(block);
        pBlock.setIconTextGap(1);
        pBlock.setToolTipText("Parenthesis");
        pBlock.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pBlock.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pBlock.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pBlock.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pBlock.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pBlock_mouseClicked(e);
                    }
                });
        jMenu2.setText("Help");
        jMenuItem8.setText("About Expression Editor");
        ScrollPaneErr.setPreferredSize(new Dimension(55, 100));
        ScrollPaneErr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.getContentPane().setLayout(borderLayout3);
        jPanel3.add(lPanel, null);
        this.getContentPane().add(jPanel3, BorderLayout.NORTH);
        this.getContentPane().add(jPanel2, BorderLayout.WEST);
        statusBar.add(status2, null);
        statusBar.add(sep8, null);
        statusBar.add(sep7, null);
        statusBar.add(jLabel2, null);
        statusBar.add(status, null);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        jScrollPane1.getViewport().add(jPanel1, null);
        jSplitPane3.add(jScrollPane1, JSplitPane.TOP);
        ScrollPaneErr.getViewport().add(errorList, null);
        jSplitPane1.add(ScrollPaneErr, JSplitPane.BOTTOM);
        jScrollPane2.getViewport().add(txtExpression, null);
        jSplitPane1.add(jScrollPane2, JSplitPane.TOP);
        jSplitPane3.add(jSplitPane1, JSplitPane.BOTTOM);
        jSplitPane2.add(jSplitPane3, JSplitPane.RIGHT);
        jScrollPane3.getViewport().add(treeView, null);
        jSplitPane2.add(jScrollPane3, JSplitPane.LEFT);
        jPanel2.add(jsWidth, null);
        jPanel2.add(pOpen, null);
        jPanel2.add(jLabel6, null);
        jPanel2.add(pZipexp, null);
        jPanel2.add(jLabel5, null);
        jPanel2.add(jLabel4, null);
        jPanel2.add(jLabel1, null);
        jPanel2.add(pCheck, null);


        jPanel2.add(pRedu, null);
        jPanel2.add(pUndo, null);
        jPanel2.add(pDel, null);
        jPanel2.add(pSave, null);
        this.getContentPane().add(jSplitPane2, BorderLayout.CENTER);

        jPanel3.setBounds(new Rectangle(0, 0, 850, 32));
        this.setBackground(new Color(33, 100, 163));

        this.setTitle("Expression Editor");
        this.setSize(new Dimension(969, 715));
        this.setResizable(true);
        this.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        this.addComponentListener(new ComponentAdapter() {
                    public void componentResized(ComponentEvent e) {
                        this_componentResized(e);
                    }
                });
        jPanel1.setBackground(Color.white);
        jPanel1.setLayout(null);

        menuFile.add(jMenuItem7);
        menuFile.addSeparator();
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);


        jMenu1.add(jMenuItem1);
        jMenu1.add(jMenuItem2);
        jMenu1.addSeparator();
        jMenu1.add(jMenuItem3);
        jMenu1.add(jMenuItem4);
        jMenu1.add(jMenuItem5);
        jMenu1.add(jMenuItem6);
        menuBar.add(jMenu1);
        menuBar.add(jMenu3);
        menuBar.add(jMenu7);
        jMenu2.add(jMenuItem8);
        menuBar.add(jMenu2);
        jMenu7.add(jmiExpr);
        jMenu7.addSeparator();
        jMenu7.add(jmiFun);
        jMenu4.add(jmiNot);
        jMenu4.addSeparator();
        jMenu4.add(jmiAnd);
        jMenu4.add(jmiOr);
        jMenu4.add(jmiXor);
        jMenu4.add(jmiImply);
        jMenu4.add(jmiEqui);
        jMenu4.addSeparator();
        jMenu4.add(jmiIn);
        jMenu4.add(jmiLike);
        jMenu5.add(jmiEq);
        jMenu5.add(jmiNeq);
        jMenu5.addSeparator();
        jMenu5.add(jmiLt);
        jMenu5.add(jmiLe);
        jMenu5.add(jmiGe);
        jMenu5.add(jmiGt);
        jMenu3.add(jMenu4);
        jMenu3.add(jMenu5);
        jMenu3.add(jMenu6);
        jMenu3.addSeparator();
        jMenu3.add(jmiParenthesis);
        jMenu3.addSeparator();
        jMenu3.add(jmiTuple);
        jMenu6.add(jmiAdd);
        jMenu6.add(jmiSub);
        jMenu6.add(jmiMulti);
        jMenu6.add(jmiDiv);
        jMenu6.addSeparator();
        jMenu6.add(jmiConcat);
        jPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)treeView.getCellRenderer();
        renderer.setLeafIcon(leaf);
        renderer.setClosedIcon(exp);
        renderer.setOpenIcon(set);

        jsWidth.setBounds(new Rectangle(2, 215, 22, 165));
        jsWidth.setOrientation(JSlider.VERTICAL);
        jsWidth.setMinimum(250);
        jsWidth.setMaximum(1500);
        jsWidth.setValue(400);
        jsWidth.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        jsWidth_mouseReleased(e);
                    }
                });
        jmiTuple.setText("Tuple");
        jmiTuple.setHorizontalAlignment(SwingConstants.LEFT);
        jmiTuple.setIcon(tuple);
        jmiTuple.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiTuple_actionPerformed(e);
                    }
                });
        pTuple.setBounds(new Rectangle(520, 5, 22, 22));
        pTuple.setHorizontalAlignment(SwingConstants.CENTER);
        pTuple.setHorizontalTextPosition(SwingConstants.CENTER);
        pTuple.setIconTextGap(1);
        pTuple.setIcon(tuple);
        pTuple.setToolTipText("Tuple");
        pTuple.addMouseListener(new MouseAdapter() {

                    public void mouseEntered(MouseEvent e) {
                        pTuple.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pTuple.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pTuple.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pTuple.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pTuple_mouseClicked(e);
                    }
                });
        jmiParenthesis.setText("Parenthesis");
        jmiParenthesis.setHorizontalAlignment(SwingConstants.LEFT);
        jmiParenthesis.setIcon(block);
        jmiParenthesis.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiParenthesis_actionPerformed(e);
                    }
                });
        jPanel1.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        jPanel1_mouseClicked(e);
                    }
                });
        aaa = new node[2];

        String output = Attribute.checkSyntax2(txtExpression,true,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2).xml;

        ddListEditor = new DropDownList(typeOfEditor,con,txtExpression,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp));
        ddListEditor.setVisible(false);
        xmlToNodes(null,output);
        undoAction();        
        design(true);  
        tmp1[0].mClicked(null);


        try{   
            JDBCQuery query=new JDBCQuery(con);
            JDBCQuery query2=new JDBCQuery(con);
            ResultSet rs,rs1,rs2,rs3;
            //String atts = "";
            //String idp = String.valueOf(TOB);
          
                
            if(typeOfEditor == 0){
                cmbAddExpr.addItem("value");
                cmbAddExpr.addItem("this");

                    //JDBCQuery query=new JDBCQuery(con);
                    //ResultSet rs1,rs2,; 

                    String dom_ids = "-1";
                    String dom_type = "";               
                
                    //System.out.println("select * from IISC_Domain where PR_id="+ tree.ID +" and Dom_id= "+ nodeIDSyntax);
                    rs1=query.select("select * from IISC_Domain where PR_id="+ treeID +" and Dom_id= "+ id_comp );
                    boolean ok = false;
                    
                    if(rs1.next()){
                        dom_type = rs1.getString("Dom_type");
                        dom_ids = rs1.getString("Dom_id");
                        if(dom_type != null){
                            String tmpd = rs1.getString("Dom_parent");
                            if(tmpd != null) dom_ids = tmpd;
                            if(tmpd != null && ( dom_type.compareTo("1") == 0 || dom_type.compareTo("4") == 0 )){
                                while(true){
                                    rs2=query.select("select * from IISC_Domain where PR_id="+ treeID +" and Dom_id = "+ id_comp);
                                    if(rs2.next()){
                                        dom_type = rs2.getString("Dom_type");
                                        if( dom_type != null && ( dom_type.compareTo("1") == 0 || dom_type.compareTo("4") == 0 ) )
                                            dom_ids = rs2.getString("Dom_parent");
                                        else
                                            break;
                                    }
                                    else 
                                        break;
                                }
                                
                                if(rs2 != null)
                                    rs2.close();
                            }
                        }
                    }
                    if ( dom_type != null && (dom_type.compareTo("2") == 0 || dom_type.compareTo("3") == 0)){
                        rs3=query.select("select * " +
                        " from IISC_Dom_Att,IISC_ATTRIBUTE,IISC_DOMAIN " +
                        " where IISC_Dom_Att.PR_id = IISC_ATTRIBUTE.PR_id and " +
                        " IISC_Dom_Att.Att_id = IISC_ATTRIBUTE.Att_id and " +
                        " IISC_ATTRIBUTE.Dom_id = IISC_DOMAIN.Dom_id and IISC_DOMAIN.PR_id = " + treeID + " and " + 
                        " IISC_Dom_Att.PR_id="+ treeID +" and IISC_Dom_Att.Dom_id = "+ id_comp);
                        while(rs3.next()){
                            String tmps = "value." + rs3.getString("Att_mnem").trim()/*.toUpperCase()*/ + " : " + rs3.getString("Dom_mnem");
                            //dom_atts += tmps + ":::";
                             cmbAddExpr.addItem(tmps);
                        }
                        rs3.close();
                    }
                    rs1.close();                    
                
            }
            else if(typeOfEditor == 2){
                String idp = String.valueOf(id_comp);
                while(true){
                    rs = query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE " +
                                        " where PR_id="+ treeID +" and  TF_ID="+ ID2 +" and TOB_id = " + idp);
                                        
                    String nextIDP = null;
                    
                    if( rs.next() )
                        nextIDP = rs.getString("TOB_superord");
                    
                    rs = query.select("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_ATT_TOB,IISC_ATTRIBUTE " +
                                    " where IISC_COMPONENT_TYPE_OBJECT_TYPE.tob_id = IISC_ATT_TOB.tob_id AND " +
                                    " IISC_COMPONENT_TYPE_OBJECT_TYPE.tf_id = IISC_ATT_TOB.tf_id AND " +
                                    " IISC_COMPONENT_TYPE_OBJECT_TYPE.PR_id = IISC_ATT_TOB.PR_id AND " +
                                    " IISC_ATT_TOB.ATT_id = IISC_ATTRIBUTE.ATT_id AND " +
                                    " IISC_ATT_TOB.PR_id = IISC_ATTRIBUTE.PR_id AND " +
                                    " IISC_COMPONENT_TYPE_OBJECT_TYPE.PR_id = "+ treeID +" and " +
                                    " IISC_COMPONENT_TYPE_OBJECT_TYPE.TF_ID = "+ ID2 +" and " +
                                    " IISC_COMPONENT_TYPE_OBJECT_TYPE.TOB_id = " + idp );
        
                    while( rs.next() ){
                        String tmps = rs.getString("Tob_mnem") + " | " + rs.getString("Att_mnem") + " | " + rs.getString("Dom_mnem") ;//rs.getString("Att_mnem") + " : " + rs.getString("Tob_mnem") + " ";
                        cmbAddExpr.addItem(tmps);
                    }
                    
                    if(nextIDP == null)
                        break;

                    idp = nextIDP;                    
                }
            }
            else if(typeOfEditor == 1){
                rs = query.select("select * " +
                                " FROM IISC_ATTRIBUTE " +
                                " where IISC_ATTRIBUTE.PR_id = "+ treeID +" and " +
                                " IISC_ATTRIBUTE.Att_id = " + id_comp );
                                //+ " and " + 
                                //" Att_mnem like '" + tmpEx + "%' ");
                
                if( rs.next() ){  
                    String tmpS = rs.getString("Att_mnem");
                    cmbAddExpr.addItem(tmpS);
                }
            }
            
            
            
            rs = query.select("SELECT * , IISC_FUNCTION.FUN_ID as FID" + 
            "   FROM ((IISC_FUNCTION LEFT JOIN IISC_PACK_FUN ON IISC_FUNCTION.Fun_id = IISC_PACK_FUN.Fun_id) " +
            "   LEFT JOIN IISC_PACKAGE ON IISC_PACK_FUN.Pack_id = IISC_PACKAGE.Pack_id) " +
            "   LEFT JOIN IISC_DOMAIN ON IISC_FUNCTION.Dom_id = IISC_DOMAIN.Dom_id " + 
            "   WHERE IISC_FUNCTION.PR_ID = " + treeID + " AND IISC_DOMAIN.PR_ID = " + treeID +
            "   ORDER BY IISC_PACKAGE.Pack_name,IISC_FUNCTION.Fun_name " );
            
            while( rs.next() ){
                String packName = rs.getString("Pack_name");
                String domName = rs.getString("Dom_mnem");
                String funName = rs.getString("Fun_name");
                String funId = rs.getString("FID");
                if(packName == null) packName = "";
                else packName += ".";
                if(domName == null) domName = "";
                
                rs2 = query2.select("   SELECT * " +
                                    "   FROM IISC_FUN_PARAM,IISC_DOMAIN " +
                                    "   WHERE   IISC_FUN_PARAM.Dom_id = IISC_DOMAIN.Dom_id AND " +
                                    "   IISC_FUN_PARAM.PR_ID = " + treeID + " AND " +
                                    "   IISC_FUN_PARAM.Fun_id = " + funId + " AND " +
                                    "   IISC_DOMAIN.PR_ID = " + treeID);
                String params = " ( ";
                while(rs2.next())
                    params += rs2.getString("Dom_mnem") + " ,";
                params = params.substring(0,params.length() - 1);
                params += ") ";
                
                cmbAddExpr.addItem(packName + funName + params + " | " + domName);
                rs2.close();
            }

            rs.close();
            if(cmbAddExpr.getItemCount() == 0)
                cmbAddExpr.addItem(" : No Available Elements : ");
            
            cmbAddExpr.setSelectedIndex(0);
        }
        catch(SQLException ex){
            System.out.println("exDDLIST:" + ex);
        } 
        
        cmbAddExpr.updateUI();
        FontMetrics fm = getFontMetrics(getFont());
        BasicComboPopup popup = (BasicComboPopup)cmbAddExpr.getUI().getAccessibleChild(cmbAddExpr,0);//Popup
        /*if(popup==null)
            System.out.println("aaaallloooo");*/
        int size = 0;
        for(int i=0; i<cmbAddExpr.getItemCount(); i++){
            String str= (String)cmbAddExpr.getItemAt(i);
        if(size<fm.stringWidth(str))
            size = fm.stringWidth(str);        
        }
        popup.setSize(size,popup.getHeight());        
        
        
        /* BasicComboPopup p = new BasicComboPopup(jComboBox1);
         p.setPopupSize(200,200);
         p.setPreferredSize(new Dimension(200,200));
*/

         jScrollPane1.getViewport().setViewPosition(new Point(0,0));
        saveOpt = txtExpression.getText();
    }
    
    private void xmlToNodes(Object ar,String xml){
        try
        {
            XPath xpath = XPathFactory.newInstance().newXPath();
            String expression = "*";
            InputSource inputSource = null;
            NodeList nodes = null;
            if(ar == null){
                aaa = new node[ (xml.split("</block>").length - 1) * 2 + xml.split("</element>").length - 1 ];
                //System.out.println("XML:\n " + xml);
                xmlGuiNo = 0;
                inputSource = new InputSource(new StringReader(xml));
                nodes = (NodeList) xpath.evaluate(expression, inputSource, XPathConstants.NODESET);
            }
            else
                nodes = (NodeList) xpath.evaluate(expression, ar, XPathConstants.NODESET);

            int j = 1;
            for(int i=0;i<nodes.getLength();i++){
                //System.out.println("J:  " + ++dn);
                //System.out.println("-=-=-=-=-=-" + nodes.item(i).getNodeName() );
                //System.out.println("-=-=-=-=-=-" + nodes.item(i).getAttributes().getNamedItem("name").getNodeValue() );
                //System.out.println("-=-=-=-=-=-" + nodes.item(i).getAttributes().getNamedItem("group").getNodeValue() );
                //System.out.println("..........." + nodes.item(i).getNodeValue()  );
                //System.out.println("************************************************************");
                if(nodes.item(i).getNodeName() == "block"){
                    aaa[xmlGuiNo] = new node();
                    aaa[xmlGuiNo].type = "B";
                    String tmpName = nodes.item(i).getAttributes().getNamedItem("name").getNodeValue();
                    
                    if(tmpName.compareTo("+") == 0)
                        aaa[xmlGuiNo].name = "ADDITION";
                    else if(tmpName.compareTo("-") == 0)
                        aaa[xmlGuiNo].name = "SUBTRACTION";
                    else if(tmpName.compareTo("*") == 0)
                        aaa[xmlGuiNo].name = "MULTIPLICATION";
                    else if(tmpName.compareTo("/") == 0)
                        aaa[xmlGuiNo].name = "DIVISION";
                    else if(tmpName.compareTo("==") == 0)
                        aaa[xmlGuiNo].name = "IS EQUAL";
                    else if(tmpName.compareTo("!=") == 0)
                        aaa[xmlGuiNo].name = "IS NOT EQUAL";
                    else if(tmpName.compareTo("<=") == 0)
                        aaa[xmlGuiNo].name = "LESS THAN OR EQUAL TO";                        
                    else if(tmpName.compareTo("<") == 0)
                        aaa[xmlGuiNo].name = "LESS THAN";
                    else if(tmpName.compareTo(">=") == 0)
                        aaa[xmlGuiNo].name = "GREATER THAN OR EQUAL TO";                        
                    else if(tmpName.compareTo(">") == 0)
                        aaa[xmlGuiNo].name = "GREATER THAN";   
                    else if(tmpName.compareTo("<=>") == 0)
                        aaa[xmlGuiNo].name = "EQUIVALENCE";                          
                    else if(tmpName.compareTo("=>") == 0)
                        aaa[xmlGuiNo].name = "IMPLICATION";    
                    else if(tmpName.compareTo("||") == 0)
                        aaa[xmlGuiNo].name = "CONCAT";                        
                    else if(tmpName.equals(":") || tmpName.equals(".."))
                        aaa[xmlGuiNo].name = "INTERVAL";
                    else
                        aaa[xmlGuiNo].name = tmpName;
                    
                    aaa[xmlGuiNo].group = Integer.parseInt(nodes.item(i).getAttributes().getNamedItem("group").getNodeValue());
                    ++xmlGuiNo;
                    xmlToNodes(nodes.item(i),xml);
                    aaa[xmlGuiNo] = new node();
                    aaa[xmlGuiNo].type = "B";
                    aaa[xmlGuiNo].name = "*END*";
                    aaa[xmlGuiNo].group = -1;
                    ++xmlGuiNo;                    
                }
                else{
                    aaa[xmlGuiNo] = new node();
                    aaa[xmlGuiNo].type = "S";
                    aaa[xmlGuiNo].name = nodes.item(i).getAttributes().getNamedItem("name").getNodeValue();
                    aaa[xmlGuiNo].value = nodes.item(i).getAttributes().getNamedItem("value").getNodeValue();
                    aaa[xmlGuiNo].group = Integer.parseInt(nodes.item(i).getAttributes().getNamedItem("group").getNodeValue());
                    ++xmlGuiNo;                    
                }
            }
        }
        catch (XPathExpressionException e)
        {
          e.printStackTrace();
        }                
    }    
    
    public void design(boolean print){

        int stack[] = new int[aaa.length];
        int stack2[] = new int[aaa.length];
        DefaultMutableTreeNodeEditor treeArray[] = new DefaultMutableTreeNodeEditor[aaa.length];
        
        int tt = 0;
       
        int stackno = 0;
        int stackno2 = 0;
        tmp1 = new expPanel[aaa.length];
        lblIcon = new JLabel[aaa.length];
        tmp1[0] = new expPanel(0,this);
        tmp1[0].jLabel1.setText(aaa[0].name);
        tmp1[0].jLabel1.setIcon(expr);         
        tmp1[0].setBounds(20,20,350,150);
        tmp1[0].jTextArea1.setVisible(false);
        tmp1[0].jComboBox1.setVisible(false);
        tmp1[0].jButton1.setEnabled(false);
        lblIcon[0] = tmp1[0].lblPosition;
        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.add(ddListEditor);
        jPanel1.add(tmp1[0]);
        stack[stackno++] = 0;
        int max = 0;
        
        jScrollPane3.remove(treeView);
        
        treeArray[tt] = new DefaultMutableTreeNodeEditor(tt,aaa[0].name);
        tt++;
        stack2[stackno2++] = 0;        

        for(int i=1; i < aaa.length; i++){
            if( aaa[i].type.compareTo("S") == 0 /*|| 
                (aaa[i].type.compareTo("B") == 0 && aaa[i].simpleExpr && aaa[i].name.compareTo("*END*") != 0)*/){
                
                tmp1[i] = new expPanel(i,this);
                tmp1[i].jTextArea1.setText(aaa[i].value);
                tmp1[i].jButton1.setVisible(false);
                lblIcon[i] = tmp1[i].lblPosition;
                tmp1[i].jLabel1.setVisible(false);
                tmp1[i].jcombot = false;
                int caseGroup = aaa[i].group;
                printStep = i;
                /*if(aaa[i].type.compareTo("B") == 0){
                    tmp1[i].jTextArea1.setToolTipText(print(i));
                    tmp1[i].jComboBox1.setToolTipText("Simple Expression");
                    caseGroup = 0;
                }
                else{*/
                    tmp1[i].jTextArea1.setToolTipText(aaa[i].value);
                    tmp1[i].jComboBox1.setToolTipText(aaa[i].name);
                //}
                
                if(caseGroup == 0)
                    tmp1[i].jComboBox1.addItem(new JLabel("Simple Expression"));
                else if(caseGroup == 1)
                    tmp1[i].jComboBox1.addItem(new JLabel("SET Element"));
                else if(caseGroup == 888)
                    tmp1[i].jComboBox1.addItem(new JLabel("LIKE Pattern"));
                else if(caseGroup == 3)
                    tmp1[i].jComboBox1.addItem(new JLabel("CHECK Element"));
                else
                    tmp1[i].jComboBox1.addItem(new JLabel("Unknown"));
                
                for(int s=0;s<tmp1[i].jComboBox1.getItemCount(); s++)
                    if(  ((JLabel)tmp1[i].jComboBox1.getItemAt(s)).getText().compareTo(aaa[i].name) == 0 ){
                        tmp1[i].jComboBox1.setSelectedIndex(s);
                        break;
                    }
                tmp1[i].jcombot = true;                    
                
                //if(aaa[i].type.compareTo("S") == 0){
                    
                    treeArray[tt] = new DefaultMutableTreeNodeEditor(tt,aaa[i].value);
                    tt++;
                    treeArray[stack2[stackno2 - 1]].add(treeArray[tt - 1]);

                    if( stack[stackno - 1] + 1 == i )
                        tmp1[i].setBounds(glX,glY,glWidth,glSsHeight);
                    else
                        tmp1[i].setBounds(
                            tmp1[i - 1].getX(),
                            tmp1[i - 1].getY() + tmp1[i - 1].getHeight() , glWidth, glSsHeight);
    
                    tmp1[stack[stackno - 1]].setSize(glWidth,tmp1[i].getHeight() + tmp1[i].getY() + glSsHeight);
                    tmp1[stack[stackno - 1]].add(tmp1[i]);
                /*}
                
                else if(aaa[i].type.compareTo("B") == 0){
                    if( stack[stackno - 1] + 1 == i )
                        tmp1[i].setBounds(glX,glY,glWidth,glSsHeight);
                    else
                        tmp1[i].setBounds(
                            tmp1[i - 1].getX(),
                            tmp1[i - 1].getY() + tmp1[i - 1].getHeight() , glWidth, glSsHeight);
                            
                    i = printStep;
                }*/
            }            
            else if( aaa[i].type.compareTo("B") == 0 ){
                if( aaa[i].name.compareTo("*END*") != 0 ){
                    
                    tmp1[i] = new expPanel(i,this);
                    tmp1[i].jLabel1.setText(aaa[i].name);
                    tmp1[i].setToolTipText(aaa[i].name);
                    tmp1[i].jLabel1.setToolTipText(aaa[i].name);
                    lblIcon[i] = tmp1[i].lblPosition;
                        
                    if(aaa[i].expand.compareTo("+") == 0){
                        tmp1[i].jButton1.setText("+");
                        tmp1[i].jButton1.setBackground(Color.BLUE);
                    }
                    else{
                        tmp1[i].jButton1.setText("-");
                        tmp1[i].jButton1.setBackground(Color.WHITE);
                    }
                        
                    tmp1[i].jTextArea1.setVisible(false);
                    tmp1[i].jComboBox1.setVisible(false);
                    
                    if( stack[stackno -1] + 1 == i ){
                        if(aaa[i].expand.compareTo("+") == 0)
                            tmp1[i].setBounds(glX,glY,glWidth,glPlus);
                        else
                            tmp1[i].setBounds(glX,glY,glWidth,glBlHeight);
                    }
                    else{
                        if(aaa[i].expand.compareTo("+") == 0)
                            tmp1[i].setBounds( tmp1[i-1].getX(), tmp1[i-1].getY() + tmp1[i-1].getHeight()  ,
                                                glWidth,glPlus);
                        else
                            tmp1[i].setBounds(tmp1[i-1].getX(), tmp1[i-1].getY() + tmp1[i-1].getHeight()  ,
                                                glWidth, glBlHeight);
                    }
                    tmp1[stack[stackno - 1]].setSize(glWidth,tmp1[i].getHeight() + tmp1[i].getY());
                    tmp1[stack[stackno - 1]].add(tmp1[i]);
                    stack[stackno++] = i;
                    
                    treeArray[tt] = new DefaultMutableTreeNodeEditor(tt,aaa[i].name);
                    tt++;
                    treeArray[stack2[stackno2 - 1]].add(treeArray[tt - 1]);
                    stack2[stackno2++] = i;
                    
                    
                    if(max < stackno)
                        max = stackno;
                        
                    if(aaa[i].expand.compareTo("+") == 0){    
                        int br = 1;
                        for( i = i + 1 ; i < aaa.length ;i++){
                            //tmp1[i] = new expPanel(i,this);
                            //treeArray[tt] = new DefaultMutableTreeNodeEditor(tt,aaa[i].name);
                            tt++;
                            if(aaa[i].type.compareTo("B") == 0){
                                if(aaa[i].name.compareTo("*END*") != 0)
                                    br++;
                                else //if( aaa[i].name.compareTo("*END*") == 0)
                                    br--;           
                            }
                            if(br == 0){
                                i--;
                                tt--;
                                break;
                            }
                        }                         
                    }
                }
                else{ // ako je END block 
                    if(stackno > 1)
                        tmp1[stack[stackno - 2]].setSize(glWidth,
                        tmp1[stack[stackno - 1]].getY() + tmp1[stack[stackno - 1]].getHeight() + glBottom  );
                    tmp1[i] = new expPanel(i,this);
                    lblIcon[i] = tmp1[i].lblPosition;
                    //tmp1[stack[stackno - 1]].setSize(tmp1[stack[stackno - 1]].getWidth() ,tmp1[stack[stackno - 1]].getHeight() ) ;    
                    tmp1[i].setBounds(tmp1[stack[stackno - 1]].getBounds());
                    stackno--;
                    treeArray[tt] = new DefaultMutableTreeNodeEditor(tt,aaa[i].name);
                    tt++;
                    stackno2--;
                }
            }
        }
        //System.out.println("------------------------------------------");
        //System.out.println(":MAX:" + max);
        //System.out.println(":SIRINA:" + (max * glX + glWidth2 + 100));

        if( max * rightHeight + glWidth2  != tmp1[0].getHeight()){
            tmp1[0].setSize(max * rightHeight + glWidth2 ,tmp1[tmp1.length - 2].getY() + tmp1[tmp1.length - 2].getHeight() + glBottom );
            jPanel1.setPreferredSize( new Dimension(tmp1[0].getWidth() + 50, tmp1[0].getHeight() + 50));
            jPanel1.setSize( new Dimension(tmp1[0].getWidth() + 50, tmp1[0].getHeight() + 50));
        }

        if(print){
            txtExpression.setText(print(1).trim());
            Attribute.checkSyntax2(txtExpression,false,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2);
        }
        
        //treeView = new JTree(treeArray[0]);
         treeModel.setRoot(null);
         treeModel.setRoot(treeArray[0]);        
        /*DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)treeView.getCellRenderer();
        jScrollPane3.getViewport().add(treeView, null);
        renderer.setLeafIcon(leaf);
        renderer.setClosedIcon(exp);
        renderer.setOpenIcon(set);          
        treeView.setSelectionRow(0);
        treeView.expandPath(treeView.getSelectionPath());
        treeView.setBounds(new Rectangle(30, 3, 165, 605));
        treeView.setShowsRootHandles(true);    
        treeView.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        treeView_mouseClicked(e);
                    }
                });        

        treeView.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));  
        */
        expandAll(treeView,true);      

        
    }

         // If expand is true, expands all nodes in the tree.
         // Otherwise, collapses all nodes in the tree.
         public void expandAll(JTree tree, boolean expand) {
             TreeNode root = (TreeNode)tree.getModel().getRoot();

             // Traverse tree from root
             expandAll(tree, new TreePath(root), expand);
         }
         private void expandAll(JTree tree, TreePath parent, boolean expand) {
             // Traverse children
             TreeNode node = (TreeNode)parent.getLastPathComponent();
             if (node.getChildCount() >= 0) {
                 for (Enumeration e=node.children(); e.hasMoreElements(); ) {
                     TreeNode n = (TreeNode)e.nextElement();
                     TreePath path = parent.pathByAddingChild(n);
                     expandAll(tree, path, expand);
                 }
             }

             // Expansion or collapse must be done bottom-up
             if (expand) {
                 tree.expandPath(parent);
             } else {
                 tree.collapsePath(parent);
             }
         }


    private String convertTextToOperator(String text){
        
        if(text.compareTo("ADDITION") == 0)
            return "+";
        else if(text.compareTo("SUBTRACTION") == 0)
            return "-";
        else if(text.compareTo("MULTIPLICATION") == 0)
            return "*";
        else if(text.compareTo("DIVISION") == 0)
            return "/";
        else if(text.compareTo("IS EQUAL") == 0)
            return "==";
        else if(text.compareTo("IS NOT EQUAL") == 0)
            return "!=";
        else if(text.compareTo("GREATER THAN") == 0)
            return ">";
        else if(text.compareTo("GREATER THAN OR EQUAL TO") == 0)
            return ">=";
        else if(text.compareTo("LESS THAN") == 0)
            return "<";
        else if(text.compareTo("LESS THAN OR EQUAL TO") == 0)
            return "<=";
        else if(text.compareTo("EQUIVALENCE") == 0)
            return "<=>";
        else if(text.compareTo("IMPLICATION") == 0)
            return "=>";
        else if(text.compareTo("CONCAT") == 0)
            return "||";
        else if(text.compareTo("INTERVAL") == 0)
            return "..";            
        else if(text.length() > 8 && text.substring(0,8).compareTo("FUNCTION") == 0)
            return text.split("\\(")[0].split("-")[1];
        else
            return text;
        
    }
    
    public String print(int poz){
        
        String output = "";
        int[] stack =new int[aaa.length];
        int pop = 0;
        
        //for(int i=0;i<aaa.length;i++)
        //    System.out.println(aaa[i].name);
        
        int i = 0;
        for(i=poz;i<aaa.length && pop >= 0; i++){
            if(aaa[i].type.compareTo("B") == 0){
                if( aaa[i].group == 1 || aaa[i].group == 2 || aaa[i].group == 78 || 
                    aaa[i].group == 4 || aaa[i].group ==5 || aaa[i].group ==88 || 
                    aaa[i].group ==8  || aaa[i].group == 99 || aaa[i].group == 9 || 
                    aaa[i].group == 10 || aaa[i].group == 9999 || aaa[i].group == 13 ){
                    stack[pop++] = i;
                    //output += " *" + aaa[i].name + "*(";
                    //System.out.println("ooo: " + output);
                    if(aaa[i].group ==78 || aaa[i].group == 13)
                    output += "( ";
                }
                else if(aaa[i].group == -1){
                    --pop;
                    if(pop >= 0 && aaa[stack[pop]].group != 77 && aaa[stack[pop]].group != 0 ){
                        //if(pop >= 0 && aaa[stack[pop]].group == 78)
                        //    output += "BLOCK2";
                            
                        String[] splitS = output.split(" ");
                        
                        if( i - 1 != stack[pop] && aaa[stack[pop]].group != 78 ){
                            output = "";
                            for(int u = 0; u < splitS.length - 1; u++)
                                output += splitS[u] + " ";
                        }
                        //output += " )*" + aaa[stack[pop]].name + "*";
                        if(aaa[stack[pop]].group == 999)
                            output += "} ";
                        else{
                            if(aaa[stack[pop]].group == 78 || aaa[stack[pop]].group == 7 ||  aaa[stack[pop]].group == 13)
                                output += ") ";
                        }
                    }
                    if( pop > 0 && aaa[stack[pop - 1]].group != 7 && aaa[stack[pop - 1]].group != 77 && 
                        aaa[stack[pop - 1]].group != 0 && aaa[stack[pop - 1]].group != 999 && 
                        aaa[stack[pop - 1]].group != 78 &&  aaa[stack[pop - 1]].group != 13)
                        output += convertTextToOperator(aaa[stack[pop - 1]].name) + " ";
                    else if(pop > 0 && /*(aaa[stack[pop ]].group == 77 ||*/ ( aaa[stack[pop - 1]].group == 999 || aaa[stack[pop - 1]].group == 13)/*)*/)
                        output += ", " ;
                    else if(pop > 0 && aaa[stack[pop]].group == 77 && aaa[stack[pop - 1]].group == 7)
                        output += ", " ;
                        
                }
                else if(aaa[i].group == 0){
                    stack[pop++] = i;
                    output += aaa[i].name + " ";
                }
                else if(aaa[i].group == 7){
                    stack[pop++] = i;
                    output += convertTextToOperator(aaa[i].name) + "( ";                 
                }
                else if(aaa[i].group == 77){
                    stack[pop++] = i;
                }
                else if(aaa[i].group == 999){
                    stack[pop++] = i;
                    output += "{ ";
                }
            }
            else if(aaa[i].type.compareTo("S") == 0 ){
                output += aaa[i].value + " ";
                if( pop > 0 && aaa[stack[pop - 1]].group != 7 && aaa[stack[pop - 1]].group != 77 && 
                    aaa[stack[pop - 1]].group != 0 && aaa[stack[pop - 1]].group != 999 && 
                    aaa[stack[pop - 1]].group != 78 &&  aaa[stack[pop - 1]].group != 13)
                    output += convertTextToOperator(aaa[stack[pop - 1]].name) + " ";
                /*else if(pop > 0 && (aaa[stack[pop - 1]].group == 77 || aaa[stack[pop - 1]].group == 999))
                    output += ", " ;*/
                 else if(pop > 0 && /*(aaa[stack[pop ]].group == 77 ||*/ (aaa[stack[pop - 1]].group == 999 || aaa[stack[pop - 1]].group == 13)/*)*/)
                     output += ", " ;
                 /*else if(pop > 0 && aaa[stack[pop ]].group == 77 && aaa[stack[pop - 1]].group == 7)
                     output += ", " ;                    */
            }
            //System.out.println(output);
            if(pop == 0) 
                pop--;
        }   
        //txtExpression.setText(output);
        printStep = i - 1;
        //System.out.println(output);
        return output ;
    } 
    
    public void disableBlockButtons(){

        boolean enabled = true;

        if(aaa[this.clicked].type.equals("S")){
            cmbAddExpr.setEnabled(true);
            pNewExpr.setEnabled(true);
            enabled = false;
        }
        else{
            cmbAddExpr.setEnabled(false);
            pNewExpr.setEnabled(false);
        }

        jMenu3.setEnabled(enabled);
        jMenu7.setEnabled(enabled);
        pDel.setEnabled(true);
        if( aaa[this.clicked].group == 7 || aaa[this.clicked].group == 9 || aaa[this.clicked].group == 8){
            enabled = false;
        }
        else if(aaa[this.clicked].group == 77 || aaa[this.clicked].group == 99 || 
                aaa[this.clicked].group == 88 || aaa[this.clicked].group == 888 || aaa[this.clicked].group == 999){
            enabled = true;
            pDel.setEnabled(!enabled);
            pDel.setEnabled(false);
        }
        
        jMenu3.setEnabled(enabled);
        jMenu7.setEnabled(enabled);
        pNot.setEnabled(enabled);
        jmiNot.setEnabled(enabled);
        pAnd.setEnabled(enabled);
        jmiAnd.setEnabled(enabled);
        pOr.setEnabled(enabled);
        jmiOr.setEnabled(enabled);
        pXor.setEnabled(enabled);
        jmiXor.setEnabled(enabled);
        pEqui.setEnabled(enabled);
        jmiEqui.setEnabled(enabled);
        pImply.setEnabled(enabled);
        jmiImply.setEnabled(enabled);
        pLike.setEnabled(enabled);
        jmiLike.setEnabled(enabled);
        pIn.setEnabled(enabled);
        jmiIn.setEnabled(enabled);
        
        pEq.setEnabled(enabled);
        jmiEq.setEnabled(enabled);
        pNeq.setEnabled(enabled);
        jmiNeq.setEnabled(enabled);
        pLt.setEnabled(enabled);
        jmiLt.setEnabled(enabled);
        pLe.setEnabled(enabled);
        jmiLe.setEnabled(enabled);
        pGt.setEnabled(enabled);
        jmiGt.setEnabled(enabled);
        pGe.setEnabled(enabled);
        jmiGe.setEnabled(enabled);
        
        pAdd.setEnabled(enabled);
        jmiAdd.setEnabled(enabled);
        pSub.setEnabled(enabled);
        jmiSub.setEnabled(enabled);
        pMulti.setEnabled(enabled);
        jmiMulti.setEnabled(enabled);
        pDiv.setEnabled(enabled);
        jmiDiv.setEnabled(enabled);
        pConcat.setEnabled(enabled);
        jmiConcat.setEnabled(enabled);
        
        pTuple.setEnabled(enabled);
        jmiTuple.setEnabled(enabled);

        pExp.setEnabled(enabled);
        jmiExpr.setEnabled(enabled);
        pFun.setEnabled(enabled);
        jmiFun.setEnabled(enabled);
        pBlock.setEnabled(enabled);
        jmiParenthesis.setEnabled(enabled);

    }

    public void changeBlock(String namee,int position,boolean desg){
        if(aaa[position].type.compareTo("S") == 0)
            return;

        node tmpN = aaa[position];
        aaa[position] = new node();
        aaa[position].name = namee;
        aaa[position].type = tmpN.type;
        aaa[position].value = tmpN.value;
        aaa[position].group = tmpN.group;
        aaa[position].expand = tmpN.expand;
        
        if(desg){
            undoAction(); 
            design(true);
        }
    }

    public int addNode(String namee,String typee,int position,int gr, boolean desingE,String valuee ){
        
        int pos = this.clicked;
        
        if(aaa[pos].type.compareTo("S") == 0 && position == 0)
            return -1;
        
        if(position == -1)
            pos = this.clicked;
        else if(position == 0){
            int br = 0;
            for(int i= pos ; i < aaa.length ;i++){
                if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") != 0)
                    br++;
                else if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") == 0)
                    br--;
            
                if(br == 0){
                    pos = i ;
                    break;
                }            
            }
        }
        else if(position == 1){
            int br = 1;
            pos++;
            for(int i= pos ; i < aaa.length && aaa[this.clicked].type.compareTo("B") == 0 ;i++){
                if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") != 0)
                    br++;
                else if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") == 0)
                    br--;
            
                if(br == 0){
                    pos = i + 1;
                    break;
                }            
            }        
        }
        
        node tmpPa[];
        int duztmp = aaa.length + 1;
        if(typee.compareTo("B") == 0)   
            duztmp++;
        
        tmpPa = new node[duztmp];
        
        int i;
        for(i=0;i<pos;i++)
             tmpPa[i] = aaa[i];
            
        tmpPa[i] = new node();
        tmpPa[i].type = typee;
        tmpPa[i].name = namee;
        tmpPa[i].group = gr;
        tmpPa[i].value = valuee;
        int returni = i;
        i++;
        if(typee.compareTo("B") == 0){
            tmpPa[i] = new node();
            tmpPa[i].type = "B";
            tmpPa[i].name = "*END*";
            tmpPa[i].group = -1;
            i++;
        }        

        for(int j = pos ;j < aaa.length; j++)
             tmpPa[i++] = aaa[j];
          
        aaa = tmpPa; 
        
        
        if(desingE){
            undoAction();  
            design(true);
        }
            
        return returni;
    }

    
    public int move(int position,boolean dsg,int pr,int re){
    
        //System.out.println("Pozition MOVE: " + position);
        
        if( aaa[pr].group == 999 || aaa[pr].group == 99 || aaa[pr].group == 88 || aaa[pr].group == 888 || aaa[pr].group == 77 ){
            status2.setForeground(Color.RED);
            status2.setText("Cannot MOVE selected node:" + aaa[pr].name + aaa[pr].value);
            pressed = released = -1;
            movePosition = 0;
            //tmp1[pr].mClicked(null);
            return -1;
        }
        else if( ( (aaa[re].group == 7 || aaa[re].group == 9 || aaa[re].group == 8) && position == 0 ) || 
            ((aaa[re].group == 999 || aaa[re].group == 99 || aaa[re].group == 88 || aaa[re].group == 888 || aaa[re].group == 77) && (position == -1 || position == 1))){
            status2.setForeground(Color.RED);
            status2.setText("Cannot MOVE selected node on this position!");
            pressed = released = -1;
            movePosition = 0;
            //tmp1[pr].mClicked(null);
            return -1;        
        }
        else{
            status2.setForeground(Color.BLUE);
            status2.setText("");
        }        
        
        if(pr < 1 || re < 1 || pr == re)
            return -1;
        
        int pik = pr + 1;
        int br = 1;
        
        if(aaa[pr].type.compareTo("B") == 0)
            for(int i= pr + 1; i < aaa.length ;i++){
                if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") != 0)
                    br++;
                else if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") == 0)
                    br--;
            
                if(br == 0){
                    pik = i + 1;
                    break;
                }
            }                

         if(position == 0 && aaa[re].type.compareTo("S") == 0)
            return -1;

        if(re < pik && re >= pr)
            return -1;

        if(position == 0 || position == 1){
            re++;
            br = 1;
            if(aaa[re - 1].type.compareTo("B") == 0)
                for(int i= re ; i < aaa.length ;i++){
                    if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") != 0)
                        br++;
                    else if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") == 0)
                        br--;
                
                    if(br == 0){
                        if(position == 1)
                            re = i + 1 ;
                        else if(position == 0)
                            re = i ;
                        break;
                    }
                }             
        }

        if( re == pr ){
            return -1;
        }
        
        node tmpp[] = new node[aaa.length ];
        //System.out.println("MOVE  Pressed: " + pr + "   Released: " + re);        
        //jjjfor(int i=0;i<aaa.length;i++)
       //jjj    tmpp[i] = aaa[i];
        tmpp = aaa;
        int first = -1;
        int second = -1;

        aaa = new node[tmpp.length ];        
        
        int i=0;
        //System.out.println("MOVE  Pressed: " + pr + "   Released: " + re);     

        if(re < pr){
            first = re;
            second = pr;

            for(i=0; i < first ; i++)
                aaa[i] = tmpp[i];
            
            for(int j=second; j< pik; j++)
                aaa[i++] = tmpp[j];            
            
            for(int j = first; j < second; j++)
                aaa[i++] = tmpp[j];
                
            for(int j=pik; j< tmpp.length; j++)
                aaa[i++] = tmpp[j];
            
        }
        else{
            first = pr;
            second = re;
            
            //System.out.println("First  : " + first);
            //System.out.println("Second : " + second);

            for(i=0; i < first ; i++)
                aaa[i] = tmpp[i];
                
            for(int j=pik; j < second ; j++)
                aaa[i++] = tmpp[j];

            for(int j = first; j< pik; j++)
                aaa[i++] = tmpp[j];
                
            for(int j = second ; j< tmpp.length; j++)
                aaa[i++] = tmpp[j];
        }

        /*for(int y=0;y<aaa.length;y++)
            System.out.println("move: " + y + "  NAME: " + tmpp[y].name);
            
        System.out.println("***************************************");
        
        for(int y=0;y<aaa.length;y++)
            System.out.println("move: " + y + "  NAME: " + aaa[y].name);
        */

        if(dsg){
            undoAction();
            design(true);
        }

        return (pik - pr);
    }    
    

    public int copy(int position,boolean dsg,int pr,int re){

        if(pr == -1 || re == -1 || pr == re)
            return -1;
        
        int pik = pr + 1;
        int br = 1;
        
        if(aaa[pr].type.compareTo("B") == 0)
            for(int i= pr + 1; i < aaa.length ;i++){
                if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") != 0)
                    br++;
                else if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") == 0)
                    br--;
            
                if(br == 0){
                    pik = i + 1;
                    break;
                }
            }                

         if(position == 0 && aaa[re].type.compareTo("S") == 0)
            return -1;
        
        if(position == 0 || position == 1){
            re++;
            br = 1;
            if(aaa[re - 1].type.compareTo("B") == 0)
                for(int i= re ; i < aaa.length ;i++){
                    if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") != 0)
                        br++;
                    else if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") == 0)
                        br--;
                
                    if(br == 0){
                        if(position == 1)
                            re = i + 1 ;
                        else if(position == 0)
                            re = i ;
                        break;
                    }
                }             
        }
        
        node tmpp[] = new node[aaa.length ];
        
        //jjjfor(int i=0;i<aaa.length;i++)
       //jjj    tmpp[i] = aaa[i];
        tmpp = aaa;
            
        aaa = new node[tmpp.length + pik - pr];
        int i=0;
        for(i=0; i < re ; i++){
            aaa[i] = tmpp[i];
        }
        
        for(int j=pr; j< pik; j++){
            aaa[i] = new node();
            aaa[i].name = tmpp[j].name;
            aaa[i].value = tmpp[j].value;
            aaa[i].type = tmpp[j].type;
            aaa[i].group = tmpp[j].group;
            aaa[i].expand = tmpp[j].expand;
            i++;
        }
        
        for(int j=re; j<tmpp.length; j++){
            aaa[i] = tmpp[j];
            i++;
        }
        
        if(dsg){
            undoAction();
            design(true);
        }
        
        return (pik - pr);
        
    }
    
    public void deleteNode(boolean dsg){

        if(this.clicked == 0)
            return;

        int br = 1;

        if(aaa[this.clicked].type.compareTo("S") == 0 ){
            br = this.clicked + 1;
        }
        else
            for(int i= this.clicked + 1; i < aaa.length ;i++){
                if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") != 0)
                    br++;
                else if(aaa[i].type.compareTo("B") == 0 && aaa[i].name.compareTo("*END*") == 0)
                    br--;
    
                if(br == 0){
                    br = i + 1;
                    break;
                }
            }
        
        node[] tmpP = new node[aaa.length - (br - this.clicked)];
        
        int i;
        for(i=0;i< this.clicked;i++)
            tmpP[i] = aaa[i];
            
        for(int j = br; j < aaa.length; j++)
            tmpP[i++] = aaa[j];

        aaa = tmpP;
        
        
        if(dsg){
            undoAction();
            design(true);
        }
        
        tmp1[0].mClicked(null);
        
    }
   
    private void undoAction(){
        
        if(undoNo == undon.length - 1){
            undoNo = 0;
            undoPoz = 0;
        }
        
        if(undoPoz < 0)
            undoPoz = 0;

        if( undoPoz < undoNo - 1 )        
            undoNo = undoPoz +1;
        
        undon[undoNo] = new node[aaa.length];
        for(int k = 0; k< aaa.length; k++){
            undon[undoNo][k] = new node();
            undon[undoNo][k] = aaa[k];
        }
        //undoPoz = ++undoNo;
        ++undoNo;
        undoPoz = undoNo - 1;
    }


    private void buttonFunc_actionPerformed(ActionEvent e) {
        FuncSelection tmp = new FuncSelection(this,"Select Function",true,true,con,"1","1","1",1,"");
        tmp.setVisible(true);
    }

    private void jMenuItem7_actionPerformed(ActionEvent e) {
        save();
    }

    private void pNot_mouseClicked(MouseEvent e) {
        if(!pNot.isEnabled())
            return;
        int tmpc = addNode("NOT","B",0,0,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pAnd_mouseClicked(MouseEvent e) {
        if(!pAnd.isEnabled())
            return;    
        int tmpc = addNode("AND","B",0,1,true,"");  
        tmp1[tmpc].mClicked(null);
    }

    private void pOr_mouseClicked(MouseEvent e) {
        if(!pOr.isEnabled())
            return;     
        int tmpc = addNode("OR","B",0,1,true,"");   
        tmp1[tmpc].mClicked(null);
    }

    private void pXor_mouseClicked(MouseEvent e) {
        if(!pXor.isEnabled())
            return;      
        int tmpc = addNode("XOR","B",0,1,true,"");  
        tmp1[tmpc].mClicked(null);
    }

    private void pEqui_mouseClicked(MouseEvent e) {
        if(!pEqui.isEnabled())
            return;      
        int tmpc = addNode("EQUIVALENCE","B",0,2,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pImply_mouseClicked(MouseEvent e) {
        if(!pImply.isEnabled())
            return;    
        int tmpc = addNode("IMPLICATION","B",0,2,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pIn_mouseClicked(MouseEvent e) {
        if(!pIn.isEnabled())
            return;      
        int tmpc = this.clicked;
        this.clicked = addNode("IN","B",0,9,false,"");
        addNode("IN_Expression","B",0,99,false,"");
        this.clicked = addNode("SET","B",0,999,false,"");
        addNode("Simple Expression","S",0,0,false,"");
        addNode("Simple Expression","S",0,0,true,"");
        tmp1[tmpc].mClicked(null);
    }

    private void pLike_mouseClicked(MouseEvent e) {
        if(!pLike.isEnabled())
            return;      
        int tmpc = this.clicked;
        this.clicked = addNode("LIKE","B",0,8,false,"");
        addNode("LIKE_Expression","B",0,88,false,"");
        addNode("LIKE Pattern","S",0,888,true,"");
        tmp1[tmpc].mClicked(null);
    }

    private void pEq_mouseClicked(MouseEvent e) {
        if(!pEq.isEnabled())
            return;      
        int tmpc = addNode("IS EQUAL","B",0,4,true,"");
        tmp1[tmpc].mClicked(null);
    }

    private void pNeq_mouseClicked(MouseEvent e) {
        if(!pNeq.isEnabled())
            return;     
        int tmpc = addNode("IS NOT EQUAL","B",0,4,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pLt_mouseClicked(MouseEvent e) {
        if(!pLt.isEnabled())
            return;    
        int tmpc = addNode("LESS THAN","B",0,4,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pLe_mouseClicked(MouseEvent e) {
        if(!pLe.isEnabled())
            return;        
        int tmpc = addNode("LESS THAN OR EQUAL TO","B",0,4,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pGe_mouseClicked(MouseEvent e) {
        if(!pGe.isEnabled())
            return;      
        int tmpc = addNode("GREATER THAN OR EQUAL TO","B",0,4,true,"");    
        tmp1[tmpc].mClicked(null);
    }


    private void pGt_mouseClicked(MouseEvent e) {
        if(!pGt.isEnabled())
            return;       
        int tmpc = addNode("GREATER THAN","B",0,4,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pAdd_mouseClicked(MouseEvent e) {
        if(!pAdd.isEnabled())
            return;         
        int tmpc = addNode("ADDITION","B",0,5,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pSub_mouseClicked(MouseEvent e) {
        if(!pSub.isEnabled())
            return;     
        int tmpc = addNode("SUBTRACTION","B",0,5,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pMulti_mouseClicked(MouseEvent e) {
        if(!pMulti.isEnabled())
            return;     
        int tmpc = addNode("MULTIPLICATION","B",0,5,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pDiv_mouseClicked(MouseEvent e) {
        if(!pDiv.isEnabled())
            return;    
        int tmpc = addNode("DIVISION","B",0,5,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pCheck_mouseClicked(MouseEvent e) {
        CCEValue tmpCCVE = Attribute.checkSyntax2(txtExpression,true,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2);
        
        if(tmpCCVE.check){
            drawSyntax(tmpCCVE);
        }
    }
    
    public void drawSyntax(CCEValue tmpCCVE){
        //System.out.println("XML:\n"+tmpCCVE.xml);
        xmlToNodes(null,tmpCCVE.xml);
        undoAction();        
        design(false);  
        tmp1[0].mClicked(null);        
    }

    private void pRedu_mouseClicked(MouseEvent e) {
        if(undoPoz < 0)
            undoPoz = 0;     
            
        undoPoz++;
        
        if(undoPoz >= undoNo){
           undoPoz = undoNo - 1;
           return;
        }
        aaa = undon[undoPoz];
        design(true);    
    }

    private void pUndo_mouseClicked(MouseEvent e) {
        if(--undoPoz < 0)
            return;

        aaa = undon[undoPoz];
        design(true);    
    }

    private void pDel_mouseClicked(MouseEvent e) {
        if(!pDel.isEnabled())
            return;
        deleteNode(true);    
    }


    private void pExp_mouseClicked(MouseEvent e) {
        if(!pExp.isEnabled())
            return;    
        int tmpc = this.clicked;
        addNode("Simple Expression","S",0,0,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pFun_mouseClicked(MouseEvent e) {
        if(!pFun.isEnabled())
            return;       
        FuncSelection tmp = new FuncSelection(this,"Select Function",true,true,con,treeID,ID2,String.valueOf(id_comp),typeOfEditor,nodeNameSyntax);
        tmp.setVisible(true);    
    }

    private void pBlock_mouseClicked(MouseEvent e) {
        if(!pBlock.isEnabled())
            return;     
        int tmpc = addNode("BLOCK","B",0,78,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pConcat_mouseClicked(MouseEvent e) {
        if(!pConcat.isEnabled())
            return;     
        int tmpc = addNode("CONCAT","B",0,10,true,"");
        tmp1[tmpc].mClicked(null);
    }

    private void txtExpression_keyReleased(KeyEvent e) {
        //System.out.println("aaaaaaaaaaaaaaa::" + e.getKeyCode());
        if( e.getKeyCode() == 16 || e.getKeyCode() == 17 || e.getKeyCode() == 18 || e.getKeyCode() == 37 || 
            e.getKeyCode() == 38 || e.getKeyCode() == 39 || e.getKeyCode() == 40 || e.getKeyCode() == 33 ||
            e.getKeyCode() == 34 || e.getKeyCode() == 35 || e.getKeyCode() == 36 || /*e.getKeyCode() == 127 || */
            e.getKeyCode() == 155 || e.getKeyCode() == 10 )
            return;
        int ds = txtExpression.getCaretPosition();
        txtExpression.setText(txtExpression.getText().replaceAll("\r",""));
        txtExpression.setCaretPosition(ds);
        Attribute.checkSyntax2(txtExpression,false,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2);
    }
    
    private int save(){
        try{
            if( Attribute.checkSyntax2(txtExpression,true,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2).check    ){
                myPane.setText(txtExpression.getText());
                JDBCQuery query=new JDBCQuery(con);
                if(typeOfEditor == 0)
                    query.update("update IISC_Domain set Dom_reg_exp_str = '" + txtExpression.getText().trim().replaceAll("'","''") + "' where Dom_id=" + id_comp + " and pr_id = " + treeID); 
                else if(typeOfEditor == 1)
                    query.update("update IISC_Attribute set Att_expr='" + txtExpression.getText().trim().replaceAll("'","''") + "' where Att_id=" + id_comp + " and pr_id = " + treeID); 
                else if(typeOfEditor == 2)
                    query.update("update IISC_COMPONENT_TYPE_OBJECT_TYPE SET Tob_check = '" + txtExpression.getText().trim().replaceAll("'","''") + "' WHERE PR_id = " + treeID + " AND Tf_id = " + ID2 + " AND Tob_id = " + id_comp); 
            }
            else{
                JOptionPane.showMessageDialog(null, "<html><center>Check expression!", "User defined domains", JOptionPane.ERROR_MESSAGE);
                return -1;
            }  
            JOptionPane.showMessageDialog(null, "<html><center>Expression Successfuly Saved!", "User defined domains", JOptionPane.INFORMATION_MESSAGE);
            tmp1[0].mClicked(null);
            saveOpt = txtExpression.getText();
            return 1;
        }
        catch(Exception ex){
            System.out.println("GRESKA:" + ex);
            return -1;
        }
    }

    private void pSave_mouseClicked(MouseEvent e) {
        save();
    }

    private void this_windowClosing(WindowEvent e) {
        exit();
    }
    
    private void exit(){
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        if(saveOpt.equals(txtExpression.getText())){
            this.dispose();
            return;
        }
            
        int what = JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "User defined expression", JOptionPane.YES_NO_CANCEL_OPTION);
        //System.out.println("Izlazak:"  + what);
        if( what == 0){
            if(Attribute.checkSyntax2(txtExpression,true,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2).check){
                if( save() == 1 )
                    this.dispose();
            }            
            else{
                JOptionPane.showMessageDialog(null, "<html><center>Check expression!", "User defined expression", JOptionPane.ERROR_MESSAGE);                        
                this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        }
        else if( what == 2 ){
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);            
            tmp1[0].mClicked(null);
        }
        else
            this.dispose();
            
    }

    private void jMenuItem26_actionPerformed(ActionEvent e) {
        pNot_mouseClicked(null);
    }

    private void jMenuItem9_actionPerformed(ActionEvent e) {
        pAnd_mouseClicked(null);
    }

    private void jMenuItem12_actionPerformed(ActionEvent e) {
        pOr_mouseClicked(null);
    }

    private void jMenuItem13_actionPerformed(ActionEvent e) {
        pXor_mouseClicked(null);
    }

    private void jMenuItem14_actionPerformed(ActionEvent e) {
        pImply_mouseClicked(null);
    }

    private void jMenuItem15_actionPerformed(ActionEvent e) {
        pEqui_mouseClicked(null);
    }

    private void jMenuItem24_actionPerformed(ActionEvent e) {
        pIn_mouseClicked(null);
    }

    private void jMenuItem25_actionPerformed(ActionEvent e) {
        pLike_mouseClicked(null);
    }

    private void jMenuItem10_actionPerformed(ActionEvent e) {
        pEq_mouseClicked(null);
    }

    private void jMenuItem16_actionPerformed(ActionEvent e) {
        pNeq_mouseClicked(null);
    }

    private void jMenuItem17_actionPerformed(ActionEvent e) {
        pLt_mouseClicked(null);
    }

    private void jMenuItem18_actionPerformed(ActionEvent e) {
        pLe_mouseClicked(null);
    }


    private void jMenuItem11_actionPerformed(ActionEvent e) {
        pAdd_mouseClicked(null);
    }

    private void jMenuItem21_actionPerformed(ActionEvent e) {
        pSub_mouseClicked(null);
    }

    private void jMenuItem22_actionPerformed(ActionEvent e) {
        pMulti_mouseClicked(null);
    }

    private void jMenuItem23_actionPerformed(ActionEvent e) {
        pDiv_mouseClicked(null);
    }

    private void jMenuItem20_actionPerformed(ActionEvent e) {
        pGt_mouseClicked(null);   
    }

    private void jMenuItem19_actionPerformed(ActionEvent e) {
        pGe_mouseClicked(null);   
    }

    private void jMenuItem27_actionPerformed(ActionEvent e) {
        pConcat_mouseClicked(null);
    }

    private void menuFileExit_actionPerformed(ActionEvent e) {
        exit();
    }


    private void jMenuItem28_actionPerformed(ActionEvent e) {
        pExp_mouseClicked(null);  
    }

    private void jMenuItem29_actionPerformed(ActionEvent e) {
        pFun_mouseClicked(null);     
    }

    private void treeView_mouseClicked(MouseEvent e) {
        DefaultMutableTreeNodeEditor selNode = (DefaultMutableTreeNodeEditor)treeView.getLastSelectedPathComponent();  
        if(selNode != null)
            tmp1[selNode.pozition].mClicked(null);
    }


    private void txtExpression_keyPressed(KeyEvent e) {
        //System.out.println("xxx:" + e.getKeyCode());
        //jScrollPane1.setVisible(false);
        if( e.getKeyCode() == 113){
        
            JComponent a = (JComponent)txtExpression.getParent();

            int x = (int)txtExpression.getCaret().getMagicCaretPosition().getX() + 2 - (int)txtExpression.getVisibleRect().getX();
            int y = (int)txtExpression.getCaret().getMagicCaretPosition().getY() - (int)txtExpression.getVisibleRect().getY();   
            
            if(x + 450 > txtExpression.getWidth())
                x = txtExpression.getWidth() - 450 - 2;
            
            while(a.getParent().getClass().toString().compareTo("class iisc.CheckConstraintExpressionEditor") != 0){
                x += a.getX();
                y += a.getY();
                a = (JComponent)a.getParent();
            }             
            int caretPo = txtExpression.getCaretPosition();
            txtExpression.setText(txtExpression.getText().replaceAll("\r",""));
            txtExpression.setCaretPosition(caretPo);            
            ddList.setData(null);
            ddList.setBounds( x, y, 450, 100 );
            ddList.setVisible(true);
            ddList.requestFocus();
        }    
    }

    private void txtExpression_focusGained(FocusEvent e) {
        Attribute.checkSyntax2(txtExpression,false,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2);
    }

    private void txtExpression_mouseClicked(MouseEvent e) {
        if(ddList != null)
            ddList.setVisible(false);
    }


    private void jPanel1_mouseClicked(MouseEvent e) {
        ddListEditor.setVisible(false);
    }


    private void pHelp_mouseClicked(MouseEvent e) {

    }

    private void setBorder(MouseEvent e) {
    }

    private void pOpen_mouseClicked(MouseEvent e) {
        CheckConstraintExpressionOpen a = new CheckConstraintExpressionOpen( pmy,"aaa",true,this);
        a.setVisible(true);    
    }

    /*private void errorList_valueChanged(ListSelectionEvent e) {
        if(errorList.getSelectedIndex() != -1){
            String[] tmpS = ((JLabel)errorList.getSelectedValue()).getText().split("\\[");
            if(tmpS.length > 0){
                tmpS = tmpS[1].split(",");
                if(tmpS.length > 0)
                    tmpS = tmpS[1].split("=");
                if(tmpS.length > 0)
                    tmpS = tmpS[1].split(":");
                if(tmpS.length > 1){
                    txtExpression.setSelectionStart(Integer.parseInt(tmpS[0]));
                    txtExpression.setSelectionEnd(Integer.parseInt(tmpS[1]));
                }
            }
        }
    }*/


    private void pMaximize_mouseClicked(MouseEvent e) {
    
        if( !minimaze ){
            Dimension scrSize = getToolkit().getScreenSize();
            scrSize.setSize(scrSize.getWidth(),scrSize.getHeight() - 100);
            this.setLocation(0,0);
            this.setSize(scrSize);   
            minimaze = true;
        }
        else{
            minimaze = false;
            this.setLocation(100,100);
            this.setSize(new Dimension(950,750));
        }

    }

    private void pNewExpr_mouseClicked(MouseEvent e) {

        if( !pNewExpr.isEnabled() )
            return;
        tmp1[this.clicked].jTextArea1.setText(tmp1[this.clicked].jTextArea1.getText() + " ");
        ddListEditor.setTxtArea(tmp1[this.clicked]);
        ddListEditor.setData(null);
        if(cmbAddExpr.getItemCount() > 0)
            cmbAddExpr.removeAllItems();
            
        cmbAddExpr.revalidate();
        cmbAddExpr.addItem("");
        for(int i=0; i <ddListEditor.getModelData().getSize(); i++)
            cmbAddExpr.addItem(ddListEditor.getModelData().getElementAt(i));
         
        SearchTable ptype=new SearchTable((Frame)getParent(),"Select Item",true,con,treeID,ID2,String.valueOf(id_comp),typeOfEditor);
        Settings.Center(ptype);
        ptype.type="Regular Expression Editor";
        ptype.owner=this;
        ptype.setVisible(true);
        /*if( this.clicked != -1 && aaa[this.clicked].type.compareTo("S") == 0){
            aaa[this.clicked].value += " " + cmbAddExpr.getSelectedItem().toString().split(":")[0];
            tmp1[this.clicked].jTextArea1.setText( tmp1[this.clicked].jTextArea1.getText() + " " + cmbAddExpr.getSelectedItem().toString().split(":")[0]);
            txtExpression.setText(print(1).trim());
            Attribute.checkSyntax2(txtExpression,false,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2);
        } */        
    }


    private void this_componentResized(ComponentEvent e) {
        lPanel.setSize(this.getWidth(),lPanel.getHeight());
        if( 870 + 60 < this.getWidth()){
            //System.out.println(this.getWidth() - 50 + "    " + (int)pHelp.getLocation().getY());
            pHelp.setLocation(this.getWidth() - 60,(int)pHelp.getLocation().getY());
            pMaximize.setLocation(this.getWidth() - 35,(int)pMaximize.getLocation().getY());                
        }
        
    }




    private void cmbAddExpr_actionPerformed(ActionEvent e) {
        if(e.getModifiers() == 16 && cmbAddExpr.getSelectedIndex() >= 0 && !cmbAddExpr.getSelectedItem().toString().equals("")){
            //tmp1[this.clicked].jTextArea1.setText(tmp1[this.clicked].jTextArea1.getText() + cmbAddExpr.getSelectedItem().toString() + " "  );

            int tmpCaret = tmp1[this.clicked].jTextArea1.getCaretPosition();
            tmp1[this.clicked].jTextArea1.setText(tmp1[this.clicked].jTextArea1.getText().replaceAll("\r",""));
            tmp1[this.clicked].jTextArea1.setCaretPosition(tmpCaret);            

            int g = tmp1[this.clicked].jTextArea1.getCaretPosition() - 1;
            String tmpEx = "";
                
            while( g >= 0 && ( (tmp1[this.clicked].jTextArea1.getText().charAt(g) >= '0' && tmp1[this.clicked].jTextArea1.getText().charAt(g) <= '9') || 
                    ( tmp1[this.clicked].jTextArea1.getText().charAt(g) >= 'A' && tmp1[this.clicked].jTextArea1.getText().charAt(g) <= 'Z' ) ||
                     (tmp1[this.clicked].jTextArea1.getText().charAt(g) >= 'a' && tmp1[this.clicked].jTextArea1.getText().charAt(g) <= 'z') || 
                tmp1[this.clicked].jTextArea1.getText().charAt(g) == '_' || tmp1[this.clicked].jTextArea1.getText().charAt(g) == '.' )){
                tmpEx = tmp1[this.clicked].jTextArea1.getText().charAt(g) + tmpEx;
                g--;
            }            

            //String tmpEx = dropDownString();
            String tmps = "";
            if(cmbAddExpr.getSelectedItem().toString().split("\\|")[0].length() >= tmpEx.length()){
                String ggg[] = cmbAddExpr.getSelectedItem().toString().split("\\|");
                int posS = 0;
                if(ggg.length == 3)
                    posS = 1;                 
                tmps = cmbAddExpr.getSelectedItem().toString().split("\\|")[posS].substring(tmpEx.length()).trim();
            }
            
            tmp1[this.clicked].jTextArea1.setText( 
                 tmp1[this.clicked].jTextArea1.getText().substring(0,tmpCaret) + tmps +  tmp1[this.clicked].jTextArea1.getText().substring(tmpCaret ,  tmp1[this.clicked].jTextArea1.getText().length() ));
            tmp1[this.clicked].my.aaa[this.clicked].value = tmp1[this.clicked].jTextArea1.getText();
            tmp1[this.clicked].my.txtExpression.setText(tmp1[this.clicked].my.print(1).trim());
            Attribute.checkSyntax2(txtExpression,false,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2);                             

             tmp1[this.clicked].jTextArea1.requestFocus();
             tmp1[this.clicked].jTextArea1.setText(tmp1[this.clicked].jTextArea1.getText().replaceAll("\r",""));               
             tmp1[this.clicked].jTextArea1.setCaretPosition(tmpCaret + tmps.length());                  
        }
    }

    private void jmiParenthesis_actionPerformed(ActionEvent e) {
        pBlock_mouseClicked(null);
    }

    private void pTuple_mouseClicked(MouseEvent e) {
        if(!pTuple.isEnabled())
            return;         
        int tmpc = addNode("TUPLE","B",0,13,true,"");    
        tmp1[tmpc].mClicked(null);       
    }

    private void jmiTuple_actionPerformed(ActionEvent e) {
        pTuple_mouseClicked(null);        
    }

    private void cmbAddExpr_mouseEntered(MouseEvent e) {
        if(aaa[this.clicked].type.equals("S")){
            ddListEditor.setTxtArea(tmp1[this.clicked]);
            ddListEditor.setData(null);    
            if(cmbAddExpr.getItemCount() > 0)
            cmbAddExpr.removeAllItems();
            cmbAddExpr.revalidate();
            cmbAddExpr.addItem("");
            for(int i=0; i <ddListEditor.getModelData().getSize(); i++)
                cmbAddExpr.addItem(ddListEditor.getModelData().getElementAt(i));            
        }    
    }

    private void jsWidth_mouseReleased(MouseEvent e) {
        glWidth2 = jsWidth.getValue();
        design(false);
    }
}


