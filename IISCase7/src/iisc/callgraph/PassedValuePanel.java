package iisc.callgraph;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.JPanel;

import iisc.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PassedValuePanel extends JDialog 
{

    private int width;
    private int height;
    private int PR_id;
    private int Tf_id;
    private int calledTf;
    public int CS_id;
    private Connection conn;
    private IISFrameMain parent;
    private Form form;
    private ListPanel lp;
    private RadioPanel rp;
    private Vector attVec;
    private Vector parVec;
    private Vector radioVector;
    private int index = -1;
    private boolean checkEnabled = false;
    private JLabel parLbl = new JLabel("Passed Value");
    private JLabel pvLbl = new JLabel("Parameter");
    private JButton saveBtn = new JButton("OK");
    private JButton cancelBtn = new JButton("Cancel");
    private JButton applyBtn = new JButton("Apply");
    private JButton helpBtn = new JButton("");
    private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    
    public static final int OK = 1;
    public static final int CANCEL = 0;
    public int action = 0;
    private int BA_id;
    private JTabbedPane tabPane = new JTabbedPane();
    private JPanel bindingPanel = new JPanel(null); 
    private JPanel optionPanel = new JPanel(null);
    
    
    //Promjenjive koje su za calling metod 
    private JPanel metodPanel = new JPanel(null);
    private JCheckBox selOpenChb = new JCheckBox();
    private JCheckBox restectedChb = new JCheckBox();
    private JLabel cmLbl = new JLabel("Calling metod");
    private JLabel selOpLbl = new JLabel("Select on open");
    private JLabel resOpLbl = new JLabel("Restricted select");
    
    private JPanel modePanel = new JPanel(null);
    private JPanel closePanel = new JPanel(null);
    private JRadioButton modalBtn = new JRadioButton();
    private JRadioButton nonModalBtn = new JRadioButton();
    private JLabel modeLbl = new JLabel("Calling mode");
    private JLabel modalLbl = new JLabel("Modal");
    private JLabel nonmodalLbl = new JLabel("Non-modal");
    private JCheckBox closeChb = new JCheckBox();
    private JLabel closeLbl = new JLabel("Close calling form"); 
    
    //Panel koji ima informacije o UI izgledu
    private JPanel uiPanel = new JPanel(null);
    private JCheckBox miChb = new JCheckBox();
    private JCheckBox btnChb = new JCheckBox();
    private JLabel uiLbl = new JLabel("UI position");
    private JLabel miLbl = new JLabel("Show as menu item ");
    private JLabel btnLbl = new JLabel("Show as button");
     
    public PassedValuePanel(IISFrameMain _parent, Connection _conn, int _PR_id, int _Tf_id, int _calledTf, int _CS_id, int _BA_id)
    {
        super((Frame)_parent,"Edit calling struct", true);
        width = 403;
        height = 315;
        parent = _parent;
        conn = _conn;
        BA_id = _BA_id;
        PR_id = _PR_id;
        Tf_id = _Tf_id;
        calledTf = _calledTf;
        CS_id = _CS_id;
        
        getContentPane().setLayout(null);
        
        setBounds(0, 0, width, height);
        tabPane.setBounds(5, 0, 380, 240);
        //bindingPanel.setBounds(35, 0, 360, 240);
        tabPane.addTab("Binding", bindingPanel);
        tabPane.addTab("Options", optionPanel);
        getContentPane().add(tabPane);
        
       // setBorder(new LineBorder(new Color(124, 124, 124), 1));
        lp = new ListPanel(140, 170, this, calledTf, conn);
        bindingPanel.add(lp);
        pvLbl.setBounds(32, 5, 140, 15);
        
        bindingPanel.add(pvLbl);
        lp.RepaintAll();
        InitRadioPanel();  
        InitRadioValue();
        InitMetodPanel();
        
        if (lp.pPanels.size() > 0)
        {
            ParameterPanel pp = (ParameterPanel) lp.pPanels.get(0);
            pp.Select();
        }
        AddButtons();
        repaint();
        lp.RepaintAll();
    }
    
    private void InitMetodPanel()
    {
        
        //Metod panel
        cmLbl.setBounds(5, 10, 70, 20);
        metodPanel.setBounds(80, 12, 270, 50);
        metodPanel.setBorder(new LineBorder(new Color(124, 124, 124), 1));
        
        selOpLbl.setBounds(35, 5, 150, 17);
        resOpLbl.setBounds(35, 30, 150, 17);
        selOpenChb.setText("");
        
        selOpenChb.setBounds(200, 5, 17, 17);
        restectedChb.setBounds(200, 30, 17, 17);
        
        metodPanel.add(selOpLbl);
        metodPanel.add(resOpLbl);
        metodPanel.add(selOpenChb);
        metodPanel.add(restectedChb);
        
        optionPanel.add(metodPanel);
        optionPanel.add(cmLbl);
        
        //Metod Panel
         modeLbl.setBounds(5, 83, 70, 20);
         modePanel.setBounds(80, 85, 270, 50);
         modePanel.setBorder(new LineBorder(new Color(124, 124, 124), 1));
         
         closePanel.setBounds(120, 0, 150, 50);
         closePanel.setBorder(new LineBorder(new Color(124, 124, 124), 1));
         modePanel.add(closePanel);
         
         modalLbl.setBounds(10, 5, 100, 17);
         nonmodalLbl.setBounds(10, 30, 100, 17);
         modalBtn.setBounds(90, 5, 17, 17);
         
         modalBtn.setText("");
         nonModalBtn.setBounds(90, 30, 17, 17);
         nonModalBtn.setText("");
         modePanel.add(modalLbl);
         modePanel.add(nonmodalLbl);
         modePanel.add(modalBtn);
         modePanel.add(nonModalBtn);
         
         closeLbl.setBounds(10, 20, 100, 17);
         closeChb.setBounds(110, 20, 17, 17);
         closePanel.setLayout(null);
         closePanel.add(closeLbl);
         closePanel.add(closeChb);
        
         optionPanel.add(modePanel);
         optionPanel.add(modeLbl);
         
         //dio za ui prikaz
          uiLbl.setBounds(5, 153, 70, 20);
          uiPanel.setBounds(80, 156, 270, 50);
          uiPanel.setBorder(new LineBorder(new Color(124, 124, 124), 1));
          
          miLbl.setBounds(35, 5, 150, 17);
          btnLbl.setBounds(35, 30, 150, 17);
          miChb.setText("");
          
          miChb.setBounds(200, 5, 17, 17);
          btnChb.setBounds(200, 30, 17, 17);
          
          uiPanel.add(miLbl);
          uiPanel.add(btnLbl);
          uiPanel.add(miChb);
          uiPanel.add(btnChb);
          
          optionPanel.add(uiPanel);
          optionPanel.add(uiLbl);
        
        /*  
        selOpenChb.addActionListener(new ActionListener()
        {
              public void actionPerformed(ActionEvent ae)
              {
                  if (!restectedChb.isSelected())
                  {
                      selOpenChb.setSelected(true);
                  }
              }
          });
          
        restectedChb.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if (!selOpenChb.isSelected())
                {
                    restectedChb.setSelected(true);
                }
            }
        });
        */
        //Metod
        btnChb.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if (!miChb.isSelected())
                {
                    btnChb.setSelected(true);
                }
            }
        });
        
        miChb.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if (!btnChb.isSelected())
                {
                    miChb.setSelected(true);
                }
            }
        });
        
        modalBtn.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent ae)
          {
              modalBtn.setSelected(true);
              nonModalBtn.setSelected(false);
              closeChb.setEnabled(false);
          }
        });
     
        nonModalBtn.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent ae)
          {
              modalBtn.setSelected(false);
              nonModalBtn.setSelected(true);
              closeChb.setEnabled(true);
          }
        });
        
        InitFromDataBase();
    }
    
    private void InitFromDataBase()
    {
       
            //ako ta calling stuktura vec postoji onda podesi kontrole prema njoj
            
            if (CS_id > 0)
            {
                int temp = 0; 
                
                try
                {
                    Statement statement = conn.createStatement();
                    ResultSet rs = statement.executeQuery("select CMet_id,CMod_id,CS_call_UI_position from IISC_CALLING_STRUCT where CS_id=" + CS_id);
                    
                    if(rs.next())
                    {
                        temp = rs.getInt("CMet_id");
                        
                        if (temp == 0)
                        {
                            selOpenChb.setSelected(true);
                            restectedChb.setSelected(false);
                        }
                        else
                        {
                            if (temp == 1)
                            {
                                selOpenChb.setSelected(false);
                                restectedChb.setSelected(true);
                            }
                            else
                            {
                                if (temp == 2)
                                {
                                    selOpenChb.setSelected(true);
                                    restectedChb.setSelected(true);
                                }
                                else
                                {
                                    selOpenChb.setSelected(false);
                                    restectedChb.setSelected(false);
                                }
                            }
                        }
                        
                        temp = rs.getInt("CMod_id");
                        
                        if (temp == 0)
                        {
                            modalBtn.setSelected(true);
                            closeChb.setEnabled(false);
                        }
                        else
                        {
                            if (temp == 1)
                            {
                                nonModalBtn.setSelected(true);
                                closeChb.setSelected(false);
                            }
                            else
                            {
                                nonModalBtn.setSelected(true);
                                closeChb.setSelected(true);
                            }
                        }
                        
                        
                        temp = rs.getInt("CS_call_UI_position");
                        
                        if (temp == 0)
                        {
                            miChb.setSelected(true);
                            btnChb.setSelected(false);
                        }
                        else
                        {
                            if (temp == 1)
                            {
                                miChb.setSelected(false);
                                btnChb.setSelected(true);
                            }
                            else
                            {
                                miChb.setSelected(true);
                                btnChb.setSelected(true);
                            }
                        }
                        
                        
                    }
                    else
                    {
                        modalBtn.setSelected(true);
                        miChb.setSelected(true);
                        closeChb.setEnabled(false);
                    }
                }
                catch(SQLException sqle)
                {
                    modalBtn.setSelected(true);
                    miChb.setSelected(true);
                    closeChb.setEnabled(false);
                }
            }
            else//ako ne postoji u bazi onda inicijalizovatai praznu
            {
                modalBtn.setSelected(true);
                miChb.setSelected(true);
                closeChb.setEnabled(false);
            }
    }
    
    
    private void AddButtons()
    {
        applyBtn.setBounds(79, 250, 75, 30);
        saveBtn.setBounds(169, 250, 75, 30);
        cancelBtn.setBounds(259, 250, 75, 30);
        helpBtn.setBounds(349, 250, 35, 30);
        
        helpBtn.setIcon(imageHelp);
        
        saveBtn.setFont(new Font("SansSerif", 0, 11));
        cancelBtn.setFont(new Font("SansSerif", 0, 11));
        
        saveBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                //Update();
                action  = PassedValuePanel.OK;
                setVisible(false);
            }
        });
        
        cancelBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                action = CANCEL;
                setVisible(false);
            }
        });
        
        applyBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                Update();
            }
        });
        
        getContentPane().add(saveBtn);
        getContentPane().add(cancelBtn);
        getContentPane().add(helpBtn);
        //getContentPane().add(applyBtn);
    }
    
    /*************************************************************************/
    /****************       Upis podataka sa forme u bazu  *******************/
    /*************************************************************************/
    public void Update()
    {
        int i;
        
        SetSelectedIndex(0);
        
        try
        {
            Statement statement = conn.createStatement();
            //ResultSet rs = statement.executeQuery("select * from IISC_CALLING_STRUCT,IISC_FORM_TYPE where IISC_CALLING_STRUCT.Called_Tf_id=IISC_FORM_TYPE.Tf_id and Caller_Tf_id =" + Tf_id + " order by Tf_id");
            
            if (CS_id < 0)
            {
                int max = 0;
            
                try
                {
                    ResultSet rs = statement.executeQuery("select max(CS_id) from IISC_CALLING_STRUCT");
                    if(rs.next())
                    {
                        CS_id = rs.getInt(1);    
                    }
                    else
                    {
                        CS_id = 0;
                    }
                }
                catch(SQLException sqle)
                {
                    CS_id = 0;
                }
                
                CS_id = CS_id + 1;
                String query = "insert into IISC_CALLING_STRUCT(CS_id,Caller_Tf_id,Called_Tf_id,CMet_id,CMod_id,CS_call_UI_position,PR_id, BA_id) values(" + CS_id + "," + Tf_id + "," + calledTf;
                
                
                if(selOpenChb.isSelected())
                {
                    if(restectedChb.isSelected())
                    {
                        query = query + ",2";
                    }
                    else
                    {
                        query = query + ",0";
                    }
                }
                else
                {
                    if(restectedChb.isSelected())
                    {
                        query = query + ",1";
                    }
                    else
                    {
                        query = query + ",3";
                    }
                }
                
                if(modalBtn.isSelected())
                {
                    query = query + ",0";
                }
                else
                {
                    if(closeChb.isSelected())
                    {
                        query = query + ",2";
                    }
                    else
                    {
                        query = query + ",1";
                    }
                }
                
                if(miChb.isSelected())
                {
                    if(btnChb.isSelected())
                    {
                        query = query + ",2";
                    }
                    else
                    {
                        query = query + ",0";
                    }
                }
                else
                {
                    query = query + ",1";
                }
                
                query = query + "," + PR_id + "," + BA_id +")";
                
                //System.out.println(query);
                statement.execute(query);
            }
            else
            {
                String query = "update IISC_CALLING_STRUCT set ";                  
                
                if(selOpenChb.isSelected())
                {
                    if(restectedChb.isSelected())
                    {
                        query = query + "CMet_id=2,";
                    }
                    else
                    {
                        query = query + "CMet_id=0,";
                    }
                }
                else
                {
                    if(restectedChb.isSelected())
                    {
                        query = query + "CMet_id=1,";
                    }
                    else
                    {
                        query = query + "CMet_id=3,";
                    }
                }
                
                if(modalBtn.isSelected())
                {
                    query = query + "CMod_id=0,";
                }
                else
                {
                    if(closeChb.isSelected())
                    {
                        query = query + "CMod_id=2,";
                    }
                    else
                    {
                        query = query + "CMod_id=1,";
                    }
                }
                
                if(miChb.isSelected())
                {
                    if(btnChb.isSelected())
                    {
                        query = query + "CS_call_UI_position=2";
                    }
                    else
                    {
                        query = query + "CS_call_UI_position=0";
                    }
                }
                else
                {
                    query = query + "CS_call_UI_position=1";
                }
                
                query = query + " where CS_id=" + CS_id;
                
                //System.out.println(query);
                statement.execute(query);
                
                statement.execute("delete from IISC_PASSED_VALUE where CS_id=" + CS_id);
            }
            
            int Pv_max = 0;
            
            try
            {
                ResultSet rs = statement.executeQuery("select max(PV_id) from IISC_PASSED_VALUE");
                if(rs.next())
                {
                    Pv_max = rs.getInt(1);    
                }
                else
                {
                    Pv_max = 0;
                }
            }
            catch(SQLException sqle)
            {
                Pv_max = 0;
            }
            
            Pv_max = Pv_max + 1;
            
            int size, j;
            String query = "";
            
            Statement statement2 = conn.createStatement();
            
            size = lp.parIds.size();
            
            for(j = 0; j < size; j++)
            {
                if (((RadioValue)radioVector.get(j)).enabled)
                {
                    query = "insert into IISC_PASSED_VALUE(PV_id,CS_id,PV_To_Par_id,PV_type,PV_value,Att_id,Tob_id,Tf_id,PV_From_Par_id,Pr_id) values(" + Pv_max + "," + CS_id + "," + ((Parameter)lp.parIds.get(j)).Par_id + "," + ((RadioValue)radioVector.get(j)).type  + ",'" +  ((RadioValue)radioVector.get(j)).txtValue + "',";
                    
                    if ((((RadioValue)radioVector.get(j)).attCmbIndex > -1) && (attVec.size() > 0))
                    {
                        query = query + ((Attribute)attVec.get(((RadioValue)radioVector.get(j)).attCmbIndex)).Att_id + "," + ((Attribute)attVec.get(((RadioValue)radioVector.get(j)).attCmbIndex)).Tob_id + ",";
                    }
                    else
                    {
                        query = query + "-1,-1,";
                    }
                    
                    query = query  + Tf_id + ",";
                    
                    if ((((RadioValue)radioVector.get(j)).parCmbIndex > -1) && (parVec.size() > 0))
                    {
                        query = query  + ((Parameter)parVec.get(((RadioValue)radioVector.get(j)).parCmbIndex)).Par_id + ",";
                    }
                    else
                    {
                         query = query  + "-1,";
                    }
                    query = query  + PR_id + ")";
                    statement2.execute(query);
                    Pv_max = Pv_max + 1;
                }
            }
        }
        catch(SQLException sqle)
        {
            System.out.print(sqle.toString() + "\n");
        }
    }
    
    public void InitRadioPanel()
    {
        rp = new RadioPanel(100, 100);
        rp.setBounds(150, 25, 190, 170);
        parLbl.setBounds(170, 5, 190, 15);
        bindingPanel.add(parLbl);
        
        int i = 0;
        attVec = new Vector();
        parVec = new Vector();
        
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_PARAMETER where Tf_id=" + Tf_id + " order by Par_id");
            
            while(rs.next())
            {   
                parVec.add(new Parameter(rs.getInt("Par_id"), rs.getString("Par_mnem")));
                i = i + 1;
            }
            
            rs = statement.executeQuery("select IISC_ATTRIBUTE.Att_id,IISC_ATTRIBUTE.Att_mnem,IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id,IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_mnem  from IISC_ATT_TOB,IISC_ATTRIBUTE,IISC_COMPONENT_TYPE_OBJECT_TYPE where IISC_ATT_TOB.Tf_id=" + Tf_id + " and IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id and IISC_ATT_TOB.Tob_id=IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id");
            
            while(rs.next())
            {   
                attVec.add(new Attribute(rs.getInt("Att_id"), rs.getString("Att_mnem"), rs.getInt("Tob_id"), rs.getString("Tob_mnem")));
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
            rp.attCombo.addItem(((Attribute)attVec.get(i)).Att_mnem + "(" + ((Attribute)attVec.get(i)).Tob_mnem + ")");
        }
        
        size = parVec.size();
        
        for(i = 0; i < size; i++)
        {
            rp.parCombo.addItem(((Parameter)parVec.get(i)).Par_mnem);
        }
        
        bindingPanel.add(rp);
    }
    
    private void ChangeChecked(int newIndex, int type)
    {
        if (!checkEnabled)
        {
            return;
        }
        if (radioVector.size() < 0)
        {
            return;
        }
        
        if (newIndex < 0)
        {
            return;
        }
        
        if (type < 0)
        {
            RadioValue value = (RadioValue) radioVector.get(newIndex);
            RadioValue newValue = new RadioValue(value.type, value.txtValue,value.attCmbIndex, value.parCmbIndex, false);
            radioVector.setElementAt(newValue, newIndex);   
        }
        else
        {
            RadioValue value = (RadioValue) radioVector.get(newIndex);
            RadioValue newValue = new RadioValue(value.type, value.txtValue,value.attCmbIndex, value.parCmbIndex, true);
            radioVector.setElementAt(newValue, newIndex);
            
        }
    }
        
    public void SetSelectedIndex(int newIndex)
    {
        if (radioVector.size() <= 0)
        {
            return;
        }
        
        if (newIndex < 0)
        {
            return;
        }
        
        int type = 0;
        
        if(rp.constRBtn.isSelected())
        {
            type = 0;
        }
        else
        {
            if(rp.attRBtn.isSelected())
            {
                type = 1;
            }
            else
            {
                if(rp.parRBtn.isSelected())
                {
                    type = 2;
                }
            }  
        }
       
        RadioValue value;
        
        //Zapamti staru vrijednost 
        if (index >= 0)
        {
            RadioValue oldValue = (RadioValue)radioVector.get(index);
            value = new RadioValue(type, rp.constTxt.getText(), rp.attCombo.getSelectedIndex(),rp.parCombo.getSelectedIndex(), oldValue.enabled);
            radioVector.setElementAt(value, index);
        }
        
        //Setuj novi index
        index = newIndex;
        
        value = (RadioValue) radioVector.get(index);
        rp.constTxt.setText(value.txtValue);
        
        try
        {
            rp.attCombo.setSelectedIndex(value.attCmbIndex);
            rp.parCombo.setSelectedIndex(value.parCmbIndex);
        }
        catch(Exception e)
        {
        }
        
        type = value.type;
        
        if(type == 0)
        {
            rp.constTxt.setEnabled(true);
            rp.attCombo.setEnabled(false);
            rp.parCombo.setEnabled(false);
            
            rp.constRBtn.setEnabled(true);   
            rp.attRBtn.setEnabled(true);
            rp.parRBtn.setEnabled(true);
            
            rp.constRBtn.setSelected(true);
            rp.attRBtn.setSelected(false);
            rp.parRBtn.setSelected(false);
        }
        else
        {
            if(type == 1)
            {
                rp.constTxt.setEnabled(false);
                rp.attCombo.setEnabled(true);
                rp.parCombo.setEnabled(false);
                
                rp.constRBtn.setEnabled(true);   
                rp.attRBtn.setEnabled(true);
                rp.parRBtn.setEnabled(true);
                
                rp.constRBtn.setSelected(false);
                rp.attRBtn.setSelected(true);
                rp.parRBtn.setSelected(false);
                
            }
            else
            {
                if(type == 2)
                {
                    rp.constTxt.setEnabled(false);
                    rp.attCombo.setEnabled(false);
                    rp.parCombo.setEnabled(true);
                    
                    rp.constRBtn.setEnabled(true);   
                    rp.attRBtn.setEnabled(true);
                    rp.parRBtn.setEnabled(true);
                    
                    rp.constRBtn.setSelected(false);
                    rp.attRBtn.setSelected(false);
                    rp.parRBtn.setSelected(true);
                }
            }  
        }
        
        if (!value.enabled)
        {
            rp.constTxt.setEnabled(false);
            rp.attCombo.setEnabled(false);
            rp.parCombo.setEnabled(false);
            rp.constRBtn.setEnabled(false);
            rp.attRBtn.setEnabled(false);
            rp.parRBtn.setEnabled(false);
        }
        try
        {
            rp.constTxt.setText(value.txtValue);
            rp.attCombo.setSelectedIndex(value.attCmbIndex);
            rp.parCombo.setSelectedIndex(value.parCmbIndex);
        }
        catch(Exception e)
        {}
        
        
    }
    
    public void InitRadioValue()
    {
        radioVector = new Vector();
        int size = lp.parIds.size();
        int i = 0;
        checkEnabled = false;
        
        if (CS_id == -1)
        {
            if(size == 0)
            {
               RadioValue value = new RadioValue(0,"", 0, 0, false);
               radioVector.add(value); 
            }
            else
            {
                for(i = 0; i < size; i++)
                {
                    RadioValue value = new RadioValue(0,"", 0, 0, false);
                    radioVector.add(value);
                }
            }
        }
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_PASSED_VALUE where CS_id=" + CS_id + " order by PV_To_Par_id");
            
            int k = 0;
            Parameter par;
            int type = -1;
            int Par_id;
            int attSelectedIndex;
            int parSelectedIndex;
            String value;
            int Att_id;
            int Tob_id;
            int From_Par_id;
            
            
            while(rs.next())
            {   
                
                type = rs.getInt("PV_type");
                Par_id = rs.getInt("PV_To_Par_id");
                
                while(k < size)
                {
                    par = (Parameter)lp.parIds.get(k);
                    
                    if (par.Par_id < Par_id)
                    {
                        RadioValue rvalue = new RadioValue(0 ,"", 0, 0, false);
                        radioVector.add(rvalue);
                        k = k + 1;
                    }
                    else
                    {
                      if (par.Par_id > Par_id)
                      {
                          break;
                      }
                      
                      ((ParameterPanel)lp.pPanels.get(k)).lChb.setSelected(true);
                      
                      attSelectedIndex = 0;
                      parSelectedIndex = 0;
                      
                      value = rs.getString("PV_value"); 
                      Att_id = rs.getInt("Att_id");
                      Tob_id = rs.getInt("Tob_id");
                      From_Par_id = rs.getInt("PV_From_Par_id");
                      int attSize = attVec.size();
                      int parSize = parVec.size();
                      Attribute att;
                      Parameter para; 
                      
                      for(i = 0; i < attSize; i++)
                      {
                          att = (Attribute)attVec.get(i);
                          
                          if ((Att_id == att.Att_id) && (Tob_id == att.Tob_id))
                          {
                              break;
                          }
                      }
                      
                      if (i < attSize)
                      {
                          attSelectedIndex = i;
                      }
                      
                      for(i = 0; i < parSize; i++)
                      {
                          para = (Parameter)parVec.get(i);
                          
                          if (From_Par_id == para.Par_id) 
                          {
                              break;
                          }
                      }
                      
                      if (i < parSize)
                      {
                          parSelectedIndex = i;
                      }
                      
                      RadioValue rvalue = new RadioValue(type, value, attSelectedIndex, parSelectedIndex, true);
                      radioVector.add(rvalue);
                      k = k + 1;
                    }
                    
                }
                i = i + 1;
            }
            rs.close();
            
            while(k < size)
            {
                RadioValue rvalue = new RadioValue(0 ,"", 0, 0, false);
                radioVector.add(rvalue);
                k = k + 1;
            }
            
            checkEnabled = true;    
            
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
            checkEnabled = true;
        }
    }
    public void Select()
    {
        
    }
    public ListPanel getListPanel()
    {
        return lp;
    }
    
    /*****************************************************************************/
    /**********************       Lista sa check boxovima         ****************/
    /*****************************************************************************/
    private class ListPanel extends JPanel
    {
        private int index;
        private JPanel panel;
        private PassedValuePanel pPanel;
        private ParameterPanel pp;
        private Vector parIds;
        private Vector pPanels;
        private int calledTf;
        private Connection conn;
        JScrollBar sb;
        JScrollBar sb2;
        JScrollPane sp;
        
        public ListPanel(int _width, int _height, PassedValuePanel _pPanel, int _calledTf, Connection _conn)
        {
            super();   
            setLayout(null);
            height = _height;
            width = _width;
            pPanel = _pPanel;
            conn = _conn;
            
            calledTf = _calledTf;
            
            setBorder(new LineBorder(new Color(124, 124, 124), 1));
            setBackground(SystemColor.textInactiveText);
            setBounds(32, 25, 140, 170);
            
            panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(SystemColor.textInactiveText);
            InitPanels();
            panel.repaint();
            
            sp = new JScrollPane(panel);
            sb = new JScrollBar(JScrollBar.VERTICAL);
            sb2 = new JScrollBar(JScrollBar.HORIZONTAL);
            sp.setBorder(new LineBorder(new Color(124, 124, 124), 1));
            sp.setBackground(Color.white);
            sp.setBounds(0, 0, 140, 170);
            
            sp.getViewport().add(panel, BorderLayout.CENTER);
            
            sp.setVerticalScrollBar(sb);
            sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            sp.setHorizontalScrollBar(sb2);
            sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            add(sp);
            sp.repaint();
            index = 0;
            
            repaint();
        }
             
        private void ChangeSelectedIndex(int newIndex)
        {
            
            int size = pPanels.size();
            
            if  (newIndex > size)
            {
                return;
            }
            
            pPanel.SetSelectedIndex(newIndex);
            ParameterPanel pp = (ParameterPanel) pPanels.get(index);
            pp.UnSelect();
            
            index = newIndex;
        }
        
        private void ChangeChecked(int newIndex, int type)
        {
            pPanel.ChangeChecked(newIndex, type);
        }
        
        private void InitPanels()
        {
            int i = 0;
            parIds = new Vector();
            pPanels = new Vector();
            
            try
            {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select * from IISC_PARAMETER where Tf_id=" + calledTf + " order by Par_id");
                
                while(rs.next())
                {   
                    parIds.add(new Parameter(rs.getInt("Par_id"), rs.getString("Par_mnem")));
                    i = i + 1;
                }
                rs.close();
                
            }
            catch(SQLException sqle)
            {
                //System.out.println("Sql exception :" + sqle);
            }
            
            int size = i;
            panel.setPreferredSize(new Dimension(139, size * 19));
            //panel.setAutoscrolls(true);
            
            for(i = 0; i < size; i++)
            {
                ParameterPanel pp =  new ParameterPanel(138, 18, i, this, ((Parameter)parIds.get(i)).Par_mnem);
                pp.setBounds(0, i * 19, 138, 18);
                pPanels.add(pp);
            }
            
            for(i = 0; i < size; i++)
            {
                panel.add(((ParameterPanel)pPanels.get(i)));
            }
        }
        
        public void RepaintAll()
        {
            int size = pPanels.size();
            
            repaint();
            panel.repaint();
            sp.repaint();
            
            for(int i = 0; i < size; i++)
            {
                ((ParameterPanel)pPanels.get(i)).RepaintAll();
            }
            
        }
    }
    
    private class ParameterPanel extends JTextField implements MouseListener
    {
        private int index;
        private ListPanel pPanel;
        private String text;
        private JLabel txtLbl;
        private ListCheckBox lChb;
        private boolean selected;
    
        public ParameterPanel(int _width, int _height, int _index, ListPanel _pPanel, String _text)
        {
            super();  
            setLayout(null);
            height = _height;
            width = _width;
            index = _index;
            pPanel = _pPanel;
            text = _text;
            setEditable(false);
            setText("        " + text);
            setSize(width, height);
            //setBounds(1, 1, width, height);
            setBackground(Color.white);
            
            setFocusable(true);
            //setBorder(BorderFactory.createEmptyBorder());
            setBorder((Border) new EmptyBorder(new Insets(2, 1 , 2, 1)));
            addMouseListener(this);
            
            lChb = new ListCheckBox(index, this);
            lChb.setSize(13, 13);
            lChb.setBounds(3, 2, 17, 13);
            add(lChb);
            
            txtLbl = new JLabel(text);
            txtLbl.setBounds(20, 2, 115, 13);
            //add(txtLbl);
            
            lChb.setFocusable(false);
            setFocusable(false);
            repaint();
            
        }
        
        private void Select()
        {
            
            pPanel.ChangeSelectedIndex(index);
            selected = true;
            setBackground(SystemColor.textHighlight); 
            setForeground(Color.white);
            lChb.setBackground(SystemColor.textHighlight);
            setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            lChb.setBounds(3, 3, 17, 13);
            txtLbl.setBounds(20, 3, 115, 13);
            lChb.repaint();
            repaint();
        }
        
        private void UnSelect()
        {
            selected = false;
            setBackground(Color.WHITE); 
            setForeground(Color.black);
            lChb.setBackground(Color.white);
            setBorder((Border) new EmptyBorder(new Insets(2, 1 , 2, 1)));
            
            lChb.setBounds(3, 2, 17, 13);
            lChb.repaint();
            repaint();
        }
        
        public void RepaintAll()
        {
            lChb.repaint();
            repaint();
        }
        
        
        public void mouseEntered(MouseEvent e)
        {
           
        }
        
        public void mouseClicked(MouseEvent e)
        {
        }
        
        public void mouseExited(MouseEvent e)
        {
            
        }
        
        public void mousePressed(MouseEvent e)
        {
            requestFocus();
            Select();
        }
        
        public void mouseReleased(MouseEvent e)
        {
        }
    }
    
    private class ListCheckBox extends JCheckBox implements ActionListener, ItemListener
    {
        int index;
        ParameterPanel parent;
        
        public ListCheckBox(int _index, ParameterPanel _parent)
        {
            super();    
            index = _index;
            parent = _parent;
            addActionListener(this);
            addItemListener(this);
            setBackground(Color.white);
        }
        
        public void actionPerformed(ActionEvent e) 
        {
            parent.Select();
            
        }
        
        public void itemStateChanged(ItemEvent e)
        {
            if(isSelected())
            {
                parent.pPanel.ChangeChecked(index, 0);
            }
            else
            {
                parent.pPanel.ChangeChecked(index, -1);
            }
        }
    }
    
    private class RadioPanel extends JPanel
    {
        
        private int width;
        private int height;
        private JRadioButton constRBtn;
        private JRadioButton attRBtn;
        private JRadioButton parRBtn;
        private JLabel constLbl;
        private JLabel attLbl;
        private JLabel parLbl; 
        private JComboBox attCombo;
        private JComboBox parCombo;
        private JTextField constTxt;
        
        
        public RadioPanel(int _width, int _height)
        {
            super();   
            setLayout(null);
            height = _height;
            width = _width;
            setBorder(new LineBorder(new Color(124, 124, 124), 1));
            
            constLbl = new JLabel();
            constLbl.setText("Value");
            constLbl.setBounds(50, 12, 130, 10);
            add(constLbl);
            
            constRBtn = new JRadioButton();
            constRBtn.setBounds(24, 27, 20, 20);
            add(constRBtn);
            
            constTxt = new JTextField("");
            constTxt.setBounds(50, 27, 130, 20);
            add(constTxt);
                        
            attLbl = new JLabel();
            attLbl.setText("Attribute");
            attLbl.setBounds(50, 64, 130, 10);
            add(attLbl);
            
            attRBtn = new JRadioButton();
            attRBtn.setBounds(24, 79, 20, 20);
            add(attRBtn);
            
            attCombo = new JComboBox();
            attCombo.setBounds(50, 79, 130, 20);
            add(attCombo);
            
            parLbl = new JLabel();
            parLbl.setText("Parameter");
            parLbl.setBounds(50, 116, 100, 10);
            add(parLbl);
            
            parRBtn = new JRadioButton();
            parRBtn.setBounds(24, 131, 20, 20);
            add(parRBtn);
            
            parCombo = new JComboBox();
            parCombo.setBounds(50, 131, 130, 20);
            add(parCombo);
            
            constRBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                  constRBtn_actionPerformed(e);
                }
            });
            
            attRBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                  attRBtn_actionPerformed(e);
                }
            });
  
            parRBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                  parRBtn_actionPerformed(e);
                }
            });
            
            constTxt.setEnabled(false);
            attCombo.setEnabled(false);
            parCombo.setEnabled(false);
            constRBtn.setEnabled(false);
            attRBtn.setEnabled(false);
            parRBtn.setEnabled(false);
            constRBtn.setSelected(true);
            repaint();
            
        }
        
        
        private void constRBtn_actionPerformed(ActionEvent e)
        {
            constRBtn.setSelected(true);
            
            if( constRBtn.isSelected() )
            {
                rp.constTxt.setEnabled(true);
                rp.attCombo.setEnabled(false);
                rp.parCombo.setEnabled(false);
                //rp.constRBtn.setSelected(false);
                rp.attRBtn.setSelected(false);
                rp.parRBtn.setSelected(false);              
            }
        }
        
        private void attRBtn_actionPerformed(ActionEvent e)
        {
            if ( attCombo.getItemCount() == 0 ) 
            {
                attRBtn.setSelected(false);
                return;
            }
            
            attRBtn.setSelected(true);
            
            if(attRBtn.isSelected())
            {
                rp.constTxt.setEnabled(false);
                rp.attCombo.setEnabled(true);
                rp.parCombo.setEnabled(false);
                rp.constRBtn.setSelected(false);
                //rp.attRBtn.setSelected(false);
                rp.parRBtn.setSelected(false);              
            }
        }
        
        private void parRBtn_actionPerformed(ActionEvent e)
        {
            if ( parCombo.getItemCount() == 0 ) 
            {
                parRBtn.setSelected(false);
                return;
            }
            
            parRBtn.setSelected(true);
            
            if(parRBtn.isSelected())
            {
                rp.constTxt.setEnabled(false);
                rp.attCombo.setEnabled(false);
                rp.parCombo.setEnabled(true);
                rp.constRBtn.setSelected(false);
                rp.attRBtn.setSelected(false);
                //rp.parRBtn.setSelected(false);              
            }
        }
    
    }
    
    private class RadioValue
    {
        public int type;
        public String txtValue;
        public int attCmbIndex;
        public int parCmbIndex;
        public boolean enabled;
        
        public RadioValue(int _type, String _txtValue, int _attCmbIndex, int _parCmbIndex, boolean _enabled)
        {
            type = _type;
            txtValue = _txtValue;
            attCmbIndex = _attCmbIndex;
            parCmbIndex = _parCmbIndex;
            enabled = _enabled;
        }
    }
        
    private class Parameter
    {
        public int Par_id;
        public String Par_mnem;
        
        private Parameter(int _Par_id, String _Par_mnem)
        {
            Par_id = _Par_id;
            Par_mnem = _Par_mnem;
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
    