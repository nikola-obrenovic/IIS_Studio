package ui;


import java.awt.Dimension;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.io.*;
import com.harmonia.renderer.Renderer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import javax.swing.table.TableCellRenderer;

import javax.xml.transform.stream.StreamSource;

// For write operation
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerConfigurationException; 
import javax.xml.transform.stream.StreamResult; 

public class Generator { 
   private Connection con;
   private Renderer r = new Renderer();
   private Template template;
   private Application application;
   private Application mApplication;
   private JFrame frame;
   private JDesktopPane desktop;
public Generator (Connection connection) 
  {  
    con=connection;    
    try {
    File dir1 = new File (".");
    System.setProperty("LIQUIDUI",dir1.getCanonicalPath()); 
   }
  catch(Exception e) 
  {
    e.printStackTrace();
  }
	}
  public void transformUIMLSpecification() throws TransformerException, TransformerConfigurationException, FileNotFoundException, IOException
  {	try {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer(new StreamSource("trans.xsl"));
        transformer.transform(new StreamSource("slika59.xml"), new StreamResult(new FileOutputStream("FormSpecUI.uiml")));
        	}
     		catch (IOException ex) {
      	 		System.out.println("Exception: \n" + ex);
       		ex.printStackTrace ();
     		}
  }
  public void renderUIMLSpecification () 
  {
    if(frame!=null)
    {
        frame= (JFrame)r.getPartByName("MainFrame"); 
        JInternalFrame[] frames=desktop.getAllFrames();
        for(int i=0;i<frames.length;i++)
            frames[i].dispose();
        frame.dispose();
    }
    r = new Renderer();
    r.runningAsApplet=true;
    String uimlFileName = "Example.uiml";
    boolean renderOK = r.renderUIML(

      uimlFileName,

      "IISUIModeler",  //interfaceName

      null,  //structureName

      null,  //contentName

      null,  //styleName

      null,  //behaviorName

      null,  //peersName

      false, //-Dtime     option

      false, //-Dnorender option

      false, //-Delements option

      false, //-Dtrace    option

      false,  //-validate  option
      false  //-validate  option

      );
    frame= (JFrame)r.getPartByName("MainFrame");
    desktop= (JDesktopPane)r.getPartByName("dtp");
    frame.addWindowListener
        (new WindowAdapter()
         {  
            public void windowDeactivated(WindowEvent e)
            {  
                if(!frame.isVisible())
                 frame.dispose();
            }
         } 
        );
    JMenuItem item=(JMenuItem)r.getPartByName("mainClose");
    item.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent ae)
              {
                  try
                   { 
                        JDesktopPane dtp=(JDesktopPane)r.getPartByName("dtp");
                        dtp.getSelectedFrame().setVisible(false);
                  }
                  catch(Exception ea)
                  {
                    ea.printStackTrace();
                  }
              }
            });
   setApplicationProperties(r,mApplication);
   if (!renderOK) System.out.print("Error!"); 
  }
public void Center(JDialog dialog) {
    Settings.CenterRelative(dialog,frame);
}
public void Center(JInternalFrame dialog) {
    Settings.CenterRelative(dialog,frame);
}
public void setApplicationProperties(Renderer r, Application app) {
    JDialog dial=(JDialog)r.getPartByName("About");
    if(dial!=null)
    dial.addPropertyChangeListener(new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent e) {
            Center((JDialog)e.getSource());
        }
    });
    JInternalFrame dia=(JInternalFrame)r.getPartByName("MenuForm");
    if(dia!=null)
    {
        /*dia.addPropertyChangeListener(new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent e) {
            Center((JInternalFrame)e.getSource());
        }
        });*/
        Settings.CenterRelative(dia,frame);
        dia.setVisible(true);
    }
    for (int i=0; i<app.formType.length;i++)
    {
        Application.FormType f=app.formType[i];
        for(int j=0;j<f.compType.length;j++){
           Application.CompType c=f.compType[j];
           if(c.superord==-1){
            JInternalFrame dialog=(JInternalFrame)r.getPartByName(""+c.id+"compType");
            if(dialog!=null)
               dialog.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) {
                    Center((JInternalFrame)e.getSource());
                }
            });
           }
           else
           {
            JDialog dialog=(JDialog)r.getPartByName(""+c.id+"compType");
            if(dialog!=null)
            dialog.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) {
                    Center((JDialog)e.getSource());
                }
            });
           }
           if(c.data_layout==1)
           {
               JDialog dialog=(JDialog)r.getPartByName(""+c.id+"compTypeInput");
               if(dialog!=null)
               dialog.addPropertyChangeListener(new PropertyChangeListener() {
                   public void propertyChange(PropertyChangeEvent e) {
                       Center((JDialog)e.getSource());
                   }
               });
               JTable table =(JTable)r.getPartByName(""+f.compType[j].id+"Table");
               setTableProperties(table);
           }
           if(c.search==1) {
               if(c.data_layout==0)
               {
                JDialog dialog=(JDialog)r.getPartByName(""+c.id+"compTypeSearch");
                if(dialog!=null)
                dialog.addPropertyChangeListener(new PropertyChangeListener() {
                   public void propertyChange(PropertyChangeEvent e) {
                       Center((JDialog)e.getSource());
                   }
                });
               }
               JComboBox combo =(JComboBox)r.getPartByName(""+c.id+"SearchItemCombo");
               setComboProperties(combo);
               combo =(JComboBox)r.getPartByName(""+c.id+"SearchCombo");
               setComboProperties(combo);
               combo =(JComboBox)r.getPartByName(""+c.id+"SearchDialogItemCombo");
               setComboProperties(combo);
               combo =(JComboBox)r.getPartByName(""+c.id+"SearchDialogCombo");
               setComboProperties(combo);
               JTable table =(JTable)r.getPartByName(""+f.compType[j].id+"SearchTable");
               setTableProperties(table);
           }
           for(int k=0;k<c.itemGroup.length;k++){
               Application.ItemGroup ig=c.itemGroup[k];
               if(ig.contex==1 && template.itemPanel.contex==1 && template.itemPanel.type==1) {
                   JTable table =(JTable)r.getPartByName(""+f.compType[j].id+"ContexTable");
                   setTableProperties(table);
               }
               for(int l=0;l<ig.items.length; l++) {
                   Application.Item it=ig.items[l];
                   if(it.elem.getClass().toString().equals("class ui.Application$AttType")){
                       if(((Application.AttType)it.elem).type==3){
                           JComboBox combo =(JComboBox)r.getPartByName(""+((Application.AttType)it.elem).att_id +c.id+"InputField");
                           setComboProperties(combo);
                       }    
                   }
                   else{
                       Application.ItemGroup igi=(Application.ItemGroup)it.elem;
                       for(int m=0;m<igi.items.length; m++) {
                           if(((Application.AttType)igi.items[m].elem).type==3){ 
                               JComboBox combo =(JComboBox)r.getPartByName(""+((Application.AttType)igi.items[m].elem).att_id +c.id+"InputField");
                               setComboProperties(combo);
                           }
                       }
                   }
               } 
           }
        }
    } 
}
public void setTableProperties(JTable table) {
    if(table!=null){
    for (int i = 0; i < table.getColumnCount(); i++) {
        table.getColumnModel().getColumn(i).setHeaderRenderer(new MyHeaderRenderer()); 
        table.getColumnModel().getColumn(i).setCellRenderer(new MyCellRenderer()); 
        table.getColumnModel().getColumn(i).setCellEditor(new MyTableCellEditor());
        table.getColumnModel().getColumn(i).setPreferredWidth(template.tablePanel.column_width);
        table.getColumnModel().getColumn(i).setMaxWidth(template.tablePanel.column_width);
        table.getColumnModel().getColumn(i).setMinWidth(template.tablePanel.column_width);
        table.getColumnModel().getColumn(i).setWidth(template.tablePanel.column_width);
    }
    //table.setRowHeight(template.tablePanel.row_height);
}
}

public void setComboProperties(JComboBox combo) {
        if(combo!=null){
            combo.setRenderer(new MyListCellRenderer()); 
            combo.getEditor().getEditorComponent().setForeground(template.itemPanel.input_fg);
            combo.getEditor().getEditorComponent().setBackground(template.itemPanel.input_bg);
            combo.getEditor().getEditorComponent().setFont(template.inputFont.font);
    }
    }
    
public void createUIMLSpecification (Template templ, Application appl) throws SQLException, IOException {
    try {
      template=templ;
      mApplication=appl;
      application=appl;
      String fileName = "Example.uiml";
      String red="";
      FileOutputStream outf = new FileOutputStream(fileName);
      DataOutputStream out = new DataOutputStream(outf);
      red="<?xml version=\"1.0\" encoding=\"ISO-8859-2\"?>\n";
      out.writeBytes(red);
      //red="<!DOCTYPE uiml PUBLIC \"-//Harmonia//DTD UIML 2.0 Draft//EN\" \"UIML2_0g.dtd\">\n";
      red="<uiml>\n" +
      "<interface id=\"IISUIModeler\">\n" +
      "<structure>\n";
      out.writeBytes(red);
      createMainFrame(out);
      red="</structure>\n" +
      openStyle()+
      getProperty("","JFrame","defaultCloseOperation","2") +
      getProperty("","JFrame","font", getFont(template.labelFont.font)) +
      getProperty("","JDialog","layout","null") +
      getProperty("","JDialog","font",getFont(template.labelFont.font)) +
      getProperty("","JDialog","background",getColor(template.formPanel.bg)) +
      getProperty("","JDialog","foreground",getColor(template.formPanel.fg)) +
      getProperty("","JInternalFrame","layout","null") +
      getProperty("","JInternalFrame","font",getFont(template.labelFont.font)) +
      getProperty("","JInternalFrame","background",getColor(template.formPanel.bg)) +
      getProperty("","JInternalFrame","foreground",getColor(template.formPanel.fg)) +
      getProperty("","JMenu","background",getColor(template.bg)) +
      getProperty("","JMenu","foreground",getColor(template.fg)) +
      getProperty("","JMenu","font",getFont(template.labelFont.font)) +
      getProperty("","JMenuBar","background",getColor(template.bg)) +
      getProperty("","JMenuBar","foreground",getColor(template.fg)) +
      getProperty("","JMenuItem","background",getColor(template.bg)) +
      getProperty("","JMenuItem","foreground",getColor(template.fg)) +
      getProperty("","JMenuItem","font",getFont(template.labelFont.font)) +
      getProperty("","JTabbedPane","background",getColor(template.itemPanel.bg)) +
      getProperty("","JTabbedPane","foreground",getColor(template.itemPanel.fg)) +
      getProperty("","JTabbedPane","font",getFont(template.labelFont.font)) +
      getProperty("","JPanel","background",getColor(template.itemPanel.bg)) +
      getProperty("","JPanel","foreground",getColor(template.itemPanel.fg)) +
      getProperty("","JPanel","font",getFont(template.labelFont.font)) +
      getProperty("","JButton","background",getColor(template.buttonPanel.bg)) +
      getProperty("","JButton","foreground",getColor(template.buttonPanel.fg)) +
      getProperty("","JButton","font",getFont(template.labelFont.font)) +
      getProperty("","JButton","contentAreaFilled","false") +
      getProperty("","JButton","opaque","true") +
      getProperty("","JButton","border",getBorder(template.buttonPanel.border_style, template.buttonPanel.border, template.buttonPanel.fg, "")) +
      getProperty("","JButton","preferredSize","75,30") +
      getProperty("","JLabel","opaque","false") +
      getProperty("","JLabel","font",getFont(template.labelFont.font)) +
      getProperty("","JTextField","border",getBorder(template.itemType[0].input_border_style, template.itemType[0].input_border, template.itemPanel.input_fg, "")) +
      getProperty("","JTextField","font",getFont(template.inputFont.font)) +
      getProperty("","JTextField","background",getColor(template.itemPanel.input_bg)) +
      getProperty("","JTextField","foreground",getColor(template.itemPanel.input_fg)) +
      getProperty("","JTextArea","border",getBorder(template.itemType[0].input_border_style, template.itemType[0].input_border, template.itemPanel.input_fg, "")) +
      getProperty("","JTextArea","font",getFont(template.inputFont.font)) +
      getProperty("","JTextArea","background",getColor(template.itemPanel.input_bg)) +
      getProperty("","JTextArea","foreground",getColor(template.itemPanel.input_fg)) +
      getProperty("","JRadioButton","border",getBorder(template.itemType[1].input_border_style, template.itemType[1].input_border, template.itemPanel.input_fg, "")) +
      getProperty("","JRadioButton","font",getFont(template.inputFont.font)) +
      getProperty("","JCheckBox","border",getBorder(template.itemType[2].input_border_style, template.itemType[2].input_border, template.itemPanel.input_fg, "")) +
      getProperty("","JCheckBox","font",getFont(template.inputFont.font)) +
      getProperty("","JComboBox","border",getBorder(template.itemType[3].input_border_style, template.itemType[3].input_border, template.itemPanel.input_fg, "")) +
      getProperty("","JComboBox","font",getFont(template.inputFont.font)) +
      getProperty("","JComboBox","background",getColor(template.itemPanel.input_bg)) +
      getProperty("","JComboBox","foreground",getColor(template.itemPanel.input_fg)) +   
      getProperty("","JList","border",getBorder(template.itemType[4].input_border_style, template.itemType[4].input_border, template.itemPanel.input_fg, "")) +
      getProperty("","JList","font",getFont(template.inputFont.font)) +
      getProperty("","JList","background",getColor(template.itemPanel.input_bg)) +
      getProperty("","JList","foreground",getColor(template.itemPanel.input_fg)) +      
      getProperty("","JTable","background",getColor(template.tablePanel.input_bg)) +
      getProperty("","JTable","foreground",getColor(template.tablePanel.input_fg)) + 
      //getProperty("","JTable","selectionBackground",getColor((new JTable()).getSelectionBackground())) +
      //getProperty("","JTable","selectionForeground",getColor((new JTable()).getSelectionForeground())) + 
      getProperty("","JTable","font",getFont(template.inputFont.font)) +
      getProperty("","JTable","intercellSpacing","0,0") +
      getProperty("","JTable","rowHeight",""+ template.tablePanel.row_height) +
      getProperty("","JTable","border",getBorder(template.tablePanel.border_style, template.tablePanel.border, template.tablePanel.input_fg, "")) +
      closeStyle() +
      "</interface>\n" +
      "<peers>\n" +
      "<logic>\n" +
      "<d-component id=\"MainFrame\" name=\"MainFrame\" maps-to=\"ui.UIFrame\">\n" +
      "<d-method id=\"Close\" maps-to=\"closeFrame\">\n" +
      "</d-method>\n" +
      "</d-component>\n" +
      "</logic>\n" +
      "<presentation base=\"Java_1.5_Harmonia_1.0\" how=\"union\" export=\"optional\">\n" +
      "</presentation></peers>\n" +
      "</uiml>\n";
      out.writeBytes(red);
      out.close();
    }
    catch (IOException ex) {
      System.out.println("Exception: \n" + ex);
      ex.printStackTrace ();
    }
    return;
    }
