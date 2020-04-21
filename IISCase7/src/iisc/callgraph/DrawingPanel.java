package iisc.callgraph;

import iisc.IISFrameMain;
import iisc.Settings;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
    
public class DrawingPanel extends JPanel implements MouseListener,MouseMotionListener
{
  public CallingGraph gr;
  public boolean line;
  public int startX;
  public int startY;
  public int lineX;
  public int lineY;
  private int i;
  private int size;
  private LineSt ln;
  private static float dash[] = { 10.0f };
  public int dragIndex = -1;
  private Polygon tmpPoly;
  private double k;
  private double n;
  private double temp;
  public int selectedLine = -1;
  public LineSt selLn;
  boolean drag;
  public FormPanel fp1;
  public FormPanel fp2;
  private int deltaX;
  private int deltaY;
  public static ImageIcon imageEdit = new ImageIcon(IISFrameMain.class.getResource("icons/edit.gif"));
  public static ImageIcon imageRemove = new ImageIcon(IISFrameMain.class.getResource("icons/remove2.gif"));  
  private JPopupMenu pop;
  private JMenuItem editMi;
  private JMenuItem removeMi;
    
  public DrawingPanel(CallingGraph _gr)
  {
      super();
      gr = _gr;
      line = false;
      drag = false;
      //setOpaque(false);
      addMouseListener(this);
      addMouseMotionListener(this);
  }
  
  public void mouseReleased(MouseEvent e)
  {
      line = false;
      
      if (( drag ) && (selectedLine > -1))
      {
          gr.CheckLine(selectedLine);
      }
      
      drag = false;
      
      //selectedLine = -1;
      //gr.lineVec.add(selLn);
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      gr.pLine.setBorder(BorderFactory.createEmptyBorder());               
      repaint();
   }
  
