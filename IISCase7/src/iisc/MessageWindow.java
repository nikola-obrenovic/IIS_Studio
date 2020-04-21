package iisc;

import java.awt.Dimension;
import java.io.*;
import java.sql.Connection;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import java.sql.*;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JList;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MessageWindow extends JDialog 
{

  private JTextPane pane = new JTextPane();
  private JButton btnCancel = new JButton();
  private File WarningDat;
  private boolean OneFileOnly;
  private boolean IndeksiP,IndeksiF, IndeksiU;
  private boolean Ogranicenja;
  private boolean Trigeri;
  private String FileIme, PutanjaDatoteke;
  private JButton btnHelp = new JButton();
  private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif")); 
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private JScrollPane jScrollPane3 = new JScrollPane();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JList List1 = new JList();
  private String Prefiks;
  private DefaultListModel modellst1=new DefaultListModel();
  private JButton btnRun = new JButton();
  private JButton btnExec = new JButton();
  private boolean DDLOnly;
  private int DBMS;
  private String UserNameDatabase;
  private String PasswordDatabase;
  private String HostDatabase;
  private String NazivDatoteke;
  private Connection con;
  //private Connection connection = null;
  //private String driverJdbcOdbc = "sun.jdbc.odbc.JdbcOdbcDriver";
  private int TargetDB;  
  private String url =new String();
  
  public MessageWindow(GenerateSQL parent, String title, boolean modal, File outputFileW, boolean OneFile, boolean IndP, boolean IndF, boolean IndU, boolean Ogr, boolean Trg, String FileName, String PathDat, boolean DDL, int SUBP, String UserName, String PasswordD, String Host, String NazivDat, Connection conn, int CiljDB, String UrlOdbc)
  {
    super(parent, title, modal);
    
    try
    {
      WarningDat = outputFileW;
      OneFileOnly = OneFile;
      DDLOnly = DDL;
      IndeksiP = IndP;
      IndeksiF = IndF;
      IndeksiU = IndU;
      Ogranicenja = Ogr;
      Trigeri = Trg;
      FileIme = FileName;
      PutanjaDatoteke = PathDat;
      Prefiks = FileName;   
      DBMS = SUBP;
      UserNameDatabase = UserName;
      PasswordDatabase = PasswordD;
      HostDatabase = Host;
      NazivDatoteke = NazivDat;
      con= conn;
      TargetDB = CiljDB;
      url = UrlOdbc;
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    
  }

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(519, 359));
    this.getContentPane().setLayout(null);
    pane.setText("jTextPane1");
    btnCancel.setText("Cancel");
    btnCancel.setBounds(new Rectangle(365, 280, 80, 30));
    btnCancel.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCancel_actionPerformed(e);
        }
      });
    btnHelp.setBounds(new Rectangle(455, 280, 35, 30));
    btnHelp.setIcon(imageHelp);
    btnHelp.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnHelp_actionPerformed(e);
        }
      });
    jTabbedPane1.setBounds(new Rectangle(15, 10, 475, 255));
    jPanel1.setLayout(null);
    jPanel2.setLayout(null);
    jScrollPane3.setBounds(new Rectangle(15, 15, 440, 200));
    jScrollPane1.setBounds(new Rectangle(15, 15, 440, 200));
    List1.setModel(modellst1);
    List1.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          List1_mouseClicked(e);
        }
      });
    btnRun.setText("View");
    btnRun.setBounds(new Rectangle(175, 280, 80, 30));
    btnRun.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jBtnRun_actionPerformed(e);
        }
      });
    btnExec.setText("Execute");
    btnExec.setBounds(new Rectangle(270, 280, 80, 30));
    btnExec.setSize(new Dimension(80, 30));
    btnExec.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnExec_actionPerformed(e);
        }
      });
    jScrollPane1.getViewport().add(List1, null);
    jPanel1.add(jScrollPane1, null);
    jTabbedPane1.addTab("Scripts", jPanel1);
    jScrollPane3.getViewport().add(pane, null);
    jPanel2.add(jScrollPane3, null);
    jTabbedPane1.addTab("Messages", jPanel2);
    this.getContentPane().add(btnExec, null);
    this.getContentPane().add(btnRun, null);
    this.getContentPane().add(jTabbedPane1, null);
    this.getContentPane().add(btnHelp, null);
    this.getContentPane().add(btnCancel, null);
    inicijalizacija();   
    popuniPane(WarningDat);
  }
  
  private String[] setAtt()
  {  
    String[] Skriptovi;
    Skriptovi = null;
    if (OneFileOnly)
    {
      Skriptovi = new String[1];
      Skriptovi[0] = "View Command DDL File (" + Prefiks + ".sql)";
    }else
    {
      Skriptovi = new String[5];
      int i=1;
      Skriptovi[0] = "View Command DDL File (" + Prefiks + "_DDL.sql)";
      Skriptovi[1] = "View Command DDL File (" + Prefiks + "_Tab.sql)";
      if (Ogranicenja)
      {
        i=i+1;
        Skriptovi[i] = "View Command DDL File (" + Prefiks + "_Con.sql)";
      }
      if(IndeksiP==true || IndeksiF==true || IndeksiU==true)
      {
        i=i+1;
        Skriptovi[i] = "View Command DDL File (" + Prefiks + "_Ind.sql)";
      }
      if (Trigeri)
      {
        i=i+1;
        Skriptovi[i] = "View Command DDL File (" + Prefiks + "_Trg.sql)";
      }
    }
    return Skriptovi;
  }
  private void inicijalizacija()
  {
    
    String[] ListaFileova = setAtt();
    modellst1.removeAllElements();    
    for(int j=0;j<ListaFileova.length;j++)
    {       
      modellst1.addElement(ListaFileova[j]); 
    }    
    List1.setSelectedIndex(0);  
    if (DDLOnly)
    {
      btnExec.setEnabled(false);
    }
  }
  
  void popuniPane(File outputFileW)
  {
    String fn = outputFileW.toString() ;
    long FileSize = outputFileW.length(); 
    if (FileSize != 0)
    {
        try {
             FileReader fr = 
                       new FileReader(fn);
             pane.read(fr, null);
             fr.close();
         }
         catch (IOException eio) {
             System.err.println(eio);
         }
    }else
    {
      String tekst;
      tekst = "Processing Complete: 0 warrning(s), 0 error(s)";
      pane.setText(tekst);
    }
  }
  

  private void btnCancel_actionPerformed(ActionEvent e)
  {
    this.dispose();
  }
 
 
  private void jBtnRun_actionPerformed(ActionEvent e)
  {
    String StavkaListe, ImeDat;
    int a,b;
    StavkaListe = List1.getSelectedValue().toString();    
    a = StavkaListe.indexOf("(") + 1;   
    b = StavkaListe.length() - 1;
    ImeDat = StavkaListe.substring(a,b);
    
    LookScript Prikaz = new LookScript(this, "Script", true,PutanjaDatoteke ,ImeDat, DDLOnly, DBMS, UserNameDatabase, PasswordDatabase, HostDatabase, NazivDatoteke, con, url, TargetDB);    
    Settings.Center(Prikaz);
    Prikaz.setVisible(true);    
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
        str = "osql -E " + " -i " + "\"" + NazivDatoteke+".sql" + "\""; 
      }else{
        str = "osql -S " + HostDatabase + " -U " + UserNameDatabase + " -P " + PasswordDatabase + " -i " + "\""+ NazivDatoteke+".sql" + "\""; //f:\skriptovi\script.sql; // local mora da se stavi u zagradu - S (local)
      } 
      outBat.println(str);
    }    
   }else //Target je odbc
   {     
     if (DBMS==2) 
     {
      
     }else 
     {      
        str = "osql -D " + url + " -i " + "\"" + NazivDatoteke+".sql" + "\""; //f:\skriptovi\script.sql; // local mora da se stavi u zagradu - S (local)
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
       System.err.println(ioe);  
    }    
   //JOptionPane.showMessageDialog(null, "The generation completed successfully.", "Generation", JOptionPane.INFORMATION_MESSAGE);
  }

  private void btnHelp_actionPerformed(ActionEvent e)
  {
    Help hlp =new  Help((IISFrameMain) getParent().getParent(),getTitle(), true, con );
    Settings.Center(hlp);
    hlp.setVisible(true);
  }

  private void List1_mouseClicked(MouseEvent e)
  {
    if (e.getClickCount() == 2)
    {
      String StavkaListe, ImeDat;
      int a,b;
      StavkaListe = List1.getSelectedValue().toString();    
      a = StavkaListe.indexOf("(") + 1;   
      b = StavkaListe.length() - 1;
      ImeDat = StavkaListe.substring(a,b);
    
      LookScript Prikaz = new LookScript(this, "Script", true,PutanjaDatoteke ,ImeDat, DDLOnly, DBMS, UserNameDatabase, PasswordDatabase, HostDatabase, NazivDatoteke, con, url, TargetDB);    
      Settings.Center(Prikaz);
      Prikaz.setVisible(true);    
    }
  }
}