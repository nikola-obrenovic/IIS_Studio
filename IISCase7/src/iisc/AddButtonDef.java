package iisc;

import java.awt.Dimension;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class AddButtonDef extends JDialog implements KeyListener, ItemListener
{
    
    public JButton btnClose = new JButton();
    public JButton btnSave = new JButton();
    private ButtonGroup bgrp=new ButtonGroup();
    private JButton btnHelp = new JButton();
    private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    public JButton btnApply = new JButton();
    
    private JLabel eLabel = new JLabel("Event");
    private JComboBox eventCombo = new JComboBox();
    private JLabel eTypeLabel = new JLabel("Level");
    private JComboBox eventTypeCombo = new JComboBox();
    private AttFunDef attFunDefPanel = null;
    IISFrameMain parent;
    public Vector events = new Vector();
    private JLabel xLabel = new JLabel("X Pos.");
    private JLabel yLabel = new JLabel("Y Pos.");
    private JLabel wLabel = new JLabel("Width");
    private JLabel hLabel = new JLabel("Height");
    private JLabel lLabel = new JLabel("Label");
    private JLabel igLabel = new JLabel("Item Group");
    
    JTextField xTxt = new JTextField();
    JTextField yTxt = new JTextField();
    JTextField wTxt = new JTextField();
    JTextField hTxt = new JTextField();
    JTextField labelTxt = new JTextField();
    JComboBox igCombo = new JComboBox();
    ArrayList igIds = new ArrayList();
    
    public int option = 0;
    Connection conn;
    public int Att_id;
    public int Tob_id;
    public int PR_id;
    public int Tf_id;
    public String func_name; 
    public String func_id;
    private Vector props;
    public int button_Id = 0;
    public int b_x =0;
    public int b_y = 0;
    public int b_h = 0;
    public int b_w = 0;
    public String b_label = "";
    public String b_item_group = "";
    
    public AddButtonDef(IISFrameMain parent, String title, int _Tob_id, int _Tf_id, int _PR_id, boolean modal, Connection conn, int button_id, Vector props)
    {
        super(parent, title, modal);        
        Tob_id = _Tob_id;
        PR_id = _PR_id;
        Tf_id = _Tf_id;
        this.parent = parent;        
        this.conn = conn;
        this.props = props;
        this.button_Id = button_id;
        
        try
        { 
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void jbInit() throws Exception
    {   
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(new Dimension(414, 443));
        this.getContentPane().setLayout(null);
        //this.setTitle("Component Type Attribute");
        
        btnClose.setMaximumSize(new Dimension(50, 30));
        btnClose.setPreferredSize(new Dimension(50, 30));
        btnClose.setText("Cancel");
        btnClose.setBounds(new Rectangle(280, 375, 80, 30));
        btnClose.setMinimumSize(new Dimension(50, 30));
        btnClose.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              close_ActionPerformed(ae);
            }
          });
        btnSave.setMaximumSize(new Dimension(50, 30));
        btnSave.setPreferredSize(new Dimension(50, 30));
        btnSave.setText("OK");
        btnSave.setBounds(new Rectangle(200, 375, 75, 30));
        btnSave.setMinimumSize(new Dimension(50, 30));
        btnSave.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              save_ActionPerformed(ae);
            }
          });
          
        btnHelp.setBounds(new Rectangle(365, 375, 35, 30));
        btnHelp.setIcon(imageHelp);
        btnHelp.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnHelp_actionPerformed(e);
            }
          });
        btnApply.setMaximumSize(new Dimension(50, 30));
        btnApply.setPreferredSize(new Dimension(50, 30));
        btnApply.setText("Apply");
        btnApply.setBounds(new Rectangle(120, 375, 75, 30));
        btnApply.setMinimumSize(new Dimension(50, 30));
        
        btnApply.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              apply_ActionPerformed(ae);
            }
          });
          
        this.getContentPane().add(btnApply, null);
        this.getContentPane().add(btnHelp, null);
        this.getContentPane().add(btnSave, null);
        this.getContentPane().add(btnClose, null);
        
        xLabel.setBounds(new Rectangle(20, 5, 92, 20));
        this.getContentPane().add(xLabel, null);
        
        xTxt.setBounds(new Rectangle(20, 25, 92, 20));
        this.getContentPane().add(xTxt, null);
        xTxt.addKeyListener(this);
        
        yLabel.setBounds(new Rectangle(112, 5, 91, 20));
        this.getContentPane().add(yLabel, null);
        
        yTxt.setBounds(new Rectangle(112, 25, 91, 20));
        this.getContentPane().add(yTxt, null);
        yTxt.addKeyListener(this);
        
        wLabel.setBounds(new Rectangle(203, 5, 91, 20));
        this.getContentPane().add(wLabel, null);
        
        wTxt.setBounds(new Rectangle(203, 25, 91, 20));
        this.getContentPane().add(wTxt, null);
        wTxt.addKeyListener(this);
        
        hLabel.setBounds(new Rectangle(294, 5, 91, 20));
        this.getContentPane().add(hLabel, null);
        
        hTxt.setBounds(new Rectangle(294, 25, 91, 20));
        this.getContentPane().add(hTxt, null);
        hTxt.addKeyListener(this);
        
        lLabel.setBounds(new Rectangle(20, 45, 90, 20));
        this.getContentPane().add(lLabel, null);
        
        labelTxt.setBounds(new Rectangle(20, 65, 183, 20));
        this.getContentPane().add(labelTxt, null);
        labelTxt.addKeyListener(this);
        
        this.LoadItemGroupCombo();
        
        igLabel.setBounds(new Rectangle(203, 45, 90, 20));
        this.getContentPane().add(igLabel, null);
        
        igCombo.setBounds(new Rectangle(203, 65, 183, 20));
        this.getContentPane().add(igCombo, null);
        
        xTxt.setText((String)props.get(0));
        yTxt.setText((String)props.get(1));
        wTxt.setText((String)props.get(2));
        hTxt.setText((String)props.get(3));
        labelTxt.setText((String)props.get(4));
        String temp = (String)props.get(5);
        
        if (temp.equals(""))
        {
            if (igCombo.getItemCount() > 0)
            {
                igCombo.setSelectedIndex(0);
            }
        }
        else
        {
            igCombo.setSelectedItem(temp);
        }
        
        igCombo.addItemListener(this);
        
        btnSave.setEnabled(false);
        btnApply.setEnabled(false);
        
        attFunDefPanel = new AttFunDef(this.getWidth() - 20, 300, -1, Tob_id, Tf_id,  PR_id, conn,  this.parent, null, "4", "", "0", "0", null, 0, this.button_Id);
        attFunDefPanel.attButtonDef = this;
        attFunDefPanel.setBounds(new Rectangle(10, 80, this.getWidth() - 20, 230));
        this.getContentPane().add(attFunDefPanel); 
    }
    
    public void LoadItemGroupCombo()
    {
        try 
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from IISC_ITEM_GROUP where TOB_id=" + this.Tob_id);
            
            while(rs.next())
            {
                this.igCombo.addItem(rs.getString("IG_name"));
                this.igIds.add(rs.getString("IG_id"));
            }
        }
        catch(Exception e)
        {}
    }
    
    private boolean CheckFields()
    {
        try
        {
            this.b_x = Integer.parseInt(this.xTxt.getText());
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Invalid value for X position", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try
        {
            this.b_y = Integer.parseInt(this.yTxt.getText());
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Invalid value for Y position", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try
        {
            this.b_w = Integer.parseInt(this.wTxt.getText());
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Invalid value for width", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try
        {
            this.b_h = Integer.parseInt(this.hTxt.getText());
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Invalid value for height", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void EnableButtons()
    {
        this.btnApply.setEnabled(true);
        this.btnSave.setEnabled(true);
    }
    private void close_ActionPerformed(ActionEvent e)
    {
        if(btnSave.isEnabled())
        {
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0)
            {
                int result = Save();
                
                if (result == 1)
                {
                    this.setVisible(false); 
                }
                else
                {
                    return;
                }
            }
        }
        this.setVisible(false); 
    }
    
    private void save_ActionPerformed(ActionEvent e)
    { 
        int result = Save();
        
        if (result == 1)
        {
            this.setVisible(false); 
        }
    }
    private void apply_ActionPerformed(ActionEvent e)
    {
        Save();
    }
    
    private int Save()
    {
        if (!this.CheckFields())
        {
            return 0;
        }
        
        if (this.attFunDefPanel != null)
        {
            if (this.attFunDefPanel.funCombo.getSelectedItem() == null || this.attFunDefPanel.funCombo.getSelectedItem().toString().equals(""))
            {
                JOptionPane.showMessageDialog(null, "<html><center>Select function!", "Component Type Attributes", JOptionPane.ERROR_MESSAGE);
                return 0;
            }
            else
            {
                if (!this.attFunDefPanel.Check())
                {
                    JOptionPane.showMessageDialog(null, "<html><center>Invalid function specification!", "Component Type Attributes", JOptionPane.ERROR_MESSAGE);
                    return 0;
                }
                else
                {
                    this.attFunDefPanel.b_x = this.b_x;
                    this.attFunDefPanel.b_y = this.b_y;
                    this.attFunDefPanel.b_w = this.b_w;
                    this.attFunDefPanel.b_h = this.b_h;
                    this.b_label = this.labelTxt.getText();
                    this.attFunDefPanel.b_label = this.labelTxt.getText();
                    this.attFunDefPanel.b_ig_id = (String)this.igIds.get(this.igCombo.getSelectedIndex());
                    this.attFunDefPanel.Update();
                    
                    this.btnApply.setEnabled(false);
                    this.btnSave.setEnabled(false);
                    this.option = 1;
                    this.func_name = this.attFunDefPanel.funCombo.getSelectedItem().toString();
                    this.button_Id = this.attFunDefPanel.button_id;
                    this.b_item_group = (String)this.igCombo.getSelectedItem();
                    return 1;
                }
            }
        }
        return 0;
    }
    
    public void btnHelp_actionPerformed(ActionEvent e)
    {
    }    
    
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) 
    {
        this.EnableButtons();
    }

    public void itemStateChanged(ItemEvent e) 
    {
        this.EnableButtons();
    }
}
