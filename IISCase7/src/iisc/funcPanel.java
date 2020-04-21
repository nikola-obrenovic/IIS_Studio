package iisc;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Insets;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;

public class funcPanel extends funcElement//JPanel 
{
//  public int level = 0; //nivo dubine
//  public int pozition = -1; // pozicija u array
//  public int father = -1;  //otac cvora
  public JLabel name = new JLabel();  
//  private int copypressed;
//  private int copyreleased;
//  public String delFlag="";
//  private String toDo = "";
  private ImageIcon logo = new ImageIcon(IISFrameMain.class.getResource("icons/bl.gif"));  
  private ImageIcon logo2 = new ImageIcon(IISFrameMain.class.getResource("icons/bl3.gif"));  
  private ImageIcon question = new ImageIcon(IISFrameMain.class.getResource("icons/question.gif"));
  private ImageIcon truee = new ImageIcon(IISFrameMain.class.getResource("icons/true.gif"));  
  private ImageIcon falsee = new ImageIcon(IISFrameMain.class.getResource("icons/false.gif"));  
  private ImageIcon forr = new ImageIcon(IISFrameMain.class.getResource("icons/for2.gif"));  
  private ImageIcon whilee = new ImageIcon(IISFrameMain.class.getResource("icons/while.gif"));  
  private ImageIcon dowhile = new ImageIcon(IISFrameMain.class.getResource("icons/dowhile.gif")); 
  private ImageIcon exception = new ImageIcon(IISFrameMain.class.getResource("icons/exception.gif")); 
  private ImageIcon help = new ImageIcon(IISFrameMain.class.getResource("icons/if2.gif")); 
  private ImageIcon function = new ImageIcon(IISFrameMain.class.getResource("icons/fung.gif")); 
  private ImageIcon comments = new ImageIcon(IISFrameMain.class.getResource("icons/comments.gif")); 
  private ImageIcon declaration = new ImageIcon(IISFrameMain.class.getResource("icons/declaration.gif"));    
  public JButton jButton1 = new JButton();
  private JPopupMenu jPopupMenu1 = new JPopupMenu();
  private JMenuItem jMenuItem2 = new JMenuItem();
  private JMenuItem jMenuItem3 = new JMenuItem();
  private JMenuItem jMenuItem4 = new JMenuItem();
  private JMenuItem jMenuItem5 = new JMenuItem();
  private JMenuItem jMenuItem6 = new JMenuItem();
  private JMenu jMenu1 = new JMenu();
  private JMenuItem jMenuItem1 = new JMenuItem();
  private JMenuItem jMenuItem7 = new JMenuItem();
  private JMenuItem jMenuItem8 = new JMenuItem();
  private JMenuItem jMenuItem9 = new JMenuItem();
  private JMenuItem jMenuItem10 = new JMenuItem();
  private JMenuItem jMenuItem11 = new JMenuItem();
//  private funAlg f = null;
  
  
  
private ImageIcon cut = new ImageIcon(IISFrameMain.class.getResource("icons/cut.gif"));   
private ImageIcon copy = new ImageIcon(IISFrameMain.class.getResource("icons/copy.gif"));   
private ImageIcon paste = new ImageIcon(IISFrameMain.class.getResource("icons/paste.gif"));   
private ImageIcon erase = new ImageIcon(IISFrameMain.class.getResource("icons/erase.gif"));
private ImageIcon neww = new ImageIcon(IISFrameMain.class.getResource("icons/new.gif"));
private ImageIcon action = new ImageIcon(IISFrameMain.class.getResource("icons/ll.gif"));

