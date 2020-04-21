package iisc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class LOV extends JDialog {
    private JPanel jPanel1 = new JPanel();
    public int Att_id;
    private int Tob_id;
    private int PR_id;
    private int AS_id;
    private int Tf_id;
    private int LV_Tf_id;
    private int LV_id=-1;
    private Connection conn;
    private IISFrameMain parent;
    private JComboBox tfCombo;
    private JComboBox attCmb;
    private JComboBox rAttCmb;
    private JLabel attLb;
    private JLabel rAttLb;
    private JLabel lb;
    private JLabel editLb;
    private JLabel searchLb;
    private JLabel mandatoryLb;
    private JCheckBox editChb;
    private JCheckBox searchChb;
    private JCheckBox mandatoryChb;
    private JPanel borderPn;
    private Vector tfids = new Vector();
    private Vector attids = new Vector();
    private Vector att = new Vector();
    private Vector retattids = new Vector();
    private JTextField regTxt;
    private JLabel regLb;
    private AttPairTableModel datm;
    private JTable table;
    private JScrollPane tableScroll;
    private JButton addBtn;
    private JButton delBtn;
    private Vector appSys = new Vector();
    private ImageIcon imageUp = new ImageIcon(IISFrameMain.class.getResource("icons/up.gif"));
    private ImageIcon imageDown = new ImageIcon(IISFrameMain.class.getResource("icons/down.gif"));
    private ImageIcon imageHelp = new ImageIcon(IISFrameMain.class.getResource("icons/help1.gif"));
    private ImageIcon iconPrevv = new ImageIcon(IISFrameMain.class.getResource("icons/prevv.gif"));
    private ImageIcon iconPrev = new ImageIcon(IISFrameMain.class.getResource("icons/prev.gif"));
    private ImageIcon iconNext = new ImageIcon(IISFrameMain.class.getResource("icons/next.gif"));
    private ImageIcon iconNextt = new ImageIcon(IISFrameMain.class.getResource("icons/nextt.gif"));
    private JPanel filterPn;
    private JRadioButton ovl = new JRadioButton();
    private JRadioButton dvl = new JRadioButton();
    private JLabel ovlLbl = new JLabel("Only via LOV");
    private JLabel dvlLbl = new JLabel("Directly & via LOV");
    private String Att_mnem;
    private JButton btnHelp = new JButton();
    public JButton btnClose = new JButton();
    public JButton btnApply = new JButton();
    public JButton btnSave = new JButton();
    public JButton btnNew = new JButton();
    public JButton btnDelete = new JButton();
    public JButton btnGenerate = new JButton();
    private JButton btnFirst = new JButton();
    private JButton btnPrev = new JButton();
    private JComboBox cmbLOV = new JComboBox();
    private JButton btnNext = new JButton();
    private JButton btnLast = new JButton();
    private String[][] lov=new String[1][3];

    public LOV() {
        this(-1,-1,-1,null,null,"", false);
    }

    public LOV(int _Tf_id, int _PR_id, int _AS_id, Connection _Conn, IISFrameMain _Parent,String title, boolean modal) {
        super((Frame) _Parent, title, modal);
        try {
            PR_id = _PR_id;
            AS_id = _AS_id;
            Tf_id = _Tf_id;
            parent = _Parent;
            conn=_Conn;
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(484, 503));
        this.getContentPane().setLayout( null );
        this.setTitle("Lists of Values");
        jPanel1.setBounds(new Rectangle(10, 50, 455, 325));
        this.getContentPane().add(btnLast, null);
        this.getContentPane().add(btnNext, null);
        this.getContentPane().add(cmbLOV, null);
        this.getContentPane().add(btnPrev, null);
        this.getContentPane().add(btnFirst, null);
        this.getContentPane().add(btnGenerate, null);
        this.getContentPane().add(btnDelete, null);
        this.getContentPane().add(btnNew, null);
        this.getContentPane().add(btnSave, null);
        this.getContentPane().add(btnApply, null);
        this.getContentPane().add(btnClose, null);
        this.getContentPane().add(btnHelp, null);
        this.getContentPane().add(jPanel1, null);
        jPanel1.setLayout(null);
        lb = new JLabel("LOV Form Type:");
        lb.setSize(100, 20);
        lb.setBounds(10,10,100, 20);
        lb.setFont(new Font("SansSerif", 0, 11));

        //searchBtn.setSize(15,50);

        borderPn = new JPanel();
        borderPn.setLayout(null);
        borderPn.setBorder(new LineBorder(new Color(124, 124, 124), 1));
        borderPn.setBounds(new Rectangle(10, 40, 220, 75));
        
        ovl.setBounds(25, 25, 20, 20);
        ovlLbl.setBounds(50, 25, 150, 20);
        dvl.setBounds(25, 45, 20, 20);
        ovl.setSelected(true);
        dvlLbl.setBounds(50, 45, 150, 20);
        dvl.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
              EnableButtons();
              if (dvl.isSelected())
              {
                  ovl.setSelected(false);
                  mandatoryChb.setEnabled(true);
                  searchChb.setEnabled(true);
              }
              else
              {
                  ovl.setSelected(true);
                  mandatoryChb.setEnabled(false);
                  searchChb.setEnabled(false);
              }
          }
        });

        btnHelp.setBounds(new Rectangle(430, 440, 35, 30));
        btnHelp.setIcon(imageHelp);
        btnHelp.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnHelp_actionPerformed(e);
          }
        });
        btnClose.setMaximumSize(new Dimension(50, 30));
        btnClose.setPreferredSize(new Dimension(50, 30));
        btnClose.setText("Close");
        btnClose.setBounds(new Rectangle(345, 440, 80, 30));
        btnClose.setMinimumSize(new Dimension(50, 30));
        btnClose.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent ae)
          {
            close_ActionPerformed(ae);
          }
        });
        btnApply.setMaximumSize(new Dimension(50, 30));
        btnApply.setPreferredSize(new Dimension(50, 30));
        btnApply.setText("Apply");
        btnApply.setBounds(new Rectangle(20, 440, 75, 30));
        btnApply.setMinimumSize(new Dimension(50, 30));
        btnApply.setEnabled(false);
        btnApply.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent ae)
          {
                        apply_ActionPerformed(ae);
                    }
        });
        btnSave.setMaximumSize(new Dimension(50, 30));
        btnSave.setPreferredSize(new Dimension(50, 30));
        btnSave.setText("OK");
        btnSave.setBounds(new Rectangle(265, 440, 75, 30));
        btnSave.setMinimumSize(new Dimension(50, 30));
        btnSave.setEnabled(false);
        btnSave.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent ae)
          {
            save_ActionPerformed(ae);
          }
        });
        editLb = new JLabel("Edit Attribute Value");
        editLb.setBounds(5, 0, 190, 20);
        editLb.setFont(new Font("SansSerif", 0, 11));
        borderPn.add(ovl);
        borderPn.add(ovlLbl);
        borderPn.add(dvl);
        borderPn.add(dvlLbl);
        borderPn.add(editLb);
        filterPn = new JPanel();
        filterPn.setLayout(null);
        filterPn.setBorder(new LineBorder(new Color(124, 124, 124), 1));
        filterPn.setBounds(new Rectangle(225, 40, 225, 75));
        filterPn.setOpaque(false);

        

        editChb = new JCheckBox("");
        editChb.setSize(20, 20);
        editChb.setBounds(60, 5, 20, 20);
        editChb.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        editChb_itemStateChanged(e);
                    }
                });
        editChb.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        EnableButtons();
                    }
                });
        //borderPn.add(editChb);

        searchLb = new JLabel("Filter LOV by value");
        searchLb.setBounds(35, 10, 125, 20);
        searchLb.setFont(new Font("SansSerif", 0, 11));

        searchChb = new JCheckBox("");
        searchChb.setSize(20, 20);
        searchChb.setBounds(10, 10, 20, 20);
        searchChb.setEnabled(false);
        searchChb.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        EnableButtons();
                    }
                });

        mandatoryLb = new JLabel("Validate value by LOV");
        mandatoryLb.setBounds(35, 40, 125, 20);
        mandatoryLb.setFont(new Font("SansSerif", 0, 11));

        mandatoryChb = new JCheckBox("");
        mandatoryChb.setSize(20, 20);
        mandatoryChb.setBounds(10, 40, 20, 20);
        mandatoryChb.setEnabled(false);
        mandatoryChb.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        EnableButtons();
                    }
                });

        filterPn.add(searchLb);

        filterPn.add(searchChb);
        filterPn.add(mandatoryLb);
        filterPn.add(mandatoryChb);
        regLb = new JLabel("Restrict expression:");
        regLb.setBounds(10, 125, 120, 20);
        regLb.setFont(new Font("SansSerif", 0, 11));

        regTxt = new JTextField("");
        regTxt.setBounds(120, 125, 255, 20);
        regTxt.setBounds(new Rectangle(120, 125, 330, 20));
        regTxt.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        EnableButtons();
                    }
                });

        tfCombo = new JComboBox();
        tfCombo.setBounds(115, 10, 225, 20);
        tfCombo.setEditable(false);
        tfCombo.setBounds(new Rectangle(115, 10, 335, 20));

        attLb = new JLabel("Lov attribute");
        attLb.setBounds(10, 155, 180, 15);
        attLb.setFont(new Font("SansSerif", 0, 11));

        attCmb = new JComboBox();
        attCmb.setBounds(10, 170, 180, 20);
        attCmb.setFont(new Font("SansSerif", 0, 11));

        rAttLb = new JLabel("Return attribute");
        rAttLb.setBounds(195, 155, 180, 15);
        rAttLb.setFont(new Font("SansSerif", 0, 11));
        rAttLb.setBounds(new Rectangle(270, 155, 180, 15));

        rAttCmb = new JComboBox();
        rAttCmb.setBounds(195, 170, 180, 20);
        rAttCmb.setFont(new Font("SansSerif", 0, 11));
        rAttCmb.setBounds(new Rectangle(270, 170, 180, 20));

        addBtn = new JButton();
        addBtn.setText("");
        //searchBtn.setSize(15,50);
        addBtn.setFont(new Font("SansSerif", 0, 10));
        addBtn.setBounds(112, 195, 25, 25);
        addBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        add_ActionPerformed(ae);
                    }
                });
        addBtn.setIcon(imageDown);

        addBtn.setBounds(new Rectangle(80, 195, 25, 25));
        delBtn = new JButton();
        delBtn.setText("");
        //searchBtn.setSize(15,50);
        delBtn.setFont(new Font("SansSerif", 0, 11));
        delBtn.setIcon(imageUp);
        delBtn.setBounds(new Rectangle(340, 195, 25, 25));
        delBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        del_ActionPerformed(ae);
                    }
                });
        datm = new AttPairTableModel();
        table = new JTable(datm);
        tableScroll = new JScrollPane();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setGridColor(new Color(0, 0, 0));
        table.setBackground(Color.white);
        //table.setAutoResizeMode(0);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoscrolls(true);
        table.getTableHeader().setResizingAllowed(true);
        //rowSMatt = table.getSelectionModel();
        table.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //table.setPreferredSize(new Dimension(300, 20));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));

        tableScroll.setBounds(new Rectangle(10, 225, 440, 90));

        TableColumn tc = table.getColumnModel().getColumn(0);
        //System.out.println("sirina" + tc.getPreferredWidth());
        //tc.setWidth(20);
        tc.setWidth(20);
        tc.setPreferredWidth(20);

        btnFirst.setMaximumSize(new Dimension(60, 60));
        btnFirst.setPreferredSize(new Dimension(25, 20));
        btnFirst.setBounds(new Rectangle(25, 10, 25, 20));
        btnFirst.setIcon(iconPrevv);
        btnFirst.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent e)
        {
          btnFirst_actionPerformed(e);
        }
        });
        btnPrev.setMaximumSize(new Dimension(60, 60));
        btnPrev.setPreferredSize(new Dimension(25, 20));
        btnPrev.setBounds(new Rectangle(55, 10, 25, 20));
        btnPrev.setIcon(iconPrev);
        btnPrev.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent e)
        {
          btnPrev_actionPerformed(e);
        }
        });
        cmbLOV.setFont(new Font("SansSerif", 0, 11));
        cmbLOV.setBounds(new Rectangle(85, 10, 300, 20));
        cmbLOV.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent e)
        {
                        cmbFun_actionPerformed(e);
                    }
        });
        btnNext.setMaximumSize(new Dimension(60, 60));
        btnNext.setPreferredSize(new Dimension(25, 20));
        btnNext.setBounds(new Rectangle(390, 10, 25, 20));
        btnNext.setIcon(iconNext);
        btnNext.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent e)
        {
          btnNext_actionPerformed(e);
        }
        });
        btnLast.setMaximumSize(new Dimension(60, 60));
        btnLast.setPreferredSize(new Dimension(25, 20));
        btnLast.setBounds(new Rectangle(425, 10, 25, 20));
        btnLast.setIcon(iconNextt);
        btnLast.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent e)
        {
          btnLast_actionPerformed(e);
        }
        });
        btnGenerate.setMaximumSize(new Dimension(50, 30));
        btnGenerate.setPreferredSize(new Dimension(50, 30));
        btnGenerate.setText("Generate default LOVs");
        btnGenerate.setBounds(new Rectangle(20, 385, 160, 30));
        btnGenerate.setMinimumSize(new Dimension(50, 30));
        btnGenerate.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnGenerate_actionPerformed(e);
                    }
                });
        btnDelete.setMaximumSize(new Dimension(50, 30));
        btnDelete.setPreferredSize(new Dimension(50, 30));
        btnDelete.setText("Delete");
        btnDelete.setBounds(new Rectangle(185, 440, 75, 30));
        btnDelete.setMinimumSize(new Dimension(50, 30));
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnDelete_actionPerformed(e);
                    }
                });
        btnNew.setMaximumSize(new Dimension(50, 30));
        btnNew.setPreferredSize(new Dimension(50, 30));
        btnNew.setText("New");
        btnNew.setBounds(new Rectangle(100, 440, 80, 30));
        btnNew.setMinimumSize(new Dimension(50, 30));


        btnNew.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnNew_actionPerformed(e);
                    }
                });
        jPanel1.add(lb);
        jPanel1.add(borderPn);
        jPanel1.add(regLb);
        jPanel1.add(regTxt);
        jPanel1.add(tfCombo);
        jPanel1.add(attLb);
        jPanel1.add(attCmb);
        jPanel1.add(rAttLb);
        jPanel1.add(rAttCmb);
        jPanel1.add(addBtn);
        jPanel1.add(delBtn);
        tableScroll.getViewport().add(table, null);
        jPanel1.add(tableScroll, null);

        jPanel1.add(filterPn, null);
        setLOVs(-1);
        InitAttributesCombo();
        InitTable();
        InitCombo(-1);
        InitAttCombo();
        InitRetAttCombo();
        if(hasDefaultLOVs())btnGenerate.setEnabled(false);
        tfCombo.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            tfCombo_actionPerformed(e);
          }
        });

        tfCombo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tfCombo_actionPerformed(e);
                    }
                });
        attCmb.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            attCmb_actionPerformed(e);
          }
        });
        
        /*tfCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              tfCombo_actionPerformed(e);
            }
        });
        */
        if (LV_id == 0)
        {
            DisableAll();
        }
        
        ovl.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
              EnableButtons();
              
              if (ovl.isSelected())
              {
                  dvl.setSelected(false);
                  mandatoryChb.setEnabled(false);
                  searchChb.setEnabled(false);
              }
              else
              {
                  dvl.setSelected(true);
                  mandatoryChb.setEnabled(true);
                  searchChb.setEnabled(true);
              }
          }
        });
        
        if ( ( rAttCmb.getItemCount() == 0 ) || ( attCmb.getItemCount() == 0) )
        {
            addBtn.setEnabled(false);
        }
        
        if ( datm.data.size() == 0 )
        {
            delBtn.setEnabled(false);
        }
        repaint();
        }
        private int getLOVs(int k){
            if(k==-1)return 0;
            if(lov[k][0]==null)return 0;
            if(lov[k][0]=="-1")return 0;
            return (new Integer(lov[k][0])).intValue();
        }
        private void setLOVs(int k){
            try
            {
                lov[0][0]="-1";
                lov[0][1]="-1";
                lov[0][2]="";
                if(cmbLOV.getItemCount()>0)
                cmbLOV.removeAllItems();
                cmbLOV.addItem("New");
                Statement statement2 = conn.createStatement();
                ResultSet rs2 = statement2.executeQuery("select count(*) from (select distinct(LV_id) from IISC_ATT_TOB where Tf_id=" + Tf_id+" and not LV_id is null)" );
                if (rs2.next())
                {
                    int j=rs2.getInt(1)+1;
                    lov=new String[j][3];
                }
                rs2.close();
                rs2 = statement2.executeQuery("select distinct(IISC_ATT_TOB.LV_id), IISC_LIST_OF_VALUES.TF_id from IISC_ATT_TOB, IISC_LIST_OF_VALUES where IISC_ATT_TOB.LV_id=IISC_LIST_OF_VALUES.LV_id and IISC_ATT_TOB.Tf_id=" + Tf_id);
                int i=1;
                int j=0;
                int tf=0;
                while (rs2.next())
                {
                    int lv=rs2.getInt(1);
                    lov[i][0]=""+lv;
                    tf=rs2.getInt(2);
                    lov[i][1]="";
                    lov[i][2]="";
                    Statement statement1 = conn.createStatement();
                    int at=0;
                    ResultSet rs1 = statement1.executeQuery("select IISC_FORM_TYPE.Tf_id, IISC_FORM_TYPE.Tf_mnem from IISC_LIST_OF_VALUES, IISC_FORM_TYPE where IISC_LIST_OF_VALUES.TF_id=IISC_FORM_TYPE.TF_id and IISC_LIST_OF_VALUES.LV_id=" + lv);                    
                    while (rs1.next())
                    {
                        lov[i][1]=rs1.getString(2);
                    }
                    rs1.close();
                    rs1 = statement1.executeQuery("select IISC_ATTRIBUTE.Att_id, IISC_ATTRIBUTE.Att_mnem from IISC_LV_return, IISC_ATTRIBUTE  where IISC_LV_return.LV_return_att_id=IISC_ATTRIBUTE.Att_id and IISC_LV_return.LV_id=" + lov[i][0]);
                    while (rs1.next())
                    {
                        lov[i][2]=lov[i][2]+rs1.getString(2)+", ";
                    }
                    rs1.close();
                    if(lov[i][2].length()>2)lov[i][2]=lov[i][2].substring(0,lov[i][2].length()-2);
                    cmbLOV.addItem(""+lov[i][1]+" ("+lov[i][2]+")");
                    if(k==lv){
                        j=i;
                        LV_Tf_id=tf;
                        LV_id=lv;
                    }
                    i++;
                }
                if(k==0){
                    LV_Tf_id=-1;
                    LV_id=-1;
                    tf=-1;
                }
                cmbLOV.setSelectedIndex(j);
                rs2.close();
                InitCombo(LV_Tf_id);
                InitTable();
                InitAttCombo();
                InitRetAttCombo();
                getLV_id();
                DisableButtons();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }             
        }
        private void getLV_id()
        {
            try
            {
                    Statement statement = conn.createStatement();
                    ResultSet rs = statement.executeQuery("select * from IISC_LIST_OF_VALUES where LV_id=" + LV_id);
                    int checked;
                    
                    if (rs.next())
                    {
                        LV_Tf_id = rs.getInt("Tf_id");
                        checked = rs.getInt("LV_type");
                        
                        if (checked == 1)
                        {
                            dvl.setSelected(true);
                        }
                        else
                        {
                            ovl.setSelected(true);
                        }
                        
                        checked = rs.getInt("LV_search");
                        
                        if (checked == 1)
                        {
                            searchChb.setSelected(true);
                        }
                        
                        checked = rs.getInt("LV_mand_validation");
                        
                        if (checked == 1)
                        {
                            mandatoryChb.setSelected(true);
                        }
                        
                        regTxt.setText(rs.getString("LV_regular_expression"));                    
                    }
                    else
                    {
                        LV_Tf_id = 0;
                        DisableAll();
                    }
                    rs.close();             
                
            }
            catch(SQLException e)
            {
                LV_id = 0;
                DisableAll();
            } 
            EnableButtons();
        }
        /* Inicijalizacija kombo boksa koji sadrzi tipove formi */
        private void InitCombo(int k)
        {
        try
        {
            Statement statement = conn.createStatement();
            Statement statement2 = conn.createStatement();
            Statement statement3 = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from IISC_TF_APPSYS,IISC_FORM_TYPE,IISC_APP_SYSTEM where IISC_TF_APPSYS.AS_id=IISC_APP_SYSTEM.AS_id and  IISC_TF_APPSYS.AS_id="+ AS_id + " and IISC_TF_APPSYS.TF_id=IISC_FORM_TYPE.TF_id");
            
            ResultSet rs2;
            ResultSet rs3;
            tfCombo.removeAllItems();
            tfCombo.addItem("");
            
            if(LV_id == 0)
            {
                tfCombo.setSelectedIndex(0);
            }
            
            int i =1;
            int lTf_id;
            
            while(rs.next())
            {
                
                lTf_id = rs.getInt("Tf_id");
                
                if ( Tf_id == lTf_id)
                {
                    continue;
                }
                
               rs2 = statement2.executeQuery("select Tob_id from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tf_id=" + lTf_id + " order by Tob_superord");
                
                if( rs2.next())
                {
                    int lvTob_id = rs2.getInt(1);
                    rs3 = statement3.executeQuery("select Att_id from IISC_ATT_TOB where IISC_ATT_TOB.Tob_id=" + lvTob_id);
                    
                    if (rs3.next())
                    {
                        tfCombo.addItem(rs.getString("Tf_mnem"));
                        tfids.add(new Integer(lTf_id));
                        appSys.add(rs.getString("AS_name"));
                    
                        if (lTf_id == k)
                        {
                            tfCombo.setSelectedIndex(i);
                        }
                        i = i + 1;
                    }
                    rs3.close();
                }
                rs2.close();
                
                
            }
            statement.close();
            statement2.close();
            statement3.close();
        }
        catch(SQLException e)
        {
            System.out.println("\n" + e.toString() + "\n");
        }
        
        repaint();
        }
        private void InitAttributesCombo()
        {
        try
        {
            Statement statement2 = conn.createStatement();
            Statement statement3 = conn.createStatement();
            Statement statement4 = conn.createStatement();
            
            ResultSet rs2;
            ResultSet rs3;
            ResultSet rs4;
            int LV_Att_id;
            int LV_Tob_id;
            
            String LV_Att_mnem;
            
            rs4 = statement4.executeQuery("select Tob_id, Tob_name from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tf_id=" + Tf_id + " order by Tob_superord");
                
            while( rs4.next())
            {
                LV_Tob_id = rs4.getInt(1);
                String Tob_name=rs4.getString(2);
                
                rs2 = statement2.executeQuery("select Att_id from IISC_ATT_TOB where Tob_id=" + LV_Tob_id);
        
                while(rs2.next())
                {
                    LV_Att_id = rs2.getInt(1);
                    
                    rs3 = statement3.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + LV_Att_id);
                    
                    if (rs3.next())
                    {
                        LV_Att_mnem = rs3.getString(1);
                        
                        //cmbAttribute.addItem(LV_Att_mnem+" ("+Tob_name+")");
                        att.add(new Integer(LV_Att_id));
                    }
                    rs3.close();
                }
                rs2.close();
                
            }
        }   
        catch(SQLException e)
        {
            System.out.println("\n" + e.toString() + "\n");
        }
        
        }        
        private void InitAttCombo()
        {
        try
        {
            Statement statement2 = conn.createStatement();
            Statement statement3 = conn.createStatement();
            Statement statement4 = conn.createStatement();
            
            if(LV_Tf_id == 0)
            {
                return;
            }
            
            ResultSet rs2;
            ResultSet rs3;
            ResultSet rs4;
            int LV_Att_id;
            int LV_Tob_id;
            
            String LV_Att_mnem;
            attCmb.removeAllItems();
            rs4 = statement4.executeQuery("select Tob_id from IISC_COMPONENT_TYPE_OBJECT_TYPE where Tf_id=" + LV_Tf_id + " order by Tob_superord");
                
            if( rs4.next())
            {
                LV_Tob_id = rs4.getInt(1);
                
                rs2 = statement2.executeQuery("select Att_id from IISC_ATT_TOB where Tob_id=" + LV_Tob_id);
        
                while(rs2.next())
                {
                    LV_Att_id = rs2.getInt(1);
                    
                    rs3 = statement3.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + LV_Att_id);
                    
                    if (rs3.next())
                    {
                        LV_Att_mnem = rs3.getString(1);
                        
                        attCmb.addItem(LV_Att_mnem);
                        attids.add(new Integer(LV_Att_id));
                    }
                    rs3.close();
                }
                rs2.close();
                
            }
            
            if (attCmb.getItemCount() == 0)
            {
                addBtn.setEnabled(false);
            }
            else
            {
               attCmb.setSelectedIndex(0);
            }
        }   
        catch(SQLException e)
        {
            System.out.println("\n" + e.toString() + "\n");
        }
        
        }
        
        
        private void InitRetAttCombo()
        {
        try
        {
            Statement statement = conn.createStatement();
            Statement statement2 = conn.createStatement();
            Statement statement3 = conn.createStatement();
            Statement statement4 = conn.createStatement();
            
            ResultSet rs2;
            ResultSet rs3;
            ResultSet rs4;
            int LV_Att_id;
            String LV_Att_mnem;
            int exists;
            int i = 0;
            String selAtt = "";
            
            try
            {
                if ( attCmb.getSelectedIndex() != -1)
                {
                    selAtt = attCmb.getSelectedItem().toString();
                }
                else
                {
                    selAtt = attCmb.getItemAt(0).toString();
                }
            }
            catch(Exception ex)
            {
                
            }
            rAttCmb.removeAllItems();
            rs4 = statement4.executeQuery("select Dom_id from IISC_ATTRIBUTE where Att_mnem='" + selAtt + "' and PR_id=" + PR_id);
            
            if ( rs4.next())
            {
                int LV_Dom_id = rs4.getInt(1);
                rs2 = statement2.executeQuery("select Att_id from IISC_ATT_TOB where Tf_id=" + Tf_id);
        
                while(rs2.next())
                {
                    LV_Att_id = rs2.getInt(1);
                    
                    rs3 = statement3.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + LV_Att_id + " and Dom_id=" + LV_Dom_id);
                    
                    if (rs3.next())
                    {
                        LV_Att_mnem = rs3.getString(1);
                        
                        exists = 0;
                        for(i = 0; i < datm.data.size(); i++)
                        {
                            if (((AttPair)datm.data.get(i)).getRetAttId() == LV_Att_id)
                            {
                                exists = 1;
                                break;
                            }
                        }
                        
                        if (exists == 0)
                        {
                            rAttCmb.addItem(LV_Att_mnem);
                            retattids.add(new Integer(LV_Att_id));
                        }
                    }
                    rs3.close();
                }
                rs2.close();
                
                if (rAttCmb.getItemCount() == 0)
                {
                    addBtn.setEnabled(false);
                }
                else
                {
                    addBtn.setEnabled(true);
                }
            }
            statement4.close();
        }   
        catch(SQLException e)
        {
            System.out.println("\n" + e.toString() + "\n");
        }
        
        }
        /************************************************************************/
        /****                       Event hendleri                          *****/
        /************************************************************************/
        public void search_ActionPerformed(ActionEvent ae)
        {
        SearchTable stb = new SearchTable(parent,"Select Form Type", true,conn);
        stb.type = "Form Type";
        JComboBox temp = new JComboBox();
        temp.addItem("");
        temp.setSelectedIndex(0);
        
        for(int i = 0;i < appSys.size(); i++)
        {
            temp.addItem(tfCombo.getItemAt(i+1).toString().trim() + " (" + appSys.get(i)+")");
        }
        stb.item = temp;
        Settings.Center(stb);
        stb.setVisible(true);
        //System.out.println(temp.getSelectedItem().toString());
        if (temp.getSelectedIndex() > 0)
        {
            if (tfCombo.getSelectedIndex() != temp.getSelectedIndex())
            {
                tfCombo.setSelectedIndex(temp.getSelectedIndex());
            }
        }
        
        }
        
        public void add_ActionPerformed(ActionEvent ae)
        {
        int i1 = attCmb.getSelectedIndex();
        int i2 = rAttCmb.getSelectedIndex();
        
        if (i2 < 0)
        {
            return;
        }
        
        if (i1 < 0)
        {
            return;
        }
        
        EnableButtons();
        AttPair pair = new AttPair(((Integer)attids.get(i1)).intValue(),((Integer)retattids.get(i2)).intValue(), attCmb.getSelectedItem().toString(),rAttCmb.getSelectedItem().toString());
        datm.data.add(pair);
        
        rAttCmb.removeItemAt(i2);
        retattids.remove(i2);
        rAttCmb.repaint();
        datm.fireTableDataChanged();
        repaint();
        
        if (rAttCmb.getItemCount() == 0)
        {
            addBtn.setEnabled(false);
        }
        delBtn.setEnabled(true);
        EnableButtons();
        }
        
        public void del_ActionPerformed(ActionEvent ae)
        {
        int rowIndex = table.getSelectedRow();
        
        if ((rowIndex < 0) || (rowIndex >= table.getRowCount()))
        {
            return;
        }
        
        int action = JOptionPane.showConfirmDialog(this, "Confirm delete", "Display Attributes", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (action == JOptionPane.OK_OPTION)
        {   
            EnableButtons();
            AttPair pair = (AttPair)datm.data.get(rowIndex);
            datm.data.remove(rowIndex);
            rAttCmb.addItem(pair.getRetAttMnem());
            retattids.add(new Integer(pair.getRetAttId()));
            rAttCmb.repaint();
            datm.fireTableDataChanged();
            repaint();
        }  
        
        if (datm.data.size() == 0)
        {
            delBtn.setEnabled(false);
        }
        else
        {
            if (rowIndex == datm.data.size())
            {
                rowIndex = rowIndex - 1;
            }
            table.getSelectionModel().setSelectionInterval(rowIndex,rowIndex);
            
        }
        addBtn.setEnabled(true);
        EnableButtons();
        }
        
        private void editChb_itemStateChanged(ItemEvent e)
        {
        if (editChb.isSelected())
        {
            searchChb.setEnabled(true);
            mandatoryChb.setEnabled(true);
        }
        else
        {
            searchChb.setEnabled(false);
            mandatoryChb.setEnabled(false);
        }
        }
        
        private void EnableButtons()
        {
            if(tfCombo.getItemCount()>0)
            {
                if(!tfCombo.getSelectedItem().equals("") && table.getModel().getRowCount()>0)
                {
                    btnApply.setEnabled(true);
                    btnSave.setEnabled(true);
                    btnDelete.setEnabled(true);
                    btnNew.setEnabled(true);
                }
                else
                    DisableButtons();
            }
            else
                DisableButtons();
            if(hasDefaultLOVs())    
                btnGenerate.setEnabled(false);
            else 
                btnGenerate.setEnabled(true);
        }
        private void DisableButtons()
        {
            btnApply.setEnabled(false);
            btnSave.setEnabled(false);
            if(LV_id!=-1)
                btnDelete.setEnabled(true);
            else
                btnDelete.setEnabled(false);
            if(hasDefaultLOVs())    
                btnGenerate.setEnabled(false);
            else 
                btnGenerate.setEnabled(true);
        }       
        private void tfCombo_actionPerformed(ItemEvent e)
        {
        int i = tfCombo.getSelectedIndex();
        EnableButtons();
        
        if (i < 0)
        {
            LV_Tf_id = 0;
            return;
        }
        
        if (i == 0)
        {
            LV_Tf_id = 0;
            DisableAll();
            return;
        }
        
        EnableAll();
        LV_Tf_id = ((Integer)tfids.get(i - 1)).intValue();
        datm.data.clear();
        //AddDefaultRowToTable();
        
        attCmb.removeAllItems();
        attids.clear();
        InitAttCombo();
        
        }
        
        private void attCmb_actionPerformed(ItemEvent e)
        {
        
        retattids.clear();
        rAttCmb.removeAllItems();
        InitRetAttCombo();
        
        }
        
        private void AddDefaultRowToTable()
        {
        String Att_mnem = "";
        
        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id=" + Att_id);
            
            if (rs.next())
            {
                Att_mnem = rs.getString(1);
            }
        }
        catch(Exception ex)
        {
            
        }
            
        AttPair pair = new AttPair(Att_id, Att_id, Att_mnem, Att_mnem);
        datm.data.add(pair);
        datm.fireTableDataChanged();
        }
        
        public void DisableAll()
        {
        searchChb.setEnabled(false);
        ovl.setEnabled(false);
        dvl.setEnabled(false);
        mandatoryChb.setEnabled(false);
        searchChb.setSelected(false);
        editChb.setSelected(false);
        mandatoryChb.setSelected(false);
        regTxt.setText("");
        regTxt.setEnabled(false);
        attCmb.removeAllItems();
        attCmb.setEnabled(false);
        rAttCmb.removeAllItems();
        rAttCmb.setEnabled(false);
        addBtn.setEnabled(false);
        delBtn.setEnabled(false);
        table.setEnabled(false);
        datm.data.clear();
        datm.fireTableDataChanged();
        }
        
        public void EnableAll()
        {
        editChb.setEnabled(true);
        if (dvl.isSelected())
        {
            searchChb.setEnabled(true);
            mandatoryChb.setEnabled(true);
        }
        ovl.setEnabled(true);
        dvl.setEnabled(true);
        regTxt.setEnabled(true);
        attCmb.setEnabled(true);
        rAttCmb.setEnabled(true);
        addBtn.setEnabled(true);
        delBtn.setEnabled(true);
        table.setEnabled(true);
        }
        
        /************************************************************************/
        /****                    Inicijalizacija tabele                      ****/
        /************************************************************************/
        private void InitTable()
        {

            
        Vector data = new Vector();
        AttPair value;
        
        //AddDefaultRowToTable();
        
        if (LV_id != 0)
        {      
            try
            {
                Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                Statement statement2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = statement.executeQuery("select IISC_LV_RETURN.Att_id, IISC_LV_RETURN.LV_return_att_id from IISC_LV_RETURN, IISC_ATT_TOB where IISC_LV_RETURN.LV_id= IISC_ATT_TOB.LV_id and IISC_LV_RETURN.LV_id =" + LV_id);
                ResultSet rs2;
                int LV_Att_id;
                int LV_ret_Att_id;
                String Att_mnem = "";
                String ret_Att_mnem = "";
                
                while(rs.next())
                {
                    LV_Att_id = rs.getInt("Att_id");
                    LV_ret_Att_id = rs.getInt("LV_return_att_id");
                    
                    rs2 = statement2.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id =" + LV_Att_id);
                    
                    if (rs2.next())
                    {
                        Att_mnem = rs2.getString(1);
                    }
                    
                    rs2 = statement2.executeQuery("select Att_mnem from IISC_ATTRIBUTE where Att_id =" + LV_ret_Att_id);
                    
                    if (rs2.next())
                    {
                        ret_Att_mnem = rs2.getString(1);
                    }
                    rs2.close();
                    
                    if ( LV_Att_id == Att_id )
                    {
                        data.add(0, new AttPair(LV_Att_id, LV_ret_Att_id, Att_mnem, ret_Att_mnem));
                    }
                    else
                    {
                        data.add(new AttPair(LV_Att_id, LV_ret_Att_id, Att_mnem, ret_Att_mnem));
                    }
                }
                rs.close();
            }
            catch(SQLException sqle)
            {
                System.out.println("Sql exception :" + sqle);
            }
        }     
        datm.populateData(data);
        
        if (data.size() == 0)
        {
            delBtn.setEnabled(false);
        }
        datm.fireTableStructureChanged();
        table.repaint();
        
        }
        
        
        public void delete()
        {
            try
            {
                Statement statement = conn.createStatement();
                statement.execute("UPDATE IISC_ATT_TOB SET LV_id=null where LV_id=" + LV_id);
                statement.execute("delete from IISC_LIST_OF_VALUES where LV_id=" + LV_id);
                statement.execute("delete from IISC_LV_RETURN where LV_id=" + LV_id);
                statement.close();
                DisableButtons();
                JOptionPane.showMessageDialog(null, "<html><center>LOV deleted!", "Lists of Values", JOptionPane.INFORMATION_MESSAGE);
            }
            catch(SQLException sqle)
            {
                System.out.println("Sql exception :" + sqle);
            }
        }
        /*************************************************************************/
        /*********              Unos podataka u bazu                      ********/
        /*************************************************************************/
        public void Update()
        {
        try
        {
            DisableButtons();
            int index = tfCombo.getSelectedIndex();
            
            if (index == 0)
            {
                if (LV_id == 0)
                {
                    return;
                }
                
                Statement statement = conn.createStatement();
                statement.execute("update IISC_ATT_TOB set LV_ID=0 where Att_id=" + Att_id + " and Tf_id=" + Tf_id + " and Tob_id=" + Tob_id);
                statement.execute("delete from IISC_LIST_OF_VALUES where LV_id=" + LV_id);
                statement.execute("delete from IISC_LV_RETURN where LV_id=" + LV_id);
                statement.close();
                return;
            } 
            
            
            int i1 = 0;
            if (dvl.isSelected())
            {
                i1 = 1;
            }
            
            int i2 = 0;
            if (searchChb.isSelected())
            {
                i2 = 1;
            }
            
            int i3 = 0;
            if (mandatoryChb.isSelected())
            {
                i3 = 1;
            }
            
            if(LV_id == -1)
            {
                try
                {
                    Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = statement.executeQuery("select max(LV_id) from IISC_LV_RETURN");
                    if (rs.next())
                    {
                        LV_id = rs.getInt(1);
                        
                    }
                    else
                    {
                        LV_id = 0;
                    }
                    rs.close();
                    statement.close();
                }
                catch(SQLException sqle)
                {
                    LV_id = 0;
                }
                
                LV_id = LV_id + 1;
                
                Statement statement2 = conn.createStatement();
                statement2.execute("insert into IISC_LIST_OF_VALUES(LV_id,PR_id,Tf_id,LV_type,LV_search,LV_regular_expression,LV_mand_validation) values(" + LV_id + "," + PR_id + "," + ((Integer)tfids.get(index - 1)).intValue() + "," + i1 + "," + i2 + ",'" + regTxt.getText() + "'," + i3 +")");
                int i;
                
                for(i = 0; i < datm.data.size(); i++)
                {
                    statement2.execute("insert into IISC_LV_RETURN(LV_id, Att_id, LV_return_att_id) values(" + LV_id + "," + ((AttPair)datm.data.get(i)).getAttId() + "," + ((AttPair)datm.data.get(i)).getRetAttId() + ")");
                    if(i==0)
                    statement2.execute("update IISC_ATT_TOB set LV_id=" + LV_id + " where att_id=" + ((AttPair)datm.data.get(i)).getRetAttId() + " and TF_id=" + Tf_id);
                }
                statement2.close();
                JOptionPane.showMessageDialog(null, "<html><center>LOV saved!", "Lists of Values", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            
            Statement statement2 = conn.createStatement();
            statement2.execute("update IISC_LIST_OF_VALUES set Tf_id =" + tfids.get(index - 1) + ",LV_type=" + i1 + ",LV_search=" + i2 + ",LV_regular_expression='" + regTxt.getText() + "',LV_mand_validation=" + i3 + " where LV_id=" + LV_id);
            statement2.execute("delete from IISC_LV_RETURN where LV_id=" + LV_id);
            statement2.execute("UPDATE IISC_ATT_TOB SET LV_id=null where LV_id=" + LV_id);
            int i;
            
            for(i = 0; i < datm.data.size(); i++)
            {
                statement2.execute("insert into IISC_LV_RETURN(LV_id, Att_id, LV_return_att_id) values(" + LV_id + "," + ((AttPair)datm.data.get(i)).getAttId() + "," + ((AttPair)datm.data.get(i)).getRetAttId() + ")");
                if(i==0)
                statement2.execute("update IISC_ATT_TOB set LV_id=" + LV_id + " where att_id=" + ((AttPair)datm.data.get(i)).getRetAttId() + " and TF_id=" + Tf_id);
            }
            statement2.close();
            setLOVs(LV_id);
            JOptionPane.showMessageDialog(null, "<html><center>LOV saved!", "Lists of Values", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException sqle)
        {
            System.out.println("Sql exception :" + sqle);
        }
    }

    private void btnHelp_actionPerformed(ActionEvent e) {
        Help hlp =new  Help((IISFrameMain) getParent(),getTitle(), true, conn );
        Settings.Center(hlp);
        hlp.setVisible(true);
    }

    private void close_ActionPerformed(ActionEvent e) {
        this.dispose();
    }

    private void apply_ActionPerformed(ActionEvent e) {
        Update();
    }

    private void save_ActionPerformed(ActionEvent e) {
        Update();
        dispose();
    }

    private void btnFirst_actionPerformed(ActionEvent e) {
        if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
                Update();        
                
        if (cmbLOV.getSelectedIndex()>0)
        {                    
            setLOVs(getLOVs(0));
        }
    }

    private void btnPrev_actionPerformed(ActionEvent e) {
        if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
                Update();        
              
        if (cmbLOV.getSelectedIndex()>0)
        {                  
            setLOVs(getLOVs(cmbLOV.getSelectedIndex()-1));
        }
    }

    private void cmbFun_actionPerformed(ActionEvent e) {
        if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
                Update(); 
        setLOVs(getLOVs(cmbLOV.getSelectedIndex()));
    }

    private void btnNext_actionPerformed(ActionEvent e) {

        if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
          if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
                Update();
        
        if (cmbLOV.getSelectedIndex()!=cmbLOV.getItemCount()-1)
        {            
            setLOVs(getLOVs(cmbLOV.getSelectedIndex()+1));
        }
    }

    private void btnLast_actionPerformed(ActionEvent e) {
        if(btnSave.isEnabled()) //ako je save digne enabled pita se da li hoce da snimi promjene
            if(JOptionPane.showConfirmDialog(null, "<html><center>Do you want to save changes?", "Function", JOptionPane.YES_NO_OPTION)==0)
                Update();      
                
        if (cmbLOV.getSelectedIndex()!=cmbLOV.getItemCount()-1)
        {
            setLOVs(getLOVs(cmbLOV.getItemCount()-1));  
        }
    }

    private void tfCombo_actionPerformed(ActionEvent e) {
    }

    private void new_ActionPerformed(ActionEvent e) {
        setLOVs(-1);
    }

    private void generate_ActionPerformed(ActionEvent e) {
    
    }

    private void btnDelete_actionPerformed(ActionEvent e) {
        delete();
        LV_Tf_id=-1;
        LV_id=-1;
        setLOVs(-1);
    }

    private void btnNew_actionPerformed(ActionEvent e) {
        LV_Tf_id=-1;
        LV_id=-1;
        setLOVs(-1);
    }

    private void btnGenerate_actionPerformed(ActionEvent e) {
        generateLOVs();
        btnGenerate.setEnabled(true);
    }
    private boolean hasDefaultLOVs() {
        try {
            Statement statement = conn.createStatement();
            Statement statement1 = conn.createStatement();
            ResultSet rs = statement.executeQuery("select IISC_ATT_TOB.att_id from IISC_RS_CONSTRAINT, IISC_RSC_LHS_RHS,IISC_ATT_TOB where IISC_RS_CONSTRAINT.LHS_id=IISC_RSC_LHS_RHS.RSCLR_id and IISC_RS_CONSTRAINT.RSC_type=0 and IISC_RSC_LHS_RHS.Att_id=IISC_ATT_TOB.Att_id and IISC_ATT_TOB.TF_id="+ Tf_id +" and IISC_RSC_LHS_RHS.AS_ID="+AS_id);
            while(rs.next()){
                int att = rs.getInt(1);
                ResultSet rs1 = statement1.executeQuery("select IISC_ATT_TOB.att_id from IISC_LV_RETURN, IISC_ATT_TOB where IISC_LV_RETURN.LV_ID=IISC_ATT_TOB.LV_ID and IISC_ATT_TOB.TF_id="+ Tf_id +" and IISC_ATT_TOB.att_id="+ att);
                if(!rs1.next()){
                    return false;
                }
            }
            statement.close();
        }
        catch(SQLException e)
        {
            System.out.println("\n" + e.toString() + "\n");
        }
        return true;
    }
    private void generateLOVs() {
        try {
            boolean can=false;
            Statement statement = conn.createStatement();
            Statement statement1 = conn.createStatement();
            Statement statement2 = conn.createStatement();
            Statement statement3 = conn.createStatement();
            Statement statement4 = conn.createStatement();
            ResultSet rs = statement.executeQuery("select IISC_ATT_TOB.att_id, IISC_RSC_RS_SET.RS_id from IISC_RS_CONSTRAINT, IISC_RSC_LHS_RHS,IISC_RSC_RS_SET, IISC_ATT_TOB where IISC_RS_CONSTRAINT.RHS_RS_Set_id=IISC_RSC_RS_SET.RS_Set_id and IISC_RS_CONSTRAINT.LHS_id=IISC_RSC_LHS_RHS.RSCLR_id and IISC_RS_CONSTRAINT.RSC_type=0 and IISC_RSC_LHS_RHS.Att_id=IISC_ATT_TOB.Att_id and IISC_ATT_TOB.TF_id="+ Tf_id +" and IISC_RSC_LHS_RHS.AS_ID="+AS_id);
            while(rs.next()){
                int att = rs.getInt(1);
                int rsid = rs.getInt(2);
                ResultSet rs1 = statement1.executeQuery("select IISC_ATT_TOB.att_id from IISC_LV_RETURN, IISC_ATT_TOB where IISC_LV_RETURN.LV_ID=IISC_ATT_TOB.LV_ID and IISC_ATT_TOB.TF_id="+ Tf_id +" and IISC_ATT_TOB.att_id="+ att);
                if(!rs1.next()){
                    ResultSet rs2 = statement2.executeQuery("select IISC_ATT_KTO.TF_id from IISC_ATT_KTO,IISC_TF_APPSYS where IISC_ATT_KTO.TF_id=IISC_TF_APPSYS.TF_id and IISC_ATT_KTO.TF_id<>"+Tf_id+" and IISC_ATT_KTO.att_id="+att+" and IISC_ATT_KTO.PR_id="+PR_id+" and IISC_TF_APPSYS.AS_id="+AS_id);
                    if(rs2.next()){
                        int tf = rs2.getInt(1);
                        int lv=0;
                        ResultSet rs3 = statement3.executeQuery("select max(LV_id) from IISC_LV_RETURN");
                        if (rs3.next())
                            lv = rs3.getInt(1)+1;
                        rs3.close();
                        //System.out.println("insert into IISC_LIST_OF_VALUES(LV_id,PR_id,Tf_id,LV_type,LV_search,LV_regular_expression,LV_mand_validation) values(" + lv + "," + PR_id + "," + tf + ",0,0,'',0)");
                        statement4.execute("insert into IISC_LIST_OF_VALUES(LV_id,PR_id,Tf_id,LV_type,LV_search,LV_regular_expression,LV_mand_validation) values(" + lv + "," + PR_id + "," + tf + ",0,0,'',0)");
                        statement4.execute("insert into IISC_LV_RETURN(LV_id, Att_id, LV_return_att_id) values(" + lv + "," + att + "," + att + ")");
                        statement4.execute("update IISC_ATT_TOB set LV_id=" + lv + " where att_id=" + att+ " and TF_id=" + Tf_id);
                        can=true;
                    }
                    rs2.close();
                }
                rs1.close();
            }
            rs.close();
            statement.close();
            statement1.close();
            statement2.close();
            statement3.close();
            statement4.close();
            if(can){
                JOptionPane.showMessageDialog(null, "<html><center>Default LOVs are created!", "Lists of Values", JOptionPane.INFORMATION_MESSAGE);
                setLOVs(-1);
            }
        }
        catch(SQLException e)
        {
            System.out.println("\n" + e.toString() + "\n");
        }        
    }
    /*************************************************************************/
        /*********                Klasa za table model                    ********/
        /*************************************************************************/
        private class AttPairTableModel extends AbstractTableModel
        {
        private String[] headers;
        private Vector data;
        
        public AttPairTableModel()
        {
            headers = new String[2];
            data = new Vector();
            headers[0] = "Attribute";
            headers[1] = "Return Att";
        }
        
        public int getColumnCount()
        {
            return 2;
        }
        
        public String getColumnName(int index)
        {
            return headers[index];
        }
        
        public int getRowCount()
        {
            return data.size();
        }
        
        public Object getValueAt(int x, int y)
        {
            AttPair value = (AttPair)data.get(x);
        
            if (y == 0)
            { 
                return (Object)value.getAttMnem();
            } 
            else
            {
                return (Object)value.getRetAttMnem();
            }
        }
        
        public void setValueAt(Object value, int row, int col) 
        {
            if (col == 0)
            {
                ((AttPair)data.elementAt(row)).setAttMnem((String)value);
                fireTableCellUpdated(row, col);
            }
            else
            {
                ((AttPair)data.elementAt(row)).setRetAttMnem((String)value);
                fireTableCellUpdated(row, col);
            }
        }
        
        public void populateData(Vector value)
        {
            data = value;
            fireTableDataChanged();
        }
        
        public boolean isCellEditable(int row, int col) 
        {
            return false;
        }    
        }
        
        private class AttPair
        {
        private int Att_id;
        private int ret_Att_id;
        private String Att_mnem;
        private String ret_Att_mnem;
        
        
        public AttPair(int _Att_id, int _ret_Att_id, String _Att_mnem, String _ret_Att_mnem)
        {
            Att_id = _Att_id;
            ret_Att_id = _ret_Att_id;
            Att_mnem = _Att_mnem;
            ret_Att_mnem = _ret_Att_mnem;
        }
        
        public void setAttId(int value)
        {
            Att_id = value;
        }
        
        public int getAttId()
        {
            return Att_id;
        }
        
        public void setRetAttId(int value)
        {
            ret_Att_id = value;
        }
        
        public int getRetAttId()
        {
            return ret_Att_id;
        }
        
        public void setAttMnem(String value)
        {
            Att_mnem = value;
        }
        
        public String getAttMnem()
        {
            return Att_mnem;
        }
        
        public void setRetAttMnem(String value)
        {
            ret_Att_mnem = value;
        }
        
        public String getRetAttMnem()
        {
            return ret_Att_mnem;
        }
        }
        }