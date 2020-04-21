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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import java.util.Enumeration;
import javax.swing.BorderFactory;
import javax.swing.DebugGraphics;
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
import javax.swing.plaf.basic.BasicComboPopup;
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

public class FunctionEditor extends JDialog {

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
    public boolean saveOpt = false;
    
    public int clicked = 0;
    public int entered = 0;
    public int pressed = 0;
    public int released = 0;
    public int tmppressed = -1;
    public int movePosition = 0;
    public String whatMove = "";
    public FunctionPanel tmp1[] = new FunctionPanel[15];
    public node[][] undon = new node[1000][];
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

    private ImageIcon blockIcon = new ImageIcon(IISFrameMain.class.getResource("icons/block2.gif"));
    private ImageIcon forIcon = new ImageIcon(IISFrameMain.class.getResource("icons/for.gif"));
    private ImageIcon whileIcon = new ImageIcon(IISFrameMain.class.getResource("icons/while.gif"));
    private ImageIcon doWhileIcon = new ImageIcon(IISFrameMain.class.getResource("icons/dowhile.gif"));
    private ImageIcon ifIcon = new ImageIcon(IISFrameMain.class.getResource("icons/if.gif"));
    private ImageIcon elseIcon = new ImageIcon(expPanel.class.getResource("icons/else.gif"));      
    private ImageIcon elseifIcon = new ImageIcon(expPanel.class.getResource("icons/elseif.gif"));      
    private ImageIcon excIcon = new ImageIcon(IISFrameMain.class.getResource("icons/exception2.gif"));
    private ImageIcon decBlockIcon = new ImageIcon(IISFrameMain.class.getResource("icons/declaration.gif"));
    private ImageIcon decXIcon = new ImageIcon(IISFrameMain.class.getResource("icons/declaration2.gif"));
    private ImageIcon commIcon = new ImageIcon(IISFrameMain.class.getResource("icons/comments.gif"));
    private ImageIcon tryIcon = new ImageIcon(IISFrameMain.class.getResource("icons/try.gif"));
    private ImageIcon catchIcon = new ImageIcon(IISFrameMain.class.getResource("icons/catch.gif"));
    private ImageIcon finallyIcon = new ImageIcon(IISFrameMain.class.getResource("icons/finally.gif"));
    private ImageIcon fungIcon = new ImageIcon(IISFrameMain.class.getResource("icons/fung.gif"));
    private ImageIcon argIcon = new ImageIcon(IISFrameMain.class.getResource("icons/arg.gif"));
    
    private ImageIcon help1 = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private ImageIcon newExpr = new ImageIcon(IISFrameMain.class.getResource("icons/newproj2.gif"));
    private ImageIcon imageOpen = new ImageIcon(IISFrameMain.class.getResource("icons/openfile.gif"));
    private ImageIcon imageOpenExp = new ImageIcon(IISFrameMain.class.getResource("icons/openexp.gif"));
    private ImageIcon leaf = new ImageIcon(IISFrameMain.class.getResource("icons/leaf.gif"));
    private ImageIcon imageClose = new ImageIcon(IISFrameMain.class.getResource("icons/closefile.gif"));
    private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help.gif")); 
    public  ImageIcon imageSave = new ImageIcon(IISFrameMain.class.getResource("icons/save2.gif"));
    private ImageIcon erase = new ImageIcon(IISFrameMain.class.getResource("icons/erase.gif"));  
    private ImageIcon block = new ImageIcon(IISFrameMain.class.getResource("icons/block.gif"));  
    private ImageIcon exp = new ImageIcon(IISFrameMain.class.getResource("icons/exp.gif"));
    private ImageIcon undoi = new ImageIcon(IISFrameMain.class.getResource("icons/undo.gif"));
    private ImageIcon redoi = new ImageIcon(IISFrameMain.class.getResource("icons/redo.gif"));
    private ImageIcon set = new ImageIcon(IISFrameMain.class.getResource("icons/set.gif"));
    private ImageIcon expr = new ImageIcon(IISFrameMain.class.getResource("icons/expr.gif")); 
    private ImageIcon functions2 = new ImageIcon(IISFrameMain.class.getResource("icons/functions2.gif")); 
    private ImageIcon cut = new ImageIcon(IISFrameMain.class.getResource("icons/cut.gif"));  
    private ImageIcon copy = new ImageIcon(IISFrameMain.class.getResource("icons/copy.gif"));  
    private ImageIcon paste = new ImageIcon(IISFrameMain.class.getResource("icons/paste.gif"));  
    private ImageIcon check = new ImageIcon(IISFrameMain.class.getResource("icons/check3.gif")); 
    private ImageIcon zipexp = new ImageIcon(IISFrameMain.class.getResource("icons/zipexp.gif")); 
    private ImageIcon maximize = new ImageIcon(IISFrameMain.class.getResource("icons/maximize.gif")); 
    private ImageIcon smt = new ImageIcon(IISFrameMain.class.getResource("icons/smt.gif")); 

    private JPanel jPanel3 = new JPanel();
    //private BorderLayout borderLayout2 = new BorderLayout();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JPanel lPanel = new JPanel();
    private JMenu jMenu1 = new JMenu();
    private JMenuItem jMenuItem1 = new JMenuItem();
    private JMenuItem jMenuItem2 = new JMenuItem();
    private JMenuItem jMenuItem3 = new JMenuItem();
    private JMenuItem jMenuItem4 = new JMenuItem();
    private JMenuItem jMenuItem5 = new JMenuItem();
    private JMenuItem jMenuItem6 = new JMenuItem();
    private JMenuItem jMenuItem7 = new JMenuItem();
    private JLabel pBlock = new JLabel();
    private JLabel pFor = new JLabel();
    private JLabel pWhile = new JLabel();
    private JLabel pIf = new JLabel();
    private JLabel pTry = new JLabel();
    private JLabel pDeclarationB = new JLabel();
    private JLabel pComment = new JLabel();
    private JLabel sep1 = new JLabel();
    private JLabel sep2 = new JLabel();
    private JLabel sep3 = new JLabel();
    private JLabel pDeclaration = new JLabel();
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
    private JMenu jMenu2 = new JMenu();
    private JMenuItem jMenuItem8 = new JMenuItem();
    public DefaultListModel model2 = new DefaultListModel();
    private JSplitPane jSplitPane2 = new JSplitPane();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JLabel sep4 = new JLabel();
    private JLabel sep5 = new JLabel();
    private JLabel sep6 = new JLabel();
    private JLabel pSepA1 = new JLabel();
    private JMenu jMenu3 = new JMenu();
    private JLabel jLabel2 = new JLabel();
    private JLabel pSepA2 = new JLabel();
    public JLabel status = new JLabel();
    public String ID2 = null;
    public String treeID = null;
    private JPanel statusBar = new JPanel();
    private JLabel sep7 = new JLabel();
    private JLabel sep8 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel pHelp = new JLabel();
    public JLabel status2 = new JLabel();
    private Frame pmy = null;
    private JLabel pMaximize = new JLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JLabel pCatch = new JLabel();
    private JLabel pFinally = new JLabel();
    private JLabel pElse = new JLabel();
    private JLabel pElseIf = new JLabel();
    private JLabel pLocalFunction = new JLabel();
    private JLabel pLocalFuncArgs = new JLabel();
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JScrollPane SPError = new JScrollPane();
    private JList errorList = new JList();
    private JLabel pDoWhile = new JLabel();
    private JMenuItem jmiExecutionB = new JMenuItem();
    private JMenuItem jmiFor = new JMenuItem();
    private JMenuItem jmiWhileDo = new JMenuItem();
    private JMenuItem jmiDoWhile = new JMenuItem();
    private JMenuItem jmiIf = new JMenuItem();
    private JMenuItem jmiElse = new JMenuItem();
    private JMenuItem jmiElseIf = new JMenuItem();
    private JMenuItem jmiDeclaration = new JMenuItem();
    private JMenuItem jmitry = new JMenuItem();
    private JMenuItem jmiCatch = new JMenuItem();
    private JMenuItem jmiFinally = new JMenuItem();
    private JMenuItem jmiLocalFun = new JMenuItem();
    private JMenuItem jmiExpr = new JMenuItem();
    private JMenu jMenu4 = new JMenu();
    private JMenuItem jmiDeclarationE = new JMenuItem();
    private JMenuItem jmiArgs = new JMenuItem();
    private JMenuItem jmiComments = new JMenuItem();
    private JMenuItem jmiFun = new JMenuItem();
    public  JComboBox cmbExp = new JComboBox();
    private JSlider jsWidth = new JSlider();

    //public int listClicked = -1;

    public FunctionEditor() {
        this(null, "", false);
    }

    public FunctionEditor(IISFrameMain parent, String title, boolean modal) {
        super((Frame)parent, title, modal);
        try {
            pmy = parent;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            
        }
    }
    
    public FunctionEditor(IISFrameMain parent, String title, boolean modal, Connection connect, /*PTree tr,*/int idd,int typeE,JTextPane par,String nodeName,String id2,String IDTREE) {
        super((Frame)parent, title, modal);
        try {
            myPane = par;
            //txtExpression.setText(myPane.getText().trim());
            nodeNameSyntax = nodeName;
            //nodeIDSyntax = nodeId;
            //startExpr = myPane.getText();
            typeOfEditor = typeE;
            id_comp = idd;
            //tree = tr;
            con = connect;
            ID2 = id2;
            treeID = IDTREE;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    private void jbInit() throws Exception {
        //ddList = new DropDownList();
        //this.add(ddList);
        //ddList.setVisible(false);
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
        //this.setSize(new Dimension(717, 651));
        //a.setBounds(new Rectangle(100, 100, 350, 175));
        //this.getContentPane().add(a, BorderLayout.CENTER);
        jSplitPane2.setDividerLocation(150);
        jSplitPane2.setLastDividerLocation(250);
        jSplitPane2.setOneTouchExpandable(true);
        jSplitPane2.setDividerSize(10);
        jScrollPane3.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        sep4.setBounds(new Rectangle(200, 5, 4, 20));
        sep4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        sep5.setBounds(new Rectangle(285, 5, 4, 20));
        sep5.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        sep6.setBounds(new Rectangle(370, 5, 4, 22));
        sep6.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pSepA1.setBounds(new Rectangle(425, 5, 4, 22));
        pSepA1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jMenu3.setText("Blocks & Statements");
        jLabel2.setText("Action:");
        jLabel2.setBounds(new Rectangle(40, 0, 45, 20));
        pSepA2.setBounds(new Rectangle(480, 5, 4, 20));
        pSepA2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        status.setBounds(new Rectangle(80, 0, 360, 20));

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
        jLabel6.setBounds(new Rectangle(2, 145, 25, 4));
        jLabel6.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pHelp.setBounds(new Rectangle(915, 5, 25, 25));
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
        status2.setBounds(new Rectangle(450, 0, 290, 20));
        pMaximize.setBounds(new Rectangle(940, 5, 20, 20));
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
        jPanel3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jPanel3.setLayout(null);
        //txtExpression.setWrapStyleWord(true);
        //txtExpression.setLineWrap(true);
        lPanel.setBounds(new Rectangle(0, 0, 1115, 30));
        lPanel.setLayout(null);

        jMenu1.setText("Edit");
        jMenuItem1.setText("Undo");
        jMenuItem1.setIcon(undoi);
        jMenuItem1.setHorizontalAlignment(SwingConstants.LEFT);
        jMenuItem1.setMargin(new Insets(0, 0, 0, 0));
        jMenuItem2.setText("Redo");
        jMenuItem2.setIcon(redoi);
        jMenuItem2.setHorizontalAlignment(SwingConstants.LEFT);
        jMenuItem2.setIconTextGap(0);
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
        jMenuItem7.setHorizontalAlignment(SwingConstants.LEFT);
        jMenuItem7.setIcon(imageSave);
        jMenuItem7.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem7_actionPerformed(e);
                    }
                });
        pBlock.setBounds(new Rectangle(5, 5, 22, 22));
        pBlock.setHorizontalAlignment(SwingConstants.CENTER);
        pBlock.setHorizontalTextPosition(SwingConstants.CENTER);
        pBlock.setIconTextGap(1);
        pBlock.setToolTipText("Execution Block");
        pBlock.setIcon(blockIcon);
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

