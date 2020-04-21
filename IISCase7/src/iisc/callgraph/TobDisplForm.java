package iisc.callgraph;

import iisc.IISFrameMain;
import iisc.PropertyTextBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

public class TobDisplForm extends JPanel 
{
    int width;
    int Tob_id;
    int Tf_id;
    int PR_id;
    Connection conn;
    private IISFrameMain parent;
    
    
    private JLabel nLaLbl = new JLabel("Window layout");
    
    private JLabel nDlLbl = new JLabel("Data layout");
    private JRadioButton fieldBtn = new JRadioButton();
    private JRadioButton tableBtn = new JRadioButton();
    private JLabel fieldLbl = new JLabel("Field layout");
    private JLabel tableLbl = new JLabel("Table layout");
    
    private JLabel nOrLbl = new JLabel("Relative order");
    
    private JLabel nPoLbl = new JLabel("Layout relative position");
    private JRadioButton bottomBtn = new JRadioButton();
    private JRadioButton rightBtn = new JRadioButton();
    private JLabel bottomLbl = new JLabel("Bottom to parent");
    private JLabel rightLbl = new JLabel("Right to parent");
    private JLabel searchLbl = new JLabel("Search funcionality");
    private JLabel delMassLbl = new JLabel("Massive delete funcionality");
    private JLabel rlirLbl = new JLabel("Retain last inserted record");
    
    private JCheckBox searchChb = new JCheckBox();
    private JCheckBox delMassChb = new JCheckBox();
    public JCheckBox rlirChb = new JCheckBox();
    
    private JTextField vOrTxt = new PropertyTextBox("");
    private JTextField vXrTxt = new PropertyTextBox("");
    private JTextField vYrTxt = new PropertyTextBox("");
    
    private JComboBox layCombo;
    
    Integer[] useitems;
    
    private static ImageIcon imageUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
    private static ImageIcon imageDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
    
    private ObjectType ob;
    private int layout = 0;
    private int maxNewOrder = 0;
    private int oldOrder;
    private int oldLayout;
    private String oldValue;
  
    private ImagePanel downIP;
    private ImagePanel upIP;
    
    private JPanel XYPn = new JPanel(null);
    private JPanel borderPn = new JPanel(null);
    private JRadioButton leftBtn = new JRadioButton();
    private JRadioButton centerBtn = new JRadioButton();
    private JRadioButton customBtn = new JRadioButton();
    
    private JLabel leftLbl = new JLabel("Left on top");
    private JLabel centerLbl = new JLabel("Center");
    private JLabel customLbl = new JLabel("Custom");
    private JLabel XLbl = new JLabel("X relative position");
    private JLabel YLbl = new JLabel("Y relative position");
    private int firstOrder;
    private int firstTobSuperord;
    private int tobSuperord;
    private boolean first = true;
    private JButton btnUp = new JButton();
    private ImageIcon iconUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
    private ImageIcon iconDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
    private JButton btnDown = new JButton(); 
    
