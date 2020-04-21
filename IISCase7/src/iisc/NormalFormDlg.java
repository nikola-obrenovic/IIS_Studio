package iisc;

import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class NormalFormDlg extends JDialog {
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JButton btnClose = new JButton();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTextArea txtDNF = new JTextArea();
    private JTextArea txtCNF = new JTextArea();
    private CheckConstraint chkCon;

    public NormalFormDlg(Frame parent, String title, boolean modal, CheckConstraint chkCon) {
        super(parent, title, modal);
        this.chkCon = chkCon;
        try {
            jbInit();
            fillData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(566, 273));
        this.getContentPane().setLayout( null );
        this.setTitle("Check Constraint Normal Forms");
        jTabbedPane1.setBounds(new Rectangle(15, 15, 525, 180));
        btnClose.setText("Close");
        btnClose.setBounds(new Rectangle(460, 205, 80, 30));
        btnClose.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnClose_actionPerformed(e);
                    }
                });
        txtDNF.setEditable(false);
        txtCNF.setEditable(false);
        jScrollPane1.getViewport().add(txtDNF, null);
        jTabbedPane1.addTab("DNF", jScrollPane1);
        jScrollPane2.getViewport().add(txtCNF, null);
        jTabbedPane1.addTab("CNF", jScrollPane2);
        this.getContentPane().add(btnClose, null);
        this.getContentPane().add(jTabbedPane1, null);
    }

    private void fillData() {
        txtDNF.setText(chkCon.getDnf().toString(false));
        txtCNF.setText(chkCon.getCnf().toString(false));
    }

    private void btnClose_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
