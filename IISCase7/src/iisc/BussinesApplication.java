package iisc;
import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionEvent;
import java.awt.Color;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class BussinesApplication extends JDialog 
{
    
    private Connection conn;
    private String BA_mnem;
    private IISFrameMain parent;
    private int PR_id;
    private int BA_id = -1;
    
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JButton applyBtn = new JButton();
    private JButton okBtn = new JButton();
    private JButton jButton3 = new JButton();
    private JButton cancelBtn = new JButton();
    private JTextPane descTxt = new JTextPane();
    private JTextField mnemTxt = new JTextField();
    private JButton jButton5 = new JButton();
    private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel jPanel1 = new JPanel();
    private JButton prevv = new JButton();
    private JButton deleteBtn = new JButton();
    private JButton newBtn = new JButton();
    private JButton prev = new JButton();
    private JComboBox tfCombo = new JComboBox();
    private JComboBox baCombo = new JComboBox();
    private JButton jButton9 = new JButton();
    private JButton jButton10 = new JButton();
    private JToolBar jToolBar1 = new JToolBar();
    private JButton nextt = new JButton();
    
    private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
    private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
    private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
    private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
    private JPanel jPanel2 = new JPanel();
    private PTree tree;
    private int selectedIndex;
    private String As_mnem;
    private int As_id;
    
    public BussinesApplication(IISFrameMain _parent, Connection _conn, String _BA_mnem, int _PR_id, String _As_mnem, PTree _tree) 
    {
    
        super((JFrame)_parent, "Business Application", true);
        
        conn = _conn;
        BA_mnem = _BA_mnem;
        parent = _parent;
        PR_id = _PR_id;
        As_mnem = _As_mnem;
        tree = _tree;
        
        InitAs();
        InitForm();
        InitCombo();
        
        if (BA_mnem.equals(""))
        {
            InitEmptyBusinessApplication();
        }
        else
        {
            InitBusinessApplication();
        }
        
        //Disablovati ova dva dugmeta na pocetku
        AddEventHandlers();
        okBtn.setEnabled(false);
        applyBtn.setEnabled(false);
    }
    
    private void InitAs()
    {
        try
        {
            Statement statement2 = conn.createStatement();
            
            ResultSet rs = statement2.executeQuery("select AS_id from IISC_APP_SYSTEM  where AS_name='" + As_mnem + "'");
           
            if (rs.next())
            {
                As_id = rs.getInt("AS_id");    
            }
            
        }
        catch(SQLException e)
        {
            System.out.println(e.toString());
        }       
    }
    private void InitForm()
    {
        jButton3.setSize(new Dimension(35, 30));
        setSize(new Dimension(458, 269));
        getContentPane().setLayout(null);
        setTitle("Business applications");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(new Rectangle(10, 10, 472, 403));
        jLabel3.setText("Name");
        jLabel3.setBounds(new Rectangle(5, 15, 65, 20));
        jLabel4.setText("Entry form type");
        jLabel4.setBounds(new Rectangle(5, 55, 115, 15));
        jLabel5.setText("Description");
        jLabel5.setBounds(new Rectangle(5, 95, 110, 10));
        applyBtn.setText("Apply");
        applyBtn.setBounds(new Rectangle(10, 330, 75, 30));
        applyBtn.setToolTipText("null");
        applyBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              applyBtn_actionPerformed(e);
            }
        });
        
        okBtn.setText("OK");
        okBtn.setBounds(new Rectangle(255, 330, 75, 30));
        okBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              okBtn_actionPerformed(e);
            }
        });
        jButton3.setBounds(new Rectangle(420, 330, 35, 30));
        jButton3.setIcon(imageHelp);
        jButton3.setMaximumSize(new Dimension(35, 30));
        jButton3.setMinimumSize(new Dimension(35, 30));
        cancelBtn.setText("Cancel");
        cancelBtn.setBounds(new Rectangle(335, 330, 80, 30));
        cancelBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              cancelBtn_actionPerformed(e);
            }
        });
        descTxt.setBounds(new Rectangle(90, 95, 340, 140));
        descTxt.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        mnemTxt.setBounds(new Rectangle(90, 15, 340, 20));
        jButton5.setText("...");
        jButton5.setBounds(new Rectangle(400, 55, 30, 20));
        jButton5.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              jButton5_actionPerformed(e);
            }
        });
        jTabbedPane1.setBounds(new Rectangle(10, 45, 445, 275));
        jPanel1.setLayout(null);
        jPanel1.setSize(new Dimension(445, 275));
        prevv.setBounds(new Rectangle(25, 5, 25, 20));
        prevv.setIcon(iconPrevv);
        prevv.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              prevv_actionPerformed(e);
            }
        });
        deleteBtn.setText("Delete");
        deleteBtn.setBounds(new Rectangle(90, 330, 75, 30));
        deleteBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              deleteBtn_actionPerformed(e);
            }
        });
        newBtn.setText("New");
        newBtn.setBounds(new Rectangle(170, 330, 80, 30));
        newBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              newBtn_actionPerformed(e);
            }
        });
        prev.setBounds(new Rectangle(55, 5, 25, 20));
        prev.setIcon(iconPrev);
        prev.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              prev_actionPerformed(e);
            }
        });
        tfCombo.setBounds(new Rectangle(90, 55, 300, 20));
        baCombo.setBounds(new Rectangle(85, 5, 245, 20));
        jButton9.setText("jButton9");
        jButton9.setBounds(new Rectangle(370, 15, 35, 25));
        jButton10.setBounds(new Rectangle(335, 5, 25, 20));
        jButton10.setIcon(iconNext);
        jButton10.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              jButton10_actionPerformed(e);
            }
        });
        jToolBar1.setBounds(new Rectangle(35, 10, 400, 30));
        jToolBar1.setLayout(null);
        jToolBar1.setFloatable(false);
        nextt.setBounds(new Rectangle(365, 5, 25, 20));
        nextt.setIcon(iconNextt);
        nextt.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              nextt_actionPerformed(e);
            }
        });
        
        jPanel1.add(tfCombo, null);
        jPanel1.add(jLabel3, null);
        jPanel1.add(mnemTxt, null);
        jPanel1.add(descTxt, null);
        jPanel1.add(jButton5, null);
        jPanel1.add(jLabel5, null);
        jPanel1.add(jLabel4, null);
        jToolBar1.add(nextt, null);
        jToolBar1.add(prev, null);
        jToolBar1.add(prevv, null);
        jToolBar1.add(baCombo, null);
        jToolBar1.add(jButton10, null);
        getContentPane().add(jToolBar1, null);
        //getContentPane().add(jButton9, null);
        getContentPane().add(newBtn, null);
        getContentPane().add(deleteBtn, null);
        getContentPane().add(cancelBtn, null);
        getContentPane().add(jButton3, null);
        getContentPane().add(okBtn, null);
        getContentPane().add(applyBtn, null);
        jTabbedPane1.addTab("Bussines Application", jPanel1);
        jTabbedPane1.addTab("Notes", jPanel2);
        getContentPane().add(jTabbedPane1, null);
    }
    
    /*************    Dodavanje event hendlera ****************/
    private void AddEventHandlers()
    {
        
        tfCombo.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            tfCombo_itemStateChanged(e);
          }
        });
        
        baCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              baCombo_itemStateChanged(e);
            }
        });
       
        mnemTxt.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
              mnemTxt_keyTyped(e);
            }
        });
        descTxt.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
              descTxt_keyTyped(e);
            }
        });
    }
    /*******************************************************************/
    /*******     Inicijalizacija vrijedosti kontrola        ************/
    /*******************************************************************/
    private void InitBusinessApplication()
    {
        try
        {
            Statement statement2 = conn.createStatement();
            
            ResultSet rs = statement2.executeQuery("select * from IISC_BUSINESS_APPLICATION,IISC_FORM_TYPE where IISC_BUSINESS_APPLICATION.PR_id="+ PR_id + " and IISC_BUSINESS_APPLICATION.TF_entry_id=IISC_FORM_TYPE.TF_id and IISC_BUSINESS_APPLICATION.BA_mnem='" + BA_mnem + "'");
            String Tf_mnem = "";
            mnemTxt.setText(BA_mnem);
            baCombo.setSelectedItem(BA_mnem);
            selectedIndex = baCombo.getSelectedIndex();
                
            if (rs.next())
            {
                Tf_mnem = rs.getString("Tf_mnem");
                BA_id = rs.getInt("BA_id");    
                descTxt.setText(rs.getString("BA_desc"));
            }
            else
            {
                InitEmptyBusinessApplication();
                return;
            }
            
            Statement statement = conn.createStatement();
            rs = statement.executeQuery("select * from IISC_CALL_GRAPH_NODE,IISC_FORM_TYPE where IISC_FORM_TYPE.PR_id="+ PR_id + " and IISC_CALL_GRAPH_NODE.Tf_id=IISC_FORM_TYPE.Tf_id and IISC_CALL_GRAPH_NODE.BA_id=" + BA_id);
            
            tfCombo.removeAllItems();
            while(rs.next())
            {
                tfCombo.addItem(rs.getString("Tf_mnem"));
            }
            tfCombo.setSelectedItem(Tf_mnem);
            
            okBtn.setEnabled(false);
            applyBtn.setEnabled(false);
            statement.close();
            statement2.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            InitEmptyBusinessApplication();
            
        }       
    }
    
    
    /*******************************************************************/
    /**  Inicijalizacija vrijedosti kontrola ako je nova BA        *****/
    /*******************************************************************/
    private void InitEmptyBusinessApplication()
    {
        int count = 0;
        
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ PR_id + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id and IISC_APP_SYSTEM.AS_id=" + As_id);
            selectedIndex = 0;
            
            tfCombo.removeAllItems();
            
            while(rs.next())
            {
                tfCombo.addItem(rs.getString("Tf_mnem"));
                count = count + 1;
            }
            
            okBtn.setEnabled(false);
            applyBtn.setEnabled(false);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            okBtn.setEnabled(false);
            applyBtn.setEnabled(false);
        }       
        
        if (count == 0)
        {
            JOptionPane.showMessageDialog(this, "There is any form type","Error",JOptionPane.ERROR_MESSAGE);
            //dispose();
        }
    }
    
    /************************************************************************/
    /*******      Procedura koja radi menagament podataka sa forme   ********/
    /************************************************************************/
    private boolean Update()
    {
    
        if (mnemTxt.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Business application name is mandatory");                 
            return false;
        }
                
        if(BA_mnem.equals("")) //Ako je nova business applikacija
        {
            try
            {
                Statement statement = conn.createStatement();
                              
                ResultSet rs = statement.executeQuery("select * from IISC_BUSINESS_APPLICATION where PR_id="+ PR_id + " and AS_id=" + As_id + " and BA_mnem='" + mnemTxt.getText() + "'");
                
                //Ako i bazi vec postoji BA sa tim imenom prijaviti gresku korisniku
                if (rs.next())
                {
                    JOptionPane.showMessageDialog(this, "Business application" + mnemTxt.getText() + " allready exists");
                    return false;
                }
                statement.close();
                
                int id = 1;
                Statement statement2 = conn.createStatement();
                
                try
                {
                    
                    rs = statement2.executeQuery("select MAX(BA_id) as max from IISC_BUSINESS_APPLICATION");
                    
                    if (rs.next())
                    {
                        id = rs.getInt("max") + 1;
                    }
                }
                catch(SQLException e) 
                {}
                statement2.close();
  
                Statement statement3 = conn.createStatement();
                int Tf_id = 0;
                
                try
                {
                    rs = statement3.executeQuery("select Tf_id from IISC_FORM_TYPE where PR_id=" + PR_id + " and Tf_mnem='" + tfCombo.getSelectedItem().toString() + "'");
                    
                    if (rs.next())
                    {
                        Tf_id = rs.getInt("Tf_id");
                    }
                }
                catch(SQLException e) 
                {
                    
                }
                statement3.close();
                
                Statement statement4 = conn.createStatement();
                
                String sql = "insert into IISC_BUSINESS_APPLICATION(BA_id,BA_mnem,BA_desc,Tf_entry_id,PR_id,AS_id,grid_visible,grid_snap,grid_size,beginX,beginY) values(";
                sql += id + ",'" + mnemTxt.getText() + "','" + descTxt.getText() + "'," + Tf_id + "," + PR_id + "," + As_id + "," + "0,0,25,0,0)";
                
                //System.out.println(sql);
                
                statement4.execute(sql);
                
                String q = "insert into IISC_CALL_GRAPH_NODE(Tf_id,Pos_x,Pos_y,mCTCHeight,mParCHeight,mCTEHeight,mParEHeight, mWidth, smallWidth,smallHeight,expanded,cExpanded,pExpanded,visible,PR_id, BA_id) values(" + Tf_id + "," + 200 + "," + 200 + ",";
                q = q + "20,20,80,80,200,140,40,";
                q = q + "0,0,0,0," + PR_id + "," + id + ")";
                
                //System.out.println("Upit je " + q);
                statement4.execute(q);
                BA_id = id;
                BA_mnem = mnemTxt.getText();
                
                DefaultMutableTreeNode child =new DefaultMutableTreeNode(BA_mnem);
      
                DefaultTreeModel model = (DefaultTreeModel)tree.tree.getModel(); 
                //model.i
                DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
                DefaultMutableTreeNode csNode = null;
                
                int i = 0;
                
            
                for (i = 0; i < root.getChildCount(); i++)
                {
                    csNode = (DefaultMutableTreeNode)root.getChildAt(i);
                    
                    if (csNode.toString().equals("Application Systems"))
                    {
                        break;
                    }
                }
                
                if (i < root.getChildCount())
                {
                    root = csNode;
                    
                    for (i = 0; i < root.getChildCount(); i++)
                    {
                        csNode = (DefaultMutableTreeNode)root.getChildAt(i);
                        
                        if (csNode.toString().equals(As_mnem))
                        {
                            break;
                        }
                    }
                    
                    if (i < root.getChildCount())
                    {
                        root = csNode;
                        
                        for (i = 0; i < root.getChildCount(); i++)
                        {
                            csNode = (DefaultMutableTreeNode)root.getChildAt(i);
                            
                            if (csNode.toString().equals("Business Applications"))
                            {
                                break;
                            }
                        }
                        
                        if (i < root.getChildCount())
                        {
                            model.insertNodeInto(child,csNode,csNode.getChildCount());
                            //tree.select_node(csNode.toString(), child.toString());
                            tree.revalidate();
                        }
                    }
                    
                }
                
                return true;
            }
            catch(SQLException e)
            {
                System.out.println(e.toString());
                return false;
            }  
        }
        else  //Treba da se uradi updejt podataka
        {
            try
            {
                ResultSet rs;
                Statement statement = conn.createStatement();
                   
                if (!BA_mnem.equals(mnemTxt.getText()))//Ako je korisnik mijenjao ime business application
                {
                    rs = statement.executeQuery("select * from IISC_BUSINESS_APPLICATION where PR_id="+ PR_id + " and BA_mnem='" + mnemTxt.getText() + "'");
                
                    //Ako u bazi vec postoji BA sa tim imenom prijaviti gresku korisniku
                     if (rs.next())
                    {
                        JOptionPane.showMessageDialog(this, "Business application" + mnemTxt.getText() + " allready exists");
                        return false;
                    }
                    statement.close();
                }
                
  
                Statement statement3 = conn.createStatement();
                int Tf_id = 0;
                
                try
                {
                    rs = statement3.executeQuery("select Tf_id from IISC_FORM_TYPE where PR_id=" + PR_id + " and Tf_mnem='" + tfCombo.getSelectedItem().toString() + "'");
                    
                    if (rs.next())
                    {
                        Tf_id = rs.getInt("Tf_id");
                    }
                }
                catch(SQLException e) 
                {
                    
                }
                statement3.close();
                
                Statement statement4 = conn.createStatement();
                
                String sql = "update IISC_BUSINESS_APPLICATION set BA_mnem='" + mnemTxt.getText() + "',BA_desc='" + descTxt.getText() + "',Tf_entry_id=" + Tf_id + " where BA_id=" + BA_id; 
                //System.out.println(sql);
                
                statement4.execute(sql);
                
                /*String q = "insert into IISC_CALL_GRAPH_NODE(Tf_id,Pos_x,Pos_y,mCTCHeight,mParCHeight,mCTEHeight,mParEHeight, mWidth, smallWidth,smallHeight,expanded,cExpanded,pExpanded,visible,PR_id, BA_id) values(" + Tf_id + "," + 200 + "," + 200 + ",";
                q = q + "20,20,80,80,200,140,40,";
                q = q + "0,0,0,0," + PR_id + "," + id + ")";
                
                statement4.execute(q);
                */
                
                /*
                
                if(option == 1)
                {
                    int selected = baCombo.getSelectedIndex();
                                       
                    baCombo.remove(selected);

                    baCombo.insertItemAt((Object)mnemTxt.getText(), selected);
                    baCombo.setSelectedIndex(selected);
                    
                }
               
                applyBtn.setEnabled(false);
                okBtn.setEnabled(false);
                */
                DefaultMutableTreeNode child =new DefaultMutableTreeNode(mnemTxt.getText());
                DefaultTreeModel model = (DefaultTreeModel)tree.tree.getModel(); 
                DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
                DefaultMutableTreeNode csNode = null;
                int i = 0;
                
                 for (i = 0; i < root.getChildCount(); i++)
                 {
                     csNode = (DefaultMutableTreeNode)root.getChildAt(i);
                     
                     if (csNode.toString().equals("Application Systems"))
                     {
                         break;
                     }
                 }
                 
                 if (i < root.getChildCount())
                 {
                     root = csNode;
                     
                     for (i = 0; i < root.getChildCount(); i++)
                     {
                         csNode = (DefaultMutableTreeNode)root.getChildAt(i);
                         
                         if (csNode.toString().equals(As_mnem))
                         {
                             break;
                         }
                     }
                     
                     if (i < root.getChildCount())
                     {
                         root = csNode;
                         
                         for (i = 0; i < root.getChildCount(); i++)
                         {
                             csNode = (DefaultMutableTreeNode)root.getChildAt(i);
                             
                             if (csNode.toString().equals("Business Applications"))
                             {
                                 break;
                             }
                         }
                         
                         DefaultMutableTreeNode tempNode = null;
                         
                         if (i < root.getChildCount())
                         {
                             root = csNode;
                             
                             for (i = 0; i < root.getChildCount(); i++)
                             {
                                 tempNode = (DefaultMutableTreeNode)root.getChildAt(i);
                                 
                                 if (tempNode.toString().equals(BA_mnem))
                                 {
                                     break;
                                 }
                             }
                         
                             if(i < root.getChildCount()) //Nasli smo cvor koji treba da zamijenimo
                             {
                                 root.remove(i);
                                 root.insert(child, i);
                                 model.reload((TreeNode)root);
                                 tree.revalidate();
                             }
                            
                             //tree.select_node(csNode.toString(), child.toString());
                             
                         }
                         
                     }   
                 }
                 
                 BA_mnem = mnemTxt.getText();
                
                return true;
            }
            catch(SQLException e)
            {
                System.out.println(e.toString());
                return false;
            }  
        }
    }
    
    /************************************************************************/
    /*******      Procedura koja brise B.A. iz drveta                ********/
    /************************************************************************/
    private void DeleteBAFromTree(String BA_mnem)
    {
        DefaultTreeModel model = (DefaultTreeModel)tree.tree.getModel(); 
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
        DefaultMutableTreeNode parent = null;
        DefaultMutableTreeNode csNode = null;
        
        int i = 0;
        
        
         for (i = 0; i < root.getChildCount(); i++)
         {
             csNode = (DefaultMutableTreeNode)root.getChildAt(i);
             
             if (csNode.toString().equals("Application Systems"))
             {
                 break;
             }
         }
         
         if (i < root.getChildCount())
         {
             root = csNode;
             
             for (i = 0; i < root.getChildCount(); i++)
             {
                 csNode = (DefaultMutableTreeNode)root.getChildAt(i);
                 
                 if (csNode.toString().equals(As_mnem))
                 {
                     break;
                 }
             }
             
             if (i < root.getChildCount())
             {
                 root = csNode;
                 
                 for (i = 0; i < root.getChildCount(); i++)
                 {
                     csNode = (DefaultMutableTreeNode)root.getChildAt(i);
                     
                     if (csNode.toString().equals("Business Applications"))
                     {
                         break;
                     }
                 }
                 
                 DefaultMutableTreeNode tempNode = null;
                 
                 if (i < root.getChildCount())
                 {
                     parent = csNode;
                     
                     for (i = 0; i < parent.getChildCount(); i++)
                     {
                         tempNode = (DefaultMutableTreeNode)parent.getChildAt(i);
                         
                         if (tempNode.toString().equals(BA_mnem))
                         {
                             break;
                         }
                     }
                 
                     if(i < parent.getChildCount()) //Nasli smo cvor koji treba da brisemo
                     {
                         parent.remove(i);
                         model.reload((TreeNode)parent);
                         tree.revalidate();
                     }
                    
                     //tree.select_node(csNode.toString(), child.toString());
                     
                 }
                 
             }   
         }
    }
    /************************************************************************/
    /*******      Jedan prosti delete cijele business applikacije    ********/
    /************************************************************************/
    public void Delete()
    {
        
        if(BA_id > -1) //Ako ima sta da se brise iz baze
        {
            try
            {
                ResultSet rs;
                Statement statement2 = conn.createStatement();
                //Brisanje iz baze podataka
                Statement statement = conn.createStatement();
                statement.execute("delete from IISC_CALL_GRAPH_NODE where BA_id=" + BA_id);
                statement.execute("delete from IISC_CALL_GRAPH_VERTEX where BA_id=" + BA_id);
                
                rs = statement2.executeQuery("select CS_id from IISC_CALLING_STRUCT where BA_id=" + BA_id);
                while ( rs.next() )
                {
                    statement.execute("delete from IISC_PASSED_VALUE where CS_id=" + rs.getInt(1));
                }
                
                statement.execute("delete from IISC_CALLING_STRUCT where BA_id=" + BA_id);
                statement.execute("delete from IISC_BUSINESS_APPLICATION where BA_id=" + BA_id);
                
                DeleteBAFromTree(BA_mnem);
            }
            catch(SQLException e)
            {
                System.out.println(e.toString());
            }  
        }
        else
        {
            descTxt.setText("");
            mnemTxt.setText("");
            BA_mnem = "";
            BA_id = -1;
            InitEmptyBusinessApplication();
            applyBtn.setEnabled(false);
            okBtn.setEnabled(false);
        }
        
        
    }
    /************************************************************************/
    /**      Delete cijele business applikacije koji se pozive iz drveta ****/
    /************************************************************************/
    public static void Delete(int BA_id, Connection conn)
    {
        
        if(BA_id > -1) //Ako ima sta da se brise iz baze
        {
            try
            {
                ResultSet rs;
                Statement statement2 = conn.createStatement();
                //Brisanje iz baze podataka
                Statement statement = conn.createStatement();
                statement.execute("delete from IISC_CALL_GRAPH_NODE where BA_id=" + BA_id);
                statement.execute("delete from IISC_CALL_GRAPH_VERTEX where BA_id=" + BA_id);
                rs = statement2.executeQuery("select CS_id from IISC_CALLING_STRUCT where BA_id=" + BA_id);
                
                while ( rs.next() )
                {
                    statement.execute("delete from IISC_PASSED_VALUE where CS_id=" + rs.getInt(1));
                }
                
                statement.execute("delete from IISC_CALLING_STRUCT where BA_id=" + BA_id);
                statement.execute("delete from IISC_BUSINESS_APPLICATION where BA_id=" + BA_id);
                
            }
            catch(SQLException e)
            {
                System.out.println(e.toString());
            }  
        }   
    }
    /************************************************************************/
    /*******      Procedura koja brise B.A. iz drveta                ********/
    /************************************************************************/
    public static void DeleteBAFromTree(String BA_mnem, PTree tree, String As_mnem)
    {
        DefaultTreeModel model = (DefaultTreeModel)tree.tree.getModel(); 
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
        DefaultMutableTreeNode parent = null;
        DefaultMutableTreeNode csNode = null;
        
        int i = 0;
        
        for (i = 0; i < root.getChildCount(); i++)
        {
            csNode = (DefaultMutableTreeNode)root.getChildAt(i);
            
            if (csNode.toString().equals("Application Systems"))
            {
                break;
            }
        }
        
        if (i < root.getChildCount())
        {
            root = csNode;
            
            for (i = 0; i < root.getChildCount(); i++)
            {
                csNode = (DefaultMutableTreeNode)root.getChildAt(i);
                
                if (csNode.toString().equals(As_mnem))
                {
                    break;
                }
            }
            
            if (i < root.getChildCount())
            {
                root = csNode;
                
                for (i = 0; i < root.getChildCount(); i++)
                {
                    csNode = (DefaultMutableTreeNode)root.getChildAt(i);
                    
                    if (csNode.toString().equals("Business Applications"))
                    {
                        break;
                    }
                }
                
                DefaultMutableTreeNode tempNode = null;
                
                if (i < root.getChildCount())
                {
                    parent = csNode;
                    
                    for (i = 0; i < parent.getChildCount(); i++)
                    {
                        tempNode = (DefaultMutableTreeNode)parent.getChildAt(i);
                        
                        if (tempNode.toString().equals(BA_mnem))
                        {
                            break;
                        }
                    }
                
                    if(i < parent.getChildCount()) //Nasli smo cvor koji treba da brisemo
                    {
                        parent.remove(i);
                        model.reload((TreeNode)parent);
                        tree.revalidate();
                    }
                   
                    //tree.select_node(csNode.toString(), child.toString());
                    
                }
                
            }   
        }
    }
    
    /*******************************************************************/
    /*******      Inicijalizacija combo boxa koji sadrzi  spisak   *****/
    /****                Biznis Aplikacija                     *********/
    /*******************************************************************/
    private void InitCombo()
    {
        try 
        { 
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select BA_mnem from IISC_BUSINESS_APPLICATION where PR_id=" + PR_id + " and AS_id=" + As_id);
            
            baCombo.setEditable(false);
            baCombo.addItem("");
            
            while (rs.next())
            {
                baCombo.addItem(rs.getString("BA_mnem"));
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    /*******************************************************************/
    /*******        Procedura za umetanje podataka sa forme ************/
    /*******************************************************************/
    private void Insert()
    {
    
    }
    
    //Ako korisnik entry poiny omoguci snimanje
    public void tfCombo_itemStateChanged(ItemEvent e)
    {
        okBtn.setEnabled(true);
        applyBtn.setEnabled(true);
    }
    
    //Ako korisnik entry poiny omoguci snimanje
    public void baCombo_itemStateChanged(ActionEvent e)
    {
        if (selectedIndex == baCombo.getSelectedIndex())
        {
            return;
        }   
       //selectedIndex = baCombo.getSelectedIndex();
        if (applyBtn.isEnabled())
        {
            int option = JOptionPane.showConfirmDialog(this, "Do you want to save changes","",JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.OK_OPTION)
            {
                if (!Update())
                {
                    baCombo.setSelectedIndex(selectedIndex);
                    return;
                }
            }       
        }
        
        selectedIndex = baCombo.getSelectedIndex();
        
        if (baCombo.getItemCount() == 0)
        {
            prev.setEnabled(false);
            prevv.setEnabled(false);
            jButton10.setEnabled(false);
            nextt.setEnabled(false);
        }
        
        BA_mnem = baCombo.getSelectedItem().toString();
        
        if (BA_mnem == "")
        {
            InitEmptyBusinessApplication();
            descTxt.setText("");
            mnemTxt.setText("");
        }
        else
        {
            InitBusinessApplication();
        }
        
        prev.setEnabled(true);
        prevv.setEnabled(true);
        jButton10.setEnabled(true);
        nextt.setEnabled(true);
        
        if (selectedIndex == 0)
        {
            prev.setEnabled(false);
            prevv.setEnabled(false);
            jButton10.setEnabled(true);
            nextt.setEnabled(true);
        }
        
        if (selectedIndex == baCombo.getItemCount() - 1)
        {
            prev.setEnabled(true);
            prevv.setEnabled(true);
            jButton10.setEnabled(false);
            nextt.setEnabled(false);
        }
        
    }
    
    public void mnemTxt_keyTyped(KeyEvent e)
    {
        okBtn.setEnabled(true);
        applyBtn.setEnabled(true);
    }
    
    public void descTxt_keyTyped(KeyEvent e)
    {
        okBtn.setEnabled(true);
        applyBtn.setEnabled(true);
    }
         
    private void applyBtn_actionPerformed(ActionEvent e)
    {
        String oldBA_mnem = BA_mnem;
        
        if (Update())
        {
            
            
            if (oldBA_mnem == "")//Ako je unesen neki podatak
            {
                baCombo.addItem(mnemTxt.getText());
                selectedIndex = baCombo.getItemCount() - 1;
                InitBusinessApplication();
                prev.setEnabled(true);
                prevv.setEnabled(true);
                jButton10.setEnabled(false);
                nextt.setEnabled(false);
            }
            else
            {
                baCombo.removeItemAt(selectedIndex);
                baCombo.insertItemAt(mnemTxt.getText(), selectedIndex);
                InitBusinessApplication();
            }
            
        }
        
    }
  
    private void deleteBtn_actionPerformed(ActionEvent e)
    {
        //Zadnje upozerenje korisniku
        if (BA_id > -1)
        {
            int option = JOptionPane.showConfirmDialog(this,"Are you sure you want to delete: " + BA_mnem, "Confirm",JOptionPane.OK_CANCEL_OPTION);
            //System.out.print(option);
            if (option == JOptionPane.OK_OPTION)
            {
                Delete();
                baCombo.removeItemAt(selectedIndex);
                selectedIndex = 0;
                baCombo.setSelectedIndex(0);
                //Setovanje controla na formi na prazne vrijednosti
                descTxt.setText("");
                mnemTxt.setText("");
                //Setovanje globalnih promjenjivih
                BA_mnem = "";
                BA_id = -1;
                //Inicijalizacija kombo boxa koji sadrzi tipove formi
                InitEmptyBusinessApplication();
                applyBtn.setEnabled(false);
                okBtn.setEnabled(false);
                prev.setEnabled(false);
                prevv.setEnabled(false);
                jButton10.setEnabled(true);
                nextt.setEnabled(true);
                
            }
        }
        else
        {
            
            //Setovanje controla na formi na prazne vrijednosti
            descTxt.setText("");
            mnemTxt.setText("");
            //Setovanje globalnih promjenjivih
            BA_mnem = "";
            BA_id = -1;
            //Inicijalizacija kombo boxa koji sadrzi tipove formi
            tfCombo.setSelectedIndex(0);
            applyBtn.setEnabled(false);
            okBtn.setEnabled(false);
            prev.setEnabled(false);
            prevv.setEnabled(false);
            jButton10.setEnabled(true);
            nextt.setEnabled(true);
        }
    }
  
    private void newBtn_actionPerformed(ActionEvent e)
    {
        BA_mnem = "";
        BA_id = -1;
        //Inicijalizacija kombo boxa koji sadrzi tipove formi
        InitEmptyBusinessApplication();
        selectedIndex = 0;
        baCombo.setSelectedIndex(0);
        descTxt.setText("");
        mnemTxt.setText("");
        applyBtn.setEnabled(false);
        okBtn.setEnabled(false);
    }
  
    private void okBtn_actionPerformed(ActionEvent e)
    {
        if (Update())
        {
            dispose();
        }
    }
  
    private void cancelBtn_actionPerformed(ActionEvent e)
    {
        if (applyBtn.isEnabled())
        {
            int option = JOptionPane.showConfirmDialog(this, "Do you want to save changes","",JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.OK_OPTION)
            {
                if (Update())
                {
                    dispose();
                }
            }
            else
            {
                dispose();
            }
        }
        else
        {
            dispose();
        }
    }
  
    private void prevv_actionPerformed(ActionEvent e)
    {
        baCombo.setSelectedIndex(0);
    }
  
    private void prev_actionPerformed(ActionEvent e)
    {
        baCombo.setSelectedIndex(selectedIndex - 1);
    }
  
    private void jButton10_actionPerformed(ActionEvent e)
    {
        baCombo.setSelectedIndex(selectedIndex + 1);
    }
  
    private void nextt_actionPerformed(ActionEvent e)
    {
        baCombo.setSelectedIndex(baCombo.getItemCount() - 1);
    }
  
    private void jButton5_actionPerformed(ActionEvent e)
    {
    
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
        private int BA_id;
        
        public SearchTable(Frame parent, String title, boolean modal,Connection con, int _BA_id)
        {
            super(parent, title, modal);
            BA_id = _BA_id;
            
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
          if(BA_id == -1)
          {
              qtm.setFormQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.PR_id="+ PR_id + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id and TF_mnem like '%"+ txtSearch.getText() +"%'"); 
          }
          else
          {
              qtm.setFormQuery("select * from IISC_CALL_GRAPH_NODE,IISC_FORM_TYPE where IISC_FORM_TYPE.PR_id="+ PR_id + " and IISC_CALL_GRAPH_NODE.TF_id=IISC_FORM_TYPE.TF_id and IISC_CALL_GRAPH_NODE.BA_id=" + BA_id); 
          }
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
    private class Filter extends JDialog
    { 
          private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
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
              {
                  connection=con;
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
              {
                  connection=con;
                  owner1=own;
                  jbInit1();
              }
              catch(Exception e)
              {
                  e.printStackTrace();
              }
      
          }
          private void jbInit1() throws Exception
          {  
              this.setResizable(false);
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
              
              qtm = new QueryTableModel(connection,-1);
              table = new JTable(qtm);
          
              table.addMouseListener(new MouseAdapter()
              {
                  public void mouseReleased(MouseEvent e)
                  {
                      jScrollPane1_mouseReleased(e);
                  }
              });
              
              qtm.setQueryFilter(col);
         
              jScrollPane1.getViewport().add(table, null);
              getContentPane().add(jScrollPane1, null);
      
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
                {
                    try 
                    {
                        String s1=owner.table.getValueAt(j,i).toString();
                        String s2=table.getValueAt(i,1).toString();
                        String[] niz=ODBCList.splitString(s1.toLowerCase(),s2.toLowerCase());
                        
                        if(!s2.equals("") && niz.length < 2)
                        {
                            owner.qtm.removeRow(j);
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
                {
                    try 
                    {
                        String s1=owner1.table.getValueAt(j,i).toString();
                        String s2=table.getValueAt(i,1).toString();
                        String[] niz=ODBCList.splitString(s1,s2);
      
                        if(!s2.equals("") && niz.length < 2)
                        {
                            owner1.qtm.removeRow(j);
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
        {
            Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
            
            Settings.Center(hlp);
            hlp.setVisible(true);
        }
      
        private void jScrollPane1_mouseReleased(MouseEvent e)
        {
      
        }
    }
}