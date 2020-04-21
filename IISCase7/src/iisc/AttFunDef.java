package iisc;

import iisc.callgraph.PassedValuePanel;

import iisc.lang.DomainDesc;
import iisc.lang.JSourceCodeEditor;

import iisc.lang.SemAnalyzer;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.awt.*;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;

import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ItemListener;

import java.awt.event.KeyListener;

import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;


public class AttFunDef extends JPanel implements ListSelectionListener
{
    private int width;
    private int height;
    public int Att_id;
    public int Tob_id;
    public int PR_id;
    public int Tf_id;
    public int root_tob_id;
    private Connection conn;
    private IISFrameMain parent;
    
    public JComboBox funCombo;
    private JButton searchBtn;
    private JLabel lb;
    private JLabel tableLabel = new JLabel("Arguments");
    private FuncAttTableModel datm;
    private JList list;
    JPanel listPanel = new JPanel();
    private JTable table;
    JScrollPane listScroll;
    private AttType attTypeFrm;
    private Vector appSys = new Vector();
    private Hashtable domains = new Hashtable();
    private Hashtable userDefDomains = new Hashtable();
    private Vector param_data = new Vector();
    private RadioPanel rPanel = null;
    private String callType = "0";
    public String eventName = "";
    public String eventId = "";
    public String old_eventId = "";
    public int level_id = 0;
    public int old_level_id = 0;
    public int button_id = 0;
    public int b_x =0;
    public int b_y = 0;
    public int b_h = 0;
    public int b_w = 0;
    public String b_label = "";
    public String b_ig_id = "";
    
    public AddEventDefParams attEventDefParams;
    public AddButtonDef attButtonDef;
    private ImageIcon paramImage = new ImageIcon(IISFrameMain.class.getResource("icons/param.gif"));
    
    public AttFunDef(int _width, int _height, int _Att_id, int _Tob_id, int _Tf_id, int _PR_id, Connection _Conn, IISFrameMain _Parent, AttType _attTypeFrm, String callType, String eventName, String eventId, String old_eventId, AddEventDefParams _attEventDefParams, int old_level_id, int level_id)
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
        this.callType = callType;
        this.eventId = eventId; 
        this.eventName = eventName; 
        this.old_eventId = old_eventId;
        this.level_id = level_id;
        this.old_level_id = old_level_id;
        
        if (callType.equals("4"))
        {
            //button 
            this.button_id = this.level_id;
        }
        else
        {
            if (callType.equals("3"))
            {
                this.GetRootCompType(this.Tf_id);
            }
        }
        attEventDefParams = _attEventDefParams;
        
        JSourceCodeEditor.InitDomains(this.conn, null, null, this.domains, this.userDefDomains, this.PR_id);
        
