package iisc;
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
        labelTitle.setText("IIS*CASE Integrated Information Systems Case tool Version 7.5.0");
    labelTitle.setFont(new Font("SansSerif", 0, 12));
    labelTitle.setIcon(new ImageIcon( Toolkit.getDefaultToolkit().getImage(getClass().getResource("icons/IISCase-big.gif"))));
    labelAuthor.setText("<font face=SansSerif size=3>" +
    "<table width=100%>" +
    "   <tr>" +
    "       <td>" +
    "           <font face=SansSerif size=3>Authors:" +
    "       </td>" +
    "       <td>" +
    "           <font face=SansSerif size=3>Ivan Lukovi\u0107" +
    "       </td>" +
    "       <td>" +
    "           <font face=SansSerif size=3>Jelena Banovi\u0107" +
    "       </td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td></td>" +
    "       <td>" +
    "           <font face=SansSerif size=3>Pavle Mogin" +
    "       </td>" +
    "       <td>" +
    "           <font face=SansSerif size=3>Žarko Karadži\u0107" +
    "       </td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td></td>" +
    "       <td>" +
    "           <font face=SansSerif size=3>Sonja Risti\u0107" +
    "       </td>" +
    "       <td>" +
    "           <font face=SansSerif size=3>Milan Brki\u0107" +
    "       </td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td></td>" +
    "       <td>" +
    "           <font face=SansSerif size=3>Miro Govedarica" +
    "       </td>" +
    "       <td>" +
    "           <font face=SansSerif size=3>Dejan \u010celar" +
    "       </td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td></td>" +
    "       <td><font face=SansSerif size=3>Slavica Aleksi\u0107</td>" +
    "       <td><font face=SansSerif size=3>Aleksandar Popovi\u0107</td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td></td>" +
    "       <td><font face=SansSerif size=3>Jovo Mosti\u0107</td>" +
    "       <td><font face=SansSerif size=3></td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td><font face=SansSerif size=3>Contact Info:</td> " +
    "       <td colspan=2><font face=SansSerif size=3>Ivan Lukovi\u0107</td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td></td> " +
    "       <td colspan=2><font face=SansSerif size=3><a href=mailto:ivan@uns.ac.rs>ivan@uns.ac.rs</a></td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td></td>" +
    "       <td colspan=2><font face=SansSerif size=3>Pavle Mogin</td>" +
    "   </tr>" +
    "   <tr>" +
    "       <td></td>" +
    "       <td colspan=2><font face=SansSerif size=3><a href=mailto:pmogin@mcs.vuw.ac.nz>pmogin@mcs.vuw.ac.nz</a></td>" +
    "   </tr>" +
    "</table>");
    labelAuthor.setAutoscrolls(true);
    labelAuthor.setFont(new Font("SansSerif", 0, 11));
    labelCopyright.setText("Copyright © 2004 - 2010");
    labelCopyright.setFont(new Font("SansSerif", 0, 12));
    labelCompany.setText("    ");
    labelAuthor.setBackground(new Color(240, 235, 226));
    labelAuthor.setEditable(false);
    labelCompany.setFont(new Font("SansSerif", 0, 11));
    this.add(labelTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(15, 25, 0, 25), 0, 0));
    this.add(labelAuthor, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 25, 0, 25), 0, 0));
    this.add(labelCopyright, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 25, 0, 25), 0, 0));
    this.add(labelCompany, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 25, 15, 25), 0, 0));
  }
}