public String getProperty(String pname, String pclass, String name, String pvalue) {
        String partClass=" part-class=\""+ pclass +"\" ";
        String partName=" part-name=\""+ pname +"\" ";
        return "<property" + ((pclass=="")?" ":partClass)  + ((pname=="")?" ":partName)  + "name=\""+ name +"\">"+ pvalue +"</property>\n";
}
public String getFont(Font f) {
    String bi=""+(f.isBold()?"bold":"")+(f.isItalic()?"italic":"");
    if(!bi.equals(""))bi="-"+bi;
    return f.getFontName() + bi+ "-"+ f.getSize();
}
public String getFont(Font f, int bold, int italic) {
    String bi=""+((f.isBold()||bold==1)?"bold":"")+((f.isItalic()||italic==1)?"italic":"");
    if(!bi.equals(""))bi="-"+bi;
    return f.getFontName() + bi+ "-"+ f.getSize();
}
public String openPart(String id, String name, String pclass) {
    return "<part " + (id==""?"":"id=\""+id+"\" ") + (name==""?"":"name=\""+name+"\" ") + "class=\""+ pclass +"\">\n";
}
 public String getPart(String id, String name, String pclass) {
     return "<part " + (id==""?"":"id=\""+id+"\" ") + (name==""?"":"name=\""+name+"\" ") + "class=\""+ pclass +"\"/>\n";
}
public String closePart() {
   return "</part>\n";
}
public String closeStyle() {
    return "</style>\n";
}
public String openStyle() {
    return "<style>\n";
}
public String getBehavior(String behavior) {
    return
    "<behavior>\n" +
    "<rule>\n" +
    "<condition>\n" +
    "<event class=\"actionPerformed\"/>\n" +
    "</condition>\n" +
    "<action>\n" +
    behavior +
    "</action>\n" +
    "</rule>\n" +
    "</behavior>\n";
}
public String getBorder(int border_style, int border_width, Color color, String title) {
        String b="";
          if(border_style==0)
              b="EmptyBorder,"+ border_width +","+ border_width +","+ border_width +","+ border_width;
          else if(border_style==1)
              b="LineBorder,"+ getColor(color) +","+ border_width;
          else if(border_style==2)
              b="EtchedBorder";
          else if(border_style==3)
              b="EtchedBorder,raised";
          else if(border_style==4)
              b="EtchedBorder,lowered";
          else if(border_style==5)
              b="BevelBorder,raised";
          else if(border_style==6)
              b="BevelBorder,lowered";
          else if(border_style==7)
              b="TitledBorder,"+title;
          return b;
}
public Rectangle getMainFrameDimension() {
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int width, height, x,y;
    int d_width=(new Double(d.getWidth())).intValue();
    int d_height=(new Double(d.getHeight())).intValue();
    width=(template.full_screen==1)?d_width:template.x;
    height=(template.full_screen==1)?d_height:template.y;
    x= (template.position==1)?0:((template.position==0)?((d_width-width)/2):(template.full_screen==1)?0:(template.x_position*d_width/100-width/2));
    y= (template.position==1)?0:((template.position==0)?((d_height-height)/2):(template.full_screen==1)?0:(template.y_position*d_height/100-height/2)); 
    return new Rectangle(x,y,width,height);
}
public void createMainFrame (DataOutputStream out)
  {
        try
        {
        String red="";
        int width, height, x,y;
        Rectangle ret=getMainFrameDimension();
        width=(int)ret.getWidth();
        height=(int)ret.getHeight();
        x= (int)ret.getX();
        y= (int)ret.getY();
        red=openPart("MainFrame","","JFrame") +
        openStyle() +
        getProperty("","","bounds", x +","+ y +","+ width +","+ height) +
        getProperty("","","title", application.name) +
        getProperty("","","iconImage", template.icon) +
        getProperty("","","resizable", ((template.resizable==1)?"true":"false")) +
        closeStyle() +
        generateMainMenu() +
        openPart("dtp","","JDesktopPane") +
        openStyle() +
        getProperty("","","background", getColor(template.desktop_bg)) +
        closeStyle() +
        generateFormTypes()+
        generateMenuForm()+
        closePart() +
        generateSubFormTypes()+
        generateInputFormTypes()+
        generateSearchFormTypes()+
        generateAbout()+
        closePart();
        out.writeBytes(red);
        }
        catch (IOException ex) {
            System.out.println("Exception: \n" + ex);
            ex.printStackTrace ();
        }
  }
  public String generateFormTypes() { 
      String r="";
      for (int i=0; i<application.formType.length;i++)
      {
        Application.FormType f=application.formType[i];
        if(f.compType.length>0)
            r=r+ generateCompType(f, f.compType[0]); 
      }
      return r;
  }
 
    public String generateSubFormTypes() { 
    String r="";
    for (int i=0; i<application.formType.length;i++)
    {
      Application.FormType f=application.formType[i];
      for(int j=0;j<f.compType.length;j++)
        if(f.compType[j].superord!=-1)
        {
            for(int k=0;k<f.compType.length;k++)
                if(f.compType[k].id==f.compType[j].superord && f.compType[k].layout==0)
                    r=r+generateCompType(f,f.compType[j]); 
        }
    }
    return r;
    }
    public String generateInputFormTypes() { 
    String r="";
    for (int i=0; i<application.formType.length;i++)
    {
      Application.FormType f=application.formType[i];
      for(int j=0;j<f.compType.length;j++)
        if(f.compType[j].data_layout==1)
            r=r+generateCompTypeInput(f,f.compType[j]); 
    }
    return r;
    }  
    public String generateSearchFormTypes() { 
    String r="";
    for (int i=0; i<application.formType.length;i++)
    {
      Application.FormType f=application.formType[i];
      for(int j=0;j<f.compType.length;j++){
        if(f.compType[j].data_layout==0 && f.compType[j].search==1)
        {
            for(int k=0;k<f.compType[j].itemGroup.length; k++) 
            {
                f.compType[j].itemGroup[k].overflow=0;
            }
            r=r+generateCompTypeSearch(f,f.compType[j]); 
        }        
      }
        
    }
    return r;
    } 
    public String generateCompTypePanel(Application.FormType f, Application.CompType cmp, int width, int height){
        String r1="";
        int h=0;
        int w=0;
        int hs=0;
        boolean ok=false;
        Application.CompType cmp1=cmp;
        int bheight=(height+5-getButtonPanelDimension(cmp,f).height - getMenuPanelDimension(cmp,f).height);
        for (int i=0; i<f.compType.length;i++)
            if(f.compType[i].superord==cmp.id)
            {
                ok=true;
                if(cmp.layout==1 && ok)
                {
                    cmp1=f.compType[i];
                    Dimension dim=getCompContentDimension(f.compType[i],f,template.itemPanel.type, f.compType[i].data_layout,1);
                    w=Math.max((int)dim.getWidth(),getButtonPanelDimension(f.compType[i],f).width);
                    h=(int)dim.getHeight()+getButtonPanelDimension(f.compType[i],f).height+((template.formPanel.title>0)?getTitleHeight():0)+getSearchPanelDimension(f,f.compType[i]).height;
                    r1=r1+
                    openPart(cmp.id + "SubCompTypePanel","","JPanel") +
                    openStyle() +
                    getProperty("","","bounds", (0 +(width-w)/2) +","+ (height + template.itemPanel.spacing -20 - getMenuPanelDimension(f.compType[i],f).height - getButtonPanelDimension(f.compType[i],f).height) +","+ w +","+ h) +
                    getProperty("","","layout", "null") +
                    getProperty("","","opaque", "false") +
                    closeStyle() +
                    generateCompTypePanel(f, f.compType[i], w, h)+
                    closePart();  
                    hs=template.itemPanel.spacing;
                }
            }

        String r="";
        r=r+
        ((template.formPanel.title>0)?openPart(cmp.id + "Title","","JLabel") +
        openStyle() +
        getProperty("","","horizontalAlignment", ((template.formPanel.title_align==0)?"LEFT":((template.formPanel.title_align==1)?"CENTER":"RIGHT"))) +
        getProperty("","","verticalAlignment", "BOTTOM") +
        getProperty("","","bounds", "20,0," +(width -40)+","+getTitHeight()) +
        getProperty("","","font", getFont(template.formPanel.titleFont.font)) +
        getProperty("","","text", cmp.name) +
        closeStyle() +
        closePart():"")+
        generateCompTypeToolbar(f,cmp) +
        generateCompTypeContent(cmp,f, template.itemPanel.type,cmp.data_layout,0,1) +
        (r1.equals("")?generateButtons(f,cmp1,(width+10-640)/2,bheight-(cmp1.superord==-1?0:getSearchPanelDimension(f,cmp1).height)):"")+
        r1;
        return r;
    }
    public String generateCompType(Application.FormType f, Application.CompType cmp){
        String r=""; 
        Rectangle ret=getMainFrameDimension();
        int x= (int)ret.getX()+5;
        int y= (int)ret.getY()+50;
        Dimension dim=getCompContentDimension(cmp,f,template.itemPanel.type, cmp.data_layout,1);
        int width=Math.max((int)dim.getWidth()+40,getButtonPanelDimension(cmp,f).width);
        int menu=getMenuPanelDimension(cmp,f).height;
        int toolbar=getToolbarDimension(cmp,f).height;
        int height=(int)dim.getHeight()+getButtonPanelDimension(cmp,f).height+menu+toolbar+((template.formPanel.title>0)?getTitleHeight():0)+((template.itemPanel.type==1 && cmp.itemGroup.length>1)?getTitHeight():0);//+getSearchPanelDimension(f,cmp).height;
        int h=0;
        int w=0;
        boolean ok=false;
        for (int i=0; i<f.compType.length;i++)
        if(f.compType[i].superord==cmp.id)
        {
            ok=true;
            if(cmp.layout==1 && ok)
            {
                dim=getCompContentDimension(f.compType[i],f,template.itemPanel.type, f.compType[i].data_layout,1);
                w=Math.max((int)dim.getWidth(),getButtonPanelDimension(f.compType[i],f).width);
                toolbar=getToolbarDimension(f.compType[i],f).height;
                h=(int)dim.getHeight()+((template.formPanel.title>0)?getTitleHeight():0)+template.itemPanel.spacing-20;
            }
        }
        width=width+10;
        r=r+
        ((cmp.superord==-1)?openPart(cmp.id+"compType","","JInternalFrame"):openPart(cmp.id+"compType","","JDialog")) +
        openStyle() +
        getProperty("","","defaultCloseOperation", "1") +
        getProperty("","","title", f.name) +
        getProperty("","","visible","false") +
        getProperty("","","resizable", "false") +
        ((cmp.superord==-1)?getProperty("","","closable", "true"):"") +
        ((cmp.superord==-1)?getProperty("","","frameIcon", template.icon):"") +
        ((cmp.superord==-1)?getProperty("","","iconifiable", "true"):getProperty("","","modal", "true")) +
        ((cmp.superord==-1)?getProperty("","","bounds", "20,20,"+ (width+10)+","+(h+height)):getProperty("","","bounds", (x+20)+","+(y+20)+","+ (width+10)+","+(h+height))) +
        closeStyle()+
        (template.formPanel.menuCall==1?generateCompTypeMenu(f,cmp):"") +
        generateCompTypePanel(f, cmp, width-10, height)+
        closePart(); 
        return r;
    }
    public String generateCompTypeInput(Application.FormType f, Application.CompType cmp){
        String r=""; 
        Rectangle ret=getMainFrameDimension();
        int x= (int)ret.getX()+5;
        int y= (int)ret.getY()+50;
        Dimension dim=getCompContentDimension(cmp,f,0,0,1);
        int width=Math.max((int)dim.getWidth()+50,getButtonInputPanelDimension(cmp,f).width);
        int height=(int)dim.getHeight()+getButtonInputPanelDimension(cmp,f).height+((template.formPanel.title>0)?getTitleHeight():0);
        r=r+
        openPart(cmp.id+"compTypeInput","","JDialog") +
        openStyle() +
        getProperty("","","defaultCloseOperation", "1") +
        getProperty("","","title", f.name+" Input Form") +
        getProperty("","","visible", "false") +
        getProperty("","","resizable", "false") +
        getProperty("","","modal", "true") +
        getProperty("","","bounds", (x+20)+","+(y+20)+","+ (width+10)+","+height) +
        closeStyle()+
        ((template.formPanel.title>0)?openPart(cmp.id + "Title","","JLabel") +
        openStyle() +
        getProperty("","","horizontalAlignment", ((template.formPanel.title_align==0)?"LEFT":((template.formPanel.title_align==1)?"CENTER":"RIGHT"))) +
        getProperty("","","verticalAlignment", "BOTTOM") +
        getProperty("","","bounds", "20,0," +(width -40)+","+getTitHeight()) +
        getProperty("","","font", getFont(template.formPanel.titleFont.font)) +
        getProperty("","","text", cmp.name + " Input Form") +
        closeStyle() +
        closePart():"")+
        generateCompTypeContent(cmp,f, 0,0,1,0) +
        generateInputButtons(f,cmp,(width-540)/2,(height+5-getButtonInputPanelDimension(cmp,f).height)) +
        closePart(); 
        return r;
    }
    public String generateCompTypeSearch(Application.FormType f, Application.CompType cmp){
        String r=""; 
        Rectangle ret=getMainFrameDimension();
        int x= (int)ret.getX()+5;
        int y= (int)ret.getY()+50;
        Dimension dim=getCompContentDimension(cmp,f,0,1,2);
        int width=Math.max((int)dim.getWidth()+50,getButtonSearchPanelDimension(cmp,f).width);
        int height=(int)dim.getHeight()+getButtonSearchPanelDimension(cmp,f).height+((template.formPanel.title>0)?getTitleHeight():0)+getSearchPanelDimension(f,cmp).height;
        r=r+
        openPart(cmp.id+"compTypeSearch","","JDialog") +
        openStyle() +
        getProperty("","","defaultCloseOperation", "1") +
        getProperty("","","title", f.name+" Search Form") +
        getProperty("","","visible", "false") +
        getProperty("","","resizable", "false") +
        getProperty("","","modal", "true") +
        getProperty("","","bounds", (x+20)+","+(y+20)+","+ (width+10)+","+height) +
        closeStyle()+
        ((template.formPanel.title>0)?openPart(cmp.id + "Title","","JLabel") +
        openStyle() +
        getProperty("","","horizontalAlignment", ((template.formPanel.title_align==0)?"LEFT":((template.formPanel.title_align==1)?"CENTER":"RIGHT"))) +
        getProperty("","","verticalAlignment", "BOTTOM") +
        getProperty("","","bounds", "20,0," +(width -40)+","+getTitHeight()) +
        getProperty("","","font", getFont(template.formPanel.titleFont.font)) +
        getProperty("","","text", cmp.name + " Search Form") +
        closeStyle() +
        closePart():"")+
        generateCompTypeContent(cmp,f, 0,1,2,0) +
        generateSearchButtons(f,cmp,(width-540)/2,(height+5-getButtonSearchPanelDimension(cmp,f).height)) +
        closePart(); 
        return r;
    }
    public Dimension getSearchPanelDimension(Application.FormType f, Application.CompType cmp){
        Dimension dim=new Dimension(0,0);
        if(cmp.search==1)
            dim=new Dimension(500,Math.max(45,(template.inputFont.font.getSize()+15)*cmp.getAttributes().length+30+template.itemPanel.spacing)); 
        return dim;
    }
    public Dimension getSearchPanelDim(Application.FormType f, Application.CompType cmp){
        Dimension dim=new Dimension(0,0);
        if(cmp.search==1)
            dim=new Dimension(450,Math.max(45,(template.inputFont.font.getSize()+15)*cmp.getAttributes().length+30+15)); 
        return dim;
    }
    public String generateSearchPanel(Application.FormType f, Application.CompType cmp,int x, int y, int width, int type){
        String r1="", r2=(type==1)?"Dialog":"";
        Application.AttType[] Att=cmp.getAttributes();
        for(int i=0;i<Att.length;i++)
            r1=r1+"<constant value=\""+Att[i].title+"\"/>\n";
        String r= ""+
            openPart(cmp.id+"Search","","JPanel") +
            openStyle() +
            getProperty("","","bounds", x+","+y+","+width+","+(getSearchPanelDim(f,cmp).height)) +
            getProperty("","","border",getBorder(template.itemPanel.panel_border, template.itemPanel.panel_border_width, template.itemPanel.fg, "")) +
            getProperty("","","background", getColor(template.itemPanel.bg)) +
            closeStyle();
        int i=0;
        for(int k=0;k<Att.length;k++){
            r=r+
            openPart(Att[k].att.id+cmp.id+"SearchLabel","","JLabel") +
            openStyle() +
            getProperty("","","preferredSize","150,"+(template.inputFont.font.getSize()+10)) +
            getProperty("","","text", Att[k].title) +
            /*getProperty("","","content","" +
                "<constant model=\"list\">\n" +
                r1 +
                "</constant>\n") +*/
            //getProperty(cmp.id+"Search"+r2+"ItemCombo","","selectedIndex", "0") +
            //getProperty("","","toolTipText", "Search by item") +
            closeStyle()+
            closePart()+
            openPart(""+Att[k].att.id+cmp.id+"SearchComboCompare","","JComboBox") +
            openStyle() +
            getProperty("","","preferredSize","60,"+(template.inputFont.font.getSize()+10)) +
            getProperty("","","content","" +
                "<constant model=\"list\">\n" +
                "<constant value=\"\"/>\n" +
                "<constant value=\"like\"/>\n" +
                "<constant value=\"&gt;\"/>\n" +
                "<constant value=\"&gt;=\"/>\n" +
                "<constant value=\"=\"/>\n" +
                "<constant value=\"=&lt;\"/>\n" +
                "<constant value=\"&lt;\"/>\n" +
                "</constant>\n") +
            getProperty(""+Att[k].att.id+cmp.id+"SearchComboCompare","","selectedIndex", "0") +
            closeStyle()+
            closePart()+
            openPart(""+Att[k].att.id+cmp.id+"SearchKey","","JTextField") +
            openStyle() +
            getProperty("","","preferredSize", "150,"+(template.inputFont.font.getSize()+10)) +
            getProperty("","","toolTipText", "Search key") +
            closeStyle()+
            closePart()+
            openPart(""+Att[k].att.id+cmp.id+"SearchComboOrder","","JComboBox") +
            openStyle() +
            getProperty("","","preferredSize","60,"+(template.inputFont.font.getSize()+10)) +
            getProperty("","","content","" +
                "<constant model=\"list\">\n" +
                "<constant value=\"\"/>\n" +
                "<constant value=\"asc\"/>\n" +
                "<constant value=\"desc\"/>\n" +
                "</constant>\n") +
            getProperty(""+Att[k].att.id+cmp.id+"SearchComboOrder","","selectedIndex", "0") +
            closeStyle()+
            closePart();
            i++;
        }
        r=r+
        (template.buttonPanel.style==0?
        openPart(cmp.id + "Filter","","JButton") +
        openStyle() +
        getProperty("","","text", "Search") +
        closeStyle() +
        closePart():
        openPart(cmp.id + "Filter","","JButton") +
        openStyle() +
        getProperty("","","preferredSize", "30,30") +
        getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/filter.gif") +
        getProperty("","","toolTipText", "Filter") +
        closeStyle() +
        closePart()) +
        (template.buttonPanel.style==0?
        openPart(cmp.id + "Reset","","JButton") +
        openStyle() +
        getProperty("","","text", "Reset") +
        closeStyle() +
        closePart():
        openPart(cmp.id + "Reset","","JButton") +
        openStyle() +
        getProperty("","","preferredSize", "30,30") +
        getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/refresh.gif") +
        getProperty("","","toolTipText", "Reset") +
        closeStyle() +
        closePart());
        r=r+closePart();
        return r;
    } 
    
    public int getLabelWidth(Application.AttType at){
        return at.title.length()*(template.labelFont.font.isBold()?(template.labelFont.font.getSize()*2/3):(template.labelFont.font.getSize()/2));
    }
    public int getTitleHeight(){
        return template.formPanel.titleFont.font.getSize()+5+ template.itemPanel.spacing;
    }
    public int getTitHeight(){
        return template.formPanel.titleFont.font.getSize()+5;
    }
    public int getLabelHeight(){
        return template.labelFont.font.getSize();
    }
    public int getMaxLabelWidth(Application.ItemGroup ig){
    int len=0;
        for(int i=0;i<ig.items.length;i++)
        {
            if(ig.items[i].elem.getClass().toString().equals("class ui.Application$AttType"))
            len=Math.max(len,((Application.AttType)ig.items[i].elem).title.length());             
        }
        return len*(template.labelFont.font.isBold()?(template.labelFont.font.getSize()*2/3):(template.labelFont.font.getSize()/2));
    }
    public int getMaxInputWidth(Application.ItemGroup ig){
    int len=0;
        for(int i=0;i<ig.items.length;i++)
        {
            if(ig.items[i].elem.getClass().toString().equals("class ui.Application$AttType"))
            {
                Application.AttType at=(Application.AttType)ig.items[i].elem;
                len=Math.max(len,((at.type==1 && at.radio_orientation==0)?(at.list_of_values.length):1)*at.width);  
            }
        }
        return len;
    }
    public int getMaxWidth(Application.ItemGroup ig){
    int len=0;
        for(int i=0;i<ig.items.length;i++)
        {
            if(ig.items[i].elem.getClass().toString().equals("class ui.Application$AttType"))
            {
                Application.AttType at=(Application.AttType)ig.items[i].elem;
                len=Math.max(len,((at.type==1 && at.radio_orientation==0)?(at.list_of_values.length):1)*at.width+at.title.length()*5);    
            }
        }
        return len;
    }
    public void generateItemBounds(Application.ItemGroup ig, Application.Item item, Application.CompType cmp, int x, int y, int nested){
        item.x_input=0;
        item.y_input=0;
        item.input_width=0;
        item.label_width=0;
        item.x_label=0;
        item.y_label=0;
        item.width=0;
        item.height=0;
        int maxInputWidth=getMaxInputWidth(ig);
        int maxLabelWidth=getMaxLabelWidth(ig);
        int maxWidth=getMaxWidth(ig);
        if(item.elem.getClass().toString().equals("class ui.Application$AttType"))
        {
        Application.AttType at=(Application.AttType)item.elem;
        item.input_width=at.width;
        item.label_width=getLabelWidth(at);
        int label_height=getLabelHeight();
        int label_align=template.itemPanel.panel_label_align;
        int input_align= template.itemPanel.panel_input_align;
        if(nested==1) {
            label_align=template.itemPanel.nested_label_align;
            input_align= template.itemPanel.nested_input_align;
        }
        if(template.itemType[at.type].title_position==0) {
            if(label_align==2 && input_align==2)
            {
                item.label_width=getLabelWidth(at);
                item.input_width=at.width;
            }
            else if(label_align==2 && input_align==0)
            {
                item.label_width=maxLabelWidth;
                item.input_width=at.width;
            }
            else if(label_align==2 && input_align==1)
            {
                item.label_width=getLabelWidth(at);
                item.input_width=maxWidth-item.label_width;
            }
            else if(label_align==1 && input_align==2)
            {
                item.label_width=maxLabelWidth;
                item.input_width=at.width;
            }
            else if(label_align==1 && input_align==0)
            {
                item.label_width=maxLabelWidth;
                item.input_width=at.width;
            }
            else if(label_align==1 && input_align==1)
            {
                item.label_width=maxLabelWidth;
                item.input_width=maxInputWidth;
            }
            else if(label_align==0 && input_align==2)
            {
                item.label_width=getLabelWidth(at);
                item.input_width=maxWidth-at.width;
            }
            else if(label_align==0 && input_align==0)
            {
                item.label_width=maxLabelWidth;
                item.input_width=at.width;
            }
            else if(label_align==0 && input_align==1)
            {
                item.label_width=maxLabelWidth;
                item.input_width=maxInputWidth;
            }

            item.x_label=x;
            item.y_label=y;
            item.x_input=x+item.label_width+template.itemType[at.type].title_offset;
            item.y_input=y;
            item.height=Math.max(label_height,((at.type==1 && at.radio_orientation==1)?(at.list_of_values.length):1)*at.height);
            item.width=item.label_width+item.input_width+template.itemType[at.type].title_offset;
            
        }
        else if(template.itemType[at.type].title_position==1) {
            item.x_label=x;
            item.y_label=y;
            item.x_input=x;
            item.y_input=y+label_height+template.itemType[at.type].title_offset;
            item.width=Math.max(item.label_width, at.width);
            item.height=label_height+((at.type==1 && at.radio_orientation==1)?(at.list_of_values.length):1)*at.height +template.itemType[at.type].title_offset;
        }
        else if(template.itemType[at.type].title_position==2) {
            item.x_label=x+at.width+template.itemType[at.type].title_offset;
            item.y_label=y;
            item.x_input=x;
            item.y_input=y;
            item.height=Math.max(label_height, ((at.type==1 && at.radio_orientation==1)?(at.list_of_values.length):1)*at.height);
            item.width=item.label_width+at.width+template.itemType[at.type].title_offset;
        }
        else if(template.itemType[at.type].title_position==3) {
            item.x_label=x;
            item.y_label=y+((at.type==1 && at.radio_orientation==1)?(at.list_of_values.length):1)*at.height+template.itemType[at.type].title_offset;
            item.x_input=x;
            item.y_input=y;
            item.width=Math.max(item.label_width, at.width);
            item.height=label_height+((at.type==1 && at.radio_orientation==1)?(at.list_of_values.length):1)*at.height+template.itemType[at.type].title_offset;
        }
     }
     else {
         generateItemGroupBounds(item,cmp, x, y);
     }
    }
    public void generateItemGroupBounds(Application.Item item, Application.CompType cmp, int x, int y){
        item.x_input=x;
        item.y_input=y;
        item.x_label=0;
        item.y_label=0;
        item.width=0;
        item.height=0;
        if(item.elem.getClass().toString().equals("class ui.Application$ItemGroup"))
        {
            Application.ItemGroup ig=(Application.ItemGroup)item.elem; 
            int x1=template.itemPanel.item_spacing,y1=template.itemPanel.item_spacing;
            for(int i=0;i<ig.items.length;i++)
            {
                if(i==0 && template.itemPanel.nested_border==7)y1=y1+10;
                generateItemBounds(ig,ig.items[i],cmp, x1, y1,1);
                if(ig.items[i].breakline==1) {
                    x1=template.itemPanel.item_spacing;
                    y1=y1+ig.items[i].height+template.itemPanel.item_spacing;
                    item.width=Math.max(x1+ig.items[i].width, item.width);
                    item.height=Math.max(y1, item.height);
                }
                else
                {
                    x1=x1+ig.items[i].width+template.itemPanel.item_spacing;
                    item.width=Math.max(x1, item.width);
                    item.height=Math.max(y1+ig.items[i].height, item.height);
                }
                
            }    
            item.width=item.width+template.itemPanel.item_spacing;
            item.height=item.height;
        }
    }
    public Dimension generateItemGroupBounds(Application.ItemGroup ig, Application.CompType cmp){
            int x1=template.itemPanel.item_spacing,y1=template.itemPanel.item_spacing;
            int pm=0;
            int height=0,width=0;
            for(int i=0;i<ig.items.length;i++)
            {
                generateItemBounds(ig,ig.items[i],cmp, x1, y1,0);
                if(ig.items[i].breakline==1) {
                    x1=template.itemPanel.item_spacing;
                    y1=y1+ig.items[i].height+template.itemPanel.item_spacing;
                    width=Math.max(width,ig.items[i].width+2*template.itemPanel.item_spacing+pm);
                    height=Math.max(y1,height);
                    pm=0;
                }
                else
                {
                    x1=x1+ig.items[i].width+template.itemPanel.item_spacing;
                    pm=x1;
                    width=Math.max(x1, width);
                    height=Math.max(y1+ig.items[i].height+template.itemPanel.item_spacing,height);
                    
                }
                
            }   
        return new Dimension(width,height+((template.itemPanel.panel_border==7)?template.itemPanel.item_spacing:0));
    }
    public String generateItem(Application.Item item, Application.CompType cmp, int nested, int overflow){
        String r="",r1="";
        int y_add=((template.itemPanel.panel_border==7&&nested==0)?template.itemPanel.item_spacing:0);
        if(item.elem.getClass().toString().equals("class ui.Application$AttType"))
        {
        Application.AttType at=(Application.AttType)item.elem;
        String align=(template.itemType[at.type].title_align==0)?"LEFT":((template.itemType[at.type].title_align==1)?"CENTER":"RIGHT");
        int label_align=template.itemPanel.panel_label_align;
        int input_align= template.itemPanel.panel_input_align;
        if(nested==1) {
            label_align=template.itemPanel.nested_label_align;
            input_align= template.itemPanel.nested_input_align;
        }
        if(label_align==0)align="LEFT";
        if(label_align==1)align="RIGHT";
        int t=((Application.AttType)item.elem).type;
        String rLabel=openPart(at.att_id + "" + cmp.id + "InputLabel","","JLabel") +
        openStyle() +
        getProperty("","","bounds", item.x_label +","+ (item.y_label+y_add) +","+ item.label_width +","+ getLabelHeight()) +
        getProperty("","","horizontalAlignment", (align)) +
        getProperty("","","text", at.title) +
        getProperty("","","font", getFont(template.labelFont.font,template.itemType[t].title_bold,template.itemType[t].title_italic)) +
        closeStyle() +
        closePart();
        String rInput="\n" + getItemType(item,cmp, overflow, nested) ;
        r="" +
        rLabel +
        rInput;
        }
        else {
            Application.ItemGroup ig=(Application.ItemGroup)item.elem;
            for(int i=0;i<ig.items.length;i++)
            {
                r1 =r1 + generateItem(ig.items[i],cmp,1, overflow);
            }  
            r="" +
            openPart(ig.id +""+ cmp.id + "nestedItemGroup","","JPanel") +
            openStyle() +
            getProperty("","","bounds", item.x_input +","+ (item.y_input+y_add) +","+ item.width +","+ item.height) +
            getProperty("","","border",getBorder(template.itemPanel.nested_border, template.itemPanel.nested_border_width, template.itemPanel.fg,ig.title)) +
            getProperty("","","name", ig.title) +
            getProperty("","","opaque", "false") +
            getProperty("","","layout", "null") +
            closeStyle() +
            r1 +
            closePart();
        }
        return r;
    }

    public String getItemType(Application.Item it, Application.CompType cmp, int overflow, int nested){
        String type="JTextField";
        Application.AttType at=(Application.AttType)it.elem;
        int x=it.x_input;
        int y=it.y_input+((template.itemPanel.panel_border==7&&nested==0)?template.itemPanel.item_spacing:0);
        if(at.text_multiline==1)type="JTextArea";
        if(at.type==1) type="JRadioButton";
        else if(at.type==2) type="JCheckBox";
        else if(at.type==3) type="JComboBox";
        else if(at.type==4) type="JList";
        String item="";
        if(at.type==1) {
         item=item+
         openPart(at.att_id + "" +cmp.id + "InputFieldPane","","ButtonGroup");
         for(int i=0;i<at.list_of_values.length;i++){
             item=item+
             openPart(at.att_id + "" +cmp.id + "InputField","",type) +
             openStyle() +
             getProperty("","","text", at.list_of_values[i]) +
             getProperty("","","bounds", (x + it.input_width-((at.type==1 && at.radio_orientation==0)?(at.list_of_values.length):1)*at.width) +","+ (y - 5) +","+ at.width +","+ at.height)+
             getProperty("","","opaque", "false") +
             (overflow==1?getProperty("","","editable", "false"):"")+
             closeStyle() +
             closePart();
             if(at.radio_orientation==0)x=x+at.width;
             else y=y+at.height;
         }
         item=item + closePart();         
        }
        else {
        item=item+
        (((at.text_multiline==1 && at.type==0 && at.text_scroll==1) || at.type==4)?(openPart(at.att_id + "" +cmp.id + "InputFieldScrollPane","","JScrollPane")+openStyle()+getProperty("","","border",getBorder(template.itemType[0].input_border_style, template.itemType[0].input_border, template.itemPanel.input_fg,""))+closeStyle()):"")+
        openPart(at.att_id + "" +cmp.id + "InputField","",type) +
        openStyle() +
        ((at.type==3 || at.type==4)? getProperty("","","content","" +
        "<constant model=\"list\">\n" +
        "<constant value=\"Item Value 1\"/>\n" +
        "<constant value=\"Item Value 2\"/>\n" +
        "<constant value=\"Item Value 3\"/>\n" +
        "<constant value=\"Item Value 4\"/>\n" +
        "<constant value=\"Item Value 5\"/>\n" +
        "<constant value=\"Item Value 6\"/>\n" +
        "<constant value=\"Item Value 7\"/>\n" +
        "<constant value=\"Item Value 8\"/>\n" +
        "<constant value=\"Item Value 9\"/>\n" +
        "<constant value=\"Item Value 10\"/>\n" +
        "</constant>\n"):"") +
        ((at.type==4&&(!at.default_value.equals("")))?getProperty("","","selectedIndex", at.default_value):"") +
        ((at.text_multiline==1 && at.type==0 && at.text_scroll==1)?getProperty("","","text", at.default_value)+(overflow==1?getProperty("","","editable", "false"):"")+getProperty("","","text", at.default_value)+closeStyle() +closePart()+openStyle():"")+
        ((at.type==4)?closeStyle() +closePart()+openStyle():"")+
        ((at.type==3&&(!at.default_value.equals("")))?getProperty("","","selectedIndex", at.default_value):"") +
        ((at.type==0 && !(at.text_multiline==1 && at.type==0 && at.text_scroll==1))?getProperty("","","text", at.default_value):"") +
        ((at.type==3)? getProperty("","","editable", ((at.combo_editable==1)?"true":"false")):"") +
        ((at.type==1 || at.type==2)? getProperty("","","opaque", "false"):"") +
        getProperty("","","bounds", (x + it.input_width-at.width) +","+ (y-(at.type==2?5:0)) +","+ at.width +","+ at.height) +
        (overflow==1&&at.text_multiline!=1?getProperty("","","editable", "false"):"")+
        closeStyle() +
        closePart();}
        return item;
        
    }
    public String generateItemGroup(Application.ItemGroup ig, Application.CompType cmp,int x, int y,int width, int height, int overflow, int inputPanelType){
        String r="", r1="";
        for(int i=0;i<ig.items.length;i++)
        {
            r1 =r1 + generateItem(ig.items[i],cmp,0, overflow);
        }    
        if(ig.contex==1&&inputPanelType==1&&template.itemPanel.contex==1)return "";
        r="" +
        openPart(ig.id +""+ cmp.id + "itemGroup","","JPanel") +
        openStyle() +
        getProperty("","","bounds", x + "," + y + "," + width + "," + height) +
        getProperty("","","preferredSize",  width + "," + height) +
        (overflow==0?getProperty("","","border",getBorder(template.itemPanel.panel_border, template.itemPanel.panel_border_width, template.itemPanel.fg,ig.title)):"") +
        getProperty("","","name", ig.title) +
        getProperty("","","background", getColor(template.itemPanel.bg)) +
        getProperty("","","layout", "null") +
        closeStyle() +
        r1 +
        closePart();
        return r;
    }

     public Dimension getItemGroupDimension(Application.ItemGroup ig, Application.CompType cmp){
             int width=0, height=0;
             for(int i=0; i< ig.items.length; i++)
             {
                 width=Math.max(width, (int)ig.items[i].width);
                 height=height+(int)ig.items[i].height;
             }
             return new Dimension(width, height);
         }
    public String generateCompTypeContent(Application.CompType cmp, Application.FormType f, int inputPanelType, int data_layout, int type, int master){
        String r="";
        int hMaster=0;
        int wMaster=0;
        int height=0, max_height=0, max_width=0,max_sum_width=0;
        if(data_layout==0) {
            Dimension dim[]=new Dimension[cmp.itemGroup.length];
            int rows=cmp.itemGroup.length/(template.itemPanel.orientation+1);
            if(rows*(template.itemPanel.orientation+1)<cmp.itemGroup.length)rows++;
            int dimrow[][]=new int[rows][3];
            int k=-1;
            int j=0;
            int contex=0;
            for(int i=0;i<cmp.itemGroup.length; i++) 
                {
                   k++;
                   if(cmp.itemGroup[i].contex==1&&inputPanelType==1&&template.itemPanel.contex==1)contex=1;
                   dim[i] = generateItemGroupBounds(cmp.itemGroup[i],cmp);
                   dimrow[j][0]=Math.max(dimrow[j][0],dim[i].width);
                   dimrow[j][1]=Math.max(dimrow[j][1],dim[i].height);
                   dimrow[j][2]=dimrow[j][2]+dim[i].width+template.itemPanel.spacing;
                   if(k==template.itemPanel.orientation)
                   {
                       k=-1;
                       j++;    
                   }
                }
            for(int i=0;i<rows; i++) {
                max_height=Math.max(max_height, dimrow[i][1]); 
                max_width=Math.max(max_width, dimrow[i][0]);
                max_sum_width=Math.max(max_sum_width, dimrow[i][2]);
                height=height+dimrow[i][1]+template.itemPanel.spacing;
            }
            max_height=Math.max(max_height, 100); 
            max_sum_width=Math.max(max_sum_width, (type==1?300:540));
            int max_w=max_width;
            max_width=Math.max(max_width, (type==1?300:540));
            int x=template.itemPanel.spacing,y=template.itemPanel.spacing,w=0,h=0;
            k=-1;
            j=0;
            int orientation=0;
            
            for(int i=0;i<cmp.itemGroup.length; i++)
            {
                k++;
                if(k>orientation)orientation=k;
                int wi= dim[i].width+((max_sum_width-dimrow[j][2])/(Math.min(template.itemPanel.orientation+1,cmp.itemGroup.length)))+template.itemPanel.spacing;
                //wi=dimrow[j][0];
                r=r+generateItemGroup(cmp.itemGroup[i],cmp,x,y,wi,dimrow[j][1],0,inputPanelType);
                if(k==template.itemPanel.orientation)
                {
                    x=template.itemPanel.spacing;
                    y=y+dimrow[j][1]+template.itemPanel.spacing;
                    k=-1;
                    j++;
                }
                else
                    x=x+wi+template.itemPanel.spacing;
            }
            if(inputPanelType==1 && cmp.itemGroup.length>1) {
                String r1="";
                int wi=20;
                int he=((template.formPanel.title>0)?getTitleHeight():0) + (type==1?0:((int)getSearchPanelDimension(f,cmp).getHeight()+template.formPanel.buttonCall>0?getToolbarDimension(cmp,f).height:0));
                int hheight=(max_height+template.labelFont.font.getSize()+2*template.itemPanel.spacing);
                if(contex==1)
                   {   
                       r1=getContex(cmp,f,template.itemPanel.contex_pos==0?hheight:75);
                       Dimension dim1 =getContexDimensions(cmp,f,template.itemPanel.contex_pos==0?hheight:75);
                       if(template.itemPanel.contex_pos==0)
                           wi=wi+ template.itemPanel.spacing +(int)dim1.getWidth();
                       else
                           he=he+ template.itemPanel.spacing +(int)dim1.getHeight();
                   }
                int wwidth=Math.max(max_w,(500-((contex==1&&template.itemPanel.contex_pos==0)?(wi):0)));
                int width=Math.max(w+10,450);
                int scwidth=width+ (template.tablePanel.overflow==1&&template.tablePanel.overflow_pos==0?wwidth:0);
                r="" +
                r1 +
                (cmp.search==1&&type==2?generateSearchPanel(f,cmp,20,(template.formPanel.title>0?getTitleHeight():0)+(template.formPanel.buttonCall>0?getToolbarDimension(cmp,f).height:0),scwidth,0):"")+
                openPart(cmp.id + "DataPanel","","JPanel") +
                openStyle() +
                getProperty("","","bounds", 20 + ","+(he+hMaster) +"," + Math.max(max_w,(type==1?300:540))  +","+ hheight) +
                getProperty("","","opaque", "false") +
                ((contex==1&&template.itemPanel.contex_pos==0)?getProperty("","","layout", "null"):"") +
                closeStyle() +
                openPart(cmp.id + "TabbedPane","","JTabbedPane") +
                openStyle() +
                getProperty("","","bounds", wi + ","+ ((contex==1&&template.itemPanel.contex_pos==0)?0:he) +"," + wwidth  +","+ hheight) +
                closeStyle() +
                r +
                closePart()+
                closePart();
            }
            else
            {
                int wwidth=(max_sum_width+(2 + orientation) *template.itemPanel.spacing);
                int hheight=(height+template.itemPanel.spacing);
                String r1="";
                r="" +
                r1 +
                (cmp.search==1&&type==2?generateSearchPanel(f,cmp,25,(template.formPanel.title>0?getTitleHeight():0)+(template.formPanel.buttonCall>0?getToolbarDimension(cmp,f).height:0),wwidth,0):"")+
                openPart(cmp.id + "DataPanel","","JPanel") +
                openStyle() +
                getProperty("","","bounds", 15 + ","+(((template.formPanel.title>0)?getTitleHeight():0)+ (type==1?0:((int)getSearchPanelDimension(f,cmp).getHeight() + template.formPanel.buttonCall>0?getToolbarDimension(cmp,f).height:0))+hMaster)+"," + wwidth +","+ hheight) +
                getProperty("","","layout", "null") +
                getProperty("","","opaque", "false") +
                closeStyle() +
                r +
                closePart();
            }
        }
        else {
           int len=0;
           Application.ItemGroup igo=null;
           for(int i=0;i<cmp.itemGroup.length; i++) {
               if(cmp.itemGroup[i].overflow==0 || template.tablePanel.overflow==0){
               for(int j=0;j<cmp.itemGroup[i].items.length; j++) {
               if(cmp.itemGroup[i].items[j].elem.getClass().toString().equals("class ui.Application$AttType"))
                   len=len +1;
               else
                   len=len +((Application.ItemGroup)cmp.itemGroup[i].items[j].elem).items.length;
               }
               }
               else {
                   igo=cmp.itemGroup[i];
               }
            }
            String[] col=new String[len];
            int k=0;
            int w=0;
            for(int i=0;i<cmp.itemGroup.length; i++) {
               if(cmp.itemGroup[i].overflow==0 || template.tablePanel.overflow==0)
               for(int j=0;j<cmp.itemGroup[i].items.length; j++) {
               if(cmp.itemGroup[i].items[j].elem.getClass().toString().equals("class ui.Application$AttType")){
                   col[k]=((Application.AttType)cmp.itemGroup[i].items[j].elem).title;
                   w=w+col[k].length()*template.labelFont.font.getSize()/2 +4 + (template.tablePanel.border_style==1?(template.tablePanel.border*2):2);
                   k=k+1;
               }
               else{
                   Application.ItemGroup ig=(Application.ItemGroup)cmp.itemGroup[i].items[j].elem;
                   for(int m=0;m<ig.items.length; m++) {
                       col[k]=((Application.AttType)ig.items[m].elem).title;
                       w=w+col[k].length()*template.labelFont.font.getSize()/2 + 4 + (template.tablePanel.border_style==1?(template.tablePanel.border*2):2);
                       k=k+1;
                   }
               }
               }     
            }
            String[][] val=new String[20][len];
            for(int j=0;j<20; j++)
               for(int i=0;i<len; i++)
                   val[j][i]=col[i]+" Value "+ (i+1) +""+(j+1);
            int x=25;
            int y=(((template.formPanel.title>0)?getTitleHeight():0)+ ((type==1 || type==0)?0:((int)getSearchPanelDimension(f,cmp).getHeight()))+(template.formPanel.buttonCall>0?getToolbarDimension(cmp,f).height:0));
            int width=Math.max(w+10,450);
            int heig=template.tablePanel.visible_rows*template.tablePanel.row_height +template.labelFont.font.getSize()+4+2*(template.tablePanel.border_style==1?(template.tablePanel.border*2):2)+10;
            String r1=""; 
            int wwidth=0;
            if(igo!=null)
            if(template.tablePanel.overflow==1) {
                int ww=template.tablePanel.overflow_pos==1?Math.max(w+10,540):width;
                generateItemGroupBounds(igo,cmp);
                Dimension dim =getItemGroupDimension(igo,cmp);
                wwidth=(template.tablePanel.overflow_pos==0?template.tablePanel.overflow_size:ww);
                int hheight=(template.tablePanel.overflow_pos==0?heig:template.tablePanel.overflow_size);
                width=Math.max(width, (type==2?450:(type==1?300:540)));
                int xx=(template.tablePanel.overflow_pos==0?(x+width-wwidth):x);
                int yy=(template.tablePanel.overflow_pos==0?y:(y+heig));
                r1=r1+
                openPart(cmp.id + "OverflowScroll","","JScrollPane") +
                openStyle() +
                getProperty("","","bounds", xx +","+ yy +","+ wwidth +","+ hheight) +
                closeStyle() +
                generateItemGroup(igo,cmp,0,0,(int)dim.getWidth()+2*template.itemPanel.spacing,(int)dim.getHeight()+4*template.itemPanel.spacing,1,inputPanelType)+
                closePart();
            }
            int scwidth=width+ (template.tablePanel.overflow==1&&template.tablePanel.overflow_pos==0?wwidth:0);
            width=Math.max(width, (type==2?450:(type==1?300:540)));
            r=r+
            (cmp.search==1&&type==2?generateSearchPanel(f,cmp,x,(template.formPanel.title>0?getTitleHeight():0)+(template.formPanel.buttonCall>0?getToolbarDimension(cmp,f).height:0),scwidth,type==2?1:0):"")+
            getTable(cmp,f,col,val,x,y+hMaster,(template.tablePanel.overflow_pos==1&&igo!=null&&template.tablePanel.overflow==1?width:(width-wwidth)),heig,type)+
            r1;
        }
        return r;
        }
    public Dimension getTablePanelDimension(Application.CompType cmp, Application.FormType f){
        int len=0;
        Application.ItemGroup igo=null;
        for(int i=0;i<cmp.itemGroup.length; i++) {
            if(cmp.itemGroup[i].overflow==0 || template.tablePanel.overflow==0){
            for(int j=0;j<cmp.itemGroup[i].items.length; j++) {
            if(cmp.itemGroup[i].items[j].elem.getClass().toString().equals("class ui.Application$AttType"))
                len=len +1;
            else
                len=len +((Application.ItemGroup)cmp.itemGroup[i].items[j].elem).items.length;
            } 
            }
            else {
               igo= cmp.itemGroup[i];
            }
         }
         String[] col=new String[len];
         int k=0;
         int w=0;
         for(int i=0;i<cmp.itemGroup.length; i++) {
            if(cmp.itemGroup[i].overflow==0 || template.tablePanel.overflow==0)
            for(int j=0;j<cmp.itemGroup[i].items.length; j++) {
            if(cmp.itemGroup[i].items[j].elem.getClass().toString().equals("class ui.Application$AttType")){
                col[k]=((Application.AttType)cmp.itemGroup[i].items[j].elem).title;
                w=w+col[k].length()*template.labelFont.font.getSize()/2 +4 + (template.tablePanel.border_style==1?(template.tablePanel.border*2):2);
                k=k+1;
            }
            else{
                Application.ItemGroup ig=(Application.ItemGroup)cmp.itemGroup[i].items[j].elem;
                for(int m=0;m<ig.items.length; m++) {
                    col[k]=((Application.AttType)ig.items[m].elem).title;
                    w=w+col[k].length()*template.labelFont.font.getSize()/2 + 4 + (template.tablePanel.border_style==1?(template.tablePanel.border*2):2);
                    k=k+1;
                }
            }
            }     
         }
       int h=0,we=0;
       if(igo!=null)
       {
        int width=Math.max(w+10,300);
        int heig=template.tablePanel.visible_rows*template.tablePanel.row_height +template.labelFont.font.getSize()+4+2*(template.tablePanel.border_style==1?(template.tablePanel.border*2):2);
        Dimension dim=new Dimension((template.tablePanel.overflow_pos==0?template.tablePanel.overflow_size:width),(template.tablePanel.overflow_pos==0?heig:template.tablePanel.overflow_size));
        if(template.tablePanel.overflow_pos==0)
            we=(int)dim.getWidth();
        else 
            h=(int)dim.getHeight();
       }
       return new Dimension(Math.max(w+10,300)+we,template.tablePanel.visible_rows*template.tablePanel.row_height +template.labelFont.font.getSize() +4 +2*(template.tablePanel.border_style==1?(template.tablePanel.border*2):2)+h+template.itemPanel.spacing);
    }
    public Dimension getCompContentDimension(Application.CompType cmp, Application.FormType f, int inputPanelType, int data_layout, int type){
        String r="";
        int height=0, max_height=0, max_width=0,max_sum_width=0;
        if(data_layout==0) {
            int contex=0;
            Dimension dim[]=new Dimension[cmp.itemGroup.length];
            int rows=cmp.itemGroup.length/(template.itemPanel.orientation+1);
            if(rows*(template.itemPanel.orientation+1)<cmp.itemGroup.length)rows++;
            int dimrow[][]=new int[rows][3];
            int k=-1;
            int j=0;
            int orientation=0;
            for(int i=0;i<cmp.itemGroup.length; i++) 
                {
                   k++;
                   if(k>orientation)orientation=k;
                   dim[i] = generateItemGroupBounds(cmp.itemGroup[i],cmp);
                   if(cmp.itemGroup[i].contex==1&&inputPanelType==1&&template.itemPanel.contex==1)contex=1;
                    dim[i] = generateItemGroupBounds(cmp.itemGroup[i],cmp);
                    dimrow[j][0]=Math.max(dimrow[j][0],dim[i].width);
                    dimrow[j][1]=Math.max(dimrow[j][1],dim[i].height);
                    dimrow[j][2]=dimrow[j][2]+dim[i].width+template.itemPanel.spacing;
                   if(k==template.itemPanel.orientation)
                   {
                       k=-1;
                       j++;
                       
                   }
                }
            int max_w=max_width;
            for(int i=0;i<rows; i++) {
                max_height=Math.max(max_height, dimrow[i][1]); 
                max_width=Math.max(max_width, dimrow[i][0]);
                max_sum_width=Math.max(max_sum_width, dimrow[i][2]);
                height=height+dimrow[i][1]+template.itemPanel.spacing;
            }
            max_height=Math.max(max_height, 100); 
            max_sum_width=Math.max(max_sum_width, (type==1?300:540));
            int max_width1=max_width;
            max_width=Math.max(max_width, (type==1?300:540));
            if(inputPanelType==1 && cmp.itemGroup.length>1) {
                int wi=0;
                int he=(max_height+template.labelFont.font.getSize());
                if(contex==1)
                {
                    Dimension dim1 =getContexDimensions(cmp,f,template.itemPanel.contex_pos==0?(he+10):75);
                    if(template.itemPanel.contex_pos==0)
                        wi=wi+ template.itemPanel.spacing +(int)dim1.getWidth();
                    else
                        he=he+ template.itemPanel.spacing +(int)dim1.getHeight();
                }
                wi=wi+max_width1;
                wi=Math.max(wi,(type==1?300:540));
                if(type==0)wi=getSearchPanelDim(f,cmp).width;
                return new Dimension(wi,he+template.itemPanel.spacing);
            }
            else
            {
                int wi=(max_sum_width+(2+orientation)*template.itemPanel.spacing);
                if(type==0)wi=getSearchPanelDim(f,cmp).width;
                return new Dimension(wi,(height+2*template.itemPanel.spacing));
            }
        }
        return getTablePanelDimension(cmp,f);
    }
    
    public String getContex(Application.CompType cmp, Application.FormType f, int def_height){
        String r="";
        for(int i=0;i<cmp.itemGroup.length; i++) 
            {
                if(cmp.itemGroup[i].contex==1) {
                    int len=0;
                    for(int j=0;j<cmp.itemGroup[i].items.length; j++) {
                        if(cmp.itemGroup[i].items[j].elem.getClass().toString().equals("class ui.Application$AttType"))
                            len=len +1;
                        else
                            len=len +((Application.ItemGroup)cmp.itemGroup[i].items[j].elem).items.length;
                    }     
                    String[] column=new String[len];
                    int w=0;
                    int k=0;
                    for(int j=0;j<cmp.itemGroup[i].items.length; j++) {
                    if(cmp.itemGroup[i].items[j].elem.getClass().toString().equals("class ui.Application$AttType")){
                        column[k]=((Application.AttType)cmp.itemGroup[i].items[j].elem).title;
                        w=w+column[k].length()*template.labelFont.font.getSize()/2 +4 + (template.tablePanel.border_style==1?(template.tablePanel.border*2):2);
                        k=k+1;
                    }
                    else{
                        Application.ItemGroup ig=(Application.ItemGroup)cmp.itemGroup[i].items[j].elem;
                        for(int m=0;m<ig.items.length; m++) {
                            column[k]=((Application.AttType)ig.items[m].elem).title;
                            w=w+column[k].length()*template.labelFont.font.getSize()/2 + 4 + (template.tablePanel.border_style==1?(template.tablePanel.border*2):2);
                            k=k+1;
                        }
                        }
                    }     
                    String[][] val=new String[20][len];
                    for(int j=0;j<20; j++)
                        for(int l=0;l<len; l++)
                            val[j][l]=column[l]+" Value "+ (l+1) +""+(j+1);
                    int x=20;
                    int y=getTitleHeight();
                    int width=w+30;
                    int height=template.itemPanel.contex_height==-1?def_height:template.itemPanel.contex_height;
                    r=getTable(cmp,f,column,val,x,y,width,height,1);
                }
                }
        return r;
    }
    public Dimension getContexDimensions(Application.CompType cmp, Application.FormType f, int def_height){
        int w=0,h=0;
        for(int i=0;i<cmp.itemGroup.length; i++) 
            {
                if(cmp.itemGroup[i].contex==1) {
                    int len=0;
                    for(int j=0;j<cmp.itemGroup[i].items.length; j++) {
                        if(cmp.itemGroup[i].items[j].elem.getClass().toString().equals("class ui.Application$AttType"))
                            len=len +1;
                        else
                            len=len +((Application.ItemGroup)cmp.itemGroup[i].items[j].elem).items.length;
                    }     
                    String[] column=new String[len];
                    int k=0;
                    for(int j=0;j<cmp.itemGroup[i].items.length; j++) {
                    if(cmp.itemGroup[i].items[j].elem.getClass().toString().equals("class ui.Application$AttType")){
                        column[k]=((Application.AttType)cmp.itemGroup[i].items[j].elem).title;
                        w=w+column[k].length()*template.labelFont.font.getSize()/2 +4 + (template.tablePanel.border_style==1?(template.tablePanel.border*2):2);
                        k=k+1;
                    }
                    else{
                        Application.ItemGroup ig=(Application.ItemGroup)cmp.itemGroup[i].items[j].elem;
                        for(int m=0;m<ig.items.length; m++) {
                            column[k]=((Application.AttType)ig.items[m].elem).title;
                            w=w+column[k].length()*template.labelFont.font.getSize()/2 + 4 + (template.tablePanel.border_style==1?(template.tablePanel.border*2):2);
                            k=k+1;
                        }
                        }
                    }        
                }
                }
        return new Dimension(w+30,template.itemPanel.contex_height==-1?def_height:template.itemPanel.contex_height);
    }
    public String getTable(Application.CompType cmp, Application.FormType f, String[] column, String[][] list,int x, int y, int w, int h, int type){
        String r1=(type==2?"Search":(type==1?"Contex":""));
        String col="", val="";
        for(int i=0;i<column.length;i++)
            col=col+"<constant value=\""+ column[i] +"\"/>\n";
        for(int j=0;j<list.length;j++){
            val=val+"<constant>\n";
            for(int i=0;i<list[j].length;i++)
            val=val+"<constant value=\""+ list[j][i] +"\"/>\n";
            val=val+"</constant>\n";
        }
         String r=""+
        openPart(cmp.id + r1+"TableScroll","","JScrollPane") +
        openStyle() +
        getProperty("","","bounds", x +","+ y +","+ w +","+ h) +
        closeStyle() +
        openPart(cmp.id + r1+"Table","","JTable") +
        openStyle() +
        getProperty("","","selectionMode", "0") +
        getProperty("","","autoResizeMode", "0") +
        getProperty("","","columnNames","" +
        "\n<constant model=\"list\">\n" +
        col +
        "</constant>\n")+
        getProperty("","","content","" +
        "\n<constant model=\"table.rowMajor\">\n" +
        val +
        "</constant>\n")+
        closeStyle() +
        closePart() +
        closePart();
        return r;
    }
     public Dimension getButtonPanelDimension(Application.CompType cmp, Application.FormType f){
        return new Dimension(580, 120);
     }
    public Dimension getButtonInputPanelDimension(Application.CompType cmp, Application.FormType f){
       return new Dimension(300, 80);
    }
    public Dimension getButtonSearchPanelDimension(Application.CompType cmp, Application.FormType f){
       return new Dimension(450, 80);
    }
    public Dimension getMenuPanelDimension(Application.CompType cmp, Application.FormType f){
        Dimension dim=new Dimension(0, 0);
        for (int i=0; i<f.compType.length;i++)
            if(f.compType[i].superord==cmp.id && cmp.layout==0 && template.formPanel.menuCall==1)
                dim=new Dimension(540, template.labelFont.font.getSize()+15);
        return dim;
    }
    public Dimension getToolbarDim(Application.CompType cmp, Application.FormType f){
        Dimension dim=new Dimension(0, 0);
        for (int i=0; i<f.compType.length;i++)
            if(f.compType[i].superord==cmp.id && f.compType[i].layout==0 && template.formPanel.buttonCall==1)
                dim=new Dimension(500, 40);
        return dim;
    }
    public Dimension getToolbarDimension(Application.CompType cmp, Application.FormType f){
        Dimension dim=new Dimension(0, 0);
        for (int i=0; i<f.compType.length;i++)
            if(f.compType[i].superord==cmp.id && f.compType[i].layout==0 && template.formPanel.buttonCall==1)
                dim=new Dimension(500, 40+template.itemPanel.spacing);
        return dim;
    }
    public String generateButtons(Application.FormType f, Application.CompType cmp,int x, int y){
      String r=""; 
      int cid=(cmp.superord>-1&&cmp.layout==1)?cmp.superord:cmp.id;
      String masiveDelete="";
      String cmpSearch="";
      String cmpSelect="";
      if(template.buttonPanel.style==1)
      {
        if(cmp.del_masive==1){
          masiveDelete=""+
          openPart(cmp.id + "MasiveDeleteButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/obsolutereport.gif") +
          getProperty("","","toolTipText", "Delete All") +
          (cmp.del_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
          closeStyle() +
          closePart();
        }
          if(cmp.search==1){
              cmpSearch=""+
              openPart(cmp.id + "SearchButton","","JButton") +
              openStyle() +
              getProperty("","","preferredSize", "30,30") +
              getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/filter.gif") +
              getProperty("","","toolTipText", "Search") +
              closeStyle() +
              getBehavior(getProperty(cmp.id+"compTypeSearch","","visible", "true")) +
              closePart();
          }  
          if(cmp.id==2){
              cmpSelect=""+
              openPart(cmp.id + "SelectButton","","JButton") +
              openStyle() +
              getProperty("","","preferredSize", "30,30") +
              getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/selectall.gif") +
              getProperty("","","toolTipText", "Select") +
              closeStyle() +
              closePart();
          }
      r="" +
          openPart(cid + "NavigationPanel","","JPanel") +
          openStyle() +
          getProperty("","","bounds", x+","+y+",640,35") +
          getProperty("","","opaque", "false") +
          closeStyle() +
          (cmp.id==2?cmpSelect:"")+
          openPart(cid + "FirstButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/prevv.gif") + 
          getProperty("","","toolTipText", "First record") +
          closeStyle() +
          closePart()+
          openPart(cid + "PrevButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/prev.gif") +
          getProperty("","","toolTipText", "Previos record record") +
          closeStyle() +
          closePart()+
          openPart(cid + "Records","","JLabel") +
          openStyle() + 
          getProperty("","","horizontalAlignment", "CENTER") +
          getProperty("","","preferredSize", "75,30") +
          getProperty("","","text", "Record: 1/100") +
          closeStyle() +
          closePart()+
          openPart(cid + "NextButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/next.gif") +
          getProperty("","","toolTipText", "Next record") +
          closeStyle() +
          closePart()+
          openPart(cid + "LastButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/nextt.gif") +
          getProperty("","","toolTipText", "Last record") +
          closeStyle() +
          closePart()+
          (cmp.search==1?cmpSearch:"")+
          closePart()+
          openPart(cid + "ButtonPanel","","JPanel") +
          openStyle() +
          getProperty("","","bounds", x+","+(y+35)+",640,35") +
          getProperty("","","opaque", "false") +
          closeStyle() +
          openPart(cid + "NewButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/new.gif") +
          getProperty("","","toolTipText", "New") +
          closeStyle() +
          getBehavior(getProperty(cid+"compTypeInput","","visible", "true")) +
          closePart() +
          openPart(cid + "DuplicateButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/copy.gif") +
          getProperty("","","toolTipText", "Duplicate") +
          (cmp.ins_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
          closeStyle() +
          getBehavior(getProperty(cid+"compTypeInput","","visible", "true")) +
          closePart() +
          openPart(cid + "DeleteButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/erase.gif") +
          getProperty("","","toolTipText", "Delete") +
          closeStyle() +
          closePart()+
          (cmp.del_masive==1?masiveDelete:"")+
          openPart(cid + "ApplyButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/apply.gif") +
          getProperty("","","toolTipText", "Apply") +
          closeStyle() +
          closePart() +
          openPart(cid + "OKButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/ok.gif") +
          getProperty("","","toolTipText", "OK") +
          closeStyle() +
          closePart() +
          openPart(cid + "Cancel","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/close.gif") +
          getProperty("","","toolTipText", "Cancel") +
          closeStyle() +
          getBehavior(getProperty(cid+"compType","","visible", "false")) +
          closePart() +
          closePart();
      }
      else
      {
      if(cmp.search==1){
          cmpSearch=""+
          openPart(cmp.id + "SearchButton","","JButton") +
          openStyle() +
          getProperty("","","text", "Search") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+"compTypeSearch","","visible", "true")) +
          closePart();
      }
      if(cmp.id==2){
          cmpSelect=""+
          openPart(cmp.id + "SelectButton","","JButton") +
          openStyle() +
          getProperty("","","text", "Select") +
          closeStyle() +
          closePart();
      }
      if(cmp.del_masive==1){
          masiveDelete=""+
          openPart(cmp.id + "MasiveDeleteButton","","JButton") +
          openStyle() +
          getProperty("","","text", "Delete All") +
          closeStyle() +
          closePart();
      }
      r="" +
      openPart(cid + "NavigationPanel","","JPanel") +
      openStyle() +
      getProperty("","","bounds", x+","+y+",640,35") +
      getProperty("","","opaque", "false") +
      closeStyle() +
      (cmp.id==2?cmpSelect:"")+
      openPart(cid + "FirstButton","","JButton") +
      openStyle() +
      getProperty("","","text", "First") +
      closeStyle() +
      closePart()+
      openPart(cid + "PrevButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Prev") +
      closeStyle() +
      closePart()+
      openPart(cid + "Records","","JLabel") +
      openStyle() + 
      getProperty("","","horizontalAlignment", "CENTER") +
      getProperty("","","preferredSize", "75,30") +
      getProperty("","","text", "Record: 1/100") +
      closeStyle() +
      closePart()+
      openPart(cid + "NextButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Next") +
      closeStyle() +
      closePart()+
      openPart(cid + "LastButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Last") +
      closeStyle() +
      closePart()+
      (cmp.search==1?cmpSearch:"")+
      closePart()+
      openPart(cid + "ButtonPanel","","JPanel") +
      openStyle() +
      getProperty("","","bounds", x+","+(y+35)+",640,35") +
      getProperty("","","opaque", "false") +
      closeStyle() +
      openPart(cid + "NewButton","","JButton") +
      openStyle() +
      getProperty("","","text", "New") +
      closeStyle() +
      getBehavior(getProperty(cid+"compTypeInput","","visible", "true")) +
      closePart() +
      openPart(cid + "DuplicateButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Duplicate") +
      (cmp.ins_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
      closeStyle() +
      getBehavior(getProperty(cid+"compTypeInput","","visible", "true")) +
      closePart() +
      openPart(cid + "DeleteButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Delete") +
      closeStyle() +
      closePart()+
      (cmp.del_masive==1?masiveDelete:"")+
      openPart(cid + "ApplyButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Apply") +
      closeStyle() +
      closePart() +
      openPart(cid + "OKButton","","JButton") +
      openStyle() +
      getProperty("","","text", "OK") +
      closeStyle() +
      closePart() +
      openPart(cid + "Cancel","","JButton") +
      openStyle() +
      getProperty("","","text", "Cancel") +
      closeStyle() +
      getBehavior(getProperty(cid+"compType","","visible", "false")) +
      closePart() +
      closePart();
      }
      return r;   
  }
    public String generateInputButtons(Application.FormType f, Application.CompType cmp,int x, int y){
      String r=""; 
      if(template.buttonPanel.style==1)
      {
      r="\n" +
          openPart(cmp.id + "ButtonPanel","","JPanel") +
          openStyle() +
          getProperty("","","bounds", x+","+y+",540,35") +
          getProperty("","","opaque", "false") +
          closeStyle() +     
          openPart(cmp.id + "OKButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/ok.gif") +
          getProperty("","","toolTipText", "OK") +
          closeStyle() +
          closePart() +
          openPart(cmp.id + "Cancel","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/close.gif") +
          getProperty("","","toolTipText", "Cancel") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+"compTypeInput","","visible", "false")) +
          closePart() +
          closePart();
      }
      else
      {
      r="\n" +
      openPart(cmp.id + "ButtonPanel","","JPanel") +
      openStyle() +
      getProperty("","","bounds", x+","+y+",540,35") +
      getProperty("","","opaque", "false") +
      closeStyle() +
      openPart(cmp.id + "OKButton","","JButton") +
      openStyle() +
      getProperty("","","text", "OK") +
      closeStyle() +
      closePart() +
      openPart(cmp.id + "Cancel","","JButton") +
      openStyle() +
      getProperty("","","text", "Cancel") +
      closeStyle() +
      getBehavior(getProperty(cmp.id+"compTypeInput","","visible", "false")) +
      closePart() +
      closePart();
      }
      return r;   
    }
    public String generateSearchButtons(Application.FormType f, Application.CompType cmp,int x, int y){
      String r=""; 
      if(template.buttonPanel.style==1)
      {
      r="\n" +
          openPart(cmp.id + "ButtonPanel","","JPanel") +
          openStyle() +
          getProperty("","","bounds", x+","+y+",540,35") +
          getProperty("","","opaque", "false") +
          closeStyle() +
          openPart(cmp.id + "OKButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/ok.gif") +
          getProperty("","","toolTipText", "OK") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+"compTypeSearch","","visible", "false")) +
          closePart() +
          openPart(cmp.id + "Cancel","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/close.gif") +
          getProperty("","","toolTipText", "Cancel") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+"compTypeSearch","","visible", "false")) +
          closePart() +
          closePart();
      }
      else
      {
      r="\n" +
      openPart(cmp.id + "ButtonPanel","","JPanel") +
      openStyle() +
      getProperty("","","bounds", x+","+y+",540,35") +
      getProperty("","","opaque", "false") +
      closeStyle() +
      openPart(cmp.id + "OKButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Select") +
      closeStyle() +
      getBehavior(getProperty(cmp.id+"compTypeSearch","","visible", "false")) +
      closePart() +
      openPart(cmp.id + "Cancel","","JButton") +
      openStyle() +
      getProperty("","","text", "Cancel") +
      closeStyle() +
      getBehavior(getProperty(cmp.id+"compTypeSearch","","visible", "false")) +
      closePart() +
      closePart();
      }
      return r;   
    }
    public String generateCompTypeMenu(Application.FormType f, Application.CompType cmp) { 
        String r="";
        for (int i=0; i<f.compType.length;i++)
        {
        if(f.compType[i].superord==cmp.id && cmp.layout==0 && template.formPanel.menuCall==1)
            r=r+    
            openPart(f.compType[i].id+"compTypeMenu","","JMenuItem") +
            openStyle() +
            getProperty("","","text",f.compType[i].name) +
            (template.formPanel.menuCall==1?getProperty("","","mnemonic", ""+(0x43)):"")+
            closeStyle() +
            getBehavior(getProperty(f.compType[i].id+"compType","","visible","true")) +
            closePart();
        }
        String ret="";
        if(!r.equals(""))
        ret =
        openPart(cmp.id+"compTypeMenu","","JMenuBar") +
          r +
        closePart();
        return ret;
    }
    public String generateCompTypeToolbar(Application.FormType f, Application.CompType cmp) { 
        String r="";
        int width=0;
        for (int i=0; i<f.compType.length;i++)
        { 
        if(f.compType[i].superord==cmp.id && f.compType[i].layout==0)
        {
            int x=(f.compType[i].name.length()*(template.labelFont.font.isBold()?template.labelFont.font.getSize()/2:(template.labelFont.font.getSize()*2/3)))+40;
            r=r+    
            openPart(cmp.id + f.compType[i].id + "ToolbarButton","","JButton") +
            openStyle() +
            getProperty("","","text", f.compType[i].name) +
            getProperty("","","preferredSize", x +",30") +
            closeStyle() +
            getBehavior(getProperty(f.compType[i].id+"compType","","visible","true")) +
            closePart();
            width=width+x+2;
        }
        }
        String ret="";
        int x=(template.formPanel.title==1?getTitleHeight():0);
        if(!r.equals(""))
        ret =
        openPart(cmp.id+"Toolbar","","JPanel") +
        openStyle() +
        getProperty("","","opaque","false") +
        getProperty("","","bounds", 20+","+x+","+width+","+getToolbarDim(cmp, f).height) +
        closeStyle() +
        r +
        closePart();
        return ret;
    }
    public String generateFormTypeMenu(Application app) { 
        String r="";
        for (int i=0; i<app.formType.length;i++)
        {
        r=r+    
        openPart(app.formType[i].id+"formTypeMenu","","JMenuItem") +
        openStyle() +
        getProperty("","","text", app.formType[i].name) +
        (template.formPanel.menuCall==1?getProperty("","","mnemonic", ""+(app.formType[i].mnemonic)):"")+
        closeStyle() +
        getBehavior(getProperty(app.formType[i].compType[0].id+"compType","","visible", "true")+getProperty(app.formType[i].compType[0].id+"compType","","selected", "true")) +
        closePart();
        }
        String ret="";
        if(!r.equals(""))
        ret = 
        openPart(app.menu+"formTypeMenuBar","","JMenu") +
        openStyle() +
        getProperty("","","text", app.menu) +
        (template.formPanel.menuCall==1?getProperty("","","mnemonic", ""+(0x41)):"")+
        getProperty("","","background",getColor(template.bg)) +
        getProperty("","","foreground",getColor(template.fg)) +
        getProperty("","","font",getFont(template.labelFont.font)) +
        closeStyle() +
        openPart("subMenu","","JMenu") +
        openStyle() +
        getProperty("","","text", "Submenu") +
        (template.formPanel.menuCall==1?getProperty("","","mnemonic", ""+(0x53)):"")+
        closeStyle() +
        openPart("subMenuItem","","JMenuItem") +
        openStyle() +
        getProperty("","","text", "Submenu Item 1") +
        (template.formPanel.menuCall==1?getProperty("","","mnemonic", ""+(0x30)):"")+
        getProperty("","","enabled", "false") +
        closeStyle() +
        closePart() +        
        openPart("subMenuItem","","JMenuItem") +
        openStyle() +
        getProperty("","","text", "Submenu Item 2") +
        (template.formPanel.menuCall==1?getProperty("","","mnemonic", ""+(0x31)):"")+
        getProperty("","","enabled", "false") +
        closeStyle() +
        closePart() +
        closePart() +
          r +
        closePart();
        return ret;
    }
  public String generateMainMenu() {
    String ret="\n" +
    openPart("mainMenubar","","JMenuBar") +
    openPart("mainFile","","JMenu") +
    openStyle() +
    getProperty("","","text", "File") +
    (template.formPanel.menuCall==1?getProperty("","","mnemonic", ""+0x46):"") +
    closeStyle() +
    openPart("mainClose","","JMenuItem") +
    openStyle() +
    getProperty("","","text", "Close") +
    getProperty("","","mnemonic", ""+0x43) +
    closeStyle() +
    closePart() +
    getPart("mainSeparate","","JSeparator") +
    openPart("mainQuit","","JMenuItem") +
    openStyle() +
    getProperty("","","text", "Quit") +
    (template.formPanel.menuCall==1?getProperty("","","mnemonic", ""+0x51):"") +
    closeStyle() +
    getBehavior(getProperty("MainFrame","","visible", "false")) +
    closePart() +
    closePart() +
    generateFormTypeMenu(application) +
    openPart("mainHelp","","JMenu") +
    openStyle() +
    getProperty("","","text", "Help") +
    (template.formPanel.menuCall==1?getProperty("","","mnemonic", ""+0x48):"") +
    closeStyle() +
    openPart("mainAbout","","JMenuItem") +
    openStyle() +
    getProperty("","","text", "About") +
    (template.formPanel.menuCall==1?getProperty("","","mnemonic", ""+0x41):"") +
    closeStyle() +
    getBehavior(getProperty("About","","visible", "true")) +
    closePart() + 
    closePart() +
    closePart();
    return ret;
  }
    public String generateAbout(){
        int size=template.labelFont.font.getSize();
        int width=size*25+50;
        String r=""; 
        r=r+
        openPart("About","","JDialog") +
        openStyle() +
        getProperty("","","defaultCloseOperation", "1") +
        getProperty("","","title", "About") +
        getProperty("","","visible", "false") +
        getProperty("","","resizable", "false") +
        getProperty("","","modal", "true") +
        getProperty("","","layout", "null") +
        getProperty("","","bounds", "20,20,"+width+","+(150+2*size+20)) +
        closeStyle()+
        openPart("labelIcon","","JLabel") +
        openStyle() +
        getProperty("","","bounds", ((width-32)/2)+",20,32,32") +
        getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/iist-big.gif") +
        closeStyle()+
        closePart()+
        openPart("labelAbout","","JLabel") +
        openStyle() +
        getProperty("","","bounds", "0,60,"+width+","+(size+5)) +
        getProperty("","","text", "The application is generated by") +
        getProperty("","","horizontalAlignment","CENTER") +
        closeStyle()+
        closePart()+
        openPart("labelAbout","","JLabel") +
        openStyle() +
        getProperty("","","bounds", "0,"+ (60+size+5+5) +","+width+","+(size+5)) +
        getProperty("","","text", "IIS*UIModeler Design Template Tool Version 6.3.1") +
        getProperty("","","horizontalAlignment","CENTER") +
        closeStyle()+
        closePart()+
        openPart("buttonAbout","","JButton") +
        openStyle() +
        getProperty("","","text", "Close") +
        getProperty("","","bounds", ((width-70)/2)+","+(90+2*size)+",70,30") +
        closeStyle()+
        getBehavior(getProperty("About","","visible", "false")) +
        closePart()+
        closePart(); 
        return r;
    }

    public String generateMenuForm(){
        int size=template.labelFont.font.getSize();
        String r=""; 
        r=r+
        openPart("MenuForm","","JInternalFrame") +
        openStyle() +
        getProperty("","","defaultCloseOperation", "1") +
        getProperty("","","title", "IIS*UIModeler Template "+ template.name) +
        getProperty("","","visible", "false") +
        getProperty("","","resizable", "false") +
        getProperty("","","layout", "null") +
        getProperty("","","bounds", "20,20,580,250") +
        getProperty("","","closable", "false") +
        getProperty("","","iconifiable", "false") +
        getProperty("","","frameIcon", template.icon) +
        closeStyle()+
        openPart("menuPanel1","","JPanel") +
        openStyle() +
        getProperty("","","bounds", "0,30,580,35") +
        getProperty("","","opaque", "false") +
        closeStyle() +
        openPart("0menuButton","","JButton") +
        openStyle() +
        getProperty("","","text", application.formType[0].name) +
        getProperty("","JButton","preferredSize","250,30") +
        closeStyle() +
        getBehavior(getProperty("1compType","","visible", "true")) +
        closePart()+
        openPart("1menuButton","","JButton") +
        openStyle() +
        getProperty("","","text", application.formType[1].name) +
        getProperty("","JButton","preferredSize","250,30") +
        closeStyle() +
        getBehavior(getProperty("2compType","","visible", "true")) +
        closePart()+
        closePart()+
        openPart("menuPanel2","","JPanel") +
        openStyle() +
        getProperty("","","bounds", "0,85,580,35") +
        getProperty("","","opaque", "false") +
        closeStyle() +
        openPart("2menuButton","","JButton") +
        openStyle() +
        getProperty("","","text", application.formType[2].name) +
        getProperty("","JButton","preferredSize","250,30") +
        closeStyle() +
        getBehavior(getProperty("3compType","","visible", "true")) +
        closePart()+
        openPart("3menuButton","","JButton") +
        openStyle() +
        getProperty("","","text", application.formType[3].name) +
        getProperty("","JButton","preferredSize","250,30") +
        closeStyle() +
        getBehavior(getProperty("4compType","","visible", "true")) +
        closePart()+
        closePart()+
        openPart("menuPanel3","","JPanel") +
        openStyle() +
        getProperty("","","bounds", "0,140,580,35") +
        getProperty("","","opaque", "false") +
        closeStyle() +
        openPart("4menuButton","","JButton") +
        openStyle() +
        getProperty("","","text", application.formType[4].name) +
        getProperty("","JButton","preferredSize","250,30") +
        closeStyle() +
        getBehavior(getProperty("5compType","","visible", "true")) +
        closePart()+
        openPart("5menuButton","","JButton") +
        openStyle() +
        getProperty("","","text", application.formType[5].name) +
        getProperty("","JButton","preferredSize","250,30") +
        closeStyle() +
        getBehavior(getProperty("6compType","","visible", "true")) +
        closePart()+
        closePart()+
        closePart(); 
        return r;
    }
  public String getColor(Color c)
  {
   String r,g,b;
   r=Integer.toHexString(c.getRed());
   g=Integer.toHexString(c.getGreen());
   b=Integer.toHexString(c.getBlue());
   if(r.equals("0"))r="00";
   if(g.equals("0"))g="00";
   if(b.equals("0"))b="00";
   return r+g+b;
  }
    public Border getBorders(int border_style, int border, Color color) {
        Border b=BorderFactory.createBevelBorder(border);
          if(border_style==0)
              b=BorderFactory.createEmptyBorder(border,border,border,border);
          else if(border_style==1)
              b=BorderFactory.createLineBorder(color, border);
          else if(border_style==2)
              b=BorderFactory.createEtchedBorder();
          else if(border_style==3)
              b=BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
          else if(border_style==4)
              b=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
          else if(border_style==5)
              b=BorderFactory.createBevelBorder(BevelBorder.RAISED);
          else if(border_style==6)
              b=BorderFactory.createBevelBorder(BevelBorder.LOWERED);
          else if(border_style==7)
              b=BorderFactory.createTitledBorder("Title");
          return b;
    }
    class MyHeaderRenderer extends JLabel implements 
                TableCellRenderer, Serializable 
    {
        public  MyHeaderRenderer() 
        { 
            setOpaque(true);
            setBorder(getBorders(template.tablePanel.border_style,template.tablePanel.border, template.tablePanel.fg)); 
            setForeground(template.tablePanel.fg); 
            setBackground(template.tablePanel.bg);
            setFont(template.labelFont.font);
               
        } 
        public Component getTableCellRendererComponent(JTable table, 
                Object value, boolean isSelected, boolean hasFocus, int row, 
                int column) 
        { 
                this.setText(value.toString()); 
                return this; 
        } 

    }
    class MyCellRenderer extends JLabel implements 
                TableCellRenderer, Serializable 
    {
        public  MyCellRenderer() 
        { 
            setOpaque(true);
            setFont(template.inputFont.font);
               
        } 
        public Component getTableCellRendererComponent(JTable table, 
                Object value, boolean isSelected, boolean hasFocus, int row, 
                int column) 
        { 
        
                this.setText(value.toString()); 
                this.setBorder(getBorders(template.tablePanel.border_style,template.tablePanel.border, template.tablePanel.fg)); 
                if(isSelected)
                {
                    this.setBackground(table.getSelectionBackground());
                    this.setForeground(table.getSelectionForeground());
                }
                else {
                    this.setForeground(template.tablePanel.input_fg); 
                    this.setBackground(template.tablePanel.input_bg);
                }
                return this; 
        } 
        public boolean isEditable() {
            return false;
        }
    }
    public class MyListCellRenderer extends JLabel implements ListCellRenderer {
         public MyListCellRenderer() {
             setOpaque(true);
         }
         public Component getListCellRendererComponent(
             JList list, 
             Object value, 
             int index, 
             boolean isSelected, 
             boolean cellHasFocus) 
         {
             
             if(value!=null)setText(value.toString());
             setFont(template.inputFont.font);
             setBackground(isSelected ? list.getSelectionBackground() : template.itemPanel.input_bg);
             setForeground(isSelected ? list.getSelectionForeground() : template.itemPanel.input_fg);
             return this;
         }
     } 
}
