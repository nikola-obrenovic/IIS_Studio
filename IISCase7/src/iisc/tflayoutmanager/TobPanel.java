package iisc.tflayoutmanager;

import iisc.IISFrameMain;
import iisc.Settings;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.sql.*;


public class TobPanel extends JPanel implements MouseListener,MouseMotionListener,ImageObserver
{
    
    private boolean mDown = false;
    private JPopupMenu pop;
    private JMenuItem editMi;
    private JMenuItem layoutMi;
    
    private int oldX;
    private int oldY;
    private int deltax;
    private int deltay;
    

    private int offsetX;
    private int offsetY;
    
    public int x1;
    public int y1;
    public int x2;
    public int y2;
    private boolean dragged = false;
    public boolean draggEnabled = false;
    
    private static ImageIcon imageObj = new ImageIcon(IISFrameMain.class.getResource("icons/object.gif"));
    private Image image = imageObj.getImage();

    private String Tob_mnem;
    public int x = 250;
    public int y = 250;
    public int sameX = 250;
    public int sameY = 250;
    public int order;
    public int width = 120;
    public int height = 70;
    public DataPanel dp;
    public HeaderPanel hp;
    
    TFLayoutManager tlm;
    public int index;
    public static final Color gradColor = new Color(181, 235, 255);//SystemColor.textHighlight;
    public static final int tfW = 15;
    public static final int tfH = 5;
    public static final int widthF = 120;
    public static final int heightF = 70;
    
    public TobPanel(String _Tob_mnem, int _order, TFLayoutManager _tlm, int _index)
    {
        super();  
        Tob_mnem =  _Tob_mnem;
        tlm = _tlm;
        index = _index;
        setLayout(null);
        //setBounds(x, y, 100, 100);
        //SetPopUpMenu();
        setBorder(new LineBorder(Color.black, 1));
        setBackground(Color.white);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        hp = new HeaderPanel(width, height, this);
        hp.setBounds(1, 1, width - 2, 18);
        
        add(hp);
        
        dp = new DataPanel(width, height, this);
        dp.setBounds(1, 19, width-2, height - 20);
        
        add(dp);
        
        repaint();
        revalidate();
        
    }
    
    public void SetDataB(int y, int widht, int height)
    {
        TobDispProperties tdp = (TobDispProperties)tlm.propVec.get(index);
        
        if ( tdp.layCombo.getSelectedIndex() == 0)
        {
            dp.setBounds(5, 25, widht, height - 2);
        }
        else
        {
            dp.setBounds(5, 18, widht, height);
        }
        
        
    }
    
    public void SetHeader(int widht)
    {
          hp.setBounds(1, 1, widht-2, 20);
          //hp.setBorder(new LineBorder(Color.black, 1));
    }
      
    public int getDataWidth()
    {
        return dp.getWidth();
    }
    
    public int getDataHeight()
    {
        return dp.getHeight();
    }
      
    public int getDataX()
    {
        return dp.getX();
    }
    public void paint(Graphics g)
    {
        
        super.paint(g);
        //super.paintChildren(g);
        
        /*hp.setBounds(1, 1, width - 1, 18);
        dp.setBounds(1, 19, width, height);
        
        hp.revalidate();
        hp.repaint();
        
        dp.revalidate();
        dp.repaint();
        */
    }
    
    /**********************************************************************/
    /********               Event handleri                  ***************/
    /**********************************************************************/
     public void mouseReleased(MouseEvent e)
     {
          setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
          
          if (dragged)
          {
              TobDispProperties tdp = (TobDispProperties)tlm.propVec.get(index);
              tlm.dirty = true;
              tdp.vXrTxt.setText(Integer.toString((int)(((double)(x - LayoutPanel.boundX)/LayoutPanel.boundW)*100)));
              tdp.vYrTxt.setText(Integer.toString((int)(((double)(y - LayoutPanel.boundY)/LayoutPanel.boundH)*100)));
          }
          
     }
    
      public void mouseClicked(MouseEvent e)
      {
          /*if (e.getClickCount() > 1)
          {
              TFProperties tf = new TFProperties(parent.getCallingGraph().parent,gr.conn, parent.Tf_id);
              Settings.Center(tf);
              tf.show();
              return;
          }
          
          if((e.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
          {
              //System.out.println("Desni");
              pop.show(this,e.getX()+10, e.getY());
          }*/
      }
      
      public void mouseMoved(MouseEvent e)
      {
          
      }
    
      public void mouseDragged(MouseEvent e)
      {
          if (!draggEnabled)
          {
              return;
          }
          dragged = true;
          deltax = (e.getX() - oldX);
          deltay = (e.getY() - oldY);
          
          if ((x + deltax < LayoutPanel.boundX))
          {
              deltax = 0;
          }
          
          if ((y + deltay < LayoutPanel.boundY))
          {
              deltay = 0;
          }
          
          if ((x + deltax + 1 > LayoutPanel.boundX + LayoutPanel.boundW))
          {
              deltax = 0;
          }
          
          if ((y + deltay + 1 > LayoutPanel.boundY + LayoutPanel.boundH))
          {
              deltay = 0;
          }
          
          offsetX = offsetX + deltax;
          offsetY = offsetY + deltay;
          dragged = true;
          
          x = x + deltax;
          y =  y + deltay;
          
          setLocation(x,y);
          tlm.layPanel.revalidate();;
          
      }
      
      public void mouseEntered(MouseEvent e)
      {
          /*if (gr.drawingPanel.line)
          {
              setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
              //lX = e.getX();
              //lY = e.getY();
          }
          else
          {
              setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
          }
          */
      }
      
