package iisc.tflayoutmanager;

import iisc.IISFrameMain;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.image.ImageObserver;
import javax.swing.JPanel;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

import java.util.Vector;

import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;

public class TobDispProperties extends JPanel
{
    int width;
    int Tob_id;
    int Tf_id;
    int PR_id;
    Connection conn;
    private IISFrameMain parent;

    private PropertyTextBox[] nTf = new PropertyTextBox[11];
    private JTextField[] vTf = new JTextField[11];

    public PropertyTextBox nLaTxt = new PropertyTextBox("Layout");
    public PropertyTextBox nDlTxt = new PropertyTextBox("Data Layout");
    public PropertyTextBox nOrTxt = new PropertyTextBox("Order");
    public PropertyTextBox nPoTxt = new PropertyTextBox("Position");
    public PropertyTextBox nPrTxt = new PropertyTextBox("Position Relative");
    public PropertyTextBox nXrTxt = new PropertyTextBox("X Relative");
    public PropertyTextBox nYrTxt = new PropertyTextBox("Y Relative");
    public PropertyTextBox nSrTxt = new PropertyTextBox("Search");
    public PropertyTextBox nDmTxt = new PropertyTextBox("Delete Massive");
    public PropertyTextBox nrlirTxt = new PropertyTextBox("Retain Last Inserted Record");

    public JTextField vLaTxt = new PropertyTextBox("");
    public JTextField vDlTxt = new PropertyTextBox("");
    public JTextField vOrTxt = new PropertyTextBox("");
    public JTextField vPoTxt = new PropertyTextBox("");
    public JTextField vPrTxt = new PropertyTextBox("");
    public JTextField vXrTxt = new PropertyTextBox("");
    public JTextField vYrTxt = new PropertyTextBox("");
    public JTextField vSrTxt = new PropertyTextBox("");
    public JTextField vDmTxt = new PropertyTextBox("");
    public JTextField vRlirTxt = new PropertyTextBox("");

    public JComboBox layCombo;
    public JComboBox dLayCombo;
    public JComboBox posCombo;
    public JComboBox relPosCombo;
    public JComboBox searchCombo;
    public JComboBox delMassCombo;
    public JComboBox rLirCombo;

    private JButton btnSave = new JButton();
    private JButton btnHelp = new JButton();
    private static final int txtHeight = 18;
    public int index;
    TFLayoutManager tlm;
    private String oldValue;
    private int layout;
    private int position;
    
    private static ImageIcon imageUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
    private static ImageIcon imageDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
    
    private ImagePanel downIP;
    private ImagePanel upIP;
    public int order;
    public int parentInd = -1;
    public Vector children;
    
    public TobDispProperties(IISFrameMain _parent, int _width, int height, int _Tob_id, int _Tf_id, int _PR_id, Connection _conn, int _index, TFLayoutManager _tlm)
    {
        super(null);
        this.setSize(width, height);

        parent = _parent;
        width = _width;
        Tob_id = _Tob_id;
        Tf_id = _Tf_id;
        PR_id = _PR_id;
        conn = _conn;
        index = _index;
        tlm = _tlm;
        children = new Vector();
        
        Assign();
        //InitTextFieldsPos();


        InitTextFields();
        InitComboBoxes();
        
        InitFromDataBase();
        InitImagePanels();
        
        if ( index == 0)
        {
            layCombo.setSelectedIndex(0);
            layCombo.setEnabled(false);
            vLaTxt.setEnabled(false);
        }
        
        AddActionListners();
        /*
        addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(ComponentEvent e)
            {
                Resize();
            }
        });
        revalidate();
        */
    }

    public void Resize()
    {
        int width = this.getWidth();
        int height;

        if (width % 2 != 0)
        {
            width = width + 1;
        }

        height = txtHeight * 10;

        int len = 10;
        int controlWidth = width / 2;

        for(int i = 0; i < len; i++)
        {
            nTf[i].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
            nTf[i].setEditable(false);
            nTf[i].setBackground(Color.white);
            vTf[i].setVisible(true);
            vTf[i].setBorder((Border) new EmptyBorder(new Insets(2, 1 , 2, 1)));
            nTf[i].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            vTf[i].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            vTf[i].addKeyListener(new java.awt.event.KeyAdapter()
            {
                public void keyReleased(KeyEvent e)
                {
                    EnableButton();
                }
            });

            nTf[i].setBounds(new Rectangle(0, i * txtHeight, controlWidth - 1, txtHeight -1));
            vTf[i].setBounds(new Rectangle(controlWidth , i * txtHeight , controlWidth - 1 , txtHeight -1));
            add(nTf[i], null);
            add(vTf[i], null);
        }
    }

    public void Resize(int width)
    {
        int height;

        if (width % 2 != 0)
        {
            width = width + 1;
        }

        height = txtHeight * 10;

        int len = 10;
        int controlWidth = width / 2;

        for(int i = 0; i < len; i++)
        {
            nTf[i].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
            nTf[i].setEditable(false);
            nTf[i].setBackground(Color.white);
            vTf[i].setVisible(true);
            vTf[i].setBorder((Border) new EmptyBorder(new Insets(2, 1 , 2, 1)));
            nTf[i].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            vTf[i].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            vTf[i].addKeyListener(new java.awt.event.KeyAdapter()
            {
                public void keyReleased(KeyEvent e)
                {
                    EnableButton();
                }
            });

            nTf[i].setBounds(new Rectangle(0, i * txtHeight, controlWidth - 1, txtHeight -1));
            vTf[i].setBounds(new Rectangle(controlWidth , i * txtHeight , controlWidth - 1 , txtHeight -1));
            add(nTf[i], null);
            add(vTf[i], null);
        }
    }
    
