package iisc.tflayoutmanager;

import iisc.IISFrameMain;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.image.ImageObserver;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;

public class LayoutPanel extends JPanel
{
    public TFLayoutManager tfl;
    public int oldSelected = -1;
    public int selected = -1;
    public boolean square = false;
    public static int beginX = 50;
    public static int beginY = 50;
    public static int sqWidth = 250;
    public static int sqHeight = 250;
    public static int boundX = 71;
    public static int boundY = 74;
    public static int boundW = 208;
    public static int boundH = 197;
    private JPanel winPanel = new HeaderPanel();
    public static ImageIcon imageClose = new ImageIcon(IISFrameMain.class.getResource("icons/closefrm.gif"));
    public static ImageIcon imageMaxi = new ImageIcon(IISFrameMain.class.getResource("icons/maxifrm.gif"));
    public static ImageIcon imageMini = new ImageIcon(IISFrameMain.class.getResource("icons/minimfrm.gif"));
    public static ImageIcon imageScreen = new ImageIcon(IISFrameMain.class.getResource("icons/screeen.gif"));
    private ImagePanel imClose = new ImagePanel(imageClose, 1, 1);
    private Image imScreen;
    private JPanel xPanel = new XPanel(15, 15);
    private JPanel minPanel = new MinPanel(15, 15);
    private JPanel maxPanel = new MaxPanel(15, 15);
    private int len;
    
    public LayoutPanel(TFLayoutManager _tfl)
    {
        setLayout(null);
        imScreen = imageScreen.getImage();
        this.setBackground(Color.WHITE);
        tfl = _tfl;
        winPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        //winPanel.setBackground(new Color(198, 195, 198));
        len = tfl.propVec.size();
        repaint();
        revalidate();    
    }
    