        pFor.setBounds(new Rectangle(35, 5, 22, 22));
        pFor.setIconTextGap(1);
        pFor.setHorizontalAlignment(SwingConstants.CENTER);
        pFor.setHorizontalTextPosition(SwingConstants.CENTER);
        pFor.setToolTipText("FOR loop");
        pFor.setIcon(forIcon);
        pFor.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pFor.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pFor.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pFor.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pFor.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pFor_mouseClicked(e);
                    }
                });
        pWhile.setBounds(new Rectangle(60, 5, 22, 22));
        pWhile.setHorizontalAlignment(SwingConstants.CENTER);
        pWhile.setHorizontalTextPosition(SwingConstants.CENTER);
        pWhile.setIconTextGap(1);
        pWhile.setToolTipText("WHILE DO loop");
        pWhile.setIcon(whileIcon);
        pWhile.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pWhile.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pWhile.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pWhile.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pWhile.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pWhile_mouseClicked(e);
                    }
                });
        pIf.setBounds(new Rectangle(290, 5, 22, 22));
        pIf.setHorizontalAlignment(SwingConstants.CENTER);
        pIf.setHorizontalTextPosition(SwingConstants.CENTER);
        pIf.setIconTextGap(1);
        pIf.setToolTipText("IF block");
        pIf.setIcon(ifIcon);
        pIf.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pIf.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pIf.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pIf.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pIf.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pIf_mouseClicked(e);
                    }
                });
        pTry.setBounds(new Rectangle(210, 5, 22, 22));
        pTry.setHorizontalAlignment(SwingConstants.CENTER);
        pTry.setHorizontalTextPosition(SwingConstants.CENTER);
        pTry.setIconTextGap(1);
        pTry.setToolTipText("TRY block");
        pTry.setIcon(tryIcon);
        pTry.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pTry.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pTry.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pTry.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pTry.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pTry_mouseClicked(e);
                    }
                });
        pDeclarationB.setBounds(new Rectangle(150, 5, 22, 22));
        pDeclarationB.setHorizontalAlignment(SwingConstants.CENTER);
        pDeclarationB.setHorizontalTextPosition(SwingConstants.CENTER);
        pDeclarationB.setIconTextGap(1);
        pDeclarationB.setToolTipText("Declaration Block");
        pDeclarationB.setIcon(decBlockIcon);
        pDeclarationB.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pDeclarationB.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pDeclarationB.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pDeclarationB.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pDeclarationB.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pDeclarationB_mouseClicked(e);
                    }
                });
        pComment.setBounds(new Rectangle(115, 5, 22, 22));
        pComment.setHorizontalAlignment(SwingConstants.CENTER);
        pComment.setHorizontalTextPosition(SwingConstants.CENTER);
        pComment.setIconTextGap(1);
        pComment.setToolTipText("Comment");
        pComment.setIcon(commIcon);
        pComment.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pComment.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pComment.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pComment.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pComment.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pComment_mouseClicked(e);
                    }
                });
        sep1.setBounds(new Rectangle(30, 5, 4, 22));
        sep1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        sep2.setBounds(new Rectangle(110, 5, 4, 22));
        sep2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        sep3.setBounds(new Rectangle(140, 5, 4, 22));
        sep3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pDeclaration.setBounds(new Rectangle(175, 5, 22, 22));
        pDeclaration.setHorizontalAlignment(SwingConstants.CENTER);
        pDeclaration.setHorizontalTextPosition(SwingConstants.CENTER);
        pDeclaration.setIconTextGap(1);
        pDeclaration.setToolTipText("Declaration");
        pDeclaration.setIcon(decXIcon);
        pDeclaration.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pDeclaration.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pDeclaration.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pDeclaration.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pDeclaration.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pDeclaration_mouseClicked(e);
                    }
                });
        jPanel2.setLayout(null);


        //lPanel.add(statusBar, null);
        lPanel.add(cmbExp, null);
        lPanel.add(pDoWhile, null);
        lPanel.add(pLocalFuncArgs, null);
        lPanel.add(pLocalFunction, null);
        lPanel.add(pElseIf, null);
        lPanel.add(pElse, null);
        lPanel.add(pFinally, null);
        lPanel.add(pCatch, null);
        lPanel.add(pSepA2, null);
        lPanel.add(pSepA1, null);
        lPanel.add(sep6, null);
        lPanel.add(sep5, null);
        lPanel.add(sep4, null);
        lPanel.add(sep3, null);
        lPanel.add(sep2, null);
        lPanel.add(sep1, null);
        lPanel.add(pComment, null);
        lPanel.add(pDeclarationB, null);
        lPanel.add(pTry, null);
        lPanel.add(pIf, null);
        lPanel.add(pWhile, null);
        lPanel.add(pFor, null);
        lPanel.add(pBlock, null);
        lPanel.add(pDeclaration, null);
        lPanel.add(pExp, null);
        lPanel.add(pFun, null);
        lPanel.add(pHelp, null);
        lPanel.add(pMaximize, null);
        jPanel3.setPreferredSize(new Dimension(150, 32));
        jPanel2.setPreferredSize(new Dimension(28, 150));

        pSave.setBounds(new Rectangle(2, 5, 22, 22));
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
        pDel.setBounds(new Rectangle(2, 35, 22, 22));
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
        pUndo.setBounds(new Rectangle(2, 65, 22, 22));
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
        pRedu.setBounds(new Rectangle(2, 90, 22, 22));
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
        pCheck.setBounds(new Rectangle(2, 120, 22, 22));
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
        jLabel1.setBounds(new Rectangle(2, 60, 22, 4));
        jLabel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jLabel4.setBounds(new Rectangle(2, 30, 22, 4));
        jLabel4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jLabel5.setBounds(new Rectangle(2, 115, 22, 4));
        jLabel5.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pExp.setBounds(new Rectangle(430, 5, 22, 22));
        pExp.setHorizontalAlignment(SwingConstants.CENTER);
        pExp.setHorizontalTextPosition(SwingConstants.CENTER);
        pExp.setIconTextGap(1);
        pExp.setIcon(smt);
        pExp.setToolTipText("Simple Statement");
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
        pFun.setBounds(new Rectangle(455, 5, 22, 22));
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
        jMenu2.setText("Help");
        jMenuItem8.setText("Operation");
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
        jScrollPane3.getViewport().add(treeView, null);
        jSplitPane2.add(jScrollPane3, JSplitPane.TOP);
        jScrollPane1.getViewport().add(jPanel1, null);
        jSplitPane1.add(jScrollPane1, JSplitPane.TOP);
        SPError.getViewport().add(errorList, null);
        jSplitPane1.add(SPError, JSplitPane.BOTTOM);
        jSplitPane2.add(jSplitPane1, JSplitPane.BOTTOM);
        jPanel2.add(jLabel6, null);
        jPanel2.add(jLabel5, null);
        jPanel2.add(jLabel4, null);


        jPanel2.add(jLabel1, null);
        jPanel2.add(pCheck, null);
        jPanel2.add(pRedu, null);
        jPanel2.add(pUndo, null);
        jPanel2.add(pDel, null);
        jPanel2.add(pSave, null);
        jPanel2.add(jsWidth, null);
        this.getContentPane().add(jSplitPane2, BorderLayout.CENTER);

        jPanel3.setBounds(new Rectangle(0, 0, 850, 32));
        this.setBackground(new Color(33, 100, 163));

        this.setTitle("Function Specification Editor");
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
        jMenu2.add(jMenuItem8);
        menuBar.add(jMenu2);
        jMenu3.add(jmiExecutionB);
        jMenu3.addSeparator();
        jMenu3.add(jmiFor);
        jMenu3.add(jmiWhileDo);
        jMenu3.add(jmiDoWhile);
        jMenu3.addSeparator();
        jMenu3.add(jmiIf);
        jMenu3.add(jmiElse);
        jMenu3.add(jmiElseIf);
        jMenu3.addSeparator();
        jMenu3.add(jmiDeclaration);
        jMenu3.addSeparator();
        jMenu3.add(jmitry);
        jMenu3.add(jmiCatch);
        jMenu3.add(jmiFinally);
        jMenu3.addSeparator();
        jMenu3.add(jmiFun);
        jMenu3.add(jmiLocalFun);
        jMenu3.add(jmiExpr);
        jMenu3.addSeparator();
        jMenu4.add(jmiDeclarationE);
        jMenu4.addSeparator();
        jMenu4.add(jmiArgs);
        jMenu4.addSeparator();
        jMenu4.add(jmiComments);
        jMenu3.add(jMenu4);
        jPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)treeView.getCellRenderer();
        renderer.setLeafIcon(leaf);
        renderer.setClosedIcon(exp);
        renderer.setOpenIcon(set);

        jsWidth.setBounds(new Rectangle(0, 155, 25, 170));
        jsWidth.setOrientation(JSlider.VERTICAL);
        jsWidth.setMinimum(250);
        jsWidth.setMaximum(1500);
        jsWidth.setValue(400);
        jsWidth.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        jsWidth_mouseReleased(e);
                    }
                });
        cmbExp.setBounds(new Rectangle(490, 5, 225, 20));
        cmbExp.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        cmbExp_mouseEntered(e);
                    }
                });
        cmbExp.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cmbExp_actionPerformed(e);
                    }
                });
        jmiFun.setText("Function");
        jmiFun.setHorizontalAlignment(SwingConstants.LEFT);
        jmiFun.setIcon(functions2);
        jmiFun.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiFun_actionPerformed(e);
                    }
                });
        jmiComments.setText("Comments");
        jmiComments.setHorizontalAlignment(SwingConstants.LEFT);
        jmiComments.setIcon(commIcon);
        jmiComments.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jjmiComments_actionPerformed(e);
                    }
                });
        jMenu4.setText("Subelements");
        jmiDeclarationE.setText("Declaration Element");
        jmiDeclarationE.setIcon(decXIcon);
        jmiDeclarationE.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiDeclarationE_actionPerformed(e);
                    }
                });
        jmiArgs.setText("Local Function Agrument");
        jmiArgs.setHorizontalAlignment(SwingConstants.LEFT);
        jmiArgs.setIcon(argIcon);
        jmiArgs.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiArgs_actionPerformed(e);
                    }
                });
        jmiExecutionB.setText("Execution Block");
        jmiExecutionB.setIcon(blockIcon);
        jmiExecutionB.setHorizontalAlignment(SwingConstants.LEFT);
        jmiExecutionB.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiExecutionB_actionPerformed(e);
                    }
                });
        jmiFor.setText("For");
        jmiFor.setHorizontalAlignment(SwingConstants.LEFT);
        jmiFor.setIcon(forIcon);
        jmiFor.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiFor_actionPerformed(e);
                    }
                });
        jmiWhileDo.setText("While Do");
        jmiWhileDo.setHorizontalAlignment(SwingConstants.LEFT);
        jmiWhileDo.setIcon(whileIcon);
        jmiWhileDo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiWhileDo_actionPerformed(e);
                    }
                });
        jmiDoWhile.setText("Do While");
        jmiDoWhile.setHorizontalAlignment(SwingConstants.LEFT);
        jmiDoWhile.setIcon(doWhileIcon);
        jmiDoWhile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiDoWhile_actionPerformed(e);
                    }
                });
        jmiIf.setText("If");
        jmiIf.setHorizontalAlignment(SwingConstants.LEFT);
        jmiIf.setIcon(ifIcon);
        jmiIf.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiIf_actionPerformed(e);
                    }
                });
        jmiElse.setText("Else");
        jmiElse.setHorizontalAlignment(SwingConstants.LEFT);
        jmiElse.setIcon(elseIcon);
        jmiElse.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiElse_actionPerformed(e);
                    }
                });
        jmiElseIf.setText("Else If");
        jmiElseIf.setHorizontalAlignment(SwingConstants.LEFT);
        jmiElseIf.setIcon(elseifIcon);
        jmiElseIf.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiElseIf_actionPerformed(e);
                    }
                });
        jmiDeclaration.setText("Declaration");
        jmiDeclaration.setHorizontalAlignment(SwingConstants.LEFT);
        jmiDeclaration.setIcon(decBlockIcon);
        jmiDeclaration.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiDeclaration_actionPerformed(e);
                    }
                });
        jmitry.setText("Try");
        jmitry.setHorizontalAlignment(SwingConstants.LEFT);
        jmitry.setIcon(tryIcon);
        jmitry.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmitry_actionPerformed(e);
                    }
                });
        jmiCatch.setText("Catch");
        jmiCatch.setHorizontalAlignment(SwingConstants.LEFT);
        jmiCatch.setIcon(catchIcon);
        jmiCatch.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiCatch_actionPerformed(e);
                    }
                });
        jmiFinally.setText("Finally");
        jmiFinally.setHorizontalAlignment(SwingConstants.LEFT);
        jmiFinally.setIcon(finallyIcon);
        jmiFinally.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiFinally_actionPerformed(e);
                    }
                });
        jmiLocalFun.setText("Local Function");
        jmiLocalFun.setHorizontalAlignment(SwingConstants.LEFT);
        jmiLocalFun.setIcon(fungIcon);
        jmiLocalFun.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiLocalFun_actionPerformed(e);
                    }
                });
        jmiExpr.setText("Simple Expression");
        jmiExpr.setHorizontalAlignment(SwingConstants.LEFT);
        jmiExpr.setIcon(newExpr);
        jmiExpr.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jmiExpr_actionPerformed(e);
                    }
                });
        pDoWhile.setBounds(new Rectangle(85, 5, 22, 22));
        pDoWhile.setHorizontalAlignment(SwingConstants.CENTER);
        pDoWhile.setHorizontalTextPosition(SwingConstants.CENTER);
        pDoWhile.setIconTextGap(1);
        pDoWhile.setToolTipText("DO WHILE loop");
        pDoWhile.setIcon(doWhileIcon);
        pDoWhile.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pDoWhile.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pDoWhile.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pDoWhile.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pDoWhile.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pDoWhile_mouseClicked(e);
                    }
                });
        pLocalFuncArgs.setBounds(new Rectangle(400, 5, 22, 22));
        pLocalFuncArgs.setHorizontalAlignment(SwingConstants.CENTER);
        pLocalFuncArgs.setHorizontalTextPosition(SwingConstants.CENTER);
        pLocalFuncArgs.setIconTextGap(1);
        pLocalFuncArgs.setToolTipText("Local Function Argument");
        pLocalFuncArgs.setIcon(argIcon);
        pLocalFuncArgs.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pLocalFuncArgs.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pLocalFuncArgs.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pLocalFuncArgs.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pLocalFuncArgs.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pLocalFuncArgs_mouseClicked(e);
                    }
                });
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setOneTouchExpandable(true);
        SPError.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pLocalFunction.setBounds(new Rectangle(375, 5, 22, 22));
        pLocalFunction.setHorizontalAlignment(SwingConstants.CENTER);
        pLocalFunction.setHorizontalTextPosition(SwingConstants.CENTER);
        pLocalFunction.setIconTextGap(1);
        pLocalFunction.setToolTipText("Local Function");
        pLocalFunction.setIcon(fungIcon);
        pLocalFunction.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pLocalFunction.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pLocalFunction.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pLocalFunction.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pLocalFunction.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pLocalFunction_mouseClicked(e);
                    }
                });
        pElseIf.setBounds(new Rectangle(345, 5, 22, 22));
        pElseIf.setHorizontalAlignment(SwingConstants.CENTER);
        pElseIf.setHorizontalTextPosition(SwingConstants.CENTER);
        pElseIf.setIconTextGap(1);
        pElseIf.setToolTipText("ELSE IF Block");
        pElseIf.setIcon(elseifIcon);
        pElseIf.setDebugGraphicsOptions(DebugGraphics.BUFFERED_OPTION);
        pElseIf.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pElseIf.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pElseIf.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pElseIf.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pElseIf.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pElseIf_mouseClicked(e);
                    }
                });
        pElse.setBounds(new Rectangle(315, 5, 22, 22));
        pElse.setHorizontalAlignment(SwingConstants.CENTER);
        pElse.setHorizontalTextPosition(SwingConstants.CENTER);
        pElse.setIconTextGap(1);
        pElse.setToolTipText("ELSE Block");
        pElse.setIcon(elseIcon);
        pElse.setDebugGraphicsOptions(DebugGraphics.BUFFERED_OPTION);
        pElse.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pElse.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pElse.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pElse.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pElse.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pElse_mouseClicked(e);
                    }
                });
        pFinally.setBounds(new Rectangle(260, 5, 22, 22));
        pFinally.setHorizontalAlignment(SwingConstants.CENTER);
        pFinally.setHorizontalTextPosition(SwingConstants.CENTER);
        pFinally.setIconTextGap(1);
        pFinally.setToolTipText("FINALLY block");
        pFinally.setIcon(finallyIcon);
        pFinally.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pFinally.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pFinally.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pFinally.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pFinally.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pFinally_mouseClicked(e);
                    }
                });
        pCatch.setBounds(new Rectangle(235, 5, 22, 22));
        pCatch.setHorizontalAlignment(SwingConstants.CENTER);
        pCatch.setHorizontalTextPosition(SwingConstants.CENTER);
        pCatch.setIconTextGap(1);
        pCatch.setToolTipText("CATCH block");
        pCatch.setIcon(catchIcon);
        pCatch.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        pCatch.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    }

                    public void mouseExited(MouseEvent e) {
                        pCatch.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                        pCatch.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                    }

                    public void mouseReleased(MouseEvent e) {
                        pCatch.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mouseClicked(MouseEvent e) {
                        pCatch_mouseClicked(e);
                    }
                });
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jPanel1.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        jPanel1_mouseClicked(e);
                    }
                });
        errorList.setCellRenderer(new CheckConstraintErrorListRender());
        errorList.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        errorList_mouseClicked(e);
                    }
                });
        aaa = new node[2];
        
        JDBCQuery query = new JDBCQuery(con);
        
        ResultSet rs1;
        rs1=query.select("select count(*) from iisc_fun_body where pr_id = " + treeID + " and fun_id = " + id_comp );
        if(rs1.next()){
            aaa = new node[rs1.getInt(1)];
            
            if(aaa.length == 0){
                aaa = new node[2];
                aaa[0] = new node();
                aaa[1] = new node();
                
                aaa[0].type = "B";
                aaa[0].name = "FUNCTION";
                aaa[0].group = 0;

                aaa[1].type = "B";
                aaa[1].name = "*END*";
                aaa[1].group = -1;                  
            }
            else{
            rs1=query.select("select * from iisc_fun_body where pr_id = " + treeID + " and fun_id = " + id_comp + " order by fb_id ");
                int i=0;
                while(rs1.next()){
                    aaa[i] = new node();
                    aaa[i].name = rs1.getString("namee");
                    aaa[i].value = rs1.getString("valuee");
                    aaa[i].group = rs1.getInt("groupe");
                    aaa[i].type = rs1.getString("type");
                    i++;
                }
            }
        }
       
        
        //String output = "<block name=\"EXPRESSION\" group=\"0\" >\n</block>\n\n\n\n";

        //String output = Attribute.checkSyntax2(txtExpression,true,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2).xml;
        //xmlToNodes(null,output);
         ddListEditor = new DropDownList(con,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp));        
         ddListEditor.setVisible(false);        
        undoAction();        
        design(false);  
        tmp1[0].mClicked(null);
        saveOpt = true;
    }
 /*   
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
    }    */
    
    public void design(boolean print){

        int stack[] = new int[aaa.length];
        int stack2[] = new int[aaa.length];
        DefaultMutableTreeNodeEditor treeArray[] = new DefaultMutableTreeNodeEditor[aaa.length];
        
        int tt = 0;
       
        int stackno = 0;
        int stackno2 = 0;
        tmp1 = new FunctionPanel[aaa.length];
        lblIcon = new JLabel[aaa.length];
        tmp1[0] = new FunctionPanel(0,this);
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
                
                tmp1[i] = new FunctionPanel(i,this);
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
                
                if(caseGroup == -6){
                    tmp1[i].jComboBox1.addItem(new JLabel("Simple Expression"));
                    tmp1[i].jComboBox1.addItem(new JLabel("Assignment"));
                }
                else if(caseGroup == -5){
                    tmp1[i].jComboBox1.addItem(new JLabel("Variable"));
                    tmp1[i].jComboBox1.addItem(new JLabel("Type"));
                    tmp1[i].jComboBox1.addItem(new JLabel("Constant"));
                    tmp1[i].jComboBox1.addItem(new JLabel("Include Function"));
                    tmp1[i].jComboBox1.addItem(new JLabel("Exception"));
                    tmp1[i].jComboBox1.addItem(new JLabel("Cursor"));
                }
                else if(caseGroup == -1)
                    tmp1[i].jComboBox1.addItem(new JLabel("Begin"));
                else if(caseGroup == -11 || caseGroup == -2 || caseGroup == -3 || caseGroup == -66)
                    tmp1[i].jComboBox1.addItem(new JLabel("Condition"));
                else if(caseGroup == -111)
                    tmp1[i].jComboBox1.addItem(new JLabel("Step"));
                else if(caseGroup == -4)
                    tmp1[i].jComboBox1.addItem(new JLabel("Comments"));
                else if(caseGroup == -44)
                    tmp1[i].jComboBox1.addItem(new JLabel("Exception"));
                else if(caseGroup == -8)
                    tmp1[i].jComboBox1.addItem(new JLabel("Arg"));
                else if(caseGroup == -88)
                    tmp1[i].jComboBox1.addItem(new JLabel("Function Name"));                    
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
                    
                    tmp1[i] = new FunctionPanel(i,this);
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
                    tmp1[i].setVisible(true);
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
                    tmp1[i] = new FunctionPanel(i,this);
                    lblIcon[i] = tmp1[i].lblPosition;
                    //tmp1[stack[stackno - 1]].setSize(tmp1[stack[stackno - 1]].getWidth() ,tmp1[stack[stackno - 1]].getHeight() ) ;    
                    tmp1[i].jTextArea1.setText(tmp1[stack[stackno - 1]].jLabel1.getText().trim());
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
            //txtExpression.setText(print(1).trim());
            //Attribute.checkSyntax2(txtExpression,false,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2);
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
        else if(text.length() > 8 && text.substring(0,8).compareTo("FUNCTION") == 0)
            return text.split("\\(")[0].split("-")[1];
        else
            return text;
        
    }
    
     
    public void disableBlockButtons(){

        boolean enabled = false;
        boolean delEnabled = false;
        boolean enabled2 = enabled;
        
        
        if(     this.clicked == 0 ||
                aaa[this.clicked].group == 11 ||
                aaa[this.clicked].group == 22 ||
                aaa[this.clicked].group == 66 ||
                aaa[this.clicked].group == 4444 ||
                aaa[this.clicked].group == 33 ||
                aaa[this.clicked].group == 888
            ){
            enabled = true;
            delEnabled = false;
        }
        else if(    aaa[this.clicked].group == 0 || 
                    aaa[this.clicked].group == 4 || 
                    aaa[this.clicked].group == 444 || 
                    aaa[this.clicked].group == 333
                ){
            enabled = true;
            delEnabled = true;
        }
        else if(
            aaa[this.clicked].group == 1 || 
            aaa[this.clicked].group == 2 ||
            aaa[this.clicked].group == 5 ||
            aaa[this.clicked].group == 6 ||
            aaa[this.clicked].group == 44 ||
            aaa[this.clicked].group == 3 || 
            aaa[this.clicked].group == 8 || 
            aaa[this.clicked].group == 3333 || 
            aaa[this.clicked].group == -4
            ){
                enabled = false;
                delEnabled = true;
        }
            
        pBlock.setEnabled(enabled);
        jmiExecutionB.setEnabled(enabled);
        pFor.setEnabled(enabled);
        jmiFor.setEnabled(enabled);
        pWhile.setEnabled(enabled);
        jmiWhileDo.setEnabled(enabled);
        pDoWhile.setEnabled(enabled);
        jmiDoWhile.setEnabled(enabled);
        if(aaa[this.clicked].type.equals("S")){
            pComment.setEnabled(false);
            jmiComments.setEnabled(false);
        }
        else{
            pComment.setEnabled(true);
            jmiComments.setEnabled(true);
        }
        pDeclarationB.setEnabled(enabled);
        jmiDeclaration.setEnabled(enabled);
        if( aaa[this.clicked].group == 5 ){
            pDeclaration.setEnabled(true);
            jmiDeclarationE.setEnabled(true);
            pLocalFunction.setEnabled(true);
            jmiLocalFun.setEnabled(true);            
        }
        else{
            pDeclaration.setEnabled(false);        
            jmiDeclarationE.setEnabled(false);
            pLocalFunction.setEnabled(false);
            jmiLocalFun.setEnabled(false);
        }
        pTry.setEnabled(enabled);
        jmitry.setEnabled(enabled);
        if( aaa[this.clicked].group == 4 ){
            pCatch.setEnabled(true);
            jmiCatch.setEnabled(true);
        }
        else{
            pCatch.setEnabled(false);
            jmiCatch.setEnabled(false);
        }
        if( aaa[this.clicked].group == 44 ){
            pFinally.setEnabled(true);
            jmiFinally.setEnabled(true);
        }
        else{            
            pFinally.setEnabled(false);
            jmiFinally.setEnabled(false);
        }
        pIf.setEnabled(enabled);
        jmiIf.setEnabled(enabled);
        pElse.setEnabled(enabled);
        jmiElse.setEnabled(enabled);
        pElseIf.setEnabled(enabled);
        jmiElseIf.setEnabled(enabled);
        if( aaa[this.clicked].group == 88 ){
            pLocalFuncArgs.setEnabled(true);
            jmiArgs.setEnabled(true);
        }
        else{        
            pLocalFuncArgs.setEnabled(false);
            jmiArgs.setEnabled(false);
        }
        pExp.setEnabled(enabled);
        jmiExpr.setEnabled(enabled);
        pFun.setEnabled(enabled);        
        jmiFun.setEnabled(enabled);

        pDel.setEnabled(delEnabled);  
    
        pFun.setEnabled(false);
        jmiFun.setEnabled(false);
         
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
        saveOpt = false;
        return returni;
    }

    
    public int move(int position,boolean dsg,int pr,int re){
    
        //System.out.println("Pozition MOVE: " + position);
        
        if( 
            aaa[pr].group == -1 || 
            aaa[pr].group == -11 || 
            aaa[pr].group == -111 || 
            aaa[pr].group == 11 || 
            aaa[pr].group == -2 || 
            aaa[pr].group == 22 || 
            aaa[pr].group == -5 || 
            aaa[pr].group == -44 || 
            aaa[pr].group == 4444 || 
            aaa[pr].group == -66 || 
            aaa[pr].group == -3 || 
            aaa[pr].group == 33  || 
            aaa[pr].group == 88  || 
            aaa[pr].group == 888  || 
            aaa[pr].group == -88  || 
            aaa[pr].group == -8 ){
            status2.setForeground(Color.RED);
            status2.setText("Cannot MOVE selected node:" + aaa[pr].name + aaa[pr].value);
            pressed = released = -1;
            movePosition = 0;
            //tmp1[pr].mClicked(null);
            return -1;
        }
        else if(( ( aaa[re].group == 1 || aaa[re].group == 2 || aaa[re].group == 3 || aaa[re].group == 3333) 
                && position == 0 ) ||
            ((aaa[pr].group == -1 || aaa[pr].group == -11 || aaa[pr].group == -111 || aaa[pr].group == 11 || 
            aaa[pr].group == -2 || aaa[pr].group == 22 || aaa[pr].group == -3 ) && (position == -1 || position == 1))
                ){
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
        saveOpt = false;
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
        
        saveOpt = false;
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
        saveOpt = false;
        tmp1[0].mClicked(null);
    }
   
    private void undoAction(){
        
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
        saveOpt = false;
    }


    private void buttonFunc_actionPerformed(ActionEvent e) {
        //FuncSelection tmp = new FuncSelection(this,"Select Function",true,this,true,con,"1","1","1",1,"");
        //tmp.setVisible(true);
    }

/*    public boolean checkSyntax(boolean ch){
        try {
            int caretPosition = txtExpression.getCaretPosition();
            
            if(txtExpression.getText().trim().compareTo("") == 0)
                return true;
            
            String output = "";
            ByteArrayInputStream byte_in = new ByteArrayInputStream (txtExpression.getText().toUpperCase().getBytes());
            InputStream is = byte_in;                
    
            ANTLRInputStream input = new ANTLRInputStream(is);
            // Create an ExprLexer that feeds from that stream
            CheckConstraintExpressionLexer lexer = new CheckConstraintExpressionLexer(input);
            // Create a stream of tokens fed by the lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // Create a parser that feeds off the token stream
            //Object aaa2[] = tokens.getTokens().toArray();
            CheckConstraintExpressionParser parser = new CheckConstraintExpressionParser(tokens);
            parser.typeOfParser = typeOfEditor;
            parser.rootNodeName = nodeNameSyntax.toUpperCase();
            parser.treeID = String.valueOf(treeID);
            parser.ID_2 = ID2;
            parser.ID = String.valueOf(id_comp);

            System.out.println("CheckConstraintExpressionEditor\nPRID: " + parser.treeID + "   ID_2: "+ parser.ID_2 + "   ID:" + parser.ID + "\n**********");            
            
            Object aaa2[] = tokens.getTokens().toArray();
    
            StyleContext context = new StyleContext();
            StyledDocument document = new DefaultStyledDocument(context);
            Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
            
            if(ch){
                output = parser.project();
                
                //jScrollPaneErr.setVisible(false);
                model2.removeAllElements();
                //System.out.println("errore:" +parser.getMyError() + ":" + parser.getMyError().split("#!#E#!#").length);
                if(ch && parser.getMyError().length() > 7){
                    for(int h=0;h < parser.getMyError().split("#!#E#!#").length ; h++){
                        JLabel tmpl = new JLabel(parser.getMyError().split("#!#E#!#")[h]);
                        tmpl.setBorder(BorderFactory.createLineBorder(new Color(243,243,243), 1)); 
                        model2.addElement(tmpl);
                    }
                }
                else if(ch){
                    xmlToNodes(null,output);
                    undoAction();        
                    design(true);   
                    saveOpt = true;
                    JLabel tmpl = new JLabel("Succes");
                    tmpl.setBorder(BorderFactory.createLineBorder(new Color(243,243,243), 1)); 
                    model2.addElement(tmpl);
                }
        
                //System.out.println("ERR:::" + parser.getMyError() );
                if( parser.getMyError().length() > 7 && ch)
                    return false;
            }
            
            document.insertString(0, txtExpression.getText(), null);
            String line = txtExpression.getText();
            for(int s=0;s<tokens.getTokens().toArray().length; s++){
                String text = "";
                int f =  Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]);
                int l =  Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1] ) + 1;
                
                System.out.println(text + " " + f + " " + l + " " + aaa2[s].toString());
                text = ("'" + line.substring(f,l) + "'").toUpperCase() ;
                System.out.println(text);
                
                if( text.compareTo("'AND'") == 0 ||
                    text.compareTo("'OR'") == 0 ||
                    text.compareTo("'XOR'") == 0 ||
                    text.compareTo("'<=>'") == 0 ||
                    text.compareTo("'=>'") == 0){
                    
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setItalic(attributes, true);
                    StyleConstants.setForeground(attributes, Color.red);                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }
                else if( text.compareTo("'TRUE'") == 0 ||
                    text.compareTo("'FALSE'") == 0 ||
                    text.compareTo("'NULL'") == 0 ){
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setBold(attributes, true);
                    StyleConstants.setForeground(attributes, Color.BLUE);                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }
                else if( text.compareTo("'VALUE'") == 0 ||
                    text.compareTo("'THIS'") == 0 ){
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setBold(attributes, true);
                    StyleConstants.setForeground(attributes, new Color(0,214,0));                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }                
                else if( text.compareTo("'IN'") == 0  ){
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setBold(attributes, true);
                    StyleConstants.setItalic(attributes, true);
                    StyleConstants.setForeground(attributes, new Color(255,181,99));                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }  
                else if( text.compareTo("'LIKE'") == 0  ){
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setBold(attributes, true);
                    StyleConstants.setItalic(attributes, true);
                    StyleConstants.setForeground(attributes, new Color(148,0,148));                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }  
                else if(text.length() > 3 &&
                        text.substring(0,2).compareTo("''") == 0 && 
                        text.substring(aaa2[s].toString().split(",")[1].split("=")[1].length() - 2,aaa2[s].toString().split(",")[1].split("=")[1].length()).compareTo("''") == 0 ){

                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setUnderline(attributes, true);
                    StyleConstants.setForeground(attributes, new Color(159,159,159));
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);                    

                }                  
            }
            txtExpression.setDocument(document);
            //System.out.println("pos:" + caretPosition);
            txtExpression.setCaretPosition(caretPosition);
            
            //System.out.println("XML::::::::\n\n"+output+"\n\nXML::::::::\n");                
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }  
        return true;
    }   
    */
    
    private void jMenuItem7_actionPerformed(ActionEvent e) {
        save();
    }


    public void drawSyntax(CCEValue tmpCCVE){
   /*     System.out.println("XML:\n"+tmpCCVE.xml);
        xmlToNodes(null,tmpCCVE.xml);
        undoAction();        
        design(false);  
        tmp1[0].mClicked(null);        
        */
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
        saveOpt = false;
        design(true);    
    }

    private void pUndo_mouseClicked(MouseEvent e) {
        if(--undoPoz < 0)
            return;

        aaa = undon[undoPoz];
        design(true);    
    }

    private void pDel_mouseClicked(MouseEvent e) {
        if(pDel.isEnabled())
            deleteNode(true);    
    }


    private void pExp_mouseClicked(MouseEvent e) {
        if(!pExp.isEnabled())
            return;      
        int tmpc = this.clicked;
        addNode("Simple Expression","S",0,-6,true,"");    
        tmp1[tmpc].mClicked(null);
    }


    private void pBlock_mouseClicked(MouseEvent e) {
        if(!pBlock.isEnabled())
            return;
        int tmpc = addNode("EXECUTION_BLOCK","B",0,0,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void txtExpression_keyReleased(KeyEvent e) {
        //System.out.println("aaaaaaaaaaaaaaa::" + e.getKeyCode());
        if( e.getKeyCode() == 16 || e.getKeyCode() == 17 || e.getKeyCode() == 18 || e.getKeyCode() == 37 || 
            e.getKeyCode() == 38 || e.getKeyCode() == 39 || e.getKeyCode() == 40 || e.getKeyCode() == 33 ||
            e.getKeyCode() == 34 || e.getKeyCode() == 35 || e.getKeyCode() == 36 || /*e.getKeyCode() == 127 || */
            e.getKeyCode() == 155 || e.getKeyCode() == 10 )
            return;
        //int ds = txtExpression.getCaretPosition();
        //txtExpression.setText(txtExpression.getText().replaceAll("\r",""));
        //txtExpression.setCaretPosition(ds);
        //Attribute.checkSyntax2(txtExpression,false,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2);
    }
    
    private int save(){
        try{
            if( check() == 1 ){
                JDBCQuery query=new JDBCQuery(con);
                /*int max = 1;
                ResultSet rs;
                rs = query.select("Select max(FB_ID) from IISC_FUN_BODY");
                if(rs.next())
                    max = rs.getInt(1);*/
                    
                query.update("delete from IISC_FUN_BODY where FUN_ID = "+ id_comp + " and PR_ID = " + treeID);
                for(int p=0;p<aaa.length;p++){
                    //System.out.println("insert into IISC_FUN_BODY(FUN_ID,PR_ID,type,valuee,namee,groupe) values (" + id_comp + ", " + treeID + ", '"+aaa[p].type + "', '" + aaa[p].value + "', '" + aaa[p].name + "' , " + aaa[p].group + " )");
                    query.update("insert into IISC_FUN_BODY(FUN_ID,PR_ID,type,valuee,namee,groupe) values (" + id_comp + ", " + treeID + ", '"+aaa[p].type + "', '" + aaa[p].value.replaceAll("'","''") + "', '" + aaa[p].name + "' , " + aaa[p].group + " )");
                }
                saveOpt = true;
            }
            else{
                JOptionPane.showMessageDialog(null, "<html><center>Check Function!", "User function ", JOptionPane.ERROR_MESSAGE);
                return -1;
            }  
            JOptionPane.showMessageDialog(null, "<html><center>Function Saved!", "User Function", JOptionPane.INFORMATION_MESSAGE);
            tmp1[0].mClicked(null);
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
        if(saveOpt){
            this.dispose();
            return;
        }
            
        int what = JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "User defined expression", JOptionPane.YES_NO_CANCEL_OPTION);
        //System.out.println("Izlazak:"  + what);
        if( what == 0){
            if( check() == 1 ){
                if( save() == 1 )
                    this.dispose();
            }            
            else{
                JOptionPane.showMessageDialog(null, "<html><center>Check expression syntax!", "User defined expression", JOptionPane.ERROR_MESSAGE);                        
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



    private void menuFileExit_actionPerformed(ActionEvent e) {
        exit();
    }

    private void jMenuItem29_actionPerformed(ActionEvent e) {
        //FuncSelection tmp = new FuncSelection(this,"Select Function",true,this,true,con,treeID,ID2,String.valueOf(id_comp),typeOfEditor,"");
        //tmp.setVisible(true);      
    }

    private void treeView_mouseClicked(MouseEvent e) {
        DefaultMutableTreeNodeEditor selNode = (DefaultMutableTreeNodeEditor)treeView.getLastSelectedPathComponent();  
        if(selNode != null)
            tmp1[selNode.pozition].mClicked(null);
    }

/*
    private void txtExpression_keyPressed(KeyEvent e) {
        System.out.println("xxx:" + e.getKeyCode());
        //jScrollPane1.setVisible(false);
    
    }
    */

    private void txtExpression_focusGained(FocusEvent e) {
        //Attribute.checkSyntax2(txtExpression,false,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2);
    }

    private void txtExpression_mouseClicked(MouseEvent e) {
        if(ddList != null)
            ddList.setVisible(false);
    }

    private void jComboBox1_actionPerformed(ActionEvent e) {
        
    }


    private void pHelp_mouseClicked(MouseEvent e) {

    }

    private void setBorder(MouseEvent e) {
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

    /*private void pNewExpr_mouseClicked(MouseEvent e) {
        if( this.clicked != -1 && aaa[this.clicked].type.compareTo("S") == 0){
            aaa[this.clicked].value += " " + cmbAddExpr.getSelectedItem().toString().split(":")[0];
            tmp1[this.clicked].jTextArea1.setText( tmp1[this.clicked].jTextArea1.getText() + " " + cmbAddExpr.getSelectedItem().toString().split(":")[0]);
            //txtExpression.setText(print(1).trim());
            //Attribute.checkSyntax2(txtExpression,false,con,typeOfEditor,String.valueOf(treeID),String.valueOf(ID2),String.valueOf(id_comp),nodeNameSyntax,model2);
        }    
    }*/

    private void pFor_mouseClicked(MouseEvent e) {
        if(!pFor.isEnabled())
            return;
        int tmpc = this.clicked;
        this.clicked = addNode("FOR","B",0,1,false,"");
        addNode("Begin","S",0,-1,false,"");
        addNode("Condition","S",0,-11,false,"");
        addNode("Step","S",0,-111,false,"");
        addNode("FOR_BODY","B",0,11,true,"");
        tmp1[tmpc].mClicked(null);
    }

    private void pWhile_mouseClicked(MouseEvent e) {
        if(!pWhile.isEnabled())
            return;    
        int tmpc = this.clicked ;
        this.clicked = addNode("WHILE","B",0,2,false,"");   
        addNode("Condition","S",0,-2,false,""); 
        addNode("WHILE_BODY","B",0,22,true,"");   
        tmp1[tmpc].mClicked(null);
    }

    private void pIf_mouseClicked(MouseEvent e) {
        if(!pIf.isEnabled())
            return;    
        int tmpc = this.clicked ;
        this.clicked = addNode("IF","B",0,3,false,"");  
        addNode("Condition","S",0,-3,false,""); 
        addNode("THEN","B",0,33,false,"");
        addNode("ELSE","B",0,333,true,"");
        tmp1[tmpc].mClicked(null);
    }

    private void pDeclaration_mouseClicked(MouseEvent e) {
        if(!pDeclaration.isEnabled())
            return;          
        int tmpc = addNode("Variable","S",0,-5,true,"");    
        tmp1[tmpc].mClicked(null);
    }

    private void pComment_mouseClicked(MouseEvent e) {
        if(!pComment.isEnabled())
            return;       
        int tmpc = this.clicked;
        addNode("Comments","S",0,-4,true,"");
        tmp1[tmpc].mClicked(null);
    }

    private void pTry_mouseClicked(MouseEvent e) {
        if(!pTry.isEnabled())
            return;     
        int tmp = this.clicked;
        int tmpc = addNode("TRY","B",0,4,false,"");
        this.clicked = addNode("CATCH","B",0,44,false,"");
        addNode("Exception","S",0,-44,false,"");
        addNode("CATCH_BLOCK","B",0,4444,false,"");
        this.clicked = tmp;
        addNode("FINNALY","B",0,444,true,""); 
        tmp1[tmpc].mClicked(null);  
    }

    private void pCatch_mouseClicked(MouseEvent e) {
        if(!pCatch.isEnabled())
            return;     
        if(aaa[this.clicked].name.equals("TRY")){
            this.clicked = addNode("CATCH","B",1,44,false,"");
            addNode("Exception","S",0,-44,false,"");
            addNode("CATCH_BLOCK","B",0,4444,true,"");            
            tmp1[this.clicked].mClicked(null);
        }
    }

    private void pFinally_mouseClicked(MouseEvent e) {
        if(!pFinally.isEnabled())
            return;     
        if(aaa[this.clicked].name.equals("CATCH")){
            int tmpc = addNode("FINNALY","B",1,444,true,"");
            tmp1[tmpc].mClicked(null);
        }
    }

    private void pElse_mouseClicked(MouseEvent e) {
        if(!pElse.isEnabled())
            return;  
        if(aaa[this.clicked].name.equals("THEN")){            
            int tmpc = this.clicked ;
            addNode("ELSE","B",1,333,true,"");
            tmp1[tmpc].mClicked(null);    
        }
    }

    private void pElseIf_mouseClicked(MouseEvent e) {
        if(!pElseIf.isEnabled())
            return;      
        int delNode = 0;
        int pos = 0;
        if(aaa[this.clicked].name.equals("ELSE")){
            delNode = this.clicked;
            pos = 1;
        }
        else if(aaa[this.clicked].name.equals("THEN")){
            pos = 1;
        }
    
        int tmpc = this.clicked ;
        this.clicked = addNode("ELSEIF","B",pos,3333,false,"");  
        addNode("Condition","S",0,-3,false,""); 
        addNode("THEN","B",0,33,false,"");
        addNode("ELSE","B",0,333,true,"");
        tmp1[tmpc].mClicked(null);    
        
        if(aaa[this.clicked].name.equals("ELSE")){
            this.clicked = delNode;
            deleteNode(true);
        }
    }

    private void pLocalFunction_mouseClicked(MouseEvent e) {
        //int tmpc = this.clicked ;
         if(!pLocalFunction.isEnabled())
             return;          
        this.clicked = addNode("LOCAL_FUNCTION","B",0,8,false,"");  
        addNode("Function Name","S",0,-88,false,"");
        addNode("ARGUMENTS","B",0,88,false,"");
        int tmpc = addNode("LOCAL_FUNCTION_BODY","B",0,888,true,"");
        tmp1[tmpc].mClicked(null);            
    }

    private void pLocalFuncArgs_mouseClicked(MouseEvent e) {
        if(!pLocalFuncArgs.isEnabled())
            return;      
        addNode("Arg","S",0,-8,true,"");     
        tmp1[this.clicked].mClicked(null); 
    }


    private int check(){
        
        String domains = ":void:";
        
        try{
            ResultSet rs1;
            JDBCQuery query = new JDBCQuery(con);
            rs1=query.select("select * from IISC_DOMAIN");
            
            while(rs1.next()){  
                domains += rs1.getString("Dom_mnem") + ":";
            }
        }
        catch(Exception ex){
            System.out.println(ex.toString());
        }
    
    
        
            int stack[] = new int[aaa.length];
            stack[0] = 0;
            int stackno = 1;
            
            DefaultListModel listModel = new DefaultListModel();
            
            for(int i = 1; i < aaa.length - 1; i++){
            
                if( aaa[i].type.equals("B") && aaa[i].name.equals("*END*") )
                    continue;
            
                int otac = -1;
                int prethodni = i -1;
                int sledeci = i + 1;
                int blocks = 0;
                for(otac = i - 1; otac >= 0; otac--)
                    if(aaa[otac].type.equals("B")){
                        if(aaa[otac].name.equals("*END*"))
                            blocks--;
                        else
                            blocks++;
                            
                        if(blocks == 1)
                            break;
                    }
                    
                blocks = 0;
                for(prethodni = i - 1; prethodni >= 0; prethodni--){
                    if(aaa[prethodni].group == -4 )
                        continue;
                    else if(aaa[prethodni].type.equals("B")){
                        if(aaa[prethodni].name.equals("*END*"))
                            blocks--;
                        else
                            blocks++;
                    }
                    if(blocks >= 0)
                        break;
                }
                
                if( aaa[i].type.equals("B")){
                    blocks = 1;
                    for(sledeci = i + 1; sledeci < aaa.length; sledeci++){
                        if( aaa[sledeci].group == -4 )
                            continue;
                        if(aaa[sledeci].type.equals("B")){
                            if(aaa[sledeci].name.equals("*END*"))
                                blocks--;
                            else
                                blocks++;
                        }
                        if(blocks == 0){
                            if(aaa[i+1].type.equals("B"))
                                sledeci++;
                            else
                                while(aaa[sledeci].group == -4)
                                    sledeci++;
                            break;
                        } 
                    }                
                }
                else
                    for(sledeci = i + 1;sledeci < aaa.length - 1 ; sledeci++){
                        if(aaa[sledeci].group != -4)
                            break;
                    }

                
                /*
                System.out.println("****************          **************");    
                System.out.println("***" + i + "*******" + prethodni + "******" + otac + "******" + sledeci);
                System.out.println("****************   " + aaa[prethodni].name + "   " + aaa[prethodni].value);
                System.out.println("****************   " + aaa[otac].name + "   " + aaa[otac].value);
                System.out.println("****************   " + aaa[sledeci].name + "   " + aaa[sledeci].value);
                System.out.flush();*/
                if(aaa[i].group == 4){
                    if(aaa[otac].group != 0 && aaa[otac].group != 11 && aaa[otac].group != 22 && 
                            aaa[otac].group != 888 && aaa[otac].group != 66 && aaa[otac].group != 33 && 
                            aaa[otac].group != 333 && aaa[otac].group != 4 && aaa[otac].group != 4444 && 
                            aaa[otac].group != 444){
                        listModel.addElement(new JLabel("TRY block cannot be in " + aaa[otac].name + " block | " + i));
                    }
                    /*else if(aaa[sledeci].group != 44)
                        listModel.addElement(new JLabel("Expect CATCH BLOCK | " + i));*/
                }
                else if(aaa[i].group == 44){
                    if(aaa[prethodni].group != 4 && aaa[prethodni].group != 44)
                        listModel.addElement(new JLabel("CATCH block must be after TRY/CACTH blocks | " + i));
                    else if(aaa[otac].group != 0 && aaa[otac].group != 11 && aaa[otac].group != 22 && 
                            aaa[otac].group != 888 && aaa[otac].group != 66 && aaa[otac].group != 33 && 
                            aaa[otac].group != 333 && aaa[otac].group != 4 && aaa[otac].group != 4444 && 
                            aaa[otac].group != 444){
                        listModel.addElement(new JLabel("TRY block cannot be in " + aaa[otac].name + " block | " + i));
                    }
                }            
                else if(aaa[i].group == 444){
                    if( aaa[prethodni].group != 44 )
                        listModel.addElement(new JLabel("FINALLY block must be after CATCH block | " + i));
                    else if(aaa[otac].group != 0 && aaa[otac].group != 11 && aaa[otac].group != 22 && 
                            aaa[otac].group != 888 && aaa[otac].group != 66 && aaa[otac].group != 33 && 
                            aaa[otac].group != 333 && aaa[otac].group != 4 && aaa[otac].group != 4444 && 
                            aaa[otac].group != 444){
                        listModel.addElement(new JLabel("FINNALY block cannot be in "+ aaa[otac].name +" block | " + i));
                    }
                }
                else if(aaa[i].group == 4444){
                    if(aaa[otac].group != 44){
                        listModel.addElement(new JLabel("CATCH BODY block must be in CATCH block | " + i));
                    }
                    else if( aaa[prethodni].group != -44 )
                        listModel.addElement(new JLabel("Exception not founded befor CATCH BODY block | " + i));  
                    else if(aaa[sledeci].group != -1 && aaa[sledeci].type.equals("B")){
                         listModel.addElement(new JLabel("CATCH BODY block must be last in CATCH block | " + i));
                    }
                }
                else if(aaa[i].group == -44){
                    if(aaa[otac].group != 44){
                        listModel.addElement(new JLabel("Exception must be in CATCH block | " + i));
                    }
                    else if( aaa[prethodni].group != 44 )
                        listModel.addElement(new JLabel("Exception must be first element in CATCH block | " + i)); 
                    /*else if( aaa[sledeci].group != 4444 )
                        listModel.addElement(new JLabel("CATCH BODU  must be after EXCEPTION | " + i)); */
                }  
                else if(aaa[i].group == -5){
                    if(aaa[otac].group != 5){
                        listModel.addElement(new JLabel("Declaration must be in DECLARATION block | " + i));
                    }// ne treba prethosni posto samo on moze da se pojavi u bloku
                    
                    //else if( aaa[sledeci].group != 5 || !aaa[sledeci].name.equals("*END*") )
                        //listModel.addElement(new JLabel("CATCH BODU  must be after EXCEPTION | " + i));                
                }
                else if(aaa[i].group == -6){
                if(aaa[otac].group != 0 && aaa[otac].group != 11 && aaa[otac].group != 22 && 
                    aaa[otac].group != 888 && aaa[otac].group != 66 && aaa[otac].group != 33 && 
                    aaa[otac].group != 333 && aaa[otac].group != 4 && aaa[otac].group != 4444 && 
                    aaa[otac].group != 444){
                        listModel.addElement(new JLabel("Expression cannot be in "+ aaa[otac].name +" block | " + i));
                    }
                }  
                else if(aaa[i].group == 5){
                   if(aaa[otac].group != 0 && aaa[otac].group != 11 && aaa[otac].group != 22 && 
                     aaa[otac].group != 888 && aaa[otac].group != 66 && aaa[otac].group != 33 && 
                     aaa[otac].group != 333 && aaa[otac].group != 4 && aaa[otac].group != 4444 && 
                     aaa[otac].group != 444){
                         listModel.addElement(new JLabel("DECLARATION block cannot be in "+ aaa[otac].name +" block | " + i));
                    }
                }    
                else if(aaa[i].group == 0){
                   if(aaa[otac].group != 0 && aaa[otac].group != 11 && aaa[otac].group != 22 && 
                     aaa[otac].group != 888 && aaa[otac].group != 66 && aaa[otac].group != 33 && 
                     aaa[otac].group != 333 && aaa[otac].group != 4 && aaa[otac].group != 4444 && 
                     aaa[otac].group != 444){
                         listModel.addElement(new JLabel("EXECUTION block cannot be in " + aaa[otac].name + " block | " + i));
                    }
                }
                else if(aaa[i].group == 1){
                   if(aaa[otac].group != 0 && aaa[otac].group != 11 && aaa[otac].group != 22 && 
                     aaa[otac].group != 888 && aaa[otac].group != 66 && aaa[otac].group != 33 && 
                     aaa[otac].group != 333 && aaa[otac].group != 4 && aaa[otac].group != 4444 && 
                     aaa[otac].group != 444){
                         listModel.addElement(new JLabel("FOR block cannot be in " + aaa[otac].name + " block | " + i));
                    }
                }
                else if(aaa[i].group == 11){
                   if(aaa[otac].group != 1){
                         listModel.addElement(new JLabel("FOR BODY block must be in FOR block | " + i));
                    }
                    else if(aaa[prethodni].group != -111){
                         listModel.addElement(new JLabel("FOR BODY block must be after Step element | " + i));
                    }
                    else if(aaa[sledeci].group != -1 && aaa[sledeci].type.equals("B")){
                         listModel.addElement(new JLabel("FOR BODY block must be last in FOR block | " + i));
                    }                
                }            
                else if(aaa[i].group == -1 && aaa[i].type.equals("S")){
                   if(aaa[otac].group != 1){
                         listModel.addElement(new JLabel("Begin element must be in FOR block | " + i));
                    }
                    else if(aaa[prethodni].group != 1){
                         listModel.addElement(new JLabel("Begin element must be first in FOR block | " + i));
                    }                
                }            
                else if(aaa[i].group == -11){
                   if(aaa[otac].group != 1){
                         listModel.addElement(new JLabel("Condition element must be in FOR block | " + i));
                    }
                    else if(aaa[prethodni].group != -1){
                         listModel.addElement(new JLabel("Condition element must be after Begin element | " + i));
                    }  
                } 
                else if(aaa[i].group == -111){
                   if(aaa[otac].group != 1){
                         listModel.addElement(new JLabel("Step element must be in FOR block | " + i));
                    }
                    else if(aaa[prethodni].group != -11){
                         listModel.addElement(new JLabel("Step element must be after Condition element | " + i));
                    }  
                } 
                else if(aaa[i].group == 2){
                   if(aaa[otac].group != 0 && aaa[otac].group != 11 && aaa[otac].group != 22 && 
                     aaa[otac].group != 888 && aaa[otac].group != 66 && aaa[otac].group != 33 && 
                     aaa[otac].group != 333 && aaa[otac].group != 4 && aaa[otac].group != 4444 && 
                     aaa[otac].group != 444){
                         listModel.addElement(new JLabel("WHILE block cannot be in " + aaa[otac].name + " block | " + i));
                    }
                }
                else if(aaa[i].group == 22){
                   if(aaa[otac].group != 2){
                         listModel.addElement(new JLabel("WHILE BODY block must be in WHILE DO block | " + i));
                    }
                    else if(aaa[prethodni].group != -2){
                          listModel.addElement(new JLabel("WHILE BODY block must be after Condition element | " + i));
                    }    
                    else if(aaa[sledeci].group != -1 && aaa[sledeci].type.equals("B")){
                         listModel.addElement(new JLabel("WHILE BODY block must be last in WHILE DO block | " + i));
                    }                 
                }
                else if(aaa[i].group == -2){
                   if(aaa[otac].group != 2){
                         listModel.addElement(new JLabel("Condition Element must be in WHILE DO block | " + i));
                    }
                    else if(aaa[prethodni].group != 2){
                          listModel.addElement(new JLabel("Condition Element must be first in WHILE DO block | " + i));
                     }                                
                }            
                else if(aaa[i].group == 6){
                   if(aaa[otac].group != 0 && aaa[otac].group != 11 && aaa[otac].group != 22 && 
                     aaa[otac].group != 888 && aaa[otac].group != 66 && aaa[otac].group != 33 && 
                     aaa[otac].group != 333 && aaa[otac].group != 4 && aaa[otac].group != 4444 && 
                     aaa[otac].group != 444){
                         listModel.addElement(new JLabel("DO WHILE bloclk cannot be in "+ aaa[otac].name + " block | " + i));
                    }
                }
                else if(aaa[i].group == 66){
                   if(aaa[otac].group != 6){
                         listModel.addElement(new JLabel("DO WHILE BODY block must be in DO WHILE block | " + i));
                    }
                    else if(aaa[prethodni].group != 6){
                          listModel.addElement(new JLabel("DO WHILE BODY BLOCK must be first in block DO WHILE block | " + i));
                     }  
                } 
                else if(aaa[i].group == -66){
                   if(aaa[otac].group != 6){
                         listModel.addElement(new JLabel("Condition Element must be in DO WHILE block | " + i));
                    }
                    else if(aaa[prethodni].group != 66){
                          listModel.addElement(new JLabel("Condition Element must be after DO_WHILE_BODY block | " + i));
                    }  
                    else if(aaa[sledeci].group != -1 && aaa[sledeci].type.equals("B")){
                         listModel.addElement(new JLabel("Condition Element must be last in DO WHILE block | " + i));
                    }                 
                }             
                else if(aaa[i].group == 3){
                   if(aaa[otac].group != 0 && aaa[otac].group != 11 && aaa[otac].group != 22 && 
                     aaa[otac].group != 888 && aaa[otac].group != 66 && aaa[otac].group != 33 && 
                     aaa[otac].group != 333 && aaa[otac].group != 4 && aaa[otac].group != 4444 && 
                     aaa[otac].group != 444){
                         listModel.addElement(new JLabel("IF block cannot be in " + aaa[otac].name + "  block | " + i));
                    }
                }
                else if(aaa[i].group == 33){
                   if(aaa[otac].group != 3333 && aaa[otac].group != 3){
                         listModel.addElement(new JLabel("THEN block must be in IF/ELSEIF blocks | " + i));
                    }
                    else if(aaa[prethodni].group != -3 ){
                         listModel.addElement(new JLabel("THEN block must be after Condition element | " + i));
                    }
                }  
                else if(aaa[i].group == 333){
                    if(aaa[otac].group != 3 && aaa[otac].group != 3333){
                         listModel.addElement(new JLabel("ELSE block must be in IF block | " + i));
                    }
                    else if(aaa[prethodni].group != 33 ){
                         listModel.addElement(new JLabel("ELSE block must be after THEN block | " + i));
                    }   
                    else if(aaa[sledeci].group != -1 && aaa[sledeci].type.equals("B")){
                         listModel.addElement(new JLabel("ELSE block must be last in IF/ELSEIF block | " + i));
                    }                    
                }            
                else if(aaa[i].group == 3333){
                   if(aaa[otac].group != 3 && aaa[otac].group != 3333){
                         listModel.addElement(new JLabel("ELSEIF block must be in IF block | " + i));
                    }
                    else if(aaa[prethodni].group != 33 ){
                         listModel.addElement(new JLabel("ELSEIF block must be after THEN block | " + i));
                    }   
                    else if(aaa[sledeci].group != -1 && aaa[sledeci].type.equals("B")){
                         listModel.addElement(new JLabel("ELSEIF block must be last in IF/ELSEIF blocks | " + i));
                    }                 
                }
                else if(aaa[i].group == -3){
                   if(aaa[otac].group != 3 && aaa[otac].group != 3333){
                         listModel.addElement(new JLabel("Condition Element must be in IF/ELSEIF blocks | " + i));
                    }
                    else if(aaa[prethodni].group != 3 && aaa[prethodni].group != 3333){
                         listModel.addElement(new JLabel("Condition block must be first in IF/ELSEIF blocks | " + i));
                    }                  
                }              
                else if(aaa[i].group == 8){
                   if(aaa[otac].group != 5 ){
                         listModel.addElement(new JLabel("LOCAL FUNCTION block must be in DECLARATION block | " + i));
                    }
                }    
                else if(aaa[i].group == -88){
                   if(aaa[otac].group != 8 ){
                         listModel.addElement(new JLabel("LOCAL FUNCTION NAME element must be in LOCAL FUNCTION block | " + i));
                    }
                    else if(aaa[prethodni].group != 8 ){
                         listModel.addElement(new JLabel("LOCAL FUNCTION NAME element must be first in LOCAL FUNCTION block | " + i));
                    }                 
                } 
                else if(aaa[i].group == 88){
                   if(aaa[otac].group != 8 ){
                         listModel.addElement(new JLabel("ARGUMENT block must be in LOCAL FUNCTION block | " + i));
                    }
                    else if(aaa[prethodni].group != -88 ){
                         listModel.addElement(new JLabel("ARGUMENT block must be after LOCAL FUNCTION NAME element | " + i));
                    }                   
                }              
                else if(aaa[i].group == 888){
                   if(aaa[otac].group != 8 ){
                         listModel.addElement(new JLabel("LOCAL FUNCTION BODY block must be in LOCAL FUNCTION block | " + i));
                    }
                    else if(aaa[prethodni].group != 88 ){
                         listModel.addElement(new JLabel("LOCAL FUNCTION BODY block must be after ARGUMENTS block | " + i));
                    }    
                    else if(aaa[sledeci].group != -1 && aaa[sledeci].type.equals("B")){
                         listModel.addElement(new JLabel("LOACL FUNCTION BODY block must be last in LOCAL FUNCTION block | " + i));
                    } 
                }  
                else if(aaa[i].group == -8){
                    if(aaa[otac].group != 88){
                        listModel.addElement(new JLabel("Declaration must be in DECLARATION block | " + i));
                    }
                }
                
                
                
                
                
                
                
                
                
                
                if(aaa[i].group == -5 && aaa[i].name.trim().equals("Constant")){
                    if(aaa[i].value.trim().replaceAll("  "," ").split("=")[0].split(" ").length != 2){
                        listModel.addElement(new JLabel("Constant must have Domain and name | " + i));
                    }
                    else if(!domains.contains(aaa[i].value.trim().replaceAll("  "," ").split("=")[0].split(" ")[0])){
                        listModel.addElement(new JLabel("Chose domain from User Defined Domains | " + i));
                    }
                    else if(!aaa[i].value.contains("=")){
                        listModel.addElement(new JLabel("Constant must have value | " + i));
                    }
                    else if(aaa[i].value.replaceAll("  "," ").split("=",2)[1].trim().equals(""))
                        listModel.addElement(new JLabel("Constant must have value | " + i));
                }
                else if(aaa[i].group == -5 && aaa[i].name.trim().equals("Variable")){
                //System.out.println(aaa[i].value.trim().replaceAll("  "," ").split("=")[0].split(" ")[0]);
                    if(aaa[i].value.trim().replaceAll("  "," ").split("=")[0].split(" ").length != 2){
                        listModel.addElement(new JLabel("Variable must have Domain and name | " + i));
                    }
                    else if(!domains.contains(aaa[i].value.trim().replaceAll("  "," ").split("=")[0].split(" ")[0])){
                        listModel.addElement(new JLabel("Chose domain from User Defined Domains | " + i));
                    }                    
                    /*else if(!){
                        listModel.addElement(new JLabel("Variable must have value | " + i));
                    }*/
                    else if (aaa[i].value.contains("=") && aaa[i].value.split("=",3)[1].trim().equals(""))
                        listModel.addElement(new JLabel("Variable must have value | " + i));
                }    
                else if( aaa[i].group == -8 ){
                    if(aaa[i].value.trim().replaceAll("  "," ").split("=")[0].split(" ").length != 2){
                        listModel.addElement(new JLabel("Argument must have Domain and name | " + i));
                    }
                    else if(!domains.contains(aaa[i].value.trim().replaceAll("  "," ").split("=")[0].split(" ")[0])){
                        listModel.addElement(new JLabel("Chose domain from User Defined Domains | " + i));
                    }                    
                    else if (aaa[i].value.contains("=") && aaa[i].value.split("=",3)[1].trim().equals(""))
                        listModel.addElement(new JLabel("Argument must have value | " + i));                    
                }    
                else if( aaa[i].group == -6 && aaa[i].name.equals("Assignment") ){
                    if(aaa[i].value.trim().replaceAll("  "," ").split("=")[0].split(" ").length != 1){
                        listModel.addElement(new JLabel("Assignment must have Variable on left side | " + i));
                    }
                    else if(!aaa[i].value.contains("=")){
                        listModel.addElement(new JLabel("Assignment must have expression on right side | " + i));
                    }                    
                    else if ( aaa[i].value.split("=",3)[1].trim().equals(""))
                        listModel.addElement(new JLabel("Assignment must have expression or right side | " + i));                    
                }   
                else if( aaa[i].group == -88  ){
                    if(aaa[i].value.trim().replaceAll("  "," ").split("=")[0].split(" ").length != 2){
                        listModel.addElement(new JLabel("Function Name must containt Domain for return value | " + i));
                    }
                    else if(!domains.contains(aaa[i].value.trim().replaceAll("  "," ").split("=")[0].split(" ")[0])){
                        listModel.addElement(new JLabel("Chose domain from User Defined Domains for return value | " + i));
                    }
                }                 
/*                else if( aaa[i].group == -5 && aaa[i].name.equals("Simple Expression") ){
                    if(aaa[i].value.trim().replaceAll("  "," ").split("=")[0].split(" ").length != 1){
                        listModel.addElement(new JLabel("Simple Expression must have Variable | " + i));
                    }
                    else if (aaa[i].value.contains("=") && aaa[i].value.split("=",3)[1].trim().equals(""))
                        listModel.addElement(new JLabel("Assignment must have expression | " + i));                    
                }                
                */
            }  
            
            if(listModel.size() == 0){
                listModel.addElement(new JLabel("Succes"));
                errorList.setModel(listModel);        
                return 1;
            }
            else{
                errorList.setModel(listModel);        
                
            }
            return -1;
    }

    private void pCheck_mouseClicked(MouseEvent e) {
        check();
    }

    private void errorList_mouseClicked(MouseEvent e) {
        if(errorList.getSelectedIndex()!= -1){
            String[] tmps = ((JLabel)errorList.getSelectedValue()).getText().split("\\|");
            tmp1[Integer.parseInt(tmps[1].trim())].mClicked(null);
        }
    }

    private void pDeclarationB_mouseClicked(MouseEvent e) {
        if(!pDeclarationB.isEnabled())
            return;      
        int tmpc = this.clicked ;
        addNode("DECLARATION","B",0,5,true,"");
        tmp1[tmpc].mClicked(null);         
    }

    private void pDoWhile_mouseClicked(MouseEvent e) {
        if(!pDoWhile.isEnabled())
            return;     
        int tmpc = this.clicked ;
        this.clicked = addNode("DO_WHILE","B",0,6,false,"");   
        addNode("DO_WHILE_BODY","B",0,66,false,"");   
        addNode("Condition","S",0,-66,true,"");         
        tmp1[tmpc].mClicked(null);    
    }

    private void this_componentResized(ComponentEvent e) {
        lPanel.setSize(this.getWidth(),lPanel.getHeight());
        if( 870 + 60 < this.getWidth()){
            //System.out.println(this.getWidth() - 50 + "    " + (int)pHelp.getLocation().getY());
            pHelp.setLocation(this.getWidth() - 60,(int)pHelp.getLocation().getY());
            pMaximize.setLocation(this.getWidth() - 35,(int)pMaximize.getLocation().getY());                
        }    
    }

    private void pFun_mouseClicked(MouseEvent e) {
        pFun.setEnabled(false);
        if(!pFun.isEnabled())
            return;      
        FuncSelection tmp = new FuncSelection(this,"Select Function",true,true,con,treeID,ID2,String.valueOf(id_comp),typeOfEditor,nodeNameSyntax);
        tmp.setVisible(true);              
    }


    private void jmiExecutionB_actionPerformed(ActionEvent e) {
        pBlock_mouseClicked(null);  
    }

    private void jmiFor_actionPerformed(ActionEvent e) {
        pFor_mouseClicked(null);
    }

    private void jmiWhileDo_actionPerformed(ActionEvent e) {
        pWhile_mouseClicked(null);
    }

    private void jmiDoWhile_actionPerformed(ActionEvent e) {
        pDoWhile_mouseClicked(null);
    }

    private void jmiIf_actionPerformed(ActionEvent e) {
        pIf_mouseClicked(null);
    }

    private void jmiElse_actionPerformed(ActionEvent e) {
        pElse_mouseClicked(null);
    }

    private void jmiElseIf_actionPerformed(ActionEvent e) {
        pElseIf_mouseClicked(null);
    }

    private void jmiDeclaration_actionPerformed(ActionEvent e) {
        pDeclarationB_mouseClicked(null);
    }

    private void jmitry_actionPerformed(ActionEvent e) {
        pTry_mouseClicked(null);
    }

    private void jmiCatch_actionPerformed(ActionEvent e) {
        pCatch_mouseClicked(null);
    }

    private void jmiFinally_actionPerformed(ActionEvent e) {
        pFinally_mouseClicked(null);
    }

    private void jmiLocalFun_actionPerformed(ActionEvent e) {
        pLocalFunction_mouseClicked(null);
    }

    private void jmiExpr_actionPerformed(ActionEvent e) {
        pExp_mouseClicked(null);
    }

    private void jmiDeclarationE_actionPerformed(ActionEvent e) {
        pDeclaration_mouseClicked(null);
    }

    private void jmiArgs_actionPerformed(ActionEvent e) {
        pLocalFuncArgs_mouseClicked(null);
    }

    private void jjmiComments_actionPerformed(ActionEvent e) {
        pComment_mouseClicked(null);
    }

    private void jmiFun_actionPerformed(ActionEvent e) {
        pFun_mouseClicked(null);
    }


    private void jPanel1_mouseClicked(MouseEvent e) {
        ddListEditor.setVisible(false);
    }

    private void cmbExp_mouseEntered(MouseEvent e) {
        //ddListEditor.setDataFunEditor(aaa,this.clicked,cmbExp);
         cmbExp.removeAllItems();
         ddListEditor.setTxtFun(tmp1[this.clicked]);
         
         tmp1[clicked].jTextArea1.setText( 
                                 tmp1[clicked].jTextArea1.getText()
                                 .replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("  "," ")
                         );
                         
         //System.out.println(tmp1[clicked].jTextArea1.getText() + ":");

         int poz = tmp1[clicked].jTextArea1.getCaretPosition();
         int pozjednako = tmp1[clicked].jTextArea1.getText().indexOf("=");
         int pozdom = tmp1[clicked].jTextArea1.getText().split("=")[0].indexOf(" ");
         
         //System.out.println(poz);
         //System.out.println(pozjednako);
         //System.out.println(pozdom);            
         
         if (   
                     (   aaa[clicked].group == -5 &&
                         (   aaa[clicked].name.trim().equals("Variable") || 
                             aaa[clicked].name.trim().equals("Constant")
                         ) 
                     ) || 
                     ( aaa[clicked].group == -8)
               ){
                 if( (pozdom >= poz )|| pozdom == -1){
                     ddListEditor.setDataDomain(cmbExp);      
                 }
                 else if(poz > pozdom && (poz < pozjednako || pozjednako == -1))
                     return;
                 else{
                     ddListEditor.setDataFunEditor(aaa,clicked,cmbExp);
                     ddListEditor.setTxtFun(tmp1[clicked]);
                 }                        
               }
         else{
             ddListEditor.setDataFunEditor(aaa,clicked,cmbExp);
             ddListEditor.setTxtFun(tmp1[clicked]);
         }        
        
        //ddListEditor.setDataFunEditor(this.aaa,this.clicked,cmbExp);
        //ddListEditor.setTxtFun(this.tmp1[this.clicked]);
    }
    
    /*

    private void setDataFunEditor (node[] a,int poz) {
        cmbExp.removeAllItems();
        int level = 0;
        boolean ok = true;

        String tmpEx = "";//dropDownString();

        int stack[] = new int[a.length];
        int stackno = 0;
        String dec = "";
        int i = 0;
        for(i=0; i< poz; i++){
            if( a[i].type.equals("B") ){
                if(a[i].group != -1){
                    stack[stackno++] = i;
                }
                else
                    stackno--;
            }
            else{
                if( a[i].group == -5){
                    for(int j=0; j< stackno; j++){
                        dec += stack[j] + ":";
                    }
                    dec += i + "\n";
                }
                else if( a[i].group == -88){
                    for(int j=0; j< stackno - 1; j++){
                        dec += stack[j] + ":";
                    }
                    dec += i + "\n";
                }
            }

        }
        String last = "";
        for(int j=0; j< stackno; j++){
            last += stack[j] + ":";
        }
        last += i + "\n";
        System.out.println(dec);
        System.out.println(last);

        for(int j= last.split(":").length - 2; j>=0; j-- ){
            for(int k=0;k < dec.split("\n").length; k++){

                //System.out.println("J:" + j)    ;
                //System.out.println("K:" + k)    ;
                //System.out.println(" :" + dec.split("\n")[k])    ;
                //System.out.println(" :" + dec.split("\n")[k].split(":").length)    ;
                //System.out.println(" :" + (dec.split("\n")[k].split(":").length + 2))    ;

                if(j + 1>= (dec.split("\n")[k].split(":").length ) )
                    continue;

                //System.out.println(a[Integer.parseInt(dec.split("\n")[k].split(":")[j])].group);
                //System.out.println(a[Integer.parseInt(last.split(":")[j])].group);
                //System.out.println(a[Integer.parseInt(dec.split("\n")[k].split(":")[j+1]) ].group);
                if(a[Integer.parseInt(dec.split("\n")[k].split(":")[j])].group == a[Integer.parseInt(last.split(":")[j])].group
                && a[Integer.parseInt(dec.split("\n")[k].split(":")[j+1]) ].group == 5 )
                {
                    String tmp = "";
                    if(a[Integer.parseInt(dec.split("\n")[k].split(":")[j+2]) ].group == -88){
                        tmp += "( ";
                        System.out.println(Integer.parseInt(dec.split("\n")[k].split(":")[j+2]));
                        System.out.println((Integer.parseInt(dec.split("\n")[k].split(":")[j+2]) + 2));
                        int t = Integer.parseInt(dec.split("\n")[k].split(":")[j+2]) + 2;
                        System.out.println("T:" + t);
                        while(a[t].group == -8 ){
                            tmp += a[t].value + " ,";
                            t++;
                        }
                        tmp = tmp.substring(0,tmp.length() - 1) + ")";
                    }
                    String tmpa = a[Integer.parseInt(dec.split("\n")[k].split(":")[j+2]) ].value + " " + tmp;
                    if(tmpa.startsWith(tmpEx))
                        cmbExp.addItem( tmpa );
                }
            }
        }
        try{
            JDBCQuery query=new JDBCQuery(con);
            JDBCQuery query2=new JDBCQuery(con);
            ResultSet rs,rs1,rs2,rs3;
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

                if((packName + funName).startsWith(tmpEx))
                    cmbExp.addItem(packName + funName + params + " | " + domName);
                rs2.close();
            }

            rs.close();
            //if(listModel.getSize() == 0)
            //    listModel.addElement(" | No Available Elements ");
        }
        catch(SQLException ex){
            System.out.println("exDDLIST:" + ex);
        }
        //listModel.addElement("aaa2 | bbb2");
    }
    */

    private void cmbExp_actionPerformed(ActionEvent e) {
        if(e.getModifiers() == 16 && cmbExp.getSelectedIndex() >= 0 && !cmbExp.getSelectedItem().toString().equals("")){
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
            if(cmbExp.getSelectedItem().toString().split("\\|")[0].length() >= tmpEx.length()){
                String ggg[] = cmbExp.getSelectedItem().toString().split("\\|");
                int posS = 0;
                if(ggg.length == 3)
                    posS = 1;                 
                tmps = cmbExp.getSelectedItem().toString().split("\\|")[posS].substring(tmpEx.length()).trim();
            }
            
            tmp1[this.clicked].jTextArea1.setText( 
                 tmp1[this.clicked].jTextArea1.getText().substring(0,tmpCaret) + tmps +  tmp1[this.clicked].jTextArea1.getText().substring(tmpCaret ,  tmp1[this.clicked].jTextArea1.getText().length() ));
            tmp1[this.clicked].my.aaa[this.clicked].value = tmp1[this.clicked].jTextArea1.getText();
            //tmp1[this.clicked].my.txtExpression.setText(tmp1[this.clicked].my.print(1).trim());

             tmp1[this.clicked].jTextArea1.requestFocus();
             tmp1[this.clicked].jTextArea1.setText(tmp1[this.clicked].jTextArea1.getText().replaceAll("\r",""));               
             tmp1[this.clicked].jTextArea1.setCaretPosition(tmpCaret + tmps.length());                  
        }    
    }

    private void jsWidth_mouseReleased(MouseEvent e) {
        glWidth2 = jsWidth.getValue();
        design(false);
    }
}


