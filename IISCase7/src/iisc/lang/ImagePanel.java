package iisc.lang;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class ImagePanel extends JLabel implements ImageObserver, MouseListener
    {    
      Image mImage;
      int x;
      int y;
      int w;
      int h;
      JLabel label = new JLabel();
      
      public ImagePanel(ImageIcon p_Icon, int p_X, int p_Y)
      {
        this.setLayout(null);
        mImage = p_Icon.getImage();
        x = p_X;
        y = p_Y;
        this.setIcon(p_Icon);
        this.addMouseListener(this);
      }
    
      /*@Override 
      public void paint(Graphics g)
      {
          super.paint(g);
          if (this.isEnabled())
          {
            g.drawImage(mImage, x, y, this);
          }
      }*/
      
      public void setX(int p_X)
      {
         x = p_X;
      }
    
      public void setY(int p_Y)
      {
          y = p_Y;
      }
      
      public void setImage(Image img)
      {
          mImage = img;
          repaint();
      }

    public void mouseClicked(MouseEvent e) 
    {
        
    }

    public void mousePressed(MouseEvent e) 
    {
        if (!this.isEnabled())
        {
            return;
        }
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    }

    public void mouseReleased(MouseEvent e) 
    {
        if (!this.isEnabled())
        {
            return;
        }
        setBorder(BorderFactory.createEmptyBorder());
    }

    public void mouseEntered(MouseEvent e) 
    {
        if (!this.isEnabled())
        {
            return;
        }
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }

    public void mouseExited(MouseEvent e) 
    {
        if (!this.isEnabled())
        {
            return;
        }
        setBorder(BorderFactory.createEmptyBorder());
    }
}

