package iisc;

import java.awt.datatransfer.StringSelection;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JCheckBox;
import java.awt.Dimension;
import java.awt.*;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.dnd.*;

public class CalledForms extends JPanel 
{

    private int width;
    private int height;
    private int PR_id;
    public int Tf_id;
    private Connection conn;
    private IISFrameMain parent;
    private Form form;
    private Vector calledTf;
    private Vector unCldTf;
    private Vector csids;
    private BAForms ba;
    private BAForms emptyForm;
    private JComboBox baCombo;
    private JButton prevBtn;
    private JButton nextBtn;
    private JButton prevABtn;
    private JButton nextABtn;
    private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
    private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
    private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
    private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
    private int baCount;
    
    public CalledForms(int _width, int _height, IISFrameMain _parent, Form _form, Connection _conn, int _PR_id)
    {
        width = _width;
        height = _height;
        parent = _parent;
        form = _form;
        PR_id = _PR_id;
        Tf_id = form.id;
        conn = _conn;
        csids = new Vector();
        setBounds(0, 0, width, height);
        setLayout(null);
        InitBaCombo();
        baCombo.setBounds(70, 10, 170, 20);
        add(baCombo);
        InitButtons();
        
        prevABtn.setEnabled(false);
        prevBtn.setEnabled(false);
         
        if (baCount < 1)
        {
            nextBtn.setEnabled(false);
            nextABtn.setEnabled(false); 
        }
        
        baCombo.setSelectedIndex(0);
        
        emptyForm = new BAForms(width, height, parent, form, conn, -1, baCombo.getSelectedItem().toString(), this);
        add(emptyForm);
        /*
        if (baCount > 0) //Ako u projektu postoji definisana neka business aplikacija
        {
            ba = new BAForms(width, height, parent, form, conn, PR_id, baCombo.getSelectedItem().toString());
            add(ba);
        }
        else
        {
            
        }*/
    }
    
