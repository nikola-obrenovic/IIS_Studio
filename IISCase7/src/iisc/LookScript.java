package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class LookScript extends JDialog 
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
  
  public LookScript(MessageWindow parent, String title, boolean modal, String Path, String PrefiksName, boolean DDLP,int SUBP,String UserNameDat, String PasswordDat, String HostDat, String NazivDat, Connection conn, String urlp, int CiljDB)
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
    this.getContentPane().add(btnExec, null);
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
    String ImeSkripta;    
    ImeSkripta = Putanja + ImeDat;
    File ChooseFile = new File(ImeSkripta);
    String fn = ChooseFile.toString();
     
   try {
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
    Help hlp =new  Help((IISFrameMain) getParent().getParent().getParent(),getTitle(), true, con );
    Settings.Center(hlp);
    hlp.setVisible(true);
  }

  private void btnSave_actionPerformed(ActionEvent e)
  {
    Writer output = null;
    String ImeSkripta;    
    ImeSkripta = Putanja + ImeDat;
    File ChooseFile = new File(ImeSkripta);
    String fn = ChooseFile.toString();
    try {      
      output = new BufferedWriter( new FileWriter(fn) );
      output.write( pane.getText() );
    }
     catch (IOException ex){
      ex.printStackTrace();
    }
    try{
      if (output != null) output.close();
     }
      catch (IOException ex){
      ex.printStackTrace();
    }
    
  }
  
  
}