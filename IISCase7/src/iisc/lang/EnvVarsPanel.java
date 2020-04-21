package iisc.lang;

import iisc.JDBCQuery;
import iisc.SearchTable;
import iisc.Settings;

import iisc.lang.JSourceCodeEditor;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

public class EnvVarsPanel extends JPanel
{
    private int width;
    private int height;
    private Connection conn;
    private JSourceCodeEditor parent;
    
    private EnvVarTableModel datm = new EnvVarTableModel();
    public JTable envVarTable = new JTable(datm);
    private JScrollPane tableSp = new JScrollPane();
    private JButton addBtn = new JButton();
    private JButton removeBtn = new JButton();
    private JButton editBtn = new JButton();
    
    int PR_id;
    
    public EnvVarsPanel(int _width, int _height, Connection _Conn, JSourceCodeEditor _Parent, int PR_id) 
    {
        parent = _Parent;
        conn = _Conn;
        width = _width;
        height = _height;
        this.PR_id = PR_id;
        
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
                
        addBtn.setBounds(new Rectangle(width - 75, 10, 75, 25));    
        editBtn.setBounds(new Rectangle(width - 75, 70, 75, 25));
        removeBtn.setBounds(new Rectangle(width - 75, 40, 75, 25));
        
        
        add(addBtn);
        add(editBtn);
        add(removeBtn);
        
        envVarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        envVarTable.setRowSelectionAllowed(true);
        envVarTable.setGridColor(new Color(0,0,0));
        envVarTable.setBackground(Color.white);
        envVarTable.getTableHeader().setReorderingAllowed(false);
        envVarTable.setAutoscrolls(true);
        envVarTable.getTableHeader().setResizingAllowed(true);
        envVarTable.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        envVarTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        envVarTable.repaint();
        
        tableSp.setBounds(new Rectangle(20, 10, this.width - 100, 150));
        tableSp.getViewport().add(envVarTable, null);
        add(tableSp);
        
        this.InitTableData();
    }
    
