package iisc;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.sql.*;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.text.html.*;
import javax.swing.JScrollPane;
public class Help extends JDialog implements ActionListener
{
  public JButton btnClose = new JButton();


  private Connection con;
  private String tittle;
  private ButtonGroup bgrp=new ButtonGroup();
  public String dialog=new String();
  private JLabel lbForm = new JLabel();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JEditorPane txtHelp = new JEditorPane();
  public Help()
  {
    this(null, "", false,null);
  }

  public Help(IISFrameMain parent, String title, boolean modal, Connection conn )
  {
    super(parent, title, modal);
    try
    { tittle=title;
      con=conn;
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
 
  private void jbInit() throws Exception
  {  this.setResizable(false);
    this.setSize(new Dimension(388, 383));
    this.getContentPane().setLayout(null);
    this.setTitle("Help - "+tittle);
    btnClose.setMaximumSize(new Dimension(50, 30));
    btnClose.setPreferredSize(new Dimension(50, 30));
    btnClose.setText("Close");
    btnClose.setBounds(new Rectangle(295, 315, 80, 30));
    btnClose.setMinimumSize(new Dimension(50, 30));
    btnClose.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnClose_actionPerformed(e);
        }
      });
    lbForm.setBounds(new Rectangle(90, 15, 285, 20));
    lbForm.setFont(new Font("Dialog", 1, 11));
    jScrollPane1.setBounds(new Rectangle(5, 5, 370, 305));
    jScrollPane1.getViewport().add(txtHelp, null);
    this.getContentPane().add(jScrollPane1, null);
    this.getContentPane().add(lbForm, null);
    this.getContentPane().add(btnClose, null);
    JDBCQuery query=new JDBCQuery(con);
    ResultSet rs;  
    txtHelp.setEditable(false);
    HTMLEditorKit kit=new HTMLEditorKit();
    btnClose.setFont(new Font("SansSerif", 0, 11));
    txtHelp.setFont(new Font("SansSerif", 0, 11));
    this.setFont(new Font("SansSerif", 0, 11));
    txtHelp.setEditorKit(kit);

    try
    {
    rs=query.select("select * from IISC_HELP_FORM where HFO_form_name='"+ tittle +"'");
    if(rs.next())
    {
      String text="<p><font style=\"font-family:Verdana;font-size:12pt;\"><b>" + rs.getString("HFO_form_name") + "</b><br>"+rs.getString("HFO_form_desc") + "</p>";
      int fid=rs.getInt("HFO_id");
      query.Close();
      rs=query.select("select * from IISC_HELP_FIELD where HFO_id="+ fid +"");
    while(rs.next())
    {
      text=text + "<p><font style=\"font-family:Verdana;font-size:11pt;\"><b>"+rs.getString("HF_field_name")+ "</b> - "+rs.getString("HF_desc") + "</p>";
    }
    txtHelp.setText(text);
     query.Close();
    }
    else
    query.Close();
    }
    catch(SQLException e)
    {
    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    }
     public void actionPerformed(ActionEvent ae)
        {

        }

  private void btnClose_actionPerformed(ActionEvent e)
  {dispose();
  }
}