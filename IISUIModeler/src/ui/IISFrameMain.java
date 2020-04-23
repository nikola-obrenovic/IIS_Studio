package ui;
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
import ui.IISFrameMain_AboutBoxPanel1;
import java.awt.Color;
 
public class IISFrameMain extends JFrame implements ActionListener 
{
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help.gif"));
  private ImageIcon imageClose = new ImageIcon(IISFrameMain.class.getResource("icons/close.gif"));
  private ImageIcon imageOpen = new ImageIcon(IISFrameMain.class.getResource("icons/open.gif"));
  private JButton buttonHelp = new JButton();
  private JButton buttonClose = new JButton();
  private JButton buttonOpen = new JButton();
  private JToolBar toolBar = new JToolBar();
  private JLabel statusBar = new JLabel();
  private BorderLayout layoutMain = new BorderLayout();
  public JDesktopPane desktop = new JDesktopPane();   
  public JMenuBar mb;
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
    this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icons/IIST.gif")));
    this.getContentPane().setLayout(null);
    this.setBounds(0, 0, 700, 600); 
    desktop.setBackground(new Color(122, 165, 119));
    // define Menubar
     mb = new JMenuBar();
    setJMenuBar(mb);
    this.setTitle("IIS*UIModeler");
        // Define File menu and with Exit menu item
    JMenu fileMenu = new JMenu("Templates");
    fileMenu.setMnemonic(KeyEvent.VK_T);
    mb.add(fileMenu);
    JMenuItem exitMenuItem = new JMenuItem("Open...");
    exitMenuItem.setMnemonic(KeyEvent.VK_O);
    fileMenu.add(exitMenuItem);
    exitMenuItem.addActionListener (this);
    exitMenuItem = new JMenuItem("Close");
    exitMenuItem.setMnemonic(KeyEvent.VK_C);
    fileMenu.add(exitMenuItem);
    exitMenuItem.addActionListener (this);
    exitMenuItem = new JMenuItem("Exit");
    exitMenuItem.setMnemonic(KeyEvent.VK_E);
    fileMenu.add(exitMenuItem);
    fileMenu = new JMenu("Window");
    fileMenu.setMnemonic(KeyEvent.VK_W);
    mb.add(fileMenu);
    exitMenuItem = new JMenuItem("Arrange windows");
    exitMenuItem.setMnemonic(KeyEvent.VK_A);
    exitMenuItem.addActionListener (this);
    fileMenu.add(exitMenuItem);
    fileMenu = new JMenu("Help");
    fileMenu.setMnemonic(KeyEvent.VK_H);
    mb.add(fileMenu);
    exitMenuItem = new JMenuItem("About");
    exitMenuItem.setMnemonic(KeyEvent.VK_A);
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
    
    
    statusBar.setText("IIS*UIModeler");
    this.getContentPane().setLayout(layoutMain);
    buttonOpen.setToolTipText("Open");
    buttonOpen.setIcon(imageOpen);
    buttonClose.setToolTipText("Close");
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
    toolBar.add(buttonHelp);
    this.getContentPane().add(toolBar, BorderLayout.NORTH);
    this.getContentPane().add(desktop, BorderLayout.CENTER);  
  }

 
  void helpAbout_ActionPerformed(ActionEvent e)
  {
    JOptionPane.showMessageDialog(this, new IISFrameMain_AboutBoxPanel1(), "About", JOptionPane.PLAIN_MESSAGE);
  }
  
    void open_ActionPerformed(ActionEvent e)
  {
    JDBCDialog Dialog =new JDBCDialog(this,"IIS*UIModeler Repository",true);
  }
  
  void close_ActionPerformed(ActionEvent e)
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
   String menuLabel = event.getActionCommand();   
   try
    { 
            if(menuLabel.equals("Exit")) {
      if( JOptionPane.showConfirmDialog (null, "<html><center>Do you really want ot exit?","IIS*UIModeler",JOptionPane.YES_NO_OPTION)==0 )
       { dispose();
        System.exit(0);}
      } 
      if(menuLabel.equals("Open...")) {
        JDBCDialog Dialog =new JDBCDialog(this,"IIS*UIModeler Repository",true);
      } 
      if(menuLabel.equals("Close")) 
        this.desktop.getSelectedFrame().setClosed(true);
      if(menuLabel.equals("Exit")) {
      if( JOptionPane.showConfirmDialog (null, "<html><center>Do you really want ot exit?","IIS*UIModeler",JOptionPane.YES_NO_OPTION)==0 )
       { 
        dispose();
        System.exit(0);
      }
      } 
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
  {mb.getMenu(1).setEnabled(false);
   mb.getMenu(2).setEnabled(false);
  }
  }

  
}