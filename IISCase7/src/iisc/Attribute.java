package iisc;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.FocusEvent;

import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JToolBar;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.*;
import java.sql.*;

import java.util.*;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JRadioButton;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.KeyAdapter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

public class Attribute extends JDialog implements ActionListener, ListSelectionListener{
        private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
        private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
        private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
        private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
        private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
        private PTree tree;
        private Connection connection;
        public String Mnem;
        private int id;
        private JTabbedPane jTabbedPane1 = new JTabbedPane();
        private JPanel JPanel1 = new JPanel();
        private JToolBar jToolBar1 = new JToolBar();
        private JButton btnFirst = new JButton();
        private JButton btnPrev = new JButton();
        private JButton btnNext = new JButton();
        private JButton btnLast = new JButton();
        private JButton btnClose = new JButton();
        public JButton btnSave = new JButton();
        private JButton btnNew = new JButton();
        private JButton btnErase = new JButton();
        private JTextArea txtDescription = new JTextArea();
        private JLabel jLabel3 = new JLabel();
        private JLabel jLabel10 = new JLabel();
        public JTextField txtName = new JTextField();
        private JPanel jPanel2 = new JPanel();
        private JPanel jPanel3 = new JPanel();
        private JLabel jLabel11 = new JLabel();
        public JComboBox cmbDomain = new JComboBox();
        private JButton btnEdit = new JButton();
        private JLabel jLabel12 = new JLabel();
        public JComboBox cmbFunction = new JComboBox();
        private JCheckBox chSUBP = new JCheckBox();
        private JLabel jLabel13 = new JLabel();
        private JComboBox cmbAttribute = new JComboBox();
        private JComboBox cmbAttributeRenamed = new JComboBox();
        private JComboBox cmbAttIns = new JComboBox();
        private JLabel jLabel14 = new JLabel();
        private JComboBox cmbAttUpd = new JComboBox();
        private JLabel jLabel15 = new JLabel();
        private JComboBox cmbAttDel = new JComboBox();
        private JLabel jLabel16 = new JLabel();
        private JButton btnAdd = new JButton();
        private JButton btnRemove = new JButton();
        private  JTable table;
        private QueryTableModel qtm;
        private ListSelectionModel rowSM;
        private JScrollPane jScrollPane1 = new JScrollPane();
        private JButton btnHelp = new JButton();
        private JPanel jPanel4 = new JPanel();
        private JLabel jLabel6 = new JLabel();
        private JTextArea txtComment = new JTextArea();
        public  JButton btnApply = new JButton();
        private JRadioButton rdElementary = new JRadioButton();
        private JRadioButton rdRenamed = new JRadioButton();
        private JComboBox cmbAtt = new JComboBox();
        private JLabel jLabel5 = new JLabel();
        private ButtonGroup bgrp=new ButtonGroup();
        private ButtonGroup bgrp1=new ButtonGroup();
        private JRadioButton rdYes = new JRadioButton();
        private JRadioButton rdNo = new JRadioButton();
        private JButton btnDomain = new JButton();
        private JButton btnFunction = new JButton();
        private JButton btnFunction1 = new JButton();
        private JButton btnFunction2 = new JButton();
        private JButton btnFunction3 = new JButton();
        private JButton btnFunction4 = new JButton();
        private JButton btnFunction5 = new JButton();
        private boolean dispose=false;
        public JTextField txtDefault = new JTextField();
        private JLabel jLabel17 = new JLabel();
        private JTextPane txtExpression = new JTextPane();
        private JPanel jPanel5 = new JPanel();
        private AttDisplayProperties adp;
        private JRadioButton jRadioButton1 = new JRadioButton();
        private JRadioButton jRadioButton2 = new JRadioButton();
        private JPanel jPanel1 = new JPanel();
        private int inherit = 0;
        private JButton expButton = new JButton();
        private JButton chkButton = new JButton();
        private JScrollPane jScrollPaneErr = new JScrollPane();
        private JList errorList = new JList();
        private DefaultListModel model2 = new DefaultListModel();
        private Frame pmy = null;
        private JLabel jLabel1 = new JLabel();
        private JLabel jLabel2 = new JLabel();
        private JLabel jLabel4 = new JLabel();
        private DropDownList ddList = null;
        private JScrollPane jScrollPane2 = new JScrollPane();

    public Attribute(){
        this(null, "", false);
    }