    public void mouseClicked(MouseEvent e)
    {
        if((e.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
        {
            if (selectedLine > -1)
            {
                SetPopUpMenu(e.getX(), e.getY());
            }
        }
        
        if (e.getClickCount() > 1)
        {
            ShowLineDialog();
        }
        
        
    }
    
    private void SetPopUpMenu(int x, int y)
    {
        pop = new JPopupMenu();
        editMi = new JMenuItem("Edit");
        removeMi = new JMenuItem("Remove");
        editMi.setBackground(Color.white);
        editMi.setIcon(imageEdit);
        removeMi.setBackground(Color.white);
        removeMi.setIcon(imageRemove);
        
        
        editMi.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              ShowLineDialog();
              gr.drawingPanel.repaint();
          }
        });
        removeMi.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              gr.RemoveFromGraph();
              gr.drawingPanel.repaint();
          }
        });
        pop.add(editMi);
        pop.add(removeMi);
        
        pop.show(this, x, y);
    }
    public void mouseMoved(MouseEvent e)
    {
        
    }
    
    private void RemoveLine()
    {
       
    
    }
  
    public void mouseDragged(MouseEvent e)
    {
        drag = true;
        
        if (selectedLine > -1)
        {
            lineX = e.getX() - startX;
            lineY = e.getY() - startY;
            
            startX = e.getX();
            startY = e.getY();
            
            if ((selLn.x1 + lineX > fp1.x) && (selLn.x1 + lineX < fp1.x + fp1.mPanel.getWidth()) && (selLn.y1 + lineY > fp1.y) && (selLn.y1 + lineY < fp1.y + fp1.mPanel.getHeight()))
            {
                selLn.x1 = selLn.x1 + lineX;
                selLn.y1 = selLn.y1 + lineY;
            }
            
            if ((selLn.x2 + lineX > fp2.x) && (selLn.x2 + lineX < fp2.x + fp2.mPanel.getWidth()) && (selLn.y2 + lineY > fp2.y) && (selLn.y2 + lineY < fp2.y + fp2.mPanel.getHeight()))
            {
                selLn.x2 = selLn.x2 + lineX;
                selLn.y2 = selLn.y2 + lineY;
            }
            repaint(Math.min(selLn.x1,selLn.x2)  - lineX, Math.min(selLn.y1,selLn.y2) - lineY,Math.max(selLn.x1,selLn.x2) - lineX, Math.max(selLn.y1,selLn.y2) - lineY);
        }
    }
    
    public void mouseEntered(MouseEvent e)
    {
        
    }
    
    public void mousePressed(MouseEvent e)
    {
        gr.setSelected(-1);
        CheckLineIsClicked(e.getX(), e.getY());
        startX = e.getX();
        startY = e.getY();
        
        /*if (selectedLine > 0)
        {
            gr.lineVec.remove(selectedLine);
            
        }*/
        if (line)
        {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            gr.pLine.setBorder(BorderFactory.createEmptyBorder());
        }
    }
  
    public void mouseExited(MouseEvent e)
    {
    }
      
    public void paint(Graphics g)
    {
        
        Graphics2D g2 = (Graphics2D)g;
        super.paint(g2);
        
        if(gr.grid_visible == 1)
        {
            int i;
            int j;
            /*if()
            {
            
            }*/
            //System.out.println(getVisibleRect().getX());
            int begX = (int)(getVisibleRect().getX());
            int begY = (int)(getVisibleRect().getY());
            
            begX = (begX/gr.grid_size) * gr.grid_size;
            
            begY = (begY/gr.grid_size) * gr.grid_size;
            
            for(i = begX; i <= begX + this.getVisibleRect().getWidth(); i = i + gr.grid_size)
            {
                for(j = begY; j <= begY + this.getVisibleRect().getHeight(); j = j + gr.grid_size)
                {
                    g2.fillRect(i,j,1,1);
                }
            }
        }
        
        
        
        size = gr.lineVec.size();
        
        
        if (dragIndex >= 0)
        {
            g2.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f, dash, 0.0f));
            
            
            for(i = 0; i < size; i++)
            {
                ln = (LineSt)gr.lineVec.get(i);
                g2.drawLine(ln.X1, ln.Y1, ln.X2 , ln.Y2 );
                drawArrow(g2, ln.X1, ln.Y1, ln.X2, ln.Y2, 2f);
            }
        }
        else
        {
            g2.setStroke(new BasicStroke(1.0f));
            for(i = 0; i < size; i++)
            {
                ln = (LineSt)gr.lineVec.get(i);
                g2.drawLine(ln.X1, ln.Y1, ln.X2 , ln.Y2 );
                drawArrow(g2, ln.X1, ln.Y1, ln.X2, ln.Y2, 2f);
            }
        }
        
        if(gr.getSelected() > -1)
        {
            g.setColor(Color.black);
            g.fillRect(gr.selFp.getX() - 5, gr.selFp.getY() - 5, 5, 5);
            g.fillRect(gr.selFp.getX() + gr.selFp.getWidth(), gr.selFp.getY() - 5, 5, 5);
            g.fillRect(gr.selFp.getX() - 5, gr.selFp.getY() + gr.selFp.getHeight(), 5, 5);
        }
        else
        {
            if((selectedLine > -1))
            {
                g2.fillRect((selLn.X1 + selLn.X2)/2 - 2, (selLn.Y1 + selLn.Y2)/2 -2, 5, 5);
                g2.fillRect((selLn.X1 + selLn.X2)/2 - 2, (selLn.Y1 + selLn.Y2)/2 -2, 5, 5);
                if (drag)
                {
                    g2.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f, dash, 0.0f));
                    g2.draw(new Line2D.Double(selLn.x1, selLn.y1, selLn.x2, selLn.y2));
                }
            }
        }
        
        if (line)
        {
            g2.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f, dash, 0.0f));
            g2.draw(new Line2D.Double(startX,startY,lineX,lineY));
        }
        
        g2.setStroke(new BasicStroke(1.0f));
        super.paintChildren(g2);
        
        
        g.dispose();
        g2.dispose();
    }
  
    public static void drawArrow(Graphics2D g2d, int xCenter, int yCenter, int x, int y, float stroke) 
    {
        double aDir=Math.atan2(xCenter-x,yCenter-y);
        //g2d.drawLine(x,y,xCenter,yCenter);
        //g2d.setStroke(new BasicStroke(2f));					
        Polygon tmpPoly=new Polygon();
        int i1=12+(int)(stroke*2);
        int i2=6+(int)stroke;							
        tmpPoly.addPoint(x,y);						
        tmpPoly.addPoint(x+xCor(i1,aDir+.5),y+yCor(i1,aDir+.5));
        tmpPoly.addPoint(x+xCor(i2,aDir),y+yCor(i2,aDir));
        tmpPoly.addPoint(x+xCor(i1,aDir-.5),y+yCor(i1,aDir-.5));
        tmpPoly.addPoint(x,y);						
        g2d.drawPolygon(tmpPoly);
        g2d.fillPolygon(tmpPoly);					
     }
     private static int yCor(int len, double dir) {return (int)(len * Math.cos(dir));}
     private static int xCor(int len, double dir) {return (int)(len * Math.sin(dir));}

    /***************************************************************/
    /****         Provjerava da li je korinik kliknuo na liniju  ***/
    /***************************************************************/
    private void CheckLineIsClicked(int x, int y)
    {
        int size = gr.lineVec.size();
        selectedLine = -1;
        
        for(int i = 0; i < size; i++)
        {
            selLn = (LineSt) gr.lineVec.get(i);
            
            if (selLn.X1 == selLn.X2)
            {                
                if ((y >= Math.min(selLn.Y1, selLn.Y2)) && (y <= Math.max(selLn.Y1, selLn.Y2)) && (x > selLn.X1 - 10) && (x < selLn.X1 + 10))
                {
                    selectedLine = i;
                    fp1 = (FormPanel)gr.tfVec.get(selLn.i);
                    fp2 = (FormPanel)gr.tfVec.get(selLn.j);
                    return;
                }
            }        
            
            if (selLn.Y1 == selLn.Y2)
            {                
                if ((y >= selLn.Y1 - 10) && (y <= selLn.Y1+ 10) && (x >= Math.min(selLn.X1, selLn.X2)) && (x <= Math.max(selLn.X1, selLn.X2)))
                {
                    selectedLine = i;
                    fp1 = (FormPanel)gr.tfVec.get(selLn.i);
                    fp2 = (FormPanel)gr.tfVec.get(selLn.j);
                    return;
                }
            }  
            
            if ((y >= Math.min(selLn.Y1, selLn.Y2)) && (y <= Math.max(selLn.Y1, selLn.Y2)) && (x >= Math.min(selLn.X1, selLn.X2)) && (x <= Math.max(selLn.X1, selLn.X2)))
            {
            
                if (((selLn.Y2 - selLn.Y1 < 10) && (selLn.Y2 - selLn.Y1 > -10)) || ((selLn.X2 - selLn.X1 < 10) && (selLn.X2 - selLn.X1 > -10)))
                {
                    selectedLine = i;
                    fp1 = (FormPanel)gr.tfVec.get(selLn.i);
                    fp2 = (FormPanel)gr.tfVec.get(selLn.j);
                    return;
                }
                
                k = ((double) selLn.Y2 - selLn.Y1)/((double)selLn.X2 - selLn.X1);
                n = ((double)(selLn.Y1 * selLn.X2 - selLn.Y2 * selLn.X1))/((double)selLn.X2 - selLn.X1);
                
                if ((y <= k * x + n + 10) && (y >= k * x + n - 10 ))
                {
                    selectedLine = i;
                    fp1 = (FormPanel)gr.tfVec.get(selLn.i);
                    fp2 = (FormPanel)gr.tfVec.get(selLn.j);
                    return;
                }                
            }
        }
    }
    
    public void ShowLineDialog()
    {
        if (selectedLine > -1)
        {
            LineSt ln = (LineSt)gr.lineVec.get(selectedLine);
            
            PassedValuePanel pp = new PassedValuePanel(gr.parent,gr.conn,gr.PR_id,((FormPanel)gr.tfVec.get(ln.i)).Tf_id,((FormPanel)gr.tfVec.get(ln.j)).Tf_id,ln.CS_id, gr.BA_id);
            Settings.Center(pp);
            pp.setVisible(true);
            
            if (pp.action == PassedValuePanel.OK)
            {
                pp.Update();
            }
            pp.dispose();
        }
    }
}