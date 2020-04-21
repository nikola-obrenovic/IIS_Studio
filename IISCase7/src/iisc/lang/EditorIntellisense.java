package iisc.lang;

import iisc.IISFrameMain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.ListCellRenderer;

public class EditorIntellisense extends JWindow  implements FocusListener, KeyListener
{
    JSourceCodeEditor editor =null;
    MyTextArea area = null;
    JScrollPane sp1 = new JScrollPane();
    JList list = null;
    static ImageIcon[] iiscImage= new ImageIcon[4];
    static Color backColor = Color.getHSBColor(((float)138)/255,((float)240)/255,((float)214)/255);
    public boolean isFocusGained = false;
    
    static 
    {
        iiscImage[0] = new ImageIcon(IISFrameMain.class.getResource("icons/folder.gif"));
        iiscImage[1] = new ImageIcon(IISFrameMain.class.getResource("icons/domain.gif"));
        iiscImage[2] = new ImageIcon(IISFrameMain.class.getResource("icons/primitive.gif"));
        iiscImage[3] = new ImageIcon(IISFrameMain.class.getResource("icons/attribute.gif"));
    }
    
    public EditorIntellisense(ArrayList domains, ArrayList userDefDom, JSourceCodeEditor editor, MyTextArea area) 
    {
        super(editor);
        this.editor = editor;
        this.area = area;
        this.setSize(300,150);
        this.setLocationRelativeTo(area);
        this.setFocusable(false);
        this.setLayout(new BorderLayout());
        
        DefaultListModel lModel = new DefaultListModel();
        
        for(int i = 0; i < domains.size(); i++)
        {
            String dname = ((DomainDesc)domains.get(i)).getName();
            lModel.addElement("P:" + dname);
        }
        
        for(int i = 0; i < userDefDom.size(); i++)
        {
            String dname = ((DomainDesc)userDefDom.get(i)).getName();
            lModel.addElement("U:" + dname);
        }
        
        list = new JList(lModel);
        list.setCellRenderer(new CustomCellRenderer());
        sp1.getViewport().add(list);
        
        list.addFocusListener(this);
        list.setFocusable(true);
        list.setBackground(backColor);
        this.getContentPane().add(sp1);
        list.setSelectedIndex(0);
    }
    
    public boolean isFocusGained()
    {
        
        return this.hasFocus() || this.isFocused() || this.list.hasFocus();
    }
    
    public void focusGained(FocusEvent e)
    {
        System.out.print("da");
    }
    
    public void focusLost(FocusEvent e)
    {
        //this.setVisible(false);
    }
    
    public void keyReleased(KeyEvent e)
    {
    }
    
    public void keyPressed(KeyEvent e)
    {
        System.out.println("intelli");
        System.out.println(e.getKeyChar());
        System.out.println(e.getKeyCode());
    }
    
    public void keyTyped(KeyEvent e)
    {
        if (e.isControlDown() && e.getKeyChar() == ' ')
        {   
        }
    }
    
    private class CustomCellRenderer extends  DefaultListCellRenderer  implements ListCellRenderer<Object> 
    {
        
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) 
        {
            JLabel label =(JLabel)super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
    
            String s = value.toString();
            label.setText(s.substring(2));
            
            if (s.startsWith("P:"))
            {
                label.setIcon(iiscImage[2]);
            }
            
            if (s.startsWith("U:"))
            {
                label.setIcon(iiscImage[1]);
            }
            
            if(isSelected)
            {
                label.setBackground(SystemColor.textHighlight);
                label.setForeground(Color.white);
            }
            else
            {
                label.setBackground(backColor);
                label.setForeground(Color.black);
            }
            
            label.revalidate();
            list.revalidate();
            return this;
        }  
    }
}
