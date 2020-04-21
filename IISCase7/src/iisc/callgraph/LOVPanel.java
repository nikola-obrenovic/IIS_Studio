package iisc.callgraph;

import iisc.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JComboBox;
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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.JRadioButton;

public class LOVPanel extends JPanel 
{
    private int width;
    private int height;
    public int Att_id;
    private int Tob_id;
    private int PR_id;
    private int Tf_id;
    private int LV_Tf_id;
    private Connection conn;
    private IISFrameMain parent;
    private JComboBox tfCombo;
    private JComboBox attCmb;
    private JComboBox rAttCmb;
    private JLabel attLb;
    private JLabel rAttLb;
    private JButton searchBtn;
    private JLabel lb;
    private JLabel editLb;
    private JLabel searchLb;
    private JLabel mandatoryLb;
    private JCheckBox editChb;
    private JCheckBox searchChb;
    private JCheckBox mandatoryChb;
    private JPanel borderPn;
    private Vector tfids = new Vector();
    private Vector attids = new Vector();
    private Vector retattids = new Vector();
    private JTextField regTxt;
    private JLabel regLb;
    private int LV_id;
    private AttPairTableModel datm;
    private JTable table;
    JScrollPane tableScroll;
    private JButton addBtn;
    private JButton delBtn;
    private AttType attTypeFrm;
    private Vector appSys = new Vector();
    private ImageIcon imageUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
    private ImageIcon imageDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
    private JPanel filterPn;
    private JRadioButton ovl = new JRadioButton();
    private JRadioButton dvl = new JRadioButton();
    private JLabel ovlLbl = new JLabel("Only via LOV");
    private JLabel dvlLbl = new JLabel("Directly & via LOV");
    private String Att_mnem;
    
    //private JRadioButton jRadioButton1 = new JRadioButton();
    
