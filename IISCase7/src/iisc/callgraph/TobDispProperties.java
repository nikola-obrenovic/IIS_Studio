package iisc.callgraph;

import iisc.IISFrameMain;
import iisc.PropertyTextBox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.sql.*;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

public class TobDispProperties extends JPanel 
{
    int width;
    int Tob_id;
    int Tf_id;
    int PR_id;
    Connection conn;
    private JPanel pPanel;
    private JToolBar tb;
    private IISFrameMain parent;
    
    private PropertyTextBox[] nTf = new PropertyTextBox[10];
    private JTextField[] vTf = new JTextField[10];
    
    private PropertyTextBox nLaTxt = new PropertyTextBox("Layout");
    private PropertyTextBox nDlTxt = new PropertyTextBox("Data Layout");
    private PropertyTextBox nOrTxt = new PropertyTextBox("Order");
    private PropertyTextBox nPoTxt = new PropertyTextBox("Position");
    private PropertyTextBox nPrTxt = new PropertyTextBox("Position Relative");
    private PropertyTextBox nXrTxt = new PropertyTextBox("X Relative");
    private PropertyTextBox nYrTxt = new PropertyTextBox("Y Relative");
    private PropertyTextBox nSrTxt = new PropertyTextBox("Search");
    private PropertyTextBox nDmTxt = new PropertyTextBox("Delete Massive");
    
    private JTextField vLaTxt = new PropertyTextBox("");
    private JTextField vDlTxt = new PropertyTextBox("");
    private JTextField vOrTxt = new PropertyTextBox("");
    private JTextField vPoTxt = new PropertyTextBox("");
    private JTextField vPrTxt = new PropertyTextBox("");
    private JTextField vXrTxt = new PropertyTextBox("");
    private JTextField vYrTxt = new PropertyTextBox("");
    private JTextField vSrTxt = new PropertyTextBox("");
    private JTextField vDmTxt = new PropertyTextBox("");
    
    private JComboBox layCombo;
    private JComboBox dLayCombo;
    private JComboBox posCombo;
    private JComboBox relPosCombo;
    private JComboBox searchCombo;
    private JComboBox delMassCombo;
    
    Integer[] useitems;
    
    private static ImageIcon imageSave = new ImageIcon(IISFrameMain.class.getResource("icons/save2.gif"));
    private static ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private static ImageIcon imageUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
    private static ImageIcon imageDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
    
    private JButton btnSave = new JButton();
    private JButton btnHelp = new JButton();
    private static final int txtHeight = 18;
    private boolean mDown = false;
    private JScrollPane sp;
    private iisc.ObjectType ob;
    private int layout = 0;
    private int maxNewOrder = 0;
    private int maxSameOrder = 0;
    private int oldOrder;
    private int oldLayout;
    private String oldValue;
  
    private ImagePanel downIP;
    private ImagePanel upIP;
     
    public TobDispProperties(IISFrameMain _parent, iisc.ObjectType _ob, int _width, int height, int _Tob_id, int _Tf_id, int _PR_id, Connection _conn)
    {
        super(new BorderLayout());
        this.setSize(width, height);
        
        parent = _parent;
        width = _width;
        Tob_id = _Tob_id;
        Tf_id = _Tf_id;
        PR_id = _PR_id;
        conn = _conn;
        ob = _ob;
        
        pPanel = new JPanel(null);
        pPanel.setSize(new Dimension(width, 180));
        pPanel.setBackground(SystemColor.textInactiveText);
        
        Assign();
        InitTextFieldsPos();
        sp = new JScrollPane(pPanel);
        //sp.setBackground(Color.YELLOW);
        sp.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        add(sp, BorderLayout.CENTER);
        InitImagePanels();
        
        InitTextFields();
        InitComboBoxes();
        InitOrder();
        InitFromDataBase();
        AddActionListners();
        revalidate();
    }
    
