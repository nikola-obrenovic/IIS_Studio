package iisc;

import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JCheckBox;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import java.awt.*;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;


public class Parameter extends JPanel 
{
    private int width;
    private int height;
    private int PR_id;
    public int Tf_id;
    private Connection conn;
    private IISFrameMain parent;
    private ParameterTableModel datm;
    private JTable table;
    JScrollPane tableScroll;
    private JButton btnAddA;
    private JButton btnDelA;
    private JButton btnChangeA;
    private JButton btnUp;
    private JButton btnDown;
    private ImageIcon upIcon;
    private ImageIcon downIcon;
    private Form form;
    private Vector deleted = new Vector();
    
    public Parameter(int _width, int _height, IISFrameMain _parent, Form _form, Connection _conn, int _PR_id)
    {
        width = _width;
        height = _height;
        parent = _parent;
        form = _form;
        PR_id = _PR_id;
        Tf_id = form.id;
        conn = _conn;
        setBounds(0, 0, width, height);
        setLayout(null);
        InitTable();
        AddButtons();
    }
   
    public void InitTable()
    {
        datm = new ParameterTableModel();
        table = new JTable(datm);
        tableScroll = new JScrollPane();
            
        Vector data = new Vector();
        
             
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_PARAMETER,IISC_DOMAIN where IISC_PARAMETER.Dom_id=IISC_DOMAIN.Dom_id and Tf_id =" + Tf_id);
            
            while(rs.next())
            {   
                data.add(new ParValue(rs.getInt("Par_id"), rs.getInt("Dom_id"), this.Tf_id, rs.getInt("Tob_id"), rs.getInt("Att_id"), rs.getString("Dom_mnem"), rs.getString("Par_mnem"), rs.getString("Par_desc"), rs.getString("Par_def_value")));
            }
            rs.close();
            
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
        }  
        datm.populateData(data);
             
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setGridColor(new Color(0,0,0));
        table.setBackground(Color.white);
        //table.setAutoResizeMode(0);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoscrolls(true);
        table.getTableHeader().setResizingAllowed(true);
        //rowSMatt = table.getSelectionModel();
        table.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //table.setPreferredSize(new Dimension(300, 20));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        
        tableScroll.setBounds(35, 10, 535, 138);
        tableScroll.getViewport().add(table);
        add(tableScroll);
        
