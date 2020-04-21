package iisc;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

public class AttEventDef extends JPanel
{
    private int width;
    private int height;
    public int Att_id;
    public int Tob_id;
    public int PR_id;
    public int Tf_id;
    private Connection conn;
    private IISFrameMain parent;
    
    private EventTableModel datm = new EventTableModel();
    public JTable eventTable = new JTable(datm);
    private JScrollPane tableSp = new JScrollPane();
    private JButton addBtn = new JButton();
    private JButton removeBtn = new JButton();
    private JButton editBtn = new JButton();
    String callType = "0";
    
    private int b_width; 
    
    public AttEventDef(int _width, int _height, int _Att_id, int _Tob_id, int _Tf_id, int _PR_id, Connection _Conn, IISFrameMain _Parent, String callType) 
    {
        Att_id = _Att_id;
        Tob_id = _Tob_id;
        PR_id = _PR_id;
        Tf_id = _Tf_id;
        parent = _Parent;
        conn = _Conn;
        width = _width;
        height = _height;
        this.callType = callType;
        
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
        setLayout(null);
        //this.setBorder(new LineBorder(Color.black, 1));
        
        addBtn.setText("Add");
        
        addBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) {
                addBtn_actionPerformed(e);
            }
        });
        removeBtn.setText("Remove");        
        
        removeBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) {
                removeBtn_actionPerformed(e);
            }
        });
        
        editBtn.setText("Edit");
        
        editBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) {
                editBtn_actionPerformed(e);
            }
        });
                
        if (this.width < 400)
        {
            addBtn.setBounds(new Rectangle(width - 75, 10, 75, 25));    
            editBtn.setBounds(new Rectangle(width - 75, 70, 75, 25));
            removeBtn.setBounds(new Rectangle(width - 75, 40, 75, 25));
            b_width = 75;
        }
        else
        {
            addBtn.setBounds(new Rectangle(575, 10, 160, 25));    
            editBtn.setBounds(new Rectangle(575, 64, 160, 25));
            removeBtn.setBounds(new Rectangle(575, 37, 160, 25));
            b_width = 160;
        }
        
        
        add(addBtn);
        add(editBtn);
        add(removeBtn);
        
        eventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventTable.setRowSelectionAllowed(true);
        eventTable.setGridColor(new Color(0,0,0));
        eventTable.setBackground(Color.white);
        eventTable.getTableHeader().setReorderingAllowed(false);
        eventTable.setAutoscrolls(true);
        eventTable.getTableHeader().setResizingAllowed(true);
        eventTable.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        eventTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        eventTable.repaint();
        
        if (this.width < 400)
        {
            tableSp.setBounds(new Rectangle(10, 10, this.width - b_width - 15, 150));
            tableSp.getViewport().add(eventTable, null);
        }
        else
        {
            tableSp.setBounds(new Rectangle(10, 10, this.width - b_width - 15, 138));
            tableSp.getViewport().add(eventTable, null);
        }
        add(tableSp);
        
        this.InitTableData();
    }
    
    private void InitTableData()
    {
        try 
        {
            Statement stmt = conn.createStatement();
            
            String sql = "select IISC_ATT_TOB_EVENT.Event_id as Event_id,IISC_EVENT.Event_mnem as Event_mnem, IISC_ATT_TOB_EVENT.Fun_id as Fun_id," +
            "IISC_FUNCTION.Fun_name as Fun_mnem,IISC_ATT_TOB_EVENT.Event_type as Event_type from IISC_ATT_TOB_EVENT, IISC_EVENT, IISC_FUNCTION where IISC_ATT_TOB_EVENT.Event_id=IISC_EVENT.Event_id and ";
            sql += "IISC_ATT_TOB_EVENT.Fun_id=IISC_FUNCTION.Fun_id and IISC_ATT_TOB_EVENT.Event_level=" + this.callType + " and IISC_ATT_TOB_EVENT.Tf_id=" + Tf_id;            
            
            if (callType.equals("1"))
            {
                sql += " and IISC_ATT_TOB_EVENT.Tob_id=" + this.Tob_id + " and IISC_ATT_TOB_EVENT.Att_id=" + this.Att_id;
            }
            else
            {
                if (callType.equals("2"))
                {
                    sql += " and IISC_ATT_TOB_EVENT.Tob_id=" + this.Tob_id;
                }
                
            }
            
            ResultSet rs = stmt.executeQuery(sql);
            
            Vector data = new Vector();
            
            while(rs.next())
            {
                EventDesc eventDesc = new EventDesc();
                eventDesc.event_id = rs.getString("Event_id");
                eventDesc.event_name = rs.getString("Event_mnem");
                eventDesc.func_id = rs.getString("Fun_id");
                eventDesc.func_name = rs.getString("Fun_mnem");
                eventDesc.level_id = rs.getInt("Event_type");
                
                if (eventDesc.level_id == 1)
                {
                    eventDesc.level_name = "Backend - DB Server";
                }
                else
                {
                    if (eventDesc.level_id == 2)
                    {
                        eventDesc.level_name = "Frontend - Application Server";
                    }
                    else
                    {
                        if (eventDesc.level_id == 3)
                        {
                            eventDesc.level_name = "Frontend - Client";
                        }
                        else
                        {
                            eventDesc.level_name = "Frontend - Unspecified";
                        }
                    }
                }
                data.add(eventDesc);
            }
            
            datm.populateData(data);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void removeBtn_actionPerformed(ActionEvent e)
    {
        if(callType.equals("1") && this.Att_id == -1)
        {
            return;
        }
        
        if(callType.equals("2") && this.Tob_id == -1)
        {
            return;
        }
        
        if(callType.equals("3") && this.Tf_id == -1)
        {
            return;
        }
        
        int action = JOptionPane.showConfirmDialog(this, "Confirm delete", "Events", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (action != JOptionPane.OK_OPTION)
        {
            return;
        }
        
        try 
        {
            int row = this.eventTable.getSelectedRow();
            EventDesc desc = (EventDesc)this.datm.data.get(row);
            
            if (callType.equals("1"))
            {
                Statement stmt = conn.createStatement();
                stmt.execute("delete from IISC_FUN_PARAM_VALUE where Call_type=" + callType + " and Att_id=" + this.Att_id + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id + " and Event_id=" + desc.event_id + " and Event_type=" + desc.level_id);
                stmt.execute("delete from IISC_ATT_TOB_EVENT where Event_level=" + callType + " and Att_id=" + this.Att_id + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id + " and Event_id=" + desc.event_id + " and Event_type=" + desc.level_id);                
            }
            else
            {
                if (callType.equals("2"))
                {
                    Statement stmt = conn.createStatement();
                    stmt.execute("delete from IISC_FUN_PARAM_VALUE where Call_type=" + callType + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id + " and Event_id=" + desc.event_id + " and Event_type=" + desc.level_id);
                    stmt.execute("delete from IISC_ATT_TOB_EVENT where Event_level=" + callType + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id + " and Event_id=" + desc.event_id + " and Event_type=" + desc.level_id);
                }
                else
                {
                    if (callType.equals("3"))
                    {
                        Statement stmt = conn.createStatement();
                        stmt.execute("delete from IISC_FUN_PARAM_VALUE where Call_type=" + callType + " and Tf_id=" + this.Tf_id + " and Event_id=" + desc.event_id+ " and Event_type=" + desc.level_id);
                        stmt.execute("delete from IISC_ATT_TOB_EVENT where Event_level=" + callType + " and Tf_id=" + this.Tf_id + " and Event_id=" + desc.event_id+ " and Event_type=" + desc.level_id);                        
                    }
                }
            }
            
            if (row < 0 || row >= this.datm.data.size())
            {
                return;
            }
            this.datm.data.remove(row);
            this.datm.fireTableDataChanged();
        }
        catch(Exception ex)
        {
        }
    }
    
    private void addBtn_actionPerformed(ActionEvent e)
    {
        if(callType.equals("1") && this.Att_id == -1)
        {
            return;
        }
        
        if(callType.equals("2") && this.Tob_id == -1)
        {
            return;
        }
        
        if(callType.equals("3") && this.Tf_id == -1)
        {
            return;
        }
        
        AddEventDefParams diag = new AddEventDefParams(this.parent, "Add event", this.Att_id, this.Tob_id, this.Tf_id, this.PR_id, true, conn, "", "-1", this.datm.data, this.callType, 0);
        Settings.Center(diag);
        diag.setVisible(true);
        
        if (diag.option == 1)
        {
            EventDesc desc = new EventDesc();
            desc.event_id = diag.event_id;
            desc.event_name = diag.event_name;
            desc.func_id= diag.func_id;
            desc.func_name = diag.func_name;
            desc.level_id = diag.level;
            desc.level_name = diag.level_name;
            
            this.datm.data.add(desc);
            this.datm.fireTableDataChanged();
        }
    }
    
    private void editBtn_actionPerformed(ActionEvent e)
    {
        if(callType.equals("1") && this.Att_id == -1)
        {
            return;
        }
        
        if(callType.equals("2") && this.Tob_id == -1)
        {
            return;
        }
        
        if(callType.equals("3") && this.Tf_id == -1)
        {
            return;
        }
        
        int row = this.eventTable.getSelectedRow();
        
        if (row < 0 || row >= this.datm.data.size())
        {
            return;
        }
        
        EventDesc desc = (EventDesc)this.datm.data.get(row);
        
        AddEventDefParams diag = new AddEventDefParams(this.parent, "Add event", this.Att_id, this.Tob_id, this.Tf_id, this.PR_id, true, conn, desc.event_name, desc.event_id, this.datm.data, this.callType, desc.level_id);
        Settings.Center(diag);
        diag.setVisible(true);
        
        if (diag.option == 1)
        {
            EventDesc newDesc = new EventDesc();
            newDesc.event_id = diag.event_id;
            newDesc.event_name = diag.event_name;
            newDesc.func_id= diag.func_id;
            newDesc.func_name = diag.func_name;
            newDesc.level_id = diag.level;
            newDesc.level_name = diag.level_name;
            this.datm.data.set(row, newDesc);
            this.datm.fireTableDataChanged();
        }
    }
    
    public class EventTableModel extends AbstractTableModel 
    {
        private String[] headers;
        public Vector data;
        
        public EventTableModel()
        {
            headers = new String[3];
            data = new Vector();
            headers[0] = "Name";
            headers[1] = "Function";
            headers[2] = "Level";
        }
        
        public int getColumnCount()
        {
            return 3;
        }
        
        public String getColumnName(int index)
        {
            return headers[index];
        }
        
        public int getRowCount()
        {
            return data.size();
        }
        
        public Object getValueAt(int x, int y)
        {
            EventDesc value = (EventDesc)data.get(x);
        
            if (y == 0)
            { 
                return value.event_name;
            } 
            else
            {       
                if (y == 1)
                {
                    return value.func_name;
                }
                else
                {
                    return value.level_name;
                }
            }
        } 
        
        
        public void populateData(Vector value)
        {
            data = value;
            fireTableDataChanged();
        }
        
        public boolean isCellEditable(int row, int col) 
        {
            return false;
        }    
    }
    
    public class EventDesc
    {
        public String event_id;
        public String event_name;
        public String func_id;
        public String func_name;
        public int level_id;
        public String level_name;
        
        public EventDesc()
        {
        }
    }

    /*public static void main(String[] args)
    {
        lookFeel();
        
        JDialog frame = new JDialog();
        frame.setSize(395, 250);
        frame.setLayout(null);
        
        AttEventDef panel = new AttEventDef(375, 250, 44, 25, 12, 3, Connect(), null, "2");
        panel.setBounds(10, 10, 390, 250);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        
        //AddEventDefParams 
    }
    
     public static void lookFeel()
     {
         try
         {
             UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
         }
         catch ( Exception e ) 
         {
             e.printStackTrace();
         }
     }
         
     private static Connection Connect()
     {
         String url = "jdbc:odbc:isscase_new";
         
         try
         {
             Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
             return (Connection)DriverManager.getConnection(url, "", "");
         }
         catch (ClassNotFoundException ef) 
         {
             JOptionPane.showMessageDialog(null, "ClassNotFoundException:    " + ef.getMessage() , "Connection error", JOptionPane.ERROR_MESSAGE);
         }
         catch(SQLException ex)
         {
             JOptionPane.showMessageDialog(null, "<html><center>This is not valid repository!", "Connection Error", JOptionPane.ERROR_MESSAGE);
         }
         return null;
     }*/
}
