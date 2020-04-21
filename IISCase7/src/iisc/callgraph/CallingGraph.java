package iisc.callgraph;

import iisc.*;
import iisc.tflayoutmanager.TFLayoutManager;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.util.Stack;
import java.util.Vector;
import javax.swing.*;
import java.awt.event.ComponentEvent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.event.InternalFrameEvent;
import javax.swing.JSlider;

public class CallingGraph extends JInternalFrame implements DragSourceListener, DragGestureListener
{
    private int width;
    private int height;
    public int selectedTf = -1;
    public int oldSelected = -1;
    public IISFrameMain parent;
    public Connection conn;
    public int PR_id;
    
    public ImageIcon imageIISCase = new ImageIcon(IISFrameMain.class.getResource("icons/baic2.gif"));
    public DrawingPanel drawingPanel;
    private JScrollPane sp;
    //ToolBar i dugmici
    
    private JMenuBar jmb = new JMenuBar();
    private JMenu jmtf = new JMenu("Form Type");
    private JMenuItem editTMi = new JMenuItem("Edit");
    private JMenuItem addTMi = new JMenuItem("Add");
    private JMenuItem removeTMi = new JMenuItem("Remove");
    
    private JMenu jm = new JMenu("View");
    private JCheckBoxMenuItem toolMi = new JCheckBoxMenuItem("ToolBar",true);
    private JCheckBoxMenuItem navMi = new JCheckBoxMenuItem("Navigator",true);
    
    private JMenu jmLayout = new JMenu("Diagram");
    private JCheckBoxMenuItem gridMi = new JCheckBoxMenuItem("Grid",true);
    private JCheckBoxMenuItem snapToGridMi = new JCheckBoxMenuItem("Snap to grid",true);
    private JMenu autoLayoutMi = new JMenu("Auto Layout");
    private JMenuItem autoLayoutLeftMi = new JMenuItem("Alignment Left");
    private JMenuItem autoLayoutCenterMi = new JMenuItem("Alignment Center");
    private JMenuItem autoLayoutRightMi = new JMenuItem("Alignment Right");
    private JMenuItem validateMi = new JMenuItem("Validate");
    //private JMenuItem showTMi = new JMenuItem("Show");
    
    private JPanel tbPanel;
    private ImagePanel pEdit;
    private ImagePanel pAdd;
    private ImagePanel pRemove;
    public ImagePanel pLine;
    public ImagePanel pSave;
    public ImagePanel pLeftAl;
    private JPanel sepPanel1;
    public ImagePanel pExpand;
    public ImagePanel pCollapse;
    public ImagePanel pGrid;
    public ImagePanel pSnap;
    private JPanel sepPanel2;
    private JPanel sepPanel3;
    public ImagePanel pRightAl;
    public ImagePanel pCenterAl;
    private JPanel sepPanel5;
    private JPanel sepPanel6;
    private JLabel gridLbl = new JLabel("grid size");
    public ImagePanel pValid;
    
    public FormPanel fp;
    public FormPanel selFp;
    public static ImageIcon imageExpand = new ImageIcon(IISFrameMain.class.getResource("icons/expand.gif"));
    public static ImageIcon imageCollapse = new ImageIcon(IISFrameMain.class.getResource("icons/collapse.gif"));
    public static ImageIcon imageEdit = new ImageIcon(IISFrameMain.class.getResource("icons/edit.gif"));
    public static ImageIcon iiscPar = new ImageIcon(IISFrameMain.class.getResource("icons/primitive.gif"));
    public static ImageIcon imageNew = new ImageIcon(IISFrameMain.class.getResource("icons/new.gif"));
    public static ImageIcon imageErase = new ImageIcon(IISFrameMain.class.getResource("icons/erase.gif"));
    public static ImageIcon imageRemove = new ImageIcon(IISFrameMain.class.getResource("icons/remove2.gif"));
    public static ImageIcon imageClose = new ImageIcon(IISFrameMain.class.getResource("icons/close.gif"));
    public static ImageIcon imageNewProj = new ImageIcon(IISFrameMain.class.getResource("icons/newproj2.gif"));
    public static ImageIcon imageMenu = new ImageIcon(IISFrameMain.class.getResource("icons/menu.gif"));
    public static ImageIcon imageForm = new ImageIcon(IISFrameMain.class.getResource("icons/form.gif"));
    public static ImageIcon imageLine = new ImageIcon(IISFrameMain.class.getResource("icons/cs1.gif"));
    public static ImageIcon imageSave = new ImageIcon(IISFrameMain.class.getResource("icons/save2.gif"));
    public static ImageIcon imageGrid = new ImageIcon(IISFrameMain.class.getResource("icons/grid.gif"));
    public static ImageIcon imageLeftAl = new ImageIcon(IISFrameMain.class.getResource("icons/leftal.gif"));
    public static ImageIcon imageRightAl = new ImageIcon(IISFrameMain.class.getResource("icons/rightal.gif"));
    public static ImageIcon imageCenterAl = new ImageIcon(IISFrameMain.class.getResource("icons/centeral.gif"));
    public static ImageIcon imageGridSnap = new ImageIcon(IISFrameMain.class.getResource("icons/gridsnap.gif"));
    public static ImageIcon imageValidBa = new ImageIcon(IISFrameMain.class.getResource("icons/validba.gif"));
    public static ImageIcon imageNonValidBa = new ImageIcon(IISFrameMain.class.getResource("icons/nonvalidba.gif"));
    
    public Vector tfVec;
    public Vector resVec;
    public PTree tree;
    public Vector lineVec;
    private JScrollPane spTree;
    public JTree grTree;
    private DragSource ds;
    private StringSelection transferable = new StringSelection("");
    public boolean dirty = false;
    private JPanel tp;
    private JPanel resPanel;
    private CrossPanel cp;
    private JPanel crpanel;
    
    private int oldX;
    private int oldY;
    public int BA_id;
    private String BA_mnem;
    public int Tf_entry_id;
    
    public int grid_visible;
    public int grid_snap;
    public int grid_size;
    
    public int entryIndex;
    public static final Color markColor =  Color.lightGray;
    public static final Color unmarkColor = /*new Color(198, 255, 198);*/new Color(181, 235, 255);
    private int beginX;
    private int beginY;
    private JLabel navLabel = new JLabel("  Navigator");
    private String errMessage = "";
    private JSlider gridSlider = new JSlider();
    private int AS_id;
    
