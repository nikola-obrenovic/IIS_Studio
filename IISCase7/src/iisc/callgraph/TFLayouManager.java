package iisc.callgraph;

import iisc.IISFrameMain;

import iisc.lang.ImagePanel;

import iisc.tflayoutmanager.LayoutPanel;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Vector;
import javax.swing.JDialog;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TFLayouManager extends JDialog 
{
    private int width;
    private int height;
    public IISFrameMain parent;
    public Connection conn;
    public int PR_id;
    public int Tf_id;
    private JPanel tbPanel;
    public ImagePanel pSave;
    
    public JPanel layPanel;
    private JScrollPane sp;
   
    public Vector tobVec;
    public Vector propVec;
    
    
    /*private DragSource ds;
    private StringSelection transferable = new StringSelection("");
    private DragSource ds2;*/
    public boolean dirty = false;
    private JPanel tp;
    private JPanel eastPanel;
    private JPanel resPanel;
    private CrossPanel cp;
    private JPanel crpanel;
    private static ImageIcon imageSave = new ImageIcon(IISFrameMain.class.getResource("icons/save2.gif"));
    private static ImageIcon imageObj = new ImageIcon(IISFrameMain.class.getResource("icons/object.gif"));	
    
    public JList tobList;
    private JScrollPane spList;
    
    private JScrollPane spProp;
    private JPanel propPanel;
    private DefaultListModel tobListM;
    private int oldX;
    private int oldY;
    public Vector orderVec;
    public Vector tobPanVec;
    
    public TFLayouManager(int _width, int _height, IISFrameMain _parent, Connection _conn, int _Tf_id, int _PR_id)
    {
        super((Frame)_parent, "Layout Editor", true);
        width = _width;
        height = _height;
        parent = _parent;
        conn = _conn;
        PR_id = _PR_id;
        Tf_id = _Tf_id;
        
        tobVec = new Vector();
        propVec = new Vector();
        
        setSize(width, height);
        setFont(new Font("Dialog", 0, 1));
        setSize(new Dimension(700, 500));
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        
        getContentPane().setBackground(new Color(212,208,200));
        setResizable(true);  
        
        //this.setMaximum(true);
        setTitle("Calling Graph");
        setBounds(0,0,parent.desktop.getWidth(),parent.desktop.getHeight());
        //setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        setFont(new Font("Dialog", 0, 1));
        setSize(new Dimension(700, 500));
        orderVec = new Vector();
        tobPanVec = new Vector();
        
        /*InitMenu();
        InitToolBar();
        */
        //Inicijalizacija DrawingPanela 
        /*layPanel = new LayoutPanel(this);
        layPanel.setLayout(null);
        layPanel.setSize(1000,1000);
        layPanel.setBackground(Color.white);
        layPanel.setPreferredSize(new Dimension(1000, 1000));*/
        
        //Inicjalizacija ScrollPane
        sp = new JScrollPane(layPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //sp.setBounds(0, 0, width - 10, height - 35 );
        sp.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        getContentPane().add(sp, BorderLayout.CENTER);
        
        InitToolBar();
        
        InitList();
        
        repaint();
        
    }
    
    private void InitList()
    {
        //Inicijalizacija toolbara 
       
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id=" + Tf_id);
            
            String tobMnem;
            TobDispProperties tdp;
            
            tobListM = new DefaultListModel();
            propVec = new Vector();
            int i = 0;
            
            while(rs.next())
            {
                tobMnem = rs.getString("Tob_mnem");
                
                tobListM.addElement(tobMnem);
                tdp = new TobDispProperties(parent,230,162,rs.getInt("Tob_id"),Tf_id,PR_id,conn, i);
                propVec.add(tdp);
                i = i + 1;
            }
            
            
            statement.close();
        }
        catch(SQLException e)
        {
            
        }
        tobList = new JList(tobListM);
        
        tobList.setCellRenderer(new CustomCellRenderer());
        
        tobList.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() > 1)
                {
                    //EditAction();
                }
                
                if((e.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
                {
                    //System.out.println("e.getX():" + e.getX() + "e.getY()" + e.getY());
                    try 
                    {
                        //try{tobList.sets.setSelectionPath(grTree.getPathForLocation (e.getX(), e.getY()));}catch(Exception exp){}
                        //pop.show(cTree, e.getX() + 10, /*parent.y + parent.mHeight + parent.mCTCHeight +*/ e.getY());
                    }
                    catch(Exception ex)
                    {
                        System.out.println("E:x" + ex.toString());
                    }
                }
            }
        });
        
        spList = new JScrollPane(tobList);
        spList.setBackground(new Color(212, 208, 200));
        spList.setFont(new Font("Verdana", 0, 11));
        spList.setPreferredSize(new Dimension(230, 125)); 
        
        
        tp = new JPanel(new BorderLayout());
        tp.setMaximumSize(new Dimension(150,25));
        tp.setPreferredSize(new Dimension(230, 25)); 
        tp.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        getContentPane().add(tp, BorderLayout.WEST);
        
        eastPanel = new JPanel(new BorderLayout());
        eastPanel.setMaximumSize(new Dimension(230,125));
        eastPanel.setPreferredSize(new Dimension(230, 125)); 
        eastPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        tp.add(eastPanel, BorderLayout.CENTER);
        
        eastPanel.add(spList, BorderLayout.CENTER);
        crpanel = new JPanel(new BorderLayout());
        crpanel.setPreferredSize(new Dimension(225, 19));
        crpanel.setMaximumSize(new Dimension(230,19));
        crpanel.setBackground(new Color(212, 208, 200));
        eastPanel.add(crpanel, BorderLayout.NORTH);
        
        cp = new CrossPanel(19, 19);
        //cp.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        cp.setPreferredSize(new Dimension(19, 19)); 
        cp.setBackground(new Color(212, 208, 200));
        cp.setMaximumSize(new Dimension(19,19)); 
        crpanel.add(cp, BorderLayout.EAST);
        cp.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                cp.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
                cp.setBorder(BorderFactory.createEmptyBorder());
            }
            
            public void mouseClicked(MouseEvent e)
            {
                //Save();
                tp.setPreferredSize(new Dimension(0, 0)); 
                tp.revalidate();
                //revalidate();
                repaint();
            }
            
            public void mouseEntered(MouseEvent e)
            {
                cp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            
            public void mouseExited(MouseEvent e)
            {
                cp.setBorder(BorderFactory.createEmptyBorder());
            }
            
        });
        
        resPanel = new JPanel();
        resPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        //cp.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        resPanel.setPreferredSize(new Dimension(5,30)); 
        resPanel.setBackground(new Color(212, 208, 200));
        resPanel.setMaximumSize(new Dimension(5,30)); 
        resPanel.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        
        resPanel.addMouseListener(new MouseAdapter()
        {
            
            public void mousePressed(MouseEvent e)
            {
                 //System.out.println("Press + e.getX():" + e.getX() + "e.getY():" + e.getY());
                 oldX = e.getX();
                 oldY = e.getY();
                 
            }
            public void mouseReleased(MouseEvent e)
            {
            
            }
           
        });
        
        
        resPanel.addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                //System.out.println("Offset" + (e.getX() - oldX));
                tp.setPreferredSize(new Dimension(tp.getWidth() + (e.getX() - oldX),tp.getHeight()));
                //oldX = e.getX();
                tp.revalidate();
                //revalidate();
            }
            
        });
        
        tp.add(resPanel, BorderLayout.EAST);
        
        
        propPanel = new JPanel(new BorderLayout());
        propPanel.setPreferredSize(new Dimension(230, 162)); 
        propPanel.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(ComponentEvent e)
            {
                int width = propPanel.getWidth();
                
                for(int i = 0; i < propVec.size(); i++)
                {
                    ((TobDispProperties)propVec.get(i)).Resize(width);
                }
            }
        });
        TobDispProperties tdp;
        
        if(propVec.size() > 0)
        {
            tdp = (TobDispProperties)propVec.get(0);
            propPanel.setPreferredSize(new Dimension(tdp.getWidth(), tdp.getHeight()));
            propPanel.add(tdp);
        }
        
        
        spProp = new JScrollPane(propPanel);
        spProp.setBackground(new Color(212, 208, 200));
        spProp.setFont(new Font("Verdana", 0, 11));
        spProp.setPreferredSize(new Dimension(230, 165)); 
        spProp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        spProp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        eastPanel.add(spProp, BorderLayout.SOUTH);
        
        if (tobListM.getSize() > 0)
        {
            tobList.setSelectedIndex(0);
        }
        tobList.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                ChangeSelectedPanel();
            }
        });
        if (tobListM.getSize() > 0)
        {
            tobList.setSelectedIndex(0);
        }
    }
    
    private void ChangeSelectedPanel()
    {
        int index = tobList.getSelectedIndex();
        
        if (index > -1)
        {
            propPanel.removeAll();
            propPanel.add((TobDispProperties)propVec.get(index));
            propPanel.revalidate();
            propPanel.repaint();
        }
        
        
    }
    
    private void InitToolBar()
    {
        //Inicijalizacija toolbara 
        tbPanel = new JPanel(null);
        tbPanel.setBackground(new Color(212, 208, 200));
        tbPanel.setFont(new Font("Verdana", 0, 11));
        tbPanel.setMaximumSize(new Dimension(30,25));
        tbPanel.setPreferredSize(new Dimension(30, 25));        
        tbPanel.setMinimumSize(new Dimension(30, 25));
        getContentPane().add(tbPanel, BorderLayout.NORTH);
        
        pSave = new ImagePanel(imageSave,0,0);
        pSave.setBackground(new Color(212, 208, 200));
        pSave.setBorder(BorderFactory.createEmptyBorder());
        pSave.setToolTipText("Edit");
        pSave.setBounds(5,2,22,22);
        pSave.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                pSave.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
                pSave.setBorder(BorderFactory.createEmptyBorder());
            }
            
            public void mouseClicked(MouseEvent e)
            {
                //Save();
            }
            
            public void mouseEntered(MouseEvent e)
            {
                pSave.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            
            public void mouseExited(MouseEvent e)
            {
                pSave.setBorder(BorderFactory.createEmptyBorder());
            }
            
        });
        tbPanel.add(pSave);
        
    }
    public class CrossPanel extends JPanel
    {
        int width;
        int height;
      
        public CrossPanel(int p_width, int p_height)
        {
            super();
            width = p_width;
            height = p_height;
        }
      
        public void paint(Graphics g)
        {
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g;
            
            g2d.setStroke(new BasicStroke(2f));				
            g2d.drawLine(6, 6, width - 6, height - 6);
            g2d.drawLine(width - 6, 6, 6, height - 6);
        }
    } 
    
    
    public class TobDispProperties extends JPanel 
    {
        int width;
        int Tob_id;
        int Tf_id;
        int PR_id;
        Connection conn;
        private IISFrameMain parent;
        
        private PropertyTextBox[] nTf = new PropertyTextBox[10];
        private JTextField[] vTf = new JTextField[10];
        
        public PropertyTextBox nLaTxt = new PropertyTextBox("Layout");
        public PropertyTextBox nDlTxt = new PropertyTextBox("Data Layout");
        public PropertyTextBox nOrTxt = new PropertyTextBox("Order");
        public PropertyTextBox nPoTxt = new PropertyTextBox("Position");
        public PropertyTextBox nPrTxt = new PropertyTextBox("Position Relative");
        public PropertyTextBox nXrTxt = new PropertyTextBox("X Relative");
        public PropertyTextBox nYrTxt = new PropertyTextBox("Y Relative");
        public PropertyTextBox nSrTxt = new PropertyTextBox("Search");
        public PropertyTextBox nDmTxt = new PropertyTextBox("Delete Massive");
        
        public JTextField vLaTxt = new PropertyTextBox("");
        public JTextField vDlTxt = new PropertyTextBox("");
        public JTextField vOrTxt = new PropertyTextBox("");
        public JTextField vPoTxt = new PropertyTextBox("");
        public JTextField vPrTxt = new PropertyTextBox("");
        public JTextField vXrTxt = new PropertyTextBox("");
        public JTextField vYrTxt = new PropertyTextBox("");
        public JTextField vSrTxt = new PropertyTextBox("");
        public JTextField vDmTxt = new PropertyTextBox("");
        
        public JComboBox layCombo;
        public JComboBox dLayCombo;
        public JComboBox posCombo;
        public JComboBox relPosCombo;
        public JComboBox searchCombo;
        public JComboBox delMassCombo;
        
        private JButton btnSave = new JButton();
        private JButton btnHelp = new JButton();
        private static final int txtHeight = 18;
        public int index;
    
        public TobDispProperties(IISFrameMain _parent, int _width, int height, int _Tob_id, int _Tf_id, int _PR_id, Connection _conn, int _index)
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
            
            Assign();
            //InitTextFieldsPos();
           
            
            InitTextFields();
            InitComboBoxes();
            InitFromDataBase();
            
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
            
            height = txtHeight * 9;
          
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
            
            height = txtHeight * 9;
          
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
                add(nTf[i], null);
                add(vTf[i], null);
            }
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
            
            layCombo.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                  //UsageComboChanhed();
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
            
            dLayCombo.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                  EnableButton();
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
            posCombo.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                  EnableButton();
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
            relPosCombo.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                  EnableButton();
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
            searchCombo.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    EnableButton();
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
            delMassCombo.addActionListener(new ActionListener()
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
               
                rs = statement.executeQuery("select * from IISC_COMPTYPE_DISPLAY where Tob_id=" + Tob_id );
                
                if (rs.next())
                {
                    
                    layCombo.setSelectedIndex(rs.getInt("Tob_layout"));
                    vTf[0].setText(layCombo.getSelectedItem().toString());
                    
                    dLayCombo.setSelectedIndex(rs.getInt("Tob_data_layout"));
                    vTf[1].setText(dLayCombo.getSelectedItem().toString());
                    vTf[2].setText("" + rs.getInt("Tob_order"));
                   
                   
                    posCombo.setSelectedIndex(rs.getInt("Tob_position"));
                    vTf[3].setText(posCombo.getSelectedItem().toString());
                    
                    relPosCombo.setSelectedIndex(rs.getInt("Tob_position_relative"));
                    vTf[4].setText(relPosCombo.getSelectedItem().toString());
                    
                   
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
            
            height = txtHeight * 9;
          
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
                add(nTf[i], null);
                add(vTf[i], null);
            }
            
            //pPanel.setPreferredSize(new Dimension(width - 3, 9*txtHeight));
        }
        
        public void EnableButton()
        {
            /*ob.btnApply.setEnabled(true);
            ob.btnSave.setEnabled(true);*/
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
            nTf[0].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
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
            nTf[1].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
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
            nTf[2].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
            nTf[2].repaint();
            vTf[2].setBackground(Color.white);
            vTf[2].repaint();
        }
        
        public void vOrTxt_focusGained(FocusEvent e)
        {
            nTf[2].setBackground(new Color(173, 189, 133));
            nTf[2].repaint();
        }
        
        public void vOrTxt_focusLost(FocusEvent e)
        {
            nTf[2].setBackground(Color.white);
            nTf[2].repaint();
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
            nTf[3].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
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
            nTf[4].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
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
            nTf[5].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
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
            nTf[6].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
            nTf[6].repaint();
            vTf[6].setBackground(Color.white);
            vTf[6].repaint();
        }
        
        public void vYrTxt_focusGained(FocusEvent e)
        {
            nTf[6].setBackground(new Color(173, 189, 133));
            nTf[6].repaint();
        }
        
        public void vYrTxt_focusLost(FocusEvent e)
        {
            nTf[6].setBackground(Color.white);
            nTf[6].repaint();
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
            nTf[7].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
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
            nTf[8].setBorder((Border) new EmptyBorder(new Insets(2, 4 , 2, 1)));
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
                    //System.out.println(q);
                    statement.execute(q);
                }
                       
            }
            catch(SQLException sqle)
            {
                System.out.println(sqle.toString());
            }
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
    
    private class CustomCellRenderer extends  DefaultListCellRenderer  implements ListCellRenderer<Object> 
    {
        
        
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) 
        {
            JLabel label =(JLabel)super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);

            String s = value.toString();
            label.setText(s);
            label.setIcon(imageObj);
            
            if(isSelected)
            {
                label.setBackground(new Color(173, 189, 133));
                label.setForeground(Color.black);
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
}