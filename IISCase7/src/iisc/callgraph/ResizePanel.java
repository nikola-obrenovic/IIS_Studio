package iisc.callgraph;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

public class ResizePanel extends JPanel
{
  private DrawingPanel gr;
  private FormPanel fp;
  public int index;
  private int oldX;
  private int oldY;
  private int x;
  private int y;
  private int fx;
  private int fy;
  private int fw;
  private int fh;
  int deltax;
  int deltay;
  int offsetX;
  int offsetY;
  double sw;
  double mw;
  double cpw;
  double ctw;
  double ppw;
  double plw;
  
  int sum;
  
  public ResizePanel(int _x, int _y, DrawingPanel _gr, int _index, FormPanel _fp)
  {
      super();
      index = _index;
      x = _y;
      y = _y;
      
      setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
      setBackground(Color.black);
      
      gr = _gr;
      fp = _fp;
      
      addMouseListener(new MouseAdapter()
      {
          public void mousePressed(MouseEvent e)
          {
              //gr.setSelected(-1);
              //gr.parent.selFp = fp;
              
              oldX = e.getX();
              oldY = e.getY();
              offsetX = 0;
              offsetY = 0;
              fx = fp.getX();
              fy = fp.getY();
              //fw = fp.width;
              //fh = fp.height;
              if (fp.expanded)
              {
                sw = 0;
                mw = 1;
                
                if (fp.compPanel.expanded)
                {
                       
                    if (fp.parPanel.expanded)
                    {
                        cpw = 0.0;
                        ctw = 0.5;
                        ppw = 0.0;
                        plw = 0.5;
                    }
                    else
                    {
                        cpw = 0.0;
                        ctw = 1;
                        ppw = 0.0;
                        plw = 0.0;
                    }
                }
                else
                {
                    if (fp.parPanel.expanded)
                    {
                        cpw = 0.0;
                        ctw = 0.0;
                        ppw = 0.0;
                        plw = 1;
                    }
                    else
                    {
                        cpw = 0.5;
                        ctw = 0.0;
                        ppw = 0.5;
                        plw = 0.0;
                    }
                }
              }
              else
              {
                  sw = 1;
                  ppw = 0;
                  plw = 0;
                  cpw = 0;
                  ctw = 0;
                  mw = 0;
              }
              
          }
          public void mouseReleased(MouseEvent e)
          {
              fp.parent.dirty = true;
              fp.dirty = true;
              offsetX = 0;
              offsetY = 0;
              fw = fp.width;
              fh = fp.height;
              //gr.gr.CheckLines(fp.index);
              gr.repaint();
              
          }
          
      });
      
      this.addMouseMotionListener(new MouseMotionAdapter()
      {
          public void mouseDragged(MouseEvent e)
          {
              //System.out.println(" e.getX(): " +  e.getX() + "e.getX()" + e.getY());
              deltax = e.getX() - oldX;
              x = x + deltax;
              
              
              deltay = e.getY() - oldY;
              y = y + deltay;
              
              setLocation(x,y);
              fp.smallWidth = fp.smallWidth + (int)(deltax * sw);
              fp.smallHeight = fp.smallHeight + (int)(deltay * sw);
              
              //fp.mHeight = fp.mHeight + (int)(deltay * mw);
              fp.mWidth = fp.mWidth + (int)(deltax * mw);
              
              fp.mCTCHeight = fp.mCTCHeight + (int)(deltay * cpw);
              fp.mCTEHeight = fp.mCTEHeight + (int)(deltay * ctw);
              
              fp.mParCHeight = fp.mParCHeight + (int)(deltay * ppw);
              fp.mParEHeight = fp.mParEHeight + (int)(deltay * plw);
              
              fp.Readjust();
              //fp.setBounds(fx,fy, fw + offsetX, fh + offsetY);
              gr.repaint();
          }
      
      });
  }
  
}