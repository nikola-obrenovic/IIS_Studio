package iisc;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import javax.swing.JTree;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import java.io.IOException;

import java.text.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import ui.*;


public class GenerateApp extends JDialog implements ActionListener,  TreeModelListener ,TreeSelectionListener 
{
    private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
    private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
    private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
    private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
    private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private JButton btnClose = new JButton();
    private PTree tree;
    private Connection connection;
    private Connection conn;
    private String Mnem;
    private String Amnem;
    private int id;
    private int fid=-1;
    private JButton btnGenerate = new JButton();
    private JButton btnHelp = new JButton();
    private JToolBar jToolBar1 = new JToolBar();
    private JButton btnFirst = new JButton();
    private JButton btnPrev = new JButton();
    private JComboBox cmbAppSystem = new JComboBox();
    private JButton btnNext = new JButton();
    private JButton btnLast = new JButton();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JLabel jLabel2 = new JLabel();
    private JTextField txtUsername = new JTextField();
    private JPasswordField txtPassword = new JPasswordField();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private JComboBox cmbDSN = new JComboBox();
    private JButton btnTest = new JButton();
    private ODBCList o=new ODBCList();
    private JTree jtree = new JTree();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private boolean enabled=false;
    private JButton btnSave = new JButton();
    private JButton btnApply = new JButton();
    private JButton btnInherate = new JButton();
    private JPanel jPanel3 = new JPanel();
    private JComboBox cmbBusinessApp = new JComboBox();
    private IISFrameMain parent;

    public GenerateApp()
    {
        this(null, "", false);
    }
    