    private void InitImagePanels()
    {
        upIP = new ImagePanel(imageUp, 2, 1);
        upIP.setBackground(Color.white);
        upIP.setBorder(BorderFactory.createEmptyBorder());
        upIP.setToolTipText("Increase");
        //System.out.println("Ako je ovo zivot bolje da umrem " + vOrTxt.getWidth());
        upIP.setBounds(vOrTxt.getWidth() - 2 * txtHeight - 5,1,txtHeight-1,txtHeight-3);
        upIP.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                upIP.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
                upIP.setBorder(BorderFactory.createEmptyBorder());
            }
            
            public void mouseClicked(MouseEvent e)
            {
                Increase();
            }
            
            public void mouseEntered(MouseEvent e)
            {
                upIP.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            
            public void mouseExited(MouseEvent e)
            {
                upIP.setBorder(BorderFactory.createEmptyBorder());
            }
            
        });
        
        downIP = new ImagePanel(imageDown,2,1);
        downIP.setBackground(Color.white);
        downIP.setBorder(BorderFactory.createEmptyBorder());
        downIP.setToolTipText("Decrease");
        downIP.setBounds(vOrTxt.getWidth() - txtHeight - 5,1,txtHeight-1,txtHeight-3);
        downIP.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                downIP.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
                downIP.setBorder(BorderFactory.createEmptyBorder());
            }
            
            public void mouseClicked(MouseEvent e)
            {
                Decrease();
            }
            
            public void mouseEntered(MouseEvent e)
            {
                downIP.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            
            public void mouseExited(MouseEvent e)
            {
                downIP.setBorder(BorderFactory.createEmptyBorder());
            }
            
        });
    }
    
    private void Increase()
    {
       
        if (parentInd == -1)
        {
            return;
        }
        
        TobDispProperties tdp = (TobDispProperties)tlm.propVec.get(parentInd); 
        Vector vec = tdp.children;
        
        if (order == vec.size())
        {
            return;
        }
        int ind = ((Integer)vec.get(order)).intValue();
        
        TobDispProperties temp = (TobDispProperties)tlm.propVec.get(ind); 
        temp.order = temp.order - 1;
        temp.vOrTxt.setText("" + temp.order);
        
        tdp.children.setElementAt(new Integer(ind), order - 1);
        tdp.children.setElementAt(new Integer(index), order);
        tlm.dirty = true;
        order = order + 1;
        vOrTxt.setText(Integer.toString(order));
        tlm.layPanel.setSelected(index);
        
    }
    
    private void Decrease()
    {
        if (order == 1)
        {
            return;
        }
        
        if (parentInd == -1)
        {
            return;
        }
        
        TobDispProperties tdp = (TobDispProperties)tlm.propVec.get(parentInd); 
        Vector vec = tdp.children;
        
        int ind = ((Integer)vec.get(order - 2)).intValue();
        
        TobDispProperties temp = (TobDispProperties)tlm.propVec.get(ind); 
        temp.order = temp.order + 1;
        temp.vOrTxt.setText("" + temp.order);
        
        tdp.children.setElementAt(new Integer(ind), order - 1);
        tdp.children.setElementAt(new Integer(index), order - 2);
        tlm.dirty = true;
        order = order - 1;
        vOrTxt.setText(Integer.toString(order));
        tlm.layPanel.setSelected(index);
        
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
        
        dLayCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dLayComboAction();  
            }
        });
        
        posCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                posComboAction();
            }
        });
        
        relPosCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                relPosComboAction();
                
            }
        });
        
        searchCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                EnableButton();
            }
        });
        
        delMassCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                EnableButton();
            }
        });
        
        rLirCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                EnableButton();
            }
        });
    }
    
    private void InitComboBoxes()
    {
        String[] items = {"New window", "Same window"};
        layCombo = new JComboBox(items);

        layCombo.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
                layCombo_focusLost(e);
            }

            public void focusGained(FocusEvent e)
            {
                layCombo_focusGained(e);
            }

        });
        
        String[] items1 = {"field layout", "table layout"};
        dLayCombo = new JComboBox(items1);

        dLayCombo.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
                dLayCombo_focusLost(e);
            }

            public void focusGained(FocusEvent e)
            {
                dLayCombo_focusGained(e);
            }
        });

        String[] items2 = {"bottom", "right"};
        posCombo = new JComboBox(items2);

        posCombo.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
                posCombo_focusLost(e);
            }

            public void focusGained(FocusEvent e)
            {
                posCombo_focusGained(e);
            }

        });
        
        String[] items3 = {"center", "left on top", "custom"};
        relPosCombo = new JComboBox(items3);

        relPosCombo.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
                relPosCombo_focusLost(e);
            }

            public void focusGained(FocusEvent e)
            {
                relPosCombo_focusGained(e);
            }

        });
        
        String[] items4 = {"false", "true"};
        searchCombo = new JComboBox(items4);

        searchCombo.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
                searchCombo_focusLost(e);
            }

            public void focusGained(FocusEvent e)
            {
                searchCombo_focusGained(e);
            }

        });
        
        delMassCombo = new JComboBox(items4);

        delMassCombo.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
                delMassCombo_focusLost(e);
            }

            public void focusGained(FocusEvent e)
            {
                delMassCombo_focusGained(e);
            }

        });
        
        rLirCombo = new JComboBox(items4);

        rLirCombo.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
                rLirCombo_focusLost(e);
            }

            public void focusGained(FocusEvent e)
            {
                rLirCombo_focusGained(e);
            }

        });
        
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
                
                if (layout == 0)
                {
                    nPoTxt.setEnabled(false);
                    vPoTxt.setEnabled(false);
                }
                else
                {
                    nPrTxt.setEnabled(false);
                    vPrTxt.setEnabled(false);
                }
                
                vTf[0].setText(layCombo.getSelectedItem().toString());

                dLayCombo.setSelectedIndex(rs.getInt("Tob_data_layout"));
                vTf[1].setText(dLayCombo.getSelectedItem().toString());
                order = rs.getInt("Tob_order");
                vTf[2].setText("" + order);


                posCombo.setSelectedIndex(rs.getInt("Tob_position"));
                position = posCombo.getSelectedIndex();
                vTf[3].setText(posCombo.getSelectedItem().toString());

                relPosCombo.setSelectedIndex(rs.getInt("Tob_position_relative"));
                vTf[4].setText(relPosCombo.getSelectedItem().toString());
                
                if ((relPosCombo.getSelectedIndex() < 2) || (layout == 1))
                {
                    vXrTxt.setEnabled(false);
                    vYrTxt.setEnabled(false);
                    nXrTxt.setEnabled(false);
                    nYrTxt.setEnabled(false);
                }
                else
                {
                        vXrTxt.setEnabled(true);
                        vYrTxt.setEnabled(true);
                        nXrTxt.setEnabled(true);
                        nYrTxt.setEnabled(true);
                }

                vTf[5].setText("" + rs.getInt("Tob_x_relative"));


                vTf[6].setText("" + rs.getInt("Tob_y_relative"));

                searchCombo.setSelectedIndex(rs.getInt("Tob_search"));
                vTf[7].setText(searchCombo.getSelectedItem().toString());

                delMassCombo.setSelectedIndex(rs.getInt("Tob_del_masive"));
                vTf[8].setText(delMassCombo.getSelectedItem().toString());
                
                rLirCombo.setSelectedIndex(rs.getInt("Tob_retain_last_ins_record"));
                vTf[9].setText(delMassCombo.getSelectedItem().toString());
            }
            else
            {
                InitEmpty();
            }
        }
        catch(Exception e)
        {
            //System.out.println(e.toString());
            InitEmpty();
            return;
        }
    }


    private void InitEmpty()
    {

        layCombo.setSelectedIndex(0);
        vTf[0].setText(layCombo.getSelectedItem().toString());

        dLayCombo.setSelectedIndex(0);
        vTf[1].setText(dLayCombo.getSelectedItem().toString());
        vTf[2].setText("0");


        posCombo.setSelectedIndex(0);
        vTf[3].setText(posCombo.getSelectedItem().toString());

        relPosCombo.setSelectedIndex(0);
        vTf[4].setText(relPosCombo.getSelectedItem().toString());


        vTf[5].setText("0");


        vTf[6].setText("0");

        searchCombo.setSelectedIndex(0);
        vTf[7].setText(searchCombo.getSelectedItem().toString());

        delMassCombo.setSelectedIndex(0);
        vTf[8].setText(delMassCombo.getSelectedItem().toString());

        rLirCombo.setSelectedIndex(0);
        vTf[9].setText(rLirCombo.getSelectedItem().toString());
    }

    private void Assign()
    {
        nTf[0] = nLaTxt;
        nTf[1] = nDlTxt;
        nTf[2] = nOrTxt;
        nTf[3] = nPoTxt;
        nTf[4] = nPrTxt;
        nTf[5] = nXrTxt;
        nTf[6] = nYrTxt;
        nTf[7] = nSrTxt;
        nTf[8] = nDmTxt;
        nTf[9] = nrlirTxt;

        vTf[0] = vLaTxt;
        vTf[1] = vDlTxt;
        vTf[2] = vOrTxt;
        vTf[3] = vPoTxt;
        vTf[4] = vPrTxt;
        vTf[5] = vXrTxt;
        vTf[6] = vYrTxt;
        vTf[7] = vSrTxt;
        vTf[8] = vDmTxt;
        vTf[9] = vRlirTxt;
    }

    private void InitTextFieldsPos()
    {
        int width = this.width;
        int height;

        if (width % 2 != 0)
        {
            width = width - 1;
        }

        height = txtHeight * 10;

        int len = 10;
        int controlWidth = width / 2;

        for(int i = 0; i < len; i++)
        {
            nTf[i].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
            nTf[i].setEditable(false);
            nTf[i].setBackground(Color.white);
            vTf[i].setVisible(true);
            vTf[i].setBorder((Border) new EmptyBorder(new Insets(2, 1 , 2, 1)));
            nTf[i].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            vTf[i].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            vTf[i].addKeyListener(new java.awt.event.KeyAdapter()
            {
                public void keyReleased(KeyEvent e)
                {
                    EnableButton();
                }
            });

            nTf[i].setBounds(new Rectangle(0, i * txtHeight, controlWidth - 1, txtHeight -1));
            vTf[i].setBounds(new Rectangle(controlWidth , i * txtHeight , controlWidth - 1 , txtHeight -1));
            add(nTf[i], null);
            add(vTf[i], null);
        }

        //pPanel.setPreferredSize(new Dimension(width - 3, 9*txtHeight));
    }

    public void EnableButton()
    {
        /*ob.btnApply.setEnabled(true);
        ob.btnSave.setEnabled(true);*/
        tlm.dirty = true;
    }

    private void InitTextFields()
    {

        nLaTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
              nLaTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
              nLaTxt_focusLost(e);
            }

        });


        vLaTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vLaTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                vLaTxt_focusLost(e);
            }

        });

        nOrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nOrTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                nOrTxt_focusLost(e);
            }

        });

        vOrTxt.setEditable(false);
        vOrTxt.setLayout(null);
        vOrTxt.setBackground(Color.WHITE);
        vOrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vOrTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                vOrTxt_focusLost(e);
            }

        });

        nDlTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nDlTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                nDlTxt_focusLost(e);
            }

        });


        vDlTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vDlTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                vDlTxt_focusLost(e);
            }

        });

        nPoTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nPoTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                nPoTxt_focusLost(e);
            }

        });


        vPoTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vPoTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                vPoTxt_focusLost(e);
            }

        });

        nPrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nPrTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                nPrTxt_focusLost(e);
            }

        });


        vPrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vPrTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                vPrTxt_focusLost(e);
            }

        });

        nXrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nXrTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                nXrTxt_focusLost(e);
            }

        });


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

        nYrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nYrTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                nYrTxt_focusLost(e);
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

        nSrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nSrTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                nSrTxt_focusLost(e);
            }

        });


        vSrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vSrTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                vSrTxt_focusLost(e);
            }

        });

        nDmTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nDmTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                nDmTxt_focusLost(e);
            }

        });


        vDmTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vDmTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                vDmTxt_focusLost(e);
            }

        });        
        
        nrlirTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nrlirTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                nrlirTxt_focusLost(e);
            }

        });


        vRlirTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vRlirTxt_focusGained(e);
            }

            public void focusLost(FocusEvent e)
            {
                vRlirTxt_focusLost(e);
            }

        });
    }

    public void nLaTxt_focusGained(FocusEvent e)
    {
        nTf[0].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[0].repaint();
        vTf[0].setBackground(SystemColor.textHighlight);
        vTf[0].setForeground(Color.white);
        vTf[0].repaint();
    }

    public void nLaTxt_focusLost(FocusEvent e)
    {
        nTf[0].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
        nTf[0].repaint();
        vTf[0].setBackground(Color.white);
        vTf[0].setForeground(Color.black);
        vTf[0].repaint();
    }

    public void vLaTxt_focusGained(FocusEvent e)
    {
        nTf[0].setBackground(SystemColor.textHighlight);
        nTf[0].setForeground(Color.white);
        nTf[0].repaint();

        if (index == 0)
        {
            return;
        }
        
        layCombo.setSize(vLaTxt.getWidth() , vLaTxt.getHeight() );
        layCombo.setBounds(0, 0, vLaTxt.getWidth(), vLaTxt.getHeight());

        vLaTxt.add(layCombo, null);
        vLaTxt.repaint();

        layCombo.repaint();
        layCombo.revalidate();
        repaint();
        layCombo.setFocusable(true);
        layCombo.requestFocus();
        layCombo.setPopupVisible(true);

    }

    public void vLaTxt_focusLost(FocusEvent e)
    {
        nTf[0].setBackground(Color.white);
        nTf[0].setForeground(Color.black);
        nTf[0].repaint();
    }

    public void nDlTxt_focusGained(FocusEvent e)
    {
        nTf[1].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[1].repaint();
        vTf[1].setBackground(SystemColor.textHighlight);
        vTf[1].setForeground(Color.white);
        vTf[1].repaint();
    }

    public void nDlTxt_focusLost(FocusEvent e)
    {
        nTf[1].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
        nTf[1].repaint();
        vTf[1].setBackground(Color.white);
        vTf[1].setForeground(Color.black);
        vTf[1].repaint();
    }

    public void vDlTxt_focusGained(FocusEvent e)
    {
        nTf[1].setBackground(SystemColor.textHighlight);
        nTf[1].setForeground(Color.white);
        nTf[1].repaint();

        dLayCombo.setSize(vDlTxt.getWidth() , vDlTxt.getHeight() );
        dLayCombo.setBounds(0, 0, vDlTxt.getWidth(), vDlTxt.getHeight());

        vDlTxt.add(dLayCombo, null);
        vDlTxt.repaint();

        dLayCombo.repaint();
        dLayCombo.revalidate();
        repaint();


        dLayCombo.setFocusable(true);
        dLayCombo.requestFocus();
        dLayCombo.setPopupVisible(true);
    }

    public void vDlTxt_focusLost(FocusEvent e)
    {
        nTf[1].setBackground(Color.white);
        nTf[1].setForeground(Color.black);
        nTf[1].repaint();
    }

    public void nOrTxt_focusGained(FocusEvent e)
    {
        nTf[2].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[2].repaint();
        vTf[2].setBackground(SystemColor.textHighlight);
        vTf[2].setForeground(Color.white);
        vTf[2].repaint();
    }

    public void nOrTxt_focusLost(FocusEvent e)
    {
        nTf[2].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
        nTf[2].repaint();
        vTf[2].setBackground(Color.white);
        vTf[2].setForeground(Color.black);
        vTf[2].repaint();
    }

    public void vOrTxt_focusGained(FocusEvent e)
    {
        nTf[2].setBackground(SystemColor.textHighlight);
        nTf[2].setForeground(Color.white);
        nTf[2].repaint();
        upIP.setBounds(vOrTxt.getWidth() - 2 * txtHeight - 5,1,txtHeight-1,txtHeight-3);
        downIP.setBounds(vOrTxt.getWidth() - txtHeight - 5,1,txtHeight-1,txtHeight-3);
        vOrTxt.add(upIP,null);
        vOrTxt.add(downIP,null);
        vOrTxt.revalidate();
        vOrTxt.repaint();
        upIP.repaint();
        downIP.repaint();
    }

    public void vOrTxt_focusLost(FocusEvent e)
    {
        nTf[2].setBackground(Color.white);
        nTf[2].setForeground(Color.black);
        nTf[2].repaint();
        vOrTxt.removeAll();
        vOrTxt.repaint();
        vOrTxt.revalidate();
        upIP.repaint();
        downIP.repaint();
    }

    public void nPoTxt_focusGained(FocusEvent e)
    {
        nTf[3].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[3].repaint();
        vTf[3].setBackground(SystemColor.textHighlight);
        vTf[3].setForeground(Color.white);
        vTf[3].repaint();
    }

    public void nPoTxt_focusLost(FocusEvent e)
    {
        nTf[3].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
        nTf[3].repaint();
        vTf[3].setBackground(Color.white);
        vTf[3].setForeground(Color.black);
        vTf[3].repaint();
    }

    public void vPoTxt_focusGained(FocusEvent e)
    {
        nTf[3].setBackground(SystemColor.textHighlight);
        nTf[3].setForeground(Color.white);
        nTf[3].repaint();

        posCombo.setSize(vPoTxt.getWidth() , vPoTxt.getHeight() );
        posCombo.setBounds(0, 0, vPoTxt.getWidth(), vPoTxt.getHeight());

        vPoTxt.add(posCombo, null);
        vPoTxt.repaint();

        posCombo.repaint();
        posCombo.revalidate();
        repaint();


        posCombo.setFocusable(true);
        posCombo.requestFocus();
        posCombo.setPopupVisible(true);
    }

    public void vPoTxt_focusLost(FocusEvent e)
    {
        nTf[3].setBackground(Color.white);
        nTf[3].setForeground(Color.black);
        nTf[3].repaint();
    }

    public void nPrTxt_focusGained(FocusEvent e)
    {
        nTf[4].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[4].repaint();
        vTf[4].setBackground(SystemColor.textHighlight);
        vTf[4].setForeground(Color.white);
        vTf[4].repaint();
    }

    public void nPrTxt_focusLost(FocusEvent e)
    {
        nTf[4].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
        nTf[4].repaint();
        vTf[4].setBackground(Color.white);
        vTf[4].setForeground(Color.black);
        vTf[4].repaint();
    }

    public void vPrTxt_focusGained(FocusEvent e)
    {
        nTf[4].setBackground(SystemColor.textHighlight);
        nTf[4].setForeground(Color.white);
        nTf[4].repaint();

        relPosCombo.setSize(vPrTxt.getWidth() , vPrTxt.getHeight() );
        relPosCombo.setBounds(0, 0, vPrTxt.getWidth(), vPrTxt.getHeight());

        vPrTxt.add(relPosCombo, null);
        vPrTxt.repaint();

        relPosCombo.repaint();
        relPosCombo.revalidate();
        repaint();


        relPosCombo.setFocusable(true);
        relPosCombo.requestFocus();
        relPosCombo.setPopupVisible(true);
    }

    public void vPrTxt_focusLost(FocusEvent e)
    {
        nTf[4].setBackground(Color.white);
        nTf[4].setForeground(Color.black);
        nTf[4].repaint();
    }

    public void nXrTxt_focusGained(FocusEvent e)
    {
        nTf[5].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[5].repaint();
        vTf[5].setBackground(SystemColor.textHighlight);
        vTf[5].setForeground(Color.white);
        vTf[5].repaint();
    }

    public void nXrTxt_focusLost(FocusEvent e)
    {
        nTf[5].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
        nTf[5].repaint();
        vTf[5].setBackground(Color.white);
        vTf[5].setForeground(Color.black);
        vTf[5].repaint();
    }

    public void vXrTxt_focusGained(FocusEvent e)
    {
          oldValue = vXrTxt.getText();
          nXrTxt.setBackground(SystemColor.textHighlight);
          nXrTxt.setForeground(Color.white);
          nXrTxt.repaint();
    }

    public void vXrTxt_focusLost(FocusEvent e)
    {
        nXrTxt.setBackground(Color.white);
        nXrTxt.setForeground(Color.black);
        nXrTxt.repaint();
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
        
        if ( vXrTxt.getText() != oldValue )
        {
            tlm.dirty = true;
            oldValue = vXrTxt.getText();
        }
        TobPanel tobP = (TobPanel)tlm.tobPanelVec.get(index);
        tobP.x = LayoutPanel.boundX + value * (LayoutPanel.sqWidth/100);
        tobP.setBounds(tobP.x, tobP.y, tobP.width, tobP.height);
        tlm.tobPanelVec.setElementAt(tobP, index);
        tlm.layPanel.revalidate();
        
    }

    public void nYrTxt_focusGained(FocusEvent e)
    {
        nTf[6].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[6].repaint();
        vTf[6].setBackground(SystemColor.textHighlight);
        vTf[6].setForeground(Color.white);
        vTf[6].repaint();
    }

    public void nYrTxt_focusLost(FocusEvent e)
    {
        nTf[6].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
        nTf[6].repaint();
        vTf[6].setBackground(Color.white);
        vTf[6].setForeground(Color.black);
        vTf[6].repaint();
    }

    public void vYrTxt_focusGained(FocusEvent e)
    {
        nTf[6].setBackground(SystemColor.textHighlight);
        nTf[6].setForeground(Color.white);
        nTf[6].repaint();
        oldValue = vYrTxt.getText();
    }

    public void vYrTxt_focusLost(FocusEvent e)
    {
        nTf[6].setBackground(Color.white);
        nTf[6].setForeground(Color.black);
        nTf[6].repaint();
        
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
        
        if ( vYrTxt.getText() != oldValue )
        {
            tlm.dirty = true;
            oldValue = vYrTxt.getText();
        }
        
        TobPanel tobP = (TobPanel)tlm.tobPanelVec.get(index);
        tobP.y = LayoutPanel.boundY + value * (LayoutPanel.boundW/100);
        tobP.setBounds(tobP.x, tobP.y, tobP.width, tobP.height);
        tlm.tobPanelVec.setElementAt(tobP, index);
        tlm.layPanel.revalidate();
    }

    public void nSrTxt_focusGained(FocusEvent e)
    {
        nTf[7].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[7].repaint();
        vTf[7].setBackground(SystemColor.textHighlight);
        vTf[7].setForeground(Color.white);
        vTf[7].repaint();
    }

    public void nSrTxt_focusLost(FocusEvent e)
    {
        nTf[7].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
        nTf[7].repaint();
        vTf[7].setBackground(Color.white);
        vTf[7].setForeground(Color.black);
        vTf[7].repaint();
    }

    public void vSrTxt_focusGained(FocusEvent e)
    {
        nTf[7].setBackground(SystemColor.textHighlight);
        nTf[7].setForeground(Color.white);
        nTf[7].repaint();

        searchCombo.setSize(vSrTxt.getWidth() , vSrTxt.getHeight() );
        searchCombo.setBounds(0, 0, vSrTxt.getWidth(), vSrTxt.getHeight());

        vSrTxt.add(searchCombo, null);
        vSrTxt.repaint();

        searchCombo.repaint();
        searchCombo.revalidate();
        repaint();


        searchCombo.setFocusable(true);
        searchCombo.requestFocus();
        searchCombo.setPopupVisible(true);
    }

    public void vSrTxt_focusLost(FocusEvent e)
    {
        nTf[7].setBackground(Color.white);
        nTf[7].setForeground(Color.black);
        nTf[7].repaint();
    }

    public void nDmTxt_focusGained(FocusEvent e)
    {
        nTf[8].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[8].repaint();
        vTf[8].setBackground(SystemColor.textHighlight);
        vTf[8].setForeground(Color.white);
        vTf[8].repaint();
    }
    
    public void nrlirTxt_focusGained(FocusEvent e)
    {
        nTf[9].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[9].repaint();
        vTf[9].setBackground(SystemColor.textHighlight);
        vTf[9].setForeground(Color.white);
        vTf[9].repaint();
    }
    
    public void nDmTxt_focusLost(FocusEvent e)
    {
        nTf[8].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
        nTf[8].repaint();
        vTf[8].setBackground(Color.white);
        vTf[8].setForeground(Color.black);
        vTf[8].repaint();
    }

    public void nrlirTxt_focusLost(FocusEvent e)
    {
        nTf[9].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
        nTf[9].repaint();
        vTf[9].setBackground(Color.white);
        vTf[9].setForeground(Color.black);
        vTf[9].repaint();
    }
    
    public void vDmTxt_focusGained(FocusEvent e)
    {
        nTf[8].setBackground(SystemColor.textHighlight);
        nTf[8].setForeground(Color.white);
        nTf[8].repaint();

        delMassCombo.setSize(vDmTxt.getWidth() , vDmTxt.getHeight() );
        delMassCombo.setBounds(0, 0, vDmTxt.getWidth(), vDmTxt.getHeight());

        vDmTxt.add(delMassCombo, null);
        vDmTxt.repaint();
        delMassCombo.revalidate();
        repaint();


        delMassCombo.setFocusable(true);
        delMassCombo.requestFocus();
        delMassCombo.setPopupVisible(true);
        delMassCombo.repaint();
    }
    
    public void vRlirTxt_focusGained(FocusEvent e)
    {
        nTf[9].setBackground(SystemColor.textHighlight);
        nTf[9].setForeground(Color.white);
        nTf[9].repaint();

        rLirCombo.setSize(vDmTxt.getWidth() , vDmTxt.getHeight() );
        rLirCombo.setBounds(0, 0, vDmTxt.getWidth(), vDmTxt.getHeight());

        vRlirTxt.add(rLirCombo, null);
        vRlirTxt.repaint();
        rLirCombo.revalidate();
        repaint();


        rLirCombo.setFocusable(true);
        rLirCombo.requestFocus();
        rLirCombo.setPopupVisible(true);
        rLirCombo.repaint();
    }
    
    public void vDmTxt_focusLost(FocusEvent e)
    {
        nTf[8].setBackground(Color.white);
        nTf[8].setForeground(Color.black);
        nTf[8].repaint();
    }
    
    public void vRlirTxt_focusLost(FocusEvent e)
    {
        nTf[9].setBackground(Color.white);
        nTf[9].setForeground(Color.black);
        nTf[9].repaint();
    }
    
    /**************************************************************************/
    /*****************       Dogadjaji za combo boxeve ************************/
    /**************************************************************************/
    public void layCombo_focusLost(FocusEvent e)
    {
        vLaTxt.setText(layCombo.getSelectedItem().toString());
        vLaTxt.removeAll();
        /*vLaTxt.revalidate();
        vLaTxt.repaint();*/
        nLaTxt.setBackground(Color.white);
        nLaTxt.setForeground(Color.black);
        nLaTxt.repaint();
    }

    public void dLayCombo_focusLost(FocusEvent e)
    {
        vDlTxt.setText(dLayCombo.getSelectedItem().toString());
        vDlTxt.removeAll();
        nDlTxt.setBackground(Color.white);
        nDlTxt.setForeground(Color.black);
        nDlTxt.repaint();
    }

    public void posCombo_focusLost(FocusEvent e)
    {
        vPoTxt.setText(posCombo.getSelectedItem().toString());
        vPoTxt.removeAll();
        nPoTxt.setBackground(Color.white);
        nPoTxt.setForeground(Color.black);
        nPoTxt.repaint();

    }

    public void relPosCombo_focusLost(FocusEvent e)
    {
        vPrTxt.setText(relPosCombo.getSelectedItem().toString());
        vPrTxt.removeAll();
        nPrTxt.setBackground(Color.white);
        nPrTxt.setForeground(Color.black);
        nPrTxt.repaint();
        repaint();
    }

    public void searchCombo_focusLost(FocusEvent e)
    {
        vSrTxt.setText(searchCombo.getSelectedItem().toString());
        vSrTxt.removeAll();
        nSrTxt.setBackground(Color.white);
        nSrTxt.setForeground(Color.black);
        nSrTxt.repaint();
        repaint();
    }

    public void delMassCombo_focusLost(FocusEvent e)
    {
        vDmTxt.setText(delMassCombo.getSelectedItem().toString());
        vDmTxt.removeAll();
        nDmTxt.setBackground(Color.white);
        nDmTxt.setForeground(Color.black);
        nDmTxt.repaint();
        repaint();
    }
    
    public void rLirCombo_focusLost(FocusEvent e)
    {
        vRlirTxt.setText(rLirCombo.getSelectedItem().toString());
        vRlirTxt.removeAll();
        nrlirTxt.setBackground(Color.white);
        nrlirTxt.setForeground(Color.black);
        nrlirTxt.repaint();
        repaint();
    }

    public void layCombo_focusGained(FocusEvent e)
    {
        nLaTxt.setBackground(SystemColor.textHighlight);
        nLaTxt.setForeground(Color.white);
        nLaTxt.repaint();
    }

    public void dLayCombo_focusGained(FocusEvent e)
    {
        nDlTxt.setBackground(SystemColor.textHighlight);
        nDlTxt.setForeground(Color.white);
        nDlTxt.repaint();
    }

    public void posCombo_focusGained(FocusEvent e)
    {
        nPoTxt.setBackground(SystemColor.textHighlight);
        nPoTxt.setForeground(Color.white);
        nPoTxt.repaint();
    }

    public void relPosCombo_focusGained(FocusEvent e)
    {
        nPrTxt.setBackground(SystemColor.textHighlight);
        nPrTxt.setForeground(Color.white);
        nPrTxt.repaint();
    }

    public void searchCombo_focusGained(FocusEvent e)
    {
        nSrTxt.setBackground(SystemColor.textHighlight);
        nSrTxt.setForeground(Color.white);
        nSrTxt.repaint();
    }

    public void delMassCombo_focusGained(FocusEvent e)
    {
        nDmTxt.setBackground(SystemColor.textHighlight);
        nDmTxt.setForeground(Color.white);
        nDmTxt.repaint();
    }

    public void rLirCombo_focusGained(FocusEvent e)
    {
        nrlirTxt.setBackground(SystemColor.textHighlight);
        nrlirTxt.setForeground(Color.white);
        nrlirTxt.repaint();
    }
    
    public void Update(Statement statement)
    {
        try
        {
            
            ResultSet rs = statement.executeQuery("select Tob_id from IISC_COMPTYPE_DISPLAY  where Tob_id=" + Tob_id);
            
            if (rs.next())
            {
                String q = "update IISC_COMPTYPE_DISPLAY set Tob_layout=" + layCombo.getSelectedIndex() + ",Tob_data_layout=" + dLayCombo.getSelectedIndex() + ",Tob_order=" + vOrTxt.getText() + ",Tob_position=" + posCombo.getSelectedIndex() + ",Tob_position_relative=" + relPosCombo.getSelectedIndex() + ",Tob_x_relative=" + vXrTxt.getText() + ",Tob_y_relative=" + vYrTxt.getText() + ",Tob_search=" + searchCombo.getSelectedIndex() + ",Tob_del_masive=" + delMassCombo.getSelectedIndex() + ",Tob_retain_last_ins_record=" + rLirCombo.getSelectedIndex();;
                q = q + " where Tob_id=" + Tob_id;
                statement.execute(q);
            }
            else
            {
                String q = "insert into IISC_COMPTYPE_DISPLAY(Tob_id,Tf_id,PR_id,Tob_layout,Tob_order,Tob_data_layout,Tob_position,Tob_position_relative,Tob_x_relative,Tob_y_relative,Tob_search,Tob_del_masive, Tob_retain_last_ins_record) values(" + Tob_id + "," + Tf_id + "," + PR_id + "," + layCombo.getSelectedIndex() + ","  + vOrTxt.getText();
                
                q = q + "," + dLayCombo.getSelectedIndex() + "," + posCombo.getSelectedIndex() + "," + relPosCombo.getSelectedIndex() + "," + vXrTxt.getText() + "," + vYrTxt.getText() + "," + searchCombo.getSelectedIndex() + "," + delMassCombo.getSelectedIndex() + "," + rLirCombo.getSelectedIndex() + ")";
                
                statement.execute(q);
                
            }
            
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle.toString());
        }
    }
    
    private void posComboAction()
    {
        if (position == posCombo.getSelectedIndex())
        {
            return;
        }
        
        tlm.dirty = true;
        EnableButton();
        position = posCombo.getSelectedIndex();
        tlm.layPanel.setSelected(index);
        //tlm.layPanel.DrawOrder();
        tlm.layPanel.revalidate();
    }
    
    private void dLayComboAction()
    {
        TobPanel tobP = (TobPanel)tlm.tobPanelVec.get(index);
        tlm.dirty = true;
        tobP.repaint();
    }
    private void relPosComboAction()
    {
        TobPanel tobP = (TobPanel)tlm.tobPanelVec.get(index);
        tlm.dirty = true;
        
        if (relPosCombo.getSelectedIndex() == 0)
        {
            if (tobP.width > LayoutPanel.boundW )
            {
                tobP.x = LayoutPanel.boundX;
            }
            else
            {
                tobP.x = LayoutPanel.boundX + LayoutPanel.boundW / 2 - tobP.width / 2;
            }
            
            if (tobP.height > LayoutPanel.boundY) 
            {
                tobP.y = LayoutPanel.boundX;
            }
            else
            {
                tobP.y = LayoutPanel.boundY + LayoutPanel.boundH / 2 - tobP.height / 2;
            }
            
            tobP.draggEnabled = false;
            vXrTxt.setText("" + (int)((((float)(tobP.x - LayoutPanel.boundX))/LayoutPanel.boundW)*100) );
            vYrTxt.setText("" + (int)((((float)(tobP.x - LayoutPanel.boundX))/LayoutPanel.boundW)*100) );
            vXrTxt.setEnabled(false);
            vYrTxt.setEnabled(false);
            nXrTxt.setEnabled(false);
            nYrTxt.setEnabled(false);
        }
        else
        {
            if (relPosCombo.getSelectedIndex() == 1)
            {
                tobP.x = LayoutPanel.boundX;
                tobP.y = LayoutPanel.boundY;
                tobP.draggEnabled = false;
                vXrTxt.setText("0");
                vYrTxt.setText("0");
                vXrTxt.setEnabled(false);
                vYrTxt.setEnabled(false);
                nXrTxt.setEnabled(false);
                nYrTxt.setEnabled(false);
            }
            else
            {
                tobP.x = LayoutPanel.boundX + Integer.parseInt(vXrTxt.getText())*(LayoutPanel.boundW/100);
                tobP.y = LayoutPanel.boundY + Integer.parseInt(vYrTxt.getText())*(LayoutPanel.boundH/100);;
                tobP.draggEnabled = true;
                vXrTxt.setEnabled(true);
                vYrTxt.setEnabled(true);
                nXrTxt.setEnabled(true);
                nYrTxt.setEnabled(true);
            }
        }
       
        tobP.setBounds(tobP.x, tobP.y, tobP.width, tobP.height);
        tlm.dirty = true;
        tlm.tobPanelVec.setElementAt(tobP, index);
        tlm.layPanel.setSelected(index);
        tlm.layPanel.revalidate();
    }
    
    private void layoutComboAction()
    {
        TobPanel tobP = (TobPanel)tlm.tobPanelVec.get(index);
        
        if (index == 0)
        {
            return;
        }
        
        if (layout == layCombo.getSelectedIndex())
        {
            return;
        }

        layout = layCombo.getSelectedIndex();
        tlm.dirty = true;
        
        if (layout == 0)
        {
            nPoTxt.setEnabled(false);
            vPoTxt.setEnabled(false);
            nPrTxt.setEnabled(true);
            vPrTxt.setEnabled(true);
            
            if (relPosCombo.getSelectedIndex() < 2)
            {
                vXrTxt.setEnabled(false);
                nXrTxt.setEnabled(false);
                vYrTxt.setEnabled(false);
                nYrTxt.setEnabled(false);
            }
            else
            {
                vXrTxt.setEnabled(true);
                nXrTxt.setEnabled(true);
                vYrTxt.setEnabled(true);
                nYrTxt.setEnabled(true);
            }
        }
        else
        {
            nPoTxt.setEnabled(true);
            vPoTxt.setEnabled(true);
            nPrTxt.setEnabled(false);
            vPrTxt.setEnabled(false);
            vXrTxt.setEnabled(false);
            nXrTxt.setEnabled(false);
            vYrTxt.setEnabled(false);
            nYrTxt.setEnabled(false);
        }
        
        tobP.width = tobP.widthF;
        tobP.height = tobP.heightF;
        
        if (layCombo.getSelectedIndex() == 0)
        {
            tlm.layPanel.removeAll();
            tlm.layPanel.square = true;
        
            if (relPosCombo.getSelectedIndex() == 0)
            {
                tobP.x = LayoutPanel.boundX + LayoutPanel.boundW / 2 - tobP.width / 2;
                tobP.y = LayoutPanel.boundY + LayoutPanel.boundH / 2 - tobP.height / 2;
                tobP.draggEnabled = false;
                vXrTxt.setEnabled(false);
                vYrTxt.setEnabled(false);
                nXrTxt.setEnabled(false);
                nYrTxt.setEnabled(false);
            }
            else
            {
                if (relPosCombo.getSelectedIndex() == 1)
                {
                    tobP.x = LayoutPanel.boundX;
                    tobP.y = LayoutPanel.boundY;
                    tobP.draggEnabled = false;
                    vXrTxt.setEnabled(false);
                    vYrTxt.setEnabled(false);
                    nXrTxt.setEnabled(false);
                    nYrTxt.setEnabled(false);
                }
                else
                {
                    tobP.x = LayoutPanel.boundX + Integer.parseInt(vXrTxt.getText())*(LayoutPanel.boundW/100);
                    tobP.y = LayoutPanel.boundY + Integer.parseInt(vYrTxt.getText())*(LayoutPanel.boundH/100);;
                    tobP.draggEnabled = true;
                    vXrTxt.setEnabled(true);
                    vYrTxt.setEnabled(true);
                    nXrTxt.setEnabled(true);
                    nYrTxt.setEnabled(true);
                }
            }

            tobP.setBounds(tobP.x, tobP.y, tobP.width, tobP.height);
            tlm.layPanel.add(tobP);
            tlm.layPanel.revalidate();
            tlm.layPanel.setSelected(index);
           
            tlm.layPanel.revalidate();
            tlm.layPanel.repaint();
        }  
        else
        {
            tlm.layPanel.removeAll();
            tlm.layPanel.square = false;
            tlm.layPanel.setSelected(index);
            tlm.layPanel.revalidate();
        }
    }

   
    public class PropertyTextBox extends JTextField
    {
        public PropertyTextBox()
        {
            super();
        }

        public PropertyTextBox(String text)
        {
            super(text);
        }

        public void paint(Graphics g)
        {
            super.paint(g);
            if (hasFocus())
            {
                g.setColor(Color.BLACK);
                //g.drawRect(0 ,0 , getWidth() - 1, getHeight() - 1);
            }

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