        //TableColumn tc = table.getColumnModel().getColumn(0);
        //System.out.println("sirina" + tc.getPreferredWidth());
        //tc.setWidth(20);
        //tc.setWidth(20);
        //tc.setPreferredWidth(20);
        //datm.fireTableStructureChanged();
        table.repaint();
    
    }  
    
    
    private void AddButtons()
    {
        btnAddA = new JButton();
        btnDelA = new JButton();
        btnChangeA = new JButton();
        btnAddA.setText("Add Parameter");
        btnAddA.setFont(new Font("SansSerif", 0, 11));
        btnAddA.setEnabled(true);
        btnAddA.setBounds(575, 10, 160, 25);
        btnAddA.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnAddA_actionPerformed(e);
            }
          });
        add(btnAddA, null);
        
        btnDelA.setText("Delete Parameter");
        btnDelA.setFont(new Font("SansSerif", 0, 11));
        btnDelA.setEnabled(true);
        btnDelA.setBounds(575, 64, 160, 25);
        btnDelA.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnDelA_actionPerformed(e);
            }
          });
        add(btnDelA, null);
        
        btnChangeA.setText("Edit Parameter");
        btnChangeA.setFont(new Font("SansSerif", 0, 11));
        btnChangeA.setEnabled(true);
        btnChangeA.setBounds(575, 37, 160, 25);
        btnChangeA.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnChangeA_actionPerformed(e);
            }
          });
        add(btnChangeA, null);
        
       
        btnUp = new JButton();
        upIcon = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
        btnUp.setIcon(upIcon);
        btnUp.setEnabled(true);
        btnUp.setBounds(5, 10, 25, 20);
        btnUp.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnUp_actionPerformed(e);
            }
          });
        add(btnUp, null);
       
        btnDown = new JButton();
        downIcon = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
        btnDown.setIcon(downIcon);
        btnDown.setEnabled(true);
        btnDown.setBounds(5, 128, 25, 20);
        btnDown.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnDown_actionPerformed(e);
            }
          });
        add(btnDown, null);
    }
    
    
    /************************************************************************/
    /*******            Update podataka koji su u tabeli          ***********/
    /************************************************************************/
    public void Update()
    {
        try
        {
            Statement statement = conn.createStatement();
            
            int len = deleted.size();
            for(int i =0; i < len; i++)
            {
                if (((Integer)deleted.get(i)).intValue() > 0)
                {
                    statement.execute("delete from IISC_PARAMETER where Par_id =" + ((Integer)deleted.get(i)).intValue());
                }
            }
            deleted.clear();
            
            len = datm.data.size();
            ParValue value;
            
            for(int i =0; i < len; i++)
            {
                value = ((ParValue)datm.data.get(i));
                if (value.getParId() > 0)
                {
                    if (value.dirty == ParValue.DIRTY)
                    {
                        statement.execute("update IISC_PARAMETER set Dom_id=" + value.getDomId()+ ",Tob_id=" + value.Tob_id + ",Att_id=" + value.Att_id + ",Par_mnem='" + value.getParMnem()+ "',Par_desc='" + value.getParDesc() + "',Par_def_value='" + value.getParDefValue()+ "' where Par_id =" + value.getParId());
                        value.dirty = ParValue.CLEAN; 
                    }
                }
            }
            
            int max = 0;
            
            try
            {
                Statement statement2 = conn.createStatement();
                ResultSet rs = statement2.executeQuery("select max(Par_id) from IISC_PARAMETER");
                if (rs.next())
                {
                    max = rs.getInt(1);
                }
                else
                {
                    max = 0;
                }
                rs.close();
                statement2.close();
            }
            catch(SQLException sqle)
            {
                max = 0;
            }
            max = max + 1;
            
            for(int i =0; i < len; i++)
            {
                value = ((ParValue)datm.data.get(i));
                if (value.getParId() < 0)
                {
                    statement.execute("insert into IISC_PARAMETER(Par_id,Dom_id,PR_id,Tf_id,Tob_id, Att_id,Par_mnem,Par_desc,Par_def_value)  values(" + max + "," + value.getDomId()+ "," + PR_id + "," + Tf_id + "," + value.Tob_id + "," + value.Att_id + ",'" + value.getParMnem()+ "','" + value.getParDesc() + "','" + value.getParDefValue()+ "')");
                    value.setParId(max);
                    value.dirty = ParValue.CLEAN;
                    max = max + 1;
                }
            }
            statement.close();
            
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
        }  
    
    }
    
    /***************************************************************************/
    /**************            Event Handlers              *********************/
    /***************************************************************************/
    private void btnAddA_actionPerformed(ActionEvent e)
    {
        ParValue value = new ParValue(-1,-1,-1, -1,-1, "","","","");
        ParForm dialog = new ParForm(value,"Add Parameter", parent, conn, PR_id, Tf_id);
        Settings.Center(dialog);
        dialog.setVisible(true);
        
        if (dialog.action == dialog.SAVE)
        {
            ParValue newPar = dialog.getValue();
            ParValue par;
            
            int size = datm.data.size();
            boolean exists = false;
            
            for(int i = 0; i < size; i++)
            {
                par = (ParValue)datm.data.get(i);
                
                if(par.Par_mnem.equals(newPar.Par_mnem) && par.Par_id != newPar.Par_id)
                {
                    exists = true;
                    break;
                }
            }
            
            if (exists)
            {
                JOptionPane.showMessageDialog(this,"Parameter allready exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            form.btnApply.setEnabled(true);
            form.btnSave.setEnabled(true);
            datm.data.add(dialog.getValue());
            datm.fireTableDataChanged();
        }
        dialog.dispose();
        
    }
    
    private void btnDelA_actionPerformed(ActionEvent e)
    {
        int rowIndex = table.getSelectedRow();
        if ((rowIndex < 0) || (rowIndex >= table.getRowCount()))
        {
            return;
        }
        
        int action = JOptionPane.showConfirmDialog(this, "Confirm delete", "Display Attributes", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (action == JOptionPane.OK_OPTION)
        {
            form.btnApply.setEnabled(true);
            form.btnSave.setEnabled(true);
            deleted.add(new Integer(((ParValue)(datm.data.get(rowIndex))).getParId()));
            datm.data.remove(rowIndex);
            int size = datm.data.size();
            
            datm.fireTableDataChanged();
        }
    }
    
    private void btnChangeA_actionPerformed(ActionEvent e)
    {
        int rowIndex = table.getSelectedRow();
        if ((rowIndex < 0) || (rowIndex >= table.getRowCount()))
        {
            return;
        }
        
        ParForm dialog = new ParForm((ParValue)datm.data.get(rowIndex),"Edit Parameter", parent, conn, PR_id, this.Tf_id);
        Settings.Center(dialog);
        dialog.setVisible(true);
        
        if (dialog.action == dialog.SAVE)
        {
            ParValue newPar = dialog.getValue();
            ParValue par;
            
            int size = datm.data.size();
            boolean exists = false;
            
            for(int i = 0; i < size; i++)
            {
                par = (ParValue)datm.data.get(i);
                
                if(par.Par_mnem.equals(newPar.Par_mnem))
                {
                    exists = true;
                    break;
                }
            }
            
            if (exists)
            {
                JOptionPane.showMessageDialog(this,"Parameter allready exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            form.btnApply.setEnabled(true);
            form.btnSave.setEnabled(true);
            ParValue value = dialog.getValue();
            value.dirty = ParValue.DIRTY;
            datm.data.setElementAt(dialog.getValue(), rowIndex);
            datm.fireTableDataChanged();
        }
        dialog.dispose();
    }
    
    private void btnUp_actionPerformed(ActionEvent e)
    {
        int rowIndex = table.getSelectedRow();
        datm.IncrementRow(rowIndex);
        
        if (rowIndex > 0)
        {
            form.btnApply.setEnabled(true);
            form.btnSave.setEnabled(true);
            table.getSelectionModel().setSelectionInterval(rowIndex - 1,rowIndex - 1);
        }
        table.repaint();
    }
    
    private void btnDown_actionPerformed(ActionEvent e)
    {
        int rowIndex = table.getSelectedRow();
        datm.DecrementRow(rowIndex);
        
        if (rowIndex + 1 < datm.data.size())
        {
            form.btnApply.setEnabled(true);
            form.btnSave.setEnabled(true);
            table.getSelectionModel().setSelectionInterval(rowIndex + 1,rowIndex + 1);
        }
    }
    
    /*************************************************************************/
    /*********                Klasa za table model                    ********/
    /*************************************************************************/
    private class ParameterTableModel extends AbstractTableModel
    {
        private String[] headers;
        private Vector data;
        
        public ParameterTableModel()
        {
            headers = new String[4];
            data = new Vector();
            headers[0] = "Name";
            headers[1] = "Domain";
            headers[2] = "Description";
            headers[3] = "Default value";
        }
        
        public int getColumnCount()
        {
            return 4;
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
            ParValue value = (ParValue)data.get(x);
  
            if (y == 0)
            { 
                return (Object)value.getParMnem();
            } 
            else
            {
                if (y == 1)
                {
                    return (Object)value.getDomMnem();
                }
                else
                {
                    if (y == 2)
                    {
                        return (Object)value.getParDesc();
                    }
                    else
                    {
                        return (Object)value.getParDefValue();
                    }
                }
            }
        }
        
        public void setValueAt(Object value, int row, int col) 
        {
        
            if (col == 0)
            { 
                ((ParValue)data.elementAt(row)).setParMnem((String)value);
                fireTableCellUpdated(row, col);
            } 
            else
            {
                if (col == 1)
                {
                    ((ParValue)data.elementAt(row)).setDomMnem((String)value);
                    fireTableCellUpdated(row, col);
                }
                else
                {
                    if (col == 2)
                    {
                        ((ParValue)data.elementAt(row)).setParDesc((String)value);
                        fireTableCellUpdated(row, col);
                    }
                    else
                    {
                        ((ParValue)data.elementAt(row)).setParDefValue((String)value);
                        fireTableCellUpdated(row, col);
                    }
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
        
        public void IncrementRow(int rowIndex)
        {
            if (rowIndex == 0) 
            {
                return;
            }
            
            ParValue temp1 = (ParValue)data.get(rowIndex);
            ParValue temp2 = (ParValue)data.get(rowIndex - 1);
            
            data.set(rowIndex, temp2);
            data.set(rowIndex - 1, temp1);
            fireTableDataChanged();
        }
        
        public void DecrementRow(int rowIndex)
        {
            if (rowIndex + 1 == data.size()) 
            {
                return;
            }
            
            ParValue temp1 = (ParValue)data.get(rowIndex);
            ParValue temp2 = (ParValue)data.get(rowIndex + 1);
            
            data.set(rowIndex, temp2);
            data.set(rowIndex + 1, temp1);
            fireTableDataChanged();
        }
    }
    
    private class ParValue
    {
        private int Par_id;
        private String Par_mnem;
        private String Par_desc;
        private String Par_def_value;
        private int Dom_id;
        private String Dom_mnem;
        private int dirty;
        private int Tob_id;
        private int Att_id;
        private int Tf_id;
        public static final int DIRTY = 0;
        public static final int CLEAN = 1;
        
        
        public ParValue(int _Par_id, int _Dom_id, int _Tf_id, int _Tob_id, int _Att_id, String _Dom_mnem, String _Par_mnem, String _Par_desc, String _Par_def_value)
        {
           Par_id = _Par_id;
           Par_mnem = _Par_mnem;
           Par_desc = _Par_desc;
           Par_def_value = _Par_def_value;
           Dom_id = _Dom_id;
           Dom_mnem = _Dom_mnem;
           Tf_id = _Tf_id;
           Tob_id = _Tob_id;
           Att_id = _Att_id;
        }
        
        public void setParId(int value)
        {
            Par_id = value;
        }
        
        public void setParMnem(String value)
        {
            Par_mnem = value;
        }
        
        public void setParDesc(String value)
        {
            Par_desc = value;
        }
        
        public void setParDefValue(String value)
        {
            Par_def_value = value;
        }
        
        public void setDomId(int value)
        {
            Dom_id = value;
        }
        
        public void setDomMnem(String value)
        {
            Dom_mnem = value;
        }
        
        public int getParId()
        {
            return Par_id;
        }
        
        public String getParMnem()
        {
            return Par_mnem;
        }
        
        public String getParDesc()
        {
            return Par_desc;
        }
        
        public String getParDefValue()
        {
            return Par_def_value;
        }
        
        public int getDomId()
        {
            return Dom_id;
        }
        
        public String getDomMnem()
        {
            return Dom_mnem;
        }
    }
    
    /******************************************************************/
    /********       Forma za editovanje vrijednosti iz tabele    ******/
    /******************************************************************/
     private class ParForm extends JDialog
     {
         private ParValue value;
         private Connection conn;
         private JComboBox domCombo = new JComboBox();
         private Vector domIds = new Vector();
         private JLabel domLbl = new JLabel("Domain");
         private JLabel nameLbl = new JLabel("Name");
         private JLabel descLbl = new JLabel("Description");
         private JLabel defValueLbl = new JLabel("Default value");
         private JButton searchBtn = new JButton("...");
         private JTextField nameTxt = new JTextField("");
         private JTextField descTxt = new JTextField("");
         private JTextField defValueTxt = new JTextField("");
         private JButton saveBtn = new JButton("OK");
         private JButton cancelBtn = new JButton("Cancel");
         private JComboBox attCombo = new JComboBox();
         private JLabel attLbl = new JLabel("Attribute");
         private int SAVE = 0;
         private int CANCEL = 1;
         private int action;
         private int PR_id;
         private int Tf_id;
         private IISFrameMain parent;
         private Vector attVec;
         
         public ParForm(ParValue p_Value, String title, IISFrameMain _parent, Connection _conn, int _PR_id, int _Tf_id)
         {
             super(_parent, title, true);
             parent = _parent;
             value = p_Value;
             conn = _conn;
             PR_id = _PR_id;
             Tf_id = _Tf_id;
             setSize(305, 230);
             getContentPane().setLayout(null);
             setResizable(false);
             action = CANCEL;
             
             domLbl.setBounds(10, 40, 90, 20);
             domCombo.setBounds( 100, 40, 155, 20);
             searchBtn.setBounds(260, 40,  30, 20);
             searchBtn.addActionListener(new ActionListener()
             {
               public void actionPerformed(ActionEvent e)
               {
                 searchBtn_actionPerformed(e);
               }
             });
             
             nameLbl.setBounds(10, 10, 90, 20);
             nameTxt.setBounds(100, 10, 190, 20);
             
             descLbl.setBounds(10, 70, 90, 20);
             descTxt.setBounds(100, 70, 190, 20);
             
             defValueLbl.setBounds(10, 100, 90, 20);
             defValueTxt.setBounds(100, 100, 190, 20);
             
             attLbl.setBounds(10, 130, 90, 20);
             attCombo.setBounds(100, 130, 190, 20);
             
             nameTxt.setText(value.Par_mnem);
             descTxt.setText(value.Par_desc);
             defValueTxt.setText(value.Par_def_value);
             
             saveBtn.setBounds(53, 170,  70, 25);
             cancelBtn.setBounds(176, 170, 70, 25);
             
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
            
             getContentPane().add(domLbl);
             getContentPane().add(domCombo);
             getContentPane().add(searchBtn);
             getContentPane().add(nameLbl);
             getContentPane().add(nameTxt);
             getContentPane().add(descTxt);
             getContentPane().add(descLbl);
             getContentPane().add(defValueTxt);
             getContentPane().add(defValueLbl);
             getContentPane().add(saveBtn);
             getContentPane().add(cancelBtn);
             getContentPane().add(attLbl);
             getContentPane().add(attCombo);
             
             InitCombo();
             InitAttCombo();
         }
         
         /* Inicijalizacija kombo boksa koji sadrzi tipove formi */
         private void InitCombo()
         {
            try
             {
                 Statement statement = conn.createStatement();
                 ResultSet rs = statement.executeQuery("select * from IISC_DOMAIN  where PR_id="+ PR_id);
                 
                 //domCombo.addItem("");
                 int Dom_id, i = 0;
                 
                 while(rs.next())
                 {
                     domCombo.addItem(rs.getString("Dom_mnem"));
                     
                     Dom_id = rs.getInt("Dom_id");
                     domIds.add(new Integer(Dom_id));
                     
                     if (value.Dom_id == Dom_id)
                     {
                         domCombo.setSelectedIndex(i);
                     }
                     i = i + 1;
                 }
             }
             catch(SQLException e)
             {
                 System.out.println("\n" + e.toString() + "\n");
             }
             
             repaint();
         }
         
         public void InitAttCombo()
         {
             int i = 0;
             attVec = new Vector();
             int selIndex = 0;
             
             try
             {
                 Statement statement = conn.createStatement();
                 ResultSet rs = statement.executeQuery("select IISC_ATTRIBUTE.Att_id,IISC_ATTRIBUTE.Att_mnem,IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id,IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_mnem  from IISC_ATT_TOB,IISC_ATTRIBUTE,IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_ATT_TOB.Tf_id=" + Tf_id + " and IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id and IISC_ATT_TOB.Tob_id=IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id");
                 
                 while(rs.next())
                 {   
                     Attribute a = new Attribute(rs.getInt("Att_id"), rs.getString("Att_mnem"), rs.getInt("Tob_id"), rs.getString("Tob_mnem"));
                     attVec.add(a);
                     
                     if ( a.Att_id == value.Att_id && a.Tob_id == value.Tob_id )
                     {
                         selIndex = i; 
                     }
                     
                     i = i + 1;
                 }
                 
                 rs.close();
                 
             }
             catch(SQLException sqle)
             {
                 System.out.println("Sql exception :" + sqle);
             }
             
             int size = attVec.size();
             
             for(i = 0; i < size; i++)
             {
                 attCombo.addItem(((Attribute)attVec.get(i)).Att_mnem + "(" + ((Attribute)attVec.get(i)).Tob_mnem + ")");
             }
             
             if (size > 0 )
             {
                attCombo.setSelectedIndex(selIndex);
             }
         }
         
         private void saveBtn_actionPerformed(ActionEvent e)
         {
             String mnem;
             mnem = nameTxt.getText();
             boolean exists = false;
             
             if ((value.Par_id == -1) || (!value.Par_mnem.endsWith(mnem)))
             {            
                 try
                 {
                     Statement statement = conn.createStatement();
                     ResultSet rs = statement.executeQuery("select Par_id from IISC_PARAMETER where Par_mnem='" + mnem + "' and TF_id=" + Tf_id);
                     if (rs.next())
                     {
                         exists = true;
                     }
                     rs.close();
                     statement.close();
                 }
                 catch(SQLException sqle)
                 {
                 }
                   
             }
             
             if (exists)
             {
                 JOptionPane.showMessageDialog(this,"Parameter named: " + mnem + " allready exits", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
             }
             
             action = SAVE;
             
             value = new ParValue(value.Par_id,((Integer)domIds.get(domCombo.getSelectedIndex())).intValue(), Tf_id, ((Attribute)attVec.get(attCombo.getSelectedIndex())).Tob_id, ((Attribute)attVec.get(attCombo.getSelectedIndex())).Att_id, domCombo.getSelectedItem().toString(), nameTxt.getText(),descTxt.getText(), defValueTxt.getText()); 
             this.setVisible(false);
         }
         
         private void cancelBtn_actionPerformed(ActionEvent e)
         {
             action = CANCEL;
             this.setVisible(false);
         }
         
         private void searchBtn_actionPerformed(ActionEvent e)
         {
             SearchTable stb = new SearchTable(parent,"Select Domain", true,conn);
             stb.type = "Domain";
             stb.item = domCombo;
             Settings.Center(stb);
             stb.setVisible(true);
         }
         
         public ParValue getValue()
         {
             return value;
         }
     }
    
    private class Attribute
    {
        public int Att_id;
        public int Tob_id;
        public String Att_mnem;
        public String Tob_mnem;
        
        private Attribute(int _Att_id, String _Att_mnem, int _Tob_id, String _Tob_mnem)
        {
            Att_id = _Att_id;
            Tob_id = _Tob_id;
            Tob_mnem = _Tob_mnem;
            Att_mnem = _Att_mnem;
        }
    }
}