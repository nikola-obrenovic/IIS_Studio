package iisc;

import java.awt.Dimension;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
import javax.swing.UIManager;

public class AddEventDefParams extends JDialog
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
    
    public int option = 0;
    Connection conn;
    public int Att_id;
    public int Tob_id;
    public int PR_id;
    public int Tf_id;
    public String event_id;
    public String event_name;
    public String func_name; 
    public String func_id;
    private String callType; 
    public int level; 
    public String level_name;
    private ArrayList eventIds = new ArrayList();
    
    public AddEventDefParams(IISFrameMain parent, String title, int _Att_id, int _Tob_id, int _Tf_id, int _PR_id, boolean modal, Connection conn, String event_name, String event_id, Vector events, String callType, int level)
    {
        super(parent, title, modal);        
        Att_id = _Att_id;
        Tob_id = _Tob_id;
        PR_id = _PR_id;
        Tf_id = _Tf_id;
        this.event_id = event_id;
        this.parent = parent;        
        this.conn = conn;
        this.callType = callType;
        this.event_name = event_name;
        this.events = events;
        this.level = level;
        
        try
        { 
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void LoadComboeEvents()
    {
    
        try 
        {
            this.eventCombo.removeAllItems();
            this.eventIds.clear();
            
            Statement stmt = conn.createStatement();
            String sql = "Select * from IISC_EVENT where Event_level=" + this.callType;
            
            int level = this.eventTypeCombo.getSelectedIndex() + 1;
            
            if (level == 1)
            {
                sql += " and Is_db_event=1";
            }
            else
            {
                if (level == 2)
                {
                    sql += " and Is_app_event=1";
                }
                else
                {
                    sql += " and Is_client_event=1";
                }
            }
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next())
            {
                String event_Mnem = rs.getString("Event_mnem");
                String eid = rs.getString("Event_id");
                
                if(!this.ContainsEvent(eid, level))
                {
                    this.eventIds.add(eid);
                    this.eventCombo.addItem(event_Mnem);                    
                }
            }
        }
        catch(Exception e)
        {}
        
        if (this.eventIds.size() > 0)
        {
            this.eventCombo.setEnabled(true);
            /*if (attFunDefPanel != null)
            {
                this.getContentPane().remove(attFunDefPanel);
            }*/
        }
        else
        {
            this.eventCombo.setEnabled(false);
            if (this.eventCombo.getItemCount() == 0)
            {
                if (attFunDefPanel != null)
                {
                    this.getContentPane().remove(attFunDefPanel);
                }
                
                attFunDefPanel = new AttFunDef(this.getWidth() - 20, 300, Att_id, Tob_id, Tf_id,  PR_id, conn,  this.parent, null, "1", "", "0", "0", this, this.level, 0);
                attFunDefPanel.setBounds(new Rectangle(10, 70, this.getWidth() - 20, 240));
                this.getContentPane().add(attFunDefPanel); 
                attFunDefPanel.Disable();
            }
        }
        
        //eventCombo_ItemStateChanged(null);
        this.paintComponents(this.getGraphics());
    }
    
    private boolean ContainsEvent(String eventid, int level)
    {
        if (this.event_id.equals(eventid) && this.level == level)
        {
            return false;
        }
        
        for(int i = 0; i < this.events.size(); i++)
        {
            AttEventDef.EventDesc desc = (AttEventDef.EventDesc)this.events.get(i);
            
            if (desc.event_id.equals(eventid) && desc.level_id == level)
            {
                return true;
            }
        }
        return false;
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
        
        eTypeLabel.setBounds(new Rectangle(20, 20, 75, 20));
        this.getContentPane().add(eTypeLabel, null);
        
        eventTypeCombo.setBounds(new Rectangle(20, 40, 180, 20));
        this.getContentPane().add(eventTypeCombo, null);
        
        eLabel.setBounds(new Rectangle(200, 20, 75, 20));
        this.getContentPane().add(eLabel, null);
        
        eventCombo.setBounds(new Rectangle(200, 40, 185, 20));
        this.getContentPane().add(eventCombo, null);
        
        eventTypeCombo.addItem("Backend - DB Server");
        eventTypeCombo.addItem("Frontend - Application Server");
        eventTypeCombo.addItem("Frontend - Client");
        eventTypeCombo.addItem("Frontend - Unspecified");
        
        if (this.level == 0)
        {
            this.eventTypeCombo.setSelectedIndex(0);
        }
        else
        {
            this.eventTypeCombo.setSelectedIndex(this.level - 1);
        }
        
        LoadComboeEvents();
        
        if (this.eventCombo.getItemCount() == 0)
        {
            this.eventCombo.setEnabled(false);
            
        }
        else
        {
            if (this.event_name.equals(""))
            {
                this.eventCombo.setSelectedIndex(0);
            }
            else
            {
                this.eventCombo.setSelectedItem(this.event_name);
            }
            
            eventCombo_ItemStateChanged(null);
        }
        
        this.eventTypeCombo.addItemListener(new ItemListener()
        {
             public void itemStateChanged(ItemEvent e) 
             {
                 eventTypeCombo_ItemStateChanged(e);
             }
        });
        
        this.eventCombo.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e) 
            {
                eventCombo_ItemStateChanged(e);
            }
        });
        
        btnSave.setEnabled(false);
        btnApply.setEnabled(false);
    }
    
    public void eventTypeCombo_ItemStateChanged(ItemEvent e) 
    {
        LoadComboeEvents();
    }
    
    public void eventCombo_ItemStateChanged(ItemEvent e) 
    {
        if (this.eventCombo.getSelectedItem() == null)
        {
            return;
        }
        
        int index = this.eventCombo.getSelectedIndex();
        String eventId = (String)this.eventIds.get(index);
        this.event_name = this.eventCombo.getSelectedItem().toString();
        
        if (attFunDefPanel != null)
        {
            this.getContentPane().remove(attFunDefPanel);
        }
        
        attFunDefPanel = new AttFunDef(this.getWidth() - 20, 240, Att_id, Tob_id, Tf_id,  PR_id, conn,  this.parent, null, this.callType, event_name, eventId, this.event_id, this, this.level, this.eventTypeCombo.getSelectedIndex() + 1);
        attFunDefPanel.setBounds(new Rectangle(10, 70, this.getWidth() - 20, 240));
        
        this.getContentPane().add(attFunDefPanel);
        attFunDefPanel.paintImmediately(0,0,this.getWidth() - 20, 30);
        btnSave.setEnabled(true);
        btnApply.setEnabled(true);
        attFunDefPanel.repaintAll();
        this.repaint();
        
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
                    this.attFunDefPanel.Update();
                    
                    int index = this.eventCombo.getSelectedIndex();
                    String eventId = (String)this.eventIds.get(index);
                    this.event_id = eventId;
                    this.btnApply.setEnabled(false);
                    this.btnSave.setEnabled(false);
                    this.event_name = this.eventCombo.getSelectedItem().toString();
                    this.option = 1;
                    this.func_name = this.attFunDefPanel.funCombo.getSelectedItem().toString();
                    this.level = this.eventTypeCombo.getSelectedIndex() + 1;
                    this.level_name = this.eventTypeCombo.getSelectedItem().toString();
                    return 1;
                }
            }
        }
        return 0;
    }
    
    public void btnHelp_actionPerformed(ActionEvent e)
    {
    }
}
