package iisc;

import java.awt.Frame;

import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/******************************************************************/
  /********       Forma za editovanje vrijednosti iz tabele    ******/
  /******************************************************************/
  public class AddDisplayValue extends JDialog
  {
      public String name;
      public String domain;
      public String dafaultValue;
      public String iodefault;
      public JLabel nameLbl = new JLabel("Name");
      public JLabel domainLbl = new JLabel("Domain");
      public JLabel defaultValueLbl = new JLabel("Default Value");
      public JLabel inoutLbl = new JLabel("In/Out Type");
      public JTextField nameTxt = new JTextField("");
      public JTextField defValTxt = new JTextField("");
      public JComboBox cmbiout = new JComboBox();
      public JButton saveBtn = new JButton("OK");
      public JButton cancelBtn = new JButton("Cancel");
      public JButton domainSelect = new JButton("...");
      public JComboBox domainCombo = new JComboBox();
      public int SAVE = 0;
      public int CANCEL = 1;
      public int action;
      public int id = -1;
      public Connection connection = null;
      public Frame parentS = null;
      private PTree tree;
      
      public AddDisplayValue(String p_name, String p_domain, String title, IISFrameMain parent,String p_defVal,String io_val,Connection con,PTree t)
      {
          super(parent, title, true);
          
          parentS = parent;
          tree = t;
          connection = con;
          name = p_name;
          domain = p_domain;
          dafaultValue = p_defVal;
          iodefault = io_val;
          
          setSize(285, 180);
          getContentPane().setLayout(null);
          setResizable(false);
          action = CANCEL;
          
          nameLbl.setBounds(10, 10, 80, 20);
          nameTxt.setBounds(90, 10, 180, 20);
          nameTxt.setText(name);
          
          domainLbl.setBounds(10, 35, 80, 20);
          domainCombo.setBounds(90,35,150,20);
          domainSelect.setBounds(245,35,25,20);
          
          try
          {
                id = tree.ID;
              ResultSet rs;
              JDBCQuery query=new JDBCQuery(connection);
              //System.out.println("select Dom_mnem from IISC_DOMAIN where PR_id="+tree.ID + " order by dom_mnem");
              rs=query.select("select Dom_mnem from IISC_DOMAIN where PR_id="+tree.ID + " order by dom_mnem");
                
              while (rs.next())
              {
                  domainCombo.addItem(rs.getString("Dom_mnem"));
              }
              domainCombo.setSelectedItem(domain);
              
              query.Close();          
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
    

          defaultValueLbl.setBounds(10, 60, 80, 20);
          defValTxt.setBounds(90, 60, 180, 20);
          defValTxt.setText(dafaultValue);
          
          inoutLbl.setBounds(10,85,80,20);
          cmbiout.setBounds(90,85,180,20);
          cmbiout.addItem("In");
          cmbiout.addItem("Out");
          cmbiout.addItem("In/Out");
          cmbiout.setSelectedItem("In");
          
          
          
          saveBtn.setBounds(55, 115, 70, 25);
          cancelBtn.setBounds(160, 115, 70, 25);
          saveBtn.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              saveBtn_actionPerformed(e);
            }
          });
          
          cancelBtn.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              cancelBtn_actionPerformed(e);
            }
          });
         
          domainSelect.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              domainSelect_actionPerformed(e);
            }
          });         
         
          getContentPane().add(nameLbl);
          getContentPane().add(domainLbl);
          getContentPane().add(defaultValueLbl);
          getContentPane().add(nameTxt);
          getContentPane().add(defValTxt);
          getContentPane().add(domainCombo);
          getContentPane().add(saveBtn);
          getContentPane().add(cancelBtn);
          getContentPane().add(cmbiout);
          getContentPane().add(inoutLbl);
          getContentPane().add(domainSelect);
      }
      private void domainSelect_actionPerformed(ActionEvent e){
          SearchTable ptype=new SearchTable((Frame)getParent(),"Select User Define Domain",true,connection,String.valueOf(id),"","",0);
          Settings.Center(ptype);
          ptype.type="Domain2";
          ptype.owner=this;
          ptype.setVisible(true);          
      }
      private void saveBtn_actionPerformed(ActionEvent e)
      {
          action = SAVE;
          name = nameTxt.getText();
          domain = domainCombo.getSelectedItem().toString();
          dafaultValue = defValTxt.getText();
          iodefault = cmbiout.getSelectedItem().toString();
          this.hide();
      }
      
      private void cancelBtn_actionPerformed(ActionEvent e)
      {
          action = CANCEL;
          this.hide();
      }
  }