package iisc.tflayoutmanager;

import iisc.IISFrameMain;
//import iisc.ImagePanel;
import iisc.PTree;
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
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.DragSource;
import java.awt.image.ImageObserver;
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
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

public class TFLayoutManager extends JDialog
{
    private int width;
    private int height;
    public IISFrameMain parent;
    public Connection conn;
    public int PR_id;
    public int Tf_id;
    private JPanel tbPanel;
    public ImagePanel pSave;
    public int maxNewOrder = 0;
    public int maxSameOrder = 0;
    
    public LayoutPanel layPanel;
    private JScrollPane sp;
    private int oldSelected = -5;
    
    public Vector tobVec;
    public Vector propVec;
    public Vector tobPanelVec;
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
    public Vector tobPanVec;
    public boolean dirty = false;
    public int option = 0;

    public TFLayoutManager(int _width, int _height, IISFrameMain _parent, Connection _conn, int _Tf_id, int _PR_id)
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
        
        setBounds(0,0,parent.desktop.getWidth(),parent.desktop.getHeight());
        //setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        setFont(new Font("Dialog", 0, 1));
        setSize(new Dimension(700, 500));
        tobPanVec = new Vector();

        layPanel = new LayoutPanel(this);
        layPanel.setLayout(null);
        layPanel.setSize(1000,1000);
        layPanel.setBackground(Color.white);
        layPanel.setPreferredSize(new Dimension(1000, 1000));