        try 
        {
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void repaintAll()
    {
        super.paintComponents(this.getGraphics());
    }
    
     public void Disable()
     {
        this.funCombo.setEnabled(false);
        this.searchBtn.setEnabled(false);
        this.funCombo.setSelectedItem("");
     }
     
    public void EnableCombo()
    {
       this.funCombo.setEnabled(true);
       this.searchBtn.setEnabled(true);
       this.funCombo.setSelectedItem("");
    }
    
     public DomainDesc GetBaseDomain(String domName)
     {
         
         DomainDesc current = null;
         
         if (this.domains.containsKey(domName.toLowerCase(Locale.US)))
         {
             current = (DomainDesc)this.domains.get(domName.toLowerCase(Locale.US));
         }
         
         if (this.userDefDomains.containsKey(domName.toLowerCase(Locale.US)))
         {
             current = (DomainDesc)this.userDefDomains.get(domName.toLowerCase(Locale.US));
         }
         
         if (current == null)
         {
             return current;
         }
         
         int i = 1;
         
         while(i < 100)
         {
             if (current.getType() == DomainDesc.PRIMITIVE)
             {
                 break;
             }
             else
             {
                 if (current.getType() == DomainDesc.INHERITED_PRIMITIVE)
                 {
                     if (this.domains.containsKey(current.getPrimitiveTypeName().toLowerCase(Locale.US).toString()))
                     {
                         current = (DomainDesc)this.domains.get(current.getPrimitiveTypeName().toLowerCase(Locale.US).toString());  
                     }
                 }
                 else
                 {
                     if (current.getType() == DomainDesc.INHERITED_USER_DEF)
                     {
                         if (this.userDefDomains.containsKey(current.getParentName().toLowerCase(Locale.US)))
                         {
                             current = (DomainDesc)this.userDefDomains.get(current.getParentName().toLowerCase(Locale.US));  
                         }
                     }
                     else
                     {
                         break;
                     }
                 }
             }
             
             i = i + 1;
         }
         
         return current;
     }
         
     public boolean CheckDomainsComp(String domName1, String domName2)
     {
         try 
         {
             DomainDesc domDesc1 = GetBaseDomain(domName1);
             DomainDesc domDesc2 = GetBaseDomain(domName2);
                 
             if (domDesc1 == null || domDesc2 == null)
             {
                 return false;
             }
             
             boolean comp = false;
             
             if (domDesc1.getType() == DomainDesc.SET)
             {
                 if (domDesc2.getType() != DomainDesc.SET)
                 {
                     return false;
                 }
                 else
                 {
                     return CheckDomainsComp(domDesc1.getParentName(), domDesc2.getParentName());
                 }
             }
             else
             {
                 if (domDesc1.getType() == DomainDesc.PRIMITIVE)
                 {
                     if (domDesc2.getType() == DomainDesc.PRIMITIVE)
                     {
                         if (SemAnalyzer.assignMatr[domDesc1.getPrimitiveDomainType()-1][domDesc2.getPrimitiveDomainType() -1] != 0)
                         {
                             comp = true;
                         }
                     }
                 }
                 else
                 {
                     if (domDesc2.getType() != DomainDesc.PRIMITIVE)
                     {
                         if (domDesc1.getId() == domDesc2.getId())
                         {
                             comp = true;
                         }
                     }
                 }
             }
             
             return comp;
         }
         catch(Exception e)
         {
             return false;
         }
     }
         
    public DomainDesc GetDomDescById(String id)
    {
        Iterator iter = this.userDefDomains.values().iterator();
        
        while (iter.hasNext())
        {
            DomainDesc desc = (DomainDesc)iter.next();
            
            if (desc.getId().equals(id))
            {
                return desc;
            }
        }
        
        return null;
    }
    
    private void jbInit() throws Exception
    {
        setLayout(null);
        //setBounds(10,10,width, height -40);
        //this.setBorder(new LineBorder(Color.black, 1));
        
        lb = new JLabel("Function:");
        lb.setSize(100, 20);
        lb.setBounds(10,10,70, 20);
        lb.setFont(new Font("SansSerif", 0, 11));
        add(lb);
        
        tableLabel.setSize(100, 20);
        tableLabel.setBounds(10,60,70, 20);
        tableLabel.setFont(new Font("SansSerif", 0, 11));
        add(tableLabel);
        
        searchBtn = new JButton();
        searchBtn.setText("...");
        //searchBtn.setSize(15,50);
        searchBtn.setFont(new Font("SansSerif", 0, 11));
        searchBtn.setBounds(345,30,30, 20);
        add(searchBtn);
        searchBtn.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              fun_ActionPerformed(ae);
            }
          });
          
        funCombo = new JComboBox();
        funCombo.setBounds(10,30,335,20);
        funCombo.setEditable(false);
        add(funCombo);
        
        InitCombo();
        
        funCombo.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            tfCombo_actionPerformed(e);
          }
        });
        
        listPanel = new JPanel();
        listPanel.setLayout(null);
        listPanel.setBorder(new LineBorder(new Color(124, 124, 124), 1));
        listPanel.setBackground(SystemColor.textInactiveText);
        listPanel.setBounds(10, 80, 365, height - 90);
        add(listPanel);
        
        InitList();
        
        listScroll = new JScrollPane();
        listScroll.setBounds(0, 0, 161, height - 90);
        listScroll.getViewport().add(list);
        listScroll.setBorder(new LineBorder(new Color(124, 124, 124), 1));
        listPanel.add(listScroll);
        
        if(param_data.size() > 0)
        {
            //list.getSelectionModel().setSelectionInterval(0,1);
            list.setSelectedValue(((DefaultListModel)this.list.getModel()).get(0), false);            
            valueChanged(null);
        }
        
        if (this.level_id == 1 && !eventName.endsWith("Record"))
        {
            list.setEnabled(false);
            if (rPanel != null)
            {
                this.rPanel.defaultChb.setEnabled(false);
            }
        }
    }
    
    private String GetDomId(int att_id)
    {
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_ATTRIBUTE where Att_id="+att_id);
            
            if(rs.next())
            {
                String domID = rs.getString("Dom_id");
                return domID;
            }
            statement.close();
        }
        catch(SQLException e)
        {
            System.out.println("\n" + e.toString() + "\n");
        }   
        return "";
    }
    
    private void GetRootCompType(int tf_id)
    {
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tf_id="+tf_id + " and Tob_superord is null");
            
            if(rs.next())
            {
                root_tob_id = rs.getInt("Tob_id");
            }
            statement.close();
        }
        catch(SQLException e)
        {
            System.out.println("\n" + e.toString() + "\n");
        }  
    }
    
    public String GetFunId(String funName)
    {
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_FUNCTION where Fun_name='" + funName + "' and PR_id=" + this.PR_id);
            
            if (rs.next())
            {
                return rs.getString("Fun_id");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }
    
    private String GetFunName(String funId)
    {
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_FUNCTION where Fun_id=" + funId);
            
            if (rs.next())
            {
                return rs.getString("Fun_name");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }
    
    /* Inicijalizacija padajuce liste koji odgovarajuce funkcije */
    private void InitCombo()
    {
        try
        {
            String attDomId = this.GetDomId(this.Att_id);
            DomainDesc attDomDesc = this.GetDomDescById(attDomId);
            
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_FUNCTION where PR_id="+PR_id);
            
            funCombo.addItem("");
            
            if (this.callType.equals("0"))
            {
                while(rs.next())
                {
                    String domID = rs.getString("Dom_id");
                    String funName = rs.getString("Fun_name");
                    
                    DomainDesc funDomDesc = this.GetDomDescById(domID);
                    
                    if (attDomDesc != null && funDomDesc != null && this.CheckDomainsComp(attDomDesc.getName(), funDomDesc.getName()))
                    {
                        funCombo.addItem(funName);
                    }
                    //
                }
            }
            else
            {
                while(rs.next())
                {
                    String funName = rs.getString("Fun_name");
                    funCombo.addItem(funName);
                    //
                }
            }
            rs.close();
            statement.close();
            
            if (callType.equals("0"))
            {
                statement = conn.createStatement();
                rs = statement.executeQuery("select * from IISC_ATT_TOB where PR_id="+PR_id + " and Att_id=" + this.Att_id + " and Tf_id=" + this.Tf_id + " and Tob_id=" + this.Tob_id);
            }
            else
            {
                if (callType.equals("1"))
                {
                    statement = conn.createStatement();
                    rs = statement.executeQuery("select * from IISC_ATT_TOB_EVENT where PR_id="+PR_id + " and Att_id=" + this.Att_id + " and Tf_id=" + this.Tf_id + " and Tob_id=" + this.Tob_id + " and Event_id=" + this.eventId + " and Event_type=" + this.level_id);
                }
                else
                {
                    if (callType.equals("2"))
                    {
                        statement = conn.createStatement();
                        rs = statement.executeQuery("select * from IISC_ATT_TOB_EVENT where PR_id="+PR_id + " and Tf_id=" + this.Tf_id + " and Tob_id=" + this.Tob_id + " and Event_id=" + this.eventId + " and Event_type=" + this.level_id);
                    }
                    else
                    {
                        if (callType.equals("3"))
                        {
                            statement = conn.createStatement();
                            rs = statement.executeQuery("select * from IISC_ATT_TOB_EVENT where PR_id="+PR_id + " and Tf_id=" + this.Tf_id + " and Event_id=" + this.eventId + " and Event_type=" + this.level_id);
                        }
                        else
                        {
                            if (callType.equals("4"))
                            {
                                statement = conn.createStatement();
                                rs = statement.executeQuery("select * from IISC_TOB_BUTTON where Btn_id=" + this.button_id);
                            }
                        }
                    }
                }
            }
            if (rs.next())
            {
                String selFunId = rs.getString("Fun_id");
                
                if (selFunId != null && !selFunId.equals("") && !selFunId.equals("0"))
                {
                    String selFunName = this.GetFunName(selFunId);
                    
                    this.funCombo.setSelectedItem(selFunName);
                }
            }
            rs.close();
            statement.close();        
        }
        catch(SQLException e)
        {
            System.out.println("\n" + e.toString() + "\n");
        }
        
        repaint();
    }
  
    private void fun_ActionPerformed(ActionEvent e)
    { 
        SearchTable ptype=new SearchTable(parent,"Select Function",true,conn);
        Settings.Center(ptype);
        ptype.type="Function";
        ptype.item=funCombo;
        //ptype.owner=this;
        ptype.setVisible(true);
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
            temp.addItem(funCombo.getItemAt(i+1).toString().trim() + " (" + appSys.get(i)+")");
        }
        stb.item = temp;
        Settings.Center(stb);
        stb.setVisible(true);
        //System.out.println(temp.getSelectedItem().toString());
        if (temp.getSelectedIndex() > 0)
        {
            if (funCombo.getSelectedIndex() != temp.getSelectedIndex())
            {
                funCombo.setSelectedIndex(temp.getSelectedIndex());
            }
        }
        
    }
    
    private void EnableButtons()
    {
        if (callType.equals("0"))
        {
            attTypeFrm.btnApply.setEnabled(true);
            attTypeFrm.btnSave.setEnabled(true);
        }
        else
        {
            if (callType.equals("1") || callType.equals("2") || callType.equals("3"))
            {
                attEventDefParams.btnApply.setEnabled(true);
                attEventDefParams.btnSave.setEnabled(true);
            }
            else
            {
                if (callType.equals("4"))
                {
                    attButtonDef.btnApply.setEnabled(true);
                    attButtonDef.btnSave.setEnabled(true);
                }
            }
        }
    }
    
    private void tfCombo_actionPerformed(ItemEvent e)
    {
        int i = funCombo.getSelectedIndex();
        EnableButtons();
        PopulateListData();
        
        if (this.rPanel != null)
        {
            this.listPanel.remove(this.rPanel);
            this.listPanel.repaint();
        }
        
        if(this.list.getModel().getSize() > 0)
        {
            list.setSelectedIndex(0);
        }
    }
    
    public void DisableAll()
    {        
        table.setEnabled(false);
        datm.data.clear();
        datm.fireTableDataChanged();
    }
    
    public void EnableAll()
    {
        table.setEnabled(true);
    }
  
    /************************************************************************/
    /****                    Inicijalizacija tabele                      ****/
    /************************************************************************/
    private void InitList()
    {
        DefaultListModel listModel = new DefaultListModel();        
        list = new JList(listModel);        
        list.setCellRenderer(new CustomCellRenderer());
        
        this.PopulateListData();
        
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBackground(Color.white);
        list.setAutoscrolls(true);
        list.getSelectionModel().addListSelectionListener(this);
    }  
    
     private String GetAttName(String att_id)
     {
         try 
         {
             Statement statement = conn.createStatement();                
             ResultSet rs = statement.executeQuery("select * from IISC_ATTRIBUTE where Att_id=" + att_id);
             
             if(rs.next())
             {
                 return rs.getString("Att_mnem");
             }
             rs.close();
         }
         catch(Exception e)
         {
         }
         return "";
     }
         
    private void InitParamValue(String funId, FunParamDesc value)
    {
        try 
        {
            Statement statement = conn.createStatement();                
            ResultSet rs = null;
            
            if (callType.equals("0"))
            {
                rs = statement.executeQuery("select * from IISC_FUN_PARAM_VALUE where Call_type=" + callType + " and Fun_id=" + funId + " and Param_id=" + value.paramId + " and Att_id=" + this.Att_id + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id);
            }
            else
            {
                if (callType.equals("1"))
                {
                    rs = statement.executeQuery("select * from IISC_FUN_PARAM_VALUE where Call_type=" + callType + " and Fun_id=" + funId + " and Param_id=" + value.paramId + " and Att_id=" + this.Att_id + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id + " and Event_id=" + this.eventId+ " and Event_type=" + this.level_id);
                }
                else
                {
                    if (callType.equals("2"))
                    {
                        rs = statement.executeQuery("select * from IISC_FUN_PARAM_VALUE where Call_type=" + callType + " and Fun_id=" + funId + " and Param_id=" + value.paramId + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id + " and Event_id=" + this.eventId+ " and Event_type=" + this.level_id);
                    }
                    else
                    {
                        if (callType.equals("3"))
                        {
                            rs = statement.executeQuery("select * from IISC_FUN_PARAM_VALUE where Call_type=" + callType + " and Fun_id=" + funId + " and Param_id=" + value.paramId + " and Tf_id=" + this.Tf_id + " and Event_id=" + this.eventId+ " and Event_type=" + this.level_id);
                        }
                        else
                        {
                            if (callType.equals("4"))
                            {
                                rs = statement.executeQuery("select * from IISC_FUN_PARAM_VALUE where Call_type=" + callType + " and Fun_id=" + funId + " and Param_id=" + value.paramId + " and Button_id=" + this.button_id);
                            }
                        }
                    }
                }
            }          
            
            
            if(rs.next())
            {
                //value = new FunParamDesc(rs.getString("Param_id"), rs.getString("Param_name"), funId, rs.getInt("Param_seq"), rs.getString("Dom_id"));
                //data.add(value);
                value.value_Att_id = rs.getString("Value_Att_id");;
                value.value_Tob_id = rs.getString("Value_Tob_id");
                value.value_Tf_id = rs.getString("Value_Tf_id");
                if (value.value_Att_id != null && !value.value_Att_id.equals(""))
                {
                    value.value_Att_name = this.GetAttName(value.value_Att_id);
                }
                value.value_type = rs.getInt("Value_type");
                value.is_subord_att = rs.getInt("Is_subord_att");
                value.value = rs.getString("Value_const");
                value.value_Event_param_name = rs.getString("Value_Event_arg_name");
            }
            rs.close();
        }
        catch(Exception e)
        {
        }
    }
    
    private void PopulateListData()
    {
        DefaultListModel list_model = new DefaultListModel();
        this.list.setModel(list_model);
        param_data = new Vector();
        
        String funName = this.funCombo.getSelectedItem().toString();
        String funId = "";
        FunParamDesc value;
        
        if (funName != null && !funName.equals(""))
        {
            funId = this.GetFunId(funName);
        }
        
        if (!funId.equals(""))
        {      
            try
            {
                Statement statement = conn.createStatement();                
                ResultSet rs = statement.executeQuery("select * from IISC_FUN_PARAM where Fun_id=" + funId);
                
                while(rs.next())
                {
                    value = new FunParamDesc(rs.getString("Param_id"), rs.getString("Param_name"), funId, rs.getInt("Param_seq"), rs.getString("Dom_id"));
                    this.InitParamValue(funId, value);
                    param_data.add(value);
                    list_model.add(list_model.size(), value.paramName + " (" + value.domName + ")");
                }
                rs.close();
                
            }
            catch(SQLException sqle)
            {
                System.out.println("Sql exception :" + sqle);
            }
        }  
        
        
    }
    /*************************************************************************/
    /*********              Unos podataka u bazu                      ********/
    /*************************************************************************/
    public boolean Check()
    {
        if (this.funCombo.getSelectedItem() == null)
        {
            return true;
        }
        
        String value = (String)this.funCombo.getSelectedItem();
        
        if (value.equals(""))
        {
            return true;
        }
        
        for(int i = 0; i  < this.param_data.size(); i++)
        {
            FunParamDesc desc = (FunParamDesc)this.param_data.get(i);
            
            if (desc == null || desc.value_type == 0)
            {
                continue;
            }
            
            if (desc.value_type == 1)
            {
                if (desc.value == null || desc.value.equals(""))
                {
                    return false;
                }
            }
            else
            {
                if (desc.value_type == 2)
                {
                    if (desc.value_Att_id == null || desc.value_Att_id.equals(""))
                    {
                        return false;
                    }
                    
                    if (desc.value_Tob_id == null || desc.value_Tob_id.equals(""))
                    {
                        return false;
                    }
                    
                    if (desc.value_Tf_id == null || desc.value_Tf_id.equals(""))
                    {
                        return false;
                    }
                }
                else
                {
                    if (desc.value_Event_param_name == null || desc.value_Event_param_name.equals(""))
                    {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    public void Update()
    {
        try
        {
            boolean isFuncSelected = false;
            String value = null;
            
            if (this.funCombo.getSelectedItem() != null)
            {
                value = (String)this.funCombo.getSelectedItem();
                value = this.GetFunId(value);
            }
            
            if (callType.equals("0"))
            {
                Statement stmt = conn.createStatement();
                
                if (this.Att_id != 0)
                {
                    stmt.execute("delete from IISC_FUN_PARAM_VALUE where Call_type=" + callType + " and Att_id=" + this.Att_id + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id);
                }
                
                if (value == null || value.equals(""))
                {
                    stmt.execute("update IISC_ATT_TOB set Fun_id=null where Att_id=" + this.Att_id + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id);
                }
                else
                {
                    stmt.execute("update IISC_ATT_TOB set Fun_id=" + value + " where Att_id=" + this.Att_id + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id);
                }
            }
            else
            {
                if (callType.equals("1"))
                {
                    Statement stmt = conn.createStatement();
                    stmt.execute("delete from IISC_FUN_PARAM_VALUE where Call_type=" + callType + " and Att_id=" + this.Att_id + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id + " and Event_id=" + this.old_eventId + " and Event_type=" + this.old_level_id);
                    stmt.execute("delete from IISC_ATT_TOB_EVENT where Event_level=" + callType + " and Att_id=" + this.Att_id + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id + " and Event_id=" + this.old_eventId + " and Event_type=" + this.old_level_id);
                    PreparedStatement ps1 = conn.prepareStatement("insert into IISC_ATT_TOB_EVENT(Event_level,Att_id,Tf_id,Tob_id,PR_id,Event_id,Fun_id,Event_type) values(?,?,?,?,?,?,?,?)");
                    ps1.setString(1, callType);
                    ps1.setInt(2, Att_id);
                    ps1.setInt(3, Tf_id);
                    ps1.setInt(4, Tob_id);
                    ps1.setInt(5, PR_id);
                    ps1.setString(6, eventId);
                    ps1.setString(7, value);
                    ps1.setInt(8, level_id);
                    ps1.execute();
                }
                else
                {
                    if (callType.equals("2"))
                    {
                        Statement stmt = conn.createStatement();
                        stmt.execute("delete from IISC_FUN_PARAM_VALUE where Call_type=" + callType + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id + " and Event_id=" + this.old_eventId + " and Event_type=" + this.old_level_id);
                        stmt.execute("delete from IISC_ATT_TOB_EVENT where Event_level=" + callType + " and Tob_id=" + this.Tob_id + " and Tf_id=" + this.Tf_id + " and Event_id=" + this.old_eventId + " and Event_type=" + this.old_level_id);
                        PreparedStatement ps1 = conn.prepareStatement("insert into IISC_ATT_TOB_EVENT(Event_level,Att_id,Tf_id,Tob_id,PR_id,Event_id,Fun_id,Event_type) values(?,?,?,?,?,?,?,?)");
                        ps1.setString(1, callType);
                        ps1.setInt(2, Att_id);
                        ps1.setInt(3, Tf_id);
                        ps1.setInt(4, Tob_id);
                        ps1.setInt(5, PR_id);
                        ps1.setString(6, eventId);
                        ps1.setString(7, value);
                        ps1.setInt(8, level_id);
                        ps1.execute();
                    }
                    else
                    {
                        if (callType.equals("3"))
                        {
                            Statement stmt = conn.createStatement();
                            stmt.execute("delete from IISC_FUN_PARAM_VALUE where Call_type=" + callType + " and Tf_id=" + this.Tf_id + " and Event_id=" + this.old_eventId + " and Event_type=" + this.old_level_id);
                            stmt.execute("delete from IISC_ATT_TOB_EVENT where Event_level=" + callType + " and Tf_id=" + this.Tf_id + " and Event_id=" + this.old_eventId + " and Event_type=" + this.old_level_id);
                            PreparedStatement ps1 = conn.prepareStatement("insert into IISC_ATT_TOB_EVENT(Event_level,Att_id,Tf_id,Tob_id,PR_id,Event_id,Fun_id, Event_type) values(?,?,?,?,?,?,?,?)");
                            ps1.setString(1, callType);
                            ps1.setInt(2, Att_id);
                            ps1.setInt(3, Tf_id);
                            ps1.setInt(4, Tob_id);
                            ps1.setInt(5, PR_id);
                            ps1.setString(6, eventId);
                            ps1.setString(7, value);
                            ps1.setInt(8, level_id);
                            ps1.execute();
                        }
                        else
                        {
                            if (callType.equals("4"))
                            {
                                Statement stmt = conn.createStatement();
                                stmt.execute("delete from IISC_FUN_PARAM_VALUE where Call_type=" + callType + " and Button_id=" + this.button_id);
                                
                                if (this.button_id == -1)
                                {
                                    ResultSet rs = stmt.executeQuery("select max(Btn_id) from IISC_TOB_BUTTON");
                                    
                                    if (rs.next())
                                    {
                                        this.button_id = rs.getInt(1) + 1;
                                    }
                                    
                                    PreparedStatement ps1 = conn.prepareStatement("insert into IISC_TOB_BUTTON(Btn_id,Tob_id,Tf_id,PR_id,Fun_id,XPos,YPos,Width,Height,Label,IG_id) values(?,?,?,?,?,?,?,?,?,?,?)");
                                    ps1.setInt(1, this.button_id);
                                    ps1.setInt(2, Tob_id);
                                    ps1.setInt(3, Tf_id);                                    
                                    ps1.setInt(4, PR_id);
                                    ps1.setString(5, value);                                                                 
                                    ps1.setInt(6, this.b_x);
                                    ps1.setInt(7, this.b_y);
                                    ps1.setInt(8, this.b_w);
                                    ps1.setInt(9, this.b_h);
                                    ps1.setString(10, this.b_label);
                                    ps1.setString(11, this.b_ig_id);
                                    ps1.execute();
                                }
                                else
                                {
                                    PreparedStatement ps1 = conn.prepareStatement("update IISC_TOB_BUTTON set Fun_id=?,XPos=?,YPos=?,Width=?,Height=?,Label=?,IG_id=? where Btn_id=?");
                                    ps1.setString(1, value);                                                                 
                                    ps1.setInt(2, this.b_x);
                                    ps1.setInt(3, this.b_y);
                                    ps1.setInt(4, this.b_w);
                                    ps1.setInt(5, this.b_h);
                                    ps1.setString(6, this.b_label);
                                    ps1.setString(7, this.b_ig_id);
                                    ps1.setInt(8, this.button_id);
                                    
                                    ps1.execute();
                                }
                            }
                        }
                    }
                }
            }
            this.old_eventId = this.eventId;
            this.old_level_id = this.level_id;
            PreparedStatement ps = conn.prepareStatement("insert into IISC_FUN_PARAM_VALUE(Fun_id,Param_id,Att_id,Tf_id,Tob_id,PR_id,Value_type,Value_const,Value_Att_id,Value_Tob_id,Value_Tf_id,Is_subord_att,Call_type,Event_id,Event_type,Button_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
             
            for(int i = 0; i < this.param_data.size(); i++)
            {
                FunParamDesc desc = (FunParamDesc)this.param_data.get(i);
                
                if (desc != null)
                {
                    ps.setString(1, value);
                    ps.setString(2, desc.paramId);
                    ps.setInt(3, Att_id);
                    ps.setInt(4, Tf_id);
                    ps.setInt(5, Tob_id);
                    ps.setInt(6, PR_id);
                    ps.setInt(7, desc.value_type);
                    ps.setString(8, desc.value);
                    ps.setString(9, desc.value_Att_id);
                    ps.setString(10, desc.value_Tob_id);
                    ps.setString(11, desc.value_Tf_id);
                    ps.setString(11, desc.value_Tf_id);
                    ps.setInt(12, desc.is_subord_att);
                    ps.setInt(13, Integer.parseInt(callType));
                    
                    if (this.eventId == null || this.eventId.equals(""))
                    {
                        ps.setInt(14, 0);
                    }
                    else
                    {
                        ps.setInt(14, Integer.parseInt(this.eventId));
                    }
                    
                    ps.setInt(15, level_id);
                    ps.setInt(16, this.button_id);
                    ps.execute();
                    
                }
            }
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
        }
    }

    public void valueChanged(ListSelectionEvent e) 
    {
        int row = this.list.getSelectedIndex();
        
        if (row < 0 || row > this.list.getModel().getSize())
        {
            return;
        }
        
        if (rPanel != null)
        {
            this.listPanel.remove(rPanel);
        }
        
        FunParamDesc desc = (FunParamDesc)this.param_data.get(row);
        rPanel = new RadioPanel(183, 150, desc);
        
        /*if (this.eventName.startsWith("Mouse") || this.eventName.startsWith("Key"))
        {
            rPanel.setBounds(160, 0, 205, 200);
        }
        else*/
        {
            rPanel.setBounds(160, 0, 205, 150);
        }
        
        listPanel.add(rPanel);        
        rPanel.paintComponents(rPanel.getGraphics());        
    }

    /*************************************************************************/
    /*********                Klasa za table model                    ********/
    /*************************************************************************/
    private class FuncAttTableModel extends AbstractTableModel
    {
        private String[] headers;
        private Vector data;
        
        public FuncAttTableModel()
        {
            headers = new String[3];
            data = new Vector();
            headers[0] = "Name";
            headers[1] = "Domain";
            headers[2] = "Value";
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
            FunParamDesc value = (FunParamDesc)data.get(x);
  
            if (y == 0)
            { 
                return (Object)value.paramName;
            } 
            else
            {
                if ( y == 1 )
                {
                    return (Object)value.domName;
                }
                else
                {
                    return (Object)value.getValue();
                }
            }
        }
        
        public void setValueAt(Object value, int row, int col) 
        {
            /*if (col == 0)
            {
                ((FunParamDesc)data.elementAt(row)).paramName = ((String)value);
                fireTableCellUpdated(row, col);
            }
            else
            {
                if (col == 0)
                {
                    ((FunParamDesc)data.elementAt(row)).domId = ((String)value);
                    fireTableCellUpdated(row, col);
                }
            }*/
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
    
    private class FunParamDesc
    {
        public String paramId;
        public String funId;
        public int paramSeq;
        public String value;
        public String paramName;
        public String domId;
        public String domName;
        public String value_Att_id;
        public String value_Tob_id;
        public String value_Tf_id;
        public String value_Att_name;
        public String value_Event_param_name;
        
        public int value_type; // 0 - defaul, 1 - const, 2 - Attr.
        public int is_subord_att;
        public FunParamDesc(String paramId, String paramName, String funId, int paramSeq, String domId)
        {
            this.paramId = paramId;
            this.paramName = paramName;
            this.funId = funId;
            this.paramSeq = paramSeq;
            this.domId = domId;
            DomainDesc temp = GetDomDescById(domId);
            this.domName = temp.getName();
            this.is_subord_att = 0;
        }       
        
        public String getValue()
        {
            if( value_Att_name != null && !value_Att_name.equals(""))
            {
                return value_Att_name;
            }
            return value;
        }
    }

    private class RadioPanel extends JPanel
    {
        private int width;
        private int height;
        private JRadioButton constRBtn;
        private JRadioButton attRBtn;
        private JRadioButton eventArgButton;
        private JLabel constLbl;
        private JLabel attLbl;
        private JLabel eventArgLabel;
        private JComboBox eventArgCombo;
        private JComboBox attCombo;
        private JTextField constTxt;
        private FunParamDesc value;
        private JLabel defaultLbl;
        private JCheckBox defaultChb;
        private ArrayList attributes = new ArrayList();
        
        public RadioPanel(int _width, int _height, FunParamDesc value)
        {
            super();   
            setLayout(null);
            height = _height;
            width = _width;
            this.value = value;
            
            setBorder(new LineBorder(new Color(124, 124, 124), 1));
            
            defaultLbl = new JLabel();
            defaultLbl.setText("Use default value");
            defaultLbl.setBounds(50, 12, 130, 20);
            add(defaultLbl);
            
            defaultChb = new JCheckBox();
            defaultChb.setBounds(24, 12, 20, 20);
            add(defaultChb);
            
            constLbl = new JLabel();
            constLbl.setText("Value");
            constLbl.setBounds(50, 42, 130, 10);
            add(constLbl);
            
            constRBtn = new JRadioButton();
            constRBtn.setBounds(24, 57, 20, 20);
            add(constRBtn);
            
            constTxt = new JTextField("");
            constTxt.setBounds(50, 57, 130, 20);
            add(constTxt);
            
            if (value.value != null)
            {
                constTxt.setText(value.value);
            }
            
            attLbl = new JLabel();
            attLbl.setText("Attribute");
            attLbl.setBounds(50, 94, 130, 10);
            add(attLbl);
            
            attRBtn = new JRadioButton();
            attRBtn.setBounds(24, 109, 20, 20);
            add(attRBtn);
            
            attCombo = new JComboBox();
            attCombo.setBounds(50, 109, 130, 20);
            add(attCombo);
            
            eventArgLabel = new JLabel();
            eventArgLabel.setText("Event argument");
            eventArgLabel.setBounds(50, 143, 130, 20);            
            
            eventArgButton = new JRadioButton();
            eventArgButton.setBounds(24, 161, 20, 20);
            
            eventArgCombo = new JComboBox();
            eventArgCombo.setBounds(50, 161, 130, 20);
            
            /*if (eventName != null && !eventName.equals("") && (eventName.startsWith("Mouse") || eventName.startsWith("Key")))
            {
                add(eventArgLabel);
                add(eventArgButton);
                add(eventArgCombo);
            }
            
            if (eventName.startsWith("Mouse"))
            {
                eventArgCombo.addItem("X");
                eventArgCombo.addItem("Y");
            }
            else
            {
                eventArgCombo.addItem("KeyCode");
            }
            if (this.value.value_Event_param_name != null && !this.value.value_Event_param_name.equals(""))
            {
                eventArgCombo.setSelectedItem(this.value.value_Event_param_name);
            }
            else
            {
                eventArgCombo.setSelectedIndex(0);
            }*/
            
            DomainDesc domDesc = GetDomDescById(value.domId);
            
            if (domDesc != null)
            {
                domDesc = GetBaseDomain(domDesc.getName());
            }
            
            if (domDesc != null)
            {
                if (level_id == 1)
                {
                    this.InitAttributes(this.attributes, Tob_id, domDesc, true, -1);
                }
                else
                {
                    if (domDesc.getType() == DomainDesc.SET)
                    {
                        //set domain is in question, so attributes from subordinate component types should be loaded
                        if (userDefDomains.containsKey(domDesc.getParentName().toLowerCase(Locale.US)))
                        {
                            domDesc = (DomainDesc)userDefDomains.get(domDesc.getParentName().toLowerCase(Locale.US));
                            
                            if (domDesc != null)
                            {
                                if (callType.equals("3"))
                                {
                                    this.InitAttributes(this.attributes, root_tob_id, domDesc, false, 1);
                                }
                                else
                                {
                                    this.InitAttributes(this.attributes, Tob_id, domDesc, false, 1);
                                }
                            }
                        }
                    }
                    else
                    {
                        if (callType.equals("3"))
                        {
                            this.InitAttributes(this.attributes, root_tob_id, domDesc, true, 0);
                        }
                        else
                        {
                            this.InitAttributes(this.attributes, Tob_id, domDesc, true, 0);
                        }
                    }
                }
            }
            
            this.PopulateCombo();
            
            if (value.value_type == 0)
            {
                defaultChb.setSelected(true);
                
                constTxt.setEnabled(false);
                attCombo.setEnabled(false);
                eventArgCombo.setEnabled(false);
                constRBtn.setEnabled(false);
                attRBtn.setEnabled(false);
                eventArgButton.setEnabled(false);
            }
            else
            {
                defaultChb.setSelected(false);
                
                constRBtn.setEnabled(true);
                attRBtn.setEnabled(true);
                eventArgButton.setEnabled(true);
                
                if (value.value_type == 1)
                {
                    constTxt.setEnabled(true);
                    attCombo.setEnabled(false);
                    eventArgCombo.setEnabled(false);
                    constRBtn.setSelected(true);
                    attRBtn.setSelected(false);
                    eventArgButton.setSelected(false);
                }
                else
                {
                    if (value.value_type == 2)
                    {
                        constTxt.setEnabled(false);
                        attCombo.setEnabled(true); 
                        eventArgCombo.setEnabled(false);
                        constRBtn.setSelected(false);
                        attRBtn.setSelected(true);
                        eventArgButton.setSelected(false);
                    }
                    else
                    {
                        constTxt.setEnabled(false);
                        attCombo.setEnabled(false); 
                        eventArgCombo.setEnabled(true);
                        constRBtn.setSelected(false);
                        attRBtn.setSelected(false);
                        eventArgButton.setSelected(true);
                    }
                }
            }
            
            constRBtn.addItemListener(new ItemListener()
            {
                public void itemStateChanged(ItemEvent e) 
                {
                    constRBtn_actionPerformed(e);
                }
            });
            
            attRBtn.addItemListener(new ItemListener()
            {
                public void itemStateChanged(ItemEvent e) 
                {
                    attRBtn_actionPerformed(e);
                }
            });
            
            eventArgButton.addItemListener(new ItemListener()
            {
                public void itemStateChanged(ItemEvent e) 
                {
                    eventRBtn_actionPerformed(e);
                }
            });
            
            this.defaultChb.addItemListener(new ItemListener()
            {
                public void itemStateChanged(ItemEvent e) 
                {
                    defaultChb_actionPerformed(e);
                }
            });
            
            this.constTxt.addKeyListener(new KeyListener()
            {

                public void keyTyped(KeyEvent e) 
                {
                }

                public void keyPressed(KeyEvent e) 
                {
                }

                public void keyReleased(KeyEvent e) 
                {
                    const_keyTyped();
                }
            });
            
            this.attCombo.addItemListener(new ItemListener()
            {
                public void itemStateChanged(ItemEvent e) 
                {
                    attCmb_ItemStateChanged();
                }
            });
            
            this.eventArgCombo.addItemListener(new ItemListener()
            {
                public void itemStateChanged(ItemEvent e) 
                {
                    eventCombo_ItemStateChanged();
                }
            });
            
            if (level_id == 1 && !eventName.endsWith("Record"))
            {
                this.defaultChb.setEnabled(false);
            }
            
            repaint();            
        }
        
        private void PopulateCombo()
        {
            int index = -1;
            
            for(int i = 0; i < this.attributes.size(); i++)
            {
                Attribute att = (Attribute)this.attributes.get(i);
                this.attCombo.addItem(att.Att_mnem+"(" + att.Tob_mnem + ")");
                
                if (value.value_Att_id != null && value.value_Tob_id != null)
                {
                    if (value.value_Att_id.equals(Integer.toString(att.Att_id)) && value.value_Tob_id.equals(Integer.toString(att.Tob_id)))
                    {
                        index = i;
                    }
                }
            }
            
            if (index != -1)
            {
                this.attCombo.setSelectedIndex(index);
            }
        }
        
        public void attCmb_ItemStateChanged() 
        {
            int row = attCombo.getSelectedIndex(); 
            
            if (row  >= 0 && row < this.attributes.size())
            {
                Attribute att = (Attribute )this.attributes.get(row);
                value.value_Att_id = Integer.toString(att.Att_id);
                value.value_Att_name = att.Att_mnem;
                value.value_Tob_id = Integer.toString(att.Tob_id);
                value.value_Tf_id = Integer.toString(Tf_id);
                value.is_subord_att = att.issubord;
            }
            EnableButtons();
        }
        
        public void eventCombo_ItemStateChanged() 
        {
            value.value_Event_param_name = (String)eventArgCombo.getSelectedItem();
            EnableButtons();
        }
        
        public void const_keyTyped() 
        {
            this.value.value = constTxt.getText();
            EnableButtons();
        }
        
        private void defaultChb_actionPerformed(ItemEvent e)
        {
            if (defaultChb.isSelected())
            {
                constTxt.setEnabled(false);
                attCombo.setEnabled(false);
                constRBtn.setEnabled(false);
                attRBtn.setEnabled(false);
                eventArgButton.setEnabled(false);
                eventArgCombo.setEditable(false);
                value.value_type = 0;
            }
            else
            {
                attCmb_ItemStateChanged();
                const_keyTyped();
                eventCombo_ItemStateChanged();
                
                constRBtn.setEnabled(true);
                attRBtn.setEnabled(true);
                eventArgButton.setEnabled(true);
                
                if (eventArgButton.isSelected())
                {
                    value.value_type = 3;
                    constTxt.setEnabled(false);
                    attCombo.setEnabled(false);
                    eventArgCombo.setEnabled(true);
                }
                else
                {
                    if (attRBtn.isSelected())
                    {
                        value.value_type = 2;
                        constTxt.setEnabled(false);
                        attCombo.setEnabled(true);
                        eventArgCombo.setEnabled(false);
                    }
                    else
                    {
                        constRBtn.setSelected(true);
                        value.value_type = 1;
                        constTxt.setEnabled(true);
                        attCombo.setEnabled(false);
                        eventArgCombo.setEnabled(false);
                    }
                }
            }
            EnableButtons();
        }
        
        private void constRBtn_actionPerformed(ItemEvent e)
        {
            if (constRBtn.isSelected())
            {
                constTxt.setEnabled(true);
                attCombo.setEnabled(false);
                attRBtn.setSelected(false);
                eventArgButton.setSelected(false);
                eventArgCombo.setEnabled(false);
            }
            value.value_type = 1;
            EnableButtons();
        }
        
        private void attRBtn_actionPerformed(ItemEvent e)
        {
            if (attRBtn.isSelected())
            {
                constTxt.setEnabled(false);
                attCombo.setEnabled(true);  
                constRBtn.setSelected(false);
                eventArgButton.setSelected(false);
                eventArgCombo.setEnabled(false);
            }
            value.value_type = 2;
            EnableButtons();
        }
        
        private void eventRBtn_actionPerformed(ItemEvent e)
        {
            if (eventArgButton.isSelected())
            {
                constTxt.setEnabled(false);
                attCombo.setEnabled(false);  
                constRBtn.setSelected(false);
                attRBtn.setSelected(false);
                eventArgButton.setSelected(true);
                eventArgCombo.setEnabled(true);
            }
            value.value_type = 3;
            EnableButtons();
        }
        
        public void InitAttributes(ArrayList attributes, int curr_tob_id, DomainDesc domDesc, boolean loadCurrent, int direction /* 0 up,1 down*/)
        {
            try
            {
                Statement statement = null;
                ResultSet rs = null;
                
                if (loadCurrent)
                {
                    statement = conn.createStatement();
                    rs = statement.executeQuery("select IISC_ATTRIBUTE.Att_id,IISC_ATTRIBUTE.Dom_id,IISC_ATTRIBUTE.Att_mnem,IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id,IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_mnem  from IISC_ATT_TOB,IISC_ATTRIBUTE,IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_ATT_TOB.Tf_id=" + Tf_id + " and IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id and IISC_ATT_TOB.Tob_id=IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id and IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id=" + curr_tob_id);
                    
                    while(rs.next())
                    {   
                        int temp_att_id = rs.getInt("Att_id");
                        int temp_tob_id = rs.getInt("Tob_id");
                        
                        //if (!(Att_id == temp_att_id && temp_tob_id == Tob_id))
                        {
                            String temp_dom_id = rs.getString("Dom_id");
                            DomainDesc temp_Dom = GetDomDescById(temp_dom_id);
                            
                            if (temp_Dom != null)
                            {   
                                if (CheckDomainsComp(domDesc.getName(), temp_Dom.getName()))
                                {
                                    attributes.add(new Attribute(temp_att_id, rs.getString("Att_mnem"), temp_tob_id, rs.getString("Tob_mnem"), direction));
                                }
                            }
                        }
                    }
                    
                    rs.close();
                    statement.close();
                }
                if ( direction == 0 ) //up
                {
                    statement = conn.createStatement();
                    rs = statement.executeQuery("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id=" + curr_tob_id);
                    
                    if (rs.next())
                    {
                        String tob_super_ord = rs.getString("Tob_superord");
                        
                        if (tob_super_ord != null && !tob_super_ord.equals(""))
                        {
                            this.InitAttributes(attributes, Integer.parseInt(tob_super_ord), domDesc, true, 0);
                        }
                    }
                }
                else
                {
                    if ( direction == 1 ) //down
                    {
                        statement = conn.createStatement();
                        rs = statement.executeQuery("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_superord=" + curr_tob_id);
                        
                        while (rs.next())
                        {
                            String tob_super_ord = rs.getString("Tob_id");
                            
                            if (tob_super_ord != null && !tob_super_ord.equals(""))
                            {
                                this.InitAttributes(attributes, Integer.parseInt(tob_super_ord), domDesc, true, 1);
                            }
                        }
                    }
                }
            }
            catch(SQLException sqle)
            {
                System.out.println("Sql exception :" + sqle);
            }
            
        }
    }

     private class CustomCellRenderer extends  DefaultListCellRenderer  implements ListCellRenderer<Object> 
     {
         
         public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) 
         {
             JLabel label =(JLabel)super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
     
             String s = value.toString();
             label.setText(s);
             label.setIcon(paramImage);
             
             if(isSelected)
             {
                 label.setBackground(SystemColor.textHighlight);
                 label.setForeground(Color.white);
             }
             else
             {
                 label.setBackground(Color.white);
                 label.setForeground(Color.black);
             }
             
             label.revalidate();
             list.revalidate();
             return this;
         }  
     }
     
    private class Attribute
    {
        public int Att_id;
        public int Tob_id;
        public String Att_mnem;
        public String Tob_mnem;
        public int issubord = 1;
        
        private Attribute(int _Att_id, String _Att_mnem, int _Tob_id, String _Tob_mnem, int _issubord)
        {
            Att_id = _Att_id;
            Tob_id = _Tob_id;
            Tob_mnem = _Tob_mnem;
            Att_mnem = _Att_mnem;
            issubord = _issubord;
        }
    }
}