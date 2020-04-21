package iisc;

import iisc.PropertyScrollPane;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import java.util.Vector;
import java.io.*;
import javax.swing.JTextArea;
import java.awt.TextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.text.Caret;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.sql.*;



public class DisplayProperties extends JFrame
{
    private PropertyPanel pPanel;
    private PropertyScrollPane sPane;
    private String drivers = "sun.jdbc.odbc.JdbcOdbcDriver";
    private String url =new String();
    private Property[] properties;
    private Connection conn;
    
    public DisplayProperties(IISFrameMain parent) 
    {
        //super(parent, "Properties", true );
        super("Properties");
        
        try
        {
          jbInit();
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
  
    }

    public DisplayProperties() 
    {
        super();
        connect();
        
        try
        {
          jbInit();
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
  
    }
    private void connect()
    {
    
        url = "jdbc:odbc:pop";
        //System.out.println(url);
        
        try 
        {
            Class.forName(drivers);
        }
        catch (ClassNotFoundException ef) 
        {
          JOptionPane.showMessageDialog(null, "ClassNotFoundException:    " + ef.getMessage() , "Connection error", JOptionPane.ERROR_MESSAGE);
          System.exit(0);
        }
  
        try
        {
            conn= (Connection)DriverManager.getConnection(url, "", "");
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "<html><center>This is not valid repository!", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
      
    }
    private void jbInit() throws Exception
    {
        //this.setResizable(false);
        this.getContentPane().setLayout(null);
        this.setSize(new Dimension(332, 487));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        PropertyFactory pf = new PropertyFactory(conn, 1, null, "");
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch(Exception e) 
        {
            System.out.println("Error setting native LAF: " + e);
        }

        /*String[] items = {"true", "false"};
        int[] keyd = {1, 2};
        properties = new Property[34];
      
        this.addComponentListener(new java.awt.event.ComponentAdapter()
        {
          public void componentResized(ComponentEvent e)
          {
            this_componentResized(e);
          }
        });
         
        properties[0] = new Property("name", "name","text","property1", null, conn);
        properties[1] = new Property("description", "description","text","", null, conn);
        properties[2] = new Property("visible", "visible" ,"combo", 0,(Object[])items,(Object[])items, null, conn);
        properties[3] = new Property("Color", "Color","color","Red", null, conn);
        
        for (int i = 4; i < properties.length; i++)
        {
            properties[i] = new Property("Aleksandar", "Aleksandar","text","Popovic", null, conn);
        }
        
        */
    
        properties = pf.GenerateFormTypeProperties(5, conn);
        
        pPanel = new PropertyPanel (properties, getWidth() - 12 );
        
        sPane = new PropertyScrollPane(pPanel, getWidth() - 10, getHeight() - 35);
        this.getContentPane().add(sPane);
        
        
    
    }
    
    public static void main(String[] args)
    {
        DisplayProperties dp = new DisplayProperties();
        dp.setVisible(true);;
    }
    
 

  private void this_componentResized(ComponentEvent e)
  {
        sPane.setSize(this.getWidth() - 10, this.getHeight() - 35);
        sPane.revalidate();
        
  }

 
}