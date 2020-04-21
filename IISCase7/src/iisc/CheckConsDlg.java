package iisc;

import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.sql.Connection;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.text.Caret;

public class CheckConsDlg extends JDialog {
    private JLabel jLabel1 = new JLabel();
    private JTextArea txtOrigCon = new JTextArea();
    private JLabel jLabel2 = new JLabel();
    private JList liRefRelSchs = new JList();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JTextArea txtDNFCon = new JTextArea();
    private ArrayList<Integer> relSchIds = new ArrayList<Integer>();

    private Connection con;
    private int appSysID;
    private int projID;
    private int chkConID;
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JScrollPane jScrollPane4 = new JScrollPane();
    private JTextArea txtCNFCon = new JTextArea();
    private JButton btnClose = new JButton();
    private JButton btnViewRS = new JButton();

    public CheckConsDlg(IISFrameMain parent, String title, boolean modal, 
                      Connection conn, int projID, int appSysID, int chkConID) {
        super(parent, title, modal);
        con = conn;
        this.projID = projID;
        this.appSysID = appSysID;
        this.chkConID = chkConID;
        try {
            jbInit();
            fillData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(551, 529));
        this.getContentPane().setLayout( null );
        this.setTitle("Check constraint");
        jLabel1.setText("Check constraint");
        jLabel1.setBounds(new Rectangle(20, 20, 90, 15));
        txtOrigCon.setEditable(false);
        jLabel2.setText("Referenced relation schemes");
        jLabel2.setBounds(new Rectangle(20, 145, 160, 15));
        liRefRelSchs.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        liRefRelSchs_mouseClicked(e);
                    }
                });
        jTabbedPane1.setBounds(new Rectangle(20, 295, 500, 155));
        txtDNFCon.setEditable(false);
        jScrollPane1.setBounds(new Rectangle(20, 35, 495, 90));
        jScrollPane2.setBounds(new Rectangle(20, 165, 335, 110));
        txtCNFCon.setEditable(false);
        btnClose.setText("Close");
        btnClose.setBounds(new Rectangle(430, 460, 90, 30));
        btnClose.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnClose_actionPerformed(e);
                    }
                });
        btnViewRS.setText("View");
        btnViewRS.setBounds(new Rectangle(365, 165, 85, 30));
        btnViewRS.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnViewRS_actionPerformed(e);
                    }
                });
        jScrollPane3.getViewport().add(txtDNFCon, null);
        jTabbedPane1.addTab("DNF", jScrollPane3);
        jScrollPane4.getViewport().add(txtCNFCon, null);
        jTabbedPane1.addTab("CNF", jScrollPane4);
        jScrollPane1.getViewport().add(txtOrigCon, null);
        jScrollPane2.getViewport().add(liRefRelSchs, null);
        this.getContentPane().add(btnViewRS, null);
        this.getContentPane().add(btnClose, null);
        this.getContentPane().add(jScrollPane2, null);
        this.getContentPane().add(jScrollPane1, null);
        this.getContentPane().add(jTabbedPane1, null);
        this.getContentPane().add(jLabel2, null);
        this.getContentPane().add(jLabel1, null);
    }

    private void fillData() {
        
        CheckConstraint chkCon = CheckConstraint.loadCheckConstraint(con,projID,appSysID,chkConID);
        
        txtOrigCon.setText(chkCon.getOrigCon());
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                jScrollPane1.getHorizontalScrollBar().setValue(0);
            }
        }); 
        
        txtDNFCon.setText(chkCon.getDnf().toString(true));
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                jScrollPane3.getHorizontalScrollBar().setValue(0);
            }
        }); 

        txtCNFCon.setText(chkCon.getCnf().toString(true));
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                jScrollPane4.getHorizontalScrollBar().setValue(0);
            }
        }); 
        
        DefaultListModel dlm = new DefaultListModel();
        for(int i=0; i<chkCon.getRefRelSch().size(); i++)
        {
            dlm.addElement(chkCon.getRefRelSch().get(i));
            relSchIds.add(chkCon.getRefRelSchIds().get(i));
        }
        liRefRelSchs.setModel(dlm);
        
    }

    private void btnClose_actionPerformed(ActionEvent e) {
        dispose();
    }

    private void liRefRelSchs_mouseClicked(MouseEvent e) {
        if(e.getClickCount() > 1)
        {
            viewSelRelScheme();
        }
    }

    private void btnViewRS_actionPerformed(ActionEvent e) {
        viewSelRelScheme();
    }
    
    private void viewSelRelScheme()
    {
        int selRelSch = liRefRelSchs.getSelectedIndex();
        PTree tr=(PTree)(((IISFrameMain)getParent()).desktop.getSelectedFrame());
        RScheme rsh=new RScheme(((IISFrameMain)this.getParent()),"Relation Scheme",true,con,relSchIds.get(selRelSch),tr,appSysID);
        Settings.Center(rsh);
        rsh.setVisible(true);
    }
}
