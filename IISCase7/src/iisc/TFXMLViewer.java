package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.AbstractAction;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.JTextArea;
import java.sql.*;
import javax.swing.JEditorPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.text.html.*;
import java.awt.TextArea;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.Color;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.XMLReaderFactory;
import java.util.*;
import org.apache.xerces.parsers.XML11Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.stream.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.swing.JToolBar;
import java.awt.event.WindowEvent;

public class TFXMLViewer extends JDialog 
{
  public static final int FORM_TYPE_SPEC = 0;
  public static final int APP_SYS_SPEC = 1;
  
  private JButton jButton1 = new JButton();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private static final String header ="";
  private MyTextPane XmlText = new MyTextPane();
  private String XmlFormTypeText;
  private String XmlShemaFormTypeText;
  private JButton btnClose = new JButton();
  private JButton btnSave = new JButton();
  private JToolBar jToolBar1 = new JToolBar();
  private JButton btnXMLscheme = new JButton();
  private String type;
  private int option;
                                      
  public TFXMLViewer(IISFrameMain parent, int[] id, Connection conn, String _type, int _option)
  {
      super(parent, "XML specification", true);
      
      type = _type;
      option = _option;
      XMLGenerator xmlGenerator = new XMLGenerator(conn);
      
      try
      {
        jbInit();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
     if (type.equals("xml"))
      setTitle("Export to XML Document");
      else
      setTitle("Form Type XML Schema");
      if (option == FORM_TYPE_SPEC)
      {
          XmlFormTypeText = xmlGenerator.GenerateFormTypeXMLDesc(id);
          XmlShemaFormTypeText = xmlGenerator.GetFormTypeXmlShema();
          
          if (type.equals("xml"))
          {
              XmlText.setText(XmlFormTypeText);
          }
          else
          {
              XmlText.setText(XmlShemaFormTypeText);
          }
          
          try
          {      
              File f = new File("temp.xsd");
              FileWriter filew = new FileWriter(f);
              filew.write(XmlShemaFormTypeText);
              filew.close();
          } 
          catch (Exception e) 
          {
              System.out.println("Exception: " + e);
          }
          try
          {      
              FileOutputStream fos = new FileOutputStream("temp.xml");
              Writer out = new OutputStreamWriter(fos, "UTF8");
              out.write(XmlFormTypeText);
              out.close();

          } 
          catch (Exception e) 
          {
              System.out.println("Exception: " + e);
          }
          
          if (isValid("temp.xml", "temp.xsd"))
          { 
              System.out.println("Xml specification is valid!");
          }
          else
          {
              System.out.println("Xml specification is not valid!");
          }
      }
      else
      {
          XmlFormTypeText = xmlGenerator.GenerateAppSysXMLDesc(id[0]);
          XmlShemaFormTypeText = xmlGenerator.GetAppSysXmlShema();
          
          if (type.equals("xml"))
          {
              XmlText.setText(XmlFormTypeText);
          }
          else
          {
              XmlText.setText(XmlShemaFormTypeText);
          }
          
          try
          {      
              File f = new File("temp.xsd");
              FileWriter filew = new FileWriter(f);
              filew.write(XmlShemaFormTypeText);
              filew.close();
          } 
          catch (Exception e) 
          {
              System.out.println("Exception: " + e);
          }
          
          try 
          {
              FileOutputStream fos = new FileOutputStream("temp.xml");
              Writer out = new OutputStreamWriter(fos, "UTF8");
              out.write(XmlFormTypeText);
              out.close();
          } 
          catch (IOException e) 
          {
              e.printStackTrace();
          }
       
          if (isValid("temp.xml", "temp.xsd"))
          { 
              System.out.println("Xml specification is valid!");
          }
          else
          {
              System.out.println("Xml specification is not valid!");
          }
      }
  }
  
  public TFXMLViewer(IISFrameMain parent, int AS_id, Connection conn, String _type, int _option, int ownedTF[], int referencedTF[])
  {
      super(parent, "XML specification", true);
      
      type = _type;
      option = _option;
      XMLGenerator xmlGenerator = new XMLGenerator(conn);
      
      try
      {
        jbInit();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
       if (type.equals("xml"))
      setTitle("Export to XML Document");
      else
      setTitle("Form Type XML Schema");
      if (option == FORM_TYPE_SPEC)
      {
          int[] tempArr = {AS_id};
          XmlFormTypeText = xmlGenerator.GenerateFormTypeXMLDesc(tempArr);
          XmlShemaFormTypeText = xmlGenerator.GetFormTypeXmlShema();
          
          if (type.equals("xml"))
          {
              XmlText.setText(XmlFormTypeText);
          }
          else
          {
              XmlText.setText(XmlShemaFormTypeText);
          }
          
          try
          {      
              File f = new File("temp.xsd");
              FileWriter filew = new FileWriter(f);
              filew.write(XmlShemaFormTypeText);
              filew.close();
          } 
          catch (Exception e) 
          {
              System.out.println("Exception: " + e);
          }
          try
          {      
              FileOutputStream fos = new FileOutputStream("temp.xml");
              Writer out = new OutputStreamWriter(fos, "UTF8");
              out.write(XmlFormTypeText);
              out.close();

          } 
          catch (Exception e) 
          {
              System.out.println("Exception: " + e);
          }
          
          if (isValid("temp.xml", "temp.xsd"))
          { 
              System.out.println("Xml specification is valid!");
          }
          else
          {
              System.out.println("Xml specification is not valid!");
          }
      }
      else
      {
          XmlFormTypeText = xmlGenerator.GenerateAppSysXMLDesc(AS_id, ownedTF, referencedTF);
          XmlShemaFormTypeText = xmlGenerator.GetAppSysXmlShema();
          
          if (type.equals("xml"))
          {
              XmlText.setText(XmlFormTypeText);
          }
          else
          {
              XmlText.setText(XmlShemaFormTypeText);
          }
          
          try
          {      
              File f = new File("temp.xsd");
              FileWriter filew = new FileWriter(f);
              filew.write(XmlShemaFormTypeText);
              filew.close();
          } 
          catch (Exception e) 
          {
              System.out.println("Exception: " + e);
          }
          
          try 
          {
              FileOutputStream fos = new FileOutputStream("temp.xml");
              Writer out = new OutputStreamWriter(fos, "UTF8");
              out.write(XmlFormTypeText);
              out.close();
          } 
          catch (IOException e) 
          {
              e.printStackTrace();
          }
       
          if (isValid("temp.xml", "temp.xsd"))
          { 
              System.out.println("Xml specification is valid!");
          }
          else
          {
              System.out.println("Xml specification is not valid!");
          }
      }
  }
  
  public TFXMLViewer(IISFrameMain parent, String _type, int option)
  {
      super(parent, "Form Type XML Schema", true);
      type = _type;
      XMLGenerator xmlGenerator = new XMLGenerator(null);
      
      try
      {
        jbInit();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
  
      //XmlText.setContentType("text/xml");
      XmlFormTypeText = "";
      
      if (option == FORM_TYPE_SPEC)
      {
          XmlShemaFormTypeText = xmlGenerator.GetFormTypeXmlShema();
      }
      else
      {
          XmlShemaFormTypeText = xmlGenerator.GetAppSysXmlShema();
      }
      
      XmlText.setText(XmlShemaFormTypeText);
      
  }
  
  private void jbInit() throws Exception
  {
      this.setResizable(true);
      this.setSize(new Dimension(734, 504));
      this.getContentPane().setLayout(null);
    this.addComponentListener(new java.awt.event.ComponentAdapter()
      {
        public void componentResized(ComponentEvent e)
        {
          this_componentResized(e);
        }
      });
      this.addWindowListener(new java.awt.event.WindowAdapter()
      {
        public void windowClosed(WindowEvent e)
        {
          this_windowClosed(e);
        }
      });
      jScrollPane1.setBounds(new Rectangle(10, 10, 710, 410));
      XmlText.setFont(new Font("SansSerif", 1, 11));
      XmlText.setText("XML");
      XmlText.setEditable(false);
      //XmlText.setSize(new Dimension(1300, 21));
      XmlText.setPreferredSize(new Dimension(1300, 21));
      btnClose.setMaximumSize(new Dimension(50, 30));
      btnClose.setPreferredSize(new Dimension(50, 30));
      btnClose.setText("Close");
      btnClose.setBounds(new Rectangle(640, 430, 80, 30));
      btnClose.setMinimumSize(new Dimension(50, 30));
      btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnClose_actionPerformed(e);
        }
      });
      btnClose.setFont(new Font("SansSerif", 0, 11));
      btnSave.setMaximumSize(new Dimension(50, 30));
      btnSave.setPreferredSize(new Dimension(50, 30));
      btnSave.setText("Save");
      btnSave.setBounds(new Rectangle(550, 430, 80, 30));
      btnSave.setMinimumSize(new Dimension(50, 30));
      btnSave.setFont(new Font("SansSerif", 0, 11));
      btnSave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnSave_actionPerformed(e);
        }
      });
      btnXMLscheme.setMaximumSize(new Dimension(50, 30));
      btnXMLscheme.setPreferredSize(new Dimension(50, 30));
      btnXMLscheme.setText("XML Schema");
      btnXMLscheme.setBounds(new Rectangle(435, 430, 105, 30));
      btnXMLscheme.setMinimumSize(new Dimension(50, 30));
      btnXMLscheme.setFont(new Font("SansSerif", 0, 11));
      btnXMLscheme.addActionListener(new ActionListener()
      {
          public void actionPerformed(ActionEvent e)
          {
              btnXML_actionPerformed(e);
          }
      });
    
      jScrollPane1.getViewport().add(XmlText, null);
      
      if(type.equals("xml"))
          this.getContentPane().add(btnXMLscheme, null);
        
      this.getContentPane().add(btnSave, null);
      this.getContentPane().add(btnClose, null);
      this.getContentPane().add(jScrollPane1, null);
  }
  
  public boolean isValid(String source, String schema)
  {
      try
      {         
          
          XML11Configuration conf = new XML11Configuration();
          XMLReader reader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
            
          reader.setFeature("http://xml.org/sax/features/validation", true);
          reader.setFeature("http://apache.org/xml/features/validation/schema", true);
          reader.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", schema);


          MyDefaultHandler handler = new MyDefaultHandler();
          
          reader.setContentHandler(handler);
          reader.setErrorHandler(handler);
         
          reader.parse(source);
          
          return (handler.isValid)?true:false;	
    }
    catch (Exception e) 
    {
        XmlText.setText(e.toString());
    }
      return false;
    
    
	}

  private void btnClose_actionPerformed(ActionEvent e)
  {
      this.dispose();
  }
  private void btnXML_actionPerformed(ActionEvent e)
  {
      TFXMLViewer xmlv=new TFXMLViewer((IISFrameMain)getParent(), "xsd", option);
      Settings.CenterDown(xmlv,25);
      xmlv.setVisible(true);
  }
  
  class MyFilter extends javax.swing.filechooser.FileFilter 
  {
      private String type;
      public boolean accept(File file) 
      {
          String filename = file.getName();
          return filename.endsWith("."+type);
      }
      MyFilter(String t)
      {
        type=t;
      }
      public String getDescription() 
      {
          return "*."+type;
      }
  }

  private void btnSave_actionPerformed(ActionEvent e)
  {
      String filename = "Untitled1";
      JFileChooser fc = new JFileChooser(new File(filename));
      fc.addChoosableFileFilter(new MyFilter(type));
    
      int result = fc.showSaveDialog(this);
    
    // Determine which button was clicked to close the dialog
      File selFile = fc.getSelectedFile();
      boolean can=true;
      
      if(selFile.isFile())
      {
          PromptDialog pd=new PromptDialog((Frame)getParent(),"",true);
          Settings.Center(pd);
          pd.setVisible(true);
          
          if(!pd.overwrite)
            can=false;
      }
      
      String[] arr=ODBCList.splitString(selFile.getName(),".");
      
      if(!arr[arr.length-1].equals(type))
      {  
          JOptionPane.showMessageDialog(this, "<html><center>Bad file extension!", "Error", JOptionPane.ERROR_MESSAGE);
          can=false;
      }
      
      if(can)
      {    
          switch (result) 
          {
              case JFileChooser.APPROVE_OPTION:
                  Writer output = null;
                  try 
                  {
                      if (type.equals("xsd"))
                      {
                          output = new BufferedWriter( new FileWriter(selFile.getPath()) );
                          output.write(this.XmlShemaFormTypeText);
                      }
                      else
                      {
                          output = new BufferedWriter( new FileWriter(selFile.getPath()) );
                          output.write(this.XmlFormTypeText);   
                      }
                  }
                  catch (IOException ex)
                  {
                      ex.printStackTrace();
                  }
                  finally 
                  {
                      try
                      {
                          if (output != null) 
                              output.close();
                      }
                      catch (IOException ex)
                      {
                          ex.printStackTrace();
                      }
                  }
                  break;
              
                  case JFileChooser.CANCEL_OPTION:
                      break;
              
                  case JFileChooser.ERROR_OPTION:
                      break;
            }
        }
    }
    
    private void this_windowClosed(WindowEvent e)
    {
        File s = new File("temp.xml");
        s.delete();
        s = new File("temp.xsd");
        s.delete();
    }
    
    class MyTextPane extends JTextPane
    {
        public boolean getScrollableTracksViewportWidth() 
        {
            return false;
        }
    }

  private void this_componentResized(ComponentEvent e)
  {jScrollPane1.setPreferredSize(new Dimension( this.getWidth()-10,this.getHeight()));
  jScrollPane1.setBounds(5,5, this.getWidth()-15,this.getHeight()-95);
  XmlText.setPreferredSize(new Dimension( this.getWidth()-10,this.getHeight()));
 XmlText.setBounds(0,0, this.getWidth()-10,this.getHeight()-90);
 btnClose.setBounds(new Rectangle(this.getWidth()-95, this.getHeight()-75, 80, 30));
 btnXMLscheme.setBounds(new Rectangle(this.getWidth()-320, this.getHeight()-75, 125, 30));
 btnSave.setBounds(new Rectangle(this.getWidth()-185, this.getHeight()-75, 80, 30));
  }
}