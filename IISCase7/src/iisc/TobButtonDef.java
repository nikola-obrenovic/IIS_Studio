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

public class TobButtonDef extends JPanel
{
    private int width;
    private int height;
    public int Att_id;
    public int Tob_id;
    public int PR_id;
    public int Tf_id;
    private Connection conn;
    private IISFrameMain parent;
    
    private TobButtonModel datm = new TobButtonModel();
    public JTable eventTable = new JTable(datm);
    private JScrollPane tableSp = new JScrollPane();
    private JButton addBtn = new JButton();
    private JButton removeBtn = new JButton();
    private JButton editBtn = new JButton();
    String callType = "0";
    
    private int b_width; 
    
    public TobButtonDef(int _width, int _height, int _Tob_id, int _Tf_id, int _PR_id, Connection _Conn, IISFrameMain _Parent) 
    {
        Tob_id = _Tob_id;
        PR_id = _PR_id;
        Tf_id = _Tf_id;
        parent = _Parent;
        conn = _Conn;
        width = _width;
        height = _height;
        
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
        
        tableSp.setBounds(new Rectangle(10, 10, this.width - b_width - 15, 150));
        tableSp.getViewport().add(eventTable, null);
        add(tableSp);
        
        this.InitTableData();
    }
    
    private void InitTableData()
    {
        try 
        {
            Statement stmt = conn.createStatement();
            
            String sql = "select IISC_TOB_BUTTON.Btn_id as Btn_id,IISC_TOB_BUTTON.XPos as X,IISC_TOB_BUTTON.YPos as Y, " +
            "IISC_TOB_BUTTON.Width as W, IISC_TOB_BUTTON.Height as H,IISC_TOB_BUTTON.Label as L,IISC_TOB_BUTTON.IG_id as IG_id, IISC_ITEM_GROUP.IG_name as IG_name," +
            "IISC_TOB_BUTTON.Fun_id as Fun_id,IISC_FUNCTION.Fun_name as Fun_mnem from IISC_TOB_BUTTON, IISC_FUNCTION,IISC_ITEM_GROUP where ";
            sql += "IISC_ITEM_GROUP.IG_id=IISC_TOB_BUTTON.IG_id and IISC_TOB_BUTTON.Fun_id=IISC_FUNCTION.Fun_id and IISC_TOB_BUTTON.Tf_id=" + Tf_id;           
            
            sql += " and IISC_TOB_BUTTON.Tob_id=" + this.Tob_id;
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            
            Vector data = new Vector();
            
            while(rs.next())
            {
                ButtonDec eventDesc = new ButtonDec();
                eventDesc.btn_id = rs.getInt("Btn_id");
                eventDesc.x = rs.getString("X");
                eventDesc.y = rs.getString("Y");
                eventDesc.w = rs.getString("W");                
                eventDesc.h = rs.getString("H");
                eventDesc.label = rs.getString("L");
                eventDesc.function = rs.getString("Fun_mnem");
                eventDesc.item_group = rs.getString("IG_name");
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
        if(this.Tob_id == -1)
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
            ButtonDec desc = (ButtonDec)this.datm.data.get(row);
            
            Statement stmt = conn.createStatement();
            stmt.execute("delete from IISC_FUN_PARAM_VALUE where Call_type=4 and Button_id=" + desc.btn_id);
            stmt.execute("delete from IISC_TOB_BUTTON where Btn_id=" + desc.btn_id);                
            
            
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
        if(this.Tob_id == -1)
        {
            return;
        }
        
        Vector props = new Vector();
        props.add("0");
        props.add("0");
        props.add("0");
        props.add("0");
        props.add("");
        props.add("");
        
        AddButtonDef diag = new AddButtonDef(this.parent, "Add button", this.Tob_id, this.Tf_id, this.PR_id, true, conn, -1, props);
        Settings.Center(diag);
        diag.setVisible(true);
        
        if (diag.option == 1)
        {
            ButtonDec desc = new ButtonDec();
            desc.btn_id = diag.button_Id;
            desc.x = Integer.toString(diag.b_x);
            desc.y = Integer.toString(diag.b_y);
            desc.w = Integer.toString(diag.b_w);
            desc.h = Integer.toString(diag.b_h);
            desc.function = diag.func_name;
            desc.label = diag.b_label;
            desc.item_group = diag.b_item_group;
            
            this.datm.data.add(desc);
            this.datm.fireTableDataChanged();
        }
    }
    
    private void editBtn_actionPerformed(ActionEvent e)
    {
        if(this.Tob_id == -1)
        {
            return;
        }
            
        int row = this.eventTable.getSelectedRow();
        
        if (row < 0 || row >= this.datm.data.size())
        {
            return;
        }
        
        ButtonDec sel_desc = (ButtonDec)this.datm.data.get(row);
        
        Vector props = new Vector();
        props.add(sel_desc.x);
        props.add(sel_desc.y);
        props.add(sel_desc.w);
        props.add(sel_desc.h);
        props.add(sel_desc.label);
        props.add(sel_desc.item_group);
        
        AddButtonDef diag = new AddButtonDef(this.parent, "Edit button", this.Tob_id, this.Tf_id, this.PR_id, true, conn, sel_desc.btn_id, props);
        Settings.Center(diag);
        diag.setVisible(true);
        
        if (diag.option == 1)
        {
            ButtonDec desc = new ButtonDec();
            desc.btn_id = diag.button_Id;
            desc.x = Integer.toString(diag.b_x);
            desc.y = Integer.toString(diag.b_y);
            desc.w = Integer.toString(diag.b_w);
            desc.h = Integer.toString(diag.b_h);
            desc.function = diag.func_name;
            desc.label = diag.b_label;
            desc.item_group = diag.b_item_group;
            
            this.datm.data.set(row, desc);
            this.datm.fireTableDataChanged();
        }
    }
    
    public class TobButtonModel extends AbstractTableModel 
    {
        private String[] headers;
        public Vector data;
        
        public TobButtonModel()
        {
            headers = new String[6];
            data = new Vector();            
            headers[0] = "Label";
            headers[1] = "Function";
            headers[2] = "Item group";
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
            ButtonDec value = (ButtonDec)data.get(x);
        
            if (y == 0)
            { 
                return value.label;
            } 
            
            if (y == 1)
            { 
                return value.function;
            }
            
            return value.item_group;
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
    
    public class ButtonDec
    {
        public int btn_id;
        public String x;
        public String y;
        public String w;
        public String h;
        public String label;
        public String function;
        public String item_group;
        
        public ButtonDec()
        {
        }
    }

    public static void main(String[] args)
    {
        lookFeel();
        
        JDialog frame = new JDialog();
        frame.setSize(395, 250);
        frame.setLayout(null);
        
        TobButtonDef panel = new TobButtonDef(375, 250, 25, 12, 3, Connect(), null);
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
     }
}