  public funcPanel(int poz,int fat,int l,funAlg fu)
  {
    super(poz,fat,l,fu);  
    //f = fu;
    //pozition = poz;
    //father = fat;
    //level = l;
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
    jMenuItem7.setIcon(action);
    this.setSize(new Dimension(380, 143));
    this.setLayout(null);
    this.setPreferredSize(new Dimension(1, 1));
    this.setBorder(BorderFactory.createLineBorder(new Color(216, 216, 216), 1));
    this.setBackground(Color.white);
    this.setAutoscrolls(true);
    this.setBounds(new Rectangle(10, 10, 350, 30));

    this.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mouseEntered(MouseEvent e)
        {
          this_mouseEntered(e);
        }

        public void mouseExited(MouseEvent e)
        {
          this_mouseExited(e);
        }

        public void mouseClicked(MouseEvent e)
        {
                        this_mouseClicked(e);
                    }

        public void mousePressed(MouseEvent e)
        {
          this_mousePressed(e);
        }

        public void mouseReleased(MouseEvent e)
        {
          this_mouseReleased(e);
        }

      });
    name.setText("Name");
    name.setBounds(new Rectangle(15, 0, 365, 15));
    name.setFont(new Font("Tahoma", 1, 11));
    name.setIcon(logo2);

    name.addPropertyChangeListener(new PropertyChangeListener()
      {
        public void propertyChange(PropertyChangeEvent e)
        {
          name_propertyChange(e);
        }
      });
    jButton1.setText("-");
    jButton1.setBounds(new Rectangle(0, 0, 15, 15));
    jButton1.setMargin(new Insets(0, 1, 1, 0));
    jButton1.setHorizontalTextPosition(SwingConstants.CENTER);
    jButton1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton1_actionPerformed(e);
        }
      });
    jPopupMenu1.setLabel("jPopupMenu1");
    jMenuItem2.setText("Copy");
    jMenuItem2.setIcon(copy);
    jMenuItem2.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem2_actionPerformed(e);
        }
      });
    jMenuItem3.setText("Paste");
    jMenuItem3.setIcon(paste);
    jMenuItem3.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem3.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem3_actionPerformed(e);
        }
      });
    jMenuItem4.setText("Delete");
    jMenuItem4.setIcon(erase);
    jMenuItem4.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem4.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem4_actionPerformed(e);
        }
      });
    jMenuItem5.setText("Cut");
    jMenuItem5.setIcon(cut);
    jMenuItem5.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem5.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem5_actionPerformed(e);
        }
      });
    jMenuItem6.setText("Fully Expand");
    jMenuItem6.setHorizontalAlignment(SwingConstants.LEFT);
    jMenu1.setText("Insert");
    jMenu1.setIcon(neww);
    jMenu1.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem1.setText("Block");
    jMenuItem1.setIcon(logo2);
    jMenuItem1.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem1_actionPerformed(e);
        }
      });
    jMenuItem7.setText("Action");
    jMenuItem7.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem7.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem7_actionPerformed(e);
        }
      });
    jMenuItem8.setText("For Loop");
    jMenuItem8.setIcon(forr);
    jMenuItem8.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem8.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem8_actionPerformed(e);
        }
      });
    jMenuItem9.setText("While Loop");
    jMenuItem9.setIcon(whilee);
    jMenuItem9.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem9.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem9_actionPerformed(e);
        }
      });
    jMenuItem10.setText("If Conditional");
    jMenuItem10.setIcon(help);
    jMenuItem10.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem10.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem10_actionPerformed(e);
        }
      });
    jMenuItem11.setText("Exception");
    jMenuItem11.setIcon(exception);
    jMenuItem11.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem11.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem11_actionPerformed(e);
        }
      });
    this.add(jButton1, null);
    this.add(name, null);
    jMenu1.add(jMenuItem1);
    jMenu1.add(jMenuItem7);
    jMenu1.addSeparator();
    jMenu1.add(jMenuItem8);
    jMenu1.add(jMenuItem9);
    jMenu1.add(jMenuItem10);
    jMenu1.add(jMenuItem11);
    jPopupMenu1.add(jMenu1);
    jPopupMenu1.addSeparator();
    jPopupMenu1.add(jMenuItem5);
    jPopupMenu1.add(jMenuItem2);
    jPopupMenu1.add(jMenuItem3);
    jPopupMenu1.add(jMenuItem4);
    jPopupMenu1.addSeparator();
    jPopupMenu1.add(jMenuItem6);
  }

  private void this_mouseEntered(MouseEvent e)
  {
      //System.out.println("entered panel*****"+this.pozition+"  x:"+e.getX()+"  y:"+e.getY());         
      f.released = this.pozition;
      if ( f.clicked != this.pozition)
          this.setBackground(new Color(165,212,248));
  }

  private void this_mouseExited(MouseEvent e)
  {
      if ( f.clicked != this.pozition)
          this.setBackground(Color.WHITE);
  }

  private void this_mouseClicked(MouseEvent e)
  {
      f.clicked = this.pozition;
      System.out.println("Panel - Pozition:"+pozition+" OTAC:"+this.father+"   nivo::"+level);
      if((e.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
      {      
            System.out.println("Uradjen klik sa desnim tasterom");
            jMenu1.setEnabled(true);
            jMenuItem1.setEnabled(true);
            jMenuItem2.setEnabled(true);
            jMenuItem3.setEnabled(true);
            jMenuItem4.setEnabled(true);
            jMenuItem5.setEnabled(true);
            jMenuItem6.setEnabled(true);
            jMenuItem7.setEnabled(true);
            jMenuItem8.setEnabled(true);
            jMenuItem9.setEnabled(true);
            jMenuItem10.setEnabled(true);
            jMenuItem11.setEnabled(true);
            
            if(this.pozition == 0 )
            {
                jMenu1.setEnabled(false);
                jMenuItem1.setEnabled(false);
                jMenuItem2.setEnabled(false);
                jMenuItem3.setEnabled(false);
                jMenuItem4.setEnabled(false);
                jMenuItem5.setEnabled(false);
                //jMenuItem6.setEnabled(false);
                jMenuItem7.setEnabled(false);
                jMenuItem8.setEnabled(false);
                jMenuItem9.setEnabled(false);
                jMenuItem10.setEnabled(false);
                jMenuItem11.setEnabled(false);              
            }
            else if(this.pozition == 1)
            {
                jMenuItem5.setEnabled(false);
                jMenuItem4.setEnabled(false);              
                
                jMenuItem3.setEnabled(false);    
                if (f.copyNode != -1 || f.cutNode != -1)
                  jMenuItem3.setEnabled(true);    
                  
                jMenuItem2.setEnabled(false);    
            }
            else if( this.name.getText().equals("EXCEPTION") )
            {
                jMenuItem11.setEnabled(false);      
                jMenuItem5.setEnabled(false);
                jMenuItem3.setEnabled(false);    
                if (f.copyNode != -1 || f.cutNode != -1)
                  jMenuItem3.setEnabled(true); 
            }    
            else if( this.name.getText().equals("IF CONDITIONAL") || this.name.getText().equals("FOR LOOP") || this.name.getText().equals("WHILE LOOP") )
            {
                jMenu1.setEnabled(false);
                jMenuItem1.setEnabled(false);
                
                jMenuItem3.setEnabled(false);    
                if (f.copyNode != -1 || f.cutNode != -1)
                  jMenuItem3.setEnabled(true); 
                    
                //jMenuItem5.setEnabled(false);
                jMenuItem6.setEnabled(false);
                jMenuItem7.setEnabled(false);
                jMenuItem8.setEnabled(false);
                jMenuItem9.setEnabled(false);
                jMenuItem10.setEnabled(false);
                jMenuItem11.setEnabled(false);           
                
                if (this.name.getText().equals("IF CONDITIONAL") || this.name.getText().equals("ELSE IF CONDITIONAL"))
                {
                    jMenu1.setEnabled(true);
                    jMenuItem1.setEnabled(true);                
                    for(int i=0;i<f.fun.length;i++)
                        if(f.fun[i].getClass().toString().equals("class iisc.funcPanel") && ((funcPanel)f.fun[i]).father == this.pozition && ((funcPanel)f.fun[i]).name.getText().equals("ELSE") )
                        {
                            jMenu1.setEnabled(false);
                            jMenuItem1.setEnabled(false);
                            break;
                        }
                }                
            }
            else if( this.name.getText().equals("THEN") || this.name.getText().equals("ELSE")  )
            {
                  jMenuItem2.setEnabled(false);
                  
                  jMenuItem3.setEnabled(false);    
                  if (f.copyNode != -1 || f.cutNode != -1)
                    jMenuItem3.setEnabled(true); 
                    
                  if (this.name.getText().equals("THEN"))
                  {
                      jMenuItem4.setEnabled(false);
                      jMenuItem5.setEnabled(false);
                  }
            }            
            else if( this.name.getText().equals("BODY") && ( ((funcPanel)f.fun[this.father]).name.getText().equals("FOR LOOP") || ((funcPanel)f.fun[this.father]).name.getText().equals("WHILE LOOP") ))
            {
                      jMenuItem4.setEnabled(false);
                      jMenuItem5.setEnabled(false);
                      jMenuItem2.setEnabled(false);
                      
                      jMenuItem3.setEnabled(false);    
                      if (f.copyNode != -1 || f.cutNode != -1)
                        jMenuItem3.setEnabled(true);                       
            }            
            
             jPopupMenu1.setSelected(jPopupMenu1);
             jPopupMenu1.show(this,e.getX(),e.getY());
      }
      

        f.jButton1.setEnabled(true); 
        f.jButton2.setEnabled(true); 
        f.jButton3.setEnabled(true); 
        f.jButton4.setEnabled(true); 
        f.jButton5.setEnabled(true); 
        f.jButton6.setEnabled(true);           
        f.jButton7.setEnabled(true); 
        f.jButton8.setEnabled(true); 
        f.jButton9.setEnabled(true); 
        f.jButton10.setEnabled(true); 
        f.jButton11.setEnabled(true); 
        f.jButton12.setEnabled(true); 
        f.jButton13.setEnabled(true); 
        f.jButton14.setEnabled(true); 
        f.jButton15.setEnabled(true); 
        f.jButton16.setEnabled(true); 
        f.jButton17.setEnabled(true); 
          
          if (this.pozition == 0 || ((funcPanel)f.fun[pozition]).father == 0)
          {
                f.jButton1.setEnabled(false); 
                f.jButton2.setEnabled(false); 
                f.jButton3.setEnabled(false); 
                f.jButton4.setEnabled(false); 
                f.jButton5.setEnabled(false); 
                f.jButton6.setEnabled(false);           
                f.jButton7.setEnabled(false); 
                f.jButton8.setEnabled(false); 
                f.jButton9.setEnabled(false); 
                f.jButton11.setEnabled(false); 
                f.jButton13.setEnabled(false);
                f.jButton14.setEnabled(false); 
                f.jButton15.setEnabled(false); 
                f.jButton16.setEnabled(false); 
                f.jButton17.setEnabled(false); 
          }
          else if(this.pozition == 1)
          {
                f.jButton3.setEnabled(false); 
                f.jButton4.setEnabled(false); 
                f.jButton5.setEnabled(false); 
                f.jButton6.setEnabled(false);           
                f.jButton7.setEnabled(false); 
              f.jButton14.setEnabled(false); 
              f.jButton15.setEnabled(false); 
          }
          else if( ((funcPanel)f.fun[this.pozition]).name.getText().equals("IF CONDITIONAL") || 
                   ((funcPanel)f.fun[this.pozition]).name.getText().equals("FOR LOOP") || 
                   ((funcPanel)f.fun[this.pozition]).name.getText().equals("WHILE LOOP") || 
                    ((funcPanel)f.fun[this.pozition]).name.getText().equals("ELSE IF CONDITIONAL")
                  )
          {
                f.jButton1.setEnabled(false); 
                f.jButton2.setEnabled(false); 
                f.jButton8.setEnabled(false); 
                f.jButton9.setEnabled(false); 
              f.jButton10.setEnabled(false); 
                f.jButton11.setEnabled(false); 
                f.jButton13.setEnabled(false); 
              f.jButton14.setEnabled(false); 
              f.jButton15.setEnabled(false); 
              f.jButton16.setEnabled(false); 
              f.jButton17.setEnabled(false); 
                
                if (  ((funcPanel)f.fun[this.pozition]).name.getText().equals("IF CONDITIONAL") || 
                      ((funcPanel)f.fun[this.pozition]).name.getText().equals("ELSE IF CONDITIONAL")
                   )
                {
                    f.jButton1.setEnabled(true);
                    for(int i=0;i<f.fun.length;i++)
                        if( f.fun[i].getClass().toString().equals("class iisc.funcPanel") && 
                            ((funcPanel)f.fun[i]).father == this.pozition && 
                            ((funcPanel)f.fun[i]).name.getText().equals("ELSE") 
                          )
                        {
                            f.jButton1.setEnabled(false);                                       
                            break;
                        }
                }
          }
          else if( ((funcPanel)f.fun[this.pozition]).name.getText().equals("EXCEPTION") )
          {
                f.jButton4.setEnabled(false); 
                f.jButton5.setEnabled(false); 
                f.jButton6.setEnabled(false); 
                f.jButton7.setEnabled(false); 
                f.jButton13.setEnabled(false); 
              f.jButton14.setEnabled(false); 
              f.jButton15.setEnabled(false); 
          }
          else if(  ((funcPanel)f.fun[this.pozition]).name.getText().equals("THEN") || 
                    ((funcPanel)f.fun[this.pozition]).name.getText().equals("ELSE")  
                  )
          {
                f.jButton4.setEnabled(false); 
                f.jButton5.setEnabled(false); 
                f.jButton6.setEnabled(false); 
                f.jButton7.setEnabled(false); 
                f.jButton14.setEnabled(false); 
                f.jButton15.setEnabled(false);                 
                
                if (((funcPanel)f.fun[this.pozition]).name.getText().equals("THEN"))
                      f.jButton3.setEnabled(false);
          }
          else if(  ((funcPanel)f.fun[this.pozition]).name.getText().equals("BODY") && 
                    ( ((funcPanel)f.fun[this.father]).name.getText().equals("FOR LOOP") || 
                      ((funcPanel)f.fun[this.father]).name.getText().equals("WHILE LOOP") 
                    )
                  )
          {
                f.jButton3.setEnabled(false);                   
                f.jButton4.setEnabled(false); 
                f.jButton5.setEnabled(false); 
                f.jButton6.setEnabled(false); 
                f.jButton7.setEnabled(false); 
                f.jButton14.setEnabled(false); 
                f.jButton15.setEnabled(false);                 
          }
      else if(  ((funcPanel)f.fun[this.pozition]).name.getText().equals("COMMENTS")) 
      {
          f.jButton1.setEnabled(false); 
          f.jButton2.setEnabled(false); 
          f.jButton8.setEnabled(false); 
          f.jButton9.setEnabled(false); 
          f.jButton10.setEnabled(false); 
          f.jButton11.setEnabled(false); 
          f.jButton13.setEnabled(false); 
          f.jButton14.setEnabled(false); 
          f.jButton16.setEnabled(false);           
          f.jButton17.setEnabled(false);                     
      }
      else if(  ((funcPanel)f.fun[this.pozition]).name.getText().equals("LOCAL FUNCTION")) 
      {
          f.jButton14.setEnabled(false); 
          f.jButton15.setEnabled(false);       
      }
          
      fillPanel();
  }
  


  private void jButton1_actionPerformed(ActionEvent e)
  {
      if (jButton1.getText().toString().equals("+"))
      {
          jButton1.setText("-");      
          f.resizeHeight( this.pozition , 1 ,"l");
          f.resizeWidth();
      }
      else
      {
          jButton1.setText("+");      
          f.resizeHeight( this.pozition , 0 ,"l");
          f.resizeWidth();
      }      
  }

  private void this_mousePressed(MouseEvent e)
  {
      //System.out.println("pressed:"+this.pozition);
      f.pressed = this.pozition;
  }


  private void this_mouseReleased(MouseEvent e)
  {
    ImageIcon icon = new ImageIcon(IISFrameMain.class.getResource("icons/cursor.gif")); 
    Image a = icon.getImage();          
    f.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(a,new java.awt.Point(1,1),"test") ); 
    
    replaceNodes(e);
  }
  
  private void replaceNodes(MouseEvent e)
  {
  
      this.copypressed = f.pressed;
      this.copyreleased = f.released;
      
      boolean ok = false;
      if (copypressed == this.copyreleased)
        System.out.println("isti");
      else  if((e.getModifiers() & InputEvent.BUTTON1_MASK)== InputEvent.BUTTON1_MASK)
      {
          //int kliknut = Integer.parseInt(funAlg.kliklbl.getText());
          System.out.print("uradjen MOVE sa lijevim tasterom ");
          toDo="";
          System.out.print("razliciti ");
          System.out.print(" Pressed:"+this.copypressed); 
          System.out.println(" released:"+this.copyreleased);               
          JPopupMenu popupMenu = new JPopupMenu();    
          JMenu copy = new JMenu("Copy");
          JMenu move = new JMenu("Move");

          JMenuItem menuItem1 = new JMenuItem("Above Selected Block"); 
          JMenuItem menuItem2 = new JMenuItem("In Selected Block"); 
          JMenuItem menuItem3 = new JMenuItem("Below Selected Block"); 
          
          JMenuItem menuItem4 = new JMenuItem("Above Selected Block"); 
          JMenuItem menuItem5 = new JMenuItem("In Selected Block"); 
          JMenuItem menuItem6 = new JMenuItem("Below Selected Block"); 
          
          if(f.fun[this.copyreleased].getClass().toString().equals("class iisc.funcPanel"))
          {
                if( ((funcPanel)f.fun[this.copypressed]).name.getText().equals("THEN") &&   
                    ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("ELSE")
                   )
                {
                    if ( ((funcPanel)f.fun[this.copypressed]).father == ((funcPanel)f.fun[this.copyreleased]).father)
                    {
                        menuItem6.setText("Rotate THEN - ELSE");
                        popupMenu.add(menuItem6);
                        toDo = "rotate then->else";
                        ok = true;
                    }
                }
                else if( ((funcPanel)f.fun[this.copypressed]).name.getText().equals("ELSE") && 
                         ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("THEN")
                       )
                {
                    if ( ((funcPanel)f.fun[this.copypressed]).father==((funcPanel)f.fun[this.copyreleased]).father  )
                    {
                        menuItem4.setText("Rotate ELSE - THEN");
                        popupMenu.add(menuItem4);
                        toDo = "rotate else->then";
                        ok = true;
                    }
                }
                else if( ((funcPanel)f.fun[copypressed]).name.getText().equals("EXCEPTION") && 
                         ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("EXCEPTION")
                        )
                {
                        
                        copy.add(menuItem1);
                        copy.add(menuItem3);
                        move.add(menuItem4);
                        move.add(menuItem6);                            
                        popupMenu.add(copy);
                        popupMenu.addSeparator();
                        popupMenu.add(move);
                        ok = true;   
                }                    
                else if((  ((funcPanel)f.fun[copypressed]).name.getText().equals("BLOCK") ||  
                           ((funcPanel)f.fun[copypressed]).name.getText().equals("IF CONDITIONAL") || 
                           ((funcPanel)f.fun[copypressed]).name.getText().equals("WHILE LOOP") || 
                           ((funcPanel)f.fun[copypressed]).name.getText().equals("FOR LOOP") || 
                           ((funcPanel)f.fun[copypressed]).name.getText().equals("EXCEPTION") || 
                           ((funcPanel)f.fun[copypressed]).name.getText().equals("COMMENTS") || 
                           ((funcPanel)f.fun[copypressed]).name.getText().equals("LOCAL FUNCTION")
                          ) && 
                      (    ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("THEN") || 
                           ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("ELSE") || 
                           ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("BODY") 
                      )
                      )
                {
                        copy.add(menuItem2);
                        move.add(menuItem5);
                        popupMenu.add(copy);
                        popupMenu.addSeparator();
                        popupMenu.add(move);                        
                        ok = true;
                }
                else if(( ((funcPanel)f.fun[copypressed]).name.getText().equals("IF CONDITIONAL") || 
                          ((funcPanel)f.fun[copypressed]).name.getText().equals("WHILE LOOP") || 
                          ((funcPanel)f.fun[copypressed]).name.getText().equals("FOR LOOP") || 
                          ((funcPanel)f.fun[copypressed]).name.getText().equals("BLOCK") || 
                          ((funcPanel)f.fun[copypressed]).name.getText().equals("COMMENTS") || 
                          ((funcPanel)f.fun[copypressed]).name.getText().equals("DECLATARION") || 
                          ((funcPanel)f.fun[copypressed]).name.getText().equals("LOCAL FUNCTION")
                        ) && 
                        ( ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("IF CONDITIONAL") || 
                          ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("WHILE LOOP") || 
                          ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("FOR LOOP") 
                          ))
                {
                        copy.add(menuItem1);
                        copy.add(menuItem3);
                        move.add(menuItem4);
                        move.add(menuItem6);     
                        popupMenu.add(copy);
                        popupMenu.addSeparator();
                        popupMenu.add(move);                           
                        ok = true;                        
                }
                else if(  ((funcPanel)f.fun[this.copypressed]).name.getText().equals("BLOCK") && 
                          (  ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("BODY") || 
                             ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("EXCEPTION")  || 
                             ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("THEN")  || 
                             ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("ELSE")
                          )
                        )
                {
                        copy.add(menuItem2);
                        move.add(menuItem5);
                        popupMenu.add(copy);
                        popupMenu.addSeparator();
                        popupMenu.add(move);                             
                        ok = true;
                }
                else if( (  ((funcPanel)f.fun[copypressed]).name.getText().equals("IF CONDITIONAL") || 
                            ((funcPanel)f.fun[copypressed]).name.getText().equals("WHILE LOOP") || 
                            ((funcPanel)f.fun[copypressed]).name.getText().equals("FOR LOOP") || 
                            ((funcPanel)f.fun[copypressed]).name.getText().equals("EXCEPTION") || 
                            ((funcPanel)f.fun[copypressed]).name.getText().equals("COMMENTS") || 
                            ((funcPanel)f.fun[copypressed]).name.getText().equals("LOCAL FUNCTION")
                          ) && 
                          ( ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("BLOCK") ||
                            ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("LOCAL FUNCTION") 
                          )
                        )
                {
                        copy.add(menuItem1);
                        copy.add(menuItem2);
                        copy.add(menuItem3);
                        move.add(menuItem4);
                        move.add(menuItem5);
                        move.add(menuItem6);    
                        popupMenu.add(copy);
                        popupMenu.addSeparator();
                        popupMenu.add(move);                          
                        ok = true;
                }
          }
          else if( f.fun[this.copyreleased].getClass().toString().equals("class iisc.funcLabel")) 
            {
                if( !((JLabel)((funcLabel)f.fun[this.copyreleased]).label.getSelectedItem()).getText().equals("Condition") && !((JLabel)((funcLabel)f.fun[copyreleased]).label.getSelectedItem()).getText().equals("Begin") && !((JLabel)((funcLabel)f.fun[copyreleased]).label.getSelectedItem()).getText().equals("Step") && !((JLabel)((funcLabel)f.fun[copyreleased]).label.getSelectedItem()).getText().equals("Param") &&
                ( ((funcPanel)f.fun[copypressed]).name.getText().equals("IF CONDITIONAL") || ((funcPanel)f.fun[copypressed]).name.getText().equals("WHILE LOOP") || ((funcPanel)f.fun[copypressed]).name.getText().equals("FOR LOOP") || ((funcPanel)f.fun[copypressed]).name.getText().equals("BLOCK"))                    )
                {
                        popupMenu.add(menuItem1);
                        //popupMenu.add(menuItem2);
                        popupMenu.add(menuItem3);
                        popupMenu.addSeparator();
                        popupMenu.add(menuItem4);
                        //popupMenu.add(menuItem5);
                        popupMenu.add(menuItem6);      
                        ok = true;                        
                }
            }
         
          menuItem1.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              popup_actionPerformed1(e);
            }
          });                
          menuItem2.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              popup_actionPerformed2(e);
            }
          });              
          menuItem3.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              popup_actionPerformed3(e);
            }
          });                 
          
          menuItem4.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              popup_actionPerformed4(e);
            }
          });                
          menuItem5.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              popup_actionPerformed5(e);
            }
          });              
          menuItem6.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              popup_actionPerformed6(e);
            }
          });                
          
          if(!ok)
          {
              menuItem2.setText("Cannot drag&drop this block");
              menuItem2.setEnabled(false);
              popupMenu.add(menuItem2);
          }
                popupMenu.setSelected(popupMenu);
                if (f.fun[this.copypressed].getClass().toString().equals("class iisc.funcPanel"))
                  popupMenu.show((funcPanel)f.fun[this.copypressed],e.getX(),e.getY());
                else if (f.fun[this.copypressed].getClass().toString().equals("class iisc.funcLabel"))
                  popupMenu.show((funcLabel)f.fun[this.copypressed],e.getX(),e.getY());
                
                f.clicked = this.copyreleased;
                //this.kliklbl.setText(""+this.released);
      }
      
      fillPanel();
  

      
  }

  private void popup_actionPerformed1(ActionEvent e)
  {
      f.pozklik = -1;
      f.copy(copypressed,this.copyreleased);
      fillPanel();
  }
  
  private void popup_actionPerformed2(ActionEvent e)
  {
      f.pozklik = 0;
      f.copy(copypressed,this.copyreleased);
      fillPanel();
  }

  private void popup_actionPerformed3(ActionEvent e)
  {
      f.pozklik = 1;
      f.copy(copypressed,this.copyreleased);
      fillPanel();
  }

  private void popup_actionPerformed4(ActionEvent e)
  {
      if(toDo.equals("rotate else->then"))
      {
          ((funcPanel)f.fun[this.copypressed]).name.setText("THEN");
          ((funcPanel)f.fun[this.copyreleased]).name.setText("ELSE");  
          
          //if(f.lastAdd <= this.copypressed)
          f.pozklik = -1;  
          //brisem = this.copypressed + 1;
      }
      else
      {
        f.pozklik = -1;          
        //brisem = this.copypressed + 1;
      }

      if (f.fun[this.copypressed].getClass().toString().equals("class iisc.funcPanel"))
          ((funcPanel)f.fun[this.copypressed]).delFlag = "ok";
      else if (f.fun[this.copypressed].getClass().toString().equals("class iisc.funcLabel"))
          ((funcLabel)f.fun[this.copypressed]).delFlag = "ok";      

      f.copy(this.copypressed,this.copyreleased);
      //System.out.println("Brisem   NOVO  :"+brisem);
      f.delete();    
      fillPanel();
  }
  
  private void popup_actionPerformed5(ActionEvent e)
  {
      if (f.fun[this.copypressed].getClass().toString().equals("class iisc.funcPanel"))
          ((funcPanel)f.fun[this.copypressed]).delFlag = "ok";
      else if (f.fun[this.copypressed].getClass().toString().equals("class iisc.funcLabel"))
          ((funcLabel)f.fun[this.copypressed]).delFlag = "ok";
          
      f.pozklik = 0;
      f.copy(copypressed,this.copyreleased);

      f.delete();   
      fillPanel();
  }

  private void popup_actionPerformed6(ActionEvent e)
  {

      //int brisem = this.copypressed;        
      
      if(toDo.equals("rotate then->else"))
      {
          ((funcPanel)f.fun[this.copypressed]).name.setText("ELSE");
          ((funcPanel)f.fun[this.copyreleased]).name.setText("THEN");
      }
    
      if (f.fun[this.copypressed].getClass().toString().equals("class iisc.funcPanel"))
          ((funcPanel)f.fun[this.copypressed]).delFlag = "ok";
      else if (f.fun[this.copypressed].getClass().toString().equals("class iisc.funcLabel"))
          ((funcLabel)f.fun[this.copypressed]).delFlag = "ok";    
    
      f.pozklik = 1;
      f.copy(this.copypressed,this.copyreleased);
      //System.out.println("Brisem:"+brisem);
      f.delete();  
      fillPanel();
  }

  private void name_propertyChange(PropertyChangeEvent e)
  {
        if (e.getPropertyName().toString().equals("text"))
        {
            if(name.getText().equals("IF CONDITIONAL") || name.getText().equals("ELSE IF CONDITIONAL"))
                name.setIcon(help);
            else if(name.getText().equals("THEN"))
                name.setIcon(truee);
            else if(name.getText().equals("ELSE"))
                name.setIcon(falsee);                
            else if(name.getText().equals("FOR LOOP"))
                name.setIcon(forr);                     
            else if(name.getText().equals("WHILE LOOP"))
                name.setIcon(whilee);                     
            else if(name.getText().equals("DO WHILE LOOP"))
                name.setIcon(dowhile);                    
            else if(name.getText().equals("EXCEPTION"))
                name.setIcon(exception);                     
            else if(name.getText().equals("DECLARATION"))
                name.setIcon(declaration);                       
            else if(name.getText().equals("COMMENTS"))
                name.setIcon(comments);                       
            else if(name.getText().substring(0,3).equals("FUN") || name.getText().equals("LOCAL FUNCTION"))
                name.setIcon(function);                          
        }    
  }

  private void jMenuItem1_actionPerformed(ActionEvent e)
  {
      f.addBlockIn();
  }

  private void jMenuItem7_actionPerformed(ActionEvent e)
  {
    f.addActionIn();
  }

  private void jMenuItem8_actionPerformed(ActionEvent e)
  {
      f.addForLoop();
  }

  private void jMenuItem9_actionPerformed(ActionEvent e)
  {
      f.addWhileLoop();
  }

  private void jMenuItem10_actionPerformed(ActionEvent e)
  {
      f.addIf();
  }

  private void jMenuItem11_actionPerformed(ActionEvent e)
  {
      f.addException();
  }

  private void jMenuItem4_actionPerformed(ActionEvent e)
  {
      ((funcPanel)f.fun[this.pozition]).delFlag = "ok";
      f.delete();
  }

  private void jMenuItem5_actionPerformed(ActionEvent e)
  {
      f.cutNode = this.pozition;
      f.copyNode = -1;
  }

  private void jMenuItem2_actionPerformed(ActionEvent e)
  {
      f.cutNode = -1;
      f.copyNode = this.pozition;  
  }

  private void jMenuItem3_actionPerformed(ActionEvent e)
  {
      if( f.copyNode != -1 )
      {
          f.copy(f.copyNode,f.clicked);
          f.copyNode = -1;
      }
      else if( f.cutNode != -1)
      {
        if (f.fun[f.cutNode].getClass().toString().equals("class iisc.funcPanel"))
          ((funcPanel)f.fun[f.cutNode]).delFlag = "ok";
        else if (f.fun[f.cutNode].getClass().toString().equals("class iisc.funcLabel"))
          ((funcLabel)f.fun[f.cutNode]).delFlag = "ok";
          
          f.copy(f.cutNode,f.clicked);
          f.delete();        
          f.cutNode = -1;
      }
      jMenuItem3.setEnabled(false);
  }

}