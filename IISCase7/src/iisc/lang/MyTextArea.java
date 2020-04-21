package iisc.lang;

import iisc.IISFrameMain;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.IPropertyManager;
import org.gjt.sp.jedit.Marker;
import org.gjt.sp.jedit.Mode;
import org.gjt.sp.jedit.buffer.BufferListener;
import org.gjt.sp.jedit.buffer.JEditBuffer;
import org.gjt.sp.jedit.syntax.ModeProvider;
import org.gjt.sp.jedit.textarea.Selection;
import org.gjt.sp.jedit.textarea.StandaloneTextArea;
import org.gjt.sp.jedit.textarea.TextArea;
import org.gjt.sp.jedit.textarea.TextAreaExtension;
import org.gjt.sp.jedit.textarea.TextAreaPainter;
import org.gjt.sp.util.IOUtilities;
import org.gjt.sp.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class MyTextArea extends StandaloneTextArea implements KeyListener,FocusListener, AutoCompletionMenuDelegate, CaretListener, MouseListener
{ 
    
    
    static IIISCasePropManager propManager = new IIISCasePropManager();
    public Connection conn = null;
    private String currLineColor = "";
    private ArrayList domains;
    private ArrayList userDefDom;
    private AutoCompletionMenu intelli = null;
    public JSourceCodeEditor editor = null;
    public boolean isIntellShown = false;
    public JFrame frame;
    private ImageIcon diskIcon = new ImageIcon(IISFrameMain.class.getResource("icons/disk.png"));
    private ImagePanel tmpPanel = new ImagePanel(diskIcon, 3,3);
    private int prevCurrPosition;
    private int processEvent = -1; 
    int currVarPos = 0;
    int currEndVarPos = 0;
    int currFuncPos = 0;
    int currEndFuncPos = 0;
    public int intelliContext = -1; //0 - arg, 1 - returns, 2 - variable, 3 - code 
    public ArrayList commands= new ArrayList();
    private Hashtable keyWords = new Hashtable();
    Rectangle rect = null;
    JPopupMenu popup = null;
    private ImageIcon undoIcon = new ImageIcon(IISFrameMain.class.getResource("icons/arrow_undo.png"));
    private ImageIcon redoIcon = new ImageIcon(IISFrameMain.class.getResource("icons/arrow_redo.png"));
    private ImageIcon cutIcon = new ImageIcon(IISFrameMain.class.getResource("icons/cut2.png"));
    private ImageIcon copyIcon = new ImageIcon(IISFrameMain.class.getResource("icons/page_copy.png"));
    private ImageIcon pasteIcon = new ImageIcon(IISFrameMain.class.getResource("icons/page_paste.png"));    
    private ImageIcon closelIcon = new ImageIcon(IISFrameMain.class.getResource("icons/close.png"));    
    private int paintNum = 0;
    
    public MyTextArea(Connection conn, ArrayList domains, ArrayList userDefDom, JSourceCodeEditor editor, JFrame frame) 
    { 
        //super(null);      
        super(propManager);     
        enableEvents(AWTEvent.FOCUS_EVENT_MASK | AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK);
        this.conn = conn;
        this.domains = domains;
        this.userDefDom = userDefDom;
        this.editor = editor;
        this.frame = frame;
        
        Mode mode = new Mode("iisc");
        mode.setProperty("file", "modes/iisc.xml"); 
        mode.init();
        
        ModeProvider modeProvider = ModeProvider.instance;
        modeProvider.removeAll();
        modeProvider.addMode(mode);
        modeProvider.loadMode(mode);
                    
        this.getBuffer().setMode(mode);          
        LoadProps();
        this.currLineColor = propManager.getProperty("view.lineHighlightColor");
        this.addFocusListener(this);
        this.addKeyListener(this);  
        this.addCaretListener(this);
        this.addMouseListener(this);
        this.frame = frame;
        
        this.InitCommands();
        intelli = new AutoCompletionMenu(this, this, frame, this.editor);
        this.CreateEditorPopupMenu();       
        this.getPainter().addExtension(new MyTextAreaExt(this));
    } 
    
    public void mouseExited(MouseEvent me)
    {}
    
    public void mouseEntered(MouseEvent me)
    {}
    
    public void mouseReleased(MouseEvent me)
    {}
    
    public void mousePressed(MouseEvent me)
    {}
    
    public void mouseClicked(MouseEvent me)
    {
        doMouseClicked(me);
    }
    
    public void ExecIndent(boolean ind)
    {
        if (this.isEnabled() && this.isEditable())
        {
            String text = this.getSelectedText();
            
            if (text == null || text.equals(""))
            {
                int line = this.getCaretLine();
                int lines[] = new int[1];
                lines[0] = line;
                
                if (ind)
                {
                    this.getBuffer().shiftIndentLeft(lines);
                }
                else
                {
                    this.getBuffer().shiftIndentRight(lines);
                }
            }
            else
            {
                Selection[] sel = this.getSelection();
                
                if(sel != null && sel.length > 0)
                {
                    for(int i = 0; i < sel.length; i++)
                    {
                        int count = sel[i].getEndLine() - sel[i].getStartLine() + 1; 
                        int lines[] = new int[count];
                        
                        for(int k = 0; k < count; k++)
                        {
                            lines[ k ] =  sel[i].getStartLine() + k;
                        }
                        
                         if (ind)
                         {
                             this.getBuffer().shiftIndentLeft(lines);
                         }
                         else
                         {
                             this.getBuffer().shiftIndentRight(lines);
                         }
                    }
                }
            }
        }
    }
    
    public void ExecComment()
    {
        if (this.isEnabled() && this.isEditable())
        {
            String text = this.getSelectedText();
           
            if (text == null || text.equals(""))
            {
                int line = this.getCaretLine();
                int offset = this.getLineStartOffset(line);
                
                if (offset >= 0 && offset < this.getBufferLength())
                {                  
                    this.getBuffer().insert(offset, "//");
                }
            }
            else
            {
                Selection[] sel = this.getSelection();
                
                if(sel != null && sel.length > 0)
                {
                    if (sel[0].getEnd() >= 0 && sel[0].getEnd()  < this.getBufferLength() && sel[0].getStart() >= 0 && sel[0].getStart()  < this.getBufferLength())
                    {
                        this.getBuffer().insert(sel[0].getEnd(), "*/");
                        this.getBuffer().insert(sel[0].getStart(), "/*");
                        //this.getBuffer().remove(sel[i].getStart(), sel[i].getEnd() - sel[i].getStart());
                    }
                }
            }
        }
    }
    
     public void ExecUncomment()
     {
         if (this.isEnabled() && this.isEditable())
         {
             String text = this.getSelectedText();
            
             if (text == null || text.equals(""))
             {
                 int line = this.getCaretLine();
                 int offset = this.getLineStartOffset(line);
                 
                 if (offset >= 0 && offset < this.getBufferLength() - 1)
                 {                  
                     if (this.getBuffer().getText(offset, 2).equals("//"))
                     {
                         this.getBuffer().remove(offset, 2);
                     }
                 }
             }
             else
             {
                 Selection[] sel = this.getSelection();
                 
                 if(sel != null && sel.length > 0)
                 {
                     if (sel[0].getEnd() >= 0 && sel[0].getEnd()  < this.getBufferLength() - 1  && sel[0].getStart() >= 0 && sel[0].getStart()  < this.getBufferLength() - 1)
                     {
                         if (this.getBuffer().getText(sel[0].getStart(), 2).equals("/*") && this.getBuffer().getText(sel[0].getEnd()-2, 2).equals("*/"))
                         {
                             this.getBuffer().remove(sel[0].getEnd() - 2, 2);
                             this.getBuffer().remove(sel[0].getStart(), 2);
                         }
                     }
                 }
             }
         }
     }
                         
    public void ExecCut()
    {
        //this.getBuffer().
        try 
        {
         Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
         
         if (this.isEnabled() && this.isEditable() && content.isDataFlavorSupported(DataFlavor.stringFlavor))
         {
            String text = this.getSelectedText();
            StringSelection data = new StringSelection(text);
            Clipboard clipboard = getToolkit().getSystemClipboard();
            clipboard.setContents(data, data);
            
             org.gjt.sp.jedit.textarea.Selection[] sel = this.getSelection();
             
             for(int i = 0; i < sel.length; i++)
             {
                 if (sel[i].getStart() >= 0 && sel[i].getEnd()  < this.getBufferLength())
                 {
                     this.getBuffer().remove(sel[i].getStart(), sel[i].getEnd() - sel[i].getStart());
                 }
             }
         }
        }
        catch(Exception e)
        {
        }
    }
    
    public void ExecCopy()
    {
        try 
        {
         Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
         
         if (content.isDataFlavorSupported(DataFlavor.stringFlavor))
         {
            //String text = (String)content.getTransferData(DataFlavor.stringFlavor);            
            String text = this.getSelectedText();
            StringSelection data = new StringSelection(text);
             Clipboard clipboard = getToolkit().getSystemClipboard();
            clipboard.setContents(data, data);            
         }
        }
        catch(Exception e)
        {
        }
    }
    
    public void ExecPaste()
    {
        try 
        {
         Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
         
         if (this.isEnabled() && this.isEditable() && content.isDataFlavorSupported(DataFlavor.stringFlavor))
         {
            String text = (String)content.getTransferData(DataFlavor.stringFlavor);            
            //System.out.println(text + "\n" + this.getCaretPosition());
            if (this.getCaretPosition() >= 0 && this.getCaretPosition() < this.getBufferLength())
            {
                this.getBuffer().insert(this.getCaretPosition(), text);
            }
            else
            {
                this.getBuffer().insert(0, text);
            }
         }
        }
        catch(Exception e)
        {
        }
    }
    
    private void CreateEditorPopupMenu()
    {
        popup = new JPopupMenu();
        
        JMenuItem cutItem = new JMenuItem("Cut");
        
        cutItem.addActionListener(new ActionListener()
        {    
            public void actionPerformed(ActionEvent e) 
            {
                ExecCut();
            }
        });
        cutItem.setIcon(cutIcon);
        
        JMenuItem copyItem = new JMenuItem("Copy");
        
        copyItem.addActionListener(new ActionListener()
        {    
            public void actionPerformed(ActionEvent e) 
            {
                ExecCopy();
            }
        });
        copyItem.setIcon(copyIcon);
        
        JMenuItem pasteItem = new JMenuItem("Paste");
        
        pasteItem.addActionListener(new ActionListener()
        {    
            public void actionPerformed(ActionEvent e) 
            {
                ExecPaste();
            }
        });
        pasteItem.setIcon(pasteIcon);
        
        popup.add(cutItem);
        popup.add(copyItem);
        popup.add(pasteItem);
        
        popup.addSeparator();
    }
    
    public void doMouseClicked(MouseEvent me) 
    {
        try 
        {
            if (this.editor.isDebug)
            {
                return;
            }
            
            if((me.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
            {
                popup.show(this, me.getX()+10, me.getY());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void InitCommands()
    {
        commands.add("if ... then else end_if;");
        commands.add("while ... begin end_while;");
        commands.add("for ... begin end_for;");
        commands.add("repeat ... end_repeat;");
        commands.add("switch ... end_switch;");
        commands.add("return Expr;");
        commands.add("break;");
        commands.add("select into ...;");
        commands.add("fetch ...;");
        commands.add("update ...;");
        
        this.keyWords.put("begin", "");
        this.keyWords.put("then", "");
        this.keyWords.put("else", "");
        this.keyWords.put("default", "");
        this.keyWords.put("repeat", "");
    }
    
    /*@Override
    public void paintImmediately(int x, int y, int w, int h) 
    {
        this.paintDebugLine();
        super.paintImmediately(x, y, w, h);
       
        //super.repaint(x, y, w, h);
        
         System.out.println("paintImmediately(x,y)" + this.paintNum);
         this.paintNum = this.paintNum + 1;
    }
    
    @Override
    public void paintImmediately(Rectangle rec) 
    {
        this.paintDebugLine();
        super.paintImmediately(rec);
        
        System.out.println("paintImmediately " + this.paintNum);
        this.paintNum = this.paintNum + 1;
       
    }
         
    @Override
    public void repaint()
    {
        this.paintDebugLine();
        super.repaint();       
        
        System.out.println("jes repaint" + this.paintNum);
        this.paintNum = this.paintNum + 1;
    }
    
    @Override
    public void paint(Graphics g)
    {
        //paintBreakPoints(g, true);      
        super.paint(g); 
        //paintBreakPoints(g, true);      
    }*/
    
    /*public void paintAll(Graphics g)
    {
        if (this.editor.isDebug)
        {   
            paintDebugLine(g);
        }
        //paintBreakPoints(g, false);       
        
        System.out.println("paintall " + this.paintNum);
        this.paintNum = this.paintNum + 1;        
        super.paintAll(g); 
        
        if (this.editor.isDebug)
        {   
            paintDebugLine(g);
        }
    }*/
    
    public void focusGained(FocusEvent e)
    {
        /*if (this.editor.isDebug)
        {
            this.repaint();
            return;
        }*/
        
        String temp = propManager.getProperty("view.lineHighlightColor");
        
        if ( temp != null && !temp.equals(this.currLineColor))
        {
            propManager.setProperty("view.lineHighlightColor", this.currLineColor);
            this.Reinit();
        }
        
        if (isIntellShown)
        {
        }
    }
    
    public void focusLost(FocusEvent e)
    {
    }
    
    public void caretUpdate(CaretEvent e) 
    {
        //this.repaint();
        //this.paintDebugLine();
        //RepaintDebugLine();
        //this.paintBreakPoints(this.getGraphics(), false);
    }
    
    /*private void RepaintDebugLine()
    {
        if (this.editor.isDebug)
        {
            Rectangle rect = this.getDebugLineRect();
            
            if(rect != null)
            {
                repaint(rect);
            }
        }
    }
    
    public void paintDebugLine()
    {
        if (this.editor != null && this.editor.isDebug)
        {
            paintDebugLine(this.getGraphics());
        }
    }*/
    
    public Rectangle getDebugLineRect(int lineHeight)
    {
        try 
        {
            int currLine = this.editor.vMashine.getCurrentLine() - 1;
            int offset = this.getLineStartOffset(currLine-1);
            int offset2 = this.getLineStartOffset(currLine);
            Point temp = this.offsetToXY(offset);
            Point temp2 = this.offsetToXY(offset2);
            int gWidth = this.getGutter().getWidth();
            
            if (temp2 == null)
            {
                return null;
            }
            int w = (int)this.getVisibleRect().getWidth() - gWidth - 16;
            
            if (temp == null) 
            {
                Rectangle rect = new Rectangle((int)temp2.getX(), (int)temp2.getY(), w, lineHeight);
                return rect;    
            }
            else
            {
                Rectangle rect = new Rectangle((int)temp2.getX(), (int)temp2.getY(), w, (int)(temp2.getY() - temp.getY()));
                return rect;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public void paintBreakPoints(Graphics gpx, int lineHeight)
    {
        if (gpx== null)
        {
            return;
        }
        
        Iterator enum1 = this.editor.getBreakPoints().values().iterator();
        Graphics2D g = (Graphics2D)gpx.create();
        
        g.setXORMode(Color.LIGHT_GRAY);
        g.setColor(new Color(255, 230, 225)); 
        
        if (g == null)
        {
            return;
        } 
        
        int count = 0;
        
        while(enum1.hasNext()) 
        {
            try 
            {
                int currLine = ((Integer)enum1.next()).intValue();
            
                if (currLine <= 0)
                {
                    return;
                }
                    
                int offset = this.getLineStartOffset(currLine-1);
                int offset2 = this.getLineStartOffset(currLine);
                Point temp = this.offsetToXY(offset);
                Point temp2 = this.offsetToXY(offset2);
                int gWidth = this.getGutter().getWidth();
                
                if (temp2 == null)
                {
                    continue;
                }     
                
                //Rectangle rect = new Rectangle((int)temp2.getX() + gWidth, (int)temp2.getY(), (int)(temp2.getY() - temp.getY()), (int)(temp2.getY() - temp.getY()));
                if (temp == null)
                {
                    int w = (int)this.getVisibleRect().getWidth() - gWidth - 16;
                    
                    //System.out.println("temp ej null(" + (int)temp2.getX() + "," + (int)temp2.getY() + "," + w + "," + lineHeight + "\n");
                    g.fillRect((int)temp2.getX(), (int)temp2.getY(), w, lineHeight);   
                }
                else
                {
                    int w = (int)this.getVisibleRect().getWidth() - gWidth - 16;
                    g.fillRect((int)temp2.getX(), (int)temp2.getY(), w, (int)(temp2.getY() - temp.getY()));
                }
                
                count = count + 1;
            }
            catch(Exception e)
            {}
        }
        //System.out.println("broj" + count);
        g.dispose();
    }
    
    public void paintDebugLine(Graphics pg, int lineHeight)
    {        
        try 
        {            
            Rectangle rect = this.getDebugLineRect(lineHeight);
            
            if (rect == null || pg == null)
            {
                return;
            } 
            
            Graphics2D g = (Graphics2D)pg.create();
            
            if (rect == null || g == null)
            {
                return;
            } 
            
            g.setXORMode(this.getBackground());
            g.setColor(Color.ORANGE);
            
            g.fillRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
            //g.setClip((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
            g.dispose();
            //repaint((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
     @Override
     public void processKeyEvent(KeyEvent evt)
     {
        int keyCode = evt.getKeyCode();
        
        if (keyCode != 0) 
        {
             this.processEvent = this.processEvent * (-1);
        }
         
        if(!intelli.isVisible())
        {
            super.processKeyEvent(evt);
        }
        else
        {
            if (keyCode != KeyEvent.VK_DOWN && keyCode != KeyEvent.VK_UP && keyCode != KeyEvent.VK_ENTER && keyCode != KeyEvent.VK_PAGE_DOWN && keyCode != KeyEvent.VK_PAGE_UP)
            {
                    super.processKeyEvent(evt);    
                    
                    if(this.processEvent == 1)
                    {
                        this.intelli.keyAdapter.keyPressed(evt);
                    }
            }
            else
            {
                if(this.processEvent == 1)
                {
                    if (keyCode == KeyEvent.VK_DOWN)
                    {
                        this.intelli.move(1);
                    }
                    else
                    {
                        if (keyCode == KeyEvent.VK_UP)
                        {
                            this.intelli.move(-1);
                        }
                        else
                        {
                            if (keyCode == KeyEvent.VK_PAGE_DOWN)
                            {
                                this.intelli.move(5);
                            }
                            else
                            {
                                if (keyCode == KeyEvent.VK_PAGE_UP)
                                {
                                    this.intelli.move(-5);
                                }
                                else
                                {
                                    this.intelli.keyAdapter.keyPressed(evt);
                                }
                            }
                        }
                    }
                }
            }
        }
         
     }
            
    @Override
    public void processMouseEvent(MouseEvent me)
    {
        
        if((me.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
        {
            popup.show(this, me.getX()+10, me.getY());
        }
        else
        {
            super.processMouseEvent(me);
        }
    }
    
    /*@Override
    public void processPaintEvent()
    {
        System.out.println("pevent");
        this.processPaintEvent();
        paintBreakPoints(this.getGraphics(), false);
    }*/
    public void keyReleased(KeyEvent e)
    {
        if (this.intelli.isVisible())
        {
            //if (AutoCompletionMenu.isCharIdentifier(e.getKeyChar()) || e.getKeyChar() == ' ')
            {
                this.intelli.updateAutoCompleteList();
            }
        }
    }
    
    public void keyPressed(KeyEvent e)
    {
        if (!this.intelli.isVisible())
        {
            if(e.getKeyChar() == ' ')
            {
                if(e.isControlDown())
                {
                     ShowIntelli();
                }
            }
        }
    }
    
    public void keyTyped(KeyEvent e)
    {
        
    }
    
    private void LoadProps()
    {
        try 
        {
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
                    
                    this.setProp(propName, propValue);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void Reinit()
    {
        this.propertiesChanged();
    }
    
    public void setProp(String propName, String propValue)
    {
        propManager.setProperty(propName, propValue);
        
        if (propName != null && propName.equals("showgutter"))
        {
            if (propValue.equals("true"))
            {
                this.getGutter().setVisible(true);
            }
            else
            {
                this.getGutter().setVisible(false);
            }
        }
    }
    
    public String getProp(String propName)
    {
        return propManager.getProperty(propName);
    }
    
    public void setCurrLineColor(String value)
    {
        propManager.setProperty("view.lineHighlightColor", value);
    }
    
    public void setCurrentLineRed()
    {
        if (this.editor.isDebug)
        {
            return;
        }
        
        String temp = propManager.getProperty("view.lineHighlightColor");
        
        if (temp == null || temp.equals("#ff0000"))
        {
            return;
        }
        this.currLineColor = temp;
        propManager.setProperty("view.lineHighlightColor", "#ff0000");
        this.Reinit();
    }
    
    public void SetOldLineColor()
    {
        propManager.setProperty("view.lineHighlightColor", this.currLineColor);
        this.Reinit();
    }
    
    /*public void actionPerformed(ActionEvent e)
    {
        System.out.println("out");
    }*/

    
    public void ShowIntelli()
    {
        if (this.editor.isDebug)
        {
            return;
        }
        this.intelliContext = -1;
        this.FindCodeBorders();        
        this.intelli.display();
        this.isIntellShown = true;
    }
    
    public int FindEndVarPos()
    {
        //valja napisati kod koji ce da nadje gdje se nalazi end_var
        return 0;
    }
    
    public void HideIntelli()
    {
        /*this.intelli.setVisible(false);
        this.isIntellShown = false;*/
    }
    
    public void setFont(String value)
    {
        propManager.setProperty("view.font", value);
    }
    
    public String getFnt()
    {
        return propManager.getProperty("view.font");
    }
    
    public void setLineNumbersVisible(String value)
    {
        propManager.setProperty("view.gutter.lineNumbers", value);
    }
    
    public String getLineNumbersVisible()
    {
        return propManager.getProperty("view.gutter.lineNumbers");
    }
    
    public void setEolMarkersVisible(String value)
    {
        propManager.setProperty("view.eolMarkers", value);
    }
    
    public String getEolMarkersVisible()
    {
        return propManager.getProperty("view.eolMarkers");
    }

    private List<String> GetDomains(String pattern)
    {
        ArrayList list = new ArrayList();
        pattern = pattern.toLowerCase(Locale.US);
        
        for(int i = 0; i < this.editor.domains.size(); i++)
        {
            DomainDesc dDesc = (DomainDesc)this.editor.domains.get(i);
            
            if (pattern == null || pattern.equals(""))    
            {   
                list.add("P:" + dDesc.getName());    
            }
            else
            {
                if (dDesc.getName().toLowerCase(Locale.US).startsWith(pattern))
                {
                    list.add("P:" + dDesc.getName());
                }
            }
        }
        
        for(int i = 0; i < this.editor.userDefDomains.size(); i++)
        {
            DomainDesc dDesc = (DomainDesc)this.editor.userDefDomains.get(i);
            
            if (pattern == null || pattern.equals(""))    
            {   
                list.add("U:" + dDesc.getName());    
            }
            else
            {
                if (dDesc.getName().toLowerCase(Locale.US).startsWith(pattern))
                {
                    list.add("U:" + dDesc.getName());
                }
            }
        }
        
        return list;
    }
    
    private List<String> GetUserDefDomains(String pattern)
    {
        ArrayList list = new ArrayList();
        pattern = pattern.toLowerCase(Locale.US);
        
        for(int i = 0; i < this.editor.userDefDomains.size(); i++)
        {
            DomainDesc dDesc = (DomainDesc)this.editor.userDefDomains.get(i);
            
            if (pattern == null || pattern.equals(""))    
            {   
                list.add("U:" + dDesc.getName());    
            }
            else
            {
                if (dDesc.getName().toLowerCase(Locale.US).startsWith(pattern))
                {
                    list.add("U:" + dDesc.getName());
                }
            }
        }
        
        return list;
    }
    
    private void GetHashTableElems(String pattern, String prefix, ArrayList list, Hashtable table)
    {
        pattern = pattern.toLowerCase(Locale.US);
        
        if (prefix.equals("F:"))
        {
            Iterator iter = table.values().iterator();
            
            while(iter.hasNext())
            {
                FunctionDesc func = (FunctionDesc)iter.next();
                
                String str = func.getName().toLowerCase(Locale.US);
                
                if (pattern == null || pattern.equals(""))    
                {   
                    list.add(prefix + func.getName());    
                }
                else
                {
                    if (str != null && str.startsWith(pattern))
                    {
                        list.add(prefix + func.getName());    
                    }
                }
            }
        }
        else
        {
            if (prefix.equals("E:") || prefix.equals("T:") || prefix.equals("J:"))
            {
                Enumeration iter = table.keys();
                
                while(iter.hasMoreElements())
                {
                    String str = (String)iter.nextElement();
                    
                    if (pattern == null || pattern.equals(""))    
                    {   
                        list.add(prefix + str);    
                    }
                    else
                    {
                        if (str != null && str.startsWith(pattern))
                        {
                            list.add(prefix + str);    
                        }
                    }
                }
            }
            else
            {
                Iterator iter = table.values().iterator();
                Node varNodes = null;
                
                while(iter.hasNext())
                {
                    Node varNode = (Node)iter.next();
                    
                    if (varNode != null && varNode.getParentNode() != null)
                    {
                        varNodes = varNode.getParentNode();
                        break;
                    }
                }
                
                if (varNodes != null)
                {
                    for(int i = 0; i < varNodes.getChildNodes().getLength(); i++)
                    {
                        Node varNode = varNodes.getChildNodes().item(i);
                        
                        if (varNode != null && varNode.getFirstChild() != null && varNode.getFirstChild().getNodeName() != null && varNode.getFirstChild().getNodeName().equals("NAME"))
                        {
                            Node nameNode = varNode.getFirstChild();
                            boolean can = true; 
                            
                            try 
                            {
                                if (prefix.equals("I:"))
                                {
                                    Node typeNode = varNode.getChildNodes().item(1);
                                    String name = typeNode.getFirstChild().getNodeName();
                                    
                                    if (!name.equalsIgnoreCase("iterator"))
                                    {
                                        can = false;
                                    }
                                }                                
                            }
                            catch(Exception e)
                            {
                            }
                            
                            if (can)
                            {
                                for(int j = 0; j < nameNode.getChildNodes().getLength(); j++)
                                {
                                    Node varNameNode = nameNode.getChildNodes().item(j);
                                    
                                    if (varNameNode.getNodeName() != null)
                                    {
                                        String name = varNameNode.getNodeName();
                                        String str = name.toLowerCase(Locale.US);
                                        
                                        if (pattern == null || pattern.equals(""))    
                                        {   
                                            list.add(prefix + name);    
                                        }
                                        else
                                        {
                                            if (str != null && str.startsWith(pattern))
                                            {
                                                list.add(prefix + name);    
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void InsertFunction(String command, int insertionStartIndex, int insertionEndIndex)
    {
        command = command.substring(2).toLowerCase(Locale.US);
        
        FunctionDesc desc = null;
        
        if (this.editor.getPredefinedFunctions().containsKey(command))
        {
            desc = (FunctionDesc)this.editor.getPredefinedFunctions().get(command);
        }
        else
        {
            if (this.editor.getFunctions().containsKey(command))
            {
                desc = (FunctionDesc)this.editor.getFunctions().get(command);
            }
        }
        
        if (desc != null)
        {
            String result = desc.getName() + "(";
            
            for(int i = 0; i < desc.getParams().size(); i++)
            {
                ParamDesc pdesc = (ParamDesc)desc.getParams().get(i);
                
                if ( i > 0 )
                {
                    result += ",";
                }
                result += pdesc.getName();
            }
            result += ")";
            
            getBuffer().remove(insertionStartIndex, insertionEndIndex-insertionStartIndex);
            getBuffer().insert(insertionStartIndex, result);
        }
    }
    
    public void InsertCommand(String command, int insertionStartIndex, int insertionEndIndex)
    {
        int i = insertionStartIndex - 1;
        String input = this.getText();
        
        char c = 'A';
        
        while (i >= 0)
        {
            c = input.charAt(i);
            
            if (c == ' ' || c == '\t')
            {
                i = i - 1;
            }
            else
            {
                break;
            }
        }
        
        String whiteText = "";
        boolean newLine = false;
        
        if ( i < insertionStartIndex - 1)
        {
            whiteText = input.substring(i + 1, insertionStartIndex);
            
            if ( c == '\r' || c == '\n')
            {
                newLine = true;
            }
        }
        else
        {
            if ( c == '\r' || c == '\n')
            {
                newLine = true;
            }
        }
        
        String insertionText = "";
        
        if (command.startsWith("C:if"))
        {
            insertionText = "if Cond1 then";
            
            if (!newLine)
            {
                insertionText += " else end_if;";
            }
            else
            {
                insertionText += "\n" + whiteText + "else" + "\n" + whiteText + "end_if;";
            }
        }
        else
        {
            if (command.startsWith("C:while"))
            {
                insertionText = "while Cond1 begin";
                
                if (!newLine)
                {
                    insertionText += " end_while;";
                }
                else
                {
                    insertionText += "\n" + whiteText + "end_while;";
                }
            }
            else
            {
                if (command.startsWith("C:for"))
                {
                    insertionText = "for Var1 := Expr1 to Expr2 ";
                    
                    if (!newLine)
                    {
                        insertionText += " begin end_for;";
                    }
                    else
                    {
                        insertionText += "\n" + whiteText + "begin" + "\n" + whiteText + "end_for;";
                    }
                }
                else
                {
                    if (command.startsWith("C:repeat"))
                    {
                        insertionText = "repeat";
                        
                        if (!newLine)
                        {
                            insertionText += " until Cond1 end_repeat;";
                        }
                        else
                        {
                            insertionText += "\n" + whiteText + "until Cond1 end_repeat;";
                        }
                    }
                    else
                    {
                        if (command.startsWith("C:switch"))
                        {
                            insertionText = "switch Expr";
                            
                            if (!newLine)
                            {
                                insertionText += " case Expr1: end_case case Expr2: end_case default end_switch;";
                            }
                            else
                            {
                                insertionText += "\n" + whiteText + "case Expr1:" + "\n" + whiteText + "end_case";
                                insertionText += "\n" + whiteText + "case Expr2:" + "\n" + whiteText + "end_case";
                                insertionText += "\n" + whiteText + "default" + "\n" + whiteText + "end_switch;";
                            }
                        }
                        else
                        {
                            if (command.startsWith("C:return"))
                            {
                                insertionText = "return Expr;";
                            }
                            else
                            {
                                if (command.startsWith("C:break"))
                                {
                                    insertionText = "break;";
                                }
                                else
                                {
                                    if (command.startsWith("C:select"))
                                    {
                                        insertionText = "select sum into Var1 from Var2;";
                                    }   
                                    else
                                    {
                                        if (command.startsWith("C:update"))
                                        {
                                            insertionText = "update ";
                                        }
                                        else
                                        {
                                            insertionText = "fetch iterName into Var1;";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if (insertionText != "")
        {   
            getBuffer().remove(insertionStartIndex, insertionEndIndex-insertionStartIndex);
            getBuffer().insert(insertionStartIndex, insertionText);
        }
        //getTextComponent().
    }
    
    private List<String> GetVarsOrKeyWords(String pattern)
    {
        ArrayList list = new ArrayList();
        pattern = pattern.toLowerCase(Locale.US);
        
        for(int i = 0; i < this.commands.size(); i++)
        {
            String command = (String)this.commands.get(i);
            
            if (pattern == null || pattern.equals(""))    
            {   
                list.add("C:" + command);    
            }
            else
            {
                if (command.startsWith(pattern))
                {
                    list.add("C:" + command);
                }
            }
        }
        
        this.GetHashTableElems(pattern, "V:", list, this.editor.getVariables());
        this.GetHashTableElems(pattern, "V:", list, this.editor.getArgs());
        this.GetHashTableElems(pattern, "E:", list, this.editor.getEnvVariables());
        return list;
    }
    
    private void GetVarMembers(String pattern, String varName, ArrayList list)
    {
        pattern = pattern.toLowerCase(Locale.US);
        
        Hashtable vars = this.editor.getVariables();
        Hashtable args = this.editor.getArgs();
        varName = varName.toLowerCase(Locale.US);
        
        Element varNode = null;
        
        if(vars.containsKey(varName))
        {
            varNode = (Element)vars.get(varName);
        }
        else
        {
            if(args.containsKey(varName))
            {
                varNode = (Element)args.get(varName);
            }
        }        
        
        if (varNode == null)
        {
            return;
        }
        
        Element typeNode = null;
        NodeList nodelist = (varNode).getElementsByTagName("TYPE");
        DomainDesc desc = null; 
        
        if (nodelist.getLength() > 0)
        {
            typeNode = (Element)nodelist.item(0);
            
            if (typeNode != null && typeNode.getFirstChild() != null)
            {
                String typeName = typeNode.getFirstChild().getNodeName();
                
                if (typeName != null)
                {
                    typeName = typeName.toLowerCase(Locale.US);
                    
                    if (this.editor.getUserDefDomains().containsKey(typeName))
                    {
                        desc = (DomainDesc)this.editor.getUserDefDomains().get(typeName);
                    }
                }
            }
        }
        
        if (desc != null)
        {
            if (desc.getType() == DomainDesc.TUPPLE || desc.getType() == DomainDesc.CHOICE)
            {
                for(int i = 0; i < desc.getMembers().size(); i++)
                {
                    AttributeDesc temp = (AttributeDesc)desc.getMembers().get(i);
                    String attName = temp.getName().toLowerCase(Locale.US);
                    
                    if (pattern == null || pattern.equals(""))    
                    {   
                        list.add("V:" + temp.getName());    
                    }
                    else
                    {
                        if (attName.startsWith(pattern))
                        {
                            list.add("V:" + temp.getName());
                        }
                    }
                }
            }
        }
        
        return;
    }
    
    public void FindCodeBorders()
    {
        String input = this.getText();
        IntPos pos = new IntPos(0);
        pos.value = 0;
        char aar[] = input.toCharArray();
        
        Token tok = null;
        
        while(true)
        {
            tok = this.GetNextToken(input, pos);
            
            if (tok.type == Token.EOF)
            {
                break;
            }
            
            if (tok.type == Token.INTERPUNCTION)
            {
                if (input.charAt(tok.begin) == '(')
                {
                    break;
                }
            }
        }
        
        if (tok.type == Token.EOF)
        {
            return;
        }
        
        this.currFuncPos = tok.begin;
        
        while(true)
        {
            tok = this.GetNextToken(input, pos);
            
            if (tok.type == Token.EOF)
            {
                break;
            }
            
            if (tok.type == Token.INTERPUNCTION)
            {
                if (input.charAt(tok.begin) == ')')
                {
                    break;
                }
            }
        }
        
        if (tok.type == Token.EOF)
        {
            return;
        }
        
        this.currEndFuncPos = tok.begin;
        
        while(true)
        {
            tok = this.GetNextToken(input, pos);
            
            if (tok.type == Token.EOF)
            {
                break;
            }
            
            if (tok.type == Token.IDENT)
            {
                tok.text = input.substring(tok.begin, tok.end + 1).toLowerCase(Locale.US);
                
                if (tok.text != null && tok.text.equals("var"))
                {
                    break;
                }
            }
        }
        
        if (tok.type == Token.EOF)
        {
            return;
        }
        
        this.currVarPos = tok.begin;
        
        while(true)
        {
            tok = this.GetNextToken(input, pos);
            
            if (tok.type == Token.EOF)
            {
                break;
            }
            
            if (tok.type == Token.IDENT)
            {
                tok.text = input.substring(tok.begin, tok.end + 1).toLowerCase(Locale.US);
                
                if (tok.text != null && tok.text.equals("end_var"))
                {
                    break;
                }
            }
        }
        
        if (tok.type == Token.EOF)
        {
            return;
        }
        
        this.currEndVarPos = tok.begin;
        
        int currPos = this.getCaretPosition();
        
        if (currPos >= this.currFuncPos && currPos <= this.currEndFuncPos)
        {
            this.intelliContext = 0;
        }
        else
        {
            if (currPos >= this.currEndFuncPos && currPos <= this.currVarPos)
            {
                this.intelliContext = 1;
            }
            else
            {
                if (currPos >= this.currVarPos && currPos <= this.currEndVarPos)
                {
                    this.intelliContext = 2;
                }
                else
                {
                    this.intelliContext = 3;
                }
            }
        }
    }
    
    public List<String> autoCompleteVarDecl()
    {
        ArrayList list = new ArrayList();
        
        IntPos currPos = new IntPos(this.getCaretPosition() - 1);
        int startIndex = currPos.value;
        
        String input = this.getText().toLowerCase(Locale.US);
        
        Token tok = this.GetPrevToken(input, currPos);
        
        if (tok.type != Token.IDENT)
        {
            return list;
        }
        
        tok.text = input.substring(tok.begin, tok.end+1);
        
        if (( tok.type == Token.IDENT && tok.text.equals("of")) && !this.isCharIdentifier(input.charAt(startIndex)))
        {
            this.intelli.setInsertionStartIndex(startIndex + 1);
            this.intelli.setInsertionEndIndex(this.getCaretPosition());
            this.intelli.partialWord = "";
            return this.GetDomains("");
        }
        
        int temp_start = tok.begin;
        
        tok = this.GetPrevToken(input, currPos);
                        
        if (tok.type == Token.EOF)
        {
            return list;
        }
        tok.text = input.substring(tok.begin, tok.end+1);
        
        if (( tok.type == Token.INTERPUNCTION && tok.text.equals(";")) || ( tok.type == Token.IDENT && tok.text.equals("var")) || (tok.type == Token.IDENT && tok.text.equals("of")))
        {
            if ((tok.type == Token.IDENT && tok.text.equals("of")))
            {
                this.intelli.setInsertionStartIndex(temp_start);
                this.intelli.setInsertionEndIndex(this.getCaretPosition());
                this.intelli.partialWord = input.substring(temp_start, this.getCaretPosition());
                return this.GetDomains(this.intelli.partialWord);
            }
            else
            {
                if (this.isCharIdentifier(input.charAt(startIndex)))
                {
                    return list;
                }
                
                this.intelli.setInsertionStartIndex(startIndex + 1);
                this.intelli.setInsertionEndIndex(this.getCaretPosition());
                this.intelli.partialWord = "";
                return this.GetDomains("");
            }
        }
        
        if (tok.type != Token.IDENT)
        {
            return list;
        }
        
        startIndex = temp_start;
        
        tok = this.GetPrevToken(input, currPos);
        
        if (tok.type == Token.EOF)
        {
            return list;
        }
        tok.text = input.substring(tok.begin, tok.end+1);
        
        if (( tok.type == Token.INTERPUNCTION && tok.text.equals(";")) || ( tok.type == Token.IDENT && tok.text.equals("var")) )
        {
            this.intelli.setInsertionStartIndex(startIndex);
            this.intelli.setInsertionEndIndex(this.getCaretPosition());
            this.intelli.partialWord = input.substring(startIndex, this.getCaretPosition());
            return this.GetDomains(this.intelli.partialWord);
        }
        
        return list;
    }
    
    public List<String> autoCompleteReturns()
    {
        ArrayList list = new ArrayList();
        
        IntPos currPos = new IntPos(this.getCaretPosition() - 1);
        int startIndex = currPos.value;
        
        String input = this.getText().toLowerCase(Locale.US);
        
        Token tok = this.GetPrevToken(input, currPos);
        
        if (tok.type != Token.IDENT)
        {
            return list;
        }
        
        tok.text = input.substring(tok.begin, tok.end+1);
        
        if (( tok.type == Token.IDENT && tok.text.equals("returns")) && !this.isCharIdentifier(input.charAt(startIndex)))
        {
            this.intelli.setInsertionStartIndex(startIndex + 1);
            this.intelli.setInsertionEndIndex(this.getCaretPosition());
            this.intelli.partialWord = "";
            return this.GetDomains("");
        }
        
        int temp_start = tok.begin;
        
        tok = this.GetPrevToken(input, currPos);
                        
        if (tok.type == Token.EOF)
        {
            return list;
        }
        tok.text = input.substring(tok.begin, tok.end+1);
        
        if ((tok.type == Token.IDENT && tok.text.equals("returns")))
        {
                this.intelli.setInsertionStartIndex(temp_start);
                this.intelli.setInsertionEndIndex(this.getCaretPosition());
                this.intelli.partialWord = input.substring(temp_start, this.getCaretPosition());
                return this.GetDomains(this.intelli.partialWord);
        }
        
        return list;
    }
    
    public List<String> autoCompleteArgDecl()
    {
        ArrayList list = new ArrayList();
        
        IntPos currPos = new IntPos(this.getCaretPosition() - 1);
        int startIndex = currPos.value;
        
        String input = this.getText().toLowerCase(Locale.US);
        
        Token tok = this.GetPrevToken(input, currPos);
        
        if (tok.type != Token.IDENT)
        {
            return list;
        }

        tok.text = input.substring(tok.begin, tok.end+1);
        
        if (tok.text.equals("in") || tok.text.equals("out") || tok.text.equals("inout"))
        {
            if (this.isCharIdentifier(input.charAt(startIndex)))
            {
                return list;
            }
            
            this.intelli.setInsertionStartIndex(startIndex + 1);
            this.intelli.setInsertionEndIndex(this.getCaretPosition());
            this.intelli.partialWord = "";
            return this.GetUserDefDomains("");
        }
        
        int temp_start = tok.begin;
        
        tok = this.GetPrevToken(input, currPos);
                        
        if (tok.type == Token.EOF)
        {
            return list;
        }
        tok.text = input.substring(tok.begin, tok.end+1);
        
        if (( tok.type == Token.INTERPUNCTION && tok.text.equals("(")) || ( tok.type == Token.INTERPUNCTION && tok.text.equals(",")))
        {
            if (this.isCharIdentifier(input.charAt(startIndex)))
            {
                return list;
            }
            
            this.intelli.setInsertionStartIndex(startIndex + 1);
            this.intelli.setInsertionEndIndex(this.getCaretPosition());
            this.intelli.partialWord = "";
            return this.GetUserDefDomains("");
        }
        
        if (tok.type != Token.IDENT)
        {
            return list;
        }
        
        if (tok.text.equals("in") || tok.text.equals("out") || tok.text.equals("inout"))
        {
            if (!this.isCharIdentifier(input.charAt(startIndex)))
            {
                return list;
            }
            
            this.intelli.setInsertionStartIndex(temp_start);
            this.intelli.setInsertionEndIndex(this.getCaretPosition());
            this.intelli.partialWord = input.substring(temp_start, this.getCaretPosition());
            return this.GetUserDefDomains(this.intelli.partialWord);
        }
        
        startIndex = temp_start;
        
        
        tok = this.GetPrevToken(input, currPos);
        
        if (tok.type == Token.EOF)
        {
            return list;
        }
        tok.text = input.substring(tok.begin, tok.end+1);
        
        if (( tok.type == Token.INTERPUNCTION && tok.text.equals("(")) || ( tok.type == Token.INTERPUNCTION && tok.text.equals(",")))
        {
            this.intelli.setInsertionStartIndex(startIndex);
            this.intelli.setInsertionEndIndex(this.getCaretPosition());
            this.intelli.partialWord = input.substring(startIndex, this.getCaretPosition());
            return this.GetUserDefDomains(this.intelli.partialWord);
        }
        
        return list;
    }
    
    public List<String> autoComplete()
    {
        ArrayList list = new ArrayList();
        
        IntPos currPos = new IntPos(this.getCaretPosition() - 1);
        int startIndex = currPos.value;
        
        String input = this.getText().toLowerCase(Locale.US);
        
        Token tok = this.GetPrevToken(input, currPos);
        Token tok1 = this.GetPrevToken(input, currPos);
        Token tok2 = this.GetPrevToken(input, currPos);
        
        if (tok.type == Token.EOF)
        {
            return list;
        }
        
        //
        
        if (tok.type == Token.INTERPUNCTION && (input.charAt(tok.begin) == ';' || input.charAt(tok.begin) == ':'))
        {
            this.intelli.setInsertionStartIndex(startIndex + 1);
            this.intelli.setInsertionEndIndex(this.getCaretPosition());
            this.intelli.partialWord = "";
            return this.GetVarsOrKeyWords("");
        }
        
        if (tok.type == Token.INTERPUNCTION && input.charAt(tok.begin) == '.')
        {
            this.intelli.setInsertionStartIndex(startIndex + 1);
            this.intelli.setInsertionEndIndex(this.getCaretPosition());
            this.intelli.partialWord = "";
            
            if (tok1.type == Token.IDENT)
            {
                tok1.text = input.substring(tok1.begin, tok1.end+1);
                this.GetVarMembers(this.intelli.partialWord, tok1.text, list);
            }
            return list;
        }
        
        if (tok.type == Token.INTERPUNCTION)
        {
            this.intelli.setInsertionStartIndex(startIndex + 1);
            this.intelli.setInsertionEndIndex(this.getCaretPosition());
            this.intelli.partialWord = "";
            
            this.GetHashTableElems(this.intelli.partialWord, "V:", list, this.editor.getVariables());
            this.GetHashTableElems(this.intelli.partialWord, "V:", list, this.editor.getArgs());
            this.GetHashTableElems(this.intelli.partialWord, "E:", list, this.editor.getEnvVariables());
            this.GetHashTableElems(this.intelli.partialWord, "F:", list, this.editor.getFunctions());
            this.GetHashTableElems(this.intelli.partialWord, "F:", list, this.editor.getPredefinedFunctions());
            return list;
        }
        
        if (tok.type == Token.IDENT)
        {
            tok.text = input.substring(tok.begin, tok.end+1);
            
            if (this.keyWords.containsKey(tok.text))
            {
                if (this.isCharIdentifier(input.charAt(startIndex)))
                {
                    return list;
                }
                
                this.intelli.setInsertionStartIndex(startIndex + 1);
                this.intelli.setInsertionEndIndex(this.getCaretPosition());
                this.intelli.partialWord = "";
                return this.GetVarsOrKeyWords("");
            }
            
            if (tok.text.toLowerCase(Locale.US).equals("update"))
            {
                if (this.isCharIdentifier(input.charAt(startIndex)))
                {
                    return list;
                }
                
                this.intelli.setInsertionStartIndex(startIndex + 1);
                this.intelli.setInsertionEndIndex(this.getCaretPosition());
                this.intelli.partialWord = "";
                this.GetHashTableElems("", "T:", list, this.editor.getCompTypes());
                return list;
            }
            
            if (tok.text.toLowerCase(Locale.US).equals("fetch"))
            {
                if (this.isCharIdentifier(input.charAt(startIndex)))
                {
                    return list;
                }
                
                this.intelli.setInsertionStartIndex(startIndex + 1);
                this.intelli.setInsertionEndIndex(this.getCaretPosition());
                this.intelli.partialWord = "";
                this.GetHashTableElems(this.intelli.partialWord, "I:", list, this.editor.getVariables());
                return list;
            }
            
            if (tok.text.toLowerCase(Locale.US).equals("into"))
            {
                if (this.isCharIdentifier(input.charAt(startIndex)))
                {
                    return list;
                }
                
                this.intelli.setInsertionStartIndex(startIndex + 1);
                this.intelli.setInsertionEndIndex(this.getCaretPosition());
                this.intelli.partialWord = "";
                this.GetHashTableElems(this.intelli.partialWord, "V:", list, this.editor.getVariables());
                return list;
            }
            
            if (tok1.type == Token.INTERPUNCTION && (input.charAt(tok1.begin) == ';' || input.charAt(tok1.begin) == ':'))
            {
                if (startIndex > tok.end)
                {
                    this.intelli.setInsertionStartIndex(startIndex + 1);
                    this.intelli.setInsertionEndIndex(this.getCaretPosition());
                    this.intelli.partialWord = "";
                    
                    this.GetHashTableElems(this.intelli.partialWord, "V:", list, this.editor.getVariables());
                    this.GetHashTableElems(this.intelli.partialWord, "V:", list, this.editor.getArgs());
                    this.GetHashTableElems(this.intelli.partialWord, "E:", list, this.editor.getEnvVariables());
                    this.GetHashTableElems(this.intelli.partialWord, "F:", list, this.editor.getFunctions());
                    this.GetHashTableElems(this.intelli.partialWord, "F:", list, this.editor.getPredefinedFunctions());
                    return list;
                }
                else
                {
                    this.intelli.setInsertionStartIndex(tok.begin);
                    this.intelli.setInsertionEndIndex(this.getCaretPosition());
                    this.intelli.partialWord = tok.text;
                    return this.GetVarsOrKeyWords(tok.text);
                }
            }
            
            if (tok1.type == Token.INTERPUNCTION && input.charAt(tok1.begin) == '.')
            {
                if (startIndex > tok.end)
                {
                    this.intelli.setInsertionStartIndex(startIndex + 1);
                    this.intelli.setInsertionEndIndex(this.getCaretPosition());
                    this.intelli.partialWord = "";
                    
                    this.GetHashTableElems(this.intelli.partialWord, "V:", list, this.editor.getVariables());
                    this.GetHashTableElems(this.intelli.partialWord, "V:", list, this.editor.getArgs());
                    this.GetHashTableElems(this.intelli.partialWord, "E:", list, this.editor.getEnvVariables());
                    this.GetHashTableElems(this.intelli.partialWord, "F:", list, this.editor.getFunctions());
                    this.GetHashTableElems(this.intelli.partialWord, "F:", list, this.editor.getPredefinedFunctions());
                    return list;
                }
                else
                {
                    this.intelli.setInsertionStartIndex(tok.begin);
                    this.intelli.setInsertionEndIndex(this.getCaretPosition());
                    this.intelli.partialWord = tok.text;
                    
                    if (tok2.type == Token.IDENT)
                    {
                        tok2.text = input.substring(tok2.begin, tok2.end+1);
                        this.GetVarMembers(this.intelli.partialWord, tok2.text, list);
                    }
                    return list;
                }
            }
            
            if (tok1.type == Token.IDENT)
            {
                tok1.text = input.substring(tok1.begin, tok1.end+1);
                
                if (keyWords.containsKey(tok1.text) && startIndex <= tok.end) 
                {
                    this.intelli.setInsertionStartIndex(tok.begin);
                    this.intelli.setInsertionEndIndex(this.getCaretPosition());
                    this.intelli.partialWord = tok.text;
                    return this.GetVarsOrKeyWords(tok.text);
                }
                
                if(tok1.text.toLowerCase(Locale.US).equals("update"))
                {
                    if (this.isCharIdentifier(input.charAt(startIndex)))
                    {
                        this.intelli.setInsertionStartIndex(tok.begin);
                        this.intelli.setInsertionEndIndex(this.getCaretPosition());
                        this.intelli.partialWord = tok.text;
                        this.GetHashTableElems(tok.text, "T:", list, this.editor.getCompTypes());
                        return list;
                    }
                    else
                    {
                        this.intelli.setInsertionStartIndex(this.getCaretPosition());
                        this.intelli.setInsertionEndIndex(this.getCaretPosition());
                        this.intelli.partialWord = "";
                        this.GetHashTableElems("", "J:", list, this.editor.getComponentTypesCommands());
                        return list;
                    }
                }
                
                if(tok1.text.toLowerCase(Locale.US).equals("fetch"))
                {
                    if (this.isCharIdentifier(input.charAt(startIndex)))
                    {
                        this.intelli.setInsertionStartIndex(tok.begin);
                        this.intelli.setInsertionEndIndex(this.getCaretPosition());
                        this.intelli.partialWord = tok.text;
                        this.GetHashTableElems(tok.text, "I:", list, this.editor.getVariables());
                        return list;
                    }
                    else
                    {
                        this.intelli.setInsertionStartIndex(this.getCaretPosition());
                        this.intelli.setInsertionEndIndex(this.getCaretPosition());
                        this.intelli.partialWord = "";
                        this.GetHashTableElems("", "V:", list, this.editor.getIteratorVariables());
                        return list;
                    }
                }
                
                if(tok1.text.toLowerCase(Locale.US).equals("into"))
                {
                    if (this.isCharIdentifier(input.charAt(startIndex)))
                    {
                        this.intelli.setInsertionStartIndex(tok.begin);
                        this.intelli.setInsertionEndIndex(this.getCaretPosition());
                        this.intelli.partialWord = tok.text;
                        this.GetHashTableElems(tok.text, "V:", list, this.editor.getVariables());
                        return list;
                    }
                    else
                    {
                        this.intelli.setInsertionStartIndex(this.getCaretPosition());
                        this.intelli.setInsertionEndIndex(this.getCaretPosition());
                        this.intelli.partialWord = "";
                        this.GetHashTableElems("", "V:", list, this.editor.getVariables());
                        return list;
                    }
                }
                
                if (tok2.type == Token.IDENT)
                {
                    tok2.text = input.substring(tok2.begin, tok2.end+1);
                    
                    this.intelli.setInsertionStartIndex(tok.begin);
                    this.intelli.setInsertionEndIndex(this.getCaretPosition());
                    this.intelli.partialWord = tok.text;
                    this.GetHashTableElems(tok.text, "J:", list, this.editor.getComponentTypesCommands());
                    return list;
                }
            }
            
            if ( tok1.type == Token.INTERPUNCTION || tok1.type == Token.IDENT)
            {
                if (startIndex > tok.end)
                {
                    this.intelli.setInsertionStartIndex(startIndex + 1);
                    this.intelli.setInsertionEndIndex(this.getCaretPosition());
                    this.intelli.partialWord = "";
                }
                else
                {
                    this.intelli.setInsertionStartIndex(tok.begin);
                    this.intelli.setInsertionEndIndex(this.getCaretPosition());
                    this.intelli.partialWord = tok.text;
                }                
                
                this.GetHashTableElems(this.intelli.partialWord, "V:", list, this.editor.getVariables());
                this.GetHashTableElems(this.intelli.partialWord, "V:", list, this.editor.getArgs());
                this.GetHashTableElems(this.intelli.partialWord, "E:", list, this.editor.getEnvVariables());
                this.GetHashTableElems(this.intelli.partialWord, "F:", list, this.editor.getFunctions());
                this.GetHashTableElems(this.intelli.partialWord, "F:", list, this.editor.getPredefinedFunctions());
                
                return list;
            }
        }
        /*int temp_start = tok.begin;
        
        tok = this.GetPrevToken(input, currPos);
                        
        if (tok.type == Token.EOF)
        {
            return list;
        }
        tok.text = input.substring(tok.begin, tok.end+1);
        
        if ((tok.type == Token.IDENT && tok.text.equals("returns")))
        {
                this.intelli.setInsertionStartIndex(temp_start);
                this.intelli.setInsertionEndIndex(this.getCaretPosition());
                this.intelli.partialWord = input.substring(temp_start, this.getCaretPosition());
                return this.GetDomains(this.intelli.partialWord);
        }*/
        
        return list;
    }
    
    public List<String> autoCompletionMenuGetMatchingWordsForPartialWord(String partialWord) 
    {
        ArrayList list = new ArrayList();
        
        IntPos currPos = new IntPos(this.getCaretPosition() - 1);
        int startIndex = currPos.value;
        
        if(this.intelliContext == 0)
        {
            //Variable 
            return this.autoCompleteArgDecl();
        }
        
        if(this.intelliContext == 1)
        {
            //Variable 
            return this.autoCompleteReturns();
        }
        
        if(this.intelliContext == 2)
        {
            //Variable 
            return this.autoCompleteVarDecl();
        }
        
        if(this.intelliContext == 3)
        {
            //Variable 
            return this.autoComplete();
        }
        
        return list;
    }

    public void autoCompletionMenuWillDisplay() 
    {
        
    }
    
    public boolean isBlanko(String input, IntPos pos)
    {
        if (input.charAt(pos.value) == ' ')
        {
            return true;
        }
        
        if (input.charAt(pos.value) == '\r')
        {
            return true;
        }
        
        if (input.charAt(pos.value) == '\n')
        {
            return true;
        }
        
        if (input.charAt(pos.value) == '\t')
        {
            return true;
        }
        
        return false;
    }
    
    public boolean isBlanko(char c)
    {
        if (c == ' ')
        {
            return true;
        }
        
        if (c == '\r')
        {
            return true;
        }
        
        if (c == '\n')
        {
            return true;
        }
        
        if (c == '\t')
        {
            return true;
        }
        
        return false;
    }
    
    public static boolean isCharIdentifier(char c) 
    {
        return Character.isLetterOrDigit(c) || c == '_';
    }
    
    public Token GetPrevToken(String input, IntPos pos)
    {
        Token tok = new Token();
        
        if(pos.value < 0)
        {
            tok.type = Token.EOF;
            return tok;
        }
        
        char c = input.charAt(pos.value);
        
        if (this.isBlanko(c))
        {
            pos.value = pos.value - 1;
            return this.GetPrevToken(input, pos);
        }
        
        if (this.isCharIdentifier(c)) //identifier
        {
            tok.end = pos.value;
            
            while(pos.value >= 0)
            {
                c = input.charAt(pos.value);
                
                if (!this.isCharIdentifier(c))
                {
                    break;
                }
                pos.value = pos.value - 1;
            }
            
            tok.begin = pos.value + 1;
            tok.type = Token.IDENT;
            return tok;
        }
        
        if ( c == '\'') //single quote string
        {
            tok.end = pos.value;
            pos.value = pos.value - 1;
            
            while(pos.value >= 0)
            {
                c = input.charAt(pos.value);
                
                if (c == '\'')
                {
                    if (pos.value -1 >= 0)
                    {
                        c = input.charAt(pos.value-1);
                        
                        if ( c == '\\')
                        {
                            pos.value = pos.value - 1;
                        }
                        else
                        {
                            break;
                        }
                    }
                    else
                    {
                        break;
                    }
                }
                pos.value = pos.value - 1;
            }
            
            if (pos.value < 0)
            {
                tok.begin = 0;
            }
            else
            {
                tok.begin = pos.value;
                pos.value = pos.value - 1;
            }
            
            tok.type = Token.STRING;
            return tok;
        }
        
        if ( c == '/') //comment
        {
            tok.end = pos.value;
            pos.value = pos.value - 1;
            
            if ( pos.value < 0)
            {
                tok.begin = tok.end;
                tok.type = Token.INTERPUNCTION;
                return tok;
            }
            
            c = input.charAt(pos.value);
            
            if ( c != '*')
            {
                tok.begin = tok.end;
                tok.type = Token.INTERPUNCTION;
                return tok;
            }
            
            pos.value = pos.value - 1;
            
            while(pos.value >= 0) //comment
            {
                c = input.charAt(pos.value);
                
                if (c == '*')
                {
                    if (pos.value -1 >= 0)
                    {
                        c = input.charAt(pos.value-1);
                        
                        if ( c == '/')
                        {
                            pos.value = pos.value - 1;
                            break;
                        }
                    }
                }
                pos.value = pos.value - 1;
            }
            
            if (pos.value < 0)
            {
                tok.begin = 0;
            }
            else
            {
                pos.value = pos.value - 1;
            }
            
            return this.GetPrevToken(input, pos);
        }
        
        tok.begin = pos.value;
        tok.end = pos.value;
        tok.type = Token.INTERPUNCTION;
        
        pos.value = pos.value - 1;
        
        return tok;
    }
    
    public int GetNextComma(int begin, int end)
    {
        try 
        {
            String input = this.getText();
            IntPos currPos = new IntPos(begin - 1);
            Token tok = this.GetPrevToken(input, currPos);
            
            if (tok.type == Token.INTERPUNCTION && input.charAt(tok.begin) == ',')
            {
                return (-1) * tok.begin;
            }
            
            currPos = new IntPos(end + 1);
            tok = this.GetNextToken(input, currPos);
            
            if (tok.type == Token.INTERPUNCTION && input.charAt(tok.begin) == ',')
            {
                return tok.end;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int GetOpenBraPos()
    {
        try 
        {
            String input = this.getText();
            IntPos currPos = new IntPos(0);
            Token tok = null;
            
            while (true)
            {
                tok = this.GetNextToken(input, currPos);
                
                if (tok.type == Token.EOF)
                {
                    return 0;
                }
                
                if(tok.type == Token.INTERPUNCTION && input.charAt(tok.begin) == '(')
                {
                    return tok.begin;
                }
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public int GetVarPos()
    {
        try 
        {
            String input = this.getText();
            IntPos currPos = new IntPos(0);
            Token tok = null;
            
            while (true)
            {
                tok = this.GetNextToken(input, currPos);
                
                if (tok.type == Token.EOF)
                {
                    return 0;
                }
                
                if(tok.type == Token.IDENT && (input.charAt(tok.begin) == 'v' || input.charAt(tok.begin) == 'V'))
                {
                    tok.text = input.substring(tok.begin, tok.end+1);
                    if (tok.text.toLowerCase(Locale.US).equalsIgnoreCase("var"))
                    {
                        return tok.end + 1;
                    }
                }
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int GetProgBeginPos()
    {
        try 
        {
            String input = this.getText();
            IntPos currPos = new IntPos(0);
            Token tok = null;
            
            while (true)
            {
                tok = this.GetNextToken(input, currPos);
                
                if (tok.type == Token.EOF)
                {
                    return 0;
                }
                
                if(tok.type == Token.IDENT && (input.charAt(tok.begin) == 'b' || input.charAt(tok.begin) == 'B'))
                {
                    tok.text = input.substring(tok.begin, tok.end+1);
                    if (tok.text.toLowerCase(Locale.US).equalsIgnoreCase("begin"))
                    {
                        return tok.end + 1;
                    }
                }
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    public Token GetNextToken(String input, IntPos pos)
    {
        Token tok = new Token();
        int len = input.length();
        
        if(pos.value >= len)
        {
            tok.type = Token.EOF;
            return tok;
        }
        
        char c = input.charAt(pos.value);
        
        if (this.isBlanko(c))
        {
            pos.value = pos.value + 1;
            return this.GetNextToken(input, pos);
        }
        
        if (this.isCharIdentifier(c)) //identifier
        {
            tok.begin = pos.value;
            
            while(pos.value < len)
            {
                c = input.charAt(pos.value);
                
                if (!this.isCharIdentifier(c))
                {
                    break;
                }
                pos.value = pos.value + 1;
            }
            
            tok.end = pos.value - 1;
            tok.type = Token.IDENT;
            return tok;
        }
        
        if ( c == '\'') //single quote string
        {
            tok.begin = pos.value;
            pos.value = pos.value + 1;
            
            while(pos.value < len)
            {
                c = input.charAt(pos.value);
                
                if (c == '\'')
                {
                    c = input.charAt(pos.value-1);
                        
                    if ( c != '\\')
                    {
                        break;
                    }
                }
                pos.value = pos.value + 1;
            }
            
            if (pos.value >= len)
            {
                tok.end = len -1 ;
            }
            else
            {
                tok.begin = pos.value;
                pos.value = pos.value + 1;
            }
            
            tok.type = Token.STRING;
            return tok;
        }
        
        if ( c == '/') //comment
        {
            tok.begin = pos.value;
            pos.value = pos.value + 1;
            
            if ( pos.value >= len)
            {
                tok.begin = tok.end;
                tok.type = Token.INTERPUNCTION;
                return tok;
            }
            
            c = input.charAt(pos.value);
            
            if ( c != '*')
            {
                tok.begin = tok.end;
                tok.type = Token.INTERPUNCTION;
                return tok;
            }
            
            pos.value = pos.value + 1;
            
            while(pos.value < len) //comment
            {
                c = input.charAt(pos.value);
                
                if (c == '*')
                {
                    if (pos.value + 1 < len)
                    {
                        c = input.charAt(pos.value+1);
                        
                        if ( c == '/')
                        {
                            pos.value = pos.value + 1;
                            break;
                        }
                    }
                }
                pos.value = pos.value + 1;
            }
            
            if (pos.value >= len )
            {
                tok.end = len - 1;
            }
            else
            {
                tok.end = pos.value;
                pos.value = pos.value + 1;
            }
            
            return this.GetNextToken(input, pos);
        }
        
        tok.begin = pos.value;
        tok.end = pos.value;
        tok.type = Token.INTERPUNCTION;
        
        pos.value = pos.value + 1;
        
        return tok;
    }
    
    public void skipBlanko(String input, IntPos pos)
    {
        while(pos.value >= 0 && this.isBlanko(input, pos))
        {
            pos.value = pos.value - 1;
        }
    }
    
    public boolean isIdentChar(String input, IntPos pos)
    {
        char c = input.charAt(pos.value);        
        return Character.isLetterOrDigit(c) || c == '_';
    }
    
    public void skipIdent(String input, IntPos pos)
    {
        while(pos.value >= 0 && this.isIdentChar(input, pos))
        {
            pos.value = pos.value - 1;
        }
    }

    public Point GetPoint()
    {
        Point temp1 = this.offsetToXY(this.getCaretPosition());
        int startOffset = this.getLineStartOffset(this.getCaretLine() + 1);
        Point temp2 = this.offsetToXY(startOffset);
        
        return new Point(temp1.x, temp2.y);
    }

    public class IntPos 
    {
        int value = 0;
        
        public IntPos(int value)
        {
            this.value = value;
        }
    }
    
    private class Token 
    {
        int begin = 0;
        int end = 0;
        String text = "";
        int type = 0; 
        
        static final int EOF = 0;
        static final int IDENT = 1;
        static final int STRING = 2;
        static final int COMMENT = 3;
        static final int INTERPUNCTION = 4;
        
        public Token()
        {
        }
    }
    
    private class MyTextAreaExt extends TextAreaExtension
    {
        public MyTextAreaExt(MyTextArea area) 
        {
            this.area = area;
        }
        int prevLine1 = -1; 
        int prevLine2 = 1; 
        MyTextArea area = null;
        int numCall = 0;
        
        public void paintScreenLineRange(java.awt.Graphics2D gfx, int firstLine, int lastLine, int[] physicalLines, int[] start, int[] end, int y, int lineHeight) 
        {
            //System.out.println("paintValidLines" + (numCall++) + "\n");
            
            paintBreakPoints(gfx, lineHeight);
            
            if (editor != null && editor.isDebug)
            {
                int currLine = editor.vMashine.getCurrentLine() - 1;
                
                for(int i = 0; i < physicalLines.length; i++)
                {
                    if (currLine == physicalLines[i])
                    {
                        paintDebugLine(gfx,lineHeight);
                        break;
                    }
                }
            }
        }
    }
}
