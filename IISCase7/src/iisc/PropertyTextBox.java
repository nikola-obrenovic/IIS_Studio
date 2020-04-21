package iisc;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JTextField;

public class PropertyTextBox extends JTextField 
{
  public PropertyTextBox()
  {
      super();
  }
  
  public PropertyTextBox(String text)
  {
      super(text);
  }
  
  public void paint(Graphics g)
  {
      super.paint(g);
      if (hasFocus())
      {
          g.setColor(Color.BLACK);
          //g.drawRect(0 ,0 , getWidth() - 1, getHeight() - 1);
      }
      
  }
  
}