    public GenerateApp(IISFrameMain _parent, String title, boolean modal)
    {
        super((Frame) _parent, title, modal);
        parent =_parent;
        try
        {
          jbInit();
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
    }
    public GenerateApp(IISFrameMain _parent, String title, boolean modal, Connection con, int m, PTree tr, int f)
    {
        super((Frame) _parent, title, modal);
        parent =_parent;
        try
        {   
            tree=tr;
            connection=con;
            id=m;
            if(f>=0){
                fid=f;
                JDBCQuery query=new JDBCQuery(connection);
                ResultSet rs1;
                rs1=query.select("select * from IISC_FORM_TYPE where PR_id="+ tree.ID +" and TF_id="+fid);
                rs1.next();
                Mnem=rs1.getString("TF_mnem");
                query.Close();
                rs1=query.select("select * from IISC_TF_APPSYS, IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and IISC_APP_SYSTEM.PR_id="+ tree.ID +" and TF_id="+fid);
                rs1.next();
                id=rs1.getInt("AS_id");
                Amnem=rs1.getString("AS_name");
                query.Close();
            }
            else
                Mnem="";
            Iterator it=tr.WindowsManager.iterator();
            while(it.hasNext())
            {
                Object obj=(Object)it.next();
                Class cls=obj.getClass();
                if(cls==this.getClass())
                { 
                 ((GenerateApp)obj).dispose();
                }
            }
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public GenerateApp(IISFrameMain _parent, String title, boolean modal, Connection con, int m, PTree tr)
    {
        super((Frame) _parent, title, modal);
        parent =_parent;
        try
        {   
            tree=tr;
            connection=con;
            if(m>=0){
                id=m;
                JDBCQuery query=new JDBCQuery(connection);
                ResultSet rs1;
                rs1=query.select("select * from IISC_APP_SYSTEM where PR_id="+ tree.ID +" and AS_id="+id);
                rs1.next();
                Mnem=rs1.getString("AS_name");
                query.Close();
            }
            else
                Mnem="";
            Iterator it=tr.WindowsManager.iterator();
            while(it.hasNext())
            {
                Object obj=(Object)it.next();
                Class cls=obj.getClass();
                if(cls==this.getClass())
                { 
                 ((GenerateApp)obj).dispose();
                }
            }
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private void jbInit() throws Exception
    {
        this.setVisible(false);
        cmbDSN = new JComboBox(o.odbc.toArray());
        this.setSize(new Dimension(513, 368));
        this.getContentPane().setLayout(null);
       
        this.setFont(new Font("SansSerif", 0, 11));
        this.setResizable(false);
        btnClose.setMaximumSize(new Dimension(50, 30));
        btnClose.setPreferredSize(new Dimension(50, 30));
        btnClose.setText("Close");
        btnClose.setBounds(new Rectangle(370, 295, 80, 30));
        btnClose.setMinimumSize(new Dimension(50, 30));
        btnClose.setFont(new Font("SansSerif", 0, 11));
        btnClose.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                        close_ActionPerformed(ae);

                    }
              });
        btnGenerate.setMaximumSize(new Dimension(50, 30));
        btnGenerate.setPreferredSize(new Dimension(50, 30));
        btnGenerate.setText("Generate Application");
        btnGenerate.setBounds(new Rectangle(35, 295, 160, 30));
        btnGenerate.setMinimumSize(new Dimension(50, 30));
        btnGenerate.setFont(new Font("SansSerif", 0, 11));
        btnGenerate.setActionCommand("Generate");
        btnGenerate.setEnabled(false);
        btnGenerate.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnGenerate_actionPerformed(e);
                    }
                });
        btnHelp.setBounds(new Rectangle(455, 295, 35, 30));
        btnHelp.setFont(new Font("SansSerif", 0, 11));
        btnHelp.setIcon(imageHelp);
        btnHelp.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                  btnHelp_actionPerformed(e);
                }
              });
        jToolBar1.setFont(new Font("Verdana", 0, 11));
        jToolBar1.setLayout(null);
        jToolBar1.setPreferredSize(new Dimension(249, 60));
        jToolBar1.setFloatable(false);
        jToolBar1.setBounds(new Rectangle(10, 10, 485, 30));
        btnFirst.setMaximumSize(new Dimension(60, 60));
        btnFirst.setPreferredSize(new Dimension(25, 20));
        btnFirst.setIcon(iconPrevv);
        btnFirst.setBounds(new Rectangle(40, 5, 25, 20));
        btnFirst.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                  prevv_ActionPerformed(ae);
                }
              });
        btnPrev.setMaximumSize(new Dimension(60, 60));
        btnPrev.setIcon(iconPrev);
        btnPrev.setPreferredSize(new Dimension(25, 20));
        btnPrev.setBounds(new Rectangle(70, 5, 25, 20));
        btnPrev.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                  prev_ActionPerformed(ae);
                }
              });
        cmbAppSystem.setFont(new Font("SansSerif", 0, 11));
        cmbAppSystem.setBounds(new Rectangle(100, 5, 285, 20));
        cmbAppSystem.addItemListener(new ItemListener()
            {
                public void itemStateChanged(ItemEvent e)
                {
                  cmbAppType_itemStateChanged(e);
                }
              });
        cmbAppSystem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                  jComboBox1_actionPerformed(e);
                }
              });
        btnNext.setMaximumSize(new Dimension(60, 60));
        btnNext.setIcon(iconNext);
        btnNext.setPreferredSize(new Dimension(25, 20));
        btnNext.setBounds(new Rectangle(390, 5, 25, 20));
        btnNext.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                  next_ActionPerformed(ae);
                }
              });
        btnLast.setMaximumSize(new Dimension(60, 60));
        btnLast.setIcon(iconNextt);
        btnLast.setPreferredSize(new Dimension(25, 20));
        btnLast.setBounds(new Rectangle(420, 5, 25, 20));
        btnLast.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                  nextt_ActionPerformed(ae);
                }
              });
        jScrollPane2.setBounds(new Rectangle(15, 20, 210, 95));
        jLabel2.setText("Username:");
        jLabel2.setBounds(new Rectangle(15, 30, 70, 20));
        jLabel2.setFont(new Font("SansSerif", 0, 11));
        txtUsername.setBounds(new Rectangle(90, 30, 135, 20));
        txtUsername.setFont(new Font("SansSerif", 0, 11));
        txtUsername.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                        txtUsername_actionPerformed(e);
                    }
              });
        txtUsername.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtUsername_keyPressed(e);
                    }
                });
        txtPassword.setBounds(new Rectangle(90, 80, 135, 20));
        txtPassword.setFont(new Font("SansSerif", 0, 11));
        txtPassword.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                        txtPassword_actionPerformed(e);
                    }
              });
        txtPassword.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtPassword_keyPressed(e);
                    }
                });
        jLabel3.setText("Password:");
        jLabel3.setBounds(new Rectangle(15, 80, 60, 20));
        jLabel3.setFont(new Font("SansSerif", 0, 11));
        jLabel1.setText("DSN:");
        jLabel1.setBounds(new Rectangle(15, 125, 60, 20));
        jLabel1.setFont(new Font("SansSerif", 0, 11));
        cmbDSN.setBounds(new Rectangle(90, 125, 135, 20));
        cmbDSN.setFont(new Font("SansSerif", 0, 11));
        cmbDSN.setActionCommand("comboDSN");
        cmbDSN.addActionListener(new ActionListener()
            {
            public void actionPerformed(ActionEvent e)
            {
                        cmbProject_actionPerformed(e);
                    }
            });
        cmbDSN.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        cmbDSN_itemStateChanged(e);
                    }
                });
        btnTest.setMaximumSize(new Dimension(50, 30));
        btnTest.setPreferredSize(new Dimension(50, 30));
        btnTest.setText("Test DB Connection");
        btnTest.setBounds(new Rectangle(90, 185, 135, 30));
        btnTest.setMinimumSize(new Dimension(50, 30));
        btnTest.setFont(new Font("SansSerif", 0, 11));
        btnTest.setActionCommand("Generate");
        btnTest.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                        save_ActionPerformed(ae);
                    }
        });
        jtree.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        jtree_mouseClicked(e);
                    }
                });
        jtree.addTreeSelectionListener(new TreeSelectionListener() {
                    public void valueChanged(TreeSelectionEvent e) {
                        jtree_valueChanged(e);
                    }
                });
        jPanel1.setBounds(new Rectangle(10, 55, 240, 225));
        jPanel1.setBorder(BorderFactory.createTitledBorder("Application System DB Connection"));
        jPanel1.setLayout(null);
        jPanel2.setBounds(new Rectangle(255, 55, 240, 165));
        jPanel2.setBorder(BorderFactory.createTitledBorder("Design Template"));
        jPanel2.setLayout(null);
        btnSave.setMaximumSize(new Dimension(50, 30));
        btnSave.setPreferredSize(new Dimension(50, 30));
        btnSave.setText("OK");
        btnSave.setBounds(new Rectangle(285, 295, 80, 30));
        btnSave.setMinimumSize(new Dimension(50, 30));
        btnSave.setFont(new Font("SansSerif", 0, 11));
        btnSave.setEnabled(false);
        btnSave.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                        ok_ActionPerformed(ae);

                    }
              });
        btnApply.setMaximumSize(new Dimension(50, 30));
        btnApply.setPreferredSize(new Dimension(50, 30));
        btnApply.setText("Apply");
        btnApply.setBounds(new Rectangle(200, 295, 80, 30));
        btnApply.setMinimumSize(new Dimension(50, 30));
        btnApply.setFont(new Font("SansSerif", 0, 11));
        btnApply.setEnabled(false);
        btnApply.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                        apply_ActionPerformed(ae);

                    }
              });
        btnInherate.setMaximumSize(new Dimension(50, 30));
        btnInherate.setPreferredSize(new Dimension(50, 30));
        btnInherate.setText("Inherate from Application System");
        btnInherate.setBounds(new Rectangle(15, 125, 210, 30));
        btnInherate.setMinimumSize(new Dimension(50, 30));
        btnInherate.setFont(new Font("SansSerif", 0, 11));
        btnInherate.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                        inherate_ActionPerformed(ae);

                    }
              });
        jPanel3.setBounds(new Rectangle(255, 225, 240, 55));
        jPanel3.setBorder(BorderFactory.createTitledBorder("Business Application"));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(null);
        cmbBusinessApp.setBounds(new Rectangle(15, 25, 210, 20));
        jToolBar1.add(btnFirst, null);
        jToolBar1.add(btnPrev, null);
        jToolBar1.add(cmbAppSystem, null);
        jToolBar1.add(btnNext, null);
        jToolBar1.add(btnLast, null);
        jPanel1.add(cmbDSN, null);
        jPanel1.add(btnTest, null);
        jPanel1.add(txtUsername, null);
        jPanel1.add(txtPassword, null);
        jPanel1.add(jLabel1, null);
        jPanel1.add(jLabel3, null);
        jPanel1.add(jLabel2, null);
        jScrollPane2.getViewport().add(jtree, null);
        jPanel2.add(jScrollPane2, null);
        jPanel2.add(btnInherate, null);
        jPanel3.add(cmbBusinessApp, null);
        this.getContentPane().add(jPanel3, null);
        this.getContentPane().add(btnApply, null);
        this.getContentPane().add(btnSave, null);
        this.getContentPane().add(jPanel1, null);
        this.getContentPane().add(jToolBar1, null);
        this.getContentPane().add(btnHelp, null);
        this.getContentPane().add(btnClose, null);
        this.getContentPane().add(btnGenerate, null);
        this.getContentPane().add(jPanel2, null);
        pravi_drvo();
        setGenerateApp(Mnem);
        
    }
     public void actionPerformed(ActionEvent ae)
    {
    
    }
    private void prevv_ActionPerformed(ActionEvent e)
    {
        String s="";
        int ids=-1;
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        if(btnSave.isEnabled())
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Application Generator", JOptionPane.YES_NO_OPTION)==0)
                Save();
        try
        {
            rs1=query.select(fid==-1?"select AS_id, AS_name from IISC_APP_SYSTEM where PR_id="+ tree.ID +" order by  AS_name asc":"select IISC_FORM_TYPE.TF_id, TF_mnem from IISC_FORM_TYPE, IISC_TF_APPSYS where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.PR_id="+ tree.ID +" and AS_id="+ id +" order by  TF_mnem asc");
            if(rs1.next())
            {
                s=rs1.getString(2);
                ids=rs1.getInt(1);
            }
            query.Close();
            setGenerateApp(s);
            Mnem=s;
            if(fid==-1)
                id=ids;
            else
                fid=ids;
            tree.select_node(Mnem,(fid==-1?"Application Systems":"Owned"));
        }
        catch(SQLException ef)
        {
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void prev_ActionPerformed(ActionEvent e)
    {
        String s=Mnem.trim() ;
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        int ids=-1;
        if(btnSave.isEnabled())
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Application Generator", JOptionPane.YES_NO_OPTION)==0)
                Save();
        try
        {
            rs1=query.select(fid==-1?"select AS_id, AS_name from IISC_APP_SYSTEM  where PR_id="+ tree.ID +" and AS_name<'" + s + "' order by AS_name desc":"select IISC_FORM_TYPE.TF_id, TF_mnem from IISC_FORM_TYPE, IISC_TF_APPSYS where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.PR_id="+ tree.ID +" and TF_mnem<'" + s + "' and AS_id="+ id +"  order by TF_mnem desc" );
            if(rs1.next())
            {
                s=rs1.getString(2);
                ids=rs1.getInt(1);
            }
            query.Close();
            setGenerateApp(s);
            Mnem=s;
            if(fid==-1)
                id=ids;
            else
                fid=ids;
            tree.select_node(Mnem,(fid==-1?"Application Systems":"Owned"));
        }
        catch(SQLException ef)
        {
         JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
        }
    }
    private void next_ActionPerformed(ActionEvent e)
    {
        String s=Mnem.trim();
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        int ids=-1;
        if(btnSave.isEnabled())
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Application Generator", JOptionPane.YES_NO_OPTION)==0)
                Save();
        try
        {  
            rs1=query.select(fid==-1?"select AS_id, AS_name from IISC_APP_SYSTEM  where PR_id="+ tree.ID +" and AS_name>'" + s + "' order by AS_name asc":"select IISC_FORM_TYPE.TF_id, TF_mnem from IISC_FORM_TYPE, IISC_TF_APPSYS where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.PR_id="+ tree.ID +" and TF_mnem>'" + s + "' and AS_id="+ id +" order by TF_mnem asc" );
            if(rs1.next())
            {
                s=rs1.getString(2);
                ids=rs1.getInt(1);
            }
            query.Close();
            setGenerateApp(s);
            Mnem=s;
            if(fid==-1)
                id=ids;
            else
                fid=ids;
            tree.select_node(Mnem,(fid==-1?"Application Systems":"Owned"));
        }
        catch(SQLException ef)
        {
         JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
     }
    private void nextt_ActionPerformed(ActionEvent e)
    {
        String s="";
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        int ids=-1;
        if(btnSave.isEnabled())
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Application Generator", JOptionPane.YES_NO_OPTION)==0)
               Save();
        try
        { 
            rs1=query.select(fid==-1?"select AS_id, AS_name from IISC_APP_SYSTEM  where PR_id="+ tree.ID +" order by  AS_name desc":"select IISC_FORM_TYPE.TF_id, TF_mnem from IISC_FORM_TYPE, IISC_TF_APPSYS where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.PR_id="+ tree.ID +"  and AS_id="+ id +" order by  TF_mnem desc");
            if(rs1.next())
            {
                s=rs1.getString(2);
                ids=rs1.getInt(1);
            }
            query.Close();
            setGenerateApp(s);
            Mnem=s;
            if(fid==-1)
                id=ids;
            else
                fid=ids;
            tree.select_node(Mnem,(fid==-1?"Application Systems":"Owned"));
        }
        catch(SQLException ef)
        {
         JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    private void Save() {
        JDBCQuery query=new JDBCQuery(connection);
        String t="NULL";
        ResultSet rs1;
        try
        { 
            if(!jtree.isSelectionEmpty())
            {
                if(jtree.getSelectionPath().getPathCount()==3)
                {
                    String sql="select * from IIST_TEMPLATE where T_name='"+ jtree.getSelectionPath().getLastPathComponent().toString() +"'";
                    rs1=query.select(sql);
                    if(rs1.next())
                        t=rs1.getString("T_id");
                    query.Close();
                }
            }
            String sql="update IISC_APP_SYSTEM set AS_dsn='"+ cmbDSN.getSelectedItem().toString() +"', AS_username='"+ txtUsername.getText() + "', AS_password='"+  new String(txtPassword.getPassword())  +"' where PR_id="+ tree.ID +" and AS_id="+id;
            query.update(sql);
            sql=fid==-1?"update IISC_APP_SYSTEM set T_id="+ t +" where PR_id="+ tree.ID +" and AS_id="+id:"update IISC_FORM_TYPE set T_id="+ t +" where PR_id="+ tree.ID +" and TF_id="+fid;
            query.update(sql);
        }
        catch(SQLException ef)
        {
         JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }
     private void close_ActionPerformed(ActionEvent e)
      {
      this.dispose();
      }
    public void setGenerateApp (String m)
    {
        try
        {
            ResultSet rs,rs1;
            JDBCQuery query=new JDBCQuery(connection);
            rs=query.select(fid==-1?"select count(*) from IISC_APP_SYSTEM where PR_id=" + tree.ID:"select count(*) from IISC_FORM_TYPE, IISC_TF_APPSYS where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.PR_id=" + tree.ID + " and AS_id="+id);
            rs.next();
            int j=rs.getInt(1);
            query.Close();
            String[] sa=query.selectArray(fid==-1?"select * from IISC_APP_SYSTEM where PR_id=" + tree.ID :"select * from IISC_FORM_TYPE, IISC_TF_APPSYS where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.PR_id=" + tree.ID + " and AS_id=" + id,j,3);
            sa[0]="";
            query.Close();
            cmbAppSystem.removeAllItems();
            for(int k=0;k<sa.length; k++)
            cmbAppSystem.addItem(sa[k]);
            cmbAppSystem.setSelectedItem(m);
            Mnem=m;
            rs1=query.select(fid==-1?"select * from IISC_APP_SYSTEM where PR_id="+ tree.ID +"  and AS_name='"+ Mnem +"'":"select * from IISC_APP_SYSTEM where PR_id="+ tree.ID +"  and AS_id="+ id);
            if(rs1.next())
            {   
                id=rs1.getInt("AS_id");
                txtUsername.setText(rs1.getString("AS_username"));
                txtPassword.setText(rs1.getString("AS_password"));
                cmbDSN.setSelectedItem(rs1.getString("AS_dsn"));
                if(cmbDSN.getSelectedObjects().length==0)cmbDSN.setSelectedIndex(0);
                String t1=rs1.getString("T_id");
                query.Close();
                rs=query.select("select count(*) from IISC_BUSINESS_APPLICATION where PR_id=" + tree.ID + " and AS_id="+id);
                rs.next();
                j=rs.getInt(1);
                query.Close();
                sa=query.selectArray1("select * from IISC_BUSINESS_APPLICATION where PR_id=" + tree.ID + " and AS_id="+id,j,2);
                cmbBusinessApp.removeAllItems();
                for(int k=0;k<sa.length; k++)
                cmbBusinessApp.addItem(sa[k]);                 
                String t=null;
                if(fid!=-1){
                    query.Close();
                    rs1=query.select("select T_id, TF_id from IISC_FORM_TYPE where TF_mnem='"+Mnem+"'");
                    if(rs1.next()){
                        t=rs1.getString("T_id"); 
                        if(t == null)t=t1;
                        fid=rs1.getInt("TF_id");  
                    }
                }
                else
                    t=t1;
                if(t != null){
                    query.Close();
                    rs1=query.select("select * from IIST_TEMPLATE, IIST_TEMPLATE_GROUP where IIST_TEMPLATE_GROUP.TG_id=IIST_TEMPLATE.TG_id and IIST_TEMPLATE.T_id="+t);
                    if(rs1.next())
                    {  
                        select_node(rs1.getString("T_name"),rs1.getString("TG_name"));
                    }
                }
                else
                    jtree.setSelectionRow(-1);
            }
            else
            {     
                txtUsername.setText("");
                txtPassword.setText("");
            }
            query.Close();
            rs1=query.select(fid==-1?"select * from IISC_APP_SYSTEM  where PR_id="+ tree.ID +" and AS_name>'" + m.trim() + "'":"select * from IISC_FORM_TYPE, IISC_TF_APPSYS where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.PR_id="+ tree.ID +" and AS_id=" + id + " and TF_mnem>'" + m.trim() + "'");
            if(rs1.next())
            {
                btnNext.setEnabled(true);
                btnLast.setEnabled(true);
            }
            else
            {
                btnNext.setEnabled(false);
                btnLast.setEnabled(false);
            }
            query.Close();
            rs1=query.select(fid==-1?"select * from IISC_APP_SYSTEM where PR_id="+ tree.ID +" and  AS_name<'" + m.trim() + "'":"select * from IISC_FORM_TYPE, IISC_TF_APPSYS where IISC_FORM_TYPE.TF_id=IISC_TF_APPSYS.TF_id and IISC_TF_APPSYS.PR_id="+ tree.ID +" and AS_id=" + id + " and  TF_mnem<'" + m.trim() + "'");
            if(rs1.next())
            {
                btnPrev.setEnabled(true);
                btnFirst.setEnabled(true);
            }
            else
            {
                btnPrev.setEnabled(false);
                btnFirst.setEnabled(false);
            }
            btnSave.setEnabled(false);
            btnApply.setEnabled(false);
            query.Close();
            if(fid!=-1)
            {
                jScrollPane2.setBounds(jScrollPane2.getX(),jScrollPane2.getY(),jScrollPane2.getWidth(),100);
                btnInherate.setVisible(true);
            }
            else
            {
                jScrollPane2.setBounds(jScrollPane2.getX(),jScrollPane2.getY(),jScrollPane2.getWidth(),130);
                btnInherate.setVisible(false);
            }
            this.setTitle("Application Generator " + (fid==-1?"":""+Amnem+" :: ") + Mnem);
            }  
            catch(SQLException e)
            {
             JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
    }
    public void select_node (String s, String p)
    {
      int i;
      jtree.setVisible(false);
      jtree.expandRow(0);
      for (i = 1; i < jtree.getRowCount(); i++) {
        jtree.expandRow(i);
        if((jtree.getPathForRow(i).getLastPathComponent().toString().equals(s) && jtree.getPathForRow(i).getParentPath().getLastPathComponent().toString().equals(p)) || (jtree.getPathForRow(i).getLastPathComponent().toString().equals(p) && s.trim().equals("")))break;
      }
      TreePath pt=jtree.getPathForRow(i);
      jtree.setSelectionPath(pt);
      for (int k = 1; k < jtree.getRowCount(); k++) {
        boolean t=false;
        for (int j = 0; j < pt.getPathCount(); j++)
          if(pt.getPathComponent(j).toString().equals(jtree.getPathForRow(k).getLastPathComponent().toString()))t=true;
        if(!t)
          jtree.collapseRow(k);
      }
      jtree.collapseRow(jtree.getRowForPath(jtree.getSelectionPath()));
      jtree.setVisible(true);
    }
    public void Reload ()
    {
        setGenerateApp(Mnem);
    }
    private void btnHelp_actionPerformed(ActionEvent e)
    {
        Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
        Settings.Center(hlp);
        hlp.setVisible(true);
    }
    
    private void txtName_propertyChange(PropertyChangeEvent e)
    {
    }    
    private void jComboBox1_actionPerformed(ActionEvent e)
    {
        try 
        { 
            if(cmbAppSystem.getSelectedItem()!=null){
                String s=cmbAppSystem.getSelectedItem().toString();
                setGenerateApp(s);
                tree.select_node(Mnem,(fid==-1?"Application Systems":"Owned"));
                Mnem=s;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    private void cmbAppType_itemStateChanged(ItemEvent e)
    {
    }


    private void testConnection(int i) 
    { 
        String url = "jdbc:odbc:"+((cmbDSN.getSelectedObjects().length==1)?cmbDSN.getSelectedItem().toString():"");
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        try 
        {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            conn = (Connection)DriverManager.getConnection(url, username, password);
            if(i==1)
                JOptionPane.showMessageDialog(null, "<html><center>Connection test succeeded!", "Connection Test", JOptionPane.INFORMATION_MESSAGE);
            enabled=true;
        }
        catch (ClassNotFoundException ef) 
        {
            if(i==1)
                JOptionPane.showMessageDialog(null, "ClassNotFoundException:    " + ef.getMessage() , "Connection Test Error", JOptionPane.ERROR_MESSAGE);
        }
        catch(SQLException ex)
        {
            if(i==1)
                JOptionPane.showMessageDialog(null, "<html><center>Connection test failed!", "Connection Test Error", JOptionPane.ERROR_MESSAGE);
        }   
    }

    private void save_ActionPerformed(ActionEvent e) {
        testConnection(1);
    }
    
    public void pravi_drvo()
    {
        ResultSet rs,rs1,rs2;
        JDBCQuery query=new JDBCQuery(connection );
        JDBCQuery query1=new JDBCQuery(connection);
        Object[] templates=new Object[1];   
        try
        {
        rs=query.select("select count(*) from IIST_TEMPLATE_GROUP");
        if(rs.next())
        {
        int i=rs.getInt(1)+1;
        templates=new Object[i];
        query.Close();
        rs=query.select("select * from IIST_TEMPLATE_GROUP  order by TG_name");
        templates[0]="Templates";
        int j=1;
        while(rs.next())
        { 
            int tid=rs.getInt("TG_id");
            rs1=query1.select("select count(*) from IIST_TEMPLATE where TG_id="+ tid);
            int s=0;
            if(rs1.next())
            {
            s=rs1.getInt(1)+1;
            }
            query1.Close();
            Object[] arr=new Object[s];
            arr[0]=rs.getString("TG_name");
            rs1=query1.select("select * from IIST_TEMPLATE where TG_id="+ tid);
            int k=1;
            while(rs1.next())
            { 
            Object[] arrp={rs1.getString("T_name")};
            arr[k] =arrp.clone();
            k++;
            }
            query1.Close();
            templates[j]=arr.clone();
            j++;   
        }
        }
        query.Close();
    
        }
        catch (SQLException e) {
          JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
        DefaultMutableTreeNode root = processHierarchy(templates);
        DefaultTreeModel trModel=new DefaultTreeModel(root);
        trModel.addTreeModelListener(this);
        jtree.setModel(trModel);
        jtree.setFont(new Font("SansSerif", 0, 11));
        jtree.addTreeSelectionListener(this);
        jtree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        jtree.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jtree.setRowHeight(20);
        jtree.setShowsRootHandles(true);
        jtree.setCellRenderer(new TreeRenderer());
        jtree.setSelectionRow(0);
    }
    private DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
       DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy[0]);
       DefaultMutableTreeNode child;
       for(int i=1; i<hierarchy.length; i++) {
         Object nodeSpecifier = hierarchy[i];
         if (nodeSpecifier instanceof Object[])  // Ie node with children
           child = processHierarchy((Object[])nodeSpecifier);
         else
           child = new DefaultMutableTreeNode(nodeSpecifier); // Ie Leaf
         node.add(child);
       }
       return(node);
     }
    public void treeNodesInserted(TreeModelEvent e) {
    }      
    public void treeNodesRemoved(TreeModelEvent e) {
    }     
    public void treeStructureChanged(TreeModelEvent e) {
    }
    public void treeNodesChanged(TreeModelEvent e){
    }
    public void valueChanged (TreeSelectionEvent e) {
    }

    private void txtUsername_actionPerformed(ActionEvent e) {
        enabled();
    }

    private void cmbProject_actionPerformed(ActionEvent e) {
        enabled();
    }

    private void txtPassword_actionPerformed(ActionEvent e) {
        enabled();
    }

    private void jtree_mouseClicked(MouseEvent e) {
        enabled();
        setInherate();
    }
    private void setInherate() {
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        try
        {
            int t=-1;
            rs1=query.select("select T_id from IISC_APP_SYSTEM where PR_id="+ tree.ID +" and AS_id="+id);
            if(rs1.next())
            {
                t=rs1.getInt(1);
            }
            query.Close();
            rs1=query.select("select * from IIST_TEMPLATE where IIST_TEMPLATE.T_id="+t);
            if(rs1.next())
            {  
                if(rs1.getString("T_name").equals(jtree.getSelectionPath().getLastPathComponent().toString()))
                    btnInherate.setEnabled(false);
                else
                    btnInherate.setEnabled(true);
            }
            query.Close();
        }
        catch(SQLException ef)
        {
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }
    private void enabled(){
        testConnection(0);
        if(enabled && !jtree.isSelectionEmpty())
            if(jtree.getSelectionPath().getPathCount()==3)
                btnGenerate.setEnabled(true);
            else
                btnGenerate.setEnabled(false);
        else
            btnGenerate.setEnabled(false);
    }

    private void jtree_valueChanged(TreeSelectionEvent e) {
        btnSave.setEnabled(true);
        btnApply.setEnabled(true);
        setInherate();
    }

    private void txtUsername_keyPressed(KeyEvent e) {
        btnSave.setEnabled(true);
        btnApply.setEnabled(true);
    }

    private void txtPassword_keyPressed(KeyEvent e) {
        btnSave.setEnabled(true);
        btnApply.setEnabled(true);
    }

    private void cmbDSN_itemStateChanged(ItemEvent e) {
        btnSave.setEnabled(true);
        btnApply.setEnabled(true);
    }

    private void ok_ActionPerformed(ActionEvent e) {
        Save();
        dispose();
    }

    private void apply_ActionPerformed(ActionEvent e) {
        Save();
        btnSave.setEnabled(false);
        btnApply.setEnabled(false);
    }

    private void inherate_ActionPerformed(ActionEvent e) {
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        try
        {
            int t=-1;
            rs1=query.select("select T_id from IISC_APP_SYSTEM where PR_id="+ tree.ID +" and AS_id="+id);
            if(rs1.next())
            {
                t=rs1.getInt(1);
            }
            query.Close();
            rs1=query.select("select * from IIST_TEMPLATE, IIST_TEMPLATE_GROUP where IIST_TEMPLATE_GROUP.TG_id=IIST_TEMPLATE.TG_id and IIST_TEMPLATE.T_id="+t);
            if(rs1.next())
            {  
                select_node(rs1.getString("T_name"),rs1.getString("TG_name"));
            }
            query.Close();
        }
        catch(SQLException ef)
        {
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }  
    }

    private void btnGenerate_actionPerformed(ActionEvent e) {
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs;
        try {
            Runtime.getRuntime().gc();
            int t=-1;
            rs=query.select("select T_id from IIST_TEMPLATE where T_name like '%"+ jtree.getSelectionPath().getLastPathComponent().toString() +"%'");
            if(rs.next())
            {
                t=rs.getInt(1);
            }
            query.Close();
            int b=-1;
            rs=query.select("select BA_id from IISC_BUSINESS_APPLICATION where BA_mnem like '%"+ cmbBusinessApp.getSelectedItem().toString() +"%'");
            if(rs.next())
            {
                b=rs.getInt(1);
            }
            query.Close();
            String url ="jdbc:odbc:"+ cmbDSN.getSelectedItem().toString();
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            ViewApplication diag=new ViewApplication(parent,"",false,connection,tree.ID,id,t,b,url, username, password,tree);
            Settings.Center(diag);
            diag.setVisible(true);
            /*
            Connection conn = (Connection)DriverManager.getConnection(url, username, password);
            Template template=new Template(connection,t);
            Generator gen=new Generator(conn);
            Application app =new Application(connection,id, b, conn);
            app.generateSubScemes(tree.ID);
            gen.createUIMLSpecification(template, app);
            gen.renderUIMLSpecification();*/
            
        }
        catch (SQLException ex) {
              System.out.println("Exception: " + ex);
              ex.printStackTrace ();
        }
        /*catch (IOException ex) {
              System.out.println("Exception: " + ex);
              ex.printStackTrace ();
        }*/
    }
}
