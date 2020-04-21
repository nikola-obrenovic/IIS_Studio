package iisc;
import java.awt.Graphics;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

public class PropertyScrollPane extends JScrollPane
{
  PropertyPanel pPanel;
  int height;
  int width;
  JScrollBar sb;
  int firstPaint;
  
  public PropertyScrollPane(PropertyPanel _pPanel, int _width, int _height )
  {
      super();
      setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
      try
      {
          pPanel = _pPanel;
          height = _height;
          width = _width;
          sb = new JScrollBar(JScrollBar.VERTICAL);
          this.getViewport().add(pPanel);
          this.setBounds(0, 0, width, height);
          setVerticalScrollBar(sb);
          firstPaint = 0;
          
          addComponentListener(new java.awt.event.ComponentAdapter()
          {
            public void componentResized(ComponentEvent e)
            {
              this_componentResized(e);
            }
          });
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
      //setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      //super.repaint();
      
  }
  public void paint(Graphics g)
  {
      if (firstPaint == 0)
      {  
          if (sb.isShowing())
          {
              
              pPanel.setWidth(this.getWidth() - sb.getWidth() - 3);
              
          }
          else
          {
              pPanel.setWidth(width - 3);
             
          }
      }
      
      super.paint(g);
      revalidate();
      firstPaint = 1;
     
  }
  
  public JScrollBar getVertScrollBar()
  {
      return sb;
  }

 
  private void this_componentResized(ComponentEvent e)
  {
      if (sb.isShowing())
      {
          pPanel.setWidth(this.getWidth() - sb.getWidth() - 3);
      }
      else
      {
          pPanel.setWidth(this.getWidth() - 3);
      }
  }
   
}