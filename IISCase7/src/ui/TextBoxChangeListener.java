package ui;

import com.harmonia.renderer.Renderer;

import iisc.JDBCQuery;

import iisc.lang.JSourceCodeEditor;
import iisc.lang.vmashine.IVmContextProvider;
import iisc.lang.vmashine.VirtualMashine;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.Statement;

import java.util.ArrayList;

import java.util.Enumeration;

import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ui.events.DetailDataChanged;
import ui.events.IDdActionListener;
import ui.events.ILoadData;
import ui.events.INextRecord;
import ui.events.IPreviousRecord;

public class TextBoxChangeListener implements FocusListener, ItemListener, ListSelectionListener,
                ActionListener, DetailDataChanged, MouseListener, KeyListener, INextRecord, IPreviousRecord, ILoadData,
                IDdActionListener, IVmContextProvider, InternalFrameListener 
{
    
    int att_id;
    int tob_id;
    int tf_id;
    int btn_id;    
    Application.FormType f;
    Connection connection;
    ArrayList clients = new ArrayList();
    ArrayList subTobs = new ArrayList();
    ArrayList superTobs = new ArrayList();
    ArrayList currTobs = new ArrayList();
    Renderer r;
    Document funcDoc;
    Element funcRoot;
    VirtualMashine vm;
    String eventName;
    boolean updateField;
    String callType = "";
    int event_type; 
    DBTable table = null; 
    DBPanel panel = null;
    boolean isDerAtt = false;
    boolean isCalcAtt = false;
    boolean isMouseClickedEvent = false;
    boolean isMousePressedEvent = false;
    boolean isMouseReleasedEvent = false;
    boolean isMouseEnteredEvent = false;
    boolean isMouseExitetedEvent = false;
    boolean isButtonEvent = false;
    boolean isFocusGainedEvent = false;
    boolean isFocusLostEvent = false;
    boolean isKeyTypedEvent = false;
    boolean isKeyPresssedEvent = false;
    boolean isKeyReleasedEvent = false;
    boolean isBeforeNextRecordEvent = false;
    boolean isAfterNextRecordEvent = false;
    boolean isBeforePreviousRecordEvent = false;
    boolean isAfterPreviousRecordEvent = false;
    boolean isBeforeLoadDataEvent = false;
    boolean isAfterLoadDataEvent = false;
    boolean isBeforeInsertEvent = false;
    boolean isAfterInsertEvent = false;
    boolean isBeforeUpdateEvent = false;
    boolean isAfterUpdateEvent = false;
    boolean isBeforeDeleteEvent = false;
    boolean isAfterDeleteEvent = false;
    boolean trigStatus = true;
    boolean isFunctionButtonListener = false;
    boolean isFrameOpenListener = false;
    boolean isFrameClosedListener = false;
    
    public TextBoxChangeListener(int att_id, int tob_id, int tf_id, Application.Item item, Application.FormType f, Application.CompType c, Connection connection, Renderer r, Document funcDoc, VirtualMashine vm, int fun_id, boolean onChange, String callType, int event_type) 
    {
        this.att_id = att_id;
        this.tob_id = tob_id;
        this.tf_id = tf_id;
        this.f = f;      
        this.connection = connection;
        this.r = r;
        this.funcDoc = funcDoc;
        this.vm = vm;
        this.event_type = event_type;
        
        if (funcDoc != null)
        {
            this.funcRoot = funcDoc.getDocumentElement();
        }
        
        boolean ok = true;
        
        if (callType.equals("0") || callType.equals("1"))
        {
            if (item == null)
            {
                ok = false;
            }
        }
        else
        {
            if (callType.equals("4"))
            {
                this.btn_id = event_type;
            }
        }
        try
        {    
            if (ok)
            {
                if (callType.equals("0"))
                {
                    this.isDerAtt = true;
                    
                    if(c.data_layout == 1)
                    {
                        table =(DBTable)r.getPartByName(""+c.id+"Table");                    
                    }
                    else
                    {
                        if(c.data_layout == 0)
                        {
                            panel =(DBPanel)r.getPartByName(""+c.id+"DataPanel");
                        }
                    }
                }
                
                ChangeListenerClient client = new ChangeListenerClient();
                
                client.fun_id = fun_id;
                client.a_id = att_id;
                client.t_id = tf_id;
                client.tb_id = tob_id;
                client.item = item;
                client.funcName = JSourceCodeEditor.LoadFuncNameFromRepository(0, Integer.toString(fun_id), this.connection);
                
                if (item != null)
                {
                    client.valueField = r.getPartByName(""+((Application.AttType)item.elem).att_id +tob_id+"InputField");
                    
                    if (this.callType.equals(""))
                    {
                        //Atribut se ne cuva u bazi pa ga je potreno racunati svaki put kada se
                        //ucitaju podaci za data block
                        if (((Application.AttType)item.elem).att.sbp == 0)
                        {
                            this.isDerAtt = false;
                            this.isCalcAtt = true;
                            
                            if (this.table != null)
                            {
                                this.table.AddDataChangeListener(this);
                            }
                            else
                            {
                                if(this.panel != null)
                                {
                                    this.panel.AddDataChangeListener(this);
                                }
                            }
                        }
                    }
                }
                
                String sql = "";
                
                if (callType.equals("4"))
                {
                    sql = "select * from IISC_FUN_PARAM_VALUE where Button_id=" + this.btn_id + " and Call_type=4";
                }
                else
                {
                    sql = "select * from IISC_FUN_PARAM_VALUE where Att_id=" + att_id + " and Tf_id=" + tf_id + " and Tob_id=" + tob_id + " and Call_type=" + callType;
                    
                    if (event_type > -1)
                    {
                        sql += "and Event_type=" + event_type;
                    }
                }
                
                Statement stmt1 = this.connection.createStatement();            
                ResultSet rs1 = stmt1.executeQuery(sql);
                
                boolean can = true;
                
                while(rs1.next())
                {
                    FuncParam param = new FuncParam();
                    
                    param.type = rs1.getInt("Value_type");
                    param.inout = this.GetInOut(connection, rs1.getInt("Param_id"));
                    
                    if (param.type != 0)
                    {
                        if (param.type == 1)
                        {
                            String value = rs1.getString("Value_const");
                            param.value = value;
                            client.params.add(param);
                        }
                        else
                        {
                            int temp_a_id = rs1.getInt("Value_Att_id");
                            int temp_t_id = rs1.getInt("Value_Tf_id");
                            int temp_tb_id = rs1.getInt("Value_Tob_id");
                            int temp_is_subord = rs1.getInt("Is_subord_att");
                            param.is_subord_att = temp_is_subord;
                            
                            this.getItem(temp_a_id, temp_t_id, temp_tb_id, temp_is_subord, param);
                            
                            if (param.item != null)
                            {
                                param.valueField = r.getPartByName(""+((Application.AttType)param.item.elem).att_id +temp_tb_id+"InputField");
                                param.is_subord_att = temp_is_subord;
                                client.params.add(param);
                                
                                String temp_tb_id_str = Integer.toString(temp_tb_id);
                                
                                if (this.isDerAtt)
                                {
                                    if (temp_tb_id == tob_id)
                                    {
                                        if (this.currTobs.size() == 0)
                                        {
                                            this.currTobs.add(temp_tb_id_str);
                                            
                                            //pripadaju istom tipu komponte
                                            if (this.table != null)
                                            {
                                                this.table.AddBeforeInsertListener(this);
                                                this.table.AddBeforeUpdateListener(this);
                                            }
                                            else
                                            {
                                                if (panel != null)
                                                {
                                                    this.panel.AddBeforeInsertListener(this);
                                                    this.panel.AddBeforeUpdateListener(this);
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (param.is_subord_att == 1)
                                        {
                                           //podredjeni tip komponente 
                                           if (!this.Contains(this.subTobs, temp_tb_id_str))
                                           {
                                               this.subTobs.add(temp_tb_id_str);
                                               
                                               if (param.table != null)
                                               {
                                                   param.table.AddAfterInsertListener(this);
                                                   param.table.AddAfterUpdateListener(this);
                                                   param.table.AddAfterDeleteListener(this);
                                                   param.table.setTobId(temp_tb_id_str);
                                               }
                                               else
                                               {
                                                    if (param.panel != null)
                                                    {
                                                        param.panel.AddAfterInsertListener(this);
                                                        param.panel.AddAfterUpdateListener(this);
                                                        param.panel.AddAfterDeleteListener(this);
                                                        param.panel.setTobId(temp_tb_id_str);
                                                    }
                                               }
                                           }
                                        }
                                        else
                                        {
                                            //nadredjeni tip komponente 
                                            if (!this.Contains(this.superTobs, temp_tb_id_str))
                                            {
                                                this.superTobs.add(temp_tb_id_str);
                                                
                                                if (param.table != null)
                                                {
                                                    param.table.AddAfterInsertListener(this);
                                                    param.table.AddAfterUpdateListener(this);
                                                    param.table.AddAfterDeleteListener(this);
                                                    param.table.setTobId(temp_tb_id_str);
                                                }
                                                else
                                                {
                                                     if (param.panel != null)
                                                     {
                                                         param.panel.AddAfterInsertListener(this);
                                                         param.panel.AddAfterUpdateListener(this);
                                                         param.panel.AddAfterDeleteListener(this);
                                                         param.panel.setTobId(temp_tb_id_str);
                                                     }
                                                }
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    if (this.isCalcAtt)
                                    {
                                        if (param.is_subord_att == 1)
                                        {
                                           //podredjeni tip komponente 
                                           if (!this.Contains(this.subTobs, temp_tb_id_str))
                                           {
                                               this.subTobs.add(temp_tb_id_str);
                                               
                                               if (param.table != null)
                                               {
                                                   param.table.AddDataChangeListener(this);
                                                   param.table.setTobId(temp_tb_id_str);
                                               }
                                               else
                                               {
                                                    if (param.panel != null)
                                                    {
                                                        param.panel.AddDataChangeListener(this);
                                                        param.panel.setTobId(temp_tb_id_str);
                                                    }
                                               }
                                           }
                                        }
                                        else
                                        {
                                            //nadredjeni tip komponente 
                                            if (!this.Contains(this.superTobs, temp_tb_id_str))
                                            {
                                                this.superTobs.add(temp_tb_id_str);
                                                
                                                if (param.table != null)
                                                {
                                                    param.table.AddDataChangeListener(this);
                                                    param.table.setTobId(temp_tb_id_str);
                                                }
                                                else
                                                {
                                                     if (param.panel != null)
                                                     {
                                                         param.panel.AddDataChangeListener(this);
                                                         param.panel.setTobId(temp_tb_id_str);
                                                     }
                                                }
                                            }
                                        }
                                        
                                        Object obj = param.valueField;
                                        
                                        if (obj instanceof JTextField)
                                        {
                                            JTextField field = (JTextField)obj;
                                            field.addFocusListener(this);
                                        }
                                        else
                                        {
                                            if (obj instanceof JComboBox)
                                            {
                                                JComboBox combo = (JComboBox)obj;
                                                combo.addItemListener(this);
                                            }
                                            else
                                            {
                                                if (obj instanceof JList)
                                                {
                                                    JList list = (JList)obj;
                                                    list.addListSelectionListener(this);
                                                }
                                                else
                                                {
                                                    if (obj instanceof ButtonGroup)
                                                    {
                                                        ButtonGroup bg = (ButtonGroup)obj;
                                                        bg.setSelected(new DefaultButtonModel(), true);
                                                        bg.getSelection().addItemListener(this);
                                                    }
                                                    else
                                                    {
                                                        if (obj instanceof JTextArea)
                                                        {
                                                            JTextArea field = (JTextArea)obj;
                                                            field.addFocusListener(this);
                                                        }    
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            else
                            {
                                can = false;
                            }
                        }
                    }
                }
                
                if (can )
                {
                    this.clients.add(client);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private int GetInOut(Connection con, int param_id)
    {
        try 
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from IISC_FUN_PARAM where Param_id="+param_id);
            
            if (rs.next())
            {
                return rs.getInt("inout");
            }
        }
        catch(Exception e)
        {
        }
        return 0;
    }
    
    public void focusGained(FocusEvent e)
    {
        if (!this.eventName.equals(""))
        {
            if (this.isFocusGainedEvent)
            {
                Recalc(this);
            }
        }
        else
        {
            
        }
    }

    public void focusLost(FocusEvent e) 
    {
        if (this.eventName.equals(""))
        {
            Recalc(this);
        }
        else
        {
            if (this.isFocusLostEvent)
            {
                Recalc(this);
            }
        }
    }
    
    public void Recalc(IVmContextProvider contextProvider)
    {   
        for(int i = 0; i < this.clients.size(); i++)
        {
            ChangeListenerClient client = (ChangeListenerClient)this.clients.get(i);
            
            ArrayList params = new ArrayList();
            
            for(int j = 0; j < client.params.size(); j++)
            {
                FuncParam param = (FuncParam)client.params.get(j);
                
                if (param.is_subord_att == 0)
                {
                    if (this.isBeforeDeleteEvent)
                    {
                        params.add(param.getValueDbData());
                    }
                    else
                    {
                        params.add(param.getValue());
                    }
                }
                else
                {
                    params.add(param.getSetValue());
                }
            }
            
            String val = vm.ExecuteFunc(client.funcName, params, contextProvider);
            
            for(int j = 0; j < client.params.size(); j++)
            {
                FuncParam param = (FuncParam)client.params.get(j);
                
                if (param.inout != 0 && param.valueField != null)
                {
                    if (param.is_subord_att == 0)
                    {
                        if (params.get(j) instanceof String)
                        {
                            this.UpdateField(param.valueField, (String)params.get(j));
                        }
                    }
                    else
                    {
                        params.add(param.getSetValue());
                    }
                }
            }
            
            if (!this.updateField)
            {
                return;
            }
            
            this.UpdateField(client.valueField, val);
        }
    }
    
    public String Recalc(String dbData[][], int row, IVmContextProvider contextProvider)
    {   
        ChangeListenerClient client = (ChangeListenerClient)this.clients.get(0);
            
        ArrayList params = new ArrayList();
        
        for(int j = 0; j < client.params.size(); j++)
        {
            FuncParam param = (FuncParam)client.params.get(j);
            
            if (param.item != null && ((Application.AttType)param.item.elem).tob_id == tob_id)
            {
                //sa istog tipa komponente 
                params.add(param.getValueDbData(dbData, row));
            }
            else
            {
                if (param.is_subord_att == 0)
                {
                    params.add(param.getValue());
                }
                else
                {
                    params.add(param.getSetValue());
                }
            }
        }
        
        String value = vm.ExecuteFunc(client.funcName, params, contextProvider);
        
        if (panel != null && this.updateField)
        {
            this.UpdateField(client.valueField, value);
        }
        
        return value;
    }
    
    public void UpdateField(Object valueField, String val)
    {
        if (valueField instanceof JTextField)
        {
            ((JTextField)valueField).setText(val);
        }
        else
        {
            if (valueField instanceof JComboBox)
            {
                ((JComboBox)valueField).setSelectedItem(val);
            }
            else
            {
                if (valueField instanceof JList)
                {
                    ((JList)valueField).setSelectedValue(val, true);
                }
                else
                {
                    if (valueField instanceof JTextArea)
                    {
                        ((JTextArea)valueField).setText(val);
                    } 
                    else
                    {
                        if (valueField instanceof ButtonGroup)
                        {
                            ButtonGroup radio = (ButtonGroup)valueField;
                            Enumeration en=radio.getElements();
                            
                            while(en.hasMoreElements()) 
                            {
                                JRadioButton btn=(JRadioButton)en.nextElement();
                                if (btn.getText().equalsIgnoreCase(val))
                                {
                                    btn.setSelected(true);
                                    return;
                                }
                                
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void SaveCurrentValue(String event_tob_id, IVmContextProvider contextProvider)
    {
        try 
        {
            int columnIndex = -1;
            Vector fieldAttributes = null;
            String[][] dbData = null;
            Connection connection = null; 
            Query queryGen = null;
            String att_mnem = null; 
            int current = 0;
            String[][] keyData = null;
            
            if (table != null )
            {
                fieldAttributes = table.fieldAttributes;
                dbData = table.dbData;
                connection = table.getConn();
                queryGen = table.getQueryGenerate();
                current = table.current;
                keyData = table.getKeyData();
            }
            else
            {
                if (panel != null )
                {
                    fieldAttributes = panel.fieldAttributes;
                    dbData = panel.dbData;
                    connection = panel.getConn();
                    queryGen = panel.getQueryGenerate();
                    current = panel.current;
                    keyData = panel.getKeyData();
                }
            }
            
            int k = 0;
            
            for(int b = 0; b < fieldAttributes.size(); b++)
            {
                Object obj[] = (Object[])fieldAttributes.get(b);
                Application.AttType temp_attType =(Application.AttType)(obj[0]);
                
                if (temp_attType.att_id == att_id && temp_attType.tob_id == tob_id)
                {
                    columnIndex = b;
                    att_mnem = temp_attType.att.mnem;
                    break;
                }
                
                if (panel != null && temp_attType.att.sbp == 0)
                {
                    k++;
                }
            }
            
            columnIndex = columnIndex - k;
            
            if (this.Contains(this.subTobs, event_tob_id))
            {
                //pozvana je sa podredjeno tipa komponente, treba preracunati i azurirati jedan atribut
                if (att_mnem != null && queryGen != null && dbData != null)
                {
                    String value = Recalc(dbData, current, contextProvider);
                    String[] row = dbData[current];
                    row[columnIndex] = value;
                    String[] c1=new String[1];
                    String[] c2=new String[1];
                    c1[0] = att_mnem;
                    c2[0] = value;
                    
                     if (table != null)
                     {
                         table.getSelectionModel().setSelectionInterval(current, current);
                         table.setVisible(true);
                         table.repaint();
                     }
                    
                    String[] upit=queryGen.getUpdateQuery(c1,c2,keyData);
                    int[] ret=new int[upit.length];
                    boolean can=true;
                    JDBCQuery query=new JDBCQuery(connection);
                    
                    query.update(upit[ 0 ]);                        
                }
            }
            else
            {
                //Pozvana je sa nadredjenog tipa komponente, moraju se azurati sve zapisti
                 if (att_mnem != null && queryGen != null && dbData != null)
                 {
                     boolean uField = this.updateField;
                     this.updateField = false;
                     
                     if (table != null)
                     {
                         table.setVisible(false);
                     }
                     for(int i = 0; i < dbData.length; i++)
                     {
                         String value = Recalc(dbData, i, contextProvider);
                         String[] row = dbData[i];
                         row[columnIndex] = value;
                         String[] c1=new String[1];
                         String[] c2=new String[1];
                         c1[0] = att_mnem;
                         c2[0] = value;
                         
                         if (table != null)
                         {
                             table.getSelectionModel().setSelectionInterval(i, i);
                         }
                         
                         try 
                         {
                             for(int j = 0; j < keyData.length; j++)
                             {
                                String keyRow[] = keyData[j];
                                String temp = getKeyValues(Integer.parseInt(keyRow[0]), fieldAttributes, row, panel);
                                
                                if (temp != null)
                                {
                                    if (keyRow[1].startsWith("'"))
                                    {
                                        keyRow[1] =  "'" + temp + "'";
                                    }
                                    else
                                    {
                                        keyRow[1] = temp;
                                    }
                                }
                             }
                         }
                         catch(Exception ex)
                         {
                         }
                         String[] upit=queryGen.getUpdateQuery(c1,c2,keyData);
                         int[] ret=new int[upit.length];
                         boolean can=true;
                         JDBCQuery query=new JDBCQuery(connection);
                                 
                         query.update(upit[ 0 ]);
                     }
                     
                     this.updateField = uField;
                     
                     if (table != null)
                     {
                         table.getSelectionModel().setSelectionInterval(current, current);
                         table.setVisible(true);
                         table.repaint();
                     }
                     else
                     {
                        if (panel != null)
                        {
                            try 
                            {
                                ChangeListenerClient client = (ChangeListenerClient)this.clients.get(0);
                                this.UpdateField(client.valueField, dbData[current][columnIndex]);
                            }
                            catch(Exception e)
                            {
                            }
                        }
                     }
                 }
            }
        }
        catch(Exception ex)
        {
        }
    }
    
    public void SaveCurrentValueCalc(String event_tob_id, IVmContextProvider contextProvider)
    {
        try 
        {
            int columnIndex = -1;
            Vector fieldAttributes = null;
            String[][] dbData = null;
            String att_mnem = null; 
            int current = 0;
            
            if (table != null )
            {
                fieldAttributes = table.fieldAttributes;
                dbData = table.dbData;
                connection = table.getConn();
                current = table.current;
            }
            else
            {
                if (panel != null )
                {
                    fieldAttributes = panel.fieldAttributes;
                    dbData = panel.dbData;
                    connection = panel.getConn();
                    current = panel.current;
                }
            }
            
            int k = 0;
            
            for(int b = 0; b < fieldAttributes.size(); b++)
            {
                Object obj[] = (Object[])fieldAttributes.get(b);
                Application.AttType temp_attType =(Application.AttType)(obj[0]);
                
                if (temp_attType.att_id == att_id && temp_attType.tob_id == tob_id)
                {
                    columnIndex = b;
                    att_mnem = temp_attType.att.mnem;
                    break;
                }
            }
            
            if (panel != null )
            {
                Recalc(contextProvider);
            }
            else
            {
                if (att_mnem != null && dbData != null)
                {
                    //table.setVisible(false);
                    for(int i = 0; i < dbData.length; i++)
                    {
                        String value = Recalc(dbData, i, contextProvider);
                        String[] row = dbData[i];
                        row[columnIndex] = value;
                        //table.getSelectionModel().setSelectionInterval(i, i);                        
                    }                    
                }
            }
        }
        catch(Exception ex)
        {
        }
    }
    
    private String getKeyValues(int att_id, Vector fieldAttributes, String row[], DBPanel panel)
    {
        try 
        {
            int columnIndex = -1;
            int k = 0;
            
            for(int b = 0; b < fieldAttributes.size(); b++)
            {
                Object obj[] = (Object[])fieldAttributes.get(b);
                Application.AttType temp_attType =(Application.AttType)(obj[0]);
                
                if (temp_attType.att_id == att_id && temp_attType.tob_id == tob_id)
                {
                    columnIndex = b;
                    break;
                }
                
                if (temp_attType.att.sbp == 0) 
                {
                    k = k + 1;
                }
            }
            
            if (columnIndex == -1)
            {
                return null;
            }
            else
            {
                return row[columnIndex - k];
            }
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    private boolean ClientExists(int a_id, int t_id, int tb_id)
    {
        for(int i = 0; i < this.clients.size(); i++)
        {
            ChangeListenerClient client = (ChangeListenerClient)this.clients.get(i);
            
            if (client.a_id == a_id && client.t_id == t_id && client.tb_id == tb_id)
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean Contains(ArrayList list, String value)
    {
        for(int i = 0; i < list.size(); i++)
        {
            if (list.get(i).equals(value))
            {
                return true;
            }
        }
        return false;
    }
    
    private void getItem(int a_id, int t_id, int tb_id, int is_subord_att, FuncParam funcParam)
    {
        if(f.id != t_id)
        {
            return;
        }
        
        for(int j = 0; j < f.compType.length; j++)
        {
            Application.CompType c = f.compType[j];
            
            if (c.id == tb_id)
            {
                for(int l=0;l<c.itemGroup.length;l++)
                {
                    for(int m=0;m<c.itemGroup[l].items.length;m++)
                    {
                        Application.Item it = c.itemGroup[l].items[m];
                        
                        if(it.elem.getClass().toString().equals("class ui.Application$AttType"))
                        {
                            Application.AttType attType = (Application.AttType)it.elem;
                            
                            if (attType.att_id == a_id)
                            {
                                //if (is_subord_att == 1)
                                {
                                    if(c.data_layout==1)
                                    {
                                        DBTable table =(DBTable)r.getPartByName(""+c.id+"Table");
                                        funcParam.table = table;                                        
                                    }
                                    else
                                    {
                                        DBPanel panel =(DBPanel)r.getPartByName(""+c.id+"DataPanel");
                                        funcParam.panel = panel;
                                    }
                                }
                                
                                funcParam.item = it;
                            }
                        }
                    }
                }
                return;
            }
        }
        
        return;
    }

    public void itemStateChanged(ItemEvent e) 
    {
        Recalc(this);
    }

    public void valueChanged(ListSelectionEvent e) 
    {
        Recalc(this);
    }

    public void actionPerformed(ActionEvent e) 
    {
        if (this.isFunctionButtonListener)
        {
            Recalc(this);
        }
        else
        {
            if (this.isButtonEvent)
            {
                Recalc(this);
            }
        }
    }

    public void DataChanged(String tob_id) 
    {
        if (this.isCalcAtt)
        {
            this.SaveCurrentValueCalc(tob_id, this);
        }
    }

    public void mouseClicked(MouseEvent e) 
    {
        if (this.isMouseClickedEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            cProvider.x = e.getX();
            cProvider.y = e.getY();
            this.Recalc(cProvider);
        }
    }

    public void mousePressed(MouseEvent e) 
    {
        if (this.isMousePressedEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            cProvider.x = e.getX();
            cProvider.y = e.getY();
            this.Recalc(cProvider);
        }
    }

    public void mouseReleased(MouseEvent e) 
    {
        if (this.isMouseReleasedEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            cProvider.x = e.getX();
            cProvider.y = e.getY();
            this.Recalc(cProvider);
        }
    }

    public void mouseEntered(MouseEvent e) 
    {
        if (this.isMouseEnteredEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            cProvider.x = e.getX();
            cProvider.y = e.getY();
            this.Recalc(cProvider);
        }
    }

    public void mouseExited(MouseEvent e) 
    {
        if (this.isMouseExitetedEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            cProvider.x = e.getX();
            cProvider.y = e.getY();
            this.Recalc(cProvider);
        }
    }

    public void keyTyped(KeyEvent e) 
    {
        if (this.isKeyTypedEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            cProvider.keyCode = e.getKeyCode();
            cProvider.keyChar = Character.toString(e.getKeyChar());
            this.Recalc(cProvider);
        }
    }

    public void keyPressed(KeyEvent e) 
    {
        if (this.isKeyPresssedEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            cProvider.keyCode = e.getKeyCode();
            cProvider.keyChar = Character.toString(e.getKeyChar());
            this.Recalc(cProvider);
        }
    }

    public void keyReleased(KeyEvent e) 
    {
        if (this.isKeyReleasedEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            cProvider.keyCode = e.getKeyCode();
            cProvider.keyChar = Character.toString(e.getKeyChar());
            this.Recalc(cProvider);
        }
    }

    public void BeforeNextRecord() 
    {
        if (this.isBeforeNextRecordEvent)
        {
            Recalc(this);
        }
    }

    public void AfterNextRecord() 
    {
        if (this.isAfterNextRecordEvent)
        {
            Recalc(this);
        }
    }

    public void BeforePreviousRecord() 
    {
        if (this.isBeforePreviousRecordEvent)
        {
            Recalc(this);
        }
    }

    public void AfterPreviousRecord() 
    {
        if (this.isAfterPreviousRecordEvent)
        {
            Recalc(this);
        }
    }

    public void BeforeLoadData() 
    {
        //System.out.println("before load data");
        
        if (this.isBeforeLoadDataEvent)
        {
            this.Recalc(this);
             
        }
    }

    public void AfterLoadData() 
    {
        if (this.isAfterLoadDataEvent)
        {
            this.Recalc(this);
        }
    }

    public boolean BeforeInsert() 
    {
        if (this.isDerAtt)
        {
            this.Recalc(this);
            return true;
        }
        
        if (this.isBeforeInsertEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            this.Recalc(cProvider);
            return cProvider.trigger_status;
        }
        return true;
    }

    public void AfterInsert(boolean status, String tob_id)
    {
        if (this.isDerAtt && status)
        {
            this.SaveCurrentValue(tob_id, this);
            return;
        }
        
        if (this.isAfterInsertEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            cProvider.trigger_status = status;
            this.Recalc(cProvider);
        }
    }

    public boolean BeforeUpdate() 
    {
        if (this.isDerAtt)
        {
            this.Recalc(this);
            return true;
        }
        
        if (this.isBeforeUpdateEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            this.Recalc(cProvider);
            return cProvider.trigger_status;
        }
        return true;
    }

    public void AfterUpdate(boolean status, String tob_id) 
    {
        if (this.isDerAtt && status)
        {
            this.SaveCurrentValue(tob_id, this);
            return;
        }
        
        if (this.isAfterUpdateEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            cProvider.trigger_status = status;
            this.Recalc(cProvider);
        }
    }

    public boolean BeforeDelete() 
    {
        if (this.isBeforeDeleteEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            this.Recalc(cProvider);
            return cProvider.trigger_status;
        }
        return true;
    }

    public void AfterDelete(boolean status, String tob_id) 
    {
        if (this.isDerAtt && status)
        {
            this.SaveCurrentValue(tob_id, this);
            return;
        }
        
        if (this.isAfterDeleteEvent)
        {
            EventContextProvider cProvider = new EventContextProvider(this);
            cProvider.trigger_status = status;
            this.Recalc(cProvider);
        }
    }

    public String GetEnvVar(String varName) 
    {
        return "";
    }

    public void ExecuteFunction(String funcName, ArrayList params) 
    {
        if (funcName.equals("update"))
        {
            UpdateForm(params);
        }
    }

    private void UpdateForm(ArrayList params)
    {
        try 
        {
            int tobId = Integer.parseInt((String)params.get(0));
            
            System.out.println(tobId);
            Application.CompType c = null;
            
            for(int i = 0; i < f.compType.length; i++)
            {
                Application.CompType temp_c = f.compType[i];
                
                if (temp_c.id == tobId)
                {
                    c = temp_c;
                    break;
                }
            }
            
            DBTable table = null;  
            DBPanel panel = null; 
            
            if (c != null)
            {
                if(c.data_layout == 1)
                {
                    table =(DBTable)r.getPartByName(""+c.id+"Table");                    
                }
                else
                {
                    if(c.data_layout == 0)
                    {
                        panel =(DBPanel)r.getPartByName(""+c.id+"DataPanel");
                    }
                }
                
                if (params.size() == 2)
                {
                    //tob command 
                    String comName = ((String)params.get(1)).toLowerCase(Locale.US);
                    
                    if (comName.equals("reload"))
                    {
                        if (table != null)
                        {
                            table.setData();
                        }
                        else
                        {
                            if (panel != null)
                            {
                                panel.setData();
                            }
                        }
                        return;
                    }
                    
                    if (comName.equals("save"))
                    {
                        if (table != null)
                        {
                            table.update();
                        }
                        else
                        {
                            if (panel != null)
                            {
                                panel.update();
                            }
                        }
                        return;
                    }
                    
                    if (comName.equals("next_rec"))
                    {
                        if (table != null)
                        {
                            table.getNext();
                        }
                        else
                        {
                            if (panel != null)
                            {
                                panel.getNext();
                            }
                        }
                        return;
                    }
                    
                    if (comName.equals("prev_rec"))
                    {
                        if (table != null)
                        {
                            table.getPrevios();
                        }
                        else
                        {
                            if (panel != null)
                            {
                                panel.getPrevios();
                            }
                        }
                        return;
                    }
                    
                    if (comName.equals("delete"))
                    {
                        if (table != null)
                        {
                            table.delete();
                        }
                        else
                        {
                            if (panel != null)
                            {
                                panel.delete();
                            }
                        }
                        return;
                    }
                    
                    if (comName.equals("activate"))
                    {
                        JInternalFrame fram = (JInternalFrame)r.getPartByName(""+c.id+"compType");
                        
                        if (fram != null)
                        {
                            fram.setVisible(true);
                        }
                        return;
                    }
                    
                    if (comName.equals("deactivate"))
                    {
                        JInternalFrame fram = (JInternalFrame)r.getPartByName(""+c.id+"compType");
                        
                        if (fram != null)
                        {
                            fram.setVisible(false);
                        }
                    }
                    
                    if (comName.equals("show"))
                    {
                        if (table != null)
                        {
                            table.setVisible(true);
                        }
                        else
                        {
                            if (panel != null)
                            {
                                panel.setVisible(true);
                            }
                        }
                        return;
                    }
                    
                    if (comName.equals("hide"))
                    {
                        if (table != null)
                        {
                            table.setVisible(false);
                        }
                        else
                        {
                            if (panel != null)
                            {
                                panel.setVisible(false);
                            }
                        }
                        return;
                    }
                }
                else
                {
                    String setList = ((String)params.get(1)).toLowerCase(Locale.US);
                    String list[] = setList.split(",");
                    
                    for(int i = 0; i < list.length; i++)
                    {
                        String setStr = list[i];
                        int index = setStr.indexOf(".");
                        
                        int attId = Integer.parseInt(setStr.substring(0, index));
                        String propValue = setStr.substring(index + 1);
                        
                        this.UpdateTobFieldProp(panel, table, attId, tobId, propValue, params.get(i + 2).toString());
                    }
                }
            }
        }
        catch(Exception e)
        {}
    }
    
    private void UpdateTobFieldProp(DBPanel panel, DBTable table, int att_id, int tob_id, String name, String value)
    {
        try 
        {
            JComponent comp = (JComponent)r.getPartByName(""+att_id +tob_id+"InputField");
            name = name.toLowerCase(Locale.US);
            
            if (name.equals("value"))
            {
                this.UpdateField(comp, value);        
                return;
            }
            
            if (name.equals("visible"))
            {
                boolean ind = value.equalsIgnoreCase("true");                
                comp.setVisible(ind);                
                return;
            }
            
            if (name.equals("enabled"))
            {
                boolean ind = value.equalsIgnoreCase("true");                
                comp.setEnabled(ind);                
                return;
            }
            
            if (name.equals("focus"))
            {
                boolean ind = value.equalsIgnoreCase("true");                
                
                if (ind)
                {
                    comp.grabFocus();
                }
                return;
            }
            
            if (name.equals("bgcolor"))
            {
                String colorStr = value.substring(1);
                
                
                int r = this.ParseHex(colorStr.substring(0,2));
                int g = this.ParseHex(colorStr.substring(2,4));
                int b = this.ParseHex(colorStr.substring(4,6));
                
                Color col = new Color(r, g, b);
                comp.setBackground(col);
                return;
            }
        }
        catch(Exception e)
        {}
    }
    
    public void DispatchSignal(ArrayList params) {
    }

    public void internalFrameOpened(InternalFrameEvent e) 
    {
        if (this.isFrameOpenListener)
        {
            this.Recalc(this);
        }
    }

    public int ParseHex(String num)
    {
        int suma = 0;
        
        for(int i = 0; i < num.length();i++)
        {
            char c = num.charAt(i);
            int digit = 0;
            
            if (c <= '9' && c >= '0')
            {
                digit = c - '0';
            }
            else
            {
                if (c <= 'F' && c >= 'A')
                {
                    digit = 10 + c - 'A';
                }
                else
                {
                    if (c <= 'f' && c >= 'a')
                    {
                        digit = 10 + c - 'a';
                    }
                }
            }
            suma = suma * 16 + digit;
        }
        return suma;
    }
    
    public void internalFrameClosing(InternalFrameEvent e) {
    }

    public void internalFrameClosed(InternalFrameEvent e) 
    {
        
    }

    public void internalFrameIconified(InternalFrameEvent e) {
    }

    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    public void internalFrameActivated(InternalFrameEvent e) 
    {
        if (this.isFrameOpenListener)
        {
            this.Recalc(this);
        }
    }

    public void internalFrameDeactivated(InternalFrameEvent e) 
    {
        if (this.isFrameClosedListener)
        {
            this.Recalc(this);
        }
    }

    private class ChangeListenerClient
    {
        public int fun_id;
        public String funcName;
        public int a_id;
        public int t_id;
        public int tb_id;
        
        public Application.Item item;
        ArrayList params = new ArrayList();
        Object valueField = null;
        
        public ChangeListenerClient()
        {}
    }
    
    private class FuncParam
    {
        public Application.Item item;
        public String value;
        public Object valueField;
        public int is_subord_att;
        DBTable table = null;
        DBPanel panel = null;
        public int type; //0-defaut, 1 - const , 2 - atribute 
        int inout = 1;
        
        public FuncParam()
        {}
        
        public String getValue()
        {
            if (type == 0)
            {
                return null;
            }
            
            if (type == 1)
            {
                return value;
            }
            else
            {
               if (valueField instanceof JTextField)
               {
                    return ((JTextField)valueField).getText();
               }
               else
                {
                   if (valueField instanceof JComboBox)
                   {
                        JComboBox box = ((JComboBox)valueField);
                        if (box.getSelectedItem() == null)
                        {
                            return "";
                        }
                        
                        return box.getSelectedItem().toString();
                   }
                   else
                   {
                       if (valueField instanceof JList)
                       {
                            JList list = ((JList)valueField);
                            
                            if (list.getSelectedValue() != null)
                            {
                                return list.getSelectedValue().toString();
                            }
                            return "";
                       }
                       else
                       {
                           if (valueField instanceof JTextArea)
                           {
                                return ((JTextArea)valueField).getText();
                           }
                           else 
                           {
                                if (valueField instanceof ButtonGroup)
                                {
                                    ButtonGroup radio = (ButtonGroup)valueField;
                                    Enumeration en=radio.getElements();
                                    String value="";
                                    
                                    while(en.hasMoreElements()) 
                                    {
                                        JRadioButton btn=(JRadioButton)en.nextElement();
                                        if(btn.isSelected())
                                        {
                                            value=btn.getText();
                                        }
                                    }
                                    return value;
                                }
                           }
                       }
                   }
               }
               return "";
            }
        }
        
        public String getValueDbData()
        {
            if (type == 0)
            {
                return null;
            }
            
            String[][] dbData = null;
            Vector fieldAttributes = null; 
            int current = 0;
            
            if (panel != null)
            {
                dbData = panel.dbData;
                fieldAttributes = panel.fieldAttributes; 
                current = panel.current;
            }
            else
            {
                if (table != null)
                {
                    dbData = table.dbData;
                    fieldAttributes = table.fieldAttributes; 
                    current = table.current;
                }
            }
            
            if (dbData != null)
            {
                int columnIndex = -1;               
                
                for(int b = 0; b < fieldAttributes.size(); b++)
                {
                    Object obj[] = (Object[])fieldAttributes.get(b);
                    Application.AttType temp_attType =(Application.AttType)(obj[0]);
                    
                    if (temp_attType.att_id == ((Application.AttType)item.elem).att_id && temp_attType.tob_id == ((Application.AttType)item.elem).tob_id)
                    {
                        columnIndex = b;
                        break;
                    }
                }
                
                if (columnIndex != -1)
                {
                    String[][] data = dbData;
                    String[] row = data[current];
                    return row[columnIndex];
                }
            }
                            
            return "";
        }
        
        public String getValueDbData(String dbData[][], int row)
        {
            Vector fieldAttributes = null; 
            int current = row;
            
            if (panel != null)
            {
                fieldAttributes = panel.fieldAttributes; 
            }
            else
            {
                if (table != null)
                {
                    fieldAttributes = table.fieldAttributes; 
                }
            }
            
            if (dbData != null)
            {
                int columnIndex = -1;               
                int k = 0;
                
                for(int b = 0; b < fieldAttributes.size(); b++)
                {
                    Object obj[] = (Object[])fieldAttributes.get(b);
                    Application.AttType temp_attType =(Application.AttType)(obj[0]);
                    
                    if (temp_attType.att_id == ((Application.AttType)item.elem).att_id && temp_attType.tob_id == ((Application.AttType)item.elem).tob_id)
                    {
                        columnIndex = b;
                        break;
                    }
                    
                    if (panel != null && temp_attType.att.sbp == 0)
                    {
                        k = k + 1;
                    }
                }
                
                columnIndex = columnIndex - k;
                
                if (columnIndex != -1)
                {
                    String[][] data = dbData;
                    String[] r = data[current];
                    return r[columnIndex];
                }
            }
                            
            return "";
        }
        
        public ArrayList getSetValue()
        {
            ArrayList result  = new ArrayList();
            int columnIndex = -1;
            Vector fieldAttributes = null;
            String[][] dbData = null;
            
            if (table != null )
            {
                fieldAttributes = table.fieldAttributes;
                dbData = table.dbData;
            }
            else
            {
                if (panel != null )
                {
                    fieldAttributes = panel.fieldAttributes;
                    dbData = panel.dbData;
                }
            }
            
            int k = 0;
            
            for(int b = 0; b < fieldAttributes.size(); b++)
            {
                Object obj[] = (Object[])fieldAttributes.get(b);
                Application.AttType temp_attType =(Application.AttType)(obj[0]);
                
                if (temp_attType.att_id == ((Application.AttType)item.elem).att_id && temp_attType.tob_id == ((Application.AttType)item.elem).tob_id)
                {
                    columnIndex = b;
                    break;
                }
                
                if (panel != null && temp_attType.att.sbp == 0)
                {
                    k = k + 1;
                }
            }
            
            columnIndex = columnIndex - k;
            
            if (columnIndex != -1)
            {
                String[][] data = dbData;
                
                for(int i = 0; i < data.length; i++)
                {
                    String[] row = data[i];
                    
                    if (columnIndex < row.length)
                    {
                        result.add(row[columnIndex]);
                    }
                }
            }
            
            return result;
        }
    }
    
    public static int GetFuncId(Connection conn, int att_id, int tob_id, int tf_id)
    {
        try 
        {
            String sql = "select * from IISC_ATT_TOB where Att_id=" + att_id + " and Tf_id=" + tf_id + " and Tob_id=" + tob_id;
            
            Statement stmt = conn.createStatement();            
            ResultSet rs = stmt.executeQuery(sql);
            
            if(rs.next())
            {
                return rs.getInt("Fun_id");
            }
        }
        catch(Exception e)
        {}
        return -1;
    }
    
    public static String GetEventName(Connection conn, int event_id)
    {
        try 
        {
            String sql = "select * from IISC_EVENT where Event_id=" + event_id;
            
            Statement stmt = conn.createStatement();            
            ResultSet rs = stmt.executeQuery(sql);
            
            if(rs.next())
            {
                return rs.getString("Event_mnem");
            }
        }
        catch(Exception e)
        {}
        return "";
    }
    
    private static void AddItemEventListeners(Application.Item it, int att_id, Object obj, Application.FormType f,Connection con, Renderer r, Document funcDoc, VirtualMashine vm, Application.CompType c)
    {
        try 
        {
            try 
            {
                String sql = "select * from IISC_ATT_TOB_EVENT where Att_id=" + att_id + " and Tf_id=" + f.id + " and Tob_id=" + c.id + " and Event_level=1 and Event_type=3";
                
                Statement stmt = con.createStatement();            
                ResultSet rs = stmt.executeQuery(sql);
                
                while(rs.next())
                {
                    int event_id = rs.getInt("Event_id");
                    int fun_id = rs.getInt("Fun_id");
                    
                    if (event_id > 0)
                    {
                        String event_name = GetEventName(con, event_id);
                        
                        TextBoxChangeListener listener = new TextBoxChangeListener(((Application.AttType)it.elem).att_id, c.id, f.id, it, f, c, con, r, funcDoc, vm, fun_id, true, "1", 3);
                        
                        listener.event_type = 3;
                        listener.eventName = event_name;
                        listener.updateField = false;
                        
                        if (event_name.startsWith("Mouse"))
                        {
                            if (event_name.equals("Mouse Clicked"))
                            {
                                listener.isMouseClickedEvent = true;
                            }
                            else
                            {
                                if (event_name.equals("Mouse Pressed"))
                                {
                                    listener.isMousePressedEvent = true;
                                }
                                else
                                {
                                    if (event_name.equals("Mouse Released"))
                                    {
                                        listener.isMouseReleasedEvent = true;
                                    }
                                    else
                                    {
                                        if (event_name.equals("Mouse Entered"))
                                        {
                                            listener.isMouseEnteredEvent = true;
                                        }
                                        else
                                        {
                                            if (event_name.equals("Mouse Exited"))
                                            {
                                                listener.isMouseExitetedEvent = true;
                                            }
                                        }
                                    }
                                }
                            }
                            
                            try 
                            {
                                JComponent comp = (JComponent)obj;
                                comp.addMouseListener(listener);
                            }
                            catch(Exception e)
                            {
                            }
                        }
                        else
                        {
                            if (event_name.startsWith("Button"))
                            {
                                listener.isButtonEvent = true;
                                
                                try 
                                {
                                    Object objbtn = r.getPartByName(""+((Application.AttType)it.elem).att_id +c.id+"FuncButton");
                                    
                                    if (objbtn != null && objbtn instanceof JButton)
                                    {
                                        JButton btn = (JButton)objbtn;
                                        
                                        btn.addActionListener(listener);
                                    }
                                }
                                catch(Exception e)
                                {
                                }
                            }
                            else
                            {
                                if (event_name.startsWith("Focus"))
                                {
                                    if (event_name.equals("Focus Gained"))
                                    {
                                        listener.isFocusGainedEvent = true;
                                    }
                                    else
                                    {
                                        if (event_name.equals("Focus Lost"))
                                        {
                                            listener.isFocusLostEvent = true;
                                        }
                                    }
                                    
                                    try 
                                    {
                                        JComponent comp = (JComponent)obj;
                                        comp.addFocusListener(listener);
                                    }
                                    catch(Exception e)
                                    {
                                    }
                                }
                                else
                                {
                                    if (event_name.startsWith("Key"))
                                    {
                                        if (event_name.equals("Key Typed"))
                                        {
                                            listener.isKeyTypedEvent = true;
                                        }
                                        else
                                        {
                                            if (event_name.equals("Key Pressed"))
                                            {
                                                listener.isKeyPresssedEvent = true;
                                            }
                                            else
                                            {
                                                if (event_name.equals("Key Released"))
                                                {
                                                    listener.isKeyReleasedEvent = true;
                                                }
                                            }
                                        }
                                        
                                        try 
                                        {
                                            JComponent comp = (JComponent)obj;
                                            comp.addKeyListener(listener);
                                        }
                                        catch(Exception e)
                                        {
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch(Exception e)
            {}
        }
        catch(Exception e)
        {
        }        
    }
    
    private static void AddFuncButtonEventListeners(Application.FormType f,Connection con, Renderer r, Document funcDoc, VirtualMashine vm, Application.CompType c)
    {
        try 
        {
            String sql = "select * from IISC_TOB_BUTTON where Tob_id=" + c.id;
            
            Statement stmt = con.createStatement();            
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next())
            {
                int button_id = rs.getInt("Btn_id");
                int fun_id = rs.getInt("Fun_id");
                
                if (button_id > 0)
                {
                    TextBoxChangeListener listener = new TextBoxChangeListener(-1, c.id, f.id, null, f, c, con, r, funcDoc, vm, fun_id, true, "4", button_id);
                    listener.isFunctionButtonListener = true;
                    JButton btn = (JButton)r.getPartByName(""+button_id+"FuncCalcButton");
                    
                    if (btn != null)
                    {
                        btn.addActionListener(listener);
                    }
                }
            }
        }
        catch(Exception e)
        {}
    }
    
    private static void AddCompTypeEventListeners(Application.FormType f,Connection con, Renderer r, Document funcDoc, VirtualMashine vm, Application.CompType c)
    {
        try 
        {
            try 
            {
                String sql = "select * from IISC_ATT_TOB_EVENT where Tf_id=" + f.id + " and Tob_id=" + c.id + " and Event_level=2 and Event_type=3";
                
                Statement stmt = con.createStatement();            
                ResultSet rs = stmt.executeQuery(sql);
                
                DBTable table = null;
                DBPanel panel = null;
                
                if(c.data_layout == 1)
                {
                    table =(DBTable)r.getPartByName(""+c.id+"Table");                    
                }
                else
                {
                    if(c.data_layout == 0)
                    {
                        panel =(DBPanel)r.getPartByName(""+c.id+"DataPanel");
                    }
                }
                
                while(rs.next())
                {
                    int event_id = rs.getInt("Event_id");
                    int fun_id = rs.getInt("Fun_id");
                    
                    if (event_id > 0)
                    {
                        String event_name = GetEventName(con, event_id);
                        
                        TextBoxChangeListener listener = new TextBoxChangeListener(-1, c.id, f.id, null, f, c, con, r, funcDoc, vm, fun_id, true, "2", 3);
                        
                        listener.event_type = 3;
                        listener.eventName = event_name;
                        listener.updateField = false;
                        
                        if (event_name.startsWith("Mouse"))
                        {
                            if (event_name.equals("Mouse Clicked"))
                            {
                                listener.isMouseClickedEvent = true;
                            }
                            else
                            {
                                if (event_name.equals("Mouse Pressed"))
                                {
                                    listener.isMousePressedEvent = true;
                                }
                                else
                                {
                                    if (event_name.equals("Mouse Released"))
                                    {
                                        listener.isMouseReleasedEvent = true;
                                    }
                                    else
                                    {
                                        if (event_name.equals("Mouse Entered"))
                                        {
                                            listener.isMouseEnteredEvent = true;
                                        }
                                        else
                                        {
                                            if (event_name.equals("Mouse Exited"))
                                            {
                                                listener.isMouseExitetedEvent = true;
                                            }
                                        }
                                    }
                                }
                            }
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.addMouseListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.addMouseListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                        
                        if (event_name.startsWith("Focus"))
                        {
                            if (event_name.equals("Focus Gained"))
                            {
                                listener.isFocusGainedEvent = true;
                            }
                            else
                            {
                                if (event_name.equals("Focus Lost"))
                                {
                                    listener.isFocusLostEvent = true;
                                }
                            }
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.addFocusListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.addFocusListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                            
                        if (event_name.startsWith("Key"))
                        {
                            if (event_name.equals("Key Typed"))
                            {
                                listener.isKeyTypedEvent = true;
                            }
                            else
                            {
                                if (event_name.equals("Key Pressed"))
                                {
                                    listener.isKeyPresssedEvent = true;
                                }
                                else
                                {
                                    if (event_name.equals("Key Released"))
                                    {
                                        listener.isKeyReleasedEvent = true;
                                    }
                                }
                            }
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.addKeyListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.addKeyListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                                
                        if (event_name.equals("Before Next Record"))
                        {
                            listener.isBeforeNextRecordEvent = true;
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.AddNextRecordListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.AddNextRecordListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }                                    
                        
                        if (event_name.equals("After Next Record"))
                        {
                            listener.isAfterNextRecordEvent = true;
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.AddNextRecordListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.AddNextRecordListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                        
                        if (event_name.equals("Before Previous Record"))
                        {
                            listener.isBeforePreviousRecordEvent = true;
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.AddPrevRecordListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.AddPrevRecordListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                        
                        if (event_name.equals("After Previous Record"))
                        {
                            listener.isAfterPreviousRecordEvent = true;
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.AddPrevRecordListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.AddPrevRecordListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                        
                        if (event_name.equals("After Load Data"))
                        {
                            listener.isAfterLoadDataEvent = true;
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.AddLoadDataListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.AddLoadDataListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                        
                        if (event_name.equals("Before Load Data"))
                        {
                            listener.isBeforeLoadDataEvent  = true;
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.AddLoadDataListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.AddLoadDataListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                        
                        if (event_name.equals("Before Insert Record"))
                        {
                            listener.isBeforeInsertEvent = true;
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.AddBeforeInsertListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.AddBeforeInsertListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                        
                        if (event_name.equals("After Insert Record"))
                        {
                            listener.isAfterInsertEvent = true;
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.AddAfterInsertListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.AddAfterInsertListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                        
                        if (event_name.equals("Before Update Record"))
                        {
                            listener.isBeforeUpdateEvent = true;
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.AddBeforeUpdateListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.AddBeforeUpdateListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                        
                        if (event_name.equals("After Update Record"))
                        {
                            listener.isAfterUpdateEvent = true;
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.AddAfterUpdateListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.AddAfterUpdateListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                        
                        if (event_name.equals("Before Delete Record"))
                        {
                            listener.isBeforeDeleteEvent = true;
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.AddBeforeDeleteListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.AddBeforeDeleteListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                        
                        if (event_name.equals("After Delete Record"))
                        {
                            listener.isAfterDeleteEvent = true;
                            
                            try 
                            {
                                if(c.data_layout == 1)
                                {
                                    table.AddAfterDeleteListener(listener);                                    
                                }
                                else
                                {
                                    if(c.data_layout == 0)
                                    {
                                        panel.AddAfterDeleteListener(listener);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                            }
                            continue;
                        }
                    }
                }
            }
            catch(Exception e)
            {}
        }
        catch(Exception e)
        {
        }        
    }
    
    private static void AddFormTypeEventListeners(Application.FormType f,Connection con, Renderer r, Document funcDoc, VirtualMashine vm, Application.CompType c)
    {
        try 
        {
            try 
            {
                String sql = "select * from IISC_ATT_TOB_EVENT where Tf_id=" + f.id + " and Event_level=3 and Event_type=3";
                
                Statement stmt = con.createStatement();            
                ResultSet rs = stmt.executeQuery(sql);
                
                DBTable table = null;
                DBPanel panel = null;
                
                if(c.data_layout == 1)
                {
                    table =(DBTable)r.getPartByName(""+c.id+"Table");                    
                }
                else
                {
                    if(c.data_layout == 0)
                    {
                        panel =(DBPanel)r.getPartByName(""+c.id+"DataPanel");
                    }
                }
                
                JInternalFrame fram = (JInternalFrame)r.getPartByName(""+c.id+"compType");
                
                while(rs.next())
                {
                    int event_id = rs.getInt("Event_id");
                    int fun_id = rs.getInt("Fun_id");
                    
                    if (event_id > 0)
                    {
                        String event_name = GetEventName(con, event_id);
                        
                        TextBoxChangeListener listener = new TextBoxChangeListener(-1, c.id, f.id, null, f, c, con, r, funcDoc, vm, fun_id, true, "2", 3);
                        
                        listener.event_type = 3;
                        listener.eventName = event_name;
                        listener.updateField = false;
                        
                        if (event_name.equals("On Open Form"))
                        {
                            listener.isFrameOpenListener = true;
                            
                            if (fram != null)
                            {
                                fram.addInternalFrameListener(listener);
                                listener.internalFrameActivated(null);
                            }
                        }
                        else
                        {
                            if (event_name.equals("On Close Form"))
                            {
                                listener.isFrameClosedListener = true;
                                
                                if (fram != null)
                                {
                                    fram.addInternalFrameListener(listener);
                                }
                            }
                        }
                    }
                }
            }
            catch(Exception e)
            {}
        }
        catch(Exception e)
        {}
    }
    
    public static void CreateDataListeners(Application.FormType f,Connection con, Renderer r, Document funcDoc, VirtualMashine vm, Application.CompType c)
    {
        if (c.superord == -1)
        {
            AddFormTypeEventListeners(f, con, r, funcDoc, vm, c);
        }
        
        AddCompTypeEventListeners(f, con, r, funcDoc, vm, c);
        AddFuncButtonEventListeners(f,con,r, funcDoc, vm, c);
        
        for(int l=0;l<c.itemGroup.length;l++)
        {
            for(int m=0;m<c.itemGroup[l].items.length;m++)
            {
                Application.Item it = c.itemGroup[l].items[m];
                
                if(it.elem instanceof Application.AttType)
                {
                    /*if(c.data_layout == 1)
                    {
                        TextBoxChangeListener listener = new TextBoxChangeListener(((Application.AttType)it.elem).att_id, c.id, f.id,  f, this.con, r, this.funcDoc, this.vm);
                        table.AddDataChangeListener(listener);
                        
                    }
                     if(c.data_layout == 0)
                     {
                         TextBoxChangeListener listener = new TextBoxChangeListener(((Application.AttType)it.elem).att_id, c.id, f.id,  f, this.con, r, this.funcDoc, this.vm);
                         panel.AddDataChangeListener(listener);
                     }
                    */
                    int att_id = ((Application.AttType)it.elem).att_id;
                    int fun_id = GetFuncId(con, att_id, c.id, f.id);
                    
                    Object obj = r.getPartByName(""+((Application.AttType)it.elem).att_id +c.id+"InputField");
                    
                    if (fun_id > 0)
                    {
                        TextBoxChangeListener listener = new TextBoxChangeListener(((Application.AttType)it.elem).att_id, c.id, f.id, it, f, c, con, r, funcDoc, vm, fun_id, true, "0", -1);
                        listener.event_type = -1;
                        listener.eventName = "";
                        listener.updateField = true;
                    }
                    
                    AddItemEventListeners(it, att_id, obj, f, con, r, funcDoc, vm, c);
                }
            }
        }
    }
    
    public class EventContextProvider implements IVmContextProvider
    {
        public int x;
        public int y;
        public boolean trigger_status;
        public int keyCode; 
        public String keyChar;
        TextBoxChangeListener listener; 
        
        public EventContextProvider(TextBoxChangeListener listener)
        {
            this.trigger_status = true;
            this.listener = listener;
        }

        public String GetEnvVar(String varName) 
        {
            if (varName == null)
            {
                return "";
            }
            varName = varName.toLowerCase(Locale.US);
            
            if (varName.equals("trigger_status"))
            {
                return Boolean.toString(trigger_status);
            }
            
            if (varName.equals("x_mouse"))
            {
                return Integer.toString(x);
            }
            
            if (varName.equals("y_mouse"))
            {
                return Integer.toString(y);
            }
            
            if (varName.equals("key_code"))
            {
                return Integer.toString(keyCode);
            }
            
            if (varName.equals("key_text"))
            {
                return "'" + keyChar + "'";
            }
            
            return listener.GetEnvVar(varName);
        }

        public void ExecuteFunction(String funcName, ArrayList params) 
        {
            listener.ExecuteFunction(funcName, params);
        }

        public void DispatchSignal(ArrayList params) 
        {
            String sig_name = params.get(0).toString().toLowerCase(Locale.US);
            
            if (sig_name.equals("abort_trigger"))
            {
                this.trigger_status = false;
            }
        }
    }
}