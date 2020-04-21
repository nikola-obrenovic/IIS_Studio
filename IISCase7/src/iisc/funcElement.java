package iisc;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
//import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class funcElement extends JPanel 
{

  public int level = 0; //nivo dubine
  public int pozition = 1; //pozicija u glavnom array
  public int father = -1; //otac cvora
  public String delFlag="";
  protected int copypressed = -1;
  protected int copyreleased = -1;
  protected funAlg f = null;
  protected String toDo = "";  
  
  public funcElement()
  {
    super();
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }  
  
  public funcElement(int poz,int fat,int l,funAlg fu)
  {
    super();
    f = fu;    
    pozition = poz;
    father = fat;
    level = l;    
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
    this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
      {
        public void mouseDragged(MouseEvent e)
        {
          this_mouseDragged(e);
        }
      });
  }

  private void this_mouseDragged(MouseEvent e)
  {
          ImageIcon icon = new ImageIcon(IISFrameMain.class.getResource("icons/cursordgarblock.gif")); 
          Image a = icon.getImage();          
          f.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(a,new java.awt.Point(8,8),"test") );  
  }
  
  public void fillPanel()
  {
          for(int i=0;i<f.fun.length;i++)
            if (i != f.clicked)
                f.fun[i].setBackground(Color.WHITE);
            else
                f.fun[i].setBackground(new Color(216,216,216));                      
  }  
}