package ui;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.*;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.text.html.*;
import java.awt.Dimension;

public class IISFrameMain_AboutBoxPanel1 extends JPanel 
{
  private Border border = BorderFactory.createEtchedBorder();
  private GridBagLayout layoutMain = new GridBagLayout();
  private JLabel labelCompany = new JLabel();
  private JLabel labelCopyright = new JLabel();
  private JTextPane labelAuthor = new JTextPane();
  private JLabel labelTitle = new JLabel();

  public IISFrameMain_AboutBoxPanel1()
  {
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
  {   HTMLEditorKit kit=new HTMLEditorKit(); 
   labelAuthor.setEditorKit(kit);
    this.setLayout(layoutMain);
    this.setBorder(border);
    this.setBackground(new Color(240, 235, 226));
    this.setSize(new Dimension(400, 220));
    labelTitle.setText("IIS*UIModeler User Interface Design Tool Version 7.0.1");
    labelTitle.setFont(new Font("SansSerif", 0, 12));
    labelTitle.setIcon(new ImageIcon( Toolkit.getDefaultToolkit().getImage(getClass().getResource("icons/IIST-big.gif"))));
    labelAuthor.setText("<font face=SansSerif size=3><table width=100%><tr><td><font face=SansSerif size=3>Authors:</td><td><font face=SansSerif size=3>Jelena Banovi\u0107</td></tr><tr><td></td><td><font face=SansSerif size=3>Ivan Lukovi\u0107</td></tr>" +
    "<tr><td><font face=SansSerif size=3>Contact Info:</td> <td colspan=2><font face=SansSerif size=3>Jelena Banovi\u0107</td></tr><tr><td></td> <td colspan=2><a href=mailto:jelenap@cg.yu>jelenap@t-com.me</a></td></tr></table>");
    labelAuthor.setAutoscrolls(true);
    labelAuthor.setFont(new Font("SansSerif", 0, 11));
    labelCopyright.setText("Copyright © 2010");
    labelCopyright.setFont(new Font("SansSerif", 0, 12));
    labelCompany.setText("    ");
    labelAuthor.setBackground(new Color(240, 235, 226));
    labelAuthor.setEditable(false);
    labelCompany.setFont(new Font("SansSerif", 0, 11));
    this.add(labelTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(15, 25, 0, 25), 0, 0));
    this.add(labelAuthor, 
                 new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, 
                                        new Insets(0, 15, 12, 17), 41, 4));
    this.add(labelCopyright, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 25, 0, 25), 0, 0));
    this.add(labelCompany, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 25, 15, 25), 0, 0));
  }
}