    public LOVPanel(int _width, int _height, int _Att_id, int _Tob_id, int _Tf_id, int _PR_id, Connection _Conn, IISFrameMain _Parent, AttType _attTypeFrm)
    {
        Att_id = _Att_id;
        Tob_id = _Tob_id;
        PR_id = _PR_id;
        Tf_id = _Tf_id;
        parent = _Parent;
        attTypeFrm = _attTypeFrm;
        conn = _Conn;
        width = _width;
        height = _height;
        setLayout(null);
        setBounds(10,10,width, height);
        
        lb = new JLabel("LOV Form Type:");
        lb.setSize(100, 20);
        lb.setBounds(10,10,100, 20);
        lb.setFont(new Font("SansSerif", 0, 11));
        add(lb);
        
        searchBtn = new JButton();
        searchBtn.setText("...");
        //searchBtn.setSize(15,50);
        searchBtn.setFont(new Font("SansSerif", 0, 11));
        searchBtn.setBounds(345,10,30, 20);
        add(searchBtn);
        searchBtn.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              search_ActionPerformed(ae);
            }
          });
        
        borderPn = new JPanel();
        borderPn.setLayout(null);
        borderPn.setBorder(new LineBorder(new Color(124, 124, 124), 1));
        borderPn.setBounds(10, 40, 365, 75);
        
        ovl.setBounds(25, 25, 20, 20);
        ovlLbl.setBounds(50, 25, 150, 20);
        dvl.setBounds(25, 45, 20, 20);
        ovl.setSelected(true);
        dvlLbl.setBounds(50, 45, 150, 20);
        dvl.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
              EnableButtons();
              if (dvl.isSelected())
              {
                  ovl.setSelected(false);
                  mandatoryChb.setEnabled(true);
                  searchChb.setEnabled(true);
              }
              else
              {
                  ovl.setSelected(true);
                  mandatoryChb.setEnabled(false);
                  searchChb.setEnabled(false);
              }
          }
        });
        
        borderPn.add(ovl);
        borderPn.add(ovlLbl);
        borderPn.add(dvl);
        borderPn.add(dvlLbl);
        
        filterPn = new JPanel();
        filterPn.setLayout(null);
        filterPn.setBorder(new LineBorder(new Color(124, 124, 124), 1));
        filterPn.setBounds(200, 0, 165, 75);
        borderPn.add(filterPn);
        
        editLb = new JLabel("Edit Attribute Value");
        editLb.setBounds(5,0,190, 20);
        editLb.setFont(new Font("SansSerif", 0, 11));
        borderPn.add(editLb);
        
        editChb = new JCheckBox("");
        editChb.setSize(20, 20);
        editChb.setBounds(60,5,20, 20);
        editChb.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                editChb_itemStateChanged(e);
            }
        });
        editChb.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              EnableButtons();
          }
        });
        //borderPn.add(editChb);
        
        searchLb = new JLabel("Filter LOV by value");
        searchLb.setBounds(35,10,125, 20);
        searchLb.setFont(new Font("SansSerif", 0, 11));
        filterPn.add(searchLb);
        
        searchChb = new JCheckBox("");
        searchChb.setSize(20, 20);
        searchChb.setBounds(10,10,20, 20);
        searchChb.setEnabled(false);
        searchChb.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              EnableButtons();
          }
        });
        filterPn.add(searchChb);
        
        mandatoryLb = new JLabel("Validate value by LOV");
        mandatoryLb.setBounds(35,40,125, 20);
        mandatoryLb.setFont(new Font("SansSerif", 0, 11));
        filterPn.add(mandatoryLb);
        
        mandatoryChb = new JCheckBox("");
        mandatoryChb.setSize(20, 20);
        mandatoryChb.setBounds(10,40,20, 20);
        mandatoryChb.setEnabled(false);
        mandatoryChb.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              EnableButtons();
          }
        });
        filterPn.add(mandatoryChb);
        
        add(borderPn);
        
        regLb = new JLabel("Restrict expression:");
        regLb.setBounds(10, 125, 120, 20);
        regLb.setFont(new Font("SansSerif", 0, 11));
        add(regLb);
        
        regTxt = new JTextField("");
        regTxt.setBounds(120, 125, 255, 20);
        regTxt.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
                EnableButtons();
            }
        });
        add(regTxt);
        
        tfCombo = new JComboBox();
        tfCombo.setBounds(115,10,225,20);
        tfCombo.setEditable(false);
        add(tfCombo);
        
        attLb = new JLabel("Lov attribute");
        attLb.setBounds(10,155,180, 15);
        attLb.setFont(new Font("SansSerif", 0, 11));
        add(attLb);
        
        attCmb = new JComboBox();
        attCmb.setBounds(10,170,180, 20);
        attCmb.setFont(new Font("SansSerif", 0, 11));
        add(attCmb);
        
        rAttLb = new JLabel("Return attribute");
        rAttLb.setBounds(195, 155, 180, 15);
        rAttLb.setFont(new Font("SansSerif", 0, 11));
        add(rAttLb);
        
        rAttCmb = new JComboBox();
        rAttCmb.setBounds(195, 170,180, 20);
        rAttCmb.setFont(new Font("SansSerif", 0, 11));
        add(rAttCmb);
        
        addBtn = new JButton();
        addBtn.setText("");
        //searchBtn.setSize(15,50);
        addBtn.setFont(new Font("SansSerif", 0, 10));
        addBtn.setBounds(112, 195, 25, 25);
        add(addBtn);
        addBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
              add_ActionPerformed(ae);
            }
        });
        addBtn.setIcon(imageDown);
        
        delBtn = new JButton();
        delBtn.setText("");
        //searchBtn.setSize(15,50);
        delBtn.setFont(new Font("SansSerif", 0, 11));
        delBtn.setIcon(imageUp);
        delBtn.setBounds(234, 195, 25, 25);
        add(delBtn);
        delBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
              del_ActionPerformed(ae);
            }
        });
        
        
        getLV_id();
        InitTable();
        InitCombo();
        InitAttCombo();
        InitRetAttCombo();
        
        tfCombo.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            tfCombo_actionPerformed(e);
          }
        });
        
        attCmb.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            attCmb_actionPerformed(e);
          }
        });
        
        /*tfCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              tfCombo_actionPerformed(e);
            }
        });
        */
        if (LV_id == 0)
        {
            DisableAll();
        }
        
        ovl.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
              EnableButtons();
              
              if (ovl.isSelected())
              {
                  dvl.setSelected(false);
                  mandatoryChb.setEnabled(false);
                  searchChb.setEnabled(false);
              }
              else
              {
                  dvl.setSelected(true);
                  mandatoryChb.setEnabled(true);
                  searchChb.setEnabled(true);
              }
          }
        });
        
        if ( ( rAttCmb.getItemCount() == 0 ) || ( attCmb.getItemCount() == 0) )
        {
            addBtn.setEnabled(false);
        }
        
        if ( datm.data.size() == 1 )
        {
            delBtn.setEnabled(false);
        }
        repaint();
    }
    
    private void getLV_id()
    {
        try
        {
            Statement statement2 = conn.createStatement();
            ResultSet rs2 = statement2.executeQuery("select LV_id from IISC_ATT_TOB where Att_id=" + Att_id + " and Tf_id=" + Tf_id + " and Tob_id=" + Tob_id);
            if (rs2.next())
            {
                LV_id = rs2.getInt(1);
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select * from IISC_LIST_OF_VALUES where LV_id=" + LV_id);
                int checked;
                
                if (rs.next())
                {
                    LV_Tf_id = rs.getInt("Tf_id");
                    checked = rs.getInt("LV_type");
                    
                    if (checked == 1)
                    {
                        dvl.setSelected(true);
                    }
                    else
                    {
                        ovl.setSelected(true);
                    }
                    
                    checked = rs.getInt("LV_search");
                    
                    if (checked == 1)
                    {
                        searchChb.setSelected(true);
                    }
                    
                    checked = rs.getInt("LV_mand_validation");
                    
                    if (checked == 1)
                    {
                        mandatoryChb.setSelected(true);
                    }
                    
                    regTxt.setText(rs.getString("LV_regular_expression"));                    
                }
                else
                {
                    LV_Tf_id = 0;
                }
                rs.close();
            }
            else
            {
                LV_id = 0;
            }
            rs2.close();
            
            
        }
        catch(SQLException e)
        {
            LV_id = 0;
        }       
    }
    
    /* Inicijalizacija kombo boksa koji sadrzi tipove formi */
    private void InitCombo()
    {
       try
        {
            Statement statement = conn.createStatement();
            Statement statement2 = conn.createStatement();
            Statement statement3 = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ PR_id + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id");
            
            ResultSet rs2;
            ResultSet rs3;
            
            tfCombo.addItem("");
            
            if(LV_id == 0)
            {
                tfCombo.setSelectedIndex(0);
            }
            
            int i =1;
            int lTf_id;
            
            while(rs.next())
            {
                
                lTf_id = rs.getInt("Tf_id");
                
                if ( Tf_id == lTf_id)
                {
                    continue;
                }
                
                //rs2 = statement2.executeQuery("select Att_id from IISC_ATT_TOB,IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_ATT_TOB.Tob_id=IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_superord='' and IISC_COMPONENT_TYPE_OBJECT_TYPE.Tf_id=" + lTf_id + " and Att_id=" + Att_id);
                rs2 = statement2.executeQuery("select Tob_id from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tf_id=" + lTf_id + " order by Tob_superord");
                
                if( rs2.next())
                {
                    int lvTob_id = rs2.getInt(1);
                    rs3 = statement3.executeQuery("select Att_id from IISC_ATT_TOB where IISC_ATT_TOB.Tob_id=" + lvTob_id + " and Att_id=" + Att_id);
                    
                    if (rs3.next())
                    {
                        tfCombo.addItem(rs.getString("Tf_mnem"));
                        tfids.add(new Integer(lTf_id));
                        appSys.add(rs.getString("AS_name"));
                    
                        if (lTf_id == LV_Tf_id)
                        {
                            tfCombo.setSelectedIndex(i);
                        }
                        i = i + 1;
                    }
                    rs3.close();
                }
                rs2.close();
                
                
            }
            statement.close();
            statement2.close();
            statement3.close();
        }
        catch(SQLException e)
        {
            System.out.println("\n" + e.toString() + "\n");
        }
        
        repaint();
    }
  
    private void InitAttCombo()
    {
        try
        {
            Statement statement2 = conn.createStatement();
            Statement statement3 = conn.createStatement();
            Statement statement4 = conn.createStatement();
            
            if(LV_Tf_id == 0)
            {
                return;
            }
            
            ResultSet rs2;
            ResultSet rs3;
            ResultSet rs4;
            int LV_Att_id;
            int LV_Tob_id;
            
            String LV_Att_mnem;
            
            rs4 = statement4.executeQuery("select Tob_id from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tf_id=" + LV_Tf_id + " order by Tob_superord");
                
            if( rs4.next())
            {
                LV_Tob_id = rs4.getInt(1);
                
                rs2 = statement2.executeQuery("select Att_id from IISC_ATT_TOB where Tob_id=" + LV_Tob_id);
        
                while(rs2.next())
                {
                    LV_Att_id = rs2.getInt(1);
                    
                    rs3 = statement3.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + LV_Att_id);
                    
                    if (rs3.next())
                    {
                        LV_Att_mnem = rs3.getString(1);
                        
                        attCmb.addItem(LV_Att_mnem);
                        attids.add(new Integer(LV_Att_id));
                    }
                    rs3.close();
                }
                rs2.close();
                
            }
            
            if (attCmb.getItemCount() == 0)
            {
                addBtn.setEnabled(false);
            }
            else
            {
               attCmb.setSelectedIndex(0);
            }
        }   
        catch(SQLException e)
        {
            System.out.println("\n" + e.toString() + "\n");
        }
    
    }
    
    
    private void InitRetAttCombo()
    {
        try
        {
            Statement statement = conn.createStatement();
            Statement statement2 = conn.createStatement();
            Statement statement3 = conn.createStatement();
            Statement statement4 = conn.createStatement();
            
            ResultSet rs2;
            ResultSet rs3;
            ResultSet rs4;
            int LV_Att_id;
            String LV_Att_mnem;
            int exists;
            int i = 0;
            String selAtt = "";
            
            try
            {
                if ( attCmb.getSelectedIndex() != -1)
                {
                    selAtt = attCmb.getSelectedItem().toString();
                }
                else
                {
                    selAtt = attCmb.getItemAt(0).toString();
                }
            }
            catch(Exception ex)
            {
                
            }
            
            rs4 = statement4.executeQuery("select Dom_id from IISC_ATTRIBUTE where Att_mnem='" + selAtt + "' and PR_id=" + PR_id);
            
            if ( rs4.next())
            {
                int LV_Dom_id = rs4.getInt(1);
                rs2 = statement2.executeQuery("select Att_id from IISC_ATT_TOB where Tf_id=" + Tf_id + " and Tob_id=" + Tob_id);
        
                while(rs2.next())
                {
                    LV_Att_id = rs2.getInt(1);
                    
                    rs3 = statement3.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + LV_Att_id + " and Dom_id=" + LV_Dom_id);
                    
                    if (rs3.next())
                    {
                        LV_Att_mnem = rs3.getString(1);
                        
                        exists = 0;
                        for(i = 0; i < datm.data.size(); i++)
                        {
                            if (((AttPair)datm.data.get(i)).getRetAttId() == LV_Att_id)
                            {
                                exists = 1;
                                break;
                            }
                        }
                        
                        if (exists == 0)
                        {
                            rAttCmb.addItem(LV_Att_mnem);
                            retattids.add(new Integer(LV_Att_id));
                        }
                    }
                    rs3.close();
                }
                rs2.close();
                
                if (rAttCmb.getItemCount() == 0)
                {
                    addBtn.setEnabled(false);
                }
                else
                {
                    addBtn.setEnabled(true);
                }
            }
            statement4.close();
        }   
        catch(SQLException e)
        {
            System.out.println("\n" + e.toString() + "\n");
        }
    
    }
    /************************************************************************/
    /****                       Event hendleri                          *****/
    /************************************************************************/
    public void search_ActionPerformed(ActionEvent ae)
    {
        SearchTable stb = new SearchTable(parent,"Select Form Type", true,conn);
        stb.type = "Form Type";
        JComboBox temp = new JComboBox();
        temp.addItem("");
        temp.setSelectedIndex(0);
        
        for(int i = 0;i < appSys.size(); i++)
        {
            temp.addItem(tfCombo.getItemAt(i+1).toString().trim() + " (" + appSys.get(i)+")");
        }
        stb.item = temp;
        Settings.Center(stb);
        stb.setVisible(true);
        //System.out.println(temp.getSelectedItem().toString());
        if (temp.getSelectedIndex() > 0)
        {
            if (tfCombo.getSelectedIndex() != temp.getSelectedIndex())
            {
                tfCombo.setSelectedIndex(temp.getSelectedIndex());
            }
        }
        
    }
    
    public void add_ActionPerformed(ActionEvent ae)
    {
        int i1 = attCmb.getSelectedIndex();
        int i2 = rAttCmb.getSelectedIndex();
        
        if (i2 < 0)
        {
            return;
        }
        
        if (i1 < 0)
        {
            return;
        }
        
        attTypeFrm.btnApply.setEnabled(true);
        attTypeFrm.btnSave.setEnabled(true);
        AttPair pair = new AttPair(((Integer)attids.get(i1)).intValue(),((Integer)retattids.get(i2)).intValue(), attCmb.getSelectedItem().toString(),rAttCmb.getSelectedItem().toString());
        datm.data.add(pair);
        
        rAttCmb.removeItemAt(i2);
        retattids.remove(i2);
        rAttCmb.repaint();
        datm.fireTableDataChanged();
        repaint();
        
        if (rAttCmb.getItemCount() == 0)
        {
            addBtn.setEnabled(false);
        }
        delBtn.setEnabled(true);
        
    }
    
    public void del_ActionPerformed(ActionEvent ae)
    {
        int rowIndex = table.getSelectedRow();
        
        if ((rowIndex <= 0) || (rowIndex >= table.getRowCount()))
        {
            return;
        }
      
        int action = JOptionPane.showConfirmDialog(this, "Confirm delete", "Display Attributes", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
      
        if (action == JOptionPane.OK_OPTION)
        {   
            attTypeFrm.btnApply.setEnabled(true);
            attTypeFrm.btnSave.setEnabled(true);
            AttPair pair = (AttPair)datm.data.get(rowIndex);
            datm.data.remove(rowIndex);
            rAttCmb.addItem(pair.getRetAttMnem());
            retattids.add(new Integer(pair.getRetAttId()));
            rAttCmb.repaint();
            datm.fireTableDataChanged();
            repaint();
        }  
        
        if (datm.data.size() == 1)
        {
            delBtn.setEnabled(false);
        }
        else
        {
            if (rowIndex == datm.data.size())
            {
                rowIndex = rowIndex - 1;
            }
            table.getSelectionModel().setSelectionInterval(rowIndex,rowIndex);
            
        }
        addBtn.setEnabled(true);
        
    }
    
    private void editChb_itemStateChanged(ItemEvent e)
    {
        if (editChb.isSelected())
        {
            searchChb.setEnabled(true);
            mandatoryChb.setEnabled(true);
        }
        else
        {
            searchChb.setEnabled(false);
            mandatoryChb.setEnabled(false);
        }
    }
    
    private void EnableButtons()
    {
        attTypeFrm.btnApply.setEnabled(true);
        attTypeFrm.btnSave.setEnabled(true);
    }
    
    private void tfCombo_actionPerformed(ItemEvent e)
    {
        int i = tfCombo.getSelectedIndex();
        attTypeFrm.btnApply.setEnabled(true);
        attTypeFrm.btnSave.setEnabled(true);
        
        if (i < 0)
        {
            LV_Tf_id = 0;
            return;
        }
        
        if (i == 0)
        {
            LV_Tf_id = 0;
            DisableAll();
            return;
        }
        
        EnableAll();
        LV_Tf_id = ((Integer)tfids.get(i - 1)).intValue();
        datm.data.clear();
        AddDefaultRowToTable();
        
        attCmb.removeAllItems();
        attids.clear();
        InitAttCombo();
        
    }
    
    private void attCmb_actionPerformed(ItemEvent e)
    {
        
        retattids.clear();
        rAttCmb.removeAllItems();
        InitRetAttCombo();
        
    }
    
    private void AddDefaultRowToTable()
    {
        String Att_mnem = "";
        
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + Att_id);
            
            if (rs.next())
            {
                Att_mnem = rs.getString(1);
            }
        }
        catch(Exception ex)
        {
            
        }
            
        AttPair pair = new AttPair(Att_id, Att_id, Att_mnem, Att_mnem);
        datm.data.add(pair);
        datm.fireTableDataChanged();
    }
    
    public void DisableAll()
    {
        searchChb.setEnabled(false);
        ovl.setEnabled(false);
        dvl.setEnabled(false);
        mandatoryChb.setEnabled(false);
        searchChb.setSelected(false);
        editChb.setSelected(false);
        mandatoryChb.setSelected(false);
        regTxt.setText("");
        regTxt.setEnabled(false);
        attCmb.removeAllItems();
        attCmb.setEnabled(false);
        rAttCmb.removeAllItems();
        rAttCmb.setEnabled(false);
        addBtn.setEnabled(false);
        delBtn.setEnabled(false);
        table.setEnabled(false);
        datm.data.clear();
        datm.fireTableDataChanged();
    }
    
    public void EnableAll()
    {
        editChb.setEnabled(true);
        if (dvl.isSelected())
        {
            searchChb.setEnabled(true);
            mandatoryChb.setEnabled(true);
        }
        ovl.setEnabled(true);
        dvl.setEnabled(true);
        regTxt.setEnabled(true);
        attCmb.setEnabled(true);
        rAttCmb.setEnabled(true);
        addBtn.setEnabled(true);
        delBtn.setEnabled(true);
        table.setEnabled(true);
    }
  
    /************************************************************************/
    /****                    Inicijalizacija tabele                      ****/
    /************************************************************************/
    private void InitTable()
    {
        datm = new AttPairTableModel();
        table = new JTable(datm);
        tableScroll = new JScrollPane();
            
        Vector data = new Vector();
        AttPair value;
        
        AddDefaultRowToTable();
        
        if (LV_id != 0)
        {      
            try
            {
                Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                Statement statement2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = statement.executeQuery("select * from IISC_LV_RETURN where LV_id =" + LV_id);
                ResultSet rs2;
                int LV_Att_id;
                int LV_ret_Att_id;
                String Att_mnem = "";
                String ret_Att_mnem = "";
                
                while(rs.next())
                {
                    LV_Att_id = rs.getInt("Att_id");
                    LV_ret_Att_id = rs.getInt("LV_return_att_id");
                    
                    rs2 = statement2.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id =" + LV_Att_id);
                    
                    if (rs2.next())
                    {
                        Att_mnem = rs2.getString(1);
                    }
                    
                    rs2 = statement2.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id =" + LV_ret_Att_id);
                    
                    if (rs2.next())
                    {
                        ret_Att_mnem = rs2.getString(1);
                    }
                    rs2.close();
                    
                    if ( LV_Att_id == Att_id )
                    {
                        data.add(0, new AttPair(LV_Att_id, LV_ret_Att_id, Att_mnem, ret_Att_mnem));
                    }
                    else
                    {
                        data.add(new AttPair(LV_Att_id, LV_ret_Att_id, Att_mnem, ret_Att_mnem));
                    }
                }
                rs.close();
                
            }
            catch(SQLException sqle)
            {
                System.out.println("Sql exception :" + sqle);
            }
        }     
        datm.populateData(data);
        
        if (data.size() == 0)
        {
            delBtn.setEnabled(false);
        }
        
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
        
        tableScroll.setBounds(10, 225, 365, 101);
        tableScroll.getViewport().add(table);
        add(tableScroll);
        
        TableColumn tc = table.getColumnModel().getColumn(0);
        //System.out.println("sirina" + tc.getPreferredWidth());
        //tc.setWidth(20);
        tc.setWidth(20);
        tc.setPreferredWidth(20);
        datm.fireTableStructureChanged();
        table.repaint();
    
    }  
    
    /*************************************************************************/
    /*********              Unos podataka u bazu                      ********/
    /*************************************************************************/
    public void Update()
    {
        try
        {
            int index = tfCombo.getSelectedIndex();
            
            if (index == 0)
            {
                if (LV_id == 0)
                {
                    return;
                }
                
                Statement statement = conn.createStatement();
                statement.execute("update IISC_ATT_TOB set LV_ID=0 where Att_id=" + Att_id + " and Tf_id=" + Tf_id + " and Tob_id=" + Tob_id);
                statement.execute("delete from IISC_LIST_OF_VALUES where LV_id=" + LV_id);
                statement.execute("delete from IISC_LV_RETURN where LV_id=" + LV_id);
                statement.close();
                return;
            } 
            
            
            int i1 = 0;
            if (dvl.isSelected())
            {
                i1 = 1;
            }
            
            int i2 = 0;
            if (searchChb.isSelected())
            {
                i2 = 1;
            }
            
            int i3 = 0;
            if (mandatoryChb.isSelected())
            {
                i3 = 1;
            }
            
            if(LV_id == 0)
            {
                try
                {
                    Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = statement.executeQuery("select max(LV_id) from IISC_LV_RETURN");
                    if (rs.next())
                    {
                        LV_id = rs.getInt(1);
                        
                    }
                    else
                    {
                        LV_id = 0;
                    }
                    rs.close();
                    statement.close();
                }
                catch(SQLException sqle)
                {
                    LV_id = 0;
                }
                
                LV_id = LV_id + 1;
                
                Statement statement2 = conn.createStatement();
                System.out.println("insert into IISC_LIST_OF_VALUES(LV_id,PR_id,Tf_id,LV_type,LV_search,LV_regular_expression,LV_mand_validation) values(" + LV_id + "," + PR_id + "," + ((Integer)tfids.get(index - 1)).intValue() + "," + i1 + "," + i2 + ",'" + regTxt.getText() + "'," + i3 +")");
                statement2.execute("insert into IISC_LIST_OF_VALUES(LV_id,PR_id,Tf_id,LV_type,LV_search,LV_regular_expression,LV_mand_validation) values(" + LV_id + "," + PR_id + "," + ((Integer)tfids.get(index - 1)).intValue() + "," + i1 + "," + i2 + ",'" + regTxt.getText() + "'," + i3 +")");
                statement2.execute("update IISC_ATT_TOB set LV_ID=" + LV_id + " where Att_id=" + Att_id + " and Tf_id=" + Tf_id + " and Tob_id=" + Tob_id);
                int i;
                
                for(i = 0; i < datm.data.size(); i++)
                {
                    statement2.execute("insert into IISC_LV_RETURN(LV_id, Att_id, LV_return_att_id) values(" + LV_id + "," + ((AttPair)datm.data.get(i)).getAttId() + "," + ((AttPair)datm.data.get(i)).getRetAttId() + ")");
                }
                statement2.close();
                return;
            }
            
            Statement statement2 = conn.createStatement();
            statement2.execute("update IISC_LIST_OF_VALUES set Tf_id =" + tfids.get(index - 1) + ",LV_type=" + i1 + ",LV_search=" + i2 + ",LV_regular_expression='" + regTxt.getText() + "',LV_mand_validation=" + i3 + " where LV_id=" + LV_id);
            statement2.execute("delete from IISC_LV_RETURN where LV_id=" + LV_id);
            int i;
            
            for(i = 0; i < datm.data.size(); i++)
            {
                statement2.execute("insert into IISC_LV_RETURN(LV_id, Att_id, LV_return_att_id) values(" + LV_id + "," + ((AttPair)datm.data.get(i)).getAttId() + "," + ((AttPair)datm.data.get(i)).getRetAttId() + ")");
            }
            statement2.close();
                
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
        }
    }
    
    /*************************************************************************/
    /*********                Klasa za table model                    ********/
    /*************************************************************************/
    private class AttPairTableModel extends AbstractTableModel
    {
        private String[] headers;
        private Vector data;
        
        public AttPairTableModel()
        {
            headers = new String[2];
            data = new Vector();
            headers[0] = "Attribute";
            headers[1] = "Return Att";
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
            AttPair value = (AttPair)data.get(x);
  
            if (y == 0)
            { 
                return (Object)value.getAttMnem();
            } 
            else
            {
                return (Object)value.getRetAttMnem();
            }
        }
        
        public void setValueAt(Object value, int row, int col) 
        {
            if (col == 0)
            {
                ((AttPair)data.elementAt(row)).setAttMnem((String)value);
                fireTableCellUpdated(row, col);
            }
            else
            {
                ((AttPair)data.elementAt(row)).setRetAttMnem((String)value);
                fireTableCellUpdated(row, col);
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
    
    private class AttPair
    {
        private int Att_id;
        private int ret_Att_id;
        private String Att_mnem;
        private String ret_Att_mnem;
        
        
        public AttPair(int _Att_id, int _ret_Att_id, String _Att_mnem, String _ret_Att_mnem)
        {
            Att_id = _Att_id;
            ret_Att_id = _ret_Att_id;
            Att_mnem = _Att_mnem;
            ret_Att_mnem = _ret_Att_mnem;
        }
        
        public void setAttId(int value)
        {
            Att_id = value;
        }
        
        public int getAttId()
        {
            return Att_id;
        }
        
        public void setRetAttId(int value)
        {
            ret_Att_id = value;
        }
        
        public int getRetAttId()
        {
            return ret_Att_id;
        }
        
        public void setAttMnem(String value)
        {
            Att_mnem = value;
        }
        
        public String getAttMnem()
        {
            return Att_mnem;
        }
        
        public void setRetAttMnem(String value)
        {
            ret_Att_mnem = value;
        }
        
        public String getRetAttMnem()
        {
            return ret_Att_mnem;
        }
    }
}