    public Attribute(IISFrameMain parent, String title, boolean modal)
    {
        super((Frame) parent, title, modal);
        try{
            pmy = parent;
            jbInit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    
    public Attribute(IISFrameMain parent, String title, boolean modal,Connection con,int m , PTree tr ){
        super((Frame) parent, title, modal);
        try{   
            pmy = parent;
            tree=tr;
            connection=con; 
            id=m;
            if(m>=0){
                JDBCQuery query=new JDBCQuery(connection);
                ResultSet rs1;
                rs1=query.select("select * from IISC_Attribute where PR_id="+ tree.ID +" and Att_id="+id);
                rs1.next();
                Mnem=rs1.getString("Att_mnem");
                query.Close();
            }
            else
                Mnem="";
            Iterator it=tr.WindowsManager.iterator();
            while(it.hasNext()){
                Object obj=(Object)it.next();
                Class cls=obj.getClass();
                if(cls==this.getClass()){ 
                    ((Attribute)obj).dispose();
                }
            }
            jbInit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void jbInit() throws Exception{
    
        ddList = new DropDownList(1,connection,txtExpression,String.valueOf(tree.ID),"",String.valueOf(id));
        jPanel3.add(ddList);
        ddList.setVisible(false);
     
        errorList.setModel(model2);
        errorList.setCellRenderer(new CheckConstraintErrorListRender());
        //jScrollPaneErr.setVisible(false);
        jLabel1.setText("Error log");
        jLabel1.setBounds(new Rectangle(10, 245, 60, 15));
        jLabel2.setText("messages:");
        jLabel2.setBounds(new Rectangle(10, 260, 70, 15));
        jLabel4.setText("Expression:");
        jLabel4.setBounds(new Rectangle(10, 90, 65, 15));
        jScrollPane2.setBounds(new Rectangle(10, 105, 420, 100));
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        txtDefault.addKeyListener(new KeyAdapter(){
        public void keyPressed(KeyEvent e){
          txtDefault_keyPressed(e);
        }
        });
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs;
        this.setSize(new Dimension(466, 476));
        this.setResizable(false);
        this.getContentPane().setLayout(null);
        this.setTitle("Attributes");
        jTabbedPane1.setBounds(new Rectangle(5, 40, 445, 355));
        JPanel1.setLayout(null);
        jToolBar1.setFont(new Font("Verdana", 0, 11));
        jToolBar1.setLayout(null);
        jToolBar1.setPreferredSize(new Dimension(249, 60));
        jToolBar1.setFloatable(false);
        jToolBar1.setBounds(new Rectangle(15, 5, 440, 35));
        btnFirst.setMaximumSize(new Dimension(60, 60));
        btnFirst.setPreferredSize(new Dimension(25, 20));
        btnFirst.setIcon(iconPrevv);
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
        btnPrev.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              prev_ActionPerformed(ae);
            }
          });
        btnNext.setMaximumSize(new Dimension(60, 60));
        btnNext.setIcon(iconNext);
        btnNext.setPreferredSize(new Dimension(25, 20));
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
        btnLast.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              nextt_ActionPerformed(ae);
            }
          });
        btnClose.setMaximumSize(new Dimension(50, 30));
        btnClose.setPreferredSize(new Dimension(50, 30));
        btnClose.setText("Cancel");
        btnClose.setBounds(new Rectangle(330, 405, 80, 30));
        btnClose.setMinimumSize(new Dimension(50, 30));
        btnClose.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              close_ActionPerformed(ae);
            }
          });
        btnSave.setMaximumSize(new Dimension(50, 30));
        btnSave.setPreferredSize(new Dimension(50, 30));
        btnSave.setText("OK");
        btnSave.setBounds(new Rectangle(250, 405, 75, 30));
        btnSave.setMinimumSize(new Dimension(50, 30));
        btnSave.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              save_ActionPerformed(ae);
            }
          });
        btnNew.setMaximumSize(new Dimension(50, 30));
        btnNew.setPreferredSize(new Dimension(50, 30));
        btnNew.setText("New");
        btnNew.setBounds(new Rectangle(165, 405, 80, 30));
        btnNew.setMinimumSize(new Dimension(50, 30));
        btnNew.addActionListener(new ActionListener()
         {
        public void actionPerformed(ActionEvent ae)
        {
          new_ActionPerformed(ae);
        }
      });
        btnErase.setMaximumSize(new Dimension(50, 30));
        btnErase.setPreferredSize(new Dimension(50, 30));
        btnErase.setText("Delete");
        btnErase.setBounds(new Rectangle(85, 405, 75, 30));
        btnErase.setMinimumSize(new Dimension(50, 30));
        btnErase.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              erase_ActionPerformed(ae);
            }
          });
        txtDescription.setBounds(new Rectangle(90, 55, 345, 100));
        txtDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        jLabel3.setText("Description:");
        jLabel3.setBounds(new Rectangle(15, 50, 85, 20));
        jLabel3.setFont(new Font("SansSerif", 0, 11));
        jLabel10.setText("Name:");
        jLabel10.setBounds(new Rectangle(15, 20, 65, 20));
        jLabel10.setFont(new Font("SansSerif", 0, 11));
        txtName.setBounds(new Rectangle(90, 20, 345, 20));
        txtName.setFont(new Font("SansSerif", 0, 11));
        jPanel2.setLayout(null);
        jPanel3.setLayout(null);
        jLabel11.setText("Domain:");
        jLabel11.setFont(new Font("SansSerif", 0, 11));
        jLabel11.setBounds(new Rectangle(15, 10, 60, 15));
      //  cmbDomain=new JComboBox();
        cmbDomain.setBounds(new Rectangle(85, 10, 310, 20));
        btnEdit.setMaximumSize(new Dimension(50, 30));
        btnEdit.setPreferredSize(new Dimension(50, 30));
        btnEdit.setText("Edit Domain");
        btnEdit.setBounds(new Rectangle(270, 35, 125, 30));
        btnEdit.setMinimumSize(new Dimension(50, 30));
        btnEdit.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              edit_ActionPerformed(ae);
            }
          });
        jLabel12.setText("Query function:");
        jLabel12.setFont(new Font("SansSerif", 0, 11));
        jLabel12.setBounds(new Rectangle(15, 295, 80, 15));
        rs=query.select("select count(*) from IISC_Function  where PR_id="+ tree.ID +"");
        rs.next();
        int j=rs.getInt(1);
            query.Close();
        String[] sa=query.selectArray("select * from IISC_Function  where PR_id="+ tree.ID +"",j,3);
        sa[0]="";
        query.Close();
        cmbFunction=new JComboBox(sa);
        cmbFunction.setBounds(new Rectangle(90, 295, 310, 20));
        chSUBP.setText("Exists in database schema");
        chSUBP.setBounds(new Rectangle(10, 165, 165, 20));
        jLabel13.setText("Attribute:");
        jLabel13.setFont(new Font("SansSerif", 0, 11));
        jLabel13.setBounds(new Rectangle(10, 25, 115, 15));
   
        rs=query.select("select count(*) from IISC_Attribute  where PR_id="+ tree.ID +" and  Att_id<>"+id);
        rs.next();
        j=rs.getInt(1);
        query.Close();
        sa=query.selectArray("select * from IISC_Attribute  where PR_id="+ tree.ID +" and Att_id<>"+id ,j,3);
        sa[0]="";
        query.Close();
        cmbAttribute=new JComboBox(sa); 
        cmbAttribute.setBounds(new Rectangle(85, 20, 310, 20));
        cmbAttributeRenamed=new JComboBox(sa); 
        cmbAttributeRenamed.setBounds(new Rectangle(190, 195, 210, 20));
        rs=query.select("select count(*) from IISC_Function  where PR_id="+ tree.ID +"");
        rs.next();
        j=rs.getInt(1);
        query.Close();
        sa=query.selectArray("select * from IISC_Function  where PR_id="+ tree.ID +"",j,3);
        sa[0]="";
        query.Close();
        cmbAttIns=new JComboBox(sa);
        cmbAttIns.setBounds(new Rectangle(130, 70, 265, 20));
        jLabel14.setText("On-insert function:");
        jLabel14.setFont(new Font("SansSerif", 0, 11));
        jLabel14.setBounds(new Rectangle(10, 75, 115, 15));
        rs=query.select("select count(*) from IISC_Function  where PR_id="+ tree.ID +"");
        rs.next();
        j=rs.getInt(1);
        query.Close();
        sa=query.selectArray("select * from IISC_Function  where PR_id="+ tree.ID +"",j,3);
        sa[0]="";
        query.Close();
        cmbAttUpd=new JComboBox(sa);
        cmbAttUpd.setBounds(new Rectangle(130, 100,265, 20));
        jLabel15.setText("On-update function:");
        jLabel15.setFont(new Font("SansSerif", 0, 11));
        jLabel15.setBounds(new Rectangle(10, 105, 115, 15));
        rs=query.select("select count(*) from IISC_Function where PR_id="+ tree.ID +" ");
        rs.next();
        j=rs.getInt(1);
        query.Close();
        sa=query.selectArray("select * from IISC_Function  where PR_id="+ tree.ID +" ",j,3);
        sa[0]="";
        query.Close();
        cmbAttDel=new JComboBox(sa);
        cmbAttDel.setBounds(new Rectangle(130, 130,265, 20));
        jLabel16.setText("On-delete function:");
        jLabel16.setFont(new Font("SansSerif", 0, 11));
        jLabel16.setBounds(new Rectangle(10, 135, 115, 15));
        btnAdd.setMaximumSize(new Dimension(50, 30));
        btnAdd.setPreferredSize(new Dimension(50, 30));
        btnAdd.setText("Add");
        btnAdd.setBounds(new Rectangle(250, 160, 90, 30));
        btnAdd.setMinimumSize(new Dimension(50, 30));
        btnAdd.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              add_ActionPerformed(ae);
            }
          });
        btnRemove.setMaximumSize(new Dimension(50, 30));
        btnRemove.setPreferredSize(new Dimension(50, 30));
        btnRemove.setText("Remove");
        btnRemove.setBounds(new Rectangle(345, 160, 90, 30));
        btnRemove.setMinimumSize(new Dimension(50, 30));
        btnRemove.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              remove_ActionPerformed(ae);
            }
          });
        qtm=new QueryTableModel(connection,id);
        table=new JTable(qtm);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setGridColor(new Color(0,0,0));
        table.setBackground(Color.white);
        table.setAutoResizeMode(0);
        table.setAutoscrolls(true);
	rowSM = table.getSelectionModel();
        cmbDomain.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              cmbDomain_actionPerformed(e);
            }
          });
        cmbAttributeRenamed.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              cmbAttributeRenamed_actionPerformed(e);
            }
          });
        cmbFunction.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              cmbFunction_actionPerformed(e);
            }
          });
        txtName.addKeyListener(new KeyAdapter()
          {
            public void keyTyped(KeyEvent e)
            {
              txtName_keyTyped(e);
            }
    
            public void keyReleased(KeyEvent e)
            {
              txtName_keyReleased(e);
            }
          });
        txtName.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              txtName_actionPerformed(e);
            }
          });
        btnFirst.setBounds(new Rectangle(40, 5, 25, 20));
        btnPrev.setBounds(new Rectangle(70, 5, 25, 20));
        btnNext.setBounds(new Rectangle(335, 5, 25, 20));
        btnLast.setBounds(new Rectangle(365, 5, 25, 20));
        btnAdd.setFont(new Font("SansSerif", 0, 11));
        btnRemove.setFont(new Font("SansSerif", 0, 11));
        jTabbedPane1.setFont(new Font("SansSerif", 0, 11));
        txtDescription.setFont(new Font("SansSerif", 0, 11));
        txtDescription.addKeyListener(new KeyAdapter()
          {
            public void keyTyped(KeyEvent e)
            {
              txtDescription_keyTyped(e);
            }
          });
        JPanel1.setFont(new Font("SansSerif", 0, 11));
        chSUBP.setFont(new Font("SansSerif", 0, 11));
        chSUBP.addPropertyChangeListener(new PropertyChangeListener()
          {
            public void propertyChange(PropertyChangeEvent e)
            {
              chSUBP_propertyChange(e);
            }
          });
        chSUBP.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              chSUBP_actionPerformed(e);
            }
          });
        btnErase.setFont(new Font("SansSerif", 0, 11));
        btnNew.setFont(new Font("SansSerif", 0, 11));
        btnSave.setFont(new Font("SansSerif", 0, 11));
        btnSave.setEnabled(false);
        btnClose.setFont(new Font("SansSerif", 0, 11));
      table.setBorder(BorderFactory.createLineBorder(Color.black, 1));
       //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setBounds(new Rectangle(5, 205, 430, 115));
        jScrollPane1.setFont(new Font("SansSerif", 0, 11));
        btnHelp.setBounds(new Rectangle(415, 405, 35, 30));
        btnHelp.setIcon(imageHelp);
        btnHelp.setFont(new Font("SansSerif", 0, 11));
        btnHelp.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnHelp_actionPerformed(e);
            }
          });
        jPanel4.setLayout(null);
        jPanel4.setFont(new Font("SansSerif", 0, 11));
        jLabel6.setText("Comment:");
        jLabel6.setBounds(new Rectangle(5, 5, 85, 20));
        jLabel6.setFont(new Font("SansSerif", 0, 11));
        txtComment.setBounds(new Rectangle(5, 30, 430, 290));
        txtComment.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        txtComment.setFont(new Font("SansSerif", 0, 11));
        txtComment.addKeyListener(new KeyAdapter()
          {
            public void keyTyped(KeyEvent e)
            {
              txtComment_keyTyped(e);
            }
          });
        btnApply.setMaximumSize(new Dimension(50, 30));
        btnApply.setPreferredSize(new Dimension(50, 30));
        btnApply.setText("Apply");
        btnApply.setBounds(new Rectangle(5, 405, 75, 30));
        btnApply.setMinimumSize(new Dimension(50, 30));
        btnApply.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              apply_ActionPerformed(ae);
            }
          });
        btnApply.setFont(new Font("SansSerif", 0, 11));
        btnApply.setEnabled(false);
        rdElementary.setText("Elementary");
        rdElementary.setFont(new Font("SansSerif", 0, 11));
        rdElementary.setBounds(new Rectangle(10, 195, 82, 20));
        rdElementary.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              rdElementary_actionPerformed(e);
            }
          });
        rdRenamed.setText("Renamed from");
        rdRenamed.setBounds(new Rectangle(90, 195, 95, 20));
        rdRenamed.setFont(new Font("SansSerif", 0, 11));
        rdRenamed.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              rdRenamed_actionPerformed(e);
            }
          });
        cmbAtt.setFont(new Font("SansSerif", 0, 11));
        cmbAtt.setBounds(new Rectangle(100, 5, 230, 20));
        cmbAtt.addItemListener(new ItemListener()
          {
            public void itemStateChanged(ItemEvent e)
            {
              cmbAtt_itemStateChanged(e);
            }
          });
        cmbAtt.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              jComboBox1_actionPerformed(e);
            }
          });
        jLabel5.setText("Derived:");
        jLabel5.setBounds(new Rectangle(15, 230, 85, 20));
        jLabel5.setFont(new Font("SansSerif", 0, 11));
        rdYes.setText("Yes");
        rdYes.setBounds(new Rectangle(90, 260, 50, 15));
        rdYes.setFont(new Font("SansSerif", 0, 11));
        rdYes.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              rdYes_actionPerformed(e);
            }
          });
        rdYes.addPropertyChangeListener(new PropertyChangeListener()
          {
            public void propertyChange(PropertyChangeEvent e)
            {
              rdYes_propertyChange(e);
            }
          });
        rdNo.setText("No");
        rdNo.setBounds(new Rectangle(90, 235, 45, 20));
        rdNo.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              rdNo_actionPerformed(e);
            }
          });
        rdNo.addPropertyChangeListener(new PropertyChangeListener()
          {
            public void propertyChange(PropertyChangeEvent e)
            {
              rdNo_propertyChange(e);
            }
          });
        btnDomain.setMaximumSize(new Dimension(50, 30));
        btnDomain.setPreferredSize(new Dimension(50, 30));
        btnDomain.setText("...");
        btnDomain.setBounds(new Rectangle(405, 10, 30, 20));
        btnDomain.setMinimumSize(new Dimension(50, 30));
        btnDomain.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              domain_ActionPerformed(ae);
            }
          });
        btnDomain.setFont(new Font("SansSerif", 0, 11));
        btnFunction.setMaximumSize(new Dimension(50, 30));
        btnFunction.setPreferredSize(new Dimension(50, 30));
        btnFunction.setText("...");
        btnFunction.setBounds(new Rectangle(405, 295, 30, 20));
        btnFunction.setMinimumSize(new Dimension(50, 30));
        btnFunction.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              fun_ActionPerformed(ae);
            }
          });
        btnFunction.setFont(new Font("SansSerif", 0, 11));
        btnFunction1.setMaximumSize(new Dimension(50, 30));
        btnFunction1.setPreferredSize(new Dimension(50, 30));
        btnFunction1.setText("...");
        btnFunction1.setBounds(new Rectangle(405, 70, 30, 20));
        btnFunction1.setMinimumSize(new Dimension(50, 30));
        btnFunction1.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              funins_ActionPerformed(ae);
            }
          });
        btnFunction1.setFont(new Font("SansSerif", 0, 11));
        btnFunction2.setMaximumSize(new Dimension(50, 30));
        btnFunction2.setPreferredSize(new Dimension(50, 30));
        btnFunction2.setText("...");
        btnFunction2.setBounds(new Rectangle(405, 100, 30, 20));
        btnFunction2.setMinimumSize(new Dimension(50, 30));
        btnFunction2.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              funupd_ActionPerformed(ae);
            }
          });
        btnFunction2.setFont(new Font("SansSerif", 0, 11));
        btnFunction3.setMaximumSize(new Dimension(50, 30));
        btnFunction3.setPreferredSize(new Dimension(50, 30));
        btnFunction3.setText("...");
        btnFunction3.setBounds(new Rectangle(405, 130, 30, 20));
        btnFunction3.setMinimumSize(new Dimension(50, 30));
        btnFunction3.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              fundel_ActionPerformed(ae);
            }
          });
        btnFunction3.setFont(new Font("SansSerif", 0, 11));
        btnFunction4.setMaximumSize(new Dimension(50, 30));
        btnFunction4.setPreferredSize(new Dimension(50, 30));
        btnFunction4.setText("...");
        btnFunction4.setBounds(new Rectangle(405, 20, 30, 20));
        btnFunction4.setMinimumSize(new Dimension(50, 30));
        btnFunction4.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              att_ActionPerformed(ae);
            }
          });
        btnFunction4.setFont(new Font("SansSerif", 0, 11));
        btnFunction5.setMaximumSize(new Dimension(50, 30));
        btnFunction5.setPreferredSize(new Dimension(50, 30));
        btnFunction5.setText("...");
        btnFunction5.setBounds(new Rectangle(405, 195, 30, 20));
        btnFunction5.setMinimumSize(new Dimension(50, 30));
        btnFunction5.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent ae)
            {
              rename_ActionPerformed(ae);
            }
          });
        btnFunction5.setFont(new Font("SansSerif", 0, 11));
        txtDefault.setFont(new Font("SansSerif", 0, 11));
        txtDefault.setBounds(new Rectangle(85, 65, 145, 20));
        txtDefault.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              txtDefault_actionPerformed(e);
            }
          });
        jLabel17.setFont(new Font("SansSerif", 0, 11));
        jLabel17.setBounds(new Rectangle(10, 65, 75, 20));
        jLabel17.setText("Default value:");
            txtExpression.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        txtExpression.setFont(new Font("SansSerif", 0, 11));
        txtExpression.addKeyListener(new KeyAdapter()
          {
            public void keyTyped(KeyEvent e)
            {
                            txtExpression_keyTyped(e);
                        }
    
                        public void keyReleased(KeyEvent e) {
                            txtExpression_keyReleased(e);
                        }
    
                        public void keyPressed(KeyEvent e) {
                            txtExpression_keyPressed(e);
                        }
                    });
        txtExpression.addFocusListener(new FocusAdapter() {
                    public void focusGained(FocusEvent e) {
                        txtExpression_focusGained(e);
                    }
                });
        txtExpression.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        txtExpression_mouseClicked(e);
                    }
                });
        jPanel5.setLayout(null);


        bgrp.add(rdRenamed);
        bgrp.add(rdElementary);
        bgrp1.add(rdYes);
        bgrp1.add(rdNo);
        jPanel4.add(jLabel6, null);
        jPanel4.add(txtComment, null);
        jToolBar1.add(cmbAtt, null);
        jToolBar1.add(btnFirst, null);
        jToolBar1.add(btnPrev, null);
        jToolBar1.add(btnNext, null);
        jToolBar1.add(btnLast, null);
        JPanel1.add(btnFunction5, null);
        JPanel1.add(btnFunction, null);
        JPanel1.add(rdNo, null);
        JPanel1.add(rdYes, null);
        JPanel1.add(jLabel5, null);
        JPanel1.add(rdRenamed, null);
        JPanel1.add(rdElementary, null);
        JPanel1.add(chSUBP, null);
        JPanel1.add(cmbFunction, null);
        JPanel1.add(jLabel12, null);
        JPanel1.add(txtName, null);
        JPanel1.add(jLabel10, null);
        JPanel1.add(jLabel3, null);
        JPanel1.add(txtDescription, null);
        JPanel1.add(cmbAttributeRenamed, null);
        jPanel2.add(btnFunction4, null);
        jPanel2.add(btnFunction3, null);
        jPanel2.add(btnFunction2, null);
        jPanel2.add(btnFunction1, null);
        jPanel2.add(table, null);
        jPanel2.add(jScrollPane1, null);
        jPanel2.add(btnRemove, null);
        jPanel2.add(btnAdd, null);
        jPanel2.add(jLabel16, null);
        jPanel2.add(cmbAttDel, null);
        jPanel2.add(jLabel15, null);
        jPanel2.add(cmbAttUpd, null);
        jPanel2.add(jLabel14, null);
        jPanel2.add(cmbAttIns, null);
        jPanel2.add(jLabel13, null);
        jPanel2.add(cmbAttribute, null);
        jScrollPane1.getViewport().add(table, null);
        jTabbedPane1.addTab("Attribute", JPanel1);
        jTabbedPane1.addTab("Derived from attributes", jPanel2);
        jTabbedPane1.addTab("Domain", jPanel3);
        jTabbedPane1.addTab("Display", jPanel5);
        jTabbedPane1.addTab("Notes", jPanel4);

        jScrollPaneErr.getViewport().add(errorList, null);
        jScrollPane2.getViewport().add(txtExpression, null);
        jPanel3.add(jScrollPane2, null);
        jPanel3.add(jLabel4, null);
        jPanel3.add(jLabel2, null);
        jPanel3.add(jLabel1, null);
        jPanel3.add(jScrollPaneErr, null);
        jPanel3.add(chkButton, null);
        jPanel3.add(expButton, null);
        jPanel3.add(jLabel17, null);
        jPanel3.add(txtDefault, null);
        jPanel3.add(btnDomain, null);
        jPanel3.add(btnEdit, null);
        jPanel3.add(cmbDomain, null);
        jPanel3.add(jLabel11, null);
        this.getContentPane().add(btnApply, null);
        this.getContentPane().add(jTabbedPane1, null);
        this.getContentPane().add(btnClose, null);
        this.getContentPane().add(btnSave, null);
        this.getContentPane().add(btnNew, null);
        this.getContentPane().add(btnErase, null);
        this.getContentPane().add(btnHelp, null);
        this.getContentPane().add(jToolBar1, null);
        setAttribute(Mnem);
        adp = new AttDisplayProperties(jTabbedPane1.getWidth() - 30, 350, connection, 
                         (IISFrameMain)getParent(), id, this, tree.ID);
        jRadioButton2.setBounds(new Rectangle(80, 5, 80, 20));
        jRadioButton1.setBounds(new Rectangle(265, 5, 85, 20));
        jPanel1.setLayout(null);
        jPanel1.setBorder(BorderFactory.createLineBorder(new Color(124, 124, 
                                                                   124), 1));
        jPanel1.setBounds(new Rectangle(20, 10, 405, 30));
        expButton.setText("Expression Editor");
        expButton.setBounds(new Rectangle(85, 210, 170, 30));
        expButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        expButton_actionPerformed(e);
                    }
                });
        chkButton.setText("Check Syntax");
        chkButton.setBounds(new Rectangle(260, 210, 170, 30));
        chkButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        chkButton_actionPerformed(e);
                    }
                });
        jScrollPaneErr.setBounds(new Rectangle(85, 245, 345, 75));
        jScrollPaneErr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jRadioButton2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jRadioButton2_actionPerformed(e);
                    }
                });
        jRadioButton1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jRadioButton1_actionPerformed(e);
                    }
                });
        jRadioButton2.setText("Inherit");
        jRadioButton1.setText("Override");

        adp.setBounds(20, 45, jTabbedPane1.getWidth() - 30, 350);
        jPanel1.add(jRadioButton2, null);
        jPanel1.add(jRadioButton1, null);
        jPanel5.add(jPanel1, null);
        jPanel5.add(adp);
        jPanel5.repaint();
    }
    public void actionPerformed(ActionEvent ae){   
    }
    
    
    private void prevv_ActionPerformed(ActionEvent e){
        String s="";
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        try{
            if(btnSave.isEnabled())
                if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0){
                    update(1);
                }
        
        rs1=query.select("select * from IISC_Attribute where PR_id="+ tree.ID +" order by  Att_mnem asc");
        if(rs1.next()){
            s=rs1.getString(3);
        }
        query.Close();
        
        setAttribute(s);
        Mnem=s;
        tree.select_node(Mnem,"Attributes");
        }
        catch(SQLException ef){
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void prev_ActionPerformed(ActionEvent e){
        String s=Mnem.trim() ;
        
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        try{
            if(btnSave.isEnabled())
                if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0){
                    update(1);
            }
            
            rs1=query.select("select Att_mnem from IISC_Attribute  where PR_id="+ tree.ID +" and Att_mnem<'" + s + "' order by Att_mnem desc" );
            if(rs1.next()){
                s=rs1.getString(1);
            }
            query.Close();
        
            setAttribute(s);
            tree.select_node(Mnem,"Attributes");
        
            Mnem=s;
        }
        catch(SQLException ef){
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void next_ActionPerformed(ActionEvent e){
        String s=Mnem.trim() ;
        
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        try
        {
            if(btnSave.isEnabled())
                if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0){
                    update(1);
                }
            rs1=query.select("select Att_mnem from IISC_Attribute  where PR_id="+ tree.ID +" and Att_mnem>'" + s + "' order by Att_mnem asc" );
            if(rs1.next()){
                s=rs1.getString(1);
            }
            query.Close();
            
            setAttribute(s);
            Mnem=s;
            tree.select_node(Mnem,"Attributes");
        }
        catch(SQLException ef){
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }


    private void nextt_ActionPerformed(ActionEvent e){
        String s="";
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        try{
            if(btnSave.isEnabled())
                if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0){
                    update(1);
                }
                
            rs1=query.select("select * from IISC_Attribute  where PR_id="+ tree.ID +" order by  Att_mnem desc");
            if(rs1.next()){
                s=rs1.getString(3);
            }
            query.Close();
            
            setAttribute(s);
            Mnem=s;
            tree.select_node(Mnem,"Attributes");
        }
        catch(SQLException ef){
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void close_ActionPerformed(ActionEvent e){
        if(btnSave.isEnabled())
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0){
                update(1);
            }
        this.dispose();
    }
    
    private void remove_ActionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
        //if( !table.isRowSelected(0))
        if(table.getSelectedRowCount()>0)
        qtm.deleteRow(table.getSelectedRow());
    }
    
    
    private void save_ActionPerformed(ActionEvent e){
        dispose=true;
        //checkSyntax(true);
        checkSyntax2(txtExpression,true,connection,1,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2);
        update(1);
        tree.select_node(Mnem,"Attributes");
    }
    
    private void apply_ActionPerformed(ActionEvent e){
        dispose=false;
        //checkSyntax(true);
        checkSyntax2(txtExpression,true,connection,1,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2);
        update(1);
        tree.select_node(Mnem,"Attributes");
    }
    
    
    private void add_ActionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;
        try { 
            rs1=query.select("select *  from IISC_Derived_Attribute, IISC_ATTRIBUTE where IISC_Derived_Attribute.Att_id_derived=IISC_ATTRIBUTE.Att_id and  IISC_Derived_Attribute.PR_id="+ tree.ID +" and Att_mnem='"+ cmbAttribute.getSelectedItem().toString() +"' and  IISC_Derived_Attribute.Att_id="+id );
            if(rs1.next()){
                JOptionPane.showMessageDialog(null, "<html><center>Attribute already in list!", "Attributes", JOptionPane.ERROR_MESSAGE);
                query.Close();
            }
            else if(cmbAttribute.getSelectedItem().toString().equals(""))
                JOptionPane.showMessageDialog(null, "<html><center>Attribute required!", "Attributes", JOptionPane.ERROR_MESSAGE);
            else if(cmbAttDel.getSelectedItem().toString().equals("") && cmbAttIns.getSelectedItem().toString().equals("") && cmbAttUpd.getSelectedItem().toString().equals("") )
                JOptionPane.showMessageDialog(null, "<html><center>At least one function required!", "Attributes", JOptionPane.ERROR_MESSAGE);
            else{
                query.Close();
                rs1=query.select("select max(DA_id)+1  from IISC_Derived_Attribute");
                int id1;
                if(rs1.next())
                    id1=rs1.getInt(1);
                else
                    id1=0;
                query.Close();
                rs1=query.select("select *   from IISC_Attribute  where PR_id="+ tree.ID +" and Att_mnem='"+ cmbAttribute.getSelectedItem().toString() +"'");
                int ida;
                if(rs1.next())
                    ida=rs1.getInt(1);
                else 
                    ida=0;
                query.Close();
                int i=query.update("insert into IISC_Derived_Attribute(DA_id,PR_id,Att_id,Att_id_derived) values ("+ id1 +","+ tree.ID +","+ id +","+ ida +")");
                rs1=query.select("select max(DAF_id)+1  from IISC_DER_ATT_FUN");
                int idaf;
                if(rs1.next())
                    idaf=rs1.getInt(1);
                else 
                    idaf=0;
                query.Close();

                if(!cmbAttDel.getSelectedItem().toString().equals("")){
                    rs1=query.select("select *   from IISC_Function  where PR_id="+ tree.ID +" and Fun_name='"+ cmbAttDel.getSelectedItem().toString() +"'");
                    int idf=0;
                    rs1.next();
                    idf=rs1.getInt(1);
                    query.Close();
                    int k=query.update("insert into IISC_DER_ATT_FUN(DAF_ID,PR_id,DA_id,Fun_id,DAF_mode) values ("+ idaf +","+ tree.ID +","+ id1 +","+ idf +",'delete')");
                }
                rs1=query.select("select max(DAF_id)+1  from IISC_DER_ATT_FUN");
                if(rs1.next())
                    idaf=rs1.getInt(1);
                else
                    idaf=0;
                query.Close();
                if(!cmbAttUpd.getSelectedItem().toString().equals("")){
                    rs1=query.select("select *   from IISC_Function  where PR_id="+ tree.ID +" and Fun_name='"+ cmbAttUpd.getSelectedItem().toString() +"'");
                    int idf;
                    rs1.next();
                    idf=rs1.getInt(1);
                    query.Close();
                    int k=query.update("insert into IISC_DER_ATT_FUN(DAF_ID,PR_id,DA_id,Fun_id,DAF_mode) values ("+ idaf +","+ tree.ID +","+ id1 +","+ idf +",'update')");
                }
                rs1=query.select("select max(DAF_id)+1  from IISC_DER_ATT_FUN");
                if(rs1.next())
                    idaf=rs1.getInt(1);
                else 
                    idaf=0;
                query.Close();
                if(!cmbAttIns.getSelectedItem().toString().equals("")){
                    rs1=query.select("select *   from IISC_Function  where PR_id="+ tree.ID +" and Fun_name='"+ cmbAttIns.getSelectedItem().toString() +"'");
                    int idf;
                    rs1.next();
                    idf=rs1.getInt(1);
                    query.Close();
                    int k=query.update("insert into IISC_DER_ATT_FUN(DAF_ID,PR_id,DA_id,Fun_id,DAF_mode) values ("+ idaf +","+ tree.ID +","+ id1 +","+ idf +",'insert')");
                    cmbAttIns.setSelectedItem("");
                    cmbAttUpd.setSelectedItem("");
                    cmbAttDel.setSelectedItem("");
                }
                qtm.setQuery("select * from IISC_DERIVED_ATTRIBUTE where Att_id="+id);
                int width=117;
                table.getColumnModel().getColumn(0).setPreferredWidth(width);
                table.getColumnModel().getColumn(1).setPreferredWidth(width);
                table.getColumnModel().getColumn(2).setPreferredWidth(width);
            }
        }  
        catch(SQLException ef){
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void Reload (){
        try{
            ResultSet rs,rs1;
            JDBCQuery query=new JDBCQuery(connection);
            String m=cmbAttribute.getSelectedItem().toString();
            rs=query.select("select count(*) from IISC_Attribute where PR_ID="+ tree.ID);
            rs.next();
            int j=rs.getInt(1);
            query.Close();
            String[] sad=query.selectArray("select * from IISC_Attribute where PR_ID="+ tree.ID,j,3);
            sad[0]="";
            query.Close();
            cmbAttribute.removeAllItems();
            for(int k=0;k<sad.length; k++)
                cmbAttribute.addItem(sad[k]);
            cmbAttribute.setSelectedItem(m);
            m=cmbDomain.getSelectedItem().toString();
            rs=query.select("select count(*) from IISC_Domain  where PR_id="+ tree.ID +"");
            rs.next();
            j=rs.getInt(1);
            query.Close();
            String[] sa=query.selectArray("select * from IISC_Domain  where PR_id="+ tree.ID +"",j,3);
            sa[0]="";
            query.Close();
            cmbDomain.removeAllItems();
            for (int k=0; k<sa.length;k++)
                cmbDomain.addItem(sa[k]);
            cmbDomain.setSelectedItem(m);
            m=cmbFunction.getSelectedItem().toString();
            rs=query.select("select count(*) from IISC_Function  where PR_id="+ tree.ID +"");
            rs.next();
            j=rs.getInt(1);
            query.Close();
            sa=query.selectArray("select * from IISC_Function  where PR_id="+ tree.ID +"",j,3);
            sa[0]="";
            query.Close();
            cmbFunction.removeAllItems();
            for(int k=0;k<sa.length; k++)
                cmbFunction.addItem(sa[k]);
            cmbFunction.setSelectedItem(m);
            m=cmbAttDel.getSelectedItem().toString();
            rs=query.select("select count(*) from IISC_Function  where PR_id="+ tree.ID +"");
            rs.next();
            j=rs.getInt(1);
            query.Close();
            sa=query.selectArray("select * from IISC_Function  where PR_id="+ tree.ID +"",j,3);
            sa[0]="";
            query.Close();
            cmbAttDel.removeAllItems();
            for(int k=0;k<sa.length; k++)
                cmbAttDel.addItem(sa[k]);
            cmbAttDel.setSelectedItem(m);
            m=cmbAttIns.getSelectedItem().toString();
            rs=query.select("select count(*) from IISC_Function  where PR_id="+ tree.ID +"");
            rs.next();
            j=rs.getInt(1);
            query.Close();
            sa=query.selectArray("select * from IISC_Function  where PR_id="+ tree.ID +"",j,3);
            sa[0]="";
            query.Close();
            cmbAttIns.removeAllItems();
            for(int k=0;k<sa.length; k++)
                cmbAttIns.addItem(sa[k]);
            cmbAttIns.setSelectedItem(m);
            m=cmbAttUpd.getSelectedItem().toString();
            rs=query.select("select count(*) from IISC_Function  where PR_id="+ tree.ID +"");
            rs.next();
            j=rs.getInt(1);
            query.Close();
            sa=query.selectArray("select * from IISC_Function  where PR_id="+ tree.ID +"",j,3);
            sa[0]="";
            query.Close();
            cmbAttUpd.removeAllItems();
            for(int k=0;k<sa.length; k++)
                cmbAttUpd.addItem(sa[k]);
            cmbAttUpd.setSelectedItem(m);
        }
        catch(SQLException ef){
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void new_ActionPerformed(ActionEvent e){
        Mnem="";
        id=-1;
        setAttribute("");
    }
    
    private void edit_ActionPerformed(ActionEvent e){   
        try{
            JDBCQuery query=new JDBCQuery(connection);
            ResultSet rs1;
            int d;
            rs1=query.select("select *  from IISC_Domain  where PR_id="+ tree.ID +" and  Dom_mnem='" + cmbDomain.getSelectedItem().toString().trim() + "'");
            if(rs1.next())
                d=rs1.getInt("Dom_id");
            else 
                d=-1;
            query.Close();
            ((IISFrameMain)getParent()).dom=new Domain((IISFrameMain)getParent(),((IISFrameMain)getParent()).desktop.getSelectedFrame().getTitle(),true,connection, d,(PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame());
            Settings.Center(((IISFrameMain)getParent()).dom);
            ((IISFrameMain)getParent()).dom.att=id;
            ((IISFrameMain)getParent()).dom.at=this;
            ((IISFrameMain)getParent()).dom.btnClose.setText("Select");
            ((IISFrameMain)getParent()).dom.setVisible(true);
        }
        catch(SQLException ef){
            JOptionPane.showMessageDialog(null, ef.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
  
  
    public void update (int k){
        JDBCQuery query=new JDBCQuery(connection),query1=new JDBCQuery(connection);
        ResultSet rs1,rs;  
        boolean can=true;
        //System.out.println(txtName.getText().trim());
        try{
            if(Mnem.trim().equals(""))
            {
                rs1=query.select("select * from IISC_Attribute  where PR_id="+ tree.ID +" and Att_mnem='"+ txtName.getText().trim() +"'");
                if(rs1.next()){
                    JOptionPane.showMessageDialog(null, "<html><center>Name exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    can=false;
                }
                query.Close();
            }
            else{
                if(!Mnem.toLowerCase().trim().equals(txtName.getText().toLowerCase().trim())){
                    rs1=query.select("select * from IISC_Attribute  where PR_id="+ tree.ID +" and Att_mnem='"+ txtName.getText().trim() +"'");
                    if(rs1.next()){ 
                        JOptionPane.showMessageDialog(null, "<html><center>Name exists", "Error", JOptionPane.ERROR_MESSAGE);
                        can=false;
                    }
                    query.Close();
                }  
            }
            if(txtName.getText().trim().equals(""))
                JOptionPane.showMessageDialog(null, "<html><center>Name required!", "Attributes", JOptionPane.ERROR_MESSAGE);
            else if(txtName.getText().split(" ").length>1)
                JOptionPane.showMessageDialog(null, "<html><center>Name cannot contain blank character!", "Attributes", JOptionPane.ERROR_MESSAGE);
            else if(txtDescription.getText().trim().equals(""))
                JOptionPane.showMessageDialog(null, "<html><center>Description required!", "Attributes", JOptionPane.ERROR_MESSAGE);
            else if(cmbDomain.getSelectedItem().toString().trim().equals(""))
                JOptionPane.showMessageDialog(null, "<html><center>Domain required!", "Attributes", JOptionPane.ERROR_MESSAGE);
            else if(cmbAttributeRenamed.getSelectedItem().toString().trim().equals("") && rdRenamed.isSelected())
                JOptionPane.showMessageDialog(null, "<html><center>Renamed Attribute required!", "Attributes", JOptionPane.ERROR_MESSAGE);
            else if(rdYes.isSelected() && cmbFunction.getSelectedItem().toString().trim().equals("") && cmbAttDel.getSelectedItem().toString().trim().equals("")  && cmbAttUpd.getSelectedItem().toString().trim().equals("")  && cmbAttIns.getSelectedItem().toString().trim().equals("") )
                JOptionPane.showMessageDialog(null, "<html><center>Derived Attribute requires at least one function to be specified!", "Attributes", JOptionPane.ERROR_MESSAGE);
            else if(!checkSyntax2(txtExpression,true,connection,1,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2).check)
                JOptionPane.showMessageDialog(null, "<html><center>Check expression syntax!", "User defined domains", JOptionPane.ERROR_MESSAGE);            
            else if(can){
                String dom;
                String fun;
                int subp,der;
                int elem=-1;
                rs1=query.select("select *  from IISC_Domain  where PR_id="+ tree.ID +" and  Dom_mnem='" + cmbDomain.getSelectedItem().toString().trim() + "'");
                if(rs1.next())
                    dom=rs1.getString("Dom_id");
                else dom=null;
                    query.Close();
                rs1=query.select("select *  from IISC_Function  where PR_id="+ tree.ID +" and  Fun_name='" + cmbFunction.getSelectedItem().toString().trim() + "'");
                if(rs1.next() && rdYes.isSelected())
                    fun=rs1.getString("Fun_id");
                else fun=null;
                    query.Close();
                if(rdElementary.isSelected())
                    elem=-1;
                else {
                    rs1=query.select("select *  from IISC_ATTRIBUTE  where PR_id="+ tree.ID +" and  Att_mnem='" + cmbAttributeRenamed.getSelectedItem().toString().trim() + "'");
                    rs1.next();
                    elem=rs1.getInt("Att_id");
                    query.Close();
                }
                
                if(chSUBP.isSelected())
                    subp=1;
                else 
                    subp=0;
                if(rdYes.isSelected())  
                    der=1;
                else 
                    der=0;
                    
                if(Mnem.trim().equals("")){
                    rs1=query.select("select max(Att_id)+1  from IISC_Attribute");
                    if(rs1.next())
                        id=rs1.getInt(1);
                    else 
                        id=0;
                    query.Close();
                    int i=query.update("insert into IISC_Attribute(Att_id,PR_id,Att_mnem,Dom_id,Fun_id,Att_name,Att_expr,Att_sbp,Att_elem,Att_der,Att_default,Att_comment) values ("+ id +","+ tree.ID +", '" + txtName.getText().trim() + "'," + dom + "," + fun + ",'" + txtDescription.getText().trim() + "','" + txtExpression.getText().trim().replaceAll("'","''") + "'," + subp + "," + elem + "," + der + ",'" + txtDefault.getText() + "','" + txtComment.getText().trim() + "')");
                    tree.insert(txtName.getText().trim(),"Attributes");
                    Mnem=txtName.getText().trim();
                }
                else{
                    int i=query.update("update IISC_Attribute set Att_mnem='" + txtName.getText().trim() + "',Att_name='" + txtDescription.getText().trim() + "',Att_expr='" + txtExpression.getText().trim().replaceAll("'","''") + "', Dom_id=" + dom + ", Fun_id=" + fun + ", Att_elem=" + elem + ", Att_sbp=" + subp + ",Att_der=" + der + ",Att_default='" + txtDefault.getText() + "',Att_comment='" + txtComment.getText().trim() + "' where Att_id=" + id + ""); 
                    tree.change(Mnem ,"Attributes",txtName.getText().trim());
                    Mnem=txtName.getText().trim();
                }
                rs=query1.select("select ID_name,a.Att_id,a.ID_id, count(*) from IISC_INC_DEP_LHS_RHS as a,IISC_INC_DEP_LHS_RHS as b,IISC_INCLUSION_DEPENDENCY  where a.ID_id= IISC_INCLUSION_DEPENDENCY.ID_id and a.ID_id=b.ID_id and a.PR_id="+ tree.ID + " and a.Att_id="+ id +" and a.ID_lhs_rhs=0 and b.ID_lhs_rhs=1 and  b.PR_id="+ tree.ID+ " group by ID_name,a.Att_id,a.ID_id ");
                while(rs.next()){
                    int idc=rs.getInt(4);
                    int idi=rs.getInt(3);
                    
                    if(idc==1){ 
                        tree.removenode(rs.getString(1),"Inclusion Dependencies");
                        query.update("delete from IISC_INC_DEP_LHS_RHS  where ID_id="+ idi);   
                        query.update("delete from IISC_INCLUSION_DEPENDENCY  where ID_id="+ idi);   
                    }
                }
                query1.Close();    
                if(elem>=0){   
                    int m=0;
                    boolean go=true;
                    while(go){
                        m++;
                        rs=query.select("select * from IISC_INCLUSION_DEPENDENCY  where ID_name='Inclusion Dependency "+ m +"' and PR_id="+ tree.ID);
                        if(!rs.next()){
                            query.Close();
                            go=false;
                        }
                        else
                            query.Close();
                    }
                    String nameinc = "Inclusion Dependency "+ m;
                    rs=query.select("select max(ID_id)+1 from IISC_INCLUSION_DEPENDENCY"); 
                    int j=0;
                    if(rs.next())
                        j=rs.getInt(1);
                    query.Close();
                    query.update("insert into IISC_INCLUSION_DEPENDENCY(ID_id,PR_id,ID_name) values("+j+","+tree.ID+",'"+nameinc+"')");
                    query.update("insert into IISC_INC_DEP_LHS_RHS(ID_id,Att_id,PR_id,ID_lhs_rhs) values("+j+","+ id +","+tree.ID+",0)");
                    query.update("insert into IISC_INC_DEP_LHS_RHS(ID_id,Att_id,PR_id,ID_lhs_rhs) values("+j+","+ elem +","+tree.ID+",1)");
                    tree.insert(nameinc,"Inclusion Dependencies");
                }
                
                if(k==1){
                    adp.Att_id = id;
                    adp.Update();
                    JOptionPane.showMessageDialog(null, "<html><center>Attribute saved!", "Attributes", JOptionPane.INFORMATION_MESSAGE);
                    btnApply.setEnabled(false);
                    btnSave.setEnabled(false);
                }
                if(dispose)
                    dispose();
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void setAttribute (String m){ 
        try{  
            btnApply.setEnabled(false);
            btnSave.setEnabled(false);
            txtName.setText(m);
            
            ResultSet rs,rs1;
            JDBCQuery query=new JDBCQuery(connection);
            rs=query.select("select count(*) from IISC_Attribute where PR_ID="+ tree.ID);
            rs.next();
            int j=rs.getInt(1);
            query.Close();
            String[] sad=query.selectArray("select * from IISC_Attribute where PR_ID="+ tree.ID,j,3);
            sad[0]="";
            query.Close();
            cmbAtt.removeAllItems();
            for(int k=0;k<sad.length; k++)
                cmbAtt.addItem(sad[k]);
            cmbAtt.setSelectedItem(m);
            Mnem=m;
            if(Mnem.equals(""))
                tree.select_node(Mnem,"Attributes");
            rs=query.select("select count(*) from IISC_Domain  where PR_id="+ tree.ID +"");
            rs.next();
            j=rs.getInt(1);
            query.Close();
            String[] sa=query.selectArray("select * from IISC_Domain  where PR_id="+ tree.ID +"",j,3);
            sa[0]="";
            query.Close();
            cmbDomain.removeAllItems();
            for (int k=0; k<sa.length;k++)
                cmbDomain.addItem(sa[k]);
            rs1=query.select("select * from IISC_Attribute where PR_id="+ tree.ID +"  and Att_mnem='"+ Mnem +"'");
            if(rs1.next()){   
                int domid=rs1.getInt("Dom_id");
                txtName.setText(rs1.getString("Att_mnem"));
                txtComment.setText(rs1.getString("Att_comment"));
                txtDescription.setText(rs1.getString("Att_name"));
                txtExpression.setText(rs1.getString("Att_expr").trim());
                //checkSyntax(true);
                checkSyntax2(txtExpression,true,connection,1,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2);
                txtDefault.setText(rs1.getString("Att_default"));
                String funid=rs1.getString("Fun_id") ;
                if(rs1.getInt("Att_sbp")==1)
                {
                    chSUBP.setSelected(true);
                    rdNo.setEnabled(true);
                }
                else 
                {
                    chSUBP.setSelected(false);
                    rdNo.setEnabled(false);
                }
                int elem=rs1.getInt("Att_elem");
                if(rs1.getInt("Att_der")==1){
                    rdYes.setSelected(true);
                    rdNo.setSelected(false);
                }
                else{
                    rdYes.setSelected(false);
                    rdNo.setSelected(true);
                }
                id=rs1.getInt("Att_id");
                cmbAttUpd.setSelectedItem("");
                cmbAttIns.setSelectedItem("");
                cmbAttDel.setSelectedItem("");
                query.Close();
                cmbAttributeRenamed.setSelectedItem("");
                if(elem==-1){
                    rdElementary.setSelected(true);
                    rdRenamed.setSelected(false);
                }
                else{
                    rdElementary.setSelected(false);
                    rdRenamed.setSelected(true);
                    rs=query.select("select * from IISC_Attribute where PR_id="+ tree.ID +"  and Att_id="+ elem +"");
                    if(rs.next())
                        cmbAttributeRenamed.setSelectedItem(rs.getString("Att_mnem") );
                    query.Close();
                }
        
                cmbFunction.setSelectedItem("");
                if(funid!=null){
                    rs=query.select("select * from IISC_Function where PR_id="+ tree.ID +"  and Fun_id="+ funid+"");
                    if(rs.next())
                        cmbFunction.setSelectedItem(rs.getString("Fun_name") );
                    query.Close();
                }
                rs=query.select("select * from IISC_Domain where PR_id="+ tree.ID +"  and Dom_id="+ domid +"");
                if(rs.next())
                    cmbDomain.setSelectedItem(rs.getString("Dom_mnem") );
                query.Close();
            }
            else{
                query.Close();
                txtDefault.setText("");
                txtName.setText("");
                txtDescription.setText("");
                txtComment.setText("");
                txtExpression.setText("");
                rdElementary.setSelected(true);
                rdRenamed.setSelected(false);
                rdYes.setSelected(false);
                rdNo.setSelected(true);
                chSUBP.setSelected(true);
                cmbAttribute.setSelectedItem("");
                cmbFunction.setSelectedItem("");
                cmbAttUpd.setSelectedItem("");
                cmbAttIns.setSelectedItem("");
                cmbAttDel.setSelectedItem("");
                jTabbedPane1.setEnabledAt(1,false);
                cmbFunction.setSelectedItem("");
                cmbFunction.setEnabled(false);
                btnFunction.setEnabled(false);
                id = -1;
            }
            if(rdYes.isSelected()){ 
                if(chSUBP.isSelected())
                    jTabbedPane1.setEnabledAt(1,true);
                else
                    jTabbedPane1.setEnabledAt(1,false);
                cmbFunction.setEnabled(true);
                btnFunction.setEnabled(true);
            }
            else{
                jTabbedPane1.setEnabledAt(1,false);
                cmbFunction.setSelectedItem("");
                cmbFunction.setEnabled(false);
                btnFunction.setEnabled(false);
            }
            if(rdElementary.isSelected()){
                cmbAttributeRenamed.setEnabled(false);
                btnFunction5.setEnabled(false);
            }
            else{
                cmbAttributeRenamed.setEnabled(true);
                btnFunction5.setEnabled(true);
            }
            
            btnApply.setEnabled(false);
            btnSave.setEnabled(false);
            qtm.setQuery("select * from IISC_DERIVED_ATTRIBUTE where  Att_id="+id);
            int width = 117;
            table.getColumnModel().getColumn(0).setPreferredWidth(width);
            table.getColumnModel().getColumn(1).setPreferredWidth(width);
            table.getColumnModel().getColumn(2).setPreferredWidth(width);
            rs1=query.select("select * from IISC_Attribute  where PR_id="+ tree.ID +" and Att_mnem>'" + m.trim() + "'" );
            if(rs1.next()){
                btnNext.setEnabled(true);
                btnLast.setEnabled(true);
            }
            else{
                btnNext.setEnabled(false);
                btnLast.setEnabled(false);
            }
            query.Close();
            rs1=query.select("select * from IISC_Attribute where PR_id="+ tree.ID +" and  Att_mnem<'" + m.trim() + "'" );
            if(rs1.next()){
                btnPrev.setEnabled(true);
                btnFirst.setEnabled(true);
            }
            else{
                btnPrev.setEnabled(false);
                btnFirst.setEnabled(false);
            }
            query.Close();

            if (adp != null){
                jPanel5.remove(adp);
                adp = new AttDisplayProperties(jTabbedPane1.getWidth() - 30,350, connection, (IISFrameMain)getParent(), id, this, tree.ID);
                adp.setBounds(20, 45 , jTabbedPane1.getWidth() - 40, 350);
                jPanel5.add(adp);
                jPanel5.repaint();
                inherit = 0;
                jRadioButton2.setSelected(false);
                jRadioButton1.setSelected(false);
            }
        }
        catch(SQLException e){  
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void erase_ActionPerformed(ActionEvent e){
        if(delete()){
            tree.removenode(Mnem,"Attributes");
            Mnem="";
            setAttribute(Mnem);
            tree.select_node(Mnem,"Attributes");
        }
    }


    public boolean delete(){
        JDBCQuery query=new JDBCQuery(connection);
        ResultSet rs1;  
        boolean can=true;
        if(Mnem.equals(""))can=false;
        try
        {
            rs1=query.select("select * from IISC_ATT_TOB  where Att_id="+ id +"");
            if(rs1.next()){ 
                JOptionPane.showMessageDialog(null, "<html><center>Attribute can not be removed!", "Error", JOptionPane.ERROR_MESSAGE);
                can=false;
            } 
            query.Close();
            rs1=query.select("select * from IISC_DER_ATT_FUN  where DA_id="+ id +"");
            if(rs1.next()){ 
                JOptionPane.showMessageDialog(null, "<html><center>Attribute can not be removed!", "Error", JOptionPane.ERROR_MESSAGE);
                can=false;
            } 
            query.Close();
            if(can){
                int i=query.update("delete from IISC_Attribute where Att_id=" + id + ""); 
                i=query.update("delete from IISC_ATTRIBUTE_DISPLAY where Att_id=" + id + ""); 
                i=query.update("delete from IISC_ATT_DISPLAY_VALUES where Att_id=" + id + ""); 
                JOptionPane.showMessageDialog(null, "<html><center>Attribute removed!", "Attributes", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return can;
    }

    public void valueChanged(ListSelectionEvent e){}

    private void btnHelp_actionPerformed(ActionEvent e){
        Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, connection );
        Settings.Center(hlp);
        hlp.setVisible(true);
    }


    private void jComboBox1_actionPerformed(ActionEvent e)
    {
        try{
            if(btnSave.isEnabled())
                if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Inclusion Dependencies", JOptionPane.YES_NO_OPTION)==0){
                    update(1);
                }
            String s="";
            if(cmbAtt.getSelectedItem()!=null)
            s=cmbAtt.getSelectedItem().toString();
            setAttribute(s);
            Mnem=s;
            tree.select_node(Mnem,"Attributes");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void cmbAtt_itemStateChanged(ItemEvent e){
    }

    private void rdElementary_actionPerformed(ActionEvent e)
    {
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
        if(rdElementary.isSelected()){
            cmbAttributeRenamed.setEnabled(false);
            btnFunction5.setEnabled(false);
        }
        else{
            cmbAttributeRenamed.setEnabled(true);
            btnFunction5.setEnabled(true);
        }
    }

    private void txtName_actionPerformed(ActionEvent e){
    }

    private void chSUBP_actionPerformed(ActionEvent e){
        if(!chSUBP.isSelected()){
            JDBCQuery query=new JDBCQuery(connection);
            ResultSet rs1;  
            try{
                rs1=query.select("select * from IISC_ATT_KTO,IISC_FORM_TYPE  where IISC_ATT_KTO.TF_id=IISC_FORM_TYPE.TF_id  and Att_id="+ id +"");
                if(rs1.next()){ 
                    JOptionPane.showMessageDialog(null, "<html><center>The property <b>Exists in database schema</b> cannot be set to No.<br> This attribute participate in a component type key in the form type  <b>"+ rs1.getString("TF_mnem") +"</b>.", "Error", JOptionPane.ERROR_MESSAGE);
                    chSUBP.setSelected(true);
                }
                else
                {
                    query.Close();
                    rs1=query.select("select * from IISC_ATT_UTO,IISC_FORM_TYPE  where IISC_ATT_UTO.TF_id=IISC_FORM_TYPE.TF_id  and Att_id="+ id +"");
                    if(rs1.next()){ 
                        JOptionPane.showMessageDialog(null, "<html><center>The property  <b>Exists in database schema</b> cannot be set to No.<br> This attribute participate in a component type unique constraint in the form type  <b>"+ rs1.getString("TF_mnem") +"</b>.", "Error", JOptionPane.ERROR_MESSAGE);
                        chSUBP.setSelected(true);
                    } 
                }
                query.Close();

            }
            catch(SQLException eq){
                JOptionPane.showMessageDialog(null, eq.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
        if(!chSUBP.isSelected()){
            rdYes.setSelected(true);
            rdNo.setEnabled(false);
        }
        else
            rdNo.setEnabled(true);
        if(chSUBP.isSelected() && rdYes.isSelected()){
            jTabbedPane1.setEnabledAt(1,true);
            cmbFunction.setEnabled(true);
            btnFunction.setEnabled(true);
        }
        else if(!chSUBP.isSelected() && rdYes.isSelected()){
            jTabbedPane1.setEnabledAt(1,false);
            cmbFunction.setEnabled(true);
            btnFunction.setEnabled(true);
        }
        else{
            jTabbedPane1.setEnabledAt(1,false);
            cmbFunction.setEnabled(false);
            btnFunction.setEnabled(false);
        }
  }

    private void rdYes_propertyChange(PropertyChangeEvent e){
    }

    private void rdNo_propertyChange(PropertyChangeEvent e){ 
    }

    private void chSUBP_propertyChange(PropertyChangeEvent e){
    }

    private void rdYes_actionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
        if(rdYes.isSelected()){ 
            if(chSUBP.isSelected())
                jTabbedPane1.setEnabledAt(1,true);
            else
                jTabbedPane1.setEnabledAt(1,false);
            cmbFunction.setEnabled(true);
            btnFunction.setEnabled(true);
        }
        else{
            jTabbedPane1.setEnabledAt(1,false);
            cmbFunction.setSelectedItem("");
            cmbFunction.setEnabled(false);
            btnFunction.setEnabled(false);
        }
    }

    private void rdNo_actionPerformed(ActionEvent e)
    { 
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
        if(rdYes.isSelected() && chSUBP.isSelected()){
            jTabbedPane1.setEnabledAt(1,true);
            cmbFunction.setEnabled(true);
            btnFunction.setEnabled(true);
        }
        else{
            jTabbedPane1.setEnabledAt(1,false);
            cmbFunction.setSelectedItem("");
            cmbFunction.setEnabled(false);
            btnFunction.setEnabled(false);
        }
    }

    private void txtName_keyTyped(KeyEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void txtDescription_keyTyped(KeyEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void txtExpression_keyTyped(KeyEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
        //System.out.println("xxx:" + e.getKeyCode());
        //jScrollPane1.setVisible(false);    
    }

    private void rdRenamed_actionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
        if(rdElementary.isSelected()){
            cmbAttributeRenamed.setEnabled(false);
            btnFunction5.setEnabled(false);
        }
        else{
            cmbAttributeRenamed.setEnabled(true);
            btnFunction5.setEnabled(true);
        }
    }

    private void txtComment_keyTyped(KeyEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void cmbFunction_actionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void cmbAttributeRenamed_actionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void cmbDomain_actionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }

    private void domain_ActionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);  
        SearchTable ptype=new SearchTable((Frame)getParent(),"Select Parent Domain",true,connection);
        Settings.Center(ptype);
        ptype.type="Domain";
        ptype.owner=this;
        ptype.setVisible(true);
    }

    private void fun_ActionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);  
        SearchTable ptype=new SearchTable((Frame)getParent(),"Select Function",true,connection);
        Settings.Center(ptype);
        ptype.type="Function";
        ptype.item=cmbFunction;
        ptype.owner=this;
        ptype.setVisible(true);
    }
    
    private void funins_ActionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);  
        SearchTable ptype=new SearchTable((Frame)getParent(),"Select Function",true,connection);
        Settings.Center(ptype);
        ptype.type="Function";
        ptype.item=cmbAttIns;
        ptype.owner=this;
        ptype.setVisible(true);
    }

    private void funupd_ActionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);  
        SearchTable ptype=new SearchTable((Frame)getParent(),"Select Function",true,connection);
        Settings.Center(ptype);
        ptype.type="Function";
        ptype.item=cmbAttUpd;
        ptype.owner=this;
        ptype.setVisible(true);
    }
    
    private void fundel_ActionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);  
        SearchTable ptype=new SearchTable((Frame)getParent(),"Select Function",true,connection);
        Settings.Center(ptype);
        ptype.type="Function";
        ptype.item=cmbAttDel;
        ptype.owner=this;
        ptype.setVisible(true);
    }
    
    private void att_ActionPerformed(ActionEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);  
        SearchTable ptype=new SearchTable((Frame)getParent(),"Select Attribute",true,connection);
        Settings.Center(ptype);
        ptype.type="Attribute";
        ptype.item=cmbAttribute;
        ptype.owner=this;
        ptype.setVisible(true);
    }
    
    private void rename_ActionPerformed(ActionEvent e){   
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);  
        SearchTable ptype=new SearchTable((Frame)getParent(),"Select Attribute",true,connection);
        Settings.Center(ptype);
        ptype.type="Attribute";
        ptype.item=cmbAttributeRenamed;
        ptype.owner=this;
        ptype.setVisible(true);
    }

    private void txtName_keyReleased(KeyEvent e){
        if(id==-1)
            txtDescription.setText(txtName.getText());
    }
    
    private void txtDefault_actionPerformed(ActionEvent e){
    }

    private void txtDefault_keyPressed(KeyEvent e){
        btnApply.setEnabled(true);
        btnSave.setEnabled(true);
    }
    
    private void jRadioButton1_actionPerformed(ActionEvent e){
        jRadioButton1.setSelected(true);
        jRadioButton2.setSelected(false);
        if ( inherit != -1){
          inherit = -1;
          adp.EnableAll();
        }
    }

        private void jRadioButton2_actionPerformed(ActionEvent e){
            jRadioButton2.setSelected(true);
            jRadioButton1.setSelected(false);
            
            if ( inherit != 1){
                inherit = 1;
                adp.Inherit();
                adp.DisableAll();
                adp.revalidate();
                adp.repaint();
            }
        }


    private void expButton_actionPerformed(ActionEvent e) {
        if( id == -1 ){
            JOptionPane.showMessageDialog(null, "<html><center>Save Attribute First!", "Error", JOptionPane.ERROR_MESSAGE);    
        }
        else if( checkSyntax2(txtExpression,true,connection,1,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2).check ){
            CheckConstraintExpressionEditor a= new CheckConstraintExpressionEditor((Frame) pmy,"Function",true,connection,/*tree,*/id,1,txtExpression,txtName.getText(),"",String.valueOf(tree.ID));
            a.setVisible(true);
        }    
    }

    private void chkButton_actionPerformed(ActionEvent e) {
        //checkSyntax(true);
         checkSyntax2(txtExpression,true,connection,1,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2);
    }

    private void txtExpression_keyReleased(KeyEvent e) {
        //System.out.println("aaaaaaaaaaaaaaa::" + e.getKeyCode());
        if( e.getKeyCode() == 16 || e.getKeyCode() == 17 || e.getKeyCode() == 18 || e.getKeyCode() == 37 || 
            e.getKeyCode() == 38 || e.getKeyCode() == 39 || e.getKeyCode() == 40 || e.getKeyCode() == 33 ||
            e.getKeyCode() == 34 || e.getKeyCode() == 35 || e.getKeyCode() == 36 || /*e.getKeyCode() == 127 || */
            e.getKeyCode() == 155 || e.getKeyCode() == 10 )
            return;
        
        //checkSyntax(false);
         checkSyntax2(txtExpression,false,connection,1,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2);
    }

     /*public boolean checkSyntax(boolean ch){
         try {
             int caretPosition = txtExpression.getCaretPosition();
             
             if( ch)
                 btnApply.setEnabled(true);
             if(txtExpression.getText().trim().compareTo("") == 0)
                 return true;
             
             String output = "";
             ByteArrayInputStream byte_in = new ByteArrayInputStream (txtExpression.getText().toUpperCase().getBytes());
             InputStream is = byte_in;                
    
             ANTLRInputStream input = new ANTLRInputStream(is);
             // Create an ExprLexer that feeds from that stream
             CheckConstraintExpressionLexer lexer = new CheckConstraintExpressionLexer(input);
             // Create a stream of tokens fed by the lexer
             CommonTokenStream tokens = new CommonTokenStream(lexer);
             // Create a parser that feeds off the token stream
             //Object aaa2[] = tokens.getTokens().toArray();
             CheckConstraintExpressionParser parser = new CheckConstraintExpressionParser(tokens);
             parser.rootNodeName = txtName.getText().toUpperCase();
             parser.typeOfParser = 1;
             parser.con = connection;

             // Begin parsing at rule prog
             
             Object aaa2[] = tokens.getTokens().toArray();
    
             StyleContext context = new StyleContext();
             StyledDocument document = new DefaultStyledDocument(context);
             Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
              
            if(ch){
                output = parser.project();
                
                //jScrollPaneErr.setVisible(false);
                model2.removeAllElements();
                //System.out.println("errore:" +parser.getMyError() + ":" + parser.getMyError().split("#!#E#!#").length);
                if(parser.getMyError().length() > 7){
                    for(int h=0; h < parser.getMyError().split("#!#E#!#").length && ch; h++){
                        JLabel tmpl = new JLabel(parser.getMyError().split("#!#E#!#")[h]);
                        tmpl.setBorder(BorderFactory.createLineBorder(new Color(243,243,243), 1));    
                        model2.addElement(tmpl);
                    }
                }
                else if(ch){
                    JLabel tmpl = new JLabel("Succes");
                    tmpl.setBorder(BorderFactory.createLineBorder(new Color(243,243,243), 1));                 
                    model2.addElement(tmpl);
                }
                
                
                //System.out.println("ERR:::" + parser.getMyError() );
                if( parser.getMyError().length() > 7 && ch){
                    //jScrollPaneErr.setVisible(true);
                    btnApply.setEnabled(false);
                    return false;
                }
            }
             
             document.insertString(0, txtExpression.getText(), null);
             String line = txtExpression.getText();
             for(int s=0;s<tokens.getTokens().toArray().length; s++){
                 String text = "";
                 int f =  Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]);
                 int l =  Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1] ) + 1;
                 
                 text = ("'" + line.substring(f,l) + "'").toUpperCase() ;
                 
                 //System.out.println(text);
                 if( text.compareTo("'AND'") == 0 ||
                     text.compareTo("'OR'") == 0 ||
                     text.compareTo("'XOR'") == 0 ||
                     text.compareTo("'<=>'") == 0 ||
                     text.compareTo("'=>'") == 0){
                     
                     SimpleAttributeSet attributes = new SimpleAttributeSet();
                     StyleConstants.setItalic(attributes, true);
                     StyleConstants.setForeground(attributes, Color.red);                
                     document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                     Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                 }
                 else if( text.compareTo("'TRUE'") == 0 ||
                     text.compareTo("'FALSE'") == 0 ||
                     text.compareTo("'NULL'") == 0 ){
                     SimpleAttributeSet attributes = new SimpleAttributeSet();
                     StyleConstants.setBold(attributes, true);
                     StyleConstants.setForeground(attributes, Color.BLUE);                
                     document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                     Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                 }
                 else if( text.compareTo("'VALUE'") == 0 ||
                     text.compareTo("'THIS'") == 0 ){
                     SimpleAttributeSet attributes = new SimpleAttributeSet();
                     StyleConstants.setBold(attributes, true);
                     StyleConstants.setForeground(attributes, new Color(0,214,0));                
                     document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                     Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                 }                
                 else if( text.compareTo("'IN'") == 0  ){
                     SimpleAttributeSet attributes = new SimpleAttributeSet();
                     StyleConstants.setBold(attributes, true);
                     StyleConstants.setItalic(attributes, true);
                     StyleConstants.setForeground(attributes, new Color(255,181,99));                
                     document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                     Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                 }  
                 else if( text.compareTo("'LIKE'") == 0  ){
                     SimpleAttributeSet attributes = new SimpleAttributeSet();
                     StyleConstants.setBold(attributes, true);
                     StyleConstants.setItalic(attributes, true);
                     StyleConstants.setForeground(attributes, new Color(148,0,148));                
                     document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                     Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                 }  
                 else if(text.length() > 3 &&
                         text.substring(0,2).compareTo("''") == 0 && 
                         text.substring(aaa2[s].toString().split(",")[1].split("=")[1].length() - 2,aaa2[s].toString().split(",")[1].split("=")[1].length()).compareTo("''") == 0 ){

                     SimpleAttributeSet attributes = new SimpleAttributeSet();
                     StyleConstants.setUnderline(attributes, true);
                     StyleConstants.setForeground(attributes, new Color(159,159,159));
                     document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                     Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);                    

                 }                  
             }
             txtExpression.setDocument(document);
             //System.out.println("pos:" + caretPosition);
             txtExpression.setCaretPosition(caretPosition);
             
             //System.out.println("XML::::::::\n\n"+output+"\n\nXML::::::::\n");                
         } 
         catch (Exception ex) {
             ex.printStackTrace();
         }  
         return true;
     }
*/

    private void txtExpression_focusGained(FocusEvent e) {
        //checkSyntax(false);
        checkSyntax2(txtExpression,false,connection,1,String.valueOf(tree.ID),"",String.valueOf(id),txtName.getText(),model2);
    }

    private void txtExpression_mouseClicked(MouseEvent e) {
        if(ddList != null)
            ddList.setVisible(false);
    }















































    public static CCEValue checkSyntax2(JTextPane expression,boolean chkSyntax,Connection con,int type,String TREE_ID,
                                        String ID2,String ID,String nodeName,DefaultListModel errModelList){
        String output = "<block name=\"EXPRESSION\" group=\"0\" >\n</block>\n\n\n\n";                                        
        String output2 = "<block name=\"EXPRESSION\" group=\"0\" >\n</block>\n\n\n\n";                                        
        try {
            int caretPosition = expression.getCaretPosition();
            expression.setText(expression.getText().replaceAll("\r",""));
                            
            if(expression.getText().trim().compareTo("") == 0)
                return new CCEValue(true,output2);
            
            //String output = outputt;
            ByteArrayInputStream byte_in = new ByteArrayInputStream (expression.getText()/*.toUpperCase()*/.getBytes());
            InputStream is = byte_in;                
    
            ANTLRInputStreamCaseIn input = new ANTLRInputStreamCaseIn(is);
            // Create an ExprLexer that feeds from that stream
            CheckConstraintExpressionLexer lexer = new CheckConstraintExpressionLexer(input);
            // Create a stream of tokens fed by the lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // Create a parser that feeds off the token stream
            CheckConstraintExpressionParser parser = new CheckConstraintExpressionParser(tokens);
            
            parser.rootNodeName = nodeName/*.toUpperCase()*/.trim();
            parser.con = con;
            parser.typeOfParser = type;
            parser.treeID = TREE_ID;
            parser.ID_2 = ID2;
            parser.ID = ID;

            /*for(int s=0;s<aaa2.length; s++)
                System.out.println(s+" - " + aaa2[s].toString().split(",")[1].split("'")[1]);*/
            // Begin parsing at rule prog
            
            Object aaa2[] = tokens.getTokens().toArray();
    
            StyleContext context = new StyleContext();
            StyledDocument document = new DefaultStyledDocument(context);
            Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
             
            if(chkSyntax){
                output = parser.project();
                
                errModelList.removeAllElements();
                //System.out.println("errore:" +parser.getMyError() + ":" + parser.getMyError().split("#!#E#!#").length);
                if(parser.getMyError().length() > 7){
                    for(int h=0; h < parser.getMyError().split("#!#E#!#").length; h++){
                        JLabel tmpl = new JLabel(parser.getMyError().split("#!#E#!#")[h]);
                        tmpl.setBorder(BorderFactory.createLineBorder(new Color(243,243,243), 1));    
                        errModelList.addElement(tmpl);
                    }
                }
                else{
                    JLabel tmpl = new JLabel("Succes");
                    tmpl.setBorder(BorderFactory.createLineBorder(new Color(243,243,243), 1));                 
                    errModelList.addElement(tmpl);
                }
                //System.out.println("ERR:::" + parser.getMyError() );
                if( parser.getMyError().length() > 7 ){
                    return new CCEValue(false,output);
                }
            }
            
            document.insertString(0, expression.getText(), null);
            String line = expression.getText();
            for(int s=0;s<tokens.getTokens().toArray().length; s++){
                String text = "";
                int f =  Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]);
                int l =  Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1] ) + 1;
                
                text = ("'" + line.substring(f,l) + "'").toUpperCase() ;
                
                //System.out.println(text);
                if( text.compareTo("'AND'") == 0 ||
                    text.compareTo("'OR'") == 0 ||
                    text.compareTo("'XOR'") == 0 ||
                    text.compareTo("'<=>'") == 0 ||
                    text.compareTo("'=>'") == 0){
                    
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setItalic(attributes, true);
                    StyleConstants.setForeground(attributes, Color.red);                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }
                else if( text.compareTo("'TRUE'") == 0 ||
                    text.compareTo("'FALSE'") == 0 ||
                    text.compareTo("'NULL'") == 0 ){
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setBold(attributes, true);
                    StyleConstants.setForeground(attributes, Color.BLUE);                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }
                else if( text.compareTo("'VALUE'") == 0 ||
                    text.compareTo("'THIS'") == 0 ){
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setBold(attributes, true);
                    StyleConstants.setForeground(attributes, new Color(0,214,0));                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }                
                else if( text.compareTo("'IN'") == 0  ){
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setBold(attributes, true);
                    StyleConstants.setItalic(attributes, true);
                    StyleConstants.setForeground(attributes, new Color(255,181,99));                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }  
                else if( text.compareTo("'LIKE'") == 0  ){
                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setBold(attributes, true);
                    StyleConstants.setItalic(attributes, true);
                    StyleConstants.setForeground(attributes, new Color(148,0,148));                
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);
                }  
                else if(text.length() > 3 &&
                        text.substring(0,2).compareTo("''") == 0 && 
                        text.substring(aaa2[s].toString().split(",")[1].split("=")[1].length() - 2,aaa2[s].toString().split(",")[1].split("=")[1].length()).compareTo("''") == 0 ){

                    SimpleAttributeSet attributes = new SimpleAttributeSet();
                    StyleConstants.setUnderline(attributes, true);
                    StyleConstants.setForeground(attributes, new Color(159,159,159));
                    document.setCharacterAttributes(Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]),
                    Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[1]) - Integer.parseInt(aaa2[s].toString().split(",")[1].split("=")[0].split(":")[0]) + 1, attributes, false);                    

                }     
                //System.out.println(output);
            }
            expression.setDocument(document);
            //System.out.println("pos:" + caretPosition);
            expression.setCaretPosition(caretPosition);
            
            //System.out.println("XML::::::::\n\n"+output+"\n\nXML::::::::\n");                
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }  
        return new CCEValue(true,output); 
    }

    private void txtExpression_keyPressed(KeyEvent e) {
        if( e.getKeyCode() == 113){
        
            //if(ddList != null)
            //    this.remove(ddList);
            //jScrollPane1.setVisible(true);
            //jList1.requestFocus();
            
            int x = (int)jScrollPane2.getBounds().getX();
            int y = (int)jScrollPane2.getLocation().getY() + (int)jScrollPane2.getSize().getHeight();
            
            //System.out.println((int)jScrollPane2.getBounds().getX());
            //System.out.println((int)jScrollPane2.getBounds().getY());

            //if(x + 200 > txtExpression.getWidth())
            //    x = txtExpression.getWidth() - 200 - 2 ;
    
            //if(y < txtExpression.getHeight() / 2)
            //    y = y - 80 - 15 ;
            //y += (int)jScrollPane2.getLocation().getY();

            ddList.setData(null);
            ddList.setBounds( x , y + 5, jScrollPane2.getWidth() , 110 );
            ddList.setVisible(true);
            ddList.requestFocus();
        }       
    }
}