    public void setSelected(int index)
    {
        oldSelected = selected;
        selected = index;
        int parentInd = -1;
        
        TobDispProperties tdp = (TobDispProperties)tfl.propVec.get(selected);
        
        if (tdp.layCombo.getSelectedIndex() == 0)
        {
            square = true;
            TobPanel tobP = (TobPanel)tfl.tobPanelVec.get(selected);
            removeAll();
            InitPanel(selected);
            tobP.setBounds(tobP.x, tobP.y, tobP.width, tobP.height);
            tobP.SetHeader(tobP.getWidth());
            RearangePanel(selected);
            add(tobP);
        }
        else
        {
        
            //Nalazi prvog roditelja koji je new Window
            while( true )
            {
                
                tdp = (TobDispProperties)tfl.propVec.get(selected);
                
                if (tdp.layCombo.getSelectedIndex() == 0)
                {
                    break;
                }
                
                parentInd = tdp.parentInd;
                
                if ( parentInd == -1)
                {
                    break;
                }
                selected = parentInd;
                
            }
        
            this.removeAll();
            TobPanel tobP = (TobPanel)tfl.tobPanelVec.get(selected);
            
            InitPanel(selected);
            square = true;
            
            /*winPanel.setBounds(beginX, beginY- 22, tobP.width, 23 );
            
            winPanel.removeAll();
            winPanel.setLayout(null);
            
            xPanel.setBounds(winPanel.getWidth() - 18, 4, 15, 15);
            minPanel.setBounds(winPanel.getWidth() - 35, 4, 15, 15);
            maxPanel.setBounds(winPanel.getWidth() - 52, 4, 15, 15);
            winPanel.add(xPanel);
            winPanel.add(minPanel);
            winPanel.add(maxPanel);
            winPanel.revalidate();
            add(winPanel);
            */
            tobP.setBounds(tobP.x, tobP.y,tobP.width, tobP.height);
            tobP.SetHeader(tobP.getWidth());
            RearangePanel(selected);
            add(tobP);
        }
        
        
        
        repaint();
    }
    
    
    //Ovo je krucijalna procedura
    public void InitPanel(int index)
    {
        int prevX;
        int prevY;
        int prevW = 120;
        int prevH = 45;
        int maxX = 120;
        int maxY = 70;
        
        int beginY;
        int lastDown = -1;
        /*int lastRight = -1;
        int maxWithDown = 120;
        int maxHeightRight = 75;
        */
        TobDispProperties tdp;
        TobDispProperties ctdp;
        TobPanel tp, tpc;
        
        Vector vec;
        int i, j;
        
        len = tfl.propVec.size();
        
        tdp = (TobDispProperties)tfl.propVec.get(index);
        tp = (TobPanel)tfl.tobPanelVec.get(index);
        
        if ( tdp.layCombo.getSelectedIndex() == 0)
        {
            beginY = 25;
        }
        else
        {
            beginY = 15;
        }
        
        prevY = beginY;
        prevX = 5;
        tp.width = 120;
        tp.height = 70;
        
        vec = tdp.children;
        
         //Prvo slozi one koje su dolje
        for(i = 0; i < vec.size(); i++)
        {
            //Treba provjeriti sve njegove potomke
            j = ((Integer) vec.get(i)).intValue();
            
            ctdp = (TobDispProperties)tfl.propVec.get(j);
            tpc = (TobPanel)tfl.tobPanelVec.get(j);
            
            //Ako se potomak nalazi u istom prozoru
            if ( ctdp.layCombo.getSelectedIndex() == 1)
            {
                //Provjera da li je se pozicionira dolje ili gore
                if ( ctdp.posCombo.getSelectedIndex() == 0)//Ovo je dolje
                {
                    InitPanel(j);
                    lastDown = j;
                    
                    tpc.x = prevX;
                    tpc.y = prevY + prevH + 5;
                    tpc.setBounds(prevX, prevY + prevH + 5,  tpc.width, tpc.height);
                    tp.add(tpc);
                    
                    prevY = prevY + prevH + 5;
                    prevW = tpc.width;
                    prevH = tpc.height;
                    
                    if (prevY + prevH + 5 > maxY)
                    {
                        maxY = prevY + prevH + 5;
                    }
                    
                    if (prevX + prevW + 5 > maxX)
                    {
                        maxX = prevX + prevW + 5;
                    }
                }
                else //ako je desno od njega onda je to super
                {
                    InitPanel(j);
                    lastDown = j;
                    
                    tpc.x = prevX + prevW + 5;
                    tpc.y = prevY;
                    tpc.setBounds(tpc.x, prevY, tpc.width, tpc.height);
                    tp.add(tpc);
                    
                    prevX = prevX + prevW + 5;
                    prevW = tpc.width;
                    prevH = tpc.height;
                    
                    if (prevX + prevW + 5 > maxX)
                    {
                        maxX = prevX + prevW + 5;
                    }
                    
                    if (prevY + prevH + 5 > maxY)
                    {
                        maxY = prevY + prevH + 5;
                    }
                }
            }    
        }
        
        
        tp.SetDataB(beginY, 110, 45);
        
        tp.width = maxX;
        tp.height = maxY;
            
    }
    
    
    //Vrsi peglanje rjesenja
     //Ovo je krucijalna procedura
     public void RearangePanel(int index)
     {
         int beginY;
         int k; 
         
         TobDispProperties tdp;
         TobDispProperties ctdp;
         
         TobDispProperties ctdpNext = null;
         
         TobPanel tp, tpc;
         
         Vector vec;
         int i, j, p;
         
         len = tfl.propVec.size();
         
         tdp = (TobDispProperties)tfl.propVec.get(index);
         tp = (TobPanel)tfl.tobPanelVec.get(index);
         
         if ( tdp.layCombo.getSelectedIndex() == 0)
         {
             beginY = 25;
         }
         else
         {
             beginY = 15;
         }
         
         vec = tdp.children;
         i = 0;
         
         while ( i < vec.size())
         {
             
             j = ((Integer) vec.get(i)).intValue();
             
             ctdp = (TobDispProperties)tfl.propVec.get(j);
             tpc = (TobPanel)tfl.tobPanelVec.get(j);
             
             //Ako se potomak nalazi u istom prozoru
             if ( ctdp.layCombo.getSelectedIndex() == 0)
             {
                i = i + 1;
                continue;
             }
             
             //Treba provjeriti sve njegove potomke
             k = i + 1;
             
             while( k < vec.size())
             {
                 p = ((Integer) vec.get(k)).intValue();
                 ctdpNext = (TobDispProperties)tfl.propVec.get(p);
                 
                 if ( ctdpNext.layCombo.getSelectedIndex() == 1)
                 {
                    break;
                 }
                 k = k + 1;
                 
             }
             
             if ( k < vec.size()) //Ako nije poslednji u redosledu
             {
                 if ( ctdpNext.posCombo.getSelectedIndex() == 0)//Ako se sledeci nalazi dolje onda ga prosiri do kraja
                 {
                    tpc.width = tp.width - tpc.x - 5;
                    tpc.setBounds(tpc.x, tpc.y,tpc.width, tpc.height);
                 }
                 else
                 {
                     tpc.height = tp.height - tpc.y - 5;
                     tpc.setBounds(tpc.x, tpc.y,tpc.width , tpc.height);
                 }
             }
             else //Ako jeste onda je to super
             {
                 tpc.width = tp.width - tpc.x - 5;
                 tpc.height = tp.height - tpc.y - 5;
                 tpc.setBounds(tpc.x, tpc.y,tpc.width , tpc.height);
             }
             RearangePanel(j);
             i = k;
             
         }
         
         if (vec.size() > 0)//Ako ima nekog potomka provjeri o kome se radi 
         {
             j = ((Integer) vec.get(0)).intValue();
             
             ctdpNext = (TobDispProperties)tfl.propVec.get(j);
             
             if ( ctdpNext.posCombo.getSelectedIndex() == 0)//Ako se sledeci nalazi dolje onda ga prosiri do kraja
             {
                 tp.SetDataB(beginY, tp.width - 10, 45);
             }
             else
             {
                 tp.SetDataB(beginY, 115, tp.height - beginY - 5);
             }
             
         }
         else
         {
             tp.SetDataB(beginY, tp.width - 10, tp.height - beginY - 5);
         }
         
         tp.revalidate();
         tp.repaint();
     }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        if (square)
        {
            //g.drawRect(beginY, beginY, sqWidth, sqHeight);
            g.drawImage(imScreen,beginX, beginY, sqWidth, sqWidth, this);
        }
        
