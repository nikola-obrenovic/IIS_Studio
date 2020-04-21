package iisc;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Font;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.AbstractAction;
import java.io.*;
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
import java.awt.event.WindowEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
public class XMLViewer extends JDialog 
{



  private JScrollPane jScrollPane1 = new JScrollPane();
 MyTextPane jTextPane1 = new MyTextPane();
  public JButton btnClose = new JButton();
  private Connection con;
  public JButton btnSave = new JButton();
  public String type="xml";
  public JButton btnXMLscheme = new JButton();
  public int as;
  static final String JAXP_SCHEMA_LANGUAGE =
    	"http://java.sun.com/xml/jaxp/properties/schemaLanguage";	
	static final String W3C_XML_SCHEMA =
	    "http://www.w3.org/2001/XMLSchema"; 
	static final String JAXP_SCHEMA_SOURCE =
    	"http://java.sun.com/xml/jaxp/properties/schemaSource";

  public XMLViewer()
  {
    this(null, "", false,null,"");
  }

   public XMLViewer(IISFrameMain parent, String title, boolean modal, Connection conn, String t )
  {
    super(parent, title, modal);
    try
    {
    type=t;
      con=conn;
     
      JDBCQuery query=new JDBCQuery(con);
     ResultSet rs;
     if(((PTree)(parent.desktop.getSelectedFrame())).tree.getSelectionPath().getLastPathComponent().toString().equals("Relation Schemes"))
    rs=query.select("select * from IISC_App_System where AS_name='" + ((PTree)(parent.desktop.getSelectedFrame())).tree.getSelectionPath().getParentPath().getLastPathComponent().toString() + "'  and PR_id="+((PTree)(parent.desktop.getSelectedFrame())).ID);
else
rs=query.select("select * from IISC_App_System where AS_name='" + ((PTree)(parent.desktop.getSelectedFrame())).tree.getSelectionPath().getLastPathComponent().toString() + "'  and PR_id="+((PTree)(parent.desktop.getSelectedFrame())).ID);

 if(rs.next())
  as=rs.getInt("AS_id");
  query.Close();
         Iterator it=((PTree)((IISFrameMain)parent).desktop.getSelectedFrame()).WindowsManager.iterator();
      while(it.hasNext())
      {
        Object obj=(Object)it.next();
        Class cls=obj.getClass();
        if(cls==this.getClass())
        {if(((XMLViewer)obj).as==as && ((XMLViewer)obj).type.equals(type) )
        { ((XMLViewer)obj).dispose();
        }
        }
      }
   jbInit();  }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
  private void jbInit() throws Exception
  { this.setResizable(true);
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
    jTextPane1.setFont(new Font("SansSerif", 1, 11));
    jTextPane1.setText("jTextPane1");
    jTextPane1.setEditable(false);
    jTextPane1.setPreferredSize(new Dimension(1300, 21));
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
    btnXMLscheme.setText("DB XML Schema");
    btnXMLscheme.setBounds(new Rectangle(415, 430, 125, 30));
    btnXMLscheme.setMinimumSize(new Dimension(50, 30));
    btnXMLscheme.setFont(new Font("SansSerif", 0, 11));
    btnXMLscheme.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnXML_actionPerformed(e);
        }
      });
    //jTextPane1.setEditable(false);
    jScrollPane1.getViewport().add(jTextPane1, null);
    if(type.equals("xml"))
    this.getContentPane().add(btnXMLscheme, BorderLayout.NORTH);
    this.getContentPane().add(btnSave, BorderLayout.SOUTH);
    this.getContentPane().add(btnClose, BorderLayout.SOUTH);
    this.getContentPane().add(jScrollPane1, BorderLayout.SOUTH);
        try
    {JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;  

    rs=query.select("select * from IISC_PRIMITIVE_TYPE order by PT_mnemonic");
    String types=new String();
    while(rs.next())
    types=types+ tab(6)+"<xs:enumeration value=\""+ rs.getString("PT_mnemonic") +"\"/> \n";
      query.Close();
     jTextPane1.setText(getXML(types));
     String msg=new String();
     if(type.equals("xml"))
      if(isValid("temp.xml","temp.xsd")){
  			msg = "File is valid";	
  		}else{
  			msg = "ERROR! INVALID FILE";
  		}
  		System.out.println(msg);
    }
    catch(SQLException e)
    {
    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    }
  private String getXML(String types)
  {
   String  str="<?xml  version=\"1.0\"  encoding=\"UTF-8\"?>\n"+                              
"<xs:schema  xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"  elementFormDefault=\"qualified\">\n"+                              
tab(1)+"<xs:element  name=\"action\">  \n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:attribute  name=\"actType\"  use=\"required\">  \n"+                              
tab(4)+"<xs:simpleType>  \n"+                                    
tab(5)+"<xs:restriction  base=\"xs:NMTOKEN\">\n"+                            
tab(6)+"<xs:enumeration  value=\"NO_ACTION\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"CASCADE\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"SET_DEFAULT\"/>  \n"+                            
tab(6)+"<xs:enumeration  value=\"SET_NULL\"/>  \n"+                            
tab(5)+"</xs:restriction>\n"+                                    
tab(4)+"</xs:simpleType>\n"+                                    
tab(3)+"</xs:attribute>  \n"+                                    
tab(2)+"</xs:complexType>  \n"+                                    
tab(1)+"</xs:element>  \n"+                                    
tab(1)+"<xs:element  name=\"attCond\"  type=\"xs:string\"/>\n"+                              
tab(1)+"<xs:element  name=\"attrConstraint\">  \n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:sequence minOccurs=\"0\" >\n"+                                    
tab(4)+"<xs:element  ref=\"consRelScheme\"  maxOccurs=\"unbounded\"/>\n"+                              
tab(4)+"<xs:element  ref=\"attCond\" minOccurs=\"0\"/>\n"+                              
tab(4)+"<xs:element  ref=\"validateTime\" minOccurs=\"0\"/>      \n"+                            
tab(3)+"</xs:sequence>\n"+                                    
tab(3)+"<xs:attribute  name=\"conName\"  type=\"xs:string\"  use=\"required\"/>\n"+                                  
tab(3)+"<xs:attribute  name=\"conClass\"  use=\"required\">\n"+                              
tab(4)+"<xs:simpleType>  \n"+                                    
tab(5)+"<xs:restriction  base=\"xs:NMTOKEN\">\n"+                            
tab(6)+"<xs:enumeration  value=\"NOTNULL\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"CHECK\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"PRIMARY_KEY\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"UNIQUE\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"REFERENCES\"/>\n"+                            
tab(5)+"</xs:restriction>\n"+                                    
tab(4)+"</xs:simpleType>\n"+                                    
tab(3)+"</xs:attribute>  \n"+                                    
tab(2)+"</xs:complexType>  \n"+                                    
tab(1)+"</xs:element>  \n"+                                    
tab(1)+"<xs:element  name=\"attribute\">  \n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:sequence>\n"+                                    
//tab(4)+"<xs:choice>\n"+                                    
tab(4)+"<xs:element  ref=\"domain\"/>  \n"+                            
//tab(5)+"<xs:element  ref=\"predefDomain\"/>\n"+                            
//tab(4)+"</xs:choice>  \n"+                                    
tab(4)+"<xs:element  ref=\"attrConstraint\"  minOccurs=\"0\"  maxOccurs=\"unbounded\"/>  \n"+                                  
tab(3)+"</xs:sequence>\n"+                                    
tab(3)+"<xs:attribute  name=\"attName\"  type=\"xs:string\"  use=\"required\"/>\n"+                                  
tab(3)+"<xs:attribute  name=\"attMod\"  use=\"optional\">\n"+                              
tab(4)+"<xs:simpleType>  \n"+                                    
tab(5)+"<xs:restriction  base=\"xs:NMTOKEN\">\n"+                            
tab(6)+"<xs:enumeration  value=\"yes\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"no\"/>  \n"+                            
tab(5)+"</xs:restriction>\n"+                                    
tab(4)+"</xs:simpleType>\n"+                                    
tab(3)+"</xs:attribute>  \n"+                                    
tab(2)+"</xs:complexType>  \n"+                                    
tab(1)+"</xs:element>  \n"+                                    
tab(1)+"<xs:element  name=\"conSchAttr\">  \n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:attribute  name=\"conAttName\"  type=\"xs:string\"  use=\"required\"/>\n"+                                  
tab(2)+"</xs:complexType>  \n"+                                    
tab(1)+"</xs:element>  \n"+                                    
/*tab(1)+"<xs:element  name=\"conSchAttr\">\n"+                            
tab(2)+"<xs:complexType/>  \n"+                                    
tab(1)+"</xs:element>  \n"+  */                                  
tab(1)+"<xs:element  name=\"conValActivity\">  \n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:sequence>\n"+                                    
tab(4)+"<xs:element  ref=\"operation\"/>\n"+                            
tab(4)+"<xs:element  ref=\"action\"/>\n"+                            
tab(3)+"</xs:sequence>\n"+                                    
tab(2)+"</xs:complexType>  \n"+                                              
tab(1)+"</xs:element>  \n"+                                    
tab(1)+"<xs:element  name=\"consRelScheme\">\n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:sequence>\n"+                                    
tab(4)+"<xs:element  ref=\"conSchAttr\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n"+                              
tab(4)+"<xs:element  ref=\"conValActivity\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n"+                              
tab(3)+"</xs:sequence>\n"+                                    
tab(3)+"<xs:attribute  name=\"schemeName\"  type=\"xs:string\"  use=\"required\"/>\n"+                                  
tab(3)+"<xs:attribute  name=\"schemeRole\"  use=\"required\">  \n"+                              
tab(4)+"<xs:simpleType>  \n"+                                    
tab(5)+"<xs:restriction  base=\"xs:NMTOKEN\">\n"+                            
tab(6)+"<xs:enumeration  value=\"referencing\"/>  \n"+                            
tab(6)+"<xs:enumeration  value=\"referenced\"/>\n"+                            
tab(5)+"</xs:restriction>\n"+                                    
tab(4)+"</xs:simpleType>\n"+                                    
tab(3)+"</xs:attribute>  \n"+                                    
tab(2)+"</xs:complexType>  \n"+                                    
tab(1)+"</xs:element>  \n"+                                    
tab(1)+"<xs:element  name=\"domCondition\"  type=\"xs:string\"/>  \n"+                              
tab(1)+"<xs:element  name=\"domain\">  \n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:sequence>\n"+                                    
tab(4)+"<xs:element  ref=\"predefDomain\"/>\n"+                            
tab(4)+"<xs:element  ref=\"domain\" minOccurs=\"0\" />  \n"+                            
tab(4)+"<xs:element  ref=\"domCondition\" minOccurs=\"0\" />      \n"+                            
tab(3)+"</xs:sequence>\n"+                                    
tab(3)+"<xs:attribute  name=\"length\"  type=\"xs:string\"/>\n"+                              
tab(2)+"</xs:complexType>  \n"+                                    
tab(1)+"</xs:element>  \n"+                                    
tab(1)+"<xs:element  name=\"operation\">  \n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:attribute  name=\"operType\"  use=\"required\">\n"+                              
tab(4)+"<xs:simpleType>  \n"+                                    
tab(5)+"<xs:restriction  base=\"xs:NMTOKEN\">\n"+                            
tab(6)+"<xs:enumeration  value=\"ON_UPDATE\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"ON_DELETE\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"ON_INSERT\"/>\n"+                            
tab(5)+"</xs:restriction>\n"+                                    
tab(4)+"</xs:simpleType>\n"+                                    
tab(3)+"</xs:attribute>  \n"+                                    
tab(2)+"</xs:complexType>  \n"+                                    
tab(1)+"</xs:element>  \n"+                                    
tab(1)+"<xs:element  name=\"predefDomain\">  \n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:attribute  name=\"type\"  use=\"required\">\n"+                              
tab(4)+"<xs:simpleType>  \n"+                                    
tab(5)+"<xs:restriction  base=\"xs:NMTOKEN\">\n"+                            
types+                      
tab(5)+"</xs:restriction>\n"+                                    
tab(4)+"</xs:simpleType>\n"+                                    
tab(3)+"</xs:attribute>  \n"+                                    
tab(3)+"<xs:attribute  name=\"length\"  type=\"xs:string\"/>\n"+                              
tab(2)+"</xs:complexType>  \n"+                                    
tab(1)+"</xs:element>  \n"+                                    
tab(1)+"<xs:element  name=\"relScheme\">  \n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:sequence>\n"+                                    
tab(4)+"<xs:element  ref=\"role\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n"+                              
tab(4)+"<xs:element  ref=\"attribute\"  maxOccurs=\"unbounded\"/>  \n"+                              
tab(4)+"<xs:element  ref=\"schemeConstraint\"  minOccurs=\"0\"  maxOccurs=\"unbounded\"/>\n"+                                  
tab(3)+"</xs:sequence>\n"+                                    
tab(3)+"<xs:attribute  name=\"schemeName\"  type=\"xs:string\"  use=\"required\"/>\n"+                                  
tab(3)+"<xs:attribute  name=\"dbSchemeName\"  type=\"xs:string\"  use=\"required\"/>\n"+                                  
tab(2)+"</xs:complexType>  \n"+                                    
tab(1)+"</xs:element>  \n"+                                    
tab(1)+"<xs:element  name=\"role\">\n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:attribute  name=\"roleID\"  use=\"required\">\n"+                              
tab(4)+"<xs:simpleType>  \n"+                                    
tab(5)+"<xs:restriction  base=\"xs:NMTOKEN\">\n"+                            
tab(6)+"<xs:enumeration  value=\"read\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"insert\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"modify\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"delete\"/>\n"+                            
tab(5)+"</xs:restriction>\n"+                                    
tab(4)+"</xs:simpleType>\n"+                                    
tab(3)+"</xs:attribute>  \n"+                                    
tab(2)+"</xs:complexType>  \n"+                                    
tab(1)+"</xs:element>  \n"+                                    
tab(1)+"<xs:element  name=\"sbcCond\"  type=\"xs:string\"/>\n"+                              
tab(1)+"<xs:element  name=\"schCond\"  type=\"xs:string\"/>\n"+                              
tab(1)+"<xs:element  name=\"schemeConstraint\">\n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:sequence  minOccurs=\"0\" >\n"+                                    
tab(4)+"<xs:element  ref=\"consRelScheme\"  maxOccurs=\"unbounded\"/>\n"+                              
tab(4)+"<xs:element  ref=\"schCond\"  minOccurs=\"0\"/>\n"+                              
tab(4)+"<xs:element  ref=\"validateTime\" minOccurs=\"0\"/>      \n"+                            
tab(3)+"</xs:sequence>\n"+                                    
tab(3)+"<xs:attribute  name=\"conName\"  type=\"xs:string\"  use=\"required\"/>\n"+                                  
tab(3)+"<xs:attribute  name=\"conClass\"  use=\"required\">\n"+                              
tab(4)+"<xs:simpleType>  \n"+                                    
tab(5)+"<xs:restriction  base=\"xs:NMTOKEN\">\n"+                            
tab(6)+"<xs:enumeration  value=\"CHECK\"/>  \n"+                            
tab(6)+"<xs:enumeration  value=\"PRIMARY_KEY\"/>  \n"+                            
tab(6)+"<xs:enumeration  value=\"UNIQUE\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"REFERENCES\"/>\n"+                            
tab(5)+"</xs:restriction>\n"+                                    
tab(4)+"</xs:simpleType>\n"+                                    
tab(3)+"</xs:attribute>  \n"+                                    
tab(2)+"</xs:complexType>  \n"+                                    
tab(1)+"</xs:element>  \n"+                                    
tab(1)+"<xs:element  name=\"subschConstraint\">\n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:sequence>\n"+                                    
tab(4)+"<xs:element  ref=\"consRelScheme\"  maxOccurs=\"unbounded\"/>\n"+                              
tab(4)+"<xs:element  ref=\"sbcCond\"  minOccurs=\"0\"/>\n"+                              
tab(4)+"<xs:element  ref=\"validateTime\"/>      \n"+                            
tab(3)+"</xs:sequence>\n"+                                    
tab(3)+"<xs:attribute  name=\"conName\"  type=\"xs:string\"  use=\"required\"/>\n"+                                  
tab(3)+"<xs:attribute  name=\"conClass\"  use=\"required\">\n"+                              
tab(4)+"<xs:simpleType>  \n"+                                    
tab(5)+"<xs:restriction  base=\"xs:NMTOKEN\">\n"+                            
tab(6)+"<xs:enumeration  value=\"ASSERTION\"/>\n"+                            
tab(5)+"</xs:restriction>\n"+                                    
tab(4)+"</xs:simpleType>\n"+                                    
tab(3)+"</xs:attribute>  \n"+                                    
tab(2)+"</xs:complexType>  \n"+                                    
tab(1)+"</xs:element>  \n"+                                    
tab(1)+"<xs:element  name=\"subschema\">  \n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:sequence>\n"+                                    
tab(4)+"<xs:element  ref=\"relScheme\"  maxOccurs=\"unbounded\"/>  \n"+                              
tab(4)+"<xs:element  ref=\"subschConstraint\"  minOccurs=\"0\"  maxOccurs=\"unbounded\"/>\n"+                                  
tab(3)+"</xs:sequence>\n"+                                    
tab(3)+"<xs:attribute  name=\"subschName\"  type=\"xs:string\"  use=\"required\"/>\n"+                                  
tab(2)+"</xs:complexType>  \n"+                                    
tab(1)+"</xs:element>  \n"+                                    
tab(1)+"<xs:element  name=\"validateTime\">  \n"+                            
tab(2)+"<xs:complexType>\n"+                                    
tab(3)+"<xs:attribute  name=\"validateTimeType\"  default=\"INITIALY_IMMEDIATE_NOT_DEFERRABLE\">\n"+                              
tab(4)+"<xs:simpleType>  \n"+                                    
tab(5)+"<xs:restriction  base=\"xs:NMTOKEN\">\n"+                            
tab(6)+"<xs:enumeration  value=\"INITIALY_DEFERRED\"/>  \n"+                            
tab(6)+"<xs:enumeration  value=\"INITIALY_IMMEDIATE_NOT_DEFERRABLE\"/>\n"+                            
tab(6)+"<xs:enumeration  value=\"INITIALY_IMMEDIATE_DEFERRABLE\"/>  \n"+                            
tab(5)+"</xs:restriction>\n"+                                    
tab(4)+"</xs:simpleType>\n"+                                    
tab(3)+"</xs:attribute>   \n"+                                                                                                                                                                          
tab(2)+"</xs:complexType>\n"+                                                                                                                                                                                   
tab(1)+"</xs:element>\n"+                                                                                                                                                                                                           
"</xs:schema>       ";
   try
   {      
        File f = new File("temp.xsd");
        FileWriter filew = new FileWriter(f);
        filew.write(str);
        filew.close();
   } 
       catch (Exception e) {
      System.out.println("Exception: " + e);
    }
if(type.equals("xsd"))
    {
 return str; 
    }
    else
    {
    return buildDom();
    }
  }
  public  String writeXml(Document doc) {
        try { 
        
            Source source = new DOMSource(doc);
            Writer s=new StringWriter( );
            StreamResult result = new StreamResult(s);  
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
          return  s.toString() ;

    } catch (TransformerConfigurationException e) {
      System.out.println("TransformerConfigurationException: " + e);
    } catch (TransformerException e) {
      System.out.println("TransformerException: " + e);
    }
    return null;
     }
	public static boolean isValid(String source, String schema){
		
	/*	DocumentBuilderFactory factory =
      		DocumentBuilderFactory.newInstance();
      	
      	//Configure DocumentBuilderFactory to generate a namespace-aware, 
      	// validating parser that uses XML Schema	
		factory.setNamespaceAware(true);
		
		//Set the property for schema validation to work
		factory.setValidating(true);
		
		try {
			
			//Set a factory attribute specify the parser language to use
			factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
			
			//Specify your schema file within the application
			//NOTE: You only need to do this if you did not 
			// declare your schema in the XML document 
			factory.setAttribute(JAXP_SCHEMA_SOURCE, schema);	
			
			//configure DocumentBuilderFactory to generate a namespace-aware, 
			// validating parser that uses XML Schema
			DocumentBuilder builder = factory.newDocumentBuilder();	
			
			//Add a custom error handler
			MyDefaultHandler dh = new MyDefaultHandler();
			builder.setErrorHandler(dh);
			
			//parse the document
	  		Document document = builder.parse(source); 
	  			
	  		return (dh.isValid)?true:false;	
	  		
		}catch (IllegalArgumentException iae) {
			// Happens if the parser does not support JAXP 1.2			
		  	System.out.println(iae + 
		  		"\nDownload the latest copy of JAXP from http://java.sun.com");	  
		}catch (Exception e) {
			//Any other errors
	  		System.err.println(e);
	  	}	  	  
		return false;*/
    try{
            // specify this in system properties
                  // or use one of the next 2 lines
           XML11Configuration conf = new XML11Configuration();
            XMLReader reader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
              
                  reader.setFeature("http://xml.org/sax/features/validation", true);
                  reader.setFeature("http://apache.org/xml/features/validation/schema", true);
            reader.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", schema);


            MyDefaultHandler handler = new MyDefaultHandler();
            
            reader.setContentHandler(handler);
            reader.setErrorHandler(handler);
           
            reader.parse(source);
         return (handler.isValid)?true:false;	}
        catch (Exception e) {
            e.printStackTrace();
        
        }
 return false;
    
    
	}
  private String tab(int i)
  { String str=new String();
    for(int j=1;j<=i;j++)
    str=str+"          ";
    return str;
  }
  public  String buildDom()
    {
           DocumentBuilderFactory factory =
           DocumentBuilderFactory.newInstance();
            factory.setValidating(true);   
        factory.setNamespaceAware(true);
        try {
      //   factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",  "http://www.w3.org/2001/XMLSchema");
      //   factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", new File("temp.xsd"));

         DocumentBuilder builder = factory.newDocumentBuilder();
         Document document = builder.newDocument();  // Create from whole cloth
           int b=0;
          document.createTextNode("\n"+tab(b));
          ResultSet rs,rs1,rs2,rs3,rs4;
          JDBCQuery query=new JDBCQuery(con),query1=new JDBCQuery(con),query2=new JDBCQuery(con),query3=new JDBCQuery(con),query4=new JDBCQuery(con);
          Element root = 
          (Element) document.createElement("subschema"); 
          root.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
        //  root.setAttribute("xsi:noNamespaceSchemaLocation","temp.xsd");
          rs=query.select("select * from IISC_App_system where AS_id=" +as +" and PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID);
          if(rs.next())
          root.setAttribute("subschName",rs.getString(3)+ " subschema");
          query.Close();
          document.appendChild(root);          
          b++;
          rs=query.select("select * from IISC_RELATION_SCHEME where AS_id=" +as +" and PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID);
          while(rs.next())
          {
          root.appendChild(document.createTextNode("\n"+tab(b)));
           b++;
          Element schema = (Element) document.createElement("relScheme"); 
          schema.setAttribute("schemeName",rs.getString("RS_desc"));
          String rsname=rs.getString("RS_name");
          int rsid=rs.getInt("RS_id");
          schema.setAttribute("dbSchemeName",rsname);
        /*  schema.appendChild(document.createTextNode("\n"+tab(b)));
          Element role = (Element) document.createElement("role"); 
           role.setAttribute("roleID","read");
           schema.appendChild(role);
           schema.appendChild(document.createTextNode("\n"+tab(b)));
           role = (Element) document.createElement("role"); 
           role.setAttribute("roleID","insert");
           schema.appendChild(role);
           schema.appendChild(document.createTextNode("\n"+tab(b)));
           role = (Element) document.createElement("role"); 
           role.setAttribute("roleID","modify");
           schema.appendChild(role);
           schema.appendChild(document.createTextNode("\n"+tab(b)));
           role = (Element) document.createElement("role"); 
           role.setAttribute("roleID","delete");
           schema.appendChild(role);*/
          
          
           rs1=query1.select("select * from IISC_RS_ATT,IISC_ATTRIBUTE,IISC_DOMAIN,IISC_PRIMITIVE_TYPE where IISC_DOMAIN.Dom_data_type=IISC_PRIMITIVE_TYPE.PT_id and IISC_ATTRIBUTE.DOM_id=IISC_DOMAIN.DOM_id and IISC_RS_ATT.Att_id=IISC_ATTRIBUTE.Att_id and IISC_RS_ATT.AS_id=" +as +"  and IISC_RS_ATT.RS_id=" +rsid +" and IISC_RS_ATT.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " order by Att_sequence");
          while(rs1.next())
          { int attid= rs1.getInt("Att_id") ;
           String attname= rs1.getString("Att_mnem") ;
           schema.appendChild(document.createTextNode("\n"+tab(b)));
           b++;
           
           Element att = (Element) document.createElement("attribute"); 
           att.setAttribute("attName",attname);
        
           Element dom = (Element) document.createElement("domain"); 
           String dl=""+rs1.getString("Dom_length");
           if(dl!=null && !dl.equals("") && !dl.equals("null"))
           dom.setAttribute("length",dl);
           Element pd = (Element) document.createElement("predefDomain"); 
           pd.setAttribute("type",rs1.getString("PT_mnemonic"));
           pd.setAttribute("length",rs1.getString("PT_length"));
           String constr=rs1.getString("Dom_reg_exp_str");
           Element con = (Element) document.createElement("domCondition"); 
           con.appendChild(document.createTextNode(constr));
           String parent=""+ rs1.getString("Dom_parent");
           
            b++;
           dom.appendChild(document.createTextNode("\n"+tab(b)));
          
           dom.appendChild(pd);
           
          
           Element domp = (Element) document.createElement("domain"); 
            
           if(parent!=null && !parent.equals("") && !parent.equals("null"))
           { 
           rs2=query2.select("select * from  IISC_DOMAIN,IISC_PRIMITIVE_TYPE  where IISC_DOMAIN.Dom_data_type=IISC_PRIMITIVE_TYPE.PT_id and Dom_id="+ parent +"  and  PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + "  ");
          while(rs2.next())
          {
          Element dom1 = (Element) document.createElement("domain"); 
           String dl1=""+rs2.getString("Dom_length");
           if(dl1!=null && !dl1.equals("") && !dl1.equals("null"))
           dom1.setAttribute("length",dl);
           Element pd1 = (Element) document.createElement("predefDomain"); 
           pd1.setAttribute("type",rs2.getString("PT_mnemonic"));
           pd1.setAttribute("length",rs2.getString("PT_length"));
           Element con1 = (Element) document.createElement("domCondition"); 
           String domcon= rs2.getString("Dom_reg_exp_str");
           con1.appendChild(document.createTextNode(domcon));
           b++;
           domp.appendChild(document.createTextNode("\n"+tab(b)));
           domp.appendChild(pd1);
           if(!domcon.equals(""))
           {domp.appendChild(document.createTextNode("\n"+tab(b)));
           domp.appendChild(con1);}
           b--;
           domp.appendChild(document.createTextNode("\n"+tab(b)));} 
         
         dom.appendChild(document.createTextNode("\n"+tab(b)));
           dom.appendChild(domp);
          query2.Close();
          
          } 
        
            
           if(constr!=null && !constr.equals("") && !constr.equals("null")){
           dom.appendChild(document.createTextNode("\n"+tab(b)));
           dom.appendChild(con);}
           b--;
           dom.appendChild(document.createTextNode("\n"+tab(b)));
            att.appendChild(document.createTextNode("\n"+tab(b)));
            att.appendChild(dom);
    
           int isnull=rs1.getInt("Att_null");
           if(isnull==0)
           { 
              att.appendChild(document.createTextNode("\n"+tab(b)));
              Element attrcon = (Element)document.createElement("attrConstraint"); 
             attrcon.setAttribute("conName","NOT NULL");
             attrcon.setAttribute("conClass","NOTNULL");
              att.appendChild(attrcon);
          }
         
           rs2=query2.select("select  *  from  IISC_RS_KEY,IISC_RSK_ATT  where Att_id="+ attid +" and IISC_RS_KEY.RS_rbrk=IISC_RSK_ATT.RS_rbrk and IISC_RS_KEY.RS_id=IISC_RSK_ATT.RS_id and IISC_RS_KEY.RS_id="+ rsid +"  and  IISC_RS_KEY.RS_primary_key=1  and  IISC_RS_KEY.As_id="+ as +"  and  IISC_RS_KEY.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " ");
          if(rs2.next())
          {int rbrk=rs2.getInt("RS_rbrk");  
          query2.Close();
             rs2=query2.select("select  *   from  IISC_RS_KEY,IISC_RSK_ATT  where IISC_RS_KEY.RS_rbrk= "+ rbrk +" and  Att_id<>"+ attid +" and IISC_RS_KEY.RS_rbrk=IISC_RSK_ATT.RS_rbrk and IISC_RS_KEY.RS_id=IISC_RSK_ATT.RS_id and IISC_RS_KEY.RS_id="+ rsid +"  and  IISC_RS_KEY.RS_primary_key=1  and  IISC_RS_KEY.As_id="+ as +"  and  IISC_RS_KEY.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " ");
          if(!rs2.next())
            { att.appendChild(document.createTextNode("\n"+tab(b)));
              Element attrcon = (Element)document.createElement("attrConstraint"); 
             attrcon.setAttribute("conName","PRIMARY KEY");
             attrcon.setAttribute("conClass","PRIMARY_KEY");
              att.appendChild(attrcon);
            }
          }
          query2.Close();
           rs2=query2.select("select  *  from  IISC_RS_KEY,IISC_RSK_ATT  where Att_id="+ attid +" and IISC_RS_KEY.RS_rbrk=IISC_RSK_ATT.RS_rbrk and IISC_RS_KEY.RS_id=IISC_RSK_ATT.RS_id and IISC_RS_KEY.RS_id="+ rsid +"  and  IISC_RS_KEY.RS_primary_key=0  and  IISC_RS_KEY.As_id="+ as +"  and  IISC_RS_KEY.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " ");
          if(rs2.next())
          {int rbrk=rs2.getInt("RS_rbrk");  
          query2.Close();
             rs2=query2.select("select  *   from  IISC_RS_KEY,IISC_RSK_ATT  where IISC_RS_KEY.RS_rbrk= "+ rbrk +" and  Att_id<>"+ attid +" and IISC_RS_KEY.RS_rbrk=IISC_RSK_ATT.RS_rbrk and IISC_RS_KEY.RS_id=IISC_RSK_ATT.RS_id and IISC_RS_KEY.RS_id="+ rsid +"  and  IISC_RS_KEY.RS_primary_key=0  and  IISC_RS_KEY.As_id="+ as +"  and  IISC_RS_KEY.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " ");
          if(!rs2.next())
            { att.appendChild(document.createTextNode("\n"+tab(b)));
              Element attrcon = (Element)document.createElement("attrConstraint"); 
             attrcon.setAttribute("conName","UNIQUE");
             attrcon.setAttribute("conClass","UNIQUE");
            
              att.appendChild(attrcon);
            }
          }
          query2.Close();
         rs2=query2.select("select  RSC_desc,RS_name,RSC_mod_action,RSC_ins_action,RSC_del_action    from  IISC_RS_Constraint,IISC_RSC_RS_SET as a,IISC_RSC_RS_SET as b, IISC_RELATION_SCHEME,IISC_RSC_LHS_RHS as c,IISC_RSC_LHS_RHS as d,IISC_RSC_ACTION   where IISC_RELATION_SCHEME.RS_ID=b.RS_id   and  IISC_RS_Constraint.LHS_RS_Set_id=a.RS_SET_id and IISC_RS_Constraint.LHS_id=c.RSCLR_id and IISC_RS_Constraint.RHS_id=d.RSCLR_id  and c.Att_id =" + attid + "    and a.RS_id=b.RS_id  and ( a.RS_id =" + rsid + " or (b.RS_id =" + rsid + " and IISC_RS_Constraint.RSC_type=2) ) and a.As_id="+ as +"  and  b.As_id="+ as +" and a.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " and b.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + "  and  IISC_RS_Constraint.RHS_RS_Set_id=b.RS_SET_id  and IISC_RS_Constraint.As_id="+ as +"  and  IISC_RS_Constraint.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " ");
              while(rs2.next()) 
              {
               
             String cons=rs2.getString("RSC_desc");
             String rsnamer=rs2.getString("RS_name");
              att.appendChild(document.createTextNode("\n"+tab(b)));
              Element attrcon = (Element)document.createElement("attrConstraint"); 
             attrcon.setAttribute("conName",cons);
             attrcon.setAttribute("conClass","REFERENCES");
             Element relcon = (Element)document.createElement("consRelScheme"); 
             relcon.setAttribute("schemeName",rsnamer);
             relcon.setAttribute("schemeRole","referenced");
             b++;
             attrcon.appendChild(document.createTextNode("\n"+tab(b)));
             attrcon.appendChild(relcon);
                      b++;
             Element attrschcon = (Element)document.createElement("conSchAttr"); 
             attrschcon.setAttribute("conAttName",attname);
             relcon.appendChild(document.createTextNode("\n"+tab(b)));
             relcon.appendChild(attrschcon);
             b++;
             Element conval = (Element)document.createElement("conValActivity");
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             Element oper = (Element)document.createElement("operation");
             Element action = (Element)document.createElement("action");
             oper.setAttribute("operType","ON_UPDATE");
             action.setAttribute("actType",getAction(rs2.getInt("RSC_mod_action")));
             conval.appendChild(oper);
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             conval.appendChild(action);
             b--;
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             relcon.appendChild(document.createTextNode("\n"+tab(b)));
             relcon.appendChild(conval);
            
             b++;
             conval = (Element)document.createElement("conValActivity");
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             oper = (Element)document.createElement("operation");
             action = (Element)document.createElement("action");
             oper.setAttribute("operType","ON_DELETE");
             action.setAttribute("actType",getAction(rs2.getInt("RSC_del_action")));
             conval.appendChild(oper);
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             conval.appendChild(action);
             b--;
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             relcon.appendChild(document.createTextNode("\n"+tab(b)));
             relcon.appendChild(conval);
             relcon.appendChild(document.createTextNode("\n"+tab(b)));
             conval = (Element)document.createElement("conValActivity");
             b++;
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             oper = (Element)document.createElement("operation");
             action = (Element)document.createElement("action");
             oper.setAttribute("operType","ON_INSERT");
             action.setAttribute("actType",getAction(rs2.getInt("RSC_ins_action")));
             conval.appendChild(oper);
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             conval.appendChild(action);
             b--;
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             
              relcon.appendChild(conval);
              b--;
             relcon.appendChild(document.createTextNode("\n"+tab(b)));
              b--; 
             attrcon.appendChild(document.createTextNode("\n"+tab(b)));
             att.appendChild(attrcon);
                }
            b--;
          query2.Close();
            att.appendChild(document.createTextNode("\n"+tab(b)));
           schema.appendChild(att);
           }
           query1.Close();
           b--;
         
         
         
         
         rs2=query2.select("select  IISC_RS_KEY.RS_rbrk,count(*)  from  IISC_RS_KEY,IISC_RSK_ATT  where   IISC_RS_KEY.RS_rbrk=IISC_RSK_ATT.RS_rbrk and IISC_RS_KEY.RS_id=IISC_RSK_ATT.RS_id and IISC_RS_KEY.RS_id="+ rsid +"  and  IISC_RS_KEY.RS_primary_key=1  and  IISC_RS_KEY.As_id="+ as +"  and  IISC_RS_KEY.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " group by IISC_RS_KEY.RS_rbrk");
        while( rs2.next() )
         { if(rs2.getInt(2)>1)
          {b++;
          int rbrk=rs2.getInt("RS_rbrk");  
          
             Element relcon = (Element)document.createElement("schemeConstraint"); 
             relcon.setAttribute("conName","PRIMARY KEY");
             relcon.setAttribute("conClass","PRIMARY_KEY");
             b++;
             relcon.appendChild(document.createTextNode("\n"+tab(b)));
              Element attrcon = (Element)document.createElement("consRelScheme"); 
             attrcon.setAttribute("schemeName",rsname);
             attrcon.setAttribute("schemeRole","referenced");
           
             rs3=query3.select("select  *   from  IISC_RS_KEY,IISC_RSK_ATT,IISC_ATTRIBUTE  where IISC_RSK_ATT.Att_id=IISC_ATTRIBUTE.Att_id and  IISC_RS_KEY.RS_rbrk= "+ rbrk +"  and IISC_RS_KEY.RS_rbrk=IISC_RSK_ATT.RS_rbrk and IISC_RS_KEY.RS_id=IISC_RSK_ATT.RS_id and IISC_RS_KEY.RS_id="+ rsid +"  and  IISC_RS_KEY.RS_primary_key=1  and  IISC_RS_KEY.As_id="+ as +"  and  IISC_RS_KEY.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " ");   
          while(rs3.next())
            {b++;
             attrcon.appendChild(document.createTextNode("\n"+tab(b)));
             Element attrschcon = (Element)document.createElement("conSchAttr"); 
             attrschcon.setAttribute("conAttName",rs3.getString("Att_mnem"));
            
             attrcon.appendChild(attrschcon); 
             
            b--; 
            }
            attrcon.appendChild(document.createTextNode("\n"+tab(b)));
            
             relcon.appendChild(attrcon);
             b--;
            query3.Close();
            relcon.appendChild(document.createTextNode("\n"+tab(b)));
            schema.appendChild(document.createTextNode("\n"+tab(b)));
           schema.appendChild(relcon); 
           b--;
          }
         }
          query2.Close();
       
        
        
        
         rs2=query2.select("select  IISC_RS_KEY.RS_rbrk,count(*)  from  IISC_RS_KEY,IISC_RSK_ATT  where   IISC_RS_KEY.RS_rbrk=IISC_RSK_ATT.RS_rbrk and IISC_RS_KEY.RS_id=IISC_RSK_ATT.RS_id and IISC_RS_KEY.RS_id="+ rsid +"  and  IISC_RS_KEY.RS_primary_key=0  and  IISC_RS_KEY.As_id="+ as +"  and  IISC_RS_KEY.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " group by IISC_RS_KEY.RS_rbrk");
        while(rs2.next())
         { if(rs2.getInt(2)>1)
          {// schema.appendChild(document.createTextNode("\n"+tab(b)));  
          b++;
          int rbrk=rs2.getInt("RS_rbrk");  
          
             Element relcon = (Element)document.createElement("schemeConstraint"); 
             relcon.setAttribute("conName","UNIQUE");
             relcon.setAttribute("conClass","UNIQUE");
             b++;
             relcon.appendChild(document.createTextNode("\n"+tab(b)));
              Element attrcon = (Element)document.createElement("consRelScheme"); 
             attrcon.setAttribute("schemeName",rsname);
             attrcon.setAttribute("schemeRole","referenced");
           
             rs3=query3.select("select  *   from  IISC_RS_KEY,IISC_RSK_ATT,IISC_ATTRIBUTE  where IISC_RSK_ATT.Att_id=IISC_ATTRIBUTE.Att_id and  IISC_RS_KEY.RS_rbrk= "+ rbrk +"  and IISC_RS_KEY.RS_rbrk=IISC_RSK_ATT.RS_rbrk and IISC_RS_KEY.RS_id=IISC_RSK_ATT.RS_id and IISC_RS_KEY.RS_id="+ rsid +"  and  IISC_RS_KEY.RS_primary_key=0  and  IISC_RS_KEY.As_id="+ as +"  and  IISC_RS_KEY.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " ");   
          while(rs3.next())
            {b++;
             attrcon.appendChild(document.createTextNode("\n"+tab(b)));
             Element attrschcon = (Element)document.createElement("conSchAttr"); 
             attrschcon.setAttribute("conAttName",rs3.getString("Att_mnem"));
            
             attrcon.appendChild(attrschcon); 
             
            b--; 
            }
            query3.Close();
            
            attrcon.appendChild(document.createTextNode("\n"+tab(b)));
            
             relcon.appendChild(attrcon);
             b--;
            
             relcon.appendChild(document.createTextNode("\n"+tab(b)));
            schema.appendChild(document.createTextNode("\n"+tab(b)));
           schema.appendChild(relcon); 
           b--;
          }
         //schema.appendChild(document.createTextNode("\n"+tab(b)));  
         }
          query2.Close();
        
     rs2=query2.select("select distinct( IISC_RS_Constraint.RSC_id),RSC_desc,f.RS_id,f.RS_name,e.RS_id,e.RS_name,RSC_mod_action,RSC_ins_action,RSC_del_action,RSC_type   from  IISC_RS_Constraint,IISC_RSC_RS_SET as a,IISC_RSC_RS_SET as b, IISC_RELATION_SCHEME as e, IISC_RELATION_SCHEME as f,IISC_RSC_LHS_RHS as c,IISC_RSC_LHS_RHS as d,IISC_RSC_ACTION    where f.RS_ID=b.RS_id   and   a.RS_id<>b.RS_id  and  e.RS_ID=a.RS_id  and  IISC_RS_Constraint.LHS_RS_Set_id=a.RS_SET_id and  IISC_RS_Constraint.LHS_id=c.RSCLR_id and  IISC_RS_Constraint.RHS_id=d.RSCLR_id   and  ((RSC_type<>1 and a.RS_id =" + rsid + ") or (RSC_type=1 and b.RS_id =" + rsid + ")) and a.As_id="+ as +"  and  b.As_id="+ as +" and a.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " and b.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + "  and  IISC_RS_Constraint.RHS_RS_Set_id=b.RS_SET_id  and IISC_RS_Constraint.As_id="+ as +"  and  IISC_RS_Constraint.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " ");
     Synthesys syn=new Synthesys();
     syn.as=as;
     syn.con=con;
      
      RelationScheme rsheme=syn.getRelationScheme(rsid);
 int cons=-1;
        while(rs2.next())
         {
         int cons1=rs2.getInt("RSC_id");
         if(cons1==cons)continue;
         cons=cons1;
          int type=rs2.getInt("RSC_type");
         int rsidr=rs2.getInt(3);
        String rsnamer=rs2.getString(4);
        String rsnamee=rs2.getString(6);
        String consname=rs2.getString("RSC_desc");
      
          b++;
             schema.appendChild(document.createTextNode("\n"+tab(b)));
             Element relcon = (Element)document.createElement("schemeConstraint"); 
             relcon.setAttribute("conName",consname);
             relcon.setAttribute("conClass","REFERENCES");
             b++;
               relcon.appendChild(document.createTextNode("\n"+tab(b)));
              Element attrcon = (Element)document.createElement("consRelScheme"); 
             attrcon.setAttribute("schemeName",rsname);
             if (type==1)
             attrcon.setAttribute("schemeRole","referenced");
             else
             attrcon.setAttribute("schemeRole","referencing");
           
            rs3=query3.select("select  *   from  IISC_ATTRIBUTE,IISC_RSC_LHS_RHS,IISC_RS_CONSTRAINT  where IISC_ATTRIBUTE.ATT_id=IISC_RSC_LHS_RHS.ATT_id and ((IISC_RSC_LHS_RHS.RSCLR_id=IISC_RS_CONSTRAINT.LHS_id and RSC_type<>1) or  (IISC_RSC_LHS_RHS.RSCLR_id=IISC_RS_CONSTRAINT.RHS_id and RSC_type=1)) and IISC_RS_CONSTRAINT.RSC_ID=" + cons + "  and IISC_RS_Constraint.As_id="+ as +"  and  IISC_RS_Constraint.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " ");   
              while(rs3.next())
             {
             b++;
             attrcon.appendChild(document.createTextNode("\n"+tab(b)));
             Element attrschcon = (Element)document.createElement("conSchAttr"); 
             attrschcon.setAttribute("conAttName",rs3.getString("Att_mnem"));
            
             attrcon.appendChild(attrschcon); 
            
            
            b--; 
            }
             query3.Close();
             relcon.appendChild(attrcon);
              b++; b++;
              Element conval = (Element)document.createElement("conValActivity");
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             Element oper = (Element)document.createElement("operation");
             Element action = (Element)document.createElement("action");
             oper.setAttribute("operType","ON_UPDATE");
             action.setAttribute("actType",getAction(rs2.getInt("RSC_mod_action")));
             conval.appendChild(oper);
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             conval.appendChild(action);
             b--;
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             attrcon.appendChild(document.createTextNode("\n"+tab(b)));
             attrcon.appendChild(conval);
            
             b++;
             conval = (Element)document.createElement("conValActivity");
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             oper = (Element)document.createElement("operation");
             action = (Element)document.createElement("action");
             oper.setAttribute("operType","ON_DELETE");
             action.setAttribute("actType",getAction(rs2.getInt("RSC_del_action")));
             conval.appendChild(oper);
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             conval.appendChild(action);
             b--;
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             attrcon.appendChild(document.createTextNode("\n"+tab(b)));
             attrcon.appendChild(conval);
             attrcon.appendChild(document.createTextNode("\n"+tab(b)));
             conval = (Element)document.createElement("conValActivity");
             b++;
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             oper = (Element)document.createElement("operation");
             action = (Element)document.createElement("action");
             oper.setAttribute("operType","ON_INSERT");
             action.setAttribute("actType",getAction(rs2.getInt("RSC_ins_action")));
             conval.appendChild(oper);
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             conval.appendChild(action);
             b--;
             conval.appendChild(document.createTextNode("\n"+tab(b)));
             attrcon.appendChild(conval);
              
             b--;
             attrcon.appendChild(document.createTextNode("\n"+tab(b)));
            relcon.appendChild(attrcon);
             
            rs4=query4.select("select  distinct( IISC_RSC_RS_SET.RS_id),IISC_RELATION_SCHEME.RS_name   from  IISC_RSC_RS_SET,IISC_RS_CONSTRAINT ,IISC_RELATION_SCHEME where IISC_RSC_RS_SET.RS_id=IISC_RELATION_SCHEME.RS_id and (( IISC_RSC_RS_SET.RS_Set_id=IISC_RS_CONSTRAINT.RHS_RS_Set_id and RSC_type<>1) or ( IISC_RSC_RS_SET.RS_Set_id=IISC_RS_CONSTRAINT.LHS_RS_Set_id and RSC_type=1)) and IISC_RS_CONSTRAINT.RSC_ID=" + cons + "  and IISC_RS_Constraint.As_id="+ as +"  and  IISC_RS_Constraint.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " ");   
           while(rs4.next())
           {relcon.appendChild(document.createTextNode("\n"+tab(b))); 
           rsidr=rs4.getInt(1);
              rsnamer=rs4.getString(2);
              attrcon = (Element)document.createElement("consRelScheme"); 
              
             attrcon.setAttribute("schemeName",rsnamer);
             if(type==1)
              attrcon.setAttribute("schemeRole","referencing");
              else
             attrcon.setAttribute("schemeRole","referenced");
            rs3=query3.select("select  *   from  IISC_RSC_RS_SET,IISC_ATTRIBUTE,IISC_RSC_LHS_RHS,IISC_RS_CONSTRAINT,IISC_RS_ATT  where IISC_ATTRIBUTE.Att_id=IISC_RS_ATT.Att_id  and IISC_RS_ATT.RS_id="+rsidr+" and (( IISC_RSC_RS_SET.RS_Set_id=IISC_RS_CONSTRAINT.RHS_RS_Set_id and RSC_type<>1) or ( IISC_RSC_RS_SET.RS_Set_id=IISC_RS_CONSTRAINT.LHS_RS_Set_id and RSC_type=1)) and IISC_RSC_RS_SET.RS_id="+rsidr+" and IISC_ATTRIBUTE.ATT_id=IISC_RSC_LHS_RHS.ATT_id and    ((IISC_RSC_LHS_RHS.RSCLR_id=IISC_RS_CONSTRAINT.RHS_id and RSC_type<>1) or ( IISC_RSC_LHS_RHS.RSCLR_id=IISC_RS_CONSTRAINT.LHS_id and RSC_type=1))  and IISC_RS_CONSTRAINT.RSC_ID=" + cons + "  and IISC_RS_Constraint.As_id="+ as +"  and  IISC_RS_Constraint.PR_id="+((PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame())).ID + " ");   
              while(rs3.next())
             {
             b++;
             attrcon.appendChild(document.createTextNode("\n"+tab(b)));
             Element attrschcon = (Element)document.createElement("conSchAttr"); 
             attrschcon.setAttribute("conAttName",rs3.getString("Att_mnem"));
              attrcon.appendChild(attrschcon); 
            b--; 
            }
            attrcon.appendChild(document.createTextNode("\n"+tab(b)));
            relcon.appendChild(attrcon);
             query3.Close();
             }
            query4.Close();
      
              b--;
            
           relcon.appendChild(document.createTextNode("\n"+tab(b)));
              
           schema.appendChild(relcon); 
           
           b--;
          
       
         }
        query2.Close();
      
       schema.appendChild(document.createTextNode("\n"+tab(b))); 
        root.appendChild(schema); } 
         b--;
          query.Close();
          root.appendChild(document.createTextNode("\n"+tab(b)));
            document.getDocumentElement().normalize();
            Source source = new DOMSource(document);
            File s=new File("temp.xml");
            StreamResult result = new StreamResult(s);  
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
            return writeXml(document);
          

        } catch (ParserConfigurationException pce) {
            // Parser with specified options can't be built
            pce.printStackTrace(); }
         catch(SQLException e)
    {
    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
       
        catch (IllegalArgumentException x) {
        x.printStackTrace();
  // Happens if the parser does not support JAXP 1.2
 
 } catch (TransformerConfigurationException e) {
      System.out.println("TransformerConfigurationException: " + e);
    } catch (TransformerException e) {
      System.out.println("TransformerException: " + e);
    }
 
    catch (Exception e) {
      System.out.println("Exception: " + e);
    }
return null;
    } // buildDom

  private String getAction(int i)
  {
    if(i==0)return new String("NO_ACTION");
    else if(i==1)return new String("CASCADE");
    else if(i==2)return new String("SET_DEFAULT");
    else if(i==3)return new String("SET_NULL");
    return new String("");
  }
  private void btnClose_actionPerformed(ActionEvent e)
  {dispose();
  }

  private void btnSave_actionPerformed(ActionEvent e)
  { String filename = "Untitled1";
    JFileChooser fc = new JFileChooser(new File(filename));
   fc.addChoosableFileFilter(new MyFilter(type));
    // Show open dialog; this method does not return until the dialog is closed
  //  fc.showOpenDialog(frame);
  //  File selFile = fc.getSelectedFile();
    
    // Show save dialog; this method does not return until the dialog is closed
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
     {  JOptionPane.showMessageDialog(this, "<html><center>Bad file extension!", "Error", JOptionPane.ERROR_MESSAGE);
        can=false;
       
     }
      if(can)
{    switch (result) {
      case JFileChooser.APPROVE_OPTION:
   Writer output = null;
    try {
      //use buffering
      output = new BufferedWriter( new FileWriter(selFile.getPath()) );
      output.write( jTextPane1.getText() );
    }
     catch (IOException ex){
      ex.printStackTrace();
    }
    finally {
      //flush and close both "output" and its underlying FileWriter
     try{
      if (output != null) output.close();
     }
      catch (IOException ex){
      ex.printStackTrace();
    }
    }
        break;
      case JFileChooser.CANCEL_OPTION:
        // Cancel or the close-dialog icon was clicked
        break;
      case JFileChooser.ERROR_OPTION:
        // The selection process did not complete successfully
        break;


    }
     
  
}   



}

  private void btnXML_actionPerformed(ActionEvent e)
  {XMLViewer xmlv=new XMLViewer((IISFrameMain)getParent(),"DB XML Schema",true,con,"xsd");
    Settings.CenterDown(xmlv,25);
  
    xmlv.setVisible(true);
  }

  private void this_windowClosed(WindowEvent e)
  {
   File s=new File("temp.xml");
   s.delete();
   s=new File("temp.xsd");
   s.delete();
  }

  private void this_componentResized(ComponentEvent e)
  {
    jScrollPane1.setPreferredSize(new Dimension( this.getWidth()-10,this.getHeight()));
  jScrollPane1.setBounds(5,5, this.getWidth()-15,this.getHeight()-95);
  jTextPane1.setPreferredSize(new Dimension( this.getWidth()-10,this.getHeight()));
 jTextPane1.setBounds(0,0, this.getWidth()-10,this.getHeight()-90);
 btnClose.setBounds(new Rectangle(this.getWidth()-95, this.getHeight()-75, 80, 30));
 btnXMLscheme.setBounds(new Rectangle(this.getWidth()-320, this.getHeight()-75, 125, 30));
 btnSave.setBounds(new Rectangle(this.getWidth()-185, this.getHeight()-75, 80, 30));
  }
}
    class OpenFileAction extends AbstractAction {
        JDialog frame;
        JFileChooser chooser;
    
        OpenFileAction(JDialog frame, JFileChooser chooser) {
            super("Open...");
            this.chooser = chooser;
            this.frame = frame;
        }
    
        public void actionPerformed(ActionEvent evt) {
            // Show dialog; this method does not return until dialog is closed
            chooser.showOpenDialog(frame);
    
            // Get the selected file
            File file = chooser.getSelectedFile();
        }
    };
    
    // This action creates and shows a modal save-file dialog.
    class SaveFileAction extends AbstractAction {
        JFileChooser chooser;
        JDialog frame;
    
        SaveFileAction(JDialog frame, JFileChooser chooser) {
            super("Save As...");
            this.chooser = chooser;
            this.frame = frame;

        }
    
        public void actionPerformed(ActionEvent evt) {
            // Show dialog; this method does not return until dialog is closed
            chooser.showSaveDialog(frame);
    
            // Get the selected file
            File file = chooser.getSelectedFile();
        }

    }
    class MyFilter extends javax.swing.filechooser.FileFilter {
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

class MyDefaultHandler extends DefaultHandler {	
	
	//flag to check if the xml document was valid
	public boolean isValid = true;
	
	//Receive notification of a recoverable error.			
	public void error(SAXParseException se) {
		setValidity(se);		
	}
	
	//Receive notification of a non-recoverable error. 
 	public void fatalError(SAXParseException se) {
 		setValidity(se);
    }

	//Receive notification of a warning.        
 	public void warning(SAXParseException se) { 
 		setValidity(se);
	}
	
	private void setValidity(SAXParseException se){
		isValid = false;
    	System.out.println(se.toString());
	}
}

      class MyTextPane extends JTextPane
      {
       public boolean getScrollableTracksViewportWidth() {
 
	return false;
    }

      }