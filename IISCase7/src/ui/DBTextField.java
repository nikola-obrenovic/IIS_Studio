package ui;

import iisc.lang.vmashine.VirtualMashine;

import java.awt.Color;
import java.awt.Rectangle;

import java.io.FileInputStream;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class DBTextField extends JTextField
{
    String attId = "";
    String tobId = "";
    String funName = "";
    
    public static VirtualMashine vMashine = new VirtualMashine();
    public static Document funcDoc;
    
    static 
    {
        try 
        {
            FileInputStream input = new FileInputStream("functions.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();            
            funcDoc = builder.parse(input);
            vMashine.InitAssemblerCode(funcDoc);
        }
        catch(Exception e)
        {}
    }
    
    public DBTextField() 
    {
        
    }
    
    public DBTextField(String text) 
    {
        super(text);
    }
    
    public void setBounds(Rectangle r)
    {
        super.setBounds(r);
    }
    
    public void setEditabile(Border border)
    {
        super.setBorder(border);
    }
    
    public void setText(String text)
    {
        String val = "";
        
        if (this.funName != null && this.funName != "")
        {
            String result = vMashine.ExecuteFunc(this.funName, null);
            super.setText(result);
            return;
        }
        super.setText(text);
    }
    
    public void setTobId(String tobId)
    {
        this.tobId = tobId;
    }
    
    public void setAttId(String attId)
    {
        this.attId = attId;
    }
    
    public void setFunName(String funName)
    {
        this.funName = funName;
    }
    
    public void setForeground(Color c)
    {
        super.setForeground(c);
    }
}
