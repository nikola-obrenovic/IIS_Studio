package iisc;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;


/**
 * Klasa koja sluzi za globalna podesavanja na nivou aplikacije.
 * Tu su metode za centriranje JFrame i JDialog prozora na ekranu i
 * podesavanje izgleda grafickih elemenata aplikacije.
 */
public class Settings 
{
  
  public static void Center(JFrame frejm)
  {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    if ( (screenSize.getWidth() > frejm.getWidth()) && (screenSize.getHeight() >
    frejm.getHeight()))
        frejm.setBounds( (int)(screenSize.getWidth() - (int)frejm.getWidth())/2,
    (int)(screenSize.getHeight()-frejm.getHeight())/2, frejm.getWidth(), frejm.getHeight());
    else
    // If preferred height or width is greter than screen size, fill up the whole screen
        frejm.setBounds( 0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
  }


 public static void Center(Frame frejm)
  {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    if ( (screenSize.getWidth() > frejm.getWidth()) && (screenSize.getHeight() >
    frejm.getHeight()))
        frejm.setBounds( (int)(screenSize.getWidth() - (int)frejm.getWidth())/2,
    (int)(screenSize.getHeight()-frejm.getHeight())/2, frejm.getWidth(), frejm.getHeight());
    else
    // If preferred height or width is greter than screen size, fill up the whole screen
        frejm.setBounds( 0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
  }

  public static void Center(JDialog frejm)
  {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    if ( (screenSize.getWidth() > frejm.getWidth()) && (screenSize.getHeight() >
    frejm.getHeight()))
        frejm.setBounds( (int)(screenSize.getWidth() - (int)frejm.getWidth())/2,
    (int)(screenSize.getHeight()-frejm.getHeight())/2, frejm.getWidth(), frejm.getHeight());
    else
    // If preferred height or width is greter than screen size, fill up the whole screen
        frejm.setBounds( 0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
  }

 public static void CenterDown(JDialog frejm,int k)
  {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    if ( (screenSize.getWidth() > frejm.getWidth()) && (screenSize.getHeight() >
    frejm.getHeight()))
        frejm.setBounds( (int)(screenSize.getWidth() - (int)frejm.getWidth())/2+k,
    (int)(screenSize.getHeight()-frejm.getHeight())/2+k, frejm.getWidth(), frejm.getHeight());
    else
    // If preferred height or width is greter than screen size, fill up the whole screen
        frejm.setBounds( 0+k, 0+k, (int)screenSize.getWidth()+k, (int)screenSize.getHeight()+k);
  }
  
 public static void Center(JInternalFrame frejm)
  {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    if ( (screenSize.getWidth() > frejm.getWidth()) && (screenSize.getHeight() >
    frejm.getHeight()))
        frejm.setBounds( (int)(screenSize.getWidth() - (int)frejm.getWidth())/2,
    (int)(screenSize.getHeight()-frejm.getHeight())/2, frejm.getWidth(), frejm.getHeight());
    else
    // If preferred height or width is greter than screen size, fill up the whole screen
        frejm.setBounds( 0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
  }

  public static void lookFeel()
  {
    try 
    {
     UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		}
		catch ( Exception e ) 
    {
      //System.out.println("Unable to set LookAndFeel.");
		}
  }

  public static void messageBox ( String title, String msg ) {
    JOptionPane tempPane = new JOptionPane();
    tempPane.setFont(new Font("Dialog", 0, 12));
		tempPane.showMessageDialog( null ,msg, title, JOptionPane.INFORMATION_MESSAGE, null);
	}
    public static void CenterRelative(JInternalFrame frejm, JFrame parent)
    {
      frejm.setBounds( (int)(parent.getWidth() - (int)frejm.getWidth())/2,
      (int)(parent.getHeight()-frejm.getHeight())/2-50, frejm.getWidth(), frejm.getHeight());
    }
    public static void CenterRelative(JDialog frejm, JFrame parent)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if ( (screenSize.getWidth() > frejm.getWidth()) && (screenSize.getHeight() >
        frejm.getHeight()))
            frejm.setBounds( (int)(screenSize.getWidth() - (int)frejm.getWidth())/2,
        (int)(screenSize.getHeight()-frejm.getHeight())/2, frejm.getWidth(), frejm.getHeight());
        else
        // If preferred height or width is greter than screen size, fill up the whole screen
            frejm.setBounds( 0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
    }
}