    public CallingGraph(int _width, int _height, IISFrameMain _parent, Connection _conn, int _PR_id, PTree _tree, String _BA_mnem)
    {
        super("Business application diagram");

        width = _width;
        height = _height;
        parent = _parent;
        conn = _conn;
        PR_id = _PR_id;
        BA_mnem = _BA_mnem;
        tree = _tree;
        InitBussinesApplication();
        
        tfVec = new Vector();
        resVec = new Vector();
        lineVec = new Vector();
        
        setSize(width, height);
        setVisible(true); 
        setFont(new Font("Dialog", 0, 1));
        setSize(new Dimension(414, 450));
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        
        getContentPane().setBackground(new Color(212,208,200));
        setResizable(true);  
        setMaximizable(true);
        setClosable(true); 
        setIconifiable(true); 
        setFrameIcon(imageIISCase);
        setMaximizable(true);
        //this.setMaximum(true);
        setBounds(0,0,parent.desktop.getWidth(),parent.desktop.getHeight());
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        setFont(new Font("Dialog", 0, 1));
        setSize(new Dimension(414, 450));
        
        InitMenu();
        InitToolBar();
        
        //Inicijalizacija DrawingPanela 
        drawingPanel = new DrawingPanel(this);
        drawingPanel.setLayout(null);
        drawingPanel.setSize(10000,10000);
        drawingPanel.setBackground(Color.white);
        drawingPanel.setPreferredSize(new Dimension(10000, 10000));
        
        //Inicjalizacija ScrollPane
        sp = new JScrollPane(drawingPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        getContentPane().add(sp, BorderLayout.CENTER);
        drawingPanel.scrollRectToVisible(new Rectangle(new Point(beginX, beginY)));
        
        AddPanels();
        
        AddLines();
        
        
        oldSelected = tfVec.size() - 1;
        
        InitTree();
        ds = new DragSource();
        ds = new DragSource();
                DragGestureRecognizer dgr = ds.createDefaultDragGestureRecognizer(grTree,
                DnDConstants.ACTION_MOVE, this);
                
        setSelected(-1);
        repaint();
        
        if (grid_visible == 1)
        {
            pGrid.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            gridSlider.setEnabled(true);
        }
        else
        {
            gridSlider.setEnabled(false);
        }
        
        if (grid_snap == 1)
        {
            pSnap.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        }
        
        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter()
        {
            public void internalFrameClosing(InternalFrameEvent e)
            {
              this_internalFrameClosing(e);
            }
        });
        
        if ( Validate() )
        {
            pValid.setImage(imageValidBa.getImage());
            pValid.revalidate();
        }
        else
        {
            pValid.setImage(imageNonValidBa.getImage());
            pValid.revalidate();
        }
        
        gridSlider.setValue((int)((((float)(grid_size - 10))/40)*100));
        
        gridSlider.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent ce)
            {
                int value = gridSlider.getValue();
                grid_size = 10 + (int)((((float)value) / 100.0) * 40);
                dirty = true;
                drawingPanel.repaint();
            }
        }
        );
    }
    
    private void InitBussinesApplication()
    {
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_BUSINESS_APPLICATION where PR_id =" + PR_id + " and BA_mnem='" + BA_mnem + "'");
            
            if (rs.next())
            {   
                BA_id = rs.getInt("BA_id");
                AS_id  = rs.getInt("AS_id");
                Tf_entry_id = rs.getInt("Tf_entry_id");
                grid_visible = rs.getInt("grid_visible");
                grid_snap = rs.getInt("grid_snap");
                grid_size = rs.getInt("grid_size");
                beginX = rs.getInt("beginX");
                beginY = rs.getInt("beginY");
            }
            
            rs.close();
            statement.close();
            
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
        }  
    }
    
    private void this_internalFrameClosing(InternalFrameEvent e)
    {
        if ( dirty )
        {
            int opt = JOptionPane.showConfirmDialog(this, "Do you want to save changes","", JOptionPane.YES_NO_OPTION);
            
            if ( opt == JOptionPane.YES_OPTION )
            {
                Save();
            }
        }
    }
  
    private void DeleteCallingStruct(int CS_id)
    {
        try
        {
            Statement statement = conn.createStatement();
            statement.execute("delete from IISC_PASSED_VALUE where CS_id =" + CS_id); 
            statement.execute("delete from IISC_CALLING_STRUCT where CS_id =" + CS_id);
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
        }  
    }
    
    public void dragGestureRecognized(DragGestureEvent dge) 
    {
        
        try
        {
            transferable = new StringSelection(grTree.getSelectionPath().toString());
            ds.startDrag(dge, DragSource.DefaultMoveDrop, transferable, this);
        }
        catch(Exception e)
        {
            transferable = new StringSelection("");
        }
        
       
        
    }

    public void dragEnter(DragSourceDragEvent dsde)
    {
        //System.out.println("Drag Enter");
    }

  public void dragExit(DragSourceEvent dse) 
  {
      //System.out.println("Drag Exit");
  }

  public void dragOver(DragSourceDragEvent dsde) 
  {
      //System.out.println("Drag Over");
  }

  public void dragDropEnd(DragSourceDropEvent dsde) 
  {
         
      if ((dsde.getX() > sp.getLocationOnScreen().getX()) && (dsde.getX() < sp.getLocationOnScreen().getX() + drawingPanel.getVisibleRect().getWidth()))
      {
          if ((dsde.getY() > sp.getLocationOnScreen().getY()) && (dsde.getY() < sp.getLocationOnScreen().getY() + drawingPanel.getVisibleRect().getHeight()))
          {
              if(grTree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned") || grTree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Referenced"))
              {
                  try
                  {
                        ResultSet rs;
                        Statement statement = conn.createStatement();
                        
                        int size = tfVec.size();
                        String Tf_name = grTree.getSelectionPath().getLastPathComponent().toString();
                        for(int i = 0; i < size; i++)
                        {
                            if(Tf_name.equals(((FormPanel)tfVec.get(i)).Tf_mnem))
                            {
                                JOptionPane.showMessageDialog(this,"Diagram allready contains that from type", "Info",JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        rs = statement.executeQuery("select * from IISC_FORM_TYPE  where TF_mnem='"+ grTree.getSelectionPath().getLastPathComponent().toString() + "' and PR_id=" + PR_id);
                        
                        int Tf_id;
                        String Tf_mnem;
                        
                        if(rs.next())
                        {
                            Tf_id = rs.getInt("Tf_id");
                            Tf_mnem = rs.getString("Tf_mnem");
                            
                            //rs1 = statement.executeQuery("select * from IISC_CALL_GRAPH_NODE where TF_id=" + Tf_id + " and BA_id=" + BA_id);
                            
                            /*if(rs1.next())
                            {
                                int visible = rs1.getInt("visible");
                                
                                FormPanel fp = new FormPanel((int)(drawingPanel.getVisibleRect().getX() + dsde.getX() - drawingPanel.getLocationOnScreen().getX()), (int)(drawingPanel.getVisibleRect().getY() + dsde.getY() - drawingPanel.getLocationOnScreen().getY()), this, Tf_id,tfVec.size());
                                //statement2.execute("update IISC_CALL_GRAPH_NODE set Pos_x=" + fp.x + ",Pos_y=" + fp.y + " where Tf_id=" + Tf_id);
                                
                                fp.smallWidth = rs1.getInt("smallWidth");
                                fp.smallHeight = rs1.getInt("smallHeight");
                                fp.mCTCHeight = rs1.getInt("mCTCHeight");
                                fp.mCTEHeight = rs1.getInt("mCTEHeight");
                                fp.mParCHeight = rs1.getInt("mParCHeight");
                                fp.mParEHeight = rs1.getInt("mParEHeight");
                                fp.mWidth = rs1.getInt("mWidth");
                                     
                                if(rs1.getInt("expanded") == 1)
                                {
                                    fp.expanded = true;
                                    fp.mPanel.cp.SetState(1);
                                }
                                else
                                {
                                    fp.expanded = false;
                                    fp.mPanel.cp.SetState(-1);
                                }
                                if (rs1.getInt("cExpanded") == 1)
                                {
                                    fp.compPanel.expanded = true;
                                    fp.compPanel.cp.SetState(1);
                                }
                                else
                                {
                                    fp.compPanel.expanded = false;
                                    fp.compPanel.cp.SetState(-1);
                                }
                                
                                if (rs1.getInt("pExpanded") == 1)
                                {
                                    fp.parPanel.expanded = true;
                                    fp.parPanel.cp.SetState(1);
                                }
                                else
                                {
                                    fp.parPanel.expanded = false;
                                    fp.parPanel.cp.SetState(-1);
                                }
                                fp.dirty = true;
                                tfVec.add(fp);
                                ResizePanel rp = new ResizePanel(fp.getX() + fp.getWidth(), fp.getY() + fp.getHeight(),drawingPanel,0, fp);
                                resVec.add(rp);
                                fp.Readjust();
                                fp.revalidate();
                                drawingPanel.add(fp);
                                drawingPanel.revalidate();
                                
                            }
                            else
                            {*/
                                FormPanel fp = new FormPanel((int)(drawingPanel.getVisibleRect().getX() + dsde.getX() - sp.getLocationOnScreen().getX()), (int)(drawingPanel.getVisibleRect().getY() + dsde.getY() - sp.getLocationOnScreen().getY()), this, Tf_id, tfVec.size());
                                fp.dirty = true;
                                tfVec.add(fp);
                                ResizePanel rp = new ResizePanel(fp.getX() + fp.getWidth(), fp.getY() + fp.getHeight(),drawingPanel,0, fp);
                                resVec.add(rp);
                                drawingPanel.add(fp);
                                drawingPanel.repaint();
                                statement = conn.createStatement();
                                String q = "insert into IISC_CALL_GRAPH_NODE(Tf_id,Pos_x,Pos_y,mCTCHeight,mParCHeight,mCTEHeight,mParEHeight, mWidth, smallWidth,smallHeight,expanded,cExpanded,pExpanded,visible,PR_id, BA_id) values(" + Tf_id + "," + fp.x + "," + fp.y + ",";
                                q = q + fp.mCTCHeight + "," + fp.mParCHeight + "," + fp.mCTEHeight + "," + fp.mCTEHeight + "," + fp.mWidth + "," + fp.smallWidth + "," + fp.smallHeight;
                                
                                if (fp.expanded)
                                {
                                    q = q + "," + "1";
                                }
                                else
                                {
                                    q = q + "," + "0";
                                }
                                
                                if (fp.compPanel.expanded)
                                {
                                    q = q + "," + "1";
                                }
                                else
                                {
                                    q = q + "," + "0";
                                }
                                
                                if (fp.parPanel.expanded)
                                {
                                    q = q + "," + "1";
                                }
                                else
                                {
                                    q = q + "," + "0";
                                }
                                q = q + ",1," + PR_id + "," + BA_id + ")";
                                statement.execute( q );
                                statement.close();
                            //}
                        }
                        else
                        {
                            statement.close();
                        }
                  } 
                  catch(SQLException e)
                  {
                      System.out.println(e.toString());
                  }  
              }
              /*else
              {
                  if(grTree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Associations"))
                  {
                      try
                      {
                            String str=new String();
                            ResultSet rs,rs1;
                            Statement statement = conn.createStatement();
                            Statement statement2 = conn.createStatement();
                            
                            int size = tfVec.size();
                            String CS_name = grTree.getSelectionPath().getLastPathComponent().toString();
                            
                            int pos = CS_name.indexOf("_To:");
                            
                            if (pos < 0)
                            {
                                return;
                            }
                            String caller_Mnem = CS_name.substring(5, pos);
                            String called_Mnem = CS_name.substring(pos + 4, CS_name.length());
                            int caller_id;
                            int called_id;
                            int CS_id;
                            
                            rs = statement.executeQuery("select Tf_id from IISC_FORM_TYPE  where TF_mnem='"+ caller_Mnem + "' and PR_id=" + PR_id);
                            if(rs.next())
                            {
                                caller_id = rs.getInt("Tf_id");
                            }
                            else
                            {
                                return;
                            }
                            
                            rs = statement.executeQuery("select Tf_id from IISC_FORM_TYPE  where TF_mnem='"+ called_Mnem + "' and PR_id=" + PR_id);
                            if(rs.next())
                            {
                                called_id = rs.getInt("Tf_id");
                            }
                            else
                            {
                                return;
                            }
                            rs = statement.executeQuery("select * from IISC_CALL_GRAPH_VERTEX where caller_Tf="+ caller_id + " and called_Tf=" + called_id + " and PR_id=" + PR_id);
                            
                            if(rs.next())
                            {
                                CS_id = rs.getInt("CS_id");
                                size = lineVec.size();
                                
                                int i;
                                int j;
                                
                                size = lineVec.size();
                                
                                for(i = 0; i < size; i++)
                                {
                                    if(CS_id == ((LineSt)lineVec.get(i)).CS_id)
                                    {
                                        JOptionPane.showMessageDialog(this,"Diagram allready contains this calling structure", "Info",JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                }
                                
                                size = tfVec.size();
                                
                                for(i = 0; i < size; i++)
                                {
                                    if(caller_id == ((FormPanel)tfVec.get(i)).Tf_id)
                                    {
                                        break;
                                    }
                                }
                                for(j = 0; j < size; j++)
                                {
                                    if(called_id == ((FormPanel)tfVec.get(j)).Tf_id)
                                    {
                                        break;
                                    }
                                }
                                
                                if ((i < size) && (j < size))
                                {
                                    LineSt ln = new LineSt(i, j, rs.getInt("X1"), rs.getInt("Y1"), rs.getInt("X2"), rs.getInt("Y2"),CS_id,this);
                                    CheckLine(ln);
                                    lineVec.add(ln);
                                    drawingPanel.repaint();
                                }
                                
                            }
                            else
                            {
                                return;
                            }
                      } 
                      catch(SQLException e)
                      {
                          System.out.println(e.toString());
                      }  
                  }
              }*/
          
          }
      }
  }

    public void dropActionChanged(DragSourceDragEvent dsde) 
    {
        //System.out.println("Drop Action Changed");
    }
  
   
    private void AddPanels()
    {
        try
        {
              int Tf_id;
              int i = 0;
              ResultSet rs1;
              Statement statement = conn.createStatement();
              
              rs1 = statement.executeQuery("select * from IISC_CALL_GRAPH_NODE where PR_id=" + PR_id + " and BA_id=" + BA_id);
                  
              while (rs1.next())
              {
                  //int visible = rs1.getInt("visible");
                  
                  //if (visible == 1)
                  //{
                      Tf_id = rs1.getInt("Tf_id");
                      FormPanel fp = new FormPanel(rs1.getInt("Pos_x"), rs1.getInt("Pos_y"), this, Tf_id, tfVec.size());
                      
                      if(Tf_entry_id == Tf_id)
                      {
                          fp.color = markColor;
                          entryIndex = i;
                      }
                      i = i + 1;
                      fp.smallWidth = rs1.getInt("smallWidth");
                      fp.smallHeight = rs1.getInt("smallHeight");
                      fp.mCTCHeight = rs1.getInt("mCTCHeight");
                      fp.mCTEHeight = rs1.getInt("mCTEHeight");
                      fp.mParCHeight = rs1.getInt("mParCHeight");
                      fp.mParEHeight = rs1.getInt("mParEHeight");
                      fp.mWidth = rs1.getInt("mWidth");
                      
                      if(rs1.getInt("expanded") == 1)
                      {
                          fp.expanded = true;
                          fp.mPanel.cp.SetState(1);
                      }
                      else
                      {
                          fp.expanded = false;
                          fp.mPanel.cp.SetState(-1);
                      }
                      if (rs1.getInt("cExpanded") == 1)
                      {
                          fp.compPanel.expanded = true;
                          fp.compPanel.cp.SetState(1);
                      }
                      else
                      {
                          fp.compPanel.expanded = false;
                          fp.compPanel.cp.SetState(-1);
                      }
                      
                      if (rs1.getInt("pExpanded") == 1)
                      {
                          fp.parPanel.expanded = true;
                          fp.parPanel.cp.SetState(1);
                      }
                      else
                      {
                          fp.parPanel.expanded = false;
                          fp.parPanel.cp.SetState(-1);
                      }
                      tfVec.add(fp);
                      ResizePanel rp = new ResizePanel(fp.getX() + fp.getWidth(), fp.getY() + fp.getHeight(),drawingPanel,0, fp);
                      resVec.add(rp);
                      fp.Readjust();
                      drawingPanel.add(fp);
                      drawingPanel.repaint();
                  
                //}
            }
        } 
        catch(SQLException e)
        {
            System.out.println(e.toString());
        }  
  }
    
    private void AddLines()
    {
        LineSt ln;
        int caller_Tf;
        int called_Tf;
        int i;
        int j;
        int size = tfVec.size();
        
        try
        {
              
              ResultSet rs1;
              Statement statement = conn.createStatement();
              
              rs1 = statement.executeQuery("select * from IISC_CALL_GRAPH_VERTEX where PR_id=" + PR_id + " and BA_id=" + BA_id);
                  
              while (rs1.next())
              {
                  //int visible = rs1.getInt("visible");
                  
                  //if (visible == 1)
                  //{
                      caller_Tf = rs1.getInt("caller_Tf");
                      called_Tf = rs1.getInt("called_Tf");
                      
                      for(i = 0; i < size; i++)
                      {
                          if(caller_Tf == ((FormPanel)tfVec.get(i)).Tf_id)
                          {
                              break;
                          }
                      }
                      for(j = 0; j < size; j++)
                      {
                          if(called_Tf == ((FormPanel)tfVec.get(j)).Tf_id)
                          {
                              break;
                          }
                      }
                      
                      if ((i < size) && (j < size))
                      {
                          ln = new LineSt(i, j, rs1.getInt("X1"), rs1.getInt("Y1"), rs1.getInt("X2"), rs1.getInt("Y2"),rs1.getInt("CS_id"),this);
                          CheckLine(ln);
                          lineVec.add(ln);
                      }
                //}
            }
        } 
        catch(SQLException e)
        {
            System.out.println(e.toString());
        }  
    }
    
    public void setSelected(int index)
    {
        oldSelected = selectedTf;
        
        if (selectedTf == index)
        {
            return;
        }
        
        if(selectedTf >= 0)
        {
            drawingPanel.remove((ResizePanel) resVec.get(selectedTf));
        }
        
        
        selectedTf = index;
        if(selectedTf >= 0)
        {
            
            selFp = (FormPanel)tfVec.get(selectedTf);
            ResizePanel rp = (ResizePanel) resVec.get(selectedTf);
            rp.setBounds(selFp.getX() + selFp.getWidth(), selFp.getY() + selFp.getHeight(), 5, 5);
            drawingPanel.add(rp, null);
        }
        
        drawingPanel.repaint();
        //drawingPanel.revalidate();
        
    }
    
    public int getSelected()
    {
        return selectedTf;
    }
    
    public int getOldSelected()
    {
        return oldSelected;
    }
    
    public Vector getTFVector()
    {
        return tfVec;
    }
    
    public Vector getResVector()
    {
        return resVec;
    }
    
    private void MakeFormPopup(int x, int y)
    {
        JPopupMenu pop = new JPopupMenu();
        JMenuItem editPop = new JMenuItem("Edit");
        //removePop = new JMenuItem("Remove");
        
        JMenuItem layoutPop = new JMenuItem("Layout Editor");
        JMenuItem markPop = new JMenuItem("Mark as EntryPoint");
        JMenuItem findPop = new JMenuItem("Find in diagram");
        JMenuItem removePop = new JMenuItem("Remove");
        JMenuItem addPop = new JMenuItem("Add");
        
        editPop.setBackground(Color.white);
        layoutPop.setBackground(Color.white);
        markPop.setBackground(Color.white);
        findPop.setBackground(Color.white);
        editPop.setIcon(imageEdit);
        removePop.setBackground(Color.white);
        removePop.setIcon(imageRemove);
        addPop.setBackground(Color.white);
        addPop.setIcon(imageNewProj);
        
        //editMi.setBounds(0, 0, 40, 15);
        //editMi.setIcon(imageExpand);
        
        addPop.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              AddFormPanel(grTree.getLastSelectedPathComponent().toString());
          }
        });
        
        removePop.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              RemoveTfFromGraph(grTree.getLastSelectedPathComponent().toString());
          }
        });
        layoutPop.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              LayoutTf(grTree.getLastSelectedPathComponent().toString());
          }
        });
      
        markPop.addActionListener(new ActionListener()
        {
          
          public void actionPerformed(ActionEvent e)
          {
              MarkTf(grTree.getLastSelectedPathComponent().toString());
          }
          
        });
        editPop.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              EditTf(grTree.getLastSelectedPathComponent().toString());
              drawingPanel.repaint();
          }
        });
        
        findPop.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              FindTf(grTree.getLastSelectedPathComponent().toString());
          }
        });
        
        pop.add(editPop);
        pop.add(addPop);
        pop.add(removePop);
        pop.addSeparator();
        pop.add(layoutPop);
        pop.add(markPop);
        pop.add(findPop);
        pop.show(grTree,(int) x + 10,/* (int)grTree.getLocationOnScreen().getY()*/ y);
      
        //pop.dipose();
    }
    
    private void MakeCSPopup(int x, int y)
    {
        JPopupMenu pop = new JPopupMenu();
        JMenuItem editPop = new JMenuItem("Edit");
        
        JMenuItem findPop = new JMenuItem("Find in diagram");
        JMenuItem removePop = new JMenuItem("Remove");
        
        editPop.setBackground(Color.white);
        findPop.setBackground(Color.white);
        editPop.setIcon(imageEdit);
        removePop.setBackground(Color.white);
        removePop.setIcon(imageRemove);
        
        //editMi.setBounds(0, 0, 40, 15);
        //editMi.setIcon(imageExpand);
        
        
        removePop.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              RemoveLine(grTree.getLastSelectedPathComponent().toString());
          }
        });
      
        editPop.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              EditLine(grTree.getLastSelectedPathComponent().toString());
          }
        });
        
        findPop.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              FindLine(grTree.getLastSelectedPathComponent().toString());
          }
        });
        
        pop.add(editPop);
        pop.add(removePop);
        pop.addSeparator();
        pop.add(findPop);
        pop.show(grTree,(int) x + 10,/* (int)grTree.getLocationOnScreen().getY()*/ y);
      
        //pop.dipose();
    }
    
    private void InitMenu()
    {
        
        jmtf.add(editTMi);
        jmtf.add(addTMi);
        jmtf.add(removeTMi);
        
        jm.add(toolMi);
        jm.add(navMi);
        
        jmLayout.add(gridMi);
        jmLayout.add(snapToGridMi);
        jmLayout.add(autoLayoutMi);
        jmLayout.add(validateMi);
        
        autoLayoutMi.add(autoLayoutLeftMi);
        autoLayoutMi.add(autoLayoutCenterMi);
        autoLayoutMi.add(autoLayoutRightMi);
        
        if (grid_visible == 1)
        {
            gridMi.setSelected(true);
            gridSlider.setEnabled(true);
        }
        else
        {
            gridMi.setSelected(false);
            gridSlider.setEnabled(false);
        }
        
        if (grid_snap == 1)
        {
            snapToGridMi.setSelected(true);
        }
        else
        {
            snapToGridMi.setSelected(false);
        }
        
        toolMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!toolMi.isSelected())
                {
                    tbPanel.setPreferredSize(new Dimension(0,0));
                }
                else
                {
                    tbPanel.setPreferredSize(new Dimension(30,25));
                }
                /*remove(tbPanel);
                
                add(tbPanel,BorderLayout.EAST);*/
                tbPanel.revalidate();
                revalidate();
                repaint();
              
            }
        });
        
        navMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!navMi.isSelected())
                {
                    tp.setPreferredSize(new Dimension(0,0));
                }
                else
                {
                    tp.setPreferredSize(new Dimension(150,25));
                }
                tp.revalidate();
                revalidate();
                repaint();
            }
        });
        
        gridMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dirty = true;
                
                if (!gridMi.isSelected())
                {
                    grid_visible = 0;
                    gridSlider.setEnabled(false);
                    drawingPanel.repaint();
                }
                else
                {
                    grid_visible = 1;
                    gridSlider.setEnabled(true);
                    drawingPanel.repaint();
                }
            }
        });
        
        snapToGridMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dirty = true;
                if (!snapToGridMi.isSelected())
                {
                    grid_snap = 0;
                    drawingPanel.repaint();
                }
                else
                {
                    grid_snap = 1;
                    drawingPanel.repaint();
                }
            }
        });
        
        autoLayoutLeftMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dirty = true;
                AutoLayoutLeft();
            }
        });
        
        autoLayoutCenterMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dirty = true;
                AutoLayout();
            }
        });
        
        autoLayoutRightMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dirty = true;
                AutoLayoutRight();
            }
        });
        
        validateMi.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                CheckValid();
                
            }
        });
        //jm.add(moveTMi);
        jmb.add(jmtf);
        jmb.add(jm);
        jmb.add(jmLayout);
        jmb.setPreferredSize(new Dimension(30,20));
        setJMenuBar(jmb);
        
        //add(jmb);
    }
    
    private boolean CheckValid()
    {
        boolean ind = Validate();
        
        if (ind)
        {
            JOptionPane.showMessageDialog(this, "<html><center>Success", "Validation", JOptionPane.INFORMATION_MESSAGE);
            pValid.setImage(imageValidBa.getImage());
        }
        else
        {
            JOptionPane.showMessageDialog(this, errMessage, "Validation", JOptionPane.WARNING_MESSAGE); 
            pValid.setImage(imageNonValidBa.getImage());
        }
        
        return ind;
    }
    public DefaultMutableTreeNode getTfNode(int Tf_id, String Tf_mnem)
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(Tf_mnem, true);
        //root.add(new DefaultMutableTreeNode("miko"));
        
        Stack st = new Stack();
        Stack tobSt = new Stack();
        Vector tobVec = new Vector();
        Vector attVec = new Vector();
        InitAttributes(attVec, Tf_id);
        
        try
        {
            ResultSet rs;
            Statement statement = conn.createStatement();
            rs = statement.executeQuery("select * from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tf_id=" + Tf_id + " order by TOB_superord");
            
            while(rs.next())
            {
                tobVec.add(new CompType(rs.getInt("Tob_id"), rs.getString("Tob_mnem"), rs.getInt("Tob_superord")));
            }
            
           
            if (tobVec.size() > 0)
            {
                DefaultMutableTreeNode node;
                node = new DefaultMutableTreeNode(((CompType)tobVec.get(0)).Tob_mnem, true);
                root.add(node);
                st.push(node);
                tobSt.push(tobVec.get(0));
                int i = 0;
                DefaultMutableTreeNode compChildNode;
                DefaultMutableTreeNode childNode;
                DefaultMutableTreeNode attchildNode;
                CompType ct;
                i = 0;
                
                while(!st.isEmpty())
                {
                    node = (DefaultMutableTreeNode)st.pop();
                    ct = (CompType)tobSt.pop();
                    i = 0;
                    attchildNode = new DefaultMutableTreeNode("Attributes", true);
                    compChildNode = new DefaultMutableTreeNode("Component types", true);
                    AddAttributes(attchildNode,ct.Tob_id, attVec);
                    
                    if (attchildNode.getChildCount() > 0)
                    {
                        node.add(attchildNode);
                    }
                    
                    for(i = tobVec.size() - 1; i > 0; i--)
                    {
                        
                        if(ct.Tob_id == ((CompType)tobVec.get(i)).Tob_superord)
                        {
                            childNode = new DefaultMutableTreeNode(((CompType)tobVec.get(i)).Tob_mnem, true);
                            st.push(childNode);
                            tobSt.push(tobVec.get(i));
                            compChildNode.add(childNode);
                        }
                    }
                    
                    if(compChildNode.getChildCount() > 0)
                    {
                        node.add(compChildNode);
                    }
                }
            }
            /*
            cTree = new JTree(root);
            cTree.setCellRenderer(new CustomCellRenderer());
            
            cTree.addMouseListener(new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    if (e.getClickCount() > 1)
                    {
                        EditAction();
                    }
                    
                    if((e.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
                    {
                        System.out.println("e.getX():" + e.getX() + "e.getY()" + e.getY());
                        try 
                        {
                            try{cTree.setSelectionPath(cTree.getPathForLocation (e.getX(), e.getY()));}catch(Exception exp){}
                            pop.show(cTree, e.getX() + 10, /*parent.y + parent.mHeight + parent.mCTCHeight + e.getY());*/
                       /* }
                        catch(Exception ex)
                        {
                            System.out.println("E:x" + ex.toString());
                        }
                    }
                }
            });*/
            return root;
        }
        catch(SQLException sqle)
        {
            return root;
        }
        
        
    }
    
    public void InitAttributes(Vector attVec, int Tf_id)
    {
        try
        {
            ResultSet rs;
            Statement statement = conn.createStatement();
            rs = statement.executeQuery("select * from IISC_ATT_TOB,IISC_ATTRIBUTE where Tf_id=" + Tf_id + " and IISC_ATT_TOB.Att_id=IISC_ATTRIBUTE.Att_id");
            
            while(rs.next())
            {
                attVec.add(new Attribute(rs.getInt("Att_id"), rs.getString("Att_mnem"), rs.getInt("Tob_id")));
            }
        }
        catch(SQLException sqle)
        {
        }
        
    }
    public int AddAttributes(DefaultMutableTreeNode node, int Tob_id, Vector attVec)
    {
        int i;
         
        DefaultMutableTreeNode childNode;
        
        for(i = 0; i < attVec.size(); i++)
        {
            if(Tob_id == ((Attribute)attVec.get(i)).Tob_id)
            {
                childNode = new DefaultMutableTreeNode(((Attribute)attVec.get(i)).Att_mnem, true);
                node.add(childNode);
            }
        }    
           
        return i;
    }
    
    public void DeleteFromTree(LineSt line)
    {
        String name = "From:" + ((FormPanel)tfVec.get(line.i)).Tf_mnem + "_To:" + ((FormPanel)tfVec.get(line.j)).Tf_mnem;
        
        DefaultTreeModel model = (DefaultTreeModel)grTree.getModel(); 
        
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode)((DefaultMutableTreeNode)model.getRoot()).getChildAt(1);
        
        for (int i = 0; i < parent.getChildCount(); i++) 
        {
            if (parent.getChildAt(i).toString().equals(name))
            {
                model.removeNodeFromParent((MutableTreeNode)parent.getChildAt(i));
                break;
            }
        }
        grTree.revalidate();
    }
    
    public void SetTfNode(int Tf_id, String oldName, String name)
    {
    
        DefaultTreeModel model = (DefaultTreeModel)grTree.getModel(); 
        
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode)((DefaultMutableTreeNode)model.getRoot()).getChildAt(0);
        
        //parent = (DefaultMutableTreeNode);
        SetTfNode(parent, Tf_id, oldName, name);
        
        model.reload();
        grTree.revalidate();
        
       /* if (child != null )
        {
            grTree.getSelectionModel().setSelectionPath(child.getPath());
        }*/
    }
    
    public void SetTfNode(DefaultMutableTreeNode node, int Tf_id, String oldName, String name)
    {
    
       
        DefaultMutableTreeNode child = null;
        
        if ( node.toString().equals("Owned") || node.toString().equals("Referenced"))
        {
            for (int i = 0; i < node.getChildCount(); i++) 
            {
                if (node.getChildAt(i).toString().equals(oldName))
                {
                    child = getTfNode(Tf_id, name);
                    //parent.add(child);
                    node.remove(i);
                    node.insert(child, i);
                }
            }
        }
        else
        {
            for (int i = 0; i < node.getChildCount(); i++) 
            {
                SetTfNode((DefaultMutableTreeNode)node.getChildAt(i), Tf_id, oldName, name);
            }
        }
    }
        
    private DefaultMutableTreeNode GetASNode(int AS_id)
    {
        DefaultMutableTreeNode root = null;
        
        try
        {
            Statement statement = conn.createStatement();
            String AS_mnem = "";
            
            ResultSet rs = statement.executeQuery("select AS_name from IISC_APP_SYSTEM where PR_id="+ PR_id + " and AS_id=" + AS_id);
            
            if(rs.next())
            {
                AS_mnem = rs.getString(1);
            }
            else
            {
                return null;
            }
            
            root = new DefaultMutableTreeNode(AS_mnem);
            
            rs = statement.executeQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ PR_id + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id and IISC_APP_SYSTEM.AS_id=" + AS_id);
            
            DefaultMutableTreeNode tfchild = new DefaultMutableTreeNode("Form Types");
            DefaultMutableTreeNode child = new DefaultMutableTreeNode("Owned");
            
            DefaultMutableTreeNode refchild = new DefaultMutableTreeNode("Referenced");
            
            tfchild.add(child);
            tfchild.add(refchild);
            
            root.add(tfchild);
            
            while(rs.next())
            {
                child.add(getTfNode(rs.getInt("Tf_id"), rs.getString("Tf_mnem")));
            }
            
            rs = statement.executeQuery("select * from IISC_APP_SYS_REFERENCE,IISC_FORM_TYPE where IISC_APP_SYS_REFERENCE.Tf_id=IISC_FORM_TYPE.Tf_id  and IISC_FORM_TYPE.PR_id=" + PR_id + " and IISC_APP_SYS_REFERENCE.AS_id=" + AS_id);
            
            while(rs.next())
            {
                refchild.add(getTfNode(rs.getInt("Tf_id"), rs.getString("Tf_mnem")));
            }
            
            DefaultMutableTreeNode childAs = new DefaultMutableTreeNode("Child Application Systems");
            DefaultMutableTreeNode temp;
            
            rs = statement.executeQuery("select AS_id_con from IISC_APP_SYSTEM_CONTAIN  where PR_id=" + PR_id + " and AS_id=" + AS_id);
            
            while(rs.next())
            {
                temp = GetASNode(rs.getInt(1));
                
                if (temp != null) 
                {
                    childAs.add(temp);    
                }
            }
            
            if ( childAs.getChildCount() > 0)
            {
                root.add(childAs);
            }
            
            statement.close();
            
            return root;
        }
        catch(SQLException e)
        {
            System.out.println(e.toString());
            return root;
        }
    }
    
    private void InitTree()
    {
        //Inicijalizacija toolbara 
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(BA_mnem);
        DefaultMutableTreeNode asChild;
        
        try
        {
            
            asChild = GetASNode(AS_id);
            
            if (asChild != null)
            {
                root.add(asChild);
            }
            
            Statement statement2 = conn.createStatement();
            Statement statement3 = conn.createStatement();
            Statement statement4 = conn.createStatement();
            ResultSet rs1 = statement2.executeQuery("select * from IISC_CALL_GRAPH_VERTEX where PR_id="+ PR_id + " and BA_id=" + BA_id);
            ResultSet rs2;
            ResultSet rs3;
            
            
            //Dodavanje dodavanje linija 
            DefaultMutableTreeNode verChild = new DefaultMutableTreeNode("Associations");
            root.add(verChild);
            DefaultMutableTreeNode lineChild;
            
            while(rs1.next())
            {
                rs2 = statement3.executeQuery("select Tf_mnem from IISC_FORM_TYPE where Tf_id="+ rs1.getInt("caller_Tf"));
                
                if ( rs2.next() )
                {
                    rs3 = statement4.executeQuery("select Tf_mnem from IISC_FORM_TYPE where Tf_id="+ rs1.getInt("called_Tf"));
                    
                    if (rs3.next())
                    {
                        lineChild = new DefaultMutableTreeNode("From:" + rs2.getString("Tf_mnem") + "_To:" + rs3.getString("Tf_mnem"));
                        verChild.add(lineChild);
                    }
                }
                
            }
            statement2.close();
            statement3.close();
            statement4.close();
        }
        catch(SQLException e)
        {
            
        }
        grTree = new JTree(root);
        grTree.setCellRenderer(new CustomCellRenderer());
        
        grTree.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() > 1)
                {
                    //EditAction();
                    try 
                    {
                        if( grTree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned") || grTree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Referenced"))
                        {
                            EditTf(grTree.getLastSelectedPathComponent().toString());
                            drawingPanel.repaint();
                            return;
                        }
                        
                        if( grTree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Associations"))
                        {
                            EditLine(grTree.getLastSelectedPathComponent().toString());
                            return;
                        }
                    }
                    catch(Exception ex)
                    {
                        System.out.println("E:x" + ex.toString());
                    }
            
                }
                
                if((e.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
                {
                    //System.out.println("e.getX():" + e.getX() + "e.getY()" + e.getY());
                    try 
                    {
                        try{grTree.setSelectionPath(grTree.getPathForLocation (e.getX(), e.getY()));}catch(Exception exp){}
                        
                        if (grTree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Owned") || grTree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Referenced"))
                        {
                            MakeFormPopup(e.getX(), e.getY());
                            return;
                        }
                        
                        if( grTree.getSelectionPath().getParentPath().getLastPathComponent().toString().equals("Associations"))
                        {
                            MakeCSPopup(e.getX(), e.getY());
                            return;
                        }
                    }
                    catch(Exception ex)
                    {
                        System.out.println("E:x" + ex.toString());
                    }
                }
            }
        });
        
        spTree = new JScrollPane(grTree);
        spTree.setBackground(new Color(212, 208, 200));
        spTree.setFont(new Font("Verdana", 0, 11));
        spTree.setPreferredSize(new Dimension(150, 125)); 
        
        
        tp = new JPanel(new BorderLayout());
        tp.setMaximumSize(new Dimension(250,25));
        tp.setPreferredSize(new Dimension(250, 25)); 
        tp.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        getContentPane().add(tp, BorderLayout.WEST);
        
        tp.add(spTree, BorderLayout.CENTER);
        
        crpanel = new JPanel(new BorderLayout());
        crpanel.setPreferredSize(new Dimension(145, 19));
        crpanel.setMaximumSize(new Dimension(150,19));
        crpanel.setBackground(new Color(212, 208, 200));
        tp.add(crpanel, BorderLayout.NORTH);
        
        navLabel.setFont(new Font("SansSerif",1,12));
        navLabel.setPreferredSize(new Dimension(110, 18)); 
        navLabel.setBackground(new Color(212, 208, 200));
        navLabel.setMaximumSize(new Dimension(110,18)); 
        crpanel.add(navLabel, BorderLayout.WEST);
        
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
                revalidate();
                repaint();
                navMi.setSelected(false);
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
                revalidate();
            }
            
        });
        
        tp.add(resPanel, BorderLayout.EAST);
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
        
        sepPanel1 = new JPanel();
        sepPanel1.setBackground(new Color(212, 208, 200));
        sepPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        sepPanel1.setToolTipText("Edit");
        sepPanel1.setBounds(28,2,3,22);
        tbPanel.add(sepPanel1);
        
        pEdit = new ImagePanel(imageEdit,4,4);
        pEdit.setBackground(new Color(212, 208, 200));
        pEdit.setBorder(BorderFactory.createEmptyBorder());
        pEdit.setToolTipText("Edit");
        pEdit.setBounds(31,2,22,22);
        pEdit.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                pEdit.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
                pEdit.setBorder(BorderFactory.createEmptyBorder());
            }
            
            public void mouseClicked(MouseEvent e)
            {
                EditTf();
                drawingPanel.repaint();
            }
            
            public void mouseEntered(MouseEvent e)
            {
                pEdit.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            
            public void mouseExited(MouseEvent e)
            {
                pEdit.setBorder(BorderFactory.createEmptyBorder());
            }
            
        });
        
        tbPanel.add(pEdit);
        
        pAdd = new ImagePanel(imageNewProj,3,3);
        pAdd.setBackground(new Color(212, 208, 200));
        pAdd.setBorder(BorderFactory.createEmptyBorder());
        pAdd.setToolTipText("Add to graph");
        pAdd.setBounds(54,2,22,22);
        pAdd.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                pAdd.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
                pAdd.setBorder(BorderFactory.createEmptyBorder());
            }
            
            public void mouseClicked(MouseEvent e)
            {
                AddFormPanel();
            }
            
            public void mouseEntered(MouseEvent e)
            {
                pAdd.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            
            public void mouseExited(MouseEvent e)
            {
                pAdd.setBorder(BorderFactory.createEmptyBorder());
            }
            
        });
        
        tbPanel.add(pAdd);
       
        pRemove = new ImagePanel(imageRemove,3,3);
        pRemove.setBackground(new Color(212, 208, 200));
        pRemove.setBorder(BorderFactory.createEmptyBorder());
        pRemove.setToolTipText("Remove from graph");
        pRemove.setBounds(77,2,22,22);
        pRemove.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                pRemove.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
                pRemove.setBorder(BorderFactory.createEmptyBorder());
            }
            
            public void mouseClicked(MouseEvent e)
            {
                RemoveFromGraph();
            }
            
            public void mouseEntered(MouseEvent e)
            {
                pRemove.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            
            public void mouseExited(MouseEvent e)
            {
                pRemove.setBorder(BorderFactory.createEmptyBorder());
            }
            
        });
        
        tbPanel.add(pRemove);
        
        
        pLine = new ImagePanel(imageLine,0,0);
        pLine.setBackground(new Color(212, 208, 200));
        pLine.setBorder(BorderFactory.createEmptyBorder());
        pLine.setToolTipText("Add calling structure");
        pLine.setBounds(100,2,22,22);
        pLine.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                pLine.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
                
            }
            
            public void mouseClicked(MouseEvent e)
            {
                drawingPanel.line = true;
                drawingPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            }
            
            public void mouseEntered(MouseEvent e)
            {
                if (!drawingPanel.line)
                {
                    pLine.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));  
                }
                else
                {
                    //pLine.setBorder(BorderFactory.createEmptyBorder());
                }
            }
            
            public void mouseExited(MouseEvent e)
            {
                if (!drawingPanel.line)
                {
                    pLine.setBorder(BorderFactory.createEmptyBorder());
                }
            }
            
        });
        
        tbPanel.add(pLine);
        
        sepPanel2 = new JPanel();
        sepPanel2.setBackground(new Color(212, 208, 200));
        sepPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        sepPanel2.setBounds(123,2,3,22);
        tbPanel.add(sepPanel2);
        
        pExpand = new ImagePanel(imageExpand,5,5);
        pExpand.setBackground(new Color(212, 208, 200));
        pExpand.setBorder(BorderFactory.createEmptyBorder());
        pExpand.setToolTipText("Expand Node");
        pExpand.setBounds(127,2,22,22);
        pExpand.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                pExpand.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
                
            }
            
            public void mouseClicked(MouseEvent e)
            {
                if(grTree.isSelectionEmpty())
                {
                    for (int i = 0; i < grTree.getRowCount(); i++) 
                    {
                        grTree.expandRow(i);
                    }
                }
                else
                {
                    expand((DefaultMutableTreeNode) grTree.getLastSelectedPathComponent());
                   
                }
            }
            
            public void mouseEntered(MouseEvent e)
            {
                pExpand.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            
            public void mouseExited(MouseEvent e)
            {
                pExpand.setBorder(BorderFactory.createEmptyBorder());
            }
            
        });
        
        tbPanel.add(pExpand);
        
        pCollapse = new ImagePanel(imageCollapse,5,5);
        pCollapse.setBackground(new Color(212, 208, 200));
        pCollapse.setBorder(BorderFactory.createEmptyBorder());
        pCollapse.setToolTipText("Expand Node");
        pCollapse.setBounds(150,2,22,22);
        pCollapse.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                pCollapse.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
                
            }
            
            public void mouseClicked(MouseEvent e)
            {
                
                if(grTree.isSelectionEmpty())
                {
                    for (int i = 0; i < grTree.getRowCount(); i++) 
                    {
                        grTree.collapseRow(i);
                    }
                }
                else
                {
                    collapse((DefaultMutableTreeNode) grTree.getLastSelectedPathComponent());
                }

            }
            
            public void mouseEntered(MouseEvent e)
            {
                pCollapse.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            
            public void mouseExited(MouseEvent e)
            {
                pCollapse.setBorder(BorderFactory.createEmptyBorder());
            }
            
        });
        
        tbPanel.add(pCollapse);
        
        sepPanel3 = new JPanel();
        sepPanel3.setBackground(new Color(212, 208, 200));
        sepPanel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        sepPanel3.setBounds(173,2,3,22);
        tbPanel.add(sepPanel3);
        
        pGrid = new ImagePanel(imageGrid, 3, 3);
        pGrid.setBackground(new Color(212, 208, 200));
        pGrid.setBorder(BorderFactory.createEmptyBorder());
        pGrid.setToolTipText("Grid");
        pGrid.setBounds(177,2,22,22);
        pGrid.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                pGrid.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
                
            }
            
            public void mouseClicked(MouseEvent e)
            {
               dirty = true;
               
               if (grid_visible == 1)
               {
                  grid_visible = 0;
                  pGrid.setBorder(BorderFactory.createEmptyBorder());
                  gridSlider.setEnabled(false);
                  drawingPanel.repaint();
               }
               else
               {
                  grid_visible = 1;
                  gridSlider.setEnabled(true);
                  pGrid.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                  drawingPanel.repaint();
               }
            }
            
            public void mouseEntered(MouseEvent e)
            {
                if ( grid_visible == 0)
                {
                    pGrid.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                }
            }
            
            public void mouseExited(MouseEvent e)
            {
                if ( grid_visible == 0)
                {
                    pGrid.setBorder(BorderFactory.createEmptyBorder());
                }
            }
            
        });
        
        tbPanel.add(pGrid);
        
        pSnap = new ImagePanel(imageGridSnap, 3, 3);
        pSnap.setBackground(new Color(212, 208, 200));
        pSnap.setBorder(BorderFactory.createEmptyBorder());
        pSnap.setToolTipText("Snap to grid");
        pSnap.setBounds(200,2,22,22);
        pSnap.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                pSnap.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
                
            }
            
            public void mouseClicked(MouseEvent e)
            {
               dirty = true;
               
               if (grid_snap == 1)
               {
                  grid_snap = 0;
                  pSnap.setBorder(BorderFactory.createEmptyBorder());
                  //drawingPanel.repaint();
               }
               else
               {
                  grid_snap = 1;
                  pSnap.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                  //drawingPanel.repaint();
               }
            }
            
            public void mouseEntered(MouseEvent e)
            {
                if ( grid_snap == 0)
                {
                    pSnap.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                }
            }
            
            public void mouseExited(MouseEvent e)
            {
                if ( grid_snap == 0)
                {
                    pSnap.setBorder(BorderFactory.createEmptyBorder());
                }
            }
            
        });
        
        tbPanel.add(pSnap);
        
        sepPanel3 = new JPanel();
        sepPanel3.setBackground(new Color(212, 208, 200));
        sepPanel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        sepPanel3.setBounds(223,2,3,22);
        tbPanel.add(sepPanel3);
        
        
        
        pLeftAl = new ImagePanel(imageLeftAl, 3, 4);
        pLeftAl.setBackground(new Color(212, 208, 200));
        pLeftAl.setBorder(BorderFactory.createEmptyBorder());
        pLeftAl.setToolTipText("Left alignment");
        pLeftAl.setBounds(227,2,22,22);
        
        tbPanel.add(pLeftAl);
        
        pLeftAl.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                pLeftAl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
                
            }
            
            public void mouseClicked(MouseEvent e)
            {
                dirty = true;
                AutoLayoutLeft();
            }
            
            public void mouseEntered(MouseEvent e)
            {
                pLeftAl.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            
            public void mouseExited(MouseEvent e)
            {
                pLeftAl.setBorder(BorderFactory.createEmptyBorder());
            }
            
        });
        
        pCenterAl = new ImagePanel(imageCenterAl, 3, 4);
        pCenterAl.setBackground(new Color(212, 208, 200));
        pCenterAl.setBorder(BorderFactory.createEmptyBorder());
        pCenterAl.setToolTipText("Center aligment");
        pCenterAl.setBounds(250,2,22,22);
        
        pCenterAl.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                pCenterAl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
            }
            
            public void mouseClicked(MouseEvent e)
            {
                dirty = true;
                AutoLayout();
            }
            
            public void mouseEntered(MouseEvent e)
            {
                pCenterAl.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            
            public void mouseExited(MouseEvent e)
            {
                pCenterAl.setBorder(BorderFactory.createEmptyBorder());
            }
            
        });
        
        tbPanel.add(pCenterAl);
        
        pRightAl = new ImagePanel(imageRightAl, 3, 4);
        pRightAl.setBackground(new Color(212, 208, 200));
        pRightAl.setBorder(BorderFactory.createEmptyBorder());
        pRightAl.setToolTipText("Right alignment");
        pRightAl.setBounds(273,2,22,22);
        pRightAl.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                pRightAl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
            }
            
            public void mouseClicked(MouseEvent e)
            {
                dirty = true;
                AutoLayoutRight();
            }
            
            public void mouseEntered(MouseEvent e)
            {
                pRightAl.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            
            public void mouseExited(MouseEvent e)
            {
                pRightAl.setBorder(BorderFactory.createEmptyBorder());
            }
            
        });
        tbPanel.add(pRightAl);
        
        sepPanel5 = new JPanel();
        sepPanel5.setBackground(new Color(212, 208, 200));
        sepPanel5.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        sepPanel5.setBounds(296,2,3,22);
        tbPanel.add(sepPanel5);
        
        pValid = new ImagePanel(imageValidBa, 2, 2);
        pValid.setBackground(new Color(212, 208, 200));
        pValid.setBorder(BorderFactory.createEmptyBorder());
        pValid.setToolTipText("Validate");
        pValid.setBounds(300,2,22,22);
        pValid.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            { 
                pValid.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
            
            public void mouseReleased(MouseEvent e)
            {
            }
            
            public void mouseClicked(MouseEvent e)
            {
                CheckValid();
            }
            
            public void mouseEntered(MouseEvent e)
            {
                pValid.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }
            
            public void mouseExited(MouseEvent e)
            {
                pValid.setBorder(BorderFactory.createEmptyBorder());
            }
            
        });
        tbPanel.add(pValid);
        
        sepPanel6 = new JPanel();
        sepPanel6.setBackground(new Color(212, 208, 200));
        sepPanel6.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        sepPanel6.setBounds(323,2,3,22);
        tbPanel.add(sepPanel6);
        
        gridLbl.setBounds(330, 0, 45, 22);
        gridLbl.setFont(new Font("SansSerif", 0, 11));
        gridLbl.setBackground(new Color(212, 208, 200));
        tbPanel.add(gridLbl  );
        
        gridSlider.setBackground(new Color(212, 208, 200));
        gridSlider.setBounds(375 ,2, 50, 20);
        tbPanel.add(gridSlider);
    }
    
    private void expand(DefaultMutableTreeNode node )
    {
        grTree.expandPath(new TreePath(node.getPath()));
    
        for(int i=0;i<node.getChildCount();i++)
        {
          expand((DefaultMutableTreeNode )node.getChildAt(i));
        } 
    }

    private void collapse(DefaultMutableTreeNode node )
    {    
        for(int i=0;i<node.getChildCount();i++)
        {
            collapse((DefaultMutableTreeNode )node.getChildAt(i));
        } 
          
        grTree.collapsePath(new TreePath(node.getPath()));
    }
    
    //Automatsko podesavanje entry pointa
    public void UpdateEntryPoint()
    {
        try
        {
            Statement statement = conn.createStatement();
            statement.execute("update IISC_BUSINESS_APPLICATION set Tf_entry_id=" + ((FormPanel)tfVec.get(entryIndex)).Tf_id + " where BA_id=" + BA_id);
        }   
        catch(SQLException e)
        {
            System.out.println(e.toString());
        } 
    }
    
    private void Save()
    {
        try
        {
            Statement statement = conn.createStatement();
            statement.execute("update IISC_BUSINESS_APPLICATION set Tf_entry_id=" + ((FormPanel)tfVec.get(entryIndex)).Tf_id + ",grid_snap=" + grid_snap + ",grid_visible=" + grid_visible + ",grid_size=" + grid_size + ",beginX=" + ((int)drawingPanel.getVisibleRect().getX()) + ",beginY=" + ((int)drawingPanel.getVisibleRect().getY()) + " where BA_id=" + BA_id);
            
            dirty = false;
            
            int size = tfVec.size();
            int i;
            FormPanel fp;
            
            for(i = 0; i < size; i++)
            {
                fp = (FormPanel)tfVec.get(i);
                
                if (fp.dirty)
                {
                    String q = "update IISC_CALL_GRAPH_NODE set Pos_x=" + fp.x + ",Pos_y=" + fp.y + ",mCTCHeight=" + fp.mCTCHeight + ",mParCHeight=" + fp.mParCHeight + ",mCTEHeight=" + fp.mCTEHeight + ",mParEHeight=" + fp.mParEHeight + ",mWidth=" + fp.mWidth + ",smallWidth=" + fp.smallWidth + ",smallHeight=" + fp.smallHeight;
                   
                    if (fp.expanded)
                    {
                        q = q + ",expanded=1";
                    }
                    else
                    {
                        q = q + ",expanded=0";
                    }
                    
                    if (fp.compPanel.expanded)
                    {
                        q = q + ",cExpanded=1";
                    }
                    else
                    {
                        q = q + ",cExpanded=0";
                    }
                    
                    if (fp.parPanel.expanded)
                    {
                        q = q + ",pExpanded=1";
                    }
                    else
                    {
                        q = q + ",pExpanded=0";
                    }
                    
                    q = q + ",visible=1 where Tf_id=" + fp.Tf_id + " and BA_id=" + BA_id;
                    statement.execute( q );
                }
                
            }
            size = lineVec.size();
            LineSt ln;
            
            for(i = 0; i < size; i++)
            {
                ln = (LineSt)lineVec.get(i);
                
                if (ln.dirty)
                {
                    String q = "update IISC_CALL_GRAPH_VERTEX set X1=" + ln.x1 + ",Y1=" + ln.y1 + ",X2=" + ln.x2 + ",Y2=" + ln.y2 + ",visible=1";
                    q = q + " where CS_id=" + ln.CS_id;
                    
                    statement.execute( q );
                }
                else
                {
                    statement.execute("update IISC_CALL_GRAPH_VERTEX set visible=1 where CS_id=" + ln.CS_id);
                }
            }
        } 
        catch(SQLException e)
        {
            System.out.println(e.toString());
        }  
    }
    
    public void EditTf()
    {
        
        if (selectedTf < 0)
        {
            drawingPanel.ShowLineDialog();
            return;
        }
        FormPanel fp = (FormPanel)tfVec.get(selectedTf);
        
        Form frm = new Form(parent,"Form Type", true, conn, fp.Tf_id, fp.Tf_mnem, PR_id, fp.AS_id, fp.As_mnem, tree, BA_id, this);
        Settings.Center(frm);
        frm.setVisible(true);
        String oldName = fp.Tf_mnem;
        fp.ReInit();
        fp.parPanel.ReInitParList();
        fp.parPanel.revalidate();
        fp.compPanel.ReInitTree();
        fp.revalidate();
        SetTfNode(fp.Tf_id, oldName, fp.Tf_mnem);
        
    }
    
    public void EditTf(String name)
    {
        int i; 
        int size = tfVec.size();
        
        
        for(i = 0; i < size; i++)
        {
            if (((FormPanel)tfVec.get(i)).Tf_mnem.equals(name))
            {
                break;
            }
        }
        
        //Ako ga nema na dijagramu
        if ( i == size )
        {
            JOptionPane.showMessageDialog(this, "Diagram does not containt form type :" + name,"Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        FormPanel fp = (FormPanel)tfVec.get(i);
        
        Form frm = new Form(parent,"Form Type", true, conn, fp.Tf_id, fp.Tf_mnem, PR_id, fp.AS_id, fp.As_mnem, tree, BA_id, this);
        Settings.Center(frm);
        frm.setVisible(true);
        String oldName = fp.Tf_mnem;
        fp.ReInit();
        fp.parPanel.ReInitParList();
        fp.parPanel.revalidate();
        fp.compPanel.ReInitTree();
        fp.revalidate();
        SetTfNode(fp.Tf_id, oldName, fp.Tf_mnem);
    }
    
    public void EditLine(String name)
    {
        int pos = name.indexOf("_To:");
        try
        {              
            if (pos < 0)
            {
                return;
            }
            
            String caller_Mnem = name.substring(5, pos);
            String called_Mnem = name.substring(pos + 4, name.length());
            
            int caller_id;
            int called_id;
            int CS_id;
            
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select Tf_id from IISC_FORM_TYPE  where TF_mnem='"+ caller_Mnem + "' and PR_id=" + PR_id);
            
            if(rs.next())
            {
                caller_id = rs.getInt("Tf_id");
            }
            else
            {
                return;
            }
            
            rs = statement.executeQuery("select Tf_id from IISC_FORM_TYPE  where TF_mnem='"+ called_Mnem + "' and PR_id=" + PR_id);
            
            if(rs.next())
            {
                called_id = rs.getInt("Tf_id");
            }
            else
            {
                return;
            }
            
            rs = statement.executeQuery("select * from IISC_CALL_GRAPH_VERTEX where caller_Tf="+ caller_id + " and called_Tf=" + called_id + " and PR_id=" + PR_id + " and BA_id=" + BA_id);
            
            LineSt ln = null;     
            if(rs.next())
            {
                CS_id = rs.getInt("CS_id");
                
                int i;
                
                int size = lineVec.size();
                
                for(i = 0; i < size; i++)
                {
                    ln = (LineSt)lineVec.get(i);
                    
                    if(CS_id == ln.CS_id)
                    {
                        break;
                    }
                }
                
                if(i == size)
                {
                    return;
                }
            }
            else
            {
                return;
            }
            PassedValuePanel pp = new PassedValuePanel(parent,conn,PR_id,((FormPanel)tfVec.get(ln.i)).Tf_id,((FormPanel)tfVec.get(ln.j)).Tf_id,ln.CS_id, BA_id);
            Settings.Center(pp);
            pp.setVisible(true);
            pp.dispose();
              
         } 
        catch(SQLException e)
        {
            System.out.println(e.toString());
        }  
    }
    
    public void FindTf(String name)
    {
        int i; 
        int size = tfVec.size();
        
        for(i = 0; i < size; i++)
        {
            if (((FormPanel)tfVec.get(i)).Tf_mnem.equals(name))
            {
                break;
            }
        }
        
        //Ako ga nema na dijagramu
        if ( i == size )
        {
            JOptionPane.showMessageDialog(this, "Diagram does not containt form type :" + name,"Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        setSelected(i);
        FormPanel fp = (FormPanel)tfVec.get(i);
        
        if ((fp.x < drawingPanel.getVisibleRect().getX()) || (fp.x > drawingPanel.getVisibleRect().getX() + drawingPanel.getVisibleRect().getWidth()) || (fp.y > drawingPanel.getVisibleRect().getY() + drawingPanel.getVisibleRect().getHeight()) || (fp.y < drawingPanel.getVisibleRect().getY()))
        {
            int offsetX = fp.x - ((int)(drawingPanel.getVisibleRect().getWidth()/2));
            int offsetY = fp.y - ((int)(drawingPanel.getVisibleRect().getHeight()/2));
            
            if (offsetX < 0)
            {
                offsetX = 0;
            }
            if (offsetY < 0)
            {
                offsetY = 0;
            }
            drawingPanel.scrollRectToVisible(new Rectangle(offsetX, offsetY, (int)drawingPanel.getVisibleRect().getWidth(), (int) drawingPanel.getVisibleRect().getHeight()));
            drawingPanel.repaint();
        }
        
        
    }
    public void MarkTf(String name)
    {
        int i; 
        int size = tfVec.size();
        
        for(i = 0; i < size; i++)
        {
            if (((FormPanel)tfVec.get(i)).Tf_mnem.equals(name))
            {
                break;
            }
        }
        
        //Ako ga nema na dijagramu
        if ( i == size )
        {
            JOptionPane.showMessageDialog(this, "Diagram does not containt form type :" + name,"Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (entryIndex == i)
        {
            return;
        }
        
        FormPanel fp = (FormPanel)tfVec.get(entryIndex);
        fp.color = CallingGraph.unmarkColor;
        fp.repaint();
        
        fp = (FormPanel)tfVec.get(i);
        fp.color = CallingGraph.markColor;
        entryIndex = i;
        UpdateEntryPoint();
        fp.repaint();
    }
    
    public void LayoutTf(String name)
    {
        int i; 
        int size = tfVec.size();
        
        for(i = 0; i < size; i++)
        {
            if (((FormPanel)tfVec.get(i)).Tf_mnem.equals(name))
            {
                break;
            }
        }
        
        //Ako ga nema na dijagramu
        if ( i == size )
        {
            JOptionPane.showMessageDialog(this, "Diagram does not containt form type :" + name,"Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        FormPanel fp = (FormPanel)tfVec.get(i);
        TFLayoutManager tflm = new TFLayoutManager(500, 600, parent, conn, fp.Tf_id, PR_id);
        Settings.Center(tflm);
        tflm.setVisible(true);
    }
    
    private void RemoveTfFromGraph(String name)
    {
          int i;
          int size = tfVec.size();
          
          for(i = 0; i < size; i++)
          {
              if (((FormPanel)tfVec.get(i)).Tf_mnem.equals(name))
              {
                  break;
              }
          }
          
          //Ako ga nema na dijagramu
          if ( i == size )
          {
              JOptionPane.showMessageDialog(this, "Diagram does not containt form type :" + name,"Error", JOptionPane.ERROR_MESSAGE);
              return;
          }
          
          //Ako je entry point
          if (i == entryIndex)
          {
              JOptionPane.showMessageDialog(this,"Entry point form type can not be removed","Message",JOptionPane.ERROR_MESSAGE);        
              return;
          }
          
          FormPanel fp = (FormPanel)tfVec.get(i);
          ResizePanel rp = (ResizePanel) resVec.get(i);
          LineSt line;
          drawingPanel.remove(fp);
          drawingPanel.remove(rp);
          
          size = lineVec.size();
          
          int k = 0;
          
          while( k < size)
          {
              line = (LineSt) lineVec.get(k);
              
              if ((i == line.i) || (i == line.j))
              {
                  try
                  {  
                      Statement statement = conn.createStatement();
                      statement.execute("delete from IISC_PASSED_VALUE where CS_id =" + line.CS_id); 
                      statement.execute("delete from IISC_CALLING_STRUCT where CS_id =" + line.CS_id);
                      statement.execute("delete from IISC_CALL_GRAPH_VERTEX where PR_id=" + PR_id + " and CS_id=" + line.CS_id);
                      DeleteFromTree(line);
                      
                  } 
                  catch(SQLException e)
                  {
                      System.out.println(e.toString());
                  }  
                  lineVec.remove(k);
                  size = size - 1;
              }
              else
              {
                  k = k + 1;
              }
              
          }
          
          size = resVec.size();
          //Promijeni indekse linija koje su tu
          for(int j = 0; j < lineVec.size(); j ++)
          {
              line = (LineSt) lineVec.get(j);
          
              if (i < line.i) 
              {
                  line.i = line.i - 1;
                  lineVec.setElementAt(line,j);
              }
              if (i < line.j)
              {
                  line.j = line.j - 1;
                  lineVec.setElementAt(line,j);
              }
          }
              
          for(k = i + 1; k < size; k++)
          {
              fp = (FormPanel)tfVec.get(k);
              rp = (ResizePanel)resVec.get(k);
              
              fp.index = fp.index - 1;
              rp.index = rp.index - 1;
              tfVec.setElementAt(fp,k);
              resVec.setElementAt(rp,k);
              
          }
          FormPanel node = (FormPanel)tfVec.get(i);
          try
          {  
              Statement statement = conn.createStatement();
              statement.execute("delete from IISC_CALL_GRAPH_NODE where BA_id=" + BA_id + " and Tf_id=" + node.Tf_id);
          } 
          catch(SQLException e)
          {
              System.out.println(e.toString());
          }
          tfVec.remove(i);
          resVec.remove(i);
          oldSelected = -1;
          selectedTf = -1;
          drawingPanel.revalidate();
          drawingPanel.repaint();
    }
    
    public void RemoveFromGraph()
    {
        if (selectedTf < 0)
          {
              
              if (drawingPanel.selectedLine > -1)
              {
                  try
                  {   
                      LineSt ln = (LineSt)lineVec.get(drawingPanel.selectedLine);
                      Statement statement = conn.createStatement();
                      statement.execute("delete from IISC_PASSED_VALUE where CS_id =" + ln.CS_id); 
                      statement.execute("delete from IISC_CALLING_STRUCT where CS_id =" + ln.CS_id);
                      statement.execute("delete from IISC_CALL_GRAPH_VERTEX where BA_id=" + BA_id + " and CS_id=" + ln.CS_id);
                      DeleteFromTree(ln);
                      lineVec.remove(drawingPanel.selectedLine);
                      
                  } 
                  catch(SQLException e)
                  {
                      System.out.println(e.toString());
                  }  
                  drawingPanel.selectedLine = -1;
                  drawingPanel.repaint();
              }
              return;
          }
          
          if (selectedTf == entryIndex)
          {
              JOptionPane.showMessageDialog(this,"Entry point form type can not be removed","Message",JOptionPane.ERROR_MESSAGE);        
              return;
          }
          
          if (selectedTf < entryIndex)
          {
              entryIndex = entryIndex - 1;
          }
          
          FormPanel fp = (FormPanel)tfVec.get(selectedTf);
          ResizePanel rp = (ResizePanel) resVec.get(selectedTf);
          LineSt line;
          drawingPanel.remove(fp);
          drawingPanel.remove(rp);
          
          int size = lineVec.size();
          
          int i = 0;
          
          while( i < size)
          {
              line = (LineSt) lineVec.get(i);
              
              if ((selectedTf == line.i) || (selectedTf == line.j))
              {
                  try
                  {  
                      Statement statement = conn.createStatement();
                      statement.execute("delete from IISC_PASSED_VALUE where CS_id =" + line.CS_id); 
                      statement.execute("delete from IISC_CALLING_STRUCT where CS_id =" + line.CS_id);
                      statement.execute("delete from IISC_CALL_GRAPH_VERTEX where PR_id=" + PR_id + " and CS_id=" + line.CS_id);
                      DeleteFromTree(line);
                      
                  } 
                  catch(SQLException e)
                  {
                      System.out.println(e.toString());
                  }  
                  lineVec.remove(i);
                  size = size - 1;
              }
              else
              {
                  i = i + 1;
              }
              
          }
          
          size = resVec.size();
          //Promijeni indekse linija koje su tu
          for(int j = 0; j < lineVec.size(); j ++)
          {
              line = (LineSt) lineVec.get(j);
          
              if (selectedTf < line.i) 
              {
                  line.i = line.i - 1;
                  lineVec.setElementAt(line,j);
              }
              if (selectedTf < line.j)
              {
                  line.j = line.j - 1;
                  lineVec.setElementAt(line,j);
              }
          }
              
          for(i = selectedTf + 1; i < size; i++)
          {
              fp = (FormPanel)tfVec.get(i);
              rp = (ResizePanel)resVec.get(i);
              
              fp.index = fp.index - 1;
              rp.index = rp.index - 1;
              tfVec.setElementAt(fp,i);
              resVec.setElementAt(rp,i);
              
          }
          FormPanel node = (FormPanel)tfVec.get(selectedTf);
          try
          {  
              Statement statement = conn.createStatement();
              statement.execute("delete from IISC_CALL_GRAPH_NODE where BA_id=" + BA_id + " and Tf_id=" + node.Tf_id);
          } 
          catch(SQLException e)
          {
              System.out.println(e.toString());
          }
          tfVec.remove(selectedTf);
          resVec.remove(selectedTf);
          oldSelected = -1;
          selectedTf = -1;
          drawingPanel.revalidate();
          drawingPanel.repaint();
    }
    
    private void FindLine(String name)
    {
        
        try
        {   
            int pos = name.indexOf("_To:");
                  
            if (pos < 0)
            {
                return;
            }
            
            String caller_Mnem = name.substring(5, pos);
            String called_Mnem = name.substring(pos + 4, name.length());
            
            int caller_id;
            int called_id;
            int CS_id;
            
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select Tf_id from IISC_FORM_TYPE  where TF_mnem='"+ caller_Mnem + "' and PR_id=" + PR_id);
            
            if(rs.next())
            {
                caller_id = rs.getInt("Tf_id");
            }
            else
            {
                return;
            }
            
            rs = statement.executeQuery("select Tf_id from IISC_FORM_TYPE  where TF_mnem='"+ called_Mnem + "' and PR_id=" + PR_id);
            
            if(rs.next())
            {
                called_id = rs.getInt("Tf_id");
            }
            else
            {
                return;
            }
            
            rs = statement.executeQuery("select * from IISC_CALL_GRAPH_VERTEX where caller_Tf="+ caller_id + " and called_Tf=" + called_id + " and PR_id=" + PR_id + " and BA_id=" + BA_id);
            
            LineSt ln = null;     
            int i;
            
            if(rs.next())
            {
                CS_id = rs.getInt("CS_id");
                
                int size = lineVec.size();
                
                for(i = 0; i < size; i++)
                {
                    ln = (LineSt)lineVec.get(i);
                    
                    if(CS_id == ln.CS_id)
                    {
                        break;
                    }
                }
                
                if(i == size)
                {
                    return;
                }
            }
            else
            {
                return;
            }
            
            if ((ln.X1 < drawingPanel.getVisibleRect().getX()) || (ln.X1 > drawingPanel.getVisibleRect().getX() + drawingPanel.getVisibleRect().getWidth()) || (ln.Y1 > drawingPanel.getVisibleRect().getY() + drawingPanel.getVisibleRect().getHeight()) || (ln.Y1 < drawingPanel.getVisibleRect().getY()))
            {
                int offsetX = ln.X1 - ((int)(drawingPanel.getVisibleRect().getWidth()/2));
                int offsetY = ln.Y1 - ((int)(drawingPanel.getVisibleRect().getHeight()/2));
                
                if (offsetX < 0)
                {
                    offsetX = 0;
                }
                if (offsetY < 0)
                {
                    offsetY = 0;
                }
                
                drawingPanel.scrollRectToVisible(new Rectangle(offsetX, offsetY, (int)drawingPanel.getVisibleRect().getWidth(), (int) drawingPanel.getVisibleRect().getHeight()));
                drawingPanel.selectedLine = i;
                setSelected(-1);
                drawingPanel.selLn = ln;
                drawingPanel.fp1 = (FormPanel)tfVec.get(drawingPanel.selLn.i);
                drawingPanel.fp2 = (FormPanel)tfVec.get(drawingPanel.selLn.j);
                drawingPanel.repaint();
                return;
            }
            
            if ((ln.X2 < drawingPanel.getVisibleRect().getX()) || (ln.X2 > drawingPanel.getVisibleRect().getX() + drawingPanel.getVisibleRect().getWidth()) || (ln.Y2 > drawingPanel.getVisibleRect().getY() + drawingPanel.getVisibleRect().getHeight()) || (ln.Y2 < drawingPanel.getVisibleRect().getY()))
            {
                int offsetX = ln.X2 - ((int)(drawingPanel.getVisibleRect().getWidth()/2));
                int offsetY = ln.Y2 - ((int)(drawingPanel.getVisibleRect().getHeight()/2));
                
                if (offsetX < 0)
                {
                    offsetX = 0;
                }
                if (offsetY < 0)
                {
                    offsetY = 0;
                }
                
                drawingPanel.scrollRectToVisible(new Rectangle(offsetX, offsetY, (int)drawingPanel.getVisibleRect().getWidth(), (int) drawingPanel.getVisibleRect().getHeight()));
            }
            
            setSelected(-1);
            drawingPanel.selectedLine = i;
            drawingPanel.selLn = ln;
            drawingPanel.fp1 = (FormPanel)tfVec.get(drawingPanel.selLn.i);
            drawingPanel.fp2 = (FormPanel)tfVec.get(drawingPanel.selLn.j);
            drawingPanel.repaint();
        } 
        catch(SQLException e)
        {
            System.out.println(e.toString());
        }  
              
    }
    
    private void RemoveLine(String name)
    {
        
        try
        {   
            int pos = name.indexOf("_To:");
                  
            if (pos < 0)
            {
                return;
            }
            
            String caller_Mnem = name.substring(5, pos);
            String called_Mnem = name.substring(pos + 4, name.length());
            
            int caller_id;
            int called_id;
            int CS_id;
            
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select Tf_id from IISC_FORM_TYPE  where TF_mnem='"+ caller_Mnem + "' and PR_id=" + PR_id);
            
            if(rs.next())
            {
                caller_id = rs.getInt("Tf_id");
            }
            else
            {
                return;
            }
            
            rs = statement.executeQuery("select Tf_id from IISC_FORM_TYPE  where TF_mnem='"+ called_Mnem + "' and PR_id=" + PR_id);
            
            if(rs.next())
            {
                called_id = rs.getInt("Tf_id");
            }
            else
            {
                return;
            }
            
            rs = statement.executeQuery("select * from IISC_CALL_GRAPH_VERTEX where caller_Tf="+ caller_id + " and called_Tf=" + called_id + " and PR_id=" + PR_id + " and BA_id=" + BA_id);
            
            LineSt ln = null;     
            int i;
            
            if(rs.next())
            {
                CS_id = rs.getInt("CS_id");
                
                int size = lineVec.size();
                
                for(i = 0; i < size; i++)
                {
                    ln = (LineSt)lineVec.get(i);
                    
                    if(CS_id == ln.CS_id)
                    {
                        break;
                    }
                }
                
                if(i == size)
                {
                    return;
                }
            }
            else
            {
                return;
            }
            Statement statement2 = conn.createStatement();
            statement2.execute("delete from IISC_PASSED_VALUE where CS_id =" + ln.CS_id); 
            statement2.execute("delete from IISC_CALLING_STRUCT where CS_id =" + ln.CS_id);
            statement2.execute("delete from IISC_CALL_GRAPH_VERTEX where BA_id=" + BA_id + " and CS_id=" + ln.CS_id);
            DeleteFromTree(ln);
            lineVec.remove(i);
            statement2.close();
            
        } 
        catch(SQLException e)
        {
            System.out.println(e.toString());
        }  
        drawingPanel.selectedLine = -1;
        drawingPanel.repaint();
              
    }
    private void AddFormPanel()
    {
        RefForm rf = new RefForm(parent, "Add Form Type", true);
        Settings.Center(rf);
        rf.setVisible(true);
        
        if(rf.option == rf.OK)
        {
            FormValue fv = rf.fv;
            
            int size = tfVec.size();
            
            for(int i = 0; i < size; i++)
            {
                if(fv.Tf_id == ((FormPanel)tfVec.get(i)).Tf_id)
                {
                    JOptionPane.showMessageDialog(this,"Diagram allready contains that from type", "Info",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            try
            {
               
                
                Statement statement = conn.createStatement();
                /*
                rs1 = statement.executeQuery("select * from IISC_CALL_GRAPH_NODE where TF_id=" + fv.Tf_id + "BA_id" + BA_id);
                
                if(rs1.next())
                {
                    int visible = rs1.getInt("visible");
                    
                    FormPanel fp = new FormPanel(rs1.getInt("Pos_x"),rs1.getInt("Pos_y"), this, fv.Tf_id,tfVec.size());
                    //statement2.execute("update IISC_CALL_GRAPH_NODE set Pos_x=" + fp.x + ",Pos_y=" + fp.y + " where Tf_id=" + Tf_id);
                    
                    fp.smallWidth = rs1.getInt("smallWidth");
                    fp.smallHeight = rs1.getInt("smallHeight");
                    fp.mCTCHeight = rs1.getInt("mCTCHeight");
                    fp.mCTEHeight = rs1.getInt("mCTEHeight");
                    fp.mParCHeight = rs1.getInt("mParCHeight");
                    fp.mParEHeight = rs1.getInt("mParEHeight");
                    fp.mWidth = rs1.getInt("mWidth");
                    
                    if(rs1.getInt("expanded") == 1)
                    {
                        fp.expanded = true;
                        fp.mPanel.cp.SetState(1);
                    }
                    else
                    {
                        fp.expanded = false;
                        fp.mPanel.cp.SetState(-1);
                    }
                    if (rs1.getInt("cExpanded") == 1)
                    {
                        fp.compPanel.expanded = true;
                        fp.compPanel.cp.SetState(1);
                    }
                    else
                    {
                        fp.compPanel.expanded = false;
                        fp.compPanel.cp.SetState(-1);
                    }
                    
                    if (rs1.getInt("pExpanded") == 1)
                    {
                        fp.parPanel.expanded = true;
                        fp.parPanel.cp.SetState(1);
                    }
                    else
                    {
                        fp.parPanel.expanded = false;
                        fp.parPanel.cp.SetState(-1);
                    }
                    tfVec.add(fp);
                    ResizePanel rp = new ResizePanel(fp.getX() + fp.getWidth(), fp.getY() + fp.getHeight(),drawingPanel,0, fp);
                    resVec.add(rp);
                    fp.Readjust();
                    fp.revalidate();
                    drawingPanel.add(fp);
                    drawingPanel.revalidate();
                    
                }
                else
                {*/
                    FormPanel fp = new FormPanel((int)drawingPanel.getVisibleRect().getX() + (int)(Math.random()* drawingPanel.getVisibleRect().getWidth()), (int)drawingPanel.getVisibleRect().getY() + (int)(Math.random()* drawingPanel.getVisibleRect().getHeight()), this, fv.Tf_id, tfVec.size());
                    tfVec.add(fp);
                    ResizePanel rp = new ResizePanel(fp.getX() + fp.getWidth(), fp.getY() + fp.getHeight(),drawingPanel,0, fp);
                    resVec.add(rp);
                    drawingPanel.add(fp);
                    drawingPanel.repaint();
                    statement = conn.createStatement();
                    String q = "insert into IISC_CALL_GRAPH_NODE(Tf_id,Pos_x,Pos_y,mCTCHeight,mParCHeight,mCTEHeight,mParEHeight, mWidth, smallWidth,smallHeight,expanded,cExpanded,pExpanded,visible,PR_id, BA_id) values(" + fv.Tf_id + "," + fp.x + "," + fp.y + ",";
                    q = q + fp.mCTCHeight + "," + fp.mParCHeight + "," + fp.mCTEHeight + "," + fp.mCTEHeight + "," + fp.mWidth + "," + fp.smallWidth + "," + fp.smallHeight;
                    
                    if (fp.expanded)
                    {
                        q = q + "," + "1";
                    }
                    else
                    {
                        q = q + "," + "0";
                    }
                    
                    if (fp.compPanel.expanded)
                    {
                        q = q + "," + "1";
                    }
                    else
                    {
                        q = q + "," + "0";
                    }
                    
                    if (fp.parPanel.expanded)
                    {
                        q = q + "," + "1";
                    }
                    else
                    {
                        q = q + "," + "0";
                    }
                    q = q + ",0," + PR_id + "," + BA_id + ")";
                    
                    statement.execute( q );
                    statement.close();
                //}
            } 
            catch(SQLException e)
            {
                System.out.println(e.toString());
            } 
        }
    }
    
    private void AddFormPanel(String name)
    {
        
        int size = tfVec.size();
        int Tf_id;
        
        for(int i = 0; i < size; i++)
        {
            if(((FormPanel)tfVec.get(i)).Tf_mnem.equals(name))
            {
                JOptionPane.showMessageDialog(this,"Diagram allready contains that from type", "Info",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        try
        {
            ResultSet rs1;
            Statement statement = conn.createStatement();
            Statement statement2 = conn.createStatement();
             
            rs1 = statement2.executeQuery("select Tf_id from IISC_FORM_TYPE where Tf_mnem='" + name + "' and PR_ID=" + PR_id);
            
            if(rs1.next())
            {
                Tf_id = rs1.getInt("Tf_id");
            }
            else
            {
                return;
            }
            FormPanel fp = new FormPanel((int)drawingPanel.getVisibleRect().getX() + (int)(Math.random()* drawingPanel.getVisibleRect().getWidth()), (int)drawingPanel.getVisibleRect().getY() + (int)(Math.random()* drawingPanel.getVisibleRect().getHeight()), this, Tf_id, tfVec.size());
            tfVec.add(fp);
            ResizePanel rp = new ResizePanel(fp.getX() + fp.getWidth(), fp.getY() + fp.getHeight(),drawingPanel,0, fp);
            resVec.add(rp);
            drawingPanel.add(fp);
            drawingPanel.repaint();
            statement = conn.createStatement();
            String q = "insert into IISC_CALL_GRAPH_NODE(Tf_id,Pos_x,Pos_y,mCTCHeight,mParCHeight,mCTEHeight,mParEHeight, mWidth, smallWidth,smallHeight,expanded,cExpanded,pExpanded,visible,PR_id, BA_id) values(" + Tf_id + "," + fp.x + "," + fp.y + ",";
            q = q + fp.mCTCHeight + "," + fp.mParCHeight + "," + fp.mCTEHeight + "," + fp.mCTEHeight + "," + fp.mWidth + "," + fp.smallWidth + "," + fp.smallHeight;
            
            if (fp.expanded)
            {
                q = q + "," + "1";
            }
            else
            {
                q = q + "," + "0";
            }
            
            if (fp.compPanel.expanded)
            {
                q = q + "," + "1";
            }
            else
            {
                q = q + "," + "0";
            }
            
            if (fp.parPanel.expanded)
            {
                q = q + "," + "1";
            }
            else
            {
                q = q + "," + "0";
            }
            q = q + ",0," + PR_id + "," + BA_id + ")";
            
            statement.execute( q );
            statement.close();
        } 
        catch(SQLException e)
        {
            System.out.println(e.toString());
        } 
    }
      
      private class RefForm extends JDialog 
      {
          private JButton btnSave = new JButton();
          private JComboBox cmbRefForm = new JComboBox();
          private JButton btnCancel = new JButton();
          private JButton btnFunction5 = new JButton();
          private int option;
          private int OK = 1;
          private int CANCEL = 0;
          public FormValue fv;
          private Vector fvVector;
          
          public RefForm(IISFrameMain parent, String title, boolean modal)
          {
           
              super(parent, title, modal);
              option = CANCEL;
              this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
              try
              {
                  jbInit();
              }
              catch(Exception e)
              {
                e.printStackTrace();
              }
        
          }
    
          private void jbInit() throws Exception
          {     
              setSize(new Dimension(292, 102));
              getContentPane().setLayout(null);
              setTitle("Add Form Type");
              setFont(new Font("Dialog", 0, 11));
              setModal(true);
              btnSave.setText("OK");
              btnSave.setBounds(new Rectangle(115, 40, 85, 25));
              //btnSave.setSelected(true);
              btnSave.addActionListener(new ActionListener()
                {
                  public void actionPerformed(ActionEvent e)
                  {
                    btnSave_actionPerformed(e);
                  }
                });
              cmbRefForm.setBounds(new Rectangle(5, 10, 230, 20));
              btnCancel.setText("Cancel");
              btnCancel.setBounds(new Rectangle(205, 40, 75, 25));
              //btnCancel.setSelected(true);
              btnCancel.addActionListener(new ActionListener()
                {
                  public void actionPerformed(ActionEvent e)
                  {
                    btnCancel_actionPerformed(e);
                  }
                });
              btnFunction5.setMaximumSize(new Dimension(50, 30));
              btnFunction5.setPreferredSize(new Dimension(50, 30));
              btnFunction5.setText("...");
              btnFunction5.setBounds(new Rectangle(245, 10, 30, 20));
              btnFunction5.setMinimumSize(new Dimension(50, 30));
              btnFunction5.setFont(new Font("SansSerif", 0, 11));
              btnFunction5.addActionListener(new ActionListener()
                {
          
                  public void actionPerformed(ActionEvent e)
                  {
                    btnFunction5_actionPerformed(e);
                  }
                });
              getContentPane().add(btnFunction5, null);
              getContentPane().add(btnCancel, null);
              getContentPane().add(cmbRefForm, null);
              getContentPane().add(btnSave, null);
              InitCombo();
          }
    
          private void btnSave_actionPerformed(ActionEvent e)
          { 
              int index = cmbRefForm.getSelectedIndex();
              
              
              if(index > -1)
              {
                  option = OK;
                  fv = (FormValue)fvVector.get(index);
              }
              else
              {
                  option = CANCEL;
              }
              setVisible(false);
          }
      
          private void btnCancel_actionPerformed(ActionEvent e)
          {
              option = CANCEL;
              setVisible(false);
          }
      
          private void btnFunction5_actionPerformed(ActionEvent e)
          {
              SearchTable ptype=new SearchTable((Frame)getParent(),"Select Form Type",true,conn);
              Settings.Center(ptype);
              ptype.type="Form Type";
              ptype.btnNew.setEnabled(false);
              ptype.btnPrimitive.setEnabled(false);
              ptype.item=cmbRefForm;
              ptype.owner=this;
              ptype.setVisible(true);
          }
          
          /* Inicijalizacija kombo boksa koji sadrzi tipove formi */
          private void InitCombo()
          {
              fvVector = new Vector();
              
              try
              {
                  Statement statement = conn.createStatement();
                  ResultSet rs = statement.executeQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ PR_id + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id");
                  
                  
                  while(rs.next())
                  {
                      fv = new FormValue(rs.getInt("Tf_id"), rs.getString("Tf_mnem"), rs.getInt("AS_id"), rs.getString("AS_name"), rs.getInt("Tf_use"));
                      cmbRefForm.addItem(fv.Tf_mnem + " (" + fv.As_mnem + ")");
                      fvVector.add(fv);
                  }
              }
              catch(SQLException e)
              {
                  
              }
              
              repaint();
          }
      }
      private class FormValue
      {   
          int Tf_id;
          String Tf_mnem;
          int As_id;
          String As_mnem;
          int type;
          
          public FormValue(int _Tf_id, String _Tf_mnem, int _As_id, String _As_mnem, int _type)
          {
              Tf_id = _Tf_id;
              Tf_mnem = _Tf_mnem;
              As_id = _As_id;
              As_mnem = _As_mnem;
              type = _type;//da li je meni ili forma
              if(type < 2)
              {
                  type = 0;
              }
              else
              {
                  type = 1;
              }
          }

      }
      
      
          
    private class SearchTable extends JDialog implements ActionListener, ListSelectionListener
    {
        private JScrollPane jScrollPane1 = new JScrollPane();
        private JTextField txtSearch = new JTextField();
        private JButton btnSelect = new JButton();
        public QueryTableModel qtm;
        public String type;
        public Object owner;
        public JComboBox item;
        public String query;
        private Connection connection;
        private ListSelectionModel rowSM;
        public JTable table = new JTable();
        public JButton btnPrimitive = new JButton();
        private JButton btnCancel = new JButton();
        private JButton btnHelp = new JButton();
        private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif")); 
        private ImageIcon imageFilter = new ImageIcon(IISFrameMain.class.getResource("icons/filter.gif"));
        public JButton btnNew = new JButton();
        private JButton btnFilter = new JButton();
        private JButton btnSelect1 = new JButton();
      
        public SearchTable(Frame parent, String title, boolean modal,Connection con)
        {
            super(parent, title, modal);
            try
            { 
                connection=con;
                jbInit();
                refresh();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
      
        }

        private void jbInit() throws Exception
        {  
            this.setResizable(false);
            qtm=new QueryTableModel(connection,-1);
            table=new JTable(qtm);
            this.setSize(new Dimension(490, 313));
            this.getContentPane().setLayout(null);
            this.setFont(new Font("SansSerif", 0, 11));
            jScrollPane1.setBounds(new Rectangle(5, 50, 470, 180));
            jScrollPane1.setFont(new Font("SansSerif", 0, 11));
            txtSearch.setBounds(new Rectangle(5, 15, 420, 20));
            txtSearch.setFont(new Font("SansSerif", 0, 11));
            btnSelect.setMaximumSize(new Dimension(50, 30));
            btnSelect.setPreferredSize(new Dimension(50, 30));
            btnSelect.setText("Reset");
            btnSelect.setBounds(new Rectangle(90, 240, 80, 30));
            btnSelect.setMinimumSize(new Dimension(50, 30));
            btnSelect.setFont(new Font("SansSerif", 0, 11));
            table.setFont(new Font("SansSerif", 0, 11));
            jScrollPane1.getViewport().add(table, null);
            this.getContentPane().add(btnSelect1, null);
            this.getContentPane().add(btnFilter, null);
            this.getContentPane().add(btnNew, null);
            this.getContentPane().add(btnHelp, null);
            this.getContentPane().add(btnCancel, null);
            this.getContentPane().add(btnPrimitive, null);
            this.getContentPane().add(btnSelect, null);
            this.getContentPane().add(txtSearch, null);
            jScrollPane1.getViewport().add(table, null);
            this.getContentPane().add(jScrollPane1, null);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setRowSelectionAllowed(true);
            table.setGridColor(new Color(0,0,0));
            table.setBackground(Color.white);
            table.setAutoResizeMode(0);
            table.setAutoscrolls(true);
            table.getTableHeader().setReorderingAllowed(false);
            rowSM = table.getSelectionModel();
            addWindowListener(new WindowAdapter()
            {
                public void windowActivated(WindowEvent e)
                {
                    this_windowActivated(e);
                }
            });
            addComponentListener(new ComponentAdapter()
            {
                public void componentShown(ComponentEvent e)
                {
                    this_componentShown(e);
                }
            });
            btnSelect1.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  btnSelect_actionPerformed(e);
                }
              });
            btnSelect1.setFont(new Font("SansSerif", 0, 11));
            btnSelect1.setMinimumSize(new Dimension(50, 30));
            btnSelect1.setBounds(new Rectangle(5, 240, 80, 30));
            btnSelect1.setText("Select");
            btnSelect1.setPreferredSize(new Dimension(50, 30));
            btnSelect1.setMaximumSize(new Dimension(50, 30));
            this.addPropertyChangeListener(new PropertyChangeListener()
              {
                public void propertyChange(PropertyChangeEvent e)
                {
                  this_propertyChange(e);
                }
              });
            btnFilter.setBounds(new Rectangle(440, 10, 35, 30));
            btnFilter.setFont(new Font("SansSerif", 0, 11));
            btnFilter.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  btnFilter_actionPerformed(e);
                }
              });
            btnFilter.setIcon(imageFilter);
            btnNew.setMaximumSize(new Dimension(50, 30));
            btnNew.setPreferredSize(new Dimension(50, 30));
            btnNew.setBounds(new Rectangle(175, 240, 85, 30));
            btnNew.setMinimumSize(new Dimension(50, 30));
            btnNew.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent ae)
                {
                  new_ActionPerformed(ae);
                }
              });
            btnNew.setFont(new Font("SansSerif", 0, 11));
            btnNew.setText("New");
            btnPrimitive.setText("Edit");
            btnHelp.setIcon(imageHelp);
            btnHelp.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  btnHelp_actionPerformed(e);
                }
              });
            btnHelp.setFont(new Font("SansSerif", 0, 11));
            btnHelp.setBounds(new Rectangle(440, 240, 35, 30));
            this.addFocusListener(new FocusAdapter()
              {
                public void focusGained(FocusEvent e)
                {
                  this_focusGained(e);
                }
              });
            btnCancel.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  btnCancel_actionPerformed(e);
                }
              });
            btnCancel.setFont(new Font("SansSerif", 0, 11));
            btnCancel.setMinimumSize(new Dimension(50, 30));
            btnCancel.setBounds(new Rectangle(355, 240, 80, 30));
            btnCancel.setText("Cancel");
            btnCancel.setPreferredSize(new Dimension(50, 30));
            btnCancel.setMaximumSize(new Dimension(50, 30));
            btnSelect.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  btnReset_actionPerformed(e);
                }
              });
            txtSearch.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent e)
                {
                  txtSearch_actionPerformed(e);
                }
              });
            txtSearch.addKeyListener(new KeyAdapter()
              {
                public void keyReleased(KeyEvent e)
                {
                  txtSearch_keyReleased(e);
                }
              });
            btnPrimitive.setFont(new Font("SansSerif", 0, 11));
            btnPrimitive.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent ae)
                {
                  close_ActionPerformed(ae);
                }
              });
            btnPrimitive.setMinimumSize(new Dimension(50, 30));
            btnPrimitive.setBounds(new Rectangle(265, 240, 85, 30));
            btnPrimitive.setPreferredSize(new Dimension(50, 30));
            btnPrimitive.setMaximumSize(new Dimension(50, 30));
            table.addMouseListener(new MouseAdapter() {

            public void mouseReleased (MouseEvent me) 
            {
              doMouseClicked(me);

            }
            });
  }
  void doMouseClicked (MouseEvent me) 
  {
      if(me.getClickCount()==2)
      {
          choose();
      }
  }
  public void actionPerformed(ActionEvent e)
  {
  
  }

  private void btnSearch_actionPerformed(ActionEvent e)
  {
  }
  
  public void valueChanged(ListSelectionEvent e){}



  private void new_ActionPerformed(ActionEvent e)
  {
  
  }
  
  private void close_ActionPerformed(ActionEvent e)
  {
  }

  private void txtSearch_keyReleased(KeyEvent e)
  {
      qtm.setFormQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ PR_id + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id and TF_mnem like '%"+ txtSearch.getText() +"%'"); 
      table.getColumnModel().getColumn(0).setMinWidth(0);
      table.getColumnModel().getColumn(0).setMaxWidth(0);
      table.getColumnModel().getColumn(0).setPreferredWidth(0);
      table.getColumnModel().getColumn(0).setWidth(0);
     
  }

  private void txtSearch_actionPerformed(ActionEvent e)
  {
  }

  private void choose()
  {
      JDBCQuery query = new JDBCQuery(connection);
      ResultSet rs;
  
      try
      {
              
          String str = table.getValueAt(table.getSelectedRow(),1).toString().trim() + " ("+ table.getValueAt(table.getSelectedRow(),2).toString()+")";
          item.setSelectedItem(str);
      }
      catch(Exception e)
      {
      
      }
      dispose();
  
  }
  private void btnSelect_actionPerformed(ActionEvent e)
  {
      choose();
  }
   private void btnCancel_actionPerformed(ActionEvent e)
  { 
      dispose();
  }

  private void this_focusGained(FocusEvent e)
  {
      refresh(); 
  }
