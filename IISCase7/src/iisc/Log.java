package iisc;
import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JDialog;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
 
import java.awt.event.ActionListener;
import javax.swing.JTextPane;
public class Log extends JDialog implements ActionListener
{


  private JPanel jPanel1 = new JPanel();
  public JButton btnClose = new JButton();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTextPane txtLog = new JTextPane();
  
  public Log()
  {
    this(null, "", false);
  }

  public Log(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(396, 383));
    this.getContentPane().setLayout(null);
    this.setFont(new Font("SansSerif", 0, 11));
    jPanel1.setLayout(null);
    jPanel1.setBounds(new Rectangle(0, 0, 1, 1));
    jPanel1.setBackground(new Color(255, 252, 233));
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
    btnClose.setFont(new Font("SansSerif", 0, 11));
    jScrollPane1.setBounds(new Rectangle(10, 15, 365, 290));
    txtLog.setEditable(false);
     
    jScrollPane1.getViewport().add(txtLog, null);
    this.getContentPane().add(jScrollPane1, null);
    this.getContentPane().add(btnClose, null);
    this.getContentPane().add(jPanel1, null);
  }

  public void setLogText(String s)
  {txtLog.setText(s);
  }
  private void btnClose_actionPerformed(ActionEvent e)
  {dispose();
  }
  public void  actionPerformed(ActionEvent e)
  { 
  }
}