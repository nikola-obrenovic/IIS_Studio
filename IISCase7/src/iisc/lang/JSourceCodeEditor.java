package iisc.lang;

import iisc.IISFrameMain;

import iisc.LookScript;
import iisc.Settings;

import java.awt.BorderLayout;
import java.awt.*;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.image.ImageObserver;

import java.io.*;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JFileChooser;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.tree.DefaultTreeModel;

import javax.swing.tree.TreeCellRenderer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import iisc.lang.vmashine.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.sql.PreparedStatement;

import java.sql.Savepoint;

import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JPopupMenu;

import javax.swing.JRootPane;

import javax.swing.JTextArea;

import javax.swing.JTextPane;
import javax.swing.JToolBar;

import javax.swing.border.EtchedBorder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.OutputKeys;

import org.apache.xerces.dom.ElementImpl;

import org.gjt.sp.jedit.textarea.StandaloneTextArea;

import org.w3c.dom.Element;


public class JSourceCodeEditor extends JDialog implements VarListener, IVmContextProvider
{
    
    private ImageIcon diskIcon = new ImageIcon(IISFrameMain.class.getResource("icons/disk.png"));
    private ImageIcon openIcon = new ImageIcon(IISFrameMain.class.getResource("icons/folder.png"));
    private ImageIcon buildIcon = new ImageIcon(IISFrameMain.class.getResource("icons/cog.png"));
    private ImageIcon dbSaveIcon = new ImageIcon(IISFrameMain.class.getResource("icons/database_save.png"));
    private ImageIcon settingsIcon = new ImageIcon(IISFrameMain.class.getResource("icons/hammer_screwdriver.png"));
    private ImageIcon bulletGoIcon = new ImageIcon(IISFrameMain.class.getResource("icons/bullet_go.png"));
    private ImageIcon bugIcon = new ImageIcon(IISFrameMain.class.getResource("icons/bug.png"));
    private ImageIcon stopIcon = new ImageIcon(IISFrameMain.class.getResource("icons/stop_16.png"));
    private ImageIcon nextStepIcon = new ImageIcon(IISFrameMain.class.getResource("icons/next_step.png"));
    private ImageIcon buildRunIcon = new ImageIcon(IISFrameMain.class.getResource("icons/cog_go.png"));
    private ImageIcon resumeIcon = new ImageIcon(IISFrameMain.class.getResource("icons/control_fastforward_blue.png"));
    private ImageIcon pauseIcon = new ImageIcon(IISFrameMain.class.getResource("icons/control_pause_blue.png"));
    private ImageIcon undoIcon = new ImageIcon(IISFrameMain.class.getResource("icons/arrow_undo.png"));
    private ImageIcon redoIcon = new ImageIcon(IISFrameMain.class.getResource("icons/arrow_redo.png"));
    private ImageIcon cutIcon = new ImageIcon(IISFrameMain.class.getResource("icons/cut2.png"));
    private ImageIcon copyIcon = new ImageIcon(IISFrameMain.class.getResource("icons/page_copy.png"));
    private ImageIcon pasteIcon = new ImageIcon(IISFrameMain.class.getResource("icons/page_paste.png"));    
    private ImageIcon closelIcon = new ImageIcon(IISFrameMain.class.getResource("icons/close.png"));    
    private ImageIcon textEditorIcon = new ImageIcon(IISFrameMain.class.getResource("icons/page_white_text.png"));
    private ImageIcon treeEditorIcon = new ImageIcon(IISFrameMain.class.getResource("icons/tree.png"));
    private ImageIcon commentIcon = new ImageIcon(IISFrameMain.class.getResource("icons/text_padding_left.png"));
    private ImageIcon uncommentIcon = new ImageIcon(IISFrameMain.class.getResource("icons/text_padding_right.png"));
    private ImageIcon indentIcon = new ImageIcon(IISFrameMain.class.getResource("icons/text_indent.png"));
    private ImageIcon outdentIcon = new ImageIcon(IISFrameMain.class.getResource("icons/text_indent_remove.png"));
    private ImageIcon toggleBreakpointIcon = new ImageIcon(IISFrameMain.class.getResource("icons/timeline_marker.png"));
    
    //StandaloneTextArea area;
    MyTextArea area;
    public IISCaseLangParser parser;
    public VirtualMashine vMashine = null;
    ArrayList watchVars = new ArrayList();
    ArrayList packages = new ArrayList();
    
    private Connection con;
    private Connection exec_con;
    private String drivers = "sun.jdbc.odbc.JdbcOdbcDriver";
    
    private JMenuBar jMenuBar1 = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem openMI = new JMenuItem("Open file");
    JMenuItem saveMI = new JMenuItem("Save to file");
    JMenuItem saveDBMI = new JMenuItem("Save to repository");
    JMenuItem closeMI = new JMenuItem("Exit");
    
    JMenu editMenu = new JMenu("Edit");
    JMenuItem undoMI = new JMenuItem("Undo");
    JMenuItem redoMI = new JMenuItem("Redo");    
    
    JMenuItem cutMI = new JMenuItem("Cut");
    JMenuItem copyMI = new JMenuItem("Copy");
    JMenuItem pasteMI = new JMenuItem("Paste");
    
    JMenuItem indentMI = new JMenuItem("Increase Indent");
    JMenuItem decraseMI = new JMenuItem("Decrease Indent");
    JMenuItem commentMI = new JMenuItem("Comment Selection");
    JMenuItem uncommentMI = new JMenuItem("Uncomment Selection");
    
    JMenu viewMenu = new JMenu("View");
    JCheckBoxMenuItem  errorListMI = new JCheckBoxMenuItem("Errors");
    JCheckBoxMenuItem  outputMI = new JCheckBoxMenuItem("Output");
    JCheckBoxMenuItem  watchMI = new JCheckBoxMenuItem("Watch");
    
    JMenu compAndRunMenu = new JMenu("Compile&Run");
    JMenuItem compiledMI = new JMenuItem("Compile");
    JMenuItem runMI = new JMenuItem("Run");
    JMenuItem compileRunMI = new JMenuItem("Compile&Run");
    JMenuItem startDebugMI = new JMenuItem("Start Debug");
    JMenuItem stopDebugMI = new JMenuItem("Stop Debug");
    JMenuItem nextCommMI = new JMenuItem("Next Command");
    JMenuItem addToWatchMI = new JMenuItem("Add Watch");
    JMenuItem removeWatchMI = new JMenuItem("Remove Watch");
    JMenuItem removeAllWatchMI = new JMenuItem("Remove All Watches");
    JMenuItem editWatchMI = new JMenuItem("Edit Watch");
    JMenuItem addBreakPointMI = new JMenuItem("Add Breakpoint");
    JMenuItem removeBreakPointMI = new JMenuItem("Remove Breakpoint");
    JMenuItem toggleBreakPointMI = new JMenuItem("Toggle Breakpoint");
    JMenuItem resumeMI = new JMenuItem("Resume Execution");
    
    JMenuItem popUpaddToWatchMI = new JMenuItem("Add Watch");
    JMenuItem popUPRemoveWatchMI = new JMenuItem("Remove Watch");
    JMenuItem popUPRemoveAllWatchMI = new JMenuItem("Remove All");
    JMenuItem popUPEditWatchMI = new JMenuItem("Edit Watch");
    
    JMenu toolsMenu = new JMenu("Tools");
    JMenu convertMenu = new JMenu("Convert");
    JMenuItem plslqMI = new JMenuItem("PLSql");
    JMenuItem settingsMI = new JMenuItem("Settings");
    JMenuItem switchToEditor = new JMenuItem("Switch to Tree Editor");
    
