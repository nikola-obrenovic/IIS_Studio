package iisc;

import iisc.PropertyScrollPane;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import java.util.Vector;
import java.io.*;
import javax.swing.JTextArea;
import java.awt.TextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.text.Caret;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.sql.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionListener;

public class TFProperties extends JDialog
{
    private JPanel pPanel;
    private JToolBar tb;
    private Connection conn;
    private int Tf_id;
    private IISFrameMain parent;
    
    private PropertyTextBox[] nTf = new PropertyTextBox[10];
    private JTextField[] vTf = new JTextField[10];
    
    private PropertyTextBox nNaTxt = new PropertyTextBox("Name");
    private PropertyTextBox nTiTxt = new PropertyTextBox("Title");
    private PropertyTextBox nCrTxt = new PropertyTextBox("Created");
    private PropertyTextBox nLmTxt = new PropertyTextBox("Last modified");
    private PropertyTextBox nFrTxt = new PropertyTextBox("Frequency");
    private PropertyTextBox nFuTxt = new PropertyTextBox("Frequency unit");
    private PropertyTextBox nReTxt = new PropertyTextBox("Response");
    private PropertyTextBox nRuTxt = new PropertyTextBox("Response unit");
    private PropertyTextBox nUsTxt = new PropertyTextBox("Usage");
    private PropertyTextBox nCoTxt = new PropertyTextBox("Considered in db design");
    
    private JTextField vNaTxt = new JTextField("");
    private JTextField vTiTxt = new JTextField("");
    private JTextField vCrTxt = new JTextField("");
    private JTextField vLmTxt = new JTextField("");
    private JTextField vFrTxt = new JTextField("");
    private JTextField vFuTxt = new JTextField("");
    private JTextField vReTxt = new JTextField("");
    private JTextField vRuTxt = new JTextField("");
    private JTextField vUsTxt = new JTextField("");
    private JTextField vCoTxt = new JTextField("");
    
    private JComboBox resCombo;
    private JComboBox freqCombo;
    private JComboBox usageCombo;
    private JComboBox consCombo;
    Integer[] useitems;
    
    private static ImageIcon imageSave = new ImageIcon(IISFrameMain.class.getResource("icons/save2.gif"));
    private static ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private JButton btnSave = new JButton();
    private JButton btnHelp = new JButton();
    private static final int txtHeight = 18;
    private boolean mDown = false;
    
