package ui;

import iisc.Settings;
import java.awt.Dimension;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.io.*;
import com.harmonia.renderer.Renderer;

import iisc.JDBCQuery;

import iisc.lang.JSourceCodeEditor;
import iisc.lang.vmashine.VirtualMashine;

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
import javax.swing.ButtonGroup;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;

// For write operation
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerConfigurationException; 
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class Generator { 
   private Connection con;
   private Renderer r = new Renderer();
   private Template template;
   private Application app;
   private Application.ApplicationSystem application;
   private Application.ApplicationSystem mApplication;
   private JFrame frame;
   private JDesktopPane desktop;
   private SubSchemaGen[] schemas;
   private String url;
   private String username;
   private String password;
   String window = "";
   int curr;
   //Aleksandar - begin
   Document funcDoc;
   VirtualMashine vm;
   //Aleksandar - end
   
public Generator (Connection connection, SubSchemaGen[] _schemas, String _url, String _username, String _password) 
  {  
    url=_url;
    username=_username;
    password=_password;
    con=connection; 
    schemas=_schemas;
    try {
    File dir1 = new File (".");
    System.setProperty("LIQUIDUI",dir1.getCanonicalPath()); 
    System.out.println(System.getProperty("LIQUIDUI")); 
   }
  catch(Exception e) 
  {
    e.printStackTrace();
  }
    
      //Aleksandar - begin
      try 
      {
        String xmlTxt = JSourceCodeEditor.LoadCodeFromRepository(-1, connection);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        funcDoc = builder.parse(new java.io.ByteArrayInputStream(xmlTxt.getBytes("utf-8")));
        vm = new VirtualMashine();
        vm.InitAssemblerCode(funcDoc);
        vm.SetConnection(url, username, password);
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
      //Aleksandar - end
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
    if (frame!=null)
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
   setApplicationProperties(r,app);
   if (!renderOK) System.out.print("Error!"); 
  }
public void Center(JDialog dialog) {
    Settings.CenterRelative(dialog,frame);
}
public void Center(JInternalFrame dialog) {
    Settings.CenterRelative(dialog,frame);
}
public void setFormAppParameter(Application.ApplicationSystem app){
    for(int j=0;j<app.formType.length;j++){
        Application.FormType c=app.formType[j];
        Iterator its1=c.menu.iterator();
        while(its1.hasNext()) {
            Application.Menu txt1=(Application.Menu)its1.next();
                Iterator its2=txt1.passedValues.iterator();
                while(its2.hasNext()) {
                    Application.PassedValue txt2=(Application.PassedValue)its2.next();
                    txt2.attribute=r.getPartByName(""+txt2.att +txt2.tob+"InputField");
                }
        }
        its1=c.parentMenu.iterator();
        while(its1.hasNext()) {
            Application.Menu txt1=(Application.Menu)its1.next();
                Iterator its2=txt1.passedValues.iterator();
                while(its2.hasNext()) {
                    Application.PassedValue txt2=(Application.PassedValue)its2.next();
                    txt2.attribute=r.getPartByName(""+txt2.att +txt2.tob+"InputField");
                }
        }
        its1=c.parameters.iterator();
        while(its1.hasNext()) {
            Application.Parameter txt1=(Application.Parameter)its1.next();
            txt1.attribute=r.getPartByName(""+txt1.att +txt1.tob+"InputField");
        }
    }
}
public void setFormLOV(Application.FormType f, Application.ApplicationSystem app){
    for(int j=0;j<f.compType.length;j++){
        Application.CompType c=f.compType[j];
        DBPanel panel1= (DBPanel)r.getPartByName(""+c.id+"DataPanel");
        if(panel1!=null)panel1.setLOVvalidate();
        DBTable table1 =(DBTable)r.getPartByName(""+c.id+"Table");
        if(table1!=null)table1.setLOVvalidate();
    }
}
public void setFormApp(Application.FormType f, Application.ApplicationSystem app){
    if(f.type==2)
    {
        JInternalFrame fram=(JInternalFrame)r.getPartByName(""+f.id+"menuFormType");
        if(fram!=null && app.entry==f.id)
            fram.setVisible(true);
        return;
    }
    int s=-1;
    for (int k=0; k<schemas.length;k++){
        if(schemas[k].tf.id==f.id)
            s=k;
    }
    f.Pu.addAll(schemas[s].Pu);
    f.Pa.addAll(schemas[s].Pa);
    
    for(int j=0;j<f.compType.length;j++){
        Application.CompType c=f.compType[j];
        JDialog dialog=null;
        JInternalFrame fram=null;
        if(c.superord==-1)
        {
            fram=(JInternalFrame)r.getPartByName(""+c.id+"compType");
            if(app.entry==f.id)
                fram.setVisible(true);
        }
        else
            dialog=(JDialog)r.getPartByName(""+c.id+"compType");
        if(dialog!=null)
        dialog.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                Center((JDialog)e.getSource());
            }
        });
        if(fram!=null)
        fram.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
 //               Center((JInternalFrame)e.getSource());
            }
        });
       if(c.data_layout==1)
       {
           dialog=(JDialog)r.getPartByName(""+c.id+"compTypeInput");
           if(dialog!=null)
           dialog.addPropertyChangeListener(new PropertyChangeListener() {
               public void propertyChange(PropertyChangeEvent e) {
                   Center((JDialog)e.getSource());
               }
           });
           
           DBTable table =(DBTable)r.getPartByName(""+f.compType[j].id+"Table");
           if(c.search==1){
                table.searchTable=(JTable)r.getPartByName(""+f.compType[j].id+"SearchTable");
           }
           if(c.del_masive==1){
                table.masiveDeleteTable=(JTable)r.getPartByName(""+f.compType[j].id+"MasiveDeleteTable");
                table.masive_delete=1;
           }
           if(!f.parameters.isEmpty())
                table.parameterTable=(JTable)r.getPartByName(""+f.id+"ParametersTable");  
           table.selectButton=(JButton)r.getPartByName(""+f.compType[j].id+"SelectButton");
           table.setTemplate(template);
           /*if(f.compType[j].superord!=-1){
               table.parent=(DBPanel)r.getPartByName(""+f.compType[j].superord+"DataPanel");
               if(table.parent==null)
                   table.parent=(DBTable)r.getPartByName(""+f.compType[j].superord+"Table");
           }*/
           int cid=f.compType[j].id;
           table.record=(JLabel)r.getPartByName(""+cid+"Records");
           /*if(table.record==null){
               cid=f.compType[j].superord;
               table.record=(JLabel)r.getPartByName(""+cid+"Records");
           }*/
           
            //Aleksandar - Begin Dodavanje listenera kada je u pitanju calc atribut 
            TextBoxChangeListener.CreateDataListeners(f, con, r, funcDoc, vm, c);
            //Aleksandar end
            
           table.setForm(f,con, app.entry);
           Set nig=c.getNestedItemGroups();
           for(int l=0;l<c.itemGroup.length;l++){
               for(int m=0;m<c.itemGroup[l].items.length;m++){
               if(nig.contains(""+c.itemGroup[l].id))continue;
               Application.Item it=c.itemGroup[l].items[m];
               if(it.elem.getClass().toString().equals("class ui.Application$AttType")){
                   addTableFields(it, c, table, l);  
               }
               else{
                   Application.ItemGroup igi=(Application.ItemGroup)it.elem;
                   for(int n=0;n<igi.items.length; n++) {
                       addTableFields(igi.items[n], c, table, l); 
                   }
               }
           }
           }
           
           //Aleksandar - begin 
           if (c.superord==-1)
           {
                table.RaiseLoadDataListeners(0);
           }
           //Aleksandar - end
           table.setModel();
           //Aleksandar - begin 
           if (c.superord==-1)
           {
                table.RaiseLoadDataListeners(1);
           }
           //Aleksandar - end
           if(table.searchTable!=null)table.setSearchModel();
           if(table.masiveDeleteTable!=null)table.setMasiveDeleteModel();
           if(table.parameterTable!=null)table.setParameterModel();
           //setTableProperties(table);
           setTableProperties(table.searchTable);
           setTableProperties(table.masiveDeleteTable);
           setTableProperties(table.parameterTable);
           if(c.layout==0 && c.superord!=-1)
           {
                for(int i=0;i<f.compType.length;i++){
                   if(c.superord==f.compType[i].id)
                       for(int k=0;k<f.compType[i].keys.length;k++){
                           Iterator it1=f.compType[i].keys[k].iterator();
                           while(it1.hasNext()){
                               Application.AttType at=(Application.AttType)it1.next();
                               Object[] masterField=new  Object[2];
                               masterField[0]=at;
                               masterField[1] =r.getPartByName(""+at.att_id +c.id+"MasterInputField");
                               if(masterField[1]!=null)table.masterAttributes.add(masterField);
                           }
                       }
                }
           }
       }
       else
           {
               DBPanel panel =(DBPanel)r.getPartByName(""+f.compType[j].id+"DataPanel");
               panel.setTemplate(template);
               if(c.search==1)
                    panel.searchTable=(JTable)r.getPartByName(""+f.compType[j].id+"SearchTable");
               if(c.del_masive==1)
               {
                    panel.masiveDeleteTable=(JTable)r.getPartByName(""+f.compType[j].id+"MasiveDeleteTable");
                    panel.masive_delete=1;
               }
               panel.contexTable=(JTable)r.getPartByName(""+f.compType[j].id+"ContexTable");
               if(!f.parameters.isEmpty())
                    panel.parameterTable=(JTable)r.getPartByName(""+f.id+"ParametersTable");
               panel.selectButton=(JButton)r.getPartByName(""+f.compType[j].id+"SelectButton"); 
               /*if(f.compType[j].superord!=-1){
                   panel.parent=(DBPanel)r.getPartByName(""+f.compType[j].superord+"DataPanel");
                   if(panel.parent==null)
                       panel.parent=(DBTable)r.getPartByName(""+f.compType[j].superord+"Table");
               }*/
               int cid=f.compType[j].id;
               panel.record=(JLabel)r.getPartByName(""+cid+"Records");
               /*if(panel.record==null){
                   cid=f.compType[j].superord;
                   panel.record=(JLabel)r.getPartByName(""+cid+"Records");
               }*/
               
                               
               //Aleksandar - Begin Dodavanje listenera kada je u pitanju calc atribut 
               TextBoxChangeListener.CreateDataListeners(f, con, r, funcDoc, vm, c);
               //Aleksandar end
                
               panel.setForm(f,con, app.entry);
               for(int l=0;l<c.itemGroup.length;l++){
                   JPanel itg=(JPanel)r.getPartByName(""+c.itemGroup[l].id +c.id+"itemGroup");
                   if(itg!=null)
                    itg=(JPanel)r.getPartByName(""+c.itemGroup[l].id +c.id+"nestedItemGroup");
                   if(itg!=null)
                   panel.itemGroupPanels.add(itg);
                   if(c.itemGroup[l].contex==1 && template.itemPanel.contex==1 && template.itemPanel.type==1)
                   {
                       JTabbedPane pane=(JTabbedPane)r.getPartByName(""+c.id+"TabbedPane");
                       if(itg!=null)
                            pane.remove(itg);
                   }
                   Set nig=c.getNestedItemGroups();
                   for(l=0;l<c.itemGroup.length;l++){
                       for(int m=0;m<c.itemGroup[l].items.length;m++){
                       if(nig.contains(""+c.itemGroup[l].id))continue;
                       Application.Item it=c.itemGroup[l].items[m];
                       if(it.elem.getClass().toString().equals("class ui.Application$AttType")){
                           addPanelFields(it, c, panel, l);  
                       }
                       else{
                           Application.ItemGroup igi=(Application.ItemGroup)it.elem;
                           for(int n=0;n<igi.items.length; n++) {
                               addPanelFields(igi.items[n], c, panel, l); 
                           }
                       }
                       }
               } 
               }
               panel.pane=(JTabbedPane)r.getPartByName(""+c.id+"TabbedPane");
               panel.setItemGroupPanels();
               panel.Connect();
               /*if(panel.method!=3)
               panel.setData();*/
               
               //panel.setCaller(-1);
               if(panel.searchTable!=null)panel.setSearchModel();
               if(panel.masiveDeleteTable!=null)panel.setMasiveDeleteModel();
               if(panel.parameterTable!=null)panel.setParameterModel();
               setTableProperties(panel.searchTable);
               setTableProperties(panel.contexTable);
               setTableProperties(panel.masiveDeleteTable);
               setTableProperties(panel.parameterTable);
               
               if(c.layout==0 && c.superord!=-1)
               {
                    for(int i=0;i<f.compType.length;i++){
                           if(c.superord==f.compType[i].id)
                               for(int k=0;k<f.compType[i].keys.length;k++){
                                   Iterator it1=f.compType[i].keys[k].iterator();
                                   while(it1.hasNext()){
                                       Application.AttType at=(Application.AttType)it1.next();
                                       Object[] masterField=new  Object[2];
                                       masterField[0]=at;
                                       masterField[1] =r.getPartByName(""+at.att_id +c.id+"MasterInputField");
                                       if(masterField[1]!=null)panel.masterAttributes.add(masterField);
                                   }
                               }
                   }
           }
           }  
       if(c.search==1) {
           if(c.data_layout==0)
           {
            dialog=(JDialog)r.getPartByName(""+c.id+"compTypeSearch");
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
        if(c.del_masive==1) {
             dialog=(JDialog)r.getPartByName(""+c.id+"compTypeMasiveDelete");
             if(dialog!=null)
             dialog.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) {
                    Center((JDialog)e.getSource());
                }
             });
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
    Object root=null;
    if(!f.parameters.isEmpty()){
             JDialog dialog=(JDialog)r.getPartByName(""+f.id+"formTypeParameters");
             if(dialog!=null)
             dialog.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) {
                    Center((JDialog)e.getSource());
                }
             });
        }
    for(int j=0;j<f.compType.length;j++){
        Application.CompType c=f.compType[j];
        Set panels=getSamePanels(f,c);
        Set switchOn=new HashSet();
        Set switchOff=new HashSet();
        switchOn.add(""+c.id);
        panels.remove(""+c.id);
        switchOff=panels;
        DBTable table=null;
        DBPanel panel=null;
        if(c.data_layout==1){
            table =(DBTable)r.getPartByName(""+f.compType[j].id+"Table");
            table.switchOff=getJPanels(switchOff);
            table.switchOn=getJPanels(switchOn);
            table.setTemplate(template);
            if(f.compType[j].superord!=-1){
                table.parent=(DBPanel)r.getPartByName(""+f.compType[j].superord+"DataPanel");
                if((DBPanel)table.parent==null)
                    table.parent=(DBTable)r.getPartByName(""+f.compType[j].superord+"Table");
            }
            else
                root=table;
        }
        else {
            panel =(DBPanel)r.getPartByName(""+f.compType[j].id+"DataPanel");
            panel.switchOff=getJPanels(switchOff);
            panel.switchOn=getJPanels(switchOn);
            if(f.compType[j].superord!=-1){
                panel.parent=(DBPanel)r.getPartByName(""+f.compType[j].superord+"DataPanel");
                if((DBPanel)panel.parent==null)
                    panel.parent=(DBTable)r.getPartByName(""+f.compType[j].superord+"Table");
            }
            else
                root=panel;
        }
        DBTable table1;
        DBPanel panel1;
        table1 =(DBTable)r.getPartByName(""+f.compType[j].superord+"Table");
        if(table1!=null){
            int k=table1.child.length;
            Object[] pom=new Object[k+1];
            for(int i=0;i<k;i++)
                pom[i]=table1.child[i];
            if(table!=null)
            {
                pom[k]=table;
                table1.child=pom;
            }
            else {
                pom[k]=panel;
                table1.child=pom;
            }
        }
        panel1 =(DBPanel)r.getPartByName(""+f.compType[j].superord+"DataPanel");
        if(panel1!=null){
            int k=panel1.child.length;
            Object[] pom=new Object[k+1];
            for(int i=0;i<k;i++)
                pom[i]=panel1.child[i];
            if(table!=null)
            {
                pom[k]=table;
                panel1.child=pom;
            }
            else {
                pom[k]=panel;
                panel1.child=pom;
            }
        }
    }
    
    
    if(root.getClass().toString().equals("class ui.DBTable"))((DBTable)root).setCaller(-1);
    else if(root.getClass().toString().equals("class ui.DBPanel"))((DBPanel)root).setCaller(-1);
}
public void addPanelFields(Application.Item it, Application.CompType c, DBPanel panel, int l){
    Object[] field=new  Object[2];
    Object[] searchField=new  Object[4];
    field[0]=(Application.AttType)it.elem;
    field[1] =r.getPartByName(""+((Application.AttType)it.elem).att_id +c.id+"InputField");
    panel.fieldAttributes.add(field);
    if(c.search==1){
        searchField[0]=(Application.AttType)it.elem;
        searchField[1] =r.getPartByName(""+((Application.AttType)it.elem).att_id +c.id+"SearchKey");
        searchField[2] =r.getPartByName(""+((Application.AttType)it.elem).att_id +c.id+"SearchComboCompare");
        searchField[3] =r.getPartByName(""+((Application.AttType)it.elem).att_id +c.id+"SearchComboOrder");
        panel.searchFieldAttributes.add(searchField);
    }
    if(((Application.AttType)it.elem).list_of_values.tf!=null){
        Application.ListOfValues lv=((Application.AttType)it.elem).list_of_values;
        lv.returnsAttribute=new Object[lv.returns.length][2];
        for(int k=0;k<lv.returns.length;k++){
            lv.returnsAttribute[k][0]=r.getPartByName(""+lv.returns[k][0] +lv.returns[k][2]+"InputField");
            lv.returnsAttribute[k][1]=r.getPartByName(""+lv.returns[k][1] +lv.returns[k][3]+"InputField");
        }
        DBPanel panel1= (DBPanel)r.getPartByName(""+lv.returns[0][3]+"DataPanel");
        if(panel1!=null)
        {
             panel1.LoVF.add(lv);
        }
        DBTable table1= (DBTable)r.getPartByName(""+lv.returns[0][3]+"Table");
        if(table1!=null)
        {
             table1.LoVF.add(lv);
        }
        panel1= (DBPanel)r.getPartByName(""+lv.returns[0][3]+"SearchDataPanel");
        if(panel1!=null)
        {
             panel1.LoV.add(lv);
        }
        table1= (DBTable)r.getPartByName(""+lv.returns[0][3]+"Table");
         if(table1!=null)
          table1.LoV.add(lv);
    }
}
public void addTableFields(Application.Item it, Application.CompType c, DBTable table, int l){
    Object[] field=new  Object[2];
    Object[] searchField=new  Object[4];
    field[0]=(Application.AttType)it.elem;
    field[1] =r.getPartByName(""+((Application.AttType)it.elem).att_id +c.id+"InputField");
    table.fieldAttributes.add(field);
    if(c.itemGroup[l].overflow==1&&template.tablePanel.overflow==1)
    {
        Object[] overflowField=new  Object[2];
        overflowField[0]=(Application.AttType)it.elem;
        overflowField[1]=r.getPartByName(""+((Application.AttType)it.elem).att_id +c.id+"overflowInputField");
        table.overflowFieldAttributes.add(overflowField);  
    }
    //                       if(c.itemGroup[l].overflow==1&&template.tablePanel.overflow==1)
    //                            table.overflowFieldAttributes.add(field);
    if(c.search==1){
        searchField[0]=(Application.AttType)it.elem;
        searchField[1] =r.getPartByName(""+((Application.AttType)it.elem).att_id +c.id+"SearchKey");
        searchField[2] =r.getPartByName(""+((Application.AttType)it.elem).att_id +c.id+"SearchComboCompare");
        searchField[3] =r.getPartByName(""+((Application.AttType)it.elem).att_id +c.id+"SearchComboOrder");
        table.searchFieldAttributes.add(searchField);
    }
    if(((Application.AttType)it.elem).list_of_values.tf!=null){
        Application.ListOfValues lv=((Application.AttType)it.elem).list_of_values;
        lv.returnsAttribute=new Object[lv.returns.length][2];
        for(int k=0;k<lv.returns.length;k++){
            lv.returnsAttribute[k][0]=r.getPartByName(""+lv.returns[k][0] +lv.returns[k][2]+"InputField");
            lv.returnsAttribute[k][1]=r.getPartByName(""+lv.returns[k][1] +lv.returns[k][3]+"InputField");
        }
        DBTable table1= (DBTable)r.getPartByName(""+lv.returns[0][3]+"Table");
        if(table1!=null)
        {
             table1.LoVF.add(lv);
        }
        DBPanel panel1= (DBPanel)r.getPartByName(""+lv.returns[0][3]+"DataPanel");
        if(panel1!=null)
        {
             panel1.LoVF.add(lv);
        }                           
        panel1= (DBPanel)r.getPartByName(""+lv.returns[0][3]+"SearchDataPanel");
        if(panel1!=null)
        {
             panel1.LoV.add(lv);
        }
        table1= (DBTable)r.getPartByName(""+lv.returns[0][3]+"Table");
        if(table1!=null)
          table1.LoV.add(lv);
    }
}
public Set getJPanels(Set cmps){
    Set pom=new HashSet();
    Iterator it=cmps.iterator();
    while(it.hasNext()){
        String id=it.next().toString();
        JPanel panel=(JPanel)r.getPartByName(id+"NavigationPanel");
        if(panel!=null)pom.add(panel);
        panel=(JPanel)r.getPartByName(id+"ButtonPanel");
        if(panel!=null)pom.add(panel);
    }
    return pom;
}
public Set getSamePanels(Application.FormType f, Application.CompType c){
    Set Panel=new HashSet();
    for(int i=0;i<f.compType.length; i++) {
        Set pom=new HashSet();
        pom= getPanel(f,f.compType[i], new HashSet());
        if(pom.size()>Panel.size() && pom.contains(""+c.id)) {
            Panel=new HashSet();
            Panel.addAll(pom);
        }
    }
    return Panel;
}
public Set getDesc(Application.FormType f, Application.CompType c){
    Set ret=new HashSet();
    for(int i=0;i<f.compType.length; i++){
        if(f.compType[i].superord==c.id){
            if(f.compType[i].layout==1){
                ret.add(f.compType[i]);
                ret.addAll(getDesc(f, f.compType[i]));
            }
        }
    }
    return ret;
}
public Set getPanel(Application.FormType f, Application.CompType c, Set Panel){
    Panel.add(""+c.id);
    Set desc=getDesc(f,c);
    Iterator it=desc.iterator();
    while(it.hasNext()){
        Application.CompType comp=(Application.CompType)it.next();
        if(comp.layout==1)getPanel(f,comp,Panel);
    }
    return Panel;
}
public void setApplicationProperties(Renderer r, Application app) {
    JDialog dial=(JDialog)r.getPartByName("About");
    if(dial!=null)
    dial.addPropertyChangeListener(new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent e) {
            Center((JDialog)e.getSource());
        }
    });
    for (int i=0; i<app.application.formType.length;i++)
    {
        setFormApp(app.application.formType[i], app.application);
    } 
    for (int i=0; i<app.subApplication.length;i++)
    {
        for (int j=0; j<app.subApplication[i].formType.length;j++)
        {
            setFormApp(app.subApplication[i].formType[j], app.application);
        } 
    } 
    for (int i=0; i<app.application.formType.length;i++)
    {
//        setFormLOV(app.application.formType[i], app.application);
    } 
    for (int i=0; i<app.subApplication.length;i++)
    {
        for (int j=0; j<app.subApplication[i].formType.length;j++)
        {
            setFormLOV(app.subApplication[i].formType[j], app.application);
        } 
    } 
    setFormAppParameter(app.application);
    for (int i=0; i<app.subApplication.length;i++)
    {
        setFormAppParameter(app.application); 
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
      app=appl;
      mApplication=appl.application;
      application=appl.application;
      String fileName = "Example.uiml";
      String red="";
      FileOutputStream outf = new FileOutputStream(fileName);
      DataOutputStream out = new DataOutputStream(outf);
      red="<?xml version=\"1.0\" encoding=\"ISO-8859-2\"?>\n";
      out.writeBytes(red);
      //red="<!DOCTYPE uiml PUBLIC \"-//Harmonia//DTD UIML 2.0 Draft//EN\" \"UIML2_0g.dtd\">\n";
      red="<uiml>\n" +
      "<interface id=\"IISUIModeler\">\n" +
      "<structure>\n"+
      generateFormTypeFrame()+
      "</structure>\n" +
      openStyle()+
      getProperty("","JFrame","defaultCloseOperation","2") +
      getProperty("","JFrame","font", getFont(template.labelFont.font)) +
    /*  getProperty("","JFrame","layout","null") +
      getProperty("","JFrame","font",getFont(template.labelFont.font)) +
      getProperty("","JFrame","background",getColor(template.formPanel.bg)) +
      getProperty("","JFrame","foreground",getColor(template.formPanel.fg)) +*/
      getProperty("","JInternalFrame","layout","null") +
      getProperty("","JInternalFrame","font",getFont(template.labelFont.font)) +
      getProperty("","JInternalFrame","background",getColor(template.formPanel.bg)) +
      getProperty("","JInternalFrame","foreground",getColor(template.formPanel.fg)) +
      getProperty("","JDialog","layout","null") +
      getProperty("","JDialog","font",getFont(template.labelFont.font)) +
      getProperty("","JDialog","background",getColor(template.formPanel.bg)) +
      getProperty("","JDialog","foreground",getColor(template.formPanel.fg)) +
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
    //  getProperty("","JTable","selectionBackground",getColor(template.tablePanel.bg)) +
    //  getProperty("","JTable","selectionForeground",getColor(template.tablePanel.fg)) + 
      getProperty("","JTable","font",getFont(template.inputFont.font)) +
      getProperty("","JTable","intercellSpacing","0,0") +
      getProperty("","JTable","rowHeight",""+ template.tablePanel.row_height) +
      getProperty("","JTable","border",getBorder(template.tablePanel.border_style, template.tablePanel.border, template.tablePanel.input_fg, "")) +
      getProperty("","DataTable","foreground",getColor(template.tablePanel.input_fg)) + 
    //getProperty("","DataTable","selectionBackground",getColor((new JTable()).getSelectionBackground())) +
    //  getProperty("","DataTable","selectionForeground",getColor((new JTable()).getSelectionForeground())) + 
      getProperty("","DataTable","font",getFont(template.inputFont.font)) +
      getProperty("","DataTable","intercellSpacing","0,0") +
      getProperty("","DataTable","rowHeight",""+ template.tablePanel.row_height) +
      getProperty("","DataTable","border",getBorder(template.tablePanel.border_style, template.tablePanel.border, template.tablePanel.input_fg, "")) +
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
    //  "<d-class id=\"DBFrame\" used-in-tag=\"part\" maps-type=\"class\" maps-to=\"ui.DBFrame\" how=\"replace\" export=\"optional\">\n" +
    //  "<d-property maps-type=\"\" return-type=\"\" id=\"addToContainer\" maps-to=\"add\"/>\n" +
    //  "<d-property id=\"defaultCloseOperation\" maps-type=\"setMethod\" maps-to=\"setDefaultCloseOperation\">\n" + 
    //  "<d-param type=\"int\"/>\n" + 
    //  "</d-property>" +
    //  "<d-property id=\"title\" maps-type=\"setMethod\" maps-to=\"setTitle\">\n" + 
    //  "<d-param type=\"java.lang.String\"/>\n" + 
    //  "</d-property>" +
    //  "<d-property id=\"iconImage\" maps-type=\"setMethod\" maps-to=\"setIconImage\">\n" + 
    //  "<d-param type=\"java.awt.Image\"/>\n" + 
    //  "</d-property>\n" + 
    //  "<d-property id=\"resizable\" maps-type=\"setMethod\" maps-to=\"setResizable\">\n" + 
    //  "<d-param type=\"boolean\"/>\n" + 
    //  "</d-property>" +
    //  "<d-property id=\"visible\" maps-type=\"setMethod\" maps-to=\"setVisible\">\n" + 
    //  "<d-param type=\"boolean\"/>\n" + 
    // "</d-property>" +
    //  "<d-property id=\"bounds\" maps-type=\"setMethod\" maps-to=\"setBounds\">\n" + 
    //  "<d-param type=\"java.awt.Rectangle\"/>\n" + 
    //  "</d-property>" +
    //  "</d-class>\n" +
        "<d-class id=\"DataTable\" used-in-tag=\"part\" maps-type=\"class\" maps-to=\"ui.DBTable\" how=\"replace\" export=\"optional\">\n" +
        "<d-property id=\"preferredScrollableViewportSize\" maps-type=\"setMethod\" maps-to=\"setPreferredScrollableViewportSize\">" +
        "<d-param type=\"java.awt.Dimension\"/>" +
        "</d-property>" +
        "<d-property id=\"visible\" maps-type=\"setMethod\" maps-to=\"setVisible\">\n" + 
        "<d-param type=\"boolean\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"LoV\" maps-type=\"setMethod\" maps-to=\"setL0Vs\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" +
        "<d-property id=\"caller\" maps-type=\"setMethod\" maps-to=\"setCaller\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" +
        "<d-property id=\"setSearch\" maps-type=\"setMethod\" maps-to=\"setSearchData\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" +
        "<d-property id=\"setSearchChoice\" maps-type=\"setMethod\" maps-to=\"setSearchChoiceData\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" +
        "<d-property id=\"setMasiveDelete\" maps-type=\"setMethod\" maps-to=\"setMasiveDelete\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" +
        "<d-property id=\"reset\" maps-type=\"setMethod\" maps-to=\"resetData\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" + 
        "<d-property id=\"resetL\" maps-type=\"setMethod\" maps-to=\"resData\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" + 
        "<d-property id=\"setAction\" maps-type=\"setMethod\" maps-to=\"setAction\">\n" + 
        "<d-param type=\"java.lang.String\"/>\n" + 
        "</d-property>" + 
        "<d-property id=\"url\" maps-type=\"setMethod\" maps-to=\"setUrl\">\n" + 
        "<d-param type=\"java.lang.String\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"comp\" maps-type=\"setMethod\" maps-to=\"setComp\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" +
        "<d-property id=\"pr\" maps-type=\"setMethod\" maps-to=\"setPR\">\n" +  
        "<d-param type=\"int\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"as\" maps-type=\"setMethod\" maps-to=\"setAS\">\n" +  
        "<d-param type=\"int\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"username\" maps-type=\"setMethod\" maps-to=\"setUsername\">\n" + 
        "<d-param type=\"java.lang.String\"/>\n" + 
        "</d-property>" + 
        "<d-property id=\"password\" maps-type=\"setMethod\" maps-to=\"setPassword\">\n" + 
        "<d-param type=\"java.lang.String\"/>\n" + 
        "</d-property>" + 
        "<d-property id=\"border\" maps-type=\"setMethod\" maps-to=\"setBorder\" post-child=\"false\" export=\"optional\">\n" + 
        "<d-param type=\"javax.swing.border.Border\" />\n" + 
        "</d-property>" +
        "<d-property return-type=\"\" id=\"selectionBackground\" maps-type=\"setMethod\" maps-to=\"setSelectionBackground\">\n" +
        "<d-param id=\"\" type=\"java.awt.Color\"/></d-property>\n" +
        "<d-property return-type=\"\" id=\"selectionForeground\" maps-type=\"setMethod\" maps-to=\"setSelectionForeground\">\n" +
        "<d-param id=\"\" type=\"java.awt.Color\"/></d-property>\n" +
        "<d-property id=\"rowHeight\" maps-type=\"setMethod\" maps-to=\"setRowHeight\">\n" + 
        "<d-param type=\"int\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"intercellSpacing\" maps-type=\"setMethod\" maps-to=\"setIntercellSpacing\">\n" + 
        "<d-param type=\"java.awt.Dimension\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"font\" maps-type=\"setMethod\" maps-to=\"setFont\">\n" + 
        "<d-param type=\"java.awt.Font\"/>\n" + 
        "</d-property>" +
        "<d-property return-type=\"\" id=\"background\" maps-type=\"setMethod\" maps-to=\"setBackground\">\n" +
        "<d-param id=\"\" type=\"java.awt.Color\"/></d-property>\n" +
        "<d-property return-type=\"\" id=\"foreground\" maps-type=\"setMethod\" maps-to=\"setForeground\">\n" +
        "<d-param id=\"\" type=\"java.awt.Color\"/></d-property>\n" +
        "<d-property return-type=\"\" id=\"gridColor\" maps-type=\"setMethod\" maps-to=\"setGridColor\">\n" +
        "<d-param id=\"\" type=\"java.awt.Color\"/></d-property>\n" +
        "<d-property id=\"autoCreateColumnsFromModel\" maps-type=\"setMethod\" maps-to=\"setAutoCreateColumnsFromModel\">" +
        "<d-param type=\"boolean\"/> </d-property>" +
        "<d-property id=\"autoResizeMode\" maps-type=\"setMethod\" maps-to=\"setAutoResizeMode\">\n" + 
        "<d-param type=\"int\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"selectionMode\" maps-type=\"setMethod\" maps-to=\"setSelectionMode\">\n" + 
        "<d-param type=\"int\"/>\n" + 
        "</d-property>" +
        "</d-class>\n" +
        "<d-class id=\"DataPanel\" used-in-tag=\"part\" maps-type=\"class\" maps-to=\"ui.DBPanel\" how=\"replace\" export=\"optional\">\n" +
        "<d-property id=\"addToContainer\" maps-type=\"method\" maps-to=\"add\"/>"+
        "<d-property id=\"visible\" maps-type=\"setMethod\" maps-to=\"setVisible\">\n" + 
        "<d-param type=\"boolean\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"opaque\" maps-type=\"setMethod\" maps-to=\"setOpaque\">\n" + 
        "<d-param type=\"boolean\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"LoV\" maps-type=\"setMethod\" maps-to=\"setL0Vs\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" +
        "<d-property id=\"caller\" maps-type=\"setMethod\" maps-to=\"setCaller\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" +
        "<d-property id=\"setSearch\" maps-type=\"setMethod\" maps-to=\"setSearchData\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" +
        "<d-property id=\"setSearchChoice\" maps-type=\"setMethod\" maps-to=\"setSearchChoiceData\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" +
        "<d-property id=\"setMasiveDelete\" maps-type=\"setMethod\" maps-to=\"setMasiveDelete\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" +
        "<d-property id=\"reset\" maps-type=\"setMethod\" maps-to=\"resetData\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" +
        "<d-property id=\"resetL\" maps-type=\"setMethod\" maps-to=\"resData\">\n" +  
        "<d-param type=\"int\"/>\n" +  
        "</d-property>" + 
        "<d-property id=\"setAction\" maps-type=\"setMethod\" maps-to=\"setAction\">\n" + 
        "<d-param type=\"java.lang.String\"/>\n" + 
        "</d-property>" + 
        "<d-property id=\"url\" maps-type=\"setMethod\" maps-to=\"setUrl\">\n" + 
        "<d-param type=\"java.lang.String\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"comp\" maps-type=\"setMethod\" maps-to=\"setComp\">\n" +  
        "<d-param type=\"int\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"pr\" maps-type=\"setMethod\" maps-to=\"setPR\">\n" +  
        "<d-param type=\"int\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"as\" maps-type=\"setMethod\" maps-to=\"setAS\">\n" +  
        "<d-param type=\"int\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"username\" maps-type=\"setMethod\" maps-to=\"setUsername\">\n" + 
        "<d-param type=\"java.lang.String\"/>\n" + 
        "</d-property>" + 
        "<d-property id=\"password\" maps-type=\"setMethod\" maps-to=\"setPassword\">\n" + 
        "<d-param type=\"java.lang.String\"/>\n" + 
        "</d-property>" + 
        "<d-property return-type=\"\" id=\"background\" maps-type=\"setMethod\" maps-to=\"setBackground\">\n" +
        "<d-param id=\"\" type=\"java.awt.Color\"/></d-property>\n" +
        "<d-property return-type=\"\" id=\"foreground\" maps-type=\"setMethod\" maps-to=\"setForeground\">\n" +
        "<d-param id=\"\" type=\"java.awt.Color\"/></d-property>\n" +
        "<d-property return-type=\"\" id=\"font\" maps-type=\"setMethod\" maps-to=\"setFont\">\n" +
        "<d-param id=\"\" type=\"java.awt.Font\"/></d-property>\n" +
        "<d-property return-type=\"\" id=\"border\" maps-type=\"setMethod\" maps-to=\"setBorder\">\n" +
        "<d-param id=\"\" type=\"javax.swing.border.Border\"/></d-property>\n" +
        "<d-property id=\"preferredSize\" maps-type=\"setMethod\" maps-to=\"setPreferredSize\">" +
        "<d-param type=\"java.awt.Dimension\"/></d-property>\n" +
        "<d-property id=\"bounds\" maps-type=\"setMethod\" maps-to=\"setBounds\">\n" + 
        "<d-param type=\"java.awt.Rectangle\"/>\n" + 
        "</d-property>" +
        "<d-property id=\"layout\" maps-type=\"setMethod\" maps-to=\"setLayout\">" +
        "<d-param type=\"java.awt.LayoutManager\"/>" +
        "</d-property>" +
        "</d-class>\n" +
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
public String getCall(String name, String param){
    return
        "<call name=\""+name+"\">" +
        "<param>"+param+"</param>" +
        "</call>";
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
/*public void createMain (DataOutputStream out)
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
   //     generateMenuForm()+
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
  }*/
    public String generateFormTypeFrame() { 
        String r="";
        for (int i=0; i<application.formType.length;i++)
        {
          Application.FormType f=application.formType[i];
          if(f.id==application.entry)
              r=r+ generateMainFrame(f); 
        }
        return r;
    }
  /*public String  generateSubApplications(){
      String r="";
      for (int i=0; i<app.subApplication.length;i++)
      {
          for (int k=0; k<app.subApplication[i].formType.length;k++)
          {
            Application.FormType f=app.subApplication[i].formType[k];
            if(f.compType.length>0)
                r=r+ generateCompType(f, f.compType[0]); 
          }
      }
      return r;
  }*/
   public String  generateSubApplicationsInternalFrames(){
         String r="";
         for (int i=0; i<app.subApplication.length;i++)
         {
            r=   r+
                generateFormTypes(app.subApplication[i]);
         }
         return r;
     } 
   public String  generateSubApplications(){
         String r="";
         for (int i=0; i<app.subApplication.length;i++)
         {
            r=   r+
                generateSubFormTypes(app.subApplication[i])+
                generateInputFormTypes(app.subApplication[i])+
                generateSearchFormTypes(app.subApplication[i])+
                generateMasiveDeleteFormTypes(app.subApplication[i])+
                generateParametersFormTypes(app.subApplication[i]);
         }
         return r;
     }
 
  
  public String generateFormTypes(Application.ApplicationSystem application) { 
      String r="";
      for (int i=0; i<application.formType.length;i++)
      {
        Application.FormType f=application.formType[i];
        if(f.compType.length>0){
            r=r+ generateCompType(f, f.compType[0]); 
        }
        else if(f.type==2){
            r=r+ generateMenuFormType(f); 
        } 
      }
      return r;
  }
 
    public String generateSubFormTypes(Application.ApplicationSystem application) { 
    String r="";
    for (int i=0; i<application.formType.length;i++)
    {
      Application.FormType f=application.formType[i];
      for(int j=0;j<f.compType.length;j++)
        if(f.compType[j].superord!=-1)
        {
            //for(int k=0;k<f.compType.length;k++)
                //if(f.compType[k].id==f.compType[j].superord && f.compType[k].layout==0)
            if(f.compType[j].layout==0)
                r=r+generateCompType(f,f.compType[j]); 
        }
    }
    return r;
    }
    public String generateInputFormTypes(Application.ApplicationSystem application) { 
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
    public String generateSearchFormTypes(Application.ApplicationSystem application) { 
    String r="";
    for (int i=0; i<application.formType.length;i++)
    {
      Application.FormType f=application.formType[i];
      for(int j=0;j<f.compType.length;j++){
        if(f.compType[j].search==1)
        {
            r=r+generateCompTypeSearch(f,f.compType[j]); 
        }        
      }
        
    }
    return r;
    } 
    public String generateMasiveDeleteFormTypes(Application.ApplicationSystem application) { 
    String r="";
    for (int i=0; i<application.formType.length;i++)
    {
      Application.FormType f=application.formType[i];
      for(int j=0;j<f.compType.length;j++){
        if(f.compType[j].del_masive==1)
        {
            r=r+generateCompTypeMasiveDelete(f,f.compType[j]); 
        }        
      }
        
    }
    return r;
    } 
    public String generateParametersFormTypes(Application.ApplicationSystem application) { 
    String r="";
    for (int i=0; i<application.formType.length;i++)
    {
      Application.FormType f=application.formType[i];
      for(int j=0;j<f.compType.length;j++){
        if(f.parameters.size()>=0)
        {
            r=r+generateFormTypeParameters(f); 
        }        
      }
        
    }
    return r;
    } 
    /*public String generateCompTypePanel(Application.FormType f, Application.CompType cmp, int width, int height){
        String r1="";
        int h=0;
        int w=0;
        int hs=0;
        boolean ok=false;
        Application.CompType cmp1=null;
        int bheight=(height+10-getButtonPanelDimension(cmp,f).height - getMenuPanelDimension(cmp,f).height);
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
                    hs=template.itemPanel.spacing;
                    r1=r1+
                   openPart(cmp.id + "SubCompTypePanel","","JPanel") +
                    openStyle() +
                    getProperty("","","bounds", (0 +(width-w)/2) +","+ (height+ template.itemPanel.spacing -20 - getMenuPanelDimension(f.compType[i],f).height - getButtonPanelDimension(f.compType[i],f).height) +","+ w +","+ h) +
                    getProperty("","","layout", "null") +
                    getProperty("","","opaque", "false") +
                    closeStyle() +
                    generateCompTypePanel(f, f.compType[i], w, h)+
                    closePart();  
                    
                }
            }

        String r="";
        int parameterWidth=0;
        if(!f.parameters.isEmpty() && cmp.superord==-1){
            if(template.buttonPanel.style==1)
            {
            r="\n" +
                openPart(cmp.id+"Parameters","","JPanel") +
                openStyle() +
                getProperty("","","bounds", (width -40)+","+0+",40,35") +
                getProperty("","","opaque","false") +
                closeStyle()+
                openPart(cmp.id + "ParametersButton","","JButton") +
                openStyle() +
                getProperty("","","preferredSize", "30,30") +
                getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/item.gif") +
                getProperty("","","toolTipText", "Parameters") +
                closeStyle() +
                getBehavior(getProperty(f.id+"formTypeParameters","","visible", "true")) +
                closePart()+
                closePart();
                parameterWidth=40;
            }
            else
            {
                r="\n" +
                openPart(cmp.id+"Parameters","","JPanel") +
                openStyle() +
                getProperty("","","bounds", (width -85)+","+0+",85,35") +
                getProperty("","","opaque","false") +
                closeStyle()+
                openPart(cmp.id + "ParametersButton","","JButton") +
                openStyle() +
                getProperty("","","text", "Parameters") +
                closeStyle() +
                getBehavior(getProperty(f.id+"formTypeParameters","","visible", "true")) +
                closePart()+
                closePart();
                parameterWidth=85;
            }
        }
        if(cmp1==null)cmp1=cmp;
        r=r+
        ((template.formPanel.title>0)?openPart(cmp.id + "Title","","JLabel") +
        openStyle() +
        getProperty("","","horizontalAlignment", ((template.formPanel.title_align==0)?"LEFT":((template.formPanel.title_align==1)?"CENTER":"RIGHT"))) +
        getProperty("","","verticalAlignment", "BOTTOM") +
        getProperty("","","bounds", "20,10," +(width -40-parameterWidth)+","+getTitHeight()) +
        getProperty("","","font", getFont(template.formPanel.titleFont.font)) +
        getProperty("","","text", cmp.name) +
        closeStyle() +
        closePart():"")+
        generateCompTypeToolbar(f,cmp) +
        generateCompTypeContent(cmp,f, template.itemPanel.type,cmp.data_layout,0,1) +
        (r1.equals("")?generateButtons(f,cmp1,(width+15-640)/2,bheight, "true"):"")+
        r1;
        return r;
    }*/
     public String generateCompTypePanel(Application.FormType f, Application.CompType cmp, int width, int height, int _x, int _y){
         Application.CompType cmp1=null;
         int bheight=(height-getButtonPanelDimension(cmp,f).height - getMenuPanelDimension(cmp,f).height);
         String r="";
         int parameterWidth=0;
         if(!f.parameters.isEmpty() && cmp.superord==-1){
             if(template.buttonPanel.style==1)
             {
             r="\n" +
                 openPart(cmp.id+"Parameters","","JPanel") +
                 openStyle() +
                 getProperty("","","bounds", (width -40-template.itemPanel.spacing)+","+0+",40,35") +
                 getProperty("","","opaque","false") +
                 closeStyle()+
                 openPart(cmp.id + "ParametersButton","","JButton") +
                 openStyle() +
                 getProperty("","","preferredSize", "30,30") +
                 getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/item.gif") +
                 getProperty("","","toolTipText", "Parameters") +
                 closeStyle() +
                 getBehavior(getProperty(f.id+"formTypeParameters","","visible", "true")) +
                 closePart()+
                 closePart();
                 parameterWidth=40;
             }
             else
             {
                 r="\n" +
                 openPart(cmp.id+"Parameters","","JPanel") +
                 openStyle() +
                 getProperty("","","bounds", (width -85-template.itemPanel.spacing)+","+0+",85,35") +
                 getProperty("","","opaque","false") +
                 closeStyle()+
                 openPart(cmp.id + "ParametersButton","","JButton") +
                 openStyle() +
                 getProperty("","","text", "Parameters") +
                 closeStyle() +
                 getBehavior(getProperty(f.id+"formTypeParameters","","visible", "true")) +
                 closePart()+
                 closePart();
                 parameterWidth=85;
             }
         }
         Vector[] cmpArr=getCompArray(cmp,f);
         Dimension dimr=getCompTypeDimension(cmp,f,template.itemPanel.type,cmp.data_layout,1);
         int add=0;
         Dimension d=generateCompTypeMasterDetailDimension(f,cmp);
         int wMaster=(int)d.getWidth();
         Dimension dimc=getCompTypeDimension(cmp,f,template.itemPanel.type, cmp.data_layout,1);
         for(int i=0;i<cmpArr[0].size();i++){
             
         }
         int widthc=Math.max(Math.max(Math.max((int)dimc.getWidth(),getButtonPanelDimension(cmp,f).width),wMaster),540)-template.itemPanel.spacing;;
         if(dimr.width<widthc&&cmpArr[0].size()>0)
            add=Math.round((widthc-dimr.width)/cmpArr[0].size());
         Dimension dim=new Dimension();
         dim.width=template.itemPanel.spacing;
         dim.height=0;
         Iterator cit=cmpArr[0].iterator();
         int[][] dimxy=new int[cmpArr[0].size()][4];
         int x=_x+(_x==0?template.itemPanel.spacing:0), y=_y+(_y==0?template.itemPanel.spacing:0), j=0;
         int wadd=0;
         while(cit.hasNext()){
             Object o=cit.next();
             if(o.getClass().toString().equals("class ui.Application$CompType"))
             {  
                 Application.CompType c=(Application.CompType)o;
                 Dimension d1=generateCompTypeMasterDetailDimension(f,c);
                 int wm=(int)d1.getWidth()+2*template.itemPanel.spacing;
                 Dimension dim1=getCompContentDimension(c,f,template.itemPanel.type,c.data_layout,1);
                 dim1.height=dim1.height+getToolbarDimension(c,f).height+((template.formPanel.title>0)?getTitleHeight():0)+((c.layout==0&&c.superord!=-1)?generateCompTypeMasterDetailDimension(f,c).height:0);
                 dim1.width=Math.max(dim1.width,wm);
                 dim.width=dim.width+dim1.width+template.itemPanel.spacing;
                 dim.height=Math.max(dim1.height+template.itemPanel.spacing,dim.height);
                 dimxy[j][0]=x;
                 dimxy[j][1]=y;
                 dimxy[j][2]=dim1.width+template.itemPanel.spacing;//+add;
                 dimxy[j][3]=dim1.height+template.itemPanel.spacing;
                 x=x+dimxy[j][2];
                 wadd=wadd+dimxy[j][2];
                 j++;
             }
             else {
                 Vector[] cmpArrm=(Vector[])o;
                 Application.CompType c=(Application.CompType)cmpArrm[0].iterator().next();
                 Dimension dim1=getCompTypeDimension(c,f,template.itemPanel.type,c.data_layout,1);
                 dim.width=dim.width+dim1.width+template.itemPanel.spacing;
                 //dim.height=Math.max(dim1.height+template.itemPanel.spacing,dim.height);      
                 dim.height=Math.max(dim1.height,dim.height);
                 dimxy[j][0]=x;
                 dimxy[j][1]=y;
                 dimxy[j][2]=dim1.width+template.itemPanel.spacing;//+add;
                 dimxy[j][3]=dim1.height+template.itemPanel.spacing;
                 x=x+dimxy[j][2];
                 wadd=wadd+dimxy[j][2];
                 j++;
             }
         }
         if(wadd<widthc&&cmpArr[0].size()>0)
            add=Math.round((widthc-wadd)/cmpArr[0].size());
         else add=0;
         //dim.height=dim.height+template.itemPanel.spacing;
         j=0;
         cit=cmpArr[0].iterator();
         while(cit.hasNext()){
             Object o=cit.next();
             if(o.getClass().toString().equals("class ui.Application$CompType"))
             {  
                 Application.CompType c=(Application.CompType)o;
                 r=r+generateCompTypeP(f, c, dimxy[j][0], dimxy[j][1], dimxy[j][2]+add, dim.height, parameterWidth);
                 j++;
                 if(j<dimxy.length)dimxy[j][0]=dimxy[j][0]+add;
             }
             else {
                 Vector[] cmpArrm=(Vector[])o;
                 Application.CompType c=(Application.CompType)cmpArrm[0].iterator().next();
                 r=r+generateCompTypePanel(f, c, dimxy[j][2]+add, dim.height,dimxy[j][0], dimxy[j][1]);
                 j++;
                 if(j<dimxy.length)dimxy[j][0]=dimxy[j][0]+add;
             }
         }
         if(dimr.width<dim.width&&cmpArr[1].size()>0)
            add=Math.round((dim.width-dimr.width)/cmpArr[1].size());
         cit=cmpArr[1].iterator();
         Dimension dimm=new Dimension();
         dimm.width=0;
         dimm.height=0;
         if(cmpArr[1].size()>0){
         dimm.width=template.itemPanel.spacing;
         dimm.height=template.itemPanel.spacing;
         dimxy=new int[cmpArr[1].size()][4];
         x=_x+(_x==0?template.itemPanel.spacing:0); y=_y+dim.height; j=0;
         wadd=0;
         while(cit.hasNext()){
             Object o=cit.next();
             if(o.getClass().toString().equals("class ui.Application$CompType"))
             {  
                 Application.CompType c=(Application.CompType)o;
                 Dimension d1=generateCompTypeMasterDetailDimension(f,c);
                 int wm=(int)d1.getWidth()+2*template.itemPanel.spacing;
                 Dimension dimm1=getCompContentDimension(c,f,template.itemPanel.type,c.data_layout,1);
                 dimm1.height=dimm1.height+getToolbarDimension(c,f).height+((template.formPanel.title>0)?getTitleHeight():0);
                 dimm1.width=Math.max(dimm1.width,wm);
                 dimm.width=dimm.width+dimm1.width+template.itemPanel.spacing;
                 dimm.height=Math.max(dimm1.height+template.itemPanel.spacing,dimm.height);
                 dimxy[j][0]=x;
                 dimxy[j][1]=y;
                 dimxy[j][2]=dimm1.width+template.itemPanel.spacing;
                 dimxy[j][3]=dimm1.height+template.itemPanel.spacing;
                 x=x+dimxy[j][2];
                 wadd=wadd+dimxy[j][2];
                 j++;
             }
             else {
                 Vector[] cmpArrm=(Vector[])o;
                 Application.CompType c=(Application.CompType)cmpArrm[0].iterator().next();
                 Dimension dimm1=getCompContentDimension(c,f,template.itemPanel.type,c.data_layout,1);
                 dimm1.height=dimm1.height+getToolbarDimension(c,f).height+((template.formPanel.title>0)?getTitleHeight():0);
                 dimm.width=dimm.width+dimm1.width+template.itemPanel.spacing;
                 //dimm.height=Math.max(dimm1.height+template.itemPanel.spacing,dimm.height);
                 dimm.height=Math.max(dimm1.height,dimm.height);
                 dimxy[j][0]=x;
                 dimxy[j][1]=y;
                 dimxy[j][2]=dimm1.width+template.itemPanel.spacing;
                 dimxy[j][3]=dimm1.height+template.itemPanel.spacing;
                 x=x+dimxy[j][2];
                 wadd=wadd+dimxy[j][2];
                 j++;
             }
         }
            if(wadd<widthc&&cmpArr[1].size()>0)
               add=Math.round((widthc-wadd)/cmpArr[1].size());
            else add=0;
         //add=0;
         //if(dimm.width<dim.width&&cmpArr[1].size()>0)
           //add=Math.round((Math.max(dim.width,cmp.del_masive==0?540:600)-dimm.width)/cmpArr[1].size());
         dim.width=Math.max(dim.width,dimm.width);
         dim.height=dim.height+dimm.height;
         //dimm.height=dimm.height+template.itemPanel.spacing;
        }
         j=0;
         cit=cmpArr[1].iterator();
         while(cit.hasNext()){
             Object o=cit.next();
             if(o.getClass().toString().equals("class ui.Application$CompType"))
             {  
                 Application.CompType c=(Application.CompType)o;
                r=r+generateCompTypeP(f, c, dimxy[j][0], dimxy[j][1], dimxy[j][2]+add, dimm.height, parameterWidth);
                j++;
                if(j<dimxy.length)dimxy[j][0]=dimxy[j][0]+add;
             }
             else {
                 Vector[] cmpArrm=(Vector[])o;
                 Application.CompType c=(Application.CompType)cmpArrm[0].iterator().next();
                 r=r+generateCompTypePanel(f, c,  dimxy[j][2]+add, dimm.height, dimxy[j][0], dimxy[j][1] );
                 j++;
                 if(j<dimxy.length)dimxy[j][0]=dimxy[j][0]+add; 
             }
         } 
         return r;
     }
    public String generateCmpButtons(Application.FormType f, Application.CompType cmp, Vector[] cmpArr, Dimension dimr){
        String r="";
        Iterator cit=cmpArr[0].iterator();
        while(cit.hasNext()){
            Object o=cit.next();
            if(o.getClass().toString().equals("class ui.Application$CompType"))
            {  
                Application.CompType c=(Application.CompType)o;
                r=r+generateButtons(f,c,Math.max(0,(dimr.width-getButtonPanelDimension(cmp,f).width)/2), dimr.height , (c.superord==-1||c.layout==0?"true":"false"));
            }
            else {
                Vector[] cmpArr1=(Vector[])o;
                r=r+generateCmpButtons(f, cmp,cmpArr1, dimr);  
            }
        }
        cit=cmpArr[1].iterator();
        while(cit.hasNext()){
            Object o=cit.next();
            if(o.getClass().toString().equals("class ui.Application$CompType"))
            {  
                Application.CompType c=(Application.CompType)o;
                r=r+generateButtons(f,c,Math.max(0,(dimr.width-getButtonPanelDimension(cmp,f).width)/2), dimr.height , "false");
            }
            else {
                Vector[] cmpArr1=(Vector[])o;
                r=r+generateCmpButtons(f, cmp,cmpArr1, dimr);    
            }
        }
        return r;
    }
    public String generateCmpButtons(Application.FormType f, Dimension dimr){
        String r="";
        r=r+generateButtons(f,Math.max(0,(dimr.width-getButtonPanelDimension(f).width)/2), dimr.height);
        return r;
    }
    public String generateCompTypeP(Application.FormType f, Application.CompType cmp, int x, int y, int width, int height, int parameterWidth){
        String r= openPart( cmp.id + "compTypePanel","","JPanel") +
        openStyle() +
        getProperty("","","bounds", x +","+ y +","+ width +","+ height) +
        getProperty("","","opaque", "false") +
        getProperty("","","layout", "null") +
        closeStyle() +
        ((template.formPanel.title>0)?openPart(cmp.id + "Title","","JLabel") +
        openStyle() +
        getProperty("","","horizontalAlignment", ((template.formPanel.title_align==0)?"LEFT":((template.formPanel.title_align==1)?"CENTER":"RIGHT"))) +
        getProperty("","","verticalAlignment", "BOTTOM") +
        getProperty("","","bounds", "0,0," +(width -40-parameterWidth)+","+getTitHeight()) +
        getProperty("","","font", getFont(template.formPanel.titleFont.font)) +
        getProperty("","","text", cmp.name) +
        closeStyle() +
        closePart():"")+
        generateCompTypeToolbar(f,cmp) +
        generateCompTypeContent(cmp,f, template.itemPanel.type,cmp.data_layout,0,(cmp.layout==0&&cmp.superord!=-1)?1:0, width-4*template.itemPanel.spacing)+
        closePart();
        return r;
    }
    public String generateMainMenu(Application.FormType tf, Application.CompType cmp) {
      String ret="\n" +
      openPart("mainMenubar","","JMenuBar") +
      openPart("mainFile","","JMenu") +
      openStyle() +
      getProperty("","","text", "File") +
      (template.formPanel.menuCall==1?getProperty("","","mnemonic", ""+0x46):"") +
      closeStyle() +
      openPart("mainApp","","JMenuItem") +
      openStyle() +
      getProperty("","","text", "Application") +
      getProperty("","","mnemonic", ""+0x43) +
      closeStyle();
      if(cmp!=null)
        ret=ret+getBehavior(getProperty(cmp.id+"compType","","visible", "true")+getProperty(cmp.id+"LoVButton","","visible", "false")+getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","caller", "-1"));
      else
          ret=ret+getBehavior(getProperty(tf.id+"menuFormType","","visible", "true")); 
      ret=ret+ closePart() +
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
    public String generateMainFrame(Application.FormType f){
        String r=""; 
        Application.CompType cmp=null;
        if(f.type!=2)cmp=f.compType[0];
        Rectangle ret=getMainFrameDimension();
        //int x= (int)ret.getX()+5;
        //int y= (int)ret.getY()+50;
        int width=(int)ret.getWidth();
        int height=(int)ret.getHeight();
        int x= (int)ret.getX();
        int y= (int)ret.getY();
        Dimension dim=new Dimension(0,0);
        if(f.type!=2)
        dim=getCompContentDimension(cmp,f,template.itemPanel.type, cmp.data_layout,1);
        //int width=Math.max((int)dim.getWidth()+40,getButtonPanelDimension(cmp,f).width);
        //int menu=getMenuPanelDimension(cmp,f).height;
        //int toolbar=getToolbarDimension(cmp,f).height;
        //int height=(int)dim.getHeight()+getButtonPanelDimension(cmp,f).height+menu+toolbar+((template.formPanel.title>0)?getTitleHeight():0)+getSearchPanelDimension(f,cmp).height;
        int h=0;
        int w=0;
        boolean ok=false;
        if(cmp!=null)
        for (int i=0; i<f.compType.length;i++)
        {
            if(f.compType[i].superord==cmp.id)
            {
                {
                    ok=true;
                    if(cmp.layout==1 && ok)
                    {
                        dim=getCompContentDimension(f.compType[i],f,template.itemPanel.type, f.compType[i].data_layout,1);
                        w=Math.max((int)dim.getWidth(),getButtonPanelDimension(f.compType[i],f).width);
                        //int toolbar=getToolbarDimension(f.compType[i],f).height;
                        h=(int)dim.getHeight()+((template.formPanel.title>0)?getTitleHeight():0)/*+getSearchPanelDimension(f,f.compType[i]).height*/+template.itemPanel.spacing-20;
                    }
                }
            }
        }
        //width=width+10;
        if(f.type==2){
            window=f.id+"menuFormType";
            curr=-1;
        }
        else{
            window=cmp.id+"compType";
            curr=cmp.id;
        }
        r=r+openPart("MainFrame","","JFrame") +
        openStyle() +
        getProperty("","","bounds", x +","+ y +","+ width +","+ height) +
        getProperty("","","title", application.name) +
        getProperty("","","iconImage", template.icon) +
        getProperty("","","resizable", ((template.resizable==1)?"true":"false")) +
        closeStyle() +
        generateMainMenu(f,cmp) +
        openPart("dtp","","JDesktopPane") +
        openStyle() +
        getProperty("","","background", getColor(template.desktop_bg)) +
        closeStyle() +
        /*r=r+
        openPart(cmp.id+"compType","","JFrame") +
        openStyle() +
        getProperty("","","defaultCloseOperation", "1") +
        getProperty("","","title", f.name) +
        getProperty("","","visible","true") +
        getProperty("","","resizable", "false") +
     //   ((cmp.superord==-1)?getProperty("","","closable", "true"):"") +
        ((cmp.superord==-1)?getProperty("","","iconImage", template.icon):"") +
    //    ((cmp.superord==-1)?getProperty("","","iconifiable", "true"):getProperty("","","modal", "true")) +
        ((cmp.superord==-1)?getProperty("","","bounds", "20,20,"+ (width+10)+","+(h+height)):getProperty("","","bounds", (x+20)+","+(y+20)+","+ (width+10)+","+(h+height))) +
        closeStyle()+
        (template.formPanel.menuCall==1?generateCompTypeMenu(f,cmp):"") +
        generateCompTypePanel(f, cmp, width-10, height)+*/
        
//        ((cmp.superord==-1)?(
        generateFormTypes(application)+
        generateSubApplicationsInternalFrames()+
        closePart() +
        generateSubApplications()+
        generateSubFormTypes(application)+
        generateInputFormTypes(application)+
        generateSearchFormTypes(application)+
        generateMasiveDeleteFormTypes(application)+
        generateParametersFormTypes(application)+
        generateAbout()+
        //):"")+
        closePart(); 
        return r;
    }
    public String generateMenuFormType(Application.FormType f){
        String r=""; 
        Rectangle ret=getMainFrameDimension();
        int x= (int)ret.getX()+5;
        int y= (int)ret.getY()+50;
        int width=getButtonPanelDimension(f).width;
        int menu=getMenuPanelDimension(f).height;
        int toolbar=getToolbarDimension(f).height;
        String mn=generateCompTypeMenu(f);
        int height=getButtonPanelDimension(f).height+(mn.equals("")?0:menu) +toolbar;
        int h=0;
        int w=0;
        window=f.id+"menuFormType";
        curr=f.id;
        String bounds="20,20,";
        Rectangle rec=getMainFrameDimension();
        bounds=Math.round(rec.getWidth()/2-(width+10)/2)+ ","+Math.round(rec.getHeight()/2-(h+height)/2)+",";
        
        r=r+
        openPart(window,"","JInternalFrame")+
        openStyle() +
        getProperty("","","defaultCloseOperation", "1") +
        getProperty("","","title", f.name) +
        getProperty("","","visible","false") +
        getProperty("","","resizable", "false") +     
        getProperty("","","closable", "true") +
        getProperty("","","frameIcon", template.icon) +
        getProperty("","","iconifiable", "true")+
        getProperty("","","bounds", bounds + width+","+height) +
        closeStyle()+
        (template.formPanel.menuCall==1?mn:"") +
        generateCompTypeToolbar(f)+
        generateCmpButtons(f,new Dimension(width, height-getButtonPanelDimension(f).height-(mn.equals("")?0:menu)))+
        closePart(); 
        return r;
    }
    public String generateCompType(Application.FormType f, Application.CompType cmp){
        String r=""; 
        Rectangle ret=getMainFrameDimension();
        int x= (int)ret.getX()+5;
        int y= (int)ret.getY()+50;
        int hMaster=0;
        int wMaster=0;
        Dimension d=generateCompTypeMasterDetailDimension(f,cmp);
        hMaster=(int)d.getHeight();
        wMaster=(int)d.getWidth();
        //Dimension dim=getCompContentDimension(cmp,f,template.itemPanel.type, cmp.data_layout,1);
        Dimension dim=getCompTypeDimension(cmp,f,template.itemPanel.type, cmp.data_layout,1);
        int width=Math.max(Math.max((int)dim.getWidth(),getButtonPanelDimension(cmp,f).width),wMaster);
        int menu=getMenuPanelDimension(cmp,f).height;
        int toolbar=getToolbarDimension(cmp,f).height;
        int tabbed=(template.itemPanel.type==1 && cmp.itemGroup.length>1)?template.labelFont.font.getSize():0;
        int height=(int)dim.getHeight()+getButtonPanelDimension(cmp,f).height+tabbed+menu;//+toolbar+((template.formPanel.title>0)?getTitleHeight():0)+((cmp.superord!=-1&&cmp.layout==0)?hMaster:0);//+getSearchPanelDimension(f,cmp).height;
        int h=0;
        int w=0;
        /*boolean ok=false;
        for (int i=0; i<f.compType.length;i++)
        if(f.compType[i].superord==cmp.id)
        {
            ok=true;
            if(cmp.layout==1 && ok)
            {
                dim=getCompContentDimension(f.compType[i],f,template.itemPanel.type, f.compType[i].data_layout,1);
                w=Math.max((int)dim.getWidth(),getButtonPanelDimension(f.compType[i],f).width);
                toolbar=getToolbarDimension(f.compType[i],f).height; 
                h=(int)dim.getHeight()+((template.formPanel.title>0)?getTitleHeight():0)+getSearchPanelDimension(f,f.compType[i]).height+template.itemPanel.spacing-20;
            }
        }
        width=width+10;*/
        window=cmp.id+"compType";
        curr=cmp.id;
        String bounds="20,20,";
        Rectangle rec=getMainFrameDimension();
        if(cmp.position_relative==1)bounds="0,0,";
        else if(cmp.position_relative==0)bounds=Math.round(rec.getWidth()/2-(width+10)/2)+ ","+Math.round(rec.getHeight()/2-(h+height)/2)+",";
        else bounds=cmp.x_relative+","+cmp.y_relative+",";
        r=r+
        ((cmp.superord==-1)?openPart(cmp.id+"compType","","JInternalFrame"):openPart(cmp.id+"compType","","JDialog")) +
        openStyle() +
        getProperty("","","defaultCloseOperation", "1") +
        getProperty("","","title", f.name) +
        getProperty("","","visible","false") +
        getProperty("","","resizable", "false") +     
        ((cmp.superord==-1)?getProperty("","","closable", "true"):"") +
        ((cmp.superord==-1)?getProperty("","","frameIcon", template.icon):"") +
        ((cmp.superord==-1)?getProperty("","","iconifiable", "true"):getProperty("","","modal", "false")) +
        ((cmp.superord==-1)?getProperty("","","bounds", bounds + width+","+height):getProperty("","","bounds", (x+20)+","+(y+20)+","+ (width+10)+","+(h+height))) +
        closeStyle()+
        (template.formPanel.menuCall==1?generateCompTypeMenu(f,cmp):"") +
        generateCompTypePanel(f, cmp, width, height, 0, 0)+
        generateCmpButtons(f,cmp,getCompArray(cmp,f),new Dimension(width, height-getButtonPanelDimension(cmp,f).height - getMenuPanelDimension(cmp,f).height))+
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
        window=cmp.id+"compTypeInput";
        r=r+
        openPart(cmp.id+"compTypeInput","","JDialog") +
        openStyle() +
        getProperty("","","defaultCloseOperation", "1") +
        getProperty("","","title", f.name+" Input Form") +
        getProperty("","","visible", "false") +
        getProperty("","","resizable", "false") +
        getProperty("","","modal", "false") +
        getProperty("","","bounds", (x+20)+","+(y+20)+","+ (width+10)+","+height) +
        closeStyle()+
        ((template.formPanel.title>0)?openPart(cmp.id + "Title","","JLabel") +
        openStyle() +
        getProperty("","","horizontalAlignment", ((template.formPanel.title_align==0)?"LEFT":((template.formPanel.title_align==1)?"CENTER":"RIGHT"))) +
        getProperty("","","verticalAlignment", "BOTTOM") +
        getProperty("","","bounds", "20,0," +(width-2*template.itemPanel.spacing) +","+getTitHeight()) +
        getProperty("","","font", getFont(template.formPanel.titleFont.font)) +
        getProperty("","","text", cmp.name + " Input Form") +
        closeStyle() +
        closePart():"")+
        generateCompTypeContent(cmp,f, 0,0,1,0,width-2*template.itemPanel.spacing) +
        generateInputButtons(f,cmp,(width-getButtonInputPanelDimension(cmp,f).width)/2,(height-getButtonInputPanelDimension(cmp,f).height)) +
        closePart(); 
        return r;
    }
    public String generateCompTypeSearch(Application.FormType f, Application.CompType cmp){
        String r=""; 
        Rectangle ret=getMainFrameDimension();
        int x= (int)ret.getX()+5;
        int y= (int)ret.getY()+50;
        Dimension dim=getCompContentDimension(cmp,f,0,1,2);
        int width=Math.max((int)dim.getWidth(),getButtonSearchPanelDimension(cmp,f).width);
        int height=(int)dim.getHeight()+getButtonSearchPanelDimension(cmp,f).height+((template.formPanel.title>0)?getTitleHeight():0)+getSearchPanelDimension(f,cmp).height+6*template.itemPanel.spacing;
        window=cmp.id+"compTypeSearch";
        r=r+
        openPart(cmp.id+"compTypeSearch","","JDialog") +
        openStyle() +
        getProperty("","","defaultCloseOperation", "1") +
        getProperty("","","title", f.name+" Search Form") +
        getProperty("","","visible", "false") +
        getProperty("","","resizable", "false") +
        getProperty("","","modal", "true") +
        getProperty("","","bounds", (x+20)+","+(y+20)+","+ (width-10)+","+height) +
        closeStyle()+
        ((template.formPanel.title>0)?openPart(cmp.id + "Title","","JLabel") +
        openStyle() +
        getProperty("","","horizontalAlignment", ((template.formPanel.title_align==0)?"LEFT":((template.formPanel.title_align==1)?"CENTER":"RIGHT"))) +
        getProperty("","","verticalAlignment", "BOTTOM") +
        getProperty("","","bounds", "20,"+template.itemPanel.spacing+"," +(width -40)+","+getTitHeight()) +
        getProperty("","","font", getFont(template.formPanel.titleFont.font)) +
        getProperty("","","text", cmp.name + " Search Form") +
        closeStyle() +
        closePart():"")+
        generateCompTypeContent(cmp,f, 0,1,2,0,width-4*template.itemPanel.spacing) +
        generateSearchButtons(f,cmp,(width-getButtonInputPanelDimension(cmp,f).width)/2,(height-getButtonSearchPanelDimension(cmp,f).height)) +
        closePart(); 
        return r;
    }
    public String generateCompTypeMasiveDelete(Application.FormType f, Application.CompType cmp){
        String r=""; 
        Rectangle ret=getMainFrameDimension();
        int x= (int)ret.getX()+5;
        int y= (int)ret.getY()+50;
        int width=400;
        int height=300;
        String[] col=new String[]{"Parameter","Value","Attribute"};
        String[][] val=new String[3][f.parameters.size()];
        for(int j=0;j<3; j++)
           for(int i=0;i<f.parameters.size(); i++)
               val[j][i]="";
        r=r+
        openPart(cmp.id+"compTypeMasiveDelete","","JDialog") +
        openStyle() +
        getProperty("","","defaultCloseOperation", "1") +
        getProperty("","","title", f.name+" Masive Delete") +
        getProperty("","","visible", "false") +
        getProperty("","","resizable", "false") +
        getProperty("","","modal", "true") +
        getProperty("","","bounds", (x+20)+","+(y+20)+","+ (width+10)+","+height) +
        closeStyle()+
        ((template.formPanel.title>0)?openPart(f.id + "Title","","JLabel") +
        openStyle() +
        getProperty("","","horizontalAlignment", ((template.formPanel.title_align==0)?"LEFT":((template.formPanel.title_align==1)?"CENTER":"RIGHT"))) +
        getProperty("","","verticalAlignment", "BOTTOM") +
        getProperty("","","bounds", "20,"+template.itemPanel.spacing+"," +(width -40)+","+getTitHeight()) +
        getProperty("","","font", getFont(template.formPanel.titleFont.font)) +
        getProperty("","","text", f.name + " Masive Delete") +
        closeStyle() +
        closePart():"")+
        getTable(cmp,f,col,val,2*template.itemPanel.spacing,getTitHeight()+2*template.itemPanel.spacing,width-4*template.itemPanel.spacing,height-getButtonParametersPanelDimension(f).height-getTitHeight()-2*template.itemPanel.spacing,4)+
        generateMasiveDeleteButtons(f,cmp, x, height- getButtonParametersPanelDimension(f).height+5)+
        closePart(); 
        return r;
    }
    public String generateFormTypeParameters(Application.FormType f){
        String r=""; 
        Rectangle ret=getMainFrameDimension();
        int x= (int)ret.getX()+5;
        int y= (int)ret.getY()+50;
        int width=400;
        int height=300;
        String[] col=new String[]{"Parameter","Value","Attribute"};
        String[][] val=new String[3][f.parameters.size()];
        for(int j=0;j<3; j++)
           for(int i=0;i<f.parameters.size(); i++)
               val[j][i]="";
        r=r+
        openPart(f.id+"formTypeParameters","","JDialog") +
        openStyle() +
        getProperty("","","defaultCloseOperation", "1") +
        getProperty("","","title", f.name+" Parameters") +
        getProperty("","","visible", "false") +
        getProperty("","","resizable", "false") +
        getProperty("","","modal", "true") +
        getProperty("","","bounds", (x+20)+","+(y+20)+","+ (width+10)+","+height) +
        closeStyle()+
        ((template.formPanel.title>0)?openPart(f.id + "Title","","JLabel") +
        openStyle() +
        getProperty("","","horizontalAlignment", ((template.formPanel.title_align==0)?"LEFT":((template.formPanel.title_align==1)?"CENTER":"RIGHT"))) +
        getProperty("","","verticalAlignment", "BOTTOM") +
        getProperty("","","bounds", "20,"+template.itemPanel.spacing+"," +(width -40)+","+getTitHeight()) +
        getProperty("","","font", getFont(template.formPanel.titleFont.font)) +
        getProperty("","","text", f.name + " Parameters") +
        closeStyle() +
        closePart():"")+
        getTable(null,f,col,val,2*template.itemPanel.spacing,getTitHeight()+2*template.itemPanel.spacing,width-4*template.itemPanel.spacing,height-getButtonParametersPanelDimension(f).height-getTitHeight()-2*template.itemPanel.spacing,3)+
        generateParametersButtons(f,x+85, height- getButtonParametersPanelDimension(f).height+5)+
        closePart(); 
        return r;
    }
    public Dimension getSearchPanelDimension(Application.FormType f, Application.CompType cmp){
        Dimension dim=new Dimension(0,0);
        if(cmp.search==1)
            dim=new Dimension(400,Math.max(45,(template.inputFont.font.getSize()+15)*cmp.getAttributes().length+template.itemPanel.spacing)); 
        return dim;
    }
    public Dimension getSearchPanelDim(Application.FormType f, Application.CompType cmp){
        Dimension dim=new Dimension(0,0);
        if(cmp.search==1)
            dim=new Dimension(400,Math.max(45,(template.inputFont.font.getSize()+15)*cmp.getAttributes().length+template.itemPanel.spacing+35)); 
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
            getProperty("","","preferredSize", "180,"+(template.inputFont.font.getSize()+10)) +
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
        getBehavior(getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","setSearch", "0"))+
        closePart():
        openPart(cmp.id + "Filter","","JButton") +
        openStyle() +
        getProperty("","","preferredSize", "30,30") +
        getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/filter.gif") +
        getProperty("","","toolTipText", "Filter") +
        closeStyle() +
        getBehavior(getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","setSearch", "0"))+
        closePart()) +
        (template.buttonPanel.style==0?
        openPart(cmp.id + "Reset","","JButton") +
        openStyle() +
        getProperty("","","text", "Reset") +
        closeStyle() +
        getBehavior(getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","reset", "0")) +
        closePart():
        openPart(cmp.id + "Reset","","JButton") +
        openStyle() +
        getProperty("","","preferredSize", "30,30") +
        getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/refresh.gif") +
        getProperty("","","toolTipText", "Reset") +
        closeStyle() +
        getBehavior(getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","reset", "0")) +
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
                len=Math.max(len,((at.type==1 && at.radio_orientation==0)?(at.values.length):1)*(at.width));  
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
                len=Math.max(len,((at.type==1 && at.radio_orientation==0)?(at.values.length):1)*(at.width)+at.title.length()*(template.labelFont.font.isBold()?(template.labelFont.font.getSize()*2/3):(template.labelFont.font.getSize()/2)));    
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
        item.input_width=((at.type==1 && at.radio_orientation==0)?(at.values.length):1)  * (at.width);
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
                //item.input_width=at.width;
            }
            else if(label_align==2 && input_align==0)
            {
                item.label_width=maxLabelWidth;
                //item.input_width=at.width;
            }
            else if(label_align==2 && input_align==1)
            {
                item.label_width=getLabelWidth(at);
                item.input_width=maxWidth-item.label_width;
            }
            else if(label_align==1 && input_align==2)
            {
                item.label_width=maxLabelWidth;
                //item.input_width=at.width;
            }
            else if(label_align==1 && input_align==0)
            {
                item.label_width=maxLabelWidth;
                //item.input_width=at.width;
            }
            else if(label_align==1 && input_align==1)
            {
                item.label_width=maxLabelWidth;
                item.input_width=maxInputWidth;
            }
            else if(label_align==0 && input_align==2)
            {
                item.label_width=getLabelWidth(at);
                item.input_width=maxWidth-item.input_width;//-at.width;
            }
            else if(label_align==0 && input_align==0)
            {
                item.label_width=maxLabelWidth;
                //item.input_width=at.width;
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
            item.height=Math.max(label_height,((at.type==1 && at.radio_orientation==1)?(at.values.length):1)*at.height);
            item.width=item.label_width+item.input_width+template.itemType[at.type].title_offset;
            //if(at.type==1 && at.radio_orientation==0)System.out.println("Sirirna"+item.input_width+" "+item.width);
        }
        else if(template.itemType[at.type].title_position==1) {
            item.x_label=x;
            item.y_label=y;
            item.x_input=x;
            item.y_input=y+label_height+template.itemType[at.type].title_offset;
            item.width=Math.max(item.label_width, item.input_width);
            item.height=label_height+((at.type==1 && at.radio_orientation==1)?(at.values.length):1)*at.height +template.itemType[at.type].title_offset;
        }
        else if(template.itemType[at.type].title_position==2) {
            item.x_label=x+item.input_width+template.itemType[at.type].title_offset;
            item.y_label=y;
            item.x_input=x;
            item.y_input=y;
            item.height=Math.max(label_height, ((at.type==1 && at.radio_orientation==1)?(at.values.length):1)*at.height);
            item.width=item.label_width+item.input_width+template.itemType[at.type].title_offset;
        }
        else if(template.itemType[at.type].title_position==3) {
            item.x_label=x;
            item.y_label=y+((at.type==1 && at.radio_orientation==1)?(at.values.length):1)*at.height+template.itemType[at.type].title_offset;
            item.x_input=x;
            item.y_input=y;
            item.width=Math.max(item.label_width, item.input_width);
            item.height=label_height+((at.type==1 && at.radio_orientation==1)?(at.values.length):1)*at.height+template.itemType[at.type].title_offset;
        }
        //item.width=item.width+(((at.type==1 && at.radio_orientation==0)?(at.values.length):1)-1)*at.width;
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
            int w=0;
            for(int i=0;i<ig.items.length;i++)
            {
                if(i==0 && template.itemPanel.nested_border==7)y1=y1+template.itemPanel.item_spacing;
                generateItemBounds(ig,ig.items[i],cmp, x1, y1,1);
                   //if((i!=0 && ig.items[i-1].breakline==0) || i==(ig.items.length-1)){
                if(ig.items[i].breakline==0) {
                    x1=x1+ig.items[i].width+template.itemPanel.item_spacing;
                    if(i!=0 && ig.items[i-1].breakline==0)
                    w=w+ig.items[i].width+template.itemPanel.item_spacing;
                    else 
                    w=ig.items[i].width+template.itemPanel.item_spacing;
                    item.width=Math.max(w, item.width);
                    item.height=Math.max(y1+ig.items[i].height, item.height);
                }
                else
                {
                    x1=template.itemPanel.item_spacing;
                    y1=y1+ig.items[i].height+template.itemPanel.item_spacing;
                    if(i!=0 && ig.items[i-1].breakline==0)
                    w=w +ig.items[i].width+template.itemPanel.item_spacing;
                    else
                    w=x1+ig.items[i].width+template.itemPanel.item_spacing;
                    item.width=Math.max(w, item.width);
                    item.height=Math.max(y1, item.height); 
                    w=x1+ig.items[i].width;
                }
            }
            item.width=item.width+(template.itemPanel.nested_border==7?template.itemPanel.item_spacing*2:0);
            //item.height=item.height+template.itemPanel.item_spacing;
        }
    }
    public Dimension generateItemGroupBounds(Application.ItemGroup ig, Application.CompType cmp){
            int x1=template.itemPanel.item_spacing,y1=template.itemPanel.item_spacing;
            int pm=0;
            int height=0,width=0;
            int w=0;
            
            for(int i=0;i<ig.items.length;i++)
            {
                generateItemBounds(ig,ig.items[i],cmp, x1, y1,0);
                if(ig.items[i].breakline==0) {
                //if((i!=0 && ig.items[i-1].breakline==0) || i==(ig.items.length-1)){ //|| i==(ig.items.length-1)) {
                    x1=x1+ig.items[i].width+template.itemPanel.item_spacing;
                    if(i!=0 && ig.items[i-1].breakline==0)
                    w=w+ig.items[i].width+template.itemPanel.item_spacing;
                    else 
                    w=ig.items[i].width+template.itemPanel.item_spacing;
                    width=Math.max(w, width);
                    height=Math.max(y1+ig.items[i].height, height);
                }
                else
                {
                    x1=template.itemPanel.item_spacing;
                    y1=y1+ig.items[i].height+template.itemPanel.item_spacing;
                    if(i!=0 && ig.items[i-1].breakline==0)
                    w=w +ig.items[i].width+template.itemPanel.item_spacing;
                    else
                    w=x1+ig.items[i].width+template.itemPanel.item_spacing;
                    width=Math.max(w, width);
                    height=Math.max(y1, height); 
                    w=x1+ig.items[i].width;
                }
            }  
            /*for(int i=0;i<ig.items.length;i++)
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
                
            } */   
        return new Dimension(width+template.itemPanel.item_spacing,height+template.itemPanel.item_spacing);
    }
    public String generateItem(Application.Item item, Application.CompType cmp, int nested, int overflow,int master){
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
        String rInput="\n" + getItemType(item,cmp, overflow, master, nested) ;
        r="" +
        rLabel +
        rInput;
        
        //Aleksanadr - Begin
        if (at.generateButon)
        {
            if(at.type==1) 
            {
                int x = item.x_input;
                int y = item.y_input + (( template.itemPanel.panel_border==7 && nested==0)?template.itemPanel.item_spacing:0);
                
                for(int i=0;i<at.values.length;i++)
                {
                    if(at.radio_orientation==0)
                    {
                        x = x + at.width;
                    }
                    else 
                    {
                        y = y + at.height;
                    }
                }
                
                String rButton=openPart(at.att_id + "" + cmp.id + "FuncButton","","JButton") +
                openStyle() +
                getProperty("","","bounds", (x + 0)+","+ (y +at.height/2 - 10) +","+ "50" +","+ "20") +
                getProperty("","","text", "Calc") +
                closeStyle() +
                closePart();
                
                r = r + rButton;
            }
            else
            {
                boolean done = false;
                
                int begin = rInput.indexOf("bounds\">");
                if (begin > -1)
                {
                    begin = begin + 8;
                    int len = rInput.length();
                    int end = begin;
                    
                    while( end < len )
                    {
                        if (rInput.charAt(end) == '<')
                        {
                            break;
                        }
                        end = end + 1;
                    }
                    
                    if (end < len)
                    {
                        String bounds = rInput.substring(begin, end);
                        
                        if (bounds != "")
                        {
                            String dots[] = bounds.split(",");
                            
                            if (dots.length == 4)
                            {
                                try 
                                {
                                    int x = Integer.parseInt(dots[ 0 ]);
                                    int y = Integer.parseInt(dots[ 1 ]);
                                    int w = Integer.parseInt(dots[ 2 ]);
                                    int h = Integer.parseInt(dots[ 3 ]);
                                    
                                    String rButton=openPart(at.att_id + "" + cmp.id + "FuncButton","","JButton") +
                                    openStyle() +
                                    getProperty("","","bounds", (x + w)+","+ (y + h/2 - 10) +","+ "50" +","+ "20") +
                                    getProperty("","","text", "Calc") +
                                    closeStyle() +
                                    closePart();
                                    
                                    r = r + rButton;
                                    done = true;
                                }
                                catch(Exception e)
                                {
                                }
                            }
                        }
                    }
                }
                
                if (!done)
                {
                    String rButton=openPart(at.att_id + "" + cmp.id + "FuncButton","","JButton") +
                    openStyle() +
                    getProperty("","","bounds", (item.x_input + item.width)+","+ (item.y_input + item.height/2 - 10) +","+ "50" +","+ "20") +
                    getProperty("","","text", "Calc") +
                    closeStyle() +
                    closePart();
                    
                    r = r + rButton;
                }
            }
        }
        //Aleksanadr - Begin
        }
        else {
            Application.ItemGroup ig=(Application.ItemGroup)item.elem;
            int width=item.width;
            for(int i=0;i<ig.items.length;i++)
            {
                r1 =r1 + generateItem(ig.items[i],cmp,1, overflow,master);
               /* if(i>0 && ig.items[i-1].breakline==0) 
                    width=width +ig.items[i].width+ template.itemPanel.item_spacing;
                else
                    width=Math.max(ig.items[i].width, width);*/
            }  
            item.width=width;
            r="" +
            openPart(ig.id +""+ cmp.id + "nestedItemGroup","","JPanel") +
            openStyle() +
            getProperty("","","bounds", item.x_input +","+ (item.y_input) +","+ (width) +","+ item.height) +
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
    public String getItemType(Application.Item it, Application.CompType cmp, int overflow, int master, int nested){
        String type="JTextField";
        String name="InputField";
        if(overflow==1)name="overflowInputField";
        if(master==1)name="MasterInputField";
        Application.AttType at=(Application.AttType)it.elem;
        int x=it.x_input;
        int y=it.y_input+((template.itemPanel.panel_border==7&&nested==0)?template.itemPanel.item_spacing:0);
        if(at.text_multiline==1)type="JTextArea";
        if(at.type==1) type="JRadioButton";
        else if(at.type==2) type="JCheckBox";
        else if(at.type==3) type="JComboBox";
        else if(at.type==4) type="JList";
        String item="";
        int less=0;
        if(at.list_of_values.tf!=null){
            if(at.list_of_values.att_id==at.att_id)less=30;
        }
        if(at.type==1) {
         item=item+
         openPart(at.att_id + "" +cmp.id + name,"","ButtonGroup");
         for(int i=0;i<at.values.length;i++){
             item=item+
             openPart(at.att_id + "" +cmp.id + name+i,"",type) +
             openStyle() +
             getProperty("","","text", at.values[i][1].toString()) +
             getProperty("","","bounds", x +","+ y+","+ (at.width) +","+ at.height)+
             getProperty("","","opaque", "false") +
             (((overflow==1 || !(at.ins_allowed==1 || at.upd_allowed==1)) && !(at.type==4))?getProperty("","","editable", "false"):"")+
             closeStyle() +
             closePart();
             if(at.radio_orientation==0)x=x+at.width;
             else y=y+at.height;
         }
         item=item + closePart();         
        }
        else {
        String itemValues="";
        for (int i=0;i<at.values.length;i++)
            itemValues=itemValues+"<constant value=\""+ at.values[i][1].toString() +"\"/>\n";
        item=item+
        (((at.text_multiline==1 && at.type==0 && at.text_scroll==1) || at.type==4)?(openPart(at.att_id + "" +cmp.id + "InputFieldScrollPane","","JScrollPane")+openStyle()+getProperty("","","border",getBorder(template.itemType[0].input_border_style, template.itemType[0].input_border, template.itemPanel.input_fg,""))+closeStyle()):"")+
        openPart(at.att_id + "" +cmp.id + name,"",type) +
        openStyle() +
        ((at.type==3 || at.type==4)? getProperty("","","content","" +
        "<constant model=\"list\">\n" +
        itemValues +
        "</constant>\n"):"") +
        ((at.type==4&&(!at.default_value.equals("")))?getProperty("","","selectedIndex", ""+at.default_index_value):"") +
        ((at.text_multiline==1 && at.type==0 && at.text_scroll==1)?getProperty("","","text", at.default_value)+
        (((overflow==1 || !(at.ins_allowed==1 || at.upd_allowed==1)) && !((at.text_multiline==1 && at.type==0 && at.text_scroll==1)) && !(at.type==4))?getProperty("","","editable", "false"):"")+closeStyle() +closePart()+openStyle():"")+
        ((at.type==4)?closeStyle() +closePart()+openStyle():"")+
        ((at.type==3&&(!at.default_value.equals("")))?getProperty("","","selectedIndex", ""+at.default_index_value):"") +
        ((at.type==0 && !(at.text_multiline==1 && at.type==0 && at.text_scroll==1))?getProperty("","","text", at.default_value):"") +
        ((at.type==1 || at.type==2)? getProperty("","","opaque", "false"):"") +
        getProperty("","","bounds", (x + it.input_width-at.width) +","+ (y) +","+ (at.width-less) +","+ at.height) +
        ((at.type==2)?getProperty("","","text", at.values[0][1]):"") +
        (((!(at.ins_allowed==1 || at.upd_allowed==1)||(at.type==3 &&at.combo_editable!=1)||(overflow==1&&at.text_multiline!=1))&&!(at.type==4)) ?getProperty("","","editable", "false"):"")+
        ((at.list_of_values.tf!=null && at.list_of_values.type==0)?getProperty("","","editable", "false"):"")+
        closeStyle() +
        closePart();}
        if(at.list_of_values.tf!=null){
            int tfid=at.list_of_values.tf.compType[0].id;
            if(at.list_of_values.att_id==at.att_id)
                item=item+
                openPart(cmp.id+at.att_id+ "SearchLoVButton","","JButton") +
                openStyle() +
                getProperty("","","text", "...") +
                getProperty("","","bounds", (x+it.input_width-less+1) +","+ (y) +",30,"+ at.height)+
                closeStyle() +
                getBehavior(getProperty(tfid+(at.list_of_values.tf.compType[0].data_layout==1?"Table":"DataPanel"),"","resetL", "0") +getProperty(tfid+"compType","","visible", "true")+getProperty(tfid+"LoVButton","","visible", "true")+getProperty(tfid+(at.list_of_values.tf.compType[0].data_layout==1?"Table":"DataPanel"),"","LoV", ""+at.list_of_values.id)) +
                closePart();   
        }
        return item;
        
    }
    public String getFuncCalcButtons(Application.ItemGroup ig, Application.CompType cmp, int x, int y)
    {
        String r = "";
        
        try 
        {
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from IISC_TOB_BUTTON where Tob_id=" + cmp.id + " and IG_id=" + ig.id);
            
            while (rs.next())
            {
                int b_id = rs.getInt("Btn_id");
                int b_x = rs.getInt("XPos");
                int b_y = rs.getInt("YPos");
                int b_w = rs.getInt("Width");
                int b_h = rs.getInt("Height");
                String b_label = rs.getString("Label");
                
                String rButton=openPart(b_id + "FuncCalcButton","","JButton") +
                openStyle() +
                getProperty("","","bounds", (x + b_x)+","+ (y + b_y) +","+ b_w +","+ b_h) +
                getProperty("","","text", b_label) +
                closeStyle() +
                closePart();
                
                r = r + rButton;
            }
        }
        catch(Exception e)
        {}
        return r;
    }
    
    public String generateItemGroup(Application.ItemGroup ig, Application.CompType cmp,int x, int y,int width, int height, int overflow, int inputPanelType){
        String r="", r1="", r2 = this.getFuncCalcButtons(ig, cmp, x, y);
        for(int i=0;i<ig.items.length;i++)
        {
            r1 =r1 + generateItem(ig.items[i],cmp,0, overflow,0);
        }    
        //if(ig.contex==1&&inputPanelType==1&&template.itemPanel.contex==1)return "";
        r="" +
        openPart((ig.overflow==1&&inputPanelType==1&&template.tablePanel.overflow==1)?cmp.id + "overflowGroup":ig.id +""+ cmp.id + "itemGroup","","JPanel") +
        openStyle() +
        getProperty("","","bounds", x + "," + y + "," + width + "," + height) +
        getProperty("","","preferredSize",  width + "," + height) +
        (overflow==0?getProperty("","","border",getBorder(template.itemPanel.panel_border, template.itemPanel.panel_border_width, template.itemPanel.fg,ig.title)):"") +
        getProperty("","","name", ig.title) +
        getProperty("","","background", getColor(template.itemPanel.bg)) +
        getProperty("","","layout", "null") +
        getProperty("","","visible", (ig.contex==1&&inputPanelType==1&&template.itemPanel.contex==1&&overflow!=1)?"false":"true") +
        closeStyle() +
        r1 +
        r2 +
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
    public Dimension generateCompTypeMasterDetailDimension(Application.FormType tf,Application.CompType cmp){
        String r="",r1="";
        int width=0, height=0, x1=0, y1=0, height1=0;
        if(cmp.layout==0)
        for(int i=0;i<tf.compType.length;i++)
        {
            if(tf.compType[i].id==cmp.superord){
                    for(int k=0;k<tf.compType[i].keys.length;k++){
                        Iterator it=tf.compType[i].keys[k].iterator();
                        while(it.hasNext()){
                            Application.AttType at=(Application.AttType)it.next();
                            for(int l=0;l<tf.compType[i].itemGroup.length;l++){
                                generateItemGroupBounds(tf.compType[i].itemGroup[l],tf.compType[i]);
                                for(int m=0;m<tf.compType[i].itemGroup[l].items.length;m++){
                                    if(tf.compType[i].itemGroup[l].items[m].elem.getClass().toString().equals("class ui.Application$AttType")){
                                        if(((Application.AttType)tf.compType[i].itemGroup[l].items[m].elem).att_id==at.att_id){
                                            Dimension d=getItemGroupDimension(tf.compType[i].itemGroup[l],tf.compType[i]);
                                            //width=Math.max(width,(int)d.getWidth());
                                            //height=Math.max(height,(int)d.getHeight());
                                            width=Math.max(width,(int)tf.compType[i].itemGroup[l].items[m].width);
                                            height=height+(int)((Application.AttType)tf.compType[i].itemGroup[l].items[m].elem).height+template.itemPanel.item_spacing;
                                            x1=tf.compType[i].itemGroup[l].items[m].x_label;
                                            y1=tf.compType[i].itemGroup[l].items[m].y_label+tf.compType[i].itemGroup[l].items[m].height+template.itemPanel.item_spacing;
                                            height1=tf.compType[i].itemGroup[l].items[m].height;
                                        }       
                                    }
                                    else{
                                        Application.ItemGroup ig=(Application.ItemGroup)tf.compType[i].itemGroup[l].items[m].elem;
                                        generateItemGroupBounds(ig,tf.compType[i]);
                                        for(int n=0;n<ig.items.length; n++) {
                                            if(((Application.AttType)ig.items[m].elem).att_id==at.att_id){
                                                Dimension d=getItemGroupDimension(ig,tf.compType[i]);
                                                //width=Math.max(width,(int)d.getWidth());
                                                //height=Math.max(height,(int)d.getHeight());
                                                 width=Math.max(width,(int)ig.items[m].width);
                                                 height=height+((Application.AttType)ig.items[m].elem).height+template.itemPanel.item_spacing;
                                                x1=tf.compType[i].itemGroup[l].items[m].x_label;
                                                y1=tf.compType[i].itemGroup[l].items[m].y_label+tf.compType[i].itemGroup[l].items[m].height+template.itemPanel.item_spacing;
                                                height1=tf.compType[i].itemGroup[l].items[m].height;
                                            }
                                                
                                        }
                                    }
                                }
                            }
                            
                        }
                    }
            }
        } 
        if(width!=0)width=width+2*template.itemPanel.spacing;
        if(height!=0)height=height+2*template.itemPanel.spacing;
        return new Dimension(width,height);
    }
    public String generateCompTypeMasterDetail(Application.FormType tf,Application.CompType cmp,int width, int height, int x, int y){
        String r="",r1="";
        int x1=0, y1=0, height1=0;
        if(cmp.layout==0)
        for(int i=0;i<tf.compType.length;i++)
        {
            if(tf.compType[i].id==cmp.superord){
                    for(int k=0;k<tf.compType[i].keys.length;k++){
                        Iterator it=tf.compType[i].keys[k].iterator();
                        while(it.hasNext()){
                            Application.AttType at=(Application.AttType)it.next();
                            for(int l=0;l<tf.compType[i].itemGroup.length;l++){
                                
                                for(int m=0;m<tf.compType[i].itemGroup[l].items.length;m++){
                                    generateItemGroupBounds(tf.compType[i].itemGroup[l],tf.compType[i]);
                                    if(tf.compType[i].itemGroup[l].items[m].elem.getClass().toString().equals("class ui.Application$AttType")){
                                        if(((Application.AttType)tf.compType[i].itemGroup[l].items[m].elem).att_id==at.att_id){
                                            r1 =r1 + generateItem(tf.compType[i].itemGroup[l].items[m],cmp,0, 1, 1);
                                            x1=tf.compType[i].itemGroup[l].items[m].x_label;
                                            y1=tf.compType[i].itemGroup[l].items[m].y_label+tf.compType[i].itemGroup[l].items[m].height+template.itemPanel.item_spacing;
                                            height1=tf.compType[i].itemGroup[l].items[m].height;
                                        }
                                    }
                                    else{
                                        Application.ItemGroup ig=(Application.ItemGroup)tf.compType[i].itemGroup[l].items[m].elem;
                                        generateItemGroupBounds(ig,tf.compType[i]);
                                        for(int n=0;n<ig.items.length; n++) {
                                            if(((Application.AttType)ig.items[m].elem).att_id==at.att_id){
                                                r1 =r1 + generateItem(ig.items[m],cmp,0, 1, 1);
                                                x1=ig.items[m].x_label;
                                                y1=ig.items[m].y_label+ig.items[m].height+template.itemPanel.item_spacing;
                                                height1=ig.items[m].height;
                                            }
                                        }
                                    }
                                }
                            }
                            
                        }
                    }
            }
        }
        r=r+""+
        openPart( cmp.id + "masterDetail","","JPanel") +
        openStyle() +
        getProperty("","","bounds", x + "," + (y- 2*template.itemPanel.spacing) + "," + width + "," + height) +
        getProperty("","","preferredSize",  width + "," + height) +
//        getProperty("","","background", getColor(template.itemPanel.bg)) +
        getProperty("","","opaque", "false") +
        getProperty("","","layout", "null") +
        closeStyle() +
        r1 +
        closePart();
        return r;
    }
     public String generateCompTypeContent(Application.CompType cmp, Application.FormType f, int inputPanelType, int data_layout, int type, int master, int ww){
         String r="";
         int hMaster=0;
         int wMaster=0;
         Set nig=cmp.getNestedItemGroups();
         Dimension d=generateCompTypeMasterDetailDimension(f,cmp);
         hMaster=(int)d.getHeight();
         wMaster=(int)d.getWidth();
         if(master==0){
             hMaster=0; 
             wMaster=0;
         }
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
             max_height=Math.max(max_height, 0); //100
             max_sum_width=Math.max(max_sum_width, ww); //500
             int max_w=max_width;
             max_width=Math.max(max_width, ww); //500
             int x=template.itemPanel.spacing,y=template.itemPanel.spacing,w=0,h=0;
             k=-1;
             j=0;
             int orientation=0;
             for(int i=0;i<cmp.itemGroup.length; i++)
             {
                 if(nig.contains(""+cmp.itemGroup[i].id))continue;
                 k++;
                 if(k>orientation)orientation=k;
                 int wi= dim[i].width+((max_sum_width-dimrow[j][2])/(Math.min(template.itemPanel.orientation+1,cmp.itemGroup.length)))+template.itemPanel.spacing;
                 //wi=dimrow[j][0];
                 Dimension dim1=new Dimension();
                 if(contex==1){
                     dim1 =getContexDimensions(cmp,f,75); 
                 }
                 r=r+generateItemGroup(cmp.itemGroup[i],cmp,x,y,wi-(int)dim1.getWidth(),dimrow[j][1],0,inputPanelType);
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
             if(inputPanelType==1 && cmp.itemGroup.length-nig.size()>1) {
                 String r1="";
                 int wi=template.itemPanel.spacing;
                 int he=((template.formPanel.title>0)?getTitleHeight():0) + (type!=2?getToolbarDimension(cmp,f).height:0);
                 int hheight=(max_height+template.labelFont.font.getSize()+2*template.itemPanel.spacing);
                 if(contex==1)
                    {   
                        r1=getContex(cmp,f,template.itemPanel.contex_pos==0?hheight:75);
                        Dimension dim1 =getContexDimensions(cmp,f,template.itemPanel.contex_pos==0?hheight:75);
                        if(template.itemPanel.contex_pos==0)
                            wi=wi +(int)dim1.getWidth();
                        else
                            he=he+ template.itemPanel.spacing +(int)dim1.getHeight();
                    }
                 int wwidth=Math.max(max_w,(ww-((contex==1&&template.itemPanel.contex_pos==0)?(wi):0))); //500
                 if(master==1) r1=r1 +generateCompTypeMasterDetail(f,cmp, wwidth, hMaster, 20,he);
                 r="" +
                 r1 +
                 (cmp.search==1&&type==2?generateSearchPanel(f,cmp,20,(template.formPanel.title>0?getTitleHeight():0)+(type!=2?getToolbarDimension(cmp,f).height:0),wwidth+wi-20,0):"")+
                 openPart(cmp.id + "DataPanel","","DataPanel") +
                 openStyle() +
                 getProperty("","","bounds", (wi) + ","+(he+hMaster) +"," + (wwidth+template.itemPanel.spacing)  +","+ hheight) +
                 getProperty("","","opaque", "false") +
                 getProperty("","","url", url) +
                 getProperty("","","username", username) +
                 getProperty("","","password", password) +
                 getProperty("","","comp", ""+cmp.id) +
                 getProperty("","","pr", ""+app.pr_id) +
                 getProperty("","","as", ""+app.application.id) +
                 closeStyle() +
                 openPart(cmp.id + "TabbedPane","","JTabbedPane") +
                 openStyle() +
                 getProperty("","","bounds", (wi+template.itemPanel.spacing) + ","+(he+hMaster) +"," + (wwidth)  +","+ hheight) +
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
                 int he=((template.formPanel.title>0)?getTitleHeight():0) + (type!=2?getToolbarDimension(cmp,f).height:0);
                 if(master==1) r1="" +generateCompTypeMasterDetail(f,cmp, wwidth, hMaster, 25, he);
                 r="" +
                 r1 +
                 (cmp.search==1&&type==2?generateSearchPanel(f,cmp,25,(template.formPanel.title>0?getTitleHeight():0)+(type!=2?getToolbarDimension(cmp,f).height:0),wwidth,0):"")+
                 openPart(cmp.id + "DataPanel","","DataPanel") +
                 openStyle() +
                 getProperty("","","bounds", 0 + ","+(((template.formPanel.title>0)?getTitleHeight():0)+ (type==1?0:((int)getSearchPanelDimension(f,cmp).getHeight() + type!=2?getToolbarDimension(cmp,f).height:0))+hMaster+(cmp.search==1&&type==2?getSearchPanelDim(f,cmp).height:0))+"," + wwidth +","+ hheight) +
                 getProperty("","","layout", "null") +
                 getProperty("","","url", url) +
                 getProperty("","","username", username) +
                 getProperty("","","password", password) +
                 getProperty("","","comp", ""+cmp.id) +
                 getProperty("","","pr", ""+app.pr_id) +
                 getProperty("","","as", ""+app.application.id) +
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
             int x=0;//25
             int y=((template.formPanel.title>0)?getTitleHeight():0) + (type!=2?getToolbarDimension(cmp,f).height:0);
             int width=Math.max(w,ww); //500
             width=ww;
             int heig=template.tablePanel.visible_rows*template.tablePanel.row_height +2*template.labelFont.font.getSize()+2*(template.tablePanel.border_style==1?(template.tablePanel.border*2):2);
             String r1=""; 
             int wwidth=0;
             if(igo!=null)
             if(template.tablePanel.overflow==1 && type!=2) {
                 generateItemGroupBounds(igo,cmp);
                 Dimension dim =generateItemGroupBounds(igo,cmp);
                 wwidth=(template.tablePanel.overflow_pos==0?template.tablePanel.overflow_size:(width-2*template.itemPanel.spacing));
                 int hheight=(template.tablePanel.overflow_pos==0?heig:template.tablePanel.overflow_size);
                 int xx=(template.tablePanel.overflow_pos==0?(x+width-wwidth):(x+2*template.itemPanel.spacing));
                 int yy=(template.tablePanel.overflow_pos==0?y:(y+heig));
                 r1=r1+
                 openPart(cmp.id + "OverflowScroll","","JScrollPane") +
                 openStyle() +
                 getProperty("","","bounds", (xx) +","+ yy +","+ wwidth +","+ hheight) +
                 closeStyle() +
                 generateItemGroup(igo,cmp,0,0,(int)dim.getWidth(),(int)dim.getHeight(),1,inputPanelType)+
                 closePart();
             }
             int scwidth=width+ (template.tablePanel.overflow==1&&template.tablePanel.overflow_pos==0?wwidth:0);
             if(master==1) r="" +generateCompTypeMasterDetail(f,cmp, width, hMaster, x, y);
             r=r+
             (cmp.search==1&&type==2?generateSearchPanel(f,cmp,x+2*template.itemPanel.spacing,(template.formPanel.title>0?getTitleHeight():0)+template.itemPanel.spacing,(template.tablePanel.overflow_pos==1&&igo!=null&&template.tablePanel.overflow==1?width:(width-wwidth))-2*template.itemPanel.spacing,type==2?1:0):"")+
             getTable(cmp,f,col,val,x+2*template.itemPanel.spacing,y+hMaster+(cmp.search==1&&type==2?(getSearchPanelDim(f,cmp).height+2*template.itemPanel.spacing):0),(template.tablePanel.overflow_pos==1&&igo!=null&&template.tablePanel.overflow==1?width:(width-wwidth))-2*template.itemPanel.spacing,(template.tablePanel.overflow_pos==0&&igo!=null&&template.tablePanel.overflow==1?heig:(heig)),type)+
             r1;
         }
         return r;
         }
    public Dimension getTablePanelDimension(Application.CompType cmp, Application.FormType f, int type){
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
                w=w+col[k].length()*template.labelFont.font.getSize()/2  + (template.tablePanel.border_style==1?(template.tablePanel.border*2):2);
                k=k+1;
            }
            else{
                Application.ItemGroup ig=(Application.ItemGroup)cmp.itemGroup[i].items[j].elem;
                for(int m=0;m<ig.items.length; m++) {
                    col[k]=((Application.AttType)ig.items[m].elem).title;
                    w=w+col[k].length()*template.labelFont.font.getSize()/2  + (template.tablePanel.border_style==1?(template.tablePanel.border*2):2);
                    k=k+1;
                }
            }
            }     
         }
       w=w+2*template.itemPanel.spacing;
       int h=0,we=0;
       if(igo!=null)
       {
        int width=w; //500
        int heig=template.tablePanel.visible_rows*template.tablePanel.row_height +2*template.labelFont.font.getSize()+2*(template.tablePanel.border_style==1?(template.tablePanel.border*2):2);
        Dimension dim=new Dimension(((template.tablePanel.overflow_pos==0)?template.tablePanel.overflow_size:width),((template.tablePanel.overflow_pos==0)?heig:template.tablePanel.overflow_size));
        if(type==0){
            if(template.tablePanel.overflow_pos==0)
                we=(int)dim.getWidth();
            else 
                h=(int)dim.getHeight();
           }
       }
       return new Dimension(w+we,template.tablePanel.visible_rows*template.tablePanel.row_height +2*template.labelFont.font.getSize() +2*(template.tablePanel.border_style==1?(template.tablePanel.border*2):2)+h+template.itemPanel.spacing); //500
    }
    public Dimension getCompTypeDimension(Application.CompType cmp, Application.FormType f, int inputPanelType, int data_layout, int type){
        Dimension dim=new Dimension();
        dim.width=template.itemPanel.spacing;
        dim.height=0;
        Vector[] cmpArr=getCompArray(cmp,f);
        Iterator cit=cmpArr[0].iterator();
        while(cit.hasNext()){
            Object o=cit.next();
            if(o.getClass().toString().equals("class ui.Application$CompType"))
            {  
                Application.CompType c=(Application.CompType)o;
                Dimension d1=generateCompTypeMasterDetailDimension(f,c);
                int wm=(int)d1.getWidth()+2*template.itemPanel.spacing;
                Dimension dim1=getCompContentDimension(c,f,inputPanelType,c.data_layout,type);
                dim1.height=dim1.height+getToolbarDimension(c,f).height+((template.formPanel.title>0)?getTitleHeight():0)+((c.layout==0&&c.superord!=-1)?generateCompTypeMasterDetailDimension(f,c).height:0);
                dim1.width=Math.max(dim1.width,wm);
                dim.width=dim.width+dim1.width+template.itemPanel.spacing;
                dim.height=Math.max(dim1.height+template.itemPanel.spacing,dim.height);
            }
            else {
                Vector[] cmpArrm=(Vector[])o;
                Application.CompType c=(Application.CompType)cmpArrm[0].iterator().next();
                Dimension dim1=getCompTypeDimension(c,f,inputPanelType,c.data_layout,type);
                dim.width=dim.width+dim1.width+template.itemPanel.spacing;
                //dim.height=Math.max(dim1.height+template.itemPanel.spacing,dim.height);
                dim.height=Math.max(dim1.height,dim.height);
            }
        }
        //dim.height=dim.height+template.itemPanel.spacing;
        cit=cmpArr[1].iterator();
        if(cmpArr[1].size()>0){
            Dimension dimm=new Dimension();
            dimm.width=template.itemPanel.spacing;
            dimm.height=template.itemPanel.spacing;
            while(cit.hasNext()){
                Object o=cit.next();
                if(o.getClass().toString().equals("class ui.Application$CompType"))
                {  
                    Application.CompType c=(Application.CompType)o;
                    Dimension dimm1=getCompContentDimension(c,f,inputPanelType,c.data_layout,type);
                    dimm1.height=dimm1.height+getToolbarDimension(c,f).height+((template.formPanel.title>0)?getTitleHeight():0);
                    dimm.width=dimm.width+dimm1.width+template.itemPanel.spacing;
                    dimm.height=Math.max(dimm1.height+template.itemPanel.spacing,dimm.height);
                }
                else {
                    Vector[] cmpArrm=(Vector[])o;
                    Application.CompType c=(Application.CompType)cmpArrm[0].iterator().next();
                    Dimension d1=generateCompTypeMasterDetailDimension(f,c);
                    int wm=(int)d1.getWidth()+2*template.itemPanel.spacing;
                    Dimension dimm1=getCompTypeDimension(c,f,inputPanelType,c.data_layout,type);
                    dimm1.width=Math.max(dimm1.width,wm);
                    dimm.width=dimm.width+dimm1.width+template.itemPanel.spacing;
                    //dimm.height=Math.max(dimm1.height+template.itemPanel.spacing,dimm.height);
                    dimm.height=Math.max(dimm1.height,dimm.height);
                }
            }
            dim.width=Math.max(dim.width,dimm.width);
            dim.height=dim.height+dimm.height;//+template.itemPanel.spacing;
        }
        return dim;
    }
    public Vector[] getCompArray(Application.CompType cmp, Application.FormType f){
        Vector[] v=new Vector[2];
        v[0]=new Vector();
        v[1]=new Vector();
        v[0].add(cmp);
        int cmpid=cmp.id;
        for(int i=0; i<f.compType.length;i++){
            if(f.compType[i].superord==cmpid && f.compType[i].layout==1){
                if(f.compType[i].position==1)
                {
                    //
                    Vector[] v1=getCompArray(f.compType[i],f);
                    if(!v1[1].isEmpty())
                        v[0].add(v1);
                    else
                        v[0].add(f.compType[i]);
                }
                else
                {
                    //v[1].add(f.compType[i]);
                    Vector[] v1=getCompArray(f.compType[i],f);
                    if(!v1[1].isEmpty())
                        v[1].add(v1);
                    else
                        v[1].add(f.compType[i]);
                }
            }
        }
        return v;
    }
    public Dimension getCompContentDimension(Application.CompType cmp, Application.FormType f, int inputPanelType, int data_layout, int type){
        String r="";
        Set nig=cmp.getNestedItemGroups();
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
                   if(nig.contains(""+cmp.itemGroup[i].id))continue;
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
            max_height=Math.max(max_height, 0);//100 
            max_sum_width=Math.max(max_sum_width, 0); //500
            int max_width1=max_width;
            max_width=Math.max(max_width, 0);//500
            if(inputPanelType==1 && cmp.itemGroup.length-nig.size()>1) {
                int wi=0;
                int he=(max_height+2*template.labelFont.font.getSize());
                if(contex==1)
                {
                    Dimension dim1 =getContexDimensions(cmp,f,template.itemPanel.contex_pos==0?(he+10):75);
                    if(template.itemPanel.contex_pos==0)
                        wi=wi+ template.itemPanel.spacing +(int)dim1.getWidth();
                    else
                        he=he+ template.itemPanel.spacing +(int)dim1.getHeight();
                }
                wi=wi+max_width1;
                wi=Math.max(wi,0);//500
                return new Dimension(wi+2*template.itemPanel.spacing,he+2*template.itemPanel.spacing);
            }
            else
            {
                return new Dimension((max_sum_width+(2+orientation)*template.itemPanel.spacing), (height+2*template.itemPanel.spacing));
            }
        }
        return getTablePanelDimension(cmp,f,type==2?1:0);
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
                    int width=w;
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
        String r1=(type==2?"Search":(type==1?"Contex":(type==3?"Parameters":(type==4?"MasiveDelete":""))));
        //String r2="DataTable";
        String r2=((type==1 || type==2 || type==3)?"JTable":"DataTable");
        String col="", val="";
        for(int i=0;i<column.length;i++)
            col=col+"<constant value=\""+ column[i] +"\"/>\n";
        for(int j=0;j<list.length;j++){
            val=val+"<constant>\n";
            for(int i=0;i<list[j].length;i++)
            val=val+"<constant value=\""+ list[j][i] +"\"/>\n";
            val=val+"</constant>\n";
        }
        col=col+"<constant value=\"col\"/>\n";
        val=val+"<constant>\n";
        val=val+"<constant value=\"val\"/>\n";
        val=val+"</constant>\n";
         String r=""+
        openPart(""+(type==3?""+f.id+r1:(""+cmp.id + r1))+"TableScroll","","JScrollPane") +
        openStyle() +
        getProperty("","","bounds", x +","+ y +","+ w +","+ h) +
        closeStyle() +
        openPart(""+(type==3?""+f.id+r1:(""+cmp.id + r1))+"Table","",r2) +
        openStyle() +
        getProperty("","","visible", "true") +
        getProperty("","","selectionMode", "0") +
        getProperty("","","autoResizeMode", "0");
        if(type!=1 && type!=2 && type!=3)
            r=r+getProperty("","","url", url) +
            getProperty("","","username", username) +
            getProperty("","","password", password) +
            getProperty("","","comp", ""+cmp.id) +
            getProperty("","","pr", ""+app.pr_id) +
            getProperty("","","as", ""+app.application.id);
   /*      getProperty("","","columnNames","" +
        "\n<constant model=\"list\">\n" +
         col +
        "</constant>\n")+
        getProperty("","","content","" +
        "\n<constant model=\"table.rowMajor\">\n" +
    //    val +
        "</constant>\n")+  */
        r=r+closeStyle() +
        closePart() +
        closePart();
        return r;
    }
     public Dimension getButtonPanelDimension(Application.CompType cmp, Application.FormType f){
        if(cmp.del_masive==0)return new Dimension(540, 120);
        return new Dimension(600, 120);
     }
    public Dimension getButtonInputPanelDimension(Application.CompType cmp, Application.FormType f){
       return new Dimension(540, 80);
    }
    public Dimension getButtonPanelDimension(Application.FormType f){
       return new Dimension(600, 80);
    }
    public Dimension getButtonSearchPanelDimension(Application.CompType cmp, Application.FormType f){
       return new Dimension(540, 80);
    }
    public Dimension getButtonParametersPanelDimension(Application.FormType f){
       return new Dimension(540, 80);
    }
    public Dimension getMenuPanelDimension(Application.CompType cmp, Application.FormType f){
        Dimension dim=new Dimension(0, 0);
        boolean yes=false;
        for (int i=0; i<f.compType.length;i++)
            if((f.compType[i].superord==cmp.id || (f.menu.size()>0&&cmp.superord==-1)) && f.compType[i].layout==0 && template.formPanel.menuCall==1)yes=true;
            if(yes)dim=new Dimension(540, template.labelFont.font.getSize()+15);
        return dim;
    }
    public Dimension getMenuPanelDimension(Application.FormType f){
        Dimension dim=new Dimension(0, 0);
        if(f.menu.size()>0 && template.formPanel.menuCall==1)
           dim=new Dimension(540, template.labelFont.font.getSize()+15);
        return dim;
    }
    public Dimension getToolbarDim(Application.FormType f){
        Dimension dim=new Dimension(0, 0);
        if(f.type!=2)return dim;
        Object[] menu=f.menu.toArray();
        for (int i=0; i<menu.length;i++)
        {
            if(((Application.Menu)menu[i]).ui_position==1 || ((Application.Menu)menu[i]).ui_position==2);
            dim=new Dimension(500, 40);
        }
        return dim;
    }
    public Dimension getToolbarDim(Application.CompType cmp, Application.FormType f){
        Dimension dim=new Dimension(0, 0);
        for (int i=0; i<f.compType.length;i++)
            if(f.compType[i].superord==cmp.id && f.compType[i].layout==0 && template.formPanel.buttonCall==1)
                dim=new Dimension(500, 40);
        Object[] menu=f.menu.toArray();
        if(cmp.superord==-1)
        for (int i=0; i<menu.length;i++)
        {
            if(((Application.Menu)menu[i]).ui_position==1 || ((Application.Menu)menu[i]).ui_position==2);
            dim=new Dimension(500, 40);
        }
        return dim;
    }
    public Dimension getToolbarDimension(Application.CompType cmp, Application.FormType f){
        Dimension dim=new Dimension(0, 0);
        for (int i=0; i<f.compType.length;i++)
            if(f.compType[i].superord==cmp.id && f.compType[i].layout==0 && template.formPanel.buttonCall==1)
                dim=new Dimension(500, 40+template.itemPanel.spacing);
        Object[] menu=f.menu.toArray();
        if(cmp.superord==-1)
        for (int i=0; i<menu.length;i++)
        {
            if(((Application.Menu)menu[i]).ui_position==1 || ((Application.Menu)menu[i]).ui_position==2);
                dim=new Dimension(500, 40+template.itemPanel.spacing);
        }
        return dim;
    }
    public Dimension getToolbarDimension(Application.FormType f){
        Dimension dim=new Dimension(0, 0);
        Object[] menu=f.menu.toArray();
        for (int i=0; i<menu.length;i++)
        {
            if(((Application.Menu)menu[i]).ui_position==1 || ((Application.Menu)menu[i]).ui_position==2);
                dim=new Dimension(500, 40+template.itemPanel.spacing);
        }
        return dim;
    }    
    public String generateButtons(Application.FormType f, int x, int y){
      String r=""; 
      if(template.buttonPanel.style==1)
      {
            r=r +
            openPart(f.id + "NavigationPanel","","JPanel") +
            openStyle() +
            getProperty("","","bounds", x+","+y+","+getButtonPanelDimension(f).width+",35") +
            getProperty("","","opaque", "false") +
            getProperty("","","visible", "true")+
            closeStyle() +
            openPart(curr + "Cancel","","JButton") +
            openStyle() +
            getProperty("","","preferredSize", "30,30") +
            getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/close.gif") +
            getProperty("","","toolTipText", "Close") +
            closeStyle() +
            getBehavior(getProperty(window,"","visible", "false")) +
            closePart() +
            closePart();
        }
        else
        {
            r=r +
            openPart(f.id + "NavigationPanel","","JPanel") +
            openStyle() +
            getProperty("","","bounds", x+","+y+","+getButtonPanelDimension(f).width+",35") +
            getProperty("","","opaque", "false") +
            getProperty("","","visible", "true")+
            closeStyle() +
            openPart(curr + "Close","","JButton") +
            openStyle() +
            getProperty("","","text", "Close") +
            closeStyle() +
            getBehavior(getProperty(window,"","visible", "false")) +
            closePart() +
            closePart();
        }
        return r;   
    }
    public String generateButtons(Application.FormType f, Application.CompType cmp,int x, int y, String visible){
      String r=""; 
      //int cid=cmp.superord>-1?cmp.superord:cmp.id;
      int cid=cmp.id;
      int layout=-1;
      curr=cmp.id;
      if(curr==cmp.id)
          layout=cmp.data_layout;
      else {
          for (int i=0; i<f.compType.length;i++)
              if(curr==f.compType[i].id)
                  layout=f.compType[i].data_layout;
      }
      /*for (int j=0; j<f.compType.length;j++)
            if(f.compType[j].id==cmp.superord && f.compType[j].layout==1 )
            {
                //r=generateButtons(f,f.compType[j],x,y,"true");
                visible="false";
            }*/
      /* for (int i=0; i<f.compType.length;i++){
           if(f.compType[i].superord==cmp.id && cmp.layout==1)
               generateButtons(f,f.compType[i],x,y,"false");
        }*/
       curr=cmp.id;
      if(template.buttonPanel.style==1)
      {
      String cmpSearch="";
      if(cmp.search==1){
          cmpSearch=""+
          openPart(cmp.id + "SearchButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/filter.gif") +
          getProperty("","","toolTipText", "Search") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","reset", "0")+getProperty(cmp.id+"compTypeSearch","","visible", "true")) +
          closePart();
      }
      String cmpSelect="";
      if(cmp.superord==-1){
          cmpSelect=""+
          openPart(cmp.id + "SelectButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/selectall.gif") +
          getProperty("","","toolTipText", "Select") +
          getProperty("","","visible", "false") +
          closeStyle() +
          getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "select")) +
          closePart();
      }
      String masiveDelete="";
      if(cmp.del_masive==1){
          masiveDelete=""+
          openPart(cmp.id + "MasiveDeleteButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/obsolutereport.gif") +
          getProperty("","","toolTipText", "Masive delete") +
          (cmp.del_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
          closeStyle() +
          //getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "deleteAll")) +
          getBehavior(getProperty(curr+"compTypeMasiveDelete","","visible", "true")) +
          closePart();
      }
      r=r +
          openPart(cmp.id + "NavigationPanel","","JPanel") +
          openStyle() +
          getProperty("","","bounds", x+","+y+","+getButtonPanelDimension(cmp,f).width+",35") +
          getProperty("","","opaque", "false") +
          getProperty("","","visible", visible)+
          closeStyle() +
          (cmp.superord==-1?cmpSelect:"")+
          openPart(curr + "FirstButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/prevv.gif") + 
          getProperty("","","toolTipText", "First record") +
          (cmp.que_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
          closeStyle() +
          getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "first")) +
          closePart()+
          openPart(curr + "PrevButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/prev.gif") +
          getProperty("","","toolTipText", "Previos record record") +
          (cmp.que_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
          closeStyle() +
          getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "previos")) +
          closePart()+
          openPart(curr+"Records","","JLabel") +
          openStyle() + 
          getProperty("","","horizontalAlignment", "CENTER") +
          getProperty("","","preferredSize", "75,30") +
          getProperty("","","text", "Records: 0") +
          closeStyle() +
          closePart()+
          openPart(curr + "NextButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/next.gif") +
          getProperty("","","toolTipText", "Next record") +
          (cmp.que_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
          closeStyle() +
          getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "next")) +
          closePart()+
          openPart(curr + "LastButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/nextt.gif") +
          getProperty("","","toolTipText", "Last record") +
          (cmp.que_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
          closeStyle() +
          getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "last")) +
          closePart()+
          (cmp.search==1?cmpSearch:"")+
          closePart()+
          openPart(cmp.id + "ButtonPanel","","JPanel") +
          openStyle() +
          getProperty("","","bounds", x+","+(y+35)+","+getButtonPanelDimension(cmp,f).width+",35") +
          getProperty("","","opaque", "false") +
          getProperty("","","visible", visible) +
          closeStyle() +
          openPart(curr + "NewButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/new.gif") +
          getProperty("","","toolTipText", "New") +
          (cmp.ins_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
          closeStyle() +
          getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "insert")+(layout==1?getProperty(curr+"compTypeInput","","visible", "true"):"")) +
          closePart() +
          openPart(curr + "DuplicateButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/copy.gif") +
          getProperty("","","toolTipText", "Duplicate") +
          (cmp.ins_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
          closeStyle() +
          getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "duplicate")+(layout==1?getProperty(curr+"compTypeInput","","visible", "true"):"")) +
          closePart() +
          openPart(curr + "DeleteButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/erase.gif") +
          getProperty("","","toolTipText", "Delete") +
          (cmp.del_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
          closeStyle() +
          getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "delete")) +
          closePart()+
          (cmp.del_masive==1?masiveDelete:"")+
          openPart(curr + "ApplyButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/apply.gif") +
          getProperty("","","toolTipText", cmp.data_layout==0?"Apply":"Edit") +
          ((cmp.upd_allowed==1 || cmp.ins_allowed==1)?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
          closeStyle() +
          getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", (layout==1?"update":"save"))+(layout==1?getProperty(curr+"compTypeInput","","visible", "true"):"")) +
          closePart() +
          openPart(curr + "OKButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/ok.gif") +
          getProperty("","","toolTipText", "OK") +
          ((cmp.ins_allowed==1 || cmp.upd_allowed==1)?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
          closeStyle() +
          getBehavior(layout==0?getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "save"):""+getProperty(curr+"compType","","visible", "false")) +
          closePart() +
          openPart(curr + "LoVButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/select.gif") +
          getProperty("","","toolTipText", "Select") +
          getProperty("","","visible", "false") +
          closeStyle() +
          getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "lov")+getProperty(curr+"compType","","visible", "false")) +
          closePart() +
          openPart(curr + "Cancel","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/close.gif") +
          getProperty("","","toolTipText", "Cancel") +
          closeStyle() +
          getBehavior(getProperty(window,"","visible", "false")) +
          closePart() +
          closePart();
      }
      else
      {
      String cmpSearch="";
      if(cmp.search==1){
          cmpSearch=""+
          openPart(cmp.id + "SearchButton","","JButton") +
          openStyle() +
          getProperty("","","text", "Search") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","reset", "0")+getProperty(cmp.id+"compTypeSearch","","visible", "true")) +
          closePart();
      }
      String cmpSelect="";
      if(cmp.superord==-1){
          cmpSelect=""+
          openPart(cmp.id + "SelectButton","","JButton") +
          openStyle() +
          getProperty("","","text", "Select") +
          getProperty("","","visible", "false") +
          closeStyle() +
          getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "select")) +
          closePart();
      }
      String masiveDelete="";
      if(cmp.del_masive==1){
          masiveDelete=""+
          openPart(cmp.id + "MasiveDeleteButton","","JButton") +
          openStyle() +
          getProperty("","","text", "Masive del.") +
          closeStyle() +
          //getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "deleteAll")) +
          getBehavior(getProperty(curr+"compTypeMasiveDelete","","visible", "true")) +
          closePart();
      }
      r=r +
      openPart(cmp.id + "NavigationPanel","","JPanel") +
      openStyle() +
      getProperty("","","bounds", x+","+y+","+getButtonPanelDimension(cmp,f).width+",35") +
      getProperty("","","opaque", "false") +
      getProperty("","","visible", visible)+
      closeStyle() +
      (cmp.superord==-1?cmpSelect:"")+
      openPart(curr + "FirstButton","","JButton") +
      openStyle() +
      getProperty("","","text", "First") +
      (cmp.que_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
      closeStyle() +
      getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "first")) +
      closePart()+
      openPart(curr + "PrevButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Prev") +
      (cmp.que_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
      closeStyle() +
      getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "previos")) +
      closePart()+
      openPart(curr+"Records","","JLabel") +
      openStyle() + 
      getProperty("","","horizontalAlignment", "CENTER") +
      getProperty("","","preferredSize", "75,30") +
      getProperty("","","text", "Records: 0") +
      closeStyle() +
      closePart()+
      openPart(curr + "NextButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Next") +
      (cmp.que_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
      closeStyle() +
      getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "next")) +
      closePart()+
      openPart(curr + "LastButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Last") +
      (cmp.que_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
      closeStyle() +
      getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "last")) +
      closePart()+
      (cmp.search==1?cmpSearch:"")+
      closePart()+
      openPart(cmp.id + "ButtonPanel","","JPanel") +
      openStyle() +
      getProperty("","","bounds", x+","+(y+35)+","+getButtonPanelDimension(cmp,f).width+",35") +
      getProperty("","","opaque", "false") +
      getProperty("","","visible", visible) +
      closeStyle() +
      openPart(curr + "NewButton","","JButton") +
      openStyle() +
      getProperty("","","text", "New") +
      (cmp.ins_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
      closeStyle() +
      getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "insert")+(layout==1?getProperty(cid+"compTypeInput","","visible", "true"):"")) +
      closePart() +
      openPart(curr + "DuplicateButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Duplicate") +
      (cmp.ins_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
      closeStyle() +
      getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "duplicate")+(layout==1?getProperty(cid+"compTypeInput","","visible", "true"):"")) +
      closePart() +
      openPart(curr + "DeleteButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Delete") +
      (cmp.del_allowed==1?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
      closeStyle() +
      getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "delete")) +
      closePart()+
      (cmp.del_masive==1?masiveDelete:"")+
      openPart(curr + "ApplyButton","","JButton") +
      openStyle() +
      getProperty("","","text", cmp.data_layout==0?"Apply":"Edit") +
      ((cmp.ins_allowed==1 || cmp.upd_allowed==1)?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
      closeStyle() +
      getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", (layout==1?"update":"save"))+(layout==1?getProperty(cid+"compTypeInput","","visible", "true"):"")) +
      closePart() +
      openPart(curr + "OKButton","","JButton") +
      openStyle() +
      ((cmp.ins_allowed==1 || cmp.upd_allowed==1)?getProperty("","","visible", "true"):getProperty("","","visible", "false"))+
      getProperty("","","text", "OK") +
      closeStyle() +
      getBehavior(layout==0?getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "save"):""+getProperty(curr+"compType","","visible", "false")) +
      closePart() +
      openPart(curr + "LoVButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Select") +
      getProperty("","","visible", "false") +
      closeStyle() +
      getBehavior(getProperty(curr+(layout==1?"Table":"DataPanel"),"","setAction", "lov")+getProperty(curr+"compType","","visible", "false")) +
      closePart() +
      openPart(curr + "Cancel","","JButton") +
      openStyle() +
      getProperty("","","text", "Cancel") +
      closeStyle() +
      getBehavior(getProperty(window,"","visible", "false")) +
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
          openPart(cmp.id + "InputButtonPanel","","JPanel") +
          openStyle() +
          getProperty("","","bounds", x+","+y+",540,35") +
          getProperty("","","opaque", "false") +
          closeStyle() +
          /*openPart(cmp.id + "NewButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/new.gif") +
          getProperty("","","toolTipText", "New") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+"Table","","setAction", "create")) +
          closePart() +
          openPart(cmp.id + "DeleteButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/erase.gif") +
          getProperty("","","toolTipText", "Delete") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+"Table","","setAction", "delete")+getProperty(cmp.id+"compTypeInput","","visible", "false")) +
          closePart()+
          openPart(cmp.id + "ApplyButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/apply.gif") +
          getProperty("","","toolTipText", "Apply") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+"Table","","setAction", "save")) +
          closePart() + */      
          openPart(cmp.id + "InputOKButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/ok.gif") +
          getProperty("","","toolTipText", "OK") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+"Table","","setAction", "save")+getProperty(cmp.id+"compTypeInput","","visible", "false")) +
          closePart() +
          openPart(cmp.id + "InputCancel","","JButton") +
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
      openPart(cmp.id + "InputButtonPanel","","JPanel") +
      openStyle() +
      getProperty("","","bounds", x+","+y+",540,35") +
      getProperty("","","opaque", "false") +
      closeStyle() +
      /*openPart(cmp.id + "NewButton","","JButton") +
      openStyle() +
      getProperty("","","text", "New") +
      closeStyle() +
      getBehavior(getProperty(cmp.id+"Table","","setAction", "create")) +
      closePart() +
      openPart(cmp.id + "DeleteButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Delete") +
      closeStyle() +
      getBehavior(getProperty(cmp.id+"Table","","setAction", "delete")+getProperty(cmp.id+"compTypeInput","","visible", "false")) +
      closePart()+
      openPart(cmp.id + "ApplyButton","","JButton") +
      openStyle() +
      getProperty("","","text", "Apply") +
      closeStyle() +
      getBehavior(getProperty(cmp.id+"Table","","setAction", "save")) +
      closePart() +*/
      openPart(cmp.id + "InputOKButton","","JButton") +
      openStyle() +
      getProperty("","","text", "OK") +
      closeStyle() +
      getBehavior(getProperty(cmp.id+"Table","","setAction", "save")+getProperty(cmp.id+"compTypeInput","","visible", "false")) +
      closePart() +
      openPart(cmp.id + "InputCancel","","JButton") +
      openStyle() +
      getProperty("","","text", "Cancel") +
      closeStyle() +
      getBehavior(getProperty(cmp.id+"compTypeInput","","visible", "false")) +
      closePart() +
      closePart();
      }
      return r;   
    }
    public String generateParametersButtons(Application.FormType f, int x, int y){
      String r=""; 
      if(template.buttonPanel.style==1)
      {
      r="\n" +
          openPart(f.id + "ButtonPanel","","JPanel") +
          openStyle() +
          getProperty("","","bounds", x+","+y+",540,35") +
          getProperty("","","opaque", "false") +
          closeStyle() +
          openPart(f.id + "Cancel","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/close.gif") +
          getProperty("","","toolTipText", "Cancel") +
          closeStyle() +
          getBehavior(getProperty(f.id+"formTypeParameters","","visible", "false")) +
          closePart() +
          closePart();
      }
      else
      {
          r="\n" +
          openPart(f.id + "ButtonPanel","","JPanel") +
          openStyle() +
          getProperty("","","bounds", x-30+","+y+",80,35") +
          getProperty("","","opaque", "false") +
          closeStyle() +
          openPart(f.id + "Cancel","","JButton") +
          openStyle() +
          getProperty("","","text", "Cancel") +
          closeStyle() +
          getBehavior(getProperty(f.id+"formTypeParameters","","visible", "false")) +
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
          getBehavior(getProperty(cmp.id+"compTypeSearch","","visible", "false")+getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","setSearchChoice", "0")) +
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
          getBehavior(getProperty(cmp.id+"compTypeSearch","","visible", "false")+getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","setSearchChoice", "0")) +
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
    public String generateMasiveDeleteButtons(Application.FormType f, Application.CompType cmp,int x, int y){
      String r=""; 
      if(template.buttonPanel.style==1)
      {
      r="\n" +
          openPart(cmp.id + "ButtonPanel","","JPanel") +
          openStyle() +
          getProperty("","","bounds", x+","+y+",400,35") +
          getProperty("","","opaque", "false") +
          closeStyle() +
          openPart(cmp.id + "DeleteButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/erase.gif") +
          getProperty("","","toolTipText", "Delete") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","setMasiveDelete", "0")) +
          closePart() +
          openPart(cmp.id + "DeleteAllButton","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/obsolutereport.gif") +
          getProperty("","","toolTipText", "Delete All") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","setAction", "deleteAll")) +
          closePart() +
          openPart(cmp.id + "Cancel","","JButton") +
          openStyle() +
          getProperty("","","preferredSize", "30,30") +
          getProperty("","","icon", System.getProperty("LIQUIDUI")+"/icons/close.gif") +
          getProperty("","","toolTipText", "Cancel") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+"compTypeMasiveDelete","","visible", "false")) +
          closePart() +
          closePart();
      }
      else
      {
          r="\n" +
          openPart(cmp.id + "ButtonPanel","","JPanel") +
          openStyle() +
          getProperty("","","bounds", (x-180)+","+y+",400,35") +
          getProperty("","","opaque", "false") +
          closeStyle() +
          openPart(curr + "DeleteButton","","JButton") +
          openStyle() +
          getProperty("","","text", "Delete") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","setMasiveDelete", "0")) +
          closePart() +
          openPart(curr + "DeleteAllButton","","JButton") +
          openStyle() +
          getProperty("","","text", "Delete All") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","setAction", "deleteAll")) +
          closePart() +
          openPart(cmp.id + "Cancel","","JButton") +
          openStyle() +
          getProperty("","","text", "Cancel") +
          closeStyle() +
          getBehavior(getProperty(cmp.id+"compTypeMasiveDelete","","visible", "false")) +
          closePart() +
          closePart();
      }
      return r;  
    }
    public String generateCompTypeMenu(Application.FormType f) { 
        String r="";
        r=r + getMenu(f, 1);
        String ret="";
        if(!r.equals(""))
        ret =
        openPart(f.id+"menuFormTypeMenu","","JMenuBar") +
          r +
        closePart();
        return ret;
    }
    public String generateCompTypeMenu(Application.FormType f, Application.CompType cmp) { 
        String r="";
        Set p=getDesc(f,cmp);
        HashSet pom=new HashSet();
        Iterator it=p.iterator();
        while(it.hasNext()){
            Application.CompType c=(Application.CompType)it.next();
            if(c.superord==-1 || c.layout==1)pom.add(""+c.id);
        }
        for (int i=0; i<f.compType.length;i++)
        {
        if((pom.contains(""+f.compType[i].superord)||f.compType[i].superord==cmp.id)  && f.compType[i].layout==0 && template.formPanel.menuCall==1)
            r=r+    
            openPart(f.compType[i].id+"compTypeMenu","","JMenuItem") +
            openStyle() +
            getProperty("","","text",f.compType[i].name) +
            //getProperty("","","maximumSize",""+(f.compType[i].name.length()*(template.labelFont.font.isBold()?(template.labelFont.font.getSize()):(template.labelFont.font.getSize()))/2+30)+",100") +
             getProperty("","","minimumSize",""+(f.compType[i].name.length()*(template.labelFont.font.isBold()?(template.labelFont.font.getSize()/3*2):(template.labelFont.font.getSize()/2))+10)+",10") +
             getProperty("","","maximumSize",""+(f.compType[i].name.length()*(template.labelFont.font.isBold()?(template.labelFont.font.getSize()/3*2):(template.labelFont.font.getSize()/2))+20)+",10") +
            (template.formPanel.menuCall==1?getProperty("","","mnemonic", ""+getMnemonic(f.compType[i])):"")+
            closeStyle() +
            getBehavior(getProperty(f.compType[i].id+"compType","","visible","true")+getProperty(f.compType[i].id+"LoVButton","","visible", "false")+getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","caller", "-1"))+//getProperty(f.compType[i].id+"compType","","modal","true")) +
            closePart();
        }
        if(cmp.superord==-1)
        r=r + getMenu(f, 1);
        String ret="";
        if(!r.equals(""))
        ret =
        openPart(cmp.id+"compTypeMenu","","JMenuBar") +
          r +
        closePart();
        return ret;
    }
    private String getMenu(Application.FormType f, int k) { 
        String r="";
        Object[] menu=f.menu.toArray();
        for (int i=0; i<menu.length;i++)
        {
            Application.FormType f1=app.getFormT(((Application.Menu)menu[i]).called_tf);
            if(f1 !=null && ((Application.Menu)menu[i]).ui_position!=1){
            r=r+
            openPart(f1.id+"compTypeMenu","","JMenuItem") +
            openStyle() +
            getProperty("","","text",f1.name) +
            getProperty("","","background",getColor(template.bg)) +
            getProperty("","","foreground",getColor(template.fg)) +
            getProperty("","","font",getFont(template.labelFont.font)) +
           // (k==1?getProperty("","","maximumSize",""+(f1.name.length()*(template.labelFont.font.isBold()?(template.labelFont.font.getSize()):(template.labelFont.font.getSize()))/2+30)+",100"):"") +
            (k==1?getProperty("","","minimumSize",""+(f1.name.length()*(template.labelFont.font.isBold()?(template.labelFont.font.getSize()/3*2):(template.labelFont.font.getSize()/2))+10)+",10"):"") +
            (k==1?getProperty("","","minimumSize",""+(f1.name.length()*(template.labelFont.font.isBold()?(template.labelFont.font.getSize()/3*2):(template.labelFont.font.getSize()/2))+20)+",10"):"") +
            (template.formPanel.menuCall==1?getProperty("","","mnemonic",""+getMnemonic(f1)):"")+
            closeStyle();
            r = r+
            getBehavior((f1.type!=2?getProperty(f1.compType[0].id+"compType","","visible","true")+getProperty(f1.compType[0].id+(f1.compType[0].data_layout==1?"Table":"DataPanel"),"","caller", ""+f.id)+
            getProperty(f1.compType[0].id+"LoVButton","","visible", "false"):"")+
           // getProperty(f1.compType[0].id+"compType","","modal",((Application.Menu)menu[i]).mode==0?"true":"false")+
            getProperty(f1.type==2?(f1.id+"menuFormType"):(f1.compType[0].id+"compType"),"","visible",((Application.Menu)menu[i]).mode==2?"false":"true"));
            r=r+
         //   (menu[i].subMenu!=null?getMenu(menu[i].subMenu,0):"")+
            closePart();}
        }
        return r;
    }
    public String generateCompTypeToolbar(Application.FormType f, Application.CompType cmp) { 
        String r="";
        int width=0;
        for (int i=0; i<f.compType.length;i++)
        { 
        /*
        if(f.compType[i].superord==cmp.id && f.compType[i].layout==0)
        {
            int x=(f.compType[i].name.length()*(template.labelFont.font.isBold()?template.labelFont.font.getSize()/2:(template.labelFont.font.getSize()*2/3)))+40;
            r=r+    
            openPart(cmp.id + f.compType[i].id + "ToolbarButton","","JButton") +
            openStyle() +
            getProperty("","","text", f.compType[i].name) +
            getProperty("","","preferredSize", x +",30") +
            (template.formPanel.menuCall==1?getProperty("","","mnemonic", ""+getMnemonic(f.compType[i])):"")+
            closeStyle() +
            getBehavior(getProperty(f.compType[i].id+"compType","","visible","true")+getProperty(f.compType[i].id+"SelectButton","","visible", "false"))+//getProperty(f.compType[i].id+"compType","","modal","true")) +
            closePart();
            width=width+x+2;
        }*/
            if(f.compType[i].superord==cmp.id && f.compType[i].layout==0 && template.formPanel.buttonCall==1) {
                int x=(f.compType[i].name.length()*(template.labelFont.font.isBold()?template.labelFont.font.getSize()/2:(template.labelFont.font.getSize()*2/3)))+2*template.itemPanel.item_spacing;
                r=r+    
                openPart(cmp.id + f.compType[i].id + "ToolbarButton","","JButton") +
                openStyle() +
                getProperty("","","text",f.compType[i].name) +
                getProperty("","","preferredSize", x +",30") +
                closeStyle() +
                getBehavior(getProperty(f.compType[i].id+"compType","","visible","true")+getProperty(f.compType[i].id+"LoVButton","","visible", "false")+getProperty(cmp.id+(cmp.data_layout==1?"Table":"DataPanel"),"","caller", "-1"))+//getProperty(f.compType[i].id+"compType","","modal","true")) +
                closePart();
                width=width+x+2;
            }
        }
        String ret="";
        int x=(template.formPanel.title==1?getTitleHeight():0);
        if(cmp.superord==-1)
        r=r + getTool(f, 1);
        if(!r.equals(""))
        ret =
        openPart(cmp.id+"Toolbar","","JPanel") +
        openStyle() +
        getProperty("","","opaque","false") +
        getProperty("","","bounds", template.itemPanel.spacing+","+x+","+(width + (cmp.superord==-1?getToolDimW(f, 1):0)+template.itemPanel.spacing*2)+","+getToolbarDim(cmp, f).height) +
        closeStyle() +
        r +
        closePart();
        return ret;
    }
    public String generateCompTypeToolbar(Application.FormType f) { 
        String r="";
        int width=0;
        String ret="";
        int x=template.itemPanel.spacing;
        r=r + getTool(f, 1);
        if(!r.equals(""))
        ret =
        openPart(f.id+"MenuToolbar","","JPanel") +
        openStyle() +
        getProperty("","","opaque","false") +
        getProperty("","","bounds", template.itemPanel.spacing +","+x+","+(template.itemPanel.spacing*2 + getToolDimW(f, 1))+","+getToolbarDim(f).height) +
        closeStyle() +
        r +
        closePart();
        return ret;
    }
    private int getToolDimW(Application.FormType f, int k) { 
        int width=0;
        int x=0;
        Object[] menu=f.menu.toArray();
        for (int i=0; i<menu.length;i++)
        {
            if(((Application.Menu)menu[i]).ui_position>0)
                x=app.getFormT(((Application.Menu)menu[i]).called_tf).name.length()*(template.labelFont.font.isBold()?template.labelFont.font.getSize()/2:(template.labelFont.font.getSize()*2/3))+40;
            else 
                x=0;
            width=width+x+template.itemPanel.item_spacing;
        }
        return width;
    }
    private String getTool(Application.FormType f, int k) { 
        String r="";
        Object[] menu=f.menu.toArray();
        for (int i=0; i<menu.length;i++)
        {
            Application.FormType f1=app.getFormT(((Application.Menu)menu[i]).called_tf);
            int x=(f1.name.length()*(template.labelFont.font.isBold()?template.labelFont.font.getSize()/2:(template.labelFont.font.getSize()*2/3)))+40;
            if(f1 !=null && ((Application.Menu)menu[i]).ui_position>0){
            r=r+
            openPart(f1.id + "ToolbarButton","","JButton") +
            openStyle() +
            getProperty("","","text",f1.name) +
            getProperty("","","preferredSize", x +",30") +
            closeStyle();
            r = r+
            getBehavior((f1.type!=2?getProperty(f1.compType[0].id+"compType","","visible","true")+getProperty(f1.compType[0].id+(f1.compType[0].data_layout==1?"Table":"DataPanel"),"","caller", ""+f.id)+
            getProperty(f1.compType[0].id+"LoVButton","","visible", "false"):"")+
            //getProperty(f1.compType[0].id+"compType","","modal",((Application.Menu)menu[i]).mode==0?"true":"false")+
            getProperty(f.type==2?(f1.id+"menuFormType"):(f.compType[0].id+"compType"),"","visible",((Application.Menu)menu[i]).mode==2?"false":"true"));
            r=r+
         //   (menu[i].subMenu!=null?getMenu(menu[i].subMenu,0):"")+
            closePart();}
        }
        return r;
    }
    public int getMnemonic(Application.CompType c)
    {
        char[] ch=c.name.toCharArray();
        return (int)ch[0];
    }
    public int getMnemonic(Application.FormType f)
    {
        char[] ch=f.name.toCharArray();
        return (int)ch[0];
    }
    public String generateAbout(){
        int size=template.labelFont.font.getSize();
        int width=size*25+50;
        String r=""; 
        window="About";
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
        getProperty("","","text", "IIS*CASE Integrated Information Systems Case tool Version 7.0.0") +
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