    private BorderLayout borderLayout1 = new BorderLayout();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JTabbedPane treePanel = new JTabbedPane();
    private JPanel editorPanel = new JPanel();
    private JPanel toolBarPanel = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private BorderLayout borderLayout2 = new BorderLayout();
    private BorderLayout borderLayout3 = new BorderLayout();
    private BorderLayout borderLayout4 = new BorderLayout();
    private JPanel editorAndTreePanel = new JPanel();
    private JPanel errPanel = new JPanel();
    //private JPanel treePanel = new JPanel();
    private JPanel testPanel = new JPanel();
    private JSplitPane jSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorAndTreePanel, jTabbedPane1);
    private JSplitPane jSplitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editorPanel, treePanel);
    
    ImagePanel pSave;
    ImagePanel pOpen;
    ImagePanel pDbSave;
    ImagePanel pUndo;
    ImagePanel pRedo;
    ImagePanel pCut;
    ImagePanel pCopy;
    ImagePanel pPaste;
    ImagePanel pBuild;    
    ImagePanel pSettings;
    ImagePanel pGo;
    ImagePanel pBuildGo;
    ImagePanel pStartDebug;
    ImagePanel pStopDebug;
    ImagePanel pNextComm;
    ImagePanel pResume;
    ImagePanel pToggle;
    ImagePanel pTextEditor;
    ImagePanel pTreeEditor;
    ImagePanel pComment;
    ImagePanel pUncomment;
    ImagePanel pIndent;
    ImagePanel pOutdent;
    String filename = "other_stat.txt";
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane outputScrollPane = new JScrollPane();
    private JScrollPane watchScrollPane = new JScrollPane();
    private TextArea outputTextArea = new TextArea();
    private BorderLayout borderLayout5 = new BorderLayout();
    private ErrorTableModel errTableModel = new ErrorTableModel();
    private JTable errorTable = new JTable(errTableModel);
    private WatchTableModel watchTableModel = new WatchTableModel(watchVars);
    private JTable watchTable = new JTable(watchTableModel);
    private BorderLayout borderLayout6 = new BorderLayout();
    JPopupMenu watchPopupMenu = new JPopupMenu();
    
    public ArrayList domains = new ArrayList();
    public ArrayList userDefDomains = new ArrayList();
    public ArrayList functions = new ArrayList();
    public ArrayList currVars = new ArrayList();
    
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTree tree = new JTree();
    private JTree infoTree = new JTree();
    private CommandTree commTree = null;
    public IISFrameMain parent = null;
    
    private int prevHeight = 500;
    private int prevWidth = 800;
    public int PR_id = 3;
    public boolean isDebug = false;
    private boolean isStandalone = false;
    
    Document errDoc = null;
    Element errRoot = null;
    Document funcDoc = null;
    Element funcRoot = null;
    Element currentFunctionNode = null;
    String funcName = "";
    String func_id = "";
    boolean isTreeEditorActive = false; 
    boolean isTextEditorActive = true;
    Hashtable breakpoints = new Hashtable();
    
    public JSourceCodeEditor(String func_id)
    {
        try 
        {
            this.func_id = func_id;            
            Connect();
            this.funcName = LoadFuncNameFromRepository(this.PR_id, this.func_id, this.con);
            jbInit();
            this.commTree.InitTree(this.parser.getAstXml());
            
            /*this.breakpoints.put(24, new Integer(24));
            this.breakpoints.put(29, new Integer(29));*/
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public JSourceCodeEditor(IISFrameMain parent, String func_id, Connection con, int PR_id)
    {
        super(parent, "Function", true);
        //super("Function", true, true, true);
        
        try 
        {
            this.isStandalone = true;
            this.func_id = func_id;            
            this.con = con;
            this.parent = parent;
            OpenExecConn();
            this.PR_id = PR_id;
            this.funcName = LoadFuncNameFromRepository(this.PR_id, this.func_id, this.con);
            this.setTitle(this.funcName);
            jbInit();            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void Connect()
    {
        String url = "jdbc:odbc:isscase_new";
        
        try
        {
            Class.forName(drivers);
            con = (Connection)DriverManager.getConnection(url, "", "");
        }
        catch (ClassNotFoundException ef) 
        {
            JOptionPane.showMessageDialog(null, "ClassNotFoundException:    " + ef.getMessage() , "Connection error", JOptionPane.ERROR_MESSAGE);
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "<html><center>This is not valid repository!", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
        
        OpenExecConn();
    }
    
    private void OpenExecConn()
    {
        if (con != null)
        {
            try 
            {
                String conname = "";
                
                File fXmlFile = new File("editor_settings.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);
                                
                Element settingRoot = doc.getDocumentElement();
                
                for(int i = 0; i < settingRoot.getChildNodes().getLength(); i++)
                {
                    Node propNode = settingRoot.getChildNodes().item(i);
                    
                    if (propNode.getNodeType() == Node.ELEMENT_NODE)
                    {
                        String propName = propNode.getNodeName();
                        String propValue = SemAnalyzer.getTextContent(propNode);
                        
                        if (propName.equalsIgnoreCase("nativesqlconn"))
                        {
                            conname = propValue;
                            break;
                        }
                    }
                }
                 
                if (conname != null ||  conname.length() > 0)
                {
                
                    String url = "jdbc:odbc:" + conname;
                    
                    try
                    {
                        Class.forName(drivers);
                        this.exec_con = (Connection)DriverManager.getConnection(url, "", "");
                    }
                    catch (ClassNotFoundException ef) 
                    {
                        //JOptionPane.showMessageDialog(null, "ClassNotFoundException:    " + ef.getMessage() , "Connection error", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(SQLException ex)
                    {
                        //JOptionPane.showMessageDialog(null, "<html><center>This is not valid repository!", "Connection Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            catch(Exception e)
            {}
        }
    }
    
    public static void lookFeel()
    {
        try
        {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        }
        catch ( Exception e ) 
        {
            e.printStackTrace();
        }
    }
    /*
    public static void main(String[] args)
    {
        lookFeel();        
        JSourceCodeEditor editor = new JSourceCodeEditor("17");
        Settings.Center(editor);
        editor.setVisible(true);
    }*/
    
    private void this_windowClosing(WindowEvent e) 
    {
        this.setVisible(false);
        this.dispose();
       
    }
    
    private void jbInit() throws Exception 
    {
        /*this.addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                this_windowClosing(e);
            }
        });*/
                
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(borderLayout1);
        this.setSize(1200,700);
        this.setResizable(true);
        
        //tree
        InitInfoTree();
        
        this.editorAndTreePanel.setLayout(this.borderLayout6);
        this.editorAndTreePanel.add(this.jSplitPane2);
        setJMenuBar(jMenuBar1);
      
        //Editor
        area = new MyTextArea(this.con, this.domains, this.userDefDomains, this, this.parent);
        
        this.editorPanel.setLayout(this.borderLayout4);
        this.editorPanel.add(area, BorderLayout.CENTER);
        this.editorPanel.setBackground(Color.white);
        
        //Greske
        this.errPanel.setLayout(borderLayout5); 
        this.errPanel.add(errorTable, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(errPanel, null);
        jTabbedPane1.addTab("Errors", jScrollPane1);
        
        this.outputScrollPane.getViewport().add(this.outputTextArea, null);
        jTabbedPane1.addTab("Output", this.outputTextArea);
        
        this.watchScrollPane.getViewport().add(this.watchTable, null);
        jTabbedPane1.addTab("Watch", this.watchScrollPane);
        
        this.getContentPane().add(jSplitPane1, BorderLayout.CENTER);
        this.jSplitPane1.setDividerLocation(2 * (int)(this.getHeight() /3));
        //this.jSplitPane1.setDividerLocation(375);
        this.toolBarPanel.setPreferredSize(new Dimension(this.getWidth(), 25));
        this.getContentPane().add(toolBarPanel, BorderLayout.NORTH);
        this.toolBarPanel.setLayout(null);
        
        JScrollPane jScrollPane3 = new JScrollPane();
        jScrollPane3.getViewport().add(infoTree, null);
        this.treePanel.addTab("Project info", jScrollPane3);
        jScrollPane2.getViewport().add(tree, null);
        tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("")));
        //this.treePanel.setLayout(new BorderLayout());
        //this.treePanel.addTab("Sem. Analisys", this.jScrollPane2);
        
        this.commTree = new CommandTree("func", this.area, this.PR_id);
        JScrollPane jScrollPane4 = new JScrollPane();
        jScrollPane4.getViewport().add(this.commTree, null);
        this.treePanel.addTab("Tree editor", jScrollPane4);
        
        this.treePanel.setSelectedIndex(1);
        
        //this.toolBarPanel.setLayout(new BorderLayout());
         //this.toolBarPanel.setLayout(null);
        /*toolBarPanel.add(pOpen);
        pOpen.setEnabled(false);*/
        
        int currPos = 3;
         
        pOpen = new ImagePanel(openIcon,3,3);
        pOpen.setBorder(BorderFactory.createEmptyBorder());
        pOpen.setToolTipText("Open text from file");
        pOpen.setBounds(currPos,2,22,22);
         
        pOpen.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                Open();
            }
            
        });
        toolBarPanel.add(pOpen);
         
         currPos += 23;
        
        pSave = new ImagePanel(diskIcon,3,3);
        pSave.setBorder(BorderFactory.createEmptyBorder());
        pSave.setToolTipText("Save to file");
        pSave.setBounds(currPos,2,22,22);
        
        pSave.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                Save();
            }
            
        });
        toolBarPanel.add(pSave);
        
        currPos += 23;
        
        pDbSave = new ImagePanel(dbSaveIcon,3,3);
        pDbSave.setBorder(BorderFactory.createEmptyBorder());
        pDbSave.setToolTipText("Build");
        pDbSave.setBounds(currPos,2,22,22);
        pDbSave.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                DbSave();
            }
            
        });
        toolBarPanel.add(pDbSave);
        
        currPos += 23;
        
        JPanel sepPanel = new JPanel();
        sepPanel.setBackground(new Color(212, 208, 200));
        sepPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        sepPanel.setBounds(currPos,2,3,22);
        toolBarPanel.add(sepPanel);
        
        currPos +=3;
        
        pUndo = new ImagePanel(undoIcon,3,5);
        pUndo.setBorder(BorderFactory.createEmptyBorder());
        pUndo.setToolTipText("Undo");
        pUndo.setBounds(currPos,2,22,22);
        pUndo.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                Undo();     
            }
        });
        
        toolBarPanel.add(pUndo);        
        currPos += 23;
        
        pRedo = new ImagePanel(redoIcon,3,5);
        pRedo.setBorder(BorderFactory.createEmptyBorder());
        pRedo.setToolTipText("Redo");
        pRedo.setBounds(currPos,2,22,22);
        pRedo.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                Redo();     
            }
        });
        
        toolBarPanel.add(pRedo);        
        currPos += 23;
        
        pCut = new ImagePanel(cutIcon,3,5);
        pCut.setBorder(BorderFactory.createEmptyBorder());
        pCut.setToolTipText("Cut");
        pCut.setBounds(currPos,2,22,22);
        pCut.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                Cut();     
            }
        });
        
        toolBarPanel.add(pCut);        
        currPos += 23;
        
        pCopy = new ImagePanel(copyIcon,3,5);
        pCopy.setBorder(BorderFactory.createEmptyBorder());
        pCopy.setToolTipText("Copy");
        pCopy.setBounds(currPos,2,22,22);
        pCopy.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                Copy();     
            }
        });
        
        toolBarPanel.add(pCopy);        
        currPos += 23;
        
        pPaste = new ImagePanel(pasteIcon,3,5);
        pPaste.setBorder(BorderFactory.createEmptyBorder());
        pPaste.setToolTipText("Paste");
        pPaste.setBounds(currPos,2,22,22);
        pPaste.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                Paste();     
            }
        });
        
        toolBarPanel.add(pPaste);        
        currPos += 23;
        
        sepPanel = new JPanel();        
        sepPanel.setBackground(new Color(212, 208, 200));
        sepPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        sepPanel.setBounds(currPos,2,3,22);
        toolBarPanel.add(sepPanel);
        
        currPos +=3;
        
        pComment = new ImagePanel(commentIcon,5,5);
        pComment.setBorder(BorderFactory.createEmptyBorder());
        pComment.setToolTipText("Comment out selected lines");
        pComment.setBounds(currPos,2,22,22);
        pComment.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                Comment();     
            }
        });
        
        toolBarPanel.add(pComment);        
        currPos += 23;
        
        pUncomment = new ImagePanel(uncommentIcon,5,5);
        pUncomment.setBorder(BorderFactory.createEmptyBorder());
        pUncomment.setToolTipText("Uncomment selected lines");
        pUncomment.setBounds(currPos,2,22,22);
        pUncomment.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                UnComment();
            }
        });
        
        toolBarPanel.add(pUncomment);        
        currPos += 23;
        
        pIndent = new ImagePanel(indentIcon,5,5);
        pIndent.setBorder(BorderFactory.createEmptyBorder());
        pIndent.setToolTipText("Increase indent");
        pIndent.setBounds(currPos,2,22,22);
        pIndent.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                //area.ExecIndent(false);     
                Indent(false);
            }
        });
        
        toolBarPanel.add(pIndent);        
        currPos += 23;
        
        pOutdent = new ImagePanel(outdentIcon,5,5);
        pOutdent.setBorder(BorderFactory.createEmptyBorder());
        pOutdent.setToolTipText("Decrease indent");
        pOutdent.setBounds(currPos,2,22,22);
        pOutdent.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                //area.ExecIndent(true);      
                Indent(true);
            }
        });
        
        toolBarPanel.add(pOutdent);        
        currPos += 23;
        
        sepPanel = new JPanel();        
        sepPanel.setBackground(new Color(212, 208, 200));
        sepPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        sepPanel.setBounds(currPos,2,3,22);
        toolBarPanel.add(sepPanel);
        
        currPos +=3;
        
        pBuild = new ImagePanel(buildIcon,2,3);
        pBuild.setBorder(BorderFactory.createEmptyBorder());
        pBuild.setToolTipText("Compile");
        pBuild.setBounds(currPos,2,23,22);
        pBuild.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                Build();     
            }
            
        });
        
        toolBarPanel.add(pBuild);        
        currPos += 23;
        
        pGo = new ImagePanel(bulletGoIcon,3,3);
        pGo.setBorder(BorderFactory.createEmptyBorder());
        pGo.setToolTipText("Run");
        pGo.setBounds(currPos,2,22,22);
        pGo.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                ExecuteFunction();
            }
        });
        
        toolBarPanel.add(pGo);        
        currPos += 23;
        
        pBuildGo = new ImagePanel(buildRunIcon,3,3);
        pBuildGo.setBorder(BorderFactory.createEmptyBorder());
        pBuildGo.setToolTipText("Compile&Run");
        pBuildGo.setBounds(currPos,2,22,22);
        pBuildGo.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                BuildAndRun();
            }
        });
        
        toolBarPanel.add(pBuildGo);        
        currPos += 23;
        
        sepPanel = new JPanel();        
        sepPanel.setBackground(new Color(212, 208, 200));
        sepPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        sepPanel.setBounds(currPos,2,3,22);
        toolBarPanel.add(sepPanel);
        
        currPos +=3;
        
        pStartDebug = new ImagePanel(bugIcon,3,3);
        pStartDebug.setBorder(BorderFactory.createEmptyBorder());
        pStartDebug.setToolTipText("Start debug");
        pStartDebug.setBounds(currPos,2,22,22);
        pStartDebug.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                StartDebug();
            }
            
        });
        
        toolBarPanel.add(pStartDebug);
        
        currPos += 23;
        
        pStopDebug = new ImagePanel(stopIcon,3,3);
        pStopDebug.setBorder(BorderFactory.createEmptyBorder());
        pStopDebug.setToolTipText("Stop debug");
        pStopDebug.setBounds(currPos,2,22,22);
        
        pStopDebug.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                StopDebug();
            }
        });
        pStopDebug.setEnabled(false);
        toolBarPanel.add(pStopDebug);
        
        currPos += 23;
        
        pNextComm = new ImagePanel(nextStepIcon,3,3);
        pNextComm.setBorder(BorderFactory.createEmptyBorder());
        pNextComm.setToolTipText("Next command");
        pNextComm.setBounds(currPos,2,22,22);
        pNextComm.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                NextCommand();
            }
        });
        pNextComm.setEnabled(false);
        toolBarPanel.add(pNextComm);
        
        currPos += 23;
        
        pResume = new ImagePanel(resumeIcon,3,3);
        pResume.setBorder(BorderFactory.createEmptyBorder());
        pResume.setToolTipText("Resume execution");
        pResume.setBounds(currPos,2,22,22);
        pResume.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                NextBreakPointCommand();
            }
        });
        pResume.setEnabled(false);
        
        toolBarPanel.add(pResume);
        currPos += 23;
        
        pToggle = new ImagePanel(toggleBreakpointIcon,3,3);
        pToggle.setBorder(BorderFactory.createEmptyBorder());
        pToggle.setToolTipText("Toggle Breakpoint");
        pToggle.setBounds(currPos,2,22,22);
        pToggle.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                ToggleBreakPoint();
            }
        });
        pToggle.setEnabled(true);
        
        toolBarPanel.add(pToggle);
        currPos += 23;
        
        sepPanel = new JPanel();        
        sepPanel.setBackground(new Color(212, 208, 200));
        sepPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        sepPanel.setBounds(currPos,2,3,22);
        toolBarPanel.add(sepPanel);
        
        currPos +=3;
        
        pTextEditor = new ImagePanel(textEditorIcon,3,3);
        pTextEditor.setBorder(BorderFactory.createEmptyBorder());
        pTextEditor.setToolTipText("Text editor");
        pTextEditor.setBounds(currPos,2,22,22);
        pTextEditor.removeMouseListener(pTextEditor);
        pTextEditor.addMouseListener(new MouseListener()
        {

                    public void mouseClicked(MouseEvent e) 
                    {
                        isTreeEditorActive = true;
                        switchEditorActionPerformed();
                        pTextEditor.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                        pTreeEditor.setBorder(BorderFactory.createEmptyBorder());
                    }

                    public void mousePressed(MouseEvent e) {
                    }

                    public void mouseReleased(MouseEvent e) {
                    }

                    public void mouseEntered(MouseEvent e) {
                    }

                    public void mouseExited(MouseEvent e) {
                    }
                });
        
        toolBarPanel.add(pTextEditor);
        currPos += 23;
        
        pTreeEditor = new ImagePanel(treeEditorIcon,3,3);
        pTreeEditor.setBorder(BorderFactory.createEmptyBorder());
        pTreeEditor.setToolTipText("Tree editor");
        pTreeEditor.setBounds(currPos,2,22,22);
        pTreeEditor.removeMouseListener(pTreeEditor);
        pTreeEditor.addMouseListener(new MouseListener()
        {

                     public void mouseClicked(MouseEvent e) 
                     {
                         isTreeEditorActive = false;
                         switchEditorActionPerformed();
                         pTreeEditor.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                         pTextEditor.setBorder(BorderFactory.createEmptyBorder());
                     }

                     public void mousePressed(MouseEvent e) {
                     }

                     public void mouseReleased(MouseEvent e) {
                     }

                     public void mouseEntered(MouseEvent e) {
                     }

                     public void mouseExited(MouseEvent e) {
                     }
                 });
                 
        toolBarPanel.add(pTreeEditor);
        currPos += 23;
        
        isTreeEditorActive = true;
        switchEditorActionPerformed();
        pTextEditor.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        pTreeEditor.setBorder(BorderFactory.createEmptyBorder());
        
        sepPanel = new JPanel();        
        sepPanel.setBackground(new Color(212, 208, 200));
        sepPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        sepPanel.setBounds(currPos,2,3,22);
        toolBarPanel.add(sepPanel);
        
        currPos +=3;
        
        pSettings = new ImagePanel(settingsIcon,3,3);
        pSettings.setBorder(BorderFactory.createEmptyBorder());
        pSettings.setToolTipText("Settings");
        pSettings.setBounds(currPos,2,22,22);
        pSettings.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                ShowSettings();
            }
        });
        
        toolBarPanel.add(pSettings);
        
        currPos += 23;
        
        InitMenu();
        InitFuncXML(funcName);
        
        InitTextArea();
        InitErrorsXML();
        LoadWatchList();
        
        this.errorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.errorTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                tableValueChanged(e);
            }
        });
        
        this.addComponentListener(new ComponentAdapter() {
                    public void componentResized(ComponentEvent e) {
                        this_componentResized(e);
                    }
                });
        
        Build();
        
        this.writeXmlFile(this.funcDoc, "functions_proba.xml");
    }
    
    private void Undo()
    {
        if (!pUndo.isEnabled())
        {
            return;
        }
        
        this.area.getBuffer().undo(this.area);
    }
    
    private void Redo()
    {
        if (!pRedo.isEnabled())
        {
            return;
        }
        
        this.area.getBuffer().redo(this.area);
    }
    
    private void Cut()
    {
        if (!pCut.isEnabled())
        {
            return;
        }
        
        this.area.ExecCut();
    }
    
    private void Copy()
    {
        if (!pCopy.isEnabled())
        {
            return;
        }
        
        this.area.ExecCopy();
    }
    
    private void Paste()
    {
        if (!pPaste.isEnabled())
        {
            return;
        }
        
        this.area.ExecPaste();
    }
    
    private void Comment()
    {
        if (!pComment.isEnabled())
        {
            return;
        }
        
        this.area.ExecComment();
    }
    
    private void UnComment()
    {
        if (!pUncomment.isEnabled())
        {
            return;
        }
        
        this.area.ExecUncomment();
    }
    
    private void Indent(boolean ind)
    {
        if (!pIndent.isEnabled())
        {
            return;
        }
        
        this.area.ExecIndent(ind);
    }
    
    private void InitInfoTree()
    {
        try 
        {
            LoadPredefinedFuncDesc();
            
            if (this.con == null)
            {
                return;
            }
            
            InitDomains(this.con, this.domains, this.userDefDomains, null, null, this.PR_id);
            
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from IISC_FUNCTION where PR_id=" + this.PR_id);
            
            while(rs.next())
            {
                FunctionDesc fd = new FunctionDesc("","");
                
                fd.setName(rs.getString("Fun_name"));
                Statement stmt1 = this.con.createStatement();
                
                ResultSet rs1 = stmt1.executeQuery("select * from IISC_DOMAIN where Dom_id=" + rs.getString("Dom_id"));
                
                if(rs1.next())
                {
                    fd.setDomName(rs1.getString("Dom_mnem"));
                }
                rs1.close();
                stmt1.close();
                
                stmt1 = this.con.createStatement();
                rs1 = stmt1.executeQuery("select * from IISC_FUN_PARAM where Fun_id=" + rs.getString("Fun_id"));
                
                while(rs1.next())
                {
                    ParamDesc p = new ParamDesc("","");
                    
                    p.setName(rs1.getString("Param_name"));
                    p.setSeqNumber(rs1.getInt("Param_seq"));
                    p.setType(rs1.getInt("inout"));
                    
                    Statement stmt3 = this.con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("select * from IISC_DOMAIN where Dom_id=" + rs1.getString("Dom_id"));
                    
                    if(rs3.next())
                    {
                        p.setDomName(rs3.getString("Dom_mnem"));
                    }
                    
                    fd.getParams().add(p);
                }
                        
                this.functions.add(fd);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        DefaultMutableTreeNode infoTreeRoot = new DefaultMutableTreeNode("Environment");
        DefaultTreeModel trModel=new DefaultTreeModel(infoTreeRoot);
        infoTree = new JTree(trModel);
        infoTree.setCellRenderer(new InfoTreeCustomCellRenderer());
        
        DefaultMutableTreeNode  domainsNode = new DefaultMutableTreeNode("Domains");        
        DefaultMutableTreeNode  functionsNode = new DefaultMutableTreeNode("User-defined Functions");
        DefaultMutableTreeNode  predefinedFuncNode = new DefaultMutableTreeNode("Predefined Functions");
        infoTreeRoot.add(domainsNode);
        infoTreeRoot.add(functionsNode);
        infoTreeRoot.add(predefinedFuncNode);
        
        DefaultMutableTreeNode  userDefDomNode = new DefaultMutableTreeNode("User defined domains");
        DefaultMutableTreeNode  primitiveDomains = new DefaultMutableTreeNode("Primitive domains");        
        domainsNode.add(userDefDomNode);
        domainsNode.add(primitiveDomains);
        
        for(int i = 0; i < domains.size(); i++)
        {
            DomainDesc desc = (DomainDesc)domains.get(i);
            DefaultMutableTreeNode  primDomNode = new DefaultMutableTreeNode(desc.getName());        
            primitiveDomains.add(primDomNode);
        }
        
        for(int i = 0; i < userDefDomains.size(); i++)
        {
            DomainDesc desc = (DomainDesc)userDefDomains.get(i);
            DefaultMutableTreeNode  primDomNode = new DefaultMutableTreeNode(desc.getFullName());        
            userDefDomNode.add(primDomNode);
            
            if (desc.getType() == DomainDesc.TUPPLE || desc.getType() == DomainDesc.CHOICE && desc.getMembers().size() > 0)
            {
                DefaultMutableTreeNode  attNode = new DefaultMutableTreeNode("Attributes");        
                primDomNode.add(attNode);
                
                for(int j = 0; j < desc.getMembers().size(); j++)
                {
                    AttributeDesc aDesc = (AttributeDesc)desc.getMembers().get(j);
                    DefaultMutableTreeNode  aNode = new DefaultMutableTreeNode(aDesc.getName() + " (Domain:" + aDesc.getDomName() + ")");        
                    attNode.add(aNode);
                }
            }
        }
        
        for(int i = 0; i < functions.size(); i++)
        {
            FunctionDesc desc = (FunctionDesc)functions.get(i);
            DefaultMutableTreeNode  primDomNode = new DefaultMutableTreeNode(desc.getDescName());        
            functionsNode.add(primDomNode);
        }
        
        for(int i = 0; i < this.packages.size(); i++)
        {
            PackageDesc desc = (PackageDesc)this.packages.get(i);
            DefaultMutableTreeNode  packageNode = new DefaultMutableTreeNode(desc.name);        
            predefinedFuncNode.add(packageNode);
            
            for(int j = 0; j < desc.func.size(); j++)
            {
                FunctionDesc funcSpec = (FunctionDesc)desc.func.get(j);
                DefaultMutableTreeNode  funNode = new DefaultMutableTreeNode(funcSpec.getDescName());        
                packageNode.add(funNode);
            }
        }
    }
    
    public static void InitDomains(Connection con, ArrayList domains, ArrayList userDefDomains, Hashtable domainsTable, Hashtable userDefDomainsTable, int PR_id)
    {
        try 
        {
            if (con == null)
            {
                return;
            }
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from IISC_PRIMITIVE_TYPE");
            
            while(rs.next())
            {
                DomainDesc d = new DomainDesc(rs.getString("PT_mnemonic"),rs.getString("PT_id"), DomainDesc.PRIMITIVE);
                d.setPrimitiveDomainType(rs.getInt("PT_base_type"));                
                
                if (domains != null)
                {
                    domains.add(d);
                }
                else
                {
                    if (domainsTable != null)
                    {
                        if (!domainsTable.containsKey(d.getName().toLowerCase(Locale.US)))
                        {
                            domainsTable.put(d.getName().toLowerCase(Locale.US), d);
                        }
                    }
                }
            }
            
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from IISC_DOMAIN where PR_id=" + PR_id);
                        
            while(rs.next())
            {
                DomainDesc d = new DomainDesc(rs.getString("Dom_mnem"),rs.getString("Dom_id"), DomainDesc.PRIMITIVE);
                
                int dom_type = rs.getInt("Dom_type");
                
                if (dom_type==0)
                {
                    d.setType(DomainDesc.INHERITED_PRIMITIVE);
                    d.setPrimitiveTypeId(rs.getString("Dom_data_type"));
                    
                    Statement stmt1 = con.createStatement();
                    ResultSet rs1 = stmt1.executeQuery("select * from IISC_PRIMITIVE_TYPE where PT_id=" + d.getPrimitiveTypeId());
                    
                    if(rs1.next())
                    {
                        d.setPrimitiveTypeName(rs1.getString("PT_mnemonic"));
                    }
                }
                else
                {
                    if (dom_type==1)
                    {
                        d.setType(DomainDesc.INHERITED_USER_DEF);
                        
                        Statement stmt1 = con.createStatement();
                        ResultSet rs1 = stmt1.executeQuery("select * from IISC_DOMAIN where Dom_id=" + rs.getString("Dom_parent"));
                        
                        if(rs1.next())
                        {
                            d.setParentName(rs1.getString("Dom_mnem"));
                        }
                    }
                    else
                    {
                        if (dom_type==2 || dom_type==3)
                        {
                            if (dom_type==2)
                            {
                                d.setType(DomainDesc.TUPPLE);
                            }
                            else
                            {
                                d.setType(DomainDesc.CHOICE);
                            }
                            
                            Statement stmt1 = con.createStatement();
                            ResultSet rs1 = stmt1.executeQuery("select * from IISC_DOM_ATT where Dom_id=" + d.getId());
                            
                            while(rs1.next())
                            {
                                String attId = rs1.getString("Att_id");
                                int rbr = rs1.getInt("Att_rbr");
                                
                                Statement stmt2 = con.createStatement();
                                ResultSet rs2 = stmt2.executeQuery("select * from IISC_ATTRIBUTE where Att_id=" + attId);
                                
                                if (rs2.next())
                                {
                                    AttributeDesc aDesc = new AttributeDesc(rs2.getString("Att_mnem"), attId, rs2.getString("Dom_id"), rbr);
                                    
                                    Statement stmt3 = con.createStatement();
                                    ResultSet rs3 = stmt3.executeQuery("select * from IISC_DOMAIN where Dom_id=" + aDesc.getDomId());
                                    
                                    if(rs3.next())
                                    {
                                        aDesc.setDomName(rs3.getString("Dom_mnem"));
                                        d.getMembers().add(aDesc);
                                    }
                                }
                            }
                        }
                        else
                        {                
                            if (dom_type==4)
                            {
                                d.setType(DomainDesc.SET);
                                String domParId = rs.getString("Dom_parent");
                                
                                if ( domParId != null && domParId != "")
                                {
                                    Statement stmt1 = con.createStatement();
                                    ResultSet rs1 = stmt1.executeQuery("select * from IISC_DOMAIN where Dom_id=" + domParId);
                                    
                                    if(rs1.next())
                                    {
                                        d.setParentName(rs1.getString("Dom_mnem"));
                                    }
                                }
                                else
                                {
                                    Statement stmt1 = con.createStatement();
                                    ResultSet rs1 = stmt1.executeQuery("select * from IISC_PRIMITIVE_TYPE where PT_id=" + rs.getString("Dom_data_type"));
                                    
                                    if(rs1.next())
                                    {
                                        d.setParentName(rs1.getString("PT_mnemonic"));
                                    }
                                }
                            }
                        }
                    }
                }
                
                if (userDefDomains != null)
                {
                    userDefDomains.add(d);
                }
                else
                {
                    if (userDefDomainsTable != null)
                    {
                        if (!userDefDomainsTable.containsKey(d.getName().toLowerCase(Locale.US)))
                        {
                            userDefDomainsTable.put(d.getName().toLowerCase(Locale.US), d);
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void writeXmlFile(Document doc, String filename) 
    {
        try 
        {
            // Prepare the DOM document for writing
            Source source = new DOMSource(doc);

            // Prepare the output file
            File file = new File(filename);
            Result result = new StreamResult(file);

            // Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
        } 
        catch (TransformerConfigurationException e) 
        {
            e.printStackTrace();
        } 
        catch (TransformerException e) 
        {
            e.printStackTrace();
        }
    }
    
    public void Build()
    {
        BuildWithoutBuilidgTree();
        this.commTree.InitTree(this.parser.getAstXml());
    }
    
    public void BuildWithoutBuilidgTree()
    {
        try 
        {
            this.parser = null;
            System.gc();
            
            parser = new IISCaseLangParser(con, PR_id, this.currentFunctionNode);
            int errNum = parser.getErrors().size();
            
            String text = this.area.getText();        
            parser.ParseProject(text, this.func_id);
            this.currentFunctionNode.setAttribute("Name", this.funcName);
            
            Document doc = parser.getAstXml();
            this.writeXmlFile(doc, "parsed.xml");
            
            errNum = parser.getErrors().size();
            this.errTableModel.data.clear();
        
            if ( parser.getError() )
            {
                int len = parser.getErrors().size();
                for( int i = 0; i < len; i++)
                {
                    this.errTableModel.data.add(((ErrorDescription)(parser.getErrors().get(i))).message);
                }
            }
            else
            {
                this.errTableModel.data.add("Success");
            }
            
            this.errTableModel.fireTableDataChanged();
            
            if (errNum == 0)
            {
                DbSave();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Hashtable getVariables()
    {
        if (this.parser != null)
        {
            return this.parser.getVariables();
        }
        else
        {
            return new Hashtable();
        }
    }
    
    public Hashtable getIteratorVariables()
    {
        if (this.parser != null)
        {
            return this.parser.getIteratorVariables();
        }
        else
        {
            return new Hashtable();
        }
    }
    
    public Hashtable getEnvVariables()
    {
        if (this.parser != null)
        {
            return this.parser.getEnvVariables();
        }
        else
        {
            return new Hashtable();
        }
    }
    
    public Hashtable getArgs()
    {
        if (this.parser != null)
        {
            return this.parser.getArgs();
        }
        else
        {
            return new Hashtable();
        }
    }
    
    public Hashtable getFunctions()
    {
        if (this.parser != null)
        {
            return this.parser.getFunctions();
        }
        else
        {
            return new Hashtable();
        }
    }
    
    public Hashtable getPredefinedFunctions()
    {
        if (this.parser != null)
        {
            return this.parser.getPredefinedFunctions();
        }
        else
        {
            return new Hashtable();
        }
    }
    
    public Hashtable getDomains()
    {
        if (this.parser != null)
        {
            return this.parser.getDomains();
        }
        else
        {
            return new Hashtable();
        }
    }

    public Hashtable getUserDefDomains()
    {
        if (this.parser != null)
        {
            return this.parser.getUserDefDomains();
        }
        else
        {
            return new Hashtable();
        }
    }

    public Hashtable getCompTypes()
    {
        if (this.parser != null)
        {
            return this.parser.getComponentTypes();
        }
        else
        {
            return new Hashtable();
        }
    }
    
    public Hashtable getComponentTypesCommands()
    {
        if (this.parser != null)
        {
            return this.parser.getComponentTypesCommands();
        }
        else
        {
            return new Hashtable();
        }
    }
    
    private void InitTextArea()
    {
        //ReadFile();
        this.area.repaint();        
        this.area.setCaretPosition(0);
    }
    
    private void switchEditorActionPerformed()
    {
        if (this.isTreeEditorActive)
        {
            this.switchToEditor.setText("Switch to Tree Editor");
            this.isTextEditorActive = true;
            this.isTreeEditorActive = false;
            this.area.setEnabled(true);
            this.commTree.editable = false;
        }
        else
        {
            this.switchToEditor.setText("Switch to Text Editor");
            this.isTextEditorActive = false;
            this.isTreeEditorActive = true;
            this.area.setEnabled(false);
            
            this.commTree.editable = true;
        }
    }
    
    private void ConvToPlsSql()
    {
        PlSqlTranslator translator = new PlSqlTranslator(this.con, PR_id);
        String plsqlCode = translator.ConvertToPlSql(Integer.parseInt(this.func_id));
        PreviewScript prev = new PreviewScript(this, "Script", true, plsqlCode);
        Settings.Center(prev);
        prev.setVisible(true);
        translator = null;
        System.gc();
        //LookScript frm = new LookScript(this.parent, "PlSql code", true, "", "", true, 1, "", "", "", "", this.con, "", 0);
        //frm.setVisible(true);
    }
    
    private void InitMenu()
    {
        //file menu 
        this.fileMenu.add(this.openMI);
        this.openMI.setIcon(this.openIcon);
        this.fileMenu.addSeparator();
        this.fileMenu.add(this.saveMI);
        this.saveMI.setIcon(this.diskIcon);
        this.fileMenu.add(this.saveDBMI);
        this.saveDBMI.setIcon(this.dbSaveIcon);
        this.fileMenu.addSeparator();
        this.fileMenu.add(this.closeMI);
        this.closeMI.setIcon(closelIcon);
        
        this.jMenuBar1.add(this.fileMenu);
        
        //edit menu
        this.editMenu.add(this.undoMI);
        this.undoMI.setIcon(this.undoIcon);
        this.editMenu.add(this.redoMI);    
        this.redoMI.setIcon(this.redoIcon);
        this.editMenu.addSeparator();
        this.editMenu.add(this.cutMI);
        this.cutMI.setIcon(this.cutIcon);
        this.editMenu.add(this.copyMI);
        this.copyMI.setIcon(this.copyIcon);
        this.editMenu.add(this.pasteMI);
        this.pasteMI.setIcon(this.pasteIcon);
        this.editMenu.addSeparator();
        this.editMenu.add(this.commentMI);
        this.commentMI.setIcon(this.commentIcon);
        this.editMenu.add(this.uncommentMI);
        this.uncommentMI.setIcon(this.uncommentIcon);
        this.editMenu.add(this.indentMI);
        this.indentMI.setIcon(this.indentIcon);
        this.editMenu.add(this.decraseMI);
        this.decraseMI.setIcon(this.outdentIcon);
        
        this.jMenuBar1.add(this.editMenu);
        
        //view menu
        this.viewMenu.add(this.errorListMI);
        errorListMI.setSelected(true);
        this.viewMenu.add(this.outputMI);
        outputMI.setSelected(true);
        this.viewMenu.add(this.watchMI);
        watchMI.setSelected(true);
        
        this.jMenuBar1.add(this.viewMenu);
        
        this.compAndRunMenu.add(this.compiledMI);
        this.compAndRunMenu.add(this.runMI);
        this.compAndRunMenu.add(this.compileRunMI);
        this.compAndRunMenu.addSeparator();
        this.compAndRunMenu.add(this.startDebugMI);
        this.compAndRunMenu.add(this.stopDebugMI);
        this.compAndRunMenu.add(this.nextCommMI);
        this.compAndRunMenu.add(this.resumeMI);
        this.resumeMI.setIcon(this.resumeIcon);
        this.compAndRunMenu.add(this.toggleBreakPointMI);
        this.toggleBreakPointMI.setIcon(this.toggleBreakpointIcon);
        this.compAndRunMenu.addSeparator();
        this.compAndRunMenu.add(this.addToWatchMI);
        this.compAndRunMenu.add(this.removeWatchMI);
        this.compAndRunMenu.add(this.editWatchMI);
        this.compAndRunMenu.add(this.removeAllWatchMI);
        /*this.compAndRunMenu.addSeparator();
        this.compAndRunMenu.add(this.addBreakPointMI);
        this.compAndRunMenu.add(removeBreakPointMI);*/
        
        this.compiledMI.setIcon(buildIcon);
        this.runMI.setIcon(bulletGoIcon);
        this.compileRunMI.setIcon(buildRunIcon);
        this.startDebugMI.setIcon(bugIcon);
        this.stopDebugMI.setIcon(stopIcon);
        this.nextCommMI.setIcon(nextStepIcon);
        this.stopDebugMI.setEnabled(false);
        this.nextCommMI.setEnabled(false);
        this.resumeMI.setEnabled(false);
        this.jMenuBar1.add(this.compAndRunMenu);
        
        this.watchPopupMenu.add(this.popUpaddToWatchMI);
        this.watchPopupMenu.addSeparator();
        this.watchPopupMenu.add(this.popUPEditWatchMI);    
        this.watchPopupMenu.add(this.popUPRemoveWatchMI);
        this.watchPopupMenu.addSeparator();
        this.watchPopupMenu.add(this.popUPRemoveAllWatchMI);
        
        //tools menu
        this.toolsMenu.add(this.convertMenu);
        this.convertMenu.add(this.plslqMI);
        this.toolsMenu.addSeparator();
        this.toolsMenu.add(this.switchToEditor);
        this.toolsMenu.addSeparator();
        this.toolsMenu.add(this.settingsMI);        
        
        this.watchMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ToggleWatch();
            }
        });
        
        this.outputMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ToggleOutputList();
            }
        });
        
        this.errorListMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ToggleErrorList();
            }
        });
        
        this.settingsMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ShowSettings();
            }
        });
        
        this.toggleBreakPointMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ToggleBreakPoint();
            }
        });
        
        this.resumeMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                NextBreakPointCommand();
            }
        });
        
        this.compiledMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Build();
            }
        });
        
        this.runMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ExecuteFunction();
            }
        });
        
        this.compileRunMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                BuildAndRun();
            }
        });
        
        this.commentMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Comment();
            }
        });
        
        this.uncommentMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                UnComment();
            }
        });
        
        this.indentMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //area.ExecIndent(false);
                Indent(false);
            }
        });
        
        this.decraseMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //area.ExecIndent(true);
                Indent(true);
            }
        });
        
        this.undoMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Undo();
            }
        });
        
        this.undoMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Undo();
            }
        });
        
        this.redoMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Redo();
            }
        });
        
        this.cutMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Cut();
            }
        });
        
        this.copyMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Copy();
            }
        });
        
        this.pasteMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Paste();
            }
        });
        
        this.openMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Open();
            }
        });
        
        this.saveMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Save();
            }
        });
        
        this.saveDBMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                DbSave();
            }
        });
        
        switchToEditor.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                switchEditorActionPerformed();
            }
        });
        
        plslqMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ConvToPlsSql();
            }
        });
        
        this.jMenuBar1.add(this.toolsMenu);
        
        this.closeMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);       
            }
        });        
        
        this.watchTable.addMouseListener( new MouseListener()
        {
            public void mouseClicked(MouseEvent e)
            {
            }
            
            public void mousePressed(MouseEvent e)
            {
                showPopup(e);
            }
            
            public void mouseReleased(MouseEvent e)
            {
            }
            
            public void mouseEntered(MouseEvent e)
            {
            }
            
            public void mouseExited(MouseEvent e)
            {
            }
            private void showPopup(MouseEvent e) 
            {
                if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1) 
                {
                    watchPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        
        this.startDebugMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                StartDebug();
            }
        });
        
        this.stopDebugMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                StopDebug();
            }
        });
        
        this.nextCommMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                NextCommand();
            }
        });
        
        this.addToWatchMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                AddToWatchList();
            }
        });
        
        this.popUpaddToWatchMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                AddToWatchList();
            }
        });
        
        this.removeWatchMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                RemoveFromWatchList();
            }
        });
        
        this.popUPRemoveWatchMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                RemoveFromWatchList();
            }
        });
        
        this.removeAllWatchMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                RemoveAllFromWatchList();
            }
        });
        
        this.popUPRemoveAllWatchMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                RemoveAllFromWatchList();
            }
        });
        
        this.editWatchMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                EditWatch();
            }
        });
        
        this.popUPEditWatchMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                EditWatch();
            }
        });
        
        this.addBreakPointMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                AddBreakPoint();
            }
        });
        
        this.removeBreakPointMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                RemoveBreakPoint();
            }
        });
    }
    
    public boolean getIsDebug()
    {
        return this.isDebug;
    }
    
    public void InitTable()
    {
        //this.errorTable = new JTable()
    }
    
    private void ReadFile()
    {
        try 
        {
            BufferedReader input = new BufferedReader(new FileReader(filename));            
            StringBuilder contents = new StringBuilder();

            String line = null;
            
            while (( line = input.readLine()) != null)
            {
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
            
            this.area.setText(contents.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void Open()
    {
        if (!pOpen.isEnabled())
        {
            return;
        }
        
        try 
        {
            JFileChooser fc = new JFileChooser() ;
            
            int result = fc.showDialog(this,"Open file");
            
            //if ( result == JFileChooser.)
            File f = fc.getSelectedFile();
            
            if ( f == null)
            {
                return;
            }
            
            BufferedReader input = new BufferedReader(new FileReader(f));            
            StringBuilder contents = new StringBuilder();

            String line = null;
            
            while (( line = input.readLine()) != null)
            {
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
            this.area.setText(contents.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void DbSave()
    {
        //Savepoint sp = null;
        
        try
        {
            //con.setAutoCommit(false);
            //sp = con.setSavepoint();
            
            Statement stmt1 = con.createStatement();
            stmt1.execute("delete from IISC_FUN_TEXT where Fun_id=" + func_id);
            stmt1.close();
            
            int charNum = 0;            
            String source  = this.area.getText();
            int sourceLen = source.length();
            int sequence = 0;
            
            while(charNum < sourceLen)
            {
                int rest = sourceLen - charNum + 1;
                String chunk = "";
                
                if (rest <= 255)
                {
                    chunk = source.substring(charNum);
                    charNum = sourceLen;
                }
                else
                {
                    chunk = source.substring(charNum, charNum+255);
                    charNum += 255;
                }
                
                String sql = "insert into IISC_FUN_TEXT(Fun_id,Sequence,Fun_Source_Text) values(?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1,this.func_id);
                ps.setString(2, Integer.toString(sequence));
                ps.setString(3, chunk);
                ps.execute();
                sequence = sequence  + 1;                
                ps.close();
            }
            //con.commit();
        }
        catch(Exception e)
        {
            /*if ( sp != null )
            {
                try 
                {
                    con.rollback(sp);
                } catch (SQLException re) 
                {
                    
                }
            }*/
            e.printStackTrace();
        }
        
        DbCompiledCodeSave();
    }
    
    private String NodeToString(Node node) 
    {
        StringWriter sw = new StringWriter();
        
        try 
        {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } 
        catch(TransformerException te) 
        {
            System.out.println("nodeToString Transformer Exception");
        }
        return sw.toString();
    }
    
    public void DbCompiledCodeSave()
    {
        //Savepoint sp = null;
        
        try
        {
            //con.setAutoCommit(false);
            //sp = con.setSavepoint();
            
            Statement stmt1 = con.createStatement();
            stmt1.execute("delete from IISC_FUN_COMPILED_CODE where Fun_id=" + func_id);
            stmt1.close();
            
            if (this.currentFunctionNode == null)
            {
                return;
            }
            
            int charNum = 0;            
            String source  = this.NodeToString(this.currentFunctionNode);
            
            if (source == null)
            {
                return;
            }
            
            int sourceLen = source.length();
            int sequence = 0;
            
            while(charNum < sourceLen)
            {
                int rest = sourceLen - charNum + 1;
                String chunk = "";
                
                if (rest <= 255)
                {
                    chunk = source.substring(charNum);
                    charNum = sourceLen;
                }
                else
                {
                    chunk = source.substring(charNum, charNum+255);
                    charNum += 255;
                }
                
                String sql = "insert into IISC_FUN_COMPILED_CODE(Fun_id,Sequence,Fun_Code) values(?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1,this.func_id);
                ps.setString(2, Integer.toString(sequence));
                ps.setString(3, chunk);
                ps.execute();
                sequence = sequence  + 1;                
                ps.close();
            }
            con.commit();
        }
        catch(Exception e)
        {
            /*if ( sp != null )
            {
                try 
                {
                    con.rollback(sp);
                } catch (SQLException re) 
                {
                    
                }
            }*/
            e.printStackTrace();
        }
        /*finally
        {
           try 
           {
               con.setAutoCommit(true);
           } catch (SQLException ex) 
           {
               
           }
        }*/
    }
    
    public void ToggleErrorList()
    {
        try 
        {
            if (!this.errorListMI.isSelected())
            {
                jTabbedPane1.remove(jScrollPane1); 
                this.repaint();
            }
            else
            {
                jTabbedPane1.insertTab("Errors", null, jScrollPane1, "Errors", 0);
                jTabbedPane1.setSelectedIndex(0);
                this.repaint();
            }
        }
        catch(Exception e)
        {
             e.printStackTrace();
        }
    }
        
    public void ToggleOutputList()
    {
        try 
        {
            if (!this.outputMI.isSelected())
            {
                jTabbedPane1.remove(this.outputTextArea); 
                this.repaint();
            }
            else
            {
                if (this.errorListMI.isSelected())
                {
                    jTabbedPane1.insertTab("Output", null, this.outputTextArea, "Output", 1);
                    jTabbedPane1.setSelectedIndex(1);
                }
                else
                {
                    jTabbedPane1.insertTab("Output", null, this.outputTextArea, "Output", 0);
                    jTabbedPane1.setSelectedIndex(0);
                }
                //jTabbedPane1.insertTab("Output", null, jScrollPane1, "Errors", 0);
                this.repaint();
            }
        }
        catch(Exception e)
        {
             e.printStackTrace();
        }
    }
    
    public void ToggleWatch()
    {
        try 
        {
            if (!this.watchMI.isSelected())
            {
                jTabbedPane1.remove(this.watchScrollPane); 
                this.repaint();
            }
            else
            {
                if (this.errorListMI.isSelected() && this.outputMI.isSelected())
                {
                    jTabbedPane1.insertTab("Watch", null, this.watchScrollPane, "Watch", 2);
                    jTabbedPane1.setSelectedIndex(2);
                }
                else
                {
                    if (!this.errorListMI.isSelected() && !this.outputMI.isSelected())
                    {
                        jTabbedPane1.insertTab("Watch", null, this.watchScrollPane, "Watch", 0);
                        jTabbedPane1.setSelectedIndex(0);
                    }
                    else
                    {
                        jTabbedPane1.insertTab("Watch", null, this.watchScrollPane, "Watch", 1);
                        jTabbedPane1.setSelectedIndex(1);
                    }
                }
                //jTabbedPane1.insertTab("Output", null, jScrollPane1, "Errors", 0);
                this.repaint();
            }
        }
        catch(Exception e)
        {
             e.printStackTrace();
        }
    }
    
    public void ShowSettings()
    {
        try 
        {
            EditorSettings editor = new EditorSettings(this, this.con);
            Settings.Center(editor);
            editor.setVisible(true);
            this.repaint();
            
            OpenExecConn();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void BuildAndRun()
    {
        Build();
        ExecuteFunction();
    }
    
    public void ExecuteFunction()
    {
        try 
        {
            if ( parser.getError() )
            {
                JOptionPane.showMessageDialog(this, "Unable to run. Function specification is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            vMashine = new VirtualMashine(this.exec_con);
            vMashine.InitAssemblerCode(parser.getAssemberCode());
            
            String result = vMashine.ExecuteFunc(this.funcName, this);
            this.outputTextArea.setText(vMashine.getOutput() + "\nFunction result:\n" + result);
            this.area.Reinit();
            this.repaint();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public int getCurrentOffset()
    {
        return this.area.getBuffer().getLineStartOffset(vMashine.getCurrentLine()- 1);
    }
    
    public void VarChanged(String varName, int varInd)
    {
        //System.out.println(varName);
        for(int i = 0; i < this.watchVars.size(); i++)
        {
            VarRef vRef = (VarRef)this.watchVars.get(i);
            
            if (vRef.varIndex == varInd)
            {
                if (vRef.value != null)
                {
                    vRef.varValue = vRef.value.ToString();
                }
            }
        }
        this.watchTableModel.fireTableDataChanged();
    }
    
    private void SetButtonsStartDebug()
    {
        pNextComm.setEnabled(true);
        pStopDebug.setEnabled(true);
        pResume.setEnabled(true);
        pStartDebug.setEnabled(false);
        
        pBuild.setEnabled(false);
        pBuildGo.setEnabled(false);
        pGo.setEnabled(false);
        
        pCut.setEnabled(false);
        pCopy.setEnabled(false);
        pPaste.setEnabled(false);
        pUndo.setEnabled(false);
        pRedo.setEnabled(false);
        pOpen.setEnabled(false);
        
        pComment.setEnabled(false);
        pUncomment.setEnabled(false);
        pIndent.setEnabled(false);
        pOutdent.setEnabled(false);
        
        this.openMI.setEnabled(false);
        this.undoMI.setEnabled(false);
        this.redoMI.setEnabled(false);
        this.cutMI.setEnabled(false);
        this.copyMI.setEnabled(false);
        this.pasteMI.setEnabled(false);
        this.indentMI.setEnabled(false);       
        this.decraseMI.setEnabled(false);
        this.uncommentMI.setEnabled(false);
        this.commentMI.setEnabled(false);
        this.resumeMI.setEnabled(true);
    }
    
    private void SetButtonsStopDebug()
    {
        pNextComm.setEnabled(false);
        pStopDebug.setEnabled(false);
        pResume.setEnabled(false);
        pStartDebug.setEnabled(true);
        
        pBuild.setEnabled(true);
        pBuildGo.setEnabled(true);
        pGo.setEnabled(true);
        
        pCut.setEnabled(true);
        pCopy.setEnabled(true);
        pPaste.setEnabled(true);
        pUndo.setEnabled(true);
        pRedo.setEnabled(true);
        pOpen.setEnabled(true);
        
        pComment.setEnabled(true);
        pUncomment.setEnabled(true);
        pIndent.setEnabled(true);
        pOutdent.setEnabled(true);
        
        this.openMI.setEnabled(true);
        this.undoMI.setEnabled(true);
        this.redoMI.setEnabled(true);
        this.cutMI.setEnabled(true);
        this.copyMI.setEnabled(true);
        this.pasteMI.setEnabled(true);
        this.indentMI.setEnabled(true);       
        this.decraseMI.setEnabled(true);
        this.uncommentMI.setEnabled(true);
        this.commentMI.setEnabled(true);
        this.resumeMI.setEnabled(false);
    }
    
    public void StartDebug()
    {
        try 
        {
            this.Build();
            
            if ( parser.getError() )
            {
                JOptionPane.showMessageDialog(this, "Unable to run. Function specification is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            this.isDebug = true;
            this.startDebugMI.setEnabled(false);
            this.stopDebugMI.setEnabled(true);
            this.nextCommMI.setEnabled(true);
            //this.area.setCaretBlinkEnabled(false);
            this.area.setEnabled(false);
            vMashine = new VirtualMashine(this.exec_con);
            vMashine.InitAssemblerCode(funcDoc);
            vMashine.AddVarListener(this);
            
            vMashine.BeginDebuggingFunc(this.funcName, this);
            
            for(int i = 0; i < this.watchVars.size(); i++)
            {
                VarRef vRef = (VarRef)this.watchVars.get(i);
                vMashine.GetInitialVarValue(vRef);
            }
            
            this.watchTableModel.fireTableDataChanged();
            int currLine = vMashine.getCurrentLine();
            //System.out.println(currLine);
            this.outputTextArea.setText(vMashine.getOutput());            
            int offset = this.area.getBuffer().getLineStartOffset(currLine - 1);
            this.area.setCaretPosition(offset);
            //this.area.moveCaretPosition(offset, false); 
             SetButtonsStartDebug();
            this.area.repaint();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void StopDebug()
    {
        try 
        {
            this.isDebug = false;
            this.startDebugMI.setEnabled(true);
            this.stopDebugMI.setEnabled(false);
            this.nextCommMI.setEnabled(false);
            this.area.setEnabled(true);
            //this.area.setCaretBlinkEnabled(true);
            
            if (vMashine == null)
            {
                return;
            }
            
            vMashine.setState(VirtualMashine.FINAL);
            
            SetButtonsStopDebug();
            
            this.area.Reinit();
            this.repaint();
            this.area.repaint();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void NextCommand()
    {
        try 
        {
            if (vMashine == null)
            {
                return;
            }
            
            if (vMashine.getState() != VirtualMashine.EXEC_BY_COMM)
            {
                return;
            }
            
            vMashine.NextStep(this);
            this.outputTextArea.setText(vMashine.getOutput());
            
            int offset = this.area.getBuffer().getLineStartOffset(vMashine.getCurrentLine()- 1);
            this.area.setCaretPosition(offset, false);
            this.area.setEnabled(false);
            //this.area.setCaretBlinkEnabled(false);
            
            if (vMashine.getState() == VirtualMashine.FINAL)
            {
                this.isDebug = false;
                this.startDebugMI.setEnabled(true);
                this.stopDebugMI.setEnabled(false);
                this.nextCommMI.setEnabled(false);
                this.area.setEnabled(true);
                //this.area.setCaretBlinkEnabled(false);
                this.area.SetOldLineColor();
                SetButtonsStopDebug();
            }       
            this.area.repaint();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void NextBreakPointCommand()
    {
        if (!pResume.isEnabled())
        {
            return;
        }
        
        try 
        {
            if (vMashine == null)
            {
                return;
            }
            
            if (vMashine.getState() != VirtualMashine.EXEC_BY_COMM)
            {
                return;
            }
            
            while(true)
            {
                vMashine.NextStep(this);
                //this.area.setCaretBlinkEnabled(false);
                
                if (vMashine.getState() == VirtualMashine.FINAL)
                {
                    break;
                }
                else
                {
                    int currLine = vMashine.getCurrentLine()- 1;
                    
                    if (this.breakpoints.containsKey(currLine))
                    {
                        break;
                    }
                    
                }
            }
            
            this.outputTextArea.setText(vMashine.getOutput());
            
            if (vMashine.getState() == VirtualMashine.FINAL)
            {
                this.isDebug = false;
                this.startDebugMI.setEnabled(true);
                this.stopDebugMI.setEnabled(false);
                this.nextCommMI.setEnabled(false);
                this.area.setEnabled(true);
                //this.area.setCaretBlinkEnabled(false);
                this.area.SetOldLineColor();
                SetButtonsStopDebug();
            }
            else
            {
                int offset = this.area.getBuffer().getLineStartOffset(vMashine.getCurrentLine()- 1);
                this.area.setCaretPosition(offset, false);
                this.area.setEnabled(false);
            }
            
            this.area.repaint();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void Save()
    {
        try 
        {
            JFileChooser fc = new JFileChooser();
            int result = fc.showDialog(this,"Save to file");
            File f = fc.getSelectedFile();
            
            if (f != null)
            {
                FileWriter fw = new FileWriter(f);
                Writer output = new BufferedWriter(fw);
                //System.out.println("to je " + this.area.getText());
                output.write(this.area.getText());
                output.close();
                fw.close();
            }
        }
        catch(Exception e)
        {
            System.out.println("greska");
        }
    }
    
    public void tableValueChanged(ListSelectionEvent e)
    {
        //this.getdisp
        if ( e.getValueIsAdjusting() )
        {
            int selectedRow = this.errorTable.getSelectedRow();
            
            if ( parser.getError() )
            {
                if ( selectedRow > -1 && selectedRow < this.parser.getErrors().size())
                {
                    ErrorDescription errDesc = (ErrorDescription)this.parser.getErrors().get(selectedRow);
                    
                    //System.out
                    int offset = this.area.getBuffer().getLineStartOffset(errDesc.line - 1);
                    
                    System.out.println("line :" + errDesc.line + " pos " + errDesc.charPos);
                    //System.out.println("line : " + errDesc.line + "offset " + offset);
                    
                    this.area.setCaretPosition(offset);                    
                    this.area.setCurrentLineRed();
                }
            }
        }
    }

    private void this_componentResized(ComponentEvent e) 
    {
        int deltaH = this.jSplitPane1.getHeight() - this.prevHeight;
        System.out.println(deltaH);
        //this.jSplitPane1.setDividerLocation((jSplitPane1.getDividerLocation() + deltaH));
        this.prevHeight = this.jSplitPane1.getHeight();
        
        //this.jSplitPane2.setDividerLocation((jSplitPane2.getDividerLocation() + (this.editorAndTreePanel.getWidth() - this.prevWidth)));
        //this.prevWidth = this.editorAndTreePanel.getWidth();   
    }

    public String GetEnvVar(String varName) 
    {
        return JOptionPane.showInputDialog(this, "Enter value for " + varName);
    }

    public void ExecuteFunction(String varName, ArrayList params) {
    }

    public void DispatchSignal(ArrayList params) 
    {
        String temp = params.get(0).toString();
        
        for(int i = 1; i < params.size(); i++)
        {
            temp += "," + params.get(i).toString();
        }
        
        JOptionPane.showMessageDialog(this, temp);
    }

    private class InfoTreeCustomCellRenderer extends    JLabel implements  TreeCellRenderer
    {
         private ImageIcon[]     iiscImage;
         private boolean selected;
         
         public InfoTreeCustomCellRenderer()
         {
             // Ucitaj ikonice za drvo
             iiscImage= new ImageIcon[6];
             iiscImage[0] = new ImageIcon(IISFrameMain.class.getResource("icons/folder.gif"));
             iiscImage[1] = new ImageIcon(IISFrameMain.class.getResource("icons/domain.gif"));
             iiscImage[2] = new ImageIcon(IISFrameMain.class.getResource("icons/primitive.gif"));
             iiscImage[3] = new ImageIcon(IISFrameMain.class.getResource("icons/attribute.gif"));
             iiscImage[4] = new ImageIcon(IISFrameMain.class.getResource("icons/function.gif"));
             iiscImage[5] = new ImageIcon(IISFrameMain.class.getResource("icons/package.gif"));
         }
     
         public Component getTreeCellRendererComponent( JTree tree,
                                               Object value, boolean bSelected, boolean bExpanded,
                                               boolean bLeaf, int iRow, boolean bHasFocus )
         {
             // Find out which node we are rendering and get its text
             DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
             Object[]    Path =  node.getUserObjectPath();
             String      labelText = (String)node.getUserObject();
              
             setText( labelText );
             setFont(new Font("SansSerif",0,11));
             ///setForeground(Color.black);
             selected = bSelected;
             
             if( Path.length == 1 )
             {
                 setIcon( iiscImage[0] );
                 return this;
             }
             
             if(Path[Path.length - 1].toString() == "Domains" || Path[Path.length -1].toString()=="User defined domains" || Path[Path.length -1].toString()=="Primitive domains" || Path[Path.length -1].toString()=="Attributes" || Path[Path.length -1].toString()=="User-defined Functions" || Path[Path.length -1].toString()=="Predefined Functions")
             {
                 setIcon( iiscImage[0] );
                 return this;
             }
             
             if(Path[Path.length - 2].toString() == "User defined domains")
             {
                 setIcon( iiscImage[1] );
                 return this;
             }
             
             if(Path[Path.length - 2].toString() == "Primitive domains")
             {
                 setIcon( iiscImage[2] );
                 return this;
             }
             
             if(Path[Path.length - 2].toString()=="Attributes")
             {
                 setIcon( iiscImage[3] );
                 return this;
             }
             
             if(Path[Path.length - 2].toString()=="User-defined Functions")
             {
                 setIcon( iiscImage[4] );
                 return this;
             }
             
             if(Path[Path.length - 2].toString()=="Predefined Functions")
             {
                 setIcon( iiscImage[5] );
                 return this;
             }
             
             if(Path.length >=3 && Path[Path.length - 3].toString()=="Predefined Functions")
             {
                 setIcon( iiscImage[4] );
                 return this;
             }
             
             return this;
         }
         
         public void paint( Graphics g )
         {
             Color               bColor;
           // Set the correct background color
            if (selected)
            {
               bColor = SystemColor.textHighlight;
               setForeground(Color.white);
            }
            else
            {
               bColor = Color.white;
               setForeground(Color.black);
            }
            g.setColor(bColor);
            g.fillRect( 0, 0, getWidth() - 1, getHeight() - 1 );
       
            super.paint( g );
         }     
     }
      
    public void ToggleBreakPoint()
    {
        int line = this.area.getCaretLine();        
        
        if (!this.breakpoints.containsKey(line))
        {
            Integer intLine = new Integer(line);
            this.breakpoints.put(line, intLine);
        }
        else
        {
            this.breakpoints.remove(line);
        }
        
        this.area.repaint();
    }
    
    public void AddBreakPoint()
    {
        int line = this.area.getCaretLine();
        Integer intLine = new Integer(line);
        
        if (!this.breakpoints.containsKey(line))
        {
            this.breakpoints.put(line, intLine);
        }
        this.area.repaint();
    }
    
    public void RemoveBreakPoint()
    {
        int line = this.area.getCaretLine();
        Integer intLine = new Integer(line);
        
        if (this.breakpoints.containsKey(line))
        {
            this.breakpoints.remove(line);
        }
        this.area.repaint();
    }
    
    public Hashtable getBreakPoints()
    {
        return this.breakpoints;
    }
    
    public void EditWatch()
    {
        try 
        {
            int selectedRow = this.watchTable.getSelectedRow();
            
            if ( selectedRow > -1 && selectedRow < this.watchVars.size())
            {
                VarRef vRef = (VarRef)watchVars.get(selectedRow);
                
                String varName = JOptionPane.showInputDialog(this, "Var name", vRef.fullVarName);
                
                VarRef newVRef = new VarRef();
                newVRef.fullVarName = varName;
                ErrorDescription desc = this.ParseVarName(varName, newVRef);
                
                if (desc != null)
                {
                    JOptionPane.showMessageDialog(this,desc.message, "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    this.watchVars.set(selectedRow, newVRef);
                    
                    if (this.isDebug)
                    {
                        if (this.vMashine != null)   
                        {
                            this.vMashine.GetInitialVarValue(newVRef);
                        }
                    }
                    
                    this.watchTableModel.fireTableDataChanged();
                    SaveWatchList();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void RemoveAllFromWatchList()
    {
        this.watchVars.clear();
        this.watchTableModel.fireTableDataChanged();
        SaveWatchList();
    }
    
    public void RemoveFromWatchList()
    {
        int selectedRow = this.watchTable.getSelectedRow();
        
        if ( selectedRow > -1 && selectedRow < this.watchVars.size())
        {
            this.watchVars.remove(selectedRow);
            this.watchTableModel.fireTableDataChanged();
            SaveWatchList();
        }
    }
    
    public void AddToWatchList()
    {
        try 
        {
            String varName = JOptionPane.showInputDialog(this, "Var name", "Add to watch list", JOptionPane.QUESTION_MESSAGE);
            
            this.AddToWatchList(varName);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void LoadWatchList()
    {
        try 
        {
            String wachlist = "";
            
            File fXmlFile = new File("editor_settings.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
                            
            Element settingRoot = doc.getDocumentElement();
            
            for(int i = 0; i < settingRoot.getChildNodes().getLength(); i++)
            {
                Node propNode = settingRoot.getChildNodes().item(i);
                
                if (propNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    String propName = propNode.getNodeName();
                    String propValue = SemAnalyzer.getTextContent(propNode);
                    
                    if (propName.equalsIgnoreCase("watchlist"))
                    {
                        wachlist = propValue;
                    }
                }
            }
             
            if (wachlist == null ||  wachlist.length() == 0)
            {
                return;
            }
            
            String[] vars = wachlist.split(";");
            
            for(int i = 0; i < vars.length; i++)
            {
                this.AddToWatchList(vars[i]);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void SaveWatchList()
    {
        try 
        {
            String watchlist = "";
            
            for(int i = 0; i < this.watchVars.size(); i++)
            {
                if (i > 0)
                {
                    watchlist += ";";
                }

                VarRef vRef = (VarRef )this.watchVars.get(i);
                watchlist += vRef.fullVarName;
            }
            
            File fXmlFile = new File("editor_settings.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
                            
            Element settingRoot = doc.getDocumentElement();
            
            for(int i = 0; i < settingRoot.getChildNodes().getLength(); i++)
            {
                Node propNode = settingRoot.getChildNodes().item(i);
                
                if (propNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    String propName = propNode.getNodeName();
                    
                    if (propName.equalsIgnoreCase("watchlist"))
                    {
                        while(propNode.getChildNodes().getLength() > 0)
                        {
                            propNode.removeChild(propNode.getFirstChild());
                        }
                        Node textnode = doc.createTextNode(watchlist);
                        propNode.appendChild(textnode);
                        writeXmlFile(doc, "editor_settings.xml");
                        break;
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void AddToWatchList(String fullVarName)
    {
        VarRef vRef = new VarRef();
        vRef.fullVarName = fullVarName;
        ErrorDescription desc = this.ParseVarName(fullVarName, vRef);
        
        if (desc != null)
        {
            JOptionPane.showMessageDialog(this,desc.message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            this.watchVars.add(vRef);
            
            if (this.isDebug)
            {
                if (this.vMashine != null)   
                {
                    this.vMashine.GetInitialVarValue(vRef);
                }
            }
            
            this.watchTableModel.fireTableDataChanged();
            SaveWatchList();
        }
    }
    
    private ErrorDescription ParseVarName(String varName, VarRef vRef)
    {
        int len = varName.length();
        IntPos pos = new IntPos();
        pos.pos = 0;
        
        while( pos.pos < len )
        {
            if (varName.charAt( pos.pos ) <= 'z' && varName.charAt( pos.pos ) >= 'a' )
            {
                pos.pos = pos.pos + 1;
                continue;
            }
            
            if (varName.charAt( pos.pos ) <= 'Z' && varName.charAt( pos.pos ) >= 'A' )
            {
                pos.pos = pos.pos + 1;
                continue;
            }
            
            if (varName.charAt( pos.pos ) <= '9' && varName.charAt( pos.pos ) >= '0' )
            {
                pos.pos = pos.pos + 1;
                continue;
            }
            
            if ( varName.charAt( pos.pos ) == '_' )
            {
                pos.pos = pos.pos + 1;
                continue;
            }
            break;
        }
        
        if (pos.pos == len)
        {
            vRef.varName = varName;
            return null;
        }
        else
        {
            vRef.varName = varName.substring(0, pos.pos);
            return ParseVarMember(varName, vRef, pos);
        }
    }
    
    private ErrorDescription ParseVarMember(String varName, VarRef vRef, IntPos pos)
    {
        int len = varName.length();
        
        if (pos.pos >= len)
        {
            return null;
        }
        
        if (varName.charAt( pos.pos ) != '.' && varName.charAt( pos.pos ) != '[')
        {
            //this.Loader
            String[] args = new String[2];
            args[ 0 ] = varName.substring(pos.pos, pos.pos+1);
            args[ 1 ] = Integer.toString(pos.pos);
            
            return this.LoadErrorDescription(args, "INVALID_VAR_REF_UNEXPECED_CHAR");
        }
        else
        {
            if (varName.charAt( pos.pos ) == '[')
            {
                return this.ParseArrayMember(varName, vRef, pos);
            }
            else
            {
                return this.ParseTuppleMember(varName, vRef, pos);
            }
        }
    }
    
    private ErrorDescription ParseArrayMember(String varName, VarRef vRef, IntPos pos)
    {
        pos.pos = pos.pos + 1;        
        int len = varName.length();
        
        if (pos.pos >= len)
        {
            String[] args = new String[1];
            args[ 0 ] = "";
            return this.LoadErrorDescription(args, "INVALID_VAR_REF_UNEXPECED_END");
        }
         
        if (!(varName.charAt( pos.pos ) <= '9' && varName.charAt( pos.pos ) >= '0' ))
        {
            String[] args = new String[2];
            args[ 0 ] = varName.substring(pos.pos, pos.pos+1);
            args[ 1 ] = Integer.toString(pos.pos);
            
            return this.LoadErrorDescription(args, "INVALID_VAR_REF_UNEXPECED_CHAR");
        }
        
        int begin = pos.pos;
        
        while( pos.pos < len )
        {
            if (varName.charAt( pos.pos ) <= '9' && varName.charAt( pos.pos ) >= '0' )
            {
                pos.pos = pos.pos + 1;
                continue;
            }
            break;
        }
        
        if (pos.pos >= len)
        {
            String[] args = new String[1];
            args[ 0 ] = "";
            return this.LoadErrorDescription(args, "INVALID_VAR_REF_UNEXPECED_END");
        }
        
        if (!(varName.charAt( pos.pos ) == ']'))
        {
            String[] args = new String[3];
            args[ 0 ] = varName.substring(pos.pos, pos.pos+1);
            args[ 1 ] = Integer.toString(pos.pos);
            args[ 2 ] = "]";
            
            return this.LoadErrorDescription(args, "INVALID_VAR_REF_UNEXPECED_CHAR_EXPECED");
        }
        
        int end = pos.pos;
        
        String indexStr = varName.substring(begin, end);
        
        int index = -1;
        
        try 
        {
            index = Integer.parseInt(indexStr);
        }
        catch(Exception e)
        {
            index = -1;
        }
        
        if (index == -1)
        {
            String[] args = new String[2];
            args[ 0 ] = indexStr;
            args[ 1 ] = Integer.toString(begin);
            
            return this.LoadErrorDescription(args, "INVALID_VAR_REF_INVALID_INDEX");
        }
        
        VarMemRef memRef = new VarMemRef();
        memRef.arrIndex = index;
        memRef.memIndex = -1;
        memRef.memName = "";
        vRef.mems.add(memRef);
        
        pos.pos = pos.pos + 1;
        
        return ParseVarMember(varName, vRef, pos);
    }
    
    private boolean IsIdentifierChar(char c)
    {
        if (c <= 'z' && c >= 'a' )
        {
            return true;
        }
        
        if (c <= 'Z' && c >= 'A' )
        {
            return true;
        }
        
        if (c <= '9' && c >= '0' )
        {
            return true;
        }
        
        if ( c == '_' )
        {
            return true;
        }
        
        return false;
    }
    
    private ErrorDescription ParseTuppleMember(String varName, VarRef vRef, IntPos pos)
    {
        pos.pos = pos.pos + 1;        
        int len = varName.length();
        
        if (pos.pos >= len)
        {
            String[] args = new String[1];
            args[ 0 ] = "";
            return this.LoadErrorDescription(args, "INVALID_VAR_REF_UNEXPECED_END");
        }
         
        if (!this.IsIdentifierChar(varName.charAt( pos.pos )))
        {
            String[] args = new String[2];
            args[ 0 ] = varName.substring(pos.pos, pos.pos+1);
            args[ 1 ] = Integer.toString(pos.pos);
            
            return this.LoadErrorDescription(args, "INVALID_VAR_REF_UNEXPECED_CHAR");
        }
        
        int begin = pos.pos;
        
        while( pos.pos < len )
        {
            if (this.IsIdentifierChar(varName.charAt( pos.pos )))
            {
                pos.pos = pos.pos + 1;
                continue;
            }
            break;
        }
        
        int end = pos.pos;
        
        String memStr = varName.substring(begin, end);
        
        VarMemRef memRef = new VarMemRef();
        memRef.arrIndex = -1;
        memRef.memIndex = -1;
        memRef.memName = memStr;
        vRef.mems.add(memRef);
        
        pos.pos = pos.pos + 1;
        
        return ParseVarMember(varName, vRef, pos);
    }
    
    public class IntPos 
    {
        int pos = 0;
    }
    
    private void InitErrorsXML()
    {
        try 
        {
            InputStream in =  JSourceCodeEditor.class.getResourceAsStream("xml/IISCasePaserErrors.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            errDoc = builder.parse(in);
            
            errRoot = errDoc.getDocumentElement();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    } 
    
    public Element GetFuncByName(Element funcNode, String funcName)
    {
        if (funcNode == null)
        {
            return null;
        }
        
        for(int i = 0; i < funcNode.getChildNodes().getLength(); i++)
        {
            if (funcNode.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
            {
                Element cNode = (Element)funcNode.getChildNodes().item(i);
                String name = cNode.getAttribute("Name");
                
                if (name != null && name != "")
                {
                    if (name.toLowerCase(Locale.US).equals(funcName))
                    {
                        return cNode;
                    }
                }
            }
        }
        
        return null;
    }
    
    public static String LoadCodeFromRepository(int PR_id, Connection conn)
    {
        try 
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            
            if( PR_id == -1 )
            {
                rs = stmt.executeQuery("Select Fun_id from IISC_FUNCTION");
            }
            else
            {
                rs = stmt.executeQuery("Select Fun_id from IISC_FUNCTION where PR_id=" + PR_id);
            }
            
            StringBuilder builder = new StringBuilder();
            builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><functions>");
            
            while(rs.next())
            {
                String fun_id = rs.getString("Fun_id");
                
                Statement stmt1 = conn.createStatement();
                ResultSet rs1 = stmt1.executeQuery("select * from IISC_FUN_COMPILED_CODE where Fun_id=" + fun_id + " order by Sequence ASC") ;
                
                while(rs1.next())
                {
                    String chunk = rs1.getString("Fun_Code");
                    
                    if (chunk != null && !chunk.equalsIgnoreCase(""))
                    {
                        builder.append(chunk);
                    }
                }
                
                rs1.close();
                stmt1.close();
            } 
            
            rs.close();
            stmt.close();
            
            builder.append("</functions>");
            return builder.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
    
    public static String GetDomName(String dom_id, Connection conn)
    {
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from IISC_DOMAIN where Dom_id=" + dom_id);    
            
            if (rs.next())
            {
                return rs.getString("Dom_mnem");
            }
        }
        catch(Exception ex)
        {}
        return "";
    }
    
    public static String GenerateEmptyFuncSource(int PR_id, String fun_id, Connection conn)
    {
        StringBuilder builder = new StringBuilder();
        String fun_name = "";
        String fun_ret_id = "";
        String fun_ret_name = "";
        
        try
        {
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select * from IISC_FUNCTION where Fun_id=" + fun_id) ;
                
            if(rs1.next())
            {
                fun_name = rs1.getString("Fun_name");
                fun_ret_id = rs1.getString("Dom_id");
                fun_ret_name = GetDomName(fun_ret_id, conn);
            }
            
            rs1.close();
            stmt1.close();
            
            if (fun_name != null && !fun_name.equals(""))
            {
                builder.append("FUNCTION " + fun_name + "(");
                
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from IISC_FUN_PARAM where Fun_id=" + fun_id + " order by Param_seq asc");
                
                int i = 0; 
                boolean can = true;
                
                while(rs.next())
                {
                    try 
                    {
                        String dom_id = rs.getString("Dom_id");
                        String inout = rs.getString("inout");//in 0, out 1, inout 2
                        String param_name = rs.getString("Param_name");
                        String dom_name = GetDomName(dom_id, conn);
                        
                        if (dom_name != null && !dom_name.equals(""))
                        {
                            if (i > 0)
                            {
                                builder.append(", ");  
                            }
                            builder.append(param_name); 
                            
                            if (inout.equals("1"))
                            {
                                builder.append(" OUT"); 
                            }
                            else
                            {
                                if (inout.equals("2"))
                                {
                                    builder.append(" INOUT"); 
                                }
                            }
                            
                            builder.append(" " + dom_name); 
                            
                            i = i + 1;
                        }
                    }
                    catch(Exception e)
                    {}
                }
                rs.close();
                stmt.close();
            }
        }
        catch(Exception ex)
        {}
        
        builder.append(")\n");
        builder.append("RETURNS " + fun_ret_name + "\n");
        builder.append("VAR\n");
        builder.append("END_VAR\n");
        builder.append("BEGIN\n");
        builder.append("END\n");
        return builder.toString();
        
    }
    
    public static String LoadFuncNameFromRepository(int PR_id, String fun_id, Connection conn)
    {
        String name = "";
        
        try 
        {
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select * from IISC_FUNCTION where Fun_id=" + fun_id) ;
                
            if(rs1.next())
            {
                name = rs1.getString("Fun_Name");
                
                if (name != null && !name.equalsIgnoreCase(""))
                {
                    return name;
                }
            }
            
            rs1.close();
            stmt1.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return name;
    }
    
    public static String LoadFuncIdFromRepository(int PR_id, String fun_mnem, Connection conn)
    {
        String name = "";
        
        try 
        {
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select * from IISC_FUNCTION where Fun_name='" + fun_mnem + "'") ;
                
            if(rs1.next())
            {
                name = rs1.getString("Fun_id");
                
                if (name != null && !name.equalsIgnoreCase(""))
                {
                    return name;
                }
            }
            
            rs1.close();
            stmt1.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return name;
    }
    
    public static String LoadFuncSourceFromRepository(int PR_id, String fun_id, Connection conn)
    {
        try 
        {
            StringBuilder builder = new StringBuilder();
            
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select * from IISC_FUN_TEXT where Fun_id=" + fun_id + " order by Sequence") ;
                
            while(rs1.next())
            {
                String chunk = rs1.getString("Fun_Source_Text");
                
                if (chunk != null && !chunk.equalsIgnoreCase(""))
                {
                    builder.append(chunk);
                }
            }
            
            rs1.close();
            stmt1.close();
            
            if (builder.length() == 0)
            {
                return GenerateEmptyFuncSource(PR_id, fun_id, conn);
            }
            else
            {
                return builder.toString();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
    
    private void InitFuncXML(String funcName)
    {
        boolean success = true;
        DocumentBuilder builder = null;
        try 
        {
            String xmlTxt = LoadCodeFromRepository(this.PR_id, this.con);
            
            /*FileWriter fw = new FileWriter(new File("test.txt"));
            Writer output = new BufferedWriter(fw);
            output.write(xmlTxt);
            output.close();
            fw.close();*/
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            funcDoc = builder.parse(new java.io.ByteArrayInputStream(xmlTxt.getBytes("utf-8")));
            
            funcRoot= funcDoc.getDocumentElement();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            success = false;
        }
        
        try
        {
            if (funcRoot == null)
            {
                funcDoc = builder.newDocument();
                funcRoot = funcDoc.createElement("functions");
                funcDoc.appendChild(funcRoot);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            success = false;
        }
        try 
        {
            if (funcName != null && funcName != "")
            {
                this.currentFunctionNode = this.GetFuncByName(funcRoot, funcName.toLowerCase(Locale.US));
                
                if (this.currentFunctionNode == null && this.funcDoc != null)
                {
                    this.currentFunctionNode = this.funcDoc.createElement("func");
                    this.currentFunctionNode.setAttribute("Name", this.funcName);
                }
                this.area.setText(LoadFuncSourceFromRepository(this.PR_id, this.func_id, this.con));
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void LoadPredefinedFuncDesc()
    {
        try 
        {
            InputStream in =  JSourceCodeEditor.class.getResourceAsStream("xml/IISCasePredefinedFunctions.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document predefinedInDoc = builder.parse(in);
            
            Element builInRoot = predefinedInDoc.getDocumentElement();
            
            for(int i = 0; i < builInRoot.getChildNodes().getLength();i++)
            {
                if (builInRoot.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE)
                {
                    Element packageNode = (Element)builInRoot.getChildNodes().item(i);
                    
                    PackageDesc desc= new PackageDesc();
                    desc.name = packageNode.getNodeName();
                    
                    for(int j = 0; j < packageNode.getChildNodes().getLength(); j++)
                    {
                        if (packageNode.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element funcNode = (Element)packageNode.getChildNodes().item(j);
                            
                            FunctionDesc fSpec = new FunctionDesc("", "");
                            fSpec.name = funcNode.getNodeName();
                            
                            NodeList returnNL = funcNode.getElementsByTagName("RETURN_TYPE");
                            
                            if (returnNL.getLength() > 0)
                            {
                                Element retTypeNode = (Element)returnNL.item(0);
                                
                                for(int k = 0; k < retTypeNode.getChildNodes().getLength(); k++)
                                {
                                    if (retTypeNode.getChildNodes().item(k).getNodeType() == Node.ELEMENT_NODE)
                                    {
                                        fSpec.domName = retTypeNode.getChildNodes().item(k).getNodeName();
                                    }
                                }
                            }
                            
                            NodeList paramsNL = funcNode.getElementsByTagName("PARAMS");
                            
                            if (paramsNL.getLength() > 0)
                            {
                                Element paramsNode = (Element)paramsNL.item(0);
                                
                                int s = 0;
                                
                                for(int k = 0; k < paramsNode.getChildNodes().getLength(); k++)
                                {
                                    if (paramsNode.getChildNodes().item(k).getNodeType() == Node.ELEMENT_NODE)
                                    {   
                                        Element paramNode = (Element)paramsNode.getChildNodes().item(k);
                                        
                                        ParamDesc p = new ParamDesc("","");
                                        p.setName(paramNode.getNodeName());
                                        p.setSeqNumber(s++);
                                        p.setType(ParamDesc.IN);
                                        fSpec.params.add(p);
                                        
                                        for(int b = 0; b < paramNode.getChildNodes().getLength(); b++)
                                        {
                                            if (paramNode.getChildNodes().item(b).getNodeType() == Node.ELEMENT_NODE)
                                            {  
                                                p.setDomName(paramNode.getChildNodes().item(b).getNodeName());
                                                break;
                                            }
                                        } 
                                            
                                    }
                                }
                                
                            }
                            //Element paramsNode = (Element)funcNode.getChildNodes().item(0);
                            
                            
                            desc.func.add(fSpec);
                        }
                    }
    
                    this.packages.add(desc);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private class PackageDesc
    {
        public String name = "";
        public ArrayList func = new ArrayList();
        
    };
    
    public ErrorDescription LoadErrorDescription(String[] args, String code)
    {
        ErrorDescription result = new ErrorDescription();
        
        try 
        {
            NodeList errNodes = errRoot.getElementsByTagName(code);
            
            if ( errNodes.getLength() > 0 )
            {
                Node errNode = errNodes.item(0);
                String message = SemAnalyzer.getTextContent(errNode);
                result.message = message;
                
                for(int i = 0; i < args.length; i++)
                {
                    result.message = result.message.replaceAll("%s" + (i+1), args[i]);
                }
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public class PreviewScript extends JDialog 
    {
      private JScrollPane jScrollPane1 = new JScrollPane();
      private JButton jButton1 = new JButton();
      private String ImeDat, Putanja;
      private JTextPane pane = new JTextPane();
      private JButton btnSave = new JButton();
      private JButton btnExec = new JButton();
      private String HostDatabase;
      private String UserNameDatabase;
      private String PasswordDatabase;
      private String NazivDatoteke;
      private int DBMS;
      private boolean DDL;
      private JButton btnHelp = new JButton();
      private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif")); 
      private Connection con;
      private int TargetDB;  
      private String url =new String();
      private String text; 
      
      public PreviewScript(JSourceCodeEditor parent, String title, boolean modal, String Path, String PrefiksName, boolean DDLP,int SUBP,String UserNameDat, String PasswordDat, String HostDat, String NazivDat, Connection conn, String urlp, int CiljDB)
      {
        super(parent, title, modal);
        try
        {
          Putanja = Path;
          ImeDat = PrefiksName;
          UserNameDatabase = UserNameDat;
          PasswordDatabase = PasswordDat;
          HostDatabase = HostDat;
          NazivDatoteke = NazivDat;
          DBMS = SUBP;
          DDL = DDLP;
          con = conn;
          TargetDB = CiljDB;
          url = urlp;
          jbInit();
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }

      }
      
      public PreviewScript(JSourceCodeEditor parent, String title, boolean modal, String text)
      {
          super(parent, title, modal);
          try
          {
            this.text = text;
            jbInit();
          }
          catch(Exception e)
          {
            e.printStackTrace();
          }
      }
      
      private void jbInit() throws Exception
      {
        this.setSize(new Dimension(519, 509));
        this.getContentPane().setLayout(null);
        jScrollPane1.setSize(new Dimension(458, 340));
        jScrollPane1.setBounds(new Rectangle(15, 25, 480, 400));
        jButton1.setText("Cancel");
        jButton1.setSize(new Dimension(80, 30));
        jButton1.setBounds(new Rectangle(365, 435, 80, 30));
        jButton1.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              jButton1_actionPerformed(e);
            }
          });
        btnSave.setText("Save");
        btnSave.setBounds(new Rectangle(175, 435, 80, 30));
        btnSave.setSize(new Dimension(80, 30));
        btnSave.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnSave_actionPerformed(e);
            }
          });
        btnExec.setText("Execute");
        btnExec.setBounds(new Rectangle(270, 435, 80, 30));
        btnExec.setSize(new Dimension(80, 30));
        btnExec.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnExec_actionPerformed(e);
            }
          });
        btnHelp.setBounds(new Rectangle(460, 435, 35, 30));
        btnHelp.setIcon(imageHelp);
        btnHelp.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnHelp_actionPerformed(e);
            }
          });
        this.getContentPane().add(btnHelp, null);
        
        if (text == null)
        {
            this.getContentPane().add(btnExec, null);
        }
        else
        {
            btnSave.setBounds(new Rectangle(270, 435, 80, 30));
        }
        this.getContentPane().add(btnSave, null);
        this.getContentPane().add(jButton1, null);
        jScrollPane1.getViewport().add(pane, null);
        this.getContentPane().add(jScrollPane1, null);
        SetPane();
        inicijalizacija();
      }
      private void inicijalizacija()
      {
        if (DDL)
        {
          btnExec.setEnabled(false);
        }
      }
      private void jButton1_actionPerformed(ActionEvent e)
      {
        this.dispose();
      }
      private void SetPane()
      {
        if (text != null)
        {
            this.pane.setText(text);
        }
        else
        {
            String ImeSkripta;    
            ImeSkripta = Putanja + ImeDat;
            File ChooseFile = new File(ImeSkripta);
            String fn = ChooseFile.toString();
         
            try 
            {
                 FileReader fr = 
                           new FileReader(fn);             
                 pane.read(fr, null);
                 fr.close();
            }
            catch (IOException eio) 
            {
                System.err.println(eio);
            }
        }
      }
      
      private void btnExec_actionPerformed(ActionEvent e)
      {    
        String str;
        PrintWriter outBat=null; 
        str="";
        try
        {
          File outputFile;      
          outputFile = new File("BatDat.bat");      
          outBat = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
        }  
        catch (IOException ex) {
                System.err.println("Cannot open file.");
                System.err.println(ex);
        }
        if (TargetDB == 2) //target database source
        {
        if (DBMS==2) 
        {
          str = "set ORACLE_SID=" + HostDatabase;
          outBat.println(str);
          str = "sqlplus "+ UserNameDatabase + "/" +PasswordDatabase + " @" + NazivDatoteke+".sql"; //"D:\\skriptovi\\script.sql";
          outBat.println(str);
        }else 
        {
          if (HostDatabase.equals("") && UserNameDatabase.equals("") && PasswordDatabase.equals(""))
          {
            str = "osql -E " + " -i " + NazivDatoteke+".sql"; 
          }else{
            str = "osql -S " + HostDatabase + " -U " + UserNameDatabase + " -P " + PasswordDatabase + " -i " + NazivDatoteke+".sql"; //f:\skriptovi\script.sql; // local mora da se stavi u zagradu - S (local)
          } 
          outBat.println(str);
        }    
       }else //Target je odbc
       {     
         if (DBMS==2) 
         {
          
         }else 
         {      
            str = "osql -D " + url + " -i " + NazivDatoteke+".sql"; //f:\skriptovi\script.sql; // local mora da se stavi u zagradu - S (local)
            outBat.println(str);
         }     
       }   
       outBat.close();
       try
        {
          Runtime runtime = Runtime.getRuntime();
          Process proc;      
          proc= runtime.exec("BatDat.bat");
          
        }catch (IOException ioe)
        {
           JOptionPane.showMessageDialog(null, "Exception:    " + ioe.getMessage() , "Connection error", JOptionPane.ERROR_MESSAGE);
              
        } 
        JOptionPane.showMessageDialog(null, "The generation completed successfully.", "Check", JOptionPane.INFORMATION_MESSAGE);
      }

      private void btnHelp_actionPerformed(ActionEvent e)
      {
        /*Help hlp =new  Help((IISFrameMain) getParent().getParent().getParent(),getTitle(), true, con );
        Settings.Center(hlp);
        hlp.setVisible(true);*/
      }

      private void btnSave_actionPerformed(ActionEvent e)
      {
            Writer output = null;
            JFileChooser fc = new JFileChooser();
        
            int result = fc.showOpenDialog(this);
        
            if (result == JFileChooser.APPROVE_OPTION)
            {
                File ChooseFile = fc.getSelectedFile();
                String fn = ChooseFile.toString();
                
                try 
                {      
                    output = new BufferedWriter( new FileWriter(fn) );
                    output.write( pane.getText() );
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
        
                try
                {
                    if (output != null) output.close();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
      }
      
      
    }
}