    public TobDisplForm(IISFrameMain _parent, ObjectType _ob, int _width, int height, int _Tob_id, int _Tf_id, int _PR_id, Connection _conn)
    {
        super(null);
        this.setSize(width, height);
        
        parent = _parent;
        width = _width;
        Tob_id = _Tob_id;
        Tf_id = _Tf_id;
        PR_id = _PR_id;
        conn = _conn;
        ob = _ob;
        
        nLaLbl.setBounds(10, 0, 100, 17);
        nLaLbl.setFont(new Font("SansSerif", 0, 11));
        add(nLaLbl);
        
        String[] items = {"New window", "Same window"};
        layCombo = new JComboBox(items);
        layCombo.setBounds(140, 0, 210, 20);
        add(layCombo);
        
        //Data layout radio group
        nDlLbl.setBounds(10, 27, 100, 17);
        nDlLbl.setFont(new Font("SansSerif", 0, 11));
        add(nDlLbl);
        
        fieldBtn.setBounds(135, 30, 20, 17);
        add(fieldBtn);
        
        fieldLbl.setBounds(160, 30, 100, 17);
        fieldLbl.setFont(new Font("SansSerif", 0, 11));
        add(fieldLbl);
        
        tableBtn.setBounds(250, 30, 20, 17);
        add(tableBtn);
        
        tableLbl.setBounds(275, 30, 100, 17);
        tableLbl.setFont(new Font("SansSerif", 0, 11));
        add(tableLbl);
        
        InitImagePanels();
        //O kako je lijepo biti konj kao ja 
        nOrLbl.setBounds(10, 55, 150, 17);
        nOrLbl.setFont(new Font("SansSerif", 0, 11));
        add(nOrLbl);
        
        vOrTxt.setBounds(140, 55, 80, 20);
        vOrTxt.setFont(new Font("SansSerif", 0, 11));
        vOrTxt.setEditable(false);
        vOrTxt.setBackground(Color.WHITE);
        add(vOrTxt);
        
        btnUp.setBounds(230, 55, 20, 20);
        btnUp.setIcon(imageUp);
        btnDown.setBounds(255,55,20,20);
        btnDown.setIcon(imageDown);
        
        add(btnUp);
        add(btnDown);
        
        //Layout relative position 
        nPoLbl.setBounds(10, 82, 120, 17);
        nPoLbl.setFont(new Font("SansSerif", 0, 11));
        add(nPoLbl);
        
        bottomBtn.setBounds(135, 82, 20, 17);
        add(bottomBtn);
        
        bottomLbl.setBounds(160, 82, 100, 17);
        bottomLbl.setFont(new Font("SansSerif", 0, 11));
        add(bottomLbl);
        
        rightBtn.setBounds(250, 82, 20, 17);
        add(rightBtn);
        
        rightLbl.setBounds(275, 82, 100, 17);
        rightLbl.setFont(new Font("SansSerif", 0, 11));
        add(rightLbl);
        //InitTextFields();
        //InitComboBoxes();
        
        //Panel za window relative position 
        borderPn = new JPanel();
        borderPn.setLayout(null);
        borderPn.setBorder(new LineBorder(new Color(124, 124, 124), 1));
        borderPn.setBounds(10, 106, 340, 75);
        
        centerBtn.setBounds(15, 17, 20, 20);
        //centerBtn.setSelected(true);
        centerLbl.setBounds(40, 17, 150, 17);
        
        leftBtn.setBounds(15, 35, 20, 20);
        leftLbl.setBounds(40, 35, 150, 17);
        
        customBtn.setBounds(15, 54, 20, 17);
        customLbl.setBounds(40, 54, 150, 17);
        
        borderPn.add(leftBtn);
        borderPn.add(leftLbl);
        borderPn.add(centerBtn);
        borderPn.add(centerLbl);
        borderPn.add(customBtn);
        borderPn.add(customLbl);
        XYPn.setBorder(new LineBorder(new Color(124, 124, 124), 1));
        XYPn.setBounds(140, 0, 200, 75);
        borderPn.add(XYPn);
        
        JLabel editLb = new JLabel("Window relative position");
        editLb.setBounds(5,0,144, 17);
        editLb.setFont(new Font("SansSerif", 0, 11));
        borderPn.add(editLb);
        
        XLbl.setBounds(5,10,125, 20);
        XLbl.setFont(new Font("SansSerif", 0, 11));
        XYPn.add(XLbl);
        
        vXrTxt.setBounds(130,10,60, 20);
        vXrTxt.setFont(new Font("SansSerif", 0, 11));
        XYPn.add(vXrTxt);
        
        YLbl.setBounds(5,40,125, 20);
        YLbl.setFont(new Font("SansSerif", 0, 11));
        XYPn.add(YLbl);
        
        vYrTxt.setBounds(130,40,60, 20);
        vYrTxt.setFont(new Font("SansSerif", 0, 11));
        XYPn.add(vYrTxt);
        add(borderPn);
        
        //delete and search
        searchLbl.setBounds(60, 185, 100, 17);
        add(searchLbl);
        searchChb.setBounds(210, 185, 17, 17);
        add(searchChb);
        
        delMassLbl.setBounds(60, 210, 150, 17);
        add(delMassLbl);
        delMassChb.setBounds(210, 210, 17, 17);
        add(delMassChb);
        
        rlirLbl.setBounds(60, 235, 150, 17);
        add(rlirLbl);
        rlirChb.setBounds(210, 235, 17, 17);
        add(rlirChb);
        
        InitOrder();
        InitFromDataBase();
        AddActionListners();
        InitTextFieldsListeners();
        revalidate();
    }
    
