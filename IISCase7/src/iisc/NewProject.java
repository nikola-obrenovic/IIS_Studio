package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewProject extends JDialog  implements ActionListener 
{
  private JLabel jLabel4 = new JLabel();


  private JButton btnCancel = new JButton();
  private JButton btnOK = new JButton();
  private JButton btnCancel1 = new JButton();
  public NewProject()
  {
    this(null, "", false);
  }

  public NewProject(Frame parent, String title, boolean modal)
  {
    super(parent, title, modal);
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {  this.setResizable(false);
    this.setSize(new Dimension(299, 117));
    this.getContentPane().setLayout(null);
    jLabel4.setText("Do you want to create New Project in this connection? ");
    jLabel4.setBounds(new Rectangle(10, 10, 335, 35));
    jLabel4.setFont(new Font("SansSerif", 0, 11));
    btnCancel.setText("No");
    btnCancel.setBounds(new Rectangle(100, 50, 80, 25));
    btnCancel.setFont(new Font("SansSerif", 0, 11));
    btnCancel.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCancel_actionPerformed(e);
        }
      });
    btnOK.setText("Yes");
    btnOK.setBounds(new Rectangle(20, 50, 70, 25));
    btnOK.setFont(new Font("SansSerif", 0, 11));
    btnOK.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnOK_actionPerformed(e);
        }
      });
    btnCancel1.setText("Cancel");
    btnCancel1.setBounds(new Rectangle(190, 50, 80, 25));
    btnCancel1.setFont(new Font("SansSerif", 0, 11));
    btnCancel1.setRequestFocusEnabled(false);
    btnCancel1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCancel1_actionPerformed(e);
        }
      });
    this.getContentPane().add(btnCancel1, null);
    this.getContentPane().add(btnOK, null);
    this.getContentPane().add(btnCancel, null);
    this.getContentPane().add(jLabel4, null);
  }

  private void btnCancel_actionPerformed(ActionEvent e)
  {this.dispose();
  JDBCDialog Dialog =new JDBCDialog((IISFrameMain)getParent(),"IIS*CASE Repository",true);
 
  }

  private void btnOK_actionPerformed(ActionEvent e)
  {this.dispose();
  PTree tree=new PTree(((PTree)((IISFrameMain)getParent()).desktop.getSelectedFrame()).con,"New",(IISFrameMain)getParent());
  tree.setVisible(true);
  ((IISFrameMain)getParent()).desktop.add(tree, BorderLayout.CENTER);
    try {
        tree.setSelected(true);
    } catch (java.beans.PropertyVetoException ed) {ed.printStackTrace();}
  
  }
  public void actionPerformed(ActionEvent e)
  {
  
  }

  private void btnCancel1_actionPerformed(ActionEvent e)
  {dispose();
  }
}