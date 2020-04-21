package iisc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;

public class CheckConsDlgOld extends JDialog implements ActionListener {
    
    private Connection con;
    private int appSysID;
    private int projID;
    private int chkConID;
            
    public JButton btnClose = new JButton();
    private JLabel lbRelSch = new JLabel("Referenced Relation Schemes");
    private JLabel lbOrigCon = new JLabel("Original Check Constraint");
    private JLabel lbDNFCon = new JLabel("DNF Check Constraint");
    private JLabel lbCNFCon = new JLabel("CNF Check Constraint");
        
    private JTextArea txtOrigCon = new JTextArea();
    private JTextArea txtDNFCon = new JTextArea();
    private JTextArea txtCNFCon = new JTextArea();
    
    private JScrollPane spRelSch = new JScrollPane();
    private JList liRelSch = new JList();
    
    public CheckConsDlgOld(IISFrameMain parent, String title, boolean modal, 
                      Connection conn, int projID, int appSysID, int chkConID) {
        super(parent, title, modal);
        
        con = conn;
        this.projID = projID;
        this.appSysID = appSysID;
        this.chkConID = chkConID;
        init();
        
    }

    private void init(){
        
        this.setResizable(false);
       
        this.setSize(new Dimension(405, 674));
        btnClose.setMaximumSize(new Dimension(70, 30));
        btnClose.setPreferredSize(new Dimension(70, 30));
        btnClose.setText("Close");
        btnClose.setMinimumSize(new Dimension(70, 30));
        btnClose.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        close_ActionPerformed(ae);
                    }
                });
        
        CheckConstraint chkCon = CheckConstraint.loadCheckConstraint(con,projID,appSysID,chkConID);
        
        txtOrigCon.setText(chkCon.getOrigCon());
        txtOrigCon.setSize(380,150);
        JScrollPane spOrigCon = new JScrollPane(txtOrigCon);
        spOrigCon.setPreferredSize(new Dimension(250, 80));
        spOrigCon.setAlignmentX(LEFT_ALIGNMENT);
        
        txtDNFCon.setText(chkCon.toString());
        txtDNFCon.setSize(380,150);
        JScrollPane spDNFCon = new JScrollPane(txtDNFCon);
        spDNFCon.setPreferredSize(new Dimension(250, 80));
        spDNFCon.setAlignmentX(LEFT_ALIGNMENT);
        
        txtCNFCon.setText("");
        txtCNFCon.setSize(380,150);
        JScrollPane spCNFCon = new JScrollPane(txtCNFCon);
        spCNFCon.setPreferredSize(new Dimension(250, 80));
        spCNFCon.setAlignmentX(LEFT_ALIGNMENT);
        
        DefaultListModel dlm = new DefaultListModel();
        for(int i=0; i<chkCon.getRefRelSch().size(); i++)
            dlm.addElement(chkCon.getRefRelSch().get(i));
        liRelSch.setModel(dlm);
        liRelSch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        liRelSch.setLayoutOrientation(JList.VERTICAL);
        JScrollPane spList = new JScrollPane(liRelSch);
        spList.setPreferredSize(new Dimension(250, 80));
        spList.setAlignmentX(LEFT_ALIGNMENT);

        JPanel pnlCon = new JPanel();
        pnlCon.setLayout(new BoxLayout(pnlCon, BoxLayout.PAGE_AXIS));
        pnlCon.add(lbRelSch);
        pnlCon.add(Box.createRigidArea(new Dimension(0,5)));
        pnlCon.add(spList);
        pnlCon.add(Box.createRigidArea(new Dimension(0,5)));
        pnlCon.add(lbOrigCon);
        pnlCon.add(Box.createRigidArea(new Dimension(0,5)));
        pnlCon.add(spOrigCon);
        pnlCon.add(Box.createRigidArea(new Dimension(0,5)));
        pnlCon.add(lbDNFCon);
        pnlCon.add(Box.createRigidArea(new Dimension(0,5)));
        pnlCon.add(spDNFCon);
        pnlCon.add(Box.createRigidArea(new Dimension(0,5)));
        pnlCon.add(lbCNFCon);
        pnlCon.add(Box.createRigidArea(new Dimension(0,5)));
        pnlCon.add(spCNFCon);
        pnlCon.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel pnlBtns = new JPanel();
        pnlBtns.setLayout(new BoxLayout(pnlBtns, BoxLayout.LINE_AXIS));
        pnlBtns.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlBtns.add(Box.createHorizontalGlue());
        pnlBtns.add(btnClose);
        pnlBtns.add(Box.createRigidArea(new Dimension(10, 0)));
              
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pnlCon,BorderLayout.CENTER);
        this.getContentPane().add(pnlBtns, BorderLayout.SOUTH);
        
    }

    private void close_ActionPerformed(ActionEvent e) {
        dispose();
    }


    public void actionPerformed(ActionEvent e) {
    }
}