    private void InitImagePanels()
    {
          btnUp.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
                int newOrder = Integer.parseInt(vOrTxt.getText());
                
                if ((newOrder == maxNewOrder) || (maxNewOrder == 0))
                {
                    return;
                }
                newOrder = newOrder + 1;
                
                vOrTxt.setText(Integer.toString(newOrder));
                
                EnableButton();
            }
          });
          
        btnDown.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
                int newOrder = Integer.parseInt(vOrTxt.getText());
                
                if (newOrder == 1)
                {
                    return;
                }
                newOrder = newOrder - 1;
                
                vOrTxt.setText(Integer.toString(newOrder));
                EnableButton();
            }
          });
        
    }
    
    private void AddActionListners()
    {
        layCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              layoutComboAction();
            }
        });
        
        fieldBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
              fieldBtn.setSelected(true);
              tableBtn.setSelected(false);
          }
        });
        
        tableBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
              tableBtn.setSelected(true);
              fieldBtn.setSelected(false);
          }
        });
        
        bottomBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
              bottomBtn.setSelected(true);
              rightBtn.setSelected(false);
          }
        });
        
        rightBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
              rightBtn.setSelected(true);
              bottomBtn.setSelected(false);
          }
        });
        
        centerBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
              centerBtn.setSelected(true);
              leftBtn.setSelected(false);
              customBtn.setSelected(false);
              vXrTxt.setEnabled(false);
              vYrTxt.setEnabled(false);
            }
        });
        
        leftBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
              leftBtn.setSelected(true);
              centerBtn.setSelected(false);
              customBtn.setSelected(false);
              vXrTxt.setEnabled(false);
              vYrTxt.setEnabled(false);          
            }
        });
        
        customBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
              customBtn.setSelected(true);
              centerBtn.setSelected(false);
              leftBtn.setSelected(false);
              vXrTxt.setEnabled(true);
              vYrTxt.setEnabled(true);
          }
        });
        
        delMassChb.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
          }
        });
        
        searchChb.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
          }
        });
        
        rlirChb.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
          }
        });
        
    }
    ///Ako se promijeni i nadredjeni tip komponente onda se mora promijeniti i 
    //relativni poredak
    public void ChangeSuperOrd(String newSuperOrd)
    {
        if(first)
        {
            first = false;
            return;
        }
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs;
           
            rs = statement.executeQuery("select Tob_id from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tob_mnem='" + newSuperOrd + "' and Tf_id=" + Tf_id);
            
            if (rs.next())
            {
                tobSuperord = rs.getInt(1);
            }
            else
            {
                tobSuperord = 0;
            }            
            
            //Ako nije root tip komponente onda nadji maksimalni order njegovih rodjaka
             rs = statement.executeQuery("select max(Tob_order) from IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_COMPTYPE_DISPLAY where IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id=IISC_COMPTYPE_DISPLAY.Tob_id and Tob_superord=" + tobSuperord);
             
             if (rs.next())
             {
                 maxNewOrder = rs.getInt(1);
             }
             else
             {
                maxNewOrder = 0;
             }
             
            maxNewOrder = maxNewOrder + 1;
            
            vOrTxt.setText("" + maxNewOrder);
            
        }
        catch(Exception e)
        {
            maxNewOrder  = 0;
        }
    }
    
    private void InitOrder()
    {
    
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs;
           
            rs = statement.executeQuery("select Tob_superord from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tob_id=" + Tob_id);
            
            if (rs.next())
            {
                firstTobSuperord = rs.getInt(1);
                tobSuperord = firstTobSuperord;
            }
            else
            {
                firstTobSuperord = 0;
            }
            
            //Ako se radi o root tipu komponente onda ne smije da se mijenja poredak
            if (firstTobSuperord == 0)
            {
                maxNewOrder = 0;
                return;
            }
            
            //Ako nije root tip komponente onda nadji maksimalni order njegovih rodjaka
             rs = statement.executeQuery("select max(Tob_order) from IISC_COMPONENT_TYPE_OBJECT_TYPE,IISC_COMPTYPE_DISPLAY where IISC_COMPONENT_TYPE_OBJECT_TYPE.Tob_id=IISC_COMPTYPE_DISPLAY.Tob_id and Tob_superord=" + firstTobSuperord);
             
             if (rs.next())
             {
                 maxNewOrder = rs.getInt(1);
             }
             else
             {
                maxNewOrder = 0;
             }
            
        }
        catch(Exception e)
        {
            maxNewOrder  = 0;
        }
    }
    
    private void InitFromDataBase()
    {
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs;
           
            rs = statement.executeQuery("select * from IISC_COMPTYPE_DISPLAY where Tob_id=" + Tob_id );
            
            if (rs.next())
            {
                layCombo.setSelectedIndex(rs.getInt("Tob_layout"));
                layout = layCombo.getSelectedIndex();
                oldLayout = layCombo.getSelectedIndex();
                
                if (layout == 0)
                {
                    bottomBtn.setEnabled(false);
                    rightBtn.setEnabled(false);
                }
                else
                {
                    leftBtn.setEnabled(false);
                    centerBtn.setEnabled(false);
                    customBtn.setEnabled(false);
                }
                
                if (rs.getInt("Tob_data_layout") == 0)
                {
                    fieldBtn.setSelected(true);
                }
                else
                {
                    tableBtn.setSelected(true);
                }
                
                oldOrder = rs.getInt("Tob_order");
                firstOrder = oldOrder;
                
                vOrTxt.setText("" + oldOrder);
                
                
                if (rs.getInt("Tob_position") == 0)
                {
                    bottomBtn.setSelected(true);
                }
                else
                {
                    rightBtn.setSelected(true);
                }
                
                int winRelPos = rs.getInt("Tob_position_relative");
                
                if (winRelPos == 1)
                {
                    leftBtn.setSelected(true);
                }
                else
                {
                    if (winRelPos == 0)
                    {
                        centerBtn.setSelected(true);
                    }
                    else
                    {
                        customBtn.setSelected(true);
                    }
                }

                if ((winRelPos < 2) || (layout == 1))
                {
                    vXrTxt.setEnabled(false);
                    vYrTxt.setEnabled(false);
                }
                else
                {
                        vXrTxt.setEnabled(true);
                        vYrTxt.setEnabled(true);
                }

                vXrTxt.setText("" + rs.getInt("Tob_x_relative"));


                vYrTxt.setText("" + rs.getInt("Tob_y_relative"));

                if (rs.getInt("Tob_search") == 1)
                {
                    searchChb.setSelected(true);
                }
                if (rs.getInt("Tob_del_masive") == 1)
                {
                    delMassChb.setSelected(true);
                }
                
                if (rs.getInt("Tob_retain_last_ins_record") == 0)
                {
                    rlirChb.setSelected(false);
                }
                else
                {
                    rlirChb.setSelected(true);
                }
            }
            else
            {
                InitEmpty();
            }
        }
        catch(Exception e)
        {
            InitEmpty();
            return;
        }
    }
    
    private void InitEmpty()
    {
    
        layCombo.setSelectedIndex(0);
        bottomBtn.setEnabled(false);
        rightBtn.setEnabled(false);
        
        bottomBtn.setSelected(true);        
        fieldBtn.setSelected(true);
        
        centerBtn.setSelected(true);
        vXrTxt.setText("0");
        vXrTxt.setEnabled(false);
        vYrTxt.setText("0");
        vYrTxt.setEnabled(false);
        maxNewOrder = maxNewOrder + 1;
        vOrTxt.setText("" + maxNewOrder);
        rlirChb.setSelected(false);
    }
    
    public void EnableButton()
    {
        ob.btnApply.setEnabled(true);
        ob.btnSave.setEnabled(true);
    }
    
    private void InitTextFieldsListeners()
    {
        vXrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vXrTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                vXrTxt_focusLost(e);
            }
          
        });
        
        
        vYrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vYrTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                vYrTxt_focusLost(e);
            }
          
        });
        
        
    }
    
    public void vXrTxt_focusGained(FocusEvent e)
    {
          
          oldValue = vXrTxt.getText();
    }
    
    public void vXrTxt_focusLost(FocusEvent e)
    {
        int value;
        
        try
        {
            value = Integer.parseInt(vXrTxt.getText());
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this,"Enter integer value beetwen 0 i 99","Error", JOptionPane.ERROR_MESSAGE);
            vXrTxt.setText(oldValue);
            return;
        }
        
        if ((value < 0) || (value > 99))
        {
            JOptionPane.showMessageDialog(this,"Enter integer value beetwen 0 i 99","Error", JOptionPane.ERROR_MESSAGE);
            vXrTxt.setText(oldValue);
            return;
        }
    }
    
    public void vYrTxt_focusGained(FocusEvent e)
    {
        oldValue = vYrTxt.getText();
    }
    
    public void vYrTxt_focusLost(FocusEvent e)
    {
        
        int value;
        
        try
        {
            value = Integer.parseInt(vYrTxt.getText());
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this,"Enter integer value beetwen 0 i 99","Error", JOptionPane.ERROR_MESSAGE);
            vYrTxt.setText(oldValue);
            return;
        }
        
        if ((value < 0) || (value > 99))
        {
            JOptionPane.showMessageDialog(this,"Enter integer value beetwen 0 i 99","Error", JOptionPane.ERROR_MESSAGE);
            vYrTxt.setText(oldValue);
            return;
        }
    }
    
    public void Update()
    {
        
        try
        {
            boolean inserted = false;
            Statement statement = conn.createStatement();
            Statement statement3 = conn.createStatement();
            
            ResultSet rs1 = statement3.executeQuery("select Tob_id from IISC_COMPTYPE_DISPLAY where Tob_id=" + Tob_id);
            
            if (rs1.next())
            {
                String q = "update IISC_COMPTYPE_DISPLAY set Tob_layout=" + layCombo.getSelectedIndex();
                
                if (fieldBtn.isSelected())
                {
                    q = q + ",Tob_data_layout=0";
                }
                else
                {
                    q = q + ",Tob_data_layout=1";
                }
                
                if (bottomBtn.isSelected())
                {
                    q = q + ",Tob_position=0";
                }
                else
                {
                    q = q + ",Tob_position=1";
                }
                
                if(centerBtn.isSelected())
                {
                    q = q + ",Tob_position_relative=0";
                }
                else
                {
                    if (leftBtn.isSelected())
                    {
                        q = q + ",Tob_position_relative=1";
                    }
                    else
                    {
                        q = q + ",Tob_position_relative=2";
                    }
                }
                
                q = q + ",Tob_x_relative=" + vXrTxt.getText() + ",Tob_y_relative=" + vYrTxt.getText() + ",Tob_order=" + vOrTxt.getText() ;
                
                if (searchChb.isSelected())
                {
                    q = q + ",Tob_search=1";
                }
                else
                {
                    q = q + ",Tob_search=0";
                }
                
                if (delMassChb.isSelected())
                {
                    q = q +  ",Tob_del_masive=1";
                }
                else
                {
                    q = q +  ",Tob_del_masive=0";
                }
                
                if (rlirChb.isSelected())
                {
                    q = q +  ",Tob_retain_last_ins_record=1";
                }
                else
                {
                    q = q +  ",Tob_retain_last_ins_record=0";
                }
                
                q = q + " where Tob_id=" + Tob_id;
                //System.out.println(q);
                statement.execute(q);
            }
            else
            {
                
                inserted = true;
                String q = "insert into IISC_COMPTYPE_DISPLAY(Tob_id,Tf_id,PR_id,Tob_layout,Tob_order, Tob_data_layout,Tob_position,Tob_position_relative,Tob_x_relative,Tob_y_relative,Tob_search,Tob_del_masive,Tob_retain_last_ins_record) values(" + Tob_id + "," + Tf_id + "," + PR_id + "," + layCombo.getSelectedIndex() + ","  + vOrTxt.getText();
                if (fieldBtn.isSelected())
                {
                    q = q + ",0";
                }
                else
                {
                    q = q + ",1";
                }
                
                if (bottomBtn.isSelected())
                {
                    q = q + ",0";
                }
                else
                {
                    q = q + ",1";
                }
                
                if(centerBtn.isSelected())
                {
                    q = q + ",0";
                }
                else
                {
                    if (leftBtn.isSelected())
                    {
                        q = q + ",1";
                    }
                    else
                    {
                        q = q + ",2";
                    }
                }
                
                q = q + "," + vXrTxt.getText() + "," + vYrTxt.getText() ;
                
                if (searchChb.isSelected())
                {
                    q = q + ",1";
                }
                else
                {
                    q = q + ",0";
                }
                
                if (delMassChb.isSelected())
                {
                    q = q +  ",1";
                }
                else
                {
                    q = q +  ",0";
                }
                
                if (rlirChb.isSelected())
                {
                    q = q +  ",1)";
                }
                else
                {
                    q = q +  ",0)";
                }
                //System.out.println(q);
                statement.execute(q);
                
            }
            
            //Ako nije novi nego se vrsi update
            if ( inserted )
            {   
                Statement statement2 = conn.createStatement();
                
                ResultSet rs = statement2.executeQuery("select Tob_id from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tob_superord=" + firstTobSuperord + " and Tob_id<>" + Tob_id);   
                
                int newOrder = Integer.parseInt(vOrTxt.getText());
                
                while(rs.next())
                {
                    statement.execute("update IISC_COMPTYPE_DISPLAY set Tob_order=Tob_order + 1 where Tob_order >=" + newOrder + " and Tob_id=" + rs.getInt(1));
                }
            }
            else
            {
                //Ako je promijenjen parent
                if ((firstTobSuperord != 0) && (firstTobSuperord != tobSuperord))
                {
                    int newOrder = Integer.parseInt(vOrTxt.getText());
                    Statement statement4 = conn.createStatement();
                    
                    ResultSet rs = statement4.executeQuery("select Tob_id from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tob_superord=" + firstTobSuperord + " and Tob_id<>" + Tob_id);   
                    
                    newOrder = Integer.parseInt(vOrTxt.getText());
                    
                    while(rs.next())
                    {
                        statement.execute("update IISC_COMPTYPE_DISPLAY set Tob_order=Tob_order - 1 where Tob_order >" + oldOrder + " and Tob_id=" + rs.getInt(1));
                    }
                    
                    rs = statement4.executeQuery("select Tob_id from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tob_superord=" + tobSuperord + " and Tob_id<>" + Tob_id);   
                                        
                    newOrder = Integer.parseInt(vOrTxt.getText());
                    
                    while(rs.next())
                    {
                        statement.execute("update IISC_COMPTYPE_DISPLAY set Tob_order=Tob_order + 1 where Tob_order >=" + newOrder + " and Tob_id=" + rs.getInt(1));
                    }
                    
                }
                //Ako je parent isti onda se 
                else
                {
                    //Update ordera 
                    int newOrder = Integer.parseInt(vOrTxt.getText());
                
                
                    if (oldOrder < newOrder)
                    {
                        //Nadji mu srodnike
                        Statement statement2 = conn.createStatement();
                        
                        ResultSet rs2 = statement2.executeQuery("select Tob_id from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tob_superord=" + firstTobSuperord + " and Tob_id<>" + Tob_id);   
                        
                        while(rs2.next())
                        {
                            statement2.execute("update IISC_COMPTYPE_DISPLAY set Tob_order=Tob_order - 1 where Tob_order >" + oldOrder + " and Tob_order <=" + newOrder + " and Tob_id=" + rs2.getInt(1));
                        }
                    }
                    else
                    {
                        if (oldOrder > newOrder)
                        {
                            //Nadji mu srodnike
                            Statement statement2 = conn.createStatement();
                            ResultSet rs2 = statement2.executeQuery("select Tob_id from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tob_superord=" + firstTobSuperord + " and Tob_id<>" + Tob_id);   
                            
                            while(rs2.next())
                            {
                                statement.execute("update IISC_COMPTYPE_DISPLAY set Tob_order=Tob_order + 1 where Tob_order >=" + newOrder + " and Tob_order <" + oldOrder  + " and Tob_id=" + rs2.getInt(1));
                            }
                        }
                        
                    }
                }
            }
            //Ako se promijenio layout tj. ako je bio Same window a sad je NewWindow i viceversa
            /*if (layout != oldLayout)
            {
                if(oldOrder > 0)
                {
                    statement.execute("update IISC_COMPTYPE_DISPLAY set Tob_order=Tob_order - 1 where Tob_order >" + oldOrder + " and Tob_layout=" + oldLayout + " and Tf_id=" + Tf_id);
                    
                }
                statement.execute("update IISC_COMPTYPE_DISPLAY set Tob_order=Tob_order + 1 where Tob_order >=" + newOrder + " and Tob_layout=" + layout + " and Tf_id=" + Tf_id);
            }
            else//Ako layout nije promijenjen
            {
                if(oldOrder == 0)
                {
                    statement.execute("update IISC_COMPTYPE_DISPLAY set Tob_order=Tob_order + 1 where Tob_order >=" + newOrder + " and Tob_layout=" + layout + " and Tf_id=" + Tf_id);
                }
                else
                {
                    if (oldOrder < newOrder)
                    {
                        statement.execute("update IISC_COMPTYPE_DISPLAY set Tob_order=Tob_order - 1 where Tob_order >" + oldOrder + " and Tob_order <=" + newOrder + " and Tob_layout=" + layout + " and Tf_id=" + Tf_id);
                    }
                    else
                    {
                        statement.execute("update IISC_COMPTYPE_DISPLAY set Tob_order=Tob_order + 1 where Tob_order >=" + newOrder + " and Tob_order <" + oldOrder + " and Tob_layout=" + layout + " and Tf_id=" + Tf_id);
                    }
                }
            }
             
            statement.execute("update IISC_COMPTYPE_DISPLAY set Tob_order=" + vOrTxt.getText() + " where Tob_id=" + j);
            */
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle.toString());
        }
        
    }
    
  
    private void layoutComboAction()
    {
        if (layout == layCombo.getSelectedIndex())
        {
            return;
        }

        EnableButton();
        
        layout = layCombo.getSelectedIndex();

        if (layout == 0)
        {
            
            bottomBtn.setEnabled(false);
            rightBtn.setEnabled(false);
            centerBtn.setEnabled(true);
            customBtn.setEnabled(true);
            leftBtn.setEnabled(true);
            
            
            if (customBtn.isSelected())
            {
                vXrTxt.setEnabled(true);
                vYrTxt.setEnabled(true);
            }
            else
            {
                vXrTxt.setEnabled(false);
                vYrTxt.setEnabled(false);
            }
        }
        else
        {
            
            bottomBtn.setEnabled(true);
            rightBtn.setEnabled(true);
            centerBtn.setEnabled(false);
            customBtn.setEnabled(false);
            leftBtn.setEnabled(false);
            vXrTxt.setEnabled(false);
            vYrTxt.setEnabled(false);
           
        }
    }
    
    public class ImagePanel extends JPanel implements ImageObserver
    {
  
        Image mImage;
        int x;
        int y;
      
        public ImagePanel(ImageIcon p_Icon, int p_X, int p_Y)
        {
          mImage = p_Icon.getImage();
          x = p_X;
          y = p_Y;
        }
      
        public void paint(Graphics g)
        {
            super.paint(g);
          
            g.drawImage(mImage, x, y, mImage.getWidth(this), mImage.getHeight(this), this);
            g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        }
        public void setX(int p_X)
        {
           x = p_X;
        }
      
        public void setY(int p_Y)
        {
            y = p_Y;
        }
    }
}