      public void mousePressed(MouseEvent e)
      {
          /*
          if (gr.drawingPanel.line)
          {
              gr.drawingPanel.startX = parent.getX() + e.getX();
              gr.drawingPanel.startY = parent.getY() + e.getY();
              
              return;
          }
          */
          if (!draggEnabled)
          {
              return;
          }
          mDown = true;
          oldX = e.getX();
          oldY = e.getY();
          offsetX = 0;
          offsetY = 0;
          this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
          
          
      }
    
      public void mouseExited(MouseEvent e)
      {
      }
      
     
      private void SetPopUpMenu()
      {
          pop = new JPopupMenu();
          editMi = new JMenuItem("Edit");
          layoutMi = new JMenuItem("Layout Editor");
          //editMi.setBounds(0, 0, 40, 15);
          //editMi.setIcon(imageExpand);
          pop.add(editMi);
          pop.add(layoutMi);
          layoutMi.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
                /*TFLayoutManager tflm = new TFLayoutManager(500, 600, gr.parent,gr.conn,parent.Tf_id, gr.PR_id);
                Settings.Center(tflm);
                tflm.show();*/
            }
        });
      }
    
    private class DataPanel extends JPanel
    {
        private int width;
        private int height;
        private TobPanel tp;
        
        public DataPanel(int _width, int _height, TobPanel _tp)
        {
            super();  
            setLayout(null);
            width = _width;
            height = _height;
            tp = _tp;
            this.setSize(_width, height);
            setBackground(Color.white);
        }
        
        public void paint(Graphics g)
        {
            super.paint(g);
            TobDispProperties tdp = (TobDispProperties)tlm.propVec.get(index);
            
            if ( tdp.dLayCombo.getSelectedIndex() == 0)
            {
                DrawField(g);
            }
            else
            {
                DrawTabular(g);
            }
            
        }
        
        
        /**********************************************************************/
        /********              Crtanje izgleda                  ***************/
        /**********************************************************************/
        private void DrawTabular(Graphics g)
        {
            int fieldNum = (this.getWidth() - 8) / (tfW + 2);
            
            int offsetX = ( this.getWidth() - fieldNum * (tfW + 2) + 2) / 2;
            
            int i;
            int beginX = offsetX;
            int beginY = 5;
            
            g.setColor(Color.darkGray);
            
            for(i = 0; i < fieldNum; i++)
            {
                g.fillRect(beginX, beginY, tfW, tfH);
                beginX = beginX + tfW + 2;
            }
            
            beginY = beginY + tfH + 2;
            
            g.setColor(Color.black);
            
            g.fillRect(offsetX - 2, beginY, this.getWidth() - 2*offsetX + 4, 2);
            
            g.setColor(Color.lightGray);
            
            beginY = beginY + 4;
            
            int h = this.getHeight() - beginY;
            
            int rowNum = (h - 1) / (tfH + 2);
            
            int j = 0;
            
            for(j = 0; j < rowNum; j++)
            {
                beginX = offsetX;
                
                for(i = 0; i < fieldNum; i++)
                {
                    g.fillRect(beginX, beginY, tfW, tfH);
                    beginX = beginX + tfW + 2;
                }
                
                beginY = beginY + tfH + 2;
                
            }
            
        }
        
        private void DrawField(Graphics g)
        {
            int fieldNum = 2;
            
            int offsetX = ( this.getWidth() - 75) / 2;
            int offsetY = 5;
            
            int i;
            int beginX = offsetX;
            int beginY = 10;
            int fH = 5;
            
            
            int h = this.getHeight() - beginY;
            
            int rowNum = (h - 1) / (fH + 10);
            
            int j = 0;
            
            g.setFont(new Font("SansSerif", Font.BOLD, 11));
            
            
            j = 0;
            
            while (beginY + fH + 2 < getHeight()) //for(j = 0; j < rowNum; j++)
            {
                
                beginX = offsetX;
                g.setColor(Color.black);
                g.drawString("Label", beginX, beginY + 7);
                g.drawRect(beginX + 35, beginY, 40, 7);
                g.setColor(Color.lightGray);
                
                g.fillRect(beginX + 35, beginY, 40, 7);
                beginY = beginY + fH + 10;
                
            }
        }
        
    }
     
    private class HeaderPanel extends JPanel
    {
          private int width;
          private int height;
          private TobPanel tp;
          
          public HeaderPanel(int _width, int _height, TobPanel _tp)
          {
              super();  
              setLayout(null);
              width = _width;
              height = _height;
              tp = _tp;
              this.setSize(width, height);
              setBackground(Color.white);
          }
          
          public void paint(Graphics g)
          {
              
              
              Graphics2D g2 = (Graphics2D)g;
              
              TobDispProperties tdp = (TobDispProperties)tlm.propVec.get(index);
              super.paint(g2);
              
              if (tdp.layCombo.getSelectedIndex() == 0)
              {
                  //g2.drawRect(0, 0, getWidth() - 1, 18 - 1);
                  GradientPaint gp = new GradientPaint(10,0,Color.white,this.getWidth(), 20, gradColor, false);
                  g2.setPaint(gp);
                  g2.fillRect(0, 0, this.getWidth() , 20);
                  g.setColor(Color.BLACK);
                  g.drawLine(0, 19, this.getWidth(), 19);
                  g.setFont(new Font("SansSerif",Font.BOLD,10));
                  g.drawString(Tob_mnem,17, 13);
                  g.drawImage(image,1, 1, 17, 17,this);
              }
              else
              {   
                  g.setColor(Color.BLACK);
                  g.setFont(new Font("SansSerif",Font.BOLD,10));
                  g.drawString(Tob_mnem,3, 13);
              }
              
              dp.repaint();
              
          }
          
      } 
  }