        this.paintChildren(g);
    }
    
    public class ImagePanel extends JPanel implements ImageObserver
    {
  
      Image mImage;
      int x;
      int y;
    
      public ImagePanel(ImageIcon p_Icon, int p_X, int p_Y)
      {
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.setBackground(new Color(198, 195, 198));
        mImage = p_Icon.getImage();
        x = p_X;
        y = p_Y;
      }
    
      public void paint(Graphics g)
      {
          super.paint(g);
        
          g.drawImage(mImage, x, y, mImage.getWidth(this), mImage.getHeight(this), this);
      }
      public void setX(int p_X)
      {
         x = p_X;
      }
    
      public void setY(int p_Y)
      {
          y = p_Y;
      }
    }
    
    public class XPanel extends JPanel
    {
        int width;
        int height;

        public XPanel(int p_width, int p_height)
        {
            super();
            setBorder(new BevelBorder(BevelBorder.RAISED,Color.lightGray, Color.darkGray));
            //setBorder(BorderFactory.createEtchedBorder());
            setBackground(Color.lightGray);
            width = p_width;
            height = p_height;
        }

        public void paint(Graphics g)
        {
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g;

            g2d.setStroke(new BasicStroke(2f));
            g2d.drawLine(5, 5, width - 5, height - 5);
            g2d.drawLine(width - 5, 5, 5, height - 5);
        }
    }
    
    public class MinPanel extends JPanel
    {
        int width;
        int height;

        public MinPanel(int p_width, int p_height)
        {
            super();
            setBorder(new BevelBorder(BevelBorder.RAISED,Color.lightGray, Color.darkGray));
            //setBorder(BorderFactory.createEtchedBorder());
            setBackground(Color.lightGray);
            width = p_width;
            height = p_height;
        }

        public void paint(Graphics g)
        {
            super.paint(g);
            
            g.drawRect(2, 2, width - 6, height - 6);
            g.fillRect(2, 2, width - 6, 3);
            /*Graphics2D g2d = (Graphics2D)g;

            g2d.setStroke(new BasicStroke(1f));
            
            g2.
            g2d.drawLine(6, 6, width - 6, height - 6);
            g2d.drawLine(width - 6, 6, 6, height - 6);*/
        }
    }
    
    public class MaxPanel extends JPanel
    {
        int width;
        int height;

        public MaxPanel(int p_width, int p_height)
        {
            super();
            setBorder(new BevelBorder(BevelBorder.RAISED,Color.lightGray, Color.darkGray));
            //setBorder(BorderFactory.createEtchedBorder());
            setBackground(Color.lightGray);
            width = p_width;
            height = p_height;
        }

        public void paint(Graphics g)
        {
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g;

            g2d.setStroke(new BasicStroke(2f));
            g2d.drawLine(4, height - 5, width - 5, height - 5);
            
        }
    }
    
    public class HeaderPanel extends JPanel
    {
        int width;
        int height;

        public HeaderPanel()
        {
            super();
            setLayout(null);
            setBorder(new LineBorder(Color.black, 1));
            setBackground(Color.lightGray);
        }

        public void paint(Graphics g)
        {
            super.paint(g);
            Graphics2D g2 = (Graphics2D)g;
            GradientPaint gp = new GradientPaint(0,0, Color.white ,this.getWidth() - 2, 18, SystemColor.textHighlight, false);
            g2.setPaint(gp);
            g2.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
            super.paintChildren(g);

        }
    }

  public LayoutPanel()
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
  {
  }
}