    private void InitImagePanels()
    {
        upIP = new ImagePanel(imageUp, 2, 1);
        upIP.setBackground(Color.white);
        upIP.setBorder(BorderFactory.createEmptyBorder());
        upIP.setToolTipText("Up");
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
                int newOrder = Integer.parseInt(vOrTxt.getText());
                
                if (layout == 0)
                {
                    if (newOrder == maxNewOrder)
                    {
                        return;
                    }
                    newOrder = newOrder + 1;
                    
                    vOrTxt.setText(Integer.toString(newOrder));
                    
                }
                else
                {
                    if (newOrder == maxSameOrder)
                    {
                        return;
                    }
                    newOrder = newOrder + 1;
                    
                    vOrTxt.setText(Integer.toString(newOrder));
                }
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
        downIP.setToolTipText("Up");
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
                //Save();
                int newOrder = Integer.parseInt(vOrTxt.getText());
                
                if (newOrder == 1)
                {
                    return;
                }
                newOrder = newOrder - 1;
                
                vOrTxt.setText(Integer.toString(newOrder));
               
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
    private void InitComboBoxes()
    {
        String[] items = {"new window", "same window"};
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
              EnableButton();
            }
        });
        posCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
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
    }
    
    private void InitOrder()
    {
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs;
           
            rs = statement.executeQuery("select max(Tob_order) from IISC_COMPTYPE_DISPLAY where Tf_id=" + Tf_id + " and Tob_layout=0" );
            
            if (rs.next())
            {
                maxNewOrder = rs.getInt(1);
            }
        }
        catch(Exception e)
        {
        }
        
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs;
           
            rs = statement.executeQuery("select max(Tob_order) from IISC_COMPTYPE_DISPLAY where Tf_id=" + Tf_id + " and Tob_layout=1");
            
            if (rs.next())
            {
                maxSameOrder = rs.getInt(1);
            }
        }
        catch(Exception e)
        {
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
                
                oldOrder = rs.getInt("Tob_order");
                
                if(oldOrder == 0)
                {
                    if(layout == 0)
                    {         
                        maxNewOrder = maxNewOrder + 1;
                        vTf[2].setText("" + (maxNewOrder));
                    }
                    else
                    {
                        maxSameOrder = maxSameOrder + 1;
                        vTf[2].setText("" + (maxSameOrder));
                    }
                }
                else
                {
                    vTf[2].setText("" + oldOrder);
                }
                vTf[2].setEditable(false);
                vTf[2].setBackground(Color.white);
                
                posCombo.setSelectedIndex(rs.getInt("Tob_position"));
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
        vTf[0].setText(layCombo.getSelectedItem().toString());
        
        dLayCombo.setSelectedIndex(0);
        vTf[1].setText(dLayCombo.getSelectedItem().toString());
        vTf[2].setText("" + (maxNewOrder));
       
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
        
        vTf[0] = vLaTxt;
        vTf[1] = vDlTxt;
        vTf[2] = vOrTxt;
        vTf[3] = vPoTxt;
        vTf[4] = vPrTxt;
        vTf[5] = vXrTxt;
        vTf[6] = vYrTxt;
        vTf[7] = vSrTxt;
        vTf[8] = vDmTxt;
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
      
        int len = 9;
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
            pPanel.add(nTf[i], null);
            pPanel.add(vTf[i], null);
        }
        
        //pPanel.setPreferredSize(new Dimension(width - 3, 9*txtHeight));
    }
    
    public void EnableButton()
    {
        ob.btnApply.setEnabled(true);
        ob.btnSave.setEnabled(true);
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
    }
    
    public void nLaTxt_focusGained(FocusEvent e)
    {
        nTf[0].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[0].repaint();
        vTf[0].setBackground(new Color(173, 189, 133));
        vTf[0].repaint();
    }
    
    public void nLaTxt_focusLost(FocusEvent e)
    {
        nTf[0].setBorder(BorderFactory.createEmptyBorder());
        nTf[0].repaint();
        vTf[0].setBackground(Color.white);
        vTf[0].repaint();
    }
    
    public void vLaTxt_focusGained(FocusEvent e)
    {
        nTf[0].setBackground(new Color(173, 189, 133));
        nTf[0].repaint();
        
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
        nTf[0].repaint();
    }
    
    public void nDlTxt_focusGained(FocusEvent e)
    {
        nTf[1].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[1].repaint();
        vTf[1].setBackground(new Color(173, 189, 133));
        vTf[1].repaint();
    }
    
    public void nDlTxt_focusLost(FocusEvent e)
    {
        nTf[1].setBorder(BorderFactory.createEmptyBorder());
        nTf[1].repaint();
        vTf[1].setBackground(Color.white);
        vTf[1].repaint();
    }
    
    public void vDlTxt_focusGained(FocusEvent e)
    {
        nTf[1].setBackground(new Color(173, 189, 133));
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
        nTf[1].repaint();
    }
    
    public void nOrTxt_focusGained(FocusEvent e)
    {
        nTf[2].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[2].repaint();
        vTf[2].setBackground(new Color(173, 189, 133));
        vTf[2].repaint();
    }
    
    public void nOrTxt_focusLost(FocusEvent e)
    {
        nTf[2].setBorder(BorderFactory.createEmptyBorder());
        nTf[2].repaint();
        vTf[2].setBackground(Color.white);
        vTf[2].repaint();
    }
    
    public void vOrTxt_focusGained(FocusEvent e)
    {
        nTf[2].setBackground(new Color(173, 189, 133));
        nTf[2].repaint();
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
        vTf[3].setBackground(new Color(173, 189, 133));
        vTf[3].repaint();
    }
    
    public void nPoTxt_focusLost(FocusEvent e)
    {
        nTf[3].setBorder(BorderFactory.createEmptyBorder());
        nTf[3].repaint();
        vTf[3].setBackground(Color.white);
        vTf[3].repaint();
    }
    
    public void vPoTxt_focusGained(FocusEvent e)
    {
        nTf[3].setBackground(new Color(173, 189, 133));
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
        nTf[3].repaint();   
    }
    
    public void nPrTxt_focusGained(FocusEvent e)
    {
        nTf[4].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[4].repaint();
        vTf[4].setBackground(new Color(173, 189, 133));
        vTf[4].repaint();
    }
    
    public void nPrTxt_focusLost(FocusEvent e)
    {
        nTf[4].setBorder(BorderFactory.createEmptyBorder());
        nTf[4].repaint();
        vTf[4].setBackground(Color.white);
        vTf[4].repaint();
    }
    
    public void vPrTxt_focusGained(FocusEvent e)
    {
        nTf[4].setBackground(new Color(173, 189, 133));
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
        nTf[4].repaint();
    }
    
    public void nXrTxt_focusGained(FocusEvent e)
    {
        nTf[5].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[5].repaint();
        vTf[5].setBackground(new Color(173, 189, 133));
        vTf[5].repaint();
    }
    
    public void nXrTxt_focusLost(FocusEvent e)
    {
        nTf[5].setBorder(BorderFactory.createEmptyBorder());
        nTf[5].repaint();
        vTf[5].setBackground(Color.white);
        vTf[5].repaint();
    }
    
    public void vXrTxt_focusGained(FocusEvent e)
    {
          nXrTxt.setBackground(new Color(173, 189, 133));
          nXrTxt.repaint();
    }
    
    public void vXrTxt_focusLost(FocusEvent e)
    {
        nXrTxt.setBackground(Color.white);
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
    }
    
    public void nYrTxt_focusGained(FocusEvent e)
    {
        nTf[6].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[6].repaint();
        vTf[6].setBackground(new Color(173, 189, 133));
        vTf[6].repaint();
    }
    
    public void nYrTxt_focusLost(FocusEvent e)
    {
        nTf[6].setBorder(BorderFactory.createEmptyBorder());
        nTf[6].repaint();
        vTf[6].setBackground(Color.white);
        vTf[6].repaint();
    }
    
    public void vYrTxt_focusGained(FocusEvent e)
    {
        oldValue = vYrTxt.getText();
        nTf[6].setBackground(new Color(173, 189, 133));
        nTf[6].repaint();
    }
    
    public void vYrTxt_focusLost(FocusEvent e)
    {
        nTf[6].setBackground(Color.white);
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
    }
    
    public void nSrTxt_focusGained(FocusEvent e)
    {
        nTf[7].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[7].repaint();
        vTf[7].setBackground(new Color(173, 189, 133));
        vTf[7].repaint();
    }
    
    public void nSrTxt_focusLost(FocusEvent e)
    {
        nTf[7].setBorder(BorderFactory.createEmptyBorder());
        nTf[7].repaint();
        vTf[7].setBackground(Color.white);
        vTf[7].repaint();
    }
    
    public void vSrTxt_focusGained(FocusEvent e)
    {
        nTf[7].setBackground(new Color(173, 189, 133));
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
        nTf[7].repaint();
    }
    
    public void nDmTxt_focusGained(FocusEvent e)
    {
        nTf[8].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[8].repaint();
        vTf[8].setBackground(new Color(173, 189, 133));
        vTf[8].repaint();
    }
    
    public void nDmTxt_focusLost(FocusEvent e)
    {
        nTf[8].setBorder(BorderFactory.createEmptyBorder());
        nTf[8].repaint();
        vTf[8].setBackground(Color.white);
        vTf[8].repaint(); 
    }
    
    public void vDmTxt_focusGained(FocusEvent e)
    {
        nTf[8].setBackground(new Color(173, 189, 133));
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
    
    public void vDmTxt_focusLost(FocusEvent e)
    {
        nTf[8].setBackground(Color.white);
        nTf[8].repaint();
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
        nLaTxt.repaint();
    }
    
    public void dLayCombo_focusLost(FocusEvent e)
    {
        vDlTxt.setText(dLayCombo.getSelectedItem().toString());
        vDlTxt.removeAll();
        nDlTxt.setBackground(Color.white);
        nDlTxt.repaint();
    }
    
    public void posCombo_focusLost(FocusEvent e)
    {
        vPoTxt.setText(posCombo.getSelectedItem().toString());
        vPoTxt.removeAll(); 
        nPoTxt.setBackground(Color.white);
        nPoTxt.repaint();
        
    }
    
    public void relPosCombo_focusLost(FocusEvent e)
    {
        vPrTxt.setText(relPosCombo.getSelectedItem().toString());
        vPrTxt.removeAll(); 
        nPrTxt.setBackground(Color.white);
        nPrTxt.repaint();
        repaint();
    }
    
    public void searchCombo_focusLost(FocusEvent e)
    {
        vSrTxt.setText(searchCombo.getSelectedItem().toString());
        vSrTxt.removeAll(); 
        nSrTxt.setBackground(Color.white);
        nSrTxt.repaint();
        repaint();
    }
    
    public void delMassCombo_focusLost(FocusEvent e)
    {
        vDmTxt.setText(delMassCombo.getSelectedItem().toString());
        vDmTxt.removeAll(); 
        nDmTxt.setBackground(Color.white);
        nDmTxt.repaint();
        repaint();
    }
    
    public void layCombo_focusGained(FocusEvent e)
    {
        nLaTxt.setBackground(new Color(173, 189, 133));
        nLaTxt.repaint();
    }
    
    public void dLayCombo_focusGained(FocusEvent e)
    {
        nDlTxt.setBackground(new Color(173, 189, 133));
        nDlTxt.repaint();
    }
    
    public void posCombo_focusGained(FocusEvent e)
    {
        nPoTxt.setBackground(new Color(173, 189, 133));
        nPoTxt.repaint();
    }
    
    public void relPosCombo_focusGained(FocusEvent e)
    {
        nPrTxt.setBackground(new Color(173, 189, 133));
        nPrTxt.repaint();
    }
    
    public void searchCombo_focusGained(FocusEvent e)
    {
        nSrTxt.setBackground(new Color(173, 189, 133));
        nSrTxt.repaint();
    }
    
    public void delMassCombo_focusGained(FocusEvent e)
    {
        nDmTxt.setBackground(new Color(173, 189, 133));
        nDmTxt.repaint();
    }
    
    public void Update(int j, boolean update)
    {
         try
        {
            Statement statement = conn.createStatement();
            
            if (update)
            {
                String q = "update IISC_COMPTYPE_DISPLAY set Tob_layout=" + layCombo.getSelectedIndex() + ",Tob_data_layout=" + dLayCombo.getSelectedIndex() + ",Tob_order=" + vOrTxt.getText() + ",Tob_position=" + posCombo.getSelectedIndex() + ",Tob_position_relative=" + relPosCombo.getSelectedIndex() + ",Tob_x_relative=" + vXrTxt.getText() + ",Tob_y_relative=" + vYrTxt.getText() + ",Tob_search=" + searchCombo.getSelectedIndex() + ",Tob_del_masive=" + delMassCombo.getSelectedIndex();
                q = q + " where Tob_id=" + j;
                //System.out.println(q);
                statement.execute(q);
            }
            else
            {
                Tob_id = j;
                String q = "insert into IISC_COMPTYPE_DISPLAY(Tob_id,Tf_id,PR_id,Tob_layout,Tob_data_layout,Tob_order,Tob_position,Tob_position_relative,Tob_x_relative,Tob_y_relative,Tob_search,Tob_del_masive) values(" + Tob_id + "," + Tf_id + "," + PR_id + "," + layCombo.getSelectedIndex() + "," + dLayCombo.getSelectedIndex() + "," + vOrTxt.getText() + "," + posCombo.getSelectedIndex() + "," + relPosCombo.getSelectedIndex() + "," + vXrTxt.getText() + "," + vYrTxt.getText() + "," + searchCombo.getSelectedIndex() + "," + delMassCombo.getSelectedIndex() + ")";
                System.out.println(q);
                statement.execute(q);
            }
            
            //Update ordera 
            int newOrder = Integer.parseInt(vOrTxt.getText());
            
            //Ako se promijenio layout tj. ako je bio Same window a sad je NewWindow i viceversa
            if (layout != oldLayout)
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
            
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle.toString());
        }
    }
    
   private void relPosComboAction()
    {
        EnableButton();
        
        if (relPosCombo.getSelectedIndex() == 0)
        {
            vXrTxt.setEnabled(false);
            vYrTxt.setEnabled(false);
            nXrTxt.setEnabled(false);
            nYrTxt.setEnabled(false);
        }
        else
        {
            if (relPosCombo.getSelectedIndex() == 1)
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
            maxNewOrder = maxNewOrder + 1;
            maxSameOrder = maxSameOrder - 1;
            vTf[2].setText("" + maxNewOrder);
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
            maxNewOrder = maxNewOrder - 1;
            maxSameOrder = maxSameOrder + 1;
            vTf[2].setText("" + maxSameOrder);
            
            nPoTxt.setEnabled(true);
            vPoTxt.setEnabled(true);
            nPrTxt.setEnabled(false);
            vPrTxt.setEnabled(false);
            vXrTxt.setEnabled(false);
            nXrTxt.setEnabled(false);
            vYrTxt.setEnabled(false);
            nYrTxt.setEnabled(false);
        }
        
        if (layout == 0)
        {
            if (relPosCombo.getSelectedIndex() == 0)
            {
                vXrTxt.setEnabled(false);
                vYrTxt.setEnabled(false);
                nXrTxt.setEnabled(false);
                nYrTxt.setEnabled(false);
            }
            else
            {
                if (relPosCombo.getSelectedIndex() == 1)
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