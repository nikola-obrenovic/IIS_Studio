package iisc;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class funcLabel extends funcElement //JPanel 
{
//  public int level = 0; //nivo dubine
//  public int pozition = 1; //pozicija u glavnom array
//  public int father = -1; //otac cvora
//  public String delFlag="";
//  public int copypressed = -1;
//  public int copyreleased = -1;
//  private String toDo="";

  public JTextField action = new JTextField();
  public JComboBox label = new JComboBox();
  //private funAlg f = null;
  private JPopupMenu jPopupMenu1 = new JPopupMenu();
  private JMenuItem jMenuItem1 = new JMenuItem();
  private JMenuItem jMenuItem2 = new JMenuItem();
  private JMenuItem jMenuItem3 = new JMenuItem();
  
private ImageIcon cut = new ImageIcon(IISFrameMain.class.getResource("icons/cut.gif"));   
private ImageIcon copy = new ImageIcon(IISFrameMain.class.getResource("icons/copy.gif"));   
//private ImageIcon paste = new ImageIcon(IISFrameMain.class.getResource("icons/paste.gif"));   
private ImageIcon erase = new ImageIcon(IISFrameMain.class.getResource("icons/erase.gif"));  
  private JTextArea jTextArea1 = new JTextArea();
  
 /* public funcLabel()
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

  }*/

  public funcLabel(int poz,int fat,int l,funAlg fu)
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
    this.setOpaque(true);
    this.setLayout(null);
    this.setSize(new Dimension(282, 15));
    this.setBounds(new Rectangle(10, 10, 280, 15));
    this.setBackground(Color.white);
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
      });
    action.setText("action");
    action.setFont(new Font("Tahoma", 1, 10));
    action.setOpaque(false);
    action.setHorizontalAlignment(JTextField.CENTER);
    ComboBoxRenderer render = new ComboBoxRenderer();
    this.setBorder(BorderFactory.createLineBorder(new Color(216, 216, 216), 1));
    action.setBounds(new Rectangle(140, 0, 140, 15));
    action.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
    action.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
      {
        public void mouseDragged(MouseEvent e)
        {
          action_mouseDragged(e);
        }
      });
    action.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mousePressed(MouseEvent e)
        {
          action_mousePressed(e);
        }

        public void mouseReleased(MouseEvent e)
        {
          action_mouseReleased(e);
        }

        public void mouseEntered(MouseEvent e)
        {
          action_mouseEntered(e);
        }

        public void mouseExited(MouseEvent e)
        {
          action_mouseExited(e);
        }

        public void mouseClicked(MouseEvent e)
        {
          action_mouseClicked(e);
        }
      });
    label.setRenderer(render);
    label.setOpaque(false);
    label.setPreferredSize(new Dimension(140, 15));
    label.setMinimumSize(new Dimension(140, 15));
    label.setBounds(new Rectangle(0, 0, 140, 15));
    label.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
      {
        public void mouseDragged(MouseEvent e)
        {
          label_mouseDragged(e);
        }
      });
    label.addMouseListener(new java.awt.event.MouseAdapter()
      {
        public void mousePressed(MouseEvent e)
        {
          label_mousePressed(e);
        }

        public void mouseReleased(MouseEvent e)
        {
          label_mouseReleased(e);
        }

        public void mouseEntered(MouseEvent e)
        {
          label_mouseEntered(e);
        }

        public void mouseExited(MouseEvent e)
        {
          label_mouseExited(e);
        }

        public void mouseClicked(MouseEvent e)
        {
          label_mouseClicked(e);
        }
      });
    jPopupMenu1.setLabel("jPopupMenu1");
    jMenuItem1.setText("Cut");
    jMenuItem1.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem1.setIcon(cut);
    jMenuItem1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem1_actionPerformed(e);
        }
      });
    jMenuItem2.setText("Copy");
    jMenuItem2.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem2.setIcon(copy);
    jMenuItem2.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem2_actionPerformed(e);
        }
      });
    jMenuItem3.setText("Delete");
    jMenuItem3.setIcon(erase);
    jMenuItem3.setHorizontalAlignment(SwingConstants.LEFT);
    jMenuItem3.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenuItem3_actionPerformed(e);
        }
      });
    jTextArea1.setText("jTextArea1");
    this.add(label, null);
    this.add(action, null);
    jPopupMenu1.add(jMenuItem1);
    jPopupMenu1.add(jMenuItem2);
    jPopupMenu1.add(jMenuItem3);
    
    //action.setUI((ComboBoxUI) MyComboBoxUI.createUI(action));
  }

  public void itemsForDeclaration()
  {
    this.label.removeAllItems();
    this.label.addItem(new JLabel("Type"));
    this.label.addItem(new JLabel("Variable"));
    this.label.addItem(new JLabel("Constant"));
    this.label.addItem(new JLabel("Cursor"));    
  }

  public void itemsForNotes()
  {
    System.out.println("unosim poziciju::"+this.pozition);
    this.label.removeAllItems();
    this.label.addItem(new JLabel("Note"));
  }  
  
  public void itemsForAction()
  {
    System.out.println("unosim poziciju::"+this.pozition);
    this.label.removeAllItems();
    this.label.addItem(new JLabel("Assignment"));
    this.label.addItem(new JLabel("SQL Expression"));
    this.label.addItem(new JLabel("Function Call"));    
  }  

  private void this_mouseEntered(MouseEvent e)
  {
      if(f.clicked != pozition)
        this.setBackground(new Color(165,212,248));
      //System.out.println("Pozicija Labela"+pozicija);
  }


  private void this_mouseExited(MouseEvent e)
  {
        if(f.clicked != pozition)
            this.setBackground(Color.WHITE);
  }

  private void this_mouseClicked(MouseEvent e)
  { 
        f.clicked = this.pozition;
        System.out.println("Label - Pozition:"+pozition+" OTAC:"+this.father);
  }
  
  
  private void this_mousePressed(MouseEvent e)
  {
        //System.out.println("pressed:"+this.pozition);
        f.pressed = pozition;
  }



  private void label_mousePressed(MouseEvent e)
  {
      f.pressed = this.pozition;  
  }

  private void action_mousePressed(MouseEvent e)
  {
      f.pressed = this.pozition;  
  }



  private void label_mouseEntered(MouseEvent e)
  {
      f.released = this.pozition;
      if (f.clicked != pozition)
          this.setBackground(new Color(165,212,248));  
  }

  private void action_mouseEntered(MouseEvent e)
  {
      f.released = this.pozition;
      if ( f.clicked != this.pozition)
          this.setBackground(new Color(165,212,248));

      this.action.setToolTipText(this.action.getText());
      this.label.setToolTipText(this.action.getText());
          
  }

  private void action_mouseExited(MouseEvent e)
  {
      if ( f.clicked != this.pozition)
          this.setBackground(Color.WHITE);  
  }

  private void label_mouseExited(MouseEvent e)
  {
      if ( f.clicked != this.pozition)
          this.setBackground(Color.WHITE);    
  }

  private void action_mouseClicked(MouseEvent e)
  {
      System.out.println("Panel - Pozition:"+pozition+" OTAC:"+this.father);  
      if((e.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
      {      
              jMenuItem1.setEnabled(true);
              jMenuItem2.setEnabled(true);

              jMenuItem3.setEnabled(false);    
              if (f.copyNode != -1 || f.cutNode != -1)
                jMenuItem3.setEnabled(true);   
                
                System.out.println("ssss+++"+((JLabel)(this.label.getSelectedItem())).getText());
                
          if(this.father != 0 && 
          !((JLabel)this.label.getSelectedItem()).getText().equals("Begin")  && 
          !((JLabel)this.label.getSelectedItem()).getText().equals("Condition")   && 
          !((JLabel)this.label.getSelectedItem()).getText().equals("Step")   )
          {                
               jPopupMenu1.setSelected(jPopupMenu1);
               jPopupMenu1.show(action,e.getX(),e.getY());                
          }
      }      
      clickedOnFuncLabel();
  }
  
  private void clickedOnFuncLabel()
  {
        if ( ((funcLabel)f.fun[this.pozition]).father == 0 )
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
        }
      fillPanel();        
  }

  private void label_mouseClicked(MouseEvent e)
  {
      //f.kliklbl.setText(""+pozition);
      f.clicked = this.pozition;
      System.out.println("Panel - Pozition:"+pozition+" OTAC:"+this.father); 
      if((e.getModifiers() & InputEvent.BUTTON3_MASK)== InputEvent.BUTTON3_MASK)
      {      
              jMenuItem1.setEnabled(true);
              jMenuItem2.setEnabled(true);

              jMenuItem3.setEnabled(false);    
              if (f.copyNode != -1 || f.cutNode != -1)
                jMenuItem3.setEnabled(true);     
                
          if(this.father != 0 && !((JLabel)((funcLabel)f.fun[this.pozition]).label.getSelectedItem()).getText().equals("Begin")  && !((JLabel)((funcLabel)f.fun[this.pozition]).label.getSelectedItem()).getText().equals("Condition")   && !((JLabel)((funcLabel)f.fun[this.pozition]).label.getSelectedItem()).getText().equals("Step")   )
          {                
               jPopupMenu1.setSelected(jPopupMenu1);
               jPopupMenu1.show(label,e.getX(),e.getY());                
          }
      }
      clickedOnFuncLabel();
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
              System.out.println("uradjen MOVE sa lijevim tasterom");
              toDo="";
              System.out.println("razliciti");
              System.out.println("Pressed:"+this.copypressed); 
              System.out.println("released:"+this.copyreleased);               
              JPopupMenu popupMenu = new JPopupMenu();    
              JMenu copy = new JMenu("Copy");
              JMenu move = new JMenu("Move");

              JMenuItem menuItem1 = new JMenuItem("Copy Above"); 
              JMenuItem menuItem2 = new JMenuItem("Copy in Block"); 
              JMenuItem menuItem3 = new JMenuItem("Copy After"); 
              
              JMenuItem menuItem4 = new JMenuItem("Move Above"); 
              JMenuItem menuItem5 = new JMenuItem("Move in Block"); 
              JMenuItem menuItem6 = new JMenuItem("Move After"); 
              
              if( f.fun[this.copypressed].getClass().toString().equals("class iisc.funcPanel") && f.fun[this.copyreleased].getClass().toString().equals("class iisc.funcPanel"))
              {/*
                    if( ((funcPanel)f.fun[this.copypressed]).name.getText().equals("THEN") && ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("ELSE"))
                    {
                        if ( ((funcPanel)f.fun[this.copypressed]).father == ((funcPanel)f.fun[this.copyreleased]).father)
                        {
                            menuItem6.setText("Rotate THEN - ELSE");
                            popupMenu.add(menuItem6);
                            toDo = "rotate then->else";
                            ok = true;
                        }
                    }
                    else if( ((funcPanel)f.fun[this.copypressed]).name.getText().equals("ELSE") && ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("THEN"))
                    {
                        if ( ((funcPanel)f.fun[this.copypressed]).father == ((funcPanel)f.fun[this.copyreleased]).father)
                        {
                            menuItem4.setText("Rotate ELSE - THEN");
                            popupMenu.add(menuItem4);
                            toDo = "rotate else->then";
                            ok = true;
                        }
                    }
                    else if( ((funcPanel)f.fun[copypressed]).name.getText().equals("EXCEPTION") && ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("EXCEPTION"))
                    {
                            popupMenu.add(menuItem1);
                            popupMenu.add(menuItem3);
                            popupMenu.addSeparator();
                            popupMenu.add(menuItem4);
                            popupMenu.add(menuItem6);                            
                            ok = true;   
                    }                    
                    else if((  ((funcPanel)f.fun[copypressed]).name.getText().equals("BLOCK") ||  ((funcPanel)f.fun[copypressed]).name.getText().equals("IF CONDITIONAL") || ((funcPanel)f.fun[copypressed]).name.getText().equals("WHILE LOOP") || ((funcPanel)f.fun[copypressed]).name.getText().equals("FOR LOOP") || ((funcPanel)f.fun[copypressed]).name.getText().equals("EXCEPTION")) && 
                    ( ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("THEN") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("ELSE") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("BODY") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("EXCEPTION")) )
                    {
                            popupMenu.add(menuItem2);
                            popupMenu.add(menuItem5);
                            ok = true;
                    }
                    else if(( ((funcPanel)f.fun[copypressed]).name.getText().equals("IF CONDITIONAL") || ((funcPanel)f.fun[copypressed]).name.getText().equals("WHILE LOOP") || ((funcPanel)f.fun[copypressed]).name.getText().equals("FOR LOOP") || ((funcPanel)f.fun[copypressed]).name.getText().equals("EXCEPTION")  || ((funcPanel)f.fun[copypressed]).name.getText().equals("BLOCK"))&& 
                    ( ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("IF CONDITIONAL") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("WHILE LOOP") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("FOR LOOP") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("EXCEPTION")))
                    {
                            popupMenu.add(menuItem1);
                            popupMenu.add(menuItem3);
                            popupMenu.addSeparator();
                            popupMenu.add(menuItem4);
                            popupMenu.add(menuItem6);                            
                            ok = true;                        
                    }
                    else if( ((funcPanel)f.fun[this.copypressed]).name.getText().equals("BLOCK") && (((funcPanel)f.fun[this.copyreleased]).name.getText().equals("BODY") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("EXCEPTION")  || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("THEN")  || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("ELSE")))
                    {
                            popupMenu.add(menuItem2);
                            popupMenu.add(menuItem5);
                            ok = true;
                    }
                    else if( ( ((funcPanel)f.fun[copypressed]).name.getText().equals("IF CONDITIONAL") || ((funcPanel)f.fun[copypressed]).name.getText().equals("WHILE LOOP") || ((funcPanel)f.fun[copypressed]).name.getText().equals("FOR LOOP") || ((funcPanel)f.fun[copypressed]).name.getText().equals("EXCEPTION")) && 
                    ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("BLOCK"))
                    {
                            popupMenu.add(menuItem1);
                            popupMenu.add(menuItem2);
                            popupMenu.add(menuItem3);
                            popupMenu.addSeparator();
                            popupMenu.add(menuItem4);
                            popupMenu.add(menuItem5);
                            popupMenu.add(menuItem6);      
                            ok = true;
                    }*/
              }
              else if( f.fun[copypressed].getClass().toString().equals("class iisc.funcLabel") && f.fun[this.copyreleased].getClass().toString().equals("class iisc.funcLabel")) 
                {
                    if( !((JLabel)((funcLabel)f.fun[this.copypressed]).label.getSelectedItem()).getText().equals("Condition") && !((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Begin") && !((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Step") && !((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Param") &&
                      !((JLabel)((funcLabel)f.fun[this.copyreleased]).label.getSelectedItem()).getText().equals("Condition") && !((JLabel)((funcLabel)f.fun[copyreleased]).label.getSelectedItem()).getText().equals("Begin") && !((JLabel)((funcLabel)f.fun[copyreleased]).label.getSelectedItem()).getText().equals("Step") && !((JLabel)((funcLabel)f.fun[copyreleased]).label.getSelectedItem()).getText().equals("Param") )
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
              //else if( f.fun[copypressed].getClass().toString().equals("class iisc.funcPanel") && f.fun[this.copyreleased].getClass().toString().equals("class iisc.funcLabel")) 
                //{}
              else if( f.fun[copypressed].getClass().toString().equals("class iisc.funcLabel") && f.fun[this.copyreleased].getClass().toString().equals("class iisc.funcPanel")) 
                {
                    if(((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Condition") && ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("BODY"))
                    {
                        if ( ((funcLabel)f.fun[copypressed]).father == ((funcPanel)f.fun[this.copyreleased]).father && ((funcPanel)f.fun[((funcLabel)f.fun[copypressed]).father]).name.getText().equals("WHILE LOOP") &&
                        ((funcLabel)f.fun[copypressed]).pozition < ((funcPanel)f.fun[this.copyreleased]).pozition)
                        {
                            menuItem6.setText("Move Condition AFTER body of loop");
                            popupMenu.add(menuItem6);
                            toDo = "convert while->dowhile";
                            ok = true;
                        }
                        else if ( ((funcLabel)f.fun[copypressed]).father == ((funcPanel)f.fun[this.copyreleased]).father && ((funcPanel)f.fun[((funcLabel)f.fun[copypressed]).father]).name.getText().equals("WHILE LOOP") &&
                        ((funcLabel)f.fun[copypressed]).pozition > ((funcPanel)f.fun[this.copyreleased]).pozition)
                        {
                            menuItem4.setText("Move Condition BEFORE body of loop");
                            popupMenu.add(menuItem4);
                            toDo = "convert dowhile->while";
                            ok = true;
                        }                        
                    }
                    /*else if( ((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Condition") && ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("BODY"))
                    {

                    }*/
                    else if(  !((JLabel)((funcLabel)f.fun[this.copypressed]).label.getSelectedItem()).getText().equals("Condition") && !((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Begin") && !((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Step") && !((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Param") &&
                    ( ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("EXCEPTION") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("ELSE") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("THEN") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("BODY") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("BLOCK"))                    )
                    {
                        popupMenu.add(menuItem2);
                        popupMenu.addSeparator();
                        popupMenu.add(menuItem5);                                                
                        if(((funcPanel)f.fun[this.copyreleased]).name.getText().equals("BLOCK"))
                        {
                            popupMenu.removeAll();
                            popupMenu.add(menuItem1);
                            popupMenu.add(menuItem2);
                            popupMenu.add(menuItem3);
                            popupMenu.addSeparator();
                            popupMenu.add(menuItem4);
                            popupMenu.add(menuItem5);
                            popupMenu.add(menuItem6);                           
                        }
                        ok = true;
                    }
                    else if(  !((JLabel)((funcLabel)f.fun[this.copypressed]).label.getSelectedItem()).getText().equals("Condition") && !((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Begin") && !((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Step") && !((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Param") &&
                    ( ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("FOR LOOP") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("WHILE LOOP") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("IF CONDITIONAL")         )                    )
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
                    /*else if( !((JLabel)((funcLabel)f.fun[this.copypressed]).label.getSelectedItem()).getText().equals("Condition") && !((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Begin") && !((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Step") && !((JLabel)((funcLabel)f.fun[copypressed]).label.getSelectedItem()).getText().equals("Param") &&
                    (((funcPanel)f.fun[this.copyreleased]).name.getText().equals("-BODY") ||  ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("-BODY-WHILE-") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("-BODY-FOR-") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("-THEN-") || 
                    ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("-ELSE-") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("-BODY-FUNCTION-") || ((funcPanel)f.fun[this.copyreleased]).name.getText().equals("-EXCEPTION-")))
                    {
                        //copy.add(menuItem2);
                        //move.add(menuItem5);
                        popupMenu.add(menuItem2);
                        popupMenu.addSeparator();
                        popupMenu.add(menuItem5);                        
                        ok = true;
                    }*/
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
  
      //int brisem = this.copypressed;    
      //System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
      //System.out.println("Brisem   STARO  :"+brisem);
      
      if(toDo.equals("rotate else->then"))
      {
          ((funcPanel)f.fun[this.copypressed]).name.setText("THEN");
          ((funcPanel)f.fun[this.copyreleased]).name.setText("ELSE");  
          
          //if(f.lastAdd <= this.copypressed)
          f.pozklik = -1;  
          //brisem = this.copypressed + 1;
      }
      else if(toDo.equals("convert dowhile->while"))
      {
          f.pozklik = -1;
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
      
      //int brisem = copypressed;      
      //System.out.println("Brisem:"+brisem);
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
          f.pozklik = 1;
      }
      else if(toDo.equals("convert while->dowhile"))
      {
          f.pozklik = 1;
      }
      else
          f.pozklik = 1;
    
      if (f.fun[this.copypressed].getClass().toString().equals("class iisc.funcPanel"))
          ((funcPanel)f.fun[this.copypressed]).delFlag = "ok";
      else if (f.fun[this.copypressed].getClass().toString().equals("class iisc.funcLabel"))
          ((funcLabel)f.fun[this.copypressed]).delFlag = "ok";    
    
      
      f.copy(this.copypressed,this.copyreleased);
      //System.out.println("Brisem:"+brisem);
      f.delete();  
      fillPanel();
  }  
 /* 
  private void fillPanel()
  {
          for(int i=0;i<f.fun.length;i++)
            if (i != this.pozition)
            {
                if ( f.fun[i].getClass().toString().equals("class iisc.funcPanel") )
                    ((funcPanel)f.fun[i]).setBackground(Color.WHITE);                                      
                else if ( f.fun[i].getClass().toString().equals("class iisc.funcLabel") )
                    ((funcLabel)f.fun[i]).setBackground(Color.WHITE);
            }
            else
            {
                if ( f.fun[i].getClass().toString().equals("class iisc.funcPanel") )
                    ((funcPanel)f.fun[i]).setBackground(new Color(216,216,216));
                else if ( f.fun[i].getClass().toString().equals("class iisc.funcLabel") )
                    ((funcLabel)f.fun[i]).setBackground(new Color(216,216,216));              
            }
  }  
  */
  
  private void label_mouseReleased(MouseEvent e)
  {
        ImageIcon icon = new ImageIcon(IISFrameMain.class.getResource("icons/cursor.gif")); 
        Image a = icon.getImage();          
        f.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(a,new java.awt.Point(1,1),"test") );
        replaceNodes(e);
  }  

  private void action_mouseReleased(MouseEvent e)
  {
        ImageIcon icon = new ImageIcon(IISFrameMain.class.getResource("icons/cursor.gif")); 
        Image a = icon.getImage();          
        f.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(a,new java.awt.Point(1,1),"test") );  
        replaceNodes(e);
  }

  private void jMenuItem1_actionPerformed(ActionEvent e)
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
        ((funcLabel)f.fun[this.pozition]).delFlag = "ok";
        f.delete();  
  }

  private void label_mouseDragged(MouseEvent e)
  {
      ImageIcon icon = new ImageIcon(IISFrameMain.class.getResource("icons/cursordrag.gif")); 
      Image a = icon.getImage();          
      f.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(a,new java.awt.Point(1,1),"test") );    
  }

  private void action_mouseDragged(MouseEvent e)
  {
      ImageIcon icon = new ImageIcon(IISFrameMain.class.getResource("icons/cursordrag.gif")); 
      Image a = icon.getImage();          
      f.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(a,new java.awt.Point(1,1),"test") );      
  }


}