        //Inicjalizacija ScrollPane
        sp = new JScrollPane(layPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //sp.setBounds(0, 0, width - 10, height - 35 );
        sp.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        getContentPane().add(sp, BorderLayout.CENTER);

        //InitOrder();
        InitToolBar();

        InitList();
        InitOrder();
        tobList.getSelectionModel().setSelectionInterval(0, 0);
        layPanel.setSelected(0);
        
        repaint();
        //revalidate();
        
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
              this_windowClosing(e);
            }
        });
        
        
    }

    private void InitList()
    {
        //Inicijalizacija liste
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where TF_id=" + Tf_id + " order by Tob_superord");

            String tobMnem;
            TobDispProperties tdp;

            tobListM = new DefaultListModel();
            propVec = new Vector();
            tobPanelVec = new Vector();
            
            int i = 0;
            TobPanel tobP; 
            while(rs.next())
            {
                tobMnem = rs.getString("Tob_mnem");

                tobListM.addElement(tobMnem);
                tdp = new TobDispProperties(parent,230,180,rs.getInt("Tob_id"),Tf_id,PR_id,conn, i, this);
                propVec.add(tdp);
                
                tobP = new TobPanel(tobMnem, 0, this, i);
                tobPanelVec.add(tobP);
                i = i + 1;
            }


            statement.close();
        }
        catch(SQLException e)
        {

        }
        
        TobDispProperties tdp;
        TobPanel tobP;
        
        for(int j = 0; j < propVec.size(); j++)
        {
            tdp = (TobDispProperties)propVec.get(j);
            tobP = (TobPanel)tobPanelVec.get(j);
            
            //Racunam x i y ..tj gdje ce na panelu da se prikaze
            if (tdp.layCombo.getSelectedIndex() == 0)
            {   
                
                if (tdp.relPosCombo.getSelectedIndex() == 1)
                {
                    tobP.x = LayoutPanel.boundX;
                    tobP.y = LayoutPanel.boundY;
                }
                else
                {
                    tobP.x = LayoutPanel.boundX + Integer.parseInt(tdp.vXrTxt.getText())*(LayoutPanel.boundW/100);
                    tobP.y = LayoutPanel.boundY + Integer.parseInt(tdp.vYrTxt.getText())*(LayoutPanel.boundH/100);;
                    tobP.draggEnabled = true;
                }
                
            }
            
            tobPanelVec.setElementAt(tobP, j);
            
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
                tp.setPreferredSize(new Dimension(tp.getWidth() + (e.getX() - oldX),tp.getHeight()));
                //oldX = e.getX();
                tp.revalidate();
                //revalidate();
            }

        });

        tp.add(resPanel, BorderLayout.EAST);
        propPanel = new JPanel(new BorderLayout());
        propPanel.setPreferredSize(new Dimension(230, 180));
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
        
       
        if(propVec.size() > 0)
        {
            tdp = (TobDispProperties)propVec.get(0);
            propPanel.setPreferredSize(new Dimension(tdp.getWidth(), tdp.getHeight()));
            propPanel.add(tdp);
            tdp.repaint();
            tdp.revalidate();
            propPanel.revalidate();
            propPanel.repaint();
        }
        
        spProp = new JScrollPane(propPanel);
        spProp.setBackground(new Color(212, 208, 200));
        spProp.setFont(new Font("Verdana", 0, 11));
        spProp.setPreferredSize(new Dimension(230, 180));
        spProp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        spProp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        eastPanel.add(spProp, BorderLayout.SOUTH);
        
        tobList.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                ChangeSelectedPanel();
            }
        });
        
    }
    
    public void InitFirst()
    {
        if (tobListM.getSize() > 0)
        {
            tobList.setSelectedIndex(0);
        }      
    }
    
    private void InitOrder()
    {
        try
        {
            int len = propVec.size();
            TobDispProperties tdp, tdp1 = null;
            Statement statement = conn.createStatement();
            Statement statement2 = conn.createStatement();
            int Tb_id;
            int j, k;
            int index;
            
            if ( len > 0 )
            {
                tdp = (TobDispProperties)propVec.get(0);
                tdp.order = 1;
                tdp.vOrTxt.setText("1");
            }
            for(index = 0; index < len; index++) 
            {
                tdp = (TobDispProperties)propVec.get(index);
                
                ResultSet rs = statement.executeQuery("select Tob_id from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tob_superord=" + tdp.Tob_id);
                
                while( rs.next() )
                {
                    Tb_id = rs.getInt(1);
                    int or = tdp.children.size();
                    
                    for(j = 0; j < len; j++)
                    {
                        tdp1 = (TobDispProperties)propVec.get(j);       
                        
                        if (tdp1.Tob_id == Tb_id)
                        {
                            tdp1.parentInd = index;
                            break;
                        }
                    }
                    
                    if ( j == len )
                    {
                        continue;
                    }
                    ResultSet rs2 = statement2.executeQuery("select Tob_order from IISC_COMPTYPE_DISPLAY where Tob_id=" + Tb_id + " order by Tob_order");
                    
                    //Ovo tu je krizno mjesto
                    if ( rs2.next() )
                    {
                        or = rs2.getInt(1);
                        k = 0;
                        int p;
                        TobDispProperties tdpk;
                        
                        while( k < tdp.children.size())
                        {
                            p = ((Integer)tdp.children.get(k)).intValue();
                            tdpk = (TobDispProperties)propVec.get(p);
                            
                            if ( tdpk.order > or )
                            {
                                tdp.children.add(k, new Integer(j));
                                break;
                            }
                             k = k + 1;
                        }
                        
                        if ( k == tdp.children.size())
                        {
                            tdp.children.add(new Integer(j));
                        }
                        tdp1.order = or;
                        tdp1.vOrTxt.setText("" + or);
                    }
                    else
                    {
                        tdp.children.add(new Integer(j));
                        tdp1.order = tdp.children.size();
                        tdp1.vOrTxt.setText("" + tdp.children.size());
                    }
                }
            }
            statement.close();
            statement2.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        
        
    }
    
    private void ChangeSelectedPanel()
    {
        int index = tobList.getSelectedIndex();

        if (index == oldSelected)
        {
            return;
        }
        oldSelected = index;
        
        if (index > -1)
        {
        
            TobDispProperties tdp = (TobDispProperties)propVec.get(index);
            propPanel.removeAll();
            propPanel.add(tdp);
            propPanel.revalidate();
            propPanel.repaint();
            tdp.repaint();
            tdp.revalidate();
            layPanel.setSelected(index);
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
                Save();
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
    
    public void Save()
    {
        int size = propVec.size();
        
        try
        {
            Statement statement = conn.createStatement();
            
            for(int i = 0; i < size; i++)
            {
                ((TobDispProperties)propVec.get(i)).Update(statement);
            }
            dirty = false;
            statement.close();
            
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle.toString());
        }
    }
    
    private void this_windowClosing(WindowEvent e)
    {
        if ( dirty )
        {
            int opt = JOptionPane.showConfirmDialog(this, "Do you want to save changes","", JOptionPane.YES_NO_OPTION);
            
            if ( opt == JOptionPane.YES_OPTION )
            {
                option = 1;
                Save();
            }
            else
            {
                option = 0;
            }
        }
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
    
    /*****************************************************************/
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