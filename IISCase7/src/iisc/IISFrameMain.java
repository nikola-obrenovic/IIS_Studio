package iisc;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.awt.event.WindowEvent;
import java.awt.event.ContainerEvent;
import javax.swing.tree.TreePath;
 
public class IISFrameMain extends JFrame implements ActionListener 
{
    private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help.gif"));
    private ImageIcon imageClose = new ImageIcon(IISFrameMain.class.getResource("icons/close.gif"));
    private ImageIcon imageOpen = new ImageIcon(IISFrameMain.class.getResource("icons/open.gif"));
    private ImageIcon imageAppGenerator = new ImageIcon(IISFrameMain.class.getResource("icons/app_generator.gif"));
    private ImageIcon imageSQLGenerator = new ImageIcon(IISFrameMain.class.getResource("icons/sql_generator.gif"));
    private ImageIcon imageDB = new ImageIcon(IISFrameMain.class.getResource("icons/db.gif"));
    private ImageIcon imageDBAnalysis = new ImageIcon(IISFrameMain.class.getResource("icons/dbAnalysis.gif"));
    private JButton buttonHelp = new JButton();
    private JButton buttonClose = new JButton();
    private JButton buttonOpen = new JButton();
    private JToolBar toolBar = new JToolBar();
    private JLabel statusBar = new JLabel();
    private BorderLayout layoutMain = new BorderLayout();
    public JDesktopPane desktop = new JDesktopPane();   
    public Clipboard clipboard=new Clipboard();
    public Domain dom;
    public JMenuBar mb;
    public JButton buttonDB = new JButton();
    public JButton buttonDBSchemaAnalysis = new JButton();
    public JButton buttonAppGenerator = new JButton();
    public JButton buttonSQLGenerator = new JButton();
    public IISFrameMain()
    {
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
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icons/IISCase.gif")));
        this.getContentPane().setLayout(null);
        this.setBounds(0, 0, 700, 600); 
        desktop.setBackground(new Color(33, 100, 163));
        // define Menubar
         mb = new JMenuBar();
        setJMenuBar(mb);
        this.setTitle("IIS*Case");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Define File menu and with Exit menu item
        JMenu fileMenu = new JMenu("Projects");
        fileMenu.setMnemonic(KeyEvent.VK_P);
        mb.add(fileMenu);
        JMenuItem exitMenuItem = new JMenuItem("New",KeyEvent.VK_N);
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(this);
        exitMenuItem = new JMenuItem("Open...",KeyEvent.VK_O);
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        exitMenuItem = new JMenuItem("Close",KeyEvent.VK_C);
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        exitMenuItem = new JMenuItem("Exit",KeyEvent.VK_X);
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        fileMenu = new JMenu("Fundamentals");
         fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setEnabled(false);
        mb.add(fileMenu);
        exitMenuItem = new JMenuItem("User defined domains",KeyEvent.VK_U);
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        exitMenuItem = new JMenuItem("Attributes",KeyEvent.VK_A);
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        exitMenuItem = new JMenuItem("Functions",KeyEvent.VK_F);
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
         exitMenuItem = new JMenuItem("Inclusion Dependencies",KeyEvent.VK_I);
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        exitMenuItem = new JMenuItem("Primitive domains",KeyEvent.VK_P);
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        fileMenu = new JMenu("Application Systems");
         fileMenu.setMnemonic(KeyEvent.VK_A);
         fileMenu.setEnabled(false);
        mb.add(fileMenu);
        exitMenuItem = new JMenuItem("Application Systems",KeyEvent.VK_A);
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        exitMenuItem = new JMenuItem("Application Types",KeyEvent.VK_P);
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        exitMenuItem = new JMenuItem("Form Types",KeyEvent.VK_F);
        fileMenu.add(exitMenuItem);
        
        exitMenuItem.addActionListener (this);
        exitMenuItem = new JMenuItem("Relation Schemes",KeyEvent.VK_R);
        fileMenu.add(exitMenuItem);
        
        exitMenuItem.addActionListener (this);
        exitMenuItem = new JMenuItem("DB Schema Design",KeyEvent.VK_D);
        fileMenu.add(exitMenuItem);
        
        exitMenuItem.addActionListener (this);
        exitMenuItem = new JMenuItem("DB Schema Analysis",KeyEvent.VK_S);
        fileMenu.add(exitMenuItem);
         
        
        exitMenuItem.addActionListener (this);
        JMenu fileMenu1 = new JMenu("Generate DB Schema");
         fileMenu1.setMnemonic(KeyEvent.VK_G);
        fileMenu.add(fileMenu1);
            
        exitMenuItem = new JMenuItem("SQL DDL",KeyEvent.VK_S);     
        fileMenu1.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        exitMenuItem = new JMenuItem("OO DDL",KeyEvent.VK_O);     
        fileMenu1.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        exitMenuItem.setEnabled(false);
        exitMenuItem = new JMenuItem("XML Schema",KeyEvent.VK_X);
        fileMenu1.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        exitMenuItem.setEnabled(false); 
        
        exitMenuItem = new JMenuItem("Generate Application",KeyEvent.VK_A);
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        exitMenuItem.setEnabled(false);
            
        fileMenu1 = new JMenu("Export");
        fileMenu1.setMnemonic(KeyEvent.VK_E);
        fileMenu.add(fileMenu1);
        fileMenu1.setEnabled(false);
            
        exitMenuItem = new JMenuItem("DB Schema to XML Document",KeyEvent.VK_D);     
        fileMenu1.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        exitMenuItem = new JMenuItem("Form Types to XML Document",KeyEvent.VK_F);     
        fileMenu1.add(exitMenuItem);
        exitMenuItem.addActionListener (this);
        
        fileMenu = new JMenu("Window");
        fileMenu.setMnemonic(KeyEvent.VK_W);
        mb.add(fileMenu);
        exitMenuItem = new JMenuItem("Arrange windows",KeyEvent.VK_A);
        exitMenuItem.addActionListener (this);
        fileMenu.add(exitMenuItem);
        fileMenu = new JMenu("Administrative Options");
        fileMenu.setMnemonic(KeyEvent.VK_O);
        mb.add(fileMenu);
        fileMenu1 = new JMenu("XML Schemas");
        fileMenu1.setMnemonic(KeyEvent.VK_X);
        buttonDBSchemaAnalysis.setMaximumSize(new Dimension(27, 25));
        buttonDBSchemaAnalysis.setMinimumSize(new Dimension(27, 25));
        buttonDBSchemaAnalysis.setToolTipText("DB Schema Analysis");
        buttonDBSchemaAnalysis.setIcon(imageDBAnalysis);
        buttonDBSchemaAnalysis.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent ae)
        {
                        buttonDBSchema_ActionPerformed(ae);
                    }
        });
        buttonAppGenerator.setMaximumSize(new Dimension(27, 25));
        buttonAppGenerator.setMinimumSize(new Dimension(27, 25));
        buttonAppGenerator.setToolTipText("Generate Application");
        buttonAppGenerator.setIcon(imageAppGenerator);
        buttonAppGenerator.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent ae)
        {
                        buttonAppGenerator_ActionPerformed(ae);
                    }
        });
        buttonSQLGenerator.setMaximumSize(new Dimension(27, 25));
        buttonSQLGenerator.setMinimumSize(new Dimension(27, 25));
        buttonSQLGenerator.setToolTipText("Generate SQL DDL");
        buttonSQLGenerator.setIcon(imageSQLGenerator);
        buttonSQLGenerator.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent ae)
        {
                        buttonSQLGenerator_ActionPerformed(ae);
                    }
        });
        buttonDB.setMaximumSize(new Dimension(27, 25));
        buttonDB.setMinimumSize(new Dimension(27, 25));
        buttonDB.setToolTipText("DB Schema Design");
        buttonDB.setIcon(imageDB);
        buttonDB.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent ae)
        {
                        buttonDB_ActionPerformed(ae);
                    }
        });
        fileMenu.add(fileMenu1);
        exitMenuItem = new JMenuItem("DB XML Schema",KeyEvent.VK_D);
        exitMenuItem.addActionListener (this);
        fileMenu1.add(exitMenuItem);
        exitMenuItem = new JMenuItem("Form Type XML Schema",KeyEvent.VK_F);
        exitMenuItem.addActionListener (this);
        fileMenu1.add(exitMenuItem);
        
        fileMenu = new JMenu("Help");
        fileMenu.setMnemonic(KeyEvent.VK_H);
        mb.add(fileMenu);
        exitMenuItem = new JMenuItem("About",KeyEvent.VK_A);
        fileMenu.add(exitMenuItem);
        desktop.addContainerListener(new ContainerAdapter()
        {
            public void componentRemoved(ContainerEvent e)
            {
              desktop_componentRemoved(e);
            }
          });
        buttonHelp.setMaximumSize(new Dimension(27, 25));
        buttonHelp.setMinimumSize(new Dimension(27, 25));
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
              this_windowClosing(e);
            }
          });
        exitMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
              helpAbout_ActionPerformed(ae);
            }
          });
        fileMenu1.getMenuComponent(1).setEnabled(true); 
        statusBar.setText("IIS*Case");
        this.getContentPane().setLayout(layoutMain);
        buttonOpen.setToolTipText("Open Project");
        buttonOpen.setIcon(imageOpen);
        buttonClose.setToolTipText("Close Project");
        buttonClose.setIcon(imageClose);
        buttonOpen.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
              open_ActionPerformed(ae);
            }
          });
        buttonClose.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
              close_ActionPerformed(ae);
            }
          });
        buttonHelp.setToolTipText("About");
        buttonHelp.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
              helpAbout_ActionPerformed(ae);
            }
          });
        buttonHelp.setIcon(imageHelp);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        toolBar.add(buttonOpen);
        toolBar.add(buttonClose);
        toolBar.add(buttonDB, null);
        toolBar.add(buttonDBSchemaAnalysis, null);
        toolBar.add(buttonAppGenerator, null);
        toolBar.add(buttonSQLGenerator, null);
        toolBar.add(buttonHelp);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        this.getContentPane().add(desktop, BorderLayout.CENTER);
    }

    public void helpAbout_ActionPerformed(ActionEvent e)
    {
    JOptionPane.showMessageDialog(this, new IISFrameMain_AboutBoxPanel1(), "About", JOptionPane.PLAIN_MESSAGE);
    }
      
    void open_ActionPerformed(ActionEvent e)
    {
    JDBCDialog Dialog =new JDBCDialog(this,"IIS*Case Repository",true);
    }
      
    public void close_ActionPerformed(ActionEvent e)
    {
       try
        {
          this.desktop.getSelectedFrame().setClosed(true);
        }
        catch(Exception ea)
        {
          ea.printStackTrace();
        }
    }
    public void actionPerformed( ActionEvent event )
    {
        String menuLabel = "";
        if(event.getActionCommand().toString().equals(""))menuLabel=((JButton)event.getSource()).getToolTipText();
        else
        menuLabel=event.getActionCommand().toString();
        try
        { 
            if(menuLabel.equals("Exit")) {
                if( JOptionPane.showConfirmDialog (null, "<html><center>Do you really want ot exit?","IIS*Case",JOptionPane.YES_NO_OPTION)==0 )
                { 
                    dispose();
                    System.exit(0);
                }
            } 
            if(menuLabel.equals("New")) {
                if(this.desktop.getAllFrames().length>0)
                {
                    NewProject np=new NewProject(this,this.getTitle(),true);
                    Settings.Center(np);
                    np.setVisible(true);
                }
                else
                {
                    JDBCDialog Dialog =new JDBCDialog(this,"IIS*Case Repository",true);
                }
            } 
            if(menuLabel.equals("Open...")) {
                JDBCDialog Dialog =new JDBCDialog(this,"IIS*Case Repository",true);
            } 
            if(menuLabel.equals("Close")) 
                this.desktop.getSelectedFrame().setClosed(true);
            ResultSet rs;
            JDBCQuery query=new JDBCQuery(((PTree)this.desktop.getSelectedFrame()).con);
            if(menuLabel.equals("Arrange windows")) {
                int inc=0;
                Object[] ptr= this.desktop.getAllFrames();
                for(int i=0;i<ptr.length ;i++)
                {
                 inc=inc+((PTree)ptr[i]).WindowsManager.size();
                }     
                int x=this.getX();
                int y=this.getY();
                inc=(int)(Math.sqrt((this.getWidth()/2*this.getWidth()/2)+(this.getHeight()/2*this.getHeight()/2))/(inc+1));
                x=x-inc;
                y=y-inc;
                for(int i=0;i<ptr.length ;i++)
                {
                    Iterator it=((PTree)ptr[i]).WindowsManager.iterator();
                    while(it.hasNext())
                    {
                     JDialog dial=(JDialog)it.next();
                     if(dial.isVisible())
                     {x=x+inc;
                      y=y+inc;
                     dial.setBounds(x,y,dial.getWidth(),dial.getHeight());
                     }
                     dial.requestFocus();
                    }
                }
            } 
            if (menuLabel.equals("Functions"))
            {
                Function fun=new Function(this,((PTree)this.desktop.getSelectedFrame()).getTitle(),false,((PTree)this.desktop.getSelectedFrame()).con,-1,((PTree)this.desktop.getSelectedFrame()));
                Settings.Center(fun);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(fun);
                fun.setVisible(true);
            }
            if (menuLabel.equals("User defined domains"))
            {
                dom=new Domain(this,((PTree)this.desktop.getSelectedFrame()).getTitle(),false,((PTree)this.desktop.getSelectedFrame()).con,-1,((PTree)this.desktop.getSelectedFrame()));
                Settings.Center(dom);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(dom);
                dom.setVisible(true);
            }
            if(menuLabel.equals("Primitive domains")) {
                PrimitiveTypes ptypes=new PrimitiveTypes(this,this.desktop.getSelectedFrame().getTitle(),-1,false,((PTree)this.desktop.getSelectedFrame()).con,((PTree)this.desktop.getSelectedFrame()));
                Settings.Center(ptypes);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(ptypes);
                ptypes.setVisible(true);
            }
            if(menuLabel.equals("Application Types")) {
                AppType type=new AppType(this,this.desktop.getSelectedFrame().getTitle(),false,((PTree)this.desktop.getSelectedFrame()).con,-1,(PTree)this.desktop.getSelectedFrame());
                Settings.Center(type);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(type);
                type.setVisible(true);
            } 
            if(menuLabel.equals("Application Systems")) {
                AppSys appsys=new AppSys(this,this.desktop.getSelectedFrame().getTitle(),false,((PTree)this.desktop.getSelectedFrame()).con,-1,(PTree)this.desktop.getSelectedFrame());
                Settings.Center(appsys);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(appsys);
                appsys.setVisible(true);
            } 
            if (menuLabel.equals("Attributes"))
            {
                Attribute att=new Attribute(this,((PTree)this.desktop.getSelectedFrame()).getTitle(),false,((PTree)this.desktop.getSelectedFrame()).con,-1,((PTree)this.desktop.getSelectedFrame()));
                Settings.Center(att);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(att);
                att.setVisible(true);
            }
            int i=-1;  
            if (menuLabel.equals("Inclusion Dependencies"))
            {
                InclusionDep indep=new InclusionDep(this,((PTree)this.desktop.getSelectedFrame()).getTitle(),-1,false,((PTree)this.desktop.getSelectedFrame()).con,((PTree)this.desktop.getSelectedFrame()));
                Settings.Center(indep);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(indep);
                indep.setVisible(true);
            }
            if(((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Relation Schemes"))
            {
                rs=query.select("select * from IISC_RELATION_SCHEME where RS_name='" + ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+((PTree)this.desktop.getSelectedFrame()).ID);
                if(rs.next())
                    i=rs.getInt(1);
                query.Close();
            }
            if(((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString().equals("Form Types"))
            {
                rs=query.select("select * from IISC_FORM_TYPE where Tf_mnem='" + ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+((PTree)this.desktop.getSelectedFrame()).ID);
                if(rs.next())
                    i=rs.getInt(1);
                query.Close();
            }
            if(((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
            {
                rs=query.select("select * from IISC_APP_SYSTEM where AS_name='" + ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+((PTree)this.desktop.getSelectedFrame()).ID);
                if(rs.next())
                    i=rs.getInt(1);
                query.Close();
            }
            if (menuLabel.equals("Form Types")&&((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
            {
                Form frm=new Form(this,((PTree)this.desktop.getSelectedFrame()).getTitle(),false,((PTree)this.desktop.getSelectedFrame()).con,-1,((PTree)this.desktop.getSelectedFrame()),((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getLastPathComponent().toString());
                Settings.Center(frm);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(frm);
                frm.setVisible(true);
            }
            if (menuLabel.equals("Form Types")&&((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getLastPathComponent().toString().equals("Form Types"))
            {
                Form frm=new Form(this,((PTree)this.desktop.getSelectedFrame()).getTitle(),false,((PTree)this.desktop.getSelectedFrame()).con,-1,((PTree)this.desktop.getSelectedFrame()),((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString());
                Settings.Center(frm);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(frm);
                frm.setVisible(true);
            }
            if (menuLabel.equals("Form Types")&&((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Form Types"))
            {
                Form frm=new Form(this,((PTree)this.desktop.getSelectedFrame()).getTitle(),false,((PTree)this.desktop.getSelectedFrame()).con,i,((PTree)this.desktop.getSelectedFrame()),((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString());
                Settings.Center(frm);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(frm);
                frm.setVisible(true);
            }
            if (menuLabel.equals("Relation Schemes")&&((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
            {
                RScheme rel=new RScheme(this,((PTree)this.desktop.getSelectedFrame()).getTitle(),false,((PTree)this.desktop.getSelectedFrame()).con,-1,((PTree)this.desktop.getSelectedFrame()),((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getLastPathComponent().toString());
                Settings.Center(rel);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(rel);
                rel.setVisible(true);
            }
            if (menuLabel.equals("Relation Schemes")&&((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getLastPathComponent().toString().equals("Relation Schemes"))
            {
                RScheme rel=new RScheme(this,((PTree)this.desktop.getSelectedFrame()).getTitle(),false,((PTree)this.desktop.getSelectedFrame()).con,-1,((PTree)this.desktop.getSelectedFrame()),((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString());
                Settings.Center(rel);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(rel);
                rel.setVisible(true);
            }
            if (menuLabel.equals("Relation Schemes")&&((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Relation Schemes"))
            {
                RScheme rel=new RScheme(this,((PTree)this.desktop.getSelectedFrame()).getTitle(),false,((PTree)this.desktop.getSelectedFrame()).con,i,((PTree)this.desktop.getSelectedFrame()),((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent().toString());
                Settings.Center(rel);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(rel);
                rel.setVisible(true);
            }
            if (menuLabel.equals("DB Schema Design")&&  ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
            { 
                DBSchemeDesign dbsys=new DBSchemeDesign(this,this.desktop.getSelectedFrame().getTitle(),false,((PTree)this.desktop.getSelectedFrame()).con,(PTree)this.desktop.getSelectedFrame());
                Settings.Center(dbsys);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(dbsys);
                dbsys.setVisible(true);
            }
            if (menuLabel.equals("DB Schema Analysis")&&  ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
            { 
                DBSchemaAnalysis dbsys=new DBSchemaAnalysis(this,this.desktop.getSelectedFrame().getTitle(),false,((PTree)this.desktop.getSelectedFrame()).con,(PTree)this.desktop.getSelectedFrame());
                Settings.Center(dbsys);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(dbsys);
                dbsys.setVisible(true);
            }
            if (menuLabel.equals("DB Schema to XML Document")&& (((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getLastPathComponent().toString().equals("Relation Schemes")|| ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")))
            { 
                XMLViewer xmlv=new XMLViewer(this,"Export DB Schema to XML Document",false,((PTree)this.desktop.getSelectedFrame()).con,"xml");
                Settings.Center(xmlv);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(xmlv);
                xmlv.setVisible(true);
            }
            if ((menuLabel.equals("SQL DDL") || menuLabel.equals("Generate SQL DDL"))&& (((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getLastPathComponent().toString().equals("Relation Schemes")|| ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")))
            { 
                GenerateSQL Gsql=new GenerateSQL(this,"SQL skript",false, ((PTree)this.desktop.getSelectedFrame()).con,(PTree)this.desktop.getSelectedFrame());
                Settings.Center(Gsql);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(Gsql);
                Gsql.setVisible(true);
            } 
            if (menuLabel.equals("Generate Application")&& ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
            { 
                GenerateApp gapp=new GenerateApp(this,"Generate Application",false, ((PTree)this.desktop.getSelectedFrame()).con,i,(PTree)this.desktop.getSelectedFrame());
                Settings.Center(gapp);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(gapp);
                gapp.setVisible(true);
            } 
            if (menuLabel.equals("Generate Application")&& ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned"))
            { 
                GenerateApp gapp=new GenerateApp(this,"Generate Application",false, ((PTree)this.desktop.getSelectedFrame()).con,-1,(PTree)this.desktop.getSelectedFrame(),i);
                Settings.Center(gapp);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(gapp);
                gapp.setVisible(true);
            } 
            if (menuLabel.equals("Form Types to XML Document")&& ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned"))
            {  
                int pr[]=new int[1];
                pr[0]=i;
                if(((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPaths().length>1)
                {
                    TreePath path[]=((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPaths();
                    pr=new int[path.length];
                    for(i=0;i<path.length;i++)
                    {
                        rs=query.select("select * from IISC_FORM_TYPE where Tf_mnem='" + path[i].getLastPathComponent().toString() + "'  and PR_id="+((PTree)this.desktop.getSelectedFrame()).ID);
                        if(rs.next())
                            pr[i]=rs.getInt(1);
                        query.Close();
                    }
                }
                TFXMLViewer tf=new TFXMLViewer(this,pr,((PTree)this.desktop.getSelectedFrame()).con,"xml",TFXMLViewer.FORM_TYPE_SPEC);
                Settings.Center(tf);
                tf.setVisible(true);
            }
            if (menuLabel.equals("Form Types to XML Document")&& ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
            {
                int pr[]=new int[1];
                pr[0]=i;
                TFXMLViewer tf=new TFXMLViewer(this,pr,((PTree)this.desktop.getSelectedFrame()).con,"xml",TFXMLViewer.APP_SYS_SPEC);
                Settings.Center(tf);
                tf.setVisible(true);
            }
            if (menuLabel.equals("DB XML Schema")&& (((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getLastPathComponent().toString().equals("Relation Schemes")||  ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems")))
            { 
                XMLViewer xmlv=new XMLViewer(this,"DB XML Schema",false,((PTree)this.desktop.getSelectedFrame()).con,"xsd");
                Settings.Center(xmlv);
                ((PTree)this.desktop.getSelectedFrame()).WindowsManager.add(xmlv);
                xmlv.setVisible(true);
            }
            if (menuLabel.equals("Form Type XML Schema")&& ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Application Systems"))
            {
                int pr[]=new int[1];
                pr[0]=i;
                TFXMLViewer tf=new TFXMLViewer(this,pr,((PTree)this.desktop.getSelectedFrame()).con,"xsd",TFXMLViewer.APP_SYS_SPEC);
                Settings.Center(tf);
                tf.setVisible(true);
            }
            if (menuLabel.equals("Form Type XML Schema")&& ((PTree)this.desktop.getSelectedFrame()).tree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned"))
            {
                int pr[]=new int[1];
                pr[0]=i;
                TFXMLViewer tf=new TFXMLViewer(this,pr,((PTree)this.desktop.getSelectedFrame()).con,"xsd",TFXMLViewer.FORM_TYPE_SPEC);
                Settings.Center(tf);
                tf.setVisible(true);
            }
        }
        catch(Exception ea)
        {
              ea.printStackTrace();
        }
    }
    
    private void this_windowClosing(WindowEvent e)
    {
        System.exit(0);
    }
    private void desktop_componentRemoved(ContainerEvent e)
    {
        if(desktop.getAllFrames().length==0)
        {
            mb.getMenu(1).setEnabled(false);
            mb.getMenu(2).setEnabled(false);
        } 
    }
    private void buttonDB_ActionPerformed(ActionEvent e) {
        actionPerformed(e);
    }
    
    private void buttonDBSchema_ActionPerformed(ActionEvent e) {
        actionPerformed(e);
    }
    
    private void buttonAppGenerator_ActionPerformed(ActionEvent e) {
        actionPerformed(e);
    }
    
    private void buttonSQLGenerator_ActionPerformed(ActionEvent e) {
        actionPerformed(e);
    }
}