    public TFProperties(IISFrameMain _parent, Connection _conn, int _Tf_id) 
    {
        super((JFrame)_parent, "Form Type Properties", true);
        conn = _conn;
        Tf_id = _Tf_id;
        parent = _parent;
        getContentPane().setLayout(null);
        
        setSize(new Dimension(332, 487));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        tb = new JToolBar();
        tb.setFont(new Font("Verdana", 0, 11));
        tb.setBackground(new Color(212,208,200));
        
        tb.setBounds(0, 0, 30, 487);
        //tb.setPreferredSize(new Dimension(30, 25));
        tb.setOrientation(1);
        tb.setFloatable(false);
        //tb.setMinimumSize(new Dimension(30, 30));
        
        pPanel = new JPanel();
        pPanel.setBounds(31, 0, 293, 487);
        pPanel.setBackground(SystemColor.textInactiveText);
        vLmTxt.setBackground(SystemColor.LIGHT_GRAY);
        vCrTxt.setBackground(SystemColor.LIGHT_GRAY);
        pPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        pPanel.setLayout(null);
        getContentPane().add(pPanel);
        getContentPane().add(tb);
        
        //Inicijalizacija dugmadi
        btnSave.setMaximumSize(new Dimension(30, 30));
        btnSave.setBackground(new Color(212, 208, 200));
        btnSave.setPreferredSize(new Dimension(25, 22));
        btnSave.setIcon(imageSave);
        btnSave.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        btnSave.setRolloverEnabled(true);
        btnSave.setToolTipText("Save changes");
        btnSave.setMinimumSize(new Dimension(21, 21));
        btnSave.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnSave_actionPerformed(e);
            }
        });
        tb.add(btnSave);
        
        btnHelp.setMaximumSize(new Dimension(30, 30));
        btnHelp.setBackground(new Color(212, 208, 200));
        btnHelp.setPreferredSize(new Dimension(25, 22));
        btnHelp.setIcon(imageHelp);
        btnHelp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        btnHelp.setRolloverEnabled(true);
        btnHelp.setToolTipText("Save changes");
        btnHelp.setMinimumSize(new Dimension(21, 21));
        btnHelp.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnSave_actionPerformed(e);
            }
        });
        tb.add(btnHelp);
        
        Assign();
        InitTextFields();
        InitTextFieldsPos();
        InitFromDataBase();
        InitComboBoxes();
        pPanel.revalidate();
        this.requestFocus();
        this.repaint();
    }
    
    private void InitComboBoxes()
    {
        usageCombo.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
                usageCombo_focusLost(e);
            }
            
            public void focusGained(FocusEvent e)
            {
                usageCombo_focusGained(e);
            }
            
        });
        usageCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              UsageComboChanhed();
            }
        });
        
        freqCombo.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
              freqCombo_focusLost(e);
            }
            
            public void focusGained(FocusEvent e)
            {
                freqCombo_focusGained(e);
            }
        });
        freqCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
            }
        });
        
        resCombo.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
              resCombo_focusLost(e);
            }
            
            public void focusGained(FocusEvent e)
            {
                resCombo_focusGained(e);
            }
            
        });
        resCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
            }
        });
        
        consCombo.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
              consCombo_focusLost(e);
            }
            
            public void focusGained(FocusEvent e)
            {
                consCombo_focusGained(e);
            }
            
        });
        consCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              EnableButton();
            }
        });
    }
    
    private void InitFromDataBase()
    {
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs;
           
            rs = statement.executeQuery("select * from IISC_FORM_TYPE where Tf_id=" + Tf_id );
            
            if (rs.next())
            {
                String[] items = {"sec." , "min." , "hour" , "day" , "week" , "month" , "year"};
                resCombo = new JComboBox(items);
                freqCombo = new JComboBox(items);
                
                String[] propmtuseitems = {"menu" , "program"};
                usageCombo = new JComboBox(propmtuseitems);
                useitems = new Integer[2];
                
                useitems[0] = new Integer(0);
                useitems[1] = new Integer(2);
                
                vTf[0].setText(rs.getString("Tf_mnem"));
                vTf[1].setText(rs.getString("Tf_title"));
                vTf[2].setText(rs.getString("Tf_crdate"));
                vTf[2].setEditable(false);
                vTf[3].setText(rs.getString("Tf_moddate"));
                vTf[3].setEditable(false);
                vTf[4].setText(rs.getString("Tf_freq"));
                
                String str = rs.getString("Tf_freq_unit");
                int selectedIn = 0;
                
                vTf[5].setText(str);
                for(int i = 0; i < items.length; i++)
                {
                    if (items[i].equals(str))
                    {
                        selectedIn = i;
                        break;
                    }
                }
                freqCombo.setSelectedIndex(selectedIn);
                
                vTf[6].setText(rs.getString("Tf_rest"));
                
                str = rs.getString("Tf_rest_unit");
                selectedIn = 0;
                
                vTf[7].setText(str);
                for(int i = 0; i < items.length; i++)
                {
                    if (items[i].equals(str))
                    {
                        selectedIn = i;
                        break;
                    }
                }
                resCombo.setSelectedIndex(selectedIn);
                
                int use = rs.getInt("Tf_use");
                
                if (use == 2)
                {
                    usageCombo.setSelectedIndex(1);
                    vTf[8].setText("menu");
                    
                    String[] propmtconsitems = {"yes" , "no"};
                    consCombo = new JComboBox(propmtconsitems);
                    vTf[9].setText("no");
                    consCombo.setSelectedIndex(1);
                    vCoTxt.setFocusable(false);
                    vCoTxt.setBackground(SystemColor.LIGHT_GRAY);
                    
                    
                }
                else
                {
                    if (use == 1)
                    {
                        usageCombo.setSelectedIndex(1);
                        vTf[8].setText("program");
                        String[] propmtconsitems = {"yes" , "no"};
                        consCombo = new JComboBox(propmtconsitems);
                        consCombo.setSelectedIndex(1);
                        vTf[9].setText("no");
                    }
                    else
                    {
                        usageCombo.setSelectedIndex(1);
                        vTf[8].setText("program");
                        String[] propmtconsitems = {"yes" , "no"};
                        consCombo = new JComboBox(propmtconsitems);
                        consCombo.setSelectedIndex(0);
                        vTf[9].setText("yes");
                    }
                }
            }
        }
        catch(Exception e)
        {
            return;
        }
    }
    private void Assign()
    {
        nTf[0] = nNaTxt;
        nTf[1] = nTiTxt;
        nTf[2] = nCrTxt;
        nTf[3] = nLmTxt;
        nTf[4] = nFrTxt;
        nTf[5] = nFuTxt;
        nTf[6] = nReTxt;
        nTf[7] = nRuTxt;
        nTf[8] = nUsTxt;
        nTf[9] = nCoTxt;
        
        vTf[0] = vNaTxt;
        vTf[1] = vTiTxt;
        vTf[2] = vCrTxt;
        vTf[3] = vLmTxt;
        vTf[4] = vFrTxt;
        vTf[5] = vFuTxt;
        vTf[6] = vReTxt;
        vTf[7] = vRuTxt;
        vTf[8] = vUsTxt;
        vTf[9] = vCoTxt;
    }
    
    private void InitTextFields()
    {
        
        nNaTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
              nNaTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
              nNaTxt_focusLost(e);
            }
          
        });
        
        
        vNaTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vNaTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                vNaTxt_focusLost(e);
            }
          
        });
        
        nTiTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
              nTiTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
              nTiTxt_focusLost(e);
            }
          
        });
        
        
        vTiTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vTiTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                vTiTxt_focusLost(e);
            }
          
        });
        
        nCrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nCrTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                nCrTxt_focusLost(e);
            }
          
        });
        
        
        vCrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vCrTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                vCrTxt_focusLost(e);
            }
          
        });
        
        nLmTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nLmTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                nLmTxt_focusLost(e);
            }
          
        });
        
        
        vLmTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vLmTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                vLmTxt_focusLost(e);
            }
          
        });
        
        nFrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nFrTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                nFrTxt_focusLost(e);
            }
          
        });
        
        
        vFrTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vFrTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                vFrTxt_focusLost(e);
            }
          
        });
        
        nFuTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nFuTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                nFuTxt_focusLost(e);
            }
          
        });
        
        
        vFuTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vFuTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                vFuTxt_focusLost(e);
            }
          
        });
        
        nReTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nReTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                nReTxt_focusLost(e);
            }
          
        });
        
        
        vReTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vReTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                vReTxt_focusLost(e);
            }
          
        });
        
        nRuTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nRuTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                nRuTxt_focusLost(e);
            }
          
        });
        
        
        vRuTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vRuTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                vRuTxt_focusLost(e);
            }
          
        });
        
        nUsTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nUsTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                nUsTxt_focusLost(e);
            }
          
        });
        
        
        vUsTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vUsTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                vUsTxt_focusLost(e);
            }
          
        });
        
        nCoTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                nCoTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                nCoTxt_focusLost(e);
            }
          
        });
        
        
        vCoTxt.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(FocusEvent e)
            {
                vCoTxt_focusGained(e);
            }
            
            public void focusLost(FocusEvent e)
            {
                vCoTxt_focusLost(e);
            }
          
        });
    }
    
    private void InitTextFieldsPos()
    {
        int width = pPanel.getWidth();
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
            pPanel.add(nTf[i], null);
            pPanel.add(vTf[i], null);
        }
    }
    
    public void EnableButton()
    {
    
    }
    
    public void nNaTxt_focusGained(FocusEvent e)
    {
        nTf[0].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[0].repaint();
        vTf[0].setBackground(new Color(173, 189, 133));
        vTf[0].repaint();
    }
    
    public void nNaTxt_focusLost(FocusEvent e)
    {
        nTf[0].setBorder(BorderFactory.createEmptyBorder());
        nTf[0].repaint();
        vTf[0].setBackground(Color.white);
        vTf[0].repaint();
    }
    
    public void vNaTxt_focusGained(FocusEvent e)
    {
        nTf[0].setBackground(new Color(173, 189, 133));
        nTf[0].repaint();
    }
    
    public void vNaTxt_focusLost(FocusEvent e)
    {
        nTf[0].setBackground(Color.white);
        nTf[0].repaint();
    }
    
    public void nTiTxt_focusGained(FocusEvent e)
    {
        nTf[1].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[1].repaint();
        vTf[1].setBackground(new Color(173, 189, 133));
        vTf[1].repaint();
    }
    
    public void nTiTxt_focusLost(FocusEvent e)
    {
        nTf[1].setBorder(BorderFactory.createEmptyBorder());
        nTf[1].repaint();
        vTf[1].setBackground(Color.white);
        vTf[1].repaint();
    }
    
    public void vTiTxt_focusGained(FocusEvent e)
    {
        nTf[1].setBackground(new Color(173, 189, 133));
        nTf[1].repaint();
    }
    
    public void vTiTxt_focusLost(FocusEvent e)
    {
        nTf[1].setBackground(Color.white);
        nTf[1].repaint();
    }
    
    public void nCrTxt_focusGained(FocusEvent e)
    {
        nTf[2].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[2].repaint();
        vTf[2].setBackground(new Color(173, 189, 133));
        vTf[2].repaint();
    }
    
    public void nCrTxt_focusLost(FocusEvent e)
    {
        nTf[2].setBorder(BorderFactory.createEmptyBorder());
        nTf[2].repaint();
        vTf[2].setBackground(SystemColor.LIGHT_GRAY);
        vTf[2].repaint();
    }
    
    public void vCrTxt_focusGained(FocusEvent e)
    {
        nTf[2].setBackground(new Color(173, 189, 133));
        nTf[2].repaint();
    }
    
    public void vCrTxt_focusLost(FocusEvent e)
    {
        nTf[2].setBackground(Color.white);
        nTf[2].repaint();
    }
    
    public void nLmTxt_focusGained(FocusEvent e)
    {
        nTf[3].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[3].repaint();
        vTf[3].setBackground(new Color(173, 189, 133));
        vTf[3].repaint();
    }
    
    public void nLmTxt_focusLost(FocusEvent e)
    {
        nTf[3].setBorder(BorderFactory.createEmptyBorder());
        nTf[3].repaint();
        vTf[3].setBackground(SystemColor.LIGHT_GRAY);
        vTf[3].repaint();
    }
    
    public void vLmTxt_focusGained(FocusEvent e)
    {
        nTf[3].setBackground(new Color(173, 189, 133));
        nTf[3].repaint();
    }
    
    public void vLmTxt_focusLost(FocusEvent e)
    {
        nTf[3].setBackground(Color.white);
        nTf[3].repaint();   
    }
    
    public void nFrTxt_focusGained(FocusEvent e)
    {
        nTf[4].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[4].repaint();
        vTf[4].setBackground(new Color(173, 189, 133));
        vTf[4].repaint();
    }
    
    public void nFrTxt_focusLost(FocusEvent e)
    {
        nTf[4].setBorder(BorderFactory.createEmptyBorder());
        nTf[4].repaint();
        vTf[4].setBackground(Color.white);
        vTf[4].repaint();
    }
    
    public void vFrTxt_focusGained(FocusEvent e)
    {
        nTf[4].setBackground(new Color(173, 189, 133));
        nTf[4].repaint(); 
    }
    
    public void vFrTxt_focusLost(FocusEvent e)
    {
        nTf[4].setBackground(Color.white);
        nTf[4].repaint();
    }
    
    public void nFuTxt_focusGained(FocusEvent e)
    {
        nTf[5].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[5].repaint();
        vTf[5].setBackground(new Color(173, 189, 133));
        vTf[5].repaint();
    }
    
    public void nFuTxt_focusLost(FocusEvent e)
    {
        nTf[5].setBorder(BorderFactory.createEmptyBorder());
        nTf[5].repaint();
        vTf[5].setBackground(Color.white);
        vTf[5].repaint();
    }
    
    public void vFuTxt_focusGained(FocusEvent e)
    {
          nFuTxt.setBackground(new Color(173, 189, 133));
          nFuTxt.repaint();
          
          freqCombo.setSize(vFuTxt.getWidth() , vFuTxt.getHeight() );
          freqCombo.setBounds(0, 0, vFuTxt.getWidth(), vFuTxt.getHeight());
         
          vFuTxt.add(freqCombo, null);
          vFuTxt.repaint();
         
          freqCombo.repaint();
          freqCombo.revalidate();
          repaint();
          
          
          freqCombo.setFocusable(true);
          freqCombo.requestFocus();
          freqCombo.setPopupVisible(true);

    }
    
    public void vFuTxt_focusLost(FocusEvent e)
    {
        nFuTxt.setBackground(Color.white);
        nFuTxt.repaint();
    }
    
    public void nReTxt_focusGained(FocusEvent e)
    {
        nTf[6].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[6].repaint();
        vTf[6].setBackground(new Color(173, 189, 133));
        vTf[6].repaint();
    }
    
    public void nReTxt_focusLost(FocusEvent e)
    {
        nTf[6].setBorder(BorderFactory.createEmptyBorder());
        nTf[6].repaint();
        vTf[6].setBackground(Color.white);
        vTf[6].repaint();
    }
    
    public void vReTxt_focusGained(FocusEvent e)
    {
        nTf[6].setBackground(new Color(173, 189, 133));
        nTf[6].repaint();
    }
    
    public void vReTxt_focusLost(FocusEvent e)
    {
        nTf[6].setBackground(Color.white);
        nTf[6].repaint();
    }
    
    public void nRuTxt_focusGained(FocusEvent e)
    {
        nTf[7].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[7].repaint();
        vTf[7].setBackground(new Color(173, 189, 133));
        vTf[7].repaint();
    }
    
    public void nRuTxt_focusLost(FocusEvent e)
    {
        nTf[7].setBorder(BorderFactory.createEmptyBorder());
        nTf[7].repaint();
        vTf[7].setBackground(Color.white);
        vTf[7].repaint();
    }
    
    public void vRuTxt_focusGained(FocusEvent e)
    {
        nTf[7].setBackground(new Color(173, 189, 133));
        nTf[7].repaint();
        
        resCombo.setSize(vRuTxt.getWidth() , vRuTxt.getHeight() );
        resCombo.setBounds(0, 0, vRuTxt.getWidth(), vRuTxt.getHeight());
       
        vRuTxt.add(resCombo, null);
        vRuTxt.repaint();
       
        resCombo.repaint();
        resCombo.revalidate();
        repaint();
        
        
        resCombo.setFocusable(true);
        resCombo.requestFocus();
        resCombo.setPopupVisible(true);
    }
    
    public void vRuTxt_focusLost(FocusEvent e)
    {
        nTf[7].setBackground(Color.white);
        nTf[7].repaint();
    }
    
    public void nUsTxt_focusGained(FocusEvent e)
    {
        nTf[8].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[8].repaint();
        vTf[8].setBackground(new Color(173, 189, 133));
        vTf[8].repaint();
    }
    
    public void nUsTxt_focusLost(FocusEvent e)
    {
        nTf[8].setBorder(BorderFactory.createEmptyBorder());
        nTf[8].repaint();
        vTf[8].setBackground(Color.white);
        vTf[8].repaint(); 
    }
    
    public void vUsTxt_focusGained(FocusEvent e)
    {
        nTf[8].setBackground(new Color(173, 189, 133));
        nTf[8].repaint(); 
        
        usageCombo.setSize(vUsTxt.getWidth() , vUsTxt.getHeight() );
        usageCombo.setBounds(0, 0, vUsTxt.getWidth(), vUsTxt.getHeight());
       
        vUsTxt.add(usageCombo, null);
        vUsTxt.repaint();
        usageCombo.revalidate();
        repaint();
        
        
        usageCombo.setFocusable(true);
        usageCombo.requestFocus();
        usageCombo.setPopupVisible(true);
        usageCombo.repaint();
    }
    
    public void vUsTxt_focusLost(FocusEvent e)
    {
        nTf[8].setBackground(Color.white);
        nTf[8].repaint();
    }
    
    public void nCoTxt_focusGained(FocusEvent e)
    {
        nTf[9].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        nTf[9].repaint();
        vTf[9].setBackground(new Color(173, 189, 133));
        vTf[9].repaint();
    }
    
    public void nCoTxt_focusLost(FocusEvent e)
    {
        nTf[9].setBorder(BorderFactory.createEmptyBorder());
        nTf[9].repaint();
        
        if (vTf[9].isFocusable())
        {
            vTf[9].setBackground(Color.white);
            vTf[9].repaint(); 
        }
        else
        {
            vTf[9].setBackground(SystemColor.lightGray);
            vTf[9].repaint();
        }
    }
    
    public void vCoTxt_focusGained(FocusEvent e)
    {
        nTf[9].setBackground(new Color(173, 189, 133));
        nTf[9].repaint();
        
        consCombo.setSize(vCoTxt.getWidth() - 1 , vCoTxt.getHeight() - 1 );
        consCombo.setBounds(0, 0, vCoTxt.getWidth(), vCoTxt.getHeight());
       
        vCoTxt.add(consCombo, null);
        vCoTxt.repaint();
       
        consCombo.repaint();
        consCombo.revalidate();
        repaint();
        
        
        consCombo.setFocusable(true);
        consCombo.requestFocus();
        consCombo.setPopupVisible(true);
    }
    
    public void vCoTxt_focusLost(FocusEvent e)
    {
        nTf[9].setBackground(Color.white);
        nTf[9].repaint();
    }
    
    /*************************************************************/
    /********       Dogadjaji za combo boxeve             ********/
    /*************************************************************/
    public void usageCombo_focusLost(FocusEvent e)
    {
        vUsTxt.setText(usageCombo.getSelectedItem().toString());
        vUsTxt.removeAll();
        vUsTxt.revalidate();
        vUsTxt.repaint();
        nUsTxt.setBackground(Color.white);
        nUsTxt.repaint();
    }
    
    public void freqCombo_focusLost(FocusEvent e)
    {
        vFuTxt.setText(freqCombo.getSelectedItem().toString());
        vFuTxt.removeAll();
        nFuTxt.setBackground(Color.white);
        nFuTxt.repaint();
    }
    
    public void resCombo_focusLost(FocusEvent e)
    {
        vRuTxt.setText(resCombo.getSelectedItem().toString());
        vRuTxt.removeAll(); 
        nRuTxt.setBackground(Color.white);
        nRuTxt.repaint();
        
    }
    
    public void consCombo_focusLost(FocusEvent e)
    {
        vCoTxt.setText(consCombo.getSelectedItem().toString());
        vCoTxt.removeAll(); 
        nCoTxt.setBackground(Color.white);
        nCoTxt.repaint();
        repaint();
    }
    
    public void usageCombo_focusGained(FocusEvent e)
    {
        nUsTxt.setBackground(new Color(173, 189, 133));
        nUsTxt.repaint();
    }
    
    public void freqCombo_focusGained(FocusEvent e)
    {
        nFuTxt.setBackground(new Color(173, 189, 133));
        nFuTxt.repaint();
    }
    
    public void resCombo_focusGained(FocusEvent e)
    {
        nRuTxt.setBackground(new Color(173, 189, 133));
        nRuTxt.repaint();
    }
    
    public void consCombo_focusGained(FocusEvent e)
    {
        nCoTxt.setBackground(new Color(173, 189, 133));
        nCoTxt.repaint();
    }
    
    
    /*********************************************************************/
    /**************         Specifikumi nekih kontrola      **************/
    /*********************************************************************/
    private void UsageComboChanhed()
    {
        if(usageCombo.getSelectedIndex() == 1)
        {
            vCoTxt.setFocusable(true);
            vCoTxt.setBackground(Color.white);
        }
        else
        {
            vCoTxt.setFocusable(false);
            vCoTxt.setBackground(SystemColor.LIGHT_GRAY);
        }
    }
    
    public void btnSave_actionPerformed(ActionEvent e)
    {
        
    }
    
}