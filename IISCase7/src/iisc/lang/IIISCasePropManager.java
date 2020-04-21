package iisc.lang;

import java.awt.TextArea;

import java.io.IOException;
import java.io.InputStream;

import java.util.Enumeration;
import java.util.Properties;

import org.gjt.sp.jedit.IPropertyManager;
import org.gjt.sp.util.IOUtilities;
import org.gjt.sp.util.Log;

public class IIISCasePropManager implements IPropertyManager 
{
    Properties props = new Properties(); 
    static int count = 0;
    
    public IIISCasePropManager() 
    {
        props.putAll(loadProperties("/org/gjt/sp/jedit/jedit_keys.props"));
        props.putAll(loadProperties("/org/gjt/sp/jedit/jedit.props"));
        props.putAll(loadProperties("/org/gjt/sp/jedit/jedit_gui.props"));
        props.put("view.gutter.font", "Tahoma");
        props.put("view.gutter.fontstyle", "0");
        props.put("view.gutter.bgColor", "#dbdbdb");
        props.put("view.gutter.fgColor", "#ffff00");
        props.put("view.gutter.numberAlignment", "left");
        props.put("view.gutter.highlightInterval", "1");
        props.put("view.eolMarkerColor", "#000000");
        props.put("view.font", "Tahoma");
        props.put("view.fontsize", "10");
        props.put("view.fontstyle", "0");
        props.put("view.lineHighlight", "true");
        props.put("view.lineHighlightColor", "#99ccfff");
        /*Enumeration en = props.keys();
        
        while(en.hasMoreElements())
        {
            String keyName = en.nextElement().toString();
            System.out.print(keyName + "= ");
            if (this.props.containsKey(keyName))
            {
                System.out.println(this.getProperty(keyName));
            }
        }*/
    }
    
    public void setProperty(String propName, String propValue)
    {
        props.setProperty(propName, propValue);
        
        if (!props.containsKey(propName))
        {
            props.put(propName, propValue);
        }
    }
    
    public String getProperty(String propName) 
    { 
        try 
        {
            String temp = props.getProperty(propName);
            //System.out.println(propName + "=" + temp);
            return temp;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }
    
    private static Properties loadProperties(String fileName) 
    {
        Properties props = new Properties();
        InputStream in = TextArea.class.getResourceAsStream(fileName);
        
        try 
        {
            props.load(in);
        } 
        catch (IOException e) 
        {
            Log.log(Log.ERROR, TextArea.class, e);
        }
        finally 
        {
            IOUtilities.closeQuietly(in);
        }
        return props;
    }
}
