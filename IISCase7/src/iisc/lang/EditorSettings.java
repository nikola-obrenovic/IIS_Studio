package iisc.lang;

import iisc.lang.EnvVarsPanel;
import iisc.IISFrameMain;

import iisc.ODBCList;

import java.awt.BorderLayout;

import java.awt.Color;

import java.awt.Dimension;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListDataListener;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import say.swing.JFontChooser;

public class EditorSettings extends JDialog implements TreeSelectionListener 
{
    MyTextArea area = null;
    JSourceCodeEditor editor = null;
    public boolean dirty = false;
    private JTree tree = new JTree();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel mainPanel = new JPanel();
    private JPanel propPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel smallButtonPanel = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JPanel treePanel = new JPanel();
    private JSplitPane jSplitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, propPanel);
    private JScrollPane sp1 = new JScrollPane();
    private JPanel gutterPanel = new JPanel();
    private JPanel textAreaPanel = new JPanel();
    private JPanel nativeSQLPanel = new JPanel();
    private EnvVarsPanel eVarPanel = null;
    private PropertyCheckBox showGutterChb = new PropertyCheckBox("showgutter", "Show gutter");
    private PropertyCheckBox showGutterLineNumChb = new PropertyCheckBox("view.gutter.lineNumbers", "Line numbers");
    private PropertyCheckBox shorwEOLMarkersChb = new PropertyCheckBox("view.eolMarkers", "Show EOL markers");
    private PropertyFont gutterFontPicker = new PropertyFont("view.gutter.font");
    private PropertyColorPicker gutterBgColor = new PropertyColorPicker("view.gutter.bgColor", "Background color");
    private PropertyColorPicker gutterFgColor = new PropertyColorPicker("view.gutter.fgColor", "Line number color");
    private PropertyCombo gutterNumAlign = new PropertyCombo("view.gutter.numberAlignment", "Number alignment");
    private PropertyTextBox gutterBorderWidth = new PropertyTextBox("view.gutter.borderWidth", "Border width");
    private PropertyCheckBox higlightCurrLineChb = new PropertyCheckBox("view.gutter.highlightCurrentLine", "Highlight curr. line");
    private PropertyTextBox higlightIntervalNum = new PropertyTextBox("view.gutter.highlightInterval", "Highlight interval");
    private PropertyColorPicker eolMarkerColor = new PropertyColorPicker("view.eolMarkerColor", "EOL marker color");
    private PropertyColorPicker areaBackColor = new PropertyColorPicker("view.bgColor", "Background color");
    private PropertyFont areaFontPicker = new PropertyFont("view.font");
    private PropertyCheckBox areahiglightCurrLineChb = new PropertyCheckBox("view.lineHighlight", "Highlight curr. line");
    private PropertyColorPicker areaSelLineColor = new PropertyColorPicker("view.lineHighlightColor", "Selected line color");
    private PropertyCombo sqlConnCombo = new PropertyCombo("nativesqlconn", "Connection");
    
    private JButton saveBtn = new JButton("OK");
    private JButton cancelBtn = new JButton("Cancel");
    private JButton applyBtn = new JButton("Apply");
    private JButton helpBtn = new JButton("");
    private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private Connection conn;
    private ArrayList propControls = new ArrayList();
    private Document settingsDoc = null;
    
    public EditorSettings(JSourceCodeEditor editor, Connection conn) 
    {
        super(editor, "Options", true);
        this.editor = editor;
        this.area = editor.area;
        this.conn = conn;
        LoadSettingsDoc();
        
        try 
        {
            jbInit();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    private void LoadSettingsDoc()
    {
        try 
        {
            File fXmlFile = new File("editor_settings.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            settingsDoc = dBuilder.parse(fXmlFile);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void UpdateProperty(String propName, String propValue)
    {
        try 
        {
            Element settingRoot = settingsDoc.getDocumentElement();
            
            for(int i = 0; i < settingRoot.getChildNodes().getLength(); i++)
            {
                Node propNode = settingRoot.getChildNodes().item(i);
                
                if (propNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    String tempPropName = propNode.getNodeName();
                    
                    if (propName.equalsIgnoreCase(tempPropName))
                    {
                        //propNode.setTextContent(propValue);
                        while(propNode.getChildNodes().getLength() > 0)
                        {
                            propNode.removeChild(propNode.getFirstChild());
                        }
                        Node textnode = this.settingsDoc.createTextNode(propValue);
                        propNode.appendChild(textnode);
                        JSourceCodeEditor.writeXmlFile(this.settingsDoc, "editor_settings.xml");
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
    
    private void jbInit() throws Exception {
        this.setSize(630,400);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(borderLayout1);
        mainPanel.setLayout(borderLayout2);
        mainPanel.add(jSplitPane1, BorderLayout.CENTER);
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        buttonPanel.setPreferredSize(new Dimension(600,50));
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        jSplitPane1.setDividerLocation(200);
        gutterPanel.setLayout(null);
        textAreaPanel.setLayout(null);     
        nativeSQLPanel.setLayout(null);
        treePanel.setLayout(new BorderLayout());
        treePanel.add(sp1, BorderLayout.CENTER);
        this.eVarPanel = new EnvVarsPanel(400, 400, this.conn, editor, editor.PR_id);
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Settings");
        DefaultTreeModel trModel=new DefaultTreeModel(root);
        //trModel.addTreeModelListener(this);
        tree = new JTree(trModel);
        sp1.getViewport().add(tree);
        DefaultMutableTreeNode editorNode = new DefaultMutableTreeNode("Editor");
        root.add(editorNode);        
        DefaultMutableTreeNode gutterNode = new DefaultMutableTreeNode("Gutter");
        editorNode.add(gutterNode);
        DefaultMutableTreeNode textAreaNode = new DefaultMutableTreeNode("TextArea");
        editorNode.add(textAreaNode);
        DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode("DB");
        root.add(dbNode);        
        DefaultMutableTreeNode nativeConnNode = new DefaultMutableTreeNode("Native SQL connection");
        dbNode.add(nativeConnNode);
        DefaultMutableTreeNode compilerNode = new DefaultMutableTreeNode("Compiler");
        root.add(compilerNode);        
        DefaultMutableTreeNode envVarNode = new DefaultMutableTreeNode("Environment Variables");
        compilerNode.add(envVarNode);
        tree.setFont(new Font("SansSerif", 0, 11));
        
        gutterPanel.setVisible(true);
        textAreaPanel.setVisible(false);
        nativeSQLPanel.setVisible(false);
        eVarPanel.setVisible(false);
        propPanel.setLayout(new BorderLayout());
        //gutterPanel.setBackground(Color.MAGENTA);
        propPanel.add(gutterPanel, BorderLayout.CENTER);
        propPanel.add(textAreaPanel, BorderLayout.CENTER);
        propPanel.add(nativeSQLPanel, BorderLayout.CENTER);
        propPanel.add(eVarPanel, BorderLayout.CENTER);
        applyBtn.setBounds(10, 10, 75, 30);
        saveBtn.setBounds(100, 10, 75, 30);
        cancelBtn.setBounds(190, 10, 75, 30);
        helpBtn.setBounds(280, 10, 35, 30);
        
        helpBtn.setIcon(imageHelp);
        
        saveBtn.setFont(new Font("SansSerif", 0, 11));
        cancelBtn.setFont(new Font("SansSerif", 0, 11));
        
        saveBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                Save();
                setVisible(false);
            }
        });
        
        cancelBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                //action = CANCEL;
                if (dirty)
                {
                    int opt = JOptionPane.showConfirmDialog(editor, "Do you want to save changes","", JOptionPane.YES_NO_OPTION);
                    
                    if ( opt == JOptionPane.YES_OPTION )
                    {
                        Save();
                    }
                }
                
                setVisible(false);
                
            }
        });
        
        applyBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                Save();
            }
        });
        
        applyBtn.setEnabled(false);
        saveBtn.setEnabled(false);
        
        smallButtonPanel.setLayout(null);
        smallButtonPanel.setSize(new Dimension(330,50));
        smallButtonPanel.setPreferredSize(new Dimension(330,50));
        smallButtonPanel.add(saveBtn);
        smallButtonPanel.add(cancelBtn);
        smallButtonPanel.add(helpBtn);
        smallButtonPanel.add(applyBtn);
        JPanel tempPanel = new JPanel();
        tempPanel.setPreferredSize(new Dimension(330,50));
        //this.buttonPanel.add(tempPanel, BorderLayout.CENTER);
        this.buttonPanel.add(smallButtonPanel, BorderLayout.EAST);
        
        //show gutter line num chb
        showGutterChb.setBounds(50, 20, 350, 20);
        showGutterChb.Init();        
        propControls.add(showGutterChb);
        gutterPanel.add(showGutterChb, null);
        
        //show gutter chb
        showGutterLineNumChb.setBounds(50, 50, 350, 20);
        showGutterLineNumChb.Init();        
        propControls.add(showGutterLineNumChb);
        gutterPanel.add(showGutterLineNumChb, null);
        
        //show eol markers
        shorwEOLMarkersChb.setBounds(50, 20, 350, 20);
        shorwEOLMarkersChb.Init();        
        propControls.add(shorwEOLMarkersChb);
        textAreaPanel.add(shorwEOLMarkersChb, null);
        
        //gutter font 
        gutterFontPicker.setBounds(50, 80, 350, 30);
        gutterFontPicker.Init();
        gutterPanel.add(gutterFontPicker, null);
        propControls.add(gutterFontPicker);
     
        //gutter bgcolor 
        gutterBgColor.setBounds(50, 120, 350, 20);
        gutterBgColor.Init();
        gutterPanel.add(gutterBgColor, null);
        propControls.add(gutterBgColor);
        
        //gutter fgcolor 
        gutterFgColor.setBounds(50, 150, 350, 20);
        gutterFgColor.Init();
        gutterPanel.add(gutterFgColor, null);
        propControls.add(gutterFgColor);
        
        //gutter numm align 
        gutterNumAlign.setBounds(50, 180, 350, 20);
        ArrayList numAllignItems = new ArrayList();
        numAllignItems.add("left");
        numAllignItems.add("center");
        numAllignItems.add("right");
        gutterNumAlign.Init(numAllignItems);
        gutterPanel.add(gutterNumAlign, null);
        propControls.add(gutterNumAlign);
        
        //border width
        gutterBorderWidth.setBounds(50, 210, 350, 20);
        gutterBorderWidth.Init();
        gutterPanel.add(gutterBorderWidth, null);
        propControls.add(gutterBorderWidth);
        
        //gutter highlight curr line 
        higlightCurrLineChb.setBounds(50, 240, 350, 20);
        higlightCurrLineChb.Init();
        gutterPanel.add(higlightCurrLineChb, null);
        propControls.add(higlightCurrLineChb);
        
        //gutter highlight interval
        higlightIntervalNum.setBounds(50, 270, 350, 20);
        higlightIntervalNum.Init();
        gutterPanel.add(higlightIntervalNum, null);
        propControls.add(higlightIntervalNum);
        
        //eolmarker color
        eolMarkerColor.setBounds(50, 50, 350, 20);
        eolMarkerColor.Init();        
        propControls.add(eolMarkerColor);
        textAreaPanel.add(eolMarkerColor, null);
         
        //area back color
        areaBackColor.setBounds(50, 80, 350, 20);
        areaBackColor.Init();        
        propControls.add(areaBackColor);
        textAreaPanel.add(areaBackColor, null);
        
        //area font
        areaFontPicker.setBounds(50, 110, 350, 30);
        areaFontPicker.Init();        
        propControls.add(areaFontPicker);
        textAreaPanel.add(areaFontPicker, null);
        
        //highlight curr line
        areahiglightCurrLineChb.setBounds(50, 150, 350, 20);
        areahiglightCurrLineChb.Init();        
        propControls.add(areahiglightCurrLineChb);
        textAreaPanel.add(areahiglightCurrLineChb, null);
        
        //Curr line color
        areaSelLineColor.setBounds(50, 180, 350, 20);
        areaSelLineColor.Init();        
        propControls.add(areaSelLineColor);
        //textAreaPanel.add(areaSelLineColor, null);
        
         sqlConnCombo.setBounds(50, 20, 350, 20);
         ArrayList conns = new ArrayList();
         ODBCList o=new ODBCList();
         conns.addAll(o.odbc);
         sqlConnCombo.Init(conns);
         nativeSQLPanel.add(sqlConnCombo, null);
         propControls.add(sqlConnCombo);
         
        tree.expandPath(new TreePath(root.getPath()));
        tree.expandPath(new TreePath(editorNode.getPath()));      
        tree.addTreeSelectionListener(this);        
        tree.setSelectionPath(new TreePath(gutterNode.getPath()));
    }
    
    public void Save()
    {
        try 
        {
            for(int i = 0; i < propControls.size(); i++)
            {
                if (propControls.get(i) instanceof PropertyCheckBox)
                {
                    PropertyCheckBox item = (PropertyCheckBox)propControls.get(i);
                    item.Update();
                }
                else
                {
                    if (propControls.get(i) instanceof PropertyFont)
                    {
                        PropertyFont item = (PropertyFont)propControls.get(i);
                        item.Update();
                    }
                    else
                    {
                        if (propControls.get(i) instanceof PropertyColorPicker)
                        {
                            PropertyColorPicker item = (PropertyColorPicker)propControls.get(i);
                            item.Update();
                        }
                        else
                        {
                            if (propControls.get(i) instanceof PropertyCombo)
                            {
                                PropertyCombo item = (PropertyCombo)propControls.get(i);
                                item.Update();
                            }
                            else
                            {
                                if (propControls.get(i) instanceof PropertyTextBox)
                                {
                                    PropertyTextBox item = (PropertyTextBox)propControls.get(i);
                                    item.Update();
                                }
                            }
                        }
                    }
                }                
            }
            
            dirty = false;
            this.saveBtn.setEnabled(false);
            this.applyBtn.setEnabled(false);
            this.area.Reinit();
        }
        catch(Exception e)
        {}
    }
    
    private class PropertyTextBox extends JPanel implements KeyListener
    {
        public String propName;
        public String propValue;
        public ArrayList items = null;
        boolean isDirty = false;
        private JLabel label = new JLabel("");
        private JTextField field = new JTextField();
        
        public PropertyTextBox(String propName, String text)
        {   
            super();
            this.propName = propName; 
            this.setLayout(null);
            this.setSize(350, 20);
            this.label.setBounds(0,0,100,20);
            this.label.setText(text);
            this.label.setFont(new Font("SansSerif", 0, 11));
            this.add(this.label);
            
            this.field.setBounds(100,0,250,20);
            this.field.setFont(new Font("SansSerif", 0, 11));
            this.add(this.field);      
        }
        
        public void Init()
        {
            String propValue = area.getProp(propName);
            this.field.setText(propValue);
            
            this.field.addKeyListener(this);
        }
        
        public void Update()
        {   
            try 
            {
                boolean can = true;
                
                try 
                {
                    int temp = Integer.parseInt(this.field.getText());
                }
                catch(Exception ex)
                {
                    can = false;
                }
                
                if (can)
                {
                    this.propValue = field.getText();
                    area.setProp(propName, this.propValue);
                
                    if (!isDirty)
                    {
                        return;
                    }
                    
                    /*Statement stmt = conn.createStatement();
                    stmt.execute("update IISC_LANG_EDITOR_SETTINGS set PropValue = '" + propValue + "'" + " Where PropName='" + this.propName + "'");*/
                    UpdateProperty(propName, this.propValue);
                }
            }
            catch(Exception e)
            {
            
            }
        }
        
        public String getPropertyName()
        {
            return this.propName;
        }


        public void keyTyped(KeyEvent e) 
        {
            String old_PropValue = this.propValue;
            
            boolean can = true;
            
            try 
            {
                int temp = Integer.parseInt(this.field.getText());
            }
            catch(Exception ex)
            {
                can = false;
            }
            
            if (can)
            {
                this.propValue = field.getText();
                isDirty = true;
                dirty = true;
                applyBtn.setEnabled(true);
                saveBtn.setEnabled(true);
            }
            else
            {
                field.setText(this.propValue);
            }
        }

        public void keyPressed(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
        }
    }
    
    private class PropertyCombo extends JPanel implements ActionListener
    {
        public String propName;
        public String propValue;
        public ArrayList items = null;
        boolean isDirty = false;
        private JLabel label = new JLabel("");
        private JComboBox combo = new JComboBox();
        
        public PropertyCombo(String propName, String text)
        {   
            super();
            this.propName = propName; 
            this.setLayout(null);
            this.setSize(350, 20);
            this.label.setBounds(0,0,100,20);
            this.label.setText(text);
            this.label.setFont(new Font("SansSerif", 0, 11));
            this.add(this.label);
            
            this.combo.setBounds(100,0,250,20);
            this.combo.setFont(new Font("SansSerif", 0, 11));
            this.add(this.combo);      
        }
        
        public void Init(ArrayList items)
        {
            String propValue = area.getProp(propName);
            
            if (propValue != "" && propValue != null)
            {
                for(int i = 0; i < items.size(); i++)
                {
                    this.combo.addItem(items.get(i));
                }
                
                this.combo.setSelectedItem(propValue);
                combo.addActionListener(this);
            }
        }
        
        public void Update()
        {   
            try 
            {
                area.setProp(propName, this.propValue);
                
                if (!isDirty)
                {
                    return;
                }
                
                /*Statement stmt = conn.createStatement();
                stmt.execute("update IISC_LANG_EDITOR_SETTINGS set PropValue = '" + propValue + "'" + " Where PropName='" + this.propName + "'");*/
                 UpdateProperty(propName, this.propValue);
            }
            catch(Exception e)
            {
            
            }
        }
        
        public String getPropertyName()
        {
            return this.propName;
        }

        public void actionPerformed(ActionEvent e) 
        {
            propValue = this.combo.getSelectedItem().toString();
            isDirty = true;
            dirty = true;
            applyBtn.setEnabled(true);
            saveBtn.setEnabled(true);
        }
    }
    
    public void valueChanged(TreeSelectionEvent e)
    {
        //System.out.println(e.getPath().getLastPathComponent().toString());
        
        if (e.getPath().getLastPathComponent() != null)
        {
            if (e.getPath().getLastPathComponent().toString().equals("Gutter"))
            {
                //propPanel.removeAll();
                textAreaPanel.setVisible(false);
                gutterPanel.setVisible(true);
                nativeSQLPanel.setVisible(false);
                eVarPanel.setVisible(false);
                propPanel.add(gutterPanel, BorderLayout.CENTER);
                gutterPanel.repaint();
                propPanel.repaint();
                jSplitPane1.repaint();
                this.repaint();
            }
            
            if (e.getPath().getLastPathComponent().toString().equals("TextArea"))
            {
                //propPanel.removeAll();
                gutterPanel.setVisible(false);
                textAreaPanel.setVisible(true);
                nativeSQLPanel.setVisible(false);
                eVarPanel.setVisible(false);
                propPanel.add(textAreaPanel, BorderLayout.CENTER);
                textAreaPanel.repaint();
                propPanel.repaint();
                this.repaint();
            }
            
            if (e.getPath().getLastPathComponent().toString().equals("Native SQL connection"))
            {
                //propPanel.removeAll();
                gutterPanel.setVisible(false);
                textAreaPanel.setVisible(false);
                nativeSQLPanel.setVisible(true);
                eVarPanel.setVisible(false);
                propPanel.add(nativeSQLPanel, BorderLayout.CENTER);
                textAreaPanel.repaint();
                propPanel.repaint();
                this.repaint();
            }
            
            if (e.getPath().getLastPathComponent().toString().equals("Environment Variables"))
            {
                //propPanel.removeAll();
                gutterPanel.setVisible(false);
                textAreaPanel.setVisible(false);
                nativeSQLPanel.setVisible(false);
                eVarPanel.setVisible(true);
                propPanel.add(eVarPanel, BorderLayout.CENTER);
                textAreaPanel.repaint();
                propPanel.repaint();
                this.repaint();
            }
        }
    }
    
    private class PropertyColorPicker extends JPanel implements MouseListener
    {
        public String propName;
        public String propValue;
        public Color c;
        boolean isDirty = false;
        private JLabel label = new JLabel("");
        private JPanel biggerPannel = new JPanel();
        private JPanel smallerPannel = new JPanel();
        
        public PropertyColorPicker(String propName, String text)
        {   
            super();
            this.propName = propName; 
            this.setLayout(null);
            this.setSize(350, 30);
            this.label.setBounds(0,0,250,20);
            this.label.setText(text);
            this.label.setFont(new Font("SansSerif", 0, 11));
            this.add(this.label);
            
            biggerPannel.setLayout(null);
            biggerPannel.setBorder(new LineBorder(Color.BLACK,1));
            biggerPannel.setBounds(195, 0, 50, 20);
            
            this.add(this.biggerPannel);
            
            smallerPannel.setBounds(3, 3, 44, 14);
            smallerPannel.setBorder(new LineBorder(Color.BLACK,1));
            biggerPannel.add(smallerPannel);            
        }
        
        public void Init()
        {
            String propValue = area.getProp(propName);
            //this.btn.addActionListener(this);
            if (propValue != "" && propValue != null)
            {
                String colorStr = propValue.substring(1);
                
                try 
                {
                    int r = this.ParseHex(colorStr.substring(0,2));
                    int g = this.ParseHex(colorStr.substring(2,4));
                    int b = this.ParseHex(colorStr.substring(4,6));
                    
                    this.c = new Color(r, g, b);
                    
                    this.smallerPannel.setBackground(this.c);
                    
                    this.smallerPannel.addMouseListener(this);
                    this.biggerPannel.addMouseListener(this);
                }
                catch(Exception e)
                {
                }
            }
        }
        
        public int ParseHex(String num)
        {
            int suma = 0;
            
            for(int i = 0; i < num.length();i++)
            {
                char c = num.charAt(i);
                int digit = 0;
                
                if (c <= '9' && c >= '0')
                {
                    digit = c - '0';
                }
                else
                {
                    if (c <= 'F' && c >= 'A')
                    {
                        digit = 10 + c - 'A';
                    }
                    else
                    {
                        if (c <= 'f' && c >= 'a')
                        {
                            digit = 10 + c - 'a';
                        }
                    }
                }
                suma = suma * 16 + digit;
            }
            return suma;
        }
        
        public void Update()
        {   
            try 
            {
                area.setProp(propName, this.propValue);
                
                if (!isDirty)
                {
                    return;
                }
                
                /*Statement stmt = conn.createStatement();
                stmt.execute("update IISC_LANG_EDITOR_SETTINGS set PropValue = '" + propValue + "'" + " Where PropName='" + this.propName + "'");*/
                 UpdateProperty(propName, this.propValue);
            }
            catch(Exception e)
            {
            
            }
        }
        
        public String getPropertyName()
        {
            return this.propName;
        }

        public void mouseClicked(MouseEvent e) 
        {
            JColorChooser cc = new JColorChooser();
            
            Color newColor = cc.showDialog(this, "Choose color", this.c);
            
            if(newColor != null)
            {
                this.c = newColor;
                
                isDirty = true;
                dirty = true;
                
                applyBtn.setEnabled(true);
                saveBtn.setEnabled(true);
                
                this.smallerPannel.setBackground(this.c);
                
                this.propValue = "#" ;
                
                String temp = Integer.toHexString(this.c.getRed());
                
                if (temp.length() == 1)
                {
                    temp = "0" + temp;
                }
                
                propValue += temp;
                
                temp = Integer.toHexString(this.c.getGreen());
                                
                if (temp.length() == 1)
                {
                    temp = "0" + temp;
                }
                
                propValue += temp;
                                
                temp = Integer.toHexString(this.c.getBlue());
                                
                if (temp.length() == 1)
                {
                    temp = "0" + temp;
                }
                
                propValue += temp;
            }
            else
            {
            }
        }

        public void mousePressed(MouseEvent e)
        { 
            biggerPannel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        }
        
        public void mouseReleased(MouseEvent e)
        {
            biggerPannel.setBorder(BorderFactory.createEmptyBorder());
        }
        
        public void mouseEntered(MouseEvent e)
        {
            biggerPannel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }
        
        public void mouseExited(MouseEvent e)
        {
            //biggerPannel.setBorder(BorderFactory.createEmptyBorder());
             biggerPannel.setBorder(new LineBorder(Color.BLACK,1));
        }
    }
    
    private class PropertyFont extends JPanel implements ActionListener
    {
        public String propName;
        boolean isDirty = false;
        public Font font = new Font("Tahoma", Font.PLAIN, 10);
        private JLabel label = new JLabel("Font");
        private JButton btn = new JButton("");
        
        public PropertyFont(String propName)
        {   
            super();
            this.propName = propName; 
            this.setLayout(null);
            this.setSize(350, 30);
            this.label.setBounds(0,0,50,30);
            this.label.setFont(new Font("SansSerif", 0, 11));
            this.add(this.label);
            
            this.btn.setBounds(100,0,250,30);
            this.add(this.btn);
        }
        
        public void Init()
        {
            String fontName = area.getProp(propName);
            int fontStyle = 0;
            int fontSize = 10;
            
            try 
            {
                fontStyle = Integer.parseInt(area.getProp(propName+"style")); 
            }
            catch(Exception e)
            {}
            
            try 
            {
                fontSize = Integer.parseInt(area.getProp(propName+"size")); 
            }
            catch(Exception e)
            {}
            
            try 
            {
                this.font = new Font(fontName, fontStyle, fontSize);
                this.btn.setFont(this.font);
                this.btn.setText(fontName + " " + fontSize);
                
            }
            catch(Exception e)
            {}
            
            this.btn.addActionListener(this);
        }
        
        public void Update()
        {   
            try 
            {
                area.setProp(propName, this.font.getName());
                area.setProp(propName+"style", Integer.toString(this.font.getStyle()));
                area.setProp(propName+"size", Integer.toString(this.font.getSize()));
                
                if (!isDirty)
                {
                    return;
                }
                
               /* Statement stmt = conn.createStatement();
                stmt.execute("update IISC_LANG_EDITOR_SETTINGS set PropValue = '" + this.font.getName() + "'" + " Where PropName='" + this.propName + "'");
                stmt.execute("update IISC_LANG_EDITOR_SETTINGS set PropValue = '" + this.font.getStyle() + "'" + " Where PropName='" + (this.propName+"style") + "'");
                stmt.execute("update IISC_LANG_EDITOR_SETTINGS set PropValue = '" + this.font.getSize() + "'" + " Where PropName='" + (this.propName+"size") + "'");*/
                UpdateProperty(propName, this.font.getName());
                UpdateProperty(this.propName+"style", ""+this.font.getStyle());
                UpdateProperty(this.propName+"size", ""+this.font.getSize());
            }
            catch(Exception e)
            {
            
            }
        }
        
        public String getPropertyName()
        {
            return this.propName;
        }

        public void actionPerformed(ActionEvent e) 
        {
            JFontChooser fc = new JFontChooser();
            
            fc.setFont(new Font("SansSerif", 0, 11));
            fc.setSelectedFont(this.font);
            int result = fc.showDialog(this);
            
            if (result == 0)
            {
                this.font = fc.getSelectedFont();
                this.btn.setFont(this.font);
                this.btn.setText(this.font.getName() + " " + this.font.getSize());
                isDirty = true;
                dirty = true;
                
                applyBtn.setEnabled(true);
                saveBtn.setEnabled(true);
            }
        }
    }
    
    private class PropertyCheckBox extends JPanel implements ChangeListener
    {
        public String propName;
        boolean isDirty = false;
        private JLabel label = new JLabel("");
        private JCheckBox box = new JCheckBox("");
        
        public PropertyCheckBox(String propName, String text)
        {   
            this.propName = propName;  
            this.setLayout(null);
            this.setSize(350, 20);
            this.label.setBounds(0,0,100,20);
            this.label.setText(text);
            this.label.setFont(new Font("SansSerif", 0, 11));
            this.add(this.label);
            
            this.box.setBounds(210,0,20,20);
            this.add(this.box);
        }
        
        public void Init()
        {
            String propValue = area.getProp(propName);
            
            if (propValue != null)
            {
                if (propValue.equals("true"))
                {
                    box.setSelected(true);
                }
                else
                {
                    box.setSelected(false);
                }
                
            }
            box.addChangeListener(this);
        }
        
        public void Update()
        {   
            try 
            {
                String propValue = "";
                
                if (box.isSelected())
                {
                    propValue = "true";
                }
                else
                {
                    propValue = "false";
                }
                
                area.setProp(propName, propValue);
                
                if (!isDirty)
                {
                    return;
                }
                
                /*Statement stmt = conn.createStatement();
                stmt.execute("update IISC_LANG_EDITOR_SETTINGS set PropValue = '" + propValue + "'" + " Where PropName='" + this.propName + "'");*/
                UpdateProperty(propName, propValue);
            }
            catch(Exception e)
            {
            
            }
            
            box.addChangeListener(this);
        }
        
        public String getPropertyName()
        {
            return this.propName;
        }
        
        public void stateChanged(ChangeEvent e)
        {
            //System.out.println(this.propName + "  " +  this.isSelected());
            isDirty = true;
            dirty = true;
            applyBtn.setEnabled(true);
            saveBtn.setEnabled(true);
        }
    }
}
