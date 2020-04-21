package iisc.callgraph;

import iisc.*;
import iisc.tflayoutmanager.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.sql.*;

public class FrmMainPanel extends JPanel implements MouseListener,MouseMotionListener,ImageObserver
{
    
    private FormPanel parent;
    public CrossPanel cp;
    private boolean mDown = false;
    private JPopupMenu pop;
    private JMenuItem editMi;
    private JMenuItem layoutMi;
    private JMenuItem markMi;
    private JMenuItem removeMi;
    private int oldX;
    private int oldY;
    private int deltax;
    private int deltay;
    private Image image;
    private CallingGraph gr;
    private int offsetX;
    private int offsetY;
    
    public int x1;
    public int y1;
    public int x2;
    public int y2;
    private boolean dragged = false;
    public static ImageIcon imageEdit = new ImageIcon(IISFrameMain.class.getResource("icons/edit.gif"));
    public static ImageIcon imageRemove = new ImageIcon(IISFrameMain.class.getResource("icons/remove2.gif"));
    
    public FrmMainPanel(FormPanel _parent)
    {
        super();  
        gr = _parent.parent;
        
        if (_parent.type == 1)
        {
            image = gr.imageMenu.getImage();
            
        }
        else
        {
            image = gr.imageForm.getImage();
        }
        parent = _parent;
        //setBounds(0, 0, mWidth, mHeight);
        
        setLayout(null);
        setBorder(new LineBorder(Color.black, 1));
        setBackground(SystemColor.lightGray);
        addMouseListener(this);
        addMouseMotionListener(this);
        cp = new CrossPanel(this);
        add(cp);
        //this.setOpaque(false);
        revalidate();
        
    }
    
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        
        super.paint(g2);
        
         // Fill with a gradient.
        GradientPaint gp = new GradientPaint(0,0,Color.white,this.getWidth(), this.getHeight(),  parent.color, false);
        g2.setPaint(gp);
        g2.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
        