    private void InitTableData()
    {
        try 
        {
            Statement stmt = conn.createStatement();
            
            String sql = "select IISC_ENV_VAR.Var_id as Var_id,IISC_ENV_VAR.Var_name as Var_Name,IISC_ENV_VAR.Dom_Id as Dom_Id,IISC_DOMAIN.Dom_mnem as Dom_mnem" +
            " from IISC_ENV_VAR, IISC_DOMAIN where IISC_ENV_VAR.Dom_Id=IISC_DOMAIN.Dom_id";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            
            Vector data = new Vector();
            
            while(rs.next())
            {
                VarDesc varDesc = new VarDesc();
                varDesc.var_id = rs.getInt("Var_id");
                varDesc.name = rs.getString("Var_Name");
                varDesc.dom_id = rs.getInt("Dom_Id");
                varDesc.domain = rs.getString("Dom_mnem");
                data.add(varDesc);
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
        int action = JOptionPane.showConfirmDialog(this, "Confirm delete", "Environment var", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (action != JOptionPane.OK_OPTION)
        {
            return;
        }
        
        try 
        {
            int row = this.envVarTable.getSelectedRow();
            VarDesc desc = (VarDesc)this.datm.data.get(row);
            
            Statement stmt = conn.createStatement();
            stmt.execute("delete from IISC_ENV_VAR where Var_id=" + desc.var_id);      
            
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
        EnvVarFrm diag = new EnvVarFrm("", "", "Add variable", this.parent.parent, conn, this.PR_id);
        Settings.Center(diag);
        diag.setVisible(true);
        
        if (diag.action == 0)
        {
            VarDesc desc = new VarDesc();
            boolean success = false;
            try 
            {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select max(Var_id) + 1 from IISC_ENV_VAR");
                
                if (rs.next())
                {
                    desc.var_id = rs.getInt(1);
                    desc.domain = diag.domain;
                    
                    ResultSet rs1 = stmt.executeQuery("select Dom_id from IISC_DOMAIN where Dom_mnem='" + desc.domain + "'");
                    
                    if (rs1.next())
                    {
                        desc.dom_id = rs1.getInt(1);
                    }
                    desc.name = diag.name;
                    
                    PreparedStatement ps = conn.prepareStatement("insert into IISC_ENV_VAR(Var_id, Var_name, Dom_id, PR_id) values (?,?,?,?)");
                    ps.setInt(1, desc.var_id);
                    ps.setString(2, desc.name);
                    ps.setInt(3, desc.dom_id);
                    ps.setInt(4, this.PR_id);
                    ps.execute();
                    success = true;
                }
            }
            catch(Exception ex)
            {
            }
            
            if (success)
            {
                this.datm.data.add(desc);
                this.datm.fireTableDataChanged();
            }
        }
    }
    
    private void editBtn_actionPerformed(ActionEvent e)
    {
        int row = this.envVarTable.getSelectedRow();
        
        if (row < 0 || row >= this.datm.data.size())
        {
            return;
        }
        
        VarDesc sel_desc = (VarDesc)this.datm.data.get(row);
        
        EnvVarFrm diag = new EnvVarFrm(sel_desc.name, sel_desc.domain, "Edit variable", this.parent.parent, conn, this.PR_id);
        Settings.Center(diag);
        diag.setVisible(true);
        
        if (diag.action == 0)
        {
            boolean success = false;
            try 
            {
                sel_desc.domain = diag.domain;
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery("select Dom_id from IISC_DOMAIN where Dom_mnem='" + sel_desc.domain + "'");
                
                if (rs1.next())
                {
                    sel_desc.dom_id = rs1.getInt(1);
                }
                sel_desc.name = diag.name;
                
                PreparedStatement ps = conn.prepareStatement("update IISC_ENV_VAR set Var_name=?,Dom_id=? where Var_id=?");
                ps.setString(1, sel_desc.name);
                ps.setInt(2, sel_desc.dom_id);
                ps.setInt(3, sel_desc.var_id);
                ps.execute();
                success = true;
            }
            catch(Exception ex)
            {
            }
            
            if (success)
            {
                this.datm.data.set(row, sel_desc);
                this.datm.fireTableDataChanged();
            }
        }
        
        /*AddButtonDef diag = new AddButtonDef(this.parent, "Edit button", this.Tob_id, this.Tf_id, this.PR_id, true, conn, sel_desc.btn_id, props);
        Settings.Center(diag);
        diag.setVisible(true);
        
        if (diag.option == 1)
        {
            VarDesc desc = new VarDesc();
            desc.btn_id = diag.button_Id;
            desc.x = Integer.toString(diag.b_x);
            desc.y = Integer.toString(diag.b_y);
            desc.w = Integer.toString(diag.b_w);
            desc.h = Integer.toString(diag.b_h);
            desc.function = diag.func_name;
            desc.label = diag.b_label;
            desc.item_group = diag.b_item_group;
            
            
        }*/
    }
    
    public class EnvVarTableModel extends AbstractTableModel 
    {
        private String[] headers;
        public Vector data;
        
        public EnvVarTableModel()
        {
            headers = new String[2];
            data = new Vector();            
            headers[0] = "Name";
            headers[1] = "Type";
        }
        
        public int getColumnCount()
        {
            return 2;
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
            VarDesc value = (VarDesc)data.get(x);
        
            if (y == 0)
            { 
                return value.name;
            } 
            
            return value.domain;
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
    
    public class VarDesc
    {
        public int var_id;
        public String name;
        public int dom_id;
        public String domain;
        
        public VarDesc()
        {
        }
    }

    /*public static void main(String[] args)
    {
        lookFeel();
        
        JDialog frame = new JDialog();
        frame.setSize(395, 250);
        frame.setLayout(null);
        
        EnvVarsPanel panel = new EnvVarsPanel(375, 250, Connect(), null, 3);
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
    */
    
    public class EnvVarFrm extends JDialog
    {
        public String name;
        public String domain;
        public String dafaultValue;
        public String iodefault;
        public JLabel nameLbl = new JLabel("Name");
        public JLabel domainLbl = new JLabel("Domain");
        public JTextField nameTxt = new JTextField("");
        public JButton saveBtn = new JButton("OK");
        public JButton cancelBtn = new JButton("Cancel");
        public JButton domainSelect = new JButton("...");
        public JComboBox domainCombo = new JComboBox();
        public int SAVE = 0;
        public int CANCEL = 1;
        public int action;
        public int id = -1;
        public Connection connection = null;
        public Frame parentS = null;
        private boolean isVar = false;
        
        public EnvVarFrm(String p_name, String p_domain, String title, JFrame parent,Connection con, int PR_id)
        {
            super(parent, title, true);
            
            if (title.endsWith("variable"))
            {
                  this.isVar = true;
            }
            this.id = PR_id;
            parentS = parent;
            connection = con;
            name = p_name;
            domain = p_domain;
            
            setSize(285, 160);
            getContentPane().setLayout(null);
            setResizable(false);
            action = CANCEL;
            
            nameLbl.setBounds(10, 10, 80, 20);
            nameTxt.setBounds(90, 10, 180, 20);
            nameTxt.setText(name);
            
            domainLbl.setBounds(10, 35, 80, 20);
            domainCombo.setBounds(90,35,150,20);
            domainSelect.setBounds(245,35,25,20);
            
            try
            {
                ResultSet rs;
                JDBCQuery query=new JDBCQuery(connection);
                //System.out.println("select Dom_mnem from IISC_DOMAIN where PR_id="+tree.ID + " order by dom_mnem");
                rs=query.select("select Dom_mnem from IISC_DOMAIN where PR_id="+ this.id + " order by dom_mnem");
                  
                while (rs.next())
                {
                    domainCombo.addItem(rs.getString("Dom_mnem"));
                }
                
                domainCombo.setSelectedItem(domain);
                
                query.Close();          
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
            saveBtn.setBounds(55, 95, 70, 25);
            cancelBtn.setBounds(160, 95, 70, 25);
            saveBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                saveBtn_actionPerformed(e);
              }
            });
            
            cancelBtn.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                cancelBtn_actionPerformed(e);
              }
            });
           
            domainSelect.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                domainSelect_actionPerformed(e);
              }
            });         
           
            getContentPane().add(nameLbl);
            getContentPane().add(domainLbl);
            getContentPane().add(nameTxt);
            getContentPane().add(domainCombo);
            getContentPane().add(saveBtn);
            getContentPane().add(cancelBtn);
            getContentPane().add(domainSelect);
            
            if (this.isVar)
            {
                saveBtn.setBounds(55, 95, 70, 25);
                cancelBtn.setBounds(160, 95, 70, 25);
                setSize(285, 160);
            }
            
            Settings.Center(this);
        }
        private void domainSelect_actionPerformed(ActionEvent e)
        {
            SearchTable ptype=new SearchTable((Frame)getParent(),"Select User Define Domain",true,connection,String.valueOf(id),"","",0);
            ptype.item = this.domainCombo;
            Settings.Center(ptype);
            ptype.type="Domain4";
            ptype.owner=this;
            ptype.setVisible(true);          
        }
        
        private void saveBtn_actionPerformed(ActionEvent e)
        {
            action = SAVE;
            name = nameTxt.getText();
            domain = domainCombo.getSelectedItem().toString();
            this.setVisible(false);
        }
        
        private void cancelBtn_actionPerformed(ActionEvent e)
        {
            action = CANCEL;
            setVisible(false);
        }
    }
}