private void refresh()
{   
    qtm.setFormQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ PR_id+ " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id"); 
    table.getColumnModel().getColumn(0).setMinWidth(0);
    table.getColumnModel().getColumn(0).setMaxWidth(0);
    table.getColumnModel().getColumn(0).setPreferredWidth(0);
    table.getColumnModel().getColumn(0).setWidth(0);
}
  private void btnHelp_actionPerformed(ActionEvent e)
  {
      Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
      Settings.Center(hlp);
      hlp.setVisible(true);
  }

  private void this_propertyChange(PropertyChangeEvent e)
  {
      qtm.setFormQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ PR_id + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id"); 
      query="select * from IISC_TF_APPSYS,IISC_FORM_TYPE where from IISC_TF_APPSYS.PR_id=" + PR_id + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id";
      table.getColumnModel().getColumn(0).setMinWidth(0);
      table.getColumnModel().getColumn(0).setMaxWidth(0);
      table.getColumnModel().getColumn(0).setPreferredWidth(0);
      table.getColumnModel().getColumn(0).setWidth(0);
  }

  private void btnReset_actionPerformed(ActionEvent e)
  {
      qtm.setFormQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ PR_id + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id"); 
      query="select * from IISC_TF_APPSYS,IISC_FORM_TYPE where from IISC_TF_APPSYS.PR_id="+ PR_id + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id";
      table.getColumnModel().getColumn(0).setMinWidth(0);
      table.getColumnModel().getColumn(0).setMaxWidth(0);
      table.getColumnModel().getColumn(0).setPreferredWidth(0);
      table.getColumnModel().getColumn(0).setWidth(0);
      
      txtSearch.setText("");
  }

  private void this_componentShown(ComponentEvent e)
  {
  }

  private void this_windowActivated(WindowEvent e)
  { 
  }

  private void btnFilter_actionPerformed(ActionEvent e)
  {
      Filter filt =new  Filter((IISFrameMain) getParent(),getTitle(), true, connection,this );
      Settings.Center(filt);
      filt.setVisible(true);
  }
}

    public class Filter extends JDialog
    { private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
      private JScrollPane jScrollPane1 = new JScrollPane();
      private JTable table = new JTable();
      private JButton btnSave = new JButton();
      public JButton btnClose = new JButton();
      private JButton btnHelp = new JButton();
      public QueryTableModel qtm;
      private Connection connection;
      private SearchTable owner;
       private Collision owner1;
    
      public Filter()
      {
        this(null, "", false);
      }
    
      public Filter(IISFrameMain parent, String title, boolean modal)
      {
        super((Frame) parent, title, modal);
        try
        {
    
          jbInit();
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
    
      }
      public Filter(IISFrameMain parent, String title, boolean modal, Connection con, SearchTable own)
      { 
        super((Frame)parent, title, modal);
        try
        {connection=con;
        owner=own;
          jbInit();
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
    
      }
     public Filter(IISFrameMain parent, String title, boolean modal, Connection con, Collision own)
      { 
        super((Frame)parent, title, modal);
        try
        {connection=con;
        owner1=own;
          jbInit1();
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
    
      }
        private void jbInit1() throws Exception
      {  this.setResizable(false);
        this.setSize(new Dimension(398, 306));
        this.getContentPane().setLayout(null);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setTitle("Filter");
        jScrollPane1.setBounds(new Rectangle(5, 10, 380, 210));
        jScrollPane1.setFont(new Font("SansSerif", 0, 11));
        btnSave.setMaximumSize(new Dimension(50, 30));
        btnSave.setPreferredSize(new Dimension(50, 30));
        btnSave.setText("Filter");
        btnSave.setBounds(new Rectangle(185, 230, 75, 30));
        btnSave.setMinimumSize(new Dimension(50, 30));
        btnSave.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              save1_ActionPerformed(ae);
            }
          });
        btnSave.setFont(new Font("SansSerif", 0, 11));
        btnClose.setMaximumSize(new Dimension(50, 30));
        btnClose.setPreferredSize(new Dimension(50, 30));
        btnClose.setText("Cancel");
        btnClose.setBounds(new Rectangle(265, 230, 80, 30));
        btnClose.setMinimumSize(new Dimension(50, 30));
        btnClose.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              close1_ActionPerformed(ae);
            }
          });
        btnClose.setFont(new Font("SansSerif", 0, 11));
        btnHelp.setBounds(new Rectangle(350, 230, 35, 30));
        btnHelp.setIcon(imageHelp);
        btnHelp.setFont(new Font("SansSerif", 0, 11));
        btnHelp.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnHelp_actionPerformed(e);
            }
          });
        this.getContentPane().add(btnHelp, null);
        this.getContentPane().add(btnClose, null);
        this.getContentPane().add(btnSave, null);
        int k=owner1.table.getColumnCount();
        String[] col=new String[k];
        for (int i=0; i< k;i++)
        {
         col[i]=owner1.table.getColumnName(i);
        }
        qtm=new QueryTableModel(connection,-1);
        table=new JTable(qtm);
        table.addMouseListener(new MouseAdapter()
          {
            public void mouseReleased(MouseEvent e)
            {
              jScrollPane1_mouseReleased(e);
            }
          });
        qtm.setQueryFilter(col);
       
    jScrollPane1.getViewport().add(table, null);
        this.getContentPane().add(jScrollPane1, null);
    
          }
      private void jbInit() throws Exception
      {
        this.setSize(new Dimension(398, 306));
        this.getContentPane().setLayout(null);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setTitle("Filter");
        jScrollPane1.setBounds(new Rectangle(5, 10, 380, 210));
        jScrollPane1.setFont(new Font("SansSerif", 0, 11));
        btnSave.setMaximumSize(new Dimension(50, 30));
        btnSave.setPreferredSize(new Dimension(50, 30));
        btnSave.setText("Filter");
        btnSave.setBounds(new Rectangle(185, 230, 75, 30));
        btnSave.setMinimumSize(new Dimension(50, 30));
        btnSave.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              save_ActionPerformed(ae);
            }
          });
        btnSave.setFont(new Font("SansSerif", 0, 11));
        btnClose.setMaximumSize(new Dimension(50, 30));
        btnClose.setPreferredSize(new Dimension(50, 30));
        btnClose.setText("Cancel");
        btnClose.setBounds(new Rectangle(265, 230, 80, 30));
        btnClose.setMinimumSize(new Dimension(50, 30));
        btnClose.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              close1_ActionPerformed(ae);
            }
          });
        btnClose.setFont(new Font("SansSerif", 0, 11));
        btnHelp.setBounds(new Rectangle(350, 230, 35, 30));
        btnHelp.setIcon(imageHelp);
        btnHelp.setFont(new Font("SansSerif", 0, 11));
        btnHelp.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnHelp_actionPerformed(e);
            }
          });
        this.getContentPane().add(btnHelp, null);
        this.getContentPane().add(btnClose, null);
        this.getContentPane().add(btnSave, null);
        int k=owner.table.getColumnCount();
        String[] col=new String[k];
        for (int i=0; i< k;i++)
        {
         col[i]=owner.table.getColumnName(i);
        }
        qtm=new QueryTableModel(connection,-1);
        table=new JTable(qtm);
        table.addMouseListener(new MouseAdapter()
          {
            public void mouseReleased(MouseEvent e)
            {
              jScrollPane1_mouseReleased(e);
            }
          });
        qtm.setQueryFilter(col);
         if(owner.type.equals("Component Type Attribute") || owner.type.equals("Form Type"))
      table.setRowHeight(0, 1);
    jScrollPane1.getViewport().add(table, null);
        this.getContentPane().add(jScrollPane1, null);
    
          }
         
      public void valueChanged(ListSelectionEvent e){}
      private void save_ActionPerformed(ActionEvent e)
      {  
      
      for (int j=owner.table.getRowCount()-1; j>-1 ;j--)
     {
    
      for (int i=0; i< table.getRowCount();i++)
      {try {
      String s1=owner.table.getValueAt(j,i).toString();
      String s2=table.getValueAt(i,1).toString();
     String[] niz=ODBCList.splitString(s1.toLowerCase(),s2.toLowerCase());
    if(!s2.equals("") && niz.length < 2)
      {owner.qtm.removeRow(j);
      break;
      }
      }
         catch(Exception ae)
        {
          ae.printStackTrace();
        }
     }
      }
     
      dispose();
      }
    
      private void save1_ActionPerformed(ActionEvent e)
      {  
      
      for (int j=owner1.table.getRowCount()-1; j>-1 ;j--)
     {
      for (int i=0; i< table.getRowCount();i++)
      {try {
      String s1=owner1.table.getValueAt(j,i).toString();
      String s2=table.getValueAt(i,1).toString();
     String[] niz=ODBCList.splitString(s1,s2);
    if(!s2.equals("") && niz.length < 2)
      {owner1.qtm.removeRow(j);
      break;
      }
      }
         catch(Exception ae)
        {
          ae.printStackTrace();
        }
     }
      }
     
      dispose();
      } 
    
      private void close1_ActionPerformed(ActionEvent e)
      {
      dispose();
      }
    
      private void btnHelp_actionPerformed(ActionEvent e)
      {Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
     Settings.Center(hlp);
     hlp.setVisible(true);
      }
    
      private void jScrollPane1_mouseReleased(MouseEvent e)
      {
    
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
      }
      public void setX(int p_X)
      {
         x = p_X;
      }
    
      public void setY(int p_Y)
      {
          y = p_Y;
      }
      
      public void setImage(Image img)
      {
          mImage = img;
          repaint();
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
    
    public void AddLine(int x1, int y1, int x2, int y2, int i, int j, int CS_id)
    {
        lineVec.add(new LineSt(i, j, x1, y1, x2, y2, CS_id, this));
    }
    
    public void AdjustLine(int index)
    {
        int size = lineVec.size();
        int i;
        LineSt ln;
        
        for(i = 0; i < size; i++)
        {
            ln = (LineSt)lineVec.get(i);
            
            if ((ln.i == index) || (ln.j == index))
            {
                ln.UpdateLeft();
                ln.UpdateRight();
                lineVec.setElementAt(ln, i);
            }
        }
        
    }
    
    public void AdjustLine(int index, int offsetX, int offsetY)
    {
        int size = lineVec.size();
        int i;
        LineSt ln;
        
        for(i = 0; i < size; i++)
        {
            ln = (LineSt)lineVec.get(i);
            
            if (ln.i == index)
            {
                ln.x1 = ln.x1 + offsetX;
                ln.y1 = ln.y1 + offsetY;
                ln.UpdateLeft();
                ln.UpdateRight();
                lineVec.setElementAt(ln, i);
                continue;
            }
            
            if  (ln.j == index)
            {
                ln.x2 = ln.x2 + offsetX;
                ln.y2= ln.y2 + offsetY;
                ln.UpdateLeft();
                ln.UpdateRight();
                lineVec.setElementAt(ln, i);
            }
        }  
    }
    
    public void CheckLines(int index)
    {
        int size = lineVec.size();
        int i;
        LineSt ln;
        FormPanel fp = (FormPanel)tfVec.get(index);
        
        for(i = 0; i < size; i++)
        {
            ln = (LineSt)lineVec.get(i);
            
            if (ln.i == index)
            {
                
                if ((ln.x1 <= fp.x) || (ln.x1 >= fp.x + fp.mPanel.getWidth()) || (ln.y1 <= fp.y) || (ln.y1 >= fp.y + fp.mPanel.getHeight()))
                { 
                    ln.x1 = fp.x + fp.mPanel.getWidth()/2;
                    ln.y1 = fp.y + fp.mPanel.getHeight()/2;
                    
                    lineVec.setElementAt(ln, i);
                }
                ln.UpdateLeft();
                ln.UpdateRight();
                continue;
            }
            
            if  (ln.j == index)
            {
                if ((ln.x2 <= fp.x) || (ln.x2 >= fp.x + fp.mPanel.getWidth()) || (ln.y2 <= fp.y) || (ln.y2 >= fp.y + fp.mPanel.getHeight()))
                { 
                    ln.x2 = fp.x + fp.mPanel.getWidth()/2;
                    ln.y2 = fp.y + fp.mPanel.getHeight()/2;
                    lineVec.setElementAt(ln, i);
                }
                ln.UpdateLeft();
                ln.UpdateRight();
                continue;
            }
        }  
    }
    
    public void CheckLine(int index)
    {
        
        LineSt ln = (LineSt)lineVec.get(index);
        FormPanel fp = (FormPanel)tfVec.get(ln.i);
        
       
                
        if ((ln.x1 <= fp.x) || (ln.x1 >= fp.x + fp.mPanel.getWidth()) || (ln.y1 <= fp.y) || (ln.y1 >= fp.y + fp.mPanel.getHeight()))
        { 
            ln.x1 = fp.x + fp.mPanel.getWidth()/2;
            ln.y1 = fp.y + fp.mPanel.getHeight()/2;
        }
        
        fp = (FormPanel)tfVec.get(ln.j);
        
        if ((ln.x2 <= fp.x) || (ln.x2 >= fp.x + fp.mPanel.getWidth()) || (ln.y2 <= fp.y) || (ln.y2 >= fp.y + fp.mPanel.getHeight()))
        { 
            ln.x2 = fp.x + fp.mPanel.getWidth()/2;
            ln.y2 = fp.y + fp.mPanel.getHeight()/2;     
        }   
        
        ln.UpdateLeft();
        ln.UpdateRight();
        lineVec.setElementAt(ln, index);
    }
    
    public void CheckLine()
    {
        
        for(int index = 0; index < lineVec.size(); index++)
        {
            LineSt ln = (LineSt)lineVec.get(index);
            FormPanel fp = (FormPanel)tfVec.get(ln.i);
            
           
                    
            if ((ln.x1 <= fp.x) || (ln.x1 >= fp.x + fp.mPanel.getWidth()) || (ln.y1 <= fp.y) || (ln.y1 >= fp.y + fp.mPanel.getHeight()))
            { 
                ln.x1 = fp.x + fp.mPanel.getWidth()/2;
                ln.y1 = fp.y + fp.mPanel.getHeight()/2;
            }
            
            fp = (FormPanel)tfVec.get(ln.j);
            
            if ((ln.x2 <= fp.x) || (ln.x2 >= fp.x + fp.mPanel.getWidth()) || (ln.y2 <= fp.y) || (ln.y2 >= fp.y + fp.mPanel.getHeight()))
            { 
                ln.x2 = fp.x + fp.mPanel.getWidth()/2;
                ln.y2 = fp.y + fp.mPanel.getHeight()/2;     
            }   
            
            ln.UpdateLeft();
            ln.UpdateRight();
            lineVec.setElementAt(ln, index);
        }
    }
    
    public void CheckLine(LineSt ln)
    {
        FormPanel fp = (FormPanel)tfVec.get(ln.i);
             
        if ((ln.x1 <= fp.x) || (ln.x1 >= fp.x + fp.mPanel.getWidth()) || (ln.y1 <= fp.y) || (ln.y1 >= fp.y + fp.mPanel.getHeight()))
        { 
            ln.x1 = fp.x + fp.mPanel.getWidth()/2;
            ln.y1 = fp.y + fp.mPanel.getHeight()/2;
        }
        
        fp = (FormPanel)tfVec.get(ln.j);
        
        if ((ln.x2 <= fp.x) || (ln.x2 >= fp.x + fp.mPanel.getWidth()) || (ln.y2 <= fp.y) || (ln.y2 >= fp.y + fp.mPanel.getHeight()))
        { 
            ln.x2 = fp.x + fp.mPanel.getWidth()/2;
            ln.y2 = fp.y + fp.mPanel.getHeight()/2;     
        }   
        
        ln.UpdateLeft();
        ln.UpdateRight();
    }
    
    public void InsertNode(String str)
    {
        DefaultMutableTreeNode child =new DefaultMutableTreeNode(str);
      
        DefaultTreeModel model = (DefaultTreeModel)grTree.getModel(); 
        //model.i
        DefaultMutableTreeNode csNode = (DefaultMutableTreeNode)((DefaultMutableTreeNode)model.getRoot()).getChildAt(1);
        model.insertNodeInto(child,csNode,csNode.getChildCount());
        grTree.revalidate();
    }
    
    public void AutoLayout()
    {   
        int size = tfVec.size();
        int offsetX = 30;
        int offsetY = 50;
        
        int i;
        FormPanel fp;
        Vector order = new Vector();
        Vector level = new Vector();
        
        for(i = 0; i < size; i++)
        {
            fp = (FormPanel)tfVec.get(i);
            fp.expanded = false;
            fp.mPanel.cp.SetState(-1);
            fp.Readjust();
            fp.smallWidth = 140;
            fp.smallHeight = 40;
            fp.dirty = true;
            
            if (i != entryIndex)
            {
                fp.aLayout = 0;
            }
            else
            {
                fp.aLayout = 1;
            }
            
            tfVec.set(i, fp);
        }
        
        i = 0;
        int currentIndex;
        int currentLevel;
        int childIndex;
        
        int j;
        
        order.add(new Integer(entryIndex));
        level.add(new Integer(0));
        
        while(i < order.size())
        {
            currentIndex = ((Integer)order.get(i)).intValue();
            currentLevel = ((Integer)level.get(i)).intValue();
            
            for(j = 0; j < lineVec.size(); j++)
            {
                
                if (((LineSt)lineVec.get(j)).i == currentIndex)
                {
                    childIndex = ((LineSt)lineVec.get(j)).j;
                    fp = (FormPanel)tfVec.get(childIndex);
                    if (fp.aLayout == 0)
                    {
                        fp.aLayout = 1;
                        order.add(new Integer(childIndex));
                        level.add(new Integer(currentLevel + 1));
                        tfVec.setElementAt(fp,childIndex);
                    }
                }
            }
            i = i + 1;
        }
        
        i = 0;
        int levelWidth;
        int beginX; 
        int beginY;
        
        while (i < order.size())
        {
            
            currentIndex = i + 1;
            currentLevel = ((Integer)level.get(i)).intValue();
            beginY = 20 + currentLevel*(40 + offsetY);
            
            while (currentIndex < order.size())
            {
                if (((Integer)level.get(currentIndex)).intValue() != currentLevel)
                {
                    break;
                }
                else
                {
                    currentIndex = currentIndex + 1;
                }
            }
            
            levelWidth = (currentIndex - i - 1)*offsetX + (currentIndex - i) * 140;
            beginX = drawingPanel.getWidth() / 2 - (levelWidth/2);
            
            if (beginX < 0)
            {
                beginX = 0;
            }
            
            for(j = i; j < currentIndex; j++)
            {
                fp = (FormPanel)tfVec.get(((Integer)order.get(j)).intValue());
                fp.y = beginY;
                fp.x = beginX;
                fp.setBounds(fp.x,fp.y, fp.smallWidth, fp.smallHeight);
                tfVec.setElementAt(fp,((Integer)order.get(j)).intValue());
                beginX = beginX + (offsetX + 140);
                
            }
            i = currentIndex;
        }
        
        size = tfVec.size();
        boolean valid = true;
        int nonValidIndex = 0;
        beginY = 20; 
        beginX = drawingPanel.getWidth() / 2 + 70 + offsetX;
        for(i = 0; i < size; i++)
        {
            fp = (FormPanel)tfVec.get(i);
            
            if (fp.aLayout == 0) //Znaci ovaj nije obidjen
            {
                nonValidIndex = i;
                valid = false;
                fp.y = beginY;
                fp.x = beginX;
                fp.setBounds(fp.x,fp.y, fp.smallWidth, fp.smallHeight);
                tfVec.setElementAt(fp,i);
                beginX = beginX + (offsetX + 140);
            }
            
            
        }
        
        CheckLine();
        drawingPanel.scrollRectToVisible(new Rectangle((drawingPanel.getWidth() / 2) - ((int)(drawingPanel.getVisibleRect().getWidth()/2)), 0,(int)(drawingPanel.getVisibleRect().getWidth()), (int)(drawingPanel.getVisibleRect().getHeight())));
        setSelected(-1);
        drawingPanel.selectedLine = -1;
        drawingPanel.line = false;
        
        if (!valid)
        {
            fp = (FormPanel)tfVec.get(nonValidIndex);
            JOptionPane.showMessageDialog(this,"Form type : " + fp.Tf_mnem + " can not be reached from entry point","", JOptionPane.WARNING_MESSAGE); 
        }
        //drawingPanel.repaint();
    }
    
    /****************************************************************************/
    /***************        Poravnjavanje po lijevoj ivici      *****************/
    /****************************************************************************/
    public void AutoLayoutLeft()
    {   
        int size = tfVec.size();
        int offsetX = 30;
        int offsetY = 50;
        
        int i;
        FormPanel fp;
        Vector order = new Vector();
        Vector level = new Vector();
        
        for(i = 0; i < size; i++)
        {
            fp = (FormPanel)tfVec.get(i);
            fp.expanded = false;
            fp.mPanel.cp.SetState(-1);
            fp.Readjust();
            fp.smallWidth = 140;
            fp.smallHeight = 40;
            fp.dirty = true;
            
            if (i != entryIndex)
            {
                fp.aLayout = 0;
            }
            else
            {
                fp.aLayout = 1;
            }
            
            tfVec.set(i, fp);
        }
        
        i = 0;
        int currentIndex;
        int currentLevel;
        int childIndex;
        
        int j;
        
        order.add(new Integer(entryIndex));
        level.add(new Integer(0));
        
        while(i < order.size())
        {
            currentIndex = ((Integer)order.get(i)).intValue();
            currentLevel = ((Integer)level.get(i)).intValue();
            
            for(j = 0; j < lineVec.size(); j++)
            {
                
                if (((LineSt)lineVec.get(j)).i == currentIndex)
                {
                    childIndex = ((LineSt)lineVec.get(j)).j;
                    fp = (FormPanel)tfVec.get(childIndex);
                    if (fp.aLayout == 0)
                    {
                        fp.aLayout = 1;
                        order.add(new Integer(childIndex));
                        level.add(new Integer(currentLevel + 1));
                        tfVec.setElementAt(fp,childIndex);
                    }
                }
            }
            i = i + 1;
        }
        
        i = 0;
        int levelWidth;
        int beginX; 
        int beginY;
        
        while (i < order.size())
        {
            
            currentIndex = i + 1;
            currentLevel = ((Integer)level.get(i)).intValue();
            beginY = 20 + currentLevel*(40 + offsetY);
            
            while (currentIndex < order.size())
            {
                if (((Integer)level.get(currentIndex)).intValue() != currentLevel)
                {
                    break;
                }
                else
                {
                    currentIndex = currentIndex + 1;
                }
            }
            
            levelWidth = (currentIndex - i - 1)*offsetX + (currentIndex - i) * 140;
            beginX = 20;
            
            if (beginX < 0)
            {
                beginX = 0;
            }
            
            for(j = i; j < currentIndex; j++)
            {
                fp = (FormPanel)tfVec.get(((Integer)order.get(j)).intValue());
                fp.y = beginY;
                fp.x = beginX;
                fp.setBounds(fp.x,fp.y, fp.smallWidth, fp.smallHeight);
                tfVec.setElementAt(fp,((Integer)order.get(j)).intValue());
                beginX = beginX + (offsetX + 140);
                
            }
            i = currentIndex;
        }
        
        size = tfVec.size();
        boolean valid = true;
        int nonValidIndex = 0;
        beginY = 20; 
        beginX = 140 + offsetX;
        for(i = 0; i < size; i++)
        {
            fp = (FormPanel)tfVec.get(i);
            
            if (fp.aLayout == 0) //Znaci ovaj nije obidjen
            {
                nonValidIndex = i;
                valid = false;
                fp.y = beginY;
                fp.x = beginX;
                fp.setBounds(fp.x,fp.y, fp.smallWidth, fp.smallHeight);
                tfVec.setElementAt(fp,i);
                beginX = beginX + (offsetX + 140);
            }
            
            
        }
        
        CheckLine();
        drawingPanel.scrollRectToVisible(new Rectangle(0, 0,(int)(drawingPanel.getVisibleRect().getWidth()), (int)(drawingPanel.getVisibleRect().getHeight())));
        setSelected(-1);
        drawingPanel.selectedLine = -1;
        drawingPanel.line = false;
        
        if (!valid)
        {
            fp = (FormPanel)tfVec.get(nonValidIndex);
            JOptionPane.showMessageDialog(this,"Form type : " + fp.Tf_mnem + " can not be reached from entry point","", JOptionPane.WARNING_MESSAGE); 
        }
        //drawingPanel.repaint();
    }
    
    
    /****************************************************************************/
    /***************        Poravnjavanje po desnoj ivici      *****************/
    /****************************************************************************/
    public void AutoLayoutRight()
    {   
        int size = tfVec.size();
        int offsetX = 30;
        int offsetY = 50;
        
        int i;
        FormPanel fp;
        Vector order = new Vector();
        Vector level = new Vector();
        
        for(i = 0; i < size; i++)
        {
            fp = (FormPanel)tfVec.get(i);
            fp.expanded = false;
            fp.mPanel.cp.SetState(-1);
            fp.Readjust();
            fp.smallWidth = 140;
            fp.smallHeight = 40;
            fp.dirty = true;
            
            if (i != entryIndex)
            {
                fp.aLayout = 0;
            }
            else
            {
                fp.aLayout = 1;
            }
            
            tfVec.set(i, fp);
        }
        
        i = 0;
        int currentIndex;
        int currentLevel;
        int childIndex;
        
        int j;
        
        order.add(new Integer(entryIndex));
        level.add(new Integer(0));
        
        while(i < order.size())
        {
            currentIndex = ((Integer)order.get(i)).intValue();
            currentLevel = ((Integer)level.get(i)).intValue();
            
            for(j = 0; j < lineVec.size(); j++)
            {
                
                if (((LineSt)lineVec.get(j)).i == currentIndex)
                {
                    childIndex = ((LineSt)lineVec.get(j)).j;
                    fp = (FormPanel)tfVec.get(childIndex);
                    if (fp.aLayout == 0)
                    {
                        fp.aLayout = 1;
                        order.add(new Integer(childIndex));
                        level.add(new Integer(currentLevel + 1));
                        tfVec.setElementAt(fp,childIndex);
                    }
                }
            }
            i = i + 1;
        }
        
        i = 0;
        int levelWidth;
        int beginX; 
        int beginY;
        
        while (i < order.size())
        {
            
            currentIndex = i + 1;
            currentLevel = ((Integer)level.get(i)).intValue();
            beginY = 20 + currentLevel*(40 + offsetY);
            
            while (currentIndex < order.size())
            {
                if (((Integer)level.get(currentIndex)).intValue() != currentLevel)
                {
                    break;
                }
                else
                {
                    currentIndex = currentIndex + 1;
                }
            }
            
            levelWidth = (currentIndex - i - 1)*offsetX + (currentIndex - i) * 140;
            beginX = drawingPanel.getWidth() - (offsetX + 140);
            
            
            for(j = i; j < currentIndex; j++)
            {
                fp = (FormPanel)tfVec.get(((Integer)order.get(j)).intValue());
                fp.y = beginY;
                fp.x = beginX;
                fp.setBounds(fp.x,fp.y, fp.smallWidth, fp.smallHeight);
                tfVec.setElementAt(fp,((Integer)order.get(j)).intValue());
                beginX = beginX - (offsetX + 140);
                
            }
            i = currentIndex;
        }
        
        size = tfVec.size();
        boolean valid = true;
        int nonValidIndex = 0;
        beginY = 20; 
        beginX = drawingPanel.getWidth() - (140 + offsetX) * 2;
        
        for(i = 0; i < size; i++)
        {
            fp = (FormPanel)tfVec.get(i);
            
            if (fp.aLayout == 0) //Znaci ovaj nije obidjen
            {
                nonValidIndex = i;
                valid = false;
                fp.y = beginY;
                fp.x = beginX;
                fp.setBounds(fp.x,fp.y, fp.smallWidth, fp.smallHeight);
                tfVec.setElementAt(fp,i);
                beginX = beginX - (offsetX + 140);
            }
            
            
        }
        
        CheckLine();
        drawingPanel.scrollRectToVisible(new Rectangle(new Point(drawingPanel.getWidth(), 0)));
        setSelected(-1);
        drawingPanel.selectedLine = -1;
        drawingPanel.line = false;
        
        if (!valid)
        {
            fp = (FormPanel)tfVec.get(nonValidIndex);
            JOptionPane.showMessageDialog(this,"Form type : " + fp.Tf_mnem + " can not be reached from entry point","", JOptionPane.WARNING_MESSAGE); 
        }
        //drawingPanel.repaint();
    }
    
    public boolean Validate()
    {
        int size = tfVec.size();
        errMessage = "";
        
        FormPanel fp;
        Vector order = new Vector();
        int i;
        
        for(i = 0; i < size; i++)
        {
            fp = (FormPanel)tfVec.get(i);
            
            if (i != entryIndex)
            {
                fp.aLayout = 0;
            }
            else
            {
                fp.aLayout = 1;
            }
            tfVec.set(i, fp);
        }
        
        i = 0;
        int currentIndex;
        int childIndex;
        
        int j;
        
        order.add(new Integer(entryIndex));
       
        
        while(i < order.size())
        {
            currentIndex = ((Integer)order.get(i)).intValue();
            
            for(j = 0; j < lineVec.size(); j++)
            {
                
                if (((LineSt)lineVec.get(j)).i == currentIndex)
                {
                    childIndex = ((LineSt)lineVec.get(j)).j;
                    fp = (FormPanel)tfVec.get(childIndex);
                    
                    if (fp.aLayout == 0)
                    {
                        fp.aLayout = 1;
                        order.add(new Integer(childIndex));
                        tfVec.setElementAt(fp,childIndex);
                    }
                }
            }
            
            i = i + 1;
        }
        
        if(0 == tfVec.size())
        {
            errMessage = "Diagram does not contain entry point";
            return false;
        }
        
        j = 0;
        fp = null;
        
        while(j < tfVec.size())
        {
            fp = (FormPanel)tfVec.get(j);
            
            if (fp.aLayout == 0)
            {
                break;
            }
            
            j = j + 1;
        }
        
        
        if(j == tfVec.size())
        {
            errMessage = "";
            return true;
        }
        else
        {
            errMessage = "Form type : " + fp.Tf_mnem + " can not be reached from entry point";
            return false;
        }
    }
    
    private class CustomCellRenderer extends	JLabel implements	TreeCellRenderer
    {
        private ImageIcon[]	iiscImage;
        private boolean selected;
        
        public CustomCellRenderer()
        {
            // Ucitaj ikonice za drvo
            iiscImage= new ImageIcon[12];
            iiscImage[0] = new ImageIcon(IISFrameMain.class.getResource("icons/form.gif"));
            iiscImage[4] = new ImageIcon(IISFrameMain.class.getResource("icons/menu.gif"));
            iiscImage[1] = new ImageIcon(IISFrameMain.class.getResource("icons/folder.gif"));
            iiscImage[2] = new ImageIcon(IISFrameMain.class.getResource("icons/attribute.gif"));
            iiscImage[3] = new ImageIcon(IISFrameMain.class.getResource("icons/object.gif"));	
            iiscImage[5] = new ImageIcon(IISFrameMain.class.getResource("icons/baic2.gif"));
            iiscImage[6] = new ImageIcon(IISFrameMain.class.getResource("icons/cs1.gif"));
            iiscImage[7] = new ImageIcon(IISFrameMain.class.getResource("icons/forms.gif"));
            iiscImage[8] = new ImageIcon(IISFrameMain.class.getResource("icons/formsall.gif"));
            iiscImage[9] = new ImageIcon(IISFrameMain.class.getResource("icons/appchild.gif"));
            iiscImage[10] = new ImageIcon(IISFrameMain.class.getResource("icons/app.gif"));
            iiscImage[11] = new ImageIcon(IISFrameMain.class.getResource("icons/apps.gif"));
        }
    
        public Component getTreeCellRendererComponent( JTree tree,
                                              Object value, boolean bSelected, boolean bExpanded,
                                              boolean bLeaf, int iRow, boolean bHasFocus )
        {
            // Find out which node we are rendering and get its text
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
            Object[]	Path =  node.getUserObjectPath();
            String	labelText = (String)node.getUserObject();
             
            setText( labelText );
            setFont(new Font("SansSerif",0,11));
            ///setForeground(Color.black);
            selected = bSelected;
            
            if( Path.length == 1 )
            {
                setIcon( iiscImage[5] );
                return this;
            }
            
            if(Path[Path.length - 1].toString()=="Form Types")
            {
                setIcon( iiscImage[8] );
                return this;
            }
            
            if(Path[Path.length - 1].toString() == "Component types" || Path[Path.length -1].toString()=="Attributes" || Path[Path.length -1].toString()=="Associations")
            {
                setIcon( iiscImage[1] );
                return this;
            }
            
            if(Path[Path.length - 1].toString() == "Child Application Systems")
            {
                setIcon( iiscImage[11] );
                return this;
            }
            
            if(Path[Path.length - 2].toString() == "Child Application Systems")
            {
                setIcon( iiscImage[9] );
                return this;
            }
            
            if(Path[Path.length - 2].toString() == "Component types")
            {
                setIcon( iiscImage[3] );
                return this;
            }
            
            if(Path[Path.length - 2].toString()=="Attributes")
            {
                setIcon( iiscImage[2] );
                return this;
            }
            
            if(Path[Path.length - 2].toString()=="Associations")
            {
                setIcon( iiscImage[6] );
                return this;
            }
            
            if((Path[Path.length - 1].toString()=="Owned") || (Path[Path.length - 1].toString()=="Referenced"))
            {
                setIcon( iiscImage[7] );
                return this;
            }
            
            if((Path[Path.length - 2].toString()=="Owned") || (Path[Path.length - 2].toString()=="Referenced"))
            {
                setIcon( getFormIcon(labelText) );
                return this;
            }
            
            
            
            if( Path.length == 2 )
            {
                setIcon( iiscImage[10] );
                return this;
            }
            
            setIcon( iiscImage[3] );
            return this;
            
        }
        
        private ImageIcon getFormIcon(String s)
        { 
           try
            {
              JDBCQuery query=new JDBCQuery(conn);
              ResultSet rs;
              rs=query.select("select * from IISC_FORM_TYPE  where TF_mnem='"+ s + "' and PR_id=" + PR_id);
              
              if(rs.next())
              {
                  int j=rs.getInt("Tf_use");
                  query.Close();
                  if (j < 2)
                  {
                    return iiscImage[0];
                  }
                  
                  return iiscImage[4];
                  
              }
              else
              {
                  query.Close();
              }
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }  
        return iiscImage[0];
        }
        
        public void paint( Graphics g )
        {
            Color		bColor;
            
      
          // Set the correct background color
          if (selected)
          {
              bColor = SystemColor.textHighlight;
              setForeground(Color.white);
          }
          else
          {
              bColor = Color.white;
              setForeground(Color.black);
          }
          g.setColor(bColor);
          g.fillRect( 0, 0, getWidth() - 1, getHeight() - 1 );
      
          super.paint( g );
        }
    
    }
    
    private class Attribute
    {
        public int Att_id;
        public int Tob_id;
        public String Att_mnem;
        
        private Attribute(int _Att_id, String _Att_mnem, int _Tob_id)
        {
            Att_id = _Att_id;
            Tob_id = _Tob_id;
            Att_mnem = _Att_mnem;
        }
    }
    
    private class CompType
    {
        public int Tob_id;
        public String Tob_mnem;
        public int Tob_superord;
        
        private CompType(int _Tob_id, String _Tob_mnem, int _Tob_superord)
        {
           
            Tob_id = _Tob_id;
            Tob_mnem = _Tob_mnem;
            Tob_superord = _Tob_superord;
        }
    } 
}     