        g2.setColor(Color.black);
        g2.setFont(new Font("SansSerif",1,12));
        g2.drawString(parent.Tf_mnem,22, 17);
        g2.drawImage(image,3, 20, this);
        super.paintChildren(g2);
        /*
        if(line)
        {
            g.drawLine(lX,lY,lineEX,lineEY);
        }
        */
    }
    /**********************************************************************/
    /********               Event handleri                  ***************/
    /**********************************************************************/
     public void mouseReleased(MouseEvent e)
     {
        if (gr.drawingPanel.line)
        {
            int x = gr.drawingPanel.lineX;
            int y = gr.drawingPanel.lineY;
            
            Vector fpVec = gr.tfVec;
            
            int size = fpVec.size();
            FormPanel fp;
            
            for(int i = 0; i < size; i++)
            {
                fp = (FormPanel)fpVec.get(i);
                
                if (parent.index != fp.index)
                {
                    if ((gr.drawingPanel.lineX > fp.x) && (gr.drawingPanel.lineX < fp.x + fp.mPanel.getWidth()))
                    {
                        if ((gr.drawingPanel.lineY > fp.y) && (gr.drawingPanel.lineY < fp.y + fp.mPanel.getHeight()))
                        {
                            
                            try
                            {
                                String str=new String();
                                ResultSet rs,rs1;
                                Statement statement = gr.conn.createStatement();
                                Statement statement2 = gr.conn.createStatement();
                                
                                rs = statement.executeQuery("select * from IISC_CALL_GRAPH_VERTEX  where caller_Tf=" + parent.Tf_id + " and called_Tf=" + fp.Tf_id + "and PR_id=" + gr.PR_id + " and BA_id=" + gr.BA_id);
                                
                                if(rs.next())
                                {
                                    JOptionPane.showMessageDialog(this,"Calling structure allready exists","Message",JOptionPane.WARNING_MESSAGE);        
                                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                    gr.drawingPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                    parent.parent.setSelected(-1);
                                    gr.drawingPanel.line = false;
                                    gr.pLine.setBorder(BorderFactory.createEmptyBorder());
                                    gr.drawingPanel.repaint();
                                    parent.repaint();
                                    return;
                                }
                                
                                PassedValuePanel pp = new PassedValuePanel(gr.parent,gr.conn,gr.PR_id,parent.Tf_id,fp.Tf_id,-1, gr.BA_id);
                                Settings.Center(pp);
                                pp.setVisible(true);
                                
                                if (pp.action == PassedValuePanel.OK)
                                {
                                    pp.Update();
                                    LineSt st = new LineSt(parent.index,fp.index,gr.drawingPanel.startX , gr.drawingPanel.startY, gr.drawingPanel.lineX, gr.drawingPanel.lineY, pp.CS_id, gr);
                                    st.UpdateLeft();
                                    st.UpdateRight();
                                    gr.lineVec.add(st);
                                    
                                    statement.execute("insert into IISC_CALL_GRAPH_VERTEX(CS_id,caller_Tf,called_Tf,X1,Y1,X2,Y2,visible,PR_id,BA_id) values(" + st.CS_id + "," + parent.Tf_id + "," + fp.Tf_id + "," + st.x1 + "," + st.y1 + "," + st.x2 + "," + st.y2 + ",1," + gr.PR_id + "," + gr.BA_id + ")");
                                    
                                    gr.InsertNode("From:" + parent.Tf_mnem + "_To:" + fp.Tf_mnem);
                                }
                                pp.dispose();
                                statement.close();
                                break;
                            } 
                            
                            catch(SQLException ex)
                            {
                                System.out.println(ex.toString());
                            } 
                        }
                    }
                }
            }
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            gr.drawingPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            parent.parent.setSelected(-1);
            gr.drawingPanel.line = false;
            gr.pLine.setBorder(BorderFactory.createEmptyBorder());
            gr.drawingPanel.repaint();
            parent.repaint();
        }
        else
        {
            if (dragged)
            {
                if( gr.grid_snap == 1)
                {
                    offsetX = offsetX - (parent.x - (parent.x / gr.grid_size)*gr.grid_size);
                    offsetY = offsetY - (parent.y - (parent.y / gr.grid_size)*gr.grid_size);
                    parent.x = (parent.x / gr.grid_size)*gr.grid_size;
                    parent.y = (parent.y / gr.grid_size)*gr.grid_size;
                    parent.setLocation(parent.x,parent.y);
                }
                dragged = false;
                gr.dirty = true;
                parent.dirty = true;
                gr.AdjustLine(parent.index, offsetX, offsetY);
                parent.parent.setSelected(parent.index);
            }
            else
            {
                gr.AdjustLine(parent.index);
            }
            gr.drawingPanel.dragIndex = -1;
            gr.drawingPanel.line = false;
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            
        }
     }
    
      public void mouseClicked(MouseEvent e)
      {
          if (e.getClickCount() > 1)
          {
              TFProperties tf = new TFProperties(parent.getCallingGraph().parent,gr.conn, parent.Tf_id);
              Settings.Center(tf);
              tf.setVisible(true);
              gr.drawingPanel.repaint();
              return;
          }
          
          if((e.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
          {
              SetPopUpMenu();
              pop.show(this,e.getX()+10, e.getY());
          }
      }
      
      public void mouseMoved(MouseEvent e)
      {
          
      }
    
      public void mouseDragged(MouseEvent e)
      {
          if (gr.drawingPanel.line)
          {
              gr.drawingPanel.lineX = parent.getX() + e.getX();
              gr.drawingPanel.lineY = parent.getY() + e.getY();
              gr.drawingPanel.repaint();
              return;
          }
          deltax = (e.getX() - oldX);
          deltay = (e.getY() - oldY);
          
          if ((parent.x + deltax < 0) || (parent.y + deltay < 0))
          {
              return;
          }
          
          if ((parent.x + deltax > gr.drawingPanel.getWidth()) || (parent.y + deltay > gr.drawingPanel.getHeight()))
          {
              return;
          }
          offsetX = offsetX + deltax;
          offsetY = offsetY + deltay;
          dragged = true;
          gr.drawingPanel.dragIndex = parent.index;
          
          parent.x = parent.x + deltax;
          parent.y =  parent.y + deltay;
          
          if ((parent.x <= gr.drawingPanel.getVisibleRect().getX()) || (parent.x + parent.mPanel.getWidth() >= gr.drawingPanel.getVisibleRect().getX() + gr.drawingPanel.getVisibleRect().getWidth()))
          {
          
              gr.drawingPanel.scrollRectToVisible(new Rectangle((int)gr.drawingPanel.getVisibleRect().getX() + deltax,(int)gr.drawingPanel.getVisibleRect().getY(), (int)gr.drawingPanel.getVisibleRect().getWidth(), (int)gr.drawingPanel.getVisibleRect().getHeight()));
              //drawingPanel.repaint();
          }
          else
          {
              if ((parent.y + parent.getHeight()> gr.drawingPanel.getVisibleRect().getY() + gr.drawingPanel.getVisibleRect().getHeight()) || (parent.y < gr.drawingPanel.getVisibleRect().getY()))
              {
              
                  gr.drawingPanel.scrollRectToVisible(new Rectangle((int)gr.drawingPanel.getVisibleRect().getX(),(int)gr.drawingPanel.getVisibleRect().getY() + deltay, (int)gr.drawingPanel.getVisibleRect().getWidth(), (int)gr.drawingPanel.getVisibleRect().getHeight()));
                  //drawingPanel.repaint();
              }
          }
          parent.setLocation(parent.x,parent.y);
          gr.setSelected(-1);
          gr.drawingPanel.repaint(parent.x - deltax, parent.y - deltay, parent.getWidth() + deltay, parent.getHeight() + deltay);
          
      }
      
      public void mouseEntered(MouseEvent e)
      {
          if (gr.drawingPanel.line)
          {
              setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
              //lX = e.getX();
              //lY = e.getY();
          }
          else
          {
              setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
          }
          
      }
      
      public void mousePressed(MouseEvent e)
      {
          if (gr.drawingPanel.line)
          {
              gr.drawingPanel.startX = parent.getX() + e.getX();
              gr.drawingPanel.startY = parent.getY() + e.getY();
              
              return;
          }
          
          mDown = true;
          oldX = e.getX();
          oldY = e.getY();
          offsetX = 0;
          offsetY = 0;
          parent.parent.setSelected(parent.index);
          this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
          
      }
    
      public void mouseExited(MouseEvent e)
      {
      }
      
      private void Expand()
      {
          parent.expanded = true;
          parent.Readjust();   
      }
    
      private void Collapse()
      {
          parent.expanded = false;
          parent.Readjust();
      }
      
      private void SetPopUpMenu()
      {
          pop = new JPopupMenu();
          editMi = new JMenuItem("Edit");
          removeMi = new JMenuItem("Remove");
          layoutMi = new JMenuItem("Layout Editor");
          markMi = new JMenuItem("Mark as EntryPoint");
          editMi.setBackground(Color.white);
          layoutMi.setBackground(Color.white);
          markMi.setBackground(Color.white);
          editMi.setIcon(imageEdit);
          removeMi.setBackground(Color.white);
          removeMi.setIcon(imageRemove);
          
          layoutMi.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
                TFLayoutManager tflm = new TFLayoutManager(500, 600, gr.parent,gr.conn,parent.Tf_id, gr.PR_id);
                Settings.Center(tflm);
                tflm.InitFirst();
                tflm.setVisible(true);
            }
          });
    
          markMi.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
                if (gr.entryIndex == parent.index)
                {
                    return;
                }
                
                FormPanel fp = (FormPanel)gr.tfVec.get(gr.entryIndex);
                fp.color = CallingGraph.unmarkColor;
                fp.repaint();
                
                parent.color = CallingGraph.markColor;
                gr.entryIndex = parent.index;
                gr.UpdateEntryPoint();
                parent.repaint();
                
            }
          });
          
          editMi.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
                gr.EditTf();
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
          pop.addSeparator();
          pop.add(layoutMi);
          pop.add(markMi);
      }
    
      public class CrossPanel extends JLabel implements MouseListener
      {
          private int state;
          private FrmMainPanel fp;
          private boolean mDown = false;
          private BorderLayout brLayout = new BorderLayout();
          
          private CrossPanel(FrmMainPanel _fp)
          {
              state = -1;    
              fp = _fp;
              setBackground(SystemColor.lightGray);
              
              setBorder(BorderFactory.createEmptyBorder());
              setLayout(brLayout);
              
              setIcon(gr.imageExpand);
              
              addMouseListener(this);
              
              
              setSize(15, 15);
              setBounds(5, 5, 15, 15);
              
              
          }
          
          public void SetState(int newState)
          {
              state = newState;
              if (state == 1)
              {
                  setIcon(gr.imageCollapse);
              }
              else
              {
                  setIcon(gr.imageExpand);
              }
              this.repaint();
              
          }
          public void mousePressed(MouseEvent e)
          {
              state = state * (-1);
              mDown = true;
              setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
              fp.parent.dirty = true;
              fp.parent.parent.dirty = true;
              
              if (state == 1)
              {
                  fp.Expand();
                  setIcon(gr.imageCollapse);
              }
              else
              {
                  fp.Collapse();
                  setIcon(gr.imageExpand);
              }
              this.repaint();
          }
          
          public void mouseReleased(MouseEvent e)
          {
              mDown = false;
              setBorder(BorderFactory.createEmptyBorder());
          }
          
          public void mouseClicked(MouseEvent e)
          {
              
          }
          
          public void mouseEntered(MouseEvent e)
          {
              if (mDown)
              {
                  return;
              }
              
              setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
          }
          
          public void mouseExited(MouseEvent e)
          {
              setBorder(BorderFactory.createEmptyBorder());
          }
      }
  }