    private void InitButtons()
    {
        prevABtn = new JButton();
        prevABtn.setBounds(10, 10, 25, 20);
        prevABtn.setIcon(iconPrevv);
        prevBtn = new JButton();
        prevBtn.setBounds(40, 10, 25, 20);
        prevBtn.setIcon(iconPrev);
        
        nextBtn = new JButton();
        nextBtn.setBounds(245, 10, 25, 20);
        nextBtn.setIcon(iconNext);
        nextABtn = new JButton();
        nextABtn.setBounds(275, 10, 25, 20);
        nextABtn.setIcon(iconNextt);
        
        nextBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
              next_ActionPerformed(ae);
            }
        });

        nextABtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
               nextABtn_ActionPerformed(ae);
            }
        });
            
        prevBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                prevBtn_ActionPerformed(ae);
            }
        });

        prevABtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
               prevABtn_ActionPerformed(ae);
            }
        });

        add(prevABtn);
        add(prevBtn);
        add(nextBtn);
        add(nextABtn);
    }
    
    private void InitBaCombo()
    {
        baCombo = new JComboBox(); 
        
        try
        {
              Statement statement = conn.createStatement();
              ResultSet rs = statement.executeQuery("select BA_mnem from IISC_BUSINESS_APPLICATION where PR_id=" + PR_id);
              baCount = 0;
              
              baCombo.addItem("");
              
              while(rs.next())
              {   
                  baCombo.addItem(rs.getString("BA_mnem"));
                  baCount = baCount + 1;
              }
              rs.close();   
              
              baCombo.addItemListener(new ItemListener()
              {
                  public void itemStateChanged(ItemEvent e)
                  {
                    baCombo_itemStateChanged(e);
                  }
              });
          }
          catch(SQLException sqle)
          {
              System.out.println("Sql exception :" + sqle);
          }
    
    }
    
    /***********************************************************************/
    /*          Event handleri za navigaciju                               */
    /***********************************************************************/
    public void next_ActionPerformed(ActionEvent ae)
    {
       int selectedItem = baCombo.getSelectedIndex();
       
       selectedItem = selectedItem + 1;
       
       prevBtn.setEnabled(true);
       prevABtn.setEnabled(true);
       
       if ( selectedItem == baCombo.getItemCount() - 1 )
       {
          nextBtn.setEnabled(false);
          nextABtn.setEnabled(false);
       }
       
       baCombo.setSelectedIndex(selectedItem);
    }
    
    public void nextABtn_ActionPerformed(ActionEvent ae)
    {
       prevBtn.setEnabled(true);
       prevABtn.setEnabled(true);
       nextBtn.setEnabled(false);
       nextABtn.setEnabled(false);
       
       baCombo.setSelectedIndex(baCombo.getItemCount() - 1);
    }
    
    public void prevBtn_ActionPerformed(ActionEvent ae)
    {
       int selectedItem = baCombo.getSelectedIndex();
       
       selectedItem = selectedItem - 1;
       
       nextABtn.setEnabled(true);
       nextBtn.setEnabled(true);
       
       if ( selectedItem == 0 )
       {
          prevBtn.setEnabled(false);
          prevABtn.setEnabled(false);
       }
       
       baCombo.setSelectedIndex(selectedItem);
    }
    
    public void prevABtn_ActionPerformed(ActionEvent ae)
    {
       int selectedItem = baCombo.getSelectedIndex();
       
       selectedItem = selectedItem - 1;
       
       nextABtn.setEnabled(true);
       nextBtn.setEnabled(true);
       prevBtn.setEnabled(false);
       prevABtn.setEnabled(false);
       
       baCombo.setSelectedIndex(0);
    }
    
    
    public void baCombo_itemStateChanged(ItemEvent e)
    {
        try
        {
             if (ba != null )
             {
                remove(ba);
                remove(ba.emptyPanel);
                remove((JPanel)ba.pvPanels.get(ba.selectedIndex));
             }
             else
             {
                 remove(emptyForm);             
                 remove(emptyForm.emptyPanel);
             }
        }
        catch(Exception ex)
        {
        }
        
        if ( baCombo.getSelectedIndex() == 0)
        {
            
            add(emptyForm);
            add(emptyForm.emptyPanel);
            ba = null;
        }
        else
        {
            ba = new BAForms(width, height, parent, form, conn, PR_id, baCombo.getSelectedItem().toString(), this);
            add(ba);
        }
        
        //ba.revalidate();
        revalidate();
        repaint();
    }
                
    public void Update()
    {
        if ((ba != null) || (baCombo.getSelectedIndex() > 0))
        {
            ba.Update();
        }
    }
      
    public class BAForms extends JPanel implements DragSourceListener, DragGestureListener
    {
        private int width;
        private int height;
        private int PR_id;
        private int Tf_id;
        private Connection conn;
        private IISFrameMain parent;
        private Form form;
        private Vector calledTf;
        private Vector unCldTf;
        private Vector csids;
        
        private Vector pvPanels;
        private PasedValuePanel pp;
        private PasedValuePanel emptyPanel;
        
        private JList remList;
        private JList calledList;
        private DefaultListModel cldf;
        private DefaultListModel rldf;
        private ListTransferHandler ltf = new ListTransferHandler();
        private JButton rBtn = new JButton("<");
        private JButton raBtn = new JButton("<<");
        private JButton lBtn = new JButton(">");
        private JButton laBtn = new JButton(">>");
        private JLabel ofLbl = new JLabel("Other Form Types");
        private JLabel cfLbl = new JLabel("Called Form Types");
    
        private int selectedIndex;
        private DragSource ds;
        private StringSelection transferable = new StringSelection("");
        private DragSource ds2;
        private StringSelection transferable2;
        private ImageIcon iiscImage = new ImageIcon(IISFrameMain.class.getResource("icons/form.gif"));
        
        private int from = -1;
        public int BA_id;
        private int beginX = 0;
        private int beginY = 0;
        public String BA_mnem;
        CalledForms cf;
        private JScrollPane sp1 = new JScrollPane();
        private JScrollPane sp2 = new JScrollPane();
        
        public BAForms(int _width, int _height, IISFrameMain _parent, Form _form, Connection _conn, int _PR_id, String _BA_mnem, CalledForms _cf)
        {
            width = _width;
            selectedIndex = -1;
            height = _height;
            parent = _parent;
            form = _form;
            PR_id = _PR_id;
            Tf_id = form.id;
            conn = _conn;
            BA_mnem = _BA_mnem;
            cf = _cf;
            
            InitBussinesApplication();
                        
            csids = new Vector();
            setBounds(0, 40, 385, height);
            setLayout(null);
            InitCalledListBox();
            InitRemListBox();
            AddButtons();
            pvPanels = new Vector();
            InitPassedValuePanels();
            remList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            calledList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            if (cldf.getSize() == 0)
            {
                rBtn.setEnabled(false);
                raBtn.setEnabled(false);
            }
            
            if (rldf.getSize() == 0)
            {
                lBtn.setEnabled(false);
                laBtn.setEnabled(false);
            }
            
            ds = new DragSource();
            DragGestureRecognizer dgr = ds.createDefaultDragGestureRecognizer(remList,
            DnDConstants.ACTION_MOVE, this);
            
            ds2 = new DragSource();
            DragGestureRecognizer dgr2 = ds2.createDefaultDragGestureRecognizer(calledList,
            DnDConstants.ACTION_MOVE, this);
        }
        
        /***********************************************************************/
        /*******      Inicijalizacija imena bussines applikacije          ******/
        /***********************************************************************/
        private void InitBussinesApplication()
        {
            try
            {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select * from IISC_BUSINESS_APPLICATION where PR_id =" + PR_id + " and BA_mnem='" + BA_mnem + "'");
                
                if (rs.next())
                {   
                    BA_id = rs.getInt("BA_id");
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
        
        /***********************************************************************/
        public void dragGestureRecognized(DragGestureEvent dge) 
        {
            transferable = new StringSelection("");
            if (dge.getDragSource().equals(ds))
            {
                from = 0;
                ds2.startDrag(dge, DragSource.DefaultMoveDrop, iiscImage.getImage(),new Point(10, 10),transferable, this);
            }
            else
            {
                from = 1;
                ds.startDrag(dge, DragSource.DefaultMoveDrop, iiscImage.getImage(),new Point(10, 10),transferable, this);
            }
            //System.out.println("Drag Gesture Recognized!" + dge.getDragOrigin().getX());
            //transferable = new StringSelection(remList.getSelectedValue().toString());
            //ds.startDrag(dge, DragSource.DefaultMoveDrop, transferable, this);
            
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
          if (from == 0)
          {
              if ((dsde.getX() > calledList.getLocationOnScreen().getX()) && (dsde.getX() < calledList.getLocationOnScreen().getX() + calledList.getWidth()))
              {
                  if ((dsde.getY() > calledList.getLocationOnScreen().getY()) && (dsde.getY() < calledList.getLocationOnScreen().getY() + calledList.getHeight()))
                  {
                      FromRemaindedToCalled();
                  }
              }
          }
          else
          {
              if ((dsde.getX() > remList.getLocationOnScreen().getX()) && (dsde.getX() < remList.getLocationOnScreen().getX() + remList.getWidth()))
              {
                  if ((dsde.getY() > remList.getLocationOnScreen().getY()) && (dsde.getY() < remList.getLocationOnScreen().getY() + remList.getHeight()))
                  {
                      FromCalledToRemainded();
                  }
              }
          }
          /*
          try
          {
              
              System.out.print("Drag Drop End: ");
              System.out.println(dsde.getX());
              System.out.println(transferable.getTransferData(DataFlavor.stringFlavor).toString());
          } 
          catch (IOException io) 
          {
              io.printStackTrace();
              //      dropTargetDropEvent.rejectDrop();
          }
          catch (UnsupportedFlavorException ufe) 
          {
              ufe.printStackTrace();
              //dropTargetDropEvent.rejectDrop();
          }
          */
      }
    
      public void dropActionChanged(DragSourceDragEvent dsde) 
      {
          //System.out.println("Drop Action Changed");
      }
      /***********************************************************************/
        private void InitCalledListBox()
        {
            calledTf = new Vector();
            int i = 0;
            cldf = new DefaultListModel();
            
            try
            {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select * from IISC_CALLING_STRUCT,IISC_FORM_TYPE where IISC_CALLING_STRUCT.Called_Tf_id=IISC_FORM_TYPE.Tf_id and Caller_Tf_id =" + Tf_id + " and BA_id=" + BA_id + " order by Tf_id");
                
                while(rs.next())
                {   
                    calledTf.add(new FormValue(rs.getInt("Tf_id"), rs.getString("Tf_mnem")));
                    csids.add(new Integer(rs.getInt("CS_id")));
                    i = i + 1;
                }
                rs.close();
                
            }
            catch(SQLException sqle)
            {
                //System.out.println("Sql exception :" + sqle);
            }
            
                
            for(int j = 0; j < i; j ++)
            {
                cldf.addElement(((FormValue)calledTf.get(j)).Tf_mnem);
            }
            
            cfLbl.setBounds(230,10, 150, 15);
            add(cfLbl);
            
            calledList = new JList(cldf);
            
            sp1.setBounds(230, 30, 150, 180);
            sp1.setBorder(new LineBorder(new Color(124, 124, 124), 1));
            sp1.getViewport().add(calledList);
            
            //calledList.setBackground(new Color(173, 189, 133));;
            //calledList.setCellRenderer(new CustomCellRenderer());
            calledList.setCellRenderer(new CustomCellRenderer());
            calledList.addListSelectionListener(new ListSelectionListener()
            {
                public void valueChanged(ListSelectionEvent e)
                {
                    ChangeSelectedPanel();
                }
            });
            
            add(sp1);
        }
        
        private void InitRemListBox()
        {
            unCldTf = new Vector();
            int i = 0;
            int k = 0;
            boolean exists = false;
            int cfSize = calledTf.size();
            int uTf_id;
            
            try
            {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ PR_id + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id order by IISC_FORM_TYPE.TF_id");
                
                while(rs.next())
                {   
                    uTf_id = rs.getInt("Tf_id");
                    exists = false;
                    
                    if (uTf_id == Tf_id)
                    {
                        exists = true;
                    }
                    else
                    {
                        k = 0;
                        
                        while( k < cfSize)
                        {
                            if (uTf_id == ((FormValue)calledTf.get(k)).Tf_id)
                            {
                                exists = true;
                                break;
                            }
                            
                            if (uTf_id < ((FormValue)calledTf.get(k)).Tf_id)
                            {
                                break;
                            }
                            
                            k = k + 1;
                        }
                    }
                    
                    if (!exists)
                    {
                        unCldTf.add(new FormValue(uTf_id, rs.getString("Tf_mnem")));
                        i = i + 1;
                    }
                }
                rs.close();            
            }
            catch(SQLException sqle)
            {
                System.out.println("Sql exception :" + sqle);
            }
            rldf = new DefaultListModel();
            
            for(int j = 0; j < i; j ++)
            {
                rldf.addElement(((FormValue)unCldTf.get(j)).Tf_mnem);
            }
            remList = new JList(rldf);
            remList.setCellRenderer(new CustomCellRenderer());
            
            ofLbl.setBounds(10, 10, 150, 15);
            add(ofLbl);
            
            sp2.setBounds(10, 30, 150, 180);
            sp2.setBorder(new LineBorder(new Color(124, 124, 124), 1));
            
            remList.setDragEnabled(true);
            remList.setTransferHandler(ltf);
            
            remList.addMouseListener(new MouseAdapter()
                {
                    public void mousePressed(MouseEvent e) 
                    {
                        //rl_mp(e);
                    }
                }
            );
            
            sp2.getViewport().add(remList);
            add(sp2); 
        }
        
        private void rl_mp(MouseEvent e)
        {
            JComponent c = (JComponent)e.getSource();
            ListTransferHandler handler = (ListTransferHandler)c.getTransferHandler();
            handler.exportAsDrag(c, e, TransferHandler.MOVE);
        }
        
        private void InitPassedValuePanels()
        {
           int size = calledTf.size();
           emptyPanel = new PasedValuePanel(200, 200, parent, form, conn, Tf_id, -1, -1, BA_id);
           emptyPanel.setBounds(390, 19, 340, 230);
           emptyPanel.tabPane.setEnabled(false);
           
           for(int i = 0; i < size; i++)
           {
              pp = new PasedValuePanel(200, 200, parent, form, conn, Tf_id,((FormValue)calledTf.get(i)).Tf_id,((Integer)csids.get(i)).intValue(), BA_id);
              pp.setBounds(390, 19, 340, 230);
              pvPanels.add(pp);
           }
           
           if (size > 0)
           {
              cf.add((PasedValuePanel)pvPanels.get(0));
              calledList.setSelectedIndex(0);
           }
           else
           {
              cf.add(emptyPanel);
           }
           
           repaint();
        }
        
        private void AddButtons()
        {
            rBtn.setBounds(170, 50, 50, 30);
            raBtn.setBounds(170, 90, 50, 30);
            lBtn.setBounds(170, 130, 50, 30);
            laBtn.setBounds(170, 170, 50, 30);
            
            rBtn.setFont(new Font("SansSerif", 0, 14));
            raBtn.setFont(new Font("SansSerif", 0, 14));
            lBtn.setFont(new Font("SansSerif", 0, 14));
            laBtn.setFont(new Font("SansSerif", 0, 14));
            
            rBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                  rBtn_ActionPerformed(ae);
                }
            });
            
            raBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                  raBtn_ActionPerformed(ae);
                }
            });
            
            lBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                  lBtn_ActionPerformed(ae);
                }
            });
            
            laBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                  laBtn_ActionPerformed(ae);
                }
            });
            
            add(rBtn);
            add(raBtn);
            add(lBtn);
            add(laBtn);
        }
        
        
        /*************************************************************************/
        /****************       Upis podataka sa forme u bazu  *******************/
        /*************************************************************************/
        public void Update()
        {
            int i;

            for(i = 0; i < calledTf.size(); i++)
            {
                PasedValuePanel pp = (PasedValuePanel)pvPanels.get(i);
                pp.SetSelectedIndex(pp.index);
            }
            
            try
            {
                AddFormPanel(Tf_id);
                
                int CS_id;
                
                Statement statement = conn.createStatement();
                Statement statement2 = conn.createStatement();
                ResultSet rs;
                
                //Brisanje calling struktura i update dijagrama
                for(i = 0; i < unCldTf.size(); i++)
                {
                    rs = statement2.executeQuery("select CS_id from IISC_CALLING_STRUCT where Called_Tf_id=" +((FormValue)unCldTf.get(i)).Tf_id + " and Caller_Tf_id =" + Tf_id + " and BA_id=" + BA_id);
                
                    if (rs.next())
                    {
                        CS_id = rs.getInt(1);
                        
                        statement.execute("delete from IISC_PASSED_VALUE where CS_id=" + CS_id);
                        statement.execute("delete from IISC_CALL_GRAPH_VERTEX where CS_id=" + CS_id);
                        statement.execute("delete from IISC_CALLING_STRUCT where CS_id=" + CS_id);
                        
                    }
                }
                
                
                /*
                for(i = 0; i < csids.size(); i++)
                {
                    statement.execute("delete from IISC_PASSED_VALUE where CS_id=" + ((Integer)csids.get(i)).intValue());
                    statement.execute("delete from IISC_CALLING_STRUCT where CS_id=" + ((Integer)csids.get(i)).intValue());
    
                }
                */
                
                
                int max = 0;
                
                try
                {
                    rs = statement.executeQuery("select max(CS_id) from IISC_CALLING_STRUCT");
                    if(rs.next())
                    {
                        max = rs.getInt(1);    
                    }
                    else
                    {
                        max = 0;
                    }
                }
                catch(SQLException sqle)
                {
                    max = 0;
                }
                
                max = max + 1;
                
                int Pv_max = 0;
                
                try
                {
                    rs = statement.executeQuery("select max(PV_id) from IISC_PASSED_VALUE");
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
                
                int j;
                String query = "";
                statement2.close();
                statement2 = conn.createStatement();
                
                for(i = 0; i < calledTf.size(); i++)
                {
                    PasedValuePanel pp = (PasedValuePanel)pvPanels.get(i);
                    
                    if (pp.CS_id == -1)//Ako je nova calling struktura dodata i ne postoji u bazi
                    {
                        //Ako ne postoji dodaj i jedan panel
                        AddFormPanel(((FormValue)calledTf.get(i)).Tf_id);
                        //Dodaj liniju, 
                        query = "insert into IISC_CALLING_STRUCT(CS_id,Caller_Tf_id,Called_Tf_id,CMet_id,CMod_id,CS_call_UI_position,PR_id, BA_id) values(" + max + "," + Tf_id + "," + ((FormValue)calledTf.get(i)).Tf_id;
                          
                        if(pp.selOpenChb.isSelected())
                        {
                              if(pp.restectedChb.isSelected())
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
                              if(pp.restectedChb.isSelected())
                              {
                                  query = query + ",1";
                              }
                              else
                              {
                                  query = query + ",3";
                              }
                          }
                          
                          if(pp.modalBtn.isSelected())
                          {
                              query = query + ",0";
                          }
                          else
                          {
                              if(pp.closeChb.isSelected())
                              {
                                  query = query + ",2";
                              }
                              else
                              {
                                  query = query + ",1";
                              }
                          }
                          
                          if(pp.miChb.isSelected())
                          {
                              if(pp.btnChb.isSelected())
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
                        statement2.execute(query);
                         
                        statement2.execute("insert into IISC_CALL_GRAPH_VERTEX(CS_id,caller_Tf,called_Tf,X1,Y1,X2,Y2,visible,PR_id,BA_id) values(" + max + "," + Tf_id + "," + ((FormValue)calledTf.get(i)).Tf_id + "," + 0 + "," + 0 + "," + 0 + "," + 0 + ",1," + PR_id + "," + BA_id + ")");
                        
                        
                        //query = "insert into IISC_CALLING_STRUCT(CS_id,Caller_Tf_id,Called_Tf_id,CMet_id,CMod_id,PR_id, BA_id) values(" + max + "," + Tf_id + "," + ((FormValue)calledTf.get(i)).Tf_id + ",0,0," +PR_id + "," + BA_id + ")";
                        //System.out.println(query);
                        //statement2.execute(query);
                        
                        int size = pp.lp.parIds.size();
                        
                        for(j = 0; j < size; j++)
                        {
                            if (((RadioValue)pp.radioVector.get(j)).enabled)
                            {
                                query = "insert into IISC_PASSED_VALUE(PV_id,CS_id,PV_To_Par_id,PV_type,PV_value,Att_id,Tob_id,Tf_id,PV_From_Par_id,Pr_id) values(" + Pv_max + "," + max + "," + ((Parameter)pp.lp.parIds.get(j)).Par_id + "," + ((RadioValue)pp.radioVector.get(j)).type  + ",'" +  ((RadioValue)pp.radioVector.get(j)).txtValue + "',";
                                
                                if (pp.attVec.size() > 0)
                                {
                                    query = query + ((Attribute)pp.attVec.get(((RadioValue)pp.radioVector.get(j)).attCmbIndex)).Att_id + "," + ((Attribute)pp.attVec.get(((RadioValue)pp.radioVector.get(j)).attCmbIndex)).Tob_id + "," + Tf_id + ","; 
                                }
                                else
                                {
                                    query = query + "0,0,0,"; 
                                }
                                
                                if (pp.parVec.size() > 0)
                                {
                                    query = query + ((Parameter)pp.parVec.get(((RadioValue)pp.radioVector.get(j)).parCmbIndex)).Par_id + ","  + PR_id + ")";
                                }
                                else
                                {
                                    query = query + "0,";
                                }
                                query = query + "" + PR_id + ")";
                                //System.out.println(query);

                                statement2.execute(query);
                                Pv_max = Pv_max + 1;
                            }
                        }
                        max = max + 1;
                    }
                    else
                    {
                        query = "update IISC_CALLING_STRUCT set ";                  
                        
                        if(pp.selOpenChb.isSelected())
                        {
                            if(pp.restectedChb.isSelected())
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
                            if(pp.restectedChb.isSelected())
                            {
                                query = query + "CMet_id=1,";
                            }
                            else
                            {
                                query = query + "CMet_id=3,";
                            }
                        }
                        
                        if(pp.modalBtn.isSelected())
                        {
                            query = query + "CMod_id=0,";
                        }
                        else
                        {
                            if(pp.closeChb.isSelected())
                            {
                                query = query + "CMod_id=2,";
                            }
                            else
                            {
                                query = query + "CMod_id=1,";
                            }
                        }
                        
                        if(pp.miChb.isSelected())
                        {
                            if(pp.btnChb.isSelected())
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
                        
                        query = query + " where CS_id=" + pp.CS_id;
                        
                        System.out.println(query);
                        statement.execute(query);
                        
                        int size = pp.lp.parIds.size();
                        statement2.execute("delete from IISC_PASSED_VALUE where CS_id=" + pp.CS_id);
                        
                        
                        for(j = 0; j < size; j++)
                        {
                            if (((RadioValue)pp.radioVector.get(j)).enabled)
                            {
                            
                                query = "insert into IISC_PASSED_VALUE(PV_id,CS_id,PV_To_Par_id,PV_type,PV_value,Att_id,Tob_id,Tf_id,PV_From_Par_id,Pr_id) values(" + Pv_max + "," + pp.CS_id + "," + ((Parameter)pp.lp.parIds.get(j)).Par_id + "," + ((RadioValue)pp.radioVector.get(j)).type  + ",'" +  ((RadioValue)pp.radioVector.get(j)).txtValue + "',";
                                
                                if (pp.attVec.size() > 0) 
                                {
                                    query = query  + ((Attribute)pp.attVec.get(((RadioValue)pp.radioVector.get(j)).attCmbIndex)).Att_id + "," + ((Attribute)pp.attVec.get(((RadioValue)pp.radioVector.get(j)).attCmbIndex)).Tob_id + "," + Tf_id + ",";                              
                                }
                                else
                                {
                                    query = query  + "0,0,0,";                              
                                }
                                
                                if (pp.parVec.size() > 0)
                                {
                                    query = query + ((Parameter)pp.parVec.get(((RadioValue)pp.radioVector.get(j)).parCmbIndex)).Par_id + ",";
                                }
                                else
                                {
                                    query = query + "0,";
                                }
                                
                                query = query + PR_id + ")";
                                //System.out.print(query + "\n");
                        
                                statement2.execute(query);
                                Pv_max = Pv_max + 1;
                            }
                        }
                    }
                }
            }
            catch(SQLException sqle)
            {
                System.out.print(sqle.toString() + "\n");
            }
        }
        
        
        /*************************************************************************/
        /****  Procedura koja dodaje novi cvor grafa ako on ne postoji u bazi   **/
        /*************************************************************************/
        private void AddFormPanel(int Tf_id)
        {
           
            boolean exists = false;
            try
            {
                ResultSet rs;
                Statement statement = conn.createStatement();
                
                String q = "select * from IISC_CALL_GRAPH_NODE where Tf_id=" + Tf_id + " and BA_id=" + BA_id;
                
                rs = statement.executeQuery(q);
                
                if (rs.next())
                {
                    exists = true;
                }
                else
                {
                    exists = false;
                }
                
            }
            catch(SQLException e)
            {
                exists = false;
            } 
            
            if(!exists)
            {
                try
                {
                
                    String str=new String();
                    ResultSet rs;
                    
                    Statement statement = conn.createStatement();
                    double temp = Math.random();
                    int posiX = 0;
                    int posiY = 0;
                    
                    if ((beginX + (int)(temp* 300)) > 9800)
                    {
                        posiX = beginX - (int)(temp* 300);
                    }
                    else
                    {
                        posiX = beginX + (int)(temp* 300);
                    }
                    
                    temp = Math.random();
                    
                    if ((beginY + (int)(temp* 300)) > 9800)
                    {
                        posiY = beginY - (int)(temp* 300);
                    }
                    else
                    {
                        posiY = beginY + (int)(temp* 300);
                    }
                    
                    String q = "insert into IISC_CALL_GRAPH_NODE(Tf_id,Pos_x,Pos_y,mCTCHeight,mParCHeight,mCTEHeight,mParEHeight, mWidth, smallWidth,smallHeight,expanded,cExpanded,pExpanded,visible,PR_id, BA_id) values(" + Tf_id + "," + posiX + "," + posiY + ",";
                    q = q + "20,20,80,80,200,140,40,";
                    
                    q = q + "0,0,0,0," + PR_id + "," + BA_id + ")";
                    //System.out.println("Upit je" + q);
                    statement.execute( q );
                    statement.close();
                }
                catch(SQLException e)
                {
                    System.out.println(e.toString());
                } 
            } 
        }

        /*************************************************************************/
        /****************                 Event Handleri       *******************/
        /*************************************************************************/
        public void lBtn_ActionPerformed(ActionEvent ae)
        {
            FromRemaindedToCalled();
        }
        
        public void rBtn_ActionPerformed(ActionEvent ae)
        {
            FromCalledToRemainded();
        }
        
        public void FromRemaindedToCalled()
        {
            
            int index = remList.getSelectedIndex();
            
            if (index < 0)
            {
                return;
            }
            
            form.btnApply.setEnabled(true);
            form.btnSave.setEnabled(true);
            
            if (cldf.size() == 0)
            {
                cf.remove(emptyPanel);
            }
            //Napravi novi panel
            int Cs_id = -1; 
            
            //Ispitaj da li postoji vec ta calling struktura u bazi
            try
            {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select CS_id from IISC_CALLING_STRUCT where PR_id=" + PR_id + " and BA_id=" + BA_id + " and Caller_Tf_id=" + Tf_id + " and Called_Tf_id=" + ((FormValue)unCldTf.get(index)).Tf_id );
                
                if (rs.next())
                {
                    Cs_id = rs.getInt(1);
                }
                else
                {
                    Cs_id = -1;
                }
            }
            catch(SQLException sqle)
            {
                Cs_id = -1;
            }
            
            pp = new PasedValuePanel(200, 200, parent, form, conn, Tf_id,((FormValue)unCldTf.get(index)).Tf_id, Cs_id , BA_id);
            pp.setBounds(390, 19, 340, 230);
            pvPanels.add(pp);
            
            rldf.remove(index); //Ukloni iz spiska nepozvanih
            cldf.addElement(((FormValue)unCldTf.get(index)).Tf_mnem); //dodaj u spisak pozvanih
            
            //Dodaj iz vektora pozvanih u vector nepozvanih
            calledTf.add(unCldTf.get(index));
            unCldTf.remove(index);
            
            if (cldf.size() == 1)
            {
                calledList.setSelectedIndex(0);;
            }
            
            int size = rldf.getSize();
            
            if (size == 0) 
            { 
                lBtn.setEnabled(false);
                laBtn.setEnabled(false);
            }
            else 
            { 
                if (index == rldf.getSize()) 
                {
                    index--;
                }
                if (index > -1)
                {
                    remList.setSelectedIndex(index);
                    remList.ensureIndexIsVisible(index); 
                }
            }
            
            if(!raBtn.isEnabled())
            {
                raBtn.setEnabled(true);
                rBtn.setEnabled(true);
            }
        }
        
        public void FromCalledToRemainded()
        {
            int index = calledList.getSelectedIndex();
            
            if (index < 0)
            {
                return;
            }
            
            form.btnApply.setEnabled(true);
            form.btnSave.setEnabled(true);
            
            cldf.remove(index);   //ukloni iz list modela s desne strane
            rldf.addElement(((FormValue)calledTf.get(index)).Tf_mnem);  //dodaj u listu sa lijeve strane 
            unCldTf.add(calledTf.get(index)); //dodaj u spisak nepozvanih formi
            calledTf.remove(index); //Ukloni iz spiska pozvanih 
             
            int size = cldf.getSize();
            int temp = index;
            
            if (size == 0) 
            { 
                rBtn.setEnabled(false);
                raBtn.setEnabled(false);
                cf.remove((PasedValuePanel)pvPanels.get(0));
                pvPanels.removeAllElements();
                cf.add(emptyPanel);
                repaint();
            }
            else 
            { 
                cf.remove((PasedValuePanel)pvPanels.get(index));
                pvPanels.remove(index);
                if (index == cldf.getSize()) 
                {
                    index--;
                }
                calledList.setSelectedIndex(index);
                calledList.ensureIndexIsVisible(index);
                 //Ukloni panel
            }
            
            if (!laBtn.isEnabled())
            {   
                laBtn.setEnabled(true);
                lBtn.setEnabled(true);
            }
            
            form.btnApply.setEnabled(true);
        
        }
        public void raBtn_ActionPerformed(ActionEvent ae)
        {
            int size = calledTf.size();
            int index = calledList.getSelectedIndex();
            
            if (index > -1)
            {
                cf.remove((PasedValuePanel)pvPanels.get(index));
            }
            form.btnApply.setEnabled(true);
            form.btnSave.setEnabled(true);
            
            
            
            cldf.removeAllElements();
            
            for(int i = 0; i < size; i++)
            {
                rldf.addElement(((FormValue)calledTf.get(i)).Tf_mnem);
                unCldTf.add(calledTf.get(i));
                
            }
            
            
            pvPanels.removeAllElements();
            
            calledTf.removeAllElements();
            cf.add(emptyPanel);
            repaint();
            rBtn.setEnabled(false);
            raBtn.setEnabled(false);
            laBtn.setEnabled(true);
            lBtn.setEnabled(true);
            
            
            
        }
        
        public void laBtn_ActionPerformed(ActionEvent ae)
        {
            int size = unCldTf.size();
            
            rldf.removeAllElements();
            
            cf.remove(emptyPanel);
            
            form.btnApply.setEnabled(true);
            form.btnSave.setEnabled(true);
            
            for(int i = 0; i < size; i++)
            {
                cldf.addElement(((FormValue)unCldTf.get(i)).Tf_mnem);
                calledTf.add(unCldTf.get(i));
                
                int Cs_id = -1; 
                //Ispitaj da li postoji vec ta calling struktura u bazi
                try
                {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select CS_id from IISC_CALLING_STRUCT where PR_id=" + PR_id + " and BA_id=" + BA_id + " and Caller_Tf_id=" + Tf_id + " and Called_Tf_id=" + ((FormValue)unCldTf.get(i)).Tf_id );
                    
                    if (rs.next())
                    {
                        Cs_id = rs.getInt(1);
                    }
                    else
                    {
                        Cs_id = -1;
                    }
                }
                catch(SQLException sqle)
                {
                    Cs_id = -1;
                }
                pp = new PasedValuePanel(200, 200, parent, form, conn, Tf_id,((FormValue)calledTf.get(i)).Tf_id, Cs_id, BA_id);
                pp.setBounds(390, 19, 340, 230);
                pvPanels.add(pp);    
            }
            
            unCldTf.removeAllElements();
            rBtn.setEnabled(true);
            raBtn.setEnabled(true);
            laBtn.setEnabled(false);
            lBtn.setEnabled(false);
            
        }
        
        private void ChangeSelectedPanel()
        {
            int index = calledList.getSelectedIndex();
            
            if (index < 0)
            {
                return;
            }
            if(pvPanels.size() == 0)
            {
                return;
            }
            
            //Ukloni panel sa strane
            try
            {
                //cf.remove(emptyPanel);
                cf.remove((PasedValuePanel)pvPanels.get(selectedIndex));
            }
            catch(Exception e)
            {
            }
            repaint();
            selectedIndex = index;
            
            if (selectedIndex >= pvPanels.size())
            {
                return;
            }
            
            cf.add((PasedValuePanel)pvPanels.get(selectedIndex));
            
            //((PasedValuePanel)pvPanels.get(selectedIndex)).repaint();
            ((PasedValuePanel)pvPanels.get(selectedIndex)).revalidate();
            ((PasedValuePanel)pvPanels.get(selectedIndex)).getListPanel().RepaintAll();
            
            this.repaint();
            repaint();
            cf.revalidate();
            cf.repaint();
            
        }
      
        public class PasedValuePanel extends JPanel 
        { 
            private int width;
            private int height;
            private int PR_id;
            private int Tf_id;
            private int calledTf;
            private int CS_id;
            private int BA_id;
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
             
            public PasedValuePanel(int _width, int _height, IISFrameMain _parent, Form _form, Connection _conn, int _Tf_id, int _calledTf, int _CS_id, int _BA_id)
            {
                width = _width;
                height = _height;
                parent = _parent;
                form = _form;
                PR_id = form.tree.ID;
                Tf_id = form.id;
                conn = _conn;
                BA_id = _BA_id;
                Tf_id = _Tf_id;
                calledTf = _calledTf;
                CS_id = _CS_id;
                
                setLayout(null);
                
                setBounds(0, 0, width, height);
                tabPane.setBounds(5, 0, 335, 230);
                //bindingPanel.setBounds(35, 0, 360, 240);
                tabPane.addTab("Binding", bindingPanel);
                tabPane.addTab("Options", optionPanel);
                add(tabPane);
                
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
                
                repaint();
                lp.RepaintAll();
                //rp.AddListeners();
            }
            
             private void InitMetodPanel()
             {
                 
                 //Metod panel
                 cmLbl.setBounds(5, 10, 70, 20);
                 metodPanel.setBounds(80, 12, 240, 50);
                 metodPanel.setBorder(new LineBorder(new Color(124, 124, 124), 1));
                 
                 selOpLbl.setBounds(35, 5, 150, 17);
                 resOpLbl.setBounds(35, 30, 150, 17);
                 selOpenChb.setText("");
                 
                 selOpenChb.setBounds(180, 5, 17, 17);
                 restectedChb.setBounds(180, 30, 17, 17);
                 
                 metodPanel.add(selOpLbl);
                 metodPanel.add(resOpLbl);
                 metodPanel.add(selOpenChb);
                 metodPanel.add(restectedChb);
                 
                 optionPanel.add(metodPanel);
                 optionPanel.add(cmLbl);
                 
                 //Metod Panel
                  modeLbl.setBounds(5, 83, 70, 20);
                  modePanel.setBounds(80, 80, 240, 50);
                  modePanel.setBorder(new LineBorder(new Color(124, 124, 124), 1));
                  
                  closePanel.setBounds(100, 0, 140, 50);
                  closePanel.setBorder(new LineBorder(new Color(124, 124, 124), 1));
                  modePanel.add(closePanel);
                  
                  modalLbl.setBounds(10, 5, 100, 17);
                  nonmodalLbl.setBounds(10, 30, 100, 17);
                  modalBtn.setBounds(70, 5, 17, 17);
                  
                  modalBtn.setText("");
                  nonModalBtn.setBounds(70, 30, 17, 17);
                  nonModalBtn.setText("");
                  modePanel.add(modalLbl);
                  modePanel.add(nonmodalLbl);
                  modePanel.add(modalBtn);
                  modePanel.add(nonModalBtn);
                  
                  closeLbl.setBounds(10, 20, 100, 17);
                  closeChb.setBounds(100, 20, 17, 17);
                  closePanel.setLayout(null);
                  closePanel.add(closeLbl);
                  closePanel.add(closeChb);
                 
                  optionPanel.add(modePanel);
                  optionPanel.add(modeLbl);
                  
                  //dio za ui prikaz
                   uiLbl.setBounds(5, 147, 70, 20);
                   uiPanel.setBounds(80, 146, 240, 50);
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
                   InitFromDataBase();
                 
                 /*
                 selOpenChb.addActionListener(new ActionListener()
                 {
                       public void actionPerformed(ActionEvent ae)
                       {
                           form.btnApply.setEnabled(true);
                           form.btnSave.setEnabled(true);
                           
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
                         form.btnApply.setEnabled(true);
                         form.btnSave.setEnabled(true);
                         
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
                         form.btnApply.setEnabled(true);
                         form.btnSave.setEnabled(true);
                         
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
                         form.btnApply.setEnabled(true);
                         form.btnSave.setEnabled(true);
                         
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
                   
                       form.btnApply.setEnabled(true);
                       form.btnSave.setEnabled(true);
                       modalBtn.setSelected(true);
                       nonModalBtn.setSelected(false);
                       closeChb.setEnabled(false);
                   }
                 });
              
                 nonModalBtn.addActionListener(new ActionListener()
                 {
                   public void actionPerformed(ActionEvent ae)
                   {
                       form.btnApply.setEnabled(true);
                       form.btnSave.setEnabled(true);
                       
                       modalBtn.setSelected(false);
                       nonModalBtn.setSelected(true);
                       closeChb.setEnabled(true);
                   }
                 });
                 
                 
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
                                 //selOpenChb.setSelected(true);
                                 modalBtn.setSelected(true);
                                 miChb.setSelected(true);
                                 closeChb.setEnabled(false);
                             }
                         }
                         catch(SQLException sqle)
                         {
                             //selOpenChb.setSelected(true);
                             modalBtn.setSelected(true);
                             miChb.setSelected(true);
                             closeChb.setEnabled(false);
                         }
                     }
                     else//ako ne postoji u bazi onda inicijalizovatai praznu
                     {
                         //selOpenChb.setSelected(true);
                         modalBtn.setSelected(true);
                         miChb.setSelected(true);
                         closeChb.setEnabled(false);
                     }
             }
                 
            public void InitRadioPanel()
            {
                rp = new RadioPanel(100, 100);
                rp.setBounds(135, 25, 190, 170);
                parLbl.setBounds(145, 5, 190, 15);
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
                if (radioVector.size() < 0)
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
                    //System.out.println("select * from IISC_PASSED_VALUE where CS_id=" + CS_id + " and BA_id=" + BA_id + " order by PV_To_Par_id");
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
            private class ListPanel  extends JPanel
            {
                private int index;
                private JPanel panel;
                private PasedValuePanel pPanel;
                private ParameterPanel pp;
                private Vector parIds;
                private Vector pPanels;
                private int calledTf;
                private Connection conn;
                JScrollBar sb;
                JScrollBar sb2;
                JScrollPane sp;
                
                public ListPanel(int _width, int _height, PasedValuePanel _pPanel, int _calledTf, Connection _conn)
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
                    setBounds(5, 25, 140, 170);
                    
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
                    lChb.setBackground(SystemColor.textHighlight);
                    setForeground(Color.white);
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
                    lChb.setBackground(Color.WHITE);
                    setForeground(Color.black);
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
                    form.btnApply.setEnabled(true);
                    form.btnSave.setEnabled(true);
                    
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
                    constRBtn.setBounds(20, 27, 20, 20);
                    add(constRBtn);
                    
                    constTxt = new JTextField("");
                    constTxt.setBounds(50, 27, 130, 20);
                    add(constTxt);
                                
                    attLbl = new JLabel();
                    attLbl.setText("Attribute");
                    attLbl.setBounds(50, 64, 130, 10);
                    add(attLbl);
                    
                    attRBtn = new JRadioButton();
                    attRBtn.setBounds(20, 79, 20, 20);
                    add(attRBtn);
                    
                    attCombo = new JComboBox();
                    attCombo.setBounds(50, 79, 130, 20);
                    add(attCombo);
                    
                    parLbl = new JLabel();
                    parLbl.setText("Parameter");
                    parLbl.setBounds(50, 116, 100, 10);
                    add(parLbl);
                    
                    parRBtn = new JRadioButton();
                    parRBtn.setBounds(20, 131, 20, 20);
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
                    
                    constTxt.addKeyListener(new KeyAdapter()
                    {
                        public void keyTyped(KeyEvent e)
                        {
                          form.btnApply.setEnabled(true);
                          form.btnSave.setEnabled(true);
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
                    
                    if(constRBtn.isSelected())
                    {
                        form.btnApply.setEnabled(true);
                        form.btnSave.setEnabled(true);
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
                    if (rp.attCombo.getItemCount() == 0)
                    {
                        rp.attRBtn.setSelected(false);
                        return;
                    }
                    rp.attRBtn.setSelected(true);
                    
                    if(attRBtn.isSelected())
                    {
                        form.btnApply.setEnabled(true);
                        form.btnSave.setEnabled(true);
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
                    if (rp.parCombo.getItemCount() == 0)
                    {
                        rp.parRBtn.setSelected(false);
                        return;
                    }
                    rp.parRBtn.setSelected(true); 
                    
                    if(parRBtn.isSelected())
                    {
                        form.btnApply.setEnabled(true);
                        form.btnSave.setEnabled(true);
                        rp.constTxt.setEnabled(false);
                        rp.attCombo.setEnabled(false);
                        rp.parCombo.setEnabled(true);
                        rp.constRBtn.setSelected(false);
                        rp.attRBtn.setSelected(false);
                        //rp.parRBtn.setSelected(true);              
                    }
                }
                
                public void AddListeners()
                {
                    attCombo.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                          System.out.print(e.getActionCommand()+ "\n" );
                          //if (e.getModifiers() & InputEvent.
                          //form.btnApply.setEnabled(true);
                          //form.btnSave.setEnabled(true);
                        }
                    });
                    
                    parCombo.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                          //form.btnApply.setEnabled(true);
                          //form.btnSave.setEnabled(true);
                        }
                    });
                }
            
            }
            
            
        }
        
        public class RadioValue
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
        
        private class FormValue
        {
            public int Tf_id;
            public String Tf_mnem;
            
            public FormValue(int _Tf_id, String _Tf_mnem)
            {
                Tf_id = _Tf_id;
                Tf_mnem = _Tf_mnem;
            }
        }
        
        
        private class CustomCellRenderer extends  DefaultListCellRenderer  implements ListCellRenderer<Object> 
        {
            
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) 
            {
                JLabel label =(JLabel)super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
    
                String s = value.toString();
                label.setText(s);
                label.setIcon(iiscImage);
                
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
        
        public class ListTransferHandler extends TransferHandler 
        {
            private int indices = -1;
            private int addIndex = -1; 
            private int addCount = 0;  
                
        
            protected String exportString(JComponent c) 
            {
                JList list = (JList)c;
                indices = list.getSelectedIndex();
                Object value = list.getSelectedValue();
                return value.toString();
            
            }
            
            protected void importString(JComponent c, String str) 
            {
                JList target = (JList)c;
                DefaultListModel listModel = (DefaultListModel)target.getModel();
                int index = target.getSelectedIndex();
                int max = listModel.getSize();
                
                if (index < 0) 
                {
                    index = max;
                } 
                else 
                {
                    index++;
                    if (index > max) 
                    {
                        index = max;
                    }
                }
                addIndex = index;
                listModel.add(index, str);
            }
        
            protected void cleanup(JComponent c, boolean remove) 
            {
                if (remove && (indices > -1)) 
                {
                    JList source = (JList)c;
                    DefaultListModel model  = (DefaultListModel)source.getModel();
                    model.remove(indices);
                    
                }
                indices = -1;
                addCount = 0;
                addIndex = -1;
            }
        }
    }
}