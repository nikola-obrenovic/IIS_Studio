package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PromptDialog extends JDialog  implements ActionListener 
{
  private JLabel jLabel4 = new JLabel();

 public boolean overwrite=false;
  private JButton btnCancel = new JButton();
  private JButton btnOK = new JButton();
  public PromptDialog()
  {
    this(null, "", false);
  }

  public PromptDialog(Frame parent, String title, boolean modal )
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
  {
    this.setSize(new Dimension(267, 114));
    this.getContentPane().setLayout(null);
    jLabel4.setText("File exists. Overwrite this file? ");
    jLabel4.setBounds(new Rectangle(55, 5, 335, 35));
    jLabel4.setFont(new Font("SansSerif", 0, 11));
    btnCancel.setText("No");
    btnCancel.setBounds(new Rectangle(140, 45, 80, 25));
    btnCancel.setFont(new Font("SansSerif", 0, 11));
    btnCancel.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnCancel_actionPerformed(e);
        }
      });
    btnOK.setText("Yes");
    btnOK.setBounds(new Rectangle(45, 45, 70, 25));
    btnOK.setFont(new Font("SansSerif", 0, 11));
    btnOK.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnOK_actionPerformed(e);
        }
      });
    this.getContentPane().add(btnOK, null);
    this.getContentPane().add(btnCancel, null);
    this.getContentPane().add(jLabel4, null);
  }

  private void btnCancel_actionPerformed(ActionEvent e)
  { overwrite=false;
  this.setVisible(false);
  
  }

  private void btnOK_actionPerformed(ActionEvent e)
  {
  overwrite=true;
  this.setVisible(false);
  }
  public void actionPerformed(ActionEvent e)
  {
  
  }

  private void btnCancel1_actionPerformed(ActionEvent e)
